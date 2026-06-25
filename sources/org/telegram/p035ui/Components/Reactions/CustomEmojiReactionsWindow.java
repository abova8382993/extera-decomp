package org.telegram.p035ui.Components.Reactions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AnimationNotificationsLocker;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.ChatActivity;
import org.telegram.p035ui.Components.AnimatedEmojiDrawable;
import org.telegram.p035ui.Components.Bulletin;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.EmojiTabsStrip;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.Premium.PremiumFeatureBottomSheet;
import org.telegram.p035ui.Components.Premium.PremiumLockIconView;
import org.telegram.p035ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p035ui.Components.ReactionsContainerLayout;
import org.telegram.p035ui.Components.StableAnimator;
import org.telegram.p035ui.LaunchActivity;
import org.telegram.p035ui.SelectAnimatedEmojiDialog;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_stars;

/* JADX INFO: loaded from: classes7.dex */
public class CustomEmojiReactionsWindow {
    private int account;
    boolean attachToParent;
    BaseFragment baseFragment;
    private boolean cascadeAnimation;
    public ContainerView containerView;
    private float dismissProgress;
    private boolean dismissed;
    boolean enterTransitionFinished;
    float enterTransitionProgress;
    float fromRadius;
    float fromRectTranslateX;
    float fromRectTranslateY;
    private boolean invalidatePath;
    boolean isShowing;
    float keyboardHeight;
    private Runnable onDismiss;
    List<ReactionsLayoutInBubble.VisibleReaction> reactions;
    ReactionsContainerLayout reactionsContainerLayout;
    Theme.ResourcesProvider resourcesProvider;
    SelectAnimatedEmojiDialog selectAnimatedEmojiDialog;
    public boolean transition;
    private final int type;
    private ValueAnimator valueAnimator;
    private boolean wasFocused;
    WindowManager windowManager;
    public FrameLayout windowView;
    float yTranslation;
    RectF fromRect = new RectF();
    public RectF drawingRect = new RectF();
    private final Path pathToClipApi20 = new Path();
    int[] location = new int[2];
    final AnimationNotificationsLocker notificationsLocker = new AnimationNotificationsLocker();
    HashSet<View> animatingEnterChild = new HashSet<>();
    ArrayList<ValueAnimator> animators = new ArrayList<>();
    private int frameDrawCount = 0;

    public CustomEmojiReactionsWindow(int i, BaseFragment baseFragment, List<ReactionsLayoutInBubble.VisibleReaction> list, HashSet<ReactionsLayoutInBubble.VisibleReaction> hashSet, final ReactionsContainerLayout reactionsContainerLayout, Theme.ResourcesProvider resourcesProvider, boolean z) {
        this.type = i;
        this.reactions = list;
        this.baseFragment = baseFragment;
        this.resourcesProvider = resourcesProvider;
        Context context = baseFragment != null ? baseFragment.getContext() : reactionsContainerLayout.getContext();
        C48641 c48641 = new FrameLayout(context) { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow.1
            Bulletin.Delegate bulletinDelegate = new Bulletin.Delegate() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow.1.1
                public AnonymousClass1() {
                }

                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public int getBottomOffset(int i2) {
                    return (int) CustomEmojiReactionsWindow.this.keyboardHeight;
                }
            };

            @Override // android.view.ViewGroup, android.view.View
            public void dispatchSetPressed(boolean z2) {
            }

            public C48641(Context context2) {
                super(context2);
                this.bulletinDelegate = new Bulletin.Delegate() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow.1.1
                    public AnonymousClass1() {
                    }

                    @Override // org.telegram.ui.Components.Bulletin.Delegate
                    public int getBottomOffset(int i2) {
                        return (int) CustomEmojiReactionsWindow.this.keyboardHeight;
                    }
                };
            }

            @Override // android.view.ViewGroup, android.view.View
            public boolean dispatchKeyEvent(KeyEvent keyEvent) {
                if (keyEvent.getAction() == 1 && keyEvent.getKeyCode() == 4) {
                    CustomEmojiReactionsWindow customEmojiReactionsWindow = CustomEmojiReactionsWindow.this;
                    if (customEmojiReactionsWindow.enterTransitionFinished) {
                        customEmojiReactionsWindow.dismiss();
                    }
                    return true;
                }
                return super.dispatchKeyEvent(keyEvent);
            }

            @Override // android.view.View
            public boolean fitSystemWindows(Rect rect) {
                CustomEmojiReactionsWindow customEmojiReactionsWindow = CustomEmojiReactionsWindow.this;
                if (customEmojiReactionsWindow.keyboardHeight != rect.bottom && customEmojiReactionsWindow.wasFocused) {
                    CustomEmojiReactionsWindow customEmojiReactionsWindow2 = CustomEmojiReactionsWindow.this;
                    customEmojiReactionsWindow2.keyboardHeight = rect.bottom;
                    customEmojiReactionsWindow2.updateWindowPosition();
                }
                return super.fitSystemWindows(rect);
            }

            /* JADX INFO: renamed from: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$1$1 */
            public class AnonymousClass1 implements Bulletin.Delegate {
                public AnonymousClass1() {
                }

                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public int getBottomOffset(int i2) {
                    return (int) CustomEmojiReactionsWindow.this.keyboardHeight;
                }
            }

            @Override // android.view.ViewGroup, android.view.View
            public void onAttachedToWindow() {
                super.onAttachedToWindow();
                Bulletin.addDelegate(this, this.bulletinDelegate);
            }

            @Override // android.view.ViewGroup, android.view.View
            public void onDetachedFromWindow() {
                super.onDetachedFromWindow();
                Bulletin.removeDelegate(this);
            }

            @Override // android.view.View
            public void setAlpha(float f) {
                super.setAlpha(f);
            }
        };
        this.windowView = c48641;
        c48641.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(view);
            }
        });
        this.attachToParent = i == 2 || i == 4 || i == 5 || z;
        this.containerView = new ContainerView(context2);
        C48652 c48652 = new C48652(baseFragment, context2, false, null, reactionsContainerLayout.getWindowType(), i != 1, resourcesProvider, 16, reactionsContainerLayout, baseFragment);
        this.selectAnimatedEmojiDialog = c48652;
        c48652.setOutlineProvider(new ViewOutlineProvider() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow.3
            final Rect rect = new Rect();
            final RectF rectTmp = new RectF();
            final RectF rectF = new RectF();

            public C48663() {
            }

            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                float fLerp = AndroidUtilities.lerp(CustomEmojiReactionsWindow.this.fromRadius, AndroidUtilities.m1036dp(8.0f), CustomEmojiReactionsWindow.this.enterTransitionProgress);
                this.rectTmp.set(0.0f, 0.0f, view.getMeasuredWidth(), view.getMeasuredHeight());
                CustomEmojiReactionsWindow customEmojiReactionsWindow = CustomEmojiReactionsWindow.this;
                AndroidUtilities.lerp(customEmojiReactionsWindow.fromRect, this.rectTmp, customEmojiReactionsWindow.enterTransitionProgress, this.rectF);
                this.rectF.round(this.rect);
                outline.setRoundRect(this.rect, fLerp);
            }
        });
        this.selectAnimatedEmojiDialog.setClipToOutline(true);
        this.selectAnimatedEmojiDialog.setPaused(reactionsContainerLayout.paused, reactionsContainerLayout.pausedExceptSelected);
        this.selectAnimatedEmojiDialog.setOnLongPressedListener(new SelectAnimatedEmojiDialog.onLongPressedListener() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow.4
            final /* synthetic */ ReactionsContainerLayout val$reactionsContainerLayout;

            public C48674(final ReactionsContainerLayout reactionsContainerLayout2) {
                reactionsContainerLayout = reactionsContainerLayout2;
            }

            @Override // org.telegram.ui.SelectAnimatedEmojiDialog.onLongPressedListener
            public void onLongPressed(SelectAnimatedEmojiDialog.ImageViewEmoji imageViewEmoji) {
                boolean z2 = imageViewEmoji.isDefaultReaction;
                ReactionsContainerLayout reactionsContainerLayout2 = reactionsContainerLayout;
                if (z2) {
                    reactionsContainerLayout2.onReactionClicked(imageViewEmoji, imageViewEmoji.reaction, true);
                } else {
                    reactionsContainerLayout2.onReactionClicked(imageViewEmoji, ReactionsLayoutInBubble.VisibleReaction.fromCustomEmoji(Long.valueOf(imageViewEmoji.span.documentId)), true);
                }
            }
        });
        this.selectAnimatedEmojiDialog.setOnRecentClearedListener(new SelectAnimatedEmojiDialog.onRecentClearedListener() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow.5
            final /* synthetic */ ReactionsContainerLayout val$reactionsContainerLayout;

            public C48685(final ReactionsContainerLayout reactionsContainerLayout2) {
                reactionsContainerLayout = reactionsContainerLayout2;
            }

            @Override // org.telegram.ui.SelectAnimatedEmojiDialog.onRecentClearedListener
            public void onRecentCleared() {
                reactionsContainerLayout.clearRecentReactions();
            }
        });
        this.selectAnimatedEmojiDialog.setRecentReactions(list);
        this.selectAnimatedEmojiDialog.setSelectedReactions(hashSet);
        this.selectAnimatedEmojiDialog.setDrawBackground(false);
        this.selectAnimatedEmojiDialog.onShow(null);
        this.containerView.addView(this.selectAnimatedEmojiDialog, LayoutHelper.createFrame(-1, -2.0f, 0, 0.0f, 0.0f, 0.0f, 0.0f));
        int i2 = i == 5 ? 2 : 16;
        if (i == 5) {
            this.containerView.setClipChildren(false);
            this.containerView.setClipToPadding(false);
            this.windowView.setClipChildren(false);
            this.windowView.setClipToPadding(false);
        }
        float f = i2;
        this.windowView.addView(this.containerView, LayoutHelper.createFrame(-1, -1.0f, i == 5 ? 85 : 48, f, f, f, 16.0f));
        this.windowView.setClipChildren(false);
        if (i == 1 || (reactionsContainerLayout2.getDelegate() != null && reactionsContainerLayout2.getDelegate().drawBackground())) {
            this.selectAnimatedEmojiDialog.setBackgroundDelegate(new SelectAnimatedEmojiDialog.BackgroundDelegate() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$$ExternalSyntheticLambda1
                @Override // org.telegram.ui.SelectAnimatedEmojiDialog.BackgroundDelegate
                public final void drawRect(Canvas canvas, int i3, int i4, int i5, int i6, float f2, float f3) {
                    this.f$0.lambda$new$1(reactionsContainerLayout2, canvas, i3, i4, i5, i6, f2, f3);
                }
            });
        }
        if (this.attachToParent) {
            ((ViewGroup) reactionsContainerLayout2.getParent()).addView(this.windowView);
        } else {
            WindowManager.LayoutParams layoutParamsCreateLayoutParams = createLayoutParams(false);
            WindowManager windowManager = AndroidUtilities.findActivity(context2).getWindowManager();
            this.windowManager = windowManager;
            AndroidUtilities.setPreferredMaxRefreshRate(windowManager, this.windowView, layoutParamsCreateLayoutParams);
            this.windowManager.addView(this.windowView, layoutParamsCreateLayoutParams);
        }
        this.reactionsContainerLayout = reactionsContainerLayout2;
        reactionsContainerLayout2.setOnSwitchedToLoopView(new Runnable() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$2();
            }
        });
        reactionsContainerLayout2.prepareAnimation(true);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$3(reactionsContainerLayout2);
            }
        }, 50L);
        if (i != 5) {
            NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.stopAllHeavyOperations, 7);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$1 */
    public class C48641 extends FrameLayout {
        Bulletin.Delegate bulletinDelegate = new Bulletin.Delegate() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow.1.1
            public AnonymousClass1() {
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public int getBottomOffset(int i2) {
                return (int) CustomEmojiReactionsWindow.this.keyboardHeight;
            }
        };

        @Override // android.view.ViewGroup, android.view.View
        public void dispatchSetPressed(boolean z2) {
        }

        public C48641(Context context2) {
            super(context2);
            this.bulletinDelegate = new Bulletin.Delegate() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow.1.1
                public AnonymousClass1() {
                }

                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public int getBottomOffset(int i2) {
                    return (int) CustomEmojiReactionsWindow.this.keyboardHeight;
                }
            };
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            if (keyEvent.getAction() == 1 && keyEvent.getKeyCode() == 4) {
                CustomEmojiReactionsWindow customEmojiReactionsWindow = CustomEmojiReactionsWindow.this;
                if (customEmojiReactionsWindow.enterTransitionFinished) {
                    customEmojiReactionsWindow.dismiss();
                }
                return true;
            }
            return super.dispatchKeyEvent(keyEvent);
        }

        @Override // android.view.View
        public boolean fitSystemWindows(Rect rect) {
            CustomEmojiReactionsWindow customEmojiReactionsWindow = CustomEmojiReactionsWindow.this;
            if (customEmojiReactionsWindow.keyboardHeight != rect.bottom && customEmojiReactionsWindow.wasFocused) {
                CustomEmojiReactionsWindow customEmojiReactionsWindow2 = CustomEmojiReactionsWindow.this;
                customEmojiReactionsWindow2.keyboardHeight = rect.bottom;
                customEmojiReactionsWindow2.updateWindowPosition();
            }
            return super.fitSystemWindows(rect);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$1$1 */
        public class AnonymousClass1 implements Bulletin.Delegate {
            public AnonymousClass1() {
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public int getBottomOffset(int i2) {
                return (int) CustomEmojiReactionsWindow.this.keyboardHeight;
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            Bulletin.addDelegate(this, this.bulletinDelegate);
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            Bulletin.removeDelegate(this);
        }

        @Override // android.view.View
        public void setAlpha(float f) {
            super.setAlpha(f);
        }
    }

    public /* synthetic */ void lambda$new$0(View view) {
        if (this.enterTransitionFinished) {
            dismiss();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$2 */
    public class C48652 extends SelectAnimatedEmojiDialog {
        final /* synthetic */ BaseFragment val$baseFragment;
        final /* synthetic */ ReactionsContainerLayout val$reactionsContainerLayout;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C48652(BaseFragment baseFragment, Context context, boolean z, Integer num, int i, boolean z2, Theme.ResourcesProvider resourcesProvider, int i2, ReactionsContainerLayout reactionsContainerLayout, BaseFragment baseFragment2) {
            super(baseFragment, context, z, num, i, z2, resourcesProvider, i2);
            this.val$reactionsContainerLayout = reactionsContainerLayout;
            this.val$baseFragment = baseFragment2;
        }

        @Override // org.telegram.p035ui.SelectAnimatedEmojiDialog
        public boolean prevWindowKeyboardVisible() {
            if (this.val$reactionsContainerLayout.getDelegate() != null) {
                return this.val$reactionsContainerLayout.getDelegate().needEnterText();
            }
            return false;
        }

        @Override // org.telegram.p035ui.SelectAnimatedEmojiDialog
        public void onInputFocus() {
            if (CustomEmojiReactionsWindow.this.wasFocused) {
                return;
            }
            CustomEmojiReactionsWindow.this.wasFocused = true;
            CustomEmojiReactionsWindow customEmojiReactionsWindow = CustomEmojiReactionsWindow.this;
            if (!customEmojiReactionsWindow.attachToParent) {
                customEmojiReactionsWindow.windowManager.updateViewLayout(customEmojiReactionsWindow.windowView, customEmojiReactionsWindow.createLayoutParams(true));
            }
            BaseFragment baseFragment = this.val$baseFragment;
            if (baseFragment instanceof ChatActivity) {
                ((ChatActivity) baseFragment).needEnterText();
            }
            if (this.val$reactionsContainerLayout.getDelegate() != null) {
                this.val$reactionsContainerLayout.getDelegate().needEnterText();
            }
        }

        @Override // org.telegram.p035ui.SelectAnimatedEmojiDialog
        public void onReactionClick(SelectAnimatedEmojiDialog.ImageViewEmoji imageViewEmoji, ReactionsLayoutInBubble.VisibleReaction visibleReaction) {
            this.val$reactionsContainerLayout.onReactionClicked(imageViewEmoji, visibleReaction, false);
            AndroidUtilities.hideKeyboard(CustomEmojiReactionsWindow.this.windowView);
        }

        @Override // org.telegram.p035ui.SelectAnimatedEmojiDialog
        public void onEmojiSelected(View view, Long l, TLRPC.Document document, TL_stars.TL_starGiftUnique tL_starGiftUnique, Integer num) {
            if (this.val$baseFragment != null) {
                ReactionsContainerLayout reactionsContainerLayout = this.val$reactionsContainerLayout;
                if (!reactionsContainerLayout.channelReactions && reactionsContainerLayout.getWindowType() != 13 && !UserConfig.getInstance(this.val$baseFragment.getCurrentAccount()).isPremium()) {
                    try {
                        CustomEmojiReactionsWindow.this.windowView.performHapticFeedback(3);
                    } catch (Exception unused) {
                    }
                    BulletinFactory.m1142of(CustomEmojiReactionsWindow.this.windowView, null).createEmojiBulletin(document, AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.UnlockPremiumEmojiReaction)), LocaleController.getString(C2797R.string.PremiumMore), new Runnable() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onEmojiSelected$0();
                        }
                    }).show();
                    return;
                }
            }
            if (l == null && document == null) {
                return;
            }
            if (document != null) {
                AnimatedEmojiDrawable.getDocumentFetcher(UserConfig.selectedAccount).putDocument(document);
            }
            this.val$reactionsContainerLayout.onReactionClicked(view, ReactionsLayoutInBubble.VisibleReaction.fromCustomEmoji(Long.valueOf(l == null ? document.f1253id : l.longValue())), false);
            AndroidUtilities.hideKeyboard(CustomEmojiReactionsWindow.this.windowView);
        }

        public /* synthetic */ void lambda$onEmojiSelected$0() {
            CustomEmojiReactionsWindow.this.showUnlockPremiumAlert();
        }

        @Override // org.telegram.p035ui.SelectAnimatedEmojiDialog
        /* JADX INFO: renamed from: invalidateParent */
        public void lambda$new$3() {
            CustomEmojiReactionsWindow.this.containerView.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$3 */
    public class C48663 extends ViewOutlineProvider {
        final Rect rect = new Rect();
        final RectF rectTmp = new RectF();
        final RectF rectF = new RectF();

        public C48663() {
        }

        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            float fLerp = AndroidUtilities.lerp(CustomEmojiReactionsWindow.this.fromRadius, AndroidUtilities.m1036dp(8.0f), CustomEmojiReactionsWindow.this.enterTransitionProgress);
            this.rectTmp.set(0.0f, 0.0f, view.getMeasuredWidth(), view.getMeasuredHeight());
            CustomEmojiReactionsWindow customEmojiReactionsWindow = CustomEmojiReactionsWindow.this;
            AndroidUtilities.lerp(customEmojiReactionsWindow.fromRect, this.rectTmp, customEmojiReactionsWindow.enterTransitionProgress, this.rectF);
            this.rectF.round(this.rect);
            outline.setRoundRect(this.rect, fLerp);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$4 */
    public class C48674 implements SelectAnimatedEmojiDialog.onLongPressedListener {
        final /* synthetic */ ReactionsContainerLayout val$reactionsContainerLayout;

        public C48674(final ReactionsContainerLayout reactionsContainerLayout2) {
            reactionsContainerLayout = reactionsContainerLayout2;
        }

        @Override // org.telegram.ui.SelectAnimatedEmojiDialog.onLongPressedListener
        public void onLongPressed(SelectAnimatedEmojiDialog.ImageViewEmoji imageViewEmoji) {
            boolean z2 = imageViewEmoji.isDefaultReaction;
            ReactionsContainerLayout reactionsContainerLayout2 = reactionsContainerLayout;
            if (z2) {
                reactionsContainerLayout2.onReactionClicked(imageViewEmoji, imageViewEmoji.reaction, true);
            } else {
                reactionsContainerLayout2.onReactionClicked(imageViewEmoji, ReactionsLayoutInBubble.VisibleReaction.fromCustomEmoji(Long.valueOf(imageViewEmoji.span.documentId)), true);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$5 */
    public class C48685 implements SelectAnimatedEmojiDialog.onRecentClearedListener {
        final /* synthetic */ ReactionsContainerLayout val$reactionsContainerLayout;

        public C48685(final ReactionsContainerLayout reactionsContainerLayout2) {
            reactionsContainerLayout = reactionsContainerLayout2;
        }

        @Override // org.telegram.ui.SelectAnimatedEmojiDialog.onRecentClearedListener
        public void onRecentCleared() {
            reactionsContainerLayout.clearRecentReactions();
        }
    }

    public /* synthetic */ void lambda$new$1(ReactionsContainerLayout reactionsContainerLayout, Canvas canvas, int i, int i2, int i3, int i4, float f, float f2) {
        RectF rectF = AndroidUtilities.rectTmp;
        rectF.set(i, i2, i3, i4);
        reactionsContainerLayout.getDelegate().drawRoundRect(canvas, rectF, 0.0f, this.containerView.getX() + f, getBlurOffset() + f2, 255, true);
    }

    public /* synthetic */ void lambda$new$2() {
        this.containerView.invalidate();
    }

    public /* synthetic */ void lambda$new$3(ReactionsContainerLayout reactionsContainerLayout) {
        this.isShowing = true;
        this.containerView.invalidate();
        reactionsContainerLayout.prepareAnimation(false);
        createTransition(true);
    }

    public void setLongPressEnabled(boolean z) {
        this.selectAnimatedEmojiDialog.setLongPressEnabled(z);
    }

    public void updateWindowPosition() {
        if (this.dismissed) {
            return;
        }
        float measuredHeight = this.yTranslation;
        int iM1036dp = AndroidUtilities.m1036dp(32.0f);
        int i = this.type;
        if (i == 1 || i == 2) {
            iM1036dp = AndroidUtilities.m1036dp(24.0f);
        }
        float f = iM1036dp;
        if (this.containerView.getMeasuredHeight() + measuredHeight > (this.windowView.getMeasuredHeight() - this.keyboardHeight) - f) {
            measuredHeight = ((this.windowView.getMeasuredHeight() - this.keyboardHeight) - this.containerView.getMeasuredHeight()) - f;
        }
        if (measuredHeight < 0.0f) {
            measuredHeight = 0.0f;
        }
        this.containerView.animate().translationY(measuredHeight).setDuration(250L).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$$ExternalSyntheticLambda9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$updateWindowPosition$4(valueAnimator);
            }
        }).setInterpolator(CubicBezierInterpolator.DEFAULT).start();
    }

    public /* synthetic */ void lambda$updateWindowPosition$4(ValueAnimator valueAnimator) {
        this.containerView.invalidate();
    }

    public WindowManager.LayoutParams createLayoutParams(boolean z) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.height = -1;
        layoutParams.width = -1;
        int i = this.type;
        layoutParams.type = (i == 0 || i == 3) ? MediaDataController.MAX_STYLE_RUNS_COUNT : 99;
        layoutParams.softInputMode = 16;
        if (z) {
            layoutParams.flags = 65792;
        } else {
            layoutParams.flags = 65800;
        }
        layoutParams.format = -3;
        return layoutParams;
    }

    public void showUnlockPremiumAlert() {
        BaseFragment baseFragment = this.baseFragment;
        if (baseFragment instanceof ChatActivity) {
            baseFragment.showDialog(new PremiumFeatureBottomSheet(this.baseFragment, 11, false));
            return;
        }
        BaseFragment lastFragment = LaunchActivity.getLastFragment();
        if (lastFragment != null) {
            lastFragment.showDialog(new PremiumFeatureBottomSheet(this.baseFragment, 11, false));
        }
    }

    private void createTransition(final boolean z) {
        ValueAnimator valueAnimatorOfFloat;
        this.fromRect.set(this.reactionsContainerLayout.rect);
        ReactionsContainerLayout reactionsContainerLayout = this.reactionsContainerLayout;
        this.fromRadius = reactionsContainerLayout.radius;
        int[] iArr = new int[2];
        if (z) {
            reactionsContainerLayout.getLocationOnScreen(this.location);
        }
        this.windowView.getLocationOnScreen(iArr);
        float fM1036dp = ((((this.location[1] - iArr[1]) - AndroidUtilities.m1036dp(44.0f)) - AndroidUtilities.m1036dp(52.0f)) - (this.selectAnimatedEmojiDialog.includeHint ? AndroidUtilities.m1036dp(26.0f) : 0)) + this.reactionsContainerLayout.getTopOffset();
        if (this.reactionsContainerLayout.showExpandableReactions()) {
            fM1036dp = (this.location[1] - iArr[1]) - AndroidUtilities.m1036dp(12.0f);
        }
        if (this.containerView.getMeasuredHeight() + fM1036dp > this.windowView.getMeasuredHeight() - AndroidUtilities.m1036dp(32.0f)) {
            fM1036dp = (this.windowView.getMeasuredHeight() - AndroidUtilities.m1036dp(32.0f)) - this.containerView.getMeasuredHeight();
        }
        if (fM1036dp < AndroidUtilities.m1036dp(16.0f)) {
            fM1036dp = AndroidUtilities.m1036dp(16.0f);
        }
        if (this.type == 5) {
            fM1036dp = Math.min(fM1036dp, 0.0f);
        }
        int i = this.type;
        if (i == 1) {
            this.containerView.setTranslationX(((this.windowView.getMeasuredWidth() - this.containerView.getMeasuredWidth()) / 2.0f) - AndroidUtilities.m1036dp(16.0f));
        } else if (i == 2 || i == 4) {
            this.containerView.setTranslationX((this.location[0] - iArr[0]) - AndroidUtilities.m1036dp(18.0f));
        } else {
            this.containerView.setTranslationX((this.location[0] - iArr[0]) - AndroidUtilities.m1036dp(2.0f));
        }
        if (!z) {
            this.yTranslation = this.containerView.getTranslationY();
        } else {
            this.yTranslation = fM1036dp;
            this.containerView.setTranslationY(fM1036dp);
        }
        RectF rectF = this.fromRect;
        float x = (this.location[0] - iArr[0]) - this.containerView.getX();
        this.fromRectTranslateX = x;
        float y = (this.location[1] - iArr[1]) - this.containerView.getY();
        this.fromRectTranslateY = y;
        rectF.offset(x, y);
        this.reactionsContainerLayout.setCustomEmojiEnterProgress(this.enterTransitionProgress);
        if (z) {
            this.cascadeAnimation = SharedConfig.getDevicePerformanceClass() >= 2 && LiteMode.isEnabled(LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS);
            this.enterTransitionFinished = false;
        } else {
            this.cascadeAnimation = false;
        }
        if (this.cascadeAnimation) {
            updateCascadeEnter(0.0f, true);
        }
        updateContainersAlpha();
        this.selectAnimatedEmojiDialog.setEnterAnimationInProgress(true);
        this.selectAnimatedEmojiDialog.emojiTabs.showRecentTabStub(z && this.cascadeAnimation);
        this.account = UserConfig.selectedAccount;
        this.notificationsLocker.lock();
        ValueAnimator valueAnimator = this.valueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.transition = true;
        int i2 = this.type;
        float f = this.enterTransitionProgress;
        if (i2 == 4) {
            valueAnimatorOfFloat = ValueAnimator.ofFloat(f, z ? 1.0f : 0.0f);
        } else {
            valueAnimatorOfFloat = StableAnimator.ofFloat(f, z ? 1.0f : 0.0f);
        }
        this.valueAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$$ExternalSyntheticLambda5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f$0.lambda$createTransition$5(z, valueAnimator2);
            }
        });
        if (!z) {
            syncReactionFrames();
        }
        this.valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow.6
            final /* synthetic */ boolean val$enter;

            public C48696(final boolean z2) {
                z = z2;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                CustomEmojiReactionsWindow.this.updateContainersAlpha();
                CustomEmojiReactionsWindow.this.updateContentPosition();
                CustomEmojiReactionsWindow.this.checkAnimationEnd(z);
                CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.invalidateOutline();
                CustomEmojiReactionsWindow customEmojiReactionsWindow = CustomEmojiReactionsWindow.this;
                boolean z2 = z;
                customEmojiReactionsWindow.enterTransitionProgress = z2 ? 1.0f : 0.0f;
                if (z2) {
                    customEmojiReactionsWindow.enterTransitionFinished = true;
                    customEmojiReactionsWindow.containerView.invalidate();
                }
                CustomEmojiReactionsWindow customEmojiReactionsWindow2 = CustomEmojiReactionsWindow.this;
                customEmojiReactionsWindow2.reactionsContainerLayout.setCustomEmojiEnterProgress(Utilities.clamp(customEmojiReactionsWindow2.enterTransitionProgress, 1.0f, 0.0f));
                if (!z) {
                    CustomEmojiReactionsWindow.this.reactionsContainerLayout.setImportantForAccessibility(0);
                    CustomEmojiReactionsWindow.this.reactionsContainerLayout.setSkipDraw(false);
                    CustomEmojiReactionsWindow.this.removeView();
                    Runtime.getRuntime().gc();
                    CustomEmojiReactionsWindow customEmojiReactionsWindow3 = CustomEmojiReactionsWindow.this;
                    customEmojiReactionsWindow3.reactionsContainerLayout.setCustomEmojiReactionsBackground((customEmojiReactionsWindow3.type == 4 || CustomEmojiReactionsWindow.this.type == 5) ? false : true);
                }
                CustomEmojiReactionsWindow.this.transition = false;
            }
        });
        if (this.type == 4) {
            this.valueAnimator.setDuration(420L);
            this.valueAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        } else {
            boolean z2 = this.cascadeAnimation;
            ValueAnimator valueAnimator2 = this.valueAnimator;
            if (z2) {
                valueAnimator2.setDuration(450L);
                this.valueAnimator.setInterpolator(new OvershootInterpolator(0.5f));
            } else {
                valueAnimator2.setDuration(350L);
                this.valueAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
            }
        }
        this.containerView.invalidate();
        switchLayerType(true);
        ReactionsContainerLayout reactionsContainerLayout2 = this.reactionsContainerLayout;
        if (!z2) {
            reactionsContainerLayout2.isHiddenNextReaction = true;
            reactionsContainerLayout2.invalidate();
            this.valueAnimator.setStartDelay(30L);
            this.valueAnimator.start();
        } else {
            reactionsContainerLayout2.setCustomEmojiReactionsBackground(false);
            final ValueAnimator valueAnimator3 = this.valueAnimator;
            Objects.requireNonNull(valueAnimator3);
            HwEmojis.prepare(new Runnable() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    valueAnimator3.start();
                }
            }, this.cascadeAnimation);
        }
        HwEmojis.enableHw();
    }

    public /* synthetic */ void lambda$createTransition$5(boolean z, ValueAnimator valueAnimator) {
        this.valueAnimator = null;
        this.enterTransitionProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        updateContainersAlpha();
        updateContentPosition();
        this.reactionsContainerLayout.setCustomEmojiEnterProgress(Utilities.clamp(this.enterTransitionProgress, 1.0f, 0.0f));
        this.invalidatePath = true;
        this.containerView.invalidate();
        this.selectAnimatedEmojiDialog.invalidateOutline();
        if (this.cascadeAnimation) {
            updateCascadeEnter(this.enterTransitionProgress, z);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$6 */
    public class C48696 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$enter;

        public C48696(final boolean z2) {
            z = z2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            CustomEmojiReactionsWindow.this.updateContainersAlpha();
            CustomEmojiReactionsWindow.this.updateContentPosition();
            CustomEmojiReactionsWindow.this.checkAnimationEnd(z);
            CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.invalidateOutline();
            CustomEmojiReactionsWindow customEmojiReactionsWindow = CustomEmojiReactionsWindow.this;
            boolean z2 = z;
            customEmojiReactionsWindow.enterTransitionProgress = z2 ? 1.0f : 0.0f;
            if (z2) {
                customEmojiReactionsWindow.enterTransitionFinished = true;
                customEmojiReactionsWindow.containerView.invalidate();
            }
            CustomEmojiReactionsWindow customEmojiReactionsWindow2 = CustomEmojiReactionsWindow.this;
            customEmojiReactionsWindow2.reactionsContainerLayout.setCustomEmojiEnterProgress(Utilities.clamp(customEmojiReactionsWindow2.enterTransitionProgress, 1.0f, 0.0f));
            if (!z) {
                CustomEmojiReactionsWindow.this.reactionsContainerLayout.setImportantForAccessibility(0);
                CustomEmojiReactionsWindow.this.reactionsContainerLayout.setSkipDraw(false);
                CustomEmojiReactionsWindow.this.removeView();
                Runtime.getRuntime().gc();
                CustomEmojiReactionsWindow customEmojiReactionsWindow3 = CustomEmojiReactionsWindow.this;
                customEmojiReactionsWindow3.reactionsContainerLayout.setCustomEmojiReactionsBackground((customEmojiReactionsWindow3.type == 4 || CustomEmojiReactionsWindow.this.type == 5) ? false : true);
            }
            CustomEmojiReactionsWindow.this.transition = false;
        }
    }

    public void updateContainersAlpha() {
        if (this.cascadeAnimation) {
            return;
        }
        this.selectAnimatedEmojiDialog.searchBox.setAlpha(this.enterTransitionProgress);
        this.selectAnimatedEmojiDialog.emojiGridView.setAlpha(this.enterTransitionProgress);
        this.selectAnimatedEmojiDialog.emojiSearchGridView.setAlpha(this.enterTransitionProgress);
        this.selectAnimatedEmojiDialog.emojiTabs.setAlpha(this.enterTransitionProgress);
        this.selectAnimatedEmojiDialog.emojiTabsShadow.setAlpha(this.enterTransitionProgress);
    }

    public void updateContentPosition() {
        this.selectAnimatedEmojiDialog.contentView.setTranslationX(this.cascadeAnimation ? 0.0f : this.containerView.enterTransitionOffsetX);
        this.selectAnimatedEmojiDialog.contentView.setTranslationY(this.containerView.enterTransitionOffsetY);
        this.selectAnimatedEmojiDialog.contentView.setPivotX(this.containerView.enterTransitionScalePx);
        this.selectAnimatedEmojiDialog.contentView.setPivotY(this.containerView.enterTransitionScalePy);
        this.selectAnimatedEmojiDialog.contentView.setScaleX(this.containerView.enterTransitionScale);
        this.selectAnimatedEmojiDialog.contentView.setScaleY(this.containerView.enterTransitionScale);
    }

    private void switchLayerType(boolean z) {
        int i = z ? 2 : 0;
        this.selectAnimatedEmojiDialog.emojiGridView.setLayerType(i, null);
        this.selectAnimatedEmojiDialog.searchBox.setLayerType(i, null);
        if (this.cascadeAnimation) {
            for (int i2 = 0; i2 < Math.min(this.selectAnimatedEmojiDialog.emojiTabs.contentView.getChildCount(), 16); i2++) {
                this.selectAnimatedEmojiDialog.emojiTabs.contentView.getChildAt(i2).setLayerType(i, null);
            }
        } else {
            this.selectAnimatedEmojiDialog.emojiTabsShadow.setLayerType(i, null);
            this.selectAnimatedEmojiDialog.emojiTabs.setLayerType(i, null);
        }
    }

    private void setScaleForChild(View view, float f) {
        if (view instanceof SelectAnimatedEmojiDialog.ImageViewEmoji) {
            ((SelectAnimatedEmojiDialog.ImageViewEmoji) view).setAnimatedScale(f);
        } else if (view instanceof EmojiTabsStrip.EmojiTabButton) {
            view.setScaleX(f);
            view.setScaleY(f);
        }
    }

    private void updateCascadeEnter(float f, boolean z) {
        SelectAnimatedEmojiDialog selectAnimatedEmojiDialog;
        int y = (int) (this.selectAnimatedEmojiDialog.getY() + this.selectAnimatedEmojiDialog.contentView.getY() + this.selectAnimatedEmojiDialog.emojiGridView.getY());
        final ArrayList arrayList = null;
        int i = 0;
        boolean z2 = false;
        while (true) {
            int childCount = this.selectAnimatedEmojiDialog.emojiGridView.getChildCount();
            selectAnimatedEmojiDialog = this.selectAnimatedEmojiDialog;
            if (i >= childCount) {
                break;
            }
            View childAt = selectAnimatedEmojiDialog.emojiGridView.getChildAt(i);
            if (!this.animatingEnterChild.contains(childAt)) {
                float top = childAt.getTop() + y + (childAt.getMeasuredHeight() / 2.0f);
                RectF rectF = this.drawingRect;
                if (top < rectF.bottom && top > rectF.top && f != 0.0f) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(childAt);
                    this.animatingEnterChild.add(childAt);
                } else {
                    setScaleForChild(childAt, 0.0f);
                    z2 = true;
                }
            }
            i++;
        }
        int y2 = (int) (selectAnimatedEmojiDialog.getY() + this.selectAnimatedEmojiDialog.contentView.getY() + this.selectAnimatedEmojiDialog.emojiTabs.getY());
        for (int i2 = 0; i2 < this.selectAnimatedEmojiDialog.emojiTabs.contentView.getChildCount(); i2++) {
            View childAt2 = this.selectAnimatedEmojiDialog.emojiTabs.contentView.getChildAt(i2);
            if (!this.animatingEnterChild.contains(childAt2)) {
                float top2 = childAt2.getTop() + y2 + (childAt2.getMeasuredHeight() / 2.0f);
                RectF rectF2 = this.drawingRect;
                if (top2 < rectF2.bottom && top2 > rectF2.top && f != 0.0f) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(childAt2);
                    this.animatingEnterChild.add(childAt2);
                } else {
                    setScaleForChild(childAt2, 0.0f);
                    z2 = true;
                }
            }
        }
        if (z2) {
            this.selectAnimatedEmojiDialog.emojiGridViewContainer.invalidate();
        }
        if (arrayList != null) {
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$$ExternalSyntheticLambda8
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$updateCascadeEnter$6(arrayList, valueAnimator);
                }
            });
            this.animators.add(valueAnimatorOfFloat);
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow.7
                final /* synthetic */ boolean val$enter;
                final /* synthetic */ ValueAnimator val$valueAnimator;

                public C48707(ValueAnimator valueAnimatorOfFloat2, boolean z3) {
                    valueAnimator = valueAnimatorOfFloat2;
                    z = z3;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    CustomEmojiReactionsWindow.this.animators.remove(valueAnimator);
                    CustomEmojiReactionsWindow.this.checkAnimationEnd(z);
                }
            });
            if (this.type == 4) {
                valueAnimatorOfFloat2.setDuration(420L);
                valueAnimatorOfFloat2.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
            } else {
                valueAnimatorOfFloat2.setDuration(350L);
                valueAnimatorOfFloat2.setInterpolator(new OvershootInterpolator(1.0f));
            }
            valueAnimatorOfFloat2.start();
        }
    }

    public /* synthetic */ void lambda$updateCascadeEnter$6(ArrayList arrayList, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        for (int i = 0; i < arrayList.size(); i++) {
            setScaleForChild((View) arrayList.get(i), fFloatValue);
        }
        this.selectAnimatedEmojiDialog.emojiGridViewContainer.invalidate();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$7 */
    public class C48707 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$enter;
        final /* synthetic */ ValueAnimator val$valueAnimator;

        public C48707(ValueAnimator valueAnimatorOfFloat2, boolean z3) {
            valueAnimator = valueAnimatorOfFloat2;
            z = z3;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            CustomEmojiReactionsWindow.this.animators.remove(valueAnimator);
            CustomEmojiReactionsWindow.this.checkAnimationEnd(z);
        }
    }

    public void checkAnimationEnd(boolean z) {
        View childAt;
        if (this.animators.isEmpty()) {
            switchLayerType(false);
            HwEmojis.disableHw();
            this.notificationsLocker.unlock();
            this.selectAnimatedEmojiDialog.setEnterAnimationInProgress(false);
            if (z) {
                this.selectAnimatedEmojiDialog.emojiTabs.showRecentTabStub(false);
                this.selectAnimatedEmojiDialog.emojiGridView.invalidate();
                this.selectAnimatedEmojiDialog.emojiGridView.invalidateViews();
                this.selectAnimatedEmojiDialog.searchBox.checkInitialization();
                this.selectAnimatedEmojiDialog.sendAccessibilityEvent(32);
                this.reactionsContainerLayout.setImportantForAccessibility(4);
                int i = 0;
                while (true) {
                    if (i >= this.selectAnimatedEmojiDialog.emojiGridView.getChildCount()) {
                        childAt = null;
                        break;
                    } else {
                        if (this.selectAnimatedEmojiDialog.emojiGridView.getChildAt(i) instanceof SelectAnimatedEmojiDialog.ImageViewEmoji) {
                            childAt = this.selectAnimatedEmojiDialog.emojiGridView.getChildAt(i);
                            break;
                        }
                        i++;
                    }
                }
                if (childAt != null) {
                    childAt.performAccessibilityAction(64, null);
                } else {
                    this.selectAnimatedEmojiDialog.performAccessibilityAction(64, null);
                }
                float pullingLeftProgress = this.reactionsContainerLayout.getPullingLeftProgress();
                ReactionsContainerLayout reactionsContainerLayout = this.reactionsContainerLayout;
                if (pullingLeftProgress > 0.0f) {
                    reactionsContainerLayout.isHiddenNextReaction = false;
                    reactionsContainerLayout.onCustomEmojiWindowOpened();
                } else {
                    reactionsContainerLayout.isHiddenNextReaction = true;
                    reactionsContainerLayout.onCustomEmojiWindowOpened();
                }
                this.selectAnimatedEmojiDialog.resetBackgroundBitmaps();
                syncReactionFrames();
                this.containerView.invalidate();
            }
        }
    }

    private void syncReactionFrames() {
        for (int i = 0; i < this.selectAnimatedEmojiDialog.emojiGridView.getChildCount(); i++) {
            if (this.selectAnimatedEmojiDialog.emojiGridView.getChildAt(i) instanceof SelectAnimatedEmojiDialog.ImageViewEmoji) {
                SelectAnimatedEmojiDialog.ImageViewEmoji imageViewEmoji = (SelectAnimatedEmojiDialog.ImageViewEmoji) this.selectAnimatedEmojiDialog.emojiGridView.getChildAt(i);
                if (imageViewEmoji.reaction != null) {
                    imageViewEmoji.notDraw = false;
                    imageViewEmoji.invalidate();
                }
            }
        }
    }

    public void removeView() {
        if (this.type != 5) {
            NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.startAllHeavyOperations, 7);
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$removeView$7();
            }
        });
    }

    public /* synthetic */ void lambda$removeView$7() {
        if (this.windowView.getParent() == null) {
            return;
        }
        if (this.attachToParent) {
            AndroidUtilities.removeFromParent(this.windowView);
        } else {
            try {
                this.windowManager.removeView(this.windowView);
            } catch (Exception unused) {
            }
        }
        Runnable runnable = this.onDismiss;
        if (runnable != null) {
            runnable.run();
        }
    }

    public void dismiss() {
        if (this.dismissed) {
            return;
        }
        ReactionsContainerLayout reactionsContainerLayout = this.reactionsContainerLayout;
        if (reactionsContainerLayout != null) {
            reactionsContainerLayout.onCustomEmojiWindowClosing();
        }
        Bulletin.hideVisible();
        this.dismissed = true;
        AndroidUtilities.hideKeyboard(this.windowView);
        createTransition(false);
        if (this.wasFocused) {
            BaseFragment baseFragment = this.baseFragment;
            if (baseFragment instanceof ChatActivity) {
                ((ChatActivity) baseFragment).onEditTextDialogClose(true, true);
            }
        }
    }

    public void onDismissListener(Runnable runnable) {
        this.onDismiss = runnable;
    }

    public void dismiss(boolean z) {
        if (this.dismissed && z) {
            return;
        }
        this.dismissed = true;
        if (!z) {
            removeView();
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$$ExternalSyntheticLambda4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$dismiss$8(valueAnimator);
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow.8
            public C48718() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                CustomEmojiReactionsWindow.this.removeView();
            }
        });
        valueAnimatorOfFloat.setDuration(150L);
        valueAnimatorOfFloat.start();
    }

    public /* synthetic */ void lambda$dismiss$8(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.dismissProgress = fFloatValue;
        this.containerView.setAlpha(1.0f - fFloatValue);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$8 */
    public class C48718 extends AnimatorListenerAdapter {
        public C48718() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            CustomEmojiReactionsWindow.this.removeView();
        }
    }

    public boolean isShowing() {
        return !this.dismissed;
    }

    public void dismissWithAlpha() {
        if (this.dismissed) {
            return;
        }
        Bulletin.hideVisible();
        this.dismissed = true;
        AndroidUtilities.hideKeyboard(this.windowView);
        this.windowView.animate().alpha(0.0f).setDuration(150L).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow.9
            public C48729() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                CustomEmojiReactionsWindow.this.checkAnimationEnd(false);
                CustomEmojiReactionsWindow customEmojiReactionsWindow = CustomEmojiReactionsWindow.this;
                customEmojiReactionsWindow.enterTransitionProgress = 0.0f;
                customEmojiReactionsWindow.reactionsContainerLayout.setCustomEmojiEnterProgress(Utilities.clamp(0.0f, 1.0f, 0.0f));
                CustomEmojiReactionsWindow.this.reactionsContainerLayout.setSkipDraw(false);
                CustomEmojiReactionsWindow.this.windowView.setVisibility(8);
                CustomEmojiReactionsWindow.this.removeView();
            }
        });
        if (this.wasFocused) {
            BaseFragment baseFragment = this.baseFragment;
            if (baseFragment instanceof ChatActivity) {
                ((ChatActivity) baseFragment).onEditTextDialogClose(true, true);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow$9 */
    public class C48729 extends AnimatorListenerAdapter {
        public C48729() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            CustomEmojiReactionsWindow.this.checkAnimationEnd(false);
            CustomEmojiReactionsWindow customEmojiReactionsWindow = CustomEmojiReactionsWindow.this;
            customEmojiReactionsWindow.enterTransitionProgress = 0.0f;
            customEmojiReactionsWindow.reactionsContainerLayout.setCustomEmojiEnterProgress(Utilities.clamp(0.0f, 1.0f, 0.0f));
            CustomEmojiReactionsWindow.this.reactionsContainerLayout.setSkipDraw(false);
            CustomEmojiReactionsWindow.this.windowView.setVisibility(8);
            CustomEmojiReactionsWindow.this.removeView();
        }
    }

    public class ContainerView extends FrameLayout {
        Paint backgroundPaint;
        private final Path clipPath;
        float enterTransitionOffsetX;
        float enterTransitionOffsetY;
        float enterTransitionScale;
        float enterTransitionScalePx;
        float enterTransitionScalePy;
        int[] radiusTmp;
        Drawable shadow;
        Rect shadowPad;
        HashMap<ReactionsLayoutInBubble.VisibleReaction, SelectAnimatedEmojiDialog.ImageViewEmoji> transitionReactions;

        public ContainerView(Context context) {
            super(context);
            this.shadowPad = new Rect();
            this.backgroundPaint = new Paint(1);
            this.radiusTmp = new int[4];
            this.transitionReactions = new HashMap<>();
            this.enterTransitionOffsetX = 0.0f;
            this.enterTransitionOffsetY = 0.0f;
            this.enterTransitionScale = 1.0f;
            this.enterTransitionScalePx = 0.0f;
            this.enterTransitionScalePy = 0.0f;
            this.clipPath = new Path();
            this.shadow = ContextCompat.getDrawable(context, C2797R.drawable.reactions_bubble_shadow).mutate();
            Rect rect = this.shadowPad;
            int iM1036dp = AndroidUtilities.m1036dp(7.0f);
            rect.bottom = iM1036dp;
            rect.right = iM1036dp;
            rect.top = iM1036dp;
            rect.left = iM1036dp;
            this.shadow.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelShadow, CustomEmojiReactionsWindow.this.resourcesProvider), PorterDuff.Mode.MULTIPLY));
            int i = CustomEmojiReactionsWindow.this.type;
            Paint paint = this.backgroundPaint;
            if (i == 2) {
                paint.setColor(ColorUtils.blendARGB(-16777216, -1, 0.13f));
            } else {
                paint.setColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuBackground, CustomEmojiReactionsWindow.this.resourcesProvider));
            }
        }

        @Override // android.view.View
        public void invalidate() {
            ReactionsContainerLayout reactionsContainerLayout;
            super.invalidate();
            if (CustomEmojiReactionsWindow.this.type == 1 || !((reactionsContainerLayout = CustomEmojiReactionsWindow.this.reactionsContainerLayout) == null || reactionsContainerLayout.getDelegate() == null || !CustomEmojiReactionsWindow.this.reactionsContainerLayout.getDelegate().drawBackground())) {
                CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.invalidateSearchBox();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:61:0x00a7  */
        @Override // android.widget.FrameLayout, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onMeasure(int r9, int r10) {
            /*
                r8 = this;
                org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow r0 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.this
                int r0 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.m13602$$Nest$fgettype(r0)
                r1 = 1
                r2 = 5
                r3 = 4
                r4 = 8
                r5 = 1108344832(0x42100000, float:36.0)
                if (r0 == r1) goto L50
                org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow r0 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.this
                int r0 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.m13602$$Nest$fgettype(r0)
                r1 = 2
                if (r0 == r1) goto L50
                org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow r0 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.this
                int r0 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.m13602$$Nest$fgettype(r0)
                if (r0 != r3) goto L21
                goto L50
            L21:
                org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow r0 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.this
                int r0 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.m13602$$Nest$fgettype(r0)
                r1 = 1094713344(0x41400000, float:12.0)
                if (r0 != r2) goto L36
                int r9 = org.telegram.messenger.AndroidUtilities.m1036dp(r5)
                int r9 = r9 * r4
                int r10 = org.telegram.messenger.AndroidUtilities.m1036dp(r1)
                int r9 = r9 + r10
                goto L58
            L36:
                int r9 = android.view.View.MeasureSpec.getSize(r9)
                int r10 = android.view.View.MeasureSpec.getSize(r10)
                int r9 = java.lang.Math.min(r9, r10)
                int r10 = org.telegram.messenger.AndroidUtilities.m1036dp(r5)
                int r10 = r10 * r4
                int r0 = org.telegram.messenger.AndroidUtilities.m1036dp(r1)
                int r10 = r10 + r0
                if (r10 >= r9) goto L58
                r9 = r10
                goto L58
            L50:
                org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow r9 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.this
                org.telegram.ui.Components.ReactionsContainerLayout r9 = r9.reactionsContainerLayout
                int r9 = r9.getMeasuredWidth()
            L58:
                org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow r10 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.this
                int r10 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.m13602$$Nest$fgettype(r10)
                r0 = 1090519040(0x41000000, float:8.0)
                if (r10 != r3) goto L6d
                int r10 = org.telegram.messenger.AndroidUtilities.m1036dp(r5)
                int r10 = r10 * r4
                int r0 = org.telegram.messenger.AndroidUtilities.m1036dp(r0)
            L6b:
                int r10 = r10 - r0
                goto L9f
            L6d:
                org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow r10 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.this
                org.telegram.ui.Components.ReactionsContainerLayout r10 = r10.reactionsContainerLayout
                boolean r10 = r10.showExpandableReactions()
                if (r10 == 0) goto L9e
                org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow r10 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.this
                java.util.List<org.telegram.ui.Components.Reactions.ReactionsLayoutInBubble$VisibleReaction> r10 = r10.reactions
                int r10 = r10.size()
                float r10 = (float) r10
                float r10 = r10 / r0
                double r6 = (double) r10
                double r6 = java.lang.Math.ceil(r6)
                int r10 = (int) r6
                if (r10 > r4) goto L94
                int r1 = org.telegram.messenger.AndroidUtilities.m1036dp(r5)
                int r10 = r10 * r1
                int r0 = org.telegram.messenger.AndroidUtilities.m1036dp(r0)
                int r10 = r10 + r0
                goto L9f
            L94:
                int r10 = org.telegram.messenger.AndroidUtilities.m1036dp(r5)
                int r10 = r10 * r4
                int r0 = org.telegram.messenger.AndroidUtilities.m1036dp(r0)
                goto L6b
            L9e:
                r10 = r9
            L9f:
                org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow r0 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.this
                int r0 = org.telegram.p035ui.Components.Reactions.CustomEmojiReactionsWindow.m13602$$Nest$fgettype(r0)
                if (r0 != r2) goto Lb1
                r0 = 1132331008(0x437e0000, float:254.0)
                int r0 = org.telegram.messenger.AndroidUtilities.m1036dp(r0)
                int r10 = java.lang.Math.min(r0, r10)
            Lb1:
                r0 = 1073741824(0x40000000, float:2.0)
                int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r0)
                int r10 = android.view.View.MeasureSpec.makeMeasureSpec(r10, r0)
                super.onMeasure(r9, r10)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.Reactions.CustomEmojiReactionsWindow.ContainerView.onMeasure(int, int):void");
        }

        @Override // android.view.ViewGroup, android.view.View
        public void dispatchDraw(Canvas canvas) {
            float f;
            int i;
            float f2;
            int i2;
            View childAt;
            int i3;
            float f3;
            float f4;
            int i4;
            float f5;
            float f6;
            float f7;
            float f8;
            float fM1036dp;
            float fLerp;
            boolean z;
            float f9;
            float f10;
            float f11;
            float f12;
            ReactionsLayoutInBubble.VisibleReaction visibleReaction;
            Canvas canvas2 = canvas;
            CustomEmojiReactionsWindow customEmojiReactionsWindow = CustomEmojiReactionsWindow.this;
            if (customEmojiReactionsWindow.isShowing) {
                float f13 = 1.0f;
                float f14 = 0.0f;
                float fClamp = Utilities.clamp(customEmojiReactionsWindow.enterTransitionProgress, 1.0f, 0.0f);
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
                int i5 = 4;
                if (CustomEmojiReactionsWindow.this.type == 4) {
                    CustomEmojiReactionsWindow customEmojiReactionsWindow2 = CustomEmojiReactionsWindow.this;
                    customEmojiReactionsWindow2.fromRect.set(customEmojiReactionsWindow2.reactionsContainerLayout.rect);
                    CustomEmojiReactionsWindow customEmojiReactionsWindow3 = CustomEmojiReactionsWindow.this;
                    customEmojiReactionsWindow3.fromRect.offset(customEmojiReactionsWindow3.fromRectTranslateX, customEmojiReactionsWindow3.fromRectTranslateY);
                }
                CustomEmojiReactionsWindow customEmojiReactionsWindow4 = CustomEmojiReactionsWindow.this;
                AndroidUtilities.lerp(customEmojiReactionsWindow4.fromRect, rectF, customEmojiReactionsWindow4.enterTransitionProgress, customEmojiReactionsWindow4.drawingRect);
                CustomEmojiReactionsWindow customEmojiReactionsWindow5 = CustomEmojiReactionsWindow.this;
                float fLerp2 = AndroidUtilities.lerp(customEmojiReactionsWindow5.fromRadius, AndroidUtilities.m1036dp(customEmojiReactionsWindow5.type == 5 ? 20.0f : 8.0f), CustomEmojiReactionsWindow.this.enterTransitionProgress);
                this.transitionReactions.clear();
                if (CustomEmojiReactionsWindow.this.type == 1 || (CustomEmojiReactionsWindow.this.reactionsContainerLayout.getDelegate() != null && CustomEmojiReactionsWindow.this.reactionsContainerLayout.getDelegate().drawBackground())) {
                    f = 0.05f;
                    i = 1;
                    CustomEmojiReactionsWindow.this.reactionsContainerLayout.getDelegate().drawRoundRect(canvas2, CustomEmojiReactionsWindow.this.drawingRect, fLerp2, getX(), CustomEmojiReactionsWindow.this.getBlurOffset(), 255, true);
                    canvas2 = canvas2;
                } else {
                    this.shadow.setAlpha((int) (Utilities.clamp(fClamp / 0.05f, 1.0f, 0.0f) * 255.0f));
                    Drawable drawable = this.shadow;
                    RectF rectF2 = CustomEmojiReactionsWindow.this.drawingRect;
                    int i6 = (int) rectF2.left;
                    Rect rect = this.shadowPad;
                    f = 0.05f;
                    drawable.setBounds(i6 - rect.left, ((int) rectF2.top) - rect.top, ((int) rectF2.right) + rect.right, ((int) rectF2.bottom) + rect.bottom);
                    this.shadow.draw(canvas2);
                    canvas2.drawRoundRect(CustomEmojiReactionsWindow.this.drawingRect, fLerp2, fLerp2, this.backgroundPaint);
                    i = 1;
                }
                if (CustomEmojiReactionsWindow.this.reactionsContainerLayout.hintView != null) {
                    canvas2.save();
                    CustomEmojiReactionsWindow customEmojiReactionsWindow6 = CustomEmojiReactionsWindow.this;
                    RectF rectF3 = customEmojiReactionsWindow6.drawingRect;
                    canvas2.translate(rectF3.left, (rectF3.top + customEmojiReactionsWindow6.reactionsContainerLayout.hintView.getY()) - ((CustomEmojiReactionsWindow.this.type == 3 || CustomEmojiReactionsWindow.this.type == 4 || CustomEmojiReactionsWindow.this.type == 5) ? CustomEmojiReactionsWindow.this.reactionsContainerLayout.rect.top : 0.0f));
                    f2 = 255.0f;
                    canvas2.saveLayerAlpha(0.0f, 0.0f, CustomEmojiReactionsWindow.this.reactionsContainerLayout.hintView.getMeasuredWidth(), CustomEmojiReactionsWindow.this.reactionsContainerLayout.hintView.getMeasuredHeight(), (int) (CustomEmojiReactionsWindow.this.reactionsContainerLayout.hintView.getAlpha() * 255.0f * (1.0f - CustomEmojiReactionsWindow.this.enterTransitionProgress)), 31);
                    CustomEmojiReactionsWindow.this.reactionsContainerLayout.hintView.draw(canvas2);
                    canvas2.restore();
                    canvas2.restore();
                } else {
                    f2 = 255.0f;
                }
                CustomEmojiReactionsWindow customEmojiReactionsWindow7 = CustomEmojiReactionsWindow.this;
                RectF rectF4 = customEmojiReactionsWindow7.drawingRect;
                float fWidth = (rectF4.left - customEmojiReactionsWindow7.reactionsContainerLayout.rect.left) + (rectF4.width() - CustomEmojiReactionsWindow.this.reactionsContainerLayout.rect.width());
                CustomEmojiReactionsWindow customEmojiReactionsWindow8 = CustomEmojiReactionsWindow.this;
                if (customEmojiReactionsWindow8.enterTransitionProgress > f || customEmojiReactionsWindow8.type == 5) {
                    canvas2.save();
                    CustomEmojiReactionsWindow customEmojiReactionsWindow9 = CustomEmojiReactionsWindow.this;
                    RectF rectF5 = customEmojiReactionsWindow9.drawingRect;
                    canvas2.translate(fWidth, (rectF5.top - customEmojiReactionsWindow9.reactionsContainerLayout.rect.top) + (rectF5.height() - CustomEmojiReactionsWindow.this.reactionsContainerLayout.rect.height()));
                    CustomEmojiReactionsWindow.this.reactionsContainerLayout.drawBubbles(canvas2);
                    canvas2.restore();
                }
                if (CustomEmojiReactionsWindow.this.type == 5) {
                    this.clipPath.rewind();
                    this.clipPath.addRoundRect(CustomEmojiReactionsWindow.this.drawingRect, fLerp2, fLerp2, Path.Direction.CW);
                    canvas2.save();
                    canvas2.clipPath(this.clipPath);
                }
                this.enterTransitionOffsetX = 0.0f;
                this.enterTransitionOffsetY = 0.0f;
                this.enterTransitionScale = 1.0f;
                this.enterTransitionScalePx = 0.0f;
                this.enterTransitionScalePy = 0.0f;
                CustomEmojiReactionsWindow customEmojiReactionsWindow10 = CustomEmojiReactionsWindow.this;
                if (customEmojiReactionsWindow10.reactionsContainerLayout != null) {
                    for (int childCount = customEmojiReactionsWindow10.selectAnimatedEmojiDialog.emojiGridView.getChildCount() - i; childCount >= 0; childCount--) {
                        if (CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.emojiGridView.getChildAt(childCount) instanceof SelectAnimatedEmojiDialog.ImageViewEmoji) {
                            SelectAnimatedEmojiDialog.ImageViewEmoji imageViewEmoji = (SelectAnimatedEmojiDialog.ImageViewEmoji) CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.emojiGridView.getChildAt(childCount);
                            if (imageViewEmoji.isFirstReactions && (visibleReaction = imageViewEmoji.reaction) != null) {
                                this.transitionReactions.put(visibleReaction, imageViewEmoji);
                            }
                        }
                    }
                    int iSave = canvas2.save();
                    CustomEmojiReactionsWindow customEmojiReactionsWindow11 = CustomEmojiReactionsWindow.this;
                    RectF rectF6 = customEmojiReactionsWindow11.drawingRect;
                    canvas2.translate(rectF6.left, rectF6.top + ((customEmojiReactionsWindow11.reactionsContainerLayout.getTopOffset() + CustomEmojiReactionsWindow.this.reactionsContainerLayout.expandSize()) * (1.0f - CustomEmojiReactionsWindow.this.enterTransitionProgress)));
                    float fMax = Math.max(1.0f - (CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.emojiSearchGridView.getVisibility() == 0 ? CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.emojiSearchGridView.getAlpha() : 0.0f), 1.0f - CustomEmojiReactionsWindow.this.enterTransitionProgress);
                    if (fMax != 1.0f) {
                        canvas2.saveLayerAlpha(0.0f, 0.0f, CustomEmojiReactionsWindow.this.drawingRect.width(), CustomEmojiReactionsWindow.this.drawingRect.height(), (int) (fMax * f2), 31);
                    }
                    int x = (int) (CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.getX() + CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.emojiGridView.getX());
                    int y = (int) (CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.getY() + CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.emojiGridView.getY());
                    int i7 = CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.emojiTabs.getParent() != null ? i : 0;
                    if (CustomEmojiReactionsWindow.this.type != 5) {
                        canvas2.clipRect(y, i7 != 0 ? x + (AndroidUtilities.m1036dp(36.0f) * CustomEmojiReactionsWindow.this.enterTransitionProgress) : 0.0f, y + CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.emojiGridView.getMeasuredWidth(), x + CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.emojiGridView.getMeasuredHeight());
                    }
                    int i8 = -1;
                    int i9 = -1;
                    while (i9 < CustomEmojiReactionsWindow.this.reactionsContainerLayout.recyclerListView.getChildCount()) {
                        CustomEmojiReactionsWindow customEmojiReactionsWindow12 = CustomEmojiReactionsWindow.this;
                        if (i9 == i8) {
                            childAt = customEmojiReactionsWindow12.reactionsContainerLayout.nextRecentReaction;
                        } else {
                            childAt = customEmojiReactionsWindow12.reactionsContainerLayout.recyclerListView.getChildAt(i9);
                        }
                        if (childAt.getLeft() < 0 || childAt.getVisibility() == 8) {
                            i3 = i9;
                            f3 = f13;
                            f4 = f14;
                            i4 = i5;
                        } else {
                            canvas2.save();
                            if (childAt instanceof ReactionsContainerLayout.ReactionHolderView) {
                                ReactionsContainerLayout.ReactionHolderView reactionHolderView = (ReactionsContainerLayout.ReactionHolderView) childAt;
                                PremiumLockIconView premiumLockIconView = reactionHolderView.lockIconView;
                                if (premiumLockIconView != null) {
                                    premiumLockIconView.setAlpha(f13 - CustomEmojiReactionsWindow.this.enterTransitionProgress);
                                }
                                SelectAnimatedEmojiDialog.ImageViewEmoji imageViewEmoji2 = this.transitionReactions.get(reactionHolderView.currentReaction);
                                if (imageViewEmoji2 != null) {
                                    float x2 = childAt.getX();
                                    float y2 = childAt.getY();
                                    f5 = 2.0f;
                                    if (i9 == i8) {
                                        x2 -= CustomEmojiReactionsWindow.this.reactionsContainerLayout.recyclerListView.getX();
                                        y2 -= CustomEmojiReactionsWindow.this.reactionsContainerLayout.recyclerListView.getY();
                                    }
                                    float f15 = x2;
                                    float x3 = (((imageViewEmoji2.getX() + CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.getX()) + CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.emojiGridView.getX()) - reactionHolderView.loopImageView.getX()) - AndroidUtilities.m1036dp(f13);
                                    float y3 = (((imageViewEmoji2.getY() + CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.getY()) + CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.gridViewContainer.getY()) + CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.emojiGridView.getY()) - reactionHolderView.loopImageView.getY();
                                    float measuredWidth = imageViewEmoji2.getMeasuredWidth();
                                    if (imageViewEmoji2.selected || CustomEmojiReactionsWindow.this.type == i5) {
                                        if (CustomEmojiReactionsWindow.this.type == i5) {
                                            f9 = 0.87f * measuredWidth;
                                            x3 -= AndroidUtilities.m1036dp(0.33f);
                                            y3 -= AndroidUtilities.m1036dp(1.33f);
                                        } else {
                                            f9 = measuredWidth;
                                        }
                                        if (imageViewEmoji2.selected) {
                                            f9 *= 0.95f;
                                        }
                                        float f16 = (measuredWidth - f9) / 2.0f;
                                        x3 += f16;
                                        y3 += f16;
                                        measuredWidth = f9;
                                    }
                                    float f17 = x3;
                                    float fLerp3 = AndroidUtilities.lerp(f15, f17, CustomEmojiReactionsWindow.this.enterTransitionProgress);
                                    float fLerp4 = AndroidUtilities.lerp(y2, y3, CustomEmojiReactionsWindow.this.enterTransitionProgress);
                                    float f18 = f14;
                                    float measuredWidth2 = measuredWidth / reactionHolderView.loopImageView.getMeasuredWidth();
                                    fLerp = AndroidUtilities.lerp(f13, measuredWidth2, CustomEmojiReactionsWindow.this.enterTransitionProgress);
                                    float f19 = f13;
                                    if (reactionHolderView.position == 0) {
                                        fM1036dp = AndroidUtilities.m1036dp(6.0f);
                                        f11 = fM1036dp;
                                        f10 = f18;
                                        f12 = f10;
                                    } else {
                                        fM1036dp = reactionHolderView.selected ? AndroidUtilities.m1036dp(6.0f) : f18;
                                        f10 = fM1036dp;
                                        f11 = f10;
                                        f12 = f11;
                                    }
                                    canvas2.translate(fLerp3, fLerp4);
                                    canvas2.scale(fLerp, fLerp);
                                    if (this.enterTransitionOffsetX == f18 && this.enterTransitionOffsetY == f18) {
                                        CustomEmojiReactionsWindow customEmojiReactionsWindow13 = CustomEmojiReactionsWindow.this;
                                        this.enterTransitionOffsetX = AndroidUtilities.lerp((customEmojiReactionsWindow13.fromRect.left + f15) - f17, f18, customEmojiReactionsWindow13.enterTransitionProgress);
                                        CustomEmojiReactionsWindow customEmojiReactionsWindow14 = CustomEmojiReactionsWindow.this;
                                        this.enterTransitionOffsetY = AndroidUtilities.lerp((customEmojiReactionsWindow14.fromRect.top + y2) - y3, f18, customEmojiReactionsWindow14.enterTransitionProgress);
                                        this.enterTransitionScale = AndroidUtilities.lerp(f19 / measuredWidth2, f19, CustomEmojiReactionsWindow.this.enterTransitionProgress);
                                        this.enterTransitionScalePx = f17;
                                        this.enterTransitionScalePy = y3;
                                    }
                                    f6 = f10;
                                    f7 = f11;
                                    f8 = f12;
                                } else {
                                    f5 = 2.0f;
                                    canvas2.translate(childAt.getX() + reactionHolderView.loopImageView.getX(), childAt.getY() + reactionHolderView.loopImageView.getY());
                                    f6 = 0.0f;
                                    f7 = 0.0f;
                                    f8 = 0.0f;
                                    fM1036dp = 0.0f;
                                    fLerp = 1.0f;
                                }
                                if (imageViewEmoji2 != null) {
                                    if (imageViewEmoji2.selected) {
                                        float measuredWidth3 = reactionHolderView.getMeasuredWidth() / f5;
                                        float measuredHeight = reactionHolderView.getMeasuredHeight() / f5;
                                        float measuredWidth4 = reactionHolderView.getMeasuredWidth() - AndroidUtilities.m1036dp(f5);
                                        float fLerp5 = AndroidUtilities.lerp(measuredWidth4, (imageViewEmoji2.getMeasuredWidth() - AndroidUtilities.m1036dp(f5)) / fLerp, CustomEmojiReactionsWindow.this.enterTransitionProgress);
                                        RectF rectF7 = AndroidUtilities.rectTmp;
                                        float f20 = fLerp5 / f5;
                                        i3 = i9;
                                        rectF7.set(measuredWidth3 - f20, measuredHeight - f20, measuredWidth3 + f20, measuredHeight + f20);
                                        float fLerp6 = AndroidUtilities.lerp(measuredWidth4 / f5, AndroidUtilities.m1036dp(4.0f), CustomEmojiReactionsWindow.this.enterTransitionProgress);
                                        canvas2.drawRoundRect(rectF7, fLerp6, fLerp6, CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.selectorPaint);
                                    } else {
                                        i3 = i9;
                                    }
                                    z = false;
                                    reactionHolderView.drawSelected = false;
                                    if (f7 != 0.0f) {
                                        ImageReceiver imageReceiver = reactionHolderView.loopImageView.getImageReceiver();
                                        reactionHolderView.checkPlayLoopImage();
                                        AnimatedEmojiDrawable animatedEmojiDrawable = reactionHolderView.loopImageView.animatedEmojiDrawable;
                                        if (animatedEmojiDrawable != null && animatedEmojiDrawable.getImageReceiver() != null) {
                                            imageReceiver = reactionHolderView.loopImageView.animatedEmojiDrawable.getImageReceiver();
                                        }
                                        int[] roundRadius = imageReceiver.getRoundRadius();
                                        i4 = 4;
                                        for (int i10 = 0; i10 < 4; i10++) {
                                            this.radiusTmp[i10] = roundRadius[i10];
                                        }
                                        f4 = 0.0f;
                                        imageReceiver.setRoundRadius((int) AndroidUtilities.lerp(fM1036dp, 0.0f, CustomEmojiReactionsWindow.this.enterTransitionProgress), (int) AndroidUtilities.lerp(f6, 0.0f, CustomEmojiReactionsWindow.this.enterTransitionProgress), (int) AndroidUtilities.lerp(f8, 0.0f, CustomEmojiReactionsWindow.this.enterTransitionProgress), (int) AndroidUtilities.lerp(f7, 0.0f, CustomEmojiReactionsWindow.this.enterTransitionProgress));
                                        reactionHolderView.draw(canvas2);
                                        imageReceiver.setRoundRadius(this.radiusTmp);
                                    } else {
                                        reactionHolderView.draw(canvas2);
                                        f4 = 0.0f;
                                        i4 = 4;
                                    }
                                    reactionHolderView.drawSelected = true;
                                    if (!imageViewEmoji2.notDraw) {
                                        imageViewEmoji2.notDraw = true;
                                        imageViewEmoji2.invalidate();
                                    }
                                } else {
                                    i3 = i9;
                                    z = false;
                                    f4 = 0.0f;
                                    i4 = 4;
                                    if (reactionHolderView.hasEnterAnimation && reactionHolderView.loopImageView.getImageReceiver().getLottieAnimation() == null) {
                                        float alpha = reactionHolderView.enterImageView.getImageReceiver().getAlpha();
                                        reactionHolderView.enterImageView.getImageReceiver().setAlpha((1.0f - fClamp) * alpha);
                                        reactionHolderView.enterImageView.draw(canvas2);
                                        reactionHolderView.enterImageView.getImageReceiver().setAlpha(alpha);
                                    } else {
                                        reactionHolderView.checkPlayLoopImage();
                                        ImageReceiver imageReceiver2 = reactionHolderView.loopImageView.getImageReceiver();
                                        AnimatedEmojiDrawable animatedEmojiDrawable2 = reactionHolderView.loopImageView.animatedEmojiDrawable;
                                        if (animatedEmojiDrawable2 != null && animatedEmojiDrawable2.getImageReceiver() != null) {
                                            imageReceiver2 = reactionHolderView.loopImageView.animatedEmojiDrawable.getImageReceiver();
                                        }
                                        float alpha2 = imageReceiver2.getAlpha();
                                        imageReceiver2.setAlpha((1.0f - fClamp) * alpha2);
                                        reactionHolderView.loopImageView.draw(canvas2);
                                        imageReceiver2.setAlpha(alpha2);
                                    }
                                }
                                if (reactionHolderView.loopImageView.getVisibility() != 0) {
                                    invalidate();
                                }
                                f3 = 1.0f;
                            } else {
                                i3 = i9;
                                f4 = f14;
                                i4 = i5;
                                float x4 = (childAt.getX() + CustomEmojiReactionsWindow.this.drawingRect.width()) - CustomEmojiReactionsWindow.this.reactionsContainerLayout.rect.width();
                                float y4 = childAt.getY();
                                CustomEmojiReactionsWindow customEmojiReactionsWindow15 = CustomEmojiReactionsWindow.this;
                                canvas2.translate(x4, (y4 + customEmojiReactionsWindow15.fromRect.top) - customEmojiReactionsWindow15.drawingRect.top);
                                f3 = 1.0f;
                                canvas2.saveLayerAlpha(0.0f, 0.0f, childAt.getMeasuredWidth(), childAt.getMeasuredHeight(), (int) ((1.0f - fClamp) * f2), 31);
                                float f21 = CustomEmojiReactionsWindow.this.enterTransitionProgress;
                                canvas2.scale(1.0f - f21, 1.0f - f21, r9.getMeasuredWidth() >> 1, r9.getMeasuredHeight() >> 1);
                                childAt.draw(canvas2);
                                canvas2.restore();
                            }
                            canvas2.restore();
                        }
                        i9 = i3 + 1;
                        f14 = f4;
                        i5 = i4;
                        f13 = f3;
                        i8 = -1;
                    }
                    canvas2.restoreToCount(iSave);
                }
                super.dispatchDraw(canvas);
                if (CustomEmojiReactionsWindow.this.frameDrawCount < 5) {
                    if (CustomEmojiReactionsWindow.this.frameDrawCount == 3) {
                        i2 = 1;
                        CustomEmojiReactionsWindow.this.reactionsContainerLayout.setSkipDraw(true);
                    } else {
                        i2 = 1;
                    }
                    CustomEmojiReactionsWindow.this.frameDrawCount += i2;
                }
                CustomEmojiReactionsWindow.this.selectAnimatedEmojiDialog.drawBigReaction(canvas2, this);
                if (CustomEmojiReactionsWindow.this.type == 5) {
                    canvas2.restore();
                }
                if (CustomEmojiReactionsWindow.this.valueAnimator != null) {
                    invalidate();
                }
                HwEmojis.exec();
            }
        }
    }

    public float getBlurOffset() {
        int i = this.type;
        ContainerView containerView = this.containerView;
        if (i == 1) {
            return containerView.getY() - AndroidUtilities.statusBarHeight;
        }
        return containerView.getY() + this.windowView.getY();
    }

    public void setRecentReactions(List<ReactionsLayoutInBubble.VisibleReaction> list) {
        this.selectAnimatedEmojiDialog.setRecentReactions(list);
    }

    public SelectAnimatedEmojiDialog getSelectAnimatedEmojiDialog() {
        return this.selectAnimatedEmojiDialog;
    }
}
