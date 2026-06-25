package org.telegram.p035ui.Components;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;

/* JADX INFO: loaded from: classes7.dex */
public class ShareLocationDrawable extends Drawable {
    private int currentType;
    private Drawable drawable;
    private Drawable drawableLeft;
    private Drawable drawableRight;
    private long lastUpdateTime = 0;
    private float[] progress = {0.0f, -0.5f};

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    public ShareLocationDrawable(Context context, int i) {
        this.currentType = i;
        if (i == 4) {
            this.drawable = context.getResources().getDrawable(C2797R.drawable.filled_extend_location).mutate();
            this.drawableLeft = context.getResources().getDrawable(C2797R.drawable.smallanimationpinleft).mutate();
            this.drawableRight = context.getResources().getDrawable(C2797R.drawable.smallanimationpinright).mutate();
        } else if (i == 5) {
            this.drawable = context.getResources().getDrawable(C2797R.drawable.filled_stop_location).mutate();
            this.drawableLeft = context.getResources().getDrawable(C2797R.drawable.smallanimationpinleft).mutate();
            this.drawableRight = context.getResources().getDrawable(C2797R.drawable.smallanimationpinright).mutate();
        } else if (i == 1) {
            this.drawable = context.getResources().getDrawable(C2797R.drawable.smallanimationpin).mutate();
            this.drawableLeft = context.getResources().getDrawable(C2797R.drawable.smallanimationpinleft).mutate();
            this.drawableRight = context.getResources().getDrawable(C2797R.drawable.smallanimationpinright).mutate();
        } else {
            this.drawable = context.getResources().getDrawable(C2797R.drawable.animationpin).mutate();
            this.drawableLeft = context.getResources().getDrawable(C2797R.drawable.animationpinleft).mutate();
            this.drawableRight = context.getResources().getDrawable(C2797R.drawable.animationpinright).mutate();
        }
    }

    private void update() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = jCurrentTimeMillis - this.lastUpdateTime;
        this.lastUpdateTime = jCurrentTimeMillis;
        if (j > 16) {
            j = 16;
        }
        for (int i = 0; i < 2; i++) {
            float[] fArr = this.progress;
            if (fArr[i] >= 1.0f) {
                fArr[i] = 0.0f;
            }
            float f = fArr[i] + (j / 1300.0f);
            fArr[i] = f;
            if (f > 1.0f) {
                fArr[i] = 1.0f;
            }
        }
        invalidateSelf();
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0184  */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void draw(android.graphics.Canvas r24) {
        /*
            Method dump skipped, instruction units count: 451
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.ShareLocationDrawable.draw(android.graphics.Canvas):void");
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.drawable.setColorFilter(colorFilter);
        this.drawableLeft.setColorFilter(colorFilter);
        this.drawableRight.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        int i = this.currentType;
        if (i == 4 || i == 5) {
            return AndroidUtilities.m1036dp(42.0f);
        }
        if (i == 3) {
            return AndroidUtilities.m1036dp(100.0f);
        }
        if (i == 2) {
            return AndroidUtilities.m1036dp(74.0f);
        }
        if (i == 1) {
            return AndroidUtilities.m1036dp(40.0f);
        }
        return AndroidUtilities.m1036dp(120.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        int i = this.currentType;
        if (i == 4 || i == 5) {
            return AndroidUtilities.m1036dp(42.0f);
        }
        if (i == 3) {
            return AndroidUtilities.m1036dp(100.0f);
        }
        if (i == 2) {
            return AndroidUtilities.m1036dp(74.0f);
        }
        if (i == 1) {
            return AndroidUtilities.m1036dp(40.0f);
        }
        return AndroidUtilities.m1036dp(180.0f);
    }
}
