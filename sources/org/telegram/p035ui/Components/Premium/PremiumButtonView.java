package org.telegram.p035ui.Components.Premium;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.core.graphics.ColorUtils;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BuildVars;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.BadWayToMakeButtonRound;
import org.telegram.p035ui.Components.AnimatedFloat;
import org.telegram.p035ui.Components.AnimatedTextView;
import org.telegram.p035ui.Components.CircularProgressDrawable;
import org.telegram.p035ui.Components.CounterView;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.Loadable;
import org.telegram.p035ui.Components.RLottieImageView;
import org.telegram.p035ui.Components.ScaleStateListAnimator;
import org.telegram.p035ui.Components.voip.CellFlickerDrawable;

/* JADX INFO: loaded from: classes7.dex */
public class PremiumButtonView extends FrameLayout implements Loadable {
    public FrameLayout buttonLayout;
    public AnimatedTextView buttonTextView;
    AnimatedFloat counterOffset;
    AnimatedFloat counterOffset2;
    CounterView counterView;
    public boolean drawGradient;
    private boolean drawOverlayColor;
    CellFlickerDrawable flickerDrawable;
    RLottieImageView iconView;
    private boolean inc;
    private boolean isButtonTextSet;
    private boolean isFlickerDisabled;
    private boolean loading;
    private ValueAnimator loadingAnimator;
    private CircularProgressDrawable loadingDrawable;
    private float loadingT;
    private boolean nonClickable;
    ValueAnimator overlayAnimator;
    private float overlayProgress;
    public AnimatedTextView overlayTextView;
    private Paint paintOverlayPaint;
    Path path;
    private float progress;
    private int radius;
    private boolean showOverlay;

    public PremiumButtonView(Context context, boolean z, Theme.ResourcesProvider resourcesProvider) {
        this(context, AndroidUtilities.m1036dp(8.0f), z, resourcesProvider);
    }

    public PremiumButtonView(Context context, int i, boolean z, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.paintOverlayPaint = new Paint(1);
        this.path = new Path();
        this.drawGradient = true;
        this.counterOffset = new AnimatedFloat(this);
        this.counterOffset2 = new AnimatedFloat(this);
        this.loadingT = 0.0f;
        this.radius = i;
        CellFlickerDrawable cellFlickerDrawable = new CellFlickerDrawable();
        this.flickerDrawable = cellFlickerDrawable;
        cellFlickerDrawable.animationSpeedScale = 1.2f;
        cellFlickerDrawable.drawFrame = false;
        cellFlickerDrawable.repeatProgress = 4.0f;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(0);
        C47331 c47331 = new AnimatedTextView(context, true, true, true) { // from class: org.telegram.ui.Components.Premium.PremiumButtonView.1
            public C47331(Context context2, boolean z2, boolean z3, boolean z4) {
                super(context2, z2, z3, z4);
            }

            @Override // org.telegram.p035ui.Components.AnimatedTextView, android.view.View
            public void onDraw(Canvas canvas) {
                if (PremiumButtonView.this.loadingT > 0.0f) {
                    if (PremiumButtonView.this.loadingDrawable == null) {
                        PremiumButtonView.this.loadingDrawable = new CircularProgressDrawable(PremiumButtonView.this.buttonTextView.getTextColor());
                    }
                    int iM1036dp = (int) ((1.0f - PremiumButtonView.this.loadingT) * AndroidUtilities.m1036dp(24.0f));
                    PremiumButtonView.this.loadingDrawable.setBounds(0, iM1036dp, getWidth(), getHeight() + iM1036dp);
                    PremiumButtonView.this.loadingDrawable.setAlpha((int) (PremiumButtonView.this.loadingT * 255.0f));
                    PremiumButtonView.this.loadingDrawable.draw(canvas);
                    invalidate();
                }
                if (PremiumButtonView.this.loadingT < 1.0f) {
                    if (PremiumButtonView.this.loadingT != 0.0f) {
                        canvas.save();
                        canvas.translate(0.0f, (int) (PremiumButtonView.this.loadingT * AndroidUtilities.m1036dp(-24.0f)));
                        canvas.scale(1.0f, 1.0f - (PremiumButtonView.this.loadingT * 0.4f));
                        super.onDraw(canvas);
                        canvas.restore();
                        return;
                    }
                    super.onDraw(canvas);
                }
            }
        };
        this.buttonTextView = c47331;
        c47331.setAnimationProperties(0.35f, 0L, 350L, CubicBezierInterpolator.EASE_OUT_QUINT);
        this.buttonTextView.setGravity(17);
        AnimatedTextView animatedTextView = this.buttonTextView;
        int i2 = Theme.key_featuredStickers_buttonText;
        animatedTextView.setTextColor(Theme.getColor(i2));
        this.buttonTextView.setTextSize(AndroidUtilities.m1036dp(14.0f));
        this.buttonTextView.setTypeface(AndroidUtilities.bold());
        RLottieImageView rLottieImageView = new RLottieImageView(context2);
        this.iconView = rLottieImageView;
        rLottieImageView.setColorFilter(Theme.getColor(i2));
        this.iconView.setVisibility(8);
        C47342 c47342 = new FrameLayout(context2) { // from class: org.telegram.ui.Components.Premium.PremiumButtonView.2
            public C47342(Context context2) {
                super(context2);
            }

            @Override // android.view.View
            public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
                AnimatedTextView animatedTextView2;
                AnimatedTextView animatedTextView3;
                super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
                accessibilityNodeInfo.setClassName("android.widget.Button");
                CharSequence text = (!PremiumButtonView.this.showOverlay || (animatedTextView3 = PremiumButtonView.this.overlayTextView) == null) ? null : animatedTextView3.getText();
                if (text == null && (animatedTextView2 = PremiumButtonView.this.buttonTextView) != null) {
                    text = animatedTextView2.getText();
                }
                if (text != null) {
                    accessibilityNodeInfo.setText(text);
                    if (getContentDescription() == null) {
                        accessibilityNodeInfo.setContentDescription(text);
                    }
                }
            }
        };
        this.buttonLayout = c47342;
        c47342.addView(linearLayout, LayoutHelper.createFrame(-2, -2, 17));
        this.buttonLayout.setBackground(Theme.createSimpleSelectorRoundRectDrawable(i, 0, ColorUtils.setAlphaComponent(Theme.getColor(i2), 120)));
        linearLayout.addView(this.buttonTextView, LayoutHelper.createLinear(-2, -2, 16));
        linearLayout.addView(this.iconView, LayoutHelper.createLinear(24, 24, 0.0f, 16, 4, 0, 0, 0));
        addView(this.buttonLayout);
        BadWayToMakeButtonRound.round(this);
        ScaleStateListAnimator.apply(this, 0.02f, 1.2f);
        if (z) {
            C47353 c47353 = new AnimatedTextView(context2, true, true, true) { // from class: org.telegram.ui.Components.Premium.PremiumButtonView.3
                public C47353(Context context2, boolean z2, boolean z3, boolean z4) {
                    super(context2, z2, z3, z4);
                }

                @Override // org.telegram.p035ui.Components.AnimatedTextView, android.view.View
                public void onDraw(Canvas canvas) {
                    if (PremiumButtonView.this.loadingT > 0.0f) {
                        if (PremiumButtonView.this.loadingDrawable == null) {
                            PremiumButtonView.this.loadingDrawable = new CircularProgressDrawable(PremiumButtonView.this.buttonTextView.getTextColor());
                        }
                        int iM1036dp = (int) ((1.0f - PremiumButtonView.this.loadingT) * AndroidUtilities.m1036dp(24.0f));
                        PremiumButtonView.this.loadingDrawable.setBounds(0, iM1036dp, getWidth(), getHeight() + iM1036dp);
                        PremiumButtonView.this.loadingDrawable.setAlpha((int) (PremiumButtonView.this.loadingT * 255.0f));
                        PremiumButtonView.this.loadingDrawable.draw(canvas);
                        invalidate();
                    }
                    if (PremiumButtonView.this.loadingT < 1.0f) {
                        if (PremiumButtonView.this.loadingT != 0.0f) {
                            canvas.save();
                            canvas.translate(0.0f, (int) (PremiumButtonView.this.loadingT * AndroidUtilities.m1036dp(-24.0f)));
                            canvas.scale(1.0f, 1.0f - (PremiumButtonView.this.loadingT * 0.4f));
                            super.onDraw(canvas);
                            canvas.restore();
                            return;
                        }
                        super.onDraw(canvas);
                    }
                }
            };
            this.overlayTextView = c47353;
            c47353.setPadding(AndroidUtilities.m1036dp(34.0f), 0, AndroidUtilities.m1036dp(34.0f), 0);
            this.overlayTextView.setGravity(17);
            this.overlayTextView.setTextColor(Theme.getColor(i2, resourcesProvider));
            this.overlayTextView.setTextSize(AndroidUtilities.m1036dp(14.0f));
            this.overlayTextView.setTypeface(AndroidUtilities.bold());
            this.overlayTextView.getDrawable().setAllowCancel(true);
            this.overlayTextView.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(8.0f), 0, ColorUtils.setAlphaComponent(Theme.getColor(i2), 120)));
            addView(this.overlayTextView);
            this.paintOverlayPaint.setColor(Theme.getColor(Theme.key_featuredStickers_addButton, resourcesProvider));
            updateOverlayProgress();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Premium.PremiumButtonView$1 */
    public class C47331 extends AnimatedTextView {
        public C47331(Context context2, boolean z2, boolean z3, boolean z4) {
            super(context2, z2, z3, z4);
        }

        @Override // org.telegram.p035ui.Components.AnimatedTextView, android.view.View
        public void onDraw(Canvas canvas) {
            if (PremiumButtonView.this.loadingT > 0.0f) {
                if (PremiumButtonView.this.loadingDrawable == null) {
                    PremiumButtonView.this.loadingDrawable = new CircularProgressDrawable(PremiumButtonView.this.buttonTextView.getTextColor());
                }
                int iM1036dp = (int) ((1.0f - PremiumButtonView.this.loadingT) * AndroidUtilities.m1036dp(24.0f));
                PremiumButtonView.this.loadingDrawable.setBounds(0, iM1036dp, getWidth(), getHeight() + iM1036dp);
                PremiumButtonView.this.loadingDrawable.setAlpha((int) (PremiumButtonView.this.loadingT * 255.0f));
                PremiumButtonView.this.loadingDrawable.draw(canvas);
                invalidate();
            }
            if (PremiumButtonView.this.loadingT < 1.0f) {
                if (PremiumButtonView.this.loadingT != 0.0f) {
                    canvas.save();
                    canvas.translate(0.0f, (int) (PremiumButtonView.this.loadingT * AndroidUtilities.m1036dp(-24.0f)));
                    canvas.scale(1.0f, 1.0f - (PremiumButtonView.this.loadingT * 0.4f));
                    super.onDraw(canvas);
                    canvas.restore();
                    return;
                }
                super.onDraw(canvas);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Premium.PremiumButtonView$2 */
    public class C47342 extends FrameLayout {
        public C47342(Context context2) {
            super(context2);
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            AnimatedTextView animatedTextView2;
            AnimatedTextView animatedTextView3;
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName("android.widget.Button");
            CharSequence text = (!PremiumButtonView.this.showOverlay || (animatedTextView3 = PremiumButtonView.this.overlayTextView) == null) ? null : animatedTextView3.getText();
            if (text == null && (animatedTextView2 = PremiumButtonView.this.buttonTextView) != null) {
                text = animatedTextView2.getText();
            }
            if (text != null) {
                accessibilityNodeInfo.setText(text);
                if (getContentDescription() == null) {
                    accessibilityNodeInfo.setContentDescription(text);
                }
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Premium.PremiumButtonView$3 */
    public class C47353 extends AnimatedTextView {
        public C47353(Context context2, boolean z2, boolean z3, boolean z4) {
            super(context2, z2, z3, z4);
        }

        @Override // org.telegram.p035ui.Components.AnimatedTextView, android.view.View
        public void onDraw(Canvas canvas) {
            if (PremiumButtonView.this.loadingT > 0.0f) {
                if (PremiumButtonView.this.loadingDrawable == null) {
                    PremiumButtonView.this.loadingDrawable = new CircularProgressDrawable(PremiumButtonView.this.buttonTextView.getTextColor());
                }
                int iM1036dp = (int) ((1.0f - PremiumButtonView.this.loadingT) * AndroidUtilities.m1036dp(24.0f));
                PremiumButtonView.this.loadingDrawable.setBounds(0, iM1036dp, getWidth(), getHeight() + iM1036dp);
                PremiumButtonView.this.loadingDrawable.setAlpha((int) (PremiumButtonView.this.loadingT * 255.0f));
                PremiumButtonView.this.loadingDrawable.draw(canvas);
                invalidate();
            }
            if (PremiumButtonView.this.loadingT < 1.0f) {
                if (PremiumButtonView.this.loadingT != 0.0f) {
                    canvas.save();
                    canvas.translate(0.0f, (int) (PremiumButtonView.this.loadingT * AndroidUtilities.m1036dp(-24.0f)));
                    canvas.scale(1.0f, 1.0f - (PremiumButtonView.this.loadingT * 0.4f));
                    super.onDraw(canvas);
                    canvas.restore();
                    return;
                }
                super.onDraw(canvas);
            }
        }
    }

    public void setNonClickable() {
        this.nonClickable = true;
        setClickable(false);
        this.buttonLayout.setClickable(false);
        setStateListAnimator(null);
    }

    public boolean isShowOverlay() {
        return this.showOverlay;
    }

    public RLottieImageView getIconView() {
        return this.iconView;
    }

    public AnimatedTextView getTextView() {
        return this.buttonTextView;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // org.telegram.p035ui.Components.Loadable
    public void setLoading(boolean z) {
        if (this.loading != z) {
            ValueAnimator valueAnimator = this.loadingAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.loadingAnimator = null;
            }
            float f = this.loadingT;
            this.loading = z;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, z ? 1.0f : 0.0f);
            this.loadingAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.Premium.PremiumButtonView$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    this.f$0.lambda$setLoading$0(valueAnimator2);
                }
            });
            this.loadingAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.Premium.PremiumButtonView.4
                final /* synthetic */ boolean val$loading;

                public C47364(boolean z2) {
                    z = z2;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    PremiumButtonView.this.loadingT = z ? 1.0f : 0.0f;
                    PremiumButtonView.this.buttonTextView.invalidate();
                    AnimatedTextView animatedTextView = PremiumButtonView.this.overlayTextView;
                    if (animatedTextView != null) {
                        animatedTextView.invalidate();
                    }
                }
            });
            this.loadingAnimator.setDuration(320L);
            this.loadingAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
            this.loadingAnimator.start();
        }
    }

    public /* synthetic */ void lambda$setLoading$0(ValueAnimator valueAnimator) {
        this.loadingT = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.buttonTextView.invalidate();
        AnimatedTextView animatedTextView = this.overlayTextView;
        if (animatedTextView != null) {
            animatedTextView.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Premium.PremiumButtonView$4 */
    public class C47364 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$loading;

        public C47364(boolean z2) {
            z = z2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            PremiumButtonView.this.loadingT = z ? 1.0f : 0.0f;
            PremiumButtonView.this.buttonTextView.invalidate();
            AnimatedTextView animatedTextView = PremiumButtonView.this.overlayTextView;
            if (animatedTextView != null) {
                animatedTextView.invalidate();
            }
        }
    }

    @Override // org.telegram.p035ui.Components.Loadable
    public boolean isLoading() {
        return this.loading;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        if (this.counterView != null) {
            this.counterOffset.set(((r0.counterDrawable.getWidth() * 0.85f) + AndroidUtilities.m1036dp(3.0f)) / 2.0f);
            this.counterOffset2.set((getMeasuredWidth() / 2.0f) + (this.overlayTextView.getDrawable().getWidth() / 2.0f) + AndroidUtilities.m1036dp(3.0f));
            this.overlayTextView.setTranslationX(-this.counterOffset.get());
            this.counterView.setTranslationX(this.counterOffset2.get() - this.counterOffset.get());
        } else {
            AnimatedTextView animatedTextView = this.overlayTextView;
            if (animatedTextView != null) {
                animatedTextView.setTranslationX(0.0f);
            }
        }
        RectF rectF = AndroidUtilities.rectTmp;
        rectF.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
        if (this.overlayProgress != 1.0f || !this.drawOverlayColor) {
            boolean z = this.inc;
            float f = this.progress;
            if (z) {
                float f2 = f + 0.016f;
                this.progress = f2;
                if (f2 > 3.0f) {
                    this.inc = false;
                }
            } else {
                float f3 = f - 0.016f;
                this.progress = f3;
                if (f3 < 1.0f) {
                    this.inc = true;
                }
            }
            if (this.drawGradient) {
                PremiumGradient.getInstance().updateMainGradientMatrix(0, 0, getMeasuredWidth(), getMeasuredHeight(), (-getMeasuredWidth()) * 0.1f * this.progress, 0.0f);
                int i = this.radius;
                canvas.drawRoundRect(rectF, i, i, PremiumGradient.getInstance().getMainGradientPaint());
            } else {
                this.paintOverlayPaint.setAlpha(255);
                int i2 = this.radius;
                canvas.drawRoundRect(rectF, i2, i2, this.paintOverlayPaint);
            }
            invalidate();
        }
        if (!BuildVars.IS_BILLING_UNAVAILABLE && !this.isFlickerDisabled) {
            this.flickerDrawable.setParentWidth(getMeasuredWidth());
            this.flickerDrawable.draw(canvas, rectF, this.radius, null);
        }
        float f4 = this.overlayProgress;
        if (f4 != 0.0f && this.drawOverlayColor) {
            this.paintOverlayPaint.setAlpha((int) (f4 * 255.0f));
            if (this.overlayProgress != 1.0f) {
                this.path.rewind();
                this.path.addCircle(getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f, Math.max(getMeasuredWidth(), getMeasuredHeight()) * 1.4f * this.overlayProgress, Path.Direction.CW);
                canvas.save();
                canvas.clipPath(this.path);
                int i3 = this.radius;
                canvas.drawRoundRect(rectF, i3, i3, this.paintOverlayPaint);
                canvas.restore();
            } else {
                int i4 = this.radius;
                canvas.drawRoundRect(rectF, i4, i4, this.paintOverlayPaint);
            }
        }
        super.dispatchDraw(canvas);
    }

    public void setOverlayText(CharSequence charSequence, boolean z, boolean z2) {
        this.showOverlay = true;
        this.drawOverlayColor = z;
        this.overlayTextView.setText(charSequence, z2);
        this.overlayTextView.setContentDescription(charSequence);
        updateOverlay(z2);
    }

    private void updateOverlay(boolean z) {
        ValueAnimator valueAnimator = this.overlayAnimator;
        if (valueAnimator != null) {
            valueAnimator.removeAllListeners();
            this.overlayAnimator.cancel();
        }
        if (!z) {
            this.overlayProgress = this.showOverlay ? 1.0f : 0.0f;
            updateOverlayProgress();
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.overlayProgress, this.showOverlay ? 1.0f : 0.0f);
        this.overlayAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.Premium.PremiumButtonView.5
            public C47375() {
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                PremiumButtonView.this.overlayProgress = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                PremiumButtonView.this.updateOverlayProgress();
            }
        });
        this.overlayAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.Premium.PremiumButtonView.6
            public C47386() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PremiumButtonView premiumButtonView = PremiumButtonView.this;
                premiumButtonView.overlayProgress = premiumButtonView.showOverlay ? 1.0f : 0.0f;
                PremiumButtonView.this.updateOverlayProgress();
            }
        });
        this.overlayAnimator.setDuration(250L);
        this.overlayAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
        this.overlayAnimator.start();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Premium.PremiumButtonView$5 */
    public class C47375 implements ValueAnimator.AnimatorUpdateListener {
        public C47375() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator2) {
            PremiumButtonView.this.overlayProgress = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
            PremiumButtonView.this.updateOverlayProgress();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Premium.PremiumButtonView$6 */
    public class C47386 extends AnimatorListenerAdapter {
        public C47386() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            PremiumButtonView premiumButtonView = PremiumButtonView.this;
            premiumButtonView.overlayProgress = premiumButtonView.showOverlay ? 1.0f : 0.0f;
            PremiumButtonView.this.updateOverlayProgress();
        }
    }

    public void updateOverlayProgress() {
        this.overlayTextView.setAlpha(this.overlayProgress);
        this.overlayTextView.setTranslationY(AndroidUtilities.m1036dp(12.0f) * (1.0f - this.overlayProgress));
        this.buttonLayout.setAlpha(1.0f - this.overlayProgress);
        this.buttonLayout.setTranslationY((-AndroidUtilities.m1036dp(12.0f)) * this.overlayProgress);
        this.buttonLayout.setVisibility(this.overlayProgress == 1.0f ? 4 : 0);
        this.overlayTextView.setVisibility(this.overlayProgress == 0.0f ? 4 : 0);
        invalidate();
    }

    public void clearOverlayText() {
        this.showOverlay = false;
        updateOverlay(true);
    }

    public void setIcon(int i) {
        this.iconView.setAnimation(i, 24, 24);
        CellFlickerDrawable cellFlickerDrawable = this.flickerDrawable;
        cellFlickerDrawable.progress = 2.0f;
        cellFlickerDrawable.setOnRestartCallback(new Runnable() { // from class: org.telegram.ui.Components.Premium.PremiumButtonView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setIcon$1();
            }
        });
        invalidate();
        this.iconView.setVisibility(0);
    }

    public /* synthetic */ void lambda$setIcon$1() {
        this.iconView.getAnimatedDrawable().setCurrentFrame(0, true);
        this.iconView.playAnimation();
    }

    public void hideIcon() {
        this.flickerDrawable.setOnRestartCallback(null);
        this.iconView.setVisibility(8);
    }

    public void setFlickerDisabled(boolean z) {
        this.isFlickerDisabled = z;
        invalidate();
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.buttonLayout.setEnabled(z);
    }

    @Override // android.view.View
    public boolean isEnabled() {
        return this.buttonLayout.isEnabled();
    }

    public void setButton(String str, View.OnClickListener onClickListener) {
        setButton(str, onClickListener, false);
    }

    public void setButton(String str, View.OnClickListener onClickListener, boolean z) {
        if (!this.isButtonTextSet && z) {
            z = true;
        }
        this.isButtonTextSet = true;
        if (z && this.buttonTextView.isAnimating()) {
            this.buttonTextView.cancelAnimation();
        }
        this.buttonTextView.setText(str, z);
        this.buttonLayout.setContentDescription(str);
        if (this.nonClickable) {
            return;
        }
        this.buttonLayout.setOnClickListener(onClickListener);
    }

    public void checkCounterView() {
        if (this.counterView == null) {
            CounterView counterView = new CounterView(getContext(), null);
            this.counterView = counterView;
            counterView.setGravity(3);
            this.counterView.setColors(Theme.key_featuredStickers_addButton, Theme.key_featuredStickers_buttonText);
            this.counterView.counterDrawable.circleScale = 0.8f;
            setClipChildren(false);
            addView(this.counterView, LayoutHelper.createFrame(-1, 24, 16));
        }
    }
}
