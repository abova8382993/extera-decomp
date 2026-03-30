package org.telegram.p026ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2702R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.p026ui.ActionBar.ActionBar;
import org.telegram.p026ui.ActionBar.BaseFragment;
import org.telegram.p026ui.ActionBar.Theme;
import org.telegram.p026ui.ActionBar.ThemeDescription;
import org.telegram.p026ui.Cells.AvailableReactionCell;
import org.telegram.p026ui.Cells.TextInfoPrivacyCell;
import org.telegram.p026ui.Cells.ThemePreviewMessagesCell;
import org.telegram.p026ui.Components.AnimatedEmojiDrawable;
import org.telegram.p026ui.Components.LayoutHelper;
import org.telegram.p026ui.Components.Premium.PremiumFeatureBottomSheet;
import org.telegram.p026ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p026ui.Components.RecyclerListView;
import org.telegram.p026ui.Components.SimpleThemeDescription;
import org.telegram.p026ui.SelectAnimatedEmojiDialog;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p025tl.TL_stars;

/* JADX INFO: loaded from: classes6.dex */
public class ReactionsDoubleTapManageActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    private LinearLayout contentView;
    int infoRow;
    private RecyclerView.Adapter listAdapter;
    private RecyclerListView listView;
    int premiumReactionRow;
    int previewRow;
    int reactionsStartRow = -1;
    int rowCount;
    private SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialog;

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        getNotificationCenter().addObserver(this, NotificationCenter.reactionsDidLoad);
        getNotificationCenter().addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        return super.onFragmentCreate();
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.actionBar.setTitle(LocaleController.getString(C2702R.string.Reactions));
        this.actionBar.setBackButtonImage(C2702R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.ReactionsDoubleTapManageActivity.1
            C63431() {
            }

            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    ReactionsDoubleTapManageActivity.this.finishFragment();
                }
            }
        });
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        RecyclerListView recyclerListView = new RecyclerListView(context);
        this.listView = recyclerListView;
        ((DefaultItemAnimator) recyclerListView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.listView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerListView recyclerListView2 = this.listView;
        C63442 c63442 = new RecyclerListView.SelectionAdapter() { // from class: org.telegram.ui.ReactionsDoubleTapManageActivity.2
            final /* synthetic */ Context val$context;

            C63442(Context context2) {
                context = context2;
            }

            @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
            public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
                return viewHolder.getItemViewType() == 3 || viewHolder.getItemViewType() == 2;
            }

            /* JADX INFO: renamed from: org.telegram.ui.ReactionsDoubleTapManageActivity$2$1 */
            class AnonymousClass1 extends View {
                AnonymousClass1(Context context) {
                    super(context);
                }

                @Override // android.view.View
                protected void onMeasure(int i, int i2) {
                    super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(16.0f), TLObject.FLAG_30));
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View availableReactionCell;
                if (i == 0) {
                    ThemePreviewMessagesCell themePreviewMessagesCell = new ThemePreviewMessagesCell(context, ((BaseFragment) ReactionsDoubleTapManageActivity.this).parentLayout, 2);
                    themePreviewMessagesCell.setImportantForAccessibility(4);
                    themePreviewMessagesCell.fragment = ReactionsDoubleTapManageActivity.this;
                    availableReactionCell = themePreviewMessagesCell;
                } else if (i == 2) {
                    TextInfoPrivacyCell textInfoPrivacyCell = new TextInfoPrivacyCell(context);
                    textInfoPrivacyCell.setText(LocaleController.getString(C2702R.string.DoubleTapPreviewRational));
                    textInfoPrivacyCell.setBackground(Theme.getThemedDrawableByKey(context, C2702R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    availableReactionCell = textInfoPrivacyCell;
                } else if (i == 3) {
                    SetDefaultReactionCell setDefaultReactionCell = ReactionsDoubleTapManageActivity.this.new SetDefaultReactionCell(context);
                    setDefaultReactionCell.update(false);
                    availableReactionCell = setDefaultReactionCell;
                } else if (i == 4) {
                    AnonymousClass1 anonymousClass1 = new View(context) { // from class: org.telegram.ui.ReactionsDoubleTapManageActivity.2.1
                        AnonymousClass1(Context context2) {
                            super(context2);
                        }

                        @Override // android.view.View
                        protected void onMeasure(int i2, int i22) {
                            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(16.0f), TLObject.FLAG_30));
                        }
                    };
                    anonymousClass1.setBackground(Theme.getThemedDrawableByKey(context, C2702R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    availableReactionCell = anonymousClass1;
                } else {
                    availableReactionCell = new AvailableReactionCell(context, true, true);
                }
                return new RecyclerListView.Holder(availableReactionCell);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                if (getItemViewType(i) != 1) {
                    return;
                }
                AvailableReactionCell availableReactionCell = (AvailableReactionCell) viewHolder.itemView;
                TLRPC.TL_availableReaction tL_availableReaction = (TLRPC.TL_availableReaction) ReactionsDoubleTapManageActivity.this.getAvailableReactions().get(i - ReactionsDoubleTapManageActivity.this.reactionsStartRow);
                availableReactionCell.bind(tL_availableReaction, tL_availableReaction.reaction.contains(MediaDataController.getInstance(((BaseFragment) ReactionsDoubleTapManageActivity.this).currentAccount).getDoubleTapReaction()), ((BaseFragment) ReactionsDoubleTapManageActivity.this).currentAccount);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                ReactionsDoubleTapManageActivity reactionsDoubleTapManageActivity = ReactionsDoubleTapManageActivity.this;
                return reactionsDoubleTapManageActivity.rowCount + (reactionsDoubleTapManageActivity.premiumReactionRow < 0 ? reactionsDoubleTapManageActivity.getAvailableReactions().size() : 0) + 1;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemViewType(int i) {
                ReactionsDoubleTapManageActivity reactionsDoubleTapManageActivity = ReactionsDoubleTapManageActivity.this;
                if (i == reactionsDoubleTapManageActivity.previewRow) {
                    return 0;
                }
                if (i == reactionsDoubleTapManageActivity.infoRow) {
                    return 2;
                }
                if (i == reactionsDoubleTapManageActivity.premiumReactionRow) {
                    return 3;
                }
                return i == getItemCount() - 1 ? 4 : 1;
            }
        };
        this.listAdapter = c63442;
        recyclerListView2.setAdapter(c63442);
        this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.ReactionsDoubleTapManageActivity$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i) {
                this.f$0.lambda$createView$0(view, i);
            }
        });
        linearLayout.addView(this.listView, LayoutHelper.createLinear(-1, -1));
        this.contentView = linearLayout;
        this.fragmentView = linearLayout;
        updateColors();
        updateRows();
        return this.contentView;
    }

    /* JADX INFO: renamed from: org.telegram.ui.ReactionsDoubleTapManageActivity$1 */
    class C63431 extends ActionBar.ActionBarMenuOnItemClick {
        C63431() {
        }

        @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
        public void onItemClick(int i) {
            if (i == -1) {
                ReactionsDoubleTapManageActivity.this.finishFragment();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.ReactionsDoubleTapManageActivity$2 */
    class C63442 extends RecyclerListView.SelectionAdapter {
        final /* synthetic */ Context val$context;

        C63442(Context context2) {
            context = context2;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 3 || viewHolder.getItemViewType() == 2;
        }

        /* JADX INFO: renamed from: org.telegram.ui.ReactionsDoubleTapManageActivity$2$1 */
        class AnonymousClass1 extends View {
            AnonymousClass1(Context context2) {
                super(context2);
            }

            @Override // android.view.View
            protected void onMeasure(int i2, int i22) {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(16.0f), TLObject.FLAG_30));
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View availableReactionCell;
            if (i == 0) {
                ThemePreviewMessagesCell themePreviewMessagesCell = new ThemePreviewMessagesCell(context, ((BaseFragment) ReactionsDoubleTapManageActivity.this).parentLayout, 2);
                themePreviewMessagesCell.setImportantForAccessibility(4);
                themePreviewMessagesCell.fragment = ReactionsDoubleTapManageActivity.this;
                availableReactionCell = themePreviewMessagesCell;
            } else if (i == 2) {
                TextInfoPrivacyCell textInfoPrivacyCell = new TextInfoPrivacyCell(context);
                textInfoPrivacyCell.setText(LocaleController.getString(C2702R.string.DoubleTapPreviewRational));
                textInfoPrivacyCell.setBackground(Theme.getThemedDrawableByKey(context, C2702R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                availableReactionCell = textInfoPrivacyCell;
            } else if (i == 3) {
                SetDefaultReactionCell setDefaultReactionCell = ReactionsDoubleTapManageActivity.this.new SetDefaultReactionCell(context);
                setDefaultReactionCell.update(false);
                availableReactionCell = setDefaultReactionCell;
            } else if (i == 4) {
                AnonymousClass1 anonymousClass1 = new View(context) { // from class: org.telegram.ui.ReactionsDoubleTapManageActivity.2.1
                    AnonymousClass1(Context context2) {
                        super(context2);
                    }

                    @Override // android.view.View
                    protected void onMeasure(int i2, int i22) {
                        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(16.0f), TLObject.FLAG_30));
                    }
                };
                anonymousClass1.setBackground(Theme.getThemedDrawableByKey(context, C2702R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                availableReactionCell = anonymousClass1;
            } else {
                availableReactionCell = new AvailableReactionCell(context, true, true);
            }
            return new RecyclerListView.Holder(availableReactionCell);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (getItemViewType(i) != 1) {
                return;
            }
            AvailableReactionCell availableReactionCell = (AvailableReactionCell) viewHolder.itemView;
            TLRPC.TL_availableReaction tL_availableReaction = (TLRPC.TL_availableReaction) ReactionsDoubleTapManageActivity.this.getAvailableReactions().get(i - ReactionsDoubleTapManageActivity.this.reactionsStartRow);
            availableReactionCell.bind(tL_availableReaction, tL_availableReaction.reaction.contains(MediaDataController.getInstance(((BaseFragment) ReactionsDoubleTapManageActivity.this).currentAccount).getDoubleTapReaction()), ((BaseFragment) ReactionsDoubleTapManageActivity.this).currentAccount);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            ReactionsDoubleTapManageActivity reactionsDoubleTapManageActivity = ReactionsDoubleTapManageActivity.this;
            return reactionsDoubleTapManageActivity.rowCount + (reactionsDoubleTapManageActivity.premiumReactionRow < 0 ? reactionsDoubleTapManageActivity.getAvailableReactions().size() : 0) + 1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            ReactionsDoubleTapManageActivity reactionsDoubleTapManageActivity = ReactionsDoubleTapManageActivity.this;
            if (i == reactionsDoubleTapManageActivity.previewRow) {
                return 0;
            }
            if (i == reactionsDoubleTapManageActivity.infoRow) {
                return 2;
            }
            if (i == reactionsDoubleTapManageActivity.premiumReactionRow) {
                return 3;
            }
            return i == getItemCount() - 1 ? 4 : 1;
        }
    }

    public /* synthetic */ void lambda$createView$0(View view, int i) {
        if (view instanceof AvailableReactionCell) {
            AvailableReactionCell availableReactionCell = (AvailableReactionCell) view;
            if (availableReactionCell.locked && !getUserConfig().isPremium()) {
                showDialog(new PremiumFeatureBottomSheet(this, 4, true));
                return;
            } else {
                MediaDataController.getInstance(this.currentAccount).setDoubleTapReaction(availableReactionCell.react.reaction);
                this.listView.getAdapter().notifyItemRangeChanged(0, this.listView.getAdapter().getItemCount());
                return;
            }
        }
        if (view instanceof SetDefaultReactionCell) {
            showSelectStatusDialog((SetDefaultReactionCell) view);
        }
    }

    private class SetDefaultReactionCell extends FrameLayout {
        private AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable imageDrawable;
        private TextView textView;

        public SetDefaultReactionCell(Context context) {
            super(context);
            setBackgroundColor(ReactionsDoubleTapManageActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
            TextView textView = new TextView(context);
            this.textView = textView;
            textView.setTextSize(1, 16.0f);
            this.textView.setTextColor(ReactionsDoubleTapManageActivity.this.getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
            this.textView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_REGULAR));
            this.textView.setText(LocaleController.getString(C2702R.string.DoubleTapSetting));
            addView(this.textView, LayoutHelper.createFrame(-1, -2.0f, 23, 20.0f, 0.0f, 48.0f, 0.0f));
            this.imageDrawable = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(this, AndroidUtilities.m1081dp(24.0f));
        }

        public void update(boolean z) {
            String doubleTapReaction = MediaDataController.getInstance(((BaseFragment) ReactionsDoubleTapManageActivity.this).currentAccount).getDoubleTapReaction();
            if (doubleTapReaction != null && doubleTapReaction.startsWith("animated_")) {
                try {
                    this.imageDrawable.set(Long.parseLong(doubleTapReaction.substring(9)), z);
                    return;
                } catch (Exception unused) {
                }
            }
            TLRPC.TL_availableReaction tL_availableReaction = MediaDataController.getInstance(((BaseFragment) ReactionsDoubleTapManageActivity.this).currentAccount).getReactionsMap().get(doubleTapReaction);
            if (tL_availableReaction != null) {
                this.imageDrawable.set(tL_availableReaction.static_icon, z);
            }
        }

        public void updateImageBounds() {
            this.imageDrawable.setBounds((getWidth() - this.imageDrawable.getIntrinsicWidth()) - AndroidUtilities.m1081dp(21.0f), (getHeight() - this.imageDrawable.getIntrinsicHeight()) / 2, getWidth() - AndroidUtilities.m1081dp(21.0f), (getHeight() + this.imageDrawable.getIntrinsicHeight()) / 2);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            updateImageBounds();
            this.imageDrawable.draw(canvas);
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(50.0f), TLObject.FLAG_30));
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            this.imageDrawable.detach();
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            this.imageDrawable.attach();
        }
    }

    public void showSelectStatusDialog(SetDefaultReactionCell setDefaultReactionCell) {
        int iCenterX;
        int i;
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable;
        SetDefaultReactionCell setDefaultReactionCell2;
        if (this.selectAnimatedEmojiDialog != null) {
            return;
        }
        SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] selectAnimatedEmojiDialogWindowArr = new SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[1];
        if (setDefaultReactionCell != null) {
            AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable2 = setDefaultReactionCell.imageDrawable;
            if (setDefaultReactionCell.imageDrawable != null) {
                setDefaultReactionCell.imageDrawable.play();
                setDefaultReactionCell.updateImageBounds();
                Rect rect = AndroidUtilities.rectTmp2;
                rect.set(setDefaultReactionCell.imageDrawable.getBounds());
                int iM1081dp = (-(setDefaultReactionCell.getHeight() - rect.centerY())) - AndroidUtilities.m1081dp(16.0f);
                iCenterX = rect.centerX() - (setDefaultReactionCell.getRight() - ((int) Math.min(AndroidUtilities.m1081dp(324.0f), AndroidUtilities.displaySize.x * 0.95f)));
                setDefaultReactionCell2 = setDefaultReactionCell;
                swapAnimatedEmojiDrawable = swapAnimatedEmojiDrawable2;
                i = iM1081dp;
            } else {
                setDefaultReactionCell2 = setDefaultReactionCell;
                iCenterX = 0;
                i = 0;
                swapAnimatedEmojiDrawable = swapAnimatedEmojiDrawable2;
            }
        } else {
            iCenterX = 0;
            i = 0;
            swapAnimatedEmojiDrawable = null;
            setDefaultReactionCell2 = null;
        }
        C63453 c63453 = new SelectAnimatedEmojiDialog(this, getContext(), false, Integer.valueOf(iCenterX), 2, null) { // from class: org.telegram.ui.ReactionsDoubleTapManageActivity.3
            final /* synthetic */ SetDefaultReactionCell val$cell;
            final /* synthetic */ SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] val$popup;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C63453(BaseFragment this, Context context, boolean z, Integer num, int i2, Theme.ResourcesProvider resourcesProvider, SetDefaultReactionCell setDefaultReactionCell3, SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] selectAnimatedEmojiDialogWindowArr2) {
                super(this, context, z, num, i2, resourcesProvider);
                setDefaultReactionCell = setDefaultReactionCell3;
                selectAnimatedEmojiDialogWindowArr = selectAnimatedEmojiDialogWindowArr2;
            }

            @Override // org.telegram.p026ui.SelectAnimatedEmojiDialog
            protected void onEmojiSelected(View view, Long l, TLRPC.Document document, TL_stars.TL_starGiftUnique tL_starGiftUnique, Integer num) {
                if (l == null) {
                    return;
                }
                MediaDataController.getInstance(((BaseFragment) ReactionsDoubleTapManageActivity.this).currentAccount).setDoubleTapReaction("animated_" + l);
                SetDefaultReactionCell setDefaultReactionCell3 = setDefaultReactionCell;
                if (setDefaultReactionCell3 != null) {
                    setDefaultReactionCell3.update(true);
                }
                if (selectAnimatedEmojiDialogWindowArr[0] != null) {
                    ReactionsDoubleTapManageActivity.this.selectAnimatedEmojiDialog = null;
                    selectAnimatedEmojiDialogWindowArr[0].dismiss();
                }
            }

            @Override // org.telegram.p026ui.SelectAnimatedEmojiDialog
            protected void onReactionClick(SelectAnimatedEmojiDialog.ImageViewEmoji imageViewEmoji, ReactionsLayoutInBubble.VisibleReaction visibleReaction) {
                MediaDataController.getInstance(((BaseFragment) ReactionsDoubleTapManageActivity.this).currentAccount).setDoubleTapReaction(visibleReaction.emojicon);
                SetDefaultReactionCell setDefaultReactionCell3 = setDefaultReactionCell;
                if (setDefaultReactionCell3 != null) {
                    setDefaultReactionCell3.update(true);
                }
                if (selectAnimatedEmojiDialogWindowArr[0] != null) {
                    ReactionsDoubleTapManageActivity.this.selectAnimatedEmojiDialog = null;
                    selectAnimatedEmojiDialogWindowArr[0].dismiss();
                }
            }
        };
        String doubleTapReaction = getMediaDataController().getDoubleTapReaction();
        if (doubleTapReaction != null && doubleTapReaction.startsWith("animated_")) {
            try {
                c63453.setSelected(Long.valueOf(Long.parseLong(doubleTapReaction.substring(9))));
            } catch (Exception unused) {
            }
        }
        List availableReactions = getAvailableReactions();
        ArrayList arrayList = new ArrayList(20);
        for (int i2 = 0; i2 < availableReactions.size(); i2++) {
            ReactionsLayoutInBubble.VisibleReaction visibleReaction = new ReactionsLayoutInBubble.VisibleReaction();
            visibleReaction.emojicon = ((TLRPC.TL_availableReaction) availableReactions.get(i2)).reaction;
            arrayList.add(visibleReaction);
        }
        c63453.setRecentReactions(arrayList);
        c63453.setSaveState(3);
        c63453.setScrimDrawable(swapAnimatedEmojiDrawable, setDefaultReactionCell2);
        C63464 c63464 = new SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow(c63453, -2, -2) { // from class: org.telegram.ui.ReactionsDoubleTapManageActivity.4
            C63464(View c634532, int i3, int i4) {
                super(c634532, i3, i4);
            }

            @Override // org.telegram.ui.SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow, android.widget.PopupWindow
            public void dismiss() {
                super.dismiss();
                ReactionsDoubleTapManageActivity.this.selectAnimatedEmojiDialog = null;
            }
        };
        this.selectAnimatedEmojiDialog = c63464;
        selectAnimatedEmojiDialogWindowArr2[0] = c63464;
        c63464.showAsDropDown(setDefaultReactionCell3, 0, i, 53);
        selectAnimatedEmojiDialogWindowArr2[0].dimBehind();
    }

    /* JADX INFO: renamed from: org.telegram.ui.ReactionsDoubleTapManageActivity$3 */
    class C63453 extends SelectAnimatedEmojiDialog {
        final /* synthetic */ SetDefaultReactionCell val$cell;
        final /* synthetic */ SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] val$popup;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C63453(ReactionsDoubleTapManageActivity this, Context context, boolean z, Integer num, int i2, Theme.ResourcesProvider resourcesProvider, SetDefaultReactionCell setDefaultReactionCell3, SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] selectAnimatedEmojiDialogWindowArr2) {
            super(this, context, z, num, i2, resourcesProvider);
            setDefaultReactionCell = setDefaultReactionCell3;
            selectAnimatedEmojiDialogWindowArr = selectAnimatedEmojiDialogWindowArr2;
        }

        @Override // org.telegram.p026ui.SelectAnimatedEmojiDialog
        protected void onEmojiSelected(View view, Long l, TLRPC.Document document, TL_stars.TL_starGiftUnique tL_starGiftUnique, Integer num) {
            if (l == null) {
                return;
            }
            MediaDataController.getInstance(((BaseFragment) ReactionsDoubleTapManageActivity.this).currentAccount).setDoubleTapReaction("animated_" + l);
            SetDefaultReactionCell setDefaultReactionCell3 = setDefaultReactionCell;
            if (setDefaultReactionCell3 != null) {
                setDefaultReactionCell3.update(true);
            }
            if (selectAnimatedEmojiDialogWindowArr[0] != null) {
                ReactionsDoubleTapManageActivity.this.selectAnimatedEmojiDialog = null;
                selectAnimatedEmojiDialogWindowArr[0].dismiss();
            }
        }

        @Override // org.telegram.p026ui.SelectAnimatedEmojiDialog
        protected void onReactionClick(SelectAnimatedEmojiDialog.ImageViewEmoji imageViewEmoji, ReactionsLayoutInBubble.VisibleReaction visibleReaction) {
            MediaDataController.getInstance(((BaseFragment) ReactionsDoubleTapManageActivity.this).currentAccount).setDoubleTapReaction(visibleReaction.emojicon);
            SetDefaultReactionCell setDefaultReactionCell3 = setDefaultReactionCell;
            if (setDefaultReactionCell3 != null) {
                setDefaultReactionCell3.update(true);
            }
            if (selectAnimatedEmojiDialogWindowArr[0] != null) {
                ReactionsDoubleTapManageActivity.this.selectAnimatedEmojiDialog = null;
                selectAnimatedEmojiDialogWindowArr[0].dismiss();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.ReactionsDoubleTapManageActivity$4 */
    class C63464 extends SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow {
        C63464(View c634532, int i3, int i4) {
            super(c634532, i3, i4);
        }

        @Override // org.telegram.ui.SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow, android.widget.PopupWindow
        public void dismiss() {
            super.dismiss();
            ReactionsDoubleTapManageActivity.this.selectAnimatedEmojiDialog = null;
        }
    }

    private void updateRows() {
        this.previewRow = 0;
        this.rowCount = 1 + 1;
        this.infoRow = 1;
        if (UserConfig.getInstance(this.currentAccount).isPremium()) {
            this.reactionsStartRow = -1;
            int i = this.rowCount;
            this.rowCount = i + 1;
            this.premiumReactionRow = i;
            return;
        }
        this.premiumReactionRow = -1;
        this.reactionsStartRow = this.rowCount;
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        getNotificationCenter().removeObserver(this, NotificationCenter.reactionsDidLoad);
        getNotificationCenter().removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
    }

    public List getAvailableReactions() {
        return getMediaDataController().getReactionsList();
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public ArrayList getThemeDescriptions() {
        return SimpleThemeDescription.createThemeDescriptions(new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.ReactionsDoubleTapManageActivity$$ExternalSyntheticLambda1
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                this.f$0.updateColors();
            }

            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public /* synthetic */ void onAnimationProgress(float f) {
                ThemeDescription.ThemeDescriptionDelegate.CC.$default$onAnimationProgress(this, f);
            }
        }, Theme.key_windowBackgroundWhite, Theme.key_windowBackgroundWhiteBlackText, Theme.key_windowBackgroundWhiteGrayText2, Theme.key_listSelector, Theme.key_windowBackgroundGray, Theme.key_windowBackgroundWhiteGrayText4, Theme.key_text_RedRegular, Theme.key_windowBackgroundChecked, Theme.key_windowBackgroundCheckText, Theme.key_switchTrackBlue, Theme.key_switchTrackBlueChecked, Theme.key_switchTrackBlueThumb, Theme.key_switchTrackBlueThumbChecked);
    }

    public void updateColors() {
        this.contentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listAdapter.notifyDataSetChanged();
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i2 != this.currentAccount) {
            return;
        }
        if (i == NotificationCenter.reactionsDidLoad) {
            this.listAdapter.notifyDataSetChanged();
        } else if (i == NotificationCenter.currentUserPremiumStatusChanged) {
            updateRows();
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
