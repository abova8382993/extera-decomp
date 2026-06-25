package org.telegram.p035ui.Components;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Cells.DialogCell;

/* JADX INFO: loaded from: classes7.dex */
public class MessageSeenCheckDrawable {
    private int colorKey;
    private Drawable drawable;

    /* JADX INFO: renamed from: h */
    private int f1582h;
    private int lastColor;
    private float lastDensity;
    private CharSequence lastSpanned;

    /* JADX INFO: renamed from: oy */
    private float f1583oy;
    private int resId;

    /* JADX INFO: renamed from: w */
    private int f1584w;

    public MessageSeenCheckDrawable(int i, int i2) {
        this.f1584w = -1;
        this.f1582h = -1;
        this.f1583oy = 4.66f;
        this.resId = i;
        this.colorKey = i2;
    }

    public MessageSeenCheckDrawable(int i, int i2, int i3, int i4, float f) {
        this(i, i2);
        this.f1584w = i3;
        this.f1582h = i4;
        this.f1583oy = f;
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
        int i = this.f1584w;
        int intrinsicWidth = i <= 0 ? this.drawable.getIntrinsicWidth() : AndroidUtilities.m1036dp(i);
        int i2 = this.f1582h;
        int intrinsicHeight = i2 <= 0 ? this.drawable.getIntrinsicHeight() : AndroidUtilities.m1036dp(i2);
        int iM1036dp = AndroidUtilities.m1036dp(this.f1583oy);
        this.drawable.setBounds(0, iM1036dp, intrinsicWidth, intrinsicHeight + iM1036dp);
        spannableStringBuilder.setSpan(new ImageSpan(this.drawable, 2), 0, 1, 33);
        spannableStringBuilder.setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.m1036dp(2.0f)), 1, 2, 33);
        this.lastSpanned = spannableStringBuilder;
        return spannableStringBuilder;
    }
}
