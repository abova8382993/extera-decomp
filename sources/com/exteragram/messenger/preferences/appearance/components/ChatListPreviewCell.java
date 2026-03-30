package com.exteragram.messenger.preferences.appearance.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import com.exteragram.messenger.preferences.components.CustomPreferenceCell;
import com.exteragram.messenger.preferences.components.PreviewBackgroundDrawable;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2888R;
import org.telegram.p029ui.ActionBar.ActionBar;
import org.telegram.p029ui.ActionBar.Theme;
import org.telegram.p029ui.Components.AnimatedEmojiDrawable;
import org.telegram.p029ui.Components.LayoutHelper;
import p022j$.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class ChatListPreviewCell extends FrameLayout implements CustomPreferenceCell {
    private final ActionBar actionBar;
    private Drawable premiumStar;
    private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable statusDrawable;

    public ChatListPreviewCell(Context context) {
        super(context);
        setWillNotDraw(false);
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(null, AndroidUtilities.m1124dp(26.0f));
        this.statusDrawable = swapAnimatedEmojiDrawable;
        swapAnimatedEmojiDrawable.center = true;
        ActionBar actionBar = new ActionBar(context);
        this.actionBar = actionBar;
        actionBar.setItemsColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText), false);
        actionBar.setOccupyStatusBar(false);
        actionBar.createMenu().addItem(0, C2888R.drawable.ic_ab_other);
        actionBar.setBackground(new PreviewBackgroundDrawable());
        actionBar.setSupportsHolidayImage(true);
        addView(actionBar, LayoutHelper.createFrame(-1, -2.0f, 17, 21.0f, 21.0f, 21.0f, 21.0f));
        updateStatus(false);
    }

    public void updateCentered(boolean z) {
        this.actionBar.refreshTitlePosition(z);
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        ActionBar actionBar = this.actionBar;
        if (actionBar != null) {
            actionBar.invalidate();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0074  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateStatus(boolean r5) {
        /*
            r4 = this;
            org.telegram.ui.ActionBar.ActionBar r0 = r4.actionBar
            if (r0 != 0) goto L5
            return
        L5:
            int r0 = org.telegram.messenger.UserConfig.selectedAccount
            org.telegram.messenger.UserConfig r0 = org.telegram.messenger.UserConfig.getInstance(r0)
            org.telegram.tgnet.TLRPC$User r0 = r0.getCurrentUser()
            if (r0 == 0) goto L74
            boolean r1 = com.exteragram.messenger.ExteraConfig.hideActionBarStatus
            if (r1 != 0) goto L74
            java.lang.Long r1 = org.telegram.messenger.UserObject.getEmojiStatusDocumentId(r0)
            if (r1 == 0) goto L36
            org.telegram.ui.Components.AnimatedEmojiDrawable$SwapAnimatedEmojiDrawable r0 = r4.statusDrawable
            long r1 = r1.longValue()
            r0.set(r1, r5)
            org.telegram.ui.Components.AnimatedEmojiDrawable$SwapAnimatedEmojiDrawable r0 = r4.statusDrawable
            int r1 = org.telegram.p029ui.ActionBar.Theme.key_profile_verifiedBackground
            int r1 = org.telegram.p029ui.ActionBar.Theme.getColor(r1)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r0.setColor(r1)
            org.telegram.ui.Components.AnimatedEmojiDrawable$SwapAnimatedEmojiDrawable r0 = r4.statusDrawable
            goto L75
        L36:
            boolean r0 = r0.premium
            if (r0 == 0) goto L74
            android.graphics.drawable.Drawable r0 = r4.premiumStar
            if (r0 != 0) goto L71
            android.content.Context r0 = r4.getContext()
            android.content.res.Resources r0 = r0.getResources()
            int r1 = org.telegram.messenger.C2888R.drawable.msg_premium_liststar
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r1)
            android.graphics.drawable.Drawable r0 = r0.mutate()
            android.graphics.PorterDuffColorFilter r1 = new android.graphics.PorterDuffColorFilter
            int r2 = org.telegram.p029ui.ActionBar.Theme.key_profile_verifiedBackground
            int r2 = org.telegram.p029ui.ActionBar.Theme.getColor(r2)
            android.graphics.PorterDuff$Mode r3 = android.graphics.PorterDuff.Mode.MULTIPLY
            r1.<init>(r2, r3)
            r0.setColorFilter(r1)
            com.exteragram.messenger.preferences.appearance.components.ChatListPreviewCell$1 r1 = new com.exteragram.messenger.preferences.appearance.components.ChatListPreviewCell$1
            r2 = 1099956224(0x41900000, float:18.0)
            int r3 = org.telegram.messenger.AndroidUtilities.m1124dp(r2)
            int r2 = org.telegram.messenger.AndroidUtilities.m1124dp(r2)
            r1.<init>(r0, r3, r2)
            r4.premiumStar = r1
        L71:
            android.graphics.drawable.Drawable r0 = r4.premiumStar
            goto L75
        L74:
            r0 = 0
        L75:
            if (r5 == 0) goto L84
            org.telegram.ui.ActionBar.ActionBar r5 = r4.actionBar
            java.lang.String r1 = com.exteragram.messenger.utils.text.LocaleUtils.getActionBarTitle()
            r2 = 1
            r3 = 250(0xfa, float:3.5E-43)
            r5.setTitleAnimatedX(r1, r0, r2, r3)
            return
        L84:
            org.telegram.ui.ActionBar.ActionBar r5 = r4.actionBar
            java.lang.String r1 = com.exteragram.messenger.utils.text.LocaleUtils.getActionBarTitle()
            r5.setTitle(r1, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.preferences.appearance.components.ChatListPreviewCell.updateStatus(boolean):void");
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0.0f, getMeasuredHeight() - 1, getMeasuredWidth(), getMeasuredHeight() - 1, Theme.dividerPaint);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(0, 0));
        setMeasuredDimension(View.MeasureSpec.getSize(i), getMeasuredHeight());
    }

    @Override // com.exteragram.messenger.preferences.components.CustomPreferenceCell
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ChatListPreviewCell) {
            return Objects.equals(this.actionBar, ((ChatListPreviewCell) obj).actionBar);
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.statusDrawable;
        if (swapAnimatedEmojiDrawable != null) {
            swapAnimatedEmojiDrawable.attach();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.statusDrawable;
        if (swapAnimatedEmojiDrawable != null) {
            swapAnimatedEmojiDrawable.detach();
        }
    }
}
