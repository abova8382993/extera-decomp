package org.telegram.p035ui.Components;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.collection.LongSparseArray;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.ActionBar;
import org.telegram.p035ui.ActionBar.AdjustPanLayoutHelper;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.ActionBar.ThemeDescription;
import org.telegram.p035ui.Cells.HeaderCell;
import org.telegram.p035ui.Cells.PollCreateCheckCell;
import org.telegram.p035ui.Cells.PollEditTextCell;
import org.telegram.p035ui.Cells.ShadowSectionCell;
import org.telegram.p035ui.Cells.TextCell;
import org.telegram.p035ui.Cells.TextCheckCell;
import org.telegram.p035ui.Cells.TextInfoPrivacyCell;
import org.telegram.p035ui.ChatActivity;
import org.telegram.p035ui.ChatActivity$$ExternalSyntheticLambda278;
import org.telegram.p035ui.Components.AlertsCreator;
import org.telegram.p035ui.Components.ChatActivityEnterViewAnimatedIconView;
import org.telegram.p035ui.Components.ChatAttachAlert;
import org.telegram.p035ui.Components.ChatAttachAlertAudioLayout;
import org.telegram.p035ui.Components.ChatAttachAlertDocumentLayout;
import org.telegram.p035ui.Components.ChatAttachAlertLocationLayout;
import org.telegram.p035ui.Components.ChatAttachAlertPollLayout;
import org.telegram.p035ui.Components.EmojiView;
import org.telegram.p035ui.Components.RecyclerListView;
import org.telegram.p035ui.Components.SizeNotifierFrameLayout;
import org.telegram.p035ui.Components.SuggestEmojiView;
import org.telegram.p035ui.Components.poll.PollAttachedMedia;
import org.telegram.p035ui.Components.poll.PollAttachedMediaPack;
import org.telegram.p035ui.Components.poll.WebPageLoader;
import org.telegram.p035ui.Components.poll.attached.PollAttachedMediaFile;
import org.telegram.p035ui.Components.poll.attached.PollAttachedMediaGallery;
import org.telegram.p035ui.Components.poll.attached.PollAttachedMediaLink;
import org.telegram.p035ui.Components.poll.attached.PollAttachedMediaLocation;
import org.telegram.p035ui.Components.poll.attached.PollAttachedMediaMusic;
import org.telegram.p035ui.Components.poll.attached.PollAttachedMediaSticker;
import org.telegram.p035ui.Components.poll.sheets.CountrySelectBottomSheet;
import org.telegram.p035ui.ContentPreviewViewer;
import org.telegram.p035ui.PhotoViewer;
import org.telegram.p035ui.Stories.recorder.KeyboardNotifier;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes7.dex */
public class ChatAttachAlertPollLayout extends ChatAttachAlert.AttachAlertLayout implements SizeNotifierFrameLayout.SizeNotifierFrameLayoutDelegate, NotificationCenter.NotificationCenterDelegate {
    private final int MAX_CAPTION_LENGTH;
    private final int[] POLL_DURATION_OPTIONS;
    private int addAnswerRow;
    private boolean allowAdding;
    private boolean allowAddingOptions;
    private int allowAddingRow;
    private boolean allowMarking;
    private int allowMarkingRow;
    private boolean allowNesterScroll;
    private boolean allowRevoting;
    private boolean anonymousPoll;
    private int answerHeaderRow;
    private int answerSectionRow;
    private int answerStartRow;
    private final CharSequence[] answers;
    private final boolean[] answersChecks;
    private int answersCount;
    private final PollAttachedMediaPack attachedMedia;
    private final Paint checkboxPaint;
    private ArrayList<String> countriesList;
    private ChatAttachAlert currentAttachAlert;
    private int currentAttachAlertIndex;
    private PollEditTextCell currentCell;
    private PollCreateActivityDelegate delegate;
    private int descriptionRow;
    private CharSequence descriptionString;
    private boolean destroyed;
    private boolean doneItemEnabled;
    private int emojiPadding;
    public EmojiView emojiView;
    public boolean emojiViewVisible;
    public boolean emojiViewWasVisible;
    private int emptyRow;
    private boolean hideResults;
    private boolean hintShowed;
    private HintView hintView;
    private boolean ignoreLayout;
    private boolean isAnimatePopupClosing;
    public boolean isEmojiSearchOpened;
    private final boolean isPremium;
    private final DefaultItemAnimator itemAnimator;
    private int keyboardHeight;
    private int keyboardHeightLand;
    private final KeyboardNotifier keyboardNotifier;
    private boolean keyboardVisible;
    private int lastSizeChangeValue1;
    private boolean lastSizeChangeValue2;
    private final FillLastLinearLayoutManager layoutManager;
    private final ListAdapter listAdapter;
    private final RecyclerListView listView;
    private final int maxAnswersCount;
    private boolean multipleChoise;
    private final Runnable openKeyboardRunnable;
    private int paddingRow;
    private int poll2vAllowAddingRow;
    private int poll2vAllowRevotingRow;
    private int poll2vAnonymousRow;
    private int poll2vLimitByCountryListRow;
    private final ToggleRow poll2vLimitByCountryRow;
    private int poll2vLimitDurationHideResultsRow;
    private int poll2vLimitDurationHideResultsRowInfo;
    private int poll2vLimitDurationRow;
    private int poll2vLimitDurationTimeRow;
    private int poll2vMultipleRow;
    private int poll2vQuizRow;
    private int poll2vShuffleRow;
    private final ToggleRow poll2vSubscribersOnlyRow;
    private int pollLimitDeadline;
    private int pollLimitDuration;
    private int questionHeaderRow;
    private int questionRow;
    private int questionSectionRow;
    private CharSequence questionString;
    private boolean quizOnly;
    private boolean quizPoll;
    private int requestFieldFocusAtPosition;
    private int rowCount;
    private int settingsHeaderRow;
    private int settingsSectionRow;
    private int showMediaHintIndexAfterSmoothScroll;
    private boolean shuffleOptions;
    private boolean smoothScrollToOption;
    private int solutionInfoRow;
    private int solutionRow;
    private int solutionRowHeader;
    private CharSequence solutionString;
    private SuggestEmojiView suggestEmojiPanel;
    private final boolean todo;
    private final ToggleRow[] toggleRows;
    private int topPadding;
    private boolean waitingForKeyboardOpen;
    public boolean wasEmojiSearchOpened;
    private final WebPageLoader webPageLoader;

    public interface PollCreateActivityDelegate {
        void sendPoll(TLRPC.MessageMedia messageMedia, CharSequence charSequence, PollAttachedMediaPack pollAttachedMediaPack, ArrayList<Integer> arrayList, boolean z, int i, long j);
    }

    /* JADX INFO: renamed from: $r8$lambda$9w1_RlEquHPRRrW-P2y0Rh0a-FE */
    public static /* synthetic */ void m11103$r8$lambda$9w1_RlEquHPRRrWP2y0Rh0aFE() {
    }

    public static int getAllowedLayoutsForIndex(int i) {
        return (i == -2 || i == -3) ? 90 : 41026;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int needsActionBar() {
        return 1;
    }

    public static class EmptyView extends View {
        public EmptyView(Context context) {
            super(context);
        }
    }

    public class TouchHelperCallback extends ItemTouchHelper.Callback {
        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        }

        public TouchHelperCallback() {
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() != 5) {
                return ItemTouchHelper.Callback.makeMovementFlags(0, 0);
            }
            return ItemTouchHelper.Callback.makeMovementFlags(3, 0);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            if (viewHolder.getItemViewType() != viewHolder2.getItemViewType()) {
                return false;
            }
            ChatAttachAlertPollLayout.this.listAdapter.swapElements(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
            return true;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            super.onChildDraw(canvas, recyclerView, viewHolder, f, f2, i, z);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
            if (i != 0) {
                ChatAttachAlertPollLayout.this.listView.setItemAnimator(ChatAttachAlertPollLayout.this.itemAnimator);
                ChatAttachAlertPollLayout.this.listView.cancelClickRunnables(false);
                viewHolder.itemView.setPressed(true);
                viewHolder.itemView.setBackgroundColor(ChatAttachAlertPollLayout.this.getThemedColor(Theme.key_dialogBackground));
            }
            super.onSelectedChanged(viewHolder, i);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setPressed(false);
            viewHolder.itemView.setBackground(null);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$1 */
    public class RunnableC41351 implements Runnable {
        public RunnableC41351() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ChatAttachAlertPollLayout.this.currentCell != null) {
                EditTextBoldCursor editField = ChatAttachAlertPollLayout.this.currentCell.getEditField();
                if (ChatAttachAlertPollLayout.this.destroyed || editField == null || !ChatAttachAlertPollLayout.this.waitingForKeyboardOpen || ChatAttachAlertPollLayout.this.keyboardVisible || AndroidUtilities.usingHardwareInput || AndroidUtilities.isInMultiwindow || !AndroidUtilities.isTablet()) {
                    return;
                }
                editField.requestFocus();
                AndroidUtilities.showKeyboard(editField);
                AndroidUtilities.cancelRunOnUIThread(ChatAttachAlertPollLayout.this.openKeyboardRunnable);
                AndroidUtilities.runOnUIThread(ChatAttachAlertPollLayout.this.openKeyboardRunnable, 100L);
            }
        }
    }

    public ChatAttachAlertPollLayout(final ChatAttachAlert chatAttachAlert, final Context context, boolean z, final Theme.ResourcesProvider resourcesProvider, Boolean bool) {
        super(chatAttachAlert, context, resourcesProvider);
        this.answersCount = 1;
        this.allowRevoting = true;
        this.shuffleOptions = true;
        this.allowAddingOptions = true;
        this.multipleChoise = true;
        this.allowAdding = true;
        this.allowMarking = true;
        this.requestFieldFocusAtPosition = -1;
        ToggleRow toggleRow = new ToggleRow();
        this.poll2vSubscribersOnlyRow = toggleRow;
        ToggleRow toggleRow2 = new ToggleRow();
        this.poll2vLimitByCountryRow = toggleRow2;
        this.toggleRows = new ToggleRow[]{toggleRow, toggleRow2};
        this.countriesList = new ArrayList<>();
        this.POLL_DURATION_OPTIONS = new int[]{3600, 10800, 28800, 86400, 259200};
        this.openKeyboardRunnable = new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.1
            public RunnableC41351() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (ChatAttachAlertPollLayout.this.currentCell != null) {
                    EditTextBoldCursor editField = ChatAttachAlertPollLayout.this.currentCell.getEditField();
                    if (ChatAttachAlertPollLayout.this.destroyed || editField == null || !ChatAttachAlertPollLayout.this.waitingForKeyboardOpen || ChatAttachAlertPollLayout.this.keyboardVisible || AndroidUtilities.usingHardwareInput || AndroidUtilities.isInMultiwindow || !AndroidUtilities.isTablet()) {
                        return;
                    }
                    editField.requestFocus();
                    AndroidUtilities.showKeyboard(editField);
                    AndroidUtilities.cancelRunOnUIThread(ChatAttachAlertPollLayout.this.openKeyboardRunnable);
                    AndroidUtilities.runOnUIThread(ChatAttachAlertPollLayout.this.openKeyboardRunnable, 100L);
                }
            }
        };
        this.smoothScrollToOption = false;
        this.showMediaHintIndexAfterSmoothScroll = -1;
        this.isEmojiSearchOpened = false;
        this.wasEmojiSearchOpened = false;
        Paint paint = new Paint(1);
        this.checkboxPaint = paint;
        this.attachedMedia = new PollAttachedMediaPack();
        this.todo = z;
        int answersMaxCount = getAnswersMaxCount();
        this.maxAnswersCount = answersMaxCount;
        this.answers = new CharSequence[answersMaxCount];
        this.answersChecks = new boolean[answersMaxCount];
        boolean zIsPremium = AccountInstance.getInstance(this.parentAlert.currentAccount).getUserConfig().isPremium();
        this.isPremium = zIsPremium;
        if (bool != null) {
            boolean zBooleanValue = bool.booleanValue();
            this.quizPoll = zBooleanValue;
            this.quizOnly = zBooleanValue;
            this.allowAddingOptions = !zBooleanValue;
            this.allowRevoting = !zBooleanValue;
        }
        updateRows();
        paint.setColor(getThemedColor(Theme.key_telegram_color));
        this.parentAlert.sizeNotifierFrameLayout.setDelegate(this);
        ListAdapter listAdapter = new ListAdapter(context);
        this.listAdapter = listAdapter;
        C41442 c41442 = new RecyclerListView(context) { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.2
            public C41442(final Context context2) {
                super(context2);
            }

            @Override // androidx.recyclerview.widget.RecyclerView
            public void requestChildOnScreen(View view, View view2) {
                if (view instanceof PollEditTextCell) {
                    super.requestChildOnScreen(view, view2);
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.ViewParent
            public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z2) {
                rect.bottom += AndroidUtilities.m1036dp(60.0f);
                return super.requestChildRectangleOnScreen(view, rect, z2);
            }
        };
        this.listView = c41442;
        this.iBlur3Capture = c41442;
        this.iBlur3CaptureView = c41442;
        this.occupyStatusBar = true;
        this.occupyNavigationBar = true;
        C41453 c41453 = new DefaultItemAnimator() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.3
            public C41453() {
            }

            @Override // androidx.recyclerview.widget.DefaultItemAnimator
            public void onMoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() == 0) {
                    ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                    chatAttachAlertPollLayout.parentAlert.updateLayout(chatAttachAlertPollLayout, true, 0);
                }
            }
        };
        this.itemAnimator = c41453;
        c41442.setItemAnimator(c41453);
        this.countriesList.clear();
        c41453.setSupportsChangeAnimations(false);
        c41453.setDelayAnimations(false);
        c41453.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        c41453.setDurations(350L);
        c41442.setClipToPadding(false);
        c41442.setVerticalScrollBarEnabled(false);
        c41442.setSections(true);
        C41464 c41464 = new FillLastLinearLayoutManager(context2, 1, false, AndroidUtilities.m1036dp(65.0f) + AndroidUtilities.statusBarHeight, c41442) { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.4
            public C41464(final Context context2, int i, boolean z2, int i2, RecyclerView c414422) {
                super(context2, i, z2, i2, c414422);
            }

            /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$4$1 */
            public class AnonymousClass1 extends LinearSmoothScroller {
                public AnonymousClass1(Context context) {
                    super(context);
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public int calculateDyToMakeVisible(View view, int i) {
                    if (ChatAttachAlertPollLayout.this.smoothScrollToOption) {
                        i = -1;
                    }
                    int iCalculateDyToMakeVisible = super.calculateDyToMakeVisible(view, i);
                    if (ChatAttachAlertPollLayout.this.smoothScrollToOption) {
                        iCalculateDyToMakeVisible += AndroidUtilities.m1036dp(160.0f);
                    }
                    if (!ChatAttachAlertPollLayout.this.smoothScrollToOption) {
                        iCalculateDyToMakeVisible -= (ChatAttachAlertPollLayout.this.topPadding - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(7.0f);
                    }
                    if (ChatAttachAlertPollLayout.this.smoothScrollToOption && iCalculateDyToMakeVisible == 0 && ChatAttachAlertPollLayout.this.showMediaHintIndexAfterSmoothScroll >= 0) {
                        ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                        chatAttachAlertPollLayout.showMediaHint(chatAttachAlertPollLayout.showMediaHintIndexAfterSmoothScroll);
                        ChatAttachAlertPollLayout.this.showMediaHintIndexAfterSmoothScroll = -1;
                    }
                    ChatAttachAlertPollLayout.this.smoothScrollToOption = false;
                    return iCalculateDyToMakeVisible;
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public int calculateTimeForDeceleration(int i) {
                    return super.calculateTimeForDeceleration(i) * 2;
                }
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
                AnonymousClass1 anonymousClass1 = new LinearSmoothScroller(recyclerView.getContext()) { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.4.1
                    public AnonymousClass1(Context context2) {
                        super(context2);
                    }

                    @Override // androidx.recyclerview.widget.LinearSmoothScroller
                    public int calculateDyToMakeVisible(View view, int i2) {
                        if (ChatAttachAlertPollLayout.this.smoothScrollToOption) {
                            i2 = -1;
                        }
                        int iCalculateDyToMakeVisible = super.calculateDyToMakeVisible(view, i2);
                        if (ChatAttachAlertPollLayout.this.smoothScrollToOption) {
                            iCalculateDyToMakeVisible += AndroidUtilities.m1036dp(160.0f);
                        }
                        if (!ChatAttachAlertPollLayout.this.smoothScrollToOption) {
                            iCalculateDyToMakeVisible -= (ChatAttachAlertPollLayout.this.topPadding - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(7.0f);
                        }
                        if (ChatAttachAlertPollLayout.this.smoothScrollToOption && iCalculateDyToMakeVisible == 0 && ChatAttachAlertPollLayout.this.showMediaHintIndexAfterSmoothScroll >= 0) {
                            ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                            chatAttachAlertPollLayout.showMediaHint(chatAttachAlertPollLayout.showMediaHintIndexAfterSmoothScroll);
                            ChatAttachAlertPollLayout.this.showMediaHintIndexAfterSmoothScroll = -1;
                        }
                        ChatAttachAlertPollLayout.this.smoothScrollToOption = false;
                        return iCalculateDyToMakeVisible;
                    }

                    @Override // androidx.recyclerview.widget.LinearSmoothScroller
                    public int calculateTimeForDeceleration(int i2) {
                        return super.calculateTimeForDeceleration(i2) * 2;
                    }
                };
                anonymousClass1.setTargetPosition(i);
                startSmoothScroll(anonymousClass1);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
            public int[] getChildRectangleOnScreenScrollAmount(View view, Rect rect) {
                int height = getHeight() - getPaddingBottom();
                int top = (view.getTop() + rect.top) - view.getScrollY();
                int iHeight = rect.height() + top;
                int iMin = Math.min(0, top);
                int iMax = Math.max(0, iHeight - height);
                if (iMin == 0) {
                    iMin = Math.min(top, iMax);
                }
                return new int[]{0, iMin};
            }
        };
        this.layoutManager = c41464;
        c414422.setLayoutManager(c41464);
        c41464.setSkipFirstItem();
        new ItemTouchHelper(new TouchHelperCallback()).attachToRecyclerView(c414422);
        addView(c414422, LayoutHelper.createFrame(-1, -1, 51));
        c414422.setPreserveFocusAfterLayout(true);
        c414422.setAdapter(listAdapter);
        c414422.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda6
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i) {
                this.f$0.lambda$new$4(resourcesProvider, chatAttachAlert, context2, view, i);
            }
        });
        c414422.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.6
            public C41486() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                chatAttachAlertPollLayout.parentAlert.updateLayout(chatAttachAlertPollLayout, true, i2);
                if (ChatAttachAlertPollLayout.this.suggestEmojiPanel != null && ChatAttachAlertPollLayout.this.suggestEmojiPanel.isShown()) {
                    SuggestEmojiView.AnchorViewDelegate delegate = ChatAttachAlertPollLayout.this.suggestEmojiPanel.getDelegate();
                    boolean z2 = delegate instanceof PollEditTextCell;
                    ChatAttachAlertPollLayout chatAttachAlertPollLayout2 = ChatAttachAlertPollLayout.this;
                    if (z2) {
                        RecyclerView.ViewHolder viewHolderFindContainingViewHolder = chatAttachAlertPollLayout2.listView.findContainingViewHolder((PollEditTextCell) delegate);
                        if (viewHolderFindContainingViewHolder != null) {
                            int adapterPosition = viewHolderFindContainingViewHolder.getAdapterPosition();
                            int direction = ChatAttachAlertPollLayout.this.suggestEmojiPanel.getDirection();
                            ChatAttachAlertPollLayout chatAttachAlertPollLayout3 = ChatAttachAlertPollLayout.this;
                            if (direction == 0) {
                                chatAttachAlertPollLayout3.suggestEmojiPanel.setTranslationY((viewHolderFindContainingViewHolder.itemView.getY() - AndroidUtilities.m1036dp(166.0f)) + viewHolderFindContainingViewHolder.itemView.getMeasuredHeight());
                            } else {
                                chatAttachAlertPollLayout3.suggestEmojiPanel.setTranslationY(viewHolderFindContainingViewHolder.itemView.getY());
                            }
                            if (adapterPosition < ChatAttachAlertPollLayout.this.layoutManager.findFirstVisibleItemPosition() || adapterPosition > ChatAttachAlertPollLayout.this.layoutManager.findLastVisibleItemPosition()) {
                                ChatAttachAlertPollLayout.this.suggestEmojiPanel.forceClose();
                            }
                        } else {
                            ChatAttachAlertPollLayout.this.suggestEmojiPanel.forceClose();
                        }
                    } else {
                        chatAttachAlertPollLayout2.suggestEmojiPanel.forceClose();
                    }
                }
                if (i2 == 0 || ChatAttachAlertPollLayout.this.hintView == null) {
                    return;
                }
                ChatAttachAlertPollLayout.this.hintView.hide();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                RecyclerListView.Holder holder;
                int top;
                if (i == 0) {
                    int iM1036dp = AndroidUtilities.m1036dp(13.0f);
                    int backgroundPaddingTop = ChatAttachAlertPollLayout.this.parentAlert.getBackgroundPaddingTop();
                    if (((ChatAttachAlertPollLayout.this.parentAlert.scrollOffsetY[0] - backgroundPaddingTop) - iM1036dp) + backgroundPaddingTop < ActionBar.getCurrentActionBarHeight() && (holder = (RecyclerListView.Holder) ChatAttachAlertPollLayout.this.listView.findViewHolderForAdapterPosition(1)) != null && (top = (holder.itemView.getTop() - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(65.0f)) > 0) {
                        ChatAttachAlertPollLayout.this.listView.smoothScrollBy(0, top);
                    }
                    if (ChatAttachAlertPollLayout.this.showMediaHintIndexAfterSmoothScroll >= 0) {
                        ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                        chatAttachAlertPollLayout.showMediaHint(chatAttachAlertPollLayout.showMediaHintIndexAfterSmoothScroll);
                        ChatAttachAlertPollLayout.this.showMediaHintIndexAfterSmoothScroll = -1;
                    }
                }
            }
        });
        HintView hintView = new HintView(context2, 4);
        this.hintView = hintView;
        hintView.setAlpha(0.0f);
        this.hintView.setVisibility(4);
        addView(this.hintView, LayoutHelper.createFrame(-2, -2.0f, 51, 19.0f, 0.0f, 19.0f, 0.0f));
        this.MAX_CAPTION_LENGTH = MessagesController.getInstance(this.parentAlert.currentAccount).config.pollCaptionLengthMax.get();
        this.webPageLoader = new WebPageLoader(this.parentAlert.currentAccount);
        NotificationCenter.getInstance(this.parentAlert.currentAccount).addObserver(this, NotificationCenter.didReceivedWebpagesInUpdates);
        if (zIsPremium) {
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
            C41497 c41497 = new SuggestEmojiView(context2, this.parentAlert.currentAccount, null, resourcesProvider) { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.7
                @Override // org.telegram.p035ui.Components.SuggestEmojiView
                public int emojiCacheType() {
                    return 3;
                }

                public C41497(final Context context2, int i, SuggestEmojiView.AnchorViewDelegate anchorViewDelegate, final Theme.ResourcesProvider resourcesProvider2) {
                    super(context2, i, anchorViewDelegate, resourcesProvider2);
                }
            };
            this.suggestEmojiPanel = c41497;
            c41497.forbidCopy();
            this.suggestEmojiPanel.forbidSetAsStatus();
            this.suggestEmojiPanel.setHorizontalPadding(AndroidUtilities.m1036dp(24.0f));
            addView(this.suggestEmojiPanel, LayoutHelper.createFrame(-2, 160, 51));
        }
        this.keyboardNotifier = new KeyboardNotifier(this.parentAlert.sizeNotifierFrameLayout, null);
        checkDoneButton();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$2 */
    public class C41442 extends RecyclerListView {
        public C41442(final Context context2) {
            super(context2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView
        public void requestChildOnScreen(View view, View view2) {
            if (view instanceof PollEditTextCell) {
                super.requestChildOnScreen(view, view2);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.ViewParent
        public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z2) {
            rect.bottom += AndroidUtilities.m1036dp(60.0f);
            return super.requestChildRectangleOnScreen(view, rect, z2);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$3 */
    public class C41453 extends DefaultItemAnimator {
        public C41453() {
        }

        @Override // androidx.recyclerview.widget.DefaultItemAnimator
        public void onMoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.getAdapterPosition() == 0) {
                ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                chatAttachAlertPollLayout.parentAlert.updateLayout(chatAttachAlertPollLayout, true, 0);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$4 */
    public class C41464 extends FillLastLinearLayoutManager {
        public C41464(final Context context2, int i, boolean z2, int i2, RecyclerView c414422) {
            super(context2, i, z2, i2, c414422);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$4$1 */
        public class AnonymousClass1 extends LinearSmoothScroller {
            public AnonymousClass1(Context context2) {
                super(context2);
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateDyToMakeVisible(View view, int i2) {
                if (ChatAttachAlertPollLayout.this.smoothScrollToOption) {
                    i2 = -1;
                }
                int iCalculateDyToMakeVisible = super.calculateDyToMakeVisible(view, i2);
                if (ChatAttachAlertPollLayout.this.smoothScrollToOption) {
                    iCalculateDyToMakeVisible += AndroidUtilities.m1036dp(160.0f);
                }
                if (!ChatAttachAlertPollLayout.this.smoothScrollToOption) {
                    iCalculateDyToMakeVisible -= (ChatAttachAlertPollLayout.this.topPadding - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(7.0f);
                }
                if (ChatAttachAlertPollLayout.this.smoothScrollToOption && iCalculateDyToMakeVisible == 0 && ChatAttachAlertPollLayout.this.showMediaHintIndexAfterSmoothScroll >= 0) {
                    ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                    chatAttachAlertPollLayout.showMediaHint(chatAttachAlertPollLayout.showMediaHintIndexAfterSmoothScroll);
                    ChatAttachAlertPollLayout.this.showMediaHintIndexAfterSmoothScroll = -1;
                }
                ChatAttachAlertPollLayout.this.smoothScrollToOption = false;
                return iCalculateDyToMakeVisible;
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateTimeForDeceleration(int i2) {
                return super.calculateTimeForDeceleration(i2) * 2;
            }
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
            AnonymousClass1 anonymousClass1 = new LinearSmoothScroller(recyclerView.getContext()) { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.4.1
                public AnonymousClass1(Context context2) {
                    super(context2);
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public int calculateDyToMakeVisible(View view, int i2) {
                    if (ChatAttachAlertPollLayout.this.smoothScrollToOption) {
                        i2 = -1;
                    }
                    int iCalculateDyToMakeVisible = super.calculateDyToMakeVisible(view, i2);
                    if (ChatAttachAlertPollLayout.this.smoothScrollToOption) {
                        iCalculateDyToMakeVisible += AndroidUtilities.m1036dp(160.0f);
                    }
                    if (!ChatAttachAlertPollLayout.this.smoothScrollToOption) {
                        iCalculateDyToMakeVisible -= (ChatAttachAlertPollLayout.this.topPadding - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(7.0f);
                    }
                    if (ChatAttachAlertPollLayout.this.smoothScrollToOption && iCalculateDyToMakeVisible == 0 && ChatAttachAlertPollLayout.this.showMediaHintIndexAfterSmoothScroll >= 0) {
                        ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                        chatAttachAlertPollLayout.showMediaHint(chatAttachAlertPollLayout.showMediaHintIndexAfterSmoothScroll);
                        ChatAttachAlertPollLayout.this.showMediaHintIndexAfterSmoothScroll = -1;
                    }
                    ChatAttachAlertPollLayout.this.smoothScrollToOption = false;
                    return iCalculateDyToMakeVisible;
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public int calculateTimeForDeceleration(int i2) {
                    return super.calculateTimeForDeceleration(i2) * 2;
                }
            };
            anonymousClass1.setTargetPosition(i);
            startSmoothScroll(anonymousClass1);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public int[] getChildRectangleOnScreenScrollAmount(View view, Rect rect) {
            int height = getHeight() - getPaddingBottom();
            int top = (view.getTop() + rect.top) - view.getScrollY();
            int iHeight = rect.height() + top;
            int iMin = Math.min(0, top);
            int iMax = Math.max(0, iHeight - height);
            if (iMin == 0) {
                iMin = Math.min(top, iMax);
            }
            return new int[]{0, iMin};
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
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
    public /* synthetic */ void lambda$new$4(final Theme.ResourcesProvider resourcesProvider, ChatAttachAlert chatAttachAlert, final Context context, final View view, int i) {
        boolean z;
        boolean z2;
        if (i == this.poll2vLimitByCountryListRow) {
            CountrySelectBottomSheet countrySelectBottomSheet = new CountrySelectBottomSheet(getContext(), resourcesProvider);
            countrySelectBottomSheet.setListener(new CountrySelectBottomSheet.Listener() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.5
                public C41475() {
                }

                @Override // org.telegram.ui.Components.poll.sheets.CountrySelectBottomSheet.Listener
                public void onCountrySelected(List<String> list) {
                    ChatAttachAlertPollLayout.this.countriesList.clear();
                    ChatAttachAlertPollLayout.this.countriesList.addAll(list);
                    if (ChatAttachAlertPollLayout.this.poll2vLimitByCountryListRow >= 0) {
                        ChatAttachAlertPollLayout.this.listAdapter.notifyItemChanged(ChatAttachAlertPollLayout.this.poll2vLimitByCountryListRow);
                    }
                }
            });
            countrySelectBottomSheet.prepare(this.countriesList);
            countrySelectBottomSheet.show();
            return;
        }
        if (i == this.poll2vLimitDurationTimeRow) {
            ItemOptions itemOptionsMakeOptions = ItemOptions.makeOptions(chatAttachAlert.container, resourcesProvider, view);
            int i2 = 0;
            while (true) {
                int[] iArr = this.POLL_DURATION_OPTIONS;
                if (i2 < iArr.length) {
                    final int i3 = iArr[i2];
                    TimerDrawable ttlIcon = TimerDrawable.getTtlIcon(i3);
                    ttlIcon.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_actionBarDefaultSubmenuItemIcon), PorterDuff.Mode.SRC_IN));
                    itemOptionsMakeOptions.add(ttlIcon, LocaleController.formatPluralString("Hours", i3 / 3600, new Object[0]), new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$new$0(i3, view);
                        }
                    });
                    i2++;
                } else {
                    itemOptionsMakeOptions.add(C2797R.drawable.msg_customize, LocaleController.getString(C2797R.string.PollV2PollDurationOptionCustom), new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$new$3(context, view, resourcesProvider);
                        }
                    });
                    itemOptionsMakeOptions.setDrawScrim(false);
                    itemOptionsMakeOptions.setDimAlpha(0);
                    itemOptionsMakeOptions.show();
                    return;
                }
            }
        } else {
            if (i == this.addAnswerRow) {
                addNewField();
                return;
            }
            boolean z3 = view instanceof TextCheckCell;
            if (z3 || (view instanceof PollCreateCheckCell)) {
                boolean z4 = this.quizPoll;
                SuggestEmojiView suggestEmojiView = this.suggestEmojiPanel;
                if (suggestEmojiView != null) {
                    suggestEmojiView.forceClose();
                }
                ToggleRow[] toggleRowArr = this.toggleRows;
                int length = toggleRowArr.length;
                int i4 = 0;
                while (true) {
                    if (i4 >= length) {
                        z = false;
                        z2 = 0;
                        break;
                    }
                    ToggleRow toggleRow = toggleRowArr[i4];
                    if (i == toggleRow.row) {
                        boolean z5 = toggleRow.checked;
                        boolean z6 = !z5;
                        toggleRow.checked = z6;
                        if (i == this.poll2vLimitByCountryRow.row) {
                            this.listView.setItemAnimator(this.itemAnimator);
                            this.poll2vLimitByCountryRow.setDivider(z6);
                            ToggleRow toggleRow2 = this.poll2vLimitByCountryRow;
                            if (!z5) {
                                toggleRow2.addRows(1);
                            } else {
                                toggleRow2.removeRows(1);
                            }
                            updateRows();
                        }
                        z = true;
                        z2 = z6;
                    } else {
                        i4++;
                    }
                }
                if (!z) {
                    if (i == this.poll2vAnonymousRow) {
                        z2 = this.anonymousPoll;
                        this.anonymousPoll = !z2;
                        checkAllowAddingOptionsRow();
                    } else {
                        int i5 = this.allowAddingRow;
                        if (i == i5) {
                            z2 = !this.allowAdding;
                            this.allowAdding = z2;
                        } else if (i == this.poll2vAllowAddingRow) {
                            if (!this.quizPoll && !this.anonymousPoll) {
                                this.allowAddingOptions = !this.allowAddingOptions;
                            }
                            z2 = this.allowAddingOptions;
                        } else if (i == this.poll2vShuffleRow) {
                            z2 = !this.shuffleOptions;
                            this.shuffleOptions = z2;
                        } else if (i == this.poll2vLimitDurationRow) {
                            if (this.pollLimitDuration == 0 && this.pollLimitDeadline == 0) {
                                this.pollLimitDuration = 86400;
                                this.pollLimitDeadline = 0;
                                int i6 = this.poll2vLimitDurationTimeRow;
                                updateRows();
                                if (i6 < 0) {
                                    RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.listView.findViewHolderForAdapterPosition(this.poll2vLimitDurationRow);
                                    if (viewHolderFindViewHolderForAdapterPosition != null) {
                                        View view2 = viewHolderFindViewHolderForAdapterPosition.itemView;
                                        if (view2 instanceof PollCreateCheckCell) {
                                            ((PollCreateCheckCell) view2).setDivider(true);
                                        }
                                    }
                                    this.listView.setItemAnimator(this.itemAnimator);
                                    this.listAdapter.notifyItemRangeInserted(this.poll2vLimitDurationTimeRow, 3);
                                }
                            } else {
                                this.pollLimitDuration = 0;
                                this.pollLimitDeadline = 0;
                                int i7 = this.poll2vLimitDurationTimeRow;
                                updateRows();
                                this.listView.setItemAnimator(this.itemAnimator);
                                this.listAdapter.notifyItemRangeRemoved(i7, 3);
                                RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition2 = this.listView.findViewHolderForAdapterPosition(this.poll2vLimitDurationRow);
                                if (viewHolderFindViewHolderForAdapterPosition2 != null) {
                                    View view3 = viewHolderFindViewHolderForAdapterPosition2.itemView;
                                    if (view3 instanceof PollCreateCheckCell) {
                                        ((PollCreateCheckCell) view3).setDivider(false);
                                    }
                                }
                            }
                            z2 = (this.pollLimitDuration == 0 && this.pollLimitDeadline == 0) ? 0 : 1;
                        } else if (i == this.poll2vAllowRevotingRow) {
                            z2 = !this.allowRevoting;
                            this.allowRevoting = z2;
                        } else if (i == this.allowMarkingRow) {
                            z2 = !this.allowMarking;
                            this.allowMarking = z2;
                            updateRows();
                            int i8 = this.allowAddingRow;
                            if (i8 >= 0 && i5 < 0) {
                                this.listView.setItemAnimator(this.itemAnimator);
                                this.listAdapter.notifyItemInserted(this.allowAddingRow);
                            } else if (i5 >= 0 && i8 < 0) {
                                this.listView.setItemAnimator(this.itemAnimator);
                                this.listAdapter.notifyItemRemoved(i5);
                            }
                        } else if (i == this.poll2vMultipleRow) {
                            boolean z7 = this.multipleChoise;
                            z2 = !z7;
                            this.multipleChoise = z2;
                            if (z7 && this.quizPoll) {
                                int i9 = 0;
                                boolean z8 = false;
                                while (true) {
                                    boolean[] zArr = this.answersChecks;
                                    if (i9 >= zArr.length) {
                                        break;
                                    }
                                    if (z8) {
                                        zArr[i9] = false;
                                    } else if (zArr[i9]) {
                                        z8 = true;
                                    }
                                    i9++;
                                    z8 = z8;
                                }
                            }
                            int childCount = this.listView.getChildCount();
                            while (i < childCount) {
                                RecyclerListView recyclerListView = this.listView;
                                RecyclerView.ViewHolder childViewHolder = recyclerListView.getChildViewHolder(recyclerListView.getChildAt(i));
                                if (childViewHolder.getItemViewType() == 5) {
                                    ((PollEditTextCell) childViewHolder.itemView).setCheckboxMultiselect(this.multipleChoise, true);
                                }
                                i++;
                            }
                        } else if (i == this.poll2vLimitDurationHideResultsRow) {
                            z2 = !this.hideResults;
                            this.hideResults = z2;
                        } else if (i == this.poll2vQuizRow) {
                            if (this.quizOnly) {
                                return;
                            }
                            this.listView.setItemAnimator(this.itemAnimator);
                            z2 = !this.quizPoll;
                            this.quizPoll = z2;
                            int i10 = this.solutionRowHeader;
                            updateRows();
                            boolean z9 = this.quizPoll;
                            ListAdapter listAdapter = this.listAdapter;
                            if (z9) {
                                listAdapter.notifyItemRangeInserted(this.solutionRowHeader, 3);
                            } else {
                                listAdapter.notifyItemRangeRemoved(i10, 3);
                            }
                            this.listAdapter.notifyItemChanged(this.emptyRow);
                            if (this.quizPoll) {
                                this.allowRevoting = false;
                                int i11 = this.poll2vAllowRevotingRow;
                                if (i11 >= 0) {
                                    RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition3 = this.listView.findViewHolderForAdapterPosition(i11);
                                    if (viewHolderFindViewHolderForAdapterPosition3 != null) {
                                        ((PollCreateCheckCell) viewHolderFindViewHolderForAdapterPosition3.itemView).setChecked(false);
                                    } else {
                                        this.listAdapter.notifyItemChanged(this.poll2vAllowRevotingRow);
                                    }
                                }
                            } else {
                                int i12 = this.poll2vAllowRevotingRow;
                                if (i12 >= 0 && this.listView.findViewHolderForAdapterPosition(i12) == null) {
                                    this.listAdapter.notifyItemChanged(this.poll2vAllowRevotingRow);
                                }
                            }
                            checkAllowAddingOptionsRow();
                            if (this.quizPoll && !this.multipleChoise) {
                                int i13 = 0;
                                boolean z10 = false;
                                while (true) {
                                    boolean[] zArr2 = this.answersChecks;
                                    if (i13 >= zArr2.length) {
                                        break;
                                    }
                                    if (z10) {
                                        zArr2[i13] = false;
                                    } else if (zArr2[i13]) {
                                        z10 = true;
                                    }
                                    i13++;
                                    z10 = z10;
                                }
                            }
                        }
                    }
                }
                if (this.hintShowed && !this.quizPoll) {
                    this.hintView.hide();
                }
                this.listView.getChildCount();
                for (int i14 = this.answerStartRow; i14 < this.answerStartRow + this.answersCount; i14++) {
                    RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition4 = this.listView.findViewHolderForAdapterPosition(i14);
                    if (viewHolderFindViewHolderForAdapterPosition4 != null) {
                        View view4 = viewHolderFindViewHolderForAdapterPosition4.itemView;
                        if (view4 instanceof PollEditTextCell) {
                            PollEditTextCell pollEditTextCell = (PollEditTextCell) view4;
                            pollEditTextCell.setShowCheckBox(this.quizPoll, true);
                            pollEditTextCell.setChecked(this.answersChecks[i14 - this.answerStartRow], z4);
                            if (pollEditTextCell.getTop() > AndroidUtilities.m1036dp(40.0f) && i == this.poll2vQuizRow && !this.hintShowed) {
                                this.hintView.setText(LocaleController.getString(C2797R.string.PollTapToSelect));
                                this.hintView.showForView(pollEditTextCell.getCheckBox(), true);
                                this.hintShowed = true;
                            }
                        }
                    }
                }
                if (z3) {
                    ((TextCheckCell) view).setChecked(z2);
                } else if (view instanceof PollCreateCheckCell) {
                    ((PollCreateCheckCell) view).setChecked(z2);
                }
                checkDoneButton();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$5 */
    public class C41475 implements CountrySelectBottomSheet.Listener {
        public C41475() {
        }

        @Override // org.telegram.ui.Components.poll.sheets.CountrySelectBottomSheet.Listener
        public void onCountrySelected(List<String> list) {
            ChatAttachAlertPollLayout.this.countriesList.clear();
            ChatAttachAlertPollLayout.this.countriesList.addAll(list);
            if (ChatAttachAlertPollLayout.this.poll2vLimitByCountryListRow >= 0) {
                ChatAttachAlertPollLayout.this.listAdapter.notifyItemChanged(ChatAttachAlertPollLayout.this.poll2vLimitByCountryListRow);
            }
        }
    }

    public /* synthetic */ void lambda$new$0(int i, View view) {
        this.pollLimitDeadline = 0;
        this.pollLimitDuration = i;
        if (view instanceof TextCell) {
            checkDurationInfoRow((TextCell) view, true);
        } else {
            this.listAdapter.notifyItemChanged(this.poll2vLimitDurationTimeRow);
        }
    }

    public /* synthetic */ void lambda$new$3(Context context, final View view, Theme.ResourcesProvider resourcesProvider) {
        AlertsCreator.createPollCloseDatePickerDialog(context, this.pollLimitDeadline, new AlertsCreator.ScheduleDatePickerDelegate() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda12
            @Override // org.telegram.ui.Components.AlertsCreator.ScheduleDatePickerDelegate
            public final void didSelectDate(boolean z, int i, int i2) {
                this.f$0.lambda$new$1(view, z, i, i2);
            }
        }, new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                ChatAttachAlertPollLayout.m11103$r8$lambda$9w1_RlEquHPRRrWP2y0Rh0aFE();
            }
        }, new AlertsCreator.ScheduleDatePickerColors(resourcesProvider), resourcesProvider);
    }

    public /* synthetic */ void lambda$new$1(View view, boolean z, int i, int i2) {
        if (z) {
            this.pollLimitDeadline = i;
            this.pollLimitDuration = 0;
            if (view instanceof TextCell) {
                checkDurationInfoRow((TextCell) view, true);
            } else {
                this.listAdapter.notifyItemChanged(this.poll2vLimitDurationTimeRow);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$6 */
    public class C41486 extends RecyclerView.OnScrollListener {
        public C41486() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
            chatAttachAlertPollLayout.parentAlert.updateLayout(chatAttachAlertPollLayout, true, i2);
            if (ChatAttachAlertPollLayout.this.suggestEmojiPanel != null && ChatAttachAlertPollLayout.this.suggestEmojiPanel.isShown()) {
                SuggestEmojiView.AnchorViewDelegate delegate = ChatAttachAlertPollLayout.this.suggestEmojiPanel.getDelegate();
                boolean z2 = delegate instanceof PollEditTextCell;
                ChatAttachAlertPollLayout chatAttachAlertPollLayout2 = ChatAttachAlertPollLayout.this;
                if (z2) {
                    RecyclerView.ViewHolder viewHolderFindContainingViewHolder = chatAttachAlertPollLayout2.listView.findContainingViewHolder((PollEditTextCell) delegate);
                    if (viewHolderFindContainingViewHolder != null) {
                        int adapterPosition = viewHolderFindContainingViewHolder.getAdapterPosition();
                        int direction = ChatAttachAlertPollLayout.this.suggestEmojiPanel.getDirection();
                        ChatAttachAlertPollLayout chatAttachAlertPollLayout3 = ChatAttachAlertPollLayout.this;
                        if (direction == 0) {
                            chatAttachAlertPollLayout3.suggestEmojiPanel.setTranslationY((viewHolderFindContainingViewHolder.itemView.getY() - AndroidUtilities.m1036dp(166.0f)) + viewHolderFindContainingViewHolder.itemView.getMeasuredHeight());
                        } else {
                            chatAttachAlertPollLayout3.suggestEmojiPanel.setTranslationY(viewHolderFindContainingViewHolder.itemView.getY());
                        }
                        if (adapterPosition < ChatAttachAlertPollLayout.this.layoutManager.findFirstVisibleItemPosition() || adapterPosition > ChatAttachAlertPollLayout.this.layoutManager.findLastVisibleItemPosition()) {
                            ChatAttachAlertPollLayout.this.suggestEmojiPanel.forceClose();
                        }
                    } else {
                        ChatAttachAlertPollLayout.this.suggestEmojiPanel.forceClose();
                    }
                } else {
                    chatAttachAlertPollLayout2.suggestEmojiPanel.forceClose();
                }
            }
            if (i2 == 0 || ChatAttachAlertPollLayout.this.hintView == null) {
                return;
            }
            ChatAttachAlertPollLayout.this.hintView.hide();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            RecyclerListView.Holder holder;
            int top;
            if (i == 0) {
                int iM1036dp = AndroidUtilities.m1036dp(13.0f);
                int backgroundPaddingTop = ChatAttachAlertPollLayout.this.parentAlert.getBackgroundPaddingTop();
                if (((ChatAttachAlertPollLayout.this.parentAlert.scrollOffsetY[0] - backgroundPaddingTop) - iM1036dp) + backgroundPaddingTop < ActionBar.getCurrentActionBarHeight() && (holder = (RecyclerListView.Holder) ChatAttachAlertPollLayout.this.listView.findViewHolderForAdapterPosition(1)) != null && (top = (holder.itemView.getTop() - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(65.0f)) > 0) {
                    ChatAttachAlertPollLayout.this.listView.smoothScrollBy(0, top);
                }
                if (ChatAttachAlertPollLayout.this.showMediaHintIndexAfterSmoothScroll >= 0) {
                    ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                    chatAttachAlertPollLayout.showMediaHint(chatAttachAlertPollLayout.showMediaHintIndexAfterSmoothScroll);
                    ChatAttachAlertPollLayout.this.showMediaHintIndexAfterSmoothScroll = -1;
                }
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$7 */
    public class C41497 extends SuggestEmojiView {
        @Override // org.telegram.p035ui.Components.SuggestEmojiView
        public int emojiCacheType() {
            return 3;
        }

        public C41497(final Context context2, int i, SuggestEmojiView.AnchorViewDelegate anchorViewDelegate, final Theme.ResourcesProvider resourcesProvider2) {
            super(context2, i, anchorViewDelegate, resourcesProvider2);
        }
    }

    private void checkAllowAddingOptionsRow() {
        boolean z = (this.quizPoll || this.anonymousPoll) ? false : true;
        if (!z) {
            this.allowAddingOptions = false;
        }
        int i = this.poll2vAllowAddingRow;
        if (i < 0) {
            return;
        }
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.listView.findViewHolderForAdapterPosition(i);
        if (viewHolderFindViewHolderForAdapterPosition == null) {
            this.listAdapter.notifyItemChanged(this.poll2vAllowAddingRow);
            return;
        }
        PollCreateCheckCell pollCreateCheckCell = (PollCreateCheckCell) viewHolderFindViewHolderForAdapterPosition.itemView;
        if (!z) {
            pollCreateCheckCell.setChecked(false);
        }
        pollCreateCheckCell.getCheckBox().setIconVisible(!z, true);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onPause() {
        super.onPause();
        ListAdapter listAdapter = this.listAdapter;
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
        if (this.isPremium) {
            hideEmojiPopup(false);
            SuggestEmojiView suggestEmojiView = this.suggestEmojiPanel;
            if (suggestEmojiView != null) {
                suggestEmojiView.forceClose();
            }
            PollEditTextCell pollEditTextCell = this.currentCell;
            if (pollEditTextCell != null) {
                pollEditTextCell.setEmojiButtonVisibility(false);
                this.currentCell.getTextView().clearFocus();
                AndroidUtilities.hideKeyboard(this.currentCell.getEditField());
            }
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onHideShowProgress(float f) {
        this.parentAlert.updateDoneItemEnabled();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onMenuItemClick(int i) {
        if (i == 40) {
            if (this.todo) {
                onTodoDoneButtonClick();
            } else {
                onPollDoneButtonClick();
            }
        }
    }

    private void onTodoDoneButtonClick() {
        CharSequence[] charSequenceArr = {getFixedString(this.questionString)};
        ArrayList<TLRPC.MessageEntity> entities = MediaDataController.getInstance(this.parentAlert.currentAccount).getEntities(charSequenceArr, true);
        CharSequence charSequence = charSequenceArr[0];
        if (entities != null) {
            int size = entities.size();
            for (int i = 0; i < size; i++) {
                TLRPC.MessageEntity messageEntity = entities.get(i);
                if (messageEntity.offset + messageEntity.length > charSequence.length()) {
                    messageEntity.length = charSequence.length() - messageEntity.offset;
                }
            }
        }
        final TLRPC.TL_messageMediaToDo tL_messageMediaToDo = new TLRPC.TL_messageMediaToDo();
        TLRPC.TodoList todoList = new TLRPC.TodoList();
        tL_messageMediaToDo.todo = todoList;
        boolean z = this.allowMarking;
        todoList.others_can_append = z && this.allowAdding;
        todoList.others_can_complete = z;
        todoList.title = new TLRPC.TL_textWithEntities();
        tL_messageMediaToDo.todo.title.text = charSequence.toString();
        tL_messageMediaToDo.todo.title.entities = entities;
        int i2 = 0;
        while (true) {
            CharSequence[] charSequenceArr2 = this.answers;
            if (i2 < charSequenceArr2.length) {
                if (!TextUtils.isEmpty(getFixedString(charSequenceArr2[i2]))) {
                    CharSequence[] charSequenceArr3 = {getFixedString(this.answers[i2])};
                    ArrayList<TLRPC.MessageEntity> entities2 = MediaDataController.getInstance(this.parentAlert.currentAccount).getEntities(charSequenceArr3, true);
                    CharSequence charSequence2 = charSequenceArr3[0];
                    if (entities2 != null) {
                        int size2 = entities2.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            TLRPC.MessageEntity messageEntity2 = entities2.get(i3);
                            if (messageEntity2.offset + messageEntity2.length > charSequence2.length()) {
                                messageEntity2.length = charSequence2.length() - messageEntity2.offset;
                            }
                        }
                    }
                    TLRPC.TodoItem todoItem = new TLRPC.TodoItem();
                    TLRPC.TL_textWithEntities tL_textWithEntities = new TLRPC.TL_textWithEntities();
                    todoItem.title = tL_textWithEntities;
                    tL_textWithEntities.text = charSequence2.toString();
                    todoItem.title.entities = entities2;
                    todoItem.f1405id = tL_messageMediaToDo.todo.list.size() + 1;
                    tL_messageMediaToDo.todo.list.add(todoItem);
                }
                i2++;
            } else {
                ChatAttachAlert chatAttachAlert = this.parentAlert;
                final ChatActivity chatActivity = (ChatActivity) chatAttachAlert.baseFragment;
                AlertsCreator.ensurePaidMessageConfirmation(chatAttachAlert.currentAccount, chatAttachAlert.getDialogId(), this.parentAlert.getAdditionalMessagesCount() + 1, new Utilities.Callback() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda4
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$onTodoDoneButtonClick$6(chatActivity, tL_messageMediaToDo, (Long) obj);
                    }
                });
                return;
            }
        }
    }

    public /* synthetic */ void lambda$onTodoDoneButtonClick$6(ChatActivity chatActivity, final TLRPC.TL_messageMediaToDo tL_messageMediaToDo, final Long l) {
        if (chatActivity.isInScheduleMode()) {
            AlertsCreator.createScheduleDatePickerDialog(chatActivity.getParentActivity(), chatActivity.getDialogId(), new AlertsCreator.ScheduleDatePickerDelegate() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda11
                @Override // org.telegram.ui.Components.AlertsCreator.ScheduleDatePickerDelegate
                public final void didSelectDate(boolean z, int i, int i2) {
                    this.f$0.lambda$onTodoDoneButtonClick$5(tL_messageMediaToDo, l, z, i, i2);
                }
            });
        } else {
            this.delegate.sendPoll(tL_messageMediaToDo, null, null, null, true, 0, l.longValue());
            this.parentAlert.dismiss(true);
        }
    }

    public /* synthetic */ void lambda$onTodoDoneButtonClick$5(TLRPC.TL_messageMediaToDo tL_messageMediaToDo, Long l, boolean z, int i, int i2) {
        this.delegate.sendPoll(tL_messageMediaToDo, null, null, null, z, i, l.longValue());
        this.parentAlert.dismiss(true);
    }

    private void onPollDoneButtonClick() {
        if (this.quizPoll && !this.doneItemEnabled) {
            int i = 0;
            for (int i2 = 0; i2 < this.answersChecks.length; i2++) {
                if (!TextUtils.isEmpty(getFixedString(this.answers[i2])) && this.answersChecks[i2]) {
                    i++;
                }
            }
            if (i <= 0) {
                showQuizHint();
                return;
            }
            return;
        }
        int i3 = 0;
        while (true) {
            CharSequence[] charSequenceArr = this.answers;
            if (i3 < charSequenceArr.length) {
                if (TextUtils.isEmpty(getFixedString(charSequenceArr[i3])) && this.attachedMedia.get(i3) != null) {
                    this.smoothScrollToOption = true;
                    this.showMediaHintIndexAfterSmoothScroll = i3;
                    this.listView.smoothScrollToPosition(this.answerStartRow + i3);
                    return;
                }
                i3++;
            } else {
                CharSequence[] charSequenceArr2 = {getFixedString(this.questionString)};
                ArrayList<TLRPC.MessageEntity> entities = MediaDataController.getInstance(this.parentAlert.currentAccount).getEntities(charSequenceArr2, true);
                CharSequence charSequence = charSequenceArr2[0];
                if (entities != null) {
                    int size = entities.size();
                    for (int i4 = 0; i4 < size; i4++) {
                        TLRPC.MessageEntity messageEntity = entities.get(i4);
                        if (messageEntity.offset + messageEntity.length > charSequence.length()) {
                            messageEntity.length = charSequence.length() - messageEntity.offset;
                        }
                    }
                }
                final TLRPC.TL_messageMediaPoll tL_messageMediaPoll = new TLRPC.TL_messageMediaPoll();
                TLRPC.TL_poll tL_poll = new TLRPC.TL_poll();
                tL_messageMediaPoll.poll = tL_poll;
                tL_poll.multiple_choice = this.multipleChoise;
                tL_poll.quiz = this.quizPoll;
                tL_poll.public_voters = !this.anonymousPoll;
                tL_poll.open_answers = this.allowAddingOptions;
                tL_poll.revoting_disabled = !this.allowRevoting;
                tL_poll.shuffle_answers = this.shuffleOptions;
                tL_poll.subscribers_only = this.poll2vSubscribersOnlyRow.checked;
                if (this.poll2vLimitByCountryRow.checked && !this.countriesList.isEmpty()) {
                    TLRPC.Poll poll = tL_messageMediaPoll.poll;
                    poll.flags |= 4096;
                    poll.countries_iso2.addAll(this.countriesList);
                }
                TLRPC.Poll poll2 = tL_messageMediaPoll.poll;
                poll2.creator = true;
                int i5 = this.pollLimitDuration;
                if (i5 != 0) {
                    poll2.hide_results_until_close = this.hideResults;
                    poll2.close_period = i5;
                    poll2.flags |= 16;
                } else {
                    int i6 = this.pollLimitDeadline;
                    if (i6 != 0) {
                        poll2.hide_results_until_close = this.hideResults;
                        poll2.close_date = i6;
                        poll2.flags |= 32;
                    }
                }
                poll2.question = new TLRPC.TL_textWithEntities();
                tL_messageMediaPoll.poll.question.text = charSequence.toString();
                tL_messageMediaPoll.poll.question.entities = entities;
                final ArrayList arrayList = new ArrayList(this.maxAnswersCount);
                int i7 = 0;
                while (true) {
                    CharSequence[] charSequenceArr3 = this.answers;
                    if (i7 >= charSequenceArr3.length) {
                        break;
                    }
                    if (TextUtils.isEmpty(getFixedString(charSequenceArr3[i7]))) {
                        this.attachedMedia.removeAnswerAndShift(tL_messageMediaPoll.poll.answers.size());
                    } else {
                        CharSequence[] charSequenceArr4 = {getFixedString(this.answers[i7])};
                        ArrayList<TLRPC.MessageEntity> entities2 = MediaDataController.getInstance(this.parentAlert.currentAccount).getEntities(charSequenceArr4, true);
                        CharSequence charSequence2 = charSequenceArr4[0];
                        if (entities2 != null) {
                            int size2 = entities2.size();
                            for (int i8 = 0; i8 < size2; i8++) {
                                TLRPC.MessageEntity messageEntity2 = entities2.get(i8);
                                if (messageEntity2.offset + messageEntity2.length > charSequence2.length()) {
                                    messageEntity2.length = charSequence2.length() - messageEntity2.offset;
                                }
                            }
                        }
                        TLRPC.TL_pollAnswer tL_pollAnswer = new TLRPC.TL_pollAnswer();
                        TLRPC.TL_textWithEntities tL_textWithEntities = new TLRPC.TL_textWithEntities();
                        tL_pollAnswer.text = tL_textWithEntities;
                        tL_textWithEntities.text = charSequence2.toString();
                        tL_pollAnswer.text.entities = entities2;
                        tL_pollAnswer.option = new byte[]{(byte) (tL_messageMediaPoll.poll.answers.size() + 48)};
                        if ((this.multipleChoise || this.quizPoll) && this.answersChecks[i7]) {
                            arrayList.add(Integer.valueOf(tL_messageMediaPoll.poll.answers.size()));
                        }
                        tL_messageMediaPoll.poll.answers.add(tL_pollAnswer);
                    }
                    i7++;
                }
                tL_messageMediaPoll.results = new TLRPC.TL_pollResults();
                CharSequence fixedString = getFixedString(this.solutionString);
                if (fixedString != null) {
                    tL_messageMediaPoll.results.solution = fixedString.toString();
                    ArrayList<TLRPC.MessageEntity> entities3 = MediaDataController.getInstance(this.parentAlert.currentAccount).getEntities(new CharSequence[]{fixedString}, true);
                    if (entities3 != null && !entities3.isEmpty()) {
                        tL_messageMediaPoll.results.solution_entities = entities3;
                    }
                    if (!TextUtils.isEmpty(tL_messageMediaPoll.results.solution)) {
                        tL_messageMediaPoll.results.flags |= 16;
                    }
                }
                ChatAttachAlert chatAttachAlert = this.parentAlert;
                final ChatActivity chatActivity = (ChatActivity) chatAttachAlert.baseFragment;
                AlertsCreator.ensurePaidMessageConfirmation(chatAttachAlert.currentAccount, chatAttachAlert.getDialogId(), this.parentAlert.getAdditionalMessagesCount() + 1, new Utilities.Callback() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda7
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$onPollDoneButtonClick$8(chatActivity, tL_messageMediaPoll, arrayList, (Long) obj);
                    }
                });
                return;
            }
        }
    }

    public /* synthetic */ void lambda$onPollDoneButtonClick$8(ChatActivity chatActivity, final TLRPC.TL_messageMediaPoll tL_messageMediaPoll, final ArrayList arrayList, final Long l) {
        if (chatActivity.isInScheduleMode()) {
            AlertsCreator.createScheduleDatePickerDialog(chatActivity.getParentActivity(), chatActivity.getDialogId(), new AlertsCreator.ScheduleDatePickerDelegate() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda8
                @Override // org.telegram.ui.Components.AlertsCreator.ScheduleDatePickerDelegate
                public final void didSelectDate(boolean z, int i, int i2) {
                    this.f$0.lambda$onPollDoneButtonClick$7(tL_messageMediaPoll, arrayList, l, z, i, i2);
                }
            });
        } else {
            this.delegate.sendPoll(tL_messageMediaPoll, this.descriptionString, this.attachedMedia, arrayList, true, 0, l.longValue());
            this.parentAlert.dismiss(true);
        }
    }

    public /* synthetic */ void lambda$onPollDoneButtonClick$7(TLRPC.TL_messageMediaPoll tL_messageMediaPoll, ArrayList arrayList, Long l, boolean z, int i, int i2) {
        this.delegate.sendPoll(tL_messageMediaPoll, this.descriptionString, this.attachedMedia, arrayList, z, i, l.longValue());
        this.parentAlert.dismiss(true);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getCurrentItemTop() {
        View childAt;
        if (this.listView.getChildCount() <= 1 || (childAt = this.listView.getChildAt(1)) == null) {
            return Integer.MAX_VALUE;
        }
        RecyclerListView.Holder holder = (RecyclerListView.Holder) this.listView.findContainingViewHolder(childAt);
        int y = (((int) childAt.getY()) - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(20.0f);
        int i = (y <= 0 || holder == null || holder.getAdapterPosition() != 1) ? 0 : y;
        if (y < 0 || holder == null || holder.getAdapterPosition() != 1) {
            y = i;
        }
        return y + AndroidUtilities.m1036dp(25.0f);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getFirstOffset() {
        return getListTopPadding() + AndroidUtilities.m1036dp(17.0f);
    }

    @Override // android.view.View
    public void setTranslationY(float f) {
        super.setTranslationY(f);
        this.parentAlert.getSheetContainer().invalidate();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getListTopPadding() {
        return this.topPadding;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0032  */
    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onPreMeasure(int r3, int r4) {
        /*
            r2 = this;
            org.telegram.ui.Components.ChatAttachAlert r3 = r2.parentAlert
            org.telegram.ui.Components.SizeNotifierFrameLayout r3 = r3.sizeNotifierFrameLayout
            int r3 = r3.measureKeyboardHeight()
            r0 = 1101004800(0x41a00000, float:20.0)
            int r0 = org.telegram.messenger.AndroidUtilities.m1036dp(r0)
            r1 = 0
            if (r3 > r0) goto L48
            boolean r3 = r2.emojiViewVisible
            if (r3 != 0) goto L48
            boolean r3 = r2.isAnimatePopupClosing
            if (r3 != 0) goto L48
            boolean r3 = r2.isEmojiSearchOpened
            if (r3 == 0) goto L1e
            goto L48
        L1e:
            boolean r3 = org.telegram.messenger.AndroidUtilities.isTablet()
            if (r3 != 0) goto L32
            android.graphics.Point r3 = org.telegram.messenger.AndroidUtilities.displaySize
            int r0 = r3.x
            int r3 = r3.y
            if (r0 <= r3) goto L32
            float r3 = (float) r4
            r4 = 1080033280(0x40600000, float:3.5)
            float r3 = r3 / r4
            int r3 = (int) r3
            goto L36
        L32:
            int r4 = r4 / 5
            int r3 = r4 * 2
        L36:
            r4 = 1095761920(0x41500000, float:13.0)
            int r4 = org.telegram.messenger.AndroidUtilities.m1036dp(r4)
            int r3 = r3 - r4
            if (r3 >= 0) goto L40
            r3 = r1
        L40:
            org.telegram.ui.Components.ChatAttachAlert r4 = r2.parentAlert
            boolean r0 = r2.allowNesterScroll
            r4.setAllowNestedScroll(r0)
            goto L53
        L48:
            r3 = 1112539136(0x42500000, float:52.0)
            int r3 = org.telegram.messenger.AndroidUtilities.m1036dp(r3)
            org.telegram.ui.Components.ChatAttachAlert r4 = r2.parentAlert
            r4.setAllowNestedScroll(r1)
        L53:
            int r4 = org.telegram.messenger.AndroidUtilities.statusBarHeight
            int r3 = r3 + r4
            r4 = 1
            r2.ignoreLayout = r4
            int r4 = r2.topPadding
            if (r4 != r3) goto L67
            org.telegram.ui.Components.RecyclerListView r4 = r2.listView
            int r4 = r4.getPaddingBottom()
            int r0 = r2.listPaddingBottom
            if (r4 == r0) goto L7d
        L67:
            r2.topPadding = r3
            org.telegram.ui.Components.RecyclerListView r3 = r2.listView
            int r4 = r2.listPaddingBottom
            r3.setPaddingWithoutRequestLayout(r1, r1, r1, r4)
            org.telegram.ui.Components.RecyclerListView r3 = r2.listView
            r4 = 0
            r3.setItemAnimator(r4)
            org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter r3 = r2.listAdapter
            int r4 = r2.paddingRow
            r3.notifyItemChanged(r4)
        L7d:
            r2.ignoreLayout = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.ChatAttachAlertPollLayout.onPreMeasure(int, int):void");
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getButtonsHideOffset() {
        return AndroidUtilities.m1036dp(70.0f);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.ignoreLayout) {
            return;
        }
        super.requestLayout();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void scrollToTop() {
        this.listView.smoothScrollToPosition(1);
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.didReceivedWebpagesInUpdates) {
            this.webPageLoader.apply((LongSparseArray) objArr[0]);
            int size = this.attachedMedia.medias.size();
            for (int i3 = 0; i3 < size; i3++) {
                PollAttachedMedia pollAttachedMedia = this.attachedMedia.medias.get(i3);
                if (pollAttachedMedia instanceof PollAttachedMediaLink) {
                    checkPollLinkMedia((PollAttachedMediaLink) pollAttachedMedia, true);
                }
            }
            return;
        }
        if (i == NotificationCenter.emojiLoaded) {
            EmojiView emojiView = this.emojiView;
            if (emojiView != null) {
                emojiView.invalidateViews();
            }
            PollEditTextCell pollEditTextCell = this.currentCell;
            if (pollEditTextCell != null) {
                int currentTextColor = pollEditTextCell.getEditField().getCurrentTextColor();
                this.currentCell.getEditField().setTextColor(-1);
                this.currentCell.getEditField().setTextColor(currentTextColor);
            }
        }
    }

    public static CharSequence getFixedString(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        CharSequence trimmedString = AndroidUtilities.getTrimmedString(charSequence);
        while (TextUtils.indexOf(trimmedString, "\n\n\n") >= 0) {
            trimmedString = TextUtils.replace(trimmedString, new String[]{"\n\n\n"}, new CharSequence[]{"\n\n"});
        }
        while (TextUtils.indexOf(trimmedString, "\n\n\n") == 0) {
            trimmedString = TextUtils.replace(trimmedString, new String[]{"\n\n\n"}, new CharSequence[]{"\n\n"});
        }
        return trimmedString;
    }

    public void showMediaHint(int i) {
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.listView.findViewHolderForAdapterPosition(this.answerStartRow + i);
        if (viewHolderFindViewHolderForAdapterPosition != null) {
            View view = viewHolderFindViewHolderForAdapterPosition.itemView;
            if (view instanceof PollEditTextCell) {
                PollEditTextCell pollEditTextCell = (PollEditTextCell) view;
                if (pollEditTextCell.getTop() > AndroidUtilities.m1036dp(40.0f)) {
                    SuggestEmojiView suggestEmojiView = this.suggestEmojiPanel;
                    if (suggestEmojiView != null) {
                        suggestEmojiView.forceClose();
                    }
                    this.hintView.setText(LocaleController.getString(C2797R.string.PollAddTextOrRemoveMedia));
                    this.hintView.showForView(pollEditTextCell.getCheckBox(), true);
                    ImageView imageView = this.hintView.arrowImageView;
                    imageView.setTranslationX(imageView.getTranslationX() + AndroidUtilities.m1036dp(48.0f));
                    HintView hintView = this.hintView;
                    hintView.setTranslationY(hintView.getTranslationY() + AndroidUtilities.m1036dp(10.0f));
                }
            }
        }
    }

    private void showQuizHint() {
        for (int i = this.answerStartRow; i < this.answerStartRow + this.answersCount; i++) {
            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.listView.findViewHolderForAdapterPosition(i);
            if (viewHolderFindViewHolderForAdapterPosition != null) {
                View view = viewHolderFindViewHolderForAdapterPosition.itemView;
                if (view instanceof PollEditTextCell) {
                    PollEditTextCell pollEditTextCell = (PollEditTextCell) view;
                    if (pollEditTextCell.getTop() > AndroidUtilities.m1036dp(40.0f)) {
                        SuggestEmojiView suggestEmojiView = this.suggestEmojiPanel;
                        if (suggestEmojiView != null) {
                            suggestEmojiView.forceClose();
                        }
                        this.hintView.setText(LocaleController.getString(C2797R.string.PollTapToSelect));
                        this.hintView.showForView(pollEditTextCell.getCheckBox(), true);
                        return;
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public void checkDoneButton() {
        int i;
        if (this.quizPoll) {
            i = 0;
            for (int i2 = 0; i2 < this.answersChecks.length; i2++) {
                if (!TextUtils.isEmpty(getFixedString(this.answers[i2])) && this.answersChecks[i2]) {
                    i++;
                }
            }
        } else {
            i = 0;
        }
        int i3 = this.todo ? getMessagesController().todoTitleLengthMax : 255;
        int i4 = this.todo ? getMessagesController().todoItemLengthMax : 100;
        boolean z = (TextUtils.isEmpty(getFixedString(this.descriptionString)) || this.descriptionString.length() <= this.MAX_CAPTION_LENGTH) && (TextUtils.isEmpty(getFixedString(this.solutionString)) || this.solutionString.length() <= 200) && !TextUtils.isEmpty(getFixedString(this.questionString)) && this.questionString.length() <= i3;
        int i5 = 0;
        int i6 = 0;
        boolean z2 = false;
        while (true) {
            CharSequence[] charSequenceArr = this.answers;
            if (i5 >= charSequenceArr.length) {
                break;
            }
            if (!TextUtils.isEmpty(getFixedString(charSequenceArr[i5]))) {
                if (this.answers[i5].length() > i4) {
                    i6 = 0;
                    z2 = true;
                    break;
                } else {
                    i6++;
                    z2 = true;
                }
            }
            i5++;
        }
        if (i6 < 1 || (this.quizPoll && i < 1)) {
            z = false;
        }
        if (!TextUtils.isEmpty(this.solutionString) || !TextUtils.isEmpty(this.questionString) || !TextUtils.isEmpty(this.descriptionString) || z2 || this.attachedMedia.medias.size() > 0) {
            this.allowNesterScroll = false;
        } else {
            this.allowNesterScroll = true;
        }
        this.parentAlert.setAllowNestedScroll(this.allowNesterScroll);
        this.doneItemEnabled = z;
        this.parentAlert.updateDoneItemEnabled();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public boolean isDoneItemEnabled() {
        return this.doneItemEnabled;
    }

    public void updateRows() {
        this.solutionRowHeader = -1;
        this.solutionRow = -1;
        this.solutionInfoRow = -1;
        this.poll2vMultipleRow = -1;
        this.poll2vAnonymousRow = -1;
        this.poll2vLimitDurationRow = -1;
        this.poll2vLimitDurationTimeRow = -1;
        this.poll2vLimitDurationHideResultsRow = -1;
        this.poll2vLimitDurationHideResultsRowInfo = -1;
        this.poll2vAllowAddingRow = -1;
        this.poll2vShuffleRow = -1;
        this.poll2vAllowRevotingRow = -1;
        this.poll2vQuizRow = -1;
        this.poll2vSubscribersOnlyRow.row = -1;
        this.poll2vLimitByCountryRow.row = -1;
        this.poll2vLimitByCountryListRow = -1;
        this.allowAddingRow = -1;
        this.allowMarkingRow = -1;
        this.addAnswerRow = -1;
        this.answerStartRow = -1;
        this.settingsSectionRow = -1;
        this.descriptionRow = -1;
        this.paddingRow = 0;
        int i = 1 + 1;
        this.questionHeaderRow = 1;
        int i2 = i + 1;
        this.rowCount = i2;
        this.questionRow = i;
        boolean z = this.todo;
        if (!z) {
            this.rowCount = i + 2;
            this.descriptionRow = i2;
        }
        int i3 = this.rowCount;
        int i4 = i3 + 1;
        this.questionSectionRow = i3;
        int i5 = i3 + 2;
        this.rowCount = i5;
        this.answerHeaderRow = i4;
        int i6 = this.answersCount;
        if (i6 != 0) {
            this.answerStartRow = i5;
            this.rowCount = i5 + i6;
        }
        if (i6 != this.answers.length) {
            int i7 = this.rowCount;
            this.rowCount = i7 + 1;
            this.addAnswerRow = i7;
        }
        int i8 = this.rowCount;
        this.answerSectionRow = i8;
        int i9 = i8 + 2;
        this.rowCount = i9;
        this.settingsHeaderRow = i8 + 1;
        if (z) {
            int i10 = i8 + 3;
            this.rowCount = i10;
            this.allowMarkingRow = i9;
            if (this.allowMarking) {
                this.rowCount = i8 + 4;
                this.allowAddingRow = i10;
            }
        } else {
            TLRPC.Chat currentChat = ((ChatActivity) this.parentAlert.baseFragment).getCurrentChat();
            boolean z2 = ChatObject.isChannel(currentChat) && !currentChat.megagroup;
            if (!z2) {
                int i11 = this.rowCount;
                this.rowCount = i11 + 1;
                this.poll2vAnonymousRow = i11;
            } else {
                this.anonymousPoll = true;
            }
            int i12 = this.rowCount;
            int i13 = i12 + 1;
            this.rowCount = i13;
            this.poll2vMultipleRow = i12;
            if (!z2) {
                this.rowCount = i12 + 2;
                this.poll2vAllowAddingRow = i13;
            } else {
                this.allowAddingOptions = false;
            }
            int i14 = this.rowCount;
            this.poll2vAllowRevotingRow = i14;
            this.poll2vShuffleRow = i14 + 1;
            int i15 = i14 + 3;
            this.rowCount = i15;
            this.poll2vQuizRow = i14 + 2;
            if (z2) {
                ToggleRow toggleRow = this.poll2vSubscribersOnlyRow;
                int i16 = i14 + 4;
                this.rowCount = i16;
                toggleRow.row = i15;
                ToggleRow toggleRow2 = this.poll2vLimitByCountryRow;
                int i17 = i14 + 5;
                this.rowCount = i17;
                toggleRow2.row = i16;
                if (toggleRow2.checked) {
                    this.rowCount = i14 + 6;
                    this.poll2vLimitByCountryListRow = i17;
                }
            }
            int i18 = this.rowCount;
            int i19 = i18 + 1;
            this.rowCount = i19;
            this.poll2vLimitDurationRow = i18;
            if (this.pollLimitDuration != 0 || this.pollLimitDeadline != 0) {
                this.poll2vLimitDurationTimeRow = i19;
                this.poll2vLimitDurationHideResultsRow = i18 + 2;
                this.rowCount = i18 + 4;
                this.poll2vLimitDurationHideResultsRowInfo = i18 + 3;
            }
            int i20 = this.rowCount;
            int i21 = i20 + 1;
            this.rowCount = i21;
            this.settingsSectionRow = i20;
            if (this.quizPoll) {
                this.solutionRowHeader = i21;
                this.solutionRow = i20 + 2;
                this.rowCount = i20 + 4;
                this.solutionInfoRow = i20 + 3;
            }
        }
        int i22 = this.rowCount;
        this.rowCount = i22 + 1;
        this.emptyRow = i22;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onShow(ChatAttachAlert.AttachAlertLayout attachAlertLayout) {
        try {
            this.parentAlert.actionBar.getTitleTextView().setBuildFullLayout(true);
        } catch (Exception unused) {
        }
        if (this.todo) {
            this.parentAlert.actionBar.setTitle(LocaleController.getString(C2797R.string.TodoTitle));
        } else {
            boolean z = this.quizOnly;
            ChatAttachAlert chatAttachAlert = this.parentAlert;
            if (z) {
                chatAttachAlert.actionBar.setTitle(LocaleController.getString(C2797R.string.NewQuiz));
            } else {
                chatAttachAlert.actionBar.setTitle(LocaleController.getString(C2797R.string.NewPoll));
            }
        }
        this.parentAlert.updateDoneItemEnabled();
        this.layoutManager.scrollToPositionWithOffset(0, 0);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onDestroy() {
        super.onDestroy();
        this.destroyed = true;
        NotificationCenter.getInstance(this.parentAlert.currentAccount).removeObserver(this, NotificationCenter.didReceivedWebpagesInUpdates);
        if (this.isPremium) {
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
            EmojiView emojiView = this.emojiView;
            if (emojiView != null) {
                this.parentAlert.sizeNotifierFrameLayout.removeView(emojiView);
            }
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onHidden() {
        this.parentAlert.updateDoneItemEnabled();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public boolean onBackPressed() {
        if (this.emojiViewVisible) {
            hideEmojiPopup(true);
            return true;
        }
        if (checkDiscard()) {
            return super.onBackPressed();
        }
        return true;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public boolean onDismissWithTouchOutside() {
        if (checkDiscard()) {
            return super.onDismissWithTouchOutside();
        }
        return false;
    }

    private boolean checkDiscard() {
        boolean zIsEmpty = TextUtils.isEmpty(getFixedString(this.questionString)) && TextUtils.isEmpty(getFixedString(this.descriptionString)) && TextUtils.isEmpty(getFixedString(this.solutionString)) && this.attachedMedia.medias.size() == 0;
        if (zIsEmpty) {
            for (int i = 0; i < this.answersCount && (zIsEmpty = TextUtils.isEmpty(getFixedString(this.answers[i]))); i++) {
            }
        }
        if (!zIsEmpty) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.parentAlert.baseFragment.getParentActivity());
            builder.setTitle(LocaleController.getString(this.todo ? C2797R.string.CancelTodoAlertTitle : C2797R.string.CancelPollAlertTitle));
            builder.setMessage(LocaleController.getString(this.todo ? C2797R.string.CancelTodoAlertText : C2797R.string.CancelPollAlertText));
            builder.setPositiveButton(LocaleController.getString(C2797R.string.PassportDiscard), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda0
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i2) {
                    this.f$0.lambda$checkDiscard$9(alertDialog, i2);
                }
            });
            builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
            builder.show();
        }
        return zIsEmpty;
    }

    public /* synthetic */ void lambda$checkDiscard$9(AlertDialog alertDialog, int i) {
        this.parentAlert.lambda$new$0();
    }

    public void setDelegate(PollCreateActivityDelegate pollCreateActivityDelegate) {
        this.delegate = pollCreateActivityDelegate;
    }

    /* JADX WARN: Removed duplicated region for block: B:91:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x00a1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setTextLeft(android.view.View r5, int r6) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof org.telegram.p035ui.Cells.PollEditTextCell
            if (r0 != 0) goto L6
            goto La6
        L6:
            org.telegram.ui.Cells.PollEditTextCell r5 = (org.telegram.p035ui.Cells.PollEditTextCell) r5
            int r0 = r4.descriptionRow
            r1 = 0
            if (r6 != r0) goto L1a
            int r6 = r4.MAX_CAPTION_LENGTH
            java.lang.CharSequence r0 = r4.descriptionString
            if (r0 == 0) goto L17
            int r1 = r0.length()
        L17:
            int r0 = r6 - r1
            goto L6b
        L1a:
            int r0 = r4.questionRow
            if (r6 != r0) goto L34
            boolean r6 = r4.todo
            if (r6 == 0) goto L29
            org.telegram.messenger.MessagesController r6 = r4.getMessagesController()
            int r6 = r6.todoTitleLengthMax
            goto L2b
        L29:
            r6 = 255(0xff, float:3.57E-43)
        L2b:
            java.lang.CharSequence r0 = r4.questionString
            if (r0 == 0) goto L17
            int r1 = r0.length()
            goto L17
        L34:
            int r0 = r4.solutionRow
            if (r6 != r0) goto L45
            java.lang.CharSequence r6 = r4.solutionString
            if (r6 == 0) goto L40
            int r1 = r6.length()
        L40:
            r6 = 200(0xc8, float:2.8E-43)
            int r0 = 200 - r1
            goto L6b
        L45:
            int r0 = r4.answerStartRow
            if (r6 < r0) goto La6
            int r2 = r4.answersCount
            int r2 = r2 + r0
            if (r6 >= r2) goto La6
            int r6 = r6 - r0
            boolean r0 = r4.todo
            if (r0 == 0) goto L5a
            org.telegram.messenger.MessagesController r0 = r4.getMessagesController()
            int r0 = r0.todoItemLengthMax
            goto L5c
        L5a:
            r0 = 100
        L5c:
            java.lang.CharSequence[] r2 = r4.answers
            r6 = r2[r6]
            if (r6 == 0) goto L66
            int r1 = r6.length()
        L66:
            int r6 = r0 - r1
            r3 = r0
            r0 = r6
            r6 = r3
        L6b:
            float r1 = (float) r0
            float r6 = (float) r6
            r2 = 1060320051(0x3f333333, float:0.7)
            float r2 = r2 * r6
            float r6 = r6 - r2
            int r6 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r6 > 0) goto La1
            java.lang.Integer r6 = java.lang.Integer.valueOf(r0)
            java.lang.Object[] r6 = new java.lang.Object[]{r6}
            java.lang.String r1 = "%d"
            java.lang.String r6 = java.lang.String.format(r1, r6)
            r5.setText2(r6)
            org.telegram.ui.ActionBar.SimpleTextView r5 = r5.getTextView2()
            if (r0 >= 0) goto L90
            int r6 = org.telegram.p035ui.ActionBar.Theme.key_text_RedRegular
            goto L92
        L90:
            int r6 = org.telegram.p035ui.ActionBar.Theme.key_windowBackgroundWhiteGrayText3
        L92:
            int r4 = r4.getThemedColor(r6)
            r5.setTextColor(r4)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r6)
            r5.setTag(r4)
            return
        La1:
            java.lang.String r4 = ""
            r5.setText2(r4)
        La6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.ChatAttachAlertPollLayout.setTextLeft(android.view.View, int):void");
    }

    public void addNewField() {
        resetSuggestEmojiPanel();
        this.listView.setItemAnimator(this.itemAnimator);
        boolean[] zArr = this.answersChecks;
        int i = this.answersCount;
        zArr[i] = false;
        int i2 = i + 1;
        this.answersCount = i2;
        if (i2 == this.answers.length) {
            this.listAdapter.notifyItemRemoved(this.addAnswerRow);
        }
        this.listAdapter.notifyItemInserted(this.addAnswerRow);
        updateRows();
        this.requestFieldFocusAtPosition = (this.answerStartRow + this.answersCount) - 1;
        this.listAdapter.notifyItemChanged(this.answerSectionRow);
        this.listAdapter.notifyItemChanged(this.emptyRow);
    }

    private void updateSuggestEmojiPanelDelegate(RecyclerView.ViewHolder viewHolder) {
        SuggestEmojiView suggestEmojiView = this.suggestEmojiPanel;
        if (suggestEmojiView != null) {
            suggestEmojiView.forceClose();
            SuggestEmojiView suggestEmojiView2 = this.suggestEmojiPanel;
            if (suggestEmojiView2 == null || viewHolder == null || !(viewHolder.itemView instanceof PollEditTextCell)) {
                return;
            }
            SuggestEmojiView.AnchorViewDelegate delegate = suggestEmojiView2.getDelegate();
            View view = viewHolder.itemView;
            if (delegate != view) {
                this.suggestEmojiPanel.setDelegate((PollEditTextCell) view);
            }
        }
    }

    private void resetSuggestEmojiPanel() {
        SuggestEmojiView suggestEmojiView = this.suggestEmojiPanel;
        if (suggestEmojiView != null) {
            suggestEmojiView.setDelegate(null);
            this.suggestEmojiPanel.forceClose();
        }
    }

    @Override // org.telegram.ui.Components.SizeNotifierFrameLayout.SizeNotifierFrameLayoutDelegate
    public void onSizeChanged(int i, boolean z) {
        boolean z2;
        if (this.isPremium) {
            if (i > AndroidUtilities.m1036dp(50.0f) && this.keyboardVisible && !AndroidUtilities.isInMultiwindow && !AndroidUtilities.isTablet()) {
                if (z) {
                    this.keyboardHeightLand = i;
                    MessagesController.getGlobalEmojiSettings().edit().putInt("kbd_height_land3", this.keyboardHeightLand).commit();
                } else {
                    this.keyboardHeight = i;
                    MessagesController.getGlobalEmojiSettings().edit().putInt("kbd_height", this.keyboardHeight).commit();
                }
            }
            if (this.emojiViewVisible) {
                int iM1036dp = z ? this.keyboardHeightLand : this.keyboardHeight;
                if (this.isEmojiSearchOpened) {
                    iM1036dp += AndroidUtilities.m1036dp(120.0f);
                }
                int i2 = iM1036dp + AndroidUtilities.navigationBarHeight;
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.emojiView.getLayoutParams();
                int i3 = layoutParams.width;
                int i4 = AndroidUtilities.displaySize.x;
                if (i3 != i4 || layoutParams.height != i2 || this.wasEmojiSearchOpened != this.isEmojiSearchOpened) {
                    layoutParams.width = i4;
                    layoutParams.height = i2;
                    this.emojiView.setLayoutParams(layoutParams);
                    this.emojiPadding = layoutParams.height;
                    this.keyboardNotifier.fire();
                    this.parentAlert.sizeNotifierFrameLayout.requestLayout();
                    boolean z3 = this.wasEmojiSearchOpened;
                    if (z3 != this.isEmojiSearchOpened) {
                        animateEmojiViewTranslationY(z3 ? -AndroidUtilities.m1036dp(120.0f) : AndroidUtilities.m1036dp(120.0f), 0.0f);
                    }
                    this.wasEmojiSearchOpened = this.isEmojiSearchOpened;
                }
            }
            if (this.lastSizeChangeValue1 == i && this.lastSizeChangeValue2 == z) {
                return;
            }
            this.lastSizeChangeValue1 = i;
            this.lastSizeChangeValue2 = z;
            boolean z4 = this.keyboardVisible;
            PollEditTextCell pollEditTextCell = this.currentCell;
            if (pollEditTextCell != null) {
                this.keyboardVisible = pollEditTextCell.getEditField().isFocused() && this.keyboardNotifier.keyboardVisible() && i > 0;
            } else {
                this.keyboardVisible = false;
            }
            if (this.keyboardVisible && this.emojiViewVisible) {
                showEmojiPopup(0);
            }
            if (this.emojiPadding != 0 && !(z2 = this.keyboardVisible) && z2 != z4 && !this.emojiViewVisible) {
                this.emojiPadding = 0;
                this.keyboardNotifier.fire();
                this.parentAlert.sizeNotifierFrameLayout.requestLayout();
            }
            if (this.keyboardVisible && this.waitingForKeyboardOpen) {
                this.waitingForKeyboardOpen = false;
                AndroidUtilities.cancelRunOnUIThread(this.openKeyboardRunnable);
            }
        }
    }

    public boolean isWaitingForKeyboardOpen() {
        return this.waitingForKeyboardOpen;
    }

    public void onEmojiClicked(PollEditTextCell pollEditTextCell) {
        this.currentCell = pollEditTextCell;
        if (this.emojiViewVisible) {
            collapseSearchEmojiView();
            openKeyboardInternal();
        } else {
            showEmojiPopup(1);
        }
    }

    private void collapseSearchEmojiView() {
        if (this.isEmojiSearchOpened) {
            this.emojiView.closeSearch(false);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.emojiView.getLayoutParams();
            layoutParams.height -= AndroidUtilities.m1036dp(120.0f);
            this.emojiView.setLayoutParams(layoutParams);
            this.emojiPadding = layoutParams.height;
            this.wasEmojiSearchOpened = this.isEmojiSearchOpened;
            this.isEmojiSearchOpened = false;
            animateEmojiViewTranslationY(-AndroidUtilities.m1036dp(120.0f), 0.0f);
        }
    }

    private void openKeyboardInternal() {
        if (this.currentCell != null) {
            this.keyboardNotifier.awaitKeyboard();
            EditTextBoldCursor editField = this.currentCell.getEditField();
            editField.requestFocus();
            AndroidUtilities.showKeyboard(editField);
        }
        showEmojiPopup(AndroidUtilities.usingHardwareInput ? 0 : 2);
        if (AndroidUtilities.usingHardwareInput || this.keyboardVisible || AndroidUtilities.isInMultiwindow || AndroidUtilities.isTablet()) {
            return;
        }
        this.waitingForKeyboardOpen = true;
        AndroidUtilities.cancelRunOnUIThread(this.openKeyboardRunnable);
        AndroidUtilities.runOnUIThread(this.openKeyboardRunnable, 100L);
    }

    private void showEmojiPopup(int i) {
        ChatActivityEnterViewAnimatedIconView emojiButton;
        PollEditTextCell pollEditTextCell;
        if (this.isPremium) {
            if (i == 1) {
                EmojiView emojiView = this.emojiView;
                boolean z = emojiView != null && emojiView.getVisibility() == 0;
                createEmojiView();
                this.emojiView.setVisibility(0);
                this.emojiViewWasVisible = this.emojiViewVisible;
                this.emojiViewVisible = true;
                EmojiView emojiView2 = this.emojiView;
                if (this.keyboardHeight <= 0) {
                    if (AndroidUtilities.isTablet()) {
                        this.keyboardHeight = AndroidUtilities.m1036dp(150.0f);
                    } else {
                        this.keyboardHeight = MessagesController.getGlobalEmojiSettings().getInt("kbd_height", AndroidUtilities.m1036dp(200.0f));
                    }
                }
                if (this.keyboardHeightLand <= 0) {
                    if (AndroidUtilities.isTablet()) {
                        this.keyboardHeightLand = AndroidUtilities.m1036dp(150.0f);
                    } else {
                        this.keyboardHeightLand = MessagesController.getGlobalEmojiSettings().getInt("kbd_height_land3", AndroidUtilities.m1036dp(200.0f));
                    }
                }
                Point point = AndroidUtilities.displaySize;
                int i2 = point.x > point.y ? this.keyboardHeightLand : this.keyboardHeight;
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) emojiView2.getLayoutParams();
                layoutParams.height = AndroidUtilities.navigationBarHeight + i2;
                emojiView2.setLayoutParams(layoutParams);
                if (!AndroidUtilities.isInMultiwindow && !AndroidUtilities.isTablet() && (pollEditTextCell = this.currentCell) != null) {
                    AndroidUtilities.hideKeyboard(pollEditTextCell.getEditField());
                }
                this.emojiPadding = i2;
                this.keyboardNotifier.fire();
                this.parentAlert.sizeNotifierFrameLayout.requestLayout();
                PollEditTextCell pollEditTextCell2 = this.currentCell;
                emojiButton = pollEditTextCell2 != null ? pollEditTextCell2.getEmojiButton() : null;
                if (emojiButton != null) {
                    emojiButton.setState(ChatActivityEnterViewAnimatedIconView.State.KEYBOARD, true);
                }
                if (z || this.keyboardVisible) {
                    return;
                }
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.emojiPadding, 0.0f);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda5
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$showEmojiPopup$10(valueAnimator);
                    }
                });
                valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.8
                    public C41508() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        ChatAttachAlertPollLayout.this.emojiView.setTranslationY(0.0f);
                    }
                });
                valueAnimatorOfFloat.setDuration(250L);
                valueAnimatorOfFloat.setInterpolator(AdjustPanLayoutHelper.keyboardInterpolator);
                valueAnimatorOfFloat.start();
                return;
            }
            PollEditTextCell pollEditTextCell3 = this.currentCell;
            emojiButton = pollEditTextCell3 != null ? pollEditTextCell3.getEmojiButton() : null;
            if (emojiButton != null) {
                emojiButton.setState(ChatActivityEnterViewAnimatedIconView.State.SMILE, true);
            }
            EmojiView emojiView3 = this.emojiView;
            if (emojiView3 != null) {
                this.emojiViewWasVisible = this.emojiViewVisible;
                this.emojiViewVisible = false;
                this.isEmojiSearchOpened = false;
                if (AndroidUtilities.usingHardwareInput || AndroidUtilities.isInMultiwindow) {
                    emojiView3.setVisibility(8);
                }
            }
            if (i == 0) {
                this.emojiPadding = 0;
            }
            this.keyboardNotifier.fire();
            this.parentAlert.sizeNotifierFrameLayout.requestLayout();
        }
    }

    public /* synthetic */ void lambda$showEmojiPopup$10(ValueAnimator valueAnimator) {
        this.emojiView.setTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$8 */
    public class C41508 extends AnimatorListenerAdapter {
        public C41508() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            ChatAttachAlertPollLayout.this.emojiView.setTranslationY(0.0f);
        }
    }

    public void onCellFocusChanges(PollEditTextCell pollEditTextCell, boolean z) {
        if (this.isPremium && z) {
            if (this.currentCell == pollEditTextCell && this.emojiViewVisible && this.isEmojiSearchOpened) {
                collapseSearchEmojiView();
                this.emojiViewVisible = false;
            }
            PollEditTextCell pollEditTextCell2 = this.currentCell;
            this.currentCell = pollEditTextCell;
            pollEditTextCell.setEmojiButtonVisibility(true);
            ChatActivityEnterViewAnimatedIconView emojiButton = pollEditTextCell.getEmojiButton();
            ChatActivityEnterViewAnimatedIconView.State state = ChatActivityEnterViewAnimatedIconView.State.SMILE;
            emojiButton.setState(state, false);
            updateSuggestEmojiPanelDelegate(this.listView.findContainingViewHolder(pollEditTextCell));
            if (pollEditTextCell2 == null || pollEditTextCell2 == pollEditTextCell) {
                return;
            }
            if (this.emojiViewVisible) {
                collapseSearchEmojiView();
                hideEmojiPopup(false);
                openKeyboardInternal();
            }
            pollEditTextCell2.setEmojiButtonVisibility(false);
            pollEditTextCell2.getEmojiButton().setState(state, false);
        }
    }

    public void hideEmojiPopup(boolean z) {
        if (this.isPremium) {
            if (this.emojiViewVisible) {
                this.emojiView.scrollEmojiToTop();
                this.emojiView.closeSearch(false);
                if (z) {
                    this.emojiView.hideSearchKeyboard();
                }
                this.isEmojiSearchOpened = false;
                showEmojiPopup(0);
            }
            if (z) {
                EmojiView emojiView = this.emojiView;
                if (emojiView != null && emojiView.getVisibility() == 0) {
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, this.emojiView.getMeasuredHeight());
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda3
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            this.f$0.lambda$hideEmojiPopup$11(valueAnimator);
                        }
                    });
                    this.isAnimatePopupClosing = true;
                    valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.9
                        public C41519() {
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            ChatAttachAlertPollLayout.this.isAnimatePopupClosing = false;
                            ChatAttachAlertPollLayout.this.emojiView.setTranslationY(0.0f);
                            ChatAttachAlertPollLayout.this.hideEmojiView();
                        }
                    });
                    valueAnimatorOfFloat.setDuration(250L);
                    valueAnimatorOfFloat.setInterpolator(AdjustPanLayoutHelper.keyboardInterpolator);
                    valueAnimatorOfFloat.start();
                    return;
                }
                hideEmojiView();
            }
        }
    }

    public /* synthetic */ void lambda$hideEmojiPopup$11(ValueAnimator valueAnimator) {
        this.emojiView.setTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$9 */
    public class C41519 extends AnimatorListenerAdapter {
        public C41519() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            ChatAttachAlertPollLayout.this.isAnimatePopupClosing = false;
            ChatAttachAlertPollLayout.this.emojiView.setTranslationY(0.0f);
            ChatAttachAlertPollLayout.this.hideEmojiView();
        }
    }

    public void hideEmojiView() {
        EmojiView emojiView;
        ChatActivityEnterViewAnimatedIconView emojiButton;
        if (!this.emojiViewVisible && (emojiView = this.emojiView) != null && emojiView.getVisibility() != 8) {
            PollEditTextCell pollEditTextCell = this.currentCell;
            if (pollEditTextCell != null && (emojiButton = pollEditTextCell.getEmojiButton()) != null) {
                emojiButton.setState(ChatActivityEnterViewAnimatedIconView.State.SMILE, false);
            }
            this.emojiView.setVisibility(8);
        }
        int i = this.emojiPadding;
        this.emojiPadding = 0;
        if (i != 0) {
            this.keyboardNotifier.fire();
        }
    }

    public boolean isAnimatePopupClosing() {
        return this.isAnimatePopupClosing;
    }

    public boolean isPopupShowing() {
        return this.emojiViewVisible;
    }

    public boolean isPopupVisible() {
        EmojiView emojiView = this.emojiView;
        return emojiView != null && emojiView.getVisibility() == 0;
    }

    public int getEmojiPadding() {
        return this.emojiPadding;
    }

    private void createEmojiView() {
        EmojiView emojiView = this.emojiView;
        if (emojiView != null && emojiView.currentAccount != UserConfig.selectedAccount) {
            this.parentAlert.sizeNotifierFrameLayout.removeView(emojiView);
            this.emojiView = null;
        }
        if (this.emojiView != null) {
            return;
        }
        EmojiView emojiView2 = new EmojiView(null, true, false, false, getContext(), true, null, null, true, this.resourcesProvider, false);
        this.emojiView = emojiView2;
        emojiView2.emojiCacheType = 3;
        emojiView2.shouldLightenBackground = false;
        emojiView2.fixBottomTabContainerTranslation = false;
        emojiView2.setShouldDrawBackground(false);
        this.emojiView.allowEmojisForNonPremium(false);
        this.emojiView.setVisibility(8);
        if (AndroidUtilities.isTablet()) {
            this.emojiView.setForseMultiwindowLayout(true);
        }
        this.emojiView.setDelegate(new C413610());
        this.parentAlert.sizeNotifierFrameLayout.addView(this.emojiView);
        this.emojiView.setBottomInset(AndroidUtilities.navigationBarHeight);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$10 */
    public class C413610 implements EmojiView.EmojiViewDelegate {
        public C413610() {
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public boolean onBackspace() {
            EditTextBoldCursor editField;
            if (ChatAttachAlertPollLayout.this.currentCell == null || (editField = ChatAttachAlertPollLayout.this.currentCell.getEditField()) == null) {
                return false;
            }
            editField.dispatchKeyEvent(new KeyEvent(0, 67));
            return true;
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public void onEmojiSelected(String str) {
            EditTextBoldCursor editField;
            if (ChatAttachAlertPollLayout.this.currentCell == null || (editField = ChatAttachAlertPollLayout.this.currentCell.getEditField()) == null) {
                return;
            }
            int selectionEnd = editField.getSelectionEnd();
            if (selectionEnd < 0) {
                selectionEnd = 0;
            }
            try {
                CharSequence charSequenceReplaceEmoji = Emoji.replaceEmoji(str, editField.getPaint().getFontMetricsInt(), false);
                editField.setText(editField.getText().insert(selectionEnd, charSequenceReplaceEmoji));
                int length = selectionEnd + charSequenceReplaceEmoji.length();
                editField.setSelection(length, length);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public void onCustomEmojiSelected(long j, TLRPC.Document document, String str, boolean z) {
            EditTextBoldCursor editField;
            AnimatedEmojiSpan animatedEmojiSpan;
            if (ChatAttachAlertPollLayout.this.currentCell == null || (editField = ChatAttachAlertPollLayout.this.currentCell.getEditField()) == null) {
                return;
            }
            int selectionEnd = editField.getSelectionEnd();
            if (selectionEnd < 0) {
                selectionEnd = 0;
            }
            try {
                SpannableString spannableString = new SpannableString(str);
                if (document != null) {
                    animatedEmojiSpan = new AnimatedEmojiSpan(document, editField.getPaint().getFontMetricsInt());
                } else {
                    animatedEmojiSpan = new AnimatedEmojiSpan(j, editField.getPaint().getFontMetricsInt());
                }
                animatedEmojiSpan.cacheType = 3;
                spannableString.setSpan(animatedEmojiSpan, 0, spannableString.length(), 33);
                editField.setText(editField.getText().insert(selectionEnd, spannableString));
                int length = selectionEnd + spannableString.length();
                editField.setSelection(length, length);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public void onClearEmojiRecent() {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChatAttachAlertPollLayout.this.getContext(), ChatAttachAlertPollLayout.this.resourcesProvider);
            builder.setTitle(LocaleController.getString(C2797R.string.ClearRecentEmojiTitle));
            builder.setMessage(LocaleController.getString(C2797R.string.ClearRecentEmojiText));
            builder.setPositiveButton(LocaleController.getString(C2797R.string.ClearButton), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$10$$ExternalSyntheticLambda0
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    this.f$0.lambda$onClearEmojiRecent$0(alertDialog, i);
                }
            });
            builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
            builder.show();
        }

        public /* synthetic */ void lambda$onClearEmojiRecent$0(AlertDialog alertDialog, int i) {
            ChatAttachAlertPollLayout.this.emojiView.clearRecentEmoji();
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public void onSearchOpenClose(int i) {
            ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
            chatAttachAlertPollLayout.isEmojiSearchOpened = i != 0;
            chatAttachAlertPollLayout.parentAlert.sizeNotifierFrameLayout.requestLayout();
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public boolean isSearchOpened() {
            return ChatAttachAlertPollLayout.this.isEmojiSearchOpened;
        }
    }

    private void animateEmojiViewTranslationY(final float f, final float f2) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$animateEmojiViewTranslationY$12(f, f2, valueAnimator);
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.11
            final /* synthetic */ float val$toY;

            public C413711(final float f22) {
                f = f22;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ChatAttachAlertPollLayout.this.emojiView.setTranslationY(f);
            }
        });
        valueAnimatorOfFloat.setDuration(250L);
        valueAnimatorOfFloat.setInterpolator(AdjustPanLayoutHelper.keyboardInterpolator);
        valueAnimatorOfFloat.start();
    }

    public /* synthetic */ void lambda$animateEmojiViewTranslationY$12(float f, float f2, ValueAnimator valueAnimator) {
        this.emojiView.setTranslationY(AndroidUtilities.lerp(f, f2, ((Float) valueAnimator.getAnimatedValue()).floatValue()));
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$11 */
    public class C413711 extends AnimatorListenerAdapter {
        final /* synthetic */ float val$toY;

        public C413711(final float f22) {
            f = f22;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            ChatAttachAlertPollLayout.this.emojiView.setTranslationY(f);
        }
    }

    public void checkAllowedCountriesList(TextCell textCell, boolean z) {
        textCell.setTextAndValue(LocaleController.getString(C2797R.string.PollV2AllowedCountries), formatCountriesList(this.countriesList), z, true);
    }

    public void checkDurationInfoRow(TextCell textCell, boolean z) {
        if (this.pollLimitDeadline != 0) {
            textCell.setTextAndValue(LocaleController.getString(C2797R.string.PollV2PollEnds), LocaleController.formatShortDateTime(this.pollLimitDeadline), z, false);
        } else if (this.pollLimitDuration != 0) {
            textCell.setTextAndValue(LocaleController.getString(C2797R.string.PollV2PollDuration), LocaleController.formatPluralString("Hours", this.pollLimitDuration / 3600, new Object[0]), z, false);
        } else {
            textCell.setTextAndValue(LocaleController.getString(C2797R.string.PollV2PollEnds), null, z, false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:95:0x0105  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void deletePollAnswerView(final android.view.View r8, final org.telegram.p035ui.Cells.PollEditTextCell r9, boolean r10) {
        /*
            Method dump skipped, instruction units count: 317
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.ChatAttachAlertPollLayout.deletePollAnswerView(android.view.View, org.telegram.ui.Cells.PollEditTextCell, boolean):void");
    }

    public /* synthetic */ void lambda$deletePollAnswerView$13(View view, PollEditTextCell pollEditTextCell, AlertDialog alertDialog, int i) {
        view.setTag(null);
        deletePollAnswerView(view, pollEditTextCell, false);
    }

    public class ListAdapter extends RecyclerListView.SelectionAdapter {
        private final Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return ChatAttachAlertPollLayout.this.rowCount;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            int itemViewType = viewHolder.getItemViewType();
            if (itemViewType == 0) {
                HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                if (i == ChatAttachAlertPollLayout.this.questionHeaderRow) {
                    headerCell.getTextView().setGravity(19);
                    headerCell.setText(LocaleController.getString(ChatAttachAlertPollLayout.this.todo ? C2797R.string.TodoTitle : C2797R.string.PollQuestion2));
                    return;
                }
                if (i == ChatAttachAlertPollLayout.this.solutionRowHeader) {
                    headerCell.getTextView().setGravity(19);
                    headerCell.setText(LocaleController.getString(C2797R.string.AddAnExplanationHeader));
                    return;
                }
                headerCell.getTextView().setGravity((LocaleController.isRTL ? 5 : 3) | 16);
                int i2 = ChatAttachAlertPollLayout.this.answerHeaderRow;
                ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                if (i == i2) {
                    if (chatAttachAlertPollLayout.quizOnly) {
                        headerCell.setText(LocaleController.getString(C2797R.string.QuizAnswers));
                        return;
                    } else {
                        headerCell.setText(LocaleController.getString(ChatAttachAlertPollLayout.this.todo ? C2797R.string.TodoItemsTitle : C2797R.string.AnswerOptions2));
                        return;
                    }
                }
                if (i == chatAttachAlertPollLayout.settingsHeaderRow) {
                    headerCell.setText(LocaleController.getString(C2797R.string.Settings));
                    return;
                }
                return;
            }
            boolean z = true;
            if (itemViewType == 6) {
                TextCheckCell textCheckCell = (TextCheckCell) viewHolder.itemView;
                if (i == ChatAttachAlertPollLayout.this.allowAddingRow) {
                    textCheckCell.setTextAndCheck(LocaleController.getString(C2797R.string.TodoAllowAddingTasks), ChatAttachAlertPollLayout.this.allowAdding, ChatAttachAlertPollLayout.this.allowMarkingRow != -1);
                    textCheckCell.setEnabled(true, null);
                    return;
                } else if (i == ChatAttachAlertPollLayout.this.allowMarkingRow) {
                    textCheckCell.setTextAndCheck(LocaleController.getString(C2797R.string.TodoAllowMarkingDone), ChatAttachAlertPollLayout.this.allowMarking, false);
                    textCheckCell.setEnabled(true, null);
                    return;
                } else {
                    if (i == ChatAttachAlertPollLayout.this.poll2vLimitDurationHideResultsRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString(C2797R.string.PollV2HideResults), ChatAttachAlertPollLayout.this.hideResults, false);
                        textCheckCell.setEnabled(true, null);
                        return;
                    }
                    return;
                }
            }
            if (itemViewType == 2) {
                TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                textInfoPrivacyCell.setFixedSize(0);
                new CombinedDrawable(new ColorDrawable(ChatAttachAlertPollLayout.this.getThemedColor(Theme.key_windowBackgroundGray)), Theme.getThemedDrawableByKey(this.mContext, C2797R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow)).setFullsize(true);
                if (i == ChatAttachAlertPollLayout.this.solutionInfoRow) {
                    textInfoPrivacyCell.setText(LocaleController.getString(C2797R.string.AddAnExplanationInfo));
                    return;
                }
                if (i == ChatAttachAlertPollLayout.this.settingsSectionRow) {
                    textInfoPrivacyCell.setFixedSize(12);
                    textInfoPrivacyCell.setText(null);
                    return;
                }
                int i3 = ChatAttachAlertPollLayout.this.maxAnswersCount - ChatAttachAlertPollLayout.this.answersCount;
                ChatAttachAlertPollLayout chatAttachAlertPollLayout2 = ChatAttachAlertPollLayout.this;
                if (i3 <= 0) {
                    textInfoPrivacyCell.setText(LocaleController.getString(chatAttachAlertPollLayout2.todo ? C2797R.string.TodoAddTaskInfoMax : C2797R.string.AddAnOptionInfoMax));
                    return;
                }
                boolean z2 = chatAttachAlertPollLayout2.todo;
                ChatAttachAlertPollLayout chatAttachAlertPollLayout3 = ChatAttachAlertPollLayout.this;
                if (z2) {
                    textInfoPrivacyCell.setText(LocaleController.formatPluralStringComma("TodoNewTaskInfo", chatAttachAlertPollLayout3.maxAnswersCount - ChatAttachAlertPollLayout.this.answersCount));
                    return;
                } else if (i == chatAttachAlertPollLayout3.poll2vLimitDurationHideResultsRowInfo) {
                    textInfoPrivacyCell.setText(LocaleController.getString(C2797R.string.PollV2HideResultsInfo));
                    return;
                } else {
                    textInfoPrivacyCell.setText(LocaleController.formatString(C2797R.string.AddAnOptionInfo, LocaleController.formatPluralString("Option", ChatAttachAlertPollLayout.this.maxAnswersCount - ChatAttachAlertPollLayout.this.answersCount, new Object[0])));
                    return;
                }
            }
            if (itemViewType == 3) {
                TextCell textCell = (TextCell) viewHolder.itemView;
                int i4 = ChatAttachAlertPollLayout.this.poll2vLimitByCountryListRow;
                ChatAttachAlertPollLayout chatAttachAlertPollLayout4 = ChatAttachAlertPollLayout.this;
                if (i == i4) {
                    chatAttachAlertPollLayout4.checkAllowedCountriesList(textCell, false);
                    return;
                }
                if (i == chatAttachAlertPollLayout4.poll2vLimitDurationTimeRow) {
                    ChatAttachAlertPollLayout.this.checkDurationInfoRow(textCell, false);
                    return;
                }
                textCell.setColors(-1, Theme.key_telegram_color_text);
                Drawable drawable = this.mContext.getResources().getDrawable(C2797R.drawable.poll_add_circle);
                Drawable drawable2 = this.mContext.getResources().getDrawable(C2797R.drawable.poll_add_plus);
                int themedColor = ChatAttachAlertPollLayout.this.getThemedColor(Theme.key_switchTrackChecked);
                PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
                drawable.setColorFilter(new PorterDuffColorFilter(themedColor, mode));
                drawable2.setColorFilter(new PorterDuffColorFilter(ChatAttachAlertPollLayout.this.getThemedColor(Theme.key_checkboxCheck), mode));
                textCell.setTextAndIcon((CharSequence) LocaleController.getString(ChatAttachAlertPollLayout.this.todo ? C2797R.string.TodoNewTask : C2797R.string.AddAnOption), (Drawable) new CombinedDrawable(drawable, drawable2), false);
                textCell.imageLeft = 20;
                textCell.offsetFromImage = 58;
                return;
            }
            if (itemViewType == 9) {
                viewHolder.itemView.requestLayout();
                return;
            }
            if (itemViewType != 10) {
                return;
            }
            PollCreateCheckCell pollCreateCheckCell = (PollCreateCheckCell) viewHolder.itemView;
            pollCreateCheckCell.setDivider(false);
            if (i == ChatAttachAlertPollLayout.this.poll2vAnonymousRow) {
                pollCreateCheckCell.setTextAndValueAndIconAndCheck(LocaleController.getString(C2797R.string.PollV2ShowWhoVoted), LocaleController.getString(C2797R.string.PollV2ShowWhoVotedInfo), IconBackgroundColors.BLUE, C2797R.drawable.filled_poll_view_24, !ChatAttachAlertPollLayout.this.anonymousPoll);
            } else if (i == ChatAttachAlertPollLayout.this.poll2vMultipleRow) {
                pollCreateCheckCell.setTextAndValueAndIconAndCheck(LocaleController.getString(C2797R.string.PollV2AllowMultipleAnswers), LocaleController.getString(C2797R.string.PollV2AllowMultipleAnswersInfo), IconBackgroundColors.ORANGE, C2797R.drawable.filled_poll_multiple_24, ChatAttachAlertPollLayout.this.multipleChoise);
            } else if (i == ChatAttachAlertPollLayout.this.poll2vAllowRevotingRow) {
                pollCreateCheckCell.setTextAndValueAndIconAndCheck(LocaleController.getString(C2797R.string.PollV2AllowRevoting), LocaleController.getString(C2797R.string.PollV2AllowRevotingInfo), IconBackgroundColors.PURPLE, C2797R.drawable.filled_poll_revote_24, ChatAttachAlertPollLayout.this.allowRevoting);
            } else if (i == ChatAttachAlertPollLayout.this.poll2vAllowAddingRow) {
                pollCreateCheckCell.setTextAndValueAndIconAndCheck(LocaleController.getString(C2797R.string.PollV2AllowAddingOptions), LocaleController.getString(C2797R.string.PollV2AllowAddingOptionsInfo), IconBackgroundColors.CYAN, C2797R.drawable.filled_poll_add_24, ChatAttachAlertPollLayout.this.allowAddingOptions);
            } else if (i == ChatAttachAlertPollLayout.this.poll2vShuffleRow) {
                pollCreateCheckCell.setTextAndValueAndIconAndCheck(LocaleController.getString(C2797R.string.PollV2ShuffleOptions), LocaleController.getString(C2797R.string.PollV2ShuffleOptionsInfo), IconBackgroundColors.ORANGE_DEEP, C2797R.drawable.filled_poll_shuffle_24, ChatAttachAlertPollLayout.this.shuffleOptions);
            } else if (i == ChatAttachAlertPollLayout.this.poll2vQuizRow) {
                pollCreateCheckCell.setTextAndValueAndIconAndCheck(LocaleController.getString(C2797R.string.PollV2SetCorrectAnswer), LocaleController.getString(C2797R.string.PollV2SetCorrectAnswerInfo), IconBackgroundColors.GREEN, C2797R.drawable.filled_poll_correct_24, ChatAttachAlertPollLayout.this.quizPoll);
            } else if (i == ChatAttachAlertPollLayout.this.poll2vLimitByCountryRow.row) {
                pollCreateCheckCell.setTextAndValueAndIconAndCheck(LocaleController.getString(C2797R.string.PollV2LimitByCountry), LocaleController.getString(C2797R.string.PollV2LimitByCountryInfo), IconBackgroundColors.BLUE_LIGHT, C2797R.drawable.filled_location, ChatAttachAlertPollLayout.this.poll2vLimitByCountryRow.checked);
            } else if (i == ChatAttachAlertPollLayout.this.poll2vSubscribersOnlyRow.row) {
                pollCreateCheckCell.setTextAndValueAndIconAndCheck(LocaleController.getString(C2797R.string.PollV2RestrictToSubscribers), LocaleController.getString(C2797R.string.PollV2RestrictToSubscribersInfo), IconBackgroundColors.BLUE_DEEP, C2797R.drawable.msg_folders_groups, ChatAttachAlertPollLayout.this.poll2vSubscribersOnlyRow.checked);
            } else if (i == ChatAttachAlertPollLayout.this.poll2vLimitDurationRow) {
                pollCreateCheckCell.setTextAndValueAndIconAndCheck(LocaleController.getString(C2797R.string.PollV2LimitDuration), LocaleController.getString(C2797R.string.PollV2LimitDurationInfo), IconBackgroundColors.RED, C2797R.drawable.filled_poll_deadline_24, (ChatAttachAlertPollLayout.this.pollLimitDuration == 0 && ChatAttachAlertPollLayout.this.pollLimitDeadline == 0) ? false : true);
                pollCreateCheckCell.setDivider((ChatAttachAlertPollLayout.this.pollLimitDuration == 0 && ChatAttachAlertPollLayout.this.pollLimitDeadline == 0) ? false : true);
            }
            if (i == ChatAttachAlertPollLayout.this.poll2vQuizRow) {
                pollCreateCheckCell.getCheckBox().setIconVisible(ChatAttachAlertPollLayout.this.quizOnly, false);
                return;
            }
            if (i == ChatAttachAlertPollLayout.this.poll2vAllowAddingRow) {
                Switch checkBox = pollCreateCheckCell.getCheckBox();
                if (!ChatAttachAlertPollLayout.this.quizPoll && !ChatAttachAlertPollLayout.this.anonymousPoll) {
                    z = false;
                }
                checkBox.setIconVisible(z, false);
                return;
            }
            pollCreateCheckCell.getCheckBox().setIconVisible(false, false);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            CharSequence charSequence = _UrlKt.FRAGMENT_ENCODE_SET;
            if (itemViewType == 4) {
                PollEditTextCell pollEditTextCell = (PollEditTextCell) viewHolder.itemView;
                pollEditTextCell.setTag(1);
                if (ChatAttachAlertPollLayout.this.questionString != null) {
                    charSequence = ChatAttachAlertPollLayout.this.questionString;
                }
                pollEditTextCell.setTextAndHint(charSequence, LocaleController.getString(ChatAttachAlertPollLayout.this.todo ? C2797R.string.TodoTitlePlaceholder : C2797R.string.QuestionHint), true);
                pollEditTextCell.setTag(null);
                ChatAttachAlertPollLayout.this.setTextLeft(viewHolder.itemView, viewHolder.getAdapterPosition());
                return;
            }
            if (itemViewType == 11) {
                PollEditTextCell pollEditTextCell2 = (PollEditTextCell) viewHolder.itemView;
                pollEditTextCell2.setTag(1);
                if (ChatAttachAlertPollLayout.this.descriptionString != null) {
                    charSequence = ChatAttachAlertPollLayout.this.descriptionString;
                }
                pollEditTextCell2.setTextAndHint(charSequence, LocaleController.getString(C2797R.string.QuestionDescriptionHint), false);
                pollEditTextCell2.setTag(null);
                pollEditTextCell2.attachView.setAttachedMedia(ChatAttachAlertPollLayout.this.attachedMedia.get(-2), false);
                ChatAttachAlertPollLayout.this.setTextLeft(viewHolder.itemView, viewHolder.getAdapterPosition());
                return;
            }
            if (itemViewType != 5) {
                if (itemViewType == 7) {
                    PollEditTextCell pollEditTextCell3 = (PollEditTextCell) viewHolder.itemView;
                    pollEditTextCell3.setTag(1);
                    if (ChatAttachAlertPollLayout.this.solutionString != null) {
                        charSequence = ChatAttachAlertPollLayout.this.solutionString;
                    }
                    pollEditTextCell3.setTextAndHint(charSequence, LocaleController.getString(C2797R.string.AddAnExplanation), false);
                    pollEditTextCell3.setTag(null);
                    if (!ChatAttachAlertPollLayout.this.todo) {
                        pollEditTextCell3.attachView.setAttachedMedia(ChatAttachAlertPollLayout.this.attachedMedia.get(-3), false);
                    }
                    ChatAttachAlertPollLayout.this.setTextLeft(viewHolder.itemView, viewHolder.getAdapterPosition());
                    return;
                }
                return;
            }
            int adapterPosition = viewHolder.getAdapterPosition();
            PollEditTextCell pollEditTextCell4 = (PollEditTextCell) viewHolder.itemView;
            pollEditTextCell4.setTag(1);
            pollEditTextCell4.setCheckboxMultiselect(ChatAttachAlertPollLayout.this.multipleChoise, false);
            int i = adapterPosition - ChatAttachAlertPollLayout.this.answerStartRow;
            pollEditTextCell4.setTextAndHint(ChatAttachAlertPollLayout.this.answers[i], LocaleController.getString(ChatAttachAlertPollLayout.this.todo ? C2797R.string.TodoTaskPlaceholder : C2797R.string.OptionHint), true);
            pollEditTextCell4.setTag(null);
            if (ChatAttachAlertPollLayout.this.requestFieldFocusAtPosition == adapterPosition) {
                EditTextBoldCursor textView = pollEditTextCell4.getTextView();
                textView.requestFocus();
                AndroidUtilities.showKeyboard(textView);
                ChatAttachAlertPollLayout.this.requestFieldFocusAtPosition = -1;
            }
            if (!ChatAttachAlertPollLayout.this.todo) {
                pollEditTextCell4.attachView.setAttachedMedia(ChatAttachAlertPollLayout.this.attachedMedia.get(i), false);
            }
            ChatAttachAlertPollLayout.this.setTextLeft(viewHolder.itemView, adapterPosition);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onViewDetachedFromWindow(RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() == 4 || viewHolder.getItemViewType() == 11 || viewHolder.getItemViewType() == 5) {
                EditTextBoldCursor textView = ((PollEditTextCell) viewHolder.itemView).getTextView();
                if (textView.isFocused()) {
                    if (ChatAttachAlertPollLayout.this.isPremium) {
                        if (ChatAttachAlertPollLayout.this.suggestEmojiPanel != null) {
                            ChatAttachAlertPollLayout.this.suggestEmojiPanel.forceClose();
                        }
                        ChatAttachAlertPollLayout.this.hideEmojiPopup(true);
                    }
                    ChatAttachAlertPollLayout.this.currentCell = null;
                    textView.clearFocus();
                    AndroidUtilities.hideKeyboard(textView);
                }
            }
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return adapterPosition == ChatAttachAlertPollLayout.this.addAnswerRow || adapterPosition == ChatAttachAlertPollLayout.this.poll2vQuizRow || adapterPosition == ChatAttachAlertPollLayout.this.poll2vAnonymousRow || adapterPosition == ChatAttachAlertPollLayout.this.poll2vMultipleRow || adapterPosition == ChatAttachAlertPollLayout.this.poll2vAllowAddingRow || adapterPosition == ChatAttachAlertPollLayout.this.poll2vLimitDurationRow || adapterPosition == ChatAttachAlertPollLayout.this.poll2vAllowRevotingRow || adapterPosition == ChatAttachAlertPollLayout.this.poll2vShuffleRow || adapterPosition == ChatAttachAlertPollLayout.this.poll2vLimitDurationTimeRow || adapterPosition == ChatAttachAlertPollLayout.this.poll2vLimitDurationHideResultsRow || adapterPosition == ChatAttachAlertPollLayout.this.poll2vLimitByCountryRow.row || adapterPosition == ChatAttachAlertPollLayout.this.poll2vSubscribersOnlyRow.row || adapterPosition == ChatAttachAlertPollLayout.this.poll2vLimitByCountryListRow;
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter$1 */
        public class C41521 extends PollEditTextCell {
            final /* synthetic */ int val$viewType;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C41521(Context context, boolean z, int i, View.OnClickListener onClickListener, Theme.ResourcesProvider resourcesProvider, int i2) {
                super(context, z, i, onClickListener, resourcesProvider);
                i = i2;
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public void onFieldTouchUp(EditTextBoldCursor editTextBoldCursor) {
                ChatAttachAlertPollLayout.this.parentAlert.makeFocusable(editTextBoldCursor, true);
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public void onEditTextFocusChanged(boolean z) {
                ChatAttachAlertPollLayout.this.onCellFocusChanges(this, z);
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public void onActionModeStart(EditTextBoldCursor editTextBoldCursor, ActionMode actionMode) {
                if (!ChatAttachAlertPollLayout.this.todo && i == 11) {
                    if (editTextBoldCursor.isFocused() && editTextBoldCursor.hasSelection()) {
                        Menu menu = actionMode.getMenu();
                        if (menu.findItem(R.id.copy) == null) {
                            return;
                        }
                        ChatActivity.fillActionModeMenu(menu, ((ChatActivity) ChatAttachAlertPollLayout.this.parentAlert.baseFragment).getCurrentEncryptedChat(), false, true);
                        return;
                    }
                    return;
                }
                super.onActionModeStart(editTextBoldCursor, actionMode);
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            /* JADX INFO: renamed from: onEmojiButtonClicked */
            public void lambda$new$1(PollEditTextCell pollEditTextCell) {
                ChatAttachAlertPollLayout.this.onEmojiClicked(pollEditTextCell);
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public boolean onPastedMultipleLines(ArrayList<CharSequence> arrayList) {
                ListAdapter listAdapter;
                if (arrayList.isEmpty()) {
                    return false;
                }
                this.textView.getText().replace(this.textView.getSelectionStart(), this.textView.getSelectionEnd(), arrayList.remove(0));
                int i = 0;
                while (!arrayList.isEmpty() && i < ChatAttachAlertPollLayout.this.maxAnswersCount) {
                    int length = ChatAttachAlertPollLayout.this.answers.length - 1;
                    while (true) {
                        listAdapter = ListAdapter.this;
                        if (length > i) {
                            ChatAttachAlertPollLayout.this.answers[length] = ChatAttachAlertPollLayout.this.answers[length - 1];
                            length--;
                        }
                    }
                    ChatAttachAlertPollLayout.this.answers[i] = arrayList.remove(0);
                    ChatAttachAlertPollLayout.this.answersCount++;
                    i++;
                }
                ChatAttachAlertPollLayout.this.updateRows();
                ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                chatAttachAlertPollLayout.requestFieldFocusAtPosition = (chatAttachAlertPollLayout.answerStartRow + i) - 1;
                ChatAttachAlertPollLayout.this.listView.setItemAnimator(ChatAttachAlertPollLayout.this.itemAnimator);
                ChatAttachAlertPollLayout.this.listAdapter.notifyDataSetChanged();
                return true;
            }
        }

        public /* synthetic */ void lambda$onCreateViewHolder$0(View view) {
            ChatAttachAlertPollLayout.this.openAttachOrReplaceMenuForOptions(-2);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter$2 */
        public class C41532 implements TextWatcher {
            final /* synthetic */ PollEditTextCell val$cell;
            final /* synthetic */ int val$viewType;

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public C41532(PollEditTextCell pollEditTextCell, int i) {
                pollEditTextCell = pollEditTextCell;
                i = i;
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (pollEditTextCell.getTag() != null) {
                    return;
                }
                int i = i;
                ListAdapter listAdapter = ListAdapter.this;
                int i2 = i == 11 ? ChatAttachAlertPollLayout.this.descriptionRow : ChatAttachAlertPollLayout.this.questionRow;
                RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = ChatAttachAlertPollLayout.this.listView.findViewHolderForAdapterPosition(i2);
                if (viewHolderFindViewHolderForAdapterPosition != null && ChatAttachAlertPollLayout.this.suggestEmojiPanel != null) {
                    for (ImageSpan imageSpan : (ImageSpan[]) editable.getSpans(0, editable.length(), ImageSpan.class)) {
                        editable.removeSpan(imageSpan);
                    }
                    Emoji.replaceEmoji(editable, pollEditTextCell.getEditField().getPaint().getFontMetricsInt(), false);
                    ChatAttachAlertPollLayout.this.suggestEmojiPanel.setDirection(1);
                    ChatAttachAlertPollLayout.this.suggestEmojiPanel.setDelegate(pollEditTextCell);
                    ChatAttachAlertPollLayout.this.suggestEmojiPanel.setTranslationY(viewHolderFindViewHolderForAdapterPosition.itemView.getY());
                    ChatAttachAlertPollLayout.this.suggestEmojiPanel.fireUpdate();
                }
                int i3 = i;
                ListAdapter listAdapter2 = ListAdapter.this;
                if (i3 == 11) {
                    ChatAttachAlertPollLayout.this.descriptionString = editable;
                } else {
                    ChatAttachAlertPollLayout.this.questionString = editable;
                }
                if (viewHolderFindViewHolderForAdapterPosition != null) {
                    ChatAttachAlertPollLayout.this.setTextLeft(viewHolderFindViewHolderForAdapterPosition.itemView, i2);
                }
                ChatAttachAlertPollLayout.this.checkDoneButton();
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter$3 */
        public class C41543 extends PollEditTextCell {
            public C41543(Context context, boolean z, int i, View.OnClickListener onClickListener) {
                super(context, z, i, onClickListener);
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public void onFieldTouchUp(EditTextBoldCursor editTextBoldCursor) {
                ChatAttachAlertPollLayout.this.parentAlert.makeFocusable(editTextBoldCursor, true);
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public void onEditTextFocusChanged(boolean z) {
                ChatAttachAlertPollLayout.this.onCellFocusChanges(this, z);
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public void onActionModeStart(EditTextBoldCursor editTextBoldCursor, ActionMode actionMode) {
                if (editTextBoldCursor.isFocused() && editTextBoldCursor.hasSelection()) {
                    Menu menu = actionMode.getMenu();
                    if (menu.findItem(R.id.copy) == null) {
                        return;
                    }
                    ChatActivity.fillActionModeMenu(menu, ((ChatActivity) ChatAttachAlertPollLayout.this.parentAlert.baseFragment).getCurrentEncryptedChat(), false, true);
                }
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            /* JADX INFO: renamed from: onEmojiButtonClicked */
            public void lambda$new$1(PollEditTextCell pollEditTextCell) {
                ChatAttachAlertPollLayout.this.onEmojiClicked(pollEditTextCell);
            }
        }

        public /* synthetic */ void lambda$onCreateViewHolder$1(View view) {
            ChatAttachAlertPollLayout.this.openAttachOrReplaceMenuForOptions(-3);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter$4 */
        public class C41554 implements TextWatcher {
            final /* synthetic */ PollEditTextCell val$cell;

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public C41554(PollEditTextCell pollEditTextCell) {
                pollEditTextCell = pollEditTextCell;
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (pollEditTextCell.getTag() != null) {
                    return;
                }
                RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = ChatAttachAlertPollLayout.this.listView.findViewHolderForAdapterPosition(ChatAttachAlertPollLayout.this.solutionRow);
                if (viewHolderFindViewHolderForAdapterPosition != null && ChatAttachAlertPollLayout.this.suggestEmojiPanel != null) {
                    for (ImageSpan imageSpan : (ImageSpan[]) editable.getSpans(0, editable.length(), ImageSpan.class)) {
                        editable.removeSpan(imageSpan);
                    }
                    Emoji.replaceEmoji(editable, pollEditTextCell.getEditField().getPaint().getFontMetricsInt(), false);
                    ChatAttachAlertPollLayout.this.suggestEmojiPanel.setDirection(1);
                    ChatAttachAlertPollLayout.this.suggestEmojiPanel.setDelegate(pollEditTextCell);
                    ChatAttachAlertPollLayout.this.suggestEmojiPanel.setTranslationY(viewHolderFindViewHolderForAdapterPosition.itemView.getY());
                    ChatAttachAlertPollLayout.this.suggestEmojiPanel.fireUpdate();
                }
                ChatAttachAlertPollLayout.this.solutionString = editable;
                if (viewHolderFindViewHolderForAdapterPosition != null) {
                    ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                    chatAttachAlertPollLayout.setTextLeft(viewHolderFindViewHolderForAdapterPosition.itemView, chatAttachAlertPollLayout.solutionRow);
                }
                ChatAttachAlertPollLayout.this.checkDoneButton();
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter$5 */
        public class C41565 extends View {
            public C41565(Context context) {
                super(context);
            }

            @Override // android.view.View
            public void onMeasure(int i, int i2) {
                setMeasuredDimension(View.MeasureSpec.getSize(i), ChatAttachAlertPollLayout.this.topPadding);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View headerCell;
            View textInfoPrivacyCell;
            switch (i) {
                case 0:
                    headerCell = new HeaderCell(this.mContext, Theme.key_windowBackgroundWhiteBlueHeader, 21, 15, false, ChatAttachAlertPollLayout.this.resourcesProvider);
                    textInfoPrivacyCell = headerCell;
                    break;
                case 1:
                    ShadowSectionCell shadowSectionCell = new ShadowSectionCell(this.mContext, ChatAttachAlertPollLayout.this.resourcesProvider);
                    new CombinedDrawable(new ColorDrawable(ChatAttachAlertPollLayout.this.getThemedColor(Theme.key_windowBackgroundGray)), Theme.getThemedDrawableByKey(this.mContext, C2797R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow)).setFullsize(true);
                    textInfoPrivacyCell = shadowSectionCell;
                    break;
                case 2:
                    textInfoPrivacyCell = new TextInfoPrivacyCell(this.mContext, ChatAttachAlertPollLayout.this.resourcesProvider);
                    break;
                case 3:
                    textInfoPrivacyCell = new TextCell(this.mContext, ChatAttachAlertPollLayout.this.resourcesProvider);
                    break;
                case 4:
                case 11:
                    Context context = this.mContext;
                    boolean z = ChatAttachAlertPollLayout.this.isPremium;
                    C41521 c41521 = new PollEditTextCell(context, false, z ? 1 : 0, null, ChatAttachAlertPollLayout.this.resourcesProvider) { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.ListAdapter.1
                        final /* synthetic */ int val$viewType;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C41521(Context context2, boolean z2, int i2, View.OnClickListener onClickListener, Theme.ResourcesProvider resourcesProvider, int i3) {
                            super(context2, z2, i2, onClickListener, resourcesProvider);
                            i = i3;
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public void onFieldTouchUp(EditTextBoldCursor editTextBoldCursor) {
                            ChatAttachAlertPollLayout.this.parentAlert.makeFocusable(editTextBoldCursor, true);
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public void onEditTextFocusChanged(boolean z2) {
                            ChatAttachAlertPollLayout.this.onCellFocusChanges(this, z2);
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public void onActionModeStart(EditTextBoldCursor editTextBoldCursor, ActionMode actionMode) {
                            if (!ChatAttachAlertPollLayout.this.todo && i == 11) {
                                if (editTextBoldCursor.isFocused() && editTextBoldCursor.hasSelection()) {
                                    Menu menu = actionMode.getMenu();
                                    if (menu.findItem(R.id.copy) == null) {
                                        return;
                                    }
                                    ChatActivity.fillActionModeMenu(menu, ((ChatActivity) ChatAttachAlertPollLayout.this.parentAlert.baseFragment).getCurrentEncryptedChat(), false, true);
                                    return;
                                }
                                return;
                            }
                            super.onActionModeStart(editTextBoldCursor, actionMode);
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        /* JADX INFO: renamed from: onEmojiButtonClicked */
                        public void lambda$new$1(PollEditTextCell pollEditTextCell) {
                            ChatAttachAlertPollLayout.this.onEmojiClicked(pollEditTextCell);
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public boolean onPastedMultipleLines(ArrayList<CharSequence> arrayList) {
                            ListAdapter listAdapter;
                            if (arrayList.isEmpty()) {
                                return false;
                            }
                            this.textView.getText().replace(this.textView.getSelectionStart(), this.textView.getSelectionEnd(), arrayList.remove(0));
                            int i2 = 0;
                            while (!arrayList.isEmpty() && i2 < ChatAttachAlertPollLayout.this.maxAnswersCount) {
                                int length = ChatAttachAlertPollLayout.this.answers.length - 1;
                                while (true) {
                                    listAdapter = ListAdapter.this;
                                    if (length > i2) {
                                        ChatAttachAlertPollLayout.this.answers[length] = ChatAttachAlertPollLayout.this.answers[length - 1];
                                        length--;
                                    }
                                }
                                ChatAttachAlertPollLayout.this.answers[i2] = arrayList.remove(0);
                                ChatAttachAlertPollLayout.this.answersCount++;
                                i2++;
                            }
                            ChatAttachAlertPollLayout.this.updateRows();
                            ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                            chatAttachAlertPollLayout.requestFieldFocusAtPosition = (chatAttachAlertPollLayout.answerStartRow + i2) - 1;
                            ChatAttachAlertPollLayout.this.listView.setItemAnimator(ChatAttachAlertPollLayout.this.itemAnimator);
                            ChatAttachAlertPollLayout.this.listAdapter.notifyDataSetChanged();
                            return true;
                        }
                    };
                    if (i3 == 11 && !ChatAttachAlertPollLayout.this.todo) {
                        c41521.setTextRight(98);
                        c41521.addAttachView().setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f$0.lambda$onCreateViewHolder$0(view);
                            }
                        });
                    }
                    c41521.createErrorTextView();
                    c41521.setIconsColor(Theme.key_pollCreateIcons);
                    c41521.addTextWatcher(new TextWatcher() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.ListAdapter.2
                        final /* synthetic */ PollEditTextCell val$cell;
                        final /* synthetic */ int val$viewType;

                        @Override // android.text.TextWatcher
                        public void beforeTextChanged(CharSequence charSequence, int i2, int i22, int i3) {
                        }

                        @Override // android.text.TextWatcher
                        public void onTextChanged(CharSequence charSequence, int i2, int i22, int i3) {
                        }

                        public C41532(PollEditTextCell c415212, int i3) {
                            pollEditTextCell = c415212;
                            i = i3;
                        }

                        @Override // android.text.TextWatcher
                        public void afterTextChanged(Editable editable) {
                            if (pollEditTextCell.getTag() != null) {
                                return;
                            }
                            int i2 = i;
                            ListAdapter listAdapter = ListAdapter.this;
                            int i22 = i2 == 11 ? ChatAttachAlertPollLayout.this.descriptionRow : ChatAttachAlertPollLayout.this.questionRow;
                            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = ChatAttachAlertPollLayout.this.listView.findViewHolderForAdapterPosition(i22);
                            if (viewHolderFindViewHolderForAdapterPosition != null && ChatAttachAlertPollLayout.this.suggestEmojiPanel != null) {
                                for (ImageSpan imageSpan : (ImageSpan[]) editable.getSpans(0, editable.length(), ImageSpan.class)) {
                                    editable.removeSpan(imageSpan);
                                }
                                Emoji.replaceEmoji(editable, pollEditTextCell.getEditField().getPaint().getFontMetricsInt(), false);
                                ChatAttachAlertPollLayout.this.suggestEmojiPanel.setDirection(1);
                                ChatAttachAlertPollLayout.this.suggestEmojiPanel.setDelegate(pollEditTextCell);
                                ChatAttachAlertPollLayout.this.suggestEmojiPanel.setTranslationY(viewHolderFindViewHolderForAdapterPosition.itemView.getY());
                                ChatAttachAlertPollLayout.this.suggestEmojiPanel.fireUpdate();
                            }
                            int i3 = i;
                            ListAdapter listAdapter2 = ListAdapter.this;
                            if (i3 == 11) {
                                ChatAttachAlertPollLayout.this.descriptionString = editable;
                            } else {
                                ChatAttachAlertPollLayout.this.questionString = editable;
                            }
                            if (viewHolderFindViewHolderForAdapterPosition != null) {
                                ChatAttachAlertPollLayout.this.setTextLeft(viewHolderFindViewHolderForAdapterPosition.itemView, i22);
                            }
                            ChatAttachAlertPollLayout.this.checkDoneButton();
                        }
                    });
                    headerCell = c415212;
                    textInfoPrivacyCell = headerCell;
                    break;
                case 5:
                default:
                    Context context2 = this.mContext;
                    boolean z2 = ChatAttachAlertPollLayout.this.isPremium;
                    final C41576 c41576 = new PollEditTextCell(context2, false, z2 ? 1 : 0, new View.OnClickListener() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$onCreateViewHolder$2(view);
                        }
                    }, ChatAttachAlertPollLayout.this.resourcesProvider) { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.ListAdapter.6
                        public C41576(Context context22, boolean z3, int i2, View.OnClickListener onClickListener, Theme.ResourcesProvider resourcesProvider) {
                            super(context22, z3, i2, onClickListener, resourcesProvider);
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public void onActionModeStart(EditTextBoldCursor editTextBoldCursor, ActionMode actionMode) {
                            if (ChatAttachAlertPollLayout.this.todo) {
                                if (editTextBoldCursor.isFocused() && editTextBoldCursor.hasSelection()) {
                                    Menu menu = actionMode.getMenu();
                                    if (menu.findItem(R.id.copy) == null) {
                                        return;
                                    }
                                    ChatActivity.fillActionModeMenu(menu, ((ChatActivity) ChatAttachAlertPollLayout.this.parentAlert.baseFragment).getCurrentEncryptedChat(), false, true);
                                    return;
                                }
                                return;
                            }
                            super.onActionModeStart(editTextBoldCursor, actionMode);
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public boolean drawDivider() {
                            RecyclerView.ViewHolder viewHolderFindContainingViewHolder = ChatAttachAlertPollLayout.this.listView.findContainingViewHolder(this);
                            if (viewHolderFindContainingViewHolder != null) {
                                int adapterPosition = viewHolderFindContainingViewHolder.getAdapterPosition();
                                if (ChatAttachAlertPollLayout.this.answersCount == ChatAttachAlertPollLayout.this.maxAnswersCount && adapterPosition == (ChatAttachAlertPollLayout.this.answerStartRow + ChatAttachAlertPollLayout.this.answersCount) - 1) {
                                    return false;
                                }
                            }
                            return true;
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public boolean shouldShowCheckBox() {
                            return ChatAttachAlertPollLayout.this.quizPoll;
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public void onFieldTouchUp(EditTextBoldCursor editTextBoldCursor) {
                            ChatAttachAlertPollLayout.this.parentAlert.makeFocusable(editTextBoldCursor, true);
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public void onEditTextFocusChanged(boolean z3) {
                            ChatAttachAlertPollLayout.this.onCellFocusChanges(this, z3);
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public void onCheckBoxClick(PollEditTextCell pollEditTextCell, boolean z3) {
                            int adapterPosition;
                            if (z3 && ChatAttachAlertPollLayout.this.quizPoll && !ChatAttachAlertPollLayout.this.multipleChoise) {
                                Arrays.fill(ChatAttachAlertPollLayout.this.answersChecks, false);
                                ChatAttachAlertPollLayout.this.listView.getChildCount();
                                for (int i2 = ChatAttachAlertPollLayout.this.answerStartRow; i2 < ChatAttachAlertPollLayout.this.answerStartRow + ChatAttachAlertPollLayout.this.answersCount; i2++) {
                                    RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = ChatAttachAlertPollLayout.this.listView.findViewHolderForAdapterPosition(i2);
                                    if (viewHolderFindViewHolderForAdapterPosition != null) {
                                        View view = viewHolderFindViewHolderForAdapterPosition.itemView;
                                        if (view instanceof PollEditTextCell) {
                                            ((PollEditTextCell) view).setChecked(false, true);
                                        }
                                    }
                                }
                            }
                            super.onCheckBoxClick(pollEditTextCell, z3);
                            RecyclerView.ViewHolder viewHolderFindContainingViewHolder = ChatAttachAlertPollLayout.this.listView.findContainingViewHolder(pollEditTextCell);
                            if (viewHolderFindContainingViewHolder != null && (adapterPosition = viewHolderFindContainingViewHolder.getAdapterPosition()) != -1) {
                                ChatAttachAlertPollLayout.this.answersChecks[adapterPosition - ChatAttachAlertPollLayout.this.answerStartRow] = z3;
                            }
                            ChatAttachAlertPollLayout.this.checkDoneButton();
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public boolean isChecked(PollEditTextCell pollEditTextCell) {
                            int adapterPosition;
                            RecyclerView.ViewHolder viewHolderFindContainingViewHolder = ChatAttachAlertPollLayout.this.listView.findContainingViewHolder(pollEditTextCell);
                            if (viewHolderFindContainingViewHolder == null || (adapterPosition = viewHolderFindContainingViewHolder.getAdapterPosition()) == -1) {
                                return false;
                            }
                            return ChatAttachAlertPollLayout.this.answersChecks[adapterPosition - ChatAttachAlertPollLayout.this.answerStartRow];
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        /* JADX INFO: renamed from: onEmojiButtonClicked */
                        public void lambda$new$1(PollEditTextCell pollEditTextCell) {
                            ChatAttachAlertPollLayout.this.onEmojiClicked(pollEditTextCell);
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public boolean onPastedMultipleLines(ArrayList<CharSequence> arrayList) {
                            int childAdapterPosition;
                            ListAdapter listAdapter;
                            if (arrayList.isEmpty() || (childAdapterPosition = ChatAttachAlertPollLayout.this.listView.getChildAdapterPosition(this) - ChatAttachAlertPollLayout.this.answerStartRow) < 0) {
                                return false;
                            }
                            this.textView.getText().replace(this.textView.getSelectionStart(), this.textView.getSelectionEnd(), arrayList.remove(0));
                            int i2 = childAdapterPosition + 1;
                            while (!arrayList.isEmpty() && i2 < ChatAttachAlertPollLayout.this.maxAnswersCount) {
                                int length = ChatAttachAlertPollLayout.this.answers.length - 1;
                                while (true) {
                                    listAdapter = ListAdapter.this;
                                    if (length > i2) {
                                        ChatAttachAlertPollLayout.this.answers[length] = ChatAttachAlertPollLayout.this.answers[length - 1];
                                        length--;
                                    }
                                }
                                ChatAttachAlertPollLayout.this.answers[i2] = arrayList.remove(0);
                                ChatAttachAlertPollLayout.this.answersCount++;
                                i2++;
                            }
                            ChatAttachAlertPollLayout.this.updateRows();
                            ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                            chatAttachAlertPollLayout.requestFieldFocusAtPosition = (chatAttachAlertPollLayout.answerStartRow + i2) - 1;
                            ChatAttachAlertPollLayout.this.listView.setItemAnimator(ChatAttachAlertPollLayout.this.itemAnimator);
                            ChatAttachAlertPollLayout.this.listAdapter.notifyDataSetChanged();
                            return true;
                        }
                    };
                    if (!ChatAttachAlertPollLayout.this.todo) {
                        c41576.setTextRight(140);
                        c41576.addAttachView().setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter$$ExternalSyntheticLambda3
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f$0.lambda$onCreateViewHolder$3(c41576, view);
                            }
                        });
                    }
                    int i2 = Theme.key_pollCreateIcons;
                    c41576.setIconsColor(i2);
                    c41576.supportMultiselect();
                    c41576.getCheckBox().setColor(-1, i2, Theme.key_checkboxCheck);
                    c41576.addTextWatcher(new TextWatcher() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.ListAdapter.7
                        final /* synthetic */ PollEditTextCell val$cell;

                        @Override // android.text.TextWatcher
                        public void beforeTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
                        }

                        @Override // android.text.TextWatcher
                        public void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
                        }

                        public C41587(final PollEditTextCell c415762) {
                            pollEditTextCell = c415762;
                        }

                        @Override // android.text.TextWatcher
                        public void afterTextChanged(Editable editable) {
                            int adapterPosition;
                            int adapterPosition2;
                            RecyclerView.ViewHolder viewHolderFindContainingViewHolder = ChatAttachAlertPollLayout.this.listView.findContainingViewHolder(pollEditTextCell);
                            if (viewHolderFindContainingViewHolder == null || (adapterPosition2 = (adapterPosition = viewHolderFindContainingViewHolder.getAdapterPosition()) - ChatAttachAlertPollLayout.this.answerStartRow) < 0 || adapterPosition2 >= ChatAttachAlertPollLayout.this.answers.length) {
                                return;
                            }
                            if (ChatAttachAlertPollLayout.this.suggestEmojiPanel != null) {
                                for (ImageSpan imageSpan : (ImageSpan[]) editable.getSpans(0, editable.length(), ImageSpan.class)) {
                                    editable.removeSpan(imageSpan);
                                }
                                Emoji.replaceEmoji(editable, pollEditTextCell.getEditField().getPaint().getFontMetricsInt(), false);
                                float y = (viewHolderFindContainingViewHolder.itemView.getY() - AndroidUtilities.m1036dp(166.0f)) + viewHolderFindContainingViewHolder.itemView.getMeasuredHeight();
                                ListAdapter listAdapter = ListAdapter.this;
                                if (y > 0.0f) {
                                    ChatAttachAlertPollLayout.this.suggestEmojiPanel.setDirection(0);
                                    ChatAttachAlertPollLayout.this.suggestEmojiPanel.setTranslationY(y);
                                } else {
                                    ChatAttachAlertPollLayout.this.suggestEmojiPanel.setDirection(1);
                                    ChatAttachAlertPollLayout.this.suggestEmojiPanel.setTranslationY(viewHolderFindContainingViewHolder.itemView.getY());
                                }
                                ChatAttachAlertPollLayout.this.suggestEmojiPanel.setDelegate(pollEditTextCell);
                                ChatAttachAlertPollLayout.this.suggestEmojiPanel.fireUpdate();
                            }
                            ChatAttachAlertPollLayout.this.answers[adapterPosition2] = editable;
                            ChatAttachAlertPollLayout.this.setTextLeft(pollEditTextCell, adapterPosition);
                            ChatAttachAlertPollLayout.this.checkDoneButton();
                        }
                    });
                    c415762.setShowNextButton(true);
                    EditTextBoldCursor textView = c415762.getTextView();
                    textView.setImeOptions(textView.getImeOptions() | 5);
                    textView.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter$$ExternalSyntheticLambda4
                        @Override // android.widget.TextView.OnEditorActionListener
                        public final boolean onEditorAction(TextView textView2, int i3, KeyEvent keyEvent) {
                            return this.f$0.lambda$onCreateViewHolder$4(c415762, textView2, i3, keyEvent);
                        }
                    });
                    textView.setOnKeyListener(new View.OnKeyListener() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter$$ExternalSyntheticLambda5
                        @Override // android.view.View.OnKeyListener
                        public final boolean onKey(View view, int i3, KeyEvent keyEvent) {
                            return ChatAttachAlertPollLayout.ListAdapter.m11205$r8$lambda$3KVDPGbSyw9E5cNTsjfrmIl1t0(c415762, view, i3, keyEvent);
                        }
                    });
                    textInfoPrivacyCell = c415762;
                    break;
                case 6:
                    textInfoPrivacyCell = new TextCheckCell(this.mContext, ChatAttachAlertPollLayout.this.resourcesProvider);
                    break;
                case 7:
                    C41543 c41543 = new PollEditTextCell(this.mContext, false, ChatAttachAlertPollLayout.this.isPremium ? 1 : 0, null) { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.ListAdapter.3
                        public C41543(Context context3, boolean z3, int i3, View.OnClickListener onClickListener) {
                            super(context3, z3, i3, onClickListener);
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public void onFieldTouchUp(EditTextBoldCursor editTextBoldCursor) {
                            ChatAttachAlertPollLayout.this.parentAlert.makeFocusable(editTextBoldCursor, true);
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public void onEditTextFocusChanged(boolean z3) {
                            ChatAttachAlertPollLayout.this.onCellFocusChanges(this, z3);
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        public void onActionModeStart(EditTextBoldCursor editTextBoldCursor, ActionMode actionMode) {
                            if (editTextBoldCursor.isFocused() && editTextBoldCursor.hasSelection()) {
                                Menu menu = actionMode.getMenu();
                                if (menu.findItem(R.id.copy) == null) {
                                    return;
                                }
                                ChatActivity.fillActionModeMenu(menu, ((ChatActivity) ChatAttachAlertPollLayout.this.parentAlert.baseFragment).getCurrentEncryptedChat(), false, true);
                            }
                        }

                        @Override // org.telegram.p035ui.Cells.PollEditTextCell
                        /* JADX INFO: renamed from: onEmojiButtonClicked */
                        public void lambda$new$1(PollEditTextCell pollEditTextCell) {
                            ChatAttachAlertPollLayout.this.onEmojiClicked(pollEditTextCell);
                        }
                    };
                    c41543.createErrorTextView();
                    if (!ChatAttachAlertPollLayout.this.todo) {
                        c41543.setTextRight(98);
                        c41543.addAttachView().setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter$$ExternalSyntheticLambda1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f$0.lambda$onCreateViewHolder$1(view);
                            }
                        });
                    }
                    c41543.setIconsColor(Theme.key_pollCreateIcons);
                    c41543.addTextWatcher(new TextWatcher() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.ListAdapter.4
                        final /* synthetic */ PollEditTextCell val$cell;

                        @Override // android.text.TextWatcher
                        public void beforeTextChanged(CharSequence charSequence, int i3, int i22, int i32) {
                        }

                        @Override // android.text.TextWatcher
                        public void onTextChanged(CharSequence charSequence, int i3, int i22, int i32) {
                        }

                        public C41554(PollEditTextCell c415432) {
                            pollEditTextCell = c415432;
                        }

                        @Override // android.text.TextWatcher
                        public void afterTextChanged(Editable editable) {
                            if (pollEditTextCell.getTag() != null) {
                                return;
                            }
                            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = ChatAttachAlertPollLayout.this.listView.findViewHolderForAdapterPosition(ChatAttachAlertPollLayout.this.solutionRow);
                            if (viewHolderFindViewHolderForAdapterPosition != null && ChatAttachAlertPollLayout.this.suggestEmojiPanel != null) {
                                for (ImageSpan imageSpan : (ImageSpan[]) editable.getSpans(0, editable.length(), ImageSpan.class)) {
                                    editable.removeSpan(imageSpan);
                                }
                                Emoji.replaceEmoji(editable, pollEditTextCell.getEditField().getPaint().getFontMetricsInt(), false);
                                ChatAttachAlertPollLayout.this.suggestEmojiPanel.setDirection(1);
                                ChatAttachAlertPollLayout.this.suggestEmojiPanel.setDelegate(pollEditTextCell);
                                ChatAttachAlertPollLayout.this.suggestEmojiPanel.setTranslationY(viewHolderFindViewHolderForAdapterPosition.itemView.getY());
                                ChatAttachAlertPollLayout.this.suggestEmojiPanel.fireUpdate();
                            }
                            ChatAttachAlertPollLayout.this.solutionString = editable;
                            if (viewHolderFindViewHolderForAdapterPosition != null) {
                                ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                                chatAttachAlertPollLayout.setTextLeft(viewHolderFindViewHolderForAdapterPosition.itemView, chatAttachAlertPollLayout.solutionRow);
                            }
                            ChatAttachAlertPollLayout.this.checkDoneButton();
                        }
                    });
                    headerCell = c415432;
                    textInfoPrivacyCell = headerCell;
                    break;
                case 8:
                    EmptyView emptyView = new EmptyView(this.mContext);
                    emptyView.setTag(-33024);
                    textInfoPrivacyCell = emptyView;
                    break;
                case 9:
                    C41565 c41565 = new View(this.mContext) { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.ListAdapter.5
                        public C41565(Context context3) {
                            super(context3);
                        }

                        @Override // android.view.View
                        public void onMeasure(int i3, int i22) {
                            setMeasuredDimension(View.MeasureSpec.getSize(i3), ChatAttachAlertPollLayout.this.topPadding);
                        }
                    };
                    c41565.setTag(-33024);
                    textInfoPrivacyCell = c41565;
                    break;
                case 10:
                    PollCreateCheckCell pollCreateCheckCell = new PollCreateCheckCell(this.mContext, ChatAttachAlertPollLayout.this.resourcesProvider);
                    pollCreateCheckCell.getCheckBox().setIcon(C2797R.drawable.permission_locked);
                    textInfoPrivacyCell = pollCreateCheckCell;
                    break;
            }
            textInfoPrivacyCell.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            return new RecyclerListView.Holder(textInfoPrivacyCell);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter$6 */
        public class C41576 extends PollEditTextCell {
            public C41576(Context context22, boolean z3, int i2, View.OnClickListener onClickListener, Theme.ResourcesProvider resourcesProvider) {
                super(context22, z3, i2, onClickListener, resourcesProvider);
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public void onActionModeStart(EditTextBoldCursor editTextBoldCursor, ActionMode actionMode) {
                if (ChatAttachAlertPollLayout.this.todo) {
                    if (editTextBoldCursor.isFocused() && editTextBoldCursor.hasSelection()) {
                        Menu menu = actionMode.getMenu();
                        if (menu.findItem(R.id.copy) == null) {
                            return;
                        }
                        ChatActivity.fillActionModeMenu(menu, ((ChatActivity) ChatAttachAlertPollLayout.this.parentAlert.baseFragment).getCurrentEncryptedChat(), false, true);
                        return;
                    }
                    return;
                }
                super.onActionModeStart(editTextBoldCursor, actionMode);
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public boolean drawDivider() {
                RecyclerView.ViewHolder viewHolderFindContainingViewHolder = ChatAttachAlertPollLayout.this.listView.findContainingViewHolder(this);
                if (viewHolderFindContainingViewHolder != null) {
                    int adapterPosition = viewHolderFindContainingViewHolder.getAdapterPosition();
                    if (ChatAttachAlertPollLayout.this.answersCount == ChatAttachAlertPollLayout.this.maxAnswersCount && adapterPosition == (ChatAttachAlertPollLayout.this.answerStartRow + ChatAttachAlertPollLayout.this.answersCount) - 1) {
                        return false;
                    }
                }
                return true;
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public boolean shouldShowCheckBox() {
                return ChatAttachAlertPollLayout.this.quizPoll;
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public void onFieldTouchUp(EditTextBoldCursor editTextBoldCursor) {
                ChatAttachAlertPollLayout.this.parentAlert.makeFocusable(editTextBoldCursor, true);
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public void onEditTextFocusChanged(boolean z3) {
                ChatAttachAlertPollLayout.this.onCellFocusChanges(this, z3);
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public void onCheckBoxClick(PollEditTextCell pollEditTextCell, boolean z3) {
                int adapterPosition;
                if (z3 && ChatAttachAlertPollLayout.this.quizPoll && !ChatAttachAlertPollLayout.this.multipleChoise) {
                    Arrays.fill(ChatAttachAlertPollLayout.this.answersChecks, false);
                    ChatAttachAlertPollLayout.this.listView.getChildCount();
                    for (int i2 = ChatAttachAlertPollLayout.this.answerStartRow; i2 < ChatAttachAlertPollLayout.this.answerStartRow + ChatAttachAlertPollLayout.this.answersCount; i2++) {
                        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = ChatAttachAlertPollLayout.this.listView.findViewHolderForAdapterPosition(i2);
                        if (viewHolderFindViewHolderForAdapterPosition != null) {
                            View view = viewHolderFindViewHolderForAdapterPosition.itemView;
                            if (view instanceof PollEditTextCell) {
                                ((PollEditTextCell) view).setChecked(false, true);
                            }
                        }
                    }
                }
                super.onCheckBoxClick(pollEditTextCell, z3);
                RecyclerView.ViewHolder viewHolderFindContainingViewHolder = ChatAttachAlertPollLayout.this.listView.findContainingViewHolder(pollEditTextCell);
                if (viewHolderFindContainingViewHolder != null && (adapterPosition = viewHolderFindContainingViewHolder.getAdapterPosition()) != -1) {
                    ChatAttachAlertPollLayout.this.answersChecks[adapterPosition - ChatAttachAlertPollLayout.this.answerStartRow] = z3;
                }
                ChatAttachAlertPollLayout.this.checkDoneButton();
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public boolean isChecked(PollEditTextCell pollEditTextCell) {
                int adapterPosition;
                RecyclerView.ViewHolder viewHolderFindContainingViewHolder = ChatAttachAlertPollLayout.this.listView.findContainingViewHolder(pollEditTextCell);
                if (viewHolderFindContainingViewHolder == null || (adapterPosition = viewHolderFindContainingViewHolder.getAdapterPosition()) == -1) {
                    return false;
                }
                return ChatAttachAlertPollLayout.this.answersChecks[adapterPosition - ChatAttachAlertPollLayout.this.answerStartRow];
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            /* JADX INFO: renamed from: onEmojiButtonClicked */
            public void lambda$new$1(PollEditTextCell pollEditTextCell) {
                ChatAttachAlertPollLayout.this.onEmojiClicked(pollEditTextCell);
            }

            @Override // org.telegram.p035ui.Cells.PollEditTextCell
            public boolean onPastedMultipleLines(ArrayList<CharSequence> arrayList) {
                int childAdapterPosition;
                ListAdapter listAdapter;
                if (arrayList.isEmpty() || (childAdapterPosition = ChatAttachAlertPollLayout.this.listView.getChildAdapterPosition(this) - ChatAttachAlertPollLayout.this.answerStartRow) < 0) {
                    return false;
                }
                this.textView.getText().replace(this.textView.getSelectionStart(), this.textView.getSelectionEnd(), arrayList.remove(0));
                int i2 = childAdapterPosition + 1;
                while (!arrayList.isEmpty() && i2 < ChatAttachAlertPollLayout.this.maxAnswersCount) {
                    int length = ChatAttachAlertPollLayout.this.answers.length - 1;
                    while (true) {
                        listAdapter = ListAdapter.this;
                        if (length > i2) {
                            ChatAttachAlertPollLayout.this.answers[length] = ChatAttachAlertPollLayout.this.answers[length - 1];
                            length--;
                        }
                    }
                    ChatAttachAlertPollLayout.this.answers[i2] = arrayList.remove(0);
                    ChatAttachAlertPollLayout.this.answersCount++;
                    i2++;
                }
                ChatAttachAlertPollLayout.this.updateRows();
                ChatAttachAlertPollLayout chatAttachAlertPollLayout = ChatAttachAlertPollLayout.this;
                chatAttachAlertPollLayout.requestFieldFocusAtPosition = (chatAttachAlertPollLayout.answerStartRow + i2) - 1;
                ChatAttachAlertPollLayout.this.listView.setItemAnimator(ChatAttachAlertPollLayout.this.itemAnimator);
                ChatAttachAlertPollLayout.this.listAdapter.notifyDataSetChanged();
                return true;
            }
        }

        public /* synthetic */ void lambda$onCreateViewHolder$2(View view) {
            ChatAttachAlertPollLayout.this.deletePollAnswerView(view, (PollEditTextCell) view.getParent(), true);
        }

        public /* synthetic */ void lambda$onCreateViewHolder$3(PollEditTextCell pollEditTextCell, View view) {
            int adapterPosition;
            RecyclerView.ViewHolder viewHolderFindContainingViewHolder = ChatAttachAlertPollLayout.this.listView.findContainingViewHolder(pollEditTextCell);
            if (viewHolderFindContainingViewHolder == null || (adapterPosition = viewHolderFindContainingViewHolder.getAdapterPosition() - ChatAttachAlertPollLayout.this.answerStartRow) < 0 || adapterPosition >= ChatAttachAlertPollLayout.this.answers.length) {
                return;
            }
            ChatAttachAlertPollLayout.this.openAttachOrReplaceMenuForOptions(adapterPosition);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter$7 */
        public class C41587 implements TextWatcher {
            final /* synthetic */ PollEditTextCell val$cell;

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }

            public C41587(final PollEditTextCell c415762) {
                pollEditTextCell = c415762;
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                int adapterPosition;
                int adapterPosition2;
                RecyclerView.ViewHolder viewHolderFindContainingViewHolder = ChatAttachAlertPollLayout.this.listView.findContainingViewHolder(pollEditTextCell);
                if (viewHolderFindContainingViewHolder == null || (adapterPosition2 = (adapterPosition = viewHolderFindContainingViewHolder.getAdapterPosition()) - ChatAttachAlertPollLayout.this.answerStartRow) < 0 || adapterPosition2 >= ChatAttachAlertPollLayout.this.answers.length) {
                    return;
                }
                if (ChatAttachAlertPollLayout.this.suggestEmojiPanel != null) {
                    for (ImageSpan imageSpan : (ImageSpan[]) editable.getSpans(0, editable.length(), ImageSpan.class)) {
                        editable.removeSpan(imageSpan);
                    }
                    Emoji.replaceEmoji(editable, pollEditTextCell.getEditField().getPaint().getFontMetricsInt(), false);
                    float y = (viewHolderFindContainingViewHolder.itemView.getY() - AndroidUtilities.m1036dp(166.0f)) + viewHolderFindContainingViewHolder.itemView.getMeasuredHeight();
                    ListAdapter listAdapter = ListAdapter.this;
                    if (y > 0.0f) {
                        ChatAttachAlertPollLayout.this.suggestEmojiPanel.setDirection(0);
                        ChatAttachAlertPollLayout.this.suggestEmojiPanel.setTranslationY(y);
                    } else {
                        ChatAttachAlertPollLayout.this.suggestEmojiPanel.setDirection(1);
                        ChatAttachAlertPollLayout.this.suggestEmojiPanel.setTranslationY(viewHolderFindContainingViewHolder.itemView.getY());
                    }
                    ChatAttachAlertPollLayout.this.suggestEmojiPanel.setDelegate(pollEditTextCell);
                    ChatAttachAlertPollLayout.this.suggestEmojiPanel.fireUpdate();
                }
                ChatAttachAlertPollLayout.this.answers[adapterPosition2] = editable;
                ChatAttachAlertPollLayout.this.setTextLeft(pollEditTextCell, adapterPosition);
                ChatAttachAlertPollLayout.this.checkDoneButton();
            }
        }

        public /* synthetic */ boolean lambda$onCreateViewHolder$4(PollEditTextCell pollEditTextCell, TextView textView, int i, KeyEvent keyEvent) {
            int adapterPosition;
            if (i != 5) {
                return false;
            }
            RecyclerView.ViewHolder viewHolderFindContainingViewHolder = ChatAttachAlertPollLayout.this.listView.findContainingViewHolder(pollEditTextCell);
            if (viewHolderFindContainingViewHolder != null && (adapterPosition = viewHolderFindContainingViewHolder.getAdapterPosition()) != -1) {
                int i2 = adapterPosition - ChatAttachAlertPollLayout.this.answerStartRow;
                if (i2 == ChatAttachAlertPollLayout.this.answersCount - 1 && ChatAttachAlertPollLayout.this.answersCount < ChatAttachAlertPollLayout.this.maxAnswersCount) {
                    ChatAttachAlertPollLayout.this.addNewField();
                } else if (i2 == ChatAttachAlertPollLayout.this.answersCount - 1) {
                    AndroidUtilities.hideKeyboard(pollEditTextCell.getTextView());
                } else {
                    RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = ChatAttachAlertPollLayout.this.listView.findViewHolderForAdapterPosition(adapterPosition + 1);
                    if (viewHolderFindViewHolderForAdapterPosition != null) {
                        View view = viewHolderFindViewHolderForAdapterPosition.itemView;
                        if (view instanceof PollEditTextCell) {
                            ((PollEditTextCell) view).getTextView().requestFocus();
                        }
                    }
                }
            }
            return true;
        }

        /* JADX INFO: renamed from: $r8$lambda$3KVDPGbSyw9E5-cNTsjfrmIl1t0 */
        public static /* synthetic */ boolean m11205$r8$lambda$3KVDPGbSyw9E5cNTsjfrmIl1t0(PollEditTextCell pollEditTextCell, View view, int i, KeyEvent keyEvent) {
            EditTextBoldCursor editTextBoldCursor = (EditTextBoldCursor) view;
            if (i != 67 || keyEvent.getAction() != 0 || editTextBoldCursor.length() != 0) {
                return false;
            }
            pollEditTextCell.callOnDelete();
            return true;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == ChatAttachAlertPollLayout.this.poll2vAnonymousRow || i == ChatAttachAlertPollLayout.this.poll2vMultipleRow || i == ChatAttachAlertPollLayout.this.poll2vQuizRow || i == ChatAttachAlertPollLayout.this.poll2vAllowAddingRow || i == ChatAttachAlertPollLayout.this.poll2vAllowRevotingRow || i == ChatAttachAlertPollLayout.this.poll2vShuffleRow || i == ChatAttachAlertPollLayout.this.poll2vLimitDurationRow || i == ChatAttachAlertPollLayout.this.poll2vSubscribersOnlyRow.row || i == ChatAttachAlertPollLayout.this.poll2vLimitByCountryRow.row) {
                return 10;
            }
            if (i == ChatAttachAlertPollLayout.this.questionHeaderRow || i == ChatAttachAlertPollLayout.this.answerHeaderRow || i == ChatAttachAlertPollLayout.this.settingsHeaderRow || i == ChatAttachAlertPollLayout.this.solutionRowHeader) {
                return 0;
            }
            if (i == ChatAttachAlertPollLayout.this.questionSectionRow) {
                return 1;
            }
            if (i == ChatAttachAlertPollLayout.this.answerSectionRow || i == ChatAttachAlertPollLayout.this.settingsSectionRow || i == ChatAttachAlertPollLayout.this.solutionInfoRow || i == ChatAttachAlertPollLayout.this.poll2vLimitDurationHideResultsRowInfo) {
                return 2;
            }
            if (i == ChatAttachAlertPollLayout.this.addAnswerRow || i == ChatAttachAlertPollLayout.this.poll2vLimitDurationTimeRow || i == ChatAttachAlertPollLayout.this.poll2vLimitByCountryListRow) {
                return 3;
            }
            if (i == ChatAttachAlertPollLayout.this.questionRow) {
                return 4;
            }
            if (i == ChatAttachAlertPollLayout.this.descriptionRow) {
                return 11;
            }
            if (i == ChatAttachAlertPollLayout.this.solutionRow) {
                return 7;
            }
            if (i == ChatAttachAlertPollLayout.this.allowAddingRow || i == ChatAttachAlertPollLayout.this.allowMarkingRow || i == ChatAttachAlertPollLayout.this.poll2vLimitDurationHideResultsRow) {
                return 6;
            }
            if (i == ChatAttachAlertPollLayout.this.emptyRow) {
                return 8;
            }
            return i == ChatAttachAlertPollLayout.this.paddingRow ? 9 : 5;
        }

        public void swapElements(int i, int i2) {
            int i3 = i - ChatAttachAlertPollLayout.this.answerStartRow;
            int i4 = i2 - ChatAttachAlertPollLayout.this.answerStartRow;
            if (i3 < 0 || i4 < 0 || i3 >= ChatAttachAlertPollLayout.this.answersCount || i4 >= ChatAttachAlertPollLayout.this.answersCount) {
                return;
            }
            PollAttachedMedia pollAttachedMedia = ChatAttachAlertPollLayout.this.attachedMedia.get(i3);
            ChatAttachAlertPollLayout.this.attachedMedia.set(i3, ChatAttachAlertPollLayout.this.attachedMedia.get(i4));
            ChatAttachAlertPollLayout.this.attachedMedia.set(i4, pollAttachedMedia);
            CharSequence charSequence = ChatAttachAlertPollLayout.this.answers[i3];
            ChatAttachAlertPollLayout.this.answers[i3] = ChatAttachAlertPollLayout.this.answers[i4];
            ChatAttachAlertPollLayout.this.answers[i4] = charSequence;
            boolean z = ChatAttachAlertPollLayout.this.answersChecks[i3];
            ChatAttachAlertPollLayout.this.answersChecks[i3] = ChatAttachAlertPollLayout.this.answersChecks[i4];
            ChatAttachAlertPollLayout.this.answersChecks[i4] = z;
            notifyItemMoved(i, i2);
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_dialogScrollGlow));
        int i = Theme.key_windowBackgroundGrayShadow;
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, i));
        int i2 = Theme.key_windowBackgroundGray;
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{ShadowSectionCell.class}, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{EmptyView.class}, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, i));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextInfoPrivacyCell.class}, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText4));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlueHeader));
        int i3 = Theme.key_text_RedRegular;
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{HeaderCell.class}, new String[]{"textView2"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i3));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{HeaderCell.class}, new String[]{"textView2"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText3));
        int i4 = Theme.key_windowBackgroundWhiteBlackText;
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{PollEditTextCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i4));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_HINTTEXTCOLOR, new Class[]{PollEditTextCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteHintText));
        int i5 = Theme.key_windowBackgroundWhiteGrayIcon;
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_HINTTEXTCOLOR, new Class[]{PollEditTextCell.class}, new String[]{"deleteImageView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i5));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_HINTTEXTCOLOR, new Class[]{PollEditTextCell.class}, new String[]{"moveImageView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i5));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_DRAWABLESELECTEDSTATE | ThemeDescription.FLAG_USEBACKGROUNDDRAWABLE, new Class[]{PollEditTextCell.class}, new String[]{"deleteImageView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_stickers_menuSelector));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{PollEditTextCell.class}, new String[]{"textView2"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i3));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{PollEditTextCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i5));
        int i6 = Theme.key_checkboxCheck;
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{PollEditTextCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i6));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i4));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"valueTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText2));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_switchTrack));
        int i7 = Theme.key_switchTrackChecked;
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i7));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_telegram_color_text));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextCell.class}, new String[]{"imageView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i7));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextCell.class}, new String[]{"imageView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i6));
        return arrayList;
    }

    private int getAnswersMaxCount() {
        if (this.todo) {
            return getMessagesController().todoItemsMax;
        }
        return getMessagesController().config.pollAnswersMax.get();
    }

    private int getCurrentAccount() {
        ChatAttachAlert chatAttachAlert = this.parentAlert;
        return chatAttachAlert != null ? chatAttachAlert.currentAccount : UserConfig.selectedAccount;
    }

    private MessagesController getMessagesController() {
        return MessagesController.getInstance(getCurrentAccount());
    }

    private void openEditOrReplaceMenu(final int i) {
        ChatAttachAlert chatAttachAlert;
        BaseFragment baseFragment;
        PollAttachedMedia pollAttachedMedia = this.attachedMedia.get(i);
        if (pollAttachedMedia == null || (chatAttachAlert = this.parentAlert) == null || (baseFragment = chatAttachAlert.baseFragment) == null) {
            return;
        }
        Activity parentActivity = baseFragment.getParentActivity();
        if (pollAttachedMedia instanceof PollAttachedMediaGallery) {
            ArrayList<Object> arrayList = new ArrayList<>(1);
            arrayList.add(((PollAttachedMediaGallery) pollAttachedMedia).photoEntry);
            PhotoViewer.getInstance().setParentActivity(parentActivity);
            PhotoViewer.getInstance().openPhotoForSelect(arrayList, 0, 14, false, new PhotoViewer.EmptyPhotoViewerProvider() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.12
                private boolean openReplace;
                final /* synthetic */ int val$index;

                @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
                public boolean allowCaption() {
                    return false;
                }

                public C413812(final int i2) {
                    i = i2;
                }

                @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
                public void onPollAttachReplace() {
                    this.openReplace = true;
                }

                @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
                public void onPollAttachDelete() {
                    ChatAttachAlertPollLayout.this.lambda$openAttachMenuForOptions$23(i, null);
                }

                @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
                public void onClose() {
                    super.onClose();
                    if (this.openReplace) {
                        ChatAttachAlertPollLayout.this.lambda$showOptionsForDrawable$19(i);
                    }
                }
            }, null);
            return;
        }
        if (pollAttachedMedia instanceof PollAttachedMediaSticker) {
            PollAttachedMediaSticker pollAttachedMediaSticker = (PollAttachedMediaSticker) pollAttachedMedia;
            ContentPreviewViewer.getInstance().setParentActivity(parentActivity);
            ContentPreviewViewer.getInstance().setDelegate(new C413913(i2));
            ContentPreviewViewer contentPreviewViewer = ContentPreviewViewer.getInstance();
            TLRPC.Document document = pollAttachedMediaSticker.sticker;
            contentPreviewViewer.open(document, null, _UrlKt.FRAGMENT_ENCODE_SET, null, null, MessageObject.isAnimatedEmoji(document) ? 2 : 0, false, pollAttachedMediaSticker.parent, this.resourcesProvider, 200);
            return;
        }
        if (pollAttachedMedia instanceof PollAttachedMediaFile) {
            PollAttachedMediaFile pollAttachedMediaFile = (PollAttachedMediaFile) pollAttachedMedia;
            final String str = pollAttachedMediaFile.name;
            final String str2 = AndroidUtilities.formatFileSize(pollAttachedMediaFile.size, true, true) + " " + pollAttachedMediaFile.ext;
            showOptionsForDrawable(i2, new Utilities.CallbackReturn() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda18
                @Override // org.telegram.messenger.Utilities.CallbackReturn
                public final Object run(Object obj) {
                    return PollAttachedMediaFile.createMessagePreviewDrawable((View) obj, str, str2, null, null);
                }
            }, AndroidUtilities.m1036dp(240.0f), AndroidUtilities.m1036dp(60.0f));
            return;
        }
        if (pollAttachedMedia instanceof PollAttachedMediaMusic) {
            final PollAttachedMediaMusic pollAttachedMediaMusic = (PollAttachedMediaMusic) pollAttachedMedia;
            TLRPC.Document document2 = pollAttachedMediaMusic.messageObject.getDocument();
            final String musicTitle = MessageObject.getMusicTitle(document2, true);
            final String str3 = MessageObject.getMusicAuthor(document2, true) + " - " + LocaleController.formatShortDuration((int) MessageObject.getDocumentDuration(document2));
            showOptionsForDrawable(i2, new Utilities.CallbackReturn() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda19
                @Override // org.telegram.messenger.Utilities.CallbackReturn
                public final Object run(Object obj) {
                    String str4 = musicTitle;
                    String str5 = str3;
                    PollAttachedMediaMusic pollAttachedMediaMusic2 = pollAttachedMediaMusic;
                    return PollAttachedMediaFile.createMessagePreviewDrawable((View) obj, str4, str5, pollAttachedMediaMusic2.messageObject.getDocument(), pollAttachedMediaMusic2.messageObject);
                }
            }, AndroidUtilities.m1036dp(240.0f), AndroidUtilities.m1036dp(60.0f));
            return;
        }
        if (pollAttachedMedia instanceof PollAttachedMediaLocation) {
            final PollAttachedMediaLocation pollAttachedMediaLocation = (PollAttachedMediaLocation) pollAttachedMedia;
            showOptionsForDrawable(i2, new Utilities.CallbackReturn() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda20
                @Override // org.telegram.messenger.Utilities.CallbackReturn
                public final Object run(Object obj) {
                    return pollAttachedMediaLocation.createMessagePreviewDrawable((View) obj);
                }
            }, AndroidUtilities.m1036dp(300.0f), (AndroidUtilities.m1036dp(300.0f) * 9) / 16);
        } else if (pollAttachedMedia instanceof PollAttachedMediaLink) {
            PollAttachedMediaLink pollAttachedMediaLink = (PollAttachedMediaLink) pollAttachedMedia;
            AlertsCreator.showAddLinkToPoll(getContext(), this.resourcesProvider, pollAttachedMediaLink.url, pollAttachedMediaLink.getWebPage(), new Utilities.Callback() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda21
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$openEditOrReplaceMenu$17(i2, (String) obj);
                }
            }, new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$openEditOrReplaceMenu$18(i2);
                }
            });
        } else {
            lambda$showOptionsForDrawable$19(i2);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$12 */
    public class C413812 extends PhotoViewer.EmptyPhotoViewerProvider {
        private boolean openReplace;
        final /* synthetic */ int val$index;

        @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean allowCaption() {
            return false;
        }

        public C413812(final int i2) {
            i = i2;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void onPollAttachReplace() {
            this.openReplace = true;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void onPollAttachDelete() {
            ChatAttachAlertPollLayout.this.lambda$openAttachMenuForOptions$23(i, null);
        }

        @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void onClose() {
            super.onClose();
            if (this.openReplace) {
                ChatAttachAlertPollLayout.this.lambda$showOptionsForDrawable$19(i);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$13 */
    public class C413913 implements ContentPreviewViewer.ContentPreviewViewerDelegate {
        final /* synthetic */ int val$index;

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public long getDialogId() {
            return 0L;
        }

        public C413913(int i) {
            this.val$index = i;
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public ItemOptions getCustomItemOptions(ViewGroup viewGroup, View view) {
            ItemOptions drawScrim = ItemOptions.makeOptions(viewGroup, new View(ChatAttachAlertPollLayout.this.getContext())).setDimAlpha(0).setDrawScrim(false);
            int i = C2797R.drawable.msg_replace;
            String string = LocaleController.getString(C2797R.string.ReplaceAttachedPollMedia);
            final int i2 = this.val$index;
            ItemOptions itemOptionsAdd = drawScrim.add(i, string, new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$13$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getCustomItemOptions$0(i2);
                }
            });
            int i3 = C2797R.drawable.msg_delete;
            String string2 = LocaleController.getString(C2797R.string.Delete);
            final int i4 = this.val$index;
            return itemOptionsAdd.add(i3, (CharSequence) string2, true, new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$13$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getCustomItemOptions$1(i4);
                }
            });
        }

        public /* synthetic */ void lambda$getCustomItemOptions$0(int i) {
            ChatAttachAlertPollLayout.this.lambda$showOptionsForDrawable$19(i);
        }

        public /* synthetic */ void lambda$getCustomItemOptions$1(int i) {
            ChatAttachAlertPollLayout.this.lambda$openAttachMenuForOptions$23(i, null);
        }
    }

    public /* synthetic */ void lambda$openEditOrReplaceMenu$17(int i, String str) {
        lambda$openAttachMenuForOptions$23(i, new PollAttachedMediaLink(str));
    }

    public /* synthetic */ void lambda$openEditOrReplaceMenu$18(int i) {
        lambda$openAttachMenuForOptions$23(i, null);
    }

    private void showOptionsForDrawable(final int i, Utilities.CallbackReturn<View, Drawable> callbackReturn, int i2, int i3) {
        ItemOptions itemOptionsAdd = ItemOptions.makeOptions(this, new View(getContext())).setDimAlpha(0).setDrawScrim(false).add(C2797R.drawable.msg_replace, LocaleController.getString(C2797R.string.ReplaceAttachedPollMedia), new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showOptionsForDrawable$19(i);
            }
        }).add(C2797R.drawable.msg_delete, (CharSequence) LocaleController.getString(C2797R.string.Delete), true, new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showOptionsForDrawable$20(i);
            }
        });
        ScrimOptions scrimOptions = new ScrimOptions(getContext(), this.resourcesProvider);
        itemOptionsAdd.setOnDismiss(new ChatActivity$$ExternalSyntheticLambda278(scrimOptions));
        itemOptionsAdd.setMinWidth(AndroidUtilities.m1036dp(185.0f));
        itemOptionsAdd.setupSelectors();
        scrimOptions.setItemOptions(itemOptionsAdd);
        scrimOptions.setScrimDrawable(callbackReturn.run(scrimOptions.getWindowView()), i2, i3);
        scrimOptions.setOptionsAtCenter();
        scrimOptions.show();
    }

    public /* synthetic */ void lambda$showOptionsForDrawable$20(int i) {
        lambda$openAttachMenuForOptions$23(i, null);
    }

    public void openAttachOrReplaceMenuForOptions(int i) {
        if (this.attachedMedia.get(i) != null) {
            openEditOrReplaceMenu(i);
        } else {
            lambda$showOptionsForDrawable$19(i);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$14 */
    public class DialogC414014 extends ChatAttachAlert {
        final /* synthetic */ Runnable val$onDismiss;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DialogC414014(Context context, BaseFragment baseFragment, boolean z, boolean z2, boolean z3, Theme.ResourcesProvider resourcesProvider, Runnable runnable) {
            super(context, baseFragment, z, z2, z3, resourcesProvider);
            runnable = runnable;
        }

        @Override // org.telegram.p035ui.Components.ChatAttachAlert, org.telegram.p035ui.ActionBar.BottomSheet
        public void dismissInternal() {
            super.dismissInternal();
            Runnable runnable = runnable;
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    public static ChatAttachAlert openPollAttachMenu(BaseFragment baseFragment, int i, int i2, final Utilities.Callback<PollAttachedMedia> callback, Runnable runnable) {
        if (baseFragment == null) {
            return null;
        }
        final DialogC414014 dialogC414014 = new ChatAttachAlert(baseFragment.getContext(), baseFragment, false, false, true, baseFragment.getResourceProvider()) { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.14
            final /* synthetic */ Runnable val$onDismiss;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public DialogC414014(Context context, BaseFragment baseFragment2, boolean z, boolean z2, boolean z3, Theme.ResourcesProvider resourcesProvider, Runnable runnable2) {
                super(context, baseFragment2, z, z2, z3, resourcesProvider);
                runnable = runnable2;
            }

            @Override // org.telegram.p035ui.Components.ChatAttachAlert, org.telegram.p035ui.ActionBar.BottomSheet
            public void dismissInternal() {
                super.dismissInternal();
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        };
        dialogC414014.setDelegate(new C414115(baseFragment2, callback, dialogC414014));
        dialogC414014.setEmojiViewDelegate(new EmojiView.EmojiViewDelegate() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.16
            final /* synthetic */ ChatAttachAlert val$chatAttachAlert;

            public C414216(final ChatAttachAlert dialogC4140142) {
                chatAttachAlert = dialogC4140142;
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public void onCustomEmojiSelected(long j, TLRPC.Document document, String str, boolean z) {
                callback.run(new PollAttachedMediaSticker(document, null));
                chatAttachAlert.dismiss(true);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public void onStickerSelected(View view, TLRPC.Document document, String str, Object obj, MessageObject.SendAnimationData sendAnimationData, boolean z, int i3, int i4) {
                callback.run(new PollAttachedMediaSticker(document, obj));
                chatAttachAlert.dismiss(true);
            }
        });
        dialogC4140142.getPhotoLayout().loadGalleryPhotos();
        dialogC4140142.setMaxSelectedPhotos(1, true);
        dialogC4140142.enablePollAttachMode(i, i2);
        dialogC4140142.setLocationActivityDelegate(new ChatAttachAlertLocationLayout.LocationActivityDelegate() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda23
            @Override // org.telegram.ui.Components.ChatAttachAlertLocationLayout.LocationActivityDelegate
            public final void didSelectLocation(TLRPC.MessageMedia messageMedia, int i3, boolean z, int i4, long j) {
                callback.run(new PollAttachedMediaLocation(messageMedia));
            }
        });
        dialogC4140142.setDocumentsDelegate(new ChatAttachAlertDocumentLayout.DocumentSelectActivityDelegate() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout.17
            final /* synthetic */ ChatAttachAlert val$chatAttachAlert;
            final /* synthetic */ BaseFragment val$fragment;

            public C414317(final ChatAttachAlert dialogC4140142, BaseFragment baseFragment2) {
                chatAttachAlert = dialogC4140142;
                baseFragment = baseFragment2;
            }

            @Override // org.telegram.ui.Components.ChatAttachAlertDocumentLayout.DocumentSelectActivityDelegate
            public void didSelectFiles(ArrayList<String> arrayList, String str, ArrayList<TLRPC.MessageEntity> arrayList2, ArrayList<MessageObject> arrayList3, boolean z, int i3, int i4, long j, boolean z2, long j2) {
                if (arrayList != null && !arrayList.isEmpty()) {
                    callback.run(new PollAttachedMediaFile(arrayList.get(0)));
                }
                chatAttachAlert.dismiss(true);
            }

            @Override // org.telegram.ui.Components.ChatAttachAlertDocumentLayout.DocumentSelectActivityDelegate
            public void didSelectPhotos(ArrayList<SendMessagesHelper.SendingMediaInfo> arrayList, boolean z, int i3, int i4, long j) {
                if (arrayList != null && !arrayList.isEmpty()) {
                    callback.run(new PollAttachedMediaGallery(arrayList.get(0)));
                }
                chatAttachAlert.dismiss(true);
            }

            @Override // org.telegram.ui.Components.ChatAttachAlertDocumentLayout.DocumentSelectActivityDelegate
            public void startDocumentSelectActivity() {
                try {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("*/*");
                    baseFragment.getParentActivity().startActivityForResult(intent, 28);
                } catch (Exception e) {
                    FileLog.m1048e(e);
                }
            }
        });
        dialogC4140142.setAudioSelectDelegate(new ChatAttachAlertAudioLayout.AudioSelectDelegate() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda24
            @Override // org.telegram.ui.Components.ChatAttachAlertAudioLayout.AudioSelectDelegate
            public final void didSelectAudio(ArrayList arrayList, CharSequence charSequence, boolean z, int i3, int i4, long j, boolean z2, long j2) {
                ChatAttachAlertPollLayout.$r8$lambda$YbyRLXlAvusUY_jQksdYboAzouA(callback, dialogC4140142, arrayList, charSequence, z, i3, i4, j, z2, j2);
            }
        });
        dialogC4140142.init();
        dialogC4140142.setFocusable(true);
        dialogC4140142.show();
        return dialogC4140142;
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$15 */
    public class C414115 implements ChatAttachAlert.ChatAttachViewDelegate {
        final /* synthetic */ Utilities.Callback val$callback;
        final /* synthetic */ ChatAttachAlert val$chatAttachAlert;
        final /* synthetic */ BaseFragment val$fragment;

        @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
        public void didSelectBot(TLRPC.User user) {
        }

        @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
        public boolean needEnterComment() {
            return false;
        }

        @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
        public void onCameraOpened() {
        }

        public C414115(BaseFragment baseFragment, Utilities.Callback callback, ChatAttachAlert chatAttachAlert) {
            this.val$fragment = baseFragment;
            this.val$callback = callback;
            this.val$chatAttachAlert = chatAttachAlert;
        }

        @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
        public void didPressedButton(int i, boolean z, boolean z2, int i2, int i3, long j, boolean z3, boolean z4, long j2) {
            if (i == 15) {
                Context context = this.val$fragment.getContext();
                Theme.ResourcesProvider resourceProvider = this.val$fragment.getResourceProvider();
                final Utilities.Callback callback = this.val$callback;
                AlertsCreator.showAddLinkToPoll(context, resourceProvider, null, null, new Utilities.Callback() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$15$$ExternalSyntheticLambda0
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        callback.run(new PollAttachedMediaLink((String) obj));
                    }
                }, null);
            } else if (i == 7 || i == 8) {
                HashMap<Object, Object> selectedPhotos = this.val$chatAttachAlert.getPhotoLayout().getSelectedPhotos();
                ArrayList<Object> selectedPhotosOrder = this.val$chatAttachAlert.getPhotoLayout().getSelectedPhotosOrder();
                if (selectedPhotosOrder.size() > 0) {
                    Object obj = selectedPhotos.get(selectedPhotosOrder.get(0));
                    SendMessagesHelper.SendingMediaInfo sendingMediaInfo = new SendMessagesHelper.SendingMediaInfo();
                    if (obj instanceof MediaController.PhotoEntry) {
                        MediaController.PhotoEntry photoEntry = (MediaController.PhotoEntry) obj;
                        String str = photoEntry.imagePath;
                        if (str != null) {
                            sendingMediaInfo.path = str;
                        } else {
                            sendingMediaInfo.path = photoEntry.path;
                        }
                        sendingMediaInfo.thumbPath = photoEntry.thumbPath;
                        sendingMediaInfo.coverPath = photoEntry.coverPath;
                        sendingMediaInfo.videoEditedInfo = photoEntry.editedInfo;
                        sendingMediaInfo.isLivePhoto = photoEntry.isLivePhoto();
                        sendingMediaInfo.livePhotoVideoOffset = photoEntry.livePhotoVideoOffset;
                        sendingMediaInfo.discardLivePhoto = true;
                        sendingMediaInfo.isVideo = photoEntry.isVideo;
                        CharSequence charSequence = photoEntry.caption;
                        sendingMediaInfo.caption = charSequence != null ? charSequence.toString() : null;
                        sendingMediaInfo.entities = photoEntry.entities;
                        sendingMediaInfo.masks = photoEntry.stickers;
                        sendingMediaInfo.ttl = photoEntry.ttl;
                        sendingMediaInfo.emojiMarkup = photoEntry.emojiMarkup;
                        sendingMediaInfo.originalPhotoEntry = photoEntry;
                    } else if (obj instanceof MediaController.SearchImage) {
                        MediaController.SearchImage searchImage = (MediaController.SearchImage) obj;
                        String str2 = searchImage.imagePath;
                        if (str2 != null) {
                            sendingMediaInfo.path = str2;
                        } else {
                            sendingMediaInfo.searchImage = searchImage;
                        }
                        sendingMediaInfo.thumbPath = searchImage.thumbPath;
                        sendingMediaInfo.coverPath = searchImage.coverPath;
                        sendingMediaInfo.videoEditedInfo = searchImage.editedInfo;
                        CharSequence charSequence2 = searchImage.caption;
                        sendingMediaInfo.caption = charSequence2 != null ? charSequence2.toString() : null;
                        sendingMediaInfo.entities = searchImage.entities;
                        sendingMediaInfo.masks = searchImage.stickers;
                        sendingMediaInfo.ttl = searchImage.ttl;
                        TLRPC.BotInlineResult botInlineResult = searchImage.inlineResult;
                        if (botInlineResult != null && searchImage.type == 1) {
                            sendingMediaInfo.inlineResult = botInlineResult;
                            sendingMediaInfo.params = searchImage.params;
                        }
                        searchImage.date = (int) (System.currentTimeMillis() / 1000);
                    }
                    this.val$callback.run(new PollAttachedMediaGallery(sendingMediaInfo));
                }
            }
            this.val$chatAttachAlert.dismiss(true);
        }

        @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
        public void doOnIdle(Runnable runnable) {
            NotificationCenter.getInstance(this.val$fragment.getCurrentAccount()).doOnIdle(runnable);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$16 */
    public class C414216 implements EmojiView.EmojiViewDelegate {
        final /* synthetic */ ChatAttachAlert val$chatAttachAlert;

        public C414216(final ChatAttachAlert dialogC4140142) {
            chatAttachAlert = dialogC4140142;
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public void onCustomEmojiSelected(long j, TLRPC.Document document, String str, boolean z) {
            callback.run(new PollAttachedMediaSticker(document, null));
            chatAttachAlert.dismiss(true);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public void onStickerSelected(View view, TLRPC.Document document, String str, Object obj, MessageObject.SendAnimationData sendAnimationData, boolean z, int i3, int i4) {
            callback.run(new PollAttachedMediaSticker(document, obj));
            chatAttachAlert.dismiss(true);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertPollLayout$17 */
    public class C414317 implements ChatAttachAlertDocumentLayout.DocumentSelectActivityDelegate {
        final /* synthetic */ ChatAttachAlert val$chatAttachAlert;
        final /* synthetic */ BaseFragment val$fragment;

        public C414317(final ChatAttachAlert dialogC4140142, BaseFragment baseFragment2) {
            chatAttachAlert = dialogC4140142;
            baseFragment = baseFragment2;
        }

        @Override // org.telegram.ui.Components.ChatAttachAlertDocumentLayout.DocumentSelectActivityDelegate
        public void didSelectFiles(ArrayList<String> arrayList, String str, ArrayList<TLRPC.MessageEntity> arrayList2, ArrayList<MessageObject> arrayList3, boolean z, int i3, int i4, long j, boolean z2, long j2) {
            if (arrayList != null && !arrayList.isEmpty()) {
                callback.run(new PollAttachedMediaFile(arrayList.get(0)));
            }
            chatAttachAlert.dismiss(true);
        }

        @Override // org.telegram.ui.Components.ChatAttachAlertDocumentLayout.DocumentSelectActivityDelegate
        public void didSelectPhotos(ArrayList<SendMessagesHelper.SendingMediaInfo> arrayList, boolean z, int i3, int i4, long j) {
            if (arrayList != null && !arrayList.isEmpty()) {
                callback.run(new PollAttachedMediaGallery(arrayList.get(0)));
            }
            chatAttachAlert.dismiss(true);
        }

        @Override // org.telegram.ui.Components.ChatAttachAlertDocumentLayout.DocumentSelectActivityDelegate
        public void startDocumentSelectActivity() {
            try {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("*/*");
                baseFragment.getParentActivity().startActivityForResult(intent, 28);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$YbyRLXlAvusUY_jQksdYboAzouA(Utilities.Callback callback, ChatAttachAlert chatAttachAlert, ArrayList arrayList, CharSequence charSequence, boolean z, int i, int i2, long j, boolean z2, long j2) {
        if (arrayList != null && !arrayList.isEmpty()) {
            callback.run(new PollAttachedMediaMusic((MessageObject) arrayList.get(0)));
        }
        chatAttachAlert.dismiss(true);
    }

    /* JADX INFO: renamed from: openAttachMenuForOptions */
    public void lambda$showOptionsForDrawable$19(final int i) {
        this.currentAttachAlertIndex = i;
        this.currentAttachAlert = openPollAttachMenu(this.parentAlert.baseFragment, getStartLayoutForMedia(this.attachedMedia.get(i)), getAllowedLayoutsForIndex(i), new Utilities.Callback() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda14
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$openAttachMenuForOptions$23(i, (PollAttachedMedia) obj);
            }
        }, new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openAttachMenuForOptions$24();
            }
        });
    }

    public /* synthetic */ void lambda$openAttachMenuForOptions$24() {
        this.currentAttachAlertIndex = -1;
        this.currentAttachAlert = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onPollAttachFilePicker(android.content.Intent r3) {
        /*
            r2 = this;
            int r0 = r2.currentAttachAlertIndex
            r1 = -1
            if (r0 == r1) goto L60
            org.telegram.ui.Components.ChatAttachAlert r0 = r2.currentAttachAlert
            if (r0 != 0) goto La
            goto L60
        La:
            if (r3 == 0) goto L31
            android.net.Uri r0 = r3.getData()
            if (r0 == 0) goto L17
            android.net.Uri r3 = r3.getData()
            goto L32
        L17:
            android.content.ClipData r0 = r3.getClipData()
            if (r0 == 0) goto L31
            android.content.ClipData r3 = r3.getClipData()
            int r0 = r3.getItemCount()
            if (r0 <= 0) goto L31
            r0 = 0
            android.content.ClipData$Item r3 = r3.getItemAt(r0)
            android.net.Uri r3 = r3.getUri()
            goto L32
        L31:
            r3 = 0
        L32:
            if (r3 != 0) goto L4e
            org.telegram.ui.Components.ChatAttachAlert r3 = r2.parentAlert
            org.telegram.ui.ActionBar.BottomSheet$ContainerView r3 = r3.container
            org.telegram.ui.ActionBar.Theme$ResourcesProvider r0 = r2.resourcesProvider
            org.telegram.ui.Components.BulletinFactory r3 = org.telegram.p035ui.Components.BulletinFactory.m1142of(r3, r0)
            int r0 = org.telegram.messenger.C2797R.string.UnsupportedAttachment
            java.lang.String r0 = org.telegram.messenger.LocaleController.getString(r0)
            org.telegram.ui.ActionBar.Theme$ResourcesProvider r2 = r2.resourcesProvider
            org.telegram.ui.Components.Bulletin r2 = r3.createErrorBulletin(r0, r2)
            r2.show()
            return
        L4e:
            int r0 = r2.currentAttachAlertIndex
            org.telegram.ui.Components.poll.attached.PollAttachedMediaFile r1 = new org.telegram.ui.Components.poll.attached.PollAttachedMediaFile
            r1.<init>(r3)
            r2.lambda$openAttachMenuForOptions$23(r0, r1)
            org.telegram.ui.Components.ChatAttachAlert r2 = r2.currentAttachAlert
            if (r2 == 0) goto L60
            r3 = 1
            r2.dismiss(r3)
        L60:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.ChatAttachAlertPollLayout.onPollAttachFilePicker(android.content.Intent):void");
    }

    public static int getStartLayoutForMedia(PollAttachedMedia pollAttachedMedia) {
        if (pollAttachedMedia instanceof PollAttachedMediaMusic) {
            return 3;
        }
        if (pollAttachedMedia instanceof PollAttachedMediaFile) {
            return 4;
        }
        if (pollAttachedMedia instanceof PollAttachedMediaSticker) {
            return ((PollAttachedMediaSticker) pollAttachedMedia).isEmoji ? 14 : 13;
        }
        if (pollAttachedMedia instanceof PollAttachedMediaLocation) {
            return 6;
        }
        return pollAttachedMedia instanceof PollAttachedMediaLink ? 15 : 1;
    }

    private void checkPollLinkMedia(PollAttachedMediaLink pollAttachedMediaLink, boolean z) {
        pollAttachedMediaLink.setWebPage(this.webPageLoader.getWebPage(pollAttachedMediaLink.url), this.webPageLoader.isLoading(pollAttachedMediaLink.url), z);
    }

    private int mediaIndexToAdapterPosition(int i) {
        if (i == -2) {
            return this.descriptionRow;
        }
        if (i == -3) {
            return this.solutionRow;
        }
        int i2 = this.answerStartRow;
        if (i2 < 0 || i < 0 || i >= this.answersCount) {
            return -1;
        }
        return i2 + i;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0028  */
    /* JADX INFO: renamed from: setAttachedMedia */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void lambda$openAttachMenuForOptions$23(int r4, final org.telegram.p035ui.Components.poll.PollAttachedMedia r5) {
        /*
            r3 = this;
            org.telegram.ui.Components.poll.PollAttachedMediaPack r0 = r3.attachedMedia
            if (r5 == 0) goto L8
            r0.set(r4, r5)
            goto Lb
        L8:
            r0.remove(r4)
        Lb:
            int r4 = r3.mediaIndexToAdapterPosition(r4)
            if (r4 < 0) goto L2d
            org.telegram.ui.Components.RecyclerListView r0 = r3.listView
            androidx.recyclerview.widget.RecyclerView$ViewHolder r0 = r0.findViewHolderForAdapterPosition(r4)
            if (r0 == 0) goto L28
            android.view.View r0 = r0.itemView
            boolean r1 = r0 instanceof org.telegram.p035ui.Cells.PollEditTextCell
            if (r1 == 0) goto L28
            org.telegram.ui.Cells.PollEditTextCell r0 = (org.telegram.p035ui.Cells.PollEditTextCell) r0
            org.telegram.ui.Components.poll.PollAttachButton r4 = r0.attachView
            r0 = 1
            r4.setAttachedMedia(r5, r0)
            goto L2d
        L28:
            org.telegram.ui.Components.ChatAttachAlertPollLayout$ListAdapter r0 = r3.listAdapter
            r0.notifyItemChanged(r4)
        L2d:
            boolean r4 = r5 instanceof org.telegram.p035ui.Components.poll.attached.PollAttachedMediaLink
            if (r4 == 0) goto L44
            org.telegram.ui.Components.poll.WebPageLoader r4 = r3.webPageLoader
            r0 = r5
            org.telegram.ui.Components.poll.attached.PollAttachedMediaLink r0 = (org.telegram.p035ui.Components.poll.attached.PollAttachedMediaLink) r0
            java.lang.String r1 = r0.url
            org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda2 r2 = new org.telegram.ui.Components.ChatAttachAlertPollLayout$$ExternalSyntheticLambda2
            r2.<init>()
            r4.get(r1, r2)
            r4 = 0
            r3.checkPollLinkMedia(r0, r4)
        L44:
            r3.checkDoneButton()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.ChatAttachAlertPollLayout.lambda$openAttachMenuForOptions$23(int, org.telegram.ui.Components.poll.PollAttachedMedia):void");
    }

    public /* synthetic */ void lambda$setAttachedMedia$25(PollAttachedMedia pollAttachedMedia, TLRPC.WebPage webPage, TLObject tLObject) {
        checkPollLinkMedia((PollAttachedMediaLink) pollAttachedMedia, true);
    }

    private static String formatCountriesList(ArrayList<String> arrayList) {
        if (arrayList.isEmpty()) {
            return LocaleController.getString(C2797R.string.SearchCountriesSelect);
        }
        if (arrayList.size() == 1) {
            return LocaleController.getCountryName(arrayList.get(0));
        }
        return LocaleController.formatPluralString("PollV2AllowedCountriesListManyP", arrayList.size(), new Object[0]);
    }

    public class ToggleRow {
        public boolean checked;
        public int row;

        public /* synthetic */ ToggleRow(ChatAttachAlertPollLayout chatAttachAlertPollLayout, ChatAttachAlertPollLayoutIA chatAttachAlertPollLayoutIA) {
            this();
        }

        private ToggleRow() {
        }

        public void setDivider(boolean z) {
            if (this.row < 0) {
                return;
            }
            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = ChatAttachAlertPollLayout.this.listView.findViewHolderForAdapterPosition(this.row);
            if (viewHolderFindViewHolderForAdapterPosition != null) {
                View view = viewHolderFindViewHolderForAdapterPosition.itemView;
                if (view instanceof PollCreateCheckCell) {
                    ((PollCreateCheckCell) view).setDivider(z);
                    return;
                }
            }
            ChatAttachAlertPollLayout.this.listAdapter.notifyItemChanged(this.row);
        }

        public void addRows(int i) {
            ChatAttachAlertPollLayout.this.listAdapter.notifyItemRangeInserted(this.row + 1, i);
        }

        public void removeRows(int i) {
            ChatAttachAlertPollLayout.this.listAdapter.notifyItemRangeRemoved(this.row + 1, i);
        }
    }
}
