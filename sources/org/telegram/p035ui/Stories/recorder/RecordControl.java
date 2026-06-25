package org.telegram.p035ui.Stories.recorder;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.zxing.common.detector.MathUtils;
import java.util.ArrayList;
import java.util.List;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.AnimatedFloat;
import org.telegram.p035ui.Components.ButtonBounce;
import org.telegram.p035ui.Components.CombinedDrawable;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Stories.recorder.FlashViews;

/* JADX INFO: loaded from: classes7.dex */
public class RecordControl extends View implements FlashViews.Invertable {
    private final float HALF_PI;
    private boolean a11yPrevCheck;
    private boolean a11yPrevDual;
    private boolean a11yPrevLoading;
    private boolean a11yPrevRecording;
    private boolean a11yPrevShowLock;
    private boolean a11yPrevStartIsVideo;
    private RecordControlAccessibilityHelper accessibilityHelper;
    public float amplitude;
    public final AnimatedFloat animatedAmplitude;
    private final Paint buttonPaint;
    private final Paint buttonPaintWhite;
    private final PointF check1;
    private final PointF check2;
    private final PointF check3;
    private final AnimatedFloat checkAnimated;
    private final Paint checkPaint;
    private final Path checkPath;
    private final Path circlePath;
    private final AnimatedFloat collage;
    private float collageProgress;
    private final AnimatedFloat collageProgressAnimated;

    /* JADX INFO: renamed from: cx */
    private float f1826cx;

    /* JADX INFO: renamed from: cy */
    private float f1827cy;
    private Delegate delegate;
    private boolean discardParentTouch;
    private boolean dual;
    private final AnimatedFloat dualT;
    private final ButtonBounce flipButton;
    private boolean flipButtonWasPressed;
    private final Drawable flipDrawableBlack;
    private float flipDrawableRotate;
    private final AnimatedFloat flipDrawableRotateT;
    private final Drawable flipDrawableWhite;
    private final ImageReceiver galleryImage;

    /* JADX INFO: renamed from: h1 */
    private final PointF f1828h1;

    /* JADX INFO: renamed from: h2 */
    private final PointF f1829h2;

    /* JADX INFO: renamed from: h3 */
    private final PointF f1830h3;

    /* JADX INFO: renamed from: h4 */
    private final PointF f1831h4;
    private final Paint hintLinePaintBlack;
    private final Paint hintLinePaintWhite;
    private long lastDuration;
    private float leftCx;
    private float[] loadingSegments;
    private final ButtonBounce lockButton;
    private final Drawable lockDrawable;
    private final AnimatedFloat lockedT;
    private boolean longpressRecording;
    private final Paint mainPaint;
    private final Path metaballsPath;
    private final CombinedDrawable noGalleryDrawable;
    private final Runnable onFlipLongPressRunnable;
    private final Runnable onRecordLongPressRunnable;
    private final Paint outlineFilledPaint;
    private final Paint outlinePaint;
    private float overrideStartModeIsVideoT;

    /* JADX INFO: renamed from: p1 */
    private final PointF f1832p1;

    /* JADX INFO: renamed from: p2 */
    private final PointF f1833p2;

    /* JADX INFO: renamed from: p3 */
    private final PointF f1834p3;

    /* JADX INFO: renamed from: p4 */
    private final PointF f1835p4;
    private final Drawable pauseDrawable;
    private final ButtonBounce recordButton;
    private final AnimatedFloat recordCx;
    private boolean recording;
    private boolean recordingLoading;
    private long recordingLoadingStart;
    private final AnimatedFloat recordingLoadingT;
    private final AnimatedFloat recordingLongT;
    private long recordingStart;
    private final AnimatedFloat recordingT;
    private RadialGradient redGradient;
    private final Matrix redMatrix;
    private final Paint redPaint;
    private float rightCx;
    private boolean showLock;
    private boolean startModeIsVideo;
    private final AnimatedFloat startModeIsVideoT;
    private boolean touch;
    private final AnimatedFloat touchIsButtonT;
    private final AnimatedFloat touchIsCenter2T;
    private final AnimatedFloat touchIsCenterT;
    private long touchStart;
    private final AnimatedFloat touchT;
    private float touchX;
    private float touchY;
    private final Drawable unlockDrawable;

    public interface Delegate {
        boolean canRecordAudio();

        long getMaxVideoDuration();

        default long getMaxVisibleVideoDuration() {
            return 60000L;
        }

        void onCheckClick();

        void onFlipClick();

        void onFlipLongClick();

        void onGalleryClick();

        void onPhotoShoot();

        void onVideoDuration(long j);

        void onVideoRecordEnd(boolean z);

        void onVideoRecordLocked();

        void onVideoRecordStart(boolean z, Runnable runnable);

        void onZoom(float f);

        default boolean showStoriesDrafts() {
            return true;
        }
    }

    public void startAsVideo(boolean z) {
        this.overrideStartModeIsVideoT = -1.0f;
        this.startModeIsVideo = z;
        invalidate();
    }

    public void startAsVideoT(float f) {
        this.overrideStartModeIsVideoT = f;
        invalidate();
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    public RecordControl(Context context) {
        super(context);
        ImageReceiver imageReceiver = new ImageReceiver();
        this.galleryImage = imageReceiver;
        this.mainPaint = new Paint(1);
        Paint paint = new Paint(1);
        this.outlinePaint = paint;
        Paint paint2 = new Paint(1);
        this.outlineFilledPaint = paint2;
        Paint paint3 = new Paint(1);
        this.buttonPaint = paint3;
        Paint paint4 = new Paint(1);
        this.buttonPaintWhite = paint4;
        Paint paint5 = new Paint(1);
        this.redPaint = paint5;
        Paint paint6 = new Paint(1);
        this.hintLinePaintWhite = paint6;
        Paint paint7 = new Paint(1);
        this.hintLinePaintBlack = paint7;
        Paint paint8 = new Paint(1);
        this.checkPaint = paint8;
        Matrix matrix = new Matrix();
        this.redMatrix = matrix;
        this.recordButton = new ButtonBounce(this);
        this.flipButton = new ButtonBounce(this);
        this.lockButton = new ButtonBounce(this);
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
        this.flipDrawableRotateT = new AnimatedFloat(this, 0L, 310L, cubicBezierInterpolator);
        this.dualT = new AnimatedFloat(this, 0L, 330L, cubicBezierInterpolator);
        this.checkPath = new Path();
        this.check1 = new PointF(-AndroidUtilities.dpf2(9.666667f), AndroidUtilities.dpf2(2.3333333f));
        this.check2 = new PointF(-AndroidUtilities.dpf2(2.8333333f), AndroidUtilities.dpf2(8.666667f));
        this.check3 = new PointF(AndroidUtilities.dpf2(9.666667f), AndroidUtilities.dpf2(-3.6666667f));
        this.animatedAmplitude = new AnimatedFloat(this, 0L, 200L, CubicBezierInterpolator.DEFAULT);
        this.startModeIsVideoT = new AnimatedFloat(this, 0L, 350L, cubicBezierInterpolator);
        this.overrideStartModeIsVideoT = -1.0f;
        this.startModeIsVideo = true;
        this.recordingT = new AnimatedFloat(this, 0L, 350L, cubicBezierInterpolator);
        this.recordingLongT = new AnimatedFloat(this, 0L, 850L, cubicBezierInterpolator);
        this.loadingSegments = new float[2];
        this.recordingLoadingT = new AnimatedFloat(this, 0L, 350L, cubicBezierInterpolator);
        this.touchT = new AnimatedFloat(this, 0L, 350L, cubicBezierInterpolator);
        this.touchIsCenterT = new AnimatedFloat(this, 0L, 650L, cubicBezierInterpolator);
        this.touchIsCenter2T = new AnimatedFloat(this, 0L, 160L, CubicBezierInterpolator.EASE_IN);
        this.recordCx = new AnimatedFloat(this, 0L, 750L, cubicBezierInterpolator);
        this.touchIsButtonT = new AnimatedFloat(this, 0L, 650L, cubicBezierInterpolator);
        this.lockedT = new AnimatedFloat(this, 0L, 320L, cubicBezierInterpolator);
        this.collage = new AnimatedFloat(this, 0L, 320L, cubicBezierInterpolator);
        this.collageProgressAnimated = new AnimatedFloat(this, 0L, 320L, cubicBezierInterpolator);
        this.checkAnimated = new AnimatedFloat(this, 0L, 320L, cubicBezierInterpolator);
        this.onRecordLongPressRunnable = new Runnable() { // from class: org.telegram.ui.Stories.recorder.RecordControl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$1();
            }
        };
        this.onFlipLongPressRunnable = new Runnable() { // from class: org.telegram.ui.Stories.recorder.RecordControl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$2();
            }
        };
        this.metaballsPath = new Path();
        this.circlePath = new Path();
        this.HALF_PI = 1.5707964f;
        this.f1832p1 = new PointF();
        this.f1833p2 = new PointF();
        this.f1834p3 = new PointF();
        this.f1835p4 = new PointF();
        this.f1828h1 = new PointF();
        this.f1829h2 = new PointF();
        this.f1830h3 = new PointF();
        this.f1831h4 = new PointF();
        setWillNotDraw(false);
        RecordControlAccessibilityHelper recordControlAccessibilityHelper = new RecordControlAccessibilityHelper(this);
        this.accessibilityHelper = recordControlAccessibilityHelper;
        ViewCompat.setAccessibilityDelegate(this, recordControlAccessibilityHelper);
        RadialGradient radialGradient = new RadialGradient(0.0f, 0.0f, AndroidUtilities.m1036dp(48.0f), new int[]{-577231, -577231, -1}, new float[]{0.0f, 0.64f, 1.0f}, Shader.TileMode.CLAMP);
        this.redGradient = radialGradient;
        radialGradient.setLocalMatrix(matrix);
        paint5.setShader(this.redGradient);
        paint.setColor(-1);
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        Paint.Cap cap = Paint.Cap.ROUND;
        paint.setStrokeCap(cap);
        paint2.setColor(-577231);
        paint2.setStrokeCap(cap);
        paint2.setStyle(style);
        paint3.setColor(1677721600);
        paint4.setColor(-1);
        paint6.setColor(1493172223);
        paint7.setColor(402653184);
        paint6.setStyle(style);
        paint6.setStrokeCap(cap);
        paint7.setStyle(style);
        paint7.setStrokeCap(cap);
        paint8.setStyle(style);
        paint8.setStrokeJoin(Paint.Join.ROUND);
        paint8.setStrokeCap(cap);
        if (Build.VERSION.SDK_INT >= 29) {
            paint8.setBlendMode(BlendMode.CLEAR);
        } else {
            paint8.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
        imageReceiver.setParentView(this);
        imageReceiver.setCrossfadeWithOldImage(true);
        imageReceiver.setRoundRadius(AndroidUtilities.m1036dp(6.0f));
        Drawable drawableMutate = context.getResources().getDrawable(C2797R.drawable.msg_media_gallery).mutate();
        PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
        drawableMutate.setColorFilter(new PorterDuffColorFilter(1308622847, mode));
        CombinedDrawable combinedDrawable = new CombinedDrawable(Theme.createRoundRectDrawable(AndroidUtilities.m1036dp(6.0f), -13750737), drawableMutate);
        this.noGalleryDrawable = combinedDrawable;
        combinedDrawable.setFullsize(false);
        combinedDrawable.setIconSize(AndroidUtilities.m1036dp(24.0f), AndroidUtilities.m1036dp(24.0f));
        Drawable drawableMutate2 = context.getResources().getDrawable(C2797R.drawable.msg_photo_switch2).mutate();
        this.flipDrawableWhite = drawableMutate2;
        drawableMutate2.setColorFilter(new PorterDuffColorFilter(-1, mode));
        Drawable drawableMutate3 = context.getResources().getDrawable(C2797R.drawable.msg_photo_switch2).mutate();
        this.flipDrawableBlack = drawableMutate3;
        drawableMutate3.setColorFilter(new PorterDuffColorFilter(-16777216, mode));
        Drawable drawableMutate4 = context.getResources().getDrawable(C2797R.drawable.msg_filled_unlockedrecord).mutate();
        this.unlockDrawable = drawableMutate4;
        drawableMutate4.setColorFilter(new PorterDuffColorFilter(-1, mode));
        Drawable drawableMutate5 = context.getResources().getDrawable(C2797R.drawable.msg_filled_lockedrecord).mutate();
        this.lockDrawable = drawableMutate5;
        drawableMutate5.setColorFilter(new PorterDuffColorFilter(-16777216, mode));
        Drawable drawableMutate6 = context.getResources().getDrawable(C2797R.drawable.msg_round_pause_m).mutate();
        this.pauseDrawable = drawableMutate6;
        drawableMutate6.setColorFilter(new PorterDuffColorFilter(-1, mode));
        updateGalleryImage();
    }

    public void updateGalleryImage() {
        String str;
        ArrayList<MediaController.PhotoEntry> arrayList;
        Delegate delegate = this.delegate;
        if (delegate != null && delegate.showStoriesDrafts()) {
            ArrayList<StoryEntry> arrayList2 = MessagesController.getInstance(this.galleryImage.getCurrentAccount()).getStoriesController().getDraftsController().drafts;
            this.galleryImage.setOrientation(0, 0, true);
            if (arrayList2 != null && !arrayList2.isEmpty() && arrayList2.get(0).draftThumbFile != null) {
                this.galleryImage.setImage(ImageLocation.getForPath(arrayList2.get(0).draftThumbFile.getAbsolutePath()), "80_80", null, null, this.noGalleryDrawable, 0L, null, null, 0);
                return;
            }
        }
        MediaController.AlbumEntry albumEntry = MediaController.allMediaAlbumEntry;
        MediaController.PhotoEntry photoEntry = (albumEntry == null || (arrayList = albumEntry.photos) == null || arrayList.isEmpty()) ? null : albumEntry.photos.get(0);
        if (photoEntry != null && (str = photoEntry.thumbPath) != null) {
            this.galleryImage.setImage(ImageLocation.getForPath(str), "80_80", null, null, this.noGalleryDrawable, 0L, null, null, 0);
            return;
        }
        if (photoEntry != null && photoEntry.path != null) {
            boolean z = photoEntry.isVideo;
            ImageReceiver imageReceiver = this.galleryImage;
            if (z) {
                imageReceiver.setImage(ImageLocation.getForPath("vthumb://" + photoEntry.imageId + ":" + photoEntry.path), "80_80", null, null, this.noGalleryDrawable, 0L, null, null, 0);
                return;
            }
            imageReceiver.setOrientation(photoEntry.orientation, photoEntry.invert, true);
            this.galleryImage.setImage(ImageLocation.getForPath("thumb://" + photoEntry.imageId + ":" + photoEntry.path), "80_80", null, null, this.noGalleryDrawable, 0L, null, null, 0);
            return;
        }
        this.galleryImage.setImageBitmap(this.noGalleryDrawable);
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.galleryImage.onAttachedToWindow();
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        this.galleryImage.onDetachedFromWindow();
        super.onDetachedFromWindow();
    }

    @Override // org.telegram.ui.Stories.recorder.FlashViews.Invertable
    public void setInvert(float f) {
        this.outlinePaint.setColor(ColorUtils.blendARGB(-1, -16777216, f));
        this.buttonPaint.setColor(ColorUtils.blendARGB(1677721600, 369098752, f));
        this.hintLinePaintWhite.setColor(ColorUtils.blendARGB(1493172223, 285212671, f));
        this.hintLinePaintBlack.setColor(ColorUtils.blendARGB(402653184, 805306368, f));
        Drawable drawable = this.flipDrawableWhite;
        int iBlendARGB = ColorUtils.blendARGB(-1, -16777216, f);
        PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
        drawable.setColorFilter(new PorterDuffColorFilter(iBlendARGB, mode));
        this.unlockDrawable.setColorFilter(new PorterDuffColorFilter(ColorUtils.blendARGB(-1, -16777216, f), mode));
    }

    public void setAmplitude(float f, boolean z) {
        this.amplitude = f;
        if (z) {
            return;
        }
        this.animatedAmplitude.set(f, true);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int iM1036dp = AndroidUtilities.m1036dp(100.0f);
        float f = size;
        this.f1826cx = f / 2.0f;
        this.f1827cy = iM1036dp / 2.0f;
        float fMin = Math.min(AndroidUtilities.m1036dp(135.0f), f * 0.35f);
        float f2 = this.f1826cx;
        this.leftCx = f2 - fMin;
        float f3 = f2 + fMin;
        this.rightCx = f3;
        setDrawableBounds(this.flipDrawableWhite, f3, this.f1827cy, AndroidUtilities.m1036dp(14.0f));
        setDrawableBounds(this.flipDrawableBlack, this.rightCx, this.f1827cy, AndroidUtilities.m1036dp(14.0f));
        setDrawableBounds(this.unlockDrawable, this.leftCx, this.f1827cy);
        setDrawableBounds(this.lockDrawable, this.leftCx, this.f1827cy);
        setDrawableBounds(this.pauseDrawable, this.leftCx, this.f1827cy);
        this.galleryImage.setImageCoords(this.leftCx - AndroidUtilities.m1036dp(20.0f), this.f1827cy - AndroidUtilities.m1036dp(20.0f), AndroidUtilities.m1036dp(40.0f), AndroidUtilities.m1036dp(40.0f));
        this.redMatrix.reset();
        this.redMatrix.postTranslate(this.f1826cx, this.f1827cy);
        this.redGradient.setLocalMatrix(this.redMatrix);
        setMeasuredDimension(size, iM1036dp);
        RecordControlAccessibilityHelper recordControlAccessibilityHelper = this.accessibilityHelper;
        if (recordControlAccessibilityHelper != null) {
            recordControlAccessibilityHelper.invalidateRoot();
        }
    }

    private static void setDrawableBounds(Drawable drawable, float f, float f2) {
        setDrawableBounds(drawable, f, f2, Math.max(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()) / 2.0f);
    }

    private static void setDrawableBounds(Drawable drawable, float f, float f2, float f3) {
        drawable.setBounds((int) (f - f3), (int) (f2 - f3), (int) (f + f3), (int) (f2 + f3));
    }

    public void setCollageProgress(float f, boolean z) {
        if (Math.abs(f - this.collageProgress) < 0.01f) {
            return;
        }
        this.collageProgress = f;
        if (!z) {
            this.collage.set(f > 0.0f && !this.recording, true);
            this.collageProgressAnimated.set(f, true);
        }
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        if (this.recording || hasCheck()) {
            return;
        }
        if (!this.delegate.canRecordAudio()) {
            this.touch = false;
            this.recordButton.setPressed(false);
            this.flipButton.setPressed(false);
            this.lockButton.setPressed(false);
            return;
        }
        this.longpressRecording = true;
        this.showLock = true;
        this.delegate.onVideoRecordStart(true, new Runnable() { // from class: org.telegram.ui.Stories.recorder.RecordControl$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.recordingStart = System.currentTimeMillis();
        this.recording = true;
        Delegate delegate = this.delegate;
        this.lastDuration = 0L;
        delegate.onVideoDuration(0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        if (this.recording || hasCheck()) {
            return;
        }
        this.delegate.onFlipLongClick();
        rotateFlip(360.0f);
        this.touch = false;
        this.recordButton.setPressed(false);
        this.flipButton.setPressed(false);
        this.lockButton.setPressed(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:159:0x0836  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onDraw(android.graphics.Canvas r50) {
        /*
            Method dump skipped, instruction units count: 2301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Stories.recorder.RecordControl.onDraw(android.graphics.Canvas):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDraw$3() {
        this.recording = false;
        this.longpressRecording = false;
        this.recordingLoadingStart = SystemClock.elapsedRealtime();
        this.recordingLoading = true;
        this.touch = false;
        this.recordButton.setPressed(false);
        this.flipButton.setPressed(false);
        this.lockButton.setPressed(false);
        this.delegate.onVideoRecordEnd(true);
    }

    public boolean hasCheck() {
        return this.collageProgress >= 1.0f;
    }

    private void getVector(float f, float f2, double d, float f3, PointF pointF) {
        double d2 = f3;
        pointF.x = (float) (((double) f) + (Math.cos(d) * d2));
        pointF.y = (float) (((double) f2) + (Math.sin(d) * d2));
    }

    private float dist(PointF pointF, PointF pointF2) {
        return MathUtils.distance(pointF.x, pointF.y, pointF2.x, pointF2.y);
    }

    public void rotateFlip(float f) {
        this.flipDrawableRotateT.setDuration(f > 180.0f ? 620L : 310L);
        this.flipDrawableRotate += f;
        invalidate();
    }

    private boolean isPressed(float f, float f2, float f3, float f4, float f5, boolean z) {
        return this.recording ? (!z || f4 - f2 <= ((float) AndroidUtilities.m1036dp(100.0f))) && Math.abs(f3 - f) <= f5 : MathUtils.distance(f, f2, f3, f4) <= f5;
    }

    public boolean isTouch() {
        return this.discardParentTouch;
    }

    public void setDual(boolean z) {
        if (z != this.dual) {
            this.dual = z;
            invalidate();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        float fClamp = Utilities.clamp(motionEvent.getX() + 0.0f, this.rightCx, this.leftCx);
        float y = motionEvent.getY() + 0.0f;
        boolean zIsPressed = isPressed(fClamp, y, this.rightCx, this.f1827cy, AndroidUtilities.m1036dp(7.0f), true);
        boolean z = true;
        if (this.recordingLoading) {
            this.recordButton.setPressed(false);
            this.flipButton.setPressed(false);
            this.lockButton.setPressed(false);
        } else if (action == 0 || this.touch) {
            this.recordButton.setPressed(isPressed(fClamp, y, this.f1826cx, this.f1827cy, AndroidUtilities.m1036dp(60.0f), false));
            this.flipButton.setPressed(isPressed(fClamp, y, this.rightCx, this.f1827cy, (float) AndroidUtilities.m1036dp(30.0f), true) && !hasCheck());
            this.lockButton.setPressed(isPressed(fClamp, y, this.leftCx, this.f1827cy, (float) AndroidUtilities.m1036dp(30.0f), false) && !hasCheck());
        }
        if (action == 0) {
            this.touch = true;
            this.discardParentTouch = this.recordButton.isPressed() || this.flipButton.isPressed();
            this.touchStart = System.currentTimeMillis();
            this.touchX = fClamp;
            this.touchY = y;
            if (Math.abs(fClamp - this.f1826cx) < AndroidUtilities.m1036dp(50.0f)) {
                AndroidUtilities.runOnUIThread(this.onRecordLongPressRunnable, ViewConfiguration.getLongPressTimeout());
            }
            if (this.flipButton.isPressed()) {
                AndroidUtilities.runOnUIThread(this.onFlipLongPressRunnable, ViewConfiguration.getLongPressTimeout());
            }
        } else if (action == 2) {
            if (!this.touch) {
                return false;
            }
            this.touchX = Utilities.clamp(fClamp, this.rightCx, this.leftCx);
            this.touchY = y;
            invalidate();
            if (this.recording && !this.flipButtonWasPressed && zIsPressed) {
                rotateFlip(180.0f);
                this.delegate.onFlipClick();
            }
            if (this.recording && this.longpressRecording) {
                this.delegate.onZoom(Utilities.clamp(((this.f1827cy - AndroidUtilities.m1036dp(48.0f)) - y) / (AndroidUtilities.displaySize.y / 2.0f), 1.0f, 0.0f));
            }
        } else if (action != 1 && action != 3) {
            z = false;
        } else {
            if (!this.touch) {
                return false;
            }
            this.touch = false;
            this.discardParentTouch = false;
            AndroidUtilities.cancelRunOnUIThread(this.onRecordLongPressRunnable);
            AndroidUtilities.cancelRunOnUIThread(this.onFlipLongPressRunnable);
            if (!this.recording && this.lockButton.isPressed()) {
                this.delegate.onGalleryClick();
            } else if (this.recording && this.longpressRecording) {
                if (this.lockButton.isPressed()) {
                    this.longpressRecording = false;
                    this.lockedT.set(1.0f, true);
                    this.delegate.onVideoRecordLocked();
                } else {
                    this.recording = false;
                    this.recordingLoadingStart = SystemClock.elapsedRealtime();
                    this.recordingLoading = true;
                    this.delegate.onVideoRecordEnd(false);
                }
            } else if (this.recordButton.isPressed()) {
                if (hasCheck()) {
                    this.delegate.onCheckClick();
                } else if (!this.startModeIsVideo && !this.recording && !this.longpressRecording) {
                    this.delegate.onPhotoShoot();
                } else if (!this.recording) {
                    if (this.delegate.canRecordAudio()) {
                        this.lastDuration = 0L;
                        this.recordingStart = System.currentTimeMillis();
                        this.showLock = false;
                        this.delegate.onVideoRecordStart(false, new Runnable() { // from class: org.telegram.ui.Stories.recorder.RecordControl$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$onTouchEvent$4();
                            }
                        });
                    }
                } else {
                    this.recording = false;
                    this.recordingLoadingStart = SystemClock.elapsedRealtime();
                    this.recordingLoading = true;
                    this.delegate.onVideoRecordEnd(false);
                }
            }
            this.longpressRecording = false;
            if (this.flipButton.isPressed()) {
                rotateFlip(180.0f);
                this.delegate.onFlipClick();
            }
            this.recordButton.setPressed(false);
            this.flipButton.setPressed(false);
            this.lockButton.setPressed(false);
            invalidate();
        }
        this.flipButtonWasPressed = zIsPressed;
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTouchEvent$4() {
        this.recordingStart = System.currentTimeMillis();
        this.lastDuration = 0L;
        this.recording = true;
        this.delegate.onVideoDuration(0L);
    }

    public void stopRecording() {
        if (this.recording) {
            this.recording = false;
            this.recordingLoadingStart = SystemClock.elapsedRealtime();
            this.recordingLoading = true;
            this.delegate.onVideoRecordEnd(false);
            this.recordButton.setPressed(false);
            this.flipButton.setPressed(false);
            this.lockButton.setPressed(false);
            invalidate();
        }
    }

    public void stopRecordingLoading(boolean z) {
        this.recordingLoading = false;
        if (!z) {
            this.recordingLoadingT.set(false, true);
        }
        invalidate();
    }

    @Override // android.view.View
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        RecordControlAccessibilityHelper recordControlAccessibilityHelper = this.accessibilityHelper;
        if (recordControlAccessibilityHelper == null || !recordControlAccessibilityHelper.dispatchHoverEvent(motionEvent)) {
            return super.dispatchHoverEvent(motionEvent);
        }
        return true;
    }

    private void notifyAccessibilityIfChanged() {
        if (this.accessibilityHelper == null) {
            return;
        }
        boolean zHasCheck = hasCheck();
        boolean z = this.a11yPrevRecording;
        boolean z2 = this.recording;
        if (z == z2 && this.a11yPrevCheck == zHasCheck && this.a11yPrevDual == this.dual && this.a11yPrevStartIsVideo == this.startModeIsVideo && this.a11yPrevLoading == this.recordingLoading && this.a11yPrevShowLock == this.showLock) {
            return;
        }
        this.a11yPrevRecording = z2;
        this.a11yPrevCheck = zHasCheck;
        this.a11yPrevDual = this.dual;
        this.a11yPrevStartIsVideo = this.startModeIsVideo;
        this.a11yPrevLoading = this.recordingLoading;
        this.a11yPrevShowLock = this.showLock;
        this.accessibilityHelper.invalidateRoot();
    }

    public class RecordControlAccessibilityHelper extends ExploreByTouchHelper {
        private final Rect tmpRect;

        public RecordControlAccessibilityHelper(View view) {
            super(view);
            this.tmpRect = new Rect();
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public int getVirtualViewAt(float f, float f2) {
            if (Math.abs(f - RecordControl.this.leftCx) <= AndroidUtilities.m1036dp(30.0f) && Math.abs(f2 - RecordControl.this.f1827cy) <= AndroidUtilities.m1036dp(30.0f) && !RecordControl.this.hasCheck() && !RecordControl.this.recordingLoading) {
                return 0;
            }
            if (Math.abs(f - RecordControl.this.rightCx) > AndroidUtilities.m1036dp(30.0f) || Math.abs(f2 - RecordControl.this.f1827cy) > AndroidUtilities.m1036dp(30.0f) || RecordControl.this.hasCheck() || RecordControl.this.recordingLoading) {
                return (Math.abs(f - RecordControl.this.f1826cx) > ((float) AndroidUtilities.m1036dp(60.0f)) || Math.abs(f2 - RecordControl.this.f1827cy) > ((float) AndroidUtilities.m1036dp(60.0f))) ? Integer.MIN_VALUE : 1;
            }
            return 2;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public void getVisibleVirtualViews(List<Integer> list) {
            if (!RecordControl.this.hasCheck() && !RecordControl.this.recordingLoading) {
                list.add(0);
            }
            list.add(1);
            if (RecordControl.this.hasCheck() || RecordControl.this.recordingLoading) {
                return;
            }
            list.add(2);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            String string;
            String string2;
            accessibilityNodeInfoCompat.setClassName("android.widget.Button");
            boolean z = false;
            if (i == 0) {
                float fM1036dp = AndroidUtilities.m1036dp(22.0f);
                this.tmpRect.set((int) (RecordControl.this.leftCx - fM1036dp), (int) (RecordControl.this.f1827cy - fM1036dp), (int) (RecordControl.this.leftCx + fM1036dp), (int) (RecordControl.this.f1827cy + fM1036dp));
                accessibilityNodeInfoCompat.setBoundsInParent(this.tmpRect);
                if (RecordControl.this.recording && RecordControl.this.showLock) {
                    string = LocaleController.getString(C2797R.string.AccDescrLockRecording);
                } else {
                    string = LocaleController.getString(C2797R.string.AccDescrCameraGallery);
                }
                accessibilityNodeInfoCompat.setContentDescription(string);
                if (!RecordControl.this.recordingLoading && !RecordControl.this.hasCheck()) {
                    z = true;
                }
                accessibilityNodeInfoCompat.setEnabled(z);
                if (z) {
                    accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
                    return;
                }
                return;
            }
            if (i == 1) {
                float fM1036dp2 = AndroidUtilities.m1036dp(40.0f);
                this.tmpRect.set((int) (RecordControl.this.f1826cx - fM1036dp2), (int) (RecordControl.this.f1827cy - fM1036dp2), (int) (RecordControl.this.f1826cx + fM1036dp2), (int) (RecordControl.this.f1827cy + fM1036dp2));
                accessibilityNodeInfoCompat.setBoundsInParent(this.tmpRect);
                if (RecordControl.this.hasCheck()) {
                    string2 = LocaleController.getString(C2797R.string.Send);
                } else if (RecordControl.this.recording) {
                    string2 = LocaleController.getString(C2797R.string.AccDescrStopRecording);
                } else if (RecordControl.this.startModeIsVideo) {
                    string2 = LocaleController.getString(C2797R.string.AccDescrStartRecording);
                } else {
                    string2 = LocaleController.getString(C2797R.string.AccDescrTakePhoto);
                }
                accessibilityNodeInfoCompat.setContentDescription(string2);
                accessibilityNodeInfoCompat.setEnabled(!RecordControl.this.recordingLoading);
                if (RecordControl.this.recordingLoading) {
                    return;
                }
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
                return;
            }
            if (i == 2) {
                float fM1036dp3 = AndroidUtilities.m1036dp(22.0f);
                this.tmpRect.set((int) (RecordControl.this.rightCx - fM1036dp3), (int) (RecordControl.this.f1827cy - fM1036dp3), (int) (RecordControl.this.rightCx + fM1036dp3), (int) (RecordControl.this.f1827cy + fM1036dp3));
                accessibilityNodeInfoCompat.setBoundsInParent(this.tmpRect);
                accessibilityNodeInfoCompat.setContentDescription(LocaleController.getString(C2797R.string.AccDescrSwitchCamera));
                if (!RecordControl.this.recordingLoading && !RecordControl.this.hasCheck()) {
                    z = true;
                }
                accessibilityNodeInfoCompat.setEnabled(z);
                if (z) {
                    accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
                    return;
                }
                return;
            }
            this.tmpRect.set(0, 0, 1, 1);
            accessibilityNodeInfoCompat.setBoundsInParent(this.tmpRect);
            accessibilityNodeInfoCompat.setVisibleToUser(false);
            accessibilityNodeInfoCompat.setContentDescription(_UrlKt.FRAGMENT_ENCODE_SET);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (RecordControl.this.delegate == null || RecordControl.this.recordingLoading || i2 != 16) {
                return false;
            }
            if (i == 0) {
                if (RecordControl.this.hasCheck()) {
                    return false;
                }
                if (RecordControl.this.recording && RecordControl.this.showLock) {
                    RecordControl.this.longpressRecording = false;
                    RecordControl.this.lockedT.set(1.0f, true);
                    RecordControl.this.delegate.onVideoRecordLocked();
                    RecordControl.this.invalidate();
                } else {
                    RecordControl.this.delegate.onGalleryClick();
                }
                return true;
            }
            if (i == 1) {
                boolean zHasCheck = RecordControl.this.hasCheck();
                RecordControl recordControl = RecordControl.this;
                if (zHasCheck) {
                    recordControl.delegate.onCheckClick();
                } else {
                    boolean z = recordControl.recording;
                    RecordControl recordControl2 = RecordControl.this;
                    if (z) {
                        recordControl2.recording = false;
                        RecordControl.this.longpressRecording = false;
                        RecordControl.this.recordingLoadingStart = SystemClock.elapsedRealtime();
                        RecordControl.this.recordingLoading = true;
                        RecordControl.this.delegate.onVideoRecordEnd(false);
                        RecordControl.this.invalidate();
                    } else {
                        boolean z2 = recordControl2.startModeIsVideo;
                        RecordControl recordControl3 = RecordControl.this;
                        if (z2) {
                            if (recordControl3.delegate.canRecordAudio()) {
                                RecordControl.this.lastDuration = 0L;
                                RecordControl.this.recordingStart = System.currentTimeMillis();
                                RecordControl.this.showLock = false;
                                RecordControl.this.delegate.onVideoRecordStart(false, new Runnable() { // from class: org.telegram.ui.Stories.recorder.RecordControl$RecordControlAccessibilityHelper$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        this.f$0.lambda$onPerformActionForVirtualView$0();
                                    }
                                });
                            }
                        } else {
                            recordControl3.delegate.onPhotoShoot();
                        }
                    }
                }
                return true;
            }
            if (i != 2 || RecordControl.this.hasCheck()) {
                return false;
            }
            RecordControl.this.rotateFlip(180.0f);
            RecordControl.this.delegate.onFlipClick();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPerformActionForVirtualView$0() {
            RecordControl.this.recordingStart = System.currentTimeMillis();
            RecordControl.this.lastDuration = 0L;
            RecordControl.this.recording = true;
            RecordControl.this.delegate.onVideoDuration(RecordControl.this.lastDuration);
            RecordControl.this.invalidate();
        }
    }
}
