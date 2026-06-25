package org.telegram.p035ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.components.VerticalImageSpan;
import com.exteragram.messenger.utils.system.VibratorUtils;
import com.google.android.exoplayer2.util.Consumer;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.LongCompanionObject;
import okhttp3.internal.url._UrlKt;
import org.mvel2.MVEL;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ChatThemeController;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SvgHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserNameResolver;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.wallpaper.WallpaperBitmapHolder;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.EmojiThemes;
import org.telegram.p035ui.ActionBar.INavigationLayout;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.ActionBar.ThemeDescription;
import org.telegram.p035ui.ActionBar.theme.ThemeKey;
import org.telegram.p035ui.CameraScanActivity;
import org.telegram.p035ui.Components.AnimatedFloat;
import org.telegram.p035ui.Components.AnimatedTextView;
import org.telegram.p035ui.Components.BackupImageView;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.p035ui.Components.ChatThemeBottomSheet;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.Easings;
import org.telegram.p035ui.Components.FlickerLoadingView;
import org.telegram.p035ui.Components.HideViewAfterAnimation;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.MotionBackgroundDrawable;
import org.telegram.p035ui.Components.RLottieDrawable;
import org.telegram.p035ui.Components.RLottieImageView;
import org.telegram.p035ui.Components.RecyclerListView;
import org.telegram.p035ui.Components.StaticLayoutEx;
import org.telegram.p035ui.Components.ThemeSmallPreviewView;
import org.telegram.p035ui.QrActivity;
import org.telegram.tgnet.ResultCallback;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes6.dex */
public class QrActivity extends BaseFragment {
    private static List<EmojiThemes> cachedThemes;
    private static boolean firstOpen;
    private static final ArrayMap<String, int[]> qrColorsMap;
    private BackupImageView avatarImageView;
    private View backgroundView;
    private long chatId;
    private ImageView closeImageView;
    private MotionBackgroundDrawable currMotionDrawable;
    private EmojiThemes currentTheme;
    private final ArrayMap<String, Bitmap> emojiThemeDarkIcons;
    private Bitmap emojiThemeIcon;
    private final EmojiThemes homeTheme;
    private boolean isCurrentThemeDark;
    private RLottieImageView logoImageView;
    private final Rect logoRect;
    private ValueAnimator patternAlphaAnimator;
    private ValueAnimator patternIntensityAnimator;
    private MotionBackgroundDrawable prevMotionDrawable;
    private int[] prevQrColors;
    private int prevSystemUiVisibility;
    private QrView qrView;
    private final ThemeResourcesProvider resourcesProvider;
    private int selectedPosition;
    private MotionBackgroundDrawable tempMotionDrawable;
    private FrameLayout themeLayout;
    private ThemeListViewController themesViewController;
    private long userId;

    public interface OnItemSelectedListener {
        void onItemSelected(EmojiThemes emojiThemes, int i);
    }

    static {
        ArrayMap<String, int[]> arrayMap = new ArrayMap<>();
        qrColorsMap = arrayMap;
        arrayMap.put("🏠d", new int[]{-9324972, -13856649, -6636738, -9915042});
        arrayMap.put("🐥d", new int[]{-12344463, -7684788, -6442695, -8013488});
        arrayMap.put("⛄d", new int[]{-10051073, -10897938, -12469550, -7694337});
        arrayMap.put("💎d", new int[]{-11429643, -11814958, -5408261, -2128185});
        arrayMap.put("👨\u200d🏫d", new int[]{-6637227, -12015466, -13198627, -10631557});
        arrayMap.put("🌷d", new int[]{-1146812, -1991901, -1745517, -3443241});
        arrayMap.put("💜d", new int[]{-1156738, -1876046, -5412366, -28073});
        arrayMap.put("🎄d", new int[]{-1281978, -551386, -1870308, -742870});
        arrayMap.put("🎮d", new int[]{-15092782, -2333964, -1684365, -1269214});
        arrayMap.put("🏠n", new int[]{-15368239, -11899662, -15173939, -13850930});
        arrayMap.put("🐥n", new int[]{-11033320, -14780848, -9594089, -12604587});
        arrayMap.put("⛄n", new int[]{-13930790, -13665098, -14833975, -9732865});
        arrayMap.put("💎n", new int[]{-5089608, -9481473, -14378302, -13337899});
        arrayMap.put("👨\u200d🏫n", new int[]{-14447768, -9199261, -15356801, -15823723});
        arrayMap.put("🌷n", new int[]{-2534316, -2984177, -3258783, -5480504});
        arrayMap.put("💜n", new int[]{-3123030, -2067394, -2599576, -6067757});
        arrayMap.put("🎄n", new int[]{-2725857, -3242459, -3248848, -3569123});
        arrayMap.put("🎮n", new int[]{-3718333, -1278154, -16338695, -6076417});
        firstOpen = true;
    }

    public QrActivity(Bundle bundle) {
        super(bundle);
        this.resourcesProvider = new ThemeResourcesProvider();
        EmojiThemes emojiThemesCreateHomeQrTheme = EmojiThemes.createHomeQrTheme(this.currentAccount);
        this.homeTheme = emojiThemesCreateHomeQrTheme;
        this.logoRect = new Rect();
        this.emojiThemeDarkIcons = new ArrayMap<>();
        this.prevQrColors = null;
        this.currMotionDrawable = new MotionBackgroundDrawable();
        this.currentTheme = emojiThemesCreateHomeQrTheme;
        this.selectedPosition = -1;
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        this.userId = this.arguments.getLong("user_id");
        this.chatId = this.arguments.getLong("chat_id");
        return super.onFragmentCreate();
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0285  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x02b7  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02ba  */
    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View createView(android.content.Context r29) {
        /*
            Method dump skipped, instruction units count: 727
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.QrActivity.createView(android.content.Context):android.view.View");
    }

    /* JADX INFO: renamed from: org.telegram.ui.QrActivity$1 */
    public class C65521 extends FrameLayout {
        private boolean prevIsPortrait;

        public C65521(Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            super.dispatchTouchEvent(motionEvent);
            return true;
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            boolean z = size < size2;
            QrActivity.this.avatarImageView.setVisibility(z ? 0 : 8);
            super.onMeasure(i, i2);
            QrActivity qrActivity = QrActivity.this;
            if (z) {
                qrActivity.themeLayout.measure(View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE));
                QrActivity.this.qrView.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(260.0f), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(330.0f), TLObject.FLAG_30));
            } else {
                qrActivity.themeLayout.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(273.0f), TLObject.FLAG_30), i2);
                QrActivity.this.qrView.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(260.0f), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(310.0f), TLObject.FLAG_30));
            }
            if (this.prevIsPortrait != z) {
                QrActivity.this.qrView.onSizeChanged(QrActivity.this.qrView.getMeasuredWidth(), QrActivity.this.qrView.getMeasuredHeight(), 0, 0);
            }
            this.prevIsPortrait = z;
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int width;
            int height;
            boolean z2 = getWidth() < getHeight();
            QrActivity.this.backgroundView.layout(0, 0, getWidth(), getHeight());
            int measuredHeight = QrActivity.this.themeLayout.getVisibility() == 0 ? QrActivity.this.themeLayout.getMeasuredHeight() : 0;
            if (z2) {
                width = (getWidth() - QrActivity.this.qrView.getMeasuredWidth()) / 2;
            } else {
                width = ((getWidth() - QrActivity.this.themeLayout.getMeasuredWidth()) - QrActivity.this.qrView.getMeasuredWidth()) / 2;
            }
            if (z2) {
                height = ((((getHeight() - measuredHeight) - QrActivity.this.qrView.getMeasuredHeight()) - AndroidUtilities.m1036dp(48.0f)) / 2) + AndroidUtilities.m1036dp(52.0f);
            } else {
                height = (getHeight() - QrActivity.this.qrView.getMeasuredHeight()) / 2;
            }
            QrActivity.this.qrView.layout(width, height, QrActivity.this.qrView.getMeasuredWidth() + width, QrActivity.this.qrView.getMeasuredHeight() + height);
            if (z2) {
                int width2 = (getWidth() - QrActivity.this.avatarImageView.getMeasuredWidth()) / 2;
                int iM1036dp = height - AndroidUtilities.m1036dp(48.0f);
                QrActivity.this.avatarImageView.layout(width2, iM1036dp, QrActivity.this.avatarImageView.getMeasuredWidth() + width2, QrActivity.this.avatarImageView.getMeasuredHeight() + iM1036dp);
            }
            if (QrActivity.this.themeLayout.getVisibility() == 0) {
                if (z2) {
                    int width3 = (getWidth() - QrActivity.this.themeLayout.getMeasuredWidth()) / 2;
                    QrActivity.this.themeLayout.layout(width3, i4 - measuredHeight, QrActivity.this.themeLayout.getMeasuredWidth() + width3, i4);
                } else {
                    int height2 = (getHeight() - QrActivity.this.themeLayout.getMeasuredHeight()) / 2;
                    QrActivity.this.themeLayout.layout(i3 - QrActivity.this.themeLayout.getMeasuredWidth(), height2, i3, QrActivity.this.themeLayout.getMeasuredHeight() + height2);
                }
            }
            QrActivity.this.logoImageView.layout(QrActivity.this.logoRect.left + width, QrActivity.this.logoRect.top + height, width + QrActivity.this.logoRect.right, height + QrActivity.this.logoRect.bottom);
            int iM1036dp2 = AndroidUtilities.m1036dp(z2 ? 14.0f : 17.0f);
            int iM1036dp3 = AndroidUtilities.statusBarHeight + AndroidUtilities.m1036dp(z2 ? 10.0f : 5.0f);
            QrActivity.this.closeImageView.layout(iM1036dp2, iM1036dp3, QrActivity.this.closeImageView.getMeasuredWidth() + iM1036dp2, QrActivity.this.closeImageView.getMeasuredHeight() + iM1036dp3);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.QrActivity$2 */
    public class C65532 extends View {
        public C65532(Context context) {
            super(context);
        }

        @Override // android.view.View
        public void onDraw(Canvas canvas) {
            canvas.drawColor(QrActivity.this.isCurrentThemeDark ? -15590870 : -6569073);
            if (QrActivity.this.prevMotionDrawable != null) {
                QrActivity.this.prevMotionDrawable.setBounds(0, 0, getWidth(), getHeight());
            }
            QrActivity.this.currMotionDrawable.setBounds(0, 0, getWidth(), getHeight());
            if (QrActivity.this.prevMotionDrawable != null) {
                QrActivity.this.prevMotionDrawable.drawBackground(canvas);
            }
            QrActivity.this.currMotionDrawable.drawBackground(canvas);
            if (QrActivity.this.prevMotionDrawable != null) {
                QrActivity.this.prevMotionDrawable.drawPattern(canvas);
            }
            QrActivity.this.currMotionDrawable.drawPattern(canvas);
            super.onDraw(canvas);
        }
    }

    public /* synthetic */ void lambda$createView$0(int i, int i2, int i3, int i4) {
        this.logoRect.set(i, i2, i3, i4);
        this.qrView.requestLayout();
    }

    public /* synthetic */ void lambda$createView$1(View view) {
        finishFragment();
    }

    /* JADX INFO: renamed from: org.telegram.ui.QrActivity$3 */
    public class C65543 extends ThemeListViewController {
        public C65543(BaseFragment baseFragment, Window window) {
            super(baseFragment, window);
        }

        @Override // org.telegram.ui.QrActivity.ThemeListViewController
        public void setDarkTheme(boolean z) {
            super.setDarkTheme(z);
            QrActivity.this.isCurrentThemeDark = z;
            QrActivity qrActivity = QrActivity.this;
            qrActivity.onItemSelected(qrActivity.currentTheme, QrActivity.this.selectedPosition, false);
        }
    }

    public /* synthetic */ void lambda$createView$2(EmojiThemes emojiThemes, int i) {
        onItemSelected(emojiThemes, i, true);
    }

    public /* synthetic */ void lambda$createView$3(View view) {
        this.themesViewController.shareButton.setClickable(false);
        performShare();
    }

    public /* synthetic */ void lambda$createView$4(View view) {
        if (getParentActivity() == null) {
            return;
        }
        if (getParentActivity().checkSelfPermission("android.permission.CAMERA") != 0) {
            getParentActivity().requestPermissions(new String[]{"android.permission.CAMERA"}, 34);
        } else {
            openCameraScanActivity(this);
        }
    }

    public /* synthetic */ void lambda$createView$6() {
        this.homeTheme.loadPreviewColors(this.currentAccount);
        View view = this.fragmentView;
        if (view == null) {
            return;
        }
        view.postDelayed(new Runnable() { // from class: org.telegram.ui.QrActivity$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$5();
            }
        }, 17L);
    }

    public /* synthetic */ void lambda$createView$5() {
        onItemSelected(this.currentTheme, 0, true);
        this.logoImageView.getAnimatedDrawable().cacheFrame(33);
    }

    public /* synthetic */ void lambda$createView$7() {
        firstOpen = false;
        List<EmojiThemes> list = cachedThemes;
        if (list == null || list.isEmpty()) {
            ChatThemeController.getInstance(this.currentAccount).requestAllChatThemes(new ResultCallback<List<EmojiThemes>>() { // from class: org.telegram.ui.QrActivity.4
                public C65554() {
                }

                @Override // org.telegram.tgnet.ResultCallback
                public void onComplete(List<EmojiThemes> list2) {
                    QrActivity.this.onDataLoaded(list2);
                    QrActivity.cachedThemes = list2;
                }

                @Override // org.telegram.tgnet.ResultCallback
                public void onError(TLRPC.TL_error tL_error) {
                    Toast.makeText(QrActivity.this.getParentActivity(), tL_error.text, 0).show();
                }
            }, true);
        } else {
            onDataLoaded(cachedThemes);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.QrActivity$4 */
    public class C65554 implements ResultCallback<List<EmojiThemes>> {
        public C65554() {
        }

        @Override // org.telegram.tgnet.ResultCallback
        public void onComplete(List<EmojiThemes> list2) {
            QrActivity.this.onDataLoaded(list2);
            QrActivity.cachedThemes = list2;
        }

        @Override // org.telegram.tgnet.ResultCallback
        public void onError(TLRPC.TL_error tL_error) {
            Toast.makeText(QrActivity.this.getParentActivity(), tL_error.text, 0).show();
        }
    }

    private boolean phoneIsPublic() {
        char c2;
        ArrayList<TLRPC.PrivacyRule> privacyRules = ContactsController.getInstance(this.currentAccount).getPrivacyRules(6);
        if (privacyRules == null) {
            return false;
        }
        for (int i = 0; i < privacyRules.size(); i++) {
            TLRPC.PrivacyRule privacyRule = privacyRules.get(i);
            if (privacyRule instanceof TLRPC.TL_privacyValueAllowAll) {
                c2 = 0;
                break;
            }
            if (privacyRule instanceof TLRPC.TL_privacyValueDisallowAll) {
                break;
            }
            if (privacyRule instanceof TLRPC.TL_privacyValueAllowContacts) {
                c2 = 1;
                break;
            }
        }
        c2 = 2;
        if (c2 == 2) {
            ArrayList<TLRPC.PrivacyRule> privacyRules2 = ContactsController.getInstance(this.currentAccount).getPrivacyRules(7);
            if (privacyRules2 == null || privacyRules2.size() == 0) {
                return true;
            }
            for (int i2 = 0; i2 < privacyRules2.size(); i2++) {
                TLRPC.PrivacyRule privacyRule2 = privacyRules2.get(i2);
                if (privacyRule2 instanceof TLRPC.TL_privacyValueAllowAll) {
                    return true;
                }
                if ((privacyRule2 instanceof TLRPC.TL_privacyValueDisallowAll) || (privacyRule2 instanceof TLRPC.TL_privacyValueAllowContacts)) {
                    return false;
                }
            }
        }
        return c2 == 0 || c2 == 1;
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    @SuppressLint({"SourceLockedOrientationActivity"})
    public void onResume() {
        super.onResume();
        applyScreenSettings();
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onPause() {
        restoreScreenSettings();
        super.onPause();
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        this.themesViewController.onDestroy();
        this.themesViewController = null;
        this.emojiThemeIcon.recycle();
        this.emojiThemeIcon = null;
        int i = 0;
        while (true) {
            int size = this.emojiThemeDarkIcons.getSize();
            ArrayMap<String, Bitmap> arrayMap = this.emojiThemeDarkIcons;
            if (i < size) {
                Bitmap bitmapValueAt = arrayMap.valueAt(i);
                if (bitmapValueAt != null) {
                    bitmapValueAt.recycle();
                }
                i++;
            } else {
                arrayMap.clear();
                restoreScreenSettings();
                super.onFragmentDestroy();
                return;
            }
        }
    }

    private void applyScreenSettings() {
        if (getParentActivity() != null) {
            getParentActivity().getWindow().getDecorView().setSystemUiVisibility(this.prevSystemUiVisibility | 1028);
        }
    }

    private void restoreScreenSettings() {
        if (getParentActivity() != null) {
            getParentActivity().getWindow().getDecorView().setSystemUiVisibility(this.prevSystemUiVisibility);
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public Theme.ResourcesProvider getResourceProvider() {
        return this.resourcesProvider;
    }

    public void onDataLoaded(List<EmojiThemes> list) {
        if (list == null || list.isEmpty() || this.themesViewController == null) {
            return;
        }
        int i = 0;
        list.set(0, this.homeTheme);
        ArrayList arrayList = new ArrayList(list.size());
        for (int i2 = 0; i2 < list.size(); i2++) {
            EmojiThemes emojiThemes = list.get(i2);
            emojiThemes.loadPreviewColors(this.currentAccount);
            ChatThemeBottomSheet.ChatThemeItem chatThemeItem = new ChatThemeBottomSheet.ChatThemeItem(emojiThemes);
            boolean z = this.isCurrentThemeDark;
            chatThemeItem.themeIndex = z ? 1 : 0;
            chatThemeItem.icon = getEmojiThemeIcon(emojiThemes, z);
            arrayList.add(chatThemeItem);
        }
        this.themesViewController.adapter.setItems(arrayList);
        while (true) {
            if (i == arrayList.size()) {
                i = -1;
                break;
            } else {
                if (ThemeKey.equals(((ChatThemeBottomSheet.ChatThemeItem) arrayList.get(i)).chatTheme.getThemeKey(), this.currentTheme.getThemeKey())) {
                    this.themesViewController.selectedItem = (ChatThemeBottomSheet.ChatThemeItem) arrayList.get(i);
                    break;
                }
                i++;
            }
        }
        if (i != -1) {
            this.themesViewController.setSelectedPosition(i);
        }
        this.themesViewController.onDataLoaded();
    }

    public Bitmap getEmojiThemeIcon(EmojiThemes emojiThemes, boolean z) {
        if (z) {
            Bitmap bitmapCreateBitmap = this.emojiThemeDarkIcons.get(emojiThemes.emoji);
            if (bitmapCreateBitmap == null) {
                bitmapCreateBitmap = Bitmap.createBitmap(this.emojiThemeIcon.getWidth(), this.emojiThemeIcon.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                int[] iArr = qrColorsMap.get(emojiThemes.emoji + "n");
                if (iArr != null) {
                    if (this.tempMotionDrawable == null) {
                        this.tempMotionDrawable = new MotionBackgroundDrawable(0, 0, 0, 0, true);
                    }
                    this.tempMotionDrawable.setColors(iArr[0], iArr[1], iArr[2], iArr[3]);
                    this.tempMotionDrawable.setBounds(AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), canvas.getWidth() - AndroidUtilities.m1036dp(6.0f), canvas.getHeight() - AndroidUtilities.m1036dp(6.0f));
                    this.tempMotionDrawable.draw(canvas);
                }
                canvas.drawBitmap(this.emojiThemeIcon, 0.0f, 0.0f, (Paint) null);
                canvas.setBitmap(null);
                this.emojiThemeDarkIcons.put(emojiThemes.emoji, bitmapCreateBitmap);
            }
            return bitmapCreateBitmap;
        }
        return this.emojiThemeIcon;
    }

    private void onPatternLoaded(Bitmap bitmap, int i, boolean z) {
        if (bitmap != null) {
            this.currMotionDrawable.setPatternBitmap(i, bitmap, true);
            ValueAnimator valueAnimator = this.patternIntensityAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            if (z) {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.patternIntensityAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.QrActivity$$ExternalSyntheticLambda17
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        this.f$0.lambda$onPatternLoaded$8(valueAnimator2);
                    }
                });
                this.patternIntensityAnimator.setDuration(250L);
                this.patternIntensityAnimator.start();
                return;
            }
            this.currMotionDrawable.setPatternAlpha(1.0f);
        }
    }

    public /* synthetic */ void lambda$onPatternLoaded$8(ValueAnimator valueAnimator) {
        this.currMotionDrawable.setPatternAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

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
    public void onItemSelected(EmojiThemes emojiThemes, int i, final boolean z) {
        float fMax;
        this.selectedPosition = i;
        final EmojiThemes emojiThemes2 = this.currentTheme;
        final boolean z2 = this.isCurrentThemeDark;
        this.currentTheme = emojiThemes;
        EmojiThemes.ThemeItem themeItem = emojiThemes.getThemeItem(z2 ? 1 : 0);
        ValueAnimator valueAnimator = this.patternAlphaAnimator;
        if (valueAnimator != null) {
            fMax = Math.max(0.5f, 1.0f - ((Float) valueAnimator.getAnimatedValue()).floatValue()) * 1.0f;
            this.patternAlphaAnimator.cancel();
        } else {
            fMax = 1.0f;
        }
        MotionBackgroundDrawable motionBackgroundDrawable = this.currMotionDrawable;
        this.prevMotionDrawable = motionBackgroundDrawable;
        motionBackgroundDrawable.setIndeterminateAnimation(false);
        this.prevMotionDrawable.setAlpha(255);
        MotionBackgroundDrawable motionBackgroundDrawable2 = new MotionBackgroundDrawable();
        this.currMotionDrawable = motionBackgroundDrawable2;
        motionBackgroundDrawable2.setCallback(this.backgroundView);
        this.currMotionDrawable.setColors(themeItem.patternBgColor, themeItem.patternBgGradientColor1, themeItem.patternBgGradientColor2, themeItem.patternBgGradientColor3);
        this.currMotionDrawable.setParentView(this.backgroundView);
        this.currMotionDrawable.setPatternAlpha(1.0f);
        this.currMotionDrawable.setIndeterminateAnimation(true);
        MotionBackgroundDrawable motionBackgroundDrawable3 = this.prevMotionDrawable;
        if (motionBackgroundDrawable3 != null) {
            this.currMotionDrawable.posAnimationProgress = motionBackgroundDrawable3.posAnimationProgress;
        }
        this.qrView.setPosAnimationProgress(this.currMotionDrawable.posAnimationProgress);
        TLRPC.WallPaper wallpaper = this.currentTheme.getWallpaper(z2 ? 1 : 0);
        if (wallpaper != null) {
            this.currMotionDrawable.setPatternBitmap(wallpaper.settings.intensity);
            final long jElapsedRealtime = SystemClock.elapsedRealtime();
            this.currentTheme.loadWallpaper(z2 ? 1 : 0, new ResultCallback() { // from class: org.telegram.ui.QrActivity$$ExternalSyntheticLambda11
                @Override // org.telegram.tgnet.ResultCallback
                public final void onComplete(Object obj) {
                    this.f$0.lambda$onItemSelected$9(z2, jElapsedRealtime, (Pair) obj);
                }
            });
        } else {
            Utilities.themeQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.QrActivity$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onItemSelected$11();
                }
            }, 35L);
        }
        MotionBackgroundDrawable motionBackgroundDrawable4 = this.currMotionDrawable;
        motionBackgroundDrawable4.setPatternColorFilter(motionBackgroundDrawable4.getPatternColor());
        ArrayMap<String, int[]> arrayMap = qrColorsMap;
        StringBuilder sb = new StringBuilder();
        sb.append(emojiThemes.emoji);
        sb.append(z2 ? "n" : "d");
        final int[] iArr = arrayMap.get(sb.toString());
        if (z) {
            if (this.prevQrColors == null) {
                int[] iArr2 = new int[4];
                this.prevQrColors = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, 4);
            }
            this.currMotionDrawable.setAlpha(255);
            this.currMotionDrawable.setBackgroundAlpha(0.0f);
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.patternAlphaAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.QrActivity$$ExternalSyntheticLambda13
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    this.f$0.lambda$onItemSelected$12(iArr, valueAnimator2);
                }
            });
            this.patternAlphaAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.QrActivity.5
                final /* synthetic */ int[] val$newQrColors;

                public C65565(final int[] iArr3) {
                    iArr = iArr3;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    int[] iArr3 = iArr;
                    if (iArr3 != null) {
                        System.arraycopy(iArr3, 0, QrActivity.this.prevQrColors, 0, 4);
                    }
                    QrActivity.this.prevMotionDrawable = null;
                    QrActivity.this.patternAlphaAnimator = null;
                    QrActivity.this.currMotionDrawable.setBackgroundAlpha(1.0f);
                    QrActivity.this.currMotionDrawable.setPatternAlpha(1.0f);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    super.onAnimationCancel(animator);
                    float fFloatValue = ((Float) ((ValueAnimator) animator).getAnimatedValue()).floatValue();
                    if (iArr != null) {
                        System.arraycopy(new int[]{ColorUtils.blendARGB(QrActivity.this.prevQrColors[0], iArr[0], fFloatValue), ColorUtils.blendARGB(QrActivity.this.prevQrColors[1], iArr[1], fFloatValue), ColorUtils.blendARGB(QrActivity.this.prevQrColors[2], iArr[2], fFloatValue), ColorUtils.blendARGB(QrActivity.this.prevQrColors[3], iArr[3], fFloatValue)}, 0, QrActivity.this.prevQrColors, 0, 4);
                    }
                }
            });
            this.patternAlphaAnimator.setDuration((int) (fMax * 250.0f));
            this.patternAlphaAnimator.start();
        } else {
            if (iArr3 != null) {
                this.qrView.setColors(iArr3[0], iArr3[1], iArr3[2], iArr3[3]);
                System.arraycopy(iArr3, 0, this.prevQrColors, 0, 4);
            }
            this.prevMotionDrawable = null;
            this.backgroundView.invalidate();
        }
        final INavigationLayout.ThemeAnimationSettings themeAnimationSettings = new INavigationLayout.ThemeAnimationSettings(null, (this.isCurrentThemeDark ? Theme.getCurrentNightTheme() : Theme.getCurrentTheme()).currentAccentId, this.isCurrentThemeDark, !z);
        themeAnimationSettings.applyTheme = false;
        themeAnimationSettings.onlyTopFragment = true;
        themeAnimationSettings.resourcesProvider = getResourceProvider();
        themeAnimationSettings.duration = (int) (fMax * 250.0f);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.QrActivity$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onItemSelected$14(z, emojiThemes2, themeAnimationSettings);
            }
        });
    }

    public /* synthetic */ void lambda$onItemSelected$9(boolean z, long j, Pair pair) {
        long themeId = this.currentTheme.getThemeId(z ? 1 : 0);
        if (pair == null || themeId == 0) {
            return;
        }
        long jLongValue = ((Long) pair.first).longValue();
        Bitmap bitmap = ((WallpaperBitmapHolder) pair.second).bitmap;
        if (jLongValue != themeId || bitmap == null) {
            return;
        }
        onPatternLoaded(bitmap, this.currMotionDrawable.getIntensity(), SystemClock.elapsedRealtime() - j > 150);
    }

    public /* synthetic */ void lambda$onItemSelected$11() {
        final Bitmap bitmap = SvgHelper.getBitmap(C2797R.raw.default_pattern, this.backgroundView.getWidth(), this.backgroundView.getHeight(), -16777216);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.QrActivity$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onItemSelected$10(bitmap);
            }
        });
    }

    public /* synthetic */ void lambda$onItemSelected$10(Bitmap bitmap) {
        onPatternLoaded(bitmap, 34, true);
    }

    public /* synthetic */ void lambda$onItemSelected$12(int[] iArr, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        MotionBackgroundDrawable motionBackgroundDrawable = this.prevMotionDrawable;
        if (motionBackgroundDrawable != null) {
            motionBackgroundDrawable.setBackgroundAlpha(1.0f);
            this.prevMotionDrawable.setPatternAlpha(1.0f - fFloatValue);
        }
        this.currMotionDrawable.setBackgroundAlpha(fFloatValue);
        this.currMotionDrawable.setPatternAlpha(fFloatValue);
        if (iArr != null) {
            this.qrView.setColors(ColorUtils.blendARGB(this.prevQrColors[0], iArr[0], fFloatValue), ColorUtils.blendARGB(this.prevQrColors[1], iArr[1], fFloatValue), ColorUtils.blendARGB(this.prevQrColors[2], iArr[2], fFloatValue), ColorUtils.blendARGB(this.prevQrColors[3], iArr[3], fFloatValue));
        }
        this.backgroundView.invalidate();
    }

    /* JADX INFO: renamed from: org.telegram.ui.QrActivity$5 */
    public class C65565 extends AnimatorListenerAdapter {
        final /* synthetic */ int[] val$newQrColors;

        public C65565(final int[] iArr3) {
            iArr = iArr3;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            int[] iArr3 = iArr;
            if (iArr3 != null) {
                System.arraycopy(iArr3, 0, QrActivity.this.prevQrColors, 0, 4);
            }
            QrActivity.this.prevMotionDrawable = null;
            QrActivity.this.patternAlphaAnimator = null;
            QrActivity.this.currMotionDrawable.setBackgroundAlpha(1.0f);
            QrActivity.this.currMotionDrawable.setPatternAlpha(1.0f);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            super.onAnimationCancel(animator);
            float fFloatValue = ((Float) ((ValueAnimator) animator).getAnimatedValue()).floatValue();
            if (iArr != null) {
                System.arraycopy(new int[]{ColorUtils.blendARGB(QrActivity.this.prevQrColors[0], iArr[0], fFloatValue), ColorUtils.blendARGB(QrActivity.this.prevQrColors[1], iArr[1], fFloatValue), ColorUtils.blendARGB(QrActivity.this.prevQrColors[2], iArr[2], fFloatValue), ColorUtils.blendARGB(QrActivity.this.prevQrColors[3], iArr[3], fFloatValue)}, 0, QrActivity.this.prevQrColors, 0, 4);
            }
        }
    }

    public /* synthetic */ void lambda$onItemSelected$14(boolean z, EmojiThemes emojiThemes, INavigationLayout.ThemeAnimationSettings themeAnimationSettings) {
        ThemeResourcesProvider themeResourcesProvider = this.resourcesProvider;
        if (z) {
            themeResourcesProvider.initColors(emojiThemes, this.isCurrentThemeDark);
        } else {
            themeResourcesProvider.initColors(this.currentTheme, this.isCurrentThemeDark);
        }
        themeAnimationSettings.afterStartDescriptionsAddedRunnable = new Runnable() { // from class: org.telegram.ui.QrActivity$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onItemSelected$13();
            }
        };
        this.parentLayout.animateThemedValues(themeAnimationSettings, null);
        LinearLayout linearLayout = this.themesViewController.scanButtonWrap;
        if (linearLayout != null) {
            linearLayout.setBackground(Theme.AdaptiveRipple.createRect(ColorUtils.setAlphaComponent(Theme.AdaptiveRipple.calcRippleColor(getThemedColor(Theme.key_featuredStickers_addButton)), 25), 6.0f));
        }
    }

    public /* synthetic */ void lambda$onItemSelected$13() {
        this.resourcesProvider.initColors(this.currentTheme, this.isCurrentThemeDark);
    }

    public void performShare() {
        Point point = AndroidUtilities.displaySize;
        int iMin = Math.min(point.x, point.y);
        Point point2 = AndroidUtilities.displaySize;
        int iMax = Math.max(point2.x, point2.y);
        float f = iMin;
        if ((iMax * 1.0f) / f > 1.92f) {
            iMax = (int) (f * 1.92f);
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iMin, iMax, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        this.themeLayout.setVisibility(8);
        this.closeImageView.setVisibility(8);
        this.logoImageView.setVisibility(8);
        RLottieDrawable animatedDrawable = this.logoImageView.getAnimatedDrawable();
        QrView qrView = this.qrView;
        if (qrView != null) {
            qrView.setForShare(true);
        }
        this.fragmentView.measure(View.MeasureSpec.makeMeasureSpec(iMin, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(iMax, TLObject.FLAG_30));
        this.fragmentView.layout(0, 0, iMin, iMax);
        this.fragmentView.draw(canvas);
        animatedDrawable.setBounds(this.logoImageView.getLeft(), this.logoImageView.getTop(), this.logoImageView.getRight(), this.logoImageView.getBottom());
        animatedDrawable.drawFrame(canvas, 33);
        canvas.setBitmap(null);
        this.themeLayout.setVisibility(0);
        this.closeImageView.setVisibility(0);
        this.logoImageView.setVisibility(0);
        ViewGroup viewGroup = (ViewGroup) this.fragmentView.getParent();
        this.fragmentView.layout(0, 0, viewGroup.getWidth(), viewGroup.getHeight());
        QrView qrView2 = this.qrView;
        if (qrView2 != null) {
            qrView2.setForShare(false);
        }
        Uri bitmapShareUri = AndroidUtilities.getBitmapShareUri(bitmapCreateBitmap, "qr_tmp.jpg", Bitmap.CompressFormat.JPEG);
        if (bitmapShareUri != null) {
            try {
                getParentActivity().startActivityForResult(Intent.createChooser(new Intent("android.intent.action.SEND").setType("image/*").putExtra("android.intent.extra.STREAM", bitmapShareUri), LocaleController.getString(C2797R.string.InviteByQRCode)), 500);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.QrActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performShare$15();
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$performShare$15() {
        ThemeListViewController themeListViewController = this.themesViewController;
        if (themeListViewController == null) {
            return;
        }
        themeListViewController.shareButton.setClickable(true);
    }

    /* JADX INFO: renamed from: org.telegram.ui.QrActivity$6 */
    public class C65576 implements CameraScanActivity.CameraScanActivityDelegate {
        final /* synthetic */ int val$currentAccount;
        final /* synthetic */ BaseFragment val$fragment;

        public C65576(int i, BaseFragment baseFragment) {
            this.val$currentAccount = i;
            this.val$fragment = baseFragment;
        }

        @Override // org.telegram.ui.CameraScanActivity.CameraScanActivityDelegate
        public void didFindQr(String str) {
            String strExtractUsername = Browser.extractUsername(str);
            if (!TextUtils.isEmpty(strExtractUsername)) {
                UserNameResolver userNameResolver = MessagesController.getInstance(this.val$currentAccount).getUserNameResolver();
                final BaseFragment baseFragment = this.val$fragment;
                userNameResolver.resolve(strExtractUsername, new Consumer() { // from class: org.telegram.ui.QrActivity$6$$ExternalSyntheticLambda0
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        QrActivity.C65576.$r8$lambda$qVT2f7VGI4J9gkNw23FE3PJ2g3w(baseFragment, (Long) obj);
                    }
                });
                return;
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.QrActivity$6$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BulletinFactory.global().createSimpleBulletin(LocaleController.getString(C2797R.string.ScanQrCode), LocaleController.getString(C2797R.string.ErrorOccurred)).show();
                }
            });
        }

        public static /* synthetic */ void $r8$lambda$qVT2f7VGI4J9gkNw23FE3PJ2g3w(BaseFragment baseFragment, Long l) {
            if (baseFragment.isFinished) {
                return;
            }
            if (l == null || l.longValue() == LongCompanionObject.MAX_VALUE) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.QrActivity$6$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        BulletinFactory.global().createSimpleBulletin(LocaleController.getString(C2797R.string.ScanQrCode), LocaleController.getString(C2797R.string.ErrorOccurred)).show();
                    }
                });
            } else {
                baseFragment.presentFragment(ProfileActivity.m1186of(l.longValue()), true);
            }
        }
    }

    public static void openCameraScanActivity(BaseFragment baseFragment) {
        CameraScanActivity.showAsSheet(baseFragment, false, 1, (CameraScanActivity.CameraScanActivityDelegate) new C65576(baseFragment.getCurrentAccount(), baseFragment));
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        if (getParentActivity() != null && i == 34) {
            if (iArr.length > 0 && iArr[0] == 0) {
                openCameraScanActivity(this);
            } else {
                new AlertDialog.Builder(getParentActivity()).setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.QRCodePermissionNoCameraWithHint))).setPositiveButton(LocaleController.getString(C2797R.string.PermissionOpenSettings), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.QrActivity$$ExternalSyntheticLambda0
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i2) {
                        this.f$0.lambda$onRequestPermissionsResultFragment$16(alertDialog, i2);
                    }
                }).setNegativeButton(LocaleController.getString(C2797R.string.ContactsPermissionAlertNotNow), null).setTopAnimation(C2797R.raw.permission_request_camera, 72, false, Theme.getColor(Theme.key_dialogTopBackground)).show();
            }
        }
    }

    public /* synthetic */ void lambda$onRequestPermissionsResultFragment$16(AlertDialog alertDialog, int i) {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
            getParentActivity().startActivity(intent);
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ArrayList<ThemeDescription> themeDescriptions = super.getThemeDescriptions();
        themeDescriptions.addAll(this.themesViewController.getThemeDescriptions());
        ThemeDescription.ThemeDescriptionDelegate themeDescriptionDelegate = new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.QrActivity$$ExternalSyntheticLambda8
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                this.f$0.lambda$getThemeDescriptions$17();
            }
        };
        TextView textView = this.themesViewController.shareButton;
        int i = ThemeDescription.FLAG_BACKGROUNDFILTER;
        int i2 = Theme.key_featuredStickers_addButton;
        themeDescriptions.add(new ThemeDescription(textView, i, null, null, null, themeDescriptionDelegate, i2));
        themeDescriptions.add(new ThemeDescription(this.themesViewController.shareButton, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_featuredStickers_addButtonPressed));
        if (this.themesViewController.scanButton != null) {
            themeDescriptions.add(new ThemeDescription(this.themesViewController.scanButton, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, themeDescriptionDelegate, i2));
            themeDescriptions.add(new ThemeDescription(this.themesViewController.scanButtonIcon, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, themeDescriptionDelegate, i2));
        }
        int size = themeDescriptions.size();
        int i3 = 0;
        while (i3 < size) {
            ThemeDescription themeDescription = themeDescriptions.get(i3);
            i3++;
            themeDescription.resourcesProvider = getResourceProvider();
        }
        return themeDescriptions;
    }

    public /* synthetic */ void lambda$getThemeDescriptions$17() {
        setNavigationBarColor(getThemedColor(Theme.key_windowBackgroundGray));
    }

    public class ThemeResourcesProvider implements Theme.ResourcesProvider {
        private SparseIntArray colors;

        public /* synthetic */ ThemeResourcesProvider(QrActivity qrActivity, QrActivityIA qrActivityIA) {
            this();
        }

        private ThemeResourcesProvider() {
        }

        public void initColors(EmojiThemes emojiThemes, boolean z) {
            this.colors = emojiThemes.createColors(((BaseFragment) QrActivity.this).currentAccount, z ? 1 : 0);
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public int getColor(int i) {
            SparseIntArray sparseIntArray = this.colors;
            return sparseIntArray != null ? sparseIntArray.get(i) : Theme.getColor(i);
        }
    }

    public static class QrView extends View {
        private Bitmap backgroundBitmap;
        private final Paint bitmapGradientPaint;
        private QrCenterChangedListener centerChangedListener;
        private Runnable checkTimerToken;
        private Bitmap contentBitmap;
        private AnimatedFloat contentBitmapAlpha;
        private Paint crossfadeFromPaint;
        private Paint crossfadeToPaint;
        private final int crossfadeWidthDp;
        private boolean firstPrepare;
        private final MotionBackgroundDrawable gradientDrawable;
        private final BitmapShader gradientShader;
        private final BitmapShader gradientTextShader;
        private Integer hadHeight;
        private String hadLink;
        private String hadUserText;
        private Integer hadWidth;
        private boolean hasTimer;
        private boolean isPhone;
        private String link;
        private int linkExpires;
        private RLottieDrawable loadingMatrix;
        private boolean logoCenterSet;
        private Bitmap oldContentBitmap;
        private float[] radii;
        private boolean setData;
        private StaticLayout shareUsernameLayout;
        private TextPaint shareUsernameLayoutPaint;
        private AnimatedTextView.AnimatedTextDrawable timerTextDrawable;
        private String username;
        private static final float SHADOW_SIZE = AndroidUtilities.m1036dp(2.0f);
        private static final float RADIUS = AndroidUtilities.m1036dp(20.0f);

        public interface QrCenterChangedListener {
            void onCenterChanged(int i, int i2, int i3, int i4);
        }

        public QrView(Context context) {
            super(context);
            MotionBackgroundDrawable motionBackgroundDrawable = new MotionBackgroundDrawable();
            this.gradientDrawable = motionBackgroundDrawable;
            Paint paint = new Paint(1);
            this.bitmapGradientPaint = paint;
            CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
            this.contentBitmapAlpha = new AnimatedFloat(1.0f, this, 0L, 2000L, cubicBezierInterpolator);
            this.crossfadeFromPaint = new Paint(1);
            this.crossfadeToPaint = new Paint(1);
            this.crossfadeWidthDp = 120;
            this.radii = new float[8];
            this.checkTimerToken = new Runnable() { // from class: org.telegram.ui.QrActivity$QrView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$5();
                }
            };
            this.firstPrepare = true;
            motionBackgroundDrawable.setIndeterminateAnimation(true);
            motionBackgroundDrawable.setParentView(this);
            Bitmap bitmap = motionBackgroundDrawable.getBitmap();
            Shader.TileMode tileMode = Shader.TileMode.MIRROR;
            BitmapShader bitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
            this.gradientShader = bitmapShader;
            BitmapShader bitmapShader2 = new BitmapShader(motionBackgroundDrawable.getBitmap(), tileMode, tileMode);
            this.gradientTextShader = bitmapShader2;
            paint.setShader(bitmapShader);
            C65581 c65581 = new AnimatedTextView.AnimatedTextDrawable(false, true, false) { // from class: org.telegram.ui.QrActivity.QrView.1
                public C65581(boolean z, boolean z2, boolean z3) {
                    super(z, z2, z3);
                }

                @Override // android.graphics.drawable.Drawable
                public void invalidateSelf() {
                    QrView.this.invalidate();
                }
            };
            this.timerTextDrawable = c65581;
            c65581.setAnimationProperties(0.35f, 0L, 300L, cubicBezierInterpolator);
            this.timerTextDrawable.setCallback(this);
            this.timerTextDrawable.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_CONDENSED_BOLD));
            this.timerTextDrawable.getPaint().setShader(bitmapShader2);
            this.timerTextDrawable.setGravity(17);
            this.timerTextDrawable.setTextSize(AndroidUtilities.m1036dp(35.0f));
            this.timerTextDrawable.setText(_UrlKt.FRAGMENT_ENCODE_SET);
            Paint paint2 = this.crossfadeFromPaint;
            Shader.TileMode tileMode2 = Shader.TileMode.CLAMP;
            paint2.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, AndroidUtilities.m1036dp(120.0f), new int[]{-1, 0}, new float[]{0.0f, 1.0f}, tileMode2));
            Paint paint3 = this.crossfadeFromPaint;
            PorterDuff.Mode mode = PorterDuff.Mode.DST_OUT;
            paint3.setXfermode(new PorterDuffXfermode(mode));
            this.crossfadeToPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, AndroidUtilities.m1036dp(120.0f), new int[]{0, -1}, new float[]{0.0f, 1.0f}, tileMode2));
            this.crossfadeToPaint.setXfermode(new PorterDuffXfermode(mode));
        }

        /* JADX INFO: renamed from: org.telegram.ui.QrActivity$QrView$1 */
        public class C65581 extends AnimatedTextView.AnimatedTextDrawable {
            public C65581(boolean z, boolean z2, boolean z3) {
                super(z, z2, z3);
            }

            @Override // android.graphics.drawable.Drawable
            public void invalidateSelf() {
                QrView.this.invalidate();
            }
        }

        public void setForShare(boolean z) {
            if (this.hasTimer) {
                if (z) {
                    if (this.shareUsernameLayoutPaint == null) {
                        this.shareUsernameLayoutPaint = new TextPaint(1);
                    }
                    this.shareUsernameLayoutPaint.setShader(this.gradientTextShader);
                    this.shareUsernameLayoutPaint.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_CONDENSED_BOLD));
                    this.shareUsernameLayoutPaint.setTextSize(AndroidUtilities.m1036dp(25.0f));
                    String str = this.username;
                    if (str == null) {
                        str = _UrlKt.FRAGMENT_ENCODE_SET;
                    }
                    this.shareUsernameLayout = StaticLayoutEx.createStaticLayout(Emoji.replaceEmoji(str, this.shareUsernameLayoutPaint.getFontMetricsInt(), false), this.shareUsernameLayoutPaint, getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false, TextUtils.TruncateAt.END, getWidth() - AndroidUtilities.m1036dp(60.0f), 1);
                    return;
                }
                this.shareUsernameLayout = null;
            }
        }

        @Override // android.view.View
        public void onSizeChanged(final int i, final int i2, int i3, int i4) {
            super.onSizeChanged(i, i2, i3, i4);
            if (i == i3 && i2 == i4) {
                return;
            }
            Bitmap bitmap = this.backgroundBitmap;
            if (bitmap != null) {
                bitmap.recycle();
                this.backgroundBitmap = null;
            }
            Paint paint = new Paint(1);
            paint.setColor(-1);
            float fM1036dp = AndroidUtilities.m1036dp(4.0f);
            float f = SHADOW_SIZE;
            paint.setShadowLayer(fM1036dp, 0.0f, f, 251658240);
            this.backgroundBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(this.backgroundBitmap);
            float f2 = i;
            RectF rectF = new RectF(f, f, f2 - f, getHeight() - f);
            float f3 = RADIUS;
            canvas.drawRoundRect(rectF, f3, f3, paint);
            if (this.setData) {
                Utilities.themeQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.QrActivity$QrView$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSizeChanged$0(i, i2);
                    }
                });
            }
            float fMax = Math.max((getWidth() * 1.0f) / this.gradientDrawable.getBitmap().getWidth(), (getHeight() * 1.0f) / this.gradientDrawable.getBitmap().getHeight());
            Matrix matrix = new Matrix();
            matrix.setScale(fMax, fMax);
            this.gradientShader.setLocalMatrix(matrix);
            Matrix matrix2 = new Matrix();
            matrix2.setScale(fMax, fMax);
            matrix2.postTranslate(f2 / 2.0f, getWidth() + AndroidUtilities.m1036dp(6.0f));
            this.gradientTextShader.setLocalMatrix(matrix2);
        }

        private void drawLoading(Canvas canvas) {
            QrCenterChangedListener qrCenterChangedListener;
            if (this.loadingMatrix != null) {
                int width = (getWidth() - AndroidUtilities.m1036dp(60.0f)) / 33;
                int i = (width * 33) + 32;
                int width2 = (getWidth() - i) / 2;
                int height = (int) (getHeight() * 0.15f);
                Point point = AndroidUtilities.displaySize;
                if (point.x > point.y) {
                    height = (int) (getHeight() * 0.09f);
                }
                int i2 = height;
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(0.0f, 0.0f, getWidth(), getHeight());
                canvas.saveLayerAlpha(rectF, 255, 31);
                int i3 = width2 + 16;
                int i4 = i2 + 16;
                canvas.drawRect(i3, i4, (getWidth() - width2) - 16, (((getWidth() + i2) - width2) - width2) - 16, this.bitmapGradientPaint);
                canvas.save();
                this.loadingMatrix.setBounds(i3, i4, (getWidth() - width2) - 16, (((getWidth() + i2) - width2) - width2) - 16);
                this.loadingMatrix.draw(canvas);
                canvas.restore();
                canvas.restore();
                float width3 = getWidth() / 2.0f;
                float f = i2;
                float f2 = width2;
                float width4 = ((getWidth() / 2.0f) + f) - f2;
                float fRound = ((Math.round((r8 / 4.65f) / r6) * width) / 2) * 0.75f;
                canvas.drawCircle(width3, width4, fRound, this.bitmapGradientPaint);
                QRCodeWriter.drawSideQuads(canvas, f2, f, this.bitmapGradientPaint, 7.0f, width, 16, i, 0.75f, this.radii, true);
                if (this.logoCenterSet || (qrCenterChangedListener = this.centerChangedListener) == null) {
                    return;
                }
                qrCenterChangedListener.onCenterChanged((int) (width3 - fRound), (int) (width4 - fRound), (int) (width3 + fRound), (int) (width4 + fRound));
                this.logoCenterSet = true;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:64:0x0087  */
        @Override // android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onDraw(android.graphics.Canvas r19) {
            /*
                Method dump skipped, instruction units count: 304
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.QrActivity.QrView.onDraw(android.graphics.Canvas):void");
        }

        public void setCenterChangedListener(QrCenterChangedListener qrCenterChangedListener) {
            this.centerChangedListener = qrCenterChangedListener;
        }

        public void setData(String str, String str2, boolean z, boolean z2) {
            this.setData = true;
            this.username = str2;
            this.isPhone = z;
            if (z2) {
                TLRPC.TL_exportedContactToken cachedContactToken = MessagesController.getInstance(UserConfig.selectedAccount).getCachedContactToken();
                if (cachedContactToken != null) {
                    this.link = cachedContactToken.url;
                    this.linkExpires = cachedContactToken.expires;
                } else {
                    this.link = null;
                }
            } else {
                this.link = str;
            }
            this.hasTimer = z2;
            final int width = getWidth();
            final int height = getHeight();
            Utilities.themeQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.QrActivity$QrView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setData$1(width, height);
                }
            });
            invalidate();
            this.checkTimerToken.run();
        }

        public /* synthetic */ void lambda$new$5() {
            AndroidUtilities.cancelRunOnUIThread(this.checkTimerToken);
            boolean z = this.hasTimer;
            if (z) {
                if (z && this.loadingMatrix == null) {
                    RLottieDrawable rLottieDrawable = new RLottieDrawable(C2797R.raw.qr_matrix, "qr_matrix", AndroidUtilities.m1036dp(200.0f), AndroidUtilities.m1036dp(200.0f));
                    this.loadingMatrix = rLottieDrawable;
                    rLottieDrawable.setMasterParent(this);
                    this.loadingMatrix.getPaint().setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                    this.loadingMatrix.setAutoRepeat(1);
                    this.loadingMatrix.start();
                }
                int i = this.linkExpires;
                String str = _UrlKt.FRAGMENT_ENCODE_SET;
                if (i == 0 || System.currentTimeMillis() / 1000 >= this.linkExpires) {
                    if (this.linkExpires != 0) {
                        this.link = null;
                        final int width = getWidth();
                        final int height = getHeight();
                        Utilities.themeQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.QrActivity$QrView$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$new$2(width, height);
                            }
                        });
                        this.timerTextDrawable.setText(_UrlKt.FRAGMENT_ENCODE_SET);
                    }
                    MessagesController.getInstance(UserConfig.selectedAccount).requestContactToken(this.linkExpires == 0 ? 750L : 1750L, new Utilities.Callback() { // from class: org.telegram.ui.QrActivity$QrView$$ExternalSyntheticLambda6
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            this.f$0.lambda$new$4((TLRPC.TL_exportedContactToken) obj);
                        }
                    });
                }
                int i2 = this.linkExpires;
                if (i2 > 0 && this.link != null) {
                    long jMax = Math.max(0L, (((long) i2) - (System.currentTimeMillis() / 1000)) - 1);
                    int i3 = (int) (jMax % 60);
                    int iMin = Math.min(99, (int) (jMax / 60));
                    AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = this.timerTextDrawable;
                    StringBuilder sb = new StringBuilder();
                    sb.append(iMin < 10 ? MVEL.VERSION_SUB : _UrlKt.FRAGMENT_ENCODE_SET);
                    sb.append(iMin);
                    sb.append(":");
                    if (i3 < 10) {
                        str = MVEL.VERSION_SUB;
                    }
                    sb.append(str);
                    sb.append(i3);
                    animatedTextDrawable.setText(sb.toString(), true, false);
                }
                if (isAttachedToWindow()) {
                    AndroidUtilities.runOnUIThread(this.checkTimerToken, 1000L);
                }
            }
        }

        public /* synthetic */ void lambda$new$4(final TLRPC.TL_exportedContactToken tL_exportedContactToken) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.QrActivity$QrView$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$3(tL_exportedContactToken);
                }
            });
        }

        public /* synthetic */ void lambda$new$3(TLRPC.TL_exportedContactToken tL_exportedContactToken) {
            if (tL_exportedContactToken == null) {
                return;
            }
            int i = this.linkExpires;
            if (i != 0 && i < tL_exportedContactToken.expires) {
                VibratorUtils.vibrate(100L);
            }
            this.linkExpires = tL_exportedContactToken.expires;
            setData(tL_exportedContactToken.url, null, false, true);
        }

        public void setColors(int i, int i2, int i3, int i4) {
            this.gradientDrawable.setColors(i, i2, i3, i4);
            invalidate();
        }

        public void setPosAnimationProgress(float f) {
            this.gradientDrawable.posAnimationProgress = f;
        }

        /* JADX INFO: renamed from: prepareContent, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
        public void lambda$setData$1(int i, int i2) {
            String upperCase;
            int i3;
            int imageSize;
            float f;
            int i4;
            StaticLayout staticLayoutCreateStaticLayout;
            int i5;
            int i6;
            Drawable drawable;
            int iWidth;
            int i7;
            Integer num;
            if (i == 0 || i2 == 0) {
                return;
            }
            if ((TextUtils.isEmpty(this.username) && !this.hasTimer) || TextUtils.isEmpty(this.link)) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.QrActivity$QrView$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$prepareContent$6();
                    }
                });
                return;
            }
            if (this.hasTimer) {
                upperCase = null;
            } else {
                boolean z = this.isPhone;
                upperCase = this.username;
                if (!z) {
                    upperCase = upperCase.toUpperCase();
                }
            }
            if (TextUtils.equals(upperCase, this.hadUserText) && TextUtils.equals(this.link, this.hadLink) && (num = this.hadWidth) != null && this.hadHeight != null && num.intValue() == i && this.hadHeight.intValue() == i2) {
                return;
            }
            final Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            TextPaint textPaint = new TextPaint(65);
            int i8 = -16777216;
            textPaint.setColor(-16777216);
            textPaint.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_CONDENSED_BOLD));
            int width = bitmapCreateBitmap.getWidth() - (AndroidUtilities.m1036dp(20.0f) * 2);
            if (this.hasTimer) {
                i3 = 3;
                imageSize = 0;
                f = 30.0f;
                i4 = i8;
                staticLayoutCreateStaticLayout = null;
            } else {
                int i9 = 0;
                while (i9 <= 2) {
                    if (i9 == 0) {
                        drawable = ContextCompat.getDrawable(getContext(), C2797R.drawable.qr_at_large);
                        textPaint.setTextSize(AndroidUtilities.m1036dp(30.0f));
                    } else if (i9 == 1) {
                        drawable = ContextCompat.getDrawable(getContext(), C2797R.drawable.qr_at_medium);
                        textPaint.setTextSize(AndroidUtilities.m1036dp(25.0f));
                    } else {
                        drawable = ContextCompat.getDrawable(getContext(), C2797R.drawable.qr_at_small);
                        textPaint.setTextSize(AndroidUtilities.m1036dp(19.0f));
                    }
                    if (drawable != null) {
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        drawable.setColorFilter(new PorterDuffColorFilter(i8, PorterDuff.Mode.SRC_IN));
                    }
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(" " + upperCase);
                    if (!this.isPhone) {
                        spannableStringBuilder.setSpan(new VerticalImageSpan(drawable), 0, 1, 33);
                    }
                    float fMeasureText = textPaint.measureText(spannableStringBuilder, 1, spannableStringBuilder.length()) + drawable.getBounds().width();
                    if (i9 > 1 || fMeasureText <= width) {
                        int i10 = fMeasureText > ((float) width) ? 2 : 1;
                        int iWidth2 = i10 > 1 ? (((int) (drawable.getBounds().width() + fMeasureText)) / 2) + AndroidUtilities.m1036dp(2.0f) : width;
                        if (iWidth2 > width) {
                            iWidth = (((int) (fMeasureText + drawable.getBounds().width())) / 3) + AndroidUtilities.m1036dp(4.0f);
                            i7 = 3;
                        } else {
                            iWidth = iWidth2;
                            i7 = i10;
                        }
                        imageSize = 0;
                        i3 = 3;
                        f = 30.0f;
                        i4 = -16777216;
                        staticLayoutCreateStaticLayout = StaticLayoutEx.createStaticLayout(spannableStringBuilder, textPaint, iWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false, null, Math.min(AndroidUtilities.m1036dp(10.0f) + iWidth, bitmapCreateBitmap.getWidth()), i7);
                    } else {
                        i9++;
                        i8 = -16777216;
                    }
                }
                i3 = 3;
                imageSize = 0;
                f = 30.0f;
                i4 = i8;
                staticLayoutCreateStaticLayout = null;
            }
            float lineCount = (staticLayoutCreateStaticLayout == null ? imageSize : staticLayoutCreateStaticLayout.getLineCount()) * (textPaint.descent() - textPaint.ascent());
            int iM1036dp = i - (AndroidUtilities.m1036dp(f) * 2);
            HashMap map = new HashMap();
            map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            map.put(EncodeHintType.MARGIN, Integer.valueOf(imageSize));
            QRCodeWriter qRCodeWriter = new QRCodeWriter();
            int i11 = i3;
            Bitmap bitmapEncode = null;
            while (i11 < 5) {
                try {
                    map.put(EncodeHintType.QR_VERSION, Integer.valueOf(i11));
                    int i12 = i11;
                    try {
                        i5 = i12;
                        try {
                            bitmapEncode = qRCodeWriter.encode(this.link, iM1036dp, iM1036dp, map, null, 0.75f, 16777215, i4);
                            i6 = i4;
                            try {
                                imageSize = qRCodeWriter.getImageSize();
                            } catch (Exception unused) {
                            }
                        } catch (Exception unused2) {
                            i6 = i4;
                        }
                    } catch (Exception unused3) {
                        i5 = i12;
                    }
                } catch (Exception unused4) {
                    i5 = i11;
                }
                if (bitmapEncode != null) {
                    break;
                }
                i4 = i6;
                i11 = i5 + 1;
            }
            Bitmap bitmap = bitmapEncode;
            final int i13 = imageSize;
            if (bitmap == null) {
                return;
            }
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.drawColor(16777215);
            float width2 = (i - bitmap.getWidth()) / 2.0f;
            float f2 = i2;
            float f3 = 0.15f * f2;
            if (staticLayoutCreateStaticLayout != null && staticLayoutCreateStaticLayout.getLineCount() == i3) {
                f3 = 0.13f * f2;
            }
            if (((ViewGroup) getParent()).getMeasuredWidth() >= ((ViewGroup) getParent()).getMeasuredHeight()) {
                f3 = f2 * 0.09f;
            }
            canvas.drawBitmap(bitmap, width2, f3, new Paint(i3));
            Paint paint = new Paint(1);
            paint.setColor(-16777216);
            final float width3 = width2 + (bitmap.getWidth() * 0.5f);
            final float width4 = (bitmap.getWidth() * 0.5f) + f3;
            canvas.drawCircle(width3, width4, i13 * 0.5f, paint);
            if (staticLayoutCreateStaticLayout != null) {
                canvas.save();
                canvas.translate((canvas.getWidth() - staticLayoutCreateStaticLayout.getWidth()) * 0.5f, ((bitmap.getHeight() + f3) + (((canvas.getHeight() - (f3 + bitmap.getHeight())) - lineCount) * 0.5f)) - AndroidUtilities.m1036dp(4.0f));
                staticLayoutCreateStaticLayout.draw(canvas);
                canvas.restore();
                bitmap.recycle();
            }
            this.hadWidth = Integer.valueOf(i);
            this.hadHeight = Integer.valueOf(i2);
            this.hadUserText = upperCase;
            this.hadLink = this.link;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.QrActivity$QrView$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$prepareContent$7(bitmapCreateBitmap, width3, i13, width4);
                }
            });
        }

        public /* synthetic */ void lambda$prepareContent$6() {
            this.firstPrepare = false;
            Bitmap bitmap = this.contentBitmap;
            if (bitmap != null) {
                this.contentBitmap = null;
                this.contentBitmapAlpha.set(0.0f, true);
                Bitmap bitmap2 = this.oldContentBitmap;
                if (bitmap2 != null) {
                    bitmap2.recycle();
                }
                this.oldContentBitmap = bitmap;
                invalidate();
            }
        }

        public /* synthetic */ void lambda$prepareContent$7(Bitmap bitmap, float f, int i, float f2) {
            Bitmap bitmap2 = this.contentBitmap;
            this.contentBitmap = bitmap.extractAlpha();
            if (!this.firstPrepare) {
                this.contentBitmapAlpha.set(0.0f, true);
            }
            this.firstPrepare = false;
            Bitmap bitmap3 = this.oldContentBitmap;
            if (bitmap3 != null) {
                bitmap3.recycle();
            }
            this.oldContentBitmap = bitmap2;
            QrCenterChangedListener qrCenterChangedListener = this.centerChangedListener;
            if (qrCenterChangedListener != null) {
                float f3 = i * 0.5f;
                qrCenterChangedListener.onCenterChanged((int) (f - f3), (int) (f2 - f3), (int) (f + f3), (int) (f2 + f3));
                this.logoCenterSet = true;
            }
            invalidate();
        }

        @Override // android.view.View
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            this.checkTimerToken.run();
        }

        @Override // android.view.View
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            RLottieDrawable rLottieDrawable = this.loadingMatrix;
            if (rLottieDrawable != null) {
                rLottieDrawable.stop();
                this.loadingMatrix.recycle(false);
                this.loadingMatrix = null;
            }
        }
    }

    public class ThemeListViewController implements NotificationCenter.NotificationCenterDelegate {
        public final ChatThemeBottomSheet.Adapter adapter;
        private final Drawable backgroundDrawable;
        private final View bottomShadow;
        private View changeDayNightView;
        private ValueAnimator changeDayNightViewAnimator;
        private float changeDayNightViewProgress;
        private final RLottieDrawable darkThemeDrawable;
        private final RLottieImageView darkThemeView;
        private boolean forceDark;
        private final BaseFragment fragment;
        protected boolean isLightDarkChangeAnimation;
        private OnItemSelectedListener itemSelectedListener;
        private LinearLayoutManager layoutManager;
        private boolean prevIsPortrait;
        public final FlickerLoadingView progressView;
        private final RecyclerListView recyclerView;
        public final FrameLayout rootLayout;
        public final TextView scanButton;
        public final ImageView scanButtonIcon;
        public final LinearLayout scanButtonWrap;
        private final LinearSmoothScroller scroller;
        public ChatThemeBottomSheet.ChatThemeItem selectedItem;
        public final TextView shareButton;
        public final TextView titleView;
        private final View topShadow;
        private final Window window;
        private final Paint backgroundPaint = new Paint(1);
        public int prevSelectedPosition = -1;

        public void setDarkTheme(boolean z) {
        }

        public ThemeListViewController(BaseFragment baseFragment, Window window) {
            this.fragment = baseFragment;
            this.window = window;
            Activity parentActivity = baseFragment.getParentActivity();
            this.scroller = new LinearSmoothScroller(parentActivity) { // from class: org.telegram.ui.QrActivity.ThemeListViewController.1
                final /* synthetic */ QrActivity val$this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C65591(Context parentActivity2, QrActivity qrActivity) {
                    super(parentActivity2);
                    qrActivity = qrActivity;
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public int calculateTimeForScrolling(int i) {
                    return super.calculateTimeForScrolling(i) * 6;
                }
            };
            Drawable drawableMutate = parentActivity2.getResources().getDrawable(C2797R.drawable.sheet_shadow_round).mutate();
            this.backgroundDrawable = drawableMutate;
            int themedColor = baseFragment.getThemedColor(Theme.key_dialogBackground);
            PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
            drawableMutate.setColorFilter(new PorterDuffColorFilter(themedColor, mode));
            C65602 c65602 = new FrameLayout(parentActivity2, QrActivity.this, baseFragment) { // from class: org.telegram.ui.QrActivity.ThemeListViewController.2
                private final Rect backgroundPadding;
                final /* synthetic */ BaseFragment val$fragment;
                final /* synthetic */ QrActivity val$this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C65602(Context parentActivity2, QrActivity qrActivity, BaseFragment baseFragment2) {
                    super(parentActivity2);
                    this.val$this$0 = qrActivity;
                    this.val$fragment = baseFragment2;
                    Rect rect = new Rect();
                    this.backgroundPadding = rect;
                    ThemeListViewController.this.backgroundPaint.setColor(baseFragment2.getThemedColor(Theme.key_windowBackgroundWhite));
                    ThemeListViewController.this.backgroundDrawable.setCallback(this);
                    ThemeListViewController.this.backgroundDrawable.getPadding(rect);
                    setPadding(0, rect.top + AndroidUtilities.m1036dp(8.0f), 0, rect.bottom);
                }

                @Override // android.widget.FrameLayout, android.view.View
                public void onMeasure(int i, int i2) {
                    Point point = AndroidUtilities.displaySize;
                    boolean z = point.x < point.y;
                    int iM1036dp = AndroidUtilities.m1036dp(12.0f);
                    ThemeListViewController themeListViewController = ThemeListViewController.this;
                    if (z) {
                        themeListViewController.recyclerView.setLayoutParams(LayoutHelper.createFrame(-1, 104.0f, 8388611, 0.0f, 44.0f, 0.0f, 0.0f));
                        ThemeListViewController.this.recyclerView.setPadding(iM1036dp, 0, iM1036dp, 0);
                        ThemeListViewController themeListViewController2 = ThemeListViewController.this;
                        if (themeListViewController2.scanButtonWrap != null) {
                            themeListViewController2.shareButton.setLayoutParams(LayoutHelper.createFrame(-1, 48.0f, 8388611, 16.0f, 162.0f, 16.0f, 72.0f));
                            ThemeListViewController.this.scanButtonWrap.setLayoutParams(LayoutHelper.createFrame(-1, 48.0f, 8388691, 16.0f, 162.0f, 16.0f, 16.0f));
                        } else {
                            themeListViewController2.shareButton.setLayoutParams(LayoutHelper.createFrame(-1, 48.0f, 8388611, 16.0f, 162.0f, 16.0f, 16.0f));
                        }
                    } else {
                        themeListViewController.recyclerView.setPadding(iM1036dp, iM1036dp / 2, iM1036dp, iM1036dp);
                        ThemeListViewController themeListViewController3 = ThemeListViewController.this;
                        if (themeListViewController3.scanButtonWrap != null) {
                            themeListViewController3.recyclerView.setLayoutParams(LayoutHelper.createFrame(-1, -1.0f, 8388611, 0.0f, 44.0f, 0.0f, 136.0f));
                            ThemeListViewController.this.shareButton.setLayoutParams(LayoutHelper.createFrame(-1, 48.0f, 80, 16.0f, 0.0f, 16.0f, 72.0f));
                            ThemeListViewController.this.scanButtonWrap.setLayoutParams(LayoutHelper.createFrame(-1, 48.0f, 80, 16.0f, 0.0f, 16.0f, 16.0f));
                        } else {
                            themeListViewController3.recyclerView.setLayoutParams(LayoutHelper.createFrame(-1, -1.0f, 8388611, 0.0f, 44.0f, 0.0f, 80.0f));
                            ThemeListViewController.this.shareButton.setLayoutParams(LayoutHelper.createFrame(-1, 48.0f, 80, 16.0f, 0.0f, 16.0f, 16.0f));
                        }
                    }
                    ThemeListViewController themeListViewController4 = ThemeListViewController.this;
                    if (z) {
                        themeListViewController4.bottomShadow.setVisibility(8);
                        ThemeListViewController.this.topShadow.setVisibility(8);
                    } else {
                        int i3 = themeListViewController4.shareButton != null ? 136 : 80;
                        themeListViewController4.bottomShadow.setVisibility(0);
                        ThemeListViewController.this.bottomShadow.setLayoutParams(LayoutHelper.createFrame(-1, AndroidUtilities.m1036dp(2.0f), 80, 0.0f, 0.0f, 0.0f, i3));
                        ThemeListViewController.this.topShadow.setVisibility(0);
                        ThemeListViewController.this.topShadow.setLayoutParams(LayoutHelper.createFrame(-1, AndroidUtilities.m1036dp(2.0f), 48, 0.0f, 44.0f, 0.0f, 0.0f));
                    }
                    if (ThemeListViewController.this.prevIsPortrait != z) {
                        RecyclerListView recyclerListView = ThemeListViewController.this.recyclerView;
                        ThemeListViewController themeListViewController5 = ThemeListViewController.this;
                        LinearLayoutManager layoutManager = themeListViewController5.getLayoutManager(z);
                        themeListViewController5.layoutManager = layoutManager;
                        recyclerListView.setLayoutManager(layoutManager);
                        ThemeListViewController.this.recyclerView.requestLayout();
                        ThemeListViewController themeListViewController6 = ThemeListViewController.this;
                        int i4 = themeListViewController6.prevSelectedPosition;
                        if (i4 != -1) {
                            themeListViewController6.setSelectedPosition(i4);
                        }
                        ThemeListViewController.this.prevIsPortrait = z;
                    }
                    super.onMeasure(i, i2);
                }

                @Override // android.view.ViewGroup, android.view.View
                public void dispatchDraw(Canvas canvas) {
                    if (ThemeListViewController.this.prevIsPortrait) {
                        ThemeListViewController.this.backgroundDrawable.setBounds(-this.backgroundPadding.left, 0, getWidth() + this.backgroundPadding.right, getHeight());
                        ThemeListViewController.this.backgroundDrawable.draw(canvas);
                    } else {
                        RectF rectF = AndroidUtilities.rectTmp;
                        rectF.set(0.0f, 0.0f, getWidth() + AndroidUtilities.m1036dp(14.0f), getHeight());
                        canvas.drawRoundRect(rectF, AndroidUtilities.m1036dp(14.0f), AndroidUtilities.m1036dp(14.0f), ThemeListViewController.this.backgroundPaint);
                    }
                    super.dispatchDraw(canvas);
                }

                @Override // android.view.View
                public boolean verifyDrawable(Drawable drawable) {
                    return drawable == ThemeListViewController.this.backgroundDrawable || super.verifyDrawable(drawable);
                }
            };
            this.rootLayout = c65602;
            TextView textView = new TextView(parentActivity2);
            this.titleView = textView;
            textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            textView.setLines(1);
            textView.setSingleLine(true);
            textView.setTextColor(baseFragment2.getThemedColor(Theme.key_dialogTextBlack));
            textView.setTextSize(1, 20.0f);
            textView.setTypeface(AndroidUtilities.bold());
            textView.setPadding(AndroidUtilities.m1036dp(21.0f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(21.0f), AndroidUtilities.m1036dp(8.0f));
            c65602.addView(textView, LayoutHelper.createFrame(-1, -2.0f, 8388659, 0.0f, 0.0f, 62.0f, 0.0f));
            int i = Theme.key_featuredStickers_addButton;
            int themedColor2 = baseFragment2.getThemedColor(i);
            int iM1036dp = AndroidUtilities.m1036dp(28.0f);
            RLottieDrawable rLottieDrawable = new RLottieDrawable(C2797R.raw.sun_outline, _UrlKt.FRAGMENT_ENCODE_SET + C2797R.raw.sun_outline, iM1036dp, iM1036dp, false, null);
            this.darkThemeDrawable = rLottieDrawable;
            this.forceDark = Theme.getActiveTheme().isDark() ^ true;
            setForceDark(Theme.getActiveTheme().isDark(), false);
            rLottieDrawable.setPlayInDirectionOfCustomEndFrame(true);
            rLottieDrawable.setColorFilter(new PorterDuffColorFilter(themedColor2, mode));
            C65613 c65613 = new RLottieImageView(parentActivity2) { // from class: org.telegram.ui.QrActivity.ThemeListViewController.3
                final /* synthetic */ QrActivity val$this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C65613(Context parentActivity2, QrActivity qrActivity) {
                    super(parentActivity2);
                    qrActivity = qrActivity;
                }

                @Override // android.view.View
                public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
                    if (QrActivity.this.isCurrentThemeDark) {
                        accessibilityNodeInfo.setText(LocaleController.getString(C2797R.string.AccDescrSwitchToDayTheme));
                    } else {
                        accessibilityNodeInfo.setText(LocaleController.getString(C2797R.string.AccDescrSwitchToNightTheme));
                    }
                }
            };
            this.darkThemeView = c65613;
            c65613.setAnimation(rLottieDrawable);
            c65613.setScaleType(ImageView.ScaleType.CENTER);
            c65613.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.QrActivity$ThemeListViewController$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$0(view);
                }
            });
            c65613.setAlpha(0.0f);
            c65613.setVisibility(4);
            c65602.addView(c65613, LayoutHelper.createFrame(44, 44.0f, 8388661, 0.0f, -2.0f, 7.0f, 0.0f));
            FlickerLoadingView flickerLoadingView = new FlickerLoadingView(parentActivity2, baseFragment2.getResourceProvider());
            this.progressView = flickerLoadingView;
            flickerLoadingView.setVisibility(0);
            c65602.addView(flickerLoadingView, LayoutHelper.createFrame(-1, 104.0f, 8388611, 0.0f, 44.0f, 0.0f, 0.0f));
            Point point = AndroidUtilities.displaySize;
            this.prevIsPortrait = point.x < point.y;
            RecyclerListView recyclerListView = new RecyclerListView(parentActivity2);
            this.recyclerView = recyclerListView;
            ChatThemeBottomSheet.Adapter adapter = new ChatThemeBottomSheet.Adapter(((BaseFragment) QrActivity.this).currentAccount, QrActivity.this.resourcesProvider, 2);
            this.adapter = adapter;
            recyclerListView.setAdapter(adapter);
            recyclerListView.setClipChildren(false);
            recyclerListView.setClipToPadding(false);
            recyclerListView.setItemAnimator(null);
            recyclerListView.setNestedScrollingEnabled(false);
            LinearLayoutManager layoutManager = getLayoutManager(this.prevIsPortrait);
            this.layoutManager = layoutManager;
            recyclerListView.setLayoutManager(layoutManager);
            recyclerListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.QrActivity$ThemeListViewController$$ExternalSyntheticLambda1
                @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                public final void onItemClick(View view, int i2) {
                    this.f$0.onItemClicked(view, i2);
                }
            });
            recyclerListView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.QrActivity.ThemeListViewController.4
                final /* synthetic */ QrActivity val$this$0;
                private int yScroll = 0;

                public C65624(QrActivity qrActivity) {
                    qrActivity = qrActivity;
                }

                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView, int i2, int i3) {
                    super.onScrolled(recyclerView, i2, i3);
                    this.yScroll += i3;
                    ThemeListViewController.this.topShadow.setAlpha((this.yScroll * 1.0f) / AndroidUtilities.m1036dp(6.0f));
                }
            });
            c65602.addView(recyclerListView);
            View view = new View(parentActivity2);
            this.topShadow = view;
            view.setAlpha(0.0f);
            view.setBackground(ContextCompat.getDrawable(parentActivity2, C2797R.drawable.shadowdown));
            view.setRotation(180.0f);
            c65602.addView(view);
            View view2 = new View(parentActivity2);
            this.bottomShadow = view2;
            view2.setBackground(ContextCompat.getDrawable(parentActivity2, C2797R.drawable.shadowdown));
            c65602.addView(view2);
            TextView textView2 = new TextView(parentActivity2);
            this.shareButton = textView2;
            textView2.setBackground(Theme.AdaptiveRipple.filledRect(baseFragment2.getThemedColor(i), 24.0f));
            TextUtils.TruncateAt truncateAt = TextUtils.TruncateAt.END;
            textView2.setEllipsize(truncateAt);
            textView2.setGravity(17);
            textView2.setLines(1);
            textView2.setSingleLine(true);
            textView2.setText(LocaleController.getString(C2797R.string.ShareQrCode));
            textView2.setTextColor(baseFragment2.getThemedColor(Theme.key_featuredStickers_buttonText));
            textView2.setTextSize(1, 15.0f);
            textView2.setTypeface(AndroidUtilities.bold());
            c65602.addView(textView2);
            if (UserConfig.getInstance(((BaseFragment) QrActivity.this).currentAccount).getClientUserId() == QrActivity.this.userId) {
                LinearLayout linearLayout = new LinearLayout(parentActivity2);
                this.scanButtonWrap = linearLayout;
                linearLayout.setBackground(Theme.AdaptiveRipple.createRect(ColorUtils.setAlphaComponent(Theme.AdaptiveRipple.calcRippleColor(baseFragment2.getThemedColor(i)), 25), 24.0f));
                linearLayout.setOrientation(0);
                linearLayout.setGravity(17);
                ImageView imageView = new ImageView(parentActivity2);
                this.scanButtonIcon = imageView;
                imageView.setLayoutParams(LayoutHelper.createLinear(24, 24, 17, 0, 0, 10, 0));
                imageView.setImageResource(C2797R.drawable.profile_qr_scan_24);
                imageView.setColorFilter(new PorterDuffColorFilter(baseFragment2.getThemedColor(i), mode));
                linearLayout.addView(imageView);
                TextView textView3 = new TextView(parentActivity2);
                this.scanButton = textView3;
                textView3.setEllipsize(truncateAt);
                textView3.setGravity(17);
                textView3.setLines(1);
                textView3.setSingleLine(true);
                textView3.setText(LocaleController.getString(C2797R.string.ScanQrCode));
                textView3.setTextColor(baseFragment2.getThemedColor(i));
                textView3.setTextSize(1, 15.0f);
                textView3.setTypeface(AndroidUtilities.bold());
                linearLayout.addView(textView3);
                c65602.addView(linearLayout);
                return;
            }
            this.scanButtonWrap = null;
            this.scanButtonIcon = null;
            this.scanButton = null;
        }

        /* JADX INFO: renamed from: org.telegram.ui.QrActivity$ThemeListViewController$1 */
        public class C65591 extends LinearSmoothScroller {
            final /* synthetic */ QrActivity val$this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C65591(Context parentActivity2, QrActivity qrActivity) {
                super(parentActivity2);
                qrActivity = qrActivity;
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateTimeForScrolling(int i) {
                return super.calculateTimeForScrolling(i) * 6;
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.QrActivity$ThemeListViewController$2 */
        public class C65602 extends FrameLayout {
            private final Rect backgroundPadding;
            final /* synthetic */ BaseFragment val$fragment;
            final /* synthetic */ QrActivity val$this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C65602(Context parentActivity2, QrActivity qrActivity, BaseFragment baseFragment2) {
                super(parentActivity2);
                this.val$this$0 = qrActivity;
                this.val$fragment = baseFragment2;
                Rect rect = new Rect();
                this.backgroundPadding = rect;
                ThemeListViewController.this.backgroundPaint.setColor(baseFragment2.getThemedColor(Theme.key_windowBackgroundWhite));
                ThemeListViewController.this.backgroundDrawable.setCallback(this);
                ThemeListViewController.this.backgroundDrawable.getPadding(rect);
                setPadding(0, rect.top + AndroidUtilities.m1036dp(8.0f), 0, rect.bottom);
            }

            @Override // android.widget.FrameLayout, android.view.View
            public void onMeasure(int i, int i2) {
                Point point = AndroidUtilities.displaySize;
                boolean z = point.x < point.y;
                int iM1036dp = AndroidUtilities.m1036dp(12.0f);
                ThemeListViewController themeListViewController = ThemeListViewController.this;
                if (z) {
                    themeListViewController.recyclerView.setLayoutParams(LayoutHelper.createFrame(-1, 104.0f, 8388611, 0.0f, 44.0f, 0.0f, 0.0f));
                    ThemeListViewController.this.recyclerView.setPadding(iM1036dp, 0, iM1036dp, 0);
                    ThemeListViewController themeListViewController2 = ThemeListViewController.this;
                    if (themeListViewController2.scanButtonWrap != null) {
                        themeListViewController2.shareButton.setLayoutParams(LayoutHelper.createFrame(-1, 48.0f, 8388611, 16.0f, 162.0f, 16.0f, 72.0f));
                        ThemeListViewController.this.scanButtonWrap.setLayoutParams(LayoutHelper.createFrame(-1, 48.0f, 8388691, 16.0f, 162.0f, 16.0f, 16.0f));
                    } else {
                        themeListViewController2.shareButton.setLayoutParams(LayoutHelper.createFrame(-1, 48.0f, 8388611, 16.0f, 162.0f, 16.0f, 16.0f));
                    }
                } else {
                    themeListViewController.recyclerView.setPadding(iM1036dp, iM1036dp / 2, iM1036dp, iM1036dp);
                    ThemeListViewController themeListViewController3 = ThemeListViewController.this;
                    if (themeListViewController3.scanButtonWrap != null) {
                        themeListViewController3.recyclerView.setLayoutParams(LayoutHelper.createFrame(-1, -1.0f, 8388611, 0.0f, 44.0f, 0.0f, 136.0f));
                        ThemeListViewController.this.shareButton.setLayoutParams(LayoutHelper.createFrame(-1, 48.0f, 80, 16.0f, 0.0f, 16.0f, 72.0f));
                        ThemeListViewController.this.scanButtonWrap.setLayoutParams(LayoutHelper.createFrame(-1, 48.0f, 80, 16.0f, 0.0f, 16.0f, 16.0f));
                    } else {
                        themeListViewController3.recyclerView.setLayoutParams(LayoutHelper.createFrame(-1, -1.0f, 8388611, 0.0f, 44.0f, 0.0f, 80.0f));
                        ThemeListViewController.this.shareButton.setLayoutParams(LayoutHelper.createFrame(-1, 48.0f, 80, 16.0f, 0.0f, 16.0f, 16.0f));
                    }
                }
                ThemeListViewController themeListViewController4 = ThemeListViewController.this;
                if (z) {
                    themeListViewController4.bottomShadow.setVisibility(8);
                    ThemeListViewController.this.topShadow.setVisibility(8);
                } else {
                    int i3 = themeListViewController4.shareButton != null ? 136 : 80;
                    themeListViewController4.bottomShadow.setVisibility(0);
                    ThemeListViewController.this.bottomShadow.setLayoutParams(LayoutHelper.createFrame(-1, AndroidUtilities.m1036dp(2.0f), 80, 0.0f, 0.0f, 0.0f, i3));
                    ThemeListViewController.this.topShadow.setVisibility(0);
                    ThemeListViewController.this.topShadow.setLayoutParams(LayoutHelper.createFrame(-1, AndroidUtilities.m1036dp(2.0f), 48, 0.0f, 44.0f, 0.0f, 0.0f));
                }
                if (ThemeListViewController.this.prevIsPortrait != z) {
                    RecyclerListView recyclerListView = ThemeListViewController.this.recyclerView;
                    ThemeListViewController themeListViewController5 = ThemeListViewController.this;
                    LinearLayoutManager layoutManager = themeListViewController5.getLayoutManager(z);
                    themeListViewController5.layoutManager = layoutManager;
                    recyclerListView.setLayoutManager(layoutManager);
                    ThemeListViewController.this.recyclerView.requestLayout();
                    ThemeListViewController themeListViewController6 = ThemeListViewController.this;
                    int i4 = themeListViewController6.prevSelectedPosition;
                    if (i4 != -1) {
                        themeListViewController6.setSelectedPosition(i4);
                    }
                    ThemeListViewController.this.prevIsPortrait = z;
                }
                super.onMeasure(i, i2);
            }

            @Override // android.view.ViewGroup, android.view.View
            public void dispatchDraw(Canvas canvas) {
                if (ThemeListViewController.this.prevIsPortrait) {
                    ThemeListViewController.this.backgroundDrawable.setBounds(-this.backgroundPadding.left, 0, getWidth() + this.backgroundPadding.right, getHeight());
                    ThemeListViewController.this.backgroundDrawable.draw(canvas);
                } else {
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set(0.0f, 0.0f, getWidth() + AndroidUtilities.m1036dp(14.0f), getHeight());
                    canvas.drawRoundRect(rectF, AndroidUtilities.m1036dp(14.0f), AndroidUtilities.m1036dp(14.0f), ThemeListViewController.this.backgroundPaint);
                }
                super.dispatchDraw(canvas);
            }

            @Override // android.view.View
            public boolean verifyDrawable(Drawable drawable) {
                return drawable == ThemeListViewController.this.backgroundDrawable || super.verifyDrawable(drawable);
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.QrActivity$ThemeListViewController$3 */
        public class C65613 extends RLottieImageView {
            final /* synthetic */ QrActivity val$this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C65613(Context parentActivity2, QrActivity qrActivity) {
                super(parentActivity2);
                qrActivity = qrActivity;
            }

            @Override // android.view.View
            public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
                if (QrActivity.this.isCurrentThemeDark) {
                    accessibilityNodeInfo.setText(LocaleController.getString(C2797R.string.AccDescrSwitchToDayTheme));
                } else {
                    accessibilityNodeInfo.setText(LocaleController.getString(C2797R.string.AccDescrSwitchToNightTheme));
                }
            }
        }

        public /* synthetic */ void lambda$new$0(View view) {
            if (this.changeDayNightViewAnimator != null) {
                return;
            }
            setupLightDarkTheme(!this.forceDark);
        }

        /* JADX INFO: renamed from: org.telegram.ui.QrActivity$ThemeListViewController$4 */
        public class C65624 extends RecyclerView.OnScrollListener {
            final /* synthetic */ QrActivity val$this$0;
            private int yScroll = 0;

            public C65624(QrActivity qrActivity) {
                qrActivity = qrActivity;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i2, int i3) {
                super.onScrolled(recyclerView, i2, i3);
                this.yScroll += i3;
                ThemeListViewController.this.topShadow.setAlpha((this.yScroll * 1.0f) / AndroidUtilities.m1036dp(6.0f));
            }
        }

        public void onCreate() {
            ChatThemeController chatThemeController = ChatThemeController.getInstance(((BaseFragment) QrActivity.this).currentAccount);
            chatThemeController.preloadAllWallpaperThumbs(true);
            chatThemeController.preloadAllWallpaperThumbs(false);
            chatThemeController.preloadAllWallpaperImages(true);
            chatThemeController.preloadAllWallpaperImages(false);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
        }

        @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
        @SuppressLint({"NotifyDataSetChanged"})
        public void didReceivedNotification(int i, int i2, Object... objArr) {
            if (i == NotificationCenter.emojiLoaded) {
                this.adapter.notifyDataSetChanged();
            }
        }

        public void onDestroy() {
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        }

        public void setItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
            this.itemSelectedListener = onItemSelectedListener;
        }

        public void onDataLoaded() {
            this.darkThemeView.setAlpha(0.0f);
            this.darkThemeView.animate().alpha(1.0f).setDuration(150L).start();
            this.darkThemeView.setVisibility(0);
            this.progressView.animate().alpha(0.0f).setListener(new HideViewAfterAnimation(this.progressView)).setDuration(150L).start();
            this.recyclerView.setAlpha(0.0f);
            this.recyclerView.animate().alpha(1.0f).setDuration(150L).start();
        }

        public void setSelectedPosition(int i) {
            this.prevSelectedPosition = i;
            this.adapter.setSelectedItem(i);
            if (i > 0 && i < this.adapter.items.size() / 2) {
                i--;
            }
            this.layoutManager.scrollToPositionWithOffset(Math.min(i, this.adapter.items.size() - 1), 0);
        }

        public void onItemClicked(View view, final int i) {
            if (this.adapter.items.get(i) == this.selectedItem || this.changeDayNightView != null) {
                return;
            }
            this.isLightDarkChangeAnimation = false;
            this.selectedItem = this.adapter.items.get(i);
            this.adapter.setSelectedItem(i);
            this.rootLayout.postDelayed(new Runnable() { // from class: org.telegram.ui.QrActivity$ThemeListViewController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onItemClicked$1(i);
                }
            }, 100L);
            for (int i2 = 0; i2 < this.recyclerView.getChildCount(); i2++) {
                ThemeSmallPreviewView themeSmallPreviewView = (ThemeSmallPreviewView) this.recyclerView.getChildAt(i2);
                if (themeSmallPreviewView != view) {
                    themeSmallPreviewView.cancelAnimation();
                }
            }
            if (!this.adapter.items.get(i).chatTheme.showAsDefaultStub) {
                ((ThemeSmallPreviewView) view).playEmojiAnimation();
            }
            OnItemSelectedListener onItemSelectedListener = this.itemSelectedListener;
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelected(this.selectedItem.chatTheme, i);
            }
        }

        public /* synthetic */ void lambda$onItemClicked$1(int i) {
            int iMax;
            RecyclerView.LayoutManager layoutManager = this.recyclerView.getLayoutManager();
            if (layoutManager != null) {
                if (!this.prevIsPortrait) {
                    iMax = i;
                } else if (i > this.prevSelectedPosition) {
                    iMax = Math.min(i + 1, this.adapter.items.size() - 1);
                } else {
                    iMax = Math.max(i - 1, 0);
                }
                this.scroller.setTargetPosition(iMax);
                layoutManager.startSmoothScroll(this.scroller);
            }
            this.prevSelectedPosition = i;
        }

        @SuppressLint({"NotifyDataSetChanged"})
        private void setupLightDarkTheme(final boolean z) {
            ValueAnimator valueAnimator = this.changeDayNightViewAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            FrameLayout frameLayout = (FrameLayout) this.fragment.getParentActivity().getWindow().getDecorView();
            FrameLayout frameLayout2 = (FrameLayout) this.window.getDecorView();
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(frameLayout2.getWidth(), frameLayout2.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            this.darkThemeView.setAlpha(0.0f);
            frameLayout.draw(canvas);
            frameLayout2.draw(canvas);
            this.darkThemeView.setAlpha(1.0f);
            Paint paint = new Paint(1);
            paint.setColor(-16777216);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            Paint paint2 = new Paint(1);
            paint2.setFilterBitmap(true);
            int[] iArr = new int[2];
            this.darkThemeView.getLocationInWindow(iArr);
            float f = iArr[0];
            float f2 = iArr[1];
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            paint2.setShader(new BitmapShader(bitmapCreateBitmap, tileMode, tileMode));
            this.changeDayNightView = new View(this.fragment.getParentActivity()) { // from class: org.telegram.ui.QrActivity.ThemeListViewController.5
                final /* synthetic */ Bitmap val$bitmap;
                final /* synthetic */ Canvas val$bitmapCanvas;
                final /* synthetic */ Paint val$bitmapPaint;
                final /* synthetic */ float val$cx;
                final /* synthetic */ float val$cy;
                final /* synthetic */ boolean val$isDark;
                final /* synthetic */ float val$r;
                final /* synthetic */ float val$x;
                final /* synthetic */ Paint val$xRefPaint;
                final /* synthetic */ float val$y;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C65635(Context context, final boolean z2, Canvas canvas2, float f3, float f4, float f5, Paint paint3, Bitmap bitmapCreateBitmap2, Paint paint22, float f6, float f22) {
                    super(context);
                    z = z2;
                    canvas = canvas2;
                    f = f3;
                    f = f4;
                    f = f5;
                    paint = paint3;
                    bitmap = bitmapCreateBitmap2;
                    paint = paint22;
                    f = f6;
                    f = f22;
                }

                @Override // android.view.View
                public void onDraw(Canvas canvas2) {
                    super.onDraw(canvas2);
                    if (z) {
                        if (ThemeListViewController.this.changeDayNightViewProgress > 0.0f) {
                            canvas.drawCircle(f, f, f * ThemeListViewController.this.changeDayNightViewProgress, paint);
                        }
                        canvas2.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                    } else {
                        canvas2.drawCircle(f, f, f * (1.0f - ThemeListViewController.this.changeDayNightViewProgress), paint);
                    }
                    canvas2.save();
                    canvas2.translate(f, f);
                    ThemeListViewController.this.darkThemeView.draw(canvas2);
                    canvas2.restore();
                }
            };
            this.changeDayNightViewProgress = 0.0f;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.changeDayNightViewAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.QrActivity$ThemeListViewController$$ExternalSyntheticLambda3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    this.f$0.lambda$setupLightDarkTheme$2(valueAnimator2);
                }
            });
            this.changeDayNightViewAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.QrActivity.ThemeListViewController.6
                public C65646() {
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (ThemeListViewController.this.changeDayNightView != null) {
                        if (ThemeListViewController.this.changeDayNightView.getParent() != null) {
                            ((ViewGroup) ThemeListViewController.this.changeDayNightView.getParent()).removeView(ThemeListViewController.this.changeDayNightView);
                        }
                        ThemeListViewController.this.changeDayNightView = null;
                    }
                    ThemeListViewController.this.changeDayNightViewAnimator = null;
                    super.onAnimationEnd(animator);
                }
            });
            this.changeDayNightViewAnimator.setDuration(400L);
            this.changeDayNightViewAnimator.setInterpolator(Easings.easeInOutQuad);
            this.changeDayNightViewAnimator.start();
            frameLayout2.addView(this.changeDayNightView, new ViewGroup.LayoutParams(-1, -1));
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.QrActivity$ThemeListViewController$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setupLightDarkTheme$3(z2);
                }
            });
        }

        /* JADX INFO: renamed from: org.telegram.ui.QrActivity$ThemeListViewController$5 */
        public class C65635 extends View {
            final /* synthetic */ Bitmap val$bitmap;
            final /* synthetic */ Canvas val$bitmapCanvas;
            final /* synthetic */ Paint val$bitmapPaint;
            final /* synthetic */ float val$cx;
            final /* synthetic */ float val$cy;
            final /* synthetic */ boolean val$isDark;
            final /* synthetic */ float val$r;
            final /* synthetic */ float val$x;
            final /* synthetic */ Paint val$xRefPaint;
            final /* synthetic */ float val$y;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C65635(Context context, final boolean z2, Canvas canvas2, float f3, float f4, float f5, Paint paint3, Bitmap bitmapCreateBitmap2, Paint paint22, float f6, float f22) {
                super(context);
                z = z2;
                canvas = canvas2;
                f = f3;
                f = f4;
                f = f5;
                paint = paint3;
                bitmap = bitmapCreateBitmap2;
                paint = paint22;
                f = f6;
                f = f22;
            }

            @Override // android.view.View
            public void onDraw(Canvas canvas2) {
                super.onDraw(canvas2);
                if (z) {
                    if (ThemeListViewController.this.changeDayNightViewProgress > 0.0f) {
                        canvas.drawCircle(f, f, f * ThemeListViewController.this.changeDayNightViewProgress, paint);
                    }
                    canvas2.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                } else {
                    canvas2.drawCircle(f, f, f * (1.0f - ThemeListViewController.this.changeDayNightViewProgress), paint);
                }
                canvas2.save();
                canvas2.translate(f, f);
                ThemeListViewController.this.darkThemeView.draw(canvas2);
                canvas2.restore();
            }
        }

        public /* synthetic */ void lambda$setupLightDarkTheme$2(ValueAnimator valueAnimator) {
            this.changeDayNightViewProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.changeDayNightView.invalidate();
        }

        /* JADX INFO: renamed from: org.telegram.ui.QrActivity$ThemeListViewController$6 */
        public class C65646 extends AnimatorListenerAdapter {
            public C65646() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (ThemeListViewController.this.changeDayNightView != null) {
                    if (ThemeListViewController.this.changeDayNightView.getParent() != null) {
                        ((ViewGroup) ThemeListViewController.this.changeDayNightView.getParent()).removeView(ThemeListViewController.this.changeDayNightView);
                    }
                    ThemeListViewController.this.changeDayNightView = null;
                }
                ThemeListViewController.this.changeDayNightViewAnimator = null;
                super.onAnimationEnd(animator);
            }
        }

        public /* synthetic */ void lambda$setupLightDarkTheme$3(boolean z) {
            ChatThemeBottomSheet.Adapter adapter = this.adapter;
            if (adapter == null || adapter.items == null) {
                return;
            }
            setForceDark(z, true);
            if (this.selectedItem != null) {
                this.isLightDarkChangeAnimation = true;
                setDarkTheme(z);
            }
            if (this.adapter.items != null) {
                for (int i = 0; i < this.adapter.items.size(); i++) {
                    this.adapter.items.get(i).themeIndex = z ? 1 : 0;
                    this.adapter.items.get(i).icon = QrActivity.this.getEmojiThemeIcon(this.adapter.items.get(i).chatTheme, z);
                }
                QrActivity.this.tempMotionDrawable = null;
                this.adapter.notifyDataSetChanged();
            }
        }

        public void setForceDark(boolean z, boolean z2) {
            if (this.forceDark == z) {
                return;
            }
            this.forceDark = z;
            int framesCount = z ? this.darkThemeDrawable.getFramesCount() - 1 : 0;
            RLottieDrawable rLottieDrawable = this.darkThemeDrawable;
            if (z2) {
                rLottieDrawable.setCustomEndFrame(framesCount);
                RLottieImageView rLottieImageView = this.darkThemeView;
                if (rLottieImageView != null) {
                    rLottieImageView.playAnimation();
                    return;
                }
                return;
            }
            rLottieDrawable.setCustomEndFrame(framesCount);
            this.darkThemeDrawable.setCurrentFrame(framesCount, false, true);
            RLottieImageView rLottieImageView2 = this.darkThemeView;
            if (rLottieImageView2 != null) {
                rLottieImageView2.invalidate();
            }
        }

        public LinearLayoutManager getLayoutManager(boolean z) {
            BaseFragment baseFragment = this.fragment;
            if (z) {
                return new LinearLayoutManager(baseFragment.getParentActivity(), 0, false);
            }
            return new GridLayoutManager(baseFragment.getParentActivity(), 3, 1, false);
        }

        public void onAnimationStart() {
            List<ChatThemeBottomSheet.ChatThemeItem> list;
            ChatThemeBottomSheet.Adapter adapter = this.adapter;
            if (adapter != null && (list = adapter.items) != null) {
                Iterator<ChatThemeBottomSheet.ChatThemeItem> it = list.iterator();
                while (it.hasNext()) {
                    it.next().themeIndex = this.forceDark ? 1 : 0;
                }
            }
            if (this.isLightDarkChangeAnimation) {
                return;
            }
            setItemsAnimationProgress(1.0f);
        }

        public void setItemsAnimationProgress(float f) {
            for (int i = 0; i < this.adapter.getItemCount(); i++) {
                this.adapter.items.get(i).animationProgress = f;
            }
        }

        public void onAnimationEnd() {
            this.isLightDarkChangeAnimation = false;
        }

        /* JADX INFO: renamed from: org.telegram.ui.QrActivity$ThemeListViewController$7 */
        public class C65657 implements ThemeDescription.ThemeDescriptionDelegate {
            private boolean isAnimationStarted = false;

            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public void didSetColor() {
            }

            public C65657() {
            }

            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public void onAnimationProgress(float f) {
                if (f == 0.0f && !this.isAnimationStarted) {
                    ThemeListViewController.this.onAnimationStart();
                    this.isAnimationStarted = true;
                }
                ThemeListViewController.this.darkThemeDrawable.setColorFilter(new PorterDuffColorFilter(ThemeListViewController.this.fragment.getThemedColor(Theme.key_featuredStickers_addButton), PorterDuff.Mode.MULTIPLY));
                ThemeListViewController themeListViewController = ThemeListViewController.this;
                if (themeListViewController.isLightDarkChangeAnimation) {
                    themeListViewController.setItemsAnimationProgress(f);
                }
                if (f == 1.0f && this.isAnimationStarted) {
                    ThemeListViewController themeListViewController2 = ThemeListViewController.this;
                    themeListViewController2.isLightDarkChangeAnimation = false;
                    themeListViewController2.onAnimationEnd();
                    this.isAnimationStarted = false;
                }
            }
        }

        public ArrayList<ThemeDescription> getThemeDescriptions() {
            C65657 c65657 = new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.QrActivity.ThemeListViewController.7
                private boolean isAnimationStarted = false;

                @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
                public void didSetColor() {
                }

                public C65657() {
                }

                @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
                public void onAnimationProgress(float f) {
                    if (f == 0.0f && !this.isAnimationStarted) {
                        ThemeListViewController.this.onAnimationStart();
                        this.isAnimationStarted = true;
                    }
                    ThemeListViewController.this.darkThemeDrawable.setColorFilter(new PorterDuffColorFilter(ThemeListViewController.this.fragment.getThemedColor(Theme.key_featuredStickers_addButton), PorterDuff.Mode.MULTIPLY));
                    ThemeListViewController themeListViewController = ThemeListViewController.this;
                    if (themeListViewController.isLightDarkChangeAnimation) {
                        themeListViewController.setItemsAnimationProgress(f);
                    }
                    if (f == 1.0f && this.isAnimationStarted) {
                        ThemeListViewController themeListViewController2 = ThemeListViewController.this;
                        themeListViewController2.isLightDarkChangeAnimation = false;
                        themeListViewController2.onAnimationEnd();
                        this.isAnimationStarted = false;
                    }
                }
            };
            ArrayList<ThemeDescription> arrayList = new ArrayList<>();
            int i = ThemeDescription.FLAG_BACKGROUND;
            Paint paint = this.backgroundPaint;
            int i2 = Theme.key_dialogBackground;
            arrayList.add(new ThemeDescription(null, i, null, paint, null, null, i2));
            arrayList.add(new ThemeDescription(null, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, new Drawable[]{this.backgroundDrawable}, c65657, i2));
            arrayList.add(new ThemeDescription(this.titleView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_dialogTextBlack));
            arrayList.add(new ThemeDescription(this.recyclerView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{ThemeSmallPreviewView.class}, null, null, null, Theme.key_dialogBackgroundGray));
            int size = arrayList.size();
            int i3 = 0;
            while (i3 < size) {
                ThemeDescription themeDescription = arrayList.get(i3);
                i3++;
                themeDescription.resourcesProvider = this.fragment.getResourceProvider();
            }
            return arrayList;
        }
    }
}
