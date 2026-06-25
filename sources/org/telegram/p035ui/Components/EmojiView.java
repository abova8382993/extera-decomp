package org.telegram.p035ui.Components;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.LongSparseArray;
import android.util.Property;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSmoothScrollerCustom;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.api.dto.BadgeDTO;
import com.exteragram.messenger.badges.BadgesController;
import com.exteragram.messenger.utils.system.VibratorUtils;
import com.exteragram.messenger.utils.text.LocaleUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import kotlin.CharCodeKt$$ExternalSyntheticBUOutline0;
import me.vkryl.android.animator.BoolAnimator;
import me.vkryl.android.animator.FactorAnimator;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.CompoundEmoji;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.EmojiData;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.GenericProvider;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.support.LongSparseIntArray;
import org.telegram.messenger.utils.ViewOutlineProviderImpl;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.BottomSheet;
import org.telegram.p035ui.ActionBar.SimpleTextView;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Cells.ContextLinkCell;
import org.telegram.p035ui.Cells.EmptyCell;
import org.telegram.p035ui.Cells.FeaturedStickerSetInfoCell;
import org.telegram.p035ui.Cells.StickerEmojiCell;
import org.telegram.p035ui.Cells.StickerSetGroupInfoCell;
import org.telegram.p035ui.Cells.StickerSetNameCell;
import org.telegram.p035ui.ChatActivity;
import org.telegram.p035ui.Components.Bulletin;
import org.telegram.p035ui.Components.EmojiView;
import org.telegram.p035ui.Components.ListView.RecyclerListViewWithOverlayDraw;
import org.telegram.p035ui.Components.PagerSlidingTabStrip;
import org.telegram.p035ui.Components.Premium.PremiumButtonView;
import org.telegram.p035ui.Components.RecyclerAnimationScrollHelper;
import org.telegram.p035ui.Components.RecyclerListView;
import org.telegram.p035ui.Components.ScrollSlidingTabStrip;
import org.telegram.p035ui.Components.StickerCategoriesListView;
import org.telegram.p035ui.Components.TrendingStickersLayout;
import org.telegram.p035ui.Components.UItem;
import org.telegram.p035ui.Components.blur3.BlurredBackgroundDrawableViewFactory;
import org.telegram.p035ui.Components.blur3.DownscaleScrollableNoiseSuppressor;
import org.telegram.p035ui.Components.blur3.ViewGroupPartRenderer;
import org.telegram.p035ui.Components.blur3.capture.IBlur3Capture;
import org.telegram.p035ui.Components.blur3.drawable.color.impl.BlurredBackgroundProviderImpl;
import org.telegram.p035ui.Components.blur3.source.BlurredBackgroundSourceColor;
import org.telegram.p035ui.Components.blur3.source.BlurredBackgroundSourceRenderNode;
import org.telegram.p035ui.Components.chat.ViewPositionWatcher;
import org.telegram.p035ui.Components.emojiview.FoundEmojiPacksRecyclerView;
import org.telegram.p035ui.Components.emojiview.FoundStickerPackButton;
import org.telegram.p035ui.Components.emojiview.FoundStickerPackButtonContainer;
import org.telegram.p035ui.Components.emojiview.FoundStickerPackCell;
import org.telegram.p035ui.Components.emojiview.FoundStickerPacksHeaderCell;
import org.telegram.p035ui.Components.inset.InAppKeyboardInsetView;
import org.telegram.p035ui.ContentPreviewViewer;
import org.telegram.p035ui.StickersActivity;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes7.dex */
@SuppressLint({"ViewConstructor"})
public class EmojiView extends FrameLayout implements FactorAnimator.Target, NotificationCenter.NotificationCenterDelegate, InAppKeyboardInsetView {
    private ArrayList<Tab> allTabs;
    private boolean allowAnimatedEmoji;
    private boolean allowEmojisForNonPremium;
    private View animateExpandFromButton;
    private int animateExpandFromPosition;
    private long animateExpandStartTime;
    private int animateExpandToPosition;
    private LongSparseArray<AnimatedEmojiDrawable> animatedEmojiDrawables;
    private PorterDuffColorFilter animatedEmojiTextColorFilter;
    private final BoolAnimator animatorSearchEmojiPackSelected;
    private final BoolAnimator animatorSearchStickerPackSelected;
    private ImageView backspaceButton;
    private AnimatorSet backspaceButtonAnimation;
    private boolean backspaceOnce;
    private boolean backspacePressed;
    private final IBlur3Capture blurCaptureMethod;
    private final BlurredBackgroundDrawableViewFactory blurredBackgroundDrawableFactory;
    private final BlurredBackgroundSourceColor blurredBackgroundSourceColor;
    private final BlurredBackgroundSourceRenderNode blurredBackgroundSourceRenderNode;
    private final RectF blurredRectF;
    private final ArrayList<RectF> blurredRectList;
    private int bottomInset;
    private FrameLayout bottomTabContainer;
    private View bottomTabContainerBackground;
    private final BoolAnimator bottomTabVisibility;
    private FrameLayout bulletinContainer;
    private FrameLayout bulletinContainer2;
    private Runnable checkExpandStickerTabsRunnable;
    private ChooseStickerActionTracker chooseStickerActionTracker;
    private EmojiColorPickerWindow colorPickerView;
    private ContentPreviewViewer.ContentPreviewViewerDelegate contentPreviewViewerDelegate;
    public int currentAccount;
    private int currentBackgroundType;
    private long currentChatId;
    private int currentPage;
    private ArrayList<Tab> currentTabs;
    public boolean customOutline;
    private EmojiViewDelegate delegate;
    private boolean disableStickerEditor;
    private Paint dotPaint;
    private DragListener dragListener;
    private EmojiGridAdapter emojiAdapter;
    private FoundStickerPackButton emojiAddPackButton;
    private FoundStickerPackButtonContainer emojiAddPackButtonContainer;
    boolean emojiBanned;
    public int emojiCacheType;
    private FrameLayout emojiContainer;
    private EmojiGridView emojiGridView;
    private float emojiLastX;
    private float emojiLastY;
    private GridLayoutManager emojiLayoutManager;
    private Drawable emojiLockDrawable;
    private Paint emojiLockPaint;
    private boolean emojiPackAlertOpened;
    EmojiPagesAdapter emojiPagerAdapter;
    private RecyclerAnimationScrollHelper emojiScrollHelper;
    private EmojiSearchAdapter emojiSearchAdapter;
    private SearchField emojiSearchField;
    private FoundStickerPacksHeaderCell emojiSearchHeader;
    private int emojiSize;
    private boolean emojiSmoothScrolling;
    private AnimatorSet emojiTabShadowAnimator;
    private EmojiTabsStrip emojiTabs;
    private View emojiTabsShadow;
    private String[] emojiTitles;
    private ImageViewEmoji emojiTouchedView;
    private float emojiTouchedX;
    private float emojiTouchedY;
    private ArrayList<EmojiPack> emojipacksProcessed;
    private boolean expandStickersByDragg;
    private ArrayList<Long> expandedEmojiSets;
    private final GradientDrawable fadeDrawable;
    private int favTabNum;
    private ArrayList<TLRPC.Document> favouriteStickers;
    private ArrayList<TLRPC.StickerSetCovered> featuredEmojiSets;
    private ArrayList<TLRPC.StickerSetCovered> featuredStickerSets;
    private boolean firstEmojiAttach;
    private boolean firstGifAttach;
    private boolean firstStickersAttach;
    private boolean firstTabUpdate;
    public boolean fixBottomTabContainerTranslation;
    private boolean forseMultiwindowLayout;
    private BaseFragment fragment;
    private boolean frozen;
    ArrayList<TLRPC.TL_messages_stickerSet> frozenStickerSets;
    private GifAdapter gifAdapter;
    private final Map<String, TLRPC.messages_BotResults> gifCache;
    private FrameLayout gifContainer;
    private int gifFirstEmojiTabNum;
    private RecyclerListView gifGridView;
    private Drawable[] gifIcons;
    private GifLayoutManager gifLayoutManager;
    private RecyclerListView.OnItemClickListener gifOnItemClickListener;
    private int gifRecentTabNum;
    private GifAdapter gifSearchAdapter;
    private SearchField gifSearchField;
    private GifSearchPreloader gifSearchPreloader;
    private ScrollSlidingTabStrip gifTabs;
    private int gifTrendingTabNum;
    private boolean glassDesign;
    private int groupStickerPackNum;
    private int groupStickerPackPosition;
    private TLRPC.TL_messages_stickerSet groupStickerSet;
    private boolean groupStickersHidden;
    private boolean hasChatStickers;
    private int hasRecentEmoji;
    private Runnable hideStickersBan;
    private boolean ignorePagerScroll;
    private boolean ignoreStickersScroll;
    private TLRPC.ChatFull info;
    public ArrayList<Long> installedEmojiSets;
    private LongSparseArray<TLRPC.StickerSetCovered> installingStickerSets;
    private boolean isLayout;
    public boolean isNewHeightControl;
    private ArrayList<Long> keepFeaturedDuplicate;
    private float lastBottomScrollDy;
    private int lastFadeColor;
    private int lastNotifyWidth;
    private ArrayList<String> lastRecentArray;
    private int lastRecentCount;
    private String[] lastSearchKeyboardLanguage;
    private float lastStickersX;
    private int[] location;
    private boolean mForceHideBackspaceButton;
    private boolean mForceHideSettingsButton;
    private TextView mediaBanTooltip;
    private final Paint navbarFillPaint;
    private boolean needEmojiSearch;
    private Object outlineProvider;
    private ViewPager pager;
    private boolean premiumBulletin;
    private ArrayList<TLRPC.Document> premiumStickers;
    private int premiumTabNum;
    private TLRPC.StickerSetCovered[] primaryInstallingStickerSets;
    private ArrayList<TLRPC.Document> recentGifs;
    private ArrayList<TLRPC.Document> recentStickers;
    private int recentTabNum;
    Rect rect;
    private LongSparseArray<TLRPC.StickerSetCovered> removingStickerSets;
    private final Theme.ResourcesProvider resourcesProvider;
    private final DownscaleScrollableNoiseSuppressor scrollableViewNoiseSuppressor;
    private AnimatorSet searchAnimation;
    private ImageView searchButton;
    private int searchFieldHeight;
    private Drawable searchIconDotDrawable;
    private Drawable searchIconDrawable;
    private boolean shouldDrawBackground;
    public boolean shouldDrawStickerSettings;
    public boolean shouldLightenBackground;
    private boolean showGifs;
    private boolean showLocalPremiumEmojiHint;
    private AnimatorSet showStickersBanAnimator;
    private boolean showing;
    private long shownBottomTabAfterClick;
    private FoundStickerPackButton stickerAddPackButton;
    private FoundStickerPackButtonContainer stickerAddPackButtonContainer;
    private Drawable[] stickerIcons;
    private FoundStickerPacksHeaderCell stickerSearchHeader;
    private ArrayList<TLRPC.TL_messages_stickerSet> stickerSets;
    private ImageView stickerSettingsButton;
    boolean stickersBanned;
    private AnimatorSet stickersButtonAnimation;
    private FrameLayout stickersContainer;
    private boolean stickersContainerAttached;
    private StickersGridAdapter stickersGridAdapter;
    private RecyclerListView stickersGridView;
    private GridLayoutManager stickersLayoutManager;
    private RecyclerListView.OnItemClickListener stickersOnItemClickListener;
    private RecyclerAnimationScrollHelper stickersScrollHelper;
    private SearchField stickersSearchField;
    private StickersSearchGridAdapter stickersSearchGridAdapter;
    private ScrollSlidingTabStrip stickersTab;
    private FrameLayout stickersTabContainer;
    private int stickersTabOffset;
    private Drawable[] tabIcons;
    private final int[] tabsMinusDy;
    private ObjectAnimator[] tabsYAnimators;
    private HashMap<Long, Utilities.Callback<TLRPC.TL_messages_stickerSet>> toInstall;
    private TrendingAdapter trendingAdapter;
    private TrendingAdapter trendingEmojiAdapter;
    private int trendingTabNum;
    private PagerSlidingTabStrip typeTabs;
    private Runnable updateStickersLoadedDelayed;
    private float visibleInAppKeyboardHeight;

    public interface DragListener {
        void onDrag(int i);

        void onDragCancel();

        void onDragEnd(float f);

        void onDragStart();
    }

    public static class EmojiPack {
        public ArrayList<TLRPC.Document> documents = new ArrayList<>();
        public boolean expanded;
        public boolean featured;
        public boolean forGroup;
        public boolean free;
        public int index;
        public boolean installed;
        public TLRPC.InputStickerSet needLoadSet;
        public int resId;
        public TLRPC.StickerSet set;
        public Long thumbDocumentId;
    }

    public interface EmojiViewDelegate {
        default boolean canAddCaptionToGif(TLRPC.Document document) {
            return false;
        }

        default boolean canSchedule() {
            return false;
        }

        default long getDialogId() {
            return 0L;
        }

        default float getProgressToSearchOpened() {
            return 0.0f;
        }

        default int getThreadId() {
            return 0;
        }

        default void invalidateEnterView() {
        }

        default boolean isExpanded() {
            return false;
        }

        default boolean isInScheduleMode() {
            return false;
        }

        default boolean isSearchOpened() {
            return false;
        }

        default boolean isUserSelf() {
            return false;
        }

        default void onAnimatedEmojiUnlockClick() {
        }

        default boolean onBackspace() {
            return false;
        }

        default void onClearEmojiRecent() {
        }

        void onCustomEmojiSelected(long j, TLRPC.Document document, String str, boolean z);

        default void onEmojiSelected(String str) {
        }

        default void onEmojiSettingsClick(ArrayList<TLRPC.TL_messages_stickerSet> arrayList) {
        }

        default void onGifSelected(View view, Object obj, String str, Object obj2, boolean z, int i, int i2) {
        }

        default void onGifSelectedForAddCaption(View view, Object obj, String str, Object obj2, boolean z, int i, int i2) {
        }

        default void onSearchOpenClose(int i) {
        }

        default void onShowStickerSet(TLRPC.StickerSet stickerSet, TLRPC.InputStickerSet inputStickerSet, boolean z) {
        }

        default void onStickerSelected(View view, TLRPC.Document document, String str, Object obj, MessageObject.SendAnimationData sendAnimationData, boolean z, int i, int i2) {
        }

        default void onStickerSetAdd(TLRPC.StickerSetCovered stickerSetCovered) {
        }

        default void onStickerSetRemove(TLRPC.StickerSetCovered stickerSetCovered) {
        }

        default void onStickersGroupClick(long j) {
        }

        default void onStickersSettingsClick() {
        }

        default void onTabOpened(int i) {
        }

        default void showTrendingStickersAlert(TrendingStickersLayout trendingStickersLayout) {
        }
    }

    public interface SearchRunnable extends Runnable {
        boolean isCompleted();

        boolean isLoading();

        void loadNext();
    }

    public static /* synthetic */ void $r8$lambda$QgvpfMFqNjYqAUCQmi9fZc2cUmM(View view) {
    }

    @Override // me.vkryl.android.animator.FactorAnimator.Target
    public void onFactorChangeFinished(int i, float f, FactorAnimator factorAnimator) {
    }

    public void setAllow(boolean z, boolean z2, boolean z3) {
        setAllow(true, z, z2, z3);
    }

    public void setAllow(boolean z, boolean z2, boolean z3, boolean z4) {
        this.currentTabs.clear();
        for (int i = 0; i < this.allTabs.size(); i++) {
            if (this.allTabs.get(i).type == 0 && z) {
                this.currentTabs.add(this.allTabs.get(i));
            }
            if (this.allTabs.get(i).type == 1 && z3) {
                this.currentTabs.add(this.allTabs.get(i));
            }
            if (this.allTabs.get(i).type == 2 && z2) {
                this.currentTabs.add(this.allTabs.get(i));
            }
        }
        PagerSlidingTabStrip pagerSlidingTabStrip = this.typeTabs;
        if (pagerSlidingTabStrip != null) {
            AndroidUtilities.updateViewVisibilityAnimated(pagerSlidingTabStrip, this.currentTabs.size() > 1, 1.0f, z4);
        }
        ViewPager viewPager = this.pager;
        if (viewPager != null) {
            viewPager.setAdapter(null);
            this.pager.setAdapter(this.emojiPagerAdapter);
            PagerSlidingTabStrip pagerSlidingTabStrip2 = this.typeTabs;
            if (pagerSlidingTabStrip2 != null) {
                pagerSlidingTabStrip2.setViewPager(this.pager);
            }
        }
    }

    public void allowEmojisForNonPremium(boolean z) {
        allowEmojisForNonPremium(z, z);
    }

    public void allowEmojisForNonPremium(boolean z, boolean z2) {
        this.allowEmojisForNonPremium = z;
        this.showLocalPremiumEmojiHint = z2;
    }

    public void setShouldDrawBackground(boolean z) {
        if (this.shouldDrawBackground != z) {
            this.shouldDrawBackground = z;
            updateColors();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$1 */
    public class RunnableC42861 implements Runnable {
        public RunnableC42861() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (EmojiView.this.stickersTab.isDragging()) {
                return;
            }
            EmojiView.this.expandStickersByDragg = false;
            EmojiView.this.updateStickerTabsPosition();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$2 */
    public class C42972 implements ContentPreviewViewer.ContentPreviewViewerDelegate {
        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean canEditSticker() {
            return true;
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean needCopy(TLRPC.Document document) {
            return true;
        }

        public C42972() {
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean can() {
            return (EmojiView.this.fragment == null && EmojiView.this.shouldDrawBackground) ? false : true;
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void sendSticker(TLRPC.Document document, String str, Object obj, boolean z, int i, int i2) {
            EmojiView.this.delegate.onStickerSelected(null, document, str, obj, null, z, i, 0);
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void resetTouch() {
            if (EmojiView.this.emojiGridView != null) {
                EmojiView.this.emojiGridView.clearAllTouches();
            }
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void sendEmoji(TLRPC.Document document) {
            if (EmojiView.this.fragment instanceof ChatActivity) {
                ((ChatActivity) EmojiView.this.fragment).sendAnimatedEmoji(document, true, 0);
            }
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void setAsEmojiStatus(TLRPC.Document document, Integer num) {
            TLRPC.EmojiStatus tL_emojiStatusEmpty;
            if (document == null) {
                tL_emojiStatusEmpty = new TLRPC.TL_emojiStatusEmpty();
            } else {
                TLRPC.TL_emojiStatus tL_emojiStatus = new TLRPC.TL_emojiStatus();
                tL_emojiStatus.document_id = document.f1253id;
                if (num != null) {
                    tL_emojiStatus.flags |= 1;
                    tL_emojiStatus.until = num.intValue();
                }
                tL_emojiStatusEmpty = tL_emojiStatus;
            }
            TLRPC.User currentUser = UserConfig.getInstance(UserConfig.selectedAccount).getCurrentUser();
            final TLRPC.EmojiStatus tL_emojiStatusEmpty2 = currentUser == null ? new TLRPC.TL_emojiStatusEmpty() : currentUser.emoji_status;
            MessagesController.getInstance(EmojiView.this.currentAccount).updateEmojiStatus(tL_emojiStatusEmpty);
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.EmojiView$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setAsEmojiStatus$0(tL_emojiStatusEmpty2);
                }
            };
            if (document == null) {
                Bulletin.SimpleLayout simpleLayout = new Bulletin.SimpleLayout(EmojiView.this.getContext(), EmojiView.this.resourcesProvider);
                simpleLayout.textView.setText(LocaleController.getString(C2797R.string.RemoveStatusInfo));
                simpleLayout.imageView.setImageResource(C2797R.drawable.msg_settings_premium);
                simpleLayout.imageView.setScaleX(0.8f);
                simpleLayout.imageView.setScaleY(0.8f);
                simpleLayout.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_verifiedBackground, EmojiView.this.resourcesProvider), PorterDuff.Mode.MULTIPLY));
                Bulletin.UndoButton undoButton = new Bulletin.UndoButton(EmojiView.this.getContext(), true, EmojiView.this.resourcesProvider);
                undoButton.setUndoAction(runnable);
                simpleLayout.setButton(undoButton);
                BaseFragment baseFragment = EmojiView.this.fragment;
                EmojiView emojiView = EmojiView.this;
                if (baseFragment != null) {
                    Bulletin.make(emojiView.fragment, simpleLayout, 1500).show();
                    return;
                } else {
                    Bulletin.make(emojiView.bulletinContainer, simpleLayout, 1500).show();
                    return;
                }
            }
            BaseFragment baseFragment2 = EmojiView.this.fragment;
            EmojiView emojiView2 = EmojiView.this;
            (baseFragment2 != null ? BulletinFactory.m1143of(emojiView2.fragment) : BulletinFactory.m1142of(emojiView2.bulletinContainer, EmojiView.this.resourcesProvider)).createEmojiBulletin(document, LocaleController.getString(C2797R.string.SetAsEmojiStatusInfo), LocaleController.getString(C2797R.string.UndoNoCaps), runnable).show();
        }

        public /* synthetic */ void lambda$setAsEmojiStatus$0(TLRPC.EmojiStatus emojiStatus) {
            MessagesController.getInstance(EmojiView.this.currentAccount).updateEmojiStatus(emojiStatus);
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void setAsBadge(final TLRPC.Document document) {
            if (document == null) {
                return;
            }
            BadgesController badgesController = BadgesController.INSTANCE;
            final BadgeDTO badge = badgesController.getBadge();
            final Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.EmojiView$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setAsBadge$3(badge);
                }
            };
            badgesController.updateBadge(new BadgeDTO(document.f1253id, badge != null ? badge.getText() : null), new Consumer() { // from class: org.telegram.ui.Components.EmojiView$2$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$setAsBadge$5(document, runnable, (String) obj);
                }
            });
        }

        public /* synthetic */ void lambda$setAsBadge$3(BadgeDTO badgeDTO) {
            BadgesController.INSTANCE.updateBadge(badgeDTO, new Consumer() { // from class: org.telegram.ui.Components.EmojiView$2$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$setAsBadge$2((String) obj);
                }
            });
        }

        public /* synthetic */ void lambda$setAsBadge$2(final String str) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$2$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setAsBadge$1(str);
                }
            });
        }

        public /* synthetic */ void lambda$setAsBadge$1(String str) {
            if (str == null || !str.equals("ok")) {
                BaseFragment baseFragment = EmojiView.this.fragment;
                EmojiView emojiView = EmojiView.this;
                if (baseFragment != null) {
                    BulletinFactory.m1143of(emojiView.fragment).createErrorBulletin(LocaleController.getString(C2797R.string.UnknownError)).show();
                } else {
                    BulletinFactory.m1142of(emojiView.bulletinContainer, EmojiView.this.resourcesProvider).createErrorBulletin(LocaleController.getString(C2797R.string.UnknownError)).show();
                }
            }
        }

        public /* synthetic */ void lambda$setAsBadge$5(final TLRPC.Document document, final Runnable runnable, final String str) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$2$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setAsBadge$4(str, document, runnable);
                }
            });
        }

        public /* synthetic */ void lambda$setAsBadge$4(String str, TLRPC.Document document, Runnable runnable) {
            if (str == null || !str.equals("ok")) {
                BaseFragment baseFragment = EmojiView.this.fragment;
                EmojiView emojiView = EmojiView.this;
                if (baseFragment != null) {
                    BulletinFactory.m1143of(emojiView.fragment).createErrorBulletin(LocaleController.getString(C2797R.string.UnknownError)).show();
                    return;
                } else {
                    BulletinFactory.m1142of(emojiView.bulletinContainer, EmojiView.this.resourcesProvider).createErrorBulletin(LocaleController.getString(C2797R.string.UnknownError)).show();
                    return;
                }
            }
            BaseFragment baseFragment2 = EmojiView.this.fragment;
            EmojiView emojiView2 = EmojiView.this;
            if (baseFragment2 != null) {
                BulletinFactory.m1143of(emojiView2.fragment).createEmojiBulletin(document, LocaleController.getString(C2797R.string.SetAsBadgeStatusInfo), LocaleController.getString(C2797R.string.UndoNoCaps), runnable).show();
            } else {
                BulletinFactory.m1142of(emojiView2.bulletinContainer, EmojiView.this.resourcesProvider).createEmojiBulletin(document, LocaleController.getString(C2797R.string.SetAsBadgeStatusInfo), LocaleController.getString(C2797R.string.UndoNoCaps), runnable).show();
            }
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void copyEmoji(TLRPC.Document document) {
            SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(MessageObject.findAnimatedEmojiEmoticon(document));
            spannableStringBuilderValueOf.setSpan(new AnimatedEmojiSpan(document, (Paint.FontMetricsInt) null), 0, spannableStringBuilderValueOf.length(), 33);
            if (AndroidUtilities.addToClipboard(spannableStringBuilderValueOf)) {
                BaseFragment baseFragment = EmojiView.this.fragment;
                EmojiView emojiView = EmojiView.this;
                (baseFragment != null ? BulletinFactory.m1143of(emojiView.fragment) : BulletinFactory.m1142of(emojiView.bulletinContainer, EmojiView.this.resourcesProvider)).createCopyBulletin(LocaleController.getString(C2797R.string.EmojiCopied)).show();
            }
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean needRemoveFromRecent(TLRPC.Document document) {
            if (document == null) {
                return false;
            }
            ArrayList<String> arrayList = Emoji.recentEmoji;
            StringBuilder sb = new StringBuilder("animated_");
            sb.append(document.f1253id);
            return arrayList.contains(sb.toString());
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void removeFromRecent(TLRPC.Document document) {
            if (document != null) {
                Emoji.removeRecentEmoji("animated_" + document.f1253id);
                if (EmojiView.this.emojiAdapter != null) {
                    EmojiView.this.emojiAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public Boolean canSetAsStatus(TLRPC.Document document) {
            TLRPC.User currentUser;
            if (!UserConfig.getInstance(UserConfig.selectedAccount).isPremium() || (currentUser = UserConfig.getInstance(UserConfig.selectedAccount).getCurrentUser()) == null) {
                return null;
            }
            Long emojiStatusDocumentId = UserObject.getEmojiStatusDocumentId(currentUser);
            return Boolean.valueOf(document != null && (emojiStatusDocumentId == null || emojiStatusDocumentId.longValue() != document.f1253id));
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean needSend(int i) {
            if (i != 2) {
                return true;
            }
            if ((EmojiView.this.fragment instanceof ChatActivity) && ((ChatActivity) EmojiView.this.fragment).canSendMessage()) {
                return UserConfig.getInstance(UserConfig.selectedAccount).isPremium() || (((ChatActivity) EmojiView.this.fragment).getCurrentUser() != null && UserObject.isUserSelf(((ChatActivity) EmojiView.this.fragment).getCurrentUser())) || EmojiView.this.allowEmojisForNonPremium;
            }
            return false;
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void editSticker(TLRPC.Document document) {
            TLRPC.InputStickerSet inputStickerSet;
            int i = 0;
            while (true) {
                if (i >= document.attributes.size()) {
                    inputStickerSet = null;
                    break;
                }
                TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
                if ((documentAttribute instanceof TLRPC.TL_documentAttributeSticker) && (inputStickerSet = documentAttribute.stickerset) != null) {
                    break;
                } else {
                    i++;
                }
            }
            StickersAlert.editSticker(EmojiView.this.fragment, MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSet(inputStickerSet, true), document);
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean canSchedule() {
            return EmojiView.this.delegate.canSchedule();
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean isInScheduleMode() {
            return EmojiView.this.delegate.isInScheduleMode();
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void openSet(TLRPC.InputStickerSet inputStickerSet, boolean z) {
            if (inputStickerSet == null) {
                return;
            }
            EmojiView.this.delegate.onShowStickerSet(null, inputStickerSet, false);
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean needShowEmojiSet(TLRPC.Document document) {
            return (document == null || MessageObject.getInputStickerSet(document) == null) ? false : true;
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void showEmojiSet(TLRPC.Document document) {
            EmojiView.this.openEmojiPackAlert(MessageObject.getInputStickerSet(document), document);
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void sendGif(Object obj, Object obj2, boolean z, int i, int i2) {
            RecyclerView.Adapter adapter = EmojiView.this.gifGridView.getAdapter();
            GifAdapter gifAdapter = EmojiView.this.gifAdapter;
            EmojiView emojiView = EmojiView.this;
            if (adapter == gifAdapter) {
                emojiView.delegate.onGifSelected(null, obj, null, obj2, z, i, i2);
            } else if (emojiView.gifGridView.getAdapter() == EmojiView.this.gifSearchAdapter) {
                EmojiView.this.delegate.onGifSelected(null, obj, null, obj2, z, i, i2);
            }
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean canAddCaption(TLRPC.Document document) {
            return EmojiView.this.delegate.canAddCaptionToGif(document);
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void addCaptionToGif(Object obj, Object obj2, boolean z, int i, int i2) {
            if (EmojiView.this.gifGridView.getAdapter() == EmojiView.this.gifAdapter || EmojiView.this.gifGridView.getAdapter() == EmojiView.this.gifSearchAdapter) {
                EmojiView.this.delegate.onGifSelectedForAddCaption(null, obj, null, obj2, z, i, i2);
            }
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void gifAddedOrDeleted() {
            EmojiView.this.updateRecentGifs();
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public long getDialogId() {
            return EmojiView.this.delegate.getDialogId();
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public String getQuery(boolean z) {
            EmojiView emojiView = EmojiView.this;
            if (z) {
                if (emojiView.gifGridView.getAdapter() == EmojiView.this.gifSearchAdapter) {
                    return EmojiView.this.gifSearchAdapter.lastSearchImageString;
                }
                return null;
            }
            if (emojiView.emojiGridView.getAdapter() == EmojiView.this.emojiSearchAdapter) {
                return EmojiView.this.emojiSearchAdapter.lastSearchEmojiString;
            }
            return null;
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void deleteSticker(TLRPC.Document document) {
            TLRPC.TL_stickers_removeStickerFromSet tL_stickers_removeStickerFromSet = new TLRPC.TL_stickers_removeStickerFromSet();
            tL_stickers_removeStickerFromSet.sticker = MediaDataController.getInputStickerSetItem(document, _UrlKt.FRAGMENT_ENCODE_SET).document;
            ConnectionsManager.getInstance(EmojiView.this.currentAccount).sendRequest(tL_stickers_removeStickerFromSet, new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$2$$ExternalSyntheticLambda3
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$deleteSticker$7(tLObject, tL_error);
                }
            });
        }

        public /* synthetic */ void lambda$deleteSticker$7(final TLObject tLObject, TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$2$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$deleteSticker$6(tLObject);
                }
            });
        }

        public /* synthetic */ void lambda$deleteSticker$6(TLObject tLObject) {
            if (tLObject instanceof TLRPC.TL_messages_stickerSet) {
                TLRPC.TL_messages_stickerSet tL_messages_stickerSet = (TLRPC.TL_messages_stickerSet) tLObject;
                MediaDataController.getInstance(EmojiView.this.currentAccount).putStickerSet(tL_messages_stickerSet);
                MediaDataController.getInstance(EmojiView.this.currentAccount).replaceStickerSet(tL_messages_stickerSet);
            }
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        SearchField searchField = this.stickersSearchField;
        if (searchField != null) {
            searchField.searchEditText.setEnabled(z);
        }
        SearchField searchField2 = this.gifSearchField;
        if (searchField2 != null) {
            searchField2.searchEditText.setEnabled(z);
        }
        SearchField searchField3 = this.emojiSearchField;
        if (searchField3 != null) {
            searchField3.searchEditText.setEnabled(z);
        }
    }

    public class SearchField extends FrameLayout implements FactorAnimator.Target {
        private final BoolAnimator animatorShadowVisibility;
        private View backgroundView;
        private FrameLayout box;
        private StickerCategoriesListView categoriesListView;
        private ImageView clear;
        private Runnable delayedToggle;
        private FrameLayout inputBox;
        private View inputBoxGradient;
        private float inputBoxGradientAlpha;
        ValueAnimator inputBoxGradientAnimator;
        private boolean inputBoxShown;
        private boolean isprogress;
        private StickerCategoriesListView.EmojiCategory recent;
        private EditTextBoldCursor searchEditText;
        private ImageView searchImageView;
        private SearchStateDrawable searchStateDrawable;
        private View shadowView;
        private StickerCategoriesListView.EmojiCategory trending;
        private int type;

        @SuppressLint({"ClickableViewAccessibility"})
        public SearchField(Context context, int i) {
            super(context);
            this.animatorShadowVisibility = new BoolAnimator(0, this, CubicBezierInterpolator.EASE_OUT, 200L);
            this.inputBoxShown = false;
            this.type = i;
            View view = new View(context);
            this.shadowView = view;
            view.setVisibility(4);
            this.shadowView.setBackgroundColor(EmojiView.this.getThemedColor(Theme.key_chat_emojiPanelShadowLine));
            addView(this.shadowView, new FrameLayout.LayoutParams(-1, AndroidUtilities.getShadowHeight(), 83));
            this.backgroundView = new View(context);
            if (EmojiView.this.shouldDrawBackground) {
                this.backgroundView.setBackgroundColor(EmojiView.this.getThemedColor(Theme.key_chat_emojiPanelBackground));
            }
            addView(this.backgroundView, new FrameLayout.LayoutParams(-1, EmojiView.this.searchFieldHeight));
            FrameLayout frameLayout = new FrameLayout(context);
            this.box = frameLayout;
            frameLayout.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1036dp(18.0f), EmojiView.this.glassDesign ? EmojiView.this.getGlassIconColor(0.06f) : EmojiView.this.getThemedColor(Theme.key_chat_emojiSearchBackground)));
            this.box.setClipToOutline(true);
            this.box.setOutlineProvider(ViewOutlineProviderImpl.boundsWithPaddingRoundRect(0, AndroidUtilities.m1036dp(18.0f)));
            FrameLayout frameLayout2 = this.box;
            if (i == 2) {
                addView(frameLayout2, LayoutHelper.createFrame(-1, 36.0f, 119, 10.0f, 8.0f, 10.0f, 8.0f));
            } else {
                addView(frameLayout2, LayoutHelper.createFrame(-1, 36.0f, 119, 10.0f, 6.0f, 10.0f, 8.0f));
            }
            C43341 c43341 = new FrameLayout(context) { // from class: org.telegram.ui.Components.EmojiView.SearchField.1
                Paint fadePaint;
                final /* synthetic */ EmojiView val$this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C43341(Context context2, EmojiView emojiView) {
                    super(context2);
                    emojiView = emojiView;
                }

                @Override // android.view.ViewGroup, android.view.View
                public void dispatchDraw(Canvas canvas) {
                    if (!EmojiView.this.shouldDrawBackground && SearchField.this.inputBoxGradientAlpha > 0.0f) {
                        if (this.fadePaint == null) {
                            Paint paint = new Paint();
                            this.fadePaint = paint;
                            paint.setShader(new LinearGradient(0.0f, 0.0f, AndroidUtilities.m1036dp(18.0f), 0.0f, new int[]{-1, 0}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP));
                            this.fadePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                        }
                        canvas.saveLayerAlpha(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), 255, 31);
                        super.dispatchDraw(canvas);
                        this.fadePaint.setAlpha((int) (SearchField.this.inputBoxGradientAlpha * 255.0f));
                        canvas.drawRect(0.0f, 0.0f, AndroidUtilities.m1036dp(18.0f), getMeasuredHeight(), this.fadePaint);
                        canvas.restore();
                        return;
                    }
                    super.dispatchDraw(canvas);
                }
            };
            this.inputBox = c43341;
            this.box.addView(c43341, LayoutHelper.createFrame(-1, 40.0f, 51, 38.0f, 0.0f, 0.0f, 0.0f));
            this.searchImageView = new ImageView(context2);
            SearchStateDrawable searchStateDrawable = new SearchStateDrawable();
            this.searchStateDrawable = searchStateDrawable;
            searchStateDrawable.setIconState(0, false);
            this.searchStateDrawable.setColor(EmojiView.this.glassDesign ? EmojiView.this.getGlassIconColor(0.4f) : EmojiView.this.getThemedColor(Theme.key_chat_emojiSearchIcon));
            ImageView imageView = this.searchImageView;
            ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
            imageView.setScaleType(scaleType);
            this.searchImageView.setImageDrawable(this.searchStateDrawable);
            this.searchImageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$SearchField$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$new$0(view2);
                }
            });
            this.box.addView(this.searchImageView, LayoutHelper.createFrame(36, 36, 51));
            C43352 c43352 = new EditTextBoldCursor(context2) { // from class: org.telegram.ui.Components.EmojiView.SearchField.2
                final /* synthetic */ EmojiView val$this$0;
                final /* synthetic */ int val$type;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C43352(Context context2, EmojiView emojiView, int i2) {
                    super(context2);
                    emojiView = emojiView;
                    i = i2;
                }

                @Override // org.telegram.p035ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
                public boolean onTouchEvent(MotionEvent motionEvent) {
                    if (!SearchField.this.searchEditText.isEnabled()) {
                        return super.onTouchEvent(motionEvent);
                    }
                    if (motionEvent.getAction() == 0) {
                        if (!EmojiView.this.delegate.isSearchOpened()) {
                            SearchField searchField = SearchField.this;
                            EmojiView.this.openSearch(searchField);
                        }
                        EmojiView.this.delegate.onSearchOpenClose(i == 1 ? 2 : 1);
                        SearchField.this.searchEditText.requestFocus();
                        AndroidUtilities.showKeyboard(SearchField.this.searchEditText);
                    }
                    return super.onTouchEvent(motionEvent);
                }
            };
            this.searchEditText = c43352;
            c43352.setTextSize(1, 16.0f);
            this.searchEditText.setHintTextColor(EmojiView.this.glassDesign ? EmojiView.this.getGlassIconColor(0.45f) : EmojiView.this.getThemedColor(Theme.key_chat_emojiSearchIcon));
            this.searchEditText.setTextColor(EmojiView.this.glassDesign ? EmojiView.this.getGlassIconColor(0.8f) : EmojiView.this.getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
            this.searchEditText.setBackgroundDrawable(null);
            this.searchEditText.setPadding(0, 0, 0, 0);
            this.searchEditText.setMaxLines(1);
            this.searchEditText.setLines(1);
            this.searchEditText.setSingleLine(true);
            this.searchEditText.setImeOptions(268435459);
            this.searchEditText.setHint(LocaleController.getString(C2797R.string.Search));
            this.searchEditText.setCursorColor(EmojiView.this.getThemedColor(Theme.key_featuredStickers_addedIcon));
            this.searchEditText.setCursorSize(AndroidUtilities.m1036dp(20.0f));
            this.searchEditText.setCursorWidth(1.5f);
            this.searchEditText.setTranslationY(AndroidUtilities.m1036dp(-2.0f));
            this.inputBox.addView(this.searchEditText, LayoutHelper.createFrame(-1, 40.0f, 51, 0.0f, 0.0f, 28.0f, 0.0f));
            this.searchEditText.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.Components.EmojiView.SearchField.3
                final /* synthetic */ EmojiView val$this$0;

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                public C43363(EmojiView emojiView) {
                    emojiView = emojiView;
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    SearchField.this.updateButton();
                    String string = SearchField.this.searchEditText.getText().toString();
                    SearchField.this.search(string, true);
                    if (SearchField.this.categoriesListView != null) {
                        SearchField.this.categoriesListView.selectCategory((StickerCategoriesListView.EmojiCategory) null);
                        SearchField.this.categoriesListView.updateCategoriesShown(TextUtils.isEmpty(string), true);
                    }
                    SearchField.this.toggleClear(!TextUtils.isEmpty(string));
                    if (SearchField.this.searchEditText != null) {
                        SearchField.this.searchEditText.clearAnimation();
                        SearchField.this.searchEditText.animate().translationX(0.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
                    }
                    SearchField.this.showInputBoxGradient(false);
                }
            });
            if (EmojiView.this.shouldDrawBackground) {
                this.inputBoxGradient = new View(context2);
                Drawable drawableMutate = context2.getResources().getDrawable(C2797R.drawable.gradient_right).mutate();
                drawableMutate.setColorFilter(new PorterDuffColorFilter(Theme.blendOver(EmojiView.this.getThemedColor(Theme.key_chat_emojiPanelBackground), EmojiView.this.getThemedColor(Theme.key_chat_emojiSearchBackground)), PorterDuff.Mode.MULTIPLY));
                this.inputBoxGradient.setBackground(drawableMutate);
                this.inputBoxGradient.setAlpha(0.0f);
                this.inputBox.addView(this.inputBoxGradient, LayoutHelper.createFrame(18, -1, 3));
            }
            ImageView imageView2 = new ImageView(context2);
            this.clear = imageView2;
            imageView2.setScaleType(scaleType);
            this.clear.setImageDrawable(new CloseProgressDrawable2(1.25f) { // from class: org.telegram.ui.Components.EmojiView.SearchField.4
                final /* synthetic */ EmojiView val$this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C43374(float f, EmojiView emojiView) {
                    super(f);
                    emojiView = emojiView;
                    setSide(AndroidUtilities.m1036dp(7.0f));
                }

                @Override // org.telegram.p035ui.Components.CloseProgressDrawable2
                public int getCurrentColor() {
                    return Theme.getColor(Theme.key_chat_emojiSearchIcon, EmojiView.this.resourcesProvider);
                }
            });
            this.clear.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_listSelector, EmojiView.this.resourcesProvider), 1, AndroidUtilities.m1036dp(15.0f)));
            this.clear.setAlpha(0.0f);
            this.clear.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$SearchField$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$new$1(view2);
                }
            });
            this.box.addView(this.clear, LayoutHelper.createFrame(36, 36, 53));
            if (i2 == 1) {
                if (!EmojiView.this.allowAnimatedEmoji) {
                    return;
                }
                if (!UserConfig.getInstance(UserConfig.selectedAccount).isPremium() && !EmojiView.this.allowEmojisForNonPremium) {
                    return;
                }
            }
            C43385 c43385 = new StickerCategoriesListView(context2, null, i2 == 0 ? 3 : 0, EmojiView.this.resourcesProvider) { // from class: org.telegram.ui.Components.EmojiView.SearchField.5
                final /* synthetic */ EmojiView val$this$0;
                final /* synthetic */ int val$type;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C43385(Context context2, StickerCategoriesListView.EmojiCategory[] emojiCategoryArr, int i2, Theme.ResourcesProvider resourcesProvider, EmojiView emojiView, int i22) {
                    super(context2, emojiCategoryArr, i2, resourcesProvider);
                    emojiView = emojiView;
                    i = i22;
                }

                @Override // org.telegram.p035ui.Components.StickerCategoriesListView
                public void selectCategory(int i2) {
                    super.selectCategory(i2);
                    SearchField searchField = SearchField.this;
                    EmojiView.this.showBottomTab(searchField.categoriesListView.getSelectedCategory() == null, true);
                    if (i == 1 && EmojiView.this.emojiTabs != null) {
                        EmojiView.this.emojiTabs.showSelected(SearchField.this.categoriesListView.getSelectedCategory() == null);
                    } else if (i == 0 && EmojiView.this.stickersTab != null) {
                        EmojiView.this.stickersTab.showSelected(SearchField.this.categoriesListView.getSelectedCategory() == null);
                    }
                    SearchField.this.updateButton();
                }

                @Override // org.telegram.p035ui.Components.StickerCategoriesListView
                public boolean isTabIconsAnimationEnabled(boolean z) {
                    return LiteMode.isEnabled(LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS);
                }
            };
            this.categoriesListView = c43385;
            c43385.isGlassDesign = EmojiView.this.glassDesign;
            this.categoriesListView.setDontOccupyWidth(((int) this.searchEditText.getPaint().measureText(((Object) this.searchEditText.getHint()) + _UrlKt.FRAGMENT_ENCODE_SET)) + AndroidUtilities.m1036dp(16.0f));
            if (EmojiView.this.shouldDrawBackground) {
                this.categoriesListView.setBackgroundColor(Theme.blendOver(EmojiView.this.getThemedColor(Theme.key_chat_emojiPanelBackground), EmojiView.this.getThemedColor(Theme.key_chat_emojiSearchBackground)));
            }
            this.categoriesListView.setOnScrollIntoOccupiedWidth(new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$SearchField$$ExternalSyntheticLambda2
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$new$2((Integer) obj);
                }
            });
            this.categoriesListView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.EmojiView$SearchField$$ExternalSyntheticLambda3
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    return this.f$0.lambda$new$3(view2, motionEvent);
                }
            });
            this.categoriesListView.setOnCategoryClick(new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$SearchField$$ExternalSyntheticLambda4
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$new$4((StickerCategoriesListView.EmojiCategory) obj);
                }
            });
            this.box.addView(this.categoriesListView, LayoutHelper.createFrame(-1, 36.0f, 51, 36.0f, 0.0f, 0.0f, 0.0f));
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$SearchField$1 */
        public class C43341 extends FrameLayout {
            Paint fadePaint;
            final /* synthetic */ EmojiView val$this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C43341(Context context2, EmojiView emojiView) {
                super(context2);
                emojiView = emojiView;
            }

            @Override // android.view.ViewGroup, android.view.View
            public void dispatchDraw(Canvas canvas) {
                if (!EmojiView.this.shouldDrawBackground && SearchField.this.inputBoxGradientAlpha > 0.0f) {
                    if (this.fadePaint == null) {
                        Paint paint = new Paint();
                        this.fadePaint = paint;
                        paint.setShader(new LinearGradient(0.0f, 0.0f, AndroidUtilities.m1036dp(18.0f), 0.0f, new int[]{-1, 0}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP));
                        this.fadePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                    }
                    canvas.saveLayerAlpha(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), 255, 31);
                    super.dispatchDraw(canvas);
                    this.fadePaint.setAlpha((int) (SearchField.this.inputBoxGradientAlpha * 255.0f));
                    canvas.drawRect(0.0f, 0.0f, AndroidUtilities.m1036dp(18.0f), getMeasuredHeight(), this.fadePaint);
                    canvas.restore();
                    return;
                }
                super.dispatchDraw(canvas);
            }
        }

        public /* synthetic */ void lambda$new$0(View view) {
            if (this.searchStateDrawable.getIconState() == 1) {
                this.searchEditText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
                search(null, false);
                StickerCategoriesListView stickerCategoriesListView = this.categoriesListView;
                if (stickerCategoriesListView != null) {
                    stickerCategoriesListView.scrollToStart();
                    this.categoriesListView.selectCategory((StickerCategoriesListView.EmojiCategory) null);
                    this.categoriesListView.updateCategoriesShown(true, true);
                }
                toggleClear(false);
                EditTextBoldCursor editTextBoldCursor = this.searchEditText;
                if (editTextBoldCursor != null) {
                    editTextBoldCursor.clearAnimation();
                    this.searchEditText.animate().translationX(0.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
                }
                showInputBoxGradient(false);
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$SearchField$2 */
        public class C43352 extends EditTextBoldCursor {
            final /* synthetic */ EmojiView val$this$0;
            final /* synthetic */ int val$type;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C43352(Context context2, EmojiView emojiView, int i22) {
                super(context2);
                emojiView = emojiView;
                i = i22;
            }

            @Override // org.telegram.p035ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                if (!SearchField.this.searchEditText.isEnabled()) {
                    return super.onTouchEvent(motionEvent);
                }
                if (motionEvent.getAction() == 0) {
                    if (!EmojiView.this.delegate.isSearchOpened()) {
                        SearchField searchField = SearchField.this;
                        EmojiView.this.openSearch(searchField);
                    }
                    EmojiView.this.delegate.onSearchOpenClose(i == 1 ? 2 : 1);
                    SearchField.this.searchEditText.requestFocus();
                    AndroidUtilities.showKeyboard(SearchField.this.searchEditText);
                }
                return super.onTouchEvent(motionEvent);
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$SearchField$3 */
        public class C43363 implements TextWatcher {
            final /* synthetic */ EmojiView val$this$0;

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            public C43363(EmojiView emojiView) {
                emojiView = emojiView;
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                SearchField.this.updateButton();
                String string = SearchField.this.searchEditText.getText().toString();
                SearchField.this.search(string, true);
                if (SearchField.this.categoriesListView != null) {
                    SearchField.this.categoriesListView.selectCategory((StickerCategoriesListView.EmojiCategory) null);
                    SearchField.this.categoriesListView.updateCategoriesShown(TextUtils.isEmpty(string), true);
                }
                SearchField.this.toggleClear(!TextUtils.isEmpty(string));
                if (SearchField.this.searchEditText != null) {
                    SearchField.this.searchEditText.clearAnimation();
                    SearchField.this.searchEditText.animate().translationX(0.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
                }
                SearchField.this.showInputBoxGradient(false);
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$SearchField$4 */
        public class C43374 extends CloseProgressDrawable2 {
            final /* synthetic */ EmojiView val$this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C43374(float f, EmojiView emojiView) {
                super(f);
                emojiView = emojiView;
                setSide(AndroidUtilities.m1036dp(7.0f));
            }

            @Override // org.telegram.p035ui.Components.CloseProgressDrawable2
            public int getCurrentColor() {
                return Theme.getColor(Theme.key_chat_emojiSearchIcon, EmojiView.this.resourcesProvider);
            }
        }

        public /* synthetic */ void lambda$new$1(View view) {
            this.searchEditText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
            search(null, false);
            StickerCategoriesListView stickerCategoriesListView = this.categoriesListView;
            if (stickerCategoriesListView != null) {
                stickerCategoriesListView.scrollToStart();
                this.categoriesListView.selectCategory((StickerCategoriesListView.EmojiCategory) null);
                this.categoriesListView.updateCategoriesShown(true, true);
            }
            toggleClear(false);
            EditTextBoldCursor editTextBoldCursor = this.searchEditText;
            if (editTextBoldCursor != null) {
                editTextBoldCursor.clearAnimation();
                this.searchEditText.animate().translationX(0.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
            }
            showInputBoxGradient(false);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$SearchField$5 */
        public class C43385 extends StickerCategoriesListView {
            final /* synthetic */ EmojiView val$this$0;
            final /* synthetic */ int val$type;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C43385(Context context2, StickerCategoriesListView.EmojiCategory[] emojiCategoryArr, int i2, Theme.ResourcesProvider resourcesProvider, EmojiView emojiView, int i22) {
                super(context2, emojiCategoryArr, i2, resourcesProvider);
                emojiView = emojiView;
                i = i22;
            }

            @Override // org.telegram.p035ui.Components.StickerCategoriesListView
            public void selectCategory(int i2) {
                super.selectCategory(i2);
                SearchField searchField = SearchField.this;
                EmojiView.this.showBottomTab(searchField.categoriesListView.getSelectedCategory() == null, true);
                if (i == 1 && EmojiView.this.emojiTabs != null) {
                    EmojiView.this.emojiTabs.showSelected(SearchField.this.categoriesListView.getSelectedCategory() == null);
                } else if (i == 0 && EmojiView.this.stickersTab != null) {
                    EmojiView.this.stickersTab.showSelected(SearchField.this.categoriesListView.getSelectedCategory() == null);
                }
                SearchField.this.updateButton();
            }

            @Override // org.telegram.p035ui.Components.StickerCategoriesListView
            public boolean isTabIconsAnimationEnabled(boolean z) {
                return LiteMode.isEnabled(LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS);
            }
        }

        public /* synthetic */ void lambda$new$2(Integer num) {
            this.searchEditText.setTranslationX(-Math.max(0, num.intValue()));
            showInputBoxGradient(num.intValue() > 0);
            updateButton();
        }

        public /* synthetic */ boolean lambda$new$3(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                EmojiView.this.ignorePagerScroll = true;
            } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                EmojiView.this.ignorePagerScroll = false;
            }
            return false;
        }

        public /* synthetic */ void lambda$new$4(StickerCategoriesListView.EmojiCategory emojiCategory) {
            if (emojiCategory == this.recent) {
                showInputBoxGradient(false);
                this.categoriesListView.selectCategory(this.recent);
                EmojiView.this.gifSearchField.searchEditText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
                EmojiView.this.gifLayoutManager.scrollToPositionWithOffset(0, 0);
                return;
            }
            if (emojiCategory == this.trending) {
                showInputBoxGradient(false);
                EmojiView.this.gifSearchField.searchEditText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
                EmojiView.this.gifLayoutManager.scrollToPositionWithOffset(EmojiView.this.gifAdapter.trendingSectionItem, -AndroidUtilities.m1036dp(4.0f));
                this.categoriesListView.selectCategory(this.trending);
                ArrayList<String> arrayList = MessagesController.getInstance(EmojiView.this.currentAccount).gifSearchEmojies;
                if (arrayList.isEmpty()) {
                    return;
                }
                EmojiView.this.gifSearchPreloader.preload(arrayList.get(0));
                return;
            }
            if (this.categoriesListView.getSelectedCategory() == emojiCategory) {
                search(null, false);
                this.categoriesListView.selectCategory((StickerCategoriesListView.EmojiCategory) null);
            } else {
                search(emojiCategory.emojis, false);
                this.categoriesListView.selectCategory(emojiCategory);
            }
        }

        public boolean isCategorySelected() {
            StickerCategoriesListView stickerCategoriesListView = this.categoriesListView;
            return (stickerCategoriesListView == null || stickerCategoriesListView.getSelectedCategory() == null) ? false : true;
        }

        public void search(String str, boolean z) {
            int i = this.type;
            if (i == 0) {
                EmojiView.this.stickersSearchGridAdapter.search(str, z);
            } else if (i == 1) {
                EmojiView.this.emojiSearchAdapter.search(str, z);
            } else if (i == 2) {
                EmojiView.this.gifSearchAdapter.search(str, z);
            }
        }

        public void showInputBoxGradient(boolean z) {
            if (z == this.inputBoxShown) {
                return;
            }
            this.inputBoxShown = z;
            ValueAnimator valueAnimator = this.inputBoxGradientAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.inputBoxGradientAlpha, z ? 1.0f : 0.0f);
            this.inputBoxGradientAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.EmojiView$SearchField$$ExternalSyntheticLambda6
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    this.f$0.lambda$showInputBoxGradient$5(valueAnimator2);
                }
            });
            this.inputBoxGradientAnimator.setDuration(120L);
            this.inputBoxGradientAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
            this.inputBoxGradientAnimator.start();
        }

        public /* synthetic */ void lambda$showInputBoxGradient$5(ValueAnimator valueAnimator) {
            float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.inputBoxGradientAlpha = fFloatValue;
            View view = this.inputBoxGradient;
            if (view != null) {
                view.setAlpha(fFloatValue);
                return;
            }
            FrameLayout frameLayout = this.inputBox;
            if (frameLayout != null) {
                frameLayout.invalidate();
            }
        }

        public boolean isInProgress() {
            return this.isprogress;
        }

        public /* synthetic */ void lambda$toggleClear$6() {
            AndroidUtilities.updateViewShow(this.clear, true);
        }

        public void toggleClear(boolean z) {
            Runnable runnable = this.delayedToggle;
            if (!z) {
                if (runnable != null) {
                    AndroidUtilities.cancelRunOnUIThread(runnable);
                    this.delayedToggle = null;
                }
                AndroidUtilities.updateViewShow(this.clear, false);
                return;
            }
            if (runnable == null) {
                Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.Components.EmojiView$SearchField$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$toggleClear$6();
                    }
                };
                this.delayedToggle = runnable2;
                AndroidUtilities.runOnUIThread(runnable2, 340L);
            }
        }

        public void showProgress(boolean z) {
            this.isprogress = z;
            if (z) {
                this.searchStateDrawable.setIconState(2);
            } else {
                updateButton(true);
            }
        }

        public void updateButton() {
            updateButton(false);
        }

        private void updateButton(boolean z) {
            StickerCategoriesListView stickerCategoriesListView;
            StickerCategoriesListView stickerCategoriesListView2;
            if (!isInProgress() || ((this.searchEditText.length() == 0 && ((stickerCategoriesListView2 = this.categoriesListView) == null || stickerCategoriesListView2.getSelectedCategory() == null)) || z)) {
                this.searchStateDrawable.setIconState((this.searchEditText.length() > 0 || ((stickerCategoriesListView = this.categoriesListView) != null && stickerCategoriesListView.isCategoriesShown() && (this.categoriesListView.isScrolledIntoOccupiedWidth() || this.categoriesListView.getSelectedCategory() != null))) ? 1 : 0);
                this.isprogress = false;
            }
        }

        public void hideKeyboard() {
            AndroidUtilities.hideKeyboard(this.searchEditText);
        }

        public void showShadow(boolean z, boolean z2) {
            this.animatorShadowVisibility.setValue(z, z2);
        }

        @Override // me.vkryl.android.animator.FactorAnimator.Target
        public void onFactorChanged(int i, float f, float f2, FactorAnimator factorAnimator) {
            if (i == 0) {
                this.shadowView.setAlpha(f);
                this.shadowView.setVisibility(f > 0.0f ? 0 : 4);
            }
        }
    }

    public class TypedScrollListener extends RecyclerView.OnScrollListener {
        private boolean smoothScrolling;
        private final int type;

        public TypedScrollListener(int i) {
            this.type = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (recyclerView.getLayoutManager().isSmoothScrolling()) {
                this.smoothScrolling = true;
                return;
            }
            if (i == 0) {
                if (!this.smoothScrolling) {
                    EmojiView.this.animateTabsY(this.type);
                }
                if (EmojiView.this.ignoreStickersScroll) {
                    EmojiView.this.ignoreStickersScroll = false;
                }
                this.smoothScrolling = false;
                return;
            }
            if (i == 1) {
                if (EmojiView.this.ignoreStickersScroll) {
                    EmojiView.this.ignoreStickersScroll = false;
                }
                SearchField searchFieldForType = EmojiView.this.getSearchFieldForType(this.type);
                if (searchFieldForType != null) {
                    searchFieldForType.hideKeyboard();
                }
                this.smoothScrolling = false;
            }
            if (!this.smoothScrolling) {
                EmojiView.this.stopAnimatingTabsY(this.type);
            }
            if (this.type == 0) {
                if (EmojiView.this.chooseStickerActionTracker == null) {
                    EmojiView.this.createStickersChooseActionTracker();
                }
                EmojiView.this.chooseStickerActionTracker.doSomeAction();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            EmojiView.this.checkScroll(this.type);
            EmojiView.this.checkTabsY(this.type, i2);
            checkSearchFieldScroll();
            if (this.smoothScrolling) {
                return;
            }
            EmojiView.this.checkBottomTabScroll(i2);
        }

        private void checkSearchFieldScroll() {
            int i = this.type;
            if (i == 0) {
                EmojiView.this.checkStickersSearchFieldScroll(false);
            } else if (i == 1) {
                EmojiView.this.checkEmojiSearchFieldScroll(false);
            } else {
                if (i != 2) {
                    return;
                }
                EmojiView.this.checkGifSearchFieldScroll(false);
            }
        }
    }

    public class DraggableScrollSlidingTabStrip extends ScrollSlidingTabStrip {
        private float downX;
        private float downY;
        private boolean draggingHorizontally;
        private boolean draggingVertically;
        private boolean first;
        private float lastTranslateX;
        private float lastX;
        private boolean startedScroll;
        private final int touchSlop;
        private VelocityTracker vTracker;

        public DraggableScrollSlidingTabStrip(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context, resourcesProvider, EmojiView.this.glassDesign);
            this.first = true;
            this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        }

        @Override // org.telegram.p035ui.Components.ScrollSlidingTabStrip, android.widget.HorizontalScrollView, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (isDragging()) {
                return super.onInterceptTouchEvent(motionEvent);
            }
            if (getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            if (motionEvent.getAction() == 0) {
                this.draggingHorizontally = false;
                this.draggingVertically = false;
                this.downX = motionEvent.getRawX();
                this.downY = motionEvent.getRawY();
            } else if (!this.draggingVertically && !this.draggingHorizontally && EmojiView.this.dragListener != null && Math.abs(motionEvent.getRawY() - this.downY) >= this.touchSlop) {
                this.draggingVertically = true;
                this.downY = motionEvent.getRawY();
                EmojiView.this.dragListener.onDragStart();
                if (this.startedScroll) {
                    EmojiView.this.pager.endFakeDrag();
                    this.startedScroll = false;
                }
                return true;
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        @Override // org.telegram.p035ui.Components.ScrollSlidingTabStrip, android.widget.HorizontalScrollView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (isDragging()) {
                return super.onTouchEvent(motionEvent);
            }
            if (this.first) {
                this.first = false;
                this.lastX = motionEvent.getX();
            }
            if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
                EmojiView.this.lastStickersX = motionEvent.getRawX();
            }
            if (motionEvent.getAction() == 0) {
                this.draggingHorizontally = false;
                this.draggingVertically = false;
                this.downX = motionEvent.getRawX();
                this.downY = motionEvent.getRawY();
            } else if (!this.draggingVertically && !this.draggingHorizontally && EmojiView.this.dragListener != null) {
                if (Math.abs(motionEvent.getRawX() - this.downX) >= this.touchSlop && canScrollHorizontally((int) (this.downX - motionEvent.getRawX()))) {
                    this.draggingHorizontally = true;
                    AndroidUtilities.cancelRunOnUIThread(EmojiView.this.checkExpandStickerTabsRunnable);
                    EmojiView.this.expandStickersByDragg = true;
                    EmojiView.this.updateStickerTabsPosition();
                } else if (Math.abs(motionEvent.getRawY() - this.downY) >= this.touchSlop) {
                    this.draggingVertically = true;
                    this.downY = motionEvent.getRawY();
                    EmojiView.this.dragListener.onDragStart();
                    if (this.startedScroll) {
                        EmojiView.this.pager.endFakeDrag();
                        this.startedScroll = false;
                    }
                }
            }
            if (EmojiView.this.expandStickersByDragg && (motionEvent.getAction() == 1 || motionEvent.getAction() == 3)) {
                AndroidUtilities.runOnUIThread(EmojiView.this.checkExpandStickerTabsRunnable, 1500L);
            }
            if (this.draggingVertically) {
                if (this.vTracker == null) {
                    this.vTracker = VelocityTracker.obtain();
                }
                this.vTracker.addMovement(motionEvent);
                if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                    this.vTracker.computeCurrentVelocity(MediaDataController.MAX_STYLE_RUNS_COUNT);
                    float yVelocity = this.vTracker.getYVelocity();
                    this.vTracker.recycle();
                    this.vTracker = null;
                    int action = motionEvent.getAction();
                    EmojiView emojiView = EmojiView.this;
                    if (action == 1) {
                        emojiView.dragListener.onDragEnd(yVelocity);
                    } else {
                        emojiView.dragListener.onDragCancel();
                    }
                    this.first = true;
                    this.draggingHorizontally = false;
                    this.draggingVertically = false;
                } else {
                    EmojiView.this.dragListener.onDrag(Math.round(motionEvent.getRawY() - this.downY));
                }
                cancelLongPress();
                return true;
            }
            float translationX = getTranslationX();
            if (getScrollX() == 0 && translationX == 0.0f) {
                if (!this.startedScroll && this.lastX - motionEvent.getX() < 0.0f) {
                    if (EmojiView.this.pager.beginFakeDrag()) {
                        this.startedScroll = true;
                        this.lastTranslateX = getTranslationX();
                    }
                } else if (this.startedScroll && this.lastX - motionEvent.getX() > 0.0f && EmojiView.this.pager.isFakeDragging()) {
                    EmojiView.this.pager.endFakeDrag();
                    this.startedScroll = false;
                }
            }
            if (this.startedScroll) {
                motionEvent.getX();
                try {
                    this.lastTranslateX = translationX;
                } catch (Exception e) {
                    try {
                        EmojiView.this.pager.endFakeDrag();
                    } catch (Exception unused) {
                    }
                    this.startedScroll = false;
                    FileLog.m1048e(e);
                }
            }
            this.lastX = motionEvent.getX();
            if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
                this.first = true;
                this.draggingHorizontally = false;
                this.draggingVertically = false;
                if (this.startedScroll) {
                    EmojiView.this.pager.endFakeDrag();
                    this.startedScroll = false;
                }
            }
            return this.startedScroll || super.onTouchEvent(motionEvent);
        }
    }

    public void sendEmoji(final ImageViewEmoji imageViewEmoji, String str) {
        String str2;
        ImageViewEmoji imageViewEmoji2;
        final EmojiView emojiView;
        long j;
        EmojiViewDelegate emojiViewDelegate;
        if (imageViewEmoji == null) {
            return;
        }
        if (imageViewEmoji.getSpan() != null) {
            if (this.delegate != null) {
                final long j2 = imageViewEmoji.getSpan().documentId;
                if (imageViewEmoji.pack == null) {
                    imageViewEmoji.pack = findEmojiPackByDocumentId(j2);
                }
                final TLRPC.Document documentResolveEmojiDocument = resolveEmojiDocument(j2, imageViewEmoji.getSpan().document, imageViewEmoji.pack);
                boolean z = imageViewEmoji.pack != null && imageViewEmoji.pack.forGroup;
                final String strFindAnimatedEmojiEmoticon = documentResolveEmojiDocument != null ? MessageObject.findAnimatedEmojiEmoticon(documentResolveEmojiDocument) : null;
                if (MessageObject.isFreeEmoji(documentResolveEmojiDocument) || UserConfig.getInstance(this.currentAccount).isPremium() || (((emojiViewDelegate = this.delegate) != null && emojiViewDelegate.isUserSelf()) || z)) {
                    imageViewEmoji2 = imageViewEmoji;
                    emojiView = this;
                    j = j2;
                } else {
                    BaseFragment baseFragment = this.fragment;
                    BulletinFactory bulletinFactoryM1143of = baseFragment != null ? BulletinFactory.m1143of(baseFragment) : BulletinFactory.m1142of(this.bulletinContainer, this.resourcesProvider);
                    if (this.allowEmojisForNonPremium && this.showLocalPremiumEmojiHint) {
                        if (!ExteraConfig.getPreferences().getBoolean("local_premium_emoji_hint" + this.currentAccount, false)) {
                            showBottomTab(false, true);
                            bulletinFactoryM1143of.createEmojiBulletin(documentResolveEmojiDocument, LocaleController.getString(C2797R.string.LocalPremiumEmojiHint), LocaleController.getString(C2797R.string.f1162OK), new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda30
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$sendEmoji$0(j2, documentResolveEmojiDocument, strFindAnimatedEmojiEmoticon, imageViewEmoji);
                                }
                            }).show();
                            return;
                        }
                    }
                    imageViewEmoji2 = imageViewEmoji;
                    emojiView = this;
                    j = j2;
                    if (!emojiView.allowEmojisForNonPremium) {
                        emojiView.showBottomTab(false, true);
                        if (emojiView.premiumBulletin || emojiView.fragment == null) {
                            bulletinFactoryM1143of.createEmojiBulletin(documentResolveEmojiDocument, AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.UnlockPremiumEmojiHint)), LocaleController.getString(C2797R.string.PremiumMore), new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda31
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.openPremiumAnimatedEmojiFeature();
                                }
                            }).show();
                        } else {
                            bulletinFactoryM1143of.createSimpleBulletin(C2797R.raw.saved_messages, AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.UnlockPremiumEmojiHint2)), LocaleController.getString(C2797R.string.Open), new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda32
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$sendEmoji$1();
                                }
                            }).show();
                        }
                        emojiView.premiumBulletin = !emojiView.premiumBulletin;
                        return;
                    }
                }
                emojiView.shownBottomTabAfterClick = SystemClock.elapsedRealtime();
                emojiView.showBottomTab(true, true);
                emojiView.addEmojiToRecent("animated_" + j);
                emojiView.delegate.onCustomEmojiSelected(j, documentResolveEmojiDocument, strFindAnimatedEmojiEmoticon, imageViewEmoji2.isRecent);
                return;
            }
            return;
        }
        this.shownBottomTabAfterClick = SystemClock.elapsedRealtime();
        showBottomTab(true, true);
        String strAddColorToCode = str != null ? str : (String) imageViewEmoji.getTag();
        new SpannableStringBuilder().append((CharSequence) strAddColorToCode);
        if (str == null) {
            if (!imageViewEmoji.isRecent && (str2 = Emoji.emojiColor.get(strAddColorToCode)) != null) {
                strAddColorToCode = addColorToCode(strAddColorToCode, str2);
            }
            addEmojiToRecent(strAddColorToCode);
            EmojiViewDelegate emojiViewDelegate2 = this.delegate;
            if (emojiViewDelegate2 != null) {
                emojiViewDelegate2.onEmojiSelected(Emoji.fixEmoji(strAddColorToCode));
                return;
            }
            return;
        }
        EmojiViewDelegate emojiViewDelegate3 = this.delegate;
        if (emojiViewDelegate3 != null) {
            emojiViewDelegate3.onEmojiSelected(Emoji.fixEmoji(str));
        }
    }

    public /* synthetic */ void lambda$sendEmoji$0(long j, TLRPC.Document document, String str, ImageViewEmoji imageViewEmoji) {
        ExteraConfig.getPreferences().edit().putBoolean("local_premium_emoji_hint" + this.currentAccount, true).apply();
        this.shownBottomTabAfterClick = SystemClock.elapsedRealtime();
        showBottomTab(true, true);
        addEmojiToRecent("animated_" + j);
        this.delegate.onCustomEmojiSelected(j, document, str, imageViewEmoji.isRecent);
    }

    public /* synthetic */ void lambda$sendEmoji$1() {
        Bundle bundle = new Bundle();
        bundle.putLong("user_id", UserConfig.getInstance(this.currentAccount).getClientUserId());
        this.fragment.presentFragment(new C43083(bundle));
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$3 */
    public class C43083 extends ChatActivity {
        public C43083(Bundle bundle) {
            super(bundle);
        }

        @Override // org.telegram.p035ui.ChatActivity, org.telegram.p035ui.ActionBar.BaseFragment
        public void onTransitionAnimationEnd(boolean z, boolean z2) {
            ChatActivityEnterView chatActivityEnterView;
            super.onTransitionAnimationEnd(z, z2);
            if (!z || (chatActivityEnterView = this.chatActivityEnterView) == null) {
                return;
            }
            chatActivityEnterView.showEmojiView();
            this.chatActivityEnterView.postDelayed(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onTransitionAnimationEnd$0();
                }
            }, 100L);
        }

        public /* synthetic */ void lambda$onTransitionAnimationEnd$0() {
            if (this.chatActivityEnterView.getEmojiView() != null) {
                this.chatActivityEnterView.getEmojiView().scrollEmojisToAnimated();
            }
        }
    }

    public class ImageViewEmoji extends ImageView {
        ValueAnimator backAnimator;
        private ImageReceiver.BackgroundThreadDrawHolder[] backgroundThreadDrawHolder;
        public AnimatedEmojiDrawable drawable;
        public boolean ignoring;
        public ImageReceiver imageReceiver;
        private boolean isRecent;
        private EmojiPack pack;
        public int position;
        float pressedProgress;
        private AnimatedEmojiSpan span;

        public ImageViewEmoji(Context context) {
            super(context);
            this.backgroundThreadDrawHolder = new ImageReceiver.BackgroundThreadDrawHolder[2];
            setScaleType(ImageView.ScaleType.CENTER);
            setBackground(Theme.createRadSelectorDrawable(EmojiView.this.getThemedColor(Theme.key_listSelector), AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(4.0f)));
        }

        public void setImageDrawable(Drawable drawable, boolean z) {
            super.setImageDrawable(drawable);
            this.isRecent = z;
        }

        public void setSpan(AnimatedEmojiSpan animatedEmojiSpan) {
            this.span = animatedEmojiSpan;
        }

        public AnimatedEmojiSpan getSpan() {
            return this.span;
        }

        public TLRPC.Document getDocument() {
            AnimatedEmojiSpan animatedEmojiSpan = this.span;
            if (animatedEmojiSpan == null) {
                return null;
            }
            return EmojiView.this.resolveEmojiDocument(animatedEmojiSpan.getDocumentId(), this.span.document, this.pack);
        }

        @Override // android.widget.ImageView, android.view.View
        public void onMeasure(int i, int i2) {
            setMeasuredDimension(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i));
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName("android.view.View");
        }

        @Override // android.view.View
        public void setPressed(boolean z) {
            ValueAnimator valueAnimator;
            if (isPressed() != z) {
                super.setPressed(z);
                invalidate();
                if (z && (valueAnimator = this.backAnimator) != null) {
                    valueAnimator.removeAllListeners();
                    this.backAnimator.cancel();
                }
                if (z) {
                    return;
                }
                float f = this.pressedProgress;
                if (f != 0.0f) {
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, 0.0f);
                    this.backAnimator = valueAnimatorOfFloat;
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.EmojiView$ImageViewEmoji$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            this.f$0.lambda$setPressed$0(valueAnimator2);
                        }
                    });
                    this.backAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.ImageViewEmoji.1
                        public C43331() {
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            ImageViewEmoji.this.backAnimator = null;
                        }
                    });
                    this.backAnimator.setInterpolator(new OvershootInterpolator(5.0f));
                    this.backAnimator.setDuration(350L);
                    this.backAnimator.start();
                }
            }
        }

        public /* synthetic */ void lambda$setPressed$0(ValueAnimator valueAnimator) {
            this.pressedProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            invalidate();
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$ImageViewEmoji$1 */
        public class C43331 extends AnimatorListenerAdapter {
            public C43331() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                ImageViewEmoji.this.backAnimator = null;
            }
        }

        @Override // android.widget.ImageView, android.view.View
        public void onDraw(Canvas canvas) {
            if (isPressed()) {
                float f = this.pressedProgress;
                if (f != 1.0f) {
                    float fMin = f + (Math.min(40.0f, 1000.0f / AndroidUtilities.screenRefreshRate) / 100.0f);
                    this.pressedProgress = fMin;
                    this.pressedProgress = Utilities.clamp(fMin, 1.0f, 0.0f);
                    invalidate();
                }
            }
            float f2 = ((1.0f - this.pressedProgress) * 0.2f) + 0.8f;
            canvas.save();
            canvas.scale(f2, f2, getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f);
            super.onDraw(canvas);
            canvas.restore();
        }
    }

    public EmojiView(BaseFragment baseFragment, boolean z, boolean z2, boolean z3, Context context, boolean z4, TLRPC.ChatFull chatFull, ViewGroup viewGroup, boolean z5, Theme.ResourcesProvider resourcesProvider, boolean z6) {
        this(baseFragment, z, z2, z3, context, z4, chatFull, viewGroup, z5, resourcesProvider, z6, false);
    }

    public EmojiView(BaseFragment baseFragment, boolean z, boolean z2, boolean z3, Context context, boolean z4, TLRPC.ChatFull chatFull, ViewGroup viewGroup, boolean z5, final Theme.ResourcesProvider resourcesProvider, boolean z6, boolean z7) {
        int i;
        boolean z8;
        EmojiViewIA emojiViewIA;
        int i2;
        int i3;
        EmojiViewIA emojiViewIA2;
        super(context);
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
        this.animatorSearchStickerPackSelected = new BoolAnimator(0, this, cubicBezierInterpolator, 320L);
        this.animatorSearchEmojiPackSelected = new BoolAnimator(1, this, cubicBezierInterpolator, 320L);
        this.emojiCacheType = 2;
        this.allTabs = new ArrayList<>();
        this.currentTabs = new ArrayList<>();
        this.firstEmojiAttach = true;
        this.hasRecentEmoji = -1;
        this.gifSearchPreloader = new GifSearchPreloader();
        this.gifCache = new HashMap();
        this.firstGifAttach = true;
        this.gifRecentTabNum = -2;
        this.gifTrendingTabNum = -2;
        this.gifFirstEmojiTabNum = -2;
        this.shouldDrawBackground = true;
        this.shouldLightenBackground = true;
        this.firstStickersAttach = true;
        this.tabsMinusDy = new int[3];
        this.tabsYAnimators = new ObjectAnimator[3];
        this.currentAccount = UserConfig.selectedAccount;
        this.stickerSets = new ArrayList<>();
        this.recentGifs = new ArrayList<>();
        this.recentStickers = new ArrayList<>();
        this.favouriteStickers = new ArrayList<>();
        this.premiumStickers = new ArrayList<>();
        this.featuredStickerSets = new ArrayList<>();
        this.featuredEmojiSets = new ArrayList<>();
        this.keepFeaturedDuplicate = new ArrayList<>();
        this.expandedEmojiSets = new ArrayList<>();
        this.installedEmojiSets = new ArrayList<>();
        this.emojipacksProcessed = new ArrayList<>();
        this.toInstall = new HashMap<>();
        this.primaryInstallingStickerSets = new TLRPC.StickerSetCovered[10];
        this.installingStickerSets = new LongSparseArray<>();
        this.removingStickerSets = new LongSparseArray<>();
        this.location = new int[2];
        this.recentTabNum = -2;
        this.favTabNum = -2;
        this.trendingTabNum = -2;
        this.premiumTabNum = -2;
        this.currentBackgroundType = -1;
        this.checkExpandStickerTabsRunnable = new Runnable() { // from class: org.telegram.ui.Components.EmojiView.1
            public RunnableC42861() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (EmojiView.this.stickersTab.isDragging()) {
                    return;
                }
                EmojiView.this.expandStickersByDragg = false;
                EmojiView.this.updateStickerTabsPosition();
            }
        };
        this.contentPreviewViewerDelegate = new C42972();
        this.premiumBulletin = true;
        this.visibleInAppKeyboardHeight = -1.0f;
        this.animateExpandFromPosition = -1;
        this.animateExpandToPosition = -1;
        this.animateExpandStartTime = -1L;
        this.emojiPackAlertOpened = false;
        this.fixBottomTabContainerTranslation = true;
        this.rect = new Rect();
        RectF rectF = new RectF();
        this.blurredRectF = rectF;
        ArrayList<RectF> arrayList = new ArrayList<>(1);
        this.blurredRectList = arrayList;
        arrayList.add(rectF);
        this.navbarFillPaint = new Paint(1);
        this.fadeDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, null);
        this.bottomTabVisibility = new BoolAnimator(0, new FactorAnimator.Target() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda1
            @Override // me.vkryl.android.animator.FactorAnimator.Target
            public final void onFactorChanged(int i4, float f, float f2, FactorAnimator factorAnimator) {
                this.f$0.lambda$new$25(i4, f, f2, factorAnimator);
            }
        }, cubicBezierInterpolator, 380L, true);
        this.updateStickersLoadedDelayed = new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$31();
            }
        };
        this.disableStickerEditor = false;
        this.shouldDrawBackground = z5;
        this.fragment = baseFragment;
        this.allowAnimatedEmoji = z;
        this.resourcesProvider = resourcesProvider;
        this.glassDesign = z7;
        BlurredBackgroundSourceColor blurredBackgroundSourceColor = new BlurredBackgroundSourceColor();
        this.blurredBackgroundSourceColor = blurredBackgroundSourceColor;
        blurredBackgroundSourceColor.setColor(getThemedColor(Theme.key_windowBackgroundWhite));
        if (z6) {
            freeze(true);
        }
        ColorUtils.setAlphaComponent(getThemedColor(Theme.key_glass_defaultIcon), 30);
        this.searchFieldHeight = AndroidUtilities.m1036dp(50.0f);
        this.needEmojiSearch = z4;
        this.tabIcons = new Drawable[]{Theme.createEmojiIconSelectorDrawable(context, C2797R.drawable.smiles_tab_smiles, z7 ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiPanelBackspace), z7 ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_chat_emojiPanelIconSelected)), Theme.createEmojiIconSelectorDrawable(context, C2797R.drawable.smiles_tab_gif, z7 ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiPanelBackspace), z7 ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_chat_emojiPanelIconSelected)), Theme.createEmojiIconSelectorDrawable(context, C2797R.drawable.smiles_tab_stickers, z7 ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiPanelBackspace), z7 ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_chat_emojiPanelIconSelected))};
        Drawable drawableCreateEmojiIconSelectorDrawable = Theme.createEmojiIconSelectorDrawable(context, C2797R.drawable.msg_emoji_recent, z7 ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiPanelIcon), z7 ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_chat_emojiPanelIconSelected));
        Drawable drawableCreateEmojiIconSelectorDrawable2 = Theme.createEmojiIconSelectorDrawable(context, C2797R.drawable.emoji_tabs_faves, z7 ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiPanelIcon), z7 ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_chat_emojiPanelIconSelected));
        Drawable drawableCreateEmojiIconSelectorDrawable3 = Theme.createEmojiIconSelectorDrawable(context, C2797R.drawable.emoji_tabs_new3, z7 ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiPanelIcon), z7 ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_chat_emojiPanelIconSelected));
        Drawable drawableCreateEmojiIconSelectorDrawable4 = Theme.createEmojiIconSelectorDrawable(context, C2797R.drawable.emoji_tabs_new1, z7 ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiPanelIcon), z7 ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_chat_emojiPanelIconSelected));
        this.searchIconDrawable = drawableCreateEmojiIconSelectorDrawable4;
        int i4 = C2797R.drawable.emoji_tabs_new2;
        int i5 = Theme.key_chat_emojiPanelStickerPackSelectorLine;
        Drawable drawableCreateEmojiIconSelectorDrawable5 = Theme.createEmojiIconSelectorDrawable(context, i4, getThemedColor(i5), getThemedColor(i5));
        this.searchIconDotDrawable = drawableCreateEmojiIconSelectorDrawable5;
        this.stickerIcons = new Drawable[]{drawableCreateEmojiIconSelectorDrawable, drawableCreateEmojiIconSelectorDrawable2, drawableCreateEmojiIconSelectorDrawable3, new LayerDrawable(new Drawable[]{drawableCreateEmojiIconSelectorDrawable4, drawableCreateEmojiIconSelectorDrawable5})};
        this.gifIcons = new Drawable[]{Theme.createEmojiIconSelectorDrawable(context, C2797R.drawable.msg_emoji_recent, z7 ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiPanelIcon), z7 ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_chat_emojiPanelIconSelected)), Theme.createEmojiIconSelectorDrawable(context, C2797R.drawable.stickers_gifs_trending, z7 ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiPanelIcon), z7 ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_chat_emojiPanelIconSelected))};
        this.emojiTitles = new String[]{LocaleController.getString(C2797R.string.Emoji1), LocaleController.getString(C2797R.string.Emoji2), LocaleController.getString(C2797R.string.Emoji3), LocaleController.getString(C2797R.string.Emoji4), LocaleController.getString(C2797R.string.Emoji5), LocaleController.getString(C2797R.string.Emoji6), LocaleController.getString(C2797R.string.Emoji7), LocaleController.getString(C2797R.string.Emoji8)};
        this.showGifs = z3;
        this.info = chatFull;
        Paint paint = new Paint(1);
        this.dotPaint = paint;
        paint.setColor(getThemedColor(Theme.key_chat_emojiPanelNewTrending));
        this.outlineProvider = ViewOutlineProviderImpl.boundsWithPaddingFromViewAndRoundRect(AndroidUtilities.m1036dp(6.0f));
        this.emojiContainer = new FrameLayout(context) { // from class: org.telegram.ui.Components.EmojiView.4
            public C43174(Context context2) {
                super(context2);
            }

            @Override // android.view.ViewGroup
            public boolean drawChild(Canvas canvas, View view, long j) {
                if (view == EmojiView.this.emojiGridView || view == EmojiView.this.emojiSearchField) {
                    canvas.save();
                    float y = EmojiView.this.emojiTabs.getY() + EmojiView.this.emojiTabs.getMeasuredHeight() + 1.0f;
                    if (view == EmojiView.this.emojiGridView && EmojiView.this.emojiSearchField != null) {
                        y = Math.max(y, EmojiView.this.emojiSearchField.getY() + EmojiView.this.emojiSearchField.getMeasuredHeight() + 1.0f);
                    }
                    canvas.clipRect(0.0f, y - (AndroidUtilities.m1036dp(16.0f) * EmojiView.this.animatorSearchEmojiPackSelected.getFloatValue()), getMeasuredWidth(), getMeasuredHeight());
                    boolean zDrawChild = super.drawChild(canvas, view, j);
                    canvas.restore();
                    return zDrawChild;
                }
                return super.drawChild(canvas, view, j);
            }
        };
        Tab tab = new Tab();
        tab.type = 0;
        tab.view = this.emojiContainer;
        this.allTabs.add(tab);
        if (z) {
            MediaDataController.getInstance(this.currentAccount).checkStickers(5);
            MediaDataController.getInstance(this.currentAccount).checkFeaturedEmoji();
            this.animatedEmojiTextColorFilter = new PorterDuffColorFilter(getThemedColor(Theme.key_featuredStickers_addButton), PorterDuff.Mode.SRC_IN);
        }
        this.emojiGridView = new EmojiGridView(context2) { // from class: org.telegram.ui.Components.EmojiView.5
            public C43185(Context context2) {
                super(context2);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiGridView, org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (EmojiView.this.ignorePagerScroll) {
                    return false;
                }
                return super.onInterceptTouchEvent(motionEvent);
            }
        };
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDelay(0L);
        defaultItemAnimator.setAddDuration(220L);
        defaultItemAnimator.setMoveDuration(220L);
        defaultItemAnimator.setChangeDuration(160L);
        defaultItemAnimator.setMoveInterpolator(CubicBezierInterpolator.EASE_OUT);
        this.emojiGridView.setItemAnimator(defaultItemAnimator);
        this.emojiGridView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda19
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$new$2(resourcesProvider, view, motionEvent);
            }
        });
        this.emojiGridView.setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda20
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener
            public final boolean onItemClick(View view, int i6) {
                return this.f$0.lambda$new$3(view, i6);
            }
        });
        this.emojiGridView.setInstantClick(true);
        EmojiGridView emojiGridView = this.emojiGridView;
        C43196 c43196 = new GridLayoutManager(context2, 8) { // from class: org.telegram.ui.Components.EmojiView.6
            public C43196(Context context2, int i6) {
                super(context2, i6);
            }

            /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$6$1 */
            public class AnonymousClass1 extends LinearSmoothScrollerCustom {
                public AnonymousClass1(Context context, int i) {
                    super(context, i);
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScrollerCustom
                public void onEnd() {
                    EmojiView.this.emojiSmoothScrolling = false;
                }
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i6) {
                try {
                    AnonymousClass1 anonymousClass1 = new LinearSmoothScrollerCustom(recyclerView.getContext(), 2) { // from class: org.telegram.ui.Components.EmojiView.6.1
                        public AnonymousClass1(Context context2, int i7) {
                            super(context2, i7);
                        }

                        @Override // androidx.recyclerview.widget.LinearSmoothScrollerCustom
                        public void onEnd() {
                            EmojiView.this.emojiSmoothScrolling = false;
                        }
                    };
                    anonymousClass1.setTargetPosition(i6);
                    startSmoothScroll(anonymousClass1);
                } catch (Exception e) {
                    FileLog.m1048e(e);
                }
            }
        };
        this.emojiLayoutManager = c43196;
        emojiGridView.setLayoutManager(c43196);
        this.emojiGridView.setTopGlowOffset(AndroidUtilities.m1036dp(38.0f));
        this.emojiGridView.setBottomGlowOffset(AndroidUtilities.m1036dp(36.0f));
        this.emojiGridView.setPadding(AndroidUtilities.m1036dp(5.0f), AndroidUtilities.m1036dp(36.0f), AndroidUtilities.m1036dp(5.0f), AndroidUtilities.m1036dp(44.0f));
        EmojiGridView emojiGridView2 = this.emojiGridView;
        int i6 = Theme.key_chat_emojiPanelBackground;
        emojiGridView2.setGlowColor(getThemedColor(i6));
        this.emojiGridView.setSelectorType(100);
        this.emojiGridView.setSelectorDrawableColor(0);
        this.emojiGridView.setItemSelectorColorProvider(new GenericProvider() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda21
            @Override // org.telegram.messenger.GenericProvider
            public final Object provide(Object obj) {
                return EmojiView.$r8$lambda$roMuldUBeMpvL2VV1XisjntU8Zo((Integer) obj);
            }
        });
        this.emojiGridView.setClipToPadding(false);
        this.emojiLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.Components.EmojiView.7
            public C43207() {
            }

            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i7) {
                RecyclerView.Adapter adapter = EmojiView.this.emojiGridView.getAdapter();
                EmojiSearchAdapter emojiSearchAdapter = EmojiView.this.emojiSearchAdapter;
                EmojiView emojiView = EmojiView.this;
                if (adapter == emojiSearchAdapter) {
                    int itemViewType = emojiView.emojiSearchAdapter.getItemViewType(i7);
                    if (itemViewType == 1 || itemViewType == 3 || itemViewType == 2 || itemViewType == 4 || itemViewType == 5) {
                        return EmojiView.this.emojiLayoutManager.getSpanCount();
                    }
                } else if ((emojiView.needEmojiSearch && i7 == 0) || i7 == EmojiView.this.emojiAdapter.trendingRow || i7 == EmojiView.this.emojiAdapter.trendingHeaderRow || i7 == EmojiView.this.emojiAdapter.recentlyUsedHeaderRow || EmojiView.this.emojiAdapter.positionToSection.indexOfKey(i7) >= 0 || EmojiView.this.emojiAdapter.positionToUnlock.indexOfKey(i7) >= 0) {
                    return EmojiView.this.emojiLayoutManager.getSpanCount();
                }
                return 1;
            }

            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanGroupIndex(int i7, int i8) {
                return super.getSpanGroupIndex(i7, i8);
            }
        });
        EmojiGridView emojiGridView3 = this.emojiGridView;
        EmojiGridAdapter emojiGridAdapter = new EmojiGridAdapter();
        this.emojiAdapter = emojiGridAdapter;
        emojiGridView3.setAdapter(emojiGridAdapter);
        this.emojiGridView.addItemDecoration(new EmojiGridSpacing());
        this.emojiSearchAdapter = new EmojiSearchAdapter(context2);
        this.emojiContainer.addView(this.emojiGridView, LayoutHelper.createFrame(-1, -1.0f));
        RecyclerAnimationScrollHelper recyclerAnimationScrollHelper = new RecyclerAnimationScrollHelper(this.emojiGridView, this.emojiLayoutManager);
        this.emojiScrollHelper = recyclerAnimationScrollHelper;
        recyclerAnimationScrollHelper.setAnimationCallback(new RecyclerAnimationScrollHelper.AnimationCallback() { // from class: org.telegram.ui.Components.EmojiView.8
            public C43218() {
            }

            @Override // org.telegram.ui.Components.RecyclerAnimationScrollHelper.AnimationCallback
            public void onPreAnimation() {
                EmojiView.this.emojiGridView.updateEmojiDrawables();
                EmojiView.this.emojiSmoothScrolling = true;
            }

            @Override // org.telegram.ui.Components.RecyclerAnimationScrollHelper.AnimationCallback
            public void onEndAnimation() {
                EmojiView.this.emojiSmoothScrolling = false;
                EmojiView.this.emojiGridView.updateEmojiDrawables();
            }

            @Override // org.telegram.ui.Components.RecyclerAnimationScrollHelper.AnimationCallback
            public void ignoreView(View view, boolean z9) {
                if (view instanceof ImageViewEmoji) {
                    ((ImageViewEmoji) view).ignoring = z9;
                }
            }
        });
        this.emojiGridView.setOnScrollListener(new TypedScrollListener(1) { // from class: org.telegram.ui.Components.EmojiView.9
            public C43229(int i7) {
                super(i7);
            }

            @Override // org.telegram.ui.Components.EmojiView.TypedScrollListener, androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i7, int i8) {
                EmojiView.this.updateEmojiTabsPosition();
                if (Build.VERSION.SDK_INT >= 31 && EmojiView.this.scrollableViewNoiseSuppressor != null) {
                    EmojiView.this.scrollableViewNoiseSuppressor.onScrolled(i7, i8);
                    EmojiView.this.invalidateBlurCaptures();
                }
                super.onScrolled(recyclerView, i7, i8);
                if (EmojiView.this.emojiSearchAdapter == null || EmojiView.this.emojiGridView.getAdapter() != EmojiView.this.emojiSearchAdapter || EmojiView.this.emojiSearchAdapter.searchRunnable.isLoading() || EmojiView.this.emojiSearchAdapter.searchRunnable.isCompleted()) {
                    return;
                }
                if (EmojiView.this.emojiLayoutManager.findLastVisibleItemPosition() + 20 > EmojiView.this.emojiSearchAdapter.getItemCount()) {
                    SearchRunnable searchRunnable = EmojiView.this.emojiSearchAdapter.searchRunnable;
                    Objects.requireNonNull(searchRunnable);
                    AndroidUtilities.runOnUIThread(new EmojiView$19$$ExternalSyntheticLambda0(searchRunnable));
                }
            }

            @Override // org.telegram.ui.Components.EmojiView.TypedScrollListener, androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i7) {
                if (i7 == 0) {
                    EmojiView.this.emojiSmoothScrolling = false;
                }
                super.onScrollStateChanged(recyclerView, i7);
            }
        });
        this.emojiTabs = new EmojiTabsStrip(context2, resourcesProvider, true, false, true, z, 0, baseFragment != null ? new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$5();
            }
        } : null, z7) { // from class: org.telegram.ui.Components.EmojiView.10
            public C428710(Context context2, final Theme.ResourcesProvider resourcesProvider2, boolean z9, boolean z10, boolean z11, boolean z12, int i7, Runnable runnable, boolean z72) {
                super(context2, resourcesProvider2, z9, z10, z11, z12, i7, runnable, z72);
            }

            @Override // org.telegram.p035ui.Components.EmojiTabsStrip
            public boolean isInstalled(EmojiPack emojiPack) {
                return emojiPack.installed || EmojiView.this.installedEmojiSets.contains(Long.valueOf(emojiPack.set.f1280id));
            }

            @Override // org.telegram.p035ui.Components.EmojiTabsStrip
            public boolean allowEmojisForNonPremium() {
                return EmojiView.this.allowEmojisForNonPremium;
            }

            @Override // android.view.View
            public void setTranslationY(float f) {
                if (getTranslationY() != f) {
                    super.setTranslationY(f);
                    if (EmojiView.this.emojiTabsShadow != null) {
                        EmojiView.this.emojiTabsShadow.setTranslationY(f);
                    }
                    EmojiView.this.emojiContainer.invalidate();
                }
            }

            @Override // org.telegram.p035ui.Components.EmojiTabsStrip
            public boolean doIncludeFeatured() {
                return EmojiView.this.featuredEmojiSets.size() <= 0 || ((TLRPC.StickerSetCovered) EmojiView.this.featuredEmojiSets.get(0)).set == null || MessagesController.getEmojiSettings(EmojiView.this.currentAccount).getLong("emoji_featured_hidden", 0L) == ((TLRPC.StickerSetCovered) EmojiView.this.featuredEmojiSets.get(0)).set.f1280id || !UserConfig.getInstance(UserConfig.selectedAccount).isPremium();
            }

            /* JADX WARN: Removed duplicated region for block: B:82:0x00d9  */
            @Override // org.telegram.p035ui.Components.EmojiTabsStrip
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean onTabClick(int r9) {
                /*
                    Method dump skipped, instruction units count: 254
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.EmojiView.C428710.onTabClick(int):boolean");
            }

            @Override // org.telegram.p035ui.Components.EmojiTabsStrip
            public ColorFilter getEmojiColorFilter() {
                return EmojiView.this.animatedEmojiTextColorFilter;
            }
        };
        if (z4) {
            C428811 c428811 = new SearchField(context2, 1) { // from class: org.telegram.ui.Components.EmojiView.11
                public C428811(Context context2, int i7) {
                    super(context2, i7);
                }

                @Override // android.view.View
                public void setTranslationY(float f) {
                    if (f != getTranslationY()) {
                        super.setTranslationY(f);
                        EmojiView.this.emojiContainer.invalidate();
                    }
                }
            };
            this.emojiSearchField = c428811;
            i = -1;
            this.emojiContainer.addView(c428811, new FrameLayout.LayoutParams(-1, this.searchFieldHeight + AndroidUtilities.getShadowHeight()));
            this.emojiSearchField.searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.telegram.ui.Components.EmojiView.12
                public ViewOnFocusChangeListenerC428912() {
                }

                @Override // android.view.View.OnFocusChangeListener
                public void onFocusChange(View view, boolean z9) {
                    if (z9) {
                        EmojiView.this.lastSearchKeyboardLanguage = AndroidUtilities.getCurrentKeyboardLanguage();
                        MediaDataController.getInstance(EmojiView.this.currentAccount).fetchNewEmojiKeywords(EmojiView.this.lastSearchKeyboardLanguage);
                    }
                }
            });
            FoundStickerPacksHeaderCell foundStickerPacksHeaderCell = new FoundStickerPacksHeaderCell(context2, resourcesProvider2);
            this.emojiSearchHeader = foundStickerPacksHeaderCell;
            foundStickerPacksHeaderCell.setVisibility(8);
            this.emojiSearchHeader.setOnBackClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda23
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$6(view);
                }
            });
            this.emojiContainer.addView(this.emojiSearchHeader, new FrameLayout.LayoutParams(-1, this.searchFieldHeight));
        } else {
            i = -1;
        }
        int themedColor = getThemedColor(i6);
        if (Color.alpha(themedColor) >= 255) {
            this.emojiTabs.setBackgroundColor(themedColor);
        }
        this.emojiAdapter.processEmoji(true);
        this.emojiTabs.updateEmojiPacks(getEmojipacks());
        this.emojiContainer.addView(this.emojiTabs, LayoutHelper.createFrame(i, 36.0f));
        View view = new View(context2);
        this.emojiTabsShadow = view;
        view.setAlpha(0.0f);
        this.emojiTabsShadow.setTag(1);
        View view2 = this.emojiTabsShadow;
        int i7 = Theme.key_chat_emojiPanelShadowLine;
        view2.setBackgroundColor(getThemedColor(i7));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, AndroidUtilities.getShadowHeight(), 51);
        layoutParams.topMargin = AndroidUtilities.m1036dp(36.0f);
        this.emojiContainer.addView(this.emojiTabsShadow, layoutParams);
        this.emojiAddPackButton = new FoundStickerPackButton(context2, resourcesProvider2);
        FoundStickerPackButtonContainer foundStickerPackButtonContainer = new FoundStickerPackButtonContainer(context2, resourcesProvider2);
        this.emojiAddPackButtonContainer = foundStickerPackButtonContainer;
        foundStickerPackButtonContainer.setVisibility(8);
        this.emojiAddPackButtonContainer.addView(this.emojiAddPackButton, LayoutHelper.createFrame(-1, 48.0f, 80, 10.0f, 5.0f, 10.0f, 10.0f));
        this.emojiContainer.addView(this.emojiAddPackButtonContainer, LayoutHelper.createFrame(i, -2, 80));
        if (z2) {
            if (z3) {
                this.gifContainer = new FrameLayout(context2) { // from class: org.telegram.ui.Components.EmojiView.13
                    public C429013(Context context2) {
                        super(context2);
                    }

                    @Override // android.view.ViewGroup
                    public boolean drawChild(Canvas canvas, View view3, long j) {
                        if (view3 == EmojiView.this.gifGridView) {
                            canvas.save();
                            canvas.clipRect(0.0f, EmojiView.this.gifSearchField.getY() + EmojiView.this.gifSearchField.getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight());
                            boolean zDrawChild = super.drawChild(canvas, view3, j);
                            canvas.restore();
                            return zDrawChild;
                        }
                        return super.drawChild(canvas, view3, j);
                    }
                };
                emojiViewIA2 = null;
                Tab tab2 = new Tab();
                tab2.type = 1;
                tab2.view = this.gifContainer;
                this.allTabs.add(tab2);
                C429114 c429114 = new RecyclerListView(context2) { // from class: org.telegram.ui.Components.EmojiView.14
                    private boolean ignoreLayout;
                    private boolean wasMeasured;

                    public C429114(Context context2) {
                        super(context2);
                    }

                    @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
                    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                        return super.onInterceptTouchEvent(motionEvent) || ContentPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, EmojiView.this.gifGridView, 0, EmojiView.this.contentPreviewViewerDelegate, this.resourcesProvider);
                    }

                    @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
                    public void onMeasure(int i8, int i9) {
                        super.onMeasure(i8, i9);
                        if (this.wasMeasured) {
                            return;
                        }
                        EmojiView.this.gifAdapter.notifyDataSetChanged();
                        this.wasMeasured = true;
                    }

                    @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
                    public void onLayout(boolean z9, int i8, int i9, int i10, int i11) {
                        if (EmojiView.this.firstGifAttach && EmojiView.this.gifAdapter.getItemCount() > 1) {
                            this.ignoreLayout = true;
                            EmojiView.this.gifLayoutManager.scrollToPositionWithOffset(0, 0);
                            EmojiView.this.gifSearchField.setVisibility(0);
                            EmojiView.this.gifTabs.onPageScrolled(0, 0);
                            EmojiView.this.firstGifAttach = false;
                            this.ignoreLayout = false;
                        }
                        super.onLayout(z9, i8, i9, i10, i11);
                        EmojiView.this.checkGifSearchFieldScroll(true);
                    }

                    @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View, android.view.ViewParent
                    public void requestLayout() {
                        if (this.ignoreLayout) {
                            return;
                        }
                        super.requestLayout();
                    }
                };
                this.gifGridView = c429114;
                c429114.setClipToPadding(false);
                RecyclerListView recyclerListView = this.gifGridView;
                GifLayoutManager gifLayoutManager = new GifLayoutManager(context2);
                this.gifLayoutManager = gifLayoutManager;
                recyclerListView.setLayoutManager(gifLayoutManager);
                this.gifGridView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: org.telegram.ui.Components.EmojiView.15
                    public C429215() {
                    }

                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public void getItemOffsets(Rect rect, View view3, RecyclerView recyclerView, RecyclerView.State state) {
                        int childAdapterPosition = recyclerView.getChildAdapterPosition(view3);
                        if (EmojiView.this.gifGridView.getAdapter() == EmojiView.this.gifAdapter && childAdapterPosition == EmojiView.this.gifAdapter.trendingSectionItem) {
                            rect.set(0, 0, 0, 0);
                            return;
                        }
                        if (childAdapterPosition != 0 || !EmojiView.this.gifAdapter.addSearch) {
                            rect.left = 0;
                            rect.bottom = 0;
                            rect.top = AndroidUtilities.m1036dp(2.0f);
                            rect.right = EmojiView.this.gifLayoutManager.isLastInRow(childAdapterPosition - (EmojiView.this.gifAdapter.addSearch ? 1 : 0)) ? 0 : AndroidUtilities.m1036dp(2.0f);
                            return;
                        }
                        rect.set(0, 0, 0, 0);
                    }
                });
                this.gifGridView.setPadding(0, this.searchFieldHeight, 0, AndroidUtilities.m1036dp(44.0f) + this.bottomInset);
                i2 = 2;
                this.gifGridView.setOverScrollMode(2);
                ((SimpleItemAnimator) this.gifGridView.getItemAnimator()).setSupportsChangeAnimations(false);
                RecyclerListView recyclerListView2 = this.gifGridView;
                GifAdapter gifAdapter = new GifAdapter(this, context2, true);
                this.gifAdapter = gifAdapter;
                recyclerListView2.setAdapter(gifAdapter);
                this.gifSearchAdapter = new GifAdapter(this, context2);
                this.gifGridView.setOnScrollListener(new TypedScrollListener(2) { // from class: org.telegram.ui.Components.EmojiView.16
                    public C429316(int i8) {
                        super(i8);
                    }

                    @Override // org.telegram.ui.Components.EmojiView.TypedScrollListener, androidx.recyclerview.widget.RecyclerView.OnScrollListener
                    public void onScrolled(RecyclerView recyclerView, int i8, int i9) {
                        super.onScrolled(recyclerView, i8, i9);
                        if (Build.VERSION.SDK_INT < 31 || EmojiView.this.scrollableViewNoiseSuppressor == null) {
                            return;
                        }
                        EmojiView.this.scrollableViewNoiseSuppressor.onScrolled(i8, i9);
                        EmojiView.this.invalidateBlurCaptures();
                    }
                });
                this.gifGridView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda24
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view3, MotionEvent motionEvent) {
                        return this.f$0.lambda$new$7(resourcesProvider2, view3, motionEvent);
                    }
                });
                RecyclerListView.OnItemClickListener onItemClickListener = new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda25
                    @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                    public final void onItemClick(View view3, int i8) {
                        this.f$0.lambda$new$8(view3, i8);
                    }
                };
                this.gifOnItemClickListener = onItemClickListener;
                this.gifGridView.setOnItemClickListener(onItemClickListener);
                this.gifContainer.addView(this.gifGridView, LayoutHelper.createFrame(i, -1.0f));
                C429417 c429417 = new SearchField(context2, 2) { // from class: org.telegram.ui.Components.EmojiView.17
                    public C429417(Context context2, int i8) {
                        super(context2, i8);
                    }

                    @Override // android.view.View
                    public void setTranslationY(float f) {
                        if (getTranslationY() != f) {
                            super.setTranslationY(f);
                            EmojiView.this.gifContainer.invalidate();
                        }
                    }
                };
                this.gifSearchField = c429417;
                this.gifContainer.addView(c429417, new FrameLayout.LayoutParams(i, this.searchFieldHeight + AndroidUtilities.getShadowHeight()));
                DraggableScrollSlidingTabStrip draggableScrollSlidingTabStrip = new DraggableScrollSlidingTabStrip(context2, resourcesProvider2);
                this.gifTabs = draggableScrollSlidingTabStrip;
                draggableScrollSlidingTabStrip.setType(ScrollSlidingTabStrip.Type.TAB);
                this.gifTabs.setUnderlineHeight(AndroidUtilities.getShadowHeight());
                i3 = i5;
                this.gifTabs.setIndicatorColor(getThemedColor(i3));
                this.gifTabs.setUnderlineColor(getThemedColor(i7));
                this.gifTabs.setBackgroundColor(getThemedColor(i6));
                updateGifTabs();
                this.gifTabs.setDelegate(new ScrollSlidingTabStrip.ScrollSlidingTabStripDelegate() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda26
                    @Override // org.telegram.ui.Components.ScrollSlidingTabStrip.ScrollSlidingTabStripDelegate
                    public final void onPageSelected(int i8) {
                        this.f$0.lambda$new$9(i8);
                    }
                });
                this.gifAdapter.loadTrendingGifs();
            } else {
                i3 = i5;
                emojiViewIA2 = null;
                i2 = 2;
            }
            z8 = z5;
            this.stickersContainer = new FrameLayout(context2) { // from class: org.telegram.ui.Components.EmojiView.18
                final /* synthetic */ boolean val$shouldDrawBackground;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C429518(Context context2, boolean z82) {
                    super(context2);
                    z = z82;
                }

                @Override // android.view.ViewGroup, android.view.View
                public void onAttachedToWindow() {
                    super.onAttachedToWindow();
                    EmojiView.this.stickersContainerAttached = true;
                    EmojiView.this.updateStickerTabsPosition();
                    if (EmojiView.this.chooseStickerActionTracker != null) {
                        EmojiView.this.chooseStickerActionTracker.checkVisibility();
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
                public void onDetachedFromWindow() {
                    super.onDetachedFromWindow();
                    EmojiView.this.stickersContainerAttached = false;
                    EmojiView.this.updateStickerTabsPosition();
                    if (EmojiView.this.chooseStickerActionTracker != null) {
                        EmojiView.this.chooseStickerActionTracker.checkVisibility();
                    }
                }

                @Override // android.view.ViewGroup
                public boolean drawChild(Canvas canvas, View view3, long j) {
                    if (!z && (view3 == EmojiView.this.stickersGridView || view3 == EmojiView.this.stickersSearchField)) {
                        canvas.save();
                        float y = EmojiView.this.stickersTab.getY() + EmojiView.this.stickersTab.getMeasuredHeight() + 1.0f;
                        if (view3 == EmojiView.this.stickersGridView) {
                            y = Math.max(y, EmojiView.this.stickersSearchField.getY() + EmojiView.this.stickersSearchField.getMeasuredHeight() + 1.0f);
                        }
                        canvas.clipRect(0.0f, y - (AndroidUtilities.m1036dp(16.0f) * EmojiView.this.animatorSearchStickerPackSelected.getFloatValue()), getMeasuredWidth(), getMeasuredHeight());
                        boolean zDrawChild = super.drawChild(canvas, view3, j);
                        canvas.restore();
                        return zDrawChild;
                    }
                    return super.drawChild(canvas, view3, j);
                }
            };
            MediaDataController.getInstance(this.currentAccount).checkStickers(0);
            MediaDataController.getInstance(this.currentAccount).checkFeaturedStickers();
            C429619 c429619 = new RecyclerListViewWithOverlayDraw(context2) { // from class: org.telegram.ui.Components.EmojiView.19
                boolean ignoreLayout;

                public C429619(Context context2) {
                    super(context2);
                }

                @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
                public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                    if (EmojiView.this.ignorePagerScroll) {
                        return false;
                    }
                    return super.onInterceptTouchEvent(motionEvent) || ContentPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, EmojiView.this.stickersGridView, EmojiView.this.getMeasuredHeight(), EmojiView.this.contentPreviewViewerDelegate, this.resourcesProvider);
                }

                @Override // org.telegram.p035ui.Components.RecyclerListView, android.view.View
                public void setVisibility(int i8) {
                    super.setVisibility(i8);
                }

                @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
                public void onLayout(boolean z9, int i8, int i9, int i10, int i11) {
                    if (EmojiView.this.firstStickersAttach && EmojiView.this.stickersGridAdapter.getItemCount() > 0) {
                        this.ignoreLayout = true;
                        EmojiView.this.stickersLayoutManager.scrollToPositionWithOffset(0, 0);
                        EmojiView.this.firstStickersAttach = false;
                        this.ignoreLayout = false;
                    }
                    super.onLayout(z9, i8, i9, i10, i11);
                    EmojiView.this.checkStickersSearchFieldScroll(true);
                }

                @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View, android.view.ViewParent
                public void requestLayout() {
                    if (this.ignoreLayout) {
                        return;
                    }
                    super.requestLayout();
                }

                @Override // androidx.recyclerview.widget.RecyclerView
                public void onScrolled(int i8, int i9) {
                    super.onScrolled(i8, i9);
                    if (Build.VERSION.SDK_INT >= 31 && EmojiView.this.scrollableViewNoiseSuppressor != null) {
                        EmojiView.this.scrollableViewNoiseSuppressor.onScrolled(i8, i9);
                        EmojiView.this.invalidateBlurCaptures();
                    }
                    if (EmojiView.this.stickersTabContainer != null) {
                        EmojiView.this.stickersTab.setUnderlineHeight(EmojiView.this.stickersGridView.canScrollVertically(-1) ? AndroidUtilities.getShadowHeight() : 0);
                    }
                    if (EmojiView.this.stickersSearchGridAdapter == null || getAdapter() != EmojiView.this.stickersSearchGridAdapter || EmojiView.this.stickersSearchGridAdapter.selectedPackId != 0 || EmojiView.this.stickersSearchGridAdapter.searchRunnable.isLoading() || EmojiView.this.stickersSearchGridAdapter.searchRunnable.isCompleted()) {
                        return;
                    }
                    if (EmojiView.this.stickersLayoutManager.findLastVisibleItemPosition() + 50 > EmojiView.this.stickersSearchGridAdapter.getItemCount()) {
                        SearchRunnable searchRunnable = EmojiView.this.stickersSearchGridAdapter.searchRunnable;
                        Objects.requireNonNull(searchRunnable);
                        AndroidUtilities.runOnUIThread(new EmojiView$19$$ExternalSyntheticLambda0(searchRunnable));
                    }
                }
            };
            this.stickersGridView = c429619;
            C429820 c429820 = new GridLayoutManager(context2, 5) { // from class: org.telegram.ui.Components.EmojiView.20
                public C429820(Context context2, int i8) {
                    super(context2, i8);
                }

                @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i8) {
                    try {
                        LinearSmoothScrollerCustom linearSmoothScrollerCustom = new LinearSmoothScrollerCustom(recyclerView.getContext(), 2);
                        linearSmoothScrollerCustom.setTargetPosition(i8);
                        startSmoothScroll(linearSmoothScrollerCustom);
                    } catch (Exception e) {
                        FileLog.m1048e(e);
                    }
                }

                @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public int scrollVerticallyBy(int i8, RecyclerView.Recycler recycler, RecyclerView.State state) {
                    int iScrollVerticallyBy = super.scrollVerticallyBy(i8, recycler, state);
                    if (iScrollVerticallyBy != 0 && EmojiView.this.stickersGridView.getScrollState() == 1) {
                        EmojiView.this.expandStickersByDragg = false;
                        EmojiView.this.updateStickerTabsPosition();
                    }
                    if (EmojiView.this.chooseStickerActionTracker == null) {
                        EmojiView.this.createStickersChooseActionTracker();
                    }
                    EmojiView.this.chooseStickerActionTracker.doSomeAction();
                    return iScrollVerticallyBy;
                }
            };
            this.stickersLayoutManager = c429820;
            c429619.setLayoutManager(c429820);
            this.stickersLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.Components.EmojiView.21
                public C429921() {
                }

                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i8) {
                    if (EmojiView.this.stickersGridView.getAdapter() == EmojiView.this.stickersGridAdapter) {
                        EmojiView emojiView = EmojiView.this;
                        if (i8 == 0) {
                            return emojiView.stickersGridAdapter.stickersPerRow;
                        }
                        if (i8 == emojiView.stickersGridAdapter.totalItems || !(EmojiView.this.stickersGridAdapter.cache.get(i8) == null || (EmojiView.this.stickersGridAdapter.cache.get(i8) instanceof TLRPC.Document))) {
                            return EmojiView.this.stickersGridAdapter.stickersPerRow;
                        }
                        return 1;
                    }
                    if (i8 == EmojiView.this.stickersSearchGridAdapter.totalItems || !(EmojiView.this.stickersSearchGridAdapter.cache.get(i8) == null || (EmojiView.this.stickersSearchGridAdapter.cache.get(i8) instanceof TLRPC.Document))) {
                        return EmojiView.this.stickersGridAdapter.stickersPerRow;
                    }
                    return 1;
                }
            });
            this.stickersGridView.setPadding(0, AndroidUtilities.m1036dp(36.0f), 0, AndroidUtilities.m1036dp(44.0f));
            this.stickersGridView.setClipToPadding(false);
            Tab tab3 = new Tab();
            tab3.type = i2;
            tab3.view = this.stickersContainer;
            this.allTabs.add(tab3);
            this.stickersSearchGridAdapter = new StickersSearchGridAdapter(context2);
            RecyclerListView recyclerListView3 = this.stickersGridView;
            StickersGridAdapter stickersGridAdapter = new StickersGridAdapter(context2);
            this.stickersGridAdapter = stickersGridAdapter;
            recyclerListView3.setAdapter(stickersGridAdapter);
            this.stickersGridView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda2
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view3, MotionEvent motionEvent) {
                    return this.f$0.lambda$new$10(resourcesProvider2, view3, motionEvent);
                }
            });
            RecyclerListView.OnItemClickListener onItemClickListener2 = new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda3
                @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                public final void onItemClick(View view3, int i8) {
                    this.f$0.lambda$new$11(view3, i8);
                }
            };
            this.stickersOnItemClickListener = onItemClickListener2;
            this.stickersGridView.setOnItemClickListener(onItemClickListener2);
            this.stickersGridView.setGlowColor(getThemedColor(i6));
            this.stickersContainer.addView(this.stickersGridView);
            this.stickersScrollHelper = new RecyclerAnimationScrollHelper(this.stickersGridView, this.stickersLayoutManager);
            C430022 c430022 = new SearchField(context2, 0) { // from class: org.telegram.ui.Components.EmojiView.22
                public C430022(Context context2, int i8) {
                    super(context2, i8);
                }

                @Override // android.view.View
                public void setTranslationY(float f) {
                    if (f != getTranslationY()) {
                        super.setTranslationY(f);
                        EmojiView.this.stickersContainer.invalidate();
                    }
                }
            };
            this.stickersSearchField = c430022;
            this.stickersContainer.addView(c430022, new FrameLayout.LayoutParams(-1, this.searchFieldHeight + AndroidUtilities.getShadowHeight()));
            FoundStickerPacksHeaderCell foundStickerPacksHeaderCell2 = new FoundStickerPacksHeaderCell(context2, resourcesProvider2);
            this.stickerSearchHeader = foundStickerPacksHeaderCell2;
            foundStickerPacksHeaderCell2.setVisibility(8);
            this.stickerSearchHeader.setOnBackClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    this.f$0.lambda$new$12(view3);
                }
            });
            this.stickersContainer.addView(this.stickerSearchHeader, new FrameLayout.LayoutParams(-1, this.searchFieldHeight));
            emojiViewIA = null;
            C430123 c430123 = new C430123(context2, resourcesProvider2, baseFragment, z82);
            this.stickersTab = c430123;
            c430123.setDragEnabled(true);
            this.stickersTab.setWillNotDraw(false);
            this.stickersTab.setType(ScrollSlidingTabStrip.Type.TAB);
            this.stickersTab.setUnderlineHeight(this.stickersGridView.canScrollVertically(-1) ? AndroidUtilities.getShadowHeight() : 0);
            this.stickersTab.setIndicatorColor(getThemedColor(i3));
            this.stickersTab.setUnderlineColor(getThemedColor(i7));
            if (viewGroup != null && z82) {
                C430224 c430224 = new FrameLayout(context2) { // from class: org.telegram.ui.Components.EmojiView.24
                    Paint paint = new Paint();

                    public C430224(Context context2) {
                        super(context2);
                        this.paint = new Paint();
                    }

                    @Override // android.view.ViewGroup, android.view.View
                    public void dispatchDraw(Canvas canvas) {
                        float fM1036dp = AndroidUtilities.m1036dp(50.0f) * EmojiView.this.delegate.getProgressToSearchOpened();
                        if (fM1036dp > getMeasuredHeight()) {
                            return;
                        }
                        canvas.save();
                        if (fM1036dp != 0.0f) {
                            canvas.clipRect(0.0f, fM1036dp, getMeasuredWidth(), getMeasuredHeight());
                        }
                        this.paint.setColor(EmojiView.this.getThemedColor(Theme.key_chat_emojiPanelBackground));
                        canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), AndroidUtilities.m1036dp(36.0f) + EmojiView.this.stickersTab.getExpandedOffset(), this.paint);
                        super.dispatchDraw(canvas);
                        EmojiView.this.stickersTab.drawOverlays(canvas);
                        canvas.restore();
                    }

                    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
                    public void onLayout(boolean z9, int i8, int i9, int i10, int i11) {
                        super.onLayout(z9, i8, i9, i10, i11);
                        EmojiView.this.updateStickerTabsPosition();
                    }
                };
                this.stickersTabContainer = c430224;
                c430224.addView(this.stickersTab, LayoutHelper.createFrame(-1, 36, 51));
                viewGroup.addView(this.stickersTabContainer, LayoutHelper.createFrame(-1, -2.0f));
            } else {
                this.stickersContainer.addView(this.stickersTab, LayoutHelper.createFrame(-1, 36, 51));
            }
            updateStickerTabs(true);
            this.stickersTab.setDelegate(new ScrollSlidingTabStrip.ScrollSlidingTabStripDelegate() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda5
                @Override // org.telegram.ui.Components.ScrollSlidingTabStrip.ScrollSlidingTabStripDelegate
                public final void onPageSelected(int i8) {
                    this.f$0.lambda$new$13(i8);
                }
            });
            this.stickersGridView.setOnScrollListener(new TypedScrollListener(0));
            this.stickerAddPackButton = new FoundStickerPackButton(context2, resourcesProvider2);
            FoundStickerPackButtonContainer foundStickerPackButtonContainer2 = new FoundStickerPackButtonContainer(context2, resourcesProvider2);
            this.stickerAddPackButtonContainer = foundStickerPackButtonContainer2;
            foundStickerPackButtonContainer2.setVisibility(8);
            this.stickerAddPackButtonContainer.addView(this.stickerAddPackButton, LayoutHelper.createFrame(-1, 48.0f, 80, 10.0f, 5.0f, 10.0f, 10.0f));
            this.stickersContainer.addView(this.stickerAddPackButtonContainer, LayoutHelper.createFrame(-1, -2, 80));
        } else {
            z82 = z5;
            emojiViewIA = null;
            i2 = 2;
        }
        this.currentTabs.clear();
        this.currentTabs.addAll(this.allTabs);
        C430325 c430325 = new ViewPager(context2) { // from class: org.telegram.ui.Components.EmojiView.25
            public C430325(Context context2) {
                super(context2);
            }

            @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (EmojiView.this.ignorePagerScroll) {
                    return false;
                }
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(canScrollHorizontally(-1));
                }
                try {
                    return super.onInterceptTouchEvent(motionEvent);
                } catch (IllegalArgumentException unused) {
                    return false;
                }
            }

            @Override // androidx.viewpager.widget.ViewPager
            public void setCurrentItem(int i8, boolean z9) {
                EmojiView.this.startStopVisibleGifs(i8 == 1);
                if (i8 != getCurrentItem()) {
                    super.setCurrentItem(i8, z9);
                    return;
                }
                if (i8 == 0) {
                    EmojiView.this.tabsMinusDy[1] = 0;
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(EmojiView.this.emojiTabs, (Property<EmojiTabsStrip, Float>) ViewGroup.TRANSLATION_Y, 0.0f);
                    objectAnimatorOfFloat.setDuration(150L);
                    objectAnimatorOfFloat.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                    objectAnimatorOfFloat.start();
                    EmojiView.this.scrollEmojisToPosition(1, 0);
                    if (EmojiView.this.emojiTabs != null) {
                        EmojiView.this.emojiTabs.select(0);
                        return;
                    }
                    return;
                }
                EmojiView emojiView = EmojiView.this;
                if (i8 == 1) {
                    emojiView.gifGridView.smoothScrollToPosition(0);
                } else {
                    emojiView.stickersGridView.smoothScrollToPosition(1);
                }
            }
        };
        this.pager = c430325;
        EmojiPagesAdapter emojiPagesAdapter = new EmojiPagesAdapter();
        this.emojiPagerAdapter = emojiPagesAdapter;
        c430325.setAdapter(emojiPagesAdapter);
        C430426 c430426 = new ImageView(context2) { // from class: org.telegram.ui.Components.EmojiView.26
            public C430426(Context context2) {
                super(context2);
            }

            @Override // android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    EmojiView.this.backspacePressed = true;
                    EmojiView.this.backspaceOnce = false;
                    EmojiView.this.postBackspaceRunnable(350);
                } else if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
                    EmojiView.this.backspacePressed = false;
                    if (!EmojiView.this.backspaceOnce && EmojiView.this.delegate != null && EmojiView.this.delegate.onBackspace()) {
                        try {
                            EmojiView.this.backspaceButton.performHapticFeedback(3);
                        } catch (Exception unused) {
                        }
                    }
                }
                super.onTouchEvent(motionEvent);
                return true;
            }
        };
        this.backspaceButton = c430426;
        c430426.setHapticFeedbackEnabled(true);
        this.backspaceButton.setImageResource(C2797R.drawable.smiles_tab_clear);
        ImageView imageView = this.backspaceButton;
        int glassIconColor = z72 ? getGlassIconColor(0.6f) : getThemedColor(Theme.key_chat_emojiPanelBackspace);
        PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
        imageView.setColorFilter(new PorterDuffColorFilter(glassIconColor, mode));
        ImageView imageView2 = this.backspaceButton;
        ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
        imageView2.setScaleType(scaleType);
        this.backspaceButton.setContentDescription(LocaleController.getString(C2797R.string.AccDescrBackspace));
        this.backspaceButton.setFocusable(true);
        this.backspaceButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                EmojiView.$r8$lambda$QgvpfMFqNjYqAUCQmi9fZc2cUmM(view3);
            }
        });
        ScaleStateListAnimator.apply(this.backspaceButton);
        FrameLayout frameLayout = new FrameLayout(context2);
        this.bulletinContainer = frameLayout;
        if (z4) {
            addView(frameLayout, LayoutHelper.createFrame(-1, 100.0f, 87, 0.0f, 0.0f, 0.0f, (AndroidUtilities.getShadowHeight() / AndroidUtilities.density) + 40.0f));
        } else {
            addView(frameLayout, LayoutHelper.createFrame(-1, 100.0f, 87, 0.0f, 0.0f, 0.0f, 0.0f));
        }
        FrameLayout frameLayout2 = new FrameLayout(context2);
        this.bulletinContainer2 = frameLayout2;
        addView(frameLayout2, LayoutHelper.createFrame(-1, 100.0f, 87, 0.0f, 0.0f, 0.0f, 64.0f));
        this.bottomTabContainer = new FrameLayout(context2);
        View view3 = new View(context2);
        this.bottomTabContainerBackground = view3;
        int i8 = i2;
        this.bottomTabContainer.addView(view3, new FrameLayout.LayoutParams(-1, AndroidUtilities.m1036dp(40.0f), 83));
        View view4 = this.bottomTabContainer;
        if (z4) {
            addView(view4, LayoutHelper.createFrame(-1, 48, 80));
            this.bottomTabContainer.addView(this.backspaceButton, LayoutHelper.createFrame(48, 48.0f, 85, 2.0f, 0.0f, 2.0f, 0.0f));
            if (z2) {
                ImageView imageView3 = new ImageView(context2);
                this.stickerSettingsButton = imageView3;
                imageView3.setImageResource(C2797R.drawable.smiles_tab_settings);
                this.stickerSettingsButton.setColorFilter(new PorterDuffColorFilter(z72 ? getGlassIconColor(0.6f) : getThemedColor(Theme.key_chat_emojiPanelBackspace), mode));
                this.stickerSettingsButton.setScaleType(scaleType);
                this.stickerSettingsButton.setFocusable(true);
                this.stickerSettingsButton.setContentDescription(LocaleController.getString(C2797R.string.Settings));
                ScaleStateListAnimator.apply(this.stickerSettingsButton);
                this.bottomTabContainer.addView(this.stickerSettingsButton, LayoutHelper.createFrame(48, 48.0f, 85, 2.0f, 0.0f, 2.0f, 0.0f));
                this.stickerSettingsButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda7
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view5) {
                        this.f$0.lambda$new$15(view5);
                    }
                });
            }
            PagerSlidingTabStrip pagerSlidingTabStrip = new PagerSlidingTabStrip(context2, resourcesProvider2);
            this.typeTabs = pagerSlidingTabStrip;
            pagerSlidingTabStrip.setViewPager(this.pager);
            this.typeTabs.setShouldExpand(false);
            this.typeTabs.setIndicatorHeight(AndroidUtilities.m1036dp(3.0f));
            this.typeTabs.setIndicatorColor(ColorUtils.setAlphaComponent(getThemedColor(Theme.key_chat_emojiPanelIconSelected), 20));
            this.typeTabs.setUnderlineHeight(0);
            this.typeTabs.setTabPaddingLeftRight(AndroidUtilities.m1036dp(11.0f));
            this.typeTabs.setPadding(AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(11.0f), AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(11.0f));
            this.bottomTabContainer.addView(this.typeTabs, LayoutHelper.createFrame(-2, 48, 81));
            this.typeTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: org.telegram.ui.Components.EmojiView.27
                final /* synthetic */ boolean val$shouldDrawBackground;

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int i9) {
                }

                public C430527(boolean z82) {
                    z = z82;
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int i9, float f, int i10) {
                    SearchField searchField;
                    SearchField searchField2;
                    EmojiView.this.checkGridVisibility(i9, f);
                    EmojiView emojiView = EmojiView.this;
                    emojiView.onPageScrolled(i9, (emojiView.getMeasuredWidth() - EmojiView.this.getPaddingLeft()) - EmojiView.this.getPaddingRight(), i10);
                    boolean z9 = true;
                    EmojiView.this.showBottomTab(true, true);
                    int currentItem = EmojiView.this.pager.getCurrentItem();
                    if (currentItem == 0) {
                        searchField = EmojiView.this.emojiSearchField;
                    } else {
                        EmojiView emojiView2 = EmojiView.this;
                        if (currentItem == 1) {
                            searchField = emojiView2.gifSearchField;
                        } else {
                            searchField = emojiView2.stickersSearchField;
                        }
                    }
                    String string = searchField.searchEditText.getText().toString();
                    for (int i11 = 0; i11 < 3; i11++) {
                        if (i11 == 0) {
                            searchField2 = EmojiView.this.emojiSearchField;
                        } else {
                            EmojiView emojiView3 = EmojiView.this;
                            if (i11 == 1) {
                                searchField2 = emojiView3.gifSearchField;
                            } else {
                                searchField2 = emojiView3.stickersSearchField;
                            }
                        }
                        if (searchField2 != null && searchField2 != searchField && searchField2.searchEditText != null && !searchField2.searchEditText.getText().toString().equals(string)) {
                            searchField2.searchEditText.setText(string);
                            searchField2.searchEditText.setSelection(string.length());
                        }
                    }
                    EmojiView emojiView4 = EmojiView.this;
                    if ((i9 != 0 || f <= 0.0f) && i9 != 1) {
                        z9 = false;
                    }
                    emojiView4.startStopVisibleGifs(z9);
                    EmojiView.this.updateStickerTabsPosition();
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int i9) {
                    EmojiView.this.saveNewPage();
                    boolean z9 = false;
                    EmojiView.this.showBackspaceButton(i9 == 0, true);
                    EmojiView emojiView = EmojiView.this;
                    if (i9 == 2 && (z || emojiView.shouldDrawStickerSettings)) {
                        z9 = true;
                    }
                    emojiView.showStickerSettingsButton(z9, true);
                    if (EmojiView.this.delegate.isSearchOpened()) {
                        if (i9 == 0) {
                            if (EmojiView.this.emojiSearchField != null) {
                                EmojiView.this.emojiSearchField.searchEditText.requestFocus();
                                return;
                            }
                            return;
                        }
                        EmojiView emojiView2 = EmojiView.this;
                        if (i9 == 1) {
                            if (emojiView2.gifSearchField != null) {
                                EmojiView.this.gifSearchField.searchEditText.requestFocus();
                            }
                        } else if (emojiView2.stickersSearchField != null) {
                            EmojiView.this.stickersSearchField.searchEditText.requestFocus();
                        }
                    }
                }
            });
            ImageView imageView4 = new ImageView(context2);
            this.searchButton = imageView4;
            imageView4.setImageResource(C2797R.drawable.smiles_tab_search);
            this.searchButton.setColorFilter(new PorterDuffColorFilter(z72 ? getGlassIconColor(0.6f) : getThemedColor(Theme.key_chat_emojiPanelBackspace), mode));
            this.searchButton.setScaleType(scaleType);
            this.searchButton.setContentDescription(LocaleController.getString(C2797R.string.Search));
            this.searchButton.setFocusable(true);
            this.searchButton.setVisibility(8);
            this.bottomTabContainer.addView(this.searchButton, LayoutHelper.createFrame(48, 48.0f, 83, 2.0f, 0.0f, 2.0f, 0.0f));
            this.searchButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view5) {
                    this.f$0.lambda$new$16(view5);
                }
            });
        } else {
            addView(view4, LayoutHelper.createFrame(56, 48.0f, (LocaleController.isRTL ? 3 : 5) | 80, 0.0f, 0.0f, 2.0f, 0.0f));
            Drawable drawableCreateSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.m1036dp(56.0f), getThemedColor(i6), getThemedColor(i6));
            ScaleStateListAnimator.apply(this.backspaceButton);
            this.backspaceButton.setPadding(0, 0, AndroidUtilities.m1036dp(2.0f), 0);
            this.backspaceButton.setBackground(drawableCreateSimpleSelectorCircleDrawable);
            this.backspaceButton.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_chats_actionIcon), mode));
            this.backspaceButton.setContentDescription(LocaleController.getString(C2797R.string.AccDescrBackspace));
            this.backspaceButton.setFocusable(true);
            this.bottomTabContainer.addView(this.backspaceButton, LayoutHelper.createFrame(48, 48.0f, 51, 2.0f, 0.0f, 2.0f, 0.0f));
            this.bottomTabContainerBackground.setVisibility(8);
        }
        addView(this.pager, 0, LayoutHelper.createFrame(-1, -1, 51));
        CorrectlyMeasuringTextView correctlyMeasuringTextView = new CorrectlyMeasuringTextView(context2);
        this.mediaBanTooltip = correctlyMeasuringTextView;
        correctlyMeasuringTextView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1036dp(6.0f), getThemedColor(Theme.key_chat_gifSaveHintBackground)));
        this.mediaBanTooltip.setTextColor(getThemedColor(Theme.key_chat_gifSaveHintText));
        this.mediaBanTooltip.setPadding(AndroidUtilities.m1036dp(12.0f), AndroidUtilities.m1036dp(7.0f), AndroidUtilities.m1036dp(12.0f), AndroidUtilities.m1036dp(7.0f));
        this.mediaBanTooltip.setGravity(16);
        this.mediaBanTooltip.setTextSize(1, 14.0f);
        this.mediaBanTooltip.setVisibility(4);
        addView(this.mediaBanTooltip, LayoutHelper.createFrame(-2, -2.0f, 81, 5.0f, 0.0f, 5.0f, 53.0f));
        this.emojiSize = AndroidUtilities.m1036dp(AndroidUtilities.isTablet() ? 40.0f : 32.0f);
        EmojiColorPickerWindow emojiColorPickerWindowCreate = EmojiColorPickerWindow.create(context2, resourcesProvider2);
        this.colorPickerView = emojiColorPickerWindowCreate;
        emojiColorPickerWindowCreate.setOnSelectionUpdateListener(new Utilities.Callback2() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda9
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.lambda$new$17((Integer) obj, (Integer) obj2);
            }
        });
        this.currentPage = MessagesController.getGlobalEmojiSettings().getInt("selected_page", 0);
        Emoji.loadRecentEmoji();
        this.emojiAdapter.notifyDataSetChanged();
        setAllow(z2, z3, false);
        if (Build.VERSION.SDK_INT >= 31) {
            BlurredBackgroundSourceRenderNode blurredBackgroundSourceRenderNode = new BlurredBackgroundSourceRenderNode(null);
            this.blurredBackgroundSourceRenderNode = blurredBackgroundSourceRenderNode;
            BlurredBackgroundDrawableViewFactory blurredBackgroundDrawableViewFactory = new BlurredBackgroundDrawableViewFactory(blurredBackgroundSourceRenderNode);
            this.blurredBackgroundDrawableFactory = blurredBackgroundDrawableViewFactory;
            blurredBackgroundDrawableViewFactory.setLiquidGlassEffectAllowed(LiteMode.isEnabled(262144));
            this.scrollableViewNoiseSuppressor = new DownscaleScrollableNoiseSuppressor();
        } else {
            this.blurredBackgroundSourceRenderNode = null;
            this.blurredBackgroundDrawableFactory = new BlurredBackgroundDrawableViewFactory(blurredBackgroundSourceColor);
            this.scrollableViewNoiseSuppressor = null;
        }
        ViewPositionWatcher viewPositionWatcher = new ViewPositionWatcher(this);
        PagerSlidingTabStrip pagerSlidingTabStrip2 = this.typeTabs;
        if (pagerSlidingTabStrip2 != null) {
            viewPositionWatcher.subscribe(pagerSlidingTabStrip2, this, new ViewPositionWatcher.OnChangedListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda10
                @Override // org.telegram.ui.Components.chat.ViewPositionWatcher.OnChangedListener
                public final void onPositionChanged(View view5, RectF rectF2) {
                    this.f$0.lambda$new$18(view5, rectF2);
                }
            });
        }
        this.blurredBackgroundDrawableFactory.setSourceRootView(viewPositionWatcher, this);
        final IBlur3Capture[] iBlur3CaptureArr = new IBlur3Capture[3];
        EmojiGridView emojiGridView4 = this.emojiGridView;
        if (emojiGridView4 != null) {
            emojiGridView4.addEdgeEffectListener(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$19();
                }
            });
            final EmojiGridView emojiGridView5 = this.emojiGridView;
            Objects.requireNonNull(emojiGridView5);
            iBlur3CaptureArr[0] = new ViewGroupPartRenderer(emojiGridView5, this, new ViewGroupPartRenderer.DrawChildMethod() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda13
                @Override // org.telegram.ui.Components.blur3.ViewGroupPartRenderer.DrawChildMethod
                public final boolean drawChild(Canvas canvas, View view5, long j) {
                    return emojiGridView5.drawChild(canvas, view5, j);
                }
            });
        }
        RecyclerListView recyclerListView4 = this.gifGridView;
        if (recyclerListView4 != null) {
            recyclerListView4.addEdgeEffectListener(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$20();
                }
            });
            RecyclerListView recyclerListView5 = this.gifGridView;
            Objects.requireNonNull(recyclerListView5);
            iBlur3CaptureArr[1] = new ViewGroupPartRenderer(recyclerListView5, this, new EmojiView$$ExternalSyntheticLambda15(recyclerListView5));
        }
        RecyclerListView recyclerListView6 = this.stickersGridView;
        if (recyclerListView6 != null) {
            recyclerListView6.addEdgeEffectListener(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$21();
                }
            });
            iBlur3CaptureArr[i8] = new ViewGroupPartRenderer(this.stickersGridView, this, new ViewGroupPartRenderer.DrawChildMethod() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda17
                @Override // org.telegram.ui.Components.blur3.ViewGroupPartRenderer.DrawChildMethod
                public final boolean drawChild(Canvas canvas, View view5, long j) {
                    return this.f$0.lambda$new$22(canvas, view5, j);
                }
            });
        }
        this.blurCaptureMethod = new IBlur3Capture() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda18
            @Override // org.telegram.p035ui.Components.blur3.capture.IBlur3Capture
            public final void capture(Canvas canvas, RectF rectF2) {
                EmojiView.m11553$r8$lambda$4HN73U0mDwgTGy7HxQE4G45_LQ(iBlur3CaptureArr, canvas, rectF2);
            }
        };
        setBlurredBackgroundDrawableFactory(this.blurredBackgroundDrawableFactory);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$4 */
    public class C43174 extends FrameLayout {
        public C43174(Context context2) {
            super(context2);
        }

        @Override // android.view.ViewGroup
        public boolean drawChild(Canvas canvas, View view, long j) {
            if (view == EmojiView.this.emojiGridView || view == EmojiView.this.emojiSearchField) {
                canvas.save();
                float y = EmojiView.this.emojiTabs.getY() + EmojiView.this.emojiTabs.getMeasuredHeight() + 1.0f;
                if (view == EmojiView.this.emojiGridView && EmojiView.this.emojiSearchField != null) {
                    y = Math.max(y, EmojiView.this.emojiSearchField.getY() + EmojiView.this.emojiSearchField.getMeasuredHeight() + 1.0f);
                }
                canvas.clipRect(0.0f, y - (AndroidUtilities.m1036dp(16.0f) * EmojiView.this.animatorSearchEmojiPackSelected.getFloatValue()), getMeasuredWidth(), getMeasuredHeight());
                boolean zDrawChild = super.drawChild(canvas, view, j);
                canvas.restore();
                return zDrawChild;
            }
            return super.drawChild(canvas, view, j);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$5 */
    public class C43185 extends EmojiGridView {
        public C43185(Context context2) {
            super(context2);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiGridView, org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (EmojiView.this.ignorePagerScroll) {
                return false;
            }
            return super.onInterceptTouchEvent(motionEvent);
        }
    }

    public /* synthetic */ boolean lambda$new$2(Theme.ResourcesProvider resourcesProvider, View view, MotionEvent motionEvent) {
        return ContentPreviewViewer.getInstance().onTouch(motionEvent, this.emojiGridView, getMeasuredHeight(), null, this.contentPreviewViewerDelegate, resourcesProvider);
    }

    public /* synthetic */ boolean lambda$new$3(View view, int i) {
        String str;
        int iM1036dp;
        if (view instanceof ImageViewEmoji) {
            ImageViewEmoji imageViewEmoji = (ImageViewEmoji) view;
            if (imageViewEmoji.isRecent) {
                RecyclerView.ViewHolder viewHolderFindContainingViewHolder = this.emojiGridView.findContainingViewHolder(view);
                if (viewHolderFindContainingViewHolder != null && viewHolderFindContainingViewHolder.getAdapterPosition() <= getRecentEmoji().size()) {
                    this.delegate.onClearEmojiRecent();
                }
                this.emojiGridView.clearTouchesFor(view);
                return true;
            }
            if (imageViewEmoji.getSpan() != null || (str = (String) imageViewEmoji.getTag()) == null) {
                return false;
            }
            String strReplace = str.replace("🏻", _UrlKt.FRAGMENT_ENCODE_SET).replace("🏼", _UrlKt.FRAGMENT_ENCODE_SET).replace("🏽", _UrlKt.FRAGMENT_ENCODE_SET).replace("🏾", _UrlKt.FRAGMENT_ENCODE_SET).replace("🏿", _UrlKt.FRAGMENT_ENCODE_SET);
            String str2 = !imageViewEmoji.isRecent ? Emoji.emojiColor.get(strReplace) : null;
            boolean zIsCompound = CompoundEmoji.isCompound(strReplace);
            if (zIsCompound || EmojiData.emojiColoredMap.contains(strReplace)) {
                this.emojiTouchedView = imageViewEmoji;
                this.emojiTouchedX = this.emojiLastX;
                this.emojiTouchedY = this.emojiLastY;
                if (zIsCompound) {
                    strReplace = addColorToCode(strReplace, str2);
                } else {
                    this.colorPickerView.setSelection(CompoundEmoji.skinTones.indexOf(str2) + 1);
                }
                this.colorPickerView.setEmoji(strReplace);
                int popupWidth = this.colorPickerView.getPopupWidth();
                int popupHeight = this.colorPickerView.getPopupHeight();
                imageViewEmoji.getLocationOnScreen(this.location);
                if (this.colorPickerView.isCompound()) {
                    iM1036dp = 0;
                } else {
                    iM1036dp = (this.emojiSize * this.colorPickerView.getSelection()) + AndroidUtilities.m1036dp((this.colorPickerView.getSelection() * 4) - (AndroidUtilities.isTablet() ? 5 : 1));
                }
                int i2 = this.location[0] - iM1036dp;
                int iM1036dp2 = AndroidUtilities.m1036dp(5.0f);
                int[] iArr = this.location;
                if (i2 < iM1036dp2) {
                    iM1036dp += (iArr[0] - iM1036dp) - AndroidUtilities.m1036dp(5.0f);
                } else if ((iArr[0] - iM1036dp) + popupWidth > AndroidUtilities.displaySize.x - AndroidUtilities.m1036dp(5.0f)) {
                    iM1036dp += ((this.location[0] - iM1036dp) + popupWidth) - (AndroidUtilities.displaySize.x - AndroidUtilities.m1036dp(5.0f));
                }
                int i3 = -iM1036dp;
                int top = imageViewEmoji.getTop() < 0 ? imageViewEmoji.getTop() : 0;
                this.colorPickerView.setupArrow((AndroidUtilities.m1036dp(AndroidUtilities.isTablet() ? 30.0f : 22.0f) - i3) + ((int) AndroidUtilities.dpf2(0.5f)));
                this.colorPickerView.setFocusable(true);
                this.colorPickerView.showAsDropDown(view, i3, (((-view.getMeasuredHeight()) - popupHeight) + ((view.getMeasuredHeight() - this.emojiSize) / 2)) - top);
                this.pager.requestDisallowInterceptTouchEvent(true);
                this.emojiGridView.hideSelector(true);
                this.emojiGridView.clearTouchesFor(view);
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$6 */
    public class C43196 extends GridLayoutManager {
        public C43196(Context context2, int i6) {
            super(context2, i6);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$6$1 */
        public class AnonymousClass1 extends LinearSmoothScrollerCustom {
            public AnonymousClass1(Context context2, int i7) {
                super(context2, i7);
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScrollerCustom
            public void onEnd() {
                EmojiView.this.emojiSmoothScrolling = false;
            }
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i6) {
            try {
                AnonymousClass1 anonymousClass1 = new LinearSmoothScrollerCustom(recyclerView.getContext(), 2) { // from class: org.telegram.ui.Components.EmojiView.6.1
                    public AnonymousClass1(Context context2, int i7) {
                        super(context2, i7);
                    }

                    @Override // androidx.recyclerview.widget.LinearSmoothScrollerCustom
                    public void onEnd() {
                        EmojiView.this.emojiSmoothScrolling = false;
                    }
                };
                anonymousClass1.setTargetPosition(i6);
                startSmoothScroll(anonymousClass1);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
    }

    public static /* synthetic */ Integer $r8$lambda$roMuldUBeMpvL2VV1XisjntU8Zo(Integer num) {
        return 0;
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$7 */
    public class C43207 extends GridLayoutManager.SpanSizeLookup {
        public C43207() {
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int i7) {
            RecyclerView.Adapter adapter = EmojiView.this.emojiGridView.getAdapter();
            EmojiSearchAdapter emojiSearchAdapter = EmojiView.this.emojiSearchAdapter;
            EmojiView emojiView = EmojiView.this;
            if (adapter == emojiSearchAdapter) {
                int itemViewType = emojiView.emojiSearchAdapter.getItemViewType(i7);
                if (itemViewType == 1 || itemViewType == 3 || itemViewType == 2 || itemViewType == 4 || itemViewType == 5) {
                    return EmojiView.this.emojiLayoutManager.getSpanCount();
                }
            } else if ((emojiView.needEmojiSearch && i7 == 0) || i7 == EmojiView.this.emojiAdapter.trendingRow || i7 == EmojiView.this.emojiAdapter.trendingHeaderRow || i7 == EmojiView.this.emojiAdapter.recentlyUsedHeaderRow || EmojiView.this.emojiAdapter.positionToSection.indexOfKey(i7) >= 0 || EmojiView.this.emojiAdapter.positionToUnlock.indexOfKey(i7) >= 0) {
                return EmojiView.this.emojiLayoutManager.getSpanCount();
            }
            return 1;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanGroupIndex(int i7, int i8) {
            return super.getSpanGroupIndex(i7, i8);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$8 */
    public class C43218 extends RecyclerAnimationScrollHelper.AnimationCallback {
        public C43218() {
        }

        @Override // org.telegram.ui.Components.RecyclerAnimationScrollHelper.AnimationCallback
        public void onPreAnimation() {
            EmojiView.this.emojiGridView.updateEmojiDrawables();
            EmojiView.this.emojiSmoothScrolling = true;
        }

        @Override // org.telegram.ui.Components.RecyclerAnimationScrollHelper.AnimationCallback
        public void onEndAnimation() {
            EmojiView.this.emojiSmoothScrolling = false;
            EmojiView.this.emojiGridView.updateEmojiDrawables();
        }

        @Override // org.telegram.ui.Components.RecyclerAnimationScrollHelper.AnimationCallback
        public void ignoreView(View view, boolean z9) {
            if (view instanceof ImageViewEmoji) {
                ((ImageViewEmoji) view).ignoring = z9;
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$9 */
    public class C43229 extends TypedScrollListener {
        public C43229(int i7) {
            super(i7);
        }

        @Override // org.telegram.ui.Components.EmojiView.TypedScrollListener, androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i7, int i8) {
            EmojiView.this.updateEmojiTabsPosition();
            if (Build.VERSION.SDK_INT >= 31 && EmojiView.this.scrollableViewNoiseSuppressor != null) {
                EmojiView.this.scrollableViewNoiseSuppressor.onScrolled(i7, i8);
                EmojiView.this.invalidateBlurCaptures();
            }
            super.onScrolled(recyclerView, i7, i8);
            if (EmojiView.this.emojiSearchAdapter == null || EmojiView.this.emojiGridView.getAdapter() != EmojiView.this.emojiSearchAdapter || EmojiView.this.emojiSearchAdapter.searchRunnable.isLoading() || EmojiView.this.emojiSearchAdapter.searchRunnable.isCompleted()) {
                return;
            }
            if (EmojiView.this.emojiLayoutManager.findLastVisibleItemPosition() + 20 > EmojiView.this.emojiSearchAdapter.getItemCount()) {
                SearchRunnable searchRunnable = EmojiView.this.emojiSearchAdapter.searchRunnable;
                Objects.requireNonNull(searchRunnable);
                AndroidUtilities.runOnUIThread(new EmojiView$19$$ExternalSyntheticLambda0(searchRunnable));
            }
        }

        @Override // org.telegram.ui.Components.EmojiView.TypedScrollListener, androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i7) {
            if (i7 == 0) {
                EmojiView.this.emojiSmoothScrolling = false;
            }
            super.onScrollStateChanged(recyclerView, i7);
        }
    }

    public /* synthetic */ void lambda$new$5() {
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate != null) {
            emojiViewDelegate.onEmojiSettingsClick(this.emojiAdapter.frozenEmojiPacks);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$10 */
    public class C428710 extends EmojiTabsStrip {
        public C428710(Context context2, final Theme.ResourcesProvider resourcesProvider2, boolean z9, boolean z10, boolean z11, boolean z12, int i7, Runnable runnable, boolean z72) {
            super(context2, resourcesProvider2, z9, z10, z11, z12, i7, runnable, z72);
        }

        @Override // org.telegram.p035ui.Components.EmojiTabsStrip
        public boolean isInstalled(EmojiPack emojiPack) {
            return emojiPack.installed || EmojiView.this.installedEmojiSets.contains(Long.valueOf(emojiPack.set.f1280id));
        }

        @Override // org.telegram.p035ui.Components.EmojiTabsStrip
        public boolean allowEmojisForNonPremium() {
            return EmojiView.this.allowEmojisForNonPremium;
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            if (getTranslationY() != f) {
                super.setTranslationY(f);
                if (EmojiView.this.emojiTabsShadow != null) {
                    EmojiView.this.emojiTabsShadow.setTranslationY(f);
                }
                EmojiView.this.emojiContainer.invalidate();
            }
        }

        @Override // org.telegram.p035ui.Components.EmojiTabsStrip
        public boolean doIncludeFeatured() {
            return EmojiView.this.featuredEmojiSets.size() <= 0 || ((TLRPC.StickerSetCovered) EmojiView.this.featuredEmojiSets.get(0)).set == null || MessagesController.getEmojiSettings(EmojiView.this.currentAccount).getLong("emoji_featured_hidden", 0L) == ((TLRPC.StickerSetCovered) EmojiView.this.featuredEmojiSets.get(0)).set.f1280id || !UserConfig.getInstance(UserConfig.selectedAccount).isPremium();
        }

        /* JADX WARN: Removed duplicated region for block: B:82:0x00d9  */
        @Override // org.telegram.p035ui.Components.EmojiTabsStrip
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean onTabClick(int r9) {
            /*
                Method dump skipped, instruction units count: 254
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.EmojiView.C428710.onTabClick(int):boolean");
        }

        @Override // org.telegram.p035ui.Components.EmojiTabsStrip
        public ColorFilter getEmojiColorFilter() {
            return EmojiView.this.animatedEmojiTextColorFilter;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$11 */
    public class C428811 extends SearchField {
        public C428811(Context context2, int i7) {
            super(context2, i7);
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            if (f != getTranslationY()) {
                super.setTranslationY(f);
                EmojiView.this.emojiContainer.invalidate();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$12 */
    public class ViewOnFocusChangeListenerC428912 implements View.OnFocusChangeListener {
        public ViewOnFocusChangeListenerC428912() {
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z9) {
            if (z9) {
                EmojiView.this.lastSearchKeyboardLanguage = AndroidUtilities.getCurrentKeyboardLanguage();
                MediaDataController.getInstance(EmojiView.this.currentAccount).fetchNewEmojiKeywords(EmojiView.this.lastSearchKeyboardLanguage);
            }
        }
    }

    public /* synthetic */ void lambda$new$6(View view) {
        this.emojiSearchAdapter.resetSelectedPackId();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$13 */
    public class C429013 extends FrameLayout {
        public C429013(Context context2) {
            super(context2);
        }

        @Override // android.view.ViewGroup
        public boolean drawChild(Canvas canvas, View view3, long j) {
            if (view3 == EmojiView.this.gifGridView) {
                canvas.save();
                canvas.clipRect(0.0f, EmojiView.this.gifSearchField.getY() + EmojiView.this.gifSearchField.getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight());
                boolean zDrawChild = super.drawChild(canvas, view3, j);
                canvas.restore();
                return zDrawChild;
            }
            return super.drawChild(canvas, view3, j);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$14 */
    public class C429114 extends RecyclerListView {
        private boolean ignoreLayout;
        private boolean wasMeasured;

        public C429114(Context context2) {
            super(context2);
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return super.onInterceptTouchEvent(motionEvent) || ContentPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, EmojiView.this.gifGridView, 0, EmojiView.this.contentPreviewViewerDelegate, this.resourcesProvider);
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
        public void onMeasure(int i8, int i9) {
            super.onMeasure(i8, i9);
            if (this.wasMeasured) {
                return;
            }
            EmojiView.this.gifAdapter.notifyDataSetChanged();
            this.wasMeasured = true;
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z9, int i8, int i9, int i10, int i11) {
            if (EmojiView.this.firstGifAttach && EmojiView.this.gifAdapter.getItemCount() > 1) {
                this.ignoreLayout = true;
                EmojiView.this.gifLayoutManager.scrollToPositionWithOffset(0, 0);
                EmojiView.this.gifSearchField.setVisibility(0);
                EmojiView.this.gifTabs.onPageScrolled(0, 0);
                EmojiView.this.firstGifAttach = false;
                this.ignoreLayout = false;
            }
            super.onLayout(z9, i8, i9, i10, i11);
            EmojiView.this.checkGifSearchFieldScroll(true);
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (this.ignoreLayout) {
                return;
            }
            super.requestLayout();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$15 */
    public class C429215 extends RecyclerView.ItemDecoration {
        public C429215() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view3, RecyclerView recyclerView, RecyclerView.State state) {
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view3);
            if (EmojiView.this.gifGridView.getAdapter() == EmojiView.this.gifAdapter && childAdapterPosition == EmojiView.this.gifAdapter.trendingSectionItem) {
                rect.set(0, 0, 0, 0);
                return;
            }
            if (childAdapterPosition != 0 || !EmojiView.this.gifAdapter.addSearch) {
                rect.left = 0;
                rect.bottom = 0;
                rect.top = AndroidUtilities.m1036dp(2.0f);
                rect.right = EmojiView.this.gifLayoutManager.isLastInRow(childAdapterPosition - (EmojiView.this.gifAdapter.addSearch ? 1 : 0)) ? 0 : AndroidUtilities.m1036dp(2.0f);
                return;
            }
            rect.set(0, 0, 0, 0);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$16 */
    public class C429316 extends TypedScrollListener {
        public C429316(int i8) {
            super(i8);
        }

        @Override // org.telegram.ui.Components.EmojiView.TypedScrollListener, androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i8, int i9) {
            super.onScrolled(recyclerView, i8, i9);
            if (Build.VERSION.SDK_INT < 31 || EmojiView.this.scrollableViewNoiseSuppressor == null) {
                return;
            }
            EmojiView.this.scrollableViewNoiseSuppressor.onScrolled(i8, i9);
            EmojiView.this.invalidateBlurCaptures();
        }
    }

    public /* synthetic */ boolean lambda$new$7(Theme.ResourcesProvider resourcesProvider, View view, MotionEvent motionEvent) {
        return ContentPreviewViewer.getInstance().onTouch(motionEvent, this.gifGridView, 0, this.gifOnItemClickListener, this.contentPreviewViewerDelegate, resourcesProvider);
    }

    public /* synthetic */ void lambda$new$8(View view, int i) {
        if (this.delegate == null) {
            return;
        }
        int i2 = this.gifAdapter.addSearch ? i - 1 : i;
        RecyclerView.Adapter adapter = this.gifGridView.getAdapter();
        GifAdapter gifAdapter = this.gifAdapter;
        if (adapter != gifAdapter) {
            RecyclerView.Adapter adapter2 = this.gifGridView.getAdapter();
            GifAdapter gifAdapter2 = this.gifSearchAdapter;
            if (adapter2 != gifAdapter2 || i2 < 0 || i2 >= gifAdapter2.results.size()) {
                return;
            }
            this.delegate.onGifSelected(view, this.gifSearchAdapter.results.get(i2), this.gifSearchAdapter.lastSearchImageString, this.gifSearchAdapter.bot, true, 0, 0);
            updateRecentGifs();
            return;
        }
        if (i2 < 0) {
            return;
        }
        if (i2 < gifAdapter.recentItemsCount) {
            this.delegate.onGifSelected(view, this.recentGifs.get(i2), null, "gif", true, 0, 0);
            return;
        }
        if (this.gifAdapter.recentItemsCount > 0) {
            i2 = (i2 - this.gifAdapter.recentItemsCount) - 1;
        }
        if (i2 < 0 || i2 >= this.gifAdapter.results.size()) {
            return;
        }
        this.delegate.onGifSelected(view, this.gifAdapter.results.get(i2), null, this.gifAdapter.bot, true, 0, 0);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$17 */
    public class C429417 extends SearchField {
        public C429417(Context context2, int i8) {
            super(context2, i8);
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            if (getTranslationY() != f) {
                super.setTranslationY(f);
                EmojiView.this.gifContainer.invalidate();
            }
        }
    }

    public /* synthetic */ void lambda$new$9(int i) {
        if (i == this.gifTrendingTabNum && this.gifAdapter.results.isEmpty()) {
            return;
        }
        this.gifGridView.stopScroll();
        this.gifTabs.onPageScrolled(i, 0);
        int i2 = 1;
        if (i == this.gifRecentTabNum || i == this.gifTrendingTabNum) {
            this.gifSearchField.searchEditText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
            if (i == this.gifTrendingTabNum && this.gifAdapter.trendingSectionItem >= 1) {
                this.gifLayoutManager.scrollToPositionWithOffset(this.gifAdapter.trendingSectionItem, -AndroidUtilities.m1036dp(4.0f));
            } else {
                GifLayoutManager gifLayoutManager = this.gifLayoutManager;
                EmojiViewDelegate emojiViewDelegate = this.delegate;
                if (emojiViewDelegate != null && emojiViewDelegate.isExpanded()) {
                    i2 = 0;
                }
                gifLayoutManager.scrollToPositionWithOffset(i2, 0);
            }
            if (i == this.gifTrendingTabNum) {
                ArrayList<String> arrayList = MessagesController.getInstance(this.currentAccount).gifSearchEmojies;
                if (!arrayList.isEmpty()) {
                    this.gifSearchPreloader.preload(arrayList.get(0));
                }
            }
        } else {
            ArrayList<String> arrayList2 = MessagesController.getInstance(this.currentAccount).gifSearchEmojies;
            this.gifSearchAdapter.searchEmoji(arrayList2.get(i - this.gifFirstEmojiTabNum));
            int i3 = this.gifFirstEmojiTabNum;
            if (i - i3 > 0) {
                this.gifSearchPreloader.preload(arrayList2.get((i - i3) - 1));
            }
            if (i - this.gifFirstEmojiTabNum < arrayList2.size() - 1) {
                this.gifSearchPreloader.preload(arrayList2.get((i - this.gifFirstEmojiTabNum) + 1));
            }
        }
        resetTabsY(2);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$18 */
    public class C429518 extends FrameLayout {
        final /* synthetic */ boolean val$shouldDrawBackground;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C429518(Context context2, boolean z82) {
            super(context2);
            z = z82;
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            EmojiView.this.stickersContainerAttached = true;
            EmojiView.this.updateStickerTabsPosition();
            if (EmojiView.this.chooseStickerActionTracker != null) {
                EmojiView.this.chooseStickerActionTracker.checkVisibility();
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            EmojiView.this.stickersContainerAttached = false;
            EmojiView.this.updateStickerTabsPosition();
            if (EmojiView.this.chooseStickerActionTracker != null) {
                EmojiView.this.chooseStickerActionTracker.checkVisibility();
            }
        }

        @Override // android.view.ViewGroup
        public boolean drawChild(Canvas canvas, View view3, long j) {
            if (!z && (view3 == EmojiView.this.stickersGridView || view3 == EmojiView.this.stickersSearchField)) {
                canvas.save();
                float y = EmojiView.this.stickersTab.getY() + EmojiView.this.stickersTab.getMeasuredHeight() + 1.0f;
                if (view3 == EmojiView.this.stickersGridView) {
                    y = Math.max(y, EmojiView.this.stickersSearchField.getY() + EmojiView.this.stickersSearchField.getMeasuredHeight() + 1.0f);
                }
                canvas.clipRect(0.0f, y - (AndroidUtilities.m1036dp(16.0f) * EmojiView.this.animatorSearchStickerPackSelected.getFloatValue()), getMeasuredWidth(), getMeasuredHeight());
                boolean zDrawChild = super.drawChild(canvas, view3, j);
                canvas.restore();
                return zDrawChild;
            }
            return super.drawChild(canvas, view3, j);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$19 */
    public class C429619 extends RecyclerListViewWithOverlayDraw {
        boolean ignoreLayout;

        public C429619(Context context2) {
            super(context2);
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (EmojiView.this.ignorePagerScroll) {
                return false;
            }
            return super.onInterceptTouchEvent(motionEvent) || ContentPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, EmojiView.this.stickersGridView, EmojiView.this.getMeasuredHeight(), EmojiView.this.contentPreviewViewerDelegate, this.resourcesProvider);
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, android.view.View
        public void setVisibility(int i8) {
            super.setVisibility(i8);
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z9, int i8, int i9, int i10, int i11) {
            if (EmojiView.this.firstStickersAttach && EmojiView.this.stickersGridAdapter.getItemCount() > 0) {
                this.ignoreLayout = true;
                EmojiView.this.stickersLayoutManager.scrollToPositionWithOffset(0, 0);
                EmojiView.this.firstStickersAttach = false;
                this.ignoreLayout = false;
            }
            super.onLayout(z9, i8, i9, i10, i11);
            EmojiView.this.checkStickersSearchFieldScroll(true);
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (this.ignoreLayout) {
                return;
            }
            super.requestLayout();
        }

        @Override // androidx.recyclerview.widget.RecyclerView
        public void onScrolled(int i8, int i9) {
            super.onScrolled(i8, i9);
            if (Build.VERSION.SDK_INT >= 31 && EmojiView.this.scrollableViewNoiseSuppressor != null) {
                EmojiView.this.scrollableViewNoiseSuppressor.onScrolled(i8, i9);
                EmojiView.this.invalidateBlurCaptures();
            }
            if (EmojiView.this.stickersTabContainer != null) {
                EmojiView.this.stickersTab.setUnderlineHeight(EmojiView.this.stickersGridView.canScrollVertically(-1) ? AndroidUtilities.getShadowHeight() : 0);
            }
            if (EmojiView.this.stickersSearchGridAdapter == null || getAdapter() != EmojiView.this.stickersSearchGridAdapter || EmojiView.this.stickersSearchGridAdapter.selectedPackId != 0 || EmojiView.this.stickersSearchGridAdapter.searchRunnable.isLoading() || EmojiView.this.stickersSearchGridAdapter.searchRunnable.isCompleted()) {
                return;
            }
            if (EmojiView.this.stickersLayoutManager.findLastVisibleItemPosition() + 50 > EmojiView.this.stickersSearchGridAdapter.getItemCount()) {
                SearchRunnable searchRunnable = EmojiView.this.stickersSearchGridAdapter.searchRunnable;
                Objects.requireNonNull(searchRunnable);
                AndroidUtilities.runOnUIThread(new EmojiView$19$$ExternalSyntheticLambda0(searchRunnable));
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$20 */
    public class C429820 extends GridLayoutManager {
        public C429820(Context context2, int i8) {
            super(context2, i8);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i8) {
            try {
                LinearSmoothScrollerCustom linearSmoothScrollerCustom = new LinearSmoothScrollerCustom(recyclerView.getContext(), 2);
                linearSmoothScrollerCustom.setTargetPosition(i8);
                startSmoothScroll(linearSmoothScrollerCustom);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public int scrollVerticallyBy(int i8, RecyclerView.Recycler recycler, RecyclerView.State state) {
            int iScrollVerticallyBy = super.scrollVerticallyBy(i8, recycler, state);
            if (iScrollVerticallyBy != 0 && EmojiView.this.stickersGridView.getScrollState() == 1) {
                EmojiView.this.expandStickersByDragg = false;
                EmojiView.this.updateStickerTabsPosition();
            }
            if (EmojiView.this.chooseStickerActionTracker == null) {
                EmojiView.this.createStickersChooseActionTracker();
            }
            EmojiView.this.chooseStickerActionTracker.doSomeAction();
            return iScrollVerticallyBy;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$21 */
    public class C429921 extends GridLayoutManager.SpanSizeLookup {
        public C429921() {
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int i8) {
            if (EmojiView.this.stickersGridView.getAdapter() == EmojiView.this.stickersGridAdapter) {
                EmojiView emojiView = EmojiView.this;
                if (i8 == 0) {
                    return emojiView.stickersGridAdapter.stickersPerRow;
                }
                if (i8 == emojiView.stickersGridAdapter.totalItems || !(EmojiView.this.stickersGridAdapter.cache.get(i8) == null || (EmojiView.this.stickersGridAdapter.cache.get(i8) instanceof TLRPC.Document))) {
                    return EmojiView.this.stickersGridAdapter.stickersPerRow;
                }
                return 1;
            }
            if (i8 == EmojiView.this.stickersSearchGridAdapter.totalItems || !(EmojiView.this.stickersSearchGridAdapter.cache.get(i8) == null || (EmojiView.this.stickersSearchGridAdapter.cache.get(i8) instanceof TLRPC.Document))) {
                return EmojiView.this.stickersGridAdapter.stickersPerRow;
            }
            return 1;
        }
    }

    public /* synthetic */ boolean lambda$new$10(Theme.ResourcesProvider resourcesProvider, View view, MotionEvent motionEvent) {
        return ContentPreviewViewer.getInstance().onTouch(motionEvent, this.stickersGridView, getMeasuredHeight(), this.stickersOnItemClickListener, this.contentPreviewViewerDelegate, resourcesProvider);
    }

    public /* synthetic */ void lambda$new$11(View view, int i) {
        RecyclerView.Adapter adapter = this.stickersGridView.getAdapter();
        StickersSearchGridAdapter stickersSearchGridAdapter = this.stickersSearchGridAdapter;
        String str = adapter == stickersSearchGridAdapter ? stickersSearchGridAdapter.searchQuery : null;
        if (view instanceof StickerEmojiCell) {
            StickerEmojiCell stickerEmojiCell = (StickerEmojiCell) view;
            if (stickerEmojiCell.getSticker() != null && MessageObject.isPremiumSticker(stickerEmojiCell.getSticker()) && !AccountInstance.getInstance(this.currentAccount).getUserConfig().isPremium()) {
                ContentPreviewViewer.getInstance().showMenuFor(stickerEmojiCell);
                return;
            }
            ContentPreviewViewer.getInstance().reset();
            if (stickerEmojiCell.isDisabled()) {
                return;
            }
            stickerEmojiCell.disable();
            this.delegate.onStickerSelected(stickerEmojiCell, stickerEmojiCell.getSticker(), str, stickerEmojiCell.getParentObject(), stickerEmojiCell.getSendAnimationData(), true, 0, 0);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$22 */
    public class C430022 extends SearchField {
        public C430022(Context context2, int i8) {
            super(context2, i8);
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            if (f != getTranslationY()) {
                super.setTranslationY(f);
                EmojiView.this.stickersContainer.invalidate();
            }
        }
    }

    public /* synthetic */ void lambda$new$12(View view) {
        this.stickersSearchGridAdapter.resetSelectedPackId();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$23 */
    public class C430123 extends DraggableScrollSlidingTabStrip {
        final /* synthetic */ BaseFragment val$fragment;
        final /* synthetic */ boolean val$shouldDrawBackground;

        /* JADX INFO: renamed from: $r8$lambda$zgL8FEgwNp-COrFHgecDnqVhLX0 */
        public static /* synthetic */ void m11738$r8$lambda$zgL8FEgwNpCOrFHgecDnqVhLX0(TLObject tLObject, TLRPC.TL_error tL_error) {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C430123(Context context, Theme.ResourcesProvider resourcesProvider, BaseFragment baseFragment, boolean z) {
            super(context, resourcesProvider);
            this.val$fragment = baseFragment;
            this.val$shouldDrawBackground = z;
        }

        @Override // org.telegram.p035ui.Components.ScrollSlidingTabStrip
        public void updatePosition() {
            EmojiView.this.updateStickerTabsPosition();
            if (EmojiView.this.stickersTabContainer != null) {
                EmojiView.this.stickersTabContainer.invalidate();
            }
            invalidate();
            if (EmojiView.this.delegate != null) {
                EmojiView.this.delegate.invalidateEnterView();
            }
        }

        @Override // org.telegram.p035ui.Components.ScrollSlidingTabStrip
        public void stickerSetPositionChanged(int i, int i2) {
            int i3 = i - EmojiView.this.stickersTabOffset;
            int i4 = i2 - EmojiView.this.stickersTabOffset;
            MediaDataController mediaDataController = MediaDataController.getInstance(EmojiView.this.currentAccount);
            swapListElements(EmojiView.this.stickerSets, i3, i4);
            Collections.sort(mediaDataController.getStickerSets(0), new Comparator() { // from class: org.telegram.ui.Components.EmojiView$23$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return this.f$0.lambda$stickerSetPositionChanged$0((TLRPC.TL_messages_stickerSet) obj, (TLRPC.TL_messages_stickerSet) obj2);
                }
            });
            ArrayList<TLRPC.TL_messages_stickerSet> arrayList = EmojiView.this.frozenStickerSets;
            if (arrayList != null) {
                arrayList.clear();
                EmojiView emojiView = EmojiView.this;
                emojiView.frozenStickerSets.addAll(emojiView.stickerSets);
            }
            EmojiView.this.reloadStickersAdapter();
            AndroidUtilities.cancelRunOnUIThread(EmojiView.this.checkExpandStickerTabsRunnable);
            AndroidUtilities.runOnUIThread(EmojiView.this.checkExpandStickerTabsRunnable, 1500L);
            sendReorder();
            EmojiView.this.updateStickerTabs(true);
            if (SharedConfig.updateStickersOrderOnSend) {
                SharedConfig.toggleUpdateStickersOrderOnSend();
                BaseFragment baseFragment = this.val$fragment;
                if (baseFragment != null) {
                    BulletinFactory bulletinFactoryM1143of = BulletinFactory.m1143of(baseFragment);
                    int i5 = C2797R.raw.filter_reorder;
                    String string = LocaleController.getString(C2797R.string.DynamicPackOrderOff);
                    String string2 = LocaleController.getString(C2797R.string.DynamicPackOrderOffInfo);
                    String string3 = LocaleController.getString("Settings");
                    final BaseFragment baseFragment2 = this.val$fragment;
                    bulletinFactoryM1143of.createSimpleBulletin(i5, string, string2, string3, new Runnable() { // from class: org.telegram.ui.Components.EmojiView$23$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            baseFragment2.presentFragment(new StickersActivity(0, null));
                        }
                    }).show();
                    return;
                }
                if (EmojiView.this.bulletinContainer != null) {
                    BulletinFactory.m1142of(EmojiView.this.bulletinContainer, EmojiView.this.resourcesProvider).createSimpleBulletin(C2797R.raw.filter_reorder, LocaleController.getString(C2797R.string.DynamicPackOrderOff), LocaleController.getString(C2797R.string.DynamicPackOrderOffInfo)).show();
                }
            }
        }

        public /* synthetic */ int lambda$stickerSetPositionChanged$0(TLRPC.TL_messages_stickerSet tL_messages_stickerSet, TLRPC.TL_messages_stickerSet tL_messages_stickerSet2) {
            int iIndexOf = EmojiView.this.stickerSets.indexOf(tL_messages_stickerSet);
            int iIndexOf2 = EmojiView.this.stickerSets.indexOf(tL_messages_stickerSet2);
            if (iIndexOf < 0 || iIndexOf2 < 0) {
                return 0;
            }
            return iIndexOf - iIndexOf2;
        }

        private void swapListElements(List<TLRPC.TL_messages_stickerSet> list, int i, int i2) {
            list.add(i2, list.remove(i));
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v2, types: [int] */
        /* JADX WARN: Type inference failed for: r2v8 */
        /* JADX WARN: Type inference failed for: r2v9 */
        /* JADX WARN: Type inference failed for: r4v1, types: [java.util.ArrayList] */
        private void sendReorder() {
            MediaDataController.getInstance(EmojiView.this.currentAccount).calcNewHash(0);
            TLRPC.TL_messages_reorderStickerSets tL_messages_reorderStickerSets = new TLRPC.TL_messages_reorderStickerSets();
            tL_messages_reorderStickerSets.masks = false;
            tL_messages_reorderStickerSets.emojis = false;
            for (?? r2 = EmojiView.this.hasChatStickers; r2 < EmojiView.this.stickerSets.size(); r2++) {
                tL_messages_reorderStickerSets.order.add(Long.valueOf(((TLRPC.TL_messages_stickerSet) EmojiView.this.stickerSets.get(r2)).set.f1280id));
            }
            ConnectionsManager.getInstance(EmojiView.this.currentAccount).sendRequest(tL_messages_reorderStickerSets, new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$23$$ExternalSyntheticLambda2
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    EmojiView.C430123.m11738$r8$lambda$zgL8FEgwNpCOrFHgecDnqVhLX0(tLObject, tL_error);
                }
            });
            NotificationCenter.getInstance(EmojiView.this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.stickersDidLoad, 0, Boolean.TRUE);
        }

        @Override // org.telegram.p035ui.Components.ScrollSlidingTabStrip
        public void invalidateOverlays() {
            if (EmojiView.this.stickersTabContainer != null) {
                EmojiView.this.stickersTabContainer.invalidate();
            }
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            if (getTranslationY() != f) {
                super.setTranslationY(f);
                if (this.val$shouldDrawBackground) {
                    return;
                }
                EmojiView.this.stickersContainer.invalidate();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$24 */
    public class C430224 extends FrameLayout {
        Paint paint = new Paint();

        public C430224(Context context2) {
            super(context2);
            this.paint = new Paint();
        }

        @Override // android.view.ViewGroup, android.view.View
        public void dispatchDraw(Canvas canvas) {
            float fM1036dp = AndroidUtilities.m1036dp(50.0f) * EmojiView.this.delegate.getProgressToSearchOpened();
            if (fM1036dp > getMeasuredHeight()) {
                return;
            }
            canvas.save();
            if (fM1036dp != 0.0f) {
                canvas.clipRect(0.0f, fM1036dp, getMeasuredWidth(), getMeasuredHeight());
            }
            this.paint.setColor(EmojiView.this.getThemedColor(Theme.key_chat_emojiPanelBackground));
            canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), AndroidUtilities.m1036dp(36.0f) + EmojiView.this.stickersTab.getExpandedOffset(), this.paint);
            super.dispatchDraw(canvas);
            EmojiView.this.stickersTab.drawOverlays(canvas);
            canvas.restore();
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z9, int i8, int i9, int i10, int i11) {
            super.onLayout(z9, i8, i9, i10, i11);
            EmojiView.this.updateStickerTabsPosition();
        }
    }

    public /* synthetic */ void lambda$new$13(int i) {
        if (this.firstTabUpdate) {
            return;
        }
        if (i == this.trendingTabNum) {
            openTrendingStickers(null);
            return;
        }
        SearchField searchField = this.stickersSearchField;
        if (searchField != null && searchField.isCategorySelected()) {
            this.stickersSearchField.search(null, false);
            this.stickersSearchField.categoriesListView.selectCategory((StickerCategoriesListView.EmojiCategory) null);
        }
        if (i == this.recentTabNum) {
            this.stickersGridView.stopScroll();
            scrollStickersToPosition(this.stickersGridAdapter.getPositionForPack("recent"), 0);
            resetTabsY(0);
            ScrollSlidingTabStrip scrollSlidingTabStrip = this.stickersTab;
            int i2 = this.recentTabNum;
            scrollSlidingTabStrip.onPageScrolled(i2, i2 > 0 ? i2 : this.stickersTabOffset);
            return;
        }
        if (i == this.favTabNum) {
            this.stickersGridView.stopScroll();
            scrollStickersToPosition(this.stickersGridAdapter.getPositionForPack("fav"), 0);
            resetTabsY(0);
            ScrollSlidingTabStrip scrollSlidingTabStrip2 = this.stickersTab;
            int i3 = this.favTabNum;
            scrollSlidingTabStrip2.onPageScrolled(i3, i3 > 0 ? i3 : this.stickersTabOffset);
            return;
        }
        if (i == this.premiumTabNum) {
            this.stickersGridView.stopScroll();
            scrollStickersToPosition(this.stickersGridAdapter.getPositionForPack("premium"), 0);
            resetTabsY(0);
            ScrollSlidingTabStrip scrollSlidingTabStrip3 = this.stickersTab;
            int i4 = this.premiumTabNum;
            scrollSlidingTabStrip3.onPageScrolled(i4, i4 > 0 ? i4 : this.stickersTabOffset);
            return;
        }
        int size = i - this.stickersTabOffset;
        if (size >= this.stickerSets.size()) {
            return;
        }
        if (size >= this.stickerSets.size()) {
            size = this.stickerSets.size() - 1;
        }
        this.firstStickersAttach = false;
        this.stickersGridView.stopScroll();
        scrollStickersToPosition(this.stickersGridAdapter.getPositionForPack(this.stickerSets.get(size)), 0);
        resetTabsY(0);
        checkScroll(0);
        int i5 = this.favTabNum;
        if (i5 <= 0 && (i5 = this.recentTabNum) <= 0) {
            i5 = this.stickersTabOffset;
        }
        this.stickersTab.onPageScrolled(i, i5);
        this.expandStickersByDragg = false;
        updateStickerTabsPosition();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$25 */
    public class C430325 extends ViewPager {
        public C430325(Context context2) {
            super(context2);
        }

        @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (EmojiView.this.ignorePagerScroll) {
                return false;
            }
            if (getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(canScrollHorizontally(-1));
            }
            try {
                return super.onInterceptTouchEvent(motionEvent);
            } catch (IllegalArgumentException unused) {
                return false;
            }
        }

        @Override // androidx.viewpager.widget.ViewPager
        public void setCurrentItem(int i8, boolean z9) {
            EmojiView.this.startStopVisibleGifs(i8 == 1);
            if (i8 != getCurrentItem()) {
                super.setCurrentItem(i8, z9);
                return;
            }
            if (i8 == 0) {
                EmojiView.this.tabsMinusDy[1] = 0;
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(EmojiView.this.emojiTabs, (Property<EmojiTabsStrip, Float>) ViewGroup.TRANSLATION_Y, 0.0f);
                objectAnimatorOfFloat.setDuration(150L);
                objectAnimatorOfFloat.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                objectAnimatorOfFloat.start();
                EmojiView.this.scrollEmojisToPosition(1, 0);
                if (EmojiView.this.emojiTabs != null) {
                    EmojiView.this.emojiTabs.select(0);
                    return;
                }
                return;
            }
            EmojiView emojiView = EmojiView.this;
            if (i8 == 1) {
                emojiView.gifGridView.smoothScrollToPosition(0);
            } else {
                emojiView.stickersGridView.smoothScrollToPosition(1);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$26 */
    public class C430426 extends ImageView {
        public C430426(Context context2) {
            super(context2);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                EmojiView.this.backspacePressed = true;
                EmojiView.this.backspaceOnce = false;
                EmojiView.this.postBackspaceRunnable(350);
            } else if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
                EmojiView.this.backspacePressed = false;
                if (!EmojiView.this.backspaceOnce && EmojiView.this.delegate != null && EmojiView.this.delegate.onBackspace()) {
                    try {
                        EmojiView.this.backspaceButton.performHapticFeedback(3);
                    } catch (Exception unused) {
                    }
                }
            }
            super.onTouchEvent(motionEvent);
            return true;
        }
    }

    public /* synthetic */ void lambda$new$15(View view) {
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate != null) {
            emojiViewDelegate.onStickersSettingsClick();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$27 */
    public class C430527 implements ViewPager.OnPageChangeListener {
        final /* synthetic */ boolean val$shouldDrawBackground;

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i9) {
        }

        public C430527(boolean z82) {
            z = z82;
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i9, float f, int i10) {
            SearchField searchField;
            SearchField searchField2;
            EmojiView.this.checkGridVisibility(i9, f);
            EmojiView emojiView = EmojiView.this;
            emojiView.onPageScrolled(i9, (emojiView.getMeasuredWidth() - EmojiView.this.getPaddingLeft()) - EmojiView.this.getPaddingRight(), i10);
            boolean z9 = true;
            EmojiView.this.showBottomTab(true, true);
            int currentItem = EmojiView.this.pager.getCurrentItem();
            if (currentItem == 0) {
                searchField = EmojiView.this.emojiSearchField;
            } else {
                EmojiView emojiView2 = EmojiView.this;
                if (currentItem == 1) {
                    searchField = emojiView2.gifSearchField;
                } else {
                    searchField = emojiView2.stickersSearchField;
                }
            }
            String string = searchField.searchEditText.getText().toString();
            for (int i11 = 0; i11 < 3; i11++) {
                if (i11 == 0) {
                    searchField2 = EmojiView.this.emojiSearchField;
                } else {
                    EmojiView emojiView3 = EmojiView.this;
                    if (i11 == 1) {
                        searchField2 = emojiView3.gifSearchField;
                    } else {
                        searchField2 = emojiView3.stickersSearchField;
                    }
                }
                if (searchField2 != null && searchField2 != searchField && searchField2.searchEditText != null && !searchField2.searchEditText.getText().toString().equals(string)) {
                    searchField2.searchEditText.setText(string);
                    searchField2.searchEditText.setSelection(string.length());
                }
            }
            EmojiView emojiView4 = EmojiView.this;
            if ((i9 != 0 || f <= 0.0f) && i9 != 1) {
                z9 = false;
            }
            emojiView4.startStopVisibleGifs(z9);
            EmojiView.this.updateStickerTabsPosition();
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i9) {
            EmojiView.this.saveNewPage();
            boolean z9 = false;
            EmojiView.this.showBackspaceButton(i9 == 0, true);
            EmojiView emojiView = EmojiView.this;
            if (i9 == 2 && (z || emojiView.shouldDrawStickerSettings)) {
                z9 = true;
            }
            emojiView.showStickerSettingsButton(z9, true);
            if (EmojiView.this.delegate.isSearchOpened()) {
                if (i9 == 0) {
                    if (EmojiView.this.emojiSearchField != null) {
                        EmojiView.this.emojiSearchField.searchEditText.requestFocus();
                        return;
                    }
                    return;
                }
                EmojiView emojiView2 = EmojiView.this;
                if (i9 == 1) {
                    if (emojiView2.gifSearchField != null) {
                        EmojiView.this.gifSearchField.searchEditText.requestFocus();
                    }
                } else if (emojiView2.stickersSearchField != null) {
                    EmojiView.this.stickersSearchField.searchEditText.requestFocus();
                }
            }
        }
    }

    public /* synthetic */ void lambda$new$16(View view) {
        SearchField searchField;
        int currentItem = this.pager.getCurrentItem();
        if (currentItem == 0) {
            searchField = this.emojiSearchField;
        } else if (currentItem == 1) {
            searchField = this.gifSearchField;
        } else {
            searchField = this.stickersSearchField;
        }
        if (searchField == null) {
            return;
        }
        searchField.searchEditText.requestFocus();
        MotionEvent motionEventObtain = MotionEvent.obtain(0L, 0L, 0, 0.0f, 0.0f, 0);
        searchField.searchEditText.onTouchEvent(motionEventObtain);
        motionEventObtain.recycle();
        MotionEvent motionEventObtain2 = MotionEvent.obtain(0L, 0L, 1, 0.0f, 0.0f, 0);
        searchField.searchEditText.onTouchEvent(motionEventObtain2);
        motionEventObtain2.recycle();
    }

    public /* synthetic */ void lambda$new$17(Integer num, Integer num2) {
        ImageViewEmoji imageViewEmoji = this.emojiTouchedView;
        if (imageViewEmoji == null || !(imageViewEmoji.getDrawable() instanceof CompoundEmoji.CompoundEmojiDrawable)) {
            return;
        }
        ((CompoundEmoji.CompoundEmojiDrawable) this.emojiTouchedView.getDrawable()).update(num.intValue(), num2.intValue());
        String str = (String) this.emojiTouchedView.getTag();
        if (num.intValue() == -1 && num2.intValue() == -1) {
            Emoji.emojiColor.remove(str);
        } else {
            StringBuilder sb = new StringBuilder();
            int iIntValue = num.intValue();
            String str2 = _UrlKt.FRAGMENT_ENCODE_SET;
            sb.append(iIntValue >= 0 ? CompoundEmoji.skinTones.get(num.intValue()) : _UrlKt.FRAGMENT_ENCODE_SET);
            sb.append("\u200d");
            if (num2.intValue() >= 0) {
                str2 = CompoundEmoji.skinTones.get(num2.intValue());
            }
            sb.append(str2);
            Emoji.emojiColor.put(str, sb.toString());
        }
        Emoji.saveEmojiColors();
    }

    public /* synthetic */ void lambda$new$18(View view, RectF rectF) {
        invalidateBlurCaptures();
    }

    public /* synthetic */ void lambda$new$19() {
        this.emojiGridView.postOnAnimation(new EmojiView$$ExternalSyntheticLambda27(this));
    }

    public /* synthetic */ void lambda$new$20() {
        this.gifGridView.postOnAnimation(new EmojiView$$ExternalSyntheticLambda27(this));
    }

    public /* synthetic */ void lambda$new$21() {
        this.stickersGridView.postOnAnimation(new EmojiView$$ExternalSyntheticLambda27(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ boolean lambda$new$22(Canvas canvas, View view, long j) {
        if (view instanceof RecyclerListViewWithOverlayDraw.OverlayView) {
            canvas.save();
            canvas.translate(view.getX(), view.getY());
            ((RecyclerListViewWithOverlayDraw.OverlayView) view).preDraw(this.stickersGridView, canvas);
            canvas.restore();
        }
        return this.stickersGridView.drawChild(canvas, view, j);
    }

    /* JADX INFO: renamed from: $r8$lambda$4HN73U0mD-wgTGy7HxQE4G45_LQ */
    public static /* synthetic */ void m11553$r8$lambda$4HN73U0mDwgTGy7HxQE4G45_LQ(IBlur3Capture[] iBlur3CaptureArr, Canvas canvas, RectF rectF) {
        for (IBlur3Capture iBlur3Capture : iBlur3CaptureArr) {
            if (iBlur3Capture != null) {
                iBlur3Capture.capture(canvas, rectF);
            }
        }
    }

    public void forceHideSettingsButton() {
        this.mForceHideSettingsButton = true;
        ImageView imageView = this.stickerSettingsButton;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
    }

    public void forceHideBackspaceButton() {
        this.mForceHideBackspaceButton = true;
        ImageView imageView = this.backspaceButton;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
    }

    public void setBlurredBackgroundDrawableFactory(BlurredBackgroundDrawableViewFactory blurredBackgroundDrawableViewFactory) {
        ImageView imageView = this.backspaceButton;
        if (imageView != null) {
            imageView.setBackground(blurredBackgroundDrawableViewFactory.create(imageView).setColorProvider(BlurredBackgroundProviderImpl.emojiViewButton(this.resourcesProvider)).setRadius(AndroidUtilities.m1036dp(18.0f)).setPadding(AndroidUtilities.m1036dp(6.0f)));
        }
        ImageView imageView2 = this.searchButton;
        if (imageView2 != null) {
            imageView2.setBackground(blurredBackgroundDrawableViewFactory.create(imageView2).setColorProvider(BlurredBackgroundProviderImpl.emojiViewButton(this.resourcesProvider)).setRadius(AndroidUtilities.m1036dp(18.0f)).setPadding(AndroidUtilities.m1036dp(6.0f)));
        }
        PagerSlidingTabStrip pagerSlidingTabStrip = this.typeTabs;
        if (pagerSlidingTabStrip != null) {
            pagerSlidingTabStrip.setBackground(blurredBackgroundDrawableViewFactory.create(pagerSlidingTabStrip).setColorProvider(BlurredBackgroundProviderImpl.emojiViewButton(this.resourcesProvider)).setRadius(AndroidUtilities.m1036dp(18.0f)).setPadding(AndroidUtilities.m1036dp(6.0f)));
        }
        ImageView imageView3 = this.stickerSettingsButton;
        if (imageView3 != null) {
            imageView3.setBackground(blurredBackgroundDrawableViewFactory.create(imageView3).setColorProvider(BlurredBackgroundProviderImpl.emojiViewButton(this.resourcesProvider)).setRadius(AndroidUtilities.m1036dp(18.0f)).setPadding(AndroidUtilities.m1036dp(6.0f)));
        }
    }

    public void setBottomInset(int i) {
        if (this.bottomInset != i) {
            this.bottomInset = i;
            applyBottomInsetAsPadding(this.emojiAddPackButtonContainer, i);
            applyBottomInsetAsPadding(this.stickerAddPackButtonContainer, i);
            applyBottomInsetAsPadding(this.emojiGridView, AndroidUtilities.m1036dp(44.0f) + i);
            applyBottomInsetAsPadding(this.stickersGridView, AndroidUtilities.m1036dp(44.0f) + i);
            applyBottomInsetAsPadding(this.gifGridView, AndroidUtilities.m1036dp(44.0f) + i);
            FrameLayout frameLayout = this.bulletinContainer2;
            if (frameLayout != null) {
                frameLayout.setTranslationY(-i);
            }
            updateBottomTabContainerPosition();
            invalidate();
        }
    }

    @Override // org.telegram.p035ui.Components.inset.InAppKeyboardInsetView
    public void applyNavigationBarHeight(int i) {
        setBottomInset(i);
    }

    @Override // org.telegram.p035ui.Components.inset.InAppKeyboardInsetView
    public void applyInAppKeyboardAnimatedHeight(float f) {
        this.visibleInAppKeyboardHeight = f;
        updateBottomTabContainerPosition();
    }

    private static void applyBottomInsetAsPadding(View view, int i) {
        if (view == null) {
            return;
        }
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), i);
    }

    public class EmojiGridView extends RecyclerListView {
        private SparseIntArray headerWidthsCache;
        private boolean ignoreLayout;
        private int lastChildCount;
        ArrayList<DrawingInBackgroundLine> lineDrawables;
        ArrayList<DrawingInBackgroundLine> lineDrawablesTmp;
        private AnimatedFloat premiumT;
        private SparseArray<TouchDownInfo> touches;
        ArrayList<ArrayList<ImageViewEmoji>> unusedArrays;
        ArrayList<DrawingInBackgroundLine> unusedLineDrawables;
        SparseArray<ArrayList<ImageViewEmoji>> viewsGroupedByLines;

        public EmojiGridView(Context context) {
            super(context);
            this.viewsGroupedByLines = new SparseArray<>();
            this.lineDrawables = new ArrayList<>();
            this.lineDrawablesTmp = new ArrayList<>();
            this.unusedArrays = new ArrayList<>();
            this.unusedLineDrawables = new ArrayList<>();
            this.lastChildCount = -1;
            this.headerWidthsCache = new SparseIntArray();
            this.premiumT = new AnimatedFloat(this, 350L, CubicBezierInterpolator.EASE_OUT_QUINT);
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return super.onInterceptTouchEvent(motionEvent) || ContentPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, this, 0, EmojiView.this.contentPreviewViewerDelegate, this.resourcesProvider);
        }

        private AnimatedEmojiSpan[] getAnimatedEmojiSpans() {
            AnimatedEmojiSpan[] animatedEmojiSpanArr = new AnimatedEmojiSpan[EmojiView.this.emojiGridView.getChildCount()];
            for (int i = 0; i < EmojiView.this.emojiGridView.getChildCount(); i++) {
                View childAt = EmojiView.this.emojiGridView.getChildAt(i);
                if (childAt instanceof ImageViewEmoji) {
                    animatedEmojiSpanArr[i] = ((ImageViewEmoji) childAt).getSpan();
                }
            }
            return animatedEmojiSpanArr;
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
        public void onMeasure(int i, int i2) {
            this.ignoreLayout = true;
            int size = View.MeasureSpec.getSize(i);
            int spanCount = EmojiView.this.emojiLayoutManager.getSpanCount();
            EmojiView.this.emojiLayoutManager.setSpanCount(Math.max(1, size / AndroidUtilities.m1036dp(AndroidUtilities.isTablet() ? 60.0f : 45.0f)));
            this.ignoreLayout = false;
            super.onMeasure(i, i2);
            if (spanCount != EmojiView.this.emojiLayoutManager.getSpanCount()) {
                EmojiView.this.emojiAdapter.notifyDataSetChanged();
            }
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            if (EmojiView.this.needEmojiSearch && EmojiView.this.firstEmojiAttach) {
                this.ignoreLayout = true;
                EmojiView.this.emojiLayoutManager.scrollToPositionWithOffset(0, 0);
                EmojiView.this.firstEmojiAttach = false;
                this.ignoreLayout = false;
            }
            super.onLayout(z, i, i2, i3, i4);
            EmojiView.this.checkEmojiSearchFieldScroll(true);
            updateEmojiDrawables();
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (this.ignoreLayout) {
                return;
            }
            super.requestLayout();
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (EmojiView.this.emojiTouchedView != null && EmojiView.this.colorPickerView != null) {
                if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                    if (EmojiView.this.colorPickerView != null && EmojiView.this.colorPickerView.isShowing() && !EmojiView.this.colorPickerView.isCompound()) {
                        EmojiView.this.colorPickerView.dismiss();
                        String skinTone = EmojiView.this.colorPickerView.getSkinTone(0);
                        String strAddColorToCode = (String) EmojiView.this.emojiTouchedView.getTag();
                        if (!EmojiView.this.emojiTouchedView.isRecent) {
                            if (skinTone != null) {
                                Emoji.emojiColor.put(strAddColorToCode, skinTone);
                                strAddColorToCode = EmojiView.addColorToCode(strAddColorToCode, skinTone);
                            } else {
                                Emoji.emojiColor.remove(strAddColorToCode);
                            }
                            EmojiView.this.emojiTouchedView.setImageDrawable(Emoji.getEmojiBigDrawable(strAddColorToCode), EmojiView.this.emojiTouchedView.isRecent);
                            EmojiView emojiView = EmojiView.this;
                            emojiView.sendEmoji(emojiView.emojiTouchedView, null);
                            try {
                                performHapticFeedback(VibratorUtils.getType(3), 1);
                            } catch (Exception unused) {
                            }
                            Emoji.saveEmojiColors();
                        } else {
                            String strReplace = strAddColorToCode.replace("🏻", _UrlKt.FRAGMENT_ENCODE_SET).replace("🏼", _UrlKt.FRAGMENT_ENCODE_SET).replace("🏽", _UrlKt.FRAGMENT_ENCODE_SET).replace("🏾", _UrlKt.FRAGMENT_ENCODE_SET).replace("🏿", _UrlKt.FRAGMENT_ENCODE_SET);
                            EmojiView emojiView2 = EmojiView.this;
                            if (skinTone != null) {
                                emojiView2.sendEmoji(emojiView2.emojiTouchedView, EmojiView.addColorToCode(strReplace, skinTone));
                            } else {
                                emojiView2.sendEmoji(emojiView2.emojiTouchedView, strReplace);
                            }
                        }
                    }
                    if (EmojiView.this.colorPickerView == null || !EmojiView.this.colorPickerView.isCompound()) {
                        EmojiView.this.emojiTouchedView = null;
                    }
                    EmojiView.this.emojiTouchedX = -10000.0f;
                    EmojiView.this.emojiTouchedY = -10000.0f;
                } else if (motionEvent.getAction() == 2) {
                    if (EmojiView.this.emojiTouchedX == -10000.0f) {
                        getLocationOnScreen(EmojiView.this.location);
                        float x = EmojiView.this.location[0] + motionEvent.getX();
                        EmojiView.this.colorPickerView.pickerView.getLocationOnScreen(EmojiView.this.location);
                        EmojiView.this.colorPickerView.onTouchMove((int) (x - (EmojiView.this.location[0] + AndroidUtilities.m1036dp(3.0f))));
                    } else if (Math.abs(EmojiView.this.emojiTouchedX - motionEvent.getX()) > AndroidUtilities.getPixelsInCM(0.2f, true) || Math.abs(EmojiView.this.emojiTouchedY - motionEvent.getY()) > AndroidUtilities.getPixelsInCM(0.2f, false)) {
                        EmojiView.this.emojiTouchedX = -10000.0f;
                        EmojiView.this.emojiTouchedY = -10000.0f;
                        getLocationOnScreen(EmojiView.this.location);
                        float x2 = EmojiView.this.location[0] + motionEvent.getX();
                        EmojiView.this.colorPickerView.pickerView.getLocationOnScreen(EmojiView.this.location);
                        EmojiView.this.colorPickerView.onTouchMove((int) (x2 - (EmojiView.this.location[0] + AndroidUtilities.m1036dp(3.0f))));
                    }
                }
                if (EmojiView.this.colorPickerView == null || !EmojiView.this.colorPickerView.isCompound() || EmojiView.this.colorPickerView.isShowing()) {
                    return true;
                }
            }
            EmojiView.this.emojiLastX = motionEvent.getX();
            EmojiView.this.emojiLastY = motionEvent.getY();
            return super.onTouchEvent(motionEvent);
        }

        public void updateEmojiDrawables() {
            EmojiView emojiView = EmojiView.this;
            emojiView.animatedEmojiDrawables = AnimatedEmojiSpan.update(emojiView.emojiCacheType, this, getAnimatedEmojiSpans(), (LongSparseArray<AnimatedEmojiDrawable>) EmojiView.this.animatedEmojiDrawables);
        }

        @Override // androidx.recyclerview.widget.RecyclerView
        public void onScrollStateChanged(int i) {
            super.onScrollStateChanged(i);
            if (i == 0) {
                if (!canScrollVertically(-1) || !canScrollVertically(1)) {
                    EmojiView.this.showBottomTab(true, true);
                }
                if (canScrollVertically(1)) {
                    return;
                }
                EmojiView.this.checkTabsY(1, AndroidUtilities.m1036dp(36.0f));
            }
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
        public void dispatchDraw(Canvas canvas) {
            SparseArray<ArrayList<ImageViewEmoji>> sparseArray;
            super.dispatchDraw(canvas);
            if (this.lastChildCount != getChildCount()) {
                updateEmojiDrawables();
                this.lastChildCount = getChildCount();
            }
            int i = 0;
            while (true) {
                int size = this.viewsGroupedByLines.size();
                sparseArray = this.viewsGroupedByLines;
                if (i >= size) {
                    break;
                }
                ArrayList<ImageViewEmoji> arrayListValueAt = sparseArray.valueAt(i);
                arrayListValueAt.clear();
                this.unusedArrays.add(arrayListValueAt);
                i++;
            }
            sparseArray.clear();
            boolean z = EmojiView.this.animateExpandStartTime > 0 && SystemClock.elapsedRealtime() - EmojiView.this.animateExpandStartTime < animateExpandDuration() && EmojiView.this.animateExpandFromButton != null && EmojiView.this.animateExpandFromPosition >= 0;
            if (EmojiView.this.animatedEmojiDrawables != null && EmojiView.this.emojiGridView != null) {
                for (int i2 = 0; i2 < EmojiView.this.emojiGridView.getChildCount(); i2++) {
                    View childAt = EmojiView.this.emojiGridView.getChildAt(i2);
                    if (childAt instanceof ImageViewEmoji) {
                        int top = childAt.getTop() + ((int) childAt.getTranslationY());
                        ArrayList<ImageViewEmoji> arrayList = this.viewsGroupedByLines.get(top);
                        if (arrayList == null) {
                            if (!this.unusedArrays.isEmpty()) {
                                ArrayList<ArrayList<ImageViewEmoji>> arrayList2 = this.unusedArrays;
                                arrayList = arrayList2.remove(arrayList2.size() - 1);
                            } else {
                                arrayList = new ArrayList<>();
                            }
                            this.viewsGroupedByLines.put(top, arrayList);
                        }
                        arrayList.add((ImageViewEmoji) childAt);
                    }
                    if (z && childAt != null && getChildAdapterPosition(childAt) == EmojiView.this.animateExpandFromPosition - 1) {
                        float interpolation = CubicBezierInterpolator.EASE_OUT.getInterpolation(MathUtils.clamp((SystemClock.elapsedRealtime() - EmojiView.this.animateExpandStartTime) / 140.0f, 0.0f, 1.0f));
                        if (interpolation < 1.0f) {
                            float f = 1.0f - interpolation;
                            canvas.saveLayerAlpha(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom(), (int) (255.0f * f), 31);
                            canvas.translate(childAt.getLeft(), childAt.getTop());
                            float f2 = (f * 0.5f) + 0.5f;
                            canvas.scale(f2, f2, childAt.getWidth() / 2.0f, childAt.getHeight() / 2.0f);
                            EmojiView.this.animateExpandFromButton.draw(canvas);
                            canvas.restore();
                        }
                    }
                }
            }
            this.lineDrawablesTmp.clear();
            this.lineDrawablesTmp.addAll(this.lineDrawables);
            this.lineDrawables.clear();
            long jCurrentTimeMillis = System.currentTimeMillis();
            int i3 = 0;
            while (true) {
                DrawingInBackgroundLine drawingInBackgroundLine = null;
                if (i3 >= this.viewsGroupedByLines.size()) {
                    break;
                }
                ArrayList<ImageViewEmoji> arrayListValueAt2 = this.viewsGroupedByLines.valueAt(i3);
                ImageViewEmoji imageViewEmoji = arrayListValueAt2.get(0);
                int i4 = imageViewEmoji.position;
                int i5 = 0;
                while (true) {
                    if (i5 >= this.lineDrawablesTmp.size()) {
                        break;
                    }
                    if (this.lineDrawablesTmp.get(i5).position == i4) {
                        drawingInBackgroundLine = this.lineDrawablesTmp.get(i5);
                        this.lineDrawablesTmp.remove(i5);
                        break;
                    }
                    i5++;
                }
                if (drawingInBackgroundLine == null) {
                    if (!this.unusedLineDrawables.isEmpty()) {
                        ArrayList<DrawingInBackgroundLine> arrayList3 = this.unusedLineDrawables;
                        drawingInBackgroundLine = arrayList3.remove(arrayList3.size() - 1);
                    } else {
                        drawingInBackgroundLine = new DrawingInBackgroundLine();
                    }
                    drawingInBackgroundLine.position = i4;
                    drawingInBackgroundLine.onAttachToWindow();
                }
                this.lineDrawables.add(drawingInBackgroundLine);
                drawingInBackgroundLine.imageViewEmojis = arrayListValueAt2;
                canvas.save();
                canvas.translate(imageViewEmoji.getLeft(), imageViewEmoji.getY() + imageViewEmoji.getPaddingTop());
                drawingInBackgroundLine.startOffset = imageViewEmoji.getLeft();
                int measuredWidth = getMeasuredWidth() - (imageViewEmoji.getLeft() * 2);
                int measuredHeight = imageViewEmoji.getMeasuredHeight() - imageViewEmoji.getPaddingBottom();
                if (measuredWidth > 0 && measuredHeight > 0) {
                    drawingInBackgroundLine.draw(canvas, jCurrentTimeMillis, measuredWidth, measuredHeight, 1.0f);
                }
                canvas.restore();
                i3++;
            }
            for (int i6 = 0; i6 < this.lineDrawablesTmp.size(); i6++) {
                if (this.unusedLineDrawables.size() < 3) {
                    this.unusedLineDrawables.add(this.lineDrawablesTmp.get(i6));
                    this.lineDrawablesTmp.get(i6).imageViewEmojis = null;
                    this.lineDrawablesTmp.get(i6).reset();
                } else {
                    this.lineDrawablesTmp.get(i6).onDetachFromWindow();
                }
            }
            this.lineDrawablesTmp.clear();
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            updateEmojiDrawables();
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            AnimatedEmojiSpan.release(this, (LongSparseArray<AnimatedEmojiDrawable>) EmojiView.this.animatedEmojiDrawables);
            int i = 0;
            for (int i2 = 0; i2 < this.lineDrawables.size(); i2++) {
                this.lineDrawables.get(i2).onDetachFromWindow();
            }
            while (true) {
                int size = this.unusedLineDrawables.size();
                ArrayList<DrawingInBackgroundLine> arrayList = this.unusedLineDrawables;
                if (i < size) {
                    arrayList.get(i).onDetachFromWindow();
                    i++;
                } else {
                    arrayList.addAll(this.lineDrawables);
                    this.lineDrawables.clear();
                    return;
                }
            }
        }

        public class TouchDownInfo {
            long time;
            View view;

            /* JADX INFO: renamed from: x */
            float f1561x;

            /* JADX INFO: renamed from: y */
            float f1562y;

            public TouchDownInfo() {
            }
        }

        public void clearTouchesFor(View view) {
            if (this.touches != null) {
                int i = 0;
                while (i < this.touches.size()) {
                    TouchDownInfo touchDownInfoValueAt = this.touches.valueAt(i);
                    if (touchDownInfoValueAt.view == view) {
                        this.touches.removeAt(i);
                        i--;
                        View view2 = touchDownInfoValueAt.view;
                        if (view2 != null && (view2.getBackground() instanceof RippleDrawable)) {
                            touchDownInfoValueAt.view.getBackground().setState(new int[0]);
                        }
                        View view3 = touchDownInfoValueAt.view;
                        if (view3 != null) {
                            view3.setPressed(false);
                        }
                    }
                    i++;
                }
            }
        }

        public void clearAllTouches() {
            if (this.touches != null) {
                while (this.touches.size() > 0) {
                    TouchDownInfo touchDownInfoValueAt = this.touches.valueAt(0);
                    this.touches.removeAt(0);
                    if (touchDownInfoValueAt != null) {
                        View view = touchDownInfoValueAt.view;
                        if (view != null && (view.getBackground() instanceof RippleDrawable)) {
                            touchDownInfoValueAt.view.getBackground().setState(new int[0]);
                        }
                        View view2 = touchDownInfoValueAt.view;
                        if (view2 != null) {
                            view2.setPressed(false);
                        }
                    }
                }
            }
        }

        public long animateExpandDuration() {
            return animateExpandAppearDuration() + animateExpandCrossfadeDuration() + 150;
        }

        public long animateExpandAppearDuration() {
            return Math.max(600L, ((long) Math.min(55, EmojiView.this.animateExpandToPosition - EmojiView.this.animateExpandFromPosition)) * 40);
        }

        public long animateExpandCrossfadeDuration() {
            return Math.max(400L, ((long) Math.min(45, EmojiView.this.animateExpandToPosition - EmojiView.this.animateExpandFromPosition)) * 35);
        }

        public class DrawingInBackgroundLine extends DrawingInBackgroundThreadDrawable {
            ArrayList<ImageViewEmoji> imageViewEmojis;
            public int position;
            public int startOffset;
            ArrayList<ImageViewEmoji> drawInBackgroundViews = new ArrayList<>();
            private OvershootInterpolator appearScaleInterpolator = new OvershootInterpolator(3.0f);

            public DrawingInBackgroundLine() {
            }

            @Override // org.telegram.p035ui.Components.DrawingInBackgroundThreadDrawable
            public void draw(Canvas canvas, long j, int i, int i2, float f) {
                ArrayList<ImageViewEmoji> arrayList = this.imageViewEmojis;
                if (arrayList == null) {
                    return;
                }
                boolean z = true;
                boolean z2 = arrayList.size() <= 4 || SharedConfig.getDevicePerformanceClass() == 0 || !LiteMode.isEnabled(LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD);
                if (z2) {
                    z = z2;
                } else {
                    boolean z3 = EmojiView.this.animateExpandStartTime > 0 && SystemClock.elapsedRealtime() - EmojiView.this.animateExpandStartTime < EmojiGridView.this.animateExpandDuration();
                    for (int i3 = 0; i3 < this.imageViewEmojis.size(); i3++) {
                        ImageViewEmoji imageViewEmoji = this.imageViewEmojis.get(i3);
                        if (imageViewEmoji.pressedProgress != 0.0f || imageViewEmoji.backAnimator != null || (imageViewEmoji.position > EmojiView.this.animateExpandFromPosition && imageViewEmoji.position < EmojiView.this.animateExpandToPosition && z3)) {
                            break;
                        }
                    }
                    z = z2;
                }
                if (z) {
                    prepareDraw(System.currentTimeMillis());
                    drawInUiThread(canvas, f);
                    reset();
                    return;
                }
                super.draw(canvas, j, i, i2, f);
            }

            @Override // org.telegram.p035ui.Components.DrawingInBackgroundThreadDrawable
            public void prepareDraw(long j) {
                AnimatedEmojiDrawable animatedEmojiDrawable;
                this.drawInBackgroundViews.clear();
                for (int i = 0; i < this.imageViewEmojis.size(); i++) {
                    ImageViewEmoji imageViewEmoji = this.imageViewEmojis.get(i);
                    if (imageViewEmoji.getSpan() != null && (animatedEmojiDrawable = (AnimatedEmojiDrawable) EmojiView.this.animatedEmojiDrawables.get(imageViewEmoji.span.getDocumentId())) != null && animatedEmojiDrawable.getImageReceiver() != null) {
                        animatedEmojiDrawable.update(j);
                        ImageReceiver.BackgroundThreadDrawHolder[] backgroundThreadDrawHolderArr = imageViewEmoji.backgroundThreadDrawHolder;
                        int i2 = this.threadIndex;
                        ImageReceiver imageReceiver = animatedEmojiDrawable.getImageReceiver();
                        ImageReceiver.BackgroundThreadDrawHolder[] backgroundThreadDrawHolderArr2 = imageViewEmoji.backgroundThreadDrawHolder;
                        int i3 = this.threadIndex;
                        backgroundThreadDrawHolderArr[i2] = imageReceiver.setDrawInBackgroundThread(backgroundThreadDrawHolderArr2[i3], i3);
                        imageViewEmoji.backgroundThreadDrawHolder[this.threadIndex].time = j;
                        imageViewEmoji.backgroundThreadDrawHolder[this.threadIndex].overrideAlpha = 1.0f;
                        animatedEmojiDrawable.setAlpha(255);
                        int height = (int) (imageViewEmoji.getHeight() * 0.03f);
                        Rect rect = AndroidUtilities.rectTmp2;
                        rect.set((imageViewEmoji.getLeft() + imageViewEmoji.getPaddingLeft()) - this.startOffset, height, (imageViewEmoji.getRight() - imageViewEmoji.getPaddingRight()) - this.startOffset, ((imageViewEmoji.getMeasuredHeight() + height) - imageViewEmoji.getPaddingTop()) - imageViewEmoji.getPaddingBottom());
                        imageViewEmoji.backgroundThreadDrawHolder[this.threadIndex].setBounds(rect);
                        imageViewEmoji.drawable = animatedEmojiDrawable;
                        imageViewEmoji.imageReceiver = animatedEmojiDrawable.getImageReceiver();
                        imageViewEmoji.backgroundThreadDrawHolder[this.threadIndex].colorFilter = animatedEmojiDrawable.canOverrideColor() ? EmojiView.this.animatedEmojiTextColorFilter : null;
                        this.drawInBackgroundViews.add(imageViewEmoji);
                    }
                }
            }

            @Override // org.telegram.p035ui.Components.DrawingInBackgroundThreadDrawable
            public void drawInBackground(Canvas canvas) {
                for (int i = 0; i < this.drawInBackgroundViews.size(); i++) {
                    ImageViewEmoji imageViewEmoji = this.drawInBackgroundViews.get(i);
                    AnimatedEmojiDrawable animatedEmojiDrawable = imageViewEmoji.drawable;
                    if (animatedEmojiDrawable != null) {
                        animatedEmojiDrawable.draw(canvas, imageViewEmoji.backgroundThreadDrawHolder[this.threadIndex], false);
                    }
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:75:0x0160  */
            /* JADX WARN: Removed duplicated region for block: B:78:0x017a  */
            /* JADX WARN: Removed duplicated region for block: B:79:0x0191  */
            @Override // org.telegram.p035ui.Components.DrawingInBackgroundThreadDrawable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void drawInUiThread(android.graphics.Canvas r20, float r21) {
                /*
                    Method dump skipped, instruction units count: 413
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.EmojiGridView.DrawingInBackgroundLine.drawInUiThread(android.graphics.Canvas, float):void");
            }

            @Override // org.telegram.p035ui.Components.DrawingInBackgroundThreadDrawable
            public void onFrameReady() {
                super.onFrameReady();
                for (int i = 0; i < this.drawInBackgroundViews.size(); i++) {
                    ImageViewEmoji imageViewEmoji = this.drawInBackgroundViews.get(i);
                    if (imageViewEmoji.backgroundThreadDrawHolder != null) {
                        imageViewEmoji.backgroundThreadDrawHolder[this.threadIndex].release();
                    }
                }
                EmojiView.this.emojiGridView.invalidate();
            }
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            View view;
            View view2;
            boolean z = motionEvent.getActionMasked() == 5 || motionEvent.getActionMasked() == 0;
            boolean z2 = motionEvent.getActionMasked() == 6 || motionEvent.getActionMasked() == 1;
            boolean z3 = motionEvent.getActionMasked() == 3;
            if (z || z2 || z3) {
                int actionIndex = motionEvent.getActionIndex();
                int pointerId = motionEvent.getPointerId(actionIndex);
                if (this.touches == null) {
                    this.touches = new SparseArray<>();
                }
                float x = motionEvent.getX(actionIndex);
                float y = motionEvent.getY(actionIndex);
                View viewFindChildViewUnder = findChildViewUnder(x, y);
                if (!z) {
                    TouchDownInfo touchDownInfo = this.touches.get(pointerId);
                    this.touches.remove(pointerId);
                    if (viewFindChildViewUnder != null && touchDownInfo != null && Math.sqrt(Math.pow(x - touchDownInfo.f1561x, 2.0d) + Math.pow(y - touchDownInfo.f1562y, 2.0d)) < AndroidUtilities.touchSlop * 3.0f && !z3 && (!EmojiView.this.colorPickerView.isShowing() || SystemClock.elapsedRealtime() - touchDownInfo.time < ViewConfiguration.getLongPressTimeout())) {
                        View view3 = touchDownInfo.view;
                        int childAdapterPosition = getChildAdapterPosition(view3);
                        try {
                            if (view3 instanceof ImageViewEmoji) {
                                EmojiView.this.sendEmoji((ImageViewEmoji) view3, null);
                                performHapticFeedback(VibratorUtils.getType(3), 1);
                            } else if (view3 instanceof EmojiPackExpand) {
                                EmojiView.this.emojiAdapter.expand(childAdapterPosition, (EmojiPackExpand) view3);
                                performHapticFeedback(VibratorUtils.getType(3), 1);
                            } else if (view3 != null) {
                                view3.callOnClick();
                            }
                        } catch (Exception unused) {
                        }
                    }
                    if (touchDownInfo != null && (view2 = touchDownInfo.view) != null && (view2.getBackground() instanceof RippleDrawable)) {
                        touchDownInfo.view.getBackground().setState(new int[0]);
                    }
                    if (touchDownInfo != null && (view = touchDownInfo.view) != null) {
                        view.setPressed(false);
                    }
                } else if (viewFindChildViewUnder != null) {
                    TouchDownInfo touchDownInfo2 = new TouchDownInfo();
                    touchDownInfo2.f1561x = x;
                    touchDownInfo2.f1562y = y;
                    touchDownInfo2.time = SystemClock.elapsedRealtime();
                    touchDownInfo2.view = viewFindChildViewUnder;
                    if (viewFindChildViewUnder.getBackground() instanceof RippleDrawable) {
                        viewFindChildViewUnder.getBackground().setState(new int[]{R.attr.state_pressed, R.attr.state_enabled});
                    }
                    touchDownInfo2.view.setPressed(true);
                    this.touches.put(pointerId, touchDownInfo2);
                    stopScroll();
                }
            }
            return super.dispatchTouchEvent(motionEvent) || (!z3 && this.touches.size() > 0);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$28 */
    public class C430628 extends ChooseStickerActionTracker {
        public C430628(int i, long j, long j2) {
            super(i, j, j2);
        }

        @Override // org.telegram.ui.Components.EmojiView.ChooseStickerActionTracker
        public boolean isShown() {
            return EmojiView.this.delegate != null && EmojiView.this.getVisibility() == 0 && EmojiView.this.stickersContainerAttached;
        }
    }

    public void createStickersChooseActionTracker() {
        C430628 c430628 = new ChooseStickerActionTracker(this.currentAccount, this.delegate.getDialogId(), this.delegate.getThreadId()) { // from class: org.telegram.ui.Components.EmojiView.28
            public C430628(int i, long j, long j2) {
                super(i, j, j2);
            }

            @Override // org.telegram.ui.Components.EmojiView.ChooseStickerActionTracker
            public boolean isShown() {
                return EmojiView.this.delegate != null && EmojiView.this.getVisibility() == 0 && EmojiView.this.stickersContainerAttached;
            }
        };
        this.chooseStickerActionTracker = c430628;
        c430628.checkVisibility();
    }

    public void updateEmojiTabsPosition() {
        updateEmojiTabsPosition(this.emojiLayoutManager.findFirstCompletelyVisibleItemPosition());
    }

    public void updateEmojiTabsPosition(int i) {
        if (this.emojiSmoothScrolling) {
            return;
        }
        int i2 = -1;
        if (i != -1) {
            int length = 0;
            int size = getRecentEmoji().size() + (this.needEmojiSearch ? 1 : 0) + (this.emojiAdapter.trendingHeaderRow >= 0 ? 3 : 0);
            if (i >= size) {
                int i3 = 0;
                while (true) {
                    String[][] strArr = EmojiData.dataColored;
                    if (i3 >= strArr.length) {
                        break;
                    }
                    size += strArr[i3].length + 1;
                    if (i < size) {
                        i2 = i3 + 1;
                        break;
                    }
                    i3++;
                }
                if (i2 < 0) {
                    ArrayList<EmojiPack> emojipacks = getEmojipacks();
                    int size2 = this.emojiAdapter.packStartPosition.size() - 1;
                    while (true) {
                        if (size2 < 0) {
                            break;
                        }
                        if (((Integer) this.emojiAdapter.packStartPosition.get(size2)).intValue() <= i) {
                            EmojiPack emojiPack = this.emojipacksProcessed.get(size2);
                            while (length < emojipacks.size()) {
                                long j = emojipacks.get(length).set.f1280id;
                                long j2 = emojiPack.set.f1280id;
                                if (j == j2 && (!emojiPack.featured || (!emojiPack.installed && !this.installedEmojiSets.contains(Long.valueOf(j2))))) {
                                    length = EmojiData.dataColored.length + 1 + length;
                                    break;
                                }
                                length++;
                            }
                        } else {
                            size2--;
                        }
                    }
                } else {
                    length = i2;
                }
            }
            if (length >= 0) {
                this.emojiTabs.select(length);
            }
        }
    }

    public void checkGridVisibility(int i, float f) {
        if (this.stickersContainer == null || this.gifContainer == null) {
            return;
        }
        if (i == 0) {
            this.emojiGridView.setVisibility(0);
            this.gifGridView.setVisibility(f == 0.0f ? 8 : 0);
            this.gifTabs.setVisibility(f == 0.0f ? 8 : 0);
            this.stickersGridView.setVisibility(8);
            FrameLayout frameLayout = this.stickersTabContainer;
            if (frameLayout != null) {
                frameLayout.setVisibility(8);
                return;
            }
            return;
        }
        if (i == 1) {
            this.emojiGridView.setVisibility(8);
            this.gifGridView.setVisibility(0);
            this.gifTabs.setVisibility(0);
            this.stickersGridView.setVisibility(f == 0.0f ? 8 : 0);
            FrameLayout frameLayout2 = this.stickersTabContainer;
            if (frameLayout2 != null) {
                frameLayout2.setVisibility(f == 0.0f ? 8 : 0);
                return;
            }
            return;
        }
        if (i == 2) {
            this.emojiGridView.setVisibility(8);
            this.gifGridView.setVisibility(8);
            this.gifTabs.setVisibility(8);
            this.stickersGridView.setVisibility(0);
            FrameLayout frameLayout3 = this.stickersTabContainer;
            if (frameLayout3 != null) {
                frameLayout3.setVisibility(0);
            }
        }
    }

    public void openPremiumAnimatedEmojiFeature() {
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate != null) {
            emojiViewDelegate.onAnimatedEmojiUnlockClick();
        }
    }

    public class EmojiPackButton extends FrameLayout {
        AnimatedTextView addButtonTextView;
        FrameLayout addButtonView;
        PremiumButtonView premiumButtonView;

        public EmojiPackButton(Context context) {
            super(context);
            AnimatedTextView animatedTextView = new AnimatedTextView(getContext());
            this.addButtonTextView = animatedTextView;
            animatedTextView.setAnimationProperties(0.3f, 0L, 250L, CubicBezierInterpolator.EASE_OUT_QUINT);
            this.addButtonTextView.setTextSize(AndroidUtilities.m1036dp(14.0f));
            this.addButtonTextView.setTypeface(AndroidUtilities.bold());
            this.addButtonTextView.setTextColor(EmojiView.this.getThemedColor(Theme.key_featuredStickers_buttonText));
            this.addButtonTextView.setGravity(17);
            FrameLayout frameLayout = new FrameLayout(getContext());
            this.addButtonView = frameLayout;
            frameLayout.setBackground(Theme.AdaptiveRipple.filledRect(EmojiView.this.getThemedColor(Theme.key_featuredStickers_addButton), 8.0f));
            this.addButtonView.addView(this.addButtonTextView, LayoutHelper.createFrame(-1, -2, 17));
            addView(this.addButtonView, LayoutHelper.createFrame(-1, -1.0f));
            PremiumButtonView premiumButtonView = new PremiumButtonView(getContext(), false, EmojiView.this.resourcesProvider);
            this.premiumButtonView = premiumButtonView;
            premiumButtonView.setIcon(C2797R.raw.unlock_icon);
            addView(this.premiumButtonView, LayoutHelper.createFrame(-1, -1.0f));
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            setPadding(AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(11.0f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(11.0f));
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(44.0f) + getPaddingTop() + getPaddingBottom(), TLObject.FLAG_30));
        }
    }

    public class EmojiPackHeader extends FrameLayout implements NotificationCenter.NotificationCenterDelegate {
        TextView addButtonView;
        FrameLayout buttonsView;
        private int currentButtonState;
        boolean divider;
        private Paint dividerPaint;
        SimpleTextView headerView;
        RLottieImageView lockView;
        TextView markView;
        private EmojiPack pack;
        PremiumButtonView premiumButtonView;
        TextView removeButtonView;
        private AnimatorSet stateAnimator;
        private TLRPC.InputStickerSet toInstall;
        private TLRPC.InputStickerSet toUninstall;

        public EmojiPackHeader(Context context) {
            super(context);
            RLottieImageView rLottieImageView = new RLottieImageView(context);
            this.lockView = rLottieImageView;
            rLottieImageView.setAnimation(C2797R.raw.unlock_icon, 24, 24);
            RLottieImageView rLottieImageView2 = this.lockView;
            int i = Theme.key_chat_emojiPanelStickerSetName;
            rLottieImageView2.setColorFilter(EmojiView.this.getThemedColor(i));
            addView(this.lockView, LayoutHelper.createFrameRelatively(20.0f, 20.0f, 8388611, 10.0f, 15.0f, 0.0f, 0.0f));
            SimpleTextView simpleTextView = new SimpleTextView(context);
            this.headerView = simpleTextView;
            simpleTextView.setTextSize(15);
            this.headerView.setTextColor(EmojiView.this.getThemedColor(i));
            this.headerView.setTypeface(AndroidUtilities.bold());
            this.headerView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$0(view);
                }
            });
            TextView textView = new TextView(context);
            this.markView = textView;
            textView.setTextSize(1, 11.0f);
            this.markView.setTextColor(EmojiView.this.getThemedColor(i));
            this.markView.setTypeface(AndroidUtilities.bold());
            this.markView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1036dp(8.0f), Theme.multAlpha(EmojiView.this.getThemedColor(Theme.key_chat_emojiPanelIcon), 0.12f)));
            this.markView.setPadding(AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(1.5f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(1.5f));
            this.markView.setText(LocaleController.getString(C2797R.string.GroupEmoji));
            this.headerView.setEllipsizeByGradient(true);
            addView(this.headerView, LayoutHelper.createFrameRelatively(-2.0f, -1.0f, 8388611, 15.0f, 15.0f, 0.0f, 0.0f));
            addView(this.markView, LayoutHelper.createFrameRelatively(-2.0f, -2.0f, 8388611, 15.0f, 10.0f, 0.0f, 0.0f));
            FrameLayout frameLayout = new FrameLayout(context);
            this.buttonsView = frameLayout;
            frameLayout.setPadding(AndroidUtilities.m1036dp(11.0f), AndroidUtilities.m1036dp(11.0f), AndroidUtilities.m1036dp(11.0f), 0);
            this.buttonsView.setClipToPadding(false);
            this.buttonsView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$1(view);
                }
            });
            addView(this.buttonsView, LayoutHelper.createFrameRelatively(-2.0f, -1.0f, 8388725));
            TextView textView2 = new TextView(context);
            this.addButtonView = textView2;
            textView2.setTextSize(1, 14.0f);
            this.addButtonView.setTypeface(AndroidUtilities.bold());
            this.addButtonView.setText(LocaleController.getString(C2797R.string.Add));
            this.addButtonView.setTextColor(EmojiView.this.getThemedColor(Theme.key_featuredStickers_buttonText));
            TextView textView3 = this.addButtonView;
            int i2 = Theme.key_featuredStickers_addButton;
            textView3.setBackground(Theme.AdaptiveRipple.createRect(EmojiView.this.getThemedColor(i2), EmojiView.this.getThemedColor(Theme.key_featuredStickers_addButtonPressed), 16.0f));
            this.addButtonView.setPadding(AndroidUtilities.m1036dp(14.0f), 0, AndroidUtilities.m1036dp(14.0f), 0);
            this.addButtonView.setGravity(17);
            this.addButtonView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$2(view);
                }
            });
            this.buttonsView.addView(this.addButtonView, LayoutHelper.createFrameRelatively(-2.0f, 26.0f, 8388661));
            TextView textView4 = new TextView(context);
            this.removeButtonView = textView4;
            textView4.setTextSize(1, 14.0f);
            this.removeButtonView.setTypeface(AndroidUtilities.bold());
            this.removeButtonView.setText(LocaleController.getString(C2797R.string.StickersRemove));
            this.removeButtonView.setTextColor(EmojiView.this.getThemedColor(Theme.key_featuredStickers_removeButtonText));
            this.removeButtonView.setBackground(Theme.AdaptiveRipple.createRect(0, EmojiView.this.getThemedColor(i2) & 452984831, 16.0f));
            this.removeButtonView.setPadding(AndroidUtilities.m1036dp(12.0f), 0, AndroidUtilities.m1036dp(12.0f), 0);
            this.removeButtonView.setGravity(17);
            this.removeButtonView.setTranslationX(AndroidUtilities.m1036dp(4.0f));
            this.removeButtonView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$3(view);
                }
            });
            this.buttonsView.addView(this.removeButtonView, LayoutHelper.createFrameRelatively(-2.0f, 26.0f, 8388661));
            PremiumButtonView premiumButtonView = new PremiumButtonView(context, AndroidUtilities.m1036dp(16.0f), false, EmojiView.this.resourcesProvider);
            this.premiumButtonView = premiumButtonView;
            premiumButtonView.setIcon(C2797R.raw.unlock_icon);
            this.premiumButtonView.setButton(LocaleController.getString(C2797R.string.Unlock), new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$4(view);
                }
            });
            try {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.premiumButtonView.getIconView().getLayoutParams();
                marginLayoutParams.leftMargin = AndroidUtilities.m1036dp(1.0f);
                marginLayoutParams.topMargin = AndroidUtilities.m1036dp(1.0f);
                int iM1036dp = AndroidUtilities.m1036dp(20.0f);
                marginLayoutParams.height = iM1036dp;
                marginLayoutParams.width = iM1036dp;
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.premiumButtonView.getTextView().getLayoutParams();
                marginLayoutParams2.leftMargin = AndroidUtilities.m1036dp(5.0f);
                marginLayoutParams2.topMargin = AndroidUtilities.m1036dp(-0.5f);
                this.premiumButtonView.getChildAt(0).setPadding(AndroidUtilities.m1036dp(8.0f), 0, AndroidUtilities.m1036dp(8.0f), 0);
            } catch (Exception unused) {
            }
            this.buttonsView.addView(this.premiumButtonView, LayoutHelper.createFrameRelatively(-2.0f, 26.0f, 8388661));
            setWillNotDraw(false);
        }

        public /* synthetic */ void lambda$new$0(View view) {
            TLRPC.StickerSet stickerSet;
            EmojiPack emojiPack = this.pack;
            if (emojiPack == null || (stickerSet = emojiPack.set) == null) {
                return;
            }
            EmojiView.this.openEmojiPackAlert(stickerSet);
        }

        public /* synthetic */ void lambda$new$1(View view) {
            TextView textView = this.addButtonView;
            if (textView != null && textView.getVisibility() == 0 && this.addButtonView.isEnabled()) {
                this.addButtonView.performClick();
                return;
            }
            TextView textView2 = this.removeButtonView;
            if (textView2 != null && textView2.getVisibility() == 0 && this.removeButtonView.isEnabled()) {
                this.removeButtonView.performClick();
                return;
            }
            PremiumButtonView premiumButtonView = this.premiumButtonView;
            if (premiumButtonView != null && premiumButtonView.getVisibility() == 0 && this.premiumButtonView.isEnabled()) {
                this.premiumButtonView.performClick();
            }
        }

        public /* synthetic */ void lambda$new$2(View view) {
            TLRPC.StickerSet stickerSet;
            View childAt;
            Integer numValueOf;
            int childAdapterPosition;
            int i;
            EmojiPack emojiPack = this.pack;
            if (emojiPack == null || (stickerSet = emojiPack.set) == null) {
                return;
            }
            emojiPack.installed = true;
            if (!EmojiView.this.installedEmojiSets.contains(Long.valueOf(stickerSet.f1280id))) {
                EmojiView.this.installedEmojiSets.add(Long.valueOf(this.pack.set.f1280id));
            }
            updateState(true);
            int i2 = 0;
            while (true) {
                if (i2 >= EmojiView.this.emojiGridView.getChildCount()) {
                    childAt = null;
                    numValueOf = null;
                    break;
                } else {
                    if ((EmojiView.this.emojiGridView.getChildAt(i2) instanceof EmojiPackExpand) && (childAdapterPosition = EmojiView.this.emojiGridView.getChildAdapterPosition((childAt = EmojiView.this.emojiGridView.getChildAt(i2)))) >= 0 && (i = EmojiView.this.emojiAdapter.positionToExpand.get(childAdapterPosition)) >= 0 && i < EmojiView.this.emojipacksProcessed.size() && EmojiView.this.emojipacksProcessed.get(i) != null && this.pack != null && ((EmojiPack) EmojiView.this.emojipacksProcessed.get(i)).set.f1280id == this.pack.set.f1280id) {
                        numValueOf = Integer.valueOf(childAdapterPosition);
                        break;
                    }
                    i2++;
                }
            }
            if (numValueOf != null) {
                EmojiView.this.emojiAdapter.expand(numValueOf.intValue(), childAt);
            }
            if (this.toInstall != null) {
                return;
            }
            TLRPC.TL_inputStickerSetID tL_inputStickerSetID = new TLRPC.TL_inputStickerSetID();
            TLRPC.StickerSet stickerSet2 = this.pack.set;
            tL_inputStickerSetID.f1270id = stickerSet2.f1280id;
            tL_inputStickerSetID.access_hash = stickerSet2.access_hash;
            TLRPC.TL_messages_stickerSet stickerSet3 = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSet((TLRPC.InputStickerSet) tL_inputStickerSetID, true);
            if (stickerSet3 == null || stickerSet3.set == null) {
                NotificationCenter.getInstance(EmojiView.this.currentAccount).addObserver(this, NotificationCenter.groupStickersDidLoad);
                MediaDataController mediaDataController = MediaDataController.getInstance(EmojiView.this.currentAccount);
                this.toInstall = tL_inputStickerSetID;
                mediaDataController.getStickerSet((TLRPC.InputStickerSet) tL_inputStickerSetID, false);
                return;
            }
            install(stickerSet3);
        }

        public /* synthetic */ void lambda$new$3(View view) {
            TLRPC.StickerSet stickerSet;
            EmojiPack emojiPack = this.pack;
            if (emojiPack == null || (stickerSet = emojiPack.set) == null) {
                return;
            }
            emojiPack.installed = false;
            EmojiView.this.installedEmojiSets.remove(Long.valueOf(stickerSet.f1280id));
            updateState(true);
            if (EmojiView.this.emojiTabs != null) {
                EmojiView.this.emojiTabs.updateEmojiPacks(EmojiView.this.getEmojipacks());
            }
            EmojiView.this.updateEmojiTabsPosition();
            if (this.toUninstall != null) {
                return;
            }
            TLRPC.TL_inputStickerSetID tL_inputStickerSetID = new TLRPC.TL_inputStickerSetID();
            TLRPC.StickerSet stickerSet2 = this.pack.set;
            tL_inputStickerSetID.f1270id = stickerSet2.f1280id;
            tL_inputStickerSetID.access_hash = stickerSet2.access_hash;
            TLRPC.TL_messages_stickerSet stickerSet3 = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSet((TLRPC.InputStickerSet) tL_inputStickerSetID, true);
            if (stickerSet3 == null || stickerSet3.set == null) {
                NotificationCenter.getInstance(EmojiView.this.currentAccount).addObserver(this, NotificationCenter.groupStickersDidLoad);
                MediaDataController mediaDataController = MediaDataController.getInstance(EmojiView.this.currentAccount);
                this.toUninstall = tL_inputStickerSetID;
                mediaDataController.getStickerSet((TLRPC.InputStickerSet) tL_inputStickerSetID, false);
                return;
            }
            uninstall(stickerSet3);
        }

        public /* synthetic */ void lambda$new$4(View view) {
            EmojiView.this.openPremiumAnimatedEmojiFeature();
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            ((ViewGroup.MarginLayoutParams) this.headerView.getLayoutParams()).topMargin = AndroidUtilities.m1036dp(this.currentButtonState == 0 ? 10.0f : 15.0f);
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(this.currentButtonState == 0 ? 32.0f : 42.0f), TLObject.FLAG_30));
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            int width = this.buttonsView.getWidth() + AndroidUtilities.m1036dp(11.0f) + (this.markView.getVisibility() == 0 ? this.markView.getMeasuredWidth() : 0);
            this.headerView.setRightPadding(width);
            if (this.markView.getVisibility() == 0) {
                this.markView.setTranslationX(this.headerView.getTextWidth() + AndroidUtilities.m1036dp(4.0f));
                float maxTextWidth = (this.headerView.getMaxTextWidth() - width) + AndroidUtilities.m1036dp(4.0f);
                if (this.markView.getTranslationX() > maxTextWidth) {
                    this.markView.setTranslationX(maxTextWidth);
                }
            }
        }

        public void setStickerSet(EmojiPack emojiPack, boolean z) {
            if (emojiPack == null) {
                return;
            }
            this.pack = emojiPack;
            this.divider = z;
            this.headerView.setText(emojiPack.set.title);
            this.markView.setVisibility(emojiPack.forGroup ? 0 : 8);
            if (emojiPack.installed && !emojiPack.set.official) {
                this.premiumButtonView.setButton(LocaleController.getString(C2797R.string.Restore), new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda5
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$setStickerSet$5(view);
                    }
                });
            } else {
                this.premiumButtonView.setButton(LocaleController.getString(C2797R.string.Unlock), new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda6
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$setStickerSet$6(view);
                    }
                });
            }
            updateState(false);
        }

        public /* synthetic */ void lambda$setStickerSet$5(View view) {
            EmojiView.this.openPremiumAnimatedEmojiFeature();
        }

        public /* synthetic */ void lambda$setStickerSet$6(View view) {
            EmojiView.this.openPremiumAnimatedEmojiFeature();
        }

        @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
        public void didReceivedNotification(int i, int i2, Object... objArr) {
            TLRPC.TL_messages_stickerSet stickerSetById;
            TLRPC.TL_messages_stickerSet stickerSetById2;
            if (i == NotificationCenter.groupStickersDidLoad) {
                if (this.toInstall != null && (stickerSetById2 = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSetById(this.toInstall.f1270id)) != null && stickerSetById2.set != null) {
                    install(stickerSetById2);
                    this.toInstall = null;
                }
                if (this.toUninstall == null || (stickerSetById = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSetById(this.toUninstall.f1270id)) == null || stickerSetById.set == null) {
                    return;
                }
                uninstall(stickerSetById);
                this.toUninstall = null;
            }
        }

        private BaseFragment getFragment() {
            if (EmojiView.this.fragment != null) {
                return EmojiView.this.fragment;
            }
            return new BaseFragment() { // from class: org.telegram.ui.Components.EmojiView.EmojiPackHeader.1
                public C43251() {
                }

                @Override // org.telegram.p035ui.ActionBar.BaseFragment
                public int getCurrentAccount() {
                    return EmojiView.this.currentAccount;
                }

                @Override // org.telegram.p035ui.ActionBar.BaseFragment
                public View getFragmentView() {
                    return EmojiView.this.bulletinContainer;
                }

                @Override // org.telegram.p035ui.ActionBar.BaseFragment
                public FrameLayout getLayoutContainer() {
                    return EmojiView.this.bulletinContainer;
                }

                @Override // org.telegram.p035ui.ActionBar.BaseFragment
                public Theme.ResourcesProvider getResourceProvider() {
                    return EmojiView.this.resourcesProvider;
                }
            };
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$EmojiPackHeader$1 */
        public class C43251 extends BaseFragment {
            public C43251() {
            }

            @Override // org.telegram.p035ui.ActionBar.BaseFragment
            public int getCurrentAccount() {
                return EmojiView.this.currentAccount;
            }

            @Override // org.telegram.p035ui.ActionBar.BaseFragment
            public View getFragmentView() {
                return EmojiView.this.bulletinContainer;
            }

            @Override // org.telegram.p035ui.ActionBar.BaseFragment
            public FrameLayout getLayoutContainer() {
                return EmojiView.this.bulletinContainer;
            }

            @Override // org.telegram.p035ui.ActionBar.BaseFragment
            public Theme.ResourcesProvider getResourceProvider() {
                return EmojiView.this.resourcesProvider;
            }
        }

        private void install(TLRPC.TL_messages_stickerSet tL_messages_stickerSet) {
            EmojiPacksAlert.installSet(getFragment(), tL_messages_stickerSet, true, null, new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$install$7();
                }
            });
        }

        public /* synthetic */ void lambda$install$7() {
            this.pack.installed = true;
            updateState(true);
        }

        private void uninstall(final TLRPC.TL_messages_stickerSet tL_messages_stickerSet) {
            EmojiPacksAlert.uninstallSet(getFragment(), tL_messages_stickerSet, true, new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$uninstall$8(tL_messages_stickerSet);
                }
            }, false);
        }

        public /* synthetic */ void lambda$uninstall$8(TLRPC.TL_messages_stickerSet tL_messages_stickerSet) {
            this.pack.installed = true;
            if (!EmojiView.this.installedEmojiSets.contains(Long.valueOf(tL_messages_stickerSet.set.f1280id))) {
                EmojiView.this.installedEmojiSets.add(Long.valueOf(tL_messages_stickerSet.set.f1280id));
            }
            updateState(true);
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            NotificationCenter.getInstance(EmojiView.this.currentAccount).removeObserver(this, NotificationCenter.groupStickersDidLoad);
        }

        @Override // android.view.View
        public void onDraw(Canvas canvas) {
            Canvas canvas2;
            if (this.divider) {
                if (this.dividerPaint == null) {
                    Paint paint = new Paint(1);
                    this.dividerPaint = paint;
                    paint.setStrokeWidth(1.0f);
                    this.dividerPaint.setColor(EmojiView.this.getThemedColor(Theme.key_divider));
                }
                canvas2 = canvas;
                canvas2.drawRect(0.0f, 0.0f, getMeasuredWidth(), 1.0f, this.dividerPaint);
            } else {
                canvas2 = canvas;
            }
            super.onDraw(canvas2);
        }

        public void updateState(boolean z) {
            EmojiPack emojiPack = this.pack;
            if (emojiPack == null) {
                return;
            }
            int i = 1;
            boolean z2 = emojiPack.installed || EmojiView.this.installedEmojiSets.contains(Long.valueOf(emojiPack.set.f1280id));
            if (this.pack.free || UserConfig.getInstance(EmojiView.this.currentAccount).isPremium() || EmojiView.this.allowEmojisForNonPremium) {
                i = this.pack.featured ? z2 ? 3 : 2 : 0;
            }
            updateState(i, z);
        }

        public void updateState(int i, boolean z) {
            if ((i == 0) != (this.currentButtonState == 0)) {
                requestLayout();
            }
            this.currentButtonState = i;
            AnimatorSet animatorSet = this.stateAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.stateAnimator = null;
            }
            this.premiumButtonView.setEnabled(i == 1);
            this.addButtonView.setEnabled(i == 2);
            this.removeButtonView.setEnabled(i == 3);
            if (z) {
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.stateAnimator = animatorSet2;
                RLottieImageView rLottieImageView = this.lockView;
                float[] fArr = {i == 1 ? 0.0f : -AndroidUtilities.m1036dp(16.0f)};
                Property property = FrameLayout.TRANSLATION_X;
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(rLottieImageView, (Property<RLottieImageView, Float>) property, fArr);
                RLottieImageView rLottieImageView2 = this.lockView;
                float[] fArr2 = {i == 1 ? 1.0f : 0.0f};
                Property property2 = FrameLayout.ALPHA;
                ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(rLottieImageView2, (Property<RLottieImageView, Float>) property2, fArr2);
                ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this.headerView, (Property<SimpleTextView, Float>) property, i == 1 ? AndroidUtilities.m1036dp(16.0f) : 0.0f);
                ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(this.premiumButtonView, (Property<PremiumButtonView, Float>) property2, i == 1 ? 1.0f : 0.0f);
                PremiumButtonView premiumButtonView = this.premiumButtonView;
                float[] fArr3 = {i == 1 ? 1.0f : 0.6f};
                Property property3 = FrameLayout.SCALE_X;
                ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(premiumButtonView, (Property<PremiumButtonView, Float>) property3, fArr3);
                PremiumButtonView premiumButtonView2 = this.premiumButtonView;
                float f = i == 1 ? 1.0f : 0.6f;
                Property property4 = FrameLayout.SCALE_Y;
                animatorSet2.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat3, objectAnimatorOfFloat4, objectAnimatorOfFloat5, ObjectAnimator.ofFloat(premiumButtonView2, (Property<PremiumButtonView, Float>) property4, f), ObjectAnimator.ofFloat(this.addButtonView, (Property<TextView, Float>) property2, i == 2 ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.addButtonView, (Property<TextView, Float>) property3, i == 2 ? 1.0f : 0.6f), ObjectAnimator.ofFloat(this.addButtonView, (Property<TextView, Float>) property4, i == 2 ? 1.0f : 0.6f), ObjectAnimator.ofFloat(this.removeButtonView, (Property<TextView, Float>) property2, i == 3 ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.removeButtonView, (Property<TextView, Float>) property3, i == 3 ? 1.0f : 0.6f), ObjectAnimator.ofFloat(this.removeButtonView, (Property<TextView, Float>) property4, i == 3 ? 1.0f : 0.6f));
                this.stateAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.EmojiPackHeader.2
                    final /* synthetic */ int val$state;

                    public C43262(int i2) {
                        i = i2;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        EmojiPackHeader.this.premiumButtonView.setVisibility(0);
                        EmojiPackHeader.this.addButtonView.setVisibility(0);
                        EmojiPackHeader.this.removeButtonView.setVisibility(0);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        EmojiPackHeader.this.premiumButtonView.setVisibility(i == 1 ? 0 : 8);
                        EmojiPackHeader.this.addButtonView.setVisibility(i == 2 ? 0 : 8);
                        EmojiPackHeader.this.removeButtonView.setVisibility(i == 3 ? 0 : 8);
                    }
                });
                this.stateAnimator.setDuration(250L);
                this.stateAnimator.setInterpolator(new OvershootInterpolator(1.02f));
                this.stateAnimator.start();
                return;
            }
            this.lockView.setAlpha(i2 == 1 ? 1.0f : 0.0f);
            this.lockView.setTranslationX(i2 == 1 ? 0.0f : -AndroidUtilities.m1036dp(16.0f));
            this.headerView.setTranslationX(i2 == 1 ? AndroidUtilities.m1036dp(16.0f) : 0.0f);
            this.premiumButtonView.setAlpha(i2 == 1 ? 1.0f : 0.0f);
            this.premiumButtonView.setScaleX(i2 == 1 ? 1.0f : 0.6f);
            this.premiumButtonView.setScaleY(i2 == 1 ? 1.0f : 0.6f);
            this.premiumButtonView.setVisibility(i2 == 1 ? 0 : 8);
            this.addButtonView.setAlpha(i2 == 2 ? 1.0f : 0.0f);
            this.addButtonView.setScaleX(i2 == 2 ? 1.0f : 0.6f);
            this.addButtonView.setScaleY(i2 == 2 ? 1.0f : 0.6f);
            this.addButtonView.setVisibility(i2 == 2 ? 0 : 8);
            this.removeButtonView.setAlpha(i2 == 3 ? 1.0f : 0.0f);
            this.removeButtonView.setScaleX(i2 == 3 ? 1.0f : 0.6f);
            this.removeButtonView.setScaleY(i2 == 3 ? 1.0f : 0.6f);
            this.removeButtonView.setVisibility(i2 == 3 ? 0 : 8);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$EmojiPackHeader$2 */
        public class C43262 extends AnimatorListenerAdapter {
            final /* synthetic */ int val$state;

            public C43262(int i2) {
                i = i2;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                EmojiPackHeader.this.premiumButtonView.setVisibility(0);
                EmojiPackHeader.this.addButtonView.setVisibility(0);
                EmojiPackHeader.this.removeButtonView.setVisibility(0);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                EmojiPackHeader.this.premiumButtonView.setVisibility(i == 1 ? 0 : 8);
                EmojiPackHeader.this.addButtonView.setVisibility(i == 2 ? 0 : 8);
                EmojiPackHeader.this.removeButtonView.setVisibility(i == 3 ? 0 : 8);
            }
        }
    }

    public void openEmojiPackAlert(final TLRPC.InputStickerSet inputStickerSet, final TLRPC.Document document) {
        TLRPC.StickerSet stickerSet;
        if (inputStickerSet == null || this.emojiPackAlertOpened) {
            return;
        }
        TLRPC.TL_messages_stickerSet stickerSet2 = MediaDataController.getInstance(this.currentAccount).getStickerSet(inputStickerSet, true);
        if (stickerSet2 != null && (stickerSet = stickerSet2.set) != null && !stickerSet.emojis) {
            Context context = getContext();
            BaseFragment baseFragment = this.fragment;
            StickersAlert stickersAlert = new StickersAlert(context, baseFragment, inputStickerSet, null, baseFragment instanceof ChatActivity ? ((ChatActivity) baseFragment).getChatActivityEnterView() : null, this.resourcesProvider, false);
            BaseFragment baseFragment2 = this.fragment;
            if (baseFragment2 != null) {
                baseFragment2.showDialog(stickersAlert);
                return;
            } else {
                stickersAlert.show();
                return;
            }
        }
        this.emojiPackAlertOpened = true;
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openEmojiPackAlert$24(inputStickerSet, document);
            }
        }, 16L);
    }

    public /* synthetic */ void lambda$openEmojiPackAlert$24(TLRPC.InputStickerSet inputStickerSet, TLRPC.Document document) {
        if (getContext() == null) {
            this.emojiPackAlertOpened = false;
            return;
        }
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(inputStickerSet);
        DialogC430729 dialogC430729 = new EmojiPacksAlert(this.fragment, getContext(), this.resourcesProvider, arrayList) { // from class: org.telegram.ui.Components.EmojiView.29
            final /* synthetic */ TLRPC.InputStickerSet val$set;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public DialogC430729(BaseFragment baseFragment, Context context, Theme.ResourcesProvider resourcesProvider, ArrayList arrayList2, TLRPC.InputStickerSet inputStickerSet2) {
                super(baseFragment, context, resourcesProvider, arrayList2);
                inputStickerSet = inputStickerSet2;
            }

            @Override // org.telegram.p035ui.Components.EmojiPacksAlert, org.telegram.p035ui.ActionBar.BottomSheet, android.app.Dialog, android.content.DialogInterface, org.telegram.ui.ActionBar.BaseFragment.AttachedSheet
            /* JADX INFO: renamed from: dismiss */
            public void lambda$new$0() {
                EmojiView.this.emojiPackAlertOpened = false;
                super.lambda$new$0();
            }

            @Override // org.telegram.p035ui.Components.EmojiPacksAlert
            public void onButtonClicked(boolean z) {
                TLRPC.InputStickerSet inputStickerSet2 = inputStickerSet;
                if (inputStickerSet2 instanceof TLRPC.TL_inputStickerSetID) {
                    long j = ((TLRPC.TL_inputStickerSetID) inputStickerSet2).f1270id;
                    EmojiView emojiView = EmojiView.this;
                    if (z) {
                        if (!emojiView.installedEmojiSets.contains(Long.valueOf(j))) {
                            EmojiView.this.installedEmojiSets.add(Long.valueOf(j));
                        }
                    } else {
                        emojiView.installedEmojiSets.remove(Long.valueOf(j));
                    }
                    EmojiView.this.updateEmojiHeaders();
                }
            }
        };
        if (document != null) {
            dialogC430729.setPreviewEmoji(document);
        }
        BaseFragment baseFragment = this.fragment;
        if (baseFragment != null) {
            baseFragment.showDialog(dialogC430729);
        } else {
            dialogC430729.show();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$29 */
    public class DialogC430729 extends EmojiPacksAlert {
        final /* synthetic */ TLRPC.InputStickerSet val$set;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DialogC430729(BaseFragment baseFragment, Context context, Theme.ResourcesProvider resourcesProvider, ArrayList arrayList2, TLRPC.InputStickerSet inputStickerSet2) {
            super(baseFragment, context, resourcesProvider, arrayList2);
            inputStickerSet = inputStickerSet2;
        }

        @Override // org.telegram.p035ui.Components.EmojiPacksAlert, org.telegram.p035ui.ActionBar.BottomSheet, android.app.Dialog, android.content.DialogInterface, org.telegram.ui.ActionBar.BaseFragment.AttachedSheet
        /* JADX INFO: renamed from: dismiss */
        public void lambda$new$0() {
            EmojiView.this.emojiPackAlertOpened = false;
            super.lambda$new$0();
        }

        @Override // org.telegram.p035ui.Components.EmojiPacksAlert
        public void onButtonClicked(boolean z) {
            TLRPC.InputStickerSet inputStickerSet2 = inputStickerSet;
            if (inputStickerSet2 instanceof TLRPC.TL_inputStickerSetID) {
                long j = ((TLRPC.TL_inputStickerSetID) inputStickerSet2).f1270id;
                EmojiView emojiView = EmojiView.this;
                if (z) {
                    if (!emojiView.installedEmojiSets.contains(Long.valueOf(j))) {
                        EmojiView.this.installedEmojiSets.add(Long.valueOf(j));
                    }
                } else {
                    emojiView.installedEmojiSets.remove(Long.valueOf(j));
                }
                EmojiView.this.updateEmojiHeaders();
            }
        }
    }

    public TLRPC.Document resolveEmojiDocument(long j, TLRPC.Document document, EmojiPack emojiPack) {
        if (document != null) {
            return document;
        }
        if (emojiPack == null) {
            emojiPack = findEmojiPackByDocumentId(j);
        }
        if (emojiPack != null && emojiPack.documents != null) {
            for (int i = 0; i < emojiPack.documents.size(); i++) {
                TLRPC.Document document2 = emojiPack.documents.get(i);
                if (document2 != null && document2.f1253id == j) {
                    return document2;
                }
            }
        }
        return AnimatedEmojiDrawable.findDocument(this.currentAccount, j);
    }

    public EmojiPack findEmojiPackByDocumentId(long j) {
        if (j == 0) {
            return null;
        }
        for (int i = 0; i < this.emojipacksProcessed.size(); i++) {
            EmojiPack emojiPack = this.emojipacksProcessed.get(i);
            if (emojiPack != null && emojiPack.documents != null) {
                for (int i2 = 0; i2 < emojiPack.documents.size(); i2++) {
                    TLRPC.Document document = emojiPack.documents.get(i2);
                    if (document != null && document.f1253id == j) {
                        return emojiPack;
                    }
                }
            }
        }
        return null;
    }

    public void openEmojiPackAlert(TLRPC.StickerSet stickerSet) {
        if (stickerSet == null) {
            return;
        }
        TLRPC.TL_inputStickerSetID tL_inputStickerSetID = new TLRPC.TL_inputStickerSetID();
        tL_inputStickerSetID.f1270id = stickerSet.f1280id;
        tL_inputStickerSetID.access_hash = stickerSet.access_hash;
        openEmojiPackAlert(tL_inputStickerSetID, null);
    }

    public class EmojiGridSpacing extends RecyclerView.ItemDecoration {
        public EmojiGridSpacing() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            if (view instanceof StickerSetNameCell) {
                rect.left = AndroidUtilities.m1036dp(5.0f);
                rect.right = AndroidUtilities.m1036dp(5.0f);
                if (recyclerView.getChildAdapterPosition(view) + 1 <= EmojiView.this.emojiAdapter.plainEmojisCount || UserConfig.getInstance(EmojiView.this.currentAccount).isPremium() || EmojiView.this.allowEmojisForNonPremium) {
                    return;
                }
                rect.top = AndroidUtilities.m1036dp(10.0f);
                return;
            }
            if ((view instanceof RecyclerListView) || (view instanceof EmojiPackHeader)) {
                rect.left = -EmojiView.this.emojiGridView.getPaddingLeft();
                rect.right = -EmojiView.this.emojiGridView.getPaddingRight();
                if (view instanceof EmojiPackHeader) {
                    rect.top = AndroidUtilities.m1036dp(8.0f);
                    return;
                }
                return;
            }
            if (view instanceof BackupImageView) {
                rect.bottom = AndroidUtilities.m1036dp(12.0f);
            }
        }
    }

    public static String addColorToCode(String str, String str2) {
        boolean z;
        String strSubstring;
        if (CompoundEmoji.isHandshake(str) != null) {
            return CompoundEmoji.applyColor(str, str2);
        }
        if (Emoji.endsWithRightArrow(str)) {
            str = str.substring(0, str.length() - 2);
            z = true;
        } else {
            z = false;
        }
        int length = str.length();
        if (length > 2 && str.charAt(str.length() - 2) == 8205) {
            strSubstring = str.substring(str.length() - 2);
            str = str.substring(0, str.length() - 2);
        } else if (length <= 3 || str.charAt(str.length() - 3) != 8205) {
            strSubstring = null;
        } else {
            strSubstring = str.substring(str.length() - 3);
            str = str.substring(0, str.length() - 3);
        }
        String strConcat = str + str2;
        if (strSubstring != null) {
            strConcat = strConcat.concat(strSubstring);
        }
        return z ? strConcat.concat("\u200d➡") : strConcat;
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$30 */
    public class C430930 extends TrendingStickersLayout.Delegate {
        @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
        public boolean canSendSticker() {
            return true;
        }

        public C430930() {
        }

        @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
        public void onStickerSetAdd(TLRPC.StickerSetCovered stickerSetCovered, boolean z) {
            EmojiView.this.delegate.onStickerSetAdd(stickerSetCovered);
            if (z) {
                EmojiView.this.updateStickerTabs(true);
            }
        }

        @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
        public void onStickerSetRemove(TLRPC.StickerSetCovered stickerSetCovered) {
            EmojiView.this.delegate.onStickerSetRemove(stickerSetCovered);
        }

        @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
        public boolean onListViewInterceptTouchEvent(RecyclerListView recyclerListView, MotionEvent motionEvent) {
            return ContentPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, recyclerListView, EmojiView.this.getMeasuredHeight(), EmojiView.this.contentPreviewViewerDelegate, EmojiView.this.resourcesProvider);
        }

        @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
        public boolean onListViewTouchEvent(RecyclerListView recyclerListView, RecyclerListView.OnItemClickListener onItemClickListener, MotionEvent motionEvent) {
            return ContentPreviewViewer.getInstance().onTouch(motionEvent, recyclerListView, EmojiView.this.getMeasuredHeight(), onItemClickListener, EmojiView.this.contentPreviewViewerDelegate, EmojiView.this.resourcesProvider);
        }

        @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
        public String[] getLastSearchKeyboardLanguage() {
            return EmojiView.this.lastSearchKeyboardLanguage;
        }

        @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
        public void setLastSearchKeyboardLanguage(String[] strArr) {
            EmojiView.this.lastSearchKeyboardLanguage = strArr;
        }

        @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
        public void onStickerSelected(TLRPC.Document document, Object obj, boolean z, boolean z2, int i) {
            EmojiView.this.delegate.onStickerSelected(null, document, null, obj, null, z2, i, 0);
        }

        @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
        public boolean canSchedule() {
            return EmojiView.this.delegate.canSchedule();
        }

        @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
        public boolean isInScheduleMode() {
            return EmojiView.this.delegate.isInScheduleMode();
        }
    }

    public void openTrendingStickers(TLRPC.StickerSetCovered stickerSetCovered) {
        this.delegate.showTrendingStickersAlert(new TrendingStickersLayout(getContext(), new TrendingStickersLayout.Delegate() { // from class: org.telegram.ui.Components.EmojiView.30
            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public boolean canSendSticker() {
                return true;
            }

            public C430930() {
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public void onStickerSetAdd(TLRPC.StickerSetCovered stickerSetCovered2, boolean z) {
                EmojiView.this.delegate.onStickerSetAdd(stickerSetCovered2);
                if (z) {
                    EmojiView.this.updateStickerTabs(true);
                }
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public void onStickerSetRemove(TLRPC.StickerSetCovered stickerSetCovered2) {
                EmojiView.this.delegate.onStickerSetRemove(stickerSetCovered2);
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public boolean onListViewInterceptTouchEvent(RecyclerListView recyclerListView, MotionEvent motionEvent) {
                return ContentPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, recyclerListView, EmojiView.this.getMeasuredHeight(), EmojiView.this.contentPreviewViewerDelegate, EmojiView.this.resourcesProvider);
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public boolean onListViewTouchEvent(RecyclerListView recyclerListView, RecyclerListView.OnItemClickListener onItemClickListener, MotionEvent motionEvent) {
                return ContentPreviewViewer.getInstance().onTouch(motionEvent, recyclerListView, EmojiView.this.getMeasuredHeight(), onItemClickListener, EmojiView.this.contentPreviewViewerDelegate, EmojiView.this.resourcesProvider);
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public String[] getLastSearchKeyboardLanguage() {
                return EmojiView.this.lastSearchKeyboardLanguage;
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public void setLastSearchKeyboardLanguage(String[] strArr) {
                EmojiView.this.lastSearchKeyboardLanguage = strArr;
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public void onStickerSelected(TLRPC.Document document, Object obj, boolean z, boolean z2, int i) {
                EmojiView.this.delegate.onStickerSelected(null, document, null, obj, null, z2, i, 0);
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public boolean canSchedule() {
                return EmojiView.this.delegate.canSchedule();
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public boolean isInScheduleMode() {
                return EmojiView.this.delegate.isInScheduleMode();
            }
        }, this.primaryInstallingStickerSets, this.installingStickerSets, this.removingStickerSets, stickerSetCovered, this.resourcesProvider));
    }

    @Override // android.view.View
    public void setTranslationY(float f) {
        super.setTranslationY(f);
        updateStickerTabsPosition();
        updateBottomTabContainerPosition();
    }

    private void updateBottomTabContainerPosition() {
        int measuredHeight;
        int iM1036dp;
        BaseFragment baseFragment;
        View view = (View) getParent();
        if (view != null) {
            float y = getY();
            if (getLayoutParams().height > 0) {
                measuredHeight = getLayoutParams().height;
            } else {
                measuredHeight = getMeasuredHeight();
            }
            float f = y + measuredHeight;
            if ((!AndroidUtilities.isInMultiwindow && ((baseFragment = this.fragment) == null || !baseFragment.isInBubbleMode())) || this.isNewHeightControl) {
                iM1036dp = view.getHeight();
            } else {
                iM1036dp = AndroidUtilities.m1036dp(1.0f);
            }
            float measuredHeight2 = f - iM1036dp;
            if (this.visibleInAppKeyboardHeight >= 0.0f) {
                measuredHeight2 += getMeasuredHeight() - this.visibleInAppKeyboardHeight;
            } else if (this.bottomTabContainer.getTop() - measuredHeight2 < 0.0f || !this.fixBottomTabContainerTranslation) {
                measuredHeight2 = 0.0f;
            }
            float fLerp = (-measuredHeight2) + AndroidUtilities.lerp(AndroidUtilities.m1036dp(this.needEmojiSearch ? 45.0f : 50.0f), -this.bottomInset, this.bottomTabVisibility.getFloatValue());
            this.bottomTabContainer.setTranslationY(fLerp);
            if (this.needEmojiSearch) {
                this.bulletinContainer.setTranslationY(fLerp);
            }
        }
    }

    public void updateStickerTabsPosition() {
        ScrollSlidingTabStrip scrollSlidingTabStrip = this.stickersTab;
        if (scrollSlidingTabStrip != null && this.stickersTabContainer == null && this.delegate != null) {
            scrollSlidingTabStrip.setTranslationY((-AndroidUtilities.m1036dp(50.0f)) * this.delegate.getProgressToSearchOpened());
        }
        if (this.stickersTabContainer == null) {
            return;
        }
        boolean z = getVisibility() == 0 && this.stickersContainerAttached && this.delegate.getProgressToSearchOpened() != 1.0f;
        this.stickersTabContainer.setVisibility(z ? 0 : 8);
        if (z) {
            this.rect.setEmpty();
            this.pager.getChildVisibleRect(this.stickersContainer, this.rect, null);
            float fM1036dp = AndroidUtilities.m1036dp(50.0f) * this.delegate.getProgressToSearchOpened();
            int i = this.rect.left;
            if (i != 0 || fM1036dp != 0.0f) {
                this.expandStickersByDragg = false;
            }
            this.stickersTabContainer.setTranslationX(i);
            float top = (((getTop() + getTranslationY()) - this.stickersTabContainer.getTop()) - this.stickersTab.getExpandedOffset()) - fM1036dp;
            if (this.stickersTabContainer.getTranslationY() != top) {
                this.stickersTabContainer.setTranslationY(top);
                this.stickersTabContainer.invalidate();
            }
        }
        if (this.expandStickersByDragg && z && this.showing) {
            this.stickersTab.expandStickers(this.lastStickersX, true);
        } else {
            this.expandStickersByDragg = false;
            this.stickersTab.expandStickers(this.lastStickersX, false);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= 31 && this.blurredBackgroundSourceRenderNode != null && this.scrollableViewNoiseSuppressor != null) {
            invalidateBlurCaptures();
            RecordingCanvas recordingCanvasBeginRecording = this.blurredBackgroundSourceRenderNode.beginRecording(getMeasuredWidth(), getMeasuredHeight());
            recordingCanvasBeginRecording.drawColor(getThemedColor(Theme.key_windowBackgroundWhite));
            if (SharedConfig.chatBlurEnabled()) {
                this.scrollableViewNoiseSuppressor.draw(recordingCanvasBeginRecording, -2);
            }
            this.blurredBackgroundSourceRenderNode.endRecording();
        }
        updateBottomTabContainerPosition();
        super.dispatchDraw(canvas);
    }

    public void invalidateBlurCaptures() {
        if (Build.VERSION.SDK_INT < 31 || this.scrollableViewNoiseSuppressor == null) {
            return;
        }
        ViewPositionWatcher.computeRectInParent(this.typeTabs, this, this.blurredRectF);
        this.blurredRectF.inset(LiteMode.isEnabled(262144) ? 0.0f : -AndroidUtilities.m1036dp(48.0f), LiteMode.isEnabled(262144) ? 0.0f : -AndroidUtilities.m1036dp(48.0f));
        this.blurredRectF.right = getMeasuredWidth();
        RectF rectF = this.blurredRectF;
        rectF.bottom = Math.min(rectF.bottom, getMeasuredHeight());
        this.scrollableViewNoiseSuppressor.setupRenderNodes(this.blurredRectList, 1);
        this.scrollableViewNoiseSuppressor.invalidateResultRenderNodes(this.blurCaptureMethod, getWidth(), getHeight());
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View view, long j) {
        if (view == this.pager) {
            canvas.save();
            if (this.bottomTabContainer.getVisibility() != 8 && !this.shouldDrawBackground && this.shouldLightenBackground) {
                canvas.drawColor(ColorUtils.setAlphaComponent(-1, 25));
            }
            boolean zDrawChild = super.drawChild(canvas, view, j);
            float navigationBarThirdButtonsFactor = AndroidUtilities.getNavigationBarThirdButtonsFactor(this.bottomInset);
            if (navigationBarThirdButtonsFactor > 0.0f) {
                int iMultAlpha = Theme.multAlpha(getThemedColor(Theme.key_chat_emojiPanelBackground), navigationBarThirdButtonsFactor);
                if (this.lastFadeColor != iMultAlpha) {
                    this.fadeDrawable.setColors(new int[]{iMultAlpha, Theme.multAlpha(iMultAlpha, 0.66f), ColorUtils.setAlphaComponent(iMultAlpha, 0)});
                    this.lastFadeColor = iMultAlpha;
                }
                this.fadeDrawable.setBounds(0, getMeasuredHeight() - this.bottomInset, getMeasuredWidth(), getMeasuredHeight());
                this.fadeDrawable.draw(canvas);
            }
            canvas.restore();
            return zDrawChild;
        }
        return super.drawChild(canvas, view, j);
    }

    public void startStopVisibleGifs(boolean z) {
        RecyclerListView recyclerListView = this.gifGridView;
        if (recyclerListView == null) {
            return;
        }
        int childCount = recyclerListView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.gifGridView.getChildAt(i);
            if (childAt instanceof ContextLinkCell) {
                ImageReceiver photoImage = ((ContextLinkCell) childAt).getPhotoImage();
                if (z) {
                    photoImage.setAllowStartAnimation(true);
                    photoImage.startAnimation();
                } else {
                    photoImage.setAllowStartAnimation(false);
                    photoImage.stopAnimation();
                }
            }
        }
    }

    public ArrayList<String> getRecentEmoji() {
        if (this.allowAnimatedEmoji) {
            return Emoji.recentEmoji;
        }
        if (this.lastRecentArray == null) {
            this.lastRecentArray = new ArrayList<>();
        }
        if (Emoji.recentEmoji.size() != this.lastRecentCount) {
            this.lastRecentArray.clear();
            int i = 0;
            while (true) {
                ArrayList<String> arrayList = Emoji.recentEmoji;
                if (i >= arrayList.size()) {
                    break;
                }
                if (!arrayList.get(i).startsWith("animated_")) {
                    this.lastRecentArray.add(arrayList.get(i));
                }
                i++;
            }
            this.lastRecentCount = this.lastRecentArray.size();
        }
        return this.lastRecentArray;
    }

    public void addEmojiToRecent(String str) {
        if (str == null) {
            return;
        }
        if (!str.startsWith("animated_") && !Emoji.isValidEmoji(str)) {
            return;
        }
        Emoji.addRecentEmoji(str);
        if (getVisibility() != 0 || this.pager.getCurrentItem() != 0) {
            Emoji.sortEmoji();
            this.emojiAdapter.notifyDataSetChanged();
        }
        Emoji.saveRecentEmoji();
        if (this.allowAnimatedEmoji) {
            return;
        }
        ArrayList<String> arrayList = this.lastRecentArray;
        if (arrayList == null) {
            this.lastRecentArray = new ArrayList<>();
        } else {
            arrayList.clear();
        }
        int i = 0;
        while (true) {
            ArrayList<String> arrayList2 = Emoji.recentEmoji;
            if (i < arrayList2.size()) {
                if (!arrayList2.get(i).startsWith("animated_")) {
                    this.lastRecentArray.add(arrayList2.get(i));
                }
                i++;
            } else {
                this.lastRecentCount = this.lastRecentArray.size();
                return;
            }
        }
    }

    public void showSearchField(boolean z) {
        for (int i = 0; i < 3; i++) {
            GridLayoutManager layoutManagerForType = getLayoutManagerForType(i);
            int iFindFirstVisibleItemPosition = layoutManagerForType.findFirstVisibleItemPosition();
            if (z) {
                if (iFindFirstVisibleItemPosition == 1 || iFindFirstVisibleItemPosition == 2) {
                    layoutManagerForType.scrollToPosition(0);
                    resetTabsY(i);
                }
            } else if (iFindFirstVisibleItemPosition == 0) {
                layoutManagerForType.scrollToPositionWithOffset(0, 0);
            }
        }
    }

    public void hideSearchKeyboard() {
        SearchField searchField = this.stickersSearchField;
        if (searchField != null) {
            searchField.hideKeyboard();
        }
        SearchField searchField2 = this.gifSearchField;
        if (searchField2 != null) {
            searchField2.hideKeyboard();
        }
        SearchField searchField3 = this.emojiSearchField;
        if (searchField3 != null) {
            searchField3.hideKeyboard();
        }
    }

    public void openSearch(SearchField searchField) {
        SearchField searchField2;
        RecyclerListView recyclerListView;
        View view;
        LinearLayoutManager linearLayoutManager;
        EmojiViewDelegate emojiViewDelegate;
        AnimatorSet animatorSet = this.searchAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.searchAnimation = null;
        }
        this.firstStickersAttach = false;
        this.firstGifAttach = false;
        this.firstEmojiAttach = false;
        int i = 0;
        while (true) {
            if (i >= 3) {
                showBottomTab(false, true);
                return;
            }
            if (i == 0) {
                searchField2 = this.emojiSearchField;
                recyclerListView = this.emojiGridView;
                view = this.emojiTabs;
                linearLayoutManager = this.emojiLayoutManager;
            } else if (i == 1) {
                searchField2 = this.gifSearchField;
                recyclerListView = this.gifGridView;
                view = this.gifTabs;
                linearLayoutManager = this.gifLayoutManager;
            } else {
                searchField2 = this.stickersSearchField;
                recyclerListView = this.stickersGridView;
                view = this.stickersTab;
                linearLayoutManager = this.stickersLayoutManager;
            }
            if (searchField2 != null) {
                if (searchField == searchField2 && (emojiViewDelegate = this.delegate) != null && emojiViewDelegate.isExpanded()) {
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    this.searchAnimation = animatorSet2;
                    Property property = View.TRANSLATION_Y;
                    if (view != null && i != 2) {
                        animatorSet2.playTogether(ObjectAnimator.ofFloat(view, (Property<View, Float>) property, -AndroidUtilities.m1036dp(40.0f)), ObjectAnimator.ofFloat(recyclerListView, (Property<RecyclerListView, Float>) property, -AndroidUtilities.m1036dp(36.0f)), ObjectAnimator.ofFloat(searchField2, (Property<SearchField, Float>) property, AndroidUtilities.m1036dp(0.0f)));
                    } else {
                        animatorSet2.playTogether(ObjectAnimator.ofFloat(recyclerListView, (Property<RecyclerListView, Float>) property, i == 2 ? 0.0f : -AndroidUtilities.m1036dp(36.0f)), ObjectAnimator.ofFloat(searchField2, (Property<SearchField, Float>) property, AndroidUtilities.m1036dp(0.0f)));
                    }
                    this.searchAnimation.setDuration(220L);
                    this.searchAnimation.setInterpolator(CubicBezierInterpolator.DEFAULT);
                    this.searchAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.31
                        final /* synthetic */ RecyclerListView val$gridView;

                        public C431031(RecyclerListView recyclerListView2) {
                            recyclerListView = recyclerListView2;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            if (animator.equals(EmojiView.this.searchAnimation)) {
                                recyclerListView.setTranslationY(0.0f);
                                RecyclerListView recyclerListView2 = recyclerListView;
                                RecyclerListView recyclerListView3 = EmojiView.this.stickersGridView;
                                RecyclerListView recyclerListView4 = recyclerListView;
                                if (recyclerListView2 == recyclerListView3) {
                                    recyclerListView4.setPadding(0, 0, 0, EmojiView.this.bottomInset);
                                } else {
                                    EmojiGridView emojiGridView = EmojiView.this.emojiGridView;
                                    RecyclerListView recyclerListView5 = recyclerListView;
                                    if (recyclerListView4 == emojiGridView) {
                                        recyclerListView5.setPadding(AndroidUtilities.m1036dp(5.0f), 0, AndroidUtilities.m1036dp(5.0f), EmojiView.this.bottomInset);
                                    } else if (recyclerListView5 == EmojiView.this.gifGridView) {
                                        recyclerListView.setPadding(0, EmojiView.this.searchFieldHeight, 0, EmojiView.this.bottomInset);
                                    }
                                }
                                EmojiView.this.searchAnimation = null;
                            }
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animator) {
                            if (animator.equals(EmojiView.this.searchAnimation)) {
                                EmojiView.this.searchAnimation = null;
                            }
                        }
                    });
                    this.searchAnimation.start();
                } else {
                    searchField2.setTranslationY(AndroidUtilities.m1036dp(0.0f));
                    if (view != null && i != 2) {
                        view.setTranslationY(-AndroidUtilities.m1036dp(40.0f));
                    }
                    if (recyclerListView2 == this.stickersGridView) {
                        recyclerListView2.setPadding(0, 0, 0, this.bottomInset);
                    } else if (recyclerListView2 == this.emojiGridView) {
                        recyclerListView2.setPadding(AndroidUtilities.m1036dp(5.0f), 0, AndroidUtilities.m1036dp(5.0f), this.bottomInset);
                    } else if (recyclerListView2 == this.gifGridView) {
                        recyclerListView2.setPadding(0, this.searchFieldHeight, 0, this.bottomInset);
                    }
                    if (recyclerListView2 == this.gifGridView) {
                        GifAdapter gifAdapter = this.gifSearchAdapter;
                        boolean z = this.gifAdapter.results.size() > 0;
                        gifAdapter.showTrendingWhenSearchEmpty = z;
                        if (z) {
                            this.gifSearchAdapter.search(_UrlKt.FRAGMENT_ENCODE_SET);
                            RecyclerView.Adapter adapter = this.gifGridView.getAdapter();
                            GifAdapter gifAdapter2 = this.gifSearchAdapter;
                            if (adapter != gifAdapter2) {
                                this.gifGridView.setAdapter(gifAdapter2);
                            }
                        }
                    }
                    linearLayoutManager.scrollToPositionWithOffset(0, 0);
                }
            }
            i++;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$31 */
    public class C431031 extends AnimatorListenerAdapter {
        final /* synthetic */ RecyclerListView val$gridView;

        public C431031(RecyclerListView recyclerListView2) {
            recyclerListView = recyclerListView2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (animator.equals(EmojiView.this.searchAnimation)) {
                recyclerListView.setTranslationY(0.0f);
                RecyclerListView recyclerListView2 = recyclerListView;
                RecyclerListView recyclerListView3 = EmojiView.this.stickersGridView;
                RecyclerListView recyclerListView4 = recyclerListView;
                if (recyclerListView2 == recyclerListView3) {
                    recyclerListView4.setPadding(0, 0, 0, EmojiView.this.bottomInset);
                } else {
                    EmojiGridView emojiGridView = EmojiView.this.emojiGridView;
                    RecyclerListView recyclerListView5 = recyclerListView;
                    if (recyclerListView4 == emojiGridView) {
                        recyclerListView5.setPadding(AndroidUtilities.m1036dp(5.0f), 0, AndroidUtilities.m1036dp(5.0f), EmojiView.this.bottomInset);
                    } else if (recyclerListView5 == EmojiView.this.gifGridView) {
                        recyclerListView.setPadding(0, EmojiView.this.searchFieldHeight, 0, EmojiView.this.bottomInset);
                    }
                }
                EmojiView.this.searchAnimation = null;
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            if (animator.equals(EmojiView.this.searchAnimation)) {
                EmojiView.this.searchAnimation = null;
            }
        }
    }

    private void showEmojiShadow(boolean z, boolean z2) {
        if (z && this.emojiTabsShadow.getTag() == null) {
            return;
        }
        if (z || this.emojiTabsShadow.getTag() == null) {
            AnimatorSet animatorSet = this.emojiTabShadowAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.emojiTabShadowAnimator = null;
            }
            this.emojiTabsShadow.setTag(z ? null : 1);
            if (z2) {
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.emojiTabShadowAnimator = animatorSet2;
                animatorSet2.playTogether(ObjectAnimator.ofFloat(this.emojiTabsShadow, (Property<View, Float>) View.ALPHA, z ? 1.0f : 0.0f));
                this.emojiTabShadowAnimator.setDuration(200L);
                this.emojiTabShadowAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT);
                this.emojiTabShadowAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.32
                    public C431132() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        EmojiView.this.emojiTabShadowAnimator = null;
                    }
                });
                this.emojiTabShadowAnimator.start();
                return;
            }
            this.emojiTabsShadow.setAlpha(z ? 1.0f : 0.0f);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$32 */
    public class C431132 extends AnimatorListenerAdapter {
        public C431132() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            EmojiView.this.emojiTabShadowAnimator = null;
        }
    }

    public void closeSearch(boolean z) {
        closeSearch(z, -1L);
    }

    private void scrollStickersToPosition(int i, int i2) {
        View viewFindViewByPosition = this.stickersLayoutManager.findViewByPosition(i);
        int iFindFirstVisibleItemPosition = this.stickersLayoutManager.findFirstVisibleItemPosition();
        if (viewFindViewByPosition == null && Math.abs(i - iFindFirstVisibleItemPosition) > 40) {
            this.stickersScrollHelper.setScrollDirection(this.stickersLayoutManager.findFirstVisibleItemPosition() < i ? 0 : 1);
            this.stickersScrollHelper.scrollToPosition(i, i2, false, true);
        } else {
            this.ignoreStickersScroll = true;
            this.stickersGridView.smoothScrollToPosition(i);
        }
    }

    public void scrollEmojisToAnimated() {
        if (this.emojiSmoothScrolling) {
            return;
        }
        try {
            int i = this.emojiAdapter.sectionToPosition.get(EmojiData.dataColored.length);
            if (i > 0) {
                this.emojiGridView.stopScroll();
                updateEmojiTabsPosition(i);
                scrollEmojisToPosition(i, AndroidUtilities.m1036dp(-9.0f));
                checkEmojiTabY(null, 0);
            }
        } catch (Exception unused) {
        }
    }

    public void scrollEmojisToPosition(int i, int i2) {
        View viewFindViewByPosition = this.emojiLayoutManager.findViewByPosition(i);
        int iFindFirstVisibleItemPosition = this.emojiLayoutManager.findFirstVisibleItemPosition();
        if ((viewFindViewByPosition == null && Math.abs(i - iFindFirstVisibleItemPosition) > this.emojiLayoutManager.getSpanCount() * 9.0f) || !SharedConfig.animationsEnabled()) {
            this.emojiScrollHelper.setScrollDirection(this.emojiLayoutManager.findFirstVisibleItemPosition() < i ? 0 : 1);
            this.emojiScrollHelper.scrollToPosition(i, i2, false, true);
            return;
        }
        this.ignoreStickersScroll = true;
        C431233 c431233 = new LinearSmoothScrollerCustom(this.emojiGridView.getContext(), 2) { // from class: org.telegram.ui.Components.EmojiView.33
            public C431233(Context context, int i3) {
                super(context, i3);
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScrollerCustom
            public void onEnd() {
                EmojiView.this.emojiSmoothScrolling = false;
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScrollerCustom, androidx.recyclerview.widget.RecyclerView.SmoothScroller
            public void onStart() {
                EmojiView.this.emojiSmoothScrolling = true;
            }
        };
        c431233.setTargetPosition(i);
        c431233.setOffset(i2);
        this.emojiLayoutManager.startSmoothScroll(c431233);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$33 */
    public class C431233 extends LinearSmoothScrollerCustom {
        public C431233(Context context, int i3) {
            super(context, i3);
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScrollerCustom
        public void onEnd() {
            EmojiView.this.emojiSmoothScrolling = false;
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScrollerCustom, androidx.recyclerview.widget.RecyclerView.SmoothScroller
        public void onStart() {
            EmojiView.this.emojiSmoothScrolling = true;
        }
    }

    public void closeSearch(boolean z, long j) {
        SearchField searchField;
        RecyclerListView recyclerListView;
        GridLayoutManager gridLayoutManager;
        View view;
        TLRPC.TL_messages_stickerSet stickerSetById;
        int positionForPack;
        AnimatorSet animatorSet = this.searchAnimation;
        StickerCategoriesListView.EmojiCategory emojiCategory = null;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.searchAnimation = null;
        }
        int currentItem = this.pager.getCurrentItem();
        if (currentItem == 2 && j != -1 && (stickerSetById = MediaDataController.getInstance(this.currentAccount).getStickerSetById(j)) != null && (positionForPack = this.stickersGridAdapter.getPositionForPack(stickerSetById)) >= 0 && positionForPack < this.stickersGridAdapter.getItemCount()) {
            scrollStickersToPosition(positionForPack, AndroidUtilities.m1036dp(48.0f));
        }
        GifAdapter gifAdapter = this.gifSearchAdapter;
        if (gifAdapter != null) {
            gifAdapter.showTrendingWhenSearchEmpty = false;
        }
        int i = 0;
        while (i < 3) {
            if (i == 0) {
                searchField = this.emojiSearchField;
                recyclerListView = this.emojiGridView;
                gridLayoutManager = this.emojiLayoutManager;
                view = this.emojiTabs;
            } else if (i == 1) {
                searchField = this.gifSearchField;
                recyclerListView = this.gifGridView;
                gridLayoutManager = this.gifLayoutManager;
                view = this.gifTabs;
            } else {
                searchField = this.stickersSearchField;
                recyclerListView = this.stickersGridView;
                gridLayoutManager = this.stickersLayoutManager;
                view = this.stickersTab;
            }
            if (searchField != null) {
                searchField.searchEditText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
                if (searchField.categoriesListView != null) {
                    searchField.categoriesListView.selectCategory(emojiCategory);
                    searchField.categoriesListView.scrollToStart();
                }
                if (i == currentItem && z) {
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    this.searchAnimation = animatorSet2;
                    Property property = View.TRANSLATION_Y;
                    if (view != null && i != 1) {
                        animatorSet2.playTogether(ObjectAnimator.ofFloat(view, (Property<View, Float>) property, 0.0f), ObjectAnimator.ofFloat(recyclerListView, (Property<RecyclerListView, Float>) property, AndroidUtilities.m1036dp(36.0f)), ObjectAnimator.ofFloat(searchField, (Property<SearchField, Float>) property, AndroidUtilities.m1036dp(36.0f)));
                    } else {
                        animatorSet2.playTogether(ObjectAnimator.ofFloat(recyclerListView, (Property<RecyclerListView, Float>) property, AndroidUtilities.m1036dp(36.0f) - this.searchFieldHeight));
                    }
                    this.searchAnimation.setDuration(200L);
                    this.searchAnimation.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                    this.searchAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.34
                        final /* synthetic */ RecyclerListView val$gridView;
                        final /* synthetic */ GridLayoutManager val$layoutManager;

                        public C431334(GridLayoutManager gridLayoutManager2, RecyclerListView recyclerListView2) {
                            gridLayoutManager = gridLayoutManager2;
                            recyclerListView = recyclerListView2;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            if (animator.equals(EmojiView.this.searchAnimation)) {
                                int iFindFirstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
                                recyclerListView.setTranslationY(0.0f);
                                RecyclerListView recyclerListView2 = recyclerListView;
                                RecyclerListView recyclerListView3 = EmojiView.this.stickersGridView;
                                RecyclerListView recyclerListView4 = recyclerListView;
                                if (recyclerListView2 == recyclerListView3) {
                                    recyclerListView4.setPadding(0, AndroidUtilities.m1036dp(36.0f), 0, AndroidUtilities.m1036dp(44.0f) + EmojiView.this.bottomInset);
                                } else {
                                    RecyclerListView recyclerListView5 = EmojiView.this.gifGridView;
                                    RecyclerListView recyclerListView6 = recyclerListView;
                                    if (recyclerListView4 == recyclerListView5) {
                                        recyclerListView6.setPadding(0, EmojiView.this.searchFieldHeight, 0, AndroidUtilities.m1036dp(44.0f) + EmojiView.this.bottomInset);
                                    } else if (recyclerListView6 == EmojiView.this.emojiGridView) {
                                        recyclerListView.setPadding(AndroidUtilities.m1036dp(5.0f), AndroidUtilities.m1036dp(36.0f), AndroidUtilities.m1036dp(5.0f), AndroidUtilities.m1036dp(44.0f) + EmojiView.this.bottomInset);
                                    }
                                }
                                if (iFindFirstVisibleItemPosition != -1) {
                                    gridLayoutManager.scrollToPositionWithOffset(iFindFirstVisibleItemPosition, 0);
                                }
                                EmojiView.this.searchAnimation = null;
                            }
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animator) {
                            if (animator.equals(EmojiView.this.searchAnimation)) {
                                EmojiView.this.searchAnimation = null;
                            }
                        }
                    });
                    this.searchAnimation.start();
                } else {
                    if (searchField != this.gifSearchField) {
                        searchField.setTranslationY(AndroidUtilities.m1036dp(36.0f) - this.searchFieldHeight);
                    }
                    if (view != null && i != 2) {
                        view.setTranslationY(0.0f);
                    }
                    if (recyclerListView2 == this.stickersGridView) {
                        recyclerListView2.setPadding(0, AndroidUtilities.m1036dp(36.0f), 0, AndroidUtilities.m1036dp(44.0f) + this.bottomInset);
                    } else if (recyclerListView2 == this.gifGridView) {
                        recyclerListView2.setPadding(0, AndroidUtilities.m1036dp(40.0f), 0, AndroidUtilities.m1036dp(44.0f) + this.bottomInset);
                    } else if (recyclerListView2 == this.emojiGridView) {
                        recyclerListView2.setPadding(AndroidUtilities.m1036dp(5.0f), AndroidUtilities.m1036dp(36.0f), AndroidUtilities.m1036dp(5.0f), AndroidUtilities.m1036dp(44.0f) + this.bottomInset);
                    }
                    gridLayoutManager2.scrollToPositionWithOffset(0, 0);
                }
            }
            i++;
            emojiCategory = null;
        }
        if (z) {
            return;
        }
        this.delegate.onSearchOpenClose(0);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$34 */
    public class C431334 extends AnimatorListenerAdapter {
        final /* synthetic */ RecyclerListView val$gridView;
        final /* synthetic */ GridLayoutManager val$layoutManager;

        public C431334(GridLayoutManager gridLayoutManager2, RecyclerListView recyclerListView2) {
            gridLayoutManager = gridLayoutManager2;
            recyclerListView = recyclerListView2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (animator.equals(EmojiView.this.searchAnimation)) {
                int iFindFirstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
                recyclerListView.setTranslationY(0.0f);
                RecyclerListView recyclerListView2 = recyclerListView;
                RecyclerListView recyclerListView3 = EmojiView.this.stickersGridView;
                RecyclerListView recyclerListView4 = recyclerListView;
                if (recyclerListView2 == recyclerListView3) {
                    recyclerListView4.setPadding(0, AndroidUtilities.m1036dp(36.0f), 0, AndroidUtilities.m1036dp(44.0f) + EmojiView.this.bottomInset);
                } else {
                    RecyclerListView recyclerListView5 = EmojiView.this.gifGridView;
                    RecyclerListView recyclerListView6 = recyclerListView;
                    if (recyclerListView4 == recyclerListView5) {
                        recyclerListView6.setPadding(0, EmojiView.this.searchFieldHeight, 0, AndroidUtilities.m1036dp(44.0f) + EmojiView.this.bottomInset);
                    } else if (recyclerListView6 == EmojiView.this.emojiGridView) {
                        recyclerListView.setPadding(AndroidUtilities.m1036dp(5.0f), AndroidUtilities.m1036dp(36.0f), AndroidUtilities.m1036dp(5.0f), AndroidUtilities.m1036dp(44.0f) + EmojiView.this.bottomInset);
                    }
                }
                if (iFindFirstVisibleItemPosition != -1) {
                    gridLayoutManager.scrollToPositionWithOffset(iFindFirstVisibleItemPosition, 0);
                }
                EmojiView.this.searchAnimation = null;
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            if (animator.equals(EmojiView.this.searchAnimation)) {
                EmojiView.this.searchAnimation = null;
            }
        }
    }

    public void checkStickersSearchFieldScroll(boolean z) {
        RecyclerListView recyclerListView;
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate != null && emojiViewDelegate.isSearchOpened()) {
            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.stickersGridView.findViewHolderForAdapterPosition(0);
            SearchField searchField = this.stickersSearchField;
            if (viewHolderFindViewHolderForAdapterPosition == null) {
                searchField.showShadow(true, !z);
            } else {
                searchField.showShadow(viewHolderFindViewHolderForAdapterPosition.itemView.getTop() < this.stickersGridView.getPaddingTop(), !z);
            }
            this.stickersSearchField.setTranslationY(this.animatorSearchStickerPackSelected.getFloatValue() * AndroidUtilities.m1036dp(15.0f));
            return;
        }
        if (this.stickersSearchField == null || (recyclerListView = this.stickersGridView) == null) {
            return;
        }
        this.stickersSearchField.setTranslationY((recyclerListView.findViewHolderForAdapterPosition(0) != null ? r0.itemView.getTop() : -this.searchFieldHeight) + (this.animatorSearchStickerPackSelected.getFloatValue() * AndroidUtilities.m1036dp(15.0f)));
        this.stickersSearchField.showShadow(false, !z);
    }

    private void checkStickersSearchFieldVisibility() {
        float floatValue = 1.0f - this.animatorSearchStickerPackSelected.getFloatValue();
        this.stickersSearchField.setAlpha(floatValue);
        this.stickersSearchField.setVisibility(floatValue > 0.0f ? 0 : 4);
        float f = 1.0f - floatValue;
        this.stickerSearchHeader.setAlpha(f);
        this.stickerSearchHeader.setTranslationY((-AndroidUtilities.m1036dp(15.0f)) * floatValue);
        this.stickerSearchHeader.setVisibility(f > 0.0f ? 0 : 4);
        this.stickerAddPackButtonContainer.setAlpha(f);
        this.stickerAddPackButtonContainer.setTranslationY(AndroidUtilities.m1036dp(30.0f) * floatValue);
        this.stickerAddPackButtonContainer.setVisibility(f > 0.0f ? 0 : 4);
    }

    private void checkEmojiSearchFieldVisibility() {
        float floatValue = 1.0f - this.animatorSearchEmojiPackSelected.getFloatValue();
        this.emojiSearchField.setAlpha(floatValue);
        this.emojiSearchField.setVisibility(floatValue > 0.0f ? 0 : 4);
        float f = 1.0f - floatValue;
        this.emojiSearchHeader.setAlpha(f);
        this.emojiSearchHeader.setTranslationY((-AndroidUtilities.m1036dp(15.0f)) * floatValue);
        this.emojiSearchHeader.setVisibility(f > 0.0f ? 0 : 4);
        this.emojiAddPackButtonContainer.setAlpha(f);
        this.emojiAddPackButtonContainer.setTranslationY(AndroidUtilities.m1036dp(30.0f) * floatValue);
        this.emojiAddPackButtonContainer.setVisibility(f > 0.0f ? 0 : 4);
    }

    public void checkBottomTabScroll(float f) {
        int iM1036dp;
        if (SystemClock.elapsedRealtime() - this.shownBottomTabAfterClick < ViewConfiguration.getTapTimeout()) {
            return;
        }
        this.lastBottomScrollDy += f;
        if (this.pager.getCurrentItem() == 0) {
            iM1036dp = AndroidUtilities.m1036dp(38.0f);
        } else {
            iM1036dp = AndroidUtilities.m1036dp(48.0f);
        }
        float f2 = this.lastBottomScrollDy;
        if (f2 >= iM1036dp) {
            showBottomTab(false, true);
            return;
        }
        if (f2 <= (-iM1036dp)) {
            showBottomTab(true, true);
        } else {
            if ((this.bottomTabContainer.getTag() != null || this.lastBottomScrollDy >= 0.0f) && (this.bottomTabContainer.getTag() == null || this.lastBottomScrollDy <= 0.0f)) {
                return;
            }
            this.lastBottomScrollDy = 0.0f;
        }
    }

    public void showBackspaceButton(boolean z, boolean z2) {
        if (z && this.backspaceButton.getTag() == null) {
            return;
        }
        if ((z || this.backspaceButton.getTag() == null) && !this.mForceHideBackspaceButton) {
            AnimatorSet animatorSet = this.backspaceButtonAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.backspaceButtonAnimation = null;
            }
            this.backspaceButton.setTag(z ? null : 1);
            if (z2) {
                if (z) {
                    this.backspaceButton.setVisibility(0);
                }
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.backspaceButtonAnimation = animatorSet2;
                animatorSet2.playTogether(ObjectAnimator.ofFloat(this.backspaceButton, (Property<ImageView, Float>) View.ALPHA, z ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.backspaceButton, (Property<ImageView, Float>) View.SCALE_X, z ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.backspaceButton, (Property<ImageView, Float>) View.SCALE_Y, z ? 1.0f : 0.0f));
                this.backspaceButtonAnimation.setDuration(200L);
                this.backspaceButtonAnimation.setInterpolator(CubicBezierInterpolator.EASE_OUT);
                this.backspaceButtonAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.35
                    final /* synthetic */ boolean val$show;

                    public C431435(boolean z3) {
                        z = z3;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (z) {
                            return;
                        }
                        EmojiView.this.backspaceButton.setVisibility(4);
                    }
                });
                this.backspaceButtonAnimation.start();
                return;
            }
            this.backspaceButton.setAlpha(z3 ? 1.0f : 0.0f);
            this.backspaceButton.setScaleX(z3 ? 1.0f : 0.0f);
            this.backspaceButton.setScaleY(z3 ? 1.0f : 0.0f);
            this.backspaceButton.setVisibility(z3 ? 0 : 4);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$35 */
    public class C431435 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$show;

        public C431435(boolean z3) {
            z = z3;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (z) {
                return;
            }
            EmojiView.this.backspaceButton.setVisibility(4);
        }
    }

    public void showStickerSettingsButton(boolean z, boolean z2) {
        ImageView imageView = this.stickerSettingsButton;
        if (imageView == null || this.mForceHideSettingsButton) {
            return;
        }
        if (z && imageView.getTag() == null) {
            return;
        }
        if (z || this.stickerSettingsButton.getTag() == null) {
            AnimatorSet animatorSet = this.stickersButtonAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.stickersButtonAnimation = null;
            }
            this.stickerSettingsButton.setTag(z ? null : 1);
            if (z2) {
                if (z) {
                    this.stickerSettingsButton.setVisibility(0);
                }
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.stickersButtonAnimation = animatorSet2;
                animatorSet2.playTogether(ObjectAnimator.ofFloat(this.stickerSettingsButton, (Property<ImageView, Float>) View.ALPHA, z ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.stickerSettingsButton, (Property<ImageView, Float>) View.SCALE_X, z ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.stickerSettingsButton, (Property<ImageView, Float>) View.SCALE_Y, z ? 1.0f : 0.0f));
                this.stickersButtonAnimation.setDuration(200L);
                this.stickersButtonAnimation.setInterpolator(CubicBezierInterpolator.EASE_OUT);
                this.stickersButtonAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.36
                    final /* synthetic */ boolean val$show;

                    public C431536(boolean z3) {
                        z = z3;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (z) {
                            return;
                        }
                        EmojiView.this.stickerSettingsButton.setVisibility(4);
                    }
                });
                this.stickersButtonAnimation.start();
                return;
            }
            this.stickerSettingsButton.setAlpha(z3 ? 1.0f : 0.0f);
            this.stickerSettingsButton.setScaleX(z3 ? 1.0f : 0.0f);
            this.stickerSettingsButton.setScaleY(z3 ? 1.0f : 0.0f);
            this.stickerSettingsButton.setVisibility(z3 ? 0 : 4);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$36 */
    public class C431536 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$show;

        public C431536(boolean z3) {
            z = z3;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (z) {
                return;
            }
            EmojiView.this.stickerSettingsButton.setVisibility(4);
        }
    }

    public /* synthetic */ void lambda$new$25(int i, float f, float f2, FactorAnimator factorAnimator) {
        updateBottomTabContainerPosition();
    }

    public void showBottomTab(boolean z, boolean z2) {
        this.lastBottomScrollDy = 0.0f;
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate != null && emojiViewDelegate.isSearchOpened()) {
            z = false;
        }
        if (z && this.bottomTabContainer.getTag() == null) {
            return;
        }
        if (z || this.bottomTabContainer.getTag() == null) {
            this.bottomTabContainer.setTag(z ? null : 1);
            this.bottomTabVisibility.setValue(z, z2);
        }
    }

    public void checkTabsY(int i, int i2) {
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition;
        if (i == 1) {
            checkEmojiTabY(this.emojiGridView, i2);
            return;
        }
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if ((emojiViewDelegate == null || !emojiViewDelegate.isSearchOpened()) && !this.ignoreStickersScroll) {
            RecyclerListView listViewForType = getListViewForType(i);
            if (i2 <= 0 || listViewForType == null || listViewForType.getVisibility() != 0 || (viewHolderFindViewHolderForAdapterPosition = listViewForType.findViewHolderForAdapterPosition(0)) == null || viewHolderFindViewHolderForAdapterPosition.itemView.getTop() + this.searchFieldHeight < listViewForType.getPaddingTop()) {
                int[] iArr = this.tabsMinusDy;
                int i3 = iArr[i] - i2;
                iArr[i] = i3;
                if (i3 > 0) {
                    iArr[i] = 0;
                } else if (i3 < (-AndroidUtilities.m1036dp(288.0f))) {
                    this.tabsMinusDy[i] = -AndroidUtilities.m1036dp(288.0f);
                }
                if (i == 0) {
                    updateStickerTabsPosition();
                } else {
                    getTabsForType(i).setTranslationY(Math.max(-AndroidUtilities.m1036dp(48.0f), this.tabsMinusDy[i]));
                }
            }
        }
    }

    private void resetTabsY(int i) {
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if ((emojiViewDelegate == null || !emojiViewDelegate.isSearchOpened()) && i != 0) {
            View tabsForType = getTabsForType(i);
            this.tabsMinusDy[i] = 0;
            tabsForType.setTranslationY(0.0f);
        }
    }

    public void animateTabsY(final int i) {
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if ((emojiViewDelegate == null || !emojiViewDelegate.isSearchOpened()) && i != 0) {
            float fDpf2 = AndroidUtilities.dpf2(i == 1 ? 36.0f : 48.0f);
            float f = this.tabsMinusDy[i] / (-fDpf2);
            if (f <= 0.0f || f >= 1.0f) {
                animateSearchField(i);
                return;
            }
            View tabsForType = getTabsForType(i);
            int i2 = f > 0.5f ? (int) (-Math.ceil(fDpf2)) : 0;
            if (f > 0.5f) {
                animateSearchField(i, false, i2);
            }
            if (i == 1) {
                checkEmojiShadow(i2);
            }
            ObjectAnimator[] objectAnimatorArr = this.tabsYAnimators;
            ObjectAnimator objectAnimator = objectAnimatorArr[i];
            if (objectAnimator == null) {
                objectAnimatorArr[i] = ObjectAnimator.ofFloat(tabsForType, (Property<View, Float>) View.TRANSLATION_Y, tabsForType.getTranslationY(), i2);
                this.tabsYAnimators[i].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda33
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$animateTabsY$26(i, valueAnimator);
                    }
                });
                this.tabsYAnimators[i].setDuration(200L);
            } else {
                objectAnimator.setFloatValues(tabsForType.getTranslationY(), i2);
            }
            this.tabsYAnimators[i].start();
        }
    }

    public /* synthetic */ void lambda$animateTabsY$26(int i, ValueAnimator valueAnimator) {
        this.tabsMinusDy[i] = (int) ((Float) valueAnimator.getAnimatedValue()).floatValue();
    }

    public void stopAnimatingTabsY(int i) {
        ObjectAnimator objectAnimator = this.tabsYAnimators[i];
        if (objectAnimator == null || !objectAnimator.isRunning()) {
            return;
        }
        this.tabsYAnimators[i].cancel();
    }

    private void animateSearchField(int i) {
        RecyclerListView listViewForType = getListViewForType(i);
        int iM1036dp = AndroidUtilities.m1036dp(i == 1 ? 38.0f : 48.0f);
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = listViewForType.findViewHolderForAdapterPosition(0);
        if (viewHolderFindViewHolderForAdapterPosition != null) {
            int bottom = viewHolderFindViewHolderForAdapterPosition.itemView.getBottom();
            int i2 = this.tabsMinusDy[i];
            float f = (bottom - (iM1036dp + i2)) / this.searchFieldHeight;
            if (f > 0.0f || f < 1.0f) {
                animateSearchField(i, f > 0.5f, i2);
            }
        }
    }

    private void animateSearchField(int i, boolean z, int i2) {
        if (i == 2 || getListViewForType(i).findViewHolderForAdapterPosition(0) == null) {
            return;
        }
        C431637 c431637 = new LinearSmoothScroller(getContext()) { // from class: org.telegram.ui.Components.EmojiView.37
            final /* synthetic */ int val$tabsMinusDy;

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int getVerticalSnapPreference() {
                return -1;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C431637(Context context, int i22) {
                super(context);
                i = i22;
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateTimeForDeceleration(int i3) {
                return super.calculateTimeForDeceleration(i3) * 16;
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateDtToFit(int i3, int i4, int i5, int i6, int i7) {
                return super.calculateDtToFit(i3, i4, i5, i6, i7) + i;
            }
        };
        c431637.setTargetPosition(!z ? 1 : 0);
        getLayoutManagerForType(i).startSmoothScroll(c431637);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$37 */
    public class C431637 extends LinearSmoothScroller {
        final /* synthetic */ int val$tabsMinusDy;

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        public int getVerticalSnapPreference() {
            return -1;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C431637(Context context, int i22) {
            super(context);
            i = i22;
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        public int calculateTimeForDeceleration(int i3) {
            return super.calculateTimeForDeceleration(i3) * 16;
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        public int calculateDtToFit(int i3, int i4, int i5, int i6, int i7) {
            return super.calculateDtToFit(i3, i4, i5, i6, i7) + i;
        }
    }

    public View getTabsForType(int i) {
        if (i == 0) {
            return this.stickersTab;
        }
        if (i == 1) {
            return this.emojiTabs;
        }
        if (i == 2) {
            return this.gifTabs;
        }
        CharCodeKt$$ExternalSyntheticBUOutline0.m873m("Unexpected argument: ", i);
        return null;
    }

    public RecyclerListView getListViewForType(int i) {
        if (i == 0) {
            return this.stickersGridView;
        }
        if (i == 1) {
            return this.emojiGridView;
        }
        if (i == 2) {
            return this.gifGridView;
        }
        CharCodeKt$$ExternalSyntheticBUOutline0.m873m("Unexpected argument: ", i);
        return null;
    }

    private GridLayoutManager getLayoutManagerForType(int i) {
        if (i == 0) {
            return this.stickersLayoutManager;
        }
        if (i == 1) {
            return this.emojiLayoutManager;
        }
        if (i == 2) {
            return this.gifLayoutManager;
        }
        CharCodeKt$$ExternalSyntheticBUOutline0.m873m("Unexpected argument: ", i);
        return null;
    }

    public SearchField getSearchFieldForType(int i) {
        if (i == 0) {
            return this.stickersSearchField;
        }
        if (i == 1) {
            return this.emojiSearchField;
        }
        if (i == 2) {
            return this.gifSearchField;
        }
        CharCodeKt$$ExternalSyntheticBUOutline0.m873m("Unexpected argument: ", i);
        return null;
    }

    public void scrollEmojiToTop() {
        this.emojiGridView.stopScroll();
        this.emojiTabs.scrollTo(0, 0);
        resetTabsY(1);
        this.emojiLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    public void checkEmojiSearchFieldScroll(boolean z) {
        EmojiGridView emojiGridView;
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate != null && emojiViewDelegate.isSearchOpened()) {
            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.emojiGridView.findViewHolderForAdapterPosition(0);
            SearchField searchField = this.emojiSearchField;
            if (viewHolderFindViewHolderForAdapterPosition == null) {
                searchField.showShadow(true, !z);
            } else {
                searchField.showShadow(viewHolderFindViewHolderForAdapterPosition.itemView.getTop() < this.emojiGridView.getPaddingTop(), !z);
            }
            showEmojiShadow(false, !z);
            this.emojiSearchField.setTranslationY(this.animatorSearchEmojiPackSelected.getFloatValue() * AndroidUtilities.m1036dp(15.0f));
            return;
        }
        if (this.emojiSearchField == null || (emojiGridView = this.emojiGridView) == null) {
            return;
        }
        this.emojiSearchField.setTranslationY((emojiGridView.findViewHolderForAdapterPosition(0) != null ? r0.itemView.getTop() : -this.searchFieldHeight) + (this.animatorSearchEmojiPackSelected.getFloatValue() * AndroidUtilities.m1036dp(15.0f)));
        this.emojiSearchField.showShadow(false, !z);
        checkEmojiShadow(Math.round(this.emojiTabs.getTranslationY()));
    }

    private void checkEmojiShadow(int i) {
        ObjectAnimator objectAnimator = this.tabsYAnimators[1];
        if (objectAnimator == null || !objectAnimator.isRunning()) {
            boolean z = false;
            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.emojiGridView.findViewHolderForAdapterPosition(0);
            int iM1036dp = AndroidUtilities.m1036dp(38.0f) + i;
            if (iM1036dp > 0 && (viewHolderFindViewHolderForAdapterPosition == null || viewHolderFindViewHolderForAdapterPosition.itemView.getBottom() < iM1036dp)) {
                z = true;
            }
            showEmojiShadow(z, !this.isLayout);
        }
    }

    public void checkEmojiTabY(View view, int i) {
        EmojiGridView emojiGridView;
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition;
        if (view == null) {
            EmojiTabsStrip emojiTabsStrip = this.emojiTabs;
            this.tabsMinusDy[1] = 0;
            emojiTabsStrip.setTranslationY(0.0f);
            return;
        }
        if (view.getVisibility() != 0 || this.emojiSmoothScrolling) {
            return;
        }
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate == null || !emojiViewDelegate.isSearchOpened()) {
            if (i > 0 && (emojiGridView = this.emojiGridView) != null && emojiGridView.getVisibility() == 0 && (viewHolderFindViewHolderForAdapterPosition = this.emojiGridView.findViewHolderForAdapterPosition(0)) != null) {
                if (viewHolderFindViewHolderForAdapterPosition.itemView.getTop() + (this.needEmojiSearch ? this.searchFieldHeight : 0) >= this.emojiGridView.getPaddingTop()) {
                    return;
                }
            }
            int[] iArr = this.tabsMinusDy;
            int i2 = iArr[1] - i;
            iArr[1] = i2;
            if (i2 > 0) {
                iArr[1] = 0;
            } else if (i2 < (-AndroidUtilities.m1036dp(108.0f))) {
                this.tabsMinusDy[1] = -AndroidUtilities.m1036dp(108.0f);
            }
            this.emojiTabs.setTranslationY(Math.max(-AndroidUtilities.m1036dp(36.0f), this.tabsMinusDy[1]));
        }
    }

    public void checkGifSearchFieldScroll(boolean z) {
        int iFindLastVisibleItemPosition;
        RecyclerListView recyclerListView = this.gifGridView;
        if (recyclerListView != null && (recyclerListView.getAdapter() instanceof GifAdapter)) {
            GifAdapter gifAdapter = (GifAdapter) this.gifGridView.getAdapter();
            if (!gifAdapter.searchEndReached && gifAdapter.reqId == 0 && !gifAdapter.results.isEmpty() && (iFindLastVisibleItemPosition = this.gifLayoutManager.findLastVisibleItemPosition()) != -1 && iFindLastVisibleItemPosition > this.gifLayoutManager.getItemCount() - 5) {
                gifAdapter.search(gifAdapter.lastSearchImageString, gifAdapter.nextSearchOffset, true, gifAdapter.lastSearchIsEmoji, gifAdapter.lastSearchIsEmoji);
            }
        }
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate != null && emojiViewDelegate.isSearchOpened()) {
            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.gifGridView.findViewHolderForAdapterPosition(0);
            SearchField searchField = this.gifSearchField;
            if (viewHolderFindViewHolderForAdapterPosition == null) {
                searchField.showShadow(true, !z);
                return;
            } else {
                searchField.showShadow(viewHolderFindViewHolderForAdapterPosition.itemView.getTop() < this.gifGridView.getPaddingTop(), !z);
                return;
            }
        }
        SearchField searchField2 = this.gifSearchField;
        if (searchField2 == null || this.gifGridView == null) {
            return;
        }
        searchField2.showShadow(true, !z);
    }

    public void scrollGifsToTop() {
        this.gifLayoutManager.scrollToPositionWithOffset(0, 0);
        resetTabsY(2);
    }

    public void checkScroll(int i) {
        int iFindFirstVisibleItemPosition;
        int iFindFirstVisibleItemPosition2;
        if (i == 0) {
            if (this.ignoreStickersScroll || (iFindFirstVisibleItemPosition2 = this.stickersLayoutManager.findFirstVisibleItemPosition()) == -1 || this.stickersGridView == null) {
                return;
            }
            int i2 = this.favTabNum;
            if (i2 <= 0 && (i2 = this.recentTabNum) <= 0) {
                i2 = this.stickersTabOffset;
            }
            this.stickersTab.onPageScrolled(this.stickersGridAdapter.getTabForPosition(iFindFirstVisibleItemPosition2), i2);
            return;
        }
        if (i == 2) {
            RecyclerView.Adapter adapter = this.gifGridView.getAdapter();
            GifAdapter gifAdapter = this.gifAdapter;
            if (adapter != gifAdapter || gifAdapter.trendingSectionItem < 0 || this.gifTrendingTabNum < 0 || this.gifRecentTabNum < 0 || (iFindFirstVisibleItemPosition = this.gifLayoutManager.findFirstVisibleItemPosition()) == -1) {
                return;
            }
            this.gifTabs.onPageScrolled(iFindFirstVisibleItemPosition >= this.gifAdapter.trendingSectionItem ? this.gifTrendingTabNum : this.gifRecentTabNum, 0);
        }
    }

    public void saveNewPage() {
        ViewPager viewPager = this.pager;
        if (viewPager == null) {
            return;
        }
        int currentItem = viewPager.getCurrentItem();
        int i = currentItem != 2 ? currentItem == 1 ? 2 : 0 : 1;
        if (this.currentPage != i) {
            this.currentPage = i;
            MessagesController.getGlobalEmojiSettings().edit().putInt("selected_page", i).apply();
        }
    }

    public void clearRecentEmoji() {
        Emoji.clearRecentEmoji();
        this.emojiAdapter.notifyDataSetChanged();
    }

    public void onPageScrolled(int i, int i2, int i3) {
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate == null) {
            return;
        }
        if (i == 1) {
            emojiViewDelegate.onTabOpened(i3 != 0 ? 2 : 0);
        } else if (i == 2) {
            emojiViewDelegate.onTabOpened(3);
        } else {
            emojiViewDelegate.onTabOpened(0);
        }
    }

    public void postBackspaceRunnable(final int i) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda34
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$postBackspaceRunnable$27(i);
            }
        }, i);
    }

    public /* synthetic */ void lambda$postBackspaceRunnable$27(int i) {
        if (this.backspacePressed) {
            EmojiViewDelegate emojiViewDelegate = this.delegate;
            if (emojiViewDelegate != null && emojiViewDelegate.onBackspace()) {
                try {
                    this.backspaceButton.performHapticFeedback(3);
                } catch (Exception unused) {
                }
            }
            this.backspaceOnce = true;
            postBackspaceRunnable(Math.max(50, i - 100));
        }
    }

    public void switchToGifRecent() {
        showBackspaceButton(false, false);
        showStickerSettingsButton(false, false);
        this.pager.setCurrentItem(1, false);
    }

    public void updateEmojiHeaders() {
        if (this.emojiGridView == null) {
            return;
        }
        for (int i = 0; i < this.emojiGridView.getChildCount(); i++) {
            View childAt = this.emojiGridView.getChildAt(i);
            if (childAt instanceof EmojiPackHeader) {
                ((EmojiPackHeader) childAt).updateState(true);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:301:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x02b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateStickerTabs(boolean r13) {
        /*
            Method dump skipped, instruction units count: 755
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.EmojiView.updateStickerTabs(boolean):void");
    }

    private void checkPanels() {
        int iFindFirstVisibleItemPosition;
        if (this.stickersTab == null || (iFindFirstVisibleItemPosition = this.stickersLayoutManager.findFirstVisibleItemPosition()) == -1) {
            return;
        }
        int i = this.favTabNum;
        if (i <= 0 && (i = this.recentTabNum) <= 0) {
            i = this.stickersTabOffset;
        }
        this.stickersTab.onPageScrolled(this.stickersGridAdapter.getTabForPosition(iFindFirstVisibleItemPosition), i);
    }

    private void updateGifTabs() {
        int i;
        int currentPosition = this.gifTabs.getCurrentPosition();
        int i2 = this.gifRecentTabNum;
        boolean z = currentPosition == i2;
        boolean z2 = i2 >= 0;
        boolean zIsEmpty = this.recentGifs.isEmpty();
        this.gifTabs.beginUpdate(false);
        this.gifRecentTabNum = -2;
        this.gifTrendingTabNum = -2;
        this.gifFirstEmojiTabNum = -2;
        if (zIsEmpty) {
            i = 0;
        } else {
            this.gifRecentTabNum = 0;
            this.gifTabs.addIconTab(0, this.gifIcons[0]).setContentDescription(LocaleController.getString(C2797R.string.RecentStickers));
            i = 1;
        }
        this.gifTrendingTabNum = i;
        this.gifTabs.addIconTab(1, this.gifIcons[1]).setContentDescription(LocaleController.getString(C2797R.string.FeaturedGifs));
        this.gifFirstEmojiTabNum = i + 1;
        AndroidUtilities.m1036dp(13.0f);
        AndroidUtilities.m1036dp(11.0f);
        ArrayList<String> arrayList = MessagesController.getInstance(this.currentAccount).gifSearchEmojies;
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            String str = arrayList.get(i3);
            Emoji.EmojiDrawable emojiDrawable = Emoji.getEmojiDrawable(str);
            if (emojiDrawable != null) {
                this.gifTabs.addEmojiTab(i3 + 3, emojiDrawable, MediaDataController.getInstance(this.currentAccount).getEmojiAnimatedSticker(str)).setContentDescription(str);
            }
        }
        this.gifTabs.commitUpdate();
        this.gifTabs.updateTabStyles();
        if (z && zIsEmpty) {
            this.gifTabs.selectTab(this.gifTrendingTabNum);
            SearchField searchField = this.gifSearchField;
            if (searchField == null || searchField.categoriesListView == null) {
                return;
            }
            this.gifSearchField.categoriesListView.selectCategory(this.gifSearchField.trending);
            return;
        }
        if (ViewCompat.isLaidOut(this.gifTabs)) {
            if (zIsEmpty || z2) {
                if (zIsEmpty && z2) {
                    this.gifTabs.onPageScrolled(currentPosition - 1, 0);
                }
            } else {
                this.gifTabs.onPageScrolled(currentPosition + 1, 0);
            }
        }
    }

    public void addRecentSticker(TLRPC.Document document) {
        if (document == null) {
            return;
        }
        MediaDataController.getInstance(this.currentAccount).addRecentSticker(0, null, document, (int) (System.currentTimeMillis() / 1000), false);
        boolean zIsEmpty = this.recentStickers.isEmpty();
        this.recentStickers = MediaDataController.getInstance(this.currentAccount).getRecentStickers(0, true);
        StickersGridAdapter stickersGridAdapter = this.stickersGridAdapter;
        if (stickersGridAdapter != null) {
            stickersGridAdapter.notifyDataSetChanged();
        }
        if (zIsEmpty) {
            updateStickerTabs(false);
        }
    }

    public void addRecentGif(TLRPC.Document document) {
        if (document == null) {
            return;
        }
        boolean zIsEmpty = this.recentGifs.isEmpty();
        updateRecentGifs();
        if (zIsEmpty) {
            updateStickerTabs(false);
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.isLayout) {
            return;
        }
        super.requestLayout();
    }

    public void updateColors() {
        SearchField searchField;
        if (!this.shouldDrawBackground) {
            setBackground(null);
            this.bottomTabContainerBackground.setBackground(null);
        } else if (AndroidUtilities.isInMultiwindow || this.forseMultiwindowLayout) {
            Drawable background = getBackground();
            if (background != null) {
                background.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_chat_emojiPanelBackground), PorterDuff.Mode.MULTIPLY));
            }
        } else {
            int i = Theme.key_chat_emojiPanelBackground;
            setBackgroundColor(getThemedColor(i));
            if (this.needEmojiSearch) {
                this.bottomTabContainerBackground.setBackgroundColor(getThemedColor(i));
            }
        }
        EmojiTabsStrip emojiTabsStrip = this.emojiTabs;
        if (emojiTabsStrip != null) {
            if (this.shouldDrawBackground) {
                emojiTabsStrip.setBackgroundColor(getThemedColor(Theme.key_chat_emojiPanelBackground));
                this.emojiTabsShadow.setBackgroundColor(getThemedColor(Theme.key_chat_emojiPanelShadowLine));
            } else {
                emojiTabsStrip.setBackground(null);
            }
        }
        EmojiColorPickerWindow emojiColorPickerWindow = this.colorPickerView;
        if (emojiColorPickerWindow != null) {
            emojiColorPickerWindow.updateColors();
        }
        for (int i2 = 0; i2 < 3; i2++) {
            if (i2 == 0) {
                searchField = this.stickersSearchField;
            } else if (i2 == 1) {
                searchField = this.emojiSearchField;
            } else {
                searchField = this.gifSearchField;
            }
            if (searchField != null) {
                if (this.shouldDrawBackground) {
                    searchField.backgroundView.setBackgroundColor(getThemedColor(Theme.key_chat_emojiPanelBackground));
                } else {
                    searchField.backgroundView.setBackground(null);
                }
                searchField.shadowView.setBackgroundColor(getThemedColor(Theme.key_chat_emojiPanelShadowLine));
                searchField.searchStateDrawable.setColor(this.glassDesign ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiSearchIcon));
                Theme.setDrawableColor(searchField.box.getBackground(), this.glassDesign ? getGlassIconColor(0.06f) : getThemedColor(Theme.key_chat_emojiSearchBackground));
                searchField.box.invalidate();
                searchField.searchEditText.setHintTextColor(this.glassDesign ? getGlassIconColor(0.45f) : getThemedColor(Theme.key_chat_emojiSearchIcon));
                searchField.searchEditText.setTextColor(this.glassDesign ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
            }
        }
        Paint paint = this.dotPaint;
        if (paint != null) {
            paint.setColor(getThemedColor(Theme.key_chat_emojiPanelNewTrending));
        }
        EmojiGridView emojiGridView = this.emojiGridView;
        if (emojiGridView != null) {
            emojiGridView.setGlowColor(getThemedColor(Theme.key_chat_emojiPanelBackground));
        }
        RecyclerListView recyclerListView = this.stickersGridView;
        if (recyclerListView != null) {
            recyclerListView.setGlowColor(getThemedColor(Theme.key_chat_emojiPanelBackground));
        }
        ScrollSlidingTabStrip scrollSlidingTabStrip = this.stickersTab;
        if (scrollSlidingTabStrip != null) {
            scrollSlidingTabStrip.setIndicatorColor(getThemedColor(Theme.key_chat_emojiPanelStickerPackSelectorLine));
            this.stickersTab.setUnderlineColor(getThemedColor(Theme.key_chat_emojiPanelShadowLine));
            boolean z = this.shouldDrawBackground;
            ScrollSlidingTabStrip scrollSlidingTabStrip2 = this.stickersTab;
            if (z) {
                scrollSlidingTabStrip2.setBackgroundColor(getThemedColor(Theme.key_chat_emojiPanelBackground));
            } else {
                scrollSlidingTabStrip2.setBackground(null);
            }
        }
        ScrollSlidingTabStrip scrollSlidingTabStrip3 = this.gifTabs;
        if (scrollSlidingTabStrip3 != null) {
            scrollSlidingTabStrip3.setIndicatorColor(getThemedColor(Theme.key_chat_emojiPanelStickerPackSelectorLine));
            this.gifTabs.setUnderlineColor(getThemedColor(Theme.key_chat_emojiPanelShadowLine));
            boolean z2 = this.shouldDrawBackground;
            ScrollSlidingTabStrip scrollSlidingTabStrip4 = this.gifTabs;
            if (z2) {
                scrollSlidingTabStrip4.setBackgroundColor(getThemedColor(Theme.key_chat_emojiPanelBackground));
            } else {
                scrollSlidingTabStrip4.setBackground(null);
            }
        }
        ImageView imageView = this.backspaceButton;
        if (imageView != null) {
            imageView.setColorFilter(new PorterDuffColorFilter(this.glassDesign ? getGlassIconColor(0.6f) : getThemedColor(Theme.key_chats_actionIcon), PorterDuff.Mode.MULTIPLY));
            if (this.emojiSearchField == null) {
                Drawable background2 = this.backspaceButton.getBackground();
                int i3 = Theme.key_chat_emojiPanelBackground;
                Theme.setSelectorDrawableColor(background2, getThemedColor(i3), false);
                Theme.setSelectorDrawableColor(this.backspaceButton.getBackground(), getThemedColor(i3), true);
            }
        }
        ImageView imageView2 = this.stickerSettingsButton;
        if (imageView2 != null) {
            imageView2.setColorFilter(new PorterDuffColorFilter(this.glassDesign ? getGlassIconColor(0.6f) : getThemedColor(Theme.key_chat_emojiPanelBackspace), PorterDuff.Mode.MULTIPLY));
        }
        ImageView imageView3 = this.searchButton;
        if (imageView3 != null) {
            imageView3.setColorFilter(new PorterDuffColorFilter(this.glassDesign ? getGlassIconColor(0.6f) : getThemedColor(Theme.key_chat_emojiPanelBackspace), PorterDuff.Mode.MULTIPLY));
        }
        TextView textView = this.mediaBanTooltip;
        if (textView != null) {
            ((ShapeDrawable) textView.getBackground()).getPaint().setColor(getThemedColor(Theme.key_chat_gifSaveHintBackground));
            this.mediaBanTooltip.setTextColor(getThemedColor(Theme.key_chat_gifSaveHintText));
        }
        GifAdapter gifAdapter = this.gifSearchAdapter;
        if (gifAdapter != null) {
            ImageView imageView4 = gifAdapter.progressEmptyView.imageView;
            int i4 = Theme.key_chat_emojiPanelEmptyText;
            imageView4.setColorFilter(new PorterDuffColorFilter(getThemedColor(i4), PorterDuff.Mode.MULTIPLY));
            this.gifSearchAdapter.progressEmptyView.textView.setTextColor(getThemedColor(i4));
            this.gifSearchAdapter.progressEmptyView.progressView.setProgressColor(getThemedColor(Theme.key_progressCircle));
        }
        this.animatedEmojiTextColorFilter = new PorterDuffColorFilter(getThemedColor(Theme.key_featuredStickers_addButton), PorterDuff.Mode.SRC_IN);
        int i5 = 0;
        while (true) {
            Drawable[] drawableArr = this.tabIcons;
            if (i5 >= drawableArr.length) {
                break;
            }
            Theme.setEmojiDrawableColor(drawableArr[i5], this.glassDesign ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiBottomPanelIcon), false);
            Theme.setEmojiDrawableColor(this.tabIcons[i5], this.glassDesign ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_chat_emojiPanelIconSelected), true);
            i5++;
        }
        EmojiTabsStrip emojiTabsStrip2 = this.emojiTabs;
        if (emojiTabsStrip2 != null) {
            emojiTabsStrip2.updateColors();
        }
        int i6 = 0;
        while (true) {
            Drawable[] drawableArr2 = this.stickerIcons;
            if (i6 >= drawableArr2.length) {
                break;
            }
            Theme.setEmojiDrawableColor(drawableArr2[i6], this.glassDesign ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiPanelIcon), false);
            Theme.setEmojiDrawableColor(this.stickerIcons[i6], this.glassDesign ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_chat_emojiPanelIconSelected), true);
            i6++;
        }
        int i7 = 0;
        while (true) {
            Drawable[] drawableArr3 = this.gifIcons;
            if (i7 >= drawableArr3.length) {
                break;
            }
            Theme.setEmojiDrawableColor(drawableArr3[i7], this.glassDesign ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiPanelIcon), false);
            Theme.setEmojiDrawableColor(this.gifIcons[i7], this.glassDesign ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_chat_emojiPanelIconSelected), true);
            i7++;
        }
        Drawable drawable = this.searchIconDrawable;
        if (drawable != null) {
            Theme.setEmojiDrawableColor(drawable, this.glassDesign ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiBottomPanelIcon), false);
            Theme.setEmojiDrawableColor(this.searchIconDrawable, this.glassDesign ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_chat_emojiPanelIconSelected), true);
        }
        Drawable drawable2 = this.searchIconDotDrawable;
        if (drawable2 != null) {
            Theme.setEmojiDrawableColor(drawable2, this.glassDesign ? getGlassIconColor(0.4f) : getThemedColor(Theme.key_chat_emojiPanelStickerPackSelectorLine), false);
            Theme.setEmojiDrawableColor(this.searchIconDotDrawable, this.glassDesign ? getGlassIconColor(0.8f) : getThemedColor(Theme.key_chat_emojiPanelStickerPackSelectorLine), true);
        }
        Paint paint2 = this.emojiLockPaint;
        if (paint2 != null) {
            paint2.setColor(getThemedColor(Theme.key_chat_emojiPanelStickerSetName));
            this.emojiLockPaint.setAlpha((int) (r0.getAlpha() * 0.5f));
        }
        Drawable drawable3 = this.emojiLockDrawable;
        if (drawable3 != null) {
            drawable3.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_chat_emojiPanelStickerSetName), PorterDuff.Mode.MULTIPLY));
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        this.isLayout = true;
        if (AndroidUtilities.isInMultiwindow || this.forseMultiwindowLayout) {
            if (this.currentBackgroundType != 1) {
                if (!this.customOutline) {
                    setOutlineProvider((ViewOutlineProvider) this.outlineProvider);
                    setClipToOutline(true);
                    setElevation(AndroidUtilities.m1036dp(2.0f));
                }
                setBackgroundResource(C2797R.drawable.smiles_popup);
                Drawable background = getBackground();
                int i3 = Theme.key_chat_emojiPanelBackground;
                background.setColorFilter(new PorterDuffColorFilter(getThemedColor(i3), PorterDuff.Mode.MULTIPLY));
                if (this.needEmojiSearch && this.shouldDrawBackground) {
                    this.bottomTabContainerBackground.setBackgroundColor(getThemedColor(i3));
                }
                this.currentBackgroundType = 1;
            }
        } else if (this.currentBackgroundType != 0) {
            if (!this.customOutline) {
                setOutlineProvider(null);
                setClipToOutline(false);
                setElevation(0.0f);
            }
            if (this.shouldDrawBackground) {
                int i4 = Theme.key_chat_emojiPanelBackground;
                setBackgroundColor(getThemedColor(i4));
                if (this.needEmojiSearch) {
                    this.bottomTabContainerBackground.setBackgroundColor(getThemedColor(i4));
                }
            }
            this.currentBackgroundType = 0;
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), TLObject.FLAG_30));
        this.isLayout = false;
        setTranslationY(getTranslationY());
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i3 - i;
        if (this.lastNotifyWidth != i5) {
            this.lastNotifyWidth = i5;
            reloadStickersAdapter();
        }
        super.onLayout(z, i, i2, i3, i4);
        updateBottomTabContainerPosition();
        updateStickerTabsPosition();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.bottomInset <= 0 || motionEvent.getY() < getMeasuredHeight() - this.bottomInset) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
    }

    public void reloadStickersAdapter() {
        StickersGridAdapter stickersGridAdapter = this.stickersGridAdapter;
        if (stickersGridAdapter != null) {
            stickersGridAdapter.notifyDataSetChanged();
        }
        StickersSearchGridAdapter stickersSearchGridAdapter = this.stickersSearchGridAdapter;
        if (stickersSearchGridAdapter != null) {
            stickersSearchGridAdapter.notifyDataSetChanged();
        }
        if (ContentPreviewViewer.getInstance().isVisible()) {
            ContentPreviewViewer.getInstance().close();
        }
        ContentPreviewViewer.getInstance().reset();
    }

    public void setDelegate(EmojiViewDelegate emojiViewDelegate) {
        this.delegate = emojiViewDelegate;
    }

    public void setDragListener(DragListener dragListener) {
        this.dragListener = dragListener;
    }

    public void setChatInfo(TLRPC.ChatFull chatFull) {
        this.info = chatFull;
        updateStickerTabs(false);
    }

    public void invalidateViews() {
        this.emojiGridView.invalidateViews();
    }

    public void setForseMultiwindowLayout(boolean z) {
        this.forseMultiwindowLayout = z;
    }

    public void onOpen(boolean z, boolean z2) {
        if (this.currentPage != 0 && this.stickersBanned) {
            this.currentPage = 0;
        }
        if (this.currentPage == 0 && this.emojiBanned) {
            this.currentPage = 1;
        }
        if (this.currentPage == 0 || z || this.currentTabs.size() == 1) {
            showBackspaceButton(true, false);
            showStickerSettingsButton(false, false);
            if (this.pager.getCurrentItem() != 0) {
                this.pager.setCurrentItem(0, !z);
            }
            if (z2) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda28
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onOpen$28();
                    }
                }, 350L);
            }
        } else {
            int i = this.currentPage;
            if (i == 1) {
                showBackspaceButton(false, false);
                showStickerSettingsButton(this.shouldDrawBackground || this.shouldDrawStickerSettings, false);
                if (this.pager.getCurrentItem() != 2) {
                    this.pager.setCurrentItem(2, false);
                }
                ScrollSlidingTabStrip scrollSlidingTabStrip = this.stickersTab;
                if (scrollSlidingTabStrip != null) {
                    this.firstTabUpdate = true;
                    int i2 = this.favTabNum;
                    if (i2 >= 0) {
                        scrollSlidingTabStrip.selectTab(i2);
                    } else {
                        int i3 = this.recentTabNum;
                        if (i3 >= 0) {
                            scrollSlidingTabStrip.selectTab(i3);
                        } else {
                            scrollSlidingTabStrip.selectTab(this.stickersTabOffset);
                        }
                    }
                    this.firstTabUpdate = false;
                    this.stickersLayoutManager.scrollToPositionWithOffset(0, 0);
                }
            } else if (i == 2) {
                showBackspaceButton(false, false);
                showStickerSettingsButton(false, false);
                if (this.pager.getCurrentItem() != 1) {
                    this.pager.setCurrentItem(1, false);
                }
                ScrollSlidingTabStrip scrollSlidingTabStrip2 = this.gifTabs;
                if (scrollSlidingTabStrip2 != null) {
                    scrollSlidingTabStrip2.selectTab(0);
                }
                SearchField searchField = this.gifSearchField;
                if (searchField != null && searchField.categoriesListView != null) {
                    this.gifSearchField.categoriesListView.selectCategory(this.gifSearchField.recent);
                }
            }
        }
        showBottomTab(true, true);
    }

    public /* synthetic */ void lambda$onOpen$28() {
        ArrayList<EmojiPack> emojipacks = getEmojipacks();
        for (int i = 0; i < emojipacks.size(); i++) {
            if (emojipacks.get(i).forGroup) {
                int i2 = this.emojiAdapter.sectionToPosition.get(EmojiData.dataColored.length + i);
                this.emojiGridView.stopScroll();
                updateEmojiTabsPosition(i2);
                scrollEmojisToPosition(i2, AndroidUtilities.m1036dp(-9.0f));
                checkEmojiTabY(null, 0);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.newEmojiSuggestionsAvailable);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.groupPackUpdated);
        if (this.stickersGridAdapter != null) {
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.stickersDidLoad);
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.recentDocumentsDidLoad);
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.featuredStickersDidLoad);
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.groupStickersDidLoad);
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAttachedToWindow$29();
                }
            });
        }
    }

    public /* synthetic */ void lambda$onAttachedToWindow$29() {
        updateStickerTabs(false);
        reloadStickersAdapter();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        boolean z = getVisibility() != i;
        super.setVisibility(i);
        if (z) {
            if (i != 8) {
                Emoji.sortEmoji();
                this.emojiAdapter.notifyDataSetChanged();
                NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.stickersDidLoad);
                if (this.stickersGridAdapter != null) {
                    NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.recentDocumentsDidLoad);
                    updateStickerTabs(false);
                    reloadStickersAdapter();
                }
                checkDocuments(true);
                checkDocuments(false);
                MediaDataController.getInstance(this.currentAccount).loadRecents(0, true, true, false);
                MediaDataController.getInstance(this.currentAccount).loadRecents(0, false, true, false);
                MediaDataController.getInstance(this.currentAccount).loadRecents(2, false, true, false);
            }
            ChooseStickerActionTracker chooseStickerActionTracker = this.chooseStickerActionTracker;
            if (chooseStickerActionTracker != null) {
                chooseStickerActionTracker.checkVisibility();
            }
        }
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void onDestroy() {
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.newEmojiSuggestionsAvailable);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.stickersDidLoad);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.groupPackUpdated);
        if (this.stickersGridAdapter != null) {
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.recentDocumentsDidLoad);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.featuredStickersDidLoad);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.groupStickersDidLoad);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EmojiColorPickerWindow emojiColorPickerWindow = this.colorPickerView;
        if (emojiColorPickerWindow != null && emojiColorPickerWindow.isShowing()) {
            this.colorPickerView.dismiss();
        }
        ContentPreviewViewer.getInstance().clearDelegate(this.contentPreviewViewerDelegate);
    }

    private void checkDocuments(boolean z) {
        if (z) {
            updateRecentGifs();
            return;
        }
        int size = this.recentStickers.size();
        int size2 = this.favouriteStickers.size();
        this.recentStickers = MediaDataController.getInstance(this.currentAccount).getRecentStickers(0, true);
        this.favouriteStickers = MediaDataController.getInstance(this.currentAccount).getRecentStickers(2);
        this.premiumStickers = new ArrayList<>();
        for (int i = 0; i < this.favouriteStickers.size(); i++) {
            TLRPC.Document document = this.favouriteStickers.get(i);
            int i2 = 0;
            while (true) {
                if (i2 < this.recentStickers.size()) {
                    TLRPC.Document document2 = this.recentStickers.get(i2);
                    if (document2.dc_id == document.dc_id && document2.f1253id == document.f1253id) {
                        this.recentStickers.remove(i2);
                        break;
                    }
                    i2++;
                }
            }
        }
        if (MessagesController.getInstance(this.currentAccount).premiumFeaturesBlocked()) {
            int i3 = 0;
            while (i3 < this.favouriteStickers.size()) {
                if (MessageObject.isPremiumSticker(this.favouriteStickers.get(i3))) {
                    this.favouriteStickers.remove(i3);
                    i3--;
                }
                i3++;
            }
            int i4 = 0;
            while (i4 < this.recentStickers.size()) {
                if (MessageObject.isPremiumSticker(this.recentStickers.get(i4))) {
                    this.recentStickers.remove(i4);
                    i4--;
                }
                i4++;
            }
        }
        if (size != this.recentStickers.size() || size2 != this.favouriteStickers.size()) {
            updateStickerTabs(false);
        }
        StickersGridAdapter stickersGridAdapter = this.stickersGridAdapter;
        if (stickersGridAdapter != null) {
            stickersGridAdapter.notifyDataSetChanged();
        }
        checkPanels();
    }

    public void updateRecentGifs() {
        GifAdapter gifAdapter;
        int size = this.recentGifs.size();
        long jCalcDocumentsHash = MediaDataController.calcDocumentsHash(this.recentGifs, Integer.MAX_VALUE);
        ArrayList<TLRPC.Document> recentGifs = MediaDataController.getInstance(this.currentAccount).getRecentGifs();
        this.recentGifs = recentGifs;
        long jCalcDocumentsHash2 = MediaDataController.calcDocumentsHash(recentGifs, Integer.MAX_VALUE);
        if ((this.gifTabs != null && size == 0 && !this.recentGifs.isEmpty()) || (size != 0 && this.recentGifs.isEmpty())) {
            updateGifTabs();
        }
        if ((size == this.recentGifs.size() && jCalcDocumentsHash == jCalcDocumentsHash2) || (gifAdapter = this.gifAdapter) == null) {
            return;
        }
        gifAdapter.notifyDataSetChanged();
    }

    public void setStickersBanned(boolean z, boolean z2, long j) {
        PagerSlidingTabStrip pagerSlidingTabStrip = this.typeTabs;
        if (pagerSlidingTabStrip == null) {
            return;
        }
        this.emojiBanned = z;
        this.stickersBanned = z2;
        if (z2 || z) {
            this.currentChatId = j;
        } else {
            this.currentChatId = 0L;
        }
        View tab = pagerSlidingTabStrip.getTab(z2 ? 2 : 0);
        if (tab != null) {
            tab.setAlpha(this.currentChatId != 0 ? 0.15f : 1.0f);
            long j2 = this.currentChatId;
            if (z2) {
                if (j2 == 0 || this.pager.getCurrentItem() == 0) {
                    return;
                }
                showBackspaceButton(true, true);
                showStickerSettingsButton(false, true);
                this.pager.setCurrentItem(0, false);
                return;
            }
            if (j2 == 0 || this.pager.getCurrentItem() == 1) {
                return;
            }
            showBackspaceButton(false, true);
            showStickerSettingsButton(false, true);
            this.pager.setCurrentItem(1, false);
        }
    }

    public void showStickerBanHint(boolean z, final boolean z2, final boolean z3) {
        TLRPC.TL_chatBannedRights tL_chatBannedRights;
        TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(this.currentChatId));
        if (chat == null) {
            return;
        }
        if (z) {
            if (!ChatObject.hasAdminRights(chat) && (tL_chatBannedRights = chat.default_banned_rights) != null && (tL_chatBannedRights.send_stickers || (z2 && tL_chatBannedRights.send_plain))) {
                BaseFragment baseFragment = this.fragment;
                if ((baseFragment instanceof ChatActivity) && ((ChatActivity) baseFragment).checkCanRemoveRestrictionsByBoosts()) {
                    return;
                }
                if (z2) {
                    this.mediaBanTooltip.setText(LocaleController.getString(C2797R.string.GlobalAttachEmojiRestricted));
                } else {
                    TextView textView = this.mediaBanTooltip;
                    if (z3) {
                        textView.setText(LocaleController.getString(C2797R.string.GlobalAttachGifRestricted));
                    } else {
                        textView.setText(LocaleController.getString(C2797R.string.GlobalAttachStickersRestricted));
                    }
                }
            } else {
                TLRPC.TL_chatBannedRights tL_chatBannedRights2 = chat.banned_rights;
                if (tL_chatBannedRights2 == null) {
                    return;
                }
                if (!AndroidUtilities.isBannedForever(tL_chatBannedRights2)) {
                    if (z2) {
                        this.mediaBanTooltip.setText(LocaleController.formatString("AttachPlainRestricted", C2797R.string.AttachPlainRestricted, LocaleController.formatDateForBan(chat.banned_rights.until_date)));
                    }
                    TextView textView2 = this.mediaBanTooltip;
                    if (z3) {
                        textView2.setText(LocaleController.formatString("AttachGifRestricted", C2797R.string.AttachGifRestricted, LocaleController.formatDateForBan(chat.banned_rights.until_date)));
                    } else {
                        textView2.setText(LocaleController.formatString("AttachStickersRestricted", C2797R.string.AttachStickersRestricted, LocaleController.formatDateForBan(chat.banned_rights.until_date)));
                    }
                } else if (z2) {
                    this.mediaBanTooltip.setText(LocaleController.getString(C2797R.string.AttachPlainRestrictedForever));
                } else {
                    TextView textView3 = this.mediaBanTooltip;
                    if (z3) {
                        textView3.setText(LocaleController.getString(C2797R.string.AttachGifRestrictedForever));
                    } else {
                        textView3.setText(LocaleController.getString(C2797R.string.AttachStickersRestrictedForever));
                    }
                }
            }
            this.mediaBanTooltip.setVisibility(0);
        }
        AnimatorSet animatorSet = this.showStickersBanAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.showStickersBanAnimator = null;
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.showStickersBanAnimator = animatorSet2;
        TextView textView4 = this.mediaBanTooltip;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(textView4, (Property<TextView, Float>) View.ALPHA, z ? textView4.getAlpha() : 1.0f, z ? 1.0f : 0.0f);
        TextView textView5 = this.mediaBanTooltip;
        animatorSet2.playTogether(objectAnimatorOfFloat, ObjectAnimator.ofFloat(textView5, (Property<TextView, Float>) View.TRANSLATION_Y, z ? AndroidUtilities.m1036dp(12.0f) : textView5.getTranslationY(), z ? 0.0f : AndroidUtilities.m1036dp(12.0f)));
        Runnable runnable = this.hideStickersBan;
        if (runnable != null) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
        }
        if (z) {
            Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda35
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showStickerBanHint$30(z2, z3);
                }
            };
            this.hideStickersBan = runnable2;
            AndroidUtilities.runOnUIThread(runnable2, 3500L);
        }
        this.showStickersBanAnimator.setDuration(320L);
        this.showStickersBanAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        this.showStickersBanAnimator.start();
    }

    public /* synthetic */ void lambda$showStickerBanHint$30(boolean z, boolean z2) {
        showStickerBanHint(false, z, z2);
    }

    private void updateVisibleTrendingSets() {
        boolean z;
        RecyclerListView recyclerListView = this.stickersGridView;
        if (recyclerListView == null) {
            return;
        }
        try {
            int childCount = recyclerListView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.stickersGridView.getChildAt(i);
                if ((childAt instanceof FeaturedStickerSetInfoCell) && ((RecyclerListView.Holder) this.stickersGridView.getChildViewHolder(childAt)) != null) {
                    FeaturedStickerSetInfoCell featuredStickerSetInfoCell = (FeaturedStickerSetInfoCell) childAt;
                    ArrayList<Long> unreadStickerSets = MediaDataController.getInstance(this.currentAccount).getUnreadStickerSets();
                    TLRPC.StickerSetCovered stickerSet = featuredStickerSetInfoCell.getStickerSet();
                    boolean z2 = unreadStickerSets != null && unreadStickerSets.contains(Long.valueOf(stickerSet.set.f1280id));
                    int i2 = 0;
                    while (true) {
                        TLRPC.StickerSetCovered[] stickerSetCoveredArr = this.primaryInstallingStickerSets;
                        if (i2 >= stickerSetCoveredArr.length) {
                            z = false;
                            break;
                        }
                        TLRPC.StickerSetCovered stickerSetCovered = stickerSetCoveredArr[i2];
                        if (stickerSetCovered != null && stickerSetCovered.set.f1280id == stickerSet.set.f1280id) {
                            z = true;
                            break;
                        }
                        i2++;
                    }
                    featuredStickerSetInfoCell.setStickerSet(stickerSet, z2, true, 0, 0, z);
                    if (z2) {
                        MediaDataController.getInstance(this.currentAccount).markFeaturedStickersByIdAsRead(false, stickerSet.set.f1280id);
                    }
                    boolean z3 = this.installingStickerSets.indexOfKey(stickerSet.set.f1280id) >= 0;
                    boolean z4 = this.removingStickerSets.indexOfKey(stickerSet.set.f1280id) >= 0;
                    if (z3 || z4) {
                        if (z3 && featuredStickerSetInfoCell.isInstalled()) {
                            this.installingStickerSets.remove(stickerSet.set.f1280id);
                            z3 = false;
                        } else if (z4 && !featuredStickerSetInfoCell.isInstalled()) {
                            this.removingStickerSets.remove(stickerSet.set.f1280id);
                        }
                    }
                    featuredStickerSetInfoCell.setAddDrawProgress(!z && z3, true);
                }
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    public boolean areThereAnyStickers() {
        StickersGridAdapter stickersGridAdapter = this.stickersGridAdapter;
        return stickersGridAdapter != null && stickersGridAdapter.getItemCount() > 0;
    }

    public /* synthetic */ void lambda$new$31() {
        EmojiGridAdapter emojiGridAdapter = this.emojiAdapter;
        if (emojiGridAdapter != null) {
            emojiGridAdapter.notifyDataSetChanged(true);
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        Utilities.Callback<TLRPC.TL_messages_stickerSet> callbackRemove;
        TLRPC.StickerSet stickerSet;
        if (i == NotificationCenter.stickersDidLoad) {
            if (((Integer) objArr[0]).intValue() == 0) {
                if (this.stickersGridAdapter != null) {
                    updateStickerTabs(((Boolean) objArr[1]).booleanValue());
                    updateVisibleTrendingSets();
                    reloadStickersAdapter();
                    checkPanels();
                    return;
                }
                return;
            }
            if (((Integer) objArr[0]).intValue() == 5) {
                if (((Boolean) objArr[1]).booleanValue()) {
                    AndroidUtilities.cancelRunOnUIThread(this.updateStickersLoadedDelayed);
                    AndroidUtilities.runOnUIThread(this.updateStickersLoadedDelayed, 100L);
                    return;
                } else {
                    this.emojiAdapter.notifyDataSetChanged(false);
                    return;
                }
            }
            return;
        }
        if (i == NotificationCenter.groupPackUpdated) {
            long jLongValue = ((Long) objArr[0]).longValue();
            boolean zBooleanValue = ((Boolean) objArr[1]).booleanValue();
            TLRPC.ChatFull chatFull = this.info;
            if (chatFull != null && chatFull.f1246id == jLongValue && zBooleanValue) {
                this.emojiAdapter.notifyDataSetChanged(true);
                return;
            }
            return;
        }
        if (i == NotificationCenter.recentDocumentsDidLoad) {
            boolean zBooleanValue2 = ((Boolean) objArr[0]).booleanValue();
            int iIntValue = ((Integer) objArr[1]).intValue();
            if (zBooleanValue2 || iIntValue == 0 || iIntValue == 2) {
                checkDocuments(zBooleanValue2);
                return;
            }
            return;
        }
        if (i == NotificationCenter.featuredStickersDidLoad) {
            updateVisibleTrendingSets();
            PagerSlidingTabStrip pagerSlidingTabStrip = this.typeTabs;
            if (pagerSlidingTabStrip != null) {
                int childCount = pagerSlidingTabStrip.getChildCount();
                for (int i3 = 0; i3 < childCount; i3++) {
                    this.typeTabs.getChildAt(i3).invalidate();
                }
            }
            updateStickerTabs(false);
            return;
        }
        if (i == NotificationCenter.featuredEmojiDidLoad) {
            EmojiGridAdapter emojiGridAdapter = this.emojiAdapter;
            if (emojiGridAdapter != null) {
                emojiGridAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (i == NotificationCenter.groupStickersDidLoad) {
            Long l = (Long) objArr[0];
            long jLongValue2 = l.longValue();
            TLRPC.TL_messages_stickerSet tL_messages_stickerSet = objArr.length > 1 ? (TLRPC.TL_messages_stickerSet) objArr[1] : null;
            if (tL_messages_stickerSet != null) {
                StickersSearchGridAdapter stickersSearchGridAdapter = this.stickersSearchGridAdapter;
                if (stickersSearchGridAdapter != null && stickersSearchGridAdapter.selectedPackId == jLongValue2 && this.stickersSearchGridAdapter.selectedPackStickers.size() < tL_messages_stickerSet.documents.size()) {
                    this.stickersSearchGridAdapter.selectedPackStickers = tL_messages_stickerSet.documents;
                    this.stickersSearchGridAdapter.notifyDataSetChanged();
                }
                EmojiSearchAdapter emojiSearchAdapter = this.emojiSearchAdapter;
                if (emojiSearchAdapter != null && emojiSearchAdapter.selectedPackId == jLongValue2 && this.emojiSearchAdapter.selectedPackStickers.size() < tL_messages_stickerSet.documents.size()) {
                    this.emojiSearchAdapter.selectedPackStickers = tL_messages_stickerSet.documents;
                    this.emojiSearchAdapter.notifyDataSetChanged();
                }
            }
            TLRPC.ChatFull chatFull2 = this.info;
            if (chatFull2 != null && (stickerSet = chatFull2.stickerset) != null && stickerSet.f1280id == jLongValue2) {
                updateStickerTabs(false);
            }
            if (this.toInstall.containsKey(l) && objArr.length >= 2 && this.toInstall.get(l) != null && tL_messages_stickerSet != null && (callbackRemove = this.toInstall.remove(l)) != null) {
                callbackRemove.run(tL_messages_stickerSet);
            }
            AndroidUtilities.cancelRunOnUIThread(this.updateStickersLoadedDelayed);
            AndroidUtilities.runOnUIThread(this.updateStickersLoadedDelayed, 100L);
            return;
        }
        if (i == NotificationCenter.emojiLoaded) {
            RecyclerListView recyclerListView = this.stickersGridView;
            if (recyclerListView != null) {
                int childCount2 = recyclerListView.getChildCount();
                for (int i4 = 0; i4 < childCount2; i4++) {
                    View childAt = this.stickersGridView.getChildAt(i4);
                    if ((childAt instanceof StickerSetNameCell) || (childAt instanceof StickerEmojiCell)) {
                        childAt.invalidate();
                    }
                }
            }
            EmojiGridView emojiGridView = this.emojiGridView;
            if (emojiGridView != null) {
                emojiGridView.invalidate();
                int childCount3 = this.emojiGridView.getChildCount();
                for (int i5 = 0; i5 < childCount3; i5++) {
                    View childAt2 = this.emojiGridView.getChildAt(i5);
                    if (childAt2 instanceof ImageViewEmoji) {
                        childAt2.invalidate();
                    }
                }
            }
            EmojiColorPickerWindow emojiColorPickerWindow = this.colorPickerView;
            if (emojiColorPickerWindow != null) {
                emojiColorPickerWindow.pickerView.invalidate();
            }
            ScrollSlidingTabStrip scrollSlidingTabStrip = this.gifTabs;
            if (scrollSlidingTabStrip != null) {
                scrollSlidingTabStrip.invalidateTabs();
                return;
            }
            return;
        }
        if (i == NotificationCenter.newEmojiSuggestionsAvailable) {
            if (this.emojiGridView == null || !this.needEmojiSearch) {
                return;
            }
            if ((this.emojiSearchField.searchStateDrawable.getIconState() == 2 || this.emojiGridView.getAdapter() == this.emojiSearchAdapter) && !TextUtils.isEmpty(this.emojiSearchAdapter.lastSearchEmojiString)) {
                EmojiSearchAdapter emojiSearchAdapter2 = this.emojiSearchAdapter;
                emojiSearchAdapter2.search(emojiSearchAdapter2.lastSearchEmojiString);
                return;
            }
            return;
        }
        if (i == NotificationCenter.currentUserPremiumStatusChanged) {
            EmojiGridAdapter emojiGridAdapter2 = this.emojiAdapter;
            if (emojiGridAdapter2 != null) {
                emojiGridAdapter2.notifyDataSetChanged();
            }
            updateEmojiHeaders();
            updateStickerTabs(false);
        }
    }

    public int getThemedColor(int i) {
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        if (resourcesProvider != null) {
            return resourcesProvider.getColor(i);
        }
        return Theme.getColor(i);
    }

    public class TrendingAdapter extends RecyclerListView.SelectionAdapter {
        private boolean emoji;

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            return 0;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return true;
        }

        public TrendingAdapter(boolean z) {
            this.emoji = z;
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$TrendingAdapter$1 */
        public class C43461 extends BackupImageView {
            public C43461(Context context) {
                super(context);
            }

            @Override // org.telegram.p035ui.Components.BackupImageView, android.view.View
            public void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                if (TrendingAdapter.this.emoji) {
                    return;
                }
                if (!MediaDataController.getInstance(EmojiView.this.currentAccount).isStickerPackUnread(TrendingAdapter.this.emoji, ((TLRPC.StickerSetCovered) getTag()).set.f1280id) || EmojiView.this.dotPaint == null) {
                    return;
                }
                canvas.drawCircle(canvas.getWidth() - AndroidUtilities.m1036dp(8.0f), AndroidUtilities.m1036dp(14.0f), AndroidUtilities.m1036dp(3.0f), EmojiView.this.dotPaint);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            C43461 c43461 = new BackupImageView(EmojiView.this.getContext()) { // from class: org.telegram.ui.Components.EmojiView.TrendingAdapter.1
                public C43461(Context context) {
                    super(context);
                }

                @Override // org.telegram.p035ui.Components.BackupImageView, android.view.View
                public void onDraw(Canvas canvas) {
                    super.onDraw(canvas);
                    if (TrendingAdapter.this.emoji) {
                        return;
                    }
                    if (!MediaDataController.getInstance(EmojiView.this.currentAccount).isStickerPackUnread(TrendingAdapter.this.emoji, ((TLRPC.StickerSetCovered) getTag()).set.f1280id) || EmojiView.this.dotPaint == null) {
                        return;
                    }
                    canvas.drawCircle(canvas.getWidth() - AndroidUtilities.m1036dp(8.0f), AndroidUtilities.m1036dp(14.0f), AndroidUtilities.m1036dp(3.0f), EmojiView.this.dotPaint);
                }
            };
            c43461.setSize(AndroidUtilities.m1036dp(24.0f), AndroidUtilities.m1036dp(24.0f));
            c43461.setLayerNum(1);
            c43461.setAspectFit(true);
            c43461.setLayoutParams(new RecyclerView.LayoutParams(AndroidUtilities.m1036dp(34.0f), AndroidUtilities.m1036dp(34.0f)));
            return new RecyclerListView.Holder(c43461);
        }

        /* JADX WARN: Removed duplicated region for block: B:123:0x007e  */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r9, int r10) {
            /*
                Method dump skipped, instruction units count: 319
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.TrendingAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            boolean z = this.emoji;
            EmojiView emojiView = EmojiView.this;
            return (z ? emojiView.featuredEmojiSets : emojiView.featuredStickerSets).size();
        }
    }

    public class TrendingListView extends RecyclerListView {
        public TrendingListView(Context context, RecyclerView.Adapter adapter) {
            super(context);
            setNestedScrollingEnabled(true);
            setSelectorRadius(AndroidUtilities.m1036dp(8.0f));
            setSelectorDrawableColor(getThemedColor(Theme.key_listSelector));
            setTag(9);
            setItemAnimator(null);
            setLayoutAnimation(null);
            C43471 c43471 = new LinearLayoutManager(context) { // from class: org.telegram.ui.Components.EmojiView.TrendingListView.1
                final /* synthetic */ EmojiView val$this$0;

                @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public boolean supportsPredictiveItemAnimations() {
                    return false;
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C43471(Context context2, EmojiView emojiView) {
                    super(context2);
                    emojiView = emojiView;
                }
            };
            c43471.setOrientation(0);
            setLayoutManager(c43471);
            setAdapter(adapter);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$TrendingListView$1 */
        public class C43471 extends LinearLayoutManager {
            final /* synthetic */ EmojiView val$this$0;

            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C43471(Context context2, EmojiView emojiView) {
                super(context2);
                emojiView = emojiView;
            }
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (getParent() != null && getParent().getParent() != null) {
                getParent().getParent().requestDisallowInterceptTouchEvent(canScrollHorizontally(-1) || canScrollHorizontally(1));
                EmojiView.this.pager.requestDisallowInterceptTouchEvent(true);
            }
            return super.onInterceptTouchEvent(motionEvent);
        }
    }

    public void setDisableStickerEditor() {
        this.disableStickerEditor = true;
    }

    public class StickersGridAdapter extends RecyclerListView.SelectionAdapter {
        private Context context;
        private int stickersPerRow;
        private int totalItems;
        private SparseArray<Object> rowStartPack = new SparseArray<>();
        private HashMap<Object, Integer> packStartPosition = new HashMap<>();
        private SparseArray<Object> cache = new SparseArray<>();
        private SparseArray<Object> cacheParents = new SparseArray<>();
        private SparseIntArray positionToRow = new SparseIntArray();

        public StickersGridAdapter(Context context) {
            this.context = context;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.itemView instanceof RecyclerListView;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            int i = this.totalItems;
            if (i != 0) {
                return i + 1;
            }
            return 0;
        }

        public int getPositionForPack(Object obj) {
            Integer num = this.packStartPosition.get(obj);
            if (num == null) {
                return -1;
            }
            return num.intValue();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == 0) {
                return 4;
            }
            Object obj = this.cache.get(i);
            if (obj == null) {
                return 1;
            }
            if (obj instanceof TLRPC.Document) {
                return obj instanceof TLRPC.TL_documentEmpty ? 7 : 0;
            }
            if (!(obj instanceof String)) {
                return 2;
            }
            if ("trend1".equals(obj)) {
                return 5;
            }
            return "trend2".equals(obj) ? 6 : 3;
        }

        public int getTabForPosition(int i) {
            int iIndexOf;
            int i2;
            Object obj = this.cache.get(i);
            if ("search".equals(obj) || "trend1".equals(obj) || "trend2".equals(obj)) {
                int i3 = EmojiView.this.favTabNum;
                EmojiView emojiView = EmojiView.this;
                if (i3 >= 0) {
                    return emojiView.favTabNum;
                }
                if (emojiView.recentTabNum >= 0) {
                    return EmojiView.this.recentTabNum;
                }
                return 0;
            }
            if (i == 0) {
                i = 1;
            }
            if (this.stickersPerRow == 0) {
                int measuredWidth = EmojiView.this.getMeasuredWidth();
                if (measuredWidth == 0) {
                    measuredWidth = AndroidUtilities.displaySize.x;
                }
                this.stickersPerRow = measuredWidth / AndroidUtilities.m1036dp(72.0f);
            }
            int i4 = this.positionToRow.get(i, Integer.MIN_VALUE);
            if (i4 == Integer.MIN_VALUE) {
                iIndexOf = EmojiView.this.stickerSets.size() - 1;
                i2 = EmojiView.this.stickersTabOffset;
            } else {
                Object obj2 = this.rowStartPack.get(i4);
                if (obj2 instanceof String) {
                    if ("premium".equals(obj2)) {
                        return EmojiView.this.premiumTabNum;
                    }
                    boolean zEquals = "recent".equals(obj2);
                    EmojiView emojiView2 = EmojiView.this;
                    if (zEquals) {
                        return emojiView2.recentTabNum;
                    }
                    return emojiView2.favTabNum;
                }
                iIndexOf = EmojiView.this.stickerSets.indexOf((TLRPC.TL_messages_stickerSet) obj2);
                i2 = EmojiView.this.stickersTabOffset;
            }
            return iIndexOf + i2;
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$StickersGridAdapter$1 */
        public class C43391 extends StickerEmojiCell {
            public C43391(Context context, boolean z, Theme.ResourcesProvider resourcesProvider) {
                super(context, z, resourcesProvider);
            }

            @Override // android.widget.FrameLayout, android.view.View
            public void onMeasure(int i, int i2) {
                super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(82.0f), TLObject.FLAG_30));
            }
        }

        public /* synthetic */ void lambda$onCreateViewHolder$1(StickerSetNameCell stickerSetNameCell, View view) {
            RecyclerView.ViewHolder childViewHolder;
            if (EmojiView.this.stickersGridView.indexOfChild(stickerSetNameCell) == -1 || (childViewHolder = EmojiView.this.stickersGridView.getChildViewHolder(stickerSetNameCell)) == null) {
                return;
            }
            if (childViewHolder.getAdapterPosition() == EmojiView.this.groupStickerPackPosition) {
                TLRPC.TL_messages_stickerSet tL_messages_stickerSet = EmojiView.this.groupStickerSet;
                EmojiView emojiView = EmojiView.this;
                if (tL_messages_stickerSet != null) {
                    if (emojiView.delegate != null) {
                        EmojiView.this.delegate.onStickersGroupClick(EmojiView.this.info.f1246id);
                        return;
                    }
                    return;
                }
                MessagesController.getEmojiSettings(emojiView.currentAccount).edit().putLong("group_hide_stickers_" + EmojiView.this.info.f1246id, EmojiView.this.info.stickerset != null ? EmojiView.this.info.stickerset.f1280id : 0L).apply();
                EmojiView.this.updateStickerTabs(false);
                if (EmojiView.this.stickersGridAdapter != null) {
                    EmojiView.this.stickersGridAdapter.notifyDataSetChanged();
                    return;
                }
                return;
            }
            if (this.cache.get(childViewHolder.getAdapterPosition()) == EmojiView.this.recentStickers) {
                AlertDialog alertDialogCreate = new AlertDialog.Builder(this.context, EmojiView.this.resourcesProvider).setTitle(LocaleController.getString(C2797R.string.ClearRecentStickersAlertTitle)).setMessage(LocaleController.getString(C2797R.string.ClearRecentStickersAlertMessage)).setPositiveButton(LocaleController.getString(C2797R.string.ClearButton), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersGridAdapter$$ExternalSyntheticLambda7
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i) {
                        this.f$0.lambda$onCreateViewHolder$0(alertDialog, i);
                    }
                }).setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null).create();
                alertDialogCreate.show();
                TextView textView = (TextView) alertDialogCreate.getButton(-1);
                if (textView != null) {
                    textView.setTextColor(EmojiView.this.getThemedColor(Theme.key_text_RedBold));
                }
            }
        }

        public /* synthetic */ void lambda$onCreateViewHolder$0(AlertDialog alertDialog, int i) {
            MediaDataController.getInstance(EmojiView.this.currentAccount).clearRecentStickers();
        }

        public /* synthetic */ void lambda$onCreateViewHolder$2(View view) {
            if (EmojiView.this.delegate != null) {
                EmojiView.this.delegate.onStickersGroupClick(EmojiView.this.info.f1246id);
            }
        }

        public /* synthetic */ void lambda$onCreateViewHolder$3(View view) {
            ArrayList<TLRPC.StickerSetCovered> featuredStickerSets = MediaDataController.getInstance(EmojiView.this.currentAccount).getFeaturedStickerSets();
            if (featuredStickerSets.isEmpty()) {
                return;
            }
            MessagesController.getEmojiSettings(EmojiView.this.currentAccount).edit().putLong("featured_hidden", featuredStickerSets.get(0).set.f1280id).apply();
            if (EmojiView.this.stickersGridAdapter != null) {
                EmojiView.this.stickersGridAdapter.notifyItemRangeRemoved(1, 2);
            }
            EmojiView.this.updateStickerTabs(false);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$StickersGridAdapter$2 */
        public class C43402 extends RecyclerView.ItemDecoration {
            public C43402() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                rect.right = AndroidUtilities.m1036dp(2.0f);
            }
        }

        public /* synthetic */ void lambda$onCreateViewHolder$4(View view, int i) {
            EmojiView.this.openTrendingStickers((TLRPC.StickerSetCovered) view.getTag());
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v16, types: [android.view.View] */
        /* JADX WARN: Type inference failed for: r0v17 */
        /* JADX WARN: Type inference failed for: r0v5 */
        /* JADX WARN: Type inference failed for: r0v7 */
        /* JADX WARN: Type inference failed for: r1v10 */
        /* JADX WARN: Type inference failed for: r1v11 */
        /* JADX WARN: Type inference failed for: r1v12 */
        /* JADX WARN: Type inference failed for: r1v5 */
        /* JADX WARN: Type inference failed for: r1v6, types: [android.view.ViewGroup, android.widget.FrameLayout] */
        /* JADX WARN: Type inference failed for: r1v8 */
        /* JADX WARN: Type inference failed for: r1v9 */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @SuppressLint({"NotifyDataSetChanged"})
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            Object obj;
            ?? r0;
            ?? c43391;
            switch (i) {
                case 0:
                    c43391 = new StickerEmojiCell(this.context, true, EmojiView.this.resourcesProvider) { // from class: org.telegram.ui.Components.EmojiView.StickersGridAdapter.1
                        public C43391(Context context, boolean z, Theme.ResourcesProvider resourcesProvider) {
                            super(context, z, resourcesProvider);
                        }

                        @Override // android.widget.FrameLayout, android.view.View
                        public void onMeasure(int i2, int i22) {
                            super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(82.0f), TLObject.FLAG_30));
                        }
                    };
                    r0 = c43391;
                    break;
                case 1:
                    c43391 = new EmptyCell(this.context);
                    r0 = c43391;
                    break;
                case 2:
                    final StickerSetNameCell stickerSetNameCell = new StickerSetNameCell(this.context, false, EmojiView.this.resourcesProvider, EmojiView.this.glassDesign);
                    stickerSetNameCell.setOnIconClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersGridAdapter$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$onCreateViewHolder$1(stickerSetNameCell, view);
                        }
                    });
                    c43391 = stickerSetNameCell;
                    r0 = c43391;
                    break;
                case 3:
                    StickerSetGroupInfoCell stickerSetGroupInfoCell = new StickerSetGroupInfoCell(this.context);
                    stickerSetGroupInfoCell.setAddOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersGridAdapter$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$onCreateViewHolder$2(view);
                        }
                    });
                    stickerSetGroupInfoCell.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                    obj = stickerSetGroupInfoCell;
                    r0 = obj;
                    break;
                case 4:
                    View view = new View(this.context);
                    view.setLayoutParams(new RecyclerView.LayoutParams(-1, EmojiView.this.searchFieldHeight));
                    obj = view;
                    r0 = obj;
                    break;
                case 5:
                    StickerSetNameCell stickerSetNameCell2 = new StickerSetNameCell(this.context, false, EmojiView.this.resourcesProvider, EmojiView.this.glassDesign);
                    stickerSetNameCell2.setOnIconClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersGridAdapter$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            this.f$0.lambda$onCreateViewHolder$3(view2);
                        }
                    });
                    c43391 = stickerSetNameCell2;
                    r0 = c43391;
                    break;
                case 6:
                    EmojiView emojiView = EmojiView.this;
                    Context context = this.context;
                    TrendingAdapter trendingAdapter = emojiView.new TrendingAdapter(false);
                    emojiView.trendingAdapter = trendingAdapter;
                    TrendingListView trendingListView = emojiView.new TrendingListView(context, trendingAdapter);
                    trendingListView.setPadding(AndroidUtilities.m1036dp(8.0f), AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(8.0f), 0);
                    trendingListView.setClipToPadding(false);
                    trendingListView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: org.telegram.ui.Components.EmojiView.StickersGridAdapter.2
                        public C43402() {
                        }

                        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                        public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
                            rect.right = AndroidUtilities.m1036dp(2.0f);
                        }
                    });
                    trendingListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersGridAdapter$$ExternalSyntheticLambda3
                        @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                        public final void onItemClick(View view2, int i2) {
                            this.f$0.lambda$onCreateViewHolder$4(view2, i2);
                        }
                    });
                    trendingListView.setLayoutParams(new RecyclerView.LayoutParams(-1, AndroidUtilities.m1036dp(52.0f)));
                    obj = trendingListView;
                    r0 = obj;
                    break;
                case 7:
                    ?? frameLayout = new FrameLayout(this.context);
                    LinearLayout linearLayout = new LinearLayout(this.context);
                    linearLayout.setOrientation(1);
                    linearLayout.setGravity(17);
                    int iM1036dp = AndroidUtilities.m1036dp(13.0f);
                    EmojiView emojiView2 = EmojiView.this;
                    int i2 = Theme.key_chat_emojiPanelIcon;
                    linearLayout.setBackground(Theme.createRoundRectDrawable(iM1036dp, Theme.multAlpha(emojiView2.getThemedColor(i2), 0.12f)));
                    ScaleStateListAnimator.apply(linearLayout, 0.1f, 1.5f);
                    linearLayout.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersGridAdapter$$ExternalSyntheticLambda4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            this.f$0.lambda$onCreateViewHolder$5(view2);
                        }
                    });
                    ImageView imageView = new ImageView(this.context);
                    imageView.setImageResource(C2797R.drawable.menu_sticker_add);
                    imageView.setColorFilter(new PorterDuffColorFilter(EmojiView.this.getThemedColor(i2), PorterDuff.Mode.SRC_IN));
                    linearLayout.addView(imageView, LayoutHelper.createLinear(24, 24, 17, 0, 0, 0, 0));
                    TextView textView = new TextView(this.context);
                    textView.setGravity(17);
                    textView.setTextColor(EmojiView.this.getThemedColor(i2));
                    textView.setTextSize(1, 11.0f);
                    textView.setTypeface(AndroidUtilities.bold());
                    textView.setText(LocaleController.getString(C2797R.string.Create));
                    linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2, 17, 0, 3, 0, 0));
                    frameLayout.addView(linearLayout, LayoutHelper.createFrame(-1, -1.0f, 119, 8.0f, 8.0f, 8.0f, 8.0f));
                    c43391 = frameLayout;
                    r0 = c43391;
                    break;
                default:
                    r0 = 0;
                    break;
            }
            return new RecyclerListView.Holder(r0);
        }

        public /* synthetic */ void lambda$onCreateViewHolder$5(View view) {
            if (EmojiView.this.fragment instanceof ChatActivity) {
                ((ChatActivity) EmojiView.this.fragment).openAttachMenuForCreatingSticker();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            int itemViewType = viewHolder.getItemViewType();
            int i2 = 0;
            if (itemViewType == 0) {
                TLRPC.Document document = (TLRPC.Document) this.cache.get(i);
                StickerEmojiCell stickerEmojiCell = (StickerEmojiCell) viewHolder.itemView;
                stickerEmojiCell.setSticker(document, this.cacheParents.get(i), false);
                stickerEmojiCell.setRecent(EmojiView.this.recentStickers.contains(document));
                return;
            }
            ArrayList<TLRPC.Document> arrayList = null;
            if (itemViewType == 1) {
                EmptyCell emptyCell = (EmptyCell) viewHolder.itemView;
                if (i == this.totalItems) {
                    int i3 = this.positionToRow.get(i - 1, Integer.MIN_VALUE);
                    if (i3 == Integer.MIN_VALUE) {
                        emptyCell.setHeight(1);
                        return;
                    }
                    Object obj = this.rowStartPack.get(i3);
                    if (obj instanceof TLRPC.TL_messages_stickerSet) {
                        arrayList = ((TLRPC.TL_messages_stickerSet) obj).documents;
                    } else if (obj instanceof String) {
                        boolean zEquals = "recent".equals(obj);
                        EmojiView emojiView = EmojiView.this;
                        if (zEquals) {
                            arrayList = emojiView.recentStickers;
                        } else {
                            arrayList = emojiView.favouriteStickers;
                        }
                    }
                    if (arrayList == null) {
                        emptyCell.setHeight(1);
                        return;
                    } else if (arrayList.isEmpty()) {
                        emptyCell.setHeight(AndroidUtilities.m1036dp(8.0f));
                        return;
                    } else {
                        int height = EmojiView.this.pager.getHeight() - (((int) Math.ceil(arrayList.size() / this.stickersPerRow)) * AndroidUtilities.m1036dp(82.0f));
                        emptyCell.setHeight(height > 0 ? height : 1);
                        return;
                    }
                }
                emptyCell.setHeight(AndroidUtilities.m1036dp(82.0f));
                return;
            }
            if (itemViewType != 2) {
                if (itemViewType == 3) {
                    ((StickerSetGroupInfoCell) viewHolder.itemView).setIsLast(i == this.totalItems - 1);
                    return;
                } else {
                    if (itemViewType != 5) {
                        return;
                    }
                    ((StickerSetNameCell) viewHolder.itemView).setText(LocaleController.getString(MediaDataController.getInstance(EmojiView.this.currentAccount).loadFeaturedPremium ? C2797R.string.FeaturedStickersPremium : C2797R.string.FeaturedStickers), C2797R.drawable.msg_close, LocaleController.getString(C2797R.string.AccDescrCloseTrendingStickers));
                    return;
                }
            }
            StickerSetNameCell stickerSetNameCell = (StickerSetNameCell) viewHolder.itemView;
            stickerSetNameCell.setHeaderOnClick(null);
            if (i == EmojiView.this.groupStickerPackPosition) {
                if (!EmojiView.this.groupStickersHidden || EmojiView.this.groupStickerSet != null) {
                    i2 = EmojiView.this.groupStickerSet != null ? C2797R.drawable.msg_mini_customize : C2797R.drawable.msg_close;
                }
                TLRPC.Chat chat = EmojiView.this.info != null ? MessagesController.getInstance(EmojiView.this.currentAccount).getChat(Long.valueOf(EmojiView.this.info.f1246id)) : null;
                stickerSetNameCell.setText(LocaleController.formatString("CurrentGroupStickers", C2797R.string.CurrentGroupStickers, chat != null ? chat.title : "Group Stickers"), i2);
                return;
            }
            Object obj2 = this.cache.get(i);
            if (obj2 instanceof TLRPC.TL_messages_stickerSet) {
                final TLRPC.TL_messages_stickerSet tL_messages_stickerSet = (TLRPC.TL_messages_stickerSet) obj2;
                TLRPC.StickerSet stickerSet = tL_messages_stickerSet.set;
                if (stickerSet != null) {
                    stickerSetNameCell.setText(stickerSet.title, 0);
                    if (tL_messages_stickerSet.set.creator && !EmojiView.this.disableStickerEditor) {
                        stickerSetNameCell.setEdit(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersGridAdapter$$ExternalSyntheticLambda5
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f$0.lambda$onBindViewHolder$6(tL_messages_stickerSet, view);
                            }
                        });
                    }
                    stickerSetNameCell.setHeaderOnClick(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersGridAdapter$$ExternalSyntheticLambda6
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$onBindViewHolder$7(tL_messages_stickerSet, view);
                        }
                    });
                    return;
                }
                return;
            }
            if (obj2 == EmojiView.this.recentStickers) {
                stickerSetNameCell.setText(LocaleController.getString(C2797R.string.RecentStickers), C2797R.drawable.msg_close, LocaleController.getString(C2797R.string.ClearRecentStickersAlertTitle));
            } else if (obj2 == EmojiView.this.favouriteStickers) {
                stickerSetNameCell.setText(LocaleController.getString(C2797R.string.FavoriteStickers), 0);
            } else if (obj2 == EmojiView.this.premiumStickers) {
                stickerSetNameCell.setText(LocaleController.getString(C2797R.string.PremiumStickers), 0);
            }
        }

        public /* synthetic */ void lambda$onBindViewHolder$6(TLRPC.TL_messages_stickerSet tL_messages_stickerSet, View view) {
            EmojiView.this.delegate.onShowStickerSet(tL_messages_stickerSet.set, null, true);
        }

        public /* synthetic */ void lambda$onBindViewHolder$7(TLRPC.TL_messages_stickerSet tL_messages_stickerSet, View view) {
            EmojiView.this.delegate.onShowStickerSet(tL_messages_stickerSet.set, null, false);
        }

        /* JADX WARN: Removed duplicated region for block: B:135:0x017e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void updateItems() {
            /*
                Method dump skipped, instruction units count: 554
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.StickersGridAdapter.updateItems():void");
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyItemRangeRemoved(int i, int i2) {
            updateItems();
            super.notifyItemRangeRemoved(i, i2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            updateItems();
            super.notifyDataSetChanged();
        }
    }

    public static class EmojiPackExpand extends FrameLayout {
        public TextView textView;

        public EmojiPackExpand(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            TextView textView = new TextView(context);
            this.textView = textView;
            textView.setTextSize(1, 13.0f);
            this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhite, resourcesProvider));
            this.textView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1036dp(11.0f), ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_chat_emojiPanelStickerSetName, resourcesProvider), 99)));
            this.textView.setTypeface(AndroidUtilities.bold());
            this.textView.setPadding(AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(1.66f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(2.0f));
            addView(this.textView, LayoutHelper.createFrame(-2, -2, 17));
        }
    }

    public static class CustomEmoji {
        public long documentId;
        public String emoticon;
        public TLRPC.TL_messages_stickerSet stickerSet;

        public TLRPC.Document getDocument() {
            TLRPC.TL_messages_stickerSet tL_messages_stickerSet = this.stickerSet;
            if (tL_messages_stickerSet != null && tL_messages_stickerSet.documents != null) {
                for (int i = 0; i < this.stickerSet.documents.size(); i++) {
                    TLRPC.Document document = this.stickerSet.documents.get(i);
                    if (document != null && document.f1253id == this.documentId) {
                        return document;
                    }
                }
            }
            return null;
        }
    }

    public class EmojiGridAdapter extends RecyclerListView.SelectionAdapter {
        private int firstTrendingRow;
        private ArrayList<TLRPC.TL_messages_stickerSet> frozenEmojiPacks;
        private int itemCount;
        private ArrayList<Integer> packStartPosition;
        public int plainEmojisCount;
        private SparseIntArray positionToExpand;
        private SparseIntArray positionToSection;
        private SparseIntArray positionToUnlock;
        private int recentlyUsedHeaderRow;
        private ArrayList<Integer> rowHashCodes;
        private SparseIntArray sectionToPosition;
        private int trendingHeaderRow;
        private int trendingRow;

        public /* synthetic */ EmojiGridAdapter(EmojiView emojiView, EmojiViewIA emojiViewIA) {
            this();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public long getItemId(int i) {
            return i;
        }

        private EmojiGridAdapter() {
            this.trendingHeaderRow = -1;
            this.trendingRow = -1;
            this.firstTrendingRow = -1;
            this.recentlyUsedHeaderRow = -1;
            this.rowHashCodes = new ArrayList<>();
            this.positionToSection = new SparseIntArray();
            this.sectionToPosition = new SparseIntArray();
            this.positionToUnlock = new SparseIntArray();
            this.positionToExpand = new SparseIntArray();
            this.packStartPosition = new ArrayList<>();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.itemCount;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            return itemViewType == 0 || itemViewType == 4 || itemViewType == 3 || itemViewType == 6;
        }

        public /* synthetic */ void lambda$onCreateViewHolder$0(View view) {
            if (EmojiView.this.featuredEmojiSets == null || EmojiView.this.featuredEmojiSets.isEmpty() || ((TLRPC.StickerSetCovered) EmojiView.this.featuredEmojiSets.get(0)).set == null) {
                return;
            }
            MessagesController.getEmojiSettings(EmojiView.this.currentAccount).edit().putLong("emoji_featured_hidden", ((TLRPC.StickerSetCovered) EmojiView.this.featuredEmojiSets.get(0)).set.f1280id).commit();
            if (EmojiView.this.emojiAdapter != null) {
                EmojiView.this.emojiAdapter.notifyItemRangeRemoved(1, 3);
            }
            if (EmojiView.this.emojiTabs != null) {
                EmojiView.this.emojiTabs.updateEmojiPacks(EmojiView.this.getEmojipacks());
            }
            updateRows();
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$EmojiGridAdapter$1 */
        public class C43231 extends RecyclerView.ItemDecoration {
            public C43231() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                rect.right = AndroidUtilities.m1036dp(2.0f);
            }
        }

        public /* synthetic */ void lambda$onCreateViewHolder$1(View view, int i) {
            TLRPC.StickerSet stickerSet;
            if (view.getTag() instanceof TLRPC.StickerSetCovered) {
                TLRPC.StickerSetCovered stickerSetCovered = (TLRPC.StickerSetCovered) view.getTag();
                ArrayList arrayList = new ArrayList();
                ArrayList<TLRPC.StickerSetCovered> featuredEmojiSets = MediaDataController.getInstance(EmojiView.this.currentAccount).getFeaturedEmojiSets();
                int i2 = -1;
                for (int i3 = 0; i3 < featuredEmojiSets.size(); i3++) {
                    TLRPC.StickerSetCovered stickerSetCovered2 = featuredEmojiSets.get(i3);
                    if (stickerSetCovered2 != null && stickerSetCovered2.set != null) {
                        TLRPC.TL_inputStickerSetID tL_inputStickerSetID = new TLRPC.TL_inputStickerSetID();
                        TLRPC.StickerSet stickerSet2 = stickerSetCovered2.set;
                        tL_inputStickerSetID.f1270id = stickerSet2.f1280id;
                        tL_inputStickerSetID.access_hash = stickerSet2.access_hash;
                        arrayList.add(tL_inputStickerSetID);
                        if (stickerSetCovered != null && (stickerSet = stickerSetCovered.set) != null && stickerSet.f1280id == stickerSetCovered2.set.f1280id) {
                            i2 = i3;
                        }
                    }
                }
                MediaDataController.getInstance(EmojiView.this.currentAccount).markFeaturedStickersAsRead(true, true);
                BaseFragment baseFragment = EmojiView.this.fragment;
                Context context = EmojiView.this.getContext();
                BaseFragment baseFragment2 = EmojiView.this.fragment;
                EmojiView emojiView = EmojiView.this;
                EmojiPacksAlert emojiPacksAlert = new EmojiPacksAlert(baseFragment, context, baseFragment2 == null ? emojiView.resourcesProvider : emojiView.fragment.getResourceProvider(), arrayList);
                if (i2 >= 0) {
                    emojiPacksAlert.highlight(i2);
                }
                if (EmojiView.this.fragment != null) {
                    EmojiView.this.fragment.showDialog(emojiPacksAlert);
                } else {
                    emojiPacksAlert.show();
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View imageViewEmoji;
            View view;
            if (i != 0) {
                if (i == 1) {
                    StickerSetNameCell stickerSetNameCell = new StickerSetNameCell(EmojiView.this.getContext(), true, EmojiView.this.resourcesProvider, EmojiView.this.glassDesign);
                    stickerSetNameCell.setOnIconClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiGridAdapter$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            this.f$0.lambda$onCreateViewHolder$0(view2);
                        }
                    });
                    view = stickerSetNameCell;
                } else if (i == 3) {
                    EmojiView emojiView = EmojiView.this;
                    imageViewEmoji = emojiView.new EmojiPackButton(emojiView.getContext());
                } else if (i == 4) {
                    EmojiView emojiView2 = EmojiView.this;
                    Context context = emojiView2.getContext();
                    EmojiView emojiView3 = EmojiView.this;
                    TrendingAdapter trendingAdapter = emojiView3.new TrendingAdapter(true);
                    emojiView3.trendingEmojiAdapter = trendingAdapter;
                    TrendingListView trendingListView = emojiView2.new TrendingListView(context, trendingAdapter);
                    trendingListView.setPadding(AndroidUtilities.m1036dp(8.0f), AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(8.0f), 0);
                    trendingListView.setClipToPadding(false);
                    trendingListView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: org.telegram.ui.Components.EmojiView.EmojiGridAdapter.1
                        public C43231() {
                        }

                        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                        public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
                            rect.right = AndroidUtilities.m1036dp(2.0f);
                        }
                    });
                    trendingListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiGridAdapter$$ExternalSyntheticLambda1
                        @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                        public final void onItemClick(View view2, int i2) {
                            this.f$0.lambda$onCreateViewHolder$1(view2, i2);
                        }
                    });
                    view = trendingListView;
                } else if (i == 5) {
                    EmojiView emojiView4 = EmojiView.this;
                    imageViewEmoji = emojiView4.new EmojiPackHeader(emojiView4.getContext());
                } else if (i == 6) {
                    imageViewEmoji = new EmojiPackExpand(EmojiView.this.getContext(), EmojiView.this.resourcesProvider);
                } else {
                    View view2 = new View(EmojiView.this.getContext());
                    view2.setLayoutParams(new RecyclerView.LayoutParams(-1, EmojiView.this.searchFieldHeight));
                    imageViewEmoji = view2;
                }
                imageViewEmoji = view;
            } else {
                EmojiView emojiView5 = EmojiView.this;
                imageViewEmoji = emojiView5.new ImageViewEmoji(emojiView5.getContext());
            }
            return new RecyclerListView.Holder(imageViewEmoji);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, @SuppressLint({"RecyclerView"}) int i) {
            String str;
            String str2;
            Long lValueOf;
            TLRPC.Document document;
            TLRPC.Document document2;
            int itemViewType = viewHolder.getItemViewType();
            boolean z = true;
            EmojiPack emojiPack = null;
            if (itemViewType != 0) {
                if (itemViewType == 1) {
                    StickerSetNameCell stickerSetNameCell = (StickerSetNameCell) viewHolder.itemView;
                    stickerSetNameCell.position = i;
                    int i2 = this.positionToSection.get(i);
                    if (i == this.trendingHeaderRow) {
                        stickerSetNameCell.setText(LocaleController.getString(C2797R.string.FeaturedEmojiPacks), C2797R.drawable.msg_close, LocaleController.getString(C2797R.string.AccDescrCloseTrendingEmoji));
                        return;
                    }
                    if (i == this.recentlyUsedHeaderRow) {
                        stickerSetNameCell.setText(LocaleController.getString(C2797R.string.RecentlyUsed), 0);
                        return;
                    }
                    int length = EmojiView.this.emojiTitles.length;
                    EmojiView emojiView = EmojiView.this;
                    if (i2 >= length) {
                        try {
                            stickerSetNameCell.setText(((EmojiPack) emojiView.emojipacksProcessed.get(i2 - EmojiView.this.emojiTitles.length)).set.title, 0);
                            return;
                        } catch (Exception unused) {
                            stickerSetNameCell.setText(_UrlKt.FRAGMENT_ENCODE_SET, 0);
                            return;
                        }
                    }
                    stickerSetNameCell.setText(emojiView.emojiTitles[i2], 0);
                    return;
                }
                if (itemViewType != 5) {
                    if (itemViewType != 6) {
                        return;
                    }
                    EmojiPackExpand emojiPackExpand = (EmojiPackExpand) viewHolder.itemView;
                    int i3 = this.positionToExpand.get(i);
                    int spanCount = EmojiView.this.emojiLayoutManager.getSpanCount() * 3;
                    if (i3 >= 0 && i3 < EmojiView.this.emojipacksProcessed.size()) {
                        emojiPack = (EmojiPack) EmojiView.this.emojipacksProcessed.get(i3);
                    }
                    if (emojiPack != null) {
                        emojiPackExpand.textView.setText("+" + ((emojiPack.documents.size() - spanCount) + 1));
                        return;
                    }
                    return;
                }
                EmojiPackHeader emojiPackHeader = (EmojiPackHeader) viewHolder.itemView;
                int length2 = this.positionToSection.get(i) - EmojiView.this.emojiTitles.length;
                EmojiPack emojiPack2 = (EmojiPack) EmojiView.this.emojipacksProcessed.get(length2);
                int i4 = length2 - 1;
                EmojiPack emojiPack3 = i4 >= 0 ? (EmojiPack) EmojiView.this.emojipacksProcessed.get(i4) : null;
                if (emojiPack2 == null || !emojiPack2.featured || (emojiPack3 != null && !emojiPack3.free && emojiPack3.installed && !UserConfig.getInstance(EmojiView.this.currentAccount).isPremium() && !EmojiView.this.allowEmojisForNonPremium)) {
                    z = false;
                }
                if (emojiPack2 != null && emojiPack2.needLoadSet != null) {
                    MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSet(emojiPack2.needLoadSet, false);
                    emojiPack2.needLoadSet = null;
                }
                emojiPackHeader.setStickerSet(emojiPack2, z);
                return;
            }
            ImageViewEmoji imageViewEmoji = (ImageViewEmoji) viewHolder.itemView;
            imageViewEmoji.position = i;
            imageViewEmoji.pack = null;
            if (EmojiView.this.needEmojiSearch) {
                i--;
            }
            if (this.recentlyUsedHeaderRow >= 0) {
                i--;
            }
            if (this.trendingRow >= 0) {
                i -= 2;
            }
            int size = EmojiView.this.getRecentEmoji().size();
            if (i < size) {
                String str3 = EmojiView.this.getRecentEmoji().get(i);
                if (str3 == null || !str3.startsWith("animated_")) {
                    str = str3;
                    lValueOf = null;
                    str2 = str;
                    document2 = null;
                } else {
                    try {
                        lValueOf = Long.valueOf(Long.parseLong(str3.substring(9)));
                        str = null;
                    } catch (Exception unused2) {
                        str = str3;
                        lValueOf = null;
                    }
                    str2 = str;
                    document2 = null;
                }
            } else {
                int i5 = 0;
                while (true) {
                    String[][] strArr = EmojiData.dataColored;
                    if (i5 >= strArr.length) {
                        str = null;
                        break;
                    }
                    String[] strArr2 = strArr[i5];
                    int length3 = strArr2.length + 1;
                    int i6 = (i - size) - 1;
                    if (i6 < 0 || i >= size + length3) {
                        size += length3;
                        i5++;
                    } else {
                        str = strArr2[i6];
                        String str4 = Emoji.emojiColor.get(str);
                        if (str4 != null) {
                            String strAddColorToCode = EmojiView.addColorToCode(str, str4);
                            str2 = str;
                            str = strAddColorToCode;
                        }
                    }
                }
                str2 = str;
                if (str2 != null) {
                    lValueOf = null;
                    document = null;
                    document2 = document;
                    z = false;
                } else {
                    boolean z2 = UserConfig.getInstance(EmojiView.this.currentAccount).isPremium() || EmojiView.this.allowEmojisForNonPremium;
                    int spanCount2 = EmojiView.this.emojiLayoutManager.getSpanCount() * 3;
                    for (int i7 = 0; i7 < this.packStartPosition.size(); i7++) {
                        EmojiPack emojiPack4 = (EmojiPack) EmojiView.this.emojipacksProcessed.get(i7);
                        int iIntValue = this.packStartPosition.get(i7).intValue() + 1;
                        int size2 = ((emojiPack4.installed && !emojiPack4.featured && (emojiPack4.free || z2)) || emojiPack4.expanded) ? emojiPack4.documents.size() : Math.min(spanCount2, emojiPack4.documents.size());
                        int i8 = imageViewEmoji.position;
                        if (i8 >= iIntValue && i8 - iIntValue < size2) {
                            imageViewEmoji.pack = emojiPack4;
                            TLRPC.Document document3 = emojiPack4.documents.get(imageViewEmoji.position - iIntValue);
                            document = document3;
                            lValueOf = document3 == null ? null : Long.valueOf(document3.f1253id);
                            document2 = document;
                            z = false;
                        }
                    }
                    lValueOf = null;
                    document = null;
                    document2 = document;
                    z = false;
                }
            }
            if (lValueOf != null) {
                imageViewEmoji.setPadding(AndroidUtilities.m1036dp(3.0f), AndroidUtilities.m1036dp(3.0f), AndroidUtilities.m1036dp(3.0f), AndroidUtilities.m1036dp(3.0f));
            } else {
                imageViewEmoji.setPadding(0, 0, 0, 0);
            }
            if (lValueOf != null) {
                imageViewEmoji.setImageDrawable(null, z);
                if (imageViewEmoji.getSpan() == null || imageViewEmoji.getSpan().getDocumentId() != lValueOf.longValue()) {
                    if (document2 != null) {
                        imageViewEmoji.setSpan(new AnimatedEmojiSpan(document2, (Paint.FontMetricsInt) null));
                    } else {
                        imageViewEmoji.setSpan(new AnimatedEmojiSpan(lValueOf.longValue(), (Paint.FontMetricsInt) null));
                    }
                }
            } else {
                imageViewEmoji.setImageDrawable(Emoji.getEmojiBigDrawable(str), z);
                imageViewEmoji.setSpan(null);
            }
            imageViewEmoji.setTag(str2);
            imageViewEmoji.setContentDescription(str);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == this.trendingRow) {
                return 4;
            }
            if (i == this.trendingHeaderRow || i == this.recentlyUsedHeaderRow) {
                return 1;
            }
            if (this.positionToSection.indexOfKey(i) >= 0) {
                return this.positionToSection.get(i) >= EmojiData.dataColored.length ? 5 : 1;
            }
            if (EmojiView.this.needEmojiSearch && i == 0) {
                return 2;
            }
            if (this.positionToUnlock.indexOfKey(i) >= 0) {
                return 3;
            }
            return this.positionToExpand.indexOfKey(i) >= 0 ? 6 : 0;
        }

        private void removeGroupEmojiPackFromInstalled(TLRPC.StickerSet stickerSet, ArrayList<TLRPC.TL_messages_stickerSet> arrayList) {
            for (int i = 0; i < arrayList.size(); i++) {
                TLRPC.TL_messages_stickerSet tL_messages_stickerSet = arrayList.get(i);
                if (tL_messages_stickerSet != null && tL_messages_stickerSet.set.f1280id == stickerSet.f1280id) {
                    arrayList.remove(i);
                    return;
                }
            }
        }

        public void processEmoji(boolean z) {
            int i;
            EmojiView emojiView;
            boolean z2;
            TLRPC.TL_messages_stickerSet groupStickerSetById;
            EmojiView.this.emojipacksProcessed.clear();
            if (EmojiView.this.allowAnimatedEmoji) {
                MediaDataController mediaDataController = MediaDataController.getInstance(EmojiView.this.currentAccount);
                if (z || this.frozenEmojiPacks == null) {
                    this.frozenEmojiPacks = new ArrayList<>(mediaDataController.getStickerSets(5));
                }
                ArrayList<TLRPC.TL_messages_stickerSet> arrayList = this.frozenEmojiPacks;
                boolean z3 = UserConfig.getInstance(EmojiView.this.currentAccount).isPremium() || EmojiView.this.allowEmojisForNonPremium || LocaleUtils.canUseLocalPremiumEmojis(EmojiView.this.currentAccount);
                if (EmojiView.this.info == null || EmojiView.this.info.emojiset == null || (groupStickerSetById = mediaDataController.getGroupStickerSetById(EmojiView.this.info.emojiset)) == null) {
                    i = 0;
                } else {
                    EmojiPack emojiPack = new EmojiPack();
                    emojiPack.index = 0;
                    emojiPack.set = EmojiView.this.info.emojiset;
                    emojiPack.documents = new ArrayList<>(groupStickerSetById.documents);
                    emojiPack.free = true;
                    emojiPack.installed = true;
                    emojiPack.featured = false;
                    emojiPack.expanded = true;
                    emojiPack.forGroup = true;
                    EmojiView.this.emojipacksProcessed.add(emojiPack);
                    removeGroupEmojiPackFromInstalled(emojiPack.set, arrayList);
                    i = 1;
                }
                if (!z3) {
                    int i2 = 0;
                    while (i2 < arrayList.size()) {
                        TLRPC.TL_messages_stickerSet tL_messages_stickerSet = arrayList.get(i2);
                        if (tL_messages_stickerSet != null && !MessageObject.isPremiumEmojiPack(tL_messages_stickerSet)) {
                            EmojiPack emojiPack2 = new EmojiPack();
                            emojiPack2.index = i;
                            emojiPack2.set = tL_messages_stickerSet.set;
                            emojiPack2.documents = new ArrayList<>(tL_messages_stickerSet.documents);
                            emojiPack2.free = true;
                            emojiPack2.installed = mediaDataController.isStickerPackInstalled(tL_messages_stickerSet.set.f1280id);
                            emojiPack2.featured = false;
                            emojiPack2.expanded = true;
                            EmojiView.this.emojipacksProcessed.add(emojiPack2);
                            arrayList.remove(i2);
                            i2--;
                            i++;
                        }
                        i2++;
                    }
                }
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    TLRPC.TL_messages_stickerSet tL_messages_stickerSet2 = arrayList.get(i3);
                    if (z3) {
                        EmojiPack emojiPack3 = new EmojiPack();
                        int i4 = i + 1;
                        emojiPack3.index = i;
                        TLRPC.StickerSet stickerSet = tL_messages_stickerSet2.set;
                        emojiPack3.set = stickerSet;
                        emojiPack3.documents = tL_messages_stickerSet2.documents;
                        emojiPack3.free = false;
                        emojiPack3.installed = mediaDataController.isStickerPackInstalled(stickerSet.f1280id);
                        emojiPack3.featured = false;
                        emojiPack3.expanded = true;
                        EmojiView.this.emojipacksProcessed.add(emojiPack3);
                        i = i4;
                    } else {
                        ArrayList arrayList2 = new ArrayList();
                        ArrayList arrayList3 = new ArrayList();
                        if (tL_messages_stickerSet2 != null && tL_messages_stickerSet2.documents != null) {
                            for (int i5 = 0; i5 < tL_messages_stickerSet2.documents.size(); i5++) {
                                boolean zIsFreeEmoji = MessageObject.isFreeEmoji(tL_messages_stickerSet2.documents.get(i5));
                                ArrayList<TLRPC.Document> arrayList4 = tL_messages_stickerSet2.documents;
                                if (zIsFreeEmoji) {
                                    arrayList2.add(arrayList4.get(i5));
                                } else {
                                    arrayList3.add(arrayList4.get(i5));
                                }
                            }
                        }
                        if (arrayList2.size() > 0) {
                            EmojiPack emojiPack4 = new EmojiPack();
                            emojiPack4.index = i;
                            emojiPack4.set = tL_messages_stickerSet2.set;
                            emojiPack4.documents = new ArrayList<>(arrayList2);
                            emojiPack4.free = true;
                            emojiPack4.installed = mediaDataController.isStickerPackInstalled(tL_messages_stickerSet2.set.f1280id);
                            emojiPack4.featured = false;
                            emojiPack4.expanded = true;
                            EmojiView.this.emojipacksProcessed.add(emojiPack4);
                            i++;
                        }
                        if (arrayList3.size() > 0) {
                            EmojiPack emojiPack5 = new EmojiPack();
                            emojiPack5.index = i;
                            emojiPack5.set = tL_messages_stickerSet2.set;
                            emojiPack5.documents = new ArrayList<>(arrayList3);
                            emojiPack5.free = false;
                            emojiPack5.installed = mediaDataController.isStickerPackInstalled(tL_messages_stickerSet2.set.f1280id);
                            emojiPack5.featured = false;
                            emojiPack5.expanded = EmojiView.this.expandedEmojiSets.contains(Long.valueOf(emojiPack5.set.f1280id));
                            EmojiView.this.emojipacksProcessed.add(emojiPack5);
                            i++;
                        }
                    }
                }
                int i6 = 0;
                while (true) {
                    int size = EmojiView.this.featuredEmojiSets.size();
                    emojiView = EmojiView.this;
                    if (i6 >= size) {
                        break;
                    }
                    TLRPC.StickerSetCovered stickerSetCovered = (TLRPC.StickerSetCovered) emojiView.featuredEmojiSets.get(i6);
                    EmojiPack emojiPack6 = new EmojiPack();
                    emojiPack6.installed = mediaDataController.isStickerPackInstalled(stickerSetCovered.set.f1280id);
                    TLRPC.StickerSet stickerSet2 = stickerSetCovered.set;
                    emojiPack6.set = stickerSet2;
                    if (stickerSetCovered instanceof TLRPC.TL_stickerSetFullCovered) {
                        emojiPack6.documents = ((TLRPC.TL_stickerSetFullCovered) stickerSetCovered).documents;
                    } else if (stickerSetCovered instanceof TLRPC.TL_stickerSetNoCovered) {
                        TLRPC.TL_messages_stickerSet stickerSet3 = mediaDataController.getStickerSet(MediaDataController.getInputStickerSet(stickerSet2), Integer.valueOf(stickerSetCovered.set.hash), true);
                        if (stickerSet3 != null) {
                            emojiPack6.documents = stickerSet3.documents;
                        } else {
                            emojiPack6.needLoadSet = MediaDataController.getInputStickerSet(stickerSetCovered.set);
                        }
                    } else {
                        emojiPack6.documents = stickerSetCovered.covers;
                    }
                    ArrayList<TLRPC.Document> arrayList5 = emojiPack6.documents;
                    if (arrayList5 != null && !arrayList5.isEmpty()) {
                        int i7 = i + 1;
                        emojiPack6.index = i;
                        int i8 = 0;
                        while (true) {
                            if (i8 >= emojiPack6.documents.size()) {
                                z2 = false;
                                break;
                            } else {
                                if (!MessageObject.isFreeEmoji(emojiPack6.documents.get(i8))) {
                                    z2 = true;
                                    break;
                                }
                                i8++;
                            }
                        }
                        emojiPack6.free = !z2;
                        emojiPack6.expanded = EmojiView.this.expandedEmojiSets.contains(Long.valueOf(emojiPack6.set.f1280id));
                        emojiPack6.featured = true;
                        EmojiView.this.emojipacksProcessed.add(emojiPack6);
                        i = i7;
                    }
                    i6++;
                }
                if (emojiView.emojiTabs != null) {
                    EmojiView.this.emojiTabs.updateEmojiPacks(EmojiView.this.getEmojipacks());
                }
            }
        }

        public void expand(int i, View view) {
            int i2 = this.positionToExpand.get(i);
            if (i2 < 0 || i2 >= EmojiView.this.emojipacksProcessed.size()) {
                return;
            }
            EmojiPack emojiPack = (EmojiPack) EmojiView.this.emojipacksProcessed.get(i2);
            if (emojiPack.expanded) {
                return;
            }
            boolean z = i2 + 1 == EmojiView.this.emojipacksProcessed.size();
            int iIntValue = this.packStartPosition.get(i2).intValue();
            EmojiView.this.expandedEmojiSets.add(Long.valueOf(emojiPack.set.f1280id));
            boolean z2 = UserConfig.getInstance(EmojiView.this.currentAccount).isPremium() || EmojiView.this.allowEmojisForNonPremium;
            int spanCount = EmojiView.this.emojiLayoutManager.getSpanCount() * 3;
            int size = ((emojiPack.installed && !emojiPack.featured && (emojiPack.free || z2)) || emojiPack.expanded) ? emojiPack.documents.size() : Math.min(spanCount, emojiPack.documents.size());
            Integer numValueOf = null;
            Integer numValueOf2 = emojiPack.documents.size() > spanCount ? Integer.valueOf(iIntValue + 1 + size) : null;
            emojiPack.expanded = true;
            int size2 = emojiPack.documents.size() - size;
            if (size2 > 0) {
                numValueOf2 = Integer.valueOf(iIntValue + 1 + size);
                numValueOf = Integer.valueOf(size2);
            }
            processEmoji(false);
            updateRows();
            if (numValueOf2 == null || numValueOf == null) {
                return;
            }
            EmojiView.this.animateExpandFromButton = view;
            EmojiView.this.animateExpandFromPosition = numValueOf2.intValue();
            EmojiView.this.animateExpandToPosition = numValueOf2.intValue() + numValueOf.intValue();
            EmojiView.this.animateExpandStartTime = SystemClock.elapsedRealtime();
            notifyItemRangeInserted(numValueOf2.intValue(), numValueOf.intValue());
            notifyItemChanged(numValueOf2.intValue());
            if (z) {
                final int iIntValue2 = numValueOf2.intValue();
                final float f = numValueOf.intValue() > spanCount / 2 ? 1.5f : 4.0f;
                EmojiView.this.post(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiGridAdapter$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$expand$2(f, iIntValue2);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$expand$2(float f, int i) {
            try {
                LinearSmoothScrollerCustom linearSmoothScrollerCustom = new LinearSmoothScrollerCustom(EmojiView.this.emojiGridView.getContext(), 0, f);
                linearSmoothScrollerCustom.setTargetPosition(i);
                EmojiView.this.emojiLayoutManager.startSmoothScroll(linearSmoothScrollerCustom);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }

        public void updateRows() {
            this.positionToSection.clear();
            this.sectionToPosition.clear();
            this.positionToUnlock.clear();
            this.positionToExpand.clear();
            this.packStartPosition.clear();
            this.rowHashCodes.clear();
            int i = 0;
            this.itemCount = 0;
            boolean z = UserConfig.getInstance(EmojiView.this.currentAccount).isPremium() || EmojiView.this.allowEmojisForNonPremium;
            if (EmojiView.this.needEmojiSearch) {
                this.itemCount++;
                this.rowHashCodes.add(-1);
            }
            if (z && EmojiView.this.allowAnimatedEmoji && EmojiView.this.featuredEmojiSets.size() > 0 && ((TLRPC.StickerSetCovered) EmojiView.this.featuredEmojiSets.get(0)).set != null && MessagesController.getEmojiSettings(EmojiView.this.currentAccount).getLong("emoji_featured_hidden", 0L) != ((TLRPC.StickerSetCovered) EmojiView.this.featuredEmojiSets.get(0)).set.f1280id && EmojiView.this.needEmojiSearch) {
                int i2 = this.itemCount;
                this.trendingHeaderRow = i2;
                this.trendingRow = i2 + 1;
                this.itemCount = i2 + 3;
                this.recentlyUsedHeaderRow = i2 + 2;
                this.rowHashCodes.add(324953);
                this.rowHashCodes.add(123342);
                this.rowHashCodes.add(929132);
            } else {
                this.trendingHeaderRow = -1;
                this.trendingRow = -1;
                this.recentlyUsedHeaderRow = -1;
            }
            ArrayList<String> recentEmoji = EmojiView.this.getRecentEmoji();
            if (EmojiView.this.emojiTabs != null) {
                EmojiView.this.emojiTabs.showRecent(!recentEmoji.isEmpty());
            }
            this.itemCount += recentEmoji.size();
            for (int i3 = 0; i3 < recentEmoji.size(); i3++) {
                this.rowHashCodes.add(Integer.valueOf(Objects.hash(-43263, recentEmoji.get(i3))));
            }
            int i4 = 0;
            int i5 = 0;
            while (true) {
                String[][] strArr = EmojiData.dataColored;
                if (i4 >= strArr.length) {
                    break;
                }
                this.positionToSection.put(this.itemCount, i5);
                this.sectionToPosition.put(i5, this.itemCount);
                this.itemCount += strArr[i4].length + 1;
                this.rowHashCodes.add(Integer.valueOf(Objects.hash(43245, Integer.valueOf(i4))));
                int i6 = 0;
                while (true) {
                    String[] strArr2 = EmojiData.dataColored[i4];
                    if (i6 < strArr2.length) {
                        this.rowHashCodes.add(Integer.valueOf(strArr2[i6].hashCode()));
                        i6++;
                    }
                }
                i4++;
                i5++;
            }
            int spanCount = EmojiView.this.emojiLayoutManager.getSpanCount() * 3;
            this.plainEmojisCount = this.itemCount;
            this.firstTrendingRow = -1;
            if (EmojiView.this.emojipacksProcessed != null) {
                while (i < EmojiView.this.emojipacksProcessed.size()) {
                    this.positionToSection.put(this.itemCount, i5);
                    this.sectionToPosition.put(i5, this.itemCount);
                    this.packStartPosition.add(Integer.valueOf(this.itemCount));
                    EmojiPack emojiPack = (EmojiPack) EmojiView.this.emojipacksProcessed.get(i);
                    boolean z2 = emojiPack.featured;
                    if (z2 && this.firstTrendingRow < 0) {
                        this.firstTrendingRow = this.itemCount;
                    }
                    int size = ((emojiPack.installed && !z2 && (emojiPack.free || z)) || emojiPack.expanded) ? emojiPack.documents.size() : Math.min(spanCount, emojiPack.documents.size());
                    int i7 = 1 + size;
                    if (emojiPack.expanded || emojiPack.documents.size() <= spanCount) {
                        size = i7;
                    }
                    ArrayList<Integer> arrayList = this.rowHashCodes;
                    Integer numValueOf = Integer.valueOf(emojiPack.featured ? 56345 : -495231);
                    TLRPC.StickerSet stickerSet = emojiPack.set;
                    arrayList.add(Integer.valueOf(Objects.hash(numValueOf, Long.valueOf(stickerSet == null ? i : stickerSet.f1280id), Boolean.valueOf(emojiPack.forGroup))));
                    for (int i8 = 1; i8 < size; i8++) {
                        this.rowHashCodes.add(Integer.valueOf(Objects.hash(Integer.valueOf(emojiPack.featured ? 3442 : -9964), Long.valueOf(emojiPack.documents.get(i8 - 1).f1253id))));
                    }
                    this.itemCount += size;
                    if (!emojiPack.expanded && emojiPack.documents.size() > spanCount) {
                        this.positionToExpand.put(this.itemCount, i);
                        this.rowHashCodes.add(Integer.valueOf(Objects.hash(Integer.valueOf(emojiPack.featured ? -65174 : 92242), Long.valueOf(emojiPack.set.f1280id))));
                        this.itemCount++;
                    }
                    i++;
                    i5++;
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            notifyDataSetChanged(false);
        }

        public void notifyDataSetChanged(boolean z) {
            if (EmojiView.this.frozen) {
                return;
            }
            ArrayList arrayList = new ArrayList(this.rowHashCodes);
            MediaDataController.getInstance(EmojiView.this.currentAccount).getFeaturedEmojiSets();
            EmojiView.this.featuredEmojiSets.clear();
            processEmoji(z);
            updateRows();
            if (EmojiView.this.trendingEmojiAdapter != null) {
                EmojiView.this.trendingEmojiAdapter.notifyDataSetChanged();
            }
            DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: org.telegram.ui.Components.EmojiView.EmojiGridAdapter.2
                final /* synthetic */ ArrayList val$prevRowHashCodes;

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public boolean areContentsTheSame(int i, int i2) {
                    return true;
                }

                public C43242(ArrayList arrayList2) {
                    arrayList = arrayList2;
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public int getOldListSize() {
                    return arrayList.size();
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public int getNewListSize() {
                    return EmojiGridAdapter.this.rowHashCodes.size();
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public boolean areItemsTheSame(int i, int i2) {
                    return ((Integer) arrayList.get(i)).equals(EmojiGridAdapter.this.rowHashCodes.get(i2));
                }
            }, false).dispatchUpdatesTo(this);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$EmojiGridAdapter$2 */
        public class C43242 extends DiffUtil.Callback {
            final /* synthetic */ ArrayList val$prevRowHashCodes;

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public boolean areContentsTheSame(int i, int i2) {
                return true;
            }

            public C43242(ArrayList arrayList2) {
                arrayList = arrayList2;
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public int getOldListSize() {
                return arrayList.size();
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public int getNewListSize() {
                return EmojiGridAdapter.this.rowHashCodes.size();
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public boolean areItemsTheSame(int i, int i2) {
                return ((Integer) arrayList.get(i)).equals(EmojiGridAdapter.this.rowHashCodes.get(i2));
            }
        }
    }

    public ArrayList<EmojiPack> getEmojipacks() {
        ArrayList<EmojiPack> arrayList = new ArrayList<>();
        for (int i = 0; i < this.emojipacksProcessed.size(); i++) {
            EmojiPack emojiPack = this.emojipacksProcessed.get(i);
            if ((!emojiPack.featured && (emojiPack.installed || this.installedEmojiSets.contains(Long.valueOf(emojiPack.set.f1280id)))) || (emojiPack.featured && !emojiPack.installed && !this.installedEmojiSets.contains(Long.valueOf(emojiPack.set.f1280id)))) {
                arrayList.add(emojiPack);
            }
        }
        return arrayList;
    }

    public static class EmojiPackInfo {
        private final ArrayList<TLRPC.Document> documents;
        private final TLRPC.Document firstDocument;
        private final TLRPC.StickerSet set;
        private final TLRPC.TL_messages_stickerSet stickerSet;
        private final TLRPC.StickerSetCovered stickerSetCovered;

        public /* synthetic */ EmojiPackInfo(TLRPC.StickerSetCovered stickerSetCovered, ArrayList arrayList, EmojiViewIA emojiViewIA) {
            this(stickerSetCovered, (ArrayList<TLRPC.Document>) arrayList);
        }

        public /* synthetic */ EmojiPackInfo(TLRPC.TL_messages_stickerSet tL_messages_stickerSet, ArrayList arrayList, EmojiViewIA emojiViewIA) {
            this(tL_messages_stickerSet, (ArrayList<TLRPC.Document>) arrayList);
        }

        private EmojiPackInfo(TLRPC.TL_messages_stickerSet tL_messages_stickerSet, ArrayList<TLRPC.Document> arrayList) {
            TLRPC.Document document = null;
            this.stickerSetCovered = null;
            this.stickerSet = tL_messages_stickerSet;
            this.set = tL_messages_stickerSet.set;
            this.documents = arrayList;
            if (arrayList != null && !arrayList.isEmpty()) {
                document = arrayList.get(0);
            }
            this.firstDocument = document;
        }

        private EmojiPackInfo(TLRPC.StickerSetCovered stickerSetCovered, ArrayList<TLRPC.Document> arrayList) {
            this.stickerSetCovered = stickerSetCovered;
            TLRPC.Document document = null;
            this.stickerSet = null;
            this.set = stickerSetCovered.set;
            this.documents = arrayList;
            if (arrayList != null && !arrayList.isEmpty()) {
                document = arrayList.get(0);
            }
            this.firstDocument = document;
        }
    }

    public class EmojiSearchAdapter extends RecyclerListView.SelectionAdapter {
        private FoundEmojiPacksRecyclerView foundPacksListView;
        private boolean isCompleted;
        private String lastSearchAlias;
        private String lastSearchEmojiString;
        private SearchRunnable searchRunnable;
        private boolean searchWas;
        private long selectedPackId;
        private TLRPC.StickerSet selectedPackStickerSet;
        private ArrayList<TLRPC.Document> selectedPackStickers;
        private final ArrayList<MediaDataController.KeywordResult> result = new ArrayList<>();
        private final ArrayList<MediaDataController.KeywordResult> resultPre = new ArrayList<>();
        private final ArrayList<MediaDataController.KeywordResult> resultGlobal = new ArrayList<>();
        private final ArrayList<EmojiPackInfo> packs = new ArrayList<>();

        public EmojiSearchAdapter(Context context) {
            C43271 c43271 = new FoundEmojiPacksRecyclerView(context, EmojiView.this.currentAccount, -1, false, new Utilities.Callback2() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$$ExternalSyntheticLambda0
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    this.f$0.foundPackListFillItems((ArrayList) obj, (UniversalAdapter) obj2);
                }
            }, new Utilities.Callback5() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$$ExternalSyntheticLambda1
                @Override // org.telegram.messenger.Utilities.Callback5
                public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    this.f$0.foundPackListOnClickItem((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue());
                }
            }, null, EmojiView.this.resourcesProvider, -1, 0) { // from class: org.telegram.ui.Components.EmojiView.EmojiSearchAdapter.1
                final /* synthetic */ EmojiView val$this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C43271(Context context2, int i, int i2, boolean z, Utilities.Callback2 callback2, Utilities.Callback5 callback5, Utilities.Callback5Return callback5Return, Theme.ResourcesProvider resourcesProvider, int i3, int i4, EmojiView emojiView) {
                    super(context2, i, i2, z, callback2, callback5, callback5Return, resourcesProvider, i3, i4);
                    emojiView = emojiView;
                }
            };
            this.foundPacksListView = c43271;
            c43271.setPadding(AndroidUtilities.m1036dp(10.0f), 0, AndroidUtilities.m1036dp(10.0f), AndroidUtilities.m1036dp(5.0f));
            this.foundPacksListView.setClipToPadding(false);
            this.foundPacksListView.adapter.setApplyBackground(false);
            this.foundPacksListView.setNestedScrollingEnabled(false);
            this.foundPacksListView.setDrawSelection(false);
            this.foundPacksListView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.EmojiView.EmojiSearchAdapter.2
                final /* synthetic */ EmojiView val$this$0;

                public ViewOnTouchListenerC43282(EmojiView emojiView) {
                    emojiView = emojiView;
                }

                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == 0) {
                        EmojiView.this.ignorePagerScroll = true;
                    } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                        EmojiView.this.ignorePagerScroll = false;
                    }
                    return false;
                }
            });
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$1 */
        public class C43271 extends FoundEmojiPacksRecyclerView {
            final /* synthetic */ EmojiView val$this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C43271(Context context2, int i, int i2, boolean z, Utilities.Callback2 callback2, Utilities.Callback5 callback5, Utilities.Callback5Return callback5Return, Theme.ResourcesProvider resourcesProvider, int i3, int i4, EmojiView emojiView) {
                super(context2, i, i2, z, callback2, callback5, callback5Return, resourcesProvider, i3, i4);
                emojiView = emojiView;
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$2 */
        public class ViewOnTouchListenerC43282 implements View.OnTouchListener {
            final /* synthetic */ EmojiView val$this$0;

            public ViewOnTouchListenerC43282(EmojiView emojiView) {
                emojiView = emojiView;
            }

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    EmojiView.this.ignorePagerScroll = true;
                } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                    EmojiView.this.ignorePagerScroll = false;
                }
                return false;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:101:0x00e3  */
        /* JADX WARN: Removed duplicated region for block: B:104:0x00f9  */
        /* JADX WARN: Removed duplicated region for block: B:109:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:74:0x0059  */
        /* JADX WARN: Removed duplicated region for block: B:93:0x00be  */
        /* JADX WARN: Removed duplicated region for block: B:94:0x00c1  */
        /* JADX WARN: Removed duplicated region for block: B:97:0x00d1  */
        /* JADX WARN: Removed duplicated region for block: B:98:0x00d3  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void foundPackListOnClickItem(org.telegram.p035ui.Components.UItem r19, android.view.View r20, int r21, float r22, float r23) {
            /*
                Method dump skipped, instruction units count: 255
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.EmojiSearchAdapter.foundPackListOnClickItem(org.telegram.ui.Components.UItem, android.view.View, int, float, float):void");
        }

        public void resetSelectedPackId() {
            int childCount = this.foundPacksListView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                ((FoundStickerPackCell) this.foundPacksListView.getChildAt(i)).setSelected(false, true);
            }
            this.selectedPackId = 0L;
            EmojiView.this.animatorSearchEmojiPackSelected.setValue(false, true);
            notifyDataSetChanged();
        }

        public void foundPackListFillItems(ArrayList<UItem> arrayList, UniversalAdapter universalAdapter) {
            ArrayList<EmojiPackInfo> arrayList2 = this.packs;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                EmojiPackInfo emojiPackInfo = arrayList2.get(i);
                i++;
                EmojiPackInfo emojiPackInfo2 = emojiPackInfo;
                if (emojiPackInfo2.stickerSet != null) {
                    arrayList.add(FoundStickerPackFactory.m1151of(emojiPackInfo2.stickerSet, emojiPackInfo2.stickerSet.set.f1280id == this.selectedPackId));
                } else if (emojiPackInfo2.stickerSetCovered != null) {
                    arrayList.add(FoundStickerPackFactory.m1150of(emojiPackInfo2.stickerSetCovered, emojiPackInfo2, emojiPackInfo2.stickerSetCovered.set.f1280id == this.selectedPackId));
                }
            }
        }

        private int globalSectionStart() {
            int i;
            if (this.packs.isEmpty()) {
                i = !this.result.isEmpty() ? 2 : 1;
            } else {
                i = 3;
            }
            return i + this.result.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            if (this.selectedPackId != 0) {
                return this.selectedPackStickers.size() + 4;
            }
            if (this.result.isEmpty() && this.resultGlobal.isEmpty() && this.packs.isEmpty() && !this.searchWas) {
                return EmojiView.this.getRecentEmoji().size() + 1;
            }
            int i = 2;
            if (this.result.isEmpty() && this.resultGlobal.isEmpty() && this.packs.isEmpty()) {
                return 2;
            }
            if (!this.packs.isEmpty()) {
                i = 3;
            } else if (this.result.isEmpty()) {
                i = 1;
            }
            int size = i + this.result.size();
            return !this.resultGlobal.isEmpty() ? size + 1 + this.resultGlobal.size() : size;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 0 || viewHolder.getItemViewType() == 4;
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$3 */
        public class C43293 extends FrameLayout {
            public C43293(Context context) {
                super(context);
            }

            @Override // android.widget.FrameLayout, android.view.View
            public void onMeasure(int i, int i2) {
                int iM1036dp;
                if (((View) EmojiView.this.getParent()) != null) {
                    iM1036dp = (int) (r3.getMeasuredHeight() - EmojiView.this.getY());
                } else {
                    iM1036dp = AndroidUtilities.m1036dp(120.0f);
                }
                super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(iM1036dp - EmojiView.this.searchFieldHeight, TLObject.FLAG_30));
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View imageViewEmoji;
            if (i == 0) {
                EmojiView emojiView = EmojiView.this;
                imageViewEmoji = emojiView.new ImageViewEmoji(emojiView.getContext());
            } else if (i == 1) {
                View view = new View(EmojiView.this.getContext());
                view.setLayoutParams(new RecyclerView.LayoutParams(-1, EmojiView.this.searchFieldHeight));
                imageViewEmoji = view;
            } else if (i == 3) {
                imageViewEmoji = new StickerSetNameCell(EmojiView.this.getContext(), true, EmojiView.this.resourcesProvider, EmojiView.this.glassDesign);
            } else if (i == 4) {
                FoundEmojiPacksRecyclerView foundEmojiPacksRecyclerView = this.foundPacksListView;
                foundEmojiPacksRecyclerView.setLayoutParams(new RecyclerView.LayoutParams(-1, AndroidUtilities.m1036dp(79.0f)));
                imageViewEmoji = foundEmojiPacksRecyclerView;
            } else if (i == 5) {
                View view2 = new View(EmojiView.this.getContext());
                view2.setLayoutParams(new RecyclerView.LayoutParams(-1, AndroidUtilities.m1036dp(68.0f)));
                imageViewEmoji = view2;
            } else {
                C43293 c43293 = new FrameLayout(EmojiView.this.getContext()) { // from class: org.telegram.ui.Components.EmojiView.EmojiSearchAdapter.3
                    public C43293(Context context) {
                        super(context);
                    }

                    @Override // android.widget.FrameLayout, android.view.View
                    public void onMeasure(int i2, int i22) {
                        int iM1036dp;
                        if (((View) EmojiView.this.getParent()) != null) {
                            iM1036dp = (int) (r3.getMeasuredHeight() - EmojiView.this.getY());
                        } else {
                            iM1036dp = AndroidUtilities.m1036dp(120.0f);
                        }
                        super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(iM1036dp - EmojiView.this.searchFieldHeight, TLObject.FLAG_30));
                    }
                };
                TextView textView = new TextView(EmojiView.this.getContext());
                textView.setText(LocaleController.getString(C2797R.string.NoEmojiFound));
                textView.setTextSize(1, 16.0f);
                EmojiView emojiView2 = EmojiView.this;
                int i2 = Theme.key_chat_emojiPanelEmptyText;
                textView.setTextColor(emojiView2.getThemedColor(i2));
                c43293.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 49, 0.0f, 10.0f, 0.0f, 0.0f));
                ImageView imageView = new ImageView(EmojiView.this.getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                imageView.setImageResource(C2797R.drawable.msg_emoji_question);
                imageView.setColorFilter(new PorterDuffColorFilter(EmojiView.this.getThemedColor(i2), PorterDuff.Mode.MULTIPLY));
                c43293.addView(imageView, LayoutHelper.createFrame(48, 48, 85));
                imageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView.EmojiSearchAdapter.4
                    public ViewOnClickListenerC43304() {
                    }

                    @Override // android.view.View.OnClickListener
                    public void onClick(View view3) {
                        boolean[] zArr = new boolean[1];
                        BottomSheet.Builder builder = new BottomSheet.Builder(EmojiView.this.getContext());
                        LinearLayout linearLayout = new LinearLayout(EmojiView.this.getContext());
                        linearLayout.setOrientation(1);
                        linearLayout.setPadding(AndroidUtilities.m1036dp(21.0f), 0, AndroidUtilities.m1036dp(21.0f), 0);
                        ImageView imageView2 = new ImageView(EmojiView.this.getContext());
                        imageView2.setImageResource(C2797R.drawable.smiles_info);
                        linearLayout.addView(imageView2, LayoutHelper.createLinear(-2, -2, 49, 0, 15, 0, 0));
                        TextView textView2 = new TextView(EmojiView.this.getContext());
                        textView2.setText(LocaleController.getString(C2797R.string.EmojiSuggestions));
                        textView2.setTextSize(1, 15.0f);
                        textView2.setTextColor(EmojiView.this.getThemedColor(Theme.key_dialogTextBlue2));
                        textView2.setGravity(LocaleController.isRTL ? 5 : 3);
                        textView2.setTypeface(AndroidUtilities.bold());
                        linearLayout.addView(textView2, LayoutHelper.createLinear(-2, -2, 51, 0, 24, 0, 0));
                        TextView textView3 = new TextView(EmojiView.this.getContext());
                        textView3.setText(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.EmojiSuggestionsInfo)));
                        textView3.setTextSize(1, 15.0f);
                        textView3.setTextColor(EmojiView.this.getThemedColor(Theme.key_dialogTextBlack));
                        textView3.setGravity(LocaleController.isRTL ? 5 : 3);
                        linearLayout.addView(textView3, LayoutHelper.createLinear(-2, -2, 51, 0, 11, 0, 0));
                        TextView textView4 = new TextView(EmojiView.this.getContext());
                        int i3 = C2797R.string.EmojiSuggestionsUrl;
                        String str = EmojiSearchAdapter.this.lastSearchAlias;
                        EmojiSearchAdapter emojiSearchAdapter = EmojiSearchAdapter.this;
                        textView4.setText(LocaleController.formatString("EmojiSuggestionsUrl", i3, str != null ? emojiSearchAdapter.lastSearchAlias : EmojiView.this.lastSearchKeyboardLanguage));
                        textView4.setTextSize(1, 15.0f);
                        textView4.setTextColor(EmojiView.this.getThemedColor(Theme.key_dialogTextLink));
                        textView4.setGravity(LocaleController.isRTL ? 5 : 3);
                        linearLayout.addView(textView4, LayoutHelper.createLinear(-2, -2, 51, 0, 18, 0, 16));
                        textView4.setOnClickListener(new AnonymousClass1(zArr, builder));
                        builder.setCustomView(linearLayout);
                        builder.show();
                    }

                    /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$4$1 */
                    public class AnonymousClass1 implements View.OnClickListener {
                        final /* synthetic */ BottomSheet.Builder val$builder;
                        final /* synthetic */ boolean[] val$loadingUrl;

                        public AnonymousClass1(boolean[] zArr, BottomSheet.Builder builder) {
                            this.val$loadingUrl = zArr;
                            this.val$builder = builder;
                        }

                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            boolean[] zArr = this.val$loadingUrl;
                            if (zArr[0]) {
                                return;
                            }
                            zArr[0] = true;
                            final AlertDialog[] alertDialogArr = {new AlertDialog(EmojiView.this.getContext(), 3)};
                            TLRPC.TL_messages_getEmojiURL tL_messages_getEmojiURL = new TLRPC.TL_messages_getEmojiURL();
                            String str = EmojiSearchAdapter.this.lastSearchAlias;
                            ViewOnClickListenerC43304 viewOnClickListenerC43304 = ViewOnClickListenerC43304.this;
                            tL_messages_getEmojiURL.lang_code = str != null ? EmojiSearchAdapter.this.lastSearchAlias : EmojiView.this.lastSearchKeyboardLanguage[0];
                            ConnectionsManager connectionsManager = ConnectionsManager.getInstance(EmojiView.this.currentAccount);
                            final BottomSheet.Builder builder = this.val$builder;
                            final int iSendRequest = connectionsManager.sendRequest(tL_messages_getEmojiURL, new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$4$1$$ExternalSyntheticLambda0
                                @Override // org.telegram.tgnet.RequestDelegate
                                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                                    this.f$0.lambda$onClick$1(alertDialogArr, builder, tLObject, tL_error);
                                }
                            });
                            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$4$1$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$onClick$3(alertDialogArr, iSendRequest);
                                }
                            }, 1000L);
                        }

                        public /* synthetic */ void lambda$onClick$1(final AlertDialog[] alertDialogArr, final BottomSheet.Builder builder, final TLObject tLObject, TLRPC.TL_error tL_error) {
                            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$4$1$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$onClick$0(alertDialogArr, tLObject, builder);
                                }
                            });
                        }

                        public /* synthetic */ void lambda$onClick$0(AlertDialog[] alertDialogArr, TLObject tLObject, BottomSheet.Builder builder) {
                            try {
                                alertDialogArr[0].dismiss();
                            } catch (Throwable unused) {
                            }
                            alertDialogArr[0] = null;
                            if (tLObject instanceof TLRPC.TL_emojiURL) {
                                Browser.openUrl(EmojiView.this.getContext(), ((TLRPC.TL_emojiURL) tLObject).url);
                                builder.getDismissRunnable().run();
                            }
                        }

                        public /* synthetic */ void lambda$onClick$3(AlertDialog[] alertDialogArr, final int i) {
                            AlertDialog alertDialog = alertDialogArr[0];
                            if (alertDialog == null) {
                                return;
                            }
                            alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$4$1$$ExternalSyntheticLambda2
                                @Override // android.content.DialogInterface.OnCancelListener
                                public final void onCancel(DialogInterface dialogInterface) {
                                    this.f$0.lambda$onClick$2(i, dialogInterface);
                                }
                            });
                            alertDialogArr[0].show();
                        }

                        public /* synthetic */ void lambda$onClick$2(int i, DialogInterface dialogInterface) {
                            ConnectionsManager.getInstance(EmojiView.this.currentAccount).cancelRequest(i, true);
                        }
                    }
                });
                c43293.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                imageViewEmoji = c43293;
            }
            return new RecyclerListView.Holder(imageViewEmoji);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$4 */
        public class ViewOnClickListenerC43304 implements View.OnClickListener {
            public ViewOnClickListenerC43304() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view3) {
                boolean[] zArr = new boolean[1];
                BottomSheet.Builder builder = new BottomSheet.Builder(EmojiView.this.getContext());
                LinearLayout linearLayout = new LinearLayout(EmojiView.this.getContext());
                linearLayout.setOrientation(1);
                linearLayout.setPadding(AndroidUtilities.m1036dp(21.0f), 0, AndroidUtilities.m1036dp(21.0f), 0);
                ImageView imageView2 = new ImageView(EmojiView.this.getContext());
                imageView2.setImageResource(C2797R.drawable.smiles_info);
                linearLayout.addView(imageView2, LayoutHelper.createLinear(-2, -2, 49, 0, 15, 0, 0));
                TextView textView2 = new TextView(EmojiView.this.getContext());
                textView2.setText(LocaleController.getString(C2797R.string.EmojiSuggestions));
                textView2.setTextSize(1, 15.0f);
                textView2.setTextColor(EmojiView.this.getThemedColor(Theme.key_dialogTextBlue2));
                textView2.setGravity(LocaleController.isRTL ? 5 : 3);
                textView2.setTypeface(AndroidUtilities.bold());
                linearLayout.addView(textView2, LayoutHelper.createLinear(-2, -2, 51, 0, 24, 0, 0));
                TextView textView3 = new TextView(EmojiView.this.getContext());
                textView3.setText(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.EmojiSuggestionsInfo)));
                textView3.setTextSize(1, 15.0f);
                textView3.setTextColor(EmojiView.this.getThemedColor(Theme.key_dialogTextBlack));
                textView3.setGravity(LocaleController.isRTL ? 5 : 3);
                linearLayout.addView(textView3, LayoutHelper.createLinear(-2, -2, 51, 0, 11, 0, 0));
                TextView textView4 = new TextView(EmojiView.this.getContext());
                int i3 = C2797R.string.EmojiSuggestionsUrl;
                String str = EmojiSearchAdapter.this.lastSearchAlias;
                EmojiSearchAdapter emojiSearchAdapter = EmojiSearchAdapter.this;
                textView4.setText(LocaleController.formatString("EmojiSuggestionsUrl", i3, str != null ? emojiSearchAdapter.lastSearchAlias : EmojiView.this.lastSearchKeyboardLanguage));
                textView4.setTextSize(1, 15.0f);
                textView4.setTextColor(EmojiView.this.getThemedColor(Theme.key_dialogTextLink));
                textView4.setGravity(LocaleController.isRTL ? 5 : 3);
                linearLayout.addView(textView4, LayoutHelper.createLinear(-2, -2, 51, 0, 18, 0, 16));
                textView4.setOnClickListener(new AnonymousClass1(zArr, builder));
                builder.setCustomView(linearLayout);
                builder.show();
            }

            /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$4$1 */
            public class AnonymousClass1 implements View.OnClickListener {
                final /* synthetic */ BottomSheet.Builder val$builder;
                final /* synthetic */ boolean[] val$loadingUrl;

                public AnonymousClass1(boolean[] zArr, BottomSheet.Builder builder) {
                    this.val$loadingUrl = zArr;
                    this.val$builder = builder;
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    boolean[] zArr = this.val$loadingUrl;
                    if (zArr[0]) {
                        return;
                    }
                    zArr[0] = true;
                    final AlertDialog[] alertDialogArr = {new AlertDialog(EmojiView.this.getContext(), 3)};
                    TLRPC.TL_messages_getEmojiURL tL_messages_getEmojiURL = new TLRPC.TL_messages_getEmojiURL();
                    String str = EmojiSearchAdapter.this.lastSearchAlias;
                    ViewOnClickListenerC43304 viewOnClickListenerC43304 = ViewOnClickListenerC43304.this;
                    tL_messages_getEmojiURL.lang_code = str != null ? EmojiSearchAdapter.this.lastSearchAlias : EmojiView.this.lastSearchKeyboardLanguage[0];
                    ConnectionsManager connectionsManager = ConnectionsManager.getInstance(EmojiView.this.currentAccount);
                    final BottomSheet.Builder builder = this.val$builder;
                    final int iSendRequest = connectionsManager.sendRequest(tL_messages_getEmojiURL, new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$4$1$$ExternalSyntheticLambda0
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                            this.f$0.lambda$onClick$1(alertDialogArr, builder, tLObject, tL_error);
                        }
                    });
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$4$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onClick$3(alertDialogArr, iSendRequest);
                        }
                    }, 1000L);
                }

                public /* synthetic */ void lambda$onClick$1(final AlertDialog[] alertDialogArr, final BottomSheet.Builder builder, final TLObject tLObject, TLRPC.TL_error tL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$4$1$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onClick$0(alertDialogArr, tLObject, builder);
                        }
                    });
                }

                public /* synthetic */ void lambda$onClick$0(AlertDialog[] alertDialogArr, TLObject tLObject, BottomSheet.Builder builder) {
                    try {
                        alertDialogArr[0].dismiss();
                    } catch (Throwable unused) {
                    }
                    alertDialogArr[0] = null;
                    if (tLObject instanceof TLRPC.TL_emojiURL) {
                        Browser.openUrl(EmojiView.this.getContext(), ((TLRPC.TL_emojiURL) tLObject).url);
                        builder.getDismissRunnable().run();
                    }
                }

                public /* synthetic */ void lambda$onClick$3(AlertDialog[] alertDialogArr, final int i) {
                    AlertDialog alertDialog = alertDialogArr[0];
                    if (alertDialog == null) {
                        return;
                    }
                    alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$4$1$$ExternalSyntheticLambda2
                        @Override // android.content.DialogInterface.OnCancelListener
                        public final void onCancel(DialogInterface dialogInterface) {
                            this.f$0.lambda$onClick$2(i, dialogInterface);
                        }
                    });
                    alertDialogArr[0].show();
                }

                public /* synthetic */ void lambda$onClick$2(int i, DialogInterface dialogInterface) {
                    ConnectionsManager.getInstance(EmojiView.this.currentAccount).cancelRequest(i, true);
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:129:0x00e4  */
        /* JADX WARN: Removed duplicated region for block: B:132:0x00eb  */
        /* JADX WARN: Removed duplicated region for block: B:134:0x00ef  */
        /* JADX WARN: Removed duplicated region for block: B:135:0x00f2  */
        /* JADX WARN: Removed duplicated region for block: B:141:0x0106  */
        /* JADX WARN: Removed duplicated region for block: B:143:0x011d  */
        /* JADX WARN: Removed duplicated region for block: B:148:0x0137  */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r9, int r10) {
            /*
                Method dump skipped, instruction units count: 374
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.EmojiSearchAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (this.selectedPackId != 0) {
                if (i == 0) {
                    return 1;
                }
                if (i == 1) {
                    return 4;
                }
                if (i == 2) {
                    return 3;
                }
                return i == getItemCount() - 1 ? 5 : 0;
            }
            if (i == 0) {
                return 1;
            }
            if (i == 1 && this.searchWas && this.result.isEmpty() && this.resultGlobal.isEmpty() && this.packs.isEmpty()) {
                return 2;
            }
            if (this.packs.isEmpty()) {
                if (!this.result.isEmpty() && i == 1) {
                    return 3;
                }
            } else {
                if (i == 1) {
                    return 4;
                }
                if (i == 2) {
                    return 3;
                }
            }
            return (this.resultGlobal.isEmpty() || i != globalSectionStart()) ? 0 : 3;
        }

        public void search(String str) {
            search(str, true);
        }

        public void search(String str, boolean z) {
            if (TextUtils.isEmpty(str)) {
                this.lastSearchEmojiString = null;
                if (EmojiView.this.emojiGridView.getAdapter() != EmojiView.this.emojiAdapter) {
                    EmojiView.this.emojiGridView.setAdapter(EmojiView.this.emojiAdapter);
                    this.searchWas = false;
                }
                this.selectedPackId = 0L;
                EmojiView.this.animatorSearchEmojiPackSelected.setValue(false, true);
                notifyDataSetChanged();
            } else {
                this.lastSearchEmojiString = str.toLowerCase();
            }
            SearchRunnable searchRunnable = this.searchRunnable;
            if (searchRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(searchRunnable);
            }
            if (TextUtils.isEmpty(this.lastSearchEmojiString)) {
                return;
            }
            this.resultPre.clear();
            this.isCompleted = false;
            EmojiView.this.emojiSearchField.showProgress(true);
            C43315 c43315 = new C43315();
            this.searchRunnable = c43315;
            AndroidUtilities.runOnUIThread(c43315, z ? 300L : 0L);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$5 */
        public class C43315 implements SearchRunnable {
            public C43315() {
            }

            @Override // org.telegram.ui.Components.EmojiView.SearchRunnable
            public void loadNext() {
                if (isLoading()) {
                    return;
                }
                final ArrayList arrayList = new ArrayList();
                EmojiView.this.emojiSearchField.showProgress(true);
                EmojiSearchAdapter.this.searchEmoji(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$5$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$loadNext$0(arrayList);
                    }
                }, arrayList, true);
            }

            public /* synthetic */ void lambda$loadNext$0(ArrayList arrayList) {
                EmojiView.this.emojiSearchField.showProgress(false);
                EmojiSearchAdapter emojiSearchAdapter = EmojiSearchAdapter.this;
                emojiSearchAdapter.isCompleted = emojiSearchAdapter.resultGlobal.size() >= arrayList.size();
                EmojiSearchAdapter.this.resultGlobal.clear();
                EmojiSearchAdapter.this.resultGlobal.addAll(arrayList);
                EmojiSearchAdapter.this.notifyDataSetChanged();
            }

            @Override // org.telegram.ui.Components.EmojiView.SearchRunnable
            public boolean isLoading() {
                return EmojiView.this.emojiSearchField.isInProgress();
            }

            @Override // org.telegram.ui.Components.EmojiView.SearchRunnable
            public boolean isCompleted() {
                return EmojiSearchAdapter.this.isCompleted;
            }

            @Override // java.lang.Runnable
            public void run() {
                final LinkedHashSet linkedHashSet = new LinkedHashSet();
                final String str = EmojiSearchAdapter.this.lastSearchEmojiString;
                final Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$5$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$run$8(str);
                    }
                };
                if (Emoji.fullyConsistsOfEmojis(str)) {
                    StickerCategoriesListView.search.fetch(UserConfig.selectedAccount, str, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$5$$ExternalSyntheticLambda2
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            EmojiView.EmojiSearchAdapter.C43315.$r8$lambda$aYmFiSEV_zPNdYdH42thmnPihWA(linkedHashSet, runnable, (TLRPC.TL_emojiList) obj);
                        }
                    });
                } else {
                    runnable.run();
                }
            }

            public /* synthetic */ void lambda$run$8(final String str) {
                String[] currentKeyboardLanguage = AndroidUtilities.getCurrentKeyboardLanguage();
                if (!Arrays.equals(EmojiView.this.lastSearchKeyboardLanguage, currentKeyboardLanguage)) {
                    MediaDataController.getInstance(EmojiView.this.currentAccount).fetchNewEmojiKeywords(currentKeyboardLanguage);
                }
                EmojiView.this.lastSearchKeyboardLanguage = currentKeyboardLanguage;
                final ArrayList arrayList = new ArrayList();
                final ArrayList arrayList2 = new ArrayList();
                final ArrayList arrayList3 = new ArrayList();
                Utilities.doCallbacks(new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$5$$ExternalSyntheticLambda3
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$run$2(str, arrayList3, (Runnable) obj);
                    }
                }, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$5$$ExternalSyntheticLambda4
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$run$4(str, (Runnable) obj);
                    }
                }, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$5$$ExternalSyntheticLambda5
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$run$5(str, arrayList2, (Runnable) obj);
                    }
                }, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$5$$ExternalSyntheticLambda6
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$run$6(arrayList, (Runnable) obj);
                    }
                }, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$5$$ExternalSyntheticLambda7
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$run$7(str, arrayList, arrayList2, arrayList3, (Runnable) obj);
                    }
                });
            }

            public /* synthetic */ void lambda$run$2(String str, final ArrayList arrayList, final Runnable runnable) {
                MediaDataController.getInstance(EmojiView.this.currentAccount).searchStickerSets(true, str, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$5$$ExternalSyntheticLambda8
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$run$1(arrayList, runnable, (ArrayList) obj);
                    }
                });
            }

            public /* synthetic */ void lambda$run$1(ArrayList arrayList, Runnable runnable, ArrayList arrayList2) {
                ArrayList<TLRPC.Document> arrayList3;
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    TLRPC.StickerSetCovered stickerSetCovered = (TLRPC.StickerSetCovered) obj;
                    if (stickerSetCovered instanceof TLRPC.TL_stickerSetFullCovered) {
                        arrayList3 = ((TLRPC.TL_stickerSetFullCovered) stickerSetCovered).documents;
                    } else if (stickerSetCovered instanceof TLRPC.TL_stickerSetNoCovered) {
                        TLRPC.TL_messages_stickerSet stickerSet = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSet(MediaDataController.getInputStickerSet(stickerSetCovered.set), Integer.valueOf(stickerSetCovered.set.hash), true);
                        arrayList3 = stickerSet != null ? stickerSet.documents : null;
                    } else {
                        arrayList3 = stickerSetCovered.covers;
                    }
                    if (arrayList3 != null && !arrayList3.isEmpty()) {
                        arrayList.add(new EmojiPackInfo(stickerSetCovered, arrayList3));
                    }
                }
                runnable.run();
            }

            public /* synthetic */ void lambda$run$4(final String str, final Runnable runnable) {
                MediaDataController.getInstance(EmojiView.this.currentAccount).getEmojiSuggestions(EmojiView.this.lastSearchKeyboardLanguage, EmojiSearchAdapter.this.lastSearchEmojiString, false, new MediaDataController.KeywordResultCallback() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$5$$ExternalSyntheticLambda9
                    @Override // org.telegram.messenger.MediaDataController.KeywordResultCallback
                    public final void run(ArrayList arrayList, String str2) {
                        this.f$0.lambda$run$3(str, runnable, arrayList, str2);
                    }
                }, null, SharedConfig.suggestAnimatedEmoji || UserConfig.getInstance(EmojiView.this.currentAccount).isPremium() || EmojiView.this.allowEmojisForNonPremium, false, true, 25);
            }

            public /* synthetic */ void lambda$run$3(String str, Runnable runnable, ArrayList arrayList, String str2) {
                if (str.equals(EmojiSearchAdapter.this.lastSearchEmojiString)) {
                    EmojiSearchAdapter.this.lastSearchAlias = str2;
                    EmojiSearchAdapter.this.resultPre.addAll(arrayList);
                    runnable.run();
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:110:0x009b  */
            /* JADX WARN: Removed duplicated region for block: B:129:0x010b  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public /* synthetic */ void lambda$run$5(java.lang.String r11, java.util.ArrayList r12, java.lang.Runnable r13) {
                /*
                    Method dump skipped, instruction units count: 354
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.EmojiSearchAdapter.C43315.lambda$run$5(java.lang.String, java.util.ArrayList, java.lang.Runnable):void");
            }

            public /* synthetic */ void lambda$run$6(ArrayList arrayList, Runnable runnable) {
                if (ConnectionsManager.getInstance(EmojiView.this.currentAccount).getConnectionState() != 3) {
                    runnable.run();
                } else {
                    EmojiSearchAdapter.this.searchEmoji(runnable, arrayList, false);
                }
            }

            public /* synthetic */ void lambda$run$7(String str, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, Runnable runnable) {
                if (str.equals(EmojiSearchAdapter.this.lastSearchEmojiString)) {
                    int i = 0;
                    EmojiView.this.emojiSearchField.showProgress(false);
                    EmojiSearchAdapter.this.searchWas = true;
                    if (EmojiView.this.emojiGridView.getAdapter() != EmojiView.this.emojiSearchAdapter) {
                        EmojiView.this.emojiGridView.setAdapter(EmojiView.this.emojiSearchAdapter);
                    }
                    EmojiSearchAdapter.this.result.clear();
                    EmojiSearchAdapter.this.result.addAll(EmojiSearchAdapter.this.resultPre);
                    EmojiSearchAdapter.this.resultGlobal.clear();
                    EmojiSearchAdapter.this.resultGlobal.addAll(arrayList);
                    EmojiSearchAdapter.this.packs.clear();
                    LongSparseIntArray longSparseIntArray = new LongSparseIntArray();
                    int size = arrayList2.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList2.get(i2);
                        i2++;
                        EmojiPackInfo emojiPackInfo = (EmojiPackInfo) obj;
                        if (longSparseIntArray.indexOfKey(emojiPackInfo.set.f1280id) < 0) {
                            longSparseIntArray.append(emojiPackInfo.set.f1280id, 1);
                            EmojiSearchAdapter.this.packs.add(emojiPackInfo);
                        }
                    }
                    int size2 = arrayList3.size();
                    while (i < size2) {
                        Object obj2 = arrayList3.get(i);
                        i++;
                        EmojiPackInfo emojiPackInfo2 = (EmojiPackInfo) obj2;
                        if (longSparseIntArray.indexOfKey(emojiPackInfo2.set.f1280id) < 0) {
                            longSparseIntArray.append(emojiPackInfo2.set.f1280id, 1);
                            EmojiSearchAdapter.this.packs.add(emojiPackInfo2);
                        }
                    }
                    EmojiSearchAdapter.this.notifyDataSetChanged();
                }
            }

            public static /* synthetic */ void $r8$lambda$aYmFiSEV_zPNdYdH42thmnPihWA(LinkedHashSet linkedHashSet, Runnable runnable, TLRPC.TL_emojiList tL_emojiList) {
                if (tL_emojiList != null) {
                    linkedHashSet.addAll(tL_emojiList.document_id);
                }
                runnable.run();
            }
        }

        public void searchEmoji(final Runnable runnable, final ArrayList<MediaDataController.KeywordResult> arrayList, boolean z) {
            String[] strArr = EmojiView.this.lastSearchKeyboardLanguage;
            String str = (strArr == null || strArr.length == 0) ? _UrlKt.FRAGMENT_ENCODE_SET : strArr[0];
            final String str2 = this.lastSearchEmojiString;
            if (str2 == null) {
                return;
            }
            MediaDataController.getInstance(EmojiView.this.currentAccount).searchStickers(true, str, str2, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$$ExternalSyntheticLambda2
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$searchEmoji$0(str2, arrayList, runnable, (ArrayList) obj);
                }
            }, z);
        }

        public /* synthetic */ void lambda$searchEmoji$0(String str, ArrayList arrayList, Runnable runnable, ArrayList arrayList2) {
            if (str.equals(this.lastSearchEmojiString)) {
                AnimatedEmojiDrawable.getDocumentFetcher(EmojiView.this.currentAccount).putDocuments(arrayList2);
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    MediaDataController.KeywordResult keywordResult = new MediaDataController.KeywordResult();
                    keywordResult.emoji = "animated_" + ((TLRPC.Document) obj).f1253id;
                    keywordResult.keyword = null;
                    arrayList.add(keywordResult);
                }
                runnable.run();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            this.foundPacksListView.adapter.update(false);
            super.notifyDataSetChanged();
        }
    }

    public class EmojiPagesAdapter extends PagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
        public /* synthetic */ EmojiPagesAdapter(EmojiView emojiView, EmojiViewIA emojiViewIA) {
            this();
        }

        @Override // org.telegram.ui.Components.PagerSlidingTabStrip.IconTabProvider
        public void customOnDraw(Canvas canvas, View view, int i) {
        }

        @Override // org.telegram.ui.Components.PagerSlidingTabStrip.IconTabProvider
        public Drawable getPageIconDrawable(int i) {
            return null;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        private EmojiPagesAdapter() {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        @Override // org.telegram.ui.Components.PagerSlidingTabStrip.IconTabProvider
        public boolean canScrollToTab(int i) {
            if (i == 1 || i == 2) {
                EmojiView emojiView = EmojiView.this;
                if (emojiView.stickersBanned) {
                    emojiView.showStickerBanHint(true, false, i == 1);
                    return false;
                }
            }
            if (i == 0) {
                EmojiView emojiView2 = EmojiView.this;
                if (emojiView2.emojiBanned) {
                    emojiView2.showStickerBanHint(true, true, false);
                    return false;
                }
            }
            return true;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return EmojiView.this.currentTabs.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public CharSequence getPageTitle(int i) {
            if (i == 0) {
                return LocaleController.getString(C2797R.string.Emoji);
            }
            if (i == 1) {
                return LocaleController.getString(C2797R.string.AccDescrGIFs);
            }
            if (i != 2) {
                return null;
            }
            return LocaleController.getString(C2797R.string.AccDescrStickers);
        }

        @Override // org.telegram.ui.Components.PagerSlidingTabStrip.IconTabProvider
        public int getTabPadding(int i) {
            return AndroidUtilities.m1036dp(i == 1 ? 12.0f : 18.0f);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View view = ((Tab) EmojiView.this.currentTabs.get(i)).view;
            viewGroup.addView(view);
            return view;
        }
    }

    public class GifAdapter extends RecyclerListView.SelectionAdapter {
        private boolean addSearch;
        private TLRPC.User bot;
        private final Context context;
        private int firstResultItem;
        private int itemsCount;
        private String lastSearchImageString;
        private boolean lastSearchIsEmoji;
        private final int maxRecentRowsCount;
        private String nextSearchOffset;
        private final GifProgressEmptyView progressEmptyView;
        private int recentItemsCount;
        private int reqId;
        private ArrayList<TLRPC.BotInlineResult> results;
        private HashMap<String, TLRPC.BotInlineResult> resultsMap;
        private boolean searchEndReached;
        private Runnable searchRunnable;
        private boolean searchingUser;
        private boolean showTrendingWhenSearchEmpty;
        private int trendingSectionItem;
        private final boolean withRecent;

        public GifAdapter(EmojiView emojiView, Context context) {
            this(context, false, 0);
        }

        public GifAdapter(EmojiView emojiView, Context context, boolean z) {
            this(context, z, z ? Integer.MAX_VALUE : 0);
        }

        public GifAdapter(Context context, boolean z, int i) {
            this.results = new ArrayList<>();
            this.resultsMap = new HashMap<>();
            this.trendingSectionItem = -1;
            this.firstResultItem = -1;
            this.context = context;
            this.withRecent = z;
            this.maxRecentRowsCount = i;
            this.progressEmptyView = z ? null : EmojiView.this.new GifProgressEmptyView(context);
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.itemsCount;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == 0 && this.addSearch) {
                return 1;
            }
            boolean z = this.withRecent;
            if (z && i == this.trendingSectionItem) {
                return 2;
            }
            return (z || !this.results.isEmpty()) ? 0 : 3;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            View view2;
            if (i != 0) {
                if (i == 1) {
                    View view3 = new View(EmojiView.this.getContext());
                    view3.setLayoutParams(new RecyclerView.LayoutParams(-1, EmojiView.this.searchFieldHeight));
                    view2 = view3;
                } else if (i == 2) {
                    StickerSetNameCell stickerSetNameCell = new StickerSetNameCell(this.context, false, EmojiView.this.resourcesProvider, EmojiView.this.glassDesign);
                    stickerSetNameCell.setText(LocaleController.getString(C2797R.string.FeaturedGifs), 0);
                    RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(-1, -2);
                    ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = AndroidUtilities.m1036dp(2.5f);
                    ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = AndroidUtilities.m1036dp(5.5f);
                    stickerSetNameCell.setLayoutParams(layoutParams);
                    view2 = stickerSetNameCell;
                } else {
                    GifProgressEmptyView gifProgressEmptyView = this.progressEmptyView;
                    gifProgressEmptyView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                    view = gifProgressEmptyView;
                }
                view = view2;
            } else {
                ContextLinkCell contextLinkCell = new ContextLinkCell(this.context);
                contextLinkCell.setIsKeyboard(true);
                contextLinkCell.setCanPreviewGif(true);
                view = contextLinkCell;
            }
            return new RecyclerListView.Holder(view);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder.getItemViewType() != 0) {
                return;
            }
            ContextLinkCell contextLinkCell = (ContextLinkCell) viewHolder.itemView;
            int i2 = this.firstResultItem;
            if (i2 >= 0 && i >= i2) {
                contextLinkCell.setLink(this.results.get(i - i2), this.bot, true, false, false, true);
            } else {
                contextLinkCell.setGif((TLRPC.Document) EmojiView.this.recentGifs.get(i - (this.addSearch ? 1 : 0)), false);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            updateRecentItemsCount();
            updateItems();
            super.notifyDataSetChanged();
        }

        private void updateItems() {
            this.trendingSectionItem = -1;
            this.firstResultItem = -1;
            this.itemsCount = 0;
            if (this.addSearch) {
                this.itemsCount = 1;
            }
            if (this.withRecent) {
                this.itemsCount += this.recentItemsCount;
            }
            boolean zIsEmpty = this.results.isEmpty();
            boolean z = this.withRecent;
            if (zIsEmpty) {
                if (z) {
                    return;
                }
                this.itemsCount++;
                return;
            }
            if (z && this.recentItemsCount > 0) {
                int i = this.itemsCount;
                this.itemsCount = i + 1;
                this.trendingSectionItem = i;
            }
            int i2 = this.itemsCount;
            this.firstResultItem = i2;
            this.itemsCount = i2 + this.results.size();
        }

        private void updateRecentItemsCount() {
            int i;
            if (!this.withRecent || (i = this.maxRecentRowsCount) == 0) {
                return;
            }
            EmojiView emojiView = EmojiView.this;
            if (i == Integer.MAX_VALUE) {
                this.recentItemsCount = emojiView.recentGifs.size();
                return;
            }
            if (emojiView.gifGridView.getMeasuredWidth() == 0) {
                return;
            }
            int measuredWidth = EmojiView.this.gifGridView.getMeasuredWidth();
            int spanCount = EmojiView.this.gifLayoutManager.getSpanCount();
            int iM1036dp = AndroidUtilities.m1036dp(100.0f);
            this.recentItemsCount = 0;
            int size = EmojiView.this.recentGifs.size();
            int i2 = spanCount;
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < size; i5++) {
                Size sizeFixSize = EmojiView.this.gifLayoutManager.fixSize(EmojiView.this.gifLayoutManager.getSizeForItem((TLRPC.Document) EmojiView.this.recentGifs.get(i5)));
                int iMin = Math.min(spanCount, (int) Math.floor(spanCount * (((sizeFixSize.width / sizeFixSize.height) * iM1036dp) / measuredWidth)));
                if (i2 < iMin) {
                    this.recentItemsCount += i3;
                    i4++;
                    if (i4 == this.maxRecentRowsCount) {
                        break;
                    }
                    i2 = spanCount;
                    i3 = 0;
                }
                i3++;
                i2 -= iMin;
            }
            if (i4 < this.maxRecentRowsCount) {
                this.recentItemsCount += i3;
            }
        }

        public void loadTrendingGifs() {
            search(_UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, true, true, true);
        }

        private void searchBotUser() {
            if (this.searchingUser) {
                return;
            }
            this.searchingUser = true;
            TLRPC.TL_contacts_resolveUsername tL_contacts_resolveUsername = new TLRPC.TL_contacts_resolveUsername();
            tL_contacts_resolveUsername.username = MessagesController.getInstance(EmojiView.this.currentAccount).gifSearchBot;
            ConnectionsManager.getInstance(EmojiView.this.currentAccount).sendRequest(tL_contacts_resolveUsername, new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$GifAdapter$$ExternalSyntheticLambda1
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$searchBotUser$1(tLObject, tL_error);
                }
            });
        }

        public /* synthetic */ void lambda$searchBotUser$1(final TLObject tLObject, TLRPC.TL_error tL_error) {
            if (tLObject != null) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$GifAdapter$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$searchBotUser$0(tLObject);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$searchBotUser$0(TLObject tLObject) {
            TLRPC.TL_contacts_resolvedPeer tL_contacts_resolvedPeer = (TLRPC.TL_contacts_resolvedPeer) tLObject;
            MessagesController.getInstance(EmojiView.this.currentAccount).putUsers(tL_contacts_resolvedPeer.users, false);
            MessagesController.getInstance(EmojiView.this.currentAccount).putChats(tL_contacts_resolvedPeer.chats, false);
            MessagesStorage.getInstance(EmojiView.this.currentAccount).putUsersAndChats(tL_contacts_resolvedPeer.users, tL_contacts_resolvedPeer.chats, true, true);
            String str = this.lastSearchImageString;
            this.lastSearchImageString = null;
            search(str, _UrlKt.FRAGMENT_ENCODE_SET, false);
        }

        public void search(String str) {
            search(str, true);
        }

        public void search(final String str, boolean z) {
            if (this.withRecent) {
                return;
            }
            int i = this.reqId;
            if (i != 0) {
                if (i >= 0) {
                    ConnectionsManager.getInstance(EmojiView.this.currentAccount).cancelRequest(this.reqId, true);
                }
                this.reqId = 0;
            }
            this.lastSearchIsEmoji = false;
            GifProgressEmptyView gifProgressEmptyView = this.progressEmptyView;
            if (gifProgressEmptyView != null) {
                gifProgressEmptyView.setLoadingState(false);
            }
            Runnable runnable = this.searchRunnable;
            if (runnable != null) {
                AndroidUtilities.cancelRunOnUIThread(runnable);
            }
            if (TextUtils.isEmpty(str)) {
                this.lastSearchImageString = null;
                if (this.showTrendingWhenSearchEmpty) {
                    loadTrendingGifs();
                    return;
                }
                int currentPosition = EmojiView.this.gifTabs.getCurrentPosition();
                if (currentPosition == EmojiView.this.gifRecentTabNum || currentPosition == EmojiView.this.gifTrendingTabNum) {
                    if (EmojiView.this.gifGridView.getAdapter() != EmojiView.this.gifAdapter) {
                        EmojiView.this.gifGridView.setAdapter(EmojiView.this.gifAdapter);
                        return;
                    }
                    return;
                }
                searchEmoji(MessagesController.getInstance(EmojiView.this.currentAccount).gifSearchEmojies.get(currentPosition - EmojiView.this.gifFirstEmojiTabNum));
                return;
            }
            String lowerCase = str.toLowerCase();
            this.lastSearchImageString = lowerCase;
            if (TextUtils.isEmpty(lowerCase)) {
                return;
            }
            Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.Components.EmojiView$GifAdapter$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$search$2(str);
                }
            };
            this.searchRunnable = runnable2;
            AndroidUtilities.runOnUIThread(runnable2, z ? 300L : 0L);
        }

        public /* synthetic */ void lambda$search$2(String str) {
            search(str, _UrlKt.FRAGMENT_ENCODE_SET, true);
        }

        public void searchEmoji(String str) {
            if (this.lastSearchIsEmoji && TextUtils.equals(this.lastSearchImageString, str)) {
                EmojiView.this.gifLayoutManager.scrollToPositionWithOffset(0, 0);
            } else {
                search(str, _UrlKt.FRAGMENT_ENCODE_SET, true, true, true);
            }
        }

        public void search(String str, String str2, boolean z) {
            search(str, str2, z, false, false);
        }

        public void search(final String str, final String str2, final boolean z, final boolean z2, final boolean z3) {
            int i = this.reqId;
            if (i != 0) {
                if (i >= 0) {
                    ConnectionsManager.getInstance(EmojiView.this.currentAccount).cancelRequest(this.reqId, true);
                }
                this.reqId = 0;
            }
            this.lastSearchImageString = str;
            this.lastSearchIsEmoji = z2;
            GifProgressEmptyView gifProgressEmptyView = this.progressEmptyView;
            if (gifProgressEmptyView != null) {
                gifProgressEmptyView.setLoadingState(z2);
            }
            TLObject userOrChat = MessagesController.getInstance(EmojiView.this.currentAccount).getUserOrChat(MessagesController.getInstance(EmojiView.this.currentAccount).gifSearchBot);
            if (!(userOrChat instanceof TLRPC.User)) {
                if (z) {
                    searchBotUser();
                    if (this.withRecent) {
                        return;
                    }
                    EmojiView.this.gifSearchField.showProgress(true);
                    return;
                }
                return;
            }
            if (!this.withRecent && TextUtils.isEmpty(str2)) {
                EmojiView.this.gifSearchField.showProgress(true);
            }
            this.bot = (TLRPC.User) userOrChat;
            final String str3 = "gif_search_" + str + "_" + str2;
            RequestDelegate requestDelegate = new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$GifAdapter$$ExternalSyntheticLambda0
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$search$4(str, str2, z, z2, z3, str3, tLObject, tL_error);
                }
            };
            if (!z3 && !this.withRecent && z2 && TextUtils.isEmpty(str2)) {
                this.results.clear();
                this.resultsMap.clear();
                if (EmojiView.this.gifGridView.getAdapter() != this) {
                    EmojiView.this.gifGridView.setAdapter(this);
                }
                notifyDataSetChanged();
                EmojiView.this.scrollGifsToTop();
            }
            if (z3 && EmojiView.this.gifCache.containsKey(str3)) {
                lambda$search$3(str, str2, z, z2, true, str3, (TLObject) EmojiView.this.gifCache.get(str3));
                return;
            }
            if (EmojiView.this.gifSearchPreloader.isLoading(str3)) {
                return;
            }
            if (z3) {
                this.reqId = -1;
                MessagesStorage.getInstance(EmojiView.this.currentAccount).getBotCache(str3, requestDelegate);
                return;
            }
            TLRPC.TL_messages_getInlineBotResults tL_messages_getInlineBotResults = new TLRPC.TL_messages_getInlineBotResults();
            tL_messages_getInlineBotResults.query = str == null ? _UrlKt.FRAGMENT_ENCODE_SET : str;
            tL_messages_getInlineBotResults.bot = MessagesController.getInstance(EmojiView.this.currentAccount).getInputUser(this.bot);
            tL_messages_getInlineBotResults.offset = str2;
            tL_messages_getInlineBotResults.peer = new TLRPC.TL_inputPeerEmpty();
            this.reqId = ConnectionsManager.getInstance(EmojiView.this.currentAccount).sendRequest(tL_messages_getInlineBotResults, requestDelegate);
        }

        public /* synthetic */ void lambda$search$4(final String str, final String str2, final boolean z, final boolean z2, final boolean z3, final String str3, final TLObject tLObject, TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$GifAdapter$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$search$3(str, str2, z, z2, z3, str3, tLObject);
                }
            });
        }

        /* JADX INFO: renamed from: processResponse */
        public void lambda$search$3(String str, String str2, boolean z, boolean z2, boolean z3, String str3, TLObject tLObject) {
            if (str == null || !str.equals(this.lastSearchImageString)) {
                return;
            }
            this.reqId = 0;
            if (z3 && (!(tLObject instanceof TLRPC.messages_BotResults) || ((TLRPC.messages_BotResults) tLObject).results.isEmpty())) {
                search(str, str2, z, z2, false);
                return;
            }
            if (!this.withRecent && TextUtils.isEmpty(str2)) {
                this.results.clear();
                this.resultsMap.clear();
                EmojiView.this.gifSearchField.showProgress(false);
            }
            if (tLObject instanceof TLRPC.messages_BotResults) {
                int size = this.results.size();
                TLRPC.messages_BotResults messages_botresults = (TLRPC.messages_BotResults) tLObject;
                if (!EmojiView.this.gifCache.containsKey(str3)) {
                    EmojiView.this.gifCache.put(str3, messages_botresults);
                }
                if (!z3 && messages_botresults.cache_time != 0) {
                    MessagesStorage.getInstance(EmojiView.this.currentAccount).saveBotCache(str3, messages_botresults);
                }
                this.nextSearchOffset = messages_botresults.next_offset;
                int i = 0;
                for (int i2 = 0; i2 < messages_botresults.results.size(); i2++) {
                    TLRPC.BotInlineResult botInlineResult = messages_botresults.results.get(i2);
                    if (!this.resultsMap.containsKey(botInlineResult.f1243id)) {
                        botInlineResult.query_id = messages_botresults.query_id;
                        this.results.add(botInlineResult);
                        this.resultsMap.put(botInlineResult.f1243id, botInlineResult);
                        i++;
                    }
                }
                this.searchEndReached = size == this.results.size() || TextUtils.isEmpty(this.nextSearchOffset);
                if (i != 0) {
                    if (!z2 || size != 0) {
                        updateItems();
                        if (this.withRecent) {
                            int i3 = this.recentItemsCount;
                            if (size != 0) {
                                notifyItemChanged(i3 + (EmojiView.this.gifAdapter.addSearch ? 1 : 0) + size);
                                notifyItemRangeInserted(this.recentItemsCount + (EmojiView.this.gifAdapter.addSearch ? 1 : 0) + size + 1, i);
                            } else {
                                notifyItemRangeInserted(i3 + (EmojiView.this.gifAdapter.addSearch ? 1 : 0), i + 1);
                            }
                        } else {
                            if (size != 0) {
                                notifyItemChanged(size);
                            }
                            notifyItemRangeInserted(size + (EmojiView.this.gifAdapter.addSearch ? 1 : 0), i);
                        }
                    } else {
                        notifyDataSetChanged();
                    }
                } else if (this.results.isEmpty()) {
                    notifyDataSetChanged();
                }
            } else {
                notifyDataSetChanged();
            }
            if (this.withRecent) {
                return;
            }
            if (EmojiView.this.gifGridView.getAdapter() != this) {
                EmojiView.this.gifGridView.setAdapter(this);
            }
            if (z2 && !TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
                EmojiView.this.scrollGifsToTop();
            }
        }
    }

    public class GifSearchPreloader {
        private final List<String> loadingKeys;

        public /* synthetic */ GifSearchPreloader(EmojiView emojiView, EmojiViewIA emojiViewIA) {
            this();
        }

        private GifSearchPreloader() {
            this.loadingKeys = new ArrayList();
        }

        public boolean isLoading(String str) {
            return this.loadingKeys.contains(str);
        }

        public void preload(String str) {
            preload(str, _UrlKt.FRAGMENT_ENCODE_SET, true);
        }

        private void preload(final String str, final String str2, final boolean z) {
            final String str3 = "gif_search_" + str + "_" + str2;
            if (z && EmojiView.this.gifCache.containsKey(str3)) {
                return;
            }
            RequestDelegate requestDelegate = new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$GifSearchPreloader$$ExternalSyntheticLambda0
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$preload$1(str, str2, z, str3, tLObject, tL_error);
                }
            };
            if (z) {
                this.loadingKeys.add(str3);
                MessagesStorage.getInstance(EmojiView.this.currentAccount).getBotCache(str3, requestDelegate);
                return;
            }
            MessagesController messagesController = MessagesController.getInstance(EmojiView.this.currentAccount);
            TLObject userOrChat = messagesController.getUserOrChat(messagesController.gifSearchBot);
            if (userOrChat instanceof TLRPC.User) {
                this.loadingKeys.add(str3);
                TLRPC.TL_messages_getInlineBotResults tL_messages_getInlineBotResults = new TLRPC.TL_messages_getInlineBotResults();
                tL_messages_getInlineBotResults.query = str == null ? _UrlKt.FRAGMENT_ENCODE_SET : str;
                tL_messages_getInlineBotResults.bot = messagesController.getInputUser((TLRPC.User) userOrChat);
                tL_messages_getInlineBotResults.offset = str2;
                tL_messages_getInlineBotResults.peer = new TLRPC.TL_inputPeerEmpty();
                ConnectionsManager.getInstance(EmojiView.this.currentAccount).sendRequest(tL_messages_getInlineBotResults, requestDelegate, 2);
            }
        }

        public /* synthetic */ void lambda$preload$1(final String str, final String str2, final boolean z, final String str3, final TLObject tLObject, TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$GifSearchPreloader$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$preload$0(str, str2, z, str3, tLObject);
                }
            });
        }

        /* JADX INFO: renamed from: processResponse */
        public void lambda$preload$0(String str, String str2, boolean z, String str3, TLObject tLObject) {
            this.loadingKeys.remove(str3);
            if (EmojiView.this.gifSearchAdapter.lastSearchIsEmoji && EmojiView.this.gifSearchAdapter.lastSearchImageString.equals(str)) {
                EmojiView.this.gifSearchAdapter.lambda$search$3(str, str2, false, true, z, str3, tLObject);
                return;
            }
            if (z && (!(tLObject instanceof TLRPC.messages_BotResults) || ((TLRPC.messages_BotResults) tLObject).results.isEmpty())) {
                preload(str, str2, false);
            } else {
                if (!(tLObject instanceof TLRPC.messages_BotResults) || EmojiView.this.gifCache.containsKey(str3)) {
                    return;
                }
                EmojiView.this.gifCache.put(str3, (TLRPC.messages_BotResults) tLObject);
            }
        }
    }

    public class GifLayoutManager extends ExtendedGridLayoutManager {
        private Size size;

        public GifLayoutManager(Context context) {
            super(context, 100, true);
            this.size = new Size();
            setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.Components.EmojiView.GifLayoutManager.1
                final /* synthetic */ EmojiView val$this$0;

                public C43321(EmojiView emojiView) {
                    emojiView = emojiView;
                }

                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i) {
                    if ((i == 0 && EmojiView.this.gifAdapter.addSearch) || (EmojiView.this.gifGridView.getAdapter() == EmojiView.this.gifSearchAdapter && EmojiView.this.gifSearchAdapter.results.isEmpty())) {
                        return GifLayoutManager.this.getSpanCount();
                    }
                    GifLayoutManager gifLayoutManager = GifLayoutManager.this;
                    return gifLayoutManager.getSpanSizeForItem(i - (EmojiView.this.gifAdapter.addSearch ? 1 : 0));
                }
            });
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$GifLayoutManager$1 */
        public class C43321 extends GridLayoutManager.SpanSizeLookup {
            final /* synthetic */ EmojiView val$this$0;

            public C43321(EmojiView emojiView) {
                emojiView = emojiView;
            }

            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i) {
                if ((i == 0 && EmojiView.this.gifAdapter.addSearch) || (EmojiView.this.gifGridView.getAdapter() == EmojiView.this.gifSearchAdapter && EmojiView.this.gifSearchAdapter.results.isEmpty())) {
                    return GifLayoutManager.this.getSpanCount();
                }
                GifLayoutManager gifLayoutManager = GifLayoutManager.this;
                return gifLayoutManager.getSpanSizeForItem(i - (EmojiView.this.gifAdapter.addSearch ? 1 : 0));
            }
        }

        @Override // org.telegram.p035ui.Components.ExtendedGridLayoutManager
        public Size getSizeForItem(int i) {
            ArrayList<TLRPC.DocumentAttribute> arrayList;
            TLRPC.Document document;
            RecyclerView.Adapter adapter = EmojiView.this.gifGridView.getAdapter();
            GifAdapter gifAdapter = EmojiView.this.gifAdapter;
            EmojiView emojiView = EmojiView.this;
            TLRPC.Document document2 = null;
            arrayList = null;
            ArrayList<TLRPC.DocumentAttribute> arrayList2 = null;
            if (adapter == gifAdapter) {
                int i2 = emojiView.gifAdapter.recentItemsCount;
                EmojiView emojiView2 = EmojiView.this;
                if (i > i2) {
                    TLRPC.BotInlineResult botInlineResult = (TLRPC.BotInlineResult) emojiView2.gifAdapter.results.get((i - EmojiView.this.gifAdapter.recentItemsCount) - 1);
                    document = botInlineResult.document;
                    if (document != null) {
                        arrayList2 = document.attributes;
                    } else {
                        TLRPC.WebDocument webDocument = botInlineResult.content;
                        if (webDocument != null) {
                            arrayList2 = webDocument.attributes;
                        } else {
                            TLRPC.WebDocument webDocument2 = botInlineResult.thumb;
                            if (webDocument2 != null) {
                                arrayList2 = webDocument2.attributes;
                            }
                        }
                    }
                    arrayList = arrayList2;
                    document2 = document;
                } else {
                    if (i == emojiView2.gifAdapter.recentItemsCount) {
                        return null;
                    }
                    document2 = (TLRPC.Document) EmojiView.this.recentGifs.get(i);
                    arrayList = document2.attributes;
                }
            } else if (emojiView.gifSearchAdapter.results.isEmpty()) {
                arrayList = null;
            } else {
                TLRPC.BotInlineResult botInlineResult2 = (TLRPC.BotInlineResult) EmojiView.this.gifSearchAdapter.results.get(i);
                document = botInlineResult2.document;
                if (document != null) {
                    arrayList2 = document.attributes;
                } else {
                    TLRPC.WebDocument webDocument3 = botInlineResult2.content;
                    if (webDocument3 != null) {
                        arrayList2 = webDocument3.attributes;
                    } else {
                        TLRPC.WebDocument webDocument4 = botInlineResult2.thumb;
                        if (webDocument4 != null) {
                            arrayList2 = webDocument4.attributes;
                        }
                    }
                }
                arrayList = arrayList2;
                document2 = document;
            }
            return getSizeForItem(document2, arrayList);
        }

        @Override // org.telegram.p035ui.Components.ExtendedGridLayoutManager
        public int getFlowItemCount() {
            if (EmojiView.this.gifGridView.getAdapter() == EmojiView.this.gifSearchAdapter && EmojiView.this.gifSearchAdapter.results.isEmpty()) {
                return 0;
            }
            return getItemCount() - 1;
        }

        public Size getSizeForItem(TLRPC.Document document) {
            return getSizeForItem(document, document.attributes);
        }

        public Size getSizeForItem(TLRPC.Document document, List<TLRPC.DocumentAttribute> list) {
            TLRPC.PhotoSize closestPhotoSizeWithSize;
            int i;
            int i2;
            Size size = this.size;
            size.height = 100.0f;
            size.width = 100.0f;
            if (document != null && (closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(document.thumbs, 90)) != null && (i = closestPhotoSizeWithSize.f1278w) != 0 && (i2 = closestPhotoSizeWithSize.f1277h) != 0) {
                Size size2 = this.size;
                size2.width = i;
                size2.height = i2;
            }
            if (list != null) {
                for (int i3 = 0; i3 < list.size(); i3++) {
                    TLRPC.DocumentAttribute documentAttribute = list.get(i3);
                    if ((documentAttribute instanceof TLRPC.TL_documentAttributeImageSize) || (documentAttribute instanceof TLRPC.TL_documentAttributeVideo)) {
                        Size size3 = this.size;
                        size3.width = documentAttribute.f1256w;
                        size3.height = documentAttribute.f1255h;
                        break;
                    }
                }
            }
            return this.size;
        }
    }

    public class GifProgressEmptyView extends FrameLayout {
        private final ImageView imageView;
        private boolean loadingState;
        private final RadialProgressView progressView;
        private final TextView textView;

        public GifProgressEmptyView(Context context) {
            super(context);
            ImageView imageView = new ImageView(getContext());
            this.imageView = imageView;
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setImageResource(C2797R.drawable.gif_empty);
            int i = Theme.key_chat_emojiPanelEmptyText;
            imageView.setColorFilter(new PorterDuffColorFilter(EmojiView.this.getThemedColor(i), PorterDuff.Mode.MULTIPLY));
            addView(imageView, LayoutHelper.createFrame(-2, -2.0f, 17, 0.0f, 8.0f, 0.0f, 0.0f));
            TextView textView = new TextView(getContext());
            this.textView = textView;
            textView.setText(LocaleController.getString(C2797R.string.NoGIFsFound));
            textView.setTextSize(1, 16.0f);
            textView.setTextColor(EmojiView.this.getThemedColor(i));
            addView(textView, LayoutHelper.createFrame(-2, -2.0f, 17, 0.0f, 42.0f, 0.0f, 0.0f));
            RadialProgressView radialProgressView = new RadialProgressView(context, EmojiView.this.resourcesProvider);
            this.progressView = radialProgressView;
            radialProgressView.setVisibility(8);
            radialProgressView.setProgressColor(EmojiView.this.getThemedColor(Theme.key_progressCircle));
            addView(radialProgressView, LayoutHelper.createFrame(-2, -2, 17));
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            int iM1036dp;
            int measuredHeight = EmojiView.this.gifGridView.getMeasuredHeight();
            if (!this.loadingState) {
                iM1036dp = (int) ((((measuredHeight - EmojiView.this.searchFieldHeight) - AndroidUtilities.m1036dp(8.0f)) / 3) * 1.7f);
            } else {
                iM1036dp = measuredHeight - AndroidUtilities.m1036dp(80.0f);
            }
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(iM1036dp, TLObject.FLAG_30));
        }

        public void setLoadingState(boolean z) {
            if (this.loadingState != z) {
                this.loadingState = z;
                this.imageView.setVisibility(z ? 8 : 0);
                this.textView.setVisibility(z ? 8 : 0);
                this.progressView.setVisibility(z ? 0 : 8);
            }
        }
    }

    public class StickersSearchGridAdapter extends RecyclerListView.SelectionAdapter {
        private Context context;
        private int emojiSearchId;
        private FoundEmojiPacksRecyclerView foundPacksListView;
        private boolean isCompleted;
        private int reqId;
        private int reqId2;
        private String searchQuery;
        private long selectedPackId;
        private TLRPC.StickerSet selectedPackStickerSet;
        private ArrayList<TLRPC.Document> selectedPackStickers;
        private int totalItems;
        private SparseArray<Object> rowStartPack = new SparseArray<>();
        private SparseArray<Object> cache = new SparseArray<>();
        private SparseArray<Object> cacheParent = new SparseArray<>();
        private SparseIntArray positionToRow = new SparseIntArray();
        private SparseArray<String> positionToEmoji = new SparseArray<>();
        private ArrayList<TLRPC.TL_messages_stickerSet> localPacks = new ArrayList<>();
        private HashMap<TLRPC.TL_messages_stickerSet, Boolean> localPacksByShortName = new HashMap<>();
        private HashMap<TLRPC.TL_messages_stickerSet, Integer> localPacksByName = new HashMap<>();
        private HashMap<ArrayList<TLRPC.Document>, String> emojiStickers = new HashMap<>();
        private ArrayList<ArrayList<TLRPC.Document>> emojiArrays = new ArrayList<>();
        private ArrayList<EmojiPackInfo> foundEmojiPacks = new ArrayList<>();
        private ArrayList<TLRPC.Document> globalSearchArray = new ArrayList<>();
        private final SearchRunnable searchRunnable = new C43411();
        private int foundPacksRow = -1;

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1 */
        public class C43411 implements SearchRunnable {
            int lastId;
            String query;
            final ArrayList<TLRPC.TL_messages_stickerSet> localPacks = new ArrayList<>();
            final HashMap<TLRPC.TL_messages_stickerSet, Boolean> localPacksByShortName = new HashMap<>();
            final HashMap<TLRPC.TL_messages_stickerSet, Integer> localPacksByName = new HashMap<>();
            final HashMap<ArrayList<TLRPC.Document>, String> emojiStickers = new HashMap<>();
            final ArrayList<ArrayList<TLRPC.Document>> emojiArrays = new ArrayList<>();
            final ArrayList<EmojiPackInfo> foundEmojiPacks = new ArrayList<>();
            final ArrayList<TLRPC.Document> emojiStickersArray = new ArrayList<>(0);
            final ArrayList<TLRPC.Document> emojiStickersArray2 = new ArrayList<>(0);
            final LongSparseArray<TLRPC.Document> emojiStickersMap = new LongSparseArray<>(0);

            public C43411() {
            }

            public void searchFinish() {
                if (StickersSearchGridAdapter.this.emojiSearchId != this.lastId) {
                    return;
                }
                this.emojiArrays.remove(this.emojiStickersArray);
                StickersSearchGridAdapter.this.localPacks = this.localPacks;
                StickersSearchGridAdapter.this.localPacksByShortName = this.localPacksByShortName;
                StickersSearchGridAdapter.this.localPacksByName = this.localPacksByName;
                StickersSearchGridAdapter.this.emojiStickers = this.emojiStickers;
                StickersSearchGridAdapter.this.emojiArrays = this.emojiArrays;
                StickersSearchGridAdapter.this.foundEmojiPacks = this.foundEmojiPacks;
                StickersSearchGridAdapter.this.globalSearchArray = new ArrayList(this.emojiStickersArray);
                EmojiView.this.stickersSearchField.showProgress(false);
                if (EmojiView.this.stickersGridView.getAdapter() != EmojiView.this.stickersSearchGridAdapter) {
                    EmojiView.this.stickersGridView.setAdapter(EmojiView.this.stickersSearchGridAdapter);
                }
                StickersSearchGridAdapter.this.notifyDataSetChanged();
            }

            /* JADX WARN: Removed duplicated region for block: B:56:0x0045  */
            /* JADX WARN: Removed duplicated region for block: B:66:0x0085  */
            /* JADX WARN: Removed duplicated region for block: B:82:0x00a0 A[SYNTHETIC] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void addFromAllStickers(java.lang.Runnable r11) {
                /*
                    Method dump skipped, instruction units count: 236
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.StickersSearchGridAdapter.C43411.addFromAllStickers(java.lang.Runnable):void");
            }

            public void addFromSuggestions(final Runnable runnable) {
                final HashMap<String, ArrayList<TLRPC.Document>> allStickers = MediaDataController.getInstance(EmojiView.this.currentAccount).getAllStickers();
                if (allStickers != null && !allStickers.isEmpty() && this.query.length() > 1) {
                    String[] currentKeyboardLanguage = AndroidUtilities.getCurrentKeyboardLanguage();
                    if (!Arrays.equals(EmojiView.this.lastSearchKeyboardLanguage, currentKeyboardLanguage)) {
                        MediaDataController.getInstance(EmojiView.this.currentAccount).fetchNewEmojiKeywords(currentKeyboardLanguage);
                    }
                    EmojiView.this.lastSearchKeyboardLanguage = currentKeyboardLanguage;
                    MediaDataController.getInstance(EmojiView.this.currentAccount).getEmojiSuggestions(EmojiView.this.lastSearchKeyboardLanguage, StickersSearchGridAdapter.this.searchQuery, true, new MediaDataController.KeywordResultCallback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda11
                        @Override // org.telegram.messenger.MediaDataController.KeywordResultCallback
                        public final void run(ArrayList arrayList, String str) {
                            this.f$0.lambda$addFromSuggestions$0(allStickers, runnable, arrayList, str);
                        }
                    }, false);
                    return;
                }
                runnable.run();
            }

            public /* synthetic */ void lambda$addFromSuggestions$0(HashMap map, Runnable runnable, ArrayList arrayList, String str) {
                if (StickersSearchGridAdapter.this.emojiSearchId != this.lastId) {
                    return;
                }
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    String str2 = ((MediaDataController.KeywordResult) arrayList.get(i)).emoji;
                    ArrayList<TLRPC.Document> arrayList2 = (ArrayList) map.get(str2);
                    if (arrayList2 != null && !arrayList2.isEmpty() && !this.emojiStickers.containsKey(arrayList2)) {
                        this.emojiStickers.put(arrayList2, str2);
                        this.emojiArrays.add(arrayList2);
                    }
                }
                runnable.run();
            }

            public void addPremiumStickers(Runnable runnable) {
                HashMap<String, ArrayList<TLRPC.Document>> allStickers = MediaDataController.getInstance(EmojiView.this.currentAccount).getAllStickers();
                HashSet hashSet = new HashSet();
                ArrayList arrayList = new ArrayList();
                Iterator<ArrayList<TLRPC.Document>> it = allStickers.values().iterator();
                while (true) {
                    int i = 0;
                    if (!it.hasNext()) {
                        break;
                    }
                    ArrayList<TLRPC.Document> next = it.next();
                    int size = next.size();
                    while (i < size) {
                        TLRPC.Document document = next.get(i);
                        i++;
                        TLRPC.Document document2 = document;
                        if (!hashSet.contains(Long.valueOf(document2.f1253id)) && MessageObject.isPremiumSticker(document2)) {
                            hashSet.add(Long.valueOf(document2.f1253id));
                            arrayList.add(document2);
                            this.emojiStickersMap.put(document2.f1253id, document2);
                        }
                    }
                }
                ArrayList<TLRPC.StickerSetCovered> featuredStickerSets = MediaDataController.getInstance(EmojiView.this.currentAccount).getFeaturedStickerSets();
                int size2 = featuredStickerSets.size();
                int i2 = 0;
                while (i2 < size2) {
                    TLRPC.StickerSetCovered stickerSetCovered = featuredStickerSets.get(i2);
                    i2++;
                    TLRPC.StickerSetCovered stickerSetCovered2 = stickerSetCovered;
                    TLRPC.Document document3 = stickerSetCovered2.cover;
                    if (document3 != null && !hashSet.contains(Long.valueOf(document3.f1253id)) && MessageObject.isPremiumSticker(stickerSetCovered2.cover)) {
                        hashSet.add(Long.valueOf(stickerSetCovered2.cover.f1253id));
                        arrayList.add(stickerSetCovered2.cover);
                        LongSparseArray<TLRPC.Document> longSparseArray = this.emojiStickersMap;
                        TLRPC.Document document4 = stickerSetCovered2.cover;
                        longSparseArray.put(document4.f1253id, document4);
                    }
                    ArrayList<TLRPC.Document> arrayList2 = stickerSetCovered2.covers;
                    if (arrayList2 != null) {
                        int size3 = arrayList2.size();
                        int i3 = 0;
                        while (i3 < size3) {
                            TLRPC.Document document5 = arrayList2.get(i3);
                            i3++;
                            TLRPC.Document document6 = document5;
                            if (!hashSet.contains(Long.valueOf(document6.f1253id)) && MessageObject.isPremiumSticker(document6)) {
                                hashSet.add(Long.valueOf(document6.f1253id));
                                arrayList.add(document6);
                                this.emojiStickersMap.put(document6.f1253id, document6);
                            }
                        }
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.emojiStickersArray2.addAll(arrayList);
                    this.emojiStickers.put(this.emojiStickersArray2, StickersSearchGridAdapter.this.searchQuery);
                    this.emojiArrays.add(this.emojiStickersArray2);
                }
                runnable.run();
            }

            public void addLocalPacks(Runnable runnable) {
                int iIndexOfIgnoreCase;
                int iIndexOfIgnoreCase2;
                ArrayList<TLRPC.TL_messages_stickerSet> stickerSets = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSets(0);
                MessagesController.getInstance(EmojiView.this.currentAccount).filterPremiumStickers(stickerSets);
                int size = stickerSets.size();
                for (int i = 0; i < size; i++) {
                    TLRPC.TL_messages_stickerSet tL_messages_stickerSet = stickerSets.get(i);
                    int iIndexOfIgnoreCase3 = AndroidUtilities.indexOfIgnoreCase(tL_messages_stickerSet.set.title, StickersSearchGridAdapter.this.searchQuery);
                    if (iIndexOfIgnoreCase3 >= 0) {
                        if (iIndexOfIgnoreCase3 == 0 || tL_messages_stickerSet.set.title.charAt(iIndexOfIgnoreCase3 - 1) == ' ') {
                            this.localPacks.add(tL_messages_stickerSet);
                            this.localPacksByName.put(tL_messages_stickerSet, Integer.valueOf(iIndexOfIgnoreCase3));
                        }
                    } else {
                        String str = tL_messages_stickerSet.set.short_name;
                        if (str != null && (iIndexOfIgnoreCase2 = AndroidUtilities.indexOfIgnoreCase(str, StickersSearchGridAdapter.this.searchQuery)) >= 0 && (iIndexOfIgnoreCase2 == 0 || tL_messages_stickerSet.set.short_name.charAt(iIndexOfIgnoreCase2 - 1) == ' ')) {
                            this.localPacks.add(tL_messages_stickerSet);
                            this.localPacksByShortName.put(tL_messages_stickerSet, Boolean.TRUE);
                        }
                    }
                }
                ArrayList<TLRPC.TL_messages_stickerSet> stickerSets2 = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSets(3);
                MessagesController.getInstance(EmojiView.this.currentAccount).filterPremiumStickers(stickerSets2);
                int size2 = stickerSets2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    TLRPC.TL_messages_stickerSet tL_messages_stickerSet2 = stickerSets2.get(i2);
                    int iIndexOfIgnoreCase4 = AndroidUtilities.indexOfIgnoreCase(tL_messages_stickerSet2.set.title, StickersSearchGridAdapter.this.searchQuery);
                    if (iIndexOfIgnoreCase4 >= 0) {
                        if (iIndexOfIgnoreCase4 == 0 || tL_messages_stickerSet2.set.title.charAt(iIndexOfIgnoreCase4 - 1) == ' ') {
                            this.localPacks.add(tL_messages_stickerSet2);
                            this.localPacksByName.put(tL_messages_stickerSet2, Integer.valueOf(iIndexOfIgnoreCase4));
                        }
                    } else {
                        String str2 = tL_messages_stickerSet2.set.short_name;
                        if (str2 != null && (iIndexOfIgnoreCase = AndroidUtilities.indexOfIgnoreCase(str2, StickersSearchGridAdapter.this.searchQuery)) >= 0 && (iIndexOfIgnoreCase == 0 || tL_messages_stickerSet2.set.short_name.charAt(iIndexOfIgnoreCase - 1) == ' ')) {
                            this.localPacks.add(tL_messages_stickerSet2);
                            this.localPacksByShortName.put(tL_messages_stickerSet2, Boolean.TRUE);
                        }
                    }
                }
                runnable.run();
            }

            public void searchStickerSetsByName(final Runnable runnable) {
                MediaDataController.getInstance(EmojiView.this.currentAccount).searchStickerSets(false, this.query, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda9
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$searchStickerSetsByName$1(runnable, (ArrayList) obj);
                    }
                });
            }

            public /* synthetic */ void lambda$searchStickerSetsByName$1(Runnable runnable, ArrayList arrayList) {
                ArrayList<TLRPC.Document> arrayList2;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    TLRPC.StickerSetCovered stickerSetCovered = (TLRPC.StickerSetCovered) obj;
                    if (stickerSetCovered instanceof TLRPC.TL_stickerSetFullCovered) {
                        arrayList2 = ((TLRPC.TL_stickerSetFullCovered) stickerSetCovered).documents;
                    } else if (stickerSetCovered instanceof TLRPC.TL_stickerSetNoCovered) {
                        TLRPC.TL_messages_stickerSet stickerSet = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSet(MediaDataController.getInputStickerSet(stickerSetCovered.set), Integer.valueOf(stickerSetCovered.set.hash), true);
                        arrayList2 = stickerSet != null ? stickerSet.documents : null;
                    } else {
                        arrayList2 = stickerSetCovered.covers;
                    }
                    if (arrayList2 != null && !arrayList2.isEmpty()) {
                        this.foundEmojiPacks.add(new EmojiPackInfo(stickerSetCovered, arrayList2));
                    }
                }
                runnable.run();
            }

            public void searchStickerSets(Runnable runnable) {
                searchStickerSets(runnable, false);
            }

            private void searchStickerSets(final Runnable runnable, final boolean z) {
                String[] currentKeyboardLanguage = AndroidUtilities.getCurrentKeyboardLanguage();
                MediaDataController.getInstance(EmojiView.this.currentAccount).searchStickers(false, (currentKeyboardLanguage == null || currentKeyboardLanguage.length == 0) ? _UrlKt.FRAGMENT_ENCODE_SET : currentKeyboardLanguage[0], this.query, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda12
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$searchStickerSets$2(z, runnable, (ArrayList) obj);
                    }
                }, z);
            }

            public /* synthetic */ void lambda$searchStickerSets$2(boolean z, Runnable runnable, ArrayList arrayList) {
                if (StickersSearchGridAdapter.this.emojiSearchId != this.lastId) {
                    return;
                }
                int i = 0;
                if (z) {
                    int size = this.emojiStickersArray.size();
                    this.emojiStickersArray.clear();
                    StickersSearchGridAdapter.this.isCompleted = size == arrayList.size();
                }
                this.emojiStickersArray.addAll(arrayList);
                int size2 = arrayList.size();
                while (i < size2) {
                    Object obj = arrayList.get(i);
                    i++;
                    TLRPC.Document document = (TLRPC.Document) obj;
                    this.emojiStickersMap.put(document.f1253id, document);
                }
                this.emojiStickers.put(this.emojiStickersArray, StickersSearchGridAdapter.this.searchQuery);
                runnable.run();
            }

            public void searchStickers(final Runnable runnable) {
                if (Emoji.fullyConsistsOfEmojis(StickersSearchGridAdapter.this.searchQuery)) {
                    final TLRPC.TL_messages_getStickers tL_messages_getStickers = new TLRPC.TL_messages_getStickers();
                    tL_messages_getStickers.emoticon = this.query;
                    tL_messages_getStickers.hash = 0L;
                    StickersSearchGridAdapter stickersSearchGridAdapter = StickersSearchGridAdapter.this;
                    stickersSearchGridAdapter.reqId2 = ConnectionsManager.getInstance(EmojiView.this.currentAccount).sendRequest(tL_messages_getStickers, new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda10
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                            this.f$0.lambda$searchStickers$4(tL_messages_getStickers, runnable, tLObject, tL_error);
                        }
                    });
                    return;
                }
                runnable.run();
            }

            public /* synthetic */ void lambda$searchStickers$4(final TLRPC.TL_messages_getStickers tL_messages_getStickers, final Runnable runnable, final TLObject tLObject, TLRPC.TL_error tL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$searchStickers$3(tL_messages_getStickers, tLObject, runnable);
                    }
                });
            }

            public /* synthetic */ void lambda$searchStickers$3(TLRPC.TL_messages_getStickers tL_messages_getStickers, TLObject tLObject, Runnable runnable) {
                if (StickersSearchGridAdapter.this.emojiSearchId != this.lastId) {
                    return;
                }
                StickersSearchGridAdapter.this.reqId2 = 0;
                if (tL_messages_getStickers.emoticon.equals(this.query)) {
                    if (!(tLObject instanceof TLRPC.TL_messages_stickers)) {
                        runnable.run();
                        return;
                    }
                    TLRPC.TL_messages_stickers tL_messages_stickers = (TLRPC.TL_messages_stickers) tLObject;
                    int size = this.emojiStickersArray2.size();
                    int size2 = tL_messages_stickers.stickers.size();
                    for (int i = 0; i < size2; i++) {
                        TLRPC.Document document = tL_messages_stickers.stickers.get(i);
                        if (this.emojiStickersMap.indexOfKey(document.f1253id) < 0) {
                            this.emojiStickersArray2.add(document);
                        }
                    }
                    if (size != this.emojiStickersArray2.size()) {
                        this.emojiStickers.put(this.emojiStickersArray2, StickersSearchGridAdapter.this.searchQuery);
                        if (size == 0) {
                            this.emojiArrays.add(this.emojiStickersArray2);
                        }
                    }
                }
                runnable.run();
            }

            @Override // java.lang.Runnable
            public void run() {
                boolean zIsEmpty = TextUtils.isEmpty(StickersSearchGridAdapter.this.searchQuery);
                StickersSearchGridAdapter stickersSearchGridAdapter = StickersSearchGridAdapter.this;
                if (zIsEmpty) {
                    if (EmojiView.this.stickersGridView.getAdapter() != EmojiView.this.stickersGridAdapter) {
                        EmojiView.this.stickersGridView.setAdapter(EmojiView.this.stickersGridAdapter);
                    }
                    StickersSearchGridAdapter.this.notifyDataSetChanged();
                    return;
                }
                int i = stickersSearchGridAdapter.emojiSearchId + 1;
                stickersSearchGridAdapter.emojiSearchId = i;
                this.lastId = i;
                this.query = StickersSearchGridAdapter.this.searchQuery;
                StickersSearchGridAdapter.this.isCompleted = false;
                this.localPacks.clear();
                this.localPacksByShortName.clear();
                this.localPacksByName.clear();
                this.emojiStickers.clear();
                this.emojiArrays.clear();
                this.emojiStickersArray.clear();
                this.emojiStickersArray2.clear();
                this.emojiStickersMap.clear();
                EmojiView.this.stickersSearchField.showProgress(true);
                if ("premium".equalsIgnoreCase(this.query)) {
                    Utilities.raceCallbacks(new EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda0(this), new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda2
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            this.f$0.addPremiumStickers((Runnable) obj);
                        }
                    });
                } else {
                    Utilities.raceCallbacks(new EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda0(this), new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda3
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            this.f$0.searchStickerSets((Runnable) obj);
                        }
                    }, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda4
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            this.f$0.searchStickerSetsByName((Runnable) obj);
                        }
                    }, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda5
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            this.f$0.addFromAllStickers((Runnable) obj);
                        }
                    }, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda6
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            this.f$0.addFromSuggestions((Runnable) obj);
                        }
                    }, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda7
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            this.f$0.addLocalPacks((Runnable) obj);
                        }
                    }, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda8
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            this.f$0.searchStickers((Runnable) obj);
                        }
                    });
                }
            }

            @Override // org.telegram.ui.Components.EmojiView.SearchRunnable
            public boolean isLoading() {
                return EmojiView.this.stickersSearchField.isInProgress();
            }

            @Override // org.telegram.ui.Components.EmojiView.SearchRunnable
            public boolean isCompleted() {
                return StickersSearchGridAdapter.this.isCompleted;
            }

            @Override // org.telegram.ui.Components.EmojiView.SearchRunnable
            public void loadNext() {
                if (isLoading()) {
                    return;
                }
                EmojiView.this.stickersSearchField.showProgress(true);
                Utilities.raceCallbacks(new EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda0(this), new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda1
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$loadNext$5((Runnable) obj);
                    }
                });
            }

            public /* synthetic */ void lambda$loadNext$5(Runnable runnable) {
                searchStickerSets(runnable, true);
            }
        }

        public StickersSearchGridAdapter(Context context) {
            this.context = context;
            C43422 c43422 = new FoundEmojiPacksRecyclerView(context, EmojiView.this.currentAccount, -1, false, new Utilities.Callback2() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$$ExternalSyntheticLambda1
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    this.f$0.foundPackListFillItems((ArrayList) obj, (UniversalAdapter) obj2);
                }
            }, new Utilities.Callback5() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$$ExternalSyntheticLambda2
                @Override // org.telegram.messenger.Utilities.Callback5
                public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    this.f$0.foundPackListOnClickItem((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue());
                }
            }, null, EmojiView.this.resourcesProvider, -1, 0) { // from class: org.telegram.ui.Components.EmojiView.StickersSearchGridAdapter.2
                final /* synthetic */ EmojiView val$this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C43422(Context context2, int i, int i2, boolean z, Utilities.Callback2 callback2, Utilities.Callback5 callback5, Utilities.Callback5Return callback5Return, Theme.ResourcesProvider resourcesProvider, int i3, int i4, EmojiView emojiView) {
                    super(context2, i, i2, z, callback2, callback5, callback5Return, resourcesProvider, i3, i4);
                    emojiView = emojiView;
                }
            };
            this.foundPacksListView = c43422;
            c43422.setPadding(AndroidUtilities.m1036dp(10.0f), 0, AndroidUtilities.m1036dp(10.0f), AndroidUtilities.m1036dp(5.0f));
            this.foundPacksListView.setClipToPadding(false);
            this.foundPacksListView.adapter.setApplyBackground(false);
            this.foundPacksListView.setNestedScrollingEnabled(false);
            this.foundPacksListView.setDrawSelection(false);
            this.foundPacksListView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.EmojiView.StickersSearchGridAdapter.3
                final /* synthetic */ EmojiView val$this$0;

                public ViewOnTouchListenerC43433(EmojiView emojiView) {
                    emojiView = emojiView;
                }

                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == 0) {
                        EmojiView.this.ignorePagerScroll = true;
                    } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                        EmojiView.this.ignorePagerScroll = false;
                    }
                    return false;
                }
            });
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$2 */
        public class C43422 extends FoundEmojiPacksRecyclerView {
            final /* synthetic */ EmojiView val$this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C43422(Context context2, int i, int i2, boolean z, Utilities.Callback2 callback2, Utilities.Callback5 callback5, Utilities.Callback5Return callback5Return, Theme.ResourcesProvider resourcesProvider, int i3, int i4, EmojiView emojiView) {
                super(context2, i, i2, z, callback2, callback5, callback5Return, resourcesProvider, i3, i4);
                emojiView = emojiView;
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$3 */
        public class ViewOnTouchListenerC43433 implements View.OnTouchListener {
            final /* synthetic */ EmojiView val$this$0;

            public ViewOnTouchListenerC43433(EmojiView emojiView) {
                emojiView = emojiView;
            }

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    EmojiView.this.ignorePagerScroll = true;
                } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                    EmojiView.this.ignorePagerScroll = false;
                }
                return false;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:101:0x00e3  */
        /* JADX WARN: Removed duplicated region for block: B:104:0x00f9  */
        /* JADX WARN: Removed duplicated region for block: B:109:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:74:0x0059  */
        /* JADX WARN: Removed duplicated region for block: B:93:0x00be  */
        /* JADX WARN: Removed duplicated region for block: B:94:0x00c1  */
        /* JADX WARN: Removed duplicated region for block: B:97:0x00d1  */
        /* JADX WARN: Removed duplicated region for block: B:98:0x00d3  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void foundPackListOnClickItem(org.telegram.p035ui.Components.UItem r19, android.view.View r20, int r21, float r22, float r23) {
            /*
                Method dump skipped, instruction units count: 255
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.StickersSearchGridAdapter.foundPackListOnClickItem(org.telegram.ui.Components.UItem, android.view.View, int, float, float):void");
        }

        public void resetSelectedPackId() {
            int childCount = this.foundPacksListView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                ((FoundStickerPackCell) this.foundPacksListView.getChildAt(i)).setSelected(false, true);
            }
            this.selectedPackId = 0L;
            EmojiView.this.animatorSearchStickerPackSelected.setValue(false, true);
            notifyDataSetChanged();
        }

        public void foundPackListFillItems(ArrayList<UItem> arrayList, UniversalAdapter universalAdapter) {
            LongSparseIntArray longSparseIntArray = new LongSparseIntArray();
            ArrayList<TLRPC.TL_messages_stickerSet> arrayList2 = this.localPacks;
            int size = arrayList2.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                TLRPC.TL_messages_stickerSet tL_messages_stickerSet = arrayList2.get(i);
                i++;
                TLRPC.TL_messages_stickerSet tL_messages_stickerSet2 = tL_messages_stickerSet;
                if (longSparseIntArray.indexOfKey(tL_messages_stickerSet2.set.f1280id) < 0) {
                    longSparseIntArray.append(tL_messages_stickerSet2.set.f1280id, 1);
                    arrayList.add(FoundStickerPackFactory.m1151of(tL_messages_stickerSet2, tL_messages_stickerSet2.set.f1280id == this.selectedPackId));
                }
            }
            ArrayList<EmojiPackInfo> arrayList3 = this.foundEmojiPacks;
            int size2 = arrayList3.size();
            int i2 = 0;
            while (i2 < size2) {
                EmojiPackInfo emojiPackInfo = arrayList3.get(i2);
                i2++;
                EmojiPackInfo emojiPackInfo2 = emojiPackInfo;
                if (longSparseIntArray.indexOfKey(emojiPackInfo2.set.f1280id) < 0) {
                    longSparseIntArray.append(emojiPackInfo2.set.f1280id, 1);
                    arrayList.add(FoundStickerPackFactory.m1150of(emojiPackInfo2.stickerSetCovered, emojiPackInfo2, emojiPackInfo2.set.f1280id == this.selectedPackId));
                }
            }
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 7;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            int i = this.totalItems;
            if (i != 1) {
                return i + 1;
            }
            return 2;
        }

        public void search(String str, boolean z) {
            if (this.reqId != 0) {
                ConnectionsManager.getInstance(EmojiView.this.currentAccount).cancelRequest(this.reqId, true);
                this.reqId = 0;
            }
            if (this.reqId2 != 0) {
                ConnectionsManager.getInstance(EmojiView.this.currentAccount).cancelRequest(this.reqId2, true);
                this.reqId2 = 0;
            }
            if (TextUtils.isEmpty(str)) {
                this.searchQuery = null;
                this.localPacks.clear();
                this.emojiStickers.clear();
                this.globalSearchArray = new ArrayList<>();
                if (EmojiView.this.stickersGridView.getAdapter() != EmojiView.this.stickersGridAdapter) {
                    EmojiView.this.stickersGridView.setAdapter(EmojiView.this.stickersGridAdapter);
                }
                this.selectedPackId = 0L;
                EmojiView.this.animatorSearchStickerPackSelected.setValue(false, true);
                notifyDataSetChanged();
                EmojiView.this.stickersSearchField.showProgress(false);
            } else {
                this.searchQuery = str.toLowerCase();
                EmojiView.this.stickersSearchField.showProgress(true);
            }
            AndroidUtilities.cancelRunOnUIThread(this.searchRunnable);
            AndroidUtilities.runOnUIThread(this.searchRunnable, 300L);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (this.selectedPackId != 0 && i == getItemCount() - 1) {
                return 8;
            }
            if (i == this.foundPacksRow) {
                return 7;
            }
            if (i == 0) {
                return 4;
            }
            if (i == 1 && this.totalItems == 1) {
                return 5;
            }
            Object obj = this.cache.get(i);
            if (obj == null) {
                return 1;
            }
            if (obj instanceof TLRPC.Document) {
                return 0;
            }
            return obj instanceof TLRPC.StickerSetCovered ? 3 : 2;
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$4 */
        public class C43444 extends StickerEmojiCell {
            public C43444(Context context, boolean z, Theme.ResourcesProvider resourcesProvider) {
                super(context, z, resourcesProvider);
            }

            @Override // android.widget.FrameLayout, android.view.View
            public void onMeasure(int i, int i2) {
                super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(82.0f), TLObject.FLAG_30));
            }
        }

        public /* synthetic */ void lambda$onCreateViewHolder$0(View view) {
            FeaturedStickerSetInfoCell featuredStickerSetInfoCell = (FeaturedStickerSetInfoCell) view.getParent();
            TLRPC.StickerSetCovered stickerSet = featuredStickerSetInfoCell.getStickerSet();
            if (EmojiView.this.installingStickerSets.indexOfKey(stickerSet.set.f1280id) >= 0 || EmojiView.this.removingStickerSets.indexOfKey(stickerSet.set.f1280id) >= 0) {
                return;
            }
            if (featuredStickerSetInfoCell.isInstalled()) {
                EmojiView.this.removingStickerSets.put(stickerSet.set.f1280id, stickerSet);
                EmojiView.this.delegate.onStickerSetRemove(featuredStickerSetInfoCell.getStickerSet());
            } else {
                featuredStickerSetInfoCell.setAddDrawProgress(true, true);
                EmojiView.this.installingStickerSets.put(stickerSet.set.f1280id, stickerSet);
                EmojiView.this.delegate.onStickerSetAdd(featuredStickerSetInfoCell.getStickerSet());
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$5 */
        public class C43455 extends FrameLayout {
            public C43455(Context context) {
                super(context);
            }

            @Override // android.widget.FrameLayout, android.view.View
            public void onMeasure(int i, int i2) {
                super.onMeasure(i, View.MeasureSpec.makeMeasureSpec((int) ((((EmojiView.this.stickersGridView.getMeasuredHeight() - EmojiView.this.searchFieldHeight) - AndroidUtilities.m1036dp(8.0f)) / 3) * 1.7f), TLObject.FLAG_30));
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            FrameLayout c43444;
            View emptyCell;
            View view;
            switch (i) {
                case 0:
                    c43444 = new StickerEmojiCell(this.context, true, EmojiView.this.resourcesProvider) { // from class: org.telegram.ui.Components.EmojiView.StickersSearchGridAdapter.4
                        public C43444(Context context, boolean z, Theme.ResourcesProvider resourcesProvider) {
                            super(context, z, resourcesProvider);
                        }

                        @Override // android.widget.FrameLayout, android.view.View
                        public void onMeasure(int i2, int i22) {
                            super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(82.0f), TLObject.FLAG_30));
                        }
                    };
                    view = c43444;
                    break;
                case 1:
                    emptyCell = new EmptyCell(this.context);
                    view = emptyCell;
                    break;
                case 2:
                    emptyCell = new StickerSetNameCell(this.context, false, EmojiView.this.resourcesProvider, EmojiView.this.glassDesign);
                    view = emptyCell;
                    break;
                case 3:
                    FeaturedStickerSetInfoCell featuredStickerSetInfoCell = new FeaturedStickerSetInfoCell(this.context, 17, false, true, EmojiView.this.resourcesProvider);
                    featuredStickerSetInfoCell.setAddOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            this.f$0.lambda$onCreateViewHolder$0(view2);
                        }
                    });
                    view = featuredStickerSetInfoCell;
                    break;
                case 4:
                    emptyCell = new View(this.context);
                    emptyCell.setLayoutParams(new RecyclerView.LayoutParams(-1, EmojiView.this.searchFieldHeight));
                    view = emptyCell;
                    break;
                case 5:
                    c43444 = new FrameLayout(this.context) { // from class: org.telegram.ui.Components.EmojiView.StickersSearchGridAdapter.5
                        public C43455(Context context) {
                            super(context);
                        }

                        @Override // android.widget.FrameLayout, android.view.View
                        public void onMeasure(int i2, int i22) {
                            super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec((int) ((((EmojiView.this.stickersGridView.getMeasuredHeight() - EmojiView.this.searchFieldHeight) - AndroidUtilities.m1036dp(8.0f)) / 3) * 1.7f), TLObject.FLAG_30));
                        }
                    };
                    ImageView imageView = new ImageView(this.context);
                    imageView.setScaleType(ImageView.ScaleType.CENTER);
                    imageView.setImageResource(C2797R.drawable.stickers_empty);
                    EmojiView emojiView = EmojiView.this;
                    int i2 = Theme.key_chat_emojiPanelEmptyText;
                    imageView.setColorFilter(new PorterDuffColorFilter(emojiView.getThemedColor(i2), PorterDuff.Mode.MULTIPLY));
                    imageView.setTranslationY(-AndroidUtilities.m1036dp(24.0f));
                    c43444.addView(imageView, LayoutHelper.createFrame(-2, -2.0f, 17, 0.0f, 42.0f, 0.0f, 28.0f));
                    TextView textView = new TextView(this.context);
                    textView.setText(LocaleController.getString(C2797R.string.NoStickersFound));
                    textView.setTextSize(1, 16.0f);
                    textView.setTextColor(EmojiView.this.getThemedColor(i2));
                    c43444.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 17, 0.0f, 42.0f, 0.0f, 9.0f));
                    c43444.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                    view = c43444;
                    break;
                case 6:
                default:
                    view = null;
                    break;
                case 7:
                    view = this.foundPacksListView;
                    view.setLayoutParams(new RecyclerView.LayoutParams(-1, AndroidUtilities.m1036dp(79.0f)));
                    break;
                case 8:
                    emptyCell = new View(EmojiView.this.getContext());
                    emptyCell.setLayoutParams(new RecyclerView.LayoutParams(-1, AndroidUtilities.m1036dp(68.0f)));
                    view = emptyCell;
                    break;
            }
            return new RecyclerListView.Holder(view);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            int itemViewType = viewHolder.getItemViewType();
            boolean z = true;
            z = true;
            if (itemViewType == 0) {
                TLRPC.Document document = (TLRPC.Document) this.cache.get(i);
                StickerEmojiCell stickerEmojiCell = (StickerEmojiCell) viewHolder.itemView;
                stickerEmojiCell.setSticker(document, null, this.cacheParent.get(i), this.positionToEmoji.get(i), false);
                if (!EmojiView.this.recentStickers.contains(document) && !EmojiView.this.favouriteStickers.contains(document)) {
                    z = false;
                }
                stickerEmojiCell.setRecent(z);
                return;
            }
            Integer numValueOf = null;
            if (itemViewType == 1) {
                EmptyCell emptyCell = (EmptyCell) viewHolder.itemView;
                if (i == this.totalItems) {
                    int i2 = this.positionToRow.get(i - 1, Integer.MIN_VALUE);
                    if (i2 == Integer.MIN_VALUE) {
                        emptyCell.setHeight(1);
                        return;
                    }
                    Object obj = this.rowStartPack.get(i2);
                    if (obj instanceof TLRPC.TL_messages_stickerSet) {
                        numValueOf = Integer.valueOf(((TLRPC.TL_messages_stickerSet) obj).documents.size());
                    } else if (obj instanceof Integer) {
                        numValueOf = (Integer) obj;
                    }
                    if (numValueOf == null) {
                        emptyCell.setHeight(1);
                        return;
                    } else if (numValueOf.intValue() == 0) {
                        emptyCell.setHeight(AndroidUtilities.m1036dp(8.0f));
                        return;
                    } else {
                        int height = EmojiView.this.pager.getHeight() - (((int) Math.ceil(numValueOf.intValue() / EmojiView.this.stickersGridAdapter.stickersPerRow)) * AndroidUtilities.m1036dp(82.0f));
                        emptyCell.setHeight(height > 0 ? height : 1);
                        return;
                    }
                }
                emptyCell.setHeight(AndroidUtilities.m1036dp(82.0f));
                return;
            }
            if (itemViewType == 2) {
                StickerSetNameCell stickerSetNameCell = (StickerSetNameCell) viewHolder.itemView;
                Object obj2 = this.cache.get(i);
                if (obj2 instanceof TLRPC.TL_messages_stickerSet) {
                    TLRPC.TL_messages_stickerSet tL_messages_stickerSet = (TLRPC.TL_messages_stickerSet) obj2;
                    if (!TextUtils.isEmpty(this.searchQuery) && this.localPacksByShortName.containsKey(tL_messages_stickerSet)) {
                        TLRPC.StickerSet stickerSet = tL_messages_stickerSet.set;
                        if (stickerSet != null) {
                            stickerSetNameCell.setText(stickerSet.title, 0);
                        }
                        stickerSetNameCell.setUrl(tL_messages_stickerSet.set.short_name, this.searchQuery.length());
                        return;
                    }
                    Integer num = this.localPacksByName.get(tL_messages_stickerSet);
                    TLRPC.StickerSet stickerSet2 = tL_messages_stickerSet.set;
                    if (stickerSet2 != null && num != null) {
                        stickerSetNameCell.setText(stickerSet2.title, 0, num.intValue(), !TextUtils.isEmpty(this.searchQuery) ? this.searchQuery.length() : 0);
                    }
                    stickerSetNameCell.setUrl(null, 0);
                    return;
                }
                if (obj2 instanceof String) {
                    stickerSetNameCell.setText((String) obj2, 0);
                    stickerSetNameCell.setUrl(null, 0);
                    return;
                }
                return;
            }
            if (itemViewType != 3) {
                return;
            }
            TLRPC.StickerSetCovered stickerSetCovered = (TLRPC.StickerSetCovered) this.cache.get(i);
            FeaturedStickerSetInfoCell featuredStickerSetInfoCell = (FeaturedStickerSetInfoCell) viewHolder.itemView;
            boolean z2 = EmojiView.this.installingStickerSets.indexOfKey(stickerSetCovered.set.f1280id) >= 0;
            boolean z3 = EmojiView.this.removingStickerSets.indexOfKey(stickerSetCovered.set.f1280id) >= 0;
            if (z2 || z3) {
                if (z2 && featuredStickerSetInfoCell.isInstalled()) {
                    EmojiView.this.installingStickerSets.remove(stickerSetCovered.set.f1280id);
                    z2 = false;
                } else if (z3 && !featuredStickerSetInfoCell.isInstalled()) {
                    EmojiView.this.removingStickerSets.remove(stickerSetCovered.set.f1280id);
                }
            }
            featuredStickerSetInfoCell.setAddDrawProgress(z2, false);
            int iIndexOfIgnoreCase = TextUtils.isEmpty(this.searchQuery) ? -1 : AndroidUtilities.indexOfIgnoreCase(stickerSetCovered.set.title, this.searchQuery);
            if (iIndexOfIgnoreCase >= 0) {
                featuredStickerSetInfoCell.setStickerSet(stickerSetCovered, false, false, iIndexOfIgnoreCase, this.searchQuery.length());
                return;
            }
            featuredStickerSetInfoCell.setStickerSet(stickerSetCovered, false);
            if (TextUtils.isEmpty(this.searchQuery) || AndroidUtilities.indexOfIgnoreCase(stickerSetCovered.set.short_name, this.searchQuery) != 0) {
                return;
            }
            featuredStickerSetInfoCell.setUrl(stickerSetCovered.set.short_name, this.searchQuery.length());
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            rebuild();
            super.notifyDataSetChanged();
        }

        private void rebuild() {
            int i;
            int i2;
            int i3;
            int i4;
            this.foundPacksRow = -1;
            this.rowStartPack.clear();
            this.positionToRow.clear();
            this.cache.clear();
            this.positionToEmoji.clear();
            int i5 = 0;
            this.totalItems = 0;
            int size = this.localPacks.size() + this.localPacksByName.size();
            this.foundPacksListView.adapter.update(false);
            long j = this.selectedPackId;
            String str = _UrlKt.FRAGMENT_ENCODE_SET;
            if (j != 0) {
                ArrayList<TLRPC.Document> arrayList = this.selectedPackStickers;
                SparseArray<Object> sparseArray = this.cache;
                int i6 = this.totalItems;
                this.totalItems = i6 + 1;
                sparseArray.put(i6, "search");
                if (size > 0) {
                    SparseArray<Object> sparseArray2 = this.cache;
                    int i7 = this.totalItems;
                    this.totalItems = i7 + 1;
                    this.foundPacksRow = i7;
                    sparseArray2.put(i7, "packs");
                    SparseArray<Object> sparseArray3 = this.cache;
                    int i8 = this.totalItems;
                    this.totalItems = i8 + 1;
                    sparseArray3.put(i8, LocaleController.formatPluralString("Stickers", this.selectedPackStickerSet.count, new Object[0]));
                    i4 = 3;
                } else {
                    i4 = 1;
                }
                String str2 = this.emojiStickers.get(arrayList);
                if (str2 != null && !_UrlKt.FRAGMENT_ENCODE_SET.equals(str2)) {
                    this.positionToEmoji.put(this.totalItems, str2);
                }
                int size2 = arrayList.size();
                int i9 = 0;
                for (int i10 = 0; i10 < size2; i10++) {
                    int i11 = this.totalItems + i9;
                    int i12 = (i9 / EmojiView.this.stickersGridAdapter.stickersPerRow) + i4;
                    TLRPC.Document document = arrayList.get(i10);
                    this.cache.put(i11, document);
                    TLRPC.TL_messages_stickerSet stickerSetById = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSetById(MediaDataController.getStickerSetId(document));
                    if (stickerSetById != null) {
                        this.cacheParent.put(i11, stickerSetById);
                    }
                    this.positionToRow.put(i11, i12);
                    i9++;
                }
                int iCeil = (int) Math.ceil(i9 / EmojiView.this.stickersGridAdapter.stickersPerRow);
                while (i5 < iCeil) {
                    this.rowStartPack.put(i4 + i5, Integer.valueOf(i9));
                    i5++;
                }
                this.totalItems += iCeil * EmojiView.this.stickersGridAdapter.stickersPerRow;
                return;
            }
            boolean zIsEmpty = this.emojiArrays.isEmpty();
            ArrayList<TLRPC.Document> arrayList2 = this.globalSearchArray;
            boolean z = (arrayList2 == null || arrayList2.isEmpty()) ? false : true;
            SparseArray<Object> sparseArray4 = this.cache;
            int i13 = this.totalItems;
            this.totalItems = i13 + 1;
            sparseArray4.put(i13, "search");
            if (size > 0) {
                SparseArray<Object> sparseArray5 = this.cache;
                int i14 = this.totalItems;
                this.totalItems = i14 + 1;
                this.foundPacksRow = i14;
                sparseArray5.put(i14, "packs");
                i = 2;
            } else {
                i = 1;
            }
            if (zIsEmpty) {
                i2 = size;
                i3 = 1;
            } else {
                SparseArray<Object> sparseArray6 = this.cache;
                int i15 = this.totalItems;
                this.totalItems = i15 + 1;
                sparseArray6.put(i15, LocaleController.getString(C2797R.string.StickerOrEmojiSearchResult));
                int i16 = i + 1;
                int size3 = this.emojiArrays.size();
                int i17 = 0;
                int i18 = 0;
                while (i17 < size3) {
                    ArrayList<TLRPC.Document> arrayList3 = this.emojiArrays.get(i17);
                    String str3 = this.emojiStickers.get(arrayList3);
                    if (str3 != null && !str.equals(str3)) {
                        this.positionToEmoji.put(this.totalItems + i18, str3);
                        str = str3;
                    }
                    int size4 = arrayList3.size();
                    int i19 = i5;
                    while (i19 < size4) {
                        int i20 = this.totalItems + i18;
                        int i21 = (i18 / EmojiView.this.stickersGridAdapter.stickersPerRow) + i16;
                        TLRPC.Document document2 = arrayList3.get(i19);
                        this.cache.put(i20, document2);
                        int i22 = size;
                        TLRPC.TL_messages_stickerSet stickerSetById2 = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSetById(MediaDataController.getStickerSetId(document2));
                        if (stickerSetById2 != null) {
                            this.cacheParent.put(i20, stickerSetById2);
                        }
                        this.positionToRow.put(i20, i21);
                        i18++;
                        i19++;
                        size = i22;
                    }
                    i17++;
                    i5 = 0;
                }
                i2 = size;
                i3 = 1;
                int iCeil2 = (int) Math.ceil(i18 / EmojiView.this.stickersGridAdapter.stickersPerRow);
                for (int i23 = 0; i23 < iCeil2; i23++) {
                    this.rowStartPack.put(i16 + i23, Integer.valueOf(i18));
                }
                this.totalItems += EmojiView.this.stickersGridAdapter.stickersPerRow * iCeil2;
                i = i16 + iCeil2;
            }
            if (z) {
                SparseArray<Object> sparseArray7 = this.cache;
                int i24 = this.totalItems;
                this.totalItems = i24 + 1;
                sparseArray7.put(i24, LocaleController.getString(C2797R.string.StickerOrEmojiGlobalSearchResult));
                int i25 = i + 1;
                String str4 = this.emojiStickers.get(this.globalSearchArray);
                if (str4 != null) {
                    this.positionToEmoji.put(this.totalItems, str4);
                }
                int size5 = this.globalSearchArray.size();
                int i26 = 0;
                for (int i27 = 0; i27 < size5; i27++) {
                    int i28 = this.totalItems + i26;
                    int i29 = (i26 / EmojiView.this.stickersGridAdapter.stickersPerRow) + i25;
                    TLRPC.Document document3 = this.globalSearchArray.get(i27);
                    this.cache.put(i28, document3);
                    TLRPC.TL_messages_stickerSet stickerSetById3 = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSetById(MediaDataController.getStickerSetId(document3));
                    if (stickerSetById3 != null) {
                        this.cacheParent.put(i28, stickerSetById3);
                    }
                    this.positionToRow.put(i28, i29);
                    i26++;
                }
                int iCeil3 = (int) Math.ceil(i26 / EmojiView.this.stickersGridAdapter.stickersPerRow);
                for (int i30 = 0; i30 < iCeil3; i30++) {
                    this.rowStartPack.put(i25 + i30, Integer.valueOf(i26));
                }
                this.totalItems += iCeil3 * EmojiView.this.stickersGridAdapter.stickersPerRow;
            }
            if (zIsEmpty && !z && i2 == 0) {
                this.totalItems = i3;
            }
        }
    }

    public void searchProgressChanged() {
        updateStickerTabsPosition();
    }

    public float getStickersExpandOffset() {
        ScrollSlidingTabStrip scrollSlidingTabStrip = this.stickersTab;
        if (scrollSlidingTabStrip == null) {
            return 0.0f;
        }
        return scrollSlidingTabStrip.getExpandedOffset();
    }

    public void setShowing(boolean z) {
        this.showing = z;
        updateStickerTabsPosition();
    }

    public void onMessageSend() {
        ChooseStickerActionTracker chooseStickerActionTracker = this.chooseStickerActionTracker;
        if (chooseStickerActionTracker != null) {
            chooseStickerActionTracker.reset();
        }
    }

    public static abstract class ChooseStickerActionTracker {
        private final int currentAccount;
        private final long dialogId;
        private final long threadId;
        boolean typingWasSent;
        boolean visible = false;
        long lastActionTime = -1;

        public abstract boolean isShown();

        public ChooseStickerActionTracker(int i, long j, long j2) {
            this.currentAccount = i;
            this.dialogId = j;
            this.threadId = j2;
        }

        public void doSomeAction() {
            if (this.visible) {
                if (this.lastActionTime == -1) {
                    this.lastActionTime = System.currentTimeMillis();
                } else if (System.currentTimeMillis() - this.lastActionTime > 2000) {
                    this.typingWasSent = true;
                    this.lastActionTime = System.currentTimeMillis();
                    MessagesController.getInstance(this.currentAccount).sendTyping(this.dialogId, this.threadId, 10, 0);
                }
            }
        }

        public void reset() {
            if (this.typingWasSent) {
                MessagesController.getInstance(this.currentAccount).sendTyping(this.dialogId, this.threadId, 2, 0);
            }
            this.lastActionTime = -1L;
        }

        public void checkVisibility() {
            boolean zIsShown = isShown();
            this.visible = zIsShown;
            if (zIsShown) {
                return;
            }
            reset();
        }
    }

    public class Tab {
        int type;
        View view;

        public /* synthetic */ Tab(EmojiView emojiView, EmojiViewIA emojiViewIA) {
            this();
        }

        private Tab() {
        }
    }

    public void freeze(boolean z) {
        StickersGridAdapter stickersGridAdapter;
        boolean z2 = this.frozen;
        this.frozen = z;
        if (!z2 || z) {
            return;
        }
        int i = this.currentPage;
        if (i == 0) {
            EmojiGridAdapter emojiGridAdapter = this.emojiAdapter;
            if (emojiGridAdapter != null) {
                emojiGridAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (i == 1) {
            GifAdapter gifAdapter = this.gifAdapter;
            if (gifAdapter != null) {
                gifAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (i != 2 || (stickersGridAdapter = this.stickersGridAdapter) == null) {
            return;
        }
        stickersGridAdapter.notifyDataSetChanged();
    }

    public int getGlassIconColor(float f) {
        return ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_glass_defaultIcon, this.resourcesProvider), (int) (f * 255.0f));
    }

    public static class FoundStickerPackFactory extends UItem.UItemFactory<FoundStickerPackCell> {
        private FoundStickerPackFactory() {
        }

        static {
            UItem.UItemFactory.setup(new FoundStickerPackFactory());
        }

        @Override // org.telegram.ui.Components.UItem.UItemFactory
        public FoundStickerPackCell createView(Context context, RecyclerListView recyclerListView, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
            FoundStickerPackCell foundStickerPackCell = new FoundStickerPackCell(context, resourcesProvider);
            foundStickerPackCell.setLayoutParams(new RecyclerView.LayoutParams(AndroidUtilities.m1036dp(64.0f), -1));
            return foundStickerPackCell;
        }

        @Override // org.telegram.ui.Components.UItem.UItemFactory
        public void bindView(View view, UItem uItem, boolean z, UniversalAdapter universalAdapter, UniversalRecyclerView universalRecyclerView) {
            FoundStickerPackCell foundStickerPackCell = (FoundStickerPackCell) view;
            Object obj = uItem.object;
            if (obj instanceof TLRPC.TL_messages_stickerSet) {
                foundStickerPackCell.setPack((TLRPC.TL_messages_stickerSet) obj);
            } else if (obj instanceof TLRPC.StickerSetCovered) {
                foundStickerPackCell.setPack((TLRPC.StickerSetCovered) obj, ((EmojiPackInfo) uItem.object2).firstDocument);
            }
            foundStickerPackCell.setSelected(uItem.checked, false);
        }

        /* JADX INFO: renamed from: of */
        public static UItem m1151of(TLRPC.TL_messages_stickerSet tL_messages_stickerSet, boolean z) {
            UItem uItemOfFactory = UItem.ofFactory(FoundStickerPackFactory.class);
            uItemOfFactory.f1708id = Long.hashCode(tL_messages_stickerSet.set.f1280id);
            uItemOfFactory.longValue = tL_messages_stickerSet.set.f1280id;
            uItemOfFactory.object = tL_messages_stickerSet;
            uItemOfFactory.checked = z;
            return uItemOfFactory;
        }

        /* JADX INFO: renamed from: of */
        public static UItem m1150of(TLRPC.StickerSetCovered stickerSetCovered, EmojiPackInfo emojiPackInfo, boolean z) {
            UItem uItemOfFactory = UItem.ofFactory(FoundStickerPackFactory.class);
            uItemOfFactory.f1708id = Long.hashCode(stickerSetCovered.set.f1280id + 1);
            uItemOfFactory.longValue = stickerSetCovered.set.f1280id;
            uItemOfFactory.object = stickerSetCovered;
            uItemOfFactory.object2 = emojiPackInfo;
            uItemOfFactory.checked = z;
            return uItemOfFactory;
        }

        @Override // org.telegram.ui.Components.UItem.UItemFactory
        public boolean equals(UItem uItem, UItem uItem2) {
            return uItem.longValue == uItem2.longValue;
        }

        @Override // org.telegram.ui.Components.UItem.UItemFactory
        public boolean contentsEquals(UItem uItem, UItem uItem2) {
            return uItem.longValue == uItem2.longValue && uItem.checked == uItem2.checked;
        }
    }

    @Override // me.vkryl.android.animator.FactorAnimator.Target
    public void onFactorChanged(int i, float f, float f2, FactorAnimator factorAnimator) {
        if (i == 0) {
            checkStickersSearchFieldScroll(false);
            checkStickersSearchFieldVisibility();
            updateBottomTabContainerPosition();
            this.stickersContainer.invalidate();
            return;
        }
        if (i == 1) {
            checkEmojiSearchFieldScroll(false);
            checkEmojiSearchFieldVisibility();
            updateBottomTabContainerPosition();
            this.emojiContainer.invalidate();
        }
    }

    public void setFoundPackButtonText(final FoundStickerPackButton foundStickerPackButton, final TLObject tLObject, final TLRPC.StickerSet stickerSet, final TLRPC.Document document, final boolean z, boolean z2) {
        String pluralString;
        StickersSearchGridAdapter stickersSearchGridAdapter;
        EmojiSearchAdapter emojiSearchAdapter;
        if (stickerSet == null) {
            return;
        }
        if (!z || (emojiSearchAdapter = this.emojiSearchAdapter) == null || emojiSearchAdapter.selectedPackId == stickerSet.f1280id) {
            if (z || (stickersSearchGridAdapter = this.stickersSearchGridAdapter) == null || stickersSearchGridAdapter.selectedPackId == stickerSet.f1280id) {
                final boolean zIsStickerPackInstalled = MediaDataController.getInstance(this.currentAccount).isStickerPackInstalled(stickerSet.f1280id);
                boolean z3 = stickerSet.masks;
                if (zIsStickerPackInstalled) {
                    if (z3) {
                        pluralString = LocaleController.formatPluralString("RemoveManyMasksCount", stickerSet.count, new Object[0]);
                    } else {
                        boolean z4 = stickerSet.emojis;
                        int i = stickerSet.count;
                        if (z4) {
                            pluralString = LocaleController.formatPluralString("RemoveManyEmojiCount", i, new Object[0]);
                        } else {
                            pluralString = LocaleController.formatPluralString("RemoveManyStickersCount", i, new Object[0]);
                        }
                    }
                } else if (z3) {
                    pluralString = LocaleController.formatPluralString("AddManyMasksCount", stickerSet.count, new Object[0]);
                } else {
                    boolean z5 = stickerSet.emojis;
                    int i2 = stickerSet.count;
                    if (z5) {
                        pluralString = LocaleController.formatPluralString("AddManyEmojiCount", i2, new Object[0]);
                    } else {
                        pluralString = LocaleController.formatPluralString("AddManyStickersCount", i2, new Object[0]);
                    }
                }
                foundStickerPackButton.setText(pluralString, z2);
                foundStickerPackButton.setIsPrimary(!zIsStickerPackInstalled, z2);
                foundStickerPackButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda36
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$setFoundPackButtonText$33(tLObject, document, zIsStickerPackInstalled, foundStickerPackButton, stickerSet, z, view);
                    }
                });
            }
        }
    }

    public /* synthetic */ void lambda$setFoundPackButtonText$33(final TLObject tLObject, final TLRPC.Document document, boolean z, final FoundStickerPackButton foundStickerPackButton, final TLRPC.StickerSet stickerSet, final boolean z2, View view) {
        MediaDataController.getInstance(this.currentAccount).toggleStickerSet(getContext(), tLObject, document, z ? 0 : 2, this.fragment, this.bulletinContainer2, false, true, new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda37
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setFoundPackButtonText$32(foundStickerPackButton, tLObject, stickerSet, document, z2);
            }
        }, false);
        setFoundPackButtonText(foundStickerPackButton, tLObject, stickerSet, document, z2, true);
    }

    public /* synthetic */ void lambda$setFoundPackButtonText$32(FoundStickerPackButton foundStickerPackButton, TLObject tLObject, TLRPC.StickerSet stickerSet, TLRPC.Document document, boolean z) {
        setFoundPackButtonText(foundStickerPackButton, tLObject, stickerSet, document, z, true);
    }
}
