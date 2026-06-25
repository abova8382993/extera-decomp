package org.telegram.p035ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import androidx.core.graphics.ColorUtils;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.SharedConfig;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.NumberPicker;
import org.telegram.tgnet.TLObject;

/* JADX INFO: loaded from: classes7.dex */
public class SwipeGestureSettingsView extends FrameLayout {
    int[] backgroundKeys;
    float colorProgress;
    int currentColorKey;
    int currentIconIndex;
    int currentIconValue;
    Paint filledPaint;
    int fromColor;
    boolean hasTabs;
    RLottieImageView[] iconViews;
    RLottieDrawable[] icons;
    Paint linePaint;
    Paint outlinePaint;
    private NumberPicker picker;
    Paint pickerDividersPaint;
    float progressToSwipeFolders;
    RectF rect;
    String[] strings;
    Runnable swapIconRunnable;

    public SwipeGestureSettingsView(Context context, int i) {
        super(context);
        this.outlinePaint = new Paint(1);
        this.filledPaint = new Paint(1);
        this.linePaint = new Paint(1);
        this.pickerDividersPaint = new Paint(1);
        this.rect = new RectF();
        String[] strArr = new String[6];
        this.strings = strArr;
        this.backgroundKeys = new int[6];
        this.icons = new RLottieDrawable[6];
        this.iconViews = new RLottieImageView[2];
        this.colorProgress = 1.0f;
        strArr[0] = LocaleController.getString(C2797R.string.SwipeSettingsPin);
        this.strings[1] = LocaleController.getString(C2797R.string.SwipeSettingsRead);
        this.strings[2] = LocaleController.getString(C2797R.string.SwipeSettingsArchive);
        this.strings[3] = LocaleController.getString(C2797R.string.SwipeSettingsMute);
        this.strings[4] = LocaleController.getString(C2797R.string.SwipeSettingsDelete);
        this.strings[5] = LocaleController.getString(C2797R.string.SwipeSettingsFolders);
        int[] iArr = this.backgroundKeys;
        int i2 = Theme.key_chats_archiveBackground;
        iArr[0] = i2;
        iArr[1] = i2;
        iArr[2] = i2;
        iArr[3] = i2;
        iArr[4] = Theme.key_dialogSwipeRemove;
        iArr[5] = Theme.key_chats_archivePinBackground;
        Paint paint = this.outlinePaint;
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        this.outlinePaint.setStrokeWidth(AndroidUtilities.m1036dp(1.0f));
        this.linePaint.setStyle(style);
        Paint paint2 = this.linePaint;
        Paint.Cap cap = Paint.Cap.ROUND;
        paint2.setStrokeCap(cap);
        this.linePaint.setStrokeWidth(AndroidUtilities.m1036dp(5.0f));
        this.pickerDividersPaint.setStyle(style);
        this.pickerDividersPaint.setStrokeCap(cap);
        this.pickerDividersPaint.setStrokeWidth(AndroidUtilities.m1036dp(2.0f));
        NumberPicker numberPicker = new NumberPicker(context, 13) { // from class: org.telegram.ui.Components.SwipeGestureSettingsView.1
            @Override // org.telegram.p035ui.Components.NumberPicker, android.widget.LinearLayout, android.view.View
            public void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                float fM1036dp = AndroidUtilities.m1036dp(31.0f);
                SwipeGestureSettingsView.this.pickerDividersPaint.setColor(Theme.getColor(Theme.key_radioBackgroundChecked));
                canvas.drawLine(AndroidUtilities.m1036dp(2.0f), fM1036dp, getMeasuredWidth() - AndroidUtilities.m1036dp(2.0f), fM1036dp, SwipeGestureSettingsView.this.pickerDividersPaint);
                float measuredHeight = getMeasuredHeight() - AndroidUtilities.m1036dp(31.0f);
                canvas.drawLine(AndroidUtilities.m1036dp(2.0f), measuredHeight, getMeasuredWidth() - AndroidUtilities.m1036dp(2.0f), measuredHeight, SwipeGestureSettingsView.this.pickerDividersPaint);
            }
        };
        this.picker = numberPicker;
        numberPicker.setMinValue(0);
        this.picker.setDrawDividers(false);
        boolean zIsEmpty = MessagesController.getInstance(i).dialogFilters.isEmpty();
        this.hasTabs = !zIsEmpty;
        NumberPicker numberPicker2 = this.picker;
        String[] strArr2 = this.strings;
        numberPicker2.setMaxValue(!zIsEmpty ? strArr2.length - 1 : strArr2.length - 2);
        NumberPicker numberPicker3 = this.picker;
        boolean z = this.hasTabs;
        String[] strArr3 = this.strings;
        numberPicker3.setAllItemsCount(z ? strArr3.length : strArr3.length - 1);
        this.picker.setWrapSelectorWheel(true);
        this.picker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.SwipeGestureSettingsView$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i3) {
                return this.f$0.lambda$new$0(i3);
            }
        });
        this.picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.SwipeGestureSettingsView$$ExternalSyntheticLambda1
            @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker4, int i3, int i4) {
                this.f$0.lambda$new$1(numberPicker4, i3, i4);
            }
        });
        this.picker.setImportantForAccessibility(2);
        this.picker.setValue(SharedConfig.getChatSwipeAction(i));
        addView(this.picker, LayoutHelper.createFrame(132, -1.0f, 5, 21.0f, 0.0f, 21.0f, 0.0f));
        setWillNotDraw(false);
        this.currentIconIndex = 0;
        for (int i3 = 0; i3 < 2; i3++) {
            this.iconViews[i3] = new RLottieImageView(context);
            addView(this.iconViews[i3], LayoutHelper.createFrame(28, 28.0f, 21, 0.0f, 0.0f, 184.0f, 0.0f));
        }
        RLottieDrawable icon = getIcon(this.picker.getValue());
        if (icon != null) {
            this.iconViews[0].setImageDrawable(icon);
            icon.setCurrentFrame(icon.getFramesCount() - 1);
        }
        AndroidUtilities.updateViewVisibilityAnimated(this.iconViews[0], true, 0.5f, false);
        AndroidUtilities.updateViewVisibilityAnimated(this.iconViews[1], false, 0.5f, false);
        this.progressToSwipeFolders = this.picker.getValue() != 5 ? 0.0f : 1.0f;
        this.currentIconValue = this.picker.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$new$0(int i) {
        return this.strings[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(NumberPicker numberPicker, int i, int i2) {
        swapIcons();
        SharedConfig.updateChatListSwipeSetting(i2);
        invalidate();
        try {
            numberPicker.performHapticFeedback(3, 2);
        } catch (Exception unused) {
        }
    }

    private void swapIcons() {
        int value;
        if (this.swapIconRunnable == null && this.currentIconValue != (value = this.picker.getValue())) {
            this.currentIconValue = value;
            int i = (this.currentIconIndex + 1) % 2;
            RLottieDrawable icon = getIcon(value);
            RLottieImageView[] rLottieImageViewArr = this.iconViews;
            if (icon != null) {
                if (rLottieImageViewArr[i].getVisibility() != 0) {
                    icon.setCurrentFrame(0, false);
                }
                this.iconViews[i].setAnimation(icon);
                this.iconViews[i].playAnimation();
            } else {
                rLottieImageViewArr[i].clearAnimationDrawable();
            }
            AndroidUtilities.updateViewVisibilityAnimated(this.iconViews[this.currentIconIndex], false, 0.5f, true);
            AndroidUtilities.updateViewVisibilityAnimated(this.iconViews[i], true, 0.5f, true);
            this.currentIconIndex = i;
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.SwipeGestureSettingsView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$swapIcons$2();
                }
            };
            this.swapIconRunnable = runnable;
            AndroidUtilities.runOnUIThread(runnable, 150L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$swapIcons$2() {
        this.swapIconRunnable = null;
        swapIcons();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(102.0f), TLObject.FLAG_30));
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onDraw(android.graphics.Canvas r15) {
        /*
            Method dump skipped, instruction units count: 569
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.SwipeGestureSettingsView.onDraw(android.graphics.Canvas):void");
    }

    public RLottieDrawable getIcon(int i) {
        int i2;
        RLottieDrawable[] rLottieDrawableArr = this.icons;
        if (rLottieDrawableArr[i] == null) {
            if (i == 1) {
                i2 = C2797R.raw.swipe_read;
            } else if (i == 2) {
                i2 = C2797R.raw.chats_archive;
            } else if (i == 3) {
                i2 = C2797R.raw.swipe_mute;
            } else if (i == 4) {
                i2 = C2797R.raw.swipe_delete;
            } else if (i != 5) {
                i2 = C2797R.raw.swipe_pin;
            } else {
                i2 = C2797R.raw.swipe_disabled;
            }
            int i3 = i2;
            rLottieDrawableArr[i] = new RLottieDrawable(i3, _UrlKt.FRAGMENT_ENCODE_SET + i3, AndroidUtilities.m1036dp(28.0f), AndroidUtilities.m1036dp(28.0f), true, null);
            updateIconColor(i);
        }
        return this.icons[i];
    }

    public void updateIconColor(int i) {
        if (this.icons[i] != null) {
            int iBlendARGB = ColorUtils.blendARGB(Theme.getColor(Theme.key_windowBackgroundWhite), Theme.getColor(Theme.key_chats_archiveBackground), 0.9f);
            int color = Theme.getColor(Theme.key_chats_archiveIcon);
            RLottieDrawable[] rLottieDrawableArr = this.icons;
            if (i == 2) {
                rLottieDrawableArr[i].setLayerColor("Arrow.**", iBlendARGB);
                this.icons[i].setLayerColor("Box2.**", color);
                this.icons[i].setLayerColor("Box1.**", color);
                return;
            }
            rLottieDrawableArr[i].setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
        }
    }

    public void updateColors() {
        for (int i = 0; i < this.icons.length; i++) {
            updateIconColor(i);
        }
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        super.setBackgroundColor(i);
        updateColors();
        this.picker.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.picker.invalidate();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setEnabled(true);
        accessibilityNodeInfo.setContentDescription(this.strings[this.picker.getValue()]);
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, null));
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (accessibilityEvent.getEventType() == 1) {
            int value = this.picker.getValue() + 1;
            if (value > this.picker.getMaxValue() || value < 0) {
                value = 0;
            }
            setContentDescription(this.strings[value]);
            this.picker.changeValueByOne(true);
        }
    }
}
