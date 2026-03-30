package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Property;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.arch.core.util.Function;
import androidx.camera.core.ImageCapture$$ExternalSyntheticBackport1;
import androidx.collection.LongSparseArray;
import androidx.core.view.ViewCompat;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.backup.PreferencesUtils;
import com.exteragram.messenger.components.TranslateBeforeSendWrapper;
import com.exteragram.messenger.utils.text.TranslatorUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.tl.TL_stories;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.ui.ActionBar.ActionBarPopupWindow;
import org.telegram.ui.ActionBar.AdjustPanLayoutHelper;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Adapters.DialogsSearchAdapter;
import org.telegram.ui.Adapters.SearchAdapterHelper;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.HintDialogCell;
import org.telegram.ui.Cells.ProfileSearchCell;
import org.telegram.ui.Cells.ShareDialogCell;
import org.telegram.ui.Cells.ShareTopicCell;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.ChatActivityEnterView;
import org.telegram.ui.Components.Forum.ForumUtilities;
import org.telegram.ui.Components.MessagePreviewView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.blur3.BlurredBackgroundDrawableViewFactory;
import org.telegram.ui.Components.blur3.BlurredBackgroundWithFadeDrawable;
import org.telegram.ui.Components.blur3.DownscaleScrollableNoiseSuppressor;
import org.telegram.ui.Components.blur3.capture.IBlur3Capture;
import org.telegram.ui.Components.blur3.drawable.BlurredBackgroundDrawable;
import org.telegram.ui.Components.blur3.source.BlurredBackgroundSourceColor;
import org.telegram.ui.Components.blur3.source.BlurredBackgroundSourceRenderNode;
import org.telegram.ui.LaunchActivity;
import org.telegram.ui.MessageStatisticActivity;
import org.telegram.ui.PhotoViewer;
import org.telegram.ui.PremiumPreviewFragment;

/* JADX INFO: loaded from: classes5.dex */
public class ShareAlert extends BottomSheet implements NotificationCenter.NotificationCenterDelegate {
    private AnimatorSet animatorSet;
    private View bottomFadeView;
    private FrameLayout bulletinContainer;
    public FrameLayout bulletinContainer2;
    private BlurredBackgroundDrawable captionContainerBg;
    private float captionEditTextTopOffset;
    private float chatActivityEnterViewAnimateFromTop;
    private EditTextEmoji commentTextView;
    private int containerViewTop;
    private boolean copyLinkOnEnd;
    private float currentPanTranslationY;
    private boolean darkTheme;
    private ShareAlertDelegate delegate;
    private BlurredBackgroundDrawable emojiViewChildBg;
    private TLRPC.TL_exportedMessageLink exportedMessageLink;
    private BlurredBackgroundWithFadeDrawable fadeDrawable;
    public boolean forceDarkThemeForHint;
    private FrameLayout frameLayout;
    private FrameLayout frameLayout2;
    private boolean fullyShown;
    private RecyclerListView gridView;
    private int hasPoll;
    private boolean hideCaption;
    private boolean hideSendersName;
    private IBlur3Capture iBlur3Capture;
    private final BlurredBackgroundDrawableViewFactory iBlur3FactoryFade;
    private final BlurredBackgroundDrawableViewFactory iBlur3FactoryFrostedLiquidGlass;
    private final BlurredBackgroundDrawableViewFactory iBlur3FactoryLiquidGlass;
    private final RectF iBlur3PositionMainTabs;
    private final ArrayList iBlur3Positions;
    private final BlurredBackgroundSourceColor iBlur3SourceColor;
    private final BlurredBackgroundSourceRenderNode iBlur3SourceGlass;
    private final BlurredBackgroundSourceRenderNode iBlur3SourceGlassFrosted;
    private boolean includeStory;
    public boolean includeStoryFromMessage;
    private boolean isChannel;
    private int keyboardSize2;
    private float keyboardT;
    int lastOffset;
    private GridLayoutManager layoutManager;
    private LinearLayout linkContainer;
    private TextView linkCopyButton;
    private SimpleTextView linkTextView;
    private String[] linkToCopy;
    private ShareDialogsAdapter listAdapter;
    private boolean loadingLink;
    private Paint paint;
    private boolean panTranslationMoveLayout;
    private Activity parentActivity;
    private ChatActivity parentFragment;
    private FrameLayout pickerBottom;
    private FrameLayout pickerBottomLayout;
    private int previousScrollOffsetY;
    private ArrayList recentSearchObjects;
    private LongSparseArray recentSearchObjectsById;
    private RectF rect;
    RecyclerItemsEnterAnimator recyclerItemsEnterAnimator;
    private int scrollOffsetY;
    private final DownscaleScrollableNoiseSuppressor scrollableViewNoiseSuppressor;
    private ShareSearchAdapter searchAdapter;
    private StickerEmptyView searchEmptyView;
    private RecyclerListView searchGridView;
    private boolean searchIsVisible;
    private FillLastGridLayoutManager searchLayoutManager;
    FragmentSearchField searchView;
    private boolean searchWasVisibleBeforeTopics;
    protected Map selectedDialogTopics;
    protected LongSparseArray selectedDialogs;
    private TLRPC.Dialog selectedTopicDialog;
    private ActionBarPopupWindow sendPopupWindow;
    private String sendingFile;
    protected ArrayList sendingMessageObjects;
    private String[] sendingText;
    private View[] shadow;
    private AnimatorSet[] shadowAnimation;
    private Drawable shadowDrawable;
    private ShareTopicsAdapter shareTopicsAdapter;
    private LinearLayout sharesCountLayout;
    private int shiftDp;
    private SizeNotifierFrameLayout sizeNotifierFrameLayout;
    TL_stories.StoryItem storyItem;
    private SwitchView switchView;
    private TextPaint textPaint;
    public int timestamp;
    public CheckBox2 timestampCheckbox;
    public FrameLayout timestampFrameLayout;
    public LinearLayout timestampLayout;
    public TextView timestampTextView;
    private ValueAnimator topBackgroundAnimator;
    private int topBeforeSwitch;
    private SpringAnimation topicsAnimation;
    ActionBar topicsBackActionBar;
    private RecyclerListView topicsGridView;
    private GridLayoutManager topicsLayoutManager;
    private boolean updateSearchAdapter;
    private ChatActivityEnterView.SendButton writeButton;
    private FrameLayout writeButtonContainer;

    /* JADX INFO: loaded from: classes3.dex */
    public static class DialogSearchResult {
        public int date;
        public TLRPC.Dialog dialog = new TLRPC.TL_dialog();
        public CharSequence name;
        public TLObject object;
    }

    @Override // org.telegram.ui.ActionBar.BottomSheet
    protected boolean canDismissWithSwipe() {
        return false;
    }

    protected void onSend(LongSparseArray longSparseArray, int i, TLRPC.TL_forumTopic tL_forumTopic, boolean z) {
    }

    protected void onShareStory(View view) {
    }

    public void setStoryToShare(TL_stories.StoryItem storyItem) {
        this.storyItem = storyItem;
    }

    public interface ShareAlertDelegate {
        boolean didCopy();

        void didShare();

        /* JADX INFO: renamed from: org.telegram.ui.Components.ShareAlert$ShareAlertDelegate$-CC, reason: invalid class name */
        public abstract /* synthetic */ class CC {
            public static void $default$didShare(ShareAlertDelegate shareAlertDelegate) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SwitchView extends FrameLayout {
        private AnimatorSet animator;
        private int currentTab;
        private int lastColor;
        private SimpleTextView leftTab;
        private LinearGradient linearGradient;
        private Paint paint;
        private RectF rect;
        private SimpleTextView rightTab;
        private View searchBackground;
        private View slidingView;

        protected abstract void onTabSwitch(int i);

        public SwitchView(Context context) {
            super(context);
            this.paint = new Paint(1);
            this.rect = new RectF();
            View view = new View(context);
            this.searchBackground = view;
            view.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(18.0f), ShareAlert.this.getThemedColor(Theme.key_dialogSearchBackground)));
            addView(this.searchBackground, LayoutHelper.createFrame(-1, 36.0f, 51, 14.0f, 0.0f, 14.0f, 0.0f));
            View view2 = new View(context) { // from class: org.telegram.ui.Components.ShareAlert.SwitchView.1
                @Override // android.view.View
                public void setTranslationX(float f) {
                    super.setTranslationX(f);
                    invalidate();
                }

                @Override // android.view.View
                protected void onDraw(Canvas canvas) {
                    super.onDraw(canvas);
                    int offsetColor = AndroidUtilities.getOffsetColor(-9057429, -10513163, getTranslationX() / getMeasuredWidth(), 1.0f);
                    int offsetColor2 = AndroidUtilities.getOffsetColor(-11554882, -4629871, getTranslationX() / getMeasuredWidth(), 1.0f);
                    if (offsetColor != SwitchView.this.lastColor) {
                        SwitchView.this.linearGradient = new LinearGradient(0.0f, 0.0f, getMeasuredWidth(), 0.0f, new int[]{offsetColor, offsetColor2}, (float[]) null, Shader.TileMode.CLAMP);
                        SwitchView.this.paint.setShader(SwitchView.this.linearGradient);
                    }
                    SwitchView.this.rect.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
                    canvas.drawRoundRect(SwitchView.this.rect, AndroidUtilities.dp(18.0f), AndroidUtilities.dp(18.0f), SwitchView.this.paint);
                }
            };
            this.slidingView = view2;
            addView(view2, LayoutHelper.createFrame(-1, 36.0f, 51, 14.0f, 0.0f, 14.0f, 0.0f));
            SimpleTextView simpleTextView = new SimpleTextView(context);
            this.leftTab = simpleTextView;
            int i = Theme.key_voipgroup_nameText;
            simpleTextView.setTextColor(ShareAlert.this.getThemedColor(i));
            this.leftTab.setTextSize(13);
            this.leftTab.setLeftDrawable(R.drawable.msg_tabs_mic1);
            this.leftTab.setText(LocaleController.getString(R.string.VoipGroupInviteCanSpeak));
            this.leftTab.setGravity(17);
            addView(this.leftTab, LayoutHelper.createFrame(-1, -1.0f, 51, 14.0f, 0.0f, 0.0f, 0.0f));
            this.leftTab.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ShareAlert$SwitchView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    this.f$0.lambda$new$0(view3);
                }
            });
            SimpleTextView simpleTextView2 = new SimpleTextView(context);
            this.rightTab = simpleTextView2;
            simpleTextView2.setTextColor(ShareAlert.this.getThemedColor(i));
            this.rightTab.setTextSize(13);
            this.rightTab.setLeftDrawable(R.drawable.msg_tabs_mic2);
            this.rightTab.setText(LocaleController.getString(R.string.VoipGroupInviteListenOnly));
            this.rightTab.setGravity(17);
            addView(this.rightTab, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 0.0f, 14.0f, 0.0f));
            this.rightTab.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ShareAlert$SwitchView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    this.f$0.lambda$new$1(view3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view) {
            switchToTab(0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(View view) {
            switchToTab(1);
        }

        private void switchToTab(int i) {
            if (this.currentTab == i) {
                return;
            }
            this.currentTab = i;
            AnimatorSet animatorSet = this.animator;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.animator = animatorSet2;
            animatorSet2.playTogether(ObjectAnimator.ofFloat(this.slidingView, (Property<View, Float>) View.TRANSLATION_X, this.currentTab == 0 ? 0.0f : r0.getMeasuredWidth()));
            this.animator.setDuration(180L);
            this.animator.setInterpolator(CubicBezierInterpolator.EASE_OUT);
            this.animator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ShareAlert.SwitchView.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    SwitchView.this.animator = null;
                }
            });
            this.animator.start();
            onTabSwitch(this.currentTab);
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            int size = (View.MeasureSpec.getSize(i) - AndroidUtilities.dp(28.0f)) / 2;
            ((FrameLayout.LayoutParams) this.leftTab.getLayoutParams()).width = size;
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.rightTab.getLayoutParams();
            layoutParams.width = size;
            layoutParams.leftMargin = AndroidUtilities.dp(14.0f) + size;
            ((FrameLayout.LayoutParams) this.slidingView.getLayoutParams()).width = size;
            AnimatorSet animatorSet = this.animator;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            this.slidingView.setTranslationX(this.currentTab == 0 ? 0.0f : r1.width);
            super.onMeasure(i, i2);
        }
    }

    public static ShareAlert createShareAlert(Context context, MessageObject messageObject, String str, boolean z, String str2, boolean z2) {
        ArrayList arrayList;
        if (messageObject != null) {
            arrayList = new ArrayList();
            arrayList.add(messageObject);
        } else {
            arrayList = null;
        }
        return new ShareAlert(context, null, arrayList, str, null, z, str2, null, z2, false);
    }

    public ShareAlert(Context context, ArrayList arrayList, String str, boolean z, String str2, boolean z2) {
        this(context, arrayList, str, z, str2, z2, null);
    }

    public ShareAlert(Context context, ArrayList arrayList, String str, boolean z, String str2, boolean z2, Theme.ResourcesProvider resourcesProvider) {
        this(context, null, arrayList, null, str, null, z, str2, null, z2, false, false, null, resourcesProvider);
    }

    public ShareAlert(Context context, ChatActivity chatActivity, ArrayList arrayList, String str, String str2, boolean z, String str3, String str4, boolean z2, boolean z3) {
        this(context, chatActivity, arrayList, null, str, str2, z, str3, str4, z2, z3, false, null, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x0894  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x08b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ShareAlert(final android.content.Context r30, org.telegram.ui.ChatActivity r31, java.util.ArrayList r32, java.lang.String r33, java.lang.String r34, java.lang.String r35, boolean r36, java.lang.String r37, java.lang.String r38, boolean r39, boolean r40, boolean r41, java.lang.Integer r42, org.telegram.ui.ActionBar.Theme.ResourcesProvider r43) {
        /*
            Method dump skipped, instruction units count: 3410
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.ShareAlert.<init>(android.content.Context, org.telegram.ui.ChatActivity, java.util.ArrayList, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String, java.lang.String, boolean, boolean, boolean, java.lang.Integer, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(final Context context, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0(tLObject, context);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(TLObject tLObject, Context context) {
        if (tLObject != null) {
            this.exportedMessageLink = (TLRPC.TL_exportedMessageLink) tLObject;
            updateLinkTextView();
            if (this.copyLinkOnEnd) {
                copyLink(context);
            }
        }
        this.loadingLink = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$2(TextView textView, int i, KeyEvent keyEvent) {
        if (keyEvent == null) {
            return false;
        }
        if ((keyEvent.getAction() != 1 || keyEvent.getKeyCode() != 84) && (keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 66)) {
            return false;
        }
        AndroidUtilities.hideKeyboard(this.searchView.editText);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3() {
        this.updateSearchAdapter = true;
        this.searchView.editText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
        AndroidUtilities.showKeyboard(this.searchView.editText);
    }

    public static /* synthetic */ Integer $r8$lambda$ecX7uqh2NAahRSKkgn7yzUDSRcY(Integer num) {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(View view, int i) {
        TLRPC.TL_forumTopic itemTopic = this.shareTopicsAdapter.getItemTopic(i);
        if (itemTopic != null) {
            onTopicCellClick(itemTopic);
        }
    }

    public static /* synthetic */ Integer $r8$lambda$1PcynxTGhKcpqzn9H9A8Iu69dXA(Integer num) {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$7(View view, int i) {
        TLRPC.Dialog item;
        if (i >= 0 && (item = this.listAdapter.getItem(i)) != null) {
            selectDialog(view, item);
        }
    }

    public static /* synthetic */ Integer $r8$lambda$qkwDv7PSB2Nua20LSuVOwcrHWOU(Integer num) {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$9(View view, int i) {
        TLRPC.Dialog item;
        if (i >= 0 && (item = this.searchAdapter.getItem(i)) != null) {
            selectDialog(view, item);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$10(View view) {
        if (this.selectedDialogs.size() == 0) {
            if (this.isChannel || this.linkToCopy[0] != null) {
                dismiss();
                PhotoViewer.getInstance().closePhoto(true, false);
                if (this.linkToCopy[0] == null && this.loadingLink) {
                    this.copyLinkOnEnd = true;
                    Toast.makeText(getContext(), LocaleController.getString(R.string.Loading), 0).show();
                } else {
                    copyLink(getContext());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$11(View view) {
        if (this.selectedDialogs.size() == 0) {
            if (this.isChannel || this.linkToCopy[0] != null) {
                dismiss();
                if (this.linkToCopy[0] == null && this.loadingLink) {
                    this.copyLinkOnEnd = true;
                    Toast.makeText(getContext(), LocaleController.getString(R.string.Loading), 0).show();
                } else {
                    copyLink(getContext());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$12(MessageObject messageObject, View view) {
        BaseFragment safeLastFragment = this.parentFragment;
        if (safeLastFragment == null) {
            safeLastFragment = LaunchActivity.getSafeLastFragment();
        }
        if (safeLastFragment == null) {
            return;
        }
        dismiss();
        safeLastFragment.presentFragment(new MessageStatisticActivity(messageObject));
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ShareAlert$22, reason: invalid class name */
    class AnonymousClass22 extends FrameLayout {
        AnonymousClass22(Context context) {
            super(context);
        }

        @Override // android.view.View
        public void setVisibility(int i) {
            super.setVisibility(i);
            if (i != 0) {
                ShareAlert.this.shadow[1].setTranslationY(0.0f);
            }
        }

        @Override // android.view.View
        public void setAlpha(float f) {
            super.setAlpha(f);
            invalidate();
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            if (ShareAlert.this.chatActivityEnterViewAnimateFromTop != 0.0f && ShareAlert.this.chatActivityEnterViewAnimateFromTop != ShareAlert.this.frameLayout2.getTop() + ShareAlert.this.chatActivityEnterViewAnimateFromTop) {
                if (ShareAlert.this.topBackgroundAnimator != null) {
                    ShareAlert.this.topBackgroundAnimator.cancel();
                }
                ShareAlert shareAlert = ShareAlert.this;
                shareAlert.captionEditTextTopOffset = shareAlert.chatActivityEnterViewAnimateFromTop - (ShareAlert.this.frameLayout2.getTop() + ShareAlert.this.captionEditTextTopOffset);
                ShareAlert shareAlert2 = ShareAlert.this;
                shareAlert2.topBackgroundAnimator = ValueAnimator.ofFloat(shareAlert2.captionEditTextTopOffset, 0.0f);
                ShareAlert.this.topBackgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ShareAlert$22$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$onDraw$0(valueAnimator);
                    }
                });
                ShareAlert.this.topBackgroundAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
                ShareAlert.this.topBackgroundAnimator.setDuration(200L);
                ShareAlert.this.topBackgroundAnimator.start();
                ShareAlert.this.chatActivityEnterViewAnimateFromTop = 0.0f;
            }
            ShareAlert.this.shadow[1].setTranslationY((-(ShareAlert.this.frameLayout2.getMeasuredHeight() - AndroidUtilities.dp(48.0f))) + ShareAlert.this.captionEditTextTopOffset + ShareAlert.this.currentPanTranslationY + ((ShareAlert.this.frameLayout2.getMeasuredHeight() - AndroidUtilities.dp(48.0f)) * (1.0f - getAlpha())));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDraw$0(ValueAnimator valueAnimator) {
            ShareAlert.this.captionEditTextTopOffset = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            ShareAlert.this.frameLayout2.invalidate();
            invalidate();
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            ShareAlert.this.captionContainerBg.setBounds(0, (int) ShareAlert.this.captionEditTextTopOffset, getMeasuredWidth(), getMeasuredHeight());
            ShareAlert.this.captionContainerBg.draw(canvas);
            canvas.save();
            canvas.clipRect(0.0f, ShareAlert.this.captionEditTextTopOffset, getMeasuredWidth(), getMeasuredHeight());
            super.dispatchDraw(canvas);
            canvas.restore();
        }
    }

    public static /* synthetic */ boolean $r8$lambda$fa9TxitH_bTtIw4ogWXMyiVk3as(View view, MotionEvent motionEvent) {
        return true;
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ShareAlert$23, reason: invalid class name */
    class AnonymousClass23 extends EditTextEmoji {
        private ValueAnimator messageEditTextAnimator;
        private int messageEditTextPredrawHeigth;
        private int messageEditTextPredrawScrollY;
        private boolean shouldAnimateEditTextWithBounds;

        AnonymousClass23(Context context, SizeNotifierFrameLayout sizeNotifierFrameLayout, BaseFragment baseFragment, int i, boolean z, Theme.ResourcesProvider resourcesProvider) {
            super(context, sizeNotifierFrameLayout, baseFragment, i, z, resourcesProvider);
        }

        @Override // org.telegram.ui.Components.EditTextEmoji
        protected void bottomPanelTranslationY(float f) {
            super.bottomPanelTranslationY(f);
            ShareAlert.this.updateBottomOverlay();
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            if (this.shouldAnimateEditTextWithBounds) {
                final EditTextCaption editText = ShareAlert.this.commentTextView.getEditText();
                editText.setOffsetY(editText.getOffsetY() - ((this.messageEditTextPredrawHeigth - editText.getMeasuredHeight()) + (this.messageEditTextPredrawScrollY - editText.getScrollY())));
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(editText.getOffsetY(), 0.0f);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ShareAlert$23$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        editText.setOffsetY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
                ValueAnimator valueAnimator = this.messageEditTextAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                this.messageEditTextAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.setDuration(200L);
                valueAnimatorOfFloat.setInterpolator(CubicBezierInterpolator.DEFAULT);
                valueAnimatorOfFloat.start();
                this.shouldAnimateEditTextWithBounds = false;
            }
            super.dispatchDraw(canvas);
        }

        @Override // org.telegram.ui.Components.EditTextEmoji
        protected void onLineCountChanged(int i, int i2) {
            if (!TextUtils.isEmpty(getEditText().getText())) {
                this.shouldAnimateEditTextWithBounds = true;
                this.messageEditTextPredrawHeigth = getEditText().getMeasuredHeight();
                this.messageEditTextPredrawScrollY = getEditText().getScrollY();
                invalidate();
            } else {
                getEditText().animate().cancel();
                getEditText().setOffsetY(0.0f);
                this.shouldAnimateEditTextWithBounds = false;
            }
            ShareAlert.this.chatActivityEnterViewAnimateFromTop = r2.frameLayout2.getTop() + ShareAlert.this.captionEditTextTopOffset;
            ShareAlert.this.frameLayout2.invalidate();
        }

        @Override // org.telegram.ui.Components.EditTextEmoji
        protected void showPopup(int i) {
            super.showPopup(i);
        }

        @Override // org.telegram.ui.Components.EditTextEmoji
        public void hidePopup(boolean z) {
            super.hidePopup(z);
        }

        @Override // org.telegram.ui.Components.EditTextEmoji
        protected void createEmojiView() {
            super.createEmojiView();
            EmojiView emojiView = getEmojiView();
            if (emojiView != null) {
                emojiView.shouldLightenBackground = false;
                emojiView.fixBottomTabContainerTranslation = false;
                emojiView.setShouldDrawBackground(false);
                emojiView.setBottomInset(AndroidUtilities.navigationBarHeight);
            }
            FrameLayout frameLayout = ShareAlert.this.timestampFrameLayout;
            if (frameLayout != null) {
                frameLayout.bringToFront();
            }
            if (ShareAlert.this.frameLayout2 != null) {
                ShareAlert.this.frameLayout2.bringToFront();
            }
            if (ShareAlert.this.writeButtonContainer != null) {
                ShareAlert.this.writeButtonContainer.bringToFront();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ShareAlert$24, reason: invalid class name */
    class AnonymousClass24 implements TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        AnonymousClass24() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.ShareAlert$24$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$afterTextChanged$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$afterTextChanged$0() {
            ShareAlert.this.updateSelectedCount(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$16(View view) {
        sendInternal(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$17(View view) {
        return onSendLongClick(this.writeButton);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$18(View view) {
        this.timestampCheckbox.setChecked(!r3.isChecked(), true);
        updateLinkTextView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPremiumBlockedToast(View view, long j) {
        String userName;
        Bulletin bulletinCreateSimpleBulletin;
        int i = -this.shiftDp;
        this.shiftDp = i;
        AndroidUtilities.shakeViewSpring(view, i);
        BotWebViewVibrationEffect.APP_ERROR.vibrate();
        if (j < 0) {
            userName = _UrlKt.FRAGMENT_ENCODE_SET;
        } else {
            userName = UserObject.getUserName(MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(j)));
        }
        if (MessagesController.getInstance(this.currentAccount).premiumFeaturesBlocked()) {
            bulletinCreateSimpleBulletin = BulletinFactory.of(this.bulletinContainer, this.resourcesProvider).createSimpleBulletin(R.raw.star_premium_2, AndroidUtilities.replaceTags(LocaleController.formatString(R.string.UserBlockedNonPremium, userName)));
        } else {
            bulletinCreateSimpleBulletin = BulletinFactory.of(this.bulletinContainer, this.resourcesProvider).createSimpleBulletin(R.raw.star_premium_2, AndroidUtilities.replaceTags(LocaleController.formatString(R.string.UserBlockedNonPremium, userName)), LocaleController.getString(R.string.UserBlockedNonPremiumButton), new Runnable() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showPremiumBlockedToast$20();
                }
            });
        }
        bulletinCreateSimpleBulletin.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPremiumBlockedToast$20() {
        Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                ShareAlert.$r8$lambda$zIySNlfjdaZnyQ7LG4nDjEIRPKc();
            }
        };
        if (isKeyboardVisible()) {
            FragmentSearchField fragmentSearchField = this.searchView;
            if (fragmentSearchField != null) {
                AndroidUtilities.hideKeyboard(fragmentSearchField.editText);
            }
            AndroidUtilities.runOnUIThread(runnable, 300L);
            return;
        }
        runnable.run();
    }

    public static /* synthetic */ void $r8$lambda$zIySNlfjdaZnyQ7LG4nDjEIRPKc() {
        BaseFragment lastFragment = LaunchActivity.getLastFragment();
        if (lastFragment != null) {
            BaseFragment.BottomSheetParams bottomSheetParams = new BaseFragment.BottomSheetParams();
            bottomSheetParams.transitionFromLeft = true;
            bottomSheetParams.allowNestedScroll = false;
            lastFragment.showAsSheet(new PremiumPreviewFragment("noncontacts"), bottomSheetParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectDialog(View view, final TLRPC.Dialog dialog) {
        DialogsSearchAdapter.CategoryAdapterRecycler categoryAdapterRecycler;
        int i;
        if (dialog instanceof ShareDialogsAdapter.MyStoryDialog) {
            onShareStory(view);
            return;
        }
        if (dialog != null && (((view instanceof ShareDialogCell) && ((ShareDialogCell) view).isBlocked()) || ((view instanceof ProfileSearchCell) && ((ProfileSearchCell) view).isBlocked()))) {
            showPremiumBlockedToast(view, dialog.id);
            return;
        }
        if (this.topicsGridView.getVisibility() != 8 || this.parentActivity == null) {
            return;
        }
        if (DialogObject.isChatDialog(dialog.id)) {
            TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-dialog.id));
            if (ChatObject.isChannel(chat) && !chat.megagroup && (!ChatObject.isCanWriteToChannel(-dialog.id, this.currentAccount) || (i = this.hasPoll) == 2 || i == 3)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this.parentActivity);
                builder.setTitle(LocaleController.getString(R.string.SendMessageTitle));
                int i2 = this.hasPoll;
                if (i2 == 3) {
                    if (ChatObject.isActionBannedByDefault(chat, 10)) {
                        builder.setMessage(LocaleController.getString(R.string.ErrorSendRestrictedTodoAll));
                    } else {
                        builder.setMessage(LocaleController.getString(R.string.ErrorSendRestrictedTodo));
                    }
                } else if (i2 == 2) {
                    if (this.isChannel) {
                        builder.setMessage(LocaleController.getString(R.string.PublicPollCantForward));
                    } else if (ChatObject.isActionBannedByDefault(chat, 10)) {
                        builder.setMessage(LocaleController.getString(R.string.ErrorSendRestrictedPollsAll));
                    } else {
                        builder.setMessage(LocaleController.getString(R.string.ErrorSendRestrictedPolls));
                    }
                } else {
                    builder.setMessage(LocaleController.getString(R.string.ChannelCantSendMessage));
                }
                builder.setNegativeButton(LocaleController.getString(R.string.OK), null);
                builder.show();
                return;
            }
        } else if (DialogObject.isEncryptedDialog(dialog.id) && this.hasPoll != 0) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this.parentActivity);
            builder2.setTitle(LocaleController.getString(R.string.SendMessageTitle));
            int i3 = this.hasPoll;
            if (i3 == 3) {
                builder2.setMessage(LocaleController.getString(R.string.TodoCantForwardSecretChat));
            } else if (i3 != 0) {
                builder2.setMessage(LocaleController.getString(R.string.PollCantForwardSecretChat));
            } else {
                builder2.setMessage(LocaleController.getString(R.string.InvoiceCantForwardSecretChat));
            }
            builder2.setNegativeButton(LocaleController.getString(R.string.OK), null);
            builder2.show();
            return;
        }
        if (this.selectedDialogs.indexOfKey(dialog.id) >= 0) {
            this.selectedDialogs.remove(dialog.id);
            this.selectedDialogTopics.remove(dialog);
            if (view instanceof ProfileSearchCell) {
                ((ProfileSearchCell) view).setChecked(false, true);
            } else if (view instanceof ShareDialogCell) {
                ((ShareDialogCell) view).setChecked(false, true);
            }
            updateSelectedCount(1);
        } else {
            TLRPC.Chat chat2 = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-dialog.id));
            if (DialogObject.isChatDialog(dialog.id) && (ChatObject.isForum(chat2) || (ChatObject.isMonoForum(chat2) && ChatObject.canManageMonoForum(this.currentAccount, chat2)))) {
                this.selectedTopicDialog = dialog;
                this.topicsLayoutManager.scrollToPositionWithOffset(0, this.scrollOffsetY - this.topicsGridView.getPaddingTop());
                final AtomicReference atomicReference = new AtomicReference();
                final AnonymousClass28 anonymousClass28 = new AnonymousClass28(dialog, atomicReference, view);
                atomicReference.set(new Runnable() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda19
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$selectDialog$21(atomicReference, anonymousClass28, dialog);
                    }
                });
                NotificationCenter notificationCenter = NotificationCenter.getInstance(this.currentAccount);
                int i4 = NotificationCenter.topicsDidLoaded;
                notificationCenter.addObserver(anonymousClass28, i4);
                if (MessagesController.getInstance(this.currentAccount).getTopicsController().getTopics(-dialog.id) != null) {
                    anonymousClass28.didReceivedNotification(i4, this.currentAccount, Long.valueOf(-dialog.id));
                    return;
                } else {
                    MessagesController.getInstance(this.currentAccount).getTopicsController().loadTopics(-dialog.id);
                    AndroidUtilities.runOnUIThread((Runnable) atomicReference.get(), 300L);
                    return;
                }
            }
            this.selectedDialogs.put(dialog.id, dialog);
            if (view instanceof ProfileSearchCell) {
                ((ProfileSearchCell) view).setChecked(true, true);
            } else if (view instanceof ShareDialogCell) {
                ((ShareDialogCell) view).setChecked(true, true);
            }
            updateSelectedCount(2);
            long j = UserConfig.getInstance(this.currentAccount).clientUserId;
            if (this.searchIsVisible) {
                TLRPC.Dialog dialog2 = (TLRPC.Dialog) this.listAdapter.dialogsMap.get(dialog.id);
                if (dialog2 == null) {
                    this.listAdapter.dialogsMap.put(dialog.id, dialog);
                    this.listAdapter.dialogs.add(!this.listAdapter.dialogs.isEmpty() ? 1 : 0, dialog);
                } else if (dialog2.id != j) {
                    this.listAdapter.dialogs.remove(dialog2);
                    this.listAdapter.dialogs.add(!this.listAdapter.dialogs.isEmpty() ? 1 : 0, dialog2);
                }
                this.listAdapter.notifyDataSetChanged();
                this.updateSearchAdapter = false;
                this.searchView.editText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
                checkCurrentList(false);
                AndroidUtilities.hideKeyboard(this.searchView.editText);
            }
        }
        ShareSearchAdapter shareSearchAdapter = this.searchAdapter;
        if (shareSearchAdapter == null || (categoryAdapterRecycler = shareSearchAdapter.categoryAdapter) == null) {
            return;
        }
        categoryAdapterRecycler.notifyItemRangeChanged(0, categoryAdapterRecycler.getItemCount());
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ShareAlert$28, reason: invalid class name */
    class AnonymousClass28 implements NotificationCenter.NotificationCenterDelegate {
        final /* synthetic */ View val$cell;
        final /* synthetic */ TLRPC.Dialog val$dialog;
        final /* synthetic */ AtomicReference val$timeoutRef;

        AnonymousClass28(TLRPC.Dialog dialog, AtomicReference atomicReference, View view) {
            this.val$dialog = dialog;
            this.val$timeoutRef = atomicReference;
            this.val$cell = view;
        }

        @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
        public void didReceivedNotification(int i, int i2, Object... objArr) {
            if (((Long) objArr[0]).longValue() == (-this.val$dialog.id)) {
                boolean z = (ShareAlert.this.shareTopicsAdapter.topics == null && MessagesController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).getTopicsController().getTopics(-this.val$dialog.id) != null) || this.val$timeoutRef.get() == null;
                ShareAlert.this.shareTopicsAdapter.topics = MessagesController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).getTopicsController().getTopics(-this.val$dialog.id);
                if (z) {
                    ShareAlert.this.shareTopicsAdapter.notifyDataSetChanged();
                }
                if (ShareAlert.this.shareTopicsAdapter.topics != null) {
                    NotificationCenter.getInstance(((BottomSheet) ShareAlert.this).currentAccount).removeObserver(this, NotificationCenter.topicsDidLoaded);
                }
                if (z) {
                    ShareAlert.this.topicsGridView.setVisibility(0);
                    ShareAlert.this.topicsGridView.setAlpha(0.0f);
                    ShareAlert.this.topicsBackActionBar.setVisibility(0);
                    ShareAlert.this.topicsBackActionBar.setAlpha(0.0f);
                    if (ChatObject.isMonoForum(((BottomSheet) ShareAlert.this).currentAccount, this.val$dialog.id)) {
                        ShareAlert shareAlert = ShareAlert.this;
                        shareAlert.topicsBackActionBar.setTitle(ForumUtilities.getMonoForumTitle(((BottomSheet) shareAlert).currentAccount, MessagesController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).getChat(Long.valueOf(-this.val$dialog.id))));
                        ShareAlert.this.topicsBackActionBar.setSubtitle(LocaleController.getString(R.string.SelectChat));
                    } else {
                        ShareAlert shareAlert2 = ShareAlert.this;
                        shareAlert2.topicsBackActionBar.setTitle(MessagesController.getInstance(((BottomSheet) shareAlert2).currentAccount).getChat(Long.valueOf(-this.val$dialog.id)).title);
                        ShareAlert.this.topicsBackActionBar.setSubtitle(LocaleController.getString(R.string.SelectTopic));
                    }
                    ShareAlert shareAlert3 = ShareAlert.this;
                    shareAlert3.searchWasVisibleBeforeTopics = shareAlert3.searchIsVisible;
                    if (ShareAlert.this.topicsAnimation != null) {
                        ShareAlert.this.topicsAnimation.cancel();
                    }
                    final int[] iArr = new int[2];
                    ShareAlert.this.topicsAnimation = new SpringAnimation(new FloatValueHolder(0.0f)).setSpring(new SpringForce(1000.0f).setStiffness((ShareAlert.this.parentFragment == null || !ShareAlert.this.parentFragment.shareAlertDebugTopicsSlowMotion) ? 800.0f : 10.0f).setDampingRatio(1.0f));
                    SpringAnimation springAnimation = ShareAlert.this.topicsAnimation;
                    final View view = this.val$cell;
                    springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: org.telegram.ui.Components.ShareAlert$28$$ExternalSyntheticLambda0
                        @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
                        public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                            this.f$0.lambda$didReceivedNotification$0(view, iArr, dynamicAnimation, f, f2);
                        }
                    });
                    ShareAlert.this.topicsAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: org.telegram.ui.Components.ShareAlert$28$$ExternalSyntheticLambda1
                        @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                        public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f, float f2) {
                            this.f$0.lambda$didReceivedNotification$1(dynamicAnimation, z2, f, f2);
                        }
                    });
                    ShareAlert.this.topicsAnimation.start();
                    if (this.val$timeoutRef.get() != null) {
                        AndroidUtilities.cancelRunOnUIThread((Runnable) this.val$timeoutRef.get());
                        this.val$timeoutRef.set(null);
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didReceivedNotification$0(View view, int[] iArr, DynamicAnimation dynamicAnimation, float f, float f2) {
            ShareAlert.this.invalidateTopicsAnimation(view, iArr, f / 1000.0f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didReceivedNotification$1(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
            ShareAlert.this.gridView.setVisibility(8);
            ShareAlert.this.searchGridView.setVisibility(8);
            ShareAlert.this.searchView.setVisibility(8);
            ShareAlert.this.topicsAnimation = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$selectDialog$21(AtomicReference atomicReference, NotificationCenter.NotificationCenterDelegate notificationCenterDelegate, TLRPC.Dialog dialog) {
        atomicReference.set(null);
        notificationCenterDelegate.didReceivedNotification(NotificationCenter.topicsDidLoaded, this.currentAccount, Long.valueOf(-dialog.id));
    }

    private void collapseTopics() {
        TLRPC.Dialog dialog = this.selectedTopicDialog;
        if (dialog == null) {
            return;
        }
        final View view = null;
        this.selectedTopicDialog = null;
        for (int i = 0; i < getMainGridView().getChildCount(); i++) {
            View childAt = getMainGridView().getChildAt(i);
            if ((childAt instanceof ShareDialogCell) && ((ShareDialogCell) childAt).getCurrentDialog() == dialog.id) {
                view = childAt;
            }
        }
        if (view == null) {
            return;
        }
        SpringAnimation springAnimation = this.topicsAnimation;
        if (springAnimation != null) {
            springAnimation.cancel();
        }
        getMainGridView().setVisibility(0);
        this.searchView.setVisibility(0);
        if (this.searchIsVisible || this.searchWasVisibleBeforeTopics) {
            this.sizeNotifierFrameLayout.adjustPanLayoutHelper.ignoreOnce();
            this.searchView.editText.requestFocus();
            AndroidUtilities.showKeyboard(this.searchView.editText);
        }
        final int[] iArr = new int[2];
        SpringAnimation springAnimation2 = new SpringAnimation(new FloatValueHolder(1000.0f));
        SpringForce springForce = new SpringForce(0.0f);
        ChatActivity chatActivity = this.parentFragment;
        SpringAnimation spring = springAnimation2.setSpring(springForce.setStiffness((chatActivity == null || !chatActivity.shareAlertDebugTopicsSlowMotion) ? 800.0f : 10.0f).setDampingRatio(1.0f));
        this.topicsAnimation = spring;
        spring.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda0
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                this.f$0.lambda$collapseTopics$22(view, iArr, dynamicAnimation, f, f2);
            }
        });
        this.topicsAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                this.f$0.lambda$collapseTopics$23(dynamicAnimation, z, f, f2);
            }
        });
        this.topicsAnimation.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$collapseTopics$22(View view, int[] iArr, DynamicAnimation dynamicAnimation, float f, float f2) {
        invalidateTopicsAnimation(view, iArr, f / 1000.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$collapseTopics$23(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
        this.topicsGridView.setVisibility(8);
        this.topicsBackActionBar.setVisibility(8);
        this.shareTopicsAdapter.topics = null;
        this.shareTopicsAdapter.notifyDataSetChanged();
        this.topicsAnimation = null;
        this.searchWasVisibleBeforeTopics = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateTopicsAnimation(View view, int[] iArr, float f) {
        this.topicsGridView.setPivotX(view.getX() + (view.getWidth() / 2.0f));
        this.topicsGridView.setPivotY(view.getY() + (view.getHeight() / 2.0f));
        float f2 = 0.25f * f;
        float f3 = 0.75f + f2;
        this.topicsGridView.setScaleX(f3);
        this.topicsGridView.setScaleY(f3);
        this.topicsGridView.setAlpha(f);
        RecyclerListView mainGridView = getMainGridView();
        mainGridView.setPivotX(view.getX() + (view.getWidth() / 2.0f));
        mainGridView.setPivotY(view.getY() + (view.getHeight() / 2.0f));
        float f4 = f2 + 1.0f;
        mainGridView.setScaleX(f4);
        mainGridView.setScaleY(f4);
        float f5 = 1.0f - f;
        mainGridView.setAlpha(f5);
        this.searchView.setPivotX(r4.getWidth() / 2.0f);
        this.searchView.setPivotY(0.0f);
        float f6 = (0.1f * f5) + 0.9f;
        this.searchView.setScaleX(f6);
        this.searchView.setScaleY(f6);
        this.searchView.setAlpha(f5);
        this.topicsBackActionBar.getBackButton().setTranslationX((-AndroidUtilities.dp(16.0f)) * f5);
        this.topicsBackActionBar.getTitleTextView().setTranslationY(AndroidUtilities.dp(16.0f) * f5);
        this.topicsBackActionBar.getSubtitleTextView().setTranslationY(AndroidUtilities.dp(16.0f) * f5);
        this.topicsBackActionBar.setAlpha(f);
        this.topicsGridView.getLocationInWindow(iArr);
        float interpolation = CubicBezierInterpolator.EASE_OUT.getInterpolation(f);
        for (int i = 0; i < mainGridView.getChildCount(); i++) {
            View childAt = mainGridView.getChildAt(i);
            if (childAt instanceof ShareDialogCell) {
                childAt.setTranslationX((childAt.getX() - view.getX()) * 0.5f * interpolation);
                childAt.setTranslationY((childAt.getY() - view.getY()) * 0.5f * interpolation);
                if (childAt != view) {
                    childAt.setAlpha(1.0f - (Math.min(f, 0.5f) / 0.5f));
                } else {
                    childAt.setAlpha(f5);
                }
            }
        }
        for (int i2 = 0; i2 < this.topicsGridView.getChildCount(); i2++) {
            View childAt2 = this.topicsGridView.getChildAt(i2);
            if (childAt2 instanceof ShareTopicCell) {
                double d = 1.0f - interpolation;
                childAt2.setTranslationX((float) (((double) (-(childAt2.getX() - view.getX()))) * Math.pow(d, 2.0d)));
                childAt2.setTranslationY((float) (((double) (-((childAt2.getY() + this.topicsGridView.getTranslationY()) - view.getY()))) * Math.pow(d, 2.0d)));
            }
        }
        this.containerView.requestLayout();
        mainGridView.invalidate();
    }

    @Override // org.telegram.ui.ActionBar.BottomSheet
    public int getContainerViewHeight() {
        return this.containerView.getMeasuredHeight() - this.containerViewTop;
    }

    private boolean onSendLongClick(View view) {
        int measuredHeight;
        ChatActivity chatActivity;
        final MessagePreviewView.ToggleButton toggleButton;
        long j;
        long j2;
        if (this.parentActivity == null) {
            return false;
        }
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        if (this.sendingMessageObjects != null) {
            ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout = new ActionBarPopupWindow.ActionBarPopupWindowLayout(this.parentActivity, R.drawable.popup_fixed_alert3, this.resourcesProvider) { // from class: org.telegram.ui.Components.ShareAlert.29
                final Path path = new Path();

                @Override // android.view.ViewGroup
                protected boolean drawChild(Canvas canvas, View view2, long j3) {
                    canvas.save();
                    this.path.rewind();
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
                    this.path.addRoundRect(rectF, AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), Path.Direction.CW);
                    canvas.clipPath(this.path);
                    boolean zDrawChild = super.drawChild(canvas, view2, j3);
                    canvas.restore();
                    return zDrawChild;
                }
            };
            if (this.darkTheme) {
                actionBarPopupWindowLayout.setBackgroundColor(getThemedColor(Theme.key_voipgroup_inviteMembersBackground));
            }
            actionBarPopupWindowLayout.setAnimationEnabled(false);
            actionBarPopupWindowLayout.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.ShareAlert.30
                private android.graphics.Rect popupRect = new android.graphics.Rect();

                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view2, MotionEvent motionEvent) {
                    if (motionEvent.getActionMasked() != 0 || ShareAlert.this.sendPopupWindow == null || !ShareAlert.this.sendPopupWindow.isShowing()) {
                        return false;
                    }
                    view2.getHitRect(this.popupRect);
                    if (this.popupRect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                        return false;
                    }
                    ShareAlert.this.sendPopupWindow.dismiss();
                    return false;
                }
            });
            actionBarPopupWindowLayout.setDispatchKeyEventListener(new ActionBarPopupWindow.OnDispatchKeyEventListener() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda20
                @Override // org.telegram.ui.ActionBar.ActionBarPopupWindow.OnDispatchKeyEventListener
                public final void onDispatchKeyEvent(KeyEvent keyEvent) {
                    this.f$0.lambda$onSendLongClick$24(keyEvent);
                }
            });
            actionBarPopupWindowLayout.setShownFromBottom(false);
            actionBarPopupWindowLayout.setupRadialSelectors(getThemedColor(Theme.key_dialogButtonSelector));
            ArrayList arrayList = new ArrayList();
            boolean z = false;
            for (int i = 0; i < this.sendingMessageObjects.size(); i++) {
                MessageObject messageObject = (MessageObject) this.sendingMessageObjects.get(i);
                if (!TextUtils.isEmpty(messageObject.caption)) {
                    z = true;
                }
                TLRPC.MessageFwdHeader messageFwdHeader = messageObject.messageOwner.fwd_from;
                if (messageFwdHeader != null && messageFwdHeader.from_id == null && !arrayList.contains(messageFwdHeader.from_name)) {
                    arrayList.add(messageFwdHeader.from_name);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < this.sendingMessageObjects.size(); i2++) {
                MessageObject messageObject2 = (MessageObject) this.sendingMessageObjects.get(i2);
                if (messageObject2.isFromUser()) {
                    j2 = messageObject2.messageOwner.from_id.user_id;
                } else {
                    TLRPC.Chat chat = MessagesController.getInstance(messageObject2.currentAccount).getChat(Long.valueOf(messageObject2.messageOwner.peer_id.channel_id));
                    if (ChatObject.isChannel(chat) && chat.megagroup && messageObject2.isForwardedChannelPost()) {
                        j = messageObject2.messageOwner.fwd_from.from_id.channel_id;
                    } else {
                        j = messageObject2.messageOwner.peer_id.channel_id;
                    }
                    j2 = -j;
                }
                if (!arrayList2.contains(Long.valueOf(j2))) {
                    arrayList2.add(Long.valueOf(j2));
                }
            }
            boolean z2 = arrayList2.size() + arrayList.size() > 1;
            final MessagePreviewView.ToggleButton toggleButton2 = new MessagePreviewView.ToggleButton(this.parentActivity, R.raw.name_hide, LocaleController.getString(z2 ? R.string.ShowSenderNames : R.string.ShowSendersName), R.raw.name_show, LocaleController.getString(z2 ? R.string.HideSenderNames : R.string.HideSendersName), this.resourcesProvider);
            actionBarPopupWindowLayout.addView((View) toggleButton2, LayoutHelper.createLinear(-1, 48));
            if (z) {
                toggleButton = new MessagePreviewView.ToggleButton(this.parentActivity, R.raw.caption_hide, LocaleController.getString(R.string.ShowCaption), R.raw.caption_show, LocaleController.getString(R.string.HideCaption), this.resourcesProvider);
                toggleButton.setState(this.hideCaption, false);
                actionBarPopupWindowLayout.addView((View) toggleButton, LayoutHelper.createLinear(-1, 48));
            } else {
                toggleButton = null;
            }
            toggleButton2.setState(this.hideSendersName, false);
            toggleButton2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda21
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$onSendLongClick$25(toggleButton, toggleButton2, view2);
                }
            });
            if (toggleButton != null) {
                toggleButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda22
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        this.f$0.lambda$onSendLongClick$26(toggleButton, toggleButton2, view2);
                    }
                });
            }
            linearLayout.addView(actionBarPopupWindowLayout, LayoutHelper.createLinear(-1, -2, 0.0f, 0.0f, 0.0f, -8.0f));
        }
        ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout2 = new ActionBarPopupWindow.ActionBarPopupWindowLayout(this.parentActivity, this.resourcesProvider);
        if (this.darkTheme) {
            actionBarPopupWindowLayout2.setBackgroundColor(Theme.getColor(Theme.key_voipgroup_inviteMembersBackground));
        }
        actionBarPopupWindowLayout2.setAnimationEnabled(false);
        actionBarPopupWindowLayout2.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.ShareAlert.31
            private android.graphics.Rect popupRect = new android.graphics.Rect();

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() != 0 || ShareAlert.this.sendPopupWindow == null || !ShareAlert.this.sendPopupWindow.isShowing()) {
                    return false;
                }
                view2.getHitRect(this.popupRect);
                if (this.popupRect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    return false;
                }
                ShareAlert.this.sendPopupWindow.dismiss();
                return false;
            }
        });
        actionBarPopupWindowLayout2.setDispatchKeyEventListener(new ActionBarPopupWindow.OnDispatchKeyEventListener() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda23
            @Override // org.telegram.ui.ActionBar.ActionBarPopupWindow.OnDispatchKeyEventListener
            public final void onDispatchKeyEvent(KeyEvent keyEvent) {
                this.f$0.lambda$onSendLongClick$27(keyEvent);
            }
        });
        actionBarPopupWindowLayout2.setShownFromBottom(false);
        if (this.commentTextView.getText() != null && this.commentTextView.getText().toString().trim().length() != 0) {
            actionBarPopupWindowLayout2.addView((View) new TranslateBeforeSendWrapper(getContext(), true, true, this.resourcesProvider) { // from class: org.telegram.ui.Components.ShareAlert.32
                @Override // com.exteragram.messenger.components.TranslateBeforeSendWrapper
                protected void onClick() {
                    if (ShareAlert.this.sendPopupWindow != null && ShareAlert.this.sendPopupWindow.isShowing()) {
                        ShareAlert.this.sendPopupWindow.dismiss();
                    }
                    final AlertDialog alertDialog = new AlertDialog(getContext(), 3, this.resourcesProvider);
                    alertDialog.showDelayed(150L);
                    CharSequence[] charSequenceArr = {ShareAlert.this.commentTextView.getText()};
                    TranslatorUtils.translate(charSequenceArr[0], MediaDataController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).getEntities(charSequenceArr, true), new TranslatorUtils.TranslateCallback() { // from class: org.telegram.ui.Components.ShareAlert.32.1
                        @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                        public /* synthetic */ void onReqId(int i3) {
                            TranslatorUtils.TranslateCallback.CC.$default$onReqId(this, i3);
                        }

                        @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                        public /* synthetic */ void onSuccess(String str) {
                            TranslatorUtils.TranslateCallback.CC.$default$onSuccess(this, str);
                        }

                        @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                        public /* synthetic */ void onSuccess(TLObject tLObject, TLRPC.TL_error tL_error) {
                            TranslatorUtils.TranslateCallback.CC.$default$onSuccess(this, tLObject, tL_error);
                        }

                        @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                        public void onSuccess(TLRPC.TL_textWithEntities tL_textWithEntities) {
                            try {
                                alertDialog.dismiss();
                            } catch (Exception unused) {
                            }
                            SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(tL_textWithEntities.text);
                            MessageObject.addEntitiesToText(spannableStringBuilderValueOf, tL_textWithEntities.entities, true, true, false, true);
                            ShareAlert.this.commentTextView.setText(spannableStringBuilderValueOf);
                            ShareAlert.this.commentTextView.setSelection(spannableStringBuilderValueOf.length());
                            if (ShareAlert.this.sendPopupWindow == null || !ShareAlert.this.sendPopupWindow.isShowing()) {
                                return;
                            }
                            ShareAlert.this.sendPopupWindow.dismiss();
                        }

                        @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                        public void onFailed() {
                            try {
                                alertDialog.dismiss();
                            } catch (Exception unused) {
                            }
                            BulletinFactory.of(ShareAlert.this.bulletinContainer, ((ActionBarMenuSubItem) AnonymousClass32.this).resourcesProvider).createErrorBulletin(LocaleController.getString(R.string.TranslationFailedAlert2)).show();
                        }
                    });
                }
            }, LayoutHelper.createLinear(-1, 48));
        }
        ActionBarMenuSubItem actionBarMenuSubItem = new ActionBarMenuSubItem(getContext(), true, true, this.resourcesProvider);
        if (this.darkTheme) {
            actionBarMenuSubItem.setTextColor(getThemedColor(Theme.key_voipgroup_nameText));
            actionBarMenuSubItem.setIconColor(getThemedColor(Theme.key_windowBackgroundWhiteHintText));
        }
        actionBarMenuSubItem.setTextAndIcon(LocaleController.getString(R.string.SendWithoutSound), R.drawable.input_notify_off);
        actionBarMenuSubItem.setMinimumWidth(AndroidUtilities.dp(196.0f));
        actionBarPopupWindowLayout2.addView((View) actionBarMenuSubItem, LayoutHelper.createLinear(-1, 48));
        actionBarMenuSubItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda24
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$onSendLongClick$28(view2);
            }
        });
        ActionBarMenuSubItem actionBarMenuSubItem2 = new ActionBarMenuSubItem(getContext(), true, true, this.resourcesProvider);
        if (this.darkTheme) {
            actionBarMenuSubItem2.setTextColor(getThemedColor(Theme.key_voipgroup_nameText));
            actionBarMenuSubItem2.setIconColor(getThemedColor(Theme.key_windowBackgroundWhiteHintText));
        }
        actionBarMenuSubItem2.setTextAndIcon(LocaleController.getString(R.string.ForwardSendMessages), R.drawable.msg_send);
        actionBarPopupWindowLayout2.addView((View) actionBarMenuSubItem2, LayoutHelper.createLinear(-1, 48));
        actionBarMenuSubItem2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda25
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$onSendLongClick$29(view2);
            }
        });
        actionBarPopupWindowLayout2.setupRadialSelectors(getThemedColor(Theme.key_dialogButtonSelector));
        linearLayout.addView(actionBarPopupWindowLayout2, LayoutHelper.createLinear(-1, -2));
        ActionBarPopupWindow actionBarPopupWindow = new ActionBarPopupWindow(linearLayout, -2, -2);
        this.sendPopupWindow = actionBarPopupWindow;
        actionBarPopupWindow.setAnimationEnabled(false);
        this.sendPopupWindow.setAnimationStyle(R.style.PopupContextAnimation2);
        this.sendPopupWindow.setOutsideTouchable(true);
        this.sendPopupWindow.setClippingEnabled(true);
        this.sendPopupWindow.setInputMethodMode(2);
        this.sendPopupWindow.setSoftInputMode(0);
        this.sendPopupWindow.getContentView().setFocusableInTouchMode(true);
        SharedConfig.removeScheduledOrNoSoundHint();
        linearLayout.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), Integer.MIN_VALUE));
        this.sendPopupWindow.setFocusable(true);
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        if (this.keyboardVisible && (chatActivity = this.parentFragment) != null && chatActivity.contentView.getMeasuredHeight() > AndroidUtilities.dp(58.0f)) {
            measuredHeight = iArr[1] + view.getMeasuredHeight();
        } else {
            measuredHeight = (iArr[1] - linearLayout.getMeasuredHeight()) - AndroidUtilities.dp(2.0f);
        }
        this.sendPopupWindow.showAtLocation(view, 51, ((iArr[0] + view.getMeasuredWidth()) - linearLayout.getMeasuredWidth()) + AndroidUtilities.dp(8.0f), measuredHeight);
        this.sendPopupWindow.dimBehind();
        try {
            view.performHapticFeedback(3, 2);
        } catch (Exception unused) {
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSendLongClick$24(KeyEvent keyEvent) {
        ActionBarPopupWindow actionBarPopupWindow;
        if (keyEvent.getKeyCode() == 4 && keyEvent.getRepeatCount() == 0 && (actionBarPopupWindow = this.sendPopupWindow) != null && actionBarPopupWindow.isShowing()) {
            this.sendPopupWindow.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSendLongClick$25(MessagePreviewView.ToggleButton toggleButton, MessagePreviewView.ToggleButton toggleButton2, View view) {
        boolean z = this.hideSendersName;
        this.hideSendersName = !z;
        if (z) {
            this.hideCaption = false;
            if (toggleButton != null) {
                toggleButton.setState(false, true);
            }
        }
        toggleButton2.setState(this.hideSendersName, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSendLongClick$26(MessagePreviewView.ToggleButton toggleButton, MessagePreviewView.ToggleButton toggleButton2, View view) {
        boolean z = this.hideCaption;
        boolean z2 = !z;
        this.hideCaption = z2;
        if (!z) {
            if (!this.hideSendersName) {
                this.hideSendersName = true;
            }
        } else {
            this.hideSendersName = false;
        }
        toggleButton.setState(z2, true);
        toggleButton2.setState(this.hideSendersName, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSendLongClick$27(KeyEvent keyEvent) {
        ActionBarPopupWindow actionBarPopupWindow;
        if (keyEvent.getKeyCode() == 4 && keyEvent.getRepeatCount() == 0 && (actionBarPopupWindow = this.sendPopupWindow) != null && actionBarPopupWindow.isShowing()) {
            this.sendPopupWindow.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSendLongClick$28(View view) {
        ActionBarPopupWindow actionBarPopupWindow = this.sendPopupWindow;
        if (actionBarPopupWindow != null && actionBarPopupWindow.isShowing()) {
            this.sendPopupWindow.dismiss();
        }
        sendInternal(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSendLongClick$29(View view) {
        ActionBarPopupWindow actionBarPopupWindow = this.sendPopupWindow;
        if (actionBarPopupWindow != null && actionBarPopupWindow.isShowing()) {
            this.sendPopupWindow.dismiss();
        }
        sendInternal(true);
    }

    protected void sendInternal(final boolean z) {
        int i;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= this.selectedDialogs.size()) {
                final CharSequence[] charSequenceArr = {this.commentTextView.getText()};
                final ArrayList<TLRPC.MessageEntity> entities = MediaDataController.getInstance(this.currentAccount).getEntities(charSequenceArr, true);
                CheckBox2 checkBox2 = this.timestampCheckbox;
                final int i4 = (checkBox2 == null || !checkBox2.isChecked()) ? -1 : this.timestamp;
                ArrayList arrayList = new ArrayList();
                if (this.sendingMessageObjects != null) {
                    i = 0;
                    while (i2 < this.selectedDialogs.size()) {
                        long jKeyAt = this.selectedDialogs.keyAt(i2);
                        long sendPaidMessagesStars = MessagesController.getInstance(this.currentAccount).getSendPaidMessagesStars(jKeyAt);
                        if (sendPaidMessagesStars <= 0) {
                            sendPaidMessagesStars = DialogObject.getMessagesStarsPrice(MessagesController.getInstance(this.currentAccount).isUserContactBlocked(jKeyAt));
                        }
                        if (this.frameLayout2.getTag() != null && this.commentTextView.length() > 0 && sendPaidMessagesStars > 0) {
                            i++;
                        }
                        if (sendPaidMessagesStars > 0) {
                            i++;
                        }
                        if (sendPaidMessagesStars > 0 && !arrayList.contains(Long.valueOf(jKeyAt))) {
                            arrayList.add(Long.valueOf(jKeyAt));
                        }
                        i2++;
                    }
                } else {
                    SwitchView switchView = this.switchView;
                    int i5 = switchView != null ? switchView.currentTab : 0;
                    if (this.storyItem != null) {
                        int i6 = 0;
                        for (int i7 = 0; i7 < this.selectedDialogs.size(); i7++) {
                            long jKeyAt2 = this.selectedDialogs.keyAt(i7);
                            long sendPaidMessagesStars2 = MessagesController.getInstance(this.currentAccount).getSendPaidMessagesStars(jKeyAt2);
                            if (sendPaidMessagesStars2 <= 0) {
                                sendPaidMessagesStars2 = DialogObject.getMessagesStarsPrice(MessagesController.getInstance(this.currentAccount).isUserContactBlocked(jKeyAt2));
                            }
                            if (this.storyItem != null && this.frameLayout2.getTag() != null && this.commentTextView.length() > 0 && charSequenceArr[0] != null && sendPaidMessagesStars2 > 0) {
                                i6++;
                            }
                            if (sendPaidMessagesStars2 > 0) {
                                i6++;
                            }
                            if (sendPaidMessagesStars2 > 0 && !arrayList.contains(Long.valueOf(jKeyAt2))) {
                                arrayList.add(Long.valueOf(jKeyAt2));
                            }
                        }
                        i2 = i6;
                    } else if (this.sendingText[i5] != null) {
                        i = 0;
                        while (i2 < this.selectedDialogs.size()) {
                            long jKeyAt3 = this.selectedDialogs.keyAt(i2);
                            long sendPaidMessagesStars3 = MessagesController.getInstance(this.currentAccount).getSendPaidMessagesStars(jKeyAt3);
                            if (sendPaidMessagesStars3 <= 0) {
                                sendPaidMessagesStars3 = DialogObject.getMessagesStarsPrice(MessagesController.getInstance(this.currentAccount).isUserContactBlocked(jKeyAt3));
                            }
                            if (this.frameLayout2.getTag() != null && this.commentTextView.length() > 0 && sendPaidMessagesStars3 > 0) {
                                i++;
                            }
                            if (sendPaidMessagesStars3 > 0) {
                                i++;
                            }
                            if (sendPaidMessagesStars3 > 0 && !arrayList.contains(Long.valueOf(jKeyAt3))) {
                                arrayList.add(Long.valueOf(jKeyAt3));
                            }
                            i2++;
                        }
                    }
                    AlertsCreator.ensurePaidMessagesMultiConfirmation(this.currentAccount, arrayList, i2, new Utilities.Callback() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda26
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            this.f$0.lambda$sendInternal$30(charSequenceArr, entities, z, i4, (HashMap) obj);
                        }
                    });
                    return;
                }
                i2 = i;
                AlertsCreator.ensurePaidMessagesMultiConfirmation(this.currentAccount, arrayList, i2, new Utilities.Callback() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda26
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$sendInternal$30(charSequenceArr, entities, z, i4, (HashMap) obj);
                    }
                });
                return;
            }
            if (AlertsCreator.checkSlowMode(getContext(), this.currentAccount, this.selectedDialogs.keyAt(i3), this.frameLayout2.getTag() != null && this.commentTextView.length() > 0)) {
                return;
            } else {
                i3++;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v21, types: [boolean] */
    /* JADX WARN: Type inference failed for: r8v27 */
    public /* synthetic */ void lambda$sendInternal$30(CharSequence[] charSequenceArr, ArrayList arrayList, boolean z, int i, HashMap map) {
        int i2;
        int i3;
        char c;
        MessageObject messageObject;
        Long l;
        char c2;
        MessageObject messageObject2;
        int i4;
        Long l2;
        MessageObject messageObject3;
        long jLongValue;
        Long l3;
        long j;
        SendMessagesHelper.SendMessageParams sendMessageParamsOf;
        long j2;
        int i5;
        long jLongValue2;
        long peerDialogId = 0;
        int i6 = 1;
        ?? r8 = 0;
        if (this.sendingMessageObjects != null) {
            ArrayList arrayList2 = new ArrayList();
            int i7 = 0;
            boolean z2 = false;
            while (i7 < this.selectedDialogs.size()) {
                long jKeyAt = this.selectedDialogs.keyAt(i7);
                boolean zIsMonoForum = MessagesController.getInstance(this.currentAccount).isMonoForum(jKeyAt);
                Long l4 = map == null ? 0L : (Long) map.get(Long.valueOf(jKeyAt));
                if (l4 != null && l4.longValue() > peerDialogId) {
                    z2 = true;
                }
                TLRPC.TL_forumTopic tL_forumTopic = (TLRPC.TL_forumTopic) this.selectedDialogTopics.get(this.selectedDialogs.get(jKeyAt));
                if (tL_forumTopic == null || !zIsMonoForum) {
                    j2 = peerDialogId;
                } else {
                    j2 = peerDialogId;
                    peerDialogId = DialogObject.getPeerDialogId(tL_forumTopic.from_id);
                }
                MessageObject messageObject4 = (tL_forumTopic == null || zIsMonoForum) ? null : new MessageObject(this.currentAccount, tL_forumTopic.topicStartMessage, r8, r8);
                if (messageObject4 != null) {
                    messageObject4.isTopicMainMessage = true;
                }
                if (this.frameLayout2.getTag() == null || this.commentTextView.length() <= 0) {
                    i5 = i7;
                } else {
                    CharSequence charSequence = charSequenceArr[r8];
                    SendMessagesHelper.SendMessageParams sendMessageParamsOf2 = SendMessagesHelper.SendMessageParams.of(charSequence == null ? null : charSequence.toString(), jKeyAt, messageObject4, messageObject4, null, true, arrayList, null, null, z, 0, 0, null, false);
                    if (l4 == null) {
                        i5 = i7;
                        jLongValue2 = j2;
                    } else {
                        i5 = i7;
                        jLongValue2 = l4.longValue();
                    }
                    sendMessageParamsOf2.payStars = jLongValue2;
                    sendMessageParamsOf2.monoForumPeer = peerDialogId;
                    SendMessagesHelper.getInstance(this.currentAccount).sendMessage(sendMessageParamsOf2);
                }
                int iSendMessage = SendMessagesHelper.getInstance(this.currentAccount).sendMessage(this.sendingMessageObjects, jKeyAt, this.hideSendersName, this.hideCaption, z, 0, 0, messageObject4, i, l4 == null ? j2 : l4.longValue(), peerDialogId, null);
                if (iSendMessage != 0) {
                    arrayList2.add(Long.valueOf(jKeyAt));
                }
                if (this.selectedDialogs.size() == 1) {
                    AlertsCreator.showSendMediaAlert(iSendMessage, this.parentFragment, null);
                    if (iSendMessage != 0) {
                        break;
                    }
                }
                i7 = i5 + 1;
                peerDialogId = j2;
                r8 = 0;
            }
            int size = arrayList2.size();
            int i8 = 0;
            while (i8 < size) {
                Object obj = arrayList2.get(i8);
                i8++;
                long jLongValue3 = ((Long) obj).longValue();
                TLRPC.Dialog dialog = (TLRPC.Dialog) this.selectedDialogs.get(jLongValue3);
                this.selectedDialogs.remove(jLongValue3);
                if (dialog != null) {
                    this.selectedDialogTopics.remove(dialog);
                }
            }
            if (!this.selectedDialogs.isEmpty()) {
                onSend(this.selectedDialogs, this.sendingMessageObjects.size(), this.selectedDialogs.size() == 1 ? (TLRPC.TL_forumTopic) this.selectedDialogTopics.get(this.selectedDialogs.valueAt(0)) : null, !z2);
            }
        } else {
            SwitchView switchView = this.switchView;
            int i9 = switchView != null ? switchView.currentTab : 0;
            if (this.storyItem != null) {
                i3 = 0;
                for (int i10 = 0; i10 < this.selectedDialogs.size(); i10++) {
                    long jKeyAt2 = this.selectedDialogs.keyAt(i10);
                    boolean zIsMonoForum2 = MessagesController.getInstance(this.currentAccount).isMonoForum(jKeyAt2);
                    if (map == null) {
                        l3 = 0L;
                    } else {
                        l3 = (Long) map.get(Long.valueOf(jKeyAt2));
                    }
                    if (l3 != null && l3.longValue() > 0) {
                        i3 = 1;
                    }
                    TLRPC.TL_forumTopic tL_forumTopic2 = (TLRPC.TL_forumTopic) this.selectedDialogTopics.get(this.selectedDialogs.get(jKeyAt2));
                    long peerDialogId2 = (tL_forumTopic2 == null || !zIsMonoForum2) ? 0L : DialogObject.getPeerDialogId(tL_forumTopic2.from_id);
                    MessageObject messageObject5 = (tL_forumTopic2 == null || zIsMonoForum2) ? null : new MessageObject(this.currentAccount, tL_forumTopic2.topicStartMessage, false, false);
                    if (this.storyItem == null) {
                        if (this.frameLayout2.getTag() != null && this.commentTextView.length() > 0) {
                            CharSequence charSequence2 = charSequenceArr[0];
                            sendMessageParamsOf = SendMessagesHelper.SendMessageParams.of(charSequence2 == null ? null : charSequence2.toString(), jKeyAt2, messageObject5, messageObject5, null, true, arrayList, null, null, z, 0, 0, null, false);
                            j = peerDialogId2;
                        } else {
                            j = peerDialogId2;
                            sendMessageParamsOf = SendMessagesHelper.SendMessageParams.of(this.sendingText[i9], jKeyAt2, messageObject5, messageObject5, null, true, null, null, null, z, 0, 0, null, false);
                        }
                    } else {
                        j = peerDialogId2;
                        if (this.frameLayout2.getTag() != null && this.commentTextView.length() > 0 && charSequenceArr[0] != null) {
                            MessageObject messageObject6 = messageObject5;
                            messageObject5 = messageObject6;
                            SendMessagesHelper.getInstance(this.currentAccount).sendMessage(SendMessagesHelper.SendMessageParams.of(charSequenceArr[0].toString(), jKeyAt2, null, messageObject6, null, true, null, null, null, z, 0, 0, null, false));
                        }
                        sendMessageParamsOf = SendMessagesHelper.SendMessageParams.of(null, jKeyAt2, messageObject5, messageObject5, null, true, null, null, null, z, 0, 0, null, false);
                        sendMessageParamsOf.sendingStory = this.storyItem;
                    }
                    sendMessageParamsOf.payStars = l3 == null ? 0L : l3.longValue();
                    sendMessageParamsOf.monoForumPeer = j;
                    SendMessagesHelper.getInstance(this.currentAccount).sendMessage(sendMessageParamsOf);
                }
            } else if (this.sendingText[i9] != null) {
                int i11 = 0;
                i3 = 0;
                while (i11 < this.selectedDialogs.size()) {
                    long jKeyAt3 = this.selectedDialogs.keyAt(i11);
                    boolean zIsMonoForum3 = MessagesController.getInstance(this.currentAccount).isMonoForum(jKeyAt3);
                    if (map == null) {
                        l = 0L;
                    } else {
                        l = (Long) map.get(Long.valueOf(jKeyAt3));
                    }
                    if (l != null && l.longValue() > 0) {
                        i3 = i6;
                    }
                    TLRPC.TL_forumTopic tL_forumTopic3 = (TLRPC.TL_forumTopic) this.selectedDialogTopics.get(this.selectedDialogs.get(jKeyAt3));
                    long peerDialogId3 = (tL_forumTopic3 == null || !zIsMonoForum3) ? 0L : DialogObject.getPeerDialogId(tL_forumTopic3.from_id);
                    if (tL_forumTopic3 == null || zIsMonoForum3) {
                        c2 = 0;
                        messageObject2 = null;
                    } else {
                        c2 = 0;
                        messageObject2 = new MessageObject(this.currentAccount, tL_forumTopic3.topicStartMessage, false, false);
                    }
                    if (this.frameLayout2.getTag() == null || this.commentTextView.length() <= 0) {
                        i4 = i6;
                        l2 = l;
                        messageObject3 = messageObject2;
                    } else {
                        CharSequence charSequence3 = charSequenceArr[c2];
                        SendMessagesHelper.SendMessageParams sendMessageParamsOf3 = SendMessagesHelper.SendMessageParams.of(charSequence3 == null ? null : charSequence3.toString(), jKeyAt3, messageObject2, messageObject2, null, true, arrayList, null, null, z, 0, 0, null, false);
                        messageObject3 = messageObject2;
                        if (l == null) {
                            i4 = i6;
                            l2 = l;
                            jLongValue = 0;
                        } else {
                            i4 = i6;
                            l2 = l;
                            jLongValue = l.longValue();
                        }
                        sendMessageParamsOf3.payStars = jLongValue;
                        sendMessageParamsOf3.monoForumPeer = peerDialogId3;
                        SendMessagesHelper.getInstance(this.currentAccount).sendMessage(sendMessageParamsOf3);
                    }
                    SendMessagesHelper.SendMessageParams sendMessageParamsOf4 = SendMessagesHelper.SendMessageParams.of(this.sendingText[i9], jKeyAt3, messageObject3, messageObject3, null, true, null, null, null, z, 0, 0, null, false);
                    sendMessageParamsOf4.payStars = l2 == null ? 0L : l2.longValue();
                    sendMessageParamsOf4.monoForumPeer = peerDialogId3;
                    SendMessagesHelper.getInstance(this.currentAccount).sendMessage(sendMessageParamsOf4);
                    i11++;
                    i6 = i4;
                }
            } else {
                i2 = 1;
                if (this.sendingFile != null) {
                    for (int i12 = 0; i12 < this.selectedDialogs.size(); i12++) {
                        long jKeyAt4 = this.selectedDialogs.keyAt(i12);
                        TLRPC.TL_forumTopic tL_forumTopic4 = (TLRPC.TL_forumTopic) this.selectedDialogTopics.get(this.selectedDialogs.get(jKeyAt4));
                        if (tL_forumTopic4 != null) {
                            c = 0;
                            messageObject = new MessageObject(this.currentAccount, tL_forumTopic4.topicStartMessage, false, false);
                        } else {
                            c = 0;
                            messageObject = null;
                        }
                        if (this.frameLayout2.getTag() != null && this.commentTextView.length() > 0 && charSequenceArr[c] != null) {
                            File file = new File(this.sendingFile);
                            String str = file.getParent() + "/" + PreferencesUtils.generateBackupName(charSequenceArr[c].toString());
                            this.sendingFile = str;
                            file.renameTo(new File(str));
                        }
                        ArrayList arrayList3 = new ArrayList(ImageCapture$$ExternalSyntheticBackport1.m(new Object[]{this.sendingFile}));
                        SendMessagesHelper.prepareSendingDocuments(AccountInstance.getInstance(this.currentAccount), arrayList3, arrayList3, null, null, null, jKeyAt4, null, messageObject, null, null, null, z, 0, null, null, 0, 0L, false, 0L);
                    }
                }
                i3 = 0;
                LongSparseArray longSparseArray = this.selectedDialogs;
                onSend(longSparseArray, i2, (TLRPC.TL_forumTopic) this.selectedDialogTopics.get(longSparseArray.valueAt(0)), i3 ^ 1);
            }
            i2 = i6;
            LongSparseArray longSparseArray2 = this.selectedDialogs;
            onSend(longSparseArray2, i2, (TLRPC.TL_forumTopic) this.selectedDialogTopics.get(longSparseArray2.valueAt(0)), i3 ^ 1);
        }
        ShareAlertDelegate shareAlertDelegate = this.delegate;
        if (shareAlertDelegate != null) {
            shareAlertDelegate.didShare();
        }
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCurrentTop() {
        if (this.gridView.getChildCount() == 0) {
            return -1000;
        }
        int top = 0;
        View childAt = this.gridView.getChildAt(0);
        RecyclerListView.Holder holder = (RecyclerListView.Holder) this.gridView.findContainingViewHolder(childAt);
        if (holder == null) {
            return -1000;
        }
        int paddingTop = this.gridView.getPaddingTop();
        if (holder.getLayoutPosition() == 0 && childAt.getTop() >= 0) {
            top = childAt.getTop();
        }
        return paddingTop - top;
    }

    private RecyclerListView getMainGridView() {
        return (this.searchIsVisible || this.searchWasVisibleBeforeTopics) ? this.searchGridView : this.gridView;
    }

    public void setDelegate(ShareAlertDelegate shareAlertDelegate) {
        this.delegate = shareAlertDelegate;
    }

    @Override // org.telegram.ui.ActionBar.BottomSheet
    public void dismissInternal() {
        super.dismissInternal();
        EditTextEmoji editTextEmoji = this.commentTextView;
        if (editTextEmoji != null) {
            editTextEmoji.onDestroy();
        }
    }

    @Override // org.telegram.ui.ActionBar.BottomSheet, android.app.Dialog
    /* JADX INFO: renamed from: onBackPressed */
    public void lambda$openCrafting$9() {
        if (this.selectedTopicDialog != null) {
            collapseTopics();
            return;
        }
        EditTextEmoji editTextEmoji = this.commentTextView;
        if (editTextEmoji != null && editTextEmoji.isPopupShowing()) {
            this.commentTextView.hidePopup(true);
        } else {
            super.lambda$openCrafting$9();
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        int i3 = NotificationCenter.dialogsNeedReload;
        if (i == i3) {
            ShareDialogsAdapter shareDialogsAdapter = this.listAdapter;
            if (shareDialogsAdapter != null) {
                shareDialogsAdapter.fetchDialogs();
            }
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLayout() {
        if (this.panTranslationMoveLayout) {
            return;
        }
        RecyclerListView recyclerListView = this.searchIsVisible ? this.searchGridView : this.gridView;
        if (recyclerListView.getChildCount() <= 0) {
            return;
        }
        View childAt = recyclerListView.getChildAt(0);
        for (int i = 0; i < recyclerListView.getChildCount(); i++) {
            if (recyclerListView.getChildAt(i).getTop() < childAt.getTop()) {
                childAt = recyclerListView.getChildAt(i);
            }
        }
        RecyclerListView.Holder holder = (RecyclerListView.Holder) recyclerListView.findContainingViewHolder(childAt);
        int top = childAt.getTop() - AndroidUtilities.dp(8.0f);
        int i2 = (top <= 0 || holder == null || holder.getAdapterPosition() != 0) ? 0 : top;
        if (top >= 0 && holder != null && holder.getAdapterPosition() == 0) {
            this.lastOffset = childAt.getTop();
            runShadowAnimation(0, false);
        } else {
            this.lastOffset = Integer.MAX_VALUE;
            runShadowAnimation(0, true);
            top = i2;
        }
        if (this.topicsGridView.getVisibility() == 0) {
            RecyclerListView recyclerListView2 = this.topicsGridView;
            if (recyclerListView2.getChildCount() <= 0) {
                return;
            }
            View childAt2 = recyclerListView2.getChildAt(0);
            for (int i3 = 0; i3 < recyclerListView2.getChildCount(); i3++) {
                if (recyclerListView2.getChildAt(i3).getTop() < childAt2.getTop()) {
                    childAt2 = recyclerListView2.getChildAt(i3);
                }
            }
            RecyclerListView.Holder holder2 = (RecyclerListView.Holder) recyclerListView2.findContainingViewHolder(childAt2);
            int top2 = childAt2.getTop() - AndroidUtilities.dp(8.0f);
            int i4 = (top2 <= 0 || holder2 == null || holder2.getAdapterPosition() != 0) ? 0 : top2;
            if (top2 >= 0 && holder2 != null && holder2.getAdapterPosition() == 0) {
                this.lastOffset = childAt2.getTop();
                runShadowAnimation(0, false);
            } else {
                this.lastOffset = Integer.MAX_VALUE;
                runShadowAnimation(0, true);
                top2 = i4;
            }
            top = AndroidUtilities.lerp(top, top2, this.topicsGridView.getAlpha());
        }
        int i5 = this.scrollOffsetY;
        if (i5 != top) {
            this.previousScrollOffsetY = i5;
            RecyclerListView recyclerListView3 = this.gridView;
            float f = top;
            int i6 = (int) (this.currentPanTranslationY + f);
            this.scrollOffsetY = i6;
            recyclerListView3.setTopGlowOffset(i6);
            RecyclerListView recyclerListView4 = this.searchGridView;
            int i7 = (int) (this.currentPanTranslationY + f);
            this.scrollOffsetY = i7;
            recyclerListView4.setTopGlowOffset(i7);
            RecyclerListView recyclerListView5 = this.topicsGridView;
            int i8 = (int) (f + this.currentPanTranslationY);
            this.scrollOffsetY = i8;
            recyclerListView5.setTopGlowOffset(i8);
            this.frameLayout.setTranslationY(this.scrollOffsetY + this.currentPanTranslationY);
            this.searchEmptyView.setTranslationY(this.scrollOffsetY + this.currentPanTranslationY);
            this.containerView.invalidate();
        }
    }

    private void runShadowAnimation(final int i, final boolean z) {
        if ((!z || this.shadow[i].getTag() == null) && (z || this.shadow[i].getTag() != null)) {
            return;
        }
        this.shadow[i].setTag(z ? null : 1);
        if (z) {
            this.shadow[i].setVisibility(0);
        }
        AnimatorSet animatorSet = this.shadowAnimation[i];
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.shadowAnimation[i] = new AnimatorSet();
        this.shadowAnimation[i].playTogether(ObjectAnimator.ofFloat(this.shadow[i], (Property<View, Float>) View.ALPHA, z ? 1.0f : 0.0f));
        this.shadowAnimation[i].setDuration(150L);
        this.shadowAnimation[i].addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ShareAlert.33
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (ShareAlert.this.shadowAnimation[i] == null || !ShareAlert.this.shadowAnimation[i].equals(animator)) {
                    return;
                }
                if (!z) {
                    ShareAlert.this.shadow[i].setVisibility(4);
                }
                ShareAlert.this.shadowAnimation[i] = null;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                if (ShareAlert.this.shadowAnimation[i] == null || !ShareAlert.this.shadowAnimation[i].equals(animator)) {
                    return;
                }
                ShareAlert.this.shadowAnimation[i] = null;
            }
        });
        this.shadowAnimation[i].start();
    }

    private void copyLink(Context context) {
        final boolean z = false;
        if (this.exportedMessageLink == null && this.linkToCopy[0] == null) {
            return;
        }
        try {
            String link = getLink();
            ClipboardManager clipboardManager = (ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard");
            if (link == null) {
                link = this.exportedMessageLink.link;
            }
            clipboardManager.setPrimaryClip(ClipData.newPlainText("label", link));
            ShareAlertDelegate shareAlertDelegate = this.delegate;
            if (shareAlertDelegate != null && shareAlertDelegate.didCopy()) {
                return;
            }
            if (this.parentActivity instanceof LaunchActivity) {
                TLRPC.TL_exportedMessageLink tL_exportedMessageLink = this.exportedMessageLink;
                if (tL_exportedMessageLink != null && tL_exportedMessageLink.link.contains("/c/")) {
                    z = true;
                }
                ((LaunchActivity) this.parentActivity).showBulletin(new Function() { // from class: org.telegram.ui.Components.ShareAlert$$ExternalSyntheticLambda18
                    @Override // androidx.arch.core.util.Function
                    public final Object apply(Object obj) {
                        return ((BulletinFactory) obj).createCopyLinkBulletin(z);
                    }
                });
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    private boolean showCommentTextView(final boolean z) {
        if (z == (this.frameLayout2.getTag() != null)) {
            return false;
        }
        AnimatorSet animatorSet = this.animatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.frameLayout2.setTag(z ? 1 : null);
        if (this.commentTextView.getEditText().isFocused()) {
            AndroidUtilities.hideKeyboard(this.commentTextView.getEditText());
        }
        this.commentTextView.hidePopup(true);
        if (z) {
            this.frameLayout2.setVisibility(0);
            FrameLayout frameLayout = this.timestampFrameLayout;
            if (frameLayout != null && this.pickerBottom == null) {
                frameLayout.setVisibility(0);
            }
            this.writeButtonContainer.setVisibility(0);
        } else {
            FrameLayout frameLayout2 = this.pickerBottom;
            if (frameLayout2 != null) {
                frameLayout2.setVisibility(0);
            }
        }
        FrameLayout frameLayout3 = this.pickerBottom;
        if (frameLayout3 != null) {
            ViewCompat.setImportantForAccessibility(frameLayout3, z ? 4 : 1);
        }
        LinearLayout linearLayout = this.sharesCountLayout;
        if (linearLayout != null) {
            ViewCompat.setImportantForAccessibility(linearLayout, z ? 4 : 1);
        }
        this.animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        FrameLayout frameLayout4 = this.frameLayout2;
        Property property = View.ALPHA;
        arrayList.add(ObjectAnimator.ofFloat(frameLayout4, (Property<FrameLayout, Float>) property, z ? 1.0f : 0.0f));
        FrameLayout frameLayout5 = this.timestampFrameLayout;
        if (frameLayout5 != null && this.pickerBottom == null) {
            arrayList.add(ObjectAnimator.ofFloat(frameLayout5, (Property<FrameLayout, Float>) property, z ? 1.0f : 0.0f));
        }
        arrayList.add(ObjectAnimator.ofFloat(this.writeButtonContainer, (Property<FrameLayout, Float>) View.SCALE_X, z ? 1.0f : 0.2f));
        arrayList.add(ObjectAnimator.ofFloat(this.writeButtonContainer, (Property<FrameLayout, Float>) View.SCALE_Y, z ? 1.0f : 0.2f));
        arrayList.add(ObjectAnimator.ofFloat(this.writeButtonContainer, (Property<FrameLayout, Float>) property, z ? 1.0f : 0.0f));
        FrameLayout frameLayout6 = this.pickerBottom;
        if (frameLayout6 == null || frameLayout6.getVisibility() != 0) {
            arrayList.add(ObjectAnimator.ofFloat(this.shadow[1], (Property<View, Float>) property, z ? 1.0f : 0.0f));
        }
        FrameLayout frameLayout7 = this.pickerBottomLayout;
        if (frameLayout7 != null) {
            arrayList.add(ObjectAnimator.ofFloat(frameLayout7, (Property<FrameLayout, Float>) View.TRANSLATION_Y, 0.0f));
        }
        this.animatorSet.playTogether(arrayList);
        this.animatorSet.setInterpolator(new DecelerateInterpolator());
        this.animatorSet.setDuration(180L);
        this.animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ShareAlert.34
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (animator.equals(ShareAlert.this.animatorSet)) {
                    if (!z) {
                        ShareAlert.this.frameLayout2.setVisibility(4);
                        ShareAlert shareAlert = ShareAlert.this;
                        if (shareAlert.timestampFrameLayout != null && shareAlert.pickerBottom == null) {
                            ShareAlert.this.timestampFrameLayout.setVisibility(4);
                        }
                        ShareAlert.this.writeButtonContainer.setVisibility(4);
                    } else if (ShareAlert.this.pickerBottom != null) {
                        ShareAlert.this.pickerBottom.setVisibility(4);
                    }
                    ShareAlert.this.animatorSet = null;
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                if (animator.equals(ShareAlert.this.animatorSet)) {
                    ShareAlert.this.animatorSet = null;
                }
            }
        });
        this.animatorSet.start();
        return true;
    }

    public void updateSelectedCount(int i) {
        if (this.selectedDialogs.size() == 0) {
            showCommentTextView(false);
            return;
        }
        ArrayList arrayList = this.sendingMessageObjects;
        int size = arrayList == null ? 1 : arrayList.size();
        if (this.frameLayout2.getTag() != null && this.commentTextView.length() > 0) {
            size++;
        }
        long j = 0;
        for (int i2 = 0; i2 < this.selectedDialogs.size(); i2++) {
            long j2 = ((TLRPC.Dialog) this.selectedDialogs.valueAt(i2)).id;
            long sendPaidMessagesStars = MessagesController.getInstance(this.currentAccount).getSendPaidMessagesStars(j2);
            if (sendPaidMessagesStars <= 0) {
                sendPaidMessagesStars = DialogObject.getMessagesStarsPrice(MessagesController.getInstance(this.currentAccount).isUserContactBlocked(j2));
            }
            j += sendPaidMessagesStars;
        }
        this.writeButton.setCount(Math.max(1, this.selectedDialogs.size()), i != 0);
        this.writeButton.setStarsPrice(j, size, i != 0);
        showCommentTextView(true);
        this.commentTextView.setPadding(0, 0, Math.max(AndroidUtilities.dp(84.0f), this.writeButton.width()), 0);
    }

    @Override // org.telegram.ui.ActionBar.BottomSheet, android.app.Dialog, android.content.DialogInterface, org.telegram.ui.ActionBar.BaseFragment.AttachedSheet
    public void dismiss() {
        EditTextEmoji editTextEmoji = this.commentTextView;
        if (editTextEmoji != null) {
            AndroidUtilities.hideKeyboard(editTextEmoji.getEditText());
        }
        this.fullyShown = false;
        super.dismiss();
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.dialogsNeedReload);
    }

    private class ShareDialogsAdapter extends RecyclerListView.SelectionAdapter {
        private Context context;
        private ArrayList dialogs = new ArrayList();
        private LongSparseArray dialogsMap = new LongSparseArray();

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            return i == 0 ? 1 : 0;
        }

        private class MyStoryDialog extends TLRPC.Dialog {
            private MyStoryDialog() {
                this.id = Long.MAX_VALUE;
            }
        }

        public ShareDialogsAdapter(Context context) {
            this.context = context;
            fetchDialogs();
        }

        public void fetchDialogs() {
            TLRPC.TL_chatAdminRights tL_chatAdminRights;
            this.dialogs.clear();
            this.dialogsMap.clear();
            long j = UserConfig.getInstance(((BottomSheet) ShareAlert.this).currentAccount).clientUserId;
            if (ShareAlert.this.includeStory) {
                MyStoryDialog myStoryDialog = new MyStoryDialog();
                this.dialogs.add(myStoryDialog);
                this.dialogsMap.put(myStoryDialog.id, myStoryDialog);
            }
            if (!MessagesController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).dialogsForward.isEmpty()) {
                TLRPC.Dialog dialog = MessagesController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).dialogsForward.get(0);
                this.dialogs.add(dialog);
                this.dialogsMap.put(dialog.id, dialog);
            }
            ArrayList arrayList = new ArrayList();
            ArrayList<TLRPC.Dialog> allDialogs = MessagesController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).getAllDialogs();
            for (int i = 0; i < allDialogs.size(); i++) {
                TLRPC.Dialog dialog2 = allDialogs.get(i);
                if (dialog2 instanceof TLRPC.TL_dialog) {
                    long j2 = dialog2.id;
                    if (j2 != j && !DialogObject.isEncryptedDialog(j2)) {
                        if (!DialogObject.isUserDialog(dialog2.id)) {
                            TLRPC.Chat chat = MessagesController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).getChat(Long.valueOf(-dialog2.id));
                            if (chat != null && !ChatObject.isNotInChat(chat) && ((!chat.gigagroup || ChatObject.hasAdminRights(chat)) && (!ChatObject.isChannel(chat) || chat.creator || (((tL_chatAdminRights = chat.admin_rights) != null && tL_chatAdminRights.post_messages) || chat.megagroup)))) {
                                if (dialog2.folder_id == 1) {
                                    arrayList.add(dialog2);
                                } else {
                                    this.dialogs.add(dialog2);
                                }
                                this.dialogsMap.put(dialog2.id, dialog2);
                            }
                        } else {
                            if (dialog2.folder_id == 1) {
                                arrayList.add(dialog2);
                            } else {
                                this.dialogs.add(dialog2);
                            }
                            this.dialogsMap.put(dialog2.id, dialog2);
                        }
                    }
                }
            }
            this.dialogs.addAll(arrayList);
            if (ShareAlert.this.parentFragment != null) {
                int i2 = ShareAlert.this.parentFragment.shareAlertDebugMode;
                if (i2 == 1) {
                    ArrayList arrayList2 = this.dialogs;
                    ArrayList arrayList3 = new ArrayList(arrayList2.subList(0, Math.min(4, arrayList2.size())));
                    this.dialogs.clear();
                    this.dialogs.addAll(arrayList3);
                } else if (i2 == 2) {
                    while (!this.dialogs.isEmpty() && this.dialogs.size() < 80) {
                        ArrayList arrayList4 = this.dialogs;
                        arrayList4.add((TLRPC.Dialog) arrayList4.get(arrayList4.size() - 1));
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            int size = this.dialogs.size();
            return size != 0 ? size + 1 : size;
        }

        public TLRPC.Dialog getItem(int i) {
            int i2 = i - 1;
            if (i2 < 0 || i2 >= this.dialogs.size()) {
                return null;
            }
            return (TLRPC.Dialog) this.dialogs.get(i2);
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() != 1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            if (i == 0) {
                view = new ShareDialogCell(this.context, 0, ((BottomSheet) ShareAlert.this).resourcesProvider) { // from class: org.telegram.ui.Components.ShareAlert.ShareDialogsAdapter.1
                    @Override // org.telegram.ui.Cells.ShareDialogCell
                    protected String repostToCustomName() {
                        if (ShareAlert.this.includeStoryFromMessage) {
                            return LocaleController.getString(R.string.RepostToStory);
                        }
                        return super.repostToCustomName();
                    }
                };
                view.setLayoutParams(new RecyclerView.LayoutParams(-1, AndroidUtilities.dp(100.0f)));
            } else {
                view = new View(this.context);
                view.setLayoutParams(new RecyclerView.LayoutParams(-1, AndroidUtilities.dp((!ShareAlert.this.darkTheme || ShareAlert.this.linkToCopy[1] == null) ? 56.0f : 109.0f)));
            }
            return new RecyclerListView.Holder(view);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder.getItemViewType() == 0) {
                ShareDialogCell shareDialogCell = (ShareDialogCell) viewHolder.itemView;
                TLRPC.Dialog item = getItem(i);
                if (item == null) {
                    return;
                }
                shareDialogCell.setTopic((TLRPC.TL_forumTopic) ShareAlert.this.selectedDialogTopics.get(item), MessagesController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).isMonoForum(item.id), false);
                long j = item.id;
                shareDialogCell.setDialog(j, ShareAlert.this.selectedDialogs.indexOfKey(j) >= 0, null);
            }
        }
    }

    private class ShareTopicsAdapter extends RecyclerListView.SelectionAdapter {
        private Context context;
        private List topics;

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            return i == 0 ? 1 : 0;
        }

        public ShareTopicsAdapter(Context context) {
            this.context = context;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            List list = this.topics;
            if (list != null) {
                return list.size() + 1;
            }
            return 0;
        }

        public TLRPC.TL_forumTopic getItemTopic(int i) {
            int i2 = i - 1;
            List list = this.topics;
            if (list == null || i2 < 0 || i2 >= list.size()) {
                return null;
            }
            return (TLRPC.TL_forumTopic) this.topics.get(i2);
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() != 1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View shareTopicCell;
            if (i == 0) {
                shareTopicCell = new ShareTopicCell(this.context, ((BottomSheet) ShareAlert.this).resourcesProvider);
                shareTopicCell.setLayoutParams(new RecyclerView.LayoutParams(-1, AndroidUtilities.dp(100.0f)));
            } else {
                shareTopicCell = new View(this.context);
                shareTopicCell.setLayoutParams(new RecyclerView.LayoutParams(-1, ActionBar.getCurrentActionBarHeight()));
            }
            return new RecyclerListView.Holder(shareTopicCell);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder.getItemViewType() == 0) {
                ShareTopicCell shareTopicCell = (ShareTopicCell) viewHolder.itemView;
                if (this.topics != null) {
                    TLRPC.TL_forumTopic itemTopic = getItemTopic(i);
                    shareTopicCell.setTopic(ShareAlert.this.selectedTopicDialog, itemTopic, itemTopic != null && ShareAlert.this.selectedDialogs.indexOfKey((long) itemTopic.id) >= 0, null);
                }
            }
        }
    }

    public class ShareSearchAdapter extends RecyclerListView.SelectionAdapter {
        DialogsSearchAdapter.CategoryAdapterRecycler categoryAdapter;
        RecyclerView categoryListView;
        private Context context;
        int itemsCount;
        private int lastGlobalSearchId;
        int lastItemCont;
        private int lastLocalSearchId;
        private int lastSearchId;
        private String lastSearchText;
        private SearchAdapterHelper searchAdapterHelper;
        private Runnable searchRunnable;
        private Runnable searchRunnable2;
        private ArrayList searchResult = new ArrayList();
        int hintsCell = -1;
        int resentTitleCell = -1;
        int firstEmptyViewCell = -1;
        int recentDialogsStartRow = -1;
        int searchResultsStartRow = -1;
        int lastFilledItem = -1;
        boolean internalDialogsIsSearching = false;

        public ShareSearchAdapter(Context context) {
            this.context = context;
            SearchAdapterHelper searchAdapterHelper = new SearchAdapterHelper(false) { // from class: org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.1
                @Override // org.telegram.ui.Adapters.SearchAdapterHelper
                protected boolean filter(TLObject tLObject) {
                    return !(tLObject instanceof TLRPC.Chat) || ChatObject.canWriteToChat((TLRPC.Chat) tLObject);
                }
            };
            this.searchAdapterHelper = searchAdapterHelper;
            searchAdapterHelper.setDelegate(new SearchAdapterHelper.SearchAdapterHelperDelegate() { // from class: org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.2
                @Override // org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate
                public /* synthetic */ LongSparseArray getExcludeCallParticipants() {
                    return SearchAdapterHelper.SearchAdapterHelperDelegate.CC.$default$getExcludeCallParticipants(this);
                }

                @Override // org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate
                public /* synthetic */ LongSparseArray getExcludeUsers() {
                    return SearchAdapterHelper.SearchAdapterHelperDelegate.CC.$default$getExcludeUsers(this);
                }

                @Override // org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate
                public /* synthetic */ void onSetHashtags(ArrayList arrayList, HashMap map) {
                    SearchAdapterHelper.SearchAdapterHelperDelegate.CC.$default$onSetHashtags(this, arrayList, map);
                }

                /* JADX WARN: Removed duplicated region for block: B:12:0x003e  */
                @Override // org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void onDataSetChanged(int r4) {
                    /*
                        r3 = this;
                        org.telegram.ui.Components.ShareAlert$ShareSearchAdapter r0 = org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.this
                        org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.m10315$$Nest$fputlastGlobalSearchId(r0, r4)
                        org.telegram.ui.Components.ShareAlert$ShareSearchAdapter r0 = org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.this
                        int r0 = org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.m10311$$Nest$fgetlastLocalSearchId(r0)
                        if (r0 == r4) goto L16
                        org.telegram.ui.Components.ShareAlert$ShareSearchAdapter r4 = org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.this
                        java.util.ArrayList r4 = org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.m10314$$Nest$fgetsearchResult(r4)
                        r4.clear()
                    L16:
                        org.telegram.ui.Components.ShareAlert$ShareSearchAdapter r4 = org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.this
                        int r0 = r4.lastItemCont
                        int r4 = r4.getItemCount()
                        r1 = 1
                        if (r4 != 0) goto L3e
                        org.telegram.ui.Components.ShareAlert$ShareSearchAdapter r4 = org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.this
                        org.telegram.ui.Adapters.SearchAdapterHelper r4 = org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.m10313$$Nest$fgetsearchAdapterHelper(r4)
                        boolean r4 = r4.isSearchInProgress()
                        if (r4 != 0) goto L3e
                        org.telegram.ui.Components.ShareAlert$ShareSearchAdapter r4 = org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.this
                        boolean r2 = r4.internalDialogsIsSearching
                        if (r2 != 0) goto L3e
                        org.telegram.ui.Components.ShareAlert r4 = org.telegram.ui.Components.ShareAlert.this
                        org.telegram.ui.Components.StickerEmptyView r4 = org.telegram.ui.Components.ShareAlert.m10253$$Nest$fgetsearchEmptyView(r4)
                        r0 = 0
                        r4.showProgress(r0, r1)
                        goto L47
                    L3e:
                        org.telegram.ui.Components.ShareAlert$ShareSearchAdapter r4 = org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.this
                        org.telegram.ui.Components.ShareAlert r4 = org.telegram.ui.Components.ShareAlert.this
                        org.telegram.ui.Components.RecyclerItemsEnterAnimator r4 = r4.recyclerItemsEnterAnimator
                        r4.showItemsAnimated(r0)
                    L47:
                        org.telegram.ui.Components.ShareAlert$ShareSearchAdapter r4 = org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.this
                        r4.notifyDataSetChanged()
                        org.telegram.ui.Components.ShareAlert$ShareSearchAdapter r4 = org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.this
                        org.telegram.ui.Components.ShareAlert r4 = org.telegram.ui.Components.ShareAlert.this
                        org.telegram.ui.Components.ShareAlert.m10288$$Nest$mcheckCurrentList(r4, r1)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.AnonymousClass2.onDataSetChanged(int):void");
                }

                @Override // org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate
                public boolean canApplySearchResults(int i) {
                    return i == ShareSearchAdapter.this.lastSearchId;
                }
            });
        }

        private void searchDialogsInternal(final String str, final int i) {
            MessagesStorage.getInstance(((BottomSheet) ShareAlert.this).currentAccount).getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.ui.Components.ShareAlert$ShareSearchAdapter$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$searchDialogsInternal$1(str, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:160:0x0384  */
        /* JADX WARN: Removed duplicated region for block: B:173:0x03f6 A[Catch: Exception -> 0x001e, LOOP:7: B:145:0x033d->B:173:0x03f6, LOOP_END, TryCatch #0 {Exception -> 0x001e, blocks: (B:3:0x0002, B:5:0x0011, B:9:0x0021, B:11:0x002f, B:18:0x003d, B:20:0x0044, B:21:0x0046, B:22:0x006b, B:24:0x0071, B:26:0x0089, B:28:0x0093, B:29:0x009b, B:31:0x00a1, B:33:0x00ac, B:34:0x00b4, B:37:0x00c5, B:38:0x00ea, B:40:0x00f0, B:43:0x0104, B:45:0x010b, B:49:0x0116, B:51:0x0120, B:54:0x0139, B:56:0x013f, B:60:0x0157, B:66:0x0164, B:68:0x016b, B:70:0x0183, B:73:0x0192, B:75:0x01c6, B:74:0x019d, B:77:0x01d4, B:81:0x01e7, B:83:0x01f4, B:85:0x01fa, B:86:0x0220, B:88:0x0226, B:93:0x023d, B:95:0x0245, B:98:0x025c, B:100:0x0262, B:103:0x0279, B:104:0x027c, B:106:0x0282, B:108:0x028f, B:110:0x0295, B:112:0x029b, B:114:0x029f, B:116:0x02a3, B:118:0x02a7, B:120:0x02ab, B:121:0x02ca, B:122:0x02cd, B:123:0x02d3, B:125:0x02d9, B:127:0x02e3, B:129:0x02e7, B:130:0x02ea, B:131:0x02ed, B:132:0x0304, B:134:0x030a, B:137:0x0316, B:140:0x032a, B:142:0x0333, B:146:0x033f, B:148:0x0347, B:151:0x035e, B:153:0x0364, B:157:0x037c, B:162:0x0387, B:164:0x038e, B:166:0x03a2, B:167:0x03a9, B:169:0x03b7, B:171:0x03ec, B:170:0x03c3, B:173:0x03f6, B:175:0x0407), top: B:179:0x0002 }] */
        /* JADX WARN: Removed duplicated region for block: B:195:0x0164 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:228:0x0387 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:77:0x01d4 A[Catch: Exception -> 0x001e, LOOP:2: B:48:0x0114->B:77:0x01d4, LOOP_END, TryCatch #0 {Exception -> 0x001e, blocks: (B:3:0x0002, B:5:0x0011, B:9:0x0021, B:11:0x002f, B:18:0x003d, B:20:0x0044, B:21:0x0046, B:22:0x006b, B:24:0x0071, B:26:0x0089, B:28:0x0093, B:29:0x009b, B:31:0x00a1, B:33:0x00ac, B:34:0x00b4, B:37:0x00c5, B:38:0x00ea, B:40:0x00f0, B:43:0x0104, B:45:0x010b, B:49:0x0116, B:51:0x0120, B:54:0x0139, B:56:0x013f, B:60:0x0157, B:66:0x0164, B:68:0x016b, B:70:0x0183, B:73:0x0192, B:75:0x01c6, B:74:0x019d, B:77:0x01d4, B:81:0x01e7, B:83:0x01f4, B:85:0x01fa, B:86:0x0220, B:88:0x0226, B:93:0x023d, B:95:0x0245, B:98:0x025c, B:100:0x0262, B:103:0x0279, B:104:0x027c, B:106:0x0282, B:108:0x028f, B:110:0x0295, B:112:0x029b, B:114:0x029f, B:116:0x02a3, B:118:0x02a7, B:120:0x02ab, B:121:0x02ca, B:122:0x02cd, B:123:0x02d3, B:125:0x02d9, B:127:0x02e3, B:129:0x02e7, B:130:0x02ea, B:131:0x02ed, B:132:0x0304, B:134:0x030a, B:137:0x0316, B:140:0x032a, B:142:0x0333, B:146:0x033f, B:148:0x0347, B:151:0x035e, B:153:0x0364, B:157:0x037c, B:162:0x0387, B:164:0x038e, B:166:0x03a2, B:167:0x03a9, B:169:0x03b7, B:171:0x03ec, B:170:0x03c3, B:173:0x03f6, B:175:0x0407), top: B:179:0x0002 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public /* synthetic */ void lambda$searchDialogsInternal$1(java.lang.String r21, int r22) {
            /*
                Method dump skipped, instruction units count: 1052
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.lambda$searchDialogsInternal$1(java.lang.String, int):void");
        }

        public static /* synthetic */ int $r8$lambda$N2leCj1fsSDLiXy7aIlYyot2xnY(Object obj, Object obj2) {
            int i = ((DialogSearchResult) obj).date;
            int i2 = ((DialogSearchResult) obj2).date;
            if (i < i2) {
                return 1;
            }
            return i > i2 ? -1 : 0;
        }

        private void updateSearchResults(final ArrayList arrayList, final int i) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.ShareAlert$ShareSearchAdapter$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateSearchResults$2(i, arrayList);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateSearchResults$2(int i, ArrayList arrayList) {
            if (i != this.lastSearchId) {
                return;
            }
            getItemCount();
            this.internalDialogsIsSearching = false;
            this.lastLocalSearchId = i;
            if (this.lastGlobalSearchId != i) {
                this.searchAdapterHelper.clear();
            }
            if (ShareAlert.this.gridView.getAdapter() != ShareAlert.this.searchAdapter) {
                ShareAlert shareAlert = ShareAlert.this;
                shareAlert.topBeforeSwitch = shareAlert.getCurrentTop();
                ShareAlert.this.searchAdapter.notifyDataSetChanged();
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                TLObject tLObject = ((DialogSearchResult) arrayList.get(i2)).object;
                if (tLObject instanceof TLRPC.User) {
                    MessagesController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).putUser((TLRPC.User) tLObject, true);
                } else if (tLObject instanceof TLRPC.Chat) {
                    MessagesController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).putChat((TLRPC.Chat) tLObject, true);
                }
            }
            boolean z = !this.searchResult.isEmpty() && arrayList.isEmpty();
            if (this.searchResult.isEmpty()) {
                arrayList.isEmpty();
            }
            if (z) {
                ShareAlert shareAlert2 = ShareAlert.this;
                shareAlert2.topBeforeSwitch = shareAlert2.getCurrentTop();
            }
            this.searchResult = arrayList;
            this.searchAdapterHelper.mergeResults(arrayList, null);
            int i3 = this.lastItemCont;
            if (getItemCount() == 0 && !this.searchAdapterHelper.isSearchInProgress() && !this.internalDialogsIsSearching) {
                ShareAlert.this.searchEmptyView.showProgress(false, true);
            } else {
                ShareAlert.this.recyclerItemsEnterAnimator.showItemsAnimated(i3);
            }
            notifyDataSetChanged();
            ShareAlert.this.checkCurrentList(true);
        }

        public void searchDialogs(final String str) {
            if (str == null || !str.equals(this.lastSearchText)) {
                this.lastSearchText = str;
                if (this.searchRunnable != null) {
                    Utilities.searchQueue.cancelRunnable(this.searchRunnable);
                    this.searchRunnable = null;
                }
                Runnable runnable = this.searchRunnable2;
                if (runnable != null) {
                    AndroidUtilities.cancelRunOnUIThread(runnable);
                    this.searchRunnable2 = null;
                }
                this.searchResult.clear();
                this.searchAdapterHelper.mergeResults(null);
                this.searchAdapterHelper.queryServerSearch(null, true, true, true, true, false, 0L, false, 0, 0);
                notifyDataSetChanged();
                ShareAlert.this.checkCurrentList(true);
                if (TextUtils.isEmpty(str)) {
                    ShareAlert shareAlert = ShareAlert.this;
                    shareAlert.topBeforeSwitch = shareAlert.getCurrentTop();
                    this.lastSearchId = -1;
                    this.internalDialogsIsSearching = false;
                } else {
                    this.internalDialogsIsSearching = true;
                    final int i = this.lastSearchId + 1;
                    this.lastSearchId = i;
                    ShareAlert.this.searchEmptyView.showProgress(true, true);
                    DispatchQueue dispatchQueue = Utilities.searchQueue;
                    Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.Components.ShareAlert$ShareSearchAdapter$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$searchDialogs$4(str, i);
                        }
                    };
                    this.searchRunnable = runnable2;
                    dispatchQueue.postRunnable(runnable2, 300L);
                }
                ShareAlert.this.checkCurrentList(false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$searchDialogs$4(final String str, final int i) {
            this.searchRunnable = null;
            searchDialogsInternal(str, i);
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.ShareAlert$ShareSearchAdapter$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$searchDialogs$3(i, str);
                }
            };
            this.searchRunnable2 = runnable;
            AndroidUtilities.runOnUIThread(runnable);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$searchDialogs$3(int i, String str) {
            this.searchRunnable2 = null;
            if (i != this.lastSearchId) {
                return;
            }
            this.searchAdapterHelper.queryServerSearch(str, true, true, true, true, false, 0L, false, 0, i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            this.itemsCount = 0;
            this.hintsCell = -1;
            this.resentTitleCell = -1;
            this.recentDialogsStartRow = -1;
            this.searchResultsStartRow = -1;
            this.lastFilledItem = -1;
            if (TextUtils.isEmpty(this.lastSearchText)) {
                int i = this.itemsCount;
                this.firstEmptyViewCell = i;
                this.itemsCount = i + 2;
                this.hintsCell = i + 1;
                if (ShareAlert.this.recentSearchObjects.size() > 0) {
                    int i2 = this.itemsCount;
                    int i3 = i2 + 1;
                    this.itemsCount = i3;
                    this.resentTitleCell = i2;
                    this.recentDialogsStartRow = i3;
                    this.itemsCount = i3 + ShareAlert.this.recentSearchObjects.size();
                }
                int i4 = this.itemsCount;
                int i5 = i4 + 1;
                this.itemsCount = i5;
                this.lastFilledItem = i4;
                this.lastItemCont = i5;
                return i5;
            }
            int i6 = this.itemsCount;
            int i7 = i6 + 1;
            this.itemsCount = i7;
            this.firstEmptyViewCell = i6;
            this.searchResultsStartRow = i7;
            int size = i7 + this.searchResult.size() + this.searchAdapterHelper.getLocalServerSearch().size();
            this.itemsCount = size;
            if (size == 1) {
                this.firstEmptyViewCell = -1;
                this.itemsCount = 0;
                this.lastItemCont = 0;
                return 0;
            }
            int i8 = size + 1;
            this.itemsCount = i8;
            this.lastFilledItem = size;
            this.lastItemCont = i8;
            return i8;
        }

        public TLRPC.Dialog getItem(int i) {
            int i2 = this.recentDialogsStartRow;
            if (i >= i2 && i2 >= 0) {
                int i3 = i - i2;
                if (i3 >= 0 && i3 < ShareAlert.this.recentSearchObjects.size()) {
                    TLObject tLObject = ((DialogsSearchAdapter.RecentSearchObject) ShareAlert.this.recentSearchObjects.get(i3)).object;
                    TLRPC.TL_dialog tL_dialog = new TLRPC.TL_dialog();
                    if (tLObject instanceof TLRPC.User) {
                        tL_dialog.id = ((TLRPC.User) tLObject).id;
                        return tL_dialog;
                    }
                    if (tLObject instanceof TLRPC.Chat) {
                        tL_dialog.id = -((TLRPC.Chat) tLObject).id;
                        return tL_dialog;
                    }
                }
                return null;
            }
            int i4 = i - 1;
            if (i4 < 0) {
                return null;
            }
            if (i4 < this.searchResult.size()) {
                return ((DialogSearchResult) this.searchResult.get(i4)).dialog;
            }
            int size = i4 - this.searchResult.size();
            ArrayList localServerSearch = this.searchAdapterHelper.getLocalServerSearch();
            if (size < localServerSearch.size()) {
                TLObject tLObject2 = (TLObject) localServerSearch.get(size);
                TLRPC.TL_dialog tL_dialog2 = new TLRPC.TL_dialog();
                if (tLObject2 instanceof TLRPC.User) {
                    tL_dialog2.id = ((TLRPC.User) tLObject2).id;
                    return tL_dialog2;
                }
                if (tLObject2 instanceof TLRPC.Chat) {
                    tL_dialog2.id = -((TLRPC.Chat) tLObject2).id;
                    return tL_dialog2;
                }
            }
            return null;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return (viewHolder.getItemViewType() == 1 || viewHolder.getItemViewType() == 4) ? false : true;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View viewShowPremiumBlock;
            View view;
            if (i == 0) {
                viewShowPremiumBlock = new ProfileSearchCell(this.context, ((BottomSheet) ShareAlert.this).resourcesProvider).useCustomPaints().showPremiumBlock(true);
            } else if (i != 2) {
                if (i == 3) {
                    GraySectionCell graySectionCell = new GraySectionCell(this.context, ((BottomSheet) ShareAlert.this).resourcesProvider);
                    graySectionCell.setTextColor(Theme.key_graySectionText);
                    graySectionCell.setBackgroundColor(ShareAlert.this.getThemedColor(Theme.key_graySection));
                    graySectionCell.setText(LocaleController.getString(R.string.Recent));
                    view = graySectionCell;
                } else if (i == 4) {
                    view = new View(this.context) { // from class: org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.6
                        @Override // android.view.View
                        protected void onMeasure(int i2, int i3) {
                            super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(ShareAlert.this.searchLayoutManager.lastItemHeight, TLObject.FLAG_30));
                        }
                    };
                } else if (i == 5) {
                    ShareDialogCell shareDialogCell = new ShareDialogCell(this.context, 0, ((BottomSheet) ShareAlert.this).resourcesProvider);
                    shareDialogCell.setLayoutParams(new RecyclerView.LayoutParams(-1, AndroidUtilities.dp(100.0f)));
                    view = shareDialogCell;
                } else {
                    View view2 = new View(this.context);
                    view2.setLayoutParams(new RecyclerView.LayoutParams(-1, AndroidUtilities.dp((!ShareAlert.this.darkTheme || ShareAlert.this.linkToCopy[1] == null) ? 56.0f : 109.0f)));
                    view = view2;
                }
                viewShowPremiumBlock = view;
            } else {
                RecyclerListView recyclerListView = new RecyclerListView(this.context, ((BottomSheet) ShareAlert.this).resourcesProvider) { // from class: org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.3
                    @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
                    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                        if (getParent() != null && getParent().getParent() != null) {
                            ViewParent parent = getParent().getParent();
                            boolean z = true;
                            if (!canScrollHorizontally(-1) && !canScrollHorizontally(1)) {
                                z = false;
                            }
                            parent.requestDisallowInterceptTouchEvent(z);
                        }
                        return super.onInterceptTouchEvent(motionEvent);
                    }
                };
                this.categoryListView = recyclerListView;
                recyclerListView.setItemAnimator(null);
                recyclerListView.setLayoutAnimation(null);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context) { // from class: org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.4
                    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                    public boolean supportsPredictiveItemAnimations() {
                        return false;
                    }
                };
                linearLayoutManager.setOrientation(0);
                recyclerListView.setLayoutManager(linearLayoutManager);
                DialogsSearchAdapter.CategoryAdapterRecycler categoryAdapterRecycler = new DialogsSearchAdapter.CategoryAdapterRecycler(this.context, ((BottomSheet) ShareAlert.this).currentAccount, true, true, ((BottomSheet) ShareAlert.this).resourcesProvider) { // from class: org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.5
                    @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.CategoryAdapterRecycler, androidx.recyclerview.widget.RecyclerView.Adapter
                    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
                        TLRPC.Chat chat;
                        String firstName;
                        HintDialogCell hintDialogCell = (HintDialogCell) viewHolder.itemView;
                        if (ShareAlert.this.darkTheme || ShareAlert.this.forceDarkThemeForHint) {
                            hintDialogCell.setColors(Theme.key_voipgroup_nameText, Theme.key_voipgroup_inviteMembersBackground);
                        }
                        TLRPC.TL_topPeer tL_topPeer = MediaDataController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).hints.get(i2);
                        TLRPC.Peer peer = tL_topPeer.peer;
                        long j = peer.user_id;
                        TLRPC.User user = null;
                        if (j != 0) {
                            user = MessagesController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).getUser(Long.valueOf(tL_topPeer.peer.user_id));
                            chat = null;
                        } else {
                            long j2 = peer.channel_id;
                            if (j2 != 0) {
                                j = -j2;
                                chat = MessagesController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).getChat(Long.valueOf(tL_topPeer.peer.channel_id));
                            } else {
                                long j3 = peer.chat_id;
                                if (j3 != 0) {
                                    j = -j3;
                                    chat = MessagesController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).getChat(Long.valueOf(tL_topPeer.peer.chat_id));
                                } else {
                                    j = 0;
                                    chat = null;
                                }
                            }
                        }
                        boolean z = j == hintDialogCell.getDialogId();
                        hintDialogCell.setTag(Long.valueOf(j));
                        if (user != null) {
                            firstName = UserObject.getFirstName(user);
                        } else if (chat == null) {
                            firstName = _UrlKt.FRAGMENT_ENCODE_SET;
                        } else {
                            firstName = chat.title;
                        }
                        hintDialogCell.setDialog(j, true, firstName);
                        hintDialogCell.setChecked(ShareAlert.this.selectedDialogs.indexOfKey(j) >= 0, z);
                    }
                };
                this.categoryAdapter = categoryAdapterRecycler;
                recyclerListView.setAdapter(categoryAdapterRecycler);
                recyclerListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.Components.ShareAlert$ShareSearchAdapter$$ExternalSyntheticLambda0
                    @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                    public final void onItemClick(View view3, int i2) {
                        this.f$0.lambda$onCreateViewHolder$5(view3, i2);
                    }
                });
                viewShowPremiumBlock = recyclerListView;
            }
            return new RecyclerListView.Holder(viewShowPremiumBlock);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:7:0x002a A[PHI: r1
  0x002a: PHI (r1v5 long) = (r1v2 long), (r1v3 long) binds: [B:6:0x0028, B:9:0x0030] A[DONT_GENERATE, DONT_INLINE]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public /* synthetic */ void lambda$onCreateViewHolder$5(android.view.View r7, int r8) {
            /*
                r6 = this;
                org.telegram.ui.Cells.HintDialogCell r7 = (org.telegram.ui.Cells.HintDialogCell) r7
                org.telegram.ui.Components.ShareAlert r0 = org.telegram.ui.Components.ShareAlert.this
                int r0 = org.telegram.ui.Components.ShareAlert.access$7200(r0)
                org.telegram.messenger.MediaDataController r0 = org.telegram.messenger.MediaDataController.getInstance(r0)
                java.util.ArrayList<org.telegram.tgnet.TLRPC$TL_topPeer> r0 = r0.hints
                java.lang.Object r8 = r0.get(r8)
                org.telegram.tgnet.TLRPC$TL_topPeer r8 = (org.telegram.tgnet.TLRPC.TL_topPeer) r8
                org.telegram.tgnet.TLRPC$TL_dialog r0 = new org.telegram.tgnet.TLRPC$TL_dialog
                r0.<init>()
                org.telegram.tgnet.TLRPC$Peer r8 = r8.peer
                long r1 = r8.user_id
                r3 = 0
                int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                if (r5 == 0) goto L24
                goto L34
            L24:
                long r1 = r8.channel_id
                int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                if (r5 == 0) goto L2c
            L2a:
                long r1 = -r1
                goto L34
            L2c:
                long r1 = r8.chat_id
                int r8 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                if (r8 == 0) goto L33
                goto L2a
            L33:
                r1 = r3
            L34:
                boolean r8 = r7.isBlocked()
                if (r8 == 0) goto L40
                org.telegram.ui.Components.ShareAlert r8 = org.telegram.ui.Components.ShareAlert.this
                org.telegram.ui.Components.ShareAlert.m10292$$Nest$mshowPremiumBlockedToast(r8, r7, r1)
                return
            L40:
                r0.id = r1
                org.telegram.ui.Components.ShareAlert r8 = org.telegram.ui.Components.ShareAlert.this
                r3 = 0
                org.telegram.ui.Components.ShareAlert.m10291$$Nest$mselectDialog(r8, r3, r0)
                org.telegram.ui.Components.ShareAlert r8 = org.telegram.ui.Components.ShareAlert.this
                androidx.collection.LongSparseArray r8 = r8.selectedDialogs
                int r8 = r8.indexOfKey(r1)
                r0 = 1
                if (r8 < 0) goto L55
                r8 = r0
                goto L56
            L55:
                r8 = 0
            L56:
                r7.setChecked(r8, r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.ShareAlert.ShareSearchAdapter.lambda$onCreateViewHolder$5(android.view.View, int):void");
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            long j;
            String name;
            CharSequence charSequence;
            long j2;
            int iIndexOfIgnoreCase;
            Object obj;
            CharSequence charSequence2;
            int iIndexOfIgnoreCase2;
            if (viewHolder.getItemViewType() == 0 || viewHolder.getItemViewType() == 5) {
                TLObject tLObject = null;
                name = null;
                CharSequence name2 = null;
                TLRPC.EncryptedChat encryptedChat = null;
                if (TextUtils.isEmpty(this.lastSearchText)) {
                    int i2 = this.recentDialogsStartRow;
                    long j3 = 0;
                    if (i2 < 0 || i < i2) {
                        obj = null;
                        charSequence2 = null;
                    } else {
                        Object obj2 = ((DialogsSearchAdapter.RecentSearchObject) ShareAlert.this.recentSearchObjects.get(i - i2)).object;
                        if (obj2 instanceof TLRPC.User) {
                            TLRPC.User user = (TLRPC.User) obj2;
                            j3 = user.id;
                            name2 = ContactsController.formatName(user.first_name, user.last_name);
                        } else if (obj2 instanceof TLRPC.Chat) {
                            TLRPC.Chat chat = (TLRPC.Chat) obj2;
                            j3 = -chat.id;
                            name2 = chat.title;
                        } else if (obj2 instanceof TLRPC.TL_encryptedChat) {
                            encryptedChat = (TLRPC.TL_encryptedChat) obj2;
                            TLRPC.User user2 = MessagesController.getInstance(((BottomSheet) ShareAlert.this).currentAccount).getUser(Long.valueOf(encryptedChat.user_id));
                            if (user2 != null) {
                                j3 = user2.id;
                                name2 = ContactsController.formatName(user2.first_name, user2.last_name);
                            }
                        }
                        String lastFoundUsername = this.searchAdapterHelper.getLastFoundUsername();
                        if (TextUtils.isEmpty(lastFoundUsername) || name2 == null || (iIndexOfIgnoreCase2 = AndroidUtilities.indexOfIgnoreCase(name2.toString(), lastFoundUsername)) == -1) {
                            obj = obj2;
                            charSequence2 = name2;
                        } else {
                            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(name2);
                            spannableStringBuilder.setSpan(new ForegroundColorSpanThemable(Theme.key_windowBackgroundWhiteBlueText4, ((BottomSheet) ShareAlert.this).resourcesProvider), iIndexOfIgnoreCase2, lastFoundUsername.length() + iIndexOfIgnoreCase2, 33);
                            obj = obj2;
                            charSequence2 = spannableStringBuilder;
                        }
                    }
                    TLRPC.EncryptedChat encryptedChat2 = encryptedChat;
                    View view = viewHolder.itemView;
                    if (view instanceof ProfileSearchCell) {
                        ((ProfileSearchCell) view).setData(obj, encryptedChat2, charSequence2, null, false, false);
                        ((ProfileSearchCell) viewHolder.itemView).useSeparator = i < getItemCount() + (-2);
                        return;
                    } else {
                        CharSequence charSequence3 = charSequence2;
                        if (view instanceof ShareDialogCell) {
                            ((ShareDialogCell) view).setDialog(j3, ShareAlert.this.selectedDialogs.indexOfKey(j3) >= 0, charSequence3);
                            return;
                        }
                        return;
                    }
                }
                int size = i - 1;
                if (size < this.searchResult.size()) {
                    DialogSearchResult dialogSearchResult = (DialogSearchResult) this.searchResult.get(size);
                    j2 = dialogSearchResult.dialog.id;
                    charSequence = dialogSearchResult.name;
                } else {
                    size -= this.searchResult.size();
                    tLObject = (TLObject) this.searchAdapterHelper.getLocalServerSearch().get(size);
                    if (tLObject instanceof TLRPC.User) {
                        TLRPC.User user3 = (TLRPC.User) tLObject;
                        j = user3.id;
                        name = ContactsController.formatName(user3.first_name, user3.last_name);
                    } else {
                        TLRPC.Chat chat2 = (TLRPC.Chat) tLObject;
                        j = -chat2.id;
                        name = chat2.title;
                    }
                    String lastFoundUsername2 = this.searchAdapterHelper.getLastFoundUsername();
                    if (TextUtils.isEmpty(lastFoundUsername2) || name == null || (iIndexOfIgnoreCase = AndroidUtilities.indexOfIgnoreCase(name.toString(), lastFoundUsername2)) == -1) {
                        charSequence = name;
                    } else {
                        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(name);
                        spannableStringBuilder2.setSpan(new ForegroundColorSpanThemable(Theme.key_windowBackgroundWhiteBlueText4, ((BottomSheet) ShareAlert.this).resourcesProvider), iIndexOfIgnoreCase, lastFoundUsername2.length() + iIndexOfIgnoreCase, 33);
                        charSequence = spannableStringBuilder2;
                    }
                    j2 = j;
                }
                TLObject tLObject2 = tLObject;
                View view2 = viewHolder.itemView;
                if (view2 instanceof ProfileSearchCell) {
                    ((ProfileSearchCell) view2).setData(tLObject2, null, charSequence, null, false, false);
                    ((ProfileSearchCell) viewHolder.itemView).useSeparator = size < getItemCount() + (-2);
                    return;
                } else {
                    if (view2 instanceof ShareDialogCell) {
                        ((ShareDialogCell) view2).setDialog(j2, ShareAlert.this.selectedDialogs.indexOfKey(j2) >= 0, charSequence);
                        return;
                    }
                    return;
                }
            }
            if (viewHolder.getItemViewType() == 2) {
                ((RecyclerListView) viewHolder.itemView).getAdapter().notifyDataSetChanged();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == this.lastFilledItem) {
                return 4;
            }
            if (i == this.firstEmptyViewCell) {
                return 1;
            }
            if (i == this.hintsCell) {
                return 2;
            }
            if (i == this.resentTitleCell) {
                return 3;
            }
            return TextUtils.isEmpty(this.lastSearchText) ? 0 : 5;
        }

        public int getSpanSize(int i, int i2) {
            if (i2 == this.hintsCell || i2 == this.resentTitleCell || i2 == this.firstEmptyViewCell || i2 == this.lastFilledItem || getItemViewType(i2) == 0) {
                return i;
            }
            return 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkCurrentList(boolean z) {
        boolean z2 = true;
        if (!TextUtils.isEmpty(this.searchView.editText.getText()) || ((this.keyboardVisible && this.searchView.editText.hasFocus()) || this.searchWasVisibleBeforeTopics)) {
            this.updateSearchAdapter = true;
            if (this.selectedTopicDialog == null) {
                AndroidUtilities.updateViewVisibilityAnimated(this.gridView, false, 0.98f, true);
                AndroidUtilities.updateViewVisibilityAnimated(this.searchGridView, true);
            }
        } else {
            if (this.selectedTopicDialog == null) {
                AndroidUtilities.updateViewVisibilityAnimated(this.gridView, true, 0.98f, true);
                AndroidUtilities.updateViewVisibilityAnimated(this.searchGridView, false);
            }
            z2 = false;
        }
        if (this.searchIsVisible != z2 || z) {
            this.searchIsVisible = z2;
            this.searchAdapter.notifyDataSetChanged();
            this.listAdapter.notifyDataSetChanged();
            if (this.searchIsVisible) {
                if (this.lastOffset == Integer.MAX_VALUE) {
                    ((LinearLayoutManager) this.searchGridView.getLayoutManager()).scrollToPositionWithOffset(0, -this.searchGridView.getPaddingTop());
                } else {
                    ((LinearLayoutManager) this.searchGridView.getLayoutManager()).scrollToPositionWithOffset(0, this.lastOffset - this.searchGridView.getPaddingTop());
                }
                this.searchAdapter.searchDialogs(this.searchView.editText.getText().toString());
                return;
            }
            if (this.lastOffset == Integer.MAX_VALUE) {
                this.layoutManager.scrollToPositionWithOffset(0, 0);
            } else {
                this.layoutManager.scrollToPositionWithOffset(0, 0);
            }
        }
    }

    private String getLink() {
        String string;
        SwitchView switchView = this.switchView;
        if (switchView != null) {
            string = this.linkToCopy[switchView.currentTab];
        } else {
            TLRPC.TL_exportedMessageLink tL_exportedMessageLink = this.exportedMessageLink;
            string = tL_exportedMessageLink != null ? tL_exportedMessageLink.link : null;
            if (string == null) {
                string = this.linkToCopy[0];
            }
        }
        CheckBox2 checkBox2 = this.timestampCheckbox;
        if (checkBox2 != null && checkBox2.isChecked()) {
            try {
                string = Uri.parse(string).buildUpon().appendQueryParameter("t", AndroidUtilities.formatTimestamp(this.timestamp)).build().toString();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
        return string == null ? _UrlKt.FRAGMENT_ENCODE_SET : string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLinkTextView() {
        if (this.linkTextView != null) {
            String link = getLink();
            if (link != null) {
                if (link.startsWith("https://")) {
                    link = link.substring(8);
                } else if (link.startsWith("http://")) {
                    link = link.substring(7);
                }
            }
            this.linkTextView.setText(link);
        }
    }

    public void updateBottomOverlay() {
        AdjustPanLayoutHelper adjustPanLayoutHelper;
        if (this.frameLayout2 == null) {
            return;
        }
        EditTextEmoji editTextEmoji = this.commentTextView;
        float fDp = 0.0f;
        if (editTextEmoji != null && editTextEmoji.isPopupVisible()) {
            this.keyboardT = this.commentTextView.getEmojiPaddingShown();
        } else {
            SizeNotifierFrameLayout sizeNotifierFrameLayout = this.sizeNotifierFrameLayout;
            if (sizeNotifierFrameLayout != null && (adjustPanLayoutHelper = sizeNotifierFrameLayout.adjustPanLayoutHelper) != null && !adjustPanLayoutHelper.animationInProgress()) {
                this.keyboardT = this.keyboardSize2 > AndroidUtilities.dp(20.0f) ? 1.0f : 0.0f;
            }
        }
        FrameLayout frameLayout = this.timestampFrameLayout;
        if (frameLayout != null) {
            frameLayout.setTranslationY(-0.0f);
            fDp = 0.0f + AndroidUtilities.dp(48.0f);
        }
        FrameLayout frameLayout2 = this.pickerBottom;
        if (frameLayout2 != null) {
            float f = -fDp;
            frameLayout2.setTranslationY(f);
            LinearLayout linearLayout = this.sharesCountLayout;
            if (linearLayout != null) {
                linearLayout.setTranslationY(f);
            }
        }
        float f2 = -fDp;
        this.frameLayout2.setTranslationY(f2);
        this.writeButtonContainer.setTranslationY(f2);
    }

    private void onTopicCellClick(TLRPC.TL_forumTopic tL_forumTopic) {
        TLRPC.Dialog dialog;
        if (tL_forumTopic == null || (dialog = this.selectedTopicDialog) == null) {
            return;
        }
        long j = dialog.id;
        boolean zIsMonoForum = MessagesController.getInstance(this.currentAccount).isMonoForum(j);
        TLRPC.Dialog dialog2 = this.selectedTopicDialog;
        this.selectedDialogs.put(j, dialog2);
        this.selectedDialogTopics.put(dialog2, tL_forumTopic);
        updateSelectedCount(2);
        if (this.searchIsVisible || this.searchWasVisibleBeforeTopics) {
            if (((TLRPC.Dialog) this.listAdapter.dialogsMap.get(dialog2.id)) == null) {
                this.listAdapter.dialogsMap.put(dialog2.id, dialog2);
                this.listAdapter.dialogs.add(!this.listAdapter.dialogs.isEmpty() ? 1 : 0, dialog2);
            }
            this.listAdapter.notifyDataSetChanged();
            this.updateSearchAdapter = false;
            this.searchView.editText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
            checkCurrentList(false);
        }
        for (int i = 0; i < getMainGridView().getChildCount(); i++) {
            View childAt = getMainGridView().getChildAt(i);
            if (childAt instanceof ShareDialogCell) {
                ShareDialogCell shareDialogCell = (ShareDialogCell) childAt;
                if (shareDialogCell.getCurrentDialog() == this.selectedTopicDialog.id) {
                    shareDialogCell.setTopic(tL_forumTopic, zIsMonoForum, true);
                    shareDialogCell.setChecked(true, true);
                }
            }
        }
        collapseTopics();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void blur3_InvalidateBlur() {
        if (Build.VERSION.SDK_INT < 31 || this.scrollableViewNoiseSuppressor == null) {
            return;
        }
        this.iBlur3PositionMainTabs.set(0.0f, 0.0f, this.containerView.getMeasuredWidth(), this.containerView.getMeasuredHeight());
        this.iBlur3PositionMainTabs.inset(0.0f, LiteMode.isEnabled(262144) ? 0.0f : -AndroidUtilities.dp(48.0f));
        this.scrollableViewNoiseSuppressor.setupRenderNodes(this.iBlur3Positions, 1);
        this.scrollableViewNoiseSuppressor.invalidateResultRenderNodes(this.iBlur3Capture, this.containerView.getMeasuredWidth(), this.containerView.getMeasuredHeight());
    }
}
