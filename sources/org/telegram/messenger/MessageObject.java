package org.telegram.messenger;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.view.View;
import androidx.collection.LongSparseArray;
import androidx.core.graphics.ColorUtils;
import com.android.p006dx.rop.code.RegisterSpec;
import com.chaquo.python.internal.Common;
import com.exteragram.messenger.utils.p020ui.TextPaint;
import com.exteragram.messenger.utils.text.LocaleUtils;
import de.robv.android.xposed.callbacks.XCallback;
import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.time.DurationKt;
import me.vkryl.core.BitwiseUtils;
import okhttp3.internal.url._UrlKt;
import org.mvel2.MVEL;
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.messenger.BotInlineKeyboard;
import org.telegram.messenger.CodeHighlighting;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.SvgHelper;
import org.telegram.messenger.ringtone.RingtoneDataStore;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Business.QuickRepliesController;
import org.telegram.p035ui.Cells.ChatMessageCell;
import org.telegram.p035ui.ChatActivity;
import org.telegram.p035ui.Components.AnimatedEmojiDrawable;
import org.telegram.p035ui.Components.AnimatedEmojiSpan;
import org.telegram.p035ui.Components.ButtonBounce;
import org.telegram.p035ui.Components.ColoredImageSpan;
import org.telegram.p035ui.Components.Forum.ForumUtilities;
import org.telegram.p035ui.Components.LatexInliner;
import org.telegram.p035ui.Components.QuoteSpan;
import org.telegram.p035ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p035ui.Components.Reactions.ReactionsUtils;
import org.telegram.p035ui.Components.Text;
import org.telegram.p035ui.Components.TextStyleSpan;
import org.telegram.p035ui.Components.TranscribeButton;
import org.telegram.p035ui.Components.URLSpanBrowser;
import org.telegram.p035ui.Components.URLSpanNoUnderline;
import org.telegram.p035ui.Components.URLSpanNoUnderlineBold;
import org.telegram.p035ui.Components.URLSpanReplacement;
import org.telegram.p035ui.Components.VideoPlayer;
import org.telegram.p035ui.Components.WebPlayerView;
import org.telegram.p035ui.Components.spoilers.SpoilerEffect;
import org.telegram.p035ui.LaunchActivity;
import org.telegram.p035ui.MultiLayoutTypingAnimator;
import org.telegram.p035ui.PhotoViewer;
import org.telegram.p035ui.Stars.StarsIntroActivity;
import org.telegram.p035ui.Stories.StoriesController;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_iv;
import org.telegram.tgnet.p034tl.TL_stories;

/* JADX INFO: loaded from: classes.dex */
public class MessageObject {
    public static final int ENTITIES_ALL = 0;
    public static final int ENTITIES_ONLY_HASHTAGS = 1;
    private static final int LINES_PER_BLOCK = 10;
    private static final int LINES_PER_BLOCK_WITH_EMOJI = 5;
    public static final int MESSAGE_SEND_STATE_EDITING = 3;
    public static final int MESSAGE_SEND_STATE_SENDING = 1;
    public static final int MESSAGE_SEND_STATE_SEND_ERROR = 2;
    public static final int MESSAGE_SEND_STATE_SENT = 0;
    public static final int POSITION_FLAG_BOTTOM = 8;
    public static final int POSITION_FLAG_LEFT = 1;
    public static final int POSITION_FLAG_RIGHT = 2;
    public static final int POSITION_FLAG_TOP = 4;
    public static final int SUGGESTION_FLAG_EDIT_MEDIA = 8;
    public static final int SUGGESTION_FLAG_EDIT_PRCIE = 1;
    public static final int SUGGESTION_FLAG_EDIT_TEXT = 4;
    public static final int SUGGESTION_FLAG_EDIT_TIME = 2;
    public static final int TYPE_ACTION_PHOTO = 11;
    public static final int TYPE_ACTION_WALLPAPER = 22;
    public static final int TYPE_ANIMATED_STICKER = 15;
    public static final int TYPE_ARTICLE = 36;
    public static final int TYPE_CONTACT = 12;
    public static final int TYPE_DATE = 10;
    public static final int TYPE_EMOJIS = 19;
    public static final int TYPE_EXTENDED_MEDIA_PREVIEW = 20;
    public static final int TYPE_FILE = 9;
    public static final int TYPE_GEO = 4;
    public static final int TYPE_GIF = 8;
    public static final int TYPE_GIFT_OFFER = 33;
    public static final int TYPE_GIFT_OFFER_REJECTED = 34;
    public static final int TYPE_GIFT_PREMIUM = 18;
    public static final int TYPE_GIFT_PREMIUM_CHANNEL = 25;
    public static final int TYPE_GIFT_STARS = 30;
    public static final int TYPE_GIFT_THEME_UPDATE = 31;
    public static final int TYPE_GIVEAWAY = 26;
    public static final int TYPE_GIVEAWAY_RESULTS = 28;
    public static final int TYPE_JOINED_CHANNEL = 27;
    public static final int TYPE_LOADING = 6;
    public static final int TYPE_MUSIC = 14;
    public static final int TYPE_PAID_MEDIA = 29;
    public static final int TYPE_PHONE_CALL = 16;
    public static final int TYPE_PHOTO = 1;
    public static final int TYPE_POLL = 17;
    public static final int TYPE_ROUND_VIDEO = 5;
    public static final int TYPE_SHARING_OFFER = 35;
    public static final int TYPE_STICKER = 13;
    public static final int TYPE_STORY = 23;
    public static final int TYPE_STORY_MENTION = 24;
    public static final int TYPE_SUGGEST_BIRTHDAY = 32;
    public static final int TYPE_SUGGEST_PHOTO = 21;
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_VIDEO = 3;
    public static final int TYPE_VOICE = 2;
    private static CharSequence channelSpan;
    static final String[] excludeWords = {" vs. ", " vs ", " versus ", " ft. ", " ft ", " featuring ", " feat. ", " feat ", " presents ", " pres. ", " pres ", " and ", " & ", " . "};
    private static CharSequence groupSpan;
    public static Pattern instagramUrlPattern;
    private static Pattern loginCodePattern;
    public static Pattern urlPattern;
    private static CharSequence[] userSpan;
    public static Pattern videoTimeUrlPattern;
    public long actionDeleteGroupEventId;
    public boolean animateComments;
    public int animatedEmojiCount;
    public boolean attachPathExists;
    public double attributeDuration;
    public Bitmap audioCover;
    public int audioPlayerDuration;
    public float audioProgress;
    public int audioProgressMs;
    public int audioProgressSec;
    public StringBuilder botButtonsLayout;
    public float bufferedProgress;
    public boolean business;
    private Integer cachedApproximateHeight;
    public Boolean cachedIsSupergroup;
    public VideoPlayer.VideoUri cachedQuality;
    public Float cachedSavedTimestamp;
    private Integer cachedStartsTimestamp;
    private Integer cachedTextHeight;
    public boolean cancelEditing;
    public CharSequence caption;
    private boolean captionSummarized;
    private boolean captionTranslated;
    private boolean channelJoined;
    public boolean channelJoinedExpanded;
    public ArrayList<TLRPC.PollAnswer> checkedVotes;
    public int contentType;
    public int currentAccount;
    public TLRPC.TL_channelAdminLogEvent currentEvent;
    public Drawable customAvatarDrawable;
    public String customName;
    public String customReplyName;
    public String dateKey;
    public int dateKeyInt;
    public boolean deleted;
    public boolean deletedByThanos;
    public TLRPC.Document documentToPollAddOption;
    public boolean drawServiceWithDefaultTypeface;
    public CharSequence editingMessage;
    public ArrayList<TLRPC.MessageEntity> editingMessageEntities;
    public boolean editingMessageSearchWebPage;
    public TLRPC.Document emojiAnimatedSticker;
    public String emojiAnimatedStickerColor;
    public Long emojiAnimatedStickerId;
    private boolean emojiAnimatedStickerLoading;
    public TLRPC.VideoSize emojiMarkup;
    public int emojiOnlyCount;
    public long eventId;
    public boolean expandedExplanation;
    public HashSet<Integer> expandedQuotes;
    public long extendedMediaLastCheckTime;
    public boolean factCheckExpanded;
    private CharSequence factCheckText;
    public boolean flickerLoading;
    public boolean forceAvatar;
    public boolean forceExpired;
    public boolean forcePlayEffect;
    public float forceSeekTo;
    public boolean forceShowPollResults;
    public boolean forceUpdate;
    private float generatedWithDensity;
    private float generatedWithFontSize;
    private int generatedWithMinSize;
    public float gifState;
    public boolean hadAnimationNotReadyLoading;
    public boolean hasCode;
    public boolean hasCodeAtBottom;
    public boolean hasCodeAtTop;
    public boolean hasQuote;
    public boolean hasQuoteAtBottom;
    public boolean hasRtl;
    public boolean hasSingleCode;
    public boolean hasSingleQuote;
    private boolean hasUnwrappedEmoji;
    public boolean hasWideCode;
    public boolean hideSendersName;
    public VideoPlayer.VideoUri highestQuality;
    public ArrayList<String> highlightedWords;
    private BotInlineKeyboard.Source inlineKeyboardSource;
    public boolean isBotPendingDraft;
    public boolean isDateObject;
    public boolean isDownloadingFile;
    private Boolean isEmbedVideoCached;
    public boolean isLiveStoryPush;
    public boolean isMediaSpoilersRevealed;
    public boolean isMediaSpoilersRevealedInSharedMedia;
    public boolean isOauthPush;
    public Boolean isOutOwnerCached;
    public boolean isPlayingExplanationObject;
    public boolean isPrimaryGroupMessage;
    public boolean isReactionPush;
    public boolean isRepostPreview;
    public boolean isRepostVideoPreview;
    public boolean isRestrictedMessage;
    private int isRoundVideoCached;
    public boolean isSaved;
    public boolean isSavedFiltered;
    public Boolean isSensitiveCached;
    public boolean isSpoilersRevealed;
    public boolean isStoryMentionPush;
    public boolean isStoryPush;
    public boolean isStoryPushHidden;
    public boolean isStoryReactionPush;
    public boolean isTopicMainMessage;
    public boolean isVideoConversionObject;
    public Object lastGeoWebFileLoaded;
    public Object lastGeoWebFileSet;
    public int lastLineWidth;
    private boolean layoutCreated;
    public CharSequence linkDescription;
    public long loadedFileSize;
    public boolean loadingCancelled;
    public boolean localChannel;
    public boolean localEdit;
    public long localGroupId;
    public String localName;
    public long localSentGroupId;
    public boolean localSupergroup;
    public int localType;
    public String localUserName;
    public boolean mediaExists;
    public ImageLocation mediaSmallThumb;
    public ImageLocation mediaThumb;
    public TLRPC.Message messageOwner;
    public CharSequence messageText;
    public CharSequence messageTextForReply;
    public CharSequence messageTextShort;
    public CharSequence messageTrimmedToHighlight;
    public boolean messageTrimmedToHighlightCut;
    public String monthKey;
    public boolean notime;
    public boolean openedInViewer;
    public int overrideLinkColor;
    public long overrideLinkEmoji;
    public TLRPC.TL_peerColorCollectible overrideLinkPeerColor;
    public StoriesController.StoriesList parentStoriesList;
    public int parentWidth;
    public SvgHelper.SvgDrawable pathThumb;
    public ArrayList<TLRPC.PhotoSize> photoThumbs;
    public ArrayList<TLRPC.PhotoSize> photoThumbs2;
    public TLObject photoThumbsObject;
    public TLObject photoThumbsObject2;
    public boolean playedGiftAnimation;
    public long pollLastCheckTime;
    public ArrayList<Integer> pollMediaMapping;
    public boolean pollVisibleOnScreen;
    public boolean preview;
    public boolean previewForward;
    public String previousAttachPath;
    public TLRPC.MessageMedia previousMedia;
    public String previousMessage;
    public ArrayList<TLRPC.MessageEntity> previousMessageEntities;
    public boolean putInDownloadsStore;
    public TLRPC.Document qualityToSave;
    public String quick_reply_shortcut;
    public CharSequence quizExplanation;
    private byte[] randomWaveform;
    public boolean reactionsChanged;
    public long reactionsLastCheckTime;
    public int realDate;
    public MessageObject replyMessageObject;
    public boolean replyTextEllipsized;
    public boolean replyTextRevealed;
    public TLRPC.TL_forumTopic replyToForumTopic;
    public boolean resendAsIs;
    public boolean revealingMediaSpoilers;
    public RichMessageLayout richLayout;
    public int richMessageMediaType;
    public boolean scheduled;
    public boolean scheduledSent;
    public int searchType;
    private CharSequence secretOnceSpan;
    private CharSequence secretPlaySpan;
    public SendAnimationData sendAnimationData;
    public TLRPC.Peer sendAsPeer;
    public boolean sendPreview;
    public MediaController.PhotoEntry sendPreviewEntry;
    public boolean sentHighQuality;
    public boolean settingAvatar;
    public boolean shouldRemoveVideoEditedInfo;
    public boolean sideMenuEnabled;
    private boolean spoiledLoginCode;
    public String sponsoredAdditionalInfo;
    public String sponsoredButtonText;
    public boolean sponsoredCanReport;
    public TLRPC.PeerColor sponsoredColor;
    public byte[] sponsoredId;
    public String sponsoredInfo;
    public TLRPC.MessageMedia sponsoredMedia;
    public TLRPC.Photo sponsoredPhoto;
    public boolean sponsoredRecommended;
    public String sponsoredTitle;
    public String sponsoredUrl;
    public int stableId;
    public TL_stories.StoryItem storyItem;
    private TLRPC.WebPage storyMentionWebpage;
    public BitmapDrawable strippedThumb;
    public boolean summarized;
    public ArrayList<TextLayoutBlock> textLayoutBlocks;
    public int textWidth;
    public float textXOffset;
    public VideoPlayer.VideoUri thumbQuality;
    public Drawable[] topicIconDrawable;
    public int totalAnimatedEmojiCount;
    public boolean translated;
    public int type;
    public StoriesController.UploadingStory uploadingStory;
    public boolean useCustomPhoto;
    public CharSequence vCardData;
    public VideoEditedInfo videoEditedInfo;
    public ArrayList<VideoPlayer.Quality> videoQualities;
    private Boolean videoQualitiesCached;
    public boolean viewsReloaded;
    public int wantedBotKeyboardWidth;
    public boolean wasJustSent;
    public boolean wasUnread;
    public ArrayList<TLRPC.MessageEntity> webPageDescriptionEntities;
    public CharSequence youtubeDescription;

    /* JADX INFO: loaded from: classes5.dex */
    public static class SendAnimationData {
        public float currentScale;
        public float currentX;
        public float currentY;
        public ChatMessageCell.TransitionParams fromParams;
        public boolean fromPreview;
        public float height;
        public float progress;
        public float timeAlpha;
        public float width;

        /* JADX INFO: renamed from: x */
        public float f1154x;

        /* JADX INFO: renamed from: y */
        public float f1155y;
    }

    public static void addPhoneLinks(CharSequence charSequence) {
    }

    public void checkForScam() {
    }

    @Deprecated
    public void generateSuggestionApprovalMessageText() {
    }

    public float getProgress() {
        return 0.0f;
    }

    public boolean shouldDrawReactionsInLayout() {
        return true;
    }

    public int getChatMode() {
        if (this.scheduled) {
            return 1;
        }
        return isQuickReply() ? 5 : 0;
    }

    public static boolean hasUnreadReactions(TLRPC.Message message) {
        if (message == null) {
            return false;
        }
        return hasUnreadReactions(message.reactions);
    }

    public static boolean hasUnreadReactions(TLRPC.TL_messageReactions tL_messageReactions) {
        if (tL_messageReactions == null) {
            return false;
        }
        for (int i = 0; i < tL_messageReactions.recent_reactions.size(); i++) {
            if (tL_messageReactions.recent_reactions.get(i).unread) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPremiumSticker(TLRPC.Document document) {
        if (document != null && document.thumbs != null) {
            for (int i = 0; i < document.video_thumbs.size(); i++) {
                if ("f".equals(document.video_thumbs.get(i).type)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static long getTopicId(MessageObject messageObject) {
        if (messageObject == null) {
            return 0L;
        }
        return getTopicId(messageObject.currentAccount, messageObject.messageOwner, false);
    }

    public static long getTopicId(int i, TLRPC.Message message) {
        return getTopicId(i, message, false);
    }

    public long getMonoForumTopicId() {
        return getMonoForumTopicId(this.messageOwner);
    }

    public static long getMonoForumTopicId(TLRPC.Message message) {
        TLRPC.Peer peer;
        if (message == null || (peer = message.saved_peer_id) == null) {
            return 0L;
        }
        long j = peer.chat_id;
        if (j != 0) {
            return -j;
        }
        long j2 = peer.channel_id;
        return j2 != 0 ? -j2 : peer.user_id;
    }

    public long getTopicId() {
        return getTopicId(this.currentAccount, this.messageOwner, getForumFlags(MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-getDialogId())), MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(getDialogId()))));
    }

    private static int getForumFlags(TLRPC.Chat chat, TLRPC.User user) {
        return BitwiseUtils.setFlag(BitwiseUtils.setFlag(BitwiseUtils.setFlag(0, 1, ChatObject.isForum(chat)), 4, ChatObject.isMonoForum(chat)), 8, UserObject.isBotForum(user));
    }

    public static long getTopicId(int i, TLRPC.Message message, int i2) {
        long topicId = getTopicId(i, message, BitwiseUtils.hasFlag(i2, 1), BitwiseUtils.hasFlag(i2, 4));
        if (topicId == 0 && BitwiseUtils.hasFlag(i2, 8)) {
            return -1L;
        }
        return topicId;
    }

    @Deprecated
    public static long getTopicId(int i, TLRPC.Message message, boolean z) {
        return getTopicId(i, message, z, false);
    }

    @Deprecated
    private static long getTopicId(int i, TLRPC.Message message, boolean z, boolean z2) {
        int i2;
        long clientUserId = UserConfig.getInstance(i).getClientUserId();
        if (z2) {
            return getMonoForumTopicId(message);
        }
        if ((message.flags & TLObject.FLAG_30) != 0 && DialogObject.getPeerDialogId(message.peer_id) == clientUserId) {
            i2 = message.quick_reply_shortcut_id;
        } else {
            if (!z && i >= 0 && DialogObject.getPeerDialogId(message.peer_id) == clientUserId) {
                return getSavedDialogId(clientUserId, message);
            }
            TLRPC.MessageAction messageAction = message.action;
            if (messageAction instanceof TLRPC.TL_messageActionTopicCreate) {
                i2 = message.f1271id;
            } else {
                TLRPC.MessageReplyHeader messageReplyHeader = message.reply_to;
                if (messageReplyHeader == null || !messageReplyHeader.forum_topic) {
                    return z ? 1L : 0L;
                }
                if ((message instanceof TLRPC.TL_messageService) && !(messageAction instanceof TLRPC.TL_messageActionPinMessage)) {
                    int i3 = messageReplyHeader.reply_to_msg_id;
                    if (i3 == 0) {
                        i3 = messageReplyHeader.reply_to_top_id;
                    }
                    return i3;
                }
                int i4 = messageReplyHeader.reply_to_top_id;
                if (i4 == 0) {
                    i4 = messageReplyHeader.reply_to_msg_id;
                }
                return i4;
            }
        }
        return i2;
    }

    public static boolean isTopicActionMessage(MessageObject messageObject) {
        TLRPC.Message message;
        if (messageObject == null || (message = messageObject.messageOwner) == null) {
            return false;
        }
        TLRPC.MessageAction messageAction = message.action;
        return (messageAction instanceof TLRPC.TL_messageActionTopicCreate) || (messageAction instanceof TLRPC.TL_messageActionTopicEdit);
    }

    public static boolean canCreateStripedThubms() {
        return SharedConfig.getDevicePerformanceClass() == 2;
    }

    public static void normalizeFlags(TLRPC.Message message) {
        TLRPC.Peer peer = message.from_id;
        if (peer == null) {
            message.flags &= -257;
        }
        if (peer == null) {
            message.flags &= -5;
        }
        if (message.reply_to == null) {
            message.flags &= -9;
        }
        if (message.media == null) {
            message.flags &= -513;
        }
        if (message.reply_markup == null) {
            message.flags &= -65;
        }
        if (message.replies == null) {
            message.flags &= -8388609;
        }
        if (message.reactions == null) {
            message.flags &= -1048577;
        }
    }

    public static double getDocumentDuration(TLRPC.Document document) {
        if (document == null) {
            return 0.0d;
        }
        int size = document.attributes.size();
        for (int i = 0; i < size; i++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                return documentAttribute.duration;
            }
            if (documentAttribute instanceof TLRPC.TL_documentAttributeAudio) {
                return documentAttribute.duration;
            }
        }
        return 0.0d;
    }

    public static int getVideoWidth(TLRPC.Document document) {
        if (document == null) {
            return 0;
        }
        int size = document.attributes.size();
        for (int i = 0; i < size; i++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                return documentAttribute.f1256w;
            }
        }
        return 0;
    }

    public static int getVideoHeight(TLRPC.Document document) {
        if (document == null) {
            return 0;
        }
        int size = document.attributes.size();
        for (int i = 0; i < size; i++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                return documentAttribute.f1255h;
            }
        }
        return 0;
    }

    public static String getVideoCodec(TLRPC.Document document) {
        if (document == null) {
            return null;
        }
        int size = document.attributes.size();
        for (int i = 0; i < size; i++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                return ((TLRPC.TL_documentAttributeVideo) documentAttribute).video_codec;
            }
        }
        return null;
    }

    public boolean isWallpaperAction() {
        if (this.type == 22) {
            return true;
        }
        TLRPC.Message message = this.messageOwner;
        return message != null && (message.action instanceof TLRPC.TL_messageActionSetSameChatWallPaper);
    }

    public boolean isWallpaperForBoth() {
        TLRPC.Message message;
        if (!isWallpaperAction() || (message = this.messageOwner) == null) {
            return false;
        }
        TLRPC.MessageAction messageAction = message.action;
        return (messageAction instanceof TLRPC.TL_messageActionSetChatWallPaper) && ((TLRPC.TL_messageActionSetChatWallPaper) messageAction).for_both;
    }

    public boolean isCurrentWallpaper() {
        TLRPC.Message message;
        TLRPC.MessageAction messageAction;
        TLRPC.UserFull userFull;
        TLRPC.WallPaper wallPaper;
        return (!isWallpaperAction() || (message = this.messageOwner) == null || (messageAction = message.action) == null || messageAction.wallpaper == null || (userFull = MessagesController.getInstance(this.currentAccount).getUserFull(getDialogId())) == null || (wallPaper = userFull.wallpaper) == null || !userFull.wallpaper_overridden || this.messageOwner.action.wallpaper.f1415id != wallPaper.f1415id) ? false : true;
    }

    public int getEmojiOnlyCount() {
        return this.emojiOnlyCount;
    }

    public boolean hasMediaSpoilers() {
        TLRPC.MessageMedia messageMedia;
        return (!this.isRepostPreview && (((messageMedia = this.messageOwner.media) != null && messageMedia.spoiler) || needDrawBluredPreview())) || isHiddenSensitive();
    }

    public boolean isSensitive() {
        TLRPC.Chat chat;
        Boolean bool = this.isSensitiveCached;
        if (bool != null) {
            return bool.booleanValue();
        }
        if (this.messageOwner == null || !canBeSensitive()) {
            return false;
        }
        if (!this.messageOwner.restriction_reason.isEmpty()) {
            for (int i = 0; i < this.messageOwner.restriction_reason.size(); i++) {
                TLRPC.RestrictionReason restrictionReason = this.messageOwner.restriction_reason.get(i);
                if ("sensitive".equals(restrictionReason.reason) && ("all".equals(restrictionReason.platform) || (("android".equals(restrictionReason.platform) && (!BuildVars.isBetaApp() || BuildVars.DEBUG_PRIVATE_VERSION)) || "android-all".equals(restrictionReason.platform)))) {
                    this.isSensitiveCached = Boolean.TRUE;
                    return true;
                }
            }
        }
        if (getDialogId() < 0 && (chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-getDialogId()))) != null && chat.restriction_reason != null) {
            for (int i2 = 0; i2 < chat.restriction_reason.size(); i2++) {
                TLRPC.RestrictionReason restrictionReason2 = chat.restriction_reason.get(i2);
                if ("sensitive".equals(restrictionReason2.reason) && ("all".equals(restrictionReason2.platform) || (("android".equals(restrictionReason2.platform) && (!BuildVars.isBetaApp() || BuildVars.DEBUG_PRIVATE_VERSION)) || "android-all".equals(restrictionReason2.platform)))) {
                    this.isSensitiveCached = Boolean.TRUE;
                    return true;
                }
            }
        }
        this.isSensitiveCached = Boolean.FALSE;
        return false;
    }

    public boolean isHiddenSensitive() {
        return isSensitive() && !MessagesController.getInstance(this.currentAccount).showSensitiveContent();
    }

    public boolean canBeSensitive() {
        if (this.messageOwner == null) {
            return false;
        }
        int i = this.type;
        return ((i != 1 && i != 3 && i != 9 && i != 8 && i != 5) || this.sendPreview || this.isRepostPreview || isOutOwner() || this.messageOwner.send_state != 0) ? false : true;
    }

    public boolean shouldDrawReactions() {
        return !this.isRepostPreview;
    }

    public TLRPC.MessagePeerReaction getRandomUnreadReaction() {
        ArrayList<TLRPC.MessagePeerReaction> arrayList;
        TLRPC.TL_messageReactions tL_messageReactions = this.messageOwner.reactions;
        if (tL_messageReactions == null || (arrayList = tL_messageReactions.recent_reactions) == null || arrayList.isEmpty()) {
            return null;
        }
        return this.messageOwner.reactions.recent_reactions.get(0);
    }

    public void markPollVotesAsRead() {
        TLRPC.Message message = this.messageOwner;
        if (message != null) {
            TLRPC.MessageMedia messageMedia = message.media;
            if (messageMedia instanceof TLRPC.TL_messageMediaPoll) {
                ((TLRPC.TL_messageMediaPoll) messageMedia).results.has_unread_votes = false;
            }
        }
    }

    public void markReactionsAsRead() {
        TLRPC.TL_messageReactions tL_messageReactions = this.messageOwner.reactions;
        if (tL_messageReactions == null || tL_messageReactions.recent_reactions == null) {
            return;
        }
        boolean z = false;
        for (int i = 0; i < this.messageOwner.reactions.recent_reactions.size(); i++) {
            if (this.messageOwner.reactions.recent_reactions.get(i).unread) {
                this.messageOwner.reactions.recent_reactions.get(i).unread = false;
                z = true;
            }
        }
        if (z) {
            MessagesStorage messagesStorage = MessagesStorage.getInstance(this.currentAccount);
            TLRPC.Message message = this.messageOwner;
            messagesStorage.markMessageReactionsAsRead(message.dialog_id, getTopicId(this.currentAccount, message), this.messageOwner.f1271id);
        }
    }

    public boolean isPremiumSticker() {
        if (getMedia(this.messageOwner) == null || !getMedia(this.messageOwner).nopremium) {
            return isPremiumSticker(getDocument());
        }
        return false;
    }

    public TLRPC.VideoSize getPremiumStickerAnimation() {
        return getPremiumStickerAnimation(getDocument());
    }

    public static TLRPC.VideoSize getPremiumStickerAnimation(TLRPC.Document document) {
        if (document != null && document.thumbs != null) {
            for (int i = 0; i < document.video_thumbs.size(); i++) {
                if ("f".equals(document.video_thumbs.get(i).type)) {
                    return document.video_thumbs.get(i);
                }
            }
        }
        return null;
    }

    public void copyStableParams(MessageObject messageObject) {
        ArrayList<TextLayoutBlock> arrayList;
        TLRPC.MessageMedia messageMedia;
        ArrayList<TLRPC.ReactionCount> arrayList2;
        TLRPC.TL_messageReactions tL_messageReactions;
        this.stableId = messageObject.stableId;
        TLRPC.Message message = this.messageOwner;
        message.premiumEffectWasPlayed = messageObject.messageOwner.premiumEffectWasPlayed;
        this.forcePlayEffect = messageObject.forcePlayEffect;
        this.wasJustSent = messageObject.wasJustSent;
        TLRPC.TL_messageReactions tL_messageReactions2 = message.reactions;
        int i = 0;
        if (tL_messageReactions2 != null && (arrayList2 = tL_messageReactions2.results) != null && !arrayList2.isEmpty() && (tL_messageReactions = messageObject.messageOwner.reactions) != null && tL_messageReactions.results != null) {
            for (int i2 = 0; i2 < this.messageOwner.reactions.results.size(); i2++) {
                TLRPC.ReactionCount reactionCount = this.messageOwner.reactions.results.get(i2);
                for (int i3 = 0; i3 < messageObject.messageOwner.reactions.results.size(); i3++) {
                    TLRPC.ReactionCount reactionCount2 = messageObject.messageOwner.reactions.results.get(i3);
                    if (ReactionsLayoutInBubble.equalsTLReaction(reactionCount.reaction, reactionCount2.reaction)) {
                        reactionCount.lastDrawnPosition = reactionCount2.lastDrawnPosition;
                    }
                }
            }
        }
        boolean z = messageObject.isSpoilersRevealed;
        this.isSpoilersRevealed = z;
        TLRPC.Message message2 = this.messageOwner;
        TLRPC.Message message3 = messageObject.messageOwner;
        message2.replyStory = message3.replyStory;
        TLRPC.MessageMedia messageMedia2 = message2.media;
        if (messageMedia2 != null && (messageMedia = message3.media) != null) {
            messageMedia2.storyItem = messageMedia.storyItem;
        }
        if (!z || (arrayList = this.textLayoutBlocks) == null) {
            return;
        }
        int size = arrayList.size();
        while (i < size) {
            TextLayoutBlock textLayoutBlock = arrayList.get(i);
            i++;
            textLayoutBlock.spoilers.clear();
        }
    }

    public ArrayList<ReactionsLayoutInBubble.VisibleReaction> getChoosenReactions() {
        ArrayList<ReactionsLayoutInBubble.VisibleReaction> arrayList = new ArrayList<>();
        if (this.messageOwner.reactions != null) {
            for (int i = 0; i < this.messageOwner.reactions.results.size(); i++) {
                if (this.messageOwner.reactions.results.get(i).chosen) {
                    arrayList.add(ReactionsLayoutInBubble.VisibleReaction.fromTL(this.messageOwner.reactions.results.get(i).reaction));
                }
            }
        }
        return arrayList;
    }

    public boolean isReplyToStory() {
        TLRPC.Message message;
        TLRPC.MessageReplyHeader messageReplyHeader;
        MessageObject messageObject = this.replyMessageObject;
        return ((messageObject != null && (messageObject.messageOwner instanceof TLRPC.TL_messageEmpty)) || (messageReplyHeader = (message = this.messageOwner).reply_to) == null || messageReplyHeader.story_id == 0 || (message.flags & 8) == 0) ? false : true;
    }

    public boolean isUnsupported() {
        return getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaUnsupported;
    }

    public boolean isExpiredStory() {
        int i = this.type;
        return (i == 23 || i == 24) && (this.messageOwner.media.storyItem instanceof TL_stories.TL_storyItemDeleted);
    }

    /* JADX INFO: loaded from: classes5.dex */
    public static class VCardData {
        private String company;
        private final ArrayList<String> emails = new ArrayList<>();
        private final ArrayList<String> phones = new ArrayList<>();

        public static CharSequence parse(String str) {
            CharSequence charSequence;
            String[] strArr;
            int i;
            byte[] bArrDecodeQuotedPrintable;
            int i2;
            try {
                BufferedReader bufferedReader = new BufferedReader(new StringReader(str));
                int i3 = 0;
                boolean z = false;
                VCardData vCardData = null;
                StringBuilder sb = null;
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    if (!line.startsWith("PHOTO")) {
                        if (line.indexOf(58) >= 0) {
                            if (line.startsWith("BEGIN:VCARD")) {
                                vCardData = new VCardData();
                            } else if (line.startsWith("END:VCARD") && vCardData != null) {
                                z = true;
                            }
                        }
                        if (sb != null) {
                            sb.append(line);
                            line = sb.toString();
                            sb = null;
                        }
                        if (line.contains("=QUOTED-PRINTABLE") && line.endsWith("=")) {
                            sb = new StringBuilder(line.substring(i3, line.length() - 1));
                        } else {
                            int iIndexOf = line.indexOf(":");
                            if (iIndexOf >= 0) {
                                strArr = new String[]{line.substring(i3, iIndexOf), line.substring(iIndexOf + 1).trim()};
                            } else {
                                strArr = new String[]{line.trim()};
                            }
                            if (strArr.length < 2 || vCardData == null) {
                                i = i3;
                            } else if (strArr[i3].startsWith("ORG")) {
                                String[] strArrSplit = strArr[i3].split(";");
                                int length = strArrSplit.length;
                                int i4 = i3;
                                String str2 = null;
                                String str3 = null;
                                while (i4 < length) {
                                    String[] strArrSplit2 = strArrSplit[i4].split("=");
                                    charSequence = null;
                                    try {
                                        if (strArrSplit2.length != 2) {
                                            i2 = i3;
                                        } else {
                                            i2 = i3;
                                            if (strArrSplit2[i3].equals("CHARSET")) {
                                                str3 = strArrSplit2[1];
                                            } else if (strArrSplit2[i2].equals("ENCODING")) {
                                                str2 = strArrSplit2[1];
                                            }
                                        }
                                        i4++;
                                        i3 = i2;
                                    } catch (Throwable unused) {
                                        return charSequence;
                                    }
                                }
                                i = i3;
                                vCardData.company = strArr[1];
                                if (str2 != null && str2.equalsIgnoreCase("QUOTED-PRINTABLE") && (bArrDecodeQuotedPrintable = AndroidUtilities.decodeQuotedPrintable(AndroidUtilities.getStringBytes(vCardData.company))) != null && bArrDecodeQuotedPrintable.length != 0 && str3 != null) {
                                    vCardData.company = new String(bArrDecodeQuotedPrintable, str3);
                                }
                                vCardData.company = vCardData.company.replace(';', ' ');
                            } else {
                                i = i3;
                                if (strArr[i].startsWith("TEL")) {
                                    if (strArr[1].length() > 0) {
                                        vCardData.phones.add(strArr[1]);
                                    }
                                } else if (strArr[i].startsWith("EMAIL")) {
                                    String str4 = strArr[1];
                                    if (str4.length() > 0) {
                                        vCardData.emails.add(str4);
                                    }
                                }
                            }
                            i3 = i;
                        }
                    }
                    return charSequence;
                }
                int i5 = i3;
                charSequence = null;
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    FileLog.m1048e(e);
                }
                if (!z) {
                    return null;
                }
                StringBuilder sb2 = new StringBuilder();
                for (int i6 = i5; i6 < vCardData.phones.size(); i6++) {
                    if (sb2.length() > 0) {
                        sb2.append('\n');
                    }
                    String str5 = vCardData.phones.get(i6);
                    if (str5.contains("#") || str5.contains("*")) {
                        sb2.append(str5);
                    } else {
                        sb2.append(PhoneFormat.getInstance().format(str5));
                    }
                }
                for (int i7 = i5; i7 < vCardData.emails.size(); i7++) {
                    if (sb2.length() > 0) {
                        sb2.append('\n');
                    }
                    sb2.append(PhoneFormat.getInstance().format(vCardData.emails.get(i7)));
                }
                if (!TextUtils.isEmpty(vCardData.company)) {
                    if (sb2.length() > 0) {
                        sb2.append('\n');
                    }
                    sb2.append(vCardData.company);
                }
                return sb2;
            } catch (Throwable unused2) {
                return null;
            }
        }
    }

    public static boolean expandedQuotesEquals(HashSet<Integer> hashSet, HashSet<Integer> hashSet2) {
        if (hashSet == null && hashSet2 == null) {
            return true;
        }
        return (hashSet == null ? 0 : hashSet.size()) == (hashSet2 == null ? 0 : hashSet2.size()) && hashSet != null && hashSet.equals(hashSet2);
    }

    public static class TextLayoutBlock implements MultiLayoutTypingAnimator.Block {
        public static final int FLAG_NOT_RTL = 2;
        public static final int FLAG_RTL = 1;
        public int charactersEnd;
        public int charactersOffset;
        public boolean code;
        public ButtonBounce collapsedBounce;
        public int collapsedHeight;
        public Drawable copyIcon;
        public int copyIconColor;
        public Drawable copySelector;
        public int copySelectorColor;
        public Paint copySeparator;
        public Text copyText;
        public byte directionFlags;
        public boolean first;
        public boolean hasCodeCopyButton;
        public int height;
        public int index;
        public String language;
        public int languageHeight;
        public Text languageLayout;
        public boolean last;
        public float maxRight;
        public MessageObject messageObject;
        public int originalWidth;
        public int padBottom;
        public int padTop;
        public boolean quote;
        public boolean quoteCollapse;
        public int start;
        public StaticLayout textLayout;
        public AtomicReference<Layout> spoilersPatchedTextLayout = new AtomicReference<>();
        public List<SpoilerEffect> spoilers = new ArrayList();

        @Override // org.telegram.ui.MultiLayoutTypingAnimator.Block
        public View getParentView() {
            return null;
        }

        @Override // org.telegram.ui.MultiLayoutTypingAnimator.Block
        public Layout getLayout() {
            return this.textLayout;
        }

        public int heightCollapsed() {
            return this.quoteCollapse ? this.collapsedHeight : this.height;
        }

        public int height() {
            return (this.quoteCollapse && collapsed()) ? this.collapsedHeight : this.height;
        }

        public int height(ChatMessageCell.TransitionParams transitionParams) {
            boolean z = this.quoteCollapse;
            int i = this.height;
            return !z ? i : AndroidUtilities.lerp(i, this.collapsedHeight, collapsed(transitionParams));
        }

        public float collapsed(ChatMessageCell.TransitionParams transitionParams) {
            boolean zCollapsed;
            if (transitionParams.animateExpandedQuotes) {
                HashSet<Integer> hashSet = transitionParams.animateExpandedQuotesFrom;
                zCollapsed = true;
                if (hashSet != null && hashSet.contains(Integer.valueOf(this.index))) {
                    zCollapsed = false;
                }
            } else {
                zCollapsed = collapsed();
            }
            return AndroidUtilities.lerp(zCollapsed ? 1.0f : 0.0f, collapsed() ? 1.0f : 0.0f, transitionParams.animateChangeProgress);
        }

        public boolean collapsed() {
            HashSet<Integer> hashSet;
            MessageObject messageObject = this.messageObject;
            return messageObject == null || (hashSet = messageObject.expandedQuotes) == null || !hashSet.contains(Integer.valueOf(this.index));
        }

        public float textYOffset(ArrayList<TextLayoutBlock> arrayList) {
            TextLayoutBlock textLayoutBlock;
            if (arrayList == null) {
                return 0.0f;
            }
            int iHeight = 0;
            for (int i = 0; i < arrayList.size() && (textLayoutBlock = arrayList.get(i)) != this; i++) {
                iHeight += textLayoutBlock.padTop + textLayoutBlock.height() + textLayoutBlock.padBottom;
            }
            return iHeight;
        }

        public float textYOffset(ArrayList<TextLayoutBlock> arrayList, ChatMessageCell.TransitionParams transitionParams) {
            TextLayoutBlock textLayoutBlock;
            if (arrayList == null) {
                return 0.0f;
            }
            int iHeight = 0;
            for (int i = 0; i < arrayList.size() && (textLayoutBlock = arrayList.get(i)) != this; i++) {
                iHeight += textLayoutBlock.padTop + textLayoutBlock.height(transitionParams) + textLayoutBlock.padBottom;
            }
            return iHeight;
        }

        public void layoutCode(String str, int i, boolean z) {
            boolean z2 = i >= 75 && !z;
            this.hasCodeCopyButton = z2;
            if (z2) {
                this.copyText = new Text(LocaleController.getString(C2797R.string.CopyCode).toUpperCase(), SharedConfig.fontSize - 3, AndroidUtilities.bold());
                Drawable drawableMutate = ApplicationLoader.applicationContext.getResources().getDrawable(C2797R.drawable.msg_copy).mutate();
                this.copyIcon = drawableMutate;
                drawableMutate.setColorFilter(new PorterDuffColorFilter(this.copyIconColor, PorterDuff.Mode.SRC_IN));
                this.copySelector = Theme.createRadSelectorDrawable(this.copySelectorColor, 0, 0, Math.min(5, SharedConfig.bubbleRadius), 0);
                this.copySeparator = new Paint(1);
            }
            if (TextUtils.isEmpty(str)) {
                this.language = null;
                this.languageLayout = null;
            } else {
                this.language = str;
                Text text = new Text(capitalizeLanguage(str), (SharedConfig.fontSize - 1) - (CodeHighlighting.getTextSizeDecrement(i) / 2), AndroidUtilities.bold());
                this.languageLayout = text;
                this.languageHeight = ((int) (text.getTextSize() * 1.714f)) + AndroidUtilities.m1036dp(4.0f);
            }
        }

        public void drawCopyCodeButton(Canvas canvas, RectF rectF, int i, int i2, float f) {
            if (this.hasCodeCopyButton) {
                int iMultAlpha = Theme.multAlpha(i, 0.1f);
                if (this.copySelectorColor != iMultAlpha) {
                    Drawable drawable = this.copySelector;
                    this.copySelectorColor = iMultAlpha;
                    Theme.setSelectorDrawableColor(drawable, iMultAlpha, true);
                }
                this.copySelector.setBounds(((int) rectF.left) + AndroidUtilities.m1036dp(3.0f), (int) (rectF.bottom - AndroidUtilities.m1036dp(38.0f)), (int) rectF.right, (int) rectF.bottom);
                int i3 = (int) (255.0f * f);
                this.copySelector.setAlpha(i3);
                if (this.copySelector.getCallback() != null) {
                    this.copySelector.draw(canvas);
                }
                this.copySeparator.setColor(ColorUtils.setAlphaComponent(i2, 38));
                canvas.drawRect(rectF.left + AndroidUtilities.m1036dp(10.0f), (rectF.bottom - AndroidUtilities.m1036dp(38.0f)) - AndroidUtilities.getShadowHeight(), rectF.right - AndroidUtilities.m1036dp(6.66f), rectF.bottom - AndroidUtilities.m1036dp(38.0f), this.copySeparator);
                float fCenterX = rectF.centerX() - (Math.min(rectF.width() - AndroidUtilities.m1036dp(12.0f), ((this.copyIcon.getIntrinsicWidth() * 0.8f) + AndroidUtilities.m1036dp(5.0f)) + this.copyText.getCurrentWidth()) / 2.0f);
                float fM1036dp = rectF.bottom - (AndroidUtilities.m1036dp(38.0f) / 2.0f);
                if (this.copyIconColor != i) {
                    Drawable drawable2 = this.copyIcon;
                    this.copyIconColor = i;
                    drawable2.setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN));
                }
                this.copyIcon.setAlpha(i3);
                this.copyIcon.setBounds((int) fCenterX, (int) (fM1036dp - ((r1.getIntrinsicHeight() * 0.8f) / 2.0f)), (int) ((this.copyIcon.getIntrinsicWidth() * 0.8f) + fCenterX), (int) (((this.copyIcon.getIntrinsicHeight() * 0.8f) / 2.0f) + fM1036dp));
                this.copyIcon.draw(canvas);
                this.copyText.ellipsize(((int) (r14 - ((this.copyIcon.getIntrinsicWidth() * 0.8f) + AndroidUtilities.m1036dp(5.0f)))) + AndroidUtilities.m1036dp(12.0f)).draw(canvas, fCenterX + (this.copyIcon.getIntrinsicWidth() * 0.8f) + AndroidUtilities.m1036dp(5.0f), fM1036dp, i, f);
            }
        }

        private static String capitalizeLanguage(String str) {
            if (str == null) {
                return null;
            }
            String strReplaceAll = str.toLowerCase().replaceAll("\\W|lang$", _UrlKt.FRAGMENT_ENCODE_SET);
            strReplaceAll.getClass();
            switch (strReplaceAll) {
                case "actionscript":
                    return "ActionScript";
                case "aspnet":
                    return "ASP.NET";
                case "csharp":
                case "cs":
                    return "C#";
                case "docker":
                case "dockerfile":
                case "kotlin":
                case "pascal":
                case "arduino":
                case "c":
                case "go":
                case "lua":
                case "dart":
                case "fift":
                case "java":
                case "rust":
                case "swift":
                    return capitalizeFirst(str);
                case "python":
                case "py":
                    return "Python";
                case "typescript":
                case "ts":
                    return "TypeScript";
                case "r":
                case "tl":
                case "asm":
                case "css":
                case "csv":
                case "ini":
                case "jsx":
                case "php":
                case "tsx":
                case "xml":
                case "yml":
                case "glsl":
                case "hlsl":
                case "html":
                case "http":
                case "json":
                case "less":
                case "nasm":
                case "scss":
                case "wasm":
                case "yaml":
                case "cobol":
                case "json5":
                    return str.toUpperCase();
                case "js":
                case "javascript":
                    return "JavaScript";
                case "md":
                case "markdown":
                    return "Markdown";
                case "rb":
                case "ruby":
                    return "Ruby";
                case "cpp":
                    return "C++";
                case "tlb":
                case "tl-b":
                    return "TL-B";
                case "func":
                    return "FunC";
                case "objc":
                case "objectivec":
                    return "Objective-C";
                case "autohotkey":
                    return "AutoHotKey";
                default:
                    return str;
            }
        }

        private static String capitalizeFirst(String str) {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }

        public boolean isRtl() {
            byte b2 = this.directionFlags;
            return (b2 & 1) != 0 && (b2 & 2) == 0;
        }
    }

    public static class GroupedMessagePosition {
        public float aspectRatio;
        public boolean edge;
        public int flags;
        public boolean last;
        public float left;
        public int leftSpanOffset;
        public byte maxX;
        public byte maxY;
        public byte minX;
        public byte minY;

        /* JADX INFO: renamed from: ph */
        public float f1152ph;
        public int photoHeight;
        public int photoWidth;

        /* JADX INFO: renamed from: pw */
        public int f1153pw;
        public float[] siblingHeights;
        public int spanSize;
        public float top;

        public void set(int i, int i2, int i3, int i4, int i5, float f, int i6) {
            this.minX = (byte) i;
            this.maxX = (byte) i2;
            this.minY = (byte) i3;
            this.maxY = (byte) i4;
            this.f1153pw = i5;
            this.spanSize = i5;
            this.f1152ph = f;
            this.flags = (byte) i6;
        }
    }

    public static class GroupedMessages {
        public boolean captionAbove;
        public MessageObject captionMessage;
        public long groupId;
        public boolean hasCaption;
        public boolean hasSibling;
        public boolean isDocuments;
        public boolean reversed;
        public ArrayList<MessageObject> messages = new ArrayList<>();
        public ArrayList<GroupedMessagePosition> posArray = new ArrayList<>();
        public HashMap<MessageObject, GroupedMessagePosition> positions = new HashMap<>();
        public LongSparseArray<GroupedMessagePosition> positionsArray = new LongSparseArray<>();
        public int cachedWidthForCaption = -1;
        private int maxSizeWidth = 800;
        public final TransitionParams transitionParams = new TransitionParams();

        public GroupedMessagePosition getPosition(MessageObject messageObject) {
            if (messageObject == null) {
                return null;
            }
            GroupedMessagePosition groupedMessagePosition = this.positions.get(messageObject);
            return groupedMessagePosition == null ? this.positionsArray.get(messageObject.getId()) : groupedMessagePosition;
        }

        public static class MessageGroupedLayoutAttempt {
            public float[] heights;
            public int[] lineCounts;

            public MessageGroupedLayoutAttempt(int i, int i2, float f, float f2) {
                this.lineCounts = new int[]{i, i2};
                this.heights = new float[]{f, f2};
            }

            public MessageGroupedLayoutAttempt(int i, int i2, int i3, float f, float f2, float f3) {
                this.lineCounts = new int[]{i, i2, i3};
                this.heights = new float[]{f, f2, f3};
            }

            public MessageGroupedLayoutAttempt(int i, int i2, int i3, int i4, float f, float f2, float f3, float f4) {
                this.lineCounts = new int[]{i, i2, i3, i4};
                this.heights = new float[]{f, f2, f3, f4};
            }
        }

        private float multiHeight(float[] fArr, int i, int i2) {
            float f = 0.0f;
            while (i < i2) {
                f += fArr[i];
                i++;
            }
            return this.maxSizeWidth / f;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:388:0x0064  */
        /* JADX WARN: Removed duplicated region for block: B:389:0x0067  */
        /* JADX WARN: Removed duplicated region for block: B:391:0x006a  */
        /* JADX WARN: Removed duplicated region for block: B:411:0x00af  */
        /* JADX WARN: Removed duplicated region for block: B:413:0x00b5  */
        /* JADX WARN: Removed duplicated region for block: B:429:0x00e6  */
        /* JADX WARN: Removed duplicated region for block: B:430:0x00e9  */
        /* JADX WARN: Removed duplicated region for block: B:433:0x00f7  */
        /* JADX WARN: Removed duplicated region for block: B:434:0x00fe  */
        /* JADX WARN: Removed duplicated region for block: B:440:0x011b  */
        /* JADX WARN: Removed duplicated region for block: B:443:0x0134  */
        /* JADX WARN: Removed duplicated region for block: B:454:0x014c  */
        /* JADX WARN: Removed duplicated region for block: B:455:0x014f  */
        /* JADX WARN: Removed duplicated region for block: B:565:0x06ea  */
        /* JADX WARN: Removed duplicated region for block: B:619:0x07ea  */
        /* JADX WARN: Removed duplicated region for block: B:621:0x07ef  */
        /* JADX WARN: Removed duplicated region for block: B:623:0x07f3  */
        /* JADX WARN: Removed duplicated region for block: B:650:0x085d A[PHI: r3
  0x085d: PHI (r3v16 int) = (r3v15 int), (r3v18 int) binds: [B:645:0x084f, B:647:0x0853] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:655:0x0897  */
        /* JADX WARN: Type inference failed for: r14v29 */
        /* JADX WARN: Type inference failed for: r14v30, types: [boolean] */
        /* JADX WARN: Type inference failed for: r14v37 */
        /* JADX WARN: Type inference failed for: r8v0 */
        /* JADX WARN: Type inference failed for: r8v1 */
        /* JADX WARN: Type inference failed for: r8v68 */
        /* JADX WARN: Type inference failed for: r8v69 */
        /* JADX WARN: Type inference failed for: r8v70 */
        /* JADX WARN: Type inference failed for: r8v72 */
        /* JADX WARN: Type inference failed for: r8v73 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void calculate() {
            /*
                Method dump skipped, instruction units count: 2333
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.GroupedMessages.calculate():void");
        }

        public MessageObject findPrimaryMessageObject() {
            return findMessageWithFlags(this.reversed ? 10 : 5);
        }

        public MessageObject findCaptionMessageObject() {
            if (!this.messages.isEmpty() && this.positions.isEmpty()) {
                calculate();
            }
            MessageObject messageObject = null;
            for (int i = 0; i < this.messages.size(); i++) {
                MessageObject messageObject2 = this.messages.get(i);
                if (!TextUtils.isEmpty(messageObject2.caption)) {
                    if (messageObject != null) {
                        return null;
                    }
                    messageObject = messageObject2;
                }
            }
            return messageObject;
        }

        public MessageObject findMessageWithFlags(int i) {
            if (!this.messages.isEmpty() && this.positions.isEmpty()) {
                calculate();
            }
            for (int i2 = 0; i2 < this.messages.size(); i2++) {
                MessageObject messageObject = this.messages.get(i2);
                GroupedMessagePosition groupedMessagePosition = this.positions.get(messageObject);
                if (groupedMessagePosition != null && (groupedMessagePosition.flags & i) == i) {
                    return messageObject;
                }
            }
            return null;
        }

        public static class TransitionParams {
            public boolean backgroundChangeBounds;
            public int bottom;
            public float captionEnterProgress = 1.0f;
            public ChatMessageCell cell;
            public boolean drawBackgroundForDeletedItems;
            public boolean drawCaptionLayout;
            public boolean isNewGroup;
            public int left;
            public float offsetBottom;
            public float offsetLeft;
            public float offsetRight;
            public float offsetTop;
            public boolean pinnedBotton;
            public boolean pinnedTop;
            public int right;
            public int top;

            public void reset() {
                this.captionEnterProgress = 1.0f;
                this.offsetBottom = 0.0f;
                this.offsetTop = 0.0f;
                this.offsetRight = 0.0f;
                this.offsetLeft = 0.0f;
                this.backgroundChangeBounds = false;
            }
        }

        public boolean contains(int i) {
            if (this.messages == null) {
                return false;
            }
            for (int i2 = 0; i2 < this.messages.size(); i2++) {
                MessageObject messageObject = this.messages.get(i2);
                if (messageObject != null && messageObject.getId() == i) {
                    return true;
                }
            }
            return false;
        }
    }

    public int getLastLineWidth() {
        RichMessageLayout richMessageLayout = this.richLayout;
        if (richMessageLayout != null) {
            return richMessageLayout.getLastLineWidth();
        }
        return this.lastLineWidth;
    }

    public MessageObject(int i, TL_stories.StoryItem storyItem) {
        this.type = MediaDataController.MAX_STYLE_RUNS_COUNT;
        this.forceSeekTo = -1.0f;
        this.actionDeleteGroupEventId = -1L;
        this.overrideLinkColor = -1;
        this.overrideLinkEmoji = -1L;
        this.messageTrimmedToHighlightCut = true;
        this.topicIconDrawable = new Drawable[1];
        this.spoiledLoginCode = false;
        this.translated = false;
        this.summarized = false;
        this.currentAccount = i;
        this.storyItem = storyItem;
        if (storyItem != null) {
            TLRPC.TL_message tL_message = new TLRPC.TL_message();
            this.messageOwner = tL_message;
            tL_message.f1271id = storyItem.messageId;
            tL_message.realId = storyItem.f1454id;
            tL_message.date = storyItem.date;
            tL_message.dialog_id = storyItem.dialogId;
            tL_message.message = storyItem.caption;
            tL_message.entities = storyItem.entities;
            tL_message.media = storyItem.media;
            tL_message.attachPath = storyItem.attachPath;
        }
        this.photoThumbs = new ArrayList<>();
        this.photoThumbs2 = new ArrayList<>();
    }

    public MessageObject(int i, TLRPC.Message message, String str, String str2, String str3, boolean z, boolean z2, boolean z3, boolean z4) {
        this.type = MediaDataController.MAX_STYLE_RUNS_COUNT;
        this.forceSeekTo = -1.0f;
        this.actionDeleteGroupEventId = -1L;
        this.overrideLinkColor = -1;
        this.overrideLinkEmoji = -1L;
        this.messageTrimmedToHighlightCut = true;
        this.topicIconDrawable = new Drawable[1];
        this.spoiledLoginCode = false;
        this.translated = false;
        this.summarized = false;
        this.localType = z ? 2 : 1;
        this.currentAccount = i;
        this.localName = str2;
        this.localUserName = str3;
        this.messageText = str;
        this.messageOwner = message;
        this.localChannel = z2;
        this.localSupergroup = z3;
        this.localEdit = z4;
    }

    public MessageObject(int i, TLRPC.Message message, AbstractMap<Long, TLRPC.User> abstractMap, boolean z, boolean z2) {
        this(i, message, abstractMap, (AbstractMap<Long, TLRPC.Chat>) null, z, z2);
    }

    public MessageObject(int i, TLRPC.Message message, LongSparseArray<TLRPC.User> longSparseArray, boolean z, boolean z2) {
        this(i, message, longSparseArray, (LongSparseArray<TLRPC.Chat>) null, z, z2);
    }

    public MessageObject(int i, TLRPC.Message message, boolean z, boolean z2) {
        this(i, message, null, null, null, null, null, z, z2, 0L);
    }

    public MessageObject(int i, TLRPC.Message message, MessageObject messageObject, boolean z, boolean z2) {
        this(i, message, messageObject, null, null, null, null, z, z2, 0L);
    }

    public MessageObject(int i, TLRPC.Message message, AbstractMap<Long, TLRPC.User> abstractMap, AbstractMap<Long, TLRPC.Chat> abstractMap2, boolean z, boolean z2) {
        this(i, message, abstractMap, abstractMap2, z, z2, 0L);
    }

    public MessageObject(int i, TLRPC.Message message, LongSparseArray<TLRPC.User> longSparseArray, LongSparseArray<TLRPC.Chat> longSparseArray2, boolean z, boolean z2) {
        this(i, message, null, null, null, longSparseArray, longSparseArray2, z, z2, 0L, false, false, false);
    }

    public MessageObject(int i, TLRPC.Message message, LongSparseArray<TLRPC.User> longSparseArray, LongSparseArray<TLRPC.Chat> longSparseArray2, boolean z, boolean z2, boolean z3) {
        this(i, message, null, null, null, longSparseArray, longSparseArray2, z, z2, 0L, false, false, z3);
    }

    public MessageObject(int i, TLRPC.Message message, AbstractMap<Long, TLRPC.User> abstractMap, AbstractMap<Long, TLRPC.Chat> abstractMap2, boolean z, boolean z2, long j) {
        this(i, message, null, abstractMap, abstractMap2, null, null, z, z2, j);
    }

    public MessageObject(int i, TLRPC.Message message, MessageObject messageObject, AbstractMap<Long, TLRPC.User> abstractMap, AbstractMap<Long, TLRPC.Chat> abstractMap2, LongSparseArray<TLRPC.User> longSparseArray, LongSparseArray<TLRPC.Chat> longSparseArray2, boolean z, boolean z2, long j) {
        this(i, message, messageObject, abstractMap, abstractMap2, longSparseArray, longSparseArray2, z, z2, j, false, false, false);
    }

    public MessageObject(int i, TLRPC.Message message, MessageObject messageObject, AbstractMap<Long, TLRPC.User> abstractMap, AbstractMap<Long, TLRPC.Chat> abstractMap2, LongSparseArray<TLRPC.User> longSparseArray, LongSparseArray<TLRPC.Chat> longSparseArray2, boolean z, boolean z2, long j, boolean z3, boolean z4, boolean z5) {
        this(i, message, messageObject, abstractMap, abstractMap2, longSparseArray, longSparseArray2, z, z2, j, z3, z4, z5, 0);
    }

    public MessageObject(int i, TLRPC.Message message, MessageObject messageObject, AbstractMap<Long, TLRPC.User> abstractMap, AbstractMap<Long, TLRPC.Chat> abstractMap2, LongSparseArray<TLRPC.User> longSparseArray, LongSparseArray<TLRPC.Chat> longSparseArray2, boolean z, boolean z2, long j, boolean z3, boolean z4, boolean z5, int i2) {
        AbstractMap<Long, TLRPC.User> abstractMap3;
        AbstractMap<Long, TLRPC.Chat> abstractMap4;
        LongSparseArray<TLRPC.User> longSparseArray3;
        LongSparseArray<TLRPC.Chat> longSparseArray4;
        TextPaint textPaint;
        this.type = MediaDataController.MAX_STYLE_RUNS_COUNT;
        this.forceSeekTo = -1.0f;
        this.actionDeleteGroupEventId = -1L;
        this.overrideLinkColor = -1;
        this.overrideLinkEmoji = -1L;
        this.messageTrimmedToHighlightCut = true;
        this.topicIconDrawable = new Drawable[1];
        this.spoiledLoginCode = false;
        this.translated = false;
        this.summarized = false;
        Theme.createCommonMessageResources();
        this.isRepostPreview = z3;
        this.isRepostVideoPreview = z4;
        this.isSaved = z5 || getDialogId(message) == UserConfig.getInstance(i).getClientUserId();
        this.searchType = i2;
        this.currentAccount = i;
        this.messageOwner = message;
        this.replyMessageObject = messageObject;
        this.eventId = j;
        this.wasUnread = !message.out && message.unread;
        TLRPC.Message message2 = message.replyMessage;
        if (message2 != null) {
            abstractMap3 = abstractMap;
            abstractMap4 = abstractMap2;
            longSparseArray3 = longSparseArray;
            longSparseArray4 = longSparseArray2;
            this.replyMessageObject = new MessageObject(i, message2, null, abstractMap3, abstractMap4, longSparseArray3, longSparseArray4, false, z2, j);
        } else {
            abstractMap3 = abstractMap;
            abstractMap4 = abstractMap2;
            longSparseArray3 = longSparseArray;
            longSparseArray4 = longSparseArray2;
        }
        updateMessageText(abstractMap3, abstractMap4, longSparseArray3, longSparseArray4);
        setType();
        if (z) {
            updateTranslation(false);
        }
        measureInlineBotButtons();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(((long) this.messageOwner.date) * 1000);
        int i3 = gregorianCalendar.get(6);
        int i4 = gregorianCalendar.get(1);
        int i5 = gregorianCalendar.get(2);
        this.dateKey = String.format("%d_%02d_%02d", Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i3));
        this.dateKeyInt = (i5 * XCallback.PRIORITY_HIGHEST) + i4 + (i3 * DurationKt.NANOS_IN_MILLIS);
        this.monthKey = String.format("%d_%02d", Integer.valueOf(i4), Integer.valueOf(i5));
        createMessageSendInfo();
        generateCaption();
        if (z) {
            if (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaGame) {
                textPaint = Theme.chat_msgGameTextPaint;
            } else {
                textPaint = Theme.chat_msgTextPaint;
            }
            int[] iArr = allowsBigEmoji() ? new int[1] : null;
            CharSequence charSequenceReplaceEmoji = Emoji.replaceEmoji(this.messageText, textPaint.getFontMetricsInt(), false, iArr);
            this.messageText = charSequenceReplaceEmoji;
            Spannable spannableReplaceAnimatedEmoji = replaceAnimatedEmoji(charSequenceReplaceEmoji, textPaint.getFontMetricsInt());
            this.messageText = spannableReplaceAnimatedEmoji;
            if (iArr != null && iArr[0] > 1) {
                replaceEmojiToLottieFrame(spannableReplaceAnimatedEmoji, iArr);
            }
            checkEmojiOnly(iArr);
            checkBigAnimatedEmoji();
            setType();
            createPathThumb();
        }
        this.layoutCreated = z;
        generateThumbs(false);
        if (z2) {
            checkMediaExistance();
        }
    }

    public void checkBigAnimatedEmoji() {
        AnimatedEmojiSpan[] animatedEmojiSpanArr;
        int i;
        this.emojiAnimatedSticker = null;
        this.emojiAnimatedStickerId = null;
        if (this.emojiOnlyCount == 1 && !(getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && !(getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaInvoice) && ((getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaEmpty) || getMedia(this.messageOwner) == null)) {
            TLRPC.Message message = this.messageOwner;
            if (message.grouped_id == 0) {
                if (message.entities.isEmpty()) {
                    CharSequence charSequenceSubSequence = this.messageText;
                    int iIndexOf = TextUtils.indexOf(charSequenceSubSequence, "🏻");
                    if (iIndexOf >= 0) {
                        this.emojiAnimatedStickerColor = "_c1";
                        charSequenceSubSequence = charSequenceSubSequence.subSequence(0, iIndexOf);
                    } else {
                        iIndexOf = TextUtils.indexOf(charSequenceSubSequence, "🏼");
                        if (iIndexOf >= 0) {
                            this.emojiAnimatedStickerColor = "_c2";
                            charSequenceSubSequence = charSequenceSubSequence.subSequence(0, iIndexOf);
                        } else {
                            iIndexOf = TextUtils.indexOf(charSequenceSubSequence, "🏽");
                            if (iIndexOf >= 0) {
                                this.emojiAnimatedStickerColor = "_c3";
                                charSequenceSubSequence = charSequenceSubSequence.subSequence(0, iIndexOf);
                            } else {
                                iIndexOf = TextUtils.indexOf(charSequenceSubSequence, "🏾");
                                if (iIndexOf >= 0) {
                                    this.emojiAnimatedStickerColor = "_c4";
                                    charSequenceSubSequence = charSequenceSubSequence.subSequence(0, iIndexOf);
                                } else {
                                    iIndexOf = TextUtils.indexOf(charSequenceSubSequence, "🏿");
                                    if (iIndexOf >= 0) {
                                        this.emojiAnimatedStickerColor = "_c5";
                                        charSequenceSubSequence = charSequenceSubSequence.subSequence(0, iIndexOf);
                                    } else {
                                        this.emojiAnimatedStickerColor = _UrlKt.FRAGMENT_ENCODE_SET;
                                    }
                                }
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(this.emojiAnimatedStickerColor) && (i = iIndexOf + 2) < this.messageText.length()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(charSequenceSubSequence.toString());
                        CharSequence charSequence = this.messageText;
                        sb.append((Object) charSequence.subSequence(i, charSequence.length()));
                        charSequenceSubSequence = sb.toString();
                    }
                    if (TextUtils.isEmpty(this.emojiAnimatedStickerColor) || EmojiData.emojiColoredMap.contains(charSequenceSubSequence.toString())) {
                        this.emojiAnimatedSticker = MediaDataController.getInstance(this.currentAccount).getEmojiAnimatedSticker(charSequenceSubSequence);
                    }
                } else if (this.messageOwner.entities.size() == 1 && (this.messageOwner.entities.get(0) instanceof TLRPC.TL_messageEntityCustomEmoji)) {
                    try {
                        Long lValueOf = Long.valueOf(((TLRPC.TL_messageEntityCustomEmoji) this.messageOwner.entities.get(0)).document_id);
                        this.emojiAnimatedStickerId = lValueOf;
                        TLRPC.Document documentFindDocument = AnimatedEmojiDrawable.findDocument(this.currentAccount, lValueOf.longValue());
                        this.emojiAnimatedSticker = documentFindDocument;
                        if (documentFindDocument == null) {
                            CharSequence charSequence2 = this.messageText;
                            if ((charSequence2 instanceof Spanned) && (animatedEmojiSpanArr = (AnimatedEmojiSpan[]) ((Spanned) charSequence2).getSpans(0, charSequence2.length(), AnimatedEmojiSpan.class)) != null && animatedEmojiSpanArr.length == 1) {
                                this.emojiAnimatedSticker = animatedEmojiSpanArr[0].document;
                            }
                        }
                    } catch (Exception unused) {
                    }
                }
            }
        }
        if (this.emojiAnimatedSticker == null && this.emojiAnimatedStickerId == null) {
            generateLayout(null);
            return;
        }
        if (isSticker()) {
            this.type = 13;
        } else if (isAnimatedSticker()) {
            this.type = 15;
        } else {
            this.type = MediaDataController.MAX_STYLE_RUNS_COUNT;
        }
    }

    private void createPathThumb() {
        TLRPC.Document document = getDocument();
        if (document == null) {
            return;
        }
        this.pathThumb = DocumentObject.getSvgThumb(document, Theme.key_chat_serviceBackground, 1.0f);
    }

    public void createStrippedThumb() {
        if (this.photoThumbs != null) {
            if ((canCreateStripedThubms() || hasExtendedMediaPreview()) && this.strippedThumb == null) {
                try {
                    String str = "b";
                    if (isRoundVideo()) {
                        str = "br";
                    }
                    int size = this.photoThumbs.size();
                    for (int i = 0; i < size; i++) {
                        TLRPC.PhotoSize photoSize = this.photoThumbs.get(i);
                        if (photoSize instanceof TLRPC.TL_photoStrippedSize) {
                            this.strippedThumb = new BitmapDrawable(ApplicationLoader.applicationContext.getResources(), ImageLoader.getStrippedPhotoBitmap(photoSize.bytes, str));
                            return;
                        }
                    }
                } catch (Throwable th) {
                    FileLog.m1048e(th);
                }
            }
        }
    }

    private void createDateArray(int i, TLRPC.TL_channelAdminLogEvent tL_channelAdminLogEvent, ArrayList<MessageObject> arrayList, HashMap<String, ArrayList<MessageObject>> map, boolean z) {
        if (map.get(this.dateKey) == null) {
            map.put(this.dateKey, new ArrayList<>());
            TLRPC.TL_message tL_message = new TLRPC.TL_message();
            tL_message.message = LocaleController.formatDateChat(tL_channelAdminLogEvent.date);
            tL_message.f1271id = 0;
            tL_message.date = tL_channelAdminLogEvent.date;
            MessageObject messageObject = new MessageObject(i, tL_message, false, false);
            messageObject.type = 10;
            messageObject.contentType = 1;
            messageObject.isDateObject = true;
            if (z) {
                arrayList.add(0, messageObject);
            } else {
                arrayList.add(messageObject);
            }
        }
    }

    private void checkEmojiOnly(int[] iArr) {
        checkEmojiOnly(iArr == null ? null : Integer.valueOf(iArr[0]));
    }

    private void checkEmojiOnly(Integer num) {
        TextPaint textPaint;
        if (num != null && num.intValue() >= 1 && this.messageOwner != null && !hasNonEmojiEntities()) {
            CharSequence charSequence = this.messageText;
            Emoji.EmojiSpan[] emojiSpanArr = (Emoji.EmojiSpan[]) ((Spannable) charSequence).getSpans(0, charSequence.length(), Emoji.EmojiSpan.class);
            CharSequence charSequence2 = this.messageText;
            AnimatedEmojiSpan[] animatedEmojiSpanArr = (AnimatedEmojiSpan[]) ((Spannable) charSequence2).getSpans(0, charSequence2.length(), AnimatedEmojiSpan.class);
            this.emojiOnlyCount = Math.max(num.intValue(), (emojiSpanArr == null ? 0 : emojiSpanArr.length) + (animatedEmojiSpanArr == null ? 0 : animatedEmojiSpanArr.length));
            this.totalAnimatedEmojiCount = animatedEmojiSpanArr == null ? 0 : animatedEmojiSpanArr.length;
            this.animatedEmojiCount = 0;
            if (animatedEmojiSpanArr != null) {
                for (AnimatedEmojiSpan animatedEmojiSpan : animatedEmojiSpanArr) {
                    if (!animatedEmojiSpan.standard) {
                        this.animatedEmojiCount++;
                    }
                }
            }
            int i = this.emojiOnlyCount;
            boolean z = (i - (emojiSpanArr == null ? 0 : emojiSpanArr.length)) - (animatedEmojiSpanArr == null ? 0 : animatedEmojiSpanArr.length) > 0;
            this.hasUnwrappedEmoji = z;
            if (z) {
                if (animatedEmojiSpanArr == null || animatedEmojiSpanArr.length <= 0) {
                    return;
                }
                for (int i2 = 0; i2 < animatedEmojiSpanArr.length; i2++) {
                    animatedEmojiSpanArr[i2].replaceFontMetrics(Theme.chat_msgTextPaint.getFontMetricsInt(), (int) (Theme.chat_msgTextPaint.getTextSize() + AndroidUtilities.m1036dp(4.0f)), -1);
                    animatedEmojiSpanArr[i2].full = false;
                }
                return;
            }
            int i3 = this.animatedEmojiCount;
            boolean z2 = i == i3;
            int i4 = 2;
            switch (Math.max(i, i3)) {
                case 0:
                case 1:
                case 2:
                    TextPaint[] textPaintArr = Theme.chat_msgTextPaintEmoji;
                    textPaint = z2 ? textPaintArr[0] : textPaintArr[2];
                    i4 = 1;
                    break;
                case 3:
                    TextPaint[] textPaintArr2 = Theme.chat_msgTextPaintEmoji;
                    textPaint = z2 ? textPaintArr2[1] : textPaintArr2[3];
                    i4 = 1;
                    break;
                case 4:
                    TextPaint[] textPaintArr3 = Theme.chat_msgTextPaintEmoji;
                    textPaint = z2 ? textPaintArr3[2] : textPaintArr3[4];
                    i4 = 1;
                    break;
                case 5:
                    TextPaint[] textPaintArr4 = Theme.chat_msgTextPaintEmoji;
                    textPaint = z2 ? textPaintArr4[3] : textPaintArr4[5];
                    break;
                case 6:
                    TextPaint[] textPaintArr5 = Theme.chat_msgTextPaintEmoji;
                    textPaint = z2 ? textPaintArr5[4] : textPaintArr5[5];
                    break;
                default:
                    int i5 = this.emojiOnlyCount > 9 ? 0 : -1;
                    textPaint = Theme.chat_msgTextPaintEmoji[5];
                    i4 = i5;
                    break;
            }
            int textSize = (int) (textPaint.getTextSize() + AndroidUtilities.m1036dp(4.0f));
            if (emojiSpanArr != null && emojiSpanArr.length > 0) {
                for (Emoji.EmojiSpan emojiSpan : emojiSpanArr) {
                    emojiSpan.replaceFontMetrics(textPaint.getFontMetricsInt(), textSize);
                }
            }
            if (animatedEmojiSpanArr == null || animatedEmojiSpanArr.length <= 0) {
                return;
            }
            for (AnimatedEmojiSpan animatedEmojiSpan2 : animatedEmojiSpanArr) {
                animatedEmojiSpan2.replaceFontMetrics(textPaint.getFontMetricsInt(), textSize, i4);
                animatedEmojiSpan2.full = true;
            }
            return;
        }
        CharSequence charSequence3 = this.messageText;
        AnimatedEmojiSpan[] animatedEmojiSpanArr2 = (AnimatedEmojiSpan[]) ((Spannable) charSequence3).getSpans(0, charSequence3.length(), AnimatedEmojiSpan.class);
        if (animatedEmojiSpanArr2 != null && animatedEmojiSpanArr2.length > 0) {
            this.totalAnimatedEmojiCount = animatedEmojiSpanArr2.length;
            for (int i6 = 0; i6 < animatedEmojiSpanArr2.length; i6++) {
                animatedEmojiSpanArr2[i6].replaceFontMetrics(Theme.chat_msgTextPaint.getFontMetricsInt(), (int) (Theme.chat_msgTextPaint.getTextSize() + AndroidUtilities.m1036dp(4.0f)), -1);
                animatedEmojiSpanArr2[i6].full = false;
            }
            return;
        }
        this.totalAnimatedEmojiCount = 0;
    }

    public android.text.TextPaint getTextPaint() {
        if (this.emojiOnlyCount >= 1 && this.messageOwner != null && !hasNonEmojiEntities()) {
            int i = this.emojiOnlyCount;
            int i2 = this.animatedEmojiCount;
            boolean z = i == i2;
            switch (Math.max(i, i2)) {
                case 0:
                case 1:
                case 2:
                    TextPaint[] textPaintArr = Theme.chat_msgTextPaintEmoji;
                    return z ? textPaintArr[0] : textPaintArr[2];
                case 3:
                    TextPaint[] textPaintArr2 = Theme.chat_msgTextPaintEmoji;
                    return z ? textPaintArr2[1] : textPaintArr2[3];
                case 4:
                    TextPaint[] textPaintArr3 = Theme.chat_msgTextPaintEmoji;
                    return z ? textPaintArr3[2] : textPaintArr3[4];
                case 5:
                    TextPaint[] textPaintArr4 = Theme.chat_msgTextPaintEmoji;
                    return z ? textPaintArr4[3] : textPaintArr4[5];
                case 6:
                    TextPaint[] textPaintArr5 = Theme.chat_msgTextPaintEmoji;
                    return z ? textPaintArr5[4] : textPaintArr5[5];
                default:
                    return Theme.chat_msgTextPaintEmoji[5];
            }
        }
        return Theme.chat_msgTextPaint;
    }

    /* JADX WARN: Removed duplicated region for block: B:1742:0x0d3c  */
    /* JADX WARN: Removed duplicated region for block: B:2028:0x14a4  */
    /* JADX WARN: Removed duplicated region for block: B:2039:0x14e4  */
    /* JADX WARN: Removed duplicated region for block: B:2350:0x1d10  */
    /* JADX WARN: Removed duplicated region for block: B:2353:0x1d61  */
    /* JADX WARN: Removed duplicated region for block: B:2354:0x1d63  */
    /* JADX WARN: Removed duplicated region for block: B:2356:0x1d66  */
    /* JADX WARN: Removed duplicated region for block: B:2373:0x1df2  */
    /* JADX WARN: Removed duplicated region for block: B:2377:0x1dfe  */
    /* JADX WARN: Removed duplicated region for block: B:2381:0x1e0d  */
    /* JADX WARN: Removed duplicated region for block: B:2382:0x1e12  */
    /* JADX WARN: Removed duplicated region for block: B:2385:0x1e1f  */
    /* JADX WARN: Removed duplicated region for block: B:2388:0x1e2d  */
    /* JADX WARN: Removed duplicated region for block: B:2389:0x1e30  */
    /* JADX WARN: Removed duplicated region for block: B:2392:0x1e38  */
    /* JADX WARN: Removed duplicated region for block: B:2393:0x1e3c  */
    /* JADX WARN: Removed duplicated region for block: B:2401:0x1e70  */
    /* JADX WARN: Removed duplicated region for block: B:2415:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MessageObject(int r36, org.telegram.tgnet.TLRPC.TL_channelAdminLogEvent r37, java.util.ArrayList<org.telegram.messenger.MessageObject> r38, java.util.HashMap<java.lang.String, java.util.ArrayList<org.telegram.messenger.MessageObject>> r39, org.telegram.tgnet.TLRPC.Chat r40, int[] r41, boolean r42) {
        /*
            Method dump skipped, instruction units count: 7818
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.<init>(int, org.telegram.tgnet.TLRPC$TL_channelAdminLogEvent, java.util.ArrayList, java.util.HashMap, org.telegram.tgnet.TLRPC$Chat, int[], boolean):void");
    }

    public void spoilLoginCode() {
        TLRPC.Message message;
        if (this.spoiledLoginCode || this.messageText == null || (message = this.messageOwner) == null || message.entities == null) {
            return;
        }
        TLRPC.Peer peer = message.from_id;
        if (peer instanceof TLRPC.TL_peerUser) {
            long j = peer.user_id;
            if (j == 777000 || j == UserObject.VERIFY) {
                if (loginCodePattern == null) {
                    loginCodePattern = Pattern.compile("[\\d\\-]{5,8}");
                }
                try {
                    Matcher matcher = loginCodePattern.matcher(this.messageText);
                    if (matcher.find()) {
                        TLRPC.TL_messageEntitySpoiler tL_messageEntitySpoiler = new TLRPC.TL_messageEntitySpoiler();
                        tL_messageEntitySpoiler.offset = matcher.start();
                        tL_messageEntitySpoiler.length = matcher.end() - tL_messageEntitySpoiler.offset;
                        this.messageOwner.entities.add(tL_messageEntitySpoiler);
                    }
                } catch (Exception e) {
                    FileLog.m1048e(e);
                }
                this.spoiledLoginCode = true;
            }
        }
    }

    public boolean didSpoilLoginCode() {
        return this.spoiledLoginCode;
    }

    private CharSequence getStringFrom(TLRPC.ChatReactions chatReactions) {
        if (chatReactions instanceof TLRPC.TL_chatReactionsAll) {
            return LocaleController.getString(C2797R.string.AllReactions);
        }
        if (chatReactions instanceof TLRPC.TL_chatReactionsSome) {
            TLRPC.TL_chatReactionsSome tL_chatReactionsSome = (TLRPC.TL_chatReactionsSome) chatReactions;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            for (int i = 0; i < tL_chatReactionsSome.reactions.size(); i++) {
                if (i != 0) {
                    spannableStringBuilder.append((CharSequence) " ");
                }
                spannableStringBuilder.append(Emoji.replaceEmoji(ReactionsUtils.reactionToCharSequence(tL_chatReactionsSome.reactions.get(i)), null, false));
            }
            return spannableStringBuilder;
        }
        return LocaleController.getString(C2797R.string.NoReactions);
    }

    private String getUsernamesString(ArrayList<String> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            return LocaleController.getString(C2797R.string.UsernameEmpty).toLowerCase();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayList.size(); i++) {
            sb.append("@");
            sb.append(arrayList.get(i));
            if (i < arrayList.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private String getUserName(TLObject tLObject, ArrayList<TLRPC.MessageEntity> arrayList, int i) {
        String name;
        String publicUsername;
        long j;
        String str;
        long j2;
        if (tLObject == null) {
            name = _UrlKt.FRAGMENT_ENCODE_SET;
            str = null;
            j2 = 0;
        } else {
            if (tLObject instanceof TLRPC.User) {
                TLRPC.User user = (TLRPC.User) tLObject;
                if (user.deleted) {
                    name = LocaleController.getString(C2797R.string.HiddenName);
                } else {
                    name = ContactsController.formatName(user.first_name, user.last_name);
                }
                publicUsername = UserObject.getPublicUsername(user);
                j = user.f1407id;
            } else {
                TLRPC.Chat chat = (TLRPC.Chat) tLObject;
                name = chat.title;
                publicUsername = ChatObject.getPublicUsername(chat);
                j = -chat.f1245id;
            }
            str = publicUsername;
            j2 = j;
        }
        if (i >= 0) {
            TLRPC.TL_messageEntityMentionName tL_messageEntityMentionName = new TLRPC.TL_messageEntityMentionName();
            tL_messageEntityMentionName.user_id = j2;
            tL_messageEntityMentionName.offset = i;
            tL_messageEntityMentionName.length = name.length();
            arrayList.add(tL_messageEntityMentionName);
        }
        if (TextUtils.isEmpty(str)) {
            return name;
        }
        if (i >= 0) {
            TLRPC.TL_messageEntityMentionName tL_messageEntityMentionName2 = new TLRPC.TL_messageEntityMentionName();
            tL_messageEntityMentionName2.user_id = j2;
            tL_messageEntityMentionName2.offset = i + name.length() + 2;
            tL_messageEntityMentionName2.length = str.length() + 1;
            arrayList.add(tL_messageEntityMentionName2);
        }
        return String.format("%1$s (@%2$s)", name, str);
    }

    public boolean updateTranslation() {
        return updateTranslation(false);
    }

    public boolean updateTranslation(boolean z) {
        TLRPC.Message message = this.messageOwner;
        if (message != null && LocaleUtils.parseCustomEmojis(message.message, message.entities, message.f1271id) && !z) {
            z = true;
        }
        MessageObject messageObject = this.replyMessageObject;
        boolean z2 = (messageObject == null || messageObject == this || !messageObject.updateTranslation(z)) ? false : true;
        TranslateController translateController = MessagesController.getInstance(this.currentAccount).getTranslateController();
        TLRPC.Message message2 = this.messageOwner;
        TLRPC.TL_textWithEntities tL_textWithEntities = null;
        TLRPC.TL_textWithEntities tL_textWithEntities2 = message2 != null ? message2.voiceTranscriptionOpen ? message2.translatedVoiceTranscription : message2.translatedText : null;
        TLRPC.TL_textWithEntities tL_textWithEntities3 = (message2 == null || !message2.summarizedOpen) ? null : message2.summaryText;
        if (message2 != null && message2.summarizedOpen) {
            tL_textWithEntities = message2.translatedSummaryText;
        }
        if (tL_textWithEntities != null && message2 != null && message2.summarizedOpen && TranslateController.isSummarizable(this) && TranslateController.isTranslatable(this) && translateController.isTranslatingDialog(getDialogId()) && !translateController.isTranslateDialogHidden(getDialogId()) && TextUtils.equals(translateController.getDialogTranslateTo(getDialogId()), this.messageOwner.translatedSummaryLanguage)) {
            if (this.summarized && this.translated) {
                return z2;
            }
            this.summarized = true;
            this.translated = true;
            applyNewText(tL_textWithEntities.text);
            generateCaption();
            return true;
        }
        TLRPC.Message message3 = this.messageOwner;
        if (message3 != null && message3.summarizedOpen && TranslateController.isSummarizable(this) && tL_textWithEntities3 != null) {
            if (this.summarized && !this.translated) {
                return z2;
            }
            this.summarized = true;
            this.translated = false;
            applyNewText(tL_textWithEntities3.text);
            generateCaption();
            return true;
        }
        if (this.messageOwner != null && TranslateController.isTranslatable(this) && translateController.isTranslatingDialog(getDialogId()) && !translateController.isTranslateDialogHidden(getDialogId()) && ((tL_textWithEntities2 != null || this.messageOwner.translatedPoll != null) && TextUtils.equals(translateController.getDialogTranslateTo(getDialogId()), this.messageOwner.translatedToLanguage))) {
            if (this.translated && !this.summarized) {
                return z2;
            }
            this.translated = true;
            this.summarized = false;
            if (tL_textWithEntities2 != null) {
                applyNewText(tL_textWithEntities2.text);
                generateCaption();
            }
            return true;
        }
        TLRPC.Message message4 = this.messageOwner;
        if (message4 == null || !(z || this.translated || this.summarized)) {
            return z2;
        }
        this.translated = false;
        this.summarized = false;
        applyNewText(message4.message);
        generateCaption();
        return true;
    }

    public void applyNewText() {
        this.translated = false;
        this.summarized = false;
        applyNewText(this.messageOwner.message);
    }

    public void applyNewText(CharSequence charSequence) {
        TextPaint textPaint;
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        TLRPC.User user = isFromUser() ? MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.messageOwner.from_id.user_id)) : null;
        this.messageText = charSequence;
        ArrayList<TLRPC.MessageEntity> entities = getEntities();
        if (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaGame) {
            textPaint = Theme.chat_msgGameTextPaint;
        } else {
            textPaint = Theme.chat_msgTextPaint;
        }
        int[] iArr = allowsBigEmoji() ? new int[1] : null;
        CharSequence charSequenceReplaceEmoji = Emoji.replaceEmoji(this.messageText, textPaint.getFontMetricsInt(), false, iArr);
        this.messageText = charSequenceReplaceEmoji;
        Spannable spannableReplaceAnimatedEmoji = replaceAnimatedEmoji(charSequenceReplaceEmoji, entities, textPaint.getFontMetricsInt());
        this.messageText = spannableReplaceAnimatedEmoji;
        if (iArr != null && iArr[0] > 1) {
            replaceEmojiToLottieFrame(spannableReplaceAnimatedEmoji, iArr);
        }
        checkEmojiOnly(iArr);
        generateLayout(user);
        setType();
    }

    private boolean allowsBigEmoji() {
        TLRPC.Peer peer;
        if (!SharedConfig.allowBigEmoji) {
            return false;
        }
        TLRPC.Message message = this.messageOwner;
        if (message != null && (peer = message.peer_id) != null && (peer.channel_id != 0 || peer.chat_id != 0)) {
            MessagesController messagesController = MessagesController.getInstance(this.currentAccount);
            TLRPC.Peer peer2 = this.messageOwner.peer_id;
            long j = peer2.channel_id;
            if (j == 0) {
                j = peer2.chat_id;
            }
            TLRPC.Chat chat = messagesController.getChat(Long.valueOf(j));
            if ((chat == null || !chat.gigagroup) && ChatObject.isActionBanned(chat, 8) && !ChatObject.hasAdminRights(chat)) {
                return false;
            }
        }
        return true;
    }

    public void generateGameMessageText(TLRPC.User user) {
        if (user == null && isFromUser()) {
            user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.messageOwner.from_id.user_id));
        }
        MessageObject messageObject = this.replyMessageObject;
        TLRPC.TL_game tL_game = (messageObject == null || getMedia(messageObject) == null || getMedia(this.replyMessageObject).game == null) ? null : getMedia(this.replyMessageObject).game;
        if (tL_game == null) {
            if (user == null || user.f1407id != UserConfig.getInstance(this.currentAccount).getClientUserId()) {
                this.messageText = replaceWithLink(LocaleController.formatString("ActionUserScored", C2797R.string.ActionUserScored, LocaleController.formatPluralString("Points", this.messageOwner.action.score, new Object[0])), "un1", user);
                return;
            } else {
                this.messageText = LocaleController.formatString("ActionYouScored", C2797R.string.ActionYouScored, LocaleController.formatPluralString("Points", this.messageOwner.action.score, new Object[0]));
                return;
            }
        }
        if (user == null || user.f1407id != UserConfig.getInstance(this.currentAccount).getClientUserId()) {
            this.messageText = replaceWithLink(LocaleController.formatString("ActionUserScoredInGame", C2797R.string.ActionUserScoredInGame, LocaleController.formatPluralString("Points", this.messageOwner.action.score, new Object[0])), "un1", user);
        } else {
            this.messageText = LocaleController.formatString("ActionYouScoredInGame", C2797R.string.ActionYouScoredInGame, LocaleController.formatPluralString("Points", this.messageOwner.action.score, new Object[0]));
        }
        this.messageText = replaceWithLink(this.messageText, "un2", tL_game);
    }

    public boolean hasValidReplyMessageObject() {
        MessageObject messageObject;
        TLRPC.MessageReplyHeader messageReplyHeader;
        TLRPC.Message message = this.messageOwner;
        if ((message == null || (messageReplyHeader = message.reply_to) == null || !messageReplyHeader.forum_topic || messageReplyHeader.reply_to_msg_id != messageReplyHeader.reply_to_top_id) && (messageObject = this.replyMessageObject) != null) {
            TLRPC.Message message2 = messageObject.messageOwner;
            if (!(message2 instanceof TLRPC.TL_messageEmpty)) {
                TLRPC.MessageAction messageAction = message2.action;
                if (!(messageAction instanceof TLRPC.TL_messageActionHistoryClear) && !(messageAction instanceof TLRPC.TL_messageActionTopicCreate)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void generatePaymentSentMessageText(TLRPC.User user, boolean z) {
        String currencyString;
        if (user == null) {
            user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(getDialogId()));
        }
        String firstName = user != null ? UserObject.getFirstName(user) : _UrlKt.FRAGMENT_ENCODE_SET;
        try {
            if ("XTR".equals(this.messageOwner.action.currency)) {
                currencyString = "XTR " + this.messageOwner.action.total_amount;
            } else {
                LocaleController localeController = LocaleController.getInstance();
                TLRPC.MessageAction messageAction = this.messageOwner.action;
                currencyString = localeController.formatCurrencyString(messageAction.total_amount, messageAction.currency);
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
            currencyString = "<error>";
        }
        MessageObject messageObject = this.replyMessageObject;
        if (messageObject != null && (getMedia(messageObject) instanceof TLRPC.TL_messageMediaInvoice)) {
            TLRPC.MessageAction messageAction2 = this.messageOwner.action;
            if (messageAction2.subscription_until_date != 0) {
                MessageObject messageObject2 = this.replyMessageObject;
                if (z) {
                    this.messageText = LocaleController.formatString(C2797R.string.PaymentSuccessfullyPaidMeSubscription, firstName, currencyString, getMedia(messageObject2).title, LocaleController.formatDateTime(this.messageOwner.action.subscription_until_date, false));
                } else {
                    this.messageText = LocaleController.formatString(C2797R.string.PaymentSuccessfullyPaidSubscription, currencyString, firstName, getMedia(messageObject2).title, LocaleController.formatDateTime(this.messageOwner.action.subscription_until_date, false));
                }
            } else if (messageAction2.recurring_init && !z) {
                this.messageText = LocaleController.formatString(C2797R.string.PaymentSuccessfullyPaidRecurrent, currencyString, firstName, getMedia(this.replyMessageObject).title);
            } else {
                this.messageText = LocaleController.formatString(C2797R.string.PaymentSuccessfullyPaid, currencyString, firstName, getMedia(this.replyMessageObject).title);
            }
        } else {
            TLRPC.MessageAction messageAction3 = this.messageOwner.action;
            int i = messageAction3.subscription_until_date;
            if (i != 0) {
                if (z) {
                    this.messageText = LocaleController.formatString(C2797R.string.PaymentSuccessfullyPaidMeNoItemSubscription, firstName, currencyString, LocaleController.formatDateTime(i, false));
                } else {
                    this.messageText = LocaleController.formatString(C2797R.string.PaymentSuccessfullyPaidSubscriptionNoItem, currencyString, firstName, LocaleController.formatDateTime(i, false));
                }
            } else if (messageAction3.recurring_init && !z) {
                this.messageText = LocaleController.formatString(C2797R.string.PaymentSuccessfullyPaidNoItemRecurrent, currencyString, firstName);
            } else {
                this.messageText = LocaleController.formatString(C2797R.string.PaymentSuccessfullyPaidNoItem, currencyString, firstName);
            }
        }
        this.messageText = StarsIntroActivity.replaceStars(this.messageText);
    }

    public void generatePinMessageText(TLRPC.User user, TLRPC.Chat chat) {
        boolean z;
        if (user == null && chat == null) {
            user = user;
            if (isFromUser()) {
                user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.messageOwner.from_id.user_id));
            }
            if (user == null) {
                TLRPC.Peer peer = this.messageOwner.peer_id;
                if (peer instanceof TLRPC.TL_peerChannel) {
                    chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(this.messageOwner.peer_id.channel_id));
                } else if (peer instanceof TLRPC.TL_peerChat) {
                    chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(this.messageOwner.peer_id.chat_id));
                }
            }
        }
        MessageObject messageObject = this.replyMessageObject;
        if (messageObject != null) {
            TLRPC.Message message = messageObject.messageOwner;
            if (!(message instanceof TLRPC.TL_messageEmpty) && !(message.action instanceof TLRPC.TL_messageActionHistoryClear)) {
                if (messageObject.isMusic()) {
                    String string = LocaleController.getString(C2797R.string.ActionPinnedMusic);
                    TLObject tLObject = user;
                    if (user == null) {
                        tLObject = chat;
                    }
                    this.messageText = replaceWithLink(string, "un1", tLObject);
                    return;
                }
                if (this.replyMessageObject.isVideo()) {
                    String string2 = LocaleController.getString(C2797R.string.ActionPinnedVideo);
                    TLObject tLObject2 = user;
                    if (user == null) {
                        tLObject2 = chat;
                    }
                    this.messageText = replaceWithLink(string2, "un1", tLObject2);
                    return;
                }
                if (this.replyMessageObject.isGif()) {
                    String string3 = LocaleController.getString(C2797R.string.ActionPinnedGif);
                    TLObject tLObject3 = user;
                    if (user == null) {
                        tLObject3 = chat;
                    }
                    this.messageText = replaceWithLink(string3, "un1", tLObject3);
                    return;
                }
                if (this.replyMessageObject.isVoice()) {
                    String string4 = LocaleController.getString(C2797R.string.ActionPinnedVoice);
                    TLObject tLObject4 = user;
                    if (user == null) {
                        tLObject4 = chat;
                    }
                    this.messageText = replaceWithLink(string4, "un1", tLObject4);
                    return;
                }
                if (this.replyMessageObject.isRoundVideo()) {
                    String string5 = LocaleController.getString(C2797R.string.ActionPinnedRound);
                    TLObject tLObject5 = user;
                    if (user == null) {
                        tLObject5 = chat;
                    }
                    this.messageText = replaceWithLink(string5, "un1", tLObject5);
                    return;
                }
                if ((this.replyMessageObject.isSticker() || this.replyMessageObject.isAnimatedSticker()) && !this.replyMessageObject.isAnimatedEmoji()) {
                    String string6 = LocaleController.getString(C2797R.string.ActionPinnedSticker);
                    TLObject tLObject6 = user;
                    if (user == null) {
                        tLObject6 = chat;
                    }
                    this.messageText = replaceWithLink(string6, "un1", tLObject6);
                    return;
                }
                if (getMedia(this.replyMessageObject) instanceof TLRPC.TL_messageMediaDocument) {
                    String string7 = LocaleController.getString(C2797R.string.ActionPinnedFile);
                    TLObject tLObject7 = user;
                    if (user == null) {
                        tLObject7 = chat;
                    }
                    this.messageText = replaceWithLink(string7, "un1", tLObject7);
                    return;
                }
                if (getMedia(this.replyMessageObject) instanceof TLRPC.TL_messageMediaGeo) {
                    String string8 = LocaleController.getString(C2797R.string.ActionPinnedGeo);
                    TLObject tLObject8 = user;
                    if (user == null) {
                        tLObject8 = chat;
                    }
                    this.messageText = replaceWithLink(string8, "un1", tLObject8);
                    return;
                }
                if (getMedia(this.replyMessageObject) instanceof TLRPC.TL_messageMediaGeoLive) {
                    String string9 = LocaleController.getString(C2797R.string.ActionPinnedGeoLive);
                    TLObject tLObject9 = user;
                    if (user == null) {
                        tLObject9 = chat;
                    }
                    this.messageText = replaceWithLink(string9, "un1", tLObject9);
                    return;
                }
                if (getMedia(this.replyMessageObject) instanceof TLRPC.TL_messageMediaContact) {
                    String string10 = LocaleController.getString(C2797R.string.ActionPinnedContact);
                    TLObject tLObject10 = user;
                    if (user == null) {
                        tLObject10 = chat;
                    }
                    this.messageText = replaceWithLink(string10, "un1", tLObject10);
                    return;
                }
                boolean z2 = getMedia(this.replyMessageObject) instanceof TLRPC.TL_messageMediaPoll;
                MessageObject messageObject2 = this.replyMessageObject;
                if (z2) {
                    if (((TLRPC.TL_messageMediaPoll) getMedia(messageObject2)).poll.quiz) {
                        String string11 = LocaleController.getString(C2797R.string.ActionPinnedQuiz);
                        TLObject tLObject11 = user;
                        if (user == null) {
                            tLObject11 = chat;
                        }
                        this.messageText = replaceWithLink(string11, "un1", tLObject11);
                        return;
                    }
                    String string12 = LocaleController.getString(C2797R.string.ActionPinnedPoll);
                    TLObject tLObject12 = user;
                    if (user == null) {
                        tLObject12 = chat;
                    }
                    this.messageText = replaceWithLink(string12, "un1", tLObject12);
                    return;
                }
                if (getMedia(messageObject2) instanceof TLRPC.TL_messageMediaPhoto) {
                    String string13 = LocaleController.getString(C2797R.string.ActionPinnedPhoto);
                    TLObject tLObject13 = user;
                    if (user == null) {
                        tLObject13 = chat;
                    }
                    this.messageText = replaceWithLink(string13, "un1", tLObject13);
                    return;
                }
                boolean z3 = getMedia(this.replyMessageObject) instanceof TLRPC.TL_messageMediaPaidMedia;
                MessageObject messageObject3 = this.replyMessageObject;
                if (z3) {
                    this.messageText = LocaleController.formatPluralString("NotificationPinnedPaidMedia", (int) ((TLRPC.TL_messageMediaPaidMedia) getMedia(messageObject3)).stars_amount, chat != null ? chat.title : UserObject.getUserName(user));
                    return;
                }
                boolean z4 = getMedia(messageObject3) instanceof TLRPC.TL_messageMediaGame;
                MessageObject messageObject4 = this.replyMessageObject;
                if (z4) {
                    String string14 = LocaleController.formatString("ActionPinnedGame", C2797R.string.ActionPinnedGame, "🎮 " + getMedia(messageObject4).game.title);
                    TLObject tLObject14 = user;
                    if (user == null) {
                        tLObject14 = chat;
                    }
                    CharSequence charSequenceReplaceWithLink = replaceWithLink(string14, "un1", tLObject14);
                    this.messageText = charSequenceReplaceWithLink;
                    this.messageText = Emoji.replaceEmoji(charSequenceReplaceWithLink, Theme.chat_msgTextPaint.getFontMetricsInt(), false);
                    return;
                }
                CharSequence charSequence = messageObject4.messageText;
                if (charSequence != null && charSequence.length() > 0) {
                    CharSequence charSequenceCloneSpans = AnimatedEmojiSpan.cloneSpans(this.replyMessageObject.messageText);
                    if (charSequenceCloneSpans.length() > 20) {
                        charSequenceCloneSpans = charSequenceCloneSpans.subSequence(0, 20);
                        z = true;
                    } else {
                        z = false;
                    }
                    CharSequence charSequenceReplaceEmoji = Emoji.replaceEmoji(charSequenceCloneSpans, Theme.chat_msgTextPaint.getFontMetricsInt(), true);
                    MessageObject messageObject5 = this.replyMessageObject;
                    if (messageObject5 != null && messageObject5.messageOwner != null) {
                        charSequenceReplaceEmoji = messageObject5.replaceAnimatedEmoji(charSequenceReplaceEmoji, Theme.chat_msgTextPaint.getFontMetricsInt());
                    }
                    MediaDataController.addTextStyleRuns(this.replyMessageObject, (Spannable) charSequenceReplaceEmoji);
                    if (z) {
                        if (charSequenceReplaceEmoji instanceof SpannableStringBuilder) {
                            ((SpannableStringBuilder) charSequenceReplaceEmoji).append((CharSequence) "...");
                        } else if (charSequenceReplaceEmoji != null) {
                            charSequenceReplaceEmoji = new SpannableStringBuilder(charSequenceReplaceEmoji).append((CharSequence) "...");
                        }
                    }
                    SpannableStringBuilder spannable = AndroidUtilities.formatSpannable(LocaleController.getString(C2797R.string.ActionPinnedText), charSequenceReplaceEmoji);
                    TLObject tLObject15 = user;
                    if (user == null) {
                        tLObject15 = chat;
                    }
                    this.messageText = replaceWithLink(spannable, "un1", tLObject15);
                    return;
                }
                String string15 = LocaleController.getString(C2797R.string.ActionPinnedNoText);
                TLObject tLObject16 = user;
                if (user == null) {
                    tLObject16 = chat;
                }
                this.messageText = replaceWithLink(string15, "un1", tLObject16);
                return;
            }
        }
        String string16 = LocaleController.getString(C2797R.string.ActionPinnedNoText);
        TLObject tLObject17 = user;
        if (user == null) {
            tLObject17 = chat;
        }
        this.messageText = replaceWithLink(string16, "un1", tLObject17);
    }

    public static void updateReactions(TLRPC.Message message, TLRPC.TL_messageReactions tL_messageReactions) {
        if (message == null || tL_messageReactions == null) {
            return;
        }
        TLRPC.TL_messageReactions tL_messageReactions2 = message.reactions;
        if (tL_messageReactions2 != null) {
            int size = tL_messageReactions2.results.size();
            boolean z = false;
            for (int i = 0; i < size; i++) {
                TLRPC.ReactionCount reactionCount = message.reactions.results.get(i);
                int size2 = tL_messageReactions.results.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    TLRPC.ReactionCount reactionCount2 = tL_messageReactions.results.get(i2);
                    if (ReactionsLayoutInBubble.equalsTLReaction(reactionCount.reaction, reactionCount2.reaction)) {
                        if (!z && tL_messageReactions.min && reactionCount.chosen) {
                            reactionCount2.chosen = true;
                            z = true;
                        }
                        reactionCount2.lastDrawnPosition = reactionCount.lastDrawnPosition;
                    }
                }
                if (reactionCount.chosen) {
                    z = true;
                }
            }
        }
        message.reactions = tL_messageReactions;
        message.flags |= 1048576;
    }

    public boolean hasReactions() {
        TLRPC.TL_messageReactions tL_messageReactions = this.messageOwner.reactions;
        return (tL_messageReactions == null || tL_messageReactions.results.isEmpty()) ? false : true;
    }

    public boolean hasReaction(ReactionsLayoutInBubble.VisibleReaction visibleReaction) {
        if (hasReactions() && visibleReaction != null) {
            for (int i = 0; i < this.messageOwner.reactions.results.size(); i++) {
                if (visibleReaction.isSame(this.messageOwner.reactions.results.get(i).reaction)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasChosenReaction(ReactionsLayoutInBubble.VisibleReaction visibleReaction) {
        if (hasReactions() && visibleReaction != null) {
            for (int i = 0; i < this.messageOwner.reactions.results.size(); i++) {
                TLRPC.ReactionCount reactionCount = this.messageOwner.reactions.results.get(i);
                if (visibleReaction.isSame(reactionCount.reaction)) {
                    return reactionCount.chosen;
                }
            }
        }
        return false;
    }

    public static void updatePollResults(TLRPC.TL_messageMediaPoll tL_messageMediaPoll, TLRPC.PollResults pollResults) {
        TLRPC.Poll poll;
        ArrayList<TLRPC.PollAnswerVoters> arrayList;
        ArrayList arrayList2;
        byte[] bArr;
        ArrayList<TLRPC.PollAnswerVoters> arrayList3;
        if (tL_messageMediaPoll == null || pollResults == null) {
            return;
        }
        int i = pollResults.flags & 2;
        boolean z = pollResults.min;
        if (i != 0) {
            if (!z || (arrayList3 = tL_messageMediaPoll.results.results) == null) {
                arrayList2 = null;
                bArr = null;
            } else {
                int size = arrayList3.size();
                arrayList2 = null;
                bArr = null;
                for (int i2 = 0; i2 < size; i2++) {
                    TLRPC.PollAnswerVoters pollAnswerVoters = tL_messageMediaPoll.results.results.get(i2);
                    if (pollAnswerVoters.chosen) {
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                        }
                        arrayList2.add(pollAnswerVoters.option);
                    }
                    if (pollAnswerVoters.correct) {
                        bArr = pollAnswerVoters.option;
                    }
                }
            }
            TLRPC.PollResults pollResults2 = tL_messageMediaPoll.results;
            ArrayList<TLRPC.PollAnswerVoters> arrayList4 = pollResults.results;
            pollResults2.results = arrayList4;
            if (arrayList2 != null || bArr != null) {
                int size2 = arrayList4.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    TLRPC.PollAnswerVoters pollAnswerVoters2 = tL_messageMediaPoll.results.results.get(i3);
                    if (arrayList2 != null) {
                        int size3 = arrayList2.size();
                        int i4 = 0;
                        while (true) {
                            if (i4 >= size3) {
                                break;
                            }
                            if (Arrays.equals(pollAnswerVoters2.option, (byte[]) arrayList2.get(i4))) {
                                pollAnswerVoters2.chosen = true;
                                arrayList2.remove(i4);
                                break;
                            }
                            i4++;
                        }
                        if (arrayList2.isEmpty()) {
                            arrayList2 = null;
                        }
                    }
                    if (bArr != null && Arrays.equals(pollAnswerVoters2.option, bArr)) {
                        pollAnswerVoters2.correct = true;
                        bArr = null;
                    }
                    if (arrayList2 == null && bArr == null) {
                        break;
                    }
                }
            }
            TLRPC.PollResults pollResults3 = tL_messageMediaPoll.results;
            pollResults3.flags = 2 | pollResults3.flags;
        } else if (!z && (poll = tL_messageMediaPoll.poll) != null && poll.hide_results_until_close && ((arrayList = pollResults.results) == null || arrayList.isEmpty())) {
            tL_messageMediaPoll.results.results = new ArrayList<>();
            TLRPC.PollResults pollResults4 = tL_messageMediaPoll.results;
            pollResults4.flags = BitwiseUtils.setFlag(pollResults4.flags, 2, false);
        }
        if ((pollResults.flags & 4) != 0) {
            TLRPC.PollResults pollResults5 = tL_messageMediaPoll.results;
            pollResults5.total_voters = pollResults.total_voters;
            pollResults5.flags |= 4;
        }
        if ((pollResults.flags & 8) != 0) {
            TLRPC.PollResults pollResults6 = tL_messageMediaPoll.results;
            pollResults6.recent_voters = pollResults.recent_voters;
            pollResults6.flags |= 8;
        }
        if ((pollResults.flags & 16) != 0) {
            TLRPC.PollResults pollResults7 = tL_messageMediaPoll.results;
            pollResults7.solution = pollResults.solution;
            pollResults7.solution_entities = pollResults.solution_entities;
            pollResults7.flags |= 16;
        }
        if (pollResults.min) {
            return;
        }
        TLRPC.PollResults pollResults8 = tL_messageMediaPoll.results;
        pollResults8.has_unread_votes = pollResults.has_unread_votes;
        pollResults8.can_view_stats = pollResults.can_view_stats;
    }

    public void loadAnimatedEmojiDocument() {
        if (this.emojiAnimatedSticker != null || this.emojiAnimatedStickerId == null || this.emojiAnimatedStickerLoading) {
            return;
        }
        this.emojiAnimatedStickerLoading = true;
        AnimatedEmojiDrawable.getDocumentFetcher(this.currentAccount).fetchDocument(this.emojiAnimatedStickerId.longValue(), new AnimatedEmojiDrawable.ReceivedDocument() { // from class: org.telegram.messenger.MessageObject$$ExternalSyntheticLambda2
            @Override // org.telegram.ui.Components.AnimatedEmojiDrawable.ReceivedDocument
            public final void run(TLRPC.Document document) {
                this.f$0.lambda$loadAnimatedEmojiDocument$1(document);
            }
        });
    }

    public /* synthetic */ void lambda$loadAnimatedEmojiDocument$1(final TLRPC.Document document) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessageObject$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadAnimatedEmojiDocument$0(document);
            }
        });
    }

    public /* synthetic */ void lambda$loadAnimatedEmojiDocument$0(TLRPC.Document document) {
        this.emojiAnimatedSticker = document;
        NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.animatedEmojiDocumentLoaded, this);
    }

    public boolean isPollClosed() {
        if (this.type != 17) {
            return false;
        }
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        if (media instanceof TLRPC.TL_messageMediaPoll) {
            return ((TLRPC.TL_messageMediaPoll) media).poll.closed;
        }
        return false;
    }

    public boolean isQuiz() {
        if (this.type != 17) {
            return false;
        }
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        if (media instanceof TLRPC.TL_messageMediaPoll) {
            return ((TLRPC.TL_messageMediaPoll) media).poll.quiz;
        }
        return false;
    }

    public boolean isPublicPoll() {
        if (this.type != 17) {
            return false;
        }
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        if (media instanceof TLRPC.TL_messageMediaPoll) {
            return ((TLRPC.TL_messageMediaPoll) media).poll.public_voters;
        }
        return false;
    }

    public boolean isPoll() {
        return this.type == 17 && (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaPoll);
    }

    public boolean isTodo() {
        return this.type == 17 && (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaToDo);
    }

    public boolean canCompleteTodo() {
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        if ((media instanceof TLRPC.TL_messageMediaToDo) && !isForwarded()) {
            return isOutOwner() || ((TLRPC.TL_messageMediaToDo) media).todo.others_can_complete;
        }
        return false;
    }

    public boolean canAppendToTodo() {
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        if (!(media instanceof TLRPC.TL_messageMediaToDo) || isForwarded()) {
            return false;
        }
        TLRPC.TL_messageMediaToDo tL_messageMediaToDo = (TLRPC.TL_messageMediaToDo) media;
        if (tL_messageMediaToDo.todo.list.size() >= MessagesController.getInstance(this.currentAccount).todoItemsMax) {
            return false;
        }
        if (isOutOwner()) {
            return true;
        }
        TLRPC.TodoList todoList = tL_messageMediaToDo.todo;
        return todoList.others_can_complete && todoList.others_can_append;
    }

    public boolean canUnvote() {
        if (this.type != 17) {
            return false;
        }
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        if (media instanceof TLRPC.TL_messageMediaPoll) {
            return canUnvote((TLRPC.TL_messageMediaPoll) media);
        }
        return false;
    }

    public static boolean canUnvote(TLRPC.TL_messageMediaPoll tL_messageMediaPoll) {
        TLRPC.PollResults pollResults;
        if (tL_messageMediaPoll != null && (pollResults = tL_messageMediaPoll.results) != null && !pollResults.results.isEmpty() && !tL_messageMediaPoll.poll.revoting_disabled) {
            int size = tL_messageMediaPoll.results.results.size();
            for (int i = 0; i < size; i++) {
                if (tL_messageMediaPoll.results.results.get(i).chosen) {
                    return true;
                }
            }
        }
        return false;
    }

    public static TLRPC.PollAnswerVoters getPollResult(TLRPC.TL_messageMediaPoll tL_messageMediaPoll, byte[] bArr) {
        TLRPC.PollResults pollResults;
        if (tL_messageMediaPoll != null && (pollResults = tL_messageMediaPoll.results) != null && !pollResults.results.isEmpty()) {
            int size = tL_messageMediaPoll.results.results.size();
            for (int i = 0; i < size; i++) {
                TLRPC.PollAnswerVoters pollAnswerVoters = tL_messageMediaPoll.results.results.get(i);
                if (Arrays.equals(pollAnswerVoters.option, bArr)) {
                    return pollAnswerVoters;
                }
            }
        }
        return null;
    }

    public static boolean canShowVotersList(TLRPC.TL_messageMediaPoll tL_messageMediaPoll) {
        TLRPC.PollResults pollResults;
        if (tL_messageMediaPoll != null && (pollResults = tL_messageMediaPoll.results) != null && !pollResults.results.isEmpty()) {
            TLRPC.Poll poll = tL_messageMediaPoll.poll;
            if (poll.public_voters) {
                if (poll.closed || poll.creator) {
                    return true;
                }
                if (isVoted(tL_messageMediaPoll) && !tL_messageMediaPoll.poll.hide_results_until_close) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isVoted(TLRPC.TL_messageMediaPoll tL_messageMediaPoll) {
        TLRPC.PollResults pollResults;
        if (tL_messageMediaPoll != null && (pollResults = tL_messageMediaPoll.results) != null && !pollResults.results.isEmpty()) {
            int size = tL_messageMediaPoll.results.results.size();
            for (int i = 0; i < size; i++) {
                if (tL_messageMediaPoll.results.results.get(i).chosen) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isVotedButResultsHiddenUntilClose() {
        if (this.type != 17) {
            return false;
        }
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        if (media instanceof TLRPC.TL_messageMediaPoll) {
            return isVotedButResultsHiddenUntilClose((TLRPC.TL_messageMediaPoll) media);
        }
        return false;
    }

    public static boolean isVotedButResultsHiddenUntilClose(TLRPC.TL_messageMediaPoll tL_messageMediaPoll) {
        if (tL_messageMediaPoll == null) {
            return false;
        }
        TLRPC.Poll poll = tL_messageMediaPoll.poll;
        return !poll.closed && poll.hide_results_until_close && isVoted(tL_messageMediaPoll) && !isVoteResultsIsNotEmpty(tL_messageMediaPoll);
    }

    public static boolean isVoteResultsIsNotEmpty(TLRPC.TL_messageMediaPoll tL_messageMediaPoll) {
        TLRPC.PollResults pollResults;
        if (tL_messageMediaPoll != null && (pollResults = tL_messageMediaPoll.results) != null && !pollResults.results.isEmpty()) {
            int size = tL_messageMediaPoll.results.results.size();
            for (int i = 0; i < size; i++) {
                if (tL_messageMediaPoll.results.results.get(i).voters > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasVoteResults() {
        if (this.type != 17) {
            return false;
        }
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        if (media instanceof TLRPC.TL_messageMediaPoll) {
            return isVoteResultsIsNotEmpty((TLRPC.TL_messageMediaPoll) media);
        }
        return false;
    }

    public boolean isVoted() {
        if (this.type != 17) {
            return false;
        }
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        if (media instanceof TLRPC.TL_messageMediaPoll) {
            return isVoted((TLRPC.TL_messageMediaPoll) media);
        }
        return false;
    }

    public boolean isSponsored() {
        return this.sponsoredId != null;
    }

    public long getPollId() {
        if (this.type != 17) {
            return 0L;
        }
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        if (media instanceof TLRPC.TL_messageMediaPoll) {
            return ((TLRPC.TL_messageMediaPoll) media).poll.f1279id;
        }
        return 0L;
    }

    private TLRPC.Photo getPhotoWithId(TLRPC.WebPage webPage, long j) {
        if (webPage != null && webPage.cached_page != null) {
            TLRPC.Photo photo = webPage.photo;
            if (photo != null && photo.f1276id == j) {
                return photo;
            }
            for (int i = 0; i < webPage.cached_page.photos.size(); i++) {
                TLRPC.Photo photo2 = webPage.cached_page.photos.get(i);
                if (photo2.f1276id == j) {
                    return photo2;
                }
            }
        }
        return null;
    }

    private TLRPC.Document getDocumentWithId(TLRPC.WebPage webPage, long j) {
        if (webPage != null && webPage.cached_page != null) {
            TLRPC.Document document = webPage.document;
            if (document != null && document.f1253id == j) {
                return document;
            }
            for (int i = 0; i < webPage.cached_page.documents.size(); i++) {
                TLRPC.Document document2 = webPage.cached_page.documents.get(i);
                if (document2.f1253id == j) {
                    return document2;
                }
            }
        }
        return null;
    }

    public boolean isSupergroup() {
        if (this.localSupergroup) {
            return true;
        }
        Boolean bool = this.cachedIsSupergroup;
        if (bool != null) {
            return bool.booleanValue();
        }
        TLRPC.Peer peer = this.messageOwner.peer_id;
        if (peer != null) {
            long j = peer.channel_id;
            if (j != 0) {
                TLRPC.Chat chat = getChat(null, null, j);
                if (chat == null) {
                    return false;
                }
                boolean z = chat.megagroup;
                this.cachedIsSupergroup = Boolean.valueOf(z);
                return z;
            }
        }
        this.cachedIsSupergroup = Boolean.FALSE;
        return false;
    }

    private MessageObject getMessageObjectForBlock(TLRPC.WebPage webPage, TL_iv.PageBlock pageBlock) {
        TLRPC.TL_message tL_message;
        if (pageBlock instanceof TL_iv.pageBlockPhoto) {
            TLRPC.Photo photoWithId = getPhotoWithId(webPage, ((TL_iv.pageBlockPhoto) pageBlock).photo_id);
            if (photoWithId != webPage.photo) {
                tL_message = new TLRPC.TL_message();
                TLRPC.TL_messageMediaPhoto tL_messageMediaPhoto = new TLRPC.TL_messageMediaPhoto();
                tL_message.media = tL_messageMediaPhoto;
                tL_messageMediaPhoto.photo = photoWithId;
                tL_message.message = _UrlKt.FRAGMENT_ENCODE_SET;
                tL_message.realId = getId();
                tL_message.f1271id = Utilities.random.nextInt();
                TLRPC.Message message = this.messageOwner;
                tL_message.date = message.date;
                tL_message.peer_id = message.peer_id;
                tL_message.out = message.out;
                tL_message.from_id = message.from_id;
                return new MessageObject(this.currentAccount, tL_message, false, true);
            }
            return this;
        }
        if (pageBlock instanceof TL_iv.pageBlockVideo) {
            TL_iv.pageBlockVideo pageblockvideo = (TL_iv.pageBlockVideo) pageBlock;
            if (getDocumentWithId(webPage, pageblockvideo.video_id) != webPage.document) {
                TLRPC.TL_message tL_message2 = new TLRPC.TL_message();
                TLRPC.TL_messageMediaDocument tL_messageMediaDocument = new TLRPC.TL_messageMediaDocument();
                tL_message2.media = tL_messageMediaDocument;
                tL_messageMediaDocument.document = getDocumentWithId(webPage, pageblockvideo.video_id);
                tL_message = tL_message2;
            }
            return this;
        }
        tL_message = null;
        tL_message.message = _UrlKt.FRAGMENT_ENCODE_SET;
        tL_message.realId = getId();
        tL_message.f1271id = Utilities.random.nextInt();
        TLRPC.Message message2 = this.messageOwner;
        tL_message.date = message2.date;
        tL_message.peer_id = message2.peer_id;
        tL_message.out = message2.out;
        tL_message.from_id = message2.from_id;
        return new MessageObject(this.currentAccount, tL_message, false, true);
    }

    public ArrayList<MessageObject> getWebPagePhotos(ArrayList<MessageObject> arrayList, ArrayList<TL_iv.PageBlock> arrayList2) {
        TLRPC.WebPage webPage;
        TL_iv.Page page;
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        if (getMedia(this.messageOwner) != null && getMedia(this.messageOwner).webpage != null && (page = (webPage = getMedia(this.messageOwner).webpage).cached_page) != null) {
            if (arrayList2 == null) {
                arrayList2 = page.blocks;
            }
            for (int i = 0; i < arrayList2.size(); i++) {
                TL_iv.PageBlock pageBlock = arrayList2.get(i);
                if (pageBlock instanceof TL_iv.pageBlockSlideshow) {
                    TL_iv.pageBlockSlideshow pageblockslideshow = (TL_iv.pageBlockSlideshow) pageBlock;
                    for (int i2 = 0; i2 < pageblockslideshow.items.size(); i2++) {
                        arrayList.add(getMessageObjectForBlock(webPage, pageblockslideshow.items.get(i2)));
                    }
                } else if (pageBlock instanceof TL_iv.pageBlockCollage) {
                    TL_iv.pageBlockCollage pageblockcollage = (TL_iv.pageBlockCollage) pageBlock;
                    for (int i3 = 0; i3 < pageblockcollage.items.size(); i3++) {
                        arrayList.add(getMessageObjectForBlock(webPage, pageblockcollage.items.get(i3)));
                    }
                }
            }
        }
        return arrayList;
    }

    public void createMessageSendInfo() {
        createMessageSendInfo(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:114:0x0077  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void createMessageSendInfo(boolean r7) {
        /*
            Method dump skipped, instruction units count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.createMessageSendInfo(boolean):void");
    }

    public static boolean isPaidVideo(TLRPC.MessageMedia messageMedia) {
        return (messageMedia instanceof TLRPC.TL_messageMediaPaidMedia) && messageMedia.extended_media.size() == 1 && isExtendedVideo(messageMedia.extended_media.get(0));
    }

    public static boolean isExtendedVideo(TLRPC.MessageExtendedMedia messageExtendedMedia) {
        if (!(messageExtendedMedia instanceof TLRPC.TL_messageExtendedMedia)) {
            return (messageExtendedMedia instanceof TLRPC.TL_messageExtendedMediaPreview) && (((TLRPC.TL_messageExtendedMediaPreview) messageExtendedMedia).flags & 4) != 0;
        }
        TLRPC.MessageMedia messageMedia = ((TLRPC.TL_messageExtendedMedia) messageExtendedMedia).media;
        return (messageMedia instanceof TLRPC.TL_messageMediaDocument) && isVideoDocument(messageMedia.document);
    }

    public boolean hasSuggestionInlineButtons() {
        TLRPC.SuggestedPost suggestedPost;
        TLRPC.Message message = this.messageOwner;
        boolean z = (message == null || (suggestedPost = message.suggested_post) == null || suggestedPost.rejected || suggestedPost.accepted || isSendError() || isSending()) ? false : true;
        if (z) {
            long clientUserId = UserConfig.getInstance(this.currentAccount).getClientUserId();
            long peerDialogId = DialogObject.getPeerDialogId(this.messageOwner.saved_peer_id);
            long peerDialogId2 = DialogObject.getPeerDialogId(this.messageOwner.from_id);
            boolean z2 = clientUserId == peerDialogId;
            boolean z3 = peerDialogId == peerDialogId2;
            if ((z2 && z3) || (!z2 && !z3)) {
                return false;
            }
        }
        return z;
    }

    public BotInlineKeyboard.Source getInlineBotButtons() {
        return this.inlineKeyboardSource;
    }

    public boolean hasInlineBotButtons() {
        TLRPC.Message message;
        if (this.isRestrictedMessage || this.isRepostPreview || (message = this.messageOwner) == null) {
            return false;
        }
        TLRPC.ReplyMarkup replyMarkup = message.reply_markup;
        return ((replyMarkup instanceof TLRPC.TL_replyInlineMarkup) && !replyMarkup.rows.isEmpty()) || getInlineBotButtons() != null;
    }

    public void measureInlineBotButtons() {
        TLRPC.TL_messageReactions tL_messageReactions;
        CharSequence charSequenceReplaceEmoji;
        if (this.isRestrictedMessage) {
            return;
        }
        this.wantedBotKeyboardWidth = 0;
        this.inlineKeyboardSource = null;
        BotInlineKeyboard.Builder builder = new BotInlineKeyboard.Builder();
        TLRPC.Message message = this.messageOwner;
        if (message != null) {
            TLRPC.ReplyMarkup replyMarkup = message.reply_markup;
            if ((replyMarkup instanceof TLRPC.TL_replyInlineMarkup) && replyMarkup.rows != null) {
                builder.addBotKeyboard((TLRPC.TL_replyInlineMarkup) replyMarkup);
            }
        }
        if (hasSuggestionInlineButtons()) {
            builder.addSeparator();
            builder.addSuggestionKeyboard();
        }
        if (builder.isNotEmpty()) {
            this.inlineKeyboardSource = builder.build();
        }
        BotInlineKeyboard.Source source = this.inlineKeyboardSource;
        if ((source != null && !hasExtendedMedia()) || ((tL_messageReactions = this.messageOwner.reactions) != null && !tL_messageReactions.results.isEmpty())) {
            Theme.createCommonMessageResources();
            StringBuilder sb = this.botButtonsLayout;
            if (sb == null) {
                this.botButtonsLayout = new StringBuilder();
            } else {
                sb.setLength(0);
            }
        }
        if (source == null || hasExtendedMedia()) {
            return;
        }
        for (int i = 0; i < source.getRowsCount(); i++) {
            int columnsCount = source.getColumnsCount(i);
            int iMax = 0;
            for (int i2 = 0; i2 < columnsCount; i2++) {
                BotInlineKeyboard.Button button = source.getButton(i, i2);
                StringBuilder sb2 = this.botButtonsLayout;
                sb2.append(i);
                sb2.append(i2);
                if ((button instanceof BotInlineKeyboard.ButtonBot) && (((BotInlineKeyboard.ButtonBot) button).button instanceof TLRPC.TL_keyboardButtonBuy) && (getMedia(this.messageOwner).flags & 4) != 0) {
                    charSequenceReplaceEmoji = LocaleController.getString(C2797R.string.PaymentReceipt);
                } else {
                    String text = button.getText();
                    if (text == null) {
                        text = _UrlKt.FRAGMENT_ENCODE_SET;
                    }
                    charSequenceReplaceEmoji = Emoji.replaceEmoji(text, Theme.chat_msgBotButtonPaint.getFontMetricsInt(), false);
                }
                StaticLayout staticLayout = new StaticLayout(charSequenceReplaceEmoji, Theme.chat_msgBotButtonPaint, AndroidUtilities.m1036dp(2000.0f), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                if (staticLayout.getLineCount() > 0) {
                    float lineWidth = staticLayout.getLineWidth(0);
                    float lineLeft = staticLayout.getLineLeft(0);
                    if (lineLeft < lineWidth) {
                        lineWidth -= lineLeft;
                    }
                    if (button.getIconRes() != 0) {
                        lineWidth += AndroidUtilities.m1036dp(36.0f);
                    }
                    if (button.getIconEmoji() != 0) {
                        lineWidth += AndroidUtilities.m1036dp(36.0f);
                    }
                    iMax = Math.max(iMax, ((int) Math.ceil(lineWidth)) + AndroidUtilities.m1036dp(4.0f));
                }
            }
            this.wantedBotKeyboardWidth = Math.max(this.wantedBotKeyboardWidth, ((iMax + AndroidUtilities.m1036dp(12.0f)) * columnsCount) + (AndroidUtilities.m1036dp(5.0f) * (columnsCount - 1)));
        }
    }

    public boolean isVideoAvatar() {
        TLRPC.Photo photo;
        TLRPC.MessageAction messageAction = this.messageOwner.action;
        return (messageAction == null || (photo = messageAction.photo) == null || photo.video_sizes.isEmpty()) ? false : true;
    }

    public boolean isFcmMessage() {
        return this.localType != 0;
    }

    private TLRPC.User getUser(AbstractMap<Long, TLRPC.User> abstractMap, LongSparseArray<TLRPC.User> longSparseArray, long j) {
        TLRPC.User user;
        if (abstractMap != null) {
            user = abstractMap.get(Long.valueOf(j));
        } else {
            user = longSparseArray != null ? longSparseArray.get(j) : null;
        }
        return user == null ? MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(j)) : user;
    }

    private TLRPC.Chat getChat(AbstractMap<Long, TLRPC.Chat> abstractMap, LongSparseArray<TLRPC.Chat> longSparseArray, long j) {
        TLRPC.Chat chat;
        if (abstractMap != null) {
            chat = abstractMap.get(Long.valueOf(j));
        } else {
            chat = longSparseArray != null ? longSparseArray.get(j) : null;
        }
        return chat == null ? MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(j)) : chat;
    }

    public void updateMessageText() {
        updateMessageText(MessagesController.getInstance(this.currentAccount).getUsers(), MessagesController.getInstance(this.currentAccount).getChats(), null, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:1556:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:1557:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:1560:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:1731:0x04ff  */
    /* JADX WARN: Removed duplicated region for block: B:1850:0x080d  */
    /* JADX WARN: Removed duplicated region for block: B:1875:0x0864  */
    /* JADX WARN: Removed duplicated region for block: B:1913:0x08f2  */
    /* JADX WARN: Removed duplicated region for block: B:2176:0x0ff1  */
    /* JADX WARN: Removed duplicated region for block: B:2202:0x106f  */
    /* JADX WARN: Removed duplicated region for block: B:2219:0x10d3  */
    /* JADX WARN: Removed duplicated region for block: B:2269:0x1208  */
    /* JADX WARN: Removed duplicated region for block: B:2303:0x12bf  */
    /* JADX WARN: Removed duplicated region for block: B:2403:0x154f  */
    /* JADX WARN: Removed duplicated region for block: B:2714:0x1ca9  */
    /* JADX WARN: Removed duplicated region for block: B:2724:0x1cd1  */
    /* JADX WARN: Removed duplicated region for block: B:2861:0x2198  */
    /* JADX WARN: Removed duplicated region for block: B:2878:0x21fb  */
    /* JADX WARN: Removed duplicated region for block: B:2946:0x23a4 A[LOOP:4: B:2928:0x236b->B:2946:0x23a4, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:3046:0x25cb  */
    /* JADX WARN: Removed duplicated region for block: B:3059:0x2614  */
    /* JADX WARN: Removed duplicated region for block: B:3067:0x120b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:3088:0x23a2 A[EDGE_INSN: B:3088:0x23a2->B:2945:0x23a2 BREAK  A[LOOP:4: B:2928:0x236b->B:2946:0x23a4], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateMessageText(java.util.AbstractMap<java.lang.Long, org.telegram.tgnet.TLRPC.User> r32, java.util.AbstractMap<java.lang.Long, org.telegram.tgnet.TLRPC.Chat> r33, androidx.collection.LongSparseArray<org.telegram.tgnet.TLRPC.User> r34, androidx.collection.LongSparseArray<org.telegram.tgnet.TLRPC.Chat> r35) {
        /*
            Method dump skipped, instruction units count: 9758
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.updateMessageText(java.util.AbstractMap, java.util.AbstractMap, androidx.collection.LongSparseArray, androidx.collection.LongSparseArray):void");
    }

    private CharSequence formatTaskTitle(TLRPC.TodoItem todoItem) {
        CharSequence textWithEntities = formatTextWithEntities(todoItem.title, isOutOwner());
        if (!(textWithEntities instanceof Spannable)) {
            textWithEntities = new SpannableStringBuilder(textWithEntities);
        }
        ((Spannable) textWithEntities).setSpan(new URLSpanNoUnderline("task?" + todoItem.f1405id, true), 0, textWithEntities.length(), 33);
        return textWithEntities;
    }

    public static CharSequence formatTextWithEntities(TLRPC.TL_textWithEntities tL_textWithEntities) {
        return formatTextWithEntities(tL_textWithEntities, false);
    }

    public static CharSequence formatTextWithEntities(TLRPC.TL_textWithEntities tL_textWithEntities, boolean z) {
        Theme.createCommonChatResources();
        android.text.TextPaint textPaint = Theme.chat_actionTextPaint;
        if (textPaint == null) {
            textPaint = new android.text.TextPaint(1);
            textPaint.setTypeface(AndroidUtilities.bold());
            textPaint.setTextSize(AndroidUtilities.m1036dp(Math.max(16, SharedConfig.fontSize) - 2));
        }
        return formatTextWithEntities(tL_textWithEntities, z, textPaint);
    }

    public static CharSequence formatTextWithEntities(TLRPC.TL_textWithEntities tL_textWithEntities, boolean z, android.text.TextPaint textPaint) {
        return formatTextWithEntities(tL_textWithEntities, z, false, textPaint);
    }

    public static CharSequence formatTextWithEntities(TLRPC.TL_textWithEntities tL_textWithEntities, boolean z, boolean z2, android.text.TextPaint textPaint) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(tL_textWithEntities.text);
        addEntitiesToText(spannableStringBuilder, tL_textWithEntities.entities, z, false, z2, false);
        return replaceAnimatedEmoji(Emoji.replaceEmoji(spannableStringBuilder, textPaint.getFontMetricsInt(), false), tL_textWithEntities.entities, textPaint.getFontMetricsInt());
    }

    public static CharSequence formatRichMessage(TL_iv.RichMessage richMessage, boolean z) {
        return formatRichMessage(richMessage, z, false, 1024);
    }

    public static CharSequence formatRichMessage(TL_iv.RichMessage richMessage, boolean z, boolean z2, int i) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (richMessage != null) {
            int i2 = 0;
            while (i2 < richMessage.blocks.size()) {
                TL_iv.PageBlock pageBlock = richMessage.blocks.get(i2);
                if (i2 > 0) {
                    spannableStringBuilder.append((CharSequence) "  ");
                }
                TL_iv.RichMessage richMessage2 = richMessage;
                boolean z3 = z;
                boolean z4 = z2;
                int i3 = i;
                formatRichBlock(pageBlock, z3, z4, i3, spannableStringBuilder, richMessage2);
                if (spannableStringBuilder.length() >= i3) {
                    spannableStringBuilder.delete(i3, spannableStringBuilder.length());
                    spannableStringBuilder.append((CharSequence) "…");
                    return spannableStringBuilder;
                }
                i2++;
                z = z3;
                z2 = z4;
                i = i3;
                richMessage = richMessage2;
            }
        }
        return spannableStringBuilder;
    }

    public static CharSequence formatRichBlock(TL_iv.PageBlock pageBlock, boolean z, boolean z2, int i, SpannableStringBuilder spannableStringBuilder, TL_iv.RichMessage richMessage) {
        TLRPC.Document document;
        int i2;
        if ((pageBlock instanceof TL_iv.pageBlockTitle) || (pageBlock instanceof TL_iv.pageBlockHeader) || (pageBlock instanceof TL_iv.pageBlockSubheader) || (pageBlock instanceof TL_iv.pageBlockHeading1) || (pageBlock instanceof TL_iv.pageBlockHeading2) || (pageBlock instanceof TL_iv.pageBlockHeading3) || (pageBlock instanceof TL_iv.pageBlockHeading4) || (pageBlock instanceof TL_iv.pageBlockHeading5) || (pageBlock instanceof TL_iv.pageBlockHeading6) || (pageBlock instanceof TL_iv.pageBlockBlockquote) || (pageBlock instanceof TL_iv.pageBlockPullquote)) {
            formatRichText(pageBlock.text, z, z2, i, spannableStringBuilder, 1);
            return spannableStringBuilder;
        }
        if ((pageBlock instanceof TL_iv.pageBlockParagraph) || (pageBlock instanceof TL_iv.pageBlockFooter) || (pageBlock instanceof TL_iv.pageBlockKicker)) {
            formatRichText(pageBlock.text, z, z2, i, spannableStringBuilder, 0);
            return spannableStringBuilder;
        }
        int i3 = 0;
        if (pageBlock instanceof TL_iv.pageBlockBlockquoteBlocks) {
            TL_iv.pageBlockBlockquoteBlocks pageblockblockquoteblocks = (TL_iv.pageBlockBlockquoteBlocks) pageBlock;
            while (i3 < pageblockblockquoteblocks.blocks.size()) {
                if (i3 > 0) {
                    spannableStringBuilder.append("  ");
                }
                formatRichBlock(pageblockblockquoteblocks.blocks.get(i3), z, z2, i, spannableStringBuilder, richMessage);
                if (spannableStringBuilder.length() >= i) {
                    spannableStringBuilder.delete(i, spannableStringBuilder.length());
                    spannableStringBuilder.append("…");
                    return spannableStringBuilder;
                }
                i3++;
            }
            return spannableStringBuilder;
        }
        if (pageBlock instanceof TL_iv.pageBlockDetails) {
            formatRichText(((TL_iv.pageBlockDetails) pageBlock).title, z, z2, i, spannableStringBuilder, 0);
            return spannableStringBuilder;
        }
        if (pageBlock instanceof TL_iv.pageBlockAuthorDate) {
            formatRichText(((TL_iv.pageBlockAuthorDate) pageBlock).author, z, z2, i, spannableStringBuilder, 0);
            return spannableStringBuilder;
        }
        if (pageBlock instanceof TL_iv.pageBlockMath) {
            spannableStringBuilder.append((CharSequence) LatexInliner.inlineLatex(((TL_iv.pageBlockMath) pageBlock).source));
            return spannableStringBuilder;
        }
        if (pageBlock instanceof TL_iv.pageBlockMap) {
            spannableStringBuilder.append((CharSequence) LocaleController.getString(C2797R.string.Map));
            TL_iv.PageCaption pageCaption = pageBlock.caption;
            if (pageCaption == null || (pageCaption.text instanceof TL_iv.textEmpty)) {
                return spannableStringBuilder;
            }
            spannableStringBuilder.append("  ");
            formatRichText(pageBlock.caption.text, z, z2, i, spannableStringBuilder, 0);
            return spannableStringBuilder;
        }
        if (pageBlock instanceof TL_iv.pageBlockPreformatted) {
            formatRichText(pageBlock.text, z, z2, i, spannableStringBuilder, 4);
            return spannableStringBuilder;
        }
        if (pageBlock instanceof TL_iv.pageBlockList) {
            TL_iv.pageBlockList pageblocklist = (TL_iv.pageBlockList) pageBlock;
            for (int i4 = 0; i4 < pageblocklist.items.size(); i4++) {
                TL_iv.PageListItem pageListItem = pageblocklist.items.get(i4);
                if (pageListItem instanceof TL_iv.TL_pageListItemText) {
                    TL_iv.TL_pageListItemText tL_pageListItemText = (TL_iv.TL_pageListItemText) pageListItem;
                    if (tL_pageListItemText.checkbox) {
                        spannableStringBuilder.append(tL_pageListItemText.checked ? "✅ " : "☑️ ");
                    } else {
                        spannableStringBuilder.append("• ");
                    }
                    i2 = i;
                    formatRichText(tL_pageListItemText.text, z, z2, i2, spannableStringBuilder, 0);
                } else {
                    if (pageListItem instanceof TL_iv.TL_pageListItemBlocks) {
                        TL_iv.TL_pageListItemBlocks tL_pageListItemBlocks = (TL_iv.TL_pageListItemBlocks) pageListItem;
                        if (tL_pageListItemBlocks.checkbox) {
                            spannableStringBuilder.append(tL_pageListItemBlocks.checked ? "✅ " : "☑️ ");
                        } else {
                            spannableStringBuilder.append("• ");
                        }
                        for (int i5 = 0; i5 < tL_pageListItemBlocks.blocks.size(); i5++) {
                            if (i5 > 0) {
                                spannableStringBuilder.append("\n");
                            }
                            formatRichBlock(tL_pageListItemBlocks.blocks.get(i5), z, z2, i, spannableStringBuilder, richMessage);
                            if (spannableStringBuilder.length() >= i) {
                                spannableStringBuilder.delete(i, spannableStringBuilder.length());
                                spannableStringBuilder.append("…");
                                return spannableStringBuilder;
                            }
                        }
                    }
                    i2 = i;
                }
                if (spannableStringBuilder.length() >= i2) {
                    spannableStringBuilder.delete(i2, spannableStringBuilder.length());
                    spannableStringBuilder.append("…");
                    return spannableStringBuilder;
                }
            }
            return spannableStringBuilder;
        }
        int i6 = i;
        if (pageBlock instanceof TL_iv.pageBlockOrderedList) {
            TL_iv.pageBlockOrderedList pageblockorderedlist = (TL_iv.pageBlockOrderedList) pageBlock;
            for (int i7 = 0; i7 < pageblockorderedlist.items.size(); i7++) {
                TL_iv.PageListOrderedItem pageListOrderedItem = pageblockorderedlist.items.get(i7);
                if (pageListOrderedItem instanceof TL_iv.TL_pageListOrderedItemText) {
                    TL_iv.TL_pageListOrderedItemText tL_pageListOrderedItemText = (TL_iv.TL_pageListOrderedItemText) pageListOrderedItem;
                    spannableStringBuilder.append((CharSequence) tL_pageListOrderedItemText.num);
                    spannableStringBuilder.append(". ");
                    if (tL_pageListOrderedItemText.checkbox) {
                        spannableStringBuilder.append(tL_pageListOrderedItemText.checked ? "✅ " : "☑️ ");
                    }
                    formatRichText(tL_pageListOrderedItemText.text, z, z2, i6, spannableStringBuilder, 0);
                } else if (pageListOrderedItem instanceof TL_iv.TL_pageListOrderedItemBlocks) {
                    TL_iv.TL_pageListOrderedItemBlocks tL_pageListOrderedItemBlocks = (TL_iv.TL_pageListOrderedItemBlocks) pageListOrderedItem;
                    spannableStringBuilder.append((CharSequence) tL_pageListOrderedItemBlocks.num);
                    spannableStringBuilder.append(". ");
                    if (tL_pageListOrderedItemBlocks.checkbox) {
                        spannableStringBuilder.append(tL_pageListOrderedItemBlocks.checked ? "✅ " : "☑️ ");
                    }
                    for (int i8 = 0; i8 < tL_pageListOrderedItemBlocks.blocks.size(); i8++) {
                        if (i8 > 0) {
                            spannableStringBuilder.append("\n");
                        }
                        formatRichBlock(tL_pageListOrderedItemBlocks.blocks.get(i8), z, z2, i, spannableStringBuilder, richMessage);
                        if (spannableStringBuilder.length() >= i) {
                            spannableStringBuilder.delete(i, spannableStringBuilder.length());
                            spannableStringBuilder.append("…");
                            return spannableStringBuilder;
                        }
                    }
                }
                i6 = i;
                if (spannableStringBuilder.length() >= i6) {
                    spannableStringBuilder.delete(i6, spannableStringBuilder.length());
                    spannableStringBuilder.append("…");
                    return spannableStringBuilder;
                }
            }
            return spannableStringBuilder;
        }
        if (pageBlock instanceof TL_iv.pageBlockTable) {
            TL_iv.RichText richText = ((TL_iv.pageBlockTable) pageBlock).title;
            if (richText != null && !(richText instanceof TL_iv.textEmpty)) {
                formatRichText(richText, z, z2, i6, spannableStringBuilder, 1);
                return spannableStringBuilder;
            }
            spannableStringBuilder.append((CharSequence) LocaleController.getString(C2797R.string.AccDescrIVTable));
            return spannableStringBuilder;
        }
        if (pageBlock instanceof TL_iv.pageBlockAudio) {
            TL_iv.pageBlockAudio pageblockaudio = (TL_iv.pageBlockAudio) pageBlock;
            while (true) {
                if (i3 >= richMessage.documents.size()) {
                    document = null;
                    break;
                }
                if (richMessage.documents.get(i3).f1253id == pageblockaudio.audio_id) {
                    document = richMessage.documents.get(i3);
                    break;
                }
                i3++;
            }
            if (document == null) {
                return spannableStringBuilder;
            }
            TLRPC.TL_documentAttributeAudio tL_documentAttributeAudio = (TLRPC.TL_documentAttributeAudio) AndroidUtilities.find(document.attributes, TLRPC.TL_documentAttributeAudio.class);
            TLRPC.TL_documentAttributeFilename tL_documentAttributeFilename = (TLRPC.TL_documentAttributeFilename) AndroidUtilities.find(document.attributes, TLRPC.TL_documentAttributeFilename.class);
            if (tL_documentAttributeAudio != null) {
                if (!TextUtils.isEmpty(tL_documentAttributeAudio.title) && !TextUtils.isEmpty(tL_documentAttributeAudio.performer)) {
                    spannableStringBuilder.append("🎵 ").append((CharSequence) tL_documentAttributeAudio.performer).append(" – ").append((CharSequence) tL_documentAttributeAudio.title);
                    return spannableStringBuilder;
                }
                if (!TextUtils.isEmpty(tL_documentAttributeAudio.title)) {
                    spannableStringBuilder.append("🎵 ").append((CharSequence) tL_documentAttributeAudio.title);
                    return spannableStringBuilder;
                }
                if (tL_documentAttributeFilename == null || tL_documentAttributeFilename.file_name == null) {
                    return spannableStringBuilder;
                }
                spannableStringBuilder.append("🎵 ").append((CharSequence) tL_documentAttributeFilename.file_name);
                return spannableStringBuilder;
            }
            if (tL_documentAttributeFilename == null || tL_documentAttributeFilename.file_name == null) {
                return spannableStringBuilder;
            }
            spannableStringBuilder.append("🎵 ").append((CharSequence) tL_documentAttributeFilename.file_name);
            return spannableStringBuilder;
        }
        if (pageBlock instanceof TL_iv.pageBlockCover) {
            formatRichBlock(((TL_iv.pageBlockCover) pageBlock).cover, z, z2, i, spannableStringBuilder, richMessage);
            return spannableStringBuilder;
        }
        if (pageBlock instanceof TL_iv.pageBlockPhoto) {
            spannableStringBuilder.append((CharSequence) LocaleController.getString(C2797R.string.AttachPhoto));
            return spannableStringBuilder;
        }
        if (pageBlock instanceof TL_iv.pageBlockVideo) {
            spannableStringBuilder.append((CharSequence) LocaleController.getString(C2797R.string.AttachVideo));
            return spannableStringBuilder;
        }
        if (pageBlock instanceof TL_iv.pageBlockCollage) {
            spannableStringBuilder.append((CharSequence) LocaleController.getString(C2797R.string.AccDescrCollage));
            return spannableStringBuilder;
        }
        if (pageBlock instanceof TL_iv.pageBlockSlideshow) {
            spannableStringBuilder.append((CharSequence) LocaleController.getString(C2797R.string.AccDescrIVSlideshow));
            return spannableStringBuilder;
        }
        if (!(pageBlock instanceof TL_iv.pageBlockUnsupported)) {
            return spannableStringBuilder;
        }
        spannableStringBuilder.append((CharSequence) LocaleController.getString(C2797R.string.UnsupportedAttachment));
        return spannableStringBuilder;
    }

    public static CharSequence formatRichText(TL_iv.RichText richText, boolean z, boolean z2, int i, SpannableStringBuilder spannableStringBuilder, int i2) {
        if (richText == null) {
            return spannableStringBuilder;
        }
        int length = spannableStringBuilder.length();
        if (richText instanceof TL_iv.textPlain) {
            spannableStringBuilder.append((CharSequence) ((TL_iv.textPlain) richText).text);
        } else if (richText instanceof TL_iv.textBold) {
            formatRichText(richText.text, z, z2, i, spannableStringBuilder, i2 | 1);
        } else if (richText instanceof TL_iv.textItalic) {
            formatRichText(richText.text, z, z2, i, spannableStringBuilder, i2 | 2);
        } else if (richText instanceof TL_iv.textUnderline) {
            formatRichText(richText.text, z, z2, i, spannableStringBuilder, i2 | 16);
        } else if (richText instanceof TL_iv.textStrike) {
            formatRichText(richText.text, z, z2, i, spannableStringBuilder, i2 | 8);
        } else if (richText instanceof TL_iv.textFixed) {
            formatRichText(richText.text, z, z2, i, spannableStringBuilder, i2 | 4);
        } else if (richText instanceof TL_iv.textSpoiler) {
            formatRichText(richText.text, z, z2, i, spannableStringBuilder, i2 | 256);
        } else if (richText instanceof TL_iv.textUrl) {
            formatRichText(richText.text, z, z2, i, spannableStringBuilder, i2);
            if (spannableStringBuilder.length() > length) {
                TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
                textStyleRun.flags = i2;
                spannableStringBuilder.setSpan(new URLSpanBrowser(richText.url, textStyleRun), length, spannableStringBuilder.length(), 33);
            }
        } else if (richText instanceof TL_iv.textEmail) {
            formatRichText(richText.text, z, z2, i, spannableStringBuilder, i2);
            if (spannableStringBuilder.length() > length) {
                TextStyleSpan.TextStyleRun textStyleRun2 = new TextStyleSpan.TextStyleRun();
                textStyleRun2.flags = i2;
                spannableStringBuilder.setSpan(new URLSpanReplacement("mailto:" + richText.email, textStyleRun2), length, spannableStringBuilder.length(), 33);
            }
        } else if (richText instanceof TL_iv.textMath) {
            spannableStringBuilder.append((CharSequence) LatexInliner.inlineLatex(((TL_iv.textMath) richText).source));
        } else if (richText instanceof TL_iv.textPhone) {
            formatRichText(richText.text, z, z2, i, spannableStringBuilder, i2);
            if (spannableStringBuilder.length() > length) {
                TL_iv.textPhone textphone = (TL_iv.textPhone) richText;
                String strStripExceptNumbers = PhoneFormat.stripExceptNumbers(textphone.phone);
                if (textphone.phone.startsWith("+")) {
                    strStripExceptNumbers = "+" + strStripExceptNumbers;
                }
                TextStyleSpan.TextStyleRun textStyleRun3 = new TextStyleSpan.TextStyleRun();
                textStyleRun3.flags = i2;
                spannableStringBuilder.setSpan(new URLSpanNoUnderline("tel:" + strStripExceptNumbers, textStyleRun3), length, spannableStringBuilder.length(), 33);
            }
        } else if (richText instanceof TL_iv.textConcat) {
            ArrayList<TL_iv.RichText> arrayList = ((TL_iv.textConcat) richText).texts;
            int size = arrayList.size();
            int i3 = 0;
            while (i3 < size) {
                int i4 = i3 + 1;
                formatRichText(arrayList.get(i3), z, z2, i, spannableStringBuilder, i2);
                if (spannableStringBuilder.length() >= i) {
                    spannableStringBuilder.delete(i, spannableStringBuilder.length());
                    spannableStringBuilder.append("…");
                    return spannableStringBuilder;
                }
                i3 = i4;
            }
        }
        if (spannableStringBuilder.length() > length && i2 != 0) {
            TextStyleSpan.TextStyleRun textStyleRun4 = new TextStyleSpan.TextStyleRun();
            textStyleRun4.flags = i2;
            spannableStringBuilder.setSpan(new TextStyleSpan(textStyleRun4), length, spannableStringBuilder.length(), 33);
        }
        return spannableStringBuilder;
    }

    public static TLRPC.TL_textWithEntities removeLinks(TLRPC.TL_textWithEntities tL_textWithEntities) {
        TLRPC.TL_textWithEntities tL_textWithEntities2 = new TLRPC.TL_textWithEntities();
        tL_textWithEntities2.text = tL_textWithEntities.text;
        for (int i = 0; i < tL_textWithEntities.entities.size(); i++) {
            TLRPC.MessageEntity messageEntity = tL_textWithEntities.entities.get(i);
            if (!(messageEntity instanceof TLRPC.TL_messageEntityUrl) && !(messageEntity instanceof TLRPC.TL_messageEntityTextUrl)) {
                tL_textWithEntities2.entities.add(messageEntity);
            }
        }
        return tL_textWithEntities2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:301:0x01a3, code lost:
    
        if (r7.ttl_seconds == 0) goto L318;
     */
    /* JADX WARN: Removed duplicated region for block: B:253:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x0112  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.CharSequence getMediaTitle(org.telegram.tgnet.TLRPC.MessageMedia r7) {
        /*
            Method dump skipped, instruction units count: 671
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.getMediaTitle(org.telegram.tgnet.TLRPC$MessageMedia):java.lang.CharSequence");
    }

    public static TLRPC.MessageMedia getMedia(MessageObject messageObject) {
        TLRPC.Message message;
        if (messageObject == null || (message = messageObject.messageOwner) == null) {
            return null;
        }
        TLRPC.MessageMedia messageMedia = messageObject.sponsoredMedia;
        return messageMedia != null ? messageMedia : getMedia(message);
    }

    public static TLRPC.MessageMedia getMedia(TLRPC.Message message) {
        TLRPC.MessageMedia messageMedia = message.media;
        if (messageMedia != null && !(messageMedia instanceof TLRPC.TL_messageMediaPaidMedia) && !messageMedia.extended_media.isEmpty() && (message.media.extended_media.get(0) instanceof TLRPC.TL_messageExtendedMedia)) {
            return ((TLRPC.TL_messageExtendedMedia) message.media.extended_media.get(0)).media;
        }
        return message.media;
    }

    public static <T extends TLRPC.MessageMedia> T getMedia(TLRPC.Message message, Class<T> cls) {
        TLRPC.MessageMedia media = getMedia(message);
        if (cls.isInstance(media)) {
            return cls.cast(media);
        }
        return null;
    }

    public boolean hasRevealedExtendedMedia() {
        TLRPC.MessageMedia messageMedia = this.messageOwner.media;
        return (messageMedia == null || (messageMedia instanceof TLRPC.TL_messageMediaPaidMedia) || messageMedia.extended_media.isEmpty() || !(this.messageOwner.media.extended_media.get(0) instanceof TLRPC.TL_messageExtendedMedia)) ? false : true;
    }

    public boolean hasExtendedMedia() {
        TLRPC.MessageMedia messageMedia = this.messageOwner.media;
        return (messageMedia == null || (messageMedia instanceof TLRPC.TL_messageMediaPaidMedia) || messageMedia.extended_media.isEmpty()) ? false : true;
    }

    public boolean hasPaidMediaPreview() {
        TLRPC.MessageMedia messageMedia = this.messageOwner.media;
        return messageMedia != null && (messageMedia instanceof TLRPC.TL_messageMediaPaidMedia) && !messageMedia.extended_media.isEmpty() && (this.messageOwner.media.extended_media.get(0) instanceof TLRPC.TL_messageExtendedMediaPreview);
    }

    public boolean hasExtendedMediaPreview() {
        TLRPC.MessageMedia messageMedia = this.messageOwner.media;
        return (messageMedia == null || (messageMedia instanceof TLRPC.TL_messageMediaPaidMedia) || messageMedia.extended_media.isEmpty() || !(this.messageOwner.media.extended_media.get(0) instanceof TLRPC.TL_messageExtendedMediaPreview)) ? false : true;
    }

    private boolean hasNonEmojiEntities() {
        String str;
        TLRPC.Message message = this.messageOwner;
        if (message != null && message.entities != null) {
            for (int i = 0; i < this.messageOwner.entities.size(); i++) {
                TLRPC.MessageEntity messageEntity = this.messageOwner.entities.get(i);
                if (!(messageEntity instanceof TLRPC.TL_messageEntityCustomEmoji) && (!(messageEntity instanceof TLRPC.TL_messageEntityTextUrl) || (str = ((TLRPC.TL_messageEntityTextUrl) messageEntity).url) == null || !str.startsWith("tg://emoji?id="))) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:291:0x0099  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setType() {
        /*
            Method dump skipped, instruction units count: 1148
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.setType():void");
    }

    public boolean checkLayout() {
        CharSequence charSequence;
        TextPaint textPaint;
        int i = this.type;
        if ((i == 0 || i == 19 || i == 36) && this.messageOwner.peer_id != null && (charSequence = this.messageText) != null && (charSequence.length() != 0 || this.isBotPendingDraft)) {
            if (this.layoutCreated) {
                int minTabletSide = AndroidUtilities.isTablet() ? AndroidUtilities.getMinTabletSide() : AndroidUtilities.displaySize.x;
                TextPaint textPaint2 = Theme.chat_msgTextPaint;
                float textSize = textPaint2 != null ? textPaint2.getTextSize() : 0.0f;
                if (Math.abs(this.generatedWithMinSize - minTabletSide) > AndroidUtilities.m1036dp(52.0f) || this.generatedWithDensity != AndroidUtilities.density || this.generatedWithFontSize != textSize) {
                    this.layoutCreated = false;
                }
            }
            if (!this.layoutCreated) {
                this.layoutCreated = true;
                TLRPC.User user = isFromUser() ? MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.messageOwner.from_id.user_id)) : null;
                if (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaGame) {
                    textPaint = Theme.chat_msgGameTextPaint;
                } else {
                    textPaint = Theme.chat_msgTextPaint;
                }
                int[] iArr = allowsBigEmoji() ? new int[1] : null;
                CharSequence charSequenceReplaceEmoji = Emoji.replaceEmoji(this.messageText, textPaint.getFontMetricsInt(), false, iArr);
                this.messageText = charSequenceReplaceEmoji;
                Spannable spannableReplaceAnimatedEmoji = replaceAnimatedEmoji(charSequenceReplaceEmoji, textPaint.getFontMetricsInt());
                this.messageText = spannableReplaceAnimatedEmoji;
                if (iArr != null && iArr[0] > 1) {
                    replaceEmojiToLottieFrame(spannableReplaceAnimatedEmoji, iArr);
                }
                checkEmojiOnly(iArr);
                checkBigAnimatedEmoji();
                setType();
                generateLayout(user);
                if (this.caption != null) {
                    this.caption = null;
                    generateCaption();
                }
                return true;
            }
        }
        return false;
    }

    public void resetLayout() {
        this.layoutCreated = false;
    }

    public String getMimeType() {
        TLRPC.Document document = getDocument();
        if (document != null) {
            return document.mime_type;
        }
        boolean z = getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaInvoice;
        TLRPC.Message message = this.messageOwner;
        if (!z) {
            return getMedia(message) instanceof TLRPC.TL_messageMediaPhoto ? "image/jpeg" : (!(getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) || getMedia(this.messageOwner).webpage.photo == null) ? _UrlKt.FRAGMENT_ENCODE_SET : "image/jpeg";
        }
        TLRPC.WebDocument webDocument = ((TLRPC.TL_messageMediaInvoice) getMedia(message)).webPhoto;
        if (webDocument != null) {
            return webDocument.mime_type;
        }
        return _UrlKt.FRAGMENT_ENCODE_SET;
    }

    public boolean canPreviewDocument() {
        return canPreviewDocument(getDocument());
    }

    public static boolean isAnimatedStickerDocument(TLRPC.Document document) {
        return document != null && document.mime_type.equals("video/webm");
    }

    public static boolean isStaticStickerDocument(TLRPC.Document document) {
        return document != null && document.mime_type.equals("image/webp");
    }

    public static boolean isGifDocument(WebFile webFile) {
        if (webFile != null) {
            return webFile.mime_type.equals("image/gif") || isNewGifDocument(webFile);
        }
        return false;
    }

    public static boolean isGifDocument(TLRPC.Document document) {
        return isGifDocument(document, false);
    }

    public static boolean isGifDocument(TLRPC.Document document, boolean z) {
        String str;
        if (document == null || (str = document.mime_type) == null) {
            return false;
        }
        return (str.equals("image/gif") && !z) || isNewGifDocument(document);
    }

    public static boolean isDocumentHasThumb(TLRPC.Document document) {
        if (document != null && !document.thumbs.isEmpty()) {
            int size = document.thumbs.size();
            for (int i = 0; i < size; i++) {
                TLRPC.PhotoSize photoSize = document.thumbs.get(i);
                if (photoSize != null && !(photoSize instanceof TLRPC.TL_photoSizeEmpty) && (!(photoSize.location instanceof TLRPC.TL_fileLocationUnavailable) || photoSize.bytes != null)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canPreviewDocument(TLRPC.Document document) {
        String str;
        if (document != null && (str = document.mime_type) != null) {
            if ((isDocumentHasThumb(document) && (str.equalsIgnoreCase("image/png") || str.equalsIgnoreCase("image/jpg") || str.equalsIgnoreCase("image/jpeg"))) || (Build.VERSION.SDK_INT >= 26 && str.equalsIgnoreCase("image/heic"))) {
                for (int i = 0; i < document.attributes.size(); i++) {
                    TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
                    if (documentAttribute instanceof TLRPC.TL_documentAttributeImageSize) {
                        TLRPC.TL_documentAttributeImageSize tL_documentAttributeImageSize = (TLRPC.TL_documentAttributeImageSize) documentAttribute;
                        return tL_documentAttributeImageSize.f1256w < 6000 && tL_documentAttributeImageSize.f1255h < 6000;
                    }
                }
            } else if (BuildVars.DEBUG_PRIVATE_VERSION) {
                String documentFileName = FileLoader.getDocumentFileName(document);
                if (documentFileName.startsWith("tg_secret_sticker") && documentFileName.endsWith("json")) {
                    return true;
                }
                return documentFileName.endsWith(".svg");
            }
        }
        return false;
    }

    public static boolean isRoundVideoDocument(TLRPC.Document document) {
        if (document != null && "video/mp4".equals(document.mime_type)) {
            boolean z = false;
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < document.attributes.size(); i3++) {
                TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i3);
                if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                    i = documentAttribute.f1256w;
                    i2 = documentAttribute.f1255h;
                    z = documentAttribute.round_message;
                }
            }
            if (z && i <= 1280 && i2 <= 1280) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNewGifDocument(WebFile webFile) {
        if (webFile != null && "video/mp4".equals(webFile.mime_type)) {
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < webFile.attributes.size(); i3++) {
                TLRPC.DocumentAttribute documentAttribute = webFile.attributes.get(i3);
                if (!(documentAttribute instanceof TLRPC.TL_documentAttributeAnimated) && (documentAttribute instanceof TLRPC.TL_documentAttributeVideo)) {
                    i = documentAttribute.f1256w;
                    i2 = documentAttribute.f1255h;
                }
            }
            if (i <= 1280 && i2 <= 1280) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNewGifDocument(TLRPC.Document document) {
        if (document != null && "video/mp4".equals(document.mime_type)) {
            boolean z = false;
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < document.attributes.size(); i3++) {
                TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i3);
                if (documentAttribute instanceof TLRPC.TL_documentAttributeAnimated) {
                    z = true;
                } else if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                    i = documentAttribute.f1256w;
                    i2 = documentAttribute.f1255h;
                }
            }
            if (z && i <= 1280 && i2 <= 1280) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSystemSignUp(MessageObject messageObject) {
        if (messageObject == null) {
            return false;
        }
        TLRPC.Message message = messageObject.messageOwner;
        return (message instanceof TLRPC.TL_messageService) && (((TLRPC.TL_messageService) message).action instanceof TLRPC.TL_messageActionContactSignUp);
    }

    public void generateThumbs(boolean z) {
        ArrayList<TLRPC.PhotoSize> arrayList;
        ArrayList<TLRPC.PhotoSize> arrayList2;
        ArrayList<TLRPC.PhotoSize> arrayList3;
        TL_iv.RichMessage richMessage;
        ArrayList<TLRPC.PhotoSize> arrayList4;
        ArrayList<TLRPC.PhotoSize> arrayList5;
        ArrayList<TLRPC.PhotoSize> arrayList6;
        ArrayList<TLRPC.PhotoSize> arrayList7;
        ArrayList<TLRPC.PhotoSize> arrayList8;
        ArrayList<TLRPC.PhotoSize> arrayList9;
        ArrayList<TLRPC.PhotoSize> arrayList10;
        ArrayList<TLRPC.PhotoSize> arrayList11;
        boolean zHasExtendedMediaPreview = hasExtendedMediaPreview();
        TLRPC.Message message = this.messageOwner;
        if (zHasExtendedMediaPreview) {
            TLRPC.TL_messageExtendedMediaPreview tL_messageExtendedMediaPreview = (TLRPC.TL_messageExtendedMediaPreview) message.media.extended_media.get(0);
            if (!z) {
                this.photoThumbs = new ArrayList<>(Collections.singletonList(tL_messageExtendedMediaPreview.thumb));
            } else {
                updatePhotoSizeLocations(this.photoThumbs, Collections.singletonList(tL_messageExtendedMediaPreview.thumb));
            }
            this.photoThumbsObject = this.messageOwner;
            if (this.strippedThumb == null) {
                createStrippedThumb();
                return;
            }
            return;
        }
        if (message instanceof TLRPC.TL_messageService) {
            TLRPC.MessageAction messageAction = message.action;
            if (messageAction instanceof TLRPC.TL_messageActionChatEditPhoto) {
                TLRPC.Photo photo = messageAction.photo;
                if (!z) {
                    this.photoThumbs = new ArrayList<>(photo.sizes);
                } else {
                    ArrayList<TLRPC.PhotoSize> arrayList12 = this.photoThumbs;
                    if (arrayList12 != null && !arrayList12.isEmpty()) {
                        for (int i = 0; i < this.photoThumbs.size(); i++) {
                            TLRPC.PhotoSize photoSize = this.photoThumbs.get(i);
                            int i2 = 0;
                            while (true) {
                                if (i2 < photo.sizes.size()) {
                                    TLRPC.PhotoSize photoSize2 = photo.sizes.get(i2);
                                    if (!(photoSize2 instanceof TLRPC.TL_photoSizeEmpty) && photoSize2.type.equals(photoSize.type)) {
                                        photoSize.location = photoSize2.location;
                                        break;
                                    }
                                    i2++;
                                }
                            }
                        }
                    }
                }
                if (photo.dc_id != 0 && (arrayList11 = this.photoThumbs) != null) {
                    int size = arrayList11.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        TLRPC.FileLocation fileLocation = this.photoThumbs.get(i3).location;
                        if (fileLocation != null) {
                            fileLocation.dc_id = photo.dc_id;
                            fileLocation.file_reference = photo.file_reference;
                        }
                    }
                }
                this.photoThumbsObject = this.messageOwner.action.photo;
                return;
            }
            return;
        }
        if (this.emojiAnimatedSticker != null || this.emojiAnimatedStickerId != null) {
            if (TextUtils.isEmpty(this.emojiAnimatedStickerColor) && isDocumentHasThumb(this.emojiAnimatedSticker)) {
                if (!z || (arrayList = this.photoThumbs) == null) {
                    ArrayList<TLRPC.PhotoSize> arrayList13 = new ArrayList<>();
                    this.photoThumbs = arrayList13;
                    arrayList13.addAll(this.emojiAnimatedSticker.thumbs);
                } else if (!arrayList.isEmpty()) {
                    updatePhotoSizeLocations(this.photoThumbs, this.emojiAnimatedSticker.thumbs);
                }
                this.photoThumbsObject = this.emojiAnimatedSticker;
                return;
            }
            return;
        }
        if (getMedia(message) != null && !(getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaEmpty)) {
            boolean z2 = getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaPhoto;
            TLRPC.Message message2 = this.messageOwner;
            if (z2) {
                TLRPC.Photo photo2 = getMedia(message2).photo;
                if (!z || ((arrayList10 = this.photoThumbs) != null && arrayList10.size() != photo2.sizes.size())) {
                    this.photoThumbs = new ArrayList<>(photo2.sizes);
                } else {
                    ArrayList<TLRPC.PhotoSize> arrayList14 = this.photoThumbs;
                    if (arrayList14 != null && !arrayList14.isEmpty()) {
                        for (int i4 = 0; i4 < this.photoThumbs.size(); i4++) {
                            TLRPC.PhotoSize photoSize3 = this.photoThumbs.get(i4);
                            if (photoSize3 != null) {
                                int i5 = 0;
                                while (true) {
                                    if (i5 >= photo2.sizes.size()) {
                                        break;
                                    }
                                    TLRPC.PhotoSize photoSize4 = photo2.sizes.get(i5);
                                    if (photoSize4 != null && !(photoSize4 instanceof TLRPC.TL_photoSizeEmpty)) {
                                        if (!photoSize4.type.equals(photoSize3.type)) {
                                            if ("s".equals(photoSize3.type) && (photoSize4 instanceof TLRPC.TL_photoStrippedSize)) {
                                                this.photoThumbs.set(i4, photoSize4);
                                                break;
                                            }
                                        } else {
                                            photoSize3.location = photoSize4.location;
                                            break;
                                        }
                                    }
                                    i5++;
                                }
                            }
                        }
                    }
                }
                this.photoThumbsObject = getMedia(this.messageOwner).photo;
                return;
            }
            boolean z3 = getMedia(message2) instanceof TLRPC.TL_messageMediaDocument;
            TLRPC.Message message3 = this.messageOwner;
            if (z3) {
                TLRPC.Photo photo3 = ((TLRPC.TL_messageMediaDocument) getMedia(message3)).video_cover;
                if (photo3 != null) {
                    if (!z || ((arrayList9 = this.photoThumbs) != null && arrayList9.size() != photo3.sizes.size())) {
                        this.photoThumbs = new ArrayList<>(photo3.sizes);
                    } else {
                        ArrayList<TLRPC.PhotoSize> arrayList15 = this.photoThumbs;
                        if (arrayList15 != null && !arrayList15.isEmpty()) {
                            for (int i6 = 0; i6 < this.photoThumbs.size(); i6++) {
                                TLRPC.PhotoSize photoSize5 = this.photoThumbs.get(i6);
                                if (photoSize5 != null) {
                                    int i7 = 0;
                                    while (true) {
                                        if (i7 >= photo3.sizes.size()) {
                                            break;
                                        }
                                        TLRPC.PhotoSize photoSize6 = photo3.sizes.get(i7);
                                        if (photoSize6 != null && !(photoSize6 instanceof TLRPC.TL_photoSizeEmpty)) {
                                            if (!photoSize6.type.equals(photoSize5.type)) {
                                                if ("s".equals(photoSize5.type) && (photoSize6 instanceof TLRPC.TL_photoStrippedSize)) {
                                                    this.photoThumbs.set(i6, photoSize6);
                                                    break;
                                                }
                                            } else {
                                                photoSize5.location = photoSize6.location;
                                                break;
                                            }
                                        }
                                        i7++;
                                    }
                                }
                            }
                        }
                    }
                    this.photoThumbsObject = photo3;
                    return;
                }
                TLRPC.Document document = getDocument();
                if (isDocumentHasThumb(document)) {
                    if (!z || (arrayList8 = this.photoThumbs) == null) {
                        ArrayList<TLRPC.PhotoSize> arrayList16 = new ArrayList<>();
                        this.photoThumbs = arrayList16;
                        arrayList16.addAll(document.thumbs);
                    } else if (!arrayList8.isEmpty()) {
                        updatePhotoSizeLocations(this.photoThumbs, document.thumbs);
                    }
                    this.photoThumbsObject = document;
                    return;
                }
                return;
            }
            boolean z4 = getMedia(message3) instanceof TLRPC.TL_messageMediaGame;
            TLRPC.Message message4 = this.messageOwner;
            if (z4) {
                TLRPC.Document document2 = getMedia(message4).game.document;
                if (document2 != null && isDocumentHasThumb(document2)) {
                    if (!z) {
                        ArrayList<TLRPC.PhotoSize> arrayList17 = new ArrayList<>();
                        this.photoThumbs = arrayList17;
                        arrayList17.addAll(document2.thumbs);
                    } else {
                        ArrayList<TLRPC.PhotoSize> arrayList18 = this.photoThumbs;
                        if (arrayList18 != null && !arrayList18.isEmpty()) {
                            updatePhotoSizeLocations(this.photoThumbs, document2.thumbs);
                        }
                    }
                    this.photoThumbsObject = document2;
                }
                TLRPC.Photo photo4 = getMedia(this.messageOwner).game.photo;
                if (photo4 != null) {
                    if (!z || (arrayList7 = this.photoThumbs2) == null) {
                        this.photoThumbs2 = new ArrayList<>(photo4.sizes);
                    } else if (!arrayList7.isEmpty()) {
                        updatePhotoSizeLocations(this.photoThumbs2, photo4.sizes);
                    }
                    this.photoThumbsObject2 = photo4;
                }
                if (this.photoThumbs != null || (arrayList6 = this.photoThumbs2) == null) {
                    return;
                }
                this.photoThumbs = arrayList6;
                this.photoThumbs2 = null;
                this.photoThumbsObject = this.photoThumbsObject2;
                this.photoThumbsObject2 = null;
                return;
            }
            if (getMedia(message4) instanceof TLRPC.TL_messageMediaWebPage) {
                TLRPC.Photo photo5 = getMedia(this.messageOwner).webpage.photo;
                TLRPC.Document document3 = getMedia(this.messageOwner).webpage.document;
                if (photo5 != null) {
                    if (!z || (arrayList5 = this.photoThumbs) == null) {
                        this.photoThumbs = new ArrayList<>(photo5.sizes);
                    } else if (!arrayList5.isEmpty()) {
                        updatePhotoSizeLocations(this.photoThumbs, photo5.sizes);
                    }
                    this.photoThumbsObject = photo5;
                    return;
                }
                if (document3 == null || !isDocumentHasThumb(document3)) {
                    return;
                }
                if (!z) {
                    ArrayList<TLRPC.PhotoSize> arrayList19 = new ArrayList<>();
                    this.photoThumbs = arrayList19;
                    arrayList19.addAll(document3.thumbs);
                } else {
                    ArrayList<TLRPC.PhotoSize> arrayList20 = this.photoThumbs;
                    if (arrayList20 != null && !arrayList20.isEmpty()) {
                        updatePhotoSizeLocations(this.photoThumbs, document3.thumbs);
                    }
                }
                this.photoThumbsObject = document3;
                return;
            }
            return;
        }
        TLRPC.Message message5 = this.messageOwner;
        if (message5 != null && (richMessage = message5.rich_message) != null) {
            TLRPC.Document documentFindVideo = findVideo(richMessage);
            TLRPC.Photo photoFindPhoto = findPhoto(this.messageOwner.rich_message);
            if (documentFindVideo != null) {
                if (isDocumentHasThumb(documentFindVideo)) {
                    if (!z) {
                        ArrayList<TLRPC.PhotoSize> arrayList21 = new ArrayList<>();
                        this.photoThumbs = arrayList21;
                        arrayList21.addAll(documentFindVideo.thumbs);
                    } else {
                        ArrayList<TLRPC.PhotoSize> arrayList22 = this.photoThumbs;
                        if (arrayList22 != null && !arrayList22.isEmpty()) {
                            updatePhotoSizeLocations(this.photoThumbs, documentFindVideo.thumbs);
                        }
                    }
                    this.photoThumbsObject = documentFindVideo;
                    return;
                }
                return;
            }
            if (photoFindPhoto != null) {
                if (!z || (arrayList4 = this.photoThumbs) == null) {
                    this.photoThumbs = new ArrayList<>(photoFindPhoto.sizes);
                } else if (!arrayList4.isEmpty()) {
                    updatePhotoSizeLocations(this.photoThumbs, photoFindPhoto.sizes);
                }
                this.photoThumbsObject = photoFindPhoto;
                if (this.strippedThumb == null) {
                    createStrippedThumb();
                    return;
                }
                return;
            }
            return;
        }
        TLRPC.MessageMedia messageMedia = this.sponsoredMedia;
        if (messageMedia != null) {
            TLRPC.Photo photo6 = messageMedia.photo;
            TLRPC.Document document4 = messageMedia.document;
            if (photo6 != null) {
                if (!z || (arrayList3 = this.photoThumbs) == null) {
                    this.photoThumbs = new ArrayList<>(photo6.sizes);
                } else if (!arrayList3.isEmpty()) {
                    updatePhotoSizeLocations(this.photoThumbs, photo6.sizes);
                }
                this.photoThumbsObject = photo6;
                return;
            }
            if (document4 == null || !isDocumentHasThumb(document4)) {
                return;
            }
            if (!z) {
                ArrayList<TLRPC.PhotoSize> arrayList23 = new ArrayList<>();
                this.photoThumbs = arrayList23;
                arrayList23.addAll(document4.thumbs);
            } else {
                ArrayList<TLRPC.PhotoSize> arrayList24 = this.photoThumbs;
                if (arrayList24 != null && !arrayList24.isEmpty()) {
                    updatePhotoSizeLocations(this.photoThumbs, document4.thumbs);
                }
            }
            this.photoThumbsObject = document4;
            return;
        }
        if (this.sponsoredPhoto != null) {
            if (!z || (arrayList2 = this.photoThumbs) == null) {
                this.photoThumbs = new ArrayList<>(this.sponsoredPhoto.sizes);
            } else if (!arrayList2.isEmpty()) {
                updatePhotoSizeLocations(this.photoThumbs, this.sponsoredPhoto.sizes);
            }
            this.photoThumbsObject = this.sponsoredPhoto;
            if (this.strippedThumb == null) {
                createStrippedThumb();
            }
        }
    }

    private static void updatePhotoSizeLocations(ArrayList<TLRPC.PhotoSize> arrayList, List<TLRPC.PhotoSize> list) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            TLRPC.PhotoSize photoSize = arrayList.get(i);
            if (photoSize != null) {
                int size2 = list.size();
                int i2 = 0;
                while (true) {
                    if (i2 < size2) {
                        TLRPC.PhotoSize photoSize2 = list.get(i2);
                        if (!(photoSize2 instanceof TLRPC.TL_photoSizeEmpty) && !(photoSize2 instanceof TLRPC.TL_photoCachedSize) && photoSize2 != null && photoSize2.type.equals(photoSize.type)) {
                            photoSize.location = photoSize2.location;
                            break;
                        }
                        i2++;
                    }
                }
            }
        }
    }

    public CharSequence replaceWithLink(CharSequence charSequence, String str, ArrayList<Long> arrayList, AbstractMap<Long, TLRPC.User> abstractMap, LongSparseArray<TLRPC.User> longSparseArray) {
        TLRPC.User user;
        if (TextUtils.indexOf(charSequence, str) < 0) {
            return charSequence;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(_UrlKt.FRAGMENT_ENCODE_SET);
        for (int i = 0; i < arrayList.size(); i++) {
            if (abstractMap != null) {
                user = abstractMap.get(arrayList.get(i));
            } else {
                user = longSparseArray != null ? longSparseArray.get(arrayList.get(i).longValue()) : null;
            }
            if (user == null) {
                user = MessagesController.getInstance(this.currentAccount).getUser(arrayList.get(i));
            }
            if (user != null) {
                String userName = UserObject.getUserName(user);
                int length = spannableStringBuilder.length();
                if (spannableStringBuilder.length() != 0) {
                    spannableStringBuilder.append((CharSequence) ", ");
                }
                spannableStringBuilder.append((CharSequence) userName);
                spannableStringBuilder.setSpan(new URLSpanNoUnderlineBold(_UrlKt.FRAGMENT_ENCODE_SET + user.f1407id), length, userName.length() + length, 33);
            }
        }
        return TextUtils.replace(charSequence, new String[]{str}, new CharSequence[]{spannableStringBuilder});
    }

    public static CharSequence replaceWithLink(CharSequence charSequence, String str, CharSequence charSequence2) {
        return TextUtils.indexOf(charSequence, str) >= 0 ? TextUtils.replace(charSequence, new String[]{str}, new CharSequence[]{charSequence2}) : charSequence;
    }

    public static CharSequence replaceWithLink(CharSequence charSequence, String str, TLObject tLObject) {
        String str2;
        String str3;
        TLObject tLObject2;
        String strReplace;
        int iIndexOf = TextUtils.indexOf(charSequence, str);
        if (iIndexOf < 0) {
            return charSequence;
        }
        boolean z = tLObject instanceof TLRPC.User;
        TLObject tLObject3 = null;
        tLObject3 = null;
        tLObject3 = null;
        CharSequence topicSpannedName = _UrlKt.FRAGMENT_ENCODE_SET;
        if (z) {
            TLRPC.User user = (TLRPC.User) tLObject;
            strReplace = UserObject.getUserName(user).replace('\n', ' ');
            str2 = _UrlKt.FRAGMENT_ENCODE_SET + user.f1407id;
        } else if (tLObject instanceof TLRPC.Chat) {
            TLRPC.Chat chat = (TLRPC.Chat) tLObject;
            strReplace = chat.title.replace('\n', ' ');
            str2 = _UrlKt.FRAGMENT_ENCODE_SET + (-chat.f1245id);
        } else {
            if (tLObject instanceof TLRPC.TL_game) {
                topicSpannedName = ((TLRPC.TL_game) tLObject).title.replace('\n', ' ');
                str2 = "game";
            } else {
                if (tLObject instanceof TLRPC.TL_chatInviteExported) {
                    TLRPC.TL_chatInviteExported tL_chatInviteExported = (TLRPC.TL_chatInviteExported) tLObject;
                    topicSpannedName = tL_chatInviteExported.link.replace('\n', ' ');
                    str3 = "invite";
                    tLObject2 = tL_chatInviteExported;
                } else if (tLObject instanceof TLRPC.ForumTopic) {
                    topicSpannedName = ForumUtilities.getTopicSpannedName((TLRPC.ForumTopic) tLObject, null, false);
                    str3 = "topic";
                    tLObject2 = tLObject;
                } else {
                    str2 = MVEL.VERSION_SUB;
                }
                tLObject3 = tLObject2;
                str2 = str3;
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(TextUtils.replace(charSequence, new String[]{str}, new CharSequence[]{topicSpannedName}));
            URLSpanNoUnderlineBold uRLSpanNoUnderlineBold = new URLSpanNoUnderlineBold(str2);
            uRLSpanNoUnderlineBold.setObject(tLObject3);
            spannableStringBuilder.setSpan(uRLSpanNoUnderlineBold, iIndexOf, topicSpannedName.length() + iIndexOf, 33);
            return spannableStringBuilder;
        }
        topicSpannedName = strReplace;
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(TextUtils.replace(charSequence, new String[]{str}, new CharSequence[]{topicSpannedName}));
        URLSpanNoUnderlineBold uRLSpanNoUnderlineBold2 = new URLSpanNoUnderlineBold(str2);
        uRLSpanNoUnderlineBold2.setObject(tLObject3);
        spannableStringBuilder2.setSpan(uRLSpanNoUnderlineBold2, iIndexOf, topicSpannedName.length() + iIndexOf, 33);
        return spannableStringBuilder2;
    }

    public String getExtension() {
        String fileName = getFileName();
        int iLastIndexOf = fileName.lastIndexOf(46);
        String strSubstring = iLastIndexOf != -1 ? fileName.substring(iLastIndexOf + 1) : null;
        if (strSubstring == null || strSubstring.length() == 0) {
            strSubstring = getDocument().mime_type;
        }
        if (strSubstring == null) {
            strSubstring = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        return strSubstring.toUpperCase();
    }

    public String getFileName() {
        if (getDocument() != null) {
            return getFileName(getDocument());
        }
        return getFileName(this.messageOwner);
    }

    public String getFileNameFast() {
        if (getDocumentFast() != null) {
            return getFileName(getDocumentFast());
        }
        return getFileName(this.messageOwner);
    }

    public static String getFileName(TLRPC.Message message) {
        TLRPC.PhotoSize closestPhotoSizeWithSize;
        if (getMedia(message) instanceof TLRPC.TL_messageMediaDocument) {
            return getFileName(getDocument(message));
        }
        if (getMedia(message) instanceof TLRPC.TL_messageMediaPhoto) {
            ArrayList<TLRPC.PhotoSize> arrayList = getMedia(message).photo.sizes;
            if (arrayList.size() > 0 && (closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(arrayList, AndroidUtilities.getPhotoSize())) != null) {
                return FileLoader.getAttachFileName(closestPhotoSizeWithSize);
            }
            return _UrlKt.FRAGMENT_ENCODE_SET;
        }
        if ((getMedia(message) instanceof TLRPC.TL_messageMediaWebPage) && getMedia(message).webpage != null) {
            return getFileName(getMedia(message).webpage.document);
        }
        return _UrlKt.FRAGMENT_ENCODE_SET;
    }

    public static String getFileName(TLRPC.Document document) {
        return FileLoader.getAttachFileName(document);
    }

    public static String getFileName(TLRPC.MessageMedia messageMedia) {
        TLRPC.WebPage webPage;
        TLRPC.PhotoSize closestPhotoSizeWithSize;
        if (messageMedia instanceof TLRPC.TL_messageMediaDocument) {
            return FileLoader.getAttachFileName(messageMedia.document);
        }
        if (messageMedia instanceof TLRPC.TL_messageMediaPhoto) {
            ArrayList<TLRPC.PhotoSize> arrayList = messageMedia.photo.sizes;
            if (arrayList.size() > 0 && (closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(arrayList, AndroidUtilities.getPhotoSize())) != null) {
                return FileLoader.getAttachFileName(closestPhotoSizeWithSize);
            }
            return _UrlKt.FRAGMENT_ENCODE_SET;
        }
        if ((messageMedia instanceof TLRPC.TL_messageMediaWebPage) && (webPage = messageMedia.webpage) != null) {
            return FileLoader.getAttachFileName(webPage.document);
        }
        return _UrlKt.FRAGMENT_ENCODE_SET;
    }

    public int getMediaType() {
        if (isVideo()) {
            return 2;
        }
        if (isVoice()) {
            return 1;
        }
        if (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaDocument) {
            return 3;
        }
        return getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaPhoto ? 0 : 4;
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean containsUrls(java.lang.CharSequence r14) {
        /*
            r0 = 0
            if (r14 == 0) goto L8e
            int r1 = r14.length()
            r2 = 2
            if (r1 < r2) goto L8e
            int r1 = r14.length()
            r3 = 20480(0x5000, float:2.8699E-41)
            if (r1 <= r3) goto L14
            goto L8e
        L14:
            int r1 = r14.length()
            r3 = r0
            r4 = r3
            r5 = r4
            r6 = r5
            r7 = r6
        L1d:
            if (r3 >= r1) goto L8e
            char r8 = r14.charAt(r3)
            r9 = 48
            r10 = 32
            r11 = 1
            if (r8 < r9) goto L37
            r9 = 57
            if (r8 > r9) goto L37
            int r4 = r4 + 1
            r5 = 6
            if (r4 < r5) goto L34
            return r11
        L34:
            r5 = r0
            r6 = r5
            goto L3c
        L37:
            if (r8 == r10) goto L3b
            if (r4 > 0) goto L3c
        L3b:
            r4 = r0
        L3c:
            r9 = 64
            r12 = 47
            if (r8 == r9) goto L4c
            r9 = 35
            if (r8 == r9) goto L4c
            if (r8 == r12) goto L4c
            r9 = 36
            if (r8 != r9) goto L4e
        L4c:
            if (r3 == 0) goto L8d
        L4e:
            if (r3 == 0) goto L61
            int r9 = r3 + (-1)
            char r13 = r14.charAt(r9)
            if (r13 == r10) goto L8d
            char r9 = r14.charAt(r9)
            r13 = 10
            if (r9 != r13) goto L61
            goto L8d
        L61:
            r9 = 58
            if (r8 != r9) goto L6b
            if (r5 != 0) goto L69
            r5 = r11
            goto L89
        L69:
            r5 = r0
            goto L89
        L6b:
            if (r8 != r12) goto L75
            if (r5 != r2) goto L70
            return r11
        L70:
            if (r5 != r11) goto L69
            int r5 = r5 + 1
            goto L89
        L75:
            r9 = 46
            if (r8 != r9) goto L82
            if (r6 != 0) goto L80
            if (r7 == r10) goto L80
            int r6 = r6 + 1
            goto L89
        L80:
            r6 = r0
            goto L89
        L82:
            if (r8 == r10) goto L80
            if (r7 != r9) goto L80
            if (r6 != r11) goto L80
            return r11
        L89:
            int r3 = r3 + 1
            r7 = r8
            goto L1d
        L8d:
            return r11
        L8e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.containsUrls(java.lang.CharSequence):boolean");
    }

    public void generateLinkDescription() {
        boolean z;
        int i;
        int i2;
        TLRPC.TL_webPageAttributeStory tL_webPageAttributeStory;
        TL_stories.StoryItem storyItem;
        if (this.linkDescription != null) {
            return;
        }
        TLRPC.WebPage webPage = this.storyMentionWebpage;
        if (webPage == null) {
            webPage = getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage ? ((TLRPC.TL_messageMediaWebPage) getMedia(this.messageOwner)).webpage : null;
        }
        if (webPage != null) {
            for (int i3 = 0; i3 < webPage.attributes.size(); i3++) {
                TLRPC.WebPageAttribute webPageAttribute = webPage.attributes.get(i3);
                if ((webPageAttribute instanceof TLRPC.TL_webPageAttributeStory) && (storyItem = (tL_webPageAttributeStory = (TLRPC.TL_webPageAttributeStory) webPageAttribute).storyItem) != null && storyItem.caption != null) {
                    this.linkDescription = new SpannableStringBuilder(tL_webPageAttributeStory.storyItem.caption);
                    this.webPageDescriptionEntities = tL_webPageAttributeStory.storyItem.entities;
                    z = true;
                    break;
                }
            }
            z = false;
        } else {
            z = false;
        }
        if (this.linkDescription != null) {
            i = 0;
        } else if ((getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && (getMedia(this.messageOwner).webpage instanceof TLRPC.TL_webPage) && getMedia(this.messageOwner).webpage.description != null) {
            this.linkDescription = Spannable.Factory.getInstance().newSpannable(getMedia(this.messageOwner).webpage.description);
            String lowerCase = getMedia(this.messageOwner).webpage.site_name;
            if (lowerCase != null) {
                lowerCase = lowerCase.toLowerCase();
            }
            if ("instagram".equals(lowerCase)) {
                i2 = 1;
            } else {
                i2 = "twitter".equals(lowerCase) ? 2 : 0;
            }
            i = i2;
        } else {
            if ((getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaGame) && getMedia(this.messageOwner).game.description != null) {
                this.linkDescription = Spannable.Factory.getInstance().newSpannable(getMedia(this.messageOwner).game.description);
            } else if ((getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaInvoice) && getMedia(this.messageOwner).description != null) {
                this.linkDescription = Spannable.Factory.getInstance().newSpannable(getMedia(this.messageOwner).description);
            }
            i = 0;
        }
        if (TextUtils.isEmpty(this.linkDescription)) {
            return;
        }
        if (containsUrls(this.linkDescription)) {
            try {
                AndroidUtilities.addLinksSafe((Spannable) this.linkDescription, 1, false, true);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
        CharSequence charSequenceReplaceEmoji = Emoji.replaceEmoji(this.linkDescription, Theme.chat_msgTextPaint.getFontMetricsInt(), false);
        this.linkDescription = charSequenceReplaceEmoji;
        ArrayList<TLRPC.MessageEntity> arrayList = this.webPageDescriptionEntities;
        if (arrayList != null) {
            addEntitiesToText(charSequenceReplaceEmoji, arrayList, isOut(), z, false, !z);
            replaceAnimatedEmoji(this.linkDescription, this.webPageDescriptionEntities, Theme.chat_msgTextPaint.getFontMetricsInt());
        }
        if (i != 0) {
            if (!(this.linkDescription instanceof Spannable)) {
                this.linkDescription = new SpannableStringBuilder(this.linkDescription);
            }
            addUrlsByPattern(isOutOwner(), this.linkDescription, false, i, 0, false);
        }
    }

    public CharSequence getVoiceTranscription() {
        String str;
        TLRPC.TL_textWithEntities tL_textWithEntities;
        TLRPC.Message message = this.messageOwner;
        if (message == null || (str = message.voiceTranscription) == null) {
            return null;
        }
        if (TextUtils.isEmpty(str)) {
            SpannableString spannableString = new SpannableString(LocaleController.getString(C2797R.string.NoWordsRecognized));
            spannableString.setSpan(new CharacterStyle() { // from class: org.telegram.messenger.MessageObject.1
                public C27781() {
                }

                @Override // android.text.style.CharacterStyle
                public void updateDrawState(android.text.TextPaint textPaint) {
                    textPaint.setTextSize(textPaint.getTextSize() * 0.8f);
                    textPaint.setColor(Theme.chat_timePaint.getColor());
                }
            }, 0, spannableString.length(), 33);
            return spannableString;
        }
        String str2 = (!this.translated || (tL_textWithEntities = this.messageOwner.translatedVoiceTranscription) == null) ? this.messageOwner.voiceTranscription : tL_textWithEntities.text;
        return !TextUtils.isEmpty(str2) ? Emoji.replaceEmoji(str2, Theme.chat_msgTextPaint.getFontMetricsInt(), false) : str2;
    }

    /* JADX INFO: renamed from: org.telegram.messenger.MessageObject$1 */
    /* JADX INFO: loaded from: classes5.dex */
    public class C27781 extends CharacterStyle {
        public C27781() {
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(android.text.TextPaint textPaint) {
            textPaint.setTextSize(textPaint.getTextSize() * 0.8f);
            textPaint.setColor(Theme.chat_timePaint.getColor());
        }
    }

    public float measureVoiceTranscriptionHeight() {
        if (getVoiceTranscription() == null) {
            return 0.0f;
        }
        return StaticLayout.Builder.obtain(r0, 0, r0.length(), Theme.chat_msgTextPaint, AndroidUtilities.displaySize.x - AndroidUtilities.m1036dp(needDrawAvatar() ? 147.0f : 95.0f)).setBreakStrategy(1).setHyphenationFrequency(0).setAlignment(Layout.Alignment.ALIGN_NORMAL).build().getHeight();
    }

    public boolean isVoiceTranscriptionOpen() {
        if (this.messageOwner == null) {
            return false;
        }
        if (!isVoice() && (!isRoundVideo() || !TranscribeButton.isVideoTranscriptionOpen(this))) {
            return false;
        }
        TLRPC.Message message = this.messageOwner;
        if (!message.voiceTranscriptionOpen || message.voiceTranscription == null) {
            return false;
        }
        return message.voiceTranscriptionFinal || TranscribeButton.isTranscribing(this);
    }

    public void generateExplanation() {
        String str;
        ArrayList<TLRPC.MessageEntity> arrayList;
        TLRPC.PollResults pollResults;
        if (this.type != 17) {
            return;
        }
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        if (!(media instanceof TLRPC.TL_messageMediaPoll) || (pollResults = ((TLRPC.TL_messageMediaPoll) media).results) == null) {
            str = null;
            arrayList = null;
        } else {
            str = pollResults.solution;
            arrayList = pollResults.solution_entities;
        }
        if (str != null) {
            CharSequence charSequenceReplaceEmoji = Emoji.replaceEmoji(str, Theme.chat_explanationTextPaint.getFontMetricsInt(), false);
            this.quizExplanation = charSequenceReplaceEmoji;
            Spannable spannableReplaceAnimatedEmoji = replaceAnimatedEmoji(charSequenceReplaceEmoji, arrayList, Theme.chat_explanationTextPaint.getFontMetricsInt(), false);
            this.quizExplanation = spannableReplaceAnimatedEmoji;
            addEntitiesToText(spannableReplaceAnimatedEmoji, arrayList, isOutOwner(), true, false, false);
            return;
        }
        this.quizExplanation = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:128:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x009a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void generateCaption() {
        /*
            Method dump skipped, instruction units count: 419
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.generateCaption():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:225:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x0240  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void addUrlsByPattern(boolean r18, java.lang.CharSequence r19, boolean r20, int r21, int r22, boolean r23) {
        /*
            Method dump skipped, instruction units count: 588
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.addUrlsByPattern(boolean, java.lang.CharSequence, boolean, int, int, boolean):void");
    }

    public static int[] getWebDocumentWidthAndHeight(TLRPC.WebDocument webDocument) {
        if (webDocument == null) {
            return null;
        }
        int size = webDocument.attributes.size();
        for (int i = 0; i < size; i++) {
            TLRPC.DocumentAttribute documentAttribute = webDocument.attributes.get(i);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeImageSize) {
                return new int[]{documentAttribute.f1256w, documentAttribute.f1255h};
            }
            if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                return new int[]{documentAttribute.f1256w, documentAttribute.f1255h};
            }
        }
        return null;
    }

    public static double getWebDocumentDuration(TLRPC.WebDocument webDocument) {
        if (webDocument == null) {
            return 0.0d;
        }
        int size = webDocument.attributes.size();
        for (int i = 0; i < size; i++) {
            TLRPC.DocumentAttribute documentAttribute = webDocument.attributes.get(i);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                return documentAttribute.duration;
            }
            if (documentAttribute instanceof TLRPC.TL_documentAttributeAudio) {
                return documentAttribute.duration;
            }
        }
        return 0.0d;
    }

    public static int[] getInlineResultWidthAndHeight(TLRPC.BotInlineResult botInlineResult) {
        int[] webDocumentWidthAndHeight = getWebDocumentWidthAndHeight(botInlineResult.content);
        if (webDocumentWidthAndHeight != null) {
            return webDocumentWidthAndHeight;
        }
        int[] webDocumentWidthAndHeight2 = getWebDocumentWidthAndHeight(botInlineResult.thumb);
        return webDocumentWidthAndHeight2 == null ? new int[]{0, 0} : webDocumentWidthAndHeight2;
    }

    public static int getInlineResultDuration(TLRPC.BotInlineResult botInlineResult) {
        int webDocumentDuration = (int) getWebDocumentDuration(botInlineResult.content);
        return webDocumentDuration == 0 ? (int) getWebDocumentDuration(botInlineResult.thumb) : webDocumentDuration;
    }

    public boolean hasValidGroupId() {
        int i;
        if (getGroupId() == 0) {
            return false;
        }
        ArrayList<TLRPC.PhotoSize> arrayList = this.photoThumbs;
        return !(arrayList == null || arrayList.isEmpty()) || (i = this.type) == 3 || i == 1 || isMusic() || isDocument();
    }

    public boolean hasValidGroupIdFast() {
        int i;
        if (getGroupId() == 0) {
            return false;
        }
        ArrayList<TLRPC.PhotoSize> arrayList = this.photoThumbs;
        return !(arrayList == null || arrayList.isEmpty()) || (i = this.type) == 3 || i == 1 || i == 14 || i == 9;
    }

    public long getGroupIdForUse() {
        long j = this.localSentGroupId;
        return j != 0 ? j : this.messageOwner.grouped_id;
    }

    public long getGroupId() {
        long j = this.localGroupId;
        return j != 0 ? j : getGroupIdForUse();
    }

    public static void addLinks(boolean z, CharSequence charSequence) {
        addLinks(z, charSequence, true, false);
    }

    public static void addLinks(boolean z, CharSequence charSequence, boolean z2, boolean z3) {
        addLinks(z, charSequence, z2, z3, false);
    }

    public static void addLinks(boolean z, CharSequence charSequence, boolean z2, boolean z3, boolean z4) {
        if ((charSequence instanceof Spannable) && containsUrls(charSequence)) {
            try {
                AndroidUtilities.addLinksSafe((Spannable) charSequence, 1, z4, false);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
            addPhoneLinks(charSequence);
            addUrlsByPattern(z, charSequence, z2, 0, 0, z3);
        }
    }

    public void resetPlayingProgress() {
        this.audioProgress = 0.0f;
        this.audioProgressSec = 0;
        this.bufferedProgress = 0.0f;
    }

    private boolean addEntitiesToText(CharSequence charSequence, boolean z) {
        return addEntitiesToText(charSequence, false, z);
    }

    public boolean addEntitiesToText(CharSequence charSequence, boolean z, boolean z2) {
        if (charSequence == null) {
            return false;
        }
        if (this.isRestrictedMessage || (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaUnsupported)) {
            ArrayList arrayList = new ArrayList();
            TLRPC.TL_messageEntityItalic tL_messageEntityItalic = new TLRPC.TL_messageEntityItalic();
            tL_messageEntityItalic.offset = 0;
            tL_messageEntityItalic.length = charSequence.length();
            arrayList.add(tL_messageEntityItalic);
            return addEntitiesToText(charSequence, arrayList, isOutOwner(), true, z, z2);
        }
        return addEntitiesToText(charSequence, getEntities(), isOutOwner(), true, z, z2);
    }

    public void replaceEmojiToLottieFrame(CharSequence charSequence, int[] iArr) {
        boolean z;
        if (!(charSequence instanceof Spannable) || SharedConfig.useSystemEmoji) {
            return;
        }
        Spannable spannable = (Spannable) charSequence;
        Emoji.EmojiSpan[] emojiSpanArr = (Emoji.EmojiSpan[]) spannable.getSpans(0, spannable.length(), Emoji.EmojiSpan.class);
        AnimatedEmojiSpan[] animatedEmojiSpanArr = (AnimatedEmojiSpan[]) spannable.getSpans(0, spannable.length(), AnimatedEmojiSpan.class);
        if (emojiSpanArr != null) {
            if (((iArr == null ? 0 : iArr[0]) - emojiSpanArr.length) - (animatedEmojiSpanArr == null ? 0 : animatedEmojiSpanArr.length) > 0) {
                return;
            }
            for (Emoji.EmojiSpan emojiSpan : emojiSpanArr) {
                CharSequence charSequenceSubSequence = emojiSpan.emoji;
                if (Emoji.endsWithRightArrow(charSequenceSubSequence)) {
                    charSequenceSubSequence = charSequenceSubSequence.subSequence(0, charSequenceSubSequence.length() - 2);
                    z = true;
                } else {
                    z = false;
                }
                TLRPC.Document emojiAnimatedSticker = MediaDataController.getInstance(this.currentAccount).getEmojiAnimatedSticker(charSequenceSubSequence);
                if (emojiAnimatedSticker != null) {
                    int spanStart = spannable.getSpanStart(emojiSpan);
                    int spanEnd = spannable.getSpanEnd(emojiSpan);
                    spannable.removeSpan(emojiSpan);
                    AnimatedEmojiSpan animatedEmojiSpan = new AnimatedEmojiSpan(emojiAnimatedSticker, emojiSpan.fontMetrics);
                    animatedEmojiSpan.standard = true;
                    animatedEmojiSpan.invert = z;
                    spannable.setSpan(animatedEmojiSpan, spanStart, spanEnd, 33);
                }
            }
        }
    }

    public ArrayList<TLRPC.MessageEntity> getEntities() {
        TLRPC.TL_textWithEntities tL_textWithEntities;
        TLRPC.Message message = this.messageOwner;
        if (message == null) {
            return null;
        }
        boolean z = this.summarized;
        boolean z2 = this.translated;
        if (z) {
            if (z2 && (tL_textWithEntities = message.translatedSummaryText) != null) {
                return tL_textWithEntities.entities;
            }
            TLRPC.TL_textWithEntities tL_textWithEntities2 = message.summaryText;
            if (tL_textWithEntities2 != null) {
                return tL_textWithEntities2.entities;
            }
            return null;
        }
        if (z2) {
            if (message.voiceTranscriptionOpen) {
                TLRPC.TL_textWithEntities tL_textWithEntities3 = message.translatedVoiceTranscription;
                if (tL_textWithEntities3 != null) {
                    return tL_textWithEntities3.entities;
                }
                return null;
            }
            TLRPC.TL_textWithEntities tL_textWithEntities4 = message.translatedText;
            if (tL_textWithEntities4 == null || tL_textWithEntities4 == null) {
                return null;
            }
            return tL_textWithEntities4.entities;
        }
        return message.entities;
    }

    public Spannable replaceAnimatedEmoji(CharSequence charSequence, Paint.FontMetricsInt fontMetricsInt) {
        return replaceAnimatedEmoji(charSequence, getEntities(), fontMetricsInt, false);
    }

    public static Spannable replaceAnimatedEmoji(CharSequence charSequence, ArrayList<TLRPC.MessageEntity> arrayList, Paint.FontMetricsInt fontMetricsInt) {
        return replaceAnimatedEmoji(charSequence, arrayList, fontMetricsInt, false);
    }

    public static Spannable replaceAnimatedEmoji(CharSequence charSequence, ArrayList<TLRPC.MessageEntity> arrayList, Paint.FontMetricsInt fontMetricsInt, boolean z) {
        return replaceAnimatedEmoji(charSequence, arrayList, fontMetricsInt, z, 1.2f, 0);
    }

    public static Spannable replaceAnimatedEmoji(CharSequence charSequence, ArrayList<TLRPC.MessageEntity> arrayList, Paint.FontMetricsInt fontMetricsInt, boolean z, float f, int i) {
        Emoji.EmojiSpan emojiSpan;
        AnimatedEmojiSpan animatedEmojiSpan;
        Emoji.EmojiSpan emojiSpan2;
        Emoji.EmojiSpan emojiSpan3 = null;
        if (charSequence == null) {
            return null;
        }
        Spannable spannableString = charSequence instanceof Spannable ? (Spannable) charSequence : new SpannableString(charSequence);
        if (arrayList != null) {
            int i2 = (SharedConfig.getDevicePerformanceClass() >= 2 ? 100 : 50) - i;
            Emoji.EmojiSpan[] emojiSpanArr = (Emoji.EmojiSpan[]) spannableString.getSpans(0, spannableString.length(), Emoji.EmojiSpan.class);
            int i3 = 0;
            while (i3 < arrayList.size() && i2 > 0) {
                TLRPC.MessageEntity messageEntity = arrayList.get(i3);
                if (messageEntity instanceof TLRPC.TL_messageEntityCustomEmoji) {
                    TLRPC.TL_messageEntityCustomEmoji tL_messageEntityCustomEmoji = (TLRPC.TL_messageEntityCustomEmoji) messageEntity;
                    int i4 = 0;
                    while (i4 < emojiSpanArr.length) {
                        Emoji.EmojiSpan emojiSpan4 = emojiSpanArr[i4];
                        if (emojiSpan4 != null) {
                            int spanStart = spannableString.getSpanStart(emojiSpan4);
                            int spanEnd = spannableString.getSpanEnd(emojiSpan4);
                            int i5 = tL_messageEntityCustomEmoji.offset;
                            emojiSpan2 = emojiSpan3;
                            if (AndroidUtilities.intersect1d(i5, tL_messageEntityCustomEmoji.length + i5, spanStart, spanEnd)) {
                                spannableString.removeSpan(emojiSpan4);
                                emojiSpanArr[i4] = emojiSpan2;
                            }
                        } else {
                            emojiSpan2 = emojiSpan3;
                        }
                        i4++;
                        emojiSpan3 = emojiSpan2;
                    }
                    emojiSpan = emojiSpan3;
                    if (messageEntity.offset + messageEntity.length <= spannableString.length()) {
                        int i6 = messageEntity.offset;
                        AnimatedEmojiSpan[] animatedEmojiSpanArr = (AnimatedEmojiSpan[]) spannableString.getSpans(i6, messageEntity.length + i6, AnimatedEmojiSpan.class);
                        if (animatedEmojiSpanArr != null && animatedEmojiSpanArr.length > 0) {
                            for (AnimatedEmojiSpan animatedEmojiSpan2 : animatedEmojiSpanArr) {
                                spannableString.removeSpan(animatedEmojiSpan2);
                            }
                        }
                        if (tL_messageEntityCustomEmoji.document != null) {
                            animatedEmojiSpan = new AnimatedEmojiSpan(tL_messageEntityCustomEmoji.document, f, fontMetricsInt);
                        } else {
                            animatedEmojiSpan = new AnimatedEmojiSpan(tL_messageEntityCustomEmoji.document_id, f, fontMetricsInt);
                        }
                        animatedEmojiSpan.local = tL_messageEntityCustomEmoji.local;
                        animatedEmojiSpan.top = z;
                        int i7 = messageEntity.offset;
                        spannableString.setSpan(animatedEmojiSpan, i7, messageEntity.length + i7, 33);
                        i2--;
                    }
                    i3++;
                    emojiSpan3 = emojiSpan;
                } else {
                    emojiSpan = emojiSpan3;
                }
                i3++;
                emojiSpan3 = emojiSpan;
            }
        }
        return spannableString;
    }

    public static boolean addEntitiesToText(CharSequence charSequence, ArrayList<TLRPC.MessageEntity> arrayList, boolean z, boolean z2, boolean z3, boolean z4) {
        return addEntitiesToText(charSequence, arrayList, z, z2, z3, z4, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:365:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:492:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:524:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:617:0x04b2 A[PHI: r0 r5 r7 r8 r19
  0x04b2: PHI (r0v43 android.text.Spannable) = (r0v10 android.text.Spannable), (r0v38 android.text.Spannable), (r0v44 android.text.Spannable) binds: [B:616:0x04b0, B:619:0x04c6, B:533:0x02a3] A[DONT_GENERATE, DONT_INLINE]
  0x04b2: PHI (r5v26 int) = (r5v4 int), (r5v20 int), (r5v4 int) binds: [B:616:0x04b0, B:619:0x04c6, B:533:0x02a3] A[DONT_GENERATE, DONT_INLINE]
  0x04b2: PHI (r7v23 boolean) = (r7v3 boolean), (r7v20 boolean), (r7v3 boolean) binds: [B:616:0x04b0, B:619:0x04c6, B:533:0x02a3] A[DONT_GENERATE, DONT_INLINE]
  0x04b2: PHI (r8v51 byte) = (r8v13 byte), (r8v46 byte), (r8v52 byte) binds: [B:616:0x04b0, B:619:0x04c6, B:533:0x02a3] A[DONT_GENERATE, DONT_INLINE]
  0x04b2: PHI (r19v10 int) = (r19v2 int), (r19v5 int), (r19v11 int) binds: [B:616:0x04b0, B:619:0x04c6, B:533:0x02a3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:620:0x04c8  */
    /* JADX WARN: Removed duplicated region for block: B:656:0x027a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean addEntitiesToText(java.lang.CharSequence r20, java.util.ArrayList<org.telegram.tgnet.TLRPC.MessageEntity> r21, boolean r22, boolean r23, boolean r24, boolean r25, int r26) {
        /*
            Method dump skipped, instruction units count: 1406
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.addEntitiesToText(java.lang.CharSequence, java.util.ArrayList, boolean, boolean, boolean, boolean, int):boolean");
    }

    public static /* synthetic */ int $r8$lambda$w1b4brUQc9jkaGF7tJdOKyazR9k(TLRPC.MessageEntity messageEntity, TLRPC.MessageEntity messageEntity2) {
        int i = messageEntity.offset;
        int i2 = messageEntity2.offset;
        if (i > i2) {
            return 1;
        }
        return i < i2 ? -1 : 0;
    }

    public boolean needDrawShareButton() {
        int i;
        TLRPC.Message message;
        TLRPC.MessageFwdHeader messageFwdHeader;
        if (this.isRepostPreview || this.sideMenuEnabled || getDialogId() == UserObject.VERIFY) {
            return false;
        }
        if (this.isSaved) {
            long j = UserConfig.getInstance(this.currentAccount).clientUserId;
            long savedDialogId = getSavedDialogId(j, this.messageOwner);
            if (savedDialogId == j || savedDialogId == UserObject.ANONYMOUS || (message = this.messageOwner) == null || (messageFwdHeader = message.fwd_from) == null) {
                return false;
            }
            return (messageFwdHeader.from_id == null && messageFwdHeader.saved_from_id == null) ? false : true;
        }
        if (this.type == 27 || isSponsored() || this.hasCode || this.preview || this.scheduled || this.eventId != 0) {
            return false;
        }
        if (this.searchType == 2) {
            return true;
        }
        TLRPC.Message message2 = this.messageOwner;
        if (message2.noforwards) {
            return false;
        }
        if (message2.fwd_from != null && !isOutOwner() && this.messageOwner.fwd_from.saved_from_peer != null && getDialogId() == UserConfig.getInstance(this.currentAccount).getClientUserId()) {
            return true;
        }
        int i2 = this.type;
        if (i2 != 13 && i2 != 15 && i2 != 19) {
            TLRPC.MessageFwdHeader messageFwdHeader2 = this.messageOwner.fwd_from;
            if (messageFwdHeader2 != null && (messageFwdHeader2.from_id instanceof TLRPC.TL_peerChannel) && !isOutOwner()) {
                return true;
            }
            if (isFromUser()) {
                TLRPC.User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.messageOwner.from_id.user_id));
                if (user != null && user.bot && ("reviews_bot".equals(UserObject.getPublicUsername(user)) || "ReviewInsightsBot".equals(UserObject.getPublicUsername(user)))) {
                    return true;
                }
                if ((getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaEmpty) || getMedia(this.messageOwner) == null || ((getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && !(getMedia(this.messageOwner).webpage instanceof TLRPC.TL_webPage))) {
                    return false;
                }
                if (user != null && user.bot && !hasExtendedMedia()) {
                    return true;
                }
                if (!isOut()) {
                    if ((getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaGame) || (((getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaInvoice) && !hasExtendedMedia()) || (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage))) {
                        return true;
                    }
                    TLRPC.Peer peer = this.messageOwner.peer_id;
                    TLRPC.Chat chat = null;
                    if (peer != null) {
                        long j2 = peer.channel_id;
                        if (j2 != 0) {
                            chat = getChat(null, null, j2);
                        }
                    }
                    return ChatObject.isChannel(chat) && chat.megagroup && ChatObject.isPublic(chat) && !(getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaContact) && !(getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaGeo);
                }
            } else {
                TLRPC.Message message3 = this.messageOwner;
                if ((message3.from_id instanceof TLRPC.TL_peerChannel) || message3.post) {
                    if ((getMedia(message3) instanceof TLRPC.TL_messageMediaWebPage) && !isOutOwner()) {
                        return true;
                    }
                    if (isSupergroup()) {
                        return false;
                    }
                    TLRPC.Message message4 = this.messageOwner;
                    if (message4.peer_id.channel_id != 0 && ((message4.via_bot_id == 0 && message4.reply_to == null) || ((i = this.type) != 13 && i != 15))) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public boolean isYouTubeVideo() {
        return (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && getMedia(this.messageOwner).webpage != null && !TextUtils.isEmpty(getMedia(this.messageOwner).webpage.embed_url) && "YouTube".equals(getMedia(this.messageOwner).webpage.site_name);
    }

    public boolean isEmbedVideo() {
        TLRPC.MessageMedia messageMedia;
        TLRPC.WebPage webPage;
        TLRPC.MessageMedia messageMedia2;
        Boolean bool = this.isEmbedVideoCached;
        TLRPC.Message message = this.messageOwner;
        boolean z = false;
        if (bool != null) {
            return (message == null || (messageMedia2 = message.media) == null || messageMedia2.webpage == null || !bool.booleanValue()) ? false : true;
        }
        if (message != null && (messageMedia = message.media) != null && (webPage = messageMedia.webpage) != null && !TextUtils.isEmpty(WebPlayerView.getYouTubeVideoId(webPage.url))) {
            z = true;
        }
        this.isEmbedVideoCached = Boolean.valueOf(z);
        return z;
    }

    private int getParentWidth() {
        int i;
        if (this.preview && (i = this.parentWidth) > 0) {
            return i;
        }
        if (AndroidUtilities.isTablet()) {
            return AndroidUtilities.getMinTabletSide();
        }
        Point point = AndroidUtilities.displaySize;
        int i2 = point.x;
        return i2 > point.y ? i2 - AndroidUtilities.m1036dp(50.0f) : i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:147:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x013a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getMaxMessageTextWidth() {
        /*
            Method dump skipped, instruction units count: 369
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.getMaxMessageTextWidth():int");
    }

    public boolean updateSideMenuEnabled(boolean z) {
        if (this.sideMenuEnabled == z) {
            return false;
        }
        this.sideMenuEnabled = z;
        generateLayout(null);
        return true;
    }

    public void applyTimestampsHighlightForReplyMsg() {
        applyTimestampsHighlightForReplyMsg(this.messageText);
    }

    public void applyTimestampsHighlightForReplyMsg(CharSequence charSequence) {
        TLRPC.Message message;
        MessageObject messageObject = this.replyMessageObject;
        if (messageObject == null) {
            return;
        }
        if (messageObject.isYouTubeVideo()) {
            addUrlsByPattern(isOutOwner(), charSequence, false, 3, Integer.MAX_VALUE, false);
            return;
        }
        if (messageObject.isVideo()) {
            addUrlsByPattern(isOutOwner(), charSequence, false, 3, (int) messageObject.getDuration(), false);
            return;
        }
        if (messageObject.isMusic() || messageObject.isVoice()) {
            addUrlsByPattern(isOutOwner(), charSequence, false, 4, (int) messageObject.getDuration(), false);
        }
        if (charSequence != this.messageText || (message = this.messageOwner) == null) {
            return;
        }
        TLRPC.MessageAction messageAction = message.action;
        if ((messageAction instanceof TLRPC.TL_messageActionTodoCompletions) || (messageAction instanceof TLRPC.TL_messageActionTodoAppendTasks)) {
            updateMessageText();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:75:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean applyEntities() {
        /*
            r8 = this;
            r8.generateLinkDescription()
            r8.spoilLoginCode()
            org.telegram.tgnet.TLRPC$Message r0 = r8.messageOwner
            int r0 = r0.send_state
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L10
            r0 = r1
            goto L19
        L10:
            java.util.ArrayList r0 = r8.getEntities()
            boolean r0 = r0.isEmpty()
            r0 = r0 ^ r2
        L19:
            if (r0 != 0) goto L5c
            long r3 = r8.eventId
            r5 = 0
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 != 0) goto L5b
            org.telegram.tgnet.TLRPC$Message r0 = r8.messageOwner
            boolean r3 = r0 instanceof org.telegram.tgnet.TLRPC.TL_message_old
            if (r3 != 0) goto L5b
            boolean r3 = r0 instanceof org.telegram.tgnet.TLRPC.TL_message_old2
            if (r3 != 0) goto L5b
            boolean r3 = r0 instanceof org.telegram.tgnet.TLRPC.TL_message_old3
            if (r3 != 0) goto L5b
            boolean r3 = r0 instanceof org.telegram.tgnet.TLRPC.TL_message_old4
            if (r3 != 0) goto L5b
            boolean r3 = r0 instanceof org.telegram.tgnet.TLRPC.TL_messageForwarded_old
            if (r3 != 0) goto L5b
            boolean r3 = r0 instanceof org.telegram.tgnet.TLRPC.TL_messageForwarded_old2
            if (r3 != 0) goto L5b
            boolean r3 = r0 instanceof org.telegram.tgnet.TLRPC.TL_message_secret
            if (r3 != 0) goto L5b
            org.telegram.tgnet.TLRPC$MessageMedia r0 = getMedia(r0)
            boolean r0 = r0 instanceof org.telegram.tgnet.TLRPC.TL_messageMediaInvoice
            if (r0 != 0) goto L5b
            boolean r0 = r8.isOut()
            if (r0 == 0) goto L55
            org.telegram.tgnet.TLRPC$Message r0 = r8.messageOwner
            int r0 = r0.send_state
            if (r0 != 0) goto L5b
        L55:
            org.telegram.tgnet.TLRPC$Message r0 = r8.messageOwner
            int r0 = r0.f1271id
            if (r0 >= 0) goto L5c
        L5b:
            r1 = r2
        L5c:
            if (r1 == 0) goto L68
            boolean r0 = r8.isOutOwner()
            java.lang.CharSequence r3 = r8.messageText
            addLinks(r0, r3, r2, r2)
            goto L6d
        L68:
            java.lang.CharSequence r0 = r8.messageText
            addPhoneLinks(r0)
        L6d:
            boolean r0 = r8.isYouTubeVideo()
            if (r0 == 0) goto L83
            boolean r2 = r8.isOutOwner()
            java.lang.CharSequence r3 = r8.messageText
            r6 = 2147483647(0x7fffffff, float:NaN)
            r7 = 0
            r4 = 0
            r5 = 3
            addUrlsByPattern(r2, r3, r4, r5, r6, r7)
            goto L86
        L83:
            r8.applyTimestampsHighlightForReplyMsg()
        L86:
            java.lang.CharSequence r0 = r8.messageText
            boolean r0 = r0 instanceof android.text.Spannable
            if (r0 != 0) goto L95
            android.text.SpannableStringBuilder r0 = new android.text.SpannableStringBuilder
            java.lang.CharSequence r2 = r8.messageText
            r0.<init>(r2)
            r8.messageText = r0
        L95:
            java.lang.CharSequence r0 = r8.messageText
            boolean r8 = r8.addEntitiesToText(r0, r1)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.applyEntities():boolean");
    }

    public static StaticLayout makeStaticLayout(CharSequence charSequence, android.text.TextPaint textPaint, int i, float f, float f2, boolean z) {
        if (i <= 0) {
            i = 1;
        }
        StaticLayout.Builder alignment = StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), textPaint, i).setLineSpacing(f2, f).setBreakStrategy(1).setHyphenationFrequency(0).setAlignment(Layout.Alignment.ALIGN_NORMAL);
        if (z) {
            alignment.setIncludePad(false);
            if (Build.VERSION.SDK_INT >= 28) {
                alignment.setUseLineSpacingFromFallbacks(false);
            }
        }
        StaticLayout staticLayoutBuild = alignment.build();
        for (int i2 = 0; i2 < staticLayoutBuild.getLineCount(); i2++) {
            if (staticLayoutBuild.getLineRight(i2) > i) {
                StaticLayout.Builder alignment2 = StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), textPaint, i).setLineSpacing(f2, f).setBreakStrategy(0).setHyphenationFrequency(0).setAlignment(Layout.Alignment.ALIGN_NORMAL);
                if (z) {
                    alignment2.setIncludePad(false);
                    if (Build.VERSION.SDK_INT >= 28) {
                        alignment2.setUseLineSpacingFromFallbacks(false);
                    }
                }
                return alignment2.build();
            }
        }
        return staticLayoutBuild;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(17:878|979|879|880|882|(1:884)(12:886|(1:888)|977|889|892|(1:894)(1:896)|895|897|(4:899|(0)(1:902)|(2:911|1012)(5:991|907|(1:909)|911|1012)|912)(1:903)|904|(2:911|1012)(0)|912)|885|977|889|892|(0)(0)|895|897|(0)(0)|904|(0)(0)|912) */
    /* JADX WARN: Can't wrap try/catch for region: R(47:705|(1:707)|708|(1:710)(1:711)|712|(1:714)(1:715)|716|(1:718)|(1:720)|(1:726)(1:725)|727|(1:735)(1:734)|736|(2:738|(2:(1:743)|744)(1:741))(2:745|(7:747|(1:749)(1:750)|751|(1:753)(1:754)|755|(1:757)(1:758)|759))|760|(3:762|(1:764)(1:(1:767)(1:768))|765)(1:769)|770|(1:772)(2:774|(1:776)(28:777|778|(6:780|(1:802)(9:786|(1:788)(1:790)|789|791|(1:793)(1:795)|794|796|(1:798)(1:799)|800)|801|803|(2:805|(1:(2:808|(1:811))(1:812))(1:813))|814)(3:815|(2:817|1006)(8:818|983|819|(1:827)(1:823)|824|828|(1:830)(1:831)|832)|956)|833|987|834|(1:838)|842|981|846|850|(1:852)(17:854|(1:856)|857|(1:859)|860|(1:862)|863|(3:865|(7:989|867|868|985|869|1010|873)|1008)|874|(6:876|(17:878|979|879|880|882|(1:884)(12:886|(1:888)|977|889|892|(1:894)(1:896)|895|897|(4:899|(0)(1:902)|(2:911|1012)(5:991|907|(1:909)|911|1012)|912)(1:903)|904|(2:911|1012)(0)|912)|885|977|889|892|(0)(0)|895|897|(0)(0)|904|(0)(0)|912)|1011|913|(1:(1:916))(2:(1:918)|919)|920)(3:921|(5:923|(1:925)(1:927)|926|(1:929)(1:930)|931)(1:932)|933)|934|(3:936|(1:938)(1:939)|940)|941|(5:945|(1:947)(4:950|(1:952)|949|953)|948|949|953)|954|1007|956)|853|857|(0)|860|(0)|863|(0)|874|(0)(0)|934|(0)|941|(6:943|945|(0)(0)|948|949|953)|954|1007|956))|773|778|(0)(0)|833|987|834|(2:836|838)|842|981|846|850|(0)(0)|853|857|(0)|860|(0)|863|(0)|874|(0)(0)|934|(0)|941|(0)|954|1007|956|703) */
    /* JADX WARN: Code restructure failed: missing block: B:840:0x0588, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:843:0x058c, code lost:
    
        if (r3 == 0) goto L844;
     */
    /* JADX WARN: Code restructure failed: missing block: B:844:0x058e, code lost:
    
        r36.textXOffset = r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:845:0x0592, code lost:
    
        org.telegram.messenger.FileLog.m1048e(r0);
        r13 = 0.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:848:0x059f, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:849:0x05a0, code lost:
    
        org.telegram.messenger.FileLog.m1048e(r0);
        r0 = 0.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:891:0x063f, code lost:
    
        r6 = 0.0f;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:596:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:598:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:602:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:603:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:606:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:607:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:611:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:652:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:655:0x0253  */
    /* JADX WARN: Removed duplicated region for block: B:657:0x0259  */
    /* JADX WARN: Removed duplicated region for block: B:663:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:664:0x026f  */
    /* JADX WARN: Removed duplicated region for block: B:666:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:667:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:669:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:670:0x027b  */
    /* JADX WARN: Removed duplicated region for block: B:673:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:678:0x029f  */
    /* JADX WARN: Removed duplicated region for block: B:680:0x02a3  */
    /* JADX WARN: Removed duplicated region for block: B:697:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:698:0x02f4  */
    /* JADX WARN: Removed duplicated region for block: B:705:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:780:0x0446  */
    /* JADX WARN: Removed duplicated region for block: B:815:0x04fc  */
    /* JADX WARN: Removed duplicated region for block: B:852:0x05a8  */
    /* JADX WARN: Removed duplicated region for block: B:854:0x05af  */
    /* JADX WARN: Removed duplicated region for block: B:859:0x05c2  */
    /* JADX WARN: Removed duplicated region for block: B:862:0x05c7  */
    /* JADX WARN: Removed duplicated region for block: B:865:0x05df  */
    /* JADX WARN: Removed duplicated region for block: B:876:0x0608  */
    /* JADX WARN: Removed duplicated region for block: B:894:0x0649  */
    /* JADX WARN: Removed duplicated region for block: B:896:0x0650  */
    /* JADX WARN: Removed duplicated region for block: B:899:0x065e  */
    /* JADX WARN: Removed duplicated region for block: B:903:0x0673  */
    /* JADX WARN: Removed duplicated region for block: B:906:0x0688 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:911:0x0697 A[PHI: r30
  0x0697: PHI (r30v5 int) = (r30v4 int), (r30v4 int), (r30v4 int), (r30v6 int) binds: [B:905:0x0686, B:906:0x0688, B:908:0x0690, B:909:0x0692] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:921:0x06eb  */
    /* JADX WARN: Removed duplicated region for block: B:936:0x0733  */
    /* JADX WARN: Removed duplicated region for block: B:943:0x0760  */
    /* JADX WARN: Removed duplicated region for block: B:947:0x0768  */
    /* JADX WARN: Removed duplicated region for block: B:950:0x0770  */
    /* JADX WARN: Removed duplicated region for block: B:959:0x07a6  */
    /* JADX WARN: Removed duplicated region for block: B:970:0x07cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void generateLayout(org.telegram.tgnet.TLRPC.User r37) {
        /*
            Method dump skipped, instruction units count: 2009
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.generateLayout(org.telegram.tgnet.TLRPC$User):void");
    }

    /* JADX INFO: renamed from: org.telegram.messenger.MessageObject$2 */
    /* JADX INFO: loaded from: classes5.dex */
    public class C27792 extends CharacterStyle {
        public C27792() {
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(android.text.TextPaint textPaint) {
            textPaint.setColor(((android.text.TextPaint) Theme.chat_msgTextPaint).linkColor);
        }
    }

    public int textHeightCached() {
        RichMessageLayout richMessageLayout = this.richLayout;
        if (richMessageLayout != null) {
            return richMessageLayout.getHeight();
        }
        Integer num = this.cachedTextHeight;
        if (num != null) {
            return num.intValue();
        }
        if (this.textLayoutBlocks == null) {
            this.cachedTextHeight = 0;
            return 0;
        }
        int iHeightCollapsed = 0;
        for (int i = 0; i < this.textLayoutBlocks.size(); i++) {
            iHeightCollapsed += this.textLayoutBlocks.get(i).padTop + this.textLayoutBlocks.get(i).heightCollapsed() + this.textLayoutBlocks.get(i).padBottom;
        }
        this.cachedTextHeight = Integer.valueOf(iHeightCollapsed);
        return iHeightCollapsed;
    }

    public int textHeight() {
        RichMessageLayout richMessageLayout = this.richLayout;
        if (richMessageLayout != null) {
            return richMessageLayout.getHeight();
        }
        if (this.textLayoutBlocks == null) {
            return 0;
        }
        int iHeight = 0;
        for (int i = 0; i < this.textLayoutBlocks.size(); i++) {
            iHeight += this.textLayoutBlocks.get(i).padTop + this.textLayoutBlocks.get(i).height() + this.textLayoutBlocks.get(i).padBottom;
        }
        return iHeight;
    }

    public int textHeight(ChatMessageCell.TransitionParams transitionParams) {
        if (this.textLayoutBlocks == null) {
            return 0;
        }
        int iHeight = 0;
        for (int i = 0; i < this.textLayoutBlocks.size(); i++) {
            iHeight += this.textLayoutBlocks.get(i).padTop + this.textLayoutBlocks.get(i).height(transitionParams) + this.textLayoutBlocks.get(i).padBottom;
        }
        return iHeight;
    }

    public static class TextLayoutBlocks {
        public boolean hasCode;
        public boolean hasCodeAtBottom;
        public boolean hasCodeAtTop;
        public boolean hasQuote;
        public boolean hasQuoteAtBottom;
        public boolean hasRtl;
        public boolean hasSingleCode;
        public boolean hasSingleQuote;
        public int lastLineWidth;
        public final CharSequence text;
        public final ArrayList<TextLayoutBlock> textLayoutBlocks = new ArrayList<>();
        public int textWidth;
        public float textXOffset;

        public int textHeight() {
            int iHeight = 0;
            for (int i = 0; i < this.textLayoutBlocks.size(); i++) {
                iHeight += this.textLayoutBlocks.get(i).padTop + this.textLayoutBlocks.get(i).height() + this.textLayoutBlocks.get(i).padBottom;
            }
            return iHeight;
        }

        public int textHeight(ChatMessageCell.TransitionParams transitionParams) {
            int iHeight = 0;
            for (int i = 0; i < this.textLayoutBlocks.size(); i++) {
                iHeight += this.textLayoutBlocks.get(i).padTop + this.textLayoutBlocks.get(i).height(transitionParams) + this.textLayoutBlocks.get(i).padBottom;
            }
            return iHeight;
        }

        public void bounceFrom(TextLayoutBlocks textLayoutBlocks) {
            if (textLayoutBlocks == null) {
                return;
            }
            for (int i = 0; i < Math.min(this.textLayoutBlocks.size(), textLayoutBlocks.textLayoutBlocks.size()); i++) {
                this.textLayoutBlocks.get(i).collapsedBounce = textLayoutBlocks.textLayoutBlocks.get(i).collapsedBounce;
            }
        }

        /* JADX WARN: Can't wrap try/catch for region: R(16:629|715|630|631|633|(1:635)(11:637|(1:639)|713|640|643|(1:645)(1:647)|646|648|(1:654)(1:653)|(1:750)(3:719|657|(2:659|748)(1:751))|660)|636|713|640|643|(0)(0)|646|648|(2:650|654)(0)|(1:750)(1:749)|660) */
        /* JADX WARN: Can't wrap try/catch for region: R(44:490|(1:492)|493|(1:495)(1:496)|497|(1:499)(1:500)|501|(1:503)|(1:505)|(1:511)(1:510)|512|(2:514|(2:(1:519)|520)(1:517))(2:521|(7:523|(1:525)(1:526)|527|(1:529)(1:530)|531|(1:533)(1:534)|535))|536|(3:538|(1:540)(2:542|(1:544)(1:545))|541)(1:546)|547|(1:549)(1:(1:552)(27:553|554|(3:556|(1:568)(4:562|(1:564)(1:566)|565|567)|569)(3:570|(2:572|742)(6:573|727|574|(1:582)(1:578)|579|583)|706)|584|(1:590)|591|723|592|(1:596)|600|731|604|608|(1:610)|611|(1:613)|614|(3:616|(7:729|618|619|725|620|746|624)|744)|625|(6:627|(16:629|715|630|631|633|(1:635)(11:637|(1:639)|713|640|643|(1:645)(1:647)|646|648|(1:654)(1:653)|(1:750)(3:719|657|(2:659|748)(1:751))|660)|636|713|640|643|(0)(0)|646|648|(2:650|654)(0)|(1:750)(1:749)|660)|747|661|(1:(1:664))(2:(1:666)|667)|668)(3:669|(5:671|(1:673)(1:675)|674|(1:677)(1:678)|679)(1:680)|681)|682|(3:684|(1:686)(1:687)|688)|689|(5:695|(1:697)(4:700|(1:702)|699|703)|698|699|703)|704|743|706))|550|554|(0)(0)|584|(3:586|588|590)|591|723|592|(2:594|596)|600|731|604|608|(0)|611|(0)|614|(0)|625|(0)(0)|682|(0)|689|(7:691|693|695|(0)(0)|698|699|703)|704|743|706|488) */
        /* JADX WARN: Code restructure failed: missing block: B:598:0x044d, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:601:0x0451, code lost:
        
            if (r12 == 0) goto L602;
         */
        /* JADX WARN: Code restructure failed: missing block: B:602:0x0453, code lost:
        
            r32.textXOffset = r36;
         */
        /* JADX WARN: Code restructure failed: missing block: B:603:0x0457, code lost:
        
            org.telegram.messenger.FileLog.m1048e(r0);
            r10 = 0.0f;
         */
        /* JADX WARN: Code restructure failed: missing block: B:606:0x0464, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:607:0x0465, code lost:
        
            org.telegram.messenger.FileLog.m1048e(r0);
            r0 = 0.0f;
         */
        /* JADX WARN: Code restructure failed: missing block: B:642:0x04f0, code lost:
        
            r7 = 0.0f;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:470:0x01c3  */
        /* JADX WARN: Removed duplicated region for block: B:473:0x01cd  */
        /* JADX WARN: Removed duplicated region for block: B:475:0x01d4  */
        /* JADX WARN: Removed duplicated region for block: B:481:0x01eb  */
        /* JADX WARN: Removed duplicated region for block: B:486:0x01f7  */
        /* JADX WARN: Removed duplicated region for block: B:490:0x021d  */
        /* JADX WARN: Removed duplicated region for block: B:556:0x0310  */
        /* JADX WARN: Removed duplicated region for block: B:570:0x0387  */
        /* JADX WARN: Removed duplicated region for block: B:610:0x0473  */
        /* JADX WARN: Removed duplicated region for block: B:613:0x0478  */
        /* JADX WARN: Removed duplicated region for block: B:616:0x0492  */
        /* JADX WARN: Removed duplicated region for block: B:627:0x04ba  */
        /* JADX WARN: Removed duplicated region for block: B:645:0x04fa  */
        /* JADX WARN: Removed duplicated region for block: B:647:0x0501  */
        /* JADX WARN: Removed duplicated region for block: B:654:0x0521  */
        /* JADX WARN: Removed duplicated region for block: B:669:0x058d  */
        /* JADX WARN: Removed duplicated region for block: B:684:0x05d1  */
        /* JADX WARN: Removed duplicated region for block: B:697:0x0606  */
        /* JADX WARN: Removed duplicated region for block: B:700:0x060e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public TextLayoutBlocks(org.telegram.messenger.MessageObject r33, java.lang.CharSequence r34, android.text.TextPaint r35, int r36) {
            /*
                Method dump skipped, instruction units count: 1605
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.TextLayoutBlocks.<init>(org.telegram.messenger.MessageObject, java.lang.CharSequence, android.text.TextPaint, int):void");
        }

        /* JADX INFO: renamed from: org.telegram.messenger.MessageObject$TextLayoutBlocks$1 */
        /* JADX INFO: loaded from: classes5.dex */
        public class C27801 extends CharacterStyle {
            public C27801() {
            }

            @Override // android.text.style.CharacterStyle
            public void updateDrawState(android.text.TextPaint textPaint) {
                textPaint.setColor(((android.text.TextPaint) Theme.chat_msgTextPaint).linkColor);
            }
        }
    }

    public boolean isOut() {
        return this.messageOwner.out;
    }

    public boolean isOutOwner() {
        boolean z = true;
        if (this.previewForward) {
            return true;
        }
        Boolean bool = this.isOutOwnerCached;
        if (bool != null) {
            return bool.booleanValue();
        }
        long clientUserId = UserConfig.getInstance(this.currentAccount).getClientUserId();
        if (this.isSaved || getDialogId() == clientUserId) {
            TLRPC.MessageFwdHeader messageFwdHeader = this.messageOwner.fwd_from;
            if (messageFwdHeader != null) {
                TLRPC.Peer peer = messageFwdHeader.from_id;
                if ((peer == null || peer.user_id != clientUserId) && !messageFwdHeader.saved_out) {
                    z = false;
                }
                this.isOutOwnerCached = Boolean.valueOf(z);
                return z;
            }
            this.isOutOwnerCached = Boolean.TRUE;
            return true;
        }
        TLRPC.Peer peer2 = this.messageOwner.peer_id;
        TLRPC.Chat chat = null;
        if (peer2 != null) {
            long j = peer2.channel_id;
            if (j != 0) {
                chat = getChat(null, null, j);
            }
        }
        TLRPC.Message message = this.messageOwner;
        if (message.out) {
            TLRPC.Peer peer3 = message.from_id;
            if ((peer3 instanceof TLRPC.TL_peerUser) || ((peer3 instanceof TLRPC.TL_peerChannel) && !ChatObject.isChannelAndNotMegaGroup(chat))) {
                TLRPC.Message message2 = this.messageOwner;
                if (!message2.post) {
                    TLRPC.MessageFwdHeader messageFwdHeader2 = message2.fwd_from;
                    if (messageFwdHeader2 == null) {
                        this.isOutOwnerCached = Boolean.TRUE;
                        return true;
                    }
                    TLRPC.Peer peer4 = messageFwdHeader2.saved_from_peer;
                    if (peer4 != null && peer4.user_id != clientUserId) {
                        z = false;
                    }
                    this.isOutOwnerCached = Boolean.valueOf(z);
                    return z;
                }
            }
        }
        this.isOutOwnerCached = Boolean.FALSE;
        return false;
    }

    public boolean needDrawAvatar() {
        TLRPC.MessageFwdHeader messageFwdHeader;
        TLRPC.Chat chat;
        if (this.type == 27) {
            return false;
        }
        if (this.isRepostPreview || this.isSaved || this.forceAvatar || this.customAvatarDrawable != null || this.searchType != 0) {
            return true;
        }
        return !isSponsored() && (isFromUser() || isFromGroup() || ((getDialogId() > 0L ? 1 : (getDialogId() == 0L ? 0 : -1)) >= 0 ? (getDialogId() > UserObject.VERIFY ? 1 : (getDialogId() == UserObject.VERIFY ? 0 : -1)) == 0 : !((chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-getDialogId()))) == null || !chat.signature_profiles)) || this.eventId != 0 || !((messageFwdHeader = this.messageOwner.fwd_from) == null || messageFwdHeader.saved_from_peer == null));
    }

    public boolean needDrawAvatarInternal() {
        TLRPC.Message message;
        TLRPC.MessageFwdHeader messageFwdHeader;
        TLRPC.Chat chat;
        if (this.isRepostPreview || this.isSaved || this.forceAvatar || this.customAvatarDrawable != null || (((message = this.messageOwner) != null && message.guestchat_via_from != null) || this.searchType != 0)) {
            return true;
        }
        return !isSponsored() && ((isFromChat() && isFromUser()) || isFromGroup() || ((getDialogId() > 0L ? 1 : (getDialogId() == 0L ? 0 : -1)) >= 0 ? (getDialogId() > UserObject.VERIFY ? 1 : (getDialogId() == UserObject.VERIFY ? 0 : -1)) == 0 : !((chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-getDialogId()))) == null || !chat.signature_profiles)) || this.eventId != 0 || !((messageFwdHeader = this.messageOwner.fwd_from) == null || messageFwdHeader.saved_from_peer == null));
    }

    public boolean isFromChat() {
        TLRPC.Peer peer;
        if (getDialogId() == UserConfig.getInstance(this.currentAccount).clientUserId) {
            return true;
        }
        TLRPC.Peer peer2 = this.messageOwner.peer_id;
        TLRPC.Chat chat = null;
        if (peer2 != null) {
            long j = peer2.channel_id;
            if (j != 0) {
                chat = getChat(null, null, j);
            }
        }
        if (!(ChatObject.isChannel(chat) && chat.megagroup) && ((peer = this.messageOwner.peer_id) == null || peer.chat_id == 0)) {
            return (peer == null || peer.channel_id == 0 || chat == null || !chat.megagroup) ? false : true;
        }
        return true;
    }

    public static long getFromChatId(TLRPC.Message message) {
        return getPeerId(message.from_id);
    }

    public static long getObjectPeerId(TLObject tLObject) {
        if (tLObject == null) {
            return 0L;
        }
        if (tLObject instanceof TLRPC.Chat) {
            return -((TLRPC.Chat) tLObject).f1245id;
        }
        if (tLObject instanceof TLRPC.User) {
            return ((TLRPC.User) tLObject).f1407id;
        }
        return 0L;
    }

    public static long getPeerId(TLRPC.Peer peer) {
        long j;
        if (peer == null) {
            return 0L;
        }
        if (peer instanceof TLRPC.TL_peerChat) {
            j = peer.chat_id;
        } else if (peer instanceof TLRPC.TL_peerChannel) {
            j = peer.channel_id;
        } else {
            return peer.user_id;
        }
        return -j;
    }

    public static boolean peersEqual(TLRPC.InputPeer inputPeer, TLRPC.InputPeer inputPeer2) {
        if (inputPeer == null && inputPeer2 == null) {
            return true;
        }
        if (inputPeer != null && inputPeer2 != null) {
            if ((inputPeer instanceof TLRPC.TL_inputPeerChat) && (inputPeer2 instanceof TLRPC.TL_inputPeerChat)) {
                return inputPeer.chat_id == inputPeer2.chat_id;
            }
            if ((inputPeer instanceof TLRPC.TL_inputPeerChannel) && (inputPeer2 instanceof TLRPC.TL_inputPeerChannel)) {
                return inputPeer.channel_id == inputPeer2.channel_id;
            }
            if ((inputPeer instanceof TLRPC.TL_inputPeerUser) && (inputPeer2 instanceof TLRPC.TL_inputPeerUser)) {
                return inputPeer.user_id == inputPeer2.user_id;
            }
            if ((inputPeer instanceof TLRPC.TL_inputPeerSelf) && (inputPeer2 instanceof TLRPC.TL_inputPeerSelf)) {
                return true;
            }
        }
        return false;
    }

    public static boolean peersEqual(TLRPC.InputPeer inputPeer, TLRPC.Peer peer) {
        if (inputPeer == null && peer == null) {
            return true;
        }
        if (inputPeer != null && peer != null) {
            if ((inputPeer instanceof TLRPC.TL_inputPeerChat) && (peer instanceof TLRPC.TL_peerChat)) {
                return inputPeer.chat_id == peer.chat_id;
            }
            if ((inputPeer instanceof TLRPC.TL_inputPeerChannel) && (peer instanceof TLRPC.TL_peerChannel)) {
                return inputPeer.channel_id == peer.channel_id;
            }
            if ((inputPeer instanceof TLRPC.TL_inputPeerUser) && (peer instanceof TLRPC.TL_peerUser) && inputPeer.user_id == peer.user_id) {
                return true;
            }
        }
        return false;
    }

    public static boolean peersEqual(TLRPC.Peer peer, TLRPC.Peer peer2) {
        if (peer == null && peer2 == null) {
            return true;
        }
        if (peer != null && peer2 != null) {
            if ((peer instanceof TLRPC.TL_peerChat) && (peer2 instanceof TLRPC.TL_peerChat)) {
                return peer.chat_id == peer2.chat_id;
            }
            if ((peer instanceof TLRPC.TL_peerChannel) && (peer2 instanceof TLRPC.TL_peerChannel)) {
                return peer.channel_id == peer2.channel_id;
            }
            if ((peer instanceof TLRPC.TL_peerUser) && (peer2 instanceof TLRPC.TL_peerUser) && peer.user_id == peer2.user_id) {
                return true;
            }
        }
        return false;
    }

    public static boolean peersEqual(TLRPC.Chat chat, TLRPC.Peer peer) {
        if (chat == null && peer == null) {
            return true;
        }
        if (chat != null && peer != null) {
            if (ChatObject.isChannel(chat) && (peer instanceof TLRPC.TL_peerChannel)) {
                return chat.f1245id == peer.channel_id;
            }
            if (!ChatObject.isChannel(chat) && (peer instanceof TLRPC.TL_peerChat) && chat.f1245id == peer.chat_id) {
                return true;
            }
        }
        return false;
    }

    public long getFromChatId() {
        return getFromChatId(this.messageOwner);
    }

    public long getChatId() {
        TLRPC.Peer peer = this.messageOwner.peer_id;
        if (peer instanceof TLRPC.TL_peerChat) {
            return peer.chat_id;
        }
        if (peer instanceof TLRPC.TL_peerChannel) {
            return peer.channel_id;
        }
        return 0L;
    }

    public TLRPC.Peer getFromPeer() {
        TLRPC.Message message = this.messageOwner;
        if (message != null) {
            return message.from_id;
        }
        return null;
    }

    public TLObject getFromPeerObject() {
        TLRPC.Message message = this.messageOwner;
        if (message == null) {
            return null;
        }
        TLRPC.Peer peer = message.from_id;
        if ((peer instanceof TLRPC.TL_peerChannel_layer131) || (peer instanceof TLRPC.TL_peerChannel)) {
            return MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(this.messageOwner.from_id.channel_id));
        }
        if ((peer instanceof TLRPC.TL_peerUser_layer131) || (peer instanceof TLRPC.TL_peerUser)) {
            return MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.messageOwner.from_id.user_id));
        }
        if ((peer instanceof TLRPC.TL_peerChat_layer131) || (peer instanceof TLRPC.TL_peerChat)) {
            return MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(this.messageOwner.from_id.chat_id));
        }
        return null;
    }

    public TLObject getPeerObject() {
        TLRPC.Message message = this.messageOwner;
        if (message == null) {
            return null;
        }
        TLRPC.Peer peer = message.peer_id;
        if ((peer instanceof TLRPC.TL_peerChannel_layer131) || (peer instanceof TLRPC.TL_peerChannel)) {
            return MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(this.messageOwner.peer_id.channel_id));
        }
        if ((peer instanceof TLRPC.TL_peerUser_layer131) || (peer instanceof TLRPC.TL_peerUser)) {
            return MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.messageOwner.peer_id.user_id));
        }
        if ((peer instanceof TLRPC.TL_peerChat_layer131) || (peer instanceof TLRPC.TL_peerChat)) {
            return MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(this.messageOwner.peer_id.chat_id));
        }
        return null;
    }

    public static String getPeerObjectName(TLObject tLObject) {
        if (tLObject instanceof TLRPC.User) {
            return UserObject.getUserName((TLRPC.User) tLObject);
        }
        if (tLObject instanceof TLRPC.Chat) {
            return ((TLRPC.Chat) tLObject).title;
        }
        return "DELETED";
    }

    public boolean isFromUser() {
        TLRPC.Message message = this.messageOwner;
        return (message.from_id instanceof TLRPC.TL_peerUser) && !message.post;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isFromChannel() {
        /*
            r8 = this;
            org.telegram.tgnet.TLRPC$Message r0 = r8.messageOwner
            org.telegram.tgnet.TLRPC$Peer r0 = r0.peer_id
            r1 = 0
            r3 = 0
            if (r0 == 0) goto L14
            long r4 = r0.channel_id
            int r0 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r0 == 0) goto L14
            org.telegram.tgnet.TLRPC$Chat r0 = r8.getChat(r3, r3, r4)
            goto L15
        L14:
            r0 = r3
        L15:
            org.telegram.tgnet.TLRPC$Message r4 = r8.messageOwner
            org.telegram.tgnet.TLRPC$Peer r4 = r4.peer_id
            boolean r4 = r4 instanceof org.telegram.tgnet.TLRPC.TL_peerChannel
            r5 = 1
            if (r4 == 0) goto L25
            boolean r0 = org.telegram.messenger.ChatObject.isChannelAndNotMegaGroup(r0)
            if (r0 == 0) goto L25
            return r5
        L25:
            org.telegram.tgnet.TLRPC$Message r0 = r8.messageOwner
            org.telegram.tgnet.TLRPC$Peer r0 = r0.from_id
            if (r0 == 0) goto L35
            long r6 = r0.channel_id
            int r0 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r0 == 0) goto L35
            org.telegram.tgnet.TLRPC$Chat r3 = r8.getChat(r3, r3, r6)
        L35:
            org.telegram.tgnet.TLRPC$Message r8 = r8.messageOwner
            org.telegram.tgnet.TLRPC$Peer r8 = r8.from_id
            boolean r8 = r8 instanceof org.telegram.tgnet.TLRPC.TL_peerChannel
            if (r8 == 0) goto L44
            boolean r8 = org.telegram.messenger.ChatObject.isChannelAndNotMegaGroup(r3)
            if (r8 == 0) goto L44
            return r5
        L44:
            r8 = 0
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.isFromChannel():boolean");
    }

    public boolean isFromGroup() {
        TLRPC.Message message = this.messageOwner;
        if (message == null) {
            return false;
        }
        TLRPC.Peer peer = message.peer_id;
        TLRPC.Chat chat = null;
        if (peer != null) {
            long j = peer.channel_id;
            if (j != 0) {
                chat = getChat(null, null, j);
            }
        }
        return (this.messageOwner.from_id instanceof TLRPC.TL_peerChannel) && ChatObject.isChannel(chat) && chat.megagroup;
    }

    public boolean isForwardedChannelPost() {
        TLRPC.MessageFwdHeader messageFwdHeader;
        TLRPC.Message message = this.messageOwner;
        TLRPC.Peer peer = message.from_id;
        if (!(peer instanceof TLRPC.TL_peerChannel) || (messageFwdHeader = message.fwd_from) == null || messageFwdHeader.channel_post == 0) {
            return false;
        }
        TLRPC.Peer peer2 = messageFwdHeader.saved_from_peer;
        return (peer2 instanceof TLRPC.TL_peerChannel) && peer.channel_id == peer2.channel_id;
    }

    public boolean isUnread() {
        TLRPC.Message message = this.messageOwner;
        return message != null && message.unread;
    }

    public boolean isEdited() {
        TLRPC.Message message = this.messageOwner;
        return (message == null || (message.flags & 32768) == 0 || message.edit_date == 0 || message.edit_hide) ? false : true;
    }

    public boolean isContentUnread() {
        return this.messageOwner.media_unread;
    }

    public void setIsRead() {
        this.messageOwner.unread = false;
    }

    public static int getUnreadFlags(TLRPC.Message message) {
        int i = !message.unread ? 1 : 0;
        return !message.media_unread ? i | 2 : i;
    }

    public void setContentIsRead() {
        this.messageOwner.media_unread = false;
    }

    public int getId() {
        return this.messageOwner.f1271id;
    }

    public int getRealId() {
        TLRPC.Message message = this.messageOwner;
        int i = message.realId;
        return i != 0 ? i : message.f1271id;
    }

    public static long getMessageSize(TLRPC.Message message) {
        return getMediaSize(getMedia(message));
    }

    public static long getMediaSize(TLRPC.MessageMedia messageMedia) {
        TLRPC.Document document;
        TLRPC.WebPage webPage;
        if ((messageMedia instanceof TLRPC.TL_messageMediaWebPage) && (webPage = messageMedia.webpage) != null) {
            document = webPage.document;
        } else if (messageMedia instanceof TLRPC.TL_messageMediaGame) {
            document = messageMedia.game.document;
        } else {
            document = messageMedia != null ? messageMedia.document : null;
        }
        if (document != null) {
            return document.size;
        }
        return 0L;
    }

    public long getSize() {
        VideoPlayer.VideoUri videoUri = this.highestQuality;
        if (videoUri != null) {
            return videoUri.document.size;
        }
        VideoPlayer.VideoUri videoUri2 = this.thumbQuality;
        if (videoUri2 != null) {
            return videoUri2.document.size;
        }
        VideoPlayer.VideoUri videoUri3 = this.cachedQuality;
        if (videoUri3 != null) {
            return videoUri3.document.size;
        }
        return getMessageSize(this.messageOwner);
    }

    public static void fixMessagePeer(ArrayList<TLRPC.Message> arrayList, long j) {
        if (arrayList == null || arrayList.isEmpty() || j == 0) {
            return;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            TLRPC.Message message = arrayList.get(i);
            if (message instanceof TLRPC.TL_messageEmpty) {
                TLRPC.TL_peerChannel tL_peerChannel = new TLRPC.TL_peerChannel();
                message.peer_id = tL_peerChannel;
                tL_peerChannel.channel_id = j;
            }
        }
    }

    public long getChannelId() {
        return getChannelId(this.messageOwner);
    }

    public static long getChannelId(TLRPC.Message message) {
        TLRPC.Peer peer = message.peer_id;
        if (peer != null) {
            return peer.channel_id;
        }
        return 0L;
    }

    public static long getChatId(TLRPC.Message message) {
        if (message == null) {
            return 0L;
        }
        TLRPC.Peer peer = message.peer_id;
        if (peer instanceof TLRPC.TL_peerChat) {
            return peer.chat_id;
        }
        if (peer instanceof TLRPC.TL_peerChannel) {
            return peer.channel_id;
        }
        return 0L;
    }

    public static boolean shouldEncryptPhotoOrVideo(int i, TLRPC.Message message) {
        int i2;
        if ((message == null || message.media == null || !((isVoiceDocument(getDocument(message)) || isRoundVideoMessage(message)) && message.media.ttl_seconds == Integer.MAX_VALUE)) && !(getMedia(message) instanceof TLRPC.TL_messageMediaPaidMedia)) {
            return message instanceof TLRPC.TL_message_secret ? ((getMedia(message) instanceof TLRPC.TL_messageMediaPhoto) || isVideoMessage(message)) && (i2 = message.ttl) > 0 && i2 <= 60 : ((getMedia(message) instanceof TLRPC.TL_messageMediaPhoto) || (getMedia(message) instanceof TLRPC.TL_messageMediaDocument)) && getMedia(message).ttl_seconds != 0;
        }
        return true;
    }

    public boolean shouldEncryptPhotoOrVideo() {
        return shouldEncryptPhotoOrVideo(this.currentAccount, this.messageOwner);
    }

    public static boolean isSecretPhotoOrVideo(TLRPC.Message message) {
        int i;
        return message instanceof TLRPC.TL_message_secret ? ((getMedia(message) instanceof TLRPC.TL_messageMediaPhoto) || isRoundVideoMessage(message) || isVideoMessage(message)) && (i = message.ttl) > 0 && i <= 60 : (message instanceof TLRPC.TL_message) && ((getMedia(message) instanceof TLRPC.TL_messageMediaPhoto) || (getMedia(message) instanceof TLRPC.TL_messageMediaDocument)) && getMedia(message).ttl_seconds != 0;
    }

    public static boolean isSecretMedia(TLRPC.Message message) {
        return message instanceof TLRPC.TL_message_secret ? ((getMedia(message) instanceof TLRPC.TL_messageMediaPhoto) || isRoundVideoMessage(message) || isVideoMessage(message)) && getMedia(message).ttl_seconds != 0 : (message instanceof TLRPC.TL_message) && ((getMedia(message) instanceof TLRPC.TL_messageMediaPhoto) || (getMedia(message) instanceof TLRPC.TL_messageMediaDocument)) && getMedia(message).ttl_seconds != 0;
    }

    public boolean needDrawBluredPreview() {
        if (this.isRepostPreview) {
            return false;
        }
        if (hasExtendedMediaPreview()) {
            return true;
        }
        TLRPC.Message message = this.messageOwner;
        if (!(message instanceof TLRPC.TL_message_secret)) {
            return (message instanceof TLRPC.TL_message) && getMedia(message) != null && getMedia(this.messageOwner).ttl_seconds != 0 && ((getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaPhoto) || (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaDocument));
        }
        int iMax = Math.max(message.ttl, getMedia(message).ttl_seconds);
        return iMax > 0 && ((((getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaPhoto) || isVideo() || isGif()) && iMax <= 60) || isRoundVideo());
    }

    public boolean isSecret() {
        return this.messageOwner instanceof TLRPC.TL_message_secret;
    }

    public boolean isSecretMedia() {
        int i;
        TLRPC.Message message = this.messageOwner;
        return message instanceof TLRPC.TL_message_secret ? (((getMedia(message) instanceof TLRPC.TL_messageMediaPhoto) || isGif()) && (i = this.messageOwner.ttl) > 0 && i <= 60) || isVoice() || isRoundVideo() || isVideo() : (message instanceof TLRPC.TL_message) && getMedia(message) != null && getMedia(this.messageOwner).ttl_seconds != 0 && ((getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaPhoto) || (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaDocument));
    }

    public static void setUnreadFlags(TLRPC.Message message, int i) {
        message.unread = (i & 1) == 0;
        message.media_unread = (i & 2) == 0;
    }

    public static boolean isUnread(TLRPC.Message message) {
        return message.unread;
    }

    public static boolean isContentUnread(TLRPC.Message message) {
        return message.media_unread;
    }

    public boolean isSavedFromMegagroup() {
        TLRPC.Peer peer;
        TLRPC.MessageFwdHeader messageFwdHeader = this.messageOwner.fwd_from;
        if (messageFwdHeader == null || (peer = messageFwdHeader.saved_from_peer) == null || peer.channel_id == 0) {
            return false;
        }
        return ChatObject.isMegagroup(MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(this.messageOwner.fwd_from.saved_from_peer.channel_id)));
    }

    public static boolean isOut(TLRPC.Message message) {
        return message.out;
    }

    public long getDialogId() {
        return getDialogId(this.messageOwner);
    }

    public boolean canStreamVideo() {
        if (hasVideoQualities()) {
            return true;
        }
        TLRPC.Document document = getDocument();
        if (document != null && !(document instanceof TLRPC.TL_documentEncrypted)) {
            if (SharedConfig.streamAllVideo) {
                return true;
            }
            for (int i = 0; i < document.attributes.size(); i++) {
                TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
                if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                    return documentAttribute.supports_streaming;
                }
            }
            if (SharedConfig.streamMkv && "video/x-matroska".equals(document.mime_type)) {
                return true;
            }
        }
        return false;
    }

    public static long getDialogId(TLRPC.Message message) {
        TLRPC.Peer peer;
        if (message.dialog_id == 0 && (peer = message.peer_id) != null) {
            long j = peer.chat_id;
            if (j != 0) {
                message.dialog_id = -j;
            } else {
                long j2 = peer.channel_id;
                if (j2 != 0) {
                    message.dialog_id = -j2;
                } else if (message.from_id == null || isOut(message) || message.guestchat_via_from != null) {
                    message.dialog_id = message.peer_id.user_id;
                } else {
                    message.dialog_id = message.from_id.user_id;
                }
            }
        }
        return message.dialog_id;
    }

    public long getSavedDialogId() {
        return getSavedDialogId(UserConfig.getInstance(this.currentAccount).getClientUserId(), this.messageOwner);
    }

    public static long getSavedDialogId(long j, TLRPC.Message message) {
        TLRPC.Peer peer;
        TLRPC.Peer peer2 = message.saved_peer_id;
        if (peer2 != null) {
            long j2 = peer2.chat_id;
            if (j2 != 0) {
                return -j2;
            }
            long j3 = peer2.channel_id;
            return j3 != 0 ? -j3 : peer2.user_id;
        }
        if (message.from_id.user_id != j) {
            return 0L;
        }
        TLRPC.MessageFwdHeader messageFwdHeader = message.fwd_from;
        if (messageFwdHeader == null || (peer = messageFwdHeader.saved_from_peer) == null) {
            return ((messageFwdHeader == null || messageFwdHeader.from_id == null) && messageFwdHeader != null) ? UserObject.ANONYMOUS : j;
        }
        return DialogObject.getPeerDialogId(peer);
    }

    public static TLRPC.Peer getSavedDialogPeer(long j, TLRPC.Message message) {
        TLRPC.Peer peer;
        TLRPC.Peer peer2;
        TLRPC.Peer peer3 = message.saved_peer_id;
        if (peer3 != null) {
            return peer3;
        }
        TLRPC.Peer peer4 = message.peer_id;
        if (peer4 == null || peer4.user_id != j || (peer = message.from_id) == null || peer.user_id != j) {
            return null;
        }
        TLRPC.MessageFwdHeader messageFwdHeader = message.fwd_from;
        if (messageFwdHeader != null && (peer2 = messageFwdHeader.saved_from_peer) != null) {
            return peer2;
        }
        if (messageFwdHeader != null && messageFwdHeader.from_id != null) {
            TLRPC.TL_peerUser tL_peerUser = new TLRPC.TL_peerUser();
            tL_peerUser.user_id = j;
            return tL_peerUser;
        }
        if (messageFwdHeader != null) {
            TLRPC.TL_peerUser tL_peerUser2 = new TLRPC.TL_peerUser();
            tL_peerUser2.user_id = UserObject.ANONYMOUS;
            return tL_peerUser2;
        }
        TLRPC.TL_peerUser tL_peerUser3 = new TLRPC.TL_peerUser();
        tL_peerUser3.user_id = j;
        return tL_peerUser3;
    }

    public boolean canSetReaction() {
        TLRPC.Message message = this.messageOwner;
        if (message instanceof TLRPC.TL_messageService) {
            return message.reactions_are_possible;
        }
        return true;
    }

    public boolean isSending() {
        TLRPC.Message message = this.messageOwner;
        return message.send_state == 1 && message.f1271id < 0;
    }

    public boolean isEditing() {
        TLRPC.Message message = this.messageOwner;
        return message.send_state == 3 && message.f1271id > 0;
    }

    public boolean isEditingMedia() {
        boolean z = getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaPhoto;
        TLRPC.Message message = this.messageOwner;
        return z ? getMedia(message).photo.f1276id == 0 : (getMedia(message) instanceof TLRPC.TL_messageMediaDocument) && getMedia(this.messageOwner).document.dc_id == 0;
    }

    public boolean isSendError() {
        TLRPC.Message message = this.messageOwner;
        if (message.send_state == 2 && message.f1271id < 0) {
            return true;
        }
        if (!this.scheduled || message.f1271id <= 0) {
            return false;
        }
        return message.date < ConnectionsManager.getInstance(this.currentAccount).getCurrentTime() - (this.messageOwner.video_processing_pending ? 300 : 60);
    }

    public boolean isSent() {
        TLRPC.Message message = this.messageOwner;
        return message.send_state == 0 || message.f1271id > 0;
    }

    public int getSecretTimeLeft() {
        TLRPC.Message message = this.messageOwner;
        int i = message.ttl;
        int i2 = message.destroyTime;
        return i2 != 0 ? Math.max(0, i2 - ConnectionsManager.getInstance(this.currentAccount).getCurrentTime()) : i;
    }

    public CharSequence getSecretTimeString() {
        String str;
        if (!isSecretMedia()) {
            return null;
        }
        if (this.messageOwner.ttl == Integer.MAX_VALUE) {
            if (this.secretOnceSpan == null) {
                this.secretOnceSpan = new SpannableString(RegisterSpec.PREFIX);
                ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2797R.drawable.mini_viewonce);
                coloredImageSpan.setTranslateX(-AndroidUtilities.m1036dp(3.0f));
                coloredImageSpan.setWidth(AndroidUtilities.m1036dp(13.0f));
                CharSequence charSequence = this.secretOnceSpan;
                ((Spannable) charSequence).setSpan(coloredImageSpan, 0, charSequence.length(), 33);
            }
            return TextUtils.concat(this.secretOnceSpan, "1");
        }
        int secretTimeLeft = getSecretTimeLeft();
        if (secretTimeLeft < 60) {
            str = secretTimeLeft + "s";
        } else {
            str = (secretTimeLeft / 60) + "m";
        }
        if (this.secretPlaySpan == null) {
            this.secretPlaySpan = new SpannableString("p");
            ColoredImageSpan coloredImageSpan2 = new ColoredImageSpan(C2797R.drawable.play_mini_video);
            coloredImageSpan2.setTranslateX(AndroidUtilities.m1036dp(1.0f));
            coloredImageSpan2.setWidth(AndroidUtilities.m1036dp(13.0f));
            CharSequence charSequence2 = this.secretPlaySpan;
            ((Spannable) charSequence2).setSpan(coloredImageSpan2, 0, charSequence2.length(), 33);
        }
        return TextUtils.concat(this.secretPlaySpan, str);
    }

    public String getDocumentName() {
        return FileLoader.getDocumentFileName(getDocument());
    }

    public static boolean isWebM(TLRPC.Document document) {
        return document != null && "video/webm".equals(document.mime_type);
    }

    public static boolean isVideoSticker(TLRPC.Document document) {
        return document != null && isVideoStickerDocument(document);
    }

    public boolean isVideoSticker() {
        return getDocument() != null && isVideoStickerDocument(getDocument());
    }

    public static boolean isStickerDocument(TLRPC.Document document) {
        if (document != null) {
            for (int i = 0; i < document.attributes.size(); i++) {
                if (document.attributes.get(i) instanceof TLRPC.TL_documentAttributeSticker) {
                    return "image/webp".equals(document.mime_type) || "video/webm".equals(document.mime_type);
                }
            }
        }
        return false;
    }

    public static boolean isVideoStickerDocument(TLRPC.Document document) {
        if (document != null) {
            for (int i = 0; i < document.attributes.size(); i++) {
                TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
                if ((documentAttribute instanceof TLRPC.TL_documentAttributeSticker) || (documentAttribute instanceof TLRPC.TL_documentAttributeCustomEmoji)) {
                    return "video/webm".equals(document.mime_type);
                }
            }
        }
        return false;
    }

    public static boolean isStickerHasSet(TLRPC.Document document) {
        TLRPC.InputStickerSet inputStickerSet;
        if (document != null) {
            for (int i = 0; i < document.attributes.size(); i++) {
                TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
                if ((documentAttribute instanceof TLRPC.TL_documentAttributeSticker) && (inputStickerSet = documentAttribute.stickerset) != null && !(inputStickerSet instanceof TLRPC.TL_inputStickerSetEmpty)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isAnimatedStickerDocument(TLRPC.Document document, boolean z) {
        if (document != null && (("application/x-tgsticker".equals(document.mime_type) && !document.thumbs.isEmpty()) || "application/x-tgsdice".equals(document.mime_type))) {
            if (z) {
                return true;
            }
            int size = document.attributes.size();
            for (int i = 0; i < size; i++) {
                TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
                if (documentAttribute instanceof TLRPC.TL_documentAttributeSticker) {
                    return documentAttribute.stickerset instanceof TLRPC.TL_inputStickerSetShortName;
                }
                if (documentAttribute instanceof TLRPC.TL_documentAttributeCustomEmoji) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isAnyKindOfStickerOrEmoji(TLRPC.Document document) {
        if (document == null) {
            return false;
        }
        int size = document.attributes.size();
        for (int i = 0; i < size; i++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
            if ((documentAttribute instanceof TLRPC.TL_documentAttributeSticker) || (documentAttribute instanceof TLRPC.TL_documentAttributeCustomEmoji)) {
                return true;
            }
        }
        return false;
    }

    public static boolean canAutoplayAnimatedSticker(TLRPC.Document document) {
        return (isAnimatedStickerDocument(document, true) || isVideoStickerDocument(document)) && LiteMode.isEnabled(1);
    }

    public static boolean isMaskDocument(TLRPC.Document document) {
        if (document != null) {
            for (int i = 0; i < document.attributes.size(); i++) {
                TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
                if ((documentAttribute instanceof TLRPC.TL_documentAttributeSticker) && documentAttribute.mask) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isVoiceDocument(TLRPC.Document document) {
        if (document != null) {
            for (int i = 0; i < document.attributes.size(); i++) {
                TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
                if (documentAttribute instanceof TLRPC.TL_documentAttributeAudio) {
                    return documentAttribute.voice;
                }
            }
        }
        return false;
    }

    public static boolean isVoiceWebDocument(WebFile webFile) {
        return webFile != null && webFile.mime_type.equals("audio/ogg");
    }

    public static boolean isImageWebDocument(WebFile webFile) {
        return (webFile == null || isGifDocument(webFile) || !webFile.mime_type.startsWith("image/")) ? false : true;
    }

    public static boolean isVideoWebDocument(WebFile webFile) {
        return webFile != null && webFile.mime_type.startsWith("video/");
    }

    public static boolean isMusicDocument(TLRPC.Document document) {
        if (document != null) {
            for (int i = 0; i < document.attributes.size(); i++) {
                if (document.attributes.get(i) instanceof TLRPC.TL_documentAttributeAudio) {
                    return !r2.voice;
                }
            }
            if (!TextUtils.isEmpty(document.mime_type)) {
                String lowerCase = document.mime_type.toLowerCase();
                if (lowerCase.equals("audio/flac") || lowerCase.equals("audio/ogg") || lowerCase.equals("audio/opus") || lowerCase.equals("audio/x-opus+ogg") || lowerCase.equals("audio/wav") || lowerCase.equals("audio/x-wav")) {
                    return true;
                }
                return lowerCase.equals("application/octet-stream") && FileLoader.getDocumentFileName(document).endsWith(".opus");
            }
        }
        return false;
    }

    public static TLRPC.VideoSize getDocumentVideoThumb(TLRPC.Document document) {
        if (document == null || document.video_thumbs.isEmpty()) {
            return null;
        }
        return document.video_thumbs.get(0);
    }

    public static boolean isVideoDocument(TLRPC.Document document) {
        int iLastIndexOf;
        if (document == null) {
            return false;
        }
        String str = null;
        boolean z = false;
        int i = 0;
        int i2 = 0;
        boolean z2 = false;
        for (int i3 = 0; i3 < document.attributes.size(); i3++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i3);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                if (documentAttribute.round_message) {
                    return false;
                }
                i = documentAttribute.f1256w;
                i2 = documentAttribute.f1255h;
                z2 = true;
            } else if (documentAttribute instanceof TLRPC.TL_documentAttributeAnimated) {
                z = true;
            } else if (documentAttribute instanceof TLRPC.TL_documentAttributeFilename) {
                str = documentAttribute.file_name;
            }
        }
        if (str != null && (iLastIndexOf = str.lastIndexOf(".")) >= 0 && isV(str.substring(iLastIndexOf + 1))) {
            return false;
        }
        if (z && (i > 1280 || i2 > 1280)) {
            z = false;
        }
        if (SharedConfig.streamMkv && !z2 && "video/x-matroska".equals(document.mime_type)) {
            z2 = true;
        }
        return z2 && !z;
    }

    public static boolean isV(String str) {
        if (str == null) {
            return true;
        }
        switch (str.toLowerCase().hashCode()) {
            case -1535907675:
            case -1422950858:
            case -1253501876:
            case -907685685:
            case -788047292:
            case -338481545:
            case 3106:
            case 3184:
            case 3215:
            case 3401:
            case 3479:
            case 3494:
            case 3580:
            case 3581:
            case 3593:
            case 3632:
            case 3669:
            case 3756:
            case 3804:
            case 96400:
            case 96586:
            case 96796:
            case 96801:
            case 96894:
            case 97013:
            case 97300:
            case 97301:
            case 97543:
            case 98437:
            case 98472:
            case 98618:
            case 98689:
            case 98719:
            case 98789:
            case 98808:
            case 98819:
            case 99338:
            case 99351:
            case 99548:
            case 99556:
            case 99582:
            case 99640:
            case 99752:
            case 100208:
            case 100511:
            case 100542:
            case 100730:
            case 100882:
            case 100958:
            case 101460:
            case 101671:
            case 101854:
            case 102556:
            case 102572:
            case 103404:
            case 103438:
            case 103637:
            case 103649:
            case 104074:
            case 104269:
            case 104417:
            case 104430:
            case 104435:
            case 104474:
            case 104479:
            case 104582:
            case 104587:
            case 104987:
            case 105532:
            case 105543:
            case 105551:
            case 106202:
            case 106496:
            case 107141:
            case 107305:
            case 107932:
            case 107988:
            case 107989:
            case 108341:
            case 108382:
            case 108413:
            case 108419:
            case 108426:
            case 108430:
            case 108570:
            case 109824:
            case 109860:
            case 110754:
            case 110801:
            case 110834:
            case 110883:
            case 110968:
            case 110989:
            case 111052:
            case 111220:
            case 111265:
            case 111269:
            case 111390:
            case 111420:
            case 111482:
            case 111494:
            case 112185:
            case 112712:
            case 112788:
            case 112862:
            case 113115:
            case 113132:
            case 113291:
            case 113698:
            case 113700:
            case 113837:
            case 113854:
            case 114101:
            case 114130:
            case 114276:
            case 114381:
            case 114809:
            case 114922:
            case 114970:
            case 115161:
            case 115312:
            case 115639:
            case 116079:
            case 116537:
            case 116551:
            case 116609:
            case 117218:
            case 117537:
            case 117840:
            case 117938:
            case 118023:
            case 118026:
            case 118028:
            case 118439:
            case 118783:
            case 118807:
            case 118939:
            case 120703:
            case 3003834:
            case 3016404:
            case 3088960:
            case 3213227:
            case 3271912:
            case 3358271:
            case 3444044:
            case 3446979:
            case 3447940:
            case 3524225:
            case 3524692:
            case 3526257:
            case 3682393:
            case 35379135:
            case 114035747:
                break;
        }
        return true;
    }

    public TLRPC.Document getDocument() {
        VideoPlayer.VideoUri videoUri;
        TLRPC.Document document = this.emojiAnimatedSticker;
        if (document != null) {
            return document;
        }
        if (hasVideoQualities() && (videoUri = this.highestQuality) != null) {
            return videoUri.document;
        }
        return getDocument(this.messageOwner);
    }

    public TLRPC.Document getDocumentFast() {
        TLRPC.Document document = this.emojiAnimatedSticker;
        return document != null ? document : getDocument(this.messageOwner);
    }

    public static TLRPC.Document getDocument(TLRPC.Message message) {
        TLRPC.MessageMedia messageMedia;
        TLRPC.Document document;
        TL_iv.RichMessage richMessage;
        if (message != null && (richMessage = message.rich_message) != null) {
            return findVideo(richMessage);
        }
        if (getMedia(message) instanceof TLRPC.TL_messageMediaWebPage) {
            return getMedia(message).webpage.document;
        }
        if (getMedia(message) instanceof TLRPC.TL_messageMediaGame) {
            return getMedia(message).game.document;
        }
        if (getMedia(message) instanceof TLRPC.TL_messageMediaStory) {
            TL_stories.StoryItem storyItem = ((TLRPC.TL_messageMediaStory) getMedia(message)).storyItem;
            if (storyItem != null && (messageMedia = storyItem.media) != null && (document = messageMedia.document) != null) {
                return document;
            }
        } else if (getMedia(message) instanceof TLRPC.TL_messageMediaPaidMedia) {
            TLRPC.TL_messageMediaPaidMedia tL_messageMediaPaidMedia = (TLRPC.TL_messageMediaPaidMedia) getMedia(message);
            if (tL_messageMediaPaidMedia.extended_media.size() == 1 && (tL_messageMediaPaidMedia.extended_media.get(0) instanceof TLRPC.TL_messageExtendedMedia)) {
                return ((TLRPC.TL_messageExtendedMedia) tL_messageMediaPaidMedia.extended_media.get(0)).media.document;
            }
        }
        if (getMedia(message) != null) {
            return getMedia(message).document;
        }
        return null;
    }

    public TLRPC.Photo getPhoto() {
        return getPhoto(this.messageOwner);
    }

    public static TLRPC.Photo getPhoto(TLRPC.Message message) {
        TL_iv.RichMessage richMessage;
        if (message != null && (richMessage = message.rich_message) != null) {
            return findPhoto(richMessage);
        }
        if (getMedia(message) instanceof TLRPC.TL_messageMediaWebPage) {
            return getMedia(message).webpage.photo;
        }
        if (getMedia(message) != null) {
            return getMedia(message).photo;
        }
        return null;
    }

    public static TLRPC.Document findVideo(TL_iv.RichMessage richMessage) {
        if (richMessage == null) {
            return null;
        }
        return findVideo(richMessage.blocks, richMessage);
    }

    public static TLRPC.Document findVideo(ArrayList<TL_iv.PageBlock> arrayList, TL_iv.RichMessage richMessage) {
        if (arrayList != null && richMessage != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                TL_iv.PageBlock pageBlock = arrayList.get(i);
                i++;
                TL_iv.PageBlock pageBlock2 = pageBlock;
                if (pageBlock2 instanceof TL_iv.pageBlockVideo) {
                    return AndroidUtilities.findDocument(richMessage.documents, ((TL_iv.pageBlockVideo) pageBlock2).video_id);
                }
                if (pageBlock2 instanceof TL_iv.pageBlockCollage) {
                    return findVideo(((TL_iv.pageBlockCollage) pageBlock2).items, richMessage);
                }
                if (pageBlock2 instanceof TL_iv.pageBlockSlideshow) {
                    return findVideo(((TL_iv.pageBlockSlideshow) pageBlock2).items, richMessage);
                }
            }
        }
        return null;
    }

    public static TLRPC.Document findAudio(TL_iv.RichMessage richMessage) {
        if (richMessage == null) {
            return null;
        }
        return findAudio(richMessage.blocks, richMessage);
    }

    public static TLRPC.Document findAudio(ArrayList<TL_iv.PageBlock> arrayList, TL_iv.RichMessage richMessage) {
        if (arrayList != null && richMessage != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                TL_iv.PageBlock pageBlock = arrayList.get(i);
                i++;
                TL_iv.PageBlock pageBlock2 = pageBlock;
                if (pageBlock2 instanceof TL_iv.pageBlockAudio) {
                    return AndroidUtilities.findDocument(richMessage.documents, ((TL_iv.pageBlockAudio) pageBlock2).audio_id);
                }
                if (pageBlock2 instanceof TL_iv.pageBlockCollage) {
                    return findAudio(((TL_iv.pageBlockCollage) pageBlock2).items, richMessage);
                }
                if (pageBlock2 instanceof TL_iv.pageBlockSlideshow) {
                    return findAudio(((TL_iv.pageBlockSlideshow) pageBlock2).items, richMessage);
                }
            }
        }
        return null;
    }

    public static TLRPC.Photo findPhoto(TL_iv.RichMessage richMessage) {
        if (richMessage == null) {
            return null;
        }
        return findPhoto(richMessage.blocks, richMessage);
    }

    public static TLRPC.Photo findPhoto(ArrayList<TL_iv.PageBlock> arrayList, TL_iv.RichMessage richMessage) {
        if (arrayList != null && richMessage != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                TL_iv.PageBlock pageBlock = arrayList.get(i);
                i++;
                TL_iv.PageBlock pageBlock2 = pageBlock;
                if (pageBlock2 instanceof TL_iv.pageBlockPhoto) {
                    return AndroidUtilities.findPhoto(richMessage.photos, ((TL_iv.pageBlockPhoto) pageBlock2).photo_id);
                }
                if (pageBlock2 instanceof TL_iv.pageBlockCollage) {
                    return findPhoto(((TL_iv.pageBlockCollage) pageBlock2).items, richMessage);
                }
                if (pageBlock2 instanceof TL_iv.pageBlockSlideshow) {
                    return findPhoto(((TL_iv.pageBlockSlideshow) pageBlock2).items, richMessage);
                }
            }
        }
        return null;
    }

    public static boolean isStickerMessage(TLRPC.Message message) {
        return getMedia(message) != null && isStickerDocument(getMedia(message).document);
    }

    public static boolean isAnimatedStickerMessage(TLRPC.Message message) {
        boolean zIsEncryptedDialog = DialogObject.isEncryptedDialog(message.dialog_id);
        if ((!zIsEncryptedDialog || message.stickerVerified == 1) && getMedia(message) != null) {
            if (isAnimatedStickerDocument(getMedia(message).document, !zIsEncryptedDialog || message.out)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLocationMessage(TLRPC.Message message) {
        return (getMedia(message) instanceof TLRPC.TL_messageMediaGeo) || (getMedia(message) instanceof TLRPC.TL_messageMediaGeoLive) || (getMedia(message) instanceof TLRPC.TL_messageMediaVenue);
    }

    public static boolean isMaskMessage(TLRPC.Message message) {
        return getMedia(message) != null && isMaskDocument(getMedia(message).document);
    }

    public static boolean isMusicMessage(TLRPC.Message message) {
        if (getMedia(message) instanceof TLRPC.TL_messageMediaWebPage) {
            return isMusicDocument(getMedia(message).webpage.document);
        }
        return getMedia(message) != null && isMusicDocument(getMedia(message).document);
    }

    public static boolean isGifMessage(TLRPC.Message message) {
        if (getMedia(message) instanceof TLRPC.TL_messageMediaWebPage) {
            return isGifDocument(getMedia(message).webpage.document);
        }
        if (getMedia(message) != null) {
            if (isGifDocument(getMedia(message).document, message.grouped_id != 0)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRoundVideoMessage(TLRPC.Message message) {
        if (!(getMedia(message) instanceof TLRPC.TL_messageMediaWebPage) || getMedia(message).webpage == null) {
            return getMedia(message) != null && isRoundVideoDocument(getMedia(message).document);
        }
        return isRoundVideoDocument(getMedia(message).webpage.document);
    }

    public static boolean isPhoto(TLRPC.Message message) {
        TLRPC.MessageAction messageAction;
        TLRPC.Photo photo;
        TL_iv.RichMessage richMessage;
        if (message != null && (richMessage = message.rich_message) != null) {
            return findPhoto(richMessage) != null;
        }
        if (getMedia(message) instanceof TLRPC.TL_messageMediaWebPage) {
            return (getMedia(message).webpage.photo instanceof TLRPC.TL_photo) && !(getMedia(message).webpage.document instanceof TLRPC.TL_document);
        }
        if (message != null && (messageAction = message.action) != null && (photo = messageAction.photo) != null) {
            return photo instanceof TLRPC.TL_photo;
        }
        TLRPC.MessageMedia media = getMedia(message);
        return (media instanceof TLRPC.TL_messageMediaPhoto) && !((TLRPC.TL_messageMediaPhoto) media).live_photo;
    }

    public static boolean isVoiceMessage(TLRPC.Message message) {
        if (getMedia(message) instanceof TLRPC.TL_messageMediaWebPage) {
            return isVoiceDocument(getMedia(message).webpage.document);
        }
        return getMedia(message) != null && isVoiceDocument(getMedia(message).document);
    }

    public static boolean isNewGifMessage(TLRPC.Message message) {
        if (getMedia(message) instanceof TLRPC.TL_messageMediaWebPage) {
            return isNewGifDocument(getMedia(message).webpage.document);
        }
        return getMedia(message) != null && isNewGifDocument(getMedia(message).document);
    }

    public static boolean isLiveLocationMessage(TLRPC.Message message) {
        return getMedia(message) instanceof TLRPC.TL_messageMediaGeoLive;
    }

    public static boolean isVideoMessage(TLRPC.Message message) {
        TL_iv.RichMessage richMessage;
        if (message != null && (richMessage = message.rich_message) != null) {
            return isVideoDocument(findVideo(richMessage));
        }
        if (getMedia(message) != null && isVideoSticker(getMedia(message).document)) {
            return false;
        }
        if (getMedia(message) instanceof TLRPC.TL_messageMediaWebPage) {
            return isVideoDocument(getMedia(message).webpage.document);
        }
        return getMedia(message) != null && isVideoDocument(getMedia(message).document);
    }

    public static boolean isGameMessage(TLRPC.Message message) {
        return getMedia(message) instanceof TLRPC.TL_messageMediaGame;
    }

    public static boolean isInvoiceMessage(TLRPC.Message message) {
        return getMedia(message) instanceof TLRPC.TL_messageMediaInvoice;
    }

    public static TLRPC.InputStickerSet getInputStickerSet(TLRPC.Message message) {
        TLRPC.Document document = getDocument(message);
        if (document != null) {
            return getInputStickerSet(document);
        }
        return null;
    }

    public static TLRPC.InputStickerSet getInputStickerSet(TLRPC.Document document) {
        if (document == null) {
            return null;
        }
        int size = document.attributes.size();
        for (int i = 0; i < size; i++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
            if ((documentAttribute instanceof TLRPC.TL_documentAttributeSticker) || (documentAttribute instanceof TLRPC.TL_documentAttributeCustomEmoji)) {
                TLRPC.InputStickerSet inputStickerSet = documentAttribute.stickerset;
                if (inputStickerSet instanceof TLRPC.TL_inputStickerSetEmpty) {
                    return null;
                }
                return inputStickerSet;
            }
        }
        return null;
    }

    public static String findAnimatedEmojiEmoticon(TLRPC.Document document) {
        return findAnimatedEmojiEmoticon(document, "😀");
    }

    public static String findAnimatedEmojiEmoticon(TLRPC.Document document, String str) {
        return findAnimatedEmojiEmoticon(document, str, null);
    }

    public static String findAnimatedEmojiEmoticon(TLRPC.Document document, String str, Integer num) {
        if (document != null) {
            int size = document.attributes.size();
            for (int i = 0; i < size; i++) {
                TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
                if ((documentAttribute instanceof TLRPC.TL_documentAttributeCustomEmoji) || (documentAttribute instanceof TLRPC.TL_documentAttributeSticker)) {
                    if (num != null) {
                        TLRPC.TL_messages_stickerSet stickerSet = MediaDataController.getInstance(num.intValue()).getStickerSet(documentAttribute.stickerset, true);
                        StringBuilder sb = new StringBuilder(_UrlKt.FRAGMENT_ENCODE_SET);
                        if (stickerSet != null && stickerSet.packs != null) {
                            for (int i2 = 0; i2 < stickerSet.packs.size(); i2++) {
                                TLRPC.TL_stickerPack tL_stickerPack = stickerSet.packs.get(i2);
                                if (tL_stickerPack.documents.contains(Long.valueOf(document.f1253id))) {
                                    sb.append(tL_stickerPack.emoticon);
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(sb)) {
                            return sb.toString();
                        }
                    }
                    return documentAttribute.alt;
                }
            }
        }
        return str;
    }

    public static ArrayList<String> findStickerEmoticons(TLRPC.Document document, Integer num) {
        if (document == null) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        int size = document.attributes.size();
        for (int i = 0; i < size; i++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
            if ((documentAttribute instanceof TLRPC.TL_documentAttributeCustomEmoji) || (documentAttribute instanceof TLRPC.TL_documentAttributeSticker)) {
                if (num != null) {
                    TLRPC.TL_messages_stickerSet stickerSet = MediaDataController.getInstance(num.intValue()).getStickerSet(documentAttribute.stickerset, true);
                    if (stickerSet != null && stickerSet.packs != null) {
                        for (int i2 = 0; i2 < stickerSet.packs.size(); i2++) {
                            TLRPC.TL_stickerPack tL_stickerPack = stickerSet.packs.get(i2);
                            if (tL_stickerPack.documents.contains(Long.valueOf(document.f1253id)) && Emoji.getEmojiDrawable(tL_stickerPack.emoticon) != null) {
                                arrayList.add(tL_stickerPack.emoticon);
                            }
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        return arrayList;
                    }
                }
                if (!TextUtils.isEmpty(documentAttribute.alt) && Emoji.getEmojiDrawable(documentAttribute.alt) != null) {
                    arrayList.add(documentAttribute.alt);
                    return arrayList;
                }
            }
        }
        return null;
    }

    public static boolean isAnimatedEmoji(TLRPC.Document document) {
        if (document == null) {
            return false;
        }
        int size = document.attributes.size();
        for (int i = 0; i < size; i++) {
            if (document.attributes.get(i) instanceof TLRPC.TL_documentAttributeCustomEmoji) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFreeEmoji(TLRPC.Document document) {
        if (document == null) {
            return false;
        }
        int size = document.attributes.size();
        for (int i = 0; i < size; i++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeCustomEmoji) {
                return ((TLRPC.TL_documentAttributeCustomEmoji) documentAttribute).free;
            }
        }
        return false;
    }

    public static boolean isTextColorEmoji(TLRPC.Document document) {
        if (document == null) {
            return false;
        }
        int size = document.attributes.size();
        for (int i = 0; i < size; i++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeCustomEmoji) {
                return ((TLRPC.TL_documentAttributeCustomEmoji) documentAttribute).text_color;
            }
        }
        return false;
    }

    public static boolean isTextColorSet(TLRPC.TL_messages_stickerSet tL_messages_stickerSet) {
        TLRPC.StickerSet stickerSet;
        if (tL_messages_stickerSet != null && (stickerSet = tL_messages_stickerSet.set) != null) {
            if (stickerSet.text_color) {
                return true;
            }
            ArrayList<TLRPC.Document> arrayList = tL_messages_stickerSet.documents;
            if (arrayList != null && !arrayList.isEmpty()) {
                return isTextColorEmoji(tL_messages_stickerSet.documents.get(0));
            }
        }
        return false;
    }

    public static boolean isPremiumEmojiPack(TLRPC.TL_messages_stickerSet tL_messages_stickerSet) {
        TLRPC.StickerSet stickerSet;
        if ((tL_messages_stickerSet == null || (stickerSet = tL_messages_stickerSet.set) == null || stickerSet.emojis) && tL_messages_stickerSet != null && tL_messages_stickerSet.documents != null) {
            for (int i = 0; i < tL_messages_stickerSet.documents.size(); i++) {
                if (!isFreeEmoji(tL_messages_stickerSet.documents.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isPremiumEmojiPack(TLRPC.StickerSetCovered stickerSetCovered) {
        TLRPC.StickerSet stickerSet;
        if (stickerSetCovered != null && (stickerSet = stickerSetCovered.set) != null && !stickerSet.emojis) {
            return false;
        }
        ArrayList<TLRPC.Document> arrayList = stickerSetCovered instanceof TLRPC.TL_stickerSetFullCovered ? ((TLRPC.TL_stickerSetFullCovered) stickerSetCovered).documents : stickerSetCovered.covers;
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (!isFreeEmoji(arrayList.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static long getStickerSetId(TLRPC.Document document) {
        if (document == null) {
            return -1L;
        }
        for (int i = 0; i < document.attributes.size(); i++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeSticker) {
                TLRPC.InputStickerSet inputStickerSet = documentAttribute.stickerset;
                if (inputStickerSet instanceof TLRPC.TL_inputStickerSetEmpty) {
                    return -1L;
                }
                return inputStickerSet.f1270id;
            }
        }
        return -1L;
    }

    public static String getStickerSetName(TLRPC.Document document) {
        if (document == null) {
            return null;
        }
        for (int i = 0; i < document.attributes.size(); i++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeSticker) {
                TLRPC.InputStickerSet inputStickerSet = documentAttribute.stickerset;
                if (inputStickerSet instanceof TLRPC.TL_inputStickerSetEmpty) {
                    return null;
                }
                return inputStickerSet.short_name;
            }
        }
        return null;
    }

    public String getStickerChar() {
        TLRPC.Document document = getDocument();
        if (document == null) {
            return null;
        }
        ArrayList<TLRPC.DocumentAttribute> arrayList = document.attributes;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            TLRPC.DocumentAttribute documentAttribute = arrayList.get(i);
            i++;
            TLRPC.DocumentAttribute documentAttribute2 = documentAttribute;
            if (documentAttribute2 instanceof TLRPC.TL_documentAttributeSticker) {
                return documentAttribute2.alt;
            }
        }
        return null;
    }

    public int getApproximateHeight() {
        return getApproximateHeight(false);
    }

    public int getApproximateHeightCached() {
        Integer num = this.cachedApproximateHeight;
        if (num != null) {
            return num.intValue();
        }
        int approximateHeight = getApproximateHeight(true);
        this.cachedApproximateHeight = Integer.valueOf(approximateHeight);
        return approximateHeight;
    }

    public int getApproximateHeight(boolean z) {
        int minTabletSide;
        int i;
        int iMin;
        TLRPC.PhotoSize closestPhotoSizeWithSize;
        int iMin2;
        int i2 = this.type;
        int iM1036dp = 0;
        if (i2 == 0) {
            int iTextHeightCached = z ? textHeightCached() : textHeight();
            if ((getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && (getMedia(this.messageOwner).webpage instanceof TLRPC.TL_webPage)) {
                iM1036dp = AndroidUtilities.m1036dp(100.0f);
            }
            int i3 = iTextHeightCached + iM1036dp;
            return isReply() ? i3 + AndroidUtilities.m1036dp(42.0f) : i3;
        }
        if (i2 == 20) {
            return AndroidUtilities.getPhotoSize();
        }
        if (i2 == 2) {
            return AndroidUtilities.m1036dp(72.0f);
        }
        if (i2 == 12) {
            return AndroidUtilities.m1036dp(71.0f);
        }
        if (i2 == 9) {
            return AndroidUtilities.m1036dp(100.0f);
        }
        if (i2 == 4) {
            return AndroidUtilities.m1036dp(114.0f);
        }
        if (i2 == 14) {
            return AndroidUtilities.m1036dp(82.0f);
        }
        if (i2 == 10 || i2 == 35 || i2 == 33 || i2 == 34) {
            return AndroidUtilities.m1036dp(30.0f);
        }
        if (i2 == 11 || i2 == 18 || i2 == 31 || i2 == 30 || i2 == 25 || i2 == 21) {
            return AndroidUtilities.m1036dp(50.0f);
        }
        if (i2 == 32) {
            return AndroidUtilities.m1036dp(234.0f);
        }
        if (i2 == 5) {
            return AndroidUtilities.roundMessageSize;
        }
        if (i2 == 19) {
            return (z ? textHeightCached() : textHeight()) + AndroidUtilities.m1036dp(30.0f);
        }
        if (i2 == 13 || i2 == 15) {
            float f = AndroidUtilities.displaySize.y * 0.4f;
            if (AndroidUtilities.isTablet()) {
                minTabletSide = AndroidUtilities.getMinTabletSide();
            } else {
                minTabletSide = AndroidUtilities.displaySize.x;
            }
            float f2 = minTabletSide * 0.5f;
            TLRPC.Document document = getDocument();
            if (document != null) {
                int size = document.attributes.size();
                for (int i4 = 0; i4 < size; i4++) {
                    TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i4);
                    if (documentAttribute instanceof TLRPC.TL_documentAttributeImageSize) {
                        iM1036dp = documentAttribute.f1256w;
                        i = documentAttribute.f1255h;
                        break;
                    }
                }
                i = 0;
            } else {
                i = 0;
            }
            if (iM1036dp == 0) {
                i = (int) f;
                iM1036dp = AndroidUtilities.m1036dp(100.0f) + i;
            }
            float f3 = i;
            if (f3 > f) {
                iM1036dp = (int) (iM1036dp * (f / f3));
                i = (int) f;
            }
            float f4 = iM1036dp;
            if (f4 > f2) {
                i = (int) (i * (f2 / f4));
            }
            return i + AndroidUtilities.m1036dp(14.0f);
        }
        if (AndroidUtilities.isTablet()) {
            iMin = AndroidUtilities.getMinTabletSide();
        } else {
            Point point = AndroidUtilities.displaySize;
            iMin = Math.min(point.x, point.y);
        }
        int photoSize = (int) (iMin * 0.7f);
        int iM1036dp2 = AndroidUtilities.m1036dp(100.0f) + photoSize;
        if (photoSize > AndroidUtilities.getPhotoSize()) {
            photoSize = AndroidUtilities.getPhotoSize();
        }
        if (iM1036dp2 > AndroidUtilities.getPhotoSize()) {
            iM1036dp2 = AndroidUtilities.getPhotoSize();
        }
        ArrayList<TLRPC.PhotoSize> arrayList = this.photoThumbs;
        if (z) {
            closestPhotoSizeWithSize = (arrayList == null || arrayList.isEmpty()) ? null : this.photoThumbs.get(0);
        } else {
            closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(arrayList, AndroidUtilities.getPhotoSize());
        }
        if (closestPhotoSizeWithSize != null) {
            int iM1036dp3 = (int) (closestPhotoSizeWithSize.f1277h / (closestPhotoSizeWithSize.f1278w / photoSize));
            if (iM1036dp3 == 0) {
                iM1036dp3 = AndroidUtilities.m1036dp(100.0f);
            }
            if (iM1036dp3 <= iM1036dp2) {
                iM1036dp2 = iM1036dp3 < AndroidUtilities.m1036dp(120.0f) ? AndroidUtilities.m1036dp(120.0f) : iM1036dp3;
            }
            if (!z && needDrawBluredPreview()) {
                if (AndroidUtilities.isTablet()) {
                    iMin2 = AndroidUtilities.getMinTabletSide();
                } else {
                    Point point2 = AndroidUtilities.displaySize;
                    iMin2 = Math.min(point2.x, point2.y);
                }
                iM1036dp2 = (int) (iMin2 * 0.5f);
            }
        }
        return iM1036dp2 + AndroidUtilities.m1036dp(14.0f);
    }

    public static String getEmoji(TLRPC.Document document) {
        if (document == null) {
            return "😀";
        }
        TLRPC.TL_documentAttributeCustomEmoji tL_documentAttributeCustomEmoji = (TLRPC.TL_documentAttributeCustomEmoji) AndroidUtilities.find(document.attributes, TLRPC.TL_documentAttributeCustomEmoji.class);
        if (tL_documentAttributeCustomEmoji != null && !TextUtils.isEmpty(tL_documentAttributeCustomEmoji.alt)) {
            return tL_documentAttributeCustomEmoji.alt;
        }
        TLRPC.TL_documentAttributeSticker tL_documentAttributeSticker = (TLRPC.TL_documentAttributeSticker) AndroidUtilities.find(document.attributes, TLRPC.TL_documentAttributeSticker.class);
        if (tL_documentAttributeSticker == null || TextUtils.isEmpty(tL_documentAttributeSticker.alt)) {
            return "😀";
        }
        return tL_documentAttributeSticker.alt;
    }

    public String getStickerEmoji() {
        TLRPC.Document document = getDocument();
        if (document == null) {
            return null;
        }
        for (int i = 0; i < document.attributes.size(); i++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
            if ((documentAttribute instanceof TLRPC.TL_documentAttributeSticker) || (documentAttribute instanceof TLRPC.TL_documentAttributeCustomEmoji)) {
                String str = documentAttribute.alt;
                if (str != null && str.length() > 0) {
                    return documentAttribute.alt;
                }
                return null;
            }
        }
        return null;
    }

    public boolean isConferenceCall() {
        return this.messageOwner.action instanceof TLRPC.TL_messageActionConferenceCall;
    }

    public boolean isVideoCall() {
        TLRPC.MessageAction messageAction = this.messageOwner.action;
        if ((messageAction instanceof TLRPC.TL_messageActionPhoneCall) && messageAction.video) {
            return true;
        }
        return (messageAction instanceof TLRPC.TL_messageActionConferenceCall) && messageAction.video;
    }

    public boolean isAnimatedEmoji() {
        return (this.emojiAnimatedSticker == null && this.emojiAnimatedStickerId == null) ? false : true;
    }

    public boolean isAnimatedAnimatedEmoji() {
        return isAnimatedEmoji() && isAnimatedEmoji(getDocument());
    }

    public boolean isDice() {
        return getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaDice;
    }

    public boolean isStakeableDice() {
        TLRPC.TL_messageMediaDice tL_messageMediaDice = (TLRPC.TL_messageMediaDice) getMedia(this.messageOwner, TLRPC.TL_messageMediaDice.class);
        return tL_messageMediaDice != null && TextUtils.equals("🎲", tL_messageMediaDice.emoticon);
    }

    public boolean isStakedDice() {
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        return (media instanceof TLRPC.TL_messageMediaDice) && ((TLRPC.TL_messageMediaDice) media).game_outcome != null;
    }

    public static long getStakedDiceWinAmount(TLRPC.TL_messageMediaDice tL_messageMediaDice) {
        TLRPC.TL_messages_emojiGameOutcome tL_messages_emojiGameOutcome = tL_messageMediaDice.game_outcome;
        if (tL_messages_emojiGameOutcome == null) {
            return 0L;
        }
        long j = tL_messages_emojiGameOutcome.ton_amount;
        return j > 0 ? j : -tL_messages_emojiGameOutcome.stake_ton_amount;
    }

    public long getStakedDiceWinAmount() {
        TLRPC.TL_messages_emojiGameOutcome tL_messages_emojiGameOutcome;
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        if (!(media instanceof TLRPC.TL_messageMediaDice) || (tL_messages_emojiGameOutcome = ((TLRPC.TL_messageMediaDice) media).game_outcome) == null) {
            return 0L;
        }
        long j = tL_messages_emojiGameOutcome.ton_amount;
        return j > 0 ? j : -tL_messages_emojiGameOutcome.stake_ton_amount;
    }

    public long getStakedDiceAmount() {
        TLRPC.TL_messages_emojiGameOutcome tL_messages_emojiGameOutcome;
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        if (!(media instanceof TLRPC.TL_messageMediaDice) || (tL_messages_emojiGameOutcome = ((TLRPC.TL_messageMediaDice) media).game_outcome) == null) {
            return 0L;
        }
        return tL_messages_emojiGameOutcome.stake_ton_amount;
    }

    public String getDiceEmoji() {
        if (!isDice()) {
            return null;
        }
        TLRPC.TL_messageMediaDice tL_messageMediaDice = (TLRPC.TL_messageMediaDice) getMedia(this.messageOwner);
        if (TextUtils.isEmpty(tL_messageMediaDice.emoticon)) {
            return "🎲";
        }
        return tL_messageMediaDice.emoticon.replace("️", _UrlKt.FRAGMENT_ENCODE_SET);
    }

    public String getDiceEmoji(TLRPC.TL_messageMediaDice tL_messageMediaDice) {
        if (tL_messageMediaDice == null) {
            return null;
        }
        if (TextUtils.isEmpty(tL_messageMediaDice.emoticon)) {
            return "🎲";
        }
        return tL_messageMediaDice.emoticon.replace("️", _UrlKt.FRAGMENT_ENCODE_SET);
    }

    public int getDiceValue() {
        if (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaDice) {
            return ((TLRPC.TL_messageMediaDice) getMedia(this.messageOwner)).value;
        }
        return -1;
    }

    public boolean isSticker() {
        int i = this.type;
        return i != 1000 ? i == 13 : isStickerDocument(getDocument()) || isVideoSticker(getDocument());
    }

    public boolean isAnimatedSticker() {
        int i = this.type;
        if (i != 1000) {
            return i == 15;
        }
        boolean zIsEncryptedDialog = DialogObject.isEncryptedDialog(getDialogId());
        if (zIsEncryptedDialog && this.messageOwner.stickerVerified != 1) {
            return false;
        }
        if (this.emojiAnimatedStickerId == null || this.emojiAnimatedSticker != null) {
            return isAnimatedStickerDocument(getDocument(), (this.emojiAnimatedSticker == null && zIsEncryptedDialog && !isOut()) ? false : true);
        }
        return true;
    }

    public boolean isAnyKindOfSticker() {
        int i = this.type;
        return i == 13 || i == 15 || i == 19;
    }

    public boolean shouldDrawWithoutBackground() {
        if (isSponsored()) {
            return false;
        }
        int i = this.type;
        return i == 13 || i == 15 || i == 5 || i == 19 || isExpiredStory();
    }

    public boolean isAnimatedEmojiStickers() {
        return this.type == 19;
    }

    public boolean isAnimatedEmojiStickerSingle() {
        return this.emojiAnimatedStickerId != null;
    }

    public boolean isLocation() {
        return isLocationMessage(this.messageOwner);
    }

    public boolean isMask() {
        return isMaskMessage(this.messageOwner);
    }

    public boolean isMusic() {
        return (!isMusicMessage(this.messageOwner) || isVideo() || isRoundVideo()) ? false : true;
    }

    public boolean isDocument() {
        return (getDocument() == null || isVideo() || isMusic() || isVoice() || isAnyKindOfSticker()) ? false : true;
    }

    public boolean isVoice() {
        return isVoiceMessage(this.messageOwner);
    }

    public boolean isVoiceOnce() {
        TLRPC.Message message;
        TLRPC.MessageMedia messageMedia;
        return isVoice() && (message = this.messageOwner) != null && (messageMedia = message.media) != null && messageMedia.ttl_seconds == Integer.MAX_VALUE;
    }

    public boolean isRoundOnce() {
        TLRPC.Message message;
        TLRPC.MessageMedia messageMedia;
        return isRoundVideo() && (message = this.messageOwner) != null && (messageMedia = message.media) != null && messageMedia.ttl_seconds == Integer.MAX_VALUE;
    }

    public boolean isVideo() {
        return isVideoMessage(this.messageOwner);
    }

    public boolean isLivePhoto() {
        TLRPC.MessageMedia media = getMedia(this);
        return media != null && media.live_photo;
    }

    public boolean isVideoStory() {
        TL_stories.StoryItem storyItem;
        TLRPC.MessageMedia messageMedia;
        TLRPC.MessageMedia media = getMedia(this.messageOwner);
        if (media == null || (storyItem = media.storyItem) == null || (messageMedia = storyItem.media) == null) {
            return false;
        }
        return isVideoDocument(messageMedia.document);
    }

    public boolean isPhoto() {
        return isPhoto(this.messageOwner);
    }

    public boolean isStoryMedia() {
        TLRPC.Message message = this.messageOwner;
        return message != null && (message.media instanceof TLRPC.TL_messageMediaStory);
    }

    public boolean isLiveLocation() {
        return isLiveLocationMessage(this.messageOwner);
    }

    public boolean isExpiredLiveLocation(int i) {
        TLRPC.Message message = this.messageOwner;
        return message.date + getMedia(message).period <= i;
    }

    public boolean isGame() {
        return isGameMessage(this.messageOwner);
    }

    public boolean isInvoice() {
        return isInvoiceMessage(this.messageOwner);
    }

    public boolean isRoundVideo() {
        if (this.isRoundVideoCached == 0) {
            this.isRoundVideoCached = (this.type == 5 || isRoundVideoMessage(this.messageOwner)) ? 1 : 2;
        }
        return this.isRoundVideoCached == 1;
    }

    public boolean shouldAnimateSending() {
        if (!this.wasJustSent) {
            return false;
        }
        if (this.type == 5 || isVoice()) {
            return true;
        }
        if (!isAnyKindOfSticker() || this.sendAnimationData == null) {
            return (this.messageText == null || this.sendAnimationData == null) ? false : true;
        }
        return true;
    }

    public boolean hasAttachedStickers() {
        boolean z = getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaPhoto;
        TLRPC.Message message = this.messageOwner;
        if (z) {
            return getMedia(message).photo != null && getMedia(this.messageOwner).photo.has_stickers;
        }
        if (getMedia(message) instanceof TLRPC.TL_messageMediaDocument) {
            return isDocumentHasAttachedStickers(getMedia(this.messageOwner).document);
        }
        return false;
    }

    public static boolean isDocumentHasAttachedStickers(TLRPC.Document document) {
        if (document != null) {
            for (int i = 0; i < document.attributes.size(); i++) {
                if (document.attributes.get(i) instanceof TLRPC.TL_documentAttributeHasStickers) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isGif() {
        return isGifMessage(this.messageOwner);
    }

    public boolean isWebpageDocument() {
        return (!(getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) || getMedia(this.messageOwner).webpage.document == null || isGifDocument(getMedia(this.messageOwner).webpage.document)) ? false : true;
    }

    public boolean isWebpage() {
        return getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage;
    }

    public boolean isNewGif() {
        return getMedia(this.messageOwner) != null && isNewGifDocument(getDocument());
    }

    public boolean isAndroidTheme() {
        if (getMedia(this.messageOwner) != null && getMedia(this.messageOwner).webpage != null && !getMedia(this.messageOwner).webpage.attributes.isEmpty()) {
            int size = getMedia(this.messageOwner).webpage.attributes.size();
            for (int i = 0; i < size; i++) {
                TLRPC.WebPageAttribute webPageAttribute = getMedia(this.messageOwner).webpage.attributes.get(i);
                if (webPageAttribute instanceof TLRPC.TL_webPageAttributeTheme) {
                    TLRPC.TL_webPageAttributeTheme tL_webPageAttributeTheme = (TLRPC.TL_webPageAttributeTheme) webPageAttribute;
                    ArrayList<TLRPC.Document> arrayList = tL_webPageAttributeTheme.documents;
                    int size2 = arrayList.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        if ("application/x-tgtheme-android".equals(arrayList.get(i2).mime_type)) {
                            return true;
                        }
                    }
                    if (tL_webPageAttributeTheme.settings != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getMusicTitle() {
        return getMusicTitle(true);
    }

    public String getMusicTitle(boolean z) {
        TLRPC.Document document = getDocument();
        if (document != null) {
            for (int i = 0; i < document.attributes.size(); i++) {
                TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
                if (documentAttribute instanceof TLRPC.TL_documentAttributeAudio) {
                    if (documentAttribute.voice) {
                        if (z) {
                            return LocaleController.formatDateAudio(this.messageOwner.date, true);
                        }
                        return null;
                    }
                    String str = documentAttribute.title;
                    if (str != null && str.length() != 0) {
                        return str;
                    }
                    String documentFileName = FileLoader.getDocumentFileName(document);
                    return (TextUtils.isEmpty(documentFileName) && z) ? LocaleController.getString(C2797R.string.AudioUnknownTitle) : documentFileName;
                }
                if ((documentAttribute instanceof TLRPC.TL_documentAttributeVideo) && documentAttribute.round_message) {
                    if (isQuickReply()) {
                        return LocaleController.formatString(C2797R.string.BusinessInReplies, "/" + getQuickReplyDisplayName());
                    }
                    return LocaleController.formatDateAudio(this.messageOwner.date, true);
                }
            }
            String documentFileName2 = FileLoader.getDocumentFileName(document);
            if (!TextUtils.isEmpty(documentFileName2)) {
                return documentFileName2;
            }
        }
        return LocaleController.getString(C2797R.string.AudioUnknownTitle);
    }

    public static String getMusicTitle(TLRPC.Document document, boolean z) {
        if (document == null) {
            return LocaleController.getString(C2797R.string.AudioUnknownTitle);
        }
        for (int i = 0; i < document.attributes.size(); i++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeAudio) {
                String str = documentAttribute.title;
                if (str != null && !str.isEmpty()) {
                    return str;
                }
                String documentFileName = FileLoader.getDocumentFileName(document);
                return (TextUtils.isEmpty(documentFileName) && z) ? LocaleController.getString(C2797R.string.AudioUnknownTitle) : documentFileName;
            }
        }
        String documentFileName2 = FileLoader.getDocumentFileName(document);
        return !TextUtils.isEmpty(documentFileName2) ? documentFileName2 : LocaleController.getString(C2797R.string.AudioUnknownTitle);
    }

    public double getDuration() {
        TL_stories.StoryItem storyItem;
        TLRPC.MessageMedia messageMedia;
        double d = this.attributeDuration;
        if (d > 0.0d) {
            return d;
        }
        TLRPC.Document document = getDocument();
        if (document == null && this.type == 23 && (storyItem = getMedia(this.messageOwner).storyItem) != null && (messageMedia = storyItem.media) != null) {
            document = messageMedia.document;
        }
        if (document == null) {
            return 0.0d;
        }
        int i = this.audioPlayerDuration;
        if (i > 0) {
            return i;
        }
        for (int i2 = 0; i2 < document.attributes.size(); i2++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i2);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeAudio) {
                double d2 = documentAttribute.duration;
                this.attributeDuration = d2;
                return d2;
            }
            if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                double d3 = documentAttribute.duration;
                this.attributeDuration = d3;
                return d3;
            }
        }
        return this.audioPlayerDuration;
    }

    public String getArtworkUrl(boolean z) {
        return getArtworkUrl(getDocument(), z);
    }

    public static String getArtworkUrl(TLRPC.Document document, boolean z) {
        if (document == null || "audio/ogg".equals(document.mime_type)) {
            return null;
        }
        int size = document.attributes.size();
        for (int i = 0; i < size; i++) {
            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeAudio) {
                if (documentAttribute.voice) {
                    return null;
                }
                String strReplace = documentAttribute.performer;
                String str = documentAttribute.title;
                if (!TextUtils.isEmpty(strReplace)) {
                    for (String str2 : excludeWords) {
                        strReplace = strReplace.replace(str2, " ");
                    }
                }
                if (TextUtils.isEmpty(strReplace) && TextUtils.isEmpty(str)) {
                    return null;
                }
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("athumb://itunes.apple.com/search?term=");
                    sb.append(URLEncoder.encode(strReplace + " - " + str, "UTF-8"));
                    sb.append("&entity=song&limit=4");
                    sb.append(z ? "&s=1" : _UrlKt.FRAGMENT_ENCODE_SET);
                    return sb.toString();
                } catch (Exception unused) {
                    continue;
                }
            }
        }
        return null;
    }

    public String getMusicAuthor() {
        return getMusicAuthor(true);
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0152 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getMusicAuthor(boolean r10) {
        /*
            Method dump skipped, instruction units count: 349
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.getMusicAuthor(boolean):java.lang.String");
    }

    public static String getMusicAuthor(TLRPC.Document document, boolean z) {
        if (document != null) {
            for (int i = 0; i < document.attributes.size(); i++) {
                TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
                if ((documentAttribute instanceof TLRPC.TL_documentAttributeAudio) && !documentAttribute.voice) {
                    String str = documentAttribute.performer;
                    return (TextUtils.isEmpty(str) && z) ? LocaleController.getString(C2797R.string.AudioUnknownArtist) : str;
                }
            }
        }
        return LocaleController.getString(C2797R.string.AudioUnknownArtist);
    }

    public TLRPC.InputStickerSet getInputStickerSet() {
        return getInputStickerSet(this.messageOwner);
    }

    public boolean isForwarded() {
        return isForwardedMessage(this.messageOwner);
    }

    /* JADX WARN: Removed duplicated region for block: B:98:0x0097  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean needDrawForwarded() {
        /*
            r11 = this;
            int r0 = r11.type
            r1 = 23
            r2 = 1
            if (r0 != r1) goto Le
            boolean r0 = r11.isExpiredStory()
            if (r0 != 0) goto Le
            return r2
        Le:
            long r0 = r11.getDialogId()
            r3 = 489000(0x77628, double:2.41598E-318)
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            r1 = 0
            if (r0 != 0) goto L1b
            return r1
        L1b:
            boolean r0 = r11.isSaved
            org.telegram.tgnet.TLRPC$Message r3 = r11.messageOwner
            if (r0 == 0) goto L6d
            if (r3 == 0) goto L6c
            org.telegram.tgnet.TLRPC$MessageFwdHeader r0 = r3.fwd_from
            if (r0 != 0) goto L28
            goto L6c
        L28:
            int r0 = r11.currentAccount
            org.telegram.messenger.UserConfig r0 = org.telegram.messenger.UserConfig.getInstance(r0)
            long r3 = r0.getClientUserId()
            org.telegram.tgnet.TLRPC$Message r0 = r11.messageOwner
            long r5 = getSavedDialogId(r3, r0)
            org.telegram.tgnet.TLRPC$Message r0 = r11.messageOwner
            org.telegram.tgnet.TLRPC$MessageFwdHeader r0 = r0.fwd_from
            org.telegram.tgnet.TLRPC$Peer r0 = r0.saved_from_peer
            long r7 = org.telegram.messenger.DialogObject.getPeerDialogId(r0)
            r9 = 0
            int r0 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r0 < 0) goto L52
            org.telegram.tgnet.TLRPC$Message r11 = r11.messageOwner
            org.telegram.tgnet.TLRPC$MessageFwdHeader r11 = r11.fwd_from
            org.telegram.tgnet.TLRPC$Peer r11 = r11.from_id
            long r7 = org.telegram.messenger.DialogObject.getPeerDialogId(r11)
        L52:
            int r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r11 != 0) goto L63
            int r11 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r11 < 0) goto L62
            r3 = 2666000(0x28ae10, double:1.317179E-317)
            int r11 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r11 == 0) goto L62
            return r2
        L62:
            return r1
        L63:
            int r11 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r11 == 0) goto L6c
            int r11 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r11 == 0) goto L6c
            return r2
        L6c:
            return r1
        L6d:
            int r0 = r3.flags
            r0 = r0 & 4
            if (r0 != 0) goto L7b
            org.telegram.tgnet.TLRPC$MessageMedia r0 = getMedia(r11)
            boolean r0 = r0 instanceof org.telegram.tgnet.TLRPC.TL_messageMediaPaidMedia
            if (r0 == 0) goto Laa
        L7b:
            org.telegram.tgnet.TLRPC$Message r0 = r11.messageOwner
            org.telegram.tgnet.TLRPC$MessageFwdHeader r0 = r0.fwd_from
            if (r0 == 0) goto Laa
            boolean r3 = r0.imported
            if (r3 != 0) goto Laa
            org.telegram.tgnet.TLRPC$Peer r3 = r0.saved_from_peer
            if (r3 == 0) goto L97
            org.telegram.tgnet.TLRPC$Peer r0 = r0.from_id
            boolean r4 = r0 instanceof org.telegram.tgnet.TLRPC.TL_peerChannel
            if (r4 == 0) goto L97
            long r3 = r3.channel_id
            long r5 = r0.channel_id
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 == 0) goto Laa
        L97:
            int r0 = r11.currentAccount
            org.telegram.messenger.UserConfig r0 = org.telegram.messenger.UserConfig.getInstance(r0)
            long r3 = r0.getClientUserId()
            long r5 = r11.getDialogId()
            int r11 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r11 == 0) goto Laa
            return r2
        Laa:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.needDrawForwarded():boolean");
    }

    public static boolean isForwardedMessage(TLRPC.Message message) {
        return ((message.flags & 4) == 0 || message.fwd_from == null) ? false : true;
    }

    public boolean isReply() {
        TLRPC.Message message;
        TLRPC.MessageReplyHeader messageReplyHeader;
        MessageObject messageObject = this.replyMessageObject;
        if ((messageObject == null || !(messageObject.messageOwner instanceof TLRPC.TL_messageEmpty)) && (messageReplyHeader = (message = this.messageOwner).reply_to) != null) {
            return ((messageReplyHeader.reply_to_msg_id == 0 && messageReplyHeader.reply_to_random_id == 0) || (message.flags & 8) == 0) ? false : true;
        }
        return false;
    }

    public boolean isMediaEmpty() {
        return isMediaEmpty(this.messageOwner);
    }

    public boolean isMediaEmpty(boolean z) {
        return isMediaEmpty(this.messageOwner, z);
    }

    public boolean isMediaEmptyWebpage() {
        return isMediaEmptyWebpage(this.messageOwner);
    }

    public static boolean isMediaEmpty(TLRPC.Message message) {
        return isMediaEmpty(message, true);
    }

    public static boolean isMediaEmpty(TLRPC.Message message, boolean z) {
        if (message == null || getMedia(message) == null || (getMedia(message) instanceof TLRPC.TL_messageMediaEmpty)) {
            return true;
        }
        return z && (getMedia(message) instanceof TLRPC.TL_messageMediaWebPage);
    }

    public static boolean isMediaEmptyWebpage(TLRPC.Message message) {
        return message == null || getMedia(message) == null || (getMedia(message) instanceof TLRPC.TL_messageMediaEmpty);
    }

    public boolean hasReplies() {
        TLRPC.MessageReplies messageReplies = this.messageOwner.replies;
        return messageReplies != null && messageReplies.replies > 0;
    }

    public boolean canViewThread() {
        if (this.messageOwner.action != null) {
            return false;
        }
        if (hasReplies()) {
            return true;
        }
        MessageObject messageObject = this.replyMessageObject;
        return ((messageObject == null || messageObject.messageOwner.replies == null) && getReplyTopMsgId() == 0) ? false : true;
    }

    public boolean isComments() {
        TLRPC.MessageReplies messageReplies = this.messageOwner.replies;
        return messageReplies != null && messageReplies.comments;
    }

    public boolean isLinkedToChat(long j) {
        TLRPC.MessageReplies messageReplies = this.messageOwner.replies;
        if (messageReplies != null) {
            return j == 0 || messageReplies.channel_id == j;
        }
        return false;
    }

    public int getRepliesCount() {
        TLRPC.MessageReplies messageReplies = this.messageOwner.replies;
        if (messageReplies != null) {
            return messageReplies.replies;
        }
        return 0;
    }

    public boolean canEditMessage(TLRPC.Chat chat) {
        return canEditMessage(this.currentAccount, this.messageOwner, chat, this.scheduled);
    }

    public boolean canEditMessageScheduleTime(TLRPC.Chat chat) {
        return canEditMessageScheduleTime(this.currentAccount, this.messageOwner, chat);
    }

    public boolean canForwardMessage() {
        int i;
        return (isQuickReply() || (i = this.type) == 30 || i == 31 || i == 32 || i == 33 || i == 35 || (this.messageOwner instanceof TLRPC.TL_message_secret) || needDrawBluredPreview() || isLiveLocation() || this.type == 16 || isSponsored() || this.messageOwner.noforwards) ? false : true;
    }

    public boolean canEditMedia() {
        if (isSecretMedia()) {
            return false;
        }
        if (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaPhoto) {
            return true;
        }
        return getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaDocument ? (isVoice() || isSticker() || isAnimatedSticker() || isRoundVideo()) ? false : true : isMediaEmpty();
    }

    public boolean canEditMessageAnytime(TLRPC.Chat chat) {
        return canEditMessageAnytime(this.currentAccount, this.messageOwner, chat);
    }

    public static boolean canEditMessageAnytime(int i, TLRPC.Message message, TLRPC.Chat chat) {
        TLRPC.MessageAction messageAction;
        TLRPC.TL_chatAdminRights tL_chatAdminRights;
        TLRPC.TL_chatBannedRights tL_chatBannedRights;
        TLRPC.TL_chatAdminRights tL_chatAdminRights2;
        if (message != null && message.peer_id != null && ((getMedia(message) == null || (!isRoundVideoDocument(getMedia(message).document) && !isStickerDocument(getMedia(message).document) && !isAnimatedStickerDocument(getMedia(message).document, true))) && (((messageAction = message.action) == null || (messageAction instanceof TLRPC.TL_messageActionEmpty)) && !isForwardedMessage(message) && message.via_bot_id == 0 && message.f1271id >= 0))) {
            TLRPC.Peer peer = message.from_id;
            if (peer instanceof TLRPC.TL_peerUser) {
                long j = peer.user_id;
                if (j == message.peer_id.user_id && j == UserConfig.getInstance(i).getClientUserId() && !isLiveLocationMessage(message)) {
                    return true;
                }
            }
            if (chat == null && message.peer_id.channel_id != 0 && (chat = MessagesController.getInstance(UserConfig.selectedAccount).getChat(Long.valueOf(message.peer_id.channel_id))) == null) {
                return false;
            }
            if (ChatObject.isChannel(chat) && !chat.megagroup && (chat.creator || ((tL_chatAdminRights2 = chat.admin_rights) != null && tL_chatAdminRights2.edit_messages))) {
                return true;
            }
            if (message.out && chat != null && chat.megagroup && (chat.creator || (((tL_chatAdminRights = chat.admin_rights) != null && tL_chatAdminRights.pin_messages) || ((tL_chatBannedRights = chat.default_banned_rights) != null && !tL_chatBannedRights.pin_messages)))) {
                return true;
            }
        }
        return false;
    }

    public static boolean canEditMessageScheduleTime(int i, TLRPC.Message message, TLRPC.Chat chat) {
        if (message.video_processing_pending) {
            return false;
        }
        if (chat == null && message.peer_id.channel_id != 0 && (chat = MessagesController.getInstance(i).getChat(Long.valueOf(message.peer_id.channel_id))) == null) {
            return false;
        }
        if (!ChatObject.isChannel(chat) || chat.megagroup || chat.creator) {
            return true;
        }
        TLRPC.TL_chatAdminRights tL_chatAdminRights = chat.admin_rights;
        return tL_chatAdminRights != null && (tL_chatAdminRights.edit_messages || message.out);
    }

    /* JADX WARN: Removed duplicated region for block: B:301:0x014c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean canEditMessage(int r9, org.telegram.tgnet.TLRPC.Message r10, org.telegram.tgnet.TLRPC.Chat r11, boolean r12) {
        /*
            Method dump skipped, instruction units count: 453
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.canEditMessage(int, org.telegram.tgnet.TLRPC$Message, org.telegram.tgnet.TLRPC$Chat, boolean):boolean");
    }

    public boolean canDeleteMessage(boolean z, TLRPC.Chat chat) {
        TLRPC.Message message;
        if (isStory() && (message = this.messageOwner) != null && message.dialog_id == UserConfig.getInstance(this.currentAccount).getClientUserId()) {
            return true;
        }
        return this.eventId == 0 && this.sponsoredId == null && canDeleteMessage(this.currentAccount, z, this.messageOwner, chat);
    }

    public static boolean canDeleteMessage(int i, boolean z, TLRPC.Message message, TLRPC.Chat chat) {
        TLRPC.TL_chatAdminRights tL_chatAdminRights;
        TLRPC.TL_chatAdminRights tL_chatAdminRights2;
        TLRPC.Peer peer;
        if (message == null) {
            return false;
        }
        if (ChatObject.isChannelAndNotMegaGroup(chat) && (message.action instanceof TLRPC.TL_messageActionChatJoinedByRequest)) {
            return false;
        }
        if (message.f1271id < 0) {
            return true;
        }
        if (chat == null && (peer = message.peer_id) != null && peer.channel_id != 0) {
            chat = MessagesController.getInstance(i).getChat(Long.valueOf(message.peer_id.channel_id));
        }
        if (!ChatObject.isChannel(chat)) {
            return z || isOut(message) || !ChatObject.isChannel(chat);
        }
        if (z && !chat.megagroup) {
            return chat.creator || ((tL_chatAdminRights2 = chat.admin_rights) != null && (tL_chatAdminRights2.delete_messages || message.out));
        }
        boolean z2 = message.out;
        return (z2 && (message instanceof TLRPC.TL_messageService)) ? message.f1271id != 1 && ChatObject.canUserDoAdminAction(chat, 13) : z || (message.f1271id != 1 && (chat.creator || (((tL_chatAdminRights = chat.admin_rights) != null && (tL_chatAdminRights.delete_messages || (z2 && (chat.megagroup || tL_chatAdminRights.post_messages)))) || (chat.megagroup && z2))));
    }

    public String getForwardedName() {
        TLRPC.MessageFwdHeader messageFwdHeader = this.messageOwner.fwd_from;
        if (messageFwdHeader == null) {
            return null;
        }
        TLRPC.Peer peer = messageFwdHeader.from_id;
        if (peer instanceof TLRPC.TL_peerChannel) {
            TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(this.messageOwner.fwd_from.from_id.channel_id));
            if (chat != null) {
                return chat.title;
            }
            return null;
        }
        if (peer instanceof TLRPC.TL_peerChat) {
            TLRPC.Chat chat2 = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(this.messageOwner.fwd_from.from_id.chat_id));
            if (chat2 != null) {
                return chat2.title;
            }
            return null;
        }
        if (peer instanceof TLRPC.TL_peerUser) {
            TLRPC.User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.messageOwner.fwd_from.from_id.user_id));
            if (user != null) {
                return UserObject.getUserName(user);
            }
            return null;
        }
        String str = messageFwdHeader.from_name;
        if (str != null) {
            return str;
        }
        return null;
    }

    public Long getForwardedFromId() {
        TLRPC.MessageFwdHeader messageFwdHeader;
        TLRPC.Peer peer;
        TLRPC.Message message = this.messageOwner;
        if (message == null || (messageFwdHeader = message.fwd_from) == null || (peer = messageFwdHeader.from_id) == null) {
            return null;
        }
        return Long.valueOf(DialogObject.getPeerDialogId(peer));
    }

    public TLObject getForwardedFromPeerObject() {
        Long forwardedFromId = getForwardedFromId();
        if (forwardedFromId == null) {
            return null;
        }
        return MessagesController.getInstance(this.currentAccount).getUserOrChat(forwardedFromId.longValue());
    }

    public int getReplyMsgId() {
        TLRPC.MessageReplyHeader messageReplyHeader = this.messageOwner.reply_to;
        if (messageReplyHeader != null) {
            return messageReplyHeader.reply_to_msg_id;
        }
        return 0;
    }

    public int getReplyTopMsgId() {
        TLRPC.MessageReplyHeader messageReplyHeader = this.messageOwner.reply_to;
        if (messageReplyHeader != null) {
            return messageReplyHeader.reply_to_top_id;
        }
        return 0;
    }

    public int getReplyTopMsgId(boolean z) {
        TLRPC.MessageReplyHeader messageReplyHeader = this.messageOwner.reply_to;
        if (messageReplyHeader == null) {
            return 0;
        }
        if (z && (messageReplyHeader.flags & 2) > 0 && messageReplyHeader.reply_to_top_id == 0) {
            return 1;
        }
        return messageReplyHeader.reply_to_top_id;
    }

    public static long getReplyToDialogId(TLRPC.Message message) {
        TLRPC.MessageReplyHeader messageReplyHeader = message.reply_to;
        if (messageReplyHeader == null) {
            return 0L;
        }
        TLRPC.Peer peer = messageReplyHeader.reply_to_peer_id;
        if (peer != null) {
            return getPeerId(peer);
        }
        return getDialogId(message);
    }

    public int getReplyAnyMsgId() {
        TLRPC.MessageReplyHeader messageReplyHeader = this.messageOwner.reply_to;
        if (messageReplyHeader == null) {
            return 0;
        }
        int i = messageReplyHeader.reply_to_top_id;
        return i != 0 ? i : messageReplyHeader.reply_to_msg_id;
    }

    public boolean isPrivateForward() {
        TLRPC.MessageFwdHeader messageFwdHeader = this.messageOwner.fwd_from;
        return (messageFwdHeader == null || TextUtils.isEmpty(messageFwdHeader.from_name)) ? false : true;
    }

    public boolean isImportedForward() {
        TLRPC.MessageFwdHeader messageFwdHeader = this.messageOwner.fwd_from;
        return messageFwdHeader != null && messageFwdHeader.imported;
    }

    public long getSenderId() {
        TLRPC.Peer peer;
        TLRPC.Message message = this.messageOwner;
        TLRPC.MessageFwdHeader messageFwdHeader = message.fwd_from;
        if (messageFwdHeader != null && (peer = messageFwdHeader.saved_from_peer) != null) {
            long j = peer.user_id;
            if (j != 0) {
                TLRPC.Peer peer2 = messageFwdHeader.from_id;
                return peer2 instanceof TLRPC.TL_peerUser ? peer2.user_id : j;
            }
            if (peer.channel_id != 0) {
                if (isSavedFromMegagroup()) {
                    TLRPC.Peer peer3 = this.messageOwner.fwd_from.from_id;
                    if (peer3 instanceof TLRPC.TL_peerUser) {
                        return peer3.user_id;
                    }
                }
                TLRPC.MessageFwdHeader messageFwdHeader2 = this.messageOwner.fwd_from;
                TLRPC.Peer peer4 = messageFwdHeader2.from_id;
                if (peer4 instanceof TLRPC.TL_peerChannel) {
                    return -peer4.channel_id;
                }
                if (peer4 instanceof TLRPC.TL_peerChat) {
                    return -peer4.chat_id;
                }
                return -messageFwdHeader2.saved_from_peer.channel_id;
            }
            long j2 = peer.chat_id;
            if (j2 != 0) {
                TLRPC.Peer peer5 = messageFwdHeader.from_id;
                if (peer5 instanceof TLRPC.TL_peerUser) {
                    return peer5.user_id;
                }
                if (peer5 instanceof TLRPC.TL_peerChannel) {
                    return -peer5.channel_id;
                }
                return peer5 instanceof TLRPC.TL_peerChat ? -peer5.chat_id : -j2;
            }
        } else {
            TLRPC.Peer peer6 = message.from_id;
            if (peer6 instanceof TLRPC.TL_peerUser) {
                return peer6.user_id;
            }
            if (peer6 instanceof TLRPC.TL_peerChannel) {
                return -peer6.channel_id;
            }
            if (peer6 instanceof TLRPC.TL_peerChat) {
                return -peer6.chat_id;
            }
            if (message.post) {
                return message.peer_id.channel_id;
            }
        }
        return 0L;
    }

    public boolean isWallpaper() {
        return (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && getMedia(this.messageOwner).webpage != null && "telegram_background".equals(getMedia(this.messageOwner).webpage.type);
    }

    public boolean isTheme() {
        return (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && getMedia(this.messageOwner).webpage != null && "telegram_theme".equals(getMedia(this.messageOwner).webpage.type);
    }

    public int getMediaExistanceFlags() {
        boolean z = this.attachPathExists;
        return this.mediaExists ? (z ? 1 : 0) | 2 : z ? 1 : 0;
    }

    public void applyMediaExistanceFlags(int i) {
        if (i == -1) {
            checkMediaExistance();
        } else {
            this.attachPathExists = (i & 1) != 0;
            this.mediaExists = (i & 2) != 0;
        }
    }

    public void checkMediaExistance() {
        checkMediaExistance(true);
    }

    public void checkMediaExistance(boolean z) {
        int i;
        this.attachPathExists = false;
        this.mediaExists = false;
        int i2 = this.type;
        if (i2 == 20) {
            TLRPC.TL_messageExtendedMediaPreview tL_messageExtendedMediaPreview = (TLRPC.TL_messageExtendedMediaPreview) this.messageOwner.media.extended_media.get(0);
            if (tL_messageExtendedMediaPreview.thumb != null) {
                File pathToAttach = FileLoader.getInstance(this.currentAccount).getPathToAttach(tL_messageExtendedMediaPreview.thumb, z);
                if (!this.mediaExists) {
                    this.mediaExists = pathToAttach.exists() || (tL_messageExtendedMediaPreview.thumb instanceof TLRPC.TL_photoStrippedSize);
                }
            }
        } else if (i2 == 1 && FileLoader.getClosestPhotoSizeWithSize(this.photoThumbs, AndroidUtilities.getPhotoSize(true)) != null) {
            File pathToMessage = FileLoader.getInstance(this.currentAccount).getPathToMessage(this.messageOwner, z);
            if (needDrawBluredPreview()) {
                this.mediaExists = new File(pathToMessage.getAbsolutePath() + ".enc").exists();
            }
            if (!this.mediaExists) {
                this.mediaExists = pathToMessage.exists();
            }
        }
        if ((!this.mediaExists && this.type == 8) || (i = this.type) == 3 || i == 9 || i == 2 || i == 14 || i == 5) {
            String str = this.messageOwner.attachPath;
            if (str != null && str.length() > 0) {
                this.attachPathExists = new File(this.messageOwner.attachPath).exists();
            }
            if (!this.attachPathExists) {
                File pathToMessage2 = FileLoader.getInstance(this.currentAccount).getPathToMessage(this.messageOwner, z);
                if ((this.type == 3 && needDrawBluredPreview()) || isVoiceOnce() || isRoundOnce()) {
                    this.mediaExists = new File(pathToMessage2.getAbsolutePath() + ".enc").exists();
                }
                if (!this.mediaExists) {
                    this.mediaExists = pathToMessage2.exists();
                }
            }
        }
        if (!this.mediaExists) {
            TLRPC.Document document = getDocument();
            if (document != null) {
                boolean zIsWallpaper = isWallpaper();
                int i3 = this.currentAccount;
                if (zIsWallpaper) {
                    this.mediaExists = FileLoader.getInstance(i3).getPathToAttach(document, null, true, z).exists();
                } else {
                    this.mediaExists = FileLoader.getInstance(i3).getPathToAttach(document, null, false, z).exists();
                }
            } else {
                int i4 = this.type;
                if (i4 == 0) {
                    TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(this.photoThumbs, AndroidUtilities.getPhotoSize());
                    if (closestPhotoSizeWithSize == null) {
                        return;
                    } else {
                        this.mediaExists = FileLoader.getInstance(this.currentAccount).getPathToAttach(closestPhotoSizeWithSize, null, true, z).exists();
                    }
                } else if (i4 == 11) {
                    TLRPC.Photo photo = this.messageOwner.action.photo;
                    if (photo == null || photo.video_sizes.isEmpty()) {
                        return;
                    } else {
                        this.mediaExists = FileLoader.getInstance(this.currentAccount).getPathToAttach(photo.video_sizes.get(0), null, true, z).exists();
                    }
                }
            }
        }
        updateQualitiesCached(z);
    }

    public void setQuery(String str) {
        setQuery(str, true);
    }

    public void setQuery(String str, boolean z) {
        String strSubstring;
        int iIndexOf;
        if (TextUtils.isEmpty(str)) {
            this.highlightedWords = null;
            this.messageTrimmedToHighlight = null;
            this.messageTrimmedToHighlightCut = true;
            return;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        String lowerCase = str.trim().toLowerCase();
        String[] strArrSplit = lowerCase.split("[^\\p{L}#$]+");
        ArrayList arrayList2 = new ArrayList();
        TLRPC.MessageReplyHeader messageReplyHeader = this.messageOwner.reply_to;
        if (messageReplyHeader != null && !TextUtils.isEmpty(messageReplyHeader.quote_text)) {
            String lowerCase2 = this.messageOwner.reply_to.quote_text.trim().toLowerCase();
            if (lowerCase2.contains(lowerCase) && !arrayList.contains(lowerCase)) {
                arrayList.add(lowerCase);
                handleFoundWords(arrayList, strArrSplit, true, z);
                return;
            }
            arrayList2.addAll(Arrays.asList(lowerCase2.split("[^\\p{L}#$]+")));
        }
        int i = 0;
        if (!TextUtils.isEmpty(this.messageOwner.message)) {
            String lowerCase3 = this.messageOwner.message.trim().toLowerCase();
            if (lowerCase3.contains(lowerCase) && !arrayList.contains(lowerCase)) {
                arrayList.add(lowerCase);
                handleFoundWords(arrayList, strArrSplit, false, z);
                return;
            }
            arrayList2.addAll(Arrays.asList(lowerCase3.split("[^\\p{L}#$]+")));
        }
        if (getDocument() != null) {
            String lowerCase4 = FileLoader.getDocumentFileName(getDocument()).toLowerCase();
            if (lowerCase4.contains(lowerCase) && !arrayList.contains(lowerCase)) {
                arrayList.add(lowerCase);
            }
            arrayList2.addAll(Arrays.asList(lowerCase4.split("[^\\p{L}#$]+")));
        }
        if ((getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && (getMedia(this.messageOwner).webpage instanceof TLRPC.TL_webPage)) {
            TLRPC.WebPage webPage = getMedia(this.messageOwner).webpage;
            String str2 = webPage.title;
            if (str2 == null) {
                str2 = webPage.site_name;
            }
            if (str2 != null) {
                String lowerCase5 = str2.toLowerCase();
                if (lowerCase5.contains(lowerCase) && !arrayList.contains(lowerCase)) {
                    arrayList.add(lowerCase);
                }
                arrayList2.addAll(Arrays.asList(lowerCase5.split("[^\\p{L}#$]+")));
            }
        }
        String musicAuthor = getMusicAuthor();
        if (musicAuthor != null) {
            String lowerCase6 = musicAuthor.toLowerCase();
            if (lowerCase6.contains(lowerCase) && !arrayList.contains(lowerCase)) {
                arrayList.add(lowerCase);
            }
            arrayList2.addAll(Arrays.asList(lowerCase6.split("[^\\p{L}#$]+")));
        }
        int length = strArrSplit.length;
        int i2 = 0;
        while (i2 < length) {
            String str3 = strArrSplit[i2];
            if (str3.length() >= 2) {
                int i3 = i;
                while (i3 < arrayList2.size()) {
                    if (!arrayList.contains(arrayList2.get(i3)) && (iIndexOf = (strSubstring = (String) arrayList2.get(i3)).indexOf(str3.charAt(i))) >= 0) {
                        int iMax = Math.max(str3.length(), strSubstring.length());
                        if (iIndexOf != 0) {
                            strSubstring = strSubstring.substring(iIndexOf);
                        }
                        int iMin = Math.min(str3.length(), strSubstring.length());
                        int i4 = i;
                        int i5 = i4;
                        while (i4 < iMin && strSubstring.charAt(i4) == str3.charAt(i4)) {
                            i5++;
                            i4++;
                        }
                        if (i5 / iMax >= 0.5d) {
                            arrayList.add((String) arrayList2.get(i3));
                        }
                    }
                    i3++;
                    i = 0;
                }
            }
            i2++;
            i = 0;
        }
        handleFoundWords(arrayList, strArrSplit, i, z);
    }

    private void handleFoundWords(ArrayList<String> arrayList, String[] strArr, boolean z) {
        handleFoundWords(arrayList, strArr, z, true);
    }

    private void handleFoundWords(ArrayList<String> arrayList, String[] strArr, boolean z, boolean z2) {
        CharSequence charSequence;
        TLRPC.Message message;
        TLRPC.MessageReplyHeader messageReplyHeader;
        boolean z3;
        if (arrayList.isEmpty()) {
            return;
        }
        boolean z4 = false;
        for (int i = 0; i < arrayList.size(); i++) {
            int length = strArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                if (arrayList.get(i).contains(strArr[i2])) {
                    z4 = true;
                    break;
                }
                i2++;
            }
            if (z4) {
                break;
            }
        }
        if (z4) {
            int i3 = 0;
            while (i3 < arrayList.size()) {
                int length2 = strArr.length;
                int i4 = 0;
                while (true) {
                    if (i4 >= length2) {
                        z3 = false;
                        break;
                    } else {
                        if (arrayList.get(i3).contains(strArr[i4])) {
                            z3 = true;
                            break;
                        }
                        i4++;
                    }
                }
                if (!z3) {
                    arrayList.remove(i3);
                    i3--;
                }
                i3++;
            }
            if (arrayList.size() > 0) {
                Collections.sort(arrayList, new Comparator() { // from class: org.telegram.messenger.MessageObject$$ExternalSyntheticLambda1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return MessageObject.$r8$lambda$gmQO2dzPCHe8iMwdGhi6M6Eo3m8((String) obj, (String) obj2);
                    }
                });
                String str = arrayList.get(0);
                arrayList.clear();
                arrayList.add(str);
            }
        }
        this.highlightedWords = arrayList;
        if (this.messageOwner.message != null) {
            applyEntities();
            if (!TextUtils.isEmpty(this.caption)) {
                charSequence = this.caption;
            } else {
                charSequence = this.messageText;
            }
            CharSequence charSequenceReplaceMultipleCharSequence = AndroidUtilities.replaceMultipleCharSequence("\n", charSequence, " ");
            if (z && (message = this.messageOwner) != null && (messageReplyHeader = message.reply_to) != null && messageReplyHeader.quote_text != null) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.messageOwner.reply_to.quote_text);
                addEntitiesToText(spannableStringBuilder, this.messageOwner.reply_to.quote_entities, isOutOwner(), false, false, false);
                SpannableString spannableString = new SpannableString("q ");
                ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2797R.drawable.mini_quote);
                coloredImageSpan.setOverrideColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
                spannableString.setSpan(coloredImageSpan, 0, 1, 33);
                charSequenceReplaceMultipleCharSequence = new SpannableStringBuilder(spannableString).append((CharSequence) spannableStringBuilder).append('\n').append(charSequenceReplaceMultipleCharSequence);
            }
            String string = charSequenceReplaceMultipleCharSequence.toString();
            int length3 = string.length();
            int iIndexOf = string.toLowerCase().indexOf(arrayList.get(0));
            if (iIndexOf < 0) {
                iIndexOf = 0;
            }
            if (length3 > 120 && z2) {
                int iMax = Math.max(0, iIndexOf - 12);
                charSequenceReplaceMultipleCharSequence = charSequenceReplaceMultipleCharSequence.subSequence(iMax, Math.min(length3, (iIndexOf - iMax) + iIndexOf + 108));
            }
            this.messageTrimmedToHighlight = charSequenceReplaceMultipleCharSequence;
            this.messageTrimmedToHighlightCut = z2;
        }
    }

    public static /* synthetic */ int $r8$lambda$gmQO2dzPCHe8iMwdGhi6M6Eo3m8(String str, String str2) {
        return str2.length() - str.length();
    }

    public void createMediaThumbs() {
        TLRPC.MessageMedia messageMedia;
        if (isStoryMedia()) {
            TL_stories.StoryItem storyItem = getMedia(this.messageOwner).storyItem;
            if (storyItem == null || (messageMedia = storyItem.media) == null) {
                return;
            }
            TLRPC.Document document = messageMedia.document;
            if (document != null) {
                TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(document.thumbs, 50);
                this.mediaThumb = ImageLocation.getForDocument(FileLoader.getClosestPhotoSizeWithSize(document.thumbs, 320, false, null, true), document);
                this.mediaSmallThumb = ImageLocation.getForDocument(closestPhotoSizeWithSize, document);
                return;
            } else {
                TLRPC.PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(this.photoThumbs, 50);
                this.mediaThumb = ImageLocation.getForObject(FileLoader.getClosestPhotoSizeWithSize(this.photoThumbs, 320, false, closestPhotoSizeWithSize2, true), this.photoThumbsObject);
                this.mediaSmallThumb = ImageLocation.getForObject(closestPhotoSizeWithSize2, this.photoThumbsObject);
                return;
            }
        }
        if (isVideo()) {
            TLRPC.Document document2 = getDocument();
            TLRPC.PhotoSize closestPhotoSizeWithSize3 = FileLoader.getClosestPhotoSizeWithSize(document2.thumbs, 50);
            this.mediaThumb = ImageLocation.getForDocument(FileLoader.getClosestPhotoSizeWithSize(document2.thumbs, 320), document2);
            this.mediaSmallThumb = ImageLocation.getForDocument(closestPhotoSizeWithSize3, document2);
            return;
        }
        if (!(getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaPhoto) || getMedia(this.messageOwner).photo == null || this.photoThumbs.isEmpty()) {
            return;
        }
        TLRPC.PhotoSize closestPhotoSizeWithSize4 = FileLoader.getClosestPhotoSizeWithSize(this.photoThumbs, 50);
        this.mediaThumb = ImageLocation.getForObject(FileLoader.getClosestPhotoSizeWithSize(this.photoThumbs, 320, false, closestPhotoSizeWithSize4, false), this.photoThumbsObject);
        this.mediaSmallThumb = ImageLocation.getForObject(closestPhotoSizeWithSize4, this.photoThumbsObject);
    }

    public boolean hasHighlightedWords() {
        ArrayList<String> arrayList = this.highlightedWords;
        return (arrayList == null || arrayList.isEmpty()) ? false : true;
    }

    public boolean equals(MessageObject messageObject) {
        return messageObject != null && getId() == messageObject.getId() && getDialogId() == messageObject.getDialogId();
    }

    public boolean isReactionsAvailable() {
        return (isEditing() || isSponsored() || !isSent() || isExpiredStory() || !canSetReaction()) ? false : true;
    }

    public boolean isPaidReactionChosen() {
        if (this.messageOwner.reactions == null) {
            return false;
        }
        for (int i = 0; i < this.messageOwner.reactions.results.size(); i++) {
            if (this.messageOwner.reactions.results.get(i).reaction instanceof TLRPC.TL_reactionPaid) {
                return this.messageOwner.reactions.results.get(i).chosen;
            }
        }
        return false;
    }

    public void addPaidReactions(int i, boolean z, long j) {
        TLRPC.Message message = this.messageOwner;
        if (message.reactions == null) {
            message.reactions = new TLRPC.TL_messageReactions();
            TLRPC.Message message2 = this.messageOwner;
            message2.reactions.reactions_as_tags = getDialogId(message2) == UserConfig.getInstance(this.currentAccount).getClientUserId();
            this.messageOwner.reactions.can_see_list = isFromGroup() || isFromUser();
        }
        addPaidReactions(this.currentAccount, this.messageOwner.reactions, i, j, z);
    }

    public Long getMyPaidReactionPeer() {
        TLRPC.TL_messageReactions tL_messageReactions;
        ArrayList<TLRPC.MessageReactor> arrayList;
        TLRPC.Message message = this.messageOwner;
        if (message == null || (tL_messageReactions = message.reactions) == null || (arrayList = tL_messageReactions.top_reactors) == null) {
            return null;
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            TLRPC.MessageReactor messageReactor = arrayList.get(i);
            i++;
            TLRPC.MessageReactor messageReactor2 = messageReactor;
            if (messageReactor2 != null && messageReactor2.f1273my) {
                if (messageReactor2.anonymous) {
                    return Long.valueOf(UserObject.ANONYMOUS);
                }
                TLRPC.Peer peer = messageReactor2.peer_id;
                if (peer != null) {
                    return Long.valueOf(DialogObject.getPeerDialogId(peer));
                }
            }
        }
        return null;
    }

    public static Long getMyPaidReactionPeer(TLRPC.MessageReactions messageReactions) {
        ArrayList<TLRPC.MessageReactor> arrayList;
        if (messageReactions == null || (arrayList = messageReactions.top_reactors) == null) {
            return null;
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            TLRPC.MessageReactor messageReactor = arrayList.get(i);
            i++;
            TLRPC.MessageReactor messageReactor2 = messageReactor;
            if (messageReactor2 != null && messageReactor2.f1273my) {
                if (messageReactor2.anonymous) {
                    return Long.valueOf(UserObject.ANONYMOUS);
                }
                TLRPC.Peer peer = messageReactor2.peer_id;
                if (peer != null) {
                    return Long.valueOf(DialogObject.getPeerDialogId(peer));
                }
            }
        }
        return null;
    }

    public void setMyPaidReactionDialogId(long j) {
        TLRPC.TL_messageReactions tL_messageReactions;
        ArrayList<TLRPC.MessageReactor> arrayList;
        TLRPC.Message message = this.messageOwner;
        if (message == null || (tL_messageReactions = message.reactions) == null || (arrayList = tL_messageReactions.top_reactors) == null) {
            return;
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            TLRPC.MessageReactor messageReactor = arrayList.get(i);
            i++;
            TLRPC.MessageReactor messageReactor2 = messageReactor;
            if (messageReactor2 != null && messageReactor2.f1273my) {
                boolean z = j == UserObject.ANONYMOUS;
                messageReactor2.anonymous = z;
                int i2 = messageReactor2.flags;
                if (z) {
                    messageReactor2.flags = i2 & (-9);
                    messageReactor2.peer_id = null;
                } else {
                    messageReactor2.flags = i2 | 8;
                    messageReactor2.peer_id = MessagesController.getInstance(this.currentAccount).getPeer(j);
                }
            }
        }
    }

    public boolean doesPaidReactionExist() {
        TLRPC.Message message = this.messageOwner;
        if (message.reactions == null) {
            message.reactions = new TLRPC.TL_messageReactions();
            TLRPC.Message message2 = this.messageOwner;
            message2.reactions.reactions_as_tags = getDialogId(message2) == UserConfig.getInstance(this.currentAccount).getClientUserId();
            this.messageOwner.reactions.can_see_list = isFromGroup() || isFromUser();
        }
        for (int i = 0; i < this.messageOwner.reactions.results.size(); i++) {
            if (this.messageOwner.reactions.results.get(i).reaction instanceof TLRPC.TL_reactionPaid) {
                return true;
            }
        }
        return false;
    }

    public boolean ensurePaidReactionsExist(boolean z) {
        TLRPC.Message message = this.messageOwner;
        if (message.reactions == null) {
            message.reactions = new TLRPC.TL_messageReactions();
            TLRPC.Message message2 = this.messageOwner;
            message2.reactions.reactions_as_tags = getDialogId(message2) == UserConfig.getInstance(this.currentAccount).getClientUserId();
            this.messageOwner.reactions.can_see_list = isFromGroup() || isFromUser();
        }
        TLRPC.ReactionCount reactionCount = null;
        for (int i = 0; i < this.messageOwner.reactions.results.size(); i++) {
            if (this.messageOwner.reactions.results.get(i).reaction instanceof TLRPC.TL_reactionPaid) {
                reactionCount = this.messageOwner.reactions.results.get(i);
            }
        }
        if (reactionCount != null) {
            return false;
        }
        TLRPC.TL_reactionCount tL_reactionCount = new TLRPC.TL_reactionCount();
        tL_reactionCount.reaction = new TLRPC.TL_reactionPaid();
        tL_reactionCount.count = 1;
        tL_reactionCount.chosen = z;
        this.messageOwner.reactions.results.add(0, tL_reactionCount);
        return true;
    }

    public static void addPaidReactions(int i, TLRPC.MessageReactions messageReactions, int i2, long j, boolean z) {
        TLRPC.MessageReactor tL_messageReactor = null;
        TLRPC.ReactionCount tL_reactionCount = null;
        for (int i3 = 0; i3 < messageReactions.results.size(); i3++) {
            if (messageReactions.results.get(i3).reaction instanceof TLRPC.TL_reactionPaid) {
                tL_reactionCount = messageReactions.results.get(i3);
            }
        }
        int i4 = 0;
        while (true) {
            if (i4 >= messageReactions.top_reactors.size()) {
                break;
            }
            if (messageReactions.top_reactors.get(i4).f1273my) {
                tL_messageReactor = messageReactions.top_reactors.get(i4);
                break;
            }
            i4++;
        }
        if (tL_reactionCount == null && i2 > 0) {
            tL_reactionCount = new TLRPC.TL_reactionCount();
            tL_reactionCount.reaction = new TLRPC.TL_reactionPaid();
            messageReactions.results.add(0, tL_reactionCount);
        }
        if (tL_reactionCount != null) {
            tL_reactionCount.chosen = z;
            int iMax = Math.max(0, tL_reactionCount.count + i2);
            tL_reactionCount.count = iMax;
            if (iMax <= 0) {
                messageReactions.results.remove(tL_reactionCount);
            }
        }
        if (tL_messageReactor == null && i2 > 0) {
            tL_messageReactor = new TLRPC.TL_messageReactor();
            tL_messageReactor.f1273my = true;
            messageReactions.top_reactors.add(tL_messageReactor);
        }
        if (tL_messageReactor != null) {
            tL_messageReactor.count = Math.max(0, tL_messageReactor.count + i2);
            tL_messageReactor.anonymous = j == UserObject.ANONYMOUS;
            if (j == 0 || j == UserObject.ANONYMOUS) {
                tL_messageReactor.peer_id = MessagesController.getInstance(i).getPeer(UserConfig.getInstance(i).getClientUserId());
            } else {
                tL_messageReactor.peer_id = MessagesController.getInstance(i).getPeer(j);
            }
            if (tL_messageReactor.count <= 0) {
                messageReactions.top_reactors.remove(tL_messageReactor);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:194:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x00c1 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean selectReaction(org.telegram.ui.Components.Reactions.ReactionsLayoutInBubble.VisibleReaction r13, boolean r14, boolean r15) {
        /*
            Method dump skipped, instruction units count: 698
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.selectReaction(org.telegram.ui.Components.Reactions.ReactionsLayoutInBubble$VisibleReaction, boolean, boolean):boolean");
    }

    public boolean probablyRingtone() {
        if (!isVoiceOnce() && getDocument() != null && RingtoneDataStore.ringtoneSupportedMimeType.contains(getDocument().mime_type) && getDocument().size < MessagesController.getInstance(this.currentAccount).ringtoneSizeMax * 2) {
            for (int i = 0; i < getDocument().attributes.size(); i++) {
                TLRPC.DocumentAttribute documentAttribute = getDocument().attributes.get(i);
                if ((documentAttribute instanceof TLRPC.TL_documentAttributeAudio) && documentAttribute.duration < 5.0d) {
                    return true;
                }
            }
        }
        return false;
    }

    public byte[] getWaveform() {
        if (getDocument() == null) {
            return null;
        }
        int i = 0;
        for (int i2 = 0; i2 < getDocument().attributes.size(); i2++) {
            TLRPC.DocumentAttribute documentAttribute = getDocument().attributes.get(i2);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeAudio) {
                byte[] bArr = documentAttribute.waveform;
                if (bArr == null || bArr.length == 0) {
                    MediaController.getInstance().generateWaveform(this);
                }
                return documentAttribute.waveform;
            }
        }
        if (!isRoundVideo()) {
            return null;
        }
        if (this.randomWaveform == null) {
            this.randomWaveform = new byte[120];
            while (true) {
                byte[] bArr2 = this.randomWaveform;
                if (i >= bArr2.length) {
                    break;
                }
                bArr2[i] = (byte) (Math.random() * 255.0d);
                i++;
            }
        }
        return this.randomWaveform;
    }

    public boolean isStory() {
        return this.storyItem != null;
    }

    public boolean isBotPreview() {
        return this.storyItem instanceof StoriesController.BotPreview;
    }

    public TLRPC.WebPage getStoryMentionWebpage() {
        if (!isStoryMention()) {
            return null;
        }
        TLRPC.WebPage webPage = this.storyMentionWebpage;
        if (webPage != null) {
            return webPage;
        }
        TLRPC.TL_webPage tL_webPage = new TLRPC.TL_webPage();
        tL_webPage.type = "telegram_story";
        TLRPC.TL_webPageAttributeStory tL_webPageAttributeStory = new TLRPC.TL_webPageAttributeStory();
        tL_webPageAttributeStory.f1403id = this.messageOwner.media.f1272id;
        tL_webPageAttributeStory.peer = MessagesController.getInstance(this.currentAccount).getPeer(this.messageOwner.media.user_id);
        TL_stories.StoryItem storyItem = this.messageOwner.media.storyItem;
        if (storyItem != null) {
            tL_webPageAttributeStory.flags |= 1;
            tL_webPageAttributeStory.storyItem = storyItem;
        }
        tL_webPage.attributes.add(tL_webPageAttributeStory);
        this.storyMentionWebpage = tL_webPage;
        return tL_webPage;
    }

    public boolean isStoryMention() {
        return this.type == 24 && !isExpiredStory();
    }

    public boolean isGiveaway() {
        return this.type == 26;
    }

    public boolean isGiveawayOrGiveawayResults() {
        return isGiveaway() || isGiveawayResults();
    }

    public boolean isGiveawayResults() {
        return this.type == 28;
    }

    public boolean isAnyGift() {
        int i = this.type;
        return i == 30 || i == 18 || i == 25;
    }

    public static CharSequence userSpan() {
        return userSpan(0);
    }

    public static CharSequence userSpan(int i) {
        if (userSpan == null) {
            userSpan = new CharSequence[2];
        }
        CharSequence[] charSequenceArr = userSpan;
        if (charSequenceArr[i] == null) {
            charSequenceArr[i] = new SpannableStringBuilder("u");
            ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2797R.drawable.mini_reply_user);
            coloredImageSpan.spaceScaleX = 0.9f;
            if (i == 0) {
                coloredImageSpan.translate(0.0f, AndroidUtilities.m1036dp(1.0f));
            }
            ((SpannableStringBuilder) userSpan[i]).setSpan(coloredImageSpan, 0, 1, 33);
        }
        return userSpan[i];
    }

    public static CharSequence groupSpan() {
        if (groupSpan == null) {
            groupSpan = new SpannableStringBuilder(ImageLoader.AUTOPLAY_FILTER);
            ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2797R.drawable.msg_folders_groups);
            coloredImageSpan.setScale(0.7f, 0.7f);
            ((SpannableStringBuilder) groupSpan).setSpan(coloredImageSpan, 0, 1, 33);
        }
        return groupSpan;
    }

    public static CharSequence channelSpan() {
        if (channelSpan == null) {
            channelSpan = new SpannableStringBuilder("c");
            ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2797R.drawable.msg_folders_channels);
            coloredImageSpan.setScale(0.7f, 0.7f);
            ((SpannableStringBuilder) channelSpan).setSpan(coloredImageSpan, 0, 1, 33);
        }
        return channelSpan;
    }

    public static CharSequence peerNameWithIcon(int i, TLRPC.Peer peer) {
        return peerNameWithIcon(i, peer, !(peer instanceof TLRPC.TL_peerUser));
    }

    public static CharSequence peerNameWithIcon(int i, TLRPC.Peer peer, boolean z) {
        TLRPC.Chat chat;
        if (peer instanceof TLRPC.TL_peerUser) {
            TLRPC.User user = MessagesController.getInstance(i).getUser(Long.valueOf(peer.user_id));
            if (user == null) {
                return _UrlKt.FRAGMENT_ENCODE_SET;
            }
            if (z) {
                return new SpannableStringBuilder(userSpan()).append((CharSequence) " ").append((CharSequence) UserObject.getUserName(user));
            }
            return UserObject.getUserName(user);
        }
        if (peer instanceof TLRPC.TL_peerChat) {
            TLRPC.Chat chat2 = MessagesController.getInstance(i).getChat(Long.valueOf(peer.chat_id));
            if (chat2 == null) {
                return _UrlKt.FRAGMENT_ENCODE_SET;
            }
            if (z) {
                return new SpannableStringBuilder(ChatObject.isChannelAndNotMegaGroup(chat2) ? channelSpan() : groupSpan()).append((CharSequence) " ").append((CharSequence) chat2.title);
            }
            return chat2.title;
        }
        if (!(peer instanceof TLRPC.TL_peerChannel) || (chat = MessagesController.getInstance(i).getChat(Long.valueOf(peer.channel_id))) == null) {
            return _UrlKt.FRAGMENT_ENCODE_SET;
        }
        if (z) {
            return new SpannableStringBuilder(ChatObject.isChannelAndNotMegaGroup(chat) ? channelSpan() : groupSpan()).append((CharSequence) " ").append((CharSequence) chat.title);
        }
        return chat.title;
    }

    public static CharSequence peerNameWithIcon(int i, long j) {
        return peerNameWithIcon(i, j, false);
    }

    public static CharSequence peerNameWithIcon(int i, long j, boolean z) {
        if (j >= 0) {
            TLRPC.User user = MessagesController.getInstance(i).getUser(Long.valueOf(j));
            if (user != null) {
                return AndroidUtilities.removeDiacritics(UserObject.getUserName(user));
            }
            return _UrlKt.FRAGMENT_ENCODE_SET;
        }
        TLRPC.Chat chat = MessagesController.getInstance(i).getChat(Long.valueOf(-j));
        if (chat != null) {
            return new SpannableStringBuilder(ChatObject.isChannelAndNotMegaGroup(chat) ? channelSpan() : groupSpan()).append((CharSequence) " ").append((CharSequence) AndroidUtilities.removeDiacritics(chat.title));
        }
        return _UrlKt.FRAGMENT_ENCODE_SET;
    }

    public CharSequence getReplyQuoteNameWithIcon() {
        CharSequence charSequencePeerNameWithIcon;
        CharSequence spannableStringBuilder;
        TLRPC.Message message = this.messageOwner;
        if (message == null) {
            return _UrlKt.FRAGMENT_ENCODE_SET;
        }
        TLRPC.MessageReplyHeader messageReplyHeader = message.reply_to;
        CharSequence charSequencePeerNameWithIcon2 = null;
        if (messageReplyHeader == null) {
            boolean zIsChatDialog = DialogObject.isChatDialog(getDialogId());
            int i = this.currentAccount;
            if (zIsChatDialog) {
                charSequencePeerNameWithIcon = peerNameWithIcon(i, getDialogId());
            } else {
                spannableStringBuilder = peerNameWithIcon(i, getDialogId());
                charSequencePeerNameWithIcon2 = spannableStringBuilder;
                charSequencePeerNameWithIcon = null;
            }
        } else if (messageReplyHeader.reply_from == null) {
            charSequencePeerNameWithIcon = null;
        } else {
            TLRPC.Peer peer = messageReplyHeader.reply_to_peer_id;
            boolean z = peer == null || DialogObject.getPeerDialogId(peer) != getDialogId();
            TLRPC.MessageFwdHeader messageFwdHeader = this.messageOwner.reply_to.reply_from;
            TLRPC.Peer peer2 = messageFwdHeader.from_id;
            if (peer2 != null) {
                boolean z2 = peer2 instanceof TLRPC.TL_peerUser;
                int i2 = this.currentAccount;
                if (z2) {
                    spannableStringBuilder = peerNameWithIcon(i2, peer2, z);
                    charSequencePeerNameWithIcon2 = spannableStringBuilder;
                    charSequencePeerNameWithIcon = null;
                } else {
                    charSequencePeerNameWithIcon = peerNameWithIcon(i2, peer2, z);
                }
            } else {
                TLRPC.Peer peer3 = messageFwdHeader.saved_from_peer;
                if (peer3 != null) {
                    boolean z3 = peer3 instanceof TLRPC.TL_peerUser;
                    int i3 = this.currentAccount;
                    if (z3) {
                        spannableStringBuilder = peerNameWithIcon(i3, peer3, z);
                    } else {
                        charSequencePeerNameWithIcon = peerNameWithIcon(i3, peer3, z);
                    }
                } else {
                    if (!TextUtils.isEmpty(messageFwdHeader.from_name)) {
                        if (z) {
                            spannableStringBuilder = new SpannableStringBuilder(userSpan()).append((CharSequence) " ").append((CharSequence) this.messageOwner.reply_to.reply_from.from_name);
                        } else {
                            spannableStringBuilder = new SpannableStringBuilder(this.messageOwner.reply_to.reply_from.from_name);
                        }
                    }
                    charSequencePeerNameWithIcon = null;
                }
                charSequencePeerNameWithIcon2 = spannableStringBuilder;
                charSequencePeerNameWithIcon = null;
            }
        }
        TLRPC.Peer peer4 = this.messageOwner.reply_to.reply_to_peer_id;
        if (peer4 != null && DialogObject.getPeerDialogId(peer4) != getDialogId()) {
            TLRPC.Peer peer5 = this.messageOwner.reply_to.reply_to_peer_id;
            boolean z4 = peer5 instanceof TLRPC.TL_peerUser;
            int i4 = this.currentAccount;
            if (z4) {
                charSequencePeerNameWithIcon2 = peerNameWithIcon(i4, peer5, true);
            } else {
                charSequencePeerNameWithIcon = peerNameWithIcon(i4, peer5);
            }
        }
        MessageObject messageObject = this.replyMessageObject;
        if (messageObject != null) {
            if (messageObject.messageOwner instanceof TLRPC.TL_messageEmpty) {
                charSequencePeerNameWithIcon2 = new SpannableStringBuilder(userSpan()).append((CharSequence) " ").append((CharSequence) LocaleController.getString(C2797R.string.StarsTransactionUnknown));
            } else if (DialogObject.isChatDialog(messageObject.getSenderId())) {
                if (charSequencePeerNameWithIcon == null) {
                    charSequencePeerNameWithIcon = peerNameWithIcon(this.currentAccount, this.replyMessageObject.getSenderId());
                }
            } else if (charSequencePeerNameWithIcon2 == null) {
                MessageObject messageObject2 = this.replyMessageObject;
                if (messageObject2.messageOwner.fwd_from != null) {
                    charSequencePeerNameWithIcon2 = messageObject2.getForwardedName();
                } else {
                    charSequencePeerNameWithIcon2 = peerNameWithIcon(this.currentAccount, messageObject2.getSenderId());
                }
            }
        }
        if (charSequencePeerNameWithIcon == null || charSequencePeerNameWithIcon2 == null) {
            return charSequencePeerNameWithIcon != null ? charSequencePeerNameWithIcon : charSequencePeerNameWithIcon2 != null ? charSequencePeerNameWithIcon2 : LocaleController.getString(C2797R.string.Loading);
        }
        return new SpannableStringBuilder(charSequencePeerNameWithIcon2).append((CharSequence) " ").append(charSequencePeerNameWithIcon);
    }

    public boolean hasLinkMediaToMakeSmall() {
        boolean z = !this.isRestrictedMessage && (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && (getMedia(this.messageOwner).webpage instanceof TLRPC.TL_webPage);
        TLRPC.WebPage webPage = z ? getMedia(this.messageOwner).webpage : null;
        String str = webPage != null ? webPage.type : null;
        return z && !isGiveawayOrGiveawayResults() && webPage != null && (webPage.photo != null || isVideoDocument(webPage.document)) && !((TextUtils.isEmpty(webPage.description) && TextUtils.isEmpty(webPage.title)) || isSponsored() || "telegram_megagroup".equals(str) || "telegram_background".equals(str) || "telegram_voicechat".equals(str) || "telegram_videochat".equals(str) || "telegram_livestream".equals(str) || "telegram_user".equals(str) || "telegram_story".equals(str) || "telegram_channel_boost".equals(str) || "telegram_group_boost".equals(str) || "telegram_chat".equals(str));
    }

    public boolean isLinkMediaSmall() {
        TLRPC.WebPage webPage = (!this.isRestrictedMessage && (getMedia(this.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && (getMedia(this.messageOwner).webpage instanceof TLRPC.TL_webPage)) ? getMedia(this.messageOwner).webpage : null;
        String str = webPage != null ? webPage.type : null;
        if (webPage != null && TextUtils.isEmpty(webPage.description) && TextUtils.isEmpty(webPage.title)) {
            return false;
        }
        return Common.ASSET_APP.equals(str) || "profile".equals(str) || "article".equals(str) || "telegram_bot".equals(str) || "telegram_user".equals(str) || "telegram_channel".equals(str) || "telegram_megagroup".equals(str) || "telegram_voicechat".equals(str) || "telegram_videochat".equals(str) || "telegram_livestream".equals(str) || "telegram_channel_boost".equals(str) || "telegram_group_boost".equals(str) || "telegram_chat".equals(str);
    }

    public static class TextRange {
        public boolean code;
        public boolean collapse;
        public int end;
        public String language;
        public boolean quote;
        public int start;

        public TextRange(int i, int i2) {
            this.start = i;
            this.end = i2;
        }

        public TextRange(int i, int i2, boolean z, boolean z2, boolean z3, String str) {
            this.start = i;
            this.end = i2;
            this.quote = z;
            this.code = z2;
            this.collapse = z && z3;
            this.language = str;
        }
    }

    public static void cutIntoRanges(CharSequence charSequence, ArrayList<TextRange> arrayList) {
        String str;
        int i;
        if (charSequence == null) {
            return;
        }
        if (!(charSequence instanceof Spanned)) {
            arrayList.add(new TextRange(0, charSequence.length()));
            return;
        }
        TreeSet<Integer> treeSet = new TreeSet();
        HashMap map = new HashMap();
        Spanned spanned = (Spanned) charSequence;
        QuoteSpan.QuoteStyleSpan[] quoteStyleSpanArr = (QuoteSpan.QuoteStyleSpan[]) spanned.getSpans(0, spanned.length(), QuoteSpan.QuoteStyleSpan.class);
        int i2 = 0;
        while (true) {
            if (i2 >= quoteStyleSpanArr.length) {
                break;
            }
            QuoteSpan.QuoteStyleSpan quoteStyleSpan = quoteStyleSpanArr[i2];
            quoteStyleSpan.span.adaptLineHeight = false;
            int spanStart = spanned.getSpanStart(quoteStyleSpan);
            int spanEnd = spanned.getSpanEnd(quoteStyleSpanArr[i2]);
            treeSet.add(Integer.valueOf(spanStart));
            map.put(Integer.valueOf(spanStart), Integer.valueOf((map.containsKey(Integer.valueOf(spanStart)) ? ((Integer) map.get(Integer.valueOf(spanStart))).intValue() : 0) | (quoteStyleSpanArr[i2].span.isCollapsing ? 16 : 1)));
            treeSet.add(Integer.valueOf(spanEnd));
            map.put(Integer.valueOf(spanEnd), Integer.valueOf((map.containsKey(Integer.valueOf(spanEnd)) ? ((Integer) map.get(Integer.valueOf(spanEnd))).intValue() : 0) | 2));
            i2++;
        }
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            int iIntValue = num.intValue();
            if (iIntValue >= 0 && iIntValue < spanned.length() && map.containsKey(num)) {
                int iIntValue2 = ((Integer) map.get(num)).intValue();
                if ((iIntValue2 & 17) != 0 && (iIntValue2 & 2) != 0 && spanned.charAt(iIntValue) != '\n' && (iIntValue - 1 <= 0 || spanned.charAt(i) != '\n')) {
                    it.remove();
                    map.remove(num);
                }
            }
        }
        CodeHighlighting.Span[] spanArr = (CodeHighlighting.Span[]) spanned.getSpans(0, spanned.length(), CodeHighlighting.Span.class);
        for (int i3 = 0; i3 < spanArr.length; i3++) {
            int spanStart2 = spanned.getSpanStart(spanArr[i3]);
            int spanEnd2 = spanned.getSpanEnd(spanArr[i3]);
            treeSet.add(Integer.valueOf(spanStart2));
            map.put(Integer.valueOf(spanStart2), Integer.valueOf((map.containsKey(Integer.valueOf(spanStart2)) ? ((Integer) map.get(Integer.valueOf(spanStart2))).intValue() : 0) | 4));
            treeSet.add(Integer.valueOf(spanEnd2));
            map.put(Integer.valueOf(spanEnd2), Integer.valueOf((map.containsKey(Integer.valueOf(spanEnd2)) ? ((Integer) map.get(Integer.valueOf(spanEnd2))).intValue() : 0) | 8));
        }
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        boolean z = false;
        for (Integer num2 : treeSet) {
            int iIntValue3 = num2.intValue();
            int iIntValue4 = ((Integer) map.get(num2)).intValue();
            if (i7 != iIntValue3) {
                int i8 = iIntValue3 - 1;
                if (i8 >= 0 && i8 < charSequence.length() && charSequence.charAt(i8) == '\n') {
                    iIntValue3--;
                }
                int i9 = iIntValue3;
                if ((iIntValue4 & 8) == 0 || i6 >= spanArr.length) {
                    str = null;
                } else {
                    str = spanArr[i6].lng;
                    i6++;
                }
                int i10 = i6;
                arrayList.add(new TextRange(i7, i9, i4 > 0, i5 > 0, z, str));
                i7 = i9 + 1;
                if (i7 >= charSequence.length() || charSequence.charAt(i9) != '\n') {
                    i7 = i9;
                }
                i6 = i10;
            }
            if ((iIntValue4 & 2) != 0) {
                i4--;
            }
            if ((iIntValue4 & 1) != 0 || (iIntValue4 & 16) != 0) {
                i4++;
                z = (iIntValue4 & 16) != 0;
            }
            if ((iIntValue4 & 8) != 0) {
                i5--;
            }
            if ((iIntValue4 & 4) != 0) {
                i5++;
            }
        }
        if (i7 < charSequence.length()) {
            arrayList.add(new TextRange(i7, charSequence.length(), i4 > 0, i5 > 0, z, null));
        }
    }

    public void toggleChannelRecommendations() {
        expandChannelRecommendations(!this.channelJoinedExpanded);
    }

    public void expandChannelRecommendations(boolean z) {
        SharedPreferences.Editor editorEdit = MessagesController.getInstance(this.currentAccount).getMainSettings().edit();
        String str = "c" + getDialogId() + "_rec";
        this.channelJoinedExpanded = z;
        editorEdit.putBoolean(str, z).apply();
    }

    public static int findQuoteStart(String str, String str2, int i) {
        if (str == null || str2 == null) {
            return -1;
        }
        if (i == -1) {
            return str.indexOf(str2);
        }
        if (str2.length() + i < str.length() && str.startsWith(str2, i)) {
            return i;
        }
        int iIndexOf = str.indexOf(str2, i);
        int iLastIndexOf = str.lastIndexOf(str2, i);
        return (iIndexOf != -1 && (iLastIndexOf == -1 || iIndexOf - i < i - iLastIndexOf)) ? iIndexOf : iLastIndexOf;
    }

    public void applyQuickReply(String str, int i) {
        TLRPC.Message message = this.messageOwner;
        if (message == null) {
            return;
        }
        if (i != 0) {
            message.flags |= TLObject.FLAG_30;
            message.quick_reply_shortcut_id = i;
            TLRPC.TL_inputQuickReplyShortcutId tL_inputQuickReplyShortcutId = new TLRPC.TL_inputQuickReplyShortcutId();
            tL_inputQuickReplyShortcutId.shortcut_id = i;
            this.messageOwner.quick_reply_shortcut = tL_inputQuickReplyShortcutId;
            return;
        }
        if (str != null) {
            TLRPC.TL_inputQuickReplyShortcut tL_inputQuickReplyShortcut = new TLRPC.TL_inputQuickReplyShortcut();
            tL_inputQuickReplyShortcut.shortcut = str;
            this.messageOwner.quick_reply_shortcut = tL_inputQuickReplyShortcut;
        } else {
            message.flags &= -1073741825;
            message.quick_reply_shortcut_id = 0;
            message.quick_reply_shortcut = null;
        }
    }

    public static int getQuickReplyId(TLRPC.Message message) {
        if (message == null) {
            return 0;
        }
        if ((message.flags & TLObject.FLAG_30) != 0) {
            return message.quick_reply_shortcut_id;
        }
        TLRPC.InputQuickReplyShortcut inputQuickReplyShortcut = message.quick_reply_shortcut;
        if (inputQuickReplyShortcut instanceof TLRPC.TL_inputQuickReplyShortcutId) {
            return ((TLRPC.TL_inputQuickReplyShortcutId) inputQuickReplyShortcut).shortcut_id;
        }
        return 0;
    }

    public static int getQuickReplyId(int i, TLRPC.Message message) {
        QuickRepliesController.QuickReply quickReplyFindReply;
        if (message == null) {
            return 0;
        }
        if ((message.flags & TLObject.FLAG_30) != 0) {
            return message.quick_reply_shortcut_id;
        }
        TLRPC.InputQuickReplyShortcut inputQuickReplyShortcut = message.quick_reply_shortcut;
        if (inputQuickReplyShortcut instanceof TLRPC.TL_inputQuickReplyShortcutId) {
            return ((TLRPC.TL_inputQuickReplyShortcutId) inputQuickReplyShortcut).shortcut_id;
        }
        String quickReplyName = getQuickReplyName(message);
        if (quickReplyName == null || (quickReplyFindReply = QuickRepliesController.getInstance(i).findReply(quickReplyName)) == null) {
            return 0;
        }
        return quickReplyFindReply.f1489id;
    }

    public int getQuickReplyId() {
        return getQuickReplyId(this.messageOwner);
    }

    public static String getQuickReplyName(TLRPC.Message message) {
        if (message == null) {
            return null;
        }
        TLRPC.InputQuickReplyShortcut inputQuickReplyShortcut = message.quick_reply_shortcut;
        if (inputQuickReplyShortcut instanceof TLRPC.TL_inputQuickReplyShortcut) {
            return ((TLRPC.TL_inputQuickReplyShortcut) inputQuickReplyShortcut).shortcut;
        }
        return null;
    }

    public String getQuickReplyName() {
        return getQuickReplyName(this.messageOwner);
    }

    public String getQuickReplyDisplayName() {
        String quickReplyName = getQuickReplyName();
        if (quickReplyName != null) {
            return quickReplyName;
        }
        QuickRepliesController.QuickReply quickReplyFindReply = QuickRepliesController.getInstance(this.currentAccount).findReply(getQuickReplyId());
        if (quickReplyFindReply != null) {
            return quickReplyFindReply.name;
        }
        return _UrlKt.FRAGMENT_ENCODE_SET;
    }

    public static boolean isQuickReply(TLRPC.Message message) {
        if (message != null) {
            return ((message.flags & TLObject.FLAG_30) == 0 && message.quick_reply_shortcut == null) ? false : true;
        }
        return false;
    }

    public boolean isQuickReply() {
        return isQuickReply(this.messageOwner);
    }

    public TLRPC.TL_availableEffect getEffect() {
        TLRPC.Message message = this.messageOwner;
        if (message == null || (message.flags2 & 4) == 0) {
            return null;
        }
        return MessagesController.getInstance(this.currentAccount).getEffect(this.messageOwner.effect);
    }

    public long getEffectId() {
        TLRPC.Message message = this.messageOwner;
        if (message == null || (message.flags2 & 4) == 0) {
            return 0L;
        }
        return message.effect;
    }

    public TLRPC.TL_factCheck getFactCheck() {
        return FactCheckController.getInstance(this.currentAccount).getFactCheck(this);
    }

    public CharSequence getFactCheckText() {
        if (!isFactCheckable()) {
            return null;
        }
        TLRPC.TL_factCheck factCheck = getFactCheck();
        if (factCheck == null || factCheck.text == null) {
            this.factCheckText = null;
            return null;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(factCheck.text.text);
        addEntitiesToText(spannableStringBuilder, factCheck.text.entities, isOutOwner(), false, false, false);
        this.factCheckText = spannableStringBuilder;
        return spannableStringBuilder;
    }

    public boolean isFactCheckable() {
        if (getId() < 0 || isSponsored()) {
            return false;
        }
        int i = this.type;
        return i == 0 || i == 2 || i == 1 || i == 3 || i == 8 || i == 9;
    }

    public boolean hasEntitiesFromServer() {
        TLRPC.Message message = this.messageOwner;
        if (message == null || message.entities == null) {
            return false;
        }
        for (int i = 0; i < this.messageOwner.entities.size(); i++) {
            TLRPC.MessageEntity messageEntity = this.messageOwner.entities.get(i);
            if ((messageEntity instanceof TLRPC.TL_messageEntityPhone) || (messageEntity instanceof TLRPC.TL_messageEntityBankCard)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAlbumSingle() {
        return getMedia(this) instanceof TLRPC.TL_messageMediaPaidMedia;
    }

    public boolean hasVideoQualities() {
        return hasVideoQualities(true);
    }

    public boolean hasVideoQualities(boolean z) {
        TLRPC.MessageMedia messageMedia;
        if (this.videoQualitiesCached == null) {
            try {
                TLRPC.Message message = this.messageOwner;
                boolean z2 = false;
                if (message != null && (messageMedia = message.media) != null && messageMedia.document != null && !messageMedia.alt_documents.isEmpty()) {
                    int i = this.currentAccount;
                    TLRPC.Message message2 = this.messageOwner;
                    ArrayList<VideoPlayer.Quality> qualities = VideoPlayer.getQualities(i, message2 != null ? message2.media : null, z);
                    this.videoQualities = qualities;
                    if (qualities != null && qualities.size() > 1) {
                        z2 = true;
                    }
                    this.videoQualitiesCached = Boolean.valueOf(z2);
                    this.highestQuality = VideoPlayer.getQualityForPlayer(this.videoQualities);
                    this.thumbQuality = VideoPlayer.getQualityForThumb(this.videoQualities);
                    this.cachedQuality = VideoPlayer.getCachedQuality(this.videoQualities);
                }
                this.videoQualitiesCached = Boolean.FALSE;
                return false;
            } catch (Exception e) {
                FileLog.m1048e(e);
                this.videoQualitiesCached = Boolean.FALSE;
            }
        }
        return this.videoQualitiesCached.booleanValue();
    }

    public boolean isStarGiftAction() {
        TLRPC.Message message = this.messageOwner;
        if (message == null) {
            return false;
        }
        TLRPC.MessageAction messageAction = message.action;
        return (messageAction instanceof TLRPC.TL_messageActionStarGift) || (messageAction instanceof TLRPC.TL_messageActionStarGiftUnique);
    }

    public boolean mediaExists() {
        VideoPlayer.VideoUri videoUri;
        if (hasVideoQualities() && (videoUri = this.highestQuality) != null) {
            return videoUri.isCached();
        }
        return this.mediaExists;
    }

    public void updateQualitiesCached(boolean z) {
        ArrayList<VideoPlayer.Quality> arrayList = this.videoQualities;
        if (arrayList == null) {
            this.cachedQuality = null;
            hasVideoQualities(z);
            return;
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            VideoPlayer.Quality quality = arrayList.get(i);
            i++;
            ArrayList<VideoPlayer.VideoUri> arrayList2 = quality.uris;
            int size2 = arrayList2.size();
            int i2 = 0;
            while (i2 < size2) {
                VideoPlayer.VideoUri videoUri = arrayList2.get(i2);
                i2++;
                videoUri.updateCached(z);
            }
        }
        this.highestQuality = VideoPlayer.getQualityForPlayer(this.videoQualities);
        this.thumbQuality = VideoPlayer.getQualityForThumb(this.videoQualities);
        this.cachedQuality = VideoPlayer.getCachedQuality(this.videoQualities);
    }

    public boolean areTags() {
        TLRPC.TL_messageReactions tL_messageReactions;
        TLRPC.Message message = this.messageOwner;
        if (message == null || (tL_messageReactions = message.reactions) == null) {
            return false;
        }
        return tL_messageReactions.reactions_as_tags;
    }

    public int getVideoStartsTimestamp() {
        String str;
        Integer num = this.cachedStartsTimestamp;
        if (num != null) {
            return num.intValue();
        }
        if (!isVideo()) {
            this.cachedStartsTimestamp = -1;
            return -1;
        }
        TLRPC.MessageMedia media = getMedia(this);
        int i = media.video_timestamp;
        if (i != 0) {
            return i;
        }
        TLRPC.WebPage webPage = media.webpage;
        if (webPage != null && (str = webPage.url) != null) {
            try {
                int timestampFromLink = LaunchActivity.getTimestampFromLink(Uri.parse(str));
                this.cachedStartsTimestamp = Integer.valueOf(timestampFromLink);
                return timestampFromLink;
            } catch (Exception unused) {
            }
        }
        this.cachedStartsTimestamp = -1;
        return -1;
    }

    public float getVideoSavedProgress() {
        if (this.cachedSavedTimestamp != null) {
            return PhotoViewer.getSavedProgressFast(this);
        }
        float savedProgress = PhotoViewer.getSavedProgress(this);
        this.cachedSavedTimestamp = Float.valueOf(savedProgress);
        return savedProgress;
    }

    public TLRPC.Photo getVideoCover() {
        TLRPC.WebPage webPage;
        TLRPC.MessageMedia media = getMedia(this);
        if (media instanceof TLRPC.TL_messageMediaDocument) {
            return ((TLRPC.TL_messageMediaDocument) media).video_cover;
        }
        if (media == null || (webPage = media.webpage) == null || !webPage.video_cover_photo) {
            return null;
        }
        return webPage.photo;
    }

    public boolean hasVideoCover() {
        return getVideoCover() != null;
    }

    public boolean isPaid() {
        TLRPC.Message message = this.messageOwner;
        return message != null && message.paid_message_stars > 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:97:0x000e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.CharSequence getActionSuggestionApprovalText(java.lang.String r14, java.lang.String r15) {
        /*
            Method dump skipped, instruction units count: 525
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.getActionSuggestionApprovalText(java.lang.String, java.lang.String):java.lang.CharSequence");
    }

    public static TLRPC.PollAnswer findPollItem(MessageObject messageObject, byte[] bArr) {
        TLRPC.TL_messageMediaPoll tL_messageMediaPoll;
        TLRPC.Poll poll;
        TLRPC.MessageMedia media = getMedia(messageObject);
        if ((media instanceof TLRPC.TL_messageMediaPoll) && (poll = (tL_messageMediaPoll = (TLRPC.TL_messageMediaPoll) media).poll) != null && poll.answers != null) {
            for (int i = 0; i < tL_messageMediaPoll.poll.answers.size(); i++) {
                TLRPC.PollAnswer pollAnswer = tL_messageMediaPoll.poll.answers.get(i);
                if (Arrays.equals(pollAnswer.option, bArr)) {
                    return pollAnswer;
                }
            }
        }
        return null;
    }

    public static TLRPC.TodoItem findTodoItem(MessageObject messageObject, int i) {
        TLRPC.TL_messageMediaToDo tL_messageMediaToDo;
        TLRPC.TodoList todoList;
        TLRPC.MessageMedia media = getMedia(messageObject);
        if ((media instanceof TLRPC.TL_messageMediaToDo) && (todoList = (tL_messageMediaToDo = (TLRPC.TL_messageMediaToDo) media).todo) != null && todoList.list != null) {
            for (int i2 = 0; i2 < tL_messageMediaToDo.todo.list.size(); i2++) {
                TLRPC.TodoItem todoItem = tL_messageMediaToDo.todo.list.get(i2);
                if (todoItem.f1405id == i) {
                    return todoItem;
                }
            }
        }
        return null;
    }

    public static boolean isCompleted(MessageObject messageObject, int i) {
        TLRPC.TL_messageMediaToDo tL_messageMediaToDo;
        TLRPC.TodoList todoList;
        TLRPC.MessageMedia media = getMedia(messageObject);
        if (!(media instanceof TLRPC.TL_messageMediaToDo) || (todoList = (tL_messageMediaToDo = (TLRPC.TL_messageMediaToDo) media).todo) == null || todoList.list == null) {
            return false;
        }
        return isCompleted(tL_messageMediaToDo, i);
    }

    public static boolean isCompleted(TLRPC.TL_messageMediaToDo tL_messageMediaToDo, int i) {
        for (int i2 = 0; i2 < tL_messageMediaToDo.completions.size(); i2++) {
            if (tL_messageMediaToDo.completions.get(i2).f1404id == i) {
                return true;
            }
        }
        return false;
    }

    public static void toggleTodo(int i, long j, TLRPC.TL_messageMediaToDo tL_messageMediaToDo, int i2, boolean z, int i3) {
        int i4 = 0;
        while (i4 < tL_messageMediaToDo.completions.size()) {
            if (tL_messageMediaToDo.completions.get(i4).f1404id == i2) {
                tL_messageMediaToDo.completions.remove(i4);
                if (tL_messageMediaToDo.completions.isEmpty()) {
                    tL_messageMediaToDo.flags &= -2;
                }
                i4--;
            }
            i4++;
        }
        if (z) {
            TLRPC.TL_todoCompletion tL_todoCompletion = new TLRPC.TL_todoCompletion();
            tL_todoCompletion.f1404id = i2;
            tL_todoCompletion.completed_by = MessagesController.getInstance(i).getPeer(j);
            tL_todoCompletion.date = i3;
            tL_messageMediaToDo.flags |= 1;
            tL_messageMediaToDo.completions.add(tL_todoCompletion);
        }
    }

    public long getPollHash() {
        TLRPC.Message message = this.messageOwner;
        if (message != null && this.type == 17) {
            TLRPC.MessageMedia media = getMedia(message);
            if (media instanceof TLRPC.TL_messageMediaPoll) {
                return ((TLRPC.TL_messageMediaPoll) media).poll.hash;
            }
        }
        return 0L;
    }

    public boolean isPaidSuggestedPost() {
        TLRPC.Message message = this.messageOwner;
        if (message != null) {
            return message.paid_suggested_post_stars || message.paid_suggested_post_ton;
        }
        return false;
    }

    public boolean isPaidSuggestedPostProtected() {
        if (isPaidSuggestedPost()) {
            return ((long) (ConnectionsManager.getInstance(this.currentAccount).getCurrentTime() - this.messageOwner.date)) < MessagesController.getInstance(this.currentAccount).config.starsSuggestedPostAgeMin.get(TimeUnit.SECONDS);
        }
        return false;
    }

    public MessageSuggestionParams obtainSuggestionOffer() {
        TLRPC.Message message = this.messageOwner;
        if (message == null) {
            return null;
        }
        TLRPC.SuggestedPost suggestedPost = message.suggested_post;
        if (suggestedPost != null) {
            return MessageSuggestionParams.m1057of(suggestedPost);
        }
        TLRPC.MessageAction messageAction = message.action;
        if (messageAction instanceof TLRPC.TL_messageActionSuggestedPostApproval) {
            return MessageSuggestionParams.m1058of((TLRPC.TL_messageActionSuggestedPostApproval) messageAction);
        }
        return null;
    }

    public MessageSuggestionParams obtainSuggestionOfferFromReply() {
        MessageObject messageObject = this.replyMessageObject;
        if (messageObject != null) {
            return messageObject.obtainSuggestionOffer();
        }
        return null;
    }

    public boolean isEditedSuggestionOffer() {
        TLRPC.Message message;
        MessageObject messageObject = this.replyMessageObject;
        return (messageObject == null || messageObject.messageOwner == null || (message = this.messageOwner) == null || message.suggested_post == null) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getEditedSuggestionFlags() {
        /*
            r10 = this;
            org.telegram.messenger.MessageObject r0 = r10.replyMessageObject
            r1 = 0
            if (r0 == 0) goto L8
            org.telegram.tgnet.TLRPC$Message r0 = r0.messageOwner
            goto L9
        L8:
            r0 = r1
        L9:
            org.telegram.tgnet.TLRPC$Message r2 = r10.messageOwner
            r3 = 0
            if (r0 == 0) goto Lbb
            org.telegram.tgnet.TLRPC$SuggestedPost r4 = r0.suggested_post
            if (r4 == 0) goto Lbb
            if (r2 == 0) goto Lbb
            org.telegram.tgnet.TLRPC$SuggestedPost r5 = r2.suggested_post
            if (r5 != 0) goto L1a
            goto Lbb
        L1a:
            org.telegram.tgnet.tl.TL_stars$StarsAmount r6 = r4.price
            org.telegram.tgnet.tl.TL_stars$StarsAmount r7 = r5.price
            boolean r6 = org.telegram.messenger.utils.tlutils.AmountUtils$Amount.equals(r6, r7)
            r7 = 1
            r6 = r6 ^ r7
            int r4 = r4.schedule_date
            int r5 = r5.schedule_date
            if (r4 == r5) goto L2c
            r6 = r6 | 2
        L2c:
            java.lang.CharSequence r4 = r10.messageText
            org.telegram.messenger.MessageObject r5 = r10.replyMessageObject
            java.lang.CharSequence r5 = r5.messageText
            boolean r4 = android.text.TextUtils.equals(r4, r5)
            if (r4 != 0) goto L3a
            r6 = r6 | 4
        L3a:
            java.lang.CharSequence r4 = r10.caption
            org.telegram.messenger.MessageObject r10 = r10.replyMessageObject
            java.lang.CharSequence r10 = r10.caption
            boolean r10 = android.text.TextUtils.equals(r4, r10)
            if (r10 != 0) goto L48
            r6 = r6 | 4
        L48:
            org.telegram.tgnet.TLRPC$MessageMedia r10 = r0.media
            org.telegram.tgnet.TLRPC$MessageMedia r0 = r2.media
            boolean r2 = r10 instanceof org.telegram.tgnet.TLRPC.TL_messageMediaEmpty
            if (r2 == 0) goto L51
            r10 = r1
        L51:
            boolean r2 = r0 instanceof org.telegram.tgnet.TLRPC.TL_messageMediaEmpty
            if (r2 == 0) goto L56
            goto L57
        L56:
            r1 = r0
        L57:
            if (r10 == 0) goto Lad
            if (r1 == 0) goto Lad
            java.lang.Class r0 = r10.getClass()
            java.lang.Class r2 = r1.getClass()
            if (r0 != r2) goto Laa
            org.telegram.tgnet.TLRPC$Photo r0 = r10.photo
            if (r0 == 0) goto L78
            org.telegram.tgnet.TLRPC$Photo r2 = r1.photo
            if (r2 == 0) goto L78
            long r4 = r0.f1276id
            long r8 = r2.f1276id
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 == 0) goto L87
        L75:
            r6 = r6 | 8
            goto L87
        L78:
            if (r0 != 0) goto L7c
            r0 = r7
            goto L7d
        L7c:
            r0 = r3
        L7d:
            org.telegram.tgnet.TLRPC$Photo r2 = r1.photo
            if (r2 != 0) goto L83
            r2 = r7
            goto L84
        L83:
            r2 = r3
        L84:
            if (r0 == r2) goto L87
            goto L75
        L87:
            org.telegram.tgnet.TLRPC$Document r10 = r10.document
            if (r10 == 0) goto L9a
            org.telegram.tgnet.TLRPC$Document r0 = r1.document
            if (r0 == 0) goto L9a
            long r1 = r10.f1253id
            long r3 = r0.f1253id
            int r10 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r10 == 0) goto La9
            r10 = r6 | 8
            return r10
        L9a:
            if (r10 != 0) goto L9e
            r10 = r7
            goto L9f
        L9e:
            r10 = r3
        L9f:
            org.telegram.tgnet.TLRPC$Document r0 = r1.document
            if (r0 != 0) goto La4
            r3 = r7
        La4:
            if (r10 == r3) goto La9
            r10 = r6 | 8
            return r10
        La9:
            return r6
        Laa:
            r10 = r6 | 8
            return r10
        Lad:
            if (r10 != 0) goto Lb1
            r10 = r7
            goto Lb2
        Lb1:
            r10 = r3
        Lb2:
            if (r1 != 0) goto Lb5
            r3 = r7
        Lb5:
            if (r10 == r3) goto Lba
            r10 = r6 | 8
            return r10
        Lba:
            return r6
        Lbb:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessageObject.getEditedSuggestionFlags():int");
    }

    public CharSequence getMessageTextToTranslate(GroupedMessages groupedMessages, int[] iArr) {
        if (this.translated || this.isRestrictedMessage) {
            return null;
        }
        if (this.summarized) {
            return this.messageText;
        }
        int i = this.type;
        if (i == 19 || i == 15 || i == 13) {
            return null;
        }
        CharSequence messageCaption = ChatActivity.getMessageCaption(this, groupedMessages, iArr);
        if (messageCaption == null && isPoll()) {
            try {
                TLRPC.Poll poll = ((TLRPC.TL_messageMediaPoll) this.messageOwner.media).poll;
                StringBuilder sb = new StringBuilder(poll.question.text);
                sb.append("\n");
                ArrayList<TLRPC.PollAnswer> arrayList = poll.answers;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    TLRPC.PollAnswer pollAnswer = arrayList.get(i2);
                    i2++;
                    sb.append("\n🔘 ");
                    TLRPC.TL_textWithEntities tL_textWithEntities = pollAnswer.text;
                    sb.append(tL_textWithEntities == null ? _UrlKt.FRAGMENT_ENCODE_SET : tL_textWithEntities.text);
                }
                messageCaption = sb.toString();
            } catch (Exception unused) {
            }
        }
        if (messageCaption == null && isMediaEmpty(this.messageOwner)) {
            messageCaption = ChatActivity.getMessageContent(this, 0L, false);
        }
        if (messageCaption == null || !Emoji.fullyConsistsOfEmojis(messageCaption)) {
            return messageCaption;
        }
        return null;
    }

    public boolean needResendWhenEdit() {
        return (!ChatObject.isMonoForum(this.currentAccount, getDialogId()) || getFromChatId() == UserConfig.getInstance(this.currentAccount).getClientUserId() || isOutOwner()) ? false : true;
    }

    public static int getCompletionsCount(TLRPC.TL_messageMediaToDo tL_messageMediaToDo) {
        TLRPC.TodoList todoList;
        if (tL_messageMediaToDo == null || (todoList = tL_messageMediaToDo.todo) == null || todoList.list == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < tL_messageMediaToDo.completions.size(); i2++) {
            TLRPC.TodoCompletion todoCompletion = tL_messageMediaToDo.completions.get(i2);
            int i3 = 0;
            while (true) {
                if (i3 >= tL_messageMediaToDo.todo.list.size()) {
                    break;
                }
                if (tL_messageMediaToDo.todo.list.get(i3).f1405id == todoCompletion.f1404id) {
                    i++;
                    break;
                }
                i3++;
            }
        }
        return i;
    }
}
