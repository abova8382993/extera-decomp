package org.telegram.p035ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.android.p006dx.p009io.Opcodes;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.api.dto.BadgeDTO;
import com.exteragram.messenger.utils.p020ui.ChatHeaderUiHelper;
import com.google.android.material.timepicker.TimeModel;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.p035ui.ActionBar.ActionBar;
import org.telegram.p035ui.ActionBar.ActionBarPopupWindow;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.SimpleTextView;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.ChatActivity;
import org.telegram.p035ui.Components.AnimatedEmojiDrawable;
import org.telegram.p035ui.Components.AutoDeletePopupWrapper;
import org.telegram.p035ui.Components.Forum.ForumUtilities;
import org.telegram.p035ui.Components.SharedMediaLayout;
import org.telegram.p035ui.Stories.StoriesUtilities;
import org.telegram.p035ui.Stories.StoryViewer;
import org.telegram.p035ui.TopicsFragment;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes3.dex */
public class ChatAvatarContainer extends FrameLayout implements NotificationCenter.NotificationCenterDelegate {
    private ActionBar actionBar;
    public boolean allowDrawStories;
    public boolean allowShorterStatus;
    private AnimatedTextView animatedSubtitleTextView;
    private AvatarDrawable avatarDrawable;
    public BackupImageView avatarImageView;
    private int avatarSizeInDp;
    private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable botVerificationDrawable;
    public ButtonBounce bounce;
    private int currentAccount;
    private int currentConnectionState;
    StatusDrawable currentTypingDrawable;
    private Drawable emojiStatusDefaultDrawable;
    private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable emojiStatusDrawable;
    private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable emojiStatusDrawable2;
    private boolean glassMode;
    public boolean ignoreTouches;
    private boolean[] isOnline;
    private int largerWidth;
    private CharSequence lastSubtitle;
    private int lastSubtitleColorKey;
    private int lastWidth;
    private int leftPadding;
    private boolean occupyStatusBar;
    private Runnable onLongClick;
    private int onlineCount;
    private Integer overrideSubtitleColor;
    private ChatActivity parentFragment;
    public boolean premiumIconHiddable;
    private boolean pressed;
    private Theme.ResourcesProvider resourcesProvider;
    private int rightAvatarPadding;
    private String rightDrawable2ContentDescription;
    private String rightDrawableContentDescription;
    private boolean rightDrawableIsScam;
    private boolean rightDrawableIsScamOrVerified;
    private boolean secretChatTimer;
    private SharedMediaLayout.SharedMediaPreloader sharedMediaPreloader;
    private boolean showingSavedMessagesHint;
    private ImageView starBgItem;
    private ImageView starFgItem;
    public boolean stars;
    private StatusDrawable[] statusDrawables;
    public boolean[] statusMadeShorter;
    private Integer storiesForceState;
    private boolean subtitleIsThinkingBot;
    private AtomicReference<SimpleTextView> subtitleTextLargerCopyView;
    private SimpleTextView subtitleTextView;
    private ImageView timeItem;
    private TimerDrawable timerDrawable;
    private AnimatorSet titleAnimation;
    private AtomicReference<SimpleTextView> titleTextLargerCopyView;
    private SimpleTextView titleTextView;
    private Drawable verifiedBackground;
    private Drawable verifiedCheck;

    public boolean canSearch() {
        return false;
    }

    public boolean onAvatarClick() {
        return false;
    }

    public void openSearch() {
    }

    public boolean useAnimatedSubtitle() {
        return false;
    }

    public void hideSubtitle() {
        if (getSubtitleTextView() != null) {
            getSubtitleTextView().setVisibility(8);
        }
    }

    public void setStoriesForceState(Integer num) {
        this.storiesForceState = num;
    }

    public class SimpleTextConnectedView extends SimpleTextView {
        private AtomicReference<SimpleTextView> reference;

        public SimpleTextConnectedView(Context context, AtomicReference<SimpleTextView> atomicReference) {
            super(context);
            this.reference = atomicReference;
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            SimpleTextView simpleTextView;
            AtomicReference<SimpleTextView> atomicReference = this.reference;
            if (atomicReference != null && (simpleTextView = atomicReference.get()) != null) {
                simpleTextView.setTranslationY(f);
            }
            super.setTranslationY(f);
        }

        @Override // org.telegram.p035ui.ActionBar.SimpleTextView
        public boolean setText(CharSequence charSequence) {
            SimpleTextView simpleTextView;
            AtomicReference<SimpleTextView> atomicReference = this.reference;
            if (atomicReference != null && (simpleTextView = atomicReference.get()) != null) {
                simpleTextView.setText(charSequence);
            }
            return super.setText(charSequence);
        }
    }

    public ChatAvatarContainer(Context context, BaseFragment baseFragment, boolean z) {
        this(context, baseFragment, z, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x0318  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ChatAvatarContainer(android.content.Context r22, org.telegram.p035ui.ActionBar.BaseFragment r23, boolean r24, final org.telegram.ui.ActionBar.Theme.ResourcesProvider r25) {
        /*
            Method dump skipped, instruction units count: 955
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.ChatAvatarContainer.<init>(android.content.Context, org.telegram.ui.ActionBar.BaseFragment, boolean, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAvatarContainer$1 */
    public class C41611 extends BackupImageView {
        StoriesUtilities.AvatarStoryParams params;
        final /* synthetic */ boolean val$avatarClickable;
        final /* synthetic */ BaseFragment val$baseFragment;
        final /* synthetic */ Theme.ResourcesProvider val$resourcesProvider;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C41611(Context context, BaseFragment baseFragment, boolean z, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            this.val$baseFragment = baseFragment;
            this.val$avatarClickable = z;
            this.val$resourcesProvider = resourcesProvider;
            this.params = new AnonymousClass1(true);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAvatarContainer$1$1, reason: invalid class name */
        public class AnonymousClass1 extends StoriesUtilities.AvatarStoryParams {
            public AnonymousClass1(boolean z) {
                super(z);
            }

            @Override // org.telegram.ui.Stories.StoriesUtilities.AvatarStoryParams
            public void openStory(long j, Runnable runnable) {
                C41611.this.val$baseFragment.getOrCreateStoryViewer().open(C41611.this.getContext(), j, new StoryViewer.PlaceProvider() { // from class: org.telegram.ui.Components.ChatAvatarContainer$1$1$$ExternalSyntheticLambda0
                    @Override // org.telegram.ui.Stories.StoryViewer.PlaceProvider
                    public final boolean findView(long j2, int i, int i2, int i3, StoryViewer.TransitionViewHolder transitionViewHolder) {
                        return this.f$0.lambda$openStory$0(j2, i, i2, i3, transitionViewHolder);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ boolean lambda$openStory$0(long j, int i, int i2, int i3, StoryViewer.TransitionViewHolder transitionViewHolder) {
                C41611 c41611 = C41611.this;
                ImageReceiver imageReceiver = c41611.imageReceiver;
                transitionViewHolder.storyImage = imageReceiver;
                transitionViewHolder.crossfadeToAvatarImage = imageReceiver;
                StoriesUtilities.AvatarStoryParams avatarStoryParams = c41611.params;
                transitionViewHolder.params = avatarStoryParams;
                transitionViewHolder.isLive = avatarStoryParams.drawnLive;
                BackupImageView backupImageView = ChatAvatarContainer.this.avatarImageView;
                transitionViewHolder.view = backupImageView;
                transitionViewHolder.alpha = backupImageView.getAlpha();
                transitionViewHolder.clipTop = 0.0f;
                transitionViewHolder.clipBottom = AndroidUtilities.displaySize.y;
                transitionViewHolder.clipParent = (View) C41611.this.getParent();
                return true;
            }
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            if (this.val$avatarClickable && getImageReceiver().hasNotThumb()) {
                accessibilityNodeInfo.setText(LocaleController.getString(C2797R.string.AccDescrProfilePicture));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, LocaleController.getString(C2797R.string.Open)));
            } else {
                accessibilityNodeInfo.setVisibleToUser(false);
            }
        }

        @Override // org.telegram.p035ui.Components.BackupImageView, android.view.View
        public void onDraw(Canvas canvas) {
            long dialogId;
            if (ChatAvatarContainer.this.allowDrawStories && this.animatedEmojiDrawable == null) {
                this.params.originalAvatarRect.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
                StoriesUtilities.AvatarStoryParams avatarStoryParams = this.params;
                avatarStoryParams.drawSegments = true;
                avatarStoryParams.drawInside = true;
                avatarStoryParams.resourcesProvider = this.val$resourcesProvider;
                if (ChatAvatarContainer.this.storiesForceState != null) {
                    this.params.forceState = ChatAvatarContainer.this.storiesForceState.intValue();
                }
                if (ChatAvatarContainer.this.parentFragment != null) {
                    dialogId = ChatAvatarContainer.this.parentFragment.getDialogId();
                } else {
                    BaseFragment baseFragment = this.val$baseFragment;
                    dialogId = baseFragment instanceof TopicsFragment ? ((TopicsFragment) baseFragment).getDialogId() : 0L;
                }
                StoriesUtilities.drawAvatarWithStory(dialogId, canvas, this.imageReceiver, this.params);
                return;
            }
            super.onDraw(canvas);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (ChatAvatarContainer.this.allowDrawStories && this.params.checkOnTouchEvent(motionEvent, this)) {
                return true;
            }
            return super.onTouchEvent(motionEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (onAvatarClick()) {
            return;
        }
        openProfile(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(Theme.ResourcesProvider resourcesProvider, View view) {
        if (this.secretChatTimer) {
            this.parentFragment.showDialog(AlertsCreator.createTTLAlert(getContext(), this.parentFragment.getCurrentEncryptedChat(), resourcesProvider).create());
        } else {
            openSetTimer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        openProfile(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3() {
        this.pressed = false;
        this.bounce.setPressed(false);
        if (canSearch()) {
            openSearch();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && canSearch()) {
            this.pressed = true;
            this.bounce.setPressed(true);
            AndroidUtilities.cancelRunOnUIThread(this.onLongClick);
            AndroidUtilities.runOnUIThread(this.onLongClick, ViewConfiguration.getLongPressTimeout());
            return true;
        }
        if ((motionEvent.getAction() == 1 || motionEvent.getAction() == 3) && this.pressed) {
            this.bounce.setPressed(false);
            this.pressed = false;
            if (isClickable()) {
                openProfile(false);
            }
            AndroidUtilities.cancelRunOnUIThread(this.onLongClick);
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public void setPressed(boolean z) {
        super.setPressed(z);
        this.bounce.setPressed(z);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        canvas.save();
        float scale = this.bounce.getScale(0.02f);
        canvas.scale(scale, scale, getWidth() / 2.0f, getHeight() - (ActionBar.getCurrentActionBarHeight() / 2.0f));
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View view, long j) {
        ImageView imageView;
        if (view == this.avatarImageView && (imageView = this.timeItem) != null && imageView.getVisibility() == 0) {
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(view.getX(), view.getY(), view.getX() + view.getWidth(), view.getY() + view.getHeight());
            canvas.saveLayer(rectF, null);
            boolean zDrawChild = super.drawChild(canvas, view, j);
            canvas.drawCircle(this.timeItem.getX() + (this.timeItem.getWidth() / 2.0f), this.timeItem.getY() + (this.timeItem.getHeight() / 2.0f), AndroidUtilities.dpf2(11.5f) * this.timeItem.getScaleX(), Theme.PAINT_CLEAR);
            canvas.restore();
            return zDrawChild;
        }
        return super.drawChild(canvas, view, j);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.ignoreTouches) {
            return false;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void setTitleExpand(boolean z) {
        int iM1036dp = z ? AndroidUtilities.m1036dp(10.0f) : 0;
        if (this.titleTextView.getPaddingRight() != iM1036dp) {
            this.titleTextView.setPadding(0, AndroidUtilities.m1036dp(6.0f), iM1036dp, AndroidUtilities.m1036dp(12.0f));
            requestLayout();
            invalidate();
        }
    }

    public void setOverrideSubtitleColor(Integer num) {
        this.overrideSubtitleColor = num;
    }

    public void setAvatarSizeInDp(int i) {
        if (this.avatarSizeInDp != i) {
            this.avatarSizeInDp = i;
            BackupImageView backupImageView = this.avatarImageView;
            if (backupImageView != null) {
                backupImageView.setRoundRadius(ExteraConfig.getAvatarCorners(i));
            }
            requestLayout();
        }
    }

    public boolean openSetTimer() {
        int i;
        if (this.parentFragment.getParentActivity() == null) {
            return false;
        }
        TLRPC.Chat currentChat = this.parentFragment.getCurrentChat();
        if (currentChat != null && !ChatObject.canUserDoAdminAction(currentChat, 13)) {
            if (this.timeItem.getTag() != null) {
                this.parentFragment.showTimerHint();
            }
            return false;
        }
        TLRPC.ChatFull currentChatInfo = this.parentFragment.getCurrentChatInfo();
        TLRPC.UserFull currentUserInfo = this.parentFragment.getCurrentUserInfo();
        if (currentUserInfo != null) {
            i = currentUserInfo.ttl_period;
        } else {
            i = currentChatInfo != null ? currentChatInfo.ttl_period : 0;
        }
        AutoDeletePopupWrapper autoDeletePopupWrapper = new AutoDeletePopupWrapper(getContext(), null, new AutoDeletePopupWrapper.Callback() { // from class: org.telegram.ui.Components.ChatAvatarContainer.2
            @Override // org.telegram.ui.Components.AutoDeletePopupWrapper.Callback
            public void dismiss() {
                ActionBarPopupWindow actionBarPopupWindow = actionBarPopupWindowArr[0];
                if (actionBarPopupWindow != null) {
                    actionBarPopupWindow.dismiss();
                }
            }

            @Override // org.telegram.ui.Components.AutoDeletePopupWrapper.Callback
            public void setAutoDeleteHistory(int i2, int i3) {
                UndoView undoView;
                if (ChatAvatarContainer.this.parentFragment == null) {
                    return;
                }
                ChatAvatarContainer.this.parentFragment.getMessagesController().setDialogHistoryTTL(ChatAvatarContainer.this.parentFragment.getDialogId(), i2);
                TLRPC.ChatFull currentChatInfo2 = ChatAvatarContainer.this.parentFragment.getCurrentChatInfo();
                TLRPC.UserFull currentUserInfo2 = ChatAvatarContainer.this.parentFragment.getCurrentUserInfo();
                if ((currentUserInfo2 == null && currentChatInfo2 == null) || (undoView = ChatAvatarContainer.this.parentFragment.getUndoView()) == null) {
                    return;
                }
                undoView.showWithAction(ChatAvatarContainer.this.parentFragment.getDialogId(), i3, ChatAvatarContainer.this.parentFragment.getCurrentUser(), Integer.valueOf(currentUserInfo2 != null ? currentUserInfo2.ttl_period : currentChatInfo2.ttl_period), (Runnable) null, (Runnable) null);
            }
        }, true, 0, this.resourcesProvider);
        autoDeletePopupWrapper.lambda$updateItems$7(i);
        int i2 = -2;
        ActionBarPopupWindow actionBarPopupWindow = new ActionBarPopupWindow(autoDeletePopupWrapper.windowLayout, i2, i2) { // from class: org.telegram.ui.Components.ChatAvatarContainer.3
            @Override // org.telegram.p035ui.ActionBar.ActionBarPopupWindow, android.widget.PopupWindow
            public void dismiss() {
                super.dismiss();
                if (ChatAvatarContainer.this.parentFragment != null) {
                    ChatAvatarContainer.this.parentFragment.dimBehindView(false);
                }
            }
        };
        final ActionBarPopupWindow[] actionBarPopupWindowArr = {actionBarPopupWindow};
        actionBarPopupWindow.setPauseNotifications(true);
        actionBarPopupWindowArr[0].setDismissAnimationDuration(Opcodes.REM_INT_LIT8);
        actionBarPopupWindowArr[0].setOutsideTouchable(true);
        actionBarPopupWindowArr[0].setClippingEnabled(true);
        actionBarPopupWindowArr[0].setAnimationStyle(C2797R.style.PopupContextAnimation);
        actionBarPopupWindowArr[0].setFocusable(true);
        autoDeletePopupWrapper.windowLayout.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(1000.0f), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(1000.0f), Integer.MIN_VALUE));
        actionBarPopupWindowArr[0].setInputMethodMode(2);
        actionBarPopupWindowArr[0].getContentView().setFocusableInTouchMode(true);
        ActionBarPopupWindow actionBarPopupWindow2 = actionBarPopupWindowArr[0];
        BackupImageView backupImageView = this.avatarImageView;
        actionBarPopupWindow2.showAtLocation(backupImageView, 0, (int) (backupImageView.getX() + getX()), (int) this.avatarImageView.getY());
        this.parentFragment.dimBehindView(true);
        return true;
    }

    public void openProfile(boolean z) {
        openProfile(z, true, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void openProfile(boolean r19, boolean r20, boolean r21) {
        /*
            Method dump skipped, instruction units count: 501
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.ChatAvatarContainer.openProfile(boolean, boolean, boolean):void");
    }

    public void setOccupyStatusBar(boolean z) {
        this.occupyStatusBar = z;
    }

    public void setTitleColors(int i, int i2) {
        this.titleTextView.setTextColor(i);
        this.subtitleTextView.setTextColor(i2);
        this.subtitleTextView.setTag(Integer.valueOf(i2));
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i) + this.titleTextView.getPaddingRight();
        int iM1036dp = size - AndroidUtilities.m1036dp((this.avatarImageView.getVisibility() == 0 ? this.avatarSizeInDp + 12 : 0) + 16);
        this.avatarImageView.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(this.avatarSizeInDp), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(this.avatarSizeInDp), TLObject.FLAG_30));
        this.titleTextView.measure(View.MeasureSpec.makeMeasureSpec(iM1036dp, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(32.0f) + this.titleTextView.getPaddingRight(), Integer.MIN_VALUE));
        SimpleTextView simpleTextView = this.subtitleTextView;
        if (simpleTextView != null) {
            simpleTextView.measure(View.MeasureSpec.makeMeasureSpec(iM1036dp, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(20.0f), Integer.MIN_VALUE));
        } else {
            AnimatedTextView animatedTextView = this.animatedSubtitleTextView;
            if (animatedTextView != null) {
                animatedTextView.measure(View.MeasureSpec.makeMeasureSpec(iM1036dp, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(20.0f), Integer.MIN_VALUE));
            }
        }
        ImageView imageView = this.timeItem;
        if (imageView != null) {
            imageView.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(34.0f), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(34.0f), TLObject.FLAG_30));
        }
        ImageView imageView2 = this.starBgItem;
        if (imageView2 != null) {
            imageView2.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(20.0f), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(20.0f), TLObject.FLAG_30));
        }
        ImageView imageView3 = this.starFgItem;
        if (imageView3 != null) {
            imageView3.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(20.0f), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(20.0f), TLObject.FLAG_30));
        }
        setMeasuredDimension(size, View.MeasureSpec.getSize(i2));
        int i3 = this.lastWidth;
        if (i3 != -1 && i3 != size && i3 > size) {
            fadeOutToLessWidth(i3);
        }
        SimpleTextView simpleTextView2 = this.titleTextLargerCopyView.get();
        if (simpleTextView2 != null) {
            simpleTextView2.measure(View.MeasureSpec.makeMeasureSpec(this.largerWidth - AndroidUtilities.m1036dp((this.avatarImageView.getVisibility() == 0 ? this.avatarSizeInDp + 12 : 0) + 16), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(24.0f), Integer.MIN_VALUE));
        }
        this.lastWidth = size;
    }

    private void fadeOutToLessWidth(int i) {
        this.largerWidth = i;
        View view = (SimpleTextView) this.titleTextLargerCopyView.get();
        if (view != null) {
            removeView(view);
        }
        SimpleTextView simpleTextView = new SimpleTextView(getContext());
        this.titleTextLargerCopyView.set(simpleTextView);
        simpleTextView.setTextColor(getThemedColor(Theme.key_actionBarDefaultTitle));
        simpleTextView.setTextSizePx(AndroidUtilities.m1036dp(this.glassMode ? 17.5f : 18.0f));
        simpleTextView.setGravity(3);
        simpleTextView.setTypeface(AndroidUtilities.bold());
        simpleTextView.setLeftDrawableTopPadding(-AndroidUtilities.m1036dp(1.3f));
        simpleTextView.setRightDrawable(this.titleTextView.getRightDrawable());
        simpleTextView.setRightDrawable2(this.titleTextView.getRightDrawable2());
        simpleTextView.setRightDrawableOutside(this.titleTextView.getRightDrawableOutside());
        simpleTextView.setLeftDrawable(this.titleTextView.getLeftDrawable());
        simpleTextView.setText(this.titleTextView.getText());
        ViewPropertyAnimator duration = simpleTextView.animate().alpha(0.0f).setDuration(350L);
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
        duration.setInterpolator(cubicBezierInterpolator).withEndAction(new Runnable() { // from class: org.telegram.ui.Components.ChatAvatarContainer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$fadeOutToLessWidth$4();
            }
        }).start();
        addView(simpleTextView);
        View view2 = (SimpleTextView) this.subtitleTextLargerCopyView.get();
        if (view2 != null) {
            removeView(view2);
        }
        SimpleTextView simpleTextView2 = new SimpleTextView(getContext());
        this.subtitleTextLargerCopyView.set(simpleTextView2);
        int i2 = Theme.key_actionBarDefaultSubtitle;
        simpleTextView2.setTextColor(getThemedColor(i2));
        simpleTextView2.setTag(Integer.valueOf(i2));
        simpleTextView2.setTextSizePx(AndroidUtilities.m1036dp(this.glassMode ? 13.5f : 14.0f));
        simpleTextView2.setGravity(3);
        SimpleTextView simpleTextView3 = this.subtitleTextView;
        if (simpleTextView3 != null) {
            simpleTextView2.setText(simpleTextView3.getText());
        } else {
            AnimatedTextView animatedTextView = this.animatedSubtitleTextView;
            if (animatedTextView != null) {
                simpleTextView2.setText(animatedTextView.getText());
            }
        }
        simpleTextView2.animate().alpha(0.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator).withEndAction(new Runnable() { // from class: org.telegram.ui.Components.ChatAvatarContainer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$fadeOutToLessWidth$5();
            }
        }).start();
        addView(simpleTextView2);
        setClipChildren(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fadeOutToLessWidth$4() {
        SimpleTextView simpleTextView = this.titleTextLargerCopyView.get();
        if (simpleTextView != null) {
            removeView(simpleTextView);
            this.titleTextLargerCopyView.set(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fadeOutToLessWidth$5() {
        SimpleTextView simpleTextView = this.subtitleTextLargerCopyView.get();
        if (simpleTextView != null) {
            removeView(simpleTextView);
            this.subtitleTextLargerCopyView.set(null);
            if (this.allowDrawStories) {
                return;
            }
            setClipChildren(true);
        }
    }

    public void setGlassMode() {
        SimpleTextView simpleTextView = this.titleTextView;
        if (simpleTextView != null) {
            simpleTextView.setTextSizePx(AndroidUtilities.m1036dp(17.5f));
        }
        SimpleTextView simpleTextView2 = this.subtitleTextView;
        if (simpleTextView2 != null) {
            simpleTextView2.setTextSizePx(AndroidUtilities.m1036dp(13.5f));
        }
        this.glassMode = true;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float f;
        float f2;
        int iM1036dp;
        int currentActionBarHeight = ((ActionBar.getCurrentActionBarHeight() - this.avatarImageView.getMeasuredHeight()) / 2) + (this.occupyStatusBar ? AndroidUtilities.statusBarHeight : 0);
        boolean zIsMaterial3ChatHeaderStyle = ChatHeaderUiHelper.isMaterial3ChatHeaderStyle();
        boolean z2 = this.glassMode;
        if (zIsMaterial3ChatHeaderStyle) {
            f = z2 ? 26.66f : 27.0f;
            f2 = this.avatarSizeInDp + (z2 ? 10.66f : 12.0f);
        } else {
            f = z2 ? 23.66f : 24.0f;
            f2 = z2 ? 48.66f : 54.0f;
        }
        int iM1036dp2 = AndroidUtilities.m1036dp(f) + currentActionBarHeight;
        BackupImageView backupImageView = this.avatarImageView;
        int i5 = this.leftPadding;
        backupImageView.layout(i5, currentActionBarHeight, backupImageView.getMeasuredWidth() + i5, this.avatarImageView.getMeasuredHeight() + currentActionBarHeight);
        int i6 = this.leftPadding;
        if (this.avatarImageView.getVisibility() == 0) {
            iM1036dp = AndroidUtilities.m1036dp(f2);
        } else {
            iM1036dp = AndroidUtilities.m1036dp(this.glassMode ? 12.0f : 0.0f);
        }
        int i7 = i6 + iM1036dp + this.rightAvatarPadding;
        SimpleTextView simpleTextView = this.titleTextLargerCopyView.get();
        int visibility = getSubtitleTextView().getVisibility();
        SimpleTextView simpleTextView2 = this.titleTextView;
        if (visibility != 8) {
            simpleTextView2.layout(i7, (AndroidUtilities.m1036dp(1.66f) + currentActionBarHeight) - this.titleTextView.getPaddingTop(), this.titleTextView.getMeasuredWidth() + i7, (((this.titleTextView.getTextHeight() + currentActionBarHeight) + AndroidUtilities.m1036dp(1.66f)) - this.titleTextView.getPaddingTop()) + this.titleTextView.getPaddingBottom());
            if (simpleTextView != null) {
                simpleTextView.layout(i7, AndroidUtilities.m1036dp(1.66f) + currentActionBarHeight, simpleTextView.getMeasuredWidth() + i7, simpleTextView.getTextHeight() + currentActionBarHeight + AndroidUtilities.m1036dp(1.66f));
            }
        } else {
            simpleTextView2.layout(i7, (AndroidUtilities.m1036dp(10.0f) + currentActionBarHeight) - this.titleTextView.getPaddingTop(), this.titleTextView.getMeasuredWidth() + i7, (((this.titleTextView.getTextHeight() + currentActionBarHeight) + AndroidUtilities.m1036dp(10.0f)) - this.titleTextView.getPaddingTop()) + this.titleTextView.getPaddingBottom());
            if (simpleTextView != null) {
                simpleTextView.layout(i7, AndroidUtilities.m1036dp(10.0f) + currentActionBarHeight, simpleTextView.getMeasuredWidth() + i7, simpleTextView.getTextHeight() + currentActionBarHeight + AndroidUtilities.m1036dp(10.0f));
            }
        }
        if (this.timeItem != null) {
            int iM1036dp3 = this.leftPadding + AndroidUtilities.m1036dp(this.avatarSizeInDp - 26);
            int iM1036dp4 = AndroidUtilities.m1036dp(this.avatarSizeInDp - 27) + currentActionBarHeight;
            ImageView imageView = this.timeItem;
            imageView.layout(iM1036dp3, iM1036dp4, imageView.getMeasuredWidth() + iM1036dp3, this.timeItem.getMeasuredHeight() + iM1036dp4);
        }
        if (this.starBgItem != null) {
            int iM1036dp5 = this.leftPadding + AndroidUtilities.m1036dp(this.avatarSizeInDp - 14);
            int iM1036dp6 = AndroidUtilities.m1036dp(this.avatarSizeInDp - 18) + currentActionBarHeight;
            ImageView imageView2 = this.starBgItem;
            imageView2.layout(iM1036dp5, iM1036dp6, imageView2.getMeasuredWidth() + iM1036dp5, this.starBgItem.getMeasuredHeight() + iM1036dp6);
        }
        if (this.starFgItem != null) {
            int iM1036dp7 = this.leftPadding + AndroidUtilities.m1036dp(this.avatarSizeInDp - 14);
            int iM1036dp8 = currentActionBarHeight + AndroidUtilities.m1036dp(this.avatarSizeInDp - 18);
            ImageView imageView3 = this.starFgItem;
            imageView3.layout(iM1036dp7, iM1036dp8, imageView3.getMeasuredWidth() + iM1036dp7, this.starFgItem.getMeasuredHeight() + iM1036dp8);
        }
        SimpleTextView simpleTextView3 = this.subtitleTextView;
        if (simpleTextView3 != null) {
            simpleTextView3.layout(i7, iM1036dp2, simpleTextView3.getMeasuredWidth() + i7, this.subtitleTextView.getTextHeight() + iM1036dp2);
        } else {
            AnimatedTextView animatedTextView = this.animatedSubtitleTextView;
            if (animatedTextView != null) {
                animatedTextView.layout(i7, iM1036dp2, animatedTextView.getMeasuredWidth() + i7, this.animatedSubtitleTextView.getTextHeight() + iM1036dp2);
            }
        }
        SimpleTextView simpleTextView4 = this.subtitleTextLargerCopyView.get();
        if (simpleTextView4 != null) {
            simpleTextView4.layout(i7, iM1036dp2, simpleTextView4.getMeasuredWidth() + i7, simpleTextView4.getTextHeight() + iM1036dp2);
        }
    }

    public void setLeftPadding(int i) {
        this.leftPadding = i;
    }

    public int getLeftPadding() {
        return this.leftPadding;
    }

    public void setRightAvatarPadding(int i) {
        this.rightAvatarPadding = i;
    }

    public void showTimeItem(boolean z) {
        ImageView imageView = this.timeItem;
        if (imageView != null && imageView.getTag() == null && this.avatarImageView.getVisibility() == 0) {
            this.timeItem.clearAnimation();
            this.timeItem.setVisibility(0);
            this.timeItem.setTag(1);
            ImageView imageView2 = this.timeItem;
            if (z) {
                imageView2.animate().setDuration(180L).alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setListener(null).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ChatAvatarContainer$$ExternalSyntheticLambda8
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$showTimeItem$6(valueAnimator);
                    }
                }).start();
                return;
            }
            imageView2.setAlpha(1.0f);
            this.timeItem.setScaleY(1.0f);
            this.timeItem.setScaleX(1.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showTimeItem$6(ValueAnimator valueAnimator) {
        invalidate();
    }

    public void hideTimeItem(boolean z) {
        ImageView imageView = this.timeItem;
        if (imageView == null || imageView.getTag() == null) {
            return;
        }
        this.timeItem.clearAnimation();
        this.timeItem.setTag(null);
        ImageView imageView2 = this.timeItem;
        if (z) {
            imageView2.animate().setDuration(180L).alpha(0.0f).scaleX(0.0f).scaleY(0.0f).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ChatAvatarContainer.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ChatAvatarContainer.this.timeItem.setVisibility(8);
                    super.onAnimationEnd(animator);
                }
            }).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ChatAvatarContainer$$ExternalSyntheticLambda9
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$hideTimeItem$7(valueAnimator);
                }
            }).start();
            return;
        }
        imageView2.setVisibility(8);
        this.timeItem.setAlpha(0.0f);
        this.timeItem.setScaleY(0.0f);
        this.timeItem.setScaleX(0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideTimeItem$7(ValueAnimator valueAnimator) {
        invalidate();
    }

    public void setTime(int i, boolean z) {
        if (this.timerDrawable == null) {
            return;
        }
        boolean z2 = this.stars;
        if (i != 0 || this.secretChatTimer) {
            if (!z2) {
                showTimeItem(z);
                this.timerDrawable.setTime(i);
            } else {
                hideTimeItem(z);
            }
        }
    }

    public void setStars(final boolean z, boolean z2) {
        ImageView imageView = this.starBgItem;
        if (imageView == null || this.starFgItem == null) {
            return;
        }
        this.stars = z;
        if (!z2) {
            imageView.setVisibility(z ? 0 : 4);
            this.starBgItem.setAlpha(z ? 1.0f : 0.0f);
            this.starBgItem.setScaleX(z ? 1.1f : 0.0f);
            this.starBgItem.setScaleY(z ? 1.1f : 0.0f);
            this.starFgItem.setVisibility(z ? 0 : 4);
            this.starFgItem.setAlpha(z ? 1.0f : 0.0f);
            this.starFgItem.setScaleX(z ? 1.0f : 0.0f);
            this.starFgItem.setScaleY(z ? 1.0f : 0.0f);
            return;
        }
        if (z) {
            imageView.setVisibility(0);
            this.starFgItem.setVisibility(0);
        }
        this.starBgItem.animate().alpha(z ? 1.0f : 0.0f).scaleX(z ? 1.1f : 0.0f).scaleY(z ? 1.1f : 0.0f).withEndAction(new Runnable() { // from class: org.telegram.ui.Components.ChatAvatarContainer$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setStars$8(z);
            }
        }).start();
        this.starFgItem.animate().alpha(z ? 1.0f : 0.0f).scaleX(z ? 1.0f : 0.0f).scaleY(z ? 1.0f : 0.0f).withEndAction(new Runnable() { // from class: org.telegram.ui.Components.ChatAvatarContainer$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setStars$9(z);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setStars$8(boolean z) {
        if (z) {
            return;
        }
        this.starBgItem.setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setStars$9(boolean z) {
        if (z) {
            return;
        }
        this.starFgItem.setVisibility(4);
    }

    public void setTitleIcons(Drawable drawable, Drawable drawable2) {
        this.titleTextView.setLeftDrawable(drawable);
        if (!this.rightDrawableIsScamOrVerified && !this.rightDrawableIsScam) {
            if (drawable2 != null) {
                this.rightDrawable2ContentDescription = LocaleController.getString(C2797R.string.NotificationsMuted);
            } else {
                this.rightDrawable2ContentDescription = null;
            }
            this.titleTextView.setRightDrawable2(drawable2);
        }
        checkActionBar(true);
    }

    public AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable getBotVerificationDrawable(long j, boolean z) {
        if (j == 0) {
            return null;
        }
        this.botVerificationDrawable.set(j, z);
        this.botVerificationDrawable.setColor(Integer.valueOf(getThemedColor(Theme.key_profile_verifiedBackground)));
        this.botVerificationDrawable.offset(0, AndroidUtilities.m1036dp(1.0f));
        return this.botVerificationDrawable;
    }

    public void setTitle(CharSequence charSequence) {
        setTitle(charSequence, false, false, false, false, null, null, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setTitle(CharSequence charSequence, boolean z, boolean z2, boolean z3, boolean z4, BadgeDTO badgeDTO, TLRPC.EmojiStatus emojiStatus, boolean z5) {
        Drawable drawable;
        if (charSequence != null) {
            charSequence = Emoji.replaceEmoji(charSequence, this.titleTextView.getPaint().getFontMetricsInt(), false);
        }
        this.titleTextView.setText(charSequence);
        Drawable rightDrawable = this.titleTextView.getRightDrawable();
        if (rightDrawable instanceof AnimatedEmojiDrawable.WrapSizeDrawable) {
            Drawable drawable2 = ((AnimatedEmojiDrawable.WrapSizeDrawable) rightDrawable).getDrawable();
            if (drawable2 instanceof AnimatedEmojiDrawable) {
                ((AnimatedEmojiDrawable) drawable2).removeView(this.titleTextView);
            }
        }
        Drawable rightDrawable2 = this.titleTextView.getRightDrawable2();
        if (rightDrawable2 instanceof AnimatedEmojiDrawable.WrapSizeDrawable) {
            Drawable drawable3 = ((AnimatedEmojiDrawable.WrapSizeDrawable) rightDrawable2).getDrawable();
            if (drawable3 instanceof AnimatedEmojiDrawable) {
                ((AnimatedEmojiDrawable) drawable3).removeView(this.titleTextView);
            }
        }
        Drawable drawable4 = null;
        this.rightDrawableContentDescription = null;
        this.rightDrawable2ContentDescription = null;
        this.rightDrawableIsScamOrVerified = false;
        this.rightDrawableIsScam = z || z2;
        this.titleTextView.setRightDrawableTopPadding(0);
        byte b2 = DialogObject.getEmojiStatusDocumentId(emojiStatus) != 0;
        ArrayList arrayList = new ArrayList();
        if (z || z2) {
            ScamDrawable scamDrawable = new ScamDrawable(11, !z ? 1 : 0);
            scamDrawable.setColor(getThemedColor(Theme.key_actionBarDefaultSubtitle));
            arrayList.add(scamDrawable);
            this.rightDrawable2ContentDescription = LocaleController.getString(C2797R.string.ScamMessage);
            this.rightDrawableIsScamOrVerified = true;
        }
        if (z3) {
            if (this.verifiedBackground == null) {
                this.verifiedBackground = ContextCompat.getDrawable(ApplicationLoader.applicationContext, C2797R.drawable.verified_area).mutate();
                this.verifiedCheck = ContextCompat.getDrawable(ApplicationLoader.applicationContext, C2797R.drawable.verified_check).mutate();
            }
            Drawable drawable5 = this.verifiedBackground;
            int themedColor = getThemedColor(Theme.key_profile_verifiedBackground);
            PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
            drawable5.setColorFilter(new PorterDuffColorFilter(themedColor, mode));
            this.verifiedCheck.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_profile_verifiedCheck), mode));
            arrayList.add(new CombinedDrawable(this.verifiedBackground, this.verifiedCheck));
            this.rightDrawable2ContentDescription = LocaleController.getString(C2797R.string.AccDescrVerified);
            this.rightDrawableIsScamOrVerified = true;
        }
        if (badgeDTO != null) {
            this.emojiStatusDrawable2.set(badgeDTO.getDocumentId(), z5);
            this.emojiStatusDrawable2.setParticles(true, false);
            this.emojiStatusDrawable2.setColor(Integer.valueOf(getThemedColor(Theme.key_profile_verifiedBackground)));
            if (b2 != false && !arrayList.isEmpty()) {
                arrayList.remove(arrayList.size() - 1);
            }
            arrayList.add(this.emojiStatusDrawable2);
            this.rightDrawableIsScamOrVerified = true;
        }
        if (b2 == true) {
            this.emojiStatusDrawable.set(DialogObject.getEmojiStatusDocumentId(emojiStatus), z5);
            this.emojiStatusDrawable.setColor(Integer.valueOf(getThemedColor(Theme.key_profile_verifiedBackground)));
            drawable = this.emojiStatusDrawable;
            if (!arrayList.isEmpty()) {
                drawable4 = (Drawable) arrayList.get(0);
            }
        } else if (!z4) {
            drawable = !arrayList.isEmpty() ? (Drawable) arrayList.get(0) : null;
            if (arrayList.size() > 1) {
                drawable4 = (Drawable) arrayList.get(1);
            }
        } else if (badgeDTO != null) {
            drawable = this.emojiStatusDrawable2;
            arrayList.remove(arrayList.size() - 1);
            if (!arrayList.isEmpty()) {
                drawable4 = (Drawable) arrayList.get(0);
            }
        } else {
            Drawable drawableMutate = ContextCompat.getDrawable(ApplicationLoader.applicationContext, C2797R.drawable.msg_premium_liststar).mutate();
            this.emojiStatusDefaultDrawable = drawableMutate;
            int i = Theme.key_profile_verifiedBackground;
            drawableMutate.setColorFilter(new PorterDuffColorFilter(getThemedColor(i), PorterDuff.Mode.MULTIPLY));
            this.emojiStatusDrawable.set(this.emojiStatusDefaultDrawable, z5);
            this.emojiStatusDrawable.setColor(Integer.valueOf(getThemedColor(i)));
            drawable = this.emojiStatusDrawable;
            this.rightDrawableContentDescription = LocaleController.getString(C2797R.string.AccDescrPremium);
            if (!arrayList.isEmpty()) {
                drawable4 = (Drawable) arrayList.get(0);
            }
        }
        this.titleTextView.setRightDrawable(drawable);
        this.titleTextView.setRightDrawable2(drawable4);
        checkActionBar(z5);
    }

    public void setSubtitle(CharSequence charSequence) {
        if (this.lastSubtitle == null) {
            SimpleTextView simpleTextView = this.subtitleTextView;
            if (simpleTextView != null) {
                simpleTextView.setText(charSequence);
            } else {
                AnimatedTextView animatedTextView = this.animatedSubtitleTextView;
                if (animatedTextView != null) {
                    animatedTextView.setText(charSequence);
                }
            }
        } else {
            this.lastSubtitle = charSequence;
        }
        checkActionBar(true);
    }

    public ImageView getTimeItem() {
        return this.timeItem;
    }

    public SimpleTextView getTitleTextView() {
        return this.titleTextView;
    }

    public View getSubtitleTextView() {
        SimpleTextView simpleTextView = this.subtitleTextView;
        if (simpleTextView != null) {
            return simpleTextView;
        }
        AnimatedTextView animatedTextView = this.animatedSubtitleTextView;
        if (animatedTextView != null) {
            return animatedTextView;
        }
        return null;
    }

    public TextPaint getSubtitlePaint() {
        SimpleTextView simpleTextView = this.subtitleTextView;
        return simpleTextView != null ? simpleTextView.getTextPaint() : this.animatedSubtitleTextView.getPaint();
    }

    public void onDestroy() {
        onDestroy(false);
    }

    public void onDestroy(boolean z) {
        SharedMediaLayout.SharedMediaPreloader sharedMediaPreloader = this.sharedMediaPreloader;
        if (sharedMediaPreloader != null) {
            sharedMediaPreloader.onDestroy(this.parentFragment, z);
        }
    }

    private void setTypingAnimation(boolean z) {
        SimpleTextView simpleTextView = this.subtitleTextView;
        if (simpleTextView == null) {
            return;
        }
        int i = 0;
        if (z) {
            try {
                int iIntValue = this.subtitleIsThinkingBot ? 0 : MessagesController.getInstance(this.currentAccount).getPrintingStringType(this.parentFragment.getDialogId(), this.parentFragment.getThreadId()).intValue();
                StatusDrawable statusDrawable = this.statusDrawables[iIntValue];
                if (statusDrawable == null) {
                    return;
                }
                SimpleTextView simpleTextView2 = this.subtitleTextView;
                if (iIntValue == 5) {
                    simpleTextView2.replaceTextWithDrawable(statusDrawable, "**oo**");
                    this.statusDrawables[iIntValue].setColor(getThemedColor(Theme.key_chat_status));
                    this.subtitleTextView.setLeftDrawable((Drawable) null);
                } else {
                    simpleTextView2.replaceTextWithDrawable(null, null);
                    this.statusDrawables[iIntValue].setColor(getThemedColor(Theme.key_chat_status));
                    this.subtitleTextView.setLeftDrawable(this.statusDrawables[iIntValue]);
                }
                this.currentTypingDrawable = this.statusDrawables[iIntValue];
                while (true) {
                    StatusDrawable[] statusDrawableArr = this.statusDrawables;
                    if (i >= statusDrawableArr.length) {
                        return;
                    }
                    StatusDrawable statusDrawable2 = statusDrawableArr[i];
                    if (statusDrawable2 != null) {
                        if (i == iIntValue) {
                            statusDrawable2.start();
                        } else {
                            statusDrawable2.stop();
                        }
                    }
                    i++;
                }
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        } else {
            this.currentTypingDrawable = null;
            simpleTextView.setLeftDrawable((Drawable) null);
            this.subtitleTextView.replaceTextWithDrawable(null, null);
            while (true) {
                StatusDrawable[] statusDrawableArr2 = this.statusDrawables;
                if (i >= statusDrawableArr2.length) {
                    return;
                }
                StatusDrawable statusDrawable3 = statusDrawableArr2[i];
                if (statusDrawable3 != null) {
                    statusDrawable3.stop();
                }
                i++;
            }
        }
    }

    public void updateSubtitle() {
        updateSubtitle(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:80:0x01cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateSubtitle(boolean r21) {
        /*
            Method dump skipped, instruction units count: 1129
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.ChatAvatarContainer.updateSubtitle(boolean):void");
    }

    public static CharSequence getChatSubtitle(TLRPC.Chat chat, TLRPC.ChatFull chatFull, int i) {
        TLRPC.ChatParticipants chatParticipants;
        int i2;
        String shortNumber;
        if (!ChatObject.isChannel(chat)) {
            if (ChatObject.isKickedFromChat(chat)) {
                return LocaleController.getString(C2797R.string.YouWereKicked);
            }
            if (ChatObject.isLeftFromChat(chat)) {
                return LocaleController.getString(C2797R.string.YouLeft);
            }
            int size = chat.participants_count;
            if (chatFull != null && (chatParticipants = chatFull.participants) != null) {
                size = chatParticipants.participants.size();
            }
            if (i > 1 && size != 0) {
                return String.format("%s, %s", LocaleController.formatPluralString("Members", size, new Object[0]), LocaleController.formatPluralString("OnlineCount", i, new Object[0]));
            }
            return LocaleController.formatPluralString("Members", size, new Object[0]);
        }
        if (chatFull != null && (i2 = chatFull.participants_count) != 0) {
            if (chat.megagroup) {
                if (i > 1) {
                    return String.format("%s, %s", LocaleController.formatPluralString("Members", i2, new Object[0]), LocaleController.formatPluralString("OnlineCount", Math.min(i, chatFull.participants_count), new Object[0]));
                }
                return LocaleController.formatPluralString("Members", i2, new Object[0]);
            }
            int[] iArr = new int[1];
            boolean zIsAccessibilityScreenReaderEnabled = AndroidUtilities.isAccessibilityScreenReaderEnabled();
            int i3 = chatFull.participants_count;
            if (zIsAccessibilityScreenReaderEnabled) {
                iArr[0] = i3;
                shortNumber = String.valueOf(i3);
            } else {
                shortNumber = LocaleController.formatShortNumber(i3, iArr);
            }
            if (chat.megagroup) {
                return LocaleController.formatPluralString("Members", iArr[0], new Object[0]).replace(String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(iArr[0])), shortNumber);
            }
            return LocaleController.formatPluralString("Subscribers", iArr[0], new Object[0]).replace(String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(iArr[0])), shortNumber);
        }
        if (!chat.megagroup) {
            if (ChatObject.isPublic(chat)) {
                return LocaleController.getString(C2797R.string.ChannelPublic).toLowerCase();
            }
            return LocaleController.getString(C2797R.string.ChannelPrivate).toLowerCase();
        }
        if (chatFull == null) {
            return LocaleController.getString(C2797R.string.Loading).toLowerCase();
        }
        if (chat.has_geo) {
            return LocaleController.getString(C2797R.string.MegaLocation).toLowerCase();
        }
        if (ChatObject.isPublic(chat)) {
            return LocaleController.getString(C2797R.string.MegaPublic).toLowerCase();
        }
        return LocaleController.getString(C2797R.string.MegaPrivate).toLowerCase();
    }

    public int getLastSubtitleColorKey() {
        return this.lastSubtitleColorKey;
    }

    public void setChatAvatar(TLRPC.Chat chat) {
        this.avatarDrawable.setInfo(this.currentAccount, chat);
        BackupImageView backupImageView = this.avatarImageView;
        if (backupImageView != null) {
            backupImageView.setForUserOrChat(chat, this.avatarDrawable);
            this.avatarImageView.setRoundRadius(ExteraConfig.getAvatarCorners(this.avatarSizeInDp, false, chat != null && chat.forum, ChatObject.hasStories(chat)));
        }
    }

    public void setUserAvatar(TLRPC.User user) {
        setUserAvatar(user, false);
    }

    public void setUserAvatar(TLRPC.User user, boolean z) {
        this.avatarDrawable.setInfo(this.currentAccount, user);
        if (UserObject.isReplyUser(user)) {
            this.avatarDrawable.setAvatarType(12);
            this.avatarDrawable.setScaleSize(0.8f);
            BackupImageView backupImageView = this.avatarImageView;
            if (backupImageView != null) {
                backupImageView.setImage((ImageLocation) null, (String) null, this.avatarDrawable, user);
                return;
            }
            return;
        }
        if (UserObject.isAnonymous(user)) {
            this.avatarDrawable.setAvatarType(21);
            this.avatarDrawable.setScaleSize(0.8f);
            BackupImageView backupImageView2 = this.avatarImageView;
            if (backupImageView2 != null) {
                backupImageView2.setImage((ImageLocation) null, (String) null, this.avatarDrawable, user);
                return;
            }
            return;
        }
        if (UserObject.isUserSelf(user) && !z) {
            this.avatarDrawable.setAvatarType(1);
            this.avatarDrawable.setScaleSize(0.8f);
            BackupImageView backupImageView3 = this.avatarImageView;
            if (backupImageView3 != null) {
                backupImageView3.setImage((ImageLocation) null, (String) null, this.avatarDrawable, user);
                return;
            }
            return;
        }
        this.avatarDrawable.setScaleSize(1.0f);
        BackupImageView backupImageView4 = this.avatarImageView;
        if (backupImageView4 != null) {
            backupImageView4.setForUserOrChat(user, this.avatarDrawable);
        }
    }

    public void checkAndUpdateAvatar() {
        TLRPC.User user;
        ChatActivity chatActivity = this.parentFragment;
        if (chatActivity == null) {
            return;
        }
        TLRPC.User currentUser = chatActivity.getCurrentUser();
        TLRPC.Chat currentChat = this.parentFragment.getCurrentChat();
        if (this.parentFragment.getChatMode() == 3) {
            long savedDialogId = this.parentFragment.getSavedDialogId();
            ChatActivity chatActivity2 = this.parentFragment;
            if (savedDialogId >= 0) {
                user = chatActivity2.getMessagesController().getUser(Long.valueOf(savedDialogId));
                currentChat = null;
            } else {
                currentChat = chatActivity2.getMessagesController().getChat(Long.valueOf(-savedDialogId));
                user = null;
            }
        } else {
            user = currentUser;
        }
        if (user != null) {
            this.avatarDrawable.setInfo(this.currentAccount, user);
            if (UserObject.isReplyUser(user)) {
                this.avatarDrawable.setScaleSize(0.8f);
                this.avatarDrawable.setAvatarType(12);
                BackupImageView backupImageView = this.avatarImageView;
                if (backupImageView != null) {
                    backupImageView.setAnimatedEmojiDrawable(null);
                    this.avatarImageView.setImage((ImageLocation) null, (String) null, this.avatarDrawable, user);
                    return;
                }
                return;
            }
            if (UserObject.isAnonymous(user)) {
                this.avatarDrawable.setScaleSize(0.8f);
                this.avatarDrawable.setAvatarType(21);
                BackupImageView backupImageView2 = this.avatarImageView;
                if (backupImageView2 != null) {
                    backupImageView2.setAnimatedEmojiDrawable(null);
                    this.avatarImageView.setImage((ImageLocation) null, (String) null, this.avatarDrawable, user);
                    return;
                }
                return;
            }
            if (UserObject.isUserSelf(user) && this.parentFragment.getChatMode() == 3) {
                this.avatarDrawable.setScaleSize(0.8f);
                this.avatarDrawable.setAvatarType(22);
                BackupImageView backupImageView3 = this.avatarImageView;
                if (backupImageView3 != null) {
                    backupImageView3.setAnimatedEmojiDrawable(null);
                    this.avatarImageView.setImage((ImageLocation) null, (String) null, this.avatarDrawable, user);
                    return;
                }
                return;
            }
            boolean zIsUserSelf = UserObject.isUserSelf(user);
            AvatarDrawable avatarDrawable = this.avatarDrawable;
            if (zIsUserSelf) {
                avatarDrawable.setScaleSize(0.8f);
                this.avatarDrawable.setAvatarType(1);
                BackupImageView backupImageView4 = this.avatarImageView;
                if (backupImageView4 != null) {
                    backupImageView4.setAnimatedEmojiDrawable(null);
                    this.avatarImageView.setImage((ImageLocation) null, (String) null, this.avatarDrawable, user);
                    return;
                }
                return;
            }
            avatarDrawable.setScaleSize(1.0f);
            BackupImageView backupImageView5 = this.avatarImageView;
            if (backupImageView5 != null) {
                backupImageView5.setAnimatedEmojiDrawable(null);
                this.avatarImageView.imageReceiver.setForUserOrChat(user, this.avatarDrawable, null, true, 3, false);
                return;
            }
            return;
        }
        if (!ChatObject.isMonoForum(currentChat)) {
            if (currentChat != null) {
                this.avatarDrawable.setScaleSize(1.0f);
                this.avatarDrawable.setInfo(this.currentAccount, currentChat);
                BackupImageView backupImageView6 = this.avatarImageView;
                if (backupImageView6 != null) {
                    backupImageView6.setAnimatedEmojiDrawable(null);
                    this.avatarImageView.setForUserOrChat(currentChat, this.avatarDrawable);
                    this.avatarImageView.setRoundRadius(ExteraConfig.getAvatarCorners(this.avatarSizeInDp, false, currentChat.forum, ChatObject.hasStories(currentChat)));
                    return;
                }
                return;
            }
            return;
        }
        long topicId = this.parentFragment.getTopicId();
        if (ChatObject.canManageMonoForum(this.currentAccount, currentChat) && topicId != 0) {
            ChatActivity chatActivity3 = this.parentFragment;
            if (topicId > 0) {
                TLRPC.User user2 = chatActivity3.getMessagesController().getUser(Long.valueOf(topicId));
                this.avatarDrawable.setInfo(user2);
                this.avatarImageView.setAnimatedEmojiDrawable(null);
                this.avatarImageView.setForUserOrChat(user2, this.avatarDrawable);
            } else {
                TLRPC.Chat chat = chatActivity3.getMessagesController().getChat(Long.valueOf(-topicId));
                this.avatarDrawable.setInfo(chat);
                this.avatarImageView.setAnimatedEmojiDrawable(null);
                this.avatarImageView.setForUserOrChat(chat, this.avatarDrawable);
            }
        } else {
            this.avatarImageView.setAnimatedEmojiDrawable(null);
            ForumUtilities.setMonoForumAvatar(this.currentAccount, currentChat, this.avatarDrawable, this.avatarImageView);
        }
        this.avatarImageView.setRoundRadius(ExteraConfig.getAvatarCorners(this.avatarSizeInDp));
    }

    public void updateOnlineCount() {
        TLRPC.UserStatus userStatus;
        boolean z;
        ChatActivity chatActivity = this.parentFragment;
        if (chatActivity == null) {
            return;
        }
        this.onlineCount = 0;
        TLRPC.ChatFull currentChatInfo = chatActivity.getCurrentChatInfo();
        if (currentChatInfo == null) {
            return;
        }
        int currentTime = ConnectionsManager.getInstance(this.currentAccount).getCurrentTime();
        if ((currentChatInfo instanceof TLRPC.TL_chatFull) || (((z = currentChatInfo instanceof TLRPC.TL_channelFull)) && currentChatInfo.participants_count <= 200 && currentChatInfo.participants != null)) {
            for (int i = 0; i < currentChatInfo.participants.participants.size(); i++) {
                TLRPC.User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(currentChatInfo.participants.participants.get(i).user_id));
                if (user != null && (userStatus = user.status) != null && ((userStatus.expires > currentTime || user.f1407id == UserConfig.getInstance(this.currentAccount).getClientUserId()) && user.status.expires > 10000)) {
                    this.onlineCount++;
                }
            }
            return;
        }
        if (!z || currentChatInfo.participants_count <= 200) {
            return;
        }
        this.onlineCount = currentChatInfo.online_count;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.parentFragment != null) {
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.didUpdateConnectionState);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
            if (this.parentFragment.getChatMode() == 3) {
                NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.savedMessagesDialogsUpdate);
            }
            this.currentConnectionState = ConnectionsManager.getInstance(this.currentAccount).getConnectionState();
            updateCurrentConnectionState();
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.emojiStatusDrawable;
        if (swapAnimatedEmojiDrawable != null) {
            swapAnimatedEmojiDrawable.attach();
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable2 = this.emojiStatusDrawable2;
        if (swapAnimatedEmojiDrawable2 != null) {
            swapAnimatedEmojiDrawable2.attach();
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable3 = this.botVerificationDrawable;
        if (swapAnimatedEmojiDrawable3 != null) {
            swapAnimatedEmojiDrawable3.attach();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.parentFragment != null) {
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.didUpdateConnectionState);
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
            if (this.parentFragment.getChatMode() == 3) {
                NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.savedMessagesDialogsUpdate);
            }
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.emojiStatusDrawable;
        if (swapAnimatedEmojiDrawable != null) {
            swapAnimatedEmojiDrawable.detach();
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable2 = this.emojiStatusDrawable2;
        if (swapAnimatedEmojiDrawable2 != null) {
            swapAnimatedEmojiDrawable2.detach();
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable3 = this.botVerificationDrawable;
        if (swapAnimatedEmojiDrawable3 != null) {
            swapAnimatedEmojiDrawable3.detach();
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.didUpdateConnectionState) {
            int connectionState = ConnectionsManager.getInstance(this.currentAccount).getConnectionState();
            if (this.currentConnectionState != connectionState) {
                this.currentConnectionState = connectionState;
                updateCurrentConnectionState();
                return;
            }
            return;
        }
        if (i == NotificationCenter.emojiLoaded) {
            SimpleTextView simpleTextView = this.titleTextView;
            if (simpleTextView != null) {
                simpleTextView.invalidate();
            }
            if (getSubtitleTextView() != null) {
                getSubtitleTextView().invalidate();
            }
            invalidate();
            return;
        }
        if (i == NotificationCenter.savedMessagesDialogsUpdate) {
            updateSubtitle(true);
        }
    }

    private void updateCurrentConnectionState() {
        String string;
        int i = this.currentConnectionState;
        if (i == 2) {
            string = LocaleController.getString(C2797R.string.WaitingForNetwork);
        } else if (i == 1) {
            string = LocaleController.getString(C2797R.string.Connecting);
        } else if (i == 5) {
            string = LocaleController.getString(C2797R.string.Updating);
        } else {
            string = i == 4 ? LocaleController.getString(C2797R.string.ConnectingToProxy) : null;
        }
        if (string == null) {
            CharSequence charSequence = this.lastSubtitle;
            if (charSequence != null) {
                SimpleTextView simpleTextView = this.subtitleTextView;
                if (simpleTextView != null) {
                    simpleTextView.setText(charSequence);
                    this.lastSubtitle = null;
                    Integer num = this.overrideSubtitleColor;
                    if (num != null) {
                        this.subtitleTextView.setTextColor(num.intValue());
                    } else {
                        int i2 = this.lastSubtitleColorKey;
                        if (i2 >= 0) {
                            this.subtitleTextView.setTextColor(getThemedColor(i2));
                            this.subtitleTextView.setTag(Integer.valueOf(this.lastSubtitleColorKey));
                        }
                    }
                } else {
                    AnimatedTextView animatedTextView = this.animatedSubtitleTextView;
                    if (animatedTextView != null) {
                        animatedTextView.setText(charSequence, !LocaleController.isRTL);
                        this.lastSubtitle = null;
                        Integer num2 = this.overrideSubtitleColor;
                        if (num2 != null) {
                            this.animatedSubtitleTextView.setTextColor(num2.intValue());
                        } else {
                            int i3 = this.lastSubtitleColorKey;
                            if (i3 >= 0) {
                                this.animatedSubtitleTextView.setTextColor(getThemedColor(i3));
                                this.animatedSubtitleTextView.setTag(Integer.valueOf(this.lastSubtitleColorKey));
                            }
                        }
                    }
                }
            }
        } else {
            SimpleTextView simpleTextView2 = this.subtitleTextView;
            if (simpleTextView2 != null) {
                if (this.lastSubtitle == null) {
                    this.lastSubtitle = simpleTextView2.getText();
                }
                this.subtitleTextView.setText(string);
                Integer num3 = this.overrideSubtitleColor;
                SimpleTextView simpleTextView3 = this.subtitleTextView;
                if (num3 != null) {
                    simpleTextView3.setTextColor(num3.intValue());
                } else {
                    int i4 = Theme.key_actionBarDefaultSubtitle;
                    simpleTextView3.setTextColor(getThemedColor(i4));
                    this.subtitleTextView.setTag(Integer.valueOf(i4));
                }
            } else {
                AnimatedTextView animatedTextView2 = this.animatedSubtitleTextView;
                if (animatedTextView2 != null) {
                    if (this.lastSubtitle == null) {
                        this.lastSubtitle = animatedTextView2.getText();
                    }
                    this.animatedSubtitleTextView.setText(string, !LocaleController.isRTL);
                    Integer num4 = this.overrideSubtitleColor;
                    AnimatedTextView animatedTextView3 = this.animatedSubtitleTextView;
                    if (num4 != null) {
                        animatedTextView3.setTextColor(num4.intValue());
                    } else {
                        int i5 = Theme.key_actionBarDefaultSubtitle;
                        animatedTextView3.setTextColor(getThemedColor(i5));
                        this.animatedSubtitleTextView.setTag(Integer.valueOf(i5));
                    }
                }
            }
        }
        checkActionBar(true);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        StringBuilder sb = new StringBuilder();
        sb.append(this.titleTextView.getText());
        if (this.rightDrawableContentDescription != null) {
            sb.append(", ");
            sb.append(this.rightDrawableContentDescription);
        }
        if (this.rightDrawable2ContentDescription != null) {
            sb.append(", ");
            sb.append(this.rightDrawable2ContentDescription);
        }
        sb.append("\n");
        SimpleTextView simpleTextView = this.subtitleTextView;
        if (simpleTextView != null) {
            sb.append(simpleTextView.getText());
        } else {
            AnimatedTextView animatedTextView = this.animatedSubtitleTextView;
            if (animatedTextView != null) {
                sb.append(animatedTextView.getText());
            }
        }
        accessibilityNodeInfo.setContentDescription(sb);
        if (accessibilityNodeInfo.isClickable()) {
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, LocaleController.getString(C2797R.string.OpenProfile)));
        }
        if (accessibilityNodeInfo.isLongClickable()) {
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(32, LocaleController.getString(C2797R.string.Search)));
        }
    }

    public SharedMediaLayout.SharedMediaPreloader getSharedMediaPreloader() {
        return this.sharedMediaPreloader;
    }

    public BackupImageView getAvatarImageView() {
        return this.avatarImageView;
    }

    private int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    public void updateColors() {
        StatusDrawable statusDrawable = this.currentTypingDrawable;
        if (statusDrawable != null) {
            statusDrawable.setColor(getThemedColor(Theme.key_chat_status));
        }
        Drawable drawable = this.emojiStatusDefaultDrawable;
        if (drawable != null) {
            drawable.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_profile_verifiedBackground), PorterDuff.Mode.MULTIPLY));
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.botVerificationDrawable;
        if (swapAnimatedEmojiDrawable != null) {
            swapAnimatedEmojiDrawable.setColor(Integer.valueOf(getThemedColor(Theme.key_profile_verifiedBackground)));
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable2 = this.emojiStatusDrawable;
        if (swapAnimatedEmojiDrawable2 != null) {
            swapAnimatedEmojiDrawable2.setColor(Integer.valueOf(getThemedColor(Theme.key_profile_verifiedBackground)));
        }
        Drawable drawable2 = this.verifiedBackground;
        if (drawable2 != null) {
            drawable2.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_profile_verifiedBackground), PorterDuff.Mode.MULTIPLY));
        }
        Drawable drawable3 = this.verifiedCheck;
        if (drawable3 != null) {
            drawable3.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_profile_verifiedCheck), PorterDuff.Mode.MULTIPLY));
        }
        invalidate();
    }

    public void setActionBar(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    private void checkActionBar(boolean z) {
        ActionBar actionBar = this.actionBar;
        if (actionBar != null) {
            actionBar.checkAvatarContainerWidth(z);
        }
    }

    public boolean hasVisibleAvatar() {
        BackupImageView backupImageView = this.avatarImageView;
        return backupImageView != null && backupImageView.getVisibility() == 0;
    }

    public int getVisualWidth() {
        int iM1036dp;
        SimpleTextView simpleTextView = this.titleTextView;
        float fMax = simpleTextView != null ? Math.max(0.0f, simpleTextView.getExactWidthIncludeDrawables()) : 0.0f;
        SimpleTextView simpleTextView2 = this.subtitleTextView;
        if (simpleTextView2 != null) {
            fMax = Math.max(fMax, simpleTextView2.getExactWidthIncludeDrawables());
        }
        if (hasVisibleAvatar()) {
            iM1036dp = AndroidUtilities.m1036dp(this.avatarSizeInDp + 22);
        } else {
            iM1036dp = AndroidUtilities.m1036dp(30.0f);
        }
        return (int) (fMax + iM1036dp);
    }
}
