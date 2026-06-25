package com.exteragram.messenger.preferences.chats;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import androidx.camera.core.ImageCapture$$ExternalSyntheticBackport1;
import com.exteragram.messenger.CameraType;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.VideoMessagesCamera;
import com.exteragram.messenger.camera.CameraXSession;
import com.exteragram.messenger.p011ai.AiController;
import com.exteragram.messenger.p011ai.p012ui.activities.AiPreferencesActivity;
import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.exteragram.messenger.preferences.chats.components.DoubleTapCell;
import com.exteragram.messenger.preferences.chats.components.MessagesPreviewCell;
import com.exteragram.messenger.preferences.chats.components.SliderPreviewCell;
import com.exteragram.messenger.preferences.chats.components.StickerShapeCell;
import com.exteragram.messenger.preferences.utils.SettingsRegistry;
import com.exteragram.messenger.speech.VoiceRecognitionController;
import com.exteragram.messenger.speech.p019ui.RecognitionModelDialogs;
import com.exteragram.messenger.utils.chats.DoubleTapUtils;
import com.exteragram.messenger.utils.p020ui.PopupUtils;
import com.exteragram.messenger.utils.text.LocaleUtils;
import com.exteragram.messenger.utils.text.TranslatorUtils;
import com.google.android.exoplayer2.util.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.ActionBarMenuItem;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Cells.TextCell;
import org.telegram.p035ui.Cells.TextCheckCell2;
import org.telegram.p035ui.Components.UItem;
import org.telegram.p035ui.Components.UniversalAdapter;
import org.telegram.p035ui.Stories.recorder.DualCameraView;
import org.telegram.p035ui.ThemeActivity;

/* JADX INFO: loaded from: classes.dex */
public class ChatsPreferencesActivity extends BasePreferencesActivity {
    private CharSequence[] bottomButton;
    private boolean cameraSettingsExpanded;
    private CharSequence[] cameraType;
    private CharSequence[] doubleTapActions;
    private DoubleTapCell doubleTapCell;
    private CharSequence[] doubleTapOutActions;
    private CharSequence[] doubleTapSeekDuration;
    private boolean hideReactionsExpanded;
    private final List<String> languageCodes = ImageCapture$$ExternalSyntheticBackport1.m73m(new String[]{"none", "en", "es", "zh", "hi", "fa", "fr", "ru", "pt", "de", "ja", "ko", "it", "uk", "gu", "pl", "nl", "tr", "vi", "cs", "uz", "eo", "kk", "tg", "ca"});
    private boolean messageMenuExpanded;
    private MessagesPreviewCell messagesPreviewCell;
    private boolean pauseOnMinimizeExpanded;
    private boolean quickTransitionsExpanded;
    private CharSequence[] recognitionLanguageOptions;
    private boolean replyElementsExpanded;
    private ActionBarMenuItem resetItem;
    private StickerShapeCell stickerShapeCell;
    private SliderPreviewCell stickerSizeCell;
    private CharSequence[] videoMessagesCamera;

    public enum ChatsItem {
        STICKER_SIZE,
        HIDE_STICKER_TIME,
        REPLY_ELEMENTS,
        REPLY_COLORS,
        REPLY_EMOJI,
        REPLY_BACKGROUND,
        STICKER_SHAPE,
        AI,
        CHAT_SETTINGS,
        UNLIMITED_RECENT_STICKERS,
        HIDE_REACTIONS,
        DOUBLE_TAP,
        DOUBLE_TAP_ACTION,
        DOUBLE_TAP_ACTION_OUT_OWNER,
        BOTTOM_BUTTON,
        ADMIN_SHORTCUTS,
        QUICK_TRANSITIONS,
        QUICK_TRANSITION_FOR_CHANNELS,
        QUICK_TRANSITION_FOR_TOPICS,
        DISABLE_GREETING_STICKER,
        HIDE_KEYBOARD_ON_SCROLL,
        ADD_COMMA_AFTER_MENTION,
        HIDE_SEND_AS_PEER,
        MESSAGES_PREVIEW,
        REMOVE_MESSAGE_TAIL,
        REPLACE_EDITED_WITH_ICON,
        SHOW_ONLINE_STATUS,
        HIDE_SHARE_BUTTON,
        SHOW_RESULTS_BEFORE_VOTING,
        MESSAGE_MENU,
        COPY_PHOTO,
        SAVE,
        REPEAT,
        CLEAR,
        HISTORY,
        REPORT,
        GENERATE,
        DETAILS,
        GROUP_MESSAGE_MENU,
        MESSAGE_REACTIONS,
        GROUPS,
        CHANNELS,
        PRIVATE_CHATS,
        SPEECH_RECOGNITION_LANGUAGE,
        POST_PROCESSING_WITH_AI,
        DELETE_RECOGNITION_MODEL,
        CAMERA_TYPE,
        CAMERA_SETTINGS,
        DUAL_CAMERA,
        EXTENDED_FRAMES_PER_SECOND,
        CAMERA_STABILIZATION,
        CAMERA_MIRROR_MODE,
        VIDEO_MESSAGES_CAMERA,
        REMEMBER_LAST_USED_CAMERA,
        START_WITH_WIDE_ANGLE_CAMERA,
        STATIC_ZOOM,
        ALWAYS_SEND_IN_HD,
        HIDE_CAMERA_TILE,
        DOUBLE_TAP_SEEK_DURATION,
        PREFER_ORIGINAL_QUALITY,
        SWIPE_TO_PIP,
        UNMUTE_WITH_VOLUME_BUTTONS,
        PAUSE_ON_MINIMIZE,
        PAUSE_ON_MINIMIZE_VIDEO,
        PAUSE_ON_MINIMIZE_VOICE,
        PAUSE_ON_MINIMIZE_ROUND;

        public int getId() {
            return ordinal() + 1;
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public void initializeOptionStrings() {
        this.doubleTapActions = DoubleTapUtils.getDoubleTapActions(false);
        this.doubleTapOutActions = DoubleTapUtils.getDoubleTapActions(true);
        this.bottomButton = new CharSequence[]{LocaleController.getString(C2797R.string.Hide), LocaleUtils.capitalize(LocaleController.getString(C2797R.string.ChannelMute)), LocaleUtils.capitalize(LocaleController.getString(C2797R.string.ChannelDiscuss))};
        this.videoMessagesCamera = new CharSequence[]{LocaleController.getString(C2797R.string.VideoMessagesCameraFront), LocaleController.getString(C2797R.string.VideoMessagesCameraRear), LocaleController.getString(C2797R.string.VideoMessagesCameraAsk)};
        this.doubleTapSeekDuration = new CharSequence[]{LocaleController.formatPluralString("Seconds", 5, new Object[0]), LocaleController.formatPluralString("Seconds", 10, new Object[0]), LocaleController.formatPluralString("Seconds", 15, new Object[0]), LocaleController.formatPluralString("Seconds", 30, new Object[0])};
        this.cameraType = new CharSequence[]{"Camera 1", "Camera 2", "Camera X"};
        this.recognitionLanguageOptions = (CharSequence[]) this.languageCodes.stream().map(new Function() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return RecognitionModelDialogs.getRecognitionLanguageOption((String) obj);
            }
        }).toArray(new IntFunction() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda3
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return ChatsPreferencesActivity.$r8$lambda$PwyMHwQkb6Kklycp0iIwqZDN6KE(i);
            }
        });
    }

    public static /* synthetic */ CharSequence[] $r8$lambda$PwyMHwQkb6Kklycp0iIwqZDN6KE(int i) {
        return new CharSequence[i];
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p035ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.stickerSizeCell = new SliderPreviewCell(this.parentLayout, context, ChatsItem.STICKER_SIZE.getId(), 4, 20, ExteraConfig.getStickerSize(), LocaleController.getString(C2797R.string.StickerSize), LocaleController.getString(C2797R.string.StickerSizeLeft), LocaleController.getString(C2797R.string.StickerSizeRight), false).setListener(new SliderPreviewCell.OnSliderChangedListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda0
            @Override // com.exteragram.messenger.preferences.chats.components.SliderPreviewCell.OnSliderChangedListener
            public final void onChanged(float f) {
                this.f$0.lambda$createView$1(f);
            }
        });
        this.stickerShapeCell = new StickerShapeCell(context) { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity.1
            @Override // com.exteragram.messenger.preferences.chats.components.StickerShapeCell
            public void updateStickerPreview() {
                ((BaseFragment) ChatsPreferencesActivity.this).parentLayout.rebuildFragments(0);
                ChatsPreferencesActivity.this.stickerSizeCell.invalidate();
            }
        };
        this.doubleTapCell = new DoubleTapCell(context);
        this.messagesPreviewCell = new MessagesPreviewCell(context, this.parentLayout, 1);
        View viewCreateView = super.createView(context);
        ActionBarMenuItem actionBarMenuItemAddItem = this.actionBar.createMenu().addItem(0, C2797R.drawable.msg_reset);
        this.resetItem = actionBarMenuItemAddItem;
        actionBarMenuItemAddItem.setContentDescription(LocaleController.getString(C2797R.string.Reset));
        this.resetItem.setVisibility(ExteraConfig.getStickerSize() == 12.0f ? 8 : 0);
        this.resetItem.setTag(null);
        this.resetItem.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$3(view);
            }
        });
        this.fragmentView = viewCreateView;
        return viewCreateView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$1(float f) {
        ExteraConfig.setStickerSize(f);
        ActionBarMenuItem actionBarMenuItem = this.resetItem;
        if (actionBarMenuItem == null || actionBarMenuItem.getVisibility() == 0) {
            return;
        }
        AndroidUtilities.updateViewVisibilityAnimated(this.resetItem, true, 0.5f, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$3(View view) {
        AndroidUtilities.updateViewVisibilityAnimated(this.resetItem, false, 0.5f, true);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(ExteraConfig.getStickerSize(), 12.0f);
        valueAnimatorOfFloat.setDuration(200L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda61
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$createView$2(valueAnimator);
            }
        });
        valueAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$2(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        ExteraConfig.setStickerSize(fFloatValue);
        this.stickerSizeCell.seekBar.setProgress(fFloatValue);
        this.stickerSizeCell.invalidate();
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2797R.string.SearchAllChatsShort);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public void fillItems(ArrayList<UItem> arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asCustom(ChatsItem.STICKER_SIZE.getId(), this.stickerSizeCell).setLinkAlias("stickerSize", this));
        arrayList.add(UItem.asCheck(ChatsItem.HIDE_STICKER_TIME.getId(), LocaleController.getString(C2797R.string.StickerTime)).setChecked(ExteraConfig.getHideStickerTime()).setSearchable(this).setLinkAlias("hideStickerTime", this));
        int id = ChatsItem.REPLY_ELEMENTS.getId();
        String string = LocaleController.getString(C2797R.string.RepliesTitle);
        Locale locale = Locale.US;
        arrayList.add(UItem.asExteraExpandableSwitch(id, string, String.format(locale, "%d/%d", Integer.valueOf(getReplyElementsSelectedCount(false)), Integer.valueOf(getReplyElementsSelectedCount(true))), new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleReplyElementsSwitchClick(view);
            }
        }).setChecked(getReplyElementsSelectedCount(false) > 0).setCollapsed(!this.replyElementsExpanded).setSearchable(this).setLinkAlias("replyElements", this));
        if (this.replyElementsExpanded) {
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.REPLY_COLORS.getId(), LocaleController.getString(C2797R.string.BackgroundColors)).setChecked(ExteraConfig.getReplyColors()).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.REPLY_EMOJI.getId(), LocaleController.getString(C2797R.string.Emoji)).setChecked(ExteraConfig.getReplyEmoji()).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.REPLY_BACKGROUND.getId(), LocaleController.getString(C2797R.string.ReplyBackground)).setChecked(ExteraConfig.getReplyBackground()).pad());
        }
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.StickerShape)));
        arrayList.add(UItem.asCustom(ChatsItem.STICKER_SHAPE.getId(), this.stickerShapeCell).setLinkAlias("stickerShape", this));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asButtonWithSubtext(ChatsItem.AI.getId(), C2797R.drawable.ai_chat, LocaleController.getString(C2797R.string.AIChat), LocaleController.getString(C2797R.string.AIChatInfo), 64, 60).setSearchable(this).setLinkAlias("aiChat", this));
        arrayList.add(UItem.asButtonWithSubtext(ChatsItem.CHAT_SETTINGS.getId(), C2797R.drawable.msg_discussion, LocaleController.getString(C2797R.string.ChatSettings), LocaleController.getString(C2797R.string.ChatSettingsInfo), 64, 60).setLinkAlias("chatSettings", this));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.StickersName)));
        arrayList.add(UItem.asCheck(ChatsItem.UNLIMITED_RECENT_STICKERS.getId(), LocaleController.getString(C2797R.string.UnlimitedRecentStickers)).setChecked(ExteraConfig.getUnlimitedRecentStickers()).setSearchable(this).setLinkAlias("unlimitedRecentStickers", this));
        arrayList.add(UItem.asExteraExpandableSwitch(ChatsItem.HIDE_REACTIONS.getId(), LocaleController.getString(C2797R.string.HideReactions), String.format(locale, "%d/%d", Integer.valueOf(getHideReactionsElementsSelectedCount(false)), Integer.valueOf(getHideReactionsElementsSelectedCount(true))), new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleHideReactionsSwitchClick(view);
            }
        }).setChecked(getHideReactionsElementsSelectedCount(false) > 0).setCollapsed(!this.hideReactionsExpanded).setSearchable(this).setLinkAlias("hideReactions", this));
        if (this.hideReactionsExpanded) {
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.CHANNELS.getId(), LocaleController.getString(C2797R.string.ChannelsTab)).setChecked(ExteraConfig.getHideReactionsInChannels()).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.GROUPS.getId(), LocaleController.getString(C2797R.string.SaveToGalleryGroups)).setChecked(ExteraConfig.getHideReactionsInGroups()).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.PRIVATE_CHATS.getId(), LocaleController.getString(C2797R.string.PrivateChats)).setChecked(ExteraConfig.getHideReactionsInPrivateChats()).pad());
        }
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.HideReactionsInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.DoubleTap)));
        arrayList.add(UItem.asCustom(ChatsItem.DOUBLE_TAP.getId(), this.doubleTapCell));
        arrayList.add(UItem.asButton(ChatsItem.DOUBLE_TAP_ACTION.getId(), LocaleController.getString(C2797R.string.DoubleTapIncoming), DoubleTapUtils.getDoubleTapActionLabel(ExteraConfig.getDoubleTapAction(), false)).setSearchable(this).setLinkAlias("doubleTapIncoming", this));
        arrayList.add(UItem.asButton(ChatsItem.DOUBLE_TAP_ACTION_OUT_OWNER.getId(), LocaleController.getString(C2797R.string.DoubleTapOutgoing), DoubleTapUtils.getDoubleTapActionLabel(ExteraConfig.getDoubleTapActionOutOwner(), true)).setSearchable(this).setLinkAlias("doubleTapOutgoing", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.DoubleTapInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.MainTabsChats)));
        arrayList.add(UItem.asButton(ChatsItem.BOTTOM_BUTTON.getId(), LocaleController.getString(C2797R.string.BottomButton), this.bottomButton[ExteraConfig.getBottomButton()]).setSearchable(this).setLinkAlias("bottomButton", this));
        arrayList.add(UItem.asCheck(ChatsItem.ADMIN_SHORTCUTS.getId(), LocaleController.getString(C2797R.string.AdminShortcuts)).setChecked(ExteraConfig.getQuickAdminShortcuts()).setSearchable(this).setLinkAlias("adminShortcuts", this));
        arrayList.add(UItem.asExteraExpandableSwitch(ChatsItem.QUICK_TRANSITIONS.getId(), LocaleController.getString(C2797R.string.QuickTransitions), String.format(locale, "%d/%d", Integer.valueOf(getQuickTransitionsSelectedCount(false)), Integer.valueOf(getQuickTransitionsSelectedCount(true))), new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleQuickTransitionsSwitchClick(view);
            }
        }).setChecked(getQuickTransitionsSelectedCount(false) > 0).setCollapsed(!this.quickTransitionsExpanded).setSearchable(this).setLinkAlias("quickTransitions", this));
        if (this.quickTransitionsExpanded) {
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.QUICK_TRANSITION_FOR_CHANNELS.getId(), LocaleController.getString(C2797R.string.FilterChannels)).setChecked(ExteraConfig.getQuickTransitionForChannels()).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.QUICK_TRANSITION_FOR_TOPICS.getId(), LocaleController.getString(C2797R.string.Topics)).setChecked(ExteraConfig.getQuickTransitionForTopics()).pad());
        }
        arrayList.add(UItem.asCheck(ChatsItem.DISABLE_GREETING_STICKER.getId(), LocaleController.getString(C2797R.string.DisableGreetingSticker)).setChecked(ExteraConfig.getDisableGreetingSticker()).setSearchable(this).setLinkAlias("disableGreetingSticker", this));
        arrayList.add(UItem.asCheck(ChatsItem.HIDE_KEYBOARD_ON_SCROLL.getId(), LocaleController.getString(C2797R.string.HideKeyboardOnScroll)).setChecked(ExteraConfig.getHideKeyboardOnScroll()).setSearchable(this).setLinkAlias("hideKeyboardOnScroll", this));
        arrayList.add(UItem.asCheck(ChatsItem.ADD_COMMA_AFTER_MENTION.getId(), LocaleController.getString(C2797R.string.AddCommaAfterMention)).setChecked(ExteraConfig.getAddCommaAfterMention()).setSearchable(this).setLinkAlias("addCommaAfterMention", this));
        arrayList.add(UItem.asCheck(ChatsItem.HIDE_SEND_AS_PEER.getId(), LocaleController.getString(C2797R.string.HideSendAsPeer)).setChecked(ExteraConfig.getHideSendAsPeer()).setSearchable(this).setLinkAlias("hideSendAsPeer", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.HideSendAsPeerInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.MessagesChartTitle)));
        arrayList.add(UItem.asCustom(ChatsItem.MESSAGES_PREVIEW.getId(), this.messagesPreviewCell));
        arrayList.add(UItem.asCheck(ChatsItem.REMOVE_MESSAGE_TAIL.getId(), LocaleController.getString(C2797R.string.RemoveMessageTail)).setChecked(ExteraConfig.getRemoveMessageTail()).setSearchable(this).setLinkAlias("removeMessageTail", this));
        arrayList.add(UItem.asCheck(ChatsItem.REPLACE_EDITED_WITH_ICON.getId(), LocaleController.formatString(C2797R.string.ReplaceEditedWithIcon, LocaleController.getString(C2797R.string.EditedMessage))).setChecked(ExteraConfig.getReplaceEditedWithIcon()).setSearchable(this).setLinkAlias("replaceEditedWithIcon", this));
        arrayList.add(UItem.asCheck(ChatsItem.SHOW_ONLINE_STATUS.getId(), LocaleController.getString(C2797R.string.ShowOnlineStatus)).setChecked(ExteraConfig.getShowOnlineStatus()).setSearchable(this).setLinkAlias("showOnlineStatus", this));
        arrayList.add(UItem.asCheck(ChatsItem.HIDE_SHARE_BUTTON.getId(), LocaleController.formatString(C2797R.string.HideShareButton, LocaleController.getString(C2797R.string.ShareFile))).setChecked(ExteraConfig.getHideShareButton()).setSearchable(this).setLinkAlias("hideShareButton", this));
        arrayList.add(UItem.asCheck(ChatsItem.SHOW_RESULTS_BEFORE_VOTING.getId(), LocaleController.getString(C2797R.string.ShowPollResultsBeforeVoting), LocaleController.getString(C2797R.string.ShowPollResultsBeforeVotingHint), true).setChecked(ExteraConfig.getShowResultsBeforeVoting()).setSearchable(this).setLinkAlias("showResultsBeforeVoting", this));
        arrayList.add(UItem.asExteraExpandableSwitch(ChatsItem.MESSAGE_MENU.getId(), LocaleController.getString(C2797R.string.MessageMenu), String.format(locale, "%d/%d", Integer.valueOf(getMessageMenuSelectedCount(false)), Integer.valueOf(getMessageMenuSelectedCount(true))), new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleMessageMenuSwitchClick(view);
            }
        }).setChecked(getMessageMenuSelectedCount(false) > 0).setCollapsed(!this.messageMenuExpanded).setSearchable(this).setLinkAlias("messageMenu", this));
        if (this.messageMenuExpanded) {
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.COPY_PHOTO.getId(), LocaleController.getString(C2797R.string.CopyPhoto)).setChecked(ExteraConfig.getShowCopyPhotoButton()).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.SAVE.getId(), LocaleController.getString(C2797R.string.Save)).setChecked(ExteraConfig.getShowSaveMessageButton()).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.REPEAT.getId(), SettingsRegistry.markAsNewFeature("Chats-MessageMenu-Repeat") ? LocaleUtils.applyNewSpan(LocaleController.getString(C2797R.string.Repeat)) : LocaleController.getString(C2797R.string.Repeat)).setChecked(ExteraConfig.getShowRepeatMessageButton()).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.CLEAR.getId(), LocaleController.getString(C2797R.string.Clear)).setChecked(ExteraConfig.getShowClearButton()).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.HISTORY.getId(), LocaleController.getString(C2797R.string.MessageHistory)).setChecked(ExteraConfig.getShowHistoryButton()).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.REPORT.getId(), LocaleController.getString(C2797R.string.ReportChat)).setChecked(ExteraConfig.getShowReportButton()).pad());
            if (AiController.canUseAI()) {
                arrayList.add(UItem.asRoundCheckbox(ChatsItem.GENERATE.getId(), LocaleController.getString(C2797R.string.Generate)).setChecked(ExteraConfig.getShowGenerateButton()).pad());
            }
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.DETAILS.getId(), LocaleController.getString(C2797R.string.Details)).setChecked(ExteraConfig.getShowDetailsButton()).pad());
        }
        arrayList.add(UItem.asCheck(ChatsItem.GROUP_MESSAGE_MENU.getId(), LocaleController.getString(C2797R.string.GroupMessageMenu)).setChecked(ExteraConfig.getGroupMessageMenu()).setSearchable(this).setLinkAlias("groupMessageMenu", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.GroupMessageMenuInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.PremiumPreviewVoiceToText)));
        arrayList.add(UItem.asButton(ChatsItem.SPEECH_RECOGNITION_LANGUAGE.getId(), LocaleController.getString(C2797R.string.RecognitionLanguage), TranslatorUtils.getLanguageTitleSystem(ExteraConfig.getRecognitionLanguage())).setSearchable(this).setLinkAlias("recognitionLanguage", this));
        if (AiController.canUseAI()) {
            arrayList.add(UItem.asCheck(ChatsItem.POST_PROCESSING_WITH_AI.getId(), LocaleController.getString(C2797R.string.PostProcessingWithAi), LocaleController.getString(C2797R.string.PostProcessingWithAiInfo), true).setChecked(ExteraConfig.getPostprocessingWithAi()).setSearchable(this).setLinkAlias("postprocessingWithAi", this));
        }
        if (!getDownloadedRecognitionModels().isEmpty()) {
            arrayList.add(UItem.asButton(ChatsItem.DELETE_RECOGNITION_MODEL.getId(), C2797R.drawable.msg_delete, LocaleController.getString(C2797R.string.DeleteRecognitionModel)).red().setSearchable(this).setLinkAlias("deleteRecognitionModel", this));
        }
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.RecognitionInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.VoipCamera)));
        arrayList.add(UItem.asButton(ChatsItem.CAMERA_TYPE.getId(), LocaleController.getString(C2797R.string.CameraType), this.cameraType[ExteraConfig.getCameraType().ordinal()]).setSearchable(this).setLinkAlias("cameraType", this));
        if (ExteraConfig.getCameraType() != CameraType.CAMERA_1) {
            CameraType cameraType = ExteraConfig.getCameraType();
            CameraType cameraType2 = CameraType.CAMERA_X;
            boolean z = (cameraType == cameraType2 && CameraXSession.isSeamlessSwitchingAvailable(getContext())) || (ExteraConfig.getCameraType() == CameraType.CAMERA_2 && DualCameraView.dualAvailableStatic(getContext()));
            arrayList.add(UItem.asExteraExpandableSwitch(ChatsItem.CAMERA_SETTINGS.getId(), LocaleController.getString(C2797R.string.ExtendedSettings), String.format(locale, "%d/%d", Integer.valueOf(getCameraSettingsSelectedCount(false)), Integer.valueOf(getCameraSettingsSelectedCount(true))), new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.handleCameraSettingsSwitchClick(view);
                }
            }).setChecked(getCameraSettingsSelectedCount(false) > 0).setCollapsed(!this.cameraSettingsExpanded).setSearchable(this).setLinkAlias("cameraSettings", this));
            if (this.cameraSettingsExpanded) {
                if (z) {
                    arrayList.add(UItem.asRoundCheckbox(ChatsItem.DUAL_CAMERA.getId(), SettingsRegistry.markAsNewFeature("Camera-ExtendedSettings-SeamlessSwitching") ? LocaleUtils.applyNewSpan(LocaleController.getString(C2797R.string.SeamlessSwitching)) : LocaleController.getString(C2797R.string.SeamlessSwitching)).setChecked(DualCameraView.roundDualAvailableStatic(getContext())).pad());
                }
                arrayList.add(UItem.asRoundCheckbox(ChatsItem.EXTENDED_FRAMES_PER_SECOND.getId(), LocaleController.getString(C2797R.string.ExtendedFramesPerSecond)).setChecked(ExteraConfig.getExtendedFramesPerSecond()).pad());
                arrayList.add(UItem.asRoundCheckbox(ChatsItem.CAMERA_STABILIZATION.getId(), LocaleController.getString(C2797R.string.CameraStabilization)).setChecked(ExteraConfig.getCameraStabilization()).pad());
                if (ExteraConfig.getCameraType() != CameraType.CAMERA_2) {
                    arrayList.add(UItem.asRoundCheckbox(ChatsItem.CAMERA_MIRROR_MODE.getId(), LocaleController.getString(C2797R.string.CameraMirrorMode)).setChecked(ExteraConfig.getCameraMirrorMode()).pad());
                }
                if (ExteraConfig.getCameraType() == cameraType2) {
                    arrayList.add(UItem.asRoundCheckbox(ChatsItem.START_WITH_WIDE_ANGLE_CAMERA.getId(), LocaleController.getString(C2797R.string.StartWithWideAngleCamera)).setChecked(ExteraConfig.getStartWithWideAngleCamera()).pad());
                }
            }
            if (!z) {
                MessagesController.getGlobalMainSettings().edit().putBoolean("rounddual_available", false).apply();
            }
        }
        arrayList.add(UItem.asButton(ChatsItem.VIDEO_MESSAGES_CAMERA.getId(), LocaleController.getString(C2797R.string.VideoMessagesCamera), this.videoMessagesCamera[ExteraConfig.getVideoMessagesCamera().ordinal()]).setSearchable(this).setLinkAlias("videoMessagesCamera", this));
        if (ExteraConfig.getVideoMessagesCamera() != VideoMessagesCamera.ASK) {
            arrayList.add(UItem.asCheck(ChatsItem.REMEMBER_LAST_USED_CAMERA.getId(), LocaleController.getString(C2797R.string.RememberLastUsedCamera), LocaleController.getString(C2797R.string.RememberLastUsedCameraInfo), true).setChecked(ExteraConfig.getRememberLastUsedCamera()).setSearchable(this).setLinkAlias("rememberLastUsedCamera", this));
        }
        arrayList.add(UItem.asCheck(ChatsItem.STATIC_ZOOM.getId(), LocaleController.getString(C2797R.string.StaticZoom)).setChecked(ExteraConfig.getStaticZoom()).setSearchable(this).setLinkAlias("staticZoom", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.StaticZoomInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.AutoDownloadPhotos)));
        arrayList.add(UItem.asCheck(ChatsItem.ALWAYS_SEND_IN_HD.getId(), LocaleController.getString(C2797R.string.AlwaysSendInHD)).setChecked(ExteraConfig.getAlwaysSendInHD()).setSearchable(this).setLinkAlias("alwaysSendInHD", this));
        arrayList.add(UItem.asCheck(ChatsItem.HIDE_CAMERA_TILE.getId(), LocaleController.getString(C2797R.string.HideCameraTile)).setChecked(ExteraConfig.getHideCameraTile()).setSearchable(this).setLinkAlias("hideCameraTile", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.HideCameraTileInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.AutoDownloadVideos)));
        arrayList.add(UItem.asButton(ChatsItem.DOUBLE_TAP_SEEK_DURATION.getId(), LocaleController.getString(C2797R.string.DoubleTapSeekDuration), this.doubleTapSeekDuration[ExteraConfig.getDoubleTapSeekDuration()]).setSearchable(this).setLinkAlias("doubleTapSeekDuration", this));
        arrayList.add(UItem.asCheck(ChatsItem.PREFER_ORIGINAL_QUALITY.getId(), LocaleController.getString(C2797R.string.PreferOriginalQuality)).setChecked(ExteraConfig.getPreferOriginalQuality()).setSearchable(this).setLinkAlias("preferOriginalQuality", this));
        arrayList.add(UItem.asCheck(ChatsItem.SWIPE_TO_PIP.getId(), LocaleController.getString(C2797R.string.SwipeToPip)).setChecked(ExteraConfig.getSwipeToPip()).setSearchable(this).setLinkAlias("swipeToPip", this));
        arrayList.add(UItem.asCheck(ChatsItem.UNMUTE_WITH_VOLUME_BUTTONS.getId(), LocaleController.getString(C2797R.string.UnmuteWithVolumeButtons), LocaleController.getString(C2797R.string.UnmuteWithVolumeButtonsInfo), true).setChecked(ExteraConfig.getUnmuteWithVolumeButtons()).setSearchable(this).setLinkAlias("unmuteWithVolumeButtons", this));
        arrayList.add(UItem.asExteraExpandableSwitch(ChatsItem.PAUSE_ON_MINIMIZE.getId(), LocaleController.getString(C2797R.string.PauseOnMinimize), String.format(locale, "%d/%d", Integer.valueOf(getPauseOnMinimizeSelectedCount(false)), Integer.valueOf(getPauseOnMinimizeSelectedCount(true))), new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handlePauseOnMinimizeSwitchClick(view);
            }
        }).setChecked(getPauseOnMinimizeSelectedCount(false) > 0).setCollapsed(!this.pauseOnMinimizeExpanded).setSearchable(this).setLinkAlias("pauseOnMinimize", this));
        if (this.pauseOnMinimizeExpanded) {
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.PAUSE_ON_MINIMIZE_VIDEO.getId(), LocaleController.getString(C2797R.string.PauseOnMinimizeVideo)).setChecked(ExteraConfig.getPauseOnMinimizeVideo()).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.PAUSE_ON_MINIMIZE_VOICE.getId(), LocaleController.getString(C2797R.string.PauseOnMinimizeVoice)).setChecked(ExteraConfig.getPauseOnMinimizeVoice()).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.PAUSE_ON_MINIMIZE_ROUND.getId(), LocaleController.getString(C2797R.string.PauseOnMinimizeRound)).setChecked(ExteraConfig.getPauseOnMinimizeRound()).pad());
        }
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.PauseOnMinimizeInfo)));
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.f1708id;
        if (i2 <= 0 || i2 > ChatsItem.values().length) {
            return;
        }
        switch (C12112.f347xaca45cb3[ChatsItem.values()[uItem.f1708id - 1].ordinal()]) {
            case 1:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda10
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setHideStickerTime(((Boolean) obj).booleanValue());
                    }
                });
                this.stickerSizeCell.invalidate();
                break;
            case 2:
                handleReplyElementsClick(uItem);
                break;
            case 3:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda21
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setReplyColors(((Boolean) obj).booleanValue());
                    }
                });
                updateReplySettings();
                break;
            case 4:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda32
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setReplyEmoji(((Boolean) obj).booleanValue());
                    }
                });
                updateReplySettings();
                break;
            case 5:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda43
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setReplyBackground(((Boolean) obj).booleanValue());
                    }
                });
                updateReplySettings();
                break;
            case 6:
                presentFragment(new AiPreferencesActivity());
                break;
            case 7:
                presentFragment(new ThemeActivity(0));
                break;
            case 8:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda54
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setUnlimitedRecentStickers(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 9:
                handleHideReactionsElementsClick(uItem);
                break;
            case 10:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda56
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setHideReactionsInChannels(((Boolean) obj).booleanValue());
                    }
                });
                updateReplySettings();
                break;
            case 11:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda57
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setHideReactionsInGroups(((Boolean) obj).booleanValue());
                    }
                });
                updateReplySettings();
                break;
            case 12:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda58
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setHideReactionsInPrivateChats(((Boolean) obj).booleanValue());
                    }
                });
                updateReplySettings();
                break;
            case 13:
                showListDialog(uItem, this.doubleTapActions, DoubleTapUtils.getDoubleTapIcons(false), LocaleController.getString(C2797R.string.DoubleTapIncoming), ExteraConfig.getDoubleTapAction(), new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda59
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$4(i3);
                    }
                });
                break;
            case 14:
                showListDialog(uItem, this.doubleTapOutActions, DoubleTapUtils.getDoubleTapIcons(true), LocaleController.getString(C2797R.string.DoubleTapOutgoing), ExteraConfig.getDoubleTapActionOutOwner(), new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda60
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$5(i3);
                    }
                });
                break;
            case 15:
                showListDialog(uItem, this.bottomButton, LocaleController.getString(C2797R.string.BottomButton), ExteraConfig.getBottomButton(), new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda11
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$6(i3);
                    }
                });
                break;
            case 16:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda12
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setQuickAdminShortcuts(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 17:
                handleQuickTransitionsClick(uItem);
                break;
            case 18:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda13
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setQuickTransitionForChannels(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 19:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda14
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setQuickTransitionForTopics(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 20:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda15
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setDisableGreetingSticker(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 21:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda16
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setHideKeyboardOnScroll(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 22:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda17
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setAddCommaAfterMention(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 23:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda18
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setHideSendAsPeer(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 24:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda19
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setReplaceEditedWithIcon(((Boolean) obj).booleanValue());
                    }
                });
                this.messagesPreviewCell.refreshMessages();
                break;
            case 25:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda20
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setShowOnlineStatus(((Boolean) obj).booleanValue());
                    }
                });
                this.messagesPreviewCell.refreshMessages();
                break;
            case 26:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda22
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setRemoveMessageTail(((Boolean) obj).booleanValue());
                    }
                });
                Theme.chat_msgInDrawable = null;
                Theme.createChatResources(getParentActivity(), false);
                this.messagesPreviewCell.refreshMessages();
                break;
            case 27:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda23
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setShowResultsBeforeVoting(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 28:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda24
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setHideShareButton(((Boolean) obj).booleanValue());
                    }
                });
                this.messagesPreviewCell.refreshMessages();
                break;
            case 29:
                handleMessageMenuClick(uItem);
                break;
            case 30:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda25
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setShowCopyPhotoButton(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 31:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda26
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setShowSaveMessageButton(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 32:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda27
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setShowRepeatMessageButton(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 33:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda28
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setShowClearButton(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 34:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda29
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setShowHistoryButton(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 35:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda30
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setShowReportButton(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 36:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda31
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setShowGenerateButton(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 37:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda33
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setShowDetailsButton(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 38:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda34
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setGroupMessageMenu(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 39:
                handleSpeechRecognitionLanguageClick(uItem);
                break;
            case 40:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda35
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setPostprocessingWithAi(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 41:
                handleDeleteRecognitionModelClick();
                break;
            case 42:
                showListDialog(uItem, this.cameraType, LocaleController.getString(C2797R.string.CameraType), ExteraConfig.getCameraType().ordinal(), new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda36
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        ExteraConfig.setCameraType(CameraType.getEntries().get(i3));
                    }
                });
                break;
            case 43:
                handleCameraSettingsClick(uItem);
                break;
            case 44:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda37
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        MessagesController.getGlobalMainSettings().edit().putBoolean("rounddual_available", ((Boolean) obj).booleanValue()).apply();
                    }
                });
                break;
            case 45:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda38
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setExtendedFramesPerSecond(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 46:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda39
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setCameraStabilization(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 47:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda40
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setCameraMirrorMode(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 48:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda41
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setStartWithWideAngleCamera(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 49:
                showListDialog(uItem, this.videoMessagesCamera, LocaleController.getString(C2797R.string.VideoMessagesCamera), ExteraConfig.getVideoMessagesCamera().ordinal(), new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda42
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        ExteraConfig.setVideoMessagesCamera(VideoMessagesCamera.getEntries().get(i3));
                    }
                });
                break;
            case 50:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda44
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setRememberLastUsedCamera(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 51:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda45
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setStaticZoom(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 52:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda46
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setAlwaysSendInHD(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 53:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda47
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setHideCameraTile(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 54:
                showListDialog(uItem, this.doubleTapSeekDuration, LocaleController.getString(C2797R.string.DoubleTapSeekDuration), ExteraConfig.getDoubleTapSeekDuration(), new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda48
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        ExteraConfig.setDoubleTapSeekDuration(i3);
                    }
                });
                break;
            case 55:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda49
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setPreferOriginalQuality(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 56:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda50
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setSwipeToPip(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 57:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda51
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setUnmuteWithVolumeButtons(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 58:
                handlePauseOnMinimizeClick(uItem);
                break;
            case 59:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda52
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setPauseOnMinimizeVideo(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 60:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda53
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setPauseOnMinimizeVoice(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 61:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda55
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setPauseOnMinimizeRound(((Boolean) obj).booleanValue());
                    }
                });
                break;
        }
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$2 */
    /* JADX INFO: loaded from: classes4.dex */
    public static /* synthetic */ class C12112 {

        /* JADX INFO: renamed from: $SwitchMap$com$exteragram$messenger$preferences$chats$ChatsPreferencesActivity$ChatsItem */
        static final /* synthetic */ int[] f347xaca45cb3;

        static {
            int[] iArr = new int[ChatsItem.values().length];
            f347xaca45cb3 = iArr;
            try {
                iArr[ChatsItem.HIDE_STICKER_TIME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f347xaca45cb3[ChatsItem.REPLY_ELEMENTS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f347xaca45cb3[ChatsItem.REPLY_COLORS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f347xaca45cb3[ChatsItem.REPLY_EMOJI.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f347xaca45cb3[ChatsItem.REPLY_BACKGROUND.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f347xaca45cb3[ChatsItem.AI.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f347xaca45cb3[ChatsItem.CHAT_SETTINGS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f347xaca45cb3[ChatsItem.UNLIMITED_RECENT_STICKERS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f347xaca45cb3[ChatsItem.HIDE_REACTIONS.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f347xaca45cb3[ChatsItem.CHANNELS.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f347xaca45cb3[ChatsItem.GROUPS.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f347xaca45cb3[ChatsItem.PRIVATE_CHATS.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f347xaca45cb3[ChatsItem.DOUBLE_TAP_ACTION.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f347xaca45cb3[ChatsItem.DOUBLE_TAP_ACTION_OUT_OWNER.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f347xaca45cb3[ChatsItem.BOTTOM_BUTTON.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f347xaca45cb3[ChatsItem.ADMIN_SHORTCUTS.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f347xaca45cb3[ChatsItem.QUICK_TRANSITIONS.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f347xaca45cb3[ChatsItem.QUICK_TRANSITION_FOR_CHANNELS.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                f347xaca45cb3[ChatsItem.QUICK_TRANSITION_FOR_TOPICS.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f347xaca45cb3[ChatsItem.DISABLE_GREETING_STICKER.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                f347xaca45cb3[ChatsItem.HIDE_KEYBOARD_ON_SCROLL.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                f347xaca45cb3[ChatsItem.ADD_COMMA_AFTER_MENTION.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                f347xaca45cb3[ChatsItem.HIDE_SEND_AS_PEER.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                f347xaca45cb3[ChatsItem.REPLACE_EDITED_WITH_ICON.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                f347xaca45cb3[ChatsItem.SHOW_ONLINE_STATUS.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                f347xaca45cb3[ChatsItem.REMOVE_MESSAGE_TAIL.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                f347xaca45cb3[ChatsItem.SHOW_RESULTS_BEFORE_VOTING.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                f347xaca45cb3[ChatsItem.HIDE_SHARE_BUTTON.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                f347xaca45cb3[ChatsItem.MESSAGE_MENU.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                f347xaca45cb3[ChatsItem.COPY_PHOTO.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                f347xaca45cb3[ChatsItem.SAVE.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                f347xaca45cb3[ChatsItem.REPEAT.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                f347xaca45cb3[ChatsItem.CLEAR.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                f347xaca45cb3[ChatsItem.HISTORY.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                f347xaca45cb3[ChatsItem.REPORT.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                f347xaca45cb3[ChatsItem.GENERATE.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                f347xaca45cb3[ChatsItem.DETAILS.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                f347xaca45cb3[ChatsItem.GROUP_MESSAGE_MENU.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                f347xaca45cb3[ChatsItem.SPEECH_RECOGNITION_LANGUAGE.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                f347xaca45cb3[ChatsItem.POST_PROCESSING_WITH_AI.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                f347xaca45cb3[ChatsItem.DELETE_RECOGNITION_MODEL.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                f347xaca45cb3[ChatsItem.CAMERA_TYPE.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                f347xaca45cb3[ChatsItem.CAMERA_SETTINGS.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                f347xaca45cb3[ChatsItem.DUAL_CAMERA.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                f347xaca45cb3[ChatsItem.EXTENDED_FRAMES_PER_SECOND.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                f347xaca45cb3[ChatsItem.CAMERA_STABILIZATION.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                f347xaca45cb3[ChatsItem.CAMERA_MIRROR_MODE.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                f347xaca45cb3[ChatsItem.START_WITH_WIDE_ANGLE_CAMERA.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                f347xaca45cb3[ChatsItem.VIDEO_MESSAGES_CAMERA.ordinal()] = 49;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                f347xaca45cb3[ChatsItem.REMEMBER_LAST_USED_CAMERA.ordinal()] = 50;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                f347xaca45cb3[ChatsItem.STATIC_ZOOM.ordinal()] = 51;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                f347xaca45cb3[ChatsItem.ALWAYS_SEND_IN_HD.ordinal()] = 52;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                f347xaca45cb3[ChatsItem.HIDE_CAMERA_TILE.ordinal()] = 53;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                f347xaca45cb3[ChatsItem.DOUBLE_TAP_SEEK_DURATION.ordinal()] = 54;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                f347xaca45cb3[ChatsItem.PREFER_ORIGINAL_QUALITY.ordinal()] = 55;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                f347xaca45cb3[ChatsItem.SWIPE_TO_PIP.ordinal()] = 56;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                f347xaca45cb3[ChatsItem.UNMUTE_WITH_VOLUME_BUTTONS.ordinal()] = 57;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                f347xaca45cb3[ChatsItem.PAUSE_ON_MINIMIZE.ordinal()] = 58;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                f347xaca45cb3[ChatsItem.PAUSE_ON_MINIMIZE_VIDEO.ordinal()] = 59;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                f347xaca45cb3[ChatsItem.PAUSE_ON_MINIMIZE_VOICE.ordinal()] = 60;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                f347xaca45cb3[ChatsItem.PAUSE_ON_MINIMIZE_ROUND.ordinal()] = 61;
            } catch (NoSuchFieldError unused61) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$4(int i) {
        ExteraConfig.setDoubleTapAction(i);
        handleDoubleTapActionButtonClick(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$5(int i) {
        ExteraConfig.setDoubleTapActionOutOwner(i);
        handleDoubleTapActionButtonClick(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$6(int i) {
        ExteraConfig.setBottomButton(i);
        this.parentLayout.rebuildFragments(0);
    }

    private void handleReplyElementsClick(UItem uItem) {
        boolean z = !this.replyElementsExpanded;
        this.replyElementsExpanded = z;
        uItem.setCollapsed(z);
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReplyElementsSwitchClick(View view) {
        UItem uItemFindItemByItemId = this.listView.findItemByItemId(((TextCheckCell2) view).f1513id);
        boolean z = !uItemFindItemByItemId.checked;
        ExteraConfig.setReplyColors(z);
        ExteraConfig.setReplyEmoji(z);
        ExteraConfig.setReplyBackground(z);
        this.stickerSizeCell.invalidate();
        uItemFindItemByItemId.setChecked(z);
        this.parentLayout.rebuildFragments(0);
        this.listView.adapter.update(true);
    }

    private void handleHideReactionsElementsClick(UItem uItem) {
        boolean z = !this.hideReactionsExpanded;
        this.hideReactionsExpanded = z;
        uItem.setCollapsed(z);
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHideReactionsSwitchClick(View view) {
        UItem uItemFindItemByItemId = this.listView.findItemByItemId(((TextCheckCell2) view).f1513id);
        boolean z = !uItemFindItemByItemId.checked;
        ExteraConfig.setHideReactionsInChannels(z);
        ExteraConfig.setHideReactionsInGroups(z);
        ExteraConfig.setHideReactionsInPrivateChats(z);
        uItemFindItemByItemId.setChecked(z);
        this.listView.adapter.update(true);
    }

    private void updateReplySettings() {
        this.stickerSizeCell.invalidate();
        this.parentLayout.rebuildFragments(0);
    }

    private int getReplyElementsSelectedCount(boolean z) {
        int i = (z || ExteraConfig.getReplyColors()) ? 1 : 0;
        if (z || ExteraConfig.getReplyEmoji()) {
            i++;
        }
        return (z || ExteraConfig.getReplyBackground()) ? i + 1 : i;
    }

    private int getHideReactionsElementsSelectedCount(boolean z) {
        int i = (z || ExteraConfig.getHideReactionsInChannels()) ? 1 : 0;
        if (z || ExteraConfig.getHideReactionsInGroups()) {
            i++;
        }
        return (z || ExteraConfig.getHideReactionsInPrivateChats()) ? i + 1 : i;
    }

    private void handleDoubleTapActionButtonClick(boolean z) {
        this.doubleTapCell.updateIcons(z ? 2 : 1, true);
        this.doubleTapCell.invalidate();
    }

    private void handleMessageMenuClick(UItem uItem) {
        boolean z = !this.messageMenuExpanded;
        this.messageMenuExpanded = z;
        uItem.setCollapsed(z);
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMessageMenuSwitchClick(View view) {
        UItem uItemFindItemByItemId = this.listView.findItemByItemId(((TextCheckCell2) view).f1513id);
        boolean z = !uItemFindItemByItemId.checked;
        ExteraConfig.setShowCopyPhotoButton(z);
        ExteraConfig.setShowClearButton(z);
        ExteraConfig.setShowSaveMessageButton(z);
        ExteraConfig.setShowRepeatMessageButton(z);
        ExteraConfig.setShowReportButton(z);
        ExteraConfig.setShowHistoryButton(z);
        ExteraConfig.setShowGenerateButton(z);
        ExteraConfig.setShowDetailsButton(z);
        uItemFindItemByItemId.setChecked(z);
        this.parentLayout.rebuildFragments(0);
        this.listView.adapter.update(true);
    }

    private int getMessageMenuSelectedCount(boolean z) {
        int i = (z || ExteraConfig.getShowCopyPhotoButton()) ? 1 : 0;
        if (z || ExteraConfig.getShowClearButton()) {
            i++;
        }
        if (z || ExteraConfig.getShowSaveMessageButton()) {
            i++;
        }
        if (z || ExteraConfig.getShowRepeatMessageButton()) {
            i++;
        }
        if (z || ExteraConfig.getShowReportButton()) {
            i++;
        }
        if (z || ExteraConfig.getShowHistoryButton()) {
            i++;
        }
        if (AiController.canUseAI() && (z || ExteraConfig.getShowGenerateButton())) {
            i++;
        }
        return (z || ExteraConfig.getShowDetailsButton()) ? i + 1 : i;
    }

    private void handleQuickTransitionsClick(UItem uItem) {
        boolean z = !this.quickTransitionsExpanded;
        this.quickTransitionsExpanded = z;
        uItem.setCollapsed(z);
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleQuickTransitionsSwitchClick(View view) {
        UItem uItemFindItemByItemId = this.listView.findItemByItemId(((TextCheckCell2) view).f1513id);
        boolean z = !uItemFindItemByItemId.checked;
        ExteraConfig.setQuickTransitionForChannels(z);
        ExteraConfig.setQuickTransitionForTopics(z);
        uItemFindItemByItemId.setChecked(z);
        this.listView.adapter.update(true);
    }

    private int getQuickTransitionsSelectedCount(boolean z) {
        int i = (z || ExteraConfig.getQuickTransitionForChannels()) ? 1 : 0;
        return (z || ExteraConfig.getQuickTransitionForTopics()) ? i + 1 : i;
    }

    private void handleCameraSettingsClick(UItem uItem) {
        boolean z = !this.cameraSettingsExpanded;
        this.cameraSettingsExpanded = z;
        uItem.setCollapsed(z);
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCameraSettingsSwitchClick(View view) {
        UItem uItemFindItemByItemId = this.listView.findItemByItemId(((TextCheckCell2) view).f1513id);
        boolean z = !uItemFindItemByItemId.checked;
        CameraType cameraType = ExteraConfig.getCameraType();
        CameraType cameraType2 = CameraType.CAMERA_X;
        if ((cameraType == cameraType2 && CameraXSession.isSeamlessSwitchingAvailable(getContext())) || (ExteraConfig.getCameraType() == CameraType.CAMERA_2 && DualCameraView.dualAvailableStatic(getContext()))) {
            MessagesController.getGlobalMainSettings().edit().putBoolean("rounddual_available", z).apply();
        } else {
            MessagesController.getGlobalMainSettings().edit().putBoolean("rounddual_available", false).apply();
        }
        SharedPreferences.Editor editor = ExteraConfig.getEditor();
        ExteraConfig.setExtendedFramesPerSecond(z);
        ExteraConfig.setCameraStabilization(z);
        if (ExteraConfig.getCameraType() != CameraType.CAMERA_2) {
            ExteraConfig.setCameraMirrorMode(z);
        }
        if (ExteraConfig.getCameraType() == cameraType2) {
            ExteraConfig.setStartWithWideAngleCamera(z);
        }
        editor.apply();
        uItemFindItemByItemId.setChecked(z);
        this.listView.adapter.update(true);
    }

    private int getCameraSettingsSelectedCount(boolean z) {
        CameraType cameraType = ExteraConfig.getCameraType();
        CameraType cameraType2 = CameraType.CAMERA_X;
        int i = (((cameraType == cameraType2 && CameraXSession.isSeamlessSwitchingAvailable(getContext())) || (ExteraConfig.getCameraType() == CameraType.CAMERA_2 && DualCameraView.dualAvailableStatic(getContext()))) && (z || DualCameraView.roundDualAvailableStatic(getContext()))) ? 1 : 0;
        if (z || ExteraConfig.getExtendedFramesPerSecond()) {
            i++;
        }
        if (z || ExteraConfig.getCameraStabilization()) {
            i++;
        }
        if (ExteraConfig.getCameraType() != CameraType.CAMERA_2 && (z || ExteraConfig.getCameraMirrorMode())) {
            i++;
        }
        return ExteraConfig.getCameraType() == cameraType2 ? (z || ExteraConfig.getStartWithWideAngleCamera()) ? i + 1 : i : i;
    }

    private void handlePauseOnMinimizeClick(UItem uItem) {
        boolean z = !this.pauseOnMinimizeExpanded;
        this.pauseOnMinimizeExpanded = z;
        uItem.setCollapsed(z);
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePauseOnMinimizeSwitchClick(View view) {
        UItem uItemFindItemByItemId = this.listView.findItemByItemId(((TextCheckCell2) view).f1513id);
        boolean z = !uItemFindItemByItemId.checked;
        ExteraConfig.setPauseOnMinimizeVideo(z);
        ExteraConfig.setPauseOnMinimizeVoice(z);
        ExteraConfig.setPauseOnMinimizeRound(z);
        uItemFindItemByItemId.setChecked(z);
        this.listView.adapter.update(true);
    }

    private int getPauseOnMinimizeSelectedCount(boolean z) {
        int i = (z || ExteraConfig.getPauseOnMinimizeVideo()) ? 1 : 0;
        if (z || ExteraConfig.getPauseOnMinimizeVoice()) {
            i++;
        }
        return (z || ExteraConfig.getPauseOnMinimizeRound()) ? i + 1 : i;
    }

    private void handleSpeechRecognitionLanguageClick(final UItem uItem) {
        PopupUtils.showDialog(this.recognitionLanguageOptions, null, LocaleController.getString(C2797R.string.RecognitionLanguage), this.languageCodes.indexOf(ExteraConfig.getRecognitionLanguage()), getContext(), new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda62
            @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
            public final void onClick(int i) {
                this.f$0.lambda$handleSpeechRecognitionLanguageClick$13(uItem, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSpeechRecognitionLanguageClick$13(final UItem uItem, int i) {
        String recognitionLanguage = ExteraConfig.getRecognitionLanguage();
        final String str = this.languageCodes.get(i);
        if (Objects.equals(recognitionLanguage, str)) {
            return;
        }
        Runnable runnable = new Runnable() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda64
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleSpeechRecognitionLanguageClick$10(str, uItem);
            }
        };
        if (!Objects.equals(str, "none") && getDownloadedRecognitionModels().stream().noneMatch(new Predicate() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda65
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.equals(((VoiceRecognitionController.RecognitionModel) obj).getLanguage(), str);
            }
        })) {
            VoiceRecognitionController.RecognitionModel recognitionModelOrElse = VoiceRecognitionController.getInstance().listAvailableModels("vosk").stream().filter(new Predicate() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda66
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((VoiceRecognitionController.RecognitionModel) obj).getLanguage().equals(str);
                }
            }).findFirst().orElse(null);
            if (recognitionModelOrElse == null) {
                return;
            }
            RecognitionModelDialogs.showDownloadDialog(this, str, recognitionModelOrElse, runnable);
            return;
        }
        runnable.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSpeechRecognitionLanguageClick$10(String str, UItem uItem) {
        ExteraConfig.setRecognitionLanguage(str);
        View viewFindViewByItemId = this.listView.findViewByItemId(uItem.f1708id);
        if (viewFindViewByItemId instanceof TextCell) {
            ((TextCell) viewFindViewByItemId).setValue(TranslatorUtils.getLanguageTitleSystem(str), true);
        }
        this.listView.adapter.update(true);
    }

    private List<VoiceRecognitionController.RecognitionModel> getDownloadedRecognitionModels() {
        return VoiceRecognitionController.getInstance().listDownloadedModels("vosk");
    }

    private void handleDeleteRecognitionModelClick() {
        RecognitionModelDialogs.showDeleteFlow(this, getDownloadedRecognitionModels(), new Utilities.Callback() { // from class: com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity$$ExternalSyntheticLambda63
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$handleDeleteRecognitionModelClick$14((VoiceRecognitionController.RecognitionModel) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDeleteRecognitionModelClick$14(VoiceRecognitionController.RecognitionModel recognitionModel) {
        if (Objects.equals(ExteraConfig.getRecognitionLanguage(), recognitionModel.getLanguage())) {
            ExteraConfig.setRecognitionLanguage("none");
        }
        this.listView.adapter.update(true);
    }
}
