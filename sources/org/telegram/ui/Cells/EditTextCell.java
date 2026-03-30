package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AnimatedColor;
import org.telegram.ui.Components.AnimatedTextView;
import org.telegram.ui.Components.CubicBezierInterpolator;
import org.telegram.ui.Components.EditTextCaption;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.TextStyleSpan;
import org.telegram.ui.Components.TypefaceSpan;

/* JADX INFO: loaded from: classes6.dex */
public class EditTextCell extends FrameLayout {
    public boolean autofocused;
    public final EditTextCaption editText;
    private boolean focused;
    private boolean ignoreEditText;
    AnimatedTextView.AnimatedTextDrawable limit;
    AnimatedColor limitColor;
    private int limitCount;
    private int maxLength;
    private boolean needDivider;
    private boolean showLimitWhenEmpty;
    private boolean showLimitWhenFocused;
    private int showLimitWhenNear;

    protected void onFocusChanged(boolean z) {
    }

    protected void onTextChanged(CharSequence charSequence) {
    }

    public void setShowLimitWhenEmpty(boolean z) {
        this.showLimitWhenEmpty = z;
        if (z) {
            updateLimitText();
        }
    }

    public void setShowLimitWhenNear(int i) {
        this.showLimitWhenNear = i;
        updateLimitText();
    }

    public void updateLimitText() {
        int i;
        if (this.editText == null) {
            return;
        }
        this.limitCount = this.maxLength - getText().length();
        AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = this.limit;
        boolean zIsEmpty = TextUtils.isEmpty(getText());
        String str = _UrlKt.FRAGMENT_ENCODE_SET;
        if ((!zIsEmpty || this.showLimitWhenEmpty) && ((!this.showLimitWhenFocused || (this.focused && !this.autofocused)) && ((i = this.showLimitWhenNear) == -1 || this.limitCount <= i))) {
            str = _UrlKt.FRAGMENT_ENCODE_SET + this.limitCount;
        }
        animatedTextDrawable.setText(str);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Cells.EditTextCell$1 */
    class AnonymousClass1 implements TextView.OnEditorActionListener {
        final /* synthetic */ Runnable val$whenEnter;

        AnonymousClass1(Runnable runnable) {
            runnable = runnable;
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 6) {
                return false;
            }
            runnable.run();
            return true;
        }
    }

    public void whenHitEnter(Runnable runnable) {
        this.editText.setImeOptions(6);
        this.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.Cells.EditTextCell.1
            final /* synthetic */ Runnable val$whenEnter;

            AnonymousClass1(Runnable runnable2) {
                runnable = runnable2;
            }

            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != 6) {
                    return false;
                }
                runnable.run();
                return true;
            }
        });
    }

    public /* synthetic */ void lambda$hideKeyboardOnEnter$0() {
        AndroidUtilities.hideKeyboard(this.editText);
    }

    public void hideKeyboardOnEnter() {
        whenHitEnter(new Runnable() { // from class: org.telegram.ui.Cells.EditTextCell$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$hideKeyboardOnEnter$0();
            }
        });
    }

    public void setShowLimitOnFocus(boolean z) {
        this.showLimitWhenFocused = z;
    }

    public EditTextCell(Context context, String str, boolean z, boolean z2, int i, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.showLimitWhenNear = -1;
        this.limitColor = new AnimatedColor(this);
        AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
        this.limit = animatedTextDrawable;
        animatedTextDrawable.setAnimationProperties(0.2f, 0L, 160L, CubicBezierInterpolator.EASE_OUT_QUINT);
        this.limit.setTextSize(AndroidUtilities.dp(15.33f));
        this.limit.setGravity(5);
        this.maxLength = i;
        int i2 = 0;
        AnonymousClass2 anonymousClass2 = new EditTextCaption(context, resourcesProvider) { // from class: org.telegram.ui.Cells.EditTextCell.2
            final /* synthetic */ boolean val$allowEntities;
            final /* synthetic */ int val$maxLength;
            final /* synthetic */ Theme.ResourcesProvider val$resourceProvider;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(Context context2, Theme.ResourcesProvider resourcesProvider2, int i3, Theme.ResourcesProvider resourcesProvider22, boolean z22) {
                super(context2, resourcesProvider22);
                i = i3;
                resourcesProvider = resourcesProvider22;
                z = z22;
            }

            @Override // android.widget.TextView, android.view.View
            protected boolean verifyDrawable(Drawable drawable) {
                return drawable == EditTextCell.this.limit || super.verifyDrawable(drawable);
            }

            @Override // org.telegram.ui.Components.EditTextEffects, android.widget.TextView
            protected void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
                super.onTextChanged(charSequence, i3, i4, i5);
                AnimatedTextView.AnimatedTextDrawable animatedTextDrawable2 = EditTextCell.this.limit;
                if (animatedTextDrawable2 == null || i <= 0) {
                    return;
                }
                animatedTextDrawable2.cancelAnimation();
                EditTextCell.this.updateLimitText();
            }

            @Override // org.telegram.ui.Components.EditTextBoldCursor, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                super.dispatchDraw(canvas);
                EditTextCell editTextCell = EditTextCell.this;
                editTextCell.limit.setTextColor(editTextCell.limitColor.set(Theme.getColor(editTextCell.limitCount <= 0 ? Theme.key_text_RedRegular : Theme.key_dialogSearchHint, resourcesProvider)));
                EditTextCell.this.limit.setBounds(getScrollX(), 0, ((getScrollX() + getWidth()) - getPaddingRight()) + AndroidUtilities.dp(42.0f), getHeight());
                EditTextCell.this.limit.draw(canvas);
            }

            @Override // org.telegram.ui.Components.EditTextCaption, org.telegram.ui.Components.EditTextBoldCursor, org.telegram.ui.Components.EditTextEffects, android.widget.TextView, android.view.View
            protected void onDraw(Canvas canvas) {
                canvas.save();
                canvas.clipRect(getScrollX() + getPaddingLeft(), 0, (getScrollX() + getWidth()) - getPaddingRight(), getHeight());
                super.onDraw(canvas);
                canvas.restore();
            }

            @Override // org.telegram.ui.Components.EditTextBoldCursor
            protected void extendActionMode(ActionMode actionMode, Menu menu) {
                if (z && menu.findItem(R.id.menu_bold) == null) {
                    menu.removeItem(android.R.id.shareText);
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(LocaleController.getString(R.string.Bold));
                    spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.bold()), 0, spannableStringBuilder.length(), 33);
                    menu.add(R.id.menu_groupbolditalic, R.id.menu_bold, 6, spannableStringBuilder);
                    SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(LocaleController.getString(R.string.Italic));
                    spannableStringBuilder2.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM_ITALIC)), 0, spannableStringBuilder2.length(), 33);
                    menu.add(R.id.menu_groupbolditalic, R.id.menu_italic, 7, spannableStringBuilder2);
                    SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder(LocaleController.getString(R.string.Strike));
                    TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
                    textStyleRun.flags |= 8;
                    spannableStringBuilder3.setSpan(new TextStyleSpan(textStyleRun), 0, spannableStringBuilder3.length(), 33);
                    menu.add(R.id.menu_groupbolditalic, R.id.menu_strike, 8, spannableStringBuilder3);
                    menu.add(R.id.menu_groupbolditalic, R.id.menu_regular, 9, LocaleController.getString(R.string.Regular));
                }
            }
        };
        this.editText = anonymousClass2;
        this.limit.setCallback(anonymousClass2);
        anonymousClass2.setTextSize(1, 17.0f);
        anonymousClass2.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText, resourcesProvider22));
        int i3 = Theme.key_windowBackgroundWhiteBlackText;
        anonymousClass2.setTextColor(Theme.getColor(i3, resourcesProvider22));
        anonymousClass2.setBackground(null);
        if (z) {
            anonymousClass2.setMaxLines(5);
            anonymousClass2.setSingleLine(false);
        } else {
            anonymousClass2.setMaxLines(1);
            anonymousClass2.setSingleLine(true);
        }
        anonymousClass2.setPadding(AndroidUtilities.dp(21.0f), AndroidUtilities.dp(15.0f), AndroidUtilities.dp((i3 > 0 ? 42 : 0) + 21), AndroidUtilities.dp(15.0f));
        anonymousClass2.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        anonymousClass2.setInputType((z ? 131072 : i2) | 573441);
        anonymousClass2.setRawInputType(573441);
        anonymousClass2.setHint(str);
        anonymousClass2.setCursorColor(Theme.getColor(i3, resourcesProvider22));
        anonymousClass2.setCursorSize(AndroidUtilities.dp(19.0f));
        anonymousClass2.setCursorWidth(1.5f);
        anonymousClass2.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.Cells.EditTextCell.3
            final /* synthetic */ int val$maxLength;
            final /* synthetic */ boolean val$multiline;

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
            }

            AnonymousClass3(int i32, boolean z3) {
                i = i32;
                z = z3;
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
                if (EditTextCell.this.ignoreEditText) {
                    return;
                }
                EditTextCell.this.autofocused = false;
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (!EditTextCell.this.ignoreEditText) {
                    if (i > 0 && editable != null && editable.length() > i) {
                        EditTextCell.this.ignoreEditText = true;
                        EditTextCell.this.editText.setText(editable.subSequence(0, i));
                        EditTextCaption editTextCaption = EditTextCell.this.editText;
                        editTextCaption.setSelection(editTextCaption.length());
                        EditTextCell.this.ignoreEditText = false;
                    }
                    EditTextCell.this.onTextChanged(editable);
                }
                if (!z) {
                    return;
                }
                while (true) {
                    int iIndexOf = editable.toString().indexOf("\n");
                    if (iIndexOf < 0) {
                        return;
                    } else {
                        editable.delete(iIndexOf, iIndexOf + 1);
                    }
                }
            }
        });
        anonymousClass2.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.telegram.ui.Cells.EditTextCell.4
            AnonymousClass4() {
            }

            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z3) {
                EditTextCell.this.focused = z3;
                if (EditTextCell.this.showLimitWhenFocused) {
                    EditTextCell.this.updateLimitText();
                }
                EditTextCell.this.onFocusChanged(z3);
            }
        });
        addView(anonymousClass2, LayoutHelper.createFrame(-1, -1, 48));
        updateLimitText();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Cells.EditTextCell$2 */
    class AnonymousClass2 extends EditTextCaption {
        final /* synthetic */ boolean val$allowEntities;
        final /* synthetic */ int val$maxLength;
        final /* synthetic */ Theme.ResourcesProvider val$resourceProvider;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Context context2, Theme.ResourcesProvider resourcesProvider22, int i32, Theme.ResourcesProvider resourcesProvider222, boolean z22) {
            super(context2, resourcesProvider222);
            i = i32;
            resourcesProvider = resourcesProvider222;
            z = z22;
        }

        @Override // android.widget.TextView, android.view.View
        protected boolean verifyDrawable(Drawable drawable) {
            return drawable == EditTextCell.this.limit || super.verifyDrawable(drawable);
        }

        @Override // org.telegram.ui.Components.EditTextEffects, android.widget.TextView
        protected void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            super.onTextChanged(charSequence, i3, i4, i5);
            AnimatedTextView.AnimatedTextDrawable animatedTextDrawable2 = EditTextCell.this.limit;
            if (animatedTextDrawable2 == null || i <= 0) {
                return;
            }
            animatedTextDrawable2.cancelAnimation();
            EditTextCell.this.updateLimitText();
        }

        @Override // org.telegram.ui.Components.EditTextBoldCursor, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            EditTextCell editTextCell = EditTextCell.this;
            editTextCell.limit.setTextColor(editTextCell.limitColor.set(Theme.getColor(editTextCell.limitCount <= 0 ? Theme.key_text_RedRegular : Theme.key_dialogSearchHint, resourcesProvider)));
            EditTextCell.this.limit.setBounds(getScrollX(), 0, ((getScrollX() + getWidth()) - getPaddingRight()) + AndroidUtilities.dp(42.0f), getHeight());
            EditTextCell.this.limit.draw(canvas);
        }

        @Override // org.telegram.ui.Components.EditTextCaption, org.telegram.ui.Components.EditTextBoldCursor, org.telegram.ui.Components.EditTextEffects, android.widget.TextView, android.view.View
        protected void onDraw(Canvas canvas) {
            canvas.save();
            canvas.clipRect(getScrollX() + getPaddingLeft(), 0, (getScrollX() + getWidth()) - getPaddingRight(), getHeight());
            super.onDraw(canvas);
            canvas.restore();
        }

        @Override // org.telegram.ui.Components.EditTextBoldCursor
        protected void extendActionMode(ActionMode actionMode, Menu menu) {
            if (z && menu.findItem(R.id.menu_bold) == null) {
                menu.removeItem(android.R.id.shareText);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(LocaleController.getString(R.string.Bold));
                spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.bold()), 0, spannableStringBuilder.length(), 33);
                menu.add(R.id.menu_groupbolditalic, R.id.menu_bold, 6, spannableStringBuilder);
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(LocaleController.getString(R.string.Italic));
                spannableStringBuilder2.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM_ITALIC)), 0, spannableStringBuilder2.length(), 33);
                menu.add(R.id.menu_groupbolditalic, R.id.menu_italic, 7, spannableStringBuilder2);
                SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder(LocaleController.getString(R.string.Strike));
                TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
                textStyleRun.flags |= 8;
                spannableStringBuilder3.setSpan(new TextStyleSpan(textStyleRun), 0, spannableStringBuilder3.length(), 33);
                menu.add(R.id.menu_groupbolditalic, R.id.menu_strike, 8, spannableStringBuilder3);
                menu.add(R.id.menu_groupbolditalic, R.id.menu_regular, 9, LocaleController.getString(R.string.Regular));
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Cells.EditTextCell$3 */
    class AnonymousClass3 implements TextWatcher {
        final /* synthetic */ int val$maxLength;
        final /* synthetic */ boolean val$multiline;

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
        }

        AnonymousClass3(int i32, boolean z3) {
            i = i32;
            z = z3;
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
            if (EditTextCell.this.ignoreEditText) {
                return;
            }
            EditTextCell.this.autofocused = false;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            if (!EditTextCell.this.ignoreEditText) {
                if (i > 0 && editable != null && editable.length() > i) {
                    EditTextCell.this.ignoreEditText = true;
                    EditTextCell.this.editText.setText(editable.subSequence(0, i));
                    EditTextCaption editTextCaption = EditTextCell.this.editText;
                    editTextCaption.setSelection(editTextCaption.length());
                    EditTextCell.this.ignoreEditText = false;
                }
                EditTextCell.this.onTextChanged(editable);
            }
            if (!z) {
                return;
            }
            while (true) {
                int iIndexOf = editable.toString().indexOf("\n");
                if (iIndexOf < 0) {
                    return;
                } else {
                    editable.delete(iIndexOf, iIndexOf + 1);
                }
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Cells.EditTextCell$4 */
    class AnonymousClass4 implements View.OnFocusChangeListener {
        AnonymousClass4() {
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z3) {
            EditTextCell.this.focused = z3;
            if (EditTextCell.this.showLimitWhenFocused) {
                EditTextCell.this.updateLimitText();
            }
            EditTextCell.this.onFocusChanged(z3);
        }
    }

    public ImageView setLeftDrawable(Drawable drawable) {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageDrawable(drawable);
        addView(imageView, LayoutHelper.createFrame(24, 24.0f, 19, 18.0f, 0.0f, 0.0f, 0.0f));
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.editText.getLayoutParams();
        layoutParams.leftMargin = AndroidUtilities.dp(24.0f);
        this.editText.setLayoutParams(layoutParams);
        return imageView;
    }

    public void setText(CharSequence charSequence) {
        this.ignoreEditText = true;
        this.editText.setText(charSequence);
        EditTextCaption editTextCaption = this.editText;
        editTextCaption.setSelection(editTextCaption.getText().length());
        this.ignoreEditText = false;
    }

    public void setText(TLRPC.TL_textWithEntities tL_textWithEntities) {
        this.ignoreEditText = true;
        this.editText.setText(MessageObject.formatTextWithEntities(tL_textWithEntities, false));
        EditTextCaption editTextCaption = this.editText;
        editTextCaption.setSelection(editTextCaption.getText().length());
        this.ignoreEditText = false;
    }

    public CharSequence getText() {
        return this.editText.getText();
    }

    public TLRPC.TL_textWithEntities getTextWithEntities() {
        TLRPC.TL_textWithEntities tL_textWithEntities = new TLRPC.TL_textWithEntities();
        CharSequence[] charSequenceArr = {getText()};
        tL_textWithEntities.entities = MediaDataController.getInstance(UserConfig.selectedAccount).getEntities(charSequenceArr, true);
        tL_textWithEntities.text = charSequenceArr[0].toString();
        return tL_textWithEntities;
    }

    public boolean validate() {
        return this.maxLength < 0 || this.editText.getText().length() <= this.maxLength;
    }

    public void setDivider(boolean z) {
        this.needDivider = z;
        setWillNotDraw(!z);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.needDivider) {
            canvas.drawLine(LocaleController.isRTL ? 0.0f : AndroidUtilities.dp(22.0f), getMeasuredHeight() - 1, getMeasuredWidth() - (LocaleController.isRTL ? AndroidUtilities.dp(22.0f) : 0), getMeasuredHeight() - 1, Theme.dividerPaint);
        }
    }

    public void setMultiline(boolean z) {
        if (z) {
            this.editText.setMaxLines(5);
            this.editText.setSingleLine(false);
        } else {
            this.editText.setMaxLines(1);
            this.editText.setSingleLine(true);
        }
    }
}
