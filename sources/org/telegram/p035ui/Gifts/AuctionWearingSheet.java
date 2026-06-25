package org.telegram.p035ui.Gifts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import java.util.ArrayList;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.GiftAuctionController;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.BottomSheetWithRecyclerListView;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.RecyclerListView;
import org.telegram.p035ui.Components.UItem;
import org.telegram.p035ui.Components.UniversalAdapter;
import org.telegram.p035ui.Components.chat.ViewPositionWatcher;
import org.telegram.p035ui.Gifts.AuctionBidSheet;
import org.telegram.p035ui.Gifts.GiftSheet;
import org.telegram.p035ui.PremiumFeatureCell;
import org.telegram.p035ui.Stars.StarGiftSheet;
import org.telegram.p035ui.Stories.recorder.ButtonWithCounterView;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.p034tl.TL_stars;

/* JADX INFO: loaded from: classes7.dex */
public class AuctionWearingSheet extends BottomSheetWithRecyclerListView implements GiftAuctionController.OnAuctionUpdateListener {
    private UniversalAdapter adapter;
    private GiftAuctionController.Auction auction;
    private final ButtonWithCounterView buttonView;
    private final GiftSheet.GiftCell giftCell2;
    private final long giftId;
    private final TextView giftNameTextView;
    private final FrameLayout headerContainer;
    private final LinearLayout linearLayout;
    private final TL_stars.StarGift starGift;
    private final StarGiftSheet.TopView topView;

    public static /* synthetic */ void $r8$lambda$1dE0Ue2ZVkL_qNb0B3OVvB86LgY(View view) {
    }

    public static /* synthetic */ void $r8$lambda$8pqS2XKsbnveudOYL0RukDwECTY(View view) {
    }

    public static /* synthetic */ void $r8$lambda$AQeINEFzbZNcynMq9Ua49ICHRDY(View view) {
    }

    public static /* synthetic */ void $r8$lambda$QgMvPEWw3PTzd_LyMdx3dKiSER8(View view) {
    }

    public static /* synthetic */ void $r8$lambda$tFJbMCLOH1FF87uxFHNVccD1TdI(View view) {
    }

    public static /* synthetic */ void $r8$lambda$xpPhtK2alAo5v1jQQnMtJfcDSuw(View view) {
    }

    private void updateTable(boolean z) {
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0202  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x04fb  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x04fe  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x051f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AuctionWearingSheet(final android.content.Context r35, final org.telegram.ui.ActionBar.Theme.ResourcesProvider r36, final long r37, final org.telegram.tgnet.tl.TL_stars.StarGift r39, final java.util.ArrayList<org.telegram.tgnet.tl.TL_stars.StarGiftAttribute> r40, final java.lang.Runnable r41, boolean r42) {
        /*
            Method dump skipped, instruction units count: 1512
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Gifts.AuctionWearingSheet.<init>(android.content.Context, org.telegram.ui.ActionBar.Theme$ResourcesProvider, long, org.telegram.tgnet.tl.TL_stars$StarGift, java.util.ArrayList, java.lang.Runnable, boolean):void");
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.AuctionWearingSheet$1 */
    public class C56571 extends FrameLayout {
        RectF rectF = new RectF();
        RectF rectF2 = new RectF();

        public C56571(Context context) {
            super(context);
            this.rectF = new RectF();
            this.rectF2 = new RectF();
        }

        @Override // android.view.ViewGroup, android.view.View
        public void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            if (ViewPositionWatcher.computeRectInParent(AuctionWearingSheet.this.topView.imageLayout, this, this.rectF) && ViewPositionWatcher.computeRectInParent(AuctionWearingSheet.this.giftNameTextView, this, this.rectF2)) {
                float fM1036dp = this.rectF2.right - AndroidUtilities.m1036dp(32.0f);
                float fCenterY = this.rectF2.centerY() - AndroidUtilities.m1036dp(16.0f);
                if (this.rectF.isEmpty()) {
                    return;
                }
                canvas.save();
                canvas.translate(fM1036dp, fCenterY);
                canvas.scale(AndroidUtilities.m1036dp(32.0f) / this.rectF.width(), AndroidUtilities.m1036dp(32.0f) / this.rectF.height());
                AuctionWearingSheet.this.topView.imageLayout.draw(canvas);
                canvas.restore();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.AuctionWearingSheet$2 */
    public class C56582 extends StarGiftSheet.TopView {
        Path path = new Path();

        /* JADX INFO: renamed from: r */
        float[] f1727r = new float[8];
        final /* synthetic */ int val$topHeightDp;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C56582(Context context, Theme.ResourcesProvider resourcesProvider, Runnable runnable, View.OnClickListener onClickListener, View.OnClickListener onClickListener2, View.OnClickListener onClickListener3, View.OnClickListener onClickListener4, View.OnClickListener onClickListener5, View.OnClickListener onClickListener6, View.OnClickListener onClickListener7, int i) {
            super(context, resourcesProvider, runnable, onClickListener, onClickListener2, onClickListener3, onClickListener4, onClickListener5, onClickListener6, onClickListener7);
            i = i;
            this.path = new Path();
            this.f1727r = new float[8];
        }

        @Override // org.telegram.ui.Stars.StarGiftSheet.TopView
        public float getRealHeight() {
            return AndroidUtilities.m1036dp(i);
        }

        @Override // org.telegram.ui.Stars.StarGiftSheet.TopView
        public int getFinalHeight() {
            return AndroidUtilities.m1036dp(i);
        }

        @Override // android.view.View
        public void onSizeChanged(int i, int i2, int i3, int i4) {
            super.onSizeChanged(i, i2, i3, i4);
            float[] fArr = this.f1727r;
            float fM1036dp = AndroidUtilities.m1036dp(12.0f);
            fArr[3] = fM1036dp;
            fArr[2] = fM1036dp;
            fArr[1] = fM1036dp;
            fArr[0] = fM1036dp;
            this.path.rewind();
            this.path.addRoundRect(0.0f, 0.0f, i, i2, this.f1727r, Path.Direction.CW);
        }

        @Override // android.view.ViewGroup
        public boolean drawChild(Canvas canvas, View view, long j) {
            if (view == this.imageLayout) {
                return true;
            }
            return super.drawChild(canvas, view, j);
        }

        @Override // org.telegram.ui.Stars.StarGiftSheet.TopView, android.view.ViewGroup, android.view.View
        public void dispatchDraw(Canvas canvas) {
            canvas.save();
            canvas.clipPath(this.path);
            super.dispatchDraw(canvas);
            canvas.restore();
        }

        @Override // org.telegram.ui.Stars.StarGiftSheet.TopView
        public void updateButtonsBackgrounds(int i) {
            super.updateButtonsBackgrounds(i);
            AuctionWearingSheet.this.giftCell2.setRibbonColor(i);
        }

        @Override // android.view.View
        public void invalidate() {
            super.invalidate();
            if (AuctionWearingSheet.this.giftCell2 != null) {
                AuctionWearingSheet.this.giftCell2.invalidate();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.AuctionWearingSheet$3 */
    public class C56593 extends GiftSheet.GiftCell {
        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        public C56593(Context context, int i, Theme.ResourcesProvider resourcesProvider) {
            super(context, i, resourcesProvider);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.AuctionWearingSheet$4 */
    public class C56604 extends GiftSheet.GiftCell {
        RectF rectF = new RectF();
        RectF rectF2 = new RectF();
        Path path = new Path();

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        public C56604(Context context, int i, Theme.ResourcesProvider resourcesProvider) {
            super(context, i, resourcesProvider);
            this.rectF = new RectF();
            this.rectF2 = new RectF();
            this.path = new Path();
        }

        @Override // android.view.View
        public void onSizeChanged(int i, int i2, int i3, int i4) {
            super.onSizeChanged(i, i2, i3, i4);
            this.path.rewind();
            this.rectF.set(0.0f, 0.0f, i, i2);
            this.rectF.inset(AndroidUtilities.m1036dp(3.33f), AndroidUtilities.m1036dp(4.0f));
            this.path.addRoundRect(this.rectF, AndroidUtilities.m1036dp(11.0f), AndroidUtilities.m1036dp(11.0f), Path.Direction.CW);
        }

        @Override // android.view.ViewGroup
        public boolean drawChild(Canvas canvas, View view, long j) {
            boolean zDrawChild = super.drawChild(canvas, view, j);
            if (view == this.card) {
                if (!ViewPositionWatcher.computeRectInParent(AuctionWearingSheet.this.topView.imageLayout, AuctionWearingSheet.this.headerContainer, this.rectF) || !ViewPositionWatcher.computeRectInParent(this.card, this, this.rectF2)) {
                    return true;
                }
                float fCenterX = this.rectF2.centerX() - AndroidUtilities.m1036dp(40.0f);
                float fCenterY = this.rectF2.centerY() - AndroidUtilities.m1036dp(40.0f);
                if (!this.rectF.isEmpty()) {
                    canvas.save();
                    canvas.clipPath(this.path);
                    canvas.scale(0.6f, 0.6f, this.rectF2.centerX(), this.rectF2.centerY());
                    canvas.translate(this.rectF2.centerX() - (AuctionWearingSheet.this.topView.getWidth() / 2.0f), this.rectF2.centerY() - (AuctionWearingSheet.this.topView.getHeight() / 2.0f));
                    AuctionWearingSheet.this.topView.drawBackground(canvas, AuctionWearingSheet.this.topView.getWidth() / 2.0f, AndroidUtilities.m1036dp(104.0f), AuctionWearingSheet.this.topView.getWidth(), AuctionWearingSheet.this.topView.getHeight());
                    AuctionWearingSheet.this.topView.drawPattern(canvas, AuctionWearingSheet.this.topView.getWidth() / 2.0f, AndroidUtilities.m1036dp(104.0f), AuctionWearingSheet.this.topView.getWidth(), AuctionWearingSheet.this.topView.getHeight());
                    canvas.restore();
                    canvas.save();
                    canvas.translate(fCenterX, fCenterY);
                    canvas.scale(AndroidUtilities.m1036dp(80.0f) / this.rectF.width(), AndroidUtilities.m1036dp(80.0f) / this.rectF.height());
                    AuctionWearingSheet.this.topView.imageLayout.draw(canvas);
                    canvas.restore();
                }
            }
            return zDrawChild;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.AuctionWearingSheet$5 */
    public class C56615 extends View {
        final /* synthetic */ float val$limitedProgress;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C56615(Context context, float f) {
            super(context);
            f = f;
        }

        @Override // android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec((int) (View.MeasureSpec.getSize(i) * f), TLObject.FLAG_30), i2);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Gifts.AuctionWearingSheet$6 */
    public class C56626 extends FrameLayout {
        final /* synthetic */ float val$limitedProgress;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C56626(Context context, float f) {
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

    public /* synthetic */ void lambda$new$8(View view) {
        lambda$new$0();
    }

    public /* synthetic */ void lambda$new$9(long j, Context context, Theme.ResourcesProvider resourcesProvider, Runnable runnable, View view) {
        AuctionBidSheet auctionBidSheet = new AuctionBidSheet(context, resourcesProvider, new AuctionBidSheet.Params(j, true, null), this.auction);
        auctionBidSheet.show();
        auctionBidSheet.setCloseParentSheet(runnable);
        lambda$new$0();
    }

    @Override // org.telegram.messenger.GiftAuctionController.OnAuctionUpdateListener
    public void onUpdate(GiftAuctionController.Auction auction) {
        this.auction = auction;
        updateTable(true);
    }

    @Override // org.telegram.p035ui.ActionBar.BottomSheet, android.app.Dialog, android.content.DialogInterface, org.telegram.ui.ActionBar.BaseFragment.AttachedSheet
    /* JADX INFO: renamed from: dismiss */
    public void lambda$new$0() {
        GiftAuctionController.getInstance(this.currentAccount).unsubscribeFromGiftAuction(this.giftId, this);
        super.lambda$new$0();
    }

    @Override // org.telegram.p035ui.Components.BottomSheetWithRecyclerListView
    public CharSequence getTitle() {
        return _UrlKt.FRAGMENT_ENCODE_SET;
    }

    @Override // org.telegram.p035ui.Components.BottomSheetWithRecyclerListView
    public RecyclerListView.SelectionAdapter createAdapter(RecyclerListView recyclerListView) {
        UniversalAdapter universalAdapter = new UniversalAdapter(this.recyclerListView, getContext(), this.currentAccount, 0, true, new Utilities.Callback2() { // from class: org.telegram.ui.Gifts.AuctionWearingSheet$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.fillItems((ArrayList) obj, (UniversalAdapter) obj2);
            }
        }, this.resourcesProvider);
        this.adapter = universalAdapter;
        universalAdapter.setApplyBackground(false);
        return this.adapter;
    }

    public void fillItems(ArrayList<UItem> arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asCustom(-1, this.linearLayout));
    }

    private int getBackgroundColor() {
        return ColorUtils.blendARGB(getThemedColor(Theme.key_dialogBackgroundGray), getThemedColor(Theme.key_dialogBackground), 0.1f);
    }

    private static void showWearingMoreInfo(Context context, Theme.ResourcesProvider resourcesProvider, LinearLayout linearLayout, TL_stars.StarGift starGift) {
        if (context == null || starGift == null) {
            return;
        }
        TextView textView = new TextView(context);
        textView.setTypeface(AndroidUtilities.bold());
        textView.setGravity(17);
        textView.setText(LocaleController.formatString(C2797R.string.GiftAuctionWearInfoHeader, starGift.title));
        textView.setTextSize(1, 20.0f);
        int i = Theme.key_windowBackgroundWhiteBlackText;
        textView.setTextColor(Theme.getColor(i, resourcesProvider));
        linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2, 17, 20, 14, 20, 6));
        TextView textView2 = new TextView(context);
        textView2.setGravity(17);
        textView2.setText(LocaleController.getString(C2797R.string.GiftAuctionWearInfoText));
        textView2.setTextSize(1, 14.0f);
        textView2.setTextColor(Theme.getColor(i, resourcesProvider));
        linearLayout.addView(textView2, LayoutHelper.createLinear(-1, -2, 17, 20, 0, 20, 16));
        PremiumFeatureCell premiumFeatureCell = new PremiumFeatureCell(context, resourcesProvider);
        premiumFeatureCell.title.setText(LocaleController.getString(C2797R.string.GiftAuctionWearInfo1Header));
        premiumFeatureCell.description.setText(LocaleController.getString(C2797R.string.GiftAuctionWearInfo1Text));
        premiumFeatureCell.nextIcon.setVisibility(8);
        premiumFeatureCell.imageView.setImageResource(C2797R.drawable.msg_emoji_gem);
        premiumFeatureCell.imageView.setColorFilter(Theme.getColor(i, resourcesProvider));
        linearLayout.addView(premiumFeatureCell, LayoutHelper.createLinear(-1, -2, 6.0f, 0.0f, 6.0f, -2.0f));
        PremiumFeatureCell premiumFeatureCell2 = new PremiumFeatureCell(context, resourcesProvider);
        premiumFeatureCell2.title.setText(LocaleController.getString(C2797R.string.GiftAuctionWearInfo2Header));
        premiumFeatureCell2.description.setText(LocaleController.getString(C2797R.string.GiftAuctionWearInfo2Text));
        premiumFeatureCell2.nextIcon.setVisibility(8);
        premiumFeatureCell2.imageView.setImageResource(C2797R.drawable.menu_feature_cover_24);
        premiumFeatureCell2.imageView.setColorFilter(Theme.getColor(i, resourcesProvider));
        linearLayout.addView(premiumFeatureCell2, LayoutHelper.createLinear(-1, -2, 6.0f, 0.0f, 6.0f, -2.0f));
        PremiumFeatureCell premiumFeatureCell3 = new PremiumFeatureCell(context, resourcesProvider);
        premiumFeatureCell3.title.setText(LocaleController.getString(C2797R.string.GiftAuctionWearInfo3Header));
        premiumFeatureCell3.description.setText(LocaleController.getString(C2797R.string.GiftAuctionWearInfo3Text));
        premiumFeatureCell3.nextIcon.setVisibility(8);
        premiumFeatureCell3.imageView.setImageResource(C2797R.drawable.menu_verification);
        premiumFeatureCell3.imageView.setColorFilter(Theme.getColor(i, resourcesProvider));
        linearLayout.addView(premiumFeatureCell3, LayoutHelper.createLinear(-1, -2, 6.0f, 0.0f, 6.0f, 14.0f));
    }
}
