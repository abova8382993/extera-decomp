package org.telegram.p035ui.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.LocaleController;
import org.telegram.p035ui.ActionBar.ActionBar;
import org.telegram.p035ui.ActionBar.ActionBarMenuItem;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.ChatAttachAlert;
import org.telegram.p035ui.Components.EmojiView;
import org.telegram.p035ui.Components.RecyclerListView;

/* JADX INFO: loaded from: classes7.dex */
@SuppressLint({"ViewConstructor"})
public class ChatAttachAlertEmojiLayout extends ChatAttachAlert.AttachAlertLayout {
    public int currentItemTop;
    private final EmojiView emojiView;
    private final RecyclerListView gridView;
    private final LinearLayoutManager layoutManager;
    private final boolean sticker;
    private final View tabsView;

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int needsActionBar() {
        return 1;
    }

    public ChatAttachAlertEmojiLayout(ChatAttachAlert chatAttachAlert, Context context, Theme.ResourcesProvider resourcesProvider, boolean z) {
        super(chatAttachAlert, context, resourcesProvider);
        this.currentItemTop = 0;
        this.sticker = z;
        this.occupyNavigationBar = true;
        EmojiView emojiView = new EmojiView(chatAttachAlert.baseFragment, !z, z, false, getContext(), true, null, null, false, resourcesProvider, false, true);
        this.emojiView = emojiView;
        emojiView.shouldLightenBackground = false;
        emojiView.setAllow(!z, z, false, false);
        emojiView.forceHideBackspaceButton();
        emojiView.forceHideSettingsButton();
        emojiView.setDisableStickerEditor();
        addView(emojiView, LayoutHelper.createFrame(-1, -1.0f));
        this.tabsView = emojiView.getTabsForType(!z ? 1 : 0);
        RecyclerListView listViewForType = emojiView.getListViewForType(!z ? 1 : 0);
        this.gridView = listViewForType;
        listViewForType.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.Components.ChatAttachAlertEmojiLayout.1
            public C40811() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                ChatAttachAlertEmojiLayout chatAttachAlertEmojiLayout = ChatAttachAlertEmojiLayout.this;
                chatAttachAlertEmojiLayout.parentAlert.updateLayout(chatAttachAlertEmojiLayout, true, i2);
                ChatAttachAlertEmojiLayout.this.checkTopTabPosition();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                RecyclerListView.Holder holder;
                if (i == 0) {
                    int iM1036dp = AndroidUtilities.m1036dp(13.0f);
                    ActionBarMenuItem actionBarMenuItem = ChatAttachAlertEmojiLayout.this.parentAlert.selectedMenuItem;
                    int iM1036dp2 = iM1036dp + (actionBarMenuItem != null ? AndroidUtilities.m1036dp(actionBarMenuItem.getAlpha() * 26.0f) : 0);
                    int backgroundPaddingTop = ChatAttachAlertEmojiLayout.this.parentAlert.getBackgroundPaddingTop();
                    if (((ChatAttachAlertEmojiLayout.this.parentAlert.scrollOffsetY[0] - backgroundPaddingTop) - iM1036dp2) + backgroundPaddingTop >= ActionBar.getCurrentActionBarHeight() || (holder = (RecyclerListView.Holder) ChatAttachAlertEmojiLayout.this.gridView.findViewHolderForAdapterPosition(0)) == null || holder.itemView.getTop() <= AndroidUtilities.m1036dp(7.0f)) {
                        return;
                    }
                    ChatAttachAlertEmojiLayout.this.gridView.smoothScrollBy(0, holder.itemView.getTop() - AndroidUtilities.m1036dp(7.0f));
                }
            }
        });
        this.layoutManager = (LinearLayoutManager) listViewForType.getLayoutManager();
        checkTopTabPosition();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertEmojiLayout$1 */
    public class C40811 extends RecyclerView.OnScrollListener {
        public C40811() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            ChatAttachAlertEmojiLayout chatAttachAlertEmojiLayout = ChatAttachAlertEmojiLayout.this;
            chatAttachAlertEmojiLayout.parentAlert.updateLayout(chatAttachAlertEmojiLayout, true, i2);
            ChatAttachAlertEmojiLayout.this.checkTopTabPosition();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            RecyclerListView.Holder holder;
            if (i == 0) {
                int iM1036dp = AndroidUtilities.m1036dp(13.0f);
                ActionBarMenuItem actionBarMenuItem = ChatAttachAlertEmojiLayout.this.parentAlert.selectedMenuItem;
                int iM1036dp2 = iM1036dp + (actionBarMenuItem != null ? AndroidUtilities.m1036dp(actionBarMenuItem.getAlpha() * 26.0f) : 0);
                int backgroundPaddingTop = ChatAttachAlertEmojiLayout.this.parentAlert.getBackgroundPaddingTop();
                if (((ChatAttachAlertEmojiLayout.this.parentAlert.scrollOffsetY[0] - backgroundPaddingTop) - iM1036dp2) + backgroundPaddingTop >= ActionBar.getCurrentActionBarHeight() || (holder = (RecyclerListView.Holder) ChatAttachAlertEmojiLayout.this.gridView.findViewHolderForAdapterPosition(0)) == null || holder.itemView.getTop() <= AndroidUtilities.m1036dp(7.0f)) {
                    return;
                }
                ChatAttachAlertEmojiLayout.this.gridView.smoothScrollBy(0, holder.itemView.getTop() - AndroidUtilities.m1036dp(7.0f));
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        checkTopTabPosition();
    }

    public void setDelegate(EmojiView.EmojiViewDelegate emojiViewDelegate) {
        this.emojiView.setDelegate(emojiViewDelegate);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void scrollToTop() {
        this.gridView.smoothScrollToPosition(0);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getListTopPadding() {
        return this.gridView.getPaddingTop();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getCurrentItemTop() {
        int childCount = this.gridView.getChildCount();
        RecyclerListView recyclerListView = this.gridView;
        if (childCount <= 0) {
            int paddingTop = recyclerListView.getPaddingTop();
            this.currentItemTop = paddingTop;
            recyclerListView.setTopGlowOffset(paddingTop);
            return Integer.MAX_VALUE;
        }
        View childAt = recyclerListView.getChildAt(0);
        RecyclerListView.Holder holder = (RecyclerListView.Holder) this.gridView.findContainingViewHolder(childAt);
        int top = childAt.getTop() - AndroidUtilities.m1036dp(36.0f);
        int iM1036dp = AndroidUtilities.m1036dp(7.0f);
        if (top < AndroidUtilities.m1036dp(7.0f) || holder == null || holder.getAdapterPosition() != 0) {
            top = iM1036dp;
        }
        this.gridView.setTopGlowOffset(top);
        this.currentItemTop = top;
        return top;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getFirstOffset() {
        return getListTopPadding() + AndroidUtilities.m1036dp(56.0f);
    }

    @Override // android.view.View
    public void setTranslationY(float f) {
        super.setTranslationY(f);
        this.parentAlert.getSheetContainer().invalidate();
        invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0020  */
    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onPreMeasure(int r3, int r4) {
        /*
            r2 = this;
            android.view.ViewGroup$LayoutParams r3 = r2.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r3 = (android.widget.FrameLayout.LayoutParams) r3
            int r0 = org.telegram.p035ui.ActionBar.ActionBar.getCurrentActionBarHeight()
            r3.topMargin = r0
            boolean r3 = org.telegram.messenger.AndroidUtilities.isTablet()
            if (r3 != 0) goto L20
            android.graphics.Point r3 = org.telegram.messenger.AndroidUtilities.displaySize
            int r0 = r3.x
            int r3 = r3.y
            if (r0 <= r3) goto L20
            float r3 = (float) r4
            r4 = 1080033280(0x40600000, float:3.5)
            float r3 = r3 / r4
            int r3 = (int) r3
            goto L24
        L20:
            int r4 = r4 / 5
            int r3 = r4 * 2
        L24:
            r4 = 1112539136(0x42500000, float:52.0)
            int r4 = org.telegram.messenger.AndroidUtilities.m1036dp(r4)
            int r3 = r3 - r4
            if (r3 >= 0) goto L2e
            r3 = 0
        L2e:
            r4 = 1108344832(0x42100000, float:36.0)
            int r4 = org.telegram.messenger.AndroidUtilities.m1036dp(r4)
            int r3 = r3 + r4
            org.telegram.ui.Components.RecyclerListView r4 = r2.gridView
            int r4 = r4.getPaddingTop()
            if (r4 == r3) goto L52
            org.telegram.ui.Components.RecyclerListView r2 = r2.gridView
            r4 = 1086324736(0x40c00000, float:6.0)
            int r0 = org.telegram.messenger.AndroidUtilities.m1036dp(r4)
            int r4 = org.telegram.messenger.AndroidUtilities.m1036dp(r4)
            r1 = 1111490560(0x42400000, float:48.0)
            int r1 = org.telegram.messenger.AndroidUtilities.m1036dp(r1)
            r2.setPadding(r0, r3, r4, r1)
        L52:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.ChatAttachAlertEmojiLayout.onPreMeasure(int, int):void");
    }

    public void checkTopTabPosition() {
        this.tabsView.setTranslationY(Math.max(0, getCurrentItemTop()));
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onShow(ChatAttachAlert.AttachAlertLayout attachAlertLayout) {
        try {
            this.parentAlert.actionBar.getTitleTextView().setBuildFullLayout(true);
        } catch (Exception unused) {
        }
        this.parentAlert.actionBar.setTitle(LocaleController.getString(this.sticker ? C2797R.string.SelectSticker : C2797R.string.SelectEmoji));
        this.layoutManager.scrollToPositionWithOffset(0, 0);
    }
}
