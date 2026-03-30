package org.telegram.p026ui.Components.Premium;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import androidx.core.math.MathUtils;
import com.sun.jna.Function;
import java.util.ArrayList;
import org.mvel2.DataTypes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p026ui.ActionBar.Theme;

/* JADX INFO: loaded from: classes5.dex */
public class StarParticlesView extends View {
    private LinearGradient clipGradient;
    private Matrix clipGradientMatrix;
    private Paint clipGradientPaint;
    public boolean doNotFling;
    public Drawable drawable;
    private boolean isLiteModeParticlesAllowed;
    private Utilities.Callback powerSaverCallback;
    int size;

    /* JADX WARN: Illegal instructions before constructor call */
    public StarParticlesView(Context context) {
        int i;
        if (SharedConfig.getDevicePerformanceClass() == 2) {
            i = DataTypes.EMPTY;
        } else {
            i = SharedConfig.getDevicePerformanceClass() == 1 ? 100 : 50;
        }
        this(context, i);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Utilities.Callback callback = new Utilities.Callback() { // from class: org.telegram.ui.Components.Premium.StarParticlesView$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$onAttachedToWindow$0((Boolean) obj);
            }
        };
        this.powerSaverCallback = callback;
        LiteMode.addOnPowerSaverAppliedListener(callback);
        onApplyPowerSaverMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAttachedToWindow$0(Boolean bool) {
        onApplyPowerSaverMode();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Utilities.Callback callback = this.powerSaverCallback;
        if (callback != null) {
            LiteMode.removeOnPowerSaverAppliedListener(callback);
        }
    }

    private void onApplyPowerSaverMode() {
        boolean zIsEnabled = LiteMode.isEnabled(131072);
        if (this.isLiteModeParticlesAllowed != zIsEnabled) {
            this.isLiteModeParticlesAllowed = zIsEnabled;
            invalidate();
        }
    }

    public StarParticlesView(Context context, int i) {
        super(context);
        this.isLiteModeParticlesAllowed = true;
        this.drawable = new Drawable(i);
        configure();
    }

    protected void configure() {
        Drawable drawable = this.drawable;
        drawable.type = 100;
        drawable.roundEffect = true;
        drawable.useRotate = true;
        drawable.useBlur = true;
        drawable.checkBounds = true;
        drawable.size1 = 4;
        drawable.f1994k3 = 0.98f;
        drawable.f1993k2 = 0.98f;
        drawable.f1992k1 = 0.98f;
        drawable.init();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth() << (getMeasuredHeight() + 16);
        this.drawable.rect.set(0.0f, 0.0f, getStarsRectWidth(), AndroidUtilities.m1081dp(140.0f));
        this.drawable.rect.offset((getMeasuredWidth() - this.drawable.rect.width()) / 2.0f, (getMeasuredHeight() - this.drawable.rect.height()) / 2.0f);
        this.drawable.rect2.set(-AndroidUtilities.m1081dp(15.0f), -AndroidUtilities.m1081dp(15.0f), getMeasuredWidth() + AndroidUtilities.m1081dp(15.0f), getMeasuredHeight() + AndroidUtilities.m1081dp(15.0f));
        if (this.size != measuredWidth) {
            this.size = measuredWidth;
            this.drawable.resetPositions();
        }
    }

    protected int getStarsRectWidth() {
        return AndroidUtilities.m1081dp(140.0f);
    }

    public void setClipWithGradient() {
        Paint paint = new Paint(1);
        this.clipGradientPaint = paint;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, AndroidUtilities.m1081dp(12.0f), new int[]{16777215, -1}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP);
        this.clipGradient = linearGradient;
        this.clipGradientPaint.setShader(linearGradient);
        this.clipGradientMatrix = new Matrix();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Canvas canvas2;
        super.onDraw(canvas);
        if (this.isLiteModeParticlesAllowed) {
            if (this.clipGradientPaint != null) {
                canvas.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), Function.USE_VARARGS, 31);
                canvas2 = canvas;
            } else {
                canvas2 = canvas;
            }
            this.drawable.onDraw(canvas2);
            if (this.clipGradientPaint != null) {
                canvas2.save();
                this.clipGradientMatrix.reset();
                this.clipGradientMatrix.postTranslate(0.0f, (getHeight() + 1) - AndroidUtilities.m1081dp(12.0f));
                this.clipGradient.setLocalMatrix(this.clipGradientMatrix);
                canvas2.drawRect(0.0f, getHeight() - AndroidUtilities.m1081dp(12.0f), getWidth(), getHeight(), this.clipGradientPaint);
                this.clipGradientMatrix.reset();
                this.clipGradientMatrix.postRotate(180.0f);
                this.clipGradientMatrix.postTranslate(0.0f, AndroidUtilities.m1081dp(12.0f));
                this.clipGradient.setLocalMatrix(this.clipGradientMatrix);
                canvas2.drawRect(0.0f, 0.0f, getWidth(), AndroidUtilities.m1081dp(12.0f), this.clipGradientPaint);
                canvas2.restore();
                canvas2.restore();
            }
            if (this.drawable.paused) {
                return;
            }
            invalidate();
        }
    }

    public void flingParticles(float f) {
        if (this.doNotFling) {
            return;
        }
        float f2 = f < 60.0f ? 5.0f : f < 180.0f ? 9.0f : 15.0f;
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.Premium.StarParticlesView$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$flingParticles$1(valueAnimator);
            }
        };
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, f2);
        valueAnimatorOfFloat.addUpdateListener(animatorUpdateListener);
        valueAnimatorOfFloat.setDuration(600L);
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(f2, 1.0f);
        valueAnimatorOfFloat2.addUpdateListener(animatorUpdateListener);
        valueAnimatorOfFloat2.setDuration(2000L);
        animatorSet.playTogether(valueAnimatorOfFloat, valueAnimatorOfFloat2);
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$flingParticles$1(ValueAnimator valueAnimator) {
        this.drawable.speedScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
    }

    /* JADX INFO: loaded from: classes3.dex */
    public static class Drawable {

        /* JADX INFO: renamed from: a */
        float f1988a;

        /* JADX INFO: renamed from: a1 */
        float f1989a1;

        /* JADX INFO: renamed from: a2 */
        float f1990a2;
        public Integer color;
        public final int count;
        public boolean distributionAlgorithm;
        public Utilities.CallbackReturn getPaint;
        private int lastColor;
        public Paint overridePaint;
        public boolean paused;
        public long pausedTime;
        float[] points1;
        float[] points2;
        float[] points3;
        int pointsCount1;
        int pointsCount2;
        int pointsCount3;
        private long prevTime;
        public Theme.ResourcesProvider resourcesProvider;
        public boolean startFromCenter;
        public boolean useGradient;
        public boolean useRotate;
        public boolean useScale;
        public RectF rect = new RectF();
        public RectF rect2 = new RectF();
        public RectF excludeRect = new RectF();
        private final Bitmap[] stars = new Bitmap[3];
        public Paint paint = new Paint();
        public float excludeRadius = 0.0f;
        public float centerOffsetX = 0.0f;
        public float centerOffsetY = 0.0f;
        public ArrayList particles = new ArrayList();
        public float speedScale = 1.0f;
        public int size1 = 14;
        public int size2 = 12;
        public int size3 = 10;

        /* JADX INFO: renamed from: k1 */
        public float f1992k1 = 0.85f;

        /* JADX INFO: renamed from: k2 */
        public float f1993k2 = 0.85f;

        /* JADX INFO: renamed from: k3 */
        public float f1994k3 = 0.9f;
        public long minLifeTime = 2000;
        public int randLifeTime = MediaDataController.MAX_STYLE_RUNS_COUNT;

        /* JADX INFO: renamed from: dt */
        private final float f1991dt = 1000.0f / AndroidUtilities.screenRefreshRate;
        Matrix matrix = new Matrix();
        Matrix matrix2 = new Matrix();
        Matrix matrix3 = new Matrix();
        public boolean checkBounds = false;
        public boolean checkTime = true;
        public boolean isCircle = true;
        public boolean useBlur = false;
        public boolean forceMaxAlpha = false;
        public boolean roundEffect = true;
        public int type = -1;
        public int colorKey = Theme.key_premiumStartSmallStarsColor;
        public final boolean[] svg = new boolean[3];
        public final boolean[] flip = new boolean[3];
        private int lastParticleI = 0;

        public Drawable(int i) {
            this.count = i;
            this.distributionAlgorithm = i < 50;
        }

        public void init() {
            if (this.useRotate) {
                int i = this.count;
                this.points1 = new float[i * 2];
                this.points2 = new float[i * 2];
                this.points3 = new float[i * 2];
            }
            generateBitmaps();
            if (this.particles.isEmpty()) {
                for (int i2 = 0; i2 < this.count; i2++) {
                    this.particles.add(new Particle());
                }
            }
        }

        public void updateColors() {
            int color = Theme.getColor(this.colorKey, this.resourcesProvider);
            if (this.lastColor != color) {
                this.lastColor = color;
                generateBitmaps();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:78:0x01ab  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void generateBitmaps() {
            /*
                Method dump skipped, instruction units count: 723
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.Premium.StarParticlesView.Drawable.generateBitmaps():void");
        }

        protected int getPathColor(int i) {
            Integer num = this.color;
            if (num != null) {
                return num.intValue();
            }
            if (this.type == 100) {
                return ColorUtils.setAlphaComponent(Theme.getColor(this.colorKey, this.resourcesProvider), DataTypes.EMPTY);
            }
            return Theme.getColor(this.colorKey, this.resourcesProvider);
        }

        public void resetPositions() {
            long jCurrentTimeMillis = System.currentTimeMillis();
            for (int i = 0; i < this.particles.size(); i++) {
                ((Particle) this.particles.get(i)).genPosition(jCurrentTimeMillis);
            }
        }

        public void onDraw(Canvas canvas) {
            onDraw(canvas, 1.0f);
        }

        public void onDraw(Canvas canvas, float f) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long jClamp = MathUtils.clamp(jCurrentTimeMillis - this.prevTime, 4L, 50L);
            if (this.useRotate) {
                this.matrix.reset();
                float f2 = jClamp;
                float f3 = this.f1988a + ((f2 / 40000.0f) * 360.0f);
                this.f1988a = f3;
                this.f1989a1 += (f2 / 50000.0f) * 360.0f;
                this.f1990a2 += (f2 / 60000.0f) * 360.0f;
                this.matrix.setRotate(f3, this.rect.centerX() + this.centerOffsetX, this.rect.centerY() + this.centerOffsetY);
                this.matrix2.setRotate(this.f1989a1, this.rect.centerX() + this.centerOffsetX, this.rect.centerY() + this.centerOffsetY);
                this.matrix3.setRotate(this.f1990a2, this.rect.centerX() + this.centerOffsetX, this.rect.centerY() + this.centerOffsetY);
                this.pointsCount1 = 0;
                this.pointsCount2 = 0;
                this.pointsCount3 = 0;
                for (int i = 0; i < this.particles.size(); i++) {
                    ((Particle) this.particles.get(i)).updatePoint();
                }
                Matrix matrix = this.matrix;
                float[] fArr = this.points1;
                matrix.mapPoints(fArr, 0, fArr, 0, this.pointsCount1);
                Matrix matrix2 = this.matrix2;
                float[] fArr2 = this.points2;
                matrix2.mapPoints(fArr2, 0, fArr2, 0, this.pointsCount2);
                Matrix matrix3 = this.matrix3;
                float[] fArr3 = this.points3;
                matrix3.mapPoints(fArr3, 0, fArr3, 0, this.pointsCount3);
                this.pointsCount1 = 0;
                this.pointsCount2 = 0;
                this.pointsCount3 = 0;
            }
            for (int i2 = 0; i2 < this.particles.size(); i2++) {
                Particle particle = (Particle) this.particles.get(i2);
                if (this.paused) {
                    particle.draw(canvas, this.pausedTime, f);
                } else {
                    particle.draw(canvas, jCurrentTimeMillis, f);
                }
                if (this.checkTime && jCurrentTimeMillis > particle.lifeTime) {
                    particle.genPosition(jCurrentTimeMillis);
                }
                if (this.checkBounds && !this.rect2.contains(particle.drawingX, particle.drawingY)) {
                    particle.genPosition(jCurrentTimeMillis);
                }
            }
            this.prevTime = jCurrentTimeMillis;
        }

        public class Particle {
            private int alpha;
            private float drawingX;
            private float drawingY;
            float flipProgress;

            /* JADX INFO: renamed from: i */
            private int f1995i;
            float inProgress;
            public long lifeTime;
            private float randomRotate;
            private int starIndex;
            private float vecX;
            private float vecY;

            /* JADX INFO: renamed from: x */
            private float f1996x;

            /* JADX INFO: renamed from: x2 */
            private float f1997x2;

            /* JADX INFO: renamed from: y */
            private float f1998y;

            /* JADX INFO: renamed from: y2 */
            private float f1999y2;
            private float scale = 1.0f;
            private boolean first = true;

            public Particle() {
                int i = Drawable.this.lastParticleI;
                Drawable.this.lastParticleI = i + 1;
                this.f1995i = i;
            }

            public void updatePoint() {
                int i = this.starIndex;
                if (i == 0) {
                    Drawable drawable = Drawable.this;
                    float[] fArr = drawable.points1;
                    int i2 = drawable.pointsCount1;
                    fArr[i2 * 2] = this.f1996x;
                    fArr[(i2 * 2) + 1] = this.f1998y;
                    drawable.pointsCount1 = i2 + 1;
                    return;
                }
                if (i == 1) {
                    Drawable drawable2 = Drawable.this;
                    float[] fArr2 = drawable2.points2;
                    int i3 = drawable2.pointsCount2;
                    fArr2[i3 * 2] = this.f1996x;
                    fArr2[(i3 * 2) + 1] = this.f1998y;
                    drawable2.pointsCount2 = i3 + 1;
                    return;
                }
                if (i == 2) {
                    Drawable drawable3 = Drawable.this;
                    float[] fArr3 = drawable3.points3;
                    int i4 = drawable3.pointsCount3;
                    fArr3[i4 * 2] = this.f1996x;
                    fArr3[(i4 * 2) + 1] = this.f1998y;
                    drawable3.pointsCount3 = i4 + 1;
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:26:0x00c5  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void draw(android.graphics.Canvas r12, long r13, float r15) {
                /*
                    Method dump skipped, instruction units count: 438
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.Premium.StarParticlesView.Drawable.Particle.draw(android.graphics.Canvas, long, float):void");
            }

            public void genPosition(long j) {
                float f;
                float fM1081dp;
                double dAtan2;
                int i;
                float f2;
                float f3;
                if (Drawable.this.type == 28) {
                    if (Utilities.fastRandom.nextFloat() < 0.13f) {
                        this.starIndex = 0;
                    } else {
                        this.starIndex = (int) Math.floor((r1 * (Drawable.this.stars.length - 2)) + 1.0f);
                    }
                } else {
                    this.starIndex = Math.abs(Utilities.fastRandom.nextInt() % Drawable.this.stars.length);
                }
                Drawable drawable = Drawable.this;
                this.lifeTime = j + drawable.minLifeTime + ((long) Utilities.fastRandom.nextInt(drawable.randLifeTime * (drawable.flip[this.starIndex] ? 3 : 1)));
                this.randomRotate = 0.0f;
                float f4 = 0.6f;
                if (Drawable.this.useScale) {
                    this.scale = (Utilities.fastRandom.nextFloat() * 0.6f) + 0.4f;
                }
                Drawable drawable2 = Drawable.this;
                if (drawable2.distributionAlgorithm) {
                    float fAbs = drawable2.rect.left + Math.abs(Utilities.fastRandom.nextInt() % Drawable.this.rect.width());
                    float fAbs2 = Drawable.this.rect.top + Math.abs(Utilities.fastRandom.nextInt() % Drawable.this.rect.height());
                    float f5 = 0.0f;
                    int i2 = 0;
                    while (i2 < 10) {
                        float fAbs3 = Drawable.this.rect.left + Math.abs(Utilities.fastRandom.nextInt() % Drawable.this.rect.width());
                        float fAbs4 = Drawable.this.rect.top + Math.abs(Utilities.fastRandom.nextInt() % Drawable.this.rect.height());
                        float f6 = 2.1474836E9f;
                        int i3 = 0;
                        while (i3 < Drawable.this.particles.size()) {
                            Drawable drawable3 = Drawable.this;
                            float f7 = f4;
                            if (drawable3.startFromCenter) {
                                f2 = ((Particle) drawable3.particles.get(i3)).f1997x2 - fAbs3;
                                f3 = ((Particle) Drawable.this.particles.get(i3)).f1999y2;
                            } else {
                                f2 = ((Particle) drawable3.particles.get(i3)).f1996x - fAbs3;
                                f3 = ((Particle) Drawable.this.particles.get(i3)).f1998y;
                            }
                            float f8 = f3 - fAbs4;
                            float f9 = (f2 * f2) + (f8 * f8);
                            if (f9 < f6) {
                                f6 = f9;
                            }
                            i3++;
                            f4 = f7;
                        }
                        float f10 = f4;
                        if (f6 > f5) {
                            fAbs = fAbs3;
                            fAbs2 = fAbs4;
                            f5 = f6;
                        }
                        i2++;
                        f4 = f10;
                    }
                    f = f4;
                    this.f1996x = fAbs;
                    this.f1998y = fAbs2;
                } else {
                    f = 0.6f;
                    if (drawable2.isCircle) {
                        float fWidth = Drawable.this.rect.width();
                        float f11 = Drawable.this.excludeRadius;
                        float fAbs5 = ((Math.abs(Utilities.fastRandom.nextInt() % MediaDataController.MAX_STYLE_RUNS_COUNT) / 1000.0f) * (fWidth - f11)) + f11;
                        float fAbs6 = Math.abs(Utilities.fastRandom.nextInt() % 360);
                        if (!Drawable.this.flip[this.starIndex] || this.first) {
                            fM1081dp = 0.0f;
                        } else {
                            fAbs5 = Math.min(fAbs5, AndroidUtilities.m1081dp(10.0f));
                            fM1081dp = AndroidUtilities.m1081dp(30.0f) + 0.0f;
                        }
                        double d = fAbs5;
                        double d2 = fAbs6;
                        this.f1996x = Drawable.this.rect.centerX() + Drawable.this.centerOffsetX + ((float) (Math.sin(Math.toRadians(d2)) * d));
                        this.f1998y = Drawable.this.rect.centerY() + fM1081dp + Drawable.this.centerOffsetY + ((float) (d * Math.cos(Math.toRadians(d2))));
                    } else {
                        this.f1996x = drawable2.rect.left + Math.abs(Utilities.fastRandom.nextInt() % Drawable.this.rect.width());
                        this.f1998y = Drawable.this.rect.top + Math.abs(Utilities.fastRandom.nextInt() % Drawable.this.rect.height());
                    }
                }
                if (Drawable.this.flip[this.starIndex]) {
                    this.flipProgress = Math.abs(Utilities.fastRandom.nextFloat() * 2.0f);
                }
                Drawable drawable4 = Drawable.this;
                if (drawable4.flip[this.starIndex]) {
                    dAtan2 = Math.toRadians(280.0f - (200.0f * Utilities.fastRandom.nextFloat()));
                } else if (drawable4.startFromCenter) {
                    dAtan2 = Utilities.fastRandom.nextDouble() * 3.141592653589793d * 2.0d;
                } else {
                    float f12 = this.f1998y;
                    float fCenterY = drawable4.rect.centerY();
                    Drawable drawable5 = Drawable.this;
                    dAtan2 = Math.atan2(f12 - (fCenterY + drawable5.centerOffsetY), this.f1996x - (drawable5.rect.centerX() + Drawable.this.centerOffsetX));
                }
                this.vecX = (float) Math.cos(dAtan2);
                this.vecY = (float) Math.sin(dAtan2);
                if (Drawable.this.svg[this.starIndex]) {
                    this.alpha = (int) (((Utilities.fastRandom.nextInt(50) + 50) / 100.0f) * 120.0f);
                } else {
                    this.alpha = (int) (((Utilities.fastRandom.nextInt(50) + 50) / 100.0f) * 255.0f);
                }
                int i4 = Drawable.this.type;
                if ((i4 == 6 && ((i = this.starIndex) == 1 || i == 2)) || i4 == 9 || i4 == 3 || i4 == 7 || i4 == 24 || i4 == 11 || i4 == 22 || i4 == 4) {
                    this.randomRotate = (int) (((Utilities.fastRandom.nextInt() % 100) / 100.0f) * 45.0f);
                }
                Drawable drawable6 = Drawable.this;
                if (drawable6.type != 101) {
                    this.inProgress = 0.0f;
                }
                if (drawable6.startFromCenter) {
                    float fNextFloat = (((Utilities.fastRandom.nextFloat() * 1.2f) + f) * Math.min(Drawable.this.rect.width(), Drawable.this.rect.height())) / 2.0f;
                    float fCenterX = Drawable.this.rect.centerX() + Drawable.this.centerOffsetX + (((float) Math.cos(dAtan2)) * fNextFloat);
                    this.f1996x = fCenterX;
                    this.f1997x2 = fCenterX;
                    float fCenterY2 = Drawable.this.rect.centerY() + Drawable.this.centerOffsetY + (((float) Math.sin(dAtan2)) * fNextFloat);
                    this.f1998y = fCenterY2;
                    this.f1999y2 = fCenterY2;
                }
                this.first = false;
            }
        }
    }

    public void setPaused(boolean z) {
        Drawable drawable = this.drawable;
        if (z == drawable.paused) {
            return;
        }
        drawable.paused = z;
        if (z) {
            drawable.pausedTime = System.currentTimeMillis();
            return;
        }
        for (int i = 0; i < this.drawable.particles.size(); i++) {
            ((Drawable.Particle) this.drawable.particles.get(i)).lifeTime += System.currentTimeMillis() - this.drawable.pausedTime;
        }
        invalidate();
    }
}
