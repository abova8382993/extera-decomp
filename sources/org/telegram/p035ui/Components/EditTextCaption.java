package org.telegram.p035ui.Components;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.inputmethod.EditorInfoCompat;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.CodeHighlighting;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.utils.CopyUtilities;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.AlertDialogDecor;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.FloatingActionMode;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.AlertsCreator;
import org.telegram.p035ui.Components.QuoteSpan;
import org.telegram.p035ui.Components.TextStyleSpan;
import org.telegram.p035ui.LaunchActivity;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes3.dex */
public class EditTextCaption extends EditTextBoldCursor {
    private static final int ACCESSIBILITY_ACTION_SHARE = 268435456;
    public boolean adaptiveCreateLinkDialog;
    private boolean allowTextEntitiesIntersection;
    private String caption;
    private StaticLayout captionLayout;
    private boolean copyPasteShowed;
    private AlertDialog creationLinkDialog;
    private EditTextCaptionDelegate delegate;
    private int hintColor;
    private boolean isInitLineCount;
    private CharSequence lastLayoutText;
    private int lastLayoutWidth;
    private int lineCount;
    private final Theme.ResourcesProvider resourcesProvider;
    private Text rightText;
    private int selectionEnd;
    private int selectionStart;
    private int userNameLength;
    private int xOffset;
    private int yOffset;

    /* JADX INFO: loaded from: classes7.dex */
    public interface EditTextCaptionDelegate {
        void onSpansChanged();
    }

    public static /* synthetic */ void $r8$lambda$3rcpwF6Mh7ikoiG1MknyFUczbVs() {
    }

    public void onContextMenuClose() {
    }

    public void onContextMenuOpen() {
    }

    public void onLineCountChanged(int i, int i2) {
    }

    public EditTextCaption(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.selectionStart = -1;
        this.selectionEnd = -1;
        this.resourcesProvider = resourcesProvider;
        this.quoteColor = Theme.getColor(Theme.key_chat_inQuote, resourcesProvider);
        addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.Components.EditTextCaption.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public C42411() {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (EditTextCaption.this.lineCount != EditTextCaption.this.getLineCount()) {
                    if (!EditTextCaption.this.isInitLineCount && EditTextCaption.this.getMeasuredWidth() > 0) {
                        EditTextCaption editTextCaption = EditTextCaption.this;
                        editTextCaption.onLineCountChanged(editTextCaption.lineCount, EditTextCaption.this.getLineCount());
                    }
                    EditTextCaption editTextCaption2 = EditTextCaption.this;
                    editTextCaption2.lineCount = editTextCaption2.getLineCount();
                }
            }
        });
        setClipToPadding(true);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextCaption$1 */
    public class C42411 implements TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public C42411() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            if (EditTextCaption.this.lineCount != EditTextCaption.this.getLineCount()) {
                if (!EditTextCaption.this.isInitLineCount && EditTextCaption.this.getMeasuredWidth() > 0) {
                    EditTextCaption editTextCaption = EditTextCaption.this;
                    editTextCaption.onLineCountChanged(editTextCaption.lineCount, EditTextCaption.this.getLineCount());
                }
                EditTextCaption editTextCaption2 = EditTextCaption.this;
                editTextCaption2.lineCount = editTextCaption2.getLineCount();
            }
        }
    }

    public void setCaption(String str) {
        String str2 = this.caption;
        if ((str2 == null || str2.length() == 0) && (str == null || str.length() == 0)) {
            return;
        }
        String str3 = this.caption;
        if (str3 == null || !str3.equals(str)) {
            this.caption = str;
            if (str != null) {
                this.caption = str.replace('\n', ' ');
            }
            requestLayout();
        }
    }

    public void setDelegate(EditTextCaptionDelegate editTextCaptionDelegate) {
        this.delegate = editTextCaptionDelegate;
    }

    public void setAllowTextEntitiesIntersection(boolean z) {
        this.allowTextEntitiesIntersection = z;
    }

    public boolean getAllowTextEntitiesIntersection() {
        return this.allowTextEntitiesIntersection;
    }

    public void makeSelectedBold() {
        TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
        textStyleRun.flags |= 1;
        applyTextStyleToSelection(new TextStyleSpan(textStyleRun));
    }

    public void makeSelectedSpoiler() {
        TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
        textStyleRun.flags |= 256;
        applyTextStyleToSelection(new TextStyleSpan(textStyleRun));
        invalidateSpoilers();
    }

    public void makeSelectedItalic() {
        TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
        textStyleRun.flags |= 2;
        applyTextStyleToSelection(new TextStyleSpan(textStyleRun));
    }

    public void makeSelectedMono() {
        TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
        textStyleRun.flags |= 4;
        applyTextStyleToSelection(new TextStyleSpan(textStyleRun));
    }

    public void makeSelectedCode() {
        final int selectionEnd;
        AlertDialog.Builder builder;
        final int selectionStart = this.selectionStart;
        if (selectionStart >= 0 && (selectionEnd = this.selectionEnd) >= 0) {
            this.selectionEnd = -1;
            this.selectionStart = -1;
        } else {
            selectionStart = getSelectionStart();
            selectionEnd = getSelectionEnd();
        }
        if (selectionStart >= selectionEnd) {
            return;
        }
        final EditTextBoldCursor editTextBoldCursorCreateCodeLanguageEditText = createCodeLanguageEditText();
        fillSelectedCodeLanguage(editTextBoldCursorCreateCodeLanguageEditText, selectionStart, selectionEnd);
        if (this.adaptiveCreateLinkDialog) {
            builder = new AlertDialogDecor.Builder(getContext(), this.resourcesProvider);
        } else {
            builder = new AlertDialog.Builder(getContext(), this.resourcesProvider);
        }
        builder.setTitle(LocaleController.getString(C2797R.string.CreateCode));
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.addView(editTextBoldCursorCreateCodeLanguageEditText, LayoutHelper.createFrame(-1, 36.0f, 1, 24.0f, 0.0f, 24.0f, 0.0f));
        builder.setView(frameLayout);
        builder.setWidth(AndroidUtilities.m1036dp(292.0f));
        builder.setPositiveButton(LocaleController.getString(C2797R.string.f1162OK), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.EditTextCaption$$ExternalSyntheticLambda1
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$makeSelectedCode$0(selectionStart, selectionEnd, editTextBoldCursorCreateCodeLanguageEditText, alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.ui.Components.EditTextCaption$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                EditTextCaption.m11414$r8$lambda$aBOjHGqHBZl7dKL3jHTWBggnpA(editTextBoldCursorCreateCodeLanguageEditText, dialogInterface);
            }
        });
        if (this.adaptiveCreateLinkDialog) {
            this.creationLinkDialog = alertDialogCreate;
            alertDialogCreate.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.EditTextCaption$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f$0.lambda$makeSelectedCode$2(dialogInterface);
                }
            });
            alertDialogCreate.showDelayed(250L);
        } else {
            alertDialogCreate.show();
        }
        editTextBoldCursorCreateCodeLanguageEditText.setSelection(0, editTextBoldCursorCreateCodeLanguageEditText.getText().length());
    }

    public /* synthetic */ void lambda$makeSelectedCode$0(int i, int i2, EditTextBoldCursor editTextBoldCursor, AlertDialog alertDialog, int i3) {
        applyCodeBlock(i, i2, editTextBoldCursor.getText().toString());
    }

    /* JADX INFO: renamed from: $r8$lambda$aBOjHGqHBZl7dKL3j-HTWBggnpA */
    public static /* synthetic */ void m11414$r8$lambda$aBOjHGqHBZl7dKL3jHTWBggnpA(EditTextBoldCursor editTextBoldCursor, DialogInterface dialogInterface) {
        editTextBoldCursor.requestFocus();
        AndroidUtilities.showKeyboard(editTextBoldCursor);
    }

    public /* synthetic */ void lambda$makeSelectedCode$2(DialogInterface dialogInterface) {
        this.creationLinkDialog = null;
        requestFocus();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextCaption$2 */
    /* JADX INFO: loaded from: classes7.dex */
    public class C42422 extends EditTextBoldCursor {
        public C42422(Context context) {
            super(context);
        }

        @Override // org.telegram.p035ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(64.0f), TLObject.FLAG_30));
        }
    }

    private EditTextBoldCursor createCodeLanguageEditText() {
        C42422 c42422 = new EditTextBoldCursor(getContext()) { // from class: org.telegram.ui.Components.EditTextCaption.2
            public C42422(Context context) {
                super(context);
            }

            @Override // org.telegram.p035ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
            public void onMeasure(int i, int i2) {
                super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(64.0f), TLObject.FLAG_30));
            }
        };
        c42422.setTextSize(1, 18.0f);
        c42422.setTextColor(getThemedColor(Theme.key_dialogTextBlack));
        int i = Theme.key_windowBackgroundWhiteInputFieldActivated;
        c42422.setCursorColor(getThemedColor(i));
        c42422.setHintText(LocaleController.getString(C2797R.string.CreateCodeLanguage));
        c42422.setHeaderHintColor(getThemedColor(Theme.key_windowBackgroundWhiteBlueHeader));
        c42422.setSingleLine(true);
        c42422.setFocusable(true);
        c42422.setTransformHintToHeader(true);
        c42422.setLineColors(getThemedColor(Theme.key_windowBackgroundWhiteInputField), getThemedColor(i), getThemedColor(Theme.key_text_RedRegular));
        c42422.setImeOptions(6);
        c42422.setBackground(null);
        c42422.requestFocus();
        c42422.setPadding(0, 0, 0, 0);
        return c42422;
    }

    private void fillSelectedCodeLanguage(EditTextBoldCursor editTextBoldCursor, int i, int i2) {
        CodeHighlighting.Span[] spanArr = (CodeHighlighting.Span[]) getText().getSpans(i, i2, CodeHighlighting.Span.class);
        if (spanArr == null) {
            return;
        }
        for (CodeHighlighting.Span span : spanArr) {
            if (span != null && !TextUtils.isEmpty(span.lng)) {
                editTextBoldCursor.setText(span.lng);
                return;
            }
        }
    }

    private void applyCodeBlock(int i, int i2, String str) {
        Editable text = getText();
        try {
            CharacterStyle[] characterStyleArr = (CharacterStyle[]) text.getSpans(i, i2, CharacterStyle.class);
            if (characterStyleArr != null) {
                for (CharacterStyle characterStyle : characterStyleArr) {
                    int spanStart = text.getSpanStart(characterStyle);
                    int spanEnd = text.getSpanEnd(characterStyle);
                    text.removeSpan(characterStyle);
                    if (spanStart < i) {
                        text.setSpan(characterStyle, spanStart, i, 33);
                    }
                    if (spanEnd > i2) {
                        text.setSpan(characterStyle, i2, spanEnd, 33);
                    }
                }
            }
            text.setSpan(new CodeHighlighting.Span(true, 0, null, CodeHighlighting.normalizeLanguage(str), text.subSequence(i, i2).toString()), i, i2, 33);
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        EditTextCaptionDelegate editTextCaptionDelegate = this.delegate;
        if (editTextCaptionDelegate != null) {
            editTextCaptionDelegate.onSpansChanged();
        }
    }

    public void makeSelectedStrike() {
        TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
        textStyleRun.flags |= 8;
        applyTextStyleToSelection(new TextStyleSpan(textStyleRun));
    }

    public void makeSelectedUnderline() {
        TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
        textStyleRun.flags |= 16;
        applyTextStyleToSelection(new TextStyleSpan(textStyleRun));
    }

    public void makeSelectedQuote() {
        makeSelectedQuote(false);
    }

    public void makeSelectedQuote(boolean z) {
        int selectionEnd;
        int selectionStart = this.selectionStart;
        if (selectionStart >= 0 && (selectionEnd = this.selectionEnd) >= 0) {
            this.selectionEnd = -1;
            this.selectionStart = -1;
        } else {
            selectionStart = getSelectionStart();
            selectionEnd = getSelectionEnd();
        }
        int iPutQuoteToEditable = QuoteSpan.putQuoteToEditable(getText(), selectionStart, selectionEnd, z);
        if (iPutQuoteToEditable >= 0) {
            setSelection(iPutQuoteToEditable);
            resetFontMetricsCache();
        }
        invalidateQuotes(true);
        invalidateSpoilers();
    }

    public void makeSelectedDate() {
        final int selectionEnd;
        final int selectionStart = this.selectionStart;
        if (selectionStart >= 0 && (selectionEnd = this.selectionEnd) >= 0) {
            this.selectionEnd = -1;
            this.selectionStart = -1;
        } else {
            selectionStart = getSelectionStart();
            selectionEnd = getSelectionEnd();
        }
        AlertsCreator.createFormattedDatePickerDialog(getContext(), new AlertsCreator.FormattedDatePickerDelegate() { // from class: org.telegram.ui.Components.EditTextCaption$$ExternalSyntheticLambda10
            @Override // org.telegram.ui.Components.AlertsCreator.FormattedDatePickerDelegate
            public final void didSelectDate(int i, int i2) {
                this.f$0.lambda$makeSelectedDate$3(selectionStart, selectionEnd, i, i2);
            }
        }, new Runnable() { // from class: org.telegram.ui.Components.EditTextCaption$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                EditTextCaption.$r8$lambda$3rcpwF6Mh7ikoiG1MknyFUczbVs();
            }
        }, this.resourcesProvider);
    }

    public /* synthetic */ void lambda$makeSelectedDate$3(int i, int i2, int i3, int i4) {
        Editable text = getText();
        TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
        textStyleRun.flags |= 128;
        textStyleRun.start = i;
        textStyleRun.end = i2;
        TLRPC.TL_messageEntityFormattedDate tL_messageEntityFormattedDate = new TLRPC.TL_messageEntityFormattedDate();
        tL_messageEntityFormattedDate.date = i3;
        tL_messageEntityFormattedDate.flags = i4;
        tL_messageEntityFormattedDate.applyFlags();
        try {
            text.setSpan(new FormattedDateSpan(text.subSequence(i, i2).toString(), textStyleRun, tL_messageEntityFormattedDate), i, i2, 33);
        } catch (Exception unused) {
        }
        EditTextCaptionDelegate editTextCaptionDelegate = this.delegate;
        if (editTextCaptionDelegate != null) {
            editTextCaptionDelegate.onSpansChanged();
        }
    }

    public void translateSelected() {
        final int selectionEnd;
        final int selectionStart = this.selectionStart;
        if (selectionStart >= 0 && (selectionEnd = this.selectionEnd) >= 0) {
            this.selectionEnd = -1;
            this.selectionStart = -1;
        } else {
            selectionStart = getSelectionStart();
            selectionEnd = getSelectionEnd();
        }
        CharSequence charSequenceSubSequence = getText().subSequence(selectionStart, selectionEnd);
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        new TranslateAlert3(getContext(), safeLastFragment != null ? safeLastFragment.getResourceProvider() : null).setText(charSequenceSubSequence).setOnUse(new Utilities.Callback() { // from class: org.telegram.ui.Components.EditTextCaption$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$translateSelected$5(selectionStart, selectionEnd, (CharSequence) obj);
            }
        }).show();
        setSelection(selectionStart, selectionEnd);
    }

    public /* synthetic */ void lambda$translateSelected$5(int i, int i2, CharSequence charSequence) {
        getText().replace(i, i2, charSequence);
        setSelection(i, charSequence.length() + i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [org.telegram.ui.ActionBar.AlertDialog$Builder] */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.view.View, android.view.ViewGroup, android.widget.FrameLayout] */
    public void makeSelectedUrl() {
        Object builder;
        final int selectionEnd;
        CharSequence charSequenceCoerceToText;
        if (this.adaptiveCreateLinkDialog) {
            builder = new AlertDialogDecor.Builder(getContext(), this.resourcesProvider);
        } else {
            builder = new AlertDialog.Builder(getContext(), this.resourcesProvider);
        }
        ?? r2 = builder;
        r2.setTitle(LocaleController.getString(C2797R.string.CreateLink));
        ?? frameLayout = new FrameLayout(getContext());
        final C42433 c42433 = new EditTextBoldCursor(getContext()) { // from class: org.telegram.ui.Components.EditTextCaption.3
            public C42433(Context context) {
                super(context);
            }

            @Override // org.telegram.p035ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
            public void onMeasure(int i, int i2) {
                super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(64.0f), TLObject.FLAG_30));
            }
        };
        c42433.setTextSize(1, 18.0f);
        c42433.setText("http://");
        c42433.setTextColor(getThemedColor(Theme.key_dialogTextBlack));
        c42433.setHintText(LocaleController.getString(C2797R.string.URL));
        c42433.setHeaderHintColor(getThemedColor(Theme.key_windowBackgroundWhiteBlueHeader));
        c42433.setSingleLine(true);
        c42433.setFocusable(true);
        c42433.setTransformHintToHeader(true);
        c42433.setLineColors(getThemedColor(Theme.key_windowBackgroundWhiteInputField), getThemedColor(Theme.key_windowBackgroundWhiteInputFieldActivated), getThemedColor(Theme.key_text_RedRegular));
        c42433.setImeOptions(6);
        c42433.setBackgroundDrawable(null);
        c42433.requestFocus();
        c42433.setPadding(0, 0, 0, 0);
        c42433.setHighlightColor(getThemedColor(Theme.key_chat_inTextSelectionHighlight));
        c42433.setHandlesColor(getThemedColor(Theme.key_chat_TextSelectionCursor));
        frameLayout.addView(c42433, LayoutHelper.createFrame(-1, -1, 119));
        final TextView textView = new TextView(getContext());
        textView.setTextSize(1, 12.0f);
        textView.setTypeface(AndroidUtilities.bold());
        textView.setText(LocaleController.getString(C2797R.string.Paste));
        textView.setPadding(AndroidUtilities.m1036dp(10.0f), 0, AndroidUtilities.m1036dp(10.0f), 0);
        textView.setGravity(17);
        int themedColor = getThemedColor(Theme.key_windowBackgroundWhiteBlueText2);
        textView.setTextColor(themedColor);
        textView.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(6.0f), Theme.multAlpha(themedColor, 0.12f), Theme.multAlpha(themedColor, 0.15f)));
        ScaleStateListAnimator.apply(textView, 0.1f, 1.5f);
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, 26.0f, 21, 0.0f, 0.0f, 24.0f, 3.0f));
        final Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.EditTextCaption$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$makeSelectedUrl$6(c42433, textView);
            }
        };
        textView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EditTextCaption$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$makeSelectedUrl$7(c42433, runnable, view);
            }
        });
        c42433.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.Components.EditTextCaption.4
            final /* synthetic */ Runnable val$checkPaste;

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public C42444(final Runnable runnable2) {
                runnable = runnable2;
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                runnable.run();
            }
        });
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService("clipboard");
        if (clipboardManager != null && clipboardManager.hasPrimaryClip()) {
            try {
                charSequenceCoerceToText = clipboardManager.getPrimaryClip().getItemAt(0).coerceToText(getContext());
            } catch (Exception e) {
                FileLog.m1048e(e);
                charSequenceCoerceToText = null;
            }
            if (charSequenceCoerceToText != null) {
                c42433.setText(charSequenceCoerceToText);
                c42433.setSelection(0, c42433.getText().length());
            }
        }
        runnable2.run();
        r2.setView(frameLayout);
        final int selectionStart = this.selectionStart;
        if (selectionStart >= 0 && (selectionEnd = this.selectionEnd) >= 0) {
            this.selectionEnd = -1;
            this.selectionStart = -1;
        } else {
            selectionStart = getSelectionStart();
            selectionEnd = getSelectionEnd();
        }
        r2.setPositiveButton(LocaleController.getString(C2797R.string.f1162OK), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.EditTextCaption$$ExternalSyntheticLambda6
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$makeSelectedUrl$8(selectionStart, selectionEnd, c42433, alertDialog, i);
            }
        });
        r2.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
        if (this.adaptiveCreateLinkDialog) {
            AlertDialog alertDialogCreate = r2.create();
            this.creationLinkDialog = alertDialogCreate;
            alertDialogCreate.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.EditTextCaption$$ExternalSyntheticLambda7
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f$0.lambda$makeSelectedUrl$9(dialogInterface);
                }
            });
            this.creationLinkDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.ui.Components.EditTextCaption$$ExternalSyntheticLambda8
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    EditTextCaption.$r8$lambda$xetTtzgiciy4T6bo1Q40IE2ZMeg(c42433, dialogInterface);
                }
            });
            this.creationLinkDialog.showDelayed(250L);
        } else {
            r2.show().setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.ui.Components.EditTextCaption$$ExternalSyntheticLambda9
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    EditTextCaption.$r8$lambda$FMzUblB34zJHW4PnnG6siUqQslA(c42433, dialogInterface);
                }
            });
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) c42433.getLayoutParams();
        if (marginLayoutParams != null) {
            if (marginLayoutParams instanceof FrameLayout.LayoutParams) {
                ((FrameLayout.LayoutParams) marginLayoutParams).gravity = 1;
            }
            int iM1036dp = AndroidUtilities.m1036dp(24.0f);
            marginLayoutParams.leftMargin = iM1036dp;
            marginLayoutParams.rightMargin = iM1036dp;
            marginLayoutParams.height = AndroidUtilities.m1036dp(36.0f);
            c42433.setLayoutParams(marginLayoutParams);
        }
        c42433.setSelection(0, c42433.getText().length());
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextCaption$3 */
    /* JADX INFO: loaded from: classes7.dex */
    public class C42433 extends EditTextBoldCursor {
        public C42433(Context context) {
            super(context);
        }

        @Override // org.telegram.p035ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(64.0f), TLObject.FLAG_30));
        }
    }

    public /* synthetic */ void lambda$makeSelectedUrl$6(EditTextBoldCursor editTextBoldCursor, TextView textView) {
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService("clipboard");
        boolean z = (TextUtils.isEmpty(editTextBoldCursor.getText()) || TextUtils.equals(editTextBoldCursor.getText().toString(), "http://")) && clipboardManager != null && clipboardManager.hasPrimaryClip();
        textView.animate().alpha(z ? 1.0f : 0.0f).scaleX(z ? 1.0f : 0.7f).scaleY(z ? 1.0f : 0.7f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).setDuration(300L).start();
    }

    public /* synthetic */ void lambda$makeSelectedUrl$7(EditTextBoldCursor editTextBoldCursor, Runnable runnable, View view) {
        CharSequence charSequenceCoerceToText;
        try {
            charSequenceCoerceToText = ((ClipboardManager) getContext().getSystemService("clipboard")).getPrimaryClip().getItemAt(0).coerceToText(getContext());
        } catch (Exception e) {
            FileLog.m1048e(e);
            charSequenceCoerceToText = null;
        }
        if (charSequenceCoerceToText != null) {
            editTextBoldCursor.setText(charSequenceCoerceToText);
            editTextBoldCursor.setSelection(0, editTextBoldCursor.getText().length());
        }
        runnable.run();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextCaption$4 */
    /* JADX INFO: loaded from: classes7.dex */
    public class C42444 implements TextWatcher {
        final /* synthetic */ Runnable val$checkPaste;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public C42444(final Runnable runnable2) {
            runnable = runnable2;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            runnable.run();
        }
    }

    public /* synthetic */ void lambda$makeSelectedUrl$8(int i, int i2, EditTextBoldCursor editTextBoldCursor, AlertDialog alertDialog, int i3) {
        Editable text = getText();
        CharacterStyle[] characterStyleArr = (CharacterStyle[]) text.getSpans(i, i2, CharacterStyle.class);
        if (characterStyleArr != null && characterStyleArr.length > 0) {
            for (CharacterStyle characterStyle : characterStyleArr) {
                if (!(characterStyle instanceof AnimatedEmojiSpan) && !(characterStyle instanceof QuoteSpan.QuoteStyleSpan)) {
                    int spanStart = text.getSpanStart(characterStyle);
                    int spanEnd = text.getSpanEnd(characterStyle);
                    text.removeSpan(characterStyle);
                    if (spanStart < i) {
                        text.setSpan(characterStyle, spanStart, i, 33);
                    }
                    if (spanEnd > i2) {
                        text.setSpan(characterStyle, i2, spanEnd, 33);
                    }
                }
            }
        }
        try {
            text.setSpan(new URLSpanReplacement(editTextBoldCursor.getText().toString()), i, i2, 33);
        } catch (Exception unused) {
        }
        EditTextCaptionDelegate editTextCaptionDelegate = this.delegate;
        if (editTextCaptionDelegate != null) {
            editTextCaptionDelegate.onSpansChanged();
        }
    }

    public /* synthetic */ void lambda$makeSelectedUrl$9(DialogInterface dialogInterface) {
        this.creationLinkDialog = null;
        requestFocus();
    }

    public static /* synthetic */ void $r8$lambda$xetTtzgiciy4T6bo1Q40IE2ZMeg(EditTextBoldCursor editTextBoldCursor, DialogInterface dialogInterface) {
        editTextBoldCursor.requestFocus();
        AndroidUtilities.showKeyboard(editTextBoldCursor);
    }

    public static /* synthetic */ void $r8$lambda$FMzUblB34zJHW4PnnG6siUqQslA(EditTextBoldCursor editTextBoldCursor, DialogInterface dialogInterface) {
        editTextBoldCursor.requestFocus();
        AndroidUtilities.showKeyboard(editTextBoldCursor);
    }

    public boolean closeCreationLinkDialog(boolean z) {
        AlertDialog alertDialog = this.creationLinkDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return false;
        }
        if (!z) {
            return true;
        }
        this.creationLinkDialog.dismiss();
        return true;
    }

    public void makeSelectedRegular() {
        applyTextStyleToSelection(null);
    }

    public void setSelectionOverride(int i, int i2) {
        this.selectionStart = i;
        this.selectionEnd = i2;
    }

    private void applyTextStyleToSelection(TextStyleSpan textStyleSpan) {
        int selectionEnd;
        int selectionStart = this.selectionStart;
        if (selectionStart >= 0 && (selectionEnd = this.selectionEnd) >= 0) {
            this.selectionEnd = -1;
            this.selectionStart = -1;
        } else {
            selectionStart = getSelectionStart();
            selectionEnd = getSelectionEnd();
        }
        MediaDataController.addStyleToText(textStyleSpan, selectionStart, selectionEnd, getText(), this.allowTextEntitiesIntersection);
        if (textStyleSpan == null) {
            Editable text = getText();
            for (CodeHighlighting.Span span : (CodeHighlighting.Span[]) text.getSpans(selectionStart, selectionEnd, CodeHighlighting.Span.class)) {
                text.removeSpan(span);
            }
            QuoteSpan[] quoteSpanArr = (QuoteSpan[]) text.getSpans(selectionStart, selectionEnd, QuoteSpan.class);
            for (int i = 0; i < quoteSpanArr.length; i++) {
                text.removeSpan(quoteSpanArr[i]);
                text.removeSpan(quoteSpanArr[i].styleSpan);
                QuoteSpan.QuoteCollapsedPart quoteCollapsedPart = quoteSpanArr[i].collapsedSpan;
                if (quoteCollapsedPart != null) {
                    text.removeSpan(quoteCollapsedPart);
                }
            }
            if (quoteSpanArr.length > 0) {
                invalidateQuotes(true);
            }
        }
        EditTextCaptionDelegate editTextCaptionDelegate = this.delegate;
        if (editTextCaptionDelegate != null) {
            editTextCaptionDelegate.onSpansChanged();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onWindowFocusChanged(boolean z) {
        try {
            super.onWindowFocusChanged(z);
        } catch (Throwable th) {
            FileLog.m1048e(th);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextCaption$5 */
    /* JADX INFO: loaded from: classes7.dex */
    public class ActionModeCallbackC42455 implements ActionMode.Callback {
        final /* synthetic */ ActionMode.Callback val$callback;

        public ActionModeCallbackC42455(ActionMode.Callback callback) {
            callback = callback;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            EditTextCaption.this.copyPasteShowed = true;
            EditTextCaption.this.onContextMenuOpen();
            return callback.onCreateActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return callback.onPrepareActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (EditTextCaption.this.performMenuAction(menuItem.getItemId())) {
                actionMode.finish();
                return true;
            }
            try {
                return callback.onActionItemClicked(actionMode, menuItem);
            } catch (Exception unused) {
                return true;
            }
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            EditTextCaption.this.copyPasteShowed = false;
            EditTextCaption.this.onContextMenuClose();
            callback.onDestroyActionMode(actionMode);
        }
    }

    private ActionMode.Callback overrideCallback(ActionMode.Callback callback) {
        return new ActionMode.Callback2() { // from class: org.telegram.ui.Components.EditTextCaption.6
            final /* synthetic */ ActionMode.Callback val$callback;
            final /* synthetic */ ActionMode.Callback val$wrap;

            public C42466(ActionMode.Callback callback2, ActionMode.Callback callback3) {
                callback = callback2;
                callback = callback3;
            }

            @Override // android.view.ActionMode.Callback
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return callback.onCreateActionMode(actionMode, menu);
            }

            @Override // android.view.ActionMode.Callback
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return callback.onPrepareActionMode(actionMode, menu);
            }

            @Override // android.view.ActionMode.Callback
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return callback.onActionItemClicked(actionMode, menuItem);
            }

            @Override // android.view.ActionMode.Callback
            public void onDestroyActionMode(ActionMode actionMode) {
                callback.onDestroyActionMode(actionMode);
            }

            @Override // android.view.ActionMode.Callback2
            public void onGetContentRect(ActionMode actionMode, View view, Rect rect) {
                ActionMode.Callback callback2 = callback;
                if (callback2 instanceof ActionMode.Callback2) {
                    ((ActionMode.Callback2) callback2).onGetContentRect(actionMode, view, rect);
                } else {
                    super.onGetContentRect(actionMode, view, rect);
                }
            }
        };
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextCaption$6 */
    /* JADX INFO: loaded from: classes7.dex */
    public class C42466 extends ActionMode.Callback2 {
        final /* synthetic */ ActionMode.Callback val$callback;
        final /* synthetic */ ActionMode.Callback val$wrap;

        public C42466(ActionMode.Callback callback2, ActionMode.Callback callback3) {
            callback = callback2;
            callback = callback3;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return callback.onCreateActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return callback.onPrepareActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return callback.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            callback.onDestroyActionMode(actionMode);
        }

        @Override // android.view.ActionMode.Callback2
        public void onGetContentRect(ActionMode actionMode, View view, Rect rect) {
            ActionMode.Callback callback2 = callback;
            if (callback2 instanceof ActionMode.Callback2) {
                ((ActionMode.Callback2) callback2).onGetContentRect(actionMode, view, rect);
            } else {
                super.onGetContentRect(actionMode, view, rect);
            }
        }
    }

    public boolean performMenuAction(int i) {
        if (i == C2797R.id.menu_regular) {
            makeSelectedRegular();
            return true;
        }
        if (i == C2797R.id.menu_bold) {
            makeSelectedBold();
            return true;
        }
        if (i == C2797R.id.menu_italic) {
            makeSelectedItalic();
            return true;
        }
        if (i == C2797R.id.menu_bold_italic) {
            makeSelectedBold();
            makeSelectedItalic();
            return true;
        }
        if (i == C2797R.id.menu_mono) {
            makeSelectedMono();
            return true;
        }
        if (i == C2797R.id.menu_code) {
            makeSelectedCode();
            return true;
        }
        if (i == C2797R.id.menu_link) {
            makeSelectedUrl();
            return true;
        }
        if (i == C2797R.id.menu_strike) {
            makeSelectedStrike();
            return true;
        }
        if (i == C2797R.id.menu_underline) {
            makeSelectedUnderline();
            return true;
        }
        if (i == C2797R.id.menu_spoiler) {
            makeSelectedSpoiler();
            return true;
        }
        if (i == C2797R.id.menu_quote) {
            makeSelectedQuote();
            return true;
        }
        if (i == C2797R.id.menu_date) {
            makeSelectedDate();
            return true;
        }
        if (i != C2797R.id.menu_translate) {
            return false;
        }
        translateSelected();
        return true;
    }

    @Override // org.telegram.p035ui.Components.EditTextBoldCursor, android.view.View
    public ActionMode startActionMode(ActionMode.Callback callback, int i) {
        return super.startActionMode(overrideCallback(callback), i);
    }

    @Override // org.telegram.p035ui.Components.EditTextBoldCursor, android.view.View
    public ActionMode startActionMode(ActionMode.Callback callback) {
        return super.startActionMode(overrideCallback(callback));
    }

    @Override // org.telegram.p035ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
    @SuppressLint({"DrawAllocation"})
    public void onMeasure(int i, int i2) {
        int iIndexOf;
        try {
            this.isInitLineCount = getMeasuredWidth() == 0 && getMeasuredHeight() == 0;
            super.onMeasure(i, i2);
            if (this.isInitLineCount) {
                this.lineCount = getLineCount();
            }
            this.isInitLineCount = false;
        } catch (Exception e) {
            setMeasuredDimension(View.MeasureSpec.getSize(i), AndroidUtilities.m1036dp(51.0f));
            FileLog.m1048e(e);
        }
        int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        Editable text = getText();
        this.captionLayout = null;
        String str = this.caption;
        this.lastLayoutText = str;
        this.lastLayoutWidth = measuredWidth;
        if (str == null || str.length() <= 0 || text.length() <= 1 || text.charAt(0) != '@' || (iIndexOf = TextUtils.indexOf((CharSequence) text, ' ')) == -1) {
            return;
        }
        TextPaint paint = getPaint();
        paint.setTypeface(AndroidUtilities.regular());
        CharSequence charSequenceSubSequence = text.subSequence(0, iIndexOf + 1);
        int iCeil = (int) Math.ceil(paint.measureText(text, 0, r0));
        int iMax = Math.max(0, measuredWidth - iCeil);
        this.userNameLength = charSequenceSubSequence.length();
        CharSequence charSequenceEllipsize = TextUtils.ellipsize(this.caption, paint, iMax, TextUtils.TruncateAt.END);
        this.xOffset = iCeil;
        try {
            StaticLayout staticLayout = new StaticLayout(charSequenceEllipsize, paint, iMax, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            this.captionLayout = staticLayout;
            if (staticLayout.getLineCount() > 0) {
                this.xOffset = (int) (this.xOffset + (-this.captionLayout.getLineLeft(0)));
            }
            this.yOffset = ((getMeasuredHeight() - this.captionLayout.getLineBottom(0)) / 2) + AndroidUtilities.m1036dp(0.5f);
        } catch (Exception e2) {
            FileLog.m1048e(e2);
        }
    }

    public boolean isNearRightCaption(int i) {
        Layout layout = getLayout();
        return layout != null && layout.getLineCount() > 0 && (layout.getLineCount() > 1 || layout.getLineRight(0) + ((float) i) >= ((float) ((getWidth() - getPaddingLeft()) - getPaddingRight())));
    }

    public String getCaption() {
        return this.caption;
    }

    @Override // org.telegram.p035ui.Components.EditTextBoldCursor
    public void setHintColor(int i) {
        super.setHintColor(i);
        this.hintColor = i;
        invalidate();
    }

    @Override // org.telegram.p035ui.Components.EditTextBoldCursor, org.telegram.p035ui.Components.EditTextEffects, android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        Canvas canvas2;
        Layout layout;
        canvas.save();
        canvas.translate(0.0f, this.offsetY);
        super.onDraw(canvas);
        try {
            if (this.captionLayout != null && this.userNameLength == length()) {
                TextPaint paint = getPaint();
                int color = getPaint().getColor();
                paint.setColor(this.hintColor);
                canvas.save();
                canvas.translate(this.xOffset, this.yOffset);
                this.captionLayout.draw(canvas);
                canvas.restore();
                paint.setColor(color);
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        if (this.rightText == null || length() == 0 || (layout = getLayout()) == null || layout.getLineCount() <= 0) {
            canvas2 = canvas;
        } else {
            canvas2 = canvas;
            this.rightText.draw(canvas2, layout.getLineRight(0), (getHeight() / 2.0f) + AndroidUtilities.m1036dp(1.0f), this.hintColor, 1.0f);
        }
        canvas2.restore();
    }

    public void setRightText(CharSequence charSequence) {
        this.rightText = new Text(charSequence, 16.0f, getTypeface());
    }

    @Override // org.telegram.p035ui.Components.EditTextBoldCursor, android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompatWrap = AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo);
        if (!TextUtils.isEmpty(this.caption)) {
            accessibilityNodeInfoCompatWrap.setHintText(this.caption);
        }
        List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actionList = accessibilityNodeInfoCompatWrap.getActionList();
        int size = actionList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat = actionList.get(i);
            if (accessibilityActionCompat.getId() == 268435456) {
                accessibilityNodeInfoCompatWrap.removeAction(accessibilityActionCompat);
                break;
            }
            i++;
        }
        if (hasSelection()) {
            accessibilityNodeInfoCompatWrap.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(C2797R.id.menu_spoiler, LocaleController.getString(C2797R.string.Spoiler)));
            accessibilityNodeInfoCompatWrap.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(C2797R.id.menu_bold, LocaleController.getString(C2797R.string.Bold)));
            accessibilityNodeInfoCompatWrap.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(C2797R.id.menu_italic, LocaleController.getString(C2797R.string.Italic)));
            accessibilityNodeInfoCompatWrap.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(C2797R.id.menu_bold_italic, LocaleController.getString(C2797R.string.BoldItalic)));
            accessibilityNodeInfoCompatWrap.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(C2797R.id.menu_mono, LocaleController.getString(C2797R.string.Mono)));
            accessibilityNodeInfoCompatWrap.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(C2797R.id.menu_code, LocaleController.getString(C2797R.string.CodeBlock)));
            accessibilityNodeInfoCompatWrap.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(C2797R.id.menu_strike, LocaleController.getString(C2797R.string.Strike)));
            accessibilityNodeInfoCompatWrap.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(C2797R.id.menu_underline, LocaleController.getString(C2797R.string.Underline)));
            accessibilityNodeInfoCompatWrap.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(C2797R.id.menu_link, LocaleController.getString(C2797R.string.CreateLink)));
            accessibilityNodeInfoCompatWrap.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(C2797R.id.menu_regular, LocaleController.getString(C2797R.string.Regular)));
            accessibilityNodeInfoCompatWrap.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(C2797R.id.menu_date, LocaleController.getString(C2797R.string.FormattedDate)));
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        return performMenuAction(i) || super.performAccessibilityAction(i, bundle);
    }

    private int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    @Override // com.exteragram.messenger.components.ReceiveContentEditText, android.widget.EditText, android.widget.TextView
    public boolean onTextContextMenuItem(int i) {
        if (i != 16908322) {
            try {
                if (i == 16908321) {
                    int iMax = Math.max(0, getSelectionStart());
                    int iMin = Math.min(getText().length(), getSelectionEnd());
                    AndroidUtilities.addToClipboard(getText().subSequence(iMax, iMin));
                    AndroidUtilities.findActivity(getContext()).closeContextMenu();
                    FloatingActionMode floatingActionMode = this.floatingActionMode;
                    if (floatingActionMode != null) {
                        floatingActionMode.finish();
                    }
                    setSelection(iMax, iMin);
                    return true;
                }
                if (i == 16908320) {
                    int iMax2 = Math.max(0, getSelectionStart());
                    int iMin2 = Math.min(getText().length(), getSelectionEnd());
                    AndroidUtilities.addToClipboard(getText().subSequence(iMax2, iMin2));
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                    if (iMax2 != 0) {
                        spannableStringBuilder.append(getText().subSequence(0, iMax2));
                    }
                    if (iMin2 != getText().length()) {
                        spannableStringBuilder.append(getText().subSequence(iMin2, getText().length()));
                    }
                    setText(spannableStringBuilder);
                    setSelection(iMax2);
                    return true;
                }
            } catch (Exception unused) {
            }
        } else if (handleRichPaste(((ClipboardManager) getContext().getSystemService("clipboard")).getPrimaryClip())) {
            return true;
        }
        return super.onTextContextMenuItem(i);
    }

    @Override // com.exteragram.messenger.components.ReceiveContentEditText, android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        if (inputConnectionOnCreateInputConnection == null) {
            return null;
        }
        String[] contentMimeTypes = EditorInfoCompat.getContentMimeTypes(editorInfo);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Collections.addAll(linkedHashSet, contentMimeTypes);
        linkedHashSet.add("text/html");
        linkedHashSet.add("text/plain");
        EditorInfoCompat.setContentMimeTypes(editorInfo, (String[]) linkedHashSet.toArray(new String[0]));
        return new InputConnectionWrapper(inputConnectionOnCreateInputConnection, true) { // from class: org.telegram.ui.Components.EditTextCaption.7
            public C42477(InputConnection inputConnectionOnCreateInputConnection2, boolean z) {
                super(inputConnectionOnCreateInputConnection2, z);
            }

            @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
            public boolean commitText(CharSequence charSequence, int i) {
                ClipData primaryClip;
                CharSequence charSequenceCoerceToText;
                if (charSequence != null && charSequence.length() > 1) {
                    try {
                        ClipboardManager clipboardManager = (ClipboardManager) EditTextCaption.this.getContext().getSystemService("clipboard");
                        if (clipboardManager != null && clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClipDescription().hasMimeType("text/html") && (primaryClip = clipboardManager.getPrimaryClip()) != null && primaryClip.getItemCount() > 0 && (charSequenceCoerceToText = primaryClip.getItemAt(0).coerceToText(EditTextCaption.this.getContext())) != null && TextUtils.equals(charSequence, charSequenceCoerceToText)) {
                            if (EditTextCaption.this.handleRichPaste(primaryClip)) {
                                return true;
                            }
                        }
                    } catch (Exception e) {
                        FileLog.m1048e(e);
                    }
                }
                return super.commitText(charSequence, i);
            }
        };
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.EditTextCaption$7 */
    /* JADX INFO: loaded from: classes7.dex */
    public class C42477 extends InputConnectionWrapper {
        public C42477(InputConnection inputConnectionOnCreateInputConnection2, boolean z) {
            super(inputConnectionOnCreateInputConnection2, z);
        }

        @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
        public boolean commitText(CharSequence charSequence, int i) {
            ClipData primaryClip;
            CharSequence charSequenceCoerceToText;
            if (charSequence != null && charSequence.length() > 1) {
                try {
                    ClipboardManager clipboardManager = (ClipboardManager) EditTextCaption.this.getContext().getSystemService("clipboard");
                    if (clipboardManager != null && clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClipDescription().hasMimeType("text/html") && (primaryClip = clipboardManager.getPrimaryClip()) != null && primaryClip.getItemCount() > 0 && (charSequenceCoerceToText = primaryClip.getItemAt(0).coerceToText(EditTextCaption.this.getContext())) != null && TextUtils.equals(charSequence, charSequenceCoerceToText)) {
                        if (EditTextCaption.this.handleRichPaste(primaryClip)) {
                            return true;
                        }
                    }
                } catch (Exception e) {
                    FileLog.m1048e(e);
                }
            }
            return super.commitText(charSequence, i);
        }
    }

    public boolean handleRichPaste(ClipData clipData) {
        if (clipData != null && clipData.getItemCount() != 0) {
            ClipDescription description = clipData.getDescription();
            if (!description.hasMimeType("image/*") && !description.hasMimeType("video/*") && !description.hasMimeType("audio/*")) {
                boolean zHasMimeType = clipData.getDescription().hasMimeType("text/html");
                boolean z = clipData.getItemAt(0).getUri() != null;
                if (zHasMimeType || z) {
                    try {
                        ClipData.Item itemAt = clipData.getItemAt(0);
                        String htmlText = itemAt.getHtmlText();
                        if (htmlText == null && itemAt.getUri() != null) {
                            htmlText = readHtmlFromUri(itemAt.getUri());
                        }
                        if (htmlText == null) {
                            return false;
                        }
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(CopyUtilities.fromHTML(htmlText));
                        Emoji.replaceEmoji((CharSequence) spannableStringBuilder, getPaint().getFontMetricsInt(), false, (int[]) null);
                        AnimatedEmojiSpan[] animatedEmojiSpanArr = (AnimatedEmojiSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), AnimatedEmojiSpan.class);
                        if (animatedEmojiSpanArr != null) {
                            for (AnimatedEmojiSpan animatedEmojiSpan : animatedEmojiSpanArr) {
                                animatedEmojiSpan.applyFontMetrics(getPaint().getFontMetricsInt(), AnimatedEmojiDrawable.getCacheTypeForEnterView());
                            }
                        }
                        int iMax = Math.max(0, getSelectionStart());
                        int iMin = Math.min(getText().length(), getSelectionEnd());
                        QuoteSpan.QuoteStyleSpan[] quoteStyleSpanArr = (QuoteSpan.QuoteStyleSpan[]) getText().getSpans(iMax, iMin, QuoteSpan.QuoteStyleSpan.class);
                        if (quoteStyleSpanArr != null && quoteStyleSpanArr.length > 0) {
                            for (QuoteSpan.QuoteStyleSpan quoteStyleSpan : (QuoteSpan.QuoteStyleSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), QuoteSpan.QuoteStyleSpan.class)) {
                                spannableStringBuilder.removeSpan(quoteStyleSpan);
                                spannableStringBuilder.removeSpan(quoteStyleSpan.span);
                            }
                        } else {
                            QuoteSpan.normalizeQuotes(spannableStringBuilder);
                        }
                        setText(getText().replace(iMax, iMin, spannableStringBuilder));
                        setSelection(spannableStringBuilder.length() + iMax, iMax + spannableStringBuilder.length());
                        return true;
                    } catch (Exception e) {
                        FileLog.m1048e(e);
                    }
                }
            }
        }
        return false;
    }

    private String readHtmlFromUri(Uri uri) {
        try {
            InputStream inputStreamOpenInputStream = getContext().getContentResolver().openInputStream(uri);
            if (inputStreamOpenInputStream == null) {
                if (inputStreamOpenInputStream == null) {
                    return null;
                }
                inputStreamOpenInputStream.close();
                return null;
            }
            try {
                StringBuilder sb = new StringBuilder();
                byte[] bArr = new byte[4096];
                int i = 0;
                do {
                    int i2 = inputStreamOpenInputStream.read(bArr);
                    if (i2 == -1) {
                        break;
                    }
                    sb.append(new String(bArr, 0, i2));
                    i += i2;
                } while (i < 32768);
                String string = sb.toString();
                inputStreamOpenInputStream.close();
                return string;
            } finally {
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
            return null;
        }
    }
}
