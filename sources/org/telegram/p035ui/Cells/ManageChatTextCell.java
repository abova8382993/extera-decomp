package org.telegram.p035ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.p035ui.ActionBar.SimpleTextView;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.tgnet.TLObject;

/* JADX INFO: loaded from: classes6.dex */
public class ManageChatTextCell extends FrameLayout {
    private boolean divider;
    private int dividerColor;
    private ImageView imageView;
    private SimpleTextView textView;
    private SimpleTextView valueTextView;

    public ManageChatTextCell(Context context) {
        super(context);
        this.dividerColor = 0;
        SimpleTextView simpleTextView = new SimpleTextView(context);
        this.textView = simpleTextView;
        simpleTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(16);
        this.textView.setGravity(LocaleController.isRTL ? 5 : 3);
        addView(this.textView);
        SimpleTextView simpleTextView2 = new SimpleTextView(context);
        this.valueTextView = simpleTextView2;
        simpleTextView2.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteValueText));
        this.valueTextView.setTextSize(16);
        this.valueTextView.setGravity(LocaleController.isRTL ? 3 : 5);
        addView(this.valueTextView);
        ImageView imageView = new ImageView(context);
        this.imageView = imageView;
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        this.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayIcon), PorterDuff.Mode.MULTIPLY));
        addView(this.imageView);
    }

    public SimpleTextView getTextView() {
        return this.textView;
    }

    public SimpleTextView getValueTextView() {
        return this.valueTextView;
    }

    public void setDividerColor(int i) {
        this.dividerColor = i;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int iM1036dp = AndroidUtilities.m1036dp(48.0f);
        this.valueTextView.measure(View.MeasureSpec.makeMeasureSpec(size - AndroidUtilities.m1036dp(24.0f), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(20.0f), TLObject.FLAG_30));
        this.textView.measure(View.MeasureSpec.makeMeasureSpec(size - AndroidUtilities.m1036dp(95.0f), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(20.0f), TLObject.FLAG_30));
        this.imageView.measure(View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(iM1036dp, Integer.MIN_VALUE));
        setMeasuredDimension(size, getFullHeight() + (this.divider ? 1 : 0));
    }

    public int getFullHeight() {
        return AndroidUtilities.m1036dp(56.0f);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i4 - i2;
        int i6 = i3 - i;
        int textHeight = (i5 - this.valueTextView.getTextHeight()) / 2;
        int iM1036dp = LocaleController.isRTL ? AndroidUtilities.m1036dp(24.0f) : 0;
        SimpleTextView simpleTextView = this.valueTextView;
        simpleTextView.layout(iM1036dp, textHeight, simpleTextView.getMeasuredWidth() + iM1036dp, this.valueTextView.getMeasuredHeight() + textHeight);
        int textHeight2 = (i5 - this.textView.getTextHeight()) / 2;
        int iM1036dp2 = !LocaleController.isRTL ? AndroidUtilities.m1036dp(71.0f) : AndroidUtilities.m1036dp(24.0f);
        SimpleTextView simpleTextView2 = this.textView;
        simpleTextView2.layout(iM1036dp2, textHeight2, simpleTextView2.getMeasuredWidth() + iM1036dp2, this.textView.getMeasuredHeight() + textHeight2);
        int iM1036dp3 = AndroidUtilities.m1036dp(9.0f);
        int iM1036dp4 = !LocaleController.isRTL ? AndroidUtilities.m1036dp(21.0f) : (i6 - this.imageView.getMeasuredWidth()) - AndroidUtilities.m1036dp(21.0f);
        ImageView imageView = this.imageView;
        imageView.layout(iM1036dp4, iM1036dp3, imageView.getMeasuredWidth() + iM1036dp4, this.imageView.getMeasuredHeight() + iM1036dp3);
    }

    public void setTextColor(int i) {
        this.textView.setTextColor(i);
    }

    public void setColors(int i, int i2) {
        this.textView.setTextColor(Theme.getColor(i2));
        this.textView.setTag(Integer.valueOf(i2));
        this.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i), PorterDuff.Mode.MULTIPLY));
        this.imageView.setTag(Integer.valueOf(i));
    }

    public void setText(String str, String str2, int i, boolean z) {
        setText(str, str2, i, 5, z);
    }

    public void setText(String str, String str2, int i, int i2, boolean z) {
        this.textView.setText(str);
        SimpleTextView simpleTextView = this.valueTextView;
        if (str2 != null) {
            simpleTextView.setText(str2);
            this.valueTextView.setVisibility(0);
        } else {
            simpleTextView.setVisibility(4);
        }
        this.imageView.setPadding(0, AndroidUtilities.m1036dp(i2), 0, 0);
        this.imageView.setImageResource(i);
        this.divider = z;
        setWillNotDraw(!z);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.divider) {
            int i = this.dividerColor;
            if (i != 0) {
                Theme.dividerExtraPaint.setColor(Theme.getColor(i));
            }
            canvas.drawLine(AndroidUtilities.m1036dp(71.0f), getMeasuredHeight() - 1, getMeasuredWidth(), getMeasuredHeight() - 1, this.dividerColor != 0 ? Theme.dividerExtraPaint : Theme.dividerPaint);
        }
    }
}
