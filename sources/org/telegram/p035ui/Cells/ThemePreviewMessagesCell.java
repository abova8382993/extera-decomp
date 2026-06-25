package org.telegram.p035ui.Cells;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatMessageSharedResources;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.INavigationLayout;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Cells.ChatMessageCell;
import org.telegram.p035ui.ChatBackgroundDrawable;
import org.telegram.p035ui.Components.AnimatedColor;
import org.telegram.p035ui.Components.AnimatedFloat;
import org.telegram.p035ui.Components.AvatarDrawable;
import org.telegram.p035ui.Components.BackgroundGradientDrawable;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.MotionBackgroundDrawable;
import org.telegram.p035ui.Components.Reactions.ReactionsEffectOverlay;
import org.telegram.p035ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p035ui.Stories.recorder.StoryEntry;

/* JADX INFO: loaded from: classes6.dex */
public class ThemePreviewMessagesCell extends LinearLayout {
    private Drawable backgroundDrawable;
    private BackgroundGradientDrawable.Disposable backgroundGradientDisposable;
    private final Runnable cancelProgress;
    private ChatMessageCell[] cells;
    public boolean customAnimation;
    public BaseFragment fragment;
    private final Runnable invalidateRunnable;
    private Drawable oldBackgroundDrawable;
    private BackgroundGradientDrawable.Disposable oldBackgroundGradientDisposable;
    private Drawable overrideDrawable;
    private final AnimatedFloat overrideDrawableUpdate;
    private INavigationLayout parentLayout;
    private int progress;
    private Drawable shadowDrawable;
    private final int type;

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchSetPressed(boolean z) {
    }

    public /* synthetic */ void lambda$new$0() {
        this.progress = -1;
        int i = 0;
        while (true) {
            ChatMessageCell[] chatMessageCellArr = this.cells;
            if (i >= chatMessageCellArr.length) {
                return;
            }
            ChatMessageCell chatMessageCell = chatMessageCellArr[i];
            if (chatMessageCell != null) {
                chatMessageCell.invalidate();
            }
            i++;
        }
    }

    public ThemePreviewMessagesCell(Context context, INavigationLayout iNavigationLayout, int i) {
        this(context, iNavigationLayout, i, 0L);
    }

    public ThemePreviewMessagesCell(Context context, INavigationLayout iNavigationLayout, int i, long j) {
        this(context, iNavigationLayout, i, j, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0412  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x046d A[SYNTHETIC] */
    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ThemePreviewMessagesCell(android.content.Context r24, org.telegram.p035ui.ActionBar.INavigationLayout r25, int r26, long r27, org.telegram.ui.ActionBar.Theme.ResourcesProvider r29) {
        /*
            Method dump skipped, instruction units count: 1134
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Cells.ThemePreviewMessagesCell.<init>(android.content.Context, org.telegram.ui.ActionBar.INavigationLayout, int, long, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
    }

    /* JADX INFO: renamed from: org.telegram.ui.Cells.ThemePreviewMessagesCell$1 */
    public class C33871 extends ChatMessageCell {
        private final AnimatedColor color1;
        private final AnimatedColor color2;
        private GestureDetector gestureDetector;
        final /* synthetic */ Context val$context;
        final /* synthetic */ int val$type;

        /* JADX INFO: renamed from: org.telegram.ui.Cells.ThemePreviewMessagesCell$1$1 */
        public class AnonymousClass1 extends GestureDetector.SimpleOnGestureListener {
            public AnonymousClass1() {
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent motionEvent) {
                C33871 c33871 = C33871.this;
                if (c33871.val$type != 2 || MediaDataController.getInstance(c33871.currentAccount).getDoubleTapReaction() == null) {
                    return false;
                }
                boolean zSelectReaction = C33871.this.getMessageObject().selectReaction(ReactionsLayoutInBubble.VisibleReaction.fromEmojicon(MediaDataController.getInstance(C33871.this.currentAccount).getDoubleTapReaction()), false, false);
                C33871 c338712 = C33871.this;
                c338712.setMessageObject(c338712.getMessageObject(), null, false, false, false);
                C33871.this.requestLayout();
                ReactionsEffectOverlay.removeCurrent(false);
                if (zSelectReaction) {
                    ThemePreviewMessagesCell themePreviewMessagesCell = ThemePreviewMessagesCell.this;
                    ReactionsEffectOverlay.show(themePreviewMessagesCell.fragment, null, themePreviewMessagesCell.cells[1], null, motionEvent.getX(), motionEvent.getY(), ReactionsLayoutInBubble.VisibleReaction.fromEmojicon(MediaDataController.getInstance(C33871.this.currentAccount).getDoubleTapReaction()), C33871.this.currentAccount, 0);
                    ReactionsEffectOverlay.startAnimation();
                }
                C33871.this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserverOnPreDrawListenerC76301());
                return true;
            }

            /* JADX INFO: renamed from: org.telegram.ui.Cells.ThemePreviewMessagesCell$1$1$1 */
            public class ViewTreeObserverOnPreDrawListenerC76301 implements ViewTreeObserver.OnPreDrawListener {
                public ViewTreeObserverOnPreDrawListenerC76301() {
                }

                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    C33871.this.getViewTreeObserver().removeOnPreDrawListener(this);
                    C33871.this.getTransitionParams().resetAnimation();
                    C33871.this.getTransitionParams().animateChange();
                    C33871.this.getTransitionParams().animateChange = true;
                    C33871.this.getTransitionParams().animateChangeProgress = 0.0f;
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Cells.ThemePreviewMessagesCell$1$1$1$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            this.f$0.lambda$onPreDraw$0(valueAnimator);
                        }
                    });
                    valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.ThemePreviewMessagesCell.1.1.1.1
                        public C76311() {
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            C33871.this.getTransitionParams().resetAnimation();
                            C33871.this.getTransitionParams().animateChange = false;
                            C33871.this.getTransitionParams().animateChangeProgress = 1.0f;
                        }
                    });
                    valueAnimatorOfFloat.start();
                    return false;
                }

                public /* synthetic */ void lambda$onPreDraw$0(ValueAnimator valueAnimator) {
                    C33871.this.getTransitionParams().animateChangeProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    C33871.this.invalidate();
                }

                /* JADX INFO: renamed from: org.telegram.ui.Cells.ThemePreviewMessagesCell$1$1$1$1 */
                public class C76311 extends AnimatorListenerAdapter {
                    public C76311() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        C33871.this.getTransitionParams().resetAnimation();
                        C33871.this.getTransitionParams().animateChange = false;
                        C33871.this.getTransitionParams().animateChangeProgress = 1.0f;
                    }
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C33871(Context context, int i, boolean z, ChatMessageSharedResources chatMessageSharedResources, Theme.ResourcesProvider resourcesProvider, Context context2, int i2) {
            super(context, i, z, chatMessageSharedResources, resourcesProvider);
            this.val$context = context2;
            this.val$type = i2;
            this.gestureDetector = new GestureDetector(context2, new AnonymousClass1());
            CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT;
            this.color1 = new AnimatedColor(this, 0L, 180L, cubicBezierInterpolator);
            this.color2 = new AnimatedColor(this, 0L, 180L, cubicBezierInterpolator);
        }

        @Override // org.telegram.p035ui.Cells.ChatMessageCell, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (ThemePreviewMessagesCell.this.allowLoadingOnTouch()) {
                return super.onTouchEvent(motionEvent);
            }
            this.gestureDetector.onTouchEvent(motionEvent);
            return true;
        }

        @Override // android.view.ViewGroup, android.view.View
        public void dispatchDraw(Canvas canvas) {
            int themedColor;
            int themedColor2;
            if (getMessageObject() != null && getMessageObject().overrideLinkColor >= 0) {
                int i = getMessageObject().overrideLinkColor;
                if (i >= 14) {
                    MessagesController messagesController = MessagesController.getInstance(UserConfig.selectedAccount);
                    MessagesController.PeerColors peerColors = messagesController != null ? messagesController.peerColors : null;
                    MessagesController.PeerColor color = peerColors != null ? peerColors.getColor(i) : null;
                    if (color != null) {
                        int color1 = color.getColor1();
                        themedColor = getThemedColor(Theme.keys_avatar_background[AvatarDrawable.getPeerColorIndex(color1)]);
                        themedColor2 = getThemedColor(Theme.keys_avatar_background2[AvatarDrawable.getPeerColorIndex(color1)]);
                    } else {
                        long j = i;
                        themedColor = getThemedColor(Theme.keys_avatar_background[AvatarDrawable.getColorIndex(j)]);
                        themedColor2 = getThemedColor(Theme.keys_avatar_background2[AvatarDrawable.getColorIndex(j)]);
                    }
                } else {
                    long j2 = i;
                    themedColor = getThemedColor(Theme.keys_avatar_background[AvatarDrawable.getColorIndex(j2)]);
                    themedColor2 = getThemedColor(Theme.keys_avatar_background2[AvatarDrawable.getColorIndex(j2)]);
                }
                this.avatarDrawable.setColor(this.color1.set(themedColor), this.color2.set(themedColor2));
            } else {
                this.color1.set(this.avatarDrawable.getColor());
                this.color2.set(this.avatarDrawable.getColor2());
            }
            if (getAvatarImage() != null && getAvatarImage().getImageHeight() != 0.0f) {
                getAvatarImage().setImageCoords(getAvatarImage().getImageX(), (getMeasuredHeight() - getAvatarImage().getImageHeight()) - AndroidUtilities.m1036dp(4.0f), getAvatarImage().getImageWidth(), getAvatarImage().getImageHeight());
                getAvatarImage().setRoundRadius((int) (getAvatarImage().getImageHeight() / 2.0f));
                getAvatarImage().draw(canvas);
            } else if (this.val$type == 2) {
                invalidate();
            }
            super.dispatchDraw(canvas);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Cells.ThemePreviewMessagesCell$2 */
    public class C33882 implements ChatMessageCell.ChatMessageCellDelegate {
        public C33882() {
        }

        @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
        public boolean canPerformActions() {
            return ThemePreviewMessagesCell.this.allowLoadingOnTouch();
        }

        @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
        public void didPressReplyMessage(ChatMessageCell chatMessageCell, int i, float f, float f2, boolean z) {
            if (ThemePreviewMessagesCell.this.allowLoadingOnTouch()) {
                ThemePreviewMessagesCell.this.progress = 0;
                chatMessageCell.invalidate();
                AndroidUtilities.cancelRunOnUIThread(ThemePreviewMessagesCell.this.cancelProgress);
                AndroidUtilities.runOnUIThread(ThemePreviewMessagesCell.this.cancelProgress, 5000L);
            }
        }

        @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
        public void needOpenWebView(MessageObject messageObject, String str, String str2, String str3, String str4, int i, int i2) {
            if (ThemePreviewMessagesCell.this.allowLoadingOnTouch()) {
                ThemePreviewMessagesCell.this.progress = 2;
                AndroidUtilities.cancelRunOnUIThread(ThemePreviewMessagesCell.this.cancelProgress);
                AndroidUtilities.runOnUIThread(ThemePreviewMessagesCell.this.cancelProgress, 5000L);
            }
        }

        @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
        public void didPressInstantButton(ChatMessageCell chatMessageCell, int i) {
            if (ThemePreviewMessagesCell.this.allowLoadingOnTouch()) {
                ThemePreviewMessagesCell.this.progress = 2;
                chatMessageCell.invalidate();
                AndroidUtilities.cancelRunOnUIThread(ThemePreviewMessagesCell.this.cancelProgress);
                AndroidUtilities.runOnUIThread(ThemePreviewMessagesCell.this.cancelProgress, 5000L);
            }
        }

        @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
        public boolean isProgressLoading(ChatMessageCell chatMessageCell, int i) {
            return i == ThemePreviewMessagesCell.this.progress;
        }
    }

    public ChatMessageCell[] getCells() {
        return this.cells;
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        int i = 0;
        while (true) {
            ChatMessageCell[] chatMessageCellArr = this.cells;
            if (i >= chatMessageCellArr.length) {
                return;
            }
            chatMessageCellArr[i].invalidate();
            i++;
        }
    }

    public void setOverrideBackground(Drawable drawable) {
        this.overrideDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        if ((this.overrideDrawable instanceof ChatBackgroundDrawable) && isAttachedToWindow()) {
            ((ChatBackgroundDrawable) this.overrideDrawable).onAttachedToWindow(this);
        }
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Drawable drawable = this.overrideDrawable;
        if (drawable instanceof ChatBackgroundDrawable) {
            ((ChatBackgroundDrawable) drawable).onAttachedToWindow(this);
        }
    }

    @Override // android.view.View
    public boolean verifyDrawable(Drawable drawable) {
        return drawable == this.overrideDrawable || drawable == this.oldBackgroundDrawable || super.verifyDrawable(drawable);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onDraw(Canvas canvas) {
        Drawable cachedWallpaperNonBlocking = this.overrideDrawable;
        if (cachedWallpaperNonBlocking == null) {
            cachedWallpaperNonBlocking = Theme.getCachedWallpaperNonBlocking();
        }
        if (Theme.wallpaperLoadTask != null) {
            invalidate();
        }
        if (cachedWallpaperNonBlocking != this.backgroundDrawable && cachedWallpaperNonBlocking != null) {
            if (Theme.isAnimatingColor() || this.customAnimation) {
                this.oldBackgroundDrawable = this.backgroundDrawable;
                this.oldBackgroundGradientDisposable = this.backgroundGradientDisposable;
            } else {
                BackgroundGradientDrawable.Disposable disposable = this.backgroundGradientDisposable;
                if (disposable != null) {
                    disposable.dispose();
                    this.backgroundGradientDisposable = null;
                }
            }
            this.backgroundDrawable = cachedWallpaperNonBlocking;
            this.overrideDrawableUpdate.set(0.0f, true);
        }
        float themeAnimationValue = this.customAnimation ? this.overrideDrawableUpdate.set(1.0f) : this.parentLayout.getThemeAnimationValue();
        int i = 0;
        while (i < 2) {
            Drawable drawable = i == 0 ? this.oldBackgroundDrawable : this.backgroundDrawable;
            if (drawable != null) {
                int i2 = (i != 1 || this.oldBackgroundDrawable == null || (this.parentLayout == null && !this.customAnimation)) ? 255 : (int) (255.0f * themeAnimationValue);
                if (i2 > 0) {
                    drawable.setAlpha(i2);
                    if ((drawable instanceof ColorDrawable) || (drawable instanceof GradientDrawable) || (drawable instanceof MotionBackgroundDrawable)) {
                        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
                        if (drawable instanceof BackgroundGradientDrawable) {
                            this.backgroundGradientDisposable = ((BackgroundGradientDrawable) drawable).drawExactBoundsSize(canvas, this);
                        } else {
                            drawable.draw(canvas);
                        }
                    } else if (drawable instanceof BitmapDrawable) {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                        bitmapDrawable.setFilterBitmap(true);
                        if (bitmapDrawable.getTileModeX() == Shader.TileMode.REPEAT) {
                            canvas.save();
                            float f = 2.0f / AndroidUtilities.density;
                            canvas.scale(f, f);
                            drawable.setBounds(0, 0, (int) Math.ceil(getMeasuredWidth() / f), (int) Math.ceil(getMeasuredHeight() / f));
                        } else {
                            int measuredHeight = getMeasuredHeight();
                            float fMax = Math.max(getMeasuredWidth() / drawable.getIntrinsicWidth(), measuredHeight / drawable.getIntrinsicHeight());
                            int iCeil = (int) Math.ceil(drawable.getIntrinsicWidth() * fMax);
                            int iCeil2 = (int) Math.ceil(drawable.getIntrinsicHeight() * fMax);
                            int measuredWidth = (getMeasuredWidth() - iCeil) / 2;
                            int i3 = (measuredHeight - iCeil2) / 2;
                            canvas.save();
                            canvas.clipRect(0, 0, iCeil, getMeasuredHeight());
                            drawable.setBounds(measuredWidth, i3, iCeil + measuredWidth, iCeil2 + i3);
                        }
                        drawable.draw(canvas);
                        canvas.restore();
                    } else {
                        StoryEntry.drawBackgroundDrawable(canvas, drawable, getWidth(), getHeight());
                    }
                    if (i == 0 && this.oldBackgroundDrawable != null && themeAnimationValue >= 1.0f) {
                        BackgroundGradientDrawable.Disposable disposable2 = this.oldBackgroundGradientDisposable;
                        if (disposable2 != null) {
                            disposable2.dispose();
                            this.oldBackgroundGradientDisposable = null;
                        }
                        this.oldBackgroundDrawable = null;
                        invalidate();
                    }
                }
            }
            i++;
        }
        this.shadowDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        this.shadowDrawable.draw(canvas);
    }

    public boolean allowLoadingOnTouch() {
        int i = this.type;
        return i == 3 || i == 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BackgroundGradientDrawable.Disposable disposable = this.backgroundGradientDisposable;
        if (disposable != null) {
            disposable.dispose();
            this.backgroundGradientDisposable = null;
        }
        BackgroundGradientDrawable.Disposable disposable2 = this.oldBackgroundGradientDisposable;
        if (disposable2 != null) {
            disposable2.dispose();
            this.oldBackgroundGradientDisposable = null;
        }
        Drawable drawable = this.overrideDrawable;
        if (drawable instanceof ChatBackgroundDrawable) {
            ((ChatBackgroundDrawable) drawable).onDetachedFromWindow(this);
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.type == 2 || allowLoadingOnTouch()) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.type == 2 || allowLoadingOnTouch()) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.type == 2 || allowLoadingOnTouch()) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }
}
