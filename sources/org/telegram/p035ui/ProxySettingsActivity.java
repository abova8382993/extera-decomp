package org.telegram.p035ui;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import com.exteragram.messenger.proxy.ProxyController;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import okhttp3.internal.url._UrlKt;
import org.mvel2.MVEL;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.SvgHelper;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.ActionBar;
import org.telegram.p035ui.ActionBar.ActionBarMenuItem;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.INavigationLayout;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.ActionBar.ThemeDescription;
import org.telegram.p035ui.Cells.HeaderCell;
import org.telegram.p035ui.Cells.RadioCell;
import org.telegram.p035ui.Cells.ShadowSectionCell;
import org.telegram.p035ui.Cells.TextInfoPrivacyCell;
import org.telegram.p035ui.Cells.TextSettingsCell;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.EditTextBoldCursor;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.QRCodeBottomSheet;
import org.telegram.tgnet.ConnectionsManager;

/* JADX INFO: loaded from: classes6.dex */
public class ProxySettingsActivity extends BaseFragment {
    private boolean addingNewProxy;
    private TextInfoPrivacyCell[] bottomCells;
    private ClipboardManager.OnPrimaryClipChangedListener clipChangedListener;
    private ClipboardManager clipboardManager;
    private SharedConfig.ProxyInfo currentProxyInfo;
    private int currentType;
    private ActionBarMenuItem doneItem;
    private HeaderCell headerCell;
    private boolean ignoreOnTextChange;
    private EditTextBoldCursor[] inputFields;
    private LinearLayout inputFieldsContainer;
    private LinearLayout linearLayout2;
    private TextSettingsCell pasteCell;
    private String[] pasteFields;
    private String pasteString;
    private int pasteType;
    private ScrollView scrollView;
    private ShadowSectionCell[] sectionCell;
    private TextSettingsCell shareCell;
    private ValueAnimator shareDoneAnimator;
    private boolean shareDoneEnabled;
    private float shareDoneProgress;
    private float[] shareDoneProgressAnimValues;
    private RadioCell[] typeCell;

    public ProxySettingsActivity() {
        this.sectionCell = new ShadowSectionCell[3];
        this.bottomCells = new TextInfoPrivacyCell[2];
        this.typeCell = new RadioCell[2];
        this.currentType = -1;
        this.pasteType = -1;
        this.shareDoneProgress = 1.0f;
        this.shareDoneProgressAnimValues = new float[2];
        this.shareDoneEnabled = true;
        this.clipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() { // from class: org.telegram.ui.ProxySettingsActivity$$ExternalSyntheticLambda0
            @Override // android.content.ClipboardManager.OnPrimaryClipChangedListener
            public final void onPrimaryClipChanged() {
                this.f$0.updatePasteCell();
            }
        };
        this.currentProxyInfo = new SharedConfig.ProxyInfo(_UrlKt.FRAGMENT_ENCODE_SET, 1080, _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET);
        this.addingNewProxy = true;
    }

    public ProxySettingsActivity(SharedConfig.ProxyInfo proxyInfo) {
        this.sectionCell = new ShadowSectionCell[3];
        this.bottomCells = new TextInfoPrivacyCell[2];
        this.typeCell = new RadioCell[2];
        this.currentType = -1;
        this.pasteType = -1;
        this.shareDoneProgress = 1.0f;
        this.shareDoneProgressAnimValues = new float[2];
        this.shareDoneEnabled = true;
        this.clipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() { // from class: org.telegram.ui.ProxySettingsActivity$$ExternalSyntheticLambda0
            @Override // android.content.ClipboardManager.OnPrimaryClipChangedListener
            public final void onPrimaryClipChanged() {
                this.f$0.updatePasteCell();
            }
        };
        this.currentProxyInfo = proxyInfo;
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
        this.clipboardManager.addPrimaryClipChangedListener(this.clipChangedListener);
        updatePasteCell();
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
        this.clipboardManager.removePrimaryClipChangedListener(this.clipChangedListener);
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public View createView(final Context context) {
        this.actionBar.setTitle(LocaleController.getString(C2797R.string.ProxyDetails));
        this.actionBar.setBackButtonImage(C2797R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(false);
        INavigationLayout iNavigationLayout = this.parentLayout;
        if (iNavigationLayout != null && iNavigationLayout.isLayersLayout()) {
            this.actionBar.setOccupyStatusBar(false);
        }
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.ProxySettingsActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    ProxySettingsActivity.this.finishFragment();
                    return;
                }
                if (i != 1 || ProxySettingsActivity.this.getParentActivity() == null) {
                    return;
                }
                String strTrim = ProxySettingsActivity.this.inputFields[0].getText().toString().trim();
                String link = ProxySettingsActivity.this.addingNewProxy ? null : ProxySettingsActivity.this.currentProxyInfo.getLink();
                ProxySettingsActivity.this.currentProxyInfo.address = ProxySettingsActivity.this.inputFields[1].getText().toString();
                ProxySettingsActivity.this.currentProxyInfo.port = Utilities.parseInt((CharSequence) ProxySettingsActivity.this.inputFields[2].getText().toString()).intValue();
                int i2 = ProxySettingsActivity.this.currentType;
                ProxySettingsActivity proxySettingsActivity = ProxySettingsActivity.this;
                if (i2 == 0) {
                    proxySettingsActivity.currentProxyInfo.secret = _UrlKt.FRAGMENT_ENCODE_SET;
                    ProxySettingsActivity.this.currentProxyInfo.username = ProxySettingsActivity.this.inputFields[3].getText().toString();
                    ProxySettingsActivity.this.currentProxyInfo.password = ProxySettingsActivity.this.inputFields[4].getText().toString();
                } else {
                    proxySettingsActivity.currentProxyInfo.secret = ProxySettingsActivity.this.inputFields[5].getText().toString();
                    ProxySettingsActivity.this.currentProxyInfo.username = _UrlKt.FRAGMENT_ENCODE_SET;
                    ProxySettingsActivity.this.currentProxyInfo.password = _UrlKt.FRAGMENT_ENCODE_SET;
                }
                ProxyController proxyController = ProxyController.getInstance();
                SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
                boolean z = ProxySettingsActivity.this.addingNewProxy || globalMainSettings.getBoolean("proxy_enabled", false);
                ProxySettingsActivity proxySettingsActivity2 = ProxySettingsActivity.this;
                proxySettingsActivity2.currentProxyInfo = proxyController.saveProxy(proxySettingsActivity2.currentProxyInfo, link, strTrim);
                if (ProxySettingsActivity.this.addingNewProxy) {
                    proxyController.setCurrentProxy(ProxySettingsActivity.this.currentProxyInfo);
                }
                if (ProxySettingsActivity.this.addingNewProxy || proxyController.getCurrentProxy() == ProxySettingsActivity.this.currentProxyInfo) {
                    SharedPreferences.Editor editorEdit = globalMainSettings.edit();
                    editorEdit.putBoolean("proxy_enabled", z);
                    editorEdit.putString("proxy_ip", ProxySettingsActivity.this.currentProxyInfo.address);
                    editorEdit.putString("proxy_pass", ProxySettingsActivity.this.currentProxyInfo.password);
                    editorEdit.putString("proxy_user", ProxySettingsActivity.this.currentProxyInfo.username);
                    editorEdit.putInt("proxy_port", ProxySettingsActivity.this.currentProxyInfo.port);
                    editorEdit.putString("proxy_secret", ProxySettingsActivity.this.currentProxyInfo.secret);
                    ConnectionsManager.setProxySettings(z, ProxySettingsActivity.this.currentProxyInfo.address, ProxySettingsActivity.this.currentProxyInfo.port, ProxySettingsActivity.this.currentProxyInfo.username, ProxySettingsActivity.this.currentProxyInfo.password, ProxySettingsActivity.this.currentProxyInfo.secret);
                    editorEdit.apply();
                }
                NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.proxySettingsChanged, new Object[0]);
                ProxySettingsActivity.this.finishFragment();
            }
        });
        ActionBarMenuItem actionBarMenuItemAddItemWithWidth = this.actionBar.createMenu().addItemWithWidth(1, C2797R.drawable.ic_ab_done, AndroidUtilities.m1036dp(56.0f));
        this.doneItem = actionBarMenuItemAddItemWithWidth;
        actionBarMenuItemAddItemWithWidth.setContentDescription(LocaleController.getString(C2797R.string.Done));
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        ScrollView scrollView = new ScrollView(context);
        this.scrollView = scrollView;
        scrollView.setFillViewport(true);
        AndroidUtilities.setScrollViewEdgeEffectColor(this.scrollView, Theme.getColor(Theme.key_actionBarDefault));
        frameLayout.addView(this.scrollView, LayoutHelper.createFrame(-1, -1.0f));
        LinearLayout linearLayout = new LinearLayout(context);
        this.linearLayout2 = linearLayout;
        linearLayout.setOrientation(1);
        this.scrollView.addView(this.linearLayout2, new FrameLayout.LayoutParams(-1, -2));
        int i = 6;
        this.inputFields = new EditTextBoldCursor[6];
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: org.telegram.ui.ProxySettingsActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$0(view);
            }
        };
        int i2 = 0;
        while (i2 < 2) {
            this.typeCell[i2] = new RadioCell(context);
            this.typeCell[i2].setBackground(Theme.getSelectorDrawable(true));
            this.typeCell[i2].setTag(Integer.valueOf(i2));
            RadioCell[] radioCellArr = this.typeCell;
            if (i2 == 0) {
                radioCellArr[i2].setText(LocaleController.getString(C2797R.string.UseProxySocks5), i2 == this.currentType, true);
            } else {
                radioCellArr[i2].setText(LocaleController.getString(C2797R.string.UseProxyTelegram), i2 == this.currentType, false);
            }
            this.linearLayout2.addView(this.typeCell[i2], LayoutHelper.createLinear(-1, 50));
            this.typeCell[i2].setOnClickListener(onClickListener);
            i2++;
        }
        this.sectionCell[0] = new ShadowSectionCell(context);
        this.linearLayout2.addView(this.sectionCell[0], LayoutHelper.createLinear(-1, -2));
        LinearLayout linearLayout2 = new LinearLayout(context);
        this.inputFieldsContainer = linearLayout2;
        linearLayout2.setOrientation(1);
        this.inputFieldsContainer.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.inputFieldsContainer.setElevation(AndroidUtilities.m1036dp(1.0f));
        Drawable drawable = null;
        this.inputFieldsContainer.setOutlineProvider(null);
        this.linearLayout2.addView(this.inputFieldsContainer, LayoutHelper.createLinear(-1, -2));
        int i3 = 0;
        while (i3 < i) {
            FrameLayout frameLayout2 = new FrameLayout(context);
            this.inputFieldsContainer.addView(frameLayout2, LayoutHelper.createLinear(-1, 64));
            this.inputFields[i3] = new EditTextBoldCursor(context);
            this.inputFields[i3].setTag(Integer.valueOf(i3));
            this.inputFields[i3].setTextSize(1, 16.0f);
            this.inputFields[i3].setHintColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            EditTextBoldCursor editTextBoldCursor = this.inputFields[i3];
            int i4 = Theme.key_windowBackgroundWhiteBlackText;
            editTextBoldCursor.setTextColor(Theme.getColor(i4));
            this.inputFields[i3].setBackground(drawable);
            this.inputFields[i3].setCursorColor(Theme.getColor(i4));
            this.inputFields[i3].setCursorSize(AndroidUtilities.m1036dp(20.0f));
            this.inputFields[i3].setCursorWidth(1.5f);
            this.inputFields[i3].setSingleLine(true);
            this.inputFields[i3].setGravity((LocaleController.isRTL ? 5 : 3) | 16);
            this.inputFields[i3].setHeaderHintColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueHeader));
            this.inputFields[i3].setTransformHintToHeader(true);
            this.inputFields[i3].setLineColors(Theme.getColor(Theme.key_windowBackgroundWhiteInputField), Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated), Theme.getColor(Theme.key_text_RedRegular));
            if (i3 == 0) {
                this.inputFields[i3].setInputType(524289);
                this.inputFields[i3].addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.ProxySettingsActivity.2
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence charSequence, int i5, int i6, int i7) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int i5, int i6, int i7) {
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable editable) {
                        ProxySettingsActivity.this.updateActionBarTitle();
                    }
                });
            } else if (i3 == 1) {
                this.inputFields[i3].setInputType(524305);
                this.inputFields[i3].addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.ProxySettingsActivity.3
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence charSequence, int i5, int i6, int i7) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int i5, int i6, int i7) {
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable editable) {
                        ProxySettingsActivity.this.checkShareDone(true);
                    }
                });
            } else if (i3 == 2) {
                this.inputFields[i3].setInputType(2);
                this.inputFields[i3].addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.ProxySettingsActivity.4
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence charSequence, int i5, int i6, int i7) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int i5, int i6, int i7) {
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable editable) {
                        if (ProxySettingsActivity.this.ignoreOnTextChange) {
                            return;
                        }
                        EditTextBoldCursor editTextBoldCursor2 = ProxySettingsActivity.this.inputFields[2];
                        int selectionStart = editTextBoldCursor2.getSelectionStart();
                        String string = editTextBoldCursor2.getText().toString();
                        StringBuilder sb = new StringBuilder(string.length());
                        int i5 = 0;
                        while (i5 < string.length()) {
                            int i6 = i5 + 1;
                            String strSubstring = string.substring(i5, i6);
                            if ("0123456789".contains(strSubstring)) {
                                sb.append(strSubstring);
                            }
                            i5 = i6;
                        }
                        ProxySettingsActivity.this.ignoreOnTextChange = true;
                        int iIntValue = Utilities.parseInt((CharSequence) sb.toString()).intValue();
                        if (iIntValue < 0 || iIntValue > 65535 || !string.equals(sb.toString())) {
                            if (iIntValue < 0) {
                                editTextBoldCursor2.setText(MVEL.VERSION_SUB);
                            } else if (iIntValue > 65535) {
                                editTextBoldCursor2.setText("65535");
                            } else {
                                editTextBoldCursor2.setText(sb.toString());
                            }
                        } else if (selectionStart >= 0) {
                            editTextBoldCursor2.setSelection(Math.min(selectionStart, editTextBoldCursor2.length()));
                        }
                        ProxySettingsActivity.this.ignoreOnTextChange = false;
                        ProxySettingsActivity.this.checkShareDone(true);
                    }
                });
            } else {
                EditTextBoldCursor[] editTextBoldCursorArr = this.inputFields;
                if (i3 == 4) {
                    editTextBoldCursorArr[i3].setInputType(129);
                    this.inputFields[i3].setTypeface(Typeface.DEFAULT);
                    this.inputFields[i3].setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    editTextBoldCursorArr[i3].setInputType(524289);
                }
            }
            this.inputFields[i3].setImeOptions(268435461);
            if (i3 == 0) {
                this.inputFields[i3].setHintText(LocaleController.getString(C2797R.string.ProxyRename));
                this.inputFields[i3].setText(ProxyController.getInstance().getName(this.currentProxyInfo));
            } else if (i3 == 1) {
                this.inputFields[i3].setHintText(LocaleController.getString(C2797R.string.UseProxyAddress));
                this.inputFields[i3].setText(this.currentProxyInfo.address);
            } else if (i3 == 2) {
                this.inputFields[i3].setHintText(LocaleController.getString(C2797R.string.UseProxyPort));
                this.inputFields[i3].setText(_UrlKt.FRAGMENT_ENCODE_SET + this.currentProxyInfo.port);
            } else if (i3 == 3) {
                this.inputFields[i3].setHintText(LocaleController.getString(C2797R.string.UseProxyUsername));
                this.inputFields[i3].setText(this.currentProxyInfo.username);
            } else if (i3 == 4) {
                this.inputFields[i3].setHintText(LocaleController.getString(C2797R.string.UseProxyPassword));
                this.inputFields[i3].setText(this.currentProxyInfo.password);
            } else if (i3 == 5) {
                this.inputFields[i3].setHintText(LocaleController.getString(C2797R.string.UseProxySecret));
                this.inputFields[i3].setText(this.currentProxyInfo.secret);
            }
            EditTextBoldCursor editTextBoldCursor2 = this.inputFields[i3];
            editTextBoldCursor2.setSelection(editTextBoldCursor2.length());
            this.inputFields[i3].setPadding(0, 0, 0, 0);
            frameLayout2.addView(this.inputFields[i3], LayoutHelper.createFrame(-1, -1.0f, 51, 17.0f, i3 == 0 ? 12.0f : 0.0f, 17.0f, 0.0f));
            this.inputFields[i3].setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.ProxySettingsActivity$$ExternalSyntheticLambda2
                @Override // android.widget.TextView.OnEditorActionListener
                public final boolean onEditorAction(TextView textView, int i5, KeyEvent keyEvent) {
                    return this.f$0.lambda$createView$1(textView, i5, keyEvent);
                }
            });
            i3++;
            i = 6;
            drawable = null;
        }
        updateActionBarTitle();
        for (int i5 = 0; i5 < 2; i5++) {
            this.bottomCells[i5] = new TextInfoPrivacyCell(context);
            this.bottomCells[i5].setBackground(Theme.getThemedDrawableByKey(context, C2797R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
            TextInfoPrivacyCell[] textInfoPrivacyCellArr = this.bottomCells;
            if (i5 == 0) {
                textInfoPrivacyCellArr[i5].setText(LocaleController.getString(C2797R.string.UseProxyInfo));
            } else {
                textInfoPrivacyCellArr[i5].setText(LocaleController.getString(C2797R.string.UseProxyTelegramInfo) + "\n\n" + LocaleController.getString(C2797R.string.UseProxyTelegramInfo2));
                this.bottomCells[i5].setVisibility(8);
            }
            this.linearLayout2.addView(this.bottomCells[i5], LayoutHelper.createLinear(-1, -2));
        }
        TextSettingsCell textSettingsCell = new TextSettingsCell(this.fragmentView.getContext());
        this.pasteCell = textSettingsCell;
        textSettingsCell.setBackground(Theme.getSelectorDrawable(true));
        this.pasteCell.setText(LocaleController.getString(C2797R.string.PasteFromClipboard), false);
        TextSettingsCell textSettingsCell2 = this.pasteCell;
        int i6 = Theme.key_windowBackgroundWhiteBlueText4;
        textSettingsCell2.setTextColor(Theme.getColor(i6));
        this.pasteCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.ProxySettingsActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$3(view);
            }
        });
        this.linearLayout2.addView(this.pasteCell, 0, LayoutHelper.createLinear(-1, -2));
        this.pasteCell.setVisibility(8);
        this.sectionCell[2] = new ShadowSectionCell(this.fragmentView.getContext());
        ShadowSectionCell shadowSectionCell = this.sectionCell[2];
        Context context2 = this.fragmentView.getContext();
        int i7 = C2797R.drawable.greydivider_bottom;
        int i8 = Theme.key_windowBackgroundGrayShadow;
        shadowSectionCell.setBackground(Theme.getThemedDrawableByKey(context2, i7, i8));
        this.linearLayout2.addView(this.sectionCell[2], 1, LayoutHelper.createLinear(-1, -2));
        this.sectionCell[2].setVisibility(8);
        TextSettingsCell textSettingsCell3 = new TextSettingsCell(context);
        this.shareCell = textSettingsCell3;
        textSettingsCell3.setBackgroundDrawable(Theme.getSelectorDrawable(true));
        this.shareCell.setText(LocaleController.getString(C2797R.string.ShareFile), false);
        this.shareCell.setTextColor(Theme.getColor(i6));
        this.linearLayout2.addView(this.shareCell, LayoutHelper.createLinear(-1, -2));
        this.shareCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.ProxySettingsActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$4(context, view);
            }
        });
        this.sectionCell[1] = new ShadowSectionCell(context);
        this.sectionCell[1].setBackgroundDrawable(Theme.getThemedDrawableByKey(context, C2797R.drawable.greydivider_bottom, i8));
        this.linearLayout2.addView(this.sectionCell[1], LayoutHelper.createLinear(-1, -2));
        this.clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        this.shareDoneEnabled = true;
        this.shareDoneProgress = 1.0f;
        checkShareDone(false);
        this.currentType = -1;
        setProxyType(!TextUtils.isEmpty(this.currentProxyInfo.secret) ? 1 : 0, false);
        this.pasteType = -1;
        this.pasteString = null;
        updatePasteCell();
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0(View view) {
        setProxyType(((Integer) view.getTag()).intValue(), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createView$1(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 5) {
            if (i != 6) {
                return false;
            }
            finishFragment();
            return true;
        }
        int iIntValue = ((Integer) textView.getTag()).intValue() + 1;
        EditTextBoldCursor[] editTextBoldCursorArr = this.inputFields;
        if (iIntValue < editTextBoldCursorArr.length) {
            editTextBoldCursorArr[iIntValue].requestFocus();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$3(View view) {
        if (this.pasteType == -1) {
            return;
        }
        int i = 1;
        while (true) {
            String[] strArr = this.pasteFields;
            if (i < strArr.length) {
                int i2 = this.pasteType;
                if ((i2 != 0 || i != 5) && (i2 != 1 || (i != 3 && i != 4))) {
                    String str = strArr[i];
                    EditTextBoldCursor[] editTextBoldCursorArr = this.inputFields;
                    if (str != null) {
                        try {
                            editTextBoldCursorArr[i].setText(URLDecoder.decode(str, "UTF-8"));
                        } catch (UnsupportedEncodingException unused) {
                            this.inputFields[i].setText(this.pasteFields[i]);
                        }
                    } else {
                        editTextBoldCursorArr[i].setText((CharSequence) null);
                    }
                }
                i++;
            } else {
                EditTextBoldCursor editTextBoldCursor = this.inputFields[1];
                editTextBoldCursor.setSelection(editTextBoldCursor.length());
                setProxyType(this.pasteType, true, new Runnable() { // from class: org.telegram.ui.ProxySettingsActivity$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$createView$2();
                    }
                });
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$2() {
        AndroidUtilities.hideKeyboard(this.inputFieldsContainer.findFocus());
        for (int i = 1; i < this.pasteFields.length; i++) {
            int i2 = this.pasteType;
            if ((i2 != 0 || i == 5) && (i2 != 1 || i == 3 || i == 4)) {
                this.inputFields[i].setText((CharSequence) null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$4(Context context, View view) {
        String strBuildShareLink = ProxyController.getInstance().buildShareLink(this.currentProxyInfo, this.inputFields[0].getText().toString().trim());
        if (TextUtils.isEmpty(strBuildShareLink)) {
            return;
        }
        QRCodeBottomSheet qRCodeBottomSheet = new QRCodeBottomSheet(context, LocaleController.getString(C2797R.string.ShareQrCode), strBuildShareLink, LocaleController.getString(C2797R.string.QRCodeLinkHelpProxy), true);
        qRCodeBottomSheet.setCenterImage(SvgHelper.getBitmap(AndroidUtilities.readRes(C2797R.raw.qr_dog), AndroidUtilities.m1036dp(60.0f), AndroidUtilities.m1036dp(60.0f), false));
        showDialog(qRCodeBottomSheet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePasteCell() {
        String string;
        ClipData primaryClip = this.clipboardManager.getPrimaryClip();
        String[] strArrSplit = null;
        if (primaryClip == null || primaryClip.getItemCount() <= 0) {
            string = null;
        } else {
            try {
                string = primaryClip.getItemAt(0).coerceToText(this.fragmentView.getContext()).toString();
            } catch (Exception unused) {
                string = null;
            }
        }
        if (TextUtils.equals(string, this.pasteString)) {
            return;
        }
        this.pasteType = -1;
        this.pasteString = string;
        this.pasteFields = new String[this.inputFields.length];
        if (string != null) {
            String[] strArr = {"t.me/socks?", "tg://socks?"};
            int i = 0;
            while (true) {
                if (i >= 2) {
                    break;
                }
                int iIndexOf = string.indexOf(strArr[i]);
                if (iIndexOf >= 0) {
                    this.pasteType = 0;
                    strArrSplit = string.substring(iIndexOf + strArr[i].length()).split("&");
                    break;
                }
                i++;
            }
            if (strArrSplit == null) {
                String[] strArr2 = {"t.me/proxy?", "tg://proxy?"};
                int i2 = 0;
                while (true) {
                    if (i2 >= 2) {
                        break;
                    }
                    int iIndexOf2 = string.indexOf(strArr2[i2]);
                    if (iIndexOf2 >= 0) {
                        this.pasteType = 1;
                        strArrSplit = string.substring(iIndexOf2 + strArr2[i2].length()).split("&");
                        break;
                    }
                    i2++;
                }
            }
            if (strArrSplit != null) {
                for (String str : strArrSplit) {
                    String[] strArrSplit2 = str.split("=");
                    if (strArrSplit2.length == 2) {
                        String lowerCase = strArrSplit2[0].toLowerCase();
                        lowerCase.getClass();
                        switch (lowerCase) {
                            case "secret":
                                if (this.pasteType == 1) {
                                    this.pasteFields[5] = strArrSplit2[1];
                                    break;
                                } else {
                                    break;
                                }
                                break;
                            case "server":
                                this.pasteFields[1] = strArrSplit2[1];
                                break;
                            case "pass":
                                if (this.pasteType == 0) {
                                    this.pasteFields[4] = strArrSplit2[1];
                                    break;
                                } else {
                                    break;
                                }
                                break;
                            case "port":
                                this.pasteFields[2] = strArrSplit2[1];
                                break;
                            case "user":
                                if (this.pasteType == 0) {
                                    this.pasteFields[3] = strArrSplit2[1];
                                    break;
                                } else {
                                    break;
                                }
                                break;
                        }
                    }
                }
            }
        }
        int i3 = this.pasteType;
        TextSettingsCell textSettingsCell = this.pasteCell;
        if (i3 != -1) {
            if (textSettingsCell.getVisibility() != 0) {
                this.pasteCell.setVisibility(0);
                this.sectionCell[2].setVisibility(0);
                return;
            }
            return;
        }
        if (textSettingsCell.getVisibility() != 8) {
            this.pasteCell.setVisibility(8);
            this.sectionCell[2].setVisibility(8);
        }
    }

    private void setShareDoneEnabled(boolean z, boolean z2) {
        if (this.shareDoneEnabled != z) {
            ValueAnimator valueAnimator = this.shareDoneAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            } else if (z2) {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.shareDoneAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.setDuration(200L);
                this.shareDoneAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.ProxySettingsActivity$$ExternalSyntheticLambda7
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        this.f$0.lambda$setShareDoneEnabled$5(valueAnimator2);
                    }
                });
            }
            if (z2) {
                float[] fArr = this.shareDoneProgressAnimValues;
                fArr[0] = this.shareDoneProgress;
                fArr[1] = z ? 1.0f : 0.0f;
                this.shareDoneAnimator.start();
            } else {
                this.shareDoneProgress = z ? 1.0f : 0.0f;
                this.shareCell.setTextColor(Theme.getColor(z ? Theme.key_windowBackgroundWhiteBlueText4 : Theme.key_windowBackgroundWhiteGrayText2));
                this.doneItem.setAlpha(z ? 1.0f : 0.5f);
            }
            this.shareCell.setEnabled(z);
            this.doneItem.setEnabled(z);
            this.shareDoneEnabled = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setShareDoneEnabled$5(ValueAnimator valueAnimator) {
        this.shareDoneProgress = AndroidUtilities.lerp(this.shareDoneProgressAnimValues, valueAnimator.getAnimatedFraction());
        this.shareCell.setTextColor(ColorUtils.blendARGB(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2), Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4), this.shareDoneProgress));
        this.doneItem.setAlpha((this.shareDoneProgress / 2.0f) + 0.5f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkShareDone(boolean z) {
        if (this.shareCell == null || this.doneItem == null) {
            return;
        }
        EditTextBoldCursor[] editTextBoldCursorArr = this.inputFields;
        EditTextBoldCursor editTextBoldCursor = editTextBoldCursorArr[1];
        if (editTextBoldCursor == null || editTextBoldCursorArr[2] == null) {
            return;
        }
        setShareDoneEnabled((editTextBoldCursor.length() == 0 || Utilities.parseInt((CharSequence) this.inputFields[2].getText().toString()).intValue() == 0) ? false : true, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateActionBarTitle() {
        String name;
        EditTextBoldCursor editTextBoldCursor;
        if (this.actionBar == null) {
            return;
        }
        EditTextBoldCursor[] editTextBoldCursorArr = this.inputFields;
        if (editTextBoldCursorArr != null && editTextBoldCursorArr.length > 0 && (editTextBoldCursor = editTextBoldCursorArr[0]) != null) {
            name = editTextBoldCursor.getText().toString().trim();
        } else {
            name = this.currentProxyInfo != null ? ProxyController.getInstance().getName(this.currentProxyInfo) : null;
        }
        ActionBar actionBar = this.actionBar;
        if (TextUtils.isEmpty(name)) {
            name = LocaleController.getString(C2797R.string.ProxyDetails);
        }
        actionBar.setTitle(name);
    }

    private void setProxyType(int i, boolean z) {
        setProxyType(i, z, null);
    }

    private void setProxyType(int i, boolean z, final Runnable runnable) {
        if (this.currentType != i) {
            this.currentType = i;
            TransitionManager.endTransitions(this.linearLayout2);
            if (z) {
                TransitionSet duration = new TransitionSet().addTransition(new Fade(2)).addTransition(new ChangeBounds()).addTransition(new Fade(1)).setInterpolator((TimeInterpolator) CubicBezierInterpolator.DEFAULT).setDuration(250L);
                if (runnable != null) {
                    duration.addListener(new Transition.TransitionListener() { // from class: org.telegram.ui.ProxySettingsActivity.5
                        @Override // android.transition.Transition.TransitionListener
                        public void onTransitionCancel(Transition transition) {
                        }

                        @Override // android.transition.Transition.TransitionListener
                        public void onTransitionPause(Transition transition) {
                        }

                        @Override // android.transition.Transition.TransitionListener
                        public void onTransitionResume(Transition transition) {
                        }

                        @Override // android.transition.Transition.TransitionListener
                        public void onTransitionStart(Transition transition) {
                        }

                        @Override // android.transition.Transition.TransitionListener
                        public void onTransitionEnd(Transition transition) {
                            runnable.run();
                        }
                    });
                }
                TransitionManager.beginDelayedTransition(this.linearLayout2, duration);
            }
            int i2 = this.currentType;
            if (i2 == 0) {
                this.bottomCells[0].setVisibility(0);
                this.bottomCells[1].setVisibility(8);
                ((View) this.inputFields[5].getParent()).setVisibility(8);
                ((View) this.inputFields[4].getParent()).setVisibility(0);
                ((View) this.inputFields[3].getParent()).setVisibility(0);
            } else if (i2 == 1) {
                this.bottomCells[0].setVisibility(8);
                this.bottomCells[1].setVisibility(0);
                ((View) this.inputFields[5].getParent()).setVisibility(0);
                ((View) this.inputFields[4].getParent()).setVisibility(8);
                ((View) this.inputFields[3].getParent()).setVisibility(8);
            }
            this.typeCell[0].setChecked(this.currentType == 0, z);
            this.typeCell[1].setChecked(this.currentType == 1, z);
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z && !z2 && this.addingNewProxy) {
            this.inputFields[0].requestFocus();
            AndroidUtilities.showKeyboard(this.inputFields[0]);
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ThemeDescription.ThemeDescriptionDelegate themeDescriptionDelegate = new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.ProxySettingsActivity$$ExternalSyntheticLambda5
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                this.f$0.lambda$getThemeDescriptions$6();
            }
        };
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        arrayList.add(new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray));
        ActionBar actionBar = this.actionBar;
        int i = ThemeDescription.FLAG_BACKGROUND;
        int i2 = Theme.key_actionBarDefault;
        arrayList.add(new ThemeDescription(actionBar, i, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.scrollView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCH, null, null, null, null, Theme.key_actionBarDefaultSearch));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCHPLACEHOLDER, null, null, null, null, Theme.key_actionBarDefaultSearchPlaceholder));
        LinearLayout linearLayout = this.inputFieldsContainer;
        int i3 = ThemeDescription.FLAG_BACKGROUND;
        int i4 = Theme.key_windowBackgroundWhite;
        arrayList.add(new ThemeDescription(linearLayout, i3, null, null, null, null, i4));
        arrayList.add(new ThemeDescription(this.linearLayout2, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));
        arrayList.add(new ThemeDescription(this.shareCell, ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, i4));
        TextSettingsCell textSettingsCell = this.shareCell;
        int i5 = ThemeDescription.FLAG_SELECTORWHITE;
        int i6 = Theme.key_listSelector;
        arrayList.add(new ThemeDescription(textSettingsCell, i5, null, null, null, null, i6));
        int i7 = Theme.key_windowBackgroundWhiteBlueText4;
        arrayList.add(new ThemeDescription((View) null, 0, (Class[]) null, (String[]) null, (Paint[]) null, (Drawable[]) null, themeDescriptionDelegate, i7));
        arrayList.add(new ThemeDescription((View) null, 0, (Class[]) null, (String[]) null, (Paint[]) null, (Drawable[]) null, themeDescriptionDelegate, Theme.key_windowBackgroundWhiteGrayText2));
        arrayList.add(new ThemeDescription(this.pasteCell, ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, i4));
        arrayList.add(new ThemeDescription(this.pasteCell, ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, i6));
        arrayList.add(new ThemeDescription(this.pasteCell, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i7));
        for (int i8 = 0; i8 < this.typeCell.length; i8++) {
            arrayList.add(new ThemeDescription(this.typeCell[i8], ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_windowBackgroundWhite));
            arrayList.add(new ThemeDescription(this.typeCell[i8], ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_listSelector));
            arrayList.add(new ThemeDescription(this.typeCell[i8], 0, new Class[]{RadioCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlackText));
            arrayList.add(new ThemeDescription(this.typeCell[i8], ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioCell.class}, new String[]{"radioButton"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_radioBackground));
            arrayList.add(new ThemeDescription(this.typeCell[i8], ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioCell.class}, new String[]{"radioButton"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_radioBackgroundChecked));
        }
        if (this.inputFields != null) {
            for (int i9 = 0; i9 < this.inputFields.length; i9++) {
                EditTextBoldCursor editTextBoldCursor = this.inputFields[i9];
                int i10 = ThemeDescription.FLAG_TEXTCOLOR;
                int i11 = Theme.key_windowBackgroundWhiteBlackText;
                arrayList.add(new ThemeDescription(editTextBoldCursor, i10, null, null, null, null, i11));
                arrayList.add(new ThemeDescription(this.inputFields[i9], ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText));
                arrayList.add(new ThemeDescription(this.inputFields[i9], ThemeDescription.FLAG_HINTTEXTCOLOR | ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader));
                arrayList.add(new ThemeDescription(this.inputFields[i9], ThemeDescription.FLAG_CURSORCOLOR, null, null, null, null, i11));
                arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_windowBackgroundWhiteInputField));
                arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_windowBackgroundWhiteInputFieldActivated));
                arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_text_RedRegular));
            }
        } else {
            arrayList.add(new ThemeDescription(null, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
            arrayList.add(new ThemeDescription(null, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText));
        }
        arrayList.add(new ThemeDescription(this.headerCell, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.headerCell, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlueHeader));
        int i12 = 0;
        while (true) {
            ShadowSectionCell[] shadowSectionCellArr = this.sectionCell;
            if (i12 >= shadowSectionCellArr.length) {
                break;
            }
            if (shadowSectionCellArr[i12] != null) {
                arrayList.add(new ThemeDescription(this.sectionCell[i12], ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow));
            }
            i12++;
        }
        for (int i13 = 0; i13 < this.bottomCells.length; i13++) {
            arrayList.add(new ThemeDescription(this.bottomCells[i13], ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow));
            arrayList.add(new ThemeDescription(this.bottomCells[i13], 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText4));
            arrayList.add(new ThemeDescription(this.bottomCells[i13], ThemeDescription.FLAG_LINKCOLOR, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteLinkText));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getThemeDescriptions$6() {
        ValueAnimator valueAnimator;
        if (this.shareCell != null && ((valueAnimator = this.shareDoneAnimator) == null || !valueAnimator.isRunning())) {
            this.shareCell.setTextColor(Theme.getColor(this.shareDoneEnabled ? Theme.key_windowBackgroundWhiteBlueText4 : Theme.key_windowBackgroundWhiteGrayText2));
        }
        if (this.inputFields == null) {
            return;
        }
        int i = 0;
        while (true) {
            EditTextBoldCursor[] editTextBoldCursorArr = this.inputFields;
            if (i >= editTextBoldCursorArr.length) {
                return;
            }
            editTextBoldCursorArr[i].setLineColors(Theme.getColor(Theme.key_windowBackgroundWhiteInputField), Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated), Theme.getColor(Theme.key_text_RedRegular));
            i++;
        }
    }
}
