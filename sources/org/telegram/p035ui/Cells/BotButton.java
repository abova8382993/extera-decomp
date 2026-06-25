package org.telegram.p035ui.Cells;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.animation.OvershootInterpolator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BotInlineKeyboard;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.Components.AnimatedEmojiDrawable;
import org.telegram.p035ui.Components.LoadingDrawable;
import org.telegram.p035ui.Components.Text;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes6.dex */
class BotButton {
    public int angle;
    public AnimatedEmojiDrawable animatedEmojiDrawable;
    public TLRPC.KeyboardButton button;
    public BotInlineKeyboard.ButtonCustom buttonCustom;
    public BotInlineKeyboard.Button buttonImpl;
    public int height;
    public Drawable iconDrawable;
    public final Runnable invalidateRunnable;
    public boolean isInviteButton;
    public boolean isLocked;
    public boolean isSeparator;
    public boolean isWebAppLink;
    public long lastUpdateTime;
    public LoadingDrawable loadingDrawable;
    public int positionFlags;
    public ValueAnimator pressAnimator;
    public float pressT;
    public boolean pressed;
    public float progressAlpha;
    public Drawable selectorDrawable;
    public Text title;
    public float width;

    /* JADX INFO: renamed from: x */
    public float f1493x;

    /* JADX INFO: renamed from: y */
    public int f1494y;
    private final Path path = new Path();
    private final Paint paint = new Paint(1);
    private final RectF loadingRect = new RectF();
    private final float[] radii = new float[8];

    public BotButton(Runnable runnable) {
        this.invalidateRunnable = runnable;
    }

    /* JADX WARN: Removed duplicated region for block: B:209:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x02cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean draw(android.graphics.Canvas r18, android.graphics.RectF r19, boolean r20, boolean r21, org.telegram.ui.ActionBar.Theme.ResourcesProvider r22) {
        /*
            Method dump skipped, instruction units count: 911
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Cells.BotButton.draw(android.graphics.Canvas, android.graphics.RectF, boolean, boolean, org.telegram.ui.ActionBar.Theme$ResourcesProvider):boolean");
    }

    /* JADX INFO: renamed from: org.telegram.ui.Cells.BotButton$2 */
    public static /* synthetic */ class C32632 {

        /* JADX INFO: renamed from: $SwitchMap$org$telegram$messenger$BotInlineKeyboard$BackgroundColor */
        static final /* synthetic */ int[] f1495x30ccd364;

        static {
            int[] iArr = new int[BotInlineKeyboard.BackgroundColor.values().length];
            f1495x30ccd364 = iArr;
            try {
                iArr[BotInlineKeyboard.BackgroundColor.DANGER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1495x30ccd364[BotInlineKeyboard.BackgroundColor.SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1495x30ccd364[BotInlineKeyboard.BackgroundColor.PRIMARY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void setPressed(boolean z) {
        ValueAnimator valueAnimator;
        if (this.pressed != z) {
            this.pressed = z;
            this.invalidateRunnable.run();
            if (z && (valueAnimator = this.pressAnimator) != null) {
                valueAnimator.removeAllListeners();
                this.pressAnimator.cancel();
            }
            if (z) {
                return;
            }
            float f = this.pressT;
            if (f != 0.0f) {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, 0.0f);
                this.pressAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Cells.BotButton$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        this.f$0.lambda$setPressed$0(valueAnimator2);
                    }
                });
                this.pressAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.BotButton.1
                    public C32621() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        BotButton.this.pressAnimator = null;
                    }
                });
                this.pressAnimator.setInterpolator(new OvershootInterpolator(2.0f));
                this.pressAnimator.setDuration(350L);
                this.pressAnimator.start();
            }
        }
    }

    public /* synthetic */ void lambda$setPressed$0(ValueAnimator valueAnimator) {
        this.pressT = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.invalidateRunnable.run();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Cells.BotButton$1 */
    public class C32621 extends AnimatorListenerAdapter {
        public C32621() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            BotButton.this.pressAnimator = null;
        }
    }

    public boolean hasPositionFlag(int i) {
        return (this.positionFlags & i) == i;
    }

    public float getPressScale() {
        if (this.pressed) {
            float f = this.pressT;
            if (f != 1.0f) {
                float fMin = f + (Math.min(40.0f, 1000.0f / AndroidUtilities.screenRefreshRate) / 100.0f);
                this.pressT = fMin;
                this.pressT = Utilities.clamp(fMin, 1.0f, 0.0f);
                this.invalidateRunnable.run();
            }
        }
        return ((1.0f - this.pressT) * 0.04f) + 0.96f;
    }
}
