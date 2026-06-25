package com.exteragram.messenger.utils.p020ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.exteragram.messenger.ExteraConfig;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p035ui.ActionBar.ActionBar;
import org.telegram.p035ui.Components.BackupImageView;
import org.telegram.p035ui.Components.ChatActivityTopPanelLayout;
import org.telegram.p035ui.Components.ChatAvatarContainer;
import org.telegram.p035ui.Components.chat.ViewPositionWatcher;
import org.telegram.p035ui.Components.chat.layouts.ChatActivityFadeView;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ChatHeaderUiHelper {
    public static boolean isMaterial3ChatHeaderStyle() {
        return ExteraConfig.getNewChatHeaderStyle();
    }

    public static void setupGlassAvatarContainer(ChatAvatarContainer chatAvatarContainer) {
        chatAvatarContainer.setGlassMode();
        if (isMaterial3ChatHeaderStyle()) {
            chatAvatarContainer.setAvatarSizeInDp(46);
        }
    }

    public static void applyChatHeaderGlassStyle(ActionBar actionBar) {
        if (isMaterial3ChatHeaderStyle()) {
            actionBar.setDrawGlassMiddlePill(false);
            actionBar.setGlassShadowAlpha(0.0f);
        }
    }

    public static void setupChatTopFade(ChatActivityFadeView chatActivityFadeView, ActionBar actionBar, int i, int i2) {
        chatActivityFadeView.setFadeTopAlpha(actionBar.getVisibility() == 0 ? 255 : 0);
        chatActivityFadeView.setFadeZoneTop(getChatTopFadeZone(i2));
        chatActivityFadeView.setFadeHeightTop(getChatTopFadeHeight());
        if (isMaterial3ChatHeaderStyle()) {
            chatActivityFadeView.setTopFadeColor(i);
        }
    }

    public static boolean isLightChatStatusBar(ActionBar actionBar, int i) {
        if (!isMaterial3ChatHeaderStyle() || actionBar.isActionModeShowed()) {
            i = actionBar.getBackgroundColor();
        }
        return AndroidUtilities.computePerceivedBrightness(i) > 0.721f;
    }

    public static int getAvatarContainerLeftMargin(boolean z) {
        if (z) {
            return 4;
        }
        return isMaterial3ChatHeaderStyle() ? 57 : 53;
    }

    public static float getTopPanelActionBarGapOffset(ChatActivityTopPanelLayout chatActivityTopPanelLayout) {
        if (!isMaterial3ChatHeaderStyle() || chatActivityTopPanelLayout == null) {
            return 0.0f;
        }
        return AndroidUtilities.m1036dp(4.0f) * chatActivityTopPanelLayout.getMetadata().getTotalVisibility();
    }

    public static float getFinalTopPanelHeight(float f, ChatActivityTopPanelLayout chatActivityTopPanelLayout) {
        return f + getTopPanelActionBarGapOffset(chatActivityTopPanelLayout);
    }

    public static float getTopPanelTranslationY(float f, float f2, float f3) {
        return (f + AndroidUtilities.m1036dp(isMaterial3ChatHeaderStyle() ? -1 : -5)) - (f2 * f3);
    }

    public static int getChatTopFadeHeight() {
        return AndroidUtilities.m1036dp(isMaterial3ChatHeaderStyle() ? 78.0f : 48.0f);
    }

    public static int getChatTopFadeZone(int i) {
        return isMaterial3ChatHeaderStyle() ? i + AndroidUtilities.m1036dp(42.0f) : i;
    }

    public static final class ProfileTransitionState {
        private float avatarTranslation;
        private float avatarStartY = Float.NaN;
        private float avatarSizeDp = 42.0f;
        private float nameTranslationX = Float.NaN;
        private float nameTranslationY = Float.NaN;
        private float onlineTranslationX = Float.NaN;
        private float onlineTranslationY = Float.NaN;

        public void reset() {
            this.avatarTranslation = 0.0f;
            this.avatarStartY = Float.NaN;
            this.avatarSizeDp = 42.0f;
            this.nameTranslationX = Float.NaN;
            this.nameTranslationY = Float.NaN;
            this.onlineTranslationX = Float.NaN;
            this.onlineTranslationY = Float.NaN;
        }

        public void capture(ChatAvatarContainer chatAvatarContainer, ViewGroup viewGroup, View view, View view2) {
            BackupImageView avatarImageView;
            if (chatAvatarContainer == null || viewGroup == null || (avatarImageView = chatAvatarContainer.getAvatarImageView()) == null) {
                return;
            }
            this.avatarTranslation = ViewPositionWatcher.computeXCoordinateInParent(avatarImageView, viewGroup);
            this.avatarStartY = ViewPositionWatcher.computeYCoordinateInParent(avatarImageView, viewGroup);
            int measuredWidth = avatarImageView.getMeasuredWidth() != 0 ? avatarImageView.getMeasuredWidth() : avatarImageView.getWidth();
            if (measuredWidth > 0) {
                this.avatarSizeDp = measuredWidth / AndroidUtilities.density;
            }
            if (chatAvatarContainer.getTitleTextView() != null && view != null) {
                this.nameTranslationX = ViewPositionWatcher.computeXCoordinateInParent(chatAvatarContainer.getTitleTextView(), viewGroup) - getTransitionLayoutLeft(view);
                this.nameTranslationY = ViewPositionWatcher.computeYCoordinateInParent(chatAvatarContainer.getTitleTextView(), viewGroup) - getTransitionLayoutTop(view);
            }
            View subtitleTextView = chatAvatarContainer.getSubtitleTextView();
            if (subtitleTextView == null || view2 == null) {
                return;
            }
            this.onlineTranslationX = ViewPositionWatcher.computeXCoordinateInParent(subtitleTextView, viewGroup) - getTransitionLayoutLeft(view2);
            this.onlineTranslationY = ViewPositionWatcher.computeYCoordinateInParent(subtitleTextView, viewGroup) - getTransitionLayoutTop(view2);
        }

        public float getAvatarTranslation() {
            return this.avatarTranslation;
        }

        public float getAvatarStartY(ActionBar actionBar) {
            if (!Float.isNaN(this.avatarStartY)) {
                return this.avatarStartY;
            }
            return (((actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0) + (ActionBar.getCurrentActionBarHeight() / 2.0f)) - AndroidUtilities.m1036dp(this.avatarSizeDp / 2.0f)) + actionBar.getTranslationY();
        }

        public float getAvatarStartScale() {
            return this.avatarSizeDp / 100.0f;
        }

        public float getAvatarSizeDp() {
            return this.avatarSizeDp;
        }

        public float getNameTranslationX() {
            if (!Float.isNaN(this.nameTranslationX)) {
                return this.nameTranslationX;
            }
            return (this.avatarTranslation - AndroidUtilities.m1036dp(109.0f)) + AndroidUtilities.m1036dp(this.avatarSizeDp + 6.0f);
        }

        public float getNameTranslationY(ActionBar actionBar) {
            if (!Float.isNaN(this.nameTranslationY)) {
                return this.nameTranslationY;
            }
            return ((float) Math.floor(getAvatarStartY(actionBar))) + AndroidUtilities.m1036dp(ChatHeaderUiHelper.isMaterial3ChatHeaderStyle() ? 1.66f : 1.3f);
        }

        public float getOnlineTranslationX() {
            if (!Float.isNaN(this.onlineTranslationX)) {
                return this.onlineTranslationX;
            }
            return getNameTranslationX();
        }

        public float getOnlineTranslationY(ActionBar actionBar) {
            if (!Float.isNaN(this.onlineTranslationY)) {
                return this.onlineTranslationY;
            }
            return ((float) Math.floor(getAvatarStartY(actionBar))) + AndroidUtilities.m1036dp(ChatHeaderUiHelper.isMaterial3ChatHeaderStyle() ? 26.66f : 24.0f);
        }

        public void updateBadgePositionsFromCollapsedAvatar(View view, float f, ImageView imageView, ImageView imageView2, ImageView imageView3) {
            updateBadgePositions(view, (AndroidUtilities.m1036dp(this.avatarSizeDp) * ((f * 100.0f) / this.avatarSizeDp)) - AndroidUtilities.m1036dp(r1), imageView, imageView2, imageView3);
        }

        public void updateBadgePositionsFromExpandedAvatar(View view, float f, ImageView imageView, ImageView imageView2, ImageView imageView3) {
            updateBadgePositions(view, (view.getMeasuredWidth() - AndroidUtilities.m1036dp(this.avatarSizeDp)) * ((f * 100.0f) / this.avatarSizeDp), imageView, imageView2, imageView3);
        }

        private void updateBadgePositions(View view, float f, ImageView imageView, ImageView imageView2, ImageView imageView3) {
            if (imageView != null) {
                imageView.setTranslationX(view.getX() + AndroidUtilities.m1036dp(this.avatarSizeDp - 26.0f) + f);
                imageView.setTranslationY(view.getY() + AndroidUtilities.m1036dp(this.avatarSizeDp - 27.0f) + f);
            }
            if (imageView2 != null) {
                imageView2.setTranslationX(view.getX() + AndroidUtilities.m1036dp(this.avatarSizeDp - 14.0f) + f);
                imageView2.setTranslationY(view.getY() + AndroidUtilities.m1036dp(this.avatarSizeDp - 18.0f) + f);
            }
            if (imageView3 != null) {
                imageView3.setTranslationX(view.getX() + AndroidUtilities.m1036dp(this.avatarSizeDp - 14.0f) + f);
                imageView3.setTranslationY(view.getY() + AndroidUtilities.m1036dp(this.avatarSizeDp - 18.0f) + f);
            }
        }

        private static int getTransitionLayoutLeft(View view) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                return ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
            }
            return view.getLeft();
        }

        private static int getTransitionLayoutTop(View view) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                return ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
            }
            return view.getTop();
        }
    }
}
