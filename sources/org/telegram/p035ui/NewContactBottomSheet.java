package org.telegram.p035ui;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.exteragram.messenger.utils.system.VibratorUtils;
import com.google.android.exoplayer2.util.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import kotlin.jvm.internal.LongCompanionObject;
import okhttp3.internal.url._UrlKt;
import org.mvel2.MVEL;
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.BottomSheet;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.ActionBar.ThemeDescription;
import org.telegram.p035ui.CameraScanActivity;
import org.telegram.p035ui.Components.AlertsCreator;
import org.telegram.p035ui.Components.AnimatedPhoneNumberEditText;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.p035ui.Components.CheckBox2;
import org.telegram.p035ui.Components.CircularProgressDrawable;
import org.telegram.p035ui.Components.ContextProgressView;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.OutlineEditText;
import org.telegram.p035ui.Components.OutlineTextContainerView;
import org.telegram.p035ui.Components.PermissionRequest;
import org.telegram.p035ui.Components.RadialProgressView;
import org.telegram.p035ui.CountrySelectActivity;
import org.telegram.p035ui.NewContactBottomSheet;
import org.telegram.p035ui.Stories.recorder.ButtonWithCounterView;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes6.dex */
public class NewContactBottomSheet extends BottomSheet implements AdapterView.OnItemSelectedListener {
    private CheckBox2 checkBox;
    private LinearLayout checkLayout;
    private TextView checkTextView;
    int classGuid;
    private View codeDividerView;
    private AnimatedPhoneNumberEditText codeField;
    private HashMap<String, List<CountrySelectActivity.Country>> codesMap;
    private LinearLayout contentLayout;
    private ArrayList<CountrySelectActivity.Country> countriesArray;
    private String countryCodeForHint;
    private TextView countryFlag;
    private TextView doneButton;
    private FrameLayout doneButtonContainer;
    private boolean donePressed;
    private ContextProgressView editDoneItemProgress;
    private OutlineEditText firstNameField;
    private boolean ignoreOnPhoneChange;
    private boolean ignoreOnTextChange;
    private boolean ignoreSelection;
    private String initialFirstName;
    private String initialLastName;
    private String initialPhoneNumber;
    private boolean initialPhoneNumberWithCountryCode;
    private OutlineEditText lastNameField;
    private String lastPhone;
    private OutlineEditText notesField;
    BaseFragment parentFragment;
    private AnimatedPhoneNumberEditText phoneField;
    private HashMap<String, List<String>> phoneFormatMap;
    private OutlineTextContainerView phoneOutlineView;
    private ImageView phoneStatusView;
    private TextView plusTextView;
    private RadialProgressView progressView;
    private ButtonWithCounterView qrButton;
    private FrameLayout qrButtonContainer;
    private View qrButtonSeparator;
    private int requestingPhoneId;
    private TextView underPhoneTextView;
    private int wasCountryHintIndex;

    public static class AccountInfo {
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public NewContactBottomSheet(BaseFragment baseFragment, Context context) {
        super(context, true);
        this.countriesArray = new ArrayList<>();
        this.codesMap = new HashMap<>();
        this.phoneFormatMap = new HashMap<>();
        this.requestingPhoneId = -1;
        fixNavigationBar();
        this.waitingKeyboard = true;
        this.smoothKeyboardAnimationEnabled = true;
        this.classGuid = ConnectionsManager.generateClassGuid();
        this.parentFragment = baseFragment;
        setCustomView(createView(getContext()));
        setTitle(LocaleController.getString(C2797R.string.NewContactTitle), true);
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x05b1  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x060c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View createView(android.content.Context r27) {
        /*
            Method dump skipped, instruction units count: 1866
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.NewContactBottomSheet.createView(android.content.Context):android.view.View");
    }

    public static /* synthetic */ boolean $r8$lambda$5Cd9lGyGI5QTt7YvIi2qRk8Wpg4(View view, MotionEvent motionEvent) {
        return true;
    }

    public /* synthetic */ boolean lambda$createView$1(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 5) {
            return false;
        }
        this.lastNameField.requestFocus();
        this.lastNameField.getEditText().setSelection(this.lastNameField.getEditText().length());
        return true;
    }

    public /* synthetic */ boolean lambda$createView$2(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 5) {
            return false;
        }
        this.codeField.requestFocus();
        AnimatedPhoneNumberEditText animatedPhoneNumberEditText = this.codeField;
        animatedPhoneNumberEditText.setSelection(animatedPhoneNumberEditText.length());
        return true;
    }

    /* JADX INFO: renamed from: org.telegram.ui.NewContactBottomSheet$1 */
    public class C61381 extends TextView {
        final NotificationCenter.NotificationCenterDelegate delegate;

        public C61381(Context context) {
            super(context);
            this.delegate = new NotificationCenter.NotificationCenterDelegate() { // from class: org.telegram.ui.NewContactBottomSheet$1$$ExternalSyntheticLambda0
                @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
                public final void didReceivedNotification(int i, int i2, Object[] objArr) {
                    this.f$0.lambda$$0(i, i2, objArr);
                }
            };
        }

        public /* synthetic */ void lambda$$0(int i, int i2, Object[] objArr) {
            invalidate();
        }

        @Override // android.widget.TextView, android.view.View
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            NotificationCenter.getGlobalInstance().addObserver(this.delegate, NotificationCenter.emojiLoaded);
        }

        @Override // android.view.View
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            NotificationCenter.getGlobalInstance().removeObserver(this.delegate, NotificationCenter.emojiLoaded);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.NewContactBottomSheet$2 */
    public class C61392 implements CountrySelectActivity.CountrySelectActivityDelegate {
        public C61392() {
        }

        @Override // org.telegram.ui.CountrySelectActivity.CountrySelectActivityDelegate
        public void didSelectCountry(CountrySelectActivity.Country country) {
            NewContactBottomSheet.this.selectCountry(country);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.NewContactBottomSheet$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didSelectCountry$0();
                }
            }, 300L);
            NewContactBottomSheet.this.phoneField.requestFocus();
            NewContactBottomSheet.this.phoneField.setSelection(NewContactBottomSheet.this.phoneField.length());
        }

        public /* synthetic */ void lambda$didSelectCountry$0() {
            AndroidUtilities.showKeyboard(NewContactBottomSheet.this.phoneField);
        }
    }

    public /* synthetic */ void lambda$createView$3(View view) {
        CountrySelectActivity countrySelectActivity = new CountrySelectActivity(true);
        countrySelectActivity.setCountrySelectActivityDelegate(new C61392());
        this.parentFragment.showAsSheet(countrySelectActivity);
    }

    /* JADX INFO: renamed from: org.telegram.ui.NewContactBottomSheet$3 */
    public class C61403 extends AnimatedPhoneNumberEditText {
        public C61403(Context context) {
            super(context);
        }

        @Override // org.telegram.p035ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
        public void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            NewContactBottomSheet.this.phoneOutlineView.animateSelection((z || NewContactBottomSheet.this.phoneField.isFocused()) ? 1.0f : 0.0f);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.NewContactBottomSheet$4 */
    public class C61414 implements TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public C61414() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            boolean z;
            String str;
            CountrySelectActivity.Country country;
            CountrySelectActivity.Country country2;
            if (NewContactBottomSheet.this.ignoreOnTextChange) {
                return;
            }
            NewContactBottomSheet.this.ignoreOnTextChange = true;
            String strStripExceptNumbers = PhoneFormat.stripExceptNumbers(NewContactBottomSheet.this.codeField.getText().toString());
            NewContactBottomSheet.this.codeField.setText(strStripExceptNumbers);
            if (strStripExceptNumbers.length() == 0) {
                NewContactBottomSheet.this.setCountryButtonText(null);
                NewContactBottomSheet.this.phoneField.setHintText((String) null);
            } else {
                int i = 4;
                if (strStripExceptNumbers.length() > 4) {
                    while (true) {
                        if (i < 1) {
                            z = false;
                            str = null;
                            break;
                        }
                        String strSubstring = strStripExceptNumbers.substring(0, i);
                        List list = (List) NewContactBottomSheet.this.codesMap.get(strSubstring);
                        if (list == null) {
                            country2 = null;
                        } else if (list.size() > 1) {
                            String string = MessagesController.getGlobalMainSettings().getString("phone_code_last_matched_".concat(strSubstring), null);
                            country2 = (CountrySelectActivity.Country) list.get(list.size() - 1);
                            if (string != null) {
                                ArrayList arrayList = NewContactBottomSheet.this.countriesArray;
                                int size = arrayList.size();
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= size) {
                                        break;
                                    }
                                    Object obj = arrayList.get(i2);
                                    i2++;
                                    CountrySelectActivity.Country country3 = (CountrySelectActivity.Country) obj;
                                    if (Objects.equals(country3.shortname, string)) {
                                        country2 = country3;
                                        break;
                                    }
                                }
                            }
                        } else {
                            country2 = (CountrySelectActivity.Country) list.get(0);
                        }
                        if (country2 != null) {
                            String str2 = strStripExceptNumbers.substring(i) + NewContactBottomSheet.this.phoneField.getText().toString();
                            NewContactBottomSheet.this.codeField.setText(strSubstring);
                            str = str2;
                            strStripExceptNumbers = strSubstring;
                            z = true;
                            break;
                        }
                        i--;
                    }
                    if (!z) {
                        str = strStripExceptNumbers.substring(1) + NewContactBottomSheet.this.phoneField.getText().toString();
                        AnimatedPhoneNumberEditText animatedPhoneNumberEditText = NewContactBottomSheet.this.codeField;
                        strStripExceptNumbers = strStripExceptNumbers.substring(0, 1);
                        animatedPhoneNumberEditText.setText(strStripExceptNumbers);
                    }
                } else {
                    z = false;
                    str = null;
                }
                ArrayList arrayList2 = NewContactBottomSheet.this.countriesArray;
                int size2 = arrayList2.size();
                int i3 = 0;
                int i4 = 0;
                CountrySelectActivity.Country country4 = null;
                while (i4 < size2) {
                    Object obj2 = arrayList2.get(i4);
                    i4++;
                    CountrySelectActivity.Country country5 = (CountrySelectActivity.Country) obj2;
                    if (country5.code.startsWith(strStripExceptNumbers)) {
                        i3++;
                        if (country5.code.equals(strStripExceptNumbers)) {
                            country4 = country5;
                        }
                    }
                }
                if (i3 == 1 && country4 != null && str == null) {
                    str = strStripExceptNumbers.substring(country4.code.length()) + NewContactBottomSheet.this.phoneField.getText().toString();
                    AnimatedPhoneNumberEditText animatedPhoneNumberEditText2 = NewContactBottomSheet.this.codeField;
                    String str3 = country4.code;
                    animatedPhoneNumberEditText2.setText(str3);
                    strStripExceptNumbers = str3;
                }
                List list2 = (List) NewContactBottomSheet.this.codesMap.get(strStripExceptNumbers);
                if (list2 == null) {
                    country = null;
                } else if (list2.size() > 1) {
                    String string2 = MessagesController.getGlobalMainSettings().getString("phone_code_last_matched_" + strStripExceptNumbers, null);
                    country = (CountrySelectActivity.Country) list2.get(list2.size() - 1);
                    if (string2 != null) {
                        ArrayList arrayList3 = NewContactBottomSheet.this.countriesArray;
                        int size3 = arrayList3.size();
                        int i5 = 0;
                        while (true) {
                            if (i5 >= size3) {
                                break;
                            }
                            Object obj3 = arrayList3.get(i5);
                            i5++;
                            CountrySelectActivity.Country country6 = (CountrySelectActivity.Country) obj3;
                            if (Objects.equals(country6.shortname, string2)) {
                                country = country6;
                                break;
                            }
                        }
                    }
                } else {
                    country = (CountrySelectActivity.Country) list2.get(0);
                }
                NewContactBottomSheet newContactBottomSheet = NewContactBottomSheet.this;
                if (country != null) {
                    newContactBottomSheet.ignoreSelection = true;
                    NewContactBottomSheet.this.setCountryHint(strStripExceptNumbers, country);
                } else {
                    newContactBottomSheet.setCountryButtonText(null);
                    NewContactBottomSheet.this.phoneField.setHintText((String) null);
                }
                if (!z) {
                    NewContactBottomSheet.this.codeField.setSelection(NewContactBottomSheet.this.codeField.getText().length());
                }
                if (str != null && str.length() != 0) {
                    NewContactBottomSheet.this.phoneField.requestFocus();
                    NewContactBottomSheet.this.phoneField.setText(str);
                    NewContactBottomSheet.this.phoneField.setSelection(NewContactBottomSheet.this.phoneField.length());
                }
            }
            NewContactBottomSheet.this.ignoreOnTextChange = false;
            NewContactBottomSheet.this.updatedTextPhone();
        }
    }

    public /* synthetic */ boolean lambda$createView$4(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 5) {
            return false;
        }
        this.phoneField.requestFocus();
        AnimatedPhoneNumberEditText animatedPhoneNumberEditText = this.phoneField;
        animatedPhoneNumberEditText.setSelection(animatedPhoneNumberEditText.length());
        return true;
    }

    /* JADX INFO: renamed from: org.telegram.ui.NewContactBottomSheet$5 */
    public class C61425 extends AnimatedPhoneNumberEditText {
        public C61425(Context context) {
            super(context);
        }

        @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
        public boolean onKeyDown(int i, KeyEvent keyEvent) {
            if (i == 67 && NewContactBottomSheet.this.phoneField.length() == 0) {
                NewContactBottomSheet.this.codeField.requestFocus();
                NewContactBottomSheet.this.codeField.setSelection(NewContactBottomSheet.this.codeField.length());
                NewContactBottomSheet.this.codeField.dispatchKeyEvent(keyEvent);
            }
            return super.onKeyDown(i, keyEvent);
        }

        @Override // org.telegram.p035ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
        public void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            NewContactBottomSheet.this.phoneOutlineView.animateSelection((z || NewContactBottomSheet.this.codeField.isFocused()) ? 1.0f : 0.0f);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.NewContactBottomSheet$6 */
    public class C61436 implements TextWatcher {
        private int actionPosition;
        private int characterAction = -1;

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public C61436() {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (i2 == 0 && i3 == 1) {
                this.characterAction = 1;
                return;
            }
            if (i2 == 1 && i3 == 0) {
                if (charSequence.charAt(i) == ' ' && i > 0) {
                    this.characterAction = 3;
                    this.actionPosition = i - 1;
                    return;
                } else {
                    this.characterAction = 2;
                    return;
                }
            }
            this.characterAction = -1;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            int i;
            int i2;
            if (NewContactBottomSheet.this.ignoreOnPhoneChange) {
                return;
            }
            int selectionStart = NewContactBottomSheet.this.phoneField.getSelectionStart();
            String string = NewContactBottomSheet.this.phoneField.getText().toString();
            if (this.characterAction == 3) {
                string = string.substring(0, this.actionPosition).concat(string.substring(this.actionPosition + 1));
                selectionStart--;
            }
            StringBuilder sb = new StringBuilder(string.length());
            int i3 = 0;
            while (i3 < string.length()) {
                int i4 = i3 + 1;
                String strSubstring = string.substring(i3, i4);
                if ("0123456789".contains(strSubstring)) {
                    sb.append(strSubstring);
                }
                i3 = i4;
            }
            NewContactBottomSheet.this.ignoreOnPhoneChange = true;
            String hintText = NewContactBottomSheet.this.phoneField.getHintText();
            if (hintText != null) {
                int i5 = 0;
                while (true) {
                    if (i5 >= sb.length()) {
                        break;
                    }
                    if (i5 < hintText.length()) {
                        if (hintText.charAt(i5) == ' ') {
                            sb.insert(i5, ' ');
                            i5++;
                            if (selectionStart == i5 && (i2 = this.characterAction) != 2 && i2 != 3) {
                                selectionStart++;
                            }
                        }
                        i5++;
                    } else {
                        sb.insert(i5, ' ');
                        if (selectionStart == i5 + 1 && (i = this.characterAction) != 2 && i != 3) {
                            selectionStart++;
                        }
                    }
                }
            }
            editable.replace(0, editable.length(), sb);
            if (selectionStart >= 0) {
                NewContactBottomSheet.this.phoneField.setSelection(Math.min(selectionStart, NewContactBottomSheet.this.phoneField.length()));
            }
            NewContactBottomSheet.this.phoneField.onTextChange();
            NewContactBottomSheet.this.ignoreOnPhoneChange = false;
            NewContactBottomSheet.this.updatedTextPhone();
        }
    }

    public /* synthetic */ boolean lambda$createView$5(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 5) {
            return false;
        }
        this.doneButtonContainer.callOnClick();
        return true;
    }

    public /* synthetic */ void lambda$createView$6(View view) {
        this.checkBox.setChecked(!r3.isChecked(), true);
        updateQrButtonVisible(true);
    }

    /* JADX INFO: renamed from: org.telegram.ui.NewContactBottomSheet$7 */
    public class C61447 implements CameraScanActivity.CameraScanActivityDelegate {
        public C61447() {
        }

        @Override // org.telegram.ui.CameraScanActivity.CameraScanActivityDelegate
        public void didFindQr(String str) {
            String strExtractUsername = Browser.extractUsername(str);
            if (!TextUtils.isEmpty(strExtractUsername)) {
                MessagesController.getInstance(((BottomSheet) NewContactBottomSheet.this).currentAccount).getUserNameResolver().resolve(strExtractUsername, new Consumer() { // from class: org.telegram.ui.NewContactBottomSheet$7$$ExternalSyntheticLambda0
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        NewContactBottomSheet.C61447.$r8$lambda$4nrx_iyoUVtNgJufmkiURVgZ3Mk((Long) obj);
                    }
                });
            } else {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.NewContactBottomSheet$7$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BulletinFactory.global().createSimpleBulletin(LocaleController.getString(C2797R.string.ScanQrCode), LocaleController.getString(C2797R.string.ErrorOccurred)).show();
                    }
                });
            }
        }

        public static /* synthetic */ void $r8$lambda$4nrx_iyoUVtNgJufmkiURVgZ3Mk(Long l) {
            if (l == null || l.longValue() == LongCompanionObject.MAX_VALUE) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.NewContactBottomSheet$7$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        BulletinFactory.global().createSimpleBulletin(LocaleController.getString(C2797R.string.ScanQrCode), LocaleController.getString(C2797R.string.ErrorOccurred)).show();
                    }
                });
                return;
            }
            BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
            if (safeLastFragment != null) {
                safeLastFragment.presentFragment(ProfileActivity.m1186of(l.longValue()));
            }
        }
    }

    public /* synthetic */ void lambda$createView$7(View view) {
        lambda$new$0();
        CameraScanActivity.showAsSheet((Activity) LaunchActivity.instance, false, 1, (CameraScanActivity.CameraScanActivityDelegate) new C61447());
    }

    public /* synthetic */ boolean lambda$createView$8(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 5) {
            return false;
        }
        this.codeField.requestFocus();
        AnimatedPhoneNumberEditText animatedPhoneNumberEditText = this.codeField;
        animatedPhoneNumberEditText.setSelection(animatedPhoneNumberEditText.length());
        return true;
    }

    public /* synthetic */ void lambda$createView$10(View view) {
        doOnDone();
    }

    private void updateBottomTranslation(boolean z) {
        ViewPropertyAnimator viewPropertyAnimatorTranslationY = this.checkLayout.animate().translationY(z ? -AndroidUtilities.m1036dp(21.33f) : 0.0f);
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
        viewPropertyAnimatorTranslationY.setInterpolator(cubicBezierInterpolator).setDuration(420L).start();
        this.qrButtonContainer.animate().translationY(z ? -AndroidUtilities.m1036dp(10.665f) : 0.0f).setInterpolator(cubicBezierInterpolator).setDuration(420L).start();
    }

    private void updateQrButtonVisible(boolean z) {
        boolean zIsChecked = this.checkBox.isChecked();
        final boolean z2 = !zIsChecked;
        ButtonWithCounterView buttonWithCounterView = this.qrButton;
        if (z) {
            buttonWithCounterView.setVisibility(0);
            ViewPropertyAnimator viewPropertyAnimatorAlpha = this.qrButton.animate().alpha(!zIsChecked ? 1.0f : 0.0f);
            CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
            viewPropertyAnimatorAlpha.setInterpolator(cubicBezierInterpolator).setDuration(420L).withEndAction(new Runnable() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateQrButtonVisible$11(z2);
                }
            }).start();
            this.qrButtonSeparator.setVisibility(0);
            this.qrButtonSeparator.animate().alpha(!zIsChecked ? 1.0f : 0.0f).setInterpolator(cubicBezierInterpolator).setDuration(420L).withEndAction(new Runnable() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateQrButtonVisible$12(z2);
                }
            }).start();
            this.notesField.setVisibility(0);
            this.notesField.animate().alpha(zIsChecked ? 1.0f : 0.0f).setInterpolator(cubicBezierInterpolator).setDuration(420L).withEndAction(new Runnable() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateQrButtonVisible$13(z2);
                }
            }).start();
            return;
        }
        buttonWithCounterView.animate().cancel();
        this.qrButton.setVisibility(!zIsChecked ? 0 : 4);
        this.qrButton.setAlpha(!zIsChecked ? 1.0f : 0.0f);
        this.qrButtonSeparator.animate().cancel();
        this.qrButtonSeparator.setVisibility(!zIsChecked ? 0 : 8);
        this.qrButtonSeparator.setAlpha(!zIsChecked ? 1.0f : 0.0f);
        this.notesField.animate().cancel();
        this.notesField.setVisibility(zIsChecked ? 0 : 4);
        this.notesField.setAlpha(zIsChecked ? 1.0f : 0.0f);
    }

    public /* synthetic */ void lambda$updateQrButtonVisible$11(boolean z) {
        if (z) {
            return;
        }
        this.qrButton.setVisibility(4);
    }

    public /* synthetic */ void lambda$updateQrButtonVisible$12(boolean z) {
        if (z) {
            return;
        }
        this.qrButtonSeparator.setVisibility(4);
    }

    public /* synthetic */ void lambda$updateQrButtonVisible$13(boolean z) {
        if (z) {
            this.notesField.setVisibility(4);
        }
    }

    public void updatedTextPhone() {
        String strReplaceAll = (this.codeField.getText().toString() + this.phoneField.getText().toString()).replaceAll("[^\\d]+", _UrlKt.FRAGMENT_ENCODE_SET);
        boolean z = false;
        for (int iMin = Math.min(3, strReplaceAll.length()); iMin >= 0; iMin--) {
            String strSubstring = strReplaceAll.substring(0, iMin);
            List<CountrySelectActivity.Country> list = this.codesMap.get(strSubstring);
            if (list != null && !list.isEmpty()) {
                List<String> list2 = this.phoneFormatMap.get(strSubstring);
                if (list2 != null && !list2.isEmpty()) {
                    Iterator<String> it = list2.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        if (strReplaceAll.length() - iMin >= it.next().replace(" ", _UrlKt.FRAGMENT_ENCODE_SET).length()) {
                            z = true;
                            break;
                        }
                    }
                }
            }
            if (z) {
                break;
            }
        }
        String str = this.lastPhone;
        if (!z) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            this.lastPhone = null;
            updatedPhone(null);
            return;
        }
        if (TextUtils.equals(str, strReplaceAll)) {
            return;
        }
        this.lastPhone = strReplaceAll;
        updatedPhone(strReplaceAll);
    }

    private void updatedPhone(final String str) {
        if (this.requestingPhoneId >= 0) {
            ConnectionsManager.getInstance(this.currentAccount).cancelRequest(this.requestingPhoneId, true);
            this.requestingPhoneId = -1;
        }
        boolean zIsEmpty = TextUtils.isEmpty(str);
        ImageView imageView = this.phoneStatusView;
        if (zIsEmpty) {
            imageView.animate().scaleX(0.5f).scaleY(0.5f).alpha(0.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).setDuration(420L).start();
            this.underPhoneTextView.setText(_UrlKt.FRAGMENT_ENCODE_SET);
            updateBottomTranslation(true);
            return;
        }
        imageView.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).setDuration(420L).start();
        this.phoneStatusView.setImageDrawable(new CircularProgressDrawable(AndroidUtilities.m1036dp(30.0f), AndroidUtilities.m1036dp(3.0f), getThemedColor(Theme.key_dialogTextBlue)));
        this.underPhoneTextView.setText(_UrlKt.FRAGMENT_ENCODE_SET);
        updateBottomTranslation(true);
        final Utilities.Callback callback = new Utilities.Callback() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda17
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$updatedPhone$16(str, (TLRPC.User) obj);
            }
        };
        final TLRPC.TL_contact tL_contact = ContactsController.getInstance(this.currentAccount).contactsByPhone.get(PhoneFormat.stripExceptNumbers(str));
        if (tL_contact != null) {
            TLRPC.User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(tL_contact.user_id));
            if (user != null) {
                callback.run(user);
                return;
            } else {
                MessagesStorage.getInstance(this.currentAccount).getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$updatedPhone$18(tL_contact, callback);
                    }
                });
                return;
            }
        }
        TLRPC.TL_contacts_resolvePhone tL_contacts_resolvePhone = new TLRPC.TL_contacts_resolvePhone();
        tL_contacts_resolvePhone.phone = PhoneFormat.stripExceptNumbers(str);
        this.requestingPhoneId = ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_contacts_resolvePhone, new RequestDelegate() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda19
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$updatedPhone$20(callback, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$updatedPhone$16(final String str, final TLRPC.User user) {
        if (user == null) {
            this.phoneStatusView.setImageDrawable(null);
            this.underPhoneTextView.setText(AndroidUtilities.replaceArrows(AndroidUtilities.replaceSingleTag("This phone number is not on Telegram. **Invite >**", new Runnable() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updatedPhone$14(str);
                }
            }), true, AndroidUtilities.m1036dp(2.6666667f), AndroidUtilities.m1036dp(1.0f)));
        } else {
            Drawable drawableMutate = getContext().getResources().getDrawable(C2797R.drawable.msg_text_check).mutate();
            drawableMutate.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_windowBackgroundWhiteBlueIcon), PorterDuff.Mode.SRC_IN));
            this.phoneStatusView.setImageDrawable(drawableMutate);
            boolean z = user.contact;
            TextView textView = this.underPhoneTextView;
            if (z) {
                textView.setText(AndroidUtilities.replaceArrows(AndroidUtilities.replaceSingleTag("This phone number is already in your contacts. **View >**", new Runnable() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda24
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$updatedPhone$15(user);
                    }
                }), true, AndroidUtilities.m1036dp(2.6666667f), AndroidUtilities.m1036dp(1.0f)));
            } else {
                textView.setText("This phone number is on Telegram.");
            }
        }
        updateBottomTranslation(false);
    }

    public /* synthetic */ void lambda$updatedPhone$14(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("sms:+" + str));
        intent.putExtra("sms_body", LocaleController.formatString(C2797R.string.InviteText2, "https://telegram.org/dl"));
        getContext().startActivity(intent);
    }

    public /* synthetic */ void lambda$updatedPhone$15(TLRPC.User user) {
        lambda$new$0();
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        if (safeLastFragment != null) {
            safeLastFragment.presentFragment(ProfileActivity.m1186of(user.f1407id));
        }
    }

    public /* synthetic */ void lambda$updatedPhone$18(TLRPC.TL_contact tL_contact, final Utilities.Callback callback) {
        final TLRPC.User user = MessagesStorage.getInstance(this.currentAccount).getUser(tL_contact.user_id);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                callback.run(user);
            }
        });
    }

    public /* synthetic */ void lambda$updatedPhone$20(final Utilities.Callback callback, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updatedPhone$19(tLObject, callback);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0038  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$updatedPhone$19(org.telegram.tgnet.TLObject r5, org.telegram.messenger.Utilities.Callback r6) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof org.telegram.tgnet.TLRPC.TL_contacts_resolvedPeer
            if (r0 == 0) goto L38
            org.telegram.tgnet.TLRPC$TL_contacts_resolvedPeer r5 = (org.telegram.tgnet.TLRPC.TL_contacts_resolvedPeer) r5
            int r0 = r4.currentAccount
            org.telegram.messenger.MessagesController r0 = org.telegram.messenger.MessagesController.getInstance(r0)
            java.util.ArrayList<org.telegram.tgnet.TLRPC$User> r1 = r5.users
            r2 = 0
            r0.putUsers(r1, r2)
            int r0 = r4.currentAccount
            org.telegram.messenger.MessagesController r0 = org.telegram.messenger.MessagesController.getInstance(r0)
            java.util.ArrayList<org.telegram.tgnet.TLRPC$Chat> r1 = r5.chats
            r0.putChats(r1, r2)
            org.telegram.tgnet.TLRPC$Peer r5 = r5.peer
            long r0 = org.telegram.messenger.DialogObject.getPeerDialogId(r5)
            r2 = 0
            int r5 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r5 < 0) goto L38
            int r4 = r4.currentAccount
            org.telegram.messenger.MessagesController r4 = org.telegram.messenger.MessagesController.getInstance(r4)
            java.lang.Long r5 = java.lang.Long.valueOf(r0)
            org.telegram.tgnet.TLRPC$User r4 = r4.getUser(r5)
            goto L39
        L38:
            r4 = 0
        L39:
            r6.run(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.NewContactBottomSheet.lambda$updatedPhone$19(org.telegram.tgnet.TLObject, org.telegram.messenger.Utilities$Callback):void");
    }

    private void doOnDone() {
        BaseFragment baseFragment;
        if (this.donePressed || (baseFragment = this.parentFragment) == null || baseFragment.getParentActivity() == null) {
            return;
        }
        if (this.firstNameField.getEditText().length() == 0) {
            VibratorUtils.vibrate();
            AndroidUtilities.shakeView(this.firstNameField);
            return;
        }
        if (this.codeField.length() == 0) {
            VibratorUtils.vibrate();
            AndroidUtilities.shakeView(this.codeField);
        } else if (this.phoneField.length() == 0) {
            VibratorUtils.vibrate();
            AndroidUtilities.shakeView(this.phoneField);
        } else if (this.checkBox.isChecked()) {
            PermissionRequest.ensurePermission(C2797R.raw.permission_request_contacts, C2797R.string.PermissionNoContactsSaving, "android.permission.WRITE_CONTACTS", new Utilities.Callback() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda13
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$doOnDone$21((Boolean) obj);
                }
            });
        } else {
            done();
        }
    }

    public /* synthetic */ void lambda$doOnDone$21(Boolean bool) {
        if (bool.booleanValue()) {
            done();
        }
    }

    private void done() {
        this.donePressed = true;
        showEditDoneProgress(true, true);
        String str = "+" + this.codeField.getText().toString() + this.phoneField.getText().toString();
        String string = this.firstNameField.getEditText().getText().toString();
        String string2 = this.lastNameField.getEditText().getText().toString();
        String string3 = this.notesField.getVisibility() == 0 ? this.notesField.getEditText().getText().toString() : _UrlKt.FRAGMENT_ENCODE_SET;
        final TLRPC.TL_contacts_importContacts tL_contacts_importContacts = new TLRPC.TL_contacts_importContacts();
        final TLRPC.TL_inputPhoneContact tL_inputPhoneContact = new TLRPC.TL_inputPhoneContact();
        tL_inputPhoneContact.first_name = string;
        tL_inputPhoneContact.last_name = string2;
        tL_inputPhoneContact.phone = str;
        if (!TextUtils.isEmpty(string3)) {
            tL_inputPhoneContact.flags = 1 | tL_inputPhoneContact.flags;
            TLRPC.TL_textWithEntities tL_textWithEntities = new TLRPC.TL_textWithEntities();
            tL_inputPhoneContact.note = tL_textWithEntities;
            tL_textWithEntities.text = string3;
        }
        tL_contacts_importContacts.contacts.add(tL_inputPhoneContact);
        ConnectionsManager.getInstance(this.currentAccount).bindRequestToGuid(ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_contacts_importContacts, new RequestDelegate() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda20
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$done$23(tL_inputPhoneContact, tL_contacts_importContacts, tLObject, tL_error);
            }
        }, 2), this.classGuid);
        if (this.checkBox.isChecked()) {
            saveContact(getContext(), str, string, string2, null, null);
        }
    }

    public /* synthetic */ void lambda$done$23(final TLRPC.TL_inputPhoneContact tL_inputPhoneContact, final TLRPC.TL_contacts_importContacts tL_contacts_importContacts, TLObject tLObject, final TLRPC.TL_error tL_error) {
        final TLRPC.TL_contacts_importedContacts tL_contacts_importedContacts = (TLRPC.TL_contacts_importedContacts) tLObject;
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$done$22(tL_contacts_importedContacts, tL_inputPhoneContact, tL_error, tL_contacts_importContacts);
            }
        });
    }

    public /* synthetic */ void lambda$done$22(TLRPC.TL_contacts_importedContacts tL_contacts_importedContacts, TLRPC.TL_inputPhoneContact tL_inputPhoneContact, TLRPC.TL_error tL_error, TLRPC.TL_contacts_importContacts tL_contacts_importContacts) {
        this.donePressed = false;
        if (tL_contacts_importedContacts != null) {
            if (!tL_contacts_importedContacts.users.isEmpty()) {
                MessagesController.getInstance(this.currentAccount).putUsers(tL_contacts_importedContacts.users, false);
                MessagesController.getInstance(this.currentAccount).openChatOrProfileWith(tL_contacts_importedContacts.users.get(0), null, this.parentFragment, 1, false);
                lambda$new$0();
                return;
            } else {
                if (this.parentFragment.getParentActivity() == null) {
                    return;
                }
                showEditDoneProgress(false, true);
                AlertsCreator.createContactInviteDialog(this.parentFragment, tL_inputPhoneContact.first_name, tL_inputPhoneContact.last_name, tL_inputPhoneContact.phone);
                return;
            }
        }
        showEditDoneProgress(false, true);
        AlertsCreator.processError(this.currentAccount, tL_error, this.parentFragment, tL_contacts_importContacts, new Object[0]);
    }

    @Override // org.telegram.p035ui.ActionBar.BottomSheet, android.app.Dialog
    public void show() {
        super.show();
        this.firstNameField.getEditText().requestFocus();
        this.firstNameField.getEditText().setSelection(this.firstNameField.getEditText().length());
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$show$24();
            }
        }, 50L);
    }

    public /* synthetic */ void lambda$show$24() {
        AndroidUtilities.showKeyboard(this.firstNameField.getEditText());
    }

    private void showEditDoneProgress(boolean z, boolean z2) {
        AndroidUtilities.updateViewVisibilityAnimated(this.doneButton, !z, 0.5f, z2);
        AndroidUtilities.updateViewVisibilityAnimated(this.progressView, z, 0.5f, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getPhoneNumber(android.content.Context r5, org.telegram.tgnet.TLRPC.User r6, java.lang.String r7, boolean r8) {
        /*
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r1 = 0
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Exception -> L33
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Exception -> L33
            android.content.res.Resources r5 = r5.getResources()     // Catch: java.lang.Exception -> L33
            android.content.res.AssetManager r5 = r5.getAssets()     // Catch: java.lang.Exception -> L33
            java.lang.String r4 = "countries.txt"
            java.io.InputStream r5 = r5.open(r4)     // Catch: java.lang.Exception -> L33
            r3.<init>(r5)     // Catch: java.lang.Exception -> L33
            r2.<init>(r3)     // Catch: java.lang.Exception -> L33
        L1e:
            java.lang.String r5 = r2.readLine()     // Catch: java.lang.Exception -> L33
            if (r5 == 0) goto L35
            java.lang.String r3 = ";"
            java.lang.String[] r5 = r5.split(r3)     // Catch: java.lang.Exception -> L33
            r3 = r5[r1]     // Catch: java.lang.Exception -> L33
            r4 = 2
            r5 = r5[r4]     // Catch: java.lang.Exception -> L33
            r0.put(r3, r5)     // Catch: java.lang.Exception -> L33
            goto L1e
        L33:
            r5 = move-exception
            goto L39
        L35:
            r2.close()     // Catch: java.lang.Exception -> L33
            goto L3c
        L39:
            org.telegram.messenger.FileLog.m1048e(r5)
        L3c:
            java.lang.String r5 = "+"
            boolean r2 = r7.startsWith(r5)
            if (r2 == 0) goto L45
            goto L77
        L45:
            if (r8 != 0) goto L78
            if (r6 == 0) goto L78
            java.lang.String r8 = r6.phone
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 == 0) goto L52
            goto L78
        L52:
            java.lang.String r6 = r6.phone
            r8 = 4
        L55:
            r2 = 1
            if (r8 < r2) goto L77
            java.lang.String r2 = r6.substring(r1, r8)
            java.lang.Object r3 = r0.get(r2)
            java.lang.String r3 = (java.lang.String) r3
            if (r3 == 0) goto L74
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r5)
            r6.append(r2)
            r6.append(r7)
            java.lang.String r5 = r6.toString()
            return r5
        L74:
            int r8 = r8 + (-1)
            goto L55
        L77:
            return r7
        L78:
            java.lang.String r5 = r5.concat(r7)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.NewContactBottomSheet.getPhoneNumber(android.content.Context, org.telegram.tgnet.TLRPC$User, java.lang.String, boolean):java.lang.String");
    }

    public NewContactBottomSheet setInitialPhoneNumber(String str, boolean z) {
        this.initialPhoneNumber = str;
        this.initialPhoneNumberWithCountryCode = z;
        if (!TextUtils.isEmpty(str)) {
            TLRPC.User currentUser = UserConfig.getInstance(this.currentAccount).getCurrentUser();
            if (this.initialPhoneNumber.startsWith("+")) {
                this.codeField.setText(this.initialPhoneNumber.substring(1));
            } else if (this.initialPhoneNumberWithCountryCode || currentUser == null || TextUtils.isEmpty(currentUser.phone)) {
                this.codeField.setText(this.initialPhoneNumber);
            } else {
                String str2 = currentUser.phone;
                int i = 4;
                while (true) {
                    if (i >= 1) {
                        List<CountrySelectActivity.Country> list = this.codesMap.get(str2.substring(0, i));
                        if (list == null || list.size() <= 0) {
                            i--;
                        } else {
                            String str3 = list.get(0).code;
                            this.codeField.setText(str3);
                            if (str3.endsWith(MVEL.VERSION_SUB) && this.initialPhoneNumber.startsWith(MVEL.VERSION_SUB)) {
                                this.initialPhoneNumber = this.initialPhoneNumber.substring(1);
                            }
                        }
                    } else {
                        Context context = ApplicationLoader.applicationContext;
                        String upperCase = context != null ? ((TelephonyManager) context.getSystemService(TelephonyManager.class)).getSimCountryIso().toUpperCase(Locale.US) : Locale.getDefault().getCountry();
                        this.codeField.setText(upperCase);
                        if (upperCase.endsWith(MVEL.VERSION_SUB) && this.initialPhoneNumber.startsWith(MVEL.VERSION_SUB)) {
                            this.initialPhoneNumber = this.initialPhoneNumber.substring(1);
                        }
                    }
                }
                this.phoneField.setText(this.initialPhoneNumber);
            }
            this.initialPhoneNumber = null;
        }
        return this;
    }

    public void setInitialName(String str, String str2) {
        OutlineEditText outlineEditText = this.firstNameField;
        if (outlineEditText != null) {
            outlineEditText.getEditText().setText(str);
        } else {
            this.initialFirstName = str;
        }
        OutlineEditText outlineEditText2 = this.lastNameField;
        if (outlineEditText2 != null) {
            outlineEditText2.getEditText().setText(str2);
        } else {
            this.initialLastName = str2;
        }
    }

    public void setCountryHint(String str, CountrySelectActivity.Country country) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String languageFlag = LocaleController.getLanguageFlag(country.shortname);
        if (languageFlag != null) {
            spannableStringBuilder.append((CharSequence) languageFlag);
        }
        setCountryButtonText(Emoji.replaceEmoji(spannableStringBuilder, this.countryFlag.getPaint().getFontMetricsInt(), false));
        this.countryCodeForHint = str;
        this.wasCountryHintIndex = -1;
        invalidateCountryHint();
    }

    public void setCountryButtonText(CharSequence charSequence) {
        boolean zIsEmpty = TextUtils.isEmpty(charSequence);
        TextView textView = this.countryFlag;
        if (zIsEmpty) {
            ViewPropertyAnimator viewPropertyAnimatorAnimate = textView.animate();
            CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.DEFAULT;
            viewPropertyAnimatorAnimate.setInterpolator(cubicBezierInterpolator).translationY(AndroidUtilities.m1036dp(30.0f)).setDuration(150L);
            this.plusTextView.animate().setInterpolator(cubicBezierInterpolator).translationX(-AndroidUtilities.m1036dp(30.0f)).setDuration(150L);
            this.codeField.animate().setInterpolator(cubicBezierInterpolator).translationX(-AndroidUtilities.m1036dp(30.0f)).setDuration(150L);
            return;
        }
        textView.animate().setInterpolator(AndroidUtilities.overshootInterpolator).translationY(0.0f).setDuration(350L).start();
        ViewPropertyAnimator viewPropertyAnimatorAnimate2 = this.plusTextView.animate();
        CubicBezierInterpolator cubicBezierInterpolator2 = CubicBezierInterpolator.DEFAULT;
        viewPropertyAnimatorAnimate2.setInterpolator(cubicBezierInterpolator2).translationX(0.0f).setDuration(150L);
        this.codeField.animate().setInterpolator(cubicBezierInterpolator2).translationX(0.0f).setDuration(150L);
        this.countryFlag.setText(charSequence);
    }

    /* JADX WARN: Removed duplicated region for block: B:85:0x0090 A[PHI: r7
  0x0090: PHI (r7v3 int) = (r7v2 int), (r7v4 int) binds: [B:72:0x006d, B:83:0x008d] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void invalidateCountryHint() {
        /*
            Method dump skipped, instruction units count: 227
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.NewContactBottomSheet.invalidateCountryHint():void");
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.ignoreSelection) {
            this.ignoreSelection = false;
            return;
        }
        this.ignoreOnTextChange = true;
        this.codeField.setText(this.countriesArray.get(i).code);
        this.ignoreOnTextChange = false;
    }

    public void selectCountry(CountrySelectActivity.Country country) {
        this.ignoreOnTextChange = true;
        String str = country.code;
        this.codeField.setText(str);
        setCountryHint(str, country);
        this.ignoreOnTextChange = false;
    }

    @Override // org.telegram.p035ui.ActionBar.BottomSheet
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        OutlineEditText outlineEditText = this.firstNameField;
        int i = ThemeDescription.FLAG_TEXTCOLOR;
        int i2 = Theme.key_windowBackgroundWhiteBlackText;
        arrayList.add(new ThemeDescription(outlineEditText, i, null, null, null, null, i2));
        OutlineEditText outlineEditText2 = this.firstNameField;
        int i3 = ThemeDescription.FLAG_HINTTEXTCOLOR;
        int i4 = Theme.key_windowBackgroundWhiteHintText;
        arrayList.add(new ThemeDescription(outlineEditText2, i3, null, null, null, null, i4));
        OutlineEditText outlineEditText3 = this.firstNameField;
        int i5 = ThemeDescription.FLAG_BACKGROUNDFILTER;
        int i6 = Theme.key_windowBackgroundWhiteInputField;
        arrayList.add(new ThemeDescription(outlineEditText3, i5, null, null, null, null, i6));
        OutlineEditText outlineEditText4 = this.firstNameField;
        int i7 = ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE;
        int i8 = Theme.key_windowBackgroundWhiteInputFieldActivated;
        arrayList.add(new ThemeDescription(outlineEditText4, i7, null, null, null, null, i8));
        arrayList.add(new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, i4));
        arrayList.add(new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, i6));
        arrayList.add(new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_DRAWABLESELECTEDSTATE | ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, i8));
        arrayList.add(new ThemeDescription(this.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, i6));
        arrayList.add(new ThemeDescription(this.codeField, ThemeDescription.FLAG_DRAWABLESELECTEDSTATE | ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, i8));
        arrayList.add(new ThemeDescription(this.phoneField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.phoneField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, i4));
        arrayList.add(new ThemeDescription(this.phoneField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, i6));
        arrayList.add(new ThemeDescription(this.phoneField, ThemeDescription.FLAG_DRAWABLESELECTEDSTATE | ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, i8));
        arrayList.add(new ThemeDescription(this.editDoneItemProgress, 0, null, null, null, null, Theme.key_contextProgressInner2));
        arrayList.add(new ThemeDescription(this.editDoneItemProgress, 0, null, null, null, null, Theme.key_contextProgressOuter2));
        return arrayList;
    }

    @Override // org.telegram.p035ui.ActionBar.BottomSheet, android.app.Dialog, android.content.DialogInterface, org.telegram.ui.ActionBar.BaseFragment.AttachedSheet
    /* JADX INFO: renamed from: dismiss */
    public void lambda$new$0() {
        super.lambda$new$0();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.NewContactBottomSheet$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$dismiss$25();
            }
        }, 50L);
    }

    public /* synthetic */ void lambda$dismiss$25() {
        AndroidUtilities.hideKeyboard(this.contentLayout);
    }

    public static boolean saveContact(Context context, String str, String str2, String str3, String str4, AccountInfo accountInfo) {
        ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
        ContentProviderOperation.Builder builderNewInsert = ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI);
        builderNewInsert.withValue("account_type", null);
        builderNewInsert.withValue("account_name", null);
        arrayList.add(builderNewInsert.build());
        Uri uri = ContactsContract.Data.CONTENT_URI;
        ContentProviderOperation.Builder builderWithValue = ContentProviderOperation.newInsert(uri).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/name");
        if (!TextUtils.isEmpty(str2)) {
            builderWithValue = builderWithValue.withValue("data2", str2);
        }
        if (!TextUtils.isEmpty(str3)) {
            builderWithValue = builderWithValue.withValue("data2", str3);
        }
        arrayList.add(builderWithValue.build());
        if (str != null && !str.isEmpty()) {
            arrayList.add(ContentProviderOperation.newInsert(uri).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/phone_v2").withValue("data1", str).withValue("data2", 2).build());
        }
        if (str4 != null && !str4.isEmpty()) {
            arrayList.add(ContentProviderOperation.newInsert(uri).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/note").withValue("data1", str4).build());
        }
        try {
            context.getContentResolver().applyBatch("com.android.contacts", arrayList);
            return true;
        } catch (OperationApplicationException | RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
