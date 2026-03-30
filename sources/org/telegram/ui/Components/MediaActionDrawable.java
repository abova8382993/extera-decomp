package org.telegram.ui.Components;

import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.animation.DecelerateInterpolator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

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

    public MediaActionDrawable() {
        this.paint.setColor(-1);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        this.paint.setStrokeWidth(AndroidUtilities.dp(3.0f));
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint3.setColor(-1);
        this.textPaint.setTypeface(AndroidUtilities.bold());
        this.textPaint.setTextSize(AndroidUtilities.dp(13.0f));
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
        this.paint.setStrokeWidth(AndroidUtilities.dp(z ? 2.0f : 3.0f));
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
            this.paint.setStrokeWidth(AndroidUtilities.dp(2.0f));
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
        android.graphics.Rect bounds = getBounds();
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
    /* JADX WARN: Removed duplicated region for block: B:172:0x052f  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x05d0  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x06c6  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x06d0  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x06da  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x074c  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x0752  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0757  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x075f  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x076c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:272:0x077e  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0781  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x07a1  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x07a8  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x07d8  */
    /* JADX WARN: Removed duplicated region for block: B:285:0x07dd  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x080c  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x0810  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x081d  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0822  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x082b  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x0833  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x0842  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x0846  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x0855 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:325:0x0857  */
    /* JADX WARN: Removed duplicated region for block: B:329:0x086a  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x086d  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x0886  */
    /* JADX WARN: Removed duplicated region for block: B:336:0x08cd  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x08d6  */
    /* JADX WARN: Removed duplicated region for block: B:341:0x08da  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x08eb  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x08f0  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x0901  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0904  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0916  */
    /* JADX WARN: Removed duplicated region for block: B:359:0x094c  */
    /* JADX WARN: Removed duplicated region for block: B:362:0x0955  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x0959  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x0968  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x096b  */
    /* JADX WARN: Removed duplicated region for block: B:374:0x0993  */
    /* JADX WARN: Removed duplicated region for block: B:379:0x09a8  */
    /* JADX WARN: Removed duplicated region for block: B:382:0x09da  */
    /* JADX WARN: Removed duplicated region for block: B:385:0x09e2 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:389:0x09ea  */
    /* JADX WARN: Removed duplicated region for block: B:392:0x09f0  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x09f2  */
    /* JADX WARN: Removed duplicated region for block: B:397:0x09fa  */
    /* JADX WARN: Removed duplicated region for block: B:402:0x0a0a  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x0a0f A[PHI: r5
  0x0a0f: PHI (r5v104 int) = (r5v101 int), (r5v119 int) binds: [B:407:0x0a15, B:403:0x0a0d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:405:0x0a12 A[PHI: r5
  0x0a12: PHI (r5v103 int) = (r5v101 int), (r5v119 int) binds: [B:407:0x0a15, B:403:0x0a0d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:411:0x0a1c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:414:0x0a21 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:428:0x0a76  */
    /* JADX WARN: Removed duplicated region for block: B:429:0x0a79  */
    /* JADX WARN: Removed duplicated region for block: B:432:0x0a82  */
    /* JADX WARN: Removed duplicated region for block: B:442:0x0aac  */
    /* JADX WARN: Removed duplicated region for block: B:447:0x0ab8  */
    /* JADX WARN: Removed duplicated region for block: B:448:0x0ac3  */
    /* JADX WARN: Removed duplicated region for block: B:453:0x0add  */
    /* JADX WARN: Removed duplicated region for block: B:455:0x0ae0  */
    /* JADX WARN: Removed duplicated region for block: B:461:0x0b03  */
    /* JADX WARN: Removed duplicated region for block: B:463:0x0b07  */
    /* JADX WARN: Removed duplicated region for block: B:467:0x0b14  */
    /* JADX WARN: Removed duplicated region for block: B:475:0x0b40  */
    /* JADX WARN: Removed duplicated region for block: B:482:0x0b6f  */
    /* JADX WARN: Removed duplicated region for block: B:484:0x0ba5  */
    /* JADX WARN: Removed duplicated region for block: B:487:0x0bae  */
    /* JADX WARN: Removed duplicated region for block: B:488:0x0bca  */
    /* JADX WARN: Removed duplicated region for block: B:490:0x0bce A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:493:0x0be9  */
    /* JADX WARN: Removed duplicated region for block: B:494:0x0bec  */
    /* JADX WARN: Removed duplicated region for block: B:497:0x0c0c  */
    /* JADX WARN: Removed duplicated region for block: B:504:0x0c48 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:507:0x0c5d  */
    /* JADX WARN: Removed duplicated region for block: B:508:0x0c60  */
    /* JADX WARN: Removed duplicated region for block: B:511:0x0c8a  */
    /* JADX WARN: Removed duplicated region for block: B:514:0x0c95  */
    /* JADX WARN: Removed duplicated region for block: B:517:0x0c9f  */
    /* JADX WARN: Removed duplicated region for block: B:538:0x0d2f  */
    /* JADX WARN: Removed duplicated region for block: B:541:0x0d37  */
    /* JADX WARN: Removed duplicated region for block: B:551:0x0d4a  */
    /* JADX WARN: Removed duplicated region for block: B:553:0x0d62  */
    /* JADX WARN: Removed duplicated region for block: B:562:0x0d94  */
    /* JADX WARN: Removed duplicated region for block: B:570:0x0db8  */
    /* JADX WARN: Removed duplicated region for block: B:572:? A[RETURN, SYNTHETIC] */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void draw(android.graphics.Canvas r41) {
        /*
            Method dump skipped, instruction units count: 3516
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.MediaActionDrawable.draw(android.graphics.Canvas):void");
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return AndroidUtilities.dp(48.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(48.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        return AndroidUtilities.dp(48.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        return AndroidUtilities.dp(48.0f);
    }
}
