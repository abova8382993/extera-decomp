package org.telegram.p035ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import androidx.core.graphics.ColorUtils;
import androidx.core.math.MathUtils;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.utils.p020ui.MaterialSliderUiHelper;
import com.google.android.material.slider.Slider;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.tgnet.TLObject;

/* JADX INFO: loaded from: classes7.dex */
public class SlideChooseView extends FrameLayout {
    private final SeekBarAccessibilityDelegate accessibilityDelegate;
    private boolean allowSlide;
    private Callback callback;
    private int circleSize;
    private final View contentView;
    private int dashedFrom;
    private int gapSize;
    private int lastDash;
    private Drawable[] leftDrawables;
    private Paint linePaint;
    private int lineSize;
    private Slider materialSlider;
    private int minIndex;
    private boolean moving;
    private AnimatedFloat movingAnimatedHolder;
    private boolean needDivider;
    private int[] optionsSizes;
    private String[] optionsStr;
    private Paint paint;
    private final Theme.ResourcesProvider resourcesProvider;
    private int selectedIndex;
    private AnimatedFloat selectedIndexAnimatedHolder;
    private float selectedIndexTouch;
    private int sideSide;
    private boolean startMoving;
    private int startMovingPreset;
    private TextPaint textPaint;
    private boolean touchWasClose;
    private float xTouchDown;
    private float yTouchDown;

    public interface Callback {
        void onOptionSelected(int i);

        default void onTouchEnd() {
        }
    }

    public SlideChooseView(Context context) {
        this(context, null);
    }

    public SlideChooseView(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.dashedFrom = -1;
        this.needDivider = false;
        this.minIndex = Integer.MIN_VALUE;
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.DEFAULT;
        this.selectedIndexAnimatedHolder = new AnimatedFloat(this, 120L, cubicBezierInterpolator);
        this.movingAnimatedHolder = new AnimatedFloat(this, 150L, cubicBezierInterpolator);
        this.touchWasClose = false;
        this.allowSlide = true;
        this.resourcesProvider = resourcesProvider;
        this.paint = new Paint(1);
        this.textPaint = new TextPaint(1);
        Paint paint = new Paint(1);
        this.linePaint = paint;
        paint.setStrokeWidth(AndroidUtilities.m1036dp(2.0f));
        this.linePaint.setStrokeCap(Paint.Cap.ROUND);
        this.textPaint.setTextSize(AndroidUtilities.m1036dp(13.0f));
        this.accessibilityDelegate = new IntSeekBarAccessibilityDelegate() { // from class: org.telegram.ui.Components.SlideChooseView.1
            @Override // org.telegram.p035ui.Components.IntSeekBarAccessibilityDelegate
            public int getProgress() {
                return SlideChooseView.this.selectedIndex;
            }

            @Override // org.telegram.p035ui.Components.IntSeekBarAccessibilityDelegate
            public void setProgress(int i) {
                SlideChooseView.this.setOption(i);
            }

            @Override // org.telegram.p035ui.Components.IntSeekBarAccessibilityDelegate
            public int getMaxValue() {
                return SlideChooseView.this.optionsStr.length - 1;
            }

            @Override // org.telegram.p035ui.Components.SeekBarAccessibilityDelegate
            public CharSequence getContentDescription(View view) {
                if (SlideChooseView.this.selectedIndex < SlideChooseView.this.optionsStr.length) {
                    return SlideChooseView.this.optionsStr[SlideChooseView.this.selectedIndex];
                }
                return null;
            }
        };
        View view = new View(context) { // from class: org.telegram.ui.Components.SlideChooseView.2
            @Override // android.view.View
            public void onDraw(Canvas canvas) {
                SlideChooseView.this.drawContent(canvas);
            }
        };
        this.contentView = view;
        view.setImportantForAccessibility(2);
        addView(view, LayoutHelper.createFrame(-1, -1.0f));
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setOptions(int i, String... strArr) {
        setOptions(i, null, strArr);
    }

    public void setOptions(int i, Drawable[] drawableArr, String... strArr) {
        this.optionsStr = strArr;
        this.leftDrawables = drawableArr;
        if (strArr.length <= 0) {
            i = 0;
        }
        this.selectedIndex = i;
        this.optionsSizes = new int[strArr.length];
        int i2 = 0;
        while (true) {
            if (i2 >= this.optionsStr.length) {
                break;
            }
            this.optionsSizes[i2] = (int) Math.ceil(this.textPaint.measureText(r6[i2]));
            i2++;
        }
        Drawable[] drawableArr2 = this.leftDrawables;
        if (drawableArr2 != null) {
            for (Drawable drawable : drawableArr2) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            }
        }
        updateMaterialSliderState();
        requestLayout();
        invalidate();
    }

    public void setMinAllowedIndex(int i) {
        String[] strArr;
        if (i != -1 && (strArr = this.optionsStr) != null) {
            i = Math.min(i, strArr.length - 1);
        }
        if (this.minIndex != i) {
            this.minIndex = i;
            if (this.selectedIndex < i) {
                this.selectedIndex = i;
            }
            updateMaterialSliderState();
            invalidate();
        }
    }

    public void setDashedFrom(int i) {
        this.dashedFrom = i;
        updateMaterialSliderState();
        invalidate();
    }

    public void setNeedDivider(boolean z) {
        this.needDivider = z;
        invalidate();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        updateMaterialSliderState();
        return isUsingMaterialSlider() || super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        updateMaterialSliderState();
        if (!this.allowSlide) {
            return true;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        float fClamp = MathUtils.clamp(((x - this.sideSide) + (this.circleSize / 2.0f)) / ((this.lineSize + (this.gapSize * 2)) + r4), 0.0f, this.optionsStr.length - 1);
        boolean z = Math.abs(fClamp - ((float) Math.round(fClamp))) < 0.35f;
        if (z) {
            fClamp = Math.round(fClamp);
        }
        int i = this.minIndex;
        if (i != Integer.MIN_VALUE) {
            fClamp = Math.max(fClamp, i);
        }
        if (motionEvent.getAction() == 0) {
            this.xTouchDown = x;
            this.yTouchDown = y;
            this.selectedIndexTouch = fClamp;
            this.startMovingPreset = this.selectedIndex;
            this.startMoving = true;
            invalidate();
        } else if (motionEvent.getAction() == 2) {
            if (!this.moving && Math.abs(this.xTouchDown - x) > Math.abs(this.yTouchDown - y)) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            if (this.startMoving && Math.abs(this.xTouchDown - x) >= AndroidUtilities.touchSlop) {
                this.moving = true;
                this.startMoving = false;
            }
            if (this.moving) {
                this.selectedIndexTouch = fClamp;
                invalidate();
                if (Math.round(this.selectedIndexTouch) != this.selectedIndex && z) {
                    setOption(Math.round(this.selectedIndexTouch));
                }
            }
            invalidate();
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            if (!this.moving) {
                this.selectedIndexTouch = fClamp;
                if (motionEvent.getAction() == 1 && Math.round(this.selectedIndexTouch) != this.selectedIndex) {
                    setOption(Math.round(this.selectedIndexTouch));
                }
            } else {
                int i2 = this.selectedIndex;
                if (i2 != this.startMovingPreset) {
                    setOption(i2);
                }
            }
            Callback callback = this.callback;
            if (callback != null) {
                callback.onTouchEnd();
            }
            this.startMoving = false;
            this.moving = false;
            invalidate();
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOption(int i) {
        String[] strArr = this.optionsStr;
        if (strArr == null || strArr.length == 0) {
            return;
        }
        if (this.selectedIndex != i) {
            AndroidUtilities.vibrateCursor(this);
        }
        this.selectedIndex = i;
        Callback callback = this.callback;
        if (callback != null) {
            callback.onOptionSelected(i);
        }
        invalidate();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        updateMaterialSliderState();
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(74.0f), TLObject.FLAG_30));
        this.circleSize = AndroidUtilities.m1036dp(6.0f);
        this.gapSize = AndroidUtilities.m1036dp(2.0f);
        this.sideSide = AndroidUtilities.m1036dp(22.0f);
        int measuredWidth = getMeasuredWidth();
        int i3 = this.circleSize;
        String[] strArr = this.optionsStr;
        this.lineSize = (((measuredWidth - (i3 * strArr.length)) - ((this.gapSize * 2) * (strArr.length - 1))) - (this.sideSide * 2)) / Math.max(1, strArr.length - 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:34:0x018c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawContent(android.graphics.Canvas r27) {
        /*
            Method dump skipped, instruction units count: 831
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.SlideChooseView.drawContent(android.graphics.Canvas):void");
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        this.accessibilityDelegate.onInitializeAccessibilityNodeInfoInternal(this, accessibilityNodeInfo);
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        return super.performAccessibilityAction(i, bundle) || this.accessibilityDelegate.performAccessibilityActionInternal(this, i, bundle);
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    private int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    private void initMaterialSlider(Context context) {
        Slider sliderCreate = MaterialSliderUiHelper.create(context);
        this.materialSlider = sliderCreate;
        sliderCreate.setImportantForAccessibility(2);
        this.materialSlider.setFocusable(false);
        this.materialSlider.setFocusableInTouchMode(false);
        this.materialSlider.setLayoutDirection(0);
        this.materialSlider.setVisibility(8);
        addView(this.materialSlider, 0, LayoutHelper.createFrame(-1, 48.0f, 48, 6.0f, 24.0f, 6.0f, 0.0f));
    }

    private boolean canUseMaterialSlider() {
        String[] strArr;
        return ExteraConfig.getNewSliderStyle() && (strArr = this.optionsStr) != null && strArr.length > 1 && this.dashedFrom < 0 && this.minIndex <= 0;
    }

    private boolean isUsingMaterialSlider() {
        Slider slider = this.materialSlider;
        return slider != null && slider.getVisibility() == 0;
    }

    private void updateMaterialSliderState() {
        boolean zCanUseMaterialSlider = canUseMaterialSlider();
        if (zCanUseMaterialSlider && this.materialSlider == null) {
            initMaterialSlider(getContext());
        }
        Slider slider = this.materialSlider;
        if (slider == null) {
            return;
        }
        boolean z = false;
        int i = zCanUseMaterialSlider ? 0 : 8;
        if (slider.getVisibility() != i) {
            this.materialSlider.setVisibility(i);
            View view = this.contentView;
            if (view != null) {
                view.invalidate();
            }
        }
        if (zCanUseMaterialSlider) {
            if (isEnabled() && this.allowSlide) {
                z = true;
            }
            if (this.materialSlider.isEnabled() != z) {
                this.materialSlider.setEnabled(z);
            }
            float length = this.optionsStr.length - 1;
            if (this.materialSlider.getValue() > length) {
                updateMaterialSliderValue(length);
            }
            if (this.materialSlider.getValueFrom() != 0.0f) {
                this.materialSlider.setValueFrom(0.0f);
            }
            if (this.materialSlider.getValueTo() != length) {
                this.materialSlider.setValueTo(length);
            }
            if (this.materialSlider.getStepSize() != 0.0f) {
                this.materialSlider.setStepSize(0.0f);
            }
            MaterialSliderUiHelper.applyDiscreteStyle(this.materialSlider, this.optionsStr.length);
            updateMaterialSliderColors();
        }
    }

    private void updateMaterialSliderColors() {
        int i = this.allowSlide ? 255 : 128;
        MaterialSliderUiHelper.applyDiscreteColors(this.materialSlider, ColorUtils.setAlphaComponent(getThemedColor(Theme.key_switchTrackChecked), i), ColorUtils.setAlphaComponent(getThemedColor(Theme.key_switchTrack), i), ColorUtils.setAlphaComponent(getThemedColor(Theme.key_windowBackgroundWhite), i));
    }

    private void updateMaterialSliderValue(float f) {
        String[] strArr;
        if (this.materialSlider == null || (strArr = this.optionsStr) == null || strArr.length < 2) {
            return;
        }
        float fClamp = MathUtils.clamp(f, 0.0f, strArr.length - 1);
        if (Math.abs(this.materialSlider.getValue() - fClamp) > 1.0E-4f) {
            this.materialSlider.setValue(fClamp);
        }
    }

    public void setAllowSlide(boolean z) {
        this.allowSlide = z;
        Slider slider = this.materialSlider;
        if (slider != null) {
            slider.setEnabled(isEnabled() && z);
            updateMaterialSliderColors();
        }
        invalidate();
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        Slider slider = this.materialSlider;
        if (slider != null) {
            slider.setEnabled(z && this.allowSlide);
        }
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        View view = this.contentView;
        if (view != null) {
            view.invalidate();
        }
    }
}
