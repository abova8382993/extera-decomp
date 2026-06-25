package org.telegram.p035ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.LongSparseArray;
import android.util.Property;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.graphics.ColorUtils;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScrollerCustom;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p006dx.p009io.Opcodes;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.TabIconsMode;
import com.exteragram.messenger.components.TranslateBeforeSendWrapper;
import com.exteragram.messenger.config.BottomNavigationBar;
import com.exteragram.messenger.drawer.DrawerContainer;
import com.exteragram.messenger.utils.AppUtils;
import com.exteragram.messenger.utils.chats.MainMenuHelper;
import com.exteragram.messenger.utils.p020ui.MainTabsUiHelper;
import com.exteragram.messenger.utils.system.VibratorUtils;
import com.exteragram.messenger.utils.text.LocaleUtils;
import com.exteragram.messenger.utils.text.TranslatorUtils;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import me.vkryl.android.animator.BoolAnimator;
import me.vkryl.android.animator.FactorAnimator;
import okhttp3.internal.url._UrlKt;
import org.scilab.forge.jlatexmath.TeXSymbolParser;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AnimationNotificationsLocker;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BirthdayController;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.FilesMigrationService;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.NotificationsSettingsFacade;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.XiaomiUtilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.utils.GradientProtectionDrawable;
import org.telegram.messenger.utils.SearchTextWatcher;
import org.telegram.p035ui.ActionBar.ActionBar;
import org.telegram.p035ui.ActionBar.ActionBarMenu;
import org.telegram.p035ui.ActionBar.ActionBarMenuItem;
import org.telegram.p035ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.p035ui.ActionBar.ActionBarPopupWindow;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.BackDrawable;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.BottomSheet;
import org.telegram.p035ui.ActionBar.INavigationLayout;
import org.telegram.p035ui.ActionBar.MenuDrawable;
import org.telegram.p035ui.ActionBar.SimpleTextView;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Adapters.DialogsAdapter;
import org.telegram.p035ui.Adapters.DialogsSearchAdapter;
import org.telegram.p035ui.Adapters.FiltersView;
import org.telegram.p035ui.Cells.ActiveGiftAuctionsHintCell;
import org.telegram.p035ui.Cells.AnimatedStatusView;
import org.telegram.p035ui.Cells.DialogCell;
import org.telegram.p035ui.Cells.DialogsHintCell;
import org.telegram.p035ui.Cells.GraySectionCell;
import org.telegram.p035ui.Cells.HeaderCell;
import org.telegram.p035ui.Cells.ProfileSearchCell;
import org.telegram.p035ui.Cells.RequestPeerRequirementsCell;
import org.telegram.p035ui.Cells.TextCell;
import org.telegram.p035ui.Cells.UnconfirmedAuthHintCell;
import org.telegram.p035ui.Components.AlertsCreator;
import org.telegram.p035ui.Components.AnimatedEmojiDrawable;
import org.telegram.p035ui.Components.AnimationProperties;
import org.telegram.p035ui.Components.ArchiveHelp;
import org.telegram.p035ui.Components.AvatarDrawable;
import org.telegram.p035ui.Components.BackupImageView;
import org.telegram.p035ui.Components.BlurredRecyclerView;
import org.telegram.p035ui.Components.Bulletin;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.p035ui.Components.ChatActivityEnterView;
import org.telegram.p035ui.Components.ChatAvatarContainer;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.DialogsActivityStatusLayout;
import org.telegram.p035ui.Components.DialogsActivityTopBubblesFadeView;
import org.telegram.p035ui.Components.DialogsActivityTopPanelLayout;
import org.telegram.p035ui.Components.DialogsItemAnimator;
import org.telegram.p035ui.Components.FilterTabsView;
import org.telegram.p035ui.Components.FiltersListBottomSheet;
import org.telegram.p035ui.Components.FlickerLoadingView;
import org.telegram.p035ui.Components.FloatingDebug.FloatingDebugController;
import org.telegram.p035ui.Components.FloatingDebug.FloatingDebugProvider;
import org.telegram.p035ui.Components.FolderBottomSheet;
import org.telegram.p035ui.Components.FolderDrawable;
import org.telegram.p035ui.Components.ForegroundColorSpanThemable;
import org.telegram.p035ui.Components.Forum.ForumUtilities;
import org.telegram.p035ui.Components.FragmentContextView;
import org.telegram.p035ui.Components.FragmentFloatingButton;
import org.telegram.p035ui.Components.FragmentSearchField;
import org.telegram.p035ui.Components.ImageUpdater;
import org.telegram.p035ui.Components.ItemOptions;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.MediaActivity;
import org.telegram.p035ui.Components.NumberTextView;
import org.telegram.p035ui.Components.PacmanAnimation;
import org.telegram.p035ui.Components.PermissionRequest;
import org.telegram.p035ui.Components.PopupSwipeBackLayout;
import org.telegram.p035ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.p035ui.Components.Premium.PremiumFeatureBottomSheet;
import org.telegram.p035ui.Components.Premium.boosts.UserSelectorBottomSheet;
import org.telegram.p035ui.Components.ProxyDrawable;
import org.telegram.p035ui.Components.PullForegroundDrawable;
import org.telegram.p035ui.Components.RLottieDrawable;
import org.telegram.p035ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p035ui.Components.RecyclerAnimationScrollHelper;
import org.telegram.p035ui.Components.RecyclerItemsEnterAnimator;
import org.telegram.p035ui.Components.RecyclerListView;
import org.telegram.p035ui.Components.SearchViewPager;
import org.telegram.p035ui.Components.ShareTopView;
import org.telegram.p035ui.Components.SharedMediaLayout;
import org.telegram.p035ui.Components.SizeNotifierFrameLayout;
import org.telegram.p035ui.Components.UndoView;
import org.telegram.p035ui.Components.ViewPagerFixed;
import org.telegram.p035ui.Components.blur3.BlurredBackgroundDrawableViewFactory;
import org.telegram.p035ui.Components.blur3.DownscaleScrollableNoiseSuppressor;
import org.telegram.p035ui.Components.blur3.RenderNodeWithHash;
import org.telegram.p035ui.Components.blur3.capture.IBlur3Capture;
import org.telegram.p035ui.Components.blur3.capture.IBlur3Hash;
import org.telegram.p035ui.Components.blur3.drawable.color.impl.BlurredBackgroundProviderImpl;
import org.telegram.p035ui.Components.blur3.source.BlurredBackgroundSourceColor;
import org.telegram.p035ui.Components.blur3.source.BlurredBackgroundSourceRenderNode;
import org.telegram.p035ui.Components.blur3.utils.Blur3Utils;
import org.telegram.p035ui.Components.chat.ChatInputViewsContainer;
import org.telegram.p035ui.Components.chat.ViewPositionWatcher;
import org.telegram.p035ui.Components.inset.WindowInsetsStateHolder;
import org.telegram.p035ui.DialogsActivity;
import org.telegram.p035ui.FilterCreateActivity;
import org.telegram.p035ui.FilteredSearchView;
import org.telegram.p035ui.Gifts.GiftSheet;
import org.telegram.p035ui.GroupCreateFinalActivity;
import org.telegram.p035ui.MainTabsActivity;
import org.telegram.p035ui.PhotoViewer;
import org.telegram.p035ui.SelectAnimatedEmojiDialog;
import org.telegram.p035ui.Stars.StarGiftSheet;
import org.telegram.p035ui.Stars.StarsController;
import org.telegram.p035ui.Stories.DialogStoriesCell;
import org.telegram.p035ui.Stories.StealthModeAlert;
import org.telegram.p035ui.Stories.StoriesController;
import org.telegram.p035ui.Stories.StoriesListPlaceProvider;
import org.telegram.p035ui.Stories.UserListPoller;
import org.telegram.p035ui.Stories.recorder.HintView2;
import org.telegram.p035ui.Stories.recorder.StoryRecorder;
import org.telegram.p035ui.bots.BotWebViewSheet;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_account;
import org.telegram.tgnet.p034tl.TL_chatlists;
import org.telegram.tgnet.p034tl.TL_stars;
import org.telegram.tgnet.p034tl.TL_stories;

/* JADX INFO: loaded from: classes3.dex */
public class DialogsActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate, FloatingDebugProvider, FactorAnimator.Target, MainTabsActivity.TabFragmentDelegate {
    public static boolean switchingTheme;
    private final String ACTION_MODE_SEARCH_DIALOGS_TAG;
    private final int ADDITIONAL_LIST_HEIGHT_DP;
    private final int ANIMATOR_ID_ACTION_MODE_VISIBLE;
    private final int ANIMATOR_ID_DONE_BUTTON_VISIBLE;
    private final int ANIMATOR_ID_FILTER_TABS_VISIBLE;
    private final int ANIMATOR_ID_FORWARD_BUTTON_VISIBLE;
    private final int ANIMATOR_ID_SEARCH_BUTTON_VISIBLE;
    private final int ANIMATOR_ID_SEARCH_FILTER_TABS_VISIBLE;
    private final int ANIMATOR_ID_SEARCH_VISIBLE;
    private final int ANIMATOR_ID_SHADOW_VISIBLE;
    private final int ANIMATOR_ID_SPEED_BUTTON_VISIBLE;
    public final Property<DialogsActivity, Float> SCROLL_Y;
    public final Property<View, Float> SEARCH_TRANSLATION_Y;
    private ValueAnimator actionBarColorAnimator;
    private final Paint actionBarDefaultPaint;
    private String actionBarDefaultTitle;
    private int actionModeAdditionalHeight;
    private ImageView actionModeCloseView;
    private boolean actionModeFullyShowed;
    private final ArrayList<View> actionModeViews;
    private ActiveGiftAuctionsHintCell activeGiftAuctionsHintCell;
    private ActionBarMenuSubItem addToFolderItem;
    private String addToGroupAlertString;
    private int additionFloatingButtonOffset;
    private int additionNavigationBarHeight;
    private float additionalFloatingTranslation;
    private float additionalOffset;
    private boolean afterSignup;
    public boolean allowBots;
    public boolean allowChannels;
    private boolean allowGlobalSearch;
    public boolean allowGroups;
    public boolean allowLegacyGroups;
    public boolean allowMegagroups;
    private boolean allowMoving;
    private boolean allowSwipeDuringCurrentTouch;
    private boolean allowSwitchAccount;
    public boolean allowUsers;
    private boolean animateToHasStories;
    private AnimatedStatusView animatedStatusView;
    private boolean animatingForward;
    private final BoolAnimator animatorActionModeVisible;
    private final BoolAnimator animatorBottomTabsOffset;
    private final BoolAnimator animatorDoneButtonVisible;
    private final BoolAnimator animatorFilterTabsVisible;
    private final BoolAnimator animatorForwardButtonVisible;
    private final BoolAnimator animatorSearchButtonVisible;
    private final BoolAnimator animatorSearchFilterTabsVisible;
    private final BoolAnimator animatorSearchVisible;
    private final BoolAnimator animatorShadowVisible;
    private final BoolAnimator animatorSpeedButtonVisible;
    private ActionBarMenuItem archive2Item;
    private ActionBarMenuSubItem archiveItem;
    private boolean askAboutContacts;
    private boolean askingForPermissions;
    private UnconfirmedAuthHintCell authHintCell;
    private TLRPC.FileLocation avatar;
    private TLRPC.FileLocation avatarBig;
    private ChatAvatarContainer avatarContainer;
    private int avatarUploadingRequest;
    private boolean backAnimation;
    private BackDrawable backDrawable;
    private ActionBarMenuSubItem blockItem;
    private View blurredView;
    private ArrayList<TLRPC.Dialog> botShareDialogs;
    private Long cacheSize;
    private int canClearCacheCount;
    private boolean canDeletePsaSelected;
    private int canMuteCount;
    private int canPinCount;
    private int canReadCount;
    private int canReportSpamCount;
    private boolean canSelectTopics;
    private boolean canShowFilterTabsView;
    private boolean canShowHiddenArchive;
    private boolean canShowStoryHint;
    private int canUnarchiveCount;
    private int canUnmuteCount;
    private boolean cantSendToChannels;
    private FrameLayout chatInputBubbleContainer;
    private FrameLayout chatInputInAppContainer;
    private ChatInputViewsContainer chatInputViewsContainer;
    private boolean checkCanWrite;
    private boolean checkPermission;
    private boolean checkingImportDialog;
    private ActionBarMenuSubItem clearItem;
    private boolean closeFragment;
    private boolean closeSearchFieldOnHide;
    private ChatActivityEnterView commentView;
    private float contactsAlpha;
    private ValueAnimator contactsAlphaAnimator;
    private int currentConnectionState;
    View databaseMigrationHint;
    private int debugLastUpdateAction;
    private DialogsActivityDelegate delegate;
    private ActionBarMenuItem deleteItem;
    private Long deviceSize;
    public DialogStoriesCell dialogStoriesCell;
    public boolean dialogStoriesCellVisible;
    private DialogsActivityStatusLayout dialogsActivityStatusLayout;
    private DialogsHintCell dialogsHintCell;
    private boolean dialogsListFrozen;
    private boolean disableActionBarScrolling;
    private ActionBarMenuItem doneItem;
    private AnimatorSet doneItemAnimator;
    private DownloadProgressIcon downloadProgressIcon;
    private ActionBarMenuItem downloadsItem;
    private boolean downloadsItemVisible;
    private ItemOptions filterOptions;
    private FilterTabsView filterTabsView;
    private FiltersView filtersView;
    private boolean fixScrollYAfterArchiveOpened;
    private FragmentFloatingButton floatingButton3;
    boolean floatingButtonHidden;
    private float floatingButtonPanOffset;
    private FragmentFloatingButton floatingButtonStories;
    private boolean floatingForceVisible;
    private int folderId;
    private int forumCount;
    public long forwardOriginalChannel;
    private FragmentContextView fragmentContextView;
    private FrameLayout fragmentContextViewWrapper;
    private FragmentContextView fragmentLocationContextView;
    private FrameLayout fragmentLocationContextViewWrapper;
    private FragmentSearchField fragmentSearchField;
    private SearchTextWatcher fragmentSearchFieldWatcher;
    private ArrayList<TLRPC.Dialog> frozenDialogsList;
    private NotificationCenter.ObserversGroup globalObserversGroup;
    private boolean hasInvoice;
    public boolean hasMainTabs;
    public boolean hasOnlySlefStories;
    private int hasPoll;
    public boolean hasStories;
    private IBlur3Capture iBlur3Capture;
    private final BlurredBackgroundDrawableViewFactory iBlur3FactoryBlur;
    private final BlurredBackgroundDrawableViewFactory iBlur3FactoryFade;
    private final BlurredBackgroundDrawableViewFactory iBlur3FactoryFrostedLiquidGlass;
    private final BlurredBackgroundDrawableViewFactory iBlur3FactoryLiquidGlass;
    private final RectF iBlur3PositionActionBar;
    private final RectF iBlur3PositionMainTabs;
    private final ArrayList<RectF> iBlur3Positions;
    private final BlurredBackgroundSourceColor iBlur3SourceColor;
    private final BlurredBackgroundSourceRenderNode iBlur3SourceGlass;
    private final BlurredBackgroundSourceRenderNode iBlur3SourceGlassFrosted;
    private ImageUpdater imageUpdater;
    private int imeInsetHeight;
    private int initialDialogsType;
    private String initialSearchString;
    private int initialSearchType;
    private boolean invalidateScrollY;
    private boolean isNextButton;
    public boolean isQuote;
    public boolean isReplyTo;
    boolean isSlideBackTransition;
    private Drawable logoDrawable;
    private MainTabsActivityController mainTabsActivityController;
    boolean mainTabsHiddenByScroll;
    private int maximumVelocity;
    private boolean maybeStartTracking;
    private MenuDrawable menuDrawable;
    private int messagesCount;
    private final ArrayList<MessagesController.DialogFilter> movingDialogFilters;
    private DialogCell movingView;
    private boolean movingWas;
    private ActionBarMenuItem muteItem;
    private int navigationBarHeight;
    private AnimationNotificationsLocker notificationsLocker;
    public boolean notify;
    private NotificationCenter.ObserversGroup observersGroup;
    private boolean onlySelect;
    private MessagesStorage.TopicKey openedDialogId;
    private ActionBarMenuItem optionsItem;
    private int otherwiseReloginDays;
    private PacmanAnimation pacmanAnimation;
    private final Paint paint;
    float panTranslationY;
    private ActionBarMenuItem passcodeItem;
    private CharSequence pendingSharedCaption;
    private AlertDialog permissionDialog;
    private ActionBarMenuSubItem pin2Item;
    private ActionBarMenuItem pinItem;
    private Drawable premiumStar;
    private int prevPosition;
    private int prevTop;
    private float progressToActionMode;
    public float progressToDialogStoriesCell;
    public float progressToShowStories;
    private ProxyDrawable proxyDrawable;
    private ActionBarMenuSubItem proxyMenuSubItem;
    private ActionBarMenuSubItem readItem;
    private final RectF rect;
    private ActionBarMenuSubItem removeFromFolderItem;
    public long replyMessageAuthor;
    private long requestPeerBotId;
    private TLRPC.RequestPeerType requestPeerType;
    public boolean resetDelegate;
    private boolean rightFragmentTransitionInProgress;
    private boolean rightFragmentTransitionIsOpen;
    public RightSlidingDialogContainer rightSlidingDialogContainer;
    public int scheduleDate;
    public int scheduleRepeatPeriod;
    private float scrollAdditionalOffset;
    private boolean scrollBarVisible;
    private boolean scrollUpdated;
    private float scrollYOffset;
    private final DownscaleScrollableNoiseSuppressor scrollableViewNoiseSuppressor;
    private boolean scrollingManually;
    private float searchAnimationProgress;
    private AnimatorSet searchAnimator;
    private long searchDialogId;
    private boolean searchFiltersWasShowed;
    private boolean searchIsShowed;
    public ActionBarMenuItem searchItem;
    private TLObject searchObject;
    private String searchString;
    private SearchTabsAndFiltersLayout searchTabsAndFiltersLayout;
    private ViewPagerFixed.TabsView searchTabsView;
    private SearchViewPager searchViewPager;
    private int searchViewPagerIndex;
    float searchViewPagerTranslationY;
    private boolean searchWas;
    private boolean searchWasFullyShowed;
    private boolean searching;
    private String selectAlertString;
    private String selectAlertStringGroup;
    private SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialog;
    private ArrayList<Long> selectedDialogs;
    private NumberTextView selectedDialogsCountTextView;
    private boolean shareHintStarted;
    private Runnable shareLinkSearchRunnable;
    private ShareTopView shareTopView;
    private String sharedLink;
    private ArrayList<MediaController.PhotoEntry> sharedMediaEntries;
    private SharedMediaLayout.SharedMediaPreloader sharedMediaPreloader;
    private CharSequence sharedTextSeed;
    private int shiftDp;
    private boolean showSetPasswordConfirm;
    private String showingSuggestion;
    final int slideAmplitudeDp;
    ValueAnimator slideBackTransitionAnimator;
    boolean slideFragmentLite;
    float slideFragmentProgress;
    private DialogCell slidingView;
    private boolean slowedReloadAfterDialogClick;
    private ActionBarMenuItem speedItem;
    private long startArchivePullingTime;
    private boolean startedTracking;
    private int statusBarHeight;
    private AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable statusDrawable;
    private Long statusDrawableGiftId;
    private Bulletin storiesBulletin;
    public boolean storiesEnabled;
    private float storiesOverscroll;
    private boolean storiesOverscrollCalled;
    ValueAnimator storiesVisibilityAnimator;
    ValueAnimator storiesVisibilityAnimator2;
    private float storiesYOffset;
    private HintView2 storyHint;
    private boolean storyHintShown;
    private HintView2 storyPremiumHint;
    private ActionBarMenuItem switchItem;
    private AnimatorSet tabsAnimation;
    private boolean tabsAnimationInProgress;
    private float tabsYOffset;
    private final TextPaint textPaint;
    private DialogsActivityTopBubblesFadeView topBubblesFadeView;
    private Bulletin topBulletin;
    private DialogsActivityTopPanelLayout topPanelLayout;
    private UndoView[] undoView;
    private int undoViewIndex;
    private boolean updatePullAfterScroll;
    private Bulletin uploadingAvatarBulletin;
    private ViewPage[] viewPages;
    private ViewPositionWatcher viewPositionWatcher;
    private boolean waitingForScrollFinished;
    private boolean wasDrawn;
    private boolean wasSelectedDialogsEmpty;
    public boolean whiteActionBar;
    private final WindowInsetsStateHolder windowInsetsStateHolder;
    private ChatActivityEnterView.SendButton writeButton;
    public static boolean[] dialogsLoaded = new boolean[16];
    private static final Interpolator interpolator = new Interpolator() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda72
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return DialogsActivity.$r8$lambda$1MKEGXpjH9HrtwN1ZClzWIcweWE(f);
        }
    };
    public static float viewOffset = 0.0f;
    private static boolean isFirstLoading = false;

    public interface DialogsActivityDelegate {
        default boolean canSelectStories() {
            return false;
        }

        boolean didSelectDialogs(DialogsActivity dialogsActivity, ArrayList<MessagesStorage.TopicKey> arrayList, CharSequence charSequence, boolean z, boolean z2, int i, int i2, TopicsFragment topicsFragment);

        default boolean didSelectStories(DialogsActivity dialogsActivity) {
            return false;
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public boolean drawEdgeNavigationBar() {
        return false;
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public boolean isSupportEdgeToEdge() {
        return true;
    }

    public boolean shouldShowNextButton(DialogsActivity dialogsActivity, ArrayList<Long> arrayList, CharSequence charSequence, boolean z) {
        return false;
    }

    public MessagesStorage.TopicKey getOpenedDialogId() {
        return this.openedDialogId;
    }

    public class ViewPage extends FrameLayout {
        public boolean animateStoriesView;
        private DialogsAdapter animationSupportDialogsAdapter;
        private RecyclerListView animationSupportListView;
        private int archivePullViewState;
        private DialogsAdapter dialogsAdapter;
        private DialogsItemAnimator dialogsItemAnimator;
        private int dialogsType;
        private boolean isLocked;
        private ItemTouchHelper itemTouchhelper;
        private int lastItemsCount;
        private LinearLayoutManager layoutManager;
        public DialogsRecyclerView listView;
        public int pageAdditionalOffset;
        private FlickerLoadingView progressView;
        private PullForegroundDrawable pullForegroundDrawable;
        private RecyclerItemsEnterAnimator recyclerItemsEnterAnimator;
        Runnable saveScrollPositionRunnable;
        private RecyclerAnimationScrollHelper scrollHelper;
        public RecyclerListViewScroller scroller;
        private int selectedType;
        private SwipeController swipeController;
        Runnable updateListRunnable;
        boolean updating;

        public ViewPage(Context context) {
            super(context);
            this.saveScrollPositionRunnable = new Runnable() { // from class: org.telegram.ui.DialogsActivity$ViewPage$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$0();
                }
            };
            this.updateListRunnable = new Runnable() { // from class: org.telegram.ui.DialogsActivity$ViewPage$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$1();
                }
            };
        }

        public boolean isDefaultDialogType() {
            int i = this.dialogsType;
            return i == 0 || i == 7 || i == 8;
        }

        public /* synthetic */ void lambda$new$0() {
            DialogsRecyclerView dialogsRecyclerView = this.listView;
            if (dialogsRecyclerView == null || dialogsRecyclerView.getScrollState() != 0 || this.listView.getChildCount() <= 0 || this.listView.getLayoutManager() == null) {
                return;
            }
            int i = 1;
            boolean z = this.dialogsType == 0 && DialogsActivity.this.hasHiddenArchive() && this.archivePullViewState == 2;
            float f = DialogsActivity.this.scrollYOffset;
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.listView.getLayoutManager();
            View view = null;
            int top = Integer.MAX_VALUE;
            int i2 = -1;
            for (int i3 = 0; i3 < this.listView.getChildCount(); i3++) {
                DialogsRecyclerView dialogsRecyclerView2 = this.listView;
                int childAdapterPosition = dialogsRecyclerView2.getChildAdapterPosition(dialogsRecyclerView2.getChildAt(i3));
                View childAt = this.listView.getChildAt(i3);
                if (childAdapterPosition != -1 && childAt != null && childAt.getTop() < top) {
                    top = childAt.getTop();
                    i2 = childAdapterPosition;
                    view = childAt;
                }
            }
            if (view != null) {
                float top2 = view.getTop() - this.listView.getPaddingTop();
                if (DialogsActivity.this.hasStories) {
                    f = 0.0f;
                }
                if (this.listView.getScrollState() != 1) {
                    if (z && i2 == 0 && ((this.listView.getPaddingTop() - view.getTop()) - view.getMeasuredHeight()) + f < 0.0f) {
                        top2 = f;
                    } else {
                        i = i2;
                    }
                    linearLayoutManager.scrollToPositionWithOffset(i, (int) top2);
                }
            }
        }

        public /* synthetic */ void lambda$new$1() {
            this.dialogsAdapter.updateList(this.saveScrollPositionRunnable);
            DialogsActivity.this.invalidateScrollY = true;
            DialogsRecyclerView dialogsRecyclerView = this.listView;
            dialogsRecyclerView.updateDialogsOnNextDraw = true;
            this.updating = false;
            dialogsRecyclerView.invalidate();
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            if (getTranslationY() != f) {
                DialogsActivity.this.blur3_InvalidateBlur();
            }
            super.setTranslationY(f);
        }

        @Override // android.view.View
        public void setTranslationX(float f) {
            if (getTranslationX() != f) {
                super.setTranslationX(f);
                if (DialogsActivity.this.tabsAnimationInProgress && DialogsActivity.this.viewPages[0] == this) {
                    DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[1].selectedType, Math.abs(DialogsActivity.this.viewPages[0].getTranslationX()) / DialogsActivity.this.viewPages[0].getMeasuredWidth());
                }
                DialogsActivity.this.blur3_InvalidateBlur();
            }
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.listView.getLayoutParams();
            if (this.animateStoriesView) {
                layoutParams.bottomMargin = -AndroidUtilities.m1036dp(85.0f);
            } else {
                layoutParams.bottomMargin = 0;
            }
            super.onMeasure(i, i2);
        }

        public void updateList(boolean z) {
            if (((BaseFragment) DialogsActivity.this).isPaused) {
                return;
            }
            if (z) {
                AndroidUtilities.cancelRunOnUIThread(this.updateListRunnable);
                this.listView.setItemAnimator(this.dialogsItemAnimator);
                this.updateListRunnable.run();
            } else {
                if (this.updating) {
                    return;
                }
                this.updating = true;
                if (!this.dialogsItemAnimator.isRunning()) {
                    this.listView.setItemAnimator(null);
                }
                AndroidUtilities.runOnUIThread(this.updateListRunnable, 36L);
            }
        }
    }

    public static /* synthetic */ float $r8$lambda$1MKEGXpjH9HrtwN1ZClzWIcweWE(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * f2 * f2 * f2) + 1.0f;
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$1 */
    public class C55211 extends AnimationProperties.FloatProperty<DialogsActivity> {
        public C55211(String str) {
            super(str);
        }

        @Override // org.telegram.ui.Components.AnimationProperties.FloatProperty
        public void setValue(DialogsActivity dialogsActivity, float f) {
            dialogsActivity.setScrollY(f);
        }

        @Override // android.util.Property
        public Float get(DialogsActivity dialogsActivity) {
            return Float.valueOf(DialogsActivity.this.scrollYOffset);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$2 */
    public class C55322 extends AnimationProperties.FloatProperty<View> {
        public C55322(String str) {
            super(str);
        }

        @Override // org.telegram.ui.Components.AnimationProperties.FloatProperty
        public void setValue(View view, float f) {
            DialogsActivity dialogsActivity = DialogsActivity.this;
            dialogsActivity.searchViewPagerTranslationY = f;
            view.setTranslationY(dialogsActivity.panTranslationY + f);
            DialogsActivity.this.checkUi_searchFiltersVisibility();
        }

        @Override // android.util.Property
        public Float get(View view) {
            return Float.valueOf(DialogsActivity.this.searchViewPagerTranslationY);
        }
    }

    public class ContentView extends SizeNotifierFrameLayout {
        private Paint actionBarSearchPaint;
        private Rect blurBounds;
        private int[] pos;
        private int startedTrackingPointerId;
        private int startedTrackingX;
        private int startedTrackingY;
        private VelocityTracker velocityTracker;
        private boolean wasPortrait;

        @Override // android.view.View
        public boolean hasOverlappingRendering() {
            return false;
        }

        @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout
        public boolean invalidateOptimized() {
            return true;
        }

        public ContentView(Context context) {
            super(context);
            this.actionBarSearchPaint = new Paint(1);
            this.pos = new int[2];
            this.blurBounds = new Rect();
        }

        private boolean prepareForMoving(MotionEvent motionEvent, boolean z) {
            int nextPageId = DialogsActivity.this.filterTabsView.getNextPageId(z);
            if (nextPageId < 0) {
                return false;
            }
            getParent().requestDisallowInterceptTouchEvent(true);
            DialogsActivity.this.maybeStartTracking = false;
            DialogsActivity.this.startedTracking = true;
            this.startedTrackingX = (int) (motionEvent.getX() + DialogsActivity.this.additionalOffset);
            ((BaseFragment) DialogsActivity.this).actionBar.setEnabled(false);
            DialogsActivity.this.filterTabsView.setEnabled(false);
            DialogsActivity.this.viewPages[1].selectedType = nextPageId;
            DialogsActivity.this.viewPages[1].setVisibility(0);
            DialogsActivity.this.animatingForward = z;
            DialogsActivity.this.showScrollbars(false);
            DialogsActivity.this.switchToCurrentSelectedMode(true);
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (z) {
                dialogsActivity.viewPages[1].setTranslationX(DialogsActivity.this.viewPages[0].getMeasuredWidth());
            } else {
                dialogsActivity.viewPages[1].setTranslationX(-DialogsActivity.this.viewPages[0].getMeasuredWidth());
            }
            return true;
        }

        public void resetTabsSwipeTracking() {
            DialogsActivity.this.maybeStartTracking = false;
            DialogsActivity.this.additionalOffset = 0.0f;
            VelocityTracker velocityTracker = this.velocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.velocityTracker = null;
            }
            if (DialogsActivity.this.startedTracking) {
                DialogsActivity.this.startedTracking = false;
                ((BaseFragment) DialogsActivity.this).actionBar.setEnabled(true);
                if (DialogsActivity.this.filterTabsView != null) {
                    DialogsActivity.this.filterTabsView.setEnabled(true);
                    DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[0].selectedType, 1.0f);
                    DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[1].selectedType, 0.0f);
                }
                DialogsActivity.this.showScrollbars(true);
                DialogsActivity.this.viewPages[0].setTranslationX(0.0f);
                ViewPage viewPage = DialogsActivity.this.viewPages[1];
                boolean z = DialogsActivity.this.animatingForward;
                DialogsActivity dialogsActivity = DialogsActivity.this;
                viewPage.setTranslationX(z ? dialogsActivity.viewPages[0].getMeasuredWidth() : -dialogsActivity.viewPages[0].getMeasuredWidth());
                DialogsActivity.this.viewPages[1].setVisibility(8);
            }
        }

        @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout
        public void invalidateBlur() {
            super.invalidateBlur();
            DialogsActivity.this.blur3_InvalidateBlur();
        }

        /* JADX WARN: Removed duplicated region for block: B:47:0x00a5  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean checkTabsAnimationInProgress() {
            /*
                r7 = this;
                org.telegram.ui.DialogsActivity r0 = org.telegram.p035ui.DialogsActivity.this
                boolean r0 = org.telegram.p035ui.DialogsActivity.m15706$$Nest$fgettabsAnimationInProgress(r0)
                r1 = 0
                if (r0 == 0) goto Lc0
                org.telegram.ui.DialogsActivity r0 = org.telegram.p035ui.DialogsActivity.this
                boolean r0 = org.telegram.p035ui.DialogsActivity.m15634$$Nest$fgetbackAnimation(r0)
                org.telegram.ui.DialogsActivity r2 = org.telegram.p035ui.DialogsActivity.this
                r3 = -1
                r4 = 0
                r5 = 1065353216(0x3f800000, float:1.0)
                r6 = 1
                if (r0 == 0) goto L58
                org.telegram.ui.DialogsActivity$ViewPage[] r0 = org.telegram.p035ui.DialogsActivity.m15713$$Nest$fgetviewPages(r2)
                r0 = r0[r1]
                float r0 = r0.getTranslationX()
                float r0 = java.lang.Math.abs(r0)
                int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
                if (r0 >= 0) goto Lb9
                org.telegram.ui.DialogsActivity r0 = org.telegram.p035ui.DialogsActivity.this
                org.telegram.ui.DialogsActivity$ViewPage[] r0 = org.telegram.p035ui.DialogsActivity.m15713$$Nest$fgetviewPages(r0)
                r0 = r0[r1]
                r0.setTranslationX(r4)
                org.telegram.ui.DialogsActivity r0 = org.telegram.p035ui.DialogsActivity.this
                org.telegram.ui.DialogsActivity$ViewPage[] r0 = org.telegram.p035ui.DialogsActivity.m15713$$Nest$fgetviewPages(r0)
                r0 = r0[r6]
                org.telegram.ui.DialogsActivity r2 = org.telegram.p035ui.DialogsActivity.this
                org.telegram.ui.DialogsActivity$ViewPage[] r2 = org.telegram.p035ui.DialogsActivity.m15713$$Nest$fgetviewPages(r2)
                r2 = r2[r1]
                int r2 = r2.getMeasuredWidth()
                org.telegram.ui.DialogsActivity r4 = org.telegram.p035ui.DialogsActivity.this
                boolean r4 = org.telegram.p035ui.DialogsActivity.m15626$$Nest$fgetanimatingForward(r4)
                if (r4 == 0) goto L52
                r3 = r6
            L52:
                int r2 = r2 * r3
                float r2 = (float) r2
                r0.setTranslationX(r2)
                goto L98
            L58:
                org.telegram.ui.DialogsActivity$ViewPage[] r0 = org.telegram.p035ui.DialogsActivity.m15713$$Nest$fgetviewPages(r2)
                r0 = r0[r6]
                float r0 = r0.getTranslationX()
                float r0 = java.lang.Math.abs(r0)
                int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
                if (r0 >= 0) goto Lb9
                org.telegram.ui.DialogsActivity r0 = org.telegram.p035ui.DialogsActivity.this
                org.telegram.ui.DialogsActivity$ViewPage[] r0 = org.telegram.p035ui.DialogsActivity.m15713$$Nest$fgetviewPages(r0)
                r0 = r0[r1]
                org.telegram.ui.DialogsActivity r2 = org.telegram.p035ui.DialogsActivity.this
                org.telegram.ui.DialogsActivity$ViewPage[] r2 = org.telegram.p035ui.DialogsActivity.m15713$$Nest$fgetviewPages(r2)
                r2 = r2[r1]
                int r2 = r2.getMeasuredWidth()
                org.telegram.ui.DialogsActivity r5 = org.telegram.p035ui.DialogsActivity.this
                boolean r5 = org.telegram.p035ui.DialogsActivity.m15626$$Nest$fgetanimatingForward(r5)
                if (r5 == 0) goto L87
                goto L88
            L87:
                r3 = r6
            L88:
                int r2 = r2 * r3
                float r2 = (float) r2
                r0.setTranslationX(r2)
                org.telegram.ui.DialogsActivity r0 = org.telegram.p035ui.DialogsActivity.this
                org.telegram.ui.DialogsActivity$ViewPage[] r0 = org.telegram.p035ui.DialogsActivity.m15713$$Nest$fgetviewPages(r0)
                r0 = r0[r6]
                r0.setTranslationX(r4)
            L98:
                org.telegram.ui.DialogsActivity r0 = org.telegram.p035ui.DialogsActivity.this
                org.telegram.p035ui.DialogsActivity.m15815$$Nest$mshowScrollbars(r0, r6)
                org.telegram.ui.DialogsActivity r0 = org.telegram.p035ui.DialogsActivity.this
                android.animation.AnimatorSet r0 = org.telegram.p035ui.DialogsActivity.m15705$$Nest$fgettabsAnimation(r0)
                if (r0 == 0) goto Lb4
                org.telegram.ui.DialogsActivity r0 = org.telegram.p035ui.DialogsActivity.this
                android.animation.AnimatorSet r0 = org.telegram.p035ui.DialogsActivity.m15705$$Nest$fgettabsAnimation(r0)
                r0.cancel()
                org.telegram.ui.DialogsActivity r0 = org.telegram.p035ui.DialogsActivity.this
                r2 = 0
                org.telegram.p035ui.DialogsActivity.m15760$$Nest$fputtabsAnimation(r0, r2)
            Lb4:
                org.telegram.ui.DialogsActivity r0 = org.telegram.p035ui.DialogsActivity.this
                org.telegram.p035ui.DialogsActivity.m15761$$Nest$fputtabsAnimationInProgress(r0, r1)
            Lb9:
                org.telegram.ui.DialogsActivity r7 = org.telegram.p035ui.DialogsActivity.this
                boolean r7 = org.telegram.p035ui.DialogsActivity.m15706$$Nest$fgettabsAnimationInProgress(r7)
                return r7
            Lc0:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.DialogsActivity.ContentView.checkTabsAnimationInProgress():boolean");
        }

        public int getActionBarFullHeight() {
            float height = ((BaseFragment) DialogsActivity.this).actionBar.getHeight();
            RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
            float f = (rightSlidingDialogContainer == null || !rightSlidingDialogContainer.hasFragment()) ? 0.0f : DialogsActivity.this.rightSlidingDialogContainer.openedProgress;
            if (DialogsActivity.this.hasStories) {
                height += AndroidUtilities.m1036dp(81.0f) * (1.0f - DialogsActivity.this.searchAnimationProgress) * (1.0f - f) * (1.0f - DialogsActivity.this.progressToActionMode);
            }
            return (int) (height + DialogsActivity.this.storiesOverscroll + (DialogsActivity.this.getSearchFieldHeight() * (1.0f - DialogsActivity.this.progressToActionMode) * (1.0f - DialogsActivity.this.searchAnimationProgress) * (1.0f - f)));
        }

        public int getActionBarTop() {
            float f = DialogsActivity.this.scrollYOffset;
            RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
            return (int) ((-getY()) + (f * (1.0f - DialogsActivity.this.progressToActionMode) * (1.0f - ((rightSlidingDialogContainer == null || !rightSlidingDialogContainer.hasFragment()) ? 0.0f : DialogsActivity.this.rightSlidingDialogContainer.openedProgress)) * (1.0f - DialogsActivity.this.searchAnimationProgress)));
        }

        @Override // android.view.ViewGroup
        public boolean drawChild(Canvas canvas, View view, long j) {
            if (view == DialogsActivity.this.blurredView) {
                return true;
            }
            if (SizeNotifierFrameLayout.drawingBlur) {
                return super.drawChild(canvas, view, j);
            }
            if (view != DialogsActivity.this.viewPages[0] && ((DialogsActivity.this.viewPages.length <= 1 || view != DialogsActivity.this.viewPages[1]) && view != DialogsActivity.this.topPanelLayout && view != DialogsActivity.this.filterTabsView)) {
                if (view == ((BaseFragment) DialogsActivity.this).actionBar && DialogsActivity.this.slideFragmentProgress != 1.0f) {
                    canvas.save();
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    if (dialogsActivity.slideFragmentLite) {
                        canvas.translate(dialogsActivity.getSlideAmplitude() * (-1) * (1.0f - DialogsActivity.this.slideFragmentProgress), 0.0f);
                    } else {
                        float f = 1.0f - ((1.0f - dialogsActivity.slideFragmentProgress) * 0.05f);
                        canvas.translate((-AndroidUtilities.m1036dp(4.0f)) * (1.0f - DialogsActivity.this.slideFragmentProgress), 0.0f);
                        canvas.scale(f, f, 0.0f, (((BaseFragment) DialogsActivity.this).actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0) + (ActionBar.getCurrentActionBarHeight() / 2.0f));
                    }
                    boolean zDrawChild = super.drawChild(canvas, view, j);
                    canvas.restore();
                    return zDrawChild;
                }
                return super.drawChild(canvas, view, j);
            }
            canvas.save();
            if (view != DialogsActivity.this.topPanelLayout && view != DialogsActivity.this.filterTabsView) {
                canvas.clipRect(0.0f, (-getY()) + getActionBarTop() + getActionBarFullHeight(), getMeasuredWidth(), getMeasuredHeight());
            }
            DialogsActivity dialogsActivity2 = DialogsActivity.this;
            float f2 = dialogsActivity2.slideFragmentProgress;
            if (f2 != 1.0f) {
                if (dialogsActivity2.slideFragmentLite) {
                    canvas.translate(dialogsActivity2.getSlideAmplitude() * (-1) * (1.0f - DialogsActivity.this.slideFragmentProgress), 0.0f);
                } else {
                    float f3 = 1.0f - ((1.0f - f2) * 0.05f);
                    canvas.translate((-AndroidUtilities.m1036dp(4.0f)) * (1.0f - DialogsActivity.this.slideFragmentProgress), 0.0f);
                    canvas.scale(f3, f3, 0.0f, (-getY()) + DialogsActivity.this.scrollYOffset + getActionBarFullHeight());
                }
            }
            boolean zDrawChild2 = super.drawChild(canvas, view, j);
            canvas.restore();
            return zDrawChild2;
        }

        @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout
        public void drawBlurRect(Canvas canvas, float f, Rect rect, Paint paint, boolean z) {
            if (Build.VERSION.SDK_INT >= 29 && SharedConfig.chatBlurEnabled() && DialogsActivity.this.iBlur3SourceGlassFrosted != null && BlurredBackgroundProviderImpl.checkBlurEnabled(((BaseFragment) DialogsActivity.this).currentAccount, ((BaseFragment) DialogsActivity.this).resourceProvider)) {
                int i = (((BaseFragment) DialogsActivity.this).resourceProvider == null ? Theme.isCurrentThemeDark() : ((BaseFragment) DialogsActivity.this).resourceProvider.isDark()) ? 178 : Opcodes.ADD_INT_LIT8;
                canvas.save();
                canvas.translate(0.0f, -f);
                DialogsActivity.this.iBlur3SourceGlassFrosted.draw(canvas, rect.left, rect.top + f, rect.right, rect.bottom + f);
                canvas.restore();
                int alpha = paint.getAlpha();
                paint.setAlpha(i);
                canvas.drawRect(rect, paint);
                paint.setAlpha(alpha);
                return;
            }
            canvas.drawRect(rect, paint);
        }

        /* JADX WARN: Removed duplicated region for block: B:199:0x0070  */
        /* JADX WARN: Removed duplicated region for block: B:280:0x02bd  */
        /* JADX WARN: Removed duplicated region for block: B:281:0x02bf  */
        /* JADX WARN: Removed duplicated region for block: B:284:0x02d9  */
        /* JADX WARN: Removed duplicated region for block: B:285:0x02db  */
        /* JADX WARN: Removed duplicated region for block: B:325:0x0408  */
        /* JADX WARN: Removed duplicated region for block: B:327:0x0410  */
        /* JADX WARN: Removed duplicated region for block: B:339:0x0485  */
        /* JADX WARN: Removed duplicated region for block: B:342:0x04fa  */
        /* JADX WARN: Removed duplicated region for block: B:345:0x0507  */
        @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void dispatchDraw(android.graphics.Canvas r17) {
            /*
                Method dump skipped, instruction units count: 1308
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.DialogsActivity.ContentView.dispatchDraw(android.graphics.Canvas):void");
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            boolean z = size2 > size;
            setMeasuredDimension(size, size2);
            if (DialogsActivity.this.doneItem != null) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) DialogsActivity.this.doneItem.getLayoutParams();
                layoutParams.topMargin = ((BaseFragment) DialogsActivity.this).actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0;
                layoutParams.height = ActionBar.getCurrentActionBarHeight();
            }
            measureChildWithMargins(((BaseFragment) DialogsActivity.this).actionBar, i, 0, i2, 0);
            int iMeasureKeyboardHeight = measureKeyboardHeight();
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt != null && childAt.getVisibility() != 8 && childAt != ((BaseFragment) DialogsActivity.this).actionBar) {
                    if (childAt instanceof DatabaseMigrationHint) {
                        childAt.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(Math.max(AndroidUtilities.m1036dp(10.0f), (View.MeasureSpec.getSize(i2) + AndroidUtilities.m1036dp(2.0f)) - ((BaseFragment) DialogsActivity.this).actionBar.getMeasuredHeight()), TLObject.FLAG_30));
                    } else if (childAt instanceof ViewPage) {
                        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30);
                        int iM1036dp = AndroidUtilities.m1036dp(2.0f) + size2;
                        if (DialogsActivity.this.rightSlidingDialogContainer.hasFragment()) {
                            if (DialogsActivity.this.canShowFilterTabsView) {
                                iM1036dp += AndroidUtilities.m1036dp(50.0f);
                            }
                            if (DialogsActivity.this.hasStories) {
                                iM1036dp += AndroidUtilities.m1036dp(81.0f);
                            }
                            iM1036dp += AndroidUtilities.m1036dp(48.0f);
                        }
                        int i4 = iM1036dp + DialogsActivity.this.actionModeAdditionalHeight;
                        if (DialogsActivity.this.actionBarColorAnimator == null) {
                            childAt.setTranslationY(0.0f);
                        }
                        int i5 = DialogsActivity.this.isSlideBackTransition ? (int) (i4 * 0.05f) : 0;
                        childAt.setPadding(childAt.getPaddingLeft(), childAt.getPaddingTop(), childAt.getPaddingRight(), i5);
                        childAt.measure(iMakeMeasureSpec, View.MeasureSpec.makeMeasureSpec(Math.max(AndroidUtilities.m1036dp(10.0f), i4 + i5), TLObject.FLAG_30));
                        childAt.setPivotX(childAt.getMeasuredWidth() / 2.0f);
                    } else {
                        SearchViewPager searchViewPager = DialogsActivity.this.searchViewPager;
                        DialogsActivity dialogsActivity = DialogsActivity.this;
                        if (childAt == searchViewPager) {
                            dialogsActivity.searchViewPager.setTranslationY(DialogsActivity.this.searchViewPagerTranslationY);
                            DialogsActivity.this.searchViewPager.postsSearchContainer.setKeyboardHeight(iMeasureKeyboardHeight);
                            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30);
                            int iMakeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2) + AndroidUtilities.m1036dp(DialogsActivity.this.ADDITIONAL_LIST_HEIGHT_DP), TLObject.FLAG_30);
                            DialogsActivity.this.checkUi_searchPagesPaddings(true);
                            childAt.measure(iMakeMeasureSpec2, iMakeMeasureSpec3);
                            childAt.setPivotX(childAt.getMeasuredWidth() / 2.0f);
                            AndroidUtilities.rectTmp2.set(0, (((BaseFragment) DialogsActivity.this).actionBar.getMeasuredHeight() + AndroidUtilities.m1036dp(DialogsActivity.this.ADDITIONAL_LIST_HEIGHT_DP)) - AndroidUtilities.m1036dp(2.0f), childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
                        } else if (dialogsActivity.commentView != null && DialogsActivity.this.commentView.isPopupView(childAt)) {
                            if (AndroidUtilities.isInMultiwindow) {
                                if (AndroidUtilities.isTablet()) {
                                    childAt.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.m1036dp(320.0f), (size2 - AndroidUtilities.statusBarHeight) + getPaddingTop()), TLObject.FLAG_30));
                                } else {
                                    childAt.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec((size2 - AndroidUtilities.statusBarHeight) + getPaddingTop(), TLObject.FLAG_30));
                                }
                            } else {
                                childAt.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, TLObject.FLAG_30));
                            }
                        } else if (childAt == DialogsActivity.this.rightSlidingDialogContainer) {
                            int size3 = View.MeasureSpec.getSize(i2);
                            DialogsActivity dialogsActivity2 = DialogsActivity.this;
                            int i6 = dialogsActivity2.isSlideBackTransition ? (int) (size3 * 0.05f) : 0;
                            dialogsActivity2.rightSlidingDialogContainer.setTransitionPaddingBottom(i6);
                            childAt.measure(i, View.MeasureSpec.makeMeasureSpec(Math.max(AndroidUtilities.m1036dp(10.0f), size3 + i6), TLObject.FLAG_30));
                        } else {
                            measureChildWithMargins(childAt, i, 0, i2, 0);
                        }
                    }
                }
            }
            if (z != this.wasPortrait) {
                post(new Runnable() { // from class: org.telegram.ui.DialogsActivity$ContentView$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onMeasure$0();
                    }
                });
                this.wasPortrait = z;
            }
        }

        public /* synthetic */ void lambda$onMeasure$0() {
            if (DialogsActivity.this.selectAnimatedEmojiDialog != null) {
                DialogsActivity.this.selectAnimatedEmojiDialog.dismiss();
                DialogsActivity.this.selectAnimatedEmojiDialog = null;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:115:0x0056  */
        /* JADX WARN: Removed duplicated region for block: B:123:0x0071  */
        /* JADX WARN: Removed duplicated region for block: B:126:0x0084  */
        /* JADX WARN: Removed duplicated region for block: B:154:0x00f9  */
        /* JADX WARN: Removed duplicated region for block: B:156:0x010b  */
        /* JADX WARN: Removed duplicated region for block: B:173:0x0174  */
        @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onLayout(boolean r15, int r16, int r17, int r18, int r19) {
            /*
                Method dump skipped, instruction units count: 420
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.DialogsActivity.ContentView.onLayout(boolean, int, int, int, int):void");
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            if ((actionMasked == 1 || actionMasked == 3) && ((BaseFragment) DialogsActivity.this).actionBar.isActionModeShowed()) {
                DialogsActivity.this.allowMoving = true;
            }
            return checkTabsAnimationInProgress() || (DialogsActivity.this.filterTabsView != null && DialogsActivity.this.filterTabsView.isAnimatingIndicator()) || onTouchEvent(motionEvent);
        }

        @Override // android.view.ViewGroup, android.view.ViewParent
        public void requestDisallowInterceptTouchEvent(boolean z) {
            if (DialogsActivity.this.maybeStartTracking && !DialogsActivity.this.startedTracking) {
                onTouchEvent(null);
            }
            super.requestDisallowInterceptTouchEvent(z);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            float xVelocity;
            float yVelocity;
            float measuredWidth;
            int measuredWidth2;
            if (((BaseFragment) DialogsActivity.this).parentLayout == null || DialogsActivity.this.filterTabsView == null || DialogsActivity.this.filterTabsView.isEditing() || DialogsActivity.this.searching || DialogsActivity.this.rightSlidingDialogContainer.hasFragment() || ((BaseFragment) DialogsActivity.this).parentLayout.checkTransitionAnimation() || ((BaseFragment) DialogsActivity.this).parentLayout.isInPreviewMode() || ((BaseFragment) DialogsActivity.this).parentLayout.isPreviewOpenAnimationInProgress() || ((DialogsActivity.this.drawerContainer() != null && DialogsActivity.this.drawerContainer().isDrawerOpen()) || !((motionEvent == null || DialogsActivity.this.startedTracking || (motionEvent.getY() > getActionBarTop() + getActionBarFullHeight() && (DialogsActivity.this.chatInputViewsContainer == null || DialogsActivity.this.chatInputViewsContainer.getVisibility() != 0 || motionEvent.getY() < DialogsActivity.this.chatInputViewsContainer.getY()))) && (DialogsActivity.this.initialDialogsType == 3 || SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 5 || (SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 2 && DialogsActivity.this.viewPages[0] != null && (DialogsActivity.this.viewPages[0].dialogsAdapter.getDialogsType() == 7 || DialogsActivity.this.viewPages[0].dialogsAdapter.getDialogsType() == 8)))))) {
                return false;
            }
            if (motionEvent != null) {
                if (this.velocityTracker == null) {
                    this.velocityTracker = VelocityTracker.obtain();
                }
                this.velocityTracker.addMovement(motionEvent);
            }
            if (motionEvent != null && motionEvent.getAction() == 0 && checkTabsAnimationInProgress()) {
                DialogsActivity.this.startedTracking = true;
                this.startedTrackingPointerId = motionEvent.getPointerId(0);
                this.startedTrackingX = (int) motionEvent.getX();
                boolean z = DialogsActivity.this.animatingForward;
                int i = this.startedTrackingX;
                if (z) {
                    float f = i;
                    float measuredWidth3 = DialogsActivity.this.viewPages[0].getMeasuredWidth() + DialogsActivity.this.viewPages[0].getTranslationX();
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    if (f < measuredWidth3) {
                        dialogsActivity.additionalOffset = dialogsActivity.viewPages[0].getTranslationX();
                    } else {
                        ViewPage viewPage = dialogsActivity.viewPages[0];
                        DialogsActivity.this.viewPages[0] = DialogsActivity.this.viewPages[1];
                        DialogsActivity.this.viewPages[1] = viewPage;
                        DialogsActivity.this.animatingForward = false;
                        DialogsActivity dialogsActivity2 = DialogsActivity.this;
                        dialogsActivity2.additionalOffset = dialogsActivity2.viewPages[0].getTranslationX();
                        DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[0].selectedType, 1.0f);
                        DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[1].selectedType, DialogsActivity.this.additionalOffset / DialogsActivity.this.viewPages[0].getMeasuredWidth());
                        DialogsActivity.this.switchToCurrentSelectedMode(true);
                        DialogsActivity.this.viewPages[0].dialogsAdapter.resume();
                        DialogsActivity.this.viewPages[1].dialogsAdapter.pause();
                    }
                } else {
                    float f2 = i;
                    float measuredWidth4 = DialogsActivity.this.viewPages[1].getMeasuredWidth() + DialogsActivity.this.viewPages[1].getTranslationX();
                    DialogsActivity dialogsActivity3 = DialogsActivity.this;
                    if (f2 < measuredWidth4) {
                        ViewPage viewPage2 = dialogsActivity3.viewPages[0];
                        DialogsActivity.this.viewPages[0] = DialogsActivity.this.viewPages[1];
                        DialogsActivity.this.viewPages[1] = viewPage2;
                        DialogsActivity.this.animatingForward = true;
                        DialogsActivity dialogsActivity4 = DialogsActivity.this;
                        dialogsActivity4.additionalOffset = dialogsActivity4.viewPages[0].getTranslationX();
                        DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[0].selectedType, 1.0f);
                        DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[1].selectedType, (-DialogsActivity.this.additionalOffset) / DialogsActivity.this.viewPages[0].getMeasuredWidth());
                        DialogsActivity.this.switchToCurrentSelectedMode(true);
                        DialogsActivity.this.viewPages[0].dialogsAdapter.resume();
                        DialogsActivity.this.viewPages[1].dialogsAdapter.pause();
                    } else {
                        dialogsActivity3.additionalOffset = dialogsActivity3.viewPages[0].getTranslationX();
                    }
                }
                DialogsActivity.this.tabsAnimation.removeAllListeners();
                DialogsActivity.this.tabsAnimation.cancel();
                DialogsActivity.this.tabsAnimationInProgress = false;
            } else if (motionEvent != null && motionEvent.getAction() == 0) {
                DialogsActivity.this.additionalOffset = 0.0f;
            }
            if (motionEvent != null && motionEvent.getAction() == 0 && !DialogsActivity.this.startedTracking && !DialogsActivity.this.maybeStartTracking && DialogsActivity.this.filterTabsView.getVisibility() == 0) {
                this.startedTrackingPointerId = motionEvent.getPointerId(0);
                DialogsActivity.this.maybeStartTracking = true;
                this.startedTrackingX = (int) motionEvent.getX();
                this.startedTrackingY = (int) motionEvent.getY();
                this.velocityTracker.clear();
            } else if (motionEvent != null && motionEvent.getAction() == 2 && motionEvent.getPointerId(0) == this.startedTrackingPointerId) {
                int x = (int) ((motionEvent.getX() - this.startedTrackingX) + DialogsActivity.this.additionalOffset);
                int iAbs = Math.abs(((int) motionEvent.getY()) - this.startedTrackingY);
                if (DialogsActivity.this.startedTracking && ((DialogsActivity.this.animatingForward && x > 0) || (!DialogsActivity.this.animatingForward && x < 0))) {
                    if (!prepareForMoving(motionEvent, x < 0)) {
                        DialogsActivity.this.maybeStartTracking = true;
                        DialogsActivity.this.startedTracking = false;
                        DialogsActivity.this.viewPages[0].setTranslationX(0.0f);
                        ViewPage viewPage3 = DialogsActivity.this.viewPages[1];
                        boolean z2 = DialogsActivity.this.animatingForward;
                        DialogsActivity dialogsActivity5 = DialogsActivity.this;
                        viewPage3.setTranslationX(z2 ? dialogsActivity5.viewPages[0].getMeasuredWidth() : -dialogsActivity5.viewPages[0].getMeasuredWidth());
                        DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[1].selectedType, 0.0f);
                    }
                }
                if (DialogsActivity.this.maybeStartTracking && !DialogsActivity.this.startedTracking) {
                    float pixelsInCM = AndroidUtilities.getPixelsInCM(0.3f, true);
                    int x2 = (int) (motionEvent.getX() - this.startedTrackingX);
                    if (Math.abs(x2) >= pixelsInCM && Math.abs(x2) > iAbs) {
                        prepareForMoving(motionEvent, x < 0);
                    }
                } else if (DialogsActivity.this.startedTracking) {
                    DialogsActivity.this.viewPages[0].setTranslationX(x);
                    boolean z3 = DialogsActivity.this.animatingForward;
                    DialogsActivity dialogsActivity6 = DialogsActivity.this;
                    if (z3) {
                        dialogsActivity6.viewPages[1].setTranslationX(DialogsActivity.this.viewPages[0].getMeasuredWidth() + x);
                    } else {
                        dialogsActivity6.viewPages[1].setTranslationX(x - DialogsActivity.this.viewPages[0].getMeasuredWidth());
                    }
                    float fAbs = Math.abs(x) / DialogsActivity.this.viewPages[0].getMeasuredWidth();
                    if (DialogsActivity.this.viewPages[1].isLocked && fAbs > 0.3f) {
                        dispatchTouchEvent(MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0));
                        DialogsActivity.this.filterTabsView.shakeLock(DialogsActivity.this.viewPages[1].selectedType);
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$ContentView$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$onTouchEvent$1();
                            }
                        }, 200L);
                        return false;
                    }
                    DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[1].selectedType, fAbs);
                }
            } else if (motionEvent == null || (motionEvent.getPointerId(0) == this.startedTrackingPointerId && (motionEvent.getAction() == 3 || motionEvent.getAction() == 1 || motionEvent.getAction() == 6))) {
                this.velocityTracker.computeCurrentVelocity(MediaDataController.MAX_STYLE_RUNS_COUNT, DialogsActivity.this.maximumVelocity);
                if (motionEvent == null || motionEvent.getAction() == 3) {
                    xVelocity = 0.0f;
                    yVelocity = 0.0f;
                } else {
                    xVelocity = this.velocityTracker.getXVelocity();
                    yVelocity = this.velocityTracker.getYVelocity();
                    if (!DialogsActivity.this.startedTracking && Math.abs(xVelocity) >= AppUtils.getSwipeVelocity() && Math.abs(xVelocity) > Math.abs(yVelocity)) {
                        prepareForMoving(motionEvent, xVelocity < 0.0f);
                    }
                }
                boolean z4 = DialogsActivity.this.startedTracking;
                DialogsActivity dialogsActivity7 = DialogsActivity.this;
                if (z4) {
                    float x3 = dialogsActivity7.viewPages[0].getX();
                    DialogsActivity.this.tabsAnimation = new AnimatorSet();
                    boolean z5 = DialogsActivity.this.viewPages[1].isLocked;
                    DialogsActivity dialogsActivity8 = DialogsActivity.this;
                    if (z5) {
                        dialogsActivity8.backAnimation = true;
                    } else if (dialogsActivity8.additionalOffset != 0.0f) {
                        float fAbs2 = Math.abs(xVelocity);
                        DialogsActivity dialogsActivity9 = DialogsActivity.this;
                        if (fAbs2 > 1500.0f) {
                            dialogsActivity9.backAnimation = !dialogsActivity9.animatingForward ? xVelocity >= 0.0f : xVelocity <= 0.0f;
                        } else {
                            boolean z6 = dialogsActivity9.animatingForward;
                            DialogsActivity dialogsActivity10 = DialogsActivity.this;
                            if (z6) {
                                dialogsActivity10.backAnimation = dialogsActivity10.viewPages[1].getX() > ((float) (DialogsActivity.this.viewPages[0].getMeasuredWidth() >> 1));
                            } else {
                                dialogsActivity10.backAnimation = dialogsActivity10.viewPages[0].getX() < ((float) (DialogsActivity.this.viewPages[0].getMeasuredWidth() >> 1));
                            }
                        }
                    } else {
                        DialogsActivity.this.backAnimation = Math.abs(x3) < ((float) DialogsActivity.this.viewPages[0].getMeasuredWidth()) / 3.0f && (Math.abs(xVelocity) < ((float) AppUtils.getSwipeVelocity()) || Math.abs(xVelocity) < Math.abs(yVelocity));
                    }
                    boolean z7 = DialogsActivity.this.backAnimation;
                    Property property = View.TRANSLATION_X;
                    if (z7) {
                        measuredWidth = Math.abs(x3);
                        boolean z8 = DialogsActivity.this.animatingForward;
                        DialogsActivity dialogsActivity11 = DialogsActivity.this;
                        if (z8) {
                            dialogsActivity11.tabsAnimation.playTogether(ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[0], (Property<ViewPage, Float>) property, 0.0f), ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[1], (Property<ViewPage, Float>) property, DialogsActivity.this.viewPages[1].getMeasuredWidth()));
                        } else {
                            dialogsActivity11.tabsAnimation.playTogether(ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[0], (Property<ViewPage, Float>) property, 0.0f), ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[1], (Property<ViewPage, Float>) property, -DialogsActivity.this.viewPages[1].getMeasuredWidth()));
                        }
                    } else {
                        measuredWidth = DialogsActivity.this.viewPages[0].getMeasuredWidth() - Math.abs(x3);
                        boolean z9 = DialogsActivity.this.animatingForward;
                        DialogsActivity dialogsActivity12 = DialogsActivity.this;
                        if (z9) {
                            dialogsActivity12.tabsAnimation.playTogether(ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[0], (Property<ViewPage, Float>) property, -DialogsActivity.this.viewPages[0].getMeasuredWidth()), ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[1], (Property<ViewPage, Float>) property, 0.0f));
                        } else {
                            dialogsActivity12.tabsAnimation.playTogether(ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[0], (Property<ViewPage, Float>) property, DialogsActivity.this.viewPages[0].getMeasuredWidth()), ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[1], (Property<ViewPage, Float>) property, 0.0f));
                        }
                    }
                    DialogsActivity.this.tabsAnimation.setInterpolator(DialogsActivity.interpolator);
                    int measuredWidth5 = getMeasuredWidth();
                    float f3 = measuredWidth5 / 2;
                    float fDistanceInfluenceForSnapDuration = f3 + (AndroidUtilities.distanceInfluenceForSnapDuration(Math.min(1.0f, measuredWidth / measuredWidth5)) * f3);
                    float fAbs3 = Math.abs(xVelocity);
                    if (fAbs3 > 0.0f) {
                        measuredWidth2 = Math.round(Math.abs(fDistanceInfluenceForSnapDuration / fAbs3) * 1000.0f) * 4;
                    } else {
                        measuredWidth2 = (int) (((measuredWidth / getMeasuredWidth()) + 1.0f) * 100.0f);
                    }
                    DialogsActivity.this.tabsAnimation.setDuration(Math.max(150, Math.min(measuredWidth2, 600)));
                    DialogsActivity.this.tabsAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.ContentView.1
                        public C55741() {
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            DialogsActivity.this.tabsAnimation = null;
                            if (!DialogsActivity.this.backAnimation) {
                                ViewPage viewPage4 = DialogsActivity.this.viewPages[0];
                                DialogsActivity.this.viewPages[0] = DialogsActivity.this.viewPages[1];
                                DialogsActivity.this.viewPages[1] = viewPage4;
                                DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[0].selectedType, 1.0f);
                                DialogsActivity.this.updateCounters(false);
                                DialogsActivity.this.viewPages[0].dialogsAdapter.resume();
                                DialogsActivity.this.viewPages[1].dialogsAdapter.pause();
                            }
                            DialogsActivity.this.viewPages[1].setVisibility(8);
                            DialogsActivity.this.showScrollbars(true);
                            DialogsActivity.this.tabsAnimationInProgress = false;
                            DialogsActivity.this.maybeStartTracking = false;
                            ((BaseFragment) DialogsActivity.this).actionBar.setEnabled(true);
                            DialogsActivity.this.filterTabsView.setEnabled(true);
                            DialogsActivity dialogsActivity13 = DialogsActivity.this;
                            dialogsActivity13.checkListLoad(dialogsActivity13.viewPages[0]);
                            DialogsActivity.this.updateFloatingButtonVisibility(true);
                        }
                    });
                    DialogsActivity.this.tabsAnimation.start();
                    DialogsActivity.this.tabsAnimationInProgress = true;
                    DialogsActivity.this.startedTracking = false;
                } else {
                    dialogsActivity7.maybeStartTracking = false;
                    ((BaseFragment) DialogsActivity.this).actionBar.setEnabled(true);
                    DialogsActivity.this.filterTabsView.setEnabled(true);
                }
                VelocityTracker velocityTracker = this.velocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    this.velocityTracker = null;
                }
            }
            return DialogsActivity.this.startedTracking;
        }

        public /* synthetic */ void lambda$onTouchEvent$1() {
            DialogsActivity.this.showDialog(new LimitReachedBottomSheet(DialogsActivity.this, getContext(), 3, ((BaseFragment) DialogsActivity.this).currentAccount, null));
        }

        /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$ContentView$1 */
        /* JADX INFO: loaded from: classes6.dex */
        public class C55741 extends AnimatorListenerAdapter {
            public C55741() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                DialogsActivity.this.tabsAnimation = null;
                if (!DialogsActivity.this.backAnimation) {
                    ViewPage viewPage4 = DialogsActivity.this.viewPages[0];
                    DialogsActivity.this.viewPages[0] = DialogsActivity.this.viewPages[1];
                    DialogsActivity.this.viewPages[1] = viewPage4;
                    DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[0].selectedType, 1.0f);
                    DialogsActivity.this.updateCounters(false);
                    DialogsActivity.this.viewPages[0].dialogsAdapter.resume();
                    DialogsActivity.this.viewPages[1].dialogsAdapter.pause();
                }
                DialogsActivity.this.viewPages[1].setVisibility(8);
                DialogsActivity.this.showScrollbars(true);
                DialogsActivity.this.tabsAnimationInProgress = false;
                DialogsActivity.this.maybeStartTracking = false;
                ((BaseFragment) DialogsActivity.this).actionBar.setEnabled(true);
                DialogsActivity.this.filterTabsView.setEnabled(true);
                DialogsActivity dialogsActivity13 = DialogsActivity.this;
                dialogsActivity13.checkListLoad(dialogsActivity13.viewPages[0]);
                DialogsActivity.this.updateFloatingButtonVisibility(true);
            }
        }

        @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout
        public void drawList(Canvas canvas, boolean z, ArrayList<SizeNotifierFrameLayout.IViewWithInvalidateCallback> arrayList) {
            if (DialogsActivity.this.searchIsShowed && DialogsActivity.this.searchViewPager != null && DialogsActivity.this.searchViewPager.getVisibility() == 0) {
                DialogsActivity.this.searchViewPager.drawForBlur(canvas);
            }
        }

        @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            if (DialogsActivity.this.statusDrawable != null) {
                DialogsActivity.this.statusDrawable.attach();
            }
        }

        @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (DialogsActivity.this.statusDrawable != null) {
                DialogsActivity.this.statusDrawable.detach();
            }
        }
    }

    public float getSearchFieldAdditionOffset() {
        return -AndroidUtilities.lerp(AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(48.0f), this.animatorSearchVisible.getFloatValue());
    }

    private float getTitleAnimationPivotX() {
        if (!ExteraConfig.getCenterTitle()) {
            return AndroidUtilities.m1036dp(20.0f);
        }
        ActionBar actionBar = this.actionBar;
        if (actionBar == null || actionBar.getTitlesContainer() == null) {
            return AndroidUtilities.m1036dp(20.0f);
        }
        float measuredWidth = this.actionBar.getTitlesContainer().getMeasuredWidth();
        return measuredWidth <= 0.0f ? AndroidUtilities.m1036dp(20.0f) : measuredWidth / 2.0f;
    }

    public void updateStoriesViewAlpha(float f) {
        float f2;
        float f3;
        float fClamp = Utilities.clamp(this.searchAnimationProgress * 2.0f, 1.0f, 0.0f);
        DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
        float f4 = (1.0f - this.progressToActionMode) * f * this.progressToDialogStoriesCell;
        float f5 = 1.0f - fClamp;
        dialogStoriesCell.setAlpha(f4 * f5);
        if (this.hasStories || this.animateToHasStories) {
            float fClamp2 = Utilities.clamp((-this.scrollYOffset) / AndroidUtilities.m1036dp(81.0f), 1.0f, 0.0f);
            if (this.progressToActionMode == 1.0f) {
                fClamp2 = 1.0f;
            }
            float fClamp3 = Utilities.clamp(fClamp2 / 0.5f, 1.0f, 0.0f);
            this.dialogStoriesCell.setClipTop(0);
            if (!this.hasStories && this.animateToHasStories) {
                this.dialogStoriesCell.setTranslationY((-AndroidUtilities.m1036dp(81.0f)) - AndroidUtilities.m1036dp(8.0f));
                this.dialogStoriesCell.setProgressToCollapse(1.0f);
                f3 = this.progressToDialogStoriesCell;
            } else {
                this.dialogStoriesCell.setTranslationY(((Math.max(this.scrollYOffset, -getMaxScrollYOffsetWithoutSearch()) + this.storiesYOffset) + (this.storiesOverscroll / 2.0f)) - AndroidUtilities.m1036dp(8.0f));
                this.dialogStoriesCell.setProgressToCollapse(fClamp2, !this.rightSlidingDialogContainer.hasFragment());
                if (!this.animateToHasStories) {
                    f3 = this.progressToDialogStoriesCell;
                } else {
                    f2 = 1.0f - fClamp3;
                    this.actionBar.setTranslationY(0.0f);
                }
            }
            f2 = 1.0f - f3;
            this.actionBar.setTranslationY(0.0f);
        } else {
            if (this.hasOnlySlefStories) {
                this.dialogStoriesCell.setTranslationY(((-AndroidUtilities.m1036dp(81.0f)) + Math.max(this.scrollYOffset, -getMaxScrollYOffsetWithoutSearch())) - AndroidUtilities.m1036dp(8.0f));
                this.dialogStoriesCell.setProgressToCollapse(1.0f);
                DialogStoriesCell dialogStoriesCell2 = this.dialogStoriesCell;
                dialogStoriesCell2.setClipTop((int) (AndroidUtilities.statusBarHeight - dialogStoriesCell2.getY()));
            }
            f2 = 1.0f - this.progressToDialogStoriesCell;
            this.actionBar.setTranslationY(0.0f);
        }
        float f6 = f2 * f5;
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        float f7 = (rightSlidingDialogContainer == null || !rightSlidingDialogContainer.hasFragment()) ? 1.0f : 1.0f - this.rightSlidingDialogContainer.openedProgress;
        ActionBar actionBar = this.actionBar;
        if (f6 != 1.0f) {
            actionBar.getTitlesContainer().setPivotY(AndroidUtilities.statusBarHeight);
            this.actionBar.getTitlesContainer().setPivotX(getTitleAnimationPivotX());
            float f8 = (0.6f * f6) + 0.4f;
            this.actionBar.getTitlesContainer().setScaleY(f8);
            this.actionBar.getTitlesContainer().setScaleX(f8);
            this.actionBar.getAdditionalSubTitleOverlayContainer().setPivotX(0.0f);
            this.actionBar.getAdditionalSubTitleOverlayContainer().setPivotY(-AndroidUtilities.m1036dp(30.0f));
            this.actionBar.getAdditionalSubTitleOverlayContainer().setScaleY(f8);
            this.actionBar.getAdditionalSubTitleOverlayContainer().setScaleX(f8);
            float f9 = f6 * (1.0f - this.progressToActionMode) * f7;
            this.actionBar.getTitlesContainer().setAlpha(f9);
            this.actionBar.getTitlesContainer().setVisibility(f9 > 0.0f ? 0 : 4);
            this.actionBar.getAdditionalSubTitleOverlayContainer().setAlpha(f9);
            this.actionBar.getAdditionalSubTitleOverlayContainer().setVisibility(f9 <= 0.0f ? 4 : 0);
            return;
        }
        actionBar.getTitlesContainer().setScaleY(1.0f);
        this.actionBar.getTitlesContainer().setScaleX(1.0f);
        this.actionBar.getAdditionalSubTitleOverlayContainer().setScaleY(1.0f);
        this.actionBar.getAdditionalSubTitleOverlayContainer().setScaleX(1.0f);
        float f10 = (1.0f - this.progressToActionMode) * f7;
        this.actionBar.getTitlesContainer().setAlpha(f10);
        this.actionBar.getTitlesContainer().setVisibility(f10 > 0.0f ? 0 : 4);
        this.actionBar.getAdditionalSubTitleOverlayContainer().setAlpha(f10);
        this.actionBar.getAdditionalSubTitleOverlayContainer().setVisibility(f10 <= 0.0f ? 4 : 0);
    }

    public class DialogsRecyclerView extends BlurredRecyclerView implements StoriesListPlaceProvider.ClippedView {
        public int additionalPadding;
        float animateFromSelectorPosition;
        boolean animateSwitchingSelector;
        private RecyclerListView animationSupportListView;
        LongSparseArray<View> animationSupportViewsByDialogId;
        private int appliedPaddingTop;
        private boolean firstLayout;
        private boolean ignoreLayout;
        float lastDrawSelectorY;
        private int lastListPadding;
        private int lastTop;
        Paint paint;
        private final ViewPage parentPage;
        UserListPoller poller;
        RectF rectF;
        private float rightFragmentOpenedProgress;
        private Paint selectorPaint;
        float selectorPositionProgress;
        public boolean updateDialogsOnNextDraw;

        @Override // org.telegram.p035ui.Components.RecyclerListView
        public boolean updateEmptyViewAnimated() {
            return true;
        }

        public DialogsRecyclerView(Context context, ViewPage viewPage) {
            super(context);
            this.firstLayout = true;
            this.paint = new Paint();
            this.rectF = new RectF();
            this.selectorPositionProgress = 1.0f;
            this.parentPage = viewPage;
            this.additionalClipBottom = AndroidUtilities.m1036dp(200.0f);
        }

        public void prepareSelectorForAnimation() {
            this.selectorPositionProgress = 0.0f;
            this.animateFromSelectorPosition = this.lastDrawSelectorY;
            this.animateSwitchingSelector = this.rightFragmentOpenedProgress != 0.0f;
        }

        public void setViewsOffset(float f) {
            View viewFindViewByPosition;
            DialogsActivity.viewOffset = f;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).setTranslationY(f);
            }
            if (this.selectorPosition != -1 && (viewFindViewByPosition = getLayoutManager().findViewByPosition(this.selectorPosition)) != null) {
                this.selectorRect.set(viewFindViewByPosition.getLeft(), (int) (viewFindViewByPosition.getTop() + f), viewFindViewByPosition.getRight(), (int) (viewFindViewByPosition.getBottom() + f));
                this.selectorDrawable.setBounds(this.selectorRect);
            }
            invalidate();
        }

        public float getViewOffset() {
            return DialogsActivity.viewOffset;
        }

        @Override // org.telegram.p035ui.Components.BlurredRecyclerView
        public int measureBlurTopPadding() {
            return AndroidUtilities.m1036dp(48.0f);
        }

        @Override // android.view.ViewGroup
        public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
            super.addView(view, i, layoutParams);
            view.setTranslationY(DialogsActivity.viewOffset);
            view.setTranslationX(0.0f);
            view.setAlpha(1.0f);
        }

        @Override // android.view.ViewGroup, android.view.ViewManager
        public void removeView(View view) {
            super.removeView(view);
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            view.setAlpha(1.0f);
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
        public void onDraw(Canvas canvas) {
            if (this.parentPage.pullForegroundDrawable != null && DialogsActivity.viewOffset != 0.0f) {
                int paddingTop = getPaddingTop();
                if (paddingTop != 0) {
                    canvas.save();
                    canvas.translate(0.0f, paddingTop);
                }
                this.parentPage.pullForegroundDrawable.drawOverScroll(canvas);
                if (paddingTop != 0) {
                    canvas.restore();
                }
            }
            super.onDraw(canvas);
        }

        /* JADX WARN: Removed duplicated region for block: B:284:0x02a0  */
        /* JADX WARN: Removed duplicated region for block: B:287:0x02a5 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:291:0x02b0  */
        /* JADX WARN: Removed duplicated region for block: B:296:0x02bd  */
        /* JADX WARN: Removed duplicated region for block: B:299:0x02fb  */
        @Override // org.telegram.p035ui.Components.BlurredRecyclerView, org.telegram.p035ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void dispatchDraw(android.graphics.Canvas r23) {
            /*
                Method dump skipped, instruction units count: 1211
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.DialogsActivity.DialogsRecyclerView.dispatchDraw(android.graphics.Canvas):void");
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0 || motionEvent.getY() >= getPaddingTop() + DialogsActivity.this.scrollYOffset) {
                return super.dispatchTouchEvent(motionEvent);
            }
            return false;
        }

        private boolean drawMovingViewsOverlayed() {
            return getItemAnimator() != null && getItemAnimator().isRunning();
        }

        @Override // org.telegram.p035ui.Components.BlurredRecyclerView, org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean drawChild(Canvas canvas, View view, long j) {
            if (drawMovingViewsOverlayed() && (view instanceof DialogCell) && ((DialogCell) view).isMoving()) {
                return true;
            }
            return super.drawChild(canvas, view, j);
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView
        public void setAdapter(RecyclerView.Adapter adapter) {
            super.setAdapter(adapter);
            this.firstLayout = true;
        }

        @Override // org.telegram.p035ui.Components.BlurredRecyclerView, org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
        public void onMeasure(int i, int i2) {
            int iFindFirstVisibleItemPosition = this.parentPage.layoutManager.findFirstVisibleItemPosition();
            if (iFindFirstVisibleItemPosition != -1 && this.parentPage.itemTouchhelper.isIdle() && !this.parentPage.layoutManager.hasPendingScrollPosition() && this.parentPage.listView.getScrollState() != 1) {
                RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.parentPage.listView.findViewHolderForAdapterPosition(iFindFirstVisibleItemPosition);
                if (viewHolderFindViewHolderForAdapterPosition != null) {
                    int top = viewHolderFindViewHolderForAdapterPosition.itemView.getTop();
                    if (this.parentPage.dialogsType == 0 && DialogsActivity.this.hasHiddenArchive() && this.parentPage.archivePullViewState == 2) {
                        iFindFirstVisibleItemPosition = Math.max(1, iFindFirstVisibleItemPosition);
                    }
                    this.ignoreLayout = true;
                    this.parentPage.layoutManager.scrollToPositionWithOffset(iFindFirstVisibleItemPosition, (int) ((top - this.lastListPadding) + DialogsActivity.this.scrollAdditionalOffset + this.parentPage.pageAdditionalOffset));
                    this.ignoreLayout = false;
                }
            } else if (iFindFirstVisibleItemPosition == -1 && this.firstLayout) {
                this.parentPage.layoutManager.scrollToPositionWithOffset((this.parentPage.dialogsType == 0 && DialogsActivity.this.hasHiddenArchive()) ? 1 : 0, (int) DialogsActivity.this.scrollYOffset);
            }
            this.ignoreLayout = true;
            int currentActionBarHeight = ActionBar.getCurrentActionBarHeight() + (((BaseFragment) DialogsActivity.this).actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0);
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (dialogsActivity.hasStories && !dialogsActivity.actionModeFullyShowed) {
                currentActionBarHeight += AndroidUtilities.m1036dp(81.0f);
            }
            if (!DialogsActivity.this.actionModeFullyShowed) {
                currentActionBarHeight += DialogsActivity.this.getSearchFieldHeight();
            }
            this.additionalPadding = 0;
            float filterTabsVisibilityFactor = DialogsActivity.this.getFilterTabsVisibilityFactor(false);
            float totalVisibility = DialogsActivity.this.topPanelLayout != null ? DialogsActivity.this.topPanelLayout.getMetadata().getTotalVisibility() : 0.0f;
            int iM1036dp = currentActionBarHeight + ((int) (AndroidUtilities.m1036dp(50.0f) * filterTabsVisibilityFactor));
            this.additionalPadding += (int) (AndroidUtilities.m1036dp(50.0f) * filterTabsVisibilityFactor);
            if (DialogsActivity.this.topPanelLayout != null) {
                int animatedHeightWithPadding = (int) DialogsActivity.this.topPanelLayout.getAnimatedHeightWithPadding(AndroidUtilities.lerp(AndroidUtilities.m1036dp(14.0f), AndroidUtilities.m1036dp(7.0f), filterTabsVisibilityFactor));
                iM1036dp += animatedHeightWithPadding;
                this.additionalPadding += animatedHeightWithPadding;
            }
            int iM1036dp2 = iM1036dp - AndroidUtilities.m1036dp(Math.max(filterTabsVisibilityFactor, totalVisibility) * 5.0f);
            this.additionalPadding -= AndroidUtilities.m1036dp(Math.max(filterTabsVisibilityFactor, totalVisibility) * 5.0f);
            int iCalculateListViewPaddingBottom = DialogsActivity.this.calculateListViewPaddingBottom();
            if (iM1036dp2 != this.topPadding || iCalculateListViewPaddingBottom != getPaddingBottom()) {
                setTopGlowOffset(iM1036dp2);
                setPadding(0, iM1036dp2, 0, iCalculateListViewPaddingBottom);
                boolean z = DialogsActivity.this.hasStories;
                ViewPage viewPage = this.parentPage;
                if (z) {
                    viewPage.progressView.setPaddingTop(iM1036dp2 - AndroidUtilities.m1036dp(81.0f));
                } else {
                    viewPage.progressView.setPaddingTop(iM1036dp2);
                }
                for (int i3 = 0; i3 < getChildCount(); i3++) {
                    if (getChildAt(i3) instanceof DialogsAdapter.LastEmptyView) {
                        getChildAt(i3).requestLayout();
                    }
                }
            }
            this.ignoreLayout = false;
            if (this.firstLayout && DialogsActivity.this.getMessagesController().dialogsLoaded) {
                if (this.parentPage.dialogsType == 0 && DialogsActivity.this.hasHiddenArchive()) {
                    this.ignoreLayout = true;
                    ((LinearLayoutManager) getLayoutManager()).scrollToPositionWithOffset(1, (int) DialogsActivity.this.scrollYOffset);
                    this.ignoreLayout = false;
                }
                this.firstLayout = false;
            }
            super.onMeasure(i, i2);
            if (DialogsActivity.this.onlySelect || this.appliedPaddingTop == iM1036dp2 || DialogsActivity.this.viewPages == null || DialogsActivity.this.viewPages.length <= 1 || DialogsActivity.this.startedTracking) {
                return;
            }
            if ((DialogsActivity.this.tabsAnimation != null && DialogsActivity.this.tabsAnimation.isRunning()) || DialogsActivity.this.tabsAnimationInProgress || DialogsActivity.this.filterTabsView == null) {
                return;
            }
            DialogsActivity.this.filterTabsView.isAnimatingIndicator();
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            this.lastListPadding = getPaddingTop();
            this.lastTop = i2;
            DialogsActivity.this.scrollAdditionalOffset = 0.0f;
            this.parentPage.pageAdditionalOffset = 0;
        }

        @Override // org.telegram.p035ui.Components.BlurredRecyclerView, org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (this.ignoreLayout) {
                return;
            }
            super.requestLayout();
        }

        public void toggleArchiveHidden(boolean z, DialogCell dialogCell) {
            SharedConfig.toggleArchiveHidden();
            UndoView undoView = DialogsActivity.this.getUndoView();
            if (SharedConfig.archiveHidden) {
                if (dialogCell != null) {
                    DialogsActivity.this.disableActionBarScrolling = true;
                    DialogsActivity.this.waitingForScrollFinished = true;
                    int measuredHeight = dialogCell.getMeasuredHeight() + (dialogCell.getTop() - getPaddingTop());
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    if (dialogsActivity.hasStories && !dialogsActivity.dialogStoriesCell.isExpanded()) {
                        DialogsActivity.this.fixScrollYAfterArchiveOpened = true;
                        measuredHeight += AndroidUtilities.m1036dp(81.0f);
                    }
                    smoothScrollBy(0, measuredHeight, CubicBezierInterpolator.EASE_OUT);
                    if (z) {
                        DialogsActivity.this.updatePullAfterScroll = true;
                    } else {
                        updatePullState();
                    }
                }
                undoView.showWithAction(0L, 6, null, null);
                return;
            }
            undoView.showWithAction(0L, 7, null, null);
            updatePullState();
            if (!z || dialogCell == null) {
                return;
            }
            dialogCell.resetPinnedArchiveState();
            dialogCell.invalidate();
        }

        public void updatePullState() {
            this.parentPage.archivePullViewState = SharedConfig.archiveHidden ? 2 : 0;
            if (this.parentPage.pullForegroundDrawable != null) {
                this.parentPage.pullForegroundDrawable.setWillDraw(this.parentPage.archivePullViewState != 0);
            }
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (this.fastScrollAnimationRunning || DialogsActivity.this.waitingForScrollFinished || DialogsActivity.this.rightFragmentTransitionInProgress) {
                return false;
            }
            int action = motionEvent.getAction();
            if (action == 0) {
                setOverScrollMode(0);
            }
            if ((action == 1 || action == 3) && !this.parentPage.itemTouchhelper.isIdle() && this.parentPage.swipeController.swipingFolder) {
                this.parentPage.swipeController.swipeFolderBack = true;
                if (this.parentPage.itemTouchhelper.checkHorizontalSwipe(null, 4) != 0 && this.parentPage.swipeController.currentItemViewHolder != null) {
                    View view = this.parentPage.swipeController.currentItemViewHolder.itemView;
                    if (view instanceof DialogCell) {
                        DialogCell dialogCell = (DialogCell) view;
                        long dialogId = dialogCell.getDialogId();
                        if (DialogObject.isFolderDialogId(dialogId)) {
                            toggleArchiveHidden(false, dialogCell);
                        } else {
                            TLRPC.Dialog dialog = DialogsActivity.this.getMessagesController().dialogs_dict.get(dialogId);
                            if (dialog != null) {
                                if (SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) != 1) {
                                    int chatSwipeAction = SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount);
                                    DialogsActivity dialogsActivity = DialogsActivity.this;
                                    if (chatSwipeAction != 3) {
                                        if (SharedConfig.getChatSwipeAction(((BaseFragment) dialogsActivity).currentAccount) != 0) {
                                            if (SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 4) {
                                                ArrayList arrayList = new ArrayList();
                                                arrayList.add(Long.valueOf(dialogId));
                                                DialogsActivity.this.performSelectedDialogsAction(arrayList, 102, true, false);
                                            }
                                        } else {
                                            ArrayList arrayList2 = new ArrayList();
                                            arrayList2.add(Long.valueOf(dialogId));
                                            DialogsActivity.this.canPinCount = !DialogsActivity.this.isDialogPinned(dialog) ? 1 : 0;
                                            DialogsActivity.this.performSelectedDialogsAction(arrayList2, 100, true, false);
                                        }
                                    } else if (!dialogsActivity.getMessagesController().isDialogMuted(dialogId, 0L)) {
                                        NotificationsController.getInstance(UserConfig.selectedAccount).setDialogNotificationsSettings(dialogId, 0L, 3);
                                        if (BulletinFactory.canShowBulletin(DialogsActivity.this)) {
                                            BulletinFactory.createMuteBulletin(DialogsActivity.this, 3).show();
                                        }
                                    } else {
                                        ArrayList arrayList3 = new ArrayList();
                                        arrayList3.add(Long.valueOf(dialogId));
                                        DialogsActivity dialogsActivity2 = DialogsActivity.this;
                                        dialogsActivity2.canMuteCount = !MessagesController.getInstance(((BaseFragment) dialogsActivity2).currentAccount).isDialogMuted(dialogId, 0L) ? 1 : 0;
                                        DialogsActivity dialogsActivity3 = DialogsActivity.this;
                                        dialogsActivity3.canUnmuteCount = dialogsActivity3.canMuteCount > 0 ? 0 : 1;
                                        DialogsActivity.this.performSelectedDialogsAction(arrayList3, 104, true, false);
                                    }
                                } else {
                                    ArrayList arrayList4 = new ArrayList();
                                    arrayList4.add(Long.valueOf(dialogId));
                                    DialogsActivity.this.canReadCount = (dialog.unread_count > 0 || dialog.unread_mark) ? 1 : 0;
                                    DialogsActivity.this.performSelectedDialogsAction(arrayList4, 101, true, false);
                                }
                            }
                        }
                    }
                }
            }
            boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (this.parentPage.dialogsType == 0 && ((action == 1 || action == 3) && this.parentPage.archivePullViewState == 2 && DialogsActivity.this.hasHiddenArchive() && ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition() == 0)) {
                int paddingTop = getPaddingTop();
                DialogCell dialogCellFindArchiveDialogCell = DialogsActivity.this.findArchiveDialogCell(this.parentPage);
                if (dialogCellFindArchiveDialogCell != null) {
                    int iM1036dp = (int) (AndroidUtilities.m1036dp(SharedConfig.useThreeLinesLayout ? 76.0f : 70.0f) * 0.85f);
                    int top = (dialogCellFindArchiveDialogCell.getTop() - paddingTop) + dialogCellFindArchiveDialogCell.getMeasuredHeight();
                    long jCurrentTimeMillis = System.currentTimeMillis() - DialogsActivity.this.startArchivePullingTime;
                    if (top < iM1036dp || jCurrentTimeMillis < 200) {
                        DialogsActivity.this.disableActionBarScrolling = true;
                        smoothScrollBy(0, top, CubicBezierInterpolator.EASE_OUT_QUINT);
                        this.parentPage.archivePullViewState = 2;
                    } else if (this.parentPage.archivePullViewState != 1) {
                        if (getViewOffset() == 0.0f) {
                            DialogsActivity.this.disableActionBarScrolling = true;
                            smoothScrollBy(0, dialogCellFindArchiveDialogCell.getTop() - paddingTop, CubicBezierInterpolator.EASE_OUT_QUINT);
                        }
                        if (!DialogsActivity.this.canShowHiddenArchive) {
                            DialogsActivity.this.canShowHiddenArchive = true;
                            try {
                                performHapticFeedback(3, 2);
                            } catch (Exception unused) {
                            }
                            if (this.parentPage.pullForegroundDrawable != null) {
                                this.parentPage.pullForegroundDrawable.colorize(true);
                            }
                        }
                        dialogCellFindArchiveDialogCell.startOutAnimation();
                        this.parentPage.archivePullViewState = 1;
                        if (ExteraConfig.getArchiveOnPull()) {
                            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$DialogsRecyclerView$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$onTouchEvent$0();
                                }
                            }, 150L);
                        }
                        if (AndroidUtilities.isAccessibilityScreenReaderEnabled()) {
                            AndroidUtilities.makeAccessibilityAnnouncement(LocaleController.getString(C2797R.string.AccDescrArchivedChatsShown));
                        }
                    }
                    if (getViewOffset() != 0.0f) {
                        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(getViewOffset(), 0.0f);
                        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$DialogsRecyclerView$$ExternalSyntheticLambda1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                this.f$0.lambda$onTouchEvent$1(valueAnimator);
                            }
                        });
                        valueAnimatorOfFloat.setDuration(Math.max(100L, (long) (350.0f - ((getViewOffset() / PullForegroundDrawable.getMaxOverscroll()) * 120.0f))));
                        valueAnimatorOfFloat.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                        setScrollEnabled(false);
                        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.DialogsRecyclerView.1
                            public C55751() {
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                super.onAnimationEnd(animator);
                                DialogsRecyclerView.this.setScrollEnabled(true);
                            }
                        });
                        valueAnimatorOfFloat.start();
                    }
                }
            }
            return zOnTouchEvent;
        }

        public /* synthetic */ void lambda$onTouchEvent$0() {
            Bundle bundle = new Bundle();
            bundle.putInt("folderId", 1);
            bundle.putBoolean("onlySelect", DialogsActivity.this.onlySelect);
            DialogsActivity dialogsActivity = new DialogsActivity(bundle);
            dialogsActivity.setDelegate(DialogsActivity.this.delegate);
            DialogsActivity dialogsActivity2 = DialogsActivity.this;
            dialogsActivity2.presentFragment(dialogsActivity, dialogsActivity2.onlySelect);
        }

        public /* synthetic */ void lambda$onTouchEvent$1(ValueAnimator valueAnimator) {
            setViewsOffset(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }

        /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$DialogsRecyclerView$1 */
        /* JADX INFO: loaded from: classes6.dex */
        public class C55751 extends AnimatorListenerAdapter {
            public C55751() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                DialogsRecyclerView.this.setScrollEnabled(true);
            }
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (this.fastScrollAnimationRunning || DialogsActivity.this.waitingForScrollFinished || this.parentPage.dialogsItemAnimator.isRunning()) {
                return false;
            }
            if (motionEvent.getAction() == 0) {
                DialogsActivity.this.allowSwipeDuringCurrentTouch = !((BaseFragment) r0).actionBar.isActionModeShowed();
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView
        public boolean allowSelectChildAtPosition(View view) {
            return !(view instanceof HeaderCell) || view.isClickable();
        }

        public void setOpenRightFragmentProgress(float f) {
            this.rightFragmentOpenedProgress = f;
            invalidate();
        }

        public void setAnimationSupportView(RecyclerListView recyclerListView, float f, boolean z, boolean z2) {
            RecyclerListView recyclerListView2 = recyclerListView == null ? this.animationSupportListView : this;
            if (recyclerListView2 == null) {
                this.animationSupportListView = recyclerListView;
                return;
            }
            boolean z3 = false;
            DialogCell dialogCell = null;
            int top = Integer.MAX_VALUE;
            DialogCell dialogCell2 = null;
            for (int i = 0; i < recyclerListView2.getChildCount(); i++) {
                View childAt = recyclerListView2.getChildAt(i);
                if (childAt instanceof DialogCell) {
                    DialogCell dialogCell3 = (DialogCell) childAt;
                    if (dialogCell3.getDialogId() == DialogsActivity.this.rightSlidingDialogContainer.getCurrentFragmetDialogId()) {
                        dialogCell = dialogCell3;
                    }
                    if (childAt.getTop() >= 0 && dialogCell3.getDialogId() != 0 && childAt.getTop() < top) {
                        top = dialogCell3.getTop();
                        dialogCell2 = dialogCell3;
                    }
                }
            }
            if (dialogCell == null || getAdapter().getItemCount() * AndroidUtilities.m1036dp(70.0f) <= getMeasuredHeight() || dialogCell2.getTop() - getPaddingTop() <= (getMeasuredHeight() - getPaddingTop()) / 2.0f) {
                dialogCell = dialogCell2;
            }
            this.animationSupportListView = recyclerListView;
            if (dialogCell != null) {
                if (recyclerListView != null) {
                    recyclerListView.setPadding(getPaddingLeft(), this.topPadding, getPaddingLeft(), getPaddingBottom());
                    DialogsAdapter dialogsAdapter = (DialogsAdapter) recyclerListView.getAdapter();
                    int iFindDialogPosition = dialogsAdapter.findDialogPosition(dialogCell.getDialogId());
                    int top2 = (int) ((dialogCell.getTop() - recyclerListView2.getPaddingTop()) + f);
                    if (iFindDialogPosition >= 0) {
                        if (this.parentPage.dialogsType == 0 && this.parentPage.archivePullViewState == 2 && DialogsActivity.this.hasHiddenArchive()) {
                            z3 = true;
                        }
                        DialogsActivity dialogsActivity = DialogsActivity.this;
                        ((LinearLayoutManager) recyclerListView.getLayoutManager()).scrollToPositionWithOffset(iFindDialogPosition, dialogsAdapter.fixScrollGap(this, iFindDialogPosition, top2, z3, dialogsActivity.hasStories, dialogsActivity.canShowFilterTabsView, z));
                    }
                }
                int iFindDialogPosition2 = ((DialogsAdapter) getAdapter()).findDialogPosition(dialogCell.getDialogId());
                int top3 = dialogCell.getTop() - getPaddingTop();
                if (z2 && DialogsActivity.this.hasStories) {
                    top3 += AndroidUtilities.m1036dp(81.0f);
                }
                if (z2) {
                    top3 += DialogsActivity.this.getSearchFieldHeight();
                }
                if (iFindDialogPosition2 >= 0) {
                    ((LinearLayoutManager) getLayoutManager()).scrollToPositionWithOffset(iFindDialogPosition2, top3);
                }
            }
        }

        @Override // org.telegram.ui.Stories.StoriesListPlaceProvider.ClippedView
        public void updateClip(int[] iArr) {
            int paddingTop = (int) (getPaddingTop() + DialogsActivity.this.scrollYOffset);
            iArr[0] = paddingTop;
            iArr[1] = paddingTop + getMeasuredHeight();
        }
    }

    public StoriesController getStoriesController() {
        return getMessagesController().getStoriesController();
    }

    public class SwipeController extends ItemTouchHelper.Callback {
        private RecyclerView.ViewHolder currentItemViewHolder;
        private ViewPage parentPage;
        private boolean swipeFolderBack;
        private boolean swipingFolder;

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
            return 0.45f;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public float getSwipeVelocityThreshold(float f) {
            return Float.MAX_VALUE;
        }

        public SwipeController(ViewPage viewPage) {
            this.parentPage = viewPage;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            TLRPC.Dialog dialog;
            if (!DialogsActivity.this.waitingForDialogsAnimationEnd(this.parentPage) && ((((BaseFragment) DialogsActivity.this).parentLayout == null || !((BaseFragment) DialogsActivity.this).parentLayout.isInPreviewMode()) && !DialogsActivity.this.rightSlidingDialogContainer.hasFragment())) {
                if (this.swipingFolder && this.swipeFolderBack) {
                    View view = viewHolder.itemView;
                    if (view instanceof DialogCell) {
                        ((DialogCell) view).swipeCanceled = true;
                    }
                    this.swipingFolder = false;
                    return 0;
                }
                if (!DialogsActivity.this.onlySelect && this.parentPage.isDefaultDialogType() && DialogsActivity.this.slidingView == null) {
                    View view2 = viewHolder.itemView;
                    if (view2 instanceof DialogCell) {
                        DialogCell dialogCell = (DialogCell) view2;
                        long dialogId = dialogCell.getDialogId();
                        MessagesController.DialogFilter dialogFilter = null;
                        boolean zIsActionModeShowed = ((BaseFragment) DialogsActivity.this).actionBar.isActionModeShowed(null);
                        DialogsActivity dialogsActivity = DialogsActivity.this;
                        if (zIsActionModeShowed) {
                            TLRPC.Dialog dialog2 = dialogsActivity.getMessagesController().dialogs_dict.get(dialogId);
                            if (!DialogsActivity.this.allowMoving || dialog2 == null || !DialogsActivity.this.isDialogPinned(dialog2) || DialogObject.isFolderDialogId(dialogId)) {
                                return 0;
                            }
                            DialogsActivity.this.movingView = (DialogCell) viewHolder.itemView;
                            DialogsActivity.this.movingView.setBackgroundColor(DialogsActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                            this.swipeFolderBack = false;
                            return ItemTouchHelper.Callback.makeMovementFlags(3, 0);
                        }
                        int dialogsType = dialogsActivity.initialDialogsType;
                        try {
                            dialogsType = this.parentPage.dialogsAdapter.getDialogsType();
                        } catch (Exception unused) {
                        }
                        if ((DialogsActivity.this.filterTabsView == null || DialogsActivity.this.filterTabsView.getVisibility() != 0 || SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) != 5) && DialogsActivity.this.allowSwipeDuringCurrentTouch && (((dialogId != DialogsActivity.this.getUserConfig().clientUserId && dialogId != 777000 && dialogsType != 7 && dialogsType != 8) || SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) != 2) && (!DialogsActivity.this.getMessagesController().isPromoDialog(dialogId, false) || DialogsActivity.this.getMessagesController().promoDialogType == MessagesController.PROMO_TYPE_PSA))) {
                            if (DialogsActivity.this.folderId != 0 && ExteraConfig.getDisableUnarchiveSwipe()) {
                                return 0;
                            }
                            boolean z = DialogsActivity.this.folderId == 0 && (SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 3 || SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 1 || SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 0 || SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 4) && !DialogsActivity.this.rightSlidingDialogContainer.hasFragment();
                            if (SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 1) {
                                if (DialogsActivity.this.viewPages[0].dialogsType == 7 || DialogsActivity.this.viewPages[0].dialogsType == 8) {
                                    dialogFilter = DialogsActivity.this.getMessagesController().selectedDialogFilter[DialogsActivity.this.viewPages[0].dialogsType == 8 ? (char) 1 : (char) 0];
                                }
                                if (dialogFilter != null && (dialogFilter.flags & MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_READ) != 0 && (dialog = DialogsActivity.this.getMessagesController().dialogs_dict.get(dialogId)) != null && !dialogFilter.alwaysShow(((BaseFragment) DialogsActivity.this).currentAccount, dialog) && (dialog.unread_count > 0 || dialog.unread_mark)) {
                                    z = false;
                                }
                            }
                            this.swipeFolderBack = false;
                            this.swipingFolder = (z && !DialogObject.isFolderDialogId(dialogCell.getDialogId())) || (SharedConfig.archiveHidden && DialogObject.isFolderDialogId(dialogCell.getDialogId()));
                            dialogCell.setSliding(true);
                            return ItemTouchHelper.Callback.makeMovementFlags(0, 4);
                        }
                    }
                }
            }
            return 0;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            long dialogId;
            TLRPC.Dialog dialog;
            View view = viewHolder2.itemView;
            if (!(view instanceof DialogCell) || (dialog = DialogsActivity.this.getMessagesController().dialogs_dict.get((dialogId = ((DialogCell) view).getDialogId()))) == null || !DialogsActivity.this.isDialogPinned(dialog) || DialogObject.isFolderDialogId(dialogId)) {
                return false;
            }
            int adapterPosition = viewHolder.getAdapterPosition();
            int adapterPosition2 = viewHolder2.getAdapterPosition();
            if (this.parentPage.listView.getItemAnimator() == null) {
                ViewPage viewPage = this.parentPage;
                viewPage.listView.setItemAnimator(viewPage.dialogsItemAnimator);
            }
            this.parentPage.dialogsAdapter.moveDialogs(this.parentPage.listView, adapterPosition, adapterPosition2);
            if (DialogsActivity.this.viewPages[0].dialogsType == 7 || DialogsActivity.this.viewPages[0].dialogsType == 8) {
                MessagesController.DialogFilter dialogFilter = DialogsActivity.this.getMessagesController().selectedDialogFilter[DialogsActivity.this.viewPages[0].dialogsType == 8 ? (char) 1 : (char) 0];
                if (!DialogsActivity.this.movingDialogFilters.contains(dialogFilter)) {
                    DialogsActivity.this.movingDialogFilters.add(dialogFilter);
                }
            } else {
                DialogsActivity.this.movingWas = true;
            }
            return true;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public int convertToAbsoluteDirection(int i, int i2) {
            if (this.swipeFolderBack) {
                return 0;
            }
            return super.convertToAbsoluteDirection(i, i2);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder != null) {
                DialogCell dialogCell = (DialogCell) viewHolder.itemView;
                long dialogId = dialogCell.getDialogId();
                if (DialogObject.isFolderDialogId(dialogId)) {
                    this.parentPage.listView.toggleArchiveHidden(false, dialogCell);
                    return;
                }
                final TLRPC.Dialog dialog = DialogsActivity.this.getMessagesController().dialogs_dict.get(dialogId);
                if (dialog == null) {
                    return;
                }
                if (!DialogsActivity.this.getMessagesController().isPromoDialog(dialogId, false) && DialogsActivity.this.folderId == 0 && SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 1) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(Long.valueOf(dialogId));
                    DialogsActivity.this.canReadCount = (dialog.unread_count > 0 || dialog.unread_mark) ? 1 : 0;
                    DialogsActivity.this.performSelectedDialogsAction(arrayList, 101, true, false);
                    return;
                }
                DialogsActivity.this.slidingView = dialogCell;
                final int adapterPosition = viewHolder.getAdapterPosition();
                final int itemCount = this.parentPage.dialogsAdapter.getItemCount();
                Runnable runnable = new Runnable() { // from class: org.telegram.ui.DialogsActivity$SwipeController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSwiped$3(dialog, itemCount, adapterPosition);
                    }
                };
                DialogsActivity.this.setDialogsListFrozen(true);
                if (Utilities.random.nextInt(MediaDataController.MAX_STYLE_RUNS_COUNT) == 1) {
                    if (DialogsActivity.this.pacmanAnimation == null) {
                        DialogsActivity.this.pacmanAnimation = new PacmanAnimation(this.parentPage.listView);
                    }
                    DialogsActivity.this.pacmanAnimation.setFinishRunnable(runnable);
                    DialogsActivity.this.pacmanAnimation.start();
                    return;
                }
                runnable.run();
                return;
            }
            DialogsActivity.this.slidingView = null;
        }

        public /* synthetic */ void lambda$onSwiped$3(final TLRPC.Dialog dialog, int i, int i2) {
            if (DialogsActivity.this.frozenDialogsList == null) {
                return;
            }
            DialogsActivity.this.frozenDialogsList.remove(dialog);
            final int i3 = dialog.pinnedNum;
            DialogsActivity.this.slidingView = null;
            this.parentPage.listView.invalidate();
            int iFindLastVisibleItemPosition = this.parentPage.layoutManager.findLastVisibleItemPosition();
            if (iFindLastVisibleItemPosition == i - 1) {
                this.parentPage.layoutManager.findViewByPosition(iFindLastVisibleItemPosition).requestLayout();
            }
            boolean zIsPromoDialog = DialogsActivity.this.getMessagesController().isPromoDialog(dialog.f1251id, false);
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (zIsPromoDialog) {
                dialogsActivity.getMessagesController().hidePromoDialog();
                this.parentPage.dialogsItemAnimator.prepareForRemove();
                this.parentPage.updateList(true);
                return;
            }
            int iAddDialogToFolder = dialogsActivity.getMessagesController().addDialogToFolder(dialog.f1251id, DialogsActivity.this.folderId == 0 ? 1 : 0, -1, 0L);
            if (iAddDialogToFolder != 2 || i2 != 0) {
                this.parentPage.dialogsItemAnimator.prepareForRemove();
                this.parentPage.updateList(true);
            }
            if (DialogsActivity.this.folderId == 0) {
                if (iAddDialogToFolder == 2) {
                    if (SharedConfig.archiveHidden) {
                        SharedConfig.toggleArchiveHidden();
                    }
                    this.parentPage.dialogsItemAnimator.prepareForRemove();
                    if (i2 == 0) {
                        DialogsActivity.this.setDialogsListFrozen(true);
                        this.parentPage.updateList(true);
                        DialogsActivity.this.checkAnimationFinished();
                    } else {
                        this.parentPage.updateList(true);
                        if (!SharedConfig.archiveHidden && this.parentPage.layoutManager.findFirstVisibleItemPosition() == 0) {
                            DialogsActivity.this.disableActionBarScrolling = true;
                            this.parentPage.listView.smoothScrollBy(0, -AndroidUtilities.m1036dp(SharedConfig.useThreeLinesLayout ? 76.0f : 70.0f));
                        }
                    }
                    DialogsActivity dialogsActivity2 = DialogsActivity.this;
                    DialogsActivity.this.frozenDialogsList.add(0, dialogsActivity2.getDialogsArray(((BaseFragment) dialogsActivity2).currentAccount, this.parentPage.dialogsType, DialogsActivity.this.folderId, false).get(0));
                    this.parentPage.updateList(true);
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$SwipeController$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onSwiped$0();
                        }
                    }, 300L);
                } else if (iAddDialogToFolder == 1) {
                    RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.parentPage.listView.findViewHolderForAdapterPosition(0);
                    if (viewHolderFindViewHolderForAdapterPosition != null) {
                        View view = viewHolderFindViewHolderForAdapterPosition.itemView;
                        if (view instanceof DialogCell) {
                            DialogCell dialogCell = (DialogCell) view;
                            dialogCell.checkCurrentDialogIndex(true);
                            dialogCell.animateArchiveAvatar();
                        }
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$SwipeController$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onSwiped$1();
                        }
                    }, 300L);
                }
                SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
                boolean z = globalMainSettings.getBoolean("archivehint_l", false) || SharedConfig.archiveHidden;
                if (!z) {
                    globalMainSettings.edit().putBoolean("archivehint_l", true).apply();
                }
                UndoView undoView = DialogsActivity.this.getUndoView();
                if (undoView != null) {
                    undoView.showWithAction(dialog.f1251id, z ? 2 : 3, null, new Runnable() { // from class: org.telegram.ui.DialogsActivity$SwipeController$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onSwiped$2(dialog, i3);
                        }
                    });
                }
            }
            if (DialogsActivity.this.folderId == 0 || !DialogsActivity.this.frozenDialogsList.isEmpty()) {
                return;
            }
            this.parentPage.listView.setEmptyView(null);
            this.parentPage.progressView.setVisibility(4);
        }

        public /* synthetic */ void lambda$onSwiped$0() {
            DialogsActivity.this.setDialogsListFrozen(false);
        }

        public /* synthetic */ void lambda$onSwiped$1() {
            DialogsActivity.this.setDialogsListFrozen(false);
        }

        public /* synthetic */ void lambda$onSwiped$2(TLRPC.Dialog dialog, int i) {
            DialogsActivity.this.dialogsListFrozen = true;
            DialogsActivity.this.getMessagesController().addDialogToFolder(dialog.f1251id, 0, i, 0L);
            DialogsActivity.this.dialogsListFrozen = false;
            ArrayList<TLRPC.Dialog> dialogs = DialogsActivity.this.getMessagesController().getDialogs(0);
            int iIndexOf = dialogs.indexOf(dialog);
            if (iIndexOf >= 0) {
                ArrayList<TLRPC.Dialog> dialogs2 = DialogsActivity.this.getMessagesController().getDialogs(1);
                if (!dialogs2.isEmpty() || iIndexOf != 1) {
                    DialogsActivity.this.setDialogsListFrozen(true);
                    this.parentPage.dialogsItemAnimator.prepareForRemove();
                    this.parentPage.updateList(true);
                    DialogsActivity.this.checkAnimationFinished();
                }
                if (dialogs2.isEmpty()) {
                    dialogs.remove(0);
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    if (iIndexOf == 1) {
                        dialogsActivity.setDialogsListFrozen(true);
                        this.parentPage.updateList(true);
                        DialogsActivity.this.checkAnimationFinished();
                        return;
                    } else {
                        if (!dialogsActivity.frozenDialogsList.isEmpty()) {
                            DialogsActivity.this.frozenDialogsList.remove(0);
                        }
                        this.parentPage.dialogsItemAnimator.prepareForRemove();
                        this.parentPage.updateList(true);
                        return;
                    }
                }
                return;
            }
            this.parentPage.updateList(false);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder != null) {
                this.parentPage.listView.hideSelector(false);
            }
            this.currentItemViewHolder = viewHolder;
            if (viewHolder != null) {
                View view = viewHolder.itemView;
                if (view instanceof DialogCell) {
                    ((DialogCell) view).swipeCanceled = false;
                }
            }
            super.onSelectedChanged(viewHolder, i);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public long getAnimationDuration(RecyclerView recyclerView, int i, float f, float f2) {
            if (i == 4) {
                return 200L;
            }
            if (i == 8 && DialogsActivity.this.movingView != null) {
                final DialogCell dialogCell = DialogsActivity.this.movingView;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$SwipeController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        dialogCell.setBackgroundDrawable(null);
                    }
                }, this.parentPage.dialogsItemAnimator.getMoveDuration());
                DialogsActivity.this.movingView = null;
            }
            return super.getAnimationDuration(recyclerView, i, f, f2);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public float getSwipeEscapeVelocity(float f) {
            return AppUtils.getSwipeVelocity();
        }
    }

    public DialogsActivity(Bundle bundle) {
        super(bundle);
        int i = Build.VERSION.SDK_INT;
        this.ADDITIONAL_LIST_HEIGHT_DP = i >= 31 ? 48 : 0;
        this.ANIMATOR_ID_SEARCH_VISIBLE = 1;
        this.ANIMATOR_ID_DONE_BUTTON_VISIBLE = 2;
        this.ANIMATOR_ID_SPEED_BUTTON_VISIBLE = 3;
        this.ANIMATOR_ID_SHADOW_VISIBLE = 4;
        this.ANIMATOR_ID_SEARCH_BUTTON_VISIBLE = 5;
        this.ANIMATOR_ID_ACTION_MODE_VISIBLE = 6;
        this.ANIMATOR_ID_FORWARD_BUTTON_VISIBLE = 7;
        this.ANIMATOR_ID_FILTER_TABS_VISIBLE = 8;
        this.ANIMATOR_ID_SEARCH_FILTER_TABS_VISIBLE = 9;
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
        this.animatorSearchVisible = new BoolAnimator(1, this, cubicBezierInterpolator, 350L);
        this.animatorDoneButtonVisible = new BoolAnimator(2, this, cubicBezierInterpolator, 350L);
        this.animatorSpeedButtonVisible = new BoolAnimator(3, this, cubicBezierInterpolator, 350L);
        this.animatorShadowVisible = new BoolAnimator(4, this, cubicBezierInterpolator, 350L);
        this.animatorSearchButtonVisible = new BoolAnimator(5, this, cubicBezierInterpolator, 350L);
        this.animatorActionModeVisible = new BoolAnimator(6, this, cubicBezierInterpolator, 350L);
        this.animatorForwardButtonVisible = new BoolAnimator(7, this, cubicBezierInterpolator, 350L);
        this.animatorFilterTabsVisible = new BoolAnimator(8, this, cubicBezierInterpolator, 350L);
        this.animatorSearchFilterTabsVisible = new BoolAnimator(9, this, cubicBezierInterpolator, 350L);
        this.windowInsetsStateHolder = new WindowInsetsStateHolder(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda36
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.checkInsets();
            }
        });
        this.initialSearchType = -1;
        this.ACTION_MODE_SEARCH_DIALOGS_TAG = "search_dialogs_action_mode";
        this.allowGlobalSearch = true;
        this.hasStories = false;
        this.hasOnlySlefStories = false;
        this.animateToHasStories = false;
        this.invalidateScrollY = true;
        this.contactsAlpha = 1.0f;
        this.undoView = new UndoView[2];
        this.movingDialogFilters = new ArrayList<>();
        this.actionBarDefaultPaint = new Paint();
        this.actionModeViews = new ArrayList<>();
        this.rect = new RectF();
        this.paint = new Paint(1);
        this.textPaint = new TextPaint(1);
        this.askAboutContacts = true;
        this.checkPermission = true;
        this.resetDelegate = true;
        this.openedDialogId = new MessagesStorage.TopicKey();
        this.selectedDialogs = new ArrayList<>();
        this.notify = true;
        this.notificationsLocker = new AnimationNotificationsLocker();
        this.debugLastUpdateAction = -1;
        this.SCROLL_Y = new AnimationProperties.FloatProperty<DialogsActivity>("animationValue") { // from class: org.telegram.ui.DialogsActivity.1
            public C55211(String str) {
                super(str);
            }

            @Override // org.telegram.ui.Components.AnimationProperties.FloatProperty
            public void setValue(DialogsActivity dialogsActivity, float f) {
                dialogsActivity.setScrollY(f);
            }

            @Override // android.util.Property
            public Float get(DialogsActivity dialogsActivity) {
                return Float.valueOf(DialogsActivity.this.scrollYOffset);
            }
        };
        this.SEARCH_TRANSLATION_Y = new AnimationProperties.FloatProperty<View>("viewPagerTranslation") { // from class: org.telegram.ui.DialogsActivity.2
            public C55322(String str) {
                super(str);
            }

            @Override // org.telegram.ui.Components.AnimationProperties.FloatProperty
            public void setValue(View view, float f) {
                DialogsActivity dialogsActivity = DialogsActivity.this;
                dialogsActivity.searchViewPagerTranslationY = f;
                view.setTranslationY(dialogsActivity.panTranslationY + f);
                DialogsActivity.this.checkUi_searchFiltersVisibility();
            }

            @Override // android.util.Property
            public Float get(View view) {
                return Float.valueOf(DialogsActivity.this.searchViewPagerTranslationY);
            }
        };
        this.animatorBottomTabsOffset = new BoolAnimator(0, new FactorAnimator.Target() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda37
            @Override // me.vkryl.android.animator.FactorAnimator.Target
            public final void onFactorChanged(int i2, float f, float f2, FactorAnimator factorAnimator) {
                this.f$0.lambda$new$4(i2, f, f2, factorAnimator);
            }
        }, cubicBezierInterpolator, 380L, true);
        this.shiftDp = -4;
        this.scrollBarVisible = true;
        this.storiesEnabled = true;
        this.slideFragmentProgress = 1.0f;
        this.slideAmplitudeDp = 40;
        ArrayList<RectF> arrayList = new ArrayList<>();
        this.iBlur3Positions = arrayList;
        RectF rectF = new RectF();
        this.iBlur3PositionActionBar = rectF;
        RectF rectF2 = new RectF();
        this.iBlur3PositionMainTabs = rectF2;
        arrayList.add(rectF);
        arrayList.add(rectF2);
        BlurredBackgroundSourceColor blurredBackgroundSourceColor = new BlurredBackgroundSourceColor();
        this.iBlur3SourceColor = blurredBackgroundSourceColor;
        blurredBackgroundSourceColor.setColor(getThemedColor(Theme.key_windowBackgroundWhite));
        if (i >= 31) {
            this.scrollableViewNoiseSuppressor = new DownscaleScrollableNoiseSuppressor();
            BlurredBackgroundSourceRenderNode blurredBackgroundSourceRenderNode = new BlurredBackgroundSourceRenderNode(null);
            this.iBlur3SourceGlassFrosted = blurredBackgroundSourceRenderNode;
            blurredBackgroundSourceRenderNode.setupRenderer(new RenderNodeWithHash.Renderer() { // from class: org.telegram.ui.DialogsActivity.3
                public C55433() {
                }

                @Override // org.telegram.ui.Components.blur3.RenderNodeWithHash.Renderer
                public void renderNodeCalculateHash(IBlur3Hash iBlur3Hash) {
                    iBlur3Hash.add(DialogsActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                    iBlur3Hash.add(SharedConfig.chatBlurEnabled());
                    if (SharedConfig.chatBlurEnabled()) {
                        RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                        TopicsFragment topicsFragment = (rightSlidingDialogContainer == null || !(rightSlidingDialogContainer.getFragment() instanceof TopicsFragment)) ? null : (TopicsFragment) DialogsActivity.this.rightSlidingDialogContainer.getFragment();
                        if (topicsFragment == null || topicsFragment.getFragmentView() == null || DialogsActivity.this.searching) {
                            return;
                        }
                        iBlur3Hash.unsupported();
                    }
                }

                @Override // org.telegram.ui.Components.blur3.RenderNodeWithHash.Renderer
                public void renderNodeUpdateDisplayList(Canvas canvas) {
                    Canvas canvas2;
                    BlurredBackgroundSourceRenderNode frostedGlassSource;
                    int measuredWidth = DialogsActivity.this.fragmentView.getMeasuredWidth();
                    int measuredHeight = DialogsActivity.this.fragmentView.getMeasuredHeight();
                    canvas.drawColor(DialogsActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                    if (SharedConfig.chatBlurEnabled()) {
                        RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                        TopicsFragment topicsFragment = (rightSlidingDialogContainer == null || !(rightSlidingDialogContainer.getFragment() instanceof TopicsFragment)) ? null : (TopicsFragment) DialogsActivity.this.rightSlidingDialogContainer.getFragment();
                        if (topicsFragment == null || topicsFragment.getFragmentView() == null || DialogsActivity.this.searching || (frostedGlassSource = topicsFragment.getFrostedGlassSource()) == null) {
                            canvas2 = canvas;
                        } else {
                            canvas.save();
                            canvas.translate(topicsFragment.getFragmentView().getTranslationX(), topicsFragment.getFragmentView().getTranslationY());
                            canvas2 = canvas;
                            frostedGlassSource.draw(canvas2, 0.0f, 0.0f, measuredWidth, measuredHeight);
                            canvas2.restore();
                        }
                        DialogsActivity.this.scrollableViewNoiseSuppressor.draw(canvas2, -3);
                    }
                }
            });
            BlurredBackgroundSourceRenderNode blurredBackgroundSourceRenderNode2 = new BlurredBackgroundSourceRenderNode(null);
            this.iBlur3SourceGlass = blurredBackgroundSourceRenderNode2;
            blurredBackgroundSourceRenderNode2.setupRenderer(new RenderNodeWithHash.Renderer() { // from class: org.telegram.ui.DialogsActivity.4
                public C55544() {
                }

                @Override // org.telegram.ui.Components.blur3.RenderNodeWithHash.Renderer
                public void renderNodeCalculateHash(IBlur3Hash iBlur3Hash) {
                    iBlur3Hash.add(DialogsActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                    iBlur3Hash.add(SharedConfig.chatBlurEnabled());
                    if (SharedConfig.chatBlurEnabled()) {
                        RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                        TopicsFragment topicsFragment = (rightSlidingDialogContainer == null || !(rightSlidingDialogContainer.getFragment() instanceof TopicsFragment)) ? null : (TopicsFragment) DialogsActivity.this.rightSlidingDialogContainer.getFragment();
                        if (topicsFragment == null || topicsFragment.getFragmentView() == null || DialogsActivity.this.searching) {
                            return;
                        }
                        iBlur3Hash.unsupported();
                    }
                }

                @Override // org.telegram.ui.Components.blur3.RenderNodeWithHash.Renderer
                public void renderNodeUpdateDisplayList(Canvas canvas) {
                    Canvas canvas2;
                    BlurredBackgroundSourceRenderNode glassSource;
                    int measuredWidth = DialogsActivity.this.fragmentView.getMeasuredWidth();
                    int measuredHeight = DialogsActivity.this.fragmentView.getMeasuredHeight();
                    canvas.drawColor(DialogsActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                    if (SharedConfig.chatBlurEnabled()) {
                        RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                        TopicsFragment topicsFragment = (rightSlidingDialogContainer == null || !(rightSlidingDialogContainer.getFragment() instanceof TopicsFragment)) ? null : (TopicsFragment) DialogsActivity.this.rightSlidingDialogContainer.getFragment();
                        if (topicsFragment == null || topicsFragment.getFragmentView() == null || DialogsActivity.this.searching || (glassSource = topicsFragment.getGlassSource()) == null) {
                            canvas2 = canvas;
                        } else {
                            canvas.save();
                            canvas.translate(topicsFragment.getFragmentView().getTranslationX(), topicsFragment.getFragmentView().getTranslationY());
                            canvas2 = canvas;
                            glassSource.draw(canvas2, 0.0f, 0.0f, measuredWidth, measuredHeight);
                            canvas2.restore();
                        }
                        DialogsActivity.this.scrollableViewNoiseSuppressor.draw(canvas2, -2);
                    }
                }
            });
            BlurredBackgroundDrawableViewFactory blurredBackgroundDrawableViewFactory = new BlurredBackgroundDrawableViewFactory(blurredBackgroundSourceRenderNode);
            this.iBlur3FactoryFrostedLiquidGlass = blurredBackgroundDrawableViewFactory;
            blurredBackgroundDrawableViewFactory.setLiquidGlassEffectAllowed(LiteMode.isEnabled(262144));
            BlurredBackgroundDrawableViewFactory blurredBackgroundDrawableViewFactory2 = new BlurredBackgroundDrawableViewFactory(blurredBackgroundSourceRenderNode2);
            this.iBlur3FactoryLiquidGlass = blurredBackgroundDrawableViewFactory2;
            blurredBackgroundDrawableViewFactory2.setLiquidGlassEffectAllowed(LiteMode.isEnabled(262144));
            this.iBlur3FactoryBlur = new BlurredBackgroundDrawableViewFactory(blurredBackgroundSourceRenderNode);
        } else {
            this.scrollableViewNoiseSuppressor = null;
            this.iBlur3SourceGlassFrosted = null;
            this.iBlur3SourceGlass = null;
            this.iBlur3FactoryFrostedLiquidGlass = new BlurredBackgroundDrawableViewFactory(blurredBackgroundSourceColor);
            this.iBlur3FactoryLiquidGlass = new BlurredBackgroundDrawableViewFactory(blurredBackgroundSourceColor);
            this.iBlur3FactoryBlur = new BlurredBackgroundDrawableViewFactory(blurredBackgroundSourceColor);
        }
        this.iBlur3FactoryFade = new BlurredBackgroundDrawableViewFactory(blurredBackgroundSourceColor);
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$3 */
    public class C55433 implements RenderNodeWithHash.Renderer {
        public C55433() {
        }

        @Override // org.telegram.ui.Components.blur3.RenderNodeWithHash.Renderer
        public void renderNodeCalculateHash(IBlur3Hash iBlur3Hash) {
            iBlur3Hash.add(DialogsActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
            iBlur3Hash.add(SharedConfig.chatBlurEnabled());
            if (SharedConfig.chatBlurEnabled()) {
                RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                TopicsFragment topicsFragment = (rightSlidingDialogContainer == null || !(rightSlidingDialogContainer.getFragment() instanceof TopicsFragment)) ? null : (TopicsFragment) DialogsActivity.this.rightSlidingDialogContainer.getFragment();
                if (topicsFragment == null || topicsFragment.getFragmentView() == null || DialogsActivity.this.searching) {
                    return;
                }
                iBlur3Hash.unsupported();
            }
        }

        @Override // org.telegram.ui.Components.blur3.RenderNodeWithHash.Renderer
        public void renderNodeUpdateDisplayList(Canvas canvas) {
            Canvas canvas2;
            BlurredBackgroundSourceRenderNode frostedGlassSource;
            int measuredWidth = DialogsActivity.this.fragmentView.getMeasuredWidth();
            int measuredHeight = DialogsActivity.this.fragmentView.getMeasuredHeight();
            canvas.drawColor(DialogsActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
            if (SharedConfig.chatBlurEnabled()) {
                RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                TopicsFragment topicsFragment = (rightSlidingDialogContainer == null || !(rightSlidingDialogContainer.getFragment() instanceof TopicsFragment)) ? null : (TopicsFragment) DialogsActivity.this.rightSlidingDialogContainer.getFragment();
                if (topicsFragment == null || topicsFragment.getFragmentView() == null || DialogsActivity.this.searching || (frostedGlassSource = topicsFragment.getFrostedGlassSource()) == null) {
                    canvas2 = canvas;
                } else {
                    canvas.save();
                    canvas.translate(topicsFragment.getFragmentView().getTranslationX(), topicsFragment.getFragmentView().getTranslationY());
                    canvas2 = canvas;
                    frostedGlassSource.draw(canvas2, 0.0f, 0.0f, measuredWidth, measuredHeight);
                    canvas2.restore();
                }
                DialogsActivity.this.scrollableViewNoiseSuppressor.draw(canvas2, -3);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$4 */
    public class C55544 implements RenderNodeWithHash.Renderer {
        public C55544() {
        }

        @Override // org.telegram.ui.Components.blur3.RenderNodeWithHash.Renderer
        public void renderNodeCalculateHash(IBlur3Hash iBlur3Hash) {
            iBlur3Hash.add(DialogsActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
            iBlur3Hash.add(SharedConfig.chatBlurEnabled());
            if (SharedConfig.chatBlurEnabled()) {
                RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                TopicsFragment topicsFragment = (rightSlidingDialogContainer == null || !(rightSlidingDialogContainer.getFragment() instanceof TopicsFragment)) ? null : (TopicsFragment) DialogsActivity.this.rightSlidingDialogContainer.getFragment();
                if (topicsFragment == null || topicsFragment.getFragmentView() == null || DialogsActivity.this.searching) {
                    return;
                }
                iBlur3Hash.unsupported();
            }
        }

        @Override // org.telegram.ui.Components.blur3.RenderNodeWithHash.Renderer
        public void renderNodeUpdateDisplayList(Canvas canvas) {
            Canvas canvas2;
            BlurredBackgroundSourceRenderNode glassSource;
            int measuredWidth = DialogsActivity.this.fragmentView.getMeasuredWidth();
            int measuredHeight = DialogsActivity.this.fragmentView.getMeasuredHeight();
            canvas.drawColor(DialogsActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
            if (SharedConfig.chatBlurEnabled()) {
                RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                TopicsFragment topicsFragment = (rightSlidingDialogContainer == null || !(rightSlidingDialogContainer.getFragment() instanceof TopicsFragment)) ? null : (TopicsFragment) DialogsActivity.this.rightSlidingDialogContainer.getFragment();
                if (topicsFragment == null || topicsFragment.getFragmentView() == null || DialogsActivity.this.searching || (glassSource = topicsFragment.getGlassSource()) == null) {
                    canvas2 = canvas;
                } else {
                    canvas.save();
                    canvas.translate(topicsFragment.getFragmentView().getTranslationX(), topicsFragment.getFragmentView().getTranslationY());
                    canvas2 = canvas;
                    glassSource.draw(canvas2, 0.0f, 0.0f, measuredWidth, measuredHeight);
                    canvas2.restore();
                }
                DialogsActivity.this.scrollableViewNoiseSuppressor.draw(canvas2, -2);
            }
        }
    }

    public void setMainTabsActivityController(MainTabsActivityController mainTabsActivityController) {
        this.mainTabsActivityController = mainTabsActivityController;
    }

    public boolean openAccountSelector(View view) {
        MainTabsActivityController mainTabsActivityController = this.mainTabsActivityController;
        if (mainTabsActivityController == null || view == null) {
            return false;
        }
        return mainTabsActivityController.openAccountSelector(view, view);
    }

    public void setChildMainTabsVisible(boolean z) {
        MainTabsActivityController mainTabsActivityController = this.mainTabsActivityController;
        if (mainTabsActivityController != null) {
            mainTabsActivityController.setTabsVisible(z);
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        Bundle bundle = this.arguments;
        if (bundle != null) {
            this.onlySelect = bundle.getBoolean("onlySelect", false);
            this.canSelectTopics = this.arguments.getBoolean("canSelectTopics", false);
            this.cantSendToChannels = this.arguments.getBoolean("cantSendToChannels", false);
            this.initialDialogsType = this.arguments.getInt("dialogsType", 0);
            this.isQuote = this.arguments.getBoolean("quote", false);
            this.isReplyTo = this.arguments.getBoolean("reply_to", false);
            this.replyMessageAuthor = this.arguments.getLong("reply_to_author", 0L);
            this.forwardOriginalChannel = this.arguments.getLong("forward_into_channel", 0L);
            this.selectAlertString = this.arguments.getString("selectAlertString");
            this.selectAlertStringGroup = this.arguments.getString("selectAlertStringGroup");
            this.addToGroupAlertString = this.arguments.getString("addToGroupAlertString");
            this.allowSwitchAccount = this.arguments.getBoolean("allowSwitchAccount");
            this.checkCanWrite = this.arguments.getBoolean("checkCanWrite", true);
            this.afterSignup = this.arguments.getBoolean("afterSignup", false);
            this.folderId = this.arguments.getInt("folderId", 0);
            this.resetDelegate = this.arguments.getBoolean("resetDelegate", true);
            this.messagesCount = this.arguments.getInt("messagesCount", 0);
            this.hasPoll = this.arguments.getInt("hasPoll", 0);
            this.hasInvoice = this.arguments.getBoolean("hasInvoice", false);
            this.showSetPasswordConfirm = this.arguments.getBoolean("showSetPasswordConfirm", this.showSetPasswordConfirm);
            this.otherwiseReloginDays = this.arguments.getInt("otherwiseRelogin");
            this.allowGroups = this.arguments.getBoolean("allowGroups", true);
            this.allowMegagroups = this.arguments.getBoolean("allowMegagroups", true);
            this.allowLegacyGroups = this.arguments.getBoolean("allowLegacyGroups", true);
            this.allowChannels = this.arguments.getBoolean("allowChannels", true);
            this.allowUsers = this.arguments.getBoolean("allowUsers", true);
            this.allowBots = this.arguments.getBoolean("allowBots", true);
            this.closeFragment = this.arguments.getBoolean("closeFragment", true);
            this.allowGlobalSearch = this.arguments.getBoolean("allowGlobalSearch", true);
            this.hasMainTabs = this.arguments.getBoolean("hasMainTabs", false);
            byte[] byteArray = this.arguments.getByteArray("requestPeerType");
            if (byteArray != null) {
                try {
                    SerializedData serializedData = new SerializedData(byteArray);
                    this.requestPeerType = TLRPC.RequestPeerType.TLdeserialize(serializedData, serializedData.readInt32(true), true);
                    serializedData.cleanup();
                } catch (Exception unused) {
                }
            }
            this.requestPeerBotId = this.arguments.getLong("requestPeerBotId", 0L);
        }
        if (this.initialDialogsType == 0) {
            this.askAboutContacts = MessagesController.getGlobalNotificationsSettings().getBoolean("askAboutContacts", true);
            SharedConfig.loadProxyList();
        }
        this.observersGroup = getNotificationCenter().createObserversGroup(this);
        this.globalObserversGroup = NotificationCenter.getGlobalInstance().createObserversGroup(this);
        if (this.searchString == null) {
            this.currentConnectionState = getConnectionsManager().getConnectionState();
            this.globalObserversGroup.add(NotificationCenter.emojiLoaded);
            if (!this.onlySelect) {
                this.globalObserversGroup.add(NotificationCenter.closeSearchByActiveAction);
                this.globalObserversGroup.add(NotificationCenter.proxySettingsChanged);
                this.observersGroup.add(NotificationCenter.filterSettingsUpdated);
                this.observersGroup.add(NotificationCenter.dialogsUnreadCounterChanged);
            }
            this.observersGroup.add(NotificationCenter.dialogsNeedReload).add(NotificationCenter.dialogFiltersUpdated).add(NotificationCenter.updateInterfaces).add(NotificationCenter.encryptedChatUpdated).add(NotificationCenter.contactsDidLoad).add(NotificationCenter.appDidLogout).add(NotificationCenter.openedChatChanged).add(NotificationCenter.notificationsSettingsUpdated).add(NotificationCenter.messageReceivedByAck).add(NotificationCenter.messageReceivedByServer).add(NotificationCenter.messageSendError).add(NotificationCenter.needReloadRecentDialogsSearch).add(NotificationCenter.replyMessagesDidLoad).add(NotificationCenter.topicsDidLoaded).add(NotificationCenter.reloadHints).add(NotificationCenter.didUpdateConnectionState).add(NotificationCenter.onDownloadingFilesChanged).add(NotificationCenter.needDeleteDialog).add(NotificationCenter.folderBecomeEmpty).add(NotificationCenter.newSuggestionsAvailable).add(NotificationCenter.dialogsUnreadReactionsCounterChanged).add(NotificationCenter.dialogsUnreadPollVotesCounterChanged).add(NotificationCenter.forceImportContactsStart).add(NotificationCenter.mainUserInfoChanged).add(NotificationCenter.userEmojiStatusUpdated).add(NotificationCenter.currentUserPremiumStatusChanged);
            this.globalObserversGroup.add(NotificationCenter.didSetPasscode);
        }
        this.observersGroup.add(NotificationCenter.messagesDeleted).add(NotificationCenter.onDatabaseMigration).add(NotificationCenter.onDatabaseOpened).add(NotificationCenter.didClearDatabase).add(NotificationCenter.onDatabaseReset).add(NotificationCenter.storiesUpdated).add(NotificationCenter.storiesEnabledUpdate).add(NotificationCenter.unconfirmedAuthUpdate).add(NotificationCenter.premiumPromoUpdated).add(NotificationCenter.starBalanceUpdated).add(NotificationCenter.starSubscriptionsLoaded).add(NotificationCenter.appConfigUpdated).add(NotificationCenter.activeAuctionsUpdated);
        if (this.initialDialogsType == 0) {
            this.observersGroup.add(NotificationCenter.chatlistFolderUpdate);
            this.observersGroup.add(NotificationCenter.dialogTranslate);
        }
        loadDialogs(getAccountInstance());
        getMessagesController().getStoriesController().loadAllStories();
        getMessagesController().loadPinnedDialogs(this.folderId, 0L, null);
        if (this.databaseMigrationHint != null && !getMessagesStorage().isDatabaseMigrationInProgress()) {
            View view = this.databaseMigrationHint;
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            this.databaseMigrationHint = null;
        }
        if (isArchive()) {
            getMessagesController().getStoriesController().loadHiddenStories();
        } else {
            getMessagesController().getStoriesController().loadStories();
        }
        getContactsController().loadGlobalPrivacySetting();
        if (getMessagesController().savedViewAsChats) {
            getMessagesController().getSavedMessagesController().preloadDialogs(true);
        }
        BirthdayController.getInstance(this.currentAccount).check();
        this.additionNavigationBarHeight = MainTabsUiHelper.getAdditionalNavigationBarHeight(this.hasMainTabs);
        this.additionFloatingButtonOffset = MainTabsUiHelper.getTabsFabOffset(this.hasMainTabs);
        return true;
    }

    public static void loadDialogs(final AccountInstance accountInstance) {
        int currentAccount = accountInstance.getCurrentAccount();
        if (dialogsLoaded[currentAccount]) {
            return;
        }
        MessagesController messagesController = accountInstance.getMessagesController();
        messagesController.loadGlobalNotificationsSettings();
        messagesController.loadDialogs(0, 0, 100, true);
        messagesController.loadHintDialogs();
        messagesController.loadUserInfo(accountInstance.getUserConfig().getCurrentUser(), false, 0);
        accountInstance.getContactsController().checkInviteText();
        accountInstance.getMediaDataController().checkAllMedia(false);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                accountInstance.getDownloadController().loadDownloadingFiles();
            }
        }, 200L);
        Iterator<String> it = messagesController.diceEmojies.iterator();
        while (it.hasNext()) {
            accountInstance.getMediaDataController().loadStickersByEmojiOrName(it.next(), true, true);
        }
        dialogsLoaded[currentAccount] = true;
    }

    private boolean isDrawerAccountPreview() {
        Bundle bundle = this.arguments;
        return bundle != null && bundle.getBoolean("drawer_account_preview", false);
    }

    public int getSearchFieldHeight() {
        if (!isSupportSearch()) {
            return 0;
        }
        if (ExteraConfig.getHideDialogsSearchBar()) {
            return (int) (AndroidUtilities.m1036dp(48.0f) * this.animatorSearchVisible.getFloatValue());
        }
        return AndroidUtilities.m1036dp(48.0f);
    }

    private void updatePreviewAccountInfo() {
        TLRPC.User currentUser;
        if (this.avatarContainer == null || (currentUser = getUserConfig().getCurrentUser()) == null) {
            return;
        }
        this.avatarContainer.setTitle(UserObject.getUserName(currentUser));
        this.avatarContainer.setSubtitle(LocaleController.formatUserStatus(this.currentAccount, currentUser));
        this.avatarContainer.setUserAvatar(currentUser, true);
    }

    private void updateDrawerAccountPreviewHeaderLayout() {
        ActionBar actionBar;
        if (!isDrawerAccountPreview() || (actionBar = this.actionBar) == null) {
            return;
        }
        boolean z = !this.inPreviewMode;
        actionBar.setOccupyStatusBar(z);
        ViewGroup.LayoutParams layoutParams = this.actionBar.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            if (marginLayoutParams.topMargin != 0) {
                marginLayoutParams.topMargin = 0;
                this.actionBar.setLayoutParams(marginLayoutParams);
            } else {
                this.actionBar.requestLayout();
            }
        } else {
            this.actionBar.requestLayout();
        }
        ChatAvatarContainer chatAvatarContainer = this.avatarContainer;
        if (chatAvatarContainer != null) {
            chatAvatarContainer.setOccupyStatusBar(z);
            this.avatarContainer.requestLayout();
        }
    }

    private void applyActionBarTitleStyle() {
        ActionBar actionBar = this.actionBar;
        if (actionBar == null) {
            return;
        }
        actionBar.getTitlesContainer().setTranslationX(this.hasMainTabs ? AndroidUtilities.m1036dp(4.0f) : 0.0f);
        if (this.hasMainTabs) {
            this.actionBar.setTitleColor(getThemedColor(Theme.key_telegram_color_dialogsLogo));
            return;
        }
        int i = this.folderId;
        ActionBar actionBar2 = this.actionBar;
        if (i != 0) {
            actionBar2.setTitleColor(getThemedColor(Theme.key_actionBarDefaultArchivedTitle));
        } else {
            actionBar2.setTitleColor(getThemedColor(Theme.key_actionBarDefaultTitle));
        }
    }

    public void updateStatus(TLRPC.User user, boolean z) {
        DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
        if (dialogStoriesCell != null) {
            dialogStoriesCell.updateStatus(user, z);
        }
        if (this.statusDrawable == null || this.actionBar == null) {
            return;
        }
        Long emojiStatusDocumentId = UserObject.getEmojiStatusDocumentId(user);
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = null;
        this.statusDrawableGiftId = null;
        if (emojiStatusDocumentId != null) {
            boolean z2 = user.emoji_status instanceof TLRPC.TL_emojiStatusCollectible;
            this.statusDrawable.set(emojiStatusDocumentId.longValue(), z);
            this.statusDrawable.setParticles(z2, z);
            if (z2) {
                this.statusDrawableGiftId = Long.valueOf(((TLRPC.TL_emojiStatusCollectible) user.emoji_status).collectible_id);
            }
            this.actionBar.setRightDrawableOnClick(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda107
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$updateStatus$2(view);
                }
            });
            SelectAnimatedEmojiDialog.preload(this.currentAccount);
        } else if (user != null && MessagesController.getInstance(this.currentAccount).isPremiumUser(user)) {
            if (this.premiumStar == null) {
                this.premiumStar = getContext().getResources().getDrawable(C2797R.drawable.msg_premium_liststar).mutate();
                this.premiumStar = new AnimatedEmojiDrawable.WrapSizeDrawable(this.premiumStar, AndroidUtilities.m1036dp(18.0f), AndroidUtilities.m1036dp(18.0f)) { // from class: org.telegram.ui.DialogsActivity.5
                    public C55655(Drawable drawable, int i, int i2) {
                        super(drawable, i, i2);
                    }

                    @Override // org.telegram.ui.Components.AnimatedEmojiDrawable.WrapSizeDrawable, android.graphics.drawable.Drawable
                    public void draw(Canvas canvas) {
                        canvas.save();
                        canvas.translate(AndroidUtilities.m1036dp(-2.0f), AndroidUtilities.m1036dp(1.0f));
                        super.draw(canvas);
                        canvas.restore();
                    }
                };
            }
            this.premiumStar.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_profile_verifiedBackground), PorterDuff.Mode.MULTIPLY));
            this.statusDrawable.set(this.premiumStar, z);
            this.statusDrawable.setParticles(false, z);
            this.actionBar.setRightDrawableOnClick(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda108
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$updateStatus$3(view);
                }
            });
            SelectAnimatedEmojiDialog.preload(this.currentAccount);
        } else {
            this.statusDrawable.set((Drawable) null, z);
            this.statusDrawable.setParticles(false, z);
            this.actionBar.setRightDrawableOnClick(null);
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable2 = this.statusDrawable;
        int i = Theme.key_profile_verifiedBackground;
        swapAnimatedEmojiDrawable2.setColor(Integer.valueOf(getThemedColor(i)));
        AnimatedStatusView animatedStatusView = this.animatedStatusView;
        if (animatedStatusView != null) {
            animatedStatusView.setColor(getThemedColor(i));
        }
        SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialogWindow = this.selectAnimatedEmojiDialog;
        if (selectAnimatedEmojiDialogWindow == null || !(selectAnimatedEmojiDialogWindow.getContentView() instanceof SelectAnimatedEmojiDialog)) {
            return;
        }
        SimpleTextView titleTextView = this.actionBar.getTitleTextView();
        SelectAnimatedEmojiDialog selectAnimatedEmojiDialog = (SelectAnimatedEmojiDialog) this.selectAnimatedEmojiDialog.getContentView();
        if (titleTextView != null) {
            Drawable rightDrawable = titleTextView.getRightDrawable();
            AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable3 = this.statusDrawable;
            if (rightDrawable == swapAnimatedEmojiDrawable3) {
                swapAnimatedEmojiDrawable = swapAnimatedEmojiDrawable3;
            }
        }
        selectAnimatedEmojiDialog.setScrimDrawable(swapAnimatedEmojiDrawable, titleTextView);
    }

    public /* synthetic */ void lambda$updateStatus$2(View view) {
        DialogStoriesCell dialogStoriesCell;
        if (this.dialogStoriesCellVisible && (dialogStoriesCell = this.dialogStoriesCell) != null && !dialogStoriesCell.isExpanded()) {
            scrollToTop(true, true);
        } else {
            showSelectStatusDialog();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$5 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C55655 extends AnimatedEmojiDrawable.WrapSizeDrawable {
        public C55655(Drawable drawable, int i, int i2) {
            super(drawable, i, i2);
        }

        @Override // org.telegram.ui.Components.AnimatedEmojiDrawable.WrapSizeDrawable, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.translate(AndroidUtilities.m1036dp(-2.0f), AndroidUtilities.m1036dp(1.0f));
            super.draw(canvas);
            canvas.restore();
        }
    }

    public /* synthetic */ void lambda$updateStatus$3(View view) {
        DialogStoriesCell dialogStoriesCell;
        if (this.dialogStoriesCellVisible && (dialogStoriesCell = this.dialogStoriesCell) != null && !dialogStoriesCell.isExpanded()) {
            scrollToTop(true, true);
        } else {
            showSelectStatusDialog();
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        ViewPositionWatcher viewPositionWatcher = this.viewPositionWatcher;
        if (viewPositionWatcher != null) {
            viewPositionWatcher.shutdown();
            this.viewPositionWatcher = null;
        }
        super.onFragmentDestroy();
        NotificationCenter.ObserversGroup observersGroup = this.observersGroup;
        if (observersGroup != null) {
            observersGroup.removeAllObservers();
            this.observersGroup = null;
        }
        NotificationCenter.ObserversGroup observersGroup2 = this.globalObserversGroup;
        if (observersGroup2 != null) {
            observersGroup2.removeAllObservers();
            this.globalObserversGroup = null;
        }
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        if (chatActivityEnterView != null) {
            chatActivityEnterView.onDestroy();
        }
        ShareTopView shareTopView = this.shareTopView;
        if (shareTopView != null) {
            shareTopView.stopHintRotation();
        }
        Runnable runnable = this.shareLinkSearchRunnable;
        if (runnable != null) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
            this.shareLinkSearchRunnable = null;
        }
        UndoView undoView = this.undoView[0];
        if (undoView != null) {
            undoView.hide(true, 0);
        }
        this.notificationsLocker.unlock();
        this.delegate = null;
        Bulletin.removeDelegate(this);
        SuggestClearDatabaseBottomSheet.dismissDialog();
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public boolean dismissDialogOnPause(Dialog dialog) {
        return !(dialog instanceof BotWebViewSheet) && super.dismissDialogOnPause(dialog);
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$6 */
    public class C55706 extends ActionBar {
        public C55706(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context, resourcesProvider);
        }

        @Override // org.telegram.p035ui.ActionBar.ActionBar, android.view.View
        public void setTranslationY(float f) {
            View view;
            if (f != getTranslationY() && (view = DialogsActivity.this.fragmentView) != null) {
                view.invalidate();
            }
            super.setTranslationY(f);
        }

        @Override // org.telegram.p035ui.ActionBar.ActionBar
        public boolean shouldClipChild(View view) {
            return super.shouldClipChild(view) || view == DialogsActivity.this.doneItem;
        }

        @Override // org.telegram.p035ui.ActionBar.ActionBar, android.view.ViewGroup
        public boolean drawChild(Canvas canvas, View view, long j) {
            if (!((BaseFragment) DialogsActivity.this).inPreviewMode || DialogsActivity.this.avatarContainer == null || view == DialogsActivity.this.avatarContainer) {
                return super.drawChild(canvas, view, j);
            }
            return false;
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            if (DialogsActivity.this.fragmentSearchField == null || DialogsActivity.this.fragmentSearchField.getAlpha() <= 0.0f || !DialogsActivity.this.animatorSearchVisible.getValue()) {
                return super.dispatchTouchEvent(motionEvent);
            }
            return false;
        }

        @Override // org.telegram.p035ui.ActionBar.ActionBar
        public void closeSearchField(boolean z) {
            DialogsActivity.this.fragmentSearchField.editText.getText().clear();
            if (z && DialogsActivity.this.fragmentSearchField.editText.isFocused()) {
                AndroidUtilities.hideKeyboard(DialogsActivity.this.fragmentSearchField.editText);
            }
            DialogsActivity.this.fragmentSearchField.editText.clearFocus();
            DialogsActivity.this.fragmentSearchFieldWatcher.toggleSearch(false);
        }

        @Override // org.telegram.p035ui.ActionBar.ActionBar
        public boolean onSearchChangedIgnoreTitles() {
            RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
            return rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment();
        }

        @Override // org.telegram.p035ui.ActionBar.ActionBar
        public void onSearchFieldVisibilityChanged(boolean z) {
            RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
            if (rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment() && getBackButton() != null) {
                getBackButton().animate().alpha(z ? 1.0f : 0.0f).start();
            }
            super.onSearchFieldVisibilityChanged(z);
        }

        @Override // org.telegram.p035ui.ActionBar.ActionBar
        public void showActionMode(boolean z, View view, View view2, View[] viewArr, boolean[] zArr, View view3, int i) {
            super.showActionMode(z, view, view2, viewArr, zArr, view3, i);
            DialogsActivity.this.animatorActionModeVisible.setValue(true, z);
        }

        @Override // org.telegram.p035ui.ActionBar.ActionBar
        public void hideActionMode() {
            super.hideActionMode();
            DialogsActivity.this.animatorActionModeVisible.setValue(false, true);
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public ActionBar createActionBar(Context context) {
        C55706 c55706 = new ActionBar(context, this.resourceProvider) { // from class: org.telegram.ui.DialogsActivity.6
            public C55706(Context context2, Theme.ResourcesProvider resourcesProvider) {
                super(context2, resourcesProvider);
            }

            @Override // org.telegram.p035ui.ActionBar.ActionBar, android.view.View
            public void setTranslationY(float f) {
                View view;
                if (f != getTranslationY() && (view = DialogsActivity.this.fragmentView) != null) {
                    view.invalidate();
                }
                super.setTranslationY(f);
            }

            @Override // org.telegram.p035ui.ActionBar.ActionBar
            public boolean shouldClipChild(View view) {
                return super.shouldClipChild(view) || view == DialogsActivity.this.doneItem;
            }

            @Override // org.telegram.p035ui.ActionBar.ActionBar, android.view.ViewGroup
            public boolean drawChild(Canvas canvas, View view, long j) {
                if (!((BaseFragment) DialogsActivity.this).inPreviewMode || DialogsActivity.this.avatarContainer == null || view == DialogsActivity.this.avatarContainer) {
                    return super.drawChild(canvas, view, j);
                }
                return false;
            }

            @Override // android.view.ViewGroup, android.view.View
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                if (DialogsActivity.this.fragmentSearchField == null || DialogsActivity.this.fragmentSearchField.getAlpha() <= 0.0f || !DialogsActivity.this.animatorSearchVisible.getValue()) {
                    return super.dispatchTouchEvent(motionEvent);
                }
                return false;
            }

            @Override // org.telegram.p035ui.ActionBar.ActionBar
            public void closeSearchField(boolean z) {
                DialogsActivity.this.fragmentSearchField.editText.getText().clear();
                if (z && DialogsActivity.this.fragmentSearchField.editText.isFocused()) {
                    AndroidUtilities.hideKeyboard(DialogsActivity.this.fragmentSearchField.editText);
                }
                DialogsActivity.this.fragmentSearchField.editText.clearFocus();
                DialogsActivity.this.fragmentSearchFieldWatcher.toggleSearch(false);
            }

            @Override // org.telegram.p035ui.ActionBar.ActionBar
            public boolean onSearchChangedIgnoreTitles() {
                RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                return rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment();
            }

            @Override // org.telegram.p035ui.ActionBar.ActionBar
            public void onSearchFieldVisibilityChanged(boolean z) {
                RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                if (rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment() && getBackButton() != null) {
                    getBackButton().animate().alpha(z ? 1.0f : 0.0f).start();
                }
                super.onSearchFieldVisibilityChanged(z);
            }

            @Override // org.telegram.p035ui.ActionBar.ActionBar
            public void showActionMode(boolean z, View view, View view2, View[] viewArr, boolean[] zArr, View view3, int i) {
                super.showActionMode(z, view, view2, viewArr, zArr, view3, i);
                DialogsActivity.this.animatorActionModeVisible.setValue(true, z);
            }

            @Override // org.telegram.p035ui.ActionBar.ActionBar
            public void hideActionMode() {
                super.hideActionMode();
                DialogsActivity.this.animatorActionModeVisible.setValue(false, true);
            }
        };
        c55706.setAllowOverlayTitle(true);
        c55706.setUseContainerForTitles();
        c55706.setItemsBackgroundColor(getThemedColor(Theme.key_actionBarDefaultSelector), false);
        c55706.setItemsBackgroundColor(getThemedColor(Theme.key_actionBarActionModeDefaultSelector), true);
        c55706.setItemsColor(getThemedColor(Theme.key_actionBarDefaultIcon), false);
        c55706.setItemsColor(getThemedColor(Theme.key_actionBarActionModeDefaultIcon), true);
        c55706.createAdditionalSubTitleOverlayContainer();
        c55706.getAdditionalSubTitleOverlayContainer().setTranslationX(AndroidUtilities.m1036dp(4.0f));
        c55706.getAdditionalSubTitleOverlayContainer().setTranslationY(-AndroidUtilities.m1036dp(3.0f));
        if (!this.inPreviewMode && (!AndroidUtilities.isTablet() || this.folderId == 0 || isArchive())) {
            return c55706;
        }
        c55706.setOccupyStatusBar(false);
        return c55706;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0037  */
    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setTitleOverlayText(java.lang.String r4, int r5, java.lang.Runnable r6) {
        /*
            r3 = this;
            org.telegram.ui.RightSlidingDialogContainer r0 = r3.rightSlidingDialogContainer
            if (r0 == 0) goto Lb
            boolean r0 = r0.hasFragment()
            if (r0 == 0) goto Lb
            goto Le
        Lb:
            super.setTitleOverlayText(r4, r5, r6)
        Le:
            org.telegram.ui.ActionBar.ActionBar r6 = r3.actionBar
            if (r6 == 0) goto L3b
            org.telegram.ui.SelectAnimatedEmojiDialog$SelectAnimatedEmojiDialogWindow r6 = r3.selectAnimatedEmojiDialog
            if (r6 == 0) goto L3b
            android.view.View r6 = r6.getContentView()
            boolean r6 = r6 instanceof org.telegram.p035ui.SelectAnimatedEmojiDialog
            if (r6 == 0) goto L3b
            org.telegram.ui.ActionBar.ActionBar r6 = r3.actionBar
            org.telegram.ui.ActionBar.SimpleTextView r6 = r6.getTitleTextView()
            org.telegram.ui.SelectAnimatedEmojiDialog$SelectAnimatedEmojiDialogWindow r0 = r3.selectAnimatedEmojiDialog
            android.view.View r0 = r0.getContentView()
            org.telegram.ui.SelectAnimatedEmojiDialog r0 = (org.telegram.p035ui.SelectAnimatedEmojiDialog) r0
            if (r6 == 0) goto L37
            android.graphics.drawable.Drawable r1 = r6.getRightDrawable()
            org.telegram.ui.Components.AnimatedEmojiDrawable$SwapAnimatedEmojiDrawable r2 = r3.statusDrawable
            if (r1 != r2) goto L37
            goto L38
        L37:
            r2 = 0
        L38:
            r0.setScrimDrawable(r2, r6)
        L3b:
            org.telegram.ui.Stories.DialogStoriesCell r3 = r3.dialogStoriesCell
            if (r3 == 0) goto L42
            r3.setTitleOverlayText(r4, r5)
        L42:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.setTitleOverlayText(java.lang.String, int, java.lang.Runnable):void");
    }

    public static void setFirstCreate(boolean z) {
        isFirstLoading = z;
    }

    public /* synthetic */ void lambda$new$4(int i, float f, float f2, FactorAnimator factorAnimator) {
        applyBottomTabsOffset();
    }

    public int getCurrentBottomTabsOffset() {
        return Math.round(this.additionFloatingButtonOffset * this.animatorBottomTabsOffset.getFloatValue());
    }

    public int getCurrentUndoViewOffset() {
        return Math.round(((this.hasMainTabs && BottomNavigationBar.visible()) ? AndroidUtilities.m1036dp(MainTabsUiHelper.getTabsViewHeightDp()) : 0) * this.animatorBottomTabsOffset.getFloatValue());
    }

    private void applyBottomTabsOffset() {
        for (UndoView undoView : this.undoView) {
            if (undoView != null) {
                int currentUndoViewOffset = this.navigationBarHeight + getCurrentUndoViewOffset();
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) undoView.getLayoutParams();
                if (marginLayoutParams != null && marginLayoutParams.bottomMargin != currentUndoViewOffset) {
                    marginLayoutParams.bottomMargin = currentUndoViewOffset;
                    undoView.setLayoutParams(marginLayoutParams);
                }
            }
        }
        updateFloatingButtonOffset();
        Bulletin visibleBulletin = Bulletin.getVisibleBulletin();
        if (visibleBulletin != null) {
            visibleBulletin.updatePosition();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:509:0x0bbd  */
    /* JADX WARN: Removed duplicated region for block: B:512:0x0c06  */
    /* JADX WARN: Removed duplicated region for block: B:520:0x0c65  */
    /* JADX WARN: Removed duplicated region for block: B:523:0x0c7d  */
    /* JADX WARN: Removed duplicated region for block: B:526:0x0ca8  */
    /* JADX WARN: Removed duplicated region for block: B:534:0x0d33  */
    /* JADX WARN: Removed duplicated region for block: B:542:0x0d9a  */
    /* JADX WARN: Removed duplicated region for block: B:543:0x0db4  */
    /* JADX WARN: Removed duplicated region for block: B:551:0x0df7  */
    /* JADX WARN: Removed duplicated region for block: B:554:0x0e2e  */
    /* JADX WARN: Type inference failed for: r0v153, types: [org.telegram.ui.Components.RecyclerListView, org.telegram.ui.DialogsActivity$DialogsRecyclerView] */
    /* JADX WARN: Type inference failed for: r0v69, types: [org.telegram.ui.ActionBar.ActionBar] */
    /* JADX WARN: Type inference failed for: r11v0 */
    /* JADX WARN: Type inference failed for: r11v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r2v16, types: [android.widget.EditText, org.telegram.ui.Components.EditTextBoldCursor] */
    /* JADX WARN: Type inference failed for: r2v24, types: [org.telegram.ui.ActionBar.ActionBar] */
    /* JADX WARN: Type inference failed for: r2v259, types: [android.view.ViewGroup, org.telegram.ui.ActionBar.ActionBarMenuItem] */
    /* JADX WARN: Type inference failed for: r2v32, types: [org.telegram.ui.ActionBar.ActionBar] */
    /* JADX WARN: Type inference failed for: r2v49, types: [org.telegram.ui.Components.blur3.BlurredBackgroundDrawableViewFactory] */
    /* JADX WARN: Type inference failed for: r2v50, types: [org.telegram.ui.Components.blur3.BlurredBackgroundDrawableViewFactory] */
    /* JADX WARN: Type inference failed for: r2v51, types: [org.telegram.ui.Components.blur3.BlurredBackgroundDrawableViewFactory] */
    /* JADX WARN: Type inference failed for: r4v55, types: [org.telegram.ui.Components.RecyclerListView, org.telegram.ui.DialogsActivity$DialogsRecyclerView] */
    /* JADX WARN: Type inference failed for: r4v56, types: [org.telegram.ui.Components.RecyclerListView, org.telegram.ui.DialogsActivity$DialogsRecyclerView] */
    /* JADX WARN: Type inference failed for: r4v57, types: [androidx.recyclerview.widget.RecyclerView, org.telegram.ui.DialogsActivity$DialogsRecyclerView] */
    /* JADX WARN: Type inference failed for: r4v76 */
    /* JADX WARN: Type inference failed for: r4v77, types: [int] */
    /* JADX WARN: Type inference failed for: r4v86, types: [org.telegram.ui.Components.PullForegroundDrawable] */
    /* JADX WARN: Type inference failed for: r4v88 */
    /* JADX WARN: Type inference failed for: r5v70 */
    /* JADX WARN: Type inference failed for: r5v71, types: [boolean] */
    /* JADX WARN: Type inference failed for: r5v72 */
    /* JADX WARN: Type inference failed for: r6v6, types: [org.telegram.ui.Components.blur3.BlurredBackgroundDrawableViewFactory] */
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
    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View createView(android.content.Context r37) {
        /*
            Method dump skipped, instruction units count: 3688
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.createView(android.content.Context):android.view.View");
    }

    public /* synthetic */ void lambda$createView$7(View view) {
        showSearch(true, false, true);
        this.fragmentSearchFieldWatcher.toggleSearch(true);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda129
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$6();
            }
        }, 100L);
    }

    public /* synthetic */ void lambda$createView$6() {
        this.fragmentSearchField.editText.requestFocus();
        AndroidUtilities.showKeyboard(this.fragmentSearchField.editText);
    }

    public /* synthetic */ void lambda$createView$8(View view) {
        this.filterTabsView.setIsEditing(false);
        showDoneItem(false);
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$7 */
    public class C55717 extends FragmentSearchField {
        public C55717(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context, resourcesProvider);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0 && getAlpha() <= 0.01f) {
                return false;
            }
            if (motionEvent.getAction() == 0 && DialogsActivity.this.shouldPassSearchFieldTouchToActionBar(motionEvent)) {
                return false;
            }
            return super.dispatchTouchEvent(motionEvent);
        }
    }

    public /* synthetic */ void lambda$createView$10() {
        SearchViewPager searchViewPager = this.searchViewPager;
        if (searchViewPager != null && searchViewPager.actionModeShowing()) {
            this.searchViewPager.hideActionMode();
            return;
        }
        int length = this.fragmentSearchField.editText.length();
        FragmentSearchField fragmentSearchField = this.fragmentSearchField;
        if (length > 0) {
            fragmentSearchField.editText.getText().clear();
            return;
        }
        AndroidUtilities.hideKeyboard(fragmentSearchField.editText);
        this.fragmentSearchField.editText.clearFocus();
        this.fragmentSearchFieldWatcher.toggleSearch(false);
    }

    public /* synthetic */ void lambda$createView$11(View view, boolean z) {
        if (z) {
            this.fragmentSearchFieldWatcher.toggleSearch(true);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$8 */
    public class C55728 extends ActionBarMenuItem.ActionBarMenuItemSearchListener {
        public C55728() {
        }

        @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
        public void onSearchExpand() {
            DialogsActivity.this.searching = true;
            if (DialogsActivity.this.switchItem != null) {
                DialogsActivity.this.switchItem.setVisibility(8);
            }
            DialogsActivity.this.createSearchViewPager();
            if (DialogsActivity.this.viewPages[0] != null) {
                if (DialogsActivity.this.searchString != null) {
                    DialogsActivity.this.viewPages[0].listView.hide();
                    if (DialogsActivity.this.searchViewPager != null) {
                        DialogsActivity.this.searchViewPager.searchListView.show();
                    }
                }
                if (!DialogsActivity.this.onlySelect) {
                    if (DialogsActivity.this.storyHint != null) {
                        DialogsActivity.this.storyHint.hide();
                    }
                    if (DialogsActivity.this.storyPremiumHint != null) {
                        DialogsActivity.this.storyPremiumHint.hide();
                    }
                }
            }
            DialogStoriesCell dialogStoriesCell = DialogsActivity.this.dialogStoriesCell;
            if (dialogStoriesCell != null && dialogStoriesCell.getPremiumHint() != null) {
                DialogsActivity.this.dialogStoriesCell.getPremiumHint().hide();
            }
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (!dialogsActivity.hasStories) {
                dialogsActivity.setScrollY(0.0f);
            }
            DialogsActivity.this.updateProxyButton(false, false);
            ((BaseFragment) DialogsActivity.this).actionBar.setBackButtonContentDescription(LocaleController.getString(ExteraConfig.getNavigationDrawer() ? C2797R.string.AccDescrOpenMenu : C2797R.string.AccDescrGoBack));
            NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.needCheckSystemBarColors, new Object[0]);
            DialogsActivity.this.blur3_InvalidateBlur();
            if (DialogsActivity.this.searchViewPager != null) {
                DialogsActivity.this.searchViewPager.onShown();
            }
            if ((DialogsActivity.this.searchViewPager != null && DialogsActivity.this.searchViewPager.dialogsSearchAdapter != null && DialogsActivity.this.searchViewPager.dialogsSearchAdapter.hasRecentSearch()) || DialogsActivity.this.getMessagesController().getTotalDialogsCount() > 10 || DialogsActivity.this.searchFiltersWasShowed || DialogsActivity.this.hasStories) {
                DialogsActivity.this.searchWas = true;
                if (!DialogsActivity.this.searchIsShowed) {
                    DialogsActivity.this.showSearch(true, false, true);
                }
            }
            DialogsActivity.this.fragmentSearchField.setCloseButtonVisible(true);
            DialogsActivity.this.updateFloatingButtonVisibility(true);
            DialogsActivity.this.checkUi_mainTabsVisible();
        }

        @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
        public boolean canCollapseSearch() {
            if (DialogsActivity.this.switchItem != null) {
                DialogsActivity.this.switchItem.setVisibility(0);
            }
            if (DialogsActivity.this.searchString == null) {
                return true;
            }
            DialogsActivity.this.finishFragment();
            return false;
        }

        @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
        public void onSearchCollapse() {
            if (DialogsActivity.this.fragmentSearchField != null) {
                DialogsActivity.this.fragmentSearchField.clearSearchFiltersWithCallback();
            }
            DialogsActivity.this.searching = false;
            DialogsActivity.this.searchWas = false;
            if (DialogsActivity.this.viewPages[0] != null) {
                DialogsActivity.this.viewPages[0].listView.setEmptyView(DialogsActivity.this.folderId == 0 ? DialogsActivity.this.viewPages[0].progressView : null);
                DialogsActivity.this.showSearch(false, false, true);
            }
            DialogsActivity.this.updateFilterTabs(false, true);
            DialogsActivity.this.updateProxyButton(false, false);
            NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.needCheckSystemBarColors, Boolean.TRUE);
            DialogsActivity.this.fragmentSearchField.setCloseButtonVisible(false);
            DialogsActivity.this.updateFloatingButtonVisibility(true);
            DialogsActivity.this.checkUi_mainTabsVisible();
            DialogsActivity.this.blur3_InvalidateBlur();
        }

        @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
        public void onTextChanged(EditText editText) {
            String string = editText.getText().toString();
            if (!string.isEmpty() || ((DialogsActivity.this.searchViewPager != null && DialogsActivity.this.searchViewPager.dialogsSearchAdapter != null && DialogsActivity.this.searchViewPager.dialogsSearchAdapter.hasRecentSearch()) || DialogsActivity.this.searchFiltersWasShowed || DialogsActivity.this.hasStories)) {
                DialogsActivity.this.searchWas = true;
                if (!DialogsActivity.this.searchIsShowed) {
                    DialogsActivity.this.showSearch(true, false, true);
                }
            }
            if (DialogsActivity.this.searchViewPager != null) {
                DialogsActivity.this.searchViewPager.onTextChanged(string);
            }
        }

        @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
        public boolean canToggleSearch() {
            return !((BaseFragment) DialogsActivity.this).actionBar.isActionModeShowed() && DialogsActivity.this.databaseMigrationHint == null;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$9 */
    public class C55739 implements FragmentSearchField.SearchFiltersListener {
        public C55739() {
        }

        @Override // org.telegram.ui.Components.FragmentSearchField.SearchFiltersListener
        public void onSearchFilterCleared(FiltersView.MediaFilterData mediaFilterData) {
            if (DialogsActivity.this.searchIsShowed) {
                if (DialogsActivity.this.searchViewPager != null) {
                    DialogsActivity.this.searchViewPager.removeSearchFilter(mediaFilterData);
                    DialogsActivity.this.searchViewPager.onTextChanged(DialogsActivity.this.searchItem.getSearchField().getText().toString());
                }
                DialogsActivity.this.updateFiltersView(true, null, null, false, true);
                DialogsActivity.this.fragmentSearchFieldWatcher.listener.onTextChanged(DialogsActivity.this.fragmentSearchField.editText);
            }
        }

        @Override // org.telegram.ui.Components.FragmentSearchField.SearchFiltersListener
        public void hideActionMode() {
            if (DialogsActivity.this.searchViewPager != null) {
                DialogsActivity.this.searchViewPager.hideActionMode();
            }
        }
    }

    public /* synthetic */ void lambda$createView$12(View view) {
        getContactsController().loadGlobalPrivacySetting();
        showItemOptions();
    }

    public /* synthetic */ boolean lambda$createView$13(View view) {
        if (this.mainTabsActivityController == null) {
            getContactsController().loadGlobalPrivacySetting();
            showItemOptions();
            return true;
        }
        ActionBarMenuItem actionBarMenuItem = this.optionsItem;
        return this.mainTabsActivityController.openAccountSelector((actionBarMenuItem == null || actionBarMenuItem.getIconView() == null) ? view : this.optionsItem.getIconView(), view);
    }

    public /* synthetic */ void lambda$createView$14() {
        if (this.initialDialogsType != 10) {
            hideFloatingButton(false);
        }
        if (this.hasOnlySlefStories && getStoriesController().hasOnlySelfStories()) {
            this.dialogStoriesCell.openSelfStories();
        } else {
            scrollToTop(true, true);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$10 */
    public class C552210 extends FilterTabsView {
        public C552210(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context, resourcesProvider);
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            getParent().requestDisallowInterceptTouchEvent(true);
            DialogsActivity.this.maybeStartTracking = false;
            return super.onInterceptTouchEvent(motionEvent);
        }

        @Override // org.telegram.p035ui.Components.FilterTabsView
        public void onDefaultTabMoved() {
            if (DialogsActivity.this.getMessagesController().premiumFeaturesBlocked()) {
                return;
            }
            try {
                performHapticFeedback(VibratorUtils.getType(3), 1);
            } catch (Exception unused) {
            }
            DialogsActivity dialogsActivity = DialogsActivity.this;
            dialogsActivity.topBulletin = BulletinFactory.m1143of(dialogsActivity).createSimpleBulletin(C2797R.raw.filter_reorder, AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.LimitReachedReorderFolder, LocaleController.getString(C2797R.string.FilterAllChats))), LocaleController.getString(C2797R.string.PremiumMore), 5000, new Runnable() { // from class: org.telegram.ui.DialogsActivity$10$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDefaultTabMoved$0();
                }
            }).show(true);
        }

        public /* synthetic */ void lambda$onDefaultTabMoved$0() {
            DialogsActivity.this.showDialog(new PremiumFeatureBottomSheet(DialogsActivity.this, 9, true));
            DialogsActivity.this.filterTabsView.setIsEditing(false);
            DialogsActivity.this.showDoneItem(false);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$11 */
    public class C552311 implements FilterTabsView.FilterTabsViewDelegate {
        private TabIconsMode lastTitleType = ExteraConfig.getTabIcons();
        final /* synthetic */ Context val$context;

        public C552311(Context context) {
            this.val$context = context;
        }

        /* JADX INFO: renamed from: showDeleteAlert */
        public void lambda$didSelectTab$6(final MessagesController.DialogFilter dialogFilter) {
            boolean zIsChatlist = dialogFilter.isChatlist();
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (zIsChatlist) {
                FolderBottomSheet.showForDeletion(dialogsActivity, dialogFilter.f1156id, null);
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(dialogsActivity.getParentActivity());
            builder.setTitle(LocaleController.getString(C2797R.string.FilterDelete));
            builder.setMessage(LocaleController.getString(C2797R.string.FilterDeleteAlert));
            builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
            builder.setPositiveButton(LocaleController.getString(C2797R.string.Delete), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$11$$ExternalSyntheticLambda0
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    this.f$0.lambda$showDeleteAlert$0(dialogFilter, alertDialog, i);
                }
            });
            AlertDialog alertDialogCreate = builder.create();
            DialogsActivity.this.showDialog(alertDialogCreate);
            TextView textView = (TextView) alertDialogCreate.getButton(-1);
            if (textView != null) {
                textView.setTextColor(DialogsActivity.this.getThemedColor(Theme.key_text_RedBold));
            }
        }

        public /* synthetic */ void lambda$showDeleteAlert$0(MessagesController.DialogFilter dialogFilter, AlertDialog alertDialog, int i) {
            TLRPC.TL_messages_updateDialogFilter tL_messages_updateDialogFilter = new TLRPC.TL_messages_updateDialogFilter();
            tL_messages_updateDialogFilter.f1381id = dialogFilter.f1156id;
            DialogsActivity.this.getConnectionsManager().sendRequest(tL_messages_updateDialogFilter, null);
            DialogsActivity.this.getMessagesController().removeFilter(dialogFilter);
            DialogsActivity.this.getMessagesStorage().deleteDialogFilter(dialogFilter);
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public void onSamePageSelected() {
            DialogsActivity.this.scrollToTop(true, false);
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public void onPageReorder(int i, int i2) {
            for (ViewPage viewPage : DialogsActivity.this.viewPages) {
                if (viewPage.selectedType == i) {
                    viewPage.selectedType = i2;
                } else if (viewPage.selectedType == i2) {
                    viewPage.selectedType = i;
                }
            }
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public void onPageSelected(FilterTabsView.Tab tab, boolean z) {
            int i;
            if (DialogsActivity.this.viewPages[0].selectedType == tab.f1563id) {
                return;
            }
            boolean z2 = tab.isLocked;
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (z2) {
                dialogsActivity.filterTabsView.shakeLock(tab.f1563id);
                DialogsActivity dialogsActivity2 = DialogsActivity.this;
                DialogsActivity dialogsActivity3 = DialogsActivity.this;
                dialogsActivity2.showDialog(new LimitReachedBottomSheet(dialogsActivity3, this.val$context, 3, ((BaseFragment) dialogsActivity3).currentAccount, null));
                return;
            }
            ArrayList<MessagesController.DialogFilter> dialogFilters = dialogsActivity.getMessagesController().getDialogFilters();
            if (tab.isDefault || ((i = tab.f1563id) >= 0 && i < dialogFilters.size())) {
                DialogsActivity.this.viewPages[1].selectedType = tab.f1563id;
                DialogsActivity.this.viewPages[1].setVisibility(0);
                DialogsActivity.this.viewPages[1].setTranslationX(DialogsActivity.this.viewPages[0].getMeasuredWidth());
                DialogsActivity.this.showScrollbars(false);
                DialogsActivity.this.switchToCurrentSelectedMode(true);
                DialogsActivity.this.animatingForward = z;
            }
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public boolean canPerformActions() {
            return !DialogsActivity.this.searching;
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public void onPageScrolled(float f) {
            if (f != 1.0f || DialogsActivity.this.viewPages[1].getVisibility() == 0 || DialogsActivity.this.searching) {
                boolean z = DialogsActivity.this.animatingForward;
                DialogsActivity dialogsActivity = DialogsActivity.this;
                if (z) {
                    dialogsActivity.viewPages[0].setTranslationX((-f) * DialogsActivity.this.viewPages[0].getMeasuredWidth());
                    DialogsActivity.this.viewPages[1].setTranslationX(DialogsActivity.this.viewPages[0].getMeasuredWidth() - (f * DialogsActivity.this.viewPages[0].getMeasuredWidth()));
                } else {
                    dialogsActivity.viewPages[0].setTranslationX(DialogsActivity.this.viewPages[0].getMeasuredWidth() * f);
                    DialogsActivity.this.viewPages[1].setTranslationX((f * DialogsActivity.this.viewPages[0].getMeasuredWidth()) - DialogsActivity.this.viewPages[0].getMeasuredWidth());
                }
                if (f == 1.0f) {
                    ViewPage viewPage = DialogsActivity.this.viewPages[0];
                    DialogsActivity.this.viewPages[0] = DialogsActivity.this.viewPages[1];
                    DialogsActivity.this.viewPages[1] = viewPage;
                    DialogsActivity.this.viewPages[1].setVisibility(8);
                    DialogsActivity.this.showScrollbars(true);
                    DialogsActivity.this.updateCounters(false);
                    DialogsActivity.this.filterTabsView.stopAnimatingIndicator();
                    DialogsActivity dialogsActivity2 = DialogsActivity.this;
                    dialogsActivity2.checkListLoad(dialogsActivity2.viewPages[0]);
                    DialogsActivity.this.viewPages[0].dialogsAdapter.resume();
                    DialogsActivity.this.viewPages[1].dialogsAdapter.pause();
                    DialogsActivity.this.updateFloatingButtonVisibility(true);
                }
            }
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public int getTabCounter(int i) {
            if (DialogsActivity.this.initialDialogsType != 3 && ExteraConfig.getTabCounter()) {
                int defaultTabId = DialogsActivity.this.filterTabsView.getDefaultTabId();
                DialogsActivity dialogsActivity = DialogsActivity.this;
                if (i == defaultTabId) {
                    return dialogsActivity.getMessagesStorage().getMainUnreadCount();
                }
                ArrayList<MessagesController.DialogFilter> dialogFilters = dialogsActivity.getMessagesController().getDialogFilters();
                if (i >= 0 && i < dialogFilters.size()) {
                    return DialogsActivity.this.getMessagesController().getDialogFilters().get(i).unreadCount;
                }
            }
            return 0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:148:0x005b  */
        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean didSelectTab(org.telegram.ui.Components.FilterTabsView.TabView r19, boolean r20) {
            /*
                Method dump skipped, instruction units count: 616
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.C552311.didSelectTab(org.telegram.ui.Components.FilterTabsView$TabView, boolean):boolean");
        }

        /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$11$1 */
        /* JADX INFO: loaded from: classes6.dex */
        public class AnonymousClass1 extends Drawable {
            private Paint paint = new Paint(1);
            private RectF bound = new RectF();

            @Override // android.graphics.drawable.Drawable
            public int getOpacity() {
                return -2;
            }

            @Override // android.graphics.drawable.Drawable
            public void setColorFilter(ColorFilter colorFilter) {
            }

            public AnonymousClass1() {
                this.paint.setColor(DialogsActivity.this.getThemedColor(Theme.key_actionBarDefaultSubmenuBackground));
            }

            @Override // android.graphics.drawable.Drawable
            public void draw(Canvas canvas) {
                this.bound.set(getBounds());
                this.bound.inset(0.0f, (this.bound.height() - AndroidUtilities.m1036dp(28.0f)) / 2.0f);
                canvas.drawRoundRect(this.bound, AndroidUtilities.m1036dp(14.0f), AndroidUtilities.m1036dp(14.0f), this.paint);
            }

            @Override // android.graphics.drawable.Drawable
            public void setAlpha(int i) {
                this.paint.setAlpha(i);
            }
        }

        public /* synthetic */ void lambda$didSelectTab$1() {
            DialogsActivity.this.filterTabsView.setIsEditing(true);
            DialogsActivity.this.showDoneItem(true);
        }

        public /* synthetic */ void lambda$didSelectTab$2(boolean z, MessagesController.DialogFilter dialogFilter) {
            DialogsActivity.this.presentFragment(z ? new FiltersSetupActivity() : new FilterCreateActivity(dialogFilter));
        }

        public /* synthetic */ void lambda$didSelectTab$3(ArrayList arrayList, boolean z) {
            int i = 0;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                TLRPC.Dialog dialog = (TLRPC.Dialog) arrayList.get(i2);
                if (dialog != null) {
                    DialogsActivity.this.getNotificationsController().setDialogNotificationsSettings(dialog.f1251id, 0L, z ? 3 : 4);
                    i++;
                }
            }
            BulletinFactory.createMuteBulletin(DialogsActivity.this, z, i, (Theme.ResourcesProvider) null).show();
        }

        public /* synthetic */ void lambda$didSelectTab$4(ArrayList arrayList) {
            DialogsActivity.this.markDialogsAsRead(arrayList);
        }

        public /* synthetic */ void lambda$didSelectTab$5(boolean[] zArr, MessagesController.DialogFilter dialogFilter) {
            boolean z = zArr[0];
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (z) {
                dialogsActivity.presentFragment(new FilterChatlistActivity(dialogFilter, null));
            } else {
                FilterCreateActivity.FilterInvitesBottomSheet.show(dialogsActivity, dialogFilter, null);
            }
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public boolean isTabMenuVisible() {
            return DialogsActivity.this.filterOptions != null && DialogsActivity.this.filterOptions.isShown();
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public void onDeletePressed(int i) {
            lambda$didSelectTab$6(DialogsActivity.this.getMessagesController().getDialogFilters().get(i));
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public void onTabSelected(FilterTabsView.Tab tab, boolean z, boolean z2) {
            if (((BaseFragment) DialogsActivity.this).actionBar == null) {
                return;
            }
            TabIconsMode tabIcons = ExteraConfig.getTabIcons();
            TabIconsMode tabIconsMode = TabIconsMode.ICONS_ONLY;
            if (tabIcons != tabIconsMode) {
                if (this.lastTitleType == tabIconsMode) {
                    ((BaseFragment) DialogsActivity.this).actionBar.setTitle(LocaleUtils.getActionBarTitle(((BaseFragment) DialogsActivity.this).currentAccount), DialogsActivity.this.statusDrawable);
                    DialogStoriesCell dialogStoriesCell = DialogsActivity.this.dialogStoriesCell;
                    if (dialogStoriesCell != null) {
                        dialogStoriesCell.setDialogsTitleOverride(null, false);
                    }
                    this.lastTitleType = ExteraConfig.getTabIcons();
                    return;
                }
                return;
            }
            if (DialogsActivity.this.selectedDialogs.isEmpty()) {
                CharSequence charSequence = tab.isDefault ? DialogsActivity.this.actionBarDefaultTitle : tab.realTitle;
                DialogsActivity dialogsActivity = DialogsActivity.this;
                if (z2) {
                    ((BaseFragment) dialogsActivity).actionBar.setTitleAnimatedX(charSequence, tab.isDefault ? DialogsActivity.this.statusDrawable : null, z, 350);
                } else {
                    ((BaseFragment) dialogsActivity).actionBar.setTitle(charSequence, tab.isDefault ? DialogsActivity.this.statusDrawable : null);
                }
                DialogStoriesCell dialogStoriesCell2 = DialogsActivity.this.dialogStoriesCell;
                if (dialogStoriesCell2 != null) {
                    dialogStoriesCell2.setDialogsTitleOverride(charSequence, z2, z);
                }
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$12 */
    public class C552412 extends ActionBar.ActionBarMenuOnItemClick {
        public C552412() {
        }

        @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
        public void onItemClick(int i) {
            if ((i == 201 || i == 200 || i == 202 || i == 203) && DialogsActivity.this.searchViewPager != null) {
                DialogsActivity.this.searchViewPager.onActionBarItemClick(i);
                return;
            }
            if (i == -1) {
                if (ExteraConfig.getNavigationDrawer() && DialogsActivity.this.drawerContainer() != null && DialogsActivity.this.canOpenDrawer()) {
                    DialogsActivity.this.drawerContainer().toggleDrawer();
                    return;
                }
                RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                if (rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment()) {
                    boolean zIsActionModeShowed = ((BaseFragment) DialogsActivity.this).actionBar.isActionModeShowed();
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    if (zIsActionModeShowed) {
                        if (dialogsActivity.searchViewPager != null && DialogsActivity.this.searchViewPager.getVisibility() == 0 && DialogsActivity.this.searchViewPager.actionModeShowing()) {
                            DialogsActivity.this.searchViewPager.hideActionMode();
                            return;
                        } else {
                            DialogsActivity.this.hideActionMode(true);
                            return;
                        }
                    }
                    dialogsActivity.rightSlidingDialogContainer.lambda$presentFragment$1();
                    if (DialogsActivity.this.searchViewPager != null) {
                        DialogsActivity.this.searchViewPager.updateTabs();
                        return;
                    }
                    return;
                }
                if (DialogsActivity.this.filterTabsView == null || !DialogsActivity.this.filterTabsView.isEditing()) {
                    boolean zIsActionModeShowed2 = ((BaseFragment) DialogsActivity.this).actionBar.isActionModeShowed();
                    DialogsActivity dialogsActivity2 = DialogsActivity.this;
                    if (zIsActionModeShowed2) {
                        if (dialogsActivity2.searchViewPager != null && DialogsActivity.this.searchViewPager.getVisibility() == 0 && DialogsActivity.this.searchViewPager.actionModeShowing()) {
                            DialogsActivity.this.searchViewPager.hideActionMode();
                            return;
                        } else {
                            DialogsActivity.this.hideActionMode(true);
                            return;
                        }
                    }
                    if (dialogsActivity2.onlySelect || DialogsActivity.this.folderId != 0) {
                        DialogsActivity.this.finishFragment();
                        return;
                    }
                    return;
                }
                DialogsActivity.this.filterTabsView.setIsEditing(false);
                DialogsActivity.this.showDoneItem(false);
                return;
            }
            if (i == 1) {
                if (DialogsActivity.this.getParentActivity() == null) {
                    return;
                }
                SharedConfig.appLocked = true;
                SharedConfig.saveConfig();
                int[] iArr = new int[2];
                DialogsActivity.this.passcodeItem.getLocationInWindow(iArr);
                ((LaunchActivity) DialogsActivity.this.getParentActivity()).showPasscodeActivity(false, true, iArr[0] + (DialogsActivity.this.passcodeItem.getMeasuredWidth() / 2), iArr[1] + (DialogsActivity.this.passcodeItem.getMeasuredHeight() / 2), new Runnable() { // from class: org.telegram.ui.DialogsActivity$12$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onItemClick$0();
                    }
                }, new Runnable() { // from class: org.telegram.ui.DialogsActivity$12$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onItemClick$1();
                    }
                });
                DialogsActivity.this.getNotificationsController().showNotifications();
                DialogsActivity.this.checkUi_itemPasscodeVisibility();
                return;
            }
            if (i == 3) {
                DialogsActivity.this.showSearch(true, true, true);
                DialogsActivity.this.fragmentSearchFieldWatcher.toggleSearch(true);
                return;
            }
            if (i == 11) {
                DialogsActivity dialogsActivity3 = DialogsActivity.this;
                dialogsActivity3.openAccountSelector(dialogsActivity3.switchItem);
                return;
            }
            if (i == 109) {
                DialogsActivity dialogsActivity4 = DialogsActivity.this;
                FiltersListBottomSheet filtersListBottomSheet = new FiltersListBottomSheet(dialogsActivity4, dialogsActivity4.selectedDialogs);
                filtersListBottomSheet.setDelegate(new FiltersListBottomSheet.FiltersListBottomSheetDelegate() { // from class: org.telegram.ui.DialogsActivity$12$$ExternalSyntheticLambda2
                    @Override // org.telegram.ui.Components.FiltersListBottomSheet.FiltersListBottomSheetDelegate
                    public final void didSelectFilter(MessagesController.DialogFilter dialogFilter, boolean z) {
                        this.f$0.lambda$onItemClick$2(dialogFilter, z);
                    }
                });
                DialogsActivity.this.showDialog(filtersListBottomSheet);
                return;
            }
            if (i != 110) {
                if (i == 100 || i == 101 || i == 102 || i == 103 || i == 104 || i == 105 || i == 106 || i == 107 || i == 108) {
                    DialogsActivity dialogsActivity5 = DialogsActivity.this;
                    dialogsActivity5.performSelectedDialogsAction(dialogsActivity5.selectedDialogs, i, true, false);
                    return;
                }
                return;
            }
            MessagesController.DialogFilter dialogFilter = DialogsActivity.this.getMessagesController().getDialogFilters().get(DialogsActivity.this.viewPages[0].selectedType);
            DialogsActivity dialogsActivity6 = DialogsActivity.this;
            ArrayList<Long> dialogsCount = FiltersListBottomSheet.getDialogsCount(dialogsActivity6, dialogFilter, dialogsActivity6.selectedDialogs, false, false);
            if ((dialogFilter != null ? dialogFilter.neverShow.size() : 0) + dialogsCount.size() > 100) {
                DialogsActivity dialogsActivity7 = DialogsActivity.this;
                dialogsActivity7.showDialog(AlertsCreator.createSimpleAlert(dialogsActivity7.getParentActivity(), LocaleController.getString(C2797R.string.FilterAddToAlertFullTitle), LocaleController.getString(C2797R.string.FilterAddToAlertFullText)).create());
                return;
            }
            if (!dialogsCount.isEmpty()) {
                dialogFilter.neverShow.addAll(dialogsCount);
                for (int i2 = 0; i2 < dialogsCount.size(); i2++) {
                    Long l = dialogsCount.get(i2);
                    dialogFilter.alwaysShow.remove(l);
                    dialogFilter.pinnedDialogs.delete(l.longValue());
                }
                if (dialogFilter.isChatlist()) {
                    dialogFilter.neverShow.clear();
                }
                FilterCreateActivity.saveFilterToServer(dialogFilter, dialogFilter.flags, dialogFilter.name, dialogFilter.entities, dialogFilter.title_noanimate, dialogFilter.color, dialogFilter.emoticon, dialogFilter.alwaysShow, dialogFilter.neverShow, dialogFilter.pinnedDialogs, false, false, true, false, false, DialogsActivity.this, null);
            }
            long jLongValue = dialogsCount.size() == 1 ? dialogsCount.get(0).longValue() : 0L;
            UndoView undoView = DialogsActivity.this.getUndoView();
            if (undoView != null) {
                undoView.showWithAction(jLongValue, 21, Integer.valueOf(dialogsCount.size()), dialogFilter, (Runnable) null, (Runnable) null);
            }
            DialogsActivity.this.hideActionMode(false);
        }

        public /* synthetic */ void lambda$onItemClick$0() {
            DialogsActivity.this.passcodeItem.setAlpha(1.0f);
        }

        public /* synthetic */ void lambda$onItemClick$1() {
            DialogsActivity.this.passcodeItem.setAlpha(0.0f);
        }

        public /* synthetic */ void lambda$onItemClick$2(MessagesController.DialogFilter dialogFilter, boolean z) {
            C552412 c552412;
            boolean z2;
            ArrayList<Long> arrayList;
            ArrayList<Long> arrayList2;
            DialogsActivity dialogsActivity = DialogsActivity.this;
            ArrayList<Long> dialogsCount = FiltersListBottomSheet.getDialogsCount(dialogsActivity, dialogFilter, dialogsActivity.selectedDialogs, true, false);
            if (!z) {
                int size = (dialogFilter != null ? dialogFilter.alwaysShow.size() : 0) + dialogsCount.size();
                if ((size > DialogsActivity.this.getMessagesController().dialogFiltersChatsLimitDefault && !DialogsActivity.this.getUserConfig().isPremium()) || size > DialogsActivity.this.getMessagesController().dialogFiltersChatsLimitPremium) {
                    DialogsActivity dialogsActivity2 = DialogsActivity.this;
                    DialogsActivity dialogsActivity3 = DialogsActivity.this;
                    dialogsActivity2.showDialog(new LimitReachedBottomSheet(dialogsActivity3, dialogsActivity3.fragmentView.getContext(), 4, ((BaseFragment) DialogsActivity.this).currentAccount, null));
                    return;
                }
            }
            if (dialogFilter != null) {
                if (z) {
                    for (int i = 0; i < DialogsActivity.this.selectedDialogs.size(); i++) {
                        dialogFilter.neverShow.add((Long) DialogsActivity.this.selectedDialogs.get(i));
                        dialogFilter.alwaysShow.remove(DialogsActivity.this.selectedDialogs.get(i));
                    }
                    FilterCreateActivity.saveFilterToServer(dialogFilter, dialogFilter.flags, dialogFilter.name, dialogFilter.entities, dialogFilter.title_noanimate, dialogFilter.color, dialogFilter.emoticon, dialogFilter.alwaysShow, dialogFilter.neverShow, dialogFilter.pinnedDialogs, false, false, true, true, false, DialogsActivity.this, null);
                    long jLongValue = DialogsActivity.this.selectedDialogs.size() == 1 ? ((Long) DialogsActivity.this.selectedDialogs.get(0)).longValue() : 0L;
                    UndoView undoView = DialogsActivity.this.getUndoView();
                    if (undoView != null) {
                        undoView.showWithAction(jLongValue, 21, Integer.valueOf(DialogsActivity.this.selectedDialogs.size()), dialogFilter, (Runnable) null, (Runnable) null);
                    }
                    c552412 = this;
                    z2 = true;
                } else {
                    if (dialogsCount.isEmpty()) {
                        arrayList = dialogsCount;
                        z2 = true;
                    } else {
                        for (int i2 = 0; i2 < dialogsCount.size(); i2++) {
                            dialogFilter.neverShow.remove(dialogsCount.get(i2));
                        }
                        dialogFilter.alwaysShow.addAll(dialogsCount);
                        arrayList = dialogsCount;
                        z2 = true;
                        FilterCreateActivity.saveFilterToServer(dialogFilter, dialogFilter.flags, dialogFilter.name, dialogFilter.entities, dialogFilter.title_noanimate, dialogFilter.color, dialogFilter.emoticon, dialogFilter.alwaysShow, dialogFilter.neverShow, dialogFilter.pinnedDialogs, false, false, true, true, false, DialogsActivity.this, null);
                    }
                    if (arrayList.size() == z2) {
                        arrayList2 = arrayList;
                        jLongValue = arrayList2.get(0).longValue();
                    } else {
                        arrayList2 = arrayList;
                    }
                    c552412 = this;
                    long j = jLongValue;
                    UndoView undoView2 = DialogsActivity.this.getUndoView();
                    if (undoView2 != null) {
                        undoView2.showWithAction(j, 20, Integer.valueOf(arrayList2.size()), dialogFilter, (Runnable) null, (Runnable) null);
                    }
                }
            } else {
                c552412 = this;
                z2 = true;
                DialogsActivity.this.presentFragment(new FilterCreateActivity(null, dialogsCount));
            }
            DialogsActivity.this.hideActionMode(z2);
        }
    }

    public /* synthetic */ void lambda$createView$15(ContentView contentView, PointF pointF, Canvas canvas, RectF rectF) {
        SearchViewPager searchViewPager = this.searchViewPager;
        int alpha = searchViewPager != null ? (int) (searchViewPager.getAlpha() * 255.0f) : 0;
        for (ViewPage viewPage : this.viewPages) {
            if (viewPage != null && viewPage.getVisibility() == 0 && viewPage.getAlpha() > 0.0f) {
                float rightSlidingProgress = getRightSlidingProgress();
                if (viewPage.animationSupportListView != null && rightSlidingProgress > 0.0f) {
                    if (!ViewPositionWatcher.computeCoordinatesInParent(viewPage.listView, contentView, pointF)) {
                        return;
                    }
                    canvas.save();
                    canvas.clipRect(rectF);
                    canvas.translate(pointF.x, pointF.y);
                    viewPage.listView.dispatchDraw(canvas);
                    canvas.restore();
                } else {
                    DialogsRecyclerView dialogsRecyclerView = viewPage.listView;
                    Blur3Utils.captureRelativeParent(dialogsRecyclerView, canvas, rectF, dialogsRecyclerView, contentView, 255 - alpha);
                }
            }
        }
        SearchViewPager searchViewPager2 = this.searchViewPager;
        if (searchViewPager2 == null || searchViewPager2.getVisibility() != 0 || this.searchViewPager.getAlpha() <= 0.0f) {
            return;
        }
        SearchViewPager searchViewPager3 = this.searchViewPager;
        Blur3Utils.captureRelativeParent(searchViewPager3, canvas, rectF, searchViewPager3, contentView, alpha);
    }

    public /* synthetic */ void lambda$createView$16(ViewPage viewPage) {
        viewPage.listView.postOnAnimation(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda89
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.blur3_InvalidateBlur();
            }
        });
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$13 */
    public class C552513 extends DialogsItemAnimator {
        final /* synthetic */ ViewPage val$viewPage;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C552513(RecyclerListView recyclerListView, ViewPage viewPage) {
            super(recyclerListView);
            viewPage = viewPage;
        }

        @Override // androidx.recyclerview.widget.SimpleItemAnimator
        public void onRemoveStarting(RecyclerView.ViewHolder viewHolder) {
            super.onRemoveStarting(viewHolder);
            if (viewPage.layoutManager.findFirstVisibleItemPosition() == 0) {
                View viewFindViewByPosition = viewPage.layoutManager.findViewByPosition(0);
                if (viewFindViewByPosition != null) {
                    viewFindViewByPosition.invalidate();
                }
                if (viewPage.archivePullViewState == 2) {
                    viewPage.archivePullViewState = 1;
                }
                if (viewPage.pullForegroundDrawable != null) {
                    viewPage.pullForegroundDrawable.doNotShow();
                }
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$14 */
    public class C552614 extends LinearLayoutManager {
        private boolean fixOffset;
        boolean lastDragging;
        ValueAnimator storiesOverscrollAnimator;
        final /* synthetic */ ViewPage val$viewPage;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C552614(Context context, ViewPage viewPage) {
            super(context);
            this.val$viewPage = viewPage;
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        public int firstPosition() {
            return (this.val$viewPage.dialogsType == 0 && DialogsActivity.this.hasHiddenArchive() && this.val$viewPage.archivePullViewState == 2) ? 1 : 0;
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        public void scrollToPositionWithOffset(int i, int i2) {
            if (this.fixOffset) {
                i2 -= this.val$viewPage.listView.getPaddingTop();
            }
            super.scrollToPositionWithOffset(i, i2);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.ItemTouchHelper.ViewDropHandler
        public void prepareForDrop(View view, View view2, int i, int i2) {
            this.fixOffset = true;
            super.prepareForDrop(view, view2, i, i2);
            this.fixOffset = false;
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
            if (DialogsActivity.this.hasHiddenArchive() && i == 1) {
                super.smoothScrollToPosition(recyclerView, state, i);
                return;
            }
            LinearSmoothScrollerCustom linearSmoothScrollerCustom = new LinearSmoothScrollerCustom(recyclerView.getContext(), 0);
            linearSmoothScrollerCustom.setTargetPosition(i);
            startSmoothScroll(linearSmoothScrollerCustom);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onScrollStateChanged(int i) {
            super.onScrollStateChanged(i);
            ValueAnimator valueAnimator = this.storiesOverscrollAnimator;
            if (valueAnimator != null) {
                valueAnimator.removeAllListeners();
                this.storiesOverscrollAnimator.cancel();
            }
            if (this.val$viewPage.listView.getScrollState() != 1) {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(DialogsActivity.this.storiesOverscroll, 0.0f);
                this.storiesOverscrollAnimator = valueAnimatorOfFloat;
                final ViewPage viewPage = this.val$viewPage;
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$14$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        this.f$0.lambda$onScrollStateChanged$0(viewPage, valueAnimator2);
                    }
                });
                this.storiesOverscrollAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.14.1
                    public AnonymousClass1() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        C552614 c552614 = C552614.this;
                        DialogsActivity.this.setStoriesOvercroll(c552614.val$viewPage, 0.0f);
                    }
                });
                this.storiesOverscrollAnimator.setDuration(200L);
                this.storiesOverscrollAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
                this.storiesOverscrollAnimator.start();
            }
        }

        public /* synthetic */ void lambda$onScrollStateChanged$0(ViewPage viewPage, ValueAnimator valueAnimator) {
            DialogsActivity.this.setStoriesOvercroll(viewPage, ((Float) valueAnimator.getAnimatedValue()).floatValue());
        }

        /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$14$1 */
        public class AnonymousClass1 extends AnimatorListenerAdapter {
            public AnonymousClass1() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                C552614 c552614 = C552614.this;
                DialogsActivity.this.setStoriesOvercroll(c552614.val$viewPage, 0.0f);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:410:0x02a3  */
        /* JADX WARN: Removed duplicated region for block: B:411:0x02a7  */
        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int scrollVerticallyBy(int r22, androidx.recyclerview.widget.RecyclerView.Recycler r23, androidx.recyclerview.widget.RecyclerView.State r24) {
            /*
                Method dump skipped, instruction units count: 1231
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.C552614.scrollVerticallyBy(int, androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State):int");
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            boolean z = BuildVars.DEBUG_VERSION;
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                FileLog.m1048e(e);
                final ViewPage viewPage = this.val$viewPage;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$14$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        viewPage.dialogsAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    public /* synthetic */ void lambda$createView$17(ViewPage viewPage, View view, int i, float f, float f2) {
        if (view instanceof GraySectionCell) {
            return;
        }
        boolean z = view instanceof DialogCell;
        if (z) {
            DialogCell dialogCell = (DialogCell) view;
            if (dialogCell.isBlocked()) {
                showPremiumBlockedToast(view, dialogCell.getDialogId());
                return;
            }
        }
        if (clickSelectsDialog()) {
            onItemLongClick(viewPage.listView, view, i, 0.0f, 0.0f, viewPage.dialogsType, viewPage.dialogsAdapter);
            return;
        }
        int i2 = this.initialDialogsType;
        if (i2 == 15 && (view instanceof TextCell)) {
            viewPage.dialogsAdapter.onCreateGroupForThisClick();
            return;
        }
        if ((i2 == 11 || i2 == 13) && i == 1) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("forImport", true);
            bundle.putLongArray("result", new long[]{getUserConfig().getClientUserId()});
            bundle.putInt("chatType", 4);
            String string = this.arguments.getString("importTitle");
            if (string != null) {
                bundle.putString("title", string);
            }
            GroupCreateFinalActivity groupCreateFinalActivity = new GroupCreateFinalActivity(bundle);
            groupCreateFinalActivity.setDelegate(new GroupCreateFinalActivity.GroupCreateFinalActivityDelegate() { // from class: org.telegram.ui.DialogsActivity.15
                @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
                public void didFailChatCreation() {
                }

                @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
                public void didStartChatCreation() {
                }

                public C552715() {
                }

                @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
                public void didFinishChatCreation(GroupCreateFinalActivity groupCreateFinalActivity2, long j) {
                    ArrayList<MessagesStorage.TopicKey> arrayList = new ArrayList<>();
                    arrayList.add(MessagesStorage.TopicKey.m1066of(-j, 0L));
                    DialogsActivityDelegate dialogsActivityDelegate = DialogsActivity.this.delegate;
                    if (DialogsActivity.this.closeFragment) {
                        DialogsActivity.this.removeSelfFromStack();
                    }
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    dialogsActivityDelegate.didSelectDialogs(dialogsActivity, arrayList, null, true, dialogsActivity.notify, dialogsActivity.scheduleDate, dialogsActivity.scheduleRepeatPeriod, null);
                }
            });
            presentFragment(groupCreateFinalActivity);
            return;
        }
        if ((view instanceof DialogsHintCell) && (viewPage.dialogsType == 7 || viewPage.dialogsType == 8)) {
            TL_chatlists.TL_chatlists_chatlistUpdates chatlistUpdate = viewPage.dialogsAdapter.getChatlistUpdate();
            if (chatlistUpdate != null) {
                MessagesController.DialogFilter dialogFilter = getMessagesController().selectedDialogFilter[viewPage.dialogsType - 7];
                if (dialogFilter != null) {
                    showDialog(new FolderBottomSheet(this, dialogFilter.f1156id, chatlistUpdate));
                    return;
                }
                return;
            }
        } else if (z && !this.actionBar.isActionModeShowed() && !this.rightSlidingDialogContainer.hasFragment()) {
            DialogCell dialogCell2 = (DialogCell) view;
            AndroidUtilities.rectTmp.set(dialogCell2.avatarImage.getImageX(), dialogCell2.avatarImage.getImageY(), dialogCell2.avatarImage.getImageX2(), dialogCell2.avatarImage.getImageY2());
        }
        onItemClick(view, i, viewPage.dialogsAdapter, f, f2);
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$15 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C552715 implements GroupCreateFinalActivity.GroupCreateFinalActivityDelegate {
        @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
        public void didFailChatCreation() {
        }

        @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
        public void didStartChatCreation() {
        }

        public C552715() {
        }

        @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
        public void didFinishChatCreation(GroupCreateFinalActivity groupCreateFinalActivity2, long j) {
            ArrayList<MessagesStorage.TopicKey> arrayList = new ArrayList<>();
            arrayList.add(MessagesStorage.TopicKey.m1066of(-j, 0L));
            DialogsActivityDelegate dialogsActivityDelegate = DialogsActivity.this.delegate;
            if (DialogsActivity.this.closeFragment) {
                DialogsActivity.this.removeSelfFromStack();
            }
            DialogsActivity dialogsActivity = DialogsActivity.this;
            dialogsActivityDelegate.didSelectDialogs(dialogsActivity, arrayList, null, true, dialogsActivity.notify, dialogsActivity.scheduleDate, dialogsActivity.scheduleRepeatPeriod, null);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$16 */
    public class C552816 implements RecyclerListView.OnItemLongClickListenerExtended {
        final /* synthetic */ ViewPage val$viewPage;

        public C552816(ViewPage viewPage) {
            viewPage = viewPage;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
        public boolean onItemClick(View view, int i, float f, float f2) {
            boolean z = view instanceof DialogCell;
            if (z) {
                DialogCell dialogCell = (DialogCell) view;
                if (dialogCell.isBlocked()) {
                    DialogsActivity.this.showPremiumBlockedToast(view, dialogCell.getDialogId());
                    return true;
                }
            }
            if (DialogsActivity.this.filterTabsView != null && DialogsActivity.this.filterTabsView.getVisibility() == 0 && DialogsActivity.this.filterTabsView.isEditing()) {
                return false;
            }
            DialogsActivity dialogsActivity = DialogsActivity.this;
            ViewPage viewPage = viewPage;
            boolean zOnItemLongClick = dialogsActivity.onItemLongClick(viewPage.listView, view, i, f, f2, viewPage.dialogsType, viewPage.dialogsAdapter);
            if (zOnItemLongClick && ((BaseFragment) DialogsActivity.this).actionBar.isActionModeShowed() && z) {
                DialogsActivity dialogsActivity2 = DialogsActivity.this;
                ViewPage viewPage2 = viewPage;
                dialogsActivity2.startMultiselect(viewPage2.listView, i, viewPage2.dialogsAdapter);
            }
            return zOnItemLongClick;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
        public void onMove(float f, float f2) {
            Point point = AndroidUtilities.displaySize;
            if (point.x > point.y) {
                DialogsActivity.this.movePreviewFragment(f2);
            }
        }

        @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
        public void onLongClickRelease() {
            Point point = AndroidUtilities.displaySize;
            if (point.x > point.y) {
                DialogsActivity.this.finishPreviewFragment();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$17 */
    public class C552917 extends RecyclerView.OnScrollListener {
        final /* synthetic */ ContentView val$contentView;
        final /* synthetic */ ViewPage val$viewPage;
        private boolean wasManualScroll;

        public C552917(ViewPage viewPage, ContentView contentView) {
            viewPage = viewPage;
            contentView = contentView;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 1) {
                this.wasManualScroll = true;
                DialogsActivity.this.scrollingManually = true;
                DialogsActivity.this.viewPages[0].scroller.cancel();
                if (DialogsActivity.this.fragmentSearchField.editText.getText().length() == 0 && DialogsActivity.this.fragmentSearchField.editText.hasFocus()) {
                    AndroidUtilities.hideKeyboard(DialogsActivity.this.fragmentSearchField.editText);
                    DialogsActivity.this.fragmentSearchField.editText.clearFocus();
                }
            } else {
                DialogsActivity.this.scrollingManually = false;
            }
            if (i == 0) {
                this.wasManualScroll = false;
                DialogsActivity.this.disableActionBarScrolling = false;
                if (DialogsActivity.this.waitingForScrollFinished) {
                    DialogsActivity.this.waitingForScrollFinished = false;
                    if (DialogsActivity.this.updatePullAfterScroll) {
                        viewPage.listView.updatePullState();
                        DialogsActivity.this.updatePullAfterScroll = false;
                    }
                    viewPage.dialogsAdapter.notifyDataSetChanged();
                }
                DialogsActivity.this.checkAutoscrollToStories(viewPage);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:191:0x00c9  */
        /* JADX WARN: Type inference failed for: r7v7, types: [boolean] */
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
        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onScrolled(androidx.recyclerview.widget.RecyclerView r7, int r8, int r9) {
            /*
                Method dump skipped, instruction units count: 521
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.C552917.onScrolled(androidx.recyclerview.widget.RecyclerView, int, int):void");
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$18 */
    public class C553018 extends PullForegroundDrawable {
        final /* synthetic */ ViewPage val$viewPage;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C553018(CharSequence charSequence, CharSequence charSequence2, ViewPage viewPage) {
            super(charSequence, charSequence2);
            viewPage = viewPage;
        }

        @Override // org.telegram.p035ui.Components.PullForegroundDrawable
        public float getViewOffset() {
            return viewPage.listView.getViewOffset();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$19 */
    public class C553119 extends DialogsAdapter {
        final /* synthetic */ ViewPage val$viewPage;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C553119(DialogsActivity dialogsActivity, Context context, int i, int i2, boolean z, ArrayList arrayList, int i3, TLRPC.RequestPeerType requestPeerType, ViewPage viewPage) {
            super(dialogsActivity, context, i, i2, z, arrayList, i3, requestPeerType);
            viewPage = viewPage;
        }

        @Override // org.telegram.p035ui.Adapters.DialogsAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            viewPage.lastItemsCount = getItemCount();
            try {
                super.notifyDataSetChanged();
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
            if (DialogsActivity.this.initialDialogsType == 15) {
                DialogsActivity.this.searchItem.setVisibility(this.isEmpty ? 8 : 0);
            }
        }

        @Override // org.telegram.p035ui.Adapters.DialogsAdapter, org.telegram.ui.Cells.DialogCell.DialogCellDelegate
        public void onButtonClicked(DialogCell dialogCell) {
            TLRPC.TL_forumTopic tL_forumTopicFindTopic;
            if (dialogCell.getMessage() == null || (tL_forumTopicFindTopic = DialogsActivity.this.getMessagesController().getTopicsController().findTopic(-dialogCell.getDialogId(), MessageObject.getTopicId(((BaseFragment) DialogsActivity.this).currentAccount, dialogCell.getMessage().messageOwner, true))) == null) {
                return;
            }
            boolean z = DialogsActivity.this.onlySelect;
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (z) {
                dialogsActivity.didSelectResult(dialogCell.getDialogId(), tL_forumTopicFindTopic.f1306id, false, false);
            } else {
                ForumUtilities.openTopic(dialogsActivity, -dialogCell.getDialogId(), tL_forumTopicFindTopic, 0);
            }
        }

        @Override // org.telegram.p035ui.Adapters.DialogsAdapter, org.telegram.ui.Cells.DialogCell.DialogCellDelegate
        public void onButtonLongPress(DialogCell dialogCell) {
            DialogsActivity dialogsActivity = DialogsActivity.this;
            DialogsRecyclerView dialogsRecyclerView = viewPage.listView;
            dialogsActivity.onItemLongClick(dialogsRecyclerView, dialogCell, dialogsRecyclerView.getChildAdapterPosition(dialogCell), 0.0f, 0.0f, viewPage.dialogsType, viewPage.dialogsAdapter);
        }

        @Override // org.telegram.p035ui.Adapters.DialogsAdapter
        public void onCreateGroupForThisClick() {
            DialogsActivity.this.createGroupForThis();
        }

        @Override // org.telegram.p035ui.Adapters.DialogsAdapter
        public void onArchiveSettingsClick() {
            DialogsActivity.this.presentFragment(new ArchiveSettingsActivity());
        }

        @Override // org.telegram.p035ui.Adapters.DialogsAdapter
        public boolean showOpenBotButton() {
            return DialogsActivity.this.initialDialogsType == 0;
        }

        @Override // org.telegram.p035ui.Adapters.DialogsAdapter
        public void onOpenBot(TLRPC.User user) {
            MessagesController.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).openApp(user, 0);
        }
    }

    public /* synthetic */ void lambda$createView$18() {
        this.invalidateScrollY = true;
        this.fragmentView.invalidate();
    }

    public /* synthetic */ void lambda$createView$19(View view, int i) {
        this.filtersView.cancelClickRunnables(true);
        addSearchFilter(this.filtersView.getFilterAt(i));
    }

    public /* synthetic */ void lambda$createView$20(View view) {
        openStoriesRecorder();
    }

    public /* synthetic */ void lambda$createView$21(View view) {
        INavigationLayout iNavigationLayout = this.parentLayout;
        if (iNavigationLayout != null && iNavigationLayout.isInPreviewMode()) {
            finishPreviewFragment();
            return;
        }
        if (this.initialDialogsType == 10) {
            if (this.delegate == null || this.selectedDialogs.isEmpty()) {
                return;
            }
            ArrayList<MessagesStorage.TopicKey> arrayList = new ArrayList<>();
            for (int i = 0; i < this.selectedDialogs.size(); i++) {
                arrayList.add(MessagesStorage.TopicKey.m1066of(this.selectedDialogs.get(i).longValue(), 0L));
            }
            this.delegate.didSelectDialogs(this, arrayList, null, false, this.notify, this.scheduleDate, this.scheduleRepeatPeriod, null);
            return;
        }
        if (MessagesController.getInstance(this.currentAccount).isFrozen()) {
            AccountFrozenAlert.show(this.currentAccount);
        } else {
            openWriteContacts();
        }
    }

    public /* synthetic */ void lambda$createView$22() {
        MessagesController.getInstance(this.currentAccount).getMainSettings().edit().putBoolean("storyhint", false).commit();
    }

    public /* synthetic */ void lambda$createView$23() {
        this.viewPages[0].listView.requestLayout();
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        TopicsFragment topicsFragment = (rightSlidingDialogContainer == null || !(rightSlidingDialogContainer.getFragment() instanceof TopicsFragment)) ? null : (TopicsFragment) this.rightSlidingDialogContainer.getFragment();
        if (topicsFragment != null) {
            topicsFragment.checkUi_listViewPadding();
        }
        checkUi_searchPagesPaddings(false);
        updateContextViewPosition();
        SearchViewPager searchViewPager = this.searchViewPager;
        if (searchViewPager != null) {
            searchViewPager.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$20 */
    public class C553320 extends FragmentContextView {
        public C553320(Context context, BaseFragment baseFragment, boolean z) {
            super(context, baseFragment, z);
        }

        @Override // org.telegram.p035ui.Components.FragmentContextView, android.view.View
        public void setVisibility(int i) {
            DialogsActivity.this.topPanelLayout.setViewVisible(DialogsActivity.this.fragmentLocationContextViewWrapper, i == 0);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$21 */
    public class C553421 extends FragmentContextView {
        public C553421(Context context, BaseFragment baseFragment, boolean z) {
            super(context, baseFragment, z);
        }

        @Override // org.telegram.p035ui.Components.FragmentContextView, android.view.View
        public void setVisibility(int i) {
            DialogsActivity.this.topPanelLayout.setViewVisible(DialogsActivity.this.fragmentContextViewWrapper, i == 0);
        }
    }

    public /* synthetic */ void lambda$createView$24(Long l) {
        this.cacheSize = l;
        updateDialogsHint();
    }

    public /* synthetic */ void lambda$createView$25(Long l, Long l2) {
        this.deviceSize = l;
        updateDialogsHint();
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$22 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C553522 extends ChatActivityEnterView {
        public C553522(Activity activity, SizeNotifierFrameLayout sizeNotifierFrameLayout, ChatActivity chatActivity, boolean z) {
            super(activity, sizeNotifierFrameLayout, chatActivity, z);
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public void onChangedIslandTotalHeight(float f) {
            DialogsActivity.this.chatInputViewsContainer.setInputBubbleHeight(f);
            DialogsActivity.this.checkUi_chatListViewPaddingsBottom();
            DialogsActivity.this.blur3_InvalidateBlur();
            DialogsActivity.this.checkUi_fadeView();
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                AndroidUtilities.requestAdjustResize(DialogsActivity.this.getParentActivity(), ((BaseFragment) DialogsActivity.this).classGuid);
            }
            return super.dispatchTouchEvent(motionEvent);
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public long getStarsPrice() {
            if (DialogsActivity.this.selectedDialogs == null) {
                return 0L;
            }
            ArrayList arrayList = DialogsActivity.this.selectedDialogs;
            int size = arrayList.size();
            int i = 0;
            long j = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                long jLongValue = ((Long) obj).longValue();
                long sendPaidMessagesStars = DialogsActivity.this.getMessagesController().getSendPaidMessagesStars(jLongValue);
                if (sendPaidMessagesStars <= 0 && jLongValue > 0) {
                    sendPaidMessagesStars = DialogObject.getMessagesStarsPrice(DialogsActivity.this.getMessagesController().isUserContactBlocked(jLongValue));
                }
                j += sendPaidMessagesStars;
            }
            return j;
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public int getMessagesCount() {
            return Math.max(1, DialogsActivity.this.messagesCount + (!TextUtils.isEmpty(DialogsActivity.this.commentView == null ? _UrlKt.FRAGMENT_ENCODE_SET : DialogsActivity.this.commentView.getFieldText()) ? 1 : 0));
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$23 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C553623 implements ChatActivityEnterView.ChatActivityEnterViewDelegate {
        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void bottomPanelTranslationYChanged(float f) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void didPressAttachButton() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public boolean isVideoRecordingPaused() {
            return false;
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needChangeVideoPreviewState(int i, float f) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needSendTyping() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needShowMediaBanHint() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needStartRecordAudio(int i) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needStartRecordVideo(int i, boolean z, int i2, int i3, int i4, long j, long j2) {
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
        public void onStickersExpandedChange() {
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

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void setFrontface(boolean z) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void toggleVideoRecordingPause() {
        }

        public C553623() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onMessageSend(CharSequence charSequence, boolean z, int i, int i2, long j) {
            if (DialogsActivity.this.delegate == null || DialogsActivity.this.selectedDialogs.isEmpty()) {
                return;
            }
            ArrayList<MessagesStorage.TopicKey> arrayList = new ArrayList<>();
            int i3 = 0;
            while (true) {
                int size = DialogsActivity.this.selectedDialogs.size();
                DialogsActivity dialogsActivity = DialogsActivity.this;
                if (i3 < size) {
                    arrayList.add(MessagesStorage.TopicKey.m1066of(((Long) dialogsActivity.selectedDialogs.get(i3)).longValue(), 0L));
                    i3++;
                } else {
                    dialogsActivity.delegate.didSelectDialogs(DialogsActivity.this, arrayList, charSequence, false, z, i, i2, null);
                    return;
                }
            }
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onTextChanged(final CharSequence charSequence, boolean z, boolean z2) {
            final DialogsActivity dialogsActivity = DialogsActivity.this;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$23$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    dialogsActivity.updateSelectedCount();
                }
            }, 100L);
            if (DialogsActivity.this.shareTopView != null) {
                DialogsActivity dialogsActivity2 = DialogsActivity.this;
                if (z) {
                    dialogsActivity2.shareTopView.onTextChanged(charSequence, true);
                    return;
                }
                if (dialogsActivity2.shareLinkSearchRunnable != null) {
                    AndroidUtilities.cancelRunOnUIThread(DialogsActivity.this.shareLinkSearchRunnable);
                }
                DialogsActivity.this.shareLinkSearchRunnable = new Runnable() { // from class: org.telegram.ui.DialogsActivity$23$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onTextChanged$0(charSequence);
                    }
                };
                AndroidUtilities.runOnUIThread(DialogsActivity.this.shareLinkSearchRunnable, 1000L);
            }
        }

        public /* synthetic */ void lambda$onTextChanged$0(CharSequence charSequence) {
            DialogsActivity.this.shareLinkSearchRunnable = null;
            if (DialogsActivity.this.shareTopView != null) {
                DialogsActivity.this.shareTopView.onTextChanged(charSequence, false);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$24 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C553724 extends ChatActivityEnterView.SendButton {
        @Override // org.telegram.ui.Components.ChatActivityEnterView.SendButton
        public boolean isInactive() {
            return false;
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.SendButton
        public boolean isOpen() {
            return true;
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.SendButton
        public boolean shouldDrawBackground() {
            return true;
        }

        public C553724(Context context, int i, Theme.ResourcesProvider resourcesProvider) {
            super(context, i, resourcesProvider);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.SendButton
        public boolean isInScheduleMode() {
            return super.isInScheduleMode();
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setText(LocaleController.formatPluralString("AccDescrShareInChats", DialogsActivity.this.selectedDialogs.size(), new Object[0]));
            accessibilityNodeInfo.setClassName(Button.class.getName());
            accessibilityNodeInfo.setLongClickable(true);
            accessibilityNodeInfo.setClickable(true);
        }
    }

    public /* synthetic */ void lambda$createView$26(View view) {
        if (this.delegate == null || this.selectedDialogs.isEmpty()) {
            return;
        }
        ArrayList<MessagesStorage.TopicKey> arrayList = new ArrayList<>();
        for (int i = 0; i < this.selectedDialogs.size(); i++) {
            arrayList.add(MessagesStorage.TopicKey.m1066of(this.selectedDialogs.get(i).longValue(), 0L));
        }
        this.delegate.didSelectDialogs(this, arrayList, this.commentView.getFieldText(), false, this.notify, this.scheduleDate, this.scheduleRepeatPeriod, null);
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$25 */
    public class C553825 extends DialogStoriesCell {
        public C553825(Context context, BaseFragment baseFragment, int i, int i2) {
            super(context, baseFragment, i, i2);
        }

        /* JADX WARN: Removed duplicated region for block: B:148:0x01d5  */
        /* JADX WARN: Removed duplicated region for block: B:151:0x01de  */
        /* JADX WARN: Removed duplicated region for block: B:152:0x01e0  */
        /* JADX WARN: Removed duplicated region for block: B:155:0x01f6  */
        /* JADX WARN: Removed duplicated region for block: B:156:0x01f8  */
        /* JADX WARN: Removed duplicated region for block: B:159:0x020c  */
        /* JADX WARN: Removed duplicated region for block: B:160:0x020e  */
        /* JADX WARN: Removed duplicated region for block: B:163:0x0217  */
        /* JADX WARN: Removed duplicated region for block: B:164:0x021a  */
        /* JADX WARN: Removed duplicated region for block: B:169:0x022f  */
        /* JADX WARN: Removed duplicated region for block: B:174:0x0257  */
        /* JADX WARN: Removed duplicated region for block: B:182:0x027e  */
        /* JADX WARN: Removed duplicated region for block: B:190:0x02a2  */
        /* JADX WARN: Removed duplicated region for block: B:196:0x02c8  */
        /* JADX WARN: Removed duplicated region for block: B:202:0x02eb  */
        @Override // org.telegram.p035ui.Stories.DialogStoriesCell
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onUserLongPressed(final android.view.View r32, final long r33) {
            /*
                Method dump skipped, instruction units count: 819
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.C553825.onUserLongPressed(android.view.View, long):void");
        }

        public /* synthetic */ void lambda$onUserLongPressed$0() {
            DialogsActivity.this.dialogStoriesCell.openStoryRecorder();
        }

        public /* synthetic */ void lambda$onUserLongPressed$1() {
            Bundle bundle = new Bundle();
            bundle.putLong("dialog_id", UserConfig.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getClientUserId());
            bundle.putInt(TeXSymbolParser.TYPE_ATTR, 1);
            bundle.putInt("start_from", 9);
            DialogsActivity.this.presentFragment(new MediaActivity(bundle, null));
        }

        public /* synthetic */ void lambda$onUserLongPressed$2() {
            Bundle bundle = new Bundle();
            bundle.putLong("dialog_id", UserConfig.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getClientUserId());
            bundle.putInt(TeXSymbolParser.TYPE_ATTR, 1);
            DialogsActivity.this.presentFragment(new MediaActivity(bundle, null));
        }

        public /* synthetic */ void lambda$onUserLongPressed$3(long j) {
            DialogsActivity.this.dialogStoriesCell.openStoryRecorder(j);
        }

        public /* synthetic */ void lambda$onUserLongPressed$4(long j) {
            DialogsActivity.this.presentFragment(ChatActivity.m1139of(j));
        }

        public /* synthetic */ void lambda$onUserLongPressed$5(long j) {
            DialogsActivity.this.presentFragment(ProfileActivity.m1186of(j));
        }

        public /* synthetic */ void lambda$onUserLongPressed$6(long j) {
            DialogsActivity.this.presentFragment(ChatActivity.m1139of(j));
        }

        public /* synthetic */ void lambda$onUserLongPressed$7(String str, long j, TLRPC.User user) {
            MessagesController.getNotificationsSettings(((BaseFragment) DialogsActivity.this).currentAccount).edit().putBoolean(NotificationsSettingsFacade.PROPERTY_STORIES_NOTIFY + str, false).apply();
            DialogsActivity.this.getNotificationsController().updateServerNotificationsSettings(j, 0L);
            String strTrim = user == null ? _UrlKt.FRAGMENT_ENCODE_SET : user.first_name.trim();
            int iIndexOf = strTrim.indexOf(" ");
            if (iIndexOf > 0) {
                strTrim = strTrim.substring(0, iIndexOf);
            }
            BulletinFactory.m1143of(DialogsActivity.this).createUsersBulletin(Arrays.asList(user), AndroidUtilities.replaceTags(LocaleController.formatString("NotificationsStoryMutedHint", C2797R.string.NotificationsStoryMutedHint, strTrim))).show();
        }

        public /* synthetic */ void lambda$onUserLongPressed$8(String str, long j, TLRPC.User user) {
            MessagesController.getNotificationsSettings(((BaseFragment) DialogsActivity.this).currentAccount).edit().putBoolean(NotificationsSettingsFacade.PROPERTY_STORIES_NOTIFY + str, true).apply();
            DialogsActivity.this.getNotificationsController().updateServerNotificationsSettings(j, 0L);
            String strTrim = user == null ? _UrlKt.FRAGMENT_ENCODE_SET : user.first_name.trim();
            int iIndexOf = strTrim.indexOf(" ");
            if (iIndexOf > 0) {
                strTrim = strTrim.substring(0, iIndexOf);
            }
            BulletinFactory.m1143of(DialogsActivity.this).createUsersBulletin(Arrays.asList(user), AndroidUtilities.replaceTags(LocaleController.formatString("NotificationsStoryUnmutedHint", C2797R.string.NotificationsStoryUnmutedHint, strTrim))).show();
        }

        public /* synthetic */ void lambda$onUserLongPressed$10(final View view) {
            TL_stories.TL_storiesStealthMode stealthMode = MessagesController.getInstance(UserConfig.selectedAccount).getStoriesController().getStealthMode();
            if (stealthMode != null && ConnectionsManager.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getCurrentTime() < stealthMode.active_until_date) {
                if (view instanceof DialogStoriesCell.StoryCell) {
                    DialogsActivity.this.dialogStoriesCell.openStoryForCell((DialogStoriesCell.StoryCell) view);
                }
            } else {
                StealthModeAlert stealthModeAlert = new StealthModeAlert(getContext(), 0.0f, 1, ((BaseFragment) DialogsActivity.this).resourceProvider);
                stealthModeAlert.setListener(new StealthModeAlert.Listener() { // from class: org.telegram.ui.DialogsActivity$25$$ExternalSyntheticLambda15
                    @Override // org.telegram.ui.Stories.StealthModeAlert.Listener
                    public final void onButtonClicked(boolean z) {
                        this.f$0.lambda$onUserLongPressed$9(view, z);
                    }
                });
                DialogsActivity.this.showDialog(stealthModeAlert);
            }
        }

        public /* synthetic */ void lambda$onUserLongPressed$9(View view, boolean z) {
            if (view instanceof DialogStoriesCell.StoryCell) {
                DialogsActivity.this.dialogStoriesCell.openStoryForCell((DialogStoriesCell.StoryCell) view);
                if (z) {
                    AndroidUtilities.runOnUIThread(new DialogsActivity$25$$ExternalSyntheticLambda16(), 500L);
                }
            }
        }

        public /* synthetic */ void lambda$onUserLongPressed$12(final View view) {
            StealthModeAlert stealthModeAlert = new StealthModeAlert(getContext(), 0.0f, 1, ((BaseFragment) DialogsActivity.this).resourceProvider);
            stealthModeAlert.setListener(new StealthModeAlert.Listener() { // from class: org.telegram.ui.DialogsActivity$25$$ExternalSyntheticLambda14
                @Override // org.telegram.ui.Stories.StealthModeAlert.Listener
                public final void onButtonClicked(boolean z) {
                    this.f$0.lambda$onUserLongPressed$11(view, z);
                }
            });
            DialogsActivity.this.showDialog(stealthModeAlert);
        }

        public /* synthetic */ void lambda$onUserLongPressed$11(View view, boolean z) {
            if (view instanceof DialogStoriesCell.StoryCell) {
                DialogsActivity.this.dialogStoriesCell.openStoryForCell((DialogStoriesCell.StoryCell) view);
                if (z) {
                    AndroidUtilities.runOnUIThread(new DialogsActivity$25$$ExternalSyntheticLambda16(), 500L);
                }
            }
        }

        public /* synthetic */ void lambda$onUserLongPressed$13(long j) {
            DialogsActivity.this.toggleArciveForStory(j);
        }

        public /* synthetic */ void lambda$onUserLongPressed$14(long j) {
            DialogsActivity.this.toggleArciveForStory(j);
        }

        public /* synthetic */ void lambda$onUserLongPressed$15(long j) {
            MediaDataController.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).removePeer(j);
            DialogsActivity.this.getMessagesController().getStoriesController().toggleHidden(j, true, false, true);
        }

        @Override // org.telegram.p035ui.Stories.DialogStoriesCell
        public void onMiniListClicked() {
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (dialogsActivity.hasOnlySlefStories && dialogsActivity.getStoriesController().hasOnlySelfStories()) {
                DialogsActivity.this.dialogStoriesCell.openSelfStories();
            } else {
                DialogsActivity.this.scrollToTop(true, true);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            return !((BaseFragment) DialogsActivity.this).actionBar.isActionModeShowed() && super.dispatchTouchEvent(motionEvent);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$26 */
    public class C553926 extends View {
        public C553926(Context context) {
            super(context);
        }

        @Override // android.view.View
        public void setAlpha(float f) {
            super.setAlpha(f);
            View view = DialogsActivity.this.fragmentView;
            if (view != null) {
                view.invalidate();
            }
        }

        @Override // android.view.View
        @SuppressLint({"ClickableViewAccessibility"})
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (getAlpha() == 0.0f) {
                return false;
            }
            return super.onTouchEvent(motionEvent);
        }
    }

    public /* synthetic */ void lambda$createView$27(View view) {
        finishPreviewFragment();
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$27 */
    public class C554027 extends RightSlidingDialogContainer {
        boolean anotherFragmentOpened;
        float fromScrollYProperty;
        ViewPage transitionPage;
        final /* synthetic */ ContentView val$contentView;
        final /* synthetic */ Context val$context;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C554027(Context context, ContentView contentView, Context context2) {
            super(context);
            contentView = contentView;
            context = context2;
        }

        @Override // org.telegram.p035ui.RightSlidingDialogContainer
        public boolean getOccupyStatusbar() {
            return ((BaseFragment) DialogsActivity.this).actionBar != null && ((BaseFragment) DialogsActivity.this).actionBar.getOccupyStatusBar();
        }

        @Override // org.telegram.p035ui.RightSlidingDialogContainer
        public void openAnimationStarted(boolean z) {
            DialogsActivity.this.rightFragmentTransitionInProgress = true;
            DialogsActivity.this.rightFragmentTransitionIsOpen = z;
            contentView.requestLayout();
            this.fromScrollYProperty = DialogsActivity.this.scrollYOffset;
            ViewPage viewPage = DialogsActivity.this.viewPages[0];
            this.transitionPage = viewPage;
            if (viewPage.animationSupportListView == null) {
                this.transitionPage.animationSupportListView = new BlurredRecyclerView(context) { // from class: org.telegram.ui.DialogsActivity.27.1
                    @Override // org.telegram.p035ui.Components.BlurredRecyclerView, org.telegram.p035ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
                    public void dispatchDraw(Canvas canvas) {
                    }

                    @Override // org.telegram.p035ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
                    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                        return false;
                    }

                    @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
                    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                        return false;
                    }

                    @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
                    public boolean onTouchEvent(MotionEvent motionEvent) {
                        return false;
                    }

                    public AnonymousClass1(Context context) {
                        super(context);
                    }

                    @Override // org.telegram.p035ui.Components.BlurredRecyclerView
                    public int measureBlurTopPadding() {
                        return AndroidUtilities.m1036dp(48.0f);
                    }
                };
                this.transitionPage.animationSupportListView.setLayoutManager(new LinearLayoutManager(context) { // from class: org.telegram.ui.DialogsActivity.27.2
                    final /* synthetic */ ViewPage val$page;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass2(Context context, ViewPage viewPage2) {
                        super(context);
                        viewPage = viewPage2;
                    }

                    @Override // androidx.recyclerview.widget.LinearLayoutManager
                    public int firstPosition() {
                        return (viewPage.dialogsType == 0 && DialogsActivity.this.hasHiddenArchive() && viewPage.archivePullViewState == 2) ? 1 : 0;
                    }
                });
                this.transitionPage.animationSupportDialogsAdapter = new DialogsAdapter(DialogsActivity.this, context, this.transitionPage.dialogsType, DialogsActivity.this.folderId, DialogsActivity.this.onlySelect, DialogsActivity.this.selectedDialogs, ((BaseFragment) DialogsActivity.this).currentAccount, DialogsActivity.this.requestPeerType);
                this.transitionPage.animationSupportDialogsAdapter.setIsTransitionSupport();
                this.transitionPage.animationSupportListView.setAdapter(this.transitionPage.animationSupportDialogsAdapter);
                ViewPage viewPage2 = this.transitionPage;
                viewPage2.addView(viewPage2.animationSupportListView);
            }
            if (!z) {
                DialogsActivity.this.invalidateScrollY = false;
                DialogsActivity.this.setScrollY(-r0.getMaxScrollYOffset());
            }
            this.transitionPage.listView.stopScroll();
            this.transitionPage.animationSupportDialogsAdapter.setDialogsType(this.transitionPage.dialogsType);
            this.transitionPage.dialogsAdapter.setCollapsedView(false, this.transitionPage.listView);
            this.transitionPage.dialogsAdapter.setDialogsListFrozen(true);
            this.transitionPage.animationSupportDialogsAdapter.setDialogsListFrozen(true);
            this.transitionPage.layoutManager.setNeedFixEndGap(false);
            DialogsActivity.this.setDialogsListFrozen(true);
            DialogsActivity.this.hideFloatingButton(this.anotherFragmentOpened);
            this.transitionPage.dialogsAdapter.notifyDataSetChanged();
            this.transitionPage.animationSupportDialogsAdapter.notifyDataSetChanged();
            float f = DialogsActivity.this.scrollYOffset;
            if (z) {
                f = -f;
            }
            ViewPage viewPage3 = this.transitionPage;
            viewPage3.listView.setAnimationSupportView(viewPage3.animationSupportListView, f, z, false);
            this.transitionPage.listView.setClipChildren(false);
            this.transitionPage.listView.stopScroll();
            DialogsActivity.this.checkUi_searchFieldHint();
            DialogsActivity.this.updateDialogsHint();
        }

        /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$27$1 */
        /* JADX INFO: loaded from: classes6.dex */
        public class AnonymousClass1 extends BlurredRecyclerView {
            @Override // org.telegram.p035ui.Components.BlurredRecyclerView, org.telegram.p035ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
            public void dispatchDraw(Canvas canvas) {
            }

            @Override // org.telegram.p035ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                return false;
            }

            @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                return false;
            }

            @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                return false;
            }

            public AnonymousClass1(Context context) {
                super(context);
            }

            @Override // org.telegram.p035ui.Components.BlurredRecyclerView
            public int measureBlurTopPadding() {
                return AndroidUtilities.m1036dp(48.0f);
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$27$2 */
        /* JADX INFO: loaded from: classes6.dex */
        public class AnonymousClass2 extends LinearLayoutManager {
            final /* synthetic */ ViewPage val$page;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(Context context, ViewPage viewPage2) {
                super(context);
                viewPage = viewPage2;
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager
            public int firstPosition() {
                return (viewPage.dialogsType == 0 && DialogsActivity.this.hasHiddenArchive() && viewPage.archivePullViewState == 2) ? 1 : 0;
            }
        }

        @Override // org.telegram.p035ui.RightSlidingDialogContainer
        public void openAnimationFinished(boolean z) {
            this.transitionPage.layoutManager.setNeedFixGap(true);
            this.transitionPage.dialogsAdapter.setCollapsedView(hasFragment(), this.transitionPage.listView);
            this.transitionPage.dialogsAdapter.setDialogsListFrozen(false);
            this.transitionPage.animationSupportDialogsAdapter.setDialogsListFrozen(false);
            DialogsActivity.this.setDialogsListFrozen(false);
            this.transitionPage.listView.setClipChildren(true);
            this.transitionPage.listView.invalidate();
            this.transitionPage.dialogsAdapter.notifyDataSetChanged();
            this.transitionPage.animationSupportDialogsAdapter.notifyDataSetChanged();
            this.transitionPage.listView.setAnimationSupportView(null, 0.0f, hasFragment(), z);
            DialogsActivity.this.rightFragmentTransitionInProgress = false;
            contentView.requestLayout();
            if (!hasFragment()) {
                DialogsActivity dialogsActivity = DialogsActivity.this;
                if (dialogsActivity.hasStories) {
                    dialogsActivity.invalidateScrollY = true;
                    DialogsActivity.this.fixScrollYAfterArchiveOpened = true;
                    View view = DialogsActivity.this.fragmentView;
                    if (view != null) {
                        view.invalidate();
                    }
                }
            }
            if (DialogsActivity.this.searchViewPager != null) {
                DialogsActivity.this.searchViewPager.updateTabs();
            }
            DialogsActivity.this.updateFilterTabs(false, true);
            DialogsActivity.this.checkUi_searchFieldHint();
            DialogsActivity.this.updateDialogsHint();
            if (hasFragment() || DialogsActivity.this.getParentLayout() == null) {
                return;
            }
            DialogsActivity.this.getParentLayout().updateTitleOverlay();
        }

        @Override // org.telegram.p035ui.RightSlidingDialogContainer
        public void setOpenProgress(float f) {
            boolean z = f > 0.0f;
            if (this.anotherFragmentOpened != z) {
                this.anotherFragmentOpened = z;
            }
            View view = DialogsActivity.this.fragmentView;
            if (view != null) {
                view.invalidate();
            }
            if (((BaseFragment) DialogsActivity.this).actionBar.getTitleTextView() != null) {
                ((BaseFragment) DialogsActivity.this).actionBar.getTitleTextView().setAlpha(1.0f - f);
                if (((BaseFragment) DialogsActivity.this).actionBar.getTitleTextView().getAlpha() > 0.0f) {
                    ((BaseFragment) DialogsActivity.this).actionBar.getTitleTextView().setVisibility(0);
                }
            }
            if (((BaseFragment) DialogsActivity.this).actionBar.getBackButton() != null) {
                ((BaseFragment) DialogsActivity.this).actionBar.getBackButton().setAlpha(f != 1.0f ? 1.0f : 0.0f);
            }
            if (DialogsActivity.this.folderId != 0) {
                Paint paint = DialogsActivity.this.actionBarDefaultPaint;
                DialogsActivity dialogsActivity = DialogsActivity.this;
                int i = Theme.key_windowBackgroundWhite;
                paint.setColor(ColorUtils.blendARGB(dialogsActivity.getThemedColor(i), DialogsActivity.this.getThemedColor(i), f));
            }
            ViewPage viewPage = this.transitionPage;
            if (viewPage != null) {
                viewPage.listView.setOpenRightFragmentProgress(f);
            }
            DialogsActivity.this.checkUi_menuItems();
            DialogsActivity.this.checkUi_topPanelVisible();
            DialogsActivity.this.checkUi_filterTabsVisible();
            DialogsActivity.this.checkUi_searchFieldVisibility();
            if (DialogsActivity.this.viewPages[0] != null && DialogsActivity.this.viewPages[0].listView != null) {
                DialogsActivity.this.viewPages[0].listView.requestLayout();
            }
            View view2 = DialogsActivity.this.fragmentView;
            if (view2 != null) {
                view2.invalidate();
            }
        }
    }

    public void setStoriesOvercroll(ViewPage viewPage, float f) {
        if (this.storiesOverscroll == f) {
            return;
        }
        this.storiesOverscroll = f;
        if (f == 0.0f) {
            this.storiesOverscrollCalled = false;
        }
        this.dialogStoriesCell.setOverscroll(f);
        viewPage.listView.setViewsOffset(f);
        viewPage.listView.setOverScrollMode(f != 0.0f ? 2 : 0);
        this.fragmentView.invalidate();
        if (f <= AndroidUtilities.m1036dp(90.0f) || this.storiesOverscrollCalled || !this.dialogStoriesCell.openOverscrollSelectedStory()) {
            return;
        }
        this.storiesOverscrollCalled = true;
        getOrCreateStoryViewer().doOnAnimationReady(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda142
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setStoriesOvercroll$28();
            }
        });
    }

    public /* synthetic */ void lambda$setStoriesOvercroll$28() {
        this.fragmentView.dispatchTouchEvent(AndroidUtilities.emptyMotionEvent());
    }

    public void toggleArciveForStory(final long j) {
        final boolean z = !isArchive();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda157
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$toggleArciveForStory$31(j, z);
            }
        }, 200L);
    }

    public /* synthetic */ void lambda$toggleArciveForStory$31(final long j, final boolean z) {
        String name;
        TLRPC.User user;
        SpannableStringBuilder spannableStringBuilderReplaceTags;
        getMessagesController().getStoriesController().toggleHidden(j, z, false, true);
        BulletinFactory.UndoObject undoObject = new BulletinFactory.UndoObject();
        undoObject.onUndo = new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda162
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$toggleArciveForStory$29(j, z);
            }
        };
        undoObject.onAction = new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda163
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$toggleArciveForStory$30(j, z);
            }
        };
        if (j >= 0) {
            TLRPC.User user2 = getMessagesController().getUser(Long.valueOf(j));
            name = ContactsController.formatName(user2.first_name, null, 15);
            user = user2;
        } else {
            TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(-j));
            name = chat.title;
            user = chat;
        }
        if (isArchive()) {
            spannableStringBuilderReplaceTags = AndroidUtilities.replaceTags(LocaleController.formatString("StoriesMovedToDialogs", C2797R.string.StoriesMovedToDialogs, name));
        } else {
            spannableStringBuilderReplaceTags = AndroidUtilities.replaceTags(LocaleController.formatString("StoriesMovedToContacts", C2797R.string.StoriesMovedToContacts, ContactsController.formatName(name, null, 15)));
        }
        this.storiesBulletin = BulletinFactory.global().createUsersBulletin(Collections.singletonList(user), spannableStringBuilderReplaceTags, null, undoObject).show();
    }

    public /* synthetic */ void lambda$toggleArciveForStory$29(long j, boolean z) {
        getMessagesController().getStoriesController().toggleHidden(j, !z, false, true);
    }

    public /* synthetic */ void lambda$toggleArciveForStory$30(long j, boolean z) {
        getMessagesController().getStoriesController().toggleHidden(j, z, true, true);
    }

    public boolean checkAutoscrollToStories(ViewPage viewPage) {
        boolean z = false;
        if (!this.rightSlidingDialogContainer.hasFragment()) {
            int i = (int) (-this.scrollYOffset);
            int maxScrollYOffset = getMaxScrollYOffset();
            int maxScrollYOffsetWithoutSearch = getMaxScrollYOffsetWithoutSearch();
            if (i != 0 && i != maxScrollYOffset && i != maxScrollYOffsetWithoutSearch) {
                if (!viewPage.listView.canScrollVertically(-1)) {
                    return false;
                }
                z = true;
                if (maxScrollYOffsetWithoutSearch < i && i < maxScrollYOffset) {
                    int searchFieldHeight = getSearchFieldHeight();
                    int i2 = i - maxScrollYOffsetWithoutSearch;
                    int i3 = searchFieldHeight / 2;
                    RecyclerListViewScroller recyclerListViewScroller = viewPage.scroller;
                    if (i2 < i3) {
                        recyclerListViewScroller.smoothScrollBy(-i2);
                    } else {
                        recyclerListViewScroller.smoothScrollBy(searchFieldHeight - i2);
                    }
                    return true;
                }
                float fClamp = this.progressToActionMode != 1.0f ? Utilities.clamp((-this.scrollYOffset) / AndroidUtilities.m1036dp(81.0f), 1.0f, 0.0f) : 1.0f;
                float f = this.dialogStoriesCell.f1785K;
                RecyclerListViewScroller recyclerListViewScroller2 = viewPage.scroller;
                if (fClamp < f) {
                    recyclerListViewScroller2.smoothScrollBy(-i);
                } else {
                    recyclerListViewScroller2.smoothScrollBy(maxScrollYOffsetWithoutSearch - i);
                }
            }
        }
        return z;
    }

    public int getMaxScrollYOffsetWithoutSearch() {
        if (this.hasStories) {
            return AndroidUtilities.m1036dp(81.0f);
        }
        return 0;
    }

    public int getMaxScrollYOffset() {
        if (this.hasStories) {
            return AndroidUtilities.m1036dp(81.0f) + getSearchFieldHeight();
        }
        return getSearchFieldHeight();
    }

    private boolean isCacheHintVisible() {
        if (this.cacheSize != null && this.deviceSize != null) {
            if (r0.longValue() / this.deviceSize.longValue() < 0.3f) {
                clearCacheHintVisible();
                return false;
            }
            if (System.currentTimeMillis() > MessagesController.getGlobalMainSettings().getLong("cache_hint_showafter", 0L)) {
                return true;
            }
        }
        return false;
    }

    private void resetCacheHintVisible() {
        SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
        long j = globalMainSettings.getLong("cache_hint_period", 604800000L);
        if (j <= 604800000) {
            j = 2592000000L;
        }
        globalMainSettings.edit().putLong("cache_hint_showafter", System.currentTimeMillis() + j).putLong("cache_hint_period", j).apply();
    }

    private void clearCacheHintVisible() {
        MessagesController.getGlobalMainSettings().edit().remove("cache_hint_showafter").remove("cache_hint_period").apply();
    }

    public void showSelectStatusDialog() {
        int iCenterX;
        int i;
        if (this.selectAnimatedEmojiDialog != null || SharedConfig.appLocked) {
            return;
        }
        if (!this.hasStories || this.dialogStoriesCell.isExpanded()) {
            SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] selectAnimatedEmojiDialogWindowArr = new SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[1];
            TLRPC.User currentUser = UserConfig.getInstance(UserConfig.selectedAccount).getCurrentUser();
            SimpleTextView titleTextView = this.actionBar.getTitleTextView();
            if (titleTextView == null || titleTextView.getRightDrawable() == null) {
                iCenterX = 0;
                i = 0;
            } else {
                this.statusDrawable.play();
                this.statusDrawable.getDrawable();
                Rect rect = AndroidUtilities.rectTmp2;
                rect.set(titleTextView.getRightDrawable().getBounds());
                rect.offset((int) titleTextView.getX(), (int) titleTextView.getY());
                int iM1036dp = (-(this.actionBar.getHeight() - rect.centerY())) - AndroidUtilities.m1036dp(16.0f);
                iCenterX = (rect.centerX() - AndroidUtilities.m1036dp(16.0f)) + AndroidUtilities.m1036dp(4.0f);
                AnimatedStatusView animatedStatusView = this.animatedStatusView;
                if (animatedStatusView != null) {
                    animatedStatusView.translate(rect.centerX(), rect.centerY());
                }
                i = iM1036dp;
            }
            C554128 c554128 = new SelectAnimatedEmojiDialog(this, getContext(), true, Integer.valueOf(iCenterX), 0, getResourceProvider()) { // from class: org.telegram.ui.DialogsActivity.28
                final /* synthetic */ SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] val$popup;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C554128(BaseFragment this, Context context, boolean z, Integer num, int i2, Theme.ResourcesProvider resourcesProvider, SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] selectAnimatedEmojiDialogWindowArr2) {
                    super(this, context, z, num, i2, resourcesProvider);
                    selectAnimatedEmojiDialogWindowArr = selectAnimatedEmojiDialogWindowArr2;
                }

                @Override // org.telegram.p035ui.SelectAnimatedEmojiDialog
                public boolean willApplyEmoji(View view, Long l, TLRPC.Document document, TL_stars.TL_starGiftUnique tL_starGiftUnique, Integer num) {
                    return tL_starGiftUnique == null || StarsController.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).findUserStarGift(tL_starGiftUnique.f1443id) == null || MessagesController.getGlobalMainSettings().getInt("statusgiftpage", 0) >= 2;
                }

                @Override // org.telegram.p035ui.SelectAnimatedEmojiDialog
                public void onEmojiSelected(View view, Long l, TLRPC.Document document, TL_stars.TL_starGiftUnique tL_starGiftUnique, Integer num) {
                    TLRPC.EmojiStatus emojiStatus;
                    TLRPC.EmojiStatus tL_emojiStatusEmpty;
                    if (l == null) {
                        tL_emojiStatusEmpty = new TLRPC.TL_emojiStatusEmpty();
                    } else {
                        if (tL_starGiftUnique != null) {
                            TL_stars.SavedStarGift savedStarGiftFindUserStarGift = StarsController.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).findUserStarGift(tL_starGiftUnique.f1443id);
                            if (savedStarGiftFindUserStarGift != null && MessagesController.getGlobalMainSettings().getInt("statusgiftpage", 0) < 2) {
                                MessagesController.getGlobalMainSettings().edit().putInt("statusgiftpage", MessagesController.getGlobalMainSettings().getInt("statusgiftpage", 0) + 1).apply();
                                new StarGiftSheet(getContext(), ((BaseFragment) DialogsActivity.this).currentAccount, UserConfig.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getClientUserId(), ((BaseFragment) DialogsActivity.this).resourceProvider).set(savedStarGiftFindUserStarGift, (StarsController.IGiftsList) null).setupWearPage().show();
                                if (selectAnimatedEmojiDialogWindowArr[0] != null) {
                                    DialogsActivity.this.selectAnimatedEmojiDialog = null;
                                    selectAnimatedEmojiDialogWindowArr[0].dismiss();
                                    return;
                                }
                                return;
                            }
                            TLRPC.TL_inputEmojiStatusCollectible tL_inputEmojiStatusCollectible = new TLRPC.TL_inputEmojiStatusCollectible();
                            tL_inputEmojiStatusCollectible.collectible_id = tL_starGiftUnique.f1443id;
                            emojiStatus = tL_inputEmojiStatusCollectible;
                            if (num != null) {
                                tL_inputEmojiStatusCollectible.flags |= 1;
                                tL_inputEmojiStatusCollectible.until = num.intValue();
                                emojiStatus = tL_inputEmojiStatusCollectible;
                            }
                        } else {
                            TLRPC.TL_emojiStatus tL_emojiStatus = new TLRPC.TL_emojiStatus();
                            tL_emojiStatus.document_id = l.longValue();
                            emojiStatus = tL_emojiStatus;
                            if (num != null) {
                                tL_emojiStatus.flags |= 1;
                                tL_emojiStatus.until = num.intValue();
                                emojiStatus = tL_emojiStatus;
                            }
                        }
                        tL_emojiStatusEmpty = emojiStatus;
                    }
                    DialogsActivity.this.getMessagesController().updateEmojiStatus(tL_emojiStatusEmpty, tL_starGiftUnique);
                    if (l != null) {
                        DialogsActivity.this.animatedStatusView.animateChange(ReactionsLayoutInBubble.VisibleReaction.fromCustomEmoji(l));
                    }
                    if (selectAnimatedEmojiDialogWindowArr[0] != null) {
                        DialogsActivity.this.selectAnimatedEmojiDialog = null;
                        selectAnimatedEmojiDialogWindowArr[0].dismiss();
                    }
                }
            };
            if (currentUser != null && DialogObject.getEmojiStatusUntil(currentUser.emoji_status) > 0) {
                c554128.setExpireDateHint(DialogObject.getEmojiStatusUntil(currentUser.emoji_status));
            }
            Long l = this.statusDrawableGiftId;
            if (l != null) {
                c554128.setSelected(l);
            } else {
                c554128.setSelected(this.statusDrawable.getDrawable() instanceof AnimatedEmojiDrawable ? Long.valueOf(((AnimatedEmojiDrawable) this.statusDrawable.getDrawable()).getDocumentId()) : null);
            }
            c554128.setSaveState(1);
            c554128.setScrimDrawable(this.statusDrawable, titleTextView);
            C554229 c554229 = new SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow(c554128, -2, -2) { // from class: org.telegram.ui.DialogsActivity.29
                public C554229(View c5541282, int i2, int i3) {
                    super(c5541282, i2, i3);
                }

                @Override // org.telegram.ui.SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow, android.widget.PopupWindow
                public void dismiss() {
                    super.dismiss();
                    DialogsActivity.this.selectAnimatedEmojiDialog = null;
                }
            };
            this.selectAnimatedEmojiDialog = c554229;
            selectAnimatedEmojiDialogWindowArr2[0] = c554229;
            c554229.showAsDropDown(this.actionBar, AndroidUtilities.m1036dp(16.0f), i, 48);
            selectAnimatedEmojiDialogWindowArr2[0].dimBehind();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$28 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C554128 extends SelectAnimatedEmojiDialog {
        final /* synthetic */ SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] val$popup;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C554128(DialogsActivity this, Context context, boolean z, Integer num, int i2, Theme.ResourcesProvider resourcesProvider, SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] selectAnimatedEmojiDialogWindowArr2) {
            super(this, context, z, num, i2, resourcesProvider);
            selectAnimatedEmojiDialogWindowArr = selectAnimatedEmojiDialogWindowArr2;
        }

        @Override // org.telegram.p035ui.SelectAnimatedEmojiDialog
        public boolean willApplyEmoji(View view, Long l, TLRPC.Document document, TL_stars.TL_starGiftUnique tL_starGiftUnique, Integer num) {
            return tL_starGiftUnique == null || StarsController.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).findUserStarGift(tL_starGiftUnique.f1443id) == null || MessagesController.getGlobalMainSettings().getInt("statusgiftpage", 0) >= 2;
        }

        @Override // org.telegram.p035ui.SelectAnimatedEmojiDialog
        public void onEmojiSelected(View view, Long l, TLRPC.Document document, TL_stars.TL_starGiftUnique tL_starGiftUnique, Integer num) {
            TLRPC.EmojiStatus emojiStatus;
            TLRPC.EmojiStatus tL_emojiStatusEmpty;
            if (l == null) {
                tL_emojiStatusEmpty = new TLRPC.TL_emojiStatusEmpty();
            } else {
                if (tL_starGiftUnique != null) {
                    TL_stars.SavedStarGift savedStarGiftFindUserStarGift = StarsController.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).findUserStarGift(tL_starGiftUnique.f1443id);
                    if (savedStarGiftFindUserStarGift != null && MessagesController.getGlobalMainSettings().getInt("statusgiftpage", 0) < 2) {
                        MessagesController.getGlobalMainSettings().edit().putInt("statusgiftpage", MessagesController.getGlobalMainSettings().getInt("statusgiftpage", 0) + 1).apply();
                        new StarGiftSheet(getContext(), ((BaseFragment) DialogsActivity.this).currentAccount, UserConfig.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getClientUserId(), ((BaseFragment) DialogsActivity.this).resourceProvider).set(savedStarGiftFindUserStarGift, (StarsController.IGiftsList) null).setupWearPage().show();
                        if (selectAnimatedEmojiDialogWindowArr[0] != null) {
                            DialogsActivity.this.selectAnimatedEmojiDialog = null;
                            selectAnimatedEmojiDialogWindowArr[0].dismiss();
                            return;
                        }
                        return;
                    }
                    TLRPC.TL_inputEmojiStatusCollectible tL_inputEmojiStatusCollectible = new TLRPC.TL_inputEmojiStatusCollectible();
                    tL_inputEmojiStatusCollectible.collectible_id = tL_starGiftUnique.f1443id;
                    emojiStatus = tL_inputEmojiStatusCollectible;
                    if (num != null) {
                        tL_inputEmojiStatusCollectible.flags |= 1;
                        tL_inputEmojiStatusCollectible.until = num.intValue();
                        emojiStatus = tL_inputEmojiStatusCollectible;
                    }
                } else {
                    TLRPC.TL_emojiStatus tL_emojiStatus = new TLRPC.TL_emojiStatus();
                    tL_emojiStatus.document_id = l.longValue();
                    emojiStatus = tL_emojiStatus;
                    if (num != null) {
                        tL_emojiStatus.flags |= 1;
                        tL_emojiStatus.until = num.intValue();
                        emojiStatus = tL_emojiStatus;
                    }
                }
                tL_emojiStatusEmpty = emojiStatus;
            }
            DialogsActivity.this.getMessagesController().updateEmojiStatus(tL_emojiStatusEmpty, tL_starGiftUnique);
            if (l != null) {
                DialogsActivity.this.animatedStatusView.animateChange(ReactionsLayoutInBubble.VisibleReaction.fromCustomEmoji(l));
            }
            if (selectAnimatedEmojiDialogWindowArr[0] != null) {
                DialogsActivity.this.selectAnimatedEmojiDialog = null;
                selectAnimatedEmojiDialogWindowArr[0].dismiss();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$29 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C554229 extends SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow {
        public C554229(View c5541282, int i2, int i3) {
            super(c5541282, i2, i3);
        }

        @Override // org.telegram.ui.SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow, android.widget.PopupWindow
        public void dismiss() {
            super.dismiss();
            DialogsActivity.this.selectAnimatedEmojiDialog = null;
        }
    }

    public void showPremiumBlockedToast(View view, long j) {
        String userName;
        Bulletin bulletinCreateSimpleBulletin;
        int i = -this.shiftDp;
        this.shiftDp = i;
        AndroidUtilities.shakeViewSpring(view, i);
        BotWebViewVibrationEffect.APP_ERROR.vibrate();
        if (j < 0) {
            userName = _UrlKt.FRAGMENT_ENCODE_SET;
        } else {
            userName = UserObject.getUserName(MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(j)));
        }
        if (getMessagesController().premiumFeaturesBlocked()) {
            bulletinCreateSimpleBulletin = BulletinFactory.m1143of(this).createSimpleBulletin(C2797R.raw.star_premium_2, AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.UserBlockedNonPremium, userName)));
        } else {
            bulletinCreateSimpleBulletin = BulletinFactory.m1143of(this).createSimpleBulletin(C2797R.raw.star_premium_2, AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.UserBlockedNonPremium, userName)), LocaleController.getString(C2797R.string.UserBlockedNonPremiumButton), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda145
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showPremiumBlockedToast$32();
                }
            });
        }
        bulletinCreateSimpleBulletin.show();
    }

    public /* synthetic */ void lambda$showPremiumBlockedToast$32() {
        if (LaunchActivity.getLastFragment() != null) {
            presentFragment(new PremiumPreviewFragment("noncontacts"));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:136:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0385  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateDialogsHint() {
        /*
            Method dump skipped, instruction units count: 996
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.updateDialogsHint():void");
    }

    public /* synthetic */ void lambda$updateDialogsHint$33(View view) {
        AccountFrozenAlert.show(getContext(), this.currentAccount, getResourceProvider());
    }

    public /* synthetic */ void lambda$updateDialogsHint$34(View view) {
        PasskeysActivity.showLearnSheet(getContext(), this.currentAccount, this.resourceProvider, true);
    }

    public /* synthetic */ void lambda$updateDialogsHint$35(View view) {
        MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, "SETUP_PASSKEY");
        updateDialogsHint();
    }

    public /* synthetic */ void lambda$updateDialogsHint$38(TLRPC.TL_pendingSuggestion tL_pendingSuggestion, View view) {
        Browser.openUrl(getContext(), tL_pendingSuggestion.url);
    }

    public /* synthetic */ void lambda$updateDialogsHint$39(TLRPC.TL_pendingSuggestion tL_pendingSuggestion, View view) {
        MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, tL_pendingSuggestion.suggestion);
        updateDialogsHint();
    }

    public /* synthetic */ void lambda$updateDialogsHint$43(BirthdayController.BirthdayState birthdayState, View view) {
        if (birthdayState != null && birthdayState.today.size() == 1) {
            showDialog(new GiftSheet(getContext(), this.currentAccount, birthdayState.today.get(0).f1407id, null, null).setBirthday());
        } else {
            UserSelectorBottomSheet.open(0L, birthdayState);
        }
    }

    public /* synthetic */ void lambda$updateDialogsHint$44(View view) {
        BirthdayController.getInstance(this.currentAccount).hide();
        MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, "BIRTHDAY_CONTACTS_TODAY");
        updateDialogsHint();
        BulletinFactory.m1143of(this).createSimpleBulletin(C2797R.raw.gift, LocaleController.getString(C2797R.string.BoostingPremiumChristmasToast), 4).setDuration(5000).show();
    }

    public /* synthetic */ void lambda$updateDialogsHint$49(View view) {
        showDialog(AlertsCreator.createBirthdayPickerDialog(getContext(), LocaleController.getString(C2797R.string.EditProfileBirthdayTitle), LocaleController.getString(C2797R.string.EditProfileBirthdayButton), null, new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda97
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$updateDialogsHint$47((TL_account.TL_birthday) obj);
            }
        }, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda98
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateDialogsHint$48();
            }
        }, false, false, getResourceProvider()).create());
    }

    public /* synthetic */ void lambda$updateDialogsHint$47(TL_account.TL_birthday tL_birthday) {
        TL_account.updateBirthday updatebirthday = new TL_account.updateBirthday();
        updatebirthday.flags |= 1;
        updatebirthday.birthday = tL_birthday;
        final TLRPC.UserFull userFull = getMessagesController().getUserFull(getUserConfig().getClientUserId());
        final TL_account.TL_birthday tL_birthday2 = userFull != null ? userFull.birthday : null;
        if (userFull != null) {
            userFull.flags2 |= 32;
            userFull.birthday = tL_birthday;
        }
        getMessagesController().invalidateContentSettings();
        getConnectionsManager().sendRequest(updatebirthday, new RequestDelegate() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda134
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$updateDialogsHint$46(userFull, tL_birthday2, tLObject, tL_error);
            }
        }, 1024);
        MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, "BIRTHDAY_SETUP");
        updateDialogsHint();
    }

    public /* synthetic */ void lambda$updateDialogsHint$46(final TLRPC.UserFull userFull, final TL_account.TL_birthday tL_birthday, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda148
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateDialogsHint$45(tLObject, userFull, tL_birthday, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$updateDialogsHint$45(TLObject tLObject, TLRPC.UserFull userFull, TL_account.TL_birthday tL_birthday, TLRPC.TL_error tL_error) {
        String str;
        if (tLObject instanceof TLRPC.TL_boolTrue) {
            BulletinFactory.m1143of(this).createSimpleBulletin(C2797R.raw.gift, LocaleController.getString(C2797R.string.PrivacyBirthdaySetDone), LocaleController.getString(C2797R.string.PrivacyBirthdaySetDoneInfo)).setDuration(5000).show();
            return;
        }
        if (userFull != null) {
            int i = userFull.flags2;
            if (tL_birthday == null) {
                userFull.flags2 = i & (-33);
            } else {
                userFull.flags2 = i | 32;
            }
            userFull.birthday = tL_birthday;
            getMessagesStorage().updateUserInfo(userFull, false);
        }
        if (tL_error != null && (str = tL_error.text) != null && str.startsWith("FLOOD_WAIT_")) {
            if (getContext() != null) {
                showDialog(new AlertDialog.Builder(getContext(), this.resourceProvider).setTitle(LocaleController.getString(C2797R.string.PrivacyBirthdayTooOftenTitle)).setMessage(LocaleController.getString(C2797R.string.PrivacyBirthdayTooOftenMessage)).setPositiveButton(LocaleController.getString(C2797R.string.f1162OK), null).create());
                return;
            }
            return;
        }
        BulletinFactory.m1143of(this).createSimpleBulletin(C2797R.raw.error, LocaleController.getString(C2797R.string.UnknownError)).show();
    }

    public /* synthetic */ void lambda$updateDialogsHint$48() {
        BaseFragment.BottomSheetParams bottomSheetParams = new BaseFragment.BottomSheetParams();
        bottomSheetParams.transitionFromLeft = true;
        bottomSheetParams.allowNestedScroll = false;
        showAsSheet(new PrivacyControlActivity(11), bottomSheetParams);
    }

    public /* synthetic */ void lambda$updateDialogsHint$51(View view) {
        MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, "BIRTHDAY_SETUP");
        updateDialogsHint();
        BulletinFactory.m1143of(this).createSimpleBulletin(C2797R.raw.chats_infotip, LocaleController.getString(C2797R.string.BirthdaySetupLater), LocaleController.getString(C2797R.string.Settings), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda105
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateDialogsHint$50();
            }
        }).setDuration(5000).show();
    }

    public /* synthetic */ void lambda$updateDialogsHint$50() {
        presentFragment(new UserInfoActivity());
    }

    public /* synthetic */ void lambda$updateDialogsHint$59(View view) {
        presentFragment(new CacheControlActivity());
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda92
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateDialogsHint$58();
            }
        }, 250L);
    }

    public /* synthetic */ void lambda$updateDialogsHint$58() {
        resetCacheHintVisible();
        updateDialogsHint();
    }

    public /* synthetic */ void lambda$updateDialogsHint$60(View view) {
        openSetAvatar();
    }

    public /* synthetic */ void lambda$updateDialogsHint$61(View view) {
        MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, "USERPIC_SETUP");
        updateDialogsHint();
    }

    public static /* synthetic */ void $r8$lambda$7B3VTLorbA3LvU84LAud7khUTlQ(String str, View view) {
        ApplicationLoader applicationLoader = ApplicationLoader.applicationLoaderInstance;
        if (applicationLoader != null) {
            applicationLoader.onSuggestionClick(str);
        }
    }

    public /* synthetic */ void lambda$updateDialogsHint$64(final String str, View view) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda88
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateDialogsHint$63(str);
            }
        }, 250L);
    }

    public /* synthetic */ void lambda$updateDialogsHint$63(String str) {
        MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, str);
        updateDialogsHint();
    }

    private void checkUnconfirmedAuthHintCellVisibility() {
        RightSlidingDialogContainer rightSlidingDialogContainer;
        if (this.fragmentView == null || this.topPanelLayout == null) {
            return;
        }
        boolean z = !isInPreviewMode() && this.folderId == 0 && this.initialDialogsType == 0 && !getMessagesController().getUnconfirmedAuthController().auths.isEmpty() && ((rightSlidingDialogContainer = this.rightSlidingDialogContainer) == null || !rightSlidingDialogContainer.hasFragment()) && !this.animatorSearchVisible.getValue();
        if (z) {
            if (this.authHintCell == null) {
                UnconfirmedAuthHintCell unconfirmedAuthHintCell = new UnconfirmedAuthHintCell(getContext());
                this.authHintCell = unconfirmedAuthHintCell;
                this.topPanelLayout.addView(unconfirmedAuthHintCell);
            }
            this.authHintCell.set(this, this.currentAccount);
        }
        UnconfirmedAuthHintCell unconfirmedAuthHintCell2 = this.authHintCell;
        if (unconfirmedAuthHintCell2 != null) {
            this.topPanelLayout.setViewVisible(unconfirmedAuthHintCell2, z);
        }
    }

    private void checkActiveGiftAuctionsHintCellVisibility() {
        RightSlidingDialogContainer rightSlidingDialogContainer;
        if (this.fragmentView == null || this.topPanelLayout == null) {
            return;
        }
        boolean z = !isInPreviewMode() && this.folderId == 0 && this.initialDialogsType == 0 && getGiftAuctionsController().hasActiveAuctions() && ((rightSlidingDialogContainer = this.rightSlidingDialogContainer) == null || !rightSlidingDialogContainer.hasFragment()) && !this.animatorSearchVisible.getValue();
        if (z && this.activeGiftAuctionsHintCell == null) {
            ActiveGiftAuctionsHintCell activeGiftAuctionsHintCell = new ActiveGiftAuctionsHintCell(getContext(), this.currentAccount);
            this.activeGiftAuctionsHintCell = activeGiftAuctionsHintCell;
            this.topPanelLayout.addView(activeGiftAuctionsHintCell);
        }
        ActiveGiftAuctionsHintCell activeGiftAuctionsHintCell2 = this.activeGiftAuctionsHintCell;
        if (activeGiftAuctionsHintCell2 != null) {
            this.topPanelLayout.setViewVisible(activeGiftAuctionsHintCell2, z);
        }
    }

    public void createGroupForThis() {
        long[] jArr;
        final AlertDialog alertDialog = new AlertDialog(getContext(), 3);
        TLRPC.RequestPeerType requestPeerType = this.requestPeerType;
        if (requestPeerType instanceof TLRPC.TL_requestPeerTypeBroadcast) {
            Bundle bundle = new Bundle();
            bundle.putInt("step", 0);
            Boolean bool = this.requestPeerType.has_username;
            if (bool != null) {
                bundle.putBoolean("forcePublic", bool.booleanValue());
            }
            final ChannelCreateActivity channelCreateActivity = new ChannelCreateActivity(bundle);
            channelCreateActivity.setOnFinishListener(new Utilities.Callback2() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda146
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    this.f$0.lambda$createGroupForThis$74(channelCreateActivity, alertDialog, (BaseFragment) obj, (Long) obj2);
                }
            });
            presentFragment(channelCreateActivity);
            return;
        }
        if (requestPeerType instanceof TLRPC.TL_requestPeerTypeChat) {
            Bundle bundle2 = new Bundle();
            Boolean bool2 = this.requestPeerType.bot_participant;
            if (bool2 != null && bool2.booleanValue()) {
                jArr = new long[]{getUserConfig().getClientUserId(), this.requestPeerBotId};
            } else {
                jArr = new long[]{getUserConfig().getClientUserId()};
            }
            bundle2.putLongArray("result", jArr);
            Boolean bool3 = this.requestPeerType.forum;
            bundle2.putInt("chatType", (bool3 == null || !bool3.booleanValue()) ? 4 : 5);
            bundle2.putBoolean("canToggleTopics", false);
            GroupCreateFinalActivity groupCreateFinalActivity = new GroupCreateFinalActivity(bundle2);
            groupCreateFinalActivity.setDelegate(new C554430(alertDialog));
            presentFragment(groupCreateFinalActivity);
        }
    }

    public /* synthetic */ void lambda$createGroupForThis$74(final ChannelCreateActivity channelCreateActivity, final AlertDialog alertDialog, final BaseFragment baseFragment, final Long l) {
        Utilities.doCallbacks(new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda152
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$createGroupForThis$66(l, channelCreateActivity, baseFragment, (Runnable) obj);
            }
        }, new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda153
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$createGroupForThis$68(alertDialog, l, (Runnable) obj);
            }
        }, new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda154
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$createGroupForThis$70(l, (Runnable) obj);
            }
        }, new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda155
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$createGroupForThis$72(l, (Runnable) obj);
            }
        }, new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda156
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$createGroupForThis$73(alertDialog, l, channelCreateActivity, baseFragment, (Runnable) obj);
            }
        });
    }

    public /* synthetic */ void lambda$createGroupForThis$66(Long l, final ChannelCreateActivity channelCreateActivity, final BaseFragment baseFragment, Runnable runnable) {
        showSendToBotAlert(getMessagesController().getChat(l), runnable, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda159
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createGroupForThis$65(channelCreateActivity, baseFragment);
            }
        });
    }

    public /* synthetic */ void lambda$createGroupForThis$65(ChannelCreateActivity channelCreateActivity, BaseFragment baseFragment) {
        removeSelfFromStack();
        channelCreateActivity.removeSelfFromStack();
        baseFragment.finishFragment();
    }

    public /* synthetic */ void lambda$createGroupForThis$68(AlertDialog alertDialog, Long l, final Runnable runnable) {
        alertDialog.showDelayed(150L);
        Boolean bool = this.requestPeerType.bot_participant;
        if (bool != null && bool.booleanValue()) {
            getMessagesController().addUserToChat(l.longValue(), getMessagesController().getUser(Long.valueOf(this.requestPeerBotId)), 0, null, this, false, runnable, new MessagesController.ErrorDelegate() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda160
                @Override // org.telegram.messenger.MessagesController.ErrorDelegate
                public final boolean run(TLRPC.TL_error tL_error) {
                    return DialogsActivity.$r8$lambda$F2enXqoviRnC7NggrkrlPJpaqCo(runnable, tL_error);
                }
            });
        } else {
            runnable.run();
        }
    }

    public static /* synthetic */ boolean $r8$lambda$F2enXqoviRnC7NggrkrlPJpaqCo(Runnable runnable, TLRPC.TL_error tL_error) {
        runnable.run();
        return true;
    }

    public /* synthetic */ void lambda$createGroupForThis$70(Long l, final Runnable runnable) {
        if (this.requestPeerType.bot_admin_rights != null) {
            TLRPC.User user = getMessagesController().getUser(Long.valueOf(this.requestPeerBotId));
            MessagesController messagesController = getMessagesController();
            long jLongValue = l.longValue();
            TLRPC.RequestPeerType requestPeerType = this.requestPeerType;
            TLRPC.TL_chatAdminRights tL_chatAdminRights = requestPeerType.bot_admin_rights;
            Boolean bool = requestPeerType.bot_participant;
            messagesController.setUserAdminRole(jLongValue, user, tL_chatAdminRights, null, false, this, bool == null || !bool.booleanValue(), true, null, runnable, new MessagesController.ErrorDelegate() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda158
                @Override // org.telegram.messenger.MessagesController.ErrorDelegate
                public final boolean run(TLRPC.TL_error tL_error) {
                    return DialogsActivity.$r8$lambda$vD6T_cw5rdBb3YjShlHoRxZA0yo(runnable, tL_error);
                }
            });
            return;
        }
        runnable.run();
    }

    public static /* synthetic */ boolean $r8$lambda$vD6T_cw5rdBb3YjShlHoRxZA0yo(Runnable runnable, TLRPC.TL_error tL_error) {
        runnable.run();
        return true;
    }

    public /* synthetic */ void lambda$createGroupForThis$72(Long l, final Runnable runnable) {
        if (this.requestPeerType.user_admin_rights != null) {
            getMessagesController().setUserAdminRole(l.longValue(), getAccountInstance().getUserConfig().getCurrentUser(), ChatRightsEditActivity.rightsOR(getMessagesController().getChat(l).admin_rights, this.requestPeerType.user_admin_rights), null, true, this, false, true, null, runnable, new MessagesController.ErrorDelegate() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda161
                @Override // org.telegram.messenger.MessagesController.ErrorDelegate
                public final boolean run(TLRPC.TL_error tL_error) {
                    return DialogsActivity.$r8$lambda$Kcxx0VcADvxHqrqIKldlovS9Zsc(runnable, tL_error);
                }
            });
        } else {
            runnable.run();
        }
    }

    public static /* synthetic */ boolean $r8$lambda$Kcxx0VcADvxHqrqIKldlovS9Zsc(Runnable runnable, TLRPC.TL_error tL_error) {
        runnable.run();
        return true;
    }

    public /* synthetic */ void lambda$createGroupForThis$73(AlertDialog alertDialog, Long l, ChannelCreateActivity channelCreateActivity, BaseFragment baseFragment, Runnable runnable) {
        alertDialog.dismiss();
        getMessagesController().loadChannelParticipants(l);
        DialogsActivityDelegate dialogsActivityDelegate = this.delegate;
        removeSelfFromStack();
        channelCreateActivity.removeSelfFromStack();
        baseFragment.finishFragment();
        if (dialogsActivityDelegate != null) {
            ArrayList<MessagesStorage.TopicKey> arrayList = new ArrayList<>();
            arrayList.add(MessagesStorage.TopicKey.m1066of(-l.longValue(), 0L));
            dialogsActivityDelegate.didSelectDialogs(this, arrayList, null, false, this.notify, this.scheduleDate, this.scheduleRepeatPeriod, null);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$30 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C554430 implements GroupCreateFinalActivity.GroupCreateFinalActivityDelegate {
        final /* synthetic */ AlertDialog val$progress;

        @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
        public void didFailChatCreation() {
        }

        @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
        public void didStartChatCreation() {
        }

        public C554430(AlertDialog alertDialog) {
            this.val$progress = alertDialog;
        }

        @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
        public void didFinishChatCreation(GroupCreateFinalActivity groupCreateFinalActivity, final long j) {
            final BaseFragment[] baseFragmentArr = {groupCreateFinalActivity, null};
            Utilities.Callback callback = new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$30$$ExternalSyntheticLambda0
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$didFinishChatCreation$1(j, baseFragmentArr, (Runnable) obj);
                }
            };
            Utilities.Callback callback2 = new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$30$$ExternalSyntheticLambda1
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$didFinishChatCreation$3(j, baseFragmentArr, (Runnable) obj);
                }
            };
            final AlertDialog alertDialog = this.val$progress;
            Utilities.Callback callback3 = new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$30$$ExternalSyntheticLambda2
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$didFinishChatCreation$5(alertDialog, j, (Runnable) obj);
                }
            };
            Utilities.Callback callback4 = new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$30$$ExternalSyntheticLambda3
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$didFinishChatCreation$7(j, (Runnable) obj);
                }
            };
            Utilities.Callback callback5 = new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$30$$ExternalSyntheticLambda4
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$didFinishChatCreation$9(j, (Runnable) obj);
                }
            };
            final AlertDialog alertDialog2 = this.val$progress;
            Utilities.doCallbacks(callback, callback2, callback3, callback4, callback5, new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$30$$ExternalSyntheticLambda5
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$didFinishChatCreation$10(alertDialog2, j, baseFragmentArr, (Runnable) obj);
                }
            });
        }

        public /* synthetic */ void lambda$didFinishChatCreation$1(long j, BaseFragment[] baseFragmentArr, final Runnable runnable) {
            if (DialogsActivity.this.requestPeerType.has_username != null && DialogsActivity.this.requestPeerType.has_username.booleanValue()) {
                Bundle bundle = new Bundle();
                bundle.putInt("step", 1);
                bundle.putLong("chat_id", j);
                bundle.putBoolean("forcePublic", DialogsActivity.this.requestPeerType.has_username.booleanValue());
                ChannelCreateActivity channelCreateActivity = new ChannelCreateActivity(bundle);
                channelCreateActivity.setOnFinishListener(new Utilities.Callback2() { // from class: org.telegram.ui.DialogsActivity$30$$ExternalSyntheticLambda7
                    @Override // org.telegram.messenger.Utilities.Callback2
                    public final void run(Object obj, Object obj2) {
                        runnable.run();
                    }
                });
                DialogsActivity.this.presentFragment(channelCreateActivity);
                baseFragmentArr[1] = channelCreateActivity;
                return;
            }
            runnable.run();
        }

        public /* synthetic */ void lambda$didFinishChatCreation$3(long j, final BaseFragment[] baseFragmentArr, Runnable runnable) {
            DialogsActivity.this.showSendToBotAlert(DialogsActivity.this.getMessagesController().getChat(Long.valueOf(j)), runnable, new Runnable() { // from class: org.telegram.ui.DialogsActivity$30$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didFinishChatCreation$2(baseFragmentArr);
                }
            });
        }

        public /* synthetic */ void lambda$didFinishChatCreation$2(BaseFragment[] baseFragmentArr) {
            DialogsActivity.this.removeSelfFromStack();
            if (baseFragmentArr[1] != null) {
                baseFragmentArr[0].removeSelfFromStack();
                baseFragmentArr[1].finishFragment();
            } else {
                baseFragmentArr[0].finishFragment();
            }
        }

        public /* synthetic */ void lambda$didFinishChatCreation$5(AlertDialog alertDialog, long j, final Runnable runnable) {
            alertDialog.showDelayed(150L);
            if (DialogsActivity.this.requestPeerType.bot_participant != null && DialogsActivity.this.requestPeerType.bot_participant.booleanValue()) {
                DialogsActivity.this.getMessagesController().addUserToChat(j, DialogsActivity.this.getMessagesController().getUser(Long.valueOf(DialogsActivity.this.requestPeerBotId)), 0, null, DialogsActivity.this, false, runnable, new MessagesController.ErrorDelegate() { // from class: org.telegram.ui.DialogsActivity$30$$ExternalSyntheticLambda9
                    @Override // org.telegram.messenger.MessagesController.ErrorDelegate
                    public final boolean run(TLRPC.TL_error tL_error) {
                        return DialogsActivity.C554430.m15843$r8$lambda$1l54IXmh8FSouQnx6WDFgWB0A(runnable, tL_error);
                    }
                });
            } else {
                runnable.run();
            }
        }

        /* JADX INFO: renamed from: $r8$lambda$1l54IXmh8FSouQnx6WDFgW-B0-A */
        public static /* synthetic */ boolean m15843$r8$lambda$1l54IXmh8FSouQnx6WDFgWB0A(Runnable runnable, TLRPC.TL_error tL_error) {
            runnable.run();
            return true;
        }

        public /* synthetic */ void lambda$didFinishChatCreation$7(long j, final Runnable runnable) {
            if (DialogsActivity.this.requestPeerType.bot_admin_rights != null) {
                TLRPC.User user = DialogsActivity.this.getMessagesController().getUser(Long.valueOf(DialogsActivity.this.requestPeerBotId));
                MessagesController messagesController = DialogsActivity.this.getMessagesController();
                TLRPC.TL_chatAdminRights tL_chatAdminRights = DialogsActivity.this.requestPeerType.bot_admin_rights;
                DialogsActivity dialogsActivity = DialogsActivity.this;
                messagesController.setUserAdminRole(j, user, tL_chatAdminRights, null, false, dialogsActivity, dialogsActivity.requestPeerType.bot_participant == null || !DialogsActivity.this.requestPeerType.bot_participant.booleanValue(), true, null, runnable, new MessagesController.ErrorDelegate() { // from class: org.telegram.ui.DialogsActivity$30$$ExternalSyntheticLambda10
                    @Override // org.telegram.messenger.MessagesController.ErrorDelegate
                    public final boolean run(TLRPC.TL_error tL_error) {
                        return DialogsActivity.C554430.m15844$r8$lambda$6QAWGFYN_hifaceXl3AGN9LOoE(runnable, tL_error);
                    }
                });
                return;
            }
            runnable.run();
        }

        /* JADX INFO: renamed from: $r8$lambda$6QAWGFYN_hifac-eXl3AGN9LOoE */
        public static /* synthetic */ boolean m15844$r8$lambda$6QAWGFYN_hifaceXl3AGN9LOoE(Runnable runnable, TLRPC.TL_error tL_error) {
            runnable.run();
            return true;
        }

        public /* synthetic */ void lambda$didFinishChatCreation$9(long j, final Runnable runnable) {
            if (DialogsActivity.this.requestPeerType.user_admin_rights != null) {
                DialogsActivity.this.getMessagesController().setUserAdminRole(j, DialogsActivity.this.getAccountInstance().getUserConfig().getCurrentUser(), ChatRightsEditActivity.rightsOR(DialogsActivity.this.getMessagesController().getChat(Long.valueOf(j)).admin_rights, DialogsActivity.this.requestPeerType.user_admin_rights), null, false, DialogsActivity.this, false, true, null, runnable, new MessagesController.ErrorDelegate() { // from class: org.telegram.ui.DialogsActivity$30$$ExternalSyntheticLambda8
                    @Override // org.telegram.messenger.MessagesController.ErrorDelegate
                    public final boolean run(TLRPC.TL_error tL_error) {
                        return DialogsActivity.C554430.m15846$r8$lambda$BG3nU2Rg4XvSp527a8VlwJE8ZY(runnable, tL_error);
                    }
                });
            } else {
                runnable.run();
            }
        }

        /* JADX INFO: renamed from: $r8$lambda$BG3nU2Rg4XvSp527a8VlwJ-E8ZY */
        public static /* synthetic */ boolean m15846$r8$lambda$BG3nU2Rg4XvSp527a8VlwJE8ZY(Runnable runnable, TLRPC.TL_error tL_error) {
            runnable.run();
            return true;
        }

        public /* synthetic */ void lambda$didFinishChatCreation$10(AlertDialog alertDialog, long j, BaseFragment[] baseFragmentArr, Runnable runnable) {
            alertDialog.dismiss();
            DialogsActivity.this.getMessagesController().loadChannelParticipants(Long.valueOf(j));
            DialogsActivityDelegate dialogsActivityDelegate = DialogsActivity.this.delegate;
            DialogsActivity.this.removeSelfFromStack();
            if (baseFragmentArr[1] != null) {
                baseFragmentArr[0].removeSelfFromStack();
                baseFragmentArr[1].finishFragment();
            } else {
                baseFragmentArr[0].finishFragment();
            }
            if (dialogsActivityDelegate != null) {
                ArrayList<MessagesStorage.TopicKey> arrayList = new ArrayList<>();
                arrayList.add(MessagesStorage.TopicKey.m1066of(-j, 0L));
                DialogsActivity dialogsActivity = DialogsActivity.this;
                dialogsActivityDelegate.didSelectDialogs(dialogsActivity, arrayList, null, false, dialogsActivity.notify, dialogsActivity.scheduleDate, dialogsActivity.scheduleRepeatPeriod, null);
            }
        }
    }

    public void updateContextViewPosition() {
        float f;
        float f2;
        float alpha;
        float fM1036dp;
        float f3;
        float animatedHeightWithPadding;
        SearchTabsAndFiltersLayout searchTabsAndFiltersLayout = this.searchTabsAndFiltersLayout;
        float f4 = 0.0f;
        float measuredHeight = (searchTabsAndFiltersLayout == null || searchTabsAndFiltersLayout.getVisibility() == 8) ? 0.0f : this.searchTabsAndFiltersLayout.getMeasuredHeight();
        float fM1036dp2 = this.hasStories ? AndroidUtilities.m1036dp(81.0f) : 0.0f;
        boolean z = this.hasStories;
        float f5 = this.scrollYOffset;
        if (z) {
            float f6 = this.searchAnimationProgress;
            f = f5 + (fM1036dp2 * (1.0f - f6)) + (measuredHeight * f6);
            f2 = this.tabsYOffset;
        } else {
            f = f5 + (measuredHeight * this.searchAnimationProgress);
            f2 = this.tabsYOffset;
        }
        float f7 = f + f2 + this.storiesOverscroll;
        FragmentSearchField fragmentSearchField = this.fragmentSearchField;
        float fM1036dp3 = AndroidUtilities.m1036dp(4.0f) * ((fragmentSearchField == null || fragmentSearchField.getVisibility() != 0) ? 0.0f : this.fragmentSearchField.getAlpha());
        FilterTabsView filterTabsView = this.filterTabsView;
        if (filterTabsView != null) {
            filterTabsView.setTranslationY(f7 - fM1036dp3);
            alpha = this.filterTabsView.getAlpha();
            fM1036dp = AndroidUtilities.m1036dp(43.0f) * alpha;
            f3 = f7 + fM1036dp;
        } else {
            alpha = 0.0f;
            fM1036dp = 0.0f;
            f3 = f7;
        }
        DialogsActivityTopPanelLayout dialogsActivityTopPanelLayout = this.topPanelLayout;
        if (dialogsActivityTopPanelLayout != null) {
            dialogsActivityTopPanelLayout.setTranslationY(AndroidUtilities.lerp(f3 - fM1036dp3, (-AndroidUtilities.m1036dp(3.0f)) - (this.searchTabsView == null ? AndroidUtilities.m1036dp(44.0f) : 0), this.animatorSearchVisible.getFloatValue()));
            float totalVisibility = this.topPanelLayout.getMetadata().getTotalVisibility();
            animatedHeightWithPadding = this.topPanelLayout.getAnimatedHeightWithPadding(0.0f);
            f4 = totalVisibility;
        } else {
            animatedHeightWithPadding = 0.0f;
        }
        DialogsActivityTopBubblesFadeView dialogsActivityTopBubblesFadeView = this.topBubblesFadeView;
        if (dialogsActivityTopBubblesFadeView != null) {
            dialogsActivityTopBubblesFadeView.setTranslationY(f7 - fM1036dp3);
            float fLerp = AndroidUtilities.lerp(AndroidUtilities.m1036dp(7.0f), AndroidUtilities.m1036dp(50.0f), Math.min(f4, alpha));
            this.topBubblesFadeView.setPosition(fLerp, Math.min(AndroidUtilities.m1036dp(40.0f), (animatedHeightWithPadding + fM1036dp) - fLerp));
            this.topBubblesFadeView.setAlpha(Math.max(alpha, f4));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:135:0x009d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateFiltersView(boolean r11, java.util.ArrayList<java.lang.Object> r12, java.util.ArrayList<org.telegram.ui.Adapters.FiltersView.DateData> r13, boolean r14, boolean r15) {
        /*
            r10 = this;
            boolean r0 = r10.searchIsShowed
            if (r0 == 0) goto Lc1
            boolean r0 = r10.onlySelect
            if (r0 != 0) goto Lc1
            org.telegram.ui.Components.SearchViewPager r0 = r10.searchViewPager
            if (r0 != 0) goto Le
            goto Lc1
        Le:
            java.util.ArrayList r0 = r0.getCurrentSearchFilters()
            r1 = 0
            r2 = r1
            r3 = r2
            r4 = r3
            r5 = r4
            r6 = r5
        L18:
            int r7 = r0.size()
            r8 = 1
            if (r2 >= r7) goto L56
            java.lang.Object r7 = r0.get(r2)
            org.telegram.ui.Adapters.FiltersView$MediaFilterData r7 = (org.telegram.ui.Adapters.FiltersView.MediaFilterData) r7
            boolean r7 = r7.isMedia()
            if (r7 == 0) goto L2d
            r4 = r8
            goto L53
        L2d:
            java.lang.Object r7 = r0.get(r2)
            org.telegram.ui.Adapters.FiltersView$MediaFilterData r7 = (org.telegram.ui.Adapters.FiltersView.MediaFilterData) r7
            int r7 = r7.filterType
            r9 = 4
            if (r7 != r9) goto L3a
            r5 = r8
            goto L53
        L3a:
            java.lang.Object r7 = r0.get(r2)
            org.telegram.ui.Adapters.FiltersView$MediaFilterData r7 = (org.telegram.ui.Adapters.FiltersView.MediaFilterData) r7
            int r7 = r7.filterType
            r9 = 6
            if (r7 != r9) goto L47
            r6 = r8
            goto L53
        L47:
            java.lang.Object r7 = r0.get(r2)
            org.telegram.ui.Adapters.FiltersView$MediaFilterData r7 = (org.telegram.ui.Adapters.FiltersView.MediaFilterData) r7
            int r7 = r7.filterType
            r9 = 7
            if (r7 != r9) goto L53
            r3 = r8
        L53:
            int r2 = r2 + 1
            goto L18
        L56:
            if (r3 == 0) goto L59
            r14 = r1
        L59:
            if (r12 == 0) goto L61
            boolean r0 = r12.isEmpty()
            if (r0 == 0) goto L6b
        L61:
            if (r13 == 0) goto L69
            boolean r0 = r13.isEmpty()
            if (r0 == 0) goto L6b
        L69:
            if (r14 == 0) goto L6d
        L6b:
            r0 = r8
            goto L6e
        L6d:
            r0 = r1
        L6e:
            r2 = 0
            if (r4 != 0) goto L76
            if (r0 != 0) goto L76
            if (r11 == 0) goto L76
            goto L9d
        L76:
            if (r0 == 0) goto L9d
            if (r12 == 0) goto L83
            boolean r11 = r12.isEmpty()
            if (r11 != 0) goto L83
            if (r5 != 0) goto L83
            goto L84
        L83:
            r12 = r2
        L84:
            if (r13 == 0) goto L8f
            boolean r11 = r13.isEmpty()
            if (r11 != 0) goto L8f
            if (r6 != 0) goto L8f
            goto L90
        L8f:
            r13 = r2
        L90:
            if (r12 != 0) goto L96
            if (r13 != 0) goto L96
            if (r14 == 0) goto L9d
        L96:
            org.telegram.ui.Adapters.FiltersView r11 = r10.filtersView
            r11.setUsersAndDates(r12, r13, r14)
            r11 = r8
            goto L9e
        L9d:
            r11 = r1
        L9e:
            if (r11 != 0) goto La5
            org.telegram.ui.Adapters.FiltersView r12 = r10.filtersView
            r12.setUsersAndDates(r2, r2, r1)
        La5:
            if (r15 != 0) goto Lb0
            org.telegram.ui.Adapters.FiltersView r12 = r10.filtersView
            androidx.recyclerview.widget.RecyclerView$Adapter r12 = r12.getAdapter()
            r12.notifyDataSetChanged()
        Lb0:
            org.telegram.ui.Components.ViewPagerFixed$TabsView r12 = r10.searchTabsView
            if (r12 == 0) goto Lb7
            r12.hide(r11, r8)
        Lb7:
            org.telegram.ui.Adapters.FiltersView r12 = r10.filtersView
            r12.setEnabled(r11)
            me.vkryl.android.animator.BoolAnimator r10 = r10.animatorSearchFilterTabsVisible
            r10.setValue(r11, r8)
        Lc1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.updateFiltersView(boolean, java.util.ArrayList, java.util.ArrayList, boolean, boolean):void");
    }

    private void addSearchFilter(FiltersView.MediaFilterData mediaFilterData) {
        SearchViewPager searchViewPager;
        if (this.searchIsShowed && (searchViewPager = this.searchViewPager) != null && searchViewPager.addSearchFilter(mediaFilterData)) {
            this.fragmentSearchField.addSearchFilter(mediaFilterData);
            this.fragmentSearchField.editText.getText().clear();
            updateFiltersView(true, null, null, false, true);
        }
    }

    public void updateSpeedItem(boolean z) {
        boolean z2;
        if (this.speedItem == null) {
            return;
        }
        ArrayList<MessageObject> arrayList = getDownloadController().downloadingFiles;
        int size = arrayList.size();
        boolean z3 = false;
        int i = 0;
        while (true) {
            if (i >= size) {
                z2 = false;
                break;
            }
            MessageObject messageObject = arrayList.get(i);
            i++;
            MessageObject messageObject2 = messageObject;
            if (messageObject2.getDocument() != null && messageObject2.getDocument().size >= 157286400) {
                z2 = true;
                break;
            }
        }
        ArrayList<MessageObject> arrayList2 = getDownloadController().recentDownloadingFiles;
        int size2 = arrayList2.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size2) {
                break;
            }
            MessageObject messageObject3 = arrayList2.get(i2);
            i2++;
            MessageObject messageObject4 = messageObject3;
            if (messageObject4.getDocument() != null && messageObject4.getDocument().size >= 157286400) {
                z2 = true;
                break;
            }
        }
        if (!getUserConfig().isPremium() && !getMessagesController().premiumFeaturesBlocked() && z2 && z) {
            z3 = true;
        }
        this.animatorSpeedButtonVisible.setValue(z3, true);
    }

    private void createActionMode(String str) {
        if (this.actionBar.actionModeIsExist(str)) {
            return;
        }
        ActionBarMenu actionBarMenuCreateActionMode = this.actionBar.createActionMode(false, str);
        boolean z = this.hasMainTabs && !ExteraConfig.getNavigationDrawer();
        if (z) {
            ImageView imageView = new ImageView(getContext());
            this.actionModeCloseView = imageView;
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            this.actionModeCloseView.setImageDrawable(new BackDrawable(true));
            this.actionModeCloseView.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_actionBarActionModeDefaultIcon), PorterDuff.Mode.MULTIPLY));
            this.actionModeCloseView.setBackground(Theme.createSelectorDrawable(getThemedColor(Theme.key_actionBarActionModeDefaultSelector)));
            this.actionModeCloseView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda149
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$createActionMode$75(view);
                }
            });
            actionBarMenuCreateActionMode.addView(this.actionModeCloseView, LayoutHelper.createLinear(54, 54, 16));
            this.actionModeViews.add(this.actionModeCloseView);
        }
        NumberTextView numberTextView = new NumberTextView(actionBarMenuCreateActionMode.getContext());
        this.selectedDialogsCountTextView = numberTextView;
        numberTextView.setTextSize(18);
        this.selectedDialogsCountTextView.setTypeface(AndroidUtilities.bold());
        this.selectedDialogsCountTextView.setTextColor(getThemedColor(Theme.key_actionBarActionModeDefaultIcon));
        actionBarMenuCreateActionMode.addView(this.selectedDialogsCountTextView, LayoutHelper.createLinear(0, -1, 1.0f, z ? 18 : 72, 0, 0, 0));
        this.selectedDialogsCountTextView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda150
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return DialogsActivity.$r8$lambda$dkRdLGBjBGIH8a8VB1DITZ3kJac(view, motionEvent);
            }
        });
        this.pinItem = actionBarMenuCreateActionMode.addItemWithWidth(100, C2797R.drawable.msg_pin, AndroidUtilities.m1036dp(54.0f));
        this.muteItem = actionBarMenuCreateActionMode.addItemWithWidth(104, C2797R.drawable.msg_mute, AndroidUtilities.m1036dp(54.0f));
        this.archive2Item = actionBarMenuCreateActionMode.addItemWithWidth(107, C2797R.drawable.msg_archive, AndroidUtilities.m1036dp(54.0f));
        this.deleteItem = actionBarMenuCreateActionMode.addItemWithWidth(102, C2797R.drawable.msg_delete, AndroidUtilities.m1036dp(54.0f), LocaleController.getString(C2797R.string.Delete));
        ActionBarMenuItem actionBarMenuItemAddItemWithWidth = actionBarMenuCreateActionMode.addItemWithWidth(0, C2797R.drawable.ic_ab_other, AndroidUtilities.m1036dp(54.0f), LocaleController.getString(C2797R.string.AccDescrMoreOptions));
        this.archiveItem = actionBarMenuItemAddItemWithWidth.addSubItem(105, C2797R.drawable.msg_archive, LocaleController.getString(C2797R.string.Archive));
        this.pin2Item = actionBarMenuItemAddItemWithWidth.addSubItem(108, C2797R.drawable.msg_pin, LocaleController.getString(C2797R.string.DialogPin));
        this.addToFolderItem = actionBarMenuItemAddItemWithWidth.addSubItem(109, C2797R.drawable.msg_addfolder, LocaleController.getString(C2797R.string.FilterAddTo));
        this.removeFromFolderItem = actionBarMenuItemAddItemWithWidth.addSubItem(110, C2797R.drawable.msg_removefolder, LocaleController.getString(C2797R.string.FilterRemoveFrom));
        this.readItem = actionBarMenuItemAddItemWithWidth.addSubItem(101, C2797R.drawable.msg_markread, LocaleController.getString(C2797R.string.MarkAsRead));
        this.clearItem = actionBarMenuItemAddItemWithWidth.addSubItem(103, C2797R.drawable.msg_clear, LocaleController.getString(C2797R.string.ClearHistory));
        this.blockItem = actionBarMenuItemAddItemWithWidth.addSubItem(106, C2797R.drawable.msg_block, LocaleController.getString(C2797R.string.BlockUser));
        this.muteItem.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda151
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.lambda$createActionMode$77(view);
            }
        });
        this.actionModeViews.add(this.pinItem);
        this.actionModeViews.add(this.archive2Item);
        this.actionModeViews.add(this.muteItem);
        this.actionModeViews.add(this.deleteItem);
        this.actionModeViews.add(actionBarMenuItemAddItemWithWidth);
        updateCounters(false);
    }

    public /* synthetic */ void lambda$createActionMode$75(View view) {
        hideActionMode(true);
    }

    public static /* synthetic */ boolean $r8$lambda$dkRdLGBjBGIH8a8VB1DITZ3kJac(View view, MotionEvent motionEvent) {
        return true;
    }

    public /* synthetic */ boolean lambda$createActionMode$77(View view) {
        performSelectedDialogsAction(this.selectedDialogs, 104, true, true);
        return true;
    }

    public void closeSearching() {
        ActionBar actionBar = this.actionBar;
        if (actionBar == null || !actionBar.isSearchFieldVisible()) {
            return;
        }
        this.actionBar.closeSearchField();
        this.searchIsShowed = false;
        updateFilterTabs(true, true);
    }

    public void scrollToFolder(int i) {
        if (this.filterTabsView == null) {
            updateFilterTabs(true, true);
            if (this.filterTabsView == null) {
                return;
            }
        }
        int tabsCount = this.filterTabsView.getTabsCount() - 1;
        ArrayList<MessagesController.DialogFilter> dialogFilters = getMessagesController().getDialogFilters();
        int i2 = 0;
        while (true) {
            if (i2 >= dialogFilters.size()) {
                break;
            }
            if (dialogFilters.get(i2).f1156id == i) {
                tabsCount = i2;
                break;
            }
            i2++;
        }
        FilterTabsView.Tab tab = this.filterTabsView.getTab(tabsCount);
        if (tab != null) {
            ViewPage[] viewPageArr = this.viewPages;
            if (viewPageArr == null || viewPageArr.length <= 0 || viewPageArr[0].selectedType != tab.f1563id) {
                this.filterTabsView.scrollToTab(tab, tabsCount);
                return;
            }
            return;
        }
        this.filterTabsView.selectLastTab();
    }

    public void switchToFilter(int i) {
        FilterTabsView filterTabsView = this.filterTabsView;
        if (filterTabsView == null) {
            return;
        }
        int tabsCount = filterTabsView.getTabsCount();
        for (int i2 = 0; i2 < tabsCount; i2++) {
            FilterTabsView.Tab tab = this.filterTabsView.getTab(i2);
            if (tab != null && tab.f1563id == i) {
                this.filterTabsView.scrollToTab(tab, i2);
                return;
            }
        }
    }

    public boolean hasRightFragment() {
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        return rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment();
    }

    public void switchToCurrentSelectedMode(boolean z) {
        ViewPage[] viewPageArr;
        int i = 0;
        int i2 = 0;
        while (true) {
            viewPageArr = this.viewPages;
            if (i2 >= viewPageArr.length) {
                break;
            }
            viewPageArr[i2].listView.stopScroll();
            i2++;
        }
        if (viewPageArr[z ? 1 : 0].selectedType < 0 || this.viewPages[z ? 1 : 0].selectedType >= getMessagesController().getDialogFilters().size()) {
            return;
        }
        MessagesController.DialogFilter dialogFilter = getMessagesController().getDialogFilters().get(this.viewPages[z ? 1 : 0].selectedType);
        boolean zIsDefault = dialogFilter.isDefault();
        ViewPage[] viewPageArr2 = this.viewPages;
        if (zIsDefault) {
            viewPageArr2[z ? 1 : 0].dialogsType = this.initialDialogsType;
            this.viewPages[z ? 1 : 0].listView.updatePullState();
        } else {
            int i3 = viewPageArr2[!z ? 1 : 0].dialogsType;
            ViewPage[] viewPageArr3 = this.viewPages;
            if (i3 == 7) {
                viewPageArr3[z ? 1 : 0].dialogsType = 8;
            } else {
                viewPageArr3[z ? 1 : 0].dialogsType = 7;
            }
            this.viewPages[z ? 1 : 0].listView.setScrollEnabled(true);
            getMessagesController().selectDialogFilter(dialogFilter, this.viewPages[z ? 1 : 0].dialogsType == 8 ? 1 : 0);
        }
        this.viewPages[1].isLocked = dialogFilter.locked;
        this.viewPages[z ? 1 : 0].dialogsAdapter.setDialogsType(this.viewPages[z ? 1 : 0].dialogsType);
        LinearLayoutManager linearLayoutManager = this.viewPages[z ? 1 : 0].layoutManager;
        if (this.viewPages[z ? 1 : 0].dialogsType == 0 && hasHiddenArchive() && this.viewPages[z ? 1 : 0].archivePullViewState == 2) {
            i = 1;
        }
        linearLayoutManager.scrollToPositionWithOffset(i, (int) this.scrollYOffset);
        checkListLoad(this.viewPages[z ? 1 : 0]);
        updateFloatingButtonVisibility(z);
    }

    public void showScrollbars(boolean z) {
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr == null || this.scrollBarVisible == z) {
            return;
        }
        this.scrollBarVisible = z;
        for (ViewPage viewPage : viewPageArr) {
            if (z) {
                viewPage.listView.setScrollbarFadingEnabled(false);
            }
            viewPage.listView.setVerticalScrollBarEnabled(z);
            if (z) {
                viewPage.listView.setScrollbarFadingEnabled(true);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:220:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x018e  */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v9 */
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
    public void updateFilterTabs(boolean r23, boolean r24) {
        /*
            Method dump skipped, instruction units count: 769
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.updateFilterTabs(boolean, boolean):void");
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onPanTranslationUpdate(float f) {
        if (this.viewPages == null) {
            return;
        }
        this.panTranslationY = f;
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        int i = 0;
        if (chatActivityEnterView != null && chatActivityEnterView.isPopupShowing()) {
            this.fragmentView.setTranslationY(f);
            ViewPage[] viewPageArr = this.viewPages;
            int length = viewPageArr.length;
            while (i < length) {
                viewPageArr[i].setTranslationY(0.0f);
                i++;
            }
            if (!this.onlySelect) {
                this.actionBar.setTranslationY(0.0f);
                Bulletin bulletin = this.topBulletin;
                if (bulletin != null) {
                    bulletin.updatePosition();
                }
            }
            SearchViewPager searchViewPager = this.searchViewPager;
            if (searchViewPager != null) {
                searchViewPager.setTranslationY(this.searchViewPagerTranslationY);
                return;
            }
            return;
        }
        ViewPage[] viewPageArr2 = this.viewPages;
        int length2 = viewPageArr2.length;
        while (i < length2) {
            viewPageArr2[i].setTranslationY(f);
            i++;
        }
        if (!this.onlySelect) {
            this.actionBar.setTranslationY(f);
            Bulletin bulletin2 = this.topBulletin;
            if (bulletin2 != null) {
                bulletin2.updatePosition();
            }
        }
        SearchViewPager searchViewPager2 = this.searchViewPager;
        if (searchViewPager2 != null) {
            searchViewPager2.setTranslationY(this.panTranslationY + this.searchViewPagerTranslationY);
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void finishFragment() {
        super.finishFragment();
        ItemOptions itemOptions = this.filterOptions;
        if (itemOptions != null) {
            itemOptions.dismiss();
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onResume() {
        final DialogsActivity dialogsActivity;
        ViewPage viewPage;
        View view;
        super.onResume();
        this.mainTabsHiddenByScroll = false;
        checkUi_mainTabsVisible();
        DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
        if (dialogStoriesCell != null) {
            dialogStoriesCell.onResume();
        }
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if (rightSlidingDialogContainer != null) {
            rightSlidingDialogContainer.onResume();
        }
        if (!this.parentLayout.isInPreviewMode() && (view = this.blurredView) != null && view.getVisibility() == 0) {
            this.blurredView.setVisibility(8);
            this.blurredView.setBackground(null);
        }
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr != null) {
            for (final ViewPage viewPage2 : viewPageArr) {
                viewPage2.listView.cancelClickRunnables(true);
                viewPage2.listView.post(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda30
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onResume$78(viewPage2);
                    }
                });
            }
        }
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        if (chatActivityEnterView != null) {
            chatActivityEnterView.onResume();
        }
        if (!this.onlySelect && this.folderId == 0) {
            getMediaDataController().checkStickers(4);
        }
        SearchViewPager searchViewPager = this.searchViewPager;
        if (searchViewPager != null) {
            searchViewPager.onResume();
        }
        boolean z = this.afterSignup || getUserConfig().unacceptedTermsOfService == null;
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService("notification");
        if (z && this.folderId == 0 && this.checkPermission && !this.onlySelect) {
            final Activity parentActivity = getParentActivity();
            if (parentActivity != null) {
                this.checkPermission = false;
                final boolean z2 = parentActivity.checkSelfPermission("android.permission.READ_CONTACTS") != 0;
                int i = Build.VERSION.SDK_INT;
                final boolean z3 = (i <= 28 || BuildVars.NO_SCOPED_STORAGE) && parentActivity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0;
                final boolean z4 = i >= 33 && parentActivity.checkSelfPermission("android.permission.POST_NOTIFICATIONS") != 0;
                dialogsActivity = this;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda31
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onResume$82(z4, z2, z3, parentActivity);
                    }
                }, (dialogsActivity.afterSignup && (z2 || z4)) ? 4000L : 0L);
            } else {
                dialogsActivity = this;
            }
        } else {
            dialogsActivity = this;
            if (!dialogsActivity.onlySelect && dialogsActivity.folderId == 0 && XiaomiUtilities.isMIUI() && !XiaomiUtilities.isCustomPermissionGranted(XiaomiUtilities.OP_SHOW_WHEN_LOCKED)) {
                if (dialogsActivity.getParentActivity() == null) {
                    return;
                }
                if (!MessagesController.getGlobalNotificationsSettings().getBoolean("askedAboutMiuiLockscreen", false)) {
                    dialogsActivity.showDialog(new AlertDialog.Builder(dialogsActivity.getParentActivity()).setTopAnimation(C2797R.raw.permission_request_apk, 72, false, dialogsActivity.getThemedColor(Theme.key_dialogTopBackground)).setMessage(LocaleController.getString(C2797R.string.PermissionXiaomiLockscreen)).setPositiveButton(LocaleController.getString(C2797R.string.PermissionOpenSettings), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda32
                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                        public final void onClick(AlertDialog alertDialog, int i2) {
                            this.f$0.lambda$onResume$83(alertDialog, i2);
                        }
                    }).setNegativeButton(LocaleController.getString(C2797R.string.ContactsPermissionAlertNotNow), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda33
                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                        public final void onClick(AlertDialog alertDialog, int i2) {
                            MessagesController.getGlobalNotificationsSettings().edit().putBoolean("askedAboutMiuiLockscreen", true).apply();
                        }
                    }).create());
                }
            } else if (dialogsActivity.folderId == 0 && Build.VERSION.SDK_INT >= 34 && !notificationManager.canUseFullScreenIntent()) {
                if (dialogsActivity.getParentActivity() == null) {
                    return;
                }
                if (!MessagesController.getGlobalNotificationsSettings().getBoolean("askedAboutFSILockscreen", false)) {
                    dialogsActivity.showDialog(new AlertDialog.Builder(dialogsActivity.getParentActivity()).setTopAnimation(C2797R.raw.permission_request_apk, 72, false, dialogsActivity.getThemedColor(Theme.key_dialogTopBackground)).setMessage(LocaleController.getString(C2797R.string.PermissionFSILockscreen)).setPositiveButton(LocaleController.getString(C2797R.string.PermissionOpenSettings), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda34
                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                        public final void onClick(AlertDialog alertDialog, int i2) {
                            this.f$0.lambda$onResume$85(alertDialog, i2);
                        }
                    }).setNegativeButton(LocaleController.getString(C2797R.string.ContactsPermissionAlertNotNow), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda35
                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                        public final void onClick(AlertDialog alertDialog, int i2) {
                            MessagesController.getGlobalNotificationsSettings().edit().putBoolean("askedAboutFSILockscreen", true).commit();
                        }
                    }).create());
                }
            }
        }
        dialogsActivity.showFiltersHint();
        if (dialogsActivity.viewPages != null) {
            int i2 = 0;
            while (true) {
                ViewPage[] viewPageArr2 = dialogsActivity.viewPages;
                if (i2 >= viewPageArr2.length) {
                    break;
                }
                if (viewPageArr2[i2].dialogsType == 0 && dialogsActivity.viewPages[i2].archivePullViewState == 2 && dialogsActivity.viewPages[i2].layoutManager.findFirstVisibleItemPosition() == 0 && dialogsActivity.hasHiddenArchive()) {
                    dialogsActivity.viewPages[i2].layoutManager.scrollToPositionWithOffset(1, (int) dialogsActivity.scrollYOffset);
                }
                ViewPage[] viewPageArr3 = dialogsActivity.viewPages;
                if (i2 == 0) {
                    viewPageArr3[i2].dialogsAdapter.resume();
                } else {
                    viewPageArr3[i2].dialogsAdapter.pause();
                }
                i2++;
            }
        }
        dialogsActivity.showNextSupportedSuggestion();
        Bulletin.addDelegate(dialogsActivity, new Bulletin.Delegate() { // from class: org.telegram.ui.DialogsActivity.31
            public C554531() {
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public void onBottomOffsetChange(float f) {
                if (DialogsActivity.this.undoView[0] == null || DialogsActivity.this.undoView[0].getVisibility() != 0) {
                    DialogsActivity.this.additionalFloatingTranslation = Math.max(0.0f, (f - r0.navigationBarHeight) - DialogsActivity.this.getCurrentBottomTabsOffset());
                    DialogsActivity.this.updateFloatingButtonOffset();
                }
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public void onShow(Bulletin bulletin) {
                if (DialogsActivity.this.undoView[0] == null || DialogsActivity.this.undoView[0].getVisibility() != 0) {
                    return;
                }
                DialogsActivity.this.undoView[0].hide(true, 2);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public int getTopOffset(int i3) {
                int collapsedProgress = 0;
                int measuredHeight = (((BaseFragment) DialogsActivity.this).actionBar != null ? ((BaseFragment) DialogsActivity.this).actionBar.getMeasuredHeight() : 0) + ((DialogsActivity.this.filterTabsView == null || DialogsActivity.this.filterTabsView.getVisibility() != 0) ? 0 : DialogsActivity.this.filterTabsView.getMeasuredHeight()) + (DialogsActivity.this.topPanelLayout != null ? DialogsActivity.this.topPanelLayout.getHeight() : 0);
                DialogsActivity dialogsActivity2 = DialogsActivity.this;
                DialogStoriesCell dialogStoriesCell2 = dialogsActivity2.dialogStoriesCell;
                if (dialogStoriesCell2 != null && dialogsActivity2.dialogStoriesCellVisible) {
                    collapsedProgress = (int) ((1.0f - dialogStoriesCell2.getCollapsedProgress()) * AndroidUtilities.m1036dp(81.0f));
                }
                return measuredHeight + collapsedProgress + DialogsActivity.this.getSearchFieldHeight();
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public int getBottomOffset(int i3) {
                return DialogsActivity.this.calculateListViewPaddingBottom() + (BottomNavigationBar.floating() ? DialogsActivity.this.getCurrentUndoViewOffset() : 0);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public boolean bottomOffsetAnimated() {
                return !BottomNavigationBar.floating();
            }
        });
        if (dialogsActivity.searchIsShowed) {
            AndroidUtilities.requestAdjustResize(dialogsActivity.getParentActivity(), dialogsActivity.classGuid);
        }
        dialogsActivity.updateVisibleRows(0, false);
        dialogsActivity.updateProxyButton(false, true);
        dialogsActivity.updateStoriesVisibility(false);
        dialogsActivity.checkSuggestClearDatabase();
        dialogsActivity.checkUi_mainTabsVisible();
        if (dialogsActivity.filterTabsView == null || (viewPage = dialogsActivity.viewPages[0]) == null || viewPage.dialogsAdapter == null) {
            return;
        }
        int dialogsType = dialogsActivity.viewPages[0].dialogsAdapter.getDialogsType();
        if (dialogsType == 7 || dialogsType == 8) {
            MessagesController.DialogFilter dialogFilter = dialogsActivity.getMessagesController().selectedDialogFilter[dialogsType != 7 ? (char) 1 : (char) 0];
            if (dialogFilter != null) {
                dialogsActivity.filterTabsView.selectTabWithStableId(dialogFilter.localId);
            }
        }
    }

    public /* synthetic */ void lambda$onResume$78(ViewPage viewPage) {
        if (this.isPaused || this.viewPages == null || viewPage.getParent() == null) {
            return;
        }
        viewPage.updateList(false);
    }

    public /* synthetic */ void lambda$onResume$82(boolean z, boolean z2, boolean z3, final Activity activity) {
        if (getParentActivity() == null) {
            return;
        }
        this.afterSignup = false;
        if (z || z2 || z3) {
            this.askingForPermissions = true;
            if (z && NotificationPermissionDialog.shouldAsk(activity)) {
                PermissionRequest.requestPermission("android.permission.POST_NOTIFICATIONS", new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda111
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$onResume$80(activity, (Boolean) obj);
                    }
                });
                return;
            }
            if (z2 && this.askAboutContacts && getUserConfig().syncContacts && activity.shouldShowRequestPermissionRationale("android.permission.READ_CONTACTS")) {
                AlertDialog alertDialogCreate = AlertsCreator.createContactsPermissionDialog(activity, new MessagesStorage.IntCallback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda112
                    @Override // org.telegram.messenger.MessagesStorage.IntCallback
                    public final void run(int i) {
                        this.f$0.lambda$onResume$81(i);
                    }
                }).create();
                this.permissionDialog = alertDialogCreate;
                showDialog(alertDialogCreate);
            } else {
                if (z3 && activity.shouldShowRequestPermissionRationale("android.permission.WRITE_EXTERNAL_STORAGE")) {
                    if (activity instanceof BasePermissionsActivity) {
                        AlertDialog alertDialogCreatePermissionErrorAlert = ((BasePermissionsActivity) activity).createPermissionErrorAlert(C2797R.raw.permission_request_folder, LocaleController.getString(C2797R.string.PermissionStorageWithHint));
                        this.permissionDialog = alertDialogCreatePermissionErrorAlert;
                        showDialog(alertDialogCreatePermissionErrorAlert);
                        return;
                    }
                    return;
                }
                askForPermissons(true);
            }
        }
    }

    public /* synthetic */ void lambda$onResume$80(final Activity activity, Boolean bool) {
        if (bool.booleanValue()) {
            return;
        }
        showDialog(new NotificationPermissionDialog(activity, !PermissionRequest.canAskPermission("android.permission.POST_NOTIFICATIONS"), new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda143
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                DialogsActivity.$r8$lambda$kt7wSlR36pMlJoa63yLyU7lSLww(activity, (Boolean) obj);
            }
        }));
    }

    public static /* synthetic */ void $r8$lambda$kt7wSlR36pMlJoa63yLyU7lSLww(Activity activity, Boolean bool) {
        if (bool.booleanValue()) {
            if (!PermissionRequest.canAskPermission("android.permission.POST_NOTIFICATIONS")) {
                PermissionRequest.showPermissionSettings("android.permission.POST_NOTIFICATIONS");
            } else {
                activity.requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 1);
            }
        }
    }

    public /* synthetic */ void lambda$onResume$81(int i) {
        this.askAboutContacts = i != 0;
        MessagesController.getGlobalNotificationsSettings().edit().putBoolean("askAboutContacts", this.askAboutContacts).apply();
        askForPermissons(false);
    }

    public /* synthetic */ void lambda$onResume$83(AlertDialog alertDialog, int i) {
        Intent permissionManagerIntent = XiaomiUtilities.getPermissionManagerIntent();
        if (permissionManagerIntent != null) {
            try {
                try {
                    getParentActivity().startActivity(permissionManagerIntent);
                } catch (Exception e) {
                    FileLog.m1048e(e);
                }
            } catch (Exception unused) {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
                getParentActivity().startActivity(intent);
            }
        }
    }

    public /* synthetic */ void lambda$onResume$85(AlertDialog alertDialog, int i) {
        Intent intent = new Intent("android.settings.MANAGE_APP_USE_FULL_SCREEN_INTENT");
        intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
        try {
            getParentActivity().startActivity(intent);
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$31 */
    public class C554531 implements Bulletin.Delegate {
        public C554531() {
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public void onBottomOffsetChange(float f) {
            if (DialogsActivity.this.undoView[0] == null || DialogsActivity.this.undoView[0].getVisibility() != 0) {
                DialogsActivity.this.additionalFloatingTranslation = Math.max(0.0f, (f - r0.navigationBarHeight) - DialogsActivity.this.getCurrentBottomTabsOffset());
                DialogsActivity.this.updateFloatingButtonOffset();
            }
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public void onShow(Bulletin bulletin) {
            if (DialogsActivity.this.undoView[0] == null || DialogsActivity.this.undoView[0].getVisibility() != 0) {
                return;
            }
            DialogsActivity.this.undoView[0].hide(true, 2);
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public int getTopOffset(int i3) {
            int collapsedProgress = 0;
            int measuredHeight = (((BaseFragment) DialogsActivity.this).actionBar != null ? ((BaseFragment) DialogsActivity.this).actionBar.getMeasuredHeight() : 0) + ((DialogsActivity.this.filterTabsView == null || DialogsActivity.this.filterTabsView.getVisibility() != 0) ? 0 : DialogsActivity.this.filterTabsView.getMeasuredHeight()) + (DialogsActivity.this.topPanelLayout != null ? DialogsActivity.this.topPanelLayout.getHeight() : 0);
            DialogsActivity dialogsActivity2 = DialogsActivity.this;
            DialogStoriesCell dialogStoriesCell2 = dialogsActivity2.dialogStoriesCell;
            if (dialogStoriesCell2 != null && dialogsActivity2.dialogStoriesCellVisible) {
                collapsedProgress = (int) ((1.0f - dialogStoriesCell2.getCollapsedProgress()) * AndroidUtilities.m1036dp(81.0f));
            }
            return measuredHeight + collapsedProgress + DialogsActivity.this.getSearchFieldHeight();
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public int getBottomOffset(int i3) {
            return DialogsActivity.this.calculateListViewPaddingBottom() + (BottomNavigationBar.floating() ? DialogsActivity.this.getCurrentUndoViewOffset() : 0);
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public boolean bottomOffsetAnimated() {
            return !BottomNavigationBar.floating();
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public boolean presentFragment(BaseFragment baseFragment) {
        ViewPage[] viewPageArr;
        boolean zPresentFragment = super.presentFragment(baseFragment);
        if (zPresentFragment && (viewPageArr = this.viewPages) != null) {
            for (ViewPage viewPage : viewPageArr) {
                viewPage.dialogsAdapter.pause();
            }
        }
        HintView2 hintView2 = this.storyHint;
        if (hintView2 != null) {
            hintView2.hide();
        }
        HintView2 hintView22 = this.storyPremiumHint;
        if (hintView22 != null) {
            hintView22.hide();
        }
        Bulletin.hideVisible();
        return zPresentFragment;
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
        Bulletin bulletin = this.storiesBulletin;
        if (bulletin != null) {
            bulletin.hide();
            this.storiesBulletin = null;
        }
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if (rightSlidingDialogContainer != null) {
            rightSlidingDialogContainer.onPause();
        }
        ItemOptions itemOptions = this.filterOptions;
        if (itemOptions != null) {
            itemOptions.dismiss();
        }
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        if (chatActivityEnterView != null) {
            chatActivityEnterView.onPause();
        }
        UndoView undoView = this.undoView[0];
        if (undoView != null) {
            undoView.hide(true, 0);
        }
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr != null) {
            for (ViewPage viewPage : viewPageArr) {
                viewPage.dialogsAdapter.pause();
            }
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public boolean onBackPressed(boolean z) {
        if (drawerContainer() != null && drawerContainer().isDrawerOpen()) {
            if (z) {
                drawerContainer().closeDrawer(true);
            }
            return false;
        }
        if (hasShownSheet()) {
            if (z) {
                closeSheet();
            }
            return false;
        }
        if (this.rightSlidingDialogContainer.hasFragment() && this.rightSlidingDialogContainer.getFragment().onBackPressed(z)) {
            if (z) {
                this.rightSlidingDialogContainer.lambda$presentFragment$1();
                SearchViewPager searchViewPager = this.searchViewPager;
                if (searchViewPager != null) {
                    searchViewPager.updateTabs();
                }
            }
            return false;
        }
        ItemOptions itemOptions = this.filterOptions;
        if (itemOptions != null) {
            if (z) {
                itemOptions.dismiss();
                this.filterOptions = null;
            }
            return false;
        }
        FilterTabsView filterTabsView = this.filterTabsView;
        if (filterTabsView != null && filterTabsView.isEditing()) {
            if (z) {
                this.filterTabsView.setIsEditing(false);
                showDoneItem(false);
            }
            return false;
        }
        ActionBar actionBar = this.actionBar;
        if (actionBar != null && actionBar.isActionModeShowed()) {
            if (z) {
                SearchViewPager searchViewPager2 = this.searchViewPager;
                if (searchViewPager2 != null && searchViewPager2.getVisibility() == 0) {
                    this.searchViewPager.hideActionMode();
                }
                hideActionMode(true);
            }
            return false;
        }
        if (this.animatorSearchVisible.getValue()) {
            if (z) {
                this.fragmentSearchField.editText.getText().clear();
                this.fragmentSearchFieldWatcher.toggleSearch(false);
                this.fragmentSearchField.editText.clearFocus();
            }
            return false;
        }
        FilterTabsView filterTabsView2 = this.filterTabsView;
        if (filterTabsView2 != null && filterTabsView2.getVisibility() == 0 && !this.tabsAnimationInProgress && !this.filterTabsView.isAnimatingIndicator() && !this.startedTracking && !this.filterTabsView.isFirstTabSelected()) {
            if (z) {
                this.filterTabsView.selectFirstTab();
            }
            return false;
        }
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        if (chatActivityEnterView != null && chatActivityEnterView.isPopupShowing()) {
            if (z) {
                this.commentView.hidePopup(true);
            }
            return false;
        }
        if (this.dialogStoriesCell.isFullExpanded() && this.dialogStoriesCell.scrollToFirst()) {
            return false;
        }
        return super.onBackPressed(z);
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onBecomeFullyHidden() {
        View view;
        FilterTabsView filterTabsView;
        if (this.closeSearchFieldOnHide) {
            ActionBar actionBar = this.actionBar;
            if (actionBar != null) {
                actionBar.closeSearchField();
            }
            TLObject tLObject = this.searchObject;
            if (tLObject != null) {
                SearchViewPager searchViewPager = this.searchViewPager;
                if (searchViewPager != null) {
                    searchViewPager.dialogsSearchAdapter.putRecentSearch(this.searchDialogId, tLObject);
                }
                this.searchObject = null;
            }
            this.closeSearchFieldOnHide = false;
        }
        if (!this.hasStories && (filterTabsView = this.filterTabsView) != null && filterTabsView.getVisibility() == 0 && this.animatorFilterTabsVisible.getValue()) {
            int i = (int) (-this.scrollYOffset);
            int currentActionBarHeight = ActionBar.getCurrentActionBarHeight();
            if (i != 0 && i != currentActionBarHeight && i >= currentActionBarHeight / 2) {
                this.viewPages[0].listView.canScrollVertically(1);
            }
        }
        UndoView undoView = this.undoView[0];
        if (undoView != null) {
            undoView.hide(true, 0);
        }
        INavigationLayout iNavigationLayout = this.parentLayout;
        if ((iNavigationLayout == null || !iNavigationLayout.isInPreviewMode()) && (view = this.blurredView) != null && view.getVisibility() == 0) {
            this.blurredView.setVisibility(8);
            this.blurredView.setBackground(null);
        }
        super.onBecomeFullyHidden();
        checkUi_mainTabsVisible();
        this.canShowStoryHint = true;
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onBecomeFullyVisible() {
        HintView2 hintView2;
        super.onBecomeFullyVisible();
        checkUi_mainTabsVisible();
        if (isArchive()) {
            SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
            boolean z = globalMainSettings.getBoolean("archivehint", true);
            boolean zIsEmpty = getDialogsArray(this.currentAccount, this.initialDialogsType, this.folderId, false).isEmpty();
            if (z && zIsEmpty) {
                MessagesController.getGlobalMainSettings().edit().putBoolean("archivehint", false).commit();
                z = false;
            }
            if (z) {
                globalMainSettings.edit().putBoolean("archivehint", false).commit();
                showArchiveHelp();
            }
        }
        if (this.canShowStoryHint && !this.storyHintShown && (hintView2 = this.storyHint) != null && this.storiesEnabled) {
            this.storyHintShown = true;
            this.canShowStoryHint = false;
            hintView2.show();
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda46
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.createSearchViewPager();
            }
        }, 200L);
    }

    public void showArchiveHelp() {
        getContactsController().loadGlobalPrivacySetting();
        BottomSheet bottomSheetShow = new BottomSheet.Builder(getContext(), false, getResourceProvider()).setCustomView(new ArchiveHelp(getContext(), this.currentAccount, getResourceProvider(), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda127
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showArchiveHelp$88(bottomSheetArr);
            }
        }, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda128
            @Override // java.lang.Runnable
            public final void run() {
                DialogsActivity.m15600$r8$lambda$aOJD_RjxYpnOOdhm1Qm04fm6sc(bottomSheetArr);
            }
        }), 49).show();
        final BottomSheet[] bottomSheetArr = {bottomSheetShow};
        bottomSheetShow.fixNavigationBar(getThemedColor(Theme.key_dialogBackground));
    }

    public /* synthetic */ void lambda$showArchiveHelp$88(BottomSheet[] bottomSheetArr) {
        BottomSheet bottomSheet = bottomSheetArr[0];
        if (bottomSheet != null) {
            bottomSheet.lambda$new$0();
            bottomSheetArr[0] = null;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda138
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showArchiveHelp$87();
            }
        }, 300L);
    }

    public /* synthetic */ void lambda$showArchiveHelp$87() {
        presentFragment(new ArchiveSettingsActivity());
    }

    /* JADX INFO: renamed from: $r8$lambda$aOJD_Rjx-YpnOOdhm1Qm04fm6sc */
    public static /* synthetic */ void m15600$r8$lambda$aOJD_RjxYpnOOdhm1Qm04fm6sc(BottomSheet[] bottomSheetArr) {
        BottomSheet bottomSheet = bottomSheetArr[0];
        if (bottomSheet != null) {
            bottomSheet.lambda$new$0();
            bottomSheetArr[0] = null;
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void setInPreviewMode(boolean z) {
        super.setInPreviewMode(z);
        if (!z && this.avatarContainer != null) {
            this.actionBar.setBackground(null);
            ((ViewGroup.MarginLayoutParams) this.actionBar.getLayoutParams()).topMargin = 0;
            this.actionBar.removeView(this.avatarContainer);
            this.avatarContainer = null;
            applyActionBarTitleStyle();
            updateFilterTabs(false, false);
            this.floatingButton3.imageView.setVisibility(0);
            DialogsActivityTopPanelLayout dialogsActivityTopPanelLayout = this.topPanelLayout;
            if (dialogsActivityTopPanelLayout != null) {
                FrameLayout frameLayout = this.fragmentContextViewWrapper;
                if (frameLayout != null) {
                    dialogsActivityTopPanelLayout.addView(frameLayout);
                }
                FrameLayout frameLayout2 = this.fragmentLocationContextViewWrapper;
                if (frameLayout2 != null) {
                    this.topPanelLayout.addView(frameLayout2);
                }
            }
        }
        DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
        if (dialogStoriesCell != null) {
            if (this.dialogStoriesCellVisible && !z) {
                dialogStoriesCell.setVisibility(0);
            } else {
                dialogStoriesCell.setVisibility(8);
            }
        }
        updateFloatingButtonVisibility(true);
        updateDialogsHint();
        if (this.fragmentView != null) {
            checkUi_menuItems();
            checkUi_searchFieldVisibility();
            checkUi_searchFieldStyle();
            checkUi_filterTabsVisible();
            checkUi_searchFiltersVisibility();
            this.fragmentView.requestLayout();
        }
        checkUi_mainTabsVisible();
    }

    public boolean addOrRemoveSelectedDialog(long j, View view) {
        if (this.onlySelect && getMessagesController().isForum(j)) {
            return false;
        }
        boolean zContains = this.selectedDialogs.contains(Long.valueOf(j));
        ArrayList<Long> arrayList = this.selectedDialogs;
        if (zContains) {
            arrayList.remove(Long.valueOf(j));
            if (view instanceof DialogCell) {
                ((DialogCell) view).setChecked(false, true);
            } else if (view instanceof ProfileSearchCell) {
                ((ProfileSearchCell) view).setChecked(false, true);
            }
            return false;
        }
        arrayList.add(Long.valueOf(j));
        if (view instanceof DialogCell) {
            ((DialogCell) view).setChecked(true, true);
        } else if (view instanceof ProfileSearchCell) {
            ((ProfileSearchCell) view).setChecked(true, true);
        }
        return true;
    }

    public void search(String str, boolean z) {
        showSearch(true, false, z);
        FragmentSearchField fragmentSearchField = this.fragmentSearchField;
        if (fragmentSearchField != null) {
            fragmentSearchField.editText.setText(str);
            this.fragmentSearchField.editText.setSelection(str.length());
        }
    }

    public void showSearch(boolean z, boolean z2, boolean z3) {
        showSearch(z, z2, z3, false);
    }

    public void showSearch(boolean z, boolean z2, boolean z3, boolean z4) {
        SearchViewPager searchViewPager;
        SearchViewPager searchViewPager2;
        DialogStoriesCell dialogStoriesCell;
        SearchViewPager searchViewPager3;
        RightSlidingDialogContainer rightSlidingDialogContainer;
        SearchViewPager searchViewPager4;
        boolean z5 = z && isSupportSearch();
        boolean z6 = z3;
        this.animatorSearchVisible.setValue(z5, z6);
        if (!z5) {
            updateSpeedItem(false);
        } else {
            createSearchViewPager();
        }
        int i = this.initialDialogsType;
        if (i != 0 && i != 3) {
            z6 = false;
        }
        AnimatorSet animatorSet = this.searchAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.searchAnimator = null;
        }
        this.searchIsShowed = z5;
        blur3_InvalidateBlur();
        if (z5) {
            boolean zOnlyDialogsAdapter = (this.searchFiltersWasShowed || z4) ? false : onlyDialogsAdapter();
            SearchViewPager searchViewPager5 = this.searchViewPager;
            if (searchViewPager5 != null) {
                searchViewPager5.showOnlyDialogsAdapter(zOnlyDialogsAdapter);
            }
            boolean z7 = !zOnlyDialogsAdapter || this.hasStories;
            this.whiteActionBar = z7;
            if (z7) {
                this.searchFiltersWasShowed = true;
            }
            ViewPagerFixed.TabsView tabsView = this.searchTabsView;
            if (tabsView == null && (searchViewPager4 = this.searchViewPager) != null && !zOnlyDialogsAdapter) {
                ViewPagerFixed.TabsView tabsViewCreateTabsView = searchViewPager4.createTabsView(false, -2);
                this.searchTabsView = tabsViewCreateTabsView;
                this.searchTabsAndFiltersLayout.addView(tabsViewCreateTabsView, 0, LayoutHelper.createFrame(-1, -1, 119));
            } else if (this.searchTabsAndFiltersLayout != null && zOnlyDialogsAdapter) {
                AndroidUtilities.removeFromParent(tabsView);
                this.searchTabsView = null;
            }
            if (this.searchViewPager != null) {
                checkUi_searchPagesPaddings(false);
                this.searchViewPager.setKeyboardHeight(((ContentView) this.fragmentView).getKeyboardHeight());
                this.searchViewPager.clear();
            }
            if (this.folderId != 0 && ((rightSlidingDialogContainer = this.rightSlidingDialogContainer) == null || !rightSlidingDialogContainer.hasFragment())) {
                addSearchFilter(new FiltersView.MediaFilterData(C2797R.drawable.chats_archive, C2797R.string.ArchiveSearchFilter, (TLRPC.MessagesFilter) null, 7));
            }
        }
        if (z6 && (searchViewPager3 = this.searchViewPager) != null && searchViewPager3.dialogsSearchAdapter.hasRecentSearch()) {
            AndroidUtilities.setAdjustResizeToNothing(getParentActivity(), this.classGuid);
        } else {
            AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
        }
        if (!z5 && (dialogStoriesCell = this.dialogStoriesCell) != null && this.dialogStoriesCellVisible) {
            dialogStoriesCell.setVisibility(0);
        }
        boolean z8 = SharedConfig.getDevicePerformanceClass() == 0 || !LiteMode.isEnabled(32768);
        if (z6) {
            if (z5) {
                SearchViewPager searchViewPager6 = this.searchViewPager;
                if (searchViewPager6 != null) {
                    searchViewPager6.setVisibility(0);
                    this.searchViewPager.reset();
                }
                updateFiltersView(true, null, null, false, false);
                ViewPagerFixed.TabsView tabsView2 = this.searchTabsView;
                if (tabsView2 != null) {
                    tabsView2.hide(false, false);
                }
            } else {
                this.viewPages[0].listView.setVisibility(0);
                this.viewPages[0].setVisibility(0);
            }
            setDialogsListFrozen(true);
            this.viewPages[0].listView.setVerticalScrollBarEnabled(false);
            SearchViewPager searchViewPager7 = this.searchViewPager;
            if (searchViewPager7 != null) {
                searchViewPager7.setBackgroundColor(getThemedColor(Theme.key_windowBackgroundWhite));
            }
            this.searchAnimator = new AnimatorSet();
            ArrayList arrayList = new ArrayList();
            ViewPage viewPage = this.viewPages[0];
            Property property = View.ALPHA;
            arrayList.add(ObjectAnimator.ofFloat(viewPage, (Property<ViewPage, Float>) property, z5 ? 0.0f : 1.0f));
            ViewPage[] viewPageArr = this.viewPages;
            if (z8) {
                viewPageArr[0].setScaleX(1.0f);
                this.viewPages[0].setScaleY(1.0f);
            } else {
                arrayList.add(ObjectAnimator.ofFloat(viewPageArr[0], (Property<ViewPage, Float>) View.SCALE_X, z5 ? 0.95f : 1.0f));
                arrayList.add(ObjectAnimator.ofFloat(this.viewPages[0], (Property<ViewPage, Float>) View.SCALE_Y, z5 ? 0.95f : 1.0f));
            }
            RightSlidingDialogContainer rightSlidingDialogContainer2 = this.rightSlidingDialogContainer;
            if (rightSlidingDialogContainer2 != null) {
                rightSlidingDialogContainer2.setVisibility(0);
                arrayList.add(ObjectAnimator.ofFloat(this.rightSlidingDialogContainer, (Property<RightSlidingDialogContainer, Float>) property, z5 ? 0.0f : 1.0f));
            }
            SearchViewPager searchViewPager8 = this.searchViewPager;
            if (searchViewPager8 != null) {
                arrayList.add(ObjectAnimator.ofFloat(searchViewPager8, (Property<SearchViewPager, Float>) property, z5 ? 1.0f : 0.0f));
                if (this.hasStories) {
                    float fM1036dp = AndroidUtilities.m1036dp(81.0f) + this.scrollYOffset + AndroidUtilities.m1036dp(48.0f);
                    SearchViewPager searchViewPager9 = this.searchViewPager;
                    Property<View, Float> property2 = this.SEARCH_TRANSLATION_Y;
                    float f = z5 ? fM1036dp : 0.0f;
                    if (z5) {
                        fM1036dp = 0.0f;
                    }
                    arrayList.add(ObjectAnimator.ofFloat(searchViewPager9, (Property<SearchViewPager, Float>) property2, f, fM1036dp));
                }
                SearchViewPager searchViewPager10 = this.searchViewPager;
                if (z8) {
                    searchViewPager10.setScaleX(1.0f);
                    this.searchViewPager.setScaleY(1.0f);
                } else {
                    arrayList.add(ObjectAnimator.ofFloat(searchViewPager10, (Property<SearchViewPager, Float>) View.SCALE_X, z5 ? 1.0f : 1.05f));
                    arrayList.add(ObjectAnimator.ofFloat(this.searchViewPager, (Property<SearchViewPager, Float>) View.SCALE_Y, z5 ? 1.0f : 1.05f));
                }
            }
            if (this.downloadsItem != null) {
                updateProxyButton(false, false);
            }
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.searchAnimationProgress, z5 ? 1.0f : 0.0f);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda45
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$showSearch$90(valueAnimator);
                }
            });
            arrayList.add(valueAnimatorOfFloat);
            this.searchAnimator.playTogether(arrayList);
            this.searchAnimator.setDuration(z5 ? 200L : 180L);
            this.searchAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT);
            if (!z5) {
                this.searchAnimator.setStartDelay(20L);
            }
            this.searchAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.32
                final /* synthetic */ boolean val$show;

                public C554632(boolean z52) {
                    z = z52;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    DialogsActivity.this.notificationsLocker.unlock();
                    if (DialogsActivity.this.searchAnimator != animator) {
                        return;
                    }
                    DialogsActivity.this.setDialogsListFrozen(false);
                    boolean z9 = z;
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    if (z9) {
                        dialogsActivity.viewPages[0].listView.hide();
                        DialogStoriesCell dialogStoriesCell2 = DialogsActivity.this.dialogStoriesCell;
                        if (dialogStoriesCell2 != null) {
                            dialogStoriesCell2.setVisibility(8);
                        }
                        DialogsActivity.this.searchWasFullyShowed = true;
                        AndroidUtilities.requestAdjustResize(DialogsActivity.this.getParentActivity(), ((BaseFragment) DialogsActivity.this).classGuid);
                        DialogsActivity.this.searchItem.setVisibility(8);
                        RightSlidingDialogContainer rightSlidingDialogContainer3 = DialogsActivity.this.rightSlidingDialogContainer;
                        if (rightSlidingDialogContainer3 != null) {
                            rightSlidingDialogContainer3.setVisibility(8);
                        }
                    } else {
                        dialogsActivity.whiteActionBar = false;
                        if (dialogsActivity.searchViewPager != null) {
                            DialogsActivity.this.searchViewPager.setVisibility(8);
                        }
                        if (DialogsActivity.this.fragmentSearchField != null) {
                            DialogsActivity.this.fragmentSearchField.clearSearchFilters();
                        }
                        if (DialogsActivity.this.searchViewPager != null) {
                            DialogsActivity.this.searchViewPager.clear();
                        }
                        DialogsActivity.this.viewPages[0].listView.show();
                        DialogsActivity.this.searchWasFullyShowed = false;
                        RightSlidingDialogContainer rightSlidingDialogContainer4 = DialogsActivity.this.rightSlidingDialogContainer;
                        if (rightSlidingDialogContainer4 != null) {
                            rightSlidingDialogContainer4.setVisibility(0);
                        }
                    }
                    View view = DialogsActivity.this.fragmentView;
                    if (view != null) {
                        view.requestLayout();
                    }
                    DialogsActivity.this.setSearchAnimationProgress(z ? 1.0f : 0.0f, false);
                    DialogsActivity.this.viewPages[0].listView.setVerticalScrollBarEnabled(true);
                    if (DialogsActivity.this.searchViewPager != null) {
                        DialogsActivity.this.searchViewPager.setBackground(null);
                    }
                    DialogsActivity.this.searchAnimator = null;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    DialogsActivity.this.notificationsLocker.unlock();
                    if (DialogsActivity.this.searchAnimator == animator) {
                        boolean z9 = z;
                        DialogsActivity dialogsActivity = DialogsActivity.this;
                        if (z9) {
                            dialogsActivity.viewPages[0].listView.hide();
                        } else {
                            dialogsActivity.viewPages[0].listView.show();
                        }
                        DialogsActivity.this.searchAnimator = null;
                    }
                }
            });
            this.notificationsLocker.lock();
            this.searchAnimator.start();
        } else {
            setDialogsListFrozen(false);
            ViewPage[] viewPageArr2 = this.viewPages;
            if (z52) {
                viewPageArr2[0].listView.hide();
            } else {
                viewPageArr2[0].listView.show();
            }
            this.viewPages[0].setAlpha(z52 ? 0.0f : 1.0f);
            ViewPage[] viewPageArr3 = this.viewPages;
            if (!z8) {
                viewPageArr3[0].setScaleX(z52 ? 0.95f : 1.0f);
                this.viewPages[0].setScaleY(z52 ? 0.95f : 1.0f);
            } else {
                viewPageArr3[0].setScaleX(1.0f);
                this.viewPages[0].setScaleY(1.0f);
            }
            SearchViewPager searchViewPager11 = this.searchViewPager;
            if (searchViewPager11 != null) {
                searchViewPager11.setAlpha(z52 ? 1.0f : 0.0f);
                SearchViewPager searchViewPager12 = this.searchViewPager;
                if (!z8) {
                    searchViewPager12.setScaleX(z52 ? 1.0f : 1.1f);
                    this.searchViewPager.setScaleY(z52 ? 1.0f : 1.1f);
                } else {
                    searchViewPager12.setScaleX(1.0f);
                    this.searchViewPager.setScaleY(1.0f);
                }
                this.searchViewPager.setVisibility(z52 ? 0 : 8);
            }
            FragmentSearchField fragmentSearchField = this.fragmentSearchField;
            if (fragmentSearchField != null) {
                fragmentSearchField.setTranslationY((z52 ? -AndroidUtilities.m1036dp(36.0f) : 0) + getSearchFieldAdditionOffset());
            }
            if (this.dialogStoriesCell != null) {
                if (this.dialogStoriesCellVisible && !isInPreviewMode() && !z52) {
                    this.dialogStoriesCell.setVisibility(0);
                } else {
                    this.dialogStoriesCell.setVisibility(8);
                }
            }
            setSearchAnimationProgress(z52 ? 1.0f : 0.0f, false);
            this.fragmentView.invalidate();
        }
        int i2 = this.initialSearchType;
        if (i2 >= 0 && (searchViewPager2 = this.searchViewPager) != null) {
            searchViewPager2.setPosition(searchViewPager2.getPositionForType(i2));
        }
        if (!z52) {
            this.initialSearchType = -1;
        }
        if (z52 && z2 && (searchViewPager = this.searchViewPager) != null) {
            searchViewPager.showDownloads();
            updateSpeedItem(true);
        }
        checkUi_searchFiltersVisibility();
        updateDialogsHint();
    }

    public /* synthetic */ void lambda$showSearch$90(ValueAnimator valueAnimator) {
        setSearchAnimationProgress(((Float) valueAnimator.getAnimatedValue()).floatValue(), false);
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$32 */
    public class C554632 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$show;

        public C554632(boolean z52) {
            z = z52;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            DialogsActivity.this.notificationsLocker.unlock();
            if (DialogsActivity.this.searchAnimator != animator) {
                return;
            }
            DialogsActivity.this.setDialogsListFrozen(false);
            boolean z9 = z;
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (z9) {
                dialogsActivity.viewPages[0].listView.hide();
                DialogStoriesCell dialogStoriesCell2 = DialogsActivity.this.dialogStoriesCell;
                if (dialogStoriesCell2 != null) {
                    dialogStoriesCell2.setVisibility(8);
                }
                DialogsActivity.this.searchWasFullyShowed = true;
                AndroidUtilities.requestAdjustResize(DialogsActivity.this.getParentActivity(), ((BaseFragment) DialogsActivity.this).classGuid);
                DialogsActivity.this.searchItem.setVisibility(8);
                RightSlidingDialogContainer rightSlidingDialogContainer3 = DialogsActivity.this.rightSlidingDialogContainer;
                if (rightSlidingDialogContainer3 != null) {
                    rightSlidingDialogContainer3.setVisibility(8);
                }
            } else {
                dialogsActivity.whiteActionBar = false;
                if (dialogsActivity.searchViewPager != null) {
                    DialogsActivity.this.searchViewPager.setVisibility(8);
                }
                if (DialogsActivity.this.fragmentSearchField != null) {
                    DialogsActivity.this.fragmentSearchField.clearSearchFilters();
                }
                if (DialogsActivity.this.searchViewPager != null) {
                    DialogsActivity.this.searchViewPager.clear();
                }
                DialogsActivity.this.viewPages[0].listView.show();
                DialogsActivity.this.searchWasFullyShowed = false;
                RightSlidingDialogContainer rightSlidingDialogContainer4 = DialogsActivity.this.rightSlidingDialogContainer;
                if (rightSlidingDialogContainer4 != null) {
                    rightSlidingDialogContainer4.setVisibility(0);
                }
            }
            View view = DialogsActivity.this.fragmentView;
            if (view != null) {
                view.requestLayout();
            }
            DialogsActivity.this.setSearchAnimationProgress(z ? 1.0f : 0.0f, false);
            DialogsActivity.this.viewPages[0].listView.setVerticalScrollBarEnabled(true);
            if (DialogsActivity.this.searchViewPager != null) {
                DialogsActivity.this.searchViewPager.setBackground(null);
            }
            DialogsActivity.this.searchAnimator = null;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            DialogsActivity.this.notificationsLocker.unlock();
            if (DialogsActivity.this.searchAnimator == animator) {
                boolean z9 = z;
                DialogsActivity dialogsActivity = DialogsActivity.this;
                if (z9) {
                    dialogsActivity.viewPages[0].listView.hide();
                } else {
                    dialogsActivity.viewPages[0].listView.show();
                }
                DialogsActivity.this.searchAnimator = null;
            }
        }
    }

    public boolean onlyDialogsAdapter() {
        int totalDialogsCount = getMessagesController().getTotalDialogsCount();
        if (this.onlySelect) {
            return true;
        }
        return totalDialogsCount <= 10 && !this.hasStories;
    }

    private void updateFilterTabsVisibility(boolean z) {
        if (this.fragmentView == null) {
            return;
        }
        if (this.isPaused || this.databaseMigrationHint != null) {
            z = false;
        }
        if (this.searchIsShowed) {
            return;
        }
        this.animatorFilterTabsVisible.setValue(this.canShowFilterTabsView, z);
    }

    public void setSearchAnimationProgress(float f, boolean z) {
        this.searchAnimationProgress = f;
        boolean z2 = true;
        if (this.whiteActionBar && this.actionBar != null) {
            int themedColor = getThemedColor(this.folderId != 0 ? Theme.key_actionBarDefaultArchivedIcon : Theme.key_actionBarDefaultIcon);
            ActionBar actionBar = this.actionBar;
            int i = Theme.key_actionBarActionModeDefaultIcon;
            actionBar.setItemsColor(ColorUtils.blendARGB(themedColor, getThemedColor(i), this.searchAnimationProgress), false);
            this.actionBar.setItemsColor(ColorUtils.blendARGB(getThemedColor(i), getThemedColor(i), this.searchAnimationProgress), true);
            this.actionBar.setItemsBackgroundColor(ColorUtils.blendARGB(getThemedColor(this.folderId != 0 ? Theme.key_actionBarDefaultArchivedSelector : Theme.key_actionBarDefaultSelector), getThemedColor(Theme.key_actionBarActionModeDefaultSelector), this.searchAnimationProgress), false);
        }
        View view = this.fragmentView;
        if (view != null) {
            view.invalidate();
        }
        if (SharedConfig.getDevicePerformanceClass() != 0 && LiteMode.isEnabled(32768)) {
            z2 = false;
        }
        if (z) {
            ViewPage viewPage = this.viewPages[0];
            if (viewPage != null) {
                if (f < 1.0f) {
                    viewPage.setVisibility(0);
                }
                this.viewPages[0].setAlpha(1.0f - f);
                if (!z2) {
                    float f2 = (0.1f * f) + 0.9f;
                    this.viewPages[0].setScaleX(f2);
                    this.viewPages[0].setScaleY(f2);
                }
            }
            RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
            if (rightSlidingDialogContainer != null) {
                if (f >= 1.0f) {
                    rightSlidingDialogContainer.setVisibility(8);
                } else {
                    rightSlidingDialogContainer.setVisibility(0);
                    this.rightSlidingDialogContainer.setAlpha(1.0f - f);
                }
            }
            SearchViewPager searchViewPager = this.searchViewPager;
            if (searchViewPager != null) {
                searchViewPager.setAlpha(f);
                if (!z2) {
                    float f3 = ((1.0f - f) * 0.05f) + 1.0f;
                    this.searchViewPager.setScaleX(f3);
                    this.searchViewPager.setScaleY(f3);
                }
            }
        }
        updateContextViewPosition();
    }

    public void findAndUpdateCheckBox(long j, boolean z) {
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr == null) {
            return;
        }
        for (ViewPage viewPage : viewPageArr) {
            int childCount = viewPage.listView.getChildCount();
            int i = 0;
            while (true) {
                if (i < childCount) {
                    View childAt = viewPage.listView.getChildAt(i);
                    if (childAt instanceof DialogCell) {
                        DialogCell dialogCell = (DialogCell) childAt;
                        if (dialogCell.getDialogId() == j) {
                            dialogCell.setChecked(z, true);
                            break;
                        }
                    }
                    i++;
                }
            }
        }
    }

    public void checkListLoad(ViewPage viewPage) {
        checkListLoad(viewPage, viewPage.layoutManager.findFirstVisibleItemPosition(), viewPage.layoutManager.findLastVisibleItemPosition());
    }

    /* JADX WARN: Removed duplicated region for block: B:145:0x00d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void checkListLoad(org.telegram.ui.DialogsActivity.ViewPage r16, int r17, int r18) {
        /*
            Method dump skipped, instruction units count: 317
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.checkListLoad(org.telegram.ui.DialogsActivity$ViewPage, int, int):void");
    }

    public /* synthetic */ void lambda$checkListLoad$91(boolean z, boolean z2, boolean z3, boolean z4) {
        if (z) {
            getMessagesController().loadDialogs(this.folderId, -1, 100, z2);
        }
        if (z3) {
            getMessagesController().loadDialogs(1, -1, 100, z4);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:370:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:564:0x038e  */
    /* JADX WARN: Removed duplicated region for block: B:565:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:575:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:583:0x03c6  */
    /* JADX WARN: Removed duplicated region for block: B:586:0x03d7  */
    /* JADX WARN: Removed duplicated region for block: B:615:0x0464  */
    /* JADX WARN: Removed duplicated region for block: B:620:0x047b  */
    /* JADX WARN: Removed duplicated region for block: B:629:0x04c0  */
    /* JADX WARN: Removed duplicated region for block: B:633:0x04e3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void onItemClick(android.view.View r22, int r23, androidx.recyclerview.widget.RecyclerView.Adapter r24, float r25, float r26) {
        /*
            Method dump skipped, instruction units count: 1586
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.onItemClick(android.view.View, int, androidx.recyclerview.widget.RecyclerView$Adapter, float, float):void");
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$33 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C554733 extends TopicsFragment {
        public C554733(Bundle bundle) {
            super(bundle);
        }
    }

    private boolean isBotForumWithEmptyTopics(long j) {
        if (j < 0) {
            return false;
        }
        TLRPC.User user = getMessagesController().getUser(Long.valueOf(j));
        if (!UserObject.isBotForum(user)) {
            return false;
        }
        ArrayList<TLRPC.TL_forumTopic> topics = MessagesController.getInstance(this.currentAccount).getTopicsController().getTopics(-user.f1407id);
        return (topics == null || topics.isEmpty()) && MessagesController.getInstance(this.currentAccount).getTopicsController().endIsReached(-user.f1407id);
    }

    public static ChatActivity highlightFoundQuote(ChatActivity chatActivity, MessageObject messageObject) {
        CharSequence charSequence;
        if (messageObject != null && messageObject.hasHighlightedWords()) {
            try {
                if (!TextUtils.isEmpty(messageObject.caption)) {
                    charSequence = messageObject.caption;
                } else {
                    charSequence = messageObject.messageText;
                }
                CharSequence charSequenceHighlightText = AndroidUtilities.highlightText(charSequence, messageObject.highlightedWords, (Theme.ResourcesProvider) null);
                if (charSequenceHighlightText instanceof SpannableStringBuilder) {
                    SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) charSequenceHighlightText;
                    ForegroundColorSpanThemable[] foregroundColorSpanThemableArr = (ForegroundColorSpanThemable[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ForegroundColorSpanThemable.class);
                    if (foregroundColorSpanThemableArr.length > 0) {
                        int spanStart = spannableStringBuilder.getSpanStart(foregroundColorSpanThemableArr[0]);
                        int spanEnd = spannableStringBuilder.getSpanEnd(foregroundColorSpanThemableArr[0]);
                        for (int i = 1; i < foregroundColorSpanThemableArr.length; i++) {
                            int spanStart2 = spannableStringBuilder.getSpanStart(foregroundColorSpanThemableArr[i]);
                            int spanStart3 = spannableStringBuilder.getSpanStart(foregroundColorSpanThemableArr[i]);
                            if (spanStart2 != spanEnd) {
                                if (spanStart2 > spanEnd) {
                                    for (int i2 = spanEnd; i2 <= spanStart2; i2++) {
                                        if (!Character.isWhitespace(spannableStringBuilder.charAt(i2))) {
                                            break;
                                        }
                                    }
                                }
                            }
                            spanEnd = spanStart3;
                        }
                        chatActivity.setHighlightQuote(messageObject.getRealId(), charSequence.subSequence(spanStart, spanEnd).toString(), spanStart);
                        return chatActivity;
                    }
                }
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
        return chatActivity;
    }

    public void setOpenedDialogId(long j, long j2) {
        MessagesStorage.TopicKey topicKey = this.openedDialogId;
        topicKey.dialogId = j;
        topicKey.topicId = j2;
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr == null) {
            return;
        }
        for (ViewPage viewPage : viewPageArr) {
            if (viewPage.isDefaultDialogType() && AndroidUtilities.isTablet()) {
                viewPage.dialogsAdapter.setOpenedDialogId(this.openedDialogId.dialogId);
            }
        }
        updateVisibleRows(MessagesController.UPDATE_MASK_SELECT_DIALOG);
    }

    public boolean onItemLongClick(RecyclerListView recyclerListView, View view, int i, float f, float f2, int i2, RecyclerView.Adapter adapter) {
        DialogsSearchAdapter dialogsSearchAdapter;
        DialogsSearchAdapter dialogsSearchAdapter2;
        final long jMakeEncryptedDialogId;
        if (getParentActivity() == null || (view instanceof DialogsHintCell) || adapter.getItemViewType(i) == 21) {
            return false;
        }
        resetPendingTabsSwipeTracking();
        if (!this.actionBar.isActionModeShowed() && !AndroidUtilities.isTablet() && !this.onlySelect && (view instanceof DialogCell)) {
            DialogCell dialogCell = (DialogCell) view;
            if (!getMessagesController().isForum(dialogCell.getDialogId()) && !this.rightSlidingDialogContainer.hasFragment() && dialogCell.isPointInsideAvatar(f, f2)) {
                return showChatPreview(dialogCell);
            }
        }
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if (rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment()) {
            return false;
        }
        SearchViewPager searchViewPager = this.searchViewPager;
        if (searchViewPager != null && adapter == (dialogsSearchAdapter2 = searchViewPager.dialogsSearchAdapter)) {
            Object item = dialogsSearchAdapter2.getItem(i);
            if (!this.searchViewPager.dialogsSearchAdapter.isSearchWas()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
                builder.setTitle(LocaleController.getString(C2797R.string.ClearSearchSingleAlertTitle));
                if (item instanceof TLRPC.Chat) {
                    TLRPC.Chat chat = (TLRPC.Chat) item;
                    if (chat.monoforum) {
                        builder.setMessage(LocaleController.formatString("ClearSearchSingleChatAlertText", C2797R.string.ClearSearchSingleChatAlertText, ForumUtilities.getMonoForumTitle(this.currentAccount, chat)));
                    } else {
                        builder.setMessage(LocaleController.formatString("ClearSearchSingleChatAlertText", C2797R.string.ClearSearchSingleChatAlertText, chat.title));
                    }
                    jMakeEncryptedDialogId = -chat.f1245id;
                } else if (item instanceof TLRPC.User) {
                    TLRPC.User user = (TLRPC.User) item;
                    if (user.f1407id == getUserConfig().clientUserId) {
                        builder.setMessage(LocaleController.formatString("ClearSearchSingleChatAlertText", C2797R.string.ClearSearchSingleChatAlertText, LocaleController.getString(C2797R.string.SavedMessages)));
                    } else {
                        builder.setMessage(LocaleController.formatString("ClearSearchSingleUserAlertText", C2797R.string.ClearSearchSingleUserAlertText, ContactsController.formatName(user.first_name, user.last_name)));
                    }
                    jMakeEncryptedDialogId = user.f1407id;
                } else {
                    if (!(item instanceof TLRPC.EncryptedChat)) {
                        return false;
                    }
                    TLRPC.User user2 = getMessagesController().getUser(Long.valueOf(((TLRPC.EncryptedChat) item).user_id));
                    builder.setMessage(LocaleController.formatString("ClearSearchSingleUserAlertText", C2797R.string.ClearSearchSingleUserAlertText, ContactsController.formatName(user2.first_name, user2.last_name)));
                    jMakeEncryptedDialogId = DialogObject.makeEncryptedDialogId(r9.f1257id);
                }
                builder.setPositiveButton(LocaleController.getString(C2797R.string.ClearSearchRemove), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda133
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i3) {
                        this.f$0.lambda$onItemLongClick$92(jMakeEncryptedDialogId, alertDialog, i3);
                    }
                });
                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                AlertDialog alertDialogCreate = builder.create();
                showDialog(alertDialogCreate);
                TextView textView = (TextView) alertDialogCreate.getButton(-1);
                if (textView != null) {
                    textView.setTextColor(getThemedColor(Theme.key_text_RedBold));
                }
                return true;
            }
        }
        SearchViewPager searchViewPager2 = this.searchViewPager;
        if (searchViewPager2 != null && adapter == (dialogsSearchAdapter = searchViewPager2.dialogsSearchAdapter)) {
            if (this.onlySelect) {
                onItemClick(view, i, adapter, f, f2);
                return false;
            }
            long dialogId = (!(view instanceof ProfileSearchCell) || dialogsSearchAdapter.isGlobalSearch(i)) ? 0L : ((ProfileSearchCell) view).getDialogId();
            if (dialogId == 0) {
                return false;
            }
            showOrUpdateActionMode(dialogId, view);
            return true;
        }
        TLObject item2 = ((DialogsAdapter) adapter).getItem(i);
        if (item2 instanceof TLRPC.Dialog) {
            TLRPC.Dialog dialog = (TLRPC.Dialog) item2;
            if (this.onlySelect) {
                if ((this.initialDialogsType != 3 && !clickSelectsDialog()) || !validateSlowModeDialog(dialog.f1251id)) {
                    return false;
                }
                if (this.initialDialogsType == 1 && clickSelectsDialog() && this.canSelectTopics && getMessagesController().isForum(dialog.f1251id)) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("chat_id", -dialog.f1251id);
                    bundle.putBoolean("for_select", true);
                    bundle.putBoolean("forward_to", true);
                    bundle.putBoolean("bot_share_to", this.initialDialogsType == 1);
                    bundle.putBoolean("quote", this.isQuote);
                    bundle.putBoolean("reply_to", this.isReplyTo);
                    TopicsFragment topicsFragment = new TopicsFragment(bundle);
                    topicsFragment.setForwardFromDialogFragment(this);
                    presentFragment(topicsFragment);
                    return false;
                }
                addOrRemoveSelectedDialog(dialog.f1251id, view);
                updateSelectedCount();
            } else {
                if (dialog instanceof TLRPC.TL_dialogFolder) {
                    onArchiveLongPress(view);
                    return false;
                }
                if (this.actionBar.isActionModeShowed() && isDialogPinned(dialog)) {
                    return false;
                }
                showOrUpdateActionMode(dialog.f1251id, view);
            }
            return true;
        }
        return false;
    }

    public /* synthetic */ void lambda$onItemLongClick$92(long j, AlertDialog alertDialog, int i) {
        this.searchViewPager.dialogsSearchAdapter.removeRecentSearch(j);
    }

    public void startMultiselect(RecyclerListView recyclerListView, int i, DialogsAdapter dialogsAdapter) {
        if (getDialogForMultiselect(i, dialogsAdapter) == null) {
            return;
        }
        recyclerListView.startMultiselect(i, false, new RecyclerListView.onMultiSelectionChanged() { // from class: org.telegram.ui.DialogsActivity.34
            final /* synthetic */ DialogsAdapter val$adapter;
            final /* synthetic */ HashSet val$alreadySelectedDialogs;
            final /* synthetic */ RecyclerListView val$listView;
            final /* synthetic */ boolean val$unselect;

            @Override // org.telegram.ui.Components.RecyclerListView.onMultiSelectionChanged
            public int checkPosition(int i2, boolean z) {
                return i2;
            }

            @Override // org.telegram.ui.Components.RecyclerListView.onMultiSelectionChanged
            public boolean limitReached() {
                return false;
            }

            public C554834(DialogsAdapter dialogsAdapter2, boolean z, RecyclerListView recyclerListView2, HashSet hashSet) {
                dialogsAdapter = dialogsAdapter2;
                z = z;
                recyclerListView = recyclerListView2;
                hashSet = hashSet;
            }

            @Override // org.telegram.ui.Components.RecyclerListView.onMultiSelectionChanged
            public void onSelectionChanged(int i2, boolean z, float f, float f2) {
                TLRPC.Dialog dialogForMultiselect = DialogsActivity.this.getDialogForMultiselect(i2, dialogsAdapter);
                if (dialogForMultiselect == null) {
                    return;
                }
                if (z) {
                    z = !z;
                }
                if (z == DialogsActivity.this.selectedDialogs.contains(Long.valueOf(dialogForMultiselect.f1251id))) {
                    return;
                }
                RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = recyclerListView.findViewHolderForAdapterPosition(i2);
                DialogsActivity.this.showOrUpdateActionMode(dialogForMultiselect.f1251id, viewHolderFindViewHolderForAdapterPosition != null ? viewHolderFindViewHolderForAdapterPosition.itemView : null);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.onMultiSelectionChanged
            public boolean canSelect(int i2) {
                TLRPC.Dialog dialogForMultiselect = DialogsActivity.this.getDialogForMultiselect(i2, dialogsAdapter);
                if (dialogForMultiselect == null) {
                    return false;
                }
                boolean z = z;
                HashSet hashSet = hashSet;
                if (z) {
                    return hashSet.contains(Long.valueOf(dialogForMultiselect.f1251id));
                }
                return !hashSet.contains(Long.valueOf(dialogForMultiselect.f1251id));
            }

            @Override // org.telegram.ui.Components.RecyclerListView.onMultiSelectionChanged
            public void getPaddings(int[] iArr) {
                iArr[0] = recyclerListView.getPaddingTop();
                iArr[1] = recyclerListView.getPaddingBottom();
            }

            @Override // org.telegram.ui.Components.RecyclerListView.onMultiSelectionChanged
            public void scrollBy(int i2) {
                recyclerListView.scrollBy(0, i2);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.onMultiSelectionChanged
            public int getStartDragDistance() {
                return AndroidUtilities.m1036dp(24.0f);
            }
        });
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$34 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C554834 implements RecyclerListView.onMultiSelectionChanged {
        final /* synthetic */ DialogsAdapter val$adapter;
        final /* synthetic */ HashSet val$alreadySelectedDialogs;
        final /* synthetic */ RecyclerListView val$listView;
        final /* synthetic */ boolean val$unselect;

        @Override // org.telegram.ui.Components.RecyclerListView.onMultiSelectionChanged
        public int checkPosition(int i2, boolean z) {
            return i2;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.onMultiSelectionChanged
        public boolean limitReached() {
            return false;
        }

        public C554834(DialogsAdapter dialogsAdapter2, boolean z, RecyclerListView recyclerListView2, HashSet hashSet) {
            dialogsAdapter = dialogsAdapter2;
            z = z;
            recyclerListView = recyclerListView2;
            hashSet = hashSet;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.onMultiSelectionChanged
        public void onSelectionChanged(int i2, boolean z, float f, float f2) {
            TLRPC.Dialog dialogForMultiselect = DialogsActivity.this.getDialogForMultiselect(i2, dialogsAdapter);
            if (dialogForMultiselect == null) {
                return;
            }
            if (z) {
                z = !z;
            }
            if (z == DialogsActivity.this.selectedDialogs.contains(Long.valueOf(dialogForMultiselect.f1251id))) {
                return;
            }
            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = recyclerListView.findViewHolderForAdapterPosition(i2);
            DialogsActivity.this.showOrUpdateActionMode(dialogForMultiselect.f1251id, viewHolderFindViewHolderForAdapterPosition != null ? viewHolderFindViewHolderForAdapterPosition.itemView : null);
        }

        @Override // org.telegram.ui.Components.RecyclerListView.onMultiSelectionChanged
        public boolean canSelect(int i2) {
            TLRPC.Dialog dialogForMultiselect = DialogsActivity.this.getDialogForMultiselect(i2, dialogsAdapter);
            if (dialogForMultiselect == null) {
                return false;
            }
            boolean z = z;
            HashSet hashSet = hashSet;
            if (z) {
                return hashSet.contains(Long.valueOf(dialogForMultiselect.f1251id));
            }
            return !hashSet.contains(Long.valueOf(dialogForMultiselect.f1251id));
        }

        @Override // org.telegram.ui.Components.RecyclerListView.onMultiSelectionChanged
        public void getPaddings(int[] iArr) {
            iArr[0] = recyclerListView.getPaddingTop();
            iArr[1] = recyclerListView.getPaddingBottom();
        }

        @Override // org.telegram.ui.Components.RecyclerListView.onMultiSelectionChanged
        public void scrollBy(int i2) {
            recyclerListView.scrollBy(0, i2);
        }

        @Override // org.telegram.ui.Components.RecyclerListView.onMultiSelectionChanged
        public int getStartDragDistance() {
            return AndroidUtilities.m1036dp(24.0f);
        }
    }

    public TLRPC.Dialog getDialogForMultiselect(int i, DialogsAdapter dialogsAdapter) {
        TLObject item = dialogsAdapter.getItem(i);
        if (!(item instanceof TLRPC.Dialog) || (item instanceof TLRPC.TL_dialogFolder)) {
            return null;
        }
        return (TLRPC.Dialog) item;
    }

    private void onArchiveLongPress(View view) {
        try {
            view.performHapticFeedback(0, 2);
        } catch (Exception unused) {
        }
        BottomSheet.Builder builder = new BottomSheet.Builder(getParentActivity());
        boolean z = getMessagesStorage().getArchiveUnreadCount() != 0;
        builder.setItems(new CharSequence[]{z ? LocaleController.getString(C2797R.string.MarkAllAsRead) : null, LocaleController.getString(SharedConfig.archiveHidden ? C2797R.string.PinInTheList : C2797R.string.HideAboveTheList)}, new int[]{z ? C2797R.drawable.msg_markread : 0, SharedConfig.archiveHidden ? C2797R.drawable.chats_pin : C2797R.drawable.chats_unpin}, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda104
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$onArchiveLongPress$93(dialogInterface, i);
            }
        });
        showDialog(builder.create());
    }

    public /* synthetic */ void lambda$onArchiveLongPress$93(DialogInterface dialogInterface, int i) {
        ViewPage[] viewPageArr;
        if (i == 0) {
            getMessagesStorage().readAllDialogs(1);
            return;
        }
        if (i != 1 || (viewPageArr = this.viewPages) == null) {
            return;
        }
        for (ViewPage viewPage : viewPageArr) {
            if (viewPage.dialogsType == 0 && viewPage.getVisibility() == 0) {
                viewPage.listView.toggleArchiveHidden(true, findArchiveDialogCell(viewPage));
            }
        }
    }

    public DialogCell findArchiveDialogCell(ViewPage viewPage) {
        DialogsRecyclerView dialogsRecyclerView = viewPage.listView;
        for (int i = 0; i < dialogsRecyclerView.getChildCount(); i++) {
            View childAt = dialogsRecyclerView.getChildAt(i);
            if (childAt instanceof DialogCell) {
                DialogCell dialogCell = (DialogCell) childAt;
                if (dialogCell.isFolderCell()) {
                    return dialogCell;
                }
            }
        }
        return null;
    }

    private void resetPendingTabsSwipeTracking() {
        View view = this.fragmentView;
        if (view instanceof ContentView) {
            ((ContentView) view).resetTabsSwipeTracking();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4, types: [boolean] */
    public boolean showChatPreview(final DialogCell dialogCell) {
        long j;
        TLRPC.Chat chat;
        boolean z;
        int i;
        View view;
        MessagesController.DialogFilter dialogFilter;
        boolean[] zArr;
        long j2;
        ?? r8;
        final long j3;
        boolean z2;
        int i2;
        int i3;
        int i4;
        int size;
        int i5;
        ActionBarMenuSubItem actionBarMenuSubItem;
        boolean z3;
        LinearLayout linearLayout;
        C554935 c554935;
        LinearLayout linearLayout2;
        C554935 c5549352;
        boolean z4 = false;
        if (dialogCell.isDialogFolder()) {
            if (dialogCell.getCurrentDialogFolderId() == 1) {
                onArchiveLongPress(dialogCell);
            }
            return false;
        }
        final long dialogId = dialogCell.getDialogId();
        Bundle bundle = new Bundle();
        int messageId = dialogCell.getMessageId();
        if (DialogObject.isEncryptedDialog(dialogId)) {
            return false;
        }
        if (DialogObject.isUserDialog(dialogId)) {
            bundle.putLong("user_id", dialogId);
        } else {
            if (messageId == 0 || (chat = getMessagesController().getChat(Long.valueOf(-dialogId))) == null || chat.migrated_to == null) {
                j = dialogId;
            } else {
                bundle.putLong("migrated_to", dialogId);
                j = -chat.migrated_to.channel_id;
            }
            bundle.putLong("chat_id", -j);
        }
        if (messageId != 0) {
            bundle.putInt("message_id", messageId);
        }
        final ArrayList arrayList = new ArrayList();
        arrayList.add(Long.valueOf(dialogId));
        boolean z5 = getMessagesController().filtersEnabled && getMessagesController().dialogFiltersLoaded && getMessagesController().dialogFilters != null && getMessagesController().dialogFilters.size() > 0;
        final ActionBarPopupWindow.ActionBarPopupWindowLayout[] actionBarPopupWindowLayoutArr = new ActionBarPopupWindow.ActionBarPopupWindowLayout[1];
        if (z5) {
            LinearLayout linearLayout3 = new LinearLayout(getParentActivity());
            linearLayout3.setOrientation(1);
            C554935 c5549353 = new ScrollView(getParentActivity()) { // from class: org.telegram.ui.DialogsActivity.35
                public C554935(Context context) {
                    super(context);
                }

                @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.View
                public void onMeasure(int i6, int i7) {
                    super.onMeasure(i6, View.MeasureSpec.makeMeasureSpec((int) Math.min(View.MeasureSpec.getSize(i7), Math.min(AndroidUtilities.displaySize.y * 0.35f, AndroidUtilities.m1036dp(400.0f))), View.MeasureSpec.getMode(i7)));
                }
            };
            LinearLayout linearLayout4 = new LinearLayout(getParentActivity());
            linearLayout4.setOrientation(1);
            c5549353.addView(linearLayout4);
            int size2 = getMessagesController().dialogFilters.size();
            int i6 = 0;
            ActionBarMenuSubItem actionBarMenuSubItem2 = null;
            C554935 c5549354 = c5549353;
            while (i6 < size2) {
                final MessagesController.DialogFilter dialogFilter2 = getMessagesController().dialogFilters.get(i6);
                if (dialogFilter2.isDefault()) {
                    i5 = i6;
                    c5549352 = c5549354;
                } else {
                    final boolean zIncludesDialog = dialogFilter2.includesDialog(AccountInstance.getInstance(this.currentAccount), dialogId);
                    i5 = i6;
                    C554935 c5549355 = c5549354;
                    final ArrayList<Long> dialogsCount = FiltersListBottomSheet.getDialogsCount(this, dialogFilter2, arrayList, true, z4);
                    if (!zIncludesDialog) {
                        c5549352 = c5549355;
                        if (dialogFilter2.alwaysShow.size() + dialogsCount.size() > 100) {
                        }
                        i6 = i5 + 1;
                        linearLayout3 = linearLayout2;
                        c5549354 = c554935;
                        linearLayout4 = linearLayout;
                        z5 = z3;
                        actionBarMenuSubItem2 = actionBarMenuSubItem;
                        z4 = false;
                    }
                    actionBarMenuSubItem = new ActionBarMenuSubItem((Context) getParentActivity(), 2, false, false, (Theme.ResourcesProvider) null);
                    actionBarMenuSubItem.setChecked(zIncludesDialog);
                    LinearLayout linearLayout5 = linearLayout3;
                    Spannable spannableReplaceAnimatedEmoji = MessageObject.replaceAnimatedEmoji(Emoji.replaceEmoji(dialogFilter2.name, actionBarMenuSubItem.getTextView().getPaint().getFontMetricsInt(), false), dialogFilter2.entities, actionBarMenuSubItem.getTextView().getPaint().getFontMetricsInt());
                    actionBarMenuSubItem.setEmojiCacheType(dialogFilter2.title_noanimate ? 26 : 0);
                    final long j4 = dialogId;
                    actionBarMenuSubItem.setTextAndIcon(spannableReplaceAnimatedEmoji, 0, new FolderDrawable(getContext(), C2797R.drawable.msg_folders, dialogFilter2.color));
                    actionBarMenuSubItem.getTextView().setEmojiColor(getThemedColor(Theme.key_featuredStickers_addButton));
                    actionBarMenuSubItem.setMinimumWidth(160);
                    z3 = z5;
                    linearLayout = linearLayout4;
                    c554935 = c5549355;
                    linearLayout2 = linearLayout5;
                    dialogId = j4;
                    actionBarMenuSubItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda47
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            this.f$0.lambda$showChatPreview$94(zIncludesDialog, dialogsCount, dialogFilter2, j4, view2);
                        }
                    });
                    linearLayout.addView(actionBarMenuSubItem);
                    i6 = i5 + 1;
                    linearLayout3 = linearLayout2;
                    c5549354 = c554935;
                    linearLayout4 = linearLayout;
                    z5 = z3;
                    actionBarMenuSubItem2 = actionBarMenuSubItem;
                    z4 = false;
                }
                linearLayout2 = linearLayout3;
                z3 = z5;
                actionBarMenuSubItem = actionBarMenuSubItem2;
                c554935 = c5549352;
                linearLayout = linearLayout4;
                i6 = i5 + 1;
                linearLayout3 = linearLayout2;
                c5549354 = c554935;
                linearLayout4 = linearLayout;
                z5 = z3;
                actionBarMenuSubItem2 = actionBarMenuSubItem;
                z4 = false;
            }
            ViewGroup viewGroup = linearLayout3;
            C554935 c5549356 = c5549354;
            LinearLayout linearLayout6 = linearLayout4;
            z = z5;
            i = 160;
            if (actionBarMenuSubItem2 != null) {
                actionBarMenuSubItem2.updateSelectorBackground(false, true);
            }
            if (linearLayout6.getChildCount() <= 0) {
                view = viewGroup;
                z = false;
            } else {
                ActionBarPopupWindow.GapView gapView = new ActionBarPopupWindow.GapView(getParentActivity(), getResourceProvider(), Theme.key_actionBarDefaultSubmenuSeparator);
                gapView.setTag(C2797R.id.fit_width_tag, 1);
                ActionBarMenuSubItem actionBarMenuSubItem3 = new ActionBarMenuSubItem(getParentActivity(), true, false);
                actionBarMenuSubItem3.setTextAndIcon(LocaleController.getString(C2797R.string.Back), C2797R.drawable.ic_ab_back);
                actionBarMenuSubItem3.setMinimumWidth(160);
                actionBarMenuSubItem3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda48
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        DialogsActivity.$r8$lambda$ughQYe6_dhdDK1ZcQkKXaRufAyo(actionBarPopupWindowLayoutArr, view2);
                    }
                });
                viewGroup.addView(actionBarMenuSubItem3);
                viewGroup.addView(gapView, LayoutHelper.createLinear(-1, 8));
                viewGroup.addView(c5549356);
                view = viewGroup;
            }
        } else {
            z = z5;
            i = 160;
            view = null;
        }
        int i7 = z ? 3 : 2;
        final WeakReference[] weakReferenceArr = {new WeakReference(null)};
        ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout = new ActionBarPopupWindow.ActionBarPopupWindowLayout(getParentActivity(), C2797R.drawable.popup_fixed_alert4, getResourceProvider(), i7);
        actionBarPopupWindowLayoutArr[0] = actionBarPopupWindowLayout;
        if (z) {
            final int[] iArr = {actionBarPopupWindowLayout.addViewToSwipeBack(view)};
            ActionBarMenuSubItem actionBarMenuSubItem4 = new ActionBarMenuSubItem(getParentActivity(), true, false);
            actionBarMenuSubItem4.setTextAndIcon(LocaleController.getString(C2797R.string.FilterAddTo), C2797R.drawable.msg_addfolder);
            actionBarMenuSubItem4.setMinimumWidth(i);
            actionBarMenuSubItem4.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda49
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    actionBarPopupWindowLayoutArr[0].getSwipeBack().openForeground(iArr[0]);
                }
            });
            actionBarPopupWindowLayoutArr[0].addView(actionBarMenuSubItem4);
            actionBarPopupWindowLayoutArr[0].getSwipeBack().setOnHeightUpdateListener(new PopupSwipeBackLayout.IntCallback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda50
                @Override // org.telegram.ui.Components.PopupSwipeBackLayout.IntCallback
                public final void run(int i8) {
                    DialogsActivity.$r8$lambda$f0_AxCjcjKpWD4DoWvM1cL6m1VA(weakReferenceArr, i8);
                }
            });
        }
        ActionBarMenuSubItem actionBarMenuSubItem5 = new ActionBarMenuSubItem(getParentActivity(), !z, false);
        if (dialogCell.getHasUnread()) {
            actionBarMenuSubItem5.setTextAndIcon(LocaleController.getString(C2797R.string.MarkAsRead), C2797R.drawable.msg_markread);
        } else {
            actionBarMenuSubItem5.setTextAndIcon(LocaleController.getString(C2797R.string.MarkAsUnread), C2797R.drawable.msg_markunread);
        }
        actionBarMenuSubItem5.setMinimumWidth(i);
        actionBarMenuSubItem5.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda51
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$showChatPreview$98(dialogCell, dialogId, view2);
            }
        });
        actionBarPopupWindowLayoutArr[0].addView(actionBarMenuSubItem5);
        boolean[] zArr2 = {true};
        final TLRPC.Dialog dialog = getMessagesController().dialogs_dict.get(dialogId);
        boolean z6 = (this.viewPages[0].dialogsType == 7 || this.viewPages[0].dialogsType == 8) && (!this.actionBar.isActionModeShowed() || this.actionBar.isActionModeShowed(null));
        if (z6) {
            dialogFilter = getMessagesController().selectedDialogFilter[this.viewPages[0].dialogsType == 8 ? (char) 1 : (char) 0];
        } else {
            dialogFilter = null;
        }
        if (isDialogPinned(dialog)) {
            zArr = zArr2;
            j2 = dialogId;
            r8 = 0;
        } else {
            ArrayList<TLRPC.Dialog> dialogs = getMessagesController().getDialogs(this.folderId);
            int size3 = dialogs.size();
            int i8 = 0;
            int i9 = 0;
            int i10 = 0;
            while (true) {
                if (i8 >= size3) {
                    zArr = zArr2;
                    j2 = dialogId;
                    break;
                }
                TLRPC.Dialog dialog2 = dialogs.get(i8);
                zArr = zArr2;
                if (dialog2 instanceof TLRPC.TL_dialogFolder) {
                    j2 = dialogId;
                } else if (isDialogPinned(dialog2)) {
                    j2 = dialogId;
                    if (DialogObject.isEncryptedDialog(dialog2.f1251id)) {
                        i10++;
                    } else {
                        i9++;
                    }
                } else {
                    j2 = dialogId;
                    if (!getMessagesController().isPromoDialog(dialog2.f1251id, false)) {
                        break;
                    }
                }
                i8++;
                zArr2 = zArr;
                dialogId = j2;
            }
            if (dialog == null || isDialogPinned(dialog)) {
                i2 = 0;
                i3 = 0;
                i4 = 0;
            } else {
                boolean zIsEncryptedDialog = DialogObject.isEncryptedDialog(j2);
                int i11 = !zIsEncryptedDialog ? 1 : 0;
                if (dialogFilter == null || !dialogFilter.alwaysShow.contains(Long.valueOf(j2))) {
                    i4 = i11;
                    i3 = zIsEncryptedDialog ? 1 : 0;
                    i2 = 0;
                } else {
                    i4 = i11;
                    i3 = zIsEncryptedDialog ? 1 : 0;
                    i2 = 1;
                }
            }
            if (z6 && dialogFilter != null) {
                size = 100 - dialogFilter.alwaysShow.size();
            } else if (this.folderId != 0 || dialogFilter != null) {
                if (getUserConfig().isPremium()) {
                    size = getMessagesController().maxFolderPinnedDialogsCountPremium;
                } else {
                    size = getMessagesController().maxFolderPinnedDialogsCountDefault;
                }
            } else if (getUserConfig().isPremium()) {
                size = getMessagesController().maxPinnedDialogsCountPremium;
            } else {
                size = getMessagesController().maxPinnedDialogsCountDefault;
            }
            r8 = 0;
            zArr[0] = i3 + i10 <= size && (i4 + i9) - i2 <= size;
        }
        if (zArr[r8]) {
            ActionBarMenuSubItem actionBarMenuSubItem6 = new ActionBarMenuSubItem(getParentActivity(), r8, r8);
            if (isDialogPinned(dialog)) {
                actionBarMenuSubItem6.setTextAndIcon(LocaleController.getString(C2797R.string.UnpinMessage), C2797R.drawable.msg_unpin);
            } else {
                actionBarMenuSubItem6.setTextAndIcon(LocaleController.getString(C2797R.string.PinMessage), C2797R.drawable.msg_pin);
            }
            actionBarMenuSubItem6.setMinimumWidth(160);
            final MessagesController.DialogFilter dialogFilter3 = dialogFilter;
            j3 = j2;
            actionBarMenuSubItem6.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda52
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$showChatPreview$100(dialogFilter3, dialog, j3, view2);
                }
            });
            actionBarPopupWindowLayoutArr[0].addView(actionBarMenuSubItem6);
        } else {
            j3 = j2;
        }
        if (DialogObject.isUserDialog(j3) && UserObject.isUserSelf(getMessagesController().getUser(Long.valueOf(j3)))) {
            z2 = false;
        } else {
            ActionBarMenuSubItem actionBarMenuSubItem7 = new ActionBarMenuSubItem(getParentActivity(), false, false);
            if (!getMessagesController().isDialogMuted(j3, 0L)) {
                actionBarMenuSubItem7.setTextAndIcon(LocaleController.getString(C2797R.string.Mute), C2797R.drawable.msg_mute);
            } else {
                actionBarMenuSubItem7.setTextAndIcon(LocaleController.getString(C2797R.string.Unmute), C2797R.drawable.msg_unmute);
            }
            actionBarMenuSubItem7.setMinimumWidth(160);
            actionBarMenuSubItem7.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda53
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$showChatPreview$101(j3, view2);
                }
            });
            z2 = false;
            actionBarPopupWindowLayoutArr[0].addView(actionBarMenuSubItem7);
        }
        ActionBarMenuSubItem actionBarMenuSubItem8 = new ActionBarMenuSubItem(getParentActivity(), z2, true);
        actionBarMenuSubItem8.setIconColor(getThemedColor(Theme.key_text_RedRegular));
        int i12 = Theme.key_text_RedBold;
        actionBarMenuSubItem8.setTextColor(getThemedColor(i12));
        actionBarMenuSubItem8.setSelectorColor(Theme.multAlpha(getThemedColor(i12), 0.12f));
        actionBarMenuSubItem8.setTextAndIcon(LocaleController.getString(C2797R.string.Delete), C2797R.drawable.msg_delete);
        actionBarMenuSubItem8.setMinimumWidth(160);
        actionBarMenuSubItem8.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda54
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$showChatPreview$102(arrayList, view2);
            }
        });
        actionBarPopupWindowLayoutArr[0].addView(actionBarMenuSubItem8);
        if (!getMessagesController().checkCanOpenChat(bundle, this)) {
            return false;
        }
        if (this.searchString != null) {
            getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
        }
        prepareBlurBitmap();
        this.parentLayout.setHighlightActionButtons(true);
        WeakReference weakReference = new WeakReference(new ChatActivity(bundle));
        weakReferenceArr[0] = weakReference;
        Point point = AndroidUtilities.displaySize;
        if (point.x > point.y) {
            presentFragmentAsPreview((BaseFragment) weakReference.get());
            return true;
        }
        presentFragmentAsPreviewWithMenu((BaseFragment) weakReference.get(), actionBarPopupWindowLayoutArr[0]);
        WeakReference weakReference2 = weakReferenceArr[0];
        if (weakReference2 == null || weakReference2.get() == null) {
            return true;
        }
        ((ChatActivity) weakReferenceArr[0].get()).allowExpandPreviewByClick = true;
        try {
            ((ChatActivity) weakReferenceArr[0].get()).getAvatarContainer().getAvatarImageView().performAccessibilityAction(64, null);
            return true;
        } catch (Exception unused) {
            return true;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$35 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C554935 extends ScrollView {
        public C554935(Context context) {
            super(context);
        }

        @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.View
        public void onMeasure(int i6, int i7) {
            super.onMeasure(i6, View.MeasureSpec.makeMeasureSpec((int) Math.min(View.MeasureSpec.getSize(i7), Math.min(AndroidUtilities.displaySize.y * 0.35f, AndroidUtilities.m1036dp(400.0f))), View.MeasureSpec.getMode(i7)));
        }
    }

    public /* synthetic */ void lambda$showChatPreview$94(boolean z, ArrayList arrayList, MessagesController.DialogFilter dialogFilter, long j, View view) {
        if (!z) {
            if (!arrayList.isEmpty()) {
                for (int i = 0; i < arrayList.size(); i++) {
                    dialogFilter.neverShow.remove(arrayList.get(i));
                }
                dialogFilter.alwaysShow.addAll(arrayList);
                FilterCreateActivity.saveFilterToServer(dialogFilter, dialogFilter.flags, dialogFilter.name, dialogFilter.entities, dialogFilter.title_noanimate, dialogFilter.color, dialogFilter.emoticon, dialogFilter.alwaysShow, dialogFilter.neverShow, dialogFilter.pinnedDialogs, false, false, true, true, false, this, null);
            }
            getUndoView().showWithAction(j, 20, Integer.valueOf(arrayList.size()), dialogFilter, (Runnable) null, (Runnable) null);
        } else {
            dialogFilter.alwaysShow.remove(Long.valueOf(j));
            dialogFilter.neverShow.add(Long.valueOf(j));
            FilterCreateActivity.saveFilterToServer(dialogFilter, dialogFilter.flags, dialogFilter.name, dialogFilter.entities, dialogFilter.title_noanimate, dialogFilter.color, dialogFilter.emoticon, dialogFilter.alwaysShow, dialogFilter.neverShow, dialogFilter.pinnedDialogs, false, false, true, true, false, this, null);
            getUndoView().showWithAction(j, 21, Integer.valueOf(arrayList.size()), dialogFilter, (Runnable) null, (Runnable) null);
        }
        hideActionMode(true);
        finishPreviewFragment();
    }

    public static /* synthetic */ void $r8$lambda$ughQYe6_dhdDK1ZcQkKXaRufAyo(ActionBarPopupWindow.ActionBarPopupWindowLayout[] actionBarPopupWindowLayoutArr, View view) {
        ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout = actionBarPopupWindowLayoutArr[0];
        if (actionBarPopupWindowLayout != null) {
            actionBarPopupWindowLayout.getSwipeBack().closeForeground();
        }
    }

    public static /* synthetic */ void $r8$lambda$f0_AxCjcjKpWD4DoWvM1cL6m1VA(WeakReference[] weakReferenceArr, int i) {
        WeakReference weakReference = weakReferenceArr[0];
        if (weakReference == null || weakReference.get() == null || ((ChatActivity) weakReferenceArr[0].get()).getFragmentView() == null || !((ChatActivity) weakReferenceArr[0].get()).isInPreviewMode()) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = ((ChatActivity) weakReferenceArr[0].get()).getFragmentView().getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = AndroidUtilities.m1036dp(48.0f) + i;
            ((ChatActivity) weakReferenceArr[0].get()).getFragmentView().setLayoutParams(layoutParams);
        }
    }

    public /* synthetic */ void lambda$showChatPreview$98(DialogCell dialogCell, long j, View view) {
        if (dialogCell.getHasUnread()) {
            markAsRead(j);
        } else {
            markAsUnread(j);
        }
        finishPreviewFragment();
    }

    public /* synthetic */ void lambda$showChatPreview$100(final MessagesController.DialogFilter dialogFilter, final TLRPC.Dialog dialog, final long j, View view) {
        finishPreviewFragment();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda106
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showChatPreview$99(dialogFilter, dialog, j);
            }
        }, 100L);
    }

    public /* synthetic */ void lambda$showChatPreview$99(MessagesController.DialogFilter dialogFilter, TLRPC.Dialog dialog, long j) {
        MessagesController.DialogFilter dialogFilter2;
        DialogsActivity dialogsActivity;
        int iMin = Integer.MAX_VALUE;
        if (dialogFilter != null && isDialogPinned(dialog)) {
            int size = dialogFilter.pinnedDialogs.size();
            for (int i = 0; i < size; i++) {
                iMin = Math.min(iMin, dialogFilter.pinnedDialogs.valueAt(i));
            }
            iMin -= this.canPinCount;
        }
        int i2 = iMin;
        TLRPC.EncryptedChat encryptedChat = DialogObject.isEncryptedDialog(j) ? getMessagesController().getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(j))) : null;
        UndoView undoView = getUndoView();
        if (undoView == null) {
            return;
        }
        if (!isDialogPinned(dialog)) {
            pinDialog(j, true, dialogFilter, i2, true);
            dialogFilter2 = dialogFilter;
            undoView.showWithAction(0L, 78, (Object) 1, (Object) 1600, (Runnable) null, (Runnable) null);
            if (dialogFilter2 != null) {
                ArrayList<Long> arrayList = dialogFilter2.alwaysShow;
                if (encryptedChat != null) {
                    if (!arrayList.contains(Long.valueOf(encryptedChat.user_id))) {
                        dialogFilter2.alwaysShow.add(Long.valueOf(encryptedChat.user_id));
                    }
                } else if (!arrayList.contains(Long.valueOf(j))) {
                    dialogFilter2.alwaysShow.add(Long.valueOf(j));
                }
            }
        } else {
            pinDialog(j, false, dialogFilter, i2, true);
            dialogFilter2 = dialogFilter;
            undoView.showWithAction(0L, 79, (Object) 1, (Object) 1600, (Runnable) null, (Runnable) null);
        }
        if (dialogFilter2 != null) {
            FilterCreateActivity.saveFilterToServer(dialogFilter2, dialogFilter2.flags, dialogFilter2.name, dialogFilter2.entities, dialogFilter2.title_noanimate, dialogFilter2.color, dialogFilter2.emoticon, dialogFilter2.alwaysShow, dialogFilter2.neverShow, dialogFilter2.pinnedDialogs, false, false, true, true, false, this, null);
            dialogsActivity = this;
        } else {
            dialogsActivity = this;
        }
        dialogsActivity.getMessagesController().reorderPinnedDialogs(dialogsActivity.folderId, null, 0L);
        dialogsActivity.updateCounters(true);
        ViewPage[] viewPageArr = dialogsActivity.viewPages;
        if (viewPageArr != null) {
            for (ViewPage viewPage : viewPageArr) {
                viewPage.dialogsAdapter.onReorderStateChanged(false);
            }
        }
        dialogsActivity.updateVisibleRows(MessagesController.UPDATE_MASK_REORDER | MessagesController.UPDATE_MASK_CHECK);
    }

    public /* synthetic */ void lambda$showChatPreview$101(long j, View view) {
        boolean zIsDialogMuted = getMessagesController().isDialogMuted(j, 0L);
        if (!zIsDialogMuted) {
            getNotificationsController().setDialogNotificationsSettings(j, 0L, 3);
        } else {
            getNotificationsController().setDialogNotificationsSettings(j, 0L, 4);
        }
        BulletinFactory.createMuteBulletin(this, !zIsDialogMuted, null).show();
        finishPreviewFragment();
    }

    public /* synthetic */ void lambda$showChatPreview$102(ArrayList arrayList, View view) {
        performSelectedDialogsAction(arrayList, 102, false, false);
        finishPreviewFragment();
    }

    private boolean shouldHideDialogsFabBySetting() {
        return ExteraConfig.getHideFloatingButton() && this.initialDialogsType == 0;
    }

    public void updateFloatingButtonVisibility(boolean z) {
        boolean z2;
        boolean z3 = false;
        boolean z4 = !shouldHideDialogsFabBySetting() && (!(z2 = this.onlySelect) || this.initialDialogsType == 10) && this.folderId == 0 && !this.inPreviewMode && ((!this.searching || z2) && !this.floatingButtonHidden);
        FragmentFloatingButton fragmentFloatingButton = this.floatingButton3;
        if (fragmentFloatingButton != null) {
            fragmentFloatingButton.setButtonVisible(z4, z);
        }
        if (this.floatingButtonStories != null) {
            if (z4 && !ExteraConfig.getHideStories()) {
                z3 = true;
            }
            this.floatingButtonStories.setButtonVisible(z3, z);
            if (z3) {
                return;
            }
            HintView2 hintView2 = this.storyHint;
            if (hintView2 != null) {
                hintView2.hide();
            }
            HintView2 hintView22 = this.storyPremiumHint;
            if (hintView22 != null) {
                hintView22.hide();
            }
        }
    }

    public void updateFloatingButtonOffset() {
        float currentBottomTabsOffset = (((-this.navigationBarHeight) - getCurrentBottomTabsOffset()) - this.additionalFloatingTranslation) - this.floatingButtonPanOffset;
        FragmentFloatingButton fragmentFloatingButton = this.floatingButton3;
        if (fragmentFloatingButton != null) {
            fragmentFloatingButton.setTranslationY(currentBottomTabsOffset);
        }
        FragmentFloatingButton fragmentFloatingButton2 = this.floatingButtonStories;
        if (fragmentFloatingButton2 != null) {
            fragmentFloatingButton2.setTranslationY(currentBottomTabsOffset - AndroidUtilities.m1036dp(52.0f));
            HintView2 hintView2 = this.storyHint;
            if (hintView2 != null) {
                hintView2.setTranslationY(currentBottomTabsOffset - AndroidUtilities.m1036dp(52.0f));
            }
        }
    }

    private void updateStoriesPosting() {
        boolean zStoriesEnabled = getMessagesController().storiesEnabled();
        if (this.storiesEnabled != zStoriesEnabled) {
            updateFloatingButtonOffset();
            if (!this.storiesEnabled && zStoriesEnabled && this.storyHint != null && !ExteraConfig.getHideStories()) {
                this.storyHint.show();
            }
            this.storiesEnabled = zStoriesEnabled;
        }
        FragmentFloatingButton fragmentFloatingButton = this.floatingButton3;
        if (fragmentFloatingButton == null) {
            return;
        }
        if (this.initialDialogsType == 10) {
            fragmentFloatingButton.setImageResource(C2797R.drawable.floating_check);
            this.floatingButton3.setContentDescription(LocaleController.getString(C2797R.string.Done));
        } else {
            fragmentFloatingButton.setImageResource(C2797R.drawable.filled_fab_compose_32);
            this.floatingButton3.setContentDescription(LocaleController.getString(C2797R.string.NewMessageTitle));
        }
    }

    public boolean hasHiddenArchive() {
        return !this.onlySelect && this.initialDialogsType == 0 && this.folderId == 0 && getMessagesController().hasHiddenArchive();
    }

    public boolean waitingForDialogsAnimationEnd(ViewPage viewPage) {
        return viewPage.dialogsItemAnimator.isRunning();
    }

    public void checkAnimationFinished() {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda96
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$checkAnimationFinished$103();
            }
        }, 300L);
    }

    public /* synthetic */ void lambda$checkAnimationFinished$103() {
        setDialogsListFrozen(false);
        updateDialogIndices();
    }

    public void setScrollY(float f) {
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr != null) {
            int paddingTop = viewPageArr[0].listView.getPaddingTop() + ((int) f);
            int i = 0;
            while (true) {
                ViewPage[] viewPageArr2 = this.viewPages;
                if (i >= viewPageArr2.length) {
                    break;
                }
                viewPageArr2[i].listView.setTopGlowOffset(paddingTop);
                i++;
            }
        }
        if (this.fragmentView == null || f == this.scrollYOffset) {
            return;
        }
        this.scrollYOffset = f;
        Bulletin bulletin = this.topBulletin;
        if (bulletin != null) {
            bulletin.updatePosition();
        }
        if (this.animatedStatusView != null) {
            float currentActionBarHeight = 1.0f - ((-f) / ActionBar.getCurrentActionBarHeight());
            this.animatedStatusView.translateY2((int) f);
            this.animatedStatusView.setAlpha(MathUtils.clamp(currentActionBarHeight, 0.0f, 1.0f));
            this.animatedStatusView.setVisibility(currentActionBarHeight <= 0.0f ? 4 : 0);
        }
        checkUi_searchFieldVisibility();
        this.fragmentView.invalidate();
    }

    private void prepareBlurBitmap() {
        if (this.blurredView == null) {
            return;
        }
        int measuredWidth = (int) (this.fragmentView.getMeasuredWidth() / 9.0f);
        int measuredHeight = (int) (this.fragmentView.getMeasuredHeight() / 9.0f);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.eraseColor(getThemedColor(Theme.key_windowBackgroundWhite));
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.scale(0.11111111f, 0.11111111f);
        this.fragmentView.draw(canvas);
        Utilities.stackBlurBitmap(bitmapCreateBitmap, Math.max(9, Math.max(measuredWidth, measuredHeight) / 180));
        this.blurredView.setBackground(new BitmapDrawable(bitmapCreateBitmap));
        this.blurredView.setAlpha(0.0f);
        this.blurredView.setVisibility(0);
        checkUi_mainTabsVisible();
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onTransitionAnimationProgress(boolean z, float f) {
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if (rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment()) {
            this.rightSlidingDialogContainer.getFragment().onTransitionAnimationProgress(z, f);
        } else {
            View view = this.blurredView;
            if (view != null && view.getVisibility() == 0) {
                View view2 = this.blurredView;
                if (z) {
                    view2.setAlpha(1.0f - f);
                } else {
                    view2.setAlpha(f);
                }
            }
        }
        checkUi_mainTabsVisible();
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        View view;
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if (rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment()) {
            this.rightSlidingDialogContainer.getFragment().onTransitionAnimationEnd(z, z2);
        } else if (z && this.afterSignup) {
            try {
                this.fragmentView.performHapticFeedback(3, 2);
            } catch (Exception unused) {
            }
            if (getParentActivity() instanceof LaunchActivity) {
                ((LaunchActivity) getParentActivity()).getFireworksOverlay().start();
            }
        }
        if (z && (view = this.blurredView) != null && view.getVisibility() == 0) {
            this.blurredView.setVisibility(8);
            this.blurredView.setBackground(null);
        }
        checkUi_mainTabsVisible();
    }

    private void resetScroll() {
        boolean z;
        if (this.scrollYOffset == 0.0f || (z = this.hasStories)) {
            return;
        }
        float f = z ? -getMaxScrollYOffsetWithoutSearch() : 0.0f;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(this, this.SCROLL_Y, f));
        animatorSet.setInterpolator(CubicBezierInterpolator.DEFAULT);
        animatorSet.setDuration(250L);
        animatorSet.start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v29 */
    /* JADX WARN: Type inference failed for: r0v30 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [int] */
    /* JADX WARN: Type inference failed for: r3v8 */
    public void hideActionMode(boolean z) {
        boolean z2;
        ArrayList<MessagesController.DialogFilter> arrayList;
        this.actionBar.hideActionMode();
        this.selectedDialogs.clear();
        BackDrawable backDrawable = this.backDrawable;
        boolean z3 = true;
        if (backDrawable != null) {
            backDrawable.setRotation(0.0f, true);
        }
        if (this.menuDrawable != null && this.actionBar.getBackButton() != null && !(this.actionBar.getBackButton().getDrawable() instanceof MenuDrawable)) {
            this.actionBar.setBackButtonDrawable(this.menuDrawable);
        }
        FilterTabsView filterTabsView = this.filterTabsView;
        if (filterTabsView != null) {
            filterTabsView.animateColorsTo(Theme.key_actionBarTabLine, Theme.key_actionBarTabActiveText, Theme.key_actionBarTabUnactiveText, Theme.key_actionBarTabSelector, Theme.key_windowBackgroundWhite);
        }
        ValueAnimator valueAnimator = this.actionBarColorAnimator;
        Object obj = null;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.actionBarColorAnimator = null;
        }
        if (this.progressToActionMode == 0.0f) {
            return;
        }
        setScrollY(-getMaxScrollYOffset());
        boolean z4 = false;
        int i = 0;
        while (true) {
            ViewPage[] viewPageArr = this.viewPages;
            if (i >= viewPageArr.length) {
                break;
            }
            ViewPage viewPage = viewPageArr[i];
            if (viewPage != null) {
                viewPage.listView.cancelClickRunnables(true);
            }
            i++;
        }
        final float fMax = Math.max(0.0f, AndroidUtilities.m1036dp(this.hasStories ? 81.0f : 0.0f) + getSearchFieldHeight() + this.scrollYOffset);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.progressToActionMode, 0.0f);
        this.actionBarColorAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda39
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f$0.lambda$hideActionMode$104(fMax, valueAnimator2);
            }
        });
        this.actionBarColorAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.36
            final /* synthetic */ float val$finalTranslateListHeight;

            public C555036(final float fMax2) {
                f = fMax2;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                DialogsActivity dialogsActivity;
                super.onAnimationEnd(animator);
                DialogsActivity.this.actionBarColorAnimator = null;
                int i2 = 0;
                DialogsActivity.this.actionModeFullyShowed = false;
                DialogsActivity.this.invalidateScrollY = true;
                DialogsActivity.this.fixScrollYAfterArchiveOpened = true;
                DialogsActivity.this.fragmentView.invalidate();
                DialogsActivity dialogsActivity2 = DialogsActivity.this;
                dialogsActivity2.scrollAdditionalOffset = -((AndroidUtilities.m1036dp(dialogsActivity2.hasStories ? 81.0f : 0.0f) + DialogsActivity.this.getSearchFieldHeight()) - f);
                DialogsActivity.this.viewPages[0].setTranslationY(0.0f);
                while (true) {
                    int length = DialogsActivity.this.viewPages.length;
                    dialogsActivity = DialogsActivity.this;
                    if (i2 >= length) {
                        break;
                    }
                    if (dialogsActivity.viewPages[i2] != null) {
                        DialogsActivity.this.viewPages[i2].listView.requestLayout();
                    }
                    i2++;
                }
                dialogsActivity.fragmentView.requestLayout();
                if (DialogsActivity.this.fragmentSearchField == null || !DialogsActivity.this.animatorSearchVisible.getValue()) {
                    return;
                }
                DialogsActivity.this.fragmentSearchField.editText.requestFocus();
                AndroidUtilities.showKeyboard(DialogsActivity.this.fragmentSearchField.editText);
            }
        });
        this.actionBarColorAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
        this.actionBarColorAnimator.setDuration(200L);
        this.actionBarColorAnimator.start();
        this.allowMoving = false;
        if (!this.movingDialogFilters.isEmpty()) {
            int size = this.movingDialogFilters.size();
            int i2 = 0;
            while (true) {
                arrayList = this.movingDialogFilters;
                if (i2 >= size) {
                    break;
                }
                MessagesController.DialogFilter dialogFilter = arrayList.get(i2);
                FilterCreateActivity.saveFilterToServer(dialogFilter, dialogFilter.flags, dialogFilter.name, dialogFilter.entities, dialogFilter.title_noanimate, dialogFilter.color, dialogFilter.emoticon, dialogFilter.alwaysShow, dialogFilter.neverShow, dialogFilter.pinnedDialogs, false, false, true, true, false, this, null);
                i2++;
                size = size;
                z4 = false;
                z3 = true;
                obj = null;
            }
            arrayList.clear();
        }
        if (this.movingWas) {
            getMessagesController().reorderPinnedDialogs(this.folderId, null, 0L);
            z2 = false;
            this.movingWas = false;
        } else {
            z2 = false;
        }
        updateCounters(true);
        ViewPage[] viewPageArr2 = this.viewPages;
        if (viewPageArr2 != null) {
            int length = viewPageArr2.length;
            for (?? r3 = z2; r3 < length; r3++) {
                viewPageArr2[r3].dialogsAdapter.onReorderStateChanged(z2);
            }
        }
        int i3 = MessagesController.UPDATE_MASK_REORDER | MessagesController.UPDATE_MASK_CHECK;
        ?? r0 = z2;
        if (z) {
            r0 = MessagesController.UPDATE_MASK_CHAT;
        }
        updateVisibleRows(r0 | i3);
    }

    public /* synthetic */ void lambda$hideActionMode$104(float f, ValueAnimator valueAnimator) {
        this.viewPages[0].setTranslationY(f * (1.0f - this.progressToActionMode));
        this.progressToActionMode = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        for (int i = 0; i < this.actionBar.getChildCount(); i++) {
            if (this.actionBar.getChildAt(i).getVisibility() == 0 && this.actionBar.getChildAt(i) != this.actionBar.getActionMode() && this.actionBar.getChildAt(i) != this.actionBar.getBackButton()) {
                this.actionBar.getChildAt(i).setAlpha(1.0f - this.progressToActionMode);
            }
        }
        checkUi_searchFieldVisibility();
        checkUi_itemBackButtonVisibility();
        View view = this.fragmentView;
        if (view != null) {
            view.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$36 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C555036 extends AnimatorListenerAdapter {
        final /* synthetic */ float val$finalTranslateListHeight;

        public C555036(final float fMax2) {
            f = fMax2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            DialogsActivity dialogsActivity;
            super.onAnimationEnd(animator);
            DialogsActivity.this.actionBarColorAnimator = null;
            int i2 = 0;
            DialogsActivity.this.actionModeFullyShowed = false;
            DialogsActivity.this.invalidateScrollY = true;
            DialogsActivity.this.fixScrollYAfterArchiveOpened = true;
            DialogsActivity.this.fragmentView.invalidate();
            DialogsActivity dialogsActivity2 = DialogsActivity.this;
            dialogsActivity2.scrollAdditionalOffset = -((AndroidUtilities.m1036dp(dialogsActivity2.hasStories ? 81.0f : 0.0f) + DialogsActivity.this.getSearchFieldHeight()) - f);
            DialogsActivity.this.viewPages[0].setTranslationY(0.0f);
            while (true) {
                int length = DialogsActivity.this.viewPages.length;
                dialogsActivity = DialogsActivity.this;
                if (i2 >= length) {
                    break;
                }
                if (dialogsActivity.viewPages[i2] != null) {
                    DialogsActivity.this.viewPages[i2].listView.requestLayout();
                }
                i2++;
            }
            dialogsActivity.fragmentView.requestLayout();
            if (DialogsActivity.this.fragmentSearchField == null || !DialogsActivity.this.animatorSearchVisible.getValue()) {
                return;
            }
            DialogsActivity.this.fragmentSearchField.editText.requestFocus();
            AndroidUtilities.showKeyboard(DialogsActivity.this.fragmentSearchField.editText);
        }
    }

    private int getPinnedCount() {
        ArrayList<TLRPC.Dialog> dialogsArray;
        if ((this.viewPages[0].dialogsType == 7 || this.viewPages[0].dialogsType == 8) && (!this.actionBar.isActionModeShowed() || this.actionBar.isActionModeShowed(null))) {
            dialogsArray = getDialogsArray(this.currentAccount, this.viewPages[0].dialogsType, this.folderId, this.dialogsListFrozen);
        } else {
            dialogsArray = getMessagesController().getDialogs(this.folderId);
        }
        int size = dialogsArray.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            TLRPC.Dialog dialog = dialogsArray.get(i2);
            if (!(dialog instanceof TLRPC.TL_dialogFolder)) {
                if (!isDialogPinned(dialog)) {
                    if (!getMessagesController().isPromoDialog(dialog.f1251id, false)) {
                        break;
                    }
                } else {
                    i++;
                }
            }
        }
        return i;
    }

    public boolean isDialogPinned(TLRPC.Dialog dialog) {
        if (dialog == null) {
            return false;
        }
        MessagesController.DialogFilter dialogFilter = null;
        if ((this.viewPages[0].dialogsType == 7 || this.viewPages[0].dialogsType == 8) && (!this.actionBar.isActionModeShowed() || this.actionBar.isActionModeShowed(null))) {
            dialogFilter = getMessagesController().selectedDialogFilter[this.viewPages[0].dialogsType == 8 ? (char) 1 : (char) 0];
        }
        if (dialogFilter != null) {
            return dialogFilter.pinnedDialogs.indexOfKey(dialog.f1251id) >= 0;
        }
        return dialog.pinned;
    }

    public void performSelectedDialogsAction(ArrayList<Long> arrayList, int i, boolean z, boolean z2) {
        performSelectedDialogsAction(arrayList, i, z, z2, null);
    }

    private void performSelectedDialogsAction(final ArrayList<Long> arrayList, int i, boolean z, boolean z2, HashSet<Long> hashSet) {
        MessagesController.DialogFilter dialogFilter;
        boolean z3;
        int i2;
        int i3;
        int i4;
        int size;
        int i5;
        long j;
        int i6;
        MessagesController.DialogFilter dialogFilter2;
        TLRPC.Chat chat;
        TLRPC.User user;
        TLRPC.EncryptedChat encryptedChat;
        boolean z4;
        int i7;
        MessagesController.DialogFilter dialogFilter3;
        int i8;
        boolean z5;
        char c2;
        boolean z6;
        boolean z7;
        boolean z8;
        TLRPC.User user2;
        TLRPC.User user3;
        final DialogsActivity dialogsActivity = this;
        final int i9 = i;
        if (dialogsActivity.getParentActivity() == null) {
            return;
        }
        boolean z9 = true;
        boolean z10 = (dialogsActivity.viewPages[0].dialogsType == 7 || dialogsActivity.viewPages[0].dialogsType == 8) && (!dialogsActivity.actionBar.isActionModeShowed() || dialogsActivity.actionBar.isActionModeShowed(null));
        if (z10) {
            dialogFilter = dialogsActivity.getMessagesController().selectedDialogFilter[dialogsActivity.viewPages[0].dialogsType == 8 ? (char) 1 : (char) 0];
        } else {
            dialogFilter = null;
        }
        int size2 = arrayList.size();
        if (i9 == 105 || i9 == 107) {
            final ArrayList<Long> arrayList2 = new ArrayList<>(arrayList);
            dialogsActivity.getMessagesController().addDialogToFolder(arrayList2, dialogsActivity.canUnarchiveCount == 0 ? 1 : 0, -1, null, 0L);
            if (dialogsActivity.canUnarchiveCount == 0) {
                SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
                z3 = false;
                boolean z11 = globalMainSettings.getBoolean("archivehint_l", false) || SharedConfig.archiveHidden;
                if (z11) {
                    i2 = 1;
                } else {
                    i2 = 1;
                    globalMainSettings.edit().putBoolean("archivehint_l", true).apply();
                }
                if (z11) {
                    i3 = arrayList2.size() > i2 ? 4 : 2;
                } else {
                    i3 = arrayList2.size() > i2 ? 5 : 3;
                }
                int i10 = i3;
                UndoView undoView = dialogsActivity.getUndoView();
                if (undoView != null) {
                    undoView.showWithAction(0L, i10, null, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda78
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$performSelectedDialogsAction$105(arrayList2);
                        }
                    });
                }
            } else {
                z3 = false;
                ArrayList<TLRPC.Dialog> dialogs = dialogsActivity.getMessagesController().getDialogs(dialogsActivity.folderId);
                if (dialogsActivity.viewPages != null && dialogs.isEmpty() && !dialogsActivity.hasStories) {
                    dialogsActivity.viewPages[0].listView.setEmptyView(null);
                    dialogsActivity.viewPages[0].progressView.setVisibility(4);
                    dialogsActivity.finishFragment();
                }
            }
            dialogsActivity.hideActionMode(z3);
            return;
        }
        int i11 = 100;
        if ((i9 == 100 || i9 == 108) && dialogsActivity.canPinCount != 0) {
            ArrayList<TLRPC.Dialog> dialogs2 = dialogsActivity.getMessagesController().getDialogs(dialogsActivity.folderId);
            int size3 = dialogs2.size();
            int i12 = 0;
            int i13 = 0;
            int i14 = 0;
            while (true) {
                if (i12 >= size3) {
                    i4 = i11;
                    break;
                }
                TLRPC.Dialog dialog = dialogs2.get(i12);
                i4 = i11;
                if (!(dialog instanceof TLRPC.TL_dialogFolder)) {
                    if (dialogsActivity.isDialogPinned(dialog)) {
                        if (DialogObject.isEncryptedDialog(dialog.f1251id)) {
                            i14++;
                        } else {
                            i13++;
                        }
                    } else if (!dialogsActivity.getMessagesController().isPromoDialog(dialog.f1251id, false)) {
                        break;
                    }
                }
                i12++;
                i11 = i4;
            }
            int i15 = 0;
            int i16 = 0;
            int i17 = 0;
            for (int i18 = 0; i18 < size2; i18++) {
                Long l = arrayList.get(i18);
                int i19 = i15;
                long jLongValue = l.longValue();
                TLRPC.Dialog dialog2 = dialogsActivity.getMessagesController().dialogs_dict.get(jLongValue);
                if (dialog2 == null || dialogsActivity.isDialogPinned(dialog2)) {
                    i15 = i19;
                } else {
                    if (DialogObject.isEncryptedDialog(jLongValue)) {
                        i16++;
                        i15 = i19;
                    } else {
                        i15 = i19 + 1;
                    }
                    if (dialogFilter != null && dialogFilter.alwaysShow.contains(l)) {
                        i17++;
                    }
                }
            }
            int i20 = i15;
            if (z10) {
                size = 100 - dialogFilter.alwaysShow.size();
            } else if (dialogsActivity.folderId != 0 || dialogFilter != null) {
                if (UserConfig.getInstance(dialogsActivity.currentAccount).isPremium()) {
                    size = dialogsActivity.getMessagesController().maxFolderPinnedDialogsCountPremium;
                } else {
                    size = dialogsActivity.getMessagesController().maxFolderPinnedDialogsCountDefault;
                }
            } else {
                size = dialogsActivity.getUserConfig().isPremium() ? dialogsActivity.getMessagesController().dialogFiltersPinnedLimitPremium : dialogsActivity.getMessagesController().dialogFiltersPinnedLimitDefault;
            }
            if (i16 + i14 > size || (i20 + i13) - i17 > size) {
                if (dialogsActivity.folderId != 0 || dialogFilter != null) {
                    AlertsCreator.showSimpleAlert(dialogsActivity, LocaleController.formatString("PinFolderLimitReached", C2797R.string.PinFolderLimitReached, LocaleController.formatPluralString("Chats", size, new Object[0])));
                    return;
                } else {
                    dialogsActivity.showDialog(new LimitReachedBottomSheet(dialogsActivity, dialogsActivity.getParentActivity(), 0, dialogsActivity.currentAccount, null));
                    return;
                }
            }
            i5 = size2;
        } else {
            i4 = 100;
            if ((i9 == 102 || i9 == 103) && size2 > 1 && z) {
                final HashSet hashSet2 = new HashSet();
                boolean z12 = MessagesController.getInstance(dialogsActivity.currentAccount).canRevokePmInbox;
                long j2 = MessagesController.getInstance(dialogsActivity.currentAccount).revokeTimePmLimit;
                if (i9 == 102 && z12 && j2 == 2147483647L) {
                    int size4 = arrayList.size();
                    z8 = false;
                    int i21 = 0;
                    while (i21 < size4) {
                        Long l2 = arrayList.get(i21);
                        i21++;
                        Long l3 = l2;
                        if (DialogObject.isUserDialog(l3.longValue()) || DialogObject.isEncryptedDialog(l3.longValue())) {
                            if (DialogObject.isEncryptedDialog(l3.longValue())) {
                                TLRPC.EncryptedChat encryptedChat2 = dialogsActivity.getMessagesController().getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(l3.longValue())));
                                user2 = encryptedChat2 != null ? dialogsActivity.getMessagesController().getUser(Long.valueOf(encryptedChat2.user_id)) : null;
                            } else {
                                user2 = dialogsActivity.getMessagesController().getUser(l3);
                            }
                            if (user2 != null) {
                                ArrayList<MessageObject> arrayList3 = MessagesController.getInstance(dialogsActivity.currentAccount).dialogMessage.get(user2.f1407id);
                                boolean z13 = (arrayList3 == null || arrayList3.size() != 1 || arrayList3.get(0) == null || arrayList3.get(0).messageOwner == null || (!(arrayList3.get(0).messageOwner.action instanceof TLRPC.TL_messageActionUserJoined) && !(arrayList3.get(0).messageOwner.action instanceof TLRPC.TL_messageActionContactSignUp))) ? false : true;
                                if (!user2.bot && !UserObject.isDeleted(user2) && user2.f1407id != dialogsActivity.getUserConfig().getClientUserId() && !z13) {
                                    hashSet2.add(l3);
                                    z8 = true;
                                }
                            }
                        }
                    }
                } else {
                    z8 = false;
                }
                AlertsCreator.createClearOrDeleteDialogsAlert(dialogsActivity, i9 == 103, i9 == 102, dialogsActivity.canClearCacheCount, size2, z8, new MessagesStorage.BooleanCallback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda79
                    @Override // org.telegram.messenger.MessagesStorage.BooleanCallback
                    public final void run(boolean z14) {
                        this.f$0.lambda$performSelectedDialogsAction$107(arrayList, i9, hashSet2, z14);
                    }
                }, dialogsActivity.resourceProvider);
                return;
            }
            i5 = size2;
            if (i9 == 106 && z) {
                if (i5 == 1) {
                    Long l4 = arrayList.get(0);
                    l4.longValue();
                    user3 = dialogsActivity.getMessagesController().getUser(l4);
                } else {
                    user3 = null;
                }
                AlertsCreator.createBlockDialogAlert(dialogsActivity, i5, dialogsActivity.canReportSpamCount != 0, user3, new AlertsCreator.BlockDialogCallback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda80
                    @Override // org.telegram.ui.Components.AlertsCreator.BlockDialogCallback
                    public final void run(boolean z14, boolean z15) {
                        this.f$0.lambda$performSelectedDialogsAction$108(arrayList, z14, z15);
                    }
                });
                return;
            }
        }
        int iMin = Integer.MAX_VALUE;
        if (dialogFilter != null && ((i9 == i4 || i9 == 108) && dialogsActivity.canPinCount != 0)) {
            int size5 = dialogFilter.pinnedDialogs.size();
            for (int i22 = 0; i22 < size5; i22++) {
                iMin = Math.min(iMin, dialogFilter.pinnedDialogs.valueAt(i22));
            }
            iMin -= dialogsActivity.canPinCount;
        }
        int i23 = iMin;
        int i24 = 0;
        int i25 = 0;
        while (i24 < i5) {
            Long l5 = arrayList.get(i24);
            final long jLongValue2 = l5.longValue();
            TLRPC.Dialog dialog3 = dialogsActivity.getMessagesController().dialogs_dict.get(jLongValue2);
            if (dialog3 == null) {
                z4 = z;
                dialogFilter3 = dialogFilter;
                i7 = i23;
            } else {
                if (DialogObject.isEncryptedDialog(jLongValue2)) {
                    TLRPC.EncryptedChat encryptedChat3 = dialogsActivity.getMessagesController().getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(jLongValue2)));
                    if (encryptedChat3 != null) {
                        dialogFilter2 = dialogFilter;
                        user = dialogsActivity.getMessagesController().getUser(Long.valueOf(encryptedChat3.user_id));
                    } else {
                        dialogFilter2 = dialogFilter;
                        user = new TLRPC.TL_userEmpty();
                    }
                    encryptedChat = encryptedChat3;
                    chat = null;
                } else {
                    dialogFilter2 = dialogFilter;
                    if (DialogObject.isUserDialog(jLongValue2)) {
                        user = dialogsActivity.getMessagesController().getUser(l5);
                        chat = null;
                    } else {
                        chat = dialogsActivity.getMessagesController().getChat(Long.valueOf(-jLongValue2));
                        user = null;
                    }
                    encryptedChat = null;
                }
                if (chat != null || user != null) {
                    boolean z14 = (user == null || !user.bot || MessagesController.isSupportUser(user)) ? false : true;
                    if (i9 == 100 || i9 == 108) {
                        z4 = false;
                        if (dialogsActivity.canPinCount != 0) {
                            if (dialogsActivity.isDialogPinned(dialog3)) {
                                i7 = i23;
                                dialogFilter3 = dialogFilter2;
                                i8 = i;
                            } else {
                                i25++;
                                i7 = i23;
                                dialogFilter3 = dialogFilter2;
                                i8 = i;
                                dialogsActivity.pinDialog(jLongValue2, true, dialogFilter3, i7, i5 == 1);
                                if (dialogFilter3 != null) {
                                    int i26 = i7 + 1;
                                    ArrayList<Long> arrayList4 = dialogFilter3.alwaysShow;
                                    if (encryptedChat != null) {
                                        if (!arrayList4.contains(Long.valueOf(encryptedChat.user_id))) {
                                            dialogFilter3.alwaysShow.add(Long.valueOf(encryptedChat.user_id));
                                        }
                                    } else if (!arrayList4.contains(Long.valueOf(dialog3.f1251id))) {
                                        dialogFilter3.alwaysShow.add(Long.valueOf(dialog3.f1251id));
                                    }
                                    i7 = i26;
                                }
                            }
                        } else {
                            i7 = i23;
                            dialogFilter3 = dialogFilter2;
                            i8 = i;
                            if (dialogsActivity.isDialogPinned(dialog3)) {
                                i25++;
                                z5 = true;
                                dialogsActivity.pinDialog(jLongValue2, false, dialogFilter3, i7, i5 == 1);
                            }
                        }
                        z5 = true;
                    } else if (i9 == 101) {
                        if (dialogsActivity.canReadCount != 0) {
                            dialogsActivity.markAsRead(jLongValue2);
                        } else {
                            dialogsActivity.markAsUnread(jLongValue2);
                        }
                    } else {
                        if (i9 != 102) {
                            c2 = 'd';
                            if (i9 != 103) {
                                if (i9 == 104) {
                                    if (i5 == 1 && dialogsActivity.canMuteCount == 1) {
                                        dialogsActivity.showDialog(AlertsCreator.createMuteAlert(dialogsActivity, jLongValue2, 0L, (Theme.ResourcesProvider) null), new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda83
                                            @Override // android.content.DialogInterface.OnDismissListener
                                            public final void onDismiss(DialogInterface dialogInterface) {
                                                this.f$0.lambda$performSelectedDialogsAction$112(dialogInterface);
                                            }
                                        });
                                        return;
                                    }
                                    int i27 = i9;
                                    if (dialogsActivity.canUnmuteCount != 0) {
                                        if (dialogsActivity.getMessagesController().isDialogMuted(jLongValue2, 0L)) {
                                            dialogsActivity.getNotificationsController().setDialogNotificationsSettings(jLongValue2, 0L, 4);
                                        }
                                    } else if (z2) {
                                        dialogsActivity.showDialog(AlertsCreator.createMuteAlert(dialogsActivity, arrayList, 0, (Theme.ResourcesProvider) null), new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda84
                                            @Override // android.content.DialogInterface.OnDismissListener
                                            public final void onDismiss(DialogInterface dialogInterface) {
                                                this.f$0.lambda$performSelectedDialogsAction$113(dialogInterface);
                                            }
                                        });
                                        return;
                                    } else if (!dialogsActivity.getMessagesController().isDialogMuted(jLongValue2, 0L)) {
                                        dialogsActivity.getNotificationsController().setDialogNotificationsSettings(jLongValue2, 0L, 3);
                                    }
                                    i7 = i23;
                                    dialogFilter3 = dialogFilter2;
                                    z4 = false;
                                    i8 = i27;
                                    z5 = true;
                                }
                            }
                            i7 = i23;
                            dialogFilter3 = dialogFilter2;
                            z4 = false;
                            i8 = i9;
                            z5 = true;
                        } else {
                            c2 = 'd';
                        }
                        final int i28 = i9;
                        if (i5 == 1) {
                            if (i28 == 102 && dialogsActivity.canDeletePsaSelected) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(dialogsActivity.getParentActivity());
                                builder.setTitle(LocaleController.getString(C2797R.string.PsaHideChatAlertTitle));
                                builder.setMessage(LocaleController.getString(C2797R.string.PsaHideChatAlertText));
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.PsaHide), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda81
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i29) {
                                        this.f$0.lambda$performSelectedDialogsAction$109(alertDialog, i29);
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                dialogsActivity.showDialog(builder.create());
                                return;
                            }
                            boolean z15 = i28 == 103;
                            boolean zIsEncryptedDialog = DialogObject.isEncryptedDialog(dialog3.f1251id);
                            boolean z16 = i28 == 102;
                            final TLRPC.Chat chat2 = chat;
                            final boolean z17 = z14;
                            AlertsCreator.createClearOrDeleteDialogAlert(this, z15, chat2, user, zIsEncryptedDialog, z16, false, false, new MessagesStorage.BooleanCallback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda82
                                @Override // org.telegram.messenger.MessagesStorage.BooleanCallback
                                public final void run(boolean z18) {
                                    this.f$0.lambda$performSelectedDialogsAction$111(i28, chat2, jLongValue2, z17, z18);
                                }
                            });
                            return;
                        }
                        i9 = i28;
                        TLRPC.Chat chat3 = chat;
                        boolean z18 = z14;
                        if (dialogsActivity.getMessagesController().isPromoDialog(jLongValue2, true)) {
                            dialogsActivity.getMessagesController().hidePromoDialog();
                            i7 = i23;
                            dialogFilter3 = dialogFilter2;
                            z4 = false;
                            i8 = i9;
                            z5 = true;
                        } else if (i9 == 103 && dialogsActivity.canClearCacheCount != 0) {
                            z4 = false;
                            dialogsActivity.getMessagesController().deleteDialog(jLongValue2, 2, false);
                            i7 = i23;
                            dialogFilter3 = dialogFilter2;
                            i8 = i9;
                            z5 = true;
                        } else {
                            z4 = false;
                            if (hashSet == null || !hashSet.contains(l5)) {
                                z6 = z18;
                                z7 = false;
                            } else {
                                z6 = z18;
                                z7 = true;
                            }
                            dialogsActivity.lambda$performSelectedDialogsAction$110(i9, jLongValue2, chat3, z6, z7);
                            i7 = i23;
                            dialogFilter3 = dialogFilter2;
                            i8 = i;
                            z5 = true;
                        }
                    }
                    i24++;
                    boolean z19 = z4;
                    z9 = z5;
                    z = z19;
                    dialogFilter = dialogFilter3;
                    i9 = i8;
                    i23 = i7;
                }
                i7 = i23;
                dialogFilter3 = dialogFilter2;
                z4 = false;
            }
            i8 = i9;
            z5 = true;
            i24++;
            boolean z192 = z4;
            z9 = z5;
            z = z192;
            dialogFilter = dialogFilter3;
            i9 = i8;
            i23 = i7;
        }
        boolean z20 = z9;
        boolean z21 = z;
        int i29 = i9;
        MessagesController.DialogFilter dialogFilter4 = dialogFilter;
        if (i29 == 104 && (i5 != z20 || dialogsActivity.canMuteCount != z20)) {
            BulletinFactory.createMuteBulletin(dialogsActivity, dialogsActivity.canUnmuteCount == 0 ? z20 : z21, null).show();
        }
        int i30 = 108;
        if (i29 == 100 || i29 == 108) {
            if (dialogFilter4 != null) {
                FilterCreateActivity.saveFilterToServer(dialogFilter4, dialogFilter4.flags, dialogFilter4.name, dialogFilter4.entities, dialogFilter4.title_noanimate, dialogFilter4.color, dialogFilter4.emoticon, dialogFilter4.alwaysShow, dialogFilter4.neverShow, dialogFilter4.pinnedDialogs, false, false, true, true, false, dialogsActivity, null);
                dialogsActivity = dialogsActivity;
                j = 0;
            } else {
                j = 0;
                dialogsActivity.getMessagesController().reorderPinnedDialogs(dialogsActivity.folderId, null, 0L);
            }
            UndoView undoView2 = dialogsActivity.getUndoView();
            if (dialogsActivity.searchIsShowed && undoView2 != null) {
                undoView2.showWithAction(j, dialogsActivity.canPinCount != 0 ? 78 : 79, Integer.valueOf(i25));
            }
            i6 = i;
            i30 = 108;
        } else {
            i6 = i29;
        }
        dialogsActivity.hideActionMode((i6 == i30 || i6 == 100 || i6 == 102) ? false : true);
    }

    public /* synthetic */ void lambda$performSelectedDialogsAction$105(ArrayList arrayList) {
        getMessagesController().addDialogToFolder(arrayList, this.folderId == 0 ? 0 : 1, -1, null, 0L);
    }

    public /* synthetic */ void lambda$performSelectedDialogsAction$107(ArrayList arrayList, final int i, final HashSet hashSet, final boolean z) {
        if (arrayList.isEmpty()) {
            return;
        }
        final ArrayList<Long> arrayList2 = new ArrayList<>(arrayList);
        UndoView undoView = getUndoView();
        if (undoView != null) {
            undoView.showWithAction(arrayList2, i == 102 ? 27 : 26, (Object) null, (Object) null, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda144
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$performSelectedDialogsAction$106(i, arrayList2, z, hashSet);
                }
            }, (Runnable) null);
        }
        hideActionMode(i == 103);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$performSelectedDialogsAction$106(int i, ArrayList arrayList, boolean z, HashSet hashSet) {
        if (i == 102) {
            getMessagesController().setDialogsInTransaction(true);
            performSelectedDialogsAction(arrayList, i, false, false, z ? hashSet : null);
            getMessagesController().setDialogsInTransaction(false);
            getMessagesController().checkIfFolderEmpty(this.folderId);
            if (this.folderId == 0 || getDialogsArray(this.currentAccount, this.viewPages[0].dialogsType, this.folderId, false).size() != 0) {
                return;
            }
            this.viewPages[0].listView.setEmptyView(null);
            this.viewPages[0].progressView.setVisibility(4);
            finishFragment();
            return;
        }
        performSelectedDialogsAction(arrayList, i, false, false);
    }

    public /* synthetic */ void lambda$performSelectedDialogsAction$108(ArrayList arrayList, boolean z, boolean z2) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Long l = (Long) arrayList.get(i);
            long jLongValue = l.longValue();
            if (z) {
                getMessagesController().reportSpam(jLongValue, getMessagesController().getUser(l), null, null, false);
            }
            if (z2) {
                getMessagesController().deleteDialog(jLongValue, 0, true);
            }
            getMessagesController().blockPeer(jLongValue);
        }
        hideActionMode(false);
    }

    public /* synthetic */ void lambda$performSelectedDialogsAction$109(AlertDialog alertDialog, int i) {
        getMessagesController().hidePromoDialog();
        hideActionMode(false);
    }

    public /* synthetic */ void lambda$performSelectedDialogsAction$111(final int i, TLRPC.Chat chat, final long j, final boolean z, final boolean z2) {
        final TLRPC.Chat chat2;
        int i2;
        ArrayList<TLRPC.Dialog> arrayList;
        hideActionMode(false);
        if (i == 103 && ChatObject.isChannel(chat)) {
            chat2 = chat;
            if (!chat2.megagroup || ChatObject.isPublic(chat2)) {
                getMessagesController().deleteDialog(j, 2, z2);
                return;
            }
        } else {
            chat2 = chat;
        }
        if (i == 102 && this.folderId != 0 && getDialogsArray(this.currentAccount, this.viewPages[0].dialogsType, this.folderId, false).size() == 1) {
            this.viewPages[0].progressView.setVisibility(4);
        }
        this.debugLastUpdateAction = 3;
        int i3 = -1;
        if (i == 102) {
            setDialogsListFrozen(true);
            if (this.frozenDialogsList != null) {
                int i4 = 0;
                while (i4 < this.frozenDialogsList.size()) {
                    if (this.frozenDialogsList.get(i4).f1251id == j) {
                        break;
                    } else {
                        i4++;
                    }
                }
                i4 = -1;
                checkAnimationFinished();
                i2 = i4;
            } else {
                i4 = -1;
                checkAnimationFinished();
                i2 = i4;
            }
        } else {
            i2 = -1;
        }
        UndoView undoView = getUndoView();
        if (undoView != null) {
            undoView.showWithAction(j, i == 103 ? 0 : z2 ? 1 : 95, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda136
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$performSelectedDialogsAction$110(i, j, chat2, z, z2);
                }
            });
        }
        ArrayList arrayList2 = new ArrayList(getDialogsArray(this.currentAccount, this.viewPages[0].dialogsType, this.folderId, false));
        int i5 = 0;
        while (true) {
            if (i5 >= arrayList2.size()) {
                break;
            }
            if (((TLRPC.Dialog) arrayList2.get(i5)).f1251id == j) {
                i3 = i5;
                break;
            }
            i5++;
        }
        if (i == 102) {
            if (i2 >= 0 && i3 < 0 && (arrayList = this.frozenDialogsList) != null) {
                arrayList.remove(i2);
                this.viewPages[0].dialogsItemAnimator.prepareForRemove();
                this.viewPages[0].updateList(true);
                return;
            }
            setDialogsListFrozen(false);
        }
    }

    public /* synthetic */ void lambda$performSelectedDialogsAction$112(DialogInterface dialogInterface) {
        hideActionMode(true);
    }

    public /* synthetic */ void lambda$performSelectedDialogsAction$113(DialogInterface dialogInterface) {
        hideActionMode(true);
    }

    private void markAsRead(long j) {
        TLRPC.Dialog dialog = getMessagesController().dialogs_dict.get(j);
        MessagesController.DialogFilter dialogFilter = null;
        if ((this.viewPages[0].dialogsType == 7 || this.viewPages[0].dialogsType == 8) && (!this.actionBar.isActionModeShowed() || this.actionBar.isActionModeShowed(null))) {
            dialogFilter = getMessagesController().selectedDialogFilter[this.viewPages[0].dialogsType == 8 ? (char) 1 : (char) 0];
        }
        this.debugLastUpdateAction = 2;
        int i = -1;
        if (dialogFilter != null && (dialogFilter.flags & MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_READ) != 0 && !dialogFilter.alwaysShow(this.currentAccount, dialog)) {
            setDialogsListFrozen(true);
            checkAnimationFinished();
            if (this.frozenDialogsList != null) {
                int i2 = 0;
                while (true) {
                    if (i2 >= this.frozenDialogsList.size()) {
                        break;
                    }
                    if (this.frozenDialogsList.get(i2).f1251id == j) {
                        i = i2;
                        break;
                    }
                    i2++;
                }
                if (i < 0) {
                    setDialogsListFrozen(false, false);
                }
            }
        }
        int i3 = i;
        if (getMessagesController().isForum(j) || getMessagesController().isMonoForumWithManageRights(j)) {
            getMessagesController().markAllTopicsAsRead(j);
        }
        getMessagesController().markMentionsAsRead(j, 0L);
        MessagesController messagesController = getMessagesController();
        int i4 = dialog.top_message;
        messagesController.markDialogAsRead(j, i4, i4, dialog.last_message_date, false, 0L, 0, true, 0);
        if (i3 >= 0) {
            this.frozenDialogsList.remove(i3);
            this.viewPages[0].dialogsItemAnimator.prepareForRemove();
            this.viewPages[0].updateList(true);
        }
    }

    private void markAsUnread(long j) {
        getMessagesController().markDialogAsUnread(j, null, 0L);
    }

    public void markDialogsAsRead(ArrayList<TLRPC.Dialog> arrayList) {
        this.debugLastUpdateAction = 2;
        setDialogsListFrozen(true);
        checkAnimationFinished();
        for (int i = 0; i < arrayList.size(); i++) {
            long j = arrayList.get(i).f1251id;
            TLRPC.Dialog dialog = arrayList.get(i);
            if (getMessagesController().isForum(j) || getMessagesController().isMonoForumWithManageRights(j)) {
                getMessagesController().markAllTopicsAsRead(j);
            }
            getMessagesController().markMentionsAsRead(j, 0L);
            MessagesController messagesController = getMessagesController();
            int i2 = dialog.top_message;
            messagesController.markDialogAsRead(j, i2, i2, dialog.last_message_date, false, 0L, 0, true, 0);
        }
    }

    /* JADX INFO: renamed from: performDeleteOrClearDialogAction */
    public void lambda$performSelectedDialogsAction$110(int i, long j, TLRPC.Chat chat, boolean z, boolean z2) {
        if (i == 103) {
            getMessagesController().deleteDialog(j, 1, z2);
            return;
        }
        if (chat != null) {
            if (ChatObject.isNotInChat(chat)) {
                getMessagesController().deleteDialog(j, 0, z2);
            } else {
                getMessagesController().deleteParticipantFromChat(-j, getMessagesController().getUser(Long.valueOf(getUserConfig().getClientUserId())), (TLRPC.Chat) null, z2, false);
            }
        } else {
            getMessagesController().deleteDialog(j, 0, z2);
            if (z && z2) {
                getMessagesController().blockPeer(j);
            }
        }
        if (AndroidUtilities.isTablet()) {
            getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, Long.valueOf(j));
        }
        getMessagesController().checkIfFolderEmpty(this.folderId);
    }

    /* JADX WARN: Removed duplicated region for block: B:161:0x013a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void pinDialog(long r14, boolean r16, org.telegram.messenger.MessagesController.DialogFilter r17, int r18, boolean r19) {
        /*
            Method dump skipped, instruction units count: 321
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.pinDialog(long, boolean, org.telegram.messenger.MessagesController$DialogFilter, int, boolean):void");
    }

    public /* synthetic */ void lambda$pinDialog$114() {
        setDialogsListFrozen(false);
    }

    public void scrollToTop(boolean z, boolean z2) {
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if (rightSlidingDialogContainer == null || !rightSlidingDialogContainer.hasFragment()) {
            int i = (this.viewPages[0].dialogsType == 0 && hasHiddenArchive() && this.viewPages[0].archivePullViewState == 2) ? 1 : 0;
            int i2 = (!this.hasStories || z2 || this.dialogStoriesCell.isExpanded()) ? 0 : -AndroidUtilities.m1036dp(81.0f);
            ViewPage[] viewPageArr = this.viewPages;
            if (z) {
                viewPageArr[0].scrollHelper.setScrollDirection(1);
                this.viewPages[0].scrollHelper.scrollToPosition(i, i2, false, true);
                resetScroll();
            } else {
                viewPageArr[0].layoutManager.scrollToPositionWithOffset(i, i2);
                resetScroll();
            }
        }
    }

    public void updateCounters(boolean z) {
        int i;
        boolean z2;
        long j;
        TLRPC.User user;
        this.canDeletePsaSelected = false;
        this.canUnarchiveCount = 0;
        this.canUnmuteCount = 0;
        this.canMuteCount = 0;
        this.canPinCount = 0;
        this.canReadCount = 0;
        this.forumCount = 0;
        this.canClearCacheCount = 0;
        this.canReportSpamCount = 0;
        if (z) {
            return;
        }
        int size = this.selectedDialogs.size();
        long clientUserId = getUserConfig().getClientUserId();
        SharedPreferences notificationsSettings = getNotificationsSettings();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i2 < size) {
            TLRPC.Dialog dialog = getMessagesController().dialogs_dict.get(this.selectedDialogs.get(i2).longValue());
            if (dialog == null) {
                j = clientUserId;
            } else {
                long j2 = dialog.f1251id;
                boolean zIsDialogPinned = isDialogPinned(dialog);
                boolean z3 = dialog.unread_count != 0 || dialog.unread_mark;
                if (getMessagesController().isForum(j2)) {
                    this.forumCount++;
                }
                j = clientUserId;
                if (getMessagesController().isDialogMuted(j2, 0L)) {
                    this.canUnmuteCount++;
                } else {
                    this.canMuteCount++;
                }
                if (z3) {
                    this.canReadCount++;
                }
                if (this.folderId == 1 || dialog.folder_id == 1) {
                    this.canUnarchiveCount++;
                } else if (j2 != j && j2 != 777000 && !getMessagesController().isPromoDialog(j2, false)) {
                    i5++;
                }
                if (!DialogObject.isUserDialog(j2) || j2 == j || j2 == UserObject.VERIFY || MessagesController.isSupportUser(getMessagesController().getUser(Long.valueOf(j2)))) {
                    i7++;
                } else {
                    if (notificationsSettings.getBoolean("dialog_bar_report" + j2, true)) {
                        this.canReportSpamCount++;
                    }
                }
                if (DialogObject.isChannel(dialog)) {
                    TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(-j2));
                    if (getMessagesController().isPromoDialog(dialog.f1251id, true)) {
                        this.canClearCacheCount++;
                        if (getMessagesController().promoDialogType == MessagesController.PROMO_TYPE_PSA) {
                            i3++;
                            this.canDeletePsaSelected = true;
                        }
                    } else {
                        if (zIsDialogPinned) {
                            i6++;
                        } else {
                            this.canPinCount++;
                        }
                        if (chat == null || !chat.megagroup || ChatObject.isPublic(chat)) {
                            this.canClearCacheCount++;
                        }
                        i3++;
                    }
                } else {
                    boolean zIsChatDialog = DialogObject.isChatDialog(dialog.f1251id);
                    if (zIsChatDialog) {
                        getMessagesController().getChat(Long.valueOf(-dialog.f1251id));
                    }
                    if (DialogObject.isEncryptedDialog(dialog.f1251id)) {
                        TLRPC.EncryptedChat encryptedChat = getMessagesController().getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(dialog.f1251id)));
                        if (encryptedChat != null) {
                            user = getMessagesController().getUser(Long.valueOf(encryptedChat.user_id));
                        } else {
                            user = new TLRPC.TL_userEmpty();
                        }
                    } else {
                        user = (zIsChatDialog || !DialogObject.isUserDialog(dialog.f1251id)) ? null : getMessagesController().getUser(Long.valueOf(dialog.f1251id));
                    }
                    if (user != null && user.bot) {
                        MessagesController.isSupportUser(user);
                    }
                    if (zIsDialogPinned) {
                        i6++;
                    } else {
                        this.canPinCount++;
                    }
                }
                i4++;
                i3++;
            }
            i2++;
            clientUserId = j;
        }
        ActionBarMenuItem actionBarMenuItem = this.deleteItem;
        if (actionBarMenuItem != null) {
            if (i3 != size) {
                actionBarMenuItem.setVisibility(8);
            } else {
                actionBarMenuItem.setVisibility(0);
            }
        }
        ActionBarMenuSubItem actionBarMenuSubItem = this.clearItem;
        if (actionBarMenuSubItem != null) {
            int i8 = this.canClearCacheCount;
            if ((i8 != 0 && i8 != size) || (i4 != 0 && i4 != size)) {
                actionBarMenuSubItem.setVisibility(8);
            } else {
                actionBarMenuSubItem.setVisibility(0);
                int i9 = this.canClearCacheCount;
                ActionBarMenuSubItem actionBarMenuSubItem2 = this.clearItem;
                if (i9 != 0) {
                    actionBarMenuSubItem2.setText(LocaleController.getString(C2797R.string.ClearHistoryCache));
                } else {
                    actionBarMenuSubItem2.setText(LocaleController.getString(C2797R.string.ClearHistory));
                }
            }
        }
        ActionBarMenuSubItem actionBarMenuSubItem3 = this.archiveItem;
        if (actionBarMenuSubItem3 != null && this.archive2Item != null) {
            if (this.canUnarchiveCount != 0) {
                String string = LocaleController.getString(C2797R.string.Unarchive);
                this.archiveItem.setTextAndIcon(string, C2797R.drawable.msg_unarchive);
                this.archive2Item.setIcon(C2797R.drawable.msg_unarchive);
                this.archive2Item.setContentDescription(string);
                FilterTabsView filterTabsView = this.filterTabsView;
                if (filterTabsView != null && filterTabsView.getVisibility() == 0) {
                    this.archive2Item.setVisibility(0);
                    this.archiveItem.setVisibility(8);
                } else {
                    this.archiveItem.setVisibility(0);
                    this.archive2Item.setVisibility(8);
                }
            } else if (i5 != 0) {
                String string2 = LocaleController.getString(C2797R.string.Archive);
                this.archiveItem.setTextAndIcon(string2, C2797R.drawable.msg_archive);
                this.archive2Item.setIcon(C2797R.drawable.msg_archive);
                this.archive2Item.setContentDescription(string2);
                FilterTabsView filterTabsView2 = this.filterTabsView;
                if (filterTabsView2 != null && filterTabsView2.getVisibility() == 0) {
                    this.archive2Item.setVisibility(0);
                    this.archiveItem.setVisibility(8);
                } else {
                    this.archiveItem.setVisibility(0);
                    this.archive2Item.setVisibility(8);
                }
            } else {
                actionBarMenuSubItem3.setVisibility(8);
                this.archive2Item.setVisibility(8);
            }
        }
        ActionBarMenuItem actionBarMenuItem2 = this.pinItem;
        if (actionBarMenuItem2 == null || this.pin2Item == null) {
            i = 0;
        } else if (this.canPinCount + i6 != size) {
            actionBarMenuItem2.setVisibility(8);
            this.pin2Item.setVisibility(8);
            i = 0;
        } else {
            FilterTabsView filterTabsView3 = this.filterTabsView;
            if (filterTabsView3 != null && filterTabsView3.getVisibility() == 0) {
                i = 0;
                this.pin2Item.setVisibility(0);
                this.pinItem.setVisibility(8);
            } else {
                i = 0;
                this.pinItem.setVisibility(0);
                this.pin2Item.setVisibility(8);
            }
        }
        ActionBarMenuSubItem actionBarMenuSubItem4 = this.blockItem;
        if (actionBarMenuSubItem4 != null) {
            if (i7 != 0) {
                actionBarMenuSubItem4.setVisibility(8);
            } else {
                actionBarMenuSubItem4.setVisibility(i);
            }
        }
        if (this.removeFromFolderItem == null) {
            z2 = false;
        } else {
            FilterTabsView filterTabsView4 = this.filterTabsView;
            boolean z4 = filterTabsView4 == null || filterTabsView4.getVisibility() != 0 || this.filterTabsView.currentTabIsDefault();
            if (!z4) {
                try {
                    z4 = size >= getDialogsArray(this.currentAccount, this.viewPages[0].dialogsAdapter.getDialogsType(), this.folderId, this.dialogsListFrozen).size();
                } catch (Exception unused) {
                }
            }
            ActionBarMenuSubItem actionBarMenuSubItem5 = this.removeFromFolderItem;
            if (z4) {
                actionBarMenuSubItem5.setVisibility(8);
                z2 = false;
            } else {
                z2 = false;
                actionBarMenuSubItem5.setVisibility(0);
            }
        }
        if (this.addToFolderItem != null) {
            if (this.folderId == 1 || (this.filterTabsView != null && getFilterTabsVisibilityFactor(z2) > 0.5f && this.filterTabsView.currentTabIsDefault() && !FiltersListBottomSheet.getCanAddDialogFilters(this, this.selectedDialogs).isEmpty())) {
                this.addToFolderItem.setVisibility(0);
            } else {
                this.addToFolderItem.setVisibility(8);
            }
        }
        ActionBarMenuItem actionBarMenuItem3 = this.muteItem;
        if (actionBarMenuItem3 != null) {
            if (this.canUnmuteCount != 0) {
                actionBarMenuItem3.setIcon(C2797R.drawable.msg_unmute);
                this.muteItem.setContentDescription(LocaleController.getString(C2797R.string.ChatsUnmute));
            } else {
                actionBarMenuItem3.setIcon(C2797R.drawable.msg_mute);
                this.muteItem.setContentDescription(LocaleController.getString(C2797R.string.ChatsMute));
            }
        }
        ActionBarMenuSubItem actionBarMenuSubItem6 = this.readItem;
        if (actionBarMenuSubItem6 != null) {
            if (this.canReadCount != 0) {
                actionBarMenuSubItem6.setTextAndIcon(LocaleController.getString(C2797R.string.MarkAsRead), C2797R.drawable.msg_markread);
                this.readItem.setVisibility(0);
            } else if (this.forumCount == 0) {
                actionBarMenuSubItem6.setTextAndIcon(LocaleController.getString(C2797R.string.MarkAsUnread), C2797R.drawable.msg_markunread);
                this.readItem.setVisibility(0);
            } else {
                actionBarMenuSubItem6.setVisibility(8);
            }
        }
        ActionBarMenuItem actionBarMenuItem4 = this.pinItem;
        if (actionBarMenuItem4 == null || this.pin2Item == null) {
            return;
        }
        if (this.canPinCount != 0) {
            actionBarMenuItem4.setIcon(C2797R.drawable.msg_pin);
            this.pinItem.setContentDescription(LocaleController.getString(C2797R.string.PinToTop));
            this.pin2Item.setText(LocaleController.getString(C2797R.string.DialogPin));
        } else {
            actionBarMenuItem4.setIcon(C2797R.drawable.msg_unpin);
            this.pinItem.setContentDescription(LocaleController.getString(C2797R.string.UnpinFromTop));
            this.pin2Item.setText(LocaleController.getString(C2797R.string.DialogUnpin));
        }
    }

    public boolean validateSlowModeDialog(long j) {
        TLRPC.Chat chat;
        ChatActivityEnterView chatActivityEnterView;
        if ((this.messagesCount <= 1 && ((chatActivityEnterView = this.commentView) == null || chatActivityEnterView.getVisibility() != 0 || TextUtils.isEmpty(this.commentView.getFieldText()))) || !DialogObject.isChatDialog(j) || (chat = getMessagesController().getChat(Long.valueOf(-j))) == null || ChatObject.hasAdminRights(chat) || !chat.slowmode_enabled) {
            return true;
        }
        AlertsCreator.showSimpleAlert(this, LocaleController.getString(C2797R.string.Slowmode), LocaleController.getString(C2797R.string.SlowmodeSendError));
        return false;
    }

    public void showOrUpdateActionMode(long j, View view) {
        addOrRemoveSelectedDialog(j, view);
        boolean z = true;
        if (this.actionBar.isActionModeShowed()) {
            if (this.selectedDialogs.isEmpty()) {
                hideActionMode(true);
                return;
            }
        } else {
            if (this.searchIsShowed) {
                createActionMode("search_dialogs_action_mode");
                if (this.actionBar.getBackButton() != null && (this.actionBar.getBackButton().getDrawable() instanceof MenuDrawable)) {
                    this.actionBar.setBackButtonDrawable(new BackDrawable(false));
                }
            } else {
                createActionMode(null);
            }
            AndroidUtilities.hideKeyboard(this.fragmentView.findFocus());
            this.actionBar.setActionModeOverrideColor(getThemedColor(Theme.key_windowBackgroundWhite));
            this.actionBar.showActionMode();
            if (getPinnedCount() > 1) {
                ViewPage[] viewPageArr = this.viewPages;
                if (viewPageArr != null) {
                    for (ViewPage viewPage : viewPageArr) {
                        viewPage.dialogsAdapter.onReorderStateChanged(true);
                    }
                }
                updateVisibleRows(MessagesController.UPDATE_MASK_REORDER);
            }
            if (!this.searchIsShowed) {
                AnimatorSet animatorSet = new AnimatorSet();
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < this.actionModeViews.size(); i++) {
                    View view2 = this.actionModeViews.get(i);
                    view2.setPivotY(ActionBar.getCurrentActionBarHeight() / 2);
                    AndroidUtilities.clearDrawableAnimation(view2);
                    arrayList.add(ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.SCALE_Y, 0.1f, 1.0f));
                }
                animatorSet.playTogether(arrayList);
                animatorSet.setDuration(200L);
                animatorSet.start();
            }
            ValueAnimator valueAnimator = this.actionBarColorAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            this.actionBarColorAnimator = ValueAnimator.ofFloat(this.progressToActionMode, 1.0f);
            int i2 = 0;
            while (true) {
                ViewPage[] viewPageArr2 = this.viewPages;
                if (i2 >= viewPageArr2.length) {
                    break;
                }
                ViewPage viewPage2 = viewPageArr2[i2];
                if (viewPage2 != null) {
                    viewPage2.listView.cancelClickRunnables(true);
                }
                i2++;
            }
            final float fMax = Math.max(0.0f, AndroidUtilities.m1036dp(this.hasStories ? 81.0f : 0.0f) + getSearchFieldHeight() + this.scrollYOffset);
            if (fMax != 0.0f) {
                this.actionModeAdditionalHeight = (int) fMax;
                this.fragmentView.requestLayout();
            }
            this.actionBarColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda135
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    this.f$0.lambda$showOrUpdateActionMode$115(fMax, valueAnimator2);
                }
            });
            this.actionBarColorAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.37
                final /* synthetic */ float val$finalTranslateListHeight;

                public C555137(final float fMax2) {
                    f = fMax2;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    DialogsActivity.this.actionBarColorAnimator = null;
                    DialogsActivity.this.actionModeAdditionalHeight = 0;
                    DialogsActivity.this.actionModeFullyShowed = true;
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    dialogsActivity.scrollAdditionalOffset = (AndroidUtilities.m1036dp(dialogsActivity.hasStories ? 81.0f : 0.0f) + DialogsActivity.this.getSearchFieldHeight()) - f;
                    DialogsActivity.this.viewPages[0].setTranslationY(0.0f);
                    int i3 = 0;
                    while (true) {
                        int length = DialogsActivity.this.viewPages.length;
                        DialogsActivity dialogsActivity2 = DialogsActivity.this;
                        if (i3 < length) {
                            if (dialogsActivity2.viewPages[i3] != null) {
                                DialogsActivity.this.viewPages[i3].listView.requestLayout();
                            }
                            i3++;
                        } else {
                            dialogsActivity2.dialogStoriesCell.setProgressToCollapse(1.0f, false);
                            DialogsActivity.this.fragmentView.requestLayout();
                            return;
                        }
                    }
                }
            });
            this.actionBarColorAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.actionBarColorAnimator.setDuration(200L);
            this.actionBarColorAnimator.start();
            FilterTabsView filterTabsView = this.filterTabsView;
            if (filterTabsView != null) {
                filterTabsView.animateColorsTo(Theme.key_profile_tabSelectedLine, Theme.key_profile_tabSelectedText, Theme.key_profile_tabText, Theme.key_profile_tabSelector, Theme.key_actionBarActionModeDefault);
            }
            BackDrawable backDrawable = this.backDrawable;
            if (backDrawable != null) {
                backDrawable.setRotation(1.0f, true);
            }
            z = false;
        }
        updateCounters(false);
        this.selectedDialogsCountTextView.setNumber(this.selectedDialogs.size(), z);
    }

    public /* synthetic */ void lambda$showOrUpdateActionMode$115(float f, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.progressToActionMode = fFloatValue;
        this.viewPages[0].setTranslationY((-f) * fFloatValue);
        for (int i = 0; i < this.actionBar.getChildCount(); i++) {
            if (this.actionBar.getChildAt(i).getVisibility() == 0 && this.actionBar.getChildAt(i) != this.actionBar.getActionMode() && this.actionBar.getChildAt(i) != this.actionBar.getBackButton()) {
                this.actionBar.getChildAt(i).setAlpha(1.0f - this.progressToActionMode);
            }
        }
        View view = this.fragmentView;
        if (view != null) {
            view.invalidate();
        }
        checkUi_searchFieldVisibility();
        checkUi_itemBackButtonVisibility();
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$37 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C555137 extends AnimatorListenerAdapter {
        final /* synthetic */ float val$finalTranslateListHeight;

        public C555137(final float fMax2) {
            f = fMax2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            DialogsActivity.this.actionBarColorAnimator = null;
            DialogsActivity.this.actionModeAdditionalHeight = 0;
            DialogsActivity.this.actionModeFullyShowed = true;
            DialogsActivity dialogsActivity = DialogsActivity.this;
            dialogsActivity.scrollAdditionalOffset = (AndroidUtilities.m1036dp(dialogsActivity.hasStories ? 81.0f : 0.0f) + DialogsActivity.this.getSearchFieldHeight()) - f;
            DialogsActivity.this.viewPages[0].setTranslationY(0.0f);
            int i3 = 0;
            while (true) {
                int length = DialogsActivity.this.viewPages.length;
                DialogsActivity dialogsActivity2 = DialogsActivity.this;
                if (i3 < length) {
                    if (dialogsActivity2.viewPages[i3] != null) {
                        DialogsActivity.this.viewPages[i3].listView.requestLayout();
                    }
                    i3++;
                } else {
                    dialogsActivity2.dialogStoriesCell.setProgressToCollapse(1.0f, false);
                    DialogsActivity.this.fragmentView.requestLayout();
                    return;
                }
            }
        }
    }

    public void closeSearch() {
        if (AndroidUtilities.isTablet()) {
            ActionBar actionBar = this.actionBar;
            if (actionBar != null) {
                actionBar.closeSearchField();
            }
            TLObject tLObject = this.searchObject;
            if (tLObject != null) {
                SearchViewPager searchViewPager = this.searchViewPager;
                if (searchViewPager != null) {
                    searchViewPager.dialogsSearchAdapter.putRecentSearch(this.searchDialogId, tLObject);
                }
                this.searchObject = null;
                return;
            }
            return;
        }
        this.closeSearchFieldOnHide = true;
    }

    public RecyclerListView getListView() {
        return this.viewPages[0].listView;
    }

    public RecyclerListView getSearchListView() {
        createSearchViewPager();
        SearchViewPager searchViewPager = this.searchViewPager;
        if (searchViewPager != null) {
            return searchViewPager.searchListView;
        }
        return null;
    }

    public void createUndoView() {
        Context context;
        if (this.undoView[0] == null && (context = getContext()) != null) {
            for (int i = 0; i < 2; i++) {
                this.undoView[i] = new C555238(context);
                FrameLayout.LayoutParams layoutParamsCreateFrame = LayoutHelper.createFrame(-1, -2.0f, 83, 8.0f, 0.0f, 8.0f, 8.0f);
                layoutParamsCreateFrame.bottomMargin += this.navigationBarHeight + getCurrentUndoViewOffset();
                ContentView contentView = (ContentView) this.fragmentView;
                UndoView undoView = this.undoView[i];
                int i2 = this.undoViewIndex + 1;
                this.undoViewIndex = i2;
                contentView.addView(undoView, i2, layoutParamsCreateFrame);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$38 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C555238 extends UndoView {
        public C555238(Context context) {
            super(context);
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            super.setTranslationY(f);
            if (this == DialogsActivity.this.undoView[0]) {
                if (DialogsActivity.this.undoView[1] == null || DialogsActivity.this.undoView[1].getVisibility() != 0) {
                    DialogsActivity.this.additionalFloatingTranslation = Math.max(0.0f, (getMeasuredHeight() + AndroidUtilities.m1036dp(8.0f)) - f);
                    DialogsActivity.this.updateFloatingButtonOffset();
                }
            }
        }

        @Override // org.telegram.p035ui.Components.UndoView
        public boolean canUndo() {
            for (int i = 0; i < DialogsActivity.this.viewPages.length; i++) {
                if (DialogsActivity.this.viewPages[i].dialogsItemAnimator.isRunning()) {
                    return false;
                }
            }
            return true;
        }

        @Override // org.telegram.p035ui.Components.UndoView
        public void onRemoveDialogAction(long j, int i) {
            if (i == 1 || i == 27) {
                DialogsActivity.this.debugLastUpdateAction = 1;
                DialogsActivity.this.setDialogsListFrozen(true);
                if (DialogsActivity.this.frozenDialogsList != null) {
                    final int i2 = 0;
                    while (true) {
                        if (i2 >= DialogsActivity.this.frozenDialogsList.size()) {
                            i2 = -1;
                            break;
                        } else if (((TLRPC.Dialog) DialogsActivity.this.frozenDialogsList.get(i2)).f1251id == j) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    if (i2 >= 0) {
                        final TLRPC.Dialog dialog = (TLRPC.Dialog) dialogsActivity.frozenDialogsList.remove(i2);
                        DialogsActivity.this.viewPages[0].dialogsAdapter.notifyDataSetChanged();
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$38$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$onRemoveDialogAction$0(i2, dialog);
                            }
                        });
                    } else {
                        dialogsActivity.setDialogsListFrozen(false);
                    }
                }
                DialogsActivity.this.checkAnimationFinished();
            }
        }

        public /* synthetic */ void lambda$onRemoveDialogAction$0(int i, TLRPC.Dialog dialog) {
            if (DialogsActivity.this.frozenDialogsList == null || i < 0 || i >= DialogsActivity.this.frozenDialogsList.size()) {
                return;
            }
            DialogsActivity.this.frozenDialogsList.add(i, dialog);
            DialogsActivity.this.viewPages[0].updateList(true);
        }
    }

    public UndoView getUndoView() {
        createUndoView();
        UndoView undoView = this.undoView[0];
        if (undoView != null && undoView.getVisibility() == 0) {
            UndoView[] undoViewArr = this.undoView;
            UndoView undoView2 = undoViewArr[0];
            undoViewArr[0] = undoViewArr[1];
            undoViewArr[1] = undoView2;
            undoView2.hide(true, 2);
            ContentView contentView = (ContentView) this.fragmentView;
            contentView.removeView(this.undoView[0]);
            contentView.addView(this.undoView[0]);
        }
        return this.undoView[0];
    }

    public void updateProxyButton(boolean z, boolean z2) {
        boolean z3;
        if (this.proxyDrawable != null) {
            ActionBarMenuItem actionBarMenuItem = this.doneItem;
            if (actionBarMenuItem == null || actionBarMenuItem.getVisibility() != 0) {
                int i = 0;
                while (true) {
                    if (i >= getDownloadController().downloadingFiles.size()) {
                        z3 = false;
                        break;
                    } else {
                        if (getFileLoader().isLoadingFile(getDownloadController().downloadingFiles.get(i).getFileName())) {
                            z3 = true;
                            break;
                        }
                        i++;
                    }
                }
                this.downloadsItemVisible = getDownloadController().hasUnviewedDownloads() || z3;
                checkUi_itemDownloadsVisibility();
                boolean z4 = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean("proxy_enabled", false);
                int i2 = this.currentConnectionState;
                boolean z5 = i2 == 3 || i2 == 5;
                this.proxyDrawable.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_actionBarDefaultSubmenuItemIcon), PorterDuff.Mode.SRC_IN));
                this.proxyMenuSubItem.setTextColor(getThemedColor(Theme.key_actionBarDefaultSubmenuItem));
                ActionBarMenuSubItem actionBarMenuSubItem = this.proxyMenuSubItem;
                if (z4) {
                    actionBarMenuSubItem.setItemHeight(56);
                    this.proxyMenuSubItem.setSubtext(LocaleController.getString(z4 ? z5 ? C2797R.string.MenuProxyConnected : C2797R.string.MenuProxyConnecting : C2797R.string.MenuProxyDisabled));
                } else {
                    actionBarMenuSubItem.setItemHeight(48);
                    this.proxyMenuSubItem.setSubtext(null);
                }
                this.proxyDrawable.setConnected(z4, z5, z);
            }
        }
    }

    public void showDoneItem(boolean z) {
        this.animatorDoneButtonVisible.setValue(z, true);
        if (this.doneItem == null) {
            return;
        }
        AnimatorSet animatorSet = this.doneItemAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.doneItemAnimator = null;
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.doneItemAnimator = animatorSet2;
        animatorSet2.setDuration(180L);
        ActionBarMenuItem actionBarMenuItem = this.doneItem;
        if (z) {
            actionBarMenuItem.setVisibility(0);
        } else {
            actionBarMenuItem.setSelected(false);
            Drawable background = this.doneItem.getBackground();
            if (background != null) {
                background.setState(StateSet.NOTHING);
                background.jumpToCurrentState();
            }
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(ObjectAnimator.ofFloat(this.doneItem, (Property<ActionBarMenuItem, Float>) View.ALPHA, z ? 1.0f : 0.0f));
        this.doneItemAnimator.playTogether(arrayList);
        this.doneItemAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.39
            final /* synthetic */ boolean val$show;

            public C555339(boolean z2) {
                z = z2;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                DialogsActivity.this.doneItemAnimator = null;
                if (z || DialogsActivity.this.doneItem == null) {
                    return;
                }
                DialogsActivity.this.doneItem.setVisibility(8);
            }
        });
        this.doneItemAnimator.start();
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$39 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C555339 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$show;

        public C555339(boolean z2) {
            z = z2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            DialogsActivity.this.doneItemAnimator = null;
            if (z || DialogsActivity.this.doneItem == null) {
                return;
            }
            DialogsActivity.this.doneItem.setVisibility(8);
        }
    }

    public void updateSelectedCount() {
        ShareTopView shareTopView;
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        CharSequence fieldText = _UrlKt.FRAGMENT_ENCODE_SET;
        if (chatActivityEnterView != null) {
            this.animatorForwardButtonVisible.setValue(!this.selectedDialogs.isEmpty(), true);
            updateShareTopViewRecipients();
            if (this.selectedDialogs.isEmpty()) {
                String string = LocaleController.getString((this.initialDialogsType == 3 && this.selectAlertString == null) ? C2797R.string.ForwardTo : C2797R.string.SelectChat);
                boolean z = this.wasSelectedDialogsEmpty;
                boolean zIsEmpty = this.selectedDialogs.isEmpty();
                ActionBar actionBar = this.actionBar;
                if (z == zIsEmpty) {
                    actionBar.setTitle(string);
                } else {
                    actionBar.setTitleAnimated(string, true, 350L, CubicBezierInterpolator.EASE_OUT_QUINT);
                }
                if (this.commentView.getTag() != null) {
                    this.commentView.hidePopup(false, false, false);
                    this.commentView.closeKeyboard();
                    this.commentView.setTag(null);
                    this.fragmentView.requestLayout();
                }
            } else {
                if (this.commentView.getTag() == null) {
                    if (!hasSharedMediaEntries() && this.sharedLink == null) {
                        this.commentView.setFieldText(_UrlKt.FRAGMENT_ENCODE_SET);
                    }
                    this.commentView.setTag(1);
                    if (!this.shareHintStarted && (shareTopView = this.shareTopView) != null) {
                        this.shareHintStarted = true;
                        shareTopView.startHintRotation(LocaleController.getString(C2797R.string.ShareTapToEditMedia));
                    }
                }
                this.writeButton.setCount(Math.max(1, this.selectedDialogs.size()), true);
                int i = this.messagesCount + (!TextUtils.isEmpty(this.commentView.getFieldText()) ? 1 : 0);
                ArrayList<Long> arrayList = this.selectedDialogs;
                int size = arrayList.size();
                int i2 = 0;
                long j = 0;
                while (i2 < size) {
                    Long l = arrayList.get(i2);
                    i2++;
                    long jLongValue = l.longValue();
                    long sendPaidMessagesStars = getMessagesController().getSendPaidMessagesStars(jLongValue);
                    if (sendPaidMessagesStars <= 0 && jLongValue > 0) {
                        sendPaidMessagesStars = DialogObject.getMessagesStarsPrice(getMessagesController().isUserContactBlocked(jLongValue));
                    }
                    j += sendPaidMessagesStars;
                }
                this.writeButton.setStarsPrice(j, i);
                this.commentView.updateSendButtonPaid();
                boolean z2 = this.wasSelectedDialogsEmpty;
                boolean zIsEmpty2 = this.selectedDialogs.isEmpty();
                ActionBar actionBar2 = this.actionBar;
                if (z2 == zIsEmpty2) {
                    actionBar2.setTitle(LocaleController.formatPluralString("Recipient", this.selectedDialogs.size(), new Object[0]));
                } else {
                    actionBar2.setTitleAnimated(LocaleController.formatPluralString("Recipient", this.selectedDialogs.size(), new Object[0]), false, 350L, CubicBezierInterpolator.EASE_OUT_QUINT);
                }
            }
            this.wasSelectedDialogsEmpty = this.selectedDialogs.isEmpty();
        } else if (this.initialDialogsType == 10) {
            hideFloatingButton(this.selectedDialogs.isEmpty());
        }
        ArrayList<Long> arrayList2 = this.selectedDialogs;
        ChatActivityEnterView chatActivityEnterView2 = this.commentView;
        if (chatActivityEnterView2 != null) {
            fieldText = chatActivityEnterView2.getFieldText();
        }
        boolean zShouldShowNextButton = shouldShowNextButton(this, arrayList2, fieldText, false);
        this.isNextButton = zShouldShowNextButton;
        this.writeButton.setResourceId(zShouldShowNextButton ? C2797R.drawable.msg_arrow_forward : C2797R.drawable.send_extera_24);
    }

    @TargetApi(23)
    private void askForPermissons(boolean z) {
        final Activity parentActivity = getParentActivity();
        if (parentActivity == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (this.folderId == 0 && Build.VERSION.SDK_INT >= 33 && NotificationPermissionDialog.shouldAsk(parentActivity)) {
            if (z) {
                showDialog(new NotificationPermissionDialog(parentActivity, !PermissionRequest.canAskPermission("android.permission.POST_NOTIFICATIONS"), new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda40
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        DialogsActivity.m15602$r8$lambda$dZ7EitBJVIvUlJ6GkcMxRrtTk0(parentActivity, (Boolean) obj);
                    }
                }));
                return;
            }
            arrayList.add("android.permission.POST_NOTIFICATIONS");
        }
        if (getUserConfig().syncContacts && this.askAboutContacts && parentActivity.checkSelfPermission("android.permission.READ_CONTACTS") != 0) {
            if (z) {
                AlertDialog alertDialogCreate = AlertsCreator.createContactsPermissionDialog(parentActivity, new MessagesStorage.IntCallback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda41
                    @Override // org.telegram.messenger.MessagesStorage.IntCallback
                    public final void run(int i) {
                        this.f$0.lambda$askForPermissons$117(i);
                    }
                }).create();
                this.permissionDialog = alertDialogCreate;
                showDialog(alertDialogCreate);
                return;
            } else {
                arrayList.add("android.permission.READ_CONTACTS");
                arrayList.add("android.permission.WRITE_CONTACTS");
                arrayList.add("android.permission.GET_ACCOUNTS");
            }
        }
        int i = Build.VERSION.SDK_INT;
        if (i >= 33) {
            if (parentActivity.checkSelfPermission("android.permission.READ_MEDIA_IMAGES") != 0) {
                arrayList.add("android.permission.READ_MEDIA_IMAGES");
            }
            if (parentActivity.checkSelfPermission("android.permission.READ_MEDIA_VIDEO") != 0) {
                arrayList.add("android.permission.READ_MEDIA_VIDEO");
            }
            if (parentActivity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
            }
        } else if ((i <= 28 || BuildVars.NO_SCOPED_STORAGE) && parentActivity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
            arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        }
        if (arrayList.isEmpty()) {
            if (this.askingForPermissions) {
                this.askingForPermissions = false;
                showFiltersHint();
                return;
            }
            return;
        }
        try {
            parentActivity.requestPermissions((String[]) arrayList.toArray(new String[0]), 1);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$dZ7EitBJVIvUlJ6GkcMxRrt-Tk0 */
    public static /* synthetic */ void m15602$r8$lambda$dZ7EitBJVIvUlJ6GkcMxRrtTk0(Activity activity, Boolean bool) {
        if (bool.booleanValue()) {
            if (!PermissionRequest.canAskPermission("android.permission.POST_NOTIFICATIONS")) {
                PermissionRequest.showPermissionSettings("android.permission.POST_NOTIFICATIONS");
            } else {
                activity.requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 1);
            }
        }
    }

    public /* synthetic */ void lambda$askForPermissons$117(int i) {
        this.askAboutContacts = i != 0;
        MessagesController.getGlobalNotificationsSettings().edit().putBoolean("askAboutContacts", this.askAboutContacts).apply();
        askForPermissons(false);
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onDialogDismiss(Dialog dialog) {
        AlertDialog alertDialog;
        super.onDialogDismiss(dialog);
        if (this.folderId != 0 || (alertDialog = this.permissionDialog) == null || dialog != alertDialog || getParentActivity() == null) {
            return;
        }
        askForPermissons(false);
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ItemOptions itemOptions = this.filterOptions;
        if (itemOptions != null) {
            itemOptions.dismiss();
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        FilesMigrationService.FilesMigrationBottomSheet filesMigrationBottomSheet;
        if (i != 1) {
            if (i == 4) {
                for (int i2 : iArr) {
                    if (i2 != 0) {
                        return;
                    }
                }
                if (Build.VERSION.SDK_INT < 30 || (filesMigrationBottomSheet = FilesMigrationService.filesMigrationBottomSheet) == null) {
                    return;
                }
                filesMigrationBottomSheet.migrateOldFolder();
                return;
            }
            return;
        }
        for (int i3 = 0; i3 < strArr.length; i3++) {
            if (iArr.length > i3) {
                String str = strArr[i3];
                str.getClass();
                switch (str) {
                    case "android.permission.POST_NOTIFICATIONS":
                        if (iArr[i3] == 0) {
                            NotificationsController.getInstance(this.currentAccount).showNotifications();
                            break;
                        } else {
                            NotificationPermissionDialog.askLater();
                            break;
                        }
                        break;
                    case "android.permission.WRITE_EXTERNAL_STORAGE":
                        if (iArr[i3] == 0) {
                            ImageLoader.getInstance().checkMediaPaths();
                            break;
                        } else {
                            break;
                        }
                        break;
                    case "android.permission.READ_CONTACTS":
                        if (iArr[i3] == 0) {
                            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda44
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$onRequestPermissionsResultFragment$118();
                                }
                            });
                            getContactsController().forceImportContacts();
                            break;
                        } else {
                            SharedPreferences.Editor editorEdit = MessagesController.getGlobalNotificationsSettings().edit();
                            this.askAboutContacts = false;
                            editorEdit.putBoolean("askAboutContacts", false).apply();
                            break;
                        }
                        break;
                }
            }
        }
        if (this.askingForPermissions) {
            this.askingForPermissions = false;
            showFiltersHint();
        }
    }

    public /* synthetic */ void lambda$onRequestPermissionsResultFragment$118() {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.forceImportContactsStart, new Object[0]);
    }

    private void reloadViewPageDialogs(ViewPage viewPage, boolean z) {
        int i;
        int i2;
        if (viewPage.getVisibility() != 0) {
            return;
        }
        int currentCount = viewPage.dialogsAdapter.getCurrentCount();
        if (viewPage.dialogsType == 0 && hasHiddenArchive() && viewPage.listView.getChildCount() == 0 && viewPage.archivePullViewState == 2) {
            ((LinearLayoutManager) viewPage.listView.getLayoutManager()).scrollToPositionWithOffset(1, (int) this.scrollYOffset);
        }
        if (viewPage.dialogsAdapter.isDataSetChanged() || z) {
            viewPage.dialogsAdapter.updateHasHints();
            int itemCount = viewPage.dialogsAdapter.getItemCount();
            if (itemCount == 1 && currentCount == 1 && viewPage.dialogsAdapter.getItemViewType(0) == 5) {
                viewPage.updateList(true);
            } else {
                viewPage.updateList(false);
                if (itemCount > currentCount && (i = this.initialDialogsType) != 11 && i != 12 && i != 13) {
                    viewPage.recyclerItemsEnterAnimator.showItemsAnimated(currentCount);
                }
            }
        } else {
            updateVisibleRows(MessagesController.UPDATE_MASK_NEW_MESSAGE);
            if (viewPage.dialogsAdapter.getItemCount() > currentCount && (i2 = this.initialDialogsType) != 11 && i2 != 12 && i2 != 13) {
                viewPage.recyclerItemsEnterAnimator.showItemsAnimated(currentCount);
            }
        }
        try {
            viewPage.listView.setEmptyView(this.folderId == 0 ? viewPage.progressView : null);
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        checkListLoad(viewPage);
    }

    public void setPanTranslationOffset(float f) {
        this.floatingButtonPanOffset = f;
        updateFloatingButtonOffset();
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, final Object... objArr) {
        MessagesController.DialogFilter dialogFilter;
        final boolean zBooleanValue;
        final boolean zBooleanValue2;
        DialogsSearchAdapter dialogsSearchAdapter;
        DialogsSearchAdapter dialogsSearchAdapter2;
        char c2;
        boolean progressVisible;
        MessagesController.DialogFilter dialogFilter2;
        char c3 = 1;
        int i3 = 0;
        if (i == NotificationCenter.dialogsNeedReload) {
            ViewPage[] viewPageArr = this.viewPages;
            if (viewPageArr == null || this.dialogsListFrozen) {
                return;
            }
            for (final ViewPage viewPage : viewPageArr) {
                if (this.viewPages[0].dialogsType == 7 || this.viewPages[0].dialogsType == 8) {
                    dialogFilter2 = getMessagesController().selectedDialogFilter[this.viewPages[0].dialogsType == 8 ? (char) 1 : (char) 0];
                } else {
                    dialogFilter2 = null;
                }
                boolean z = (dialogFilter2 == null || (dialogFilter2.flags & MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_READ) == 0) ? false : true;
                if (this.slowedReloadAfterDialogClick && z) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda55
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$didReceivedNotification$119(viewPage, objArr);
                        }
                    }, 160L);
                } else {
                    reloadViewPageDialogs(viewPage, objArr.length > 0);
                }
            }
            FilterTabsView filterTabsView = this.filterTabsView;
            if (filterTabsView != null && filterTabsView.getVisibility() == 0) {
                this.filterTabsView.checkTabsCounter();
            }
            this.slowedReloadAfterDialogClick = false;
            return;
        }
        if (i == NotificationCenter.topicsDidLoaded) {
            updateVisibleRows(0);
            return;
        }
        if (i == NotificationCenter.dialogsUnreadCounterChanged) {
            FilterTabsView filterTabsView2 = this.filterTabsView;
            if (filterTabsView2 == null || filterTabsView2.getVisibility() != 0) {
                return;
            }
            FilterTabsView filterTabsView3 = this.filterTabsView;
            filterTabsView3.notifyTabCounterChanged(filterTabsView3.getDefaultTabId());
            return;
        }
        if (i == NotificationCenter.dialogsUnreadPollVotesCounterChanged) {
            updateVisibleRows(0);
            return;
        }
        if (i == NotificationCenter.dialogsUnreadReactionsCounterChanged) {
            updateVisibleRows(0);
            return;
        }
        if (i == NotificationCenter.emojiLoaded) {
            if (this.viewPages != null) {
                int i4 = 0;
                while (true) {
                    ViewPage[] viewPageArr2 = this.viewPages;
                    if (i4 >= viewPageArr2.length) {
                        break;
                    }
                    DialogsRecyclerView dialogsRecyclerView = viewPageArr2[i4].listView;
                    if (dialogsRecyclerView != null) {
                        for (int i5 = 0; i5 < dialogsRecyclerView.getChildCount(); i5++) {
                            View childAt = dialogsRecyclerView.getChildAt(i5);
                            if (childAt != null) {
                                childAt.invalidate();
                            }
                        }
                    }
                    i4++;
                }
            }
            FilterTabsView filterTabsView4 = this.filterTabsView;
            if (filterTabsView4 != null) {
                filterTabsView4.getTabsContainer().invalidateViews();
                return;
            }
            return;
        }
        if (i == NotificationCenter.closeSearchByActiveAction) {
            if (this.actionBar == null || AndroidUtilities.isTablet()) {
                return;
            }
            this.actionBar.closeSearchField();
            return;
        }
        if (i == NotificationCenter.proxySettingsChanged) {
            updateProxyButton(false, false);
            return;
        }
        if (i == NotificationCenter.updateInterfaces) {
            Integer num = (Integer) objArr[0];
            updateVisibleRows(num.intValue());
            FilterTabsView filterTabsView5 = this.filterTabsView;
            if (filterTabsView5 != null && filterTabsView5.getVisibility() == 0 && (num.intValue() & MessagesController.UPDATE_MASK_READ_DIALOG_MESSAGE) != 0) {
                this.filterTabsView.checkTabsCounter();
            }
            ViewPage[] viewPageArr3 = this.viewPages;
            if (viewPageArr3 != null) {
                int length = viewPageArr3.length;
                while (i3 < length) {
                    ViewPage viewPage2 = viewPageArr3[i3];
                    if ((num.intValue() & MessagesController.UPDATE_MASK_STATUS) != 0) {
                        viewPage2.dialogsAdapter.sortOnlineContacts(true);
                    }
                    i3++;
                }
            }
            updateStatus(UserConfig.getInstance(i2).getCurrentUser(), true);
            return;
        }
        if (i == NotificationCenter.appDidLogout) {
            dialogsLoaded[this.currentAccount] = false;
            return;
        }
        if (i == NotificationCenter.encryptedChatUpdated) {
            updateVisibleRows(0);
            return;
        }
        if (i == NotificationCenter.contactsDidLoad) {
            if (this.viewPages == null || this.dialogsListFrozen) {
                return;
            }
            FragmentFloatingButton fragmentFloatingButton = this.floatingButton3;
            if (fragmentFloatingButton != null) {
                progressVisible = fragmentFloatingButton.getProgressVisible();
                this.floatingButton3.setProgressVisible(false, true);
            } else {
                progressVisible = false;
            }
            for (ViewPage viewPage3 : this.viewPages) {
                viewPage3.dialogsAdapter.setForceUpdatingContacts(false);
            }
            if (progressVisible) {
                setContactsAlpha(0.0f);
                animateContactsAlpha(1.0f);
            }
            boolean z2 = false;
            for (ViewPage viewPage4 : this.viewPages) {
                if (!viewPage4.isDefaultDialogType() || getMessagesController().getAllFoldersDialogsCount() > 10) {
                    z2 = true;
                } else {
                    viewPage4.dialogsAdapter.notifyDataSetChanged();
                }
            }
            if (z2) {
                updateVisibleRows(0);
                return;
            }
            return;
        }
        char c4 = 2;
        if (i == NotificationCenter.openedChatChanged) {
            ViewPage[] viewPageArr4 = this.viewPages;
            if (viewPageArr4 == null) {
                return;
            }
            int length2 = viewPageArr4.length;
            int i6 = 0;
            while (i6 < length2) {
                ViewPage viewPage5 = viewPageArr4[i6];
                if (viewPage5.isDefaultDialogType() && AndroidUtilities.isTablet()) {
                    boolean zBooleanValue3 = ((Boolean) objArr[c4]).booleanValue();
                    long jLongValue = ((Long) objArr[i3]).longValue();
                    long jLongValue2 = ((Long) objArr[c3]).longValue();
                    MessagesStorage.TopicKey topicKey = this.openedDialogId;
                    if (zBooleanValue3) {
                        c2 = c4;
                        if (jLongValue == topicKey.dialogId && jLongValue2 == topicKey.topicId) {
                            topicKey.dialogId = 0L;
                            topicKey.topicId = 0L;
                        }
                    } else {
                        c2 = c4;
                        topicKey.dialogId = jLongValue;
                        topicKey.topicId = jLongValue2;
                    }
                    viewPage5.dialogsAdapter.setOpenedDialogId(this.openedDialogId.dialogId);
                } else {
                    c2 = c4;
                }
                i6++;
                c4 = c2;
                c3 = 1;
                i3 = 0;
            }
            updateVisibleRows(MessagesController.UPDATE_MASK_SELECT_DIALOG);
            return;
        }
        if (i == NotificationCenter.notificationsSettingsUpdated) {
            updateVisibleRows(0);
            return;
        }
        if (i == NotificationCenter.messageReceivedByAck || i == NotificationCenter.messageReceivedByServer || i == NotificationCenter.messageSendError) {
            updateVisibleRows(MessagesController.UPDATE_MASK_SEND_STATE);
            return;
        }
        if (i == NotificationCenter.didSetPasscode) {
            checkUi_itemPasscodeVisibility();
            return;
        }
        if (i == NotificationCenter.mainUserInfoChanged) {
            updateDrawerButton();
            return;
        }
        if (i == NotificationCenter.needReloadRecentDialogsSearch) {
            SearchViewPager searchViewPager = this.searchViewPager;
            if (searchViewPager == null || (dialogsSearchAdapter2 = searchViewPager.dialogsSearchAdapter) == null) {
                return;
            }
            dialogsSearchAdapter2.loadRecentSearch();
            return;
        }
        if (i == NotificationCenter.replyMessagesDidLoad) {
            updateVisibleRows(MessagesController.UPDATE_MASK_MESSAGE_TEXT);
            return;
        }
        if (i == NotificationCenter.reloadHints) {
            SearchViewPager searchViewPager2 = this.searchViewPager;
            if (searchViewPager2 == null || (dialogsSearchAdapter = searchViewPager2.dialogsSearchAdapter) == null) {
                return;
            }
            dialogsSearchAdapter.notifyDataSetChanged();
            return;
        }
        if (i == NotificationCenter.didUpdateConnectionState) {
            int connectionState = AccountInstance.getInstance(i2).getConnectionsManager().getConnectionState();
            if (this.currentConnectionState != connectionState) {
                this.currentConnectionState = connectionState;
                updateProxyButton(true, false);
                return;
            }
            return;
        }
        if (i == NotificationCenter.onDownloadingFilesChanged) {
            updateProxyButton(true, false);
            SearchViewPager searchViewPager3 = this.searchViewPager;
            if (searchViewPager3 != null) {
                updateSpeedItem(searchViewPager3.isDownloadsTab(searchViewPager3.getCurrentPosition()));
                return;
            }
            return;
        }
        if (i == NotificationCenter.needDeleteDialog) {
            if (this.fragmentView == null) {
                return;
            }
            final long jLongValue3 = ((Long) objArr[0]).longValue();
            final TLRPC.User user = (TLRPC.User) objArr[1];
            final TLRPC.Chat chat = (TLRPC.Chat) objArr[2];
            if (user != null && user.bot) {
                zBooleanValue2 = ((Boolean) objArr[3]).booleanValue();
                zBooleanValue = false;
            } else {
                zBooleanValue = ((Boolean) objArr[3]).booleanValue();
                zBooleanValue2 = false;
            }
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda56
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didReceivedNotification$120(chat, jLongValue3, zBooleanValue, user, zBooleanValue2);
                }
            };
            createUndoView();
            if (this.undoView[0] != null) {
                if (!ChatObject.isForum(chat)) {
                    UndoView undoView = getUndoView();
                    if (undoView != null) {
                        undoView.showWithAction(jLongValue3, zBooleanValue ? 1 : 95, runnable);
                        return;
                    }
                    return;
                }
                runnable.run();
                return;
            }
            runnable.run();
            return;
        }
        if (i == NotificationCenter.folderBecomeEmpty) {
            int iIntValue = ((Integer) objArr[0]).intValue();
            int i7 = this.folderId;
            if (i7 != iIntValue || i7 == 0) {
                return;
            }
            finishFragment();
            return;
        }
        if (i == NotificationCenter.dialogFiltersUpdated) {
            updateFilterTabs(true, true);
            return;
        }
        if (i == NotificationCenter.filterSettingsUpdated) {
            showFiltersHint();
            return;
        }
        if (i == NotificationCenter.newSuggestionsAvailable) {
            showNextSupportedSuggestion();
            updateDialogsHint();
            checkEmailConfig();
            return;
        }
        if (i == NotificationCenter.forceImportContactsStart) {
            FragmentFloatingButton fragmentFloatingButton2 = this.floatingButton3;
            if (fragmentFloatingButton2 != null) {
                fragmentFloatingButton2.setProgressVisible(true, true);
            }
            ViewPage[] viewPageArr5 = this.viewPages;
            if (viewPageArr5 != null) {
                for (ViewPage viewPage6 : viewPageArr5) {
                    viewPage6.dialogsAdapter.setForceShowEmptyCell(false);
                    viewPage6.dialogsAdapter.setForceUpdatingContacts(true);
                    viewPage6.dialogsAdapter.notifyDataSetChanged();
                }
                return;
            }
            return;
        }
        if (i == NotificationCenter.messagesDeleted) {
            if (!this.searchIsShowed || this.searchViewPager == null) {
                return;
            }
            this.searchViewPager.messagesDeleted(((Long) objArr[1]).longValue(), (ArrayList) objArr[0]);
            return;
        }
        if (i == NotificationCenter.didClearDatabase) {
            ViewPage[] viewPageArr6 = this.viewPages;
            if (viewPageArr6 != null) {
                for (ViewPage viewPage7 : viewPageArr6) {
                    viewPage7.dialogsAdapter.didDatabaseCleared();
                }
            }
            SuggestClearDatabaseBottomSheet.dismissDialog();
            return;
        }
        if (i == NotificationCenter.onDatabaseMigration) {
            boolean zBooleanValue4 = ((Boolean) objArr[0]).booleanValue();
            if (this.fragmentView != null) {
                View view = this.databaseMigrationHint;
                if (zBooleanValue4) {
                    if (view == null) {
                        DatabaseMigrationHint databaseMigrationHint = new DatabaseMigrationHint(this.fragmentView.getContext(), this.currentAccount);
                        this.databaseMigrationHint = databaseMigrationHint;
                        databaseMigrationHint.setAlpha(0.0f);
                        ((ContentView) this.fragmentView).addView(this.databaseMigrationHint);
                        this.databaseMigrationHint.animate().alpha(1.0f).setDuration(300L).setStartDelay(1000L).start();
                    }
                    this.databaseMigrationHint.setTag(1);
                    return;
                }
                if (view == null || view.getTag() == null) {
                    return;
                }
                View view2 = this.databaseMigrationHint;
                view2.animate().setListener(null).cancel();
                view2.animate().setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.40
                    final /* synthetic */ View val$localView;

                    public C555540(View view22) {
                        view = view22;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (view.getParent() != null) {
                            ((ViewGroup) view.getParent()).removeView(view);
                        }
                        DialogsActivity.this.databaseMigrationHint = null;
                    }
                }).alpha(0.0f).setStartDelay(0L).setDuration(150L).start();
                this.databaseMigrationHint.setTag(null);
                return;
            }
            return;
        }
        if (i == NotificationCenter.onDatabaseOpened) {
            checkSuggestClearDatabase();
            return;
        }
        if (i == NotificationCenter.userEmojiStatusUpdated) {
            updateStatus((TLRPC.User) objArr[0], true);
            return;
        }
        if (i == NotificationCenter.currentUserPremiumStatusChanged) {
            ActionBar actionBar = this.actionBar;
            if (actionBar != null) {
                String actionBarTitle = LocaleUtils.getActionBarTitle(this.currentAccount);
                this.actionBarDefaultTitle = actionBarTitle;
                actionBar.setTitle(actionBarTitle, this.statusDrawable);
            }
            updateStatus(UserConfig.getInstance(i2).getCurrentUser(), true);
            updateStoriesPosting();
            return;
        }
        if (i == NotificationCenter.onDatabaseReset) {
            dialogsLoaded[this.currentAccount] = false;
            loadDialogs(getAccountInstance());
            getMessagesController().loadPinnedDialogs(this.folderId, 0L, null);
            return;
        }
        if (i == NotificationCenter.chatlistFolderUpdate) {
            int iIntValue2 = ((Integer) objArr[0]).intValue();
            if (this.viewPages == null) {
                return;
            }
            int i8 = 0;
            while (true) {
                ViewPage[] viewPageArr7 = this.viewPages;
                if (i8 >= viewPageArr7.length) {
                    return;
                }
                ViewPage viewPage8 = viewPageArr7[i8];
                if (viewPage8 != null && ((viewPage8.dialogsType == 7 || viewPage8.dialogsType == 8) && (dialogFilter = getMessagesController().selectedDialogFilter[viewPage8.dialogsType - 7]) != null && iIntValue2 == dialogFilter.f1156id)) {
                    viewPage8.updateList(true);
                    return;
                }
                i8++;
            }
        } else if (i == NotificationCenter.dialogTranslate) {
            long jLongValue4 = ((Long) objArr[0]).longValue();
            if (this.viewPages == null) {
                return;
            }
            int i9 = 0;
            while (true) {
                ViewPage[] viewPageArr8 = this.viewPages;
                if (i9 >= viewPageArr8.length) {
                    return;
                }
                ViewPage viewPage9 = viewPageArr8[i9];
                if (viewPage9.listView != null) {
                    int i10 = 0;
                    while (true) {
                        if (i10 < viewPage9.listView.getChildCount()) {
                            View childAt2 = viewPage9.listView.getChildAt(i10);
                            if (childAt2 instanceof DialogCell) {
                                DialogCell dialogCell = (DialogCell) childAt2;
                                if (jLongValue4 == dialogCell.getDialogId()) {
                                    dialogCell.buildLayout();
                                    break;
                                }
                            }
                            i10++;
                        }
                    }
                }
                i9++;
            }
        } else {
            if (i == NotificationCenter.storiesUpdated) {
                updateStoriesVisibility(this.wasDrawn);
                updateVisibleRows(0);
                return;
            }
            if (i == NotificationCenter.storiesEnabledUpdate) {
                updateStoriesPosting();
                return;
            }
            if (i == NotificationCenter.unconfirmedAuthUpdate) {
                updateDialogsHint();
                return;
            }
            if (i == NotificationCenter.premiumPromoUpdated) {
                updateDialogsHint();
                return;
            }
            if (i == NotificationCenter.starBalanceUpdated || i == NotificationCenter.starSubscriptionsLoaded) {
                updateDialogsHint();
            } else if (i == NotificationCenter.appConfigUpdated) {
                updateDialogsHint();
            } else if (i == NotificationCenter.activeAuctionsUpdated) {
                updateDialogsHint();
            }
        }
    }

    public /* synthetic */ void lambda$didReceivedNotification$119(ViewPage viewPage, Object[] objArr) {
        reloadViewPageDialogs(viewPage, objArr.length > 0);
        FilterTabsView filterTabsView = this.filterTabsView;
        if (filterTabsView == null || filterTabsView.getVisibility() != 0) {
            return;
        }
        this.filterTabsView.checkTabsCounter();
    }

    public /* synthetic */ void lambda$didReceivedNotification$120(TLRPC.Chat chat, long j, boolean z, TLRPC.User user, boolean z2) {
        if (chat != null) {
            if (ChatObject.isNotInChat(chat)) {
                getMessagesController().deleteDialog(j, 0, z);
            } else {
                getMessagesController().deleteParticipantFromChat(-j, getMessagesController().getUser(Long.valueOf(getUserConfig().getClientUserId())), (TLRPC.Chat) null, z, z);
            }
        } else {
            getMessagesController().deleteDialog(j, 0, z);
            if (user != null && user.bot && z2) {
                getMessagesController().blockPeer(user.f1407id);
            }
        }
        getMessagesController().checkIfFolderEmpty(this.folderId);
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$40 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C555540 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$localView;

        public C555540(View view22) {
            view = view22;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            DialogsActivity.this.databaseMigrationHint = null;
        }
    }

    private void checkSuggestClearDatabase() {
        if (getMessagesStorage().showClearDatabaseAlert) {
            getMessagesStorage().showClearDatabaseAlert = false;
            SuggestClearDatabaseBottomSheet.show(this);
        }
    }

    private void showNextSupportedSuggestion() {
        if (this.showingSuggestion != null) {
            return;
        }
        for (String str : getMessagesController().pendingSuggestions) {
            if (showSuggestion(str)) {
                this.showingSuggestion = str;
                return;
            }
        }
    }

    private void onSuggestionDismiss() {
        if (this.showingSuggestion == null) {
            return;
        }
        getMessagesController().removeSuggestion(0L, this.showingSuggestion);
        this.showingSuggestion = null;
        showNextSupportedSuggestion();
    }

    private boolean showSuggestion(String str) {
        if (!"AUTOARCHIVE_POPULAR".equals(str)) {
            return false;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        builder.setTitle(LocaleController.getString(C2797R.string.HideNewChatsAlertTitle));
        builder.setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.HideNewChatsAlertText)));
        builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
        builder.setPositiveButton(LocaleController.getString(C2797R.string.GoToSettings), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda125
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$showSuggestion$121(alertDialog, i);
            }
        });
        showDialog(builder.create(), new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda126
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$showSuggestion$122(dialogInterface);
            }
        });
        return true;
    }

    public /* synthetic */ void lambda$showSuggestion$121(AlertDialog alertDialog, int i) {
        presentFragment(new PrivacySettingsActivity());
        AndroidUtilities.scrollToFragmentRow(this.parentLayout, "newChatsRow");
    }

    public /* synthetic */ void lambda$showSuggestion$122(DialogInterface dialogInterface) {
        onSuggestionDismiss();
    }

    private void showFiltersHint() {
        if (this.askingForPermissions || !getMessagesController().dialogFiltersLoaded || !getMessagesController().showFiltersTooltip || this.filterTabsView == null || !getMessagesController().getDialogFilters().isEmpty() || this.isPaused || !getUserConfig().filtersLoaded || this.inPreviewMode) {
            return;
        }
        SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
        if (globalMainSettings.getBoolean("filterhint", false)) {
            return;
        }
        globalMainSettings.edit().putBoolean("filterhint", true).apply();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda91
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showFiltersHint$124();
            }
        }, 1000L);
    }

    public /* synthetic */ void lambda$showFiltersHint$124() {
        UndoView undoView = getUndoView();
        if (undoView != null) {
            undoView.showWithAction(0L, 15, null, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda139
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showFiltersHint$123();
                }
            });
        }
    }

    public /* synthetic */ void lambda$showFiltersHint$123() {
        presentFragment(new FiltersSetupActivity());
    }

    private void setDialogsListFrozen(boolean z, boolean z2) {
        if (this.viewPages == null || this.dialogsListFrozen == z) {
            return;
        }
        if (z) {
            this.frozenDialogsList = new ArrayList<>(getDialogsArray(this.currentAccount, this.viewPages[0].dialogsType, this.folderId, false));
        } else {
            this.frozenDialogsList = null;
        }
        this.dialogsListFrozen = z;
        this.viewPages[0].dialogsAdapter.setDialogsListFrozen(z);
        if (z || !z2) {
            return;
        }
        boolean zIsComputingLayout = this.viewPages[0].listView.isComputingLayout();
        ViewPage[] viewPageArr = this.viewPages;
        if (zIsComputingLayout) {
            viewPageArr[0].listView.post(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda90
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setDialogsListFrozen$125();
                }
            });
        } else {
            viewPageArr[0].dialogsAdapter.notifyDataSetChanged();
        }
    }

    public /* synthetic */ void lambda$setDialogsListFrozen$125() {
        this.viewPages[0].dialogsAdapter.notifyDataSetChanged();
    }

    public void setDialogsListFrozen(boolean z) {
        setDialogsListFrozen(z, true);
    }

    public class DialogsHeader extends TLRPC.Dialog {
        public int headerType;

        public DialogsHeader(int i) {
            this.headerType = i;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:660:0x0272 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:667:0x0232 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.ArrayList<org.telegram.tgnet.TLRPC.Dialog> getDialogsArray(int r8, int r9, int r10, boolean r11) {
        /*
            Method dump skipped, instruction units count: 887
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.getDialogsArray(int, int, int, boolean):java.util.ArrayList");
    }

    private boolean meetRequestPeerRequirements(TLRPC.User user) {
        TLRPC.TL_requestPeerTypeUser tL_requestPeerTypeUser = (TLRPC.TL_requestPeerTypeUser) this.requestPeerType;
        if (user == null || UserObject.isReplyUser(user) || UserObject.isDeleted(user)) {
            return false;
        }
        Boolean bool = tL_requestPeerTypeUser.bot;
        if (bool != null && bool.booleanValue() != user.bot) {
            return false;
        }
        Boolean bool2 = tL_requestPeerTypeUser.premium;
        return bool2 == null || bool2.booleanValue() == user.premium;
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean meetRequestPeerRequirements(org.telegram.tgnet.TLRPC.User r6, org.telegram.tgnet.TLRPC.Chat r7) {
        /*
            r5 = this;
            r0 = 0
            if (r7 == 0) goto L94
            boolean r1 = org.telegram.messenger.ChatObject.isChannelAndNotMegaGroup(r7)
            org.telegram.tgnet.TLRPC$RequestPeerType r2 = r5.requestPeerType
            boolean r3 = r2 instanceof org.telegram.tgnet.TLRPC.TL_requestPeerTypeBroadcast
            if (r1 != r3) goto L94
            java.lang.Boolean r1 = r2.creator
            if (r1 == 0) goto L1b
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L1b
            boolean r1 = r7.creator
            if (r1 == 0) goto L94
        L1b:
            org.telegram.tgnet.TLRPC$RequestPeerType r1 = r5.requestPeerType
            java.lang.Boolean r1 = r1.bot_participant
            if (r1 == 0) goto L37
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L37
            org.telegram.messenger.MessagesController r1 = r5.getMessagesController()
            boolean r1 = r1.isInChatCached(r7, r6)
            if (r1 != 0) goto L37
            boolean r1 = org.telegram.messenger.ChatObject.canAddBotsToChat(r7)
            if (r1 == 0) goto L94
        L37:
            org.telegram.tgnet.TLRPC$RequestPeerType r1 = r5.requestPeerType
            java.lang.Boolean r1 = r1.has_username
            r2 = 1
            if (r1 == 0) goto L4d
            boolean r1 = r1.booleanValue()
            java.lang.String r3 = org.telegram.messenger.ChatObject.getPublicUsername(r7)
            if (r3 == 0) goto L4a
            r3 = r2
            goto L4b
        L4a:
            r3 = r0
        L4b:
            if (r1 != r3) goto L94
        L4d:
            org.telegram.tgnet.TLRPC$RequestPeerType r1 = r5.requestPeerType
            java.lang.Boolean r1 = r1.forum
            if (r1 == 0) goto L5d
            boolean r1 = r1.booleanValue()
            boolean r3 = org.telegram.messenger.ChatObject.isForum(r7)
            if (r1 != r3) goto L94
        L5d:
            org.telegram.tgnet.TLRPC$RequestPeerType r1 = r5.requestPeerType
            org.telegram.tgnet.TLRPC$TL_chatAdminRights r1 = r1.user_admin_rights
            if (r1 == 0) goto L79
            org.telegram.messenger.MessagesController r1 = r5.getMessagesController()
            org.telegram.messenger.UserConfig r3 = r5.getUserConfig()
            org.telegram.tgnet.TLRPC$User r3 = r3.getCurrentUser()
            org.telegram.tgnet.TLRPC$RequestPeerType r4 = r5.requestPeerType
            org.telegram.tgnet.TLRPC$TL_chatAdminRights r4 = r4.user_admin_rights
            boolean r1 = r1.matchesAdminRights(r7, r3, r4)
            if (r1 == 0) goto L94
        L79:
            org.telegram.tgnet.TLRPC$RequestPeerType r1 = r5.requestPeerType
            org.telegram.tgnet.TLRPC$TL_chatAdminRights r1 = r1.bot_admin_rights
            if (r1 == 0) goto L93
            org.telegram.messenger.MessagesController r1 = r5.getMessagesController()
            org.telegram.tgnet.TLRPC$RequestPeerType r5 = r5.requestPeerType
            org.telegram.tgnet.TLRPC$TL_chatAdminRights r5 = r5.bot_admin_rights
            boolean r5 = r1.matchesAdminRights(r7, r6, r5)
            if (r5 != 0) goto L93
            boolean r5 = org.telegram.messenger.ChatObject.canAddAdmins(r7)
            if (r5 == 0) goto L94
        L93:
            return r2
        L94:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.meetRequestPeerRequirements(org.telegram.tgnet.TLRPC$User, org.telegram.tgnet.TLRPC$Chat):boolean");
    }

    public void hideFloatingButton(boolean z) {
        if (this.rightSlidingDialogContainer.hasFragment()) {
            z = true;
        }
        if (z && this.floatingForceVisible) {
            return;
        }
        this.floatingButtonHidden = z;
        this.mainTabsHiddenByScroll = BottomNavigationBar.floating() && z;
        updateFloatingButtonVisibility(true);
        checkUi_mainTabsVisible();
        if (z) {
            HintView2 hintView2 = this.storyHint;
            if (hintView2 != null) {
                hintView2.hide();
            }
            HintView2 hintView22 = this.storyPremiumHint;
            if (hintView22 != null) {
                hintView22.hide();
            }
        }
    }

    public void animateContactsAlpha(float f) {
        ValueAnimator valueAnimator = this.contactsAlphaAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator duration = ValueAnimator.ofFloat(this.contactsAlpha, f).setDuration(250L);
        this.contactsAlphaAnimator = duration;
        duration.setInterpolator(CubicBezierInterpolator.DEFAULT);
        this.contactsAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda95
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f$0.lambda$animateContactsAlpha$126(valueAnimator2);
            }
        });
        this.contactsAlphaAnimator.start();
    }

    public /* synthetic */ void lambda$animateContactsAlpha$126(ValueAnimator valueAnimator) {
        setContactsAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public void setContactsAlpha(float f) {
        this.contactsAlpha = f;
        for (ViewPage viewPage : this.viewPages) {
            DialogsRecyclerView dialogsRecyclerView = viewPage.listView;
            for (int i = 0; i < dialogsRecyclerView.getChildCount(); i++) {
                View childAt = dialogsRecyclerView.getChildAt(i);
                if (childAt != null && dialogsRecyclerView.getChildAdapterPosition(childAt) >= viewPage.dialogsAdapter.getDialogsCount() + 1) {
                    childAt.setAlpha(f);
                }
            }
        }
    }

    public void setScrollDisabled(boolean z) {
        for (ViewPage viewPage : this.viewPages) {
            ((LinearLayoutManager) viewPage.listView.getLayoutManager()).setScrollDisabled(z);
        }
    }

    private void updateDialogIndices() {
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr == null) {
            return;
        }
        for (ViewPage viewPage : viewPageArr) {
            if (viewPage.getVisibility() == 0 && !viewPage.dialogsAdapter.getDialogsListIsFrozen()) {
                viewPage.updateList(false);
            }
        }
    }

    public void updateVisibleRows(int i) {
        updateVisibleRows(i, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:197:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x013b A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateVisibleRows(int r17, boolean r18) {
        /*
            Method dump skipped, instruction units count: 324
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.updateVisibleRows(int, boolean):void");
    }

    public void setDelegate(DialogsActivityDelegate dialogsActivityDelegate) {
        this.delegate = dialogsActivityDelegate;
    }

    public void setSharedMedia(ArrayList<MediaController.PhotoEntry> arrayList, CharSequence charSequence) {
        if (arrayList == null || arrayList.isEmpty()) {
            this.sharedMediaEntries = null;
            return;
        }
        this.sharedMediaEntries = arrayList;
        this.sharedLink = null;
        if (this.commentView != null) {
            attachShareTopView(charSequence);
        } else {
            this.pendingSharedCaption = charSequence;
        }
    }

    public void setSharedLink(String str, CharSequence charSequence) {
        if (str == null || str.isEmpty()) {
            this.sharedLink = null;
            this.sharedTextSeed = null;
            return;
        }
        this.sharedLink = str;
        this.sharedTextSeed = null;
        this.sharedMediaEntries = null;
        if (this.commentView != null) {
            attachShareTopView(charSequence);
        } else {
            this.pendingSharedCaption = charSequence;
        }
    }

    public void setSharedText(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence.length() == 0) {
            this.sharedTextSeed = null;
            return;
        }
        this.sharedTextSeed = charSequence;
        this.sharedLink = null;
        this.sharedMediaEntries = null;
        if (this.commentView != null) {
            attachShareTopView(charSequence2);
        } else {
            this.pendingSharedCaption = charSequence2;
        }
    }

    public boolean hasSharedMediaEntries() {
        ArrayList<MediaController.PhotoEntry> arrayList = this.sharedMediaEntries;
        return (arrayList == null || arrayList.isEmpty()) ? false : true;
    }

    public ArrayList<MediaController.PhotoEntry> getSharedMediaEntries() {
        return this.sharedMediaEntries;
    }

    public TLRPC.WebPage getSharedWebPage() {
        ShareTopView shareTopView = this.shareTopView;
        if (shareTopView != null) {
            return shareTopView.getLoadedWebPage();
        }
        return null;
    }

    public boolean isWebPagePreviewEnabled() {
        ShareTopView shareTopView = this.shareTopView;
        return shareTopView == null || shareTopView.isPreviewEnabled();
    }

    private void attachShareTopView(CharSequence charSequence) {
        if (this.commentView == null) {
            return;
        }
        if (this.sharedMediaEntries == null && this.sharedLink == null && this.sharedTextSeed == null) {
            return;
        }
        if (this.shareTopView == null) {
            ShareTopView shareTopView = new ShareTopView(getParentActivity(), getResourceProvider());
            this.shareTopView = shareTopView;
            shareTopView.setLayoutClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda42
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$attachShareTopView$127(view);
                }
            });
            this.shareTopView.setOnModeChangeListener(new ShareTopView.OnModeChangeListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda43
                @Override // org.telegram.ui.Components.ShareTopView.OnModeChangeListener
                public final void onModeChanged(int i, int i2) {
                    this.f$0.lambda$attachShareTopView$128(i, i2);
                }
            });
            this.commentView.addTopView(this.shareTopView, 48);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.shareTopView.getLayoutParams();
            layoutParams.rightMargin = -this.commentView.getPaddingRight();
            this.shareTopView.setLayoutParams(layoutParams);
        }
        if (hasSharedMediaEntries()) {
            this.shareTopView.setSharedMedia(this.currentAccount, this.sharedMediaEntries);
        } else {
            String str = this.sharedLink;
            if (str != null) {
                this.shareTopView.setSharedLink(this.currentAccount, str);
            } else {
                CharSequence charSequence2 = this.sharedTextSeed;
                if (charSequence2 != null) {
                    this.shareTopView.setSharedText(this.currentAccount, charSequence2);
                }
            }
        }
        if (!TextUtils.isEmpty(charSequence)) {
            this.commentView.setFieldText(charSequence);
        }
        this.commentView.setOverrideHint(LocaleController.getString(hasSharedMediaEntries() ? C2797R.string.AddCaption : C2797R.string.ShareComment));
        checkUi_forwardCommentFieldVisible();
        if (this.shareTopView.getMode() != 0) {
            this.commentView.showTopView(false, false);
        }
        updateShareTopViewRecipients();
    }

    public /* synthetic */ void lambda$attachShareTopView$127(View view) {
        if (hasSharedMediaEntries()) {
            openSharedMediaEditor();
        }
    }

    public /* synthetic */ void lambda$attachShareTopView$128(int i, int i2) {
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        if (chatActivityEnterView == null) {
            return;
        }
        if (i2 == 0) {
            chatActivityEnterView.hideTopView(true);
        } else {
            chatActivityEnterView.showTopView(true, false);
        }
    }

    private void updateShareTopViewRecipients() {
        ShareTopView shareTopView = this.shareTopView;
        if (shareTopView == null) {
            return;
        }
        shareTopView.setRecipients(this.currentAccount, this.selectedDialogs);
    }

    private void openSharedMediaEditor() {
        ArrayList<MediaController.PhotoEntry> arrayList = this.sharedMediaEntries;
        if (arrayList == null || arrayList.isEmpty() || getParentActivity() == null) {
            return;
        }
        int i = 0;
        MediaController.PhotoEntry photoEntry = this.sharedMediaEntries.get(0);
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        CharSequence fieldText = chatActivityEnterView != null ? chatActivityEnterView.getFieldText() : photoEntry.caption;
        ArrayList<MediaController.PhotoEntry> arrayList2 = this.sharedMediaEntries;
        int size = arrayList2.size();
        while (i < size) {
            MediaController.PhotoEntry photoEntry2 = arrayList2.get(i);
            i++;
            photoEntry2.caption = fieldText;
        }
        PhotoViewer.getInstance().setParentActivity(this, getResourceProvider());
        PhotoViewer.getInstance().hasCaptionForAllMedia = true;
        PhotoViewer.getInstance().captionForAllMedia = fieldText;
        ArrayList<Object> arrayList3 = new ArrayList<>(this.sharedMediaEntries);
        boolean[] zArr = new boolean[this.sharedMediaEntries.size()];
        Arrays.fill(zArr, true);
        PhotoViewer.getInstance().openPhotoForSelect(arrayList3, 0, 0, false, new PhotoViewer.EmptyPhotoViewerProvider() { // from class: org.telegram.ui.DialogsActivity.41
            final /* synthetic */ boolean[] val$checked;

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public int setPhotoChecked(int i2, VideoEditedInfo videoEditedInfo) {
                return i2;
            }

            public C555641(boolean[] zArr2) {
                zArr = zArr2;
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public PhotoViewer.PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, TLRPC.FileLocation fileLocation, int i2, boolean z, boolean z2) {
                BackupImageView thumbView = DialogsActivity.this.shareTopView != null ? DialogsActivity.this.shareTopView.getThumbView(i2) : null;
                if (thumbView == null) {
                    return null;
                }
                int[] iArr = new int[2];
                thumbView.getLocationInWindow(iArr);
                PhotoViewer.PlaceProviderObject placeProviderObject = new PhotoViewer.PlaceProviderObject();
                placeProviderObject.viewX = iArr[0];
                placeProviderObject.viewY = iArr[1];
                placeProviderObject.parentView = DialogsActivity.this.shareTopView;
                ImageReceiver imageReceiver = thumbView.getImageReceiver();
                placeProviderObject.imageReceiver = imageReceiver;
                placeProviderObject.thumb = imageReceiver.getBitmapSafe();
                placeProviderObject.scale = thumbView.getScaleX();
                placeProviderObject.radius = new int[]{AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f)};
                return placeProviderObject;
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public ImageReceiver.BitmapHolder getThumbForPhoto(MessageObject messageObject, TLRPC.FileLocation fileLocation, int i2) {
                BackupImageView thumbView = DialogsActivity.this.shareTopView != null ? DialogsActivity.this.shareTopView.getThumbView(i2) : null;
                if (thumbView != null) {
                    return thumbView.getImageReceiver().getBitmapSafe();
                }
                return null;
            }

            @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public long getDialogId() {
                if (DialogsActivity.this.selectedDialogs.isEmpty()) {
                    return 0L;
                }
                return ((Long) DialogsActivity.this.selectedDialogs.get(0)).longValue();
            }

            @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public boolean canSchedule() {
                if (DialogsActivity.this.selectedDialogs.isEmpty()) {
                    return false;
                }
                ArrayList arrayList4 = DialogsActivity.this.selectedDialogs;
                int size2 = arrayList4.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj = arrayList4.get(i2);
                    i2++;
                    long jLongValue = ((Long) obj).longValue();
                    if (DialogObject.isEncryptedDialog(jLongValue) || DialogsActivity.this.getMessagesController().getSendPaidMessagesStars(jLongValue) > 0) {
                        return false;
                    }
                }
                return true;
            }

            @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public boolean canSetTimer() {
                TLRPC.User user;
                if (DialogsActivity.this.selectedDialogs.isEmpty()) {
                    return false;
                }
                MessagesController messagesController = DialogsActivity.this.getMessagesController();
                ArrayList arrayList4 = DialogsActivity.this.selectedDialogs;
                int size2 = arrayList4.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj = arrayList4.get(i2);
                    i2++;
                    Long l = (Long) obj;
                    if (!DialogObject.isUserDialog(l.longValue()) || (user = messagesController.getUser(l)) == null || user.bot || UserObject.isUserSelf(user)) {
                        return false;
                    }
                }
                return true;
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public CharSequence getTitleFor(int i2) {
                if (DialogsActivity.this.sharedMediaEntries == null || DialogsActivity.this.sharedMediaEntries.isEmpty()) {
                    return null;
                }
                int size2 = DialogsActivity.this.sharedMediaEntries.size();
                DialogsActivity dialogsActivity = DialogsActivity.this;
                if (size2 == 1) {
                    return LocaleController.getString(((MediaController.PhotoEntry) dialogsActivity.sharedMediaEntries.get(0)).isVideo ? C2797R.string.AttachVideo : C2797R.string.AttachPhoto);
                }
                ArrayList arrayList4 = dialogsActivity.sharedMediaEntries;
                int size3 = arrayList4.size();
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                while (i5 < size3) {
                    Object obj = arrayList4.get(i5);
                    i5++;
                    if (((MediaController.PhotoEntry) obj).isVideo) {
                        i3++;
                    } else {
                        i4++;
                    }
                }
                if (i3 == 0) {
                    return LocaleController.formatPluralString("ShareSendPhotos", size2, new Object[0]);
                }
                if (i4 == 0) {
                    return LocaleController.formatPluralString("ShareSendVideos", size2, new Object[0]);
                }
                return LocaleController.formatPluralString("ShareSendItems", size2, new Object[0]);
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public CharSequence getSubtitleFor(int i2) {
                if (i2 < 0 || i2 >= DialogsActivity.this.sharedMediaEntries.size() || !((MediaController.PhotoEntry) DialogsActivity.this.sharedMediaEntries.get(i2)).isVideo) {
                    return DialogsActivity.this.buildRecipientText();
                }
                return null;
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public void sendButtonPressed(int i2, VideoEditedInfo videoEditedInfo, boolean z, int i3, int i4, boolean z2) {
                DialogsActivity.this.syncCaptionFromEntries();
                if (DialogsActivity.this.shareTopView != null) {
                    DialogsActivity.this.shareTopView.setSharedMedia(((BaseFragment) DialogsActivity.this).currentAccount, DialogsActivity.this.sharedMediaEntries);
                }
                if ((!z || i3 != 0) && DialogsActivity.this.delegate != null && !DialogsActivity.this.selectedDialogs.isEmpty()) {
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    dialogsActivity.notify = z;
                    dialogsActivity.scheduleDate = i3;
                    ArrayList<MessagesStorage.TopicKey> arrayList4 = new ArrayList<>();
                    for (int i5 = 0; i5 < DialogsActivity.this.selectedDialogs.size(); i5++) {
                        arrayList4.add(MessagesStorage.TopicKey.m1066of(((Long) DialogsActivity.this.selectedDialogs.get(i5)).longValue(), 0L));
                    }
                    PhotoViewer.getInstance().closePhoto(true, false);
                    DialogsActivityDelegate dialogsActivityDelegate = DialogsActivity.this.delegate;
                    DialogsActivity dialogsActivity2 = DialogsActivity.this;
                    dialogsActivityDelegate.didSelectDialogs(dialogsActivity2, arrayList4, dialogsActivity2.commentView.getFieldText(), false, z, i3, i4, null);
                    return;
                }
                PhotoViewer.getInstance().closePhoto(true, false);
            }

            @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public void onPreClose() {
                CharSequence currentCaptionText = PhotoViewer.getInstance().getCurrentCaptionText();
                if (currentCaptionText != null && DialogsActivity.this.commentView != null) {
                    DialogsActivity.this.commentView.setFieldText(currentCaptionText);
                }
                if (DialogsActivity.this.sharedMediaEntries != null) {
                    ArrayList arrayList4 = DialogsActivity.this.sharedMediaEntries;
                    int size2 = arrayList4.size();
                    int i2 = 0;
                    while (i2 < size2) {
                        Object obj = arrayList4.get(i2);
                        i2++;
                        ((MediaController.PhotoEntry) obj).caption = currentCaptionText;
                    }
                }
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public void onClose() {
                if (DialogsActivity.this.shareTopView != null) {
                    DialogsActivity.this.shareTopView.setSharedMedia(((BaseFragment) DialogsActivity.this).currentAccount, DialogsActivity.this.sharedMediaEntries);
                }
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public void onApplyCaption(CharSequence charSequence) {
                if (DialogsActivity.this.commentView != null) {
                    DialogsActivity.this.commentView.setFieldText(charSequence == null ? _UrlKt.FRAGMENT_ENCODE_SET : charSequence);
                }
                if (DialogsActivity.this.sharedMediaEntries != null) {
                    ArrayList arrayList4 = DialogsActivity.this.sharedMediaEntries;
                    int size2 = arrayList4.size();
                    int i2 = 0;
                    while (i2 < size2) {
                        Object obj = arrayList4.get(i2);
                        i2++;
                        ((MediaController.PhotoEntry) obj).caption = charSequence;
                    }
                }
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public boolean isPhotoChecked(int i2) {
                return zArr[i2];
            }
        }, null);
        PhotoViewer.getInstance().setSelectionDisabled(true);
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$41 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C555641 extends PhotoViewer.EmptyPhotoViewerProvider {
        final /* synthetic */ boolean[] val$checked;

        @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public int setPhotoChecked(int i2, VideoEditedInfo videoEditedInfo) {
            return i2;
        }

        public C555641(boolean[] zArr2) {
            zArr = zArr2;
        }

        @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public PhotoViewer.PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, TLRPC.FileLocation fileLocation, int i2, boolean z, boolean z2) {
            BackupImageView thumbView = DialogsActivity.this.shareTopView != null ? DialogsActivity.this.shareTopView.getThumbView(i2) : null;
            if (thumbView == null) {
                return null;
            }
            int[] iArr = new int[2];
            thumbView.getLocationInWindow(iArr);
            PhotoViewer.PlaceProviderObject placeProviderObject = new PhotoViewer.PlaceProviderObject();
            placeProviderObject.viewX = iArr[0];
            placeProviderObject.viewY = iArr[1];
            placeProviderObject.parentView = DialogsActivity.this.shareTopView;
            ImageReceiver imageReceiver = thumbView.getImageReceiver();
            placeProviderObject.imageReceiver = imageReceiver;
            placeProviderObject.thumb = imageReceiver.getBitmapSafe();
            placeProviderObject.scale = thumbView.getScaleX();
            placeProviderObject.radius = new int[]{AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f)};
            return placeProviderObject;
        }

        @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public ImageReceiver.BitmapHolder getThumbForPhoto(MessageObject messageObject, TLRPC.FileLocation fileLocation, int i2) {
            BackupImageView thumbView = DialogsActivity.this.shareTopView != null ? DialogsActivity.this.shareTopView.getThumbView(i2) : null;
            if (thumbView != null) {
                return thumbView.getImageReceiver().getBitmapSafe();
            }
            return null;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public long getDialogId() {
            if (DialogsActivity.this.selectedDialogs.isEmpty()) {
                return 0L;
            }
            return ((Long) DialogsActivity.this.selectedDialogs.get(0)).longValue();
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean canSchedule() {
            if (DialogsActivity.this.selectedDialogs.isEmpty()) {
                return false;
            }
            ArrayList arrayList4 = DialogsActivity.this.selectedDialogs;
            int size2 = arrayList4.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj = arrayList4.get(i2);
                i2++;
                long jLongValue = ((Long) obj).longValue();
                if (DialogObject.isEncryptedDialog(jLongValue) || DialogsActivity.this.getMessagesController().getSendPaidMessagesStars(jLongValue) > 0) {
                    return false;
                }
            }
            return true;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean canSetTimer() {
            TLRPC.User user;
            if (DialogsActivity.this.selectedDialogs.isEmpty()) {
                return false;
            }
            MessagesController messagesController = DialogsActivity.this.getMessagesController();
            ArrayList arrayList4 = DialogsActivity.this.selectedDialogs;
            int size2 = arrayList4.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj = arrayList4.get(i2);
                i2++;
                Long l = (Long) obj;
                if (!DialogObject.isUserDialog(l.longValue()) || (user = messagesController.getUser(l)) == null || user.bot || UserObject.isUserSelf(user)) {
                    return false;
                }
            }
            return true;
        }

        @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public CharSequence getTitleFor(int i2) {
            if (DialogsActivity.this.sharedMediaEntries == null || DialogsActivity.this.sharedMediaEntries.isEmpty()) {
                return null;
            }
            int size2 = DialogsActivity.this.sharedMediaEntries.size();
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (size2 == 1) {
                return LocaleController.getString(((MediaController.PhotoEntry) dialogsActivity.sharedMediaEntries.get(0)).isVideo ? C2797R.string.AttachVideo : C2797R.string.AttachPhoto);
            }
            ArrayList arrayList4 = dialogsActivity.sharedMediaEntries;
            int size3 = arrayList4.size();
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (i5 < size3) {
                Object obj = arrayList4.get(i5);
                i5++;
                if (((MediaController.PhotoEntry) obj).isVideo) {
                    i3++;
                } else {
                    i4++;
                }
            }
            if (i3 == 0) {
                return LocaleController.formatPluralString("ShareSendPhotos", size2, new Object[0]);
            }
            if (i4 == 0) {
                return LocaleController.formatPluralString("ShareSendVideos", size2, new Object[0]);
            }
            return LocaleController.formatPluralString("ShareSendItems", size2, new Object[0]);
        }

        @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public CharSequence getSubtitleFor(int i2) {
            if (i2 < 0 || i2 >= DialogsActivity.this.sharedMediaEntries.size() || !((MediaController.PhotoEntry) DialogsActivity.this.sharedMediaEntries.get(i2)).isVideo) {
                return DialogsActivity.this.buildRecipientText();
            }
            return null;
        }

        @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void sendButtonPressed(int i2, VideoEditedInfo videoEditedInfo, boolean z, int i3, int i4, boolean z2) {
            DialogsActivity.this.syncCaptionFromEntries();
            if (DialogsActivity.this.shareTopView != null) {
                DialogsActivity.this.shareTopView.setSharedMedia(((BaseFragment) DialogsActivity.this).currentAccount, DialogsActivity.this.sharedMediaEntries);
            }
            if ((!z || i3 != 0) && DialogsActivity.this.delegate != null && !DialogsActivity.this.selectedDialogs.isEmpty()) {
                DialogsActivity dialogsActivity = DialogsActivity.this;
                dialogsActivity.notify = z;
                dialogsActivity.scheduleDate = i3;
                ArrayList<MessagesStorage.TopicKey> arrayList4 = new ArrayList<>();
                for (int i5 = 0; i5 < DialogsActivity.this.selectedDialogs.size(); i5++) {
                    arrayList4.add(MessagesStorage.TopicKey.m1066of(((Long) DialogsActivity.this.selectedDialogs.get(i5)).longValue(), 0L));
                }
                PhotoViewer.getInstance().closePhoto(true, false);
                DialogsActivityDelegate dialogsActivityDelegate = DialogsActivity.this.delegate;
                DialogsActivity dialogsActivity2 = DialogsActivity.this;
                dialogsActivityDelegate.didSelectDialogs(dialogsActivity2, arrayList4, dialogsActivity2.commentView.getFieldText(), false, z, i3, i4, null);
                return;
            }
            PhotoViewer.getInstance().closePhoto(true, false);
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void onPreClose() {
            CharSequence currentCaptionText = PhotoViewer.getInstance().getCurrentCaptionText();
            if (currentCaptionText != null && DialogsActivity.this.commentView != null) {
                DialogsActivity.this.commentView.setFieldText(currentCaptionText);
            }
            if (DialogsActivity.this.sharedMediaEntries != null) {
                ArrayList arrayList4 = DialogsActivity.this.sharedMediaEntries;
                int size2 = arrayList4.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj = arrayList4.get(i2);
                    i2++;
                    ((MediaController.PhotoEntry) obj).caption = currentCaptionText;
                }
            }
        }

        @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void onClose() {
            if (DialogsActivity.this.shareTopView != null) {
                DialogsActivity.this.shareTopView.setSharedMedia(((BaseFragment) DialogsActivity.this).currentAccount, DialogsActivity.this.sharedMediaEntries);
            }
        }

        @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void onApplyCaption(CharSequence charSequence) {
            if (DialogsActivity.this.commentView != null) {
                DialogsActivity.this.commentView.setFieldText(charSequence == null ? _UrlKt.FRAGMENT_ENCODE_SET : charSequence);
            }
            if (DialogsActivity.this.sharedMediaEntries != null) {
                ArrayList arrayList4 = DialogsActivity.this.sharedMediaEntries;
                int size2 = arrayList4.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj = arrayList4.get(i2);
                    i2++;
                    ((MediaController.PhotoEntry) obj).caption = charSequence;
                }
            }
        }

        @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean isPhotoChecked(int i2) {
            return zArr[i2];
        }
    }

    public CharSequence buildRecipientText() {
        if (this.selectedDialogs.isEmpty()) {
            return null;
        }
        int size = this.selectedDialogs.size();
        ArrayList<Long> arrayList = this.selectedDialogs;
        int i = 0;
        if (size < 3) {
            StringBuilder sb = new StringBuilder();
            int size2 = arrayList.size();
            while (i < size2) {
                Long l = arrayList.get(i);
                i++;
                long jLongValue = l.longValue();
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                if (jLongValue == getUserConfig().getClientUserId()) {
                    sb.append(LocaleController.getString(C2797R.string.SavedMessages));
                } else {
                    int size3 = this.selectedDialogs.size();
                    int i2 = this.currentAccount;
                    sb.append(size3 == 1 ? DialogObject.getName(i2, jLongValue) : DialogObject.getShortName(i2, jLongValue));
                }
            }
            return LocaleController.formatString(C2797R.string.ShareSendToChats, sb.toString());
        }
        return LocaleController.formatPluralString("ShareSendToMany", arrayList.size(), new Object[0]);
    }

    public void syncCaptionFromEntries() {
        ArrayList<MediaController.PhotoEntry> arrayList;
        if (this.commentView == null || (arrayList = this.sharedMediaEntries) == null || arrayList.isEmpty()) {
            return;
        }
        MediaController.PhotoEntry photoEntry = this.sharedMediaEntries.get(0);
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        CharSequence charSequence = photoEntry.caption;
        if (charSequence == null) {
            charSequence = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        chatActivityEnterView.setFieldText(charSequence);
    }

    public void setSearchString(String str) {
        this.searchString = str;
    }

    public void setInitialSearchString(String str) {
        this.initialSearchString = str;
    }

    public boolean isMainDialogList() {
        return this.delegate == null && this.searchString == null;
    }

    public boolean isArchive() {
        return this.folderId == 1;
    }

    public void setInitialSearchType(int i) {
        this.initialSearchType = i;
    }

    private boolean checkCanWrite(long j) {
        int i;
        int i2 = this.initialDialogsType;
        if (i2 != 15 && i2 != 16 && this.addToGroupAlertString == null && this.checkCanWrite) {
            if (DialogObject.isChatDialog(j)) {
                long j2 = -j;
                TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(j2));
                if (ChatObject.isChannel(chat) && !chat.megagroup && (this.cantSendToChannels || !ChatObject.isCanWriteToChannel(j2, this.currentAccount) || (i = this.hasPoll) == 2 || i == 3)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
                    builder.setTitle(LocaleController.getString(C2797R.string.SendMessageTitle));
                    int i3 = this.hasPoll;
                    if (i3 == 3) {
                        builder.setMessage(LocaleController.getString(C2797R.string.TodoCantForward));
                    } else if (i3 == 2) {
                        builder.setMessage(LocaleController.getString(C2797R.string.PublicPollCantForward));
                    } else {
                        builder.setMessage(LocaleController.getString(C2797R.string.ChannelCantSendMessage));
                    }
                    builder.setNegativeButton(LocaleController.getString(C2797R.string.f1162OK), null);
                    showDialog(builder.create());
                    return false;
                }
            } else if (DialogObject.isEncryptedDialog(j) && (this.hasPoll != 0 || this.hasInvoice)) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getParentActivity());
                builder2.setTitle(LocaleController.getString(C2797R.string.SendMessageTitle));
                int i4 = this.hasPoll;
                if (i4 == 3) {
                    builder2.setMessage(LocaleController.getString(C2797R.string.TodoCantForwardSecretChat));
                } else if (i4 != 0) {
                    builder2.setMessage(LocaleController.getString(C2797R.string.PollCantForwardSecretChat));
                } else {
                    builder2.setMessage(LocaleController.getString(C2797R.string.InvoiceCantForwardSecretChat));
                }
                builder2.setNegativeButton(LocaleController.getString(C2797R.string.f1162OK), null);
                showDialog(builder2.create());
                return false;
            }
        }
        return true;
    }

    public void didSelectResult(long j, long j2, boolean z, boolean z2) {
        didSelectResult(j, j2, z, z2, null);
    }

    public void didSelectResult(final long j, final long j2, boolean z, final boolean z2, final TopicsFragment topicsFragment) {
        final TLRPC.Chat chat;
        final TLRPC.User user;
        String string;
        String stringSimple;
        String string2;
        String string3;
        String stringSimple2;
        TLRPC.TL_forumTopic tL_forumTopicFindTopic;
        if (checkCanWrite(j)) {
            int i = this.initialDialogsType;
            if (i == 11 || i == 12 || i == 13) {
                if (this.checkingImportDialog) {
                    return;
                }
                if (DialogObject.isUserDialog(j)) {
                    TLRPC.User user2 = getMessagesController().getUser(Long.valueOf(j));
                    if (!user2.mutual_contact) {
                        UndoView undoView = getUndoView();
                        if (undoView != null) {
                            undoView.showWithAction(j, 45, (Runnable) null);
                            return;
                        }
                        return;
                    }
                    user = user2;
                    chat = null;
                } else {
                    TLRPC.Chat chat2 = getMessagesController().getChat(Long.valueOf(-j));
                    if (!ChatObject.hasAdminRights(chat2) || !ChatObject.canChangeChatInfo(chat2)) {
                        UndoView undoView2 = getUndoView();
                        if (undoView2 != null) {
                            undoView2.showWithAction(j, 46, (Runnable) null);
                            return;
                        }
                        return;
                    }
                    chat = chat2;
                    user = null;
                }
                final AlertDialog alertDialog = new AlertDialog(getParentActivity(), 3);
                final TLRPC.TL_messages_checkHistoryImportPeer tL_messages_checkHistoryImportPeer = new TLRPC.TL_messages_checkHistoryImportPeer();
                tL_messages_checkHistoryImportPeer.peer = getMessagesController().getInputPeer(j);
                getConnectionsManager().sendRequest(tL_messages_checkHistoryImportPeer, new RequestDelegate() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda26
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$didSelectResult$131(alertDialog, user, chat, j, z2, tL_messages_checkHistoryImportPeer, tLObject, tL_error);
                    }
                });
                try {
                    alertDialog.showDelayed(300L);
                    return;
                } catch (Exception unused) {
                    return;
                }
            }
            if (!z || ((this.selectAlertString == null || this.selectAlertStringGroup == null) && this.addToGroupAlertString == null)) {
                if (i == 15) {
                    final Runnable runnable = new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda28
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$didSelectResult$133(j, j2, z2, topicsFragment);
                        }
                    };
                    Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda29
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$didSelectResult$135(j, runnable);
                        }
                    };
                    if (j < 0) {
                        showSendToBotAlert(getMessagesController().getChat(Long.valueOf(-j)), runnable2, (Runnable) null);
                        return;
                    } else {
                        showSendToBotAlert(getMessagesController().getUser(Long.valueOf(j)), runnable2, (Runnable) null);
                        return;
                    }
                }
                if (this.delegate != null) {
                    ArrayList<MessagesStorage.TopicKey> arrayList = new ArrayList<>();
                    arrayList.add(MessagesStorage.TopicKey.m1066of(j, j2));
                    if (this.delegate.didSelectDialogs(this, arrayList, null, z2, this.notify, this.scheduleDate, this.scheduleRepeatPeriod, topicsFragment) && this.resetDelegate) {
                        this.delegate = null;
                        return;
                    }
                    return;
                }
                finishFragment();
                return;
            }
            if (getParentActivity() == null) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
            if (DialogObject.isEncryptedDialog(j)) {
                TLRPC.User user3 = getMessagesController().getUser(Long.valueOf(getMessagesController().getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(j))).user_id));
                if (user3 == null) {
                    return;
                }
                string3 = LocaleController.getString(C2797R.string.SendMessageTitle);
                stringSimple2 = LocaleController.formatStringSimple(this.selectAlertString, UserObject.getUserName(user3));
                string2 = LocaleController.getString(C2797R.string.Send);
            } else if (!DialogObject.isUserDialog(j)) {
                TLRPC.Chat chat3 = getMessagesController().getChat(Long.valueOf(-j));
                if (chat3 == null) {
                    return;
                }
                String str = chat3.title;
                if (j2 != 0 && (tL_forumTopicFindTopic = getMessagesController().getTopicsController().findTopic(chat3.f1245id, j2)) != null) {
                    str = ((Object) str) + " " + tL_forumTopicFindTopic.title;
                }
                if (this.addToGroupAlertString != null) {
                    string = LocaleController.getString(C2797R.string.AddToTheGroupAlertTitle);
                    stringSimple = LocaleController.formatStringSimple(this.addToGroupAlertString, str);
                    string2 = LocaleController.getString(C2797R.string.Add);
                } else {
                    string = LocaleController.getString(C2797R.string.SendMessageTitle);
                    stringSimple = LocaleController.formatStringSimple(this.selectAlertStringGroup, str);
                    string2 = LocaleController.getString(C2797R.string.Send);
                }
                String str2 = stringSimple;
                string3 = string;
                stringSimple2 = str2;
            } else if (j == getUserConfig().getClientUserId()) {
                string3 = LocaleController.getString(C2797R.string.SendMessageTitle);
                stringSimple2 = LocaleController.formatStringSimple(this.selectAlertStringGroup, LocaleController.getString(C2797R.string.SavedMessages));
                string2 = LocaleController.getString(C2797R.string.Send);
            } else {
                TLRPC.User user4 = getMessagesController().getUser(Long.valueOf(j));
                if (user4 == null || this.selectAlertString == null) {
                    return;
                }
                string3 = LocaleController.getString(C2797R.string.SendMessageTitle);
                stringSimple2 = LocaleController.formatStringSimple(this.selectAlertString, UserObject.getUserName(user4));
                string2 = LocaleController.getString(C2797R.string.Send);
            }
            builder.setTitle(string3);
            builder.setMessage(AndroidUtilities.replaceTags(stringSimple2));
            builder.setPositiveButton(string2, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda27
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog2, int i2) {
                    this.f$0.lambda$didSelectResult$132(j, j2, topicsFragment, alertDialog2, i2);
                }
            });
            builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
            AlertDialog alertDialogCreate = builder.create();
            if (showDialog(alertDialogCreate) == null) {
                alertDialogCreate.show();
            }
        }
    }

    public /* synthetic */ void lambda$didSelectResult$131(final AlertDialog alertDialog, final TLRPC.User user, final TLRPC.Chat chat, final long j, final boolean z, final TLRPC.TL_messages_checkHistoryImportPeer tL_messages_checkHistoryImportPeer, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda114
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$didSelectResult$130(alertDialog, tLObject, user, chat, j, z, tL_error, tL_messages_checkHistoryImportPeer);
            }
        });
    }

    public /* synthetic */ void lambda$didSelectResult$130(AlertDialog alertDialog, TLObject tLObject, TLRPC.User user, TLRPC.Chat chat, final long j, final boolean z, TLRPC.TL_error tL_error, TLRPC.TL_messages_checkHistoryImportPeer tL_messages_checkHistoryImportPeer) {
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        this.checkingImportDialog = false;
        if (tLObject != null) {
            AlertsCreator.createImportDialogAlert(this, this.arguments.getString("importTitle"), ((TLRPC.TL_messages_checkedHistoryImportPeer) tLObject).confirm_text, user, chat, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda137
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didSelectResult$129(j, z);
                }
            });
        } else {
            AlertsCreator.processError(this.currentAccount, tL_error, this, tL_messages_checkHistoryImportPeer, new Object[0]);
            getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.historyImportProgressChanged, Long.valueOf(j), tL_messages_checkHistoryImportPeer, tL_error);
        }
    }

    public /* synthetic */ void lambda$didSelectResult$129(long j, boolean z) {
        setDialogsListFrozen(true);
        ArrayList<MessagesStorage.TopicKey> arrayList = new ArrayList<>();
        arrayList.add(MessagesStorage.TopicKey.m1066of(j, 0L));
        this.delegate.didSelectDialogs(this, arrayList, null, z, this.notify, this.scheduleDate, this.scheduleRepeatPeriod, null);
    }

    public /* synthetic */ void lambda$didSelectResult$132(long j, long j2, TopicsFragment topicsFragment, AlertDialog alertDialog, int i) {
        didSelectResult(j, j2, false, false, topicsFragment);
    }

    public /* synthetic */ void lambda$didSelectResult$133(long j, long j2, boolean z, TopicsFragment topicsFragment) {
        if (this.delegate != null) {
            ArrayList<MessagesStorage.TopicKey> arrayList = new ArrayList<>();
            arrayList.add(MessagesStorage.TopicKey.m1066of(j, j2));
            this.delegate.didSelectDialogs(this, arrayList, null, z, this.notify, this.scheduleDate, this.scheduleRepeatPeriod, topicsFragment);
            if (this.resetDelegate) {
                this.delegate = null;
                return;
            }
            return;
        }
        finishFragment();
    }

    public /* synthetic */ void lambda$didSelectResult$135(long j, final Runnable runnable) {
        if (this.requestPeerType.bot_admin_rights != null) {
            getMessagesController().setUserAdminRole(-j, getMessagesController().getUser(Long.valueOf(this.requestPeerBotId)), this.requestPeerType.bot_admin_rights, null, false, this, true, true, null, runnable, new MessagesController.ErrorDelegate() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda113
                @Override // org.telegram.messenger.MessagesController.ErrorDelegate
                public final boolean run(TLRPC.TL_error tL_error) {
                    return DialogsActivity.$r8$lambda$PusN9HEGLeA_SoPtxTX2QaI0_dY(runnable, tL_error);
                }
            });
        } else {
            runnable.run();
        }
    }

    public static /* synthetic */ boolean $r8$lambda$PusN9HEGLeA_SoPtxTX2QaI0_dY(Runnable runnable, TLRPC.TL_error tL_error) {
        runnable.run();
        return true;
    }

    private void showSendToBotAlert(TLRPC.User user, final Runnable runnable, final Runnable runnable2) {
        TLRPC.User user2 = getMessagesController().getUser(Long.valueOf(this.requestPeerBotId));
        showDialog(new AlertDialog.Builder(getContext()).setTitle(LocaleController.formatString(C2797R.string.AreYouSureSendChatToBotTitle, UserObject.getFirstName(user), UserObject.getFirstName(user2))).setMessage(TextUtils.concat(AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.AreYouSureSendChatToBotMessage, UserObject.getFirstName(user), UserObject.getFirstName(user2))))).setPositiveButton(LocaleController.formatString("Send", C2797R.string.Send, new Object[0]), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda115
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                runnable.run();
            }
        }).setNegativeButton(LocaleController.formatString("Cancel", C2797R.string.Cancel, new Object[0]), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda116
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                DialogsActivity.$r8$lambda$3SWoYIguGYGY13hOUR_zBFjASdQ(runnable2, alertDialog, i);
            }
        }).create());
    }

    public static /* synthetic */ void $r8$lambda$3SWoYIguGYGY13hOUR_zBFjASdQ(Runnable runnable, AlertDialog alertDialog, int i) {
        if (runnable != null) {
            runnable.run();
        }
    }

    public void showSendToBotAlert(TLRPC.Chat chat, final Runnable runnable, final Runnable runnable2) {
        CharSequence charSequenceConcat;
        String string;
        TLRPC.User user = getMessagesController().getUser(Long.valueOf(this.requestPeerBotId));
        boolean zIsChannelAndNotMegaGroup = ChatObject.isChannelAndNotMegaGroup(chat);
        AlertDialog.Builder title = new AlertDialog.Builder(getContext()).setTitle(LocaleController.formatString(C2797R.string.AreYouSureSendChatToBotTitle, chat.title, UserObject.getFirstName(user)));
        SpannableStringBuilder spannableStringBuilderReplaceTags = AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.AreYouSureSendChatToBotMessage, chat.title, UserObject.getFirstName(user)));
        Boolean bool = this.requestPeerType.bot_participant;
        if ((bool != null && bool.booleanValue() && !getMessagesController().isInChatCached(chat, user)) || this.requestPeerType.bot_admin_rights != null) {
            if (this.requestPeerType.bot_admin_rights == null) {
                string = LocaleController.formatString(C2797R.string.AreYouSureSendChatToBotAdd, UserObject.getFirstName(user), chat.title);
            } else {
                string = LocaleController.formatString(C2797R.string.AreYouSureSendChatToBotAddRights, UserObject.getFirstName(user), chat.title, RequestPeerRequirementsCell.rightsToString(this.requestPeerType.bot_admin_rights, zIsChannelAndNotMegaGroup));
            }
            charSequenceConcat = TextUtils.concat("\n\n", AndroidUtilities.replaceTags(string));
        } else {
            charSequenceConcat = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        showDialog(title.setMessage(TextUtils.concat(spannableStringBuilderReplaceTags, charSequenceConcat)).setPositiveButton(LocaleController.formatString("Send", C2797R.string.Send, new Object[0]), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda109
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                runnable.run();
            }
        }).setNegativeButton(LocaleController.formatString("Cancel", C2797R.string.Cancel, new Object[0]), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda110
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                DialogsActivity.$r8$lambda$LWa1PsJuKbHkwTAxiH9zlIJ5_yo(runnable2, alertDialog, i);
            }
        }).create());
    }

    public static /* synthetic */ void $r8$lambda$LWa1PsJuKbHkwTAxiH9zlIJ5_yo(Runnable runnable, AlertDialog alertDialog, int i) {
        if (runnable != null) {
            runnable.run();
        }
    }

    public boolean onSendLongClick(View view) {
        final DialogsActivity dialogsActivity;
        Activity parentActivity = getParentActivity();
        Theme.ResourcesProvider resourceProvider = getResourceProvider();
        if (parentActivity == null) {
            return false;
        }
        final boolean z = !this.selectedDialogs.isEmpty();
        boolean z2 = true;
        for (int i = 0; i < this.selectedDialogs.size(); i++) {
            long jLongValue = this.selectedDialogs.get(i).longValue();
            if (z && jLongValue != getUserConfig().getClientUserId()) {
                z = false;
            }
            if (DialogObject.isEncryptedDialog(jLongValue)) {
                z2 = false;
            }
            TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(-jLongValue));
            if (chat != null && !ChatObject.canWriteToChat(chat)) {
                z2 = false;
            }
        }
        ItemOptions itemOptionsMakeOptions = ItemOptions.makeOptions(this, view);
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        CharSequence fieldText = chatActivityEnterView != null ? chatActivityEnterView.getFieldText() : null;
        if (TextUtils.isEmpty(fieldText)) {
            dialogsActivity = this;
        } else {
            dialogsActivity = this;
            itemOptionsMakeOptions.add(new TranslateBeforeSendWrapper(getContext(), true, false, resourceProvider) { // from class: org.telegram.ui.DialogsActivity.42
                final /* synthetic */ CharSequence val$commentText;
                final /* synthetic */ ItemOptions val$options;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C555742(Context context, boolean z3, boolean z4, Theme.ResourcesProvider resourceProvider2, ItemOptions itemOptionsMakeOptions2, CharSequence fieldText2) {
                    super(context, z3, z4, resourceProvider2);
                    itemOptions = itemOptionsMakeOptions2;
                    charSequence = fieldText2;
                }

                @Override // com.exteragram.messenger.components.TranslateBeforeSendWrapper
                public void onClick() {
                    itemOptions.dismiss();
                    AlertDialog alertDialog = new AlertDialog(DialogsActivity.this.getParentActivity(), 3, this.resourcesProvider);
                    alertDialog.showDelayed(150L);
                    CharSequence fieldText2 = DialogsActivity.this.commentView != null ? DialogsActivity.this.commentView.getFieldText() : charSequence;
                    TranslatorUtils.translate(fieldText2, TranslatorUtils.getResolvedSendTargetLanguageCode(), MediaDataController.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getEntities(new CharSequence[]{fieldText2}, true), new TranslatorUtils.TranslateCallback() { // from class: org.telegram.ui.DialogsActivity.42.1
                        final /* synthetic */ AlertDialog val$progressDialog;

                        public AnonymousClass1(AlertDialog alertDialog2) {
                            alertDialog = alertDialog2;
                        }

                        @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                        public void onSuccess(TLRPC.TL_textWithEntities tL_textWithEntities) {
                            try {
                                alertDialog.dismiss();
                            } catch (Exception unused) {
                            }
                            SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(tL_textWithEntities.text);
                            MessageObject.addEntitiesToText(spannableStringBuilderValueOf, tL_textWithEntities.entities, true, true, false, true);
                            DialogsActivity.this.commentView.setFieldText(spannableStringBuilderValueOf);
                            DialogsActivity.this.commentView.setSelection(spannableStringBuilderValueOf.length());
                        }

                        @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                        public void onFailed() {
                            try {
                                alertDialog.dismiss();
                            } catch (Exception unused) {
                            }
                            BulletinFactory.m1143of(DialogsActivity.this).createErrorBulletin(LocaleController.getString(C2797R.string.TranslationFailedAlert2)).show();
                        }
                    });
                }

                /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$42$1 */
                public class AnonymousClass1 implements TranslatorUtils.TranslateCallback {
                    final /* synthetic */ AlertDialog val$progressDialog;

                    public AnonymousClass1(AlertDialog alertDialog2) {
                        alertDialog = alertDialog2;
                    }

                    @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                    public void onSuccess(TLRPC.TL_textWithEntities tL_textWithEntities) {
                        try {
                            alertDialog.dismiss();
                        } catch (Exception unused) {
                        }
                        SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(tL_textWithEntities.text);
                        MessageObject.addEntitiesToText(spannableStringBuilderValueOf, tL_textWithEntities.entities, true, true, false, true);
                        DialogsActivity.this.commentView.setFieldText(spannableStringBuilderValueOf);
                        DialogsActivity.this.commentView.setSelection(spannableStringBuilderValueOf.length());
                    }

                    @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                    public void onFailed() {
                        try {
                            alertDialog.dismiss();
                        } catch (Exception unused) {
                        }
                        BulletinFactory.m1143of(DialogsActivity.this).createErrorBulletin(LocaleController.getString(C2797R.string.TranslationFailedAlert2)).show();
                    }
                }
            });
        }
        itemOptionsMakeOptions2.add(C2797R.drawable.input_notify_off, LocaleController.getString(C2797R.string.SendWithoutSound), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda123
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onSendLongClick$140();
            }
        }).addIf(z2, C2797R.drawable.msg_calendar2, LocaleController.getString(C2797R.string.ScheduleMessage), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda124
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onSendLongClick$141(z);
            }
        }).setDismissOnMoveOutside(true);
        try {
            view.performHapticFeedback(3, 2);
        } catch (Exception unused) {
        }
        itemOptionsMakeOptions2.show();
        SharedConfig.removeScheduledOrNoSoundHint();
        return true;
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$42 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C555742 extends TranslateBeforeSendWrapper {
        final /* synthetic */ CharSequence val$commentText;
        final /* synthetic */ ItemOptions val$options;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C555742(Context context, boolean z3, boolean z4, Theme.ResourcesProvider resourceProvider2, ItemOptions itemOptionsMakeOptions2, CharSequence fieldText2) {
            super(context, z3, z4, resourceProvider2);
            itemOptions = itemOptionsMakeOptions2;
            charSequence = fieldText2;
        }

        @Override // com.exteragram.messenger.components.TranslateBeforeSendWrapper
        public void onClick() {
            itemOptions.dismiss();
            AlertDialog alertDialog2 = new AlertDialog(DialogsActivity.this.getParentActivity(), 3, this.resourcesProvider);
            alertDialog2.showDelayed(150L);
            CharSequence fieldText2 = DialogsActivity.this.commentView != null ? DialogsActivity.this.commentView.getFieldText() : charSequence;
            TranslatorUtils.translate(fieldText2, TranslatorUtils.getResolvedSendTargetLanguageCode(), MediaDataController.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getEntities(new CharSequence[]{fieldText2}, true), new TranslatorUtils.TranslateCallback() { // from class: org.telegram.ui.DialogsActivity.42.1
                final /* synthetic */ AlertDialog val$progressDialog;

                public AnonymousClass1(AlertDialog alertDialog22) {
                    alertDialog = alertDialog22;
                }

                @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                public void onSuccess(TLRPC.TL_textWithEntities tL_textWithEntities) {
                    try {
                        alertDialog.dismiss();
                    } catch (Exception unused) {
                    }
                    SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(tL_textWithEntities.text);
                    MessageObject.addEntitiesToText(spannableStringBuilderValueOf, tL_textWithEntities.entities, true, true, false, true);
                    DialogsActivity.this.commentView.setFieldText(spannableStringBuilderValueOf);
                    DialogsActivity.this.commentView.setSelection(spannableStringBuilderValueOf.length());
                }

                @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                public void onFailed() {
                    try {
                        alertDialog.dismiss();
                    } catch (Exception unused) {
                    }
                    BulletinFactory.m1143of(DialogsActivity.this).createErrorBulletin(LocaleController.getString(C2797R.string.TranslationFailedAlert2)).show();
                }
            });
        }

        /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$42$1 */
        public class AnonymousClass1 implements TranslatorUtils.TranslateCallback {
            final /* synthetic */ AlertDialog val$progressDialog;

            public AnonymousClass1(AlertDialog alertDialog22) {
                alertDialog = alertDialog22;
            }

            @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
            public void onSuccess(TLRPC.TL_textWithEntities tL_textWithEntities) {
                try {
                    alertDialog.dismiss();
                } catch (Exception unused) {
                }
                SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(tL_textWithEntities.text);
                MessageObject.addEntitiesToText(spannableStringBuilderValueOf, tL_textWithEntities.entities, true, true, false, true);
                DialogsActivity.this.commentView.setFieldText(spannableStringBuilderValueOf);
                DialogsActivity.this.commentView.setSelection(spannableStringBuilderValueOf.length());
            }

            @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
            public void onFailed() {
                try {
                    alertDialog.dismiss();
                } catch (Exception unused) {
                }
                BulletinFactory.m1143of(DialogsActivity.this).createErrorBulletin(LocaleController.getString(C2797R.string.TranslationFailedAlert2)).show();
            }
        }
    }

    public /* synthetic */ void lambda$onSendLongClick$140() {
        this.notify = false;
        if (this.delegate == null || this.selectedDialogs.isEmpty()) {
            return;
        }
        ArrayList<MessagesStorage.TopicKey> arrayList = new ArrayList<>();
        for (int i = 0; i < this.selectedDialogs.size(); i++) {
            arrayList.add(MessagesStorage.TopicKey.m1066of(this.selectedDialogs.get(i).longValue(), 0L));
        }
        this.delegate.didSelectDialogs(this, arrayList, this.commentView.getFieldText(), false, this.notify, this.scheduleDate, this.scheduleRepeatPeriod, null);
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$43 */
    public class C555843 implements AlertsCreator.ScheduleDatePickerDelegate {
        public C555843() {
        }

        @Override // org.telegram.ui.Components.AlertsCreator.ScheduleDatePickerDelegate
        public void didSelectDate(boolean z, int i, int i2) {
            DialogsActivity dialogsActivity = DialogsActivity.this;
            dialogsActivity.scheduleDate = i;
            dialogsActivity.scheduleRepeatPeriod = i2;
            if (dialogsActivity.delegate == null || DialogsActivity.this.selectedDialogs.isEmpty()) {
                return;
            }
            ArrayList<MessagesStorage.TopicKey> arrayList = new ArrayList<>();
            int i3 = 0;
            while (true) {
                int size = DialogsActivity.this.selectedDialogs.size();
                DialogsActivity dialogsActivity2 = DialogsActivity.this;
                if (i3 < size) {
                    arrayList.add(MessagesStorage.TopicKey.m1066of(((Long) dialogsActivity2.selectedDialogs.get(i3)).longValue(), 0L));
                    i3++;
                } else {
                    DialogsActivityDelegate dialogsActivityDelegate = dialogsActivity2.delegate;
                    DialogsActivity dialogsActivity3 = DialogsActivity.this;
                    dialogsActivityDelegate.didSelectDialogs(dialogsActivity3, arrayList, dialogsActivity3.commentView.getFieldText(), false, z, i, i2, null);
                    return;
                }
            }
        }
    }

    public /* synthetic */ void lambda$onSendLongClick$141(boolean z) {
        AlertsCreator.createScheduleDatePickerDialog(getParentActivity(), z ? getUserConfig().getClientUserId() : -1L, new AlertsCreator.ScheduleDatePickerDelegate() { // from class: org.telegram.ui.DialogsActivity.43
            public C555843() {
            }

            @Override // org.telegram.ui.Components.AlertsCreator.ScheduleDatePickerDelegate
            public void didSelectDate(boolean z2, int i, int i2) {
                DialogsActivity dialogsActivity = DialogsActivity.this;
                dialogsActivity.scheduleDate = i;
                dialogsActivity.scheduleRepeatPeriod = i2;
                if (dialogsActivity.delegate == null || DialogsActivity.this.selectedDialogs.isEmpty()) {
                    return;
                }
                ArrayList<MessagesStorage.TopicKey> arrayList = new ArrayList<>();
                int i3 = 0;
                while (true) {
                    int size = DialogsActivity.this.selectedDialogs.size();
                    DialogsActivity dialogsActivity2 = DialogsActivity.this;
                    if (i3 < size) {
                        arrayList.add(MessagesStorage.TopicKey.m1066of(((Long) dialogsActivity2.selectedDialogs.get(i3)).longValue(), 0L));
                        i3++;
                    } else {
                        DialogsActivityDelegate dialogsActivityDelegate = dialogsActivity2.delegate;
                        DialogsActivity dialogsActivity3 = DialogsActivity.this;
                        dialogsActivityDelegate.didSelectDialogs(dialogsActivity3, arrayList, dialogsActivity3.commentView.getFieldText(), false, z2, i, i2, null);
                        return;
                    }
                }
            }
        }, getResourceProvider());
    }

    private float getRightSlidingProgress() {
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if (rightSlidingDialogContainer == null || !rightSlidingDialogContainer.hasFragment()) {
            return 0.0f;
        }
        return this.rightSlidingDialogContainer.openedProgress;
    }

    /* JADX WARN: Removed duplicated region for block: B:190:0x0395  */
    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.ArrayList<org.telegram.p035ui.ActionBar.ThemeDescription> getThemeDescriptions() {
        /*
            Method dump skipped, instruction units count: 5560
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.getThemeDescriptions():java.util.ArrayList");
    }

    /* JADX WARN: Removed duplicated region for block: B:155:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x004b A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$getThemeDescriptions$142() {
        /*
            Method dump skipped, instruction units count: 440
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.lambda$getThemeDescriptions$142():void");
    }

    public /* synthetic */ void lambda$getThemeDescriptions$143() {
        SearchViewPager searchViewPager = this.searchViewPager;
        if (searchViewPager != null) {
            ActionBarMenu actionMode = searchViewPager.getActionMode();
            if (actionMode != null) {
                actionMode.setBackgroundColor(getThemedColor(Theme.key_actionBarActionModeDefault));
            }
            ActionBarMenuItem speedItem = this.searchViewPager.getSpeedItem();
            if (speedItem != null) {
                speedItem.getIconView().setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_actionBarActionModeDefaultIcon), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public Animator getCustomSlideTransition(boolean z, boolean z2, float f) {
        if (z2) {
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.slideFragmentProgress, 1.0f);
            this.slideBackTransitionAnimator = valueAnimatorOfFloat;
            return valueAnimatorOfFloat;
        }
        int iClamp = (getLayoutContainer() == null || getLayoutContainer().getMeasuredWidth() <= 0) ? 150 : (int) Utilities.clamp((200.0f / getLayoutContainer().getMeasuredWidth()) * f, 200.0f, 80.0f);
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(this.slideFragmentProgress, 1.0f);
        this.slideBackTransitionAnimator = valueAnimatorOfFloat2;
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda38
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$getCustomSlideTransition$144(valueAnimator);
            }
        });
        this.slideBackTransitionAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT);
        this.slideBackTransitionAnimator.setDuration(iClamp);
        this.slideBackTransitionAnimator.start();
        return this.slideBackTransitionAnimator;
    }

    public /* synthetic */ void lambda$getCustomSlideTransition$144(ValueAnimator valueAnimator) {
        setSlideTransitionProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void prepareFragmentToSlide(boolean z, boolean z2) {
        if (!z && z2) {
            this.isSlideBackTransition = true;
            setFragmentIsSliding(true);
        } else {
            this.slideBackTransitionAnimator = null;
            this.isSlideBackTransition = false;
            setFragmentIsSliding(false);
            setSlideTransitionProgress(1.0f);
        }
    }

    private void setFragmentIsSliding(boolean z) {
        ViewPage viewPage;
        if (SharedConfig.getDevicePerformanceClass() == 0 || !LiteMode.isEnabled(32768)) {
            return;
        }
        ViewPage[] viewPageArr = this.viewPages;
        if (z) {
            if (viewPageArr != null && (viewPage = viewPageArr[0]) != null) {
                viewPage.setLayerType(2, null);
                this.viewPages[0].setClipChildren(false);
                this.viewPages[0].setClipToPadding(false);
                this.viewPages[0].listView.setClipChildren(false);
            }
            ActionBar actionBar = this.actionBar;
            if (actionBar != null) {
                actionBar.setLayerType(2, null);
            }
            View view = this.fragmentView;
            if (view != null) {
                ((ViewGroup) view).setClipChildren(false);
                this.fragmentView.requestLayout();
                return;
            }
            return;
        }
        if (viewPageArr != null) {
            for (ViewPage viewPage2 : viewPageArr) {
                if (viewPage2 != null) {
                    viewPage2.setLayerType(0, null);
                    viewPage2.setClipChildren(true);
                    viewPage2.setClipToPadding(true);
                    viewPage2.listView.setClipChildren(true);
                }
            }
        }
        ActionBar actionBar2 = this.actionBar;
        if (actionBar2 != null) {
            actionBar2.setLayerType(0, null);
        }
        DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
        if (dialogStoriesCell != null) {
            dialogStoriesCell.setLayerType(0, null);
        }
        View view2 = this.fragmentView;
        if (view2 != null) {
            ((ViewGroup) view2).setClipChildren(true);
            this.fragmentView.requestLayout();
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onSlideProgress(boolean z, float f) {
        if ((SharedConfig.getDevicePerformanceClass() > 0 || BuildVars.DEBUG_PRIVATE_VERSION) && this.isSlideBackTransition && this.slideBackTransitionAnimator == null) {
            setSlideTransitionProgress(f);
        }
    }

    private void setSlideTransitionProgress(float f) {
        if ((SharedConfig.getDevicePerformanceClass() > 0 || BuildVars.DEBUG_PRIVATE_VERSION) && this.slideFragmentProgress != f) {
            this.slideFragmentLite = !ExteraConfig.getSpringAnimations() && (SharedConfig.getDevicePerformanceClass() == 0 || !LiteMode.isEnabled(32768));
            this.slideFragmentProgress = f;
            View view = this.fragmentView;
            if (view != null) {
                view.invalidate();
            }
            if (this.slideFragmentLite) {
                float f2 = (-AndroidUtilities.m1036dp(40.0f)) * (1.0f - this.slideFragmentProgress);
                DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
                if (dialogStoriesCell != null) {
                    dialogStoriesCell.setTranslationX(f2);
                }
                FragmentSearchField fragmentSearchField = this.fragmentSearchField;
                if (fragmentSearchField != null) {
                    fragmentSearchField.setTranslationX(f2);
                }
                RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
                if (rightSlidingDialogContainer == null || rightSlidingDialogContainer.getFragmentView() == null || this.rightFragmentTransitionInProgress) {
                    return;
                }
                this.rightSlidingDialogContainer.getFragmentView().setTranslationX(f2);
                return;
            }
            float f3 = -AndroidUtilities.m1036dp(4.0f);
            float f4 = this.slideFragmentProgress;
            float f5 = f3 * (1.0f - f4);
            float f6 = 1.0f - ((1.0f - f4) * 0.05f);
            DialogStoriesCell dialogStoriesCell2 = this.dialogStoriesCell;
            if (dialogStoriesCell2 != null) {
                dialogStoriesCell2.setScaleX(f6);
                this.dialogStoriesCell.setScaleY(f6);
                this.dialogStoriesCell.setTranslationX(f5);
                this.dialogStoriesCell.setPivotX(0.0f);
                this.dialogStoriesCell.setPivotY(0.0f);
            }
            FragmentSearchField fragmentSearchField2 = this.fragmentSearchField;
            if (fragmentSearchField2 != null) {
                fragmentSearchField2.setTranslationX(f5);
                this.fragmentSearchField.setScaleX(f6);
                this.fragmentSearchField.setScaleY(f6);
            }
            RightSlidingDialogContainer rightSlidingDialogContainer2 = this.rightSlidingDialogContainer;
            if (rightSlidingDialogContainer2 == null || rightSlidingDialogContainer2.getFragmentView() == null) {
                return;
            }
            if (!this.rightFragmentTransitionInProgress) {
                this.rightSlidingDialogContainer.getFragmentView().setScaleX(f6);
                this.rightSlidingDialogContainer.getFragmentView().setScaleY(f6);
                this.rightSlidingDialogContainer.getFragmentView().setTranslationX(f5);
            }
            this.rightSlidingDialogContainer.getFragmentView().setPivotX(0.0f);
            this.rightSlidingDialogContainer.getFragmentView().setPivotY(0.0f);
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public INavigationLayout.BackButtonState getBackButtonState() {
        return (isArchive() || this.rightSlidingDialogContainer.isOpenned) ? INavigationLayout.BackButtonState.BACK : INavigationLayout.BackButtonState.MENU;
    }

    public void setShowSearch(String str, int i) {
        int positionForType;
        if (!this.searching) {
            this.initialSearchType = i;
            this.fragmentSearchField.editText.setText(str);
            this.fragmentSearchField.editText.setSelection(str.length());
            return;
        }
        this.fragmentSearchField.editText.setText(str);
        this.fragmentSearchField.editText.setSelection(str.length());
        SearchViewPager searchViewPager = this.searchViewPager;
        if (searchViewPager == null || (positionForType = searchViewPager.getPositionForType(i)) < 0 || this.searchViewPager.getTabsView().getCurrentTabId() == positionForType) {
            return;
        }
        this.searchViewPager.getTabsView().scrollToTab(positionForType, positionForType);
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public boolean isLightStatusBar() {
        RightSlidingDialogContainer rightSlidingDialogContainer;
        if (this.searching || (rightSlidingDialogContainer = this.rightSlidingDialogContainer) == null || rightSlidingDialogContainer.getFragment() == null) {
            return ColorUtils.calculateLuminance(getThemedColor(Theme.key_windowBackgroundWhite)) > 0.699999988079071d;
        }
        return this.rightSlidingDialogContainer.getFragment().isLightStatusBar();
    }

    @Override // org.telegram.p035ui.Components.FloatingDebug.FloatingDebugProvider
    public List<FloatingDebugController.DebugItem> onGetDebugItems() {
        return Arrays.asList(new FloatingDebugController.DebugItem(LocaleController.getString(C2797R.string.DebugDialogsActivity)), new FloatingDebugController.DebugItem(LocaleController.getString(C2797R.string.ClearLocalDatabase), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda140
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onGetDebugItems$145();
            }
        }), new FloatingDebugController.DebugItem(LocaleController.getString(C2797R.string.DebugClearSendMessageAsPeers), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda141
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onGetDebugItems$146();
            }
        }));
    }

    public /* synthetic */ void lambda$onGetDebugItems$145() {
        getMessagesStorage().clearLocalDatabase();
        Toast.makeText(getContext(), LocaleController.getString(C2797R.string.DebugClearLocalDatabaseSuccess), 0).show();
    }

    public /* synthetic */ void lambda$onGetDebugItems$146() {
        getMessagesController().clearSendAsPeers();
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public boolean closeLastFragment() {
        if (this.rightSlidingDialogContainer.hasFragment()) {
            this.rightSlidingDialogContainer.lambda$presentFragment$1();
            SearchViewPager searchViewPager = this.searchViewPager;
            if (searchViewPager == null) {
                return true;
            }
            searchViewPager.updateTabs();
            return true;
        }
        return super.closeLastFragment();
    }

    public boolean getAllowGlobalSearch() {
        return this.allowGlobalSearch;
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public boolean canBeginSlide() {
        FilterTabsView filterTabsView;
        if (this.rightSlidingDialogContainer.hasFragment()) {
            return false;
        }
        if (this.initialDialogsType == 3 && (filterTabsView = this.filterTabsView) != null && filterTabsView.getVisibility() == 0) {
            return this.filterTabsView.isFirstTab();
        }
        return true;
    }

    public int getSlideAmplitude() {
        return AndroidUtilities.m1036dp(this.slideFragmentLite ? 40.0f : 20.0f) * (!this.slideFragmentLite ? -1 : 1);
    }

    public void updateStoriesVisibility(boolean z) {
        ActionBar actionBar;
        boolean z2;
        if (this.dialogStoriesCell == null || this.storiesVisibilityAnimator != null) {
            return;
        }
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if ((rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment()) || this.searchIsShowed || (actionBar = this.actionBar) == null || actionBar.isActionModeShowed() || this.onlySelect) {
            return;
        }
        int i = 0;
        if (StoryRecorder.isVisible() || (getLastStoryViewer() != null && getLastStoryViewer().isFullyVisible())) {
            z = false;
        }
        boolean zHasOnlySelfStories = !isArchive() && getStoriesController().hasOnlySelfStories();
        if (isArchive()) {
            z2 = !getStoriesController().getHiddenList().isEmpty();
        } else {
            z2 = !zHasOnlySelfStories && getStoriesController().hasStories();
            zHasOnlySelfStories = getStoriesController().hasOnlySelfStories();
        }
        this.hasOnlySlefStories = zHasOnlySelfStories;
        boolean z3 = this.dialogStoriesCellVisible;
        boolean z4 = zHasOnlySelfStories || z2;
        this.dialogStoriesCellVisible = z4;
        if (z2 || z4) {
            this.dialogStoriesCell.updateItems(z, z4 != z3);
        }
        boolean z5 = this.dialogStoriesCellVisible;
        int i2 = 8;
        if (z5 != z3) {
            if (z) {
                ValueAnimator valueAnimator = this.storiesVisibilityAnimator2;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                if (this.dialogStoriesCellVisible && !isInPreviewMode()) {
                    this.dialogStoriesCell.setVisibility(0);
                }
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.progressToDialogStoriesCell, this.dialogStoriesCellVisible ? 1.0f : 0.0f);
                this.storiesVisibilityAnimator2 = valueAnimatorOfFloat;
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity.44
                    public C555944() {
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        DialogsActivity.this.progressToDialogStoriesCell = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                        View view = DialogsActivity.this.fragmentView;
                        if (view != null) {
                            view.invalidate();
                        }
                    }
                });
                this.storiesVisibilityAnimator2.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.45
                    public C556045() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        DialogsActivity dialogsActivity = DialogsActivity.this;
                        boolean z6 = dialogsActivity.dialogStoriesCellVisible;
                        dialogsActivity.progressToDialogStoriesCell = z6 ? 1.0f : 0.0f;
                        if (!z6) {
                            dialogsActivity.dialogStoriesCell.setVisibility(8);
                        }
                        View view = DialogsActivity.this.fragmentView;
                        if (view != null) {
                            view.invalidate();
                        }
                    }
                });
                this.storiesVisibilityAnimator2.setDuration(200L);
                this.storiesVisibilityAnimator2.setInterpolator(CubicBezierInterpolator.DEFAULT);
                this.storiesVisibilityAnimator2.start();
            } else {
                this.dialogStoriesCell.setVisibility((!z5 || isInPreviewMode()) ? 8 : 0);
                this.progressToDialogStoriesCell = this.dialogStoriesCellVisible ? 1.0f : 0.0f;
                View view = this.fragmentView;
                if (view != null) {
                    view.invalidate();
                }
            }
        }
        if (z2 == this.animateToHasStories) {
            return;
        }
        this.animateToHasStories = z2;
        if (z2) {
            this.dialogStoriesCell.setProgressToCollapse(1.0f, false);
        }
        if (z && !isInPreviewMode()) {
            this.dialogStoriesCell.setVisibility(0);
            float f = -this.scrollYOffset;
            float maxScrollYOffset = z2 ? 0.0f : getMaxScrollYOffset();
            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.storiesVisibilityAnimator = valueAnimatorOfFloat2;
            valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(f, z2, maxScrollYOffset) { // from class: org.telegram.ui.DialogsActivity.46
                int currentValue;
                final /* synthetic */ float val$fromScrollY;
                final /* synthetic */ boolean val$newVisibility;
                final /* synthetic */ float val$toScrollY;

                public C556146(float f2, boolean z22, float maxScrollYOffset2) {
                    this.val$fromScrollY = f2;
                    this.val$newVisibility = z22;
                    this.val$toScrollY = maxScrollYOffset2;
                    this.currentValue = (int) f2;
                }

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    DialogsActivity.this.progressToShowStories = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    if (!this.val$newVisibility) {
                        DialogsActivity dialogsActivity = DialogsActivity.this;
                        dialogsActivity.progressToShowStories = 1.0f - dialogsActivity.progressToShowStories;
                    }
                    int iLerp = (int) AndroidUtilities.lerp(this.val$fromScrollY, this.val$toScrollY, ((Float) valueAnimator2.getAnimatedValue()).floatValue());
                    int i3 = iLerp - this.currentValue;
                    this.currentValue = iLerp;
                    DialogsActivity.this.viewPages[0].listView.scrollBy(0, i3);
                    View view2 = DialogsActivity.this.fragmentView;
                    if (view2 != null) {
                        view2.invalidate();
                    }
                }
            });
            this.storiesVisibilityAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.47
                final /* synthetic */ boolean val$newVisibility;

                public C556247(boolean z22) {
                    z = z22;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    DialogsActivity dialogsActivity;
                    DialogsActivity dialogsActivity2 = DialogsActivity.this;
                    dialogsActivity2.storiesVisibilityAnimator = null;
                    boolean z6 = z;
                    dialogsActivity2.hasStories = z6;
                    if (!z6 && !dialogsActivity2.hasOnlySlefStories) {
                        dialogsActivity2.dialogStoriesCell.setVisibility(8);
                    }
                    boolean z7 = z;
                    DialogsActivity dialogsActivity3 = DialogsActivity.this;
                    if (!z7) {
                        dialogsActivity3.setScrollY(0.0f);
                        DialogsActivity.this.scrollAdditionalOffset = AndroidUtilities.m1036dp(81.0f);
                    } else {
                        dialogsActivity3.scrollAdditionalOffset = -AndroidUtilities.m1036dp(81.0f);
                        DialogsActivity.this.setScrollY(-r3.getMaxScrollYOffsetWithoutSearch());
                    }
                    int i3 = 0;
                    while (true) {
                        int length = DialogsActivity.this.viewPages.length;
                        dialogsActivity = DialogsActivity.this;
                        if (i3 >= length) {
                            break;
                        }
                        if (dialogsActivity.viewPages[i3] != null) {
                            DialogsActivity.this.viewPages[i3].listView.requestLayout();
                        }
                        i3++;
                    }
                    View view2 = dialogsActivity.fragmentView;
                    if (view2 != null) {
                        view2.requestLayout();
                    }
                }
            });
            this.storiesVisibilityAnimator.setDuration(200L);
            this.storiesVisibilityAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.storiesVisibilityAnimator.start();
            return;
        }
        this.progressToShowStories = z22 ? 1.0f : 0.0f;
        this.hasStories = z22;
        DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
        if ((z22 || this.hasOnlySlefStories) && !isInPreviewMode()) {
            i2 = 0;
        }
        dialogStoriesCell.setVisibility(i2);
        if (!z22) {
            setScrollY(0.0f);
        } else {
            this.scrollAdditionalOffset = -AndroidUtilities.m1036dp(81.0f);
            setScrollY(-(isArchive() ? getMaxScrollYOffsetWithoutSearch() : getMaxScrollYOffset()));
            if (isArchive()) {
                this.viewPages[0].dialogsAdapter.notifyDataSetChanged();
            }
        }
        while (true) {
            ViewPage[] viewPageArr = this.viewPages;
            if (i >= viewPageArr.length) {
                break;
            }
            ViewPage viewPage = viewPageArr[i];
            if (viewPage != null) {
                viewPage.listView.requestLayout();
            }
            i++;
        }
        View view2 = this.fragmentView;
        if (view2 != null) {
            view2.requestLayout();
            this.fragmentView.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$44 */
    public class C555944 implements ValueAnimator.AnimatorUpdateListener {
        public C555944() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator2) {
            DialogsActivity.this.progressToDialogStoriesCell = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
            View view = DialogsActivity.this.fragmentView;
            if (view != null) {
                view.invalidate();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$45 */
    public class C556045 extends AnimatorListenerAdapter {
        public C556045() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            DialogsActivity dialogsActivity = DialogsActivity.this;
            boolean z6 = dialogsActivity.dialogStoriesCellVisible;
            dialogsActivity.progressToDialogStoriesCell = z6 ? 1.0f : 0.0f;
            if (!z6) {
                dialogsActivity.dialogStoriesCell.setVisibility(8);
            }
            View view = DialogsActivity.this.fragmentView;
            if (view != null) {
                view.invalidate();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$46 */
    public class C556146 implements ValueAnimator.AnimatorUpdateListener {
        int currentValue;
        final /* synthetic */ float val$fromScrollY;
        final /* synthetic */ boolean val$newVisibility;
        final /* synthetic */ float val$toScrollY;

        public C556146(float f2, boolean z22, float maxScrollYOffset2) {
            this.val$fromScrollY = f2;
            this.val$newVisibility = z22;
            this.val$toScrollY = maxScrollYOffset2;
            this.currentValue = (int) f2;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator2) {
            DialogsActivity.this.progressToShowStories = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
            if (!this.val$newVisibility) {
                DialogsActivity dialogsActivity = DialogsActivity.this;
                dialogsActivity.progressToShowStories = 1.0f - dialogsActivity.progressToShowStories;
            }
            int iLerp = (int) AndroidUtilities.lerp(this.val$fromScrollY, this.val$toScrollY, ((Float) valueAnimator2.getAnimatedValue()).floatValue());
            int i3 = iLerp - this.currentValue;
            this.currentValue = iLerp;
            DialogsActivity.this.viewPages[0].listView.scrollBy(0, i3);
            View view2 = DialogsActivity.this.fragmentView;
            if (view2 != null) {
                view2.invalidate();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$47 */
    public class C556247 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$newVisibility;

        public C556247(boolean z22) {
            z = z22;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            DialogsActivity dialogsActivity;
            DialogsActivity dialogsActivity2 = DialogsActivity.this;
            dialogsActivity2.storiesVisibilityAnimator = null;
            boolean z6 = z;
            dialogsActivity2.hasStories = z6;
            if (!z6 && !dialogsActivity2.hasOnlySlefStories) {
                dialogsActivity2.dialogStoriesCell.setVisibility(8);
            }
            boolean z7 = z;
            DialogsActivity dialogsActivity3 = DialogsActivity.this;
            if (!z7) {
                dialogsActivity3.setScrollY(0.0f);
                DialogsActivity.this.scrollAdditionalOffset = AndroidUtilities.m1036dp(81.0f);
            } else {
                dialogsActivity3.scrollAdditionalOffset = -AndroidUtilities.m1036dp(81.0f);
                DialogsActivity.this.setScrollY(-r3.getMaxScrollYOffsetWithoutSearch());
            }
            int i3 = 0;
            while (true) {
                int length = DialogsActivity.this.viewPages.length;
                dialogsActivity = DialogsActivity.this;
                if (i3 >= length) {
                    break;
                }
                if (dialogsActivity.viewPages[i3] != null) {
                    DialogsActivity.this.viewPages[i3].listView.requestLayout();
                }
                i3++;
            }
            View view2 = dialogsActivity.fragmentView;
            if (view2 != null) {
                view2.requestLayout();
            }
        }
    }

    public void createSearchViewPager() {
        int i;
        SearchViewPager searchViewPager = this.searchViewPager;
        if ((searchViewPager != null && searchViewPager.getParent() == this.fragmentView) || this.fragmentView == null || getContext() == null) {
            return;
        }
        if (this.searchString != null) {
            i = 2;
        } else {
            i = !this.onlySelect ? 1 : 0;
        }
        C556449 c556449 = new SearchViewPager(getContext(), this, i, this.initialDialogsType, this.folderId, new SearchViewPager.ChatPreviewDelegate() { // from class: org.telegram.ui.DialogsActivity.48
            public C556348() {
            }

            @Override // org.telegram.ui.Components.SearchViewPager.ChatPreviewDelegate
            public void startChatPreview(RecyclerListView recyclerListView, DialogCell dialogCell) {
                DialogsActivity.this.showChatPreview(dialogCell);
            }

            @Override // org.telegram.ui.Components.SearchViewPager.ChatPreviewDelegate
            public void move(float f) {
                Point point = AndroidUtilities.displaySize;
                if (point.x > point.y) {
                    DialogsActivity.this.movePreviewFragment(f);
                }
            }

            @Override // org.telegram.ui.Components.SearchViewPager.ChatPreviewDelegate
            public void finish() {
                Point point = AndroidUtilities.displaySize;
                if (point.x > point.y) {
                    DialogsActivity.this.finishPreviewFragment();
                }
            }
        }) { // from class: org.telegram.ui.DialogsActivity.49
            final GradientProtectionDrawable gradientDrawable = new GradientProtectionDrawable(2);
            final GradientProtectionDrawable gradientDrawable2 = new GradientProtectionDrawable(8);

            @Override // org.telegram.p035ui.Components.ViewPagerFixed
            public boolean onBackProgress(float f) {
                return false;
            }

            public C556449(Context context, final DialogsActivity this, int i2, int i3, int i4, SearchViewPager.ChatPreviewDelegate chatPreviewDelegate) {
                super(context, this, i2, i3, i4, chatPreviewDelegate);
                this.gradientDrawable = new GradientProtectionDrawable(2);
                this.gradientDrawable2 = new GradientProtectionDrawable(8);
            }

            @Override // org.telegram.p035ui.Components.ViewPagerFixed
            public void onTabPageSelected(int i2) {
                DialogsActivity.this.updateSpeedItem(isDownloadsTab(i2));
            }

            @Override // org.telegram.p035ui.Components.SearchViewPager
            public boolean includeDownloads() {
                RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                return rightSlidingDialogContainer == null || !rightSlidingDialogContainer.hasFragment();
            }

            @Override // android.view.View
            public void setTranslationY(float f) {
                super.setTranslationY(f);
                if (DialogsActivity.this.searchTabsAndFiltersLayout != null) {
                    DialogsActivity.this.searchTabsAndFiltersLayout.setTranslationY(f);
                }
            }

            @Override // android.view.View
            public void setAlpha(float f) {
                super.setAlpha(f);
                DialogsActivity.this.blur3_InvalidateBlur();
            }

            @Override // android.view.ViewGroup, android.view.View
            public void dispatchDraw(Canvas canvas) {
                super.dispatchDraw(canvas);
                if (DialogsActivity.this.searchTabsView != null) {
                    int measuredHeight = ((((BaseFragment) DialogsActivity.this).actionBar.getMeasuredHeight() + AndroidUtilities.m1036dp(DialogsActivity.this.ADDITIONAL_LIST_HEIGHT_DP)) - AndroidUtilities.m1036dp(2.0f)) + (DialogsActivity.this.topPanelLayout != null ? (int) DialogsActivity.this.topPanelLayout.getAnimatedHeightWithPadding(AndroidUtilities.m1036dp(7.0f)) : 0);
                    this.gradientDrawable.setColor(Theme.multAlpha(DialogsActivity.this.getThemedColor(Theme.key_windowBackgroundWhite), 0.7f));
                    this.gradientDrawable.setInsets(0, measuredHeight, 0, 0);
                    this.gradientDrawable.setBounds(0, 0, getMeasuredWidth(), measuredHeight + AndroidUtilities.m1036dp(54.0f));
                    this.gradientDrawable.draw(canvas);
                }
                if (DialogsActivity.this.navigationBarHeight > AndroidUtilities.m1036dp(32.0f)) {
                    this.gradientDrawable2.setColor(Theme.multAlpha(DialogsActivity.this.getThemedColor(Theme.key_windowBackgroundWhite), 0.9f));
                    this.gradientDrawable2.setBounds(0, getMeasuredHeight() - DialogsActivity.this.navigationBarHeight, getMeasuredWidth(), getMeasuredHeight());
                    this.gradientDrawable2.draw(canvas);
                }
            }

            @Override // org.telegram.p035ui.Components.SearchViewPager
            public void onPageScrolled(int i2, int i3) {
                super.onPageScrolled(i2, i3);
                if (Build.VERSION.SDK_INT < 31 || DialogsActivity.this.scrollableViewNoiseSuppressor == null) {
                    return;
                }
                DialogsActivity.this.scrollableViewNoiseSuppressor.onScrolled(i2, i3);
                DialogsActivity.this.blur3_InvalidateBlur();
            }

            @Override // org.telegram.p035ui.Components.ViewPagerFixed
            public void onTabAnimationUpdate(boolean z) {
                super.onTabAnimationUpdate(z);
                if (Build.VERSION.SDK_INT < 31 || DialogsActivity.this.scrollableViewNoiseSuppressor == null) {
                    return;
                }
                DialogsActivity.this.blur3_InvalidateBlur();
            }
        };
        this.searchViewPager = c556449;
        ((ContentView) this.fragmentView).addView(c556449, this.searchViewPagerIndex);
        this.searchViewPager.dialogsSearchAdapter.setDelegate(new C556650());
        this.searchViewPager.channelsSearchListView.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda117
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public final void onItemClick(View view, int i2, float f, float f2) {
                this.f$0.lambda$createSearchViewPager$147(view, i2, f, f2);
            }
        });
        this.searchViewPager.botsSearchListView.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda118
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public final void onItemClick(View view, int i2, float f, float f2) {
                this.f$0.lambda$createSearchViewPager$148(view, i2, f, f2);
            }
        });
        this.searchViewPager.hashtagSearchListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda119
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i2) {
                this.f$0.lambda$createSearchViewPager$149(view, i2);
            }
        });
        this.searchViewPager.botsSearchListView.setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda120
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener
            public final boolean onItemClick(View view, int i2) {
                return this.f$0.lambda$createSearchViewPager$151(view, i2);
            }
        });
        this.searchViewPager.searchListView.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda121
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public final void onItemClick(View view, int i2, float f, float f2) {
                this.f$0.lambda$createSearchViewPager$152(view, i2, f, f2);
            }
        });
        this.searchViewPager.searchListView.setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListenerExtended() { // from class: org.telegram.ui.DialogsActivity.51
            public C556751() {
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
            public boolean onItemClick(View view, int i2, float f, float f2) {
                if (view instanceof ProfileSearchCell) {
                    ProfileSearchCell profileSearchCell = (ProfileSearchCell) view;
                    if (profileSearchCell.isBlocked()) {
                        DialogsActivity.this.showPremiumBlockedToast(view, profileSearchCell.getDialogId());
                        return true;
                    }
                }
                DialogsActivity dialogsActivity = DialogsActivity.this;
                return dialogsActivity.onItemLongClick(dialogsActivity.searchViewPager.searchListView, view, i2, f, f2, -1, DialogsActivity.this.searchViewPager.dialogsSearchAdapter);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
            public void onMove(float f, float f2) {
                Point point = AndroidUtilities.displaySize;
                if (point.x > point.y) {
                    DialogsActivity.this.movePreviewFragment(f2);
                }
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
            public void onLongClickRelease() {
                Point point = AndroidUtilities.displaySize;
                if (point.x > point.y) {
                    DialogsActivity.this.finishPreviewFragment();
                }
            }
        });
        this.searchViewPager.setFilteredSearchViewDelegate(new FilteredSearchView.Delegate() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda122
            @Override // org.telegram.ui.FilteredSearchView.Delegate
            public final void updateFiltersView(boolean z, ArrayList arrayList, ArrayList arrayList2, boolean z2) {
                this.f$0.lambda$createSearchViewPager$153(z, arrayList, arrayList2, z2);
            }
        });
        this.searchViewPager.setAlpha(0.0f);
        this.searchViewPager.setScaleX(1.05f);
        this.searchViewPager.setScaleY(1.05f);
        this.searchViewPager.setVisibility(8);
        this.searchViewPager.setBlurredBackgroundDrawableFactory(this.iBlur3FactoryBlur);
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$48 */
    public class C556348 implements SearchViewPager.ChatPreviewDelegate {
        public C556348() {
        }

        @Override // org.telegram.ui.Components.SearchViewPager.ChatPreviewDelegate
        public void startChatPreview(RecyclerListView recyclerListView, DialogCell dialogCell) {
            DialogsActivity.this.showChatPreview(dialogCell);
        }

        @Override // org.telegram.ui.Components.SearchViewPager.ChatPreviewDelegate
        public void move(float f) {
            Point point = AndroidUtilities.displaySize;
            if (point.x > point.y) {
                DialogsActivity.this.movePreviewFragment(f);
            }
        }

        @Override // org.telegram.ui.Components.SearchViewPager.ChatPreviewDelegate
        public void finish() {
            Point point = AndroidUtilities.displaySize;
            if (point.x > point.y) {
                DialogsActivity.this.finishPreviewFragment();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$49 */
    public class C556449 extends SearchViewPager {
        final GradientProtectionDrawable gradientDrawable = new GradientProtectionDrawable(2);
        final GradientProtectionDrawable gradientDrawable2 = new GradientProtectionDrawable(8);

        @Override // org.telegram.p035ui.Components.ViewPagerFixed
        public boolean onBackProgress(float f) {
            return false;
        }

        public C556449(Context context, final DialogsActivity this, int i2, int i3, int i4, SearchViewPager.ChatPreviewDelegate chatPreviewDelegate) {
            super(context, this, i2, i3, i4, chatPreviewDelegate);
            this.gradientDrawable = new GradientProtectionDrawable(2);
            this.gradientDrawable2 = new GradientProtectionDrawable(8);
        }

        @Override // org.telegram.p035ui.Components.ViewPagerFixed
        public void onTabPageSelected(int i2) {
            DialogsActivity.this.updateSpeedItem(isDownloadsTab(i2));
        }

        @Override // org.telegram.p035ui.Components.SearchViewPager
        public boolean includeDownloads() {
            RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
            return rightSlidingDialogContainer == null || !rightSlidingDialogContainer.hasFragment();
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            super.setTranslationY(f);
            if (DialogsActivity.this.searchTabsAndFiltersLayout != null) {
                DialogsActivity.this.searchTabsAndFiltersLayout.setTranslationY(f);
            }
        }

        @Override // android.view.View
        public void setAlpha(float f) {
            super.setAlpha(f);
            DialogsActivity.this.blur3_InvalidateBlur();
        }

        @Override // android.view.ViewGroup, android.view.View
        public void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            if (DialogsActivity.this.searchTabsView != null) {
                int measuredHeight = ((((BaseFragment) DialogsActivity.this).actionBar.getMeasuredHeight() + AndroidUtilities.m1036dp(DialogsActivity.this.ADDITIONAL_LIST_HEIGHT_DP)) - AndroidUtilities.m1036dp(2.0f)) + (DialogsActivity.this.topPanelLayout != null ? (int) DialogsActivity.this.topPanelLayout.getAnimatedHeightWithPadding(AndroidUtilities.m1036dp(7.0f)) : 0);
                this.gradientDrawable.setColor(Theme.multAlpha(DialogsActivity.this.getThemedColor(Theme.key_windowBackgroundWhite), 0.7f));
                this.gradientDrawable.setInsets(0, measuredHeight, 0, 0);
                this.gradientDrawable.setBounds(0, 0, getMeasuredWidth(), measuredHeight + AndroidUtilities.m1036dp(54.0f));
                this.gradientDrawable.draw(canvas);
            }
            if (DialogsActivity.this.navigationBarHeight > AndroidUtilities.m1036dp(32.0f)) {
                this.gradientDrawable2.setColor(Theme.multAlpha(DialogsActivity.this.getThemedColor(Theme.key_windowBackgroundWhite), 0.9f));
                this.gradientDrawable2.setBounds(0, getMeasuredHeight() - DialogsActivity.this.navigationBarHeight, getMeasuredWidth(), getMeasuredHeight());
                this.gradientDrawable2.draw(canvas);
            }
        }

        @Override // org.telegram.p035ui.Components.SearchViewPager
        public void onPageScrolled(int i2, int i3) {
            super.onPageScrolled(i2, i3);
            if (Build.VERSION.SDK_INT < 31 || DialogsActivity.this.scrollableViewNoiseSuppressor == null) {
                return;
            }
            DialogsActivity.this.scrollableViewNoiseSuppressor.onScrolled(i2, i3);
            DialogsActivity.this.blur3_InvalidateBlur();
        }

        @Override // org.telegram.p035ui.Components.ViewPagerFixed
        public void onTabAnimationUpdate(boolean z) {
            super.onTabAnimationUpdate(z);
            if (Build.VERSION.SDK_INT < 31 || DialogsActivity.this.scrollableViewNoiseSuppressor == null) {
                return;
            }
            DialogsActivity.this.blur3_InvalidateBlur();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$50 */
    public class C556650 implements DialogsSearchAdapter.DialogsSearchAdapterDelegate {
        public C556650() {
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public void searchStateChanged(boolean z, boolean z2) {
            if (DialogsActivity.this.searchViewPager.emptyView.getVisibility() == 0) {
                z2 = true;
            }
            if (DialogsActivity.this.searching && DialogsActivity.this.searchWas && DialogsActivity.this.searchViewPager.emptyView != null) {
                if (z || DialogsActivity.this.searchViewPager.dialogsSearchAdapter.getItemCount() != 0) {
                    DialogsActivity.this.searchViewPager.emptyView.showProgress(true, z2);
                } else {
                    DialogsActivity.this.searchViewPager.emptyView.showProgress(false, z2);
                }
            }
            if (z && DialogsActivity.this.searchViewPager.dialogsSearchAdapter.getItemCount() == 0) {
                DialogsActivity.this.searchViewPager.cancelEnterAnimation();
            }
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public void didPressedBlockedDialog(View view, long j) {
            DialogsActivity.this.showPremiumBlockedToast(view, j);
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public void didPressedOnSubDialog(long j) {
            DialogsActivity dialogsActivity;
            if (DialogsActivity.this.onlySelect) {
                if (DialogsActivity.this.validateSlowModeDialog(j)) {
                    boolean zIsEmpty = DialogsActivity.this.selectedDialogs.isEmpty();
                    DialogsActivity dialogsActivity2 = DialogsActivity.this;
                    if (!zIsEmpty) {
                        DialogsActivity.this.findAndUpdateCheckBox(j, dialogsActivity2.addOrRemoveSelectedDialog(j, null));
                        DialogsActivity.this.updateSelectedCount();
                        ((BaseFragment) DialogsActivity.this).actionBar.closeSearchField();
                        return;
                    }
                    dialogsActivity2.didSelectResult(j, 0L, true, false);
                    return;
                }
                return;
            }
            Bundle bundle = new Bundle();
            if (DialogObject.isUserDialog(j)) {
                bundle.putLong("user_id", j);
            } else {
                bundle.putLong("chat_id", -j);
            }
            DialogsActivity.this.closeSearch();
            if (AndroidUtilities.isTablet() && DialogsActivity.this.viewPages != null) {
                int i = 0;
                while (true) {
                    int length = DialogsActivity.this.viewPages.length;
                    dialogsActivity = DialogsActivity.this;
                    if (i >= length) {
                        break;
                    }
                    DialogsAdapter dialogsAdapter = dialogsActivity.viewPages[i].dialogsAdapter;
                    DialogsActivity.this.openedDialogId.dialogId = j;
                    dialogsAdapter.setOpenedDialogId(j);
                    i++;
                }
                dialogsActivity.updateVisibleRows(MessagesController.UPDATE_MASK_SELECT_DIALOG);
            }
            String str = DialogsActivity.this.searchString;
            DialogsActivity dialogsActivity3 = DialogsActivity.this;
            if (str != null) {
                if (dialogsActivity3.getMessagesController().checkCanOpenChat(bundle, DialogsActivity.this)) {
                    DialogsActivity.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
                    DialogsActivity.this.presentFragment(new ChatActivity(bundle));
                    return;
                }
                return;
            }
            if (dialogsActivity3.getMessagesController().checkCanOpenChat(bundle, DialogsActivity.this)) {
                DialogsActivity.this.presentFragment(new ChatActivity(bundle));
            }
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public void needRemoveHint(final long j) {
            TLRPC.User user;
            if (DialogsActivity.this.getParentActivity() == null || (user = DialogsActivity.this.getMessagesController().getUser(Long.valueOf(j))) == null) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(DialogsActivity.this.getParentActivity());
            builder.setTitle(LocaleController.getString(C2797R.string.ChatHintsDeleteAlertTitle));
            builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("ChatHintsDeleteAlert", C2797R.string.ChatHintsDeleteAlert, ContactsController.formatName(user.first_name, user.last_name))));
            builder.setPositiveButton(LocaleController.getString(C2797R.string.StickersRemove), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$50$$ExternalSyntheticLambda2
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    this.f$0.lambda$needRemoveHint$0(j, alertDialog, i);
                }
            });
            builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
            AlertDialog alertDialogCreate = builder.create();
            DialogsActivity.this.showDialog(alertDialogCreate);
            TextView textView = (TextView) alertDialogCreate.getButton(-1);
            if (textView != null) {
                textView.setTextColor(DialogsActivity.this.getThemedColor(Theme.key_text_RedBold));
            }
        }

        public /* synthetic */ void lambda$needRemoveHint$0(long j, AlertDialog alertDialog, int i) {
            DialogsActivity.this.getMediaDataController().removePeer(j);
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public void needClearList() {
            AlertDialog.Builder builder = new AlertDialog.Builder(DialogsActivity.this.getParentActivity());
            if (DialogsActivity.this.searchViewPager.dialogsSearchAdapter.isSearchWas() && DialogsActivity.this.searchViewPager.dialogsSearchAdapter.isRecentSearchDisplayed()) {
                builder.setTitle(LocaleController.getString(C2797R.string.ClearSearchAlertPartialTitle));
                builder.setMessage(LocaleController.formatPluralString("ClearSearchAlertPartial", DialogsActivity.this.searchViewPager.dialogsSearchAdapter.getRecentResultsCount(), new Object[0]));
                builder.setPositiveButton(LocaleController.getString(C2797R.string.Clear), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$50$$ExternalSyntheticLambda0
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i) {
                        this.f$0.lambda$needClearList$1(alertDialog, i);
                    }
                });
            } else {
                builder.setTitle(LocaleController.getString(C2797R.string.ClearSearchAlertTitle));
                builder.setMessage(LocaleController.getString(C2797R.string.ClearSearchAlert));
                builder.setPositiveButton(LocaleController.getString(C2797R.string.ClearButton), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$50$$ExternalSyntheticLambda1
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i) {
                        this.f$0.lambda$needClearList$2(alertDialog, i);
                    }
                });
            }
            builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
            AlertDialog alertDialogCreate = builder.create();
            DialogsActivity.this.showDialog(alertDialogCreate);
            TextView textView = (TextView) alertDialogCreate.getButton(-1);
            if (textView != null) {
                textView.setTextColor(DialogsActivity.this.getThemedColor(Theme.key_text_RedBold));
            }
        }

        public /* synthetic */ void lambda$needClearList$1(AlertDialog alertDialog, int i) {
            DialogsActivity.this.searchViewPager.dialogsSearchAdapter.clearRecentSearch();
        }

        public /* synthetic */ void lambda$needClearList$2(AlertDialog alertDialog, int i) {
            boolean zIsRecentSearchDisplayed = DialogsActivity.this.searchViewPager.dialogsSearchAdapter.isRecentSearchDisplayed();
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (zIsRecentSearchDisplayed) {
                dialogsActivity.searchViewPager.dialogsSearchAdapter.clearRecentSearch();
            } else {
                dialogsActivity.searchViewPager.dialogsSearchAdapter.clearRecentHashtags();
            }
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public void runResultsEnterAnimation() {
            if (DialogsActivity.this.searchViewPager != null) {
                DialogsActivity.this.searchViewPager.runResultsEnterAnimation();
            }
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public boolean isSelected(long j) {
            return DialogsActivity.this.selectedDialogs.contains(Long.valueOf(j));
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public long getSearchForumDialogId() {
            RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
            if (rightSlidingDialogContainer == null || !(rightSlidingDialogContainer.getFragment() instanceof TopicsFragment)) {
                return 0L;
            }
            return ((TopicsFragment) DialogsActivity.this.rightSlidingDialogContainer.getFragment()).getDialogId();
        }
    }

    public /* synthetic */ void lambda$createSearchViewPager$147(View view, int i, float f, float f2) {
        Object object = this.searchViewPager.channelsSearchAdapter.getObject(i);
        if (object instanceof TLRPC.Chat) {
            Bundle bundle = new Bundle();
            bundle.putLong("chat_id", ((TLRPC.Chat) object).f1245id);
            ChatActivity chatActivity = new ChatActivity(bundle);
            chatActivity.setNextChannels(this.searchViewPager.channelsSearchAdapter.getNextChannels(i));
            presentFragment(chatActivity);
            return;
        }
        if (object instanceof MessageObject) {
            MessageObject messageObject = (MessageObject) object;
            Bundle bundle2 = new Bundle();
            if (messageObject.getDialogId() >= 0) {
                bundle2.putLong("user_id", messageObject.getDialogId());
            } else {
                bundle2.putLong("chat_id", -messageObject.getDialogId());
            }
            bundle2.putInt("message_id", messageObject.getId());
            presentFragment(highlightFoundQuote(new ChatActivity(bundle2), messageObject));
        }
    }

    public /* synthetic */ void lambda$createSearchViewPager$148(View view, int i, float f, float f2) {
        Object object = this.searchViewPager.botsSearchAdapter.getObject(i);
        if (object instanceof TLRPC.User) {
            presentFragment(ProfileActivity.m1186of(((TLRPC.User) object).f1407id));
            return;
        }
        if (object instanceof MessageObject) {
            MessageObject messageObject = (MessageObject) object;
            Bundle bundle = new Bundle();
            if (messageObject.getDialogId() >= 0) {
                bundle.putLong("user_id", messageObject.getDialogId());
            } else {
                bundle.putLong("chat_id", -messageObject.getDialogId());
            }
            bundle.putInt("message_id", messageObject.getId());
            presentFragment(highlightFoundQuote(new ChatActivity(bundle), messageObject));
        }
    }

    public /* synthetic */ void lambda$createSearchViewPager$149(View view, int i) {
        Object obj = this.searchViewPager.hashtagSearchAdapter.getItem(i).object;
        if (obj instanceof MessageObject) {
            MessageObject messageObject = (MessageObject) obj;
            Bundle bundle = new Bundle();
            if (messageObject.getDialogId() >= 0) {
                bundle.putLong("user_id", messageObject.getDialogId());
            } else {
                bundle.putLong("chat_id", -messageObject.getDialogId());
            }
            bundle.putInt("message_id", messageObject.getId());
            presentFragment(highlightFoundQuote(new ChatActivity(bundle), messageObject));
            return;
        }
        if (obj instanceof StoriesController.SearchStoriesList) {
            StoriesController.SearchStoriesList searchStoriesList = (StoriesController.SearchStoriesList) obj;
            Bundle bundle2 = new Bundle();
            bundle2.putInt(TeXSymbolParser.TYPE_ATTR, 3);
            bundle2.putString("hashtag", searchStoriesList.query);
            bundle2.putInt("storiesCount", searchStoriesList.getCount());
            presentFragment(new MediaActivity(bundle2, null));
        }
    }

    public /* synthetic */ boolean lambda$createSearchViewPager$151(View view, int i) {
        Object topPeerObject = this.searchViewPager.botsSearchAdapter.getTopPeerObject(i);
        if (!(topPeerObject instanceof TLRPC.User)) {
            return false;
        }
        final TLRPC.User user = (TLRPC.User) topPeerObject;
        new AlertDialog.Builder(getContext(), this.resourceProvider).setTitle(LocaleController.getString(C2797R.string.AppsClearSearch)).setMessage(LocaleController.formatString(C2797R.string.AppsClearSearchAlert, "\"" + UserObject.getUserName(user) + "\"")).setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null).setPositiveButton(LocaleController.getString(C2797R.string.Remove), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda130
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i2) {
                this.f$0.lambda$createSearchViewPager$150(user, alertDialog, i2);
            }
        }).makeRed(-1).show();
        return false;
    }

    public /* synthetic */ void lambda$createSearchViewPager$150(TLRPC.User user, AlertDialog alertDialog, int i) {
        getMediaDataController().removeWebapp(user.f1407id);
    }

    public /* synthetic */ void lambda$createSearchViewPager$152(View view, int i, float f, float f2) {
        Object item = this.searchViewPager.dialogsSearchAdapter.getItem(i);
        if (item instanceof TLRPC.TL_sponsoredPeer) {
            TLRPC.TL_sponsoredPeer tL_sponsoredPeer = (TLRPC.TL_sponsoredPeer) item;
            presentFragment(ChatActivity.m1139of(DialogObject.getPeerDialogId(tL_sponsoredPeer.peer)));
            this.searchViewPager.dialogsSearchAdapter.clickedSponsoredPeer(tL_sponsoredPeer);
            return;
        }
        if (view instanceof ProfileSearchCell) {
            ProfileSearchCell profileSearchCell = (ProfileSearchCell) view;
            if (profileSearchCell.isBlocked()) {
                showPremiumBlockedToast(view, profileSearchCell.getDialogId());
                return;
            }
        }
        int i2 = this.initialDialogsType;
        SearchViewPager searchViewPager = this.searchViewPager;
        if (i2 == 10) {
            onItemLongClick(searchViewPager.searchListView, view, i, f, f2, -1, searchViewPager.dialogsSearchAdapter);
        } else {
            onItemClick(view, i, searchViewPager.dialogsSearchAdapter, f, f2);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$51 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C556751 implements RecyclerListView.OnItemLongClickListenerExtended {
        public C556751() {
        }

        @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
        public boolean onItemClick(View view, int i2, float f, float f2) {
            if (view instanceof ProfileSearchCell) {
                ProfileSearchCell profileSearchCell = (ProfileSearchCell) view;
                if (profileSearchCell.isBlocked()) {
                    DialogsActivity.this.showPremiumBlockedToast(view, profileSearchCell.getDialogId());
                    return true;
                }
            }
            DialogsActivity dialogsActivity = DialogsActivity.this;
            return dialogsActivity.onItemLongClick(dialogsActivity.searchViewPager.searchListView, view, i2, f, f2, -1, DialogsActivity.this.searchViewPager.dialogsSearchAdapter);
        }

        @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
        public void onMove(float f, float f2) {
            Point point = AndroidUtilities.displaySize;
            if (point.x > point.y) {
                DialogsActivity.this.movePreviewFragment(f2);
            }
        }

        @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
        public void onLongClickRelease() {
            Point point = AndroidUtilities.displaySize;
            if (point.x > point.y) {
                DialogsActivity.this.finishPreviewFragment();
            }
        }
    }

    public /* synthetic */ void lambda$createSearchViewPager$153(boolean z, ArrayList arrayList, ArrayList arrayList2, boolean z2) {
        updateFiltersView(z, arrayList, arrayList2, z2, true);
    }

    public boolean clickSelectsDialog() {
        return this.initialDialogsType == 10;
    }

    public void openSetAvatar() {
        try {
            ((RLottieDrawable) ((AvatarDrawable) this.dialogsHintCell.imageView.getImageReceiver().getStaticThumb()).getCustomIcon()).restart(true);
        } catch (Exception unused) {
        }
        if (this.imageUpdater == null) {
            ImageUpdater imageUpdater = new ImageUpdater(true, 0, true);
            this.imageUpdater = imageUpdater;
            imageUpdater.setOpenWithFrontfaceCamera(true);
            ImageUpdater imageUpdater2 = this.imageUpdater;
            imageUpdater2.parentFragment = this;
            imageUpdater2.setDelegate(new C556852());
            getMediaDataController().checkFeaturedStickers();
            getMessagesController().loadSuggestedFilters();
            getMessagesController().loadUserInfo(getUserConfig().getCurrentUser(), true, this.classGuid);
        }
        TLRPC.User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(UserConfig.getInstance(this.currentAccount).getClientUserId()));
        if (user == null) {
            user = UserConfig.getInstance(this.currentAccount).getCurrentUser();
        }
        if (user == null) {
            return;
        }
        this.imageUpdater.updateColors();
        ImageUpdater imageUpdater3 = this.imageUpdater;
        TLRPC.UserProfilePhoto userProfilePhoto = user.photo;
        imageUpdater3.openMenu((userProfilePhoto == null || userProfilePhoto.photo_big == null || (userProfilePhoto instanceof TLRPC.TL_userProfilePhotoEmpty)) ? false : true, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda93
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openSetAvatar$154();
            }
        }, new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda94
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$openSetAvatar$155(dialogInterface);
            }
        }, 0);
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$52 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C556852 implements ImageUpdater.ImageUpdaterDelegate {
        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public boolean supportsBulletin() {
            return true;
        }

        public C556852() {
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public void didStartUpload(boolean z, boolean z2) {
            if (DialogsActivity.this.uploadingAvatarBulletin != null) {
                DialogsActivity.this.uploadingAvatarBulletin.hide();
                DialogsActivity.this.uploadingAvatarBulletin = null;
            }
            Bulletin.ProgressLayout progressLayout = new Bulletin.ProgressLayout(DialogsActivity.this.getContext(), ((BaseFragment) DialogsActivity.this).resourceProvider);
            BackupImageView backupImageView = progressLayout.imageView;
            if (z) {
                backupImageView.setImageBitmap(DialogsActivity.this.imageUpdater.getPreviewBitmap());
            } else {
                backupImageView.setImageBitmap(PhotoViewer.getInstance().centerImage.getBitmap());
            }
            progressLayout.setButton(new Bulletin.UndoButton(DialogsActivity.this.getContext(), true, ((BaseFragment) DialogsActivity.this).resourceProvider).setText(LocaleController.getString(C2797R.string.ViewAction)).setUndoAction(new Runnable() { // from class: org.telegram.ui.DialogsActivity$52$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.openAvatarInProfile();
                }
            }));
            progressLayout.getButton().setVisibility(8);
            progressLayout.textView.setText(LocaleController.getString(z2 ? C2797R.string.YourProfileVideoUploading : C2797R.string.YourProfilePhotoUploading), true);
            DialogsActivity dialogsActivity = DialogsActivity.this;
            dialogsActivity.uploadingAvatarBulletin = BulletinFactory.m1143of(dialogsActivity).create(progressLayout, -1);
            DialogsActivity.this.uploadingAvatarBulletin.hideAfterBottomSheet = false;
            DialogsActivity.this.uploadingAvatarBulletin.setCanHide(false);
            DialogsActivity.this.uploadingAvatarBulletin.skipShowAnimation();
            DialogsActivity.this.uploadingAvatarBulletin.show();
        }

        public void openAvatarInProfile() {
            Bundle bundle = new Bundle();
            bundle.putLong("user_id", UserConfig.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getClientUserId());
            bundle.putBoolean("my_profile", true);
            DialogsActivity.this.presentFragment(new ProfileActivity(bundle, null));
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public PhotoViewer.PlaceProviderObject getCloseIntoObject() {
            if (DialogsActivity.this.uploadingAvatarBulletin == null) {
                return null;
            }
            Bulletin.ProgressLayout progressLayout = (Bulletin.ProgressLayout) DialogsActivity.this.uploadingAvatarBulletin.getLayout();
            PhotoViewer.PlaceProviderObject placeProviderObject = new PhotoViewer.PlaceProviderObject();
            int[] iArr = new int[2];
            progressLayout.imageView.getLocationInWindow(iArr);
            placeProviderObject.viewX = iArr[0];
            placeProviderObject.viewY = iArr[1];
            placeProviderObject.parentView = DialogsActivity.this.fragmentView;
            ImageReceiver imageReceiver = progressLayout.imageView.getImageReceiver();
            placeProviderObject.imageReceiver = imageReceiver;
            placeProviderObject.thumb = imageReceiver.getBitmapSafe();
            placeProviderObject.clipBottomAddition = 0;
            placeProviderObject.radius = placeProviderObject.imageReceiver.getRoundRadius();
            placeProviderObject.scale = progressLayout.imageView.getScaleX();
            return placeProviderObject;
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public void onUploadProgressChanged(float f) {
            if (DialogsActivity.this.uploadingAvatarBulletin != null) {
                ((Bulletin.ProgressLayout) DialogsActivity.this.uploadingAvatarBulletin.getLayout()).setProgress(f * 0.9f);
            }
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public void didUploadPhoto(final TLRPC.InputFile inputFile, final TLRPC.InputFile inputFile2, final double d, final String str, final TLRPC.PhotoSize photoSize, final TLRPC.PhotoSize photoSize2, final boolean z, final TLRPC.VideoSize videoSize) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$52$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didUploadPhoto$2(inputFile, inputFile2, videoSize, d, str, z, photoSize2, photoSize);
                }
            });
        }

        public /* synthetic */ void lambda$didUploadPhoto$1(final String str, final boolean z, final TLObject tLObject, final TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$52$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didUploadPhoto$0(tL_error, tLObject, str, z);
                }
            });
        }

        public /* synthetic */ void lambda$didUploadPhoto$0(TLRPC.TL_error tL_error, TLObject tLObject, String str, boolean z) {
            if (tL_error == null) {
                TLRPC.User user = DialogsActivity.this.getMessagesController().getUser(Long.valueOf(DialogsActivity.this.getUserConfig().getClientUserId()));
                DialogsActivity dialogsActivity = DialogsActivity.this;
                if (user == null) {
                    user = dialogsActivity.getUserConfig().getCurrentUser();
                    if (user == null) {
                        return;
                    } else {
                        DialogsActivity.this.getMessagesController().putUser(user, false);
                    }
                } else {
                    dialogsActivity.getUserConfig().setCurrentUser(user);
                }
                TLRPC.TL_photos_photo tL_photos_photo = (TLRPC.TL_photos_photo) tLObject;
                ArrayList<TLRPC.PhotoSize> arrayList = tL_photos_photo.photo.sizes;
                TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(arrayList, 150);
                TLRPC.PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(arrayList, 800);
                TLRPC.VideoSize closestVideoSizeWithSize = tL_photos_photo.photo.video_sizes.isEmpty() ? null : FileLoader.getClosestVideoSizeWithSize(tL_photos_photo.photo.video_sizes, MediaDataController.MAX_STYLE_RUNS_COUNT);
                TLRPC.TL_userProfilePhoto tL_userProfilePhoto = new TLRPC.TL_userProfilePhoto();
                user.photo = tL_userProfilePhoto;
                tL_userProfilePhoto.photo_id = tL_photos_photo.photo.f1276id;
                if (closestPhotoSizeWithSize != null) {
                    tL_userProfilePhoto.photo_small = closestPhotoSizeWithSize.location;
                }
                if (closestPhotoSizeWithSize2 != null) {
                    tL_userProfilePhoto.photo_big = closestPhotoSizeWithSize2.location;
                }
                if (closestPhotoSizeWithSize != null && DialogsActivity.this.avatar != null) {
                    FileLoader.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getPathToAttach(DialogsActivity.this.avatar, true).renameTo(FileLoader.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getPathToAttach(closestPhotoSizeWithSize, true));
                    ImageLoader.getInstance().replaceImageInCache(DialogsActivity.this.avatar.volume_id + "_" + DialogsActivity.this.avatar.local_id + "@50_50", closestPhotoSizeWithSize.location.volume_id + "_" + closestPhotoSizeWithSize.location.local_id + "@50_50", ImageLocation.getForUserOrChat(((BaseFragment) DialogsActivity.this).currentAccount, user, 1), false);
                }
                if (closestVideoSizeWithSize != null && str != null) {
                    new File(str).renameTo(FileLoader.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getPathToAttach(closestVideoSizeWithSize, "mp4", true));
                } else if (closestPhotoSizeWithSize2 != null && DialogsActivity.this.avatarBig != null) {
                    FileLoader.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getPathToAttach(DialogsActivity.this.avatarBig, true).renameTo(FileLoader.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getPathToAttach(closestPhotoSizeWithSize2, true));
                }
                DialogsActivity.this.getMessagesController().getDialogPhotos(user.f1407id).addPhotoAtStart(tL_photos_photo.photo);
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(user);
                DialogsActivity.this.getMessagesStorage().putUsersAndChats(arrayList2, null, false, true);
                TLRPC.UserFull userFull = DialogsActivity.this.getMessagesController().getUserFull(DialogsActivity.this.getUserConfig().getClientUserId());
                if (userFull != null) {
                    userFull.profile_photo = tL_photos_photo.photo;
                    DialogsActivity.this.getMessagesStorage().updateUserInfo(userFull, false);
                }
            }
            DialogsActivity.this.avatar = null;
            DialogsActivity.this.avatarBig = null;
            DialogsActivity.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_ALL));
            DialogsActivity.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
            DialogsActivity.this.getUserConfig().saveConfig(true);
            MessagesController.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).removeSuggestion(0L, "USERPIC_SETUP");
            DialogsActivity.this.updateDialogsHint();
            if (DialogsActivity.this.uploadingAvatarBulletin != null) {
                Bulletin.ProgressLayout progressLayout = (Bulletin.ProgressLayout) DialogsActivity.this.uploadingAvatarBulletin.getLayout();
                progressLayout.textView.setText(LocaleController.getString(z ? C2797R.string.YourProfileVideoDone : C2797R.string.YourProfilePhotoDone), true);
                progressLayout.setProgress(1.0f);
                Bulletin.Button button = progressLayout.getButton();
                button.setScaleX(0.6f);
                button.setScaleY(0.6f);
                button.setAlpha(0.0f);
                button.setVisibility(0);
                button.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).setDuration(360L).start();
                DialogsActivity.this.uploadingAvatarBulletin.setDuration(5000);
                DialogsActivity.this.uploadingAvatarBulletin.setCanHide(false);
                DialogsActivity.this.uploadingAvatarBulletin.setCanHide(true);
            }
        }

        public /* synthetic */ void lambda$didUploadPhoto$2(TLRPC.InputFile inputFile, TLRPC.InputFile inputFile2, TLRPC.VideoSize videoSize, double d, final String str, final boolean z, TLRPC.PhotoSize photoSize, TLRPC.PhotoSize photoSize2) {
            if (inputFile != null || inputFile2 != null || videoSize != null) {
                if (DialogsActivity.this.avatar == null) {
                    return;
                }
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
                DialogsActivity dialogsActivity = DialogsActivity.this;
                dialogsActivity.avatarUploadingRequest = dialogsActivity.getConnectionsManager().sendRequest(tL_photos_uploadProfilePhoto, new RequestDelegate() { // from class: org.telegram.ui.DialogsActivity$52$$ExternalSyntheticLambda2
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$didUploadPhoto$1(str, z, tLObject, tL_error);
                    }
                });
            } else {
                DialogsActivity.this.avatar = photoSize.location;
                DialogsActivity.this.avatarBig = photoSize2.location;
            }
            ((BaseFragment) DialogsActivity.this).actionBar.createMenu().requestLayout();
        }
    }

    public /* synthetic */ void lambda$openSetAvatar$154() {
        MessagesController.getInstance(this.currentAccount).deleteUserPhoto(null);
    }

    public /* synthetic */ void lambda$openSetAvatar$155(DialogInterface dialogInterface) {
        if (this.imageUpdater.isUploadingImage()) {
            MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, "USERPIC_SETUP");
            updateDialogsHint();
        }
    }

    private void openWriteContacts() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("destroyAfterSelect", true);
        presentFragment(new ContactsActivity(bundle));
    }

    private void openStoriesRecorder() {
        if (!this.storiesEnabled) {
            HintView2 hintView2 = this.storyPremiumHint;
            if (hintView2 != null) {
                if (hintView2.shown()) {
                    return;
                } else {
                    AndroidUtilities.removeFromParent(this.storyPremiumHint);
                }
            }
            HintView2 bgColor = new HintView2(getContext(), 2).setRounding(8.0f).setDuration(8000L).setCloseButton(true).setMultilineText(true).setMaxWidthPx(AndroidUtilities.displaySize.x - AndroidUtilities.m1036dp(148.0f)).setText(AndroidUtilities.replaceSingleTag(LocaleController.getString("StoriesPremiumHint2").replace('\n', ' '), Theme.key_undo_cancelColor, 0, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda87
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$openStoriesRecorder$156();
                }
            })).setJoint(1.0f, -40.0f).setBgColor(getThemedColor(Theme.key_undo_background));
            this.storyPremiumHint = bgColor;
            bgColor.setTranslationY((-this.navigationBarHeight) - this.additionNavigationBarHeight);
            ((ViewGroup) this.fragmentView).addView(this.storyPremiumHint, LayoutHelper.createFrame(-1, 240.0f, 87, 12.0f, 0.0f, 68.0f, 40.0f));
            this.storyPremiumHint.show();
            return;
        }
        HintView2 hintView22 = this.storyHint;
        if (hintView22 != null) {
            hintView22.hide();
        }
        StoriesController.StoryLimit storyLimitCheckStoryLimit = MessagesController.getInstance(this.currentAccount).getStoriesController().checkStoryLimit();
        if (storyLimitCheckStoryLimit != null && storyLimitCheckStoryLimit.active(this.currentAccount, 1)) {
            showDialog(new LimitReachedBottomSheet(this, getContext(), storyLimitCheckStoryLimit.getLimitReachedType(), this.currentAccount, null));
        } else {
            StoryRecorder.getInstance(getParentActivity(), this.currentAccount).closeToWhenSent(new StoryRecorder.ClosingViewProvider() { // from class: org.telegram.ui.DialogsActivity.53
                public C556953() {
                }

                @Override // org.telegram.ui.Stories.recorder.StoryRecorder.ClosingViewProvider
                public void preLayout(long j, Runnable runnable) {
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    if (dialogsActivity.dialogStoriesCell != null) {
                        dialogsActivity.scrollToTop(false, true);
                        DialogsActivity.this.invalidateScrollY = true;
                        DialogsActivity.this.fragmentView.invalidate();
                        if (j == 0 || j == DialogsActivity.this.getUserConfig().getClientUserId()) {
                            DialogsActivity.this.dialogStoriesCell.scrollToFirstCell();
                        } else {
                            DialogsActivity.this.dialogStoriesCell.scrollTo(j);
                        }
                        DialogsActivity.this.viewPages[0].listView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: org.telegram.ui.DialogsActivity.53.1
                            final /* synthetic */ Runnable val$runnable;

                            public AnonymousClass1(Runnable runnable2) {
                                runnable = runnable2;
                            }

                            @Override // android.view.ViewTreeObserver.OnPreDrawListener
                            public boolean onPreDraw() {
                                DialogsActivity.this.viewPages[0].listView.getViewTreeObserver().removeOnPreDrawListener(this);
                                AndroidUtilities.runOnUIThread(runnable, 100L);
                                return false;
                            }
                        });
                        return;
                    }
                    runnable2.run();
                }

                /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$53$1 */
                public class AnonymousClass1 implements ViewTreeObserver.OnPreDrawListener {
                    final /* synthetic */ Runnable val$runnable;

                    public AnonymousClass1(Runnable runnable2) {
                        runnable = runnable2;
                    }

                    @Override // android.view.ViewTreeObserver.OnPreDrawListener
                    public boolean onPreDraw() {
                        DialogsActivity.this.viewPages[0].listView.getViewTreeObserver().removeOnPreDrawListener(this);
                        AndroidUtilities.runOnUIThread(runnable, 100L);
                        return false;
                    }
                }

                @Override // org.telegram.ui.Stories.recorder.StoryRecorder.ClosingViewProvider
                public StoryRecorder.SourceView getView(long j) {
                    DialogStoriesCell dialogStoriesCell = DialogsActivity.this.dialogStoriesCell;
                    return StoryRecorder.SourceView.fromStoryCell(dialogStoriesCell != null ? dialogStoriesCell.findStoryCell(j) : null);
                }
            }).open(null, true);
        }
    }

    public /* synthetic */ void lambda$openStoriesRecorder$156() {
        HintView2 hintView2 = this.storyPremiumHint;
        if (hintView2 != null) {
            hintView2.hide();
        }
        presentFragment(new PremiumPreviewFragment("stories"));
    }

    /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$53 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C556953 implements StoryRecorder.ClosingViewProvider {
        public C556953() {
        }

        @Override // org.telegram.ui.Stories.recorder.StoryRecorder.ClosingViewProvider
        public void preLayout(long j, Runnable runnable2) {
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (dialogsActivity.dialogStoriesCell != null) {
                dialogsActivity.scrollToTop(false, true);
                DialogsActivity.this.invalidateScrollY = true;
                DialogsActivity.this.fragmentView.invalidate();
                if (j == 0 || j == DialogsActivity.this.getUserConfig().getClientUserId()) {
                    DialogsActivity.this.dialogStoriesCell.scrollToFirstCell();
                } else {
                    DialogsActivity.this.dialogStoriesCell.scrollTo(j);
                }
                DialogsActivity.this.viewPages[0].listView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: org.telegram.ui.DialogsActivity.53.1
                    final /* synthetic */ Runnable val$runnable;

                    public AnonymousClass1(Runnable runnable22) {
                        runnable = runnable22;
                    }

                    @Override // android.view.ViewTreeObserver.OnPreDrawListener
                    public boolean onPreDraw() {
                        DialogsActivity.this.viewPages[0].listView.getViewTreeObserver().removeOnPreDrawListener(this);
                        AndroidUtilities.runOnUIThread(runnable, 100L);
                        return false;
                    }
                });
                return;
            }
            runnable22.run();
        }

        /* JADX INFO: renamed from: org.telegram.ui.DialogsActivity$53$1 */
        public class AnonymousClass1 implements ViewTreeObserver.OnPreDrawListener {
            final /* synthetic */ Runnable val$runnable;

            public AnonymousClass1(Runnable runnable22) {
                runnable = runnable22;
            }

            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                DialogsActivity.this.viewPages[0].listView.getViewTreeObserver().removeOnPreDrawListener(this);
                AndroidUtilities.runOnUIThread(runnable, 100L);
                return false;
            }
        }

        @Override // org.telegram.ui.Stories.recorder.StoryRecorder.ClosingViewProvider
        public StoryRecorder.SourceView getView(long j) {
            DialogStoriesCell dialogStoriesCell = DialogsActivity.this.dialogStoriesCell;
            return StoryRecorder.SourceView.fromStoryCell(dialogStoriesCell != null ? dialogStoriesCell.findStoryCell(j) : null);
        }
    }

    private void checkEmailConfig() {
        int iCheckEmailSuggestion = getMessagesController().checkEmailSuggestion();
        if (iCheckEmailSuggestion != 0) {
            presentFragment(new LoginActivity().changeEmail(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda85
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$checkEmailConfig$157();
                }
            }, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda86
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$checkEmailConfig$158();
                }
            }, iCheckEmailSuggestion == 2));
            getMessagesController().markEmailSuggestionAsShown();
        }
    }

    public /* synthetic */ void lambda$checkEmailConfig$157() {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourceProvider);
        lottieLayout.setAnimation(C2797R.raw.email_check_inbox, new String[0]);
        lottieLayout.textView.setText(LocaleController.getString(C2797R.string.YourLoginEmailChangedSuccess));
        Bulletin.make(this, lottieLayout, 2750).show();
        try {
            this.fragmentView.performHapticFeedback(3, 2);
        } catch (Exception unused) {
        }
    }

    public /* synthetic */ void lambda$checkEmailConfig$158() {
        getMessagesController().removeSuggestion(0L, "SETUP_LOGIN_EMAIL");
    }

    private boolean isTouchInsideCurrentDialogsList(MotionEvent motionEvent) {
        ViewPage[] viewPageArr;
        ViewPage viewPage;
        DialogsRecyclerView dialogsRecyclerView;
        if (motionEvent != null && (viewPageArr = this.viewPages) != null && viewPageArr.length != 0 && (viewPage = viewPageArr[0]) != null && (dialogsRecyclerView = viewPage.listView) != null) {
            Rect rect = AndroidUtilities.rectTmp2;
            if (dialogsRecyclerView.getVisibility() == 0 && dialogsRecyclerView.getGlobalVisibleRect(rect)) {
                return rect.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
            }
        }
        return false;
    }

    @Override // org.telegram.ui.MainTabsActivity.TabFragmentDelegate
    public boolean canParentTabsSlide(MotionEvent motionEvent, boolean z) {
        RightSlidingDialogContainer rightSlidingDialogContainer;
        if (isParentPreviewActive() || this.searchIsShowed || ((rightSlidingDialogContainer = this.rightSlidingDialogContainer) != null && rightSlidingDialogContainer.hasFragment())) {
            return false;
        }
        View view = this.blurredView;
        if (view != null && view.getVisibility() == 0) {
            return false;
        }
        FilterTabsView filterTabsView = this.filterTabsView;
        if (filterTabsView != null && filterTabsView.isEditing()) {
            return false;
        }
        if (motionEvent != null && motionEvent.getY() < this.actionBar.getMeasuredHeight()) {
            return true;
        }
        FilterTabsView filterTabsView2 = this.filterTabsView;
        boolean z2 = filterTabsView2 == null || filterTabsView2.getTabsCount() < 2 || this.filterTabsView.getCurrentTabId() == this.filterTabsView.getFirstTabId();
        FilterTabsView filterTabsView3 = this.filterTabsView;
        boolean z3 = filterTabsView3 == null || filterTabsView3.getTabsCount() < 2 || this.filterTabsView.getCurrentTabId() == this.filterTabsView.getLastTabId();
        FilterTabsView filterTabsView4 = this.filterTabsView;
        boolean z4 = filterTabsView4 != null && filterTabsView4.getTabsCount() > 1;
        int chatSwipeAction = SharedConfig.getChatSwipeAction(this.currentAccount);
        if (z && z4 && z3 && !z2 && chatSwipeAction != 5 && isTouchInsideCurrentDialogsList(motionEvent)) {
            return false;
        }
        return z ? z3 && !z2 : z2;
    }

    public void openArchivedChatsFromMainMenu() {
        Bundle bundle = new Bundle();
        bundle.putInt("folderId", 1);
        bundle.putBoolean("onlySelect", this.onlySelect);
        DialogsActivity dialogsActivity = new DialogsActivity(bundle);
        dialogsActivity.setDelegate(this.delegate);
        presentFragment(dialogsActivity, this.onlySelect);
    }

    private void showItemOptions() {
        boolean zIsCurrentThemeDark;
        final ItemOptions itemOptionsMakeOptions = ItemOptions.makeOptions((BaseFragment) this, (View) this.optionsItem, true);
        itemOptionsMakeOptions.setDimAlpha(8);
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        if (safeLastFragment == null) {
            safeLastFragment = this;
        }
        MainMenuHelper.MenuContext menuContextCreateMenuContext = MainMenuHelper.createMenuContext(this.currentAccount, this, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda99
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.openArchivedChatsFromMainMenu();
            }
        }, MainMenuHelper.createPluginContextData(this.currentAccount, safeLastFragment));
        if (!isArchive()) {
            Theme.ResourcesProvider resourcesProvider = this.resourceProvider;
            if (resourcesProvider != null) {
                zIsCurrentThemeDark = resourcesProvider.isDark();
            } else {
                zIsCurrentThemeDark = Theme.isCurrentThemeDark();
            }
            itemOptionsMakeOptions.add(zIsCurrentThemeDark ? C2797R.drawable.menu_day_mode_24 : C2797R.drawable.menu_night_mode_24, LocaleController.getString(zIsCurrentThemeDark ? C2797R.string.SwitchThemeToDay : C2797R.string.SwitchThemeToNight), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda100
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showItemOptions$160();
                }
            });
            itemOptionsMakeOptions.addGap();
            MainMenuHelper.addConfiguredItemOptions(itemOptionsMakeOptions, menuContextCreateMenuContext);
            ApplicationLoader applicationLoader = ApplicationLoader.applicationLoaderInstance;
            if (applicationLoader != null) {
                applicationLoader.addItemOptions(itemOptionsMakeOptions);
            }
            ActionBarMenuSubItem actionBarMenuSubItem = this.proxyMenuSubItem;
            if (actionBarMenuSubItem != null) {
                actionBarMenuSubItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda101
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showItemOptions$161(itemOptionsMakeOptions, view);
                    }
                });
                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                sharedPreferences.getString("proxy_ip", _UrlKt.FRAGMENT_ENCODE_SET);
                sharedPreferences.getBoolean("proxy_enabled", false);
                if (!SharedConfig.proxyList.isEmpty()) {
                    itemOptionsMakeOptions.addGap();
                    itemOptionsMakeOptions.add(this.proxyMenuSubItem);
                    this.proxyDrawable.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_actionBarDefaultSubmenuItemIcon), PorterDuff.Mode.SRC_IN));
                }
            }
        } else {
            itemOptionsMakeOptions.add(C2797R.drawable.msg_customize, LocaleController.getString(C2797R.string.ArchiveSettings), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda102
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showItemOptions$162();
                }
            });
            itemOptionsMakeOptions.add(C2797R.drawable.msg_help, LocaleController.getString(C2797R.string.HowDoesItWork), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda103
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.showArchiveHelp();
                }
            });
        }
        itemOptionsMakeOptions.setSwipebackGravity(true, false);
        itemOptionsMakeOptions.show();
        itemOptionsMakeOptions.setTranslationY(-AndroidUtilities.m1036dp(64.0f));
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$showItemOptions$160() {
        /*
            r6 = this;
            boolean r0 = org.telegram.p035ui.DialogsActivity.switchingTheme
            if (r0 == 0) goto L5
            return
        L5:
            r0 = 1
            org.telegram.p035ui.DialogsActivity.switchingTheme = r0
            android.content.Context r0 = org.telegram.messenger.ApplicationLoader.applicationContext
            java.lang.String r1 = "themeconfig"
            r2 = 0
            android.content.SharedPreferences r0 = r0.getSharedPreferences(r1, r2)
            java.lang.String r1 = "lastDayTheme"
            java.lang.String r2 = "Blue"
            java.lang.String r1 = r0.getString(r1, r2)
            org.telegram.ui.ActionBar.Theme$ThemeInfo r3 = org.telegram.p035ui.ActionBar.Theme.getTheme(r1)
            if (r3 == 0) goto L2a
            org.telegram.ui.ActionBar.Theme$ThemeInfo r3 = org.telegram.p035ui.ActionBar.Theme.getTheme(r1)
            boolean r3 = r3.isDark()
            if (r3 == 0) goto L2b
        L2a:
            r1 = r2
        L2b:
            java.lang.String r3 = "lastDarkTheme"
            java.lang.String r4 = "Dark Blue"
            java.lang.String r0 = r0.getString(r3, r4)
            org.telegram.ui.ActionBar.Theme$ThemeInfo r3 = org.telegram.p035ui.ActionBar.Theme.getTheme(r0)
            if (r3 == 0) goto L43
            org.telegram.ui.ActionBar.Theme$ThemeInfo r3 = org.telegram.p035ui.ActionBar.Theme.getTheme(r0)
            boolean r3 = r3.isDark()
            if (r3 != 0) goto L44
        L43:
            r0 = r4
        L44:
            org.telegram.ui.ActionBar.Theme$ThemeInfo r3 = org.telegram.p035ui.ActionBar.Theme.getActiveTheme()
            boolean r5 = r1.equals(r0)
            if (r5 == 0) goto L67
            boolean r5 = r3.isDark()
            if (r5 != 0) goto L65
            boolean r5 = r1.equals(r4)
            if (r5 != 0) goto L65
            java.lang.String r5 = "Night"
            boolean r5 = r1.equals(r5)
            if (r5 == 0) goto L63
            goto L65
        L63:
            r2 = r1
            goto L69
        L65:
            r4 = r0
            goto L69
        L67:
            r4 = r0
            goto L63
        L69:
            java.lang.String r0 = r3.getKey()
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L78
            org.telegram.ui.ActionBar.Theme$ThemeInfo r1 = org.telegram.p035ui.ActionBar.Theme.getTheme(r4)
            goto L7c
        L78:
            org.telegram.ui.ActionBar.Theme$ThemeInfo r1 = org.telegram.p035ui.ActionBar.Theme.getTheme(r2)
        L7c:
            r6.switchTheme(r1, r0)
            org.telegram.ui.Components.BulletinFactory r0 = org.telegram.p035ui.Components.BulletinFactory.m1143of(r6)
            org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda131 r1 = new org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda131
            r1.<init>()
            org.telegram.p035ui.ActionBar.Theme.turnOffAutoNight(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.lambda$showItemOptions$160():void");
    }

    public /* synthetic */ void lambda$showItemOptions$159() {
        presentFragment(new ThemeActivity(1));
    }

    public /* synthetic */ void lambda$showItemOptions$161(ItemOptions itemOptions, View view) {
        itemOptions.dismiss();
        presentFragment(new ProxyListActivity());
    }

    public /* synthetic */ void lambda$showItemOptions$162() {
        presentFragment(new ArchiveSettingsActivity());
    }

    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        this.windowInsetsStateHolder.setInsets(windowInsetsCompat);
        this.statusBarHeight = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).top;
        this.navigationBarHeight = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
        int i = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.ime()).bottom;
        if (this.imeInsetHeight != i) {
            this.imeInsetHeight = i;
            this.fragmentView.requestLayout();
        }
        this.dialogsActivityStatusLayout.setPadding(0, this.statusBarHeight, 0, 0);
        updateFloatingButtonOffset();
        for (UndoView undoView : this.undoView) {
            if (undoView != null) {
                int currentUndoViewOffset = this.navigationBarHeight + getCurrentUndoViewOffset();
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) undoView.getLayoutParams();
                if (marginLayoutParams != null && marginLayoutParams.bottomMargin != currentUndoViewOffset) {
                    marginLayoutParams.bottomMargin = currentUndoViewOffset;
                    undoView.setLayoutParams(marginLayoutParams);
                }
            }
        }
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if (rightSlidingDialogContainer != null) {
            ViewCompat.dispatchApplyWindowInsets(rightSlidingDialogContainer, windowInsetsCompat);
        }
        return WindowInsetsCompat.CONSUMED;
    }

    @Override // me.vkryl.android.animator.FactorAnimator.Target
    public void onFactorChanged(int i, float f, float f2, FactorAnimator factorAnimator) {
        View view;
        if (i == 1) {
            checkUi_menuItems();
            checkUi_searchFieldVisibility();
            checkUi_topPanelVisible();
            checkUi_filterTabsVisible();
            checkUi_searchFiltersVisibility();
            checkUi_searchFieldStyle();
            if (!ExteraConfig.getHideDialogsSearchBar() || (view = this.fragmentView) == null) {
                return;
            }
            view.requestLayout();
            return;
        }
        if (i == 2) {
            checkUi_menuItems();
            checkUi_searchFieldVisibility();
            return;
        }
        if (i == 3) {
            checkUi_itemSpeedVisibility();
            return;
        }
        if (i == 4) {
            View view2 = this.fragmentView;
            if (view2 != null) {
                view2.invalidate();
                return;
            }
            return;
        }
        if (i == 5) {
            checkUi_itemSearchVisibility();
            return;
        }
        if (i == 6) {
            checkUi_menuItems();
            checkUi_searchFieldVisibility();
        } else {
            if (i == 7) {
                checkUi_forwardCommentFieldVisible();
                return;
            }
            if (i == 8) {
                checkUi_filterTabsVisible();
                checkUi_searchFiltersVisibility();
            } else if (i == 9) {
                checkUi_searchFiltersVisibility();
            }
        }
    }

    @Override // me.vkryl.android.animator.FactorAnimator.Target
    public void onFactorChangeFinished(int i, float f, FactorAnimator factorAnimator) {
        ActionBarMenuItem actionBarMenuItem;
        if (i != 3 || (actionBarMenuItem = this.speedItem) == null) {
            return;
        }
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) actionBarMenuItem.getIconView().getDrawable();
        if (this.animatorSpeedButtonVisible.getValue()) {
            animatedVectorDrawable.start();
            if (SharedConfig.getDevicePerformanceClass() != 0) {
                TLRPC.TL_help_premiumPromo premiumPromo = MediaDataController.getInstance(this.currentAccount).getPremiumPromo();
                String strFeatureTypeToServerString = PremiumPreviewFragment.featureTypeToServerString(2);
                if (premiumPromo != null) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= premiumPromo.video_sections.size()) {
                            i2 = -1;
                            break;
                        } else if (premiumPromo.video_sections.get(i2).equals(strFeatureTypeToServerString)) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i2 != -1) {
                        FileLoader.getInstance(this.currentAccount).loadFile(premiumPromo.videos.get(i2), premiumPromo, 3, 0);
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        animatedVectorDrawable.reset();
    }

    public void checkUi_searchFiltersVisibility() {
        if (this.searchTabsAndFiltersLayout != null) {
            float floatValue = (this.searchTabsView != null ? 1.0f : 0.0f) * this.animatorSearchVisible.getFloatValue();
            float fLerp = AndroidUtilities.lerp(0.98f, 1.0f, floatValue);
            this.searchTabsAndFiltersLayout.setScaleX(fLerp);
            this.searchTabsAndFiltersLayout.setScaleY(fLerp);
            this.searchTabsAndFiltersLayout.setAlpha(floatValue);
            this.searchTabsAndFiltersLayout.setVisibility(floatValue > 0.0f ? 0 : 8);
        }
        if (this.searchTabsView != null) {
            float floatValue2 = 1.0f - this.animatorSearchFilterTabsVisible.getFloatValue();
            this.searchTabsView.setAlpha(floatValue2);
            this.searchTabsView.setVisibility(floatValue2 > 0.0f ? 0 : 8);
        }
        if (this.filtersView != null) {
            float floatValue3 = this.animatorSearchFilterTabsVisible.getFloatValue();
            this.filtersView.setAlpha(floatValue3);
            this.filtersView.setVisibility(floatValue3 > 0.0f ? 0 : 8);
        }
    }

    private void checkUi_forwardCommentFieldVisible() {
        float floatValue = this.animatorForwardButtonVisible.getFloatValue();
        float fLerp = AndroidUtilities.lerp(0.2f, 1.0f, floatValue);
        ChatActivityEnterView.SendButton sendButton = this.writeButton;
        if (sendButton != null) {
            sendButton.setScaleX(fLerp);
            this.writeButton.setScaleY(fLerp);
            this.writeButton.setAlpha(floatValue);
            this.writeButton.setVisibility(floatValue > 0.0f ? 0 : 8);
        }
        ChatInputViewsContainer chatInputViewsContainer = this.chatInputViewsContainer;
        if (chatInputViewsContainer != null) {
            chatInputViewsContainer.setAlpha(floatValue);
            this.chatInputViewsContainer.setVisibility(floatValue > 0.0f ? 0 : 8);
            this.chatInputViewsContainer.getFadeView().setAlpha(floatValue);
            this.chatInputViewsContainer.getFadeView().setVisibility(floatValue > 0.0f ? 0 : 8);
        }
    }

    public void checkUi_topPanelVisible() {
        if (this.topPanelLayout != null) {
            float fLerp = AndroidUtilities.lerp(0.98f, 1.0f, 1.0f);
            this.topPanelLayout.setAlpha(1.0f);
            this.topPanelLayout.setScaleX(fLerp);
            this.topPanelLayout.setScaleY(fLerp);
            this.topPanelLayout.setVisibility(0);
        }
    }

    public void checkUi_searchPagesPaddings(boolean z) {
        if (this.searchViewPager == null || this.actionBar == null) {
            return;
        }
        int i = AndroidUtilities.navigationBarHeight;
        int iM1036dp = AndroidUtilities.m1036dp(this.ADDITIONAL_LIST_HEIGHT_DP) + this.actionBar.getMeasuredHeight() + (this.searchTabsView != null ? AndroidUtilities.m1036dp(50.0f) : 0);
        DialogsActivityTopPanelLayout dialogsActivityTopPanelLayout = this.topPanelLayout;
        this.searchViewPager.setPagesPadding(iM1036dp + (dialogsActivityTopPanelLayout != null ? (int) dialogsActivityTopPanelLayout.getAnimatedHeightWithPadding(AndroidUtilities.m1036dp(7.0f)) : 0), i, z);
    }

    public float getFilterTabsVisibilityFactor(boolean z) {
        return (z ? 1.0f - this.animatorSearchVisible.getFloatValue() : 1.0f) * (1.0f - getRightSlidingProgress()) * this.animatorFilterTabsVisible.getFloatValue();
    }

    public void checkUi_filterTabsVisible() {
        ViewPage viewPage;
        float filterTabsVisibilityFactor = getFilterTabsVisibilityFactor(true);
        FilterTabsView filterTabsView = this.filterTabsView;
        if (filterTabsView != null) {
            boolean z = filterTabsView.getAlpha() != filterTabsVisibilityFactor;
            float fLerp = AndroidUtilities.lerp(0.98f, 1.0f, filterTabsVisibilityFactor);
            this.filterTabsView.setAlpha(filterTabsVisibilityFactor);
            this.filterTabsView.setScaleX(fLerp);
            this.filterTabsView.setScaleY(fLerp);
            this.filterTabsView.setVisibility(filterTabsVisibilityFactor > 0.0f ? 0 : 8);
            if (z && (viewPage = this.viewPages[0]) != null) {
                viewPage.listView.requestLayout();
            }
        }
        updateContextViewPosition();
    }

    public void checkUi_mainTabsVisible() {
        View view;
        boolean z = (!BottomNavigationBar.visible() || this.searching || this.mainTabsHiddenByScroll || isParentPreviewActive() || ((view = this.blurredView) != null && view.getBackground() != null && this.blurredView.getVisibility() != 8)) ? false : true;
        this.animatorBottomTabsOffset.setValue(z, true);
        MainTabsActivityController mainTabsActivityController = this.mainTabsActivityController;
        if (mainTabsActivityController != null) {
            mainTabsActivityController.setTabsVisible(z);
        }
    }

    private boolean isParentPreviewActive() {
        INavigationLayout iNavigationLayout = this.parentLayout;
        if (iNavigationLayout != null) {
            return iNavigationLayout.isInPreviewMode() || this.parentLayout.isPreviewOpenAnimationInProgress();
        }
        return false;
    }

    public void checkUi_searchFieldVisibility() {
        if (this.fragmentSearchField == null) {
            return;
        }
        float fClamp = 1.0f - MathUtils.clamp(((-this.scrollYOffset) - getMaxScrollYOffsetWithoutSearch()) / AndroidUtilities.m1036dp(48.0f), 0.0f, 1.0f);
        float fMax = Math.max(this.progressToActionMode, this.animatorActionModeVisible.getFloatValue());
        float floatValue = this.animatorSearchVisible.getFloatValue();
        float floatValue2 = (isSupportSearch() ? 1.0f : 0.0f) * (1.0f - fMax) * (1.0f - this.animatorDoneButtonVisible.getFloatValue());
        float fMax2 = Math.max(floatValue, fClamp * (1.0f - getRightSlidingProgress())) * floatValue2;
        if (ExteraConfig.getHideDialogsSearchBar() && !this.searching) {
            fMax2 = floatValue2 * floatValue;
        }
        this.fragmentSearchField.setAlpha(fMax2);
        this.fragmentSearchField.setVisibility(fMax2 > 0.0f ? 0 : 8);
        this.animatorSearchButtonVisible.setValue(fMax2 <= 0.01f, true);
    }

    public boolean shouldPassSearchFieldTouchToActionBar(MotionEvent motionEvent) {
        if (motionEvent != null && this.fragmentSearchField != null && this.actionBar != null && !this.animatorSearchVisible.getValue() && this.fragmentSearchField.getVisibility() == 0 && this.fragmentSearchField.getAlpha() > 0.01f) {
            float y = (this.actionBar.getY() + this.actionBar.getMeasuredHeight()) - this.fragmentSearchField.getY();
            if (y > 0.0f && motionEvent.getY() <= y) {
                return true;
            }
        }
        return false;
    }

    private void checkUi_searchFieldStyle() {
        FragmentSearchField fragmentSearchField = this.fragmentSearchField;
        if (fragmentSearchField == null) {
            return;
        }
        fragmentSearchField.setBlurredBackgroundVisibility(this.animatorSearchVisible.getFloatValue());
    }

    public void checkUi_searchFieldHint() {
        String string = LocaleController.getString(getRightSlidingProgress() > 0.5f ? C2797R.string.SearchTopics : C2797R.string.SearchChats);
        this.fragmentSearchField.editText.setContentDescription(string);
        this.fragmentSearchField.editText.setHint(string);
    }

    public void checkUi_menuItems() {
        checkUi_itemBackButtonVisibility();
        checkUi_itemOptionsVisibility();
        checkUi_itemDownloadsVisibility();
        checkUi_itemSpeedVisibility();
        checkUi_itemPasscodeVisibility();
        checkUi_itemSearchVisibility();
    }

    private void checkUi_itemBackButtonVisibility() {
        float fMax;
        if (this.actionBar == null) {
            return;
        }
        float floatValue = (1.0f - this.animatorSearchVisible.getFloatValue()) * (1.0f - getRightSlidingProgress()) * (1.0f - this.animatorDoneButtonVisible.getFloatValue());
        if (this.hasMainTabs && !ExteraConfig.getNavigationDrawer()) {
            fMax = (1.0f - this.progressToActionMode) * floatValue;
        } else {
            fMax = Math.max(this.progressToActionMode, floatValue);
        }
        FragmentFloatingButton.setAnimatedVisibility(this.actionBar.getBackButton(), fMax);
    }

    private void checkUi_itemOptionsVisibility() {
        float floatValue = 1.0f - this.animatorSearchVisible.getFloatValue();
        float rightSlidingProgress = 1.0f - getRightSlidingProgress();
        float floatValue2 = 1.0f - this.animatorDoneButtonVisible.getFloatValue();
        FragmentFloatingButton.setAnimatedVisibility(this.optionsItem, floatValue * rightSlidingProgress * floatValue2 * (ExteraConfig.getNavigationDrawer() ? 0.0f : 1.0f));
    }

    public void checkUi_itemPasscodeVisibility() {
        float f = SharedConfig.passcodeHash.isEmpty() ? 0.0f : 1.0f;
        FragmentFloatingButton.setAnimatedVisibility(this.passcodeItem, f * (1.0f - this.animatorSearchVisible.getFloatValue()) * (1.0f - getRightSlidingProgress()) * (1.0f - this.animatorDoneButtonVisible.getFloatValue()));
    }

    private void checkUi_itemDownloadsVisibility() {
        float f = this.downloadsItemVisible ? 1.0f : 0.0f;
        FragmentFloatingButton.setAnimatedVisibility(this.downloadsItem, f * (1.0f - this.animatorSearchVisible.getFloatValue()) * (1.0f - getRightSlidingProgress()) * (1.0f - this.animatorDoneButtonVisible.getFloatValue()));
    }

    private void checkUi_itemSpeedVisibility() {
        float floatValue = this.animatorSearchVisible.getFloatValue();
        float rightSlidingProgress = 1.0f - getRightSlidingProgress();
        FragmentFloatingButton.setAnimatedVisibility(this.speedItem, floatValue * rightSlidingProgress * (1.0f - this.animatorDoneButtonVisible.getFloatValue()) * this.animatorSpeedButtonVisible.getFloatValue());
    }

    private void checkUi_itemSearchVisibility() {
        float f = isSupportSearch() ? 1.0f : 0.0f;
        FragmentFloatingButton.setAnimatedVisibility(this.searchItem, f * this.animatorSearchButtonVisible.getFloatValue() * (1.0f - getRightSlidingProgress()) * (1.0f - this.animatorDoneButtonVisible.getFloatValue()));
        DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
        if (dialogStoriesCell != null) {
            dialogStoriesCell.invalidate();
        }
    }

    private boolean isSupportSearch() {
        return ((isDrawerAccountPreview() && this.inPreviewMode) || this.initialDialogsType == 2) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:124:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void blur3_InvalidateBlur() {
        /*
            Method dump skipped, instruction units count: 318
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.DialogsActivity.blur3_InvalidateBlur():void");
    }

    public int calculateListViewPaddingBottom() {
        if (this.commentView != null) {
            return (int) (this.windowInsetsStateHolder.getAnimatedMaxBottomInset() + AndroidUtilities.m1036dp(9.0f) + this.chatInputViewsContainer.getInputBubbleHeight() + AndroidUtilities.m1036dp(7.0f) + AndroidUtilities.m1036dp(2.0f));
        }
        return this.navigationBarHeight + this.additionNavigationBarHeight;
    }

    @Override // org.telegram.ui.MainTabsActivity.TabFragmentDelegate
    public BlurredBackgroundSourceRenderNode getGlassSource() {
        return this.iBlur3SourceGlass;
    }

    @Override // org.telegram.ui.MainTabsActivity.TabFragmentDelegate
    public void onParentScrollToTop() {
        ViewPage viewPage;
        FilterTabsView filterTabsView;
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr != null && (viewPage = viewPageArr[0]) != null) {
            int i = (viewPage.dialogsType == 0 && hasHiddenArchive() && this.viewPages[0].archivePullViewState == 2) ? 1 : 0;
            if (this.viewPages[0].layoutManager != null && this.viewPages[0].layoutManager.findFirstVisibleItemPosition() <= i && (filterTabsView = this.filterTabsView) != null && filterTabsView.getVisibility() == 0 && !this.filterTabsView.isFirstTabSelected()) {
                this.filterTabsView.selectFirstTab();
                return;
            }
        }
        scrollToTop(true, true);
    }

    private void switchTheme(Theme.ThemeInfo themeInfo, boolean z) {
        ActionBarMenuItem actionBarMenuItem = this.optionsItem;
        if (actionBarMenuItem == null) {
            return;
        }
        int[] iArr = new int[2];
        actionBarMenuItem.getLocationInWindow(iArr);
        iArr[0] = iArr[0] + (this.optionsItem.getIconView().getMeasuredWidth() / 2);
        iArr[1] = iArr[1] + (this.optionsItem.getIconView().getMeasuredHeight() / 2);
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.needSetDayNightTheme, themeInfo, Boolean.FALSE, iArr, -1, Boolean.valueOf(z), null, null, null, Boolean.TRUE);
    }

    private boolean shouldUseDrawerActionBarLayout() {
        return ExteraConfig.getNavigationDrawer() && !this.onlySelect && this.initialDialogsType == 0 && this.folderId == 0 && this.searchString == null;
    }

    private float getDialogStoriesMenuItemsOffset() {
        if (isArchive() || shouldUseDrawerActionBarLayout()) {
            return AndroidUtilities.m1036dp(68.0f);
        }
        return AndroidUtilities.dpf2(16.66f);
    }

    private void updateDrawerButton() {
        DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
        if (dialogStoriesCell != null) {
            dialogStoriesCell.setMenuItemsOffset(getDialogStoriesMenuItemsOffset());
        }
        if (this.actionBar != null && !this.onlySelect && this.initialDialogsType == 0 && this.folderId == 0 && this.searchString == null) {
            if (ExteraConfig.getNavigationDrawer()) {
                if (this.menuDrawable == null) {
                    MenuDrawable menuDrawable = new MenuDrawable();
                    this.menuDrawable = menuDrawable;
                    menuDrawable.setRotateToBack(false);
                }
                this.actionBar.setBackButtonDrawable(this.menuDrawable);
                this.actionBar.setBackButtonContentDescription(LocaleController.getString(C2797R.string.AccDescrOpenMenu));
            } else {
                if (this.actionBar.getBackButton() != null && (this.actionBar.getBackButton().getDrawable() instanceof MenuDrawable)) {
                    this.actionBar.setBackButtonDrawable(null);
                }
                this.menuDrawable = null;
            }
            checkUi_itemOptionsVisibility();
            checkUi_itemBackButtonVisibility();
            ActionBar actionBar = this.actionBar;
            if (actionBar != null) {
                actionBar.refreshTitlePosition(true);
            }
        }
    }

    public boolean canOpenDrawer() {
        ActionBar actionBar;
        RightSlidingDialogContainer rightSlidingDialogContainer;
        if (!ExteraConfig.getNavigationDrawer() || this.onlySelect || this.folderId != 0 || this.initialDialogsType != 0 || this.searching || (((actionBar = this.actionBar) != null && actionBar.isActionModeShowed()) || this.animatorSearchVisible.getValue() || this.searchAnimator != null || (((rightSlidingDialogContainer = this.rightSlidingDialogContainer) != null && (rightSlidingDialogContainer.hasFragment() || this.rightFragmentTransitionInProgress)) || this.tabsAnimationInProgress || this.startedTracking || this.maybeStartTracking))) {
            return false;
        }
        FilterTabsView filterTabsView = this.filterTabsView;
        if (filterTabsView != null && (filterTabsView.isEditing() || this.filterTabsView.isAnimatingIndicator())) {
            return false;
        }
        FilterTabsView filterTabsView2 = this.filterTabsView;
        if (filterTabsView2 == null) {
            return true;
        }
        filterTabsView2.getVisibility();
        return true;
    }

    public boolean canOpenDrawerBySwipe(MotionEvent motionEvent) {
        FilterTabsView filterTabsView;
        View view;
        if (motionEvent != null && this.hasMainTabs && BottomNavigationBar.visible() && !this.mainTabsHiddenByScroll && (view = this.fragmentView) != null) {
            Rect rect = AndroidUtilities.rectTmp2;
            if (view.getGlobalVisibleRect(rect)) {
                int rawY = ((int) motionEvent.getRawY()) - rect.top;
                int measuredHeight = this.fragmentView.getMeasuredHeight() - this.navigationBarHeight;
                if (rawY >= measuredHeight - AndroidUtilities.m1036dp(MainTabsUiHelper.getTabsViewHeightDp()) && rawY <= measuredHeight) {
                    return false;
                }
            }
        }
        return canOpenDrawer() && ((filterTabsView = this.filterTabsView) == null || filterTabsView.getVisibility() != 0 || SharedConfig.getChatSwipeAction(this.currentAccount) != 5 || this.filterTabsView.getCurrentTabId() == this.filterTabsView.getFirstTabId());
    }

    public DrawerContainer drawerContainer() {
        if (getParentActivity() instanceof LaunchActivity) {
            return ((LaunchActivity) getParentActivity()).drawerLayoutContainer.getDrawerContainer();
        }
        return null;
    }

    public float getTopPanelAnimatedHeight() {
        return getTopPanelAnimatedHeight(AndroidUtilities.m1036dp(14.0f));
    }

    public float getTopPanelAnimatedHeight(int i) {
        DialogsActivityTopPanelLayout dialogsActivityTopPanelLayout = this.topPanelLayout;
        if (dialogsActivityTopPanelLayout != null) {
            return dialogsActivityTopPanelLayout.getAnimatedHeightWithPadding(i);
        }
        return 0.0f;
    }

    public float getTopPanelVisibility() {
        DialogsActivityTopPanelLayout dialogsActivityTopPanelLayout = this.topPanelLayout;
        if (dialogsActivityTopPanelLayout != null) {
            return dialogsActivityTopPanelLayout.getMetadata().getTotalVisibility();
        }
        return 0.0f;
    }

    public void checkInsets() {
        ChatInputViewsContainer chatInputViewsContainer = this.chatInputViewsContainer;
        if (chatInputViewsContainer != null) {
            chatInputViewsContainer.checkInsets();
        }
        checkUi_chatListViewPaddingsBottom();
        blur3_InvalidateBlur();
        checkUi_fadeView();
        ChatActivityEnterView.SendButton sendButton = this.writeButton;
        if (sendButton != null) {
            sendButton.setTranslationY(-this.windowInsetsStateHolder.getAnimatedMaxBottomInset());
        }
    }

    public void checkUi_fadeView() {
        ChatInputViewsContainer chatInputViewsContainer = this.chatInputViewsContainer;
        if (chatInputViewsContainer != null) {
            chatInputViewsContainer.setBlurredBottomHeight(this.windowInsetsStateHolder.getAnimatedMaxBottomInset() + AndroidUtilities.m1036dp(9.0f) + this.chatInputViewsContainer.getInputBubbleHeight() + AndroidUtilities.m1036dp(7.0f));
        }
    }

    public void checkUi_chatListViewPaddingsBottom() {
        if (this.viewPages == null) {
            return;
        }
        int iCalculateListViewPaddingBottom = calculateListViewPaddingBottom();
        int i = 0;
        while (true) {
            ViewPage[] viewPageArr = this.viewPages;
            if (i >= viewPageArr.length) {
                return;
            }
            ViewPage viewPage = viewPageArr[i];
            if (viewPage != null) {
                DialogsRecyclerView dialogsRecyclerView = viewPage.listView;
                dialogsRecyclerView.setPadding(0, dialogsRecyclerView.topPadding, 0, iCalculateListViewPaddingBottom);
            }
            i++;
        }
    }

    public void drawHeaderShadow(Canvas canvas, int i) {
        INavigationLayout iNavigationLayout;
        if (this.parentLayout == null || this.actionBar == null) {
            return;
        }
        float fMax = Math.max(this.animatorShadowVisible.getFloatValue(), getRightSlidingProgress());
        float f = this.searchAnimationProgress;
        float f2 = 1.0f;
        float f3 = fMax * (1.0f - f) * (1.0f - f);
        if (f3 == 0.0f) {
            return;
        }
        if (-1 >= i) {
            f2 = 0.0f;
            i = -1;
        }
        if (f2 <= 0.0f || f3 <= 0.0f || i <= 0 || (iNavigationLayout = this.parentLayout) == null) {
            return;
        }
        iNavigationLayout.drawHeaderShadow(canvas, (int) (f2 * 255.0f * f3), i);
    }
}
