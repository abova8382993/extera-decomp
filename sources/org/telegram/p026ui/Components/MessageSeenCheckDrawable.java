package org.telegram.p026ui.Components;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p026ui.ActionBar.Theme;
import org.telegram.p026ui.Cells.DialogCell;

/* JADX INFO: loaded from: classes5.dex */
public class MessageSeenCheckDrawable {
    private int colorKey;
    private Drawable drawable;

    /* JADX INFO: renamed from: h */
    private int f1929h;
    private int lastColor;
    private float lastDensity;
    private CharSequence lastSpanned;

    /* JADX INFO: renamed from: oy */
    private float f1930oy;
    private int resId;

    /* JADX INFO: renamed from: w */
    private int f1931w;

    public MessageSeenCheckDrawable(int i, int i2) {
        this.f1931w = -1;
        this.f1929h = -1;
        this.f1930oy = 4.66f;
        this.resId = i;
        this.colorKey = i2;
    }

    public MessageSeenCheckDrawable(int i, int i2, int i3, int i4, float f) {
        this(i, i2);
        this.f1931w = i3;
        this.f1929h = i4;
        this.f1930oy = f;
    }

    public CharSequence getSpanned(Context context, Theme.ResourcesProvider resourcesProvider) {
        if (this.lastSpanned != null && this.drawable != null && AndroidUtilities.density == this.lastDensity) {
            if (this.lastColor != Theme.getColor(this.colorKey, resourcesProvider)) {
                Drawable drawable = this.drawable;
                int color = Theme.getColor(this.colorKey, resourcesProvider);
                this.lastColor = color;
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            }
            return this.lastSpanned;
        }
        if (context == null) {
            return null;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("v ");
        this.lastDensity = AndroidUtilities.density;
        Drawable drawableMutate = context.getResources().getDrawable(this.resId).mutate();
        this.drawable = drawableMutate;
        int color2 = Theme.getColor(this.colorKey, resourcesProvider);
        this.lastColor = color2;
        drawableMutate.setColorFilter(new PorterDuffColorFilter(color2, PorterDuff.Mode.SRC_IN));
        int i = this.f1931w;
        int intrinsicWidth = i <= 0 ? this.drawable.getIntrinsicWidth() : AndroidUtilities.m1081dp(i);
        int i2 = this.f1929h;
        int intrinsicHeight = i2 <= 0 ? this.drawable.getIntrinsicHeight() : AndroidUtilities.m1081dp(i2);
        int iM1081dp = AndroidUtilities.m1081dp(this.f1930oy);
        this.drawable.setBounds(0, iM1081dp, intrinsicWidth, intrinsicHeight + iM1081dp);
        spannableStringBuilder.setSpan(new ImageSpan(this.drawable, 2), 0, 1, 33);
        spannableStringBuilder.setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.m1081dp(2.0f)), 1, 2, 33);
        this.lastSpanned = spannableStringBuilder;
        return spannableStringBuilder;
    }
}
