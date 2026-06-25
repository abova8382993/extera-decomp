package org.telegram.p035ui.Components.Paint.Views;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.Keep;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p035ui.Components.Paint.Swatch;

/* JADX INFO: loaded from: classes7.dex */
public class ColorPicker extends FrameLayout {
    private static final int[] COLORS = {-1431751, -2409774, -13610525, -11942419, -8337308, -205211, -223667, -16777216, -1};
    private static final float[] LOCATIONS = {0.0f, 0.14f, 0.24f, 0.39f, 0.49f, 0.62f, 0.73f, 0.85f, 1.0f};
    private Paint backgroundPaint;
    private boolean changingWeight;
    private boolean dragging;
    private float draggingFactor;
    private Paint gradientPaint;
    private boolean interacting;
    private OvershootInterpolator interpolator;
    private float location;
    private RectF rectF;
    public ImageView settingsButton;
    private Paint swatchPaint;
    private Paint swatchStrokePaint;
    private ImageView undoButton;
    private boolean wasChangingWeight;
    private float weight;

    public interface ColorPickerDelegate {
    }

    public void setDelegate(ColorPickerDelegate colorPickerDelegate) {
    }

    public void setUndoEnabled(boolean z) {
        this.undoButton.setAlpha(z ? 1.0f : 0.3f);
        this.undoButton.setEnabled(z);
    }

    public View getSettingsButton() {
        return this.settingsButton;
    }

    public void setSettingsButtonImage(int i) {
        this.settingsButton.setImageResource(i);
    }

    public Swatch getSwatch() {
        return new Swatch(colorForLocation(this.location), this.location, this.weight);
    }

    public void setSwatch(Swatch swatch) {
        setLocation(swatch.colorLocation);
        setWeight(swatch.brushWeight);
    }

    public int colorForLocation(float f) {
        float[] fArr;
        int i;
        if (f <= 0.0f) {
            return COLORS[0];
        }
        int i2 = 1;
        if (f >= 1.0f) {
            int[] iArr = COLORS;
            return iArr[iArr.length - 1];
        }
        while (true) {
            fArr = LOCATIONS;
            if (i2 >= fArr.length) {
                i2 = -1;
                i = -1;
                break;
            }
            if (fArr[i2] >= f) {
                i = i2 - 1;
                break;
            }
            i2++;
        }
        float f2 = fArr[i];
        int[] iArr2 = COLORS;
        return interpolateColors(iArr2[i], iArr2[i2], (f - f2) / (fArr[i2] - f2));
    }

    private int interpolateColors(int i, int i2, float f) {
        float fMin = Math.min(Math.max(f, 0.0f), 1.0f);
        int iRed = Color.red(i);
        int iRed2 = Color.red(i2);
        int iGreen = Color.green(i);
        int iGreen2 = Color.green(i2);
        return Color.argb(255, Math.min(255, (int) (iRed + ((iRed2 - iRed) * fMin))), Math.min(255, (int) (iGreen + ((iGreen2 - iGreen) * fMin))), Math.min(255, (int) (Color.blue(i) + ((Color.blue(i2) - r5) * fMin))));
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setLocation(float r6) {
        /*
            r5 = this;
            r5.location = r6
            int r6 = r5.colorForLocation(r6)
            android.graphics.Paint r0 = r5.swatchPaint
            r0.setColor(r6)
            r0 = 3
            float[] r0 = new float[r0]
            android.graphics.Color.colorToHSV(r6, r0)
            r1 = 0
            r1 = r0[r1]
            double r1 = (double) r1
            r3 = 4562254508917369340(0x3f50624dd2f1a9fc, double:0.001)
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 >= 0) goto L4a
            r1 = 1
            r1 = r0[r1]
            double r1 = (double) r1
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 >= 0) goto L4a
            r1 = 2
            r0 = r0[r1]
            r1 = 1064011039(0x3f6b851f, float:0.92)
            int r2 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r2 <= 0) goto L4a
            float r0 = r0 - r1
            r6 = 1034147594(0x3da3d70a, float:0.08)
            float r0 = r0 / r6
            r6 = 1046562734(0x3e6147ae, float:0.22)
            float r0 = r0 * r6
            r6 = 1065353216(0x3f800000, float:1.0)
            float r6 = r6 - r0
            r0 = 1132396544(0x437f0000, float:255.0)
            float r6 = r6 * r0
            int r6 = (int) r6
            android.graphics.Paint r0 = r5.swatchStrokePaint
            int r6 = android.graphics.Color.rgb(r6, r6, r6)
            r0.setColor(r6)
            goto L4f
        L4a:
            android.graphics.Paint r0 = r5.swatchStrokePaint
            r0.setColor(r6)
        L4f:
            r5.invalidate()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.Paint.Views.ColorPicker.setLocation(float):void");
    }

    public void setWeight(float f) {
        this.weight = f;
        invalidate();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() > 1) {
            return false;
        }
        float x = motionEvent.getX() - this.rectF.left;
        float y = motionEvent.getY() - this.rectF.top;
        if (!this.interacting && y < (-AndroidUtilities.m1036dp(10.0f))) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 3 || actionMasked == 1 || actionMasked == 6) {
            this.interacting = false;
            this.wasChangingWeight = this.changingWeight;
            this.changingWeight = false;
            setDragging(false, true);
        } else if (actionMasked == 0 || actionMasked == 2) {
            if (!this.interacting) {
                this.interacting = true;
            }
            setLocation(Math.max(0.0f, Math.min(1.0f, x / this.rectF.width())));
            setDragging(true, true);
            if (y < (-AndroidUtilities.m1036dp(10.0f))) {
                this.changingWeight = true;
                setWeight(Math.max(0.0f, Math.min(1.0f, ((-y) - AndroidUtilities.m1036dp(10.0f)) / AndroidUtilities.m1036dp(190.0f))));
            }
            return true;
        }
        return false;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    @SuppressLint({"DrawAllocation"})
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i3 - i;
        int i6 = i4 - i2;
        this.gradientPaint.setShader(new LinearGradient(AndroidUtilities.m1036dp(56.0f), 0.0f, i5 - AndroidUtilities.m1036dp(56.0f), 0.0f, COLORS, LOCATIONS, Shader.TileMode.REPEAT));
        this.rectF.set(AndroidUtilities.m1036dp(56.0f), i6 - AndroidUtilities.m1036dp(32.0f), i5 - AndroidUtilities.m1036dp(56.0f), r9 + AndroidUtilities.m1036dp(12.0f));
        ImageView imageView = this.settingsButton;
        imageView.layout(i5 - imageView.getMeasuredWidth(), i6 - AndroidUtilities.m1036dp(52.0f), i5, i6);
        this.undoButton.layout(0, i6 - AndroidUtilities.m1036dp(52.0f), this.settingsButton.getMeasuredWidth(), i6);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        canvas.drawRoundRect(this.rectF, AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), this.gradientPaint);
        RectF rectF = this.rectF;
        int iWidth = (int) (rectF.left + (rectF.width() * this.location));
        int iCenterY = (int) ((this.rectF.centerY() + (this.draggingFactor * (-AndroidUtilities.m1036dp(70.0f)))) - (this.changingWeight ? this.weight * AndroidUtilities.m1036dp(190.0f) : 0.0f));
        AndroidUtilities.m1036dp(24.0f);
        float fFloor = (((int) Math.floor(AndroidUtilities.m1036dp(4.0f) + ((AndroidUtilities.m1036dp(19.0f) - AndroidUtilities.m1036dp(4.0f)) * this.weight))) * (this.draggingFactor + 1.0f)) / 2.0f;
        float f = iWidth;
        float f2 = iCenterY;
        canvas.drawCircle(f, f2, ((AndroidUtilities.m1036dp(22.0f) / 2) * (this.draggingFactor + 1.0f)) + 0.5f, this.swatchStrokePaint);
        canvas.drawCircle(f, f2, (AndroidUtilities.m1036dp(22.0f) / 2) * (this.draggingFactor + 1.0f), this.backgroundPaint);
        canvas.drawCircle(f, f2, fFloor, this.swatchPaint);
        canvas.drawCircle(f, f2, fFloor - AndroidUtilities.m1036dp(0.5f), this.swatchStrokePaint);
    }

    @Keep
    private void setDraggingFactor(float f) {
        this.draggingFactor = f;
        invalidate();
    }

    @Keep
    public float getDraggingFactor() {
        return this.draggingFactor;
    }

    private void setDragging(boolean z, boolean z2) {
        if (this.dragging == z) {
            return;
        }
        this.dragging = z;
        float f = z ? 1.0f : 0.0f;
        if (z2) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "draggingFactor", this.draggingFactor, f);
            objectAnimatorOfFloat.setInterpolator(this.interpolator);
            objectAnimatorOfFloat.setDuration(this.wasChangingWeight ? (int) (300.0f + (this.weight * 75.0f)) : 300);
            objectAnimatorOfFloat.start();
            return;
        }
        setDraggingFactor(f);
    }
}
