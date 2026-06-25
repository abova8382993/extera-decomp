package com.exteragram.messenger.preferences.appearance.components;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.preferences.components.CustomPreferenceCell;
import com.exteragram.messenger.preferences.components.PreviewBackgroundDrawable;
import com.exteragram.messenger.preferences.components.PreviewColors;
import java.util.Arrays;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.Easings;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.ScaleStateListAnimator;
import org.telegram.tgnet.TLObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class FabShapeCell extends LinearLayout implements CustomPreferenceCell {
    private final FabShape[] fabShape;

    public abstract void rebuildFragments();

    public FabShapeCell(Context context) {
        super(context);
        this.fabShape = new FabShape[2];
        setWillNotDraw(false);
        setOrientation(0);
        setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        setPadding(AndroidUtilities.m1036dp(13.0f), AndroidUtilities.m1036dp(15.0f), AndroidUtilities.m1036dp(13.0f), AndroidUtilities.m1036dp(21.0f));
        for (int i = 0; i < 2; i++) {
            final boolean z = true;
            if (i != 1) {
                z = false;
            }
            this.fabShape[i] = new FabShape(context, z);
            ScaleStateListAnimator.apply(this.fabShape[i], 0.03f, 1.5f);
            addView(this.fabShape[i], LayoutHelper.createLinear(-1, -1, 0.5f, 8, 0, 8, 0));
            this.fabShape[i].setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.appearance.components.FabShapeCell$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$0(z, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(boolean z, View view) {
        for (int i = 0; i < 2; i++) {
            FabShape fabShape = this.fabShape[i];
            fabShape.setSelected(view == fabShape, true);
        }
        ExteraConfig.setSquareFab(z);
        rebuildFragments();
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        for (int i = 0; i < 2; i++) {
            this.fabShape[i].invalidate();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0.0f, getMeasuredHeight() - 1, getMeasuredWidth(), getMeasuredHeight() - 1, Theme.dividerPaint);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(110.0f), TLObject.FLAG_30));
    }

    @Override // com.exteragram.messenger.preferences.components.CustomPreferenceCell
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FabShapeCell) {
            return Arrays.equals(this.fabShape, ((FabShapeCell) obj).fabShape);
        }
        return false;
    }

    public static class FabShape extends FrameLayout {
        private final PreviewBackgroundDrawable backgroundDrawable;
        private float progress;
        private final RectF rect;
        private final boolean squareFab;

        public FabShape(Context context, boolean z) {
            super(context);
            this.rect = new RectF();
            PreviewBackgroundDrawable previewBackgroundDrawable = new PreviewBackgroundDrawable(12.0f);
            this.backgroundDrawable = previewBackgroundDrawable;
            setWillNotDraw(false);
            this.squareFab = z;
            setBackground(previewBackgroundDrawable);
            setSelected((z && ExteraConfig.getSquareFab()) || !(z || ExteraConfig.getSquareFab()), false);
        }

        @Override // android.view.View
        @SuppressLint({"DrawAllocation"})
        public void onDraw(Canvas canvas) {
            int iM1036dp = AndroidUtilities.m1036dp(22.0f);
            int iM1036dp2 = AndroidUtilities.m1036dp(21.0f);
            int i = iM1036dp / 2;
            int i2 = 0;
            while (i2 < 2) {
                int iM1036dp3 = iM1036dp2 + AndroidUtilities.m1036dp(i2 == 0 ? 0.0f : 32.0f);
                Theme.dialogs_onlineCirclePaint.setColor(PreviewColors.getMockColor(false));
                float f = i * 2;
                canvas.drawRoundRect(iM1036dp - i, iM1036dp3 - i, iM1036dp + i, iM1036dp3 + i, ExteraConfig.getAvatarCorners(f, true), ExteraConfig.getAvatarCorners(f, true), Theme.dialogs_onlineCirclePaint);
                int i3 = 0;
                while (i3 < 2) {
                    Theme.dialogs_onlineCirclePaint.setColor(PreviewColors.getMockColor(i3 == 0));
                    int i4 = i3 * 10;
                    this.rect.set(AndroidUtilities.m1036dp(41.0f), iM1036dp3 - AndroidUtilities.m1036dp(7 - i4), getMeasuredWidth() - AndroidUtilities.m1036dp(i3 == 0 ? 70.0f : 55.0f), iM1036dp3 - AndroidUtilities.m1036dp(3 - i4));
                    canvas.drawRoundRect(this.rect, AndroidUtilities.m1036dp(2.0f), AndroidUtilities.m1036dp(2.0f), Theme.dialogs_onlineCirclePaint);
                    i3++;
                }
                i2++;
                iM1036dp2 = iM1036dp3;
            }
            Theme.dialogs_onlineCirclePaint.setColor(Theme.getColor(Theme.key_featuredStickers_addButton));
            this.rect.set(getMeasuredWidth() - AndroidUtilities.m1036dp(42.0f), getMeasuredHeight() - AndroidUtilities.m1036dp(12.0f), getMeasuredWidth() - AndroidUtilities.m1036dp(12.0f), getMeasuredHeight() - AndroidUtilities.m1036dp(42.0f));
            canvas.drawRoundRect(this.rect, AndroidUtilities.m1036dp(this.squareFab ? 9.0f : 100.0f), AndroidUtilities.m1036dp(this.squareFab ? 9.0f : 100.0f), Theme.dialogs_onlineCirclePaint);
            Drawable drawable = ContextCompat.getDrawable(getContext(), C2797R.drawable.filled_fab_compose_32);
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_actionIcon), PorterDuff.Mode.SRC_IN));
                drawable.setBounds(getMeasuredWidth() - AndroidUtilities.m1036dp(37.0f), getMeasuredHeight() - AndroidUtilities.m1036dp(37.0f), getMeasuredWidth() - AndroidUtilities.m1036dp(17.0f), getMeasuredHeight() - AndroidUtilities.m1036dp(17.0f));
                drawable.draw(canvas);
            }
        }

        private void setProgress(float f) {
            this.progress = f;
            this.backgroundDrawable.setSelectionProgress(f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSelected(boolean z, boolean z2) {
            float f = z ? 1.0f : 0.0f;
            float f2 = this.progress;
            if (f == f2 && z2) {
                return;
            }
            if (z2) {
                ValueAnimator duration = ValueAnimator.ofFloat(f2, f).setDuration(250L);
                duration.setInterpolator(Easings.easeInOutQuad);
                duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.appearance.components.FabShapeCell$FabShape$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$setSelected$0(valueAnimator);
                    }
                });
                duration.start();
                return;
            }
            setProgress(f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSelected$0(ValueAnimator valueAnimator) {
            setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }
    }
}
