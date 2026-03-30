package org.telegram.p029ui.Components;

import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.Keep;
import android.text.TextPaint;
import android.view.animation.DecelerateInterpolator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p029ui.ActionBar.Theme;

/* JADX INFO: loaded from: classes3.dex */
public class MediaActionDrawable extends Drawable {
    private float animatedDownloadProgress;
    private boolean animatingTransition;
    private ColorFilter colorFilter;
    private int currentIcon;
    private MediaActionDrawableDelegate delegate;
    private float downloadProgress;
    private float downloadProgressAnimationStart;
    private float downloadProgressTime;
    private float downloadRadOffset;
    private LinearGradient gradientDrawable;
    private Matrix gradientMatrix;
    private boolean hasOverlayImage;
    private boolean isMini;
    private long lastAnimationTime;
    private Theme.MessageDrawable messageDrawable;
    private int nextIcon;
    private String percentString;
    private int percentStringWidth;
    private float savedTransitionProgress;
    private TextPaint textPaint = new TextPaint(1);
    public Paint paint = new Paint(1);
    private Paint backPaint = new Paint(1);
    public Paint paint2 = new Paint(1);
    private Paint paint3 = new Paint(1);
    private RectF rect = new RectF();
    private float scale = 1.0f;
    private DecelerateInterpolator interpolator = new DecelerateInterpolator();
    private float transitionAnimationTime = 400.0f;
    private int lastPercent = -1;
    private float overrideAlpha = 1.0f;
    private float transitionProgress = 1.0f;
    public boolean drawProgressCircle = true;
    private float downloadIconScale = 1.0f;

    /* JADX INFO: loaded from: classes7.dex */
    public interface MediaActionDrawableDelegate {
        void invalidate();
    }

    public static float getCircleValue(float f) {
        while (f > 360.0f) {
            f -= 360.0f;
        }
        return f;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    public void setDownloadIconScale(float f) {
        this.downloadIconScale = f;
    }

    @Keep
    public float getDownloadIconScale() {
        return this.downloadIconScale;
    }

    public MediaActionDrawable() {
        this.paint.setColor(-1);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        this.paint.setStrokeWidth(AndroidUtilities.m1124dp(3.0f));
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint3.setColor(-1);
        this.textPaint.setTypeface(AndroidUtilities.bold());
        this.textPaint.setTextSize(AndroidUtilities.m1124dp(13.0f));
        this.textPaint.setColor(-1);
        this.paint2.setColor(-1);
    }

    public void setOverrideAlpha(float f) {
        this.overrideAlpha = f;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
        this.paint2.setColorFilter(colorFilter);
        this.paint3.setColorFilter(colorFilter);
        this.textPaint.setColorFilter(colorFilter);
    }

    public void setColor(int i) {
        int i2 = (-16777216) | i;
        this.paint.setColor(i2);
        this.paint2.setColor(i2);
        this.paint3.setColor(i2);
        this.textPaint.setColor(i2);
        this.colorFilter = new PorterDuffColorFilter(i, PorterDuff.Mode.MULTIPLY);
    }

    public void setBackColor(int i) {
        this.backPaint.setColor(i | (-16777216));
    }

    public void setMini(boolean z) {
        this.isMini = z;
        this.paint.setStrokeWidth(AndroidUtilities.m1124dp(z ? 2.0f : 3.0f));
    }

    public void setDelegate(MediaActionDrawableDelegate mediaActionDrawableDelegate) {
        this.delegate = mediaActionDrawableDelegate;
    }

    public boolean setIcon(int i, boolean z) {
        int i2;
        int i3;
        if (this.currentIcon == i && (i3 = this.nextIcon) != i) {
            this.currentIcon = i3;
            this.transitionProgress = 1.0f;
        }
        if (z) {
            int i4 = this.currentIcon;
            if (i4 == i || (i2 = this.nextIcon) == i) {
                return false;
            }
            if ((i4 == 0 && i == 1) || (i4 == 1 && i == 0)) {
                this.transitionAnimationTime = 300.0f;
            } else if (i4 == 2 && (i == 3 || i == 14)) {
                this.transitionAnimationTime = this.drawProgressCircle ? 400.0f : 250.0f;
            } else if (i4 != 4 && i == 6) {
                this.transitionAnimationTime = 360.0f;
            } else if ((i4 == 4 && i == 14) || (i4 == 14 && i == 4)) {
                this.transitionAnimationTime = 160.0f;
            } else {
                this.transitionAnimationTime = 220.0f;
            }
            if (this.animatingTransition) {
                this.currentIcon = i2;
            }
            this.animatingTransition = true;
            this.nextIcon = i;
            this.savedTransitionProgress = this.transitionProgress;
            this.transitionProgress = 0.0f;
        } else {
            if (this.currentIcon == i) {
                return false;
            }
            this.animatingTransition = false;
            this.nextIcon = i;
            this.currentIcon = i;
            this.savedTransitionProgress = this.transitionProgress;
            this.transitionProgress = 1.0f;
        }
        if (i == 3 || i == 14) {
            this.downloadRadOffset = 112.0f;
            this.animatedDownloadProgress = 0.0f;
            this.downloadProgressAnimationStart = 0.0f;
            this.downloadProgressTime = 0.0f;
        }
        invalidateSelf();
        return true;
    }

    public int getCurrentIcon() {
        return this.nextIcon;
    }

    public int getPreviousIcon() {
        return this.currentIcon;
    }

    public void setProgress(float f, boolean z) {
        if (this.downloadProgress == f) {
            return;
        }
        if (!z) {
            this.animatedDownloadProgress = f;
            this.downloadProgressAnimationStart = f;
        } else {
            if (this.animatedDownloadProgress > f) {
                this.animatedDownloadProgress = f;
            }
            this.downloadProgressAnimationStart = this.animatedDownloadProgress;
        }
        this.downloadProgress = f;
        this.downloadProgressTime = 0.0f;
        invalidateSelf();
    }

    public float getProgress() {
        return this.downloadProgress;
    }

    public float getTransitionProgress() {
        if (this.animatingTransition) {
            return this.transitionProgress;
        }
        return 1.0f;
    }

    public void setBackgroundDrawable(Theme.MessageDrawable messageDrawable) {
        this.messageDrawable = messageDrawable;
    }

    public void setBackgroundGradientDrawable(LinearGradient linearGradient) {
        this.gradientDrawable = linearGradient;
        this.gradientMatrix = new Matrix();
    }

    public void setHasOverlayImage(boolean z) {
        this.hasOverlayImage = z;
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        float intrinsicWidth = (i3 - i) / getIntrinsicWidth();
        this.scale = intrinsicWidth;
        if (intrinsicWidth < 0.7f) {
            this.paint.setStrokeWidth(AndroidUtilities.m1124dp(2.0f));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        super.invalidateSelf();
        MediaActionDrawableDelegate mediaActionDrawableDelegate = this.delegate;
        if (mediaActionDrawableDelegate != null) {
            mediaActionDrawableDelegate.invalidate();
        }
    }

    public void applyShaderMatrix(boolean z) {
        Theme.MessageDrawable messageDrawable = this.messageDrawable;
        if (messageDrawable == null || !messageDrawable.hasGradient() || this.hasOverlayImage) {
            return;
        }
        Rect bounds = getBounds();
        Shader gradientShader = this.messageDrawable.getGradientShader();
        Matrix matrix = this.messageDrawable.getMatrix();
        matrix.reset();
        this.messageDrawable.applyMatrixScale();
        if (z) {
            matrix.postTranslate(-bounds.centerX(), (-this.messageDrawable.getTopY()) + bounds.top);
        } else {
            matrix.postTranslate(0.0f, -this.messageDrawable.getTopY());
        }
        gradientShader.setLocalMatrix(matrix);
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0309  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0317  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0328  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x04e4  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0578  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x066c  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0676  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0680  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x06f0  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x06f4  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x06f9  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x0701  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x070e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:266:0x0720  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0723  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0743  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x074a  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0779  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x077e  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x07ad  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x07b1  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x07be  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x07c4  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x07cd  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x07d5  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x07e4  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x07e8  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x07f7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:319:0x07f9  */
    /* JADX WARN: Removed duplicated region for block: B:323:0x080c  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x080f  */
    /* JADX WARN: Removed duplicated region for block: B:327:0x0828  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x086f  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x0878  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x087c  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x088d  */
    /* JADX WARN: Removed duplicated region for block: B:340:0x0892  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x08a3  */
    /* JADX WARN: Removed duplicated region for block: B:347:0x08a6  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x08b8  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x08ee  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x08f7  */
    /* JADX WARN: Removed duplicated region for block: B:358:0x08fb  */
    /* JADX WARN: Removed duplicated region for block: B:362:0x090a  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x090d  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x0935  */
    /* JADX WARN: Removed duplicated region for block: B:373:0x094a  */
    /* JADX WARN: Removed duplicated region for block: B:376:0x097c  */
    /* JADX WARN: Removed duplicated region for block: B:379:0x0984 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:383:0x098c  */
    /* JADX WARN: Removed duplicated region for block: B:386:0x0992  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x0994  */
    /* JADX WARN: Removed duplicated region for block: B:391:0x099c  */
    /* JADX WARN: Removed duplicated region for block: B:396:0x09ab  */
    /* JADX WARN: Removed duplicated region for block: B:398:0x09b0 A[PHI: r4
  0x09b0: PHI (r4v123 int) = (r4v120 int), (r4v135 int) binds: [B:401:0x09b6, B:397:0x09ae] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:399:0x09b3 A[PHI: r4
  0x09b3: PHI (r4v122 int) = (r4v120 int), (r4v135 int) binds: [B:401:0x09b6, B:397:0x09ae] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:405:0x09bd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:408:0x09c2 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:422:0x0a17  */
    /* JADX WARN: Removed duplicated region for block: B:423:0x0a1a  */
    /* JADX WARN: Removed duplicated region for block: B:426:0x0a23  */
    /* JADX WARN: Removed duplicated region for block: B:436:0x0a4d  */
    /* JADX WARN: Removed duplicated region for block: B:441:0x0a59  */
    /* JADX WARN: Removed duplicated region for block: B:442:0x0a64  */
    /* JADX WARN: Removed duplicated region for block: B:447:0x0a7e  */
    /* JADX WARN: Removed duplicated region for block: B:449:0x0a81  */
    /* JADX WARN: Removed duplicated region for block: B:455:0x0aa4  */
    /* JADX WARN: Removed duplicated region for block: B:457:0x0aa8  */
    /* JADX WARN: Removed duplicated region for block: B:461:0x0ab5  */
    /* JADX WARN: Removed duplicated region for block: B:469:0x0ae1  */
    /* JADX WARN: Removed duplicated region for block: B:476:0x0b10  */
    /* JADX WARN: Removed duplicated region for block: B:478:0x0b46  */
    /* JADX WARN: Removed duplicated region for block: B:481:0x0b4f  */
    /* JADX WARN: Removed duplicated region for block: B:482:0x0b6b  */
    /* JADX WARN: Removed duplicated region for block: B:484:0x0b6f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:487:0x0b8a  */
    /* JADX WARN: Removed duplicated region for block: B:488:0x0b8d  */
    /* JADX WARN: Removed duplicated region for block: B:491:0x0bad  */
    /* JADX WARN: Removed duplicated region for block: B:498:0x0be9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:501:0x0bfe  */
    /* JADX WARN: Removed duplicated region for block: B:502:0x0c01  */
    /* JADX WARN: Removed duplicated region for block: B:505:0x0c2b  */
    /* JADX WARN: Removed duplicated region for block: B:508:0x0c36  */
    /* JADX WARN: Removed duplicated region for block: B:511:0x0c40  */
    /* JADX WARN: Removed duplicated region for block: B:532:0x0cd0  */
    /* JADX WARN: Removed duplicated region for block: B:535:0x0cd8  */
    /* JADX WARN: Removed duplicated region for block: B:545:0x0ceb  */
    /* JADX WARN: Removed duplicated region for block: B:547:0x0d03  */
    /* JADX WARN: Removed duplicated region for block: B:556:0x0d35  */
    /* JADX WARN: Removed duplicated region for block: B:564:0x0d59  */
    /* JADX WARN: Removed duplicated region for block: B:566:? A[RETURN, SYNTHETIC] */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void draw(android.graphics.Canvas r38) {
        /*
            Method dump skipped, instruction units count: 3421
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.Components.MediaActionDrawable.draw(android.graphics.Canvas):void");
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return AndroidUtilities.m1124dp(48.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return AndroidUtilities.m1124dp(48.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        return AndroidUtilities.m1124dp(48.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        return AndroidUtilities.m1124dp(48.0f);
    }
}
