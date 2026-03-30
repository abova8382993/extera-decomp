package org.telegram.p026ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.Keep;
import androidx.core.graphics.ColorUtils;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import okhttp3.internal.url._UrlKt;
import org.mvel2.Operator;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BillingController;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2702R;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.GenericProvider;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.p026ui.ActionBar.ActionBar;
import org.telegram.p026ui.ActionBar.BaseFragment;
import org.telegram.p026ui.ActionBar.INavigationLayout;
import org.telegram.p026ui.ActionBar.Theme;
import org.telegram.p026ui.ActionBar.ThemeDescription;
import org.telegram.p026ui.Business.AwayMessagesActivity;
import org.telegram.p026ui.Business.BusinessChatbotController;
import org.telegram.p026ui.Business.BusinessIntroActivity;
import org.telegram.p026ui.Business.BusinessLinksActivity;
import org.telegram.p026ui.Business.BusinessLinksController;
import org.telegram.p026ui.Business.ChatbotsActivity;
import org.telegram.p026ui.Business.GreetMessagesActivity;
import org.telegram.p026ui.Business.LocationActivity;
import org.telegram.p026ui.Business.OpeningHoursActivity;
import org.telegram.p026ui.Business.QuickRepliesActivity;
import org.telegram.p026ui.Business.QuickRepliesController;
import org.telegram.p026ui.Business.TimezonesController;
import org.telegram.p026ui.Cells.HeaderCell;
import org.telegram.p026ui.Cells.ShadowSectionCell;
import org.telegram.p026ui.Cells.TextCell;
import org.telegram.p026ui.Cells.TextInfoPrivacyCell;
import org.telegram.p026ui.Components.AlertsCreator;
import org.telegram.p026ui.Components.BulletinFactory;
import org.telegram.p026ui.Components.CubicBezierInterpolator;
import org.telegram.p026ui.Components.FillLastLinearLayoutManager;
import org.telegram.p026ui.Components.LayoutHelper;
import org.telegram.p026ui.Components.MediaActivity;
import org.telegram.p026ui.Components.Premium.AboutPremiumView;
import org.telegram.p026ui.Components.Premium.GLIcon.GLIconTextureView;
import org.telegram.p026ui.Components.Premium.PremiumButtonView;
import org.telegram.p026ui.Components.Premium.PremiumFeatureBottomSheet;
import org.telegram.p026ui.Components.Premium.PremiumGradient;
import org.telegram.p026ui.Components.Premium.PremiumTierCell;
import org.telegram.p026ui.Components.Premium.StarParticlesView;
import org.telegram.p026ui.Components.RecyclerListView;
import org.telegram.p026ui.Components.SimpleThemeDescription;
import org.telegram.p026ui.FilterCreateActivity;
import org.telegram.p026ui.PremiumPreviewFragment;
import org.telegram.p026ui.SelectAnimatedEmojiDialog;
import org.telegram.p026ui.Stories.recorder.HintView2;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p025tl.TL_account;
import org.telegram.tgnet.p025tl.TL_stars;

/* JADX INFO: loaded from: classes3.dex */
public class PremiumPreviewFragment extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    BackgroundView backgroundView;
    private FrameLayout buttonContainer;
    private View buttonDivider;
    private FrameLayout contentView;
    SubscriptionTier currentSubscriptionTier;
    private int currentYOffset;
    PremiumFeatureCell dummyCell;
    PremiumTierCell dummyTierCell;
    int featuresEndRow;
    int featuresStartRow;
    private int firstViewHeight;
    private boolean forcePremium;
    final Canvas gradientCanvas;
    Paint gradientPaint;
    final Bitmap gradientTextureBitmap;
    PremiumGradient.PremiumGradientTools gradientTools;
    int helpUsRow;
    boolean inc;
    private boolean isDialogVisible;
    boolean isLandscapeMode;
    int lastPaddingRow;
    FillLastLinearLayoutManager layoutManager;
    RecyclerListView listView;
    Matrix matrix;
    int moreFeaturesEndRow;
    int moreFeaturesStartRow;
    int moreHeaderRow;
    ArrayList morePremiumFeatures;
    int paddingRow;
    StarParticlesView particlesView;
    private PremiumButtonView premiumButtonView;
    ArrayList premiumFeatures;
    int privacyRow;
    float progress;
    float progressToFull;
    int rowCount;
    int sectionRow;
    private SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialog;
    private boolean selectAnnualByDefault;
    int selectedTierIndex;
    FrameLayout settingsView;
    Shader shader;
    Drawable shadowDrawable;
    int showAdsHeaderRow;
    int showAdsInfoRow;

    @Keep
    int showAdsRow;
    private String source;
    private int statusBarHeight;
    int statusRow;
    Paint strokePaint;
    Shader strokeShader;
    final ArrayList subscriptionTiers;
    PremiumGradient.PremiumGradientTools tiersGradientTools;
    int totalGradientHeight;
    float totalProgress;
    int totalTiersGradientHeight;
    private final int type;
    private boolean whiteBackground;

    public static /* synthetic */ void $r8$lambda$8qLlq_0tdQNBu0mq8kq48HmBL9s(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    /* JADX INFO: renamed from: $r8$lambda$IdmG-XdD9IOKvD6w09SwNb7iPyw */
    public static /* synthetic */ void m16150$r8$lambda$IdmGXdD9IOKvD6w09SwNb7iPyw(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    public static /* synthetic */ void $r8$lambda$NFplOqXqxu6lZ1w7Yqv5yvLsY8Y(View view) {
    }

    public static /* synthetic */ void $r8$lambda$uvLUhmFSlOozGY_McZFrXQVR5P8(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    /* JADX INFO: renamed from: $r8$lambda$vkLBVB9RYwgpPVlEzKUMDbD4-4E */
    public static /* synthetic */ void m16153$r8$lambda$vkLBVB9RYwgpPVlEzKUMDbD44E(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean isActionBarCrossfadeEnabled() {
        return false;
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean isSwipeBackEnabled(MotionEvent motionEvent) {
        return true;
    }

    public static int serverStringToFeatureType(String str) {
        str.getClass();
        switch (str) {
            case "animated_userpics":
                return 7;
            case "infinite_reactions":
                return 4;
            case "stories__caption":
                return 21;
            case "away_message":
                return 33;
            case "business_hours":
                return 30;
            case "business_intro":
                return 36;
            case "business_links":
                return 37;
            case "stories__quality":
                return 25;
            case "stories__save_stories_to_gallery":
                return 18;
            case "stories":
                return 14;
            case "effects":
                return 38;
            case "business_location":
                return 29;
            case "peer_colors":
                return 23;
            case "voice_to_text":
                return 8;
            case "wallpapers":
                return 22;
            case "message_privacy":
                return 27;
            case "animated_emoji":
                return 11;
            case "translations":
                return 13;
            case "business":
                return 28;
            case "no_ads":
                return 3;
            case "more_upload":
                return 1;
            case "emoji_status":
                return 12;
            case "profile_badge":
                return 6;
            case "faster_download":
                return 2;
            case "greeting_message":
                return 32;
            case "premium_stickers":
                return 5;
            case "double_limits":
                return 0;
            case "todo":
                return 39;
            case "gifts":
                return 40;
            case "folder_tags":
                return 35;
            case "stories__expiration_durations":
                return 17;
            case "stories__stealth_mode":
                return 15;
            case "quick_replies":
                return 31;
            case "business_bots":
                return 34;
            case "stories__priority_order":
                return 20;
            case "stories__links_and_formatting":
                return 19;
            case "advanced_chat_management":
                return 9;
            case "stories__permanent_views_history":
                return 16;
            case "saved_tags":
                return 24;
            case "app_icons":
                return 10;
            case "last_seen":
                return 26;
            default:
                return -1;
        }
    }

    public static String featureTypeToServerString(int i) {
        switch (i) {
            case 0:
                return "double_limits";
            case 1:
                return "more_upload";
            case 2:
                return "faster_download";
            case 3:
                return "no_ads";
            case 4:
                return "infinite_reactions";
            case 5:
                return "premium_stickers";
            case 6:
                return "profile_badge";
            case 7:
                return "animated_userpics";
            case 8:
                return "voice_to_text";
            case 9:
                return "advanced_chat_management";
            case 10:
                return "app_icons";
            case 11:
                return "animated_emoji";
            case 12:
                return "emoji_status";
            case 13:
                return "translations";
            case 14:
                return "stories";
            case 15:
                return "stories__stealth_mode";
            case 16:
                return "stories__permanent_views_history";
            case 17:
                return "stories__expiration_durations";
            case 18:
                return "stories__save_stories_to_gallery";
            case 19:
                return "stories__links_and_formatting";
            case 20:
                return "stories__priority_order";
            case 21:
                return "stories__caption";
            case 22:
                return "wallpapers";
            case 23:
                return "peer_colors";
            case 24:
                return "saved_tags";
            case 25:
                return "stories__quality";
            case 26:
                return "last_seen";
            case 27:
                return "message_privacy";
            case 28:
                return "business";
            case 29:
                return "business_location";
            case 30:
                return "business_hours";
            case 31:
                return "quick_replies";
            case 32:
                return "greeting_message";
            case 33:
                return "away_message";
            case 34:
                return "business_bots";
            case Operator.PROJECTION /* 35 */:
                return "folder_tags";
            case Operator.CONVERTABLE_TO /* 36 */:
                return "business_intro";
            case Operator.END_OF_STMT /* 37 */:
                return "business_links";
            case Operator.FOREACH /* 38 */:
                return "effects";
            case Operator.f1506IF /* 39 */:
                return "todo";
            case Operator.ELSE /* 40 */:
                return "gifts";
            default:
                return null;
        }
    }

    public PremiumPreviewFragment setForcePremium() {
        this.forcePremium = true;
        return this;
    }

    public PremiumPreviewFragment(String str) {
        this(0, str);
    }

    public PremiumPreviewFragment(int i, String str) {
        this.premiumFeatures = new ArrayList();
        this.morePremiumFeatures = new ArrayList();
        this.subscriptionTiers = new ArrayList();
        boolean z = false;
        this.selectedTierIndex = 0;
        this.strokePaint = new Paint(1);
        this.matrix = new Matrix();
        this.gradientPaint = new Paint(1);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        this.gradientTextureBitmap = bitmapCreateBitmap;
        this.gradientCanvas = new Canvas(bitmapCreateBitmap);
        this.gradientTools = new PremiumGradient.PremiumGradientTools(Theme.key_premiumGradientBackground1, Theme.key_premiumGradientBackground2, Theme.key_premiumGradientBackground3, Theme.key_premiumGradientBackground4);
        PremiumGradient.PremiumGradientTools premiumGradientTools = new PremiumGradient.PremiumGradientTools(Theme.key_premiumGradient1, Theme.key_premiumGradient2, -1, -1);
        this.tiersGradientTools = premiumGradientTools;
        premiumGradientTools.exactly = true;
        premiumGradientTools.f1981x1 = 0.0f;
        premiumGradientTools.f1983y1 = 0.0f;
        premiumGradientTools.f1982x2 = 0.0f;
        premiumGradientTools.f1984y2 = 1.0f;
        premiumGradientTools.f1979cx = 0.0f;
        premiumGradientTools.f1980cy = 0.0f;
        this.type = i;
        if (!Theme.isCurrentThemeDark() && i == 1) {
            z = true;
        }
        this.whiteBackground = z;
        this.source = str;
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.hasOwnBackground = true;
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, AndroidUtilities.m1081dp(28.0f), new int[]{1308622847, 0, 452984831}, new float[]{0.0f, 0.5f, 1.0f}, tileMode);
        this.strokeShader = linearGradient;
        this.strokePaint.setShader(linearGradient);
        this.strokePaint.setStyle(Paint.Style.STROKE);
        int color = Theme.getColor(Theme.key_premiumGradient4);
        int color2 = Theme.getColor(Theme.key_premiumGradient3);
        int i = Theme.key_premiumGradient2;
        LinearGradient linearGradient2 = new LinearGradient(0.0f, 0.0f, 0.0f, 100.0f, new int[]{color, color2, Theme.getColor(i), Theme.getColor(Theme.key_premiumGradient1), Theme.getColor(Theme.key_premiumGradient0)}, new float[]{0.0f, 0.32f, 0.5f, 0.7f, 1.0f}, tileMode);
        this.shader = linearGradient2;
        linearGradient2.setLocalMatrix(this.matrix);
        this.gradientPaint.setShader(this.shader);
        this.dummyCell = new PremiumFeatureCell(context);
        this.dummyTierCell = new PremiumTierCell(context);
        this.premiumFeatures.clear();
        this.morePremiumFeatures.clear();
        if (this.type == 0) {
            fillPremiumFeaturesList(this.premiumFeatures, this.currentAccount, false);
        } else {
            fillBusinessFeaturesList(this.premiumFeatures, this.currentAccount, false);
            fillBusinessFeaturesList(this.morePremiumFeatures, this.currentAccount, true);
            QuickRepliesController.getInstance(this.currentAccount).load();
            if (getUserConfig().isPremium()) {
                TLRPC.TL_inputStickerSetShortName tL_inputStickerSetShortName = new TLRPC.TL_inputStickerSetShortName();
                tL_inputStickerSetShortName.short_name = "RestrictedEmoji";
                MediaDataController.getInstance(this.currentAccount).getStickerSet(tL_inputStickerSetShortName, false);
                BusinessChatbotController.getInstance(this.currentAccount).load(null);
                if (getMessagesController().suggestedFilters.isEmpty()) {
                    getMessagesController().loadSuggestedFilters();
                }
                BusinessLinksController.getInstance(this.currentAccount).load(false);
            }
        }
        Rect rect = new Rect();
        Drawable drawableMutate = context.getResources().getDrawable(C2702R.drawable.sheet_shadow_round).mutate();
        this.shadowDrawable = drawableMutate;
        int i2 = Theme.key_dialogBackground;
        drawableMutate.setColorFilter(new PorterDuffColorFilter(getThemedColor(i2), PorterDuff.Mode.MULTIPLY));
        this.shadowDrawable.getPadding(rect);
        this.statusBarHeight = AndroidUtilities.isTablet() ? 0 : AndroidUtilities.statusBarHeight;
        C62091 c62091 = new FrameLayout(context) { // from class: org.telegram.ui.PremiumPreviewFragment.1
            private final Paint backgroundPaint = new Paint(1);
            boolean iconInterceptedTouch;
            int lastSize;
            boolean listInterceptedTouch;

            C62091(Context context2) {
                super(context2);
                this.backgroundPaint = new Paint(1);
            }

            @Override // android.view.ViewGroup, android.view.View
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                float x = PremiumPreviewFragment.this.backgroundView.getX() + PremiumPreviewFragment.this.backgroundView.imageFrameLayout.getX();
                float y = PremiumPreviewFragment.this.backgroundView.getY() + PremiumPreviewFragment.this.backgroundView.imageFrameLayout.getY();
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(x, y, (PremiumPreviewFragment.this.backgroundView.imageView == null ? 0 : PremiumPreviewFragment.this.backgroundView.imageView.getMeasuredWidth()) + x, (PremiumPreviewFragment.this.backgroundView.imageView == null ? 0 : PremiumPreviewFragment.this.backgroundView.imageView.getMeasuredHeight()) + y);
                if ((rectF.contains(motionEvent.getX(), motionEvent.getY()) || this.iconInterceptedTouch) && !PremiumPreviewFragment.this.listView.scrollingByUser) {
                    motionEvent.offsetLocation(-x, -y);
                    if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
                        this.iconInterceptedTouch = true;
                    } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                        this.iconInterceptedTouch = false;
                    }
                    PremiumPreviewFragment.this.backgroundView.imageView.dispatchTouchEvent(motionEvent);
                    return true;
                }
                float x2 = PremiumPreviewFragment.this.backgroundView.getX() + PremiumPreviewFragment.this.backgroundView.tierListView.getX();
                float y2 = PremiumPreviewFragment.this.backgroundView.getY() + PremiumPreviewFragment.this.backgroundView.tierListView.getY();
                rectF.set(x2, y2, PremiumPreviewFragment.this.backgroundView.tierListView.getWidth() + x2, PremiumPreviewFragment.this.backgroundView.tierListView.getHeight() + y2);
                if (PremiumPreviewFragment.this.progressToFull < 1.0f && ((rectF.contains(motionEvent.getX(), motionEvent.getY()) || this.listInterceptedTouch) && !PremiumPreviewFragment.this.listView.scrollingByUser)) {
                    motionEvent.offsetLocation(-x2, -y2);
                    if (motionEvent.getAction() == 0) {
                        this.listInterceptedTouch = true;
                    } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                        this.listInterceptedTouch = false;
                    }
                    PremiumPreviewFragment.this.backgroundView.tierListView.dispatchTouchEvent(motionEvent);
                    if (this.listInterceptedTouch) {
                        return true;
                    }
                }
                return super.dispatchTouchEvent(motionEvent);
            }

            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i3, int i4) {
                int iM1081dp = 0;
                if (View.MeasureSpec.getSize(i3) > View.MeasureSpec.getSize(i4)) {
                    PremiumPreviewFragment.this.isLandscapeMode = true;
                } else {
                    PremiumPreviewFragment.this.isLandscapeMode = false;
                }
                PremiumPreviewFragment.this.statusBarHeight = AndroidUtilities.isTablet() ? 0 : AndroidUtilities.statusBarHeight;
                PremiumPreviewFragment.this.backgroundView.measure(i3, View.MeasureSpec.makeMeasureSpec(0, 0));
                PremiumPreviewFragment.this.particlesView.getLayoutParams().height = PremiumPreviewFragment.this.backgroundView.getMeasuredHeight();
                if (PremiumPreviewFragment.this.buttonContainer != null && PremiumPreviewFragment.this.buttonContainer.getVisibility() != 8) {
                    iM1081dp = AndroidUtilities.m1081dp(68.0f);
                }
                PremiumPreviewFragment premiumPreviewFragment = PremiumPreviewFragment.this;
                premiumPreviewFragment.layoutManager.setAdditionalHeight((premiumPreviewFragment.statusBarHeight + iM1081dp) - AndroidUtilities.m1081dp(16.0f));
                PremiumPreviewFragment.this.layoutManager.setMinimumLastViewHeight(iM1081dp);
                super.onMeasure(i3, i4);
                if (this.lastSize != ((getMeasuredHeight() + getMeasuredWidth()) << 16)) {
                    PremiumPreviewFragment.this.updateBackgroundImage();
                }
            }

            @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
            protected void onLayout(boolean z, int i3, int i4, int i5, int i6) {
                super.onLayout(z, i3, i4, i5, i6);
                PremiumPreviewFragment.this.backgroundView.imageView.mRenderer.gradientScaleX = PremiumPreviewFragment.this.backgroundView.imageView.getMeasuredWidth() / getMeasuredWidth();
                PremiumPreviewFragment.this.backgroundView.imageView.mRenderer.gradientScaleY = PremiumPreviewFragment.this.backgroundView.imageView.getMeasuredHeight() / getMeasuredHeight();
                PremiumPreviewFragment.this.backgroundView.imageView.mRenderer.gradientStartX = (PremiumPreviewFragment.this.backgroundView.getX() + PremiumPreviewFragment.this.backgroundView.imageView.getX()) / getMeasuredWidth();
                PremiumPreviewFragment.this.backgroundView.imageView.mRenderer.gradientStartY = (PremiumPreviewFragment.this.backgroundView.getY() + PremiumPreviewFragment.this.backgroundView.imageView.getY()) / getMeasuredHeight();
            }

            @Override // android.view.View
            protected void onSizeChanged(int i3, int i4, int i5, int i6) {
                super.onSizeChanged(i3, i4, i5, i6);
                PremiumPreviewFragment.this.measureGradient(i3, i4);
            }

            @Override // android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                if (!PremiumPreviewFragment.this.isDialogVisible) {
                    PremiumPreviewFragment premiumPreviewFragment = PremiumPreviewFragment.this;
                    if (premiumPreviewFragment.inc) {
                        float f = premiumPreviewFragment.progress + 0.016f;
                        premiumPreviewFragment.progress = f;
                        if (f > 3.0f) {
                            premiumPreviewFragment.inc = false;
                        }
                    } else {
                        float f2 = premiumPreviewFragment.progress - 0.016f;
                        premiumPreviewFragment.progress = f2;
                        if (f2 < 1.0f) {
                            premiumPreviewFragment.inc = true;
                        }
                    }
                }
                View viewFindViewByPosition = PremiumPreviewFragment.this.listView.getLayoutManager() != null ? PremiumPreviewFragment.this.listView.getLayoutManager().findViewByPosition(0) : null;
                PremiumPreviewFragment.this.currentYOffset = viewFindViewByPosition != null ? viewFindViewByPosition.getBottom() : 0;
                int bottom = ((BaseFragment) PremiumPreviewFragment.this).actionBar.getBottom() + AndroidUtilities.m1081dp(16.0f);
                PremiumPreviewFragment.this.totalProgress = 1.0f - ((r5.currentYOffset - bottom) / (PremiumPreviewFragment.this.firstViewHeight - bottom));
                PremiumPreviewFragment premiumPreviewFragment2 = PremiumPreviewFragment.this;
                premiumPreviewFragment2.totalProgress = Utilities.clamp(premiumPreviewFragment2.totalProgress, 1.0f, 0.0f);
                int bottom2 = ((BaseFragment) PremiumPreviewFragment.this).actionBar.getBottom() + AndroidUtilities.m1081dp(16.0f);
                if (PremiumPreviewFragment.this.currentYOffset < bottom2) {
                    PremiumPreviewFragment.this.currentYOffset = bottom2;
                }
                PremiumPreviewFragment premiumPreviewFragment3 = PremiumPreviewFragment.this;
                float f3 = premiumPreviewFragment3.progressToFull;
                premiumPreviewFragment3.progressToFull = 0.0f;
                if (premiumPreviewFragment3.currentYOffset < AndroidUtilities.m1081dp(30.0f) + bottom2) {
                    PremiumPreviewFragment.this.progressToFull = ((bottom2 + AndroidUtilities.m1081dp(30.0f)) - PremiumPreviewFragment.this.currentYOffset) / AndroidUtilities.m1081dp(30.0f);
                }
                PremiumPreviewFragment premiumPreviewFragment4 = PremiumPreviewFragment.this;
                if (premiumPreviewFragment4.isLandscapeMode) {
                    premiumPreviewFragment4.progressToFull = 1.0f;
                    premiumPreviewFragment4.totalProgress = 1.0f;
                }
                if (f3 != premiumPreviewFragment4.progressToFull) {
                    premiumPreviewFragment4.listView.invalidate();
                }
                float fMax = Math.max((((((((BaseFragment) PremiumPreviewFragment.this).actionBar.getMeasuredHeight() - PremiumPreviewFragment.this.statusBarHeight) - PremiumPreviewFragment.this.backgroundView.titleView.getMeasuredHeight()) / 2.0f) + PremiumPreviewFragment.this.statusBarHeight) - PremiumPreviewFragment.this.backgroundView.getTop()) - PremiumPreviewFragment.this.backgroundView.titleView.getTop(), (PremiumPreviewFragment.this.currentYOffset - ((((BaseFragment) PremiumPreviewFragment.this).actionBar.getMeasuredHeight() + PremiumPreviewFragment.this.backgroundView.getMeasuredHeight()) - PremiumPreviewFragment.this.statusBarHeight)) + AndroidUtilities.m1081dp(PremiumPreviewFragment.this.backgroundView.tierListView.getVisibility() == 0 ? 24.0f : 16.0f));
                float fM1081dp = ((-fMax) / 4.0f) + AndroidUtilities.m1081dp(16.0f);
                PremiumPreviewFragment.this.backgroundView.setTranslationY(fMax);
                PremiumPreviewFragment.this.backgroundView.imageView.setTranslationY(fM1081dp + AndroidUtilities.m1081dp(PremiumPreviewFragment.this.type == 1 ? 9.0f : 16.0f));
                PremiumPreviewFragment premiumPreviewFragment5 = PremiumPreviewFragment.this;
                float f4 = premiumPreviewFragment5.totalProgress;
                float f5 = ((1.0f - f4) * 0.4f) + 0.6f;
                float f6 = 1.0f - (f4 > 0.5f ? (f4 - 0.5f) / 0.5f : 0.0f);
                premiumPreviewFragment5.backgroundView.imageView.setScaleX(f5);
                PremiumPreviewFragment.this.backgroundView.imageView.setScaleY(f5);
                PremiumPreviewFragment.this.backgroundView.imageView.setAlpha(f6);
                PremiumPreviewFragment.this.backgroundView.subtitleView.setAlpha(f6);
                PremiumPreviewFragment.this.backgroundView.tierListView.setAlpha(f6);
                PremiumPreviewFragment premiumPreviewFragment6 = PremiumPreviewFragment.this;
                premiumPreviewFragment6.particlesView.setAlpha(1.0f - premiumPreviewFragment6.totalProgress);
                PremiumPreviewFragment.this.particlesView.setTranslationY(((-(r1.getMeasuredHeight() - PremiumPreviewFragment.this.backgroundView.imageView.getMeasuredWidth())) / 2.0f) + PremiumPreviewFragment.this.backgroundView.getY() + PremiumPreviewFragment.this.backgroundView.imageFrameLayout.getY());
                float fM1081dp2 = AndroidUtilities.m1081dp(72.0f) - PremiumPreviewFragment.this.backgroundView.titleView.getLeft();
                PremiumPreviewFragment premiumPreviewFragment7 = PremiumPreviewFragment.this;
                float f7 = premiumPreviewFragment7.totalProgress;
                premiumPreviewFragment7.backgroundView.titleView.setTranslationX(fM1081dp2 * (1.0f - CubicBezierInterpolator.EASE_OUT_QUINT.getInterpolation(1.0f - (f7 > 0.3f ? (f7 - 0.3f) / 0.7f : 0.0f))));
                PremiumPreviewFragment.this.backgroundView.imageView.mRenderer.gradientStartX = ((PremiumPreviewFragment.this.backgroundView.getX() + PremiumPreviewFragment.this.backgroundView.imageFrameLayout.getX()) + ((getMeasuredWidth() * 0.1f) * PremiumPreviewFragment.this.progress)) / getMeasuredWidth();
                PremiumPreviewFragment.this.backgroundView.imageView.mRenderer.gradientStartY = (PremiumPreviewFragment.this.backgroundView.getY() + PremiumPreviewFragment.this.backgroundView.imageFrameLayout.getY()) / getMeasuredHeight();
                if (!PremiumPreviewFragment.this.isDialogVisible) {
                    invalidate();
                }
                PremiumPreviewFragment.this.gradientTools.gradientMatrix(0, 0, getMeasuredWidth(), getMeasuredHeight(), (-getMeasuredWidth()) * 0.1f * PremiumPreviewFragment.this.progress, 0.0f);
                if (PremiumPreviewFragment.this.whiteBackground) {
                    Paint paint = this.backgroundPaint;
                    PremiumPreviewFragment premiumPreviewFragment8 = PremiumPreviewFragment.this;
                    int i3 = Theme.key_windowBackgroundGray;
                    paint.setColor(premiumPreviewFragment8.getThemedColor(i3));
                    canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), this.backgroundPaint);
                    PremiumPreviewFragment premiumPreviewFragment9 = PremiumPreviewFragment.this;
                    if (premiumPreviewFragment9.progressToFull > 0.0f && ((BaseFragment) premiumPreviewFragment9).actionBar != null) {
                        this.backgroundPaint.setColor(ColorUtils.blendARGB(PremiumPreviewFragment.this.getThemedColor(i3), PremiumPreviewFragment.this.getThemedColor(Theme.key_windowBackgroundWhite), PremiumPreviewFragment.this.progressToFull));
                        canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), ((BaseFragment) PremiumPreviewFragment.this).actionBar.getHeight(), this.backgroundPaint);
                    }
                } else {
                    canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), PremiumPreviewFragment.this.gradientTools.paint);
                }
                super.dispatchDraw(canvas);
                if (((BaseFragment) PremiumPreviewFragment.this).parentLayout == null || !PremiumPreviewFragment.this.whiteBackground) {
                    return;
                }
                INavigationLayout iNavigationLayout = ((BaseFragment) PremiumPreviewFragment.this).parentLayout;
                PremiumPreviewFragment premiumPreviewFragment10 = PremiumPreviewFragment.this;
                iNavigationLayout.drawHeaderShadow(canvas, (int) (premiumPreviewFragment10.progressToFull * 255.0f), ((BaseFragment) premiumPreviewFragment10).actionBar.getBottom());
            }

            @Override // android.view.ViewGroup
            protected boolean drawChild(Canvas canvas, View view, long j) {
                if (view == PremiumPreviewFragment.this.listView) {
                    canvas.save();
                    canvas.clipRect(0, ((BaseFragment) PremiumPreviewFragment.this).actionBar.getBottom(), getMeasuredWidth(), getMeasuredHeight());
                    super.drawChild(canvas, view, j);
                    canvas.restore();
                    return true;
                }
                return super.drawChild(canvas, view, j);
            }
        };
        this.contentView = c62091;
        c62091.setFitsSystemWindows(true);
        RecyclerListView recyclerListView = new RecyclerListView(context2);
        this.listView = recyclerListView;
        recyclerListView.setSections(true);
        this.listView.setClipToPadding(false);
        this.listView.setPadding(0, AndroidUtilities.statusBarHeight + ActionBar.getCurrentActionBarHeight(), 0, 0);
        RecyclerListView recyclerListView2 = this.listView;
        FillLastLinearLayoutManager fillLastLinearLayoutManager = new FillLastLinearLayoutManager(context2, (AndroidUtilities.m1081dp(68.0f) + this.statusBarHeight) - AndroidUtilities.m1081dp(16.0f), this.listView);
        this.layoutManager = fillLastLinearLayoutManager;
        recyclerListView2.setLayoutManager(fillLastLinearLayoutManager);
        this.layoutManager.setFixedLastItemHeight();
        this.listView.setAdapter(new Adapter());
        this.listView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.PremiumPreviewFragment.2
            C62102() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i3) {
                super.onScrollStateChanged(recyclerView, i3);
                if (i3 == 0) {
                    int bottom = ((BaseFragment) PremiumPreviewFragment.this).actionBar.getBottom() + AndroidUtilities.m1081dp(16.0f);
                    PremiumPreviewFragment premiumPreviewFragment = PremiumPreviewFragment.this;
                    if (premiumPreviewFragment.totalProgress > 0.5f) {
                        premiumPreviewFragment.listView.smoothScrollBy(0, premiumPreviewFragment.currentYOffset - bottom);
                    } else {
                        View viewFindViewByPosition = premiumPreviewFragment.listView.getLayoutManager() != null ? PremiumPreviewFragment.this.listView.getLayoutManager().findViewByPosition(0) : null;
                        if (viewFindViewByPosition != null && viewFindViewByPosition.getTop() < 0) {
                            PremiumPreviewFragment.this.listView.smoothScrollBy(0, viewFindViewByPosition.getTop());
                        }
                    }
                }
                PremiumPreviewFragment.this.checkButtonDivider();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i3, int i4) {
                super.onScrolled(recyclerView, i3, i4);
                PremiumPreviewFragment.this.contentView.invalidate();
                PremiumPreviewFragment.this.checkButtonDivider();
            }
        });
        this.backgroundView = new BackgroundView(context2) { // from class: org.telegram.ui.PremiumPreviewFragment.3
            @Override // android.view.ViewGroup
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                return true;
            }

            C62113(Context context2) {
                super(context2);
            }
        };
        StarParticlesView starParticlesView = new StarParticlesView(context2);
        this.particlesView = starParticlesView;
        starParticlesView.setClipWithGradient();
        if (this.type == 1) {
            if (this.whiteBackground) {
                StarParticlesView.Drawable drawable = this.particlesView.drawable;
                drawable.useGradient = true;
                drawable.useBlur = false;
                drawable.checkBounds = true;
                drawable.isCircle = true;
                drawable.centerOffsetY = AndroidUtilities.m1081dp(-14.0f);
                StarParticlesView.Drawable drawable2 = this.particlesView.drawable;
                drawable2.minLifeTime = 2000L;
                drawable2.randLifeTime = 3000;
                drawable2.size1 = 16;
                drawable2.useRotate = false;
                drawable2.type = 28;
                drawable2.colorKey = i;
            } else {
                StarParticlesView.Drawable drawable3 = this.particlesView.drawable;
                drawable3.isCircle = true;
                drawable3.centerOffsetY = AndroidUtilities.m1081dp(28.0f);
                StarParticlesView.Drawable drawable4 = this.particlesView.drawable;
                drawable4.minLifeTime = 2000L;
                drawable4.randLifeTime = 3000;
                drawable4.size1 = 16;
                drawable4.useRotate = false;
                drawable4.type = 28;
            }
        }
        this.backgroundView.imageView.setStarParticlesView(this.particlesView);
        this.contentView.addView(this.particlesView, LayoutHelper.createFrame(-1, -2.0f));
        this.contentView.addView(this.backgroundView, LayoutHelper.createFrame(-1, -2.0f));
        this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda4
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i3) {
                this.f$0.lambda$createView$3(view, i3);
            }
        });
        this.contentView.addView(this.listView);
        this.premiumButtonView = new PremiumButtonView(context2, false, getResourceProvider());
        updateButtonText(false);
        this.buttonContainer = new FrameLayout(context2);
        View view = new View(context2);
        this.buttonDivider = view;
        view.setBackgroundColor(Theme.getColor(Theme.key_divider));
        this.buttonContainer.addView(this.buttonDivider, LayoutHelper.createFrame(-1, 1.0f));
        this.buttonDivider.getLayoutParams().height = 1;
        AndroidUtilities.updateViewVisibilityAnimated(this.buttonDivider, true, 1.0f, false);
        this.buttonContainer.addView(this.premiumButtonView, LayoutHelper.createFrame(-1, 48.0f, 16, 16.0f, 0.0f, 16.0f, 0.0f));
        this.buttonContainer.setBackgroundColor(getThemedColor(i2));
        if (getUserConfig().isClientActivated()) {
            this.contentView.addView(this.buttonContainer, LayoutHelper.createFrame(-1, 68, 80));
        }
        this.fragmentView = this.contentView;
        this.actionBar.setBackground(null);
        this.actionBar.setCastShadows(false);
        this.actionBar.setBackButtonImage(C2702R.drawable.ic_ab_back);
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.PremiumPreviewFragment.4
            C62124() {
            }

            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i3) {
                if (i3 == -1) {
                    PremiumPreviewFragment.this.finishFragment();
                }
            }
        });
        this.actionBar.setForceSkipTouches(true);
        updateColors();
        updateRows();
        this.backgroundView.imageView.startEnterAnimation(-180, 200L);
        if (this.forcePremium) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createView$4();
                }
            }, 400L);
        }
        MediaDataController.getInstance(this.currentAccount).preloadPremiumPreviewStickers();
        sentShowScreenStat(this.source);
        return this.fragmentView;
    }

    /* JADX INFO: renamed from: org.telegram.ui.PremiumPreviewFragment$1 */
    /* JADX INFO: loaded from: classes6.dex */
    class C62091 extends FrameLayout {
        private final Paint backgroundPaint = new Paint(1);
        boolean iconInterceptedTouch;
        int lastSize;
        boolean listInterceptedTouch;

        C62091(Context context2) {
            super(context2);
            this.backgroundPaint = new Paint(1);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            float x = PremiumPreviewFragment.this.backgroundView.getX() + PremiumPreviewFragment.this.backgroundView.imageFrameLayout.getX();
            float y = PremiumPreviewFragment.this.backgroundView.getY() + PremiumPreviewFragment.this.backgroundView.imageFrameLayout.getY();
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(x, y, (PremiumPreviewFragment.this.backgroundView.imageView == null ? 0 : PremiumPreviewFragment.this.backgroundView.imageView.getMeasuredWidth()) + x, (PremiumPreviewFragment.this.backgroundView.imageView == null ? 0 : PremiumPreviewFragment.this.backgroundView.imageView.getMeasuredHeight()) + y);
            if ((rectF.contains(motionEvent.getX(), motionEvent.getY()) || this.iconInterceptedTouch) && !PremiumPreviewFragment.this.listView.scrollingByUser) {
                motionEvent.offsetLocation(-x, -y);
                if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
                    this.iconInterceptedTouch = true;
                } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                    this.iconInterceptedTouch = false;
                }
                PremiumPreviewFragment.this.backgroundView.imageView.dispatchTouchEvent(motionEvent);
                return true;
            }
            float x2 = PremiumPreviewFragment.this.backgroundView.getX() + PremiumPreviewFragment.this.backgroundView.tierListView.getX();
            float y2 = PremiumPreviewFragment.this.backgroundView.getY() + PremiumPreviewFragment.this.backgroundView.tierListView.getY();
            rectF.set(x2, y2, PremiumPreviewFragment.this.backgroundView.tierListView.getWidth() + x2, PremiumPreviewFragment.this.backgroundView.tierListView.getHeight() + y2);
            if (PremiumPreviewFragment.this.progressToFull < 1.0f && ((rectF.contains(motionEvent.getX(), motionEvent.getY()) || this.listInterceptedTouch) && !PremiumPreviewFragment.this.listView.scrollingByUser)) {
                motionEvent.offsetLocation(-x2, -y2);
                if (motionEvent.getAction() == 0) {
                    this.listInterceptedTouch = true;
                } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                    this.listInterceptedTouch = false;
                }
                PremiumPreviewFragment.this.backgroundView.tierListView.dispatchTouchEvent(motionEvent);
                if (this.listInterceptedTouch) {
                    return true;
                }
            }
            return super.dispatchTouchEvent(motionEvent);
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i3, int i4) {
            int iM1081dp = 0;
            if (View.MeasureSpec.getSize(i3) > View.MeasureSpec.getSize(i4)) {
                PremiumPreviewFragment.this.isLandscapeMode = true;
            } else {
                PremiumPreviewFragment.this.isLandscapeMode = false;
            }
            PremiumPreviewFragment.this.statusBarHeight = AndroidUtilities.isTablet() ? 0 : AndroidUtilities.statusBarHeight;
            PremiumPreviewFragment.this.backgroundView.measure(i3, View.MeasureSpec.makeMeasureSpec(0, 0));
            PremiumPreviewFragment.this.particlesView.getLayoutParams().height = PremiumPreviewFragment.this.backgroundView.getMeasuredHeight();
            if (PremiumPreviewFragment.this.buttonContainer != null && PremiumPreviewFragment.this.buttonContainer.getVisibility() != 8) {
                iM1081dp = AndroidUtilities.m1081dp(68.0f);
            }
            PremiumPreviewFragment premiumPreviewFragment = PremiumPreviewFragment.this;
            premiumPreviewFragment.layoutManager.setAdditionalHeight((premiumPreviewFragment.statusBarHeight + iM1081dp) - AndroidUtilities.m1081dp(16.0f));
            PremiumPreviewFragment.this.layoutManager.setMinimumLastViewHeight(iM1081dp);
            super.onMeasure(i3, i4);
            if (this.lastSize != ((getMeasuredHeight() + getMeasuredWidth()) << 16)) {
                PremiumPreviewFragment.this.updateBackgroundImage();
            }
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i3, int i4, int i5, int i6) {
            super.onLayout(z, i3, i4, i5, i6);
            PremiumPreviewFragment.this.backgroundView.imageView.mRenderer.gradientScaleX = PremiumPreviewFragment.this.backgroundView.imageView.getMeasuredWidth() / getMeasuredWidth();
            PremiumPreviewFragment.this.backgroundView.imageView.mRenderer.gradientScaleY = PremiumPreviewFragment.this.backgroundView.imageView.getMeasuredHeight() / getMeasuredHeight();
            PremiumPreviewFragment.this.backgroundView.imageView.mRenderer.gradientStartX = (PremiumPreviewFragment.this.backgroundView.getX() + PremiumPreviewFragment.this.backgroundView.imageView.getX()) / getMeasuredWidth();
            PremiumPreviewFragment.this.backgroundView.imageView.mRenderer.gradientStartY = (PremiumPreviewFragment.this.backgroundView.getY() + PremiumPreviewFragment.this.backgroundView.imageView.getY()) / getMeasuredHeight();
        }

        @Override // android.view.View
        protected void onSizeChanged(int i3, int i4, int i5, int i6) {
            super.onSizeChanged(i3, i4, i5, i6);
            PremiumPreviewFragment.this.measureGradient(i3, i4);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            if (!PremiumPreviewFragment.this.isDialogVisible) {
                PremiumPreviewFragment premiumPreviewFragment = PremiumPreviewFragment.this;
                if (premiumPreviewFragment.inc) {
                    float f = premiumPreviewFragment.progress + 0.016f;
                    premiumPreviewFragment.progress = f;
                    if (f > 3.0f) {
                        premiumPreviewFragment.inc = false;
                    }
                } else {
                    float f2 = premiumPreviewFragment.progress - 0.016f;
                    premiumPreviewFragment.progress = f2;
                    if (f2 < 1.0f) {
                        premiumPreviewFragment.inc = true;
                    }
                }
            }
            View viewFindViewByPosition = PremiumPreviewFragment.this.listView.getLayoutManager() != null ? PremiumPreviewFragment.this.listView.getLayoutManager().findViewByPosition(0) : null;
            PremiumPreviewFragment.this.currentYOffset = viewFindViewByPosition != null ? viewFindViewByPosition.getBottom() : 0;
            int bottom = ((BaseFragment) PremiumPreviewFragment.this).actionBar.getBottom() + AndroidUtilities.m1081dp(16.0f);
            PremiumPreviewFragment.this.totalProgress = 1.0f - ((r5.currentYOffset - bottom) / (PremiumPreviewFragment.this.firstViewHeight - bottom));
            PremiumPreviewFragment premiumPreviewFragment2 = PremiumPreviewFragment.this;
            premiumPreviewFragment2.totalProgress = Utilities.clamp(premiumPreviewFragment2.totalProgress, 1.0f, 0.0f);
            int bottom2 = ((BaseFragment) PremiumPreviewFragment.this).actionBar.getBottom() + AndroidUtilities.m1081dp(16.0f);
            if (PremiumPreviewFragment.this.currentYOffset < bottom2) {
                PremiumPreviewFragment.this.currentYOffset = bottom2;
            }
            PremiumPreviewFragment premiumPreviewFragment3 = PremiumPreviewFragment.this;
            float f3 = premiumPreviewFragment3.progressToFull;
            premiumPreviewFragment3.progressToFull = 0.0f;
            if (premiumPreviewFragment3.currentYOffset < AndroidUtilities.m1081dp(30.0f) + bottom2) {
                PremiumPreviewFragment.this.progressToFull = ((bottom2 + AndroidUtilities.m1081dp(30.0f)) - PremiumPreviewFragment.this.currentYOffset) / AndroidUtilities.m1081dp(30.0f);
            }
            PremiumPreviewFragment premiumPreviewFragment4 = PremiumPreviewFragment.this;
            if (premiumPreviewFragment4.isLandscapeMode) {
                premiumPreviewFragment4.progressToFull = 1.0f;
                premiumPreviewFragment4.totalProgress = 1.0f;
            }
            if (f3 != premiumPreviewFragment4.progressToFull) {
                premiumPreviewFragment4.listView.invalidate();
            }
            float fMax = Math.max((((((((BaseFragment) PremiumPreviewFragment.this).actionBar.getMeasuredHeight() - PremiumPreviewFragment.this.statusBarHeight) - PremiumPreviewFragment.this.backgroundView.titleView.getMeasuredHeight()) / 2.0f) + PremiumPreviewFragment.this.statusBarHeight) - PremiumPreviewFragment.this.backgroundView.getTop()) - PremiumPreviewFragment.this.backgroundView.titleView.getTop(), (PremiumPreviewFragment.this.currentYOffset - ((((BaseFragment) PremiumPreviewFragment.this).actionBar.getMeasuredHeight() + PremiumPreviewFragment.this.backgroundView.getMeasuredHeight()) - PremiumPreviewFragment.this.statusBarHeight)) + AndroidUtilities.m1081dp(PremiumPreviewFragment.this.backgroundView.tierListView.getVisibility() == 0 ? 24.0f : 16.0f));
            float fM1081dp = ((-fMax) / 4.0f) + AndroidUtilities.m1081dp(16.0f);
            PremiumPreviewFragment.this.backgroundView.setTranslationY(fMax);
            PremiumPreviewFragment.this.backgroundView.imageView.setTranslationY(fM1081dp + AndroidUtilities.m1081dp(PremiumPreviewFragment.this.type == 1 ? 9.0f : 16.0f));
            PremiumPreviewFragment premiumPreviewFragment5 = PremiumPreviewFragment.this;
            float f4 = premiumPreviewFragment5.totalProgress;
            float f5 = ((1.0f - f4) * 0.4f) + 0.6f;
            float f6 = 1.0f - (f4 > 0.5f ? (f4 - 0.5f) / 0.5f : 0.0f);
            premiumPreviewFragment5.backgroundView.imageView.setScaleX(f5);
            PremiumPreviewFragment.this.backgroundView.imageView.setScaleY(f5);
            PremiumPreviewFragment.this.backgroundView.imageView.setAlpha(f6);
            PremiumPreviewFragment.this.backgroundView.subtitleView.setAlpha(f6);
            PremiumPreviewFragment.this.backgroundView.tierListView.setAlpha(f6);
            PremiumPreviewFragment premiumPreviewFragment6 = PremiumPreviewFragment.this;
            premiumPreviewFragment6.particlesView.setAlpha(1.0f - premiumPreviewFragment6.totalProgress);
            PremiumPreviewFragment.this.particlesView.setTranslationY(((-(r1.getMeasuredHeight() - PremiumPreviewFragment.this.backgroundView.imageView.getMeasuredWidth())) / 2.0f) + PremiumPreviewFragment.this.backgroundView.getY() + PremiumPreviewFragment.this.backgroundView.imageFrameLayout.getY());
            float fM1081dp2 = AndroidUtilities.m1081dp(72.0f) - PremiumPreviewFragment.this.backgroundView.titleView.getLeft();
            PremiumPreviewFragment premiumPreviewFragment7 = PremiumPreviewFragment.this;
            float f7 = premiumPreviewFragment7.totalProgress;
            premiumPreviewFragment7.backgroundView.titleView.setTranslationX(fM1081dp2 * (1.0f - CubicBezierInterpolator.EASE_OUT_QUINT.getInterpolation(1.0f - (f7 > 0.3f ? (f7 - 0.3f) / 0.7f : 0.0f))));
            PremiumPreviewFragment.this.backgroundView.imageView.mRenderer.gradientStartX = ((PremiumPreviewFragment.this.backgroundView.getX() + PremiumPreviewFragment.this.backgroundView.imageFrameLayout.getX()) + ((getMeasuredWidth() * 0.1f) * PremiumPreviewFragment.this.progress)) / getMeasuredWidth();
            PremiumPreviewFragment.this.backgroundView.imageView.mRenderer.gradientStartY = (PremiumPreviewFragment.this.backgroundView.getY() + PremiumPreviewFragment.this.backgroundView.imageFrameLayout.getY()) / getMeasuredHeight();
            if (!PremiumPreviewFragment.this.isDialogVisible) {
                invalidate();
            }
            PremiumPreviewFragment.this.gradientTools.gradientMatrix(0, 0, getMeasuredWidth(), getMeasuredHeight(), (-getMeasuredWidth()) * 0.1f * PremiumPreviewFragment.this.progress, 0.0f);
            if (PremiumPreviewFragment.this.whiteBackground) {
                Paint paint = this.backgroundPaint;
                PremiumPreviewFragment premiumPreviewFragment8 = PremiumPreviewFragment.this;
                int i3 = Theme.key_windowBackgroundGray;
                paint.setColor(premiumPreviewFragment8.getThemedColor(i3));
                canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), this.backgroundPaint);
                PremiumPreviewFragment premiumPreviewFragment9 = PremiumPreviewFragment.this;
                if (premiumPreviewFragment9.progressToFull > 0.0f && ((BaseFragment) premiumPreviewFragment9).actionBar != null) {
                    this.backgroundPaint.setColor(ColorUtils.blendARGB(PremiumPreviewFragment.this.getThemedColor(i3), PremiumPreviewFragment.this.getThemedColor(Theme.key_windowBackgroundWhite), PremiumPreviewFragment.this.progressToFull));
                    canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), ((BaseFragment) PremiumPreviewFragment.this).actionBar.getHeight(), this.backgroundPaint);
                }
            } else {
                canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), PremiumPreviewFragment.this.gradientTools.paint);
            }
            super.dispatchDraw(canvas);
            if (((BaseFragment) PremiumPreviewFragment.this).parentLayout == null || !PremiumPreviewFragment.this.whiteBackground) {
                return;
            }
            INavigationLayout iNavigationLayout = ((BaseFragment) PremiumPreviewFragment.this).parentLayout;
            PremiumPreviewFragment premiumPreviewFragment10 = PremiumPreviewFragment.this;
            iNavigationLayout.drawHeaderShadow(canvas, (int) (premiumPreviewFragment10.progressToFull * 255.0f), ((BaseFragment) premiumPreviewFragment10).actionBar.getBottom());
        }

        @Override // android.view.ViewGroup
        protected boolean drawChild(Canvas canvas, View view, long j) {
            if (view == PremiumPreviewFragment.this.listView) {
                canvas.save();
                canvas.clipRect(0, ((BaseFragment) PremiumPreviewFragment.this).actionBar.getBottom(), getMeasuredWidth(), getMeasuredHeight());
                super.drawChild(canvas, view, j);
                canvas.restore();
                return true;
            }
            return super.drawChild(canvas, view, j);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.PremiumPreviewFragment$2 */
    /* JADX INFO: loaded from: classes6.dex */
    class C62102 extends RecyclerView.OnScrollListener {
        C62102() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i3) {
            super.onScrollStateChanged(recyclerView, i3);
            if (i3 == 0) {
                int bottom = ((BaseFragment) PremiumPreviewFragment.this).actionBar.getBottom() + AndroidUtilities.m1081dp(16.0f);
                PremiumPreviewFragment premiumPreviewFragment = PremiumPreviewFragment.this;
                if (premiumPreviewFragment.totalProgress > 0.5f) {
                    premiumPreviewFragment.listView.smoothScrollBy(0, premiumPreviewFragment.currentYOffset - bottom);
                } else {
                    View viewFindViewByPosition = premiumPreviewFragment.listView.getLayoutManager() != null ? PremiumPreviewFragment.this.listView.getLayoutManager().findViewByPosition(0) : null;
                    if (viewFindViewByPosition != null && viewFindViewByPosition.getTop() < 0) {
                        PremiumPreviewFragment.this.listView.smoothScrollBy(0, viewFindViewByPosition.getTop());
                    }
                }
            }
            PremiumPreviewFragment.this.checkButtonDivider();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i3, int i4) {
            super.onScrolled(recyclerView, i3, i4);
            PremiumPreviewFragment.this.contentView.invalidate();
            PremiumPreviewFragment.this.checkButtonDivider();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.PremiumPreviewFragment$3 */
    /* JADX INFO: loaded from: classes6.dex */
    class C62113 extends BackgroundView {
        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return true;
        }

        C62113(Context context2) {
            super(context2);
        }
    }

    public /* synthetic */ void lambda$createView$3(View view, int i) {
        if (getUserConfig().isClientActivated()) {
            if (i == this.showAdsRow) {
                TLRPC.UserFull userFull = getMessagesController().getUserFull(getUserConfig().getClientUserId());
                if (userFull != null) {
                    TextCell textCell = (TextCell) view;
                    textCell.setChecked(!textCell.isChecked());
                    userFull.sponsored_enabled = textCell.isChecked();
                    TL_account.toggleSponsoredMessages togglesponsoredmessages = new TL_account.toggleSponsoredMessages();
                    togglesponsoredmessages.enabled = userFull.sponsored_enabled;
                    getConnectionsManager().sendRequest(togglesponsoredmessages, new RequestDelegate() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda9
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                            this.f$0.lambda$createView$1(tLObject, tL_error);
                        }
                    });
                    getMessagesStorage().updateUserInfo(userFull, false);
                }
            } else if (view instanceof PremiumFeatureCell) {
                final PremiumFeatureCell premiumFeatureCell = (PremiumFeatureCell) view;
                SubscriptionTier subscriptionTier = null;
                if (this.type == 1 && getUserConfig().isPremium()) {
                    int i2 = premiumFeatureCell.data.type;
                    if (i2 == 29) {
                        presentFragment(new LocationActivity());
                        return;
                    }
                    if (i2 == 32) {
                        presentFragment(new GreetMessagesActivity());
                        return;
                    }
                    if (i2 == 33) {
                        presentFragment(new AwayMessagesActivity());
                        return;
                    }
                    if (i2 == 30) {
                        presentFragment(new OpeningHoursActivity());
                        return;
                    }
                    if (i2 == 34) {
                        presentFragment(new ChatbotsActivity());
                        return;
                    }
                    if (i2 == 31) {
                        presentFragment(new QuickRepliesActivity());
                        return;
                    }
                    if (i2 == 14) {
                        Bundle bundle = new Bundle();
                        bundle.putLong("dialog_id", UserConfig.getInstance(this.currentAccount).getClientUserId());
                        bundle.putInt("type", 1);
                        presentFragment(new MediaActivity(bundle, null));
                        return;
                    }
                    if (i2 == 12) {
                        showSelectStatusDialog(premiumFeatureCell, UserObject.getEmojiStatusDocumentId(getUserConfig().getCurrentUser()), new Utilities.Callback2() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda10
                            @Override // org.telegram.messenger.Utilities.Callback2
                            public final void run(Object obj, Object obj2) {
                                this.f$0.lambda$createView$2(premiumFeatureCell, (Long) obj, (Integer) obj2);
                            }
                        });
                        return;
                    }
                    if (i2 == 35) {
                        presentFragment(new FiltersSetupActivity().highlightTags());
                    } else if (i2 == 36) {
                        presentFragment(new BusinessIntroActivity());
                    } else if (i2 == 37) {
                        presentFragment(new BusinessLinksActivity());
                    }
                } else {
                    sentShowFeaturePreview(this.currentAccount, premiumFeatureCell.data.type);
                    int i3 = this.selectedTierIndex;
                    if (i3 >= 0 && i3 < this.subscriptionTiers.size()) {
                        subscriptionTier = (SubscriptionTier) this.subscriptionTiers.get(this.selectedTierIndex);
                    }
                    showDialog(new PremiumFeatureBottomSheet(this, getContext(), this.currentAccount, this.type == 1, premiumFeatureCell.data.type, false, subscriptionTier));
                }
            }
        }
    }

    public /* synthetic */ void lambda$createView$1(final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$0(tL_error, tLObject);
            }
        });
    }

    public /* synthetic */ void lambda$createView$0(TLRPC.TL_error tL_error, TLObject tLObject) {
        if (tL_error != null) {
            BulletinFactory.showError(tL_error);
        } else {
            if (tLObject instanceof TLRPC.TL_boolTrue) {
                return;
            }
            BulletinFactory.m1195of(this).createErrorBulletin(LocaleController.getString(C2702R.string.UnknownError)).show();
        }
    }

    public /* synthetic */ void lambda$createView$2(PremiumFeatureCell premiumFeatureCell, Long l, Integer num) {
        TLRPC.EmojiStatus tL_emojiStatusEmpty;
        if (l == null) {
            tL_emojiStatusEmpty = new TLRPC.TL_emojiStatusEmpty();
        } else {
            TLRPC.TL_emojiStatus tL_emojiStatus = new TLRPC.TL_emojiStatus();
            tL_emojiStatus.document_id = l.longValue();
            if (num != null) {
                tL_emojiStatus.flags |= 1;
                tL_emojiStatus.until = num.intValue();
            }
            tL_emojiStatusEmpty = tL_emojiStatus;
        }
        getMessagesController().updateEmojiStatus(tL_emojiStatusEmpty);
        premiumFeatureCell.setEmoji(l == null ? 0L : l.longValue(), true);
    }

    /* JADX INFO: renamed from: org.telegram.ui.PremiumPreviewFragment$4 */
    /* JADX INFO: loaded from: classes6.dex */
    class C62124 extends ActionBar.ActionBarMenuOnItemClick {
        C62124() {
        }

        @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
        public void onItemClick(int i3) {
            if (i3 == -1) {
                PremiumPreviewFragment.this.finishFragment();
            }
        }
    }

    public /* synthetic */ void lambda$createView$4() {
        getMediaDataController().loadPremiumPromo(false);
    }

    public static void buyPremium(BaseFragment baseFragment) throws Throwable {
        buyPremium(baseFragment, "settings");
    }

    public static void fillPremiumFeaturesList(ArrayList arrayList, int i, boolean z) {
        final MessagesController messagesController = MessagesController.getInstance(i);
        int i2 = 0;
        arrayList.add(new PremiumFeatureData(0, C2702R.drawable.msg_premium_limits, LocaleController.getString(C2702R.string.PremiumPreviewLimits), LocaleController.formatString(C2702R.string.PremiumPreviewLimitsDescription, Integer.valueOf(messagesController.channelsLimitPremium), Integer.valueOf(messagesController.dialogFiltersLimitPremium), Integer.valueOf(messagesController.dialogFiltersPinnedLimitPremium), Integer.valueOf(messagesController.publicLinksLimitPremium), 4)));
        arrayList.add(new PremiumFeatureData(14, C2702R.drawable.msg_filled_stories, LocaleController.getString(C2702R.string.PremiumPreviewStories), LocaleController.formatString(C2702R.string.PremiumPreviewStoriesDescription, new Object[0])));
        arrayList.add(new PremiumFeatureData(1, C2702R.drawable.msg_premium_uploads, LocaleController.getString(C2702R.string.PremiumPreviewUploads), LocaleController.getString(C2702R.string.PremiumPreviewUploadsDescription)));
        arrayList.add(new PremiumFeatureData(2, C2702R.drawable.msg_premium_speed, LocaleController.getString(C2702R.string.PremiumPreviewDownloadSpeed), LocaleController.getString(C2702R.string.PremiumPreviewDownloadSpeedDescription)));
        arrayList.add(new PremiumFeatureData(8, C2702R.drawable.msg_premium_voice, LocaleController.getString(C2702R.string.PremiumPreviewVoiceToText), LocaleController.getString(C2702R.string.PremiumPreviewVoiceToTextDescription)));
        arrayList.add(new PremiumFeatureData(3, C2702R.drawable.msg_premium_ads, LocaleController.getString(C2702R.string.PremiumPreviewNoAds), LocaleController.getString(C2702R.string.PremiumPreviewNoAdsDescription)));
        arrayList.add(new PremiumFeatureData(4, C2702R.drawable.msg_premium_reactions, LocaleController.getString(C2702R.string.PremiumPreviewReactions2), LocaleController.getString(C2702R.string.PremiumPreviewReactions2Description)));
        arrayList.add(new PremiumFeatureData(5, C2702R.drawable.msg_premium_stickers, LocaleController.getString(C2702R.string.PremiumPreviewStickers), LocaleController.getString(C2702R.string.PremiumPreviewStickersDescription)));
        arrayList.add(new PremiumFeatureData(11, C2702R.drawable.msg_premium_emoji, LocaleController.getString(C2702R.string.PremiumPreviewEmoji), LocaleController.getString(C2702R.string.PremiumPreviewEmojiDescription)));
        arrayList.add(new PremiumFeatureData(9, C2702R.drawable.menu_premium_tools, LocaleController.getString(C2702R.string.PremiumPreviewAdvancedChatManagement), LocaleController.getString(C2702R.string.PremiumPreviewAdvancedChatManagementDescription)));
        arrayList.add(new PremiumFeatureData(6, C2702R.drawable.msg_premium_badge, LocaleController.getString(C2702R.string.PremiumPreviewProfileBadge), LocaleController.getString(C2702R.string.PremiumPreviewProfileBadgeDescription)));
        arrayList.add(new PremiumFeatureData(27, C2702R.drawable.filled_messages_paid, LocaleController.getString(C2702R.string.PremiumPreviewPaidMessages), LocaleController.getString(C2702R.string.PremiumPreviewPaidMessagesDescription)));
        arrayList.add(new PremiumFeatureData(7, C2702R.drawable.msg_premium_avatar, LocaleController.getString(C2702R.string.PremiumPreviewAnimatedProfiles), LocaleController.getString(C2702R.string.PremiumPreviewAnimatedProfilesDescription)));
        arrayList.add(new PremiumFeatureData(24, C2702R.drawable.premium_tags, LocaleController.getString(C2702R.string.PremiumPreviewTags2), LocaleController.getString(C2702R.string.PremiumPreviewTagsDescription2)));
        arrayList.add(new PremiumFeatureData(12, C2702R.drawable.premium_status, LocaleController.getString(C2702R.string.PremiumPreviewEmojiStatus), LocaleController.getString(C2702R.string.PremiumPreviewEmojiStatusDescription)));
        arrayList.add(new PremiumFeatureData(13, C2702R.drawable.msg_premium_translate, LocaleController.getString(C2702R.string.PremiumPreviewTranslations), LocaleController.getString(C2702R.string.PremiumPreviewTranslationsDescription)));
        arrayList.add(new PremiumFeatureData(22, C2702R.drawable.premium_wallpaper, LocaleController.getString(C2702R.string.PremiumPreviewWallpaper), LocaleController.getString(C2702R.string.PremiumPreviewWallpaperDescription)));
        arrayList.add(new PremiumFeatureData(23, C2702R.drawable.premium_colors, LocaleController.getString(C2702R.string.PremiumPreviewProfileColor), LocaleController.getString(C2702R.string.PremiumPreviewProfileColorDescription)));
        arrayList.add(new PremiumFeatureData(26, C2702R.drawable.menu_premium_seen, LocaleController.getString(C2702R.string.PremiumPreviewLastSeen), LocaleController.getString(C2702R.string.PremiumPreviewLastSeenDescription)));
        arrayList.add(new PremiumFeatureData(28, C2702R.drawable.filled_premium_business, LocaleController.getString(C2702R.string.TelegramBusiness), LocaleController.getString(C2702R.string.PremiumPreviewBusinessDescription)));
        arrayList.add(new PremiumFeatureData(38, C2702R.drawable.menu_premium_effects, LocaleController.getString(C2702R.string.PremiumPreviewEffects), LocaleController.getString(C2702R.string.PremiumPreviewEffectsDescription)));
        arrayList.add(new PremiumFeatureData(39, C2702R.drawable.msg_premium_icons, LocaleController.getString(C2702R.string.PremiumPreviewTodo), LocaleController.getString(C2702R.string.PremiumPreviewTodoDescription)));
        if (messagesController.premiumFeaturesTypesToPosition.size() > 0) {
            while (i2 < arrayList.size()) {
                if (messagesController.premiumFeaturesTypesToPosition.get(((PremiumFeatureData) arrayList.get(i2)).type, -1) == -1 && !BuildVars.DEBUG_PRIVATE_VERSION) {
                    arrayList.remove(i2);
                    i2--;
                }
                i2++;
            }
        }
        Collections.sort(arrayList, new Comparator() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda8
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return PremiumPreviewFragment.$r8$lambda$ZN2a9ikvKxxSRlwhDPfVOeqz8Ro(messagesController, (PremiumPreviewFragment.PremiumFeatureData) obj, (PremiumPreviewFragment.PremiumFeatureData) obj2);
            }
        });
    }

    public static /* synthetic */ int $r8$lambda$ZN2a9ikvKxxSRlwhDPfVOeqz8Ro(MessagesController messagesController, PremiumFeatureData premiumFeatureData, PremiumFeatureData premiumFeatureData2) {
        return messagesController.premiumFeaturesTypesToPosition.get(premiumFeatureData.type, Integer.MAX_VALUE) - messagesController.premiumFeaturesTypesToPosition.get(premiumFeatureData2.type, Integer.MAX_VALUE);
    }

    public static void fillBusinessFeaturesList(ArrayList arrayList, int i, boolean z) {
        final MessagesController messagesController = MessagesController.getInstance(i);
        if (!z) {
            arrayList.add(new PremiumFeatureData(29, C2702R.drawable.filled_location, LocaleController.getString(C2702R.string.PremiumBusinessLocation), LocaleController.getString(C2702R.string.PremiumBusinessLocationDescription)));
            arrayList.add(new PremiumFeatureData(30, C2702R.drawable.filled_premium_hours, LocaleController.getString(C2702R.string.PremiumBusinessOpeningHours), LocaleController.getString(C2702R.string.PremiumBusinessOpeningHoursDescription)));
            arrayList.add(new PremiumFeatureData(31, C2702R.drawable.filled_open_message, LocaleController.getString(C2702R.string.PremiumBusinessQuickReplies), LocaleController.getString(C2702R.string.PremiumBusinessQuickRepliesDescription)));
            arrayList.add(new PremiumFeatureData(32, C2702R.drawable.premium_status, LocaleController.getString(C2702R.string.PremiumBusinessGreetingMessages), LocaleController.getString(C2702R.string.PremiumBusinessGreetingMessagesDescription)));
            arrayList.add(new PremiumFeatureData(33, C2702R.drawable.filled_premium_away, LocaleController.getString(C2702R.string.PremiumBusinessAwayMessages), LocaleController.getString(C2702R.string.PremiumBusinessAwayMessagesDescription)));
            arrayList.add(new PremiumFeatureData(34, C2702R.drawable.filled_premium_bots, LocaleController.getString(C2702R.string.PremiumBusinessChatbots2), LocaleController.getString(C2702R.string.PremiumBusinessChatbotsDescription)));
            arrayList.add(new PremiumFeatureData(37, C2702R.drawable.filled_premium_chatlink, LocaleController.getString(C2702R.string.PremiumBusinessChatLinks), LocaleController.getString(C2702R.string.PremiumBusinessChatLinksDescription)));
            arrayList.add(new PremiumFeatureData(36, C2702R.drawable.filled_premium_intro, LocaleController.getString(C2702R.string.PremiumBusinessIntro), LocaleController.getString(C2702R.string.PremiumBusinessIntroDescription)));
        } else {
            arrayList.add(new PremiumFeatureData(12, C2702R.drawable.filled_premium_status2, LocaleController.getString(C2702R.string.PremiumPreviewBusinessEmojiStatus), LocaleController.getString(C2702R.string.PremiumPreviewBusinessEmojiStatusDescription)));
            arrayList.add(new PremiumFeatureData(35, C2702R.drawable.premium_tags, LocaleController.getString(C2702R.string.PremiumPreviewFolderTags), LocaleController.getString(C2702R.string.PremiumPreviewFolderTagsDescription)));
            arrayList.add(new PremiumFeatureData(14, C2702R.drawable.filled_premium_camera, LocaleController.getString(C2702R.string.PremiumPreviewBusinessStories), LocaleController.getString(C2702R.string.PremiumPreviewBusinessStoriesDescription)));
        }
        if (messagesController.businessFeaturesTypesToPosition.size() > 0) {
            int i2 = 0;
            while (i2 < arrayList.size()) {
                if (messagesController.businessFeaturesTypesToPosition.get(((PremiumFeatureData) arrayList.get(i2)).type, -1) == -1 && !BuildVars.DEBUG_VERSION) {
                    arrayList.remove(i2);
                    i2--;
                }
                i2++;
            }
        }
        Collections.sort(arrayList, new Comparator() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda7
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return PremiumPreviewFragment.$r8$lambda$QeBT0OdFclsxJ1hvdTNUoWUPg20(messagesController, (PremiumPreviewFragment.PremiumFeatureData) obj, (PremiumPreviewFragment.PremiumFeatureData) obj2);
            }
        });
    }

    public static /* synthetic */ int $r8$lambda$QeBT0OdFclsxJ1hvdTNUoWUPg20(MessagesController messagesController, PremiumFeatureData premiumFeatureData, PremiumFeatureData premiumFeatureData2) {
        return messagesController.businessFeaturesTypesToPosition.get(premiumFeatureData.type, Integer.MAX_VALUE) - messagesController.businessFeaturesTypesToPosition.get(premiumFeatureData2.type, Integer.MAX_VALUE);
    }

    public static CharSequence applyNewSpan(String str) {
        return applyNewSpan(str, -1);
    }

    public static CharSequence applyNewSpan(String str, int i) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        spannableStringBuilder.append((CharSequence) "  d");
        FilterCreateActivity.NewSpan newSpan = new FilterCreateActivity.NewSpan(false, i);
        newSpan.setColor(Theme.getColor(Theme.key_premiumGradient1));
        spannableStringBuilder.setSpan(newSpan, spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 0);
        return spannableStringBuilder;
    }

    public void updateBackgroundImage() {
        BackgroundView backgroundView;
        if (this.contentView.getMeasuredWidth() == 0 || this.contentView.getMeasuredHeight() == 0 || (backgroundView = this.backgroundView) == null || backgroundView.imageView == null) {
            return;
        }
        if (this.whiteBackground) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
            new Canvas(bitmapCreateBitmap).drawColor(ColorUtils.blendARGB(getThemedColor(Theme.key_premiumGradient2), getThemedColor(Theme.key_dialogBackground), 0.5f));
            this.backgroundView.imageView.setBackgroundBitmap(bitmapCreateBitmap);
        } else {
            this.gradientTools.gradientMatrix(0, 0, this.contentView.getMeasuredWidth(), this.contentView.getMeasuredHeight(), 0.0f, 0.0f);
            this.gradientCanvas.save();
            this.gradientCanvas.scale(100.0f / this.contentView.getMeasuredWidth(), 100.0f / this.contentView.getMeasuredHeight());
            this.gradientCanvas.drawRect(0.0f, 0.0f, this.contentView.getMeasuredWidth(), this.contentView.getMeasuredHeight(), this.gradientTools.paint);
            this.gradientCanvas.restore();
            this.backgroundView.imageView.setBackgroundBitmap(this.gradientTextureBitmap);
        }
    }

    public void checkButtonDivider() {
        AndroidUtilities.updateViewVisibilityAnimated(this.buttonDivider, this.listView.canScrollVertically(1), 1.0f, true);
    }

    public static void buyPremium(BaseFragment baseFragment, String str) throws Throwable {
        buyPremium(baseFragment, null, str, true);
    }

    public static void buyPremium(BaseFragment baseFragment, SubscriptionTier subscriptionTier, String str) {
        buyPremium(baseFragment, subscriptionTier, str, true);
    }

    public static void buyPremium(BaseFragment baseFragment, SubscriptionTier subscriptionTier, String str, boolean z) throws Throwable {
        buyPremium(baseFragment, subscriptionTier, str, z, null);
    }

    public static void buyPremium(final BaseFragment baseFragment, SubscriptionTier subscriptionTier, String str, boolean z, final BillingFlowParams.SubscriptionUpdateParams subscriptionUpdateParams) throws Throwable {
        final SubscriptionTier subscriptionTier2;
        final boolean z2;
        TLRPC.TL_premiumSubscriptionOption tL_premiumSubscriptionOption;
        String str2;
        final int currentAccount = baseFragment == null ? UserConfig.selectedAccount : baseFragment.getCurrentAccount();
        if (MessagesController.getInstance(currentAccount).isFrozen()) {
            AccountFrozenAlert.show(currentAccount);
            return;
        }
        if (subscriptionTier == null) {
            TLRPC.TL_help_premiumPromo premiumPromo = MediaDataController.getInstance(currentAccount).getPremiumPromo();
            if (premiumPromo != null) {
                ArrayList arrayList = premiumPromo.period_options;
                int size = arrayList.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    Object obj = arrayList.get(i);
                    i++;
                    TLRPC.TL_premiumSubscriptionOption tL_premiumSubscriptionOption2 = (TLRPC.TL_premiumSubscriptionOption) obj;
                    int i2 = tL_premiumSubscriptionOption2.months;
                    if (i2 == 1) {
                        subscriptionTier = new SubscriptionTier(tL_premiumSubscriptionOption2);
                    } else if (i2 == 12) {
                        subscriptionTier = new SubscriptionTier(tL_premiumSubscriptionOption2);
                        break;
                    }
                }
            }
            subscriptionTier2 = subscriptionTier;
            z2 = true;
        } else {
            subscriptionTier2 = subscriptionTier;
            z2 = z;
        }
        sentPremiumButtonClick();
        if (BuildVars.useInvoiceBilling()) {
            Activity parentActivity = baseFragment != null ? baseFragment.getParentActivity() : LaunchActivity.instance;
            if (parentActivity instanceof LaunchActivity) {
                LaunchActivity launchActivity = (LaunchActivity) parentActivity;
                if (subscriptionTier2 == null || (tL_premiumSubscriptionOption = subscriptionTier2.subscriptionOption) == null || (str2 = tL_premiumSubscriptionOption.bot_url) == null) {
                    MessagesController messagesController = MessagesController.getInstance(currentAccount);
                    if (!TextUtils.isEmpty(messagesController.premiumBotUsername)) {
                        launchActivity.setNavigateToPremiumBot(true);
                        launchActivity.onNewIntent(new Intent("android.intent.action.VIEW", Uri.parse("https://t.me/" + messagesController.premiumBotUsername + "?start=" + str)), null);
                        return;
                    }
                    if (TextUtils.isEmpty(messagesController.premiumInvoiceSlug)) {
                        return;
                    }
                    launchActivity.onNewIntent(new Intent("android.intent.action.VIEW", Uri.parse("https://t.me/$" + messagesController.premiumInvoiceSlug)), null);
                    return;
                }
                Uri uri = Uri.parse(str2);
                if (uri.getHost().equals("t.me") && !uri.getPath().startsWith("/$") && !uri.getPath().startsWith("/invoice/")) {
                    launchActivity.setNavigateToPremiumBot(true);
                }
                Browser.openUrl(launchActivity, subscriptionTier2.subscriptionOption.bot_url);
                return;
            }
            return;
        }
        ProductDetails productDetails = BillingController.PREMIUM_PRODUCT_DETAILS;
        if (productDetails == null || productDetails.getSubscriptionOfferDetails().isEmpty()) {
            return;
        }
        if (subscriptionTier2.getGooglePlayProductDetails() == null) {
            subscriptionTier2.setGooglePlayProductDetails(BillingController.PREMIUM_PRODUCT_DETAILS);
        }
        if (subscriptionTier2.getOfferDetails() == null) {
            return;
        }
        BillingController.getInstance().queryPurchases("subs", new PurchasesResponseListener() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda11
            @Override // com.android.billingclient.api.PurchasesResponseListener
            public final void onQueryPurchasesResponse(BillingResult billingResult, List list) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        PremiumPreviewFragment.$r8$lambda$ipHNRTy10Vgax2TWfdgw50B7YzQ(billingResult, baseFragment, z, list, i, subscriptionUpdateParams, subscriptionTier);
                    }
                });
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$ipHNRTy10Vgax2TWfdgw50B7YzQ(BillingResult billingResult, final BaseFragment baseFragment, final boolean z, List list, final int i, final BillingFlowParams.SubscriptionUpdateParams subscriptionUpdateParams, final SubscriptionTier subscriptionTier) {
        if (billingResult.getResponseCode() == 0) {
            final Runnable runnable = new Runnable() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    PremiumPreviewFragment.$r8$lambda$yIpvcgIMnnV05qi6Wy0XSCzF5AQ(baseFragment, z);
                }
            };
            if (list != null && !list.isEmpty() && !UserConfig.getInstance(i).isPremium()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    Purchase purchase = (Purchase) it.next();
                    if (purchase.getProducts().contains(BillingController.PREMIUM_PRODUCT_ID)) {
                        final TLRPC.TL_payments_assignPlayMarketTransaction tL_payments_assignPlayMarketTransaction = new TLRPC.TL_payments_assignPlayMarketTransaction();
                        TLRPC.TL_dataJSON tL_dataJSON = new TLRPC.TL_dataJSON();
                        tL_payments_assignPlayMarketTransaction.receipt = tL_dataJSON;
                        tL_dataJSON.data = purchase.getOriginalJson();
                        TLRPC.TL_inputStorePaymentPremiumSubscription tL_inputStorePaymentPremiumSubscription = new TLRPC.TL_inputStorePaymentPremiumSubscription();
                        tL_inputStorePaymentPremiumSubscription.restore = true;
                        if (subscriptionUpdateParams != null) {
                            tL_inputStorePaymentPremiumSubscription.upgrade = true;
                        }
                        tL_payments_assignPlayMarketTransaction.purpose = tL_inputStorePaymentPremiumSubscription;
                        ConnectionsManager.getInstance(i).sendRequest(tL_payments_assignPlayMarketTransaction, new RequestDelegate() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda18
                            @Override // org.telegram.tgnet.RequestDelegate
                            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                                PremiumPreviewFragment.$r8$lambda$Yl4bFqFkdSQfJWyDxSBa42mcuDQ(i, runnable, baseFragment, tL_payments_assignPlayMarketTransaction, tLObject, tL_error);
                            }
                        }, 66);
                        return;
                    }
                }
            }
            BillingController.getInstance().addResultListener(BillingController.PREMIUM_PRODUCT_ID, new Consumer() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda19
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    PremiumPreviewFragment.$r8$lambda$iQ6Pg9gLkBC7JhlQ881XtbHbjqY(runnable, (BillingResult) obj);
                }
            });
            final TLRPC.TL_payments_canPurchaseStore tL_payments_canPurchaseStore = new TLRPC.TL_payments_canPurchaseStore();
            final TLRPC.TL_inputStorePaymentPremiumSubscription tL_inputStorePaymentPremiumSubscription2 = new TLRPC.TL_inputStorePaymentPremiumSubscription();
            if (subscriptionUpdateParams != null) {
                tL_inputStorePaymentPremiumSubscription2.upgrade = true;
            }
            tL_payments_canPurchaseStore.purpose = tL_inputStorePaymentPremiumSubscription2;
            ConnectionsManager.getInstance(i).sendRequest(tL_payments_canPurchaseStore, new RequestDelegate() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda20
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda22
                        @Override // java.lang.Runnable
                        public final void run() {
                            PremiumPreviewFragment.$r8$lambda$JCJO4QmIlI1KwumqRov5olshBC0(tLObject, baseFragment, tL_inputStorePaymentPremiumSubscription, subscriptionTier, subscriptionUpdateParams, i, tL_error, tL_payments_canPurchaseStore);
                        }
                    });
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$yIpvcgIMnnV05qi6Wy0XSCzF5AQ(BaseFragment baseFragment, boolean z) {
        if (baseFragment instanceof PremiumPreviewFragment) {
            PremiumPreviewFragment premiumPreviewFragment = (PremiumPreviewFragment) baseFragment;
            if (z) {
                premiumPreviewFragment.setForcePremium();
            }
            premiumPreviewFragment.getMediaDataController().loadPremiumPromo(false);
            premiumPreviewFragment.listView.smoothScrollToPosition(0);
        } else {
            PremiumPreviewFragment premiumPreviewFragment2 = new PremiumPreviewFragment(null);
            if (z) {
                premiumPreviewFragment2.setForcePremium();
            }
            if (baseFragment != null) {
                baseFragment.presentFragment(premiumPreviewFragment2);
            } else {
                BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
                if (safeLastFragment != null) {
                    safeLastFragment.presentFragment(premiumPreviewFragment2);
                }
            }
        }
        if (baseFragment == null || !(baseFragment.getParentActivity() instanceof LaunchActivity)) {
            return;
        }
        try {
            baseFragment.getFragmentView().performHapticFeedback(3, 2);
        } catch (Exception unused) {
        }
        ((LaunchActivity) baseFragment.getParentActivity()).getFireworksOverlay().start();
    }

    public static /* synthetic */ void $r8$lambda$Yl4bFqFkdSQfJWyDxSBa42mcuDQ(final int i, Runnable runnable, final BaseFragment baseFragment, final TLRPC.TL_payments_assignPlayMarketTransaction tL_payments_assignPlayMarketTransaction, TLObject tLObject, final TLRPC.TL_error tL_error) {
        if (tLObject instanceof TLRPC.Updates) {
            MessagesController.getInstance(i).processUpdates((TLRPC.Updates) tLObject, false);
            AndroidUtilities.runOnUIThread(runnable);
        } else if (tL_error != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    AlertsCreator.processError(i, tL_error, baseFragment, tL_payments_assignPlayMarketTransaction, new Object[0]);
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$iQ6Pg9gLkBC7JhlQ881XtbHbjqY(Runnable runnable, BillingResult billingResult) {
        if (billingResult.getResponseCode() == 0) {
            AndroidUtilities.runOnUIThread(runnable);
        }
    }

    public static /* synthetic */ void $r8$lambda$JCJO4QmIlI1KwumqRov5olshBC0(TLObject tLObject, BaseFragment baseFragment, TLRPC.TL_inputStorePaymentPremiumSubscription tL_inputStorePaymentPremiumSubscription, SubscriptionTier subscriptionTier, BillingFlowParams.SubscriptionUpdateParams subscriptionUpdateParams, int i, TLRPC.TL_error tL_error, TLRPC.TL_payments_canPurchaseStore tL_payments_canPurchaseStore) {
        if (tLObject instanceof TLRPC.TL_boolTrue) {
            BillingController.getInstance().launchBillingFlow(baseFragment != null ? baseFragment.getParentActivity() : AndroidUtilities.getActivity(), baseFragment.getAccountInstance(), tL_inputStorePaymentPremiumSubscription, Collections.singletonList(BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(BillingController.PREMIUM_PRODUCT_DETAILS).setOfferToken(subscriptionTier.getOfferDetails().getOfferToken()).build()), subscriptionUpdateParams, false);
        } else {
            AlertsCreator.processError(i, tL_error, baseFragment, tL_payments_canPurchaseStore, new Object[0]);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static String getPremiumButtonText(int i, SubscriptionTier subscriptionTier) {
        int i2;
        String currency;
        if (BuildVars.IS_BILLING_UNAVAILABLE) {
            return LocaleController.getString(C2702R.string.SubscribeToPremiumNotAvailable);
        }
        int i3 = C2702R.string.SubscribeToPremium;
        if (subscriptionTier == null) {
            String formattedPrice = null;
            TLRPC.TL_premiumSubscriptionOption tL_premiumSubscriptionOption = null;
            formattedPrice = null;
            formattedPrice = null;
            if (BuildVars.useInvoiceBilling()) {
                TLRPC.TL_help_premiumPromo premiumPromo = MediaDataController.getInstance(i).getPremiumPromo();
                if (premiumPromo != null) {
                    ArrayList arrayList = premiumPromo.period_options;
                    int size = arrayList.size();
                    int i4 = 0;
                    while (true) {
                        if (i4 >= size) {
                            break;
                        }
                        Object obj = arrayList.get(i4);
                        i4++;
                        TLRPC.TL_premiumSubscriptionOption tL_premiumSubscriptionOption2 = (TLRPC.TL_premiumSubscriptionOption) obj;
                        int i5 = tL_premiumSubscriptionOption2.months;
                        if (i5 == 12) {
                            tL_premiumSubscriptionOption = tL_premiumSubscriptionOption2;
                            break;
                        }
                        if (tL_premiumSubscriptionOption == null && i5 == 1) {
                            tL_premiumSubscriptionOption = tL_premiumSubscriptionOption2;
                        }
                    }
                    if (tL_premiumSubscriptionOption == null) {
                        return LocaleController.getString(C2702R.string.SubscribeToPremiumNoPrice);
                    }
                    if (tL_premiumSubscriptionOption.months == 12) {
                        if (MessagesController.getInstance(i).showAnnualPerMonth) {
                            currency = BillingController.getInstance().formatCurrency(tL_premiumSubscriptionOption.amount / 12, tL_premiumSubscriptionOption.currency);
                        } else {
                            i3 = C2702R.string.SubscribeToPremiumPerYear;
                            currency = BillingController.getInstance().formatCurrency(tL_premiumSubscriptionOption.amount, tL_premiumSubscriptionOption.currency);
                        }
                    } else {
                        currency = BillingController.getInstance().formatCurrency(tL_premiumSubscriptionOption.amount, tL_premiumSubscriptionOption.currency);
                    }
                    return LocaleController.formatString(i3, currency);
                }
                return LocaleController.getString(C2702R.string.SubscribeToPremiumNoPrice);
            }
            ProductDetails productDetails = BillingController.PREMIUM_PRODUCT_DETAILS;
            if (productDetails != null) {
                List subscriptionOfferDetails = productDetails.getSubscriptionOfferDetails();
                if (!subscriptionOfferDetails.isEmpty()) {
                    Iterator it = ((ProductDetails.SubscriptionOfferDetails) subscriptionOfferDetails.get(0)).getPricingPhases().getPricingPhaseList().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        ProductDetails.PricingPhase pricingPhase = (ProductDetails.PricingPhase) it.next();
                        if (pricingPhase.getBillingPeriod().equals("P1M")) {
                            formattedPrice = pricingPhase.getFormattedPrice();
                        } else if (pricingPhase.getBillingPeriod().equals("P1Y")) {
                            if (MessagesController.getInstance(i).showAnnualPerMonth) {
                                formattedPrice = BillingController.getInstance().formatCurrency(pricingPhase.getPriceAmountMicros() / 12, pricingPhase.getPriceCurrencyCode(), 6);
                            } else {
                                i3 = C2702R.string.SubscribeToPremiumPerYear;
                                formattedPrice = BillingController.getInstance().formatCurrency(pricingPhase.getPriceAmountMicros(), pricingPhase.getPriceCurrencyCode(), 6);
                            }
                        }
                    }
                }
            }
            return formattedPrice == null ? LocaleController.getString(C2702R.string.Loading) : LocaleController.formatString(i3, formattedPrice);
        }
        if (!BuildVars.useInvoiceBilling() && subscriptionTier.getOfferDetails() == null) {
            return LocaleController.getString(C2702R.string.Loading);
        }
        boolean zIsPremium = UserConfig.getInstance(i).isPremium();
        byte b = subscriptionTier.getMonths() > 12 && subscriptionTier.getMonths() % 12 == 0;
        byte b2 = subscriptionTier.getMonths() == 12;
        String formattedPricePerYear = b2 != false ? subscriptionTier.getFormattedPricePerYear() : subscriptionTier.getFormattedPricePerMonth();
        if (zIsPremium) {
            i2 = b2 != false ? C2702R.string.UpgradePremiumPerYear : C2702R.string.UpgradePremiumPerMonth;
        } else if (b2 != false) {
            if (MessagesController.getInstance(i).showAnnualPerMonth) {
                i2 = C2702R.string.SubscribeToPremium;
                formattedPricePerYear = subscriptionTier.getFormattedPricePerMonth();
            } else {
                i2 = C2702R.string.SubscribeToPremiumPerYear;
                formattedPricePerYear = subscriptionTier.getFormattedPrice();
            }
        } else if (b == false || MessagesController.getInstance(i).showAnnualPerMonth) {
            i2 = C2702R.string.SubscribeToPremium;
            formattedPricePerYear = subscriptionTier.getFormattedPricePerMonth();
        } else {
            return LocaleController.formatString(C2702R.string.SubscribeToPremiumPerCustom, subscriptionTier.getFormattedPrice(), LocaleController.formatPluralString("Years", subscriptionTier.getMonths() / 12, new Object[0]));
        }
        return LocaleController.formatString(i2, formattedPricePerYear);
    }

    public void measureGradient(int i, int i2) {
        int measuredHeight = 0;
        for (int i3 = 0; i3 < this.premiumFeatures.size(); i3++) {
            this.dummyCell.setData((PremiumFeatureData) this.premiumFeatures.get(i3), false);
            this.dummyCell.measure(View.MeasureSpec.makeMeasureSpec(i, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE));
            ((PremiumFeatureData) this.premiumFeatures.get(i3)).yOffset = measuredHeight;
            measuredHeight += this.dummyCell.getMeasuredHeight();
        }
        for (int i4 = 0; i4 < this.morePremiumFeatures.size(); i4++) {
            this.dummyCell.setData((PremiumFeatureData) this.morePremiumFeatures.get(i4), false);
            this.dummyCell.measure(View.MeasureSpec.makeMeasureSpec(i, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE));
            ((PremiumFeatureData) this.morePremiumFeatures.get(i4)).yOffset = measuredHeight;
            measuredHeight += this.dummyCell.getMeasuredHeight();
        }
        this.totalGradientHeight = measuredHeight;
    }

    private void updateRows() {
        SubscriptionTier subscriptionTier;
        this.sectionRow = -1;
        this.privacyRow = -1;
        this.moreHeaderRow = -1;
        this.moreFeaturesStartRow = -1;
        this.moreFeaturesEndRow = -1;
        this.showAdsHeaderRow = -1;
        this.showAdsRow = -1;
        this.showAdsInfoRow = -1;
        boolean z = true;
        this.rowCount = 1;
        this.paddingRow = 0;
        this.featuresStartRow = 1;
        int size = this.premiumFeatures.size() + 1;
        this.rowCount = size;
        this.featuresEndRow = size;
        if (this.type == 1 && getUserConfig().isPremium()) {
            int i = this.rowCount;
            int i2 = i + 1;
            this.sectionRow = i;
            int i3 = i + 2;
            this.rowCount = i3;
            this.moreHeaderRow = i2;
            this.moreFeaturesStartRow = i3;
            int size2 = i3 + this.morePremiumFeatures.size();
            this.rowCount = size2;
            this.moreFeaturesEndRow = size2;
        }
        int i4 = this.rowCount;
        this.statusRow = i4;
        this.rowCount = i4 + 2;
        this.lastPaddingRow = i4 + 1;
        if (this.type == 1 && getUserConfig().isPremium()) {
            int i5 = this.rowCount;
            this.showAdsHeaderRow = i5;
            this.showAdsRow = i5 + 1;
            this.rowCount = i5 + 3;
            this.showAdsInfoRow = i5 + 2;
        }
        FrameLayout frameLayout = this.buttonContainer;
        if (getUserConfig().isPremium() && ((subscriptionTier = this.currentSubscriptionTier) == null || subscriptionTier.getMonths() >= ((SubscriptionTier) this.subscriptionTiers.get(this.selectedTierIndex)).getMonths() || this.forcePremium)) {
            z = false;
        }
        AndroidUtilities.updateViewVisibilityAnimated(frameLayout, z, 1.0f, false);
        int iM1081dp = this.buttonContainer.getVisibility() == 0 ? AndroidUtilities.m1081dp(64.0f) : 0;
        this.layoutManager.setAdditionalHeight((this.statusBarHeight + iM1081dp) - AndroidUtilities.m1081dp(16.0f));
        this.layoutManager.setMinimumLastViewHeight(iM1081dp);
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        if (getMessagesController().premiumFeaturesBlocked()) {
            return false;
        }
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.billingProductDetailsUpdated);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        getNotificationCenter().addObserver(this, NotificationCenter.premiumPromoUpdated);
        if (getMediaDataController().getPremiumPromo() != null) {
            ArrayList arrayList = getMediaDataController().getPremiumPromo().videos;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                FileLoader.getInstance(this.currentAccount).loadFile((TLRPC.Document) obj, getMediaDataController().getPremiumPromo(), 3, 0);
            }
        }
        if (this.type == 1) {
            TimezonesController.getInstance(this.currentAccount).load();
        }
        return super.onFragmentCreate();
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.billingProductDetailsUpdated);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        getNotificationCenter().removeObserver(this, NotificationCenter.premiumPromoUpdated);
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.billingProductDetailsUpdated || i == NotificationCenter.premiumPromoUpdated) {
            updateButtonText(false);
            this.backgroundView.updatePremiumTiers();
        }
        if (i == NotificationCenter.currentUserPremiumStatusChanged || i == NotificationCenter.premiumPromoUpdated) {
            this.backgroundView.updateText();
            this.backgroundView.updatePremiumTiers();
            updateRows();
            this.listView.getAdapter().notifyDataSetChanged();
        }
    }

    /* JADX INFO: loaded from: classes6.dex */
    class Adapter extends RecyclerListView.SelectionAdapter {
        /* synthetic */ Adapter(PremiumPreviewFragment premiumPreviewFragment, PremiumPreviewFragmentIA premiumPreviewFragmentIA) {
            this();
        }

        private Adapter() {
        }

        /* JADX INFO: renamed from: org.telegram.ui.PremiumPreviewFragment$Adapter$1 */
        class C62161 extends View {
            C62161(Context context) {
                super(context);
            }

            @Override // android.view.View
            protected void onMeasure(int i, int i2) {
                PremiumPreviewFragment premiumPreviewFragment = PremiumPreviewFragment.this;
                if (premiumPreviewFragment.isLandscapeMode) {
                    premiumPreviewFragment.firstViewHeight = 0;
                } else {
                    int iM1081dp = AndroidUtilities.m1081dp(64.0f);
                    if (PremiumPreviewFragment.this.backgroundView.getMeasuredHeight() + AndroidUtilities.m1081dp(8.0f) > iM1081dp) {
                        iM1081dp = PremiumPreviewFragment.this.backgroundView.getMeasuredHeight() + AndroidUtilities.m1081dp(8.0f);
                    }
                    PremiumPreviewFragment.this.firstViewHeight = iM1081dp;
                }
                super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(PremiumPreviewFragment.this.firstViewHeight, TLObject.FLAG_30));
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View c62172;
            Context context = viewGroup.getContext();
            switch (i) {
                case 1:
                    c62172 = new PremiumFeatureCell(context) { // from class: org.telegram.ui.PremiumPreviewFragment.Adapter.2
                        C62172(Context context2) {
                            super(context2);
                        }

                        @Override // org.telegram.p026ui.PremiumFeatureCell, android.view.ViewGroup, android.view.View
                        protected void dispatchDraw(Canvas canvas) {
                            float fM1081dp = AndroidUtilities.m1081dp(10.0f);
                            RectF rectF = AndroidUtilities.rectTmp;
                            rectF.set(this.imageView.getLeft(), this.imageView.getTop(), this.imageView.getRight(), this.imageView.getBottom());
                            PremiumPreviewFragment.this.matrix.reset();
                            PremiumPreviewFragment.this.matrix.postScale(1.0f, r2.totalGradientHeight / 100.0f, 0.0f, 0.0f);
                            PremiumPreviewFragment.this.matrix.postTranslate(0.0f, -this.data.yOffset);
                            PremiumPreviewFragment premiumPreviewFragment = PremiumPreviewFragment.this;
                            premiumPreviewFragment.shader.setLocalMatrix(premiumPreviewFragment.matrix);
                            canvas.drawRoundRect(rectF, fM1081dp, fM1081dp, PremiumPreviewFragment.this.gradientPaint);
                            if (((BaseFragment) PremiumPreviewFragment.this).resourceProvider != null ? ((BaseFragment) PremiumPreviewFragment.this).resourceProvider.isDark() : Theme.isCurrentThemeDark()) {
                                float fM1081dp2 = AndroidUtilities.m1081dp(1.0f);
                                PremiumPreviewFragment.this.strokePaint.setStrokeWidth(fM1081dp2);
                                canvas.save();
                                canvas.translate(rectF.left, rectF.top);
                                rectF.offset(-rectF.left, -rectF.top);
                                float f = fM1081dp2 / 2.0f;
                                rectF.inset(f, f);
                                canvas.drawRoundRect(rectF, fM1081dp, fM1081dp, PremiumPreviewFragment.this.strokePaint);
                                canvas.restore();
                            }
                            super.dispatchDraw(canvas);
                        }
                    };
                    break;
                case 2:
                    c62172 = new ShadowSectionCell(context2, 12, 0);
                    break;
                case 3:
                default:
                    c62172 = new View(context2) { // from class: org.telegram.ui.PremiumPreviewFragment.Adapter.1
                        C62161(Context context2) {
                            super(context2);
                        }

                        @Override // android.view.View
                        protected void onMeasure(int i2, int i22) {
                            PremiumPreviewFragment premiumPreviewFragment = PremiumPreviewFragment.this;
                            if (premiumPreviewFragment.isLandscapeMode) {
                                premiumPreviewFragment.firstViewHeight = 0;
                            } else {
                                int iM1081dp = AndroidUtilities.m1081dp(64.0f);
                                if (PremiumPreviewFragment.this.backgroundView.getMeasuredHeight() + AndroidUtilities.m1081dp(8.0f) > iM1081dp) {
                                    iM1081dp = PremiumPreviewFragment.this.backgroundView.getMeasuredHeight() + AndroidUtilities.m1081dp(8.0f);
                                }
                                PremiumPreviewFragment.this.firstViewHeight = iM1081dp;
                            }
                            super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(PremiumPreviewFragment.this.firstViewHeight, TLObject.FLAG_30));
                        }
                    };
                    c62172.setTag(-33024);
                    break;
                case 4:
                    c62172 = new AboutPremiumView(context2);
                    break;
                case 5:
                    c62172 = new TextInfoPrivacyCell(context2);
                    break;
                case 6:
                    c62172 = new View(context2);
                    c62172.setTag(-33024);
                    break;
                case 7:
                    c62172 = new HeaderCell(context2);
                    break;
                case 8:
                    c62172 = new TextCell(context2, 23, false, true, ((BaseFragment) PremiumPreviewFragment.this).resourceProvider);
                    break;
            }
            c62172.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            return new RecyclerListView.Holder(c62172);
        }

        /* JADX INFO: renamed from: org.telegram.ui.PremiumPreviewFragment$Adapter$2 */
        class C62172 extends PremiumFeatureCell {
            C62172(Context context2) {
                super(context2);
            }

            @Override // org.telegram.p026ui.PremiumFeatureCell, android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                float fM1081dp = AndroidUtilities.m1081dp(10.0f);
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(this.imageView.getLeft(), this.imageView.getTop(), this.imageView.getRight(), this.imageView.getBottom());
                PremiumPreviewFragment.this.matrix.reset();
                PremiumPreviewFragment.this.matrix.postScale(1.0f, r2.totalGradientHeight / 100.0f, 0.0f, 0.0f);
                PremiumPreviewFragment.this.matrix.postTranslate(0.0f, -this.data.yOffset);
                PremiumPreviewFragment premiumPreviewFragment = PremiumPreviewFragment.this;
                premiumPreviewFragment.shader.setLocalMatrix(premiumPreviewFragment.matrix);
                canvas.drawRoundRect(rectF, fM1081dp, fM1081dp, PremiumPreviewFragment.this.gradientPaint);
                if (((BaseFragment) PremiumPreviewFragment.this).resourceProvider != null ? ((BaseFragment) PremiumPreviewFragment.this).resourceProvider.isDark() : Theme.isCurrentThemeDark()) {
                    float fM1081dp2 = AndroidUtilities.m1081dp(1.0f);
                    PremiumPreviewFragment.this.strokePaint.setStrokeWidth(fM1081dp2);
                    canvas.save();
                    canvas.translate(rectF.left, rectF.top);
                    rectF.offset(-rectF.left, -rectF.top);
                    float f = fM1081dp2 / 2.0f;
                    rectF.inset(f, f);
                    canvas.drawRoundRect(rectF, fM1081dp, fM1081dp, PremiumPreviewFragment.this.strokePaint);
                    canvas.restore();
                }
                super.dispatchDraw(canvas);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:775:0x02fd  */
        /* JADX WARN: Removed duplicated region for block: B:782:0x0309 A[SYNTHETIC] */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r17, int r18) {
            /*
                Method dump skipped, instruction units count: 787
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.PremiumPreviewFragment.Adapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
        }

        public /* synthetic */ void lambda$onBindViewHolder$0() {
            PremiumPreviewFragment.this.showDialog(new RevenueSharingAdsInfoBottomSheet(PremiumPreviewFragment.this.getContext(), false, PremiumPreviewFragment.this.getResourceProvider(), null));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return PremiumPreviewFragment.this.rowCount;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            PremiumPreviewFragment premiumPreviewFragment = PremiumPreviewFragment.this;
            if (i == premiumPreviewFragment.paddingRow) {
                return 0;
            }
            if (i >= premiumPreviewFragment.featuresStartRow && i < premiumPreviewFragment.featuresEndRow) {
                return 1;
            }
            if (i >= premiumPreviewFragment.moreFeaturesStartRow && i < premiumPreviewFragment.moreFeaturesEndRow) {
                return 1;
            }
            if (i == premiumPreviewFragment.helpUsRow) {
                return 4;
            }
            if (i == premiumPreviewFragment.sectionRow || i == premiumPreviewFragment.statusRow || i == premiumPreviewFragment.privacyRow || i == premiumPreviewFragment.showAdsInfoRow) {
                return 5;
            }
            if (i == premiumPreviewFragment.lastPaddingRow) {
                return 6;
            }
            if (i == premiumPreviewFragment.moreHeaderRow || i == premiumPreviewFragment.showAdsHeaderRow) {
                return 7;
            }
            return i == premiumPreviewFragment.showAdsRow ? 8 : 0;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 1 || viewHolder.getItemViewType() == 8;
        }
    }

    /* JADX INFO: loaded from: classes6.dex */
    public static class PremiumFeatureData {
        public final String description;
        public final int icon;
        public final CharSequence title;
        public final int type;
        public int yOffset;

        public PremiumFeatureData(int i, int i2, CharSequence charSequence, String str) {
            this.type = i;
            this.icon = i2;
            this.title = charSequence;
            this.description = str;
        }
    }

    /* JADX INFO: loaded from: classes6.dex */
    class BackgroundView extends LinearLayout {
        private final FrameLayout imageFrameLayout;
        private final GLIconTextureView imageView;
        private boolean setTierListViewVisibility;
        private final TextView subtitleView;
        private RecyclerListView tierListView;
        private boolean tierListViewVisible;
        TextView titleView;

        public BackgroundView(Context context) {
            super(context);
            setOrientation(1);
            FrameLayout frameLayout = new FrameLayout(context);
            this.imageFrameLayout = frameLayout;
            int i = PremiumPreviewFragment.this.type == 1 ? Opcodes.DRETURN : Opcodes.ARRAYLENGTH;
            addView(frameLayout, LayoutHelper.createLinear(i, i, 1));
            C62181 c62181 = new GLIconTextureView(context, PremiumPreviewFragment.this.whiteBackground ? 1 : 0, PremiumPreviewFragment.this.type == 1 ? 1 : 0) { // from class: org.telegram.ui.PremiumPreviewFragment.BackgroundView.1
                final /* synthetic */ Context val$context;
                final /* synthetic */ PremiumPreviewFragment val$this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C62181(Context context2, int i2, int i3, PremiumPreviewFragment premiumPreviewFragment, Context context22) {
                    super(context22, i2, i3);
                    premiumPreviewFragment = premiumPreviewFragment;
                    context = context22;
                }

                @Override // org.telegram.p026ui.Components.Premium.GLIcon.GLIconTextureView
                public void onLongPress() {
                    super.onLongPress();
                    PremiumPreviewFragment premiumPreviewFragment = PremiumPreviewFragment.this;
                    if (premiumPreviewFragment.settingsView == null && BuildVars.DEBUG_PRIVATE_VERSION) {
                        premiumPreviewFragment.settingsView = new FrameLayout(context);
                        ScrollView scrollView = new ScrollView(context);
                        scrollView.addView(new GLIconSettingsView(context, BackgroundView.this.imageView.mRenderer));
                        PremiumPreviewFragment.this.settingsView.addView(scrollView);
                        PremiumPreviewFragment.this.settingsView.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
                        PremiumPreviewFragment.this.contentView.addView(PremiumPreviewFragment.this.settingsView, LayoutHelper.createFrame(-1, -1, 80));
                        ((ViewGroup.MarginLayoutParams) PremiumPreviewFragment.this.settingsView.getLayoutParams()).topMargin = PremiumPreviewFragment.this.currentYOffset;
                        PremiumPreviewFragment.this.settingsView.setTranslationY(AndroidUtilities.m1081dp(1000.0f));
                        PremiumPreviewFragment.this.settingsView.animate().translationY(1.0f).setDuration(300L);
                    }
                }
            };
            this.imageView = c62181;
            frameLayout.addView(c62181, LayoutHelper.createFrame(-1, -1.0f));
            frameLayout.setClipChildren(false);
            setClipChildren(false);
            TextView textView = new TextView(context22);
            this.titleView = textView;
            textView.setTextSize(1, 22.0f);
            this.titleView.setTypeface(AndroidUtilities.bold());
            this.titleView.setGravity(1);
            addView(this.titleView, LayoutHelper.createLinear(-2, -2, 0.0f, 1, 16, PremiumPreviewFragment.this.type == 1 ? 8 : 20, 16, 0));
            TextView textView2 = new TextView(context22);
            this.subtitleView = textView2;
            textView2.setTextSize(1, 14.0f);
            textView2.setLineSpacing(AndroidUtilities.m1081dp(2.0f), 1.0f);
            textView2.setGravity(1);
            addView(textView2, LayoutHelper.createLinear(-1, -2, 0.0f, 1, 16, 7, 16, 0));
            C62192 c62192 = new RecyclerListView(context22) { // from class: org.telegram.ui.PremiumPreviewFragment.BackgroundView.2
                Paint paint;
                private Path path;
                final /* synthetic */ PremiumPreviewFragment val$this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C62192(Context context22, PremiumPreviewFragment premiumPreviewFragment) {
                    super(context22);
                    premiumPreviewFragment = premiumPreviewFragment;
                    Paint paint = new Paint(1);
                    this.paint = paint;
                    paint.setColor(Theme.getColor(Theme.key_dialogBackground));
                    if (PremiumPreviewFragment.this.whiteBackground) {
                        this.paint.setShadowLayer(AndroidUtilities.m1081dp(2.0f), 0.0f, AndroidUtilities.m1081dp(0.66f), 805306368);
                    }
                    this.path = new Path();
                }

                @Override // org.telegram.p026ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
                public void dispatchDraw(Canvas canvas) {
                    this.path.rewind();
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set(0.0f, 0.0f, getWidth(), getHeight());
                    this.path.addRoundRect(rectF, AndroidUtilities.m1081dp(12.0f), AndroidUtilities.m1081dp(12.0f), Path.Direction.CW);
                    canvas.drawPath(this.path, this.paint);
                    canvas.save();
                    canvas.clipPath(this.path);
                    super.dispatchDraw(canvas);
                    canvas.restore();
                }

                @Override // org.telegram.p026ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
                protected void onSizeChanged(int i2, int i3, int i4, int i5) {
                    super.onSizeChanged(i2, i3, i4, i5);
                    BackgroundView.this.measureGradient(i2, i3);
                }

                @Override // org.telegram.p026ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
                public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                    if (PremiumPreviewFragment.this.progressToFull >= 1.0f) {
                        return false;
                    }
                    return super.onInterceptTouchEvent(motionEvent);
                }

                @Override // org.telegram.p026ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
                public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                    if (PremiumPreviewFragment.this.progressToFull >= 1.0f) {
                        return false;
                    }
                    return super.dispatchTouchEvent(motionEvent);
                }
            };
            this.tierListView = c62192;
            c62192.setOverScrollMode(2);
            this.tierListView.setLayoutManager(new LinearLayoutManager(context22));
            this.tierListView.setAdapter(new C62203(PremiumPreviewFragment.this, context22));
            this.tierListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.PremiumPreviewFragment$BackgroundView$$ExternalSyntheticLambda0
                @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                public final void onItemClick(View view, int i2) {
                    this.f$0.lambda$new$0(view, i2);
                }
            });
            final Path path = new Path();
            final float[] fArr = new float[8];
            this.tierListView.setSelectorTransformer(new Consumer() { // from class: org.telegram.ui.PremiumPreviewFragment$BackgroundView$$ExternalSyntheticLambda1
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$new$1(path, fArr, (Canvas) obj);
                }
            });
            setClipChildren(false);
            setClipToPadding(false);
            addView(this.tierListView, LayoutHelper.createLinear(-1, -2, 12.0f, 16.0f, 12.0f, 4.0f));
            updatePremiumTiers();
            updateText();
        }

        /* JADX INFO: renamed from: org.telegram.ui.PremiumPreviewFragment$BackgroundView$1 */
        class C62181 extends GLIconTextureView {
            final /* synthetic */ Context val$context;
            final /* synthetic */ PremiumPreviewFragment val$this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C62181(Context context22, int i2, int i3, PremiumPreviewFragment premiumPreviewFragment, Context context222) {
                super(context222, i2, i3);
                premiumPreviewFragment = premiumPreviewFragment;
                context = context222;
            }

            @Override // org.telegram.p026ui.Components.Premium.GLIcon.GLIconTextureView
            public void onLongPress() {
                super.onLongPress();
                PremiumPreviewFragment premiumPreviewFragment = PremiumPreviewFragment.this;
                if (premiumPreviewFragment.settingsView == null && BuildVars.DEBUG_PRIVATE_VERSION) {
                    premiumPreviewFragment.settingsView = new FrameLayout(context);
                    ScrollView scrollView = new ScrollView(context);
                    scrollView.addView(new GLIconSettingsView(context, BackgroundView.this.imageView.mRenderer));
                    PremiumPreviewFragment.this.settingsView.addView(scrollView);
                    PremiumPreviewFragment.this.settingsView.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
                    PremiumPreviewFragment.this.contentView.addView(PremiumPreviewFragment.this.settingsView, LayoutHelper.createFrame(-1, -1, 80));
                    ((ViewGroup.MarginLayoutParams) PremiumPreviewFragment.this.settingsView.getLayoutParams()).topMargin = PremiumPreviewFragment.this.currentYOffset;
                    PremiumPreviewFragment.this.settingsView.setTranslationY(AndroidUtilities.m1081dp(1000.0f));
                    PremiumPreviewFragment.this.settingsView.animate().translationY(1.0f).setDuration(300L);
                }
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.PremiumPreviewFragment$BackgroundView$2 */
        class C62192 extends RecyclerListView {
            Paint paint;
            private Path path;
            final /* synthetic */ PremiumPreviewFragment val$this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C62192(Context context222, PremiumPreviewFragment premiumPreviewFragment) {
                super(context222);
                premiumPreviewFragment = premiumPreviewFragment;
                Paint paint = new Paint(1);
                this.paint = paint;
                paint.setColor(Theme.getColor(Theme.key_dialogBackground));
                if (PremiumPreviewFragment.this.whiteBackground) {
                    this.paint.setShadowLayer(AndroidUtilities.m1081dp(2.0f), 0.0f, AndroidUtilities.m1081dp(0.66f), 805306368);
                }
                this.path = new Path();
            }

            @Override // org.telegram.p026ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
            public void dispatchDraw(Canvas canvas) {
                this.path.rewind();
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(0.0f, 0.0f, getWidth(), getHeight());
                this.path.addRoundRect(rectF, AndroidUtilities.m1081dp(12.0f), AndroidUtilities.m1081dp(12.0f), Path.Direction.CW);
                canvas.drawPath(this.path, this.paint);
                canvas.save();
                canvas.clipPath(this.path);
                super.dispatchDraw(canvas);
                canvas.restore();
            }

            @Override // org.telegram.p026ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
            protected void onSizeChanged(int i2, int i3, int i4, int i5) {
                super.onSizeChanged(i2, i3, i4, i5);
                BackgroundView.this.measureGradient(i2, i3);
            }

            @Override // org.telegram.p026ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (PremiumPreviewFragment.this.progressToFull >= 1.0f) {
                    return false;
                }
                return super.onInterceptTouchEvent(motionEvent);
            }

            @Override // org.telegram.p026ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                if (PremiumPreviewFragment.this.progressToFull >= 1.0f) {
                    return false;
                }
                return super.dispatchTouchEvent(motionEvent);
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.PremiumPreviewFragment$BackgroundView$3 */
        class C62203 extends RecyclerListView.SelectionAdapter {
            final /* synthetic */ Context val$context;
            final /* synthetic */ PremiumPreviewFragment val$this$0;

            C62203(PremiumPreviewFragment premiumPreviewFragment, Context context) {
                this.val$this$0 = premiumPreviewFragment;
                this.val$context = context;
            }

            /* JADX INFO: renamed from: org.telegram.ui.PremiumPreviewFragment$BackgroundView$3$1 */
            class AnonymousClass1 extends PremiumTierCell {
                AnonymousClass1(Context context) {
                    super(context);
                }

                @Override // org.telegram.p026ui.Components.Premium.PremiumTierCell, android.view.ViewGroup, android.view.View
                protected void dispatchDraw(Canvas canvas) {
                    if (this.discountView.getVisibility() == 0) {
                        RectF rectF = AndroidUtilities.rectTmp;
                        rectF.set(this.discountView.getLeft(), this.discountView.getTop(), this.discountView.getRight(), this.discountView.getBottom());
                        PremiumPreviewFragment.this.tiersGradientTools.gradientMatrix(0, 0, getMeasuredWidth(), PremiumPreviewFragment.this.totalTiersGradientHeight, 0.0f, -this.tier.yOffset);
                        canvas.drawRoundRect(rectF, AndroidUtilities.m1081dp(6.0f), AndroidUtilities.m1081dp(6.0f), PremiumPreviewFragment.this.tiersGradientTools.paint);
                    }
                    super.dispatchDraw(canvas);
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                final AnonymousClass1 anonymousClass1 = new PremiumTierCell(this.val$context) { // from class: org.telegram.ui.PremiumPreviewFragment.BackgroundView.3.1
                    AnonymousClass1(Context context) {
                        super(context);
                    }

                    @Override // org.telegram.p026ui.Components.Premium.PremiumTierCell, android.view.ViewGroup, android.view.View
                    protected void dispatchDraw(Canvas canvas) {
                        if (this.discountView.getVisibility() == 0) {
                            RectF rectF = AndroidUtilities.rectTmp;
                            rectF.set(this.discountView.getLeft(), this.discountView.getTop(), this.discountView.getRight(), this.discountView.getBottom());
                            PremiumPreviewFragment.this.tiersGradientTools.gradientMatrix(0, 0, getMeasuredWidth(), PremiumPreviewFragment.this.totalTiersGradientHeight, 0.0f, -this.tier.yOffset);
                            canvas.drawRoundRect(rectF, AndroidUtilities.m1081dp(6.0f), AndroidUtilities.m1081dp(6.0f), PremiumPreviewFragment.this.tiersGradientTools.paint);
                        }
                        super.dispatchDraw(canvas);
                    }
                };
                anonymousClass1.setCirclePaintProvider(new GenericProvider() { // from class: org.telegram.ui.PremiumPreviewFragment$BackgroundView$3$$ExternalSyntheticLambda0
                    @Override // org.telegram.messenger.GenericProvider
                    public final Object provide(Object obj) {
                        return this.f$0.lambda$onCreateViewHolder$0(anonymousClass1, (Void) obj);
                    }
                });
                return new RecyclerListView.Holder(anonymousClass1);
            }

            public /* synthetic */ Paint lambda$onCreateViewHolder$0(PremiumTierCell premiumTierCell, Void r9) {
                PremiumPreviewFragment.this.tiersGradientTools.gradientMatrix(0, 0, premiumTierCell.getMeasuredWidth(), PremiumPreviewFragment.this.totalTiersGradientHeight, 0.0f, -premiumTierCell.getTier().yOffset);
                return PremiumPreviewFragment.this.tiersGradientTools.paint;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                PremiumTierCell premiumTierCell = (PremiumTierCell) viewHolder.itemView;
                premiumTierCell.bind((SubscriptionTier) PremiumPreviewFragment.this.subscriptionTiers.get(i), i != getItemCount() - 1);
                premiumTierCell.setChecked(PremiumPreviewFragment.this.selectedTierIndex == i, false);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
            public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
                return !((SubscriptionTier) PremiumPreviewFragment.this.subscriptionTiers.get(viewHolder.getAdapterPosition())).subscriptionOption.current;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return PremiumPreviewFragment.this.subscriptionTiers.size();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:417:0x00f1  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public /* synthetic */ void lambda$new$0(android.view.View r6, int r7) {
            /*
                Method dump skipped, instruction units count: 246
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.PremiumPreviewFragment.BackgroundView.lambda$new$0(android.view.View, int):void");
        }

        public /* synthetic */ void lambda$new$1(Path path, float[] fArr, Canvas canvas) {
            View pressedChildView = this.tierListView.getPressedChildView();
            int adapterPosition = pressedChildView == null ? -1 : this.tierListView.getChildViewHolder(pressedChildView).getAdapterPosition();
            path.rewind();
            Rect selectorRect = this.tierListView.getSelectorRect();
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(selectorRect.left, selectorRect.top, selectorRect.right, selectorRect.bottom);
            Arrays.fill(fArr, 0.0f);
            if (adapterPosition == 0) {
                Arrays.fill(fArr, 0, 4, AndroidUtilities.m1081dp(12.0f));
            }
            if (adapterPosition == this.tierListView.getAdapter().getItemCount() - 1) {
                Arrays.fill(fArr, 4, 8, AndroidUtilities.m1081dp(12.0f));
            }
            path.addRoundRect(rectF, fArr, Path.Direction.CW);
            canvas.clipPath(path);
        }

        public void measureGradient(int i, int i2) {
            int measuredHeight = 0;
            for (int i3 = 0; i3 < PremiumPreviewFragment.this.subscriptionTiers.size(); i3++) {
                PremiumPreviewFragment premiumPreviewFragment = PremiumPreviewFragment.this;
                premiumPreviewFragment.dummyTierCell.bind((SubscriptionTier) premiumPreviewFragment.subscriptionTiers.get(i3), false);
                PremiumPreviewFragment.this.dummyTierCell.measure(View.MeasureSpec.makeMeasureSpec(i, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE));
                ((SubscriptionTier) PremiumPreviewFragment.this.subscriptionTiers.get(i3)).yOffset = measuredHeight;
                measuredHeight += PremiumPreviewFragment.this.dummyTierCell.getMeasuredHeight();
            }
            PremiumPreviewFragment.this.totalTiersGradientHeight = measuredHeight;
        }

        /* JADX WARN: Removed duplicated region for block: B:626:0x00e8  */
        /* JADX WARN: Removed duplicated region for block: B:630:0x00f4  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void updatePremiumTiers() {
            /*
                Method dump skipped, instruction units count: 472
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.PremiumPreviewFragment.BackgroundView.updatePremiumTiers():void");
        }

        public void updateText() {
            if (PremiumPreviewFragment.this.type == 0) {
                this.titleView.setText(LocaleController.getString(PremiumPreviewFragment.this.forcePremium ? C2702R.string.TelegramPremiumSubscribedTitle : C2702R.string.TelegramPremium));
                this.subtitleView.setText(AndroidUtilities.replaceTags(LocaleController.getString((PremiumPreviewFragment.this.getUserConfig().isPremium() || PremiumPreviewFragment.this.forcePremium) ? C2702R.string.TelegramPremiumSubscribedSubtitle : C2702R.string.TelegramPremiumSubtitle)));
            } else if (PremiumPreviewFragment.this.type == 1) {
                this.titleView.setText(LocaleController.getString(PremiumPreviewFragment.this.forcePremium ? C2702R.string.TelegramPremiumSubscribedTitle : C2702R.string.TelegramBusiness));
                this.subtitleView.setText(AndroidUtilities.replaceTags(LocaleController.getString((PremiumPreviewFragment.this.getUserConfig().isPremium() || PremiumPreviewFragment.this.forcePremium) ? C2702R.string.TelegramBusinessSubscribedSubtitleTemp : C2702R.string.TelegramBusinessSubtitleTemp)));
            }
            this.subtitleView.getLayoutParams().width = Math.min(AndroidUtilities.displaySize.x - AndroidUtilities.m1081dp(42.0f), HintView2.cutInFancyHalf(this.subtitleView.getText(), this.subtitleView.getPaint()));
            boolean z = PremiumPreviewFragment.this.forcePremium || BuildVars.IS_BILLING_UNAVAILABLE || PremiumPreviewFragment.this.subscriptionTiers.size() <= 1;
            if (!this.setTierListViewVisibility || !z) {
                this.tierListView.setVisibility(z ? 8 : 0);
                this.setTierListViewVisibility = true;
            } else if (this.tierListView.getVisibility() == 0 && z && this.tierListViewVisible == z) {
                final RecyclerListView recyclerListView = this.tierListView;
                final ValueAnimator duration = ValueAnimator.ofFloat(1.0f, 0.0f).setDuration(250L);
                duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PremiumPreviewFragment$BackgroundView$$ExternalSyntheticLambda2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$updateText$2(recyclerListView, duration, valueAnimator);
                    }
                });
                duration.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PremiumPreviewFragment.BackgroundView.4
                    final /* synthetic */ View val$v;

                    C62224(final View recyclerListView2) {
                        view = recyclerListView2;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        view.setVisibility(8);
                        for (int i = 0; i < PremiumPreviewFragment.this.backgroundView.getChildCount(); i++) {
                            View childAt = PremiumPreviewFragment.this.backgroundView.getChildAt(i);
                            if (childAt != BackgroundView.this.tierListView) {
                                childAt.setTranslationY(0.0f);
                            }
                        }
                    }
                });
                duration.setInterpolator(CubicBezierInterpolator.DEFAULT);
                duration.start();
            }
            this.tierListViewVisible = !z;
        }

        public /* synthetic */ void lambda$updateText$2(View view, ValueAnimator valueAnimator, ValueAnimator valueAnimator2) {
            float fM1081dp;
            float fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
            view.setAlpha(fFloatValue);
            view.setScaleX(fFloatValue);
            view.setScaleY(fFloatValue);
            float animatedFraction = valueAnimator.getAnimatedFraction();
            for (int i = 0; i < PremiumPreviewFragment.this.backgroundView.getChildCount(); i++) {
                View childAt = PremiumPreviewFragment.this.backgroundView.getChildAt(i);
                if (childAt != this.tierListView) {
                    if (childAt == this.imageFrameLayout) {
                        fM1081dp = 0.0f - (AndroidUtilities.m1081dp(15.0f) * animatedFraction);
                    } else {
                        fM1081dp = 0.0f + (AndroidUtilities.m1081dp(8.0f) * animatedFraction);
                    }
                    childAt.setTranslationY((view.getMeasuredHeight() * animatedFraction) + fM1081dp);
                }
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.PremiumPreviewFragment$BackgroundView$4 */
        class C62224 extends AnimatorListenerAdapter {
            final /* synthetic */ View val$v;

            C62224(final View recyclerListView2) {
                view = recyclerListView2;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(8);
                for (int i = 0; i < PremiumPreviewFragment.this.backgroundView.getChildCount(); i++) {
                    View childAt = PremiumPreviewFragment.this.backgroundView.getChildAt(i);
                    if (childAt != BackgroundView.this.tierListView) {
                        childAt.setTranslationY(0.0f);
                    }
                }
            }
        }
    }

    public void updateButtonText(boolean z) {
        if (this.premiumButtonView == null) {
            return;
        }
        if (!getUserConfig().isPremium() || this.currentSubscriptionTier == null || this.selectedTierIndex >= this.subscriptionTiers.size() || ((SubscriptionTier) this.subscriptionTiers.get(this.selectedTierIndex)).getMonths() >= this.currentSubscriptionTier.getMonths()) {
            if (LocaleController.isRTL) {
                z = false;
            }
            if (BuildVars.IS_BILLING_UNAVAILABLE && this.selectedTierIndex < this.subscriptionTiers.size()) {
                this.premiumButtonView.setButton(getPremiumButtonText(this.currentAccount, (SubscriptionTier) this.subscriptionTiers.get(this.selectedTierIndex)), new View.OnClickListener() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) throws Throwable {
                        this.f$0.lambda$updateButtonText$15(view);
                    }
                }, z);
                return;
            }
            if (!BuildVars.useInvoiceBilling() && (!BillingController.getInstance().isReady() || this.subscriptionTiers.isEmpty() || this.selectedTierIndex >= this.subscriptionTiers.size() || ((SubscriptionTier) this.subscriptionTiers.get(this.selectedTierIndex)).googlePlayProductDetails == null)) {
                this.premiumButtonView.setButton(LocaleController.getString(C2702R.string.Loading), new View.OnClickListener() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        PremiumPreviewFragment.$r8$lambda$NFplOqXqxu6lZ1w7Yqv5yvLsY8Y(view);
                    }
                }, z);
                this.premiumButtonView.setFlickerDisabled(true);
            } else {
                if (this.subscriptionTiers.isEmpty() || this.selectedTierIndex >= this.subscriptionTiers.size()) {
                    return;
                }
                this.premiumButtonView.setButton(getPremiumButtonText(this.currentAccount, (SubscriptionTier) this.subscriptionTiers.get(this.selectedTierIndex)), new View.OnClickListener() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) throws Throwable {
                        this.f$0.lambda$updateButtonText$17(view);
                    }
                }, z);
                this.premiumButtonView.setFlickerDisabled(false);
            }
        }
    }

    public /* synthetic */ void lambda$updateButtonText$15(View view) throws Throwable {
        buyPremium(this);
    }

    public /* synthetic */ void lambda$updateButtonText$17(View view) throws Throwable {
        TLRPC.TL_premiumSubscriptionOption tL_premiumSubscriptionOption;
        SubscriptionTier subscriptionTier = (SubscriptionTier) this.subscriptionTiers.get(this.selectedTierIndex);
        SubscriptionTier subscriptionTier2 = this.currentSubscriptionTier;
        buyPremium(this, subscriptionTier, "settings", true, (subscriptionTier2 == null || (tL_premiumSubscriptionOption = subscriptionTier2.subscriptionOption) == null || tL_premiumSubscriptionOption.transaction == null) ? null : BillingFlowParams.SubscriptionUpdateParams.newBuilder().setOldPurchaseToken(BillingController.getInstance().getLastPremiumToken()).setSubscriptionReplacementMode(5).build());
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean isLightStatusBar() {
        return this.whiteBackground;
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        BackgroundView backgroundView = this.backgroundView;
        if (backgroundView != null && backgroundView.imageView != null) {
            this.backgroundView.imageView.setPaused(false);
            this.backgroundView.imageView.setDialogVisible(false);
        }
        this.particlesView.setPaused(false);
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
        BackgroundView backgroundView = this.backgroundView;
        if (backgroundView != null && backgroundView.imageView != null) {
            this.backgroundView.imageView.setDialogVisible(true);
        }
        StarParticlesView starParticlesView = this.particlesView;
        if (starParticlesView != null) {
            starParticlesView.setPaused(true);
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean canBeginSlide() {
        BackgroundView backgroundView = this.backgroundView;
        return backgroundView == null || backgroundView.imageView == null || !this.backgroundView.imageView.touched;
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public ArrayList getThemeDescriptions() {
        return SimpleThemeDescription.createThemeDescriptions(new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda3
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                this.f$0.updateColors();
            }

            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public /* synthetic */ void onAnimationProgress(float f) {
                ThemeDescription.ThemeDescriptionDelegate.CC.$default$onAnimationProgress(this, f);
            }
        }, Theme.key_premiumGradient1, Theme.key_premiumGradient2, Theme.key_premiumGradient3, Theme.key_premiumGradient4, Theme.key_premiumGradientBackground1, Theme.key_premiumGradientBackground2, Theme.key_premiumGradientBackground3, Theme.key_premiumGradientBackground4, Theme.key_premiumGradientBackgroundOverlay, Theme.key_premiumStarGradient1, Theme.key_premiumStarGradient2, Theme.key_premiumStartSmallStarsColor, Theme.key_premiumStartSmallStarsColor2);
    }

    public void updateColors() {
        ActionBar actionBar;
        if (this.backgroundView == null || (actionBar = this.actionBar) == null) {
            return;
        }
        actionBar.setItemsColor(Theme.getColor(this.whiteBackground ? Theme.key_windowBackgroundWhiteBlackText : Theme.key_premiumGradientBackgroundOverlay), false);
        this.actionBar.setItemsColor(Theme.getColor(this.whiteBackground ? Theme.key_windowBackgroundWhiteBlackText : Theme.key_premiumGradientBackgroundOverlay), true);
        ActionBar actionBar2 = this.actionBar;
        int i = Theme.key_premiumGradientBackgroundOverlay;
        actionBar2.setItemsBackgroundColor(ColorUtils.setAlphaComponent(Theme.getColor(i), 60), false);
        this.particlesView.drawable.updateColors();
        BackgroundView backgroundView = this.backgroundView;
        if (backgroundView != null) {
            backgroundView.titleView.setTextColor(Theme.getColor(this.whiteBackground ? Theme.key_windowBackgroundWhiteBlackText : i));
            TextView textView = this.backgroundView.subtitleView;
            if (this.whiteBackground) {
                i = Theme.key_windowBackgroundWhiteBlackText;
            }
            textView.setTextColor(Theme.getColor(i));
            if (this.backgroundView.imageView != null && this.backgroundView.imageView.mRenderer != null) {
                if (this.whiteBackground) {
                    this.backgroundView.imageView.mRenderer.colorKey1 = Theme.key_premiumCoinGradient1;
                    this.backgroundView.imageView.mRenderer.colorKey2 = Theme.key_premiumCoinGradient2;
                }
                this.backgroundView.imageView.mRenderer.updateColors();
            }
        }
        updateBackgroundImage();
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean onBackPressed(boolean z) {
        if (this.settingsView == null) {
            return super.onBackPressed(z);
        }
        if (!z) {
            return false;
        }
        closeSetting();
        return false;
    }

    /* JADX INFO: renamed from: org.telegram.ui.PremiumPreviewFragment$5 */
    /* JADX INFO: loaded from: classes6.dex */
    class C62135 extends AnimatorListenerAdapter {
        C62135() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            PremiumPreviewFragment.this.contentView.removeView(PremiumPreviewFragment.this.settingsView);
            PremiumPreviewFragment.this.settingsView = null;
            super.onAnimationEnd(animator);
        }
    }

    private void closeSetting() {
        this.settingsView.animate().translationY(AndroidUtilities.m1081dp(1000.0f)).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PremiumPreviewFragment.5
            C62135() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PremiumPreviewFragment.this.contentView.removeView(PremiumPreviewFragment.this.settingsView);
                PremiumPreviewFragment.this.settingsView = null;
                super.onAnimationEnd(animator);
            }
        });
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public Dialog showDialog(Dialog dialog) {
        Dialog dialogShowDialog = super.showDialog(dialog);
        updateDialogVisibility(dialogShowDialog != null);
        return dialogShowDialog;
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    protected void onDialogDismiss(Dialog dialog) {
        super.onDialogDismiss(dialog);
        updateDialogVisibility(false);
    }

    private void updateDialogVisibility(boolean z) {
        if (z != this.isDialogVisible) {
            this.isDialogVisible = z;
            BackgroundView backgroundView = this.backgroundView;
            if (backgroundView != null && backgroundView.imageView != null) {
                this.backgroundView.imageView.setDialogVisible(z);
            }
            this.particlesView.setPaused(z);
            this.contentView.invalidate();
        }
    }

    public static void sentShowScreenStat(String str) {
        TLRPC.JSONValue tL_jsonNull;
        ConnectionsManager connectionsManager = ConnectionsManager.getInstance(UserConfig.selectedAccount);
        TLRPC.TL_help_saveAppLog tL_help_saveAppLog = new TLRPC.TL_help_saveAppLog();
        TLRPC.TL_inputAppEvent tL_inputAppEvent = new TLRPC.TL_inputAppEvent();
        tL_inputAppEvent.time = connectionsManager.getCurrentTime();
        tL_inputAppEvent.type = "premium.promo_screen_show";
        TLRPC.TL_jsonObject tL_jsonObject = new TLRPC.TL_jsonObject();
        tL_inputAppEvent.data = tL_jsonObject;
        TLRPC.TL_jsonObjectValue tL_jsonObjectValue = new TLRPC.TL_jsonObjectValue();
        if (str != null) {
            TLRPC.TL_jsonString tL_jsonString = new TLRPC.TL_jsonString();
            tL_jsonString.value = str;
            tL_jsonNull = tL_jsonString;
        } else {
            tL_jsonNull = new TLRPC.TL_jsonNull();
        }
        tL_jsonObjectValue.key = "source";
        tL_jsonObjectValue.value = tL_jsonNull;
        tL_jsonObject.value.add(tL_jsonObjectValue);
        tL_help_saveAppLog.events.add(tL_inputAppEvent);
        connectionsManager.sendRequest(tL_help_saveAppLog, new RequestDelegate() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda6
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                PremiumPreviewFragment.m16150$r8$lambda$IdmGXdD9IOKvD6w09SwNb7iPyw(tLObject, tL_error);
            }
        });
    }

    public static void sentPremiumButtonClick() {
        TLRPC.TL_help_saveAppLog tL_help_saveAppLog = new TLRPC.TL_help_saveAppLog();
        TLRPC.TL_inputAppEvent tL_inputAppEvent = new TLRPC.TL_inputAppEvent();
        tL_inputAppEvent.time = ConnectionsManager.getInstance(UserConfig.selectedAccount).getCurrentTime();
        tL_inputAppEvent.type = "premium.promo_screen_accept";
        tL_inputAppEvent.data = new TLRPC.TL_jsonNull();
        tL_help_saveAppLog.events.add(tL_inputAppEvent);
        ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tL_help_saveAppLog, new RequestDelegate() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda15
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                PremiumPreviewFragment.m16153$r8$lambda$vkLBVB9RYwgpPVlEzKUMDbD44E(tLObject, tL_error);
            }
        });
    }

    public static void sentPremiumBuyCanceled() {
        TLRPC.TL_help_saveAppLog tL_help_saveAppLog = new TLRPC.TL_help_saveAppLog();
        TLRPC.TL_inputAppEvent tL_inputAppEvent = new TLRPC.TL_inputAppEvent();
        tL_inputAppEvent.time = ConnectionsManager.getInstance(UserConfig.selectedAccount).getCurrentTime();
        tL_inputAppEvent.type = "premium.promo_screen_fail";
        tL_inputAppEvent.data = new TLRPC.TL_jsonNull();
        tL_help_saveAppLog.events.add(tL_inputAppEvent);
        ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tL_help_saveAppLog, new RequestDelegate() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda12
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                PremiumPreviewFragment.$r8$lambda$8qLlq_0tdQNBu0mq8kq48HmBL9s(tLObject, tL_error);
            }
        });
    }

    public static void sentShowFeaturePreview(int i, int i2) {
        TLRPC.TL_help_saveAppLog tL_help_saveAppLog = new TLRPC.TL_help_saveAppLog();
        TLRPC.TL_inputAppEvent tL_inputAppEvent = new TLRPC.TL_inputAppEvent();
        tL_inputAppEvent.time = ConnectionsManager.getInstance(i).getCurrentTime();
        tL_inputAppEvent.type = "premium.promo_screen_tap";
        TLRPC.TL_jsonObject tL_jsonObject = new TLRPC.TL_jsonObject();
        tL_inputAppEvent.data = tL_jsonObject;
        TLRPC.TL_jsonObjectValue tL_jsonObjectValue = new TLRPC.TL_jsonObjectValue();
        String strFeatureTypeToServerString = featureTypeToServerString(i2);
        if (strFeatureTypeToServerString != null) {
            TLRPC.TL_jsonString tL_jsonString = new TLRPC.TL_jsonString();
            tL_jsonString.value = strFeatureTypeToServerString;
            tL_jsonObjectValue.value = tL_jsonString;
        } else {
            tL_jsonObjectValue.value = new TLRPC.TL_jsonNull();
        }
        tL_jsonObjectValue.key = "item";
        tL_jsonObject.value.add(tL_jsonObjectValue);
        tL_help_saveAppLog.events.add(tL_inputAppEvent);
        ConnectionsManager.getInstance(i).sendRequest(tL_help_saveAppLog, new RequestDelegate() { // from class: org.telegram.ui.PremiumPreviewFragment$$ExternalSyntheticLambda13
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                PremiumPreviewFragment.$r8$lambda$uvLUhmFSlOozGY_McZFrXQVR5P8(tLObject, tL_error);
            }
        });
    }

    /* JADX INFO: loaded from: classes6.dex */
    public static final class SubscriptionTier {
        private int discount;
        private ProductDetails googlePlayProductDetails;
        private ProductDetails.SubscriptionOfferDetails offerDetails;
        private long pricePerMonth;
        private long pricePerYear;
        private long pricePerYearRegular;
        public final TLRPC.TL_premiumSubscriptionOption subscriptionOption;
        public int yOffset;

        public SubscriptionTier(TLRPC.TL_premiumSubscriptionOption tL_premiumSubscriptionOption) {
            this.subscriptionOption = tL_premiumSubscriptionOption;
        }

        public ProductDetails getGooglePlayProductDetails() {
            return this.googlePlayProductDetails;
        }

        public ProductDetails.SubscriptionOfferDetails getOfferDetails() {
            checkOfferDetails();
            return this.offerDetails;
        }

        public void setGooglePlayProductDetails(ProductDetails productDetails) {
            this.googlePlayProductDetails = productDetails;
        }

        public void setPricePerYearRegular(long j) {
            this.pricePerYearRegular = j;
        }

        public int getMonths() {
            return this.subscriptionOption.months;
        }

        public int getDiscount() {
            if (this.discount == 0) {
                if (getPricePerMonth() == 0) {
                    return 0;
                }
                if (this.pricePerYearRegular != 0) {
                    int pricePerYear = (int) ((1.0d - (getPricePerYear() / this.pricePerYearRegular)) * 100.0d);
                    this.discount = pricePerYear;
                    if (pricePerYear == 0) {
                        this.discount = -1;
                    }
                }
            }
            return this.discount;
        }

        public long getPricePerYear() {
            if (this.pricePerYear == 0) {
                long price = getPrice();
                if (price != 0) {
                    this.pricePerYear = (long) ((price / ((double) this.subscriptionOption.months)) * 12.0d);
                }
            }
            return this.pricePerYear;
        }

        public long getPricePerMonth() {
            if (this.pricePerMonth == 0) {
                long price = getPrice();
                if (price != 0) {
                    this.pricePerMonth = price / ((long) this.subscriptionOption.months);
                }
            }
            return this.pricePerMonth;
        }

        public String getFormattedPricePerYearRegular() {
            if (BuildVars.useInvoiceBilling() || this.subscriptionOption.store_product == null) {
                return BillingController.getInstance().formatCurrency(this.pricePerYearRegular, getCurrency());
            }
            return this.googlePlayProductDetails == null ? _UrlKt.FRAGMENT_ENCODE_SET : BillingController.getInstance().formatCurrency(this.pricePerYearRegular, getCurrency(), 6);
        }

        public String getFormattedPricePerYear() {
            if (BuildVars.useInvoiceBilling() || this.subscriptionOption.store_product == null) {
                return BillingController.getInstance().formatCurrency(getPricePerYear(), getCurrency());
            }
            return this.googlePlayProductDetails == null ? _UrlKt.FRAGMENT_ENCODE_SET : BillingController.getInstance().formatCurrency(getPricePerYear(), getCurrency(), 6);
        }

        public String getFormattedPricePerMonth() {
            if (BuildVars.useInvoiceBilling() || this.subscriptionOption.store_product == null) {
                return BillingController.getInstance().formatCurrency(getPricePerMonth(), getCurrency());
            }
            return this.googlePlayProductDetails == null ? _UrlKt.FRAGMENT_ENCODE_SET : BillingController.getInstance().formatCurrency(getPricePerMonth(), getCurrency(), 6);
        }

        public String getFormattedPrice() {
            if (BuildVars.useInvoiceBilling() || this.subscriptionOption.store_product == null) {
                return BillingController.getInstance().formatCurrency(getPrice(), getCurrency());
            }
            return this.googlePlayProductDetails == null ? _UrlKt.FRAGMENT_ENCODE_SET : BillingController.getInstance().formatCurrency(getPrice(), getCurrency(), 6);
        }

        public long getPrice() {
            if (BuildVars.useInvoiceBilling() || this.subscriptionOption.store_product == null) {
                return this.subscriptionOption.amount;
            }
            if (this.googlePlayProductDetails == null) {
                return 0L;
            }
            checkOfferDetails();
            ProductDetails.SubscriptionOfferDetails subscriptionOfferDetails = this.offerDetails;
            if (subscriptionOfferDetails == null) {
                return 0L;
            }
            return ((ProductDetails.PricingPhase) subscriptionOfferDetails.getPricingPhases().getPricingPhaseList().get(0)).getPriceAmountMicros();
        }

        public String getCurrency() {
            if (BuildVars.useInvoiceBilling() || this.subscriptionOption.store_product == null) {
                return this.subscriptionOption.currency;
            }
            if (this.googlePlayProductDetails == null) {
                return _UrlKt.FRAGMENT_ENCODE_SET;
            }
            checkOfferDetails();
            ProductDetails.SubscriptionOfferDetails subscriptionOfferDetails = this.offerDetails;
            return subscriptionOfferDetails == null ? _UrlKt.FRAGMENT_ENCODE_SET : ((ProductDetails.PricingPhase) subscriptionOfferDetails.getPricingPhases().getPricingPhaseList().get(0)).getPriceCurrencyCode();
        }

        private void checkOfferDetails() {
            ProductDetails productDetails = this.googlePlayProductDetails;
            if (productDetails != null && this.offerDetails == null) {
                for (ProductDetails.SubscriptionOfferDetails subscriptionOfferDetails : productDetails.getSubscriptionOfferDetails()) {
                    String billingPeriod = ((ProductDetails.PricingPhase) subscriptionOfferDetails.getPricingPhases().getPricingPhaseList().get(0)).getBillingPeriod();
                    if (getMonths() == 12) {
                        if (billingPeriod.equals("P1Y")) {
                            this.offerDetails = subscriptionOfferDetails;
                            return;
                        }
                    } else if (billingPeriod.equals(String.format(Locale.ROOT, "P%dM", Integer.valueOf(getMonths())))) {
                        this.offerDetails = subscriptionOfferDetails;
                        return;
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:198:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x00c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void showSelectStatusDialog(org.telegram.p026ui.PremiumFeatureCell r20, java.lang.Long r21, org.telegram.messenger.Utilities.Callback2 r22) {
        /*
            Method dump skipped, instruction units count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.PremiumPreviewFragment.showSelectStatusDialog(org.telegram.ui.PremiumFeatureCell, java.lang.Long, org.telegram.messenger.Utilities$Callback2):void");
    }

    /* JADX INFO: renamed from: org.telegram.ui.PremiumPreviewFragment$6 */
    /* JADX INFO: loaded from: classes6.dex */
    class C62146 extends SelectAnimatedEmojiDialog {
        final /* synthetic */ Utilities.Callback2 val$onSet;
        final /* synthetic */ SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] val$popup;

        @Override // org.telegram.p026ui.SelectAnimatedEmojiDialog
        protected float getScrimDrawableTranslationY() {
            return 0.0f;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C62146(BaseFragment baseFragment, Context context, boolean z, Integer num, int i, boolean z2, Theme.ResourcesProvider resourcesProvider, int i2, Utilities.Callback2 callback2, SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] selectAnimatedEmojiDialogWindowArr) {
            super(baseFragment, context, z, num, i, z2, resourcesProvider, i2);
            callback2 = callback2;
            selectAnimatedEmojiDialogWindowArr = selectAnimatedEmojiDialogWindowArr;
        }

        @Override // org.telegram.p026ui.SelectAnimatedEmojiDialog
        protected void onEmojiSelected(View view, Long l, TLRPC.Document document, TL_stars.TL_starGiftUnique tL_starGiftUnique, Integer num) {
            Utilities.Callback2 callback2 = callback2;
            if (callback2 != null) {
                callback2.run(l, num);
            }
            if (selectAnimatedEmojiDialogWindowArr[0] != null) {
                PremiumPreviewFragment.this.selectAnimatedEmojiDialog = null;
                selectAnimatedEmojiDialogWindowArr[0].dismiss();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.PremiumPreviewFragment$7 */
    /* JADX INFO: loaded from: classes6.dex */
    class C62157 extends SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow {
        C62157(View view, int i, int i2) {
            super(view, i, i2);
        }

        @Override // org.telegram.ui.SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow, android.widget.PopupWindow
        public void dismiss() {
            super.dismiss();
            PremiumPreviewFragment.this.selectAnimatedEmojiDialog = null;
        }
    }
}
