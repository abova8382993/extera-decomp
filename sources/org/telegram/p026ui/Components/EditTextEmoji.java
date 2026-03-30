package org.telegram.p026ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.exteragram.messenger.utils.text.LocaleUtils;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2702R;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.XiaomiUtilities;
import org.telegram.p026ui.ActionBar.AdjustPanLayoutHelper;
import org.telegram.p026ui.ActionBar.AlertDialog;
import org.telegram.p026ui.ActionBar.BaseFragment;
import org.telegram.p026ui.ActionBar.Theme;
import org.telegram.p026ui.ChatActivity;
import org.telegram.p026ui.Components.EmojiView;
import org.telegram.p026ui.Components.Premium.PremiumFeatureBottomSheet;
import org.telegram.p026ui.Components.SizeNotifierFrameLayout;
import org.telegram.tgnet.TLRPC;
import p019j$.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class EditTextEmoji extends FrameLayout implements NotificationCenter.NotificationCenterDelegate, SizeNotifierFrameLayout.SizeNotifierFrameLayoutDelegate {
    AdjustPanLayoutHelper adjustPanLayoutHelper;
    private boolean allowAnimatedEmoji;
    private boolean allowEmojisForNonPremium;
    private int currentStyle;
    private boolean destroyed;
    private EditTextCaption editText;
    private OutlineTextContainerView editTextFieldContainer;
    private ImageView emojiButton;
    public boolean emojiExpanded;
    private ReplaceableIconDrawable emojiIconDrawable;
    private int emojiPadding;
    private EmojiView emojiView;
    private float emojiViewAlpha;
    private int emojiViewCacheType;
    private boolean emojiViewVisible;
    private ItemOptions formatOptions;
    public boolean glassDesignForEmojiView;
    public boolean includeNavigationBar;
    private int innerTextChange;
    private boolean isAnimatePopupClosing;
    private boolean isPaused;
    private int keyboardHeight;
    private int keyboardHeightLand;
    private boolean keyboardVisible;
    private boolean lastEmojiExpanded;
    private int lastSizeChangeValue1;
    private boolean lastSizeChangeValue2;
    private Runnable openKeyboardRunnable;
    private BaseFragment parentFragment;
    private final Theme.ResourcesProvider resourcesProvider;
    private boolean showKeyboardOnResume;
    private boolean shownFormatButton;
    private SizeNotifierFrameLayout sizeNotifierLayout;
    private boolean waitingForKeyboardOpen;

    /* JADX INFO: loaded from: classes5.dex */
    public interface EditTextEmojiDelegate {
    }

    protected boolean allowSearch() {
        return false;
    }

    protected boolean allowSearchAnimation() {
        return false;
    }

    protected void bottomPanelTranslationY(float f) {
    }

    protected void closeParent() {
    }

    protected boolean customEmojiButtonDraw(Canvas canvas, View view, Drawable drawable) {
        return false;
    }

    protected void drawEmojiBackground(Canvas canvas, View view) {
    }

    protected void extendActionMode(ActionMode actionMode, Menu menu) {
    }

    protected void onEmojiKeyboardUpdate() {
    }

    protected void onLineCountChanged(int i, int i2) {
    }

    protected boolean onScrollYChange(int i) {
        return true;
    }

    protected void onWaitingForKeyboard() {
    }

    public void setDelegate(EditTextEmojiDelegate editTextEmojiDelegate) {
    }

    protected void updatedEmojiExpanded() {
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextEmoji$1 */
    class RunnableC40991 implements Runnable {
        RunnableC40991() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (EditTextEmoji.this.destroyed || EditTextEmoji.this.editText == null || !EditTextEmoji.this.waitingForKeyboardOpen || EditTextEmoji.this.keyboardVisible || AndroidUtilities.usingHardwareInput || AndroidUtilities.isInMultiwindow || !AndroidUtilities.isTablet()) {
                return;
            }
            EditTextEmoji.this.editText.requestFocus();
            AndroidUtilities.showKeyboard(EditTextEmoji.this.editText);
            AndroidUtilities.cancelRunOnUIThread(EditTextEmoji.this.openKeyboardRunnable);
            AndroidUtilities.runOnUIThread(EditTextEmoji.this.openKeyboardRunnable, 100L);
        }
    }

    public boolean isPopupVisible() {
        EmojiView emojiView = this.emojiView;
        return emojiView != null && emojiView.getVisibility() == 0;
    }

    public boolean isWaitingForKeyboardOpen() {
        return this.waitingForKeyboardOpen;
    }

    public boolean isAnimatePopupClosing() {
        return this.isAnimatePopupClosing;
    }

    public void setAdjustPanLayoutHelper(AdjustPanLayoutHelper adjustPanLayoutHelper) {
        this.adjustPanLayoutHelper = adjustPanLayoutHelper;
    }

    public int emojiCacheType() {
        return AnimatedEmojiDrawable.getCacheTypeForEnterView();
    }

    public EditTextEmoji(Context context, SizeNotifierFrameLayout sizeNotifierFrameLayout, BaseFragment baseFragment, int i, boolean z) {
        this(context, sizeNotifierFrameLayout, baseFragment, i, z, null);
    }

    public EditTextEmoji(Context context, final SizeNotifierFrameLayout sizeNotifierFrameLayout, BaseFragment baseFragment, int i, boolean z, final Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.isPaused = true;
        this.openKeyboardRunnable = new Runnable() { // from class: org.telegram.ui.Components.EditTextEmoji.1
            RunnableC40991() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (EditTextEmoji.this.destroyed || EditTextEmoji.this.editText == null || !EditTextEmoji.this.waitingForKeyboardOpen || EditTextEmoji.this.keyboardVisible || AndroidUtilities.usingHardwareInput || AndroidUtilities.isInMultiwindow || !AndroidUtilities.isTablet()) {
                    return;
                }
                EditTextEmoji.this.editText.requestFocus();
                AndroidUtilities.showKeyboard(EditTextEmoji.this.editText);
                AndroidUtilities.cancelRunOnUIThread(EditTextEmoji.this.openKeyboardRunnable);
                AndroidUtilities.runOnUIThread(EditTextEmoji.this.openKeyboardRunnable, 100L);
            }
        };
        this.emojiViewCacheType = 2;
        this.allowAnimatedEmoji = z;
        this.resourcesProvider = resourcesProvider;
        this.currentStyle = i;
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
        this.parentFragment = baseFragment;
        this.sizeNotifierLayout = sizeNotifierFrameLayout;
        sizeNotifierFrameLayout.addDelegate(this);
        int iM1081dp = AndroidUtilities.m1081dp(16.0f);
        if (i == 0) {
            OutlineTextContainerView outlineTextContainerView = new OutlineTextContainerView(context);
            this.editTextFieldContainer = outlineTextContainerView;
            addView(outlineTextContainerView, LayoutHelper.createLinear(-1, -2, 1, 0, 0, 0, 0));
        }
        C41002 c41002 = new EditTextCaption(context, resourcesProvider) { // from class: org.telegram.ui.Components.EditTextEmoji.2
            private Drawable lastIcon = null;
            final /* synthetic */ int val$style;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C41002(Context context2, final Theme.ResourcesProvider resourcesProvider2, int i2) {
                super(context2, resourcesProvider2);
                i = i2;
                this.lastIcon = null;
            }

            @Override // org.telegram.p026ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                if (EditTextEmoji.this.isPopupShowing() && motionEvent.getAction() == 0) {
                    EditTextEmoji.this.onWaitingForKeyboard();
                    EditTextEmoji editTextEmoji = EditTextEmoji.this;
                    if (editTextEmoji.emojiExpanded && editTextEmoji.emojiView != null) {
                        EditTextEmoji.this.emojiView.closeSearch(false);
                        EditTextEmoji editTextEmoji2 = EditTextEmoji.this;
                        editTextEmoji2.emojiExpanded = false;
                        editTextEmoji2.hidePopup(true);
                        AndroidUtilities.showKeyboard(this);
                    } else {
                        EditTextEmoji.this.showPopup(AndroidUtilities.usingHardwareInput ? 0 : 2);
                    }
                    EditTextEmoji.this.openKeyboardInternal();
                }
                if (motionEvent.getAction() == 0) {
                    boolean zIsFocused = isFocused();
                    requestFocus();
                    if (!AndroidUtilities.showKeyboard(this)) {
                        clearFocus();
                        requestFocus();
                    }
                    if (!zIsFocused) {
                        setSelection(getText().length());
                    }
                }
                try {
                    return super.onTouchEvent(motionEvent);
                } catch (Exception e) {
                    FileLog.m1093e(e);
                    return false;
                }
            }

            @Override // org.telegram.p026ui.Components.EditTextCaption
            protected void onLineCountChanged(int i2, int i3) {
                EditTextEmoji.this.onLineCountChanged(i2, i3);
            }

            @Override // org.telegram.p026ui.Components.EditTextBoldCursor
            protected int getActionModeStyle() {
                int i2 = i;
                if (i2 == 2 || i2 == 3) {
                    return 2;
                }
                return super.getActionModeStyle();
            }

            @Override // org.telegram.p026ui.Components.EditTextBoldCursor
            protected void extendActionMode(ActionMode actionMode, Menu menu) {
                if (EditTextEmoji.this.allowEntities()) {
                    ChatActivity.fillActionModeMenu(menu, null, EditTextEmoji.this.currentStyle == 3, true);
                } else {
                    EditTextEmoji.this.extendActionMode(actionMode, menu);
                }
                super.extendActionMode(actionMode, menu);
            }

            @Override // android.view.View
            public void scrollTo(int i2, int i3) {
                if (EditTextEmoji.this.onScrollYChange(i3)) {
                    super.scrollTo(i2, i3);
                }
            }

            @Override // org.telegram.p026ui.Components.EditTextEffects, android.widget.TextView
            protected void onSelectionChanged(int i2, int i3) {
                super.onSelectionChanged(i2, i3);
                if (EditTextEmoji.this.emojiIconDrawable != null) {
                    boolean z2 = false;
                    boolean z3 = i3 != i2;
                    if (EditTextEmoji.this.allowEntities() && z3) {
                        XiaomiUtilities.isMIUI();
                        z2 = true;
                    }
                    if (EditTextEmoji.this.shownFormatButton != z2) {
                        EditTextEmoji.this.shownFormatButton = z2;
                        if (z2) {
                            this.lastIcon = EditTextEmoji.this.emojiIconDrawable.getIcon();
                            EditTextEmoji.this.emojiIconDrawable.setIcon(C2702R.drawable.msg_edit, true);
                        } else {
                            EditTextEmoji.this.emojiIconDrawable.setIcon(this.lastIcon, true);
                            this.lastIcon = null;
                        }
                    }
                }
            }

            @Override // org.telegram.p026ui.Components.EditTextEffects
            protected int emojiCacheType() {
                return EditTextEmoji.this.emojiCacheType();
            }
        };
        this.editText = c41002;
        c41002.setImeOptions(268435456);
        EditTextCaption editTextCaption = this.editText;
        editTextCaption.setInputType(editTextCaption.getInputType() | 16384);
        EditTextCaption editTextCaption2 = this.editText;
        editTextCaption2.setFocusable(editTextCaption2.isEnabled());
        this.editText.setCursorSize(AndroidUtilities.m1081dp(20.0f));
        this.editText.setTextSize(1, 18.0f);
        this.editText.setCursorWidth(1.5f);
        this.editText.setMaxLines(4);
        this.editText.setBackground(null);
        EditTextCaption editTextCaption3 = this.editText;
        int i2 = Theme.key_windowBackgroundWhiteBlackText;
        editTextCaption3.setCursorColor(getThemedColor(i2));
        if (i2 == 0) {
            this.editText.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
            this.editText.setHintTextColor(getThemedColor(Theme.key_windowBackgroundWhiteHintText));
            this.editText.setTextColor(getThemedColor(i2));
            this.editText.setHandlesColor(getThemedColor(Theme.key_chat_TextSelectionCursor));
            this.editText.setPadding(0, iM1081dp, 0, iM1081dp);
            this.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.telegram.ui.Components.EditTextEmoji$$ExternalSyntheticLambda0
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view, boolean z2) {
                    this.f$0.lambda$new$0(view, z2);
                }
            });
            this.editTextFieldContainer.addView(this.editText, LayoutHelper.createFrame(-1, -1.0f, 19, 16.0f, 0.0f, 53.0f, 0.0f));
            this.editTextFieldContainer.attachEditText(this.editText);
        } else if (i2 == 2 || i2 == 3) {
            this.editText.setTextSize(1, 16.0f);
            this.editText.setMaxLines(8);
            this.editText.setGravity(19);
            this.editText.setAllowTextEntitiesIntersection(true);
            this.editText.setHintTextColor(-1929379841);
            this.editText.setTextColor(-1);
            this.editText.setCursorColor(-1);
            this.editText.setClipToPadding(false);
            this.editText.setPadding(0, AndroidUtilities.m1081dp(9.0f), 0, AndroidUtilities.m1081dp(9.0f));
            this.editText.setHandlesColor(-1);
            this.editText.setHighlightColor(822083583);
            this.editText.setLinkTextColor(-12147733);
            EditTextCaption editTextCaption4 = this.editText;
            editTextCaption4.quoteColor = -1;
            editTextCaption4.setTextIsSelectable(true);
            setClipChildren(false);
            setClipToPadding(false);
            addView(this.editText, LayoutHelper.createFrame(-1, -1.0f, 19, 40.0f, 0.0f, 24.0f, 0.0f));
        } else if (i2 == 4) {
            this.editText.setTextSize(1, 18.0f);
            this.editText.setMaxLines(4);
            this.editText.setGravity(19);
            this.editText.setHintTextColor(getThemedColor(Theme.key_dialogTextHint));
            this.editText.setTextColor(getThemedColor(Theme.key_dialogTextBlack));
            this.editText.setBackground(null);
            this.editText.setPadding(0, AndroidUtilities.m1081dp(11.0f), 0, AndroidUtilities.m1081dp(12.0f));
            addView(this.editText, LayoutHelper.createFrame(-1, -1.0f, 19, 14.0f, 0.0f, 48.0f, 0.0f));
        } else {
            this.editText.setGravity(19);
            this.editText.setHintTextColor(getThemedColor(Theme.key_dialogTextHint));
            this.editText.setTextColor(getThemedColor(Theme.key_dialogTextBlack));
            this.editText.setPadding(0, AndroidUtilities.m1081dp(11.0f), 0, AndroidUtilities.m1081dp(12.0f));
            addView(this.editText, LayoutHelper.createFrame(-1, -1.0f, 19, 48.0f, 0.0f, 0.0f, 0.0f));
        }
        C41013 c41013 = new ImageView(context2) { // from class: org.telegram.ui.Components.EditTextEmoji.3
            C41013(Context context2) {
                super(context2);
            }

            @Override // android.view.View
            protected void dispatchDraw(Canvas canvas) {
                EditTextEmoji editTextEmoji = EditTextEmoji.this;
                if (editTextEmoji.customEmojiButtonDraw(canvas, editTextEmoji.emojiButton, EditTextEmoji.this.emojiIconDrawable)) {
                    return;
                }
                super.dispatchDraw(canvas);
            }
        };
        this.emojiButton = c41013;
        c41013.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ImageView imageView = this.emojiButton;
        ReplaceableIconDrawable replaceableIconDrawable = new ReplaceableIconDrawable(context2);
        this.emojiIconDrawable = replaceableIconDrawable;
        imageView.setImageDrawable(replaceableIconDrawable);
        if (i2 == 0) {
            this.emojiIconDrawable.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_chat_messagePanelIcons), PorterDuff.Mode.MULTIPLY));
            this.emojiIconDrawable.setIcon(C2702R.drawable.smiles_tab_smiles, false);
            this.editTextFieldContainer.addView(this.emojiButton, LayoutHelper.createFrame(48, 48.0f, (LocaleController.isRTL ? 3 : 5) | 16, 0.0f, 0.0f, 5.0f, 0.0f));
        } else if (i2 == 2 || i2 == 3) {
            this.emojiIconDrawable.setColorFilter(new PorterDuffColorFilter(-1929379841, PorterDuff.Mode.MULTIPLY));
            this.emojiIconDrawable.setIcon(C2702R.drawable.input_smile, false);
            addView(this.emojiButton, LayoutHelper.createFrame(40, 40.0f, 83, 0.0f, 0.0f, 0.0f, 0.0f));
        } else if (i2 == 4) {
            this.emojiIconDrawable.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_chat_messagePanelIcons), PorterDuff.Mode.MULTIPLY));
            this.emojiIconDrawable.setIcon(C2702R.drawable.input_smile, false);
            addView(this.emojiButton, LayoutHelper.createFrame(48, 48.0f, 53, 0.0f, 0.0f, 0.0f, 0.0f));
        } else if (i2 == 5) {
            this.emojiIconDrawable.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_graySectionText), PorterDuff.Mode.MULTIPLY));
            this.emojiIconDrawable.setIcon(C2702R.drawable.input_smile, false);
            addView(this.emojiButton, LayoutHelper.createFrame(48, 48.0f, 83, 0.0f, 0.0f, 0.0f, 0.0f));
        } else {
            this.emojiIconDrawable.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_chat_messagePanelIcons), PorterDuff.Mode.MULTIPLY));
            this.emojiIconDrawable.setIcon(C2702R.drawable.input_smile, false);
            addView(this.emojiButton, LayoutHelper.createFrame(48, 48.0f, 83, 0.0f, 0.0f, 0.0f, 0.0f));
        }
        this.emojiButton.setBackground(Theme.createSelectorDrawable(getThemedColor(Theme.key_listSelector)));
        this.emojiButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EditTextEmoji$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$1(sizeNotifierFrameLayout, resourcesProvider2, view);
            }
        });
        this.emojiButton.setContentDescription(LocaleController.getString(C2702R.string.Emoji));
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextEmoji$2 */
    class C41002 extends EditTextCaption {
        private Drawable lastIcon = null;
        final /* synthetic */ int val$style;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41002(Context context2, final Theme.ResourcesProvider resourcesProvider2, int i2) {
            super(context2, resourcesProvider2);
            i = i2;
            this.lastIcon = null;
        }

        @Override // org.telegram.p026ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (EditTextEmoji.this.isPopupShowing() && motionEvent.getAction() == 0) {
                EditTextEmoji.this.onWaitingForKeyboard();
                EditTextEmoji editTextEmoji = EditTextEmoji.this;
                if (editTextEmoji.emojiExpanded && editTextEmoji.emojiView != null) {
                    EditTextEmoji.this.emojiView.closeSearch(false);
                    EditTextEmoji editTextEmoji2 = EditTextEmoji.this;
                    editTextEmoji2.emojiExpanded = false;
                    editTextEmoji2.hidePopup(true);
                    AndroidUtilities.showKeyboard(this);
                } else {
                    EditTextEmoji.this.showPopup(AndroidUtilities.usingHardwareInput ? 0 : 2);
                }
                EditTextEmoji.this.openKeyboardInternal();
            }
            if (motionEvent.getAction() == 0) {
                boolean zIsFocused = isFocused();
                requestFocus();
                if (!AndroidUtilities.showKeyboard(this)) {
                    clearFocus();
                    requestFocus();
                }
                if (!zIsFocused) {
                    setSelection(getText().length());
                }
            }
            try {
                return super.onTouchEvent(motionEvent);
            } catch (Exception e) {
                FileLog.m1093e(e);
                return false;
            }
        }

        @Override // org.telegram.p026ui.Components.EditTextCaption
        protected void onLineCountChanged(int i2, int i3) {
            EditTextEmoji.this.onLineCountChanged(i2, i3);
        }

        @Override // org.telegram.p026ui.Components.EditTextBoldCursor
        protected int getActionModeStyle() {
            int i2 = i;
            if (i2 == 2 || i2 == 3) {
                return 2;
            }
            return super.getActionModeStyle();
        }

        @Override // org.telegram.p026ui.Components.EditTextBoldCursor
        protected void extendActionMode(ActionMode actionMode, Menu menu) {
            if (EditTextEmoji.this.allowEntities()) {
                ChatActivity.fillActionModeMenu(menu, null, EditTextEmoji.this.currentStyle == 3, true);
            } else {
                EditTextEmoji.this.extendActionMode(actionMode, menu);
            }
            super.extendActionMode(actionMode, menu);
        }

        @Override // android.view.View
        public void scrollTo(int i2, int i3) {
            if (EditTextEmoji.this.onScrollYChange(i3)) {
                super.scrollTo(i2, i3);
            }
        }

        @Override // org.telegram.p026ui.Components.EditTextEffects, android.widget.TextView
        protected void onSelectionChanged(int i2, int i3) {
            super.onSelectionChanged(i2, i3);
            if (EditTextEmoji.this.emojiIconDrawable != null) {
                boolean z2 = false;
                boolean z3 = i3 != i2;
                if (EditTextEmoji.this.allowEntities() && z3) {
                    XiaomiUtilities.isMIUI();
                    z2 = true;
                }
                if (EditTextEmoji.this.shownFormatButton != z2) {
                    EditTextEmoji.this.shownFormatButton = z2;
                    if (z2) {
                        this.lastIcon = EditTextEmoji.this.emojiIconDrawable.getIcon();
                        EditTextEmoji.this.emojiIconDrawable.setIcon(C2702R.drawable.msg_edit, true);
                    } else {
                        EditTextEmoji.this.emojiIconDrawable.setIcon(this.lastIcon, true);
                        this.lastIcon = null;
                    }
                }
            }
        }

        @Override // org.telegram.p026ui.Components.EditTextEffects
        protected int emojiCacheType() {
            return EditTextEmoji.this.emojiCacheType();
        }
    }

    public /* synthetic */ void lambda$new$0(View view, boolean z) {
        this.editTextFieldContainer.animateSelection(z ? 1.0f : 0.0f);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextEmoji$3 */
    class C41013 extends ImageView {
        C41013(Context context2) {
            super(context2);
        }

        @Override // android.view.View
        protected void dispatchDraw(Canvas canvas) {
            EditTextEmoji editTextEmoji = EditTextEmoji.this;
            if (editTextEmoji.customEmojiButtonDraw(canvas, editTextEmoji.emojiButton, EditTextEmoji.this.emojiIconDrawable)) {
                return;
            }
            super.dispatchDraw(canvas);
        }
    }

    public /* synthetic */ void lambda$new$1(SizeNotifierFrameLayout sizeNotifierFrameLayout, Theme.ResourcesProvider resourcesProvider, View view) {
        if (!this.emojiButton.isEnabled() || this.emojiButton.getAlpha() < 0.5f) {
            return;
        }
        AdjustPanLayoutHelper adjustPanLayoutHelper = this.adjustPanLayoutHelper;
        if (adjustPanLayoutHelper == null || !adjustPanLayoutHelper.animationInProgress()) {
            if (this.shownFormatButton) {
                ItemOptions itemOptions = this.formatOptions;
                if (itemOptions == null) {
                    this.editText.hideActionMode();
                    ItemOptions itemOptionsMakeOptions = ItemOptions.makeOptions(sizeNotifierFrameLayout, resourcesProvider, this.emojiButton);
                    itemOptionsMakeOptions.setMaxHeight(AndroidUtilities.m1081dp(280.0f));
                    final EditTextCaption editTextCaption = this.editText;
                    Objects.requireNonNull(editTextCaption);
                    editTextCaption.extendActionMode(null, new MenuToItemOptions(itemOptionsMakeOptions, new Utilities.Callback() { // from class: org.telegram.ui.Components.EditTextEmoji$$ExternalSyntheticLambda4
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            editTextCaption.performMenuAction(((Integer) obj).intValue());
                        }
                    }, this.editText.getOnPremiumMenuLockClickListener()));
                    itemOptionsMakeOptions.forceTop(true);
                    itemOptionsMakeOptions.show();
                    return;
                }
                itemOptions.dismiss();
                this.formatOptions = null;
                return;
            }
            if (!isPopupShowing()) {
                showPopup(1);
                boolean zIsFocused = this.editText.isFocused();
                this.emojiView.onOpen(this.editText.length() > 0, false);
                this.editText.requestFocus();
                if (zIsFocused) {
                    return;
                }
                EditTextCaption editTextCaption2 = this.editText;
                editTextCaption2.setSelection(editTextCaption2.length());
                return;
            }
            if (this.emojiExpanded) {
                hidePopup(true);
                this.emojiExpanded = false;
                onEmojiKeyboardUpdate();
            }
            openKeyboardInternal();
        }
    }

    public void collapseEmojiView() {
        EmojiView emojiView = this.emojiView;
        if (emojiView != null) {
            emojiView.hideSearchKeyboard();
            this.emojiView.closeSearch(false);
        }
    }

    protected boolean allowEntities() {
        int i = this.currentStyle;
        return i == 2 || i == 3 || i == 5;
    }

    public void setSuggestionsEnabled(boolean z) {
        int inputType = this.editText.getInputType();
        int i = !z ? 524288 | inputType : (-524289) & inputType;
        if (this.editText.getInputType() != i) {
            this.editText.setInputType(i);
        }
    }

    public void setSizeNotifierLayout(SizeNotifierFrameLayout sizeNotifierFrameLayout) {
        SizeNotifierFrameLayout sizeNotifierFrameLayout2 = this.sizeNotifierLayout;
        if (sizeNotifierFrameLayout2 != null) {
            sizeNotifierFrameLayout2.removeDelegate(this);
        }
        this.sizeNotifierLayout = sizeNotifierFrameLayout;
        sizeNotifierFrameLayout.addDelegate(this);
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.emojiLoaded) {
            EmojiView emojiView = this.emojiView;
            if (emojiView != null) {
                emojiView.invalidateViews();
            }
            EditTextCaption editTextCaption = this.editText;
            if (editTextCaption != null) {
                int currentTextColor = editTextCaption.getCurrentTextColor();
                this.editText.setTextColor(-1);
                this.editText.setTextColor(currentTextColor);
            }
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        this.editText.setEnabled(z);
        if (z) {
            return;
        }
        this.emojiButton.setAlpha(0.5f);
        this.emojiButton.setBackground(null);
        this.emojiButton.setOnClickListener(null);
    }

    @Override // android.view.View
    public void setFocusable(boolean z) {
        this.editText.setFocusable(z);
    }

    public void hideEmojiView() {
        EmojiView emojiView;
        if (!this.emojiViewVisible && (emojiView = this.emojiView) != null && emojiView.getVisibility() != 8) {
            this.emojiView.setVisibility(8);
            this.emojiViewAlpha = 0.0f;
        }
        this.emojiPadding = 0;
        boolean z = this.emojiExpanded;
        this.emojiExpanded = false;
        if (z) {
            EmojiView emojiView2 = this.emojiView;
            if (emojiView2 != null) {
                emojiView2.closeSearch(false);
            }
            updatedEmojiExpanded();
        }
    }

    public void allowEmojisForNonPremium(boolean z) {
        this.allowEmojisForNonPremium = z;
    }

    public EmojiView getEmojiView() {
        return this.emojiView;
    }

    public void onPause() {
        this.isPaused = true;
        closeKeyboard();
    }

    public void onResume() {
        this.isPaused = false;
        if (this.showKeyboardOnResume) {
            this.showKeyboardOnResume = false;
            this.editText.requestFocus();
            AndroidUtilities.showKeyboard(this.editText);
            if (AndroidUtilities.usingHardwareInput || this.keyboardVisible || AndroidUtilities.isInMultiwindow || AndroidUtilities.isTablet()) {
                return;
            }
            this.waitingForKeyboardOpen = true;
            onWaitingForKeyboard();
            AndroidUtilities.cancelRunOnUIThread(this.openKeyboardRunnable);
            AndroidUtilities.runOnUIThread(this.openKeyboardRunnable, 100L);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        onDestroy();
        super.onDetachedFromWindow();
    }

    public void onDestroy() {
        this.destroyed = true;
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        EmojiView emojiView = this.emojiView;
        if (emojiView != null) {
            emojiView.onDestroy();
        }
        SizeNotifierFrameLayout sizeNotifierFrameLayout = this.sizeNotifierLayout;
        if (sizeNotifierFrameLayout != null) {
            sizeNotifierFrameLayout.removeDelegate(this);
        }
    }

    public void updateColors() {
        int i = this.currentStyle;
        if (i == 0) {
            this.editText.setHintTextColor(getThemedColor(Theme.key_windowBackgroundWhiteHintText));
            EditTextCaption editTextCaption = this.editText;
            int i2 = Theme.key_windowBackgroundWhiteBlackText;
            editTextCaption.setCursorColor(getThemedColor(i2));
            this.editText.setTextColor(getThemedColor(i2));
        } else if (i == 2 || i == 3) {
            this.editText.setHintTextColor(-1929379841);
            this.editText.setTextColor(-1);
            this.editText.setCursorColor(-1);
            this.editText.setHandlesColor(-1);
            this.editText.setHighlightColor(822083583);
            this.editText.quoteColor = -1;
        } else {
            this.editText.setHintTextColor(getThemedColor(Theme.key_dialogTextHint));
            this.editText.setTextColor(getThemedColor(Theme.key_dialogTextBlack));
        }
        this.emojiIconDrawable.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_chat_messagePanelIcons), PorterDuff.Mode.MULTIPLY));
        EmojiView emojiView = this.emojiView;
        if (emojiView != null) {
            emojiView.updateColors();
        }
    }

    public void setMaxLines(int i) {
        this.editText.setMaxLines(i);
    }

    public int length() {
        return this.editText.length();
    }

    public void setFilters(InputFilter[] inputFilterArr) {
        this.editText.setFilters(inputFilterArr);
    }

    public Editable getText() {
        return this.editText.getText();
    }

    public void setHint(CharSequence charSequence) {
        if (this.currentStyle == 0) {
            this.editTextFieldContainer.setText((String) charSequence);
        } else {
            this.editText.setHint(charSequence);
        }
    }

    public void setText(CharSequence charSequence) {
        this.editText.setText(charSequence);
    }

    public void setSelection(int i) {
        this.editText.setSelection(i);
    }

    public void setSelection(int i, int i2) {
        this.editText.setSelection(i, i2);
    }

    public void hidePopup(boolean z) {
        if (isPopupShowing()) {
            showPopup(0);
        }
        if (z) {
            EmojiView emojiView = this.emojiView;
            if (emojiView != null && emojiView.getVisibility() == 0 && !this.waitingForKeyboardOpen) {
                final int measuredHeight = this.emojiView.getMeasuredHeight();
                if (this.emojiView.getParent() instanceof ViewGroup) {
                    measuredHeight += ((ViewGroup) this.emojiView.getParent()).getHeight() - this.emojiView.getBottom();
                }
                this.emojiViewAlpha = 1.0f;
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, measuredHeight);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.EditTextEmoji$$ExternalSyntheticLambda2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$hidePopup$2(measuredHeight, valueAnimator);
                    }
                });
                this.isAnimatePopupClosing = true;
                valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EditTextEmoji.4
                    C41024() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        EditTextEmoji.this.isAnimatePopupClosing = false;
                        EditTextEmoji.this.emojiView.setTranslationY(0.0f);
                        EditTextEmoji.this.emojiView.setAlpha(0.0f);
                        EditTextEmoji.this.bottomPanelTranslationY(0.0f);
                        EditTextEmoji.this.emojiViewAlpha = 0.0f;
                        EditTextEmoji.this.hideEmojiView();
                    }
                });
                valueAnimatorOfFloat.setDuration(250L);
                valueAnimatorOfFloat.setInterpolator(AdjustPanLayoutHelper.keyboardInterpolator);
                valueAnimatorOfFloat.start();
            } else {
                hideEmojiView();
            }
        }
        boolean z2 = this.emojiExpanded;
        this.emojiExpanded = false;
        if (z2) {
            EmojiView emojiView2 = this.emojiView;
            if (emojiView2 != null) {
                emojiView2.closeSearch(false);
            }
            updatedEmojiExpanded();
        }
    }

    public /* synthetic */ void lambda$hidePopup$2(int i, ValueAnimator valueAnimator) {
        int i2;
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.emojiView.setTranslationY(fFloatValue);
        float f = i;
        float f2 = 1.0f - (fFloatValue / f);
        this.emojiViewAlpha = f2;
        if (i > 0 && ((i2 = this.currentStyle) == 2 || i2 == 3)) {
            this.emojiView.setAlpha(f2);
        }
        bottomPanelTranslationY(fFloatValue - f);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextEmoji$4 */
    /* JADX INFO: loaded from: classes5.dex */
    class C41024 extends AnimatorListenerAdapter {
        C41024() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            EditTextEmoji.this.isAnimatePopupClosing = false;
            EditTextEmoji.this.emojiView.setTranslationY(0.0f);
            EditTextEmoji.this.emojiView.setAlpha(0.0f);
            EditTextEmoji.this.bottomPanelTranslationY(0.0f);
            EditTextEmoji.this.emojiViewAlpha = 0.0f;
            EditTextEmoji.this.hideEmojiView();
        }
    }

    public float getEmojiPaddingShown() {
        return this.emojiViewAlpha;
    }

    public void openKeyboard() {
        this.editText.requestFocus();
        AndroidUtilities.showKeyboard(this.editText);
    }

    public void closeKeyboard() {
        AndroidUtilities.hideKeyboard(this.editText);
    }

    public boolean isPopupShowing() {
        return this.emojiViewVisible;
    }

    public boolean isKeyboardVisible() {
        return this.keyboardVisible;
    }

    protected void openKeyboardInternal() {
        onWaitingForKeyboard();
        showPopup((AndroidUtilities.usingHardwareInput || this.isPaused) ? 0 : 2);
        this.editText.requestFocus();
        AndroidUtilities.showKeyboard(this.editText);
        if (this.isPaused) {
            this.showKeyboardOnResume = true;
            return;
        }
        if (AndroidUtilities.usingHardwareInput || this.keyboardVisible || AndroidUtilities.isInMultiwindow || AndroidUtilities.isTablet()) {
            return;
        }
        this.waitingForKeyboardOpen = true;
        AndroidUtilities.cancelRunOnUIThread(this.openKeyboardRunnable);
        AndroidUtilities.runOnUIThread(this.openKeyboardRunnable, 100L);
    }

    protected void showPopup(int i) {
        if (i == 1) {
            EmojiView emojiView = this.emojiView;
            boolean z = emojiView != null && emojiView.getVisibility() == 0;
            createEmojiView();
            this.emojiView.setVisibility(0);
            this.emojiViewVisible = true;
            this.emojiViewAlpha = 1.0f;
            EmojiView emojiView2 = this.emojiView;
            if (this.keyboardHeight <= 0) {
                if (AndroidUtilities.isTablet()) {
                    this.keyboardHeight = AndroidUtilities.m1081dp(150.0f);
                } else {
                    this.keyboardHeight = MessagesController.getGlobalEmojiSettings().getInt("kbd_height", AndroidUtilities.m1081dp(200.0f));
                }
            }
            if (this.keyboardHeightLand <= 0) {
                if (AndroidUtilities.isTablet()) {
                    this.keyboardHeightLand = AndroidUtilities.m1081dp(150.0f);
                } else {
                    this.keyboardHeightLand = MessagesController.getGlobalEmojiSettings().getInt("kbd_height_land3", AndroidUtilities.m1081dp(200.0f));
                }
            }
            Point point = AndroidUtilities.displaySize;
            int iMin = (point.x > point.y ? this.keyboardHeightLand : this.keyboardHeight) + (this.includeNavigationBar ? AndroidUtilities.navigationBarHeight : 0);
            if (this.emojiExpanded) {
                iMin = Math.min(iMin + AndroidUtilities.m1081dp(200.0f), AndroidUtilities.displaySize.y);
            }
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) emojiView2.getLayoutParams();
            layoutParams.height = iMin;
            emojiView2.setLayoutParams(layoutParams);
            if (!AndroidUtilities.isInMultiwindow && !AndroidUtilities.isTablet()) {
                AndroidUtilities.hideKeyboard(this.editText);
            }
            SizeNotifierFrameLayout sizeNotifierFrameLayout = this.sizeNotifierLayout;
            if (sizeNotifierFrameLayout != null) {
                this.emojiPadding = iMin;
                sizeNotifierFrameLayout.requestLayout();
                this.emojiIconDrawable.setIcon(C2702R.drawable.input_keyboard, true);
                onWindowSizeChanged();
            }
            onEmojiKeyboardUpdate();
            if (!this.keyboardVisible && !z && allowSearchAnimation()) {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.emojiPadding, 0.0f);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.EditTextEmoji$$ExternalSyntheticLambda3
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$showPopup$3(valueAnimator);
                    }
                });
                valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EditTextEmoji.5
                    C41035() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        EditTextEmoji.this.emojiView.setTranslationY(0.0f);
                        EditTextEmoji.this.emojiView.setAlpha(1.0f);
                        EditTextEmoji.this.emojiViewAlpha = 1.0f;
                        EditTextEmoji.this.bottomPanelTranslationY(0.0f);
                    }
                });
                valueAnimatorOfFloat.setDuration(250L);
                valueAnimatorOfFloat.setInterpolator(AdjustPanLayoutHelper.keyboardInterpolator);
                valueAnimatorOfFloat.start();
                return;
            }
            this.emojiView.setAlpha(1.0f);
            this.emojiViewAlpha = 1.0f;
            bottomPanelTranslationY(0.0f);
            return;
        }
        if (this.emojiButton != null) {
            if (this.currentStyle == 0) {
                this.emojiIconDrawable.setIcon(C2702R.drawable.smiles_tab_smiles, true);
            } else {
                this.emojiIconDrawable.setIcon(C2702R.drawable.input_smile, true);
            }
        }
        if (this.emojiView != null) {
            this.emojiViewVisible = false;
            onEmojiKeyboardUpdate();
            if (AndroidUtilities.usingHardwareInput || AndroidUtilities.isInMultiwindow) {
                this.emojiView.setVisibility(8);
                this.emojiViewAlpha = 0.0f;
            }
        }
        SizeNotifierFrameLayout sizeNotifierFrameLayout2 = this.sizeNotifierLayout;
        if (sizeNotifierFrameLayout2 != null) {
            if (i == 0) {
                this.emojiPadding = 0;
                this.emojiViewAlpha = 0.0f;
            }
            sizeNotifierFrameLayout2.requestLayout();
            onWindowSizeChanged();
        }
    }

    public /* synthetic */ void lambda$showPopup$3(ValueAnimator valueAnimator) {
        int i;
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.emojiView.setTranslationY(fFloatValue);
        int i2 = this.emojiPadding;
        float f = 1.0f - (fFloatValue / i2);
        this.emojiViewAlpha = f;
        if (i2 > 0 && ((i = this.currentStyle) == 2 || i == 3)) {
            this.emojiView.setAlpha(f);
        }
        bottomPanelTranslationY(fFloatValue);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextEmoji$5 */
    /* JADX INFO: loaded from: classes5.dex */
    class C41035 extends AnimatorListenerAdapter {
        C41035() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            EditTextEmoji.this.emojiView.setTranslationY(0.0f);
            EditTextEmoji.this.emojiView.setAlpha(1.0f);
            EditTextEmoji.this.emojiViewAlpha = 1.0f;
            EditTextEmoji.this.bottomPanelTranslationY(0.0f);
        }
    }

    private void onWindowSizeChanged() {
        this.sizeNotifierLayout.getHeight();
    }

    protected void createEmojiView() {
        EmojiView emojiView = this.emojiView;
        if (emojiView != null && emojiView.currentAccount != UserConfig.selectedAccount) {
            this.sizeNotifierLayout.removeView(emojiView);
            this.emojiView = null;
        }
        if (this.emojiView != null) {
            return;
        }
        BaseFragment baseFragment = this.parentFragment;
        boolean z = this.allowAnimatedEmoji && (UserConfig.getInstance(UserConfig.selectedAccount).isPremium() || LocaleUtils.canUseLocalPremiumEmojis());
        Context context = getContext();
        boolean zAllowSearch = allowSearch();
        int i = this.currentStyle;
        C41046 c41046 = new EmojiView(baseFragment, z, false, false, context, zAllowSearch, null, null, (i == 2 || i == 3 || i == 5) ? false : true, this.resourcesProvider, false, this.glassDesignForEmojiView) { // from class: org.telegram.ui.Components.EditTextEmoji.6
            private boolean changedExpanded;
            private boolean lastExpanded;
            private int lastHeight;

            C41046(BaseFragment baseFragment2, boolean z2, boolean z3, boolean z4, Context context2, boolean zAllowSearch2, TLRPC.ChatFull chatFull, ViewGroup viewGroup, boolean z5, Theme.ResourcesProvider resourcesProvider, boolean z6, boolean z7) {
                super(baseFragment2, z2, z3, z4, context2, zAllowSearch2, chatFull, viewGroup, z5, resourcesProvider, z6, z7);
            }

            @Override // org.telegram.p026ui.Components.EmojiView, android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                if (EditTextEmoji.this.currentStyle == 2 || EditTextEmoji.this.currentStyle == 3) {
                    EditTextEmoji.this.drawEmojiBackground(canvas, this);
                }
                super.dispatchDraw(canvas);
            }

            @Override // org.telegram.p026ui.Components.EmojiView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
            protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
                int i6;
                super.onLayout(z2, i2, i3, i4, i5);
                if (EditTextEmoji.this.allowSearch()) {
                    int i7 = i5 - i3;
                    if (!this.lastExpanded && EditTextEmoji.this.emojiExpanded) {
                        this.changedExpanded = true;
                    }
                    if (this.changedExpanded && (i6 = this.lastHeight) > 0 && i7 > 0 && i7 != i6) {
                        setTranslationY(i7 - i6);
                        animate().translationY(0.0f).setInterpolator(AdjustPanLayoutHelper.keyboardInterpolator).setDuration(250L).start();
                        this.changedExpanded = false;
                    }
                    this.lastExpanded = EditTextEmoji.this.emojiExpanded;
                    this.lastHeight = i7;
                }
            }
        };
        this.emojiView = c41046;
        c41046.emojiCacheType = this.emojiViewCacheType;
        c41046.allowEmojisForNonPremium(this.allowEmojisForNonPremium);
        this.emojiView.setVisibility(8);
        this.emojiViewAlpha = 0.0f;
        if (AndroidUtilities.isTablet()) {
            this.emojiView.setForseMultiwindowLayout(true);
        }
        this.emojiView.setDelegate(new C41057());
        this.sizeNotifierLayout.addView(this.emojiView);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextEmoji$6 */
    /* JADX INFO: loaded from: classes5.dex */
    class C41046 extends EmojiView {
        private boolean changedExpanded;
        private boolean lastExpanded;
        private int lastHeight;

        C41046(BaseFragment baseFragment2, boolean z2, boolean z3, boolean z4, Context context2, boolean zAllowSearch2, TLRPC.ChatFull chatFull, ViewGroup viewGroup, boolean z5, Theme.ResourcesProvider resourcesProvider, boolean z6, boolean z7) {
            super(baseFragment2, z2, z3, z4, context2, zAllowSearch2, chatFull, viewGroup, z5, resourcesProvider, z6, z7);
        }

        @Override // org.telegram.p026ui.Components.EmojiView, android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            if (EditTextEmoji.this.currentStyle == 2 || EditTextEmoji.this.currentStyle == 3) {
                EditTextEmoji.this.drawEmojiBackground(canvas, this);
            }
            super.dispatchDraw(canvas);
        }

        @Override // org.telegram.p026ui.Components.EmojiView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
            int i6;
            super.onLayout(z2, i2, i3, i4, i5);
            if (EditTextEmoji.this.allowSearch()) {
                int i7 = i5 - i3;
                if (!this.lastExpanded && EditTextEmoji.this.emojiExpanded) {
                    this.changedExpanded = true;
                }
                if (this.changedExpanded && (i6 = this.lastHeight) > 0 && i7 > 0 && i7 != i6) {
                    setTranslationY(i7 - i6);
                    animate().translationY(0.0f).setInterpolator(AdjustPanLayoutHelper.keyboardInterpolator).setDuration(250L).start();
                    this.changedExpanded = false;
                }
                this.lastExpanded = EditTextEmoji.this.emojiExpanded;
                this.lastHeight = i7;
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextEmoji$7 */
    /* JADX INFO: loaded from: classes5.dex */
    class C41057 implements EmojiView.EmojiViewDelegate {
        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ boolean canSchedule() {
            return EmojiView.EmojiViewDelegate.CC.$default$canSchedule(this);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ long getDialogId() {
            return EmojiView.EmojiViewDelegate.CC.$default$getDialogId(this);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ float getProgressToSearchOpened() {
            return EmojiView.EmojiViewDelegate.CC.$default$getProgressToSearchOpened(this);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ int getThreadId() {
            return EmojiView.EmojiViewDelegate.CC.$default$getThreadId(this);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ void invalidateEnterView() {
            EmojiView.EmojiViewDelegate.CC.$default$invalidateEnterView(this);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ boolean isExpanded() {
            return EmojiView.EmojiViewDelegate.CC.$default$isExpanded(this);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ boolean isInScheduleMode() {
            return EmojiView.EmojiViewDelegate.CC.$default$isInScheduleMode(this);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ boolean isUserSelf() {
            return EmojiView.EmojiViewDelegate.CC.$default$isUserSelf(this);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ void onEmojiSettingsClick(ArrayList arrayList) {
            EmojiView.EmojiViewDelegate.CC.$default$onEmojiSettingsClick(this, arrayList);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        /* JADX INFO: renamed from: onGifSelected */
        public /* synthetic */ void lambda$onGifSelected$1(View view, Object obj, String str, Object obj2, boolean z, int i, int i2) {
            EmojiView.EmojiViewDelegate.CC.$default$onGifSelected(this, view, obj, str, obj2, z, i, i2);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ void onShowStickerSet(TLRPC.StickerSet stickerSet, TLRPC.InputStickerSet inputStickerSet, boolean z) {
            EmojiView.EmojiViewDelegate.CC.$default$onShowStickerSet(this, stickerSet, inputStickerSet, z);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ void onStickerSelected(View view, TLRPC.Document document, String str, Object obj, MessageObject.SendAnimationData sendAnimationData, boolean z, int i, int i2) {
            EmojiView.EmojiViewDelegate.CC.$default$onStickerSelected(this, view, document, str, obj, sendAnimationData, z, i, i2);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ void onStickerSetAdd(TLRPC.StickerSetCovered stickerSetCovered) {
            EmojiView.EmojiViewDelegate.CC.$default$onStickerSetAdd(this, stickerSetCovered);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ void onStickerSetRemove(TLRPC.StickerSetCovered stickerSetCovered) {
            EmojiView.EmojiViewDelegate.CC.$default$onStickerSetRemove(this, stickerSetCovered);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ void onStickersGroupClick(long j) {
            EmojiView.EmojiViewDelegate.CC.$default$onStickersGroupClick(this, j);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ void onStickersSettingsClick() {
            EmojiView.EmojiViewDelegate.CC.$default$onStickersSettingsClick(this);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ void onTabOpened(int i) {
            EmojiView.EmojiViewDelegate.CC.$default$onTabOpened(this, i);
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public /* synthetic */ void showTrendingStickersAlert(TrendingStickersLayout trendingStickersLayout) {
            EmojiView.EmojiViewDelegate.CC.$default$showTrendingStickersAlert(this, trendingStickersLayout);
        }

        C41057() {
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public boolean onBackspace() {
            if (EditTextEmoji.this.editText.length() == 0) {
                return false;
            }
            EditTextEmoji.this.editText.dispatchKeyEvent(new KeyEvent(0, 67));
            return true;
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public void onSearchOpenClose(int i) {
            if (EditTextEmoji.this.allowSearch()) {
                EditTextEmoji editTextEmoji = EditTextEmoji.this;
                editTextEmoji.emojiExpanded = i != 0;
                editTextEmoji.updatedEmojiExpanded();
                if (EditTextEmoji.this.sizeNotifierLayout != null) {
                    EditTextEmoji.this.sizeNotifierLayout.notifyHeightChanged();
                }
            }
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public boolean isSearchOpened() {
            return EditTextEmoji.this.emojiExpanded;
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public void onAnimatedEmojiUnlockClick() {
            BaseFragment baseFragment = EditTextEmoji.this.parentFragment;
            if (baseFragment == null) {
                new PremiumFeatureBottomSheet(new BaseFragment() { // from class: org.telegram.ui.Components.EditTextEmoji.7.1
                    AnonymousClass1() {
                    }

                    @Override // org.telegram.p026ui.ActionBar.BaseFragment
                    public int getCurrentAccount() {
                        return this.currentAccount;
                    }

                    @Override // org.telegram.p026ui.ActionBar.BaseFragment
                    public Context getContext() {
                        return EditTextEmoji.this.getContext();
                    }

                    @Override // org.telegram.p026ui.ActionBar.BaseFragment
                    public Activity getParentActivity() {
                        for (Context context = getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
                            if (context instanceof Activity) {
                                return (Activity) context;
                            }
                        }
                        return null;
                    }

                    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextEmoji$7$1$1 */
                    class DialogC73751 extends Dialog {
                        DialogC73751(Context context) {
                            super(context);
                        }

                        @Override // android.app.Dialog, android.content.DialogInterface
                        public void dismiss() {
                            EditTextEmoji.this.hidePopup(false);
                            EditTextEmoji.this.closeParent();
                        }
                    }

                    @Override // org.telegram.p026ui.ActionBar.BaseFragment
                    public Dialog getVisibleDialog() {
                        return new Dialog(EditTextEmoji.this.getContext()) { // from class: org.telegram.ui.Components.EditTextEmoji.7.1.1
                            DialogC73751(Context context) {
                                super(context);
                            }

                            @Override // android.app.Dialog, android.content.DialogInterface
                            public void dismiss() {
                                EditTextEmoji.this.hidePopup(false);
                                EditTextEmoji.this.closeParent();
                            }
                        };
                    }
                }, 11, false).show();
            } else {
                baseFragment.showDialog(new PremiumFeatureBottomSheet(baseFragment, 11, false));
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextEmoji$7$1 */
        class AnonymousClass1 extends BaseFragment {
            AnonymousClass1() {
            }

            @Override // org.telegram.p026ui.ActionBar.BaseFragment
            public int getCurrentAccount() {
                return this.currentAccount;
            }

            @Override // org.telegram.p026ui.ActionBar.BaseFragment
            public Context getContext() {
                return EditTextEmoji.this.getContext();
            }

            @Override // org.telegram.p026ui.ActionBar.BaseFragment
            public Activity getParentActivity() {
                for (Context context = getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
                    if (context instanceof Activity) {
                        return (Activity) context;
                    }
                }
                return null;
            }

            /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextEmoji$7$1$1 */
            class DialogC73751 extends Dialog {
                DialogC73751(Context context) {
                    super(context);
                }

                @Override // android.app.Dialog, android.content.DialogInterface
                public void dismiss() {
                    EditTextEmoji.this.hidePopup(false);
                    EditTextEmoji.this.closeParent();
                }
            }

            @Override // org.telegram.p026ui.ActionBar.BaseFragment
            public Dialog getVisibleDialog() {
                return new Dialog(EditTextEmoji.this.getContext()) { // from class: org.telegram.ui.Components.EditTextEmoji.7.1.1
                    DialogC73751(Context context) {
                        super(context);
                    }

                    @Override // android.app.Dialog, android.content.DialogInterface
                    public void dismiss() {
                        EditTextEmoji.this.hidePopup(false);
                        EditTextEmoji.this.closeParent();
                    }
                };
            }
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public void onEmojiSelected(String str) {
            int selectionEnd = EditTextEmoji.this.editText.getSelectionEnd();
            if (selectionEnd < 0) {
                selectionEnd = 0;
            }
            try {
                try {
                    EditTextEmoji.this.innerTextChange = 2;
                    CharSequence charSequenceReplaceEmoji = Emoji.replaceEmoji(str, EditTextEmoji.this.editText.getPaint().getFontMetricsInt(), false);
                    EditTextEmoji.this.editText.setText(EditTextEmoji.this.editText.getText().insert(selectionEnd, charSequenceReplaceEmoji));
                    int length = selectionEnd + charSequenceReplaceEmoji.length();
                    EditTextEmoji.this.editText.setSelection(length, length);
                } catch (Exception e) {
                    FileLog.m1093e(e);
                }
            } finally {
                EditTextEmoji.this.innerTextChange = 0;
            }
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public void onCustomEmojiSelected(long j, TLRPC.Document document, String str, boolean z) {
            AnimatedEmojiSpan animatedEmojiSpan;
            int selectionEnd = EditTextEmoji.this.editText.getSelectionEnd();
            if (selectionEnd < 0) {
                selectionEnd = 0;
            }
            try {
                try {
                    EditTextEmoji.this.innerTextChange = 2;
                    SpannableString spannableString = new SpannableString(str);
                    if (document != null) {
                        animatedEmojiSpan = new AnimatedEmojiSpan(document, EditTextEmoji.this.editText.getPaint().getFontMetricsInt());
                    } else {
                        animatedEmojiSpan = new AnimatedEmojiSpan(j, EditTextEmoji.this.editText.getPaint().getFontMetricsInt());
                    }
                    animatedEmojiSpan.cacheType = EditTextEmoji.this.emojiView.emojiCacheType;
                    spannableString.setSpan(animatedEmojiSpan, 0, spannableString.length(), 33);
                    EditTextEmoji.this.editText.setText(EditTextEmoji.this.editText.getText().insert(selectionEnd, spannableString));
                    int length = selectionEnd + spannableString.length();
                    EditTextEmoji.this.editText.setSelection(length, length);
                } catch (Exception e) {
                    FileLog.m1093e(e);
                }
                EditTextEmoji.this.innerTextChange = 0;
            } catch (Throwable th) {
                EditTextEmoji.this.innerTextChange = 0;
                throw th;
            }
        }

        @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
        public void onClearEmojiRecent() {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditTextEmoji.this.getContext(), EditTextEmoji.this.resourcesProvider);
            builder.setTitle(LocaleController.getString(C2702R.string.ClearRecentEmojiTitle));
            builder.setMessage(LocaleController.getString(C2702R.string.ClearRecentEmojiText));
            builder.setPositiveButton(LocaleController.getString(C2702R.string.ClearButton), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.EditTextEmoji$7$$ExternalSyntheticLambda0
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    this.f$0.lambda$onClearEmojiRecent$0(alertDialog, i);
                }
            });
            builder.setNegativeButton(LocaleController.getString(C2702R.string.Cancel), null);
            if (EditTextEmoji.this.parentFragment != null) {
                EditTextEmoji.this.parentFragment.showDialog(builder.create());
            } else {
                builder.show();
            }
        }

        public /* synthetic */ void lambda$onClearEmojiRecent$0(AlertDialog alertDialog, int i) {
            EditTextEmoji.this.emojiView.clearRecentEmoji();
        }
    }

    public boolean isPopupView(View view) {
        return view == this.emojiView;
    }

    public int getEmojiPadding() {
        return this.emojiPadding;
    }

    public int getKeyboardHeight() {
        Point point = AndroidUtilities.displaySize;
        int i = (point.x > point.y ? this.keyboardHeightLand : this.keyboardHeight) + (this.includeNavigationBar ? AndroidUtilities.navigationBarHeight : 0);
        return this.emojiExpanded ? Math.min(i + AndroidUtilities.m1081dp(200.0f), AndroidUtilities.displaySize.y) : i;
    }

    @Override // org.telegram.ui.Components.SizeNotifierFrameLayout.SizeNotifierFrameLayoutDelegate
    public void onSizeChanged(int i, boolean z) {
        boolean z2;
        int i2;
        if (i > AndroidUtilities.m1081dp(50.0f) && ((this.keyboardVisible || (i2 = this.currentStyle) == 2 || i2 == 3) && !AndroidUtilities.isInMultiwindow && !AndroidUtilities.isTablet())) {
            if (z) {
                this.keyboardHeightLand = i;
                MessagesController.getGlobalEmojiSettings().edit().putInt("kbd_height_land3", this.keyboardHeightLand).apply();
            } else {
                this.keyboardHeight = i;
                MessagesController.getGlobalEmojiSettings().edit().putInt("kbd_height", this.keyboardHeight).apply();
            }
        }
        boolean z3 = false;
        if (isPopupShowing()) {
            int iMin = (z ? this.keyboardHeightLand : this.keyboardHeight) + (this.includeNavigationBar ? AndroidUtilities.navigationBarHeight : 0);
            if (this.emojiExpanded) {
                iMin = Math.min(iMin + AndroidUtilities.m1081dp(200.0f), AndroidUtilities.displaySize.y);
            }
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.emojiView.getLayoutParams();
            int i3 = layoutParams.width;
            int i4 = AndroidUtilities.displaySize.x;
            if (i3 != i4 || layoutParams.height != iMin) {
                layoutParams.width = i4;
                layoutParams.height = iMin;
                this.emojiView.setLayoutParams(layoutParams);
                SizeNotifierFrameLayout sizeNotifierFrameLayout = this.sizeNotifierLayout;
                if (sizeNotifierFrameLayout != null) {
                    this.emojiPadding = layoutParams.height;
                    sizeNotifierFrameLayout.requestLayout();
                    onWindowSizeChanged();
                    if (this.lastEmojiExpanded != this.emojiExpanded) {
                        onEmojiKeyboardUpdate();
                    }
                }
            }
        }
        this.lastEmojiExpanded = this.emojiExpanded;
        if (this.lastSizeChangeValue1 == i && this.lastSizeChangeValue2 == z) {
            if (allowSearch()) {
                if (this.editText.isFocused() && i > 0) {
                    z3 = true;
                }
                this.keyboardVisible = z3;
            }
            onWindowSizeChanged();
            return;
        }
        this.lastSizeChangeValue1 = i;
        this.lastSizeChangeValue2 = z;
        boolean z4 = this.keyboardVisible;
        boolean z5 = this.editText.isFocused() && i > 0;
        this.keyboardVisible = z5;
        if (z5 && isPopupShowing()) {
            showPopup(0);
        }
        if (this.emojiPadding != 0 && !(z2 = this.keyboardVisible) && z2 != z4 && !isPopupShowing()) {
            this.emojiPadding = 0;
            this.sizeNotifierLayout.requestLayout();
        }
        if (this.keyboardVisible && this.waitingForKeyboardOpen) {
            this.waitingForKeyboardOpen = false;
            AndroidUtilities.cancelRunOnUIThread(this.openKeyboardRunnable);
        }
        onWindowSizeChanged();
    }

    public EditTextCaption getEditText() {
        return this.editText;
    }

    public View getEmojiButton() {
        return this.emojiButton;
    }

    private int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    public void setEmojiViewCacheType(int i) {
        this.emojiViewCacheType = i;
        EmojiView emojiView = this.emojiView;
        if (emojiView != null) {
            emojiView.emojiCacheType = i;
        }
    }
}
