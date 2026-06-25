package org.telegram.p035ui.Gifts;

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
import org.telegram.messenger.C2797R;
import org.telegram.messenger.GiftAuctionController;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.BottomSheet;
import org.telegram.p035ui.ActionBar.INavigationLayout;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Cells.ChatActionCell;
import org.telegram.p035ui.Cells.EditEmojiTextCell;
import org.telegram.p035ui.ChatActivity;
import org.telegram.p035ui.Components.AlertsCreator;
import org.telegram.p035ui.Components.BottomSheetWithRecyclerListView;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.p035ui.Components.ColoredImageSpan;
import org.telegram.p035ui.Components.Premium.GiftPremiumBottomSheet$GiftTier;
import org.telegram.p035ui.Components.Premium.boosts.BoostDialogs;
import org.telegram.p035ui.Components.Premium.boosts.BoostRepository;
import org.telegram.p035ui.Components.Premium.boosts.PremiumPreviewGiftSentBottomSheet;
import org.telegram.p035ui.Components.RecyclerListView;
import org.telegram.p035ui.Components.SizeNotifierFrameLayout;
import org.telegram.p035ui.Components.UItem;
import org.telegram.p035ui.Components.UniversalAdapter;
import org.telegram.p035ui.Components.blur3.drawable.BlurredBackgroundDrawable;
import org.telegram.p035ui.Components.chat.ViewPositionWatcher;
import org.telegram.p035ui.Gifts.AuctionBidSheet;
import org.telegram.p035ui.LaunchActivity;
import org.telegram.p035ui.ProfileActivity;
import org.telegram.p035ui.Stars.StarGiftSheet;
import org.telegram.p035ui.Stars.StarsController;
import org.telegram.p035ui.Stars.StarsIntroActivity;
import org.telegram.p035ui.Stories.recorder.ButtonWithCounterView;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_stars;

/* JADX INFO: loaded from: classes7.dex */
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
    private SendGiftSheet(final android.content.Context r32, int r33, final org.telegram.tgnet.tl.TL_stars.StarGift r34, final org.telegram.p035ui.Components.Premium.GiftPremiumBottomSheet$GiftTier r35, final long r36, final java.lang.Runnable r38, final boolean r39, final boolean r40) {
        /*
            Method dump skipped, instruction units count: 1537
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Gifts.SendGiftSheet.<init>(android.content.Context, int, org.telegram.tgnet.tl.TL_stars$StarGift, org.telegram.ui.Components.Premium.GiftPremiumBottomSheet$GiftTier, long, java.lang.Runnable, boolean, boolean):void");
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$1 */
    public class C57311 implements ChatActionCell.ChatActionCellDelegate {
        public C57311() {
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$2 */
    public class C57322 extends SizeNotifierFrameLayout {
        int maxHeight = -1;

        @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout
        public boolean isActionBarVisible() {
            return false;
        }

        @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout
        public boolean isStatusBarVisible() {
            return false;
        }

        @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout
        public boolean useRootView() {
            return false;
        }

        public C57322(Context context) {
            super(context);
            this.maxHeight = -1;
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
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
        public boolean drawChild(Canvas canvas, View view, long j) {
            if (view == this.backgroundView) {
                return true;
            }
            return super.drawChild(canvas, view, j);
        }

        @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            SendGiftSheet.this.chatLinearLayout.setTranslationY(((i4 - i2) - SendGiftSheet.this.chatLinearLayout.getMeasuredHeight()) / 2.0f);
            SendGiftSheet.this.actionCell.setVisiblePart(SendGiftSheet.this.chatLinearLayout.getY() + SendGiftSheet.this.actionCell.getY(), getBackgroundSizeY());
        }

        @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout
        public void onBackgroundViewInvalidate() {
            super.onBackgroundViewInvalidate();
            ((BottomSheetWithRecyclerListView) SendGiftSheet.this).recyclerListView.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$3 */
    public class C57333 extends EditEmojiTextCell {
        final /* synthetic */ int val$currentAccount;
        final /* synthetic */ BlurredBackgroundDrawable val$msgDrawable;

        @Override // org.telegram.p035ui.Cells.EditEmojiTextCell
        public void onFocusChanged(boolean z) {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C57333(Context context, SizeNotifierFrameLayout sizeNotifierFrameLayout, String str, boolean z, int i, int i2, Theme.ResourcesProvider resourcesProvider, BlurredBackgroundDrawable blurredBackgroundDrawable, int i3) {
            super(context, sizeNotifierFrameLayout, str, z, i, i2, resourcesProvider);
            blurredBackgroundDrawable = blurredBackgroundDrawable;
            i = i3;
        }

        @Override // org.telegram.p035ui.Cells.EditEmojiTextCell, android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            setPadding(AndroidUtilities.m1036dp(16.0f), 0, AndroidUtilities.m1036dp(12.0f), 0);
            super.onMeasure(i, i2);
        }

        @Override // android.view.ViewGroup, android.view.View
        public void dispatchDraw(Canvas canvas) {
            blurredBackgroundDrawable.setBounds(AndroidUtilities.m1036dp(10.0f), 0, getMeasuredWidth() - AndroidUtilities.m1036dp(10.0f), getMeasuredHeight());
            blurredBackgroundDrawable.draw(canvas);
            super.dispatchDraw(canvas);
        }

        @Override // org.telegram.p035ui.Cells.EditEmojiTextCell
        public void onTextChanged(CharSequence charSequence) {
            TLRPC.TL_textWithEntities tL_textWithEntities;
            boolean z = SendGiftSheet.this.action instanceof TLRPC.TL_messageActionStarGift;
            SendGiftSheet sendGiftSheet = SendGiftSheet.this;
            if (z) {
                TLRPC.TL_messageActionStarGift tL_messageActionStarGift = (TLRPC.TL_messageActionStarGift) sendGiftSheet.action;
                tL_textWithEntities = new TLRPC.TL_textWithEntities();
                tL_messageActionStarGift.message = tL_textWithEntities;
            } else {
                boolean z2 = sendGiftSheet.action instanceof TLRPC.TL_messageActionGiftCode;
                SendGiftSheet sendGiftSheet2 = SendGiftSheet.this;
                if (z2) {
                    ((TLRPC.TL_messageActionGiftCode) sendGiftSheet2.action).flags |= 16;
                    TLRPC.TL_messageActionGiftCode tL_messageActionGiftCode = (TLRPC.TL_messageActionGiftCode) SendGiftSheet.this.action;
                    tL_textWithEntities = new TLRPC.TL_textWithEntities();
                    tL_messageActionGiftCode.message = tL_textWithEntities;
                } else {
                    if (!(sendGiftSheet2.action instanceof TLRPC.TL_messageActionGiftPremium)) {
                        return;
                    }
                    ((TLRPC.TL_messageActionGiftPremium) SendGiftSheet.this.action).flags |= 16;
                    TLRPC.TL_messageActionGiftPremium tL_messageActionGiftPremium = (TLRPC.TL_messageActionGiftPremium) SendGiftSheet.this.action;
                    tL_textWithEntities = new TLRPC.TL_textWithEntities();
                    tL_messageActionGiftPremium.message = tL_textWithEntities;
                }
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
    public class C57344 extends DefaultItemAnimator {
        @Override // androidx.recyclerview.widget.DefaultItemAnimator
        public float animateByScale(View view) {
            return 0.3f;
        }

        public C57344() {
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$5 */
    public class C57355 extends View {
        final /* synthetic */ float val$limitedProgress;
        final /* synthetic */ TL_stars.StarGift val$starGift;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C57355(Context context, TL_stars.StarGift starGift, float f) {
            super(context);
            starGift = starGift;
            f = f;
        }

        @Override // android.view.View
        public void onMeasure(int i, int i2) {
            if (starGift == null) {
                super.onMeasure(i, i2);
            } else {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec((int) (View.MeasureSpec.getSize(i) * f), TLObject.FLAG_30), i2);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$6 */
    public class C57366 extends FrameLayout {
        final /* synthetic */ float val$limitedProgress;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C57366(Context context, float f) {
            super(context);
            f = f;
        }

        @Override // android.view.ViewGroup, android.view.View
        public void dispatchDraw(Canvas canvas) {
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
        int emojiPadding = this.messageEdit.editTextEmoji.getEmojiPadding();
        EditEmojiTextCell editEmojiTextCell = this.messageEdit;
        if (emojiPadding > 0) {
            editEmojiTextCell.editTextEmoji.hidePopup(true);
        } else if (editEmojiTextCell.editTextEmoji.isKeyboardVisible()) {
            this.messageEdit.editTextEmoji.closeKeyboard();
        }
        if (starGift != null) {
            buyStarGift();
        } else {
            buyPremiumTier();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$7 */
    public class C57377 extends RecyclerView.ItemDecoration {

        /* JADX INFO: renamed from: p */
        final PointF f1730p = new PointF();

        public C57377() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            float fMax;
            float f;
            float height = recyclerView.getHeight();
            if (ViewPositionWatcher.computeCoordinatesInParent(SendGiftSheet.this.chatView, ((BottomSheetWithRecyclerListView) SendGiftSheet.this).recyclerListView, this.f1730p)) {
                PointF pointF = this.f1730p;
                f = pointF.x;
                height = Math.min(height, pointF.y);
                fMax = Math.max(0.0f, this.f1730p.y + SendGiftSheet.this.chatView.getMeasuredHeight());
            } else {
                fMax = 0.0f;
                f = 0.0f;
            }
            if (ViewPositionWatcher.computeCoordinatesInParent(SendGiftSheet.this.messageEdit, ((BottomSheetWithRecyclerListView) SendGiftSheet.this).recyclerListView, this.f1730p)) {
                height = Math.min(height, this.f1730p.y);
                fMax = Math.max(fMax, this.f1730p.y + SendGiftSheet.this.messageEdit.getMeasuredHeight() + AndroidUtilities.m1036dp(12.0f));
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
        int i2 = item.f1708id;
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
            boolean zIsUpcoming = this.auction.isUpcoming(currentTime);
            GiftAuctionController.Auction auction = this.auction;
            if (zIsUpcoming) {
                int i = auction.gift.auction_start_date - currentTime;
                this.button.setText(LocaleController.getString(C2797R.string.Gift2AuctionPlaceAEarlyBid), z);
                this.button.setSubText(LocaleController.formatString(C2797R.string.Gift2AuctionStartsIn, LocaleController.formatTTLString(i)), z);
                return;
            }
            TL_stars.TL_starGiftAuctionState tL_starGiftAuctionState = auction.auctionStateActive;
            if (tL_starGiftAuctionState != null) {
                int i2 = tL_starGiftAuctionState.end_date - currentTime;
                this.button.setText(LocaleController.getString(C2797R.string.Gift2AuctionPlaceABid), z);
                this.button.setSubText(LocaleController.formatString(C2797R.string.Gift2AuctionTimeLeft, LocaleController.formatTTLString(i2)), z);
                return;
            } else {
                this.button.setText(LocaleController.getString(C2797R.string.Gift2AuctionPlaceABid), z);
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
            boolean z2 = this.useStars;
            ButtonWithCounterView buttonWithCounterView = this.button;
            if (z2) {
                buttonWithCounterView.setText(StarsIntroActivity.replaceStars(LocaleController.formatString(C2797R.string.Gift2SendPremiumStars, LocaleController.formatNumber(giftPremiumBottomSheet$GiftTier.getStarsPrice(), ',')), 1.0f, this.cachedStarSpan), z);
                this.cachedStarSpan[0].spaceScaleX = 0.85f;
            } else {
                buttonWithCounterView.setText(new SpannableStringBuilder(LocaleController.formatString(C2797R.string.Gift2SendPremium, this.premiumTier.getFormattedPrice())), z);
            }
            this.button.setSubText(null, z);
        }
    }

    @Override // org.telegram.messenger.GiftAuctionController.OnAuctionUpdateListener
    public void onUpdate(GiftAuctionController.Auction auction) {
        this.auction = auction;
    }

    public BulletinFactory getParentBulletinFactory() {
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        if (safeLastFragment == null) {
            return null;
        }
        return BulletinFactory.m1143of(safeLastFragment);
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

    @Override // org.telegram.p035ui.ActionBar.BottomSheet
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
                TLRPC.TL_premiumGiftOption tL_premiumGiftOption = giftPremiumBottomSheet$GiftTier.giftOption;
                if (tL_premiumGiftOption == null) {
                    this.button.setLoading(false);
                    return;
                }
                starsOption = tL_premiumGiftOption;
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
                    public C57388() {
                    }

                    @Override // org.telegram.p035ui.ActionBar.BaseFragment
                    public Activity getParentActivity() {
                        Activity ownerActivity = SendGiftSheet.this.getOwnerActivity();
                        if (ownerActivity == null) {
                            ownerActivity = LaunchActivity.instance;
                        }
                        return ownerActivity == null ? AndroidUtilities.findActivity(SendGiftSheet.this.getContext()) : ownerActivity;
                    }

                    @Override // org.telegram.p035ui.ActionBar.BaseFragment
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
            TLRPC.TL_premiumGiftOption tL_premiumGiftOption2 = (TLRPC.TL_premiumGiftOption) starsOption;
            if ("XTR".equalsIgnoreCase(tL_premiumGiftOption2.currency)) {
                StarsController.getInstance(this.currentAccount).buyPremiumGift(this.dialogId, tL_premiumGiftOption2, getMessage(), new Utilities.Callback2() { // from class: org.telegram.ui.Gifts.SendGiftSheet$$ExternalSyntheticLambda10
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
                    Uri uri = Uri.parse(tL_premiumGiftOption2.bot_url);
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
            BulletinFactory.m1142of(this.topBulletinContainer, this.resourcesProvider).createSimpleBulletin(C2797R.raw.error, LocaleController.formatString(C2797R.string.UnknownErrorCode, str)).show();
        }
        this.button.setLoading(false);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.SendGiftSheet$8 */
    public class C57388 extends BaseFragment {
        public C57388() {
        }

        @Override // org.telegram.p035ui.ActionBar.BaseFragment
        public Activity getParentActivity() {
            Activity ownerActivity = SendGiftSheet.this.getOwnerActivity();
            if (ownerActivity == null) {
                ownerActivity = LaunchActivity.instance;
            }
            return ownerActivity == null ? AndroidUtilities.findActivity(SendGiftSheet.this.getContext()) : ownerActivity;
        }

        @Override // org.telegram.p035ui.ActionBar.BaseFragment
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
            BulletinFactory.m1142of(this.topBulletinContainer, this.resourcesProvider).createSimpleBulletin(C2797R.raw.error, LocaleController.formatString(C2797R.string.UnknownErrorCode, str)).show();
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
        if (userFull != null && (userOrChat instanceof TLRPC.User)) {
            TLRPC.User user = (TLRPC.User) userOrChat;
            user.premium = true;
            MessagesController.getInstance(this.currentAccount).putUser(user, true);
            NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.userInfoDidLoad, Long.valueOf(user.f1407id), userFull);
        }
        if (getBaseFragment() != null) {
            ArrayList arrayList = new ArrayList(((LaunchActivity) getBaseFragment().getParentActivity()).getActionBarLayout().getFragmentStack());
            INavigationLayout parentLayout = getBaseFragment().getParentLayout();
            int size = arrayList.size();
            ChatActivity chatActivity = null;
            int i = 0;
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

    @Override // org.telegram.p035ui.Components.BottomSheetWithRecyclerListView
    public CharSequence getTitle() {
        return LocaleController.getString(this.self ? C2797R.string.Gift2TitleSelf2 : C2797R.string.Gift2Title);
    }

    @Override // org.telegram.p035ui.Components.BottomSheetWithRecyclerListView
    public RecyclerListView.SelectionAdapter createAdapter(RecyclerListView recyclerListView) {
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

    /* JADX WARN: Removed duplicated region for block: B:131:0x013d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void fillItems(java.util.ArrayList<org.telegram.p035ui.Components.UItem> r11, org.telegram.p035ui.Components.UniversalAdapter r12) {
        /*
            Method dump skipped, instruction units count: 562
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Gifts.SendGiftSheet.fillItems(java.util.ArrayList, org.telegram.ui.Components.UniversalAdapter):void");
    }

    public /* synthetic */ void lambda$fillItems$15() {
        new StarGiftSheet(getContext(), this.currentAccount, this.dialogId, this.resourcesProvider).openAsLearnMore(this.starGift.f1443id, this.name);
    }

    public /* synthetic */ void lambda$fillItems$16() {
        AuctionJoinSheet.showMoreInfo(getContext(), this.resourcesProvider, this.starGift);
    }

    public /* synthetic */ void lambda$fillItems$17() {
        new StarsIntroActivity.StarsOptionsSheet(getContext(), this.resourcesProvider).show();
    }

    @Override // org.telegram.p035ui.ActionBar.BottomSheet, android.app.Dialog
    public void show() {
        EditEmojiTextCell editEmojiTextCell = this.messageEdit;
        if (editEmojiTextCell != null) {
            editEmojiTextCell.editTextEmoji.onResume();
        }
        super.show();
    }

    @Override // org.telegram.p035ui.ActionBar.BottomSheet, android.app.Dialog, android.content.DialogInterface, org.telegram.ui.ActionBar.BaseFragment.AttachedSheet
    /* JADX INFO: renamed from: dismiss */
    public void lambda$new$0() {
        int emojiPadding = this.messageEdit.editTextEmoji.getEmojiPadding();
        EditEmojiTextCell editEmojiTextCell = this.messageEdit;
        if (emojiPadding > 0) {
            editEmojiTextCell.editTextEmoji.hidePopup(true);
            return;
        }
        boolean zIsKeyboardVisible = editEmojiTextCell.editTextEmoji.isKeyboardVisible();
        EditEmojiTextCell editEmojiTextCell2 = this.messageEdit;
        if (zIsKeyboardVisible) {
            editEmojiTextCell2.editTextEmoji.closeKeyboard();
            return;
        }
        if (editEmojiTextCell2 != null) {
            editEmojiTextCell2.editTextEmoji.onPause();
        }
        if (this.auction != null) {
            GiftAuctionController.getInstance(this.currentAccount).unsubscribeFromGiftAuction(this.auction.giftId, this);
        }
        this.isDismissed = true;
        super.lambda$new$0();
    }

    @Override // org.telegram.p035ui.ActionBar.BottomSheet, android.app.Dialog
    /* JADX INFO: renamed from: onBackPressed */
    public void lambda$openCrafting$8() {
        int emojiPadding = this.messageEdit.editTextEmoji.getEmojiPadding();
        EditEmojiTextCell editEmojiTextCell = this.messageEdit;
        if (emojiPadding > 0) {
            editEmojiTextCell.editTextEmoji.hidePopup(true);
        } else if (editEmojiTextCell.editTextEmoji.isKeyboardVisible()) {
            this.messageEdit.editTextEmoji.closeKeyboard();
        } else {
            super.lambda$openCrafting$8();
        }
    }
}
