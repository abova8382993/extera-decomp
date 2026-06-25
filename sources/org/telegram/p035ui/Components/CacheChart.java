package org.telegram.p035ui.Components;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.material.timepicker.TimeModel;
import com.google.zxing.common.detector.MathUtils;
import java.util.Arrays;
import java.util.Comparator;
import okhttp3.internal.url._UrlKt;
import org.mvel2.MVEL;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.SvgHelper;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.AnimatedTextView;
import org.telegram.p035ui.Components.CacheChart;
import org.telegram.p035ui.Components.Premium.StarParticlesView;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;

/* JADX INFO: loaded from: classes7.dex */
public abstract class CacheChart extends View {
    private static final int[] DEFAULT_COLORS;
    private static final int[] DEFAULT_PARTICLES;
    private static Long loadedStart;
    private static long particlesStart;
    private static Long start;
    private final AnimatedTextView.AnimatedTextDrawable bottomCompleteText;
    private final AnimatedTextView.AnimatedTextDrawable bottomText;
    private RectF chartBounds;
    private RectF chartInnerBounds;
    private RectF chartMeasureBounds;
    private final int[] colorKeys;
    private boolean complete;
    private StarParticlesView.Drawable completeDrawable;
    private AnimatedFloat completeFloat;
    private LinearGradient completeGradient;
    private Matrix completeGradientMatrix;
    private Paint completePaint;
    private Paint completePaintStroke;
    private Path completePath;
    private RectF completePathBounds;
    private LinearGradient completeTextGradient;
    private Matrix completeTextGradientMatrix;
    private boolean interceptTouch;
    private boolean isAttached;
    private boolean loading;
    private Paint loadingBackgroundPaint;
    public AnimatedFloat loadingFloat;
    private final int[] particles;
    private RectF roundingRect;
    private final int sectionsCount;
    private Sector[] sectors;
    private float[] segmentsTmp;
    private int selectedIndex;
    private final boolean svgParticles;
    private float[] tempFloat;
    private int[] tempPercents;
    private final AnimatedTextView.AnimatedTextDrawable topCompleteText;
    private final AnimatedTextView.AnimatedTextDrawable topText;
    private final int type;

    public static float toRad(float f) {
        return (float) (((double) (f / 180.0f)) * 3.141592653589793d);
    }

    public int heightDp() {
        return 200;
    }

    public void onSectionClick(int i) {
    }

    public abstract void onSectionDown(int i, boolean z);

    public int padInsideDp() {
        return 0;
    }

    static {
        int i = Theme.key_statisticChartLine_lightblue;
        int i2 = Theme.key_statisticChartLine_blue;
        int i3 = Theme.key_statisticChartLine_green;
        int i4 = Theme.key_statisticChartLine_purple;
        int i5 = Theme.key_statisticChartLine_lightgreen;
        int i6 = Theme.key_statisticChartLine_red;
        int i7 = Theme.key_statisticChartLine_orange;
        int i8 = Theme.key_statisticChartLine_cyan;
        int i9 = Theme.key_statisticChartLine_golden;
        DEFAULT_COLORS = new int[]{i, i2, i3, i4, i5, i6, i7, i8, i4, i9, i9};
        int i10 = C2797R.raw.cache_photos;
        int i11 = C2797R.raw.cache_videos;
        int i12 = C2797R.raw.cache_documents;
        int i13 = C2797R.raw.cache_music;
        int i14 = C2797R.raw.cache_stickers;
        int i15 = C2797R.raw.cache_profile_photos;
        int i16 = C2797R.raw.cache_other;
        DEFAULT_PARTICLES = new int[]{i10, i11, i12, i13, i11, i13, i14, i15, i16, i16, i12};
        particlesStart = -1L;
    }

    public class Sector {
        float angleCenter;
        AnimatedFloat angleCenterAnimated;
        float angleSize;
        AnimatedFloat angleSizeAnimated;
        Paint cut;
        RadialGradient gradient;
        Matrix gradientMatrix;
        int gradientWidth;
        private float lastAngleCenter;
        private float lastAngleSize;
        private float lastCx;
        private float lastCy;
        private float lastRounding;
        private float lastThickness;
        private float lastWidth;
        Paint paint;
        Bitmap particle;
        Paint particlePaint;
        float particlesAlpha;
        AnimatedFloat particlesAlphaAnimated;
        Path path;
        RectF pathBounds;
        RectF rectF;
        boolean selected;
        AnimatedFloat selectedAnimated;
        AnimatedTextView.AnimatedTextDrawable text;
        float textAlpha;
        AnimatedFloat textAlphaAnimated;
        float textScale;
        AnimatedFloat textScaleAnimated;
        Paint uncut;

        public Sector() {
            Paint paint = new Paint(3);
            this.particlePaint = paint;
            paint.setColor(-1);
            CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
            this.angleCenterAnimated = new AnimatedFloat(CacheChart.this, 650L, cubicBezierInterpolator);
            this.angleSizeAnimated = new AnimatedFloat(CacheChart.this, 650L, cubicBezierInterpolator);
            CubicBezierInterpolator cubicBezierInterpolator2 = CubicBezierInterpolator.EASE_OUT;
            this.textAlphaAnimated = new AnimatedFloat(CacheChart.this, 0L, 150L, cubicBezierInterpolator2);
            this.textScale = 1.0f;
            this.textScaleAnimated = new AnimatedFloat(CacheChart.this, 0L, 150L, cubicBezierInterpolator2);
            this.text = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
            this.particlesAlphaAnimated = new AnimatedFloat(CacheChart.this, 0L, 150L, cubicBezierInterpolator2);
            this.selectedAnimated = new AnimatedFloat(CacheChart.this, 0L, 200L, cubicBezierInterpolator);
            this.text.setTextColor(Theme.isCurrentThemeMonet() ? Theme.getColor(Theme.key_windowBackgroundGray) : -1);
            this.text.setAnimationProperties(0.35f, 0L, 200L, cubicBezierInterpolator);
            this.text.setTypeface(AndroidUtilities.bold());
            this.text.setTextSize(AndroidUtilities.m1036dp(15.0f));
            this.text.setGravity(17);
            this.path = new Path();
            this.paint = new Paint(1);
            this.pathBounds = new RectF();
            this.uncut = new Paint(1);
            Paint paint2 = new Paint(1);
            this.cut = paint2;
            paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            this.particlePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            this.rectF = new RectF();
        }

        private void setupPath(RectF rectF, RectF rectF2, float f, float f2, float f3) {
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            float f9;
            float fMin = Math.min(Math.min(f3, (rectF.width() - rectF2.width()) / 4.0f), (float) (((double) (f2 / 180.0f)) * 3.141592653589793d * ((double) (rectF2.width() / 2.0f))));
            float fWidth = (rectF.width() - rectF2.width()) / 2.0f;
            if (this.lastAngleCenter == f && this.lastAngleSize == f2 && this.lastRounding == fMin && this.lastThickness == fWidth && this.lastWidth == rectF.width() && this.lastCx == rectF.centerX() && this.lastCy == rectF.centerY()) {
                return;
            }
            this.lastAngleCenter = f;
            this.lastAngleSize = f2;
            this.lastRounding = fMin;
            this.lastThickness = fWidth;
            this.lastWidth = rectF.width();
            this.lastCx = rectF.centerX();
            this.lastCy = rectF.centerY();
            float f10 = f - f2;
            float f11 = f + f2;
            boolean z = fMin > 0.0f;
            float f12 = fMin * 2.0f;
            float fWidth2 = (fMin / ((float) (((double) (rectF.width() - f12)) * 3.141592653589793d))) * 360.0f;
            float fWidth3 = ((fMin / ((float) (((double) (rectF2.width() + f12)) * 3.141592653589793d))) * 360.0f) + ((f2 > 175.0f ? 0 : 1) * 0.5f);
            float fWidth4 = (rectF.width() / 2.0f) - fMin;
            float fWidth5 = (rectF2.width() / 2.0f) + fMin;
            this.path.rewind();
            float f13 = f11 - f10;
            if (f13 < 0.5f) {
                return;
            }
            if (z) {
                f5 = 180.0f;
                f6 = f10;
                double d = fWidth4;
                f8 = 2.0f;
                f7 = fWidth5;
                f4 = fMin;
                CacheChart.setCircleBounds(CacheChart.this.roundingRect, ((double) rectF.centerX()) + (Math.cos(CacheChart.toRad(r20)) * d), ((double) rectF.centerY()) + (d * Math.sin(CacheChart.toRad(r20))), f4);
                this.path.arcTo(CacheChart.this.roundingRect, (f6 + fWidth2) - 90.0f, 90.0f);
            } else {
                f4 = fMin;
                f5 = 180.0f;
                f6 = f10;
                f7 = fWidth5;
                f8 = 2.0f;
            }
            this.path.arcTo(rectF, f6 + fWidth2, f13 - (fWidth2 * f8));
            if (z) {
                double d2 = fWidth4;
                CacheChart.setCircleBounds(CacheChart.this.roundingRect, ((double) rectF.centerX()) + (Math.cos(CacheChart.toRad(r2)) * d2), ((double) rectF.centerY()) + (d2 * Math.sin(CacheChart.toRad(r2))), f4);
                this.path.arcTo(CacheChart.this.roundingRect, f11 - fWidth2, 90.0f);
                f9 = f7;
                double d3 = f9;
                CacheChart.setCircleBounds(CacheChart.this.roundingRect, ((double) rectF2.centerX()) + (Math.cos(CacheChart.toRad(r2)) * d3), ((double) rectF2.centerY()) + (d3 * Math.sin(CacheChart.toRad(r2))), f4);
                this.path.arcTo(CacheChart.this.roundingRect, (f11 - fWidth3) + 90.0f, 90.0f);
            } else {
                f9 = f7;
            }
            this.path.arcTo(rectF2, f11 - fWidth3, -(f13 - (fWidth3 * f8)));
            if (z) {
                double d4 = f9;
                CacheChart.setCircleBounds(CacheChart.this.roundingRect, ((double) rectF2.centerX()) + (Math.cos(CacheChart.toRad(r3)) * d4), ((double) rectF2.centerY()) + (d4 * Math.sin(CacheChart.toRad(r3))), f4);
                this.path.arcTo(CacheChart.this.roundingRect, f6 + fWidth3 + f5, 90.0f);
            }
            this.path.close();
            this.path.computeBounds(this.pathBounds, false);
        }

        private void setGradientBounds(float f, float f2, float f3, float f4) {
            this.gradientMatrix.reset();
            this.gradientMatrix.setTranslate(f, f2);
            this.gradient.setLocalMatrix(this.gradientMatrix);
        }

        private void drawParticles(Canvas canvas, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            if (f10 <= 0.0f || !LiteMode.isEnabled(LiteMode.FLAGS_CHAT)) {
                return;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            float fSqrt = (float) Math.sqrt(2.0d);
            if (CacheChart.particlesStart < 0) {
                CacheChart.particlesStart = jCurrentTimeMillis;
            }
            float f11 = (jCurrentTimeMillis - CacheChart.particlesStart) / 10000.0f;
            Bitmap bitmap = this.particle;
            if (bitmap != null) {
                int width = bitmap.getWidth();
                float f12 = width;
                float fDpf2 = AndroidUtilities.dpf2(15.0f) / f12;
                float f13 = 7.0f;
                int iFloor = (int) Math.floor((f5 % 360.0f) / 7.0f);
                int iCeil = (int) Math.ceil((f6 % 360.0f) / 7.0f);
                while (iFloor <= iCeil) {
                    float fSin = (float) ((((double) (100.0f + f11)) * (((Math.sin(2000.0f * r11) + 1.0d) * 0.25d) + 1.0d)) % 1.0d);
                    float f14 = f12 * fSqrt;
                    float f15 = f11;
                    double dLerp = AndroidUtilities.lerp(f7 - f14, f8 + f14, fSin);
                    float fCos = (float) (((double) f) + (Math.cos(CacheChart.toRad(r11)) * dLerp));
                    int i = width;
                    float fSin2 = (float) (((double) f2) + (Math.sin(CacheChart.toRad(r11)) * dLerp));
                    float fAbs = 0.65f * f10 * ((Math.abs(fSin - 0.5f) * (-1.75f)) + 1.0f);
                    double d = ((double) fSin) * 3.141592653589793d;
                    this.particlePaint.setAlpha((int) (Math.max(0.0f, Math.min(1.0f, fAbs * ((((float) (Math.sin(d) - 1.0d)) * 0.25f) + 1.0f) * AndroidUtilities.lerp(1.0f, Math.min(MathUtils.distance(fCos, fSin2, f3, f4) / AndroidUtilities.dpf2(64.0f), 1.0f), f9))) * 255.0f));
                    float fSin3 = ((float) (((double) (((((float) (Math.sin(d) - 1.0d)) * 0.25f) + 1.0f) * 0.75f)) * (((Math.sin(iFloor * f13) + 1.0d) * 0.25d) + 0.800000011920929d))) * fDpf2;
                    canvas.save();
                    canvas.translate(fCos, fSin2);
                    canvas.scale(fSin3, fSin3);
                    float f16 = -(i >> 1);
                    canvas.drawBitmap(this.particle, f16, f16, this.particlePaint);
                    canvas.restore();
                    iFloor++;
                    fSqrt = fSqrt;
                    f11 = f15;
                    width = i;
                    f13 = 7.0f;
                }
            }
        }

        public void draw(Canvas canvas, RectF rectF, RectF rectF2, float f, float f2, float f3, float f4, float f5) {
            float f6;
            Canvas canvas2;
            float f7;
            float f8 = this.selectedAnimated.set(this.selected ? 1.0f : 0.0f);
            this.rectF.set(rectF);
            this.rectF.inset((-AndroidUtilities.m1036dp(9.0f)) * f8, f8 * (-AndroidUtilities.m1036dp(9.0f)));
            float fCenterX = (float) (((double) this.rectF.centerX()) + ((Math.cos(CacheChart.toRad(f)) * ((double) (this.rectF.width() + rectF2.width()))) / 4.0d));
            float fCenterY = (float) (((double) this.rectF.centerY()) + ((Math.sin(CacheChart.toRad(f)) * ((double) (this.rectF.width() + rectF2.width()))) / 4.0d));
            float f9 = f5 * this.textAlphaAnimated.set(this.textAlpha) * f4;
            float f10 = this.particlesAlphaAnimated.set(this.particlesAlpha);
            this.paint.setAlpha((int) (f4 * 255.0f));
            float f11 = f2 * 2.0f;
            RectF rectF3 = this.rectF;
            if (f11 >= 359.0f) {
                canvas.saveLayerAlpha(rectF3, 255, 31);
                canvas.drawCircle(this.rectF.centerX(), this.rectF.centerY(), this.rectF.width() / 2.0f, this.uncut);
                canvas.drawRect(this.rectF, this.paint);
                drawParticles(canvas, this.rectF.centerX(), this.rectF.centerY(), fCenterX, fCenterY, 0.0f, 359.0f, rectF2.width() / 2.0f, this.rectF.width() / 2.0f, f9, Math.max(0.0f, (f5 / 0.75f) - 0.75f) * f10);
                canvas.drawCircle(rectF2.centerX(), rectF2.centerY(), rectF2.width() / 2.0f, this.cut);
                canvas.restore();
                canvas2 = canvas;
                f6 = fCenterX;
                f7 = f9;
            } else {
                setupPath(rectF3, rectF2, f, f2, f3);
                setGradientBounds(this.rectF.centerX(), rectF.centerY(), this.rectF.width() / 2.0f, f);
                canvas.saveLayerAlpha(this.rectF, 255, 31);
                canvas.drawPath(this.path, this.uncut);
                canvas.drawRect(this.rectF, this.paint);
                f6 = fCenterX;
                fCenterY = fCenterY;
                canvas2 = canvas;
                f7 = f9;
                drawParticles(canvas2, this.rectF.centerX(), this.rectF.centerY(), f6, fCenterY, f - f2, f + f2, rectF2.width() / 2.0f, this.rectF.width() / 2.0f, f7, Math.max(0.0f, (f5 / 0.75f) - 0.75f) * f10);
                canvas2.restore();
            }
            float f12 = this.textScaleAnimated.set(this.textScale);
            CacheChart.setCircleBounds(CacheChart.this.roundingRect, f6, fCenterY, 0.0f);
            if (f12 != 1.0f) {
                canvas2.save();
                canvas2.scale(f12, f12, CacheChart.this.roundingRect.centerX(), CacheChart.this.roundingRect.centerY());
            }
            this.text.setAlpha((int) (f7 * 255.0f));
            this.text.setBounds((int) CacheChart.this.roundingRect.left, (int) CacheChart.this.roundingRect.top, (int) CacheChart.this.roundingRect.right, (int) CacheChart.this.roundingRect.bottom);
            this.text.draw(canvas2);
            if (f12 != 1.0f) {
                canvas2.restore();
            }
        }
    }

    public CacheChart(Context context) {
        this(context, 11, DEFAULT_COLORS, 0, DEFAULT_PARTICLES);
    }

    public CacheChart(Context context, int i, int[] iArr, int i2, int[] iArr2) {
        super(context);
        this.chartMeasureBounds = new RectF();
        this.chartBounds = new RectF();
        this.chartInnerBounds = new RectF();
        this.loading = true;
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
        this.loadingFloat = new AnimatedFloat(this, 750L, cubicBezierInterpolator);
        this.complete = false;
        this.completeFloat = new AnimatedFloat(this, 650L, cubicBezierInterpolator);
        this.segmentsTmp = new float[2];
        this.roundingRect = new RectF();
        this.loadingBackgroundPaint = new Paint(1);
        this.completePath = new Path();
        this.completePaintStroke = new Paint(1);
        this.completePaint = new Paint(1);
        AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
        this.topText = animatedTextDrawable;
        AnimatedTextView.AnimatedTextDrawable animatedTextDrawable2 = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
        this.bottomText = animatedTextDrawable2;
        AnimatedTextView.AnimatedTextDrawable animatedTextDrawable3 = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
        this.topCompleteText = animatedTextDrawable3;
        AnimatedTextView.AnimatedTextDrawable animatedTextDrawable4 = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
        this.bottomCompleteText = animatedTextDrawable4;
        this.interceptTouch = true;
        this.selectedIndex = -1;
        setLayerType(2, null);
        this.sectionsCount = i;
        this.colorKeys = iArr;
        this.particles = iArr2;
        this.type = i2;
        this.svgParticles = i2 == 0;
        this.sectors = new Sector[i];
        Paint paint = this.loadingBackgroundPaint;
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        this.loadingBackgroundPaint.setColor(Theme.getColor(Theme.key_listSelector));
        this.completePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        this.completeGradient = new LinearGradient(0.0f, 0.0f, 0.0f, AndroidUtilities.m1036dp(200.0f), new int[]{7263574, -9513642, -12469647, 4307569}, new float[]{0.0f, 0.07f, 0.93f, 1.0f}, tileMode);
        this.completeTextGradient = new LinearGradient(0.0f, 0.0f, 0.0f, AndroidUtilities.m1036dp(200.0f), new int[]{7263574, -9513642, -12469647, 4307569}, new float[]{0.0f, 0.07f, 0.93f, 1.0f}, tileMode);
        this.completeGradientMatrix = new Matrix();
        this.completeTextGradientMatrix = new Matrix();
        this.completePaintStroke.setShader(this.completeGradient);
        this.completePaint.setShader(this.completeGradient);
        this.completePaintStroke.setStyle(style);
        this.completePaintStroke.setStrokeCap(Paint.Cap.ROUND);
        this.completePaintStroke.setStrokeJoin(Paint.Join.ROUND);
        animatedTextDrawable.setAnimationProperties(0.2f, 0L, 450L, cubicBezierInterpolator);
        animatedTextDrawable.setScaleProperty(0.6f);
        animatedTextDrawable.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        animatedTextDrawable.setTypeface(AndroidUtilities.bold());
        animatedTextDrawable.setTextSize(AndroidUtilities.m1036dp(32.0f));
        animatedTextDrawable.setGravity(17);
        animatedTextDrawable2.setAnimationProperties(0.6f, 0L, 450L, cubicBezierInterpolator);
        animatedTextDrawable2.setScaleProperty(0.6f);
        animatedTextDrawable2.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        animatedTextDrawable2.setTextSize(AndroidUtilities.m1036dp(12.0f));
        animatedTextDrawable2.setGravity(17);
        animatedTextDrawable3.setAnimationProperties(0.2f, 0L, 450L, cubicBezierInterpolator);
        animatedTextDrawable3.setScaleProperty(0.6f);
        animatedTextDrawable3.getPaint().setShader(this.completeTextGradient);
        animatedTextDrawable3.setTypeface(AndroidUtilities.bold());
        animatedTextDrawable3.setTextSize(AndroidUtilities.m1036dp(32.0f));
        animatedTextDrawable3.setGravity(17);
        animatedTextDrawable4.setAnimationProperties(0.6f, 0L, 450L, cubicBezierInterpolator);
        animatedTextDrawable4.setScaleProperty(0.6f);
        animatedTextDrawable4.getPaint().setShader(this.completeTextGradient);
        animatedTextDrawable4.setTypeface(AndroidUtilities.bold());
        animatedTextDrawable4.setTextSize(AndroidUtilities.m1036dp(12.0f));
        animatedTextDrawable4.setGravity(17);
        int i3 = 0;
        while (true) {
            Sector[] sectorArr = this.sectors;
            if (i3 >= sectorArr.length) {
                return;
            }
            Sector sector = new Sector();
            sectorArr[i3] = sector;
            int iBlendOver = Theme.blendOver(Theme.getColor(iArr[i3]), ConnectionsManager.FileTypeAudio);
            int iBlendOver2 = Theme.blendOver(Theme.getColor(iArr[i3]), 822083583);
            sector.gradientWidth = AndroidUtilities.m1036dp(50.0f);
            RadialGradient radialGradient = new RadialGradient(0.0f, 0.0f, AndroidUtilities.m1036dp(86.0f), new int[]{iBlendOver2, iBlendOver}, new float[]{0.3f, 1.0f}, Shader.TileMode.CLAMP);
            sector.gradient = radialGradient;
            Matrix matrix = new Matrix();
            sector.gradientMatrix = matrix;
            radialGradient.setLocalMatrix(matrix);
            sector.paint.setShader(sector.gradient);
            i3++;
        }
    }

    public void setInterceptTouch(boolean z) {
        this.interceptTouch = z;
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.isAttached = true;
        int i = 0;
        while (true) {
            Sector[] sectorArr = this.sectors;
            if (i >= sectorArr.length) {
                return;
            }
            Sector sector = sectorArr[i];
            if (sector.particle == null) {
                if (this.svgParticles) {
                    sector.particle = SvgHelper.getBitmap(this.particles[i], AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(16.0f), -1);
                } else {
                    sector.particle = BitmapFactory.decodeResource(getContext().getResources(), this.particles[i]);
                }
            }
            i++;
        }
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        int i = 0;
        this.isAttached = false;
        while (true) {
            Sector[] sectorArr = this.sectors;
            if (i >= sectorArr.length) {
                return;
            }
            Bitmap bitmap = sectorArr[i].particle;
            if (bitmap != null) {
                bitmap.recycle();
                this.sectors[i].particle = null;
            }
            i++;
        }
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int i;
        boolean z;
        float fDistance = MathUtils.distance(this.chartBounds.centerX(), this.chartBounds.centerY(), motionEvent.getX(), motionEvent.getY());
        float fAtan2 = (float) ((Math.atan2(r1 - this.chartBounds.centerY(), r0 - this.chartBounds.centerX()) / 3.141592653589793d) * 180.0d);
        if (fAtan2 < 0.0f) {
            fAtan2 += 360.0f;
        }
        if (fDistance > this.chartInnerBounds.width() / 2.0f && fDistance < (this.chartBounds.width() / 2.0f) + AndroidUtilities.m1036dp(14.0f)) {
            i = 0;
            while (true) {
                Sector[] sectorArr = this.sectors;
                if (i >= sectorArr.length) {
                    break;
                }
                Sector sector = sectorArr[i];
                float f = sector.angleCenter;
                float f2 = sector.angleSize;
                if (fAtan2 >= f - f2 && fAtan2 <= f + f2) {
                    break;
                }
                i++;
            }
        } else {
            i = -1;
        }
        if (motionEvent.getAction() == 0) {
            setSelected(i);
            if (i >= 0) {
                onSectionDown(i, i != -1);
                if (getParent() != null && this.interceptTouch) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
            }
            return true;
        }
        if (motionEvent.getAction() == 2) {
            onSectionDown(i, i != -1);
            setSelected(i);
            if (i != -1) {
                return true;
            }
        } else if (motionEvent.getAction() == 1) {
            if (i != -1) {
                onSectionClick(i);
                z = true;
            } else {
                z = false;
            }
            setSelected(-1);
            onSectionDown(i, false);
            if (z) {
                return true;
            }
        } else if (motionEvent.getAction() == 3) {
            setSelected(-1);
            onSectionDown(i, false);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void setSelected(int i) {
        if (i == this.selectedIndex) {
            return;
        }
        int i2 = 0;
        while (true) {
            Sector[] sectorArr = this.sectors;
            if (i2 < sectorArr.length) {
                if (i == i2 && sectorArr[i2].angleSize <= 0.0f) {
                    i = -1;
                }
                sectorArr[i2].selected = i == i2;
                i2++;
            } else {
                this.selectedIndex = i;
                invalidate();
                return;
            }
        }
    }

    public static class SegmentSize {
        int index;
        public boolean selected;
        public long size;

        /* JADX INFO: renamed from: of */
        public static SegmentSize m1144of(long j, boolean z) {
            SegmentSize segmentSize = new SegmentSize();
            segmentSize.size = j;
            segmentSize.selected = z;
            return segmentSize;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setSegments(long j, boolean z, SegmentSize... segmentSizeArr) {
        float[] fArr;
        float f;
        float f2;
        float f3;
        int i;
        boolean z2;
        float f4;
        float f5;
        float f6;
        float f7;
        SegmentSize[] segmentSizeArr2 = segmentSizeArr;
        long j2 = 0;
        float f8 = 0.0f;
        int i2 = 0;
        boolean z3 = true;
        if (segmentSizeArr2 == null || segmentSizeArr2.length == 0) {
            this.loading = false;
            this.complete = j == 0;
            if (!z) {
                this.loadingFloat.set(0.0f, true);
                this.completeFloat.set(this.complete ? 1.0f : 0.0f, true);
            }
            this.topCompleteText.setText(this.topText.getText(), false);
            this.topText.setText(MVEL.VERSION_SUB, z);
            this.topCompleteText.setText(MVEL.VERSION_SUB, z);
            this.bottomCompleteText.setText(this.bottomText.getText(), false);
            this.bottomText.setText("KB", z);
            this.bottomCompleteText.setText("KB", z);
            int i3 = 0;
            while (true) {
                Sector[] sectorArr = this.sectors;
                if (i3 < sectorArr.length) {
                    Sector sector = sectorArr[i3];
                    sector.textAlpha = 0.0f;
                    if (!z) {
                        sector.textAlphaAnimated.set(0.0f, true);
                    }
                    i3++;
                } else {
                    invalidate();
                    return;
                }
            }
        } else {
            this.loading = false;
            if (!z) {
                this.loadingFloat.set(0.0f, true);
            }
            SpannableString spannableString = new SpannableString("%");
            int length = segmentSizeArr2.length;
            long j3 = 0;
            int i4 = 0;
            float f9 = 1.0f;
            while (i4 < segmentSizeArr2.length) {
                if (segmentSizeArr2[i4] == null) {
                    SegmentSize segmentSize = new SegmentSize();
                    segmentSizeArr2[i4] = segmentSize;
                    segmentSize.size = j2;
                }
                SegmentSize segmentSize2 = segmentSizeArr2[i4];
                segmentSize2.index = i4;
                long j4 = j2;
                if (segmentSize2 != null && segmentSize2.selected) {
                    j3 += segmentSize2.size;
                }
                if (segmentSize2 == null || segmentSize2.size <= j4 || !segmentSize2.selected) {
                    length--;
                }
                i4++;
                j2 = j4;
            }
            long j5 = j2;
            if (j3 <= j5) {
                this.loading = false;
                this.complete = j <= j5;
                if (!z) {
                    this.loadingFloat.set(0.0f, true);
                    this.completeFloat.set(this.complete ? 1.0f : 0.0f, true);
                }
                this.topCompleteText.setText(this.topText.getText(), false);
                this.topText.setText(MVEL.VERSION_SUB, z);
                this.topCompleteText.setText(MVEL.VERSION_SUB, z);
                this.bottomCompleteText.setText(this.bottomText.getText(), false);
                this.bottomText.setText("KB", z);
                this.bottomCompleteText.setText("KB", z);
                while (true) {
                    Sector[] sectorArr2 = this.sectors;
                    if (i2 < sectorArr2.length) {
                        Sector sector2 = sectorArr2[i2];
                        sector2.textAlpha = 0.0f;
                        if (!z) {
                            sector2.textAlphaAnimated.set(0.0f, true);
                        }
                        i2++;
                    } else {
                        invalidate();
                        return;
                    }
                }
            } else {
                float f10 = 0.0f;
                int i5 = 0;
                int i6 = 0;
                while (i5 < segmentSizeArr2.length) {
                    SegmentSize segmentSize3 = segmentSizeArr2[i5];
                    if (segmentSize3 == null || !segmentSize3.selected) {
                        f5 = f8;
                        f6 = 0.02f;
                        f7 = f5;
                    } else {
                        f5 = f8;
                        f6 = 0.02f;
                        f7 = segmentSize3.size / j3;
                    }
                    if (f7 > f5 && f7 < f6) {
                        i6++;
                        f10 += f7;
                    }
                    i5++;
                    f8 = f5;
                }
                float f11 = f8;
                Math.min(segmentSizeArr2.length, this.sectors.length);
                int[] iArr = this.tempPercents;
                if (iArr == null || iArr.length != segmentSizeArr2.length) {
                    this.tempPercents = new int[segmentSizeArr2.length];
                }
                float[] fArr2 = this.tempFloat;
                if (fArr2 == null || fArr2.length != segmentSizeArr2.length) {
                    this.tempFloat = new float[segmentSizeArr2.length];
                }
                int i7 = 0;
                while (true) {
                    int length2 = segmentSizeArr2.length;
                    fArr = this.tempFloat;
                    if (i7 >= length2) {
                        break;
                    }
                    SegmentSize segmentSize4 = segmentSizeArr2[i7];
                    if (segmentSize4 == null || !segmentSize4.selected) {
                        i = i2;
                        z2 = z3;
                        f4 = f11;
                    } else {
                        i = i2;
                        z2 = z3;
                        f4 = segmentSize4.size / j3;
                    }
                    fArr[i7] = f4;
                    i7++;
                    z3 = z2;
                    i2 = i;
                }
                boolean z4 = i2;
                boolean z5 = z3;
                AndroidUtilities.roundPercents(fArr, this.tempPercents);
                if (this.type == 0) {
                    Arrays.sort(segmentSizeArr2, new Comparator() { // from class: org.telegram.ui.Components.CacheChart$$ExternalSyntheticLambda0
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return Long.compare(((CacheChart.SegmentSize) obj).size, ((CacheChart.SegmentSize) obj2).size);
                        }
                    });
                    int i8 = z4 ? 1 : 0;
                    while (true) {
                        if (i8 > segmentSizeArr2.length) {
                            break;
                        }
                        SegmentSize segmentSize5 = segmentSizeArr2[i8];
                        if (segmentSize5.index == segmentSizeArr2.length - (z5 ? 1 : 0)) {
                            SegmentSize segmentSize6 = segmentSizeArr2[z4 ? 1 : 0];
                            segmentSizeArr2[z4 ? 1 : 0] = segmentSize5;
                            segmentSizeArr2[i8] = segmentSize6;
                            break;
                        }
                        i8++;
                    }
                }
                if (length < 2) {
                    length = z4 ? 1 : 0;
                }
                float f12 = 2.0f;
                float f13 = 360.0f - (length * 2.0f);
                float f14 = f11;
                int i9 = z4 ? 1 : 0;
                int i10 = i9;
                while (i9 < segmentSizeArr2.length) {
                    SegmentSize segmentSize7 = segmentSizeArr2[i9];
                    float f15 = f12;
                    int i11 = segmentSize7.index;
                    if (segmentSize7 == null || !segmentSize7.selected) {
                        f = f14;
                        f2 = f11;
                    } else {
                        f = f14;
                        f2 = segmentSize7.size / j3;
                    }
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                    spannableStringBuilder.append((CharSequence) String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(this.tempPercents[i11])));
                    spannableStringBuilder.append((CharSequence) spannableString);
                    Sector sector3 = this.sectors[i11];
                    float f16 = f11;
                    long j6 = j3;
                    float f17 = (((double) f2) <= 0.05d || f2 >= f9) ? f16 : f9;
                    sector3.textAlpha = f17;
                    sector3.textScale = (f2 < 0.08f || this.tempPercents[i11] >= 100) ? 0.85f : f9;
                    sector3.particlesAlpha = f9;
                    if (!z) {
                        sector3.textAlphaAnimated.set(f17, true);
                        Sector sector4 = this.sectors[i11];
                        sector4.textScaleAnimated.set(sector4.textScale, true);
                        Sector sector5 = this.sectors[i11];
                        sector5.particlesAlphaAnimated.set(sector5.particlesAlpha, true);
                    }
                    Sector sector6 = this.sectors[i11];
                    if (sector6.textAlpha > f16) {
                        sector6.text.setText(spannableStringBuilder, z);
                    }
                    if (f2 >= 0.02f || f2 <= f16) {
                        f9 = 1.0f;
                        f3 = f2 * (1.0f - ((i6 * 0.02f) - f10));
                    } else {
                        f3 = 0.02f;
                        f9 = 1.0f;
                    }
                    float f18 = (f * f13) + (i10 * f15);
                    float f19 = f18 + (f3 * f13);
                    Sector[] sectorArr3 = this.sectors;
                    if (f3 <= f16) {
                        Sector sector7 = sectorArr3[i11];
                        sector7.angleCenter = (f18 + f19) / f15;
                        sector7.angleSize = Math.abs(f19 - f18) / f15;
                        Sector sector8 = this.sectors[i11];
                        sector8.textAlpha = f16;
                        if (!z) {
                            sector8.angleCenterAnimated.set(sector8.angleCenter, true);
                            Sector sector9 = this.sectors[i11];
                            sector9.angleSizeAnimated.set(sector9.angleSize, true);
                            Sector sector10 = this.sectors[i11];
                            sector10.textAlphaAnimated.set(sector10.textAlpha, true);
                        }
                        f14 = f;
                        z5 = true;
                    } else {
                        Sector sector11 = sectorArr3[i11];
                        sector11.angleCenter = (f18 + f19) / f15;
                        sector11.angleSize = Math.abs(f19 - f18) / f15;
                        if (z) {
                            z5 = true;
                        } else {
                            Sector sector12 = this.sectors[i11];
                            z5 = true;
                            sector12.angleCenterAnimated.set(sector12.angleCenter, true);
                            Sector sector13 = this.sectors[i11];
                            sector13.angleSizeAnimated.set(sector13.angleSize, true);
                        }
                        f14 = f + f3;
                        i10++;
                    }
                    i9++;
                    f12 = f15;
                    segmentSizeArr2 = segmentSizeArr;
                    j3 = j6;
                    f11 = 0.0f;
                }
                String[] strArrSplit = AndroidUtilities.formatFileSize(j3, z5, z5).split(" ");
                int length3 = strArrSplit.length;
                String str = _UrlKt.FRAGMENT_ENCODE_SET;
                String str2 = length3 > 0 ? strArrSplit[z4 ? 1 : 0] : _UrlKt.FRAGMENT_ENCODE_SET;
                if (str2.length() >= 4 && j3 < 1073741824) {
                    str2 = str2.split("\\.")[z4 ? 1 : 0];
                }
                this.topText.setText(str2, z);
                AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = this.bottomText;
                if (strArrSplit.length > 1) {
                    str = strArrSplit[1];
                }
                animatedTextDrawable.setText(str, z);
                if (this.completeFloat.get() > 0.0f) {
                    this.topCompleteText.setText(this.topText.getText(), z);
                    this.bottomCompleteText.setText(this.bottomText.getText(), z);
                }
                this.complete = z4;
                if (!z) {
                    this.completeFloat.set(0.0f, true);
                }
                invalidate();
            }
        }
    }

    public static void setCircleBounds(RectF rectF, float f, float f2, float f3) {
        rectF.set(f - f3, f2 - f3, f + f3, f2 + f3);
    }

    public static void setCircleBounds(RectF rectF, double d, double d2, float f) {
        setCircleBounds(rectF, (float) d, (float) d2, f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:234:0x03f9  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x03fd  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0414  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0461  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0554  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dispatchDraw(android.graphics.Canvas r27) {
        /*
            Method dump skipped, instruction units count: 1388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.CacheChart.dispatchDraw(android.graphics.Canvas):void");
    }

    private boolean drawAnimatedText(Canvas canvas, AnimatedTextView.AnimatedTextDrawable animatedTextDrawable, float f, float f2, float f3, float f4) {
        if (f4 <= 0.0f) {
            return false;
        }
        animatedTextDrawable.setAlpha((int) (f4 * 255.0f));
        animatedTextDrawable.setBounds(0, 0, 0, 0);
        canvas.save();
        canvas.translate(f, f2);
        canvas.scale(f3, f3);
        animatedTextDrawable.draw(canvas);
        canvas.restore();
        return animatedTextDrawable.isAnimating();
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int iM1036dp = AndroidUtilities.m1036dp(heightDp());
        int iM1036dp2 = AndroidUtilities.m1036dp(172.0f);
        this.chartMeasureBounds.set((size - iM1036dp2) / 2.0f, (iM1036dp - iM1036dp2) / 2.0f, (size + iM1036dp2) / 2.0f, (iM1036dp2 + iM1036dp) / 2.0f);
        this.completeGradientMatrix.reset();
        this.completeGradientMatrix.setTranslate(this.chartMeasureBounds.left, 0.0f);
        this.completeGradient.setLocalMatrix(this.completeGradientMatrix);
        this.completeTextGradientMatrix.reset();
        Matrix matrix = this.completeTextGradientMatrix;
        RectF rectF = this.chartMeasureBounds;
        matrix.setTranslate(rectF.left, -rectF.centerY());
        this.completeTextGradient.setLocalMatrix(this.completeTextGradientMatrix);
        StarParticlesView.Drawable drawable = this.completeDrawable;
        if (drawable != null) {
            drawable.rect.set(0.0f, 0.0f, AndroidUtilities.m1036dp(140.0f), AndroidUtilities.m1036dp(140.0f));
            this.completeDrawable.rect.offset((getMeasuredWidth() - this.completeDrawable.rect.width()) / 2.0f, (getMeasuredHeight() - this.completeDrawable.rect.height()) / 2.0f);
            this.completeDrawable.rect2.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
            this.completeDrawable.resetPositions();
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(iM1036dp, TLObject.FLAG_30));
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        requestLayout();
    }
}
