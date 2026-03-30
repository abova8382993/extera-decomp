package org.telegram.p026ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p026ui.ActionBar.Theme;

/* JADX INFO: loaded from: classes5.dex */
public class TransformableLoginButtonView extends View {
    private final Paint backgroundPaint;
    private String buttonText;
    private float buttonWidth;
    private boolean drawBackground;
    private final Paint outlinePaint;
    private float progress;
    private final RectF rect;
    private Drawable rippleDrawable;
    private TextPaint textPaint;
    private int transformType;

    public TransformableLoginButtonView(Context context) {
        super(context);
        Paint paint = new Paint(1);
        this.backgroundPaint = paint;
        Paint paint2 = new Paint(1);
        this.outlinePaint = paint2;
        this.drawBackground = true;
        this.transformType = 0;
        this.rect = new RectF();
        paint.setColor(Theme.getColor(Theme.key_chats_actionBackground));
        paint2.setStrokeWidth(AndroidUtilities.m1081dp(2.0f));
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setDrawBackground(boolean z) {
        this.drawBackground = z;
    }

    public void setRippleDrawable(Drawable drawable) {
        this.rippleDrawable = drawable;
        invalidate();
    }

    public void setTransformType(int i) {
        this.transformType = i;
        invalidate();
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.backgroundPaint.setColor(i);
        invalidate();
    }

    public void setColor(int i) {
        this.outlinePaint.setColor(i);
        invalidate();
    }

    public void setButtonText(TextPaint textPaint, String str) {
        this.textPaint = textPaint;
        this.buttonText = str;
        this.outlinePaint.setColor(textPaint.getColor());
        this.buttonWidth = textPaint.measureText(str);
    }

    public void setProgress(float f) {
        this.progress = f;
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        if (this.drawBackground) {
            float fM1081dp = AndroidUtilities.m1081dp(((this.transformType == 0 ? this.progress : 1.0f) * 26.0f) + 6.0f);
            this.rect.set(0.0f, 0.0f, getWidth(), getHeight());
            canvas2.drawRoundRect(this.rect, fM1081dp, fM1081dp, this.backgroundPaint);
        }
        int i = this.transformType;
        if (i == 0) {
            TextPaint textPaint = this.textPaint;
            if (textPaint != null && this.buttonText != null) {
                int alpha = textPaint.getAlpha();
                this.textPaint.setAlpha((int) (alpha * (1.0f - (Math.min(0.6f, this.progress) / 0.6f))));
                canvas2.drawText(this.buttonText, (getWidth() - this.buttonWidth) / 2.0f, ((getHeight() / 2.0f) + (this.textPaint.getTextSize() / 2.0f)) - AndroidUtilities.m1081dp(1.75f), this.textPaint);
                this.textPaint.setAlpha(alpha);
            }
            float fMax = (Math.max(0.4f, this.progress) - 0.4f) / 0.6f;
            if (fMax != 0.0f) {
                float fM1081dp2 = AndroidUtilities.m1081dp(21.0f) + ((getWidth() - (AndroidUtilities.m1081dp(21.0f) * 2)) * fMax);
                float height = getHeight() / 2.0f;
                canvas2.drawLine(AndroidUtilities.m1081dp(21.0f), height, fM1081dp2, height, this.outlinePaint);
                double dM1081dp = AndroidUtilities.m1081dp(9.0f) * fMax;
                float fCos = (float) (((double) fM1081dp2) - (Math.cos(0.7853981633974483d) * dM1081dp));
                float fSin = (float) (Math.sin(0.7853981633974483d) * dM1081dp);
                canvas2 = canvas;
                canvas2.drawLine(fM1081dp2, height, fCos, height - fSin, this.outlinePaint);
                canvas2.drawLine(fM1081dp2, height, fCos, height + fSin, this.outlinePaint);
            }
        } else if (i == 1) {
            float fM1081dp3 = AndroidUtilities.m1081dp(21.0f);
            float width = getWidth() - AndroidUtilities.m1081dp(21.0f);
            float height2 = getHeight() / 2.0f;
            canvas2.save();
            canvas2.translate((-AndroidUtilities.m1081dp(2.0f)) * this.progress, 0.0f);
            canvas2.rotate(this.progress * 90.0f, getWidth() / 2.0f, getHeight() / 2.0f);
            canvas2.drawLine(fM1081dp3 + ((width - fM1081dp3) * this.progress), height2, width, height2, this.outlinePaint);
            int iM1081dp = AndroidUtilities.m1081dp((this.progress * (-1.0f)) + 9.0f);
            int iM1081dp2 = AndroidUtilities.m1081dp((this.progress * 7.0f) + 9.0f);
            double d = width;
            double d2 = iM1081dp;
            double d3 = height2;
            canvas.drawLine(width, height2, (float) (d - (Math.cos(0.7853981633974483d) * d2)), (float) ((d2 * Math.sin(0.7853981633974483d)) + d3), this.outlinePaint);
            double d4 = iM1081dp2;
            canvas2 = canvas;
            canvas2.drawLine(width, height2, (float) (d - (Math.cos(0.7853981633974483d) * d4)), (float) (d3 - (d4 * Math.sin(0.7853981633974483d))), this.outlinePaint);
            canvas2.restore();
        }
        Drawable drawable = this.rippleDrawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getHeight());
            this.rippleDrawable.setHotspotBounds(0, 0, getWidth(), getHeight());
            this.rippleDrawable.draw(canvas2);
        }
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.rippleDrawable;
        if (drawable != null) {
            drawable.setState(getDrawableState());
            invalidate();
        }
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.rippleDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.rippleDrawable;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        if (super.verifyDrawable(drawable)) {
            return true;
        }
        Drawable drawable2 = this.rippleDrawable;
        return drawable2 != null && drawable == drawable2;
    }
}
