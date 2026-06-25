package org.telegram.p035ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.widget.FrameLayout;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.blur3.drawable.BlurredBackgroundDrawable;

/* JADX INFO: loaded from: classes3.dex */
public class SearchTabsAndFiltersLayout extends FrameLayout implements Theme.Colorable {
    private BlurredBackgroundDrawable blurredBackgroundDrawable;
    private final Path clipPath;

    public SearchTabsAndFiltersLayout(Context context) {
        super(context);
        this.clipPath = new Path();
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.clipPath.rewind();
        this.clipPath.addRoundRect(AndroidUtilities.m1036dp(9.0f), AndroidUtilities.m1036dp(9.0f), i - AndroidUtilities.m1036dp(9.0f), i2 - AndroidUtilities.m1036dp(9.0f), AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(16.0f), Path.Direction.CW);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(this.clipPath);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    public void setBlurredBackground(BlurredBackgroundDrawable blurredBackgroundDrawable) {
        this.blurredBackgroundDrawable = blurredBackgroundDrawable;
        setBackground(blurredBackgroundDrawable);
    }

    @Override // org.telegram.ui.ActionBar.Theme.Colorable
    public void updateColors() {
        BlurredBackgroundDrawable blurredBackgroundDrawable = this.blurredBackgroundDrawable;
        if (blurredBackgroundDrawable != null) {
            blurredBackgroundDrawable.updateColors();
        }
    }
}
