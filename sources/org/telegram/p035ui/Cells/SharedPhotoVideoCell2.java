package org.telegram.p035ui.Cells;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import androidx.core.content.ContextCompat;
import androidx.core.math.MathUtils;
import java.util.HashMap;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.DownloadController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.AnimatedFloat;
import org.telegram.p035ui.Components.AnimatedTextView;
import org.telegram.p035ui.Components.CanvasButton;
import org.telegram.p035ui.Components.CheckBoxBase;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.FlickerLoadingView;
import org.telegram.p035ui.Components.Shaker;
import org.telegram.p035ui.Components.Text;
import org.telegram.p035ui.Components.spoilers.SpoilerEffect;
import org.telegram.p035ui.Components.spoilers.SpoilerEffect2;
import org.telegram.p035ui.Stories.recorder.StoryPrivacyBottomSheet;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_stories;

/* JADX INFO: loaded from: classes3.dex */
public class SharedPhotoVideoCell2 extends FrameLayout {
    static boolean lastAutoDownload;
    static long lastUpdateDownloadSettingsTime;
    private final AnimatedFloat animatedProgress;
    private final AnimatedFloat animatedReordering;
    ValueAnimator animator;
    private boolean attached;
    private Text authorText;
    public ImageReceiver blurImageReceiver;
    private final RectF bounds;
    CanvasButton canvasButton;
    private boolean check2;
    CheckBoxBase checkBoxBase;
    float checkBoxProgress;
    private final Path chipPath;
    private final float[] chipRadii;
    private Path clipPath;
    float crossfadeProgress;
    float crossfadeToColumnsCount;
    SharedPhotoVideoCell2 crossfadeView;
    int currentAccount;
    MessageObject currentMessageObject;
    int currentParentColumnsCount;
    boolean drawVideoIcon;
    boolean drawViews;
    FlickerLoadingView globalGradientView;
    private Drawable gradientDrawable;
    private boolean gradientDrawableLoading;
    float highlightProgress;
    float imageAlpha;
    public ImageReceiver imageReceiver;
    public int imageReceiverColor;
    public ImageReceiver imageReceiverFullSize;
    float imageScale;
    public boolean isFirst;
    public boolean isLast;
    public boolean isSearchingHashtag;
    public boolean isStory;
    public boolean isStoryPinned;
    public boolean isStoryUploading;
    public boolean isTop;
    private SpoilerEffect mediaSpoilerEffect;
    private SpoilerEffect2 mediaSpoilerEffect2;
    private Path path;
    private Bitmap privacyBitmap;
    private Paint privacyPaint;
    private int privacyType;
    private final Paint progressPaint;
    private final Path rectPath;
    private boolean reorder;
    private boolean reordering;
    private final Paint scrimPaint;
    private Text sensitiveText;
    private Text sensitiveTextShort;
    private Text sensitiveTextShort2;
    private Shaker shaker;
    SharedResources sharedResources;
    boolean showLivePhoto;
    boolean showVideoLayout;
    private final Path spoilerClipPath;
    private final float[] spoilerClipRadii;
    private float spoilerMaxRadius;
    private float spoilerRevealProgress;
    private float spoilerRevealX;
    private float spoilerRevealY;
    public int storyId;
    private int style;
    StaticLayout videoInfoLayot;
    String videoText;
    AnimatedFloat viewsAlpha;
    AnimatedTextView.AnimatedTextDrawable viewsText;

    /* JADX INFO: renamed from: onCheckBoxPressed */
    public void lambda$setStyle$1() {
    }

    public void setCheck2() {
        this.check2 = true;
    }

    public void setReorder(boolean z) {
        this.reorder = z;
        invalidate();
    }

    public SharedPhotoVideoCell2(Context context, SharedResources sharedResources, int i) {
        super(context);
        this.imageReceiverColor = 0;
        this.imageReceiverFullSize = new ImageReceiver();
        this.imageReceiver = new ImageReceiver();
        this.blurImageReceiver = new ImageReceiver();
        this.imageAlpha = 1.0f;
        this.imageScale = 1.0f;
        this.drawVideoIcon = true;
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
        this.viewsAlpha = new AnimatedFloat(this, 0L, 350L, cubicBezierInterpolator);
        this.viewsText = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
        this.path = new Path();
        this.rectPath = new Path();
        this.chipPath = new Path();
        this.chipRadii = new float[8];
        this.spoilerClipPath = new Path();
        this.spoilerClipRadii = new float[8];
        this.style = 0;
        this.scrimPaint = new Paint(1);
        this.progressPaint = new Paint(1);
        this.animatedProgress = new AnimatedFloat(this, 0L, 200L, cubicBezierInterpolator);
        this.bounds = new RectF();
        this.animatedReordering = new AnimatedFloat(this, 0L, 320L, cubicBezierInterpolator);
        this.sharedResources = sharedResources;
        this.currentAccount = i;
        setChecked(false, false);
        this.imageReceiver.setParentView(this);
        this.imageReceiverFullSize.setParentView(this);
        this.blurImageReceiver.setParentView(this);
        this.imageReceiver.setDelegate(new ImageReceiver.ImageReceiverDelegate() { // from class: org.telegram.ui.Cells.SharedPhotoVideoCell2$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
            public final void didSetImage(ImageReceiver imageReceiver, boolean z, boolean z2, boolean z3) {
                this.f$0.lambda$new$0(imageReceiver, z, z2, z3);
            }
        });
        this.viewsText.setCallback(this);
        this.viewsText.setTextSize(AndroidUtilities.m1036dp(12.0f));
        this.viewsText.setTextColor(Theme.isCurrentThemeMonet() ? Theme.getColor(Theme.key_chat_mediaTimeText) : -1);
        this.viewsText.setTypeface(AndroidUtilities.bold());
        this.viewsText.setOverrideFullWidth(AndroidUtilities.displaySize.x);
        setWillNotDraw(false);
    }

    public /* synthetic */ void lambda$new$0(ImageReceiver imageReceiver, boolean z, boolean z2, boolean z3) {
        MessageObject messageObject;
        if (z && !z2 && (messageObject = this.currentMessageObject) != null && messageObject.hasMediaSpoilers() && this.imageReceiver.getBitmap() != null) {
            if (this.blurImageReceiver.getBitmap() != null) {
                this.blurImageReceiver.getBitmap().recycle();
            }
            this.blurImageReceiver.setImageBitmap(Utilities.stackBlurBitmapMax(this.imageReceiver.getBitmap()));
        }
        if (!z || z2 || !this.check2 || this.imageReceiver.getBitmap() == null) {
            return;
        }
        int dominantColor = AndroidUtilities.getDominantColor(this.imageReceiver.getBitmap());
        this.imageReceiverColor = dominantColor;
        CheckBoxBase checkBoxBase = this.checkBoxBase;
        if (checkBoxBase != null) {
            checkBoxBase.setBackgroundColor(Theme.blendOver(dominantColor, Theme.multAlpha(-1, 0.25f)));
        }
    }

    public void setStyle(int i) {
        if (this.style == i) {
            return;
        }
        this.style = i;
        if (i == 1) {
            CheckBoxBase checkBoxBase = new CheckBoxBase(this, 21, null);
            this.checkBoxBase = checkBoxBase;
            checkBoxBase.setColor(-1, Theme.key_sharedMedia_photoPlaceholder, Theme.key_checkboxCheck);
            this.checkBoxBase.setDrawUnchecked(true);
            this.checkBoxBase.setBackgroundType(0);
            this.checkBoxBase.setBounds(0, 0, AndroidUtilities.m1036dp(24.0f), AndroidUtilities.m1036dp(24.0f));
            if (this.attached) {
                this.checkBoxBase.onAttachedToWindow();
            }
            CanvasButton canvasButton = new CanvasButton(this);
            this.canvasButton = canvasButton;
            canvasButton.setDelegate(new Runnable() { // from class: org.telegram.ui.Cells.SharedPhotoVideoCell2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setStyle$1();
                }
            });
        }
    }

    private TLRPC.MessageMedia getStoryMedia(MessageObject messageObject) {
        TL_stories.StoryItem storyItem;
        if (messageObject == null || (storyItem = messageObject.storyItem) == null) {
            return null;
        }
        return storyItem.media;
    }

    private boolean mediaEqual(TLRPC.MessageMedia messageMedia, TLRPC.MessageMedia messageMedia2) {
        TLRPC.Photo photo;
        if (messageMedia == null && messageMedia2 == null) {
            return true;
        }
        if (messageMedia != null && messageMedia2 != null) {
            TLRPC.Document document = messageMedia.document;
            if (document != null) {
                TLRPC.Document document2 = messageMedia2.document;
                return document2 != null && document2.f1253id == document.f1253id;
            }
            TLRPC.Photo photo2 = messageMedia.photo;
            if (photo2 != null && (photo = messageMedia2.photo) != null && photo.f1276id == photo2.f1276id) {
                return true;
            }
        }
        return false;
    }

    private int getPrivacyType(MessageObject messageObject) {
        TL_stories.StoryItem storyItem;
        if (this.isStoryPinned) {
            return 100;
        }
        if (!this.isStory || messageObject == null || (storyItem = messageObject.storyItem) == null) {
            return -1;
        }
        if (storyItem.parsedPrivacy == null) {
            storyItem.parsedPrivacy = new StoryPrivacyBottomSheet.StoryPrivacy(this.currentAccount, storyItem.privacy);
        }
        int i = messageObject.storyItem.parsedPrivacy.type;
        if (i == 2 || i == 1 || i == 3) {
            return i;
        }
        return -1;
    }

    public void setMessageObject(MessageObject messageObject, int i) {
        setMessageObject(messageObject, i, false);
    }

    public void initFullSizeReceiver() {
        setMessageObject(this.currentMessageObject, this.currentParentColumnsCount, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:423:0x03ea  */
    /* JADX WARN: Removed duplicated region for block: B:433:0x041d  */
    /* JADX WARN: Removed duplicated region for block: B:436:0x0430  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setMessageObject(org.telegram.messenger.MessageObject r21, int r22, boolean r23) {
        /*
            Method dump skipped, instruction units count: 1146
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Cells.SharedPhotoVideoCell2.setMessageObject(org.telegram.messenger.MessageObject, int, boolean):void");
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        try {
            if (this.currentMessageObject != null) {
                accessibilityNodeInfo.setEnabled(true);
                accessibilityNodeInfo.setClickable(true);
                accessibilityNodeInfo.addAction(16);
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x00a6 A[Catch: Exception -> 0x0009, TryCatch #1 {Exception -> 0x0009, blocks: (B:60:0x0001, B:62:0x0005, B:66:0x000c, B:68:0x0017, B:70:0x001b, B:71:0x0024, B:73:0x002b, B:86:0x0064, B:89:0x006c, B:90:0x006f, B:92:0x0074, B:94:0x0080, B:96:0x0084, B:98:0x0088, B:100:0x008c, B:101:0x00a0, B:103:0x00a6, B:104:0x00bf, B:75:0x0033, B:77:0x0039, B:78:0x0043, B:79:0x0045, B:81:0x004b, B:84:0x0052, B:85:0x0059), top: B:112:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x006c A[Catch: Exception -> 0x0009, TRY_ENTER, TryCatch #1 {Exception -> 0x0009, blocks: (B:60:0x0001, B:62:0x0005, B:66:0x000c, B:68:0x0017, B:70:0x001b, B:71:0x0024, B:73:0x002b, B:86:0x0064, B:89:0x006c, B:90:0x006f, B:92:0x0074, B:94:0x0080, B:96:0x0084, B:98:0x0088, B:100:0x008c, B:101:0x00a0, B:103:0x00a6, B:104:0x00bf, B:75:0x0033, B:77:0x0039, B:78:0x0043, B:79:0x0045, B:81:0x004b, B:84:0x0052, B:85:0x0059), top: B:112:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0074 A[Catch: Exception -> 0x0009, TryCatch #1 {Exception -> 0x0009, blocks: (B:60:0x0001, B:62:0x0005, B:66:0x000c, B:68:0x0017, B:70:0x001b, B:71:0x0024, B:73:0x002b, B:86:0x0064, B:89:0x006c, B:90:0x006f, B:92:0x0074, B:94:0x0080, B:96:0x0084, B:98:0x0088, B:100:0x008c, B:101:0x00a0, B:103:0x00a6, B:104:0x00bf, B:75:0x0033, B:77:0x0039, B:78:0x0043, B:79:0x0045, B:81:0x004b, B:84:0x0052, B:85:0x0059), top: B:112:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateAccessibilityDescription() {
        /*
            Method dump skipped, instruction units count: 206
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Cells.SharedPhotoVideoCell2.updateAccessibilityDescription():void");
    }

    private void setPrivacyType(int i) {
        int i2;
        if (this.privacyType == i) {
            return;
        }
        this.privacyType = i;
        this.privacyBitmap = null;
        if (i == 1) {
            i2 = C2797R.drawable.msg_stories_closefriends;
        } else if (i == 2) {
            i2 = C2797R.drawable.msg_folders_private;
        } else if (i != 3) {
            i2 = i != 100 ? 0 : C2797R.drawable.msg_pin_mini;
        } else {
            i2 = C2797R.drawable.msg_folders_groups;
        }
        if (i2 != 0) {
            this.privacyBitmap = this.sharedResources.getPrivacyBitmap(getContext(), i2);
        }
        invalidate();
    }

    private boolean canAutoDownload(MessageObject messageObject) {
        if (System.currentTimeMillis() - lastUpdateDownloadSettingsTime > 5000) {
            lastUpdateDownloadSettingsTime = System.currentTimeMillis();
            lastAutoDownload = DownloadController.getInstance(this.currentAccount).canDownloadMedia(messageObject);
        }
        return lastAutoDownload;
    }

    public void setVideoText(String str, boolean z) {
        StaticLayout staticLayout;
        this.videoText = str;
        boolean z2 = str != null;
        this.showVideoLayout = z2;
        this.showLivePhoto = false;
        if (z2 && (staticLayout = this.videoInfoLayot) != null && !staticLayout.getText().toString().equals(str)) {
            this.videoInfoLayot = null;
        }
        this.drawVideoIcon = z;
    }

    private float getPadding() {
        float fDpf2;
        float fDpf22;
        float f;
        if (this.crossfadeProgress != 0.0f) {
            float f2 = this.crossfadeToColumnsCount;
            if (f2 == 9.0f || this.currentParentColumnsCount == 9) {
                if (f2 == 9.0f) {
                    fDpf2 = AndroidUtilities.dpf2(1.0f) * this.crossfadeProgress;
                    fDpf22 = AndroidUtilities.dpf2(2.0f);
                    f = this.crossfadeProgress;
                } else {
                    fDpf2 = AndroidUtilities.dpf2(2.0f) * this.crossfadeProgress;
                    fDpf22 = AndroidUtilities.dpf2(1.0f);
                    f = this.crossfadeProgress;
                }
                return fDpf2 + (fDpf22 * (1.0f - f));
            }
        }
        return this.currentParentColumnsCount == 9 ? AndroidUtilities.dpf2(1.0f) : AndroidUtilities.dpf2(2.0f);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawImpl(canvas, false, 1.0f, 1.0f, 1.0f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:448:0x0786  */
    /* JADX WARN: Type inference failed for: r10v16 */
    /* JADX WARN: Type inference failed for: r10v17 */
    /* JADX WARN: Type inference failed for: r10v18 */
    /* JADX WARN: Type inference failed for: r10v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v11 */
    /* JADX WARN: Type inference failed for: r14v2, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void drawImpl(android.graphics.Canvas r27, boolean r28, float r29, float r30, float r31) {
        /*
            Method dump skipped, instruction units count: 1963
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Cells.SharedPhotoVideoCell2.drawImpl(android.graphics.Canvas, boolean, float, float, float):void");
    }

    public /* synthetic */ void lambda$drawImpl$2(int[] iArr) {
        if (this.gradientDrawableLoading) {
            this.gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, iArr);
            invalidate();
            this.gradientDrawableLoading = false;
        }
    }

    public void customDraw(View view, Canvas canvas, float f, float f2, float f3) {
        canvas.save();
        if (this.clipPath == null) {
            this.clipPath = new Path();
        }
        this.clipPath.rewind();
        RectF rectF = AndroidUtilities.rectTmp;
        rectF.set(0.0f, 0.0f, f, f2);
        float fM1036dp = AndroidUtilities.m1036dp(12.0f) * f3;
        this.clipPath.addRoundRect(rectF, fM1036dp, fM1036dp, Path.Direction.CW);
        this.clipPath.close();
        canvas.clipPath(this.clipPath);
        canvas.scale(f / getWidth(), f2 / getHeight());
        boolean zHasImageLoaded = this.imageReceiverFullSize.hasImageLoaded();
        if (!zHasImageLoaded || f3 < 1.0f) {
            float f4 = 1.0f - f3;
            drawImpl(canvas, false, f4, 1.0f, f4);
        }
        if (zHasImageLoaded && f3 > 0.0f) {
            drawImpl(canvas, true, 1.0f - f3, f3, 0.0f);
        }
        canvas.restore();
    }

    public void drawDuration(Canvas canvas, RectF rectF, float f) {
        String str;
        float fPow = f;
        if (this.showVideoLayout) {
            ImageReceiver imageReceiver = this.imageReceiver;
            if (imageReceiver == null || imageReceiver.getVisible()) {
                float fWidth = rectF.width() + (AndroidUtilities.m1036dp(20.0f) * this.checkBoxProgress);
                float fWidth2 = rectF.width() / fWidth;
                if (fPow < 1.0f) {
                    fPow = (float) Math.pow(fPow, 8.0d);
                }
                canvas.save();
                canvas.translate(rectF.left, rectF.top);
                canvas.scale(fWidth2, fWidth2, 0.0f, rectF.height());
                canvas.clipRect(0.0f, 0.0f, rectF.width(), rectF.height());
                int i = this.currentParentColumnsCount;
                if (i != 9 && this.videoInfoLayot == null && (str = this.videoText) != null) {
                    this.videoInfoLayot = new StaticLayout(this.videoText, this.sharedResources.textPaint, (int) Math.ceil(this.sharedResources.textPaint.measureText(str)), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                } else if ((i >= 9 || this.videoText == null) && this.videoInfoLayot != null) {
                    this.videoInfoLayot = null;
                }
                boolean zViewsOnLeft = viewsOnLeft(fWidth);
                int iM1036dp = AndroidUtilities.m1036dp(8.0f);
                StaticLayout staticLayout = this.videoInfoLayot;
                int width = iM1036dp + (staticLayout != null ? staticLayout.getWidth() : 0) + (this.drawVideoIcon ? AndroidUtilities.m1036dp(10.0f) : 0);
                canvas.translate(AndroidUtilities.m1036dp(5.0f), (((AndroidUtilities.m1036dp(1.0f) + rectF.height()) - AndroidUtilities.m1036dp(17.0f)) - AndroidUtilities.m1036dp(4.0f)) - (zViewsOnLeft ? AndroidUtilities.m1036dp(22.0f) : 0));
                RectF rectF2 = AndroidUtilities.rectTmp;
                rectF2.set(0.0f, 0.0f, width, AndroidUtilities.m1036dp(17.0f));
                int alpha = Theme.chat_timeBackgroundPaint.getAlpha();
                Theme.chat_timeBackgroundPaint.setAlpha((int) (alpha * fPow));
                canvas.drawRoundRect(rectF2, AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(4.0f), Theme.chat_timeBackgroundPaint);
                Theme.chat_timeBackgroundPaint.setAlpha(alpha);
                if (this.drawVideoIcon) {
                    canvas.save();
                    canvas.translate(this.videoInfoLayot == null ? AndroidUtilities.m1036dp(5.0f) : AndroidUtilities.m1036dp(4.0f), (AndroidUtilities.m1036dp(17.0f) - this.sharedResources.playDrawable.getIntrinsicHeight()) / 2.0f);
                    boolean zIsCurrentThemeMonet = Theme.isCurrentThemeMonet();
                    SharedResources sharedResources = this.sharedResources;
                    if (zIsCurrentThemeMonet) {
                        sharedResources.playDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_mediaTimeText), PorterDuff.Mode.MULTIPLY));
                    } else {
                        sharedResources.playDrawable.setAlpha((int) (this.imageAlpha * 255.0f * fPow));
                    }
                    this.sharedResources.playDrawable.draw(canvas);
                    canvas.restore();
                }
                if (this.videoInfoLayot != null) {
                    canvas.translate(AndroidUtilities.m1036dp((this.drawVideoIcon ? 10 : 0) + 4), (AndroidUtilities.m1036dp(17.0f) - this.videoInfoLayot.getHeight()) / 2.0f);
                    int alpha2 = this.sharedResources.textPaint.getAlpha();
                    this.sharedResources.textPaint.setAlpha((int) (alpha2 * fPow));
                    this.videoInfoLayot.draw(canvas);
                    this.sharedResources.textPaint.setAlpha(alpha2);
                }
                canvas.restore();
            }
        }
    }

    public void updateViews() {
        MessageObject messageObject;
        TL_stories.StoryItem storyItem;
        TL_stories.StoryViews storyViews;
        if (this.isStory && (messageObject = this.currentMessageObject) != null && (storyItem = messageObject.storyItem) != null && (storyViews = storyItem.views) != null) {
            int i = storyViews.views_count;
            this.drawViews = i > 0;
            this.viewsText.setText(AndroidUtilities.formatWholeNumber(i, 0), true);
        } else {
            this.drawViews = false;
            this.viewsText.setText(_UrlKt.FRAGMENT_ENCODE_SET, false);
        }
    }

    public boolean viewsOnLeft(float f) {
        int width;
        if (this.isStory && this.currentParentColumnsCount < 5) {
            int iM1036dp = AndroidUtilities.m1036dp(26.0f) + ((int) this.viewsText.getCurrentWidth());
            if (this.showVideoLayout) {
                int iM1036dp2 = AndroidUtilities.m1036dp(8.0f);
                StaticLayout staticLayout = this.videoInfoLayot;
                width = iM1036dp2 + (staticLayout != null ? staticLayout.getWidth() : 0) + (this.drawVideoIcon ? AndroidUtilities.m1036dp(10.0f) : 0);
            } else {
                width = 0;
            }
            if (iM1036dp + ((iM1036dp <= 0 || width <= 0) ? 0 : AndroidUtilities.m1036dp(8.0f)) + width > f) {
                return true;
            }
        }
        return false;
    }

    public void drawPrivacy(Canvas canvas, RectF rectF, float f) {
        Bitmap bitmap;
        if (!this.isStory || (bitmap = this.privacyBitmap) == null || bitmap.isRecycled()) {
            return;
        }
        int iM1036dp = AndroidUtilities.m1036dp((rectF.width() / (rectF.width() + (AndroidUtilities.m1036dp(20.0f) * this.checkBoxProgress))) * 17.33f);
        canvas.save();
        float f2 = iM1036dp;
        canvas.translate((rectF.right - f2) - AndroidUtilities.m1036dp(5.66f), rectF.top + AndroidUtilities.m1036dp(5.66f));
        if (this.privacyPaint == null) {
            this.privacyPaint = new Paint(3);
        }
        this.privacyPaint.setAlpha((int) (f * 255.0f));
        RectF rectF2 = AndroidUtilities.rectTmp;
        rectF2.set(0.0f, 0.0f, f2, f2);
        canvas.drawBitmap(this.privacyBitmap, (Rect) null, rectF2, this.privacyPaint);
        canvas.restore();
    }

    public void drawAuthor(Canvas canvas, RectF rectF, float f) {
        if (this.isStory) {
            ImageReceiver imageReceiver = this.imageReceiver;
            if ((imageReceiver == null || imageReceiver.getVisible()) && this.isSearchingHashtag && this.authorText != null) {
                this.authorText.ellipsize((int) (rectF.width() - (2.0f * r0))).setVerticalClipPadding(AndroidUtilities.m1036dp(14.0f)).setShadow(0.4f * f).draw(canvas, rectF.left + AndroidUtilities.m1036dp(5.33f), rectF.top + AndroidUtilities.m1036dp(this.currentParentColumnsCount <= 2 ? 15.0f : 11.33f), Theme.multAlpha(-1, f), 1.0f);
            }
        }
    }

    public void drawViews(Canvas canvas, RectF rectF, float f) {
        if (this.isStory) {
            ImageReceiver imageReceiver = this.imageReceiver;
            if ((imageReceiver == null || imageReceiver.getVisible()) && this.currentParentColumnsCount < 5) {
                float fWidth = rectF.width() + (AndroidUtilities.m1036dp(20.0f) * this.checkBoxProgress);
                float fWidth2 = rectF.width() / fWidth;
                boolean zViewsOnLeft = viewsOnLeft(fWidth);
                float f2 = this.viewsAlpha.set(this.drawViews);
                float fPow = f * f2;
                if (fPow < 1.0f) {
                    fPow = (float) Math.pow(fPow, 8.0d);
                }
                if (f2 <= 0.0f) {
                    return;
                }
                canvas.save();
                canvas.translate(rectF.left, rectF.top);
                canvas.scale(fWidth2, fWidth2, zViewsOnLeft ? 0.0f : rectF.width(), rectF.height());
                canvas.clipRect(0.0f, 0.0f, rectF.width(), rectF.height());
                float fM1036dp = AndroidUtilities.m1036dp(26.0f) + this.viewsText.getCurrentWidth();
                canvas.translate(zViewsOnLeft ? AndroidUtilities.m1036dp(5.0f) : (rectF.width() - AndroidUtilities.m1036dp(5.0f)) - fM1036dp, ((AndroidUtilities.m1036dp(1.0f) + rectF.height()) - AndroidUtilities.m1036dp(17.0f)) - AndroidUtilities.m1036dp(4.0f));
                RectF rectF2 = AndroidUtilities.rectTmp;
                rectF2.set(0.0f, 0.0f, fM1036dp, AndroidUtilities.m1036dp(17.0f));
                int alpha = Theme.chat_timeBackgroundPaint.getAlpha();
                Theme.chat_timeBackgroundPaint.setAlpha((int) (alpha * fPow));
                canvas.drawRoundRect(rectF2, AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(4.0f), Theme.chat_timeBackgroundPaint);
                Theme.chat_timeBackgroundPaint.setAlpha(alpha);
                canvas.save();
                canvas.translate(AndroidUtilities.m1036dp(3.0f), (AndroidUtilities.m1036dp(17.0f) - this.sharedResources.viewDrawable.getBounds().height()) / 2.0f);
                boolean zIsCurrentThemeMonet = Theme.isCurrentThemeMonet();
                SharedResources sharedResources = this.sharedResources;
                if (zIsCurrentThemeMonet) {
                    sharedResources.viewDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_mediaTimeText), PorterDuff.Mode.MULTIPLY));
                } else {
                    sharedResources.viewDrawable.setAlpha((int) (this.imageAlpha * 255.0f * fPow));
                }
                this.sharedResources.viewDrawable.draw(canvas);
                canvas.restore();
                canvas.translate(AndroidUtilities.m1036dp(22.0f), 0.0f);
                this.viewsText.setBounds(0, 0, (int) fM1036dp, AndroidUtilities.m1036dp(17.0f));
                this.viewsText.setAlpha((int) (fPow * 255.0f));
                this.viewsText.draw(canvas);
                canvas.restore();
            }
        }
    }

    public boolean canRevealSpoiler() {
        MessageObject messageObject = this.currentMessageObject;
        return messageObject != null && messageObject.hasMediaSpoilers() && this.spoilerRevealProgress == 0.0f && !this.currentMessageObject.isMediaSpoilersRevealedInSharedMedia;
    }

    public void startRevealMedia(float f, float f2) {
        this.spoilerRevealX = f;
        this.spoilerRevealY = f2;
        this.spoilerMaxRadius = (float) Math.sqrt(Math.pow(getWidth(), 2.0d) + Math.pow(getHeight(), 2.0d));
        ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration((long) MathUtils.clamp(this.spoilerMaxRadius * 0.3f, 250.0f, 550.0f));
        duration.setInterpolator(CubicBezierInterpolator.EASE_BOTH);
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Cells.SharedPhotoVideoCell2$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$startRevealMedia$3(valueAnimator);
            }
        });
        duration.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.SharedPhotoVideoCell2.1
            public C33531() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SharedPhotoVideoCell2 sharedPhotoVideoCell2 = SharedPhotoVideoCell2.this;
                sharedPhotoVideoCell2.currentMessageObject.isMediaSpoilersRevealedInSharedMedia = true;
                sharedPhotoVideoCell2.invalidate();
            }
        });
        duration.start();
    }

    public /* synthetic */ void lambda$startRevealMedia$3(ValueAnimator valueAnimator) {
        this.spoilerRevealProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Cells.SharedPhotoVideoCell2$1 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C33531 extends AnimatorListenerAdapter {
        public C33531() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            SharedPhotoVideoCell2 sharedPhotoVideoCell2 = SharedPhotoVideoCell2.this;
            sharedPhotoVideoCell2.currentMessageObject.isMediaSpoilersRevealedInSharedMedia = true;
            sharedPhotoVideoCell2.invalidate();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.attached = true;
        CheckBoxBase checkBoxBase = this.checkBoxBase;
        if (checkBoxBase != null) {
            checkBoxBase.onAttachedToWindow();
        }
        if (this.currentMessageObject != null) {
            this.imageReceiver.onAttachedToWindow();
            this.imageReceiverFullSize.onAttachedToWindow();
            this.blurImageReceiver.onAttachedToWindow();
        }
        SpoilerEffect2 spoilerEffect2 = this.mediaSpoilerEffect2;
        if (spoilerEffect2 != null) {
            if (spoilerEffect2.destroyed) {
                this.mediaSpoilerEffect2 = SpoilerEffect2.getInstance(this);
            } else {
                spoilerEffect2.attach(this);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.attached = false;
        CheckBoxBase checkBoxBase = this.checkBoxBase;
        if (checkBoxBase != null) {
            checkBoxBase.onDetachedFromWindow();
        }
        if (this.currentMessageObject != null) {
            this.imageReceiver.onDetachedFromWindow();
            this.imageReceiverFullSize.onDetachedFromWindow();
            this.blurImageReceiver.onDetachedFromWindow();
        }
        SpoilerEffect2 spoilerEffect2 = this.mediaSpoilerEffect2;
        if (spoilerEffect2 != null) {
            spoilerEffect2.detach(this);
        }
    }

    public void setGradientView(FlickerLoadingView flickerLoadingView) {
        this.globalGradientView = flickerLoadingView;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        boolean z = this.isStory;
        int i3 = z ? (int) (size * 1.25f) : size;
        if (z && this.currentParentColumnsCount == 1) {
            i3 /= 2;
        }
        setMeasuredDimension(size, i3);
        updateSpoilers2();
    }

    private void updateSpoilers2() {
        if (getMeasuredHeight() <= 0 || getMeasuredWidth() <= 0) {
            return;
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && messageObject.hasMediaSpoilers() && SpoilerEffect2.supports()) {
            if (this.mediaSpoilerEffect2 == null) {
                this.mediaSpoilerEffect2 = SpoilerEffect2.getInstance(this);
            }
        } else {
            SpoilerEffect2 spoilerEffect2 = this.mediaSpoilerEffect2;
            if (spoilerEffect2 != null) {
                spoilerEffect2.detach(this);
                this.mediaSpoilerEffect2 = null;
            }
        }
    }

    public int getMessageId() {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null) {
            return messageObject.getId();
        }
        return 0;
    }

    public MessageObject getMessageObject() {
        return this.currentMessageObject;
    }

    public void setImageAlpha(float f, boolean z) {
        if (this.imageAlpha != f) {
            this.imageAlpha = f;
            if (z) {
                invalidate();
            }
        }
    }

    public void setImageScale(float f, boolean z) {
        if (this.imageScale != f) {
            this.imageScale = f;
            if (z) {
                invalidate();
            }
        }
    }

    public void setCrossfadeView(SharedPhotoVideoCell2 sharedPhotoVideoCell2, float f, int i) {
        this.crossfadeView = sharedPhotoVideoCell2;
        this.crossfadeProgress = f;
        this.crossfadeToColumnsCount = i;
    }

    public void drawCrossafadeImage(Canvas canvas) {
        if (this.crossfadeView != null) {
            canvas.save();
            canvas.translate(getX(), getY());
            this.crossfadeView.setImageScale(((getMeasuredWidth() - AndroidUtilities.m1036dp(2.0f)) * this.imageScale) / (this.crossfadeView.getMeasuredWidth() - AndroidUtilities.m1036dp(2.0f)), false);
            this.crossfadeView.draw(canvas);
            canvas.restore();
        }
    }

    public View getCrossfadeView() {
        return this.crossfadeView;
    }

    public void setChecked(boolean z, boolean z2) {
        int i;
        CheckBoxBase checkBoxBase = this.checkBoxBase;
        if ((checkBoxBase != null && checkBoxBase.isChecked()) == z) {
            return;
        }
        if (this.checkBoxBase == null) {
            CheckBoxBase checkBoxBase2 = new CheckBoxBase(this, 21, null);
            this.checkBoxBase = checkBoxBase2;
            checkBoxBase2.setColor(-1, Theme.key_sharedMedia_photoPlaceholder, Theme.key_checkboxCheck);
            if (this.check2 && (i = this.imageReceiverColor) != 0) {
                this.checkBoxBase.setBackgroundColor(Theme.blendOver(i, Theme.multAlpha(-1, 0.25f)));
            }
            this.checkBoxBase.setDrawUnchecked(false);
            this.checkBoxBase.setBackgroundType(1);
            this.checkBoxBase.setBounds(0, 0, AndroidUtilities.m1036dp(24.0f), AndroidUtilities.m1036dp(24.0f));
            if (this.attached) {
                this.checkBoxBase.onAttachedToWindow();
            }
        }
        this.checkBoxBase.setChecked(z, z2);
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            this.animator = null;
            valueAnimator.cancel();
        }
        if (z2) {
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.checkBoxProgress, z ? 1.0f : 0.0f);
            this.animator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Cells.SharedPhotoVideoCell2.2
                public C33542() {
                }

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    SharedPhotoVideoCell2.this.checkBoxProgress = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    SharedPhotoVideoCell2.this.invalidate();
                }
            });
            this.animator.setDuration(200L);
            this.animator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.SharedPhotoVideoCell2.3
                final /* synthetic */ boolean val$checked;

                public C33553(boolean z3) {
                    z = z3;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ValueAnimator valueAnimator2 = SharedPhotoVideoCell2.this.animator;
                    if (valueAnimator2 == null || !valueAnimator2.equals(animator)) {
                        return;
                    }
                    SharedPhotoVideoCell2 sharedPhotoVideoCell2 = SharedPhotoVideoCell2.this;
                    sharedPhotoVideoCell2.checkBoxProgress = z ? 1.0f : 0.0f;
                    sharedPhotoVideoCell2.animator = null;
                }
            });
            this.animator.start();
        } else {
            this.checkBoxProgress = z3 ? 1.0f : 0.0f;
        }
        invalidate();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Cells.SharedPhotoVideoCell2$2 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C33542 implements ValueAnimator.AnimatorUpdateListener {
        public C33542() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator2) {
            SharedPhotoVideoCell2.this.checkBoxProgress = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
            SharedPhotoVideoCell2.this.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Cells.SharedPhotoVideoCell2$3 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C33553 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$checked;

        public C33553(boolean z3) {
            z = z3;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            ValueAnimator valueAnimator2 = SharedPhotoVideoCell2.this.animator;
            if (valueAnimator2 == null || !valueAnimator2.equals(animator)) {
                return;
            }
            SharedPhotoVideoCell2 sharedPhotoVideoCell2 = SharedPhotoVideoCell2.this;
            sharedPhotoVideoCell2.checkBoxProgress = z ? 1.0f : 0.0f;
            sharedPhotoVideoCell2.animator = null;
        }
    }

    public void setHighlightProgress(float f) {
        if (this.highlightProgress != f) {
            this.highlightProgress = f;
            invalidate();
        }
    }

    public int getStyle() {
        return this.style;
    }

    public static class SharedResources {
        Drawable playDrawable;
        Drawable viewDrawable;
        TextPaint textPaint = new TextPaint(1);
        private Paint backgroundPaint = new Paint();
        Paint highlightPaint = new Paint();
        SparseArray<String> imageFilters = new SparseArray<>();
        private final HashMap<Integer, Bitmap> privacyBitmaps = new HashMap<>();

        public SharedResources(Context context, Theme.ResourcesProvider resourcesProvider) {
            this.textPaint.setTextSize(AndroidUtilities.m1036dp(12.0f));
            this.textPaint.setColor(Theme.isCurrentThemeMonet() ? Theme.getColor(Theme.key_chat_mediaTimeText) : -1);
            this.textPaint.setTypeface(AndroidUtilities.bold());
            Drawable drawableMutate = ContextCompat.getDrawable(context, C2797R.drawable.play_mini_video).mutate();
            this.playDrawable = drawableMutate;
            drawableMutate.setBounds(0, 0, drawableMutate.getIntrinsicWidth(), this.playDrawable.getIntrinsicHeight());
            Drawable drawableMutate2 = ContextCompat.getDrawable(context, C2797R.drawable.filled_views).mutate();
            this.viewDrawable = drawableMutate2;
            drawableMutate2.setBounds(0, 0, (int) (drawableMutate2.getIntrinsicWidth() * 0.7f), (int) (this.viewDrawable.getIntrinsicHeight() * 0.7f));
            this.backgroundPaint.setColor(Theme.getColor(Theme.key_sharedMedia_photoPlaceholder, resourcesProvider));
        }

        public String getFilterString(int i) {
            String str = this.imageFilters.get(i);
            if (str != null) {
                return str;
            }
            String str2 = i + "_" + i + "_isc";
            this.imageFilters.put(i, str2);
            return str2;
        }

        public Bitmap getPrivacyBitmap(Context context, int i) {
            Bitmap bitmap = this.privacyBitmaps.get(Integer.valueOf(i));
            if (bitmap != null) {
                return bitmap;
            }
            Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(context.getResources(), i);
            int width = bitmapDecodeResource.getWidth();
            int height = bitmapDecodeResource.getHeight();
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, config);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            Paint paint = new Paint(3);
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            paint.setColorFilter(new PorterDuffColorFilter(-10461088, mode));
            canvas.drawBitmap(bitmapDecodeResource, 0.0f, 0.0f, paint);
            Utilities.stackBlurBitmap(bitmapCreateBitmap, AndroidUtilities.m1036dp(1.0f));
            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(bitmapDecodeResource.getWidth(), bitmapDecodeResource.getHeight(), config);
            Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
            canvas2.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, paint);
            canvas2.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, paint);
            canvas2.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, paint);
            paint.setColorFilter(new PorterDuffColorFilter(-1, mode));
            canvas2.drawBitmap(bitmapDecodeResource, 0.0f, 0.0f, paint);
            bitmapCreateBitmap.recycle();
            bitmapDecodeResource.recycle();
            this.privacyBitmaps.put(Integer.valueOf(i), bitmapCreateBitmap2);
            return bitmapCreateBitmap2;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        CanvasButton canvasButton = this.canvasButton;
        if (canvasButton == null || !canvasButton.checkTouchEvent(motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.View
    public boolean verifyDrawable(Drawable drawable) {
        return this.viewsText == drawable || super.verifyDrawable(drawable);
    }

    public void setReordering(boolean z, boolean z2) {
        if (this.reordering == z) {
            return;
        }
        this.reordering = z;
        if (!z2) {
            this.animatedReordering.force(z);
        }
        invalidate();
    }
}
