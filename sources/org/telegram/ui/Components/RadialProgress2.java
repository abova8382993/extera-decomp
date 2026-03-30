package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import androidx.annotation.Keep;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec;
import com.google.android.material.progressindicator.DeterminateDrawable;
import j$.util.Objects;
import java.util.Locale;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.MediaActionDrawable;

/* JADX INFO: loaded from: classes3.dex */
public class RadialProgress2 {
    private int backgroundStroke;
    private float circleCheckProgress;
    private int circleColor;
    private int circleColorKey;
    private int circleCrossfadeColorKey;
    private float circleCrossfadeColorProgress;
    private Paint circleMiniPaint;
    public Paint circlePaint;
    private int circlePressedColor;
    private int circlePressedColorKey;
    private int circleRadius;
    private boolean drawBackground;
    private boolean drawMiniIcon;
    public int iconColor;
    public int iconColorKey;
    private int iconPressedColor;
    private int iconPressedColorKey;
    public float iconScale;
    private int indicatorColor;
    private int indicatorPressedColor;
    private boolean invertColors;
    private boolean isPressed;
    private boolean isPressedMini;
    private int maxIconSize;
    public MediaActionDrawable mediaActionDrawable;
    private Bitmap miniDrawBitmap;
    private Canvas miniDrawCanvas;
    private float miniIconScale;
    private MediaActionDrawable miniMediaActionDrawable;
    private Paint miniProgressBackgroundPaint;
    private DeterminateDrawable miniProgressDrawable;
    private CircularProgressIndicatorSpec miniProgressSpec;
    private boolean needDrawBackground;
    private float overlayImageAlpha;
    public ImageReceiver overlayImageView;
    private Paint overlayPaint;
    private float overrideAlpha;
    public float overrideCircleAlpha;
    private View parent;
    private int progressColor;
    private DeterminateDrawable progressDrawable;
    public RectF progressRect;
    private CircularProgressIndicatorSpec progressSpec;
    private Theme.ResourcesProvider resourcesProvider;
    protected int style;
    private int trackColor;
    private int trackPressedColor;
    private int waveAmplitude;

    private boolean iconIsCancel(int i) {
        return i == 3 || i == 14 || i == 12 || i == 13 || i == 10;
    }

    public RadialProgress2(View view) {
        this(view, null);
    }

    public RadialProgress2(final View view, Theme.ResourcesProvider resourcesProvider) {
        this.progressRect = new RectF();
        this.progressColor = -1;
        this.overlayPaint = new Paint(1);
        this.circlePaint = new Paint(1);
        this.circleMiniPaint = new Paint(1);
        this.needDrawBackground = false;
        this.invertColors = true;
        this.indicatorColor = -1;
        this.miniIconScale = 1.0f;
        this.circleColorKey = -1;
        this.circleCrossfadeColorKey = -1;
        this.circleCheckProgress = 1.0f;
        this.circlePressedColorKey = -1;
        this.iconColorKey = -1;
        this.iconPressedColorKey = -1;
        this.overrideCircleAlpha = 1.0f;
        this.drawBackground = true;
        this.overrideAlpha = 1.0f;
        this.overlayImageAlpha = 1.0f;
        this.iconScale = 1.0f;
        this.resourcesProvider = resourcesProvider;
        this.miniProgressBackgroundPaint = new Paint(1);
        this.parent = view;
        ImageReceiver imageReceiver = new ImageReceiver(view);
        this.overlayImageView = imageReceiver;
        imageReceiver.setInvalidateAll(true);
        MediaActionDrawable mediaActionDrawable = new MediaActionDrawable();
        this.mediaActionDrawable = mediaActionDrawable;
        Objects.requireNonNull(view);
        mediaActionDrawable.setDelegate(new MediaActionDrawable.MediaActionDrawableDelegate() { // from class: org.telegram.ui.Components.RadialProgress2$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.Components.MediaActionDrawable.MediaActionDrawableDelegate
            public final void invalidate() {
                view.invalidate();
            }
        });
        MediaActionDrawable mediaActionDrawable2 = new MediaActionDrawable();
        this.miniMediaActionDrawable = mediaActionDrawable2;
        mediaActionDrawable2.setDelegate(new MediaActionDrawable.MediaActionDrawableDelegate() { // from class: org.telegram.ui.Components.RadialProgress2$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.Components.MediaActionDrawable.MediaActionDrawableDelegate
            public final void invalidate() {
                view.invalidate();
            }
        });
        this.miniMediaActionDrawable.setMini(true);
        this.miniMediaActionDrawable.setIcon(4, false);
        int iDp = AndroidUtilities.dp(22.0f);
        this.circleRadius = iDp;
        this.overlayImageView.setRoundRadius(iDp);
        this.overlayPaint.setColor(1677721600);
    }

    @Keep
    public void setStyle(int i) {
        if (this.style == i) {
            return;
        }
        this.style = i;
        if (i == 0) {
            this.mediaActionDrawable.drawProgressCircle = true;
        } else if (i == 1 || i == 2) {
            this.mediaActionDrawable.drawProgressCircle = false;
            initMdcDrawables(this.parent.getContext());
            setWavy(i == 2);
        }
    }

    @Keep
    public int getStyle() {
        return this.style;
    }

    public boolean isMaterial3Style() {
        int i = this.style;
        return i == 1 || i == 2;
    }

    @Keep
    public void setSpecValues(int i, int i2, int i3, int i4) {
        if (isMaterial3Style()) {
            CircularProgressIndicatorSpec circularProgressIndicatorSpec = this.progressSpec;
            circularProgressIndicatorSpec.indicatorSize = i;
            circularProgressIndicatorSpec.trackThickness = i2;
            circularProgressIndicatorSpec.trackCornerRadius = i3;
            circularProgressIndicatorSpec.indicatorTrackGapSize = i4;
        }
    }

    @Keep
    public void setMiniSpecValues(int i, int i2, int i3, int i4) {
        if (isMaterial3Style()) {
            CircularProgressIndicatorSpec circularProgressIndicatorSpec = this.miniProgressSpec;
            circularProgressIndicatorSpec.indicatorSize = i;
            circularProgressIndicatorSpec.trackThickness = i2;
            circularProgressIndicatorSpec.trackCornerRadius = i3;
            circularProgressIndicatorSpec.indicatorTrackGapSize = i4;
        }
    }

    private void initMdcDrawables(Context context) {
        this.progressSpec = new CircularProgressIndicatorSpec(context, null);
        this.miniProgressSpec = new CircularProgressIndicatorSpec(context, null);
        setSpecValues(this.circleRadius * 2, AndroidUtilities.dp(3.0f), AndroidUtilities.dp(2.0f), AndroidUtilities.dp(4.0f));
        setMiniSpecValues(AndroidUtilities.dp(24.0f), AndroidUtilities.dp(3.0f), AndroidUtilities.dp(2.0f), AndroidUtilities.dp(2.0f));
        this.progressDrawable = DeterminateDrawable.createCircularDrawable(context, this.progressSpec);
        this.miniProgressDrawable = DeterminateDrawable.createCircularDrawable(context, this.miniProgressSpec);
    }

    @Keep
    public void setWavy(boolean z) {
        if (this.style != 2) {
            return;
        }
        if (z) {
            this.miniProgressSpec.indicatorInset = 0;
            this.progressSpec.indicatorInset = 0;
            setWavyValues(AndroidUtilities.dp(15.0f), AndroidUtilities.dp(1.6f), AndroidUtilities.dp(5.0f), 0.05f);
        } else {
            this.progressSpec.indicatorInset = 0;
            this.miniProgressSpec.indicatorInset = 0;
            setWavyValues(0, 0, 0, 1.0f);
        }
    }

    public void setWavyValues(int i, int i2, int i3, float f) {
        if (this.style != 2) {
            return;
        }
        CircularProgressIndicatorSpec circularProgressIndicatorSpec = this.progressSpec;
        circularProgressIndicatorSpec.wavelengthDeterminate = i;
        this.waveAmplitude = i2;
        circularProgressIndicatorSpec.waveAmplitude = i2;
        circularProgressIndicatorSpec.waveSpeed = i3;
        circularProgressIndicatorSpec.waveAmplitudeRampProgressMin = f;
        invalidateParent();
    }

    public void setNeedDrawBackground(boolean z) {
        this.needDrawBackground = z;
    }

    @Keep
    public boolean isNeedDrawBackground() {
        return this.needDrawBackground;
    }

    public void setInvertColors(boolean z) {
        this.invertColors = z;
    }

    @Keep
    public boolean isInvertColors() {
        return this.invertColors;
    }

    public void setResourcesProvider(Theme.ResourcesProvider resourcesProvider) {
        this.resourcesProvider = resourcesProvider;
    }

    @Keep
    public void setAsMini() {
        this.mediaActionDrawable.setMini(true);
    }

    @Keep
    public void setCircleRadius(int i) {
        this.circleRadius = i;
        this.overlayImageView.setRoundRadius(i);
        if (isMaterial3Style()) {
            this.progressSpec.indicatorSize = this.circleRadius * 2;
        }
    }

    public int getRadius() {
        return this.circleRadius;
    }

    public void setBackgroundDrawable(Theme.MessageDrawable messageDrawable) {
        this.mediaActionDrawable.setBackgroundDrawable(messageDrawable);
        this.miniMediaActionDrawable.setBackgroundDrawable(messageDrawable);
    }

    @Keep
    public void setBackgroundGradientDrawable(LinearGradient linearGradient) {
        this.mediaActionDrawable.setBackgroundGradientDrawable(linearGradient);
        this.miniMediaActionDrawable.setBackgroundGradientDrawable(linearGradient);
    }

    public void setImageOverlay(TLRPC.PhotoSize photoSize, TLRPC.Document document, Object obj) {
        this.overlayImageView.setImage(ImageLocation.getForDocument(photoSize, document), String.format(Locale.US, "%d_%d", Integer.valueOf(this.circleRadius * 2), Integer.valueOf(this.circleRadius * 2)), null, null, obj, 1);
    }

    public void setImageOverlay(TLRPC.PhotoSize photoSize, TLRPC.PhotoSize photoSize2, TLRPC.Document document, Object obj) {
        String str = String.format(Locale.US, "%d_%d", Integer.valueOf(this.circleRadius * 2), Integer.valueOf(this.circleRadius * 2));
        this.overlayImageView.setImage(photoSize == null ? null : ImageLocation.getForDocument(photoSize, document), str, photoSize2 != null ? ImageLocation.getForDocument(photoSize2, document) : null, str, null, 0L, null, obj, 1);
    }

    public void setImageOverlay(String str) {
        this.overlayImageView.setImage(str, str != null ? String.format(Locale.US, "%d_%d", Integer.valueOf(this.circleRadius * 2), Integer.valueOf(this.circleRadius * 2)) : null, null, null, -1L);
    }

    public void onAttachedToWindow() {
        this.overlayImageView.onAttachedToWindow();
    }

    public void onDetachedFromWindow() {
        this.overlayImageView.onDetachedFromWindow();
    }

    public void setColorKeys(int i, int i2, int i3, int i4) {
        this.circleColorKey = i;
        this.circlePressedColorKey = i2;
        this.iconColorKey = i3;
        this.iconPressedColorKey = i4;
    }

    @Keep
    public void setM3Colors(int i, int i2, int i3, int i4) {
        this.indicatorColor = i;
        this.indicatorPressedColor = i2;
        this.trackColor = i3;
        this.trackPressedColor = i4;
    }

    @Keep
    public void setM3Colors(int i, int i2) {
        setM3Colors(i, i, i2, i2);
    }

    @Keep
    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    @Keep
    public int getTrackColor() {
        return this.trackColor;
    }

    @Keep
    public int getIndicatorPressedColor() {
        return this.indicatorPressedColor;
    }

    @Keep
    public int getTrackPressedColor() {
        return this.trackPressedColor;
    }

    @Keep
    public void setColors(int i, int i2, int i3, int i4) {
        this.circleColor = i;
        this.circlePressedColor = i2;
        this.iconColor = i3;
        this.iconPressedColor = i4;
        this.circleColorKey = -1;
        this.circlePressedColorKey = -1;
        this.iconColorKey = -1;
        this.iconPressedColorKey = -1;
    }

    public void setCircleCrossfadeColor(int i, float f, float f2) {
        this.circleCrossfadeColorKey = i;
        this.circleCrossfadeColorProgress = f;
        this.circleCheckProgress = f2;
        this.miniIconScale = 1.0f;
        if (i >= 0) {
            initMiniIcons();
        }
    }

    public void setDrawBackground(boolean z) {
        this.drawBackground = z;
    }

    public void setProgressRect(int i, int i2, int i3, int i4) {
        this.progressRect.set(i, i2, i3, i4);
    }

    public void setProgressRect(float f, float f2, float f3, float f4) {
        this.progressRect.set(f, f2, f3, f4);
    }

    public RectF getProgressRect() {
        return this.progressRect;
    }

    public void setProgressColor(int i) {
        this.progressColor = i;
    }

    public void setMiniProgressBackgroundColor(int i) {
        this.miniProgressBackgroundPaint.setColor(i);
    }

    public void setProgress(float f, boolean z) {
        int i = (int) (10000.0f * f);
        if (this.drawMiniIcon) {
            this.miniMediaActionDrawable.setProgress(f, z);
            if (isMaterial3Style()) {
                this.miniProgressDrawable.setLevel(i);
                return;
            }
            return;
        }
        this.mediaActionDrawable.setProgress(f, z);
        if (isMaterial3Style()) {
            this.progressDrawable.setLevel(i);
        }
    }

    public float getProgress() {
        return (this.drawMiniIcon ? this.miniMediaActionDrawable : this.mediaActionDrawable).getProgress();
    }

    private void invalidateParent() {
        int iDp = AndroidUtilities.dp(2.0f);
        View view = this.parent;
        RectF rectF = this.progressRect;
        int i = ((int) rectF.left) - iDp;
        int i2 = ((int) rectF.top) - iDp;
        int i3 = iDp * 2;
        view.invalidate(i, i2, ((int) rectF.right) + i3, ((int) rectF.bottom) + i3);
    }

    public int getIcon() {
        return this.mediaActionDrawable.getCurrentIcon();
    }

    public int getMiniIcon() {
        return this.miniMediaActionDrawable.getCurrentIcon();
    }

    @Keep
    public void setIcon(int i, boolean z, boolean z2) {
        if (z && i == this.mediaActionDrawable.getCurrentIcon()) {
            return;
        }
        this.mediaActionDrawable.setIcon(i, z2);
        if (!z2) {
            this.parent.invalidate();
        } else {
            invalidateParent();
        }
    }

    public void setMiniIconScale(float f) {
        this.miniIconScale = f;
    }

    public void setMiniIcon(int i, boolean z, boolean z2) {
        if (i == 2 || i == 3 || i == 4) {
            if (z && i == this.miniMediaActionDrawable.getCurrentIcon()) {
                return;
            }
            this.miniMediaActionDrawable.setIcon(i, z2);
            boolean z3 = i != 4 || this.miniMediaActionDrawable.getTransitionProgress() < 1.0f;
            this.drawMiniIcon = z3;
            if (z3) {
                initMiniIcons();
            }
            if (!z2) {
                this.parent.invalidate();
            } else {
                invalidateParent();
            }
        }
    }

    public void initMiniIcons() {
        if (this.miniDrawBitmap == null) {
            try {
                this.miniDrawBitmap = Bitmap.createBitmap(AndroidUtilities.dp(48.0f), AndroidUtilities.dp(48.0f), Bitmap.Config.ARGB_8888);
                this.miniDrawCanvas = new Canvas(this.miniDrawBitmap);
            } catch (Throwable unused) {
            }
        }
    }

    public void setPressed(boolean z, boolean z2) {
        if (z2) {
            this.isPressedMini = z;
        } else {
            this.isPressed = z;
        }
        invalidateParent();
    }

    public void setOverrideAlpha(float f) {
        this.overrideAlpha = f;
    }

    public float getOverrideAlpha() {
        return this.overrideAlpha;
    }

    @Keep
    public void draw(Canvas canvas) {
        float transitionProgress;
        float transitionProgress2;
        int i;
        int iCeil;
        int iCeil2;
        boolean z;
        boolean z2;
        int iSave;
        float f;
        float f2;
        int i2;
        Canvas canvas2;
        float fCenterX;
        float fCenterY;
        int i3;
        float transitionProgress3;
        int iSave2;
        Canvas canvas3;
        Canvas canvas4;
        int i4;
        boolean z3;
        if ((this.mediaActionDrawable.getCurrentIcon() != 4 || this.mediaActionDrawable.getTransitionProgress() < 1.0f) && !this.progressRect.isEmpty()) {
            int currentIcon = this.mediaActionDrawable.getCurrentIcon();
            int previousIcon = this.mediaActionDrawable.getPreviousIcon();
            boolean zIsMaterial3Style = isMaterial3Style();
            boolean z4 = iconIsCancel(currentIcon) || currentIcon == 2;
            boolean z5 = this.needDrawBackground || (this.drawBackground && (!z4 || this.overlayImageView.hasBitmapImage()));
            if (this.backgroundStroke != 0) {
                if (currentIcon == 3) {
                    transitionProgress2 = this.mediaActionDrawable.getTransitionProgress();
                    transitionProgress = 1.0f - transitionProgress2;
                } else {
                    transitionProgress = previousIcon == 3 ? this.mediaActionDrawable.getTransitionProgress() : 1.0f;
                }
            } else if ((currentIcon == 3 || currentIcon == 6 || currentIcon == 10 || currentIcon == 8 || currentIcon == 0) && previousIcon == 4) {
                transitionProgress = this.mediaActionDrawable.getTransitionProgress();
            } else {
                if (currentIcon == 4) {
                    transitionProgress2 = this.mediaActionDrawable.getTransitionProgress();
                    transitionProgress = 1.0f - transitionProgress2;
                }
            }
            int i5 = this.circleColorKey;
            int themedColor = i5 >= 0 ? getThemedColor(i5) : this.circleColor;
            int i6 = this.circlePressedColorKey;
            int themedColor2 = i6 >= 0 ? getThemedColor(i6) : this.circlePressedColor;
            int i7 = this.iconColorKey;
            int themedColor3 = i7 >= 0 ? getThemedColor(i7) : this.iconColor;
            int i8 = this.iconPressedColorKey;
            int themedColor4 = i8 >= 0 ? getThemedColor(i8) : this.iconPressedColor;
            if (this.isPressedMini && this.circleCrossfadeColorKey < 0) {
                this.miniMediaActionDrawable.setColor(themedColor4);
                this.circleMiniPaint.setColor(themedColor2);
                i = 2;
            } else {
                this.miniMediaActionDrawable.setColor(themedColor3);
                int i9 = this.circleCrossfadeColorKey;
                if (i9 >= 0) {
                    i = 2;
                    this.circleMiniPaint.setColor(AndroidUtilities.getOffsetColor(themedColor, getThemedColor(i9), this.circleCrossfadeColorProgress, this.circleCheckProgress));
                } else {
                    i = 2;
                    this.circleMiniPaint.setColor(themedColor);
                }
            }
            int iArgb = -1;
            if (this.isPressed) {
                this.circlePaint.setColor(themedColor2);
                this.mediaActionDrawable.setColor(themedColor4);
                this.mediaActionDrawable.setBackColor(themedColor2);
                if (zIsMaterial3Style) {
                    if (this.indicatorColor != -1) {
                        this.mediaActionDrawable.setColor(this.indicatorPressedColor);
                        CircularProgressIndicatorSpec circularProgressIndicatorSpec = this.progressSpec;
                        int i10 = this.indicatorPressedColor;
                        circularProgressIndicatorSpec.indicatorColors = new int[]{i10};
                        this.miniProgressSpec.indicatorColors = new int[]{i10};
                        circularProgressIndicatorSpec.trackColor = this.trackPressedColor;
                    } else if (!z5 && this.invertColors) {
                        this.mediaActionDrawable.setColor(themedColor2);
                        this.mediaActionDrawable.setBackColor(themedColor4);
                        CircularProgressIndicatorSpec circularProgressIndicatorSpec2 = this.progressSpec;
                        circularProgressIndicatorSpec2.indicatorColors = new int[]{themedColor2};
                        this.miniProgressSpec.indicatorColors = new int[]{themedColor2};
                        circularProgressIndicatorSpec2.trackColor = ColorUtils.setAlphaComponent(themedColor2, (int) ((Theme.isCurrentThemeDark() ? 0.65f : 0.8f) * 255.0f));
                    } else {
                        CircularProgressIndicatorSpec circularProgressIndicatorSpec3 = this.progressSpec;
                        circularProgressIndicatorSpec3.indicatorColors = new int[]{themedColor4};
                        this.miniProgressSpec.indicatorColors = new int[]{themedColor4};
                        circularProgressIndicatorSpec3.trackColor = ColorUtils.setAlphaComponent(themedColor4, (int) ((Theme.isCurrentThemeDark() ? 0.65f : 0.8f) * 255.0f));
                    }
                    this.miniProgressSpec.trackColor = this.progressSpec.trackColor;
                }
                themedColor = themedColor2;
            } else {
                this.circlePaint.setColor(themedColor);
                this.mediaActionDrawable.setColor(themedColor3);
                this.mediaActionDrawable.setBackColor(themedColor);
                if (zIsMaterial3Style) {
                    int i11 = this.indicatorColor;
                    if (i11 != -1) {
                        this.mediaActionDrawable.setColor(i11);
                        CircularProgressIndicatorSpec circularProgressIndicatorSpec4 = this.progressSpec;
                        int i12 = this.indicatorColor;
                        circularProgressIndicatorSpec4.indicatorColors = new int[]{i12};
                        this.miniProgressSpec.indicatorColors = new int[]{i12};
                        circularProgressIndicatorSpec4.trackColor = this.trackColor;
                    } else if (!z5 && this.invertColors) {
                        this.mediaActionDrawable.setColor(themedColor);
                        this.mediaActionDrawable.setBackColor(themedColor3);
                        CircularProgressIndicatorSpec circularProgressIndicatorSpec5 = this.progressSpec;
                        circularProgressIndicatorSpec5.indicatorColors = new int[]{themedColor};
                        this.miniProgressSpec.indicatorColors = new int[]{themedColor};
                        circularProgressIndicatorSpec5.trackColor = ColorUtils.setAlphaComponent(themedColor, (int) ((Theme.isCurrentThemeDark() ? 0.65f : 0.8f) * 255.0f));
                    } else {
                        CircularProgressIndicatorSpec circularProgressIndicatorSpec6 = this.progressSpec;
                        circularProgressIndicatorSpec6.indicatorColors = new int[]{themedColor3};
                        this.miniProgressSpec.indicatorColors = new int[]{themedColor3};
                        circularProgressIndicatorSpec6.trackColor = ColorUtils.setAlphaComponent(themedColor3, (int) ((Theme.isCurrentThemeDark() ? 0.65f : 0.8f) * 255.0f));
                    }
                    this.miniProgressSpec.trackColor = this.progressSpec.trackColor;
                }
            }
            if ((this.drawMiniIcon || this.circleCrossfadeColorKey >= 0) && this.miniDrawCanvas != null) {
                this.miniDrawBitmap.eraseColor(0);
            }
            this.circlePaint.setAlpha((int) (this.circlePaint.getAlpha() * transitionProgress * this.overrideAlpha * this.overrideCircleAlpha));
            this.circleMiniPaint.setAlpha((int) (this.circleMiniPaint.getAlpha() * transitionProgress * this.overrideAlpha));
            if ((this.drawMiniIcon || this.circleCrossfadeColorKey >= 0) && this.miniDrawCanvas != null) {
                iCeil = (int) Math.ceil(this.progressRect.width() / 2.0f);
                iCeil2 = (int) Math.ceil(this.progressRect.height() / 2.0f);
            } else {
                iCeil = (int) this.progressRect.centerX();
                iCeil2 = (int) this.progressRect.centerY();
            }
            if (this.overlayImageView.hasBitmapImage()) {
                float currentAlpha = this.overlayImageView.getCurrentAlpha();
                this.overlayPaint.setAlpha((int) (100.0f * currentAlpha * transitionProgress * this.overrideAlpha));
                if (currentAlpha >= 1.0f) {
                    z = zIsMaterial3Style;
                    z3 = false;
                } else {
                    int iRed = Color.red(themedColor);
                    int iGreen = Color.green(themedColor);
                    int iBlue = Color.blue(themedColor);
                    z = zIsMaterial3Style;
                    iArgb = Color.argb(Color.alpha(themedColor) + ((int) ((255 - r13) * currentAlpha)), iRed + ((int) ((255 - iRed) * currentAlpha)), iGreen + ((int) ((255 - iGreen) * currentAlpha)), iBlue + ((int) ((255 - iBlue) * currentAlpha)));
                    z3 = true;
                }
                this.mediaActionDrawable.setColor(iArgb);
                ImageReceiver imageReceiver = this.overlayImageView;
                int i13 = this.circleRadius;
                imageReceiver.setImageCoords(iCeil - i13, iCeil2 - i13, i13 * 2, i13 * 2);
                z2 = z3;
            } else {
                z = zIsMaterial3Style;
                z2 = true;
            }
            Canvas canvas5 = this.miniDrawCanvas;
            if (canvas5 == null || this.circleCrossfadeColorKey < 0 || this.circleCheckProgress == 1.0f) {
                iSave = Integer.MIN_VALUE;
            } else {
                iSave = canvas5.save();
                float f3 = 1.0f - ((1.0f - this.circleCheckProgress) * 0.1f);
                this.miniDrawCanvas.scale(f3, f3, iCeil, iCeil2);
            }
            if (z2 && z5) {
                if (this.drawMiniIcon || this.circleCrossfadeColorKey >= 0) {
                    Canvas canvas6 = this.miniDrawCanvas;
                    if (canvas6 != null) {
                        f = 3.5f;
                        f2 = 0.0f;
                        canvas6.drawCircle(iCeil, iCeil2, this.circleRadius, this.circlePaint);
                    } else {
                        f = 3.5f;
                        f2 = 0.0f;
                        i4 = 4;
                    }
                } else {
                    i4 = 4;
                    f = 3.5f;
                    f2 = 0.0f;
                }
                if (currentIcon != i4 || transitionProgress != f2) {
                    if (this.backgroundStroke != 0) {
                        canvas.drawCircle(iCeil, iCeil2, this.circleRadius - AndroidUtilities.dp(f), this.circlePaint);
                    } else {
                        canvas.drawCircle(iCeil, iCeil2, this.circleRadius, this.circlePaint);
                    }
                }
            } else {
                f = 3.5f;
                f2 = 0.0f;
                z5 = false;
            }
            if (this.overlayImageView.hasBitmapImage()) {
                this.overlayImageView.setAlpha(this.overrideAlpha * transitionProgress * this.overlayImageAlpha);
                if ((this.drawMiniIcon || this.circleCrossfadeColorKey >= 0) && (canvas4 = this.miniDrawCanvas) != null) {
                    this.overlayImageView.draw(canvas4);
                    this.miniDrawCanvas.drawCircle(iCeil, iCeil2, this.circleRadius, this.overlayPaint);
                } else {
                    this.overlayImageView.draw(canvas);
                    canvas.drawCircle(iCeil, iCeil2, this.circleRadius, this.overlayPaint);
                }
            }
            int i14 = this.circleRadius;
            int i15 = this.maxIconSize;
            if (i15 > 0 && i14 > i15) {
                i14 = i15;
            }
            if (this.iconScale != 1.0f) {
                canvas.save();
                float f4 = this.iconScale;
                canvas.scale(f4, f4, iCeil, iCeil2);
            }
            if (z) {
                i2 = i;
                if (this.style == i2) {
                    if (!z5) {
                        this.progressSpec.waveAmplitude = (int) (this.waveAmplitude * getWaveScale(currentIcon, previousIcon));
                    } else {
                        CircularProgressIndicatorSpec circularProgressIndicatorSpec7 = this.progressSpec;
                        if (circularProgressIndicatorSpec7.waveAmplitude != 0) {
                            circularProgressIndicatorSpec7.waveAmplitude = 0;
                        }
                    }
                }
                this.progressDrawable.setBounds(iCeil - i14, iCeil2 - i14, iCeil + i14, iCeil2 + i14);
                this.progressDrawable.setAlpha((int) (transitionProgress * 255.0f * this.overrideAlpha));
            } else {
                i2 = i;
            }
            this.mediaActionDrawable.setBounds(iCeil - i14, iCeil2 - i14, iCeil + i14, i14 + iCeil2);
            this.mediaActionDrawable.setHasOverlayImage(this.overlayImageView.hasBitmapImage());
            boolean z6 = this.drawMiniIcon;
            if ((!z6 && this.circleCrossfadeColorKey < 0) || (canvas2 = this.miniDrawCanvas) == null) {
                canvas2 = canvas;
            }
            if (z6 || this.circleCrossfadeColorKey >= 0) {
                Canvas canvas7 = this.miniDrawCanvas;
                if (canvas7 != null) {
                    this.mediaActionDrawable.draw(canvas7);
                } else {
                    this.mediaActionDrawable.draw(canvas);
                }
            } else {
                this.mediaActionDrawable.setOverrideAlpha(this.overrideAlpha);
                this.mediaActionDrawable.draw(canvas);
            }
            if (z && z4) {
                float fDp = z5 ? (this.circleRadius - AndroidUtilities.dp(f)) / this.circleRadius : 1.0f;
                if (fDp != 1.0f) {
                    canvas2.save();
                    canvas2.scale(fDp, fDp, iCeil, iCeil2);
                }
                this.progressDrawable.draw(canvas2);
                if (fDp != 1.0f) {
                    canvas2.restore();
                }
            }
            if (iSave != Integer.MIN_VALUE && (canvas3 = this.miniDrawCanvas) != null) {
                canvas3.restoreToCount(iSave);
            }
            if (this.drawMiniIcon || this.circleCrossfadeColorKey >= 0) {
                if (Math.abs(this.progressRect.width() - AndroidUtilities.dp(44.0f)) < AndroidUtilities.density) {
                    float f5 = 16;
                    fCenterX = this.progressRect.centerX() + AndroidUtilities.dp(f5);
                    fCenterY = this.progressRect.centerY() + AndroidUtilities.dp(f5);
                    i3 = 20;
                    i2 = 0;
                } else {
                    fCenterX = this.progressRect.centerX() + AndroidUtilities.dp(18.0f);
                    fCenterY = this.progressRect.centerY() + AndroidUtilities.dp(18.0f);
                    i3 = 22;
                }
                int i16 = i3 / 2;
                if (this.drawMiniIcon) {
                    transitionProgress3 = this.miniMediaActionDrawable.getCurrentIcon() != 4 ? 1.0f : 1.0f - this.miniMediaActionDrawable.getTransitionProgress();
                    if (transitionProgress3 == f2) {
                        this.drawMiniIcon = false;
                    }
                } else {
                    transitionProgress3 = 1.0f;
                }
                Canvas canvas8 = this.miniDrawCanvas;
                if (canvas8 != null) {
                    float f6 = i3 + 18 + i2;
                    canvas8.drawCircle(AndroidUtilities.dp(f6), AndroidUtilities.dp(f6), AndroidUtilities.dp(i16 + 1) * transitionProgress3 * this.miniIconScale, Theme.checkboxSquare_eraserPaint);
                } else {
                    this.miniProgressBackgroundPaint.setColor(this.progressColor);
                    canvas.drawCircle(fCenterX, fCenterY, AndroidUtilities.dp(12.0f), this.miniProgressBackgroundPaint);
                }
                if (this.miniDrawCanvas != null) {
                    Bitmap bitmap = this.miniDrawBitmap;
                    RectF rectF = this.progressRect;
                    canvas.drawBitmap(bitmap, (int) rectF.left, (int) rectF.top, (Paint) null);
                }
                if (this.miniIconScale < 1.0f) {
                    iSave2 = canvas.save();
                    float f7 = this.miniIconScale;
                    canvas.scale(f7, f7, fCenterX, fCenterY);
                } else {
                    iSave2 = Integer.MIN_VALUE;
                }
                float f8 = i16;
                canvas.drawCircle(fCenterX, fCenterY, (AndroidUtilities.dp(f8) * transitionProgress3) + (AndroidUtilities.dp(1.0f) * (1.0f - this.circleCheckProgress)), this.circleMiniPaint);
                if (this.drawMiniIcon) {
                    this.miniMediaActionDrawable.setBounds((int) (fCenterX - (AndroidUtilities.dp(f8) * transitionProgress3)), (int) (fCenterY - (AndroidUtilities.dp(f8) * transitionProgress3)), (int) ((AndroidUtilities.dp(f8) * transitionProgress3) + fCenterX), (int) ((AndroidUtilities.dp(f8) * transitionProgress3) + fCenterY));
                    this.miniMediaActionDrawable.draw(canvas);
                    if (z) {
                        this.miniProgressDrawable.setBounds((int) (fCenterX - (AndroidUtilities.dp(f8) * transitionProgress3)), (int) (fCenterY - (AndroidUtilities.dp(f8) * transitionProgress3)), (int) (fCenterX + (AndroidUtilities.dp(f8) * transitionProgress3)), (int) (fCenterY + (AndroidUtilities.dp(f8) * transitionProgress3)));
                        this.miniProgressDrawable.setAlpha((int) (transitionProgress3 * 255.0f));
                        if (z4) {
                            this.miniProgressDrawable.draw(canvas);
                        }
                    }
                }
                if (iSave2 != Integer.MIN_VALUE) {
                    canvas.restoreToCount(iSave2);
                }
            }
            if (this.iconScale != 1.0f) {
                canvas.restore();
            }
        }
    }

    private float getWaveScale(int i, int i2) {
        float f = iconIsCancel(i) ? 1.0f : 0.0f;
        float f2 = iconIsCancel(i2) ? 1.0f : 0.0f;
        return f2 + ((f - f2) * this.mediaActionDrawable.getTransitionProgress());
    }

    public int getCircleColorKey() {
        return this.circleColorKey;
    }

    private int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    public void setMaxIconSize(int i) {
        this.maxIconSize = i;
    }

    public void setOverlayImageAlpha(float f) {
        this.overlayImageAlpha = f;
    }

    public float getTransitionProgress() {
        return (this.drawMiniIcon ? this.miniMediaActionDrawable : this.mediaActionDrawable).getTransitionProgress();
    }
}
