package org.telegram.p026ui.Components;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes3.dex */
public class MessageBackgroundDrawable extends Drawable {
    private boolean animationInProgress;
    private float currentAnimationProgress;
    private float finalRadius;
    private boolean isSelected;
    private long lastAnimationTime;
    private long lastTouchTime;
    private View parentView;
    private Paint paint = new Paint(1);
    private Paint customPaint = null;
    private float touchX = -1.0f;
    private float touchY = -1.0f;
    private float touchOverrideX = -1.0f;
    private float touchOverrideY = -1.0f;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    public MessageBackgroundDrawable(View view) {
        this.parentView = view;
    }

    public void setColor(int i) {
        this.paint.setColor(i);
    }

    public void setCustomPaint(Paint paint) {
        this.customPaint = paint;
    }

    public void setSelected(boolean z, boolean z2) {
        if (this.isSelected == z) {
            if (this.animationInProgress == z2 || z2) {
                return;
            }
            this.currentAnimationProgress = z ? 1.0f : 0.0f;
            this.animationInProgress = false;
            return;
        }
        this.isSelected = z;
        this.animationInProgress = z2;
        if (z2) {
            this.lastAnimationTime = SystemClock.elapsedRealtime();
        } else {
            this.currentAnimationProgress = z ? 1.0f : 0.0f;
        }
        calcRadius();
        invalidate();
    }

    private void invalidate() {
        View view = this.parentView;
        if (view != null) {
            view.invalidate();
            if (this.parentView.getParent() != null) {
                ((ViewGroup) this.parentView.getParent()).invalidate();
            }
        }
    }

    private void calcRadius() {
        Rect bounds = getBounds();
        float fCenterX = bounds.centerX();
        float fCenterY = bounds.centerY();
        int i = bounds.left;
        int i2 = bounds.top;
        this.finalRadius = (float) Math.ceil(Math.sqrt(((i - fCenterX) * (i - fCenterX)) + ((i2 - fCenterY) * (i2 - fCenterY))));
    }

    public void setTouchCoords(float f, float f2) {
        this.touchX = f;
        this.touchY = f2;
        this.lastTouchTime = SystemClock.elapsedRealtime();
    }

    public void setTouchCoordsOverride(float f, float f2) {
        this.touchOverrideX = f;
        this.touchOverrideY = f2;
    }

    public float getTouchX() {
        return this.touchX;
    }

    public float getTouchY() {
        return this.touchY;
    }

    public long getLastTouchTime() {
        return this.lastTouchTime;
    }

    public boolean isAnimationInProgress() {
        return this.animationInProgress;
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        calcRadius();
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(Rect rect) {
        super.setBounds(rect);
        calcRadius();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.paint.setAlpha(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0059  */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void draw(android.graphics.Canvas r11) {
        /*
            r10 = this;
            float r0 = r10.currentAnimationProgress
            r1 = 1065353216(0x3f800000, float:1.0)
            int r2 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            r3 = 0
            if (r2 != 0) goto L18
            android.graphics.Rect r0 = r10.getBounds()
            android.graphics.Paint r2 = r10.customPaint
            if (r2 == 0) goto L12
            goto L14
        L12:
            android.graphics.Paint r2 = r10.paint
        L14:
            r11.drawRect(r0, r2)
            goto L70
        L18:
            int r2 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r2 == 0) goto L70
            boolean r2 = r10.isSelected
            if (r2 == 0) goto L27
            org.telegram.ui.Components.CubicBezierInterpolator r2 = org.telegram.p026ui.Components.CubicBezierInterpolator.EASE_OUT_QUINT
            float r0 = r2.getInterpolation(r0)
            goto L31
        L27:
            org.telegram.ui.Components.CubicBezierInterpolator r2 = org.telegram.p026ui.Components.CubicBezierInterpolator.EASE_OUT_QUINT
            float r0 = r1 - r0
            float r0 = r2.getInterpolation(r0)
            float r0 = r1 - r0
        L31:
            android.graphics.Rect r2 = r10.getBounds()
            int r4 = r2.centerX()
            float r4 = (float) r4
            int r2 = r2.centerY()
            float r2 = (float) r2
            float r5 = r10.touchOverrideX
            int r6 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r6 < 0) goto L4c
            float r6 = r10.touchOverrideY
            int r7 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r7 < 0) goto L4c
            goto L5b
        L4c:
            float r5 = r10.touchX
            int r6 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r6 < 0) goto L59
            float r6 = r10.touchY
            int r7 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r7 < 0) goto L59
            goto L5b
        L59:
            r6 = r2
            r5 = r4
        L5b:
            float r7 = r1 - r0
            float r5 = r5 - r4
            float r5 = r5 * r7
            float r4 = r4 + r5
            float r6 = r6 - r2
            float r7 = r7 * r6
            float r2 = r2 + r7
            float r5 = r10.finalRadius
            float r5 = r5 * r0
            android.graphics.Paint r0 = r10.customPaint
            if (r0 == 0) goto L6b
            goto L6d
        L6b:
            android.graphics.Paint r0 = r10.paint
        L6d:
            r11.drawCircle(r4, r2, r5, r0)
        L70:
            boolean r11 = r10.animationInProgress
            if (r11 == 0) goto Lb7
            long r4 = android.os.SystemClock.elapsedRealtime()
            long r6 = r10.lastAnimationTime
            long r6 = r4 - r6
            r8 = 20
            int r11 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r11 <= 0) goto L84
            r6 = 17
        L84:
            r10.lastAnimationTime = r4
            boolean r11 = r10.isSelected
            r0 = 1131413504(0x43700000, float:240.0)
            if (r11 == 0) goto L9a
            float r11 = r10.currentAnimationProgress
            float r2 = (float) r6
            float r2 = r2 / r0
            float r11 = r11 + r2
            r10.currentAnimationProgress = r11
            int r11 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            if (r11 < 0) goto Lb4
            r10.currentAnimationProgress = r1
            goto La7
        L9a:
            float r11 = r10.currentAnimationProgress
            float r1 = (float) r6
            float r1 = r1 / r0
            float r11 = r11 - r1
            r10.currentAnimationProgress = r11
            int r11 = (r11 > r3 ? 1 : (r11 == r3 ? 0 : -1))
            if (r11 > 0) goto Lb4
            r10.currentAnimationProgress = r3
        La7:
            r11 = -1082130432(0xffffffffbf800000, float:-1.0)
            r10.touchX = r11
            r10.touchY = r11
            r10.touchOverrideX = r11
            r10.touchOverrideY = r11
            r11 = 0
            r10.animationInProgress = r11
        Lb4:
            r10.invalidate()
        Lb7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.Components.MessageBackgroundDrawable.draw(android.graphics.Canvas):void");
    }
}
