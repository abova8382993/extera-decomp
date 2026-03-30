package org.telegram.ui.Gifts;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AnimationNotificationsLocker;
import org.telegram.messenger.BillingController;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.GiftAuctionController;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.tl.TL_stars;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.INavigationLayout;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.ChatActionCell;
import org.telegram.ui.Cells.EditEmojiTextCell;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.BottomSheetWithRecyclerListView;
import org.telegram.ui.Components.BulletinFactory;
import org.telegram.ui.Components.ColoredImageSpan;
import org.telegram.ui.Components.Premium.GiftPremiumBottomSheet$GiftTier;
import org.telegram.ui.Components.Premium.boosts.BoostDialogs;
import org.telegram.ui.Components.Premium.boosts.BoostRepository;
import org.telegram.ui.Components.Premium.boosts.PremiumPreviewGiftSentBottomSheet;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.SizeNotifierFrameLayout;
import org.telegram.ui.Components.UItem;
import org.telegram.ui.Components.UniversalAdapter;
import org.telegram.ui.Components.blur3.drawable.BlurredBackgroundDrawable;
import org.telegram.ui.Components.chat.ViewPositionWatcher;
import org.telegram.ui.Gifts.AuctionBidSheet;
import org.telegram.ui.LaunchActivity;
import org.telegram.ui.ProfileActivity;
import org.telegram.ui.Stars.StarGiftSheet;
import org.telegram.ui.Stars.StarsController;
import org.telegram.ui.Stars.StarsIntroActivity;
import org.telegram.ui.Stories.recorder.ButtonWithCounterView;

/* JADX INFO: loaded from: classes5.dex */
public class SendGiftSheet extends BottomSheetWithRecyclerListView implements NotificationCenter.NotificationCenterDelegate, GiftAuctionController.OnAuctionUpdateListener {
    private final TLRPC.MessageAction action;
    private final ChatActionCell actionCell;
    private UniversalAdapter adapter;
    public final AnimationNotificationsLocker animationsLock;
    public boolean anonymous;
    private GiftAuctionController.Auction auction;
    private final ButtonWithCounterView button;
    private final LinearLayout buttonContainer;
    private final ColoredImageSpan[] cachedStarSpan;
    private final LinearLayout chatLinearLayout;
    private final SizeNotifierFrameLayout chatView;
    private final Runnable closeParentSheet;
    private final int currentAccount;
    private final long dialogId;
    private final boolean forceNotUpgrade;
    private final boolean forceUpgrade;
    boolean isDismissed;
    private final TextView leftTextView;
    private final TextView leftTextView2;
    private final FrameLayout limitContainer;
    private final FrameLayout limitContainerWrapper;
    private final View limitProgressView;
    private EditEmojiTextCell messageEdit;
    private final MessageObject messageObject;
    private final String name;
    private final GiftPremiumBottomSheet$GiftTier premiumTier;
    private final boolean self;
    private final long send_paid_messages_stars;
    private int shakeDp;
    private final TextView soldTextView;
    private final TextView soldTextView2;
    private final TL_stars.StarGift starGift;
    public boolean upgrade;
    public boolean useStars;
    private final FrameLayout valueContainerView;

    public SendGiftSheet(Context context, int i, TL_stars.StarGift starGift, long j, Runnable runnable, boolean z, boolean z2) {
        this(context, i, starGift, null, j, runnable, z, z2);
    }

    public SendGiftSheet(Context context, int i, GiftPremiumBottomSheet$GiftTier giftPremiumBottomSheet$GiftTier, long j, Runnable runnable) {
        this(context, i, null, giftPremiumBottomSheet$GiftTier, j, runnable, false, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:179:0x012a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private SendGiftSheet(final android.content.Context r33, int r34, final org.telegram.tgnet.tl.TL_stars.StarGift r35, final org.telegram.ui.Components.Premium.GiftPremiumBottomSheet$GiftTier r36, final long r37, final java.lang.Runnable r39, final boolean r40, final boolean r41) {
        /*
            Method dump skipped, instruction units count: 1543
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Gifts.SendGiftSheet.<init>(android.content.Context, int, org.telegram.tgnet.tl.TL_stars$StarGift, org.telegram.ui.Components.Premium.GiftPremiumBottomSheet$GiftTier, long, java.lang.Runnable, boolean, boolean):void");
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$1 */
    class AnonymousClass1 implements ChatActionCell.ChatActionCellDelegate {
        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ boolean canDrawOutboundsContent() {
            return ChatActionCell.ChatActionCellDelegate.CC.$default$canDrawOutboundsContent(this);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ void didClickButton(ChatActionCell chatActionCell) {
            ChatActionCell.ChatActionCellDelegate.CC.$default$didClickButton(this, chatActionCell);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ void didClickImage(ChatActionCell chatActionCell) {
            ChatActionCell.ChatActionCellDelegate.CC.$default$didClickImage(this, chatActionCell);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ boolean didLongPress(ChatActionCell chatActionCell, float f, float f2) {
            return ChatActionCell.ChatActionCellDelegate.CC.$default$didLongPress(this, chatActionCell, f, f2);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ void didOpenPremiumGift(ChatActionCell chatActionCell, TLRPC.TL_premiumGiftOption tL_premiumGiftOption, String str, boolean z) {
            ChatActionCell.ChatActionCellDelegate.CC.$default$didOpenPremiumGift(this, chatActionCell, tL_premiumGiftOption, str, z);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ void didOpenPremiumGiftChannel(ChatActionCell chatActionCell, String str, boolean z) {
            ChatActionCell.ChatActionCellDelegate.CC.$default$didOpenPremiumGiftChannel(this, chatActionCell, str, z);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ void didPressReaction(ChatActionCell chatActionCell, TLRPC.ReactionCount reactionCount, boolean z, float f, float f2) {
            ChatActionCell.ChatActionCellDelegate.CC.$default$didPressReaction(this, chatActionCell, reactionCount, z, f, f2);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ void didPressReplyMessage(ChatActionCell chatActionCell, int i) {
            ChatActionCell.ChatActionCellDelegate.CC.$default$didPressReplyMessage(this, chatActionCell, i);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ void didPressTaskLink(ChatActionCell chatActionCell, int i, int i2) {
            ChatActionCell.ChatActionCellDelegate.CC.$default$didPressTaskLink(this, chatActionCell, i, i2);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ void forceUpdate(ChatActionCell chatActionCell, boolean z) {
            ChatActionCell.ChatActionCellDelegate.CC.$default$forceUpdate(this, chatActionCell, z);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ BaseFragment getBaseFragment() {
            return ChatActionCell.ChatActionCellDelegate.CC.$default$getBaseFragment(this);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ long getDialogId() {
            return ChatActionCell.ChatActionCellDelegate.CC.$default$getDialogId(this);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ long getTopicId() {
            return ChatActionCell.ChatActionCellDelegate.CC.$default$getTopicId(this);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ void needOpenInviteLink(TLRPC.TL_chatInviteExported tL_chatInviteExported) {
            ChatActionCell.ChatActionCellDelegate.CC.$default$needOpenInviteLink(this, tL_chatInviteExported);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ void needOpenUserProfile(long j) {
            ChatActionCell.ChatActionCellDelegate.CC.$default$needOpenUserProfile(this, j);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ void needShowEffectOverlay(ChatActionCell chatActionCell, TLRPC.Document document, TLRPC.VideoSize videoSize) {
            ChatActionCell.ChatActionCellDelegate.CC.$default$needShowEffectOverlay(this, chatActionCell, document, videoSize);
        }

        @Override // org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate
        public /* synthetic */ void onTopicClick(ChatActionCell chatActionCell) {
            ChatActionCell.ChatActionCellDelegate.CC.$default$onTopicClick(this, chatActionCell);
        }

        AnonymousClass1() {
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$2 */
    class AnonymousClass2 extends SizeNotifierFrameLayout {
        int maxHeight = -1;

        @Override // org.telegram.ui.Components.SizeNotifierFrameLayout
        protected boolean isActionBarVisible() {
            return false;
        }

        @Override // org.telegram.ui.Components.SizeNotifierFrameLayout
        public boolean isStatusBarVisible() {
            return false;
        }

        @Override // org.telegram.ui.Components.SizeNotifierFrameLayout
        protected boolean useRootView() {
            return false;
        }

        AnonymousClass2(Context context) {
            super(context);
            this.maxHeight = -1;
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            if (this.maxHeight != -1) {
                super.onMeasure(i, i2);
                int measuredHeight = getMeasuredHeight();
                int i3 = this.maxHeight;
                if (measuredHeight < i3) {
                    i2 = View.MeasureSpec.makeMeasureSpec(Math.max(i3, getMeasuredHeight()), Integer.MIN_VALUE);
                }
            }
            super.onMeasure(i, i2);
            int i4 = this.maxHeight;
            if (i4 == -1) {
                this.maxHeight = Math.max(i4, getMeasuredHeight());
            }
        }

        @Override // android.view.ViewGroup
        protected boolean drawChild(Canvas canvas, View view, long j) {
            if (view == this.backgroundView) {
                return true;
            }
            return super.drawChild(canvas, view, j);
        }

        @Override // org.telegram.ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            SendGiftSheet.this.chatLinearLayout.setTranslationY(((i4 - i2) - SendGiftSheet.this.chatLinearLayout.getMeasuredHeight()) / 2.0f);
            SendGiftSheet.this.actionCell.setVisiblePart(SendGiftSheet.this.chatLinearLayout.getY() + SendGiftSheet.this.actionCell.getY(), getBackgroundSizeY());
        }

        @Override // org.telegram.ui.Components.SizeNotifierFrameLayout
        protected void onBackgroundViewInvalidate() {
            super.onBackgroundViewInvalidate();
            ((BottomSheetWithRecyclerListView) SendGiftSheet.this).recyclerListView.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$3 */
    class AnonymousClass3 extends EditEmojiTextCell {
        final /* synthetic */ int val$currentAccount;
        final /* synthetic */ BlurredBackgroundDrawable val$msgDrawable;

        @Override // org.telegram.ui.Cells.EditEmojiTextCell
        protected void onFocusChanged(boolean z) {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(Context context, SizeNotifierFrameLayout sizeNotifierFrameLayout, String str, boolean z, int i, int i2, Theme.ResourcesProvider resourcesProvider, BlurredBackgroundDrawable blurredBackgroundDrawable, int i3) {
            super(context, sizeNotifierFrameLayout, str, z, i, i2, resourcesProvider);
            blurredBackgroundDrawable = blurredBackgroundDrawable;
            i = i3;
        }

        @Override // org.telegram.ui.Cells.EditEmojiTextCell, android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            setPadding(AndroidUtilities.dp(16.0f), 0, AndroidUtilities.dp(12.0f), 0);
            super.onMeasure(i, i2);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            blurredBackgroundDrawable.setBounds(AndroidUtilities.dp(10.0f), 0, getMeasuredWidth() - AndroidUtilities.dp(10.0f), getMeasuredHeight());
            blurredBackgroundDrawable.draw(canvas);
            super.dispatchDraw(canvas);
        }

        @Override // org.telegram.ui.Cells.EditEmojiTextCell
        protected void onTextChanged(CharSequence charSequence) {
            TLRPC.TL_textWithEntities tL_textWithEntities;
            if (SendGiftSheet.this.action instanceof TLRPC.TL_messageActionStarGift) {
                TLRPC.TL_messageActionStarGift tL_messageActionStarGift = (TLRPC.TL_messageActionStarGift) SendGiftSheet.this.action;
                tL_textWithEntities = new TLRPC.TL_textWithEntities();
                tL_messageActionStarGift.message = tL_textWithEntities;
            } else if (SendGiftSheet.this.action instanceof TLRPC.TL_messageActionGiftCode) {
                ((TLRPC.TL_messageActionGiftCode) SendGiftSheet.this.action).flags |= 16;
                TLRPC.TL_messageActionGiftCode tL_messageActionGiftCode = (TLRPC.TL_messageActionGiftCode) SendGiftSheet.this.action;
                tL_textWithEntities = new TLRPC.TL_textWithEntities();
                tL_messageActionGiftCode.message = tL_textWithEntities;
            } else {
                if (!(SendGiftSheet.this.action instanceof TLRPC.TL_messageActionGiftPremium)) {
                    return;
                }
                ((TLRPC.TL_messageActionGiftPremium) SendGiftSheet.this.action).flags |= 16;
                TLRPC.TL_messageActionGiftPremium tL_messageActionGiftPremium = (TLRPC.TL_messageActionGiftPremium) SendGiftSheet.this.action;
                tL_textWithEntities = new TLRPC.TL_textWithEntities();
                tL_messageActionGiftPremium.message = tL_textWithEntities;
            }
            CharSequence[] charSequenceArr = {SendGiftSheet.this.messageEdit.getText()};
            tL_textWithEntities.entities = MediaDataController.getInstance(i).getEntities(charSequenceArr, true);
            tL_textWithEntities.text = charSequenceArr[0].toString();
            SendGiftSheet.this.messageObject.setType();
            SendGiftSheet.this.actionCell.setMessageObject(SendGiftSheet.this.messageObject, true);
            SendGiftSheet.this.adapter.update(true);
            SendGiftSheet.this.setButtonText(true);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$4 */
    class AnonymousClass4 extends DefaultItemAnimator {
        @Override // androidx.recyclerview.widget.DefaultItemAnimator
        protected float animateByScale(View view) {
            return 0.3f;
        }

        AnonymousClass4() {
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$5 */
    class AnonymousClass5 extends View {
        final /* synthetic */ float val$limitedProgress;
        final /* synthetic */ TL_stars.StarGift val$starGift;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass5(Context context, TL_stars.StarGift starGift, float f) {
            super(context);
            starGift = starGift;
            f = f;
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            if (starGift == null) {
                super.onMeasure(i, i2);
            } else {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec((int) (View.MeasureSpec.getSize(i) * f), TLObject.FLAG_30), i2);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$6 */
    class AnonymousClass6 extends FrameLayout {
        final /* synthetic */ float val$limitedProgress;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass6(Context context, float f) {
            super(context);
            f = f;
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            canvas.save();
            canvas.clipRect(0.0f, 0.0f, getWidth() * f, getHeight());
            super.dispatchDraw(canvas);
            canvas.restore();
        }
    }

    public /* synthetic */ void lambda$new$0(long j, Context context, Runnable runnable, TL_stars.StarGift starGift, View view) {
        if (this.button.isLoading()) {
            return;
        }
        if (this.auction != null) {
            AuctionBidSheet auctionBidSheet = new AuctionBidSheet(context, this.resourcesProvider, new AuctionBidSheet.Params(j, this.anonymous, getMessage()), this.auction);
            auctionBidSheet.show();
            auctionBidSheet.setCloseParentSheet(runnable);
            AndroidUtilities.hideKeyboard(this.messageEdit);
            lambda$new$0();
            if (this.isDismissed) {
                return;
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$0();
                }
            }, 500L);
            return;
        }
        this.button.setLoading(true);
        if (this.messageEdit.editTextEmoji.getEmojiPadding() > 0) {
            this.messageEdit.editTextEmoji.hidePopup(true);
        } else if (this.messageEdit.editTextEmoji.isKeyboardVisible()) {
            this.messageEdit.editTextEmoji.closeKeyboard();
        }
        if (starGift != null) {
            buyStarGift();
        } else {
            buyPremiumTier();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$7 */
    class AnonymousClass7 extends RecyclerView.ItemDecoration {
        final PointF p = new PointF();

        AnonymousClass7() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            float fMax;
            float f;
            float height = recyclerView.getHeight();
            if (ViewPositionWatcher.computeCoordinatesInParent(SendGiftSheet.this.chatView, ((BottomSheetWithRecyclerListView) SendGiftSheet.this).recyclerListView, this.p)) {
                PointF pointF = this.p;
                f = pointF.x;
                height = Math.min(height, pointF.y);
                fMax = Math.max(0.0f, this.p.y + SendGiftSheet.this.chatView.getMeasuredHeight());
            } else {
                fMax = 0.0f;
                f = 0.0f;
            }
            if (ViewPositionWatcher.computeCoordinatesInParent(SendGiftSheet.this.messageEdit, ((BottomSheetWithRecyclerListView) SendGiftSheet.this).recyclerListView, this.p)) {
                height = Math.min(height, this.p.y);
                fMax = Math.max(fMax, this.p.y + SendGiftSheet.this.messageEdit.getMeasuredHeight() + AndroidUtilities.dp(12.0f));
            }
            if (height < fMax && SendGiftSheet.this.chatView.backgroundView != null) {
                float height2 = (fMax - height) / SendGiftSheet.this.chatView.backgroundView.getHeight();
                canvas.save();
                canvas.clipRect(0.0f, height, recyclerView.getWidth(), fMax);
                canvas.translate(f, height);
                canvas.scale(height2, height2);
                SendGiftSheet.this.chatView.backgroundView.draw(canvas);
                canvas.restore();
            }
            super.onDraw(canvas, recyclerView, state);
        }
    }

    public /* synthetic */ void lambda$new$1(boolean z, boolean z2, TL_stars.StarGift starGift, GiftPremiumBottomSheet$GiftTier giftPremiumBottomSheet$GiftTier, View view, int i) {
        UniversalAdapter universalAdapter = this.adapter;
        if (!this.reverseLayout) {
            i--;
        }
        UItem item = universalAdapter.getItem(i);
        if (item == null) {
            return;
        }
        int i2 = item.id;
        if (i2 == 1) {
            boolean z3 = !this.anonymous;
            this.anonymous = z3;
            TLRPC.MessageAction messageAction = this.action;
            if (messageAction instanceof TLRPC.TL_messageActionStarGift) {
                ((TLRPC.TL_messageActionStarGift) messageAction).name_hidden = z3;
            }
            this.messageObject.updateMessageText();
            this.actionCell.setMessageObject(this.messageObject, true);
            this.adapter.update(true);
            return;
        }
        if (i2 == 2) {
            if (z || z2) {
                int i3 = -this.shakeDp;
                this.shakeDp = i3;
                AndroidUtilities.shakeViewSpring(view, i3);
                return;
            }
            boolean z4 = this.upgrade;
            this.upgrade = !z4;
            TLRPC.MessageAction messageAction2 = this.action;
            if (messageAction2 instanceof TLRPC.TL_messageActionStarGift) {
                TLRPC.TL_messageActionStarGift tL_messageActionStarGift = (TLRPC.TL_messageActionStarGift) messageAction2;
                tL_messageActionStarGift.can_upgrade = !z4 || (this.self && starGift != null && starGift.can_upgrade);
                tL_messageActionStarGift.upgrade_stars = (this.self || z4) ? 0L : this.starGift.upgrade_stars;
                tL_messageActionStarGift.convert_stars = z4 ? this.starGift.convert_stars : 0L;
            }
            this.messageObject.updateMessageText();
            this.actionCell.setMessageObject(this.messageObject, true);
            this.adapter.update(true);
            setButtonText(true);
            return;
        }
        if (i2 == 3) {
            boolean z5 = this.useStars;
            this.useStars = !z5;
            TLRPC.MessageAction messageAction3 = this.action;
            if (messageAction3 instanceof TLRPC.TL_messageActionGiftPremium) {
                TLRPC.TL_messageActionGiftPremium tL_messageActionGiftPremium = (TLRPC.TL_messageActionGiftPremium) messageAction3;
                if (!z5) {
                    tL_messageActionGiftPremium.currency = "XTR";
                    tL_messageActionGiftPremium.amount = giftPremiumBottomSheet$GiftTier.getStarsPrice();
                } else {
                    tL_messageActionGiftPremium.currency = giftPremiumBottomSheet$GiftTier.getCurrency();
                    long price = giftPremiumBottomSheet$GiftTier.getPrice();
                    tL_messageActionGiftPremium.amount = price;
                    if (giftPremiumBottomSheet$GiftTier.googlePlayProductDetails != null) {
                        tL_messageActionGiftPremium.amount = (long) (price * Math.pow(10.0d, BillingController.getInstance().getCurrencyExp(tL_messageActionGiftPremium.currency) - 6));
                    }
                }
            } else if (messageAction3 instanceof TLRPC.TL_messageActionGiftCode) {
                TLRPC.TL_messageActionGiftCode tL_messageActionGiftCode = (TLRPC.TL_messageActionGiftCode) messageAction3;
                if (!z5) {
                    tL_messageActionGiftCode.currency = "XTR";
                    tL_messageActionGiftCode.amount = giftPremiumBottomSheet$GiftTier.getStarsPrice();
                } else {
                    tL_messageActionGiftCode.currency = giftPremiumBottomSheet$GiftTier.getCurrency();
                    long price2 = giftPremiumBottomSheet$GiftTier.getPrice();
                    tL_messageActionGiftCode.amount = price2;
                    if (giftPremiumBottomSheet$GiftTier.googlePlayProductDetails != null) {
                        tL_messageActionGiftCode.amount = (long) (price2 * Math.pow(10.0d, BillingController.getInstance().getCurrencyExp(tL_messageActionGiftCode.currency) - 6));
                    }
                }
            }
            this.messageObject.updateMessageText();
            this.actionCell.setMessageObject(this.messageObject, true);
            this.adapter.update(true);
            setButtonText(true);
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.starBalanceUpdated) {
            setButtonText(true);
            UniversalAdapter universalAdapter = this.adapter;
            if (universalAdapter == null || this.premiumTier == null) {
                return;
            }
            universalAdapter.update(true);
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.starBalanceUpdated);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.starBalanceUpdated);
    }

    public void setButtonText(boolean z) {
        if (this.auction != null) {
            int currentTime = ConnectionsManager.getInstance(this.currentAccount).getCurrentTime();
            if (this.auction.isUpcoming(currentTime)) {
                int i = this.auction.gift.auction_start_date - currentTime;
                this.button.setText(LocaleController.getString(R.string.Gift2AuctionPlaceAEarlyBid), z);
                this.button.setSubText(LocaleController.formatString(R.string.Gift2AuctionStartsIn, LocaleController.formatTTLString(i)), z);
                return;
            }
            TL_stars.TL_starGiftAuctionState tL_starGiftAuctionState = this.auction.auctionStateActive;
            if (tL_starGiftAuctionState != null) {
                int i2 = tL_starGiftAuctionState.end_date - currentTime;
                this.button.setText(LocaleController.getString(R.string.Gift2AuctionPlaceABid), z);
                this.button.setSubText(LocaleController.formatString(R.string.Gift2AuctionTimeLeft, LocaleController.formatTTLString(i2)), z);
                return;
            } else {
                this.button.setText(LocaleController.getString(R.string.Gift2AuctionPlaceABid), z);
                this.button.setSubText(null, z);
                return;
            }
        }
        if (this.starGift != null) {
            long j = StarsController.getInstance(this.currentAccount).getBalance().amount;
            TL_stars.StarGift starGift = this.starGift;
            long j2 = starGift.stars + (this.upgrade ? starGift.upgrade_stars : 0L) + (TextUtils.isEmpty(this.messageEdit.getText()) ? 0L : this.send_paid_messages_stars);
            this.button.setText(StarsIntroActivity.replaceStars(LocaleController.formatPluralStringComma(this.self ? "Gift2SendSelf" : "Gift2Send", (int) j2), this.cachedStarSpan), z);
            if (StarsController.getInstance(this.currentAccount).balanceAvailable() && j2 > j) {
                this.button.setSubText(LocaleController.formatPluralStringComma("Gift2SendYourBalance", (int) j), z);
                return;
            } else {
                this.button.setSubText(null, z);
                return;
            }
        }
        GiftPremiumBottomSheet$GiftTier giftPremiumBottomSheet$GiftTier = this.premiumTier;
        if (giftPremiumBottomSheet$GiftTier != null) {
            if (this.useStars) {
                this.button.setText(StarsIntroActivity.replaceStars(LocaleController.formatString(R.string.Gift2SendPremiumStars, LocaleController.formatNumber(giftPremiumBottomSheet$GiftTier.getStarsPrice(), ',')), 1.0f, this.cachedStarSpan), z);
                this.cachedStarSpan[0].spaceScaleX = 0.85f;
            } else {
                this.button.setText(new SpannableStringBuilder(LocaleController.formatString(R.string.Gift2SendPremium, this.premiumTier.getFormattedPrice())), z);
            }
            this.button.setSubText(null, z);
        }
    }

    @Override // org.telegram.messenger.GiftAuctionController.OnAuctionUpdateListener
    public void onUpdate(GiftAuctionController.Auction auction) {
        this.auction = auction;
    }

    protected BulletinFactory getParentBulletinFactory() {
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        if (safeLastFragment == null) {
            return null;
        }
        return BulletinFactory.of(safeLastFragment);
    }

    private TLRPC.TL_textWithEntities getMessage() {
        if (MessagesController.getInstance(this.currentAccount).getSendPaidMessagesStars(this.dialogId) > 0) {
            return null;
        }
        TLRPC.MessageAction messageAction = this.action;
        if (messageAction instanceof TLRPC.TL_messageActionStarGift) {
            return ((TLRPC.TL_messageActionStarGift) messageAction).message;
        }
        if (messageAction instanceof TLRPC.TL_messageActionGiftCode) {
            return ((TLRPC.TL_messageActionGiftCode) messageAction).message;
        }
        if (messageAction instanceof TLRPC.TL_messageActionGiftPremium) {
            return ((TLRPC.TL_messageActionGiftPremium) messageAction).message;
        }
        return null;
    }

    private void buyStarGift() {
        StarsController.getInstance(this.currentAccount).buyStarGift(this.starGift, this.anonymous, this.upgrade, this.dialogId, getMessage(), new Utilities.Callback2() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda14
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.lambda$buyStarGift$2((Boolean) obj, (String) obj2);
            }
        });
    }

    public /* synthetic */ void lambda$buyStarGift$2(Boolean bool, String str) {
        TL_stars.StarGift starGift;
        if (bool.booleanValue()) {
            Runnable runnable = this.closeParentSheet;
            if (runnable != null) {
                runnable.run();
            }
            AndroidUtilities.hideKeyboard(this.messageEdit);
            lambda$new$0();
        } else if ("STARGIFT_USAGE_LIMITED".equalsIgnoreCase(str)) {
            AndroidUtilities.hideKeyboard(this.messageEdit);
            lambda$new$0();
            StarsController.getInstance(this.currentAccount).makeStarGiftSoldOut(this.starGift);
            return;
        } else if ("STARGIFT_USER_USAGE_LIMITED".equalsIgnoreCase(str)) {
            AndroidUtilities.hideKeyboard(this.messageEdit);
            lambda$new$0();
            BulletinFactory parentBulletinFactory = getParentBulletinFactory();
            if (parentBulletinFactory == null || (starGift = this.starGift) == null || !starGift.limited_per_user) {
                return;
            }
            parentBulletinFactory.createSimpleMultiBulletin(starGift.getDocument(), AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("Gift2PerUserLimit", this.starGift.per_user_total))).show();
            return;
        }
        this.button.setLoading(false);
    }

    @Override // org.telegram.ui.ActionBar.BottomSheet
    public void onOpenAnimationEnd() {
        super.onOpenAnimationEnd();
        this.recyclerListView.invalidateItemDecorations();
    }

    private void buyPremiumTier() {
        Object starsOption;
        final TLRPC.User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.dialogId));
        if (user == null) {
            this.button.setLoading(false);
            return;
        }
        if (this.useStars && this.premiumTier.isStarsPaymentAvailable()) {
            starsOption = this.premiumTier.getStarsOption();
        } else {
            GiftPremiumBottomSheet$GiftTier giftPremiumBottomSheet$GiftTier = this.premiumTier;
            TLRPC.TL_premiumGiftCodeOption tL_premiumGiftCodeOption = giftPremiumBottomSheet$GiftTier.giftCodeOption;
            if (tL_premiumGiftCodeOption != null) {
                starsOption = tL_premiumGiftCodeOption;
            } else {
                starsOption = giftPremiumBottomSheet$GiftTier.giftOption;
                if (starsOption == null) {
                    this.button.setLoading(false);
                    return;
                }
            }
        }
        if (starsOption instanceof TLRPC.TL_premiumGiftCodeOption) {
            TLRPC.TL_premiumGiftCodeOption tL_premiumGiftCodeOption2 = (TLRPC.TL_premiumGiftCodeOption) starsOption;
            if ("XTR".equalsIgnoreCase(tL_premiumGiftCodeOption2.currency)) {
                StarsController.getInstance(this.currentAccount).buyPremiumGift(this.dialogId, tL_premiumGiftCodeOption2, getMessage(), new Utilities.Callback2() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda7
                    @Override // org.telegram.messenger.Utilities.Callback2
                    public final void run(Object obj, Object obj2) {
                        this.f$0.lambda$buyPremiumTier$4(user, (Boolean) obj, (String) obj2);
                    }
                });
                return;
            } else {
                BoostRepository.payGiftCode(new ArrayList(Arrays.asList(user)), tL_premiumGiftCodeOption2, null, getMessage(), new BaseFragment() { // from class: org.telegram.ui.Gifts.SendGiftSheet.8
                    AnonymousClass8() {
                    }

                    @Override // org.telegram.ui.ActionBar.BaseFragment
                    public Activity getParentActivity() {
                        Activity ownerActivity = SendGiftSheet.this.getOwnerActivity();
                        if (ownerActivity == null) {
                            ownerActivity = LaunchActivity.instance;
                        }
                        return ownerActivity == null ? AndroidUtilities.findActivity(SendGiftSheet.this.getContext()) : ownerActivity;
                    }

                    @Override // org.telegram.ui.ActionBar.BaseFragment
                    public Theme.ResourcesProvider getResourceProvider() {
                        return ((BottomSheet) SendGiftSheet.this).resourcesProvider;
                    }
                }, new Utilities.Callback() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda8
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$buyPremiumTier$6(user, (Void) obj);
                    }
                }, new Utilities.Callback() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda9
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$buyPremiumTier$7((TLRPC.TL_error) obj);
                    }
                });
                return;
            }
        }
        if (starsOption instanceof TLRPC.TL_premiumGiftOption) {
            TLRPC.TL_premiumGiftOption tL_premiumGiftOption = (TLRPC.TL_premiumGiftOption) starsOption;
            if ("XTR".equalsIgnoreCase(tL_premiumGiftOption.currency)) {
                StarsController.getInstance(this.currentAccount).buyPremiumGift(this.dialogId, tL_premiumGiftOption, getMessage(), new Utilities.Callback2() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda10
                    @Override // org.telegram.messenger.Utilities.Callback2
                    public final void run(Object obj, Object obj2) {
                        this.f$0.lambda$buyPremiumTier$9(user, (Boolean) obj, (String) obj2);
                    }
                });
                return;
            }
            if (BuildVars.useInvoiceBilling()) {
                LaunchActivity launchActivity = LaunchActivity.instance;
                if (launchActivity != null) {
                    Uri uri = Uri.parse(tL_premiumGiftOption.bot_url);
                    if (uri.getHost().equals("t.me")) {
                        if (!uri.getPath().startsWith("/$") && !uri.getPath().startsWith("/invoice/")) {
                            launchActivity.setNavigateToPremiumBot(true);
                        } else {
                            launchActivity.setNavigateToPremiumGiftCallback(new Runnable() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda11
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$buyPremiumTier$10();
                                }
                            });
                        }
                    }
                    Browser.openUrl(launchActivity, this.premiumTier.giftOption.bot_url);
                    lambda$new$0();
                    return;
                }
                return;
            }
            if (!BillingController.getInstance().isReady() || this.premiumTier.googlePlayProductDetails == null) {
                return;
            }
            final TLRPC.TL_inputStorePaymentGiftPremium tL_inputStorePaymentGiftPremium = new TLRPC.TL_inputStorePaymentGiftPremium();
            tL_inputStorePaymentGiftPremium.user_id = MessagesController.getInstance(this.currentAccount).getInputUser(user);
            tL_inputStorePaymentGiftPremium.currency = this.premiumTier.googlePlayProductDetails.getOneTimePurchaseOfferDetails().getPriceCurrencyCode();
            tL_inputStorePaymentGiftPremium.amount = (long) ((r0.getPriceAmountMicros() / Math.pow(10.0d, 6.0d)) * Math.pow(10.0d, BillingController.getInstance().getCurrencyExp(tL_inputStorePaymentGiftPremium.currency)));
            BillingController.getInstance().addResultListener(this.premiumTier.giftOption.store_product, new Consumer() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda12
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$buyPremiumTier$12((BillingResult) obj);
                }
            });
            final TLRPC.TL_payments_canPurchaseStore tL_payments_canPurchaseStore = new TLRPC.TL_payments_canPurchaseStore();
            tL_payments_canPurchaseStore.purpose = tL_inputStorePaymentGiftPremium;
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_payments_canPurchaseStore, new RequestDelegate() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda13
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$buyPremiumTier$14(tL_inputStorePaymentGiftPremium, tL_payments_canPurchaseStore, tLObject, tL_error);
                }
            });
        }
    }

    public /* synthetic */ void lambda$buyPremiumTier$4(final TLRPC.User user, Boolean bool, String str) {
        if (bool.booleanValue()) {
            Runnable runnable = this.closeParentSheet;
            if (runnable != null) {
                runnable.run();
            }
            AndroidUtilities.hideKeyboard(this.messageEdit);
            lambda$new$0();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    PremiumPreviewGiftSentBottomSheet.show(new ArrayList(Arrays.asList(user)));
                }
            }, 250L);
        } else if (!TextUtils.isEmpty(str)) {
            BulletinFactory.of(this.topBulletinContainer, this.resourcesProvider).createSimpleBulletin(R.raw.error, LocaleController.formatString(R.string.UnknownErrorCode, str)).show();
        }
        this.button.setLoading(false);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$8 */
    class AnonymousClass8 extends BaseFragment {
        AnonymousClass8() {
        }

        @Override // org.telegram.ui.ActionBar.BaseFragment
        public Activity getParentActivity() {
            Activity ownerActivity = SendGiftSheet.this.getOwnerActivity();
            if (ownerActivity == null) {
                ownerActivity = LaunchActivity.instance;
            }
            return ownerActivity == null ? AndroidUtilities.findActivity(SendGiftSheet.this.getContext()) : ownerActivity;
        }

        @Override // org.telegram.ui.ActionBar.BaseFragment
        public Theme.ResourcesProvider getResourceProvider() {
            return ((BottomSheet) SendGiftSheet.this).resourcesProvider;
        }
    }

    public /* synthetic */ void lambda$buyPremiumTier$6(final TLRPC.User user, Void r6) {
        Runnable runnable = this.closeParentSheet;
        if (runnable != null) {
            runnable.run();
        }
        lambda$new$0();
        NotificationCenter.getInstance(UserConfig.selectedAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.giftsToUserSent, new Object[0]);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                PremiumPreviewGiftSentBottomSheet.show(new ArrayList(Arrays.asList(user)));
            }
        }, 250L);
        MessagesController.getInstance(this.currentAccount).getMainSettings().edit().putBoolean("show_gift_for_" + this.dialogId, true).putBoolean(Calendar.getInstance().get(1) + "show_gift_for_" + this.dialogId, true).apply();
    }

    public /* synthetic */ void lambda$buyPremiumTier$7(TLRPC.TL_error tL_error) {
        BoostDialogs.showToastError(getContext(), tL_error);
    }

    public /* synthetic */ void lambda$buyPremiumTier$9(final TLRPC.User user, Boolean bool, String str) {
        if (bool.booleanValue()) {
            Runnable runnable = this.closeParentSheet;
            if (runnable != null) {
                runnable.run();
            }
            AndroidUtilities.hideKeyboard(this.messageEdit);
            lambda$new$0();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    PremiumPreviewGiftSentBottomSheet.show(new ArrayList(Arrays.asList(user)));
                }
            }, 250L);
        } else if (!TextUtils.isEmpty(str)) {
            BulletinFactory.of(this.topBulletinContainer, this.resourcesProvider).createSimpleBulletin(R.raw.error, LocaleController.formatString(R.string.UnknownErrorCode, str)).show();
        }
        this.button.setLoading(false);
    }

    public /* synthetic */ void lambda$buyPremiumTier$10() {
        onGiftSuccess(false);
    }

    public /* synthetic */ void lambda$buyPremiumTier$12(BillingResult billingResult) {
        if (billingResult.getResponseCode() == 0) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$buyPremiumTier$11();
                }
            });
        }
    }

    public /* synthetic */ void lambda$buyPremiumTier$11() {
        onGiftSuccess(true);
    }

    public /* synthetic */ void lambda$buyPremiumTier$14(final TLRPC.TL_inputStorePaymentGiftPremium tL_inputStorePaymentGiftPremium, final TLRPC.TL_payments_canPurchaseStore tL_payments_canPurchaseStore, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$buyPremiumTier$13(tLObject, tL_inputStorePaymentGiftPremium, tL_error, tL_payments_canPurchaseStore);
            }
        });
    }

    public /* synthetic */ void lambda$buyPremiumTier$13(TLObject tLObject, TLRPC.TL_inputStorePaymentGiftPremium tL_inputStorePaymentGiftPremium, TLRPC.TL_error tL_error, TLRPC.TL_payments_canPurchaseStore tL_payments_canPurchaseStore) {
        if (tLObject instanceof TLRPC.TL_boolTrue) {
            BillingController.getInstance().launchBillingFlow(getBaseFragment().getParentActivity(), AccountInstance.getInstance(this.currentAccount), tL_inputStorePaymentGiftPremium, Collections.singletonList(BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(this.premiumTier.googlePlayProductDetails).build()));
        } else if (tL_error != null) {
            AlertsCreator.processError(this.currentAccount, tL_error, getBaseFragment(), tL_payments_canPurchaseStore, new Object[0]);
        }
    }

    private void onGiftSuccess(boolean z) {
        TLRPC.UserFull userFull = MessagesController.getInstance(this.currentAccount).getUserFull(this.dialogId);
        TLObject userOrChat = MessagesController.getInstance(this.currentAccount).getUserOrChat(this.dialogId);
        int i = 0;
        if (userFull != null && (userOrChat instanceof TLRPC.User)) {
            TLRPC.User user = (TLRPC.User) userOrChat;
            user.premium = true;
            MessagesController.getInstance(this.currentAccount).putUser(user, true);
            NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.userInfoDidLoad, Long.valueOf(user.id), userFull);
        }
        if (getBaseFragment() != null) {
            ArrayList arrayList = new ArrayList(((LaunchActivity) getBaseFragment().getParentActivity()).getActionBarLayout().getFragmentStack());
            INavigationLayout parentLayout = getBaseFragment().getParentLayout();
            int size = arrayList.size();
            ChatActivity chatActivity = null;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                BaseFragment baseFragment = (BaseFragment) obj;
                if (baseFragment instanceof ChatActivity) {
                    chatActivity = (ChatActivity) baseFragment;
                    if (chatActivity.getDialogId() != this.dialogId) {
                        baseFragment.removeSelfFromStack();
                    }
                } else if (baseFragment instanceof ProfileActivity) {
                    if (z && parentLayout.getLastFragment() == baseFragment) {
                        baseFragment.finishFragment();
                    } else {
                        baseFragment.removeSelfFromStack();
                    }
                }
            }
            if (chatActivity == null || chatActivity.getDialogId() != this.dialogId) {
                Bundle bundle = new Bundle();
                bundle.putLong("user_id", this.dialogId);
                parentLayout.presentFragment(new ChatActivity(bundle), true);
            }
        }
        lambda$new$0();
    }

    @Override // org.telegram.ui.Components.BottomSheetWithRecyclerListView
    protected CharSequence getTitle() {
        return LocaleController.getString(this.self ? R.string.Gift2TitleSelf2 : R.string.Gift2Title);
    }

    @Override // org.telegram.ui.Components.BottomSheetWithRecyclerListView
    protected RecyclerListView.SelectionAdapter createAdapter(RecyclerListView recyclerListView) {
        UniversalAdapter universalAdapter = new UniversalAdapter(this.recyclerListView, getContext(), this.currentAccount, 0, true, new Utilities.Callback2() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.fillItems((ArrayList) obj, (UniversalAdapter) obj2);
            }
        }, this.resourcesProvider);
        this.adapter = universalAdapter;
        universalAdapter.setApplyBackground(false);
        return this.adapter;
    }

    /* JADX WARN: Removed duplicated region for block: B:133:0x013f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void fillItems(java.util.ArrayList r11, org.telegram.ui.Components.UniversalAdapter r12) {
        /*
            Method dump skipped, instruction units count: 564
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Gifts.SendGiftSheet.fillItems(java.util.ArrayList, org.telegram.ui.Components.UniversalAdapter):void");
    }

    public /* synthetic */ void lambda$fillItems$15() {
        new StarGiftSheet(getContext(), this.currentAccount, this.dialogId, this.resourcesProvider).openAsLearnMore(this.starGift.id, this.name);
    }

    public /* synthetic */ void lambda$fillItems$16() {
        AuctionJoinSheet.showMoreInfo(getContext(), this.resourcesProvider, this.starGift);
    }

    public /* synthetic */ void lambda$fillItems$17() {
        new StarsIntroActivity.StarsOptionsSheet(getContext(), this.resourcesProvider).show();
    }

    @Override // org.telegram.ui.ActionBar.BottomSheet, android.app.Dialog
    public void show() {
        EditEmojiTextCell editEmojiTextCell = this.messageEdit;
        if (editEmojiTextCell != null) {
            editEmojiTextCell.editTextEmoji.onResume();
        }
        super.show();
    }

    @Override // org.telegram.ui.ActionBar.BottomSheet, android.app.Dialog, android.content.DialogInterface, org.telegram.ui.ActionBar.BaseFragment.AttachedSheet
    /* JADX INFO: renamed from: dismiss */
    public void lambda$new$0() {
        if (this.messageEdit.editTextEmoji.getEmojiPadding() > 0) {
            this.messageEdit.editTextEmoji.hidePopup(true);
            return;
        }
        if (this.messageEdit.editTextEmoji.isKeyboardVisible()) {
            this.messageEdit.editTextEmoji.closeKeyboard();
            return;
        }
        EditEmojiTextCell editEmojiTextCell = this.messageEdit;
        if (editEmojiTextCell != null) {
            editEmojiTextCell.editTextEmoji.onPause();
        }
        if (this.auction != null) {
            GiftAuctionController.getInstance(this.currentAccount).unsubscribeFromGiftAuction(this.auction.giftId, this);
        }
        this.isDismissed = true;
        super.lambda$new$0();
    }

    @Override // org.telegram.ui.ActionBar.BottomSheet, android.app.Dialog
    /* JADX INFO: renamed from: onBackPressed */
    public void lambda$openCrafting$9() {
        if (this.messageEdit.editTextEmoji.getEmojiPadding() > 0) {
            this.messageEdit.editTextEmoji.hidePopup(true);
        } else if (this.messageEdit.editTextEmoji.isKeyboardVisible()) {
            this.messageEdit.editTextEmoji.closeKeyboard();
        } else {
            super.lambda$openCrafting$9();
        }
    }
}
