package org.telegram.p029ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StatFs;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.SparseIntArray;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedCallback;
import androidx.appcompat.app.C0042xf5eeb9b9;
import androidx.arch.core.util.Function;
import androidx.collection.LongSparseArray;
import androidx.core.graphics.ColorUtils;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.drawer.DrawerContainer;
import com.exteragram.messenger.icons.ExteraResources;
import com.exteragram.messenger.icons.p012ui.picker.IconPickerController;
import com.exteragram.messenger.pillstack.core.PillStackConfig;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.updater.UpdaterUtils;
import com.exteragram.messenger.utils.network.RemoteUtils;
import com.exteragram.messenger.utils.p017ui.MonetUtils;
import com.google.android.material.navigation.NavigationBarView;
import com.google.common.primitives.Longs;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.internal.url._UrlKt;
import org.mvel2.MVEL;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.AutoDeleteMediaTask;
import org.telegram.messenger.BackupAgent;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2888R;
import org.telegram.messenger.ChannelBoostsController;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.FingerprintController;
import org.telegram.messenger.FlagSecureReason;
import org.telegram.messenger.GenericProvider;
import org.telegram.messenger.GiftAuctionController;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocationController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.SharedPrefsHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.pip.PipActivityController;
import org.telegram.messenger.pip.activity.IPipActivity;
import org.telegram.messenger.pip.activity.IPipActivityHandler;
import org.telegram.messenger.pip.activity.IPipActivityListener;
import org.telegram.messenger.utils.FrameMetricsOverlayView;
import org.telegram.messenger.voip.VideoCapturerDevice;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.p029ui.ActionBar.ActionBarLayout;
import org.telegram.p029ui.ActionBar.AlertDialog;
import org.telegram.p029ui.ActionBar.BaseFragment;
import org.telegram.p029ui.ActionBar.BottomSheetTabs;
import org.telegram.p029ui.ActionBar.BottomSheetTabsOverlay;
import org.telegram.p029ui.ActionBar.DrawerLayoutContainer;
import org.telegram.p029ui.ActionBar.INavigationLayout;
import org.telegram.p029ui.ActionBar.Theme;
import org.telegram.p029ui.Cells.ChatMessageCell;
import org.telegram.p029ui.Cells.LanguageCell;
import org.telegram.p029ui.ChatRightsEditActivity;
import org.telegram.p029ui.Components.AlertsCreator;
import org.telegram.p029ui.Components.AttachBotIntroTopView;
import org.telegram.p029ui.Components.BatteryDrawable;
import org.telegram.p029ui.Components.BlockingUpdateView;
import org.telegram.p029ui.Components.Bulletin;
import org.telegram.p029ui.Components.BulletinFactory;
import org.telegram.p029ui.Components.CubicBezierInterpolator;
import org.telegram.p029ui.Components.EmbedBottomSheet;
import org.telegram.p029ui.Components.FireworksOverlay;
import org.telegram.p029ui.Components.FloatingDebug.FloatingDebugController;
import org.telegram.p029ui.Components.FolderBottomSheet;
import org.telegram.p029ui.Components.Forum.ForumUtilities;
import org.telegram.p029ui.Components.GroupCallPip;
import org.telegram.p029ui.Components.LayoutHelper;
import org.telegram.p029ui.Components.PasscodeView;
import org.telegram.p029ui.Components.PasscodeViewDialog;
import org.telegram.p029ui.Components.PipRoundVideoView;
import org.telegram.p029ui.Components.PipVideoOverlay;
import org.telegram.p029ui.Components.Premium.boosts.BoostPagerBottomSheet;
import org.telegram.p029ui.Components.RLottieDrawable;
import org.telegram.p029ui.Components.RLottieImageView;
import org.telegram.p029ui.Components.SearchTagsList;
import org.telegram.p029ui.Components.SizeNotifierFrameLayout;
import org.telegram.p029ui.Components.StickersAlert;
import org.telegram.p029ui.Components.TermsOfServiceView;
import org.telegram.p029ui.Components.TextStyleSpan;
import org.telegram.p029ui.Components.ThemeEditorView;
import org.telegram.p029ui.Components.UndoView;
import org.telegram.p029ui.Components.inset.WindowAnimatedInsetsProvider;
import org.telegram.p029ui.Components.spoilers.SpoilerEffect2;
import org.telegram.p029ui.Components.voip.RTMPStreamPipOverlay;
import org.telegram.p029ui.Components.voip.VoIPHelper;
import org.telegram.p029ui.DialogsActivity;
import org.telegram.p029ui.Gifts.AuctionJoinSheet;
import org.telegram.p029ui.Gifts.GiftSheet;
import org.telegram.p029ui.LocationActivity;
import org.telegram.p029ui.PaymentFormActivity;
import org.telegram.p029ui.SelectAnimatedEmojiDialog;
import org.telegram.p029ui.Stars.ISuperRipple;
import org.telegram.p029ui.Stars.StarGiftPreviewSheet;
import org.telegram.p029ui.Stars.StarGiftSheet;
import org.telegram.p029ui.Stars.StarsController;
import org.telegram.p029ui.Stars.SuperRipple;
import org.telegram.p029ui.Stories.LiveStoryPipOverlay;
import org.telegram.p029ui.Stories.StoriesController;
import org.telegram.p029ui.Stories.StoriesListPlaceProvider;
import org.telegram.p029ui.Stories.StoryViewer;
import org.telegram.p029ui.Stories.recorder.StoryRecorder;
import org.telegram.p029ui.WallpapersListActivity;
import org.telegram.p029ui.bots.BotWebViewSheet;
import org.telegram.p029ui.bots.WebViewRequestProps;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.Vector;
import org.telegram.tgnet.p028tl.TL_account;
import org.telegram.tgnet.p028tl.TL_chatlists;
import org.telegram.tgnet.p028tl.TL_payments;
import org.telegram.tgnet.p028tl.TL_stars;
import org.telegram.tgnet.p028tl.TL_stories;
import org.webrtc.MediaStreamTrack;
import org.webrtc.voiceengine.WebRtcAudioTrack;
import p022j$.time.Duration;
import p022j$.util.function.Consumer$CC;

/* JADX INFO: loaded from: classes3.dex */
public class LaunchActivity extends BasePermissionsActivity implements INavigationLayout.INavigationLayoutDelegate, NotificationCenter.NotificationCenterDelegate, DialogsActivity.DialogsActivityDelegate, IPipActivity {
    public static LaunchActivity instance;
    public static boolean isActive;
    public static boolean isResumed;
    public static Runnable onResumeStaticCallback;
    private static LaunchActivity staticInstanceForAlerts;
    public static boolean systemBlurEnabled;
    private static Pattern timestampPattern;
    public static Runnable whenResumed;
    public ActionBarLayout actionBarLayout;
    private long alreadyShownFreeDiscSpaceAlertForced;
    private SizeNotifierFrameLayout backgroundTablet;
    private final LiteMode.BatteryReceiver batteryReceiver;
    private BlockingUpdateView blockingUpdateView;
    private Consumer blurListener;
    private BottomSheetTabsOverlay bottomSheetTabsOverlay;
    private boolean checkFreeDiscSpaceShown;
    private ArrayList contactsToSend;
    private Uri contactsToSendUri;
    private int currentConnectionState;
    private ISuperRipple currentRipple;
    private String documentsMimeType;
    private ArrayList documentsOriginalPathsArray;
    private ArrayList documentsPathsArray;
    private ArrayList documentsUrisArray;
    public DrawerLayoutContainer drawerLayoutContainer;
    private HashMap englishLocaleStrings;
    private Uri exportingChatUri;
    View feedbackView;
    private boolean finished;
    private FireworksOverlay fireworksOverlay;
    private FlagSecureReason flagSecureReason;
    public FrameLayout frameLayout;
    private FrameMetricsOverlayView frameMetricsOverlayView;
    private ArrayList importingStickers;
    private ArrayList importingStickersEmoji;
    private String importingStickersSoftware;
    private boolean isNavigationBarColorFrozen;
    private boolean isStarted;
    private RelativeLayout launchLayout;
    private ActionBarLayout layersActionBarLayout;
    private boolean loadingLocaleDialog;
    private TLRPC.TL_theme loadingTheme;
    private boolean loadingThemeAccent;
    private String loadingThemeFileName;
    private Theme.ThemeInfo loadingThemeInfo;
    private AlertDialog loadingThemeProgressDialog;
    private TLRPC.TL_wallPaper loadingThemeWallpaper;
    private String loadingThemeWallpaperName;
    private Dialog localeDialog;
    private Runnable lockRunnable;
    private ValueAnimator navBarAnimator;
    private boolean navigateToPremiumBot;
    public Runnable navigateToPremiumGiftCallback;
    private Object onBackAnimationCallback;
    private Object onBackInvokedCallback;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private Utilities.Callback onPowerSaverCallback;
    private final List onUserLeaveHintListeners;
    private List overlayPasscodeViews;
    private PasscodeViewDialog passcodeDialog;
    private Intent passcodeSaveIntent;
    private boolean passcodeSaveIntentIsNew;
    private boolean passcodeSaveIntentIsRestore;
    private ArrayList photoPathsArray;
    private final PipActivityController pipActivityController;
    private final IPipActivityHandler pipActivityHandler;
    private Dialog proxyErrorDialog;
    private final SparseIntArray requestedPermissions;
    private int requsetPermissionsPointer;
    public ActionBarLayout rightActionBarLayout;
    private View rippleAbove;
    private WindowAnimatedInsetsProvider rootAnimatedInsetsListener;
    private SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialog;
    private String sendingText;
    private FrameLayout shadowTablet;
    private FrameLayout shadowTabletSide;
    private boolean switchingAccount;
    private HashMap systemLocaleStrings;
    private boolean tabletFullSize;
    private int[] tempLocation;
    private TermsOfServiceView termsOfServiceView;
    private ImageView themeSwitchImageView;
    private ImageView themeSwitchSunView;
    private String videoPath;
    private ActionMode visibleActionMode;
    public final ArrayList visibleDialogs;
    private String voicePath;
    private boolean wasMutedByAdminRaisedHand;
    private Utilities.Callback webviewShareAPIDoneListener;
    public static final Pattern PREFIX_T_ME_PATTERN = Pattern.compile("^(?:http(?:s|)://|)([A-z0-9-]+?)\\.t\\.me");
    private static final ArrayList mainFragmentsStack = new ArrayList();
    private static final ArrayList layerFragmentsStack = new ArrayList();
    private static final ArrayList rightFragmentsStack = new ArrayList();
    private ExteraResources res = null;
    private AssetManager assetManager = null;
    private boolean startWithSafeMode = false;
    public ArrayList sheetFragmentsStack = new ArrayList();

    public static /* synthetic */ void $r8$lambda$65VHYkg1vsJ3EsSWZZHCDAFY2UU(View view) {
    }

    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    public /* synthetic */ boolean needPresentFragment(BaseFragment baseFragment, boolean z, boolean z2, INavigationLayout iNavigationLayout) {
        return INavigationLayout.INavigationLayoutDelegate.CC.$default$needPresentFragment(this, baseFragment, z, z2, iNavigationLayout);
    }

    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    public /* synthetic */ void onMeasureOverride(int[] iArr) {
        INavigationLayout.INavigationLayoutDelegate.CC.$default$onMeasureOverride(this, iArr);
    }

    public LaunchActivity() {
        PipActivityController pipActivityController = new PipActivityController(this);
        this.pipActivityController = pipActivityController;
        this.pipActivityHandler = pipActivityController.getHandler();
        this.overlayPasscodeViews = new ArrayList();
        this.visibleDialogs = new ArrayList();
        this.isNavigationBarColorFrozen = false;
        this.onUserLeaveHintListeners = new ArrayList();
        this.requestedPermissions = new SparseIntArray();
        this.requsetPermissionsPointer = 5934;
        this.blurListener = new Consumer() { // from class: org.telegram.ui.LaunchActivity.1
            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer$CC.$default$andThen(this, consumer);
            }

            C58941() {
            }

            @Override // java.util.function.Consumer
            public void m940v(Boolean bool) {
                LaunchActivity.systemBlurEnabled = bool.booleanValue();
            }
        };
        this.batteryReceiver = new LiteMode.BatteryReceiver();
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        if (this.assetManager != super.getResources().getAssets()) {
            this.res = new ExteraResources(super.getResources());
            this.assetManager = super.getResources().getAssets();
        }
        return this.res;
    }

    public Dialog getVisibleDialog() {
        for (int size = this.visibleDialogs.size() - 1; size >= 0; size--) {
            Dialog dialog = (Dialog) this.visibleDialogs.get(size);
            if (dialog.isShowing()) {
                return dialog;
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$1 */
    class C58941 implements Consumer {
        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        C58941() {
        }

        @Override // java.util.function.Consumer
        public void m940v(Boolean bool) {
            LaunchActivity.systemBlurEnabled = bool.booleanValue();
        }
    }

    public WindowAnimatedInsetsProvider getRootAnimatedInsetsListener() {
        return this.rootAnimatedInsetsListener;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws Throwable {
        ActionBarLayout actionBarLayout;
        Intent intent;
        isActive = true;
        if (BuildVars.DEBUG_VERSION) {
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy()).detectLeakedClosableObjects().penaltyLog().build());
        }
        instance = this;
        ApplicationLoader.postInitApplication();
        AndroidUtilities.checkDisplaySize(this, getResources().getConfiguration());
        int i = UserConfig.selectedAccount;
        this.currentAccount = i;
        if (!DialogsActivity.dialogsLoaded[i]) {
            DialogsActivity.setFirstCreate(true);
        }
        registerReceiver(this.batteryReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (!UserConfig.getInstance(this.currentAccount).isClientActivated() && (intent = getIntent()) != null && intent.getAction() != null && ("android.intent.action.SEND".equals(intent.getAction()) || "android.intent.action.SEND_MULTIPLE".equals(intent.getAction()))) {
            super.onCreate(bundle);
            finish();
            return;
        }
        requestWindowFeature(1);
        setTheme(C2888R.style.Theme_TMessages);
        try {
            setTaskDescription(new ActivityManager.TaskDescription((String) null, (Bitmap) null, Theme.getColor(Theme.key_actionBarDefault) | (-16777216)));
        } catch (Throwable unused) {
        }
        getWindow().setBackgroundDrawableResource(C2888R.drawable.transparent);
        FlagSecureReason flagSecureReason = new FlagSecureReason(getWindow(), new FlagSecureReason.FlagSecureCondition() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda14
            @Override // org.telegram.messenger.FlagSecureReason.FlagSecureCondition
            public final boolean run() {
                return LaunchActivity.$r8$lambda$zgDmHDYwCMzcay6OxRkyPCmZOe8();
            }
        });
        this.flagSecureReason = flagSecureReason;
        flagSecureReason.attach();
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 24) {
            AndroidUtilities.isInMultiwindow = isInMultiWindowMode();
        }
        Theme.createCommonChatResources();
        Theme.createDialogsResources(this);
        if (SharedConfig.passcodeHash.length() != 0 && SharedConfig.appLocked) {
            SharedConfig.lastPauseTime = (int) (SystemClock.elapsedRealtime() / 1000);
        }
        AndroidUtilities.fillStatusBarHeight(this, false);
        this.actionBarLayout = new ActionBarLayout(this, true);
        C59052 c59052 = new FrameLayout(this) { // from class: org.telegram.ui.LaunchActivity.2
            C59052(final Context this) {
                super(this);
            }

            @Override // android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                super.dispatchDraw(canvas);
                LaunchActivity.this.drawRippleAbove(canvas, this);
            }

            @Override // android.view.ViewGroup, android.view.View
            public WindowInsets dispatchApplyWindowInsets(WindowInsets windowInsets) {
                return AndroidUtilities.fixedDispatchApplyWindowInsets(windowInsets, this);
            }
        };
        this.frameLayout = c59052;
        c59052.setClipToPadding(false);
        this.frameLayout.setClipChildren(false);
        setContentView(this.frameLayout);
        this.rootAnimatedInsetsListener = new WindowAnimatedInsetsProvider(this.frameLayout);
        this.pipActivityController.addPipListener(new IPipActivityListener() { // from class: org.telegram.ui.LaunchActivity.3
            @Override // org.telegram.messenger.pip.activity.IPipActivityListener
            public /* synthetic */ void onCompleteExitFromPip(boolean z) {
                IPipActivityListener.CC.$default$onCompleteExitFromPip(this, z);
            }

            @Override // org.telegram.messenger.pip.activity.IPipActivityListener
            public /* synthetic */ void onStartEnterToPip() {
                IPipActivityListener.CC.$default$onStartEnterToPip(this);
            }

            C59083() {
            }

            @Override // org.telegram.messenger.pip.activity.IPipActivityListener
            public void onCompleteEnterToPip() {
                LaunchActivity.this.frameLayout.setVisibility(8);
            }

            @Override // org.telegram.messenger.pip.activity.IPipActivityListener
            public void onStartExitFromPip(boolean z) {
                LaunchActivity.this.frameLayout.setVisibility(0);
            }
        });
        ((ViewGroup) getWindow().getDecorView()).addView(this.pipActivityController.getPipContentView());
        this.pipActivityController.getPipContentView().bringToFront();
        ImageView imageView = new ImageView(this);
        this.themeSwitchImageView = imageView;
        imageView.setVisibility(8);
        C59094 c59094 = new C59094(this);
        this.drawerLayoutContainer = c59094;
        c59094.setClipChildren(false);
        this.drawerLayoutContainer.setClipToPadding(false);
        this.drawerLayoutContainer.setBehindKeyboardColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.frameLayout.addView(this.drawerLayoutContainer, LayoutHelper.createFrame(-1, -1.0f));
        C59105 c59105 = new ImageView(this) { // from class: org.telegram.ui.LaunchActivity.5
            C59105(final Context this) {
                super(this);
            }

            @Override // android.widget.ImageView, android.view.View
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                invalidate();
            }
        };
        this.themeSwitchSunView = c59105;
        c59105.setScaleType(ImageView.ScaleType.CENTER);
        this.frameLayout.addView(this.themeSwitchSunView, LayoutHelper.createFrame(48, 48.0f));
        this.themeSwitchSunView.setVisibility(8);
        FrameLayout frameLayout = this.frameLayout;
        BottomSheetTabsOverlay bottomSheetTabsOverlay = new BottomSheetTabsOverlay(this);
        this.bottomSheetTabsOverlay = bottomSheetTabsOverlay;
        frameLayout.addView(bottomSheetTabsOverlay);
        FrameLayout frameLayout2 = this.frameLayout;
        C59116 c59116 = new FireworksOverlay(this) { // from class: org.telegram.ui.LaunchActivity.6
            C59116(final Context this) {
                super(this);
                setVisibility(8);
            }

            @Override // org.telegram.p029ui.Components.FireworksOverlay
            public void start(boolean z) {
                setVisibility(0);
                super.start(z);
            }

            @Override // org.telegram.p029ui.Components.FireworksOverlay
            protected void onStop() {
                super.onStop();
                setVisibility(8);
            }
        };
        this.fireworksOverlay = c59116;
        frameLayout2.addView(c59116);
        setupActionBarLayout();
        this.drawerLayoutContainer.setParentActionBarLayout(this.actionBarLayout);
        this.actionBarLayout.setDrawerLayoutContainer(this.drawerLayoutContainer);
        syncDrawerContainerEnabled();
        this.actionBarLayout.setFragmentStack(mainFragmentsStack);
        this.actionBarLayout.setFragmentStackChangedListener(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onCreate$1();
            }
        });
        this.actionBarLayout.setDelegate(this);
        Theme.loadWallpaper(true);
        checkCurrentAccount();
        updateCurrentConnectionState();
        NotificationCenter globalInstance = NotificationCenter.getGlobalInstance();
        int i2 = NotificationCenter.closeOtherAppActivities;
        globalInstance.lambda$postNotificationNameOnUIThread$1(i2, this);
        this.currentConnectionState = ConnectionsManager.getInstance(this.currentAccount).getConnectionState();
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.needShowAlert);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.reloadInterface);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.suggestedLangpack);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.didSetNewTheme);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.needSetDayNightTheme);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.needCheckSystemBarColors);
        NotificationCenter.getGlobalInstance().addObserver(this, i2);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.didSetPasscode);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.didSetNewWallpapper);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.screenStateChanged);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.showBulletin);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.requestPermissions);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.billingConfirmPurchaseError);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.tlSchemeParseException);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.pluginMenuItemsUpdated);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        Utilities.Callback callback = new Utilities.Callback() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda16
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.onPowerSaver(((Boolean) obj).booleanValue());
            }
        };
        this.onPowerSaverCallback = callback;
        LiteMode.addOnPowerSaverAppliedListener(callback);
        if (this.actionBarLayout.getFragmentStack().isEmpty() && ((actionBarLayout = this.layersActionBarLayout) == null || actionBarLayout.getFragmentStack().isEmpty())) {
            if (UserConfig.getInstance(this.currentAccount).isClientActivated()) {
                this.actionBarLayout.addFragmentToStack(new MainTabsActivity());
            } else {
                this.actionBarLayout.addFragmentToStack(getClientNotActivatedFragment());
            }
            if (bundle != null) {
                try {
                    String string = bundle.getString("fragment");
                    if (string != null) {
                        Bundle bundle2 = bundle.getBundle("args");
                        switch (string.hashCode()) {
                            case -1529105743:
                                if (string.equals("wallpapers")) {
                                    WallpapersListActivity wallpapersListActivity = new WallpapersListActivity(0);
                                    this.actionBarLayout.addFragmentToStack(wallpapersListActivity);
                                    wallpapersListActivity.restoreSelfArgs(bundle);
                                }
                                break;
                            case -1349522494:
                                if (string.equals("chat_profile") && bundle2 != null) {
                                    ProfileActivity profileActivity = new ProfileActivity(bundle2);
                                    if (this.actionBarLayout.addFragmentToStack(profileActivity)) {
                                        profileActivity.restoreSelfArgs(bundle);
                                    }
                                }
                                break;
                            case 3052376:
                                if (string.equals("chat") && bundle2 != null) {
                                    ChatActivity chatActivity = new ChatActivity(bundle2);
                                    if (this.actionBarLayout.addFragmentToStack(chatActivity)) {
                                        chatActivity.restoreSelfArgs(bundle);
                                    }
                                }
                                break;
                            case 98629247:
                                if (string.equals("group") && bundle2 != null) {
                                    GroupCreateFinalActivity groupCreateFinalActivity = new GroupCreateFinalActivity(bundle2);
                                    if (this.actionBarLayout.addFragmentToStack(groupCreateFinalActivity)) {
                                        groupCreateFinalActivity.restoreSelfArgs(bundle);
                                    }
                                }
                                break;
                            case 738950403:
                                if (string.equals("channel") && bundle2 != null) {
                                    ChannelCreateActivity channelCreateActivity = new ChannelCreateActivity(bundle2);
                                    if (this.actionBarLayout.addFragmentToStack(channelCreateActivity)) {
                                        channelCreateActivity.restoreSelfArgs(bundle);
                                    }
                                }
                                break;
                            case 1434631203:
                                if (string.equals("settings")) {
                                    bundle2.putLong("user_id", UserConfig.getInstance(this.currentAccount).getClientUserId());
                                    ProfileActivity profileActivity2 = new ProfileActivity(bundle2);
                                    this.actionBarLayout.addFragmentToStack(profileActivity2);
                                    profileActivity2.restoreSelfArgs(bundle);
                                }
                                break;
                            case 1523894383:
                                if (string.equals("settings2")) {
                                    this.actionBarLayout.addFragmentToStack(new SettingsActivity());
                                }
                                break;
                        }
                    }
                } catch (Exception e) {
                    FileLog.m1136e(e);
                }
            }
        }
        checkLayout();
        checkSystemBarColors();
        handleIntent(getIntent(), false, bundle != null, false, null, true, true);
        try {
            String str = Build.DISPLAY;
            String str2 = Build.USER;
            String lowerCase = _UrlKt.FRAGMENT_ENCODE_SET;
            String lowerCase2 = str != null ? str.toLowerCase() : _UrlKt.FRAGMENT_ENCODE_SET;
            if (str2 != null) {
                lowerCase = lowerCase2.toLowerCase();
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1133d("OS name " + lowerCase2 + " " + lowerCase);
            }
            if ((lowerCase2.contains("flyme") || lowerCase.contains("flyme")) && Build.VERSION.SDK_INT <= 24) {
                AndroidUtilities.incorrectDisplaySizeFix = true;
                final View rootView = getWindow().getDecorView().getRootView();
                ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();
                ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda17
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        LaunchActivity.$r8$lambda$DDOCmwt7UXAtUfyR4lE720_wK_U(rootView);
                    }
                };
                this.onGlobalLayoutListener = onGlobalLayoutListener;
                viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener);
            }
        } catch (Exception e2) {
            FileLog.m1136e(e2);
        }
        MediaController.getInstance().setBaseActivity(this, true);
        int i3 = Build.VERSION.SDK_INT;
        FingerprintController.checkKeyReady();
        if (i3 >= 28 && ((ActivityManager) getSystemService("activity")).isBackgroundRestricted() && System.currentTimeMillis() - SharedConfig.BackgroundActivityPrefs.getLastCheckedBackgroundActivity() >= 86400000 && SharedConfig.BackgroundActivityPrefs.getDismissedCount() < 3) {
            AlertsCreator.createBackgroundActivityDialog(this).show();
            SharedConfig.BackgroundActivityPrefs.setLastCheckedBackgroundActivity(System.currentTimeMillis());
        }
        if (i3 >= 31) {
            MonetUtils.registerReceiver(this);
            getWindow().getDecorView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: org.telegram.ui.LaunchActivity.7
                ViewOnAttachStateChangeListenerC59127() {
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View view) {
                    LaunchActivity.this.getWindowManager().addCrossWindowBlurEnabledListener(LaunchActivity.this.blurListener);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view) {
                    LaunchActivity.this.getWindowManager().removeCrossWindowBlurEnabledListener(LaunchActivity.this.blurListener);
                }
            });
        }
        Bulletin.addDelegate(this.frameLayout, new Bulletin.Delegate() { // from class: org.telegram.ui.LaunchActivity.8
            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ boolean allowLayoutChanges() {
                return Bulletin.Delegate.CC.$default$allowLayoutChanges(this);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ boolean bottomOffsetAnimated() {
                return Bulletin.Delegate.CC.$default$bottomOffsetAnimated(this);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ boolean clipWithGradient(int i4) {
                return Bulletin.Delegate.CC.$default$clipWithGradient(this, i4);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ int getTopOffset(int i4) {
                return Bulletin.Delegate.CC.$default$getTopOffset(this, i4);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ void onBottomOffsetChange(float f) {
                Bulletin.Delegate.CC.$default$onBottomOffsetChange(this, f);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ void onHide(Bulletin bulletin) {
                Bulletin.Delegate.CC.$default$onHide(this, bulletin);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ void onShow(Bulletin bulletin) {
                Bulletin.Delegate.CC.$default$onShow(this, bulletin);
            }

            C59138() {
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public int getBottomOffset(int i4) {
                return AndroidUtilities.navigationBarHeight + AndroidUtilities.m1124dp(16.0f);
            }
        });
        getWindow().getDecorView().setSystemUiVisibility(1792);
        AndroidUtilities.enableEdgeToEdge(this);
        nativeConnect();
        checkAppUpdate(false);
        ExteraConfig.init(this.startWithSafeMode);
        if (this.startWithSafeMode) {
            this.startWithSafeMode = false;
        }
        BackupAgent.requestBackup(ApplicationLoader.applicationContext != null ? ApplicationLoader.applicationContext : this);
        RestrictedLanguagesSelectActivity.checkRestrictedLanguages(false);
        if (i3 >= 34 && ExteraConfig.predictiveBackAnimation) {
            if (this.onBackAnimationCallback == null) {
                this.onBackAnimationCallback = new OnBackAnimationCallback() { // from class: org.telegram.ui.LaunchActivity.9
                    private boolean predictiveBackStarted;
                    private boolean started = false;
                    private boolean invoked = false;
                    private boolean drawerPredictiveBackStarted = false;

                    C59149() {
                    }

                    public void onBackInvoked() {
                        DrawerLayoutContainer drawerLayoutContainer;
                        this.invoked = true;
                        if (AndroidUtilities.isTablet()) {
                            LaunchActivity.this.onBackPressed();
                            return;
                        }
                        if (LaunchActivity.this.onBackPressed(true)) {
                            if (this.drawerPredictiveBackStarted && (drawerLayoutContainer = LaunchActivity.this.drawerLayoutContainer) != null && drawerLayoutContainer.getDrawerContainer() != null) {
                                LaunchActivity.this.drawerLayoutContainer.getDrawerContainer().commitPredictiveBack();
                                return;
                            }
                            LaunchActivity launchActivity = LaunchActivity.this;
                            ActionBarLayout actionBarLayout2 = launchActivity.actionBarLayout;
                            if (actionBarLayout2 != null) {
                                actionBarLayout2.onBackInvoked();
                            } else {
                                launchActivity.onBackPressed();
                            }
                        }
                    }

                    public void onBackStarted(BackEvent backEvent) {
                        this.started = true;
                        this.invoked = false;
                        this.predictiveBackStarted = false;
                        this.drawerPredictiveBackStarted = false;
                    }

                    private void onBackStartedInternal(BackEvent backEvent) {
                        if (!AndroidUtilities.isTablet() && LaunchActivity.this.onBackPressed(false)) {
                            DrawerLayoutContainer drawerLayoutContainer = LaunchActivity.this.drawerLayoutContainer;
                            if (drawerLayoutContainer != null && drawerLayoutContainer.getDrawerContainer() != null && LaunchActivity.this.drawerLayoutContainer.getDrawerContainer().startPredictiveBack()) {
                                this.drawerPredictiveBackStarted = true;
                                return;
                            }
                            ActionBarLayout actionBarLayout2 = LaunchActivity.this.actionBarLayout;
                            if (actionBarLayout2 != null) {
                                actionBarLayout2.onBackStarted(backEvent.getTouchX(), backEvent.getTouchY());
                            }
                        }
                    }

                    public void onBackProgressed(BackEvent backEvent) {
                        DrawerLayoutContainer drawerLayoutContainer;
                        if (this.started && this.invoked) {
                            return;
                        }
                        float progress = backEvent.getProgress();
                        if (!this.predictiveBackStarted && progress > 0.015f) {
                            this.predictiveBackStarted = true;
                            onBackStartedInternal(backEvent);
                        }
                        float fMax = Math.max(0.0f, progress - 0.015f) / 0.985f;
                        if (AndroidUtilities.isTablet()) {
                            return;
                        }
                        if (this.drawerPredictiveBackStarted && (drawerLayoutContainer = LaunchActivity.this.drawerLayoutContainer) != null && drawerLayoutContainer.getDrawerContainer() != null) {
                            LaunchActivity.this.drawerLayoutContainer.getDrawerContainer().updatePredictiveBackProgress(fMax);
                            return;
                        }
                        ActionBarLayout actionBarLayout2 = LaunchActivity.this.actionBarLayout;
                        if (actionBarLayout2 != null) {
                            actionBarLayout2.onBackProgress(fMax);
                        }
                    }

                    public void onBackCancelled() {
                        DrawerLayoutContainer drawerLayoutContainer;
                        this.started = false;
                        this.invoked = false;
                        if (AndroidUtilities.isTablet()) {
                            return;
                        }
                        if (this.drawerPredictiveBackStarted && (drawerLayoutContainer = LaunchActivity.this.drawerLayoutContainer) != null && drawerLayoutContainer.getDrawerContainer() != null) {
                            LaunchActivity.this.drawerLayoutContainer.getDrawerContainer().cancelPredictiveBack();
                            this.drawerPredictiveBackStarted = false;
                        } else {
                            ActionBarLayout actionBarLayout2 = LaunchActivity.this.actionBarLayout;
                            if (actionBarLayout2 != null) {
                                actionBarLayout2.onBackCancelled();
                            }
                        }
                    }
                };
            }
            getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, LaunchActivity$$ExternalSyntheticApiModelOutline0.m1286m(this.onBackAnimationCallback));
        } else if (i3 >= 33) {
            if (this.onBackInvokedCallback == null) {
                this.onBackInvokedCallback = new OnBackInvokedCallback() { // from class: org.telegram.ui.LaunchActivity.10
                    C589510() {
                    }

                    public void onBackInvoked() {
                        if (AndroidUtilities.isTablet()) {
                            LaunchActivity.this.onBackPressed();
                            return;
                        }
                        if (LaunchActivity.this.onBackPressed(true)) {
                            LaunchActivity launchActivity = LaunchActivity.this;
                            ActionBarLayout actionBarLayout2 = launchActivity.actionBarLayout;
                            if (actionBarLayout2 != null) {
                                actionBarLayout2.onBackInvoked();
                            } else {
                                launchActivity.onBackPressed();
                            }
                        }
                    }
                };
            }
            getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, C0042xf5eeb9b9.m2m(this.onBackInvokedCallback));
        }
        checkFrameMetrics();
    }

    public static /* synthetic */ boolean $r8$lambda$zgDmHDYwCMzcay6OxRkyPCmZOe8() {
        return SharedConfig.passcodeHash.length() > 0 && !SharedConfig.allowScreenCapture;
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$2 */
    class C59052 extends FrameLayout {
        C59052(final LaunchActivity this) {
            super(this);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            LaunchActivity.this.drawRippleAbove(canvas, this);
        }

        @Override // android.view.ViewGroup, android.view.View
        public WindowInsets dispatchApplyWindowInsets(WindowInsets windowInsets) {
            return AndroidUtilities.fixedDispatchApplyWindowInsets(windowInsets, this);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$3 */
    class C59083 implements IPipActivityListener {
        @Override // org.telegram.messenger.pip.activity.IPipActivityListener
        public /* synthetic */ void onCompleteExitFromPip(boolean z) {
            IPipActivityListener.CC.$default$onCompleteExitFromPip(this, z);
        }

        @Override // org.telegram.messenger.pip.activity.IPipActivityListener
        public /* synthetic */ void onStartEnterToPip() {
            IPipActivityListener.CC.$default$onStartEnterToPip(this);
        }

        C59083() {
        }

        @Override // org.telegram.messenger.pip.activity.IPipActivityListener
        public void onCompleteEnterToPip() {
            LaunchActivity.this.frameLayout.setVisibility(8);
        }

        @Override // org.telegram.messenger.pip.activity.IPipActivityListener
        public void onStartExitFromPip(boolean z) {
            LaunchActivity.this.frameLayout.setVisibility(0);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$4 */
    class C59094 extends DrawerLayoutContainer {
        private boolean wasPortrait;

        C59094(Context context) {
            super(context);
        }

        @Override // org.telegram.p029ui.ActionBar.DrawerLayoutContainer, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) throws Exception {
            super.onLayout(z, i, i2, i3, i4);
            boolean z2 = i4 - i2 > i3 - i;
            if (z2 != this.wasPortrait) {
                post(new Runnable() { // from class: org.telegram.ui.LaunchActivity$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onLayout$0();
                    }
                });
                this.wasPortrait = z2;
            }
        }

        public /* synthetic */ void lambda$onLayout$0() {
            if (LaunchActivity.this.selectAnimatedEmojiDialog != null) {
                LaunchActivity.this.selectAnimatedEmojiDialog.dismiss();
                LaunchActivity.this.selectAnimatedEmojiDialog = null;
            }
        }

        @Override // org.telegram.p029ui.ActionBar.DrawerLayoutContainer, android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            if (LaunchActivity.this.actionBarLayout.getParent() == this) {
                LaunchActivity.this.actionBarLayout.parentDraw(this, canvas);
            }
            super.dispatchDraw(canvas);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$5 */
    class C59105 extends ImageView {
        C59105(final LaunchActivity this) {
            super(this);
        }

        @Override // android.widget.ImageView, android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$6 */
    class C59116 extends FireworksOverlay {
        C59116(final LaunchActivity this) {
            super(this);
            setVisibility(8);
        }

        @Override // org.telegram.p029ui.Components.FireworksOverlay
        public void start(boolean z) {
            setVisibility(0);
            super.start(z);
        }

        @Override // org.telegram.p029ui.Components.FireworksOverlay
        protected void onStop() {
            super.onStop();
            setVisibility(8);
        }
    }

    public /* synthetic */ void lambda$onCreate$1() {
        checkSystemBarColors(true, false);
        if (getLastFragment() == null || getLastFragment().getLastStoryViewer() == null) {
            return;
        }
        getLastFragment().getLastStoryViewer().updatePlayingMode();
    }

    public static /* synthetic */ void $r8$lambda$DDOCmwt7UXAtUfyR4lE720_wK_U(View view) {
        int measuredHeight = view.getMeasuredHeight();
        FileLog.m1133d("height = " + measuredHeight + " displayHeight = " + AndroidUtilities.displaySize.y);
        int i = (measuredHeight - AndroidUtilities.navigationBarHeight) - AndroidUtilities.statusBarHeight;
        if (i <= AndroidUtilities.m1124dp(100.0f) || i >= AndroidUtilities.displaySize.y) {
            return;
        }
        int iM1124dp = AndroidUtilities.m1124dp(100.0f) + i;
        Point point = AndroidUtilities.displaySize;
        if (iM1124dp > point.y) {
            point.y = i;
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1133d("fix display size y to " + AndroidUtilities.displaySize.y);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$7 */
    class ViewOnAttachStateChangeListenerC59127 implements View.OnAttachStateChangeListener {
        ViewOnAttachStateChangeListenerC59127() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
            LaunchActivity.this.getWindowManager().addCrossWindowBlurEnabledListener(LaunchActivity.this.blurListener);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            LaunchActivity.this.getWindowManager().removeCrossWindowBlurEnabledListener(LaunchActivity.this.blurListener);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$8 */
    class C59138 implements Bulletin.Delegate {
        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public /* synthetic */ boolean allowLayoutChanges() {
            return Bulletin.Delegate.CC.$default$allowLayoutChanges(this);
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public /* synthetic */ boolean bottomOffsetAnimated() {
            return Bulletin.Delegate.CC.$default$bottomOffsetAnimated(this);
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public /* synthetic */ boolean clipWithGradient(int i4) {
            return Bulletin.Delegate.CC.$default$clipWithGradient(this, i4);
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public /* synthetic */ int getTopOffset(int i4) {
            return Bulletin.Delegate.CC.$default$getTopOffset(this, i4);
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public /* synthetic */ void onBottomOffsetChange(float f) {
            Bulletin.Delegate.CC.$default$onBottomOffsetChange(this, f);
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public /* synthetic */ void onHide(Bulletin bulletin) {
            Bulletin.Delegate.CC.$default$onHide(this, bulletin);
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public /* synthetic */ void onShow(Bulletin bulletin) {
            Bulletin.Delegate.CC.$default$onShow(this, bulletin);
        }

        C59138() {
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public int getBottomOffset(int i4) {
            return AndroidUtilities.navigationBarHeight + AndroidUtilities.m1124dp(16.0f);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$9 */
    class C59149 implements OnBackAnimationCallback {
        private boolean predictiveBackStarted;
        private boolean started = false;
        private boolean invoked = false;
        private boolean drawerPredictiveBackStarted = false;

        C59149() {
        }

        public void onBackInvoked() {
            DrawerLayoutContainer drawerLayoutContainer;
            this.invoked = true;
            if (AndroidUtilities.isTablet()) {
                LaunchActivity.this.onBackPressed();
                return;
            }
            if (LaunchActivity.this.onBackPressed(true)) {
                if (this.drawerPredictiveBackStarted && (drawerLayoutContainer = LaunchActivity.this.drawerLayoutContainer) != null && drawerLayoutContainer.getDrawerContainer() != null) {
                    LaunchActivity.this.drawerLayoutContainer.getDrawerContainer().commitPredictiveBack();
                    return;
                }
                LaunchActivity launchActivity = LaunchActivity.this;
                ActionBarLayout actionBarLayout2 = launchActivity.actionBarLayout;
                if (actionBarLayout2 != null) {
                    actionBarLayout2.onBackInvoked();
                } else {
                    launchActivity.onBackPressed();
                }
            }
        }

        public void onBackStarted(BackEvent backEvent) {
            this.started = true;
            this.invoked = false;
            this.predictiveBackStarted = false;
            this.drawerPredictiveBackStarted = false;
        }

        private void onBackStartedInternal(BackEvent backEvent) {
            if (!AndroidUtilities.isTablet() && LaunchActivity.this.onBackPressed(false)) {
                DrawerLayoutContainer drawerLayoutContainer = LaunchActivity.this.drawerLayoutContainer;
                if (drawerLayoutContainer != null && drawerLayoutContainer.getDrawerContainer() != null && LaunchActivity.this.drawerLayoutContainer.getDrawerContainer().startPredictiveBack()) {
                    this.drawerPredictiveBackStarted = true;
                    return;
                }
                ActionBarLayout actionBarLayout2 = LaunchActivity.this.actionBarLayout;
                if (actionBarLayout2 != null) {
                    actionBarLayout2.onBackStarted(backEvent.getTouchX(), backEvent.getTouchY());
                }
            }
        }

        public void onBackProgressed(BackEvent backEvent) {
            DrawerLayoutContainer drawerLayoutContainer;
            if (this.started && this.invoked) {
                return;
            }
            float progress = backEvent.getProgress();
            if (!this.predictiveBackStarted && progress > 0.015f) {
                this.predictiveBackStarted = true;
                onBackStartedInternal(backEvent);
            }
            float fMax = Math.max(0.0f, progress - 0.015f) / 0.985f;
            if (AndroidUtilities.isTablet()) {
                return;
            }
            if (this.drawerPredictiveBackStarted && (drawerLayoutContainer = LaunchActivity.this.drawerLayoutContainer) != null && drawerLayoutContainer.getDrawerContainer() != null) {
                LaunchActivity.this.drawerLayoutContainer.getDrawerContainer().updatePredictiveBackProgress(fMax);
                return;
            }
            ActionBarLayout actionBarLayout2 = LaunchActivity.this.actionBarLayout;
            if (actionBarLayout2 != null) {
                actionBarLayout2.onBackProgress(fMax);
            }
        }

        public void onBackCancelled() {
            DrawerLayoutContainer drawerLayoutContainer;
            this.started = false;
            this.invoked = false;
            if (AndroidUtilities.isTablet()) {
                return;
            }
            if (this.drawerPredictiveBackStarted && (drawerLayoutContainer = LaunchActivity.this.drawerLayoutContainer) != null && drawerLayoutContainer.getDrawerContainer() != null) {
                LaunchActivity.this.drawerLayoutContainer.getDrawerContainer().cancelPredictiveBack();
                this.drawerPredictiveBackStarted = false;
            } else {
                ActionBarLayout actionBarLayout2 = LaunchActivity.this.actionBarLayout;
                if (actionBarLayout2 != null) {
                    actionBarLayout2.onBackCancelled();
                }
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$10 */
    /* JADX INFO: loaded from: classes6.dex */
    class C589510 implements OnBackInvokedCallback {
        C589510() {
        }

        public void onBackInvoked() {
            if (AndroidUtilities.isTablet()) {
                LaunchActivity.this.onBackPressed();
                return;
            }
            if (LaunchActivity.this.onBackPressed(true)) {
                LaunchActivity launchActivity = LaunchActivity.this;
                ActionBarLayout actionBarLayout2 = launchActivity.actionBarLayout;
                if (actionBarLayout2 != null) {
                    actionBarLayout2.onBackInvoked();
                } else {
                    launchActivity.onBackPressed();
                }
            }
        }
    }

    public void checkFrameMetrics() {
        if (Build.VERSION.SDK_INT >= 24) {
            if (this.frameMetricsOverlayView == null && SharedConfig.frameMetricsEnabled) {
                this.frameMetricsOverlayView = FrameMetricsOverlayView.attachToActivityCorner(this, NavigationBarView.ITEM_GRAVITY_START_CENTER, 12);
            }
            FrameMetricsOverlayView frameMetricsOverlayView = this.frameMetricsOverlayView;
            if (frameMetricsOverlayView == null || SharedConfig.frameMetricsEnabled) {
                return;
            }
            frameMetricsOverlayView.detach();
            this.frameMetricsOverlayView = null;
        }
    }

    public static void showAttachMenuBot(LaunchActivity launchActivity, int i, TLRPC.TL_attachMenuBot tL_attachMenuBot, String str, boolean z) {
        BaseFragment lastFragment = getLastFragment();
        if (lastFragment == null) {
            return;
        }
        long j = tL_attachMenuBot.bot_id;
        WebViewRequestProps webViewRequestPropsM1344of = WebViewRequestProps.m1344of(i, j, j, tL_attachMenuBot.short_name, null, 1, 0, 0L, false, null, false, str, null, 2, false, false);
        if (launchActivity.getBottomSheetTabs() == null || launchActivity.getBottomSheetTabs().tryReopenTab(webViewRequestPropsM1344of) == null) {
            TLRPC.User user = MessagesController.getInstance(i).getUser(Long.valueOf(tL_attachMenuBot.bot_id));
            String restrictionReason = user == null ? null : MessagesController.getInstance(i).getRestrictionReason(user.restriction_reason);
            if (!TextUtils.isEmpty(restrictionReason)) {
                MessagesController.getInstance(i);
                MessagesController.showCantOpenAlert(lastFragment, restrictionReason);
                return;
            }
            BotWebViewSheet botWebViewSheet = new BotWebViewSheet(launchActivity, lastFragment.getResourceProvider());
            botWebViewSheet.setNeedsContext(false);
            botWebViewSheet.setDefaultFullsize(z);
            botWebViewSheet.setParentActivity(launchActivity);
            botWebViewSheet.requestWebView(lastFragment, webViewRequestPropsM1344of);
            botWebViewSheet.show();
        }
    }

    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    public void onThemeProgress(float f) {
        if (ArticleViewer.hasInstance() && ArticleViewer.getInstance().isVisible()) {
            ArticleViewer.getInstance().updateThemeColors(f);
        }
        this.drawerLayoutContainer.setBehindKeyboardColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        if (PhotoViewer.hasInstance()) {
            PhotoViewer.getInstance().updateColors();
        }
    }

    private void setupActionBarLayout() {
        DrawerLayoutContainer drawerLayoutContainer;
        ViewGroup view;
        if (this.drawerLayoutContainer.indexOfChild(this.launchLayout) != -1) {
            drawerLayoutContainer = this.drawerLayoutContainer;
            view = this.launchLayout;
        } else {
            drawerLayoutContainer = this.drawerLayoutContainer;
            view = this.actionBarLayout.getView();
        }
        int iIndexOfChild = drawerLayoutContainer.indexOfChild(view);
        if (iIndexOfChild != -1) {
            this.drawerLayoutContainer.removeViewAt(iIndexOfChild);
        }
        if (AndroidUtilities.isTablet()) {
            getWindow().setSoftInputMode(16);
            C589611 c589611 = new RelativeLayout(this) { // from class: org.telegram.ui.LaunchActivity.11
                private boolean inLayout;
                private Path path = new Path();

                C589611(final Context this) {
                    super(this);
                    this.path = new Path();
                }

                @Override // android.widget.RelativeLayout, android.view.View, android.view.ViewParent
                public void requestLayout() {
                    if (this.inLayout) {
                        return;
                    }
                    super.requestLayout();
                }

                @Override // android.widget.RelativeLayout, android.view.View
                protected void onMeasure(int i, int i2) {
                    this.inLayout = true;
                    int size = View.MeasureSpec.getSize(i);
                    int size2 = View.MeasureSpec.getSize(i2);
                    setMeasuredDimension(size, size2);
                    if (!AndroidUtilities.isInMultiwindow && (!AndroidUtilities.isSmallTablet() || getResources().getConfiguration().orientation == 2)) {
                        LaunchActivity.this.tabletFullSize = false;
                        int iM1124dp = (size / 100) * 35;
                        if (iM1124dp < AndroidUtilities.m1124dp(320.0f)) {
                            iM1124dp = AndroidUtilities.m1124dp(320.0f);
                        }
                        LaunchActivity.this.actionBarLayout.getView().measure(View.MeasureSpec.makeMeasureSpec(iM1124dp, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size2, TLObject.FLAG_30));
                        LaunchActivity.this.shadowTabletSide.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1124dp(1.0f), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size2, TLObject.FLAG_30));
                        LaunchActivity.this.rightActionBarLayout.getView().measure(View.MeasureSpec.makeMeasureSpec(size - iM1124dp, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size2, TLObject.FLAG_30));
                    } else {
                        LaunchActivity.this.tabletFullSize = true;
                        LaunchActivity.this.actionBarLayout.getView().measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size2, TLObject.FLAG_30));
                    }
                    LaunchActivity.this.backgroundTablet.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size2, TLObject.FLAG_30));
                    LaunchActivity.this.shadowTablet.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size2, TLObject.FLAG_30));
                    LaunchActivity.this.layersActionBarLayout.getView().measure(View.MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.m1124dp(530.0f), size - AndroidUtilities.m1124dp(16.0f)), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(((size2 - AndroidUtilities.statusBarHeight) - AndroidUtilities.navigationBarHeight) - AndroidUtilities.m1124dp(16.0f), TLObject.FLAG_30));
                    this.inLayout = false;
                }

                @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
                protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                    int i5 = i3 - i;
                    if (!AndroidUtilities.isInMultiwindow && (!AndroidUtilities.isSmallTablet() || getResources().getConfiguration().orientation == 2)) {
                        int iM1124dp = (i5 / 100) * 35;
                        if (iM1124dp < AndroidUtilities.m1124dp(320.0f)) {
                            iM1124dp = AndroidUtilities.m1124dp(320.0f);
                        }
                        LaunchActivity.this.shadowTabletSide.layout(iM1124dp, 0, LaunchActivity.this.shadowTabletSide.getMeasuredWidth() + iM1124dp, LaunchActivity.this.shadowTabletSide.getMeasuredHeight());
                        LaunchActivity.this.actionBarLayout.getView().layout(0, 0, LaunchActivity.this.actionBarLayout.getView().getMeasuredWidth(), LaunchActivity.this.actionBarLayout.getView().getMeasuredHeight());
                        LaunchActivity.this.rightActionBarLayout.getView().layout(iM1124dp, 0, LaunchActivity.this.rightActionBarLayout.getView().getMeasuredWidth() + iM1124dp, LaunchActivity.this.rightActionBarLayout.getView().getMeasuredHeight());
                    } else {
                        LaunchActivity.this.actionBarLayout.getView().layout(0, 0, LaunchActivity.this.actionBarLayout.getView().getMeasuredWidth(), LaunchActivity.this.actionBarLayout.getView().getMeasuredHeight());
                    }
                    int measuredWidth = (i5 - LaunchActivity.this.layersActionBarLayout.getView().getMeasuredWidth()) / 2;
                    int iM1124dp2 = AndroidUtilities.statusBarHeight + AndroidUtilities.m1124dp(8.0f);
                    LaunchActivity.this.layersActionBarLayout.getView().layout(measuredWidth, iM1124dp2, LaunchActivity.this.layersActionBarLayout.getView().getMeasuredWidth() + measuredWidth, LaunchActivity.this.layersActionBarLayout.getView().getMeasuredHeight() + iM1124dp2);
                    LaunchActivity.this.backgroundTablet.layout(0, 0, LaunchActivity.this.backgroundTablet.getMeasuredWidth(), LaunchActivity.this.backgroundTablet.getMeasuredHeight());
                    LaunchActivity.this.shadowTablet.layout(0, 0, LaunchActivity.this.shadowTablet.getMeasuredWidth(), LaunchActivity.this.shadowTablet.getMeasuredHeight());
                }

                @Override // android.view.ViewGroup, android.view.View
                protected void dispatchDraw(Canvas canvas) {
                    if (LaunchActivity.this.layersActionBarLayout != null) {
                        LaunchActivity.this.layersActionBarLayout.parentDraw(this, canvas);
                    }
                    super.dispatchDraw(canvas);
                }

                @Override // android.view.ViewGroup, android.view.View
                public WindowInsets dispatchApplyWindowInsets(WindowInsets windowInsets) {
                    return AndroidUtilities.fixedDispatchApplyWindowInsets(windowInsets, this);
                }
            };
            this.launchLayout = c589611;
            if (iIndexOfChild != -1) {
                this.drawerLayoutContainer.addView(c589611, iIndexOfChild, LayoutHelper.createFrame(-1, -1.0f));
            } else {
                this.drawerLayoutContainer.addView(c589611, LayoutHelper.createFrame(-1, -1.0f));
            }
            C589712 c589712 = new SizeNotifierFrameLayout(this) { // from class: org.telegram.ui.LaunchActivity.12
                @Override // org.telegram.p029ui.Components.SizeNotifierFrameLayout
                protected boolean isActionBarVisible() {
                    return false;
                }

                C589712(final Context this) {
                    super(this);
                }
            };
            this.backgroundTablet = c589712;
            c589712.setOccupyStatusBar(false);
            this.backgroundTablet.setBackgroundImage(Theme.getCachedWallpaper(), Theme.isWallpaperMotion());
            this.launchLayout.addView(this.backgroundTablet, LayoutHelper.createRelative(-1, -1));
            ViewGroup viewGroup = (ViewGroup) this.actionBarLayout.getView().getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.actionBarLayout.getView());
            }
            this.launchLayout.addView(this.actionBarLayout.getView());
            ActionBarLayout actionBarLayout = new ActionBarLayout(this, false);
            this.rightActionBarLayout = actionBarLayout;
            actionBarLayout.setFragmentStack(rightFragmentsStack);
            this.rightActionBarLayout.setDelegate(this);
            this.launchLayout.addView(this.rightActionBarLayout.getView());
            FrameLayout frameLayout = new FrameLayout(this);
            this.shadowTabletSide = frameLayout;
            frameLayout.setBackgroundColor(1076449908);
            this.launchLayout.addView(this.shadowTabletSide);
            FrameLayout frameLayout2 = new FrameLayout(this);
            this.shadowTablet = frameLayout2;
            ArrayList arrayList = layerFragmentsStack;
            frameLayout2.setVisibility(arrayList.isEmpty() ? 8 : 0);
            this.shadowTablet.setBackgroundColor(2130706432);
            this.launchLayout.addView(this.shadowTablet);
            this.shadowTablet.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda47
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    return this.f$0.lambda$setupActionBarLayout$3(view2, motionEvent);
                }
            });
            this.shadowTablet.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda48
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    LaunchActivity.$r8$lambda$65VHYkg1vsJ3EsSWZZHCDAFY2UU(view2);
                }
            });
            ActionBarLayout actionBarLayout2 = new ActionBarLayout(this, false);
            this.layersActionBarLayout = actionBarLayout2;
            actionBarLayout2.setIsLayersLayout();
            this.layersActionBarLayout.setRemoveActionBarExtraHeight(true);
            this.layersActionBarLayout.setBackgroundView(this.shadowTablet);
            this.layersActionBarLayout.setUseAlphaAnimations(true);
            this.layersActionBarLayout.setFragmentStack(arrayList);
            this.layersActionBarLayout.setDelegate(this);
            this.layersActionBarLayout.setDrawerLayoutContainer(this.drawerLayoutContainer);
            ViewGroup view2 = this.layersActionBarLayout.getView();
            view2.setBackgroundResource(C2888R.drawable.popup_fixed_alert3);
            view2.setClipToOutline(true);
            view2.setVisibility(arrayList.isEmpty() ? 8 : 0);
            this.launchLayout.addView(view2);
        } else {
            ViewGroup viewGroup2 = (ViewGroup) this.actionBarLayout.getView().getParent();
            if (viewGroup2 != null) {
                viewGroup2.removeView(this.actionBarLayout.getView());
            }
            this.actionBarLayout.setFragmentStack(mainFragmentsStack);
            if (iIndexOfChild != -1) {
                this.drawerLayoutContainer.addView(this.actionBarLayout.getView(), iIndexOfChild, new ViewGroup.LayoutParams(-1, -1));
            } else {
                this.drawerLayoutContainer.addView(this.actionBarLayout.getView(), new ViewGroup.LayoutParams(-1, -1));
            }
        }
        FloatingDebugController.setActive(this, SharedConfig.isFloatingDebugActive, false);
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$11 */
    /* JADX INFO: loaded from: classes6.dex */
    class C589611 extends RelativeLayout {
        private boolean inLayout;
        private Path path = new Path();

        C589611(final LaunchActivity this) {
            super(this);
            this.path = new Path();
        }

        @Override // android.widget.RelativeLayout, android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (this.inLayout) {
                return;
            }
            super.requestLayout();
        }

        @Override // android.widget.RelativeLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            this.inLayout = true;
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            setMeasuredDimension(size, size2);
            if (!AndroidUtilities.isInMultiwindow && (!AndroidUtilities.isSmallTablet() || getResources().getConfiguration().orientation == 2)) {
                LaunchActivity.this.tabletFullSize = false;
                int iM1124dp = (size / 100) * 35;
                if (iM1124dp < AndroidUtilities.m1124dp(320.0f)) {
                    iM1124dp = AndroidUtilities.m1124dp(320.0f);
                }
                LaunchActivity.this.actionBarLayout.getView().measure(View.MeasureSpec.makeMeasureSpec(iM1124dp, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size2, TLObject.FLAG_30));
                LaunchActivity.this.shadowTabletSide.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1124dp(1.0f), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size2, TLObject.FLAG_30));
                LaunchActivity.this.rightActionBarLayout.getView().measure(View.MeasureSpec.makeMeasureSpec(size - iM1124dp, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size2, TLObject.FLAG_30));
            } else {
                LaunchActivity.this.tabletFullSize = true;
                LaunchActivity.this.actionBarLayout.getView().measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size2, TLObject.FLAG_30));
            }
            LaunchActivity.this.backgroundTablet.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size2, TLObject.FLAG_30));
            LaunchActivity.this.shadowTablet.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size2, TLObject.FLAG_30));
            LaunchActivity.this.layersActionBarLayout.getView().measure(View.MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.m1124dp(530.0f), size - AndroidUtilities.m1124dp(16.0f)), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(((size2 - AndroidUtilities.statusBarHeight) - AndroidUtilities.navigationBarHeight) - AndroidUtilities.m1124dp(16.0f), TLObject.FLAG_30));
            this.inLayout = false;
        }

        @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int i5 = i3 - i;
            if (!AndroidUtilities.isInMultiwindow && (!AndroidUtilities.isSmallTablet() || getResources().getConfiguration().orientation == 2)) {
                int iM1124dp = (i5 / 100) * 35;
                if (iM1124dp < AndroidUtilities.m1124dp(320.0f)) {
                    iM1124dp = AndroidUtilities.m1124dp(320.0f);
                }
                LaunchActivity.this.shadowTabletSide.layout(iM1124dp, 0, LaunchActivity.this.shadowTabletSide.getMeasuredWidth() + iM1124dp, LaunchActivity.this.shadowTabletSide.getMeasuredHeight());
                LaunchActivity.this.actionBarLayout.getView().layout(0, 0, LaunchActivity.this.actionBarLayout.getView().getMeasuredWidth(), LaunchActivity.this.actionBarLayout.getView().getMeasuredHeight());
                LaunchActivity.this.rightActionBarLayout.getView().layout(iM1124dp, 0, LaunchActivity.this.rightActionBarLayout.getView().getMeasuredWidth() + iM1124dp, LaunchActivity.this.rightActionBarLayout.getView().getMeasuredHeight());
            } else {
                LaunchActivity.this.actionBarLayout.getView().layout(0, 0, LaunchActivity.this.actionBarLayout.getView().getMeasuredWidth(), LaunchActivity.this.actionBarLayout.getView().getMeasuredHeight());
            }
            int measuredWidth = (i5 - LaunchActivity.this.layersActionBarLayout.getView().getMeasuredWidth()) / 2;
            int iM1124dp2 = AndroidUtilities.statusBarHeight + AndroidUtilities.m1124dp(8.0f);
            LaunchActivity.this.layersActionBarLayout.getView().layout(measuredWidth, iM1124dp2, LaunchActivity.this.layersActionBarLayout.getView().getMeasuredWidth() + measuredWidth, LaunchActivity.this.layersActionBarLayout.getView().getMeasuredHeight() + iM1124dp2);
            LaunchActivity.this.backgroundTablet.layout(0, 0, LaunchActivity.this.backgroundTablet.getMeasuredWidth(), LaunchActivity.this.backgroundTablet.getMeasuredHeight());
            LaunchActivity.this.shadowTablet.layout(0, 0, LaunchActivity.this.shadowTablet.getMeasuredWidth(), LaunchActivity.this.shadowTablet.getMeasuredHeight());
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            if (LaunchActivity.this.layersActionBarLayout != null) {
                LaunchActivity.this.layersActionBarLayout.parentDraw(this, canvas);
            }
            super.dispatchDraw(canvas);
        }

        @Override // android.view.ViewGroup, android.view.View
        public WindowInsets dispatchApplyWindowInsets(WindowInsets windowInsets) {
            return AndroidUtilities.fixedDispatchApplyWindowInsets(windowInsets, this);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$12 */
    /* JADX INFO: loaded from: classes6.dex */
    class C589712 extends SizeNotifierFrameLayout {
        @Override // org.telegram.p029ui.Components.SizeNotifierFrameLayout
        protected boolean isActionBarVisible() {
            return false;
        }

        C589712(final LaunchActivity this) {
            super(this);
        }
    }

    public /* synthetic */ boolean lambda$setupActionBarLayout$3(View view, MotionEvent motionEvent) {
        if (!this.actionBarLayout.getFragmentStack().isEmpty() && motionEvent.getAction() == 1) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int[] iArr = new int[2];
            this.layersActionBarLayout.getView().getLocationOnScreen(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            if (!this.layersActionBarLayout.checkTransitionAnimation() && (x <= i || x >= i + this.layersActionBarLayout.getView().getWidth() || y <= i2 || y >= i2 + this.layersActionBarLayout.getView().getHeight())) {
                if (!this.layersActionBarLayout.getFragmentStack().isEmpty()) {
                    while (this.layersActionBarLayout.getFragmentStack().size() - 1 > 0) {
                        ActionBarLayout actionBarLayout = this.layersActionBarLayout;
                        actionBarLayout.removeFragmentFromStack(actionBarLayout.getFragmentStack().get(0));
                    }
                    this.layersActionBarLayout.closeLastFragment(true);
                }
                return true;
            }
        }
        return false;
    }

    public void addOnUserLeaveHintListener(Runnable runnable) {
        this.onUserLeaveHintListeners.add(runnable);
    }

    public void removeOnUserLeaveHintListener(Runnable runnable) {
        this.onUserLeaveHintListeners.remove(runnable);
    }

    private BaseFragment getClientNotActivatedFragment() {
        if (LoginActivity.loadCurrentState(false, this.currentAccount).getInt("currentViewNum", 0) != 0) {
            return new LoginActivity();
        }
        return new IntroActivity();
    }

    public FireworksOverlay getFireworksOverlay() {
        return this.fireworksOverlay;
    }

    public BottomSheetTabsOverlay getBottomSheetTabsOverlay() {
        return this.bottomSheetTabsOverlay;
    }

    private void checkSystemBarColors() {
        checkSystemBarColors(false, true, !this.isNavigationBarColorFrozen);
    }

    private void checkSystemBarColors(boolean z) {
        checkSystemBarColors(z, true, !this.isNavigationBarColorFrozen);
    }

    private void checkSystemBarColors(boolean z, boolean z2) {
        checkSystemBarColors(false, z, z2);
    }

    public void checkSystemBarColors(boolean z, boolean z2, boolean z3) {
        boolean zIsLightStatusBar;
        ArrayList arrayList = mainFragmentsStack;
        boolean z4 = true;
        BaseFragment baseFragment = !arrayList.isEmpty() ? (BaseFragment) arrayList.get(arrayList.size() - 1) : null;
        char c = 2;
        if (baseFragment != null && (baseFragment.isRemovingFromStack() || baseFragment.isInPreviewMode())) {
            baseFragment = arrayList.size() > 1 ? (BaseFragment) arrayList.get(arrayList.size() - 2) : null;
        }
        boolean z5 = baseFragment != null && baseFragment.hasForceLightStatusBar();
        int i = Build.VERSION.SDK_INT;
        if (z2) {
            if (baseFragment != null) {
                zIsLightStatusBar = baseFragment.isLightStatusBar();
                if (baseFragment.getParentLayout() instanceof ActionBarLayout) {
                    ActionBarLayout actionBarLayout = (ActionBarLayout) baseFragment.getParentLayout();
                    if (actionBarLayout.getSheetFragment(false) != null && actionBarLayout.getSheetFragment(false).getLastSheet() != null) {
                        BaseFragment.AttachedSheet lastSheet = actionBarLayout.getSheetFragment(false).getLastSheet();
                        if (lastSheet.isShown()) {
                            zIsLightStatusBar = lastSheet.isAttachedLightStatusBar();
                        }
                    } else {
                        ArrayList<BaseFragment.AttachedSheet> arrayList2 = baseFragment.sheetsStack;
                        if (arrayList2 != null && !arrayList2.isEmpty()) {
                            ArrayList<BaseFragment.AttachedSheet> arrayList3 = baseFragment.sheetsStack;
                            BaseFragment.AttachedSheet attachedSheet = arrayList3.get(arrayList3.size() - 1);
                            if (attachedSheet.isShown()) {
                                zIsLightStatusBar = attachedSheet.isAttachedLightStatusBar();
                            }
                        }
                    }
                }
            } else {
                zIsLightStatusBar = ColorUtils.calculateLuminance(Theme.getColor(Theme.key_actionBarDefault, null, true)) > 0.699999988079071d;
            }
            AndroidUtilities.setLightStatusBar(getWindow(), zIsLightStatusBar, z5);
        }
        if (i >= 26 && z3 && (!z || baseFragment == null || !baseFragment.isInPreviewMode())) {
            int color = (baseFragment == null || !z) ? Theme.getColor(Theme.key_windowBackgroundGray, null, true) : baseFragment.getNavigationBarColor();
            if (!(baseFragment instanceof ChatActivity)) {
                c = 0;
            } else if (!((ChatActivity) baseFragment).isShouldHaveLightNavigationBarIcons()) {
                c = 1;
            }
            if (getBottomSheetTabs() != null && getBottomSheetTabs().getHeight(false) > 0) {
                c = 0;
            }
            if (this.actionBarLayout.getSheetFragment(false) != null) {
                EmptyBaseFragment sheetFragment = this.actionBarLayout.getSheetFragment(false);
                if (sheetFragment.sheetsStack != null) {
                    for (int i2 = 0; i2 < sheetFragment.sheetsStack.size(); i2++) {
                        BaseFragment.AttachedSheet attachedSheet2 = sheetFragment.sheetsStack.get(i2);
                        if (attachedSheet2.attachedToParent()) {
                            color = attachedSheet2.getNavigationBarColor(color);
                            c = 0;
                        }
                    }
                }
            }
            Iterator it = BotWebViewSheet.activeSheets.iterator();
            while (it.hasNext()) {
                color = ((BotWebViewSheet) it.next()).getNavigationBarColor(color);
                c = 0;
            }
            setNavigationBarColor(color);
            if ((c != 0 || AndroidUtilities.computePerceivedBrightness(color) < 0.721f) && c != 1) {
                z4 = false;
            }
            AndroidUtilities.setLightNavigationBar(this, z4);
        }
        if (z2) {
            getWindow().setStatusBarColor(0);
        }
    }

    public FrameLayout getMainContainerFrameLayout() {
        return this.frameLayout;
    }

    public static /* synthetic */ MainTabsActivity $r8$lambda$AgtVsDJu6qeK8DrzrChE17ZgC88(Void r0) {
        return new MainTabsActivity();
    }

    public void switchToAccount(int i, boolean z) {
        switchToAccount(i, z, new GenericProvider() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda38
            @Override // org.telegram.messenger.GenericProvider
            public final Object provide(Object obj) {
                return LaunchActivity.$r8$lambda$AgtVsDJu6qeK8DrzrChE17ZgC88((Void) obj);
            }
        });
    }

    public void switchToAccount(int i, boolean z, GenericProvider genericProvider) {
        SizeNotifierFrameLayout sizeNotifierFrameLayout;
        if (i == UserConfig.selectedAccount || !UserConfig.isValidAccount(i)) {
            return;
        }
        this.switchingAccount = true;
        ConnectionsManager.getInstance(this.currentAccount).setAppPaused(true, false);
        UserConfig.selectedAccount = i;
        UserConfig.getInstance(0).saveConfig(false);
        checkCurrentAccount();
        if (AndroidUtilities.isTablet()) {
            ActionBarLayout actionBarLayout = this.layersActionBarLayout;
            if (actionBarLayout != null) {
                actionBarLayout.removeAllFragments();
                this.layersActionBarLayout.getView().setVisibility(8);
            }
            ActionBarLayout actionBarLayout2 = this.rightActionBarLayout;
            if (actionBarLayout2 != null) {
                actionBarLayout2.removeAllFragments();
                if (!this.tabletFullSize) {
                    FrameLayout frameLayout = this.shadowTabletSide;
                    if (frameLayout != null) {
                        frameLayout.setVisibility(0);
                    }
                    if (this.rightActionBarLayout.getFragmentStack().isEmpty() && (sizeNotifierFrameLayout = this.backgroundTablet) != null) {
                        sizeNotifierFrameLayout.setVisibility(0);
                    }
                    this.rightActionBarLayout.getView().setVisibility(8);
                }
            }
        }
        if (z) {
            this.actionBarLayout.removeAllFragments();
        } else {
            this.actionBarLayout.removeFragmentFromStack(0);
        }
        this.actionBarLayout.addFragmentToStack((MainTabsActivity) genericProvider.provide(null), -3);
        this.actionBarLayout.rebuildFragments(1);
        if (AndroidUtilities.isTablet()) {
            this.layersActionBarLayout.rebuildFragments(1);
            this.rightActionBarLayout.rebuildFragments(1);
        }
        DrawerLayoutContainer drawerLayoutContainer = this.drawerLayoutContainer;
        if (drawerLayoutContainer != null && drawerLayoutContainer.getDrawerContainer() != null) {
            this.drawerLayoutContainer.getDrawerContainer().onAccountChanged();
        }
        if (!ApplicationLoader.mainInterfacePaused) {
            ConnectionsManager.getInstance(this.currentAccount).setAppPaused(false, false);
        }
        if (UserConfig.getInstance(i).unacceptedTermsOfService != null) {
            showTosActivity(i, UserConfig.getInstance(i).unacceptedTermsOfService);
        }
        updateCurrentConnectionState();
        ApplicationLoader.updateMapsProvider();
        PluginsController.getInstance().loadPluginSettings();
        this.switchingAccount = false;
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda119
            @Override // java.lang.Runnable
            public final void run() {
                PillStackConfig.notifySettingsChanged(PillStackConfig.PillType.TON.f298id, PillStackConfig.PillType.BTC.f298id, PillStackConfig.PillType.USD.f298id);
            }
        }, 150L);
    }

    private void switchToAvailableAccountOrLogout() {
        int i = 0;
        while (true) {
            if (i >= 16) {
                i = -1;
                break;
            } else if (UserConfig.getInstance(i).isClientActivated()) {
                break;
            } else {
                i++;
            }
        }
        TermsOfServiceView termsOfServiceView = this.termsOfServiceView;
        if (termsOfServiceView != null) {
            termsOfServiceView.setVisibility(8);
        }
        if (i != -1) {
            switchToAccount(i, true);
            return;
        }
        RestrictedLanguagesSelectActivity.checkRestrictedLanguages(true);
        clearFragments();
        this.actionBarLayout.rebuildLogout();
        if (AndroidUtilities.isTablet()) {
            this.layersActionBarLayout.rebuildLogout();
            this.rightActionBarLayout.rebuildLogout();
        }
        lambda$runLinkRequest$106(new IntroActivity().setOnLogout());
    }

    public static void clearFragments() {
        ArrayList arrayList = mainFragmentsStack;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((BaseFragment) obj).onFragmentDestroy();
        }
        mainFragmentsStack.clear();
        if (AndroidUtilities.isTablet()) {
            ArrayList arrayList2 = layerFragmentsStack;
            int size2 = arrayList2.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList2.get(i3);
                i3++;
                ((BaseFragment) obj2).onFragmentDestroy();
            }
            layerFragmentsStack.clear();
            ArrayList arrayList3 = rightFragmentsStack;
            int size3 = arrayList3.size();
            while (i < size3) {
                Object obj3 = arrayList3.get(i);
                i++;
                ((BaseFragment) obj3).onFragmentDestroy();
            }
            rightFragmentsStack.clear();
        }
    }

    public int getMainFragmentsCount() {
        return mainFragmentsStack.size();
    }

    private void checkCurrentAccount() {
        int i = this.currentAccount;
        if (i != UserConfig.selectedAccount) {
            NotificationCenter.getInstance(i).removeObserver(this, NotificationCenter.currentUserShowLimitReachedDialog);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.openBoostForUsersDialog);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.appDidLogout);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.mainUserInfoChanged);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.attachMenuBotsDidLoad);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.didUpdateConnectionState);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.needShowAlert);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.wasUnableToFindCurrentLocation);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.openArticle);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.hasNewContactsToImport);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.needShowPlayServicesAlert);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileLoaded);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileLoadFailed);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.historyImportProgressChanged);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.groupCallUpdated);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.stickersImportComplete);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.chatSwitchedForum);
        }
        int i2 = UserConfig.selectedAccount;
        this.currentAccount = i2;
        NotificationCenter.getInstance(i2).addObserver(this, NotificationCenter.openBoostForUsersDialog);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.appDidLogout);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.mainUserInfoChanged);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.attachMenuBotsDidLoad);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.didUpdateConnectionState);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.needShowAlert);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.wasUnableToFindCurrentLocation);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.openArticle);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.hasNewContactsToImport);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.needShowPlayServicesAlert);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.fileLoaded);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.fileLoadFailed);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.historyImportProgressChanged);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.groupCallUpdated);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.stickersImportComplete);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.currentUserShowLimitReachedDialog);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.chatSwitchedForum);
    }

    private void syncDrawerContainerEnabled() {
        DrawerLayoutContainer drawerLayoutContainer = this.drawerLayoutContainer;
        if (drawerLayoutContainer == null) {
            return;
        }
        if (ExteraConfig.navigationDrawer) {
            if (drawerLayoutContainer.getDrawerContainer() == null) {
                this.drawerLayoutContainer.setDrawerContainer(new DrawerContainer(this));
            }
        } else if (drawerLayoutContainer.getDrawerContainer() != null) {
            this.drawerLayoutContainer.setDrawerContainer(null);
        }
    }

    private void checkLayout() {
        if (!AndroidUtilities.isTablet() || this.rightActionBarLayout == null) {
            return;
        }
        if (AndroidUtilities.getWasTablet() == null || AndroidUtilities.getWasTablet().booleanValue() == AndroidUtilities.isTabletForce()) {
            if (!AndroidUtilities.isInMultiwindow && (!AndroidUtilities.isSmallTablet() || getResources().getConfiguration().orientation == 2)) {
                this.tabletFullSize = false;
                ArrayList arrayList = new ArrayList(this.actionBarLayout.getFragmentStack());
                if (arrayList.size() >= 2) {
                    while (1 < arrayList.size()) {
                        BaseFragment baseFragment = (BaseFragment) arrayList.get(1);
                        if (baseFragment instanceof ChatActivity) {
                            ((ChatActivity) baseFragment).setIgnoreAttachOnPause(true);
                        }
                        baseFragment.onPause();
                        baseFragment.onFragmentDestroy();
                        baseFragment.setParentLayout(null);
                        arrayList.remove(baseFragment);
                        this.rightActionBarLayout.addFragmentToStack(baseFragment);
                    }
                    PasscodeViewDialog passcodeViewDialog = this.passcodeDialog;
                    if (passcodeViewDialog == null || passcodeViewDialog.passcodeView.getVisibility() != 0) {
                        this.actionBarLayout.rebuildFragments(1);
                        this.rightActionBarLayout.rebuildFragments(1);
                    }
                }
                this.rightActionBarLayout.getView().setVisibility(this.rightActionBarLayout.getFragmentStack().isEmpty() ? 8 : 0);
                this.backgroundTablet.setVisibility(this.rightActionBarLayout.getFragmentStack().isEmpty() ? 0 : 8);
                this.shadowTabletSide.setVisibility(this.actionBarLayout.getFragmentStack().isEmpty() ? 8 : 0);
                return;
            }
            this.tabletFullSize = true;
            List<BaseFragment> fragmentStack = this.rightActionBarLayout.getFragmentStack();
            if (!fragmentStack.isEmpty()) {
                while (fragmentStack.size() > 0) {
                    BaseFragment baseFragment2 = fragmentStack.get(0);
                    if (baseFragment2 instanceof ChatActivity) {
                        ((ChatActivity) baseFragment2).setIgnoreAttachOnPause(true);
                    }
                    baseFragment2.onPause();
                    baseFragment2.onFragmentDestroy();
                    baseFragment2.setParentLayout(null);
                    fragmentStack.remove(baseFragment2);
                    this.actionBarLayout.addFragmentToStack(baseFragment2);
                }
                PasscodeViewDialog passcodeViewDialog2 = this.passcodeDialog;
                if (passcodeViewDialog2 == null || passcodeViewDialog2.passcodeView.getVisibility() != 0) {
                    this.actionBarLayout.rebuildFragments(1);
                }
            }
            this.shadowTabletSide.setVisibility(8);
            this.rightActionBarLayout.getView().setVisibility(8);
            this.backgroundTablet.setVisibility(this.actionBarLayout.getFragmentStack().isEmpty() ? 0 : 8);
        }
    }

    private void showUpdateActivity(int i, TLRPC.TL_help_appUpdate tL_help_appUpdate, boolean z) {
        if (this.blockingUpdateView == null) {
            BlockingUpdateView blockingUpdateView = new BlockingUpdateView(this, tL_help_appUpdate);
            this.blockingUpdateView = blockingUpdateView;
            this.drawerLayoutContainer.addView(blockingUpdateView, LayoutHelper.createFrame(-1, -1.0f));
        }
        this.blockingUpdateView.show(i, z);
    }

    private void showTosActivity(int i, TLRPC.TL_help_termsOfService tL_help_termsOfService) {
        if (this.termsOfServiceView == null) {
            TermsOfServiceView termsOfServiceView = new TermsOfServiceView(this);
            this.termsOfServiceView = termsOfServiceView;
            termsOfServiceView.setAlpha(0.0f);
            this.drawerLayoutContainer.addView(this.termsOfServiceView, LayoutHelper.createFrame(-1, -1.0f));
            this.termsOfServiceView.setDelegate(new C589813());
        }
        TLRPC.TL_help_termsOfService tL_help_termsOfService2 = UserConfig.getInstance(i).unacceptedTermsOfService;
        if (tL_help_termsOfService2 != tL_help_termsOfService && (tL_help_termsOfService2 == null || !tL_help_termsOfService2.f1726id.data.equals(tL_help_termsOfService.f1726id.data))) {
            UserConfig.getInstance(i).unacceptedTermsOfService = tL_help_termsOfService;
            UserConfig.getInstance(i).saveConfig(false);
        }
        this.termsOfServiceView.show(i, tL_help_termsOfService);
        this.termsOfServiceView.animate().alpha(1.0f).setDuration(150L).setInterpolator(AndroidUtilities.decelerateInterpolator).setListener(null).start();
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$13 */
    /* JADX INFO: loaded from: classes6.dex */
    class C589813 implements TermsOfServiceView.TermsOfServiceViewDelegate {
        C589813() {
        }

        @Override // org.telegram.ui.Components.TermsOfServiceView.TermsOfServiceViewDelegate
        public void onAcceptTerms(int i) {
            UserConfig.getInstance(i).unacceptedTermsOfService = null;
            UserConfig.getInstance(i).saveConfig(false);
            if (!LaunchActivity.mainFragmentsStack.isEmpty()) {
                ((BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1)).onResume();
            }
            LaunchActivity.this.termsOfServiceView.animate().alpha(0.0f).setDuration(150L).setInterpolator(AndroidUtilities.accelerateInterpolator).withEndAction(new Runnable() { // from class: org.telegram.ui.LaunchActivity$13$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAcceptTerms$0();
                }
            }).start();
        }

        public /* synthetic */ void lambda$onAcceptTerms$0() {
            LaunchActivity.this.termsOfServiceView.setVisibility(8);
        }
    }

    public void showPasscodeActivity(boolean z, boolean z2, int i, int i2, final Runnable runnable, Runnable runnable2) {
        if (this.drawerLayoutContainer == null || isFinishing()) {
            return;
        }
        if (this.passcodeDialog == null) {
            this.passcodeDialog = new PasscodeViewDialog(this);
        }
        SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialogWindow = this.selectAnimatedEmojiDialog;
        if (selectAnimatedEmojiDialogWindow != null) {
            selectAnimatedEmojiDialogWindow.dismiss();
            this.selectAnimatedEmojiDialog = null;
        }
        SharedConfig.appLocked = true;
        if (SecretMediaViewer.hasInstance() && SecretMediaViewer.getInstance().isVisible()) {
            SecretMediaViewer.getInstance().closePhoto(false, false);
        } else if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().closePhoto(false, true);
        } else if (ArticleViewer.hasInstance() && ArticleViewer.getInstance().isVisible()) {
            ArticleViewer.getInstance().close(false, true);
        }
        StoryRecorder.destroyInstance();
        MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
        if (playingMessageObject != null && playingMessageObject.isRoundVideo()) {
            MediaController.getInstance().cleanupPlayer(true, true);
        }
        this.passcodeDialog.show();
        this.passcodeDialog.passcodeView.onShow(this.overlayPasscodeViews.isEmpty() && z, z2, i, i2, new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda56
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showPasscodeActivity$7(runnable);
            }
        }, runnable2);
        int i3 = 0;
        while (i3 < this.overlayPasscodeViews.size()) {
            ((PasscodeView) this.overlayPasscodeViews.get(i3)).onShow(z && i3 == this.overlayPasscodeViews.size() - 1, z2, i, i2, null, null);
            i3++;
        }
        SharedConfig.isWaitingForPasscodeEnter = true;
        PasscodeView.PasscodeViewDelegate passcodeViewDelegate = new PasscodeView.PasscodeViewDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda57
            @Override // org.telegram.ui.Components.PasscodeView.PasscodeViewDelegate
            public final void didAcceptedPassword(PasscodeView passcodeView) throws Throwable {
                this.f$0.lambda$showPasscodeActivity$8(passcodeView);
            }
        };
        this.passcodeDialog.passcodeView.setDelegate(passcodeViewDelegate);
        Iterator it = this.overlayPasscodeViews.iterator();
        while (it.hasNext()) {
            ((PasscodeView) it.next()).setDelegate(passcodeViewDelegate);
        }
        try {
            NotificationsController.getInstance(UserConfig.selectedAccount).showNotifications();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    public /* synthetic */ void lambda$showPasscodeActivity$7(Runnable runnable) {
        this.actionBarLayout.getView().setVisibility(4);
        if (AndroidUtilities.isTablet()) {
            ActionBarLayout actionBarLayout = this.layersActionBarLayout;
            if (actionBarLayout != null && actionBarLayout.getView() != null && this.layersActionBarLayout.getView().getVisibility() == 0) {
                this.layersActionBarLayout.getView().setVisibility(4);
            }
            ActionBarLayout actionBarLayout2 = this.rightActionBarLayout;
            if (actionBarLayout2 != null && actionBarLayout2.getView() != null) {
                this.rightActionBarLayout.getView().setVisibility(4);
            }
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    public /* synthetic */ void lambda$showPasscodeActivity$8(PasscodeView passcodeView) throws Throwable {
        LaunchActivity launchActivity;
        SharedConfig.isWaitingForPasscodeEnter = false;
        Intent intent = this.passcodeSaveIntent;
        if (intent != null) {
            launchActivity = this;
            launchActivity.handleIntent(intent, this.passcodeSaveIntentIsNew, this.passcodeSaveIntentIsRestore, true, null, false, true);
            launchActivity.passcodeSaveIntent = null;
        } else {
            launchActivity = this;
        }
        launchActivity.actionBarLayout.getView().setVisibility(0);
        launchActivity.actionBarLayout.rebuildFragments(1);
        launchActivity.actionBarLayout.updateTitleOverlay();
        if (AndroidUtilities.isTablet()) {
            ActionBarLayout actionBarLayout = launchActivity.layersActionBarLayout;
            if (actionBarLayout != null) {
                actionBarLayout.rebuildFragments(1);
                if (launchActivity.layersActionBarLayout.getView() != null && launchActivity.layersActionBarLayout.getView().getVisibility() == 4) {
                    launchActivity.layersActionBarLayout.getView().setVisibility(0);
                }
            }
            ActionBarLayout actionBarLayout2 = launchActivity.rightActionBarLayout;
            if (actionBarLayout2 != null) {
                actionBarLayout2.rebuildFragments(1);
                if (launchActivity.rightActionBarLayout.getView() != null) {
                    launchActivity.rightActionBarLayout.getView().setVisibility(0);
                }
            }
        }
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.passcodeDismissed, passcodeView);
        try {
            NotificationsController.getInstance(UserConfig.selectedAccount).showNotifications();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    public boolean allowShowFingerprintDialog(PasscodeView passcodeView) {
        PasscodeViewDialog passcodeViewDialog;
        if (this.overlayPasscodeViews.isEmpty() && (passcodeViewDialog = this.passcodeDialog) != null) {
            return passcodeView == passcodeViewDialog.passcodeView;
        }
        List list = this.overlayPasscodeViews;
        return list.get(list.size() - 1) == passcodeView;
    }

    private boolean handleIntent(Intent intent, boolean z, boolean z2, boolean z3) {
        return handleIntent(intent, z, z2, z3, null, true, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:3434:0x374e, code lost:
    
        r40[0] = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:3435:0x3750, code lost:
    
        r10 = r105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:3436:0x3752, code lost:
    
        switchToAccount(r8, r10);
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable search skipped. Vars limit reached: 9703 (expected less than 5000) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:2104:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:2124:0x01d5  */
    /* JADX WARN: Removed duplicated region for block: B:2131:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:2139:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:2144:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:2221:0x037f  */
    /* JADX WARN: Removed duplicated region for block: B:2257:0x0466  */
    /* JADX WARN: Removed duplicated region for block: B:2310:0x054b  */
    /* JADX WARN: Removed duplicated region for block: B:2434:0x08d5  */
    /* JADX WARN: Removed duplicated region for block: B:2555:0x0d74  */
    /* JADX WARN: Removed duplicated region for block: B:2557:0x0d77  */
    /* JADX WARN: Removed duplicated region for block: B:2565:0x0dad  */
    /* JADX WARN: Removed duplicated region for block: B:2646:0x107c  */
    /* JADX WARN: Removed duplicated region for block: B:2647:0x108b  */
    /* JADX WARN: Removed duplicated region for block: B:3126:0x27f8  */
    /* JADX WARN: Removed duplicated region for block: B:3209:0x2d9a  */
    /* JADX WARN: Removed duplicated region for block: B:3253:0x2e84 A[Catch: Exception -> 0x2e99, TRY_LEAVE, TryCatch #14 {Exception -> 0x2e99, blocks: (B:3251:0x2e7a, B:3253:0x2e84), top: B:3942:0x2e7a }] */
    /* JADX WARN: Removed duplicated region for block: B:3255:0x2e92  */
    /* JADX WARN: Removed duplicated region for block: B:3267:0x2f90  */
    /* JADX WARN: Removed duplicated region for block: B:3269:0x2f93  */
    /* JADX WARN: Removed duplicated region for block: B:3274:0x2fb6  */
    /* JADX WARN: Removed duplicated region for block: B:3279:0x3066  */
    /* JADX WARN: Removed duplicated region for block: B:3299:0x32b7  */
    /* JADX WARN: Removed duplicated region for block: B:3308:0x3323  */
    /* JADX WARN: Removed duplicated region for block: B:3330:0x341b  */
    /* JADX WARN: Removed duplicated region for block: B:3353:0x34a0  */
    /* JADX WARN: Removed duplicated region for block: B:3365:0x3637  */
    /* JADX WARN: Removed duplicated region for block: B:3375:0x366a  */
    /* JADX WARN: Removed duplicated region for block: B:3376:0x366e  */
    /* JADX WARN: Removed duplicated region for block: B:3378:0x3684  */
    /* JADX WARN: Removed duplicated region for block: B:3380:0x3688  */
    /* JADX WARN: Removed duplicated region for block: B:3385:0x36a3  */
    /* JADX WARN: Removed duplicated region for block: B:3457:0x37a8  */
    /* JADX WARN: Removed duplicated region for block: B:3484:0x3875  */
    /* JADX WARN: Removed duplicated region for block: B:3485:0x3889  */
    /* JADX WARN: Removed duplicated region for block: B:3552:0x3d44  */
    /* JADX WARN: Removed duplicated region for block: B:3576:0x3db7  */
    /* JADX WARN: Removed duplicated region for block: B:3736:0x415a  */
    /* JADX WARN: Removed duplicated region for block: B:3737:0x416d  */
    /* JADX WARN: Removed duplicated region for block: B:3740:0x417c  */
    /* JADX WARN: Removed duplicated region for block: B:3859:0x449f  */
    /* JADX WARN: Removed duplicated region for block: B:3870:0x44e5  */
    /* JADX WARN: Removed duplicated region for block: B:3880:0x451f  */
    /* JADX WARN: Removed duplicated region for block: B:3884:0x4537  */
    /* JADX WARN: Removed duplicated region for block: B:3888:0x4554  */
    /* JADX WARN: Removed duplicated region for block: B:3970:0x37bc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:3972:0x27f2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v174 */
    /* JADX WARN: Type inference failed for: r0v175, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v176 */
    /* JADX WARN: Type inference failed for: r0v22, types: [org.telegram.ui.ActionBar.BaseFragment, org.telegram.ui.MainTabsActivity] */
    /* JADX WARN: Type inference failed for: r0v34, types: [org.telegram.ui.ActionBar.BaseFragment, org.telegram.ui.MainTabsActivity] */
    /* JADX WARN: Type inference failed for: r0v74, types: [org.telegram.ui.ActionBar.ActionBarLayout] */
    /* JADX WARN: Type inference failed for: r0v75, types: [org.telegram.ui.ActionBar.ActionBarLayout] */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v2, types: [android.os.Bundle, java.lang.String] */
    /* JADX WARN: Type inference failed for: r10v209 */
    /* JADX WARN: Type inference failed for: r10v211 */
    /* JADX WARN: Type inference failed for: r10v212 */
    /* JADX WARN: Type inference failed for: r10v217 */
    /* JADX WARN: Type inference failed for: r10v218 */
    /* JADX WARN: Type inference failed for: r11v10 */
    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v18 */
    /* JADX WARN: Type inference failed for: r11v19, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r11v20 */
    /* JADX WARN: Type inference failed for: r11v210 */
    /* JADX WARN: Type inference failed for: r11v211 */
    /* JADX WARN: Type inference failed for: r11v23 */
    /* JADX WARN: Type inference failed for: r11v30 */
    /* JADX WARN: Type inference failed for: r11v9, types: [boolean] */
    /* JADX WARN: Type inference failed for: r139v0, types: [android.app.Activity, android.content.Context, org.telegram.ui.BasePermissionsActivity, org.telegram.ui.LaunchActivity] */
    /* JADX WARN: Type inference failed for: r16v106 */
    /* JADX WARN: Type inference failed for: r16v145 */
    /* JADX WARN: Type inference failed for: r16v146 */
    /* JADX WARN: Type inference failed for: r16v147 */
    /* JADX WARN: Type inference failed for: r16v148 */
    /* JADX WARN: Type inference failed for: r16v149 */
    /* JADX WARN: Type inference failed for: r16v19, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r16v25 */
    /* JADX WARN: Type inference failed for: r17v19, types: [java.lang.Long] */
    /* JADX WARN: Type inference failed for: r17v21 */
    /* JADX WARN: Type inference failed for: r17v88 */
    /* JADX WARN: Type inference failed for: r17v95 */
    /* JADX WARN: Type inference failed for: r17v96 */
    /* JADX WARN: Type inference failed for: r17v97 */
    /* JADX WARN: Type inference failed for: r17v98 */
    /* JADX WARN: Type inference failed for: r17v99 */
    /* JADX WARN: Type inference failed for: r18v22, types: [java.lang.Long] */
    /* JADX WARN: Type inference failed for: r19v6, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r19v74 */
    /* JADX WARN: Type inference failed for: r19v75 */
    /* JADX WARN: Type inference failed for: r19v76 */
    /* JADX WARN: Type inference failed for: r19v77 */
    /* JADX WARN: Type inference failed for: r19v78 */
    /* JADX WARN: Type inference failed for: r19v79 */
    /* JADX WARN: Type inference failed for: r19v8 */
    /* JADX WARN: Type inference failed for: r21v13, types: [java.util.HashMap] */
    /* JADX WARN: Type inference failed for: r21v18 */
    /* JADX WARN: Type inference failed for: r21v82 */
    /* JADX WARN: Type inference failed for: r21v84 */
    /* JADX WARN: Type inference failed for: r21v85 */
    /* JADX WARN: Type inference failed for: r21v86 */
    /* JADX WARN: Type inference failed for: r21v87 */
    /* JADX WARN: Type inference failed for: r21v88 */
    /* JADX WARN: Type inference failed for: r26v0, types: [org.telegram.tgnet.TLRPC$TL_wallPaper] */
    /* JADX WARN: Type inference failed for: r26v2 */
    /* JADX WARN: Type inference failed for: r26v58 */
    /* JADX WARN: Type inference failed for: r26v59 */
    /* JADX WARN: Type inference failed for: r26v60 */
    /* JADX WARN: Type inference failed for: r26v61 */
    /* JADX WARN: Type inference failed for: r26v62 */
    /* JADX WARN: Type inference failed for: r26v63 */
    /* JADX WARN: Type inference failed for: r3v10, types: [org.telegram.ui.ActionBar.ActionBarLayout] */
    /* JADX WARN: Type inference failed for: r3v13, types: [org.telegram.ui.ActionBar.ActionBarLayout] */
    /* JADX WARN: Type inference failed for: r56v0, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r56v2 */
    /* JADX WARN: Type inference failed for: r56v57 */
    /* JADX WARN: Type inference failed for: r56v78 */
    /* JADX WARN: Type inference failed for: r56v79 */
    /* JADX WARN: Type inference failed for: r56v80 */
    /* JADX WARN: Type inference failed for: r56v81 */
    /* JADX WARN: Type inference failed for: r56v82 */
    /* JADX WARN: Type inference failed for: r8v11, types: [android.content.Intent] */
    /* JADX WARN: Type inference failed for: r8v264 */
    /* JADX WARN: Type inference failed for: r8v265 */
    /* JADX WARN: Type inference failed for: r8v266 */
    /* JADX WARN: Type inference failed for: r8v267 */
    /* JADX WARN: Type inference failed for: r9v262 */
    /* JADX WARN: Type inference failed for: r9v267 */
    /* JADX WARN: Type inference failed for: r9v268 */
    /* JADX WARN: Type inference failed for: r9v34 */
    /* JADX WARN: Type inference failed for: r9v7, types: [boolean] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean handleIntent(android.content.Intent r140, boolean r141, boolean r142, boolean r143, final org.telegram.messenger.browser.Browser.Progress r144, boolean r145, boolean r146) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 17814
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.handleIntent(android.content.Intent, boolean, boolean, boolean, org.telegram.messenger.browser.Browser$Progress, boolean, boolean):boolean");
    }

    public /* synthetic */ void lambda$handleIntent$9(String str) {
        if (this.actionBarLayout.getFragmentStack().isEmpty()) {
            return;
        }
        this.actionBarLayout.getFragmentStack().get(0).presentFragment(new PremiumPreviewFragment(Uri.parse(str).getQueryParameter("ref")));
    }

    public /* synthetic */ void lambda$handleIntent$10(Intent intent, boolean z) {
        handleIntent(intent, true, false, false);
    }

    public /* synthetic */ void lambda$handleIntent$11(Browser.Progress progress, int[] iArr, Long l) {
        if (progress != null) {
            progress.end();
        }
        if (l == null) {
            return;
        }
        if (MessagesController.getInstance(this.currentAccount).getUserOrChat(l.longValue()) == null) {
            BaseFragment lastFragment = getLastFragment();
            if (lastFragment == null || !(lastFragment instanceof ChatActivity)) {
                return;
            }
            ((ChatActivity) lastFragment).shakeContent();
            return;
        }
        new GiftSheet(this, iArr[0], l.longValue(), null).show();
    }

    public /* synthetic */ void lambda$handleIntent$13(final AlertDialog alertDialog, final String str, final Bundle bundle, final TL_account.sendConfirmPhoneCode sendconfirmphonecode, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda60
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleIntent$12(alertDialog, tL_error, str, bundle, tLObject, sendconfirmphonecode);
            }
        });
    }

    public /* synthetic */ void lambda$handleIntent$12(AlertDialog alertDialog, TLRPC.TL_error tL_error, String str, Bundle bundle, TLObject tLObject, TL_account.sendConfirmPhoneCode sendconfirmphonecode) {
        alertDialog.dismiss();
        if (tL_error == null) {
            lambda$runLinkRequest$106(new LoginActivity().cancelAccountDeletion(str, bundle, (TLRPC.TL_auth_sentCode) tLObject));
        } else {
            AlertsCreator.processError(this.currentAccount, tL_error, getActionBarLayout().getLastFragment(), sendconfirmphonecode, new Object[0]);
        }
    }

    public /* synthetic */ void lambda$handleIntent$15(final TLRPC.TL_messages_requestUrlAuth tL_messages_requestUrlAuth, final String str, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda92
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleIntent$14(tLObject, tL_messages_requestUrlAuth, str, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$handleIntent$14(TLObject tLObject, TLRPC.TL_messages_requestUrlAuth tL_messages_requestUrlAuth, String str, TLRPC.TL_error tL_error) {
        BaseFragment safeLastFragment = getSafeLastFragment();
        if (tLObject == null) {
            if (tL_error != null) {
                if ("URL_EXPIRED".equalsIgnoreCase(tL_error.text)) {
                    OAuthSheet.getBulletinFactory().createSimpleBulletin(C2888R.raw.error, getString(C2888R.string.BotAuthLoggedInFailTitle), getString(C2888R.string.BotAuthLoggedInFailNoDomain)).show();
                    return;
                } else {
                    OAuthSheet.getBulletinFactory().showForError(tL_error);
                    return;
                }
            }
            return;
        }
        if (tLObject instanceof TLRPC.TL_urlAuthResultRequest) {
            OAuthSheet.handle(false, this.currentAccount, tL_messages_requestUrlAuth, (TLRPC.TL_urlAuthResultRequest) tLObject);
        } else if (tLObject instanceof TLRPC.TL_urlAuthResultAccepted) {
            OAuthSheet.handle(false, this.currentAccount, tL_messages_requestUrlAuth, (TLRPC.TL_urlAuthResultAccepted) tLObject);
        } else if (tLObject instanceof TLRPC.TL_urlAuthResultDefault) {
            AlertsCreator.showOpenUrlAlert(safeLastFragment, str, false, true);
        }
    }

    public /* synthetic */ void lambda$handleIntent$16(long j, long j2, ChatActivity chatActivity) {
        FileLog.m1133d("LaunchActivity openForum after load " + j + " " + j2 + " TL_forumTopic " + MessagesController.getInstance(this.currentAccount).getTopicsController().findTopic(j, j2));
        if (this.actionBarLayout != null) {
            ForumUtilities.applyTopic(chatActivity, MessagesStorage.TopicKey.m1166of(-j, j2));
            getActionBarLayout().presentFragment(chatActivity);
        }
    }

    public /* synthetic */ void lambda$handleIntent$19(AlertDialog alertDialog, final ProfileActivity profileActivity, TLRPC.User user) {
        alertDialog.dismiss();
        if (user != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda88
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$handleIntent$17(profileActivity);
                }
            });
            if (AndroidUtilities.isTablet()) {
                this.actionBarLayout.showLastFragment();
                this.rightActionBarLayout.showLastFragment();
                return;
            }
            return;
        }
        showBulletin(new Function() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda89
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                return ((BulletinFactory) obj).createErrorBulletin(LocaleController.getString(C2888R.string.UserNotFound));
            }
        });
    }

    public /* synthetic */ void lambda$handleIntent$17(ProfileActivity profileActivity) {
        presentFragment(profileActivity, false, false);
    }

    public /* synthetic */ void lambda$handleIntent$22(AlertDialog alertDialog, final ChatActivity chatActivity, TLRPC.Chat chat) {
        alertDialog.dismiss();
        if (chat != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda58
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$handleIntent$20(chatActivity);
                }
            });
            if (AndroidUtilities.isTablet()) {
                this.actionBarLayout.showLastFragment();
                this.rightActionBarLayout.showLastFragment();
                return;
            }
            return;
        }
        showBulletin(new Function() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda59
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                return ((BulletinFactory) obj).createErrorBulletin(LocaleController.getString(C2888R.string.ChatNotFound));
            }
        });
    }

    public /* synthetic */ void lambda$handleIntent$20(ChatActivity chatActivity) {
        presentFragment(chatActivity, false, false);
    }

    public /* synthetic */ void lambda$handleIntent$24(final int[] iArr, LocationController.SharingLocationInfo sharingLocationInfo) {
        int i = sharingLocationInfo.messageObject.currentAccount;
        iArr[0] = i;
        switchToAccount(i, true);
        LocationActivity locationActivity = new LocationActivity(2);
        locationActivity.setMessageObject(sharingLocationInfo.messageObject);
        final long dialogId = sharingLocationInfo.messageObject.getDialogId();
        locationActivity.setDelegate(new LocationActivity.LocationActivityDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda111
            @Override // org.telegram.ui.LocationActivity.LocationActivityDelegate
            public final void didSelectLocation(TLRPC.MessageMedia messageMedia, int i2, boolean z, int i3, long j) {
                SendMessagesHelper.getInstance(iArr[0]).sendMessage(SendMessagesHelper.SendMessageParams.m1179of(messageMedia, dialogId, (MessageObject) null, (MessageObject) null, (TLRPC.ReplyMarkup) null, (HashMap<String, String>) null, z, i3, 0));
            }
        });
        lambda$runLinkRequest$106(locationActivity);
    }

    public /* synthetic */ void lambda$handleIntent$25() {
        if (this.actionBarLayout.getFragmentStack().isEmpty()) {
            return;
        }
        this.actionBarLayout.getFragmentStack().get(0).showDialog(new StickersAlert(this, this.importingStickersSoftware, this.importingStickers, this.importingStickersEmoji, null));
    }

    public /* synthetic */ void lambda$handleIntent$27(TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tLObject != null) {
            final TL_account.Password password = (TL_account.Password) tLObject;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda42
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$handleIntent$26(password);
                }
            });
        }
    }

    public /* synthetic */ void lambda$handleIntent$28(BaseFragment baseFragment, boolean z) {
        presentFragment(baseFragment, z, false);
    }

    public /* synthetic */ void lambda$handleIntent$29(boolean z, int[] iArr, TLRPC.User user, String str, ContactsActivity contactsActivity) {
        TLRPC.UserFull userFull = MessagesController.getInstance(this.currentAccount).getUserFull(user.f1825id);
        VoIPHelper.startCall(user, z, userFull != null && userFull.video_calls_available, this, userFull, AccountInstance.getInstance(iArr[0]));
    }

    public /* synthetic */ void lambda$handleIntent$33(final ActionIntroActivity actionIntroActivity, String str) {
        final AlertDialog alertDialog = new AlertDialog(this, 3);
        alertDialog.setCanCancel(false);
        alertDialog.show();
        byte[] bArrDecode = Base64.decode(str.substring(17), 8);
        TLRPC.TL_auth_acceptLoginToken tL_auth_acceptLoginToken = new TLRPC.TL_auth_acceptLoginToken();
        tL_auth_acceptLoginToken.token = bArrDecode;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_auth_acceptLoginToken, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda166
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda170
                    @Override // java.lang.Runnable
                    public final void run() {
                        LaunchActivity.$r8$lambda$crzoKiE1huLZC7kMM1dg1gy89Ww(alertDialog, tLObject, actionIntroActivity, tL_error);
                    }
                });
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$crzoKiE1huLZC7kMM1dg1gy89Ww(AlertDialog alertDialog, TLObject tLObject, final ActionIntroActivity actionIntroActivity, final TLRPC.TL_error tL_error) {
        try {
            alertDialog.dismiss();
        } catch (Exception unused) {
        }
        if (tLObject instanceof TLRPC.TL_authorization) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda180
            @Override // java.lang.Runnable
            public final void run() {
                AlertsCreator.showSimpleAlert(actionIntroActivity, LocaleController.getString(C2888R.string.AuthAnotherClient), LocaleController.getString(C2888R.string.ErrorOccurred) + "\n" + tL_error.text);
            }
        });
    }

    public /* synthetic */ void lambda$handleIntent$34(BaseFragment baseFragment, String str, String str2, AlertDialog alertDialog, int i) {
        NewContactBottomSheet newContactBottomSheet = new NewContactBottomSheet(baseFragment, this);
        newContactBottomSheet.setInitialPhoneNumber(str, false);
        if (str2 != null) {
            String[] strArrSplit = str2.split(" ", 2);
            newContactBottomSheet.setInitialName(strArrSplit[0], strArrSplit.length > 1 ? strArrSplit[1] : null);
        }
        newContactBottomSheet.show();
    }

    /* JADX INFO: renamed from: openEmailSettings */
    public void lambda$handleIntent$26(TL_account.Password password) {
        String str;
        final LoginActivity loginActivityChangeEmail = new LoginActivity().changeEmail(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda124
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openEmailSettings$35();
            }
        });
        if (password != null && (str = password.login_email_pattern) != null) {
            SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(str);
            int iIndexOf = password.login_email_pattern.indexOf(42);
            int iLastIndexOf = password.login_email_pattern.lastIndexOf(42);
            if (iIndexOf != iLastIndexOf && iIndexOf != -1 && iLastIndexOf != -1) {
                TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
                textStyleRun.flags |= 256;
                textStyleRun.start = iIndexOf;
                int i = iLastIndexOf + 1;
                textStyleRun.end = i;
                spannableStringBuilderValueOf.setSpan(new TextStyleSpan(textStyleRun), iIndexOf, i, 0);
            }
            new AlertDialog.Builder(this).setTitle(spannableStringBuilderValueOf).setMessage(getString(C2888R.string.EmailLoginChangeMessage)).setPositiveButton(getString(C2888R.string.ChangeEmail), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda125
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i2) {
                    this.f$0.lambda$openEmailSettings$36(loginActivityChangeEmail, alertDialog, i2);
                }
            }).setNegativeButton(getString(C2888R.string.Cancel), null).show();
            return;
        }
        lambda$runLinkRequest$106(loginActivityChangeEmail);
    }

    public /* synthetic */ void lambda$openEmailSettings$35() {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(this, null);
        lottieLayout.setAnimation(C2888R.raw.email_check_inbox, new String[0]);
        lottieLayout.textView.setText(getString(C2888R.string.YourLoginEmailChangedSuccess));
        BaseFragment lastFragment = getLastFragment();
        if (lastFragment != null) {
            Bulletin.make(lastFragment, lottieLayout, 1500).show();
            try {
                lastFragment.fragmentView.performHapticFeedback(3, 2);
            } catch (Exception unused) {
            }
        }
    }

    public /* synthetic */ void lambda$openEmailSettings$36(LoginActivity loginActivity, AlertDialog alertDialog, int i) {
        lambda$runLinkRequest$106(loginActivity);
    }

    public static int getTimestampFromLink(Uri uri) {
        String queryParameter;
        if (uri.getPathSegments().contains(MediaStreamTrack.VIDEO_TRACK_KIND)) {
            queryParameter = uri.getQuery();
        } else {
            queryParameter = uri.getQueryParameter("t") != null ? uri.getQueryParameter("t") : null;
        }
        if (TextUtils.isEmpty(queryParameter)) {
            return -1;
        }
        if (timestampPattern == null) {
            timestampPattern = Pattern.compile("^\\??(?:(\\d+)[dD])?(?:(\\d+)h)?(?:(\\d+)[mM])?(?:(\\d+)[sS])?$");
        }
        try {
            Matcher matcher = timestampPattern.matcher(queryParameter);
            if (matcher.matches()) {
                String strGroup = matcher.group(1);
                String strGroup2 = matcher.group(2);
                String strGroup3 = matcher.group(3);
                String strGroup4 = matcher.group(4);
                int i = 0;
                int i2 = TextUtils.isEmpty(strGroup) ? 0 : Integer.parseInt(strGroup);
                int i3 = TextUtils.isEmpty(strGroup2) ? 0 : Integer.parseInt(strGroup2);
                int i4 = TextUtils.isEmpty(strGroup3) ? 0 : Integer.parseInt(strGroup3);
                if (!TextUtils.isEmpty(strGroup4)) {
                    i = Integer.parseInt(strGroup4);
                }
                return i + (i4 * 60) + (i3 * 3600) + (i2 * 86400);
            }
        } catch (Throwable unused) {
        }
        try {
            return Integer.parseInt(queryParameter);
        } catch (Throwable unused2) {
            if (!queryParameter.contains(":")) {
                return -1;
            }
            String[] strArrSplit = queryParameter.split(":");
            int length = strArrSplit.length - 1;
            String str = MVEL.VERSION_SUB;
            String str2 = length < 0 ? MVEL.VERSION_SUB : strArrSplit[strArrSplit.length - 1];
            String str3 = strArrSplit.length - 2 < 0 ? MVEL.VERSION_SUB : strArrSplit[strArrSplit.length - 2];
            String str4 = strArrSplit.length - 3 < 0 ? MVEL.VERSION_SUB : strArrSplit[strArrSplit.length - 3];
            if (strArrSplit.length - 4 >= 0) {
                str = strArrSplit[strArrSplit.length - 4];
            }
            try {
                return Integer.parseInt(str2) + (Integer.parseInt(str3) * 60) + (Integer.parseInt(str4) * 3600) + (Integer.parseInt(str) * 86400);
            } catch (Exception e) {
                FileLog.m1136e(e);
                return -1;
            }
        }
    }

    private void openDialogsToSend(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("onlySelect", true);
        bundle.putBoolean("canSelectTopics", true);
        bundle.putInt("dialogsType", 3);
        bundle.putBoolean("allowSwitchAccount", true);
        ArrayList arrayList = this.contactsToSend;
        if (arrayList != null) {
            if (arrayList.size() != 1) {
                bundle.putString("selectAlertString", LocaleController.getString(C2888R.string.SendMessagesToText));
                bundle.putString("selectAlertStringGroup", LocaleController.getString(C2888R.string.SendContactToGroupText));
            }
        } else {
            bundle.putString("selectAlertString", LocaleController.getString(C2888R.string.SendMessagesToText));
            bundle.putString("selectAlertStringGroup", LocaleController.getString(C2888R.string.SendMessagesToGroupText));
        }
        C589914 c589914 = new DialogsActivity(bundle) { // from class: org.telegram.ui.LaunchActivity.14
            C589914(Bundle bundle2) {
                super(bundle2);
            }

            @Override // org.telegram.p029ui.DialogsActivity
            public boolean shouldShowNextButton(DialogsActivity dialogsActivity, ArrayList arrayList2, CharSequence charSequence, boolean z2) {
                if (LaunchActivity.this.exportingChatUri != null) {
                    return false;
                }
                if (LaunchActivity.this.contactsToSend != null && LaunchActivity.this.contactsToSend.size() == 1 && !LaunchActivity.mainFragmentsStack.isEmpty()) {
                    return true;
                }
                if (arrayList2.size() <= 1) {
                    return LaunchActivity.this.videoPath != null || (LaunchActivity.this.photoPathsArray != null && LaunchActivity.this.photoPathsArray.size() > 0);
                }
                return false;
            }
        };
        c589914.setDelegate(this);
        getActionBarLayout().presentFragment(c589914, !AndroidUtilities.isTablet() ? this.actionBarLayout.getFragmentStack().size() <= 1 || !(this.actionBarLayout.getFragmentStack().get(this.actionBarLayout.getFragmentStack().size() - 1) instanceof MainTabsActivity) : this.layersActionBarLayout.getFragmentStack().isEmpty() || !(this.layersActionBarLayout.getFragmentStack().get(this.layersActionBarLayout.getFragmentStack().size() - 1) instanceof MainTabsActivity), !z, true, false);
        if (SecretMediaViewer.hasInstance() && SecretMediaViewer.getInstance().isVisible()) {
            SecretMediaViewer.getInstance().closePhoto(false, false);
        } else if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().closePhoto(false, true);
        } else if (ArticleViewer.hasInstance() && ArticleViewer.getInstance().isVisible()) {
            ArticleViewer.getInstance().close(false, true);
        }
        StoryRecorder.destroyInstance();
        GroupCallActivity groupCallActivity = GroupCallActivity.groupCallInstance;
        if (groupCallActivity != null) {
            groupCallActivity.dismiss();
        }
        if (z || !AndroidUtilities.isTablet()) {
            return;
        }
        this.actionBarLayout.rebuildFragments(1);
        this.rightActionBarLayout.rebuildFragments(1);
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$14 */
    /* JADX INFO: loaded from: classes6.dex */
    class C589914 extends DialogsActivity {
        C589914(Bundle bundle2) {
            super(bundle2);
        }

        @Override // org.telegram.p029ui.DialogsActivity
        public boolean shouldShowNextButton(DialogsActivity dialogsActivity, ArrayList arrayList2, CharSequence charSequence, boolean z2) {
            if (LaunchActivity.this.exportingChatUri != null) {
                return false;
            }
            if (LaunchActivity.this.contactsToSend != null && LaunchActivity.this.contactsToSend.size() == 1 && !LaunchActivity.mainFragmentsStack.isEmpty()) {
                return true;
            }
            if (arrayList2.size() <= 1) {
                return LaunchActivity.this.videoPath != null || (LaunchActivity.this.photoPathsArray != null && LaunchActivity.this.photoPathsArray.size() > 0);
            }
            return false;
        }
    }

    private int runCommentRequest(int i, Runnable runnable, Integer num, Integer num2, Long l, Integer num3, TLRPC.Chat chat) {
        return runCommentRequest(i, runnable, num, num2, l, num3, chat, null, null, 0, -1);
    }

    private int runCommentRequest(final int i, final Runnable runnable, final Integer num, final Integer num2, final Long l, final Integer num3, final TLRPC.Chat chat, final Runnable runnable2, final String str, final int i2, final int i3) {
        if (chat == null) {
            return 0;
        }
        final TLRPC.TL_messages_getDiscussionMessage tL_messages_getDiscussionMessage = new TLRPC.TL_messages_getDiscussionMessage();
        tL_messages_getDiscussionMessage.peer = MessagesController.getInputPeer(chat);
        tL_messages_getDiscussionMessage.msg_id = num2 != null ? num.intValue() : (int) l.longValue();
        return ConnectionsManager.getInstance(i).sendRequest(tL_messages_getDiscussionMessage, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda117
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$runCommentRequest$38(i, chat, l, num2, num, runnable2, str, num3, i2, i3, tL_messages_getDiscussionMessage, runnable, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$runCommentRequest$38(final int i, final TLRPC.Chat chat, final Long l, final Integer num, final Integer num2, final Runnable runnable, final String str, final Integer num3, final int i2, final int i3, final TLRPC.TL_messages_getDiscussionMessage tL_messages_getDiscussionMessage, final Runnable runnable2, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda155
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runCommentRequest$37(tLObject, i, chat, l, num, num2, runnable, str, num3, i2, i3, tL_messages_getDiscussionMessage, runnable2);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:99:0x00e8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$runCommentRequest$37(org.telegram.tgnet.TLObject r14, int r15, org.telegram.tgnet.TLRPC.Chat r16, java.lang.Long r17, java.lang.Integer r18, java.lang.Integer r19, java.lang.Runnable r20, java.lang.String r21, java.lang.Integer r22, int r23, int r24, org.telegram.tgnet.TLRPC.TL_messages_getDiscussionMessage r25, java.lang.Runnable r26) {
        /*
            Method dump skipped, instruction units count: 293
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.lambda$runCommentRequest$37(org.telegram.tgnet.TLObject, int, org.telegram.tgnet.TLRPC$Chat, java.lang.Long, java.lang.Integer, java.lang.Integer, java.lang.Runnable, java.lang.String, java.lang.Integer, int, int, org.telegram.tgnet.TLRPC$TL_messages_getDiscussionMessage, java.lang.Runnable):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:68:0x00af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void openTopicRequest(final int r17, int r18, final org.telegram.tgnet.TLRPC.Chat r19, final int r20, org.telegram.tgnet.TLRPC.TL_forumTopic r21, final java.lang.Runnable r22, final java.lang.String r23, final java.lang.Integer r24, final int r25, final java.util.ArrayList r26, final int r27) {
        /*
            Method dump skipped, instruction units count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.openTopicRequest(int, int, org.telegram.tgnet.TLRPC$Chat, int, org.telegram.tgnet.TLRPC$TL_forumTopic, java.lang.Runnable, java.lang.String, java.lang.Integer, int, java.util.ArrayList, int):void");
    }

    public /* synthetic */ void lambda$openTopicRequest$40(final int i, final TLRPC.Chat chat, final int i2, final int i3, final Runnable runnable, final String str, final Integer num, final int i4, final ArrayList arrayList, final int i5, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda173
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openTopicRequest$39(tL_error, tLObject, i, chat, i2, i3, runnable, str, num, i4, arrayList, i5);
            }
        });
    }

    public /* synthetic */ void lambda$openTopicRequest$39(TLRPC.TL_error tL_error, TLObject tLObject, int i, TLRPC.Chat chat, int i2, int i3, Runnable runnable, String str, Integer num, int i4, ArrayList arrayList, int i5) {
        if (tL_error == null) {
            TLRPC.TL_messages_forumTopics tL_messages_forumTopics = (TLRPC.TL_messages_forumTopics) tLObject;
            LongSparseArray longSparseArray = new LongSparseArray();
            for (int i6 = 0; i6 < tL_messages_forumTopics.messages.size(); i6++) {
                longSparseArray.put(((TLRPC.Message) tL_messages_forumTopics.messages.get(i6)).f1686id, (TLRPC.Message) tL_messages_forumTopics.messages.get(i6));
            }
            MessagesController.getInstance(i).putUsers(tL_messages_forumTopics.users, false);
            MessagesController.getInstance(i).putChats(tL_messages_forumTopics.chats, false);
            MessagesController.getInstance(i).getTopicsController().processTopics(chat.f1660id, tL_messages_forumTopics.topics, longSparseArray, false, 2, -1);
            openTopicRequest(i, i2, chat, i3, MessagesController.getInstance(i).getTopicsController().findTopic(chat.f1660id, i2), runnable, str, num, i4, arrayList, i5);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:128:0x004e, code lost:
    
        r8 = new java.io.BufferedReader(new java.io.InputStreamReader(r0));
        r5 = new java.lang.StringBuilder();
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x005d, code lost:
    
        r6 = r8.readLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x0061, code lost:
    
        if (r6 == null) goto L214;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0063, code lost:
    
        if (r3 >= 100) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0065, code lost:
    
        r5.append(r6);
        r5.append('\n');
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0070, code lost:
    
        r4 = r5.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0083, code lost:
    
        r0.closeEntry();
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0086, code lost:
    
        r0.close();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String readImport(android.net.Uri r8) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 245
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.readImport(android.net.Uri):java.lang.String");
    }

    private void runImportRequest(final Uri uri, ArrayList arrayList) throws Throwable {
        final int i = UserConfig.selectedAccount;
        final AlertDialog alertDialog = new AlertDialog(this, 3);
        final int[] iArr = {0};
        String str = readImport(uri);
        if (str == null) {
            return;
        }
        TLRPC.TL_messages_checkHistoryImport tL_messages_checkHistoryImport = new TLRPC.TL_messages_checkHistoryImport();
        tL_messages_checkHistoryImport.import_head = str;
        iArr[0] = ConnectionsManager.getInstance(i).sendRequest(tL_messages_checkHistoryImport, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda52
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$runImportRequest$42(uri, i, alertDialog, tLObject, tL_error);
            }
        });
        final Runnable runnable = null;
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda53
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                LaunchActivity.m15059$r8$lambda$Ri31_SnZAzAXL8HtJmPoDujCJU(i, iArr, runnable, dialogInterface);
            }
        });
        try {
            alertDialog.showDelayed(300L);
        } catch (Exception unused) {
        }
    }

    public /* synthetic */ void lambda$runImportRequest$42(final Uri uri, final int i, final AlertDialog alertDialog, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda129
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runImportRequest$41(tLObject, uri, i, alertDialog);
            }
        }, 2L);
    }

    public /* synthetic */ void lambda$runImportRequest$41(TLObject tLObject, Uri uri, int i, AlertDialog alertDialog) {
        boolean z;
        if (isFinishing()) {
            return;
        }
        boolean z2 = false;
        if (tLObject != null && this.actionBarLayout != null) {
            TLRPC.TL_messages_historyImportParsed tL_messages_historyImportParsed = (TLRPC.TL_messages_historyImportParsed) tLObject;
            Bundle bundle = new Bundle();
            bundle.putBoolean("onlySelect", true);
            bundle.putString("importTitle", tL_messages_historyImportParsed.title);
            bundle.putBoolean("allowSwitchAccount", true);
            if (tL_messages_historyImportParsed.f1769pm) {
                bundle.putInt("dialogsType", 12);
            } else if (tL_messages_historyImportParsed.group) {
                bundle.putInt("dialogsType", 11);
            } else {
                String string = uri.toString();
                Iterator<String> it = MessagesController.getInstance(i).exportPrivateUri.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    } else if (string.contains(it.next())) {
                        bundle.putInt("dialogsType", 12);
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    Iterator<String> it2 = MessagesController.getInstance(i).exportGroupUri.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if (string.contains(it2.next())) {
                            bundle.putInt("dialogsType", 11);
                            z = true;
                            break;
                        }
                    }
                    if (!z) {
                        bundle.putInt("dialogsType", 13);
                    }
                }
            }
            if (SecretMediaViewer.hasInstance() && SecretMediaViewer.getInstance().isVisible()) {
                SecretMediaViewer.getInstance().closePhoto(false, false);
            } else if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
                PhotoViewer.getInstance().closePhoto(false, true);
            } else if (ArticleViewer.hasInstance() && ArticleViewer.getInstance().isVisible()) {
                ArticleViewer.getInstance().close(false, true);
            }
            StoryRecorder.destroyInstance();
            GroupCallActivity groupCallActivity = GroupCallActivity.groupCallInstance;
            if (groupCallActivity != null) {
                groupCallActivity.dismiss();
            }
            if (AndroidUtilities.isTablet()) {
                this.actionBarLayout.rebuildFragments(1);
                this.rightActionBarLayout.rebuildFragments(1);
            }
            DialogsActivity dialogsActivity = new DialogsActivity(bundle);
            dialogsActivity.setDelegate(this);
            if (!AndroidUtilities.isTablet() ? !(this.actionBarLayout.getFragmentStack().size() <= 1 || !(this.actionBarLayout.getFragmentStack().get(this.actionBarLayout.getFragmentStack().size() - 1) instanceof MainTabsActivity)) : !(this.layersActionBarLayout.getFragmentStack().isEmpty() || !(this.layersActionBarLayout.getFragmentStack().get(this.layersActionBarLayout.getFragmentStack().size() - 1) instanceof MainTabsActivity))) {
                z2 = true;
            }
            getActionBarLayout().presentFragment(dialogsActivity, z2, false, true, false);
        } else {
            if (this.documentsUrisArray == null) {
                this.documentsUrisArray = new ArrayList();
            }
            this.documentsUrisArray.add(0, this.exportingChatUri);
            this.exportingChatUri = null;
            openDialogsToSend(true);
        }
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$Ri31_SnZAzAXL8HtJmPoD-ujCJU */
    public static /* synthetic */ void m15059$r8$lambda$Ri31_SnZAzAXL8HtJmPoDujCJU(int i, int[] iArr, Runnable runnable, DialogInterface dialogInterface) {
        ConnectionsManager.getInstance(i).cancelRequest(iArr[0], true);
        if (runnable != null) {
            runnable.run();
        }
    }

    public void openMessage(final long j, final int i, final String str, final Browser.Progress progress, int i2, final int i3, final Integer num) {
        TLRPC.Chat chat;
        if (j < 0 && (chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-j))) != null && ChatObject.isForum(chat)) {
            if (progress != null) {
                progress.init();
            }
            openForumFromLink(j, Integer.valueOf(i), str, num, new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda144
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchActivity.$r8$lambda$ygNXKl9d55jsC09zHKY7xNzRd5o(progress);
                }
            }, i2, i3);
            return;
        }
        if (progress != null) {
            progress.init();
        }
        final Bundle bundle = new Bundle();
        if (j >= 0) {
            bundle.putLong("user_id", j);
        } else {
            long j2 = -j;
            TLRPC.Chat chat2 = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(j2));
            if (chat2 != null && chat2.forum) {
                openForumFromLink(j, Integer.valueOf(i), str, num, new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda145
                    @Override // java.lang.Runnable
                    public final void run() {
                        LaunchActivity.$r8$lambda$GSYTCpcud_Va_qQ02I0aM99qqCA(progress);
                    }
                }, i2, i3);
                return;
            }
            bundle.putLong("chat_id", j2);
        }
        bundle.putInt("message_id", i);
        ArrayList arrayList = mainFragmentsStack;
        final BaseFragment baseFragment = !arrayList.isEmpty() ? (BaseFragment) arrayList.get(arrayList.size() - 1) : null;
        if (baseFragment == null || MessagesController.getInstance(this.currentAccount).checkCanOpenChat(bundle, baseFragment)) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda146
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$openMessage$49(bundle, num, i, str, i3, j, progress, baseFragment);
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$ygNXKl9d55jsC09zHKY7xNzRd5o(Browser.Progress progress) {
        if (progress != null) {
            progress.end();
        }
    }

    public static /* synthetic */ void $r8$lambda$GSYTCpcud_Va_qQ02I0aM99qqCA(Browser.Progress progress) {
        if (progress != null) {
            progress.end();
        }
    }

    public /* synthetic */ void lambda$openMessage$49(final Bundle bundle, final Integer num, int i, String str, int i2, final long j, final Browser.Progress progress, final BaseFragment baseFragment) {
        final int i3;
        final String str2;
        final int i4;
        final ChatActivity chatActivity = new ChatActivity(bundle);
        if (num != null) {
            chatActivity.highlightTaskId = num;
            i3 = i;
            str2 = str;
            i4 = i2;
        } else {
            i3 = i;
            str2 = str;
            i4 = i2;
            chatActivity.setHighlightQuote(i3, str2, i4);
        }
        if ((AndroidUtilities.isTablet() ? this.rightActionBarLayout : getActionBarLayout()).presentFragment(chatActivity) || j >= 0) {
            if (progress != null) {
                progress.end();
                return;
            }
            return;
        }
        TLRPC.TL_channels_getChannels tL_channels_getChannels = new TLRPC.TL_channels_getChannels();
        TLRPC.TL_inputChannel tL_inputChannel = new TLRPC.TL_inputChannel();
        tL_inputChannel.channel_id = -j;
        tL_channels_getChannels.f1704id.add(tL_inputChannel);
        final int iSendRequest = ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_channels_getChannels, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda161
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$openMessage$47(progress, j, i3, num, baseFragment, bundle, chatActivity, str2, i4, tLObject, tL_error);
            }
        });
        if (progress != null) {
            progress.onCancel(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda162
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$openMessage$48(iSendRequest);
                }
            });
        }
    }

    public /* synthetic */ void lambda$openMessage$47(final Browser.Progress progress, final long j, final int i, final Integer num, final BaseFragment baseFragment, final Bundle bundle, final ChatActivity chatActivity, final String str, final int i2, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda176
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openMessage$46(progress, tLObject, j, i, num, baseFragment, bundle, chatActivity, str, i2);
            }
        });
    }

    public /* synthetic */ void lambda$openMessage$46(Browser.Progress progress, TLObject tLObject, long j, int i, Integer num, BaseFragment baseFragment, Bundle bundle, ChatActivity chatActivity, String str, int i2) {
        if (progress != null) {
            progress.end();
        }
        if (tLObject instanceof TLRPC.TL_messages_chats) {
            TLRPC.TL_messages_chats tL_messages_chats = (TLRPC.TL_messages_chats) tLObject;
            if (!tL_messages_chats.chats.isEmpty()) {
                MessagesController.getInstance(this.currentAccount).putChats(tL_messages_chats.chats, false);
                TLRPC.Chat chat = (TLRPC.Chat) tL_messages_chats.chats.get(0);
                if (chat != null && chat.forum) {
                    openForumFromLink(-j, Integer.valueOf(i), null, num, null, 0, -1);
                }
                if (baseFragment == null || MessagesController.getInstance(this.currentAccount).checkCanOpenChat(bundle, baseFragment)) {
                    ChatActivity chatActivity2 = new ChatActivity(bundle);
                    chatActivity.setHighlightQuote(i, str, i2);
                    getActionBarLayout().presentFragment(chatActivity2);
                    return;
                }
                return;
            }
        }
        showAlertDialog(AlertsCreator.createNoAccessAlert(this, LocaleController.getString(C2888R.string.DialogNotAvailable), LocaleController.getString(C2888R.string.LinkNotFound), null));
    }

    public /* synthetic */ void lambda$openMessage$48(int i) {
        ConnectionsManager.getInstance(this.currentAccount).cancelRequest(i, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:302:0x0669  */
    /* JADX WARN: Removed duplicated region for block: B:317:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void runLinkRequest(final int r65, final java.lang.String r66, final java.lang.String r67, final java.lang.String r68, final java.lang.String r69, final java.lang.String r70, final java.lang.String r71, final java.lang.String r72, final java.lang.String r73, final java.lang.String r74, final java.lang.String r75, final java.lang.String r76, final java.lang.String r77, final boolean r78, final java.lang.Integer r79, final java.lang.Long r80, final java.lang.Long r81, final java.lang.Integer r82, final java.lang.String r83, final java.util.HashMap r84, final java.lang.String r85, final java.lang.String r86, final java.lang.String r87, final java.lang.String r88, final org.telegram.tgnet.TLRPC.TL_wallPaper r89, final java.lang.String r90, final java.lang.String r91, final java.lang.String r92, final java.lang.String r93, final boolean r94, final java.lang.String r95, final int r96, final int r97, final java.lang.String r98, final java.lang.String r99, final java.lang.String r100, final java.lang.String r101, final java.lang.String r102, final org.telegram.messenger.browser.Browser.Progress r103, final boolean r104, final int r105, final boolean r106, final int r107, final int r108, final java.lang.String r109, final java.lang.String r110, final boolean r111, final java.lang.String r112, final boolean r113, final boolean r114, final boolean r115, final boolean r116, final boolean r117, final java.lang.String r118, final java.lang.Integer r119, final boolean r120) {
        /*
            Method dump skipped, instruction units count: 1671
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.runLinkRequest(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.Integer, java.lang.Long, java.lang.Long, java.lang.Integer, java.lang.String, java.util.HashMap, java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.telegram.tgnet.TLRPC$TL_wallPaper, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.telegram.messenger.browser.Browser$Progress, boolean, int, boolean, int, int, java.lang.String, java.lang.String, boolean, java.lang.String, boolean, boolean, boolean, boolean, boolean, java.lang.String, java.lang.Integer, boolean):void");
    }

    public /* synthetic */ void lambda$runLinkRequest$52(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, boolean z, Integer num, Long l, Long l2, Integer num2, String str13, HashMap map, String str14, String str15, String str16, String str17, TLRPC.TL_wallPaper tL_wallPaper, String str18, String str19, String str20, String str21, boolean z2, String str22, int i2, String str23, String str24, String str25, String str26, String str27, Browser.Progress progress, boolean z3, int i3, boolean z4, int i4, int i5, String str28, String str29, boolean z5, String str30, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, String str31, Integer num3, boolean z11, int i6) {
        LaunchActivity launchActivity;
        if (i6 != i) {
            launchActivity = this;
            launchActivity.switchToAccount(i6, true);
        } else {
            launchActivity = this;
        }
        launchActivity.runLinkRequest(i6, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, num, l, l2, num2, str13, map, str14, str15, str16, str17, tL_wallPaper, str18, str19, str20, str21, z2, str22, 1, i2, str23, str24, str25, str26, str27, progress, z3, i3, z4, i4, i5, str28, str29, z5, str30, z6, z7, z8, z9, z10, str31, num3, z11);
    }

    /* JADX INFO: renamed from: $r8$lambda$3mq-kCnFVWhkvyHJ3aShw5DLqWM */
    public static /* synthetic */ void m15039$r8$lambda$3mqkCnFVWhkvyHJ3aShw5DLqWM(Browser.Progress progress, AlertDialog alertDialog) {
        if (progress != null) {
            progress.end();
        }
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$55(final int i, final String str, final Runnable runnable, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda133
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$54(tLObject, i, str, tL_error, runnable);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$54(TLObject tLObject, int i, String str, TLRPC.TL_error tL_error, Runnable runnable) {
        if (tLObject instanceof TLRPC.User) {
            TLRPC.User user = (TLRPC.User) tLObject;
            MessagesController.getInstance(i).putUser(user, false);
            Bundle bundle = new Bundle();
            bundle.putLong("user_id", user.f1825id);
            lambda$runLinkRequest$106(new ChatActivity(bundle));
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("cant import contact token. token=");
            sb.append(str);
            sb.append(" err=");
            sb.append(tL_error == null ? null : tL_error.text);
            FileLog.m1134e(sb.toString());
            BulletinFactory.m1246of((BaseFragment) mainFragmentsStack.get(r3.size() - 1)).createErrorBulletin(LocaleController.getString(C2888R.string.NoUsernameFound)).show();
        }
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$bEwydmSNMGgs3h-Pti7o_ol2sD0 */
    public static /* synthetic */ void m15072$r8$lambda$bEwydmSNMGgs3hPti7o_ol2sD0(TLObject tLObject, int i, String str, Runnable runnable) {
        ArrayList<TLRPC.Chat> arrayList;
        ArrayList<TLRPC.User> arrayList2;
        BaseFragment baseFragment = (BaseFragment) mainFragmentsStack.get(r0.size() - 1);
        if (tLObject instanceof TL_chatlists.chatlist_ChatlistInvite) {
            TL_chatlists.chatlist_ChatlistInvite chatlist_chatlistinvite = (TL_chatlists.chatlist_ChatlistInvite) tLObject;
            boolean z = chatlist_chatlistinvite instanceof TL_chatlists.TL_chatlists_chatlistInvite;
            if (z) {
                TL_chatlists.TL_chatlists_chatlistInvite tL_chatlists_chatlistInvite = (TL_chatlists.TL_chatlists_chatlistInvite) chatlist_chatlistinvite;
                arrayList = tL_chatlists_chatlistInvite.chats;
                arrayList2 = tL_chatlists_chatlistInvite.users;
            } else if (chatlist_chatlistinvite instanceof TL_chatlists.TL_chatlists_chatlistInviteAlready) {
                TL_chatlists.TL_chatlists_chatlistInviteAlready tL_chatlists_chatlistInviteAlready = (TL_chatlists.TL_chatlists_chatlistInviteAlready) chatlist_chatlistinvite;
                arrayList = tL_chatlists_chatlistInviteAlready.chats;
                arrayList2 = tL_chatlists_chatlistInviteAlready.users;
            } else {
                arrayList = null;
                arrayList2 = null;
            }
            MessagesController.getInstance(i).putChats(arrayList, false);
            MessagesController.getInstance(i).putUsers(arrayList2, false);
            if (!z || !((TL_chatlists.TL_chatlists_chatlistInvite) chatlist_chatlistinvite).peers.isEmpty()) {
                FolderBottomSheet folderBottomSheet = new FolderBottomSheet(baseFragment, str, chatlist_chatlistinvite);
                if (baseFragment != null) {
                    baseFragment.showDialog(folderBottomSheet);
                } else {
                    folderBottomSheet.show();
                }
            } else {
                BulletinFactory.m1246of(baseFragment).createErrorBulletin(LocaleController.getString(C2888R.string.NoFolderFound)).show();
            }
        } else {
            BulletinFactory.m1246of(baseFragment).createErrorBulletin(LocaleController.getString(C2888R.string.NoFolderFound)).show();
        }
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$58(Runnable runnable, TL_payments.TL_StarGiftAuctionState tL_StarGiftAuctionState, TLRPC.TL_error tL_error) {
        GiftAuctionController.Auction auction;
        if (tL_error != null) {
            BulletinFactory.m1246of((BaseFragment) mainFragmentsStack.get(r9.size() - 1)).createSimpleBulletin(C2888R.raw.error, getString(C2888R.string.GiftAuctionNotFound)).show();
        } else if (tL_StarGiftAuctionState != null && (auction = GiftAuctionController.getInstance(this.currentAccount).getAuction(tL_StarGiftAuctionState.gift.f1846id)) != null) {
            new StarGiftPreviewSheet(this, null, this.currentAccount, auction.gift.title, auction.previewAttributes, false).show();
        }
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$59(Runnable runnable, TL_payments.TL_StarGiftAuctionState tL_StarGiftAuctionState, TLRPC.TL_error tL_error) {
        if (tL_error != null) {
            BulletinFactory.m1246of((BaseFragment) mainFragmentsStack.get(r11.size() - 1)).createSimpleBulletin(C2888R.raw.error, getString(C2888R.string.GiftAuctionNotFound)).show();
        } else if (tL_StarGiftAuctionState != null) {
            AuctionJoinSheet.show(this, (Theme.ResourcesProvider) null, this.currentAccount, 0L, tL_StarGiftAuctionState.gift.f1846id, (Runnable) null);
        }
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$61(final int i, final String str, final Runnable runnable, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda131
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$60(tL_error, tLObject, i, str, runnable);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$60(TLRPC.TL_error tL_error, TLObject tLObject, int i, String str, Runnable runnable) {
        if (tL_error != null) {
            BaseFragment safeLastFragment = getSafeLastFragment();
            if (safeLastFragment == null) {
                return;
            }
            if ("STARGIFT_ALREADY_BURNED".equalsIgnoreCase(tL_error.text)) {
                BulletinFactory.m1246of(safeLastFragment).createSimpleBulletin(C2888R.raw.fire_on, getString(C2888R.string.UniqueGiftNotFoundBurned)).show();
            } else {
                BulletinFactory.m1246of(safeLastFragment).createSimpleBulletin(C2888R.raw.error, getString(C2888R.string.UniqueGiftNotFound)).show();
            }
        } else if (tLObject instanceof TL_stars.TL_payments_uniqueStarGift) {
            TL_stars.TL_payments_uniqueStarGift tL_payments_uniqueStarGift = (TL_stars.TL_payments_uniqueStarGift) tLObject;
            MessagesController.getInstance(this.currentAccount).putUsers(tL_payments_uniqueStarGift.users, false);
            MessagesController.getInstance(this.currentAccount).putChats(tL_payments_uniqueStarGift.chats, false);
            BaseFragment safeLastFragment2 = getSafeLastFragment();
            TL_stars.StarGift starGift = tL_payments_uniqueStarGift.gift;
            if (starGift instanceof TL_stars.TL_starGiftUnique) {
                StarGiftSheet starGiftSheet = new StarGiftSheet(this, i, 0L, null).set(str, (TL_stars.TL_starGiftUnique) starGift, null);
                if (safeLastFragment2 != null) {
                    if (safeLastFragment2.getLastStoryViewer() != null && safeLastFragment2.getLastStoryViewer().isFullyVisible()) {
                        safeLastFragment2.getLastStoryViewer().showDialog(starGiftSheet);
                    } else {
                        safeLastFragment2.showDialog(starGiftSheet);
                    }
                } else {
                    starGiftSheet.show();
                }
            }
        }
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$66(final TLRPC.TL_inputInvoiceSlug tL_inputInvoiceSlug, final Runnable runnable, final int i, final String str, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda115
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$65(tL_error, tLObject, tL_inputInvoiceSlug, runnable, i, str);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$65(TLRPC.TL_error tL_error, TLObject tLObject, TLRPC.TL_inputInvoiceSlug tL_inputInvoiceSlug, final Runnable runnable, int i, String str) {
        PaymentFormActivity paymentFormActivity;
        if (tL_error != null) {
            if ("SUBSCRIPTION_ALREADY_ACTIVE".equalsIgnoreCase(tL_error.text)) {
                BulletinFactory.m1246of((BaseFragment) mainFragmentsStack.get(r7.size() - 1)).createErrorBulletin(LocaleController.getString(C2888R.string.PaymentInvoiceSubscriptionLinkAlreadyPaid)).show();
            } else {
                BulletinFactory.m1246of((BaseFragment) mainFragmentsStack.get(r7.size() - 1)).createErrorBulletin(LocaleController.getString(C2888R.string.PaymentInvoiceLinkInvalid)).show();
            }
        } else if (!isFinishing()) {
            if (tLObject instanceof TLRPC.TL_payments_paymentFormStars) {
                final Runnable runnable2 = this.navigateToPremiumGiftCallback;
                this.navigateToPremiumGiftCallback = null;
                StarsController.getInstance(this.currentAccount).openPaymentForm(null, tL_inputInvoiceSlug, (TLRPC.TL_payments_paymentFormStars) tLObject, new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda149
                    @Override // java.lang.Runnable
                    public final void run() {
                        LaunchActivity.$r8$lambda$s7bjtVsbNhBQrlUtIqSqjTMwpWM(runnable);
                    }
                }, new Utilities.Callback() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda150
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        LaunchActivity.$r8$lambda$wAFXU1FpdNZow1N26YesKaJ1gVc(runnable2, (String) obj);
                    }
                });
                return;
            }
            if (tLObject instanceof TLRPC.PaymentForm) {
                TLRPC.PaymentForm paymentForm = (TLRPC.PaymentForm) tLObject;
                MessagesController.getInstance(i).putUsers(paymentForm.users, false);
                paymentFormActivity = new PaymentFormActivity(paymentForm, str, getActionBarLayout().getLastFragment());
            } else {
                paymentFormActivity = tLObject instanceof TLRPC.PaymentReceipt ? new PaymentFormActivity((TLRPC.PaymentReceipt) tLObject) : null;
            }
            if (paymentFormActivity != null) {
                final Runnable runnable3 = this.navigateToPremiumGiftCallback;
                if (runnable3 != null) {
                    this.navigateToPremiumGiftCallback = null;
                    paymentFormActivity.setPaymentFormCallback(new PaymentFormActivity.PaymentFormCallback() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda151
                        @Override // org.telegram.ui.PaymentFormActivity.PaymentFormCallback
                        public final void onInvoiceStatusChanged(PaymentFormActivity.InvoiceStatus invoiceStatus) {
                            LaunchActivity.$r8$lambda$p6xVKlgGb0byIjCs2NXokmEuqm8(runnable3, invoiceStatus);
                        }
                    });
                }
                lambda$runLinkRequest$106(paymentFormActivity);
            }
        }
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    public static /* synthetic */ void $r8$lambda$s7bjtVsbNhBQrlUtIqSqjTMwpWM(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    public static /* synthetic */ void $r8$lambda$wAFXU1FpdNZow1N26YesKaJ1gVc(Runnable runnable, String str) {
        if (runnable == null || !"paid".equals(str)) {
            return;
        }
        runnable.run();
    }

    public static /* synthetic */ void $r8$lambda$p6xVKlgGb0byIjCs2NXokmEuqm8(Runnable runnable, PaymentFormActivity.InvoiceStatus invoiceStatus) {
        if (invoiceStatus == PaymentFormActivity.InvoiceStatus.PAID) {
            runnable.run();
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$88(final Runnable runnable, final boolean z, final int i, final int i2, final String str, final String str2, final boolean z2, final String str3, final String str4, final int i3, final String str5, final String str6, final String str7, final String str8, final String str9, final String str10, final String str11, final String str12, final String str13, final String str14, final String str15, final String str16, final boolean z3, final Integer num, final Long l, final Long l2, final Integer num2, final HashMap map, final String str17, final String str18, final String str19, final String str20, final TLRPC.TL_wallPaper tL_wallPaper, final String str21, final String str22, final String str23, final int i4, final int i5, final String str24, final String str25, final String str26, final Browser.Progress progress, final boolean z4, final int i6, final String str27, final String str28, final boolean z5, final String str29, final boolean z6, final boolean z7, final boolean z8, final boolean z9, final boolean z10, final String str30, final Integer num3, final boolean z11, final String str31, int[] iArr, final Long l3) {
        final LaunchActivity launchActivity;
        final Runnable runnable2;
        String str32;
        int i7;
        String str33;
        long j;
        String str34;
        String str35;
        long j2;
        long j3;
        boolean z12;
        String str36;
        long jLongValue;
        final TLRPC.User user;
        if (l3 != null && l3.longValue() == Long.MAX_VALUE) {
            try {
                runnable.run();
            } catch (Exception e) {
                FileLog.m1136e(e);
            }
            new AlertDialog.Builder(this, null).setTitle(LocaleController.getString(C2888R.string.AffiliateLinkExpiredTitle)).setMessage(LocaleController.getString(C2888R.string.AffiliateLinkExpiredText)).setNegativeButton(LocaleController.getString(C2888R.string.f1606OK), null).show();
            return;
        }
        if (isFinishing()) {
            return;
        }
        if (z && l3 != null) {
            MessagesController.getInstance(this.currentAccount).getStoriesController().resolveLiveStoryLink(l3.longValue(), new com.google.android.exoplayer2.util.Consumer() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda100
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$runLinkRequest$67(runnable, l3, (TL_stories.StoryItem) obj);
                }
            });
            return;
        }
        if (i != 0 && l3 != null) {
            MessagesController.getInstance(this.currentAccount).getStoriesController().resolveStoryLink(l3.longValue(), i, new com.google.android.exoplayer2.util.Consumer() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda101
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$runLinkRequest$68(runnable, l3, (TL_stories.StoryItem) obj);
                }
            });
            return;
        }
        if (i2 != 0 && l3 != null) {
            MessagesController.getInstance(this.currentAccount).getStoriesController().resolveStoryAlbumLink(l3.longValue(), i2, new com.google.android.exoplayer2.util.Consumer() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda102
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$runLinkRequest$69(runnable, l3, i2, (TL_stories.TL_storyAlbum) obj);
                }
            });
            return;
        }
        if (l3 != null && this.actionBarLayout != null && ((str == null && str2 == null) || ((str != null && l3.longValue() > 0) || ((str2 != null && l3.longValue() > 0) || ((z2 && l3.longValue() < 0) || (str3 != null && l3.longValue() < 0)))))) {
            if (!TextUtils.isEmpty(str4) && (user = MessagesController.getInstance(i3).getUser(l3)) != null && user.bot) {
                if (user.bot_attach_menu && !MediaDataController.getInstance(i3).botInAttachMenu(user.f1825id)) {
                    TLRPC.TL_messages_getAttachMenuBot tL_messages_getAttachMenuBot = new TLRPC.TL_messages_getAttachMenuBot();
                    tL_messages_getAttachMenuBot.bot = MessagesController.getInstance(i3).getInputUser(l3.longValue());
                    ConnectionsManager.getInstance(i3).sendRequest(tL_messages_getAttachMenuBot, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda103
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                            this.f$0.lambda$runLinkRequest$78(i3, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, str15, str16, z3, num, l, l2, num2, str, map, str17, str18, str19, str20, tL_wallPaper, str21, str22, str23, str2, z2, str3, i4, i5, str24, str25, str26, progress, z4, i, z, i2, i6, str27, str28, z5, str29, z6, z7, z8, z9, z10, str30, num3, z11, l3, str4, str31, user, runnable, tLObject, tL_error);
                        }
                    });
                    return;
                }
                processWebAppBot(i3, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, str15, str16, z3, num, l, l2, num2, str, map, str17, str18, str19, str20, tL_wallPaper, str21, str22, str23, str2, z2, str3, i4, i5, str24, str25, str26, str4, str31, progress, z4, i, false, i2, i6, z5, str29, user, runnable, false, false, z6, z7, z8, z9, z10, str30, z11);
                return;
            }
            if (z5 && ChatObject.isBoostSupported(MessagesController.getInstance(i3).getChat(Long.valueOf(-l3.longValue())))) {
                processBoostDialog(l3, runnable, progress);
                return;
            }
            if (str31 != null) {
                TLRPC.User user2 = MessagesController.getInstance(i3).getUser(l3);
                if (user2 != null && user2.bot) {
                    MessagesController.getInstance(i3).openApp(null, user2, str31, 0, progress, z6, z7);
                }
            } else if (str24 != null && str25 == null) {
                TLRPC.User user3 = MessagesController.getInstance(i3).getUser(l3);
                if (user3 != null && user3.bot) {
                    if (user3.bot_attach_menu) {
                        processAttachMenuBot(i3, l3.longValue(), str26, user3, str24, str31);
                    } else {
                        ArrayList arrayList = mainFragmentsStack;
                        BulletinFactory.m1246of((BaseFragment) arrayList.get(arrayList.size() - 1)).createErrorBulletin(LocaleController.getString(C2888R.string.BotCantAddToAttachMenu)).show();
                    }
                } else {
                    ArrayList arrayList2 = mainFragmentsStack;
                    BulletinFactory.m1246of((BaseFragment) arrayList2.get(arrayList2.size() - 1)).createErrorBulletin(LocaleController.getString(C2888R.string.BotSetAttachLinkNotBot)).show();
                }
            } else if (num == null || ((num2 == null && l2 == null) || l3.longValue() >= 0)) {
                launchActivity = this;
                if (str != null) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("onlySelect", true);
                    bundle.putBoolean("cantSendToChannels", true);
                    bundle.putInt("dialogsType", 1);
                    bundle.putString("selectAlertString", LocaleController.getString(C2888R.string.SendGameToText));
                    bundle.putString("selectAlertStringGroup", LocaleController.getString(C2888R.string.SendGameToGroupText));
                    DialogsActivity dialogsActivity = new DialogsActivity(bundle);
                    final TLRPC.User user4 = MessagesController.getInstance(i3).getUser(l3);
                    dialogsActivity.setDelegate(new DialogsActivity.DialogsActivityDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda104
                        @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
                        public /* synthetic */ boolean canSelectStories() {
                            return DialogsActivity.DialogsActivityDelegate.CC.$default$canSelectStories(this);
                        }

                        @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
                        public final boolean didSelectDialogs(DialogsActivity dialogsActivity2, ArrayList arrayList3, CharSequence charSequence, boolean z13, boolean z14, int i8, int i9, TopicsFragment topicsFragment) {
                            return this.f$0.lambda$runLinkRequest$79(str, i3, user4, dialogsActivity2, arrayList3, charSequence, z13, z14, i8, i9, topicsFragment);
                        }

                        @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
                        public /* synthetic */ boolean didSelectStories(DialogsActivity dialogsActivity2) {
                            return DialogsActivity.DialogsActivityDelegate.CC.$default$didSelectStories(this, dialogsActivity2);
                        }
                    });
                    launchActivity.getActionBarLayout().presentFragment(dialogsActivity, !AndroidUtilities.isTablet() ? launchActivity.actionBarLayout.getFragmentStack().size() <= 1 || !(launchActivity.actionBarLayout.getFragmentStack().get(launchActivity.actionBarLayout.getFragmentStack().size() - 1) instanceof MainTabsActivity) : launchActivity.layersActionBarLayout.getFragmentStack().isEmpty() || !(launchActivity.layersActionBarLayout.getFragmentStack().get(launchActivity.layersActionBarLayout.getFragmentStack().size() - 1) instanceof MainTabsActivity), true, true, false);
                    if (SecretMediaViewer.hasInstance() && SecretMediaViewer.getInstance().isVisible()) {
                        SecretMediaViewer.getInstance().closePhoto(false, false);
                    } else if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
                        PhotoViewer.getInstance().closePhoto(false, true);
                    } else if (ArticleViewer.hasInstance() && ArticleViewer.getInstance().isVisible()) {
                        ArticleViewer.getInstance().close(false, true);
                    }
                    StoryRecorder.destroyInstance();
                    GroupCallActivity groupCallActivity = GroupCallActivity.groupCallInstance;
                    if (groupCallActivity != null) {
                        groupCallActivity.dismiss();
                    }
                    if (AndroidUtilities.isTablet()) {
                        launchActivity.actionBarLayout.rebuildFragments(1);
                        launchActivity.rightActionBarLayout.rebuildFragments(1);
                    }
                } else if (str10 != null || str11 != null) {
                    final TLRPC.User user5 = MessagesController.getInstance(i3).getUser(l3);
                    if (user5 == null || (user5.bot && user5.bot_nochats)) {
                        try {
                            ArrayList arrayList3 = mainFragmentsStack;
                            if (arrayList3.isEmpty()) {
                                return;
                            }
                            BulletinFactory.m1246of((BaseFragment) arrayList3.get(arrayList3.size() - 1)).createErrorBulletin(LocaleController.getString(C2888R.string.BotCantJoinGroups)).show();
                            return;
                        } catch (Exception e2) {
                            FileLog.m1136e(e2);
                            return;
                        }
                    }
                    Bundle bundle2 = new Bundle();
                    bundle2.putBoolean("onlySelect", true);
                    bundle2.putInt("dialogsType", 2);
                    bundle2.putBoolean("resetDelegate", false);
                    bundle2.putBoolean("closeFragment", false);
                    bundle2.putBoolean("allowGroups", str10 != null);
                    bundle2.putBoolean("allowChannels", str11 != null);
                    String str37 = TextUtils.isEmpty(str10) ? TextUtils.isEmpty(str11) ? null : str11 : str10;
                    final DialogsActivity dialogsActivity2 = new DialogsActivity(bundle2);
                    final String str38 = str37;
                    dialogsActivity2.setDelegate(new DialogsActivity.DialogsActivityDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda105
                        @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
                        public /* synthetic */ boolean canSelectStories() {
                            return DialogsActivity.DialogsActivityDelegate.CC.$default$canSelectStories(this);
                        }

                        @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
                        public final boolean didSelectDialogs(DialogsActivity dialogsActivity3, ArrayList arrayList4, CharSequence charSequence, boolean z13, boolean z14, int i8, int i9, TopicsFragment topicsFragment) {
                            return this.f$0.lambda$runLinkRequest$84(i3, user5, str12, str38, dialogsActivity2, dialogsActivity3, arrayList4, charSequence, z13, z14, i8, i9, topicsFragment);
                        }

                        @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
                        public /* synthetic */ boolean didSelectStories(DialogsActivity dialogsActivity3) {
                            return DialogsActivity.DialogsActivityDelegate.CC.$default$didSelectStories(this, dialogsActivity3);
                        }
                    });
                    launchActivity.lambda$runLinkRequest$106(dialogsActivity2);
                } else {
                    Bundle bundle3 = new Bundle();
                    TLRPC.User user6 = MessagesController.getInstance(i3).getUser(l3);
                    if (l3.longValue() < 0) {
                        final TLRPC.Chat chat = MessagesController.getInstance(i3).getChat(Long.valueOf(-l3.longValue()));
                        if (z11 && chat.linked_monoforum_id != 0) {
                            j = 0;
                            if (MessagesController.getInstance(i3).getChat(Long.valueOf(chat.linked_monoforum_id)) == null) {
                                TLRPC.TL_channels_getFullChannel tL_channels_getFullChannel = new TLRPC.TL_channels_getFullChannel();
                                tL_channels_getFullChannel.channel = MessagesController.getInputChannel(chat);
                                ConnectionsManager.getInstance(i3).sendRequest(tL_channels_getFullChannel, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda106
                                    @Override // org.telegram.tgnet.RequestDelegate
                                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                                        this.f$0.lambda$runLinkRequest$86(i3, chat, runnable, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, str15, str16, z3, num, l, l2, num2, str, map, str17, str18, str19, str20, tL_wallPaper, str21, str22, str23, str2, z2, str3, i4, i5, str24, str25, str26, str4, str31, progress, z4, i, z, i2, i6, str27, str28, z5, str29, z6, z7, z8, z9, z10, str30, num3, z11, tLObject, tL_error);
                                    }
                                });
                                return;
                            }
                            runnable2 = runnable;
                            str32 = str2;
                            str34 = str3;
                            str35 = str9;
                            i7 = i5;
                            str33 = str24;
                            bundle3.putLong("chat_id", chat.linked_monoforum_id);
                            jLongValue = -chat.linked_monoforum_id;
                        } else {
                            runnable2 = runnable;
                            str32 = str2;
                            i7 = i5;
                            str33 = str24;
                            j = 0;
                            str34 = str3;
                            str35 = str9;
                            bundle3.putLong("chat_id", -l3.longValue());
                            jLongValue = l3.longValue();
                        }
                        j3 = jLongValue;
                    } else {
                        runnable2 = runnable;
                        str32 = str2;
                        String string = str16;
                        i7 = i5;
                        str33 = str24;
                        j = 0;
                        str34 = str3;
                        str35 = str9;
                        bundle3.putLong("user_id", l3.longValue());
                        long jLongValue2 = l3.longValue();
                        if (string != null) {
                            if (string.startsWith("@")) {
                                StringBuilder sb = new StringBuilder();
                                j2 = jLongValue2;
                                sb.append(" ");
                                sb.append(string);
                                string = sb.toString();
                            } else {
                                j2 = jLongValue2;
                            }
                            bundle3.putString("start_text", string);
                        } else {
                            j2 = jLongValue2;
                        }
                        j3 = j2;
                    }
                    if (str35 == null || user6 == null || !user6.bot) {
                        z12 = false;
                    } else {
                        bundle3.putString("botUser", str35);
                        z12 = true;
                    }
                    if (launchActivity.navigateToPremiumBot) {
                        launchActivity.navigateToPremiumBot = false;
                        bundle3.putBoolean("premium_bot", true);
                    }
                    if (num != null) {
                        bundle3.putInt("message_id", num.intValue());
                        if (num3 != null) {
                            bundle3.putInt("task_id", num3.intValue());
                        }
                    }
                    if (str32 != null) {
                        bundle3.putString("voicechat", str32);
                    }
                    boolean z13 = z12;
                    if (z2) {
                        bundle3.putBoolean("videochat", true);
                    }
                    if (str34 != null) {
                        bundle3.putString("livestream", str34);
                    }
                    if (i7 >= 0) {
                        bundle3.putInt("video_timestamp", i7);
                    }
                    if (str25 != null) {
                        str36 = str25;
                        bundle3.putString("attach_bot", str36);
                    } else {
                        str36 = str25;
                    }
                    if (str33 != null) {
                        bundle3.putString("attach_bot_start_command", str33);
                    }
                    ArrayList arrayList4 = mainFragmentsStack;
                    BaseFragment baseFragment = (arrayList4.isEmpty() || str32 != null) ? null : (BaseFragment) arrayList4.get(arrayList4.size() - 1);
                    if (baseFragment == null || MessagesController.getInstance(i3).checkCanOpenChat(bundle3, baseFragment)) {
                        boolean z14 = (baseFragment instanceof ChatActivity) && ((ChatActivity) baseFragment).getDialogId() == j3;
                        if (z13 && z14) {
                            ((ChatActivity) baseFragment).setBotUser(str35);
                        } else if (str36 != null && z14) {
                            ((ChatActivity) baseFragment).openAttachBotLayout(str36);
                        } else {
                            long j4 = -j3;
                            long j5 = j3;
                            TLRPC.Chat chat2 = MessagesController.getInstance(launchActivity.currentAccount).getChat(Long.valueOf(j4));
                            if (z9 || i6 > 0) {
                                try {
                                    runnable.run();
                                } catch (Exception e3) {
                                    FileLog.m1136e(e3);
                                }
                                if (launchActivity.isFinishing()) {
                                    return;
                                }
                                Bundle bundle4 = new Bundle();
                                if (l3.longValue() < j) {
                                    bundle4.putLong("chat_id", -l3.longValue());
                                } else {
                                    bundle4.putLong("user_id", l3.longValue());
                                }
                                if (i6 > 0) {
                                    bundle4.putBoolean("open_gifts", true);
                                    bundle4.putInt("open_gifts_collection", i6);
                                    if (l3.longValue() == UserConfig.getInstance(launchActivity.currentAccount).getClientUserId()) {
                                        bundle4.putBoolean("my_profile", true);
                                    }
                                }
                                launchActivity.getActionBarLayout().presentFragment(new ProfileActivity(bundle4));
                                return;
                            }
                            if (chat2 != null && chat2.forum) {
                                Long lValueOf = (l2 != null || num == null) ? l2 : Long.valueOf(num.intValue());
                                if (lValueOf != null && lValueOf.longValue() != j) {
                                    launchActivity.openForumFromLink(j5, num, null, num3, new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda107
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            LaunchActivity.$r8$lambda$CZU4SE_HC4AhsHQ3cjN4HEkg0fs(runnable2);
                                        }
                                    }, 0, -1);
                                    return;
                                }
                                Bundle bundle5 = new Bundle();
                                bundle5.putLong("chat_id", j4);
                                if (str32 != null) {
                                    bundle5.putString("voicechat", str32);
                                }
                                if (z2) {
                                    bundle5.putBoolean("videochat", true);
                                }
                                launchActivity.lambda$runLinkRequest$106(TopicsFragment.getTopicsOrChat(launchActivity, bundle5));
                                try {
                                    runnable2.run();
                                    return;
                                } catch (Exception e4) {
                                    FileLog.m1136e(e4);
                                    return;
                                }
                            }
                            MessagesController.getInstance(i3).ensureMessagesLoaded(j5, num == null ? 0 : num.intValue(), launchActivity.new C590116(runnable2, str34, baseFragment, j5, num, bundle3));
                            return;
                        }
                    }
                }
            } else {
                int iRunCommentRequest = runCommentRequest(i3, runnable, num, num2, l2, null, MessagesController.getInstance(i3).getChat(Long.valueOf(-l3.longValue())));
                launchActivity = this;
                iArr[0] = iRunCommentRequest;
                if (iRunCommentRequest != 0) {
                    return;
                }
            }
        } else {
            try {
                BaseFragment lastFragment = getLastFragment();
                if (lastFragment != null) {
                    if (lastFragment instanceof ChatActivity) {
                        ((ChatActivity) lastFragment).shakeContent();
                    }
                    if (AndroidUtilities.isNumeric(str5)) {
                        BulletinFactory.m1246of(lastFragment).createErrorBulletin(LocaleController.getString(C2888R.string.NoPhoneFound)).show();
                    } else {
                        BulletinFactory.m1246of(lastFragment).createErrorBulletin(LocaleController.getString(C2888R.string.NoUsernameFound)).show();
                    }
                }
            } catch (Exception e5) {
                FileLog.m1136e(e5);
            }
        }
        try {
            runnable.run();
        } catch (Exception e6) {
            FileLog.m1136e(e6);
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$67(Runnable runnable, Long l, TL_stories.StoryItem storyItem) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
        BaseFragment lastFragment = getLastFragment();
        if (storyItem == null) {
            BulletinFactory bulletinFactoryGlobal = BulletinFactory.global();
            if (bulletinFactoryGlobal != null) {
                bulletinFactoryGlobal.createSimpleBulletin(C2888R.raw.story_bomb2, LocaleController.getString(C2888R.string.StoryNotFound)).show();
                return;
            }
            return;
        }
        if (storyItem instanceof TL_stories.TL_storyItemDeleted) {
            BulletinFactory bulletinFactoryGlobal2 = BulletinFactory.global();
            if (bulletinFactoryGlobal2 != null) {
                bulletinFactoryGlobal2.createSimpleBulletin(C2888R.raw.story_bomb1, LocaleController.getString(C2888R.string.StoryNotFound)).show();
                return;
            }
            return;
        }
        if (lastFragment != null) {
            storyItem.dialogId = l.longValue();
            StoryViewer storyViewerCreateOverlayStoryViewer = lastFragment.createOverlayStoryViewer();
            storyViewerCreateOverlayStoryViewer.instantClose();
            storyViewerCreateOverlayStoryViewer.open(this, storyItem, (StoryViewer.PlaceProvider) null);
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$68(Runnable runnable, Long l, TL_stories.StoryItem storyItem) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
        BaseFragment lastFragment = getLastFragment();
        if (storyItem == null) {
            BulletinFactory bulletinFactoryGlobal = BulletinFactory.global();
            if (bulletinFactoryGlobal != null) {
                bulletinFactoryGlobal.createSimpleBulletin(C2888R.raw.story_bomb2, LocaleController.getString(C2888R.string.StoryNotFound)).show();
                return;
            }
            return;
        }
        if (storyItem instanceof TL_stories.TL_storyItemDeleted) {
            BulletinFactory bulletinFactoryGlobal2 = BulletinFactory.global();
            if (bulletinFactoryGlobal2 != null) {
                bulletinFactoryGlobal2.createSimpleBulletin(C2888R.raw.story_bomb1, LocaleController.getString(C2888R.string.StoryNotFound)).show();
                return;
            }
            return;
        }
        if (lastFragment != null) {
            storyItem.dialogId = l.longValue();
            StoryViewer storyViewerCreateOverlayStoryViewer = lastFragment.createOverlayStoryViewer();
            storyViewerCreateOverlayStoryViewer.instantClose();
            storyViewerCreateOverlayStoryViewer.open(this, storyItem, (StoryViewer.PlaceProvider) null);
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$69(Runnable runnable, Long l, int i, TL_stories.TL_storyAlbum tL_storyAlbum) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
        getLastFragment();
        if (tL_storyAlbum == null) {
            BulletinFactory bulletinFactoryGlobal = BulletinFactory.global();
            if (bulletinFactoryGlobal != null) {
                bulletinFactoryGlobal.createSimpleBulletin(C2888R.raw.story_bomb2, LocaleController.getString(C2888R.string.StoryAlbumNotFound)).show();
                return;
            }
            return;
        }
        Bundle bundle = new Bundle();
        if (l.longValue() > 0) {
            bundle.putLong("user_id", l.longValue());
            bundle.putBoolean("my_profile", l.longValue() == UserConfig.getInstance(this.currentAccount).getClientUserId());
        } else {
            bundle.putLong("chat_id", -l.longValue());
        }
        bundle.putInt("open_story_album_id", i);
        lambda$runLinkRequest$106(new ProfileActivity(bundle));
    }

    public /* synthetic */ void lambda$runLinkRequest$78(final int i, final String str, final String str2, final String str3, final String str4, final String str5, final String str6, final String str7, final String str8, final String str9, final String str10, final String str11, final String str12, final boolean z, final Integer num, final Long l, final Long l2, final Integer num2, final String str13, final HashMap map, final String str14, final String str15, final String str16, final String str17, final TLRPC.TL_wallPaper tL_wallPaper, final String str18, final String str19, final String str20, final String str21, final boolean z2, final String str22, final int i2, final int i3, final String str23, final String str24, final String str25, final Browser.Progress progress, final boolean z3, final int i4, final boolean z4, final int i5, final int i6, final String str26, final String str27, final boolean z5, final String str28, final boolean z6, final boolean z7, final boolean z8, final boolean z9, final boolean z10, final String str29, final Integer num3, final boolean z11, final Long l3, final String str30, final String str31, final TLRPC.User user, final Runnable runnable, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda138
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$77(tL_error, i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, num, l, l2, num2, str13, map, str14, str15, str16, str17, tL_wallPaper, str18, str19, str20, str21, z2, str22, i2, i3, str23, str24, str25, progress, z3, i4, z4, i5, i6, str26, str27, z5, str28, z6, z7, z8, z9, z10, str29, num3, z11, tLObject, l3, str30, str31, user, runnable);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$70(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, boolean z, Integer num, Long l, Long l2, Integer num2, String str13, HashMap map, String str14, String str15, String str16, String str17, TLRPC.TL_wallPaper tL_wallPaper, String str18, String str19, String str20, String str21, boolean z2, String str22, int i2, int i3, String str23, String str24, String str25, Browser.Progress progress, boolean z3, int i4, boolean z4, int i5, int i6, String str26, String str27, boolean z5, String str28, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, String str29, Integer num3, boolean z11) {
        runLinkRequest(i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, num, l, l2, num2, str13, map, str14, str15, str16, str17, tL_wallPaper, str18, str19, str20, str21, z2, str22, i2, i3, str23, str24, str25, null, null, progress, z3, i4, z4, i5, i6, str26, str27, z5, str28, z6, z7, z8, z9, z10, str29, num3, z11);
    }

    public /* synthetic */ void lambda$runLinkRequest$77(TLRPC.TL_error tL_error, final int i, final String str, final String str2, final String str3, final String str4, final String str5, final String str6, final String str7, final String str8, final String str9, final String str10, final String str11, final String str12, final boolean z, final Integer num, final Long l, final Long l2, final Integer num2, final String str13, final HashMap map, final String str14, final String str15, final String str16, final String str17, final TLRPC.TL_wallPaper tL_wallPaper, final String str18, final String str19, final String str20, final String str21, final boolean z2, final String str22, final int i2, final int i3, final String str23, final String str24, final String str25, final Browser.Progress progress, final boolean z3, final int i4, final boolean z4, final int i5, final int i6, final String str26, final String str27, final boolean z5, final String str28, final boolean z6, final boolean z7, final boolean z8, final boolean z9, final boolean z10, final String str29, final Integer num3, final boolean z11, TLObject tLObject, final Long l3, final String str30, final String str31, final TLRPC.User user, final Runnable runnable) {
        if (tL_error != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda158
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$runLinkRequest$70(i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, num, l, l2, num2, str13, map, str14, str15, str16, str17, tL_wallPaper, str18, str19, str20, str21, z2, str22, i2, i3, str23, str24, str25, progress, z3, i4, z4, i5, i6, str26, str27, z5, str28, z6, z7, z8, z9, z10, str29, num3, z11);
                }
            });
            return;
        }
        if (tLObject instanceof TLRPC.TL_attachMenuBotsBot) {
            final TLRPC.TL_attachMenuBot tL_attachMenuBot = ((TLRPC.TL_attachMenuBotsBot) tLObject).bot;
            final boolean z12 = tL_attachMenuBot != null && (tL_attachMenuBot.show_in_side_menu || tL_attachMenuBot.show_in_attach_menu);
            if ((tL_attachMenuBot.inactive || tL_attachMenuBot.side_menu_disclaimer_needed) && z12) {
                WebAppDisclaimerAlert.show(this, new com.google.android.exoplayer2.util.Consumer() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda159
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        this.f$0.lambda$runLinkRequest$73(tL_attachMenuBot, i, l3, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, num, l, l2, num2, str13, map, str14, str15, str16, str17, tL_wallPaper, str18, str19, str20, str21, z2, str22, i2, i3, str23, str24, str25, str30, str31, progress, z3, i4, i5, i6, z5, str28, user, runnable, z12, z6, z7, z8, z9, z10, str29, z11, (Boolean) obj);
                    }
                }, null, progress != null ? new ChatActivity$ChatMessageCellDelegate$$ExternalSyntheticLambda13(progress) : null);
            } else if (tL_attachMenuBot.request_write_access || z3) {
                final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
                AlertsCreator.createBotLaunchAlert(getLastFragment(), atomicBoolean, user, new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda160
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$runLinkRequest$76(l3, tL_attachMenuBot, atomicBoolean, i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, num, l, l2, num2, str13, map, str14, str15, str16, str17, tL_wallPaper, str18, str19, str20, str21, z2, str22, i2, i3, str23, str24, str25, str30, str31, progress, z3, i4, i5, i6, z5, str28, user, runnable, z6, z7, z8, z9, z10, str29, z11);
                    }
                });
            } else {
                processWebAppBot(i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, num, l, l2, num2, str13, map, str14, str15, str16, str17, tL_wallPaper, str18, str19, str20, str21, z2, str22, i2, i3, str23, str24, str25, str30, str31, progress, z3, i4, false, i5, i6, z5, str28, user, runnable, false, false, z6, z7, z8, z9, z10, str29, z11);
            }
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$73(TLRPC.TL_attachMenuBot tL_attachMenuBot, final int i, Long l, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, boolean z, Integer num, Long l2, Long l3, Integer num2, String str13, HashMap map, String str14, String str15, String str16, String str17, TLRPC.TL_wallPaper tL_wallPaper, String str18, String str19, String str20, String str21, boolean z2, String str22, int i2, int i3, String str23, String str24, String str25, String str26, String str27, Browser.Progress progress, boolean z3, int i4, int i5, int i6, boolean z4, String str28, TLRPC.User user, Runnable runnable, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, String str29, boolean z11, Boolean bool) {
        tL_attachMenuBot.inactive = false;
        tL_attachMenuBot.request_write_access = false;
        TLRPC.TL_messages_toggleBotInAttachMenu tL_messages_toggleBotInAttachMenu = new TLRPC.TL_messages_toggleBotInAttachMenu();
        tL_messages_toggleBotInAttachMenu.bot = MessagesController.getInstance(i).getInputUser(l.longValue());
        tL_messages_toggleBotInAttachMenu.enabled = true;
        tL_messages_toggleBotInAttachMenu.write_allowed = true;
        ConnectionsManager.getInstance(i).sendRequest(tL_messages_toggleBotInAttachMenu, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda177
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda181
                    @Override // java.lang.Runnable
                    public final void run() {
                        LaunchActivity.$r8$lambda$afYWJS1ngjyYkjBK3c0T_kJb1Ws(tLObject, i);
                    }
                });
            }
        }, 66);
        processWebAppBot(i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, num, l2, l3, num2, str13, map, str14, str15, str16, str17, tL_wallPaper, str18, str19, str20, str21, z2, str22, i2, i3, str23, str24, str25, str26, str27, progress, z3, i4, false, i5, i6, z4, str28, user, runnable, z5, true, z6, z7, z8, z9, z10, str29, z11);
    }

    public static /* synthetic */ void $r8$lambda$afYWJS1ngjyYkjBK3c0T_kJb1Ws(TLObject tLObject, int i) {
        if (tLObject instanceof TLRPC.TL_boolTrue) {
            MediaDataController.getInstance(i).loadAttachMenuBots(false, true, null);
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$76(Long l, TLRPC.TL_attachMenuBot tL_attachMenuBot, AtomicBoolean atomicBoolean, final int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, boolean z, Integer num, Long l2, Long l3, Integer num2, String str13, HashMap map, String str14, String str15, String str16, String str17, TLRPC.TL_wallPaper tL_wallPaper, String str18, String str19, String str20, String str21, boolean z2, String str22, int i2, int i3, String str23, String str24, String str25, String str26, String str27, Browser.Progress progress, boolean z3, int i4, int i5, int i6, boolean z4, String str28, TLRPC.User user, Runnable runnable, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, String str29, boolean z10) {
        SharedPrefsHelper.setWebViewConfirmShown(this.currentAccount, l.longValue(), true);
        tL_attachMenuBot.inactive = false;
        tL_attachMenuBot.request_write_access = !atomicBoolean.get();
        TLRPC.TL_messages_toggleBotInAttachMenu tL_messages_toggleBotInAttachMenu = new TLRPC.TL_messages_toggleBotInAttachMenu();
        tL_messages_toggleBotInAttachMenu.bot = MessagesController.getInstance(i).getInputUser(l.longValue());
        tL_messages_toggleBotInAttachMenu.write_allowed = atomicBoolean.get();
        ConnectionsManager.getInstance(i).sendRequest(tL_messages_toggleBotInAttachMenu, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda174
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda179
                    @Override // java.lang.Runnable
                    public final void run() {
                        LaunchActivity.$r8$lambda$Hs3X9XlNac0ajox0z995kXJZoJk(tLObject, i);
                    }
                });
            }
        }, 66);
        processWebAppBot(i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, num, l2, l3, num2, str13, map, str14, str15, str16, str17, tL_wallPaper, str18, str19, str20, str21, z2, str22, i2, i3, str23, str24, str25, str26, str27, progress, z3, i4, false, i5, i6, z4, str28, user, runnable, false, false, z5, z6, z7, z8, z9, str29, z10);
    }

    public static /* synthetic */ void $r8$lambda$Hs3X9XlNac0ajox0z995kXJZoJk(TLObject tLObject, int i) {
        if (tLObject instanceof TLRPC.TL_boolTrue) {
            MediaDataController.getInstance(i).loadAttachMenuBots(false, true, null);
        }
    }

    public /* synthetic */ boolean lambda$runLinkRequest$79(String str, int i, TLRPC.User user, DialogsActivity dialogsActivity, ArrayList arrayList, CharSequence charSequence, boolean z, boolean z2, int i2, int i3, TopicsFragment topicsFragment) {
        long j = ((MessagesStorage.TopicKey) arrayList.get(0)).dialogId;
        TLRPC.TL_inputMediaGame tL_inputMediaGame = new TLRPC.TL_inputMediaGame();
        TLRPC.TL_inputGameShortName tL_inputGameShortName = new TLRPC.TL_inputGameShortName();
        tL_inputMediaGame.f1732id = tL_inputGameShortName;
        tL_inputGameShortName.short_name = str;
        tL_inputGameShortName.bot_id = MessagesController.getInstance(i).getInputUser(user);
        SendMessagesHelper.getInstance(i).sendGame(MessagesController.getInstance(i).getInputPeer(j), tL_inputMediaGame, 0L, 0L);
        Bundle bundle = new Bundle();
        bundle.putBoolean("scrollToTopOnResume", true);
        if (DialogObject.isEncryptedDialog(j)) {
            bundle.putInt("enc_id", DialogObject.getEncryptedChatId(j));
        } else if (DialogObject.isUserDialog(j)) {
            bundle.putLong("user_id", j);
        } else {
            bundle.putLong("chat_id", -j);
        }
        if (MessagesController.getInstance(i).checkCanOpenChat(bundle, dialogsActivity)) {
            NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
            getActionBarLayout().presentFragment(new ChatActivity(bundle), true, false, true, false);
        }
        return true;
    }

    public /* synthetic */ boolean lambda$runLinkRequest$84(final int i, final TLRPC.User user, final String str, final String str2, final DialogsActivity dialogsActivity, DialogsActivity dialogsActivity2, ArrayList arrayList, CharSequence charSequence, boolean z, boolean z2, int i2, int i3, TopicsFragment topicsFragment) {
        TLRPC.TL_chatAdminRights tL_chatAdminRights;
        final long j = ((MessagesStorage.TopicKey) arrayList.get(0)).dialogId;
        final TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-j));
        if (chat != null && (chat.creator || ((tL_chatAdminRights = chat.admin_rights) != null && tL_chatAdminRights.add_admins))) {
            MessagesController.getInstance(i).checkIsInChat(false, chat, user, new MessagesController.IsInChatCheckedCallback() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda135
                @Override // org.telegram.messenger.MessagesController.IsInChatCheckedCallback
                public final void run(boolean z3, TLRPC.TL_chatAdminRights tL_chatAdminRights2, String str3) {
                    this.f$0.lambda$runLinkRequest$82(str, str2, i, chat, dialogsActivity, user, j, z3, tL_chatAdminRights2, str3);
                }
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(LocaleController.getString(C2888R.string.AddBot));
            builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("AddMembersAlertNamesText", C2888R.string.AddMembersAlertNamesText, UserObject.getUserName(user), chat == null ? _UrlKt.FRAGMENT_ENCODE_SET : chat.title)));
            builder.setNegativeButton(LocaleController.getString(C2888R.string.Cancel), null);
            builder.setPositiveButton(LocaleController.getString(C2888R.string.AddBot), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda136
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i4) {
                    this.f$0.lambda$runLinkRequest$83(j, i, user, str2, alertDialog, i4);
                }
            });
            builder.show();
        }
        return true;
    }

    public /* synthetic */ void lambda$runLinkRequest$82(final String str, final String str2, final int i, final TLRPC.Chat chat, final DialogsActivity dialogsActivity, final TLRPC.User user, final long j, final boolean z, final TLRPC.TL_chatAdminRights tL_chatAdminRights, final String str3) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda163
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$81(str, tL_chatAdminRights, z, str2, i, chat, dialogsActivity, user, j, str3);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$81(String str, TLRPC.TL_chatAdminRights tL_chatAdminRights, boolean z, String str2, final int i, final TLRPC.Chat chat, final DialogsActivity dialogsActivity, TLRPC.User user, long j, String str3) {
        TLRPC.TL_chatAdminRights tL_chatAdminRights2;
        TLRPC.TL_chatAdminRights tL_chatAdminRights3;
        if (str != null) {
            String[] strArrSplit = str.split("[+ ]");
            tL_chatAdminRights2 = new TLRPC.TL_chatAdminRights();
            for (String str4 : strArrSplit) {
                str4.getClass();
                switch (str4) {
                    case "ban_users":
                    case "restrict_members":
                        tL_chatAdminRights2.ban_users = true;
                        break;
                    case "anonymous":
                        tL_chatAdminRights2.anonymous = true;
                        break;
                    case "change_info":
                        tL_chatAdminRights2.change_info = true;
                        break;
                    case "delete_messages":
                        tL_chatAdminRights2.delete_messages = true;
                        break;
                    case "edit_messages":
                        tL_chatAdminRights2.edit_messages = true;
                        break;
                    case "manage_call":
                    case "manage_video_chats":
                        tL_chatAdminRights2.manage_call = true;
                        break;
                    case "manage_chat":
                    case "other":
                        tL_chatAdminRights2.other = true;
                        break;
                    case "manage_topics":
                    case "manage_topic":
                        tL_chatAdminRights2.manage_topics = true;
                        break;
                    case "promote_members":
                    case "add_admins":
                        tL_chatAdminRights2.add_admins = true;
                        break;
                    case "invite_users":
                        tL_chatAdminRights2.invite_users = true;
                        break;
                    case "post_messages":
                        tL_chatAdminRights2.post_messages = true;
                        break;
                    case "pin_messages":
                        tL_chatAdminRights2.pin_messages = true;
                        break;
                }
            }
        } else {
            tL_chatAdminRights2 = null;
        }
        if (tL_chatAdminRights2 == null && tL_chatAdminRights == null) {
            tL_chatAdminRights3 = null;
        } else if (tL_chatAdminRights2 == null) {
            tL_chatAdminRights3 = tL_chatAdminRights;
        } else if (tL_chatAdminRights == null) {
            tL_chatAdminRights3 = tL_chatAdminRights2;
        } else {
            tL_chatAdminRights.change_info = tL_chatAdminRights2.change_info || tL_chatAdminRights.change_info;
            tL_chatAdminRights.post_messages = tL_chatAdminRights2.post_messages || tL_chatAdminRights.post_messages;
            tL_chatAdminRights.edit_messages = tL_chatAdminRights2.edit_messages || tL_chatAdminRights.edit_messages;
            tL_chatAdminRights.add_admins = tL_chatAdminRights2.add_admins || tL_chatAdminRights.add_admins;
            tL_chatAdminRights.delete_messages = tL_chatAdminRights2.delete_messages || tL_chatAdminRights.delete_messages;
            tL_chatAdminRights.ban_users = tL_chatAdminRights2.ban_users || tL_chatAdminRights.ban_users;
            tL_chatAdminRights.invite_users = tL_chatAdminRights2.invite_users || tL_chatAdminRights.invite_users;
            tL_chatAdminRights.pin_messages = tL_chatAdminRights2.pin_messages || tL_chatAdminRights.pin_messages;
            tL_chatAdminRights.manage_call = tL_chatAdminRights2.manage_call || tL_chatAdminRights.manage_call;
            tL_chatAdminRights.anonymous = tL_chatAdminRights2.anonymous || tL_chatAdminRights.anonymous;
            tL_chatAdminRights.other = tL_chatAdminRights2.other || tL_chatAdminRights.other;
            tL_chatAdminRights3 = tL_chatAdminRights;
        }
        if (z && tL_chatAdminRights2 == null && !TextUtils.isEmpty(str2)) {
            MessagesController.getInstance(this.currentAccount).addUserToChat(chat.f1660id, user, 0, str2, dialogsActivity, true, new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda178
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$runLinkRequest$80(i, chat, dialogsActivity);
                }
            }, null);
            return;
        }
        ChatRightsEditActivity chatRightsEditActivity = new ChatRightsEditActivity(user.f1825id, -j, tL_chatAdminRights3, null, null, str3, 2, true, !z, str2);
        chatRightsEditActivity.setDelegate(new ChatRightsEditActivity.ChatRightsEditActivityDelegate() { // from class: org.telegram.ui.LaunchActivity.15
            final /* synthetic */ DialogsActivity val$fragment;
            final /* synthetic */ int val$intentAccount;

            @Override // org.telegram.ui.ChatRightsEditActivity.ChatRightsEditActivityDelegate
            public void didChangeOwner(TLRPC.User user2) {
            }

            C590015(final DialogsActivity dialogsActivity2, final int i2) {
                dialogsActivity = dialogsActivity2;
                i = i2;
            }

            @Override // org.telegram.ui.ChatRightsEditActivity.ChatRightsEditActivityDelegate
            public void didSetRights(int i2, TLRPC.TL_chatAdminRights tL_chatAdminRights4, TLRPC.TL_chatBannedRights tL_chatBannedRights, String str5) {
                dialogsActivity.removeSelfFromStack();
                NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
            }
        });
        getActionBarLayout().presentFragment(chatRightsEditActivity, false);
    }

    public /* synthetic */ void lambda$runLinkRequest$80(int i, TLRPC.Chat chat, DialogsActivity dialogsActivity) {
        NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
        Bundle bundle = new Bundle();
        bundle.putBoolean("scrollToTopOnResume", true);
        bundle.putLong("chat_id", chat.f1660id);
        if (MessagesController.getInstance(this.currentAccount).checkCanOpenChat(bundle, dialogsActivity)) {
            presentFragment(new ChatActivity(bundle), true, false);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$15 */
    /* JADX INFO: loaded from: classes6.dex */
    class C590015 implements ChatRightsEditActivity.ChatRightsEditActivityDelegate {
        final /* synthetic */ DialogsActivity val$fragment;
        final /* synthetic */ int val$intentAccount;

        @Override // org.telegram.ui.ChatRightsEditActivity.ChatRightsEditActivityDelegate
        public void didChangeOwner(TLRPC.User user2) {
        }

        C590015(final DialogsActivity dialogsActivity2, final int i2) {
            dialogsActivity = dialogsActivity2;
            i = i2;
        }

        @Override // org.telegram.ui.ChatRightsEditActivity.ChatRightsEditActivityDelegate
        public void didSetRights(int i2, TLRPC.TL_chatAdminRights tL_chatAdminRights4, TLRPC.TL_chatBannedRights tL_chatBannedRights, String str5) {
            dialogsActivity.removeSelfFromStack();
            NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$83(long j, int i, TLRPC.User user, String str, AlertDialog alertDialog, int i2) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("scrollToTopOnResume", true);
        long j2 = -j;
        bundle.putLong("chat_id", j2);
        ChatActivity chatActivity = new ChatActivity(bundle);
        NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
        MessagesController.getInstance(i).addUserToChat(j2, user, 0, str, chatActivity, null);
        getActionBarLayout().presentFragment(chatActivity, true, false, true, false);
    }

    public /* synthetic */ void lambda$runLinkRequest$86(final int i, final TLRPC.Chat chat, final Runnable runnable, final String str, final String str2, final String str3, final String str4, final String str5, final String str6, final String str7, final String str8, final String str9, final String str10, final String str11, final String str12, final boolean z, final Integer num, final Long l, final Long l2, final Integer num2, final String str13, final HashMap map, final String str14, final String str15, final String str16, final String str17, final TLRPC.TL_wallPaper tL_wallPaper, final String str18, final String str19, final String str20, final String str21, final boolean z2, final String str22, final int i2, final int i3, final String str23, final String str24, final String str25, final String str26, final String str27, final Browser.Progress progress, final boolean z3, final int i4, final boolean z4, final int i5, final int i6, final String str28, final String str29, final boolean z5, final String str30, final boolean z6, final boolean z7, final boolean z8, final boolean z9, final boolean z10, final String str31, final Integer num3, final boolean z11, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda137
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$85(tLObject, i, chat, runnable, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, num, l, l2, num2, str13, map, str14, str15, str16, str17, tL_wallPaper, str18, str19, str20, str21, z2, str22, i2, i3, str23, str24, str25, str26, str27, progress, z3, i4, z4, i5, i6, str28, str29, z5, str30, z6, z7, z8, z9, z10, str31, num3, z11);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$85(TLObject tLObject, int i, TLRPC.Chat chat, Runnable runnable, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, boolean z, Integer num, Long l, Long l2, Integer num2, String str13, HashMap map, String str14, String str15, String str16, String str17, TLRPC.TL_wallPaper tL_wallPaper, String str18, String str19, String str20, String str21, boolean z2, String str22, int i2, int i3, String str23, String str24, String str25, String str26, String str27, Browser.Progress progress, boolean z3, int i4, boolean z4, int i5, int i6, String str28, String str29, boolean z5, String str30, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, String str31, Integer num3, boolean z11) {
        if (tLObject instanceof TLRPC.TL_messages_chatFull) {
            TLRPC.TL_messages_chatFull tL_messages_chatFull = (TLRPC.TL_messages_chatFull) tLObject;
            MessagesController.getInstance(i).putUsers(tL_messages_chatFull.users, false);
            MessagesController.getInstance(i).putChats(tL_messages_chatFull.chats, false);
            MessagesStorage.getInstance(i).putUsersAndChats(tL_messages_chatFull.users, tL_messages_chatFull.chats, false, true);
            if (MessagesController.getInstance(i).getChat(Long.valueOf(chat.linked_monoforum_id)) == null) {
                try {
                    runnable.run();
                    return;
                } catch (Exception e) {
                    FileLog.m1136e(e);
                    return;
                }
            }
            runLinkRequest(i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, num, l, l2, num2, str13, map, str14, str15, str16, str17, tL_wallPaper, str18, str19, str20, str21, z2, str22, i2, i3, str23, str24, str25, str26, str27, progress, z3, i4, z4, i5, i6, str28, str29, z5, str30, z6, z7, z8, z9, z10, str31, num3, z11);
            return;
        }
        try {
            runnable.run();
        } catch (Exception e2) {
            FileLog.m1136e(e2);
        }
    }

    public static /* synthetic */ void $r8$lambda$CZU4SE_HC4AhsHQ3cjN4HEkg0fs(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$16 */
    /* JADX INFO: loaded from: classes6.dex */
    class C590116 implements MessagesController.MessagesLoadedCallback {
        final /* synthetic */ Bundle val$args;
        final /* synthetic */ long val$dialog_id;
        final /* synthetic */ Runnable val$dismissLoading;
        final /* synthetic */ BaseFragment val$lastFragment;
        final /* synthetic */ String val$livestream;
        final /* synthetic */ Integer val$messageId;

        C590116(Runnable runnable, String str, BaseFragment baseFragment, long j, Integer num, Bundle bundle) {
            this.val$dismissLoading = runnable;
            this.val$livestream = str;
            this.val$lastFragment = baseFragment;
            this.val$dialog_id = j;
            this.val$messageId = num;
            this.val$args = bundle;
        }

        /* JADX WARN: Removed duplicated region for block: B:57:0x002e  */
        @Override // org.telegram.messenger.MessagesController.MessagesLoadedCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onMessagesLoaded(boolean r8) {
            /*
                r7 = this;
                java.lang.Runnable r8 = r7.val$dismissLoading     // Catch: java.lang.Exception -> L6
                r8.run()     // Catch: java.lang.Exception -> L6
                goto Lb
            L6:
                r0 = move-exception
                r8 = r0
                org.telegram.messenger.FileLog.m1136e(r8)
            Lb:
                org.telegram.ui.LaunchActivity r8 = org.telegram.p029ui.LaunchActivity.this
                boolean r8 = r8.isFinishing()
                if (r8 != 0) goto La3
                java.lang.String r8 = r7.val$livestream
                if (r8 == 0) goto L2e
                org.telegram.ui.ActionBar.BaseFragment r8 = r7.val$lastFragment
                boolean r0 = r8 instanceof org.telegram.p029ui.ChatActivity
                if (r0 == 0) goto L2e
                org.telegram.ui.ChatActivity r8 = (org.telegram.p029ui.ChatActivity) r8
                long r0 = r8.getDialogId()
                long r2 = r7.val$dialog_id
                int r8 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r8 == 0) goto L2a
                goto L2e
            L2a:
                org.telegram.ui.ActionBar.BaseFragment r8 = r7.val$lastFragment
            L2c:
                r6 = r8
                goto L94
            L2e:
                org.telegram.ui.ActionBar.BaseFragment r8 = r7.val$lastFragment
                boolean r0 = r8 instanceof org.telegram.p029ui.ChatActivity
                if (r0 == 0) goto L83
                org.telegram.ui.ChatActivity r8 = (org.telegram.p029ui.ChatActivity) r8
                long r0 = r8.getDialogId()
                long r2 = r7.val$dialog_id
                int r8 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r8 != 0) goto L83
                java.lang.Integer r8 = r7.val$messageId
                if (r8 != 0) goto L83
                org.telegram.ui.ActionBar.BaseFragment r8 = r7.val$lastFragment
                org.telegram.ui.ChatActivity r8 = (org.telegram.p029ui.ChatActivity) r8
                org.telegram.ui.Components.RecyclerListView r0 = r8.getChatListView()
                r1 = 1084227584(0x40a00000, float:5.0)
                org.telegram.messenger.AndroidUtilities.shakeViewSpring(r0, r1)
                org.telegram.messenger.BotWebViewVibrationEffect r0 = org.telegram.messenger.BotWebViewVibrationEffect.APP_ERROR
                r0.vibrate()
                org.telegram.ui.Components.ChatActivityEnterView r0 = r8.getChatActivityEnterView()
                r2 = 0
                r3 = r2
            L5c:
                int r4 = r0.getChildCount()
                if (r3 >= r4) goto L6c
                android.view.View r4 = r0.getChildAt(r3)
                org.telegram.messenger.AndroidUtilities.shakeViewSpring(r4, r1)
                int r3 = r3 + 1
                goto L5c
            L6c:
                org.telegram.ui.ActionBar.ActionBar r8 = r8.getActionBar()
            L70:
                int r0 = r8.getChildCount()
                if (r2 >= r0) goto L80
                android.view.View r0 = r8.getChildAt(r2)
                org.telegram.messenger.AndroidUtilities.shakeViewSpring(r0, r1)
                int r2 = r2 + 1
                goto L70
            L80:
                org.telegram.ui.ActionBar.BaseFragment r8 = r7.val$lastFragment
                goto L2c
            L83:
                org.telegram.ui.ChatActivity r8 = new org.telegram.ui.ChatActivity
                android.os.Bundle r0 = r7.val$args
                r8.<init>(r0)
                org.telegram.ui.LaunchActivity r0 = org.telegram.p029ui.LaunchActivity.this
                org.telegram.ui.ActionBar.INavigationLayout r0 = r0.getActionBarLayout()
                r0.presentFragment(r8)
                goto L2c
            L94:
                java.lang.String r3 = r7.val$livestream
                long r4 = r7.val$dialog_id
                org.telegram.ui.LaunchActivity$16$$ExternalSyntheticLambda0 r1 = new org.telegram.ui.LaunchActivity$16$$ExternalSyntheticLambda0
                r2 = r7
                r1.<init>()
                r2 = 150(0x96, double:7.4E-322)
                org.telegram.messenger.AndroidUtilities.runOnUIThread(r1, r2)
            La3:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.C590116.onMessagesLoaded(boolean):void");
        }

        public /* synthetic */ void lambda$onMessagesLoaded$2(String str, final long j, final BaseFragment baseFragment) {
            if (str != null) {
                final AccountInstance accountInstance = AccountInstance.getInstance(LaunchActivity.this.currentAccount);
                long j2 = -j;
                if (accountInstance.getMessagesController().getGroupCall(j2, false) != null) {
                    VoIPHelper.startCall(accountInstance.getMessagesController().getChat(Long.valueOf(j2)), accountInstance.getMessagesController().getInputPeer(j), null, false, Boolean.valueOf(!r10.call.rtmp_stream), LaunchActivity.this, baseFragment, accountInstance);
                    return;
                }
                TLRPC.ChatFull chatFull = accountInstance.getMessagesController().getChatFull(j2);
                if (chatFull != null) {
                    if (chatFull.call == null) {
                        if (baseFragment.getParentActivity() != null) {
                            BulletinFactory.m1246of(baseFragment).createSimpleBulletin(C2888R.raw.linkbroken, LocaleController.getString(C2888R.string.InviteExpired)).show();
                            return;
                        }
                        return;
                    }
                    accountInstance.getMessagesController().getGroupCall(j2, true, new Runnable() { // from class: org.telegram.ui.LaunchActivity$16$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onMessagesLoaded$1(accountInstance, j, baseFragment);
                        }
                    });
                }
            }
        }

        public /* synthetic */ void lambda$onMessagesLoaded$1(final AccountInstance accountInstance, final long j, final BaseFragment baseFragment) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$16$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onMessagesLoaded$0(accountInstance, j, baseFragment);
                }
            });
        }

        public /* synthetic */ void lambda$onMessagesLoaded$0(AccountInstance accountInstance, long j, BaseFragment baseFragment) {
            long j2 = -j;
            ChatObject.Call groupCall = accountInstance.getMessagesController().getGroupCall(j2, false);
            VoIPHelper.startCall(accountInstance.getMessagesController().getChat(Long.valueOf(j2)), accountInstance.getMessagesController().getInputPeer(j), null, false, Boolean.valueOf(groupCall == null || !groupCall.call.rtmp_stream), LaunchActivity.this, baseFragment, accountInstance);
        }

        @Override // org.telegram.messenger.MessagesController.MessagesLoadedCallback
        public void onError() {
            if (!LaunchActivity.this.isFinishing()) {
                AlertsCreator.showSimpleAlert((BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), LocaleController.getString(C2888R.string.JoinToGroupErrorNotExist));
            }
            try {
                this.val$dismissLoading.run();
            } catch (Exception e) {
                FileLog.m1136e(e);
            }
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$94(final int i, final AlertDialog alertDialog, final Runnable runnable, final String str, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda98
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$93(tL_error, tLObject, i, alertDialog, runnable, str);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:79:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$runLinkRequest$93(org.telegram.tgnet.TLRPC.TL_error r6, org.telegram.tgnet.TLObject r7, int r8, org.telegram.p029ui.ActionBar.AlertDialog r9, java.lang.Runnable r10, java.lang.String r11) {
        /*
            Method dump skipped, instruction units count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.lambda$runLinkRequest$93(org.telegram.tgnet.TLRPC$TL_error, org.telegram.tgnet.TLObject, int, org.telegram.ui.ActionBar.AlertDialog, java.lang.Runnable, java.lang.String):void");
    }

    public static /* synthetic */ void $r8$lambda$tak5VBo80soQz8JMjFJR2533vrE(boolean[] zArr, DialogInterface dialogInterface) {
        zArr[0] = true;
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$17 */
    /* JADX INFO: loaded from: classes6.dex */
    class C590217 implements MessagesController.MessagesLoadedCallback {
        final /* synthetic */ Bundle val$args;
        final /* synthetic */ boolean[] val$canceled;
        final /* synthetic */ Runnable val$dismissLoading;
        final /* synthetic */ TLRPC.ChatInvite val$invite;

        C590217(Runnable runnable, boolean[] zArr, Bundle bundle, TLRPC.ChatInvite chatInvite) {
            runnable = runnable;
            zArr = zArr;
            bundle = bundle;
            chatInvite = chatInvite;
        }

        @Override // org.telegram.messenger.MessagesController.MessagesLoadedCallback
        public void onMessagesLoaded(boolean z) {
            try {
                runnable.run();
            } catch (Exception e) {
                FileLog.m1136e(e);
            }
            if (zArr[0]) {
                return;
            }
            ChatActivity chatActivity = new ChatActivity(bundle);
            TLRPC.ChatInvite chatInvite = chatInvite;
            if (chatInvite instanceof TLRPC.TL_chatInvitePeek) {
                chatActivity.setChatInvite(chatInvite);
            }
            LaunchActivity.this.getActionBarLayout().presentFragment(chatActivity);
        }

        @Override // org.telegram.messenger.MessagesController.MessagesLoadedCallback
        public void onError() {
            if (!LaunchActivity.this.isFinishing()) {
                AlertsCreator.showSimpleAlert((BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), LocaleController.getString(C2888R.string.JoinToGroupErrorNotExist));
            }
            try {
                runnable.run();
            } catch (Exception e) {
                FileLog.m1136e(e);
            }
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$92(final long j, String str, final Long l) {
        if (!"paid".equals(str) || l.longValue() == 0) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda156
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$91(l, j);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$91(Long l, final long j) {
        BaseFragment safeLastFragment = getSafeLastFragment();
        if (safeLastFragment == null) {
            return;
        }
        final ChatActivity chatActivityM1239of = ChatActivity.m1239of(l.longValue());
        safeLastFragment.presentFragment(chatActivityM1239of);
        final TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-l.longValue()));
        if (chat != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda175
                @Override // java.lang.Runnable
                public final void run() {
                    BulletinFactory.m1246of(chatActivityM1239of).createSimpleBulletin(C2888R.raw.stars_send, LocaleController.getString(C2888R.string.StarsSubscriptionCompleted), AndroidUtilities.replaceTags(LocaleController.formatPluralString("StarsSubscriptionCompletedText", (int) j, chat.title))).show(true);
                }
            }, 250L);
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$96(final int i, final Runnable runnable, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        if (tL_error == null) {
            MessagesController.getInstance(i).processUpdates((TLRPC.Updates) tLObject, false);
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda112
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$95(runnable, tL_error, tLObject, i);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$95(Runnable runnable, TLRPC.TL_error tL_error, TLObject tLObject, int i) {
        if (isFinishing()) {
            return;
        }
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
        if (tL_error == null) {
            if (this.actionBarLayout != null) {
                TLRPC.Updates updates = (TLRPC.Updates) tLObject;
                if (updates.chats.isEmpty()) {
                    return;
                }
                TLRPC.Chat chat = updates.chats.get(0);
                chat.left = false;
                chat.kicked = false;
                MessagesController.getInstance(i).putUsers(updates.users, false);
                MessagesController.getInstance(i).putChats(updates.chats, false);
                Bundle bundle = new Bundle();
                bundle.putLong("chat_id", chat.f1660id);
                ArrayList arrayList = mainFragmentsStack;
                if (arrayList.isEmpty() || MessagesController.getInstance(i).checkCanOpenChat(bundle, (BaseFragment) arrayList.get(arrayList.size() - 1))) {
                    ChatActivity chatActivity = new ChatActivity(bundle);
                    NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
                    getActionBarLayout().presentFragment(chatActivity, false, true, true, false);
                    return;
                }
                return;
            }
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(LocaleController.getString(C2888R.string.AppName));
        if (tL_error.text.startsWith("FLOOD_WAIT")) {
            builder.setMessage(LocaleController.getString(C2888R.string.FloodWait));
        } else if (tL_error.text.equals("USERS_TOO_MUCH")) {
            builder.setMessage(LocaleController.getString(C2888R.string.JoinToGroupErrorFull));
        } else {
            builder.setMessage(LocaleController.getString(C2888R.string.JoinToGroupErrorNotExist));
        }
        builder.setPositiveButton(LocaleController.getString(C2888R.string.f1606OK), null);
        showAlertDialog(builder);
    }

    public /* synthetic */ boolean lambda$runLinkRequest$97(boolean z, int i, String str, DialogsActivity dialogsActivity, ArrayList arrayList, CharSequence charSequence, boolean z2, boolean z3, int i2, int i3, TopicsFragment topicsFragment) {
        long j = ((MessagesStorage.TopicKey) arrayList.get(0)).dialogId;
        Bundle bundle = new Bundle();
        bundle.putBoolean("scrollToTopOnResume", true);
        bundle.putBoolean("hasUrl", z);
        if (DialogObject.isEncryptedDialog(j)) {
            bundle.putInt("enc_id", DialogObject.getEncryptedChatId(j));
        } else if (DialogObject.isUserDialog(j)) {
            bundle.putLong("user_id", j);
        } else {
            bundle.putLong("chat_id", -j);
        }
        if (MessagesController.getInstance(i).checkCanOpenChat(bundle, dialogsActivity)) {
            NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
            MediaDataController.getInstance(i).saveDraft(j, 0, str, null, null, false, 0L);
            getActionBarLayout().presentFragment(new ChatActivity(bundle), true, false, true, false);
        }
        return true;
    }

    public /* synthetic */ void lambda$runLinkRequest$101(int[] iArr, final int i, final Runnable runnable, final TL_account.getAuthorizationForm getauthorizationform, final String str, final String str2, final String str3, TLObject tLObject, final TLRPC.TL_error tL_error) {
        final TL_account.authorizationForm authorizationform = (TL_account.authorizationForm) tLObject;
        if (authorizationform != null) {
            iArr[0] = ConnectionsManager.getInstance(i).sendRequest(new TL_account.getPassword(), new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda113
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject2, TLRPC.TL_error tL_error2) {
                    this.f$0.lambda$runLinkRequest$99(runnable, i, authorizationform, getauthorizationform, str, str2, str3, tLObject2, tL_error2);
                }
            });
        } else {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda114
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$runLinkRequest$100(runnable, tL_error);
                }
            });
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$99(final Runnable runnable, final int i, final TL_account.authorizationForm authorizationform, final TL_account.getAuthorizationForm getauthorizationform, final String str, final String str2, final String str3, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda148
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$98(runnable, tLObject, i, authorizationform, getauthorizationform, str, str2, str3);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$98(Runnable runnable, TLObject tLObject, int i, TL_account.authorizationForm authorizationform, TL_account.getAuthorizationForm getauthorizationform, String str, String str2, String str3) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
        if (tLObject != null) {
            MessagesController.getInstance(i).putUsers(authorizationform.users, false);
            lambda$runLinkRequest$106(new PassportActivity(5, getauthorizationform.bot_id, getauthorizationform.scope, getauthorizationform.public_key, str, str2, str3, authorizationform, (TL_account.Password) tLObject));
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$100(Runnable runnable, TLRPC.TL_error tL_error) {
        try {
            runnable.run();
            if ("APP_VERSION_OUTDATED".equals(tL_error.text)) {
                AlertsCreator.showUpdateAppAlert(this, LocaleController.getString(C2888R.string.UpdateAppAlert), true);
                return;
            }
            showAlertDialog(AlertsCreator.createSimpleAlert(this, LocaleController.getString(C2888R.string.ErrorOccurred) + "\n" + tL_error.text));
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$103(final Runnable runnable, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda96
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$102(runnable, tLObject);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$102(Runnable runnable, TLObject tLObject) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
        if (tLObject instanceof TLRPC.TL_help_deepLinkInfo) {
            TLRPC.TL_help_deepLinkInfo tL_help_deepLinkInfo = (TLRPC.TL_help_deepLinkInfo) tLObject;
            AlertsCreator.showUpdateAppAlert(this, tL_help_deepLinkInfo.message, tL_help_deepLinkInfo.update_app);
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$105(final Runnable runnable, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda109
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$104(runnable, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$104(Runnable runnable, TLObject tLObject, TLRPC.TL_error tL_error) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
        if (tLObject instanceof TLRPC.TL_langPackLanguage) {
            showAlertDialog(AlertsCreator.createLanguageAlert(this, (TLRPC.TL_langPackLanguage) tLObject));
            return;
        }
        if (tL_error != null) {
            if ("LANG_CODE_NOT_SUPPORTED".equals(tL_error.text)) {
                showAlertDialog(AlertsCreator.createSimpleAlert(this, LocaleController.getString(C2888R.string.LanguageUnsupportedError)));
                return;
            }
            showAlertDialog(AlertsCreator.createSimpleAlert(this, LocaleController.getString(C2888R.string.ErrorOccurred) + "\n" + tL_error.text));
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$108(final Runnable runnable, final TLRPC.TL_wallPaper tL_wallPaper, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda127
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$107(runnable, tLObject, tL_wallPaper, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$107(Runnable runnable, TLObject tLObject, TLRPC.TL_wallPaper tL_wallPaper, TLRPC.TL_error tL_error) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
        if (tLObject instanceof TLRPC.TL_wallPaper) {
            TLRPC.TL_wallPaper tL_wallPaper2 = (TLRPC.TL_wallPaper) tLObject;
            boolean z = tL_wallPaper2.pattern;
            Object obj = tL_wallPaper2;
            if (z) {
                String str = tL_wallPaper2.slug;
                TLRPC.WallPaperSettings wallPaperSettings = tL_wallPaper.settings;
                WallpapersListActivity.ColorWallpaper colorWallpaper = new WallpapersListActivity.ColorWallpaper(str, wallPaperSettings.background_color, wallPaperSettings.second_background_color, wallPaperSettings.third_background_color, wallPaperSettings.fourth_background_color, AndroidUtilities.getWallpaperRotation(wallPaperSettings.rotation, false), r11.intensity / 100.0f, tL_wallPaper.settings.motion, null);
                colorWallpaper.pattern = tL_wallPaper2;
                obj = colorWallpaper;
            }
            ThemePreviewActivity themePreviewActivity = new ThemePreviewActivity(obj, null, true, false);
            TLRPC.WallPaperSettings wallPaperSettings2 = tL_wallPaper.settings;
            themePreviewActivity.setInitialModes(wallPaperSettings2.blur, wallPaperSettings2.motion, wallPaperSettings2.intensity);
            lambda$runLinkRequest$106(themePreviewActivity);
            return;
        }
        showAlertDialog(AlertsCreator.createSimpleAlert(this, LocaleController.getString(C2888R.string.ErrorOccurred) + "\n" + tL_error.text));
    }

    public /* synthetic */ void lambda$runLinkRequest$109(Browser.Progress progress) {
        this.loadingThemeFileName = null;
        this.loadingThemeWallpaperName = null;
        this.loadingThemeWallpaper = null;
        this.loadingThemeInfo = null;
        this.loadingThemeProgressDialog = null;
        this.loadingTheme = null;
        if (progress != null) {
            progress.end();
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$111(final AlertDialog alertDialog, final Runnable runnable, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda120
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$110(tLObject, alertDialog, runnable, tL_error);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:73:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$runLinkRequest$110(org.telegram.tgnet.TLObject r6, org.telegram.p029ui.ActionBar.AlertDialog r7, java.lang.Runnable r8, org.telegram.tgnet.TLRPC.TL_error r9) {
        /*
            Method dump skipped, instruction units count: 205
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.lambda$runLinkRequest$110(org.telegram.tgnet.TLObject, org.telegram.ui.ActionBar.AlertDialog, java.lang.Runnable, org.telegram.tgnet.TLRPC$TL_error):void");
    }

    public /* synthetic */ void lambda$runLinkRequest$113(final int[] iArr, final int i, final Runnable runnable, final Integer num, final Integer num2, final Long l, final Integer num3, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda97
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$112(tLObject, iArr, i, runnable, num, num2, l, num3);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$112(TLObject tLObject, int[] iArr, int i, Runnable runnable, Integer num, Integer num2, Long l, Integer num3) {
        if (tLObject instanceof TLRPC.TL_messages_chats) {
            TLRPC.TL_messages_chats tL_messages_chats = (TLRPC.TL_messages_chats) tLObject;
            if (!tL_messages_chats.chats.isEmpty()) {
                MessagesController.getInstance(this.currentAccount).putChats(tL_messages_chats.chats, false);
                iArr[0] = runCommentRequest(i, runnable, num, num2, l, num3, (TLRPC.Chat) tL_messages_chats.chats.get(0));
                return;
            }
        }
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
        showAlertDialog(AlertsCreator.createNoAccessAlert(this, LocaleController.getString(C2888R.string.DialogNotAvailable), LocaleController.getString(C2888R.string.LinkNotFound), null));
    }

    public static /* synthetic */ void $r8$lambda$qZ7BtdndriQoMCC1dMiNgStk4pA(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    public /* synthetic */ void lambda$runLinkRequest$117(final Bundle bundle, final Long l, int[] iArr, final Runnable runnable, final boolean z, final Browser.Progress progress, final Long l2, final Integer num, final Integer num2, final BaseFragment baseFragment, final int i) {
        if (getActionBarLayout().presentFragment(new ChatActivity(bundle))) {
            return;
        }
        TLRPC.TL_channels_getChannels tL_channels_getChannels = new TLRPC.TL_channels_getChannels();
        TLRPC.TL_inputChannel tL_inputChannel = new TLRPC.TL_inputChannel();
        tL_inputChannel.channel_id = l.longValue();
        tL_channels_getChannels.f1704id.add(tL_inputChannel);
        iArr[0] = ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_channels_getChannels, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda123
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$runLinkRequest$116(runnable, z, l, progress, l2, num, num2, baseFragment, i, bundle, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$116(final Runnable runnable, final boolean z, final Long l, final Browser.Progress progress, final Long l2, final Integer num, final Integer num2, final BaseFragment baseFragment, final int i, final Bundle bundle, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda141
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$115(runnable, tLObject, z, l, progress, l2, num, num2, baseFragment, i, bundle);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$115(Runnable runnable, TLObject tLObject, boolean z, Long l, Browser.Progress progress, Long l2, Integer num, Integer num2, BaseFragment baseFragment, int i, Bundle bundle) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
        if (tLObject instanceof TLRPC.TL_messages_chats) {
            TLRPC.TL_messages_chats tL_messages_chats = (TLRPC.TL_messages_chats) tLObject;
            if (!tL_messages_chats.chats.isEmpty()) {
                MessagesController.getInstance(this.currentAccount).putChats(tL_messages_chats.chats, false);
                TLRPC.Chat chat = (TLRPC.Chat) tL_messages_chats.chats.get(0);
                if (chat != null && z && ChatObject.isBoostSupported(chat)) {
                    processBoostDialog(Long.valueOf(-l.longValue()), null, progress);
                } else if (chat != null && chat.forum) {
                    if (l2 != null) {
                        openForumFromLink(-l.longValue(), num, null, num2, null, 0, -1);
                    } else {
                        openForumFromLink(-l.longValue(), null, null, num2, null, 0, -1);
                    }
                }
                if (baseFragment == null || MessagesController.getInstance(i).checkCanOpenChat(bundle, baseFragment)) {
                    getActionBarLayout().presentFragment(new ChatActivity(bundle));
                    return;
                }
                return;
            }
        }
        showAlertDialog(AlertsCreator.createNoAccessAlert(this, LocaleController.getString(C2888R.string.DialogNotAvailable), LocaleController.getString(C2888R.string.LinkNotFound), null));
    }

    public /* synthetic */ void lambda$runLinkRequest$119(final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda134
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runLinkRequest$118(tLObject);
            }
        });
    }

    public /* synthetic */ void lambda$runLinkRequest$118(TLObject tLObject) {
        if (tLObject instanceof TL_account.resolvedBusinessChatLinks) {
            TL_account.resolvedBusinessChatLinks resolvedbusinesschatlinks = (TL_account.resolvedBusinessChatLinks) tLObject;
            MessagesController.getInstance(this.currentAccount).putUsers(resolvedbusinesschatlinks.users, false);
            MessagesController.getInstance(this.currentAccount).putChats(resolvedbusinesschatlinks.chats, false);
            MessagesStorage.getInstance(this.currentAccount).putUsersAndChats(resolvedbusinesschatlinks.users, resolvedbusinesschatlinks.chats, true, true);
            Bundle bundle = new Bundle();
            TLRPC.Peer peer = resolvedbusinesschatlinks.peer;
            if (peer instanceof TLRPC.TL_peerUser) {
                bundle.putLong("user_id", peer.user_id);
            } else if ((peer instanceof TLRPC.TL_peerChat) || (peer instanceof TLRPC.TL_peerChannel)) {
                bundle.putLong("chat_id", peer.channel_id);
            }
            ChatActivity chatActivity = new ChatActivity(bundle);
            chatActivity.setResolvedChatLink(resolvedbusinesschatlinks);
            presentFragment(chatActivity, false, true);
            return;
        }
        showAlertDialog(AlertsCreator.createSimpleAlert(this, LocaleController.getString(C2888R.string.BusinessLink), LocaleController.getString(C2888R.string.BusinessLinkInvalid)));
    }

    /* JADX INFO: renamed from: $r8$lambda$p6Cu92gBy_Qia9E55HNdEq-E16Q */
    public static /* synthetic */ void m15089$r8$lambda$p6Cu92gBy_Qia9E55HNdEqE16Q(int i, int[] iArr, Runnable runnable, DialogInterface dialogInterface) {
        ConnectionsManager.getInstance(i).cancelRequest(iArr[0], true);
        if (runnable != null) {
            runnable.run();
        }
    }

    public static /* synthetic */ void $r8$lambda$TAr2w9kjYw_cj5d1Luqm4_zwqyw(int i, int[] iArr, Runnable runnable) {
        ConnectionsManager.getInstance(i).cancelRequest(iArr[0], true);
        if (runnable != null) {
            runnable.run();
        }
    }

    private void processWebAppBot(final int i, final String str, final String str2, final String str3, final String str4, final String str5, final String str6, final String str7, final String str8, final String str9, final String str10, final String str11, final String str12, final boolean z, final Integer num, final Long l, final Long l2, final Integer num2, final String str13, final HashMap map, final String str14, final String str15, final String str16, final String str17, final TLRPC.TL_wallPaper tL_wallPaper, final String str18, final String str19, final String str20, final String str21, final boolean z2, final String str22, final int i2, final int i3, final String str23, final String str24, final String str25, String str26, final String str27, final Browser.Progress progress, final boolean z3, final int i4, final boolean z4, final int i5, final int i6, final boolean z5, final String str28, final TLRPC.User user, final Runnable runnable, final boolean z6, final boolean z7, final boolean z8, final boolean z9, final boolean z10, final boolean z11, final boolean z12, final String str29, final boolean z13) {
        TLRPC.TL_messages_getBotApp tL_messages_getBotApp = new TLRPC.TL_messages_getBotApp();
        TLRPC.TL_inputBotAppShortName tL_inputBotAppShortName = new TLRPC.TL_inputBotAppShortName();
        tL_inputBotAppShortName.bot_id = MessagesController.getInstance(i).getInputUser(user);
        tL_inputBotAppShortName.short_name = str26;
        tL_messages_getBotApp.app = tL_inputBotAppShortName;
        ConnectionsManager.getInstance(i).sendRequest(tL_messages_getBotApp, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda139
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$processWebAppBot$126(progress, i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, num, l, l2, num2, str13, map, str14, str15, str16, str17, tL_wallPaper, str18, str19, str20, str21, z2, str22, i2, i3, str23, str24, str25, z3, i4, z4, i5, i6, z5, str28, z8, z9, z10, z11, z12, str29, z13, runnable, user, str27, z7, z6, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$processWebAppBot$126(final Browser.Progress progress, final int i, final String str, final String str2, final String str3, final String str4, final String str5, final String str6, final String str7, final String str8, final String str9, final String str10, final String str11, final String str12, final boolean z, final Integer num, final Long l, final Long l2, final Integer num2, final String str13, final HashMap map, final String str14, final String str15, final String str16, final String str17, final TLRPC.TL_wallPaper tL_wallPaper, final String str18, final String str19, final String str20, final String str21, final boolean z2, final String str22, final int i2, final int i3, final String str23, final String str24, final String str25, final boolean z3, final int i4, final boolean z4, final int i5, final int i6, final boolean z5, final String str26, final boolean z6, final boolean z7, final boolean z8, final boolean z9, final boolean z10, final String str27, final boolean z11, final Runnable runnable, final TLRPC.User user, final String str28, final boolean z12, final boolean z13, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (progress != null) {
            progress.end();
        }
        if (tL_error != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda164
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$processWebAppBot$122(i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, num, l, l2, num2, str13, map, str14, str15, str16, str17, tL_wallPaper, str18, str19, str20, str21, z2, str22, i2, i3, str23, str24, str25, progress, z3, i4, z4, i5, i6, z5, str26, z6, z7, z8, z9, z10, str27, z11);
                }
            });
        } else {
            final TLRPC.TL_messages_botApp tL_messages_botApp = (TLRPC.TL_messages_botApp) tLObject;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda165
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$processWebAppBot$125(runnable, i, user, tL_messages_botApp, str28, z6, z7, z8, z3, z12, z13, progress);
                }
            });
        }
    }

    public /* synthetic */ void lambda$processWebAppBot$122(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, boolean z, Integer num, Long l, Long l2, Integer num2, String str13, HashMap map, String str14, String str15, String str16, String str17, TLRPC.TL_wallPaper tL_wallPaper, String str18, String str19, String str20, String str21, boolean z2, String str22, int i2, int i3, String str23, String str24, String str25, Browser.Progress progress, boolean z3, int i4, boolean z4, int i5, int i6, boolean z5, String str26, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, String str27, boolean z11) {
        runLinkRequest(i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, num, l, l2, num2, str13, map, str14, str15, str16, str17, tL_wallPaper, str18, str19, str20, str21, z2, str22, i2, i3, str23, str24, str25, null, null, progress, z3, i4, z4, i5, i6, null, null, z5, str26, z6, z7, z8, z9, z10, str27, null, z11);
    }

    public /* synthetic */ void lambda$processWebAppBot$125(Runnable runnable, final int i, final TLRPC.User user, final TLRPC.TL_messages_botApp tL_messages_botApp, final String str, final boolean z, final boolean z2, final boolean z3, final boolean z4, boolean z5, boolean z6, Browser.Progress progress) {
        runnable.run();
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        ArrayList arrayList = mainFragmentsStack;
        final BaseFragment baseFragment = (arrayList == null || arrayList.isEmpty()) ? null : (BaseFragment) arrayList.get(arrayList.size() - 1);
        final Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda168
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processWebAppBot$123(baseFragment, i, user, tL_messages_botApp, atomicBoolean, str, z, z2, z3, z4);
            }
        };
        if (z5) {
            runnable2.run();
            return;
        }
        if (tL_messages_botApp.inactive && z6) {
            WebAppDisclaimerAlert.show(this, new com.google.android.exoplayer2.util.Consumer() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda169
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    runnable2.run();
                }
            }, null, progress != null ? new ChatActivity$ChatMessageCellDelegate$$ExternalSyntheticLambda13(progress) : null);
        } else if (tL_messages_botApp.request_write_access || z4) {
            AlertsCreator.createBotLaunchAlert(baseFragment, atomicBoolean, user, runnable2);
        } else {
            runnable2.run();
        }
    }

    public /* synthetic */ void lambda$processWebAppBot$123(BaseFragment baseFragment, int i, TLRPC.User user, TLRPC.TL_messages_botApp tL_messages_botApp, AtomicBoolean atomicBoolean, String str, boolean z, boolean z2, boolean z3, boolean z4) {
        if (baseFragment == null || !isActive || isFinishing() || isDestroyed()) {
            return;
        }
        long j = user.f1825id;
        WebViewRequestProps webViewRequestPropsM1344of = WebViewRequestProps.m1344of(i, j, j, null, null, 3, 0, 0L, false, tL_messages_botApp.app, atomicBoolean.get(), str, user, 0, z, z2);
        if (getBottomSheetTabs() == null || getBottomSheetTabs().tryReopenTab(webViewRequestPropsM1344of) == null) {
            SharedPrefsHelper.setWebViewConfirmShown(this.currentAccount, user.f1825id, true);
            BotWebViewSheet botWebViewSheet = new BotWebViewSheet(this, baseFragment.getResourceProvider());
            botWebViewSheet.setWasOpenedByLinkIntent(z3);
            botWebViewSheet.setDefaultFullsize(!z);
            if (z2) {
                botWebViewSheet.setFullscreen(true, false);
            }
            botWebViewSheet.setNeedsContext(false);
            botWebViewSheet.setParentActivity(this);
            botWebViewSheet.requestWebView(baseFragment, webViewRequestPropsM1344of);
            botWebViewSheet.show();
            if (tL_messages_botApp.inactive || z4) {
                botWebViewSheet.showJustAddedBulletin();
            }
        }
    }

    private void processAttachedMenuBotFromShortcut(final long j) {
        for (int i = 0; i < this.visibleDialogs.size(); i++) {
            if (this.visibleDialogs.get(i) instanceof BotWebViewSheet) {
                BotWebViewSheet botWebViewSheet = (BotWebViewSheet) this.visibleDialogs.get(i);
                if (botWebViewSheet.isShowing() && botWebViewSheet.getBotId() == j) {
                    return;
                }
            }
        }
        BaseFragment safeLastFragment = getSafeLastFragment();
        if (safeLastFragment != null && safeLastFragment.sheetsStack != null) {
            for (int i2 = 0; i2 < safeLastFragment.sheetsStack.size(); i2++) {
                if (safeLastFragment.sheetsStack.get(i2).isShown()) {
                    safeLastFragment.sheetsStack.get(i2);
                }
            }
        }
        EmptyBaseFragment sheetFragment = this.actionBarLayout.getSheetFragment(false);
        if (sheetFragment != null && sheetFragment.sheetsStack != null) {
            for (int i3 = 0; i3 < sheetFragment.sheetsStack.size(); i3++) {
                if (sheetFragment.sheetsStack.get(i3).isShown()) {
                    sheetFragment.sheetsStack.get(i3);
                }
            }
        }
        final Utilities.Callback callback = new Utilities.Callback() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda54
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$processAttachedMenuBotFromShortcut$127((TLRPC.User) obj);
            }
        };
        TLRPC.User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(j));
        if (user != null) {
            callback.run(user);
        } else {
            MessagesStorage.getInstance(this.currentAccount).getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda55
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$processAttachedMenuBotFromShortcut$129(j, callback);
                }
            });
        }
    }

    public /* synthetic */ void lambda$processAttachedMenuBotFromShortcut$127(TLRPC.User user) {
        MessagesController.getInstance(this.currentAccount).openApp(user, 0);
    }

    public /* synthetic */ void lambda$processAttachedMenuBotFromShortcut$129(long j, final Utilities.Callback callback) {
        final TLRPC.User user = MessagesStorage.getInstance(this.currentAccount).getUser(j);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda130
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processAttachedMenuBotFromShortcut$128(user, callback);
            }
        });
    }

    public /* synthetic */ void lambda$processAttachedMenuBotFromShortcut$128(TLRPC.User user, Utilities.Callback callback) {
        MessagesController.getInstance(this.currentAccount).putUser(user, true);
        callback.run(user);
    }

    private void processBoostDialog(Long l, Runnable runnable, Browser.Progress progress) {
        processBoostDialog(l, runnable, progress, null);
    }

    private void processBoostDialog(final Long l, final Runnable runnable, final Browser.Progress progress, final ChatMessageCell chatMessageCell) {
        final ChannelBoostsController boostsController = MessagesController.getInstance(this.currentAccount).getBoostsController();
        if (progress != null) {
            progress.init();
        }
        boostsController.getBoostsStats(l.longValue(), new com.google.android.exoplayer2.util.Consumer() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda87
            @Override // com.google.android.exoplayer2.util.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$processBoostDialog$131(progress, runnable, boostsController, l, chatMessageCell, (TL_stories.TL_premium_boostsStatus) obj);
            }
        });
    }

    public /* synthetic */ void lambda$processBoostDialog$131(final Browser.Progress progress, final Runnable runnable, ChannelBoostsController channelBoostsController, final Long l, final ChatMessageCell chatMessageCell, final TL_stories.TL_premium_boostsStatus tL_premium_boostsStatus) {
        if (tL_premium_boostsStatus != null) {
            channelBoostsController.userCanBoostChannel(l.longValue(), tL_premium_boostsStatus, new com.google.android.exoplayer2.util.Consumer() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda128
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$processBoostDialog$130(progress, l, tL_premium_boostsStatus, chatMessageCell, runnable, (ChannelBoostsController.CanApplyBoost) obj);
                }
            });
            return;
        }
        if (progress != null) {
            progress.end();
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$processBoostDialog$130(org.telegram.messenger.browser.Browser.Progress r8, java.lang.Long r9, org.telegram.tgnet.tl.TL_stories.TL_premium_boostsStatus r10, org.telegram.p029ui.Cells.ChatMessageCell r11, java.lang.Runnable r12, org.telegram.messenger.ChannelBoostsController.CanApplyBoost r13) {
        /*
            r7 = this;
            if (r8 == 0) goto L5
            r8.end()
        L5:
            org.telegram.ui.ActionBar.BaseFragment r1 = getLastFragmentIncludeMainTabs()
            if (r1 != 0) goto Ld
            goto L83
        Ld:
            org.telegram.ui.ActionBar.Theme$ResourcesProvider r8 = r1.getResourceProvider()
            org.telegram.ui.Stories.StoryViewer r0 = r1.getLastStoryViewer()
            if (r0 == 0) goto L29
            org.telegram.ui.Stories.StoryViewer r0 = r1.getLastStoryViewer()
            boolean r0 = r0.isFullyVisible()
            if (r0 == 0) goto L29
            org.telegram.ui.Stories.StoryViewer r8 = r1.getLastStoryViewer()
            org.telegram.ui.ActionBar.Theme$ResourcesProvider r8 = r8.getResourceProvider()
        L29:
            r5 = r8
            org.telegram.ui.Components.Premium.LimitReachedBottomSheet r0 = new org.telegram.ui.Components.Premium.LimitReachedBottomSheet
            r3 = 19
            int r4 = r7.currentAccount
            r2 = r7
            r0.<init>(r1, r2, r3, r4, r5)
            r0.setCanApplyBoost(r13)
            boolean r8 = r1 instanceof org.telegram.p029ui.ChatActivity
            r13 = 1
            r2 = 0
            if (r8 == 0) goto L50
            r8 = r1
            org.telegram.ui.ChatActivity r8 = (org.telegram.p029ui.ChatActivity) r8
            long r3 = r8.getDialogId()
            long r5 = r9.longValue()
            int r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r8 != 0) goto L4d
            goto L4e
        L4d:
            r13 = r2
        L4e:
            r2 = r13
            goto L68
        L50:
            boolean r8 = r1 instanceof org.telegram.p029ui.DialogsActivity
            if (r8 == 0) goto L68
            r8 = r1
            org.telegram.ui.DialogsActivity r8 = (org.telegram.p029ui.DialogsActivity) r8
            org.telegram.ui.RightSlidingDialogContainer r8 = r8.rightSlidingDialogContainer
            if (r8 == 0) goto L4d
            long r3 = r8.getCurrentFragmetDialogId()
            long r5 = r9.longValue()
            int r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r8 != 0) goto L4d
            goto L4e
        L68:
            r0.setBoostsStats(r10, r2)
            long r8 = r9.longValue()
            r0.setDialogId(r8)
            r0.setChatMessageCell(r11)
            r1.showDialog(r0)
            if (r12 == 0) goto L83
            r12.run()     // Catch: java.lang.Exception -> L7e
            return
        L7e:
            r0 = move-exception
            r8 = r0
            org.telegram.messenger.FileLog.m1136e(r8)
        L83:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.lambda$processBoostDialog$130(org.telegram.messenger.browser.Browser$Progress, java.lang.Long, org.telegram.tgnet.tl.TL_stories$TL_premium_boostsStatus, org.telegram.ui.Cells.ChatMessageCell, java.lang.Runnable, org.telegram.messenger.ChannelBoostsController$CanApplyBoost):void");
    }

    private void processAttachMenuBot(final int i, final long j, final String str, final TLRPC.User user, final String str2, final String str3) {
        TLRPC.TL_messages_getAttachMenuBot tL_messages_getAttachMenuBot = new TLRPC.TL_messages_getAttachMenuBot();
        tL_messages_getAttachMenuBot.bot = MessagesController.getInstance(i).getInputUser(j);
        ConnectionsManager.getInstance(i).sendRequest(tL_messages_getAttachMenuBot, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda140
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$processAttachMenuBot$138(i, str3, str, user, str2, j, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$processAttachMenuBot$138(final int i, final String str, final String str2, final TLRPC.User user, final String str3, final long j, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda157
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processAttachMenuBot$137(tLObject, i, str, str2, user, str3, j);
            }
        });
    }

    public /* synthetic */ void lambda$processAttachMenuBot$137(TLObject tLObject, final int i, String str, String str2, final TLRPC.User user, final String str3, final long j) {
        final DialogsActivity dialogsActivity;
        if (tLObject instanceof TLRPC.TL_attachMenuBotsBot) {
            TLRPC.TL_attachMenuBotsBot tL_attachMenuBotsBot = (TLRPC.TL_attachMenuBotsBot) tLObject;
            MessagesController.getInstance(i).putUsers(tL_attachMenuBotsBot.users, false);
            TLRPC.TL_attachMenuBot tL_attachMenuBot = tL_attachMenuBotsBot.bot;
            if (str != null) {
                showAttachMenuBot(this, this.currentAccount, tL_attachMenuBot, str, false);
                return;
            }
            ArrayList arrayList = mainFragmentsStack;
            BaseFragment baseFragment = (BaseFragment) arrayList.get(arrayList.size() - 1);
            if (AndroidUtilities.isTablet() && !(baseFragment instanceof ChatActivity)) {
                ArrayList arrayList2 = rightFragmentsStack;
                if (!arrayList2.isEmpty()) {
                    baseFragment = (BaseFragment) arrayList2.get(arrayList2.size() - 1);
                }
            }
            final BaseFragment baseFragment2 = baseFragment;
            ArrayList arrayList3 = new ArrayList();
            if (!TextUtils.isEmpty(str2)) {
                for (String str4 : str2.split(" ")) {
                    if (MediaDataController.canShowAttachMenuBotForTarget(tL_attachMenuBot, str4)) {
                        arrayList3.add(str4);
                    }
                }
            }
            if (arrayList3.isEmpty()) {
                dialogsActivity = null;
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt("dialogsType", 14);
                bundle.putBoolean("onlySelect", true);
                bundle.putBoolean("allowGroups", arrayList3.contains("groups"));
                bundle.putBoolean("allowMegagroups", arrayList3.contains("groups"));
                bundle.putBoolean("allowLegacyGroups", arrayList3.contains("groups"));
                bundle.putBoolean("allowUsers", arrayList3.contains("users"));
                bundle.putBoolean("allowChannels", arrayList3.contains("channels"));
                bundle.putBoolean("allowBots", arrayList3.contains("bots"));
                DialogsActivity dialogsActivity2 = new DialogsActivity(bundle);
                dialogsActivity2.setDelegate(new DialogsActivity.DialogsActivityDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda171
                    @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
                    public /* synthetic */ boolean canSelectStories() {
                        return DialogsActivity.DialogsActivityDelegate.CC.$default$canSelectStories(this);
                    }

                    @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
                    public final boolean didSelectDialogs(DialogsActivity dialogsActivity3, ArrayList arrayList4, CharSequence charSequence, boolean z, boolean z2, int i2, int i3, TopicsFragment topicsFragment) {
                        return this.f$0.lambda$processAttachMenuBot$132(user, str3, i, dialogsActivity3, arrayList4, charSequence, z, z2, i2, i3, topicsFragment);
                    }

                    @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
                    public /* synthetic */ boolean didSelectStories(DialogsActivity dialogsActivity3) {
                        return DialogsActivity.DialogsActivityDelegate.CC.$default$didSelectStories(this, dialogsActivity3);
                    }
                });
                dialogsActivity = dialogsActivity2;
            }
            if (tL_attachMenuBot.inactive) {
                AttachBotIntroTopView attachBotIntroTopView = new AttachBotIntroTopView(this);
                attachBotIntroTopView.setColor(Theme.getColor(Theme.key_chat_attachIcon));
                attachBotIntroTopView.setBackgroundColor(Theme.getColor(Theme.key_dialogTopBackground));
                attachBotIntroTopView.setAttachBot(tL_attachMenuBot);
                WebAppDisclaimerAlert.show(this, new com.google.android.exoplayer2.util.Consumer() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda172
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        this.f$0.lambda$processAttachMenuBot$136(i, j, dialogsActivity, baseFragment2, user, str3, (Boolean) obj);
                    }
                }, tL_attachMenuBot.request_write_access ? user : null, null);
                return;
            }
            if (dialogsActivity != null) {
                if (baseFragment2 != null) {
                    baseFragment2.dismissCurrentDialog();
                }
                for (int i2 = 0; i2 < this.visibleDialogs.size(); i2++) {
                    if (((Dialog) this.visibleDialogs.get(i2)).isShowing()) {
                        ((Dialog) this.visibleDialogs.get(i2)).dismiss();
                    }
                }
                this.visibleDialogs.clear();
                lambda$runLinkRequest$106(dialogsActivity);
                return;
            }
            if (baseFragment2 instanceof ChatActivity) {
                ChatActivity chatActivity = (ChatActivity) baseFragment2;
                if (!MediaDataController.canShowAttachMenuBot(tL_attachMenuBot, chatActivity.getCurrentUser() != null ? chatActivity.getCurrentUser() : chatActivity.getCurrentChat())) {
                    BulletinFactory.m1246of(baseFragment2).createErrorBulletin(LocaleController.getString(C2888R.string.BotAlreadyAddedToAttachMenu)).show();
                    return;
                } else {
                    chatActivity.openAttachBotLayout(user.f1825id, str3, false);
                    return;
                }
            }
            BulletinFactory.m1246of(baseFragment2).createErrorBulletin(LocaleController.getString(C2888R.string.BotAlreadyAddedToAttachMenu)).show();
            return;
        }
        ArrayList arrayList4 = mainFragmentsStack;
        BulletinFactory.m1246of((BaseFragment) arrayList4.get(arrayList4.size() - 1)).createErrorBulletin(LocaleController.getString(C2888R.string.BotCantAddToAttachMenu)).show();
    }

    public /* synthetic */ boolean lambda$processAttachMenuBot$132(TLRPC.User user, String str, int i, DialogsActivity dialogsActivity, ArrayList arrayList, CharSequence charSequence, boolean z, boolean z2, int i2, int i3, TopicsFragment topicsFragment) {
        long j = ((MessagesStorage.TopicKey) arrayList.get(0)).dialogId;
        Bundle bundle = new Bundle();
        bundle.putBoolean("scrollToTopOnResume", true);
        if (DialogObject.isEncryptedDialog(j)) {
            bundle.putInt("enc_id", DialogObject.getEncryptedChatId(j));
        } else if (DialogObject.isUserDialog(j)) {
            bundle.putLong("user_id", j);
        } else {
            bundle.putLong("chat_id", -j);
        }
        bundle.putString("attach_bot", UserObject.getPublicUsername(user));
        if (str != null) {
            bundle.putString("attach_bot_start_command", str);
        }
        if (MessagesController.getInstance(i).checkCanOpenChat(bundle, dialogsActivity)) {
            NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
            getActionBarLayout().presentFragment(new ChatActivity(bundle), true, false, true, false);
        }
        return true;
    }

    public /* synthetic */ void lambda$processAttachMenuBot$136(final int i, long j, final DialogsActivity dialogsActivity, final BaseFragment baseFragment, final TLRPC.User user, final String str, Boolean bool) {
        TLRPC.TL_messages_toggleBotInAttachMenu tL_messages_toggleBotInAttachMenu = new TLRPC.TL_messages_toggleBotInAttachMenu();
        tL_messages_toggleBotInAttachMenu.bot = MessagesController.getInstance(i).getInputUser(j);
        tL_messages_toggleBotInAttachMenu.enabled = true;
        tL_messages_toggleBotInAttachMenu.write_allowed = true;
        ConnectionsManager.getInstance(i).sendRequest(tL_messages_toggleBotInAttachMenu, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda182
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$processAttachMenuBot$135(i, dialogsActivity, baseFragment, user, str, tLObject, tL_error);
            }
        }, 66);
    }

    public /* synthetic */ void lambda$processAttachMenuBot$135(final int i, final DialogsActivity dialogsActivity, final BaseFragment baseFragment, final TLRPC.User user, final String str, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda183
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processAttachMenuBot$134(tLObject, i, dialogsActivity, baseFragment, user, str);
            }
        });
    }

    public /* synthetic */ void lambda$processAttachMenuBot$134(TLObject tLObject, int i, final DialogsActivity dialogsActivity, final BaseFragment baseFragment, final TLRPC.User user, final String str) {
        if (tLObject instanceof TLRPC.TL_boolTrue) {
            MediaDataController.getInstance(i).loadAttachMenuBots(false, true, new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda184
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$processAttachMenuBot$133(dialogsActivity, baseFragment, user, str);
                }
            });
        }
    }

    public /* synthetic */ void lambda$processAttachMenuBot$133(DialogsActivity dialogsActivity, BaseFragment baseFragment, TLRPC.User user, String str) {
        if (dialogsActivity != null) {
            if (baseFragment != null) {
                baseFragment.dismissCurrentDialog();
            }
            for (int i = 0; i < this.visibleDialogs.size(); i++) {
                if (((Dialog) this.visibleDialogs.get(i)).isShowing()) {
                    ((Dialog) this.visibleDialogs.get(i)).dismiss();
                }
            }
            this.visibleDialogs.clear();
            lambda$runLinkRequest$106(dialogsActivity);
            return;
        }
        if (baseFragment instanceof ChatActivity) {
            ((ChatActivity) baseFragment).openAttachBotLayout(user.f1825id, str, true);
        }
    }

    private void openForumFromLink(final long j, final Integer num, final String str, final Integer num2, final Runnable runnable, final int i, final int i2) {
        if (num == null) {
            Bundle bundle = new Bundle();
            bundle.putLong("chat_id", -j);
            lambda$runLinkRequest$106(TopicsFragment.getTopicsOrChat(this, bundle));
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        TLRPC.TL_channels_getMessages tL_channels_getMessages = new TLRPC.TL_channels_getMessages();
        tL_channels_getMessages.channel = MessagesController.getInstance(this.currentAccount).getInputChannel(-j);
        tL_channels_getMessages.f1706id.add(num);
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_channels_getMessages, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda118
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$openForumFromLink$140(num, num2, j, runnable, str, i, i2, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$openForumFromLink$140(final Integer num, final Integer num2, final long j, final Runnable runnable, final String str, final int i, final int i2, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda147
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openForumFromLink$139(tLObject, num, num2, j, runnable, str, i, i2);
            }
        });
    }

    public /* synthetic */ void lambda$openForumFromLink$139(TLObject tLObject, Integer num, Integer num2, long j, Runnable runnable, String str, int i, int i2) {
        TLRPC.Message message;
        if (tLObject instanceof TLRPC.messages_Messages) {
            ArrayList arrayList = ((TLRPC.messages_Messages) tLObject).messages;
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                if (arrayList.get(i3) != null && ((TLRPC.Message) arrayList.get(i3)).f1686id == num.intValue()) {
                    message = (TLRPC.Message) arrayList.get(i3);
                    break;
                }
            }
            message = null;
        } else {
            message = null;
        }
        if (message != null) {
            int i4 = this.currentAccount;
            Integer numValueOf = Integer.valueOf(message.f1686id);
            int i5 = this.currentAccount;
            runCommentRequest(i4, null, numValueOf, null, Long.valueOf(MessageObject.getTopicId(i5, message, MessagesController.getInstance(i5).isForum(message))), num2, MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-j)), runnable, str, i, i2);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putLong("chat_id", -j);
        lambda$runLinkRequest$106(TopicsFragment.getTopicsOrChat(this, bundle));
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:99:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.List findContacts(java.lang.String r19, java.lang.String r20, boolean r21) {
        /*
            Method dump skipped, instruction units count: 344
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.findContacts(java.lang.String, java.lang.String, boolean):java.util.List");
    }

    public void checkAppUpdate(boolean z) {
        checkAppUpdate(z, null);
    }

    public void checkAppUpdate(final boolean z, final Browser.Progress progress) {
        if (z || Math.abs(System.currentTimeMillis() - ExteraConfig.updateScheduleTimestamp) >= TimeUnit.HOURS.toMillis(1L)) {
            final int i = this.currentAccount;
            UpdaterUtils.getAppUpdate(new Utilities.Callback2() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda39
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    this.f$0.lambda$checkAppUpdate$143(z, i, progress, (TLRPC.TL_help_appUpdate) obj, (TLRPC.TL_error) obj2);
                }
            });
            if (progress != null) {
                progress.init();
            }
        }
    }

    public /* synthetic */ void lambda$checkAppUpdate$143(final boolean z, final int i, final Browser.Progress progress, final TLRPC.TL_help_appUpdate tL_help_appUpdate, final TLRPC.TL_error tL_error) {
        SharedConfig.lastUpdateCheckTime = System.currentTimeMillis();
        SharedConfig.saveConfig();
        if (tL_help_appUpdate != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda121
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$checkAppUpdate$141(tL_help_appUpdate, z, i, progress);
                }
            });
        } else if (tL_error != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda122
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchActivity.m15076$r8$lambda$gBEc5dCIAYsjvefDy6ryIIar4(progress, tL_error);
                }
            });
        }
    }

    public /* synthetic */ void lambda$checkAppUpdate$141(TLRPC.TL_help_appUpdate tL_help_appUpdate, boolean z, int i, Browser.Progress progress) {
        TLRPC.TL_help_appUpdate tL_help_appUpdate2 = SharedConfig.pendingAppUpdate;
        if (tL_help_appUpdate2 == null || !tL_help_appUpdate2.version.equals(tL_help_appUpdate.version) || z || tL_help_appUpdate.can_not_skip) {
            if (SharedConfig.setNewAppVersionAvailable(tL_help_appUpdate)) {
                if (tL_help_appUpdate.can_not_skip) {
                    showUpdateActivity(i, tL_help_appUpdate, false);
                    return;
                } else {
                    ApplicationLoader.applicationLoaderInstance.showUpdateAppPopup(this, tL_help_appUpdate, i);
                    NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.appUpdateAvailable, new Object[0]);
                    return;
                }
            }
            BaseFragment lastFragment = getLastFragment();
            if (SharedConfig.pendingAppUpdate != null) {
                SharedConfig.pendingAppUpdate = null;
                SharedConfig.saveConfig();
                NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.appUpdateAvailable, new Object[0]);
            }
            if (progress != null) {
                progress.end();
            }
            if (lastFragment == null || !z) {
                return;
            }
            BulletinFactory.m1246of(lastFragment).createSimpleBulletin(C2888R.raw.chats_infotip, LocaleController.getString(C2888R.string.YourVersionIsLatest)).show();
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$g-BEc-5dCIAYsjvefDy6ryIIar4 */
    public static /* synthetic */ void m15076$r8$lambda$gBEc5dCIAYsjvefDy6ryIIar4(Browser.Progress progress, TLRPC.TL_error tL_error) {
        if (progress != null) {
            progress.end();
        }
        BaseFragment lastFragment = getLastFragment();
        if (lastFragment != null) {
            BulletinFactory.m1246of(lastFragment).showForError(tL_error);
        }
    }

    public Dialog showAlertDialog(AlertDialog.Builder builder) {
        try {
            final AlertDialog alertDialogShow = builder.show();
            alertDialogShow.setCanceledOnTouchOutside(true);
            alertDialogShow.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda95
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f$0.lambda$showAlertDialog$149(alertDialogShow, dialogInterface);
                }
            });
            this.visibleDialogs.add(alertDialogShow);
            return alertDialogShow;
        } catch (Exception e) {
            FileLog.m1136e(e);
            return null;
        }
    }

    public /* synthetic */ void lambda$showAlertDialog$149(AlertDialog alertDialog, DialogInterface dialogInterface) {
        if (alertDialog != null) {
            if (alertDialog == this.localeDialog) {
                ActionBarLayout actionBarLayout = this.actionBarLayout;
                BaseFragment lastFragment = actionBarLayout == null ? null : actionBarLayout.getLastFragment();
                try {
                    String str = LocaleController.getInstance().getCurrentLocaleInfo().shortName;
                    if (lastFragment != null) {
                        BulletinFactory.m1246of(lastFragment).createSimpleBulletin(C2888R.raw.msg_translate, getStringForLanguageAlert(str.equals("en") ? this.englishLocaleStrings : this.systemLocaleStrings, "ChangeLanguageLater", C2888R.string.ChangeLanguageLater)).setDuration(5000).show();
                    } else {
                        BulletinFactory.m1245of(Bulletin.BulletinWindow.make(this), null).createSimpleBulletin(C2888R.raw.msg_translate, getStringForLanguageAlert(str.equals("en") ? this.englishLocaleStrings : this.systemLocaleStrings, "ChangeLanguageLater", C2888R.string.ChangeLanguageLater)).setDuration(5000).show();
                    }
                } catch (Exception e) {
                    FileLog.m1136e(e);
                }
                this.localeDialog = null;
            } else if (alertDialog == this.proxyErrorDialog) {
                SharedPreferences.Editor editorEdit = MessagesController.getGlobalMainSettings().edit();
                editorEdit.putBoolean("proxy_enabled", false);
                editorEdit.putBoolean("proxy_enabled_calls", false);
                editorEdit.apply();
                ConnectionsManager.setProxySettings(false, _UrlKt.FRAGMENT_ENCODE_SET, 1080, _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET);
                NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.proxySettingsChanged, new Object[0]);
                this.proxyErrorDialog = null;
            }
        }
        this.visibleDialogs.remove(alertDialog);
    }

    public void showBulletin(Function function) {
        BaseFragment baseFragment;
        ArrayList arrayList = layerFragmentsStack;
        if (!arrayList.isEmpty()) {
            baseFragment = (BaseFragment) arrayList.get(arrayList.size() - 1);
        } else {
            ArrayList arrayList2 = rightFragmentsStack;
            if (!arrayList2.isEmpty()) {
                baseFragment = (BaseFragment) arrayList2.get(arrayList2.size() - 1);
            } else {
                ArrayList arrayList3 = mainFragmentsStack;
                baseFragment = !arrayList3.isEmpty() ? (BaseFragment) arrayList3.get(arrayList3.size() - 1) : null;
            }
        }
        if (BulletinFactory.canShowBulletin(baseFragment)) {
            ((Bulletin) function.apply(BulletinFactory.m1246of(baseFragment))).show();
        }
    }

    public void setNavigateToPremiumBot(boolean z) {
        this.navigateToPremiumBot = z;
    }

    public void setNavigateToPremiumGiftCallback(Runnable runnable) {
        this.navigateToPremiumGiftCallback = runnable;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onNewIntent(Intent intent) throws Throwable {
        super.onNewIntent(intent);
        handleIntent(intent, true, false, false, null, true, true);
    }

    public void onNewIntent(Intent intent, Browser.Progress progress) {
        super.onNewIntent(intent);
        handleIntent(intent, true, false, false, progress, true, false);
    }

    @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
    public boolean canSelectStories() {
        ArrayList arrayList = this.photoPathsArray;
        return (arrayList != null && arrayList.size() == 1) || this.videoPath != null;
    }

    /* JADX WARN: Removed duplicated region for block: B:86:0x0083 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0084  */
    @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean didSelectStories(org.telegram.p029ui.DialogsActivity r17) {
        /*
            Method dump skipped, instruction units count: 221
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.didSelectStories(org.telegram.ui.DialogsActivity):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:362:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:487:0x04b9  */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r12v15, types: [org.telegram.ui.ActionBar.INavigationLayout] */
    /* JADX WARN: Type inference failed for: r13v12 */
    /* JADX WARN: Type inference failed for: r13v13, types: [int] */
    /* JADX WARN: Type inference failed for: r13v41 */
    /* JADX WARN: Type inference failed for: r14v17 */
    /* JADX WARN: Type inference failed for: r14v18, types: [boolean] */
    /* JADX WARN: Type inference failed for: r14v24 */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v5, types: [boolean] */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v21 */
    /* JADX WARN: Type inference failed for: r5v36 */
    /* JADX WARN: Type inference failed for: r5v37 */
    /* JADX WARN: Type inference failed for: r5v38 */
    /* JADX WARN: Type inference failed for: r5v39 */
    /* JADX WARN: Type inference failed for: r5v40 */
    /* JADX WARN: Type inference failed for: r5v41 */
    /* JADX WARN: Type inference failed for: r5v42 */
    /* JADX WARN: Type inference failed for: r5v43 */
    /* JADX WARN: Type inference failed for: r5v44 */
    /* JADX WARN: Type inference failed for: r5v45 */
    /* JADX WARN: Type inference failed for: r5v46 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v27 */
    /* JADX WARN: Type inference failed for: r7v28 */
    /* JADX WARN: Type inference failed for: r7v29 */
    /* JADX WARN: Type inference failed for: r7v42 */
    /* JADX WARN: Type inference failed for: r7v43 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4, types: [int] */
    @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean didSelectDialogs(final org.telegram.p029ui.DialogsActivity r43, final java.util.ArrayList r44, final java.lang.CharSequence r45, final boolean r46, boolean r47, int r48, final int r49, org.telegram.p029ui.TopicsFragment r50) {
        /*
            Method dump skipped, instruction units count: 1365
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.didSelectDialogs(org.telegram.ui.DialogsActivity, java.util.ArrayList, java.lang.CharSequence, boolean, boolean, int, int, org.telegram.ui.TopicsFragment):boolean");
    }

    public /* synthetic */ void lambda$didSelectDialogs$150(int i, DialogsActivity dialogsActivity, boolean z, ArrayList arrayList, Uri uri, AlertDialog alertDialog, long j) {
        if (j != 0) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("scrollToTopOnResume", true);
            if (!AndroidUtilities.isTablet()) {
                NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
            }
            if (DialogObject.isUserDialog(j)) {
                bundle.putLong("user_id", j);
            } else {
                bundle.putLong("chat_id", -j);
            }
            ChatActivity chatActivity = new ChatActivity(bundle);
            chatActivity.setOpenImport();
            getActionBarLayout().presentFragment(chatActivity, dialogsActivity != null || z, dialogsActivity == null, true, false);
        } else {
            this.documentsUrisArray = arrayList;
            if (arrayList == null) {
                this.documentsUrisArray = new ArrayList();
            }
            this.documentsUrisArray.add(0, uri);
            openDialogsToSend(true);
        }
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    public /* synthetic */ void lambda$didSelectDialogs$151(ChatActivity chatActivity, ArrayList arrayList, int i, int i2, CharSequence charSequence, int i3, boolean z, TLRPC.User user, boolean z2, int i4, long j, boolean z3, long j2) {
        MessageObject messageObject;
        TLRPC.TL_forumTopic tL_forumTopicFindTopic;
        if (chatActivity != null) {
            getActionBarLayout().presentFragment(chatActivity, true, false, true, false);
        }
        AccountInstance accountInstance = AccountInstance.getInstance(UserConfig.selectedAccount);
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            long j3 = ((MessagesStorage.TopicKey) arrayList.get(i5)).dialogId;
            long j4 = ((MessagesStorage.TopicKey) arrayList.get(i5)).topicId;
            if (j4 == 0 || (tL_forumTopicFindTopic = accountInstance.getMessagesController().getTopicsController().findTopic(-j3, j4)) == null || tL_forumTopicFindTopic.topicStartMessage == null) {
                messageObject = null;
            } else {
                messageObject = new MessageObject(accountInstance.getCurrentAccount(), tL_forumTopicFindTopic.topicStartMessage, false, false);
                messageObject.isTopicMainMessage = true;
            }
            MessageObject messageObject2 = messageObject;
            SendMessagesHelper.SendMessageParams sendMessageParamsM1187of = SendMessagesHelper.SendMessageParams.m1187of(user, j3, messageObject2, messageObject2, (TLRPC.ReplyMarkup) null, (HashMap<String, String>) null, z2, i4 != 0 ? i4 : i, i2);
            if (TextUtils.isEmpty(charSequence)) {
                sendMessageParamsM1187of.effect_id = j;
            }
            sendMessageParamsM1187of.invert_media = z3;
            SendMessagesHelper.getInstance(i3).sendMessage(sendMessageParamsM1187of);
            if (!TextUtils.isEmpty(charSequence)) {
                SendMessagesHelper.prepareSendingText(accountInstance, charSequence.toString(), j3, z, i4 != 0 ? i4 : i, i2, j);
            }
        }
    }

    private void onFinish() {
        Runnable runnable = this.lockRunnable;
        if (runnable != null) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
            this.lockRunnable = null;
        }
        if (this.finished) {
            return;
        }
        this.finished = true;
        int i = this.currentAccount;
        if (i != -1) {
            NotificationCenter.getInstance(i).removeObserver(this, NotificationCenter.chatSwitchedForum);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.appDidLogout);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.openBoostForUsersDialog);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.mainUserInfoChanged);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.attachMenuBotsDidLoad);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.didUpdateConnectionState);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.needShowAlert);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.wasUnableToFindCurrentLocation);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.openArticle);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.hasNewContactsToImport);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.needShowPlayServicesAlert);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileLoaded);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileLoadFailed);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.historyImportProgressChanged);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.groupCallUpdated);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.stickersImportComplete);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.currentUserShowLimitReachedDialog);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        }
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.needShowAlert);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didSetNewWallpapper);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.suggestedLangpack);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.reloadInterface);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didSetNewTheme);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.needSetDayNightTheme);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.needCheckSystemBarColors);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.closeOtherAppActivities);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didSetPasscode);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.screenStateChanged);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.showBulletin);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.requestPermissions);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.billingConfirmPurchaseError);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.tlSchemeParseException);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.pluginMenuItemsUpdated);
        Utilities.Callback callback = this.onPowerSaverCallback;
        if (callback != null) {
            LiteMode.removeOnPowerSaverAppliedListener(callback);
        }
    }

    public void onPowerSaver(boolean z) {
        BaseFragment lastFragment;
        if (this.actionBarLayout == null || !z || LiteMode.getPowerSaverLevel() >= 100 || (lastFragment = this.actionBarLayout.getLastFragment()) == null || (lastFragment instanceof LiteModeSettingsActivity)) {
            return;
        }
        int batteryLevel = LiteMode.getBatteryLevel();
        BulletinFactory.m1246of(lastFragment).createSimpleBulletin(new BatteryDrawable(batteryLevel / 100.0f, -1, lastFragment.getThemedColor(Theme.key_dialogSwipeRemove), 1.3f), LocaleController.getString(C2888R.string.LowPowerEnabledTitle), LocaleController.formatString("LowPowerEnabledSubtitle", C2888R.string.LowPowerEnabledSubtitle, String.format("%d%%", Integer.valueOf(batteryLevel))), LocaleController.getString(C2888R.string.Disable), new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda90
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onPowerSaver$152();
            }
        }).setDuration(5000).show();
    }

    public /* synthetic */ void lambda$onPowerSaver$152() {
        lambda$runLinkRequest$106(new LiteModeSettingsActivity());
    }

    /* JADX INFO: renamed from: presentFragment */
    public void lambda$runLinkRequest$106(BaseFragment baseFragment) {
        getActionBarLayout().presentFragment(baseFragment);
    }

    public boolean presentFragment(BaseFragment baseFragment, boolean z, boolean z2) {
        return getActionBarLayout().presentFragment(baseFragment, z, z2, true, false);
    }

    public INavigationLayout getActionBarLayout() {
        ActionBarLayout actionBarLayout = this.actionBarLayout;
        if (this.sheetFragmentsStack.isEmpty()) {
            return actionBarLayout;
        }
        return (INavigationLayout) this.sheetFragmentsStack.get(r0.size() - 1);
    }

    public INavigationLayout getLayersActionBarLayout() {
        return this.layersActionBarLayout;
    }

    public INavigationLayout getRightActionBarLayout() {
        return this.rightActionBarLayout;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        VoIPService sharedInstance;
        if (SharedConfig.passcodeHash.length() != 0 && SharedConfig.lastPauseTime != 0) {
            SharedConfig.lastPauseTime = 0;
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1133d("reset lastPauseTime onActivityResult");
            }
            UserConfig.getInstance(this.currentAccount).saveConfig(false);
        }
        if (i == 105) {
            boolean zCanDrawOverlays = Settings.canDrawOverlays(this);
            ApplicationLoader.canDrawOverlays = zCanDrawOverlays;
            if (zCanDrawOverlays) {
                GroupCallActivity groupCallActivity = GroupCallActivity.groupCallInstance;
                if (groupCallActivity != null) {
                    groupCallActivity.dismissInternal();
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda34
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onActivityResult$153();
                    }
                }, 200L);
                return;
            }
            return;
        }
        super.onActivityResult(i, i2, intent);
        if (i == 520) {
            if (i2 != -1 || (sharedInstance = VoIPService.getSharedInstance()) == null) {
                return;
            }
            VideoCapturerDevice.mediaProjectionPermissionResultData = intent;
            sharedInstance.createCaptureDevice(true);
            return;
        }
        if (i == 140) {
            LocationController.getInstance(this.currentAccount).startFusedLocationRequest(i2 == -1);
            return;
        }
        if (i == 521) {
            Utilities.Callback callback = this.webviewShareAPIDoneListener;
            if (callback != null) {
                callback.run(Boolean.valueOf(i2 == -1));
                this.webviewShareAPIDoneListener = null;
                return;
            }
            return;
        }
        ThemeEditorView themeEditorView = ThemeEditorView.getInstance();
        if (themeEditorView != null) {
            themeEditorView.onActivityResult(i, i2, intent);
        }
        ActionBarLayout actionBarLayout = this.actionBarLayout;
        if (actionBarLayout != null && actionBarLayout.getFragmentStack().size() != 0) {
            BaseFragment baseFragment = this.actionBarLayout.getFragmentStack().get(this.actionBarLayout.getFragmentStack().size() - 1);
            baseFragment.onActivityResultFragment(i, i2, intent);
            if (baseFragment.getLastStoryViewer() != null) {
                baseFragment.getLastStoryViewer().onActivityResult(i, i2, intent);
            }
        }
        if (AndroidUtilities.isTablet()) {
            ActionBarLayout actionBarLayout2 = this.rightActionBarLayout;
            if (actionBarLayout2 != null && actionBarLayout2.getFragmentStack().size() != 0) {
                this.rightActionBarLayout.getFragmentStack().get(this.rightActionBarLayout.getFragmentStack().size() - 1).onActivityResultFragment(i, i2, intent);
            }
            ActionBarLayout actionBarLayout3 = this.layersActionBarLayout;
            if (actionBarLayout3 != null && actionBarLayout3.getFragmentStack().size() != 0) {
                this.layersActionBarLayout.getFragmentStack().get(this.layersActionBarLayout.getFragmentStack().size() - 1).onActivityResultFragment(i, i2, intent);
            }
        }
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.onActivityResultReceived, Integer.valueOf(i), Integer.valueOf(i2), intent);
    }

    public /* synthetic */ void lambda$onActivityResult$153() {
        GroupCallPip.clearForce();
        GroupCallPip.updateVisibility(this);
    }

    public void whenWebviewShareAPIDone(Utilities.Callback callback) {
        this.webviewShareAPIDoneListener = callback;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (checkPermissionsResult(i, strArr, iArr)) {
            ApplicationLoader applicationLoader = ApplicationLoader.applicationLoaderInstance;
            if (applicationLoader == null || !applicationLoader.checkRequestPermissionResult(i, strArr, iArr)) {
                if (this.actionBarLayout.getFragmentStack().size() != 0) {
                    this.actionBarLayout.getFragmentStack().get(this.actionBarLayout.getFragmentStack().size() - 1).onRequestPermissionsResultFragment(i, strArr, iArr);
                }
                if (AndroidUtilities.isTablet()) {
                    ActionBarLayout actionBarLayout = this.rightActionBarLayout;
                    if (actionBarLayout != null && actionBarLayout.getFragmentStack().size() != 0) {
                        this.rightActionBarLayout.getFragmentStack().get(this.rightActionBarLayout.getFragmentStack().size() - 1).onRequestPermissionsResultFragment(i, strArr, iArr);
                    }
                    ActionBarLayout actionBarLayout2 = this.layersActionBarLayout;
                    if (actionBarLayout2 != null && actionBarLayout2.getFragmentStack().size() != 0) {
                        this.layersActionBarLayout.getFragmentStack().get(this.layersActionBarLayout.getFragmentStack().size() - 1).onRequestPermissionsResultFragment(i, strArr, iArr);
                    }
                }
                VoIPFragment.onRequestPermissionsResult(i, strArr, iArr);
                StoryRecorder.onRequestPermissionsResult(i, strArr, iArr);
                NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.onRequestPermissionResultReceived, Integer.valueOf(i), strArr, iArr);
                if (this.requestedPermissions.get(i, -1) >= 0) {
                    int i2 = this.requestedPermissions.get(i, -1);
                    this.requestedPermissions.delete(i);
                    NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.permissionsGranted, Integer.valueOf(i2));
                }
                NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.activityPermissionsGranted, Integer.valueOf(i), strArr, iArr);
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        isResumed = false;
        this.pipActivityHandler.onPause();
        PluginsController.getInstance().executeOnAppEvent("app_pause");
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.stopAllHeavyOperations, 4096);
        ApplicationLoader.mainInterfacePaused = true;
        final int i = this.currentAccount;
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.m15099$r8$lambda$wpsae_RVACZ1EUYixtpxEGc2sw(i);
            }
        });
        onPasscodePause();
        this.actionBarLayout.onPause();
        if (AndroidUtilities.isTablet()) {
            ActionBarLayout actionBarLayout = this.rightActionBarLayout;
            if (actionBarLayout != null) {
                actionBarLayout.onPause();
            }
            ActionBarLayout actionBarLayout2 = this.layersActionBarLayout;
            if (actionBarLayout2 != null) {
                actionBarLayout2.onPause();
            }
        }
        PasscodeViewDialog passcodeViewDialog = this.passcodeDialog;
        if (passcodeViewDialog != null) {
            passcodeViewDialog.passcodeView.onPause();
        }
        Iterator it = this.overlayPasscodeViews.iterator();
        while (it.hasNext()) {
            ((PasscodeView) it.next()).onPause();
        }
        ConnectionsManager.getInstance(this.currentAccount).setAppPaused(!(ApplicationLoader.applicationLoaderInstance != null ? r1.onPause() : false), false);
        if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().onPause();
        }
        StoryRecorder.onPause();
        if (VoIPFragment.getInstance() != null) {
            VoIPFragment.onPause();
        }
        SpoilerEffect2.pause(true);
    }

    /* JADX INFO: renamed from: $r8$lambda$wpsae_RVACZ1EUYixtpx-EGc2sw */
    public static /* synthetic */ void m15099$r8$lambda$wpsae_RVACZ1EUYixtpxEGc2sw(int i) {
        ApplicationLoader.mainInterfacePausedStageQueue = true;
        ApplicationLoader.mainInterfacePausedStageQueueTime = 0L;
        if (VoIPService.getSharedInstance() == null) {
            MessagesController.getInstance(i).ignoreSetOnline = false;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        this.isStarted = true;
        this.pipActivityHandler.onStart();
        Browser.bindCustomTabsService(this);
        ApplicationLoader.mainInterfaceStopped = false;
        GroupCallPip.updateVisibility(this);
        GroupCallActivity groupCallActivity = GroupCallActivity.groupCallInstance;
        if (groupCallActivity != null) {
            groupCallActivity.onResume();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        this.isStarted = false;
        this.pipActivityHandler.onStop();
        Browser.unbindCustomTabsService(this);
        ApplicationLoader.mainInterfaceStopped = true;
        GroupCallPip.updateVisibility(this);
        GroupCallActivity groupCallActivity = GroupCallActivity.groupCallInstance;
        if (groupCallActivity != null) {
            groupCallActivity.onPause();
        }
    }

    @Override // android.app.Activity
    public boolean onPictureInPictureRequested() {
        this.pipActivityHandler.onPictureInPictureRequested();
        return super.onPictureInPictureRequested();
    }

    @Override // android.app.Activity
    public void setPictureInPictureParams(PictureInPictureParams pictureInPictureParams) {
        super.setPictureInPictureParams(pictureInPictureParams);
        this.pipActivityHandler.setPictureInPictureParams(pictureInPictureParams);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onPictureInPictureModeChanged(boolean z, Configuration configuration) {
        super.onPictureInPictureModeChanged(z, configuration);
        this.pipActivityHandler.onPictureInPictureModeChanged(z, configuration);
        if (z || this.isStarted) {
            return;
        }
        if (RTMPStreamPipOverlay.isVisible()) {
            RTMPStreamPipOverlay.dismiss();
        }
        if (LiveStoryPipOverlay.isVisible()) {
            LiveStoryPipOverlay.dismiss();
        }
        if (PipVideoOverlay.isVisible()) {
            PipVideoOverlay.dismiss();
        }
        GroupCallActivity.onLeaveClick(this, null, false, true);
        if (PhotoViewer.getPipInstance() != null) {
            PhotoViewer.getPipInstance().destroyPhotoViewer();
        }
        if (PhotoViewer.hasInstance()) {
            PhotoViewer.getInstance().closePhoto(false, false);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        FrameMetricsOverlayView frameMetricsOverlayView;
        isActive = false;
        unregisterReceiver(this.batteryReceiver);
        if (Build.VERSION.SDK_INT >= 31) {
            MonetUtils.unregisterReceiver(this);
        }
        PluginsController.getInstance().executeOnAppEvent("app_stop");
        if (PhotoViewer.getPipInstance() != null) {
            PhotoViewer.getPipInstance().destroyPhotoViewer();
        }
        if (PhotoViewer.hasInstance()) {
            PhotoViewer.getInstance().destroyPhotoViewer();
        }
        if (SecretMediaViewer.hasInstance()) {
            SecretMediaViewer.getInstance().destroyPhotoViewer();
        }
        if (ArticleViewer.hasInstance()) {
            ArticleViewer.getInstance().destroyArticleViewer();
        }
        if (ContentPreviewViewer.hasInstance()) {
            ContentPreviewViewer.getInstance().destroy();
        }
        GroupCallActivity groupCallActivity = GroupCallActivity.groupCallInstance;
        if (groupCallActivity != null) {
            groupCallActivity.dismissInternal();
        }
        PipRoundVideoView pipRoundVideoView = PipRoundVideoView.getInstance();
        MediaController.getInstance().setBaseActivity(this, false);
        MediaController.getInstance().setFeedbackView(this.feedbackView, false);
        if (pipRoundVideoView != null) {
            pipRoundVideoView.close(false);
        }
        Theme.destroyResources();
        EmbedBottomSheet embedBottomSheet = EmbedBottomSheet.getInstance();
        if (embedBottomSheet != null) {
            embedBottomSheet.destroy();
        }
        ThemeEditorView themeEditorView = ThemeEditorView.getInstance();
        if (themeEditorView != null) {
            themeEditorView.destroy();
        }
        for (int i = 0; i < this.visibleDialogs.size(); i++) {
            try {
                if (((Dialog) this.visibleDialogs.get(i)).isShowing()) {
                    ((Dialog) this.visibleDialogs.get(i)).dismiss();
                }
            } catch (Exception e) {
                FileLog.m1136e(e);
            }
        }
        this.visibleDialogs.clear();
        try {
            if (this.onGlobalLayoutListener != null) {
                getWindow().getDecorView().getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
            }
        } catch (Exception e2) {
            FileLog.m1136e(e2);
        }
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 35) {
            Bulletin.removeDelegate(this.frameLayout);
        }
        if (i2 >= 34) {
            if (LaunchActivity$$ExternalSyntheticApiModelOutline1.m1287m(this.onBackAnimationCallback)) {
                getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(LaunchActivity$$ExternalSyntheticApiModelOutline0.m1286m(this.onBackAnimationCallback));
            }
        } else if (i2 >= 33 && LaunchActivity$$ExternalSyntheticApiModelOutline2.m1288m(this.onBackAnimationCallback)) {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(C0042xf5eeb9b9.m2m(this.onBackInvokedCallback));
        }
        if (instance == this) {
            clearFragments();
            instance = null;
        }
        super.onDestroy();
        onFinish();
        FloatingDebugController.onDestroy();
        IconPickerController.onDestroy();
        FlagSecureReason flagSecureReason = this.flagSecureReason;
        if (flagSecureReason != null) {
            flagSecureReason.detach();
        }
        if (i2 < 24 || (frameMetricsOverlayView = this.frameMetricsOverlayView) == null) {
            return;
        }
        frameMetricsOverlayView.detach();
    }

    @Override // android.app.Activity
    protected void onUserLeaveHint() {
        this.pipActivityHandler.onUserLeaveHint();
        Iterator it = this.onUserLeaveHintListeners.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        ActionBarLayout actionBarLayout = this.actionBarLayout;
        if (actionBarLayout != null) {
            actionBarLayout.onUserLeaveHint();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        MessageObject playingMessageObject;
        super.onResume();
        isResumed = true;
        this.pipActivityHandler.onResume();
        Runnable runnable = onResumeStaticCallback;
        if (runnable != null) {
            runnable.run();
            onResumeStaticCallback = null;
        }
        if (Theme.selectedAutoNightType == 3) {
            Theme.checkAutoNightThemeConditions();
        }
        checkWasMutedByAdmin(true);
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.startAllHeavyOperations, 4096);
        MediaController mediaController = MediaController.getInstance();
        ViewGroup view = this.actionBarLayout.getView();
        this.feedbackView = view;
        mediaController.setFeedbackView(view, true);
        ApplicationLoader.mainInterfacePaused = false;
        MessagesController.getInstance(this.currentAccount).sortDialogs(null);
        showLanguageAlert(false);
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.$r8$lambda$F7f6vHKNNwoTYIPYaTC6mqncaUU();
            }
        });
        checkFreeDiscSpace(0);
        MediaController.checkGallery();
        onPasscodeResume();
        PasscodeViewDialog passcodeViewDialog = this.passcodeDialog;
        if (passcodeViewDialog == null || passcodeViewDialog.passcodeView.getVisibility() != 0) {
            this.actionBarLayout.onResume();
            if (AndroidUtilities.isTablet()) {
                ActionBarLayout actionBarLayout = this.rightActionBarLayout;
                if (actionBarLayout != null) {
                    actionBarLayout.onResume();
                }
                ActionBarLayout actionBarLayout2 = this.layersActionBarLayout;
                if (actionBarLayout2 != null) {
                    actionBarLayout2.onResume();
                }
            }
        } else {
            this.actionBarLayout.dismissDialogs();
            if (AndroidUtilities.isTablet()) {
                ActionBarLayout actionBarLayout3 = this.rightActionBarLayout;
                if (actionBarLayout3 != null) {
                    actionBarLayout3.dismissDialogs();
                }
                ActionBarLayout actionBarLayout4 = this.layersActionBarLayout;
                if (actionBarLayout4 != null) {
                    actionBarLayout4.dismissDialogs();
                }
            }
            this.passcodeDialog.passcodeView.onResume();
            Iterator it = this.overlayPasscodeViews.iterator();
            while (it.hasNext()) {
                ((PasscodeView) it.next()).onResume();
            }
        }
        ConnectionsManager.getInstance(this.currentAccount).setAppPaused(false, false);
        updateCurrentConnectionState();
        if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().onResume();
        }
        StoryRecorder.onResume();
        if (PipRoundVideoView.getInstance() != null && MediaController.getInstance().isMessagePaused() && (playingMessageObject = MediaController.getInstance().getPlayingMessageObject()) != null) {
            MediaController.getInstance().seekToProgress(playingMessageObject, playingMessageObject.audioProgress);
        }
        if (UserConfig.getInstance(UserConfig.selectedAccount).unacceptedTermsOfService != null) {
            int i = UserConfig.selectedAccount;
            showTosActivity(i, UserConfig.getInstance(i).unacceptedTermsOfService);
        } else {
            TLRPC.TL_help_appUpdate tL_help_appUpdate = SharedConfig.pendingAppUpdate;
            if (tL_help_appUpdate != null && tL_help_appUpdate.can_not_skip) {
                showUpdateActivity(UserConfig.selectedAccount, SharedConfig.pendingAppUpdate, true);
            }
        }
        checkAppUpdate(false);
        RemoteUtils.init();
        ApplicationLoader.canDrawOverlays = Settings.canDrawOverlays(this);
        if (VoIPFragment.getInstance() != null) {
            VoIPFragment.onResume();
        }
        invalidateTabletMode();
        SpoilerEffect2.pause(false);
        ApplicationLoader applicationLoader = ApplicationLoader.applicationLoaderInstance;
        if (applicationLoader != null) {
            applicationLoader.onResume();
        }
        PluginsController.getInstance().executeOnAppEvent("app_resume");
        Runnable runnable2 = whenResumed;
        if (runnable2 != null) {
            runnable2.run();
            whenResumed = null;
        }
        if (MessagesController.getInstance(this.currentAccount).hasSetupEmailSuggestion()) {
            MessagesController.getInstance(this.currentAccount).checkPromoInfo(true);
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda36
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onResume$156();
            }
        }, 300L);
    }

    public static /* synthetic */ void $r8$lambda$F7f6vHKNNwoTYIPYaTC6mqncaUU() {
        ApplicationLoader.mainInterfacePausedStageQueue = false;
        ApplicationLoader.mainInterfacePausedStageQueueTime = System.currentTimeMillis();
    }

    public /* synthetic */ void lambda$onResume$156() {
        checkSystemBarColors(true);
    }

    private void invalidateTabletMode() {
        long topicId;
        Boolean wasTablet = AndroidUtilities.getWasTablet();
        if (wasTablet == null) {
            return;
        }
        AndroidUtilities.resetWasTabletFlag();
        if (wasTablet.booleanValue() != AndroidUtilities.isTablet()) {
            int i = 0;
            long j = 0;
            if (wasTablet.booleanValue()) {
                ArrayList arrayList = mainFragmentsStack;
                ArrayList arrayList2 = rightFragmentsStack;
                arrayList.addAll(arrayList2);
                ArrayList arrayList3 = layerFragmentsStack;
                arrayList.addAll(arrayList3);
                arrayList2.clear();
                arrayList3.clear();
                topicId = 0;
            } else {
                ArrayList arrayList4 = mainFragmentsStack;
                ArrayList arrayList5 = new ArrayList(arrayList4);
                arrayList4.clear();
                rightFragmentsStack.clear();
                layerFragmentsStack.clear();
                int size = arrayList5.size();
                int i2 = 0;
                long dialogId = 0;
                topicId = 0;
                while (i2 < size) {
                    Object obj = arrayList5.get(i2);
                    i2++;
                    BaseFragment baseFragment = (BaseFragment) obj;
                    if (!(baseFragment instanceof MainTabsActivity)) {
                        if (baseFragment instanceof DialogsActivity) {
                            DialogsActivity dialogsActivity = (DialogsActivity) baseFragment;
                            if (!dialogsActivity.isMainDialogList() || dialogsActivity.isArchive()) {
                            }
                        }
                        if (baseFragment instanceof ChatActivity) {
                            ChatActivity chatActivity = (ChatActivity) baseFragment;
                            if (!chatActivity.isInScheduleMode()) {
                                rightFragmentsStack.add(baseFragment);
                                if (dialogId == 0) {
                                    dialogId = chatActivity.getDialogId();
                                    topicId = chatActivity.getTopicId();
                                }
                            }
                        }
                        layerFragmentsStack.add(baseFragment);
                    }
                    mainFragmentsStack.add(baseFragment);
                }
                j = dialogId;
            }
            setupActionBarLayout();
            this.actionBarLayout.rebuildFragments(1);
            if (AndroidUtilities.isTablet()) {
                this.rightActionBarLayout.rebuildFragments(1);
                this.layersActionBarLayout.rebuildFragments(1);
                ArrayList arrayList6 = mainFragmentsStack;
                int size2 = arrayList6.size();
                while (i < size2) {
                    Object obj2 = arrayList6.get(i);
                    i++;
                    Object dialogsActivity2 = (BaseFragment) obj2;
                    if (dialogsActivity2 instanceof MainTabsActivity) {
                        dialogsActivity2 = ((MainTabsActivity) dialogsActivity2).getDialogsActivity();
                    }
                    if (dialogsActivity2 instanceof DialogsActivity) {
                        DialogsActivity dialogsActivity3 = (DialogsActivity) dialogsActivity2;
                        if (dialogsActivity3.isMainDialogList()) {
                            dialogsActivity3.setOpenedDialogId(j, topicId);
                        }
                    }
                }
            }
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        AndroidUtilities.checkDisplaySize(this, configuration);
        AndroidUtilities.setPreferredMaxRefreshRate(getWindow());
        super.onConfigurationChanged(configuration);
        this.pipActivityHandler.onConfigurationChanged(configuration);
        checkLayout();
        PipRoundVideoView pipRoundVideoView = PipRoundVideoView.getInstance();
        if (pipRoundVideoView != null) {
            pipRoundVideoView.onConfigurationChanged();
        }
        EmbedBottomSheet embedBottomSheet = EmbedBottomSheet.getInstance();
        if (embedBottomSheet != null) {
            embedBottomSheet.onConfigurationChanged(configuration);
        }
        BoostPagerBottomSheet boostPagerBottomSheet = BoostPagerBottomSheet.getInstance();
        if (boostPagerBottomSheet != null) {
            boostPagerBottomSheet.onConfigurationChanged(configuration);
        }
        PhotoViewer pipInstance = PhotoViewer.getPipInstance();
        if (pipInstance != null) {
            pipInstance.onConfigurationChanged(configuration);
        }
        ThemeEditorView themeEditorView = ThemeEditorView.getInstance();
        if (themeEditorView != null) {
            themeEditorView.onConfigurationChanged();
        }
        if (Theme.selectedAutoNightType == 3) {
            Theme.checkAutoNightThemeConditions();
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onMultiWindowModeChanged(boolean z) {
        AndroidUtilities.isInMultiwindow = z;
        checkLayout();
        super.onMultiWindowModeChanged(z);
    }

    /* JADX WARN: Removed duplicated region for block: B:1036:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:715:0x0467  */
    /* JADX WARN: Removed duplicated region for block: B:787:0x065a  */
    /* JADX WARN: Removed duplicated region for block: B:788:0x0660  */
    /* JADX WARN: Removed duplicated region for block: B:792:0x0667  */
    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void didReceivedNotification(int r22, final int r23, java.lang.Object... r24) {
        /*
            Method dump skipped, instruction units count: 2676
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.didReceivedNotification(int, int, java.lang.Object[]):void");
    }

    /* JADX INFO: renamed from: $r8$lambda$Je9PwtyxvRiIenDrJitaYuc-2fo */
    public static /* synthetic */ void m15053$r8$lambda$Je9PwtyxvRiIenDrJitaYuc2fo(int i, AlertDialog alertDialog, int i2) {
        ArrayList arrayList = mainFragmentsStack;
        if (arrayList.isEmpty()) {
            return;
        }
        MessagesController.getInstance(i).openByUserName("spambot", (BaseFragment) arrayList.get(arrayList.size() - 1), 1);
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$18 */
    /* JADX INFO: loaded from: classes6.dex */
    class C590318 extends ClickableSpan {
        C590318() {
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            LaunchActivity.this.getActionBarLayout().presentFragment(new PremiumPreviewFragment("gift"));
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setUnderlineText(false);
        }
    }

    public /* synthetic */ void lambda$didReceivedNotification$158(AlertDialog alertDialog, int i) {
        MessagesController.getInstance(this.currentAccount).performLogout(2);
    }

    public /* synthetic */ void lambda$didReceivedNotification$160(final HashMap map, final int i, AlertDialog alertDialog, int i2) {
        ArrayList arrayList = mainFragmentsStack;
        if (!arrayList.isEmpty() && AndroidUtilities.isMapsInstalled((BaseFragment) arrayList.get(arrayList.size() - 1))) {
            LocationActivity locationActivity = new LocationActivity(0);
            locationActivity.setDelegate(new LocationActivity.LocationActivityDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda86
                @Override // org.telegram.ui.LocationActivity.LocationActivityDelegate
                public final void didSelectLocation(TLRPC.MessageMedia messageMedia, int i3, boolean z, int i4, long j) {
                    LaunchActivity.$r8$lambda$YKnwL4LumEMJb6IVt8RzJPo4I5M(map, i, messageMedia, i3, z, i4, j);
                }
            });
            lambda$runLinkRequest$106(locationActivity);
        }
    }

    public static /* synthetic */ void $r8$lambda$YKnwL4LumEMJb6IVt8RzJPo4I5M(HashMap map, int i, TLRPC.MessageMedia messageMedia, int i2, boolean z, int i3, long j) {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            MessageObject messageObject = (MessageObject) ((Map.Entry) it.next()).getValue();
            SendMessagesHelper.getInstance(i).sendMessage(SendMessagesHelper.SendMessageParams.m1179of(messageMedia, messageObject.getDialogId(), messageObject, (MessageObject) null, (TLRPC.ReplyMarkup) null, (HashMap<String, String>) null, z, i3, 0));
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$19 */
    /* JADX INFO: loaded from: classes6.dex */
    class C590419 extends AnimatorListenerAdapter {
        final /* synthetic */ RLottieImageView val$darkThemeView;
        final /* synthetic */ RLottieDrawable val$drawable;
        final /* synthetic */ boolean val$toDark;
        final /* synthetic */ RLottieDrawable val$transitionDrawable;

        C590419(RLottieDrawable rLottieDrawable, RLottieImageView rLottieImageView, RLottieDrawable rLottieDrawable2, boolean z) {
            rLottieDrawable = rLottieDrawable;
            rLottieImageView = rLottieImageView;
            rLottieDrawable = rLottieDrawable2;
            z = z;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            RLottieImageView rLottieImageView;
            LaunchActivity.this.rippleAbove = null;
            LaunchActivity.this.drawerLayoutContainer.invalidate();
            LaunchActivity.this.themeSwitchImageView.invalidate();
            LaunchActivity.this.themeSwitchImageView.setImageDrawable(null);
            LaunchActivity.this.themeSwitchImageView.setVisibility(8);
            RLottieDrawable rLottieDrawable = rLottieDrawable;
            if (rLottieDrawable != null) {
                rLottieDrawable.stop();
            }
            LaunchActivity.this.themeSwitchSunView.setImageDrawable(null);
            LaunchActivity.this.themeSwitchSunView.setVisibility(8);
            RLottieImageView rLottieImageView2 = rLottieImageView;
            if (rLottieImageView2 != null) {
                rLottieImageView2.setImageDrawable(rLottieDrawable);
                rLottieImageView.invalidate();
            }
            NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.themeAccentListUpdated, new Object[0]);
            if (!z && (rLottieImageView = rLottieImageView) != null) {
                rLottieImageView.setVisibility(0);
            }
            DialogsActivity.switchingTheme = false;
        }
    }

    public /* synthetic */ void lambda$didReceivedNotification$164(ValueAnimator valueAnimator) {
        this.frameLayout.invalidate();
    }

    public /* synthetic */ void lambda$didReceivedNotification$165() {
        if (this.isNavigationBarColorFrozen) {
            this.isNavigationBarColorFrozen = false;
            checkSystemBarColors(false, true);
        }
    }

    public /* synthetic */ void lambda$didReceivedNotification$167(final Theme.ThemeInfo themeInfo, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda50
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$didReceivedNotification$166(tLObject, themeInfo);
            }
        });
    }

    public /* synthetic */ void lambda$didReceivedNotification$166(TLObject tLObject, Theme.ThemeInfo themeInfo) {
        if (tLObject instanceof TLRPC.TL_wallPaper) {
            TLRPC.TL_wallPaper tL_wallPaper = (TLRPC.TL_wallPaper) tLObject;
            this.loadingThemeInfo = themeInfo;
            this.loadingThemeWallpaperName = FileLoader.getAttachFileName(tL_wallPaper.document);
            this.loadingThemeWallpaper = tL_wallPaper;
            FileLoader.getInstance(themeInfo.account).loadFile(tL_wallPaper.document, tL_wallPaper, 1, 1);
            return;
        }
        onThemeLoadFinish();
    }

    public /* synthetic */ void lambda$didReceivedNotification$169(Theme.ThemeInfo themeInfo, File file) {
        themeInfo.createBackground(file, themeInfo.pathToWallpaper);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda93
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$didReceivedNotification$168();
            }
        });
    }

    public /* synthetic */ void lambda$didReceivedNotification$168() {
        if (this.loadingTheme == null) {
            return;
        }
        File file = new File(ApplicationLoader.getFilesDirFixed(), "remote" + this.loadingTheme.f1808id + ".attheme");
        TLRPC.TL_theme tL_theme = this.loadingTheme;
        Theme.ThemeInfo themeInfoApplyThemeFile = Theme.applyThemeFile(file, tL_theme.title, tL_theme, true);
        if (themeInfoApplyThemeFile != null) {
            lambda$runLinkRequest$106(new ThemePreviewActivity(themeInfoApplyThemeFile, true, 0, false, false));
        }
        onThemeLoadFinish();
    }

    private void invalidateCachedViews(View view) {
        if (view.getLayerType() != 0) {
            view.invalidate();
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                invalidateCachedViews(viewGroup.getChildAt(i));
            }
        }
    }

    private void checkWasMutedByAdmin(boolean z) {
        ChatObject.Call call;
        long j;
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        boolean z2 = false;
        if (sharedInstance != null && (call = sharedInstance.groupCall) != null) {
            boolean z3 = this.wasMutedByAdminRaisedHand;
            TLRPC.InputPeer groupCallPeer = sharedInstance.getGroupCallPeer();
            if (groupCallPeer != null) {
                j = groupCallPeer.user_id;
                if (j == 0) {
                    long j2 = groupCallPeer.chat_id;
                    if (j2 == 0) {
                        j2 = groupCallPeer.channel_id;
                    }
                    j = -j2;
                }
            } else {
                j = UserConfig.getInstance(this.currentAccount).clientUserId;
            }
            TLRPC.GroupCallParticipant groupCallParticipant = (TLRPC.GroupCallParticipant) call.participants.get(j);
            boolean z4 = (groupCallParticipant == null || groupCallParticipant.can_self_unmute || !groupCallParticipant.muted) ? false : true;
            if (z4 && groupCallParticipant.raise_hand_rating != 0) {
                z2 = true;
            }
            this.wasMutedByAdminRaisedHand = z2;
            if (z || !z3 || z2 || z4 || GroupCallActivity.groupCallInstance != null) {
                return;
            }
            showVoiceChatTooltip(38);
            return;
        }
        this.wasMutedByAdminRaisedHand = false;
    }

    private void showVoiceChatTooltip(int i) {
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance == null || mainFragmentsStack.isEmpty() || sharedInstance.groupCall == null) {
            return;
        }
        TLRPC.Chat chat = sharedInstance.getChat();
        BaseFragment currentVisibleFragment = this.actionBarLayout.getFragmentStack().get(this.actionBarLayout.getFragmentStack().size() - 1);
        if (currentVisibleFragment instanceof MainTabsActivity) {
            currentVisibleFragment = ((MainTabsActivity) currentVisibleFragment).getCurrentVisibleFragment();
        }
        UndoView undoView = null;
        if (currentVisibleFragment instanceof ChatActivity) {
            ChatActivity chatActivity = (ChatActivity) currentVisibleFragment;
            if (chat != null && chatActivity.getDialogId() == (-chat.f1660id)) {
                chat = null;
            }
            undoView = chatActivity.getUndoView();
        } else if (currentVisibleFragment instanceof DialogsActivity) {
            undoView = ((DialogsActivity) currentVisibleFragment).getUndoView();
        } else if (currentVisibleFragment instanceof ProfileActivity) {
            undoView = ((ProfileActivity) currentVisibleFragment).getUndoView();
        }
        if (undoView != null) {
            undoView.showWithAction(0L, i, chat);
        }
        if (i != 38 || VoIPService.getSharedInstance() == null) {
            return;
        }
        VoIPService.getSharedInstance().playAllowTalkSound();
    }

    private String getStringForLanguageAlert(HashMap map, String str, int i) {
        String str2 = (String) map.get(str);
        return str2 == null ? LocaleController.getString(str, i) : str2;
    }

    private void openThemeAccentPreview(TLRPC.TL_theme tL_theme, TLRPC.TL_wallPaper tL_wallPaper, Theme.ThemeInfo themeInfo) {
        int i = themeInfo.lastAccentId;
        Theme.ThemeAccent themeAccentCreateNewAccent = themeInfo.createNewAccent(tL_theme, this.currentAccount);
        themeInfo.prevAccentId = themeInfo.currentAccentId;
        themeInfo.setCurrentAccentId(themeAccentCreateNewAccent.f1877id);
        themeAccentCreateNewAccent.pattern = tL_wallPaper;
        lambda$runLinkRequest$106(new ThemePreviewActivity(themeInfo, i != themeInfo.lastAccentId, 0, false, false));
    }

    private void onThemeLoadFinish() {
        AlertDialog alertDialog = this.loadingThemeProgressDialog;
        if (alertDialog != null) {
            try {
                alertDialog.dismiss();
            } finally {
                this.loadingThemeProgressDialog = null;
            }
        }
        this.loadingThemeWallpaperName = null;
        this.loadingThemeWallpaper = null;
        this.loadingThemeInfo = null;
        this.loadingThemeFileName = null;
        this.loadingTheme = null;
    }

    private void checkFreeDiscSpace(final int i) {
        staticInstanceForAlerts = this;
        AutoDeleteMediaTask.run();
        SharedConfig.checkLogsToDelete();
        if ((Build.VERSION.SDK_INT < 26 || i != 0) && !this.checkFreeDiscSpaceShown) {
            Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$checkFreeDiscSpace$175(i);
                }
            }, 2000L);
        }
    }

    public /* synthetic */ void lambda$checkFreeDiscSpace$175(int i) {
        File directory;
        if (UserConfig.getInstance(this.currentAccount).isClientActivated()) {
            try {
                SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
                if ((((i == 2 || i == 1) && Math.abs(this.alreadyShownFreeDiscSpaceAlertForced - System.currentTimeMillis()) > 240000) || Math.abs(globalMainSettings.getLong("last_space_check", 0L) - System.currentTimeMillis()) >= 259200000) && (directory = FileLoader.getDirectory(4)) != null) {
                    StatFs statFs = new StatFs(directory.getAbsolutePath());
                    long availableBlocksLong = statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
                    if (i > 0 || availableBlocksLong < 52428800) {
                        if (i > 0) {
                            this.alreadyShownFreeDiscSpaceAlertForced = System.currentTimeMillis();
                        }
                        globalMainSettings.edit().putLong("last_space_check", System.currentTimeMillis()).apply();
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda94
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$checkFreeDiscSpace$174();
                            }
                        });
                    }
                }
            } catch (Throwable unused) {
            }
        }
    }

    public /* synthetic */ void lambda$checkFreeDiscSpace$174() {
        if (this.checkFreeDiscSpaceShown) {
            return;
        }
        try {
            Dialog dialogCreateFreeSpaceDialog = AlertsCreator.createFreeSpaceDialog(this);
            dialogCreateFreeSpaceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda126
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f$0.lambda$checkFreeDiscSpace$173(dialogInterface);
                }
            });
            this.checkFreeDiscSpaceShown = true;
            dialogCreateFreeSpaceDialog.show();
        } catch (Throwable unused) {
        }
    }

    public /* synthetic */ void lambda$checkFreeDiscSpace$173(DialogInterface dialogInterface) {
        this.checkFreeDiscSpaceShown = false;
    }

    public static void checkFreeDiscSpaceStatic(int i) {
        LaunchActivity launchActivity = staticInstanceForAlerts;
        if (launchActivity != null) {
            launchActivity.checkFreeDiscSpace(i);
        }
    }

    private void showLanguageAlertInternal(LocaleController.LocaleInfo localeInfo, LocaleController.LocaleInfo localeInfo2, String str) {
        try {
            this.loadingLocaleDialog = false;
            LocaleController.LocaleInfo localeInfo3 = localeInfo;
            boolean z = localeInfo3.builtIn || LocaleController.getInstance().isCurrentLocalLocale();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getStringForLanguageAlert(this.systemLocaleStrings, "ChooseYourLanguage", C2888R.string.ChooseYourLanguage));
            builder.setSubtitle(getStringForLanguageAlert(this.englishLocaleStrings, "ChooseYourLanguage", C2888R.string.ChooseYourLanguage));
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(1);
            final LanguageCell[] languageCellArr = new LanguageCell[2];
            String stringForLanguageAlert = getStringForLanguageAlert(this.systemLocaleStrings, "English", C2888R.string.English);
            LocaleController.LocaleInfo[] localeInfoArr = {z ? localeInfo3 : localeInfo2, z ? localeInfo2 : localeInfo3};
            if (!z) {
                localeInfo3 = localeInfo2;
            }
            final LocaleController.LocaleInfo[] localeInfoArr2 = {localeInfo3};
            int i = 0;
            while (i < 2) {
                LanguageCell languageCell = new LanguageCell(this);
                languageCellArr[i] = languageCell;
                LocaleController.LocaleInfo localeInfo4 = localeInfoArr[i];
                languageCell.setLanguage(localeInfo4, localeInfo4 == localeInfo2 ? stringForLanguageAlert : null, true);
                languageCellArr[i].setTag(Integer.valueOf(i));
                languageCellArr[i].setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 2));
                languageCellArr[i].setLanguageSelected(i == 0, false);
                linearLayout.addView(languageCellArr[i], LayoutHelper.createLinear(-1, 50));
                languageCellArr[i].setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda152
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        LaunchActivity.m15036$r8$lambda$052BhCMQBoR8kb8iCxO30XxOl0(localeInfoArr2, languageCellArr, view);
                    }
                });
                i++;
            }
            LanguageCell languageCell2 = new LanguageCell(this);
            languageCell2.setValue(getStringForLanguageAlert(this.systemLocaleStrings, "ChooseYourLanguageOther", C2888R.string.ChooseYourLanguageOther), getStringForLanguageAlert(this.englishLocaleStrings, "ChooseYourLanguageOther", C2888R.string.ChooseYourLanguageOther));
            languageCell2.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 2));
            languageCell2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda153
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$showLanguageAlertInternal$177(view);
                }
            });
            linearLayout.addView(languageCell2, LayoutHelper.createLinear(-1, 50));
            builder.setView(linearLayout);
            builder.setNegativeButton(LocaleController.getString(C2888R.string.f1606OK), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda154
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i2) {
                    this.f$0.lambda$showLanguageAlertInternal$178(localeInfoArr2, alertDialog, i2);
                }
            });
            this.localeDialog = showAlertDialog(builder);
            MessagesController.getGlobalMainSettings().edit().putString("language_showed2", str).apply();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$0-52BhCMQBoR8kb8iCxO30XxOl0 */
    public static /* synthetic */ void m15036$r8$lambda$052BhCMQBoR8kb8iCxO30XxOl0(LocaleController.LocaleInfo[] localeInfoArr, LanguageCell[] languageCellArr, View view) {
        Integer num = (Integer) view.getTag();
        localeInfoArr[0] = ((LanguageCell) view).getCurrentLocale();
        int i = 0;
        while (i < languageCellArr.length) {
            languageCellArr[i].setLanguageSelected(i == num.intValue(), true);
            i++;
        }
    }

    public /* synthetic */ void lambda$showLanguageAlertInternal$177(View view) {
        this.localeDialog = null;
        lambda$runLinkRequest$106(new LanguageSelectActivity());
        for (int i = 0; i < this.visibleDialogs.size(); i++) {
            if (((Dialog) this.visibleDialogs.get(i)).isShowing()) {
                ((Dialog) this.visibleDialogs.get(i)).dismiss();
            }
        }
        this.visibleDialogs.clear();
    }

    public /* synthetic */ void lambda$showLanguageAlertInternal$178(LocaleController.LocaleInfo[] localeInfoArr, AlertDialog alertDialog, int i) {
        LocaleController.getInstance().applyLanguage(localeInfoArr[0], true, false, this.currentAccount);
        rebuildAllFragments(true);
    }

    public void drawRippleAbove(Canvas canvas, View view) {
        View view2;
        if (view == null || (view2 = this.rippleAbove) == null || view2.getBackground() == null) {
            return;
        }
        if (this.tempLocation == null) {
            this.tempLocation = new int[2];
        }
        this.rippleAbove.getLocationInWindow(this.tempLocation);
        int[] iArr = this.tempLocation;
        int i = iArr[0];
        int i2 = iArr[1];
        view.getLocationInWindow(iArr);
        int[] iArr2 = this.tempLocation;
        int i3 = i - iArr2[0];
        int i4 = i2 - iArr2[1];
        canvas.save();
        canvas.translate(i3, i4);
        this.rippleAbove.getBackground().draw(canvas);
        canvas.restore();
    }

    private void showLanguageAlert(boolean z) {
        String str;
        char c;
        LocaleController.LocaleInfo localeInfo;
        if (UserConfig.getInstance(this.currentAccount).isClientActivated()) {
            try {
                if (!this.loadingLocaleDialog && !ApplicationLoader.mainInterfacePaused) {
                    String string = MessagesController.getGlobalMainSettings().getString("language_showed2", _UrlKt.FRAGMENT_ENCODE_SET);
                    final String str2 = MessagesController.getInstance(this.currentAccount).suggestedLangCode;
                    if (!z && string.equals(str2)) {
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.m1133d("alert already showed for " + string);
                            return;
                        }
                        return;
                    }
                    final LocaleController.LocaleInfo[] localeInfoArr = new LocaleController.LocaleInfo[2];
                    char c2 = 0;
                    String str3 = str2.contains("-") ? str2.split("-")[0] : str2;
                    if ("in".equals(str3)) {
                        str = "id";
                    } else if ("iw".equals(str3)) {
                        str = "he";
                    } else {
                        str = "jw".equals(str3) ? "jv" : null;
                    }
                    int i = 0;
                    while (true) {
                        if (i >= LocaleController.getInstance().languages.size()) {
                            c = c2;
                            break;
                        }
                        LocaleController.LocaleInfo localeInfo2 = LocaleController.getInstance().languages.get(i);
                        c = c2;
                        if (localeInfo2.shortName.equals("en")) {
                            localeInfoArr[c] = localeInfo2;
                        }
                        if (localeInfo2.shortName.replace("_", "-").equals(str2) || localeInfo2.shortName.equals(str3) || localeInfo2.shortName.equals(str)) {
                            localeInfoArr[1] = localeInfo2;
                        }
                        if (localeInfoArr[c] != null && localeInfoArr[1] != null) {
                            break;
                        }
                        i++;
                        c2 = c;
                    }
                    LocaleController.LocaleInfo localeInfo3 = localeInfoArr[c];
                    if (localeInfo3 != null && (localeInfo = localeInfoArr[1]) != null && localeInfo3 != localeInfo) {
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.m1133d("show lang alert for " + localeInfoArr[c].getKey() + " and " + localeInfoArr[1].getKey());
                        }
                        this.systemLocaleStrings = null;
                        this.englishLocaleStrings = null;
                        this.loadingLocaleDialog = true;
                        TLRPC.TL_langpack_getStrings tL_langpack_getStrings = new TLRPC.TL_langpack_getStrings();
                        tL_langpack_getStrings.lang_code = localeInfoArr[1].getLangCode();
                        tL_langpack_getStrings.keys.add("English");
                        tL_langpack_getStrings.keys.add("ChooseYourLanguage");
                        tL_langpack_getStrings.keys.add("ChooseYourLanguageOther");
                        tL_langpack_getStrings.keys.add("ChangeLanguageLater");
                        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_langpack_getStrings, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda40
                            @Override // org.telegram.tgnet.RequestDelegate
                            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                                this.f$0.lambda$showLanguageAlert$180(localeInfoArr, str2, tLObject, tL_error);
                            }
                        }, 8);
                        TLRPC.TL_langpack_getStrings tL_langpack_getStrings2 = new TLRPC.TL_langpack_getStrings();
                        tL_langpack_getStrings2.lang_code = localeInfoArr[c].getLangCode();
                        tL_langpack_getStrings2.keys.add("English");
                        tL_langpack_getStrings2.keys.add("ChooseYourLanguage");
                        tL_langpack_getStrings2.keys.add("ChooseYourLanguageOther");
                        tL_langpack_getStrings2.keys.add("ChangeLanguageLater");
                        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_langpack_getStrings2, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda41
                            @Override // org.telegram.tgnet.RequestDelegate
                            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                                this.f$0.lambda$showLanguageAlert$182(localeInfoArr, str2, tLObject, tL_error);
                            }
                        }, 8);
                    }
                }
            } catch (Exception e) {
                FileLog.m1136e(e);
            }
        }
    }

    public /* synthetic */ void lambda$showLanguageAlert$180(final LocaleController.LocaleInfo[] localeInfoArr, final String str, TLObject tLObject, TLRPC.TL_error tL_error) {
        final HashMap map = new HashMap();
        if (tLObject instanceof Vector) {
            Vector vector = (Vector) tLObject;
            for (int i = 0; i < vector.objects.size(); i++) {
                TLRPC.LangPackString langPackString = (TLRPC.LangPackString) vector.objects.get(i);
                map.put(langPackString.key, langPackString.value);
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda132
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showLanguageAlert$179(map, localeInfoArr, str);
            }
        });
    }

    public /* synthetic */ void lambda$showLanguageAlert$179(HashMap map, LocaleController.LocaleInfo[] localeInfoArr, String str) {
        this.systemLocaleStrings = map;
        if (this.englishLocaleStrings != null) {
            showLanguageAlertInternal(localeInfoArr[1], localeInfoArr[0], str);
        }
    }

    public /* synthetic */ void lambda$showLanguageAlert$182(final LocaleController.LocaleInfo[] localeInfoArr, final String str, TLObject tLObject, TLRPC.TL_error tL_error) {
        final HashMap map = new HashMap();
        if (tLObject instanceof Vector) {
            Vector vector = (Vector) tLObject;
            for (int i = 0; i < vector.objects.size(); i++) {
                TLRPC.LangPackString langPackString = (TLRPC.LangPackString) vector.objects.get(i);
                map.put(langPackString.key, langPackString.value);
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda99
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showLanguageAlert$181(map, localeInfoArr, str);
            }
        });
    }

    public /* synthetic */ void lambda$showLanguageAlert$181(HashMap map, LocaleController.LocaleInfo[] localeInfoArr, String str) {
        this.englishLocaleStrings = map;
        if (this.systemLocaleStrings != null) {
            showLanguageAlertInternal(localeInfoArr[1], localeInfoArr[0], str);
        }
    }

    private void onPasscodePause() {
        int i;
        if (this.lockRunnable != null) {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1133d("cancel lockRunnable onPasscodePause");
            }
            AndroidUtilities.cancelRunOnUIThread(this.lockRunnable);
            this.lockRunnable = null;
        }
        if (SharedConfig.passcodeHash.length() != 0) {
            SharedConfig.lastPauseTime = (int) (SystemClock.elapsedRealtime() / 1000);
            RunnableC590620 runnableC590620 = new Runnable() { // from class: org.telegram.ui.LaunchActivity.20
                RunnableC590620() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    if (LaunchActivity.this.lockRunnable == this) {
                        if (AndroidUtilities.needShowPasscode(true)) {
                            if (BuildVars.LOGS_ENABLED) {
                                FileLog.m1133d("lock app");
                            }
                            LaunchActivity.this.showPasscodeActivity(true, false, -1, -1, null, null);
                            try {
                                NotificationsController.getInstance(UserConfig.selectedAccount).showNotifications();
                            } catch (Exception e) {
                                FileLog.m1136e(e);
                            }
                        } else if (BuildVars.LOGS_ENABLED) {
                            FileLog.m1133d("didn't pass lock check");
                        }
                        LaunchActivity.this.lockRunnable = null;
                    }
                }
            };
            this.lockRunnable = runnableC590620;
            if (SharedConfig.appLocked || (i = SharedConfig.autoLockIn) == Integer.MAX_VALUE) {
                AndroidUtilities.runOnUIThread(runnableC590620, 1000L);
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1133d("schedule app lock in 1000");
                }
            } else if (i != 0) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1133d("schedule app lock in " + ((((long) SharedConfig.autoLockIn) * 1000) + 1000));
                }
                AndroidUtilities.runOnUIThread(this.lockRunnable, (((long) SharedConfig.autoLockIn) * 1000) + 1000);
            }
        } else {
            SharedConfig.lastPauseTime = 0;
        }
        SharedConfig.saveConfig();
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$20 */
    /* JADX INFO: loaded from: classes6.dex */
    class RunnableC590620 implements Runnable {
        RunnableC590620() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (LaunchActivity.this.lockRunnable == this) {
                if (AndroidUtilities.needShowPasscode(true)) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1133d("lock app");
                    }
                    LaunchActivity.this.showPasscodeActivity(true, false, -1, -1, null, null);
                    try {
                        NotificationsController.getInstance(UserConfig.selectedAccount).showNotifications();
                    } catch (Exception e) {
                        FileLog.m1136e(e);
                    }
                } else if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1133d("didn't pass lock check");
                }
                LaunchActivity.this.lockRunnable = null;
            }
        }
    }

    public void addOverlayPasscodeView(PasscodeView passcodeView) {
        this.overlayPasscodeViews.add(passcodeView);
    }

    public void removeOverlayPasscodeView(PasscodeView passcodeView) {
        this.overlayPasscodeViews.remove(passcodeView);
    }

    private void onPasscodeResume() {
        if (this.lockRunnable != null) {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1133d("cancel lockRunnable onPasscodeResume");
            }
            AndroidUtilities.cancelRunOnUIThread(this.lockRunnable);
            this.lockRunnable = null;
        }
        if (AndroidUtilities.needShowPasscode(true)) {
            showPasscodeActivity(true, false, -1, -1, null, null);
        }
        if (SharedConfig.lastPauseTime != 0) {
            SharedConfig.lastPauseTime = 0;
            SharedConfig.saveConfig();
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1133d("reset lastPauseTime onPasscodeResume");
            }
        }
    }

    private void updateCurrentConnectionState() {
        int i;
        String str;
        if (this.actionBarLayout == null) {
            return;
        }
        int connectionState = ConnectionsManager.getInstance(this.currentAccount).getConnectionState();
        this.currentConnectionState = connectionState;
        if (connectionState == 2) {
            i = C2888R.string.WaitingForNetwork;
            str = "WaitingForNetwork";
        } else if (connectionState == 5) {
            i = C2888R.string.Updating;
            str = "Updating";
        } else if (connectionState == 4) {
            i = C2888R.string.ConnectingToProxyWithDots;
            str = "ConnectingToProxyWithDots";
        } else if (connectionState == 1) {
            i = C2888R.string.Connecting;
            str = "Connecting";
        } else {
            i = 0;
            str = null;
        }
        this.actionBarLayout.setTitleOverlayText(str, i, (connectionState == 1 || connectionState == 4) ? new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda37
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateCurrentConnectionState$183();
            }
        } : null);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$updateCurrentConnectionState$183() {
        /*
            r2 = this;
            boolean r0 = org.telegram.messenger.AndroidUtilities.isTablet()
            if (r0 == 0) goto L1b
            java.util.ArrayList r0 = org.telegram.p029ui.LaunchActivity.layerFragmentsStack
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L30
            int r1 = r0.size()
            int r1 = r1 + (-1)
            java.lang.Object r0 = r0.get(r1)
            org.telegram.ui.ActionBar.BaseFragment r0 = (org.telegram.p029ui.ActionBar.BaseFragment) r0
            goto L31
        L1b:
            java.util.ArrayList r0 = org.telegram.p029ui.LaunchActivity.mainFragmentsStack
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L30
            int r1 = r0.size()
            int r1 = r1 + (-1)
            java.lang.Object r0 = r0.get(r1)
            org.telegram.ui.ActionBar.BaseFragment r0 = (org.telegram.p029ui.ActionBar.BaseFragment) r0
            goto L31
        L30:
            r0 = 0
        L31:
            boolean r1 = r0 instanceof org.telegram.p029ui.ProxyListActivity
            if (r1 != 0) goto L42
            boolean r0 = r0 instanceof org.telegram.p029ui.ProxySettingsActivity
            if (r0 == 0) goto L3a
            goto L42
        L3a:
            org.telegram.ui.ProxyListActivity r0 = new org.telegram.ui.ProxyListActivity
            r0.<init>()
            r2.lambda$runLinkRequest$106(r0)
        L42:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.lambda$updateCurrentConnectionState$183():void");
    }

    public void hideVisibleActionMode() {
        ActionMode actionMode = this.visibleActionMode;
        if (actionMode == null) {
            return;
        }
        actionMode.finish();
    }

    /* JADX WARN: Removed duplicated region for block: B:83:0x00a5  */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onSaveInstanceState(android.os.Bundle r7) {
        /*
            Method dump skipped, instruction units count: 296
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.LaunchActivity.onSaveInstanceState(android.os.Bundle):void");
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (onBackPressed(true)) {
            if (AndroidUtilities.isTablet()) {
                ActionBarLayout actionBarLayout = this.layersActionBarLayout;
                if (actionBarLayout != null && actionBarLayout.getView().getVisibility() == 0) {
                    this.layersActionBarLayout.onBackPressed();
                    return;
                }
                ActionBarLayout actionBarLayout2 = this.rightActionBarLayout;
                if (actionBarLayout2 != null && actionBarLayout2.getView().getVisibility() == 0 && !this.rightActionBarLayout.getFragmentStack().isEmpty()) {
                    BaseFragment baseFragment = this.rightActionBarLayout.getFragmentStack().get(this.rightActionBarLayout.getFragmentStack().size() - 1);
                    if (baseFragment.onBackPressed(true)) {
                        baseFragment.finishFragment();
                        return;
                    }
                    return;
                }
                this.actionBarLayout.onBackPressed();
                return;
            }
            this.actionBarLayout.onBackPressed();
        }
    }

    public boolean onBackPressed(boolean z) {
        if (FloatingDebugController.onBackPressed(z) || IconPickerController.onBackPressed(z)) {
            return false;
        }
        PasscodeViewDialog passcodeViewDialog = this.passcodeDialog;
        if (passcodeViewDialog != null && passcodeViewDialog.passcodeView.getVisibility() == 0) {
            if (z) {
                finish();
            }
            return false;
        }
        BottomSheetTabsOverlay bottomSheetTabsOverlay = this.bottomSheetTabsOverlay;
        if (bottomSheetTabsOverlay != null && bottomSheetTabsOverlay.isOpen) {
            if (z) {
                bottomSheetTabsOverlay.onBackPressed();
            }
            return false;
        }
        if (!SearchTagsList.onBackPressedRenameTagAlert(z)) {
            return false;
        }
        if (ContentPreviewViewer.hasInstance() && ContentPreviewViewer.getInstance().isVisible()) {
            if (z) {
                ContentPreviewViewer.getInstance().closeWithMenu();
            }
            return false;
        }
        if (SecretMediaViewer.hasInstance() && SecretMediaViewer.getInstance().isVisible()) {
            if (z) {
                SecretMediaViewer.getInstance().closePhoto(true, false);
            }
            return false;
        }
        if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
            if (z) {
                PhotoViewer.getInstance().closePhoto(true, false);
            }
            return false;
        }
        if (!ArticleViewer.hasInstance() || !ArticleViewer.getInstance().isVisible()) {
            return true;
        }
        if (z) {
            ArticleViewer.getInstance().close(true, false);
        }
        return false;
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        ActionBarLayout actionBarLayout = this.actionBarLayout;
        if (actionBarLayout != null) {
            actionBarLayout.onLowMemory();
            if (AndroidUtilities.isTablet()) {
                ActionBarLayout actionBarLayout2 = this.rightActionBarLayout;
                if (actionBarLayout2 != null) {
                    actionBarLayout2.onLowMemory();
                }
                ActionBarLayout actionBarLayout3 = this.layersActionBarLayout;
                if (actionBarLayout3 != null) {
                    actionBarLayout3.onLowMemory();
                }
            }
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onActionModeStarted(ActionMode actionMode) {
        super.onActionModeStarted(actionMode);
        this.visibleActionMode = actionMode;
        try {
            Menu menu = actionMode.getMenu();
            if (menu != null && !this.actionBarLayout.extendActionMode(menu) && AndroidUtilities.isTablet() && !this.rightActionBarLayout.extendActionMode(menu)) {
                this.layersActionBarLayout.extendActionMode(menu);
            }
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
        if (actionMode.getType() == 1) {
            return;
        }
        this.actionBarLayout.onActionModeStarted(actionMode);
        if (AndroidUtilities.isTablet()) {
            this.rightActionBarLayout.onActionModeStarted(actionMode);
            this.layersActionBarLayout.onActionModeStarted(actionMode);
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onActionModeFinished(ActionMode actionMode) {
        super.onActionModeFinished(actionMode);
        if (this.visibleActionMode == actionMode) {
            this.visibleActionMode = null;
        }
        if (actionMode.getType() == 1) {
            return;
        }
        this.actionBarLayout.onActionModeFinished(actionMode);
        if (AndroidUtilities.isTablet()) {
            this.rightActionBarLayout.onActionModeFinished(actionMode);
            this.layersActionBarLayout.onActionModeFinished(actionMode);
        }
    }

    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    public boolean onPreIme() {
        if (SecretMediaViewer.hasInstance() && SecretMediaViewer.getInstance().isVisible()) {
            SecretMediaViewer.getInstance().closePhoto(true, false);
            return true;
        }
        if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().closePhoto(true, false);
            return true;
        }
        if (!ArticleViewer.hasInstance() || !ArticleViewer.getInstance().isVisible()) {
            return false;
        }
        ArticleViewer.getInstance().close(true, false);
        return true;
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        BaseFragment lastFragment;
        int keyCode = keyEvent.getKeyCode();
        if ((keyEvent.getKeyCode() == 24 || keyEvent.getKeyCode() == 25) && (lastFragment = getLastFragment()) != null && lastFragment.getLastStoryViewer() != null) {
            lastFragment.getLastStoryViewer().dispatchKeyEvent(keyEvent);
            return true;
        }
        if (keyEvent.getKeyCode() == 24 || keyEvent.getKeyCode() == 25) {
            BaseFragment lastFragment2 = getLastFragment();
            if (lastFragment2 instanceof ChatActivity) {
                ChatActivity chatActivity = (ChatActivity) lastFragment2;
                if (chatActivity.getInstantCameraView() != null && chatActivity.getInstantCameraView().isCameraReady()) {
                    chatActivity.getInstantCameraView().onKeyDown(keyCode, keyEvent);
                    return true;
                }
            }
        }
        if (keyEvent.getAction() == 0 && (keyEvent.getKeyCode() == 24 || keyEvent.getKeyCode() == 25)) {
            if (VoIPService.getSharedInstance() != null) {
                if (Build.VERSION.SDK_INT >= 32) {
                    boolean zIsSpeakerMuted = WebRtcAudioTrack.isSpeakerMuted();
                    AudioManager audioManager = (AudioManager) getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND);
                    boolean z = audioManager.getStreamVolume(0) == audioManager.getStreamMinVolume(0) && keyEvent.getKeyCode() == 25;
                    WebRtcAudioTrack.setSpeakerMute(z);
                    if (zIsSpeakerMuted != WebRtcAudioTrack.isSpeakerMuted()) {
                        showVoiceChatTooltip(z ? 42 : 43);
                    }
                }
            } else if (ExteraConfig.unmuteWithVolumeButtons && ((!PhotoViewer.hasInstance() || !PhotoViewer.getInstance().isVisible()) && keyEvent.getRepeatCount() == 0)) {
                ArrayList arrayList = mainFragmentsStack;
                BaseFragment baseFragment = (BaseFragment) arrayList.get(arrayList.size() - 1);
                if ((baseFragment instanceof ChatActivity) && !BaseFragment.hasSheets(baseFragment) && ((ChatActivity) baseFragment).maybePlayVisibleVideo()) {
                    return true;
                }
                if (AndroidUtilities.isTablet()) {
                    ArrayList arrayList2 = rightFragmentsStack;
                    if (!arrayList2.isEmpty()) {
                        BaseFragment baseFragment2 = (BaseFragment) arrayList2.get(arrayList2.size() - 1);
                        if ((baseFragment2 instanceof ChatActivity) && !BaseFragment.hasSheets(baseFragment2) && ((ChatActivity) baseFragment2).maybePlayVisibleVideo()) {
                            return true;
                        }
                    }
                }
            }
        }
        try {
            return super.dispatchKeyEvent(keyEvent);
        } catch (Exception e) {
            FileLog.m1136e(e);
            return false;
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 82 && !SharedConfig.isWaitingForPasscodeEnter) {
            if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
                return super.onKeyUp(i, keyEvent);
            }
            if (ArticleViewer.hasInstance() && ArticleViewer.getInstance().isVisible()) {
                return super.onKeyUp(i, keyEvent);
            }
            if (AndroidUtilities.isTablet()) {
                if (this.layersActionBarLayout.getView().getVisibility() == 0 && !this.layersActionBarLayout.getFragmentStack().isEmpty()) {
                    this.layersActionBarLayout.getView().onKeyUp(i, keyEvent);
                } else if (this.rightActionBarLayout.getView().getVisibility() == 0 && !this.rightActionBarLayout.getFragmentStack().isEmpty()) {
                    this.rightActionBarLayout.getView().onKeyUp(i, keyEvent);
                } else {
                    this.actionBarLayout.getView().onKeyUp(i, keyEvent);
                }
            } else {
                this.actionBarLayout.getView().onKeyUp(i, keyEvent);
            }
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    public boolean needPresentFragment(INavigationLayout iNavigationLayout, INavigationLayout.NavigationParams navigationParams) {
        ActionBarLayout actionBarLayout;
        ActionBarLayout actionBarLayout2;
        ActionBarLayout actionBarLayout3;
        BaseFragment baseFragment = navigationParams.fragment;
        boolean z = navigationParams.removeLast;
        boolean z2 = navigationParams.noAnimation;
        if (ArticleViewer.hasInstance() && ArticleViewer.getInstance().isVisible()) {
            ArticleViewer.getInstance().close(false, true);
        }
        if (AndroidUtilities.isTablet()) {
            if (baseFragment instanceof MainTabsActivity) {
                ActionBarLayout actionBarLayout4 = this.actionBarLayout;
                if (iNavigationLayout != actionBarLayout4) {
                    actionBarLayout4.removeAllFragments();
                    getActionBarLayout().presentFragment(navigationParams.setRemoveLast(z).setNoAnimation(z2).setCheckPresentFromDelegate(false));
                    this.layersActionBarLayout.removeAllFragments();
                    this.layersActionBarLayout.getView().setVisibility(8);
                    if (!this.tabletFullSize) {
                        this.shadowTabletSide.setVisibility(0);
                        if (this.rightActionBarLayout.getFragmentStack().isEmpty()) {
                            this.backgroundTablet.setVisibility(0);
                        }
                    }
                    return false;
                }
            } else if (baseFragment instanceof DialogsActivity) {
                DialogsActivity dialogsActivity = (DialogsActivity) baseFragment;
                if (dialogsActivity.isMainDialogList() && iNavigationLayout != (actionBarLayout = this.actionBarLayout)) {
                    actionBarLayout.removeAllFragments();
                    getActionBarLayout().presentFragment(navigationParams.setRemoveLast(z).setNoAnimation(z2).setCheckPresentFromDelegate(false));
                    this.layersActionBarLayout.removeAllFragments();
                    this.layersActionBarLayout.getView().setVisibility(8);
                    if (!this.tabletFullSize) {
                        this.shadowTabletSide.setVisibility(0);
                        if (this.rightActionBarLayout.getFragmentStack().isEmpty()) {
                            this.backgroundTablet.setVisibility(0);
                        }
                    }
                    return false;
                }
                if (iNavigationLayout == this.actionBarLayout && dialogsActivity.getArguments() != null && dialogsActivity.getArguments().getInt("folderId", 0) == 1) {
                    return true;
                }
            }
            if ((baseFragment instanceof ChatActivity) && !((ChatActivity) baseFragment).isInScheduleMode()) {
                boolean z3 = this.tabletFullSize;
                if ((!z3 && iNavigationLayout == this.rightActionBarLayout) || (z3 && iNavigationLayout == this.actionBarLayout)) {
                    boolean z4 = (z3 && iNavigationLayout == (actionBarLayout3 = this.actionBarLayout) && actionBarLayout3.getFragmentStack().size() == 1) ? false : true;
                    if (!this.layersActionBarLayout.getFragmentStack().isEmpty()) {
                        while (this.layersActionBarLayout.getFragmentStack().size() - 1 > 0) {
                            ActionBarLayout actionBarLayout5 = this.layersActionBarLayout;
                            actionBarLayout5.removeFragmentFromStack(actionBarLayout5.getFragmentStack().get(0));
                        }
                        this.layersActionBarLayout.closeLastFragment(!z2);
                    }
                    if (!z4) {
                        getActionBarLayout().presentFragment(navigationParams.setNoAnimation(z2).setCheckPresentFromDelegate(false));
                    }
                    return z4;
                }
                if (!z3 && iNavigationLayout != (actionBarLayout2 = this.rightActionBarLayout) && actionBarLayout2 != null) {
                    if (actionBarLayout2.getView() != null) {
                        this.rightActionBarLayout.getView().setVisibility(0);
                    }
                    this.backgroundTablet.setVisibility(8);
                    this.rightActionBarLayout.removeAllFragments();
                    this.rightActionBarLayout.presentFragment(navigationParams.setNoAnimation(true).setRemoveLast(z).setCheckPresentFromDelegate(false));
                    if (!this.layersActionBarLayout.getFragmentStack().isEmpty()) {
                        while (this.layersActionBarLayout.getFragmentStack().size() - 1 > 0) {
                            ActionBarLayout actionBarLayout6 = this.layersActionBarLayout;
                            actionBarLayout6.removeFragmentFromStack(actionBarLayout6.getFragmentStack().get(0));
                        }
                        this.layersActionBarLayout.closeLastFragment(!z2);
                    }
                    return false;
                }
                if (z3 && iNavigationLayout != this.actionBarLayout) {
                    getActionBarLayout().presentFragment(navigationParams.setRemoveLast(this.actionBarLayout.getFragmentStack().size() > 1).setNoAnimation(z2).setCheckPresentFromDelegate(false));
                    if (!this.layersActionBarLayout.getFragmentStack().isEmpty()) {
                        while (this.layersActionBarLayout.getFragmentStack().size() - 1 > 0) {
                            ActionBarLayout actionBarLayout7 = this.layersActionBarLayout;
                            actionBarLayout7.removeFragmentFromStack(actionBarLayout7.getFragmentStack().get(0));
                        }
                        this.layersActionBarLayout.closeLastFragment(!z2);
                    }
                    return false;
                }
                ActionBarLayout actionBarLayout8 = this.layersActionBarLayout;
                if (actionBarLayout8 != null && actionBarLayout8.getFragmentStack() != null && !this.layersActionBarLayout.getFragmentStack().isEmpty()) {
                    while (this.layersActionBarLayout.getFragmentStack().size() - 1 > 0) {
                        ActionBarLayout actionBarLayout9 = this.layersActionBarLayout;
                        actionBarLayout9.removeFragmentFromStack(actionBarLayout9.getFragmentStack().get(0));
                    }
                    this.layersActionBarLayout.closeLastFragment(!z2);
                }
                getActionBarLayout().presentFragment(navigationParams.setRemoveLast(this.actionBarLayout.getFragmentStack().size() > 1).setNoAnimation(z2).setCheckPresentFromDelegate(false));
                return false;
            }
            ActionBarLayout actionBarLayout10 = this.layersActionBarLayout;
            if (actionBarLayout10 != null && iNavigationLayout != actionBarLayout10) {
                actionBarLayout10.getView().setVisibility(0);
                int i = 0;
                while (true) {
                    if (i >= 16) {
                        i = -1;
                        break;
                    }
                    if (UserConfig.getInstance(i).isClientActivated()) {
                        break;
                    }
                    i++;
                }
                if ((baseFragment instanceof LoginActivity) && i == -1) {
                    this.backgroundTablet.setVisibility(0);
                    this.shadowTabletSide.setVisibility(8);
                    this.shadowTablet.setBackgroundColor(0);
                } else {
                    this.shadowTablet.setBackgroundColor(2130706432);
                }
                this.layersActionBarLayout.presentFragment(navigationParams.setRemoveLast(z).setNoAnimation(z2).setCheckPresentFromDelegate(false));
                return false;
            }
        }
        return true;
    }

    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    public boolean needAddFragmentToStack(BaseFragment baseFragment, INavigationLayout iNavigationLayout) {
        ActionBarLayout actionBarLayout;
        ActionBarLayout actionBarLayout2;
        if (AndroidUtilities.isTablet()) {
            boolean z = baseFragment instanceof DialogsActivity;
            if (z || (baseFragment instanceof MainTabsActivity)) {
                boolean z2 = iNavigationLayout != this.actionBarLayout;
                if (z2 && z && !((DialogsActivity) baseFragment).isMainDialogList()) {
                    z2 = false;
                }
                if (z2) {
                    this.actionBarLayout.removeAllFragments();
                    this.actionBarLayout.addFragmentToStack(baseFragment);
                    this.layersActionBarLayout.removeAllFragments();
                    this.layersActionBarLayout.getView().setVisibility(8);
                    if (!this.tabletFullSize) {
                        this.shadowTabletSide.setVisibility(0);
                        if (this.rightActionBarLayout.getFragmentStack().isEmpty()) {
                            this.backgroundTablet.setVisibility(0);
                        }
                    }
                    return false;
                }
            } else if ((baseFragment instanceof ChatActivity) && !((ChatActivity) baseFragment).isInScheduleMode()) {
                boolean z3 = this.tabletFullSize;
                if (!z3 && iNavigationLayout != (actionBarLayout2 = this.rightActionBarLayout)) {
                    actionBarLayout2.getView().setVisibility(0);
                    this.backgroundTablet.setVisibility(8);
                    this.rightActionBarLayout.removeAllFragments();
                    this.rightActionBarLayout.addFragmentToStack(baseFragment);
                    if (!this.layersActionBarLayout.getFragmentStack().isEmpty()) {
                        while (this.layersActionBarLayout.getFragmentStack().size() - 1 > 0) {
                            ActionBarLayout actionBarLayout3 = this.layersActionBarLayout;
                            actionBarLayout3.removeFragmentFromStack(actionBarLayout3.getFragmentStack().get(0));
                        }
                        this.layersActionBarLayout.closeLastFragment(true);
                    }
                    return false;
                }
                if (z3 && iNavigationLayout != (actionBarLayout = this.actionBarLayout)) {
                    actionBarLayout.addFragmentToStack(baseFragment);
                    if (!this.layersActionBarLayout.getFragmentStack().isEmpty()) {
                        while (this.layersActionBarLayout.getFragmentStack().size() - 1 > 0) {
                            ActionBarLayout actionBarLayout4 = this.layersActionBarLayout;
                            actionBarLayout4.removeFragmentFromStack(actionBarLayout4.getFragmentStack().get(0));
                        }
                        this.layersActionBarLayout.closeLastFragment(true);
                    }
                    return false;
                }
            } else {
                ActionBarLayout actionBarLayout5 = this.layersActionBarLayout;
                if (iNavigationLayout != actionBarLayout5) {
                    actionBarLayout5.getView().setVisibility(0);
                    int i = 0;
                    while (true) {
                        if (i >= 16) {
                            i = -1;
                            break;
                        }
                        if (UserConfig.getInstance(i).isClientActivated()) {
                            break;
                        }
                        i++;
                    }
                    if ((baseFragment instanceof LoginActivity) && i == -1) {
                        this.backgroundTablet.setVisibility(0);
                        this.shadowTabletSide.setVisibility(8);
                        this.shadowTablet.setBackgroundColor(0);
                    } else {
                        this.shadowTablet.setBackgroundColor(2130706432);
                    }
                    this.layersActionBarLayout.addFragmentToStack(baseFragment);
                    return false;
                }
            }
        }
        return true;
    }

    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    public boolean needCloseLastFragment(INavigationLayout iNavigationLayout) {
        if (AndroidUtilities.isTablet()) {
            if (iNavigationLayout == this.actionBarLayout && iNavigationLayout.getFragmentStack().size() <= 1 && !this.switchingAccount) {
                onFinish();
                finish();
                return false;
            }
            if (iNavigationLayout == this.rightActionBarLayout) {
                if (!this.tabletFullSize) {
                    this.backgroundTablet.setVisibility(0);
                }
            } else if (iNavigationLayout == this.layersActionBarLayout && this.actionBarLayout.getFragmentStack().isEmpty() && this.layersActionBarLayout.getFragmentStack().size() == 1) {
                onFinish();
                finish();
                return false;
            }
        } else if (iNavigationLayout.getFragmentStack().size() <= 1) {
            onFinish();
            finish();
            return false;
        }
        return true;
    }

    public void rebuildAllFragments(boolean z) {
        ActionBarLayout actionBarLayout = this.layersActionBarLayout;
        if (actionBarLayout != null) {
            actionBarLayout.rebuildAllFragmentViews(z, z);
        } else {
            this.actionBarLayout.rebuildAllFragmentViews(z, z);
        }
    }

    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    public void onRebuildAllFragments(INavigationLayout iNavigationLayout, boolean z) {
        if (AndroidUtilities.isTablet() && iNavigationLayout == this.layersActionBarLayout) {
            this.rightActionBarLayout.rebuildAllFragmentViews(z, z);
            this.actionBarLayout.rebuildAllFragmentViews(z, z);
        }
    }

    public static BaseFragment getLastFragmentIncludeMainTabs() {
        BaseFragment lastFragment = getLastFragment();
        return lastFragment instanceof MainTabsActivity ? ((MainTabsActivity) lastFragment).getCurrentVisibleFragment() : lastFragment;
    }

    public static BaseFragment getLastFragment() {
        INavigationLayout iNavigationLayout;
        BubbleActivity bubbleActivity = BubbleActivity.instance;
        if (bubbleActivity != null && (iNavigationLayout = bubbleActivity.actionBarLayout) != null) {
            return iNavigationLayout.getLastFragment();
        }
        LaunchActivity launchActivity = instance;
        if (launchActivity != null && !launchActivity.sheetFragmentsStack.isEmpty()) {
            return ((INavigationLayout) instance.sheetFragmentsStack.get(r0.size() - 1)).getLastFragment();
        }
        LaunchActivity launchActivity2 = instance;
        if (launchActivity2 == null || launchActivity2.getActionBarLayout() == null) {
            return null;
        }
        return instance.getActionBarLayout().getLastFragment();
    }

    public static BaseFragment findFragment(Class cls) {
        INavigationLayout iNavigationLayout;
        BubbleActivity bubbleActivity = BubbleActivity.instance;
        if (bubbleActivity != null && (iNavigationLayout = bubbleActivity.actionBarLayout) != null) {
            return iNavigationLayout.findFragment(cls);
        }
        LaunchActivity launchActivity = instance;
        if (launchActivity != null && !launchActivity.sheetFragmentsStack.isEmpty()) {
            return ((INavigationLayout) instance.sheetFragmentsStack.get(r0.size() - 1)).findFragment(cls);
        }
        LaunchActivity launchActivity2 = instance;
        if (launchActivity2 == null || launchActivity2.getActionBarLayout() == null) {
            return null;
        }
        return instance.getActionBarLayout().findFragment(cls);
    }

    public static BaseFragment getSafeLastFragment() {
        INavigationLayout iNavigationLayout;
        BubbleActivity bubbleActivity = BubbleActivity.instance;
        if (bubbleActivity != null && (iNavigationLayout = bubbleActivity.actionBarLayout) != null) {
            return iNavigationLayout.getSafeLastFragment();
        }
        LaunchActivity launchActivity = instance;
        if (launchActivity != null && !launchActivity.sheetFragmentsStack.isEmpty()) {
            return ((INavigationLayout) instance.sheetFragmentsStack.get(r0.size() - 1)).getSafeLastFragment();
        }
        LaunchActivity launchActivity2 = instance;
        if (launchActivity2 == null || launchActivity2.getActionBarLayout() == null) {
            return null;
        }
        return instance.getActionBarLayout().getSafeLastFragment();
    }

    public static List getVisibleFragments() {
        INavigationLayout iNavigationLayout;
        BaseFragment safeLastFragment;
        ArrayList arrayList = new ArrayList();
        BubbleActivity bubbleActivity = BubbleActivity.instance;
        if (bubbleActivity != null && (iNavigationLayout = bubbleActivity.actionBarLayout) != null && (safeLastFragment = iNavigationLayout.getSafeLastFragment()) != null) {
            arrayList.add(safeLastFragment);
        }
        LaunchActivity launchActivity = instance;
        if (launchActivity != null) {
            if (!launchActivity.sheetFragmentsStack.isEmpty()) {
                for (int i = 0; i < instance.sheetFragmentsStack.size(); i++) {
                    BaseFragment safeLastFragment2 = ((INavigationLayout) instance.sheetFragmentsStack.get(i)).getSafeLastFragment();
                    if (safeLastFragment2 != null) {
                        arrayList.add(safeLastFragment2);
                    }
                }
            }
            ArrayList arrayList2 = layerFragmentsStack;
            if (!arrayList2.isEmpty()) {
                arrayList.add((BaseFragment) arrayList2.get(arrayList2.size() - 1));
            }
            ArrayList arrayList3 = rightFragmentsStack;
            if (!arrayList3.isEmpty()) {
                arrayList.add((BaseFragment) arrayList3.get(arrayList3.size() - 1));
            }
            ArrayList arrayList4 = mainFragmentsStack;
            if (!arrayList4.isEmpty()) {
                arrayList.add((BaseFragment) arrayList4.get(arrayList4.size() - 1));
            }
        }
        return arrayList;
    }

    public int getNavigationBarColor() {
        if (Build.VERSION.SDK_INT >= 26) {
            return getWindow().getNavigationBarColor();
        }
        return 0;
    }

    public void setNavigationBarColor(int i) {
        this.drawerLayoutContainer.setInternalNavigationBarColor(i);
        BottomSheetTabs bottomSheetTabs = getBottomSheetTabs();
        if (bottomSheetTabs != null) {
            bottomSheetTabs.setNavigationBarColor(i);
        }
    }

    public BottomSheetTabs getBottomSheetTabs() {
        ActionBarLayout actionBarLayout = this.rightActionBarLayout;
        if (actionBarLayout != null && actionBarLayout.getBottomSheetTabs() != null) {
            return this.rightActionBarLayout.getBottomSheetTabs();
        }
        ActionBarLayout actionBarLayout2 = this.actionBarLayout;
        if (actionBarLayout2 == null || actionBarLayout2.getBottomSheetTabs() == null) {
            return null;
        }
        return this.actionBarLayout.getBottomSheetTabs();
    }

    public void animateNavigationBarColor(int i) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        ValueAnimator valueAnimator = this.navBarAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.navBarAnimator = null;
        }
        ValueAnimator valueAnimatorOfArgb = ValueAnimator.ofArgb(getNavigationBarColor(), i);
        this.navBarAnimator = valueAnimatorOfArgb;
        valueAnimatorOfArgb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda51
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f$0.lambda$animateNavigationBarColor$184(valueAnimator2);
            }
        });
        this.navBarAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.LaunchActivity.21
            final /* synthetic */ int val$toColor;

            C590721(int i2) {
                i = i2;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                LaunchActivity.this.setNavigationBarColor(i);
            }
        });
        this.navBarAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        this.navBarAnimator.setDuration(320L);
        this.navBarAnimator.start();
    }

    /* JADX INFO: renamed from: org.telegram.ui.LaunchActivity$21 */
    /* JADX INFO: loaded from: classes6.dex */
    class C590721 extends AnimatorListenerAdapter {
        final /* synthetic */ int val$toColor;

        C590721(int i2) {
            i = i2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            LaunchActivity.this.setNavigationBarColor(i);
        }
    }

    public /* synthetic */ void lambda$animateNavigationBarColor$184(ValueAnimator valueAnimator) {
        setNavigationBarColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    public boolean isLightNavigationBar() {
        return AndroidUtilities.getLightNavigationBar(getWindow());
    }

    private void openStory(final long j, final int i, final boolean z) {
        TL_stories.StoryItem storyItem;
        StoriesController.StoriesList storiesList;
        StoriesController.StoriesList storiesList2;
        MessageObject messageObjectFindMessageObject;
        MessageObject messageObjectFindMessageObject2;
        StoriesController storiesController = MessagesController.getInstance(this.currentAccount).getStoriesController();
        TL_stories.PeerStories stories = storiesController.getStories(j);
        StoriesListPlaceProvider storiesListPlaceProviderM1320of = null;
        if (stories != null) {
            int i2 = 0;
            while (true) {
                if (i2 >= stories.stories.size()) {
                    storyItem = null;
                    break;
                } else {
                    if (stories.stories.get(i2).f1857id == i) {
                        storyItem = stories.stories.get(i2);
                        break;
                    }
                    i2++;
                }
            }
            if (storyItem != null) {
                storyItem.dialogId = j;
                BaseFragment lastFragment = getLastFragment();
                if (lastFragment == null) {
                    return;
                }
                if (lastFragment instanceof DialogsActivity) {
                    try {
                        storiesListPlaceProviderM1320of = StoriesListPlaceProvider.m1320of(((DialogsActivity) lastFragment).dialogStoriesCell.recyclerListView);
                    } catch (Exception unused) {
                    }
                }
                lastFragment.getOrCreateStoryViewer().instantClose();
                ArrayList arrayList = new ArrayList();
                arrayList.add(Long.valueOf(storyItem.dialogId));
                if (z) {
                    lastFragment.getOrCreateStoryViewer().showViewsAfterOpening();
                }
                lastFragment.getOrCreateStoryViewer().open(this, storyItem, arrayList, 0, null, stories, storiesListPlaceProviderM1320of, false);
                return;
            }
        } else {
            storyItem = null;
        }
        if (storyItem == null) {
            StoriesController.StoriesList storiesList3 = storiesController.getStoriesList(j, 0);
            if (storiesList3 == null || (messageObjectFindMessageObject2 = storiesList3.findMessageObject(i)) == null) {
                storiesList3 = null;
            } else {
                storyItem = messageObjectFindMessageObject2.storyItem;
            }
            if (storyItem != null || (storiesList2 = storiesController.getStoriesList(j, 1)) == null || (messageObjectFindMessageObject = storiesList2.findMessageObject(i)) == null) {
                storiesList = storiesList3;
            } else {
                storyItem = messageObjectFindMessageObject.storyItem;
                storiesList = storiesList2;
            }
            if (storyItem != null && storiesList != null) {
                storyItem.dialogId = j;
                BaseFragment lastFragment2 = getLastFragment();
                if (lastFragment2 == null) {
                    return;
                }
                if (lastFragment2 instanceof DialogsActivity) {
                    try {
                        storiesListPlaceProviderM1320of = StoriesListPlaceProvider.m1320of(((DialogsActivity) lastFragment2).dialogStoriesCell.recyclerListView);
                    } catch (Exception unused2) {
                    }
                }
                lastFragment2.getOrCreateStoryViewer().instantClose();
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(Long.valueOf(storyItem.dialogId));
                if (z) {
                    lastFragment2.getOrCreateStoryViewer().showViewsAfterOpening();
                }
                lastFragment2.getOrCreateStoryViewer().open(this, storyItem, arrayList2, 0, storiesList, null, storiesListPlaceProviderM1320of, false);
                return;
            }
        }
        TL_stories.TL_stories_getStoriesByID tL_stories_getStoriesByID = new TL_stories.TL_stories_getStoriesByID();
        tL_stories_getStoriesByID.peer = MessagesController.getInstance(this.currentAccount).getInputPeer(j);
        tL_stories_getStoriesByID.f1864id.add(Integer.valueOf(i));
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_stories_getStoriesByID, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda49
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$openStory$186(i, j, z, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$openStory$186(final int i, final long j, final boolean z, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda110
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openStory$185(tLObject, i, j, z);
            }
        });
    }

    public /* synthetic */ void lambda$openStory$185(TLObject tLObject, int i, long j, boolean z) {
        StoriesListPlaceProvider storiesListPlaceProviderM1320of;
        TL_stories.StoryItem storyItem;
        if (tLObject instanceof TL_stories.TL_stories_stories) {
            TL_stories.TL_stories_stories tL_stories_stories = (TL_stories.TL_stories_stories) tLObject;
            int i2 = 0;
            while (true) {
                storiesListPlaceProviderM1320of = null;
                if (i2 >= tL_stories_stories.stories.size()) {
                    storyItem = null;
                    break;
                } else {
                    if (tL_stories_stories.stories.get(i2).f1857id == i) {
                        storyItem = tL_stories_stories.stories.get(i2);
                        break;
                    }
                    i2++;
                }
            }
            if (storyItem != null) {
                storyItem.dialogId = j;
                BaseFragment lastFragment = getLastFragment();
                if (lastFragment == null) {
                    return;
                }
                if (lastFragment instanceof DialogsActivity) {
                    try {
                        storiesListPlaceProviderM1320of = StoriesListPlaceProvider.m1320of(((DialogsActivity) lastFragment).dialogStoriesCell.recyclerListView);
                    } catch (Exception unused) {
                    }
                }
                StoriesListPlaceProvider storiesListPlaceProvider = storiesListPlaceProviderM1320of;
                lastFragment.getOrCreateStoryViewer().instantClose();
                ArrayList arrayList = new ArrayList();
                arrayList.add(Long.valueOf(j));
                if (z) {
                    lastFragment.getOrCreateStoryViewer().showViewsAfterOpening();
                }
                lastFragment.getOrCreateStoryViewer().open(this, storyItem, arrayList, 0, null, null, storiesListPlaceProvider, false);
                return;
            }
        }
        BulletinFactory.global().createSimpleBulletin(C2888R.raw.error, LocaleController.getString(C2888R.string.StoryNotFound)).show(false);
    }

    private void openStories(long[] jArr, boolean z) {
        boolean z2;
        final long[] array;
        StoriesListPlaceProvider storiesListPlaceProviderM1320of;
        int i = 0;
        while (true) {
            if (i >= jArr.length) {
                z2 = true;
                break;
            }
            TLRPC.User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(jArr[i]));
            if (user != null && !user.stories_hidden) {
                z2 = false;
                break;
            }
            i++;
        }
        BaseFragment lastFragment = getLastFragment();
        if (lastFragment == null) {
            return;
        }
        StoriesController storiesController = MessagesController.getInstance(this.currentAccount).getStoriesController();
        ArrayList arrayList = new ArrayList(z2 ? storiesController.getHiddenList() : storiesController.getDialogListStories());
        boolean z3 = z2;
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        if (z3) {
            array = jArr;
        } else {
            ArrayList arrayList4 = new ArrayList();
            for (int i2 = 0; i2 < jArr.length; i2++) {
                TLRPC.User user2 = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(jArr[i2]));
                if (user2 == null || !user2.stories_hidden) {
                    arrayList4.add(Long.valueOf(jArr[i2]));
                }
            }
            array = Longs.toArray(arrayList4);
        }
        if (z) {
            for (long j : array) {
                arrayList3.add(Long.valueOf(j));
            }
        } else {
            for (long j2 : array) {
                arrayList2.add(Long.valueOf(j2));
            }
        }
        if (!arrayList3.isEmpty() && z) {
            final MessagesController messagesController = MessagesController.getInstance(this.currentAccount);
            final int[] iArr = {arrayList3.size()};
            final Runnable runnable = new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda45
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$openStories$187(iArr, array);
                }
            };
            for (int i3 = 0; i3 < arrayList3.size(); i3++) {
                final long jLongValue = ((Long) arrayList3.get(i3)).longValue();
                TL_stories.TL_stories_getPeerStories tL_stories_getPeerStories = new TL_stories.TL_stories_getPeerStories();
                TLRPC.InputPeer inputPeer = messagesController.getInputPeer(jLongValue);
                tL_stories_getPeerStories.peer = inputPeer;
                if (inputPeer instanceof TLRPC.TL_inputPeerEmpty) {
                    iArr[0] = iArr[0] - 1;
                } else if (inputPeer == null) {
                    iArr[0] = iArr[0] - 1;
                } else {
                    ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_stories_getPeerStories, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda46
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda116
                                @Override // java.lang.Runnable
                                public final void run() {
                                    LaunchActivity.$r8$lambda$M6e9VCAQW4YeMLeM37TipxL_8ng(tLObject, messagesController, j, runnable);
                                }
                            });
                        }
                    });
                }
            }
            return;
        }
        long clientUserId = UserConfig.getInstance(this.currentAccount).getClientUserId();
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            long peerDialogId = DialogObject.getPeerDialogId(((TL_stories.PeerStories) arrayList.get(i4)).peer);
            if (peerDialogId != clientUserId && !arrayList2.contains(Long.valueOf(peerDialogId)) && storiesController.hasUnreadStories(peerDialogId)) {
                arrayList2.add(Long.valueOf(peerDialogId));
            }
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        if (lastFragment instanceof DialogsActivity) {
            try {
                storiesListPlaceProviderM1320of = StoriesListPlaceProvider.m1320of(((DialogsActivity) lastFragment).dialogStoriesCell.recyclerListView);
            } catch (Exception unused) {
                storiesListPlaceProviderM1320of = null;
            }
        } else {
            storiesListPlaceProviderM1320of = null;
        }
        StoriesListPlaceProvider storiesListPlaceProvider = storiesListPlaceProviderM1320of;
        lastFragment.getOrCreateStoryViewer().instantClose();
        lastFragment.getOrCreateStoryViewer().open(this, null, arrayList2, 0, null, null, storiesListPlaceProvider, false);
    }

    public /* synthetic */ void lambda$openStories$187(int[] iArr, long[] jArr) {
        int i = iArr[0] - 1;
        iArr[0] = i;
        if (i == 0) {
            NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.storiesUpdated, new Object[0]);
            openStories(jArr, false);
        }
    }

    public static /* synthetic */ void $r8$lambda$M6e9VCAQW4YeMLeM37TipxL_8ng(TLObject tLObject, MessagesController messagesController, long j, Runnable runnable) {
        if (tLObject instanceof TL_stories.TL_stories_peerStories) {
            TL_stories.TL_stories_peerStories tL_stories_peerStories = (TL_stories.TL_stories_peerStories) tLObject;
            messagesController.putUsers(tL_stories_peerStories.users, false);
            messagesController.getStoriesController().putStories(j, tL_stories_peerStories.stories);
            runnable.run();
            return;
        }
        runnable.run();
    }

    public static void dismissAllWeb() {
        ArrayList<BaseFragment.AttachedSheet> arrayList;
        BaseFragment safeLastFragment = getSafeLastFragment();
        if (safeLastFragment == null) {
            return;
        }
        int i = 0;
        EmptyBaseFragment sheetFragment = safeLastFragment.getParentLayout() instanceof ActionBarLayout ? ((ActionBarLayout) safeLastFragment.getParentLayout()).getSheetFragment(false) : null;
        if (sheetFragment != null && (arrayList = sheetFragment.sheetsStack) != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                sheetFragment.sheetsStack.get(size).dismiss(true);
            }
        }
        ArrayList<BaseFragment.AttachedSheet> arrayList2 = safeLastFragment.sheetsStack;
        if (arrayList2 != null) {
            for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
                safeLastFragment.sheetsStack.get(size2).dismiss(true);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it = BotWebViewSheet.activeSheets.iterator();
        while (it.hasNext()) {
            arrayList3.add((BotWebViewSheet) it.next());
        }
        int size3 = arrayList3.size();
        while (i < size3) {
            Object obj = arrayList3.get(i);
            i++;
            ((BotWebViewSheet) obj).dismiss(true);
        }
    }

    public static void makeRipple(float f, float f2, float f3) {
        LaunchActivity launchActivity = instance;
        if (launchActivity == null) {
            return;
        }
        launchActivity.makeRippleInternal(f, f2, f3);
    }

    private void makeRippleInternal(float f, float f2, float f3) {
        ISuperRipple iSuperRipple;
        View decorView = getWindow().getDecorView();
        if (decorView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 33 && ((iSuperRipple = this.currentRipple) == null || iSuperRipple.view != decorView)) {
            this.currentRipple = new SuperRipple(decorView);
        }
        ISuperRipple iSuperRipple2 = this.currentRipple;
        if (iSuperRipple2 != null) {
            iSuperRipple2.animate(f, f2, f3);
        }
    }

    private static void nativeConnect() {
        if (BuildVars.DEBUG_PRIVATE_VERSION) {
            Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda91
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchActivity.m15040$r8$lambda$4XGEdHzQXXIwSAqwutxGkdCWSA();
                }
            }, Duration.ofMinutes(Utilities.random.nextInt(4) + 1).toMillis());
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$4X-GEdHzQXXIwSAqwutxGkdCWSA */
    public static /* synthetic */ void m15040$r8$lambda$4XGEdHzQXXIwSAqwutxGkdCWSA() {
        if (ConnectionsManager.native_connect()) {
            return;
        }
        ConnectionsManager.native_log();
    }

    public static int getMainFragmentsStackSize() {
        return mainFragmentsStack.size();
    }

    @Override // org.telegram.messenger.pip.activity.IPipActivity
    public PipActivityController getPipController() {
        return this.pipActivityController;
    }
}
