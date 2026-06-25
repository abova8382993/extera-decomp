package org.telegram.p035ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.Keep;
import android.text.TextPaint;
import android.view.animation.DecelerateInterpolator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p035ui.ActionBar.Theme;

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
    private Paint drawablePaint = new Paint(1);
    private RectF rect = new RectF();
    private final ColorFilter whiteColorFilter = new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_IN);
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

    public static boolean isCustomFileIcon(int i) {
        return i == 16 || i == 17 || i == 18;
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
        this.paint.setStrokeWidth(AndroidUtilities.m1036dp(3.0f));
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint3.setColor(-1);
        this.textPaint.setTypeface(AndroidUtilities.bold());
        this.textPaint.setTextSize(AndroidUtilities.m1036dp(13.0f));
        this.textPaint.setColor(-1);
        this.paint2.setColor(-1);
    }

    public void setOverrideAlpha(float f) {
        this.overrideAlpha = f;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
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
        this.paint.setStrokeWidth(AndroidUtilities.m1036dp(z ? 2.0f : 3.0f));
    }

    private void drawTintedDrawable(Canvas canvas, Drawable drawable, int i, int i2, float f, int i3) {
        int intrinsicWidth = ((int) (drawable.getIntrinsicWidth() * f)) / 2;
        int i4 = i - intrinsicWidth;
        int intrinsicHeight = ((int) (drawable.getIntrinsicHeight() * f)) / 2;
        int i5 = i2 - intrinsicHeight;
        int i6 = i + intrinsicWidth;
        int i7 = i2 + intrinsicHeight;
        applyShaderMatrix(true);
        float f2 = i4;
        float f3 = i5;
        float f4 = i6;
        float f5 = i7;
        int iSaveLayer = canvas.saveLayer(f2, f3, f4, f5, null);
        drawable.setColorFilter(this.whiteColorFilter);
        drawable.setAlpha(i3);
        drawable.setBounds(i4, i5, i6, i7);
        drawable.draw(canvas);
        this.drawablePaint.set(this.paint2);
        this.drawablePaint.setAlpha(i3);
        this.drawablePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(f2, f3, f4, f5, this.drawablePaint);
        this.drawablePaint.setXfermode(null);
        canvas.restoreToCount(iSaveLayer);
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
        int i4 = this.currentIcon;
        if (z) {
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
            if (i4 == i) {
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
            this.paint.setStrokeWidth(AndroidUtilities.m1036dp(2.0f));
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
    /* JADX WARN: Removed duplicated region for block: B:165:0x04e0  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0574  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x066d  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0677  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x0681  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x06f1  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x06f5  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x06fa  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0721  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0724  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0744  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x074b  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x077a  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x077f  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x07ae  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x07b2  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x07bf  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x07c4  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x07d1  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x07d8  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x07e1  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x07ea  */
    /* JADX WARN: Removed duplicated region for block: B:316:0x07f1  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x07f6  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x07ff  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x0805  */
    /* JADX WARN: Removed duplicated region for block: B:329:0x0814 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0816  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x0828  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x082b  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x0844  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x0851  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x0892  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x089b  */
    /* JADX WARN: Removed duplicated region for block: B:347:0x089f  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x08b0  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x08b5  */
    /* JADX WARN: Removed duplicated region for block: B:358:0x08c4  */
    /* JADX WARN: Removed duplicated region for block: B:359:0x08c7  */
    /* JADX WARN: Removed duplicated region for block: B:362:0x08d9  */
    /* JADX WARN: Removed duplicated region for block: B:365:0x090f  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x0918  */
    /* JADX WARN: Removed duplicated region for block: B:370:0x091c  */
    /* JADX WARN: Removed duplicated region for block: B:374:0x092b  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x092e  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x0954  */
    /* JADX WARN: Removed duplicated region for block: B:386:0x0969  */
    /* JADX WARN: Removed duplicated region for block: B:389:0x0998  */
    /* JADX WARN: Removed duplicated region for block: B:392:0x09a0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:396:0x09a8  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x09ae  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x09b0  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x09ba  */
    /* JADX WARN: Removed duplicated region for block: B:408:0x09c5  */
    /* JADX WARN: Removed duplicated region for block: B:410:0x09c8 A[PHI: r3
  0x09c8: PHI (r3v107 int) = (r3v103 int), (r3v128 int) binds: [B:414:0x09d0, B:409:0x09c6] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:411:0x09cb A[PHI: r3
  0x09cb: PHI (r3v106 int) = (r3v103 int), (r3v128 int) binds: [B:414:0x09d0, B:409:0x09c6] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:413:0x09cf  */
    /* JADX WARN: Removed duplicated region for block: B:418:0x09d7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:419:0x09d9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:432:0x0a2c  */
    /* JADX WARN: Removed duplicated region for block: B:433:0x0a2f  */
    /* JADX WARN: Removed duplicated region for block: B:436:0x0a38  */
    /* JADX WARN: Removed duplicated region for block: B:446:0x0a62  */
    /* JADX WARN: Removed duplicated region for block: B:451:0x0a6e  */
    /* JADX WARN: Removed duplicated region for block: B:452:0x0a79  */
    /* JADX WARN: Removed duplicated region for block: B:457:0x0a93  */
    /* JADX WARN: Removed duplicated region for block: B:459:0x0a96  */
    /* JADX WARN: Removed duplicated region for block: B:465:0x0ab9  */
    /* JADX WARN: Removed duplicated region for block: B:467:0x0abd  */
    /* JADX WARN: Removed duplicated region for block: B:471:0x0aca  */
    /* JADX WARN: Removed duplicated region for block: B:479:0x0af6  */
    /* JADX WARN: Removed duplicated region for block: B:486:0x0b23  */
    /* JADX WARN: Removed duplicated region for block: B:488:0x0b59  */
    /* JADX WARN: Removed duplicated region for block: B:491:0x0b62  */
    /* JADX WARN: Removed duplicated region for block: B:493:0x0b7f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:502:0x0bd7  */
    /* JADX WARN: Removed duplicated region for block: B:504:0x0bdf  */
    /* JADX WARN: Removed duplicated region for block: B:512:0x0c27  */
    /* JADX WARN: Removed duplicated region for block: B:515:0x0c2d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:518:0x0c42  */
    /* JADX WARN: Removed duplicated region for block: B:519:0x0c45  */
    /* JADX WARN: Removed duplicated region for block: B:522:0x0c6f  */
    /* JADX WARN: Removed duplicated region for block: B:525:0x0c7a  */
    /* JADX WARN: Removed duplicated region for block: B:528:0x0c84  */
    /* JADX WARN: Removed duplicated region for block: B:549:0x0d10  */
    /* JADX WARN: Removed duplicated region for block: B:552:0x0d18  */
    /* JADX WARN: Removed duplicated region for block: B:562:0x0d2b  */
    /* JADX WARN: Removed duplicated region for block: B:564:0x0d43  */
    /* JADX WARN: Removed duplicated region for block: B:573:0x0d73  */
    /* JADX WARN: Removed duplicated region for block: B:581:0x0d95  */
    /* JADX WARN: Removed duplicated region for block: B:583:? A[RETURN, SYNTHETIC] */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void draw(android.graphics.Canvas r38) {
        /*
            Method dump skipped, instruction units count: 3481
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.MediaActionDrawable.draw(android.graphics.Canvas):void");
    }

    private Drawable getCustomIconDrawable(int i) {
        if (i == 16) {
            return Theme.chat_pluginIcon;
        }
        if (i == 17) {
            return Theme.chat_settingsIcon;
        }
        if (i == 18) {
            return Theme.chat_stickersIcon;
        }
        return null;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return AndroidUtilities.m1036dp(48.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return AndroidUtilities.m1036dp(48.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        return AndroidUtilities.m1036dp(48.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        return AndroidUtilities.m1036dp(48.0f);
    }
}
