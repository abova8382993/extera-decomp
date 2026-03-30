package org.telegram.p029ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import okhttp3.internal.url._UrlKt;
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p029ui.ActionBar.Theme;

/* JADX INFO: loaded from: classes6.dex */
public abstract class CodeFieldContainer extends LinearLayout {
    Paint bitmapPaint;
    public CodeNumberField[] codeField;
    public boolean ignoreOnTextChange;
    public boolean isFocusSuppressed;
    Paint paint;
    float strokeWidth;

    protected abstract void processNextPressed();

    public CodeFieldContainer(Context context) {
        super(context);
        this.paint = new Paint(1);
        this.bitmapPaint = new Paint(1);
        this.paint.setStyle(Paint.Style.STROKE);
        setOrientation(0);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        Paint paint = this.paint;
        float fM1124dp = AndroidUtilities.m1124dp(1.5f);
        this.strokeWidth = fM1124dp;
        paint.setStrokeWidth(fM1124dp);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof CodeNumberField) {
                CodeNumberField codeNumberField = (CodeNumberField) childAt;
                if (!this.isFocusSuppressed) {
                    if (childAt.isFocused()) {
                        codeNumberField.animateFocusedProgress(1.0f);
                    } else if (!childAt.isFocused()) {
                        codeNumberField.animateFocusedProgress(0.0f);
                    }
                }
                float successProgress = codeNumberField.getSuccessProgress();
                this.paint.setColor(ColorUtils.blendARGB(ColorUtils.blendARGB(ColorUtils.blendARGB(Theme.getColor(Theme.key_windowBackgroundWhiteInputField), Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated), codeNumberField.getFocusedProgress()), Theme.getColor(Theme.key_text_RedBold), codeNumberField.getErrorProgress()), Theme.getColor(Theme.key_checkbox), successProgress));
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom());
                float f = this.strokeWidth;
                rectF.inset(f, f);
                if (successProgress != 0.0f) {
                    float f2 = -Math.max(0.0f, this.strokeWidth * (codeNumberField.getSuccessScaleProgress() - 1.0f));
                    rectF.inset(f2, f2);
                }
                canvas.drawRoundRect(rectF, AndroidUtilities.m1124dp(4.0f), AndroidUtilities.m1124dp(4.0f), this.paint);
            }
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        if (view instanceof CodeNumberField) {
            CodeNumberField codeNumberField = (CodeNumberField) view;
            canvas.save();
            float f = codeNumberField.enterAnimation;
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(view.getX(), view.getY(), view.getX() + view.getMeasuredWidth(), view.getY() + view.getMeasuredHeight());
            float f2 = this.strokeWidth;
            rectF.inset(f2, f2);
            canvas.clipRect(rectF);
            if (codeNumberField.replaceAnimation) {
                float f3 = (f * 0.5f) + 0.5f;
                view.setAlpha(f);
                canvas.scale(f3, f3, codeNumberField.getX() + (codeNumberField.getMeasuredWidth() / 2.0f), codeNumberField.getY() + (codeNumberField.getMeasuredHeight() / 2.0f));
            } else {
                view.setAlpha(1.0f);
                canvas.translate(0.0f, view.getMeasuredHeight() * (1.0f - f));
            }
            super.drawChild(canvas, view, j);
            canvas.restore();
            float f4 = codeNumberField.exitAnimation;
            if (f4 >= 1.0f) {
                return true;
            }
            canvas.save();
            float f5 = 1.0f - f4;
            float f6 = (f5 * 0.5f) + 0.5f;
            canvas.scale(f6, f6, codeNumberField.getX() + (codeNumberField.getMeasuredWidth() / 2.0f), codeNumberField.getY() + (codeNumberField.getMeasuredHeight() / 2.0f));
            this.bitmapPaint.setAlpha((int) (f5 * 255.0f));
            canvas.drawBitmap(codeNumberField.exitBitmap, codeNumberField.getX(), codeNumberField.getY(), this.bitmapPaint);
            canvas.restore();
            return true;
        }
        return super.drawChild(canvas, view, j);
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setNumbersCount(int r13, int r14) {
        /*
            Method dump skipped, instruction units count: 221
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.CodeFieldContainer.setNumbersCount(int, int):void");
    }

    /* JADX INFO: renamed from: org.telegram.ui.CodeFieldContainer$1 */
    class C37641 extends CodeNumberField {
        final /* synthetic */ int val$length;
        final /* synthetic */ int val$num;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C37641(Context context, int i, int i2) {
            super(context);
            i = i;
            i = i2;
        }

        @Override // android.view.View
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            int i;
            int i2 = 0;
            if (keyEvent.getKeyCode() == 4) {
                return false;
            }
            int keyCode = keyEvent.getKeyCode();
            if (i >= CodeFieldContainer.this.codeField.length) {
                return false;
            }
            if (keyEvent.getAction() != 1) {
                return isFocused();
            }
            if (keyCode == 67 && CodeFieldContainer.this.codeField[i].length() == 1) {
                CodeFieldContainer.this.codeField[i].startExitAnimation();
                CodeFieldContainer.this.codeField[i].setText(_UrlKt.FRAGMENT_ENCODE_SET);
                return true;
            }
            if (keyCode != 67 || CodeFieldContainer.this.codeField[i].length() != 0 || (i = i) <= 0) {
                if (keyCode >= 7 && keyCode <= 16) {
                    String string = Integer.toString(keyCode - 7);
                    if (CodeFieldContainer.this.codeField[i].getText() != null && string.equals(CodeFieldContainer.this.codeField[i].getText().toString())) {
                        int i3 = i;
                        if (i3 >= i - 1) {
                            CodeFieldContainer.this.processNextPressed();
                        } else {
                            CodeFieldContainer.this.codeField[i3 + 1].requestFocus();
                        }
                        return true;
                    }
                    if (CodeFieldContainer.this.codeField[i].length() > 0) {
                        CodeFieldContainer.this.codeField[i].startExitAnimation();
                    }
                    CodeFieldContainer.this.codeField[i].setText(string);
                }
                return true;
            }
            CodeNumberField[] codeNumberFieldArr = CodeFieldContainer.this.codeField;
            codeNumberFieldArr[i - 1].setSelection(codeNumberFieldArr[i - 1].length());
            while (true) {
                int i4 = i;
                if (i2 >= i4) {
                    CodeFieldContainer.this.codeField[i4 - 1].startExitAnimation();
                    CodeFieldContainer.this.codeField[i - 1].setText(_UrlKt.FRAGMENT_ENCODE_SET);
                    return true;
                }
                if (i2 == i4 - 1) {
                    CodeFieldContainer.this.codeField[i4 - 1].requestFocus();
                } else {
                    CodeFieldContainer.this.codeField[i2].clearFocus();
                }
                i2++;
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.CodeFieldContainer$2 */
    class C37652 implements TextWatcher {
        final /* synthetic */ int val$length;
        final /* synthetic */ int val$num;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        C37652(int i, int i2) {
            i = i;
            i = i2;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            int length;
            if (!CodeFieldContainer.this.ignoreOnTextChange && (length = editable.length()) >= 1) {
                int i = i;
                if (length > 1) {
                    String string = editable.toString();
                    CodeFieldContainer.this.ignoreOnTextChange = true;
                    for (int i2 = 0; i2 < Math.min(i - i, length); i2++) {
                        if (i2 == 0) {
                            editable.replace(0, length, string.substring(i2, i2 + 1));
                        } else {
                            i++;
                            int i3 = i;
                            int i4 = i3 + i2;
                            CodeNumberField[] codeNumberFieldArr = CodeFieldContainer.this.codeField;
                            if (i4 < codeNumberFieldArr.length) {
                                codeNumberFieldArr[i3 + i2].setText(string.substring(i2, i2 + 1));
                            }
                        }
                    }
                    CodeFieldContainer.this.ignoreOnTextChange = false;
                }
                int i5 = i + 1;
                if (i5 >= 0) {
                    CodeNumberField[] codeNumberFieldArr2 = CodeFieldContainer.this.codeField;
                    if (i5 < codeNumberFieldArr2.length) {
                        CodeNumberField codeNumberField = codeNumberFieldArr2[i5];
                        codeNumberField.setSelection(codeNumberField.length());
                        CodeFieldContainer.this.codeField[i5].requestFocus();
                    }
                }
                int i6 = i;
                if ((i == i6 - 1 || (i == i6 - 2 && length >= 2)) && CodeFieldContainer.this.getCode().length() == i) {
                    CodeFieldContainer.this.processNextPressed();
                }
            }
        }
    }

    public /* synthetic */ boolean lambda$setNumbersCount$0(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 5) {
            return false;
        }
        processNextPressed();
        return true;
    }

    public String getCode() {
        if (this.codeField == null) {
            return _UrlKt.FRAGMENT_ENCODE_SET;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            CodeNumberField[] codeNumberFieldArr = this.codeField;
            if (i < codeNumberFieldArr.length) {
                sb.append(PhoneFormat.stripExceptNumbers(codeNumberFieldArr[i].getText().toString()));
                i++;
            } else {
                return sb.toString();
            }
        }
    }

    public void setCode(String str) {
        this.codeField[0].setText(str);
    }

    public void setText(String str) {
        setText(str, false);
    }

    public void setText(String str, boolean z) {
        if (this.codeField == null) {
            return;
        }
        int i = 0;
        if (z) {
            int i2 = 0;
            while (true) {
                CodeNumberField[] codeNumberFieldArr = this.codeField;
                if (i2 >= codeNumberFieldArr.length) {
                    break;
                }
                if (codeNumberFieldArr[i2].isFocused()) {
                    i = i2;
                    break;
                }
                i2++;
            }
        }
        for (int i3 = i; i3 < Math.min(this.codeField.length, str.length() + i); i3++) {
            this.codeField[i3].setText(Character.toString(str.charAt(i3 - i)));
        }
    }
}
