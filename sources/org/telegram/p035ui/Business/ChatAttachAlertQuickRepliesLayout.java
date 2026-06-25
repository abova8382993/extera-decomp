package org.telegram.p035ui.Business;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.util.Consumer;
import java.util.ArrayList;
import java.util.HashSet;
import me.vkryl.android.animator.BoolAnimator;
import me.vkryl.android.animator.FactorAnimator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.utils.TextWatcherImpl;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.ActionBar.ThemeDescription;
import org.telegram.p035ui.Business.QuickRepliesActivity;
import org.telegram.p035ui.Business.QuickRepliesController;
import org.telegram.p035ui.Components.AlertsCreator;
import org.telegram.p035ui.Components.ChatAttachAlert;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.EmptyTextProgressView;
import org.telegram.p035ui.Components.FillLastLinearLayoutManager;
import org.telegram.p035ui.Components.FragmentSearchField;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.Premium.PremiumFeatureBottomSheet;
import org.telegram.p035ui.Components.RecyclerListView;
import org.telegram.p035ui.Components.blur3.BlurredBackgroundDrawableViewFactory;
import org.telegram.p035ui.Components.blur3.drawable.color.impl.BlurredBackgroundProviderImpl;

/* JADX INFO: loaded from: classes6.dex */
@SuppressLint({"ViewConstructor"})
public class ChatAttachAlertQuickRepliesLayout extends ChatAttachAlert.AttachAlertLayout implements NotificationCenter.NotificationCenterDelegate, FactorAnimator.Target {
    private final BoolAnimator animatorFadeVisible;
    private final EmptyTextProgressView emptyView;
    private final View fadeView;
    private final FrameLayout frameLayout;
    private final FillLastLinearLayoutManager layoutManager;
    private final ShareAdapter listAdapter;
    private final RecyclerListView listView;
    private final ShareSearchAdapter searchAdapter;
    private final FragmentSearchField searchField;
    private final HashSet<Integer> selectedReplies;

    public static class UserCell extends FrameLayout {
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getSelectedItemsCount() {
        return 0;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onDestroy() {
    }

    @Override // me.vkryl.android.animator.FactorAnimator.Target
    public void onFactorChanged(int i, float f, float f2, FactorAnimator factorAnimator) {
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public boolean sendSelectedItems(boolean z, int i, int i2, long j, boolean z2) {
        return false;
    }

    public ChatAttachAlertQuickRepliesLayout(ChatAttachAlert chatAttachAlert, Context context, Theme.ResourcesProvider resourcesProvider) {
        super(chatAttachAlert, context, resourcesProvider);
        this.animatorFadeVisible = new BoolAnimator(0, this, CubicBezierInterpolator.EASE_OUT_QUINT, 380L);
        this.selectedReplies = new HashSet<>();
        this.searchAdapter = new ShareSearchAdapter(context);
        ChatAttachAlert.SearchFadeView searchFadeView = new ChatAttachAlert.SearchFadeView(context, Theme.key_windowBackgroundWhite, resourcesProvider);
        this.fadeView = searchFadeView;
        searchFadeView.setVisibility(4);
        FrameLayout frameLayout = new FrameLayout(context);
        this.frameLayout = frameLayout;
        ChatAttachAlert.AttachSearchField attachSearchField = new ChatAttachAlert.AttachSearchField(context, this.parentAlert, resourcesProvider);
        this.searchField = attachSearchField;
        attachSearchField.setPadding(AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(4.0f));
        attachSearchField.editText.addTextChangedListener(new TextWatcherImpl() { // from class: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout.1
            public C31671() {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                String string = editable.toString();
                boolean zIsEmpty = string.isEmpty();
                ChatAttachAlertQuickRepliesLayout chatAttachAlertQuickRepliesLayout = ChatAttachAlertQuickRepliesLayout.this;
                if (!zIsEmpty) {
                    if (chatAttachAlertQuickRepliesLayout.emptyView != null) {
                        ChatAttachAlertQuickRepliesLayout.this.emptyView.setText(LocaleController.getString(C2797R.string.NoResult));
                    }
                } else if (chatAttachAlertQuickRepliesLayout.listView.getAdapter() != ChatAttachAlertQuickRepliesLayout.this.listAdapter) {
                    int currentTop = ChatAttachAlertQuickRepliesLayout.this.getCurrentTop();
                    ChatAttachAlertQuickRepliesLayout.this.emptyView.showTextView();
                    ChatAttachAlertQuickRepliesLayout.this.listView.setAdapter(ChatAttachAlertQuickRepliesLayout.this.listAdapter);
                    ChatAttachAlertQuickRepliesLayout.this.listAdapter.notifyDataSetChanged();
                    if (currentTop > 0) {
                        ChatAttachAlertQuickRepliesLayout.this.layoutManager.scrollToPositionWithOffset(0, -currentTop);
                    }
                }
                if (ChatAttachAlertQuickRepliesLayout.this.searchAdapter != null) {
                    ChatAttachAlertQuickRepliesLayout.this.searchAdapter.search(string);
                }
            }
        });
        attachSearchField.editText.setHint(LocaleController.getString(C2797R.string.BusinessRepliesSearch));
        frameLayout.addView(searchFadeView, LayoutHelper.createFrameMatchParent());
        FrameLayout.LayoutParams layoutParamsCreateFrame = LayoutHelper.createFrame(-1, 48.0f, 51, 7.0f, 8.0f, 7.0f, 4.0f);
        ((ViewGroup.MarginLayoutParams) layoutParamsCreateFrame).topMargin += AndroidUtilities.statusBarHeight;
        frameLayout.addView(attachSearchField, layoutParamsCreateFrame);
        EmptyTextProgressView emptyTextProgressView = new EmptyTextProgressView(context, null, resourcesProvider);
        this.emptyView = emptyTextProgressView;
        emptyTextProgressView.showTextView();
        addView(emptyTextProgressView, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 52.0f, 0.0f, 0.0f));
        C31682 c31682 = new RecyclerListView(context, resourcesProvider) { // from class: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout.2
            public C31682(Context context2, Theme.ResourcesProvider resourcesProvider2) {
                super(context2, resourcesProvider2);
            }

            @Override // org.telegram.p035ui.Components.RecyclerListView
            public boolean allowSelectChildAtPosition(float f, float f2) {
                return f2 >= ((float) ((((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertQuickRepliesLayout.this).parentAlert.scrollOffsetY[0] + AndroidUtilities.m1036dp(30.0f)) + (!((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertQuickRepliesLayout.this).parentAlert.inBubbleMode ? AndroidUtilities.statusBarHeight : 0)));
            }

            @Override // androidx.recyclerview.widget.RecyclerView
            public void onScrolled(int i, int i2) {
                super.onScrolled(i, i2);
            }
        };
        this.listView = c31682;
        c31682.setSections();
        this.iBlur3Capture = c31682;
        this.iBlur3CaptureView = c31682;
        this.occupyStatusBar = true;
        this.occupyNavigationBar = true;
        NotificationCenter.getInstance(UserConfig.selectedAccount).listenGlobal(c31682, NotificationCenter.emojiLoaded, new Utilities.Callback() { // from class: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout$$ExternalSyntheticLambda1
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$new$1((Object[]) obj);
            }
        });
        c31682.setClipToPadding(false);
        C31693 c31693 = new FillLastLinearLayoutManager(getContext(), 1, false, AndroidUtilities.m1036dp(9.0f), c31682) { // from class: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout.3
            public C31693(Context context2, int i, boolean z, int i2, RecyclerView c316822) {
                super(context2, i, z, i2, c316822);
            }

            /* JADX INFO: renamed from: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout$3$1 */
            public class AnonymousClass1 extends LinearSmoothScroller {
                public AnonymousClass1(Context context) {
                    super(context);
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public int calculateDyToMakeVisible(View view, int i) {
                    return super.calculateDyToMakeVisible(view, i) - ((ChatAttachAlertQuickRepliesLayout.this.listView.getPaddingTop() - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(8.0f));
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public int calculateTimeForDeceleration(int i) {
                    return super.calculateTimeForDeceleration(i) * 2;
                }
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
                AnonymousClass1 anonymousClass1 = new LinearSmoothScroller(recyclerView.getContext()) { // from class: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout.3.1
                    public AnonymousClass1(Context context2) {
                        super(context2);
                    }

                    @Override // androidx.recyclerview.widget.LinearSmoothScroller
                    public int calculateDyToMakeVisible(View view, int i2) {
                        return super.calculateDyToMakeVisible(view, i2) - ((ChatAttachAlertQuickRepliesLayout.this.listView.getPaddingTop() - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(8.0f));
                    }

                    @Override // androidx.recyclerview.widget.LinearSmoothScroller
                    public int calculateTimeForDeceleration(int i2) {
                        return super.calculateTimeForDeceleration(i2) * 2;
                    }
                };
                anonymousClass1.setTargetPosition(i);
                startSmoothScroll(anonymousClass1);
            }
        };
        this.layoutManager = c31693;
        c316822.setLayoutManager(c31693);
        c31693.setBind(false);
        c316822.setHorizontalScrollBarEnabled(false);
        c316822.setVerticalScrollBarEnabled(false);
        c316822.setClipToPadding(false);
        addView(c316822, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
        ShareAdapter shareAdapter = new ShareAdapter(context2);
        this.listAdapter = shareAdapter;
        c316822.setAdapter(shareAdapter);
        c316822.setGlowColor(getThemedColor(Theme.key_dialogScrollGlow));
        c316822.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout$$ExternalSyntheticLambda2
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i) {
                this.f$0.lambda$new$3(view, i);
            }
        });
        c316822.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout.4
            public C31704() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                ((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertQuickRepliesLayout.this).parentAlert.updateLayout(ChatAttachAlertQuickRepliesLayout.this, true, i2);
                ChatAttachAlertQuickRepliesLayout.this.updateEmptyViewPosition();
            }
        });
        FrameLayout.LayoutParams layoutParamsCreateFrame2 = LayoutHelper.createFrame(-1, 60, 51);
        ((ViewGroup.MarginLayoutParams) layoutParamsCreateFrame2).height += AndroidUtilities.statusBarHeight;
        addView(frameLayout, layoutParamsCreateFrame2);
        updateEmptyView();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout$1 */
    public class C31671 implements TextWatcherImpl {
        public C31671() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            String string = editable.toString();
            boolean zIsEmpty = string.isEmpty();
            ChatAttachAlertQuickRepliesLayout chatAttachAlertQuickRepliesLayout = ChatAttachAlertQuickRepliesLayout.this;
            if (!zIsEmpty) {
                if (chatAttachAlertQuickRepliesLayout.emptyView != null) {
                    ChatAttachAlertQuickRepliesLayout.this.emptyView.setText(LocaleController.getString(C2797R.string.NoResult));
                }
            } else if (chatAttachAlertQuickRepliesLayout.listView.getAdapter() != ChatAttachAlertQuickRepliesLayout.this.listAdapter) {
                int currentTop = ChatAttachAlertQuickRepliesLayout.this.getCurrentTop();
                ChatAttachAlertQuickRepliesLayout.this.emptyView.showTextView();
                ChatAttachAlertQuickRepliesLayout.this.listView.setAdapter(ChatAttachAlertQuickRepliesLayout.this.listAdapter);
                ChatAttachAlertQuickRepliesLayout.this.listAdapter.notifyDataSetChanged();
                if (currentTop > 0) {
                    ChatAttachAlertQuickRepliesLayout.this.layoutManager.scrollToPositionWithOffset(0, -currentTop);
                }
            }
            if (ChatAttachAlertQuickRepliesLayout.this.searchAdapter != null) {
                ChatAttachAlertQuickRepliesLayout.this.searchAdapter.search(string);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout$2 */
    public class C31682 extends RecyclerListView {
        public C31682(Context context2, Theme.ResourcesProvider resourcesProvider2) {
            super(context2, resourcesProvider2);
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView
        public boolean allowSelectChildAtPosition(float f, float f2) {
            return f2 >= ((float) ((((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertQuickRepliesLayout.this).parentAlert.scrollOffsetY[0] + AndroidUtilities.m1036dp(30.0f)) + (!((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertQuickRepliesLayout.this).parentAlert.inBubbleMode ? AndroidUtilities.statusBarHeight : 0)));
        }

        @Override // androidx.recyclerview.widget.RecyclerView
        public void onScrolled(int i, int i2) {
            super.onScrolled(i, i2);
        }
    }

    public /* synthetic */ void lambda$new$1(Object[] objArr) {
        AndroidUtilities.forEachViews((RecyclerView) this.listView, (Consumer<View>) new Consumer() { // from class: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout$$ExternalSyntheticLambda3
            @Override // com.google.android.exoplayer2.util.Consumer
            public final void accept(Object obj) {
                ChatAttachAlertQuickRepliesLayout.m7625$r8$lambda$LvFiaOEAzJTrFmm8PU1c7yRW10((View) obj);
            }
        });
    }

    /* JADX INFO: renamed from: $r8$lambda$LvFiaOEAzJTrFmm8PU1c7y-RW10 */
    public static /* synthetic */ void m7625$r8$lambda$LvFiaOEAzJTrFmm8PU1c7yRW10(View view) {
        if (view instanceof QuickRepliesActivity.QuickReplyView) {
            ((QuickRepliesActivity.QuickReplyView) view).invalidateEmojis();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout$3 */
    public class C31693 extends FillLastLinearLayoutManager {
        public C31693(Context context2, int i, boolean z, int i2, RecyclerView c316822) {
            super(context2, i, z, i2, c316822);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout$3$1 */
        public class AnonymousClass1 extends LinearSmoothScroller {
            public AnonymousClass1(Context context2) {
                super(context2);
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateDyToMakeVisible(View view, int i2) {
                return super.calculateDyToMakeVisible(view, i2) - ((ChatAttachAlertQuickRepliesLayout.this.listView.getPaddingTop() - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(8.0f));
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateTimeForDeceleration(int i2) {
                return super.calculateTimeForDeceleration(i2) * 2;
            }
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
            AnonymousClass1 anonymousClass1 = new LinearSmoothScroller(recyclerView.getContext()) { // from class: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout.3.1
                public AnonymousClass1(Context context2) {
                    super(context2);
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public int calculateDyToMakeVisible(View view, int i2) {
                    return super.calculateDyToMakeVisible(view, i2) - ((ChatAttachAlertQuickRepliesLayout.this.listView.getPaddingTop() - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(8.0f));
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public int calculateTimeForDeceleration(int i2) {
                    return super.calculateTimeForDeceleration(i2) * 2;
                }
            };
            anonymousClass1.setTargetPosition(i);
            startSmoothScroll(anonymousClass1);
        }
    }

    public /* synthetic */ void lambda$new$3(View view, int i) {
        final Object item;
        RecyclerView.Adapter adapter = this.listView.getAdapter();
        ShareSearchAdapter shareSearchAdapter = this.searchAdapter;
        if (adapter == shareSearchAdapter) {
            item = shareSearchAdapter.getItem(i);
        } else {
            int sectionForPosition = this.listAdapter.getSectionForPosition(i);
            int positionInSectionForPosition = this.listAdapter.getPositionInSectionForPosition(i);
            if (positionInSectionForPosition < 0 || sectionForPosition < 0) {
                return;
            } else {
                item = this.listAdapter.getItem(sectionForPosition, positionInSectionForPosition);
            }
        }
        if (item instanceof QuickRepliesController.QuickReply) {
            boolean zIsPremium = UserConfig.getInstance(this.parentAlert.currentAccount).isPremium();
            ChatAttachAlert chatAttachAlert = this.parentAlert;
            if (!zIsPremium) {
                if (chatAttachAlert.baseFragment != null) {
                    new PremiumFeatureBottomSheet(this.parentAlert.baseFragment, getContext(), this.parentAlert.currentAccount, true, 31, false, null).show();
                    return;
                }
                return;
            }
            AlertsCreator.ensurePaidMessageConfirmation(chatAttachAlert.currentAccount, chatAttachAlert.getDialogId(), ((QuickRepliesController.QuickReply) item).getMessagesCount(), new Utilities.Callback() { // from class: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout$$ExternalSyntheticLambda4
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$new$2(item, (Long) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$new$2(Object obj, Long l) {
        QuickRepliesController.getInstance(UserConfig.selectedAccount).sendQuickReplyTo(this.parentAlert.getDialogId(), (QuickRepliesController.QuickReply) obj);
        this.parentAlert.lambda$new$0();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout$4 */
    public class C31704 extends RecyclerView.OnScrollListener {
        public C31704() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            ((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertQuickRepliesLayout.this).parentAlert.updateLayout(ChatAttachAlertQuickRepliesLayout.this, true, i2);
            ChatAttachAlertQuickRepliesLayout.this.updateEmptyViewPosition();
        }
    }

    public void setupBlurredSearchField(BlurredBackgroundDrawableViewFactory blurredBackgroundDrawableViewFactory) {
        FragmentSearchField fragmentSearchField = this.searchField;
        if (fragmentSearchField != null) {
            fragmentSearchField.setupBlurredBackground(blurredBackgroundDrawableViewFactory.create(fragmentSearchField, BlurredBackgroundProviderImpl.attachMenuSearch(this.resourcesProvider)));
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void scrollToTop() {
        this.listView.smoothScrollToPosition(0);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getCurrentItemTop() {
        if (this.listView.getChildCount() <= 0) {
            return Integer.MAX_VALUE;
        }
        View childAt = this.listView.getChildAt(0);
        RecyclerListView.Holder holder = (RecyclerListView.Holder) this.listView.findContainingViewHolder(childAt);
        int top = (childAt.getTop() - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(8.0f);
        int i = (top <= 0 || holder == null || holder.getAdapterPosition() != 0) ? 0 : top;
        if (top >= 0 && holder != null && holder.getAdapterPosition() == 0) {
            this.animatorFadeVisible.setValue(false, true);
        } else {
            this.animatorFadeVisible.setValue(true, true);
            top = i;
        }
        this.frameLayout.setTranslationY(top);
        return top + AndroidUtilities.m1036dp(12.0f);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getFirstOffset() {
        return getListTopPadding() + AndroidUtilities.m1036dp(4.0f);
    }

    @Override // android.view.View
    public void setTranslationY(float f) {
        super.setTranslationY(f);
        this.parentAlert.getSheetContainer().invalidate();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getListTopPadding() {
        return this.listView.getPaddingTop();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0031  */
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
            if (r3 <= r0) goto L1d
            r3 = 1090519040(0x41000000, float:8.0)
            int r3 = org.telegram.messenger.AndroidUtilities.m1036dp(r3)
            org.telegram.ui.Components.ChatAttachAlert r4 = r2.parentAlert
            r4.setAllowNestedScroll(r1)
            goto L3c
        L1d:
            boolean r3 = org.telegram.messenger.AndroidUtilities.isTablet()
            if (r3 != 0) goto L31
            android.graphics.Point r3 = org.telegram.messenger.AndroidUtilities.displaySize
            int r0 = r3.x
            int r3 = r3.y
            if (r0 <= r3) goto L31
            float r3 = (float) r4
            r4 = 1080033280(0x40600000, float:3.5)
            float r3 = r3 / r4
            int r3 = (int) r3
            goto L36
        L31:
            int r4 = r4 / 5
            int r4 = r4 * 2
            r3 = r4
        L36:
            org.telegram.ui.Components.ChatAttachAlert r4 = r2.parentAlert
            r0 = 1
            r4.setAllowNestedScroll(r0)
        L3c:
            int r4 = org.telegram.messenger.AndroidUtilities.statusBarHeight
            int r3 = r3 + r4
            org.telegram.ui.Components.RecyclerListView r4 = r2.listView
            int r2 = r2.listPaddingBottom
            r4.setPaddingWithoutRequestLayout(r1, r3, r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Business.ChatAttachAlertQuickRepliesLayout.onPreMeasure(int, int):void");
    }

    public int getCurrentTop() {
        if (this.listView.getChildCount() == 0) {
            return -1000;
        }
        int top = 0;
        View childAt = this.listView.getChildAt(0);
        RecyclerListView.Holder holder = (RecyclerListView.Holder) this.listView.findContainingViewHolder(childAt);
        if (holder == null) {
            return -1000;
        }
        int paddingTop = this.listView.getPaddingTop();
        if (holder.getAdapterPosition() == 0 && childAt.getTop() >= 0) {
            top = childAt.getTop();
        }
        return paddingTop - top;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onShow(ChatAttachAlert.AttachAlertLayout attachAlertLayout) {
        this.layoutManager.scrollToPositionWithOffset(0, 0);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateEmptyViewPosition();
    }

    public void updateEmptyViewPosition() {
        View childAt;
        if (this.emptyView.getVisibility() == 0 && (childAt = this.listView.getChildAt(0)) != null) {
            this.emptyView.setTranslationY(((r1.getMeasuredHeight() - getMeasuredHeight()) + childAt.getTop()) / 2);
        }
    }

    public void updateEmptyView() {
        this.emptyView.setVisibility(this.listView.getAdapter().getItemCount() == 2 ? 0 : 8);
        updateEmptyViewPosition();
    }

    public class ShareAdapter extends RecyclerListView.SectionsAdapter {
        private int currentAccount;
        private Context mContext;
        private ArrayList<QuickRepliesController.QuickReply> replies;

        @Override // org.telegram.ui.Components.RecyclerListView.FastScrollAdapter
        public String getLetter(int i) {
            return null;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
        public int getSectionCount() {
            return 3;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
        public View getSectionHeaderView(int i, View view) {
            return null;
        }

        public ShareAdapter(Context context) {
            ArrayList<QuickRepliesController.QuickReply> arrayList = new ArrayList<>();
            this.replies = arrayList;
            int i = UserConfig.selectedAccount;
            this.currentAccount = i;
            this.mContext = context;
            arrayList.addAll(QuickRepliesController.getInstance(i).getFilteredReplies());
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
        public Object getItem(int i, int i2) {
            if (i != 0 && i2 >= 0 && i2 < this.replies.size()) {
                return this.replies.get(i2);
            }
            return null;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder, int i, int i2) {
            return (i == 0 || i == getSectionCount() - 1 || i2 >= this.replies.size()) ? false : true;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
        public int getCountForSection(int i) {
            if (i == 0 || i == getSectionCount() - 1) {
                return 1;
            }
            return this.replies.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View quickReplyView;
            if (i == 0) {
                quickReplyView = new QuickRepliesActivity.QuickReplyView(this.mContext, false, ((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertQuickRepliesLayout.this).resourcesProvider);
            } else if (i == 1) {
                quickReplyView = new View(this.mContext);
                quickReplyView.setLayoutParams(new RecyclerView.LayoutParams(-1, AndroidUtilities.m1036dp(56.0f)));
                quickReplyView.setTag(-33024);
            } else {
                quickReplyView = new View(this.mContext);
                quickReplyView.setTag(-33024);
            }
            return new RecyclerListView.Holder(quickReplyView);
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
        public void onBindViewHolder(int i, int i2, RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() == 0) {
                QuickRepliesActivity.QuickReplyView quickReplyView = (QuickRepliesActivity.QuickReplyView) viewHolder.itemView;
                Object item = getItem(i, i2);
                boolean z = true;
                if (i == getSectionCount() - 2 && i2 == getCountForSection(i) - 1) {
                    z = false;
                }
                if (item instanceof QuickRepliesController.QuickReply) {
                    QuickRepliesController.QuickReply quickReply = (QuickRepliesController.QuickReply) item;
                    quickReplyView.set(quickReply, null, z);
                    quickReplyView.setChecked(ChatAttachAlertQuickRepliesLayout.this.selectedReplies.contains(Integer.valueOf(quickReply.f1489id)), false);
                }
            }
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
        public int getItemViewType(int i, int i2) {
            if (i == 0) {
                return 1;
            }
            return i == getSectionCount() - 1 ? 2 : 0;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.FastScrollAdapter
        public void getPositionForScrollProgress(RecyclerListView recyclerListView, float f, int[] iArr) {
            iArr[0] = 0;
            iArr[1] = 0;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            this.replies.clear();
            this.replies.addAll(QuickRepliesController.getInstance(this.currentAccount).getFilteredReplies());
            super.notifyDataSetChanged();
            ChatAttachAlertQuickRepliesLayout.this.updateEmptyView();
        }
    }

    public class ShareSearchAdapter extends RecyclerListView.SelectionAdapter {
        public String lastQuery;
        private Context mContext;
        private ArrayList<QuickRepliesController.QuickReply> searchResult = new ArrayList<>();
        private Runnable searchRunnable;

        public ShareSearchAdapter(Context context) {
            this.mContext = context;
        }

        public void search(String str) {
            if (this.searchRunnable != null) {
                Utilities.searchQueue.cancelRunnable(this.searchRunnable);
                this.searchRunnable = null;
            }
            this.searchResult.clear();
            this.lastQuery = str;
            if (str != null) {
                String strTranslitSafe = AndroidUtilities.translitSafe(str);
                if (strTranslitSafe.startsWith("/")) {
                    strTranslitSafe = strTranslitSafe.substring(1);
                }
                QuickRepliesController quickRepliesController = QuickRepliesController.getInstance(UserConfig.selectedAccount);
                for (int i = 0; i < quickRepliesController.replies.size(); i++) {
                    QuickRepliesController.QuickReply quickReply = quickRepliesController.replies.get(i);
                    if (!quickReply.isSpecial()) {
                        String strTranslitSafe2 = AndroidUtilities.translitSafe(quickReply.name);
                        if (strTranslitSafe2.startsWith(strTranslitSafe) || strTranslitSafe2.contains(" ".concat(strTranslitSafe))) {
                            this.searchResult.add(quickReply);
                        }
                    }
                }
            }
            if (ChatAttachAlertQuickRepliesLayout.this.listView.getAdapter() != ChatAttachAlertQuickRepliesLayout.this.searchAdapter) {
                ChatAttachAlertQuickRepliesLayout.this.listView.setAdapter(ChatAttachAlertQuickRepliesLayout.this.searchAdapter);
            }
            notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.searchResult.size() + 2;
        }

        public Object getItem(int i) {
            int i2 = i - 1;
            if (i2 < 0 || i2 >= this.searchResult.size()) {
                return null;
            }
            return this.searchResult.get(i2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View quickReplyView;
            if (i == 0) {
                quickReplyView = new QuickRepliesActivity.QuickReplyView(this.mContext, false, ((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertQuickRepliesLayout.this).resourcesProvider);
            } else if (i == 1) {
                quickReplyView = new View(this.mContext);
                quickReplyView.setLayoutParams(new RecyclerView.LayoutParams(-1, AndroidUtilities.m1036dp(56.0f)));
                quickReplyView.setTag(-33024);
            } else {
                quickReplyView = new View(this.mContext);
                quickReplyView.setTag(-33024);
            }
            return new RecyclerListView.Holder(quickReplyView);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder.getItemViewType() == 0) {
                QuickRepliesActivity.QuickReplyView quickReplyView = (QuickRepliesActivity.QuickReplyView) viewHolder.itemView;
                boolean z = i != getItemCount() + (-2);
                Object item = getItem(i);
                if (item instanceof QuickRepliesController.QuickReply) {
                    QuickRepliesController.QuickReply quickReply = (QuickRepliesController.QuickReply) item;
                    quickReplyView.set(quickReply, this.lastQuery, z);
                    quickReplyView.setChecked(ChatAttachAlertQuickRepliesLayout.this.selectedReplies.contains(Integer.valueOf(quickReply.f1489id)), false);
                }
            }
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == 0) {
                return 1;
            }
            return i == getItemCount() - 1 ? 2 : 0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            ChatAttachAlertQuickRepliesLayout.this.updateEmptyView();
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ThemeDescription.ThemeDescriptionDelegate themeDescriptionDelegate = new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.Business.ChatAttachAlertQuickRepliesLayout$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                this.f$0.lambda$getThemeDescriptions$4();
            }
        };
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        arrayList.add(new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder));
        arrayList.add(new ThemeDescription(this.emptyView, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_dialogScrollGlow));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));
        int i = Theme.key_dialogTextGray2;
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"nameTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusTextView"}, (Paint[]) null, (Drawable[]) null, themeDescriptionDelegate, i));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, Theme.avatarDrawables, null, Theme.key_avatar_text));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundRed));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundOrange));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundViolet));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundGreen));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundCyan));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundBlue));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundPink));
        return arrayList;
    }

    public /* synthetic */ void lambda$getThemeDescriptions$4() {
        RecyclerListView recyclerListView = this.listView;
        if (recyclerListView != null) {
            int childCount = recyclerListView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.listView.getChildAt(i);
            }
        }
    }
}
