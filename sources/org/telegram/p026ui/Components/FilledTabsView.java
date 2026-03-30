package org.telegram.p026ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import com.sun.jna.Function;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Utilities;

/* JADX INFO: loaded from: classes5.dex */
public class FilledTabsView extends View {
    private final Paint backgroundPaint;
    private RectF[] bounds;
    private int lastPressedIndex;
    private Utilities.Callback onTabClick;
    private final Paint selectedPaint;
    private float selectedTabIndex;
    private Text[] tabs;

    public FilledTabsView(Context context) {
        super(context);
        this.backgroundPaint = new Paint(1);
        Paint paint = new Paint(1);
        this.selectedPaint = paint;
        this.lastPressedIndex = -1;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        paint.setColor(-1);
    }

    public void setTabs(CharSequence... charSequenceArr) {
        this.tabs = new Text[charSequenceArr.length];
        this.bounds = new RectF[charSequenceArr.length];
        for (int i = 0; i < charSequenceArr.length; i++) {
            this.tabs[i] = new Text(charSequenceArr[i], 14.0f, AndroidUtilities.bold());
            this.bounds[i] = new RectF();
        }
        invalidate();
    }

    public void setSelected(float f) {
        if (Math.abs(f - this.selectedTabIndex) > 0.001f) {
            invalidate();
        }
        this.selectedTabIndex = f;
    }

    public FilledTabsView onTabSelected(Utilities.Callback callback) {
        this.onTabClick = callback;
        return this;
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.backgroundPaint.setColor(i);
        invalidate();
    }

    @Override // android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.tabs == null) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        int iM1081dp = AndroidUtilities.m1081dp(2.0f) + (this.tabs.length * AndroidUtilities.m1081dp(24.0f)) + AndroidUtilities.m1081dp(2.0f);
        int i = 0;
        while (true) {
            Text[] textArr = this.tabs;
            if (i >= textArr.length) {
                break;
            }
            iM1081dp = (int) (iM1081dp + textArr[i].getWidth());
            i++;
        }
        float fM1081dp = (height - AndroidUtilities.m1081dp(30.0f)) / 2.0f;
        float fM1081dp2 = (AndroidUtilities.m1081dp(30.0f) + height) / 2.0f;
        float f = (width - iM1081dp) / 2.0f;
        RectF rectF = AndroidUtilities.rectTmp;
        rectF.set(f, fM1081dp, iM1081dp + f, fM1081dp2);
        canvas.drawRoundRect(rectF, AndroidUtilities.m1081dp(15.0f), AndroidUtilities.m1081dp(15.0f), this.backgroundPaint);
        canvas.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), Function.USE_VARARGS, 31);
        float fM1081dp3 = f + AndroidUtilities.m1081dp(14.0f);
        int i2 = 0;
        while (true) {
            Text[] textArr2 = this.tabs;
            if (i2 < textArr2.length) {
                textArr2[i2].draw(canvas, fM1081dp3, height / 2.0f, -1, 1.0f);
                this.bounds[i2].set(fM1081dp3 - AndroidUtilities.m1081dp(14.0f), fM1081dp, this.tabs[i2].getWidth() + fM1081dp3 + AndroidUtilities.m1081dp(14.0f), fM1081dp2);
                fM1081dp3 += this.tabs[i2].getWidth() + AndroidUtilities.m1081dp(24.0f);
                i2++;
            } else {
                AndroidUtilities.m1081dp(2.0f);
                float fM1081dp4 = (height - AndroidUtilities.m1081dp(26.0f)) / 2.0f;
                float fM1081dp5 = (height + AndroidUtilities.m1081dp(26.0f)) / 2.0f;
                int iClamp = Utilities.clamp((int) Math.floor(this.selectedTabIndex), this.tabs.length - 1, 0);
                int iClamp2 = Utilities.clamp((int) Math.ceil(this.selectedTabIndex), this.tabs.length - 1, 0);
                float fM1081dp6 = this.bounds[iClamp].left + AndroidUtilities.m1081dp(2.0f);
                float fM1081dp7 = this.bounds[iClamp2].left + AndroidUtilities.m1081dp(2.0f);
                float f2 = this.selectedTabIndex;
                float fLerp = AndroidUtilities.lerp(fM1081dp6, fM1081dp7, (float) (((double) f2) - Math.floor(f2)));
                float fM1081dp8 = this.bounds[iClamp].right - AndroidUtilities.m1081dp(2.0f);
                float fM1081dp9 = this.bounds[iClamp2].right - AndroidUtilities.m1081dp(2.0f);
                float f3 = this.selectedTabIndex;
                float fLerp2 = AndroidUtilities.lerp(fM1081dp8, fM1081dp9, (float) (((double) f3) - Math.floor(f3)));
                RectF rectF2 = AndroidUtilities.rectTmp;
                rectF2.set(fLerp, fM1081dp4, fLerp2, fM1081dp5);
                canvas.drawRoundRect(rectF2, AndroidUtilities.m1081dp(15.0f), AndroidUtilities.m1081dp(15.0f), this.selectedPaint);
                canvas.restore();
                return;
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i = 0;
        if (this.tabs == null || this.bounds == null) {
            return false;
        }
        while (true) {
            RectF[] rectFArr = this.bounds;
            if (i >= rectFArr.length) {
                i = -1;
                break;
            }
            if (rectFArr[i].contains(motionEvent.getX(), motionEvent.getY())) {
                break;
            }
            i++;
        }
        if (i >= 0 && i != this.lastPressedIndex) {
            this.lastPressedIndex = i;
            Utilities.Callback callback = this.onTabClick;
            if (callback != null) {
                callback.run(Integer.valueOf(i));
            }
        }
        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            this.lastPressedIndex = -1;
        }
        if (motionEvent.getAction() != 0 || i < 0) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }
}
