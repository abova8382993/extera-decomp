package org.telegram.p035ui.Components.Paint.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.Components.AnimatedEmojiSpan;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.Paint.PaintTypeface;
import org.telegram.p035ui.Components.Paint.Swatch;
import org.telegram.p035ui.Components.Paint.Views.EntityView;
import org.telegram.p035ui.Components.RectOld;

/* JADX INFO: loaded from: classes7.dex */
public class TextPaintView extends EntityView {
    private int align;
    private int baseFontSize;
    private int currentType;
    private boolean disableAutoresize;
    private EditTextOutline editText;
    private String lastTypefaceKey;
    private int maxFontSize;
    private int minFontSize;
    private Runnable onFontChange;
    private Swatch swatch;
    private PaintTypeface typeface;

    public TextPaintView(Context context, PointF pointF, int i, CharSequence charSequence, Swatch swatch, int i2) {
        super(context, pointF);
        this.typeface = PaintTypeface.ROBOTO_MEDIUM;
        this.baseFontSize = i;
        C46331 c46331 = new EditTextOutline(context) { // from class: org.telegram.ui.Components.Paint.Views.TextPaintView.1
            public C46331(Context context2) {
                super(context2);
            }

            @Override // org.telegram.p035ui.Components.EditTextEffects, android.view.View
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                EntityView.SelectionView selectionView = TextPaintView.this.selectionView;
                if (selectionView == null || selectionView.getVisibility() != 0) {
                    return false;
                }
                return super.dispatchTouchEvent(motionEvent);
            }

            @Override // org.telegram.p035ui.Components.EditTextEffects, android.widget.TextView, android.view.View
            public void onLayout(boolean z, int i3, int i4, int i5, int i6) {
                super.onLayout(z, i3, i4, i5, i6);
                TextPaintView.this.updateSelectionView();
            }

            @Override // org.telegram.p035ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
            public void onMeasure(int i3, int i4) {
                super.onMeasure(i3, i4);
                TextPaintView.this.updateSelectionView();
            }
        };
        this.editText = c46331;
        NotificationCenter.listenEmojiLoading(c46331);
        this.editText.setGravity(19);
        this.editText.setBackgroundColor(0);
        this.editText.setPadding(AndroidUtilities.m1036dp(7.0f), AndroidUtilities.m1036dp(7.0f), AndroidUtilities.m1036dp(7.0f), AndroidUtilities.m1036dp(7.0f));
        this.editText.setClickable(false);
        this.editText.setEnabled(false);
        this.editText.setCursorColor(-1);
        this.editText.setTextSize(0, this.baseFontSize);
        this.editText.setCursorSize(AndroidUtilities.m1036dp(this.baseFontSize * 0.4f));
        this.editText.setText(charSequence);
        updateHint();
        this.editText.setTextColor(swatch.color);
        this.editText.setTypeface(null, 1);
        this.editText.setHorizontallyScrolling(false);
        int i3 = Build.VERSION.SDK_INT;
        EditTextOutline editTextOutline = this.editText;
        if (i3 >= 26) {
            editTextOutline.setImeOptions(285212672);
        } else {
            editTextOutline.setImeOptions(268435456);
        }
        this.editText.setFocusableInTouchMode(true);
        this.editText.setInputType(16384);
        this.editText.setSingleLine(false);
        addView(this.editText, LayoutHelper.createFrame(-2, -2, 51));
        EditTextOutline editTextOutline2 = this.editText;
        if (i3 >= 29) {
            editTextOutline2.setBreakStrategy(0);
        } else {
            editTextOutline2.setBreakStrategy(0);
        }
        setSwatch(swatch);
        setType(i2);
        updatePosition();
        this.editText.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.Components.Paint.Views.TextPaintView.2
            boolean pasted;

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence2, int i4, int i5, int i6) {
            }

            public C46342() {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence2, int i4, int i5, int i6) {
                this.pasted = i6 > 3;
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                int iClamp;
                if (this.pasted && TextPaintView.this.minFontSize > 0 && TextPaintView.this.maxFontSize > 0 && !TextPaintView.this.disableAutoresize && TextPaintView.this.editText.getLayout() != null) {
                    float f = AndroidUtilities.displaySize.y / 3.0f;
                    float height = TextPaintView.this.editText.getLayout().getHeight();
                    if (height > f && (iClamp = Utilities.clamp((int) ((f / height) * TextPaintView.this.getBaseFontSize()), TextPaintView.this.maxFontSize, TextPaintView.this.minFontSize)) != TextPaintView.this.getBaseFontSize()) {
                        TextPaintView.this.setBaseFontSize(iClamp);
                        if (TextPaintView.this.onFontChange != null) {
                            TextPaintView.this.onFontChange.run();
                        }
                    }
                }
                TextPaintView.this.updateHint();
            }
        });
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Paint.Views.TextPaintView$1 */
    public class C46331 extends EditTextOutline {
        public C46331(Context context2) {
            super(context2);
        }

        @Override // org.telegram.p035ui.Components.EditTextEffects, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            EntityView.SelectionView selectionView = TextPaintView.this.selectionView;
            if (selectionView == null || selectionView.getVisibility() != 0) {
                return false;
            }
            return super.dispatchTouchEvent(motionEvent);
        }

        @Override // org.telegram.p035ui.Components.EditTextEffects, android.widget.TextView, android.view.View
        public void onLayout(boolean z, int i3, int i4, int i5, int i6) {
            super.onLayout(z, i3, i4, i5, i6);
            TextPaintView.this.updateSelectionView();
        }

        @Override // org.telegram.p035ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
        public void onMeasure(int i3, int i4) {
            super.onMeasure(i3, i4);
            TextPaintView.this.updateSelectionView();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Paint.Views.TextPaintView$2 */
    public class C46342 implements TextWatcher {
        boolean pasted;

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence2, int i4, int i5, int i6) {
        }

        public C46342() {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence2, int i4, int i5, int i6) {
            this.pasted = i6 > 3;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            int iClamp;
            if (this.pasted && TextPaintView.this.minFontSize > 0 && TextPaintView.this.maxFontSize > 0 && !TextPaintView.this.disableAutoresize && TextPaintView.this.editText.getLayout() != null) {
                float f = AndroidUtilities.displaySize.y / 3.0f;
                float height = TextPaintView.this.editText.getLayout().getHeight();
                if (height > f && (iClamp = Utilities.clamp((int) ((f / height) * TextPaintView.this.getBaseFontSize()), TextPaintView.this.maxFontSize, TextPaintView.this.minFontSize)) != TextPaintView.this.getBaseFontSize()) {
                    TextPaintView.this.setBaseFontSize(iClamp);
                    if (TextPaintView.this.onFontChange != null) {
                        TextPaintView.this.onFontChange.run();
                    }
                }
            }
            TextPaintView.this.updateHint();
        }
    }

    @Override // org.telegram.p035ui.Components.Paint.Views.EntityView
    public float getStickyPaddingLeft() {
        RectF rectF = this.editText.framePadding;
        if (rectF == null) {
            return 0.0f;
        }
        return rectF.left;
    }

    @Override // org.telegram.p035ui.Components.Paint.Views.EntityView
    public float getStickyPaddingRight() {
        RectF rectF = this.editText.framePadding;
        if (rectF == null) {
            return 0.0f;
        }
        return rectF.right;
    }

    @Override // org.telegram.p035ui.Components.Paint.Views.EntityView
    public float getStickyPaddingTop() {
        RectF rectF = this.editText.framePadding;
        if (rectF == null) {
            return 0.0f;
        }
        return rectF.top;
    }

    @Override // org.telegram.p035ui.Components.Paint.Views.EntityView
    public float getStickyPaddingBottom() {
        RectF rectF = this.editText.framePadding;
        if (rectF == null) {
            return 0.0f;
        }
        return rectF.bottom;
    }

    public void updateHint() {
        int length = this.editText.getText().length();
        EditTextOutline editTextOutline = this.editText;
        if (length <= 0) {
            editTextOutline.setHint(LocaleController.getString(C2797R.string.TextPlaceholder));
            this.editText.setHintTextColor(1627389951);
        } else {
            editTextOutline.setHint((CharSequence) null);
        }
    }

    public TextPaintView(Context context, TextPaintView textPaintView, PointF pointF) {
        this(context, pointF, textPaintView.baseFontSize, textPaintView.getText(), textPaintView.getSwatch(), textPaintView.currentType);
        setRotation(textPaintView.getRotation());
        setScale(textPaintView.getScale());
        setTypeface(textPaintView.getTypeface());
        setAlign(textPaintView.getAlign());
        int align = getAlign();
        int i = 2;
        this.editText.setGravity(align != 1 ? align != 2 ? 19 : 21 : 17);
        int align2 = getAlign();
        if (align2 == 1) {
            i = 4;
        } else if (align2 == 2 ? !LocaleController.isRTL : LocaleController.isRTL) {
            i = 3;
        }
        this.editText.setTextAlignment(i);
    }

    public int getBaseFontSize() {
        return this.baseFontSize;
    }

    public void setMinMaxFontSize(int i, int i2, Runnable runnable) {
        this.minFontSize = i;
        this.maxFontSize = i2;
        this.onFontChange = runnable;
    }

    public void disableAutoresize(boolean z) {
        this.disableAutoresize = z;
    }

    public void setBaseFontSize(int i) {
        this.baseFontSize = i;
        float f = i;
        this.editText.setTextSize(0, f);
        this.editText.setCursorSize(AndroidUtilities.m1036dp(f * 0.4f));
        if (this.editText.getText() != null) {
            Editable text = this.editText.getText();
            Emoji.EmojiSpan[] emojiSpanArr = (Emoji.EmojiSpan[]) text.getSpans(0, text.length(), Emoji.EmojiSpan.class);
            for (int i2 = 0; i2 < emojiSpanArr.length; i2++) {
                emojiSpanArr[i2].replaceFontMetrics(getFontMetricsInt());
                emojiSpanArr[i2].scale = 0.85f;
            }
            for (AnimatedEmojiSpan animatedEmojiSpan : (AnimatedEmojiSpan[]) text.getSpans(0, text.length(), AnimatedEmojiSpan.class)) {
                animatedEmojiSpan.replaceFontMetrics(getFontMetricsInt());
            }
            this.editText.invalidateForce();
        }
    }

    public void setAlign(int i) {
        this.align = i;
    }

    public int getAlign() {
        return this.align;
    }

    public void setTypeface(PaintTypeface paintTypeface) {
        this.typeface = paintTypeface;
        if (paintTypeface != null) {
            this.editText.setTypeface(paintTypeface.getTypeface());
        }
        updateSelectionView();
    }

    public void setTypeface(String str) {
        Iterator<PaintTypeface> it = PaintTypeface.get().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            PaintTypeface next = it.next();
            if (next.getKey().equals(str)) {
                setTypeface(next);
                str = null;
                break;
            }
        }
        this.lastTypefaceKey = str;
        updateSelectionView();
    }

    public void updateTypeface() {
        String str = this.lastTypefaceKey;
        if (str != null) {
            setTypeface(str);
        }
    }

    public PaintTypeface getTypeface() {
        return this.typeface;
    }

    public EditTextOutline getEditText() {
        return this.editText;
    }

    public void setMaxWidth(int i) {
        this.editText.setMaxWidth(i);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updatePosition();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        updatePosition();
    }

    public CharSequence getText() {
        return this.editText.getText();
    }

    public void setText(CharSequence charSequence) {
        this.editText.setText(charSequence);
        updateHint();
    }

    public Paint.FontMetricsInt getFontMetricsInt() {
        return this.editText.getPaint().getFontMetricsInt();
    }

    public float getFontSize() {
        return this.editText.getTextSize();
    }

    public View getFocusedView() {
        return this.editText;
    }

    public void beginEditing() {
        this.editText.setEnabled(true);
        this.editText.setClickable(true);
        this.editText.requestFocus();
        EditTextOutline editTextOutline = this.editText;
        editTextOutline.setSelection(editTextOutline.getText().length());
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.Paint.Views.TextPaintView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$beginEditing$0();
            }
        }, 300L);
    }

    public /* synthetic */ void lambda$beginEditing$0() {
        AndroidUtilities.showKeyboard(this.editText);
    }

    public void endEditing() {
        this.editText.clearFocus();
        this.editText.setEnabled(false);
        this.editText.setClickable(false);
        updateSelectionView();
    }

    public Swatch getSwatch() {
        return this.swatch;
    }

    public int getTextSize() {
        return (int) this.editText.getTextSize();
    }

    public void setSwatch(Swatch swatch) {
        this.swatch = swatch.clone();
        updateColor();
    }

    public void setType(int i) {
        this.currentType = i;
        updateColor();
    }

    public int getType() {
        return this.currentType;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateColor() {
        /*
            r9 = this;
            org.telegram.ui.Components.Paint.Views.EditTextOutline r0 = r9.editText
            r1 = 0
            r2 = 0
            r0.setShadowLayer(r1, r1, r1, r2)
            org.telegram.ui.Components.Paint.Swatch r0 = r9.swatch
            int r0 = r0.color
            int r3 = r9.currentType
            r4 = 1060672373(0x3f389375, float:0.721)
            r5 = -1
            r6 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            if (r3 != 0) goto L33
            org.telegram.ui.Components.Paint.Views.EditTextOutline r0 = r9.editText
            r0.setStrokeColor(r2)
            org.telegram.ui.Components.Paint.Views.EditTextOutline r0 = r9.editText
            org.telegram.ui.Components.Paint.Swatch r1 = r9.swatch
            int r1 = r1.color
            r0.setFrameColor(r1)
            org.telegram.ui.Components.Paint.Swatch r0 = r9.swatch
            int r0 = r0.color
            float r0 = org.telegram.messenger.AndroidUtilities.computePerceivedBrightness(r0)
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 < 0) goto L31
        L2f:
            r0 = r6
            goto L8a
        L31:
            r0 = r5
            goto L8a
        L33:
            r7 = 1
            r8 = 1048576000(0x3e800000, float:0.25)
            if (r3 != r7) goto L4c
            org.telegram.ui.Components.Paint.Views.EditTextOutline r1 = r9.editText
            float r2 = org.telegram.messenger.AndroidUtilities.computePerceivedBrightness(r0)
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 < 0) goto L45
            r2 = -1728053248(0xffffffff99000000, float:-6.617445E-24)
            goto L48
        L45:
            r2 = -1711276033(0xffffffff99ffffff, float:-2.6469778E-23)
        L48:
            r1.setFrameColor(r2)
            goto L8a
        L4c:
            r7 = 2
            if (r3 != r7) goto L5e
            org.telegram.ui.Components.Paint.Views.EditTextOutline r1 = r9.editText
            float r2 = org.telegram.messenger.AndroidUtilities.computePerceivedBrightness(r0)
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 < 0) goto L5a
            r5 = r6
        L5a:
            r1.setFrameColor(r5)
            goto L8a
        L5e:
            org.telegram.ui.Components.Paint.Views.EditTextOutline r7 = r9.editText
            r8 = 3
            if (r3 != r8) goto L6c
            r7.setStrokeColor(r2)
            org.telegram.ui.Components.Paint.Views.EditTextOutline r1 = r9.editText
            r1.setFrameColor(r2)
            goto L8a
        L6c:
            r7.setFrameColor(r2)
            org.telegram.ui.Components.Paint.Views.EditTextOutline r0 = r9.editText
            org.telegram.ui.Components.Paint.Swatch r3 = r9.swatch
            int r3 = r3.color
            r0.setStrokeColor(r3)
            org.telegram.ui.Components.Paint.Views.EditTextOutline r0 = r9.editText
            r0.setShadowLayer(r1, r1, r1, r2)
            org.telegram.ui.Components.Paint.Swatch r0 = r9.swatch
            int r0 = r0.color
            float r0 = org.telegram.messenger.AndroidUtilities.computePerceivedBrightness(r0)
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 < 0) goto L31
            goto L2f
        L8a:
            org.telegram.ui.Components.Paint.Views.EditTextOutline r1 = r9.editText
            r1.setTextColor(r0)
            org.telegram.ui.Components.Paint.Views.EditTextOutline r1 = r9.editText
            r1.setCursorColor(r0)
            org.telegram.ui.Components.Paint.Views.EditTextOutline r1 = r9.editText
            r1.setHandlesColor(r0)
            org.telegram.ui.Components.Paint.Views.EditTextOutline r9 = r9.editText
            r1 = 1053609165(0x3ecccccd, float:0.4)
            int r0 = org.telegram.p035ui.ActionBar.Theme.multAlpha(r0, r1)
            r9.setHighlightColor(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.Paint.Views.TextPaintView.updateColor():void");
    }

    @Override // org.telegram.p035ui.Components.Paint.Views.EntityView
    public RectOld getSelectionBounds() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup == null) {
            return new RectOld();
        }
        float scaleX = viewGroup.getScaleX();
        float measuredWidth = (getMeasuredWidth() * getScale()) + (AndroidUtilities.m1036dp(64.0f) / scaleX);
        float measuredHeight = (getMeasuredHeight() * getScale()) + (AndroidUtilities.m1036dp(52.0f) / scaleX);
        float positionX = (getPositionX() - (measuredWidth / 2.0f)) * scaleX;
        return new RectOld(positionX, (getPositionY() - (((measuredHeight - this.editText.getExtendedPaddingTop()) - AndroidUtilities.dpf2(4.0f)) / 2.0f)) * scaleX, ((measuredWidth * scaleX) + positionX) - positionX, (measuredHeight - this.editText.getExtendedPaddingBottom()) * scaleX);
    }

    @Override // org.telegram.p035ui.Components.Paint.Views.EntityView
    public TextViewSelectionView createSelectionView() {
        return new TextViewSelectionView(getContext());
    }

    public class TextViewSelectionView extends EntityView.SelectionView {
        private final Paint clearPaint;
        private Path path;

        public TextViewSelectionView(Context context) {
            super(context);
            Paint paint = new Paint(1);
            this.clearPaint = paint;
            this.path = new Path();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }

        @Override // org.telegram.ui.Components.Paint.Views.EntityView.SelectionView
        public int pointInsideHandle(float f, float f2) {
            float fM1036dp = AndroidUtilities.m1036dp(1.0f);
            float fM1036dp2 = AndroidUtilities.m1036dp(19.5f);
            float f3 = fM1036dp + fM1036dp2;
            float f4 = f3 * 2.0f;
            float measuredWidth = getMeasuredWidth() - f4;
            float measuredHeight = ((getMeasuredHeight() - f4) / 2.0f) + f3;
            if (f > f3 - fM1036dp2 && f2 > measuredHeight - fM1036dp2 && f < f3 + fM1036dp2 && f2 < measuredHeight + fM1036dp2) {
                return 1;
            }
            float f5 = f3 + measuredWidth;
            return (f <= f5 - fM1036dp2 || f2 <= measuredHeight - fM1036dp2 || f >= f5 + fM1036dp2 || f2 >= measuredHeight + fM1036dp2) ? 0 : 2;
        }

        @Override // android.view.View
        public void onDraw(Canvas canvas) {
            Canvas canvas2;
            super.onDraw(canvas);
            int saveCount = canvas.getSaveCount();
            float showAlpha = getShowAlpha();
            if (showAlpha <= 0.0f) {
                return;
            }
            if (showAlpha < 1.0f) {
                int i = (int) (showAlpha * 255.0f);
                canvas2 = canvas;
                canvas2.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), i, 31);
            } else {
                canvas2 = canvas;
            }
            float fM1036dp = AndroidUtilities.m1036dp(2.0f);
            float fDpf2 = AndroidUtilities.dpf2(5.66f);
            float fM1036dp2 = fM1036dp + fDpf2 + AndroidUtilities.m1036dp(15.0f);
            float f = fM1036dp2 * 2.0f;
            float measuredWidth = getMeasuredWidth() - f;
            float measuredHeight = getMeasuredHeight() - f;
            RectF rectF = AndroidUtilities.rectTmp;
            float f2 = fM1036dp2 + measuredWidth;
            float f3 = fM1036dp2 + measuredHeight;
            rectF.set(fM1036dp2, fM1036dp2, f2, f3);
            float fM1036dp3 = AndroidUtilities.m1036dp(12.0f);
            float fMin = Math.min(fM1036dp3, measuredWidth / 2.0f);
            float f4 = measuredHeight / 2.0f;
            float fMin2 = Math.min(fM1036dp3, f4);
            this.path.rewind();
            float f5 = fMin * 2.0f;
            float f6 = fM1036dp2 + f5;
            float f7 = 2.0f * fMin2;
            float f8 = fM1036dp2 + f7;
            rectF.set(fM1036dp2, fM1036dp2, f6, f8);
            this.path.arcTo(rectF, 180.0f, 90.0f);
            float f9 = f2 - f5;
            rectF.set(f9, fM1036dp2, f2, f8);
            this.path.arcTo(rectF, 270.0f, 90.0f);
            canvas2.drawPath(this.path, this.paint);
            this.path.rewind();
            float f10 = f3 - f7;
            rectF.set(fM1036dp2, f10, f6, f3);
            this.path.arcTo(rectF, 180.0f, -90.0f);
            rectF.set(f9, f10, f2, f3);
            this.path.arcTo(rectF, 90.0f, -90.0f);
            canvas2.drawPath(this.path, this.paint);
            float f11 = fM1036dp2 + f4;
            canvas2.drawCircle(fM1036dp2, f11, fDpf2, this.dotStrokePaint);
            canvas2.drawCircle(fM1036dp2, f11, (fDpf2 - AndroidUtilities.m1036dp(1.0f)) + 1.0f, this.dotPaint);
            canvas2.drawCircle(f2, f11, fDpf2, this.dotStrokePaint);
            canvas2.drawCircle(f2, f11, (fDpf2 - AndroidUtilities.m1036dp(1.0f)) + 1.0f, this.dotPaint);
            canvas2.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), 255, 31);
            float f12 = fM1036dp2 + fMin2;
            float f13 = f3 - fMin2;
            canvas.drawLine(fM1036dp2, f12, fM1036dp2, f13, this.paint);
            canvas.drawLine(f2, f12, f2, f13, this.paint);
            canvas.drawCircle(f2, f11, (AndroidUtilities.m1036dp(1.0f) + fDpf2) - 1.0f, this.clearPaint);
            canvas.drawCircle(fM1036dp2, f11, (fDpf2 + AndroidUtilities.m1036dp(1.0f)) - 1.0f, this.clearPaint);
            canvas.restoreToCount(saveCount);
        }
    }
}
