package org.telegram.p026ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.exteragram.messenger.utils.system.VibratorUtils;
import java.util.ArrayList;
import okhttp3.internal.url._UrlKt;
import org.mvel2.MVEL;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BillingController;
import org.telegram.messenger.C2702R;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.browser.Browser;
import org.telegram.p026ui.ActionBar.ActionBar;
import org.telegram.p026ui.ActionBar.AdjustPanLayoutHelper;
import org.telegram.p026ui.ActionBar.AlertDialog;
import org.telegram.p026ui.ActionBar.BaseFragment;
import org.telegram.p026ui.ActionBar.Theme;
import org.telegram.p026ui.ActionBar.ThemeDescription;
import org.telegram.p026ui.Cells.EditTextCell;
import org.telegram.p026ui.Cells.HeaderCell;
import org.telegram.p026ui.Cells.TextCheckCell;
import org.telegram.p026ui.Cells.TextInfoPrivacyCell;
import org.telegram.p026ui.Cells.TextSettingsCell;
import org.telegram.p026ui.Components.AlertsCreator;
import org.telegram.p026ui.Components.SectionsScrollView;
import org.telegram.p026ui.Components.SizeNotifierFrameLayout;
import org.telegram.p026ui.Components.SlideChooseView;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p025tl.TL_stars;

/* JADX INFO: loaded from: classes6.dex */
public class LinkEditActivity extends BaseFragment {
    private TextCheckCell approveCell;
    private TextInfoPrivacyCell approveHintCell;
    private FrameLayout buttonLayout;
    private TextView buttonTextView;
    private Callback callback;
    private final long chatId;
    private TextView createTextView;
    int currentInviteDate;
    private TextInfoPrivacyCell divider;
    private TextInfoPrivacyCell dividerName;
    private TextInfoPrivacyCell dividerUses;
    private boolean finished;
    private boolean ignoreSet;
    TLRPC.TL_chatInviteExported inviteToEdit;
    boolean loading;
    private EditText nameEditText;
    AlertDialog progressDialog;
    private TextSettingsCell revokeLink;
    boolean scrollToEnd;
    boolean scrollToStart;
    private SectionsScrollView scrollView;
    private TextCheckCell subCell;
    private EditTextCell subEditPriceCell;
    private TextInfoPrivacyCell subInfoCell;
    private TextView subPriceView;
    private SlideChooseView timeChooseView;
    private TextView timeEditText;
    private HeaderCell timeHeaderCell;
    private int type;
    private SlideChooseView usesChooseView;
    private EditText usesEditText;
    private HeaderCell usesHeaderCell;
    private int shakeDp = -3;
    private boolean firstLayout = true;
    private ArrayList dispalyedDates = new ArrayList();
    private final int[] defaultDates = {3600, 86400, 604800};
    private ArrayList dispalyedUses = new ArrayList();
    private final int[] defaultUses = {1, 10, 100};

    public interface Callback {
        void onLinkCreated(TLObject tLObject);

        void onLinkEdited(TLRPC.TL_chatInviteExported tL_chatInviteExported, TLObject tLObject);

        void onLinkRemoved(TLRPC.TL_chatInviteExported tL_chatInviteExported);

        void revokeLink(TLRPC.TL_chatInviteExported tL_chatInviteExported);
    }

    public static /* synthetic */ void $r8$lambda$PtMwoxo3HJKRJNJVDYxszG_lC40(Integer num) {
    }

    public LinkEditActivity(int i, long j) {
        this.type = i;
        this.chatId = j;
    }

    /* JADX WARN: Removed duplicated region for block: B:98:0x024f A[PHI: r17
  0x024f: PHI (r17v4 float) = (r17v3 float), (r17v5 float) binds: [B:97:0x024d, B:91:0x0242] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View createView(final android.content.Context r31) {
        /*
            Method dump skipped, instruction units count: 1625
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.LinkEditActivity.createView(android.content.Context):android.view.View");
    }

    /* JADX INFO: renamed from: org.telegram.ui.LinkEditActivity$1 */
    class C57161 extends ActionBar.ActionBarMenuOnItemClick {
        C57161() {
        }

        @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
        public void onItemClick(int i) {
            if (i == -1) {
                LinkEditActivity.this.finishFragment();
                AndroidUtilities.hideKeyboard(LinkEditActivity.this.usesEditText);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LinkEditActivity$2 */
    class C57172 extends SectionsScrollView.SectionsLinearLayout {
        C57172(Context context) {
            super(context);
        }

        @Override // android.widget.LinearLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            LinkEditActivity.this.firstLayout = false;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LinkEditActivity$3 */
    class C57183 extends SizeNotifierFrameLayout {
        C57183(Context context) {
            super(context);
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            measureKeyboardHeight();
            int i3 = this.keyboardHeight;
            if (i3 != 0 && i3 < AndroidUtilities.m1081dp(20.0f)) {
                LinkEditActivity.this.usesEditText.clearFocus();
                LinkEditActivity.this.nameEditText.clearFocus();
            }
            LinkEditActivity.this.buttonLayout.setVisibility(this.keyboardHeight > AndroidUtilities.m1081dp(20.0f) ? 8 : 0);
        }

        @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int scrollY = LinkEditActivity.this.scrollView.getScrollY();
            super.onLayout(z, i, i2, i3, i4);
            if (scrollY != LinkEditActivity.this.scrollView.getScrollY()) {
                LinkEditActivity linkEditActivity = LinkEditActivity.this;
                if (linkEditActivity.scrollToEnd) {
                    return;
                }
                linkEditActivity.scrollView.setTranslationY(LinkEditActivity.this.scrollView.getScrollY() - scrollY);
                LinkEditActivity.this.scrollView.animate().cancel();
                LinkEditActivity.this.scrollView.animate().translationY(0.0f).setDuration(250L).setInterpolator(AdjustPanLayoutHelper.keyboardInterpolator).start();
            }
        }

        @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            LinkEditActivity linkEditActivity = LinkEditActivity.this;
            if (linkEditActivity.scrollToEnd) {
                linkEditActivity.scrollToEnd = false;
                linkEditActivity.scrollView.smoothScrollTo(0, Math.max(0, LinkEditActivity.this.scrollView.getChildAt(0).getMeasuredHeight() - LinkEditActivity.this.scrollView.getMeasuredHeight()));
            } else if (linkEditActivity.scrollToStart) {
                linkEditActivity.scrollToStart = false;
                linkEditActivity.scrollView.smoothScrollTo(0, 0);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LinkEditActivity$4 */
    class C57194 extends TextCheckCell {
        C57194(Context context) {
            super(context);
        }

        @Override // org.telegram.p026ui.Cells.TextCheckCell, android.view.View
        protected void onDraw(Canvas canvas) {
            canvas.save();
            canvas.clipRect(0, 0, getWidth(), getHeight());
            super.onDraw(canvas);
            canvas.restore();
        }
    }

    public /* synthetic */ void lambda$createView$0(View view) {
        TextCheckCell textCheckCell = this.subCell;
        if (textCheckCell != null && textCheckCell.isChecked()) {
            TextCheckCell textCheckCell2 = this.subCell;
            int i = -this.shakeDp;
            this.shakeDp = i;
            AndroidUtilities.shakeViewSpring(textCheckCell2, i);
            return;
        }
        TextCheckCell textCheckCell3 = (TextCheckCell) view;
        boolean zIsChecked = textCheckCell3.isChecked();
        boolean z = !zIsChecked;
        textCheckCell3.setBackgroundColorAnimated(z, Theme.getColor(!zIsChecked ? Theme.key_windowBackgroundChecked : Theme.key_windowBackgroundUnchecked));
        textCheckCell3.setChecked(z);
        setUsesVisible(zIsChecked);
        this.firstLayout = true;
        if (this.subCell != null) {
            if (textCheckCell3.isChecked()) {
                this.subCell.setChecked(false);
                this.subCell.setCheckBoxIcon(C2702R.drawable.permission_locked);
                this.subEditPriceCell.setVisibility(8);
            } else if (this.inviteToEdit == null) {
                this.subCell.setCheckBoxIcon(0);
            }
        }
    }

    public /* synthetic */ void lambda$createView$3(Runnable[] runnableArr, View view) {
        if (this.inviteToEdit != null) {
            return;
        }
        if (this.approveCell.isChecked()) {
            TextCheckCell textCheckCell = this.approveCell;
            int i = -this.shakeDp;
            this.shakeDp = i;
            AndroidUtilities.shakeViewSpring(textCheckCell, i);
            return;
        }
        TextCheckCell textCheckCell2 = (TextCheckCell) view;
        textCheckCell2.setChecked(!textCheckCell2.isChecked());
        this.subEditPriceCell.setVisibility(textCheckCell2.isChecked() ? 0 : 8);
        AndroidUtilities.cancelRunOnUIThread(runnableArr[0]);
        if (textCheckCell2.isChecked()) {
            this.approveCell.setChecked(false);
            this.approveCell.setCheckBoxIcon(C2702R.drawable.permission_locked);
            this.approveHintCell.setText(LocaleController.getString(C2702R.string.ApproveNewMembersDescriptionFrozen));
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.LinkEditActivity$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createView$1();
                }
            };
            runnableArr[0] = runnable;
            AndroidUtilities.runOnUIThread(runnable, 60L);
            return;
        }
        this.approveCell.setCheckBoxIcon(0);
        this.approveHintCell.setText(LocaleController.getString(C2702R.string.ApproveNewMembersDescription));
        Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.LinkEditActivity$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$2();
            }
        };
        runnableArr[0] = runnable2;
        AndroidUtilities.runOnUIThread(runnable2);
    }

    public /* synthetic */ void lambda$createView$1() {
        this.subEditPriceCell.editText.requestFocus();
        AndroidUtilities.showKeyboard(this.subEditPriceCell.editText);
    }

    public /* synthetic */ void lambda$createView$2() {
        this.subEditPriceCell.editText.clearFocus();
        AndroidUtilities.hideKeyboard(this.subEditPriceCell.editText);
    }

    /* JADX INFO: renamed from: org.telegram.ui.LinkEditActivity$5 */
    class C57205 extends EditTextCell {
        private boolean ignoreTextChanged;

        C57205(Context context, String str, boolean z, boolean z2, int i, Theme.ResourcesProvider resourcesProvider) {
            super(context, str, z, z2, i, resourcesProvider);
        }

        @Override // org.telegram.p026ui.Cells.EditTextCell
        protected void onTextChanged(CharSequence charSequence) {
            super.onTextChanged(charSequence);
            if (this.ignoreTextChanged) {
                return;
            }
            if (TextUtils.isEmpty(charSequence)) {
                LinkEditActivity.this.subPriceView.setText(_UrlKt.FRAGMENT_ENCODE_SET);
                return;
            }
            try {
                long j = Long.parseLong(charSequence.toString());
                if (j > LinkEditActivity.this.getMessagesController().starsSubscriptionAmountMax) {
                    this.ignoreTextChanged = true;
                    j = LinkEditActivity.this.getMessagesController().starsSubscriptionAmountMax;
                    setText(Long.toString(j));
                    this.ignoreTextChanged = false;
                }
                LinkEditActivity.this.subPriceView.setText(LocaleController.formatString(LinkEditActivity.this.getConnectionsManager().isTestBackend() ? C2702R.string.RequireMonthlyFeePriceTest5Minutes : C2702R.string.RequireMonthlyFeePrice, BillingController.getInstance().formatCurrency((long) ((j / 1000.0d) * ((double) MessagesController.getInstance(((BaseFragment) LinkEditActivity.this).currentAccount).starsUsdWithdrawRate1000)), "USD")));
            } catch (Exception e) {
                FileLog.m1093e(e);
            }
        }
    }

    public /* synthetic */ void lambda$createView$4() {
        Browser.openUrl(getContext(), LocaleController.getString(C2702R.string.RequireMonthlyFeeInfoLink));
    }

    public /* synthetic */ void lambda$createView$5(boolean z, int i, int i2) {
        chooseDate(i);
    }

    public /* synthetic */ void lambda$createView$6(Context context, View view) {
        AlertsCreator.createDatePickerDialog(context, LocaleController.getString(C2702R.string.ExpireAfter), LocaleController.getString(C2702R.string.SetTimeLimit), -1L, new AlertsCreator.ScheduleDatePickerDelegate() { // from class: org.telegram.ui.LinkEditActivity$$ExternalSyntheticLambda12
            @Override // org.telegram.ui.Components.AlertsCreator.ScheduleDatePickerDelegate
            public final void didSelectDate(boolean z, int i, int i2) {
                this.f$0.lambda$createView$5(z, i, i2);
            }
        });
    }

    public /* synthetic */ void lambda$createView$7(int i) {
        if (i < this.dispalyedDates.size()) {
            this.timeEditText.setText(LocaleController.formatDateAudio(((Integer) this.dispalyedDates.get(i)).intValue() + getConnectionsManager().getCurrentTime(), false));
        } else {
            this.timeEditText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
        }
    }

    public /* synthetic */ void lambda$createView$8(int i) {
        this.usesEditText.clearFocus();
        this.ignoreSet = true;
        if (i < this.dispalyedUses.size()) {
            this.usesEditText.setText(((Integer) this.dispalyedUses.get(i)).toString());
        } else {
            this.usesEditText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
        }
        this.ignoreSet = false;
    }

    /* JADX INFO: renamed from: org.telegram.ui.LinkEditActivity$6 */
    class C57216 extends EditText {
        C57216(Context context) {
            super(context);
        }

        @Override // android.widget.TextView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 1) {
                setCursorVisible(true);
            }
            return super.onTouchEvent(motionEvent);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LinkEditActivity$7 */
    class C57227 implements TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        C57227() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            if (LinkEditActivity.this.ignoreSet) {
                return;
            }
            if (editable.toString().equals(MVEL.VERSION_SUB)) {
                LinkEditActivity.this.usesEditText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
                return;
            }
            try {
                int i = Integer.parseInt(editable.toString());
                if (i > 100000) {
                    LinkEditActivity.this.resetUses();
                } else {
                    LinkEditActivity.this.chooseUses(i);
                }
            } catch (NumberFormatException unused) {
                LinkEditActivity.this.resetUses();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LinkEditActivity$8 */
    class C57238 extends EditText {
        C57238(Context context) {
            super(context);
        }

        @Override // android.widget.TextView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 1) {
                setCursorVisible(true);
            }
            return super.onTouchEvent(motionEvent);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.LinkEditActivity$9 */
    class C57249 implements TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        C57249() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            Emoji.replaceEmoji(editable, LinkEditActivity.this.nameEditText.getPaint().getFontMetricsInt(), false);
        }
    }

    public /* synthetic */ void lambda$createView$10(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        builder.setMessage(LocaleController.getString(C2702R.string.RevokeAlert));
        builder.setTitle(LocaleController.getString(C2702R.string.RevokeLink));
        builder.setPositiveButton(LocaleController.getString(C2702R.string.RevokeButton), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.LinkEditActivity$$ExternalSyntheticLambda13
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$createView$9(alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2702R.string.Cancel), null);
        showDialog(builder.create());
    }

    public /* synthetic */ void lambda$createView$9(AlertDialog alertDialog, int i) {
        this.callback.revokeLink(this.inviteToEdit);
        finishFragment();
    }

    public void onCreateClicked(View view) {
        long j;
        boolean z;
        if (this.loading) {
            return;
        }
        int selectedIndex = this.timeChooseView.getSelectedIndex();
        if (selectedIndex < this.dispalyedDates.size() && ((Integer) this.dispalyedDates.get(selectedIndex)).intValue() < 0) {
            AndroidUtilities.shakeView(this.timeEditText);
            VibratorUtils.vibrate();
            return;
        }
        TextCheckCell textCheckCell = this.subCell;
        if (textCheckCell == null || !textCheckCell.isChecked()) {
            j = 0;
        } else {
            try {
                j = Long.parseLong(this.subEditPriceCell.editText.getText().toString());
            } catch (Exception e) {
                FileLog.m1093e(e);
                j = 0;
            }
        }
        int i = this.type;
        if (i == 0) {
            AlertDialog alertDialog = this.progressDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            this.loading = true;
            AlertDialog alertDialog2 = new AlertDialog(getParentActivity(), 3);
            this.progressDialog = alertDialog2;
            alertDialog2.showDelayed(500L);
            TLRPC.TL_messages_exportChatInvite tL_messages_exportChatInvite = new TLRPC.TL_messages_exportChatInvite();
            tL_messages_exportChatInvite.peer = getMessagesController().getInputPeer(-this.chatId);
            tL_messages_exportChatInvite.legacy_revoke_permanent = false;
            int selectedIndex2 = this.timeChooseView.getSelectedIndex();
            tL_messages_exportChatInvite.flags |= 1;
            if (selectedIndex2 < this.dispalyedDates.size()) {
                tL_messages_exportChatInvite.expire_date = ((Integer) this.dispalyedDates.get(selectedIndex2)).intValue() + getConnectionsManager().getCurrentTime();
            } else {
                tL_messages_exportChatInvite.expire_date = 0;
            }
            int selectedIndex3 = this.usesChooseView.getSelectedIndex();
            tL_messages_exportChatInvite.flags |= 2;
            if (selectedIndex3 < this.dispalyedUses.size()) {
                tL_messages_exportChatInvite.usage_limit = ((Integer) this.dispalyedUses.get(selectedIndex3)).intValue();
            } else {
                tL_messages_exportChatInvite.usage_limit = 0;
            }
            TextCheckCell textCheckCell2 = this.approveCell;
            boolean z2 = textCheckCell2 != null && textCheckCell2.isChecked();
            tL_messages_exportChatInvite.request_needed = z2;
            if (z2) {
                tL_messages_exportChatInvite.usage_limit = 0;
            }
            String string = this.nameEditText.getText().toString();
            tL_messages_exportChatInvite.title = string;
            if (!TextUtils.isEmpty(string)) {
                tL_messages_exportChatInvite.flags |= 16;
            }
            if (j > 0) {
                tL_messages_exportChatInvite.flags |= 32;
                TL_stars.TL_starsSubscriptionPricing tL_starsSubscriptionPricing = new TL_stars.TL_starsSubscriptionPricing();
                tL_messages_exportChatInvite.subscription_pricing = tL_starsSubscriptionPricing;
                tL_starsSubscriptionPricing.period = getConnectionsManager().isTestBackend() ? 300 : 2592000;
                tL_messages_exportChatInvite.subscription_pricing.amount = j;
            }
            getConnectionsManager().sendRequest(tL_messages_exportChatInvite, new RequestDelegate() { // from class: org.telegram.ui.LinkEditActivity$$ExternalSyntheticLambda14
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$onCreateClicked$13(tLObject, tL_error);
                }
            });
            return;
        }
        if (i == 1) {
            AlertDialog alertDialog3 = this.progressDialog;
            if (alertDialog3 != null) {
                alertDialog3.dismiss();
            }
            TLRPC.TL_messages_editExportedChatInvite tL_messages_editExportedChatInvite = new TLRPC.TL_messages_editExportedChatInvite();
            tL_messages_editExportedChatInvite.link = this.inviteToEdit.link;
            tL_messages_editExportedChatInvite.revoked = false;
            tL_messages_editExportedChatInvite.peer = getMessagesController().getInputPeer(-this.chatId);
            int selectedIndex4 = this.timeChooseView.getSelectedIndex();
            if (selectedIndex4 < this.dispalyedDates.size()) {
                if (this.currentInviteDate != ((Integer) this.dispalyedDates.get(selectedIndex4)).intValue()) {
                    tL_messages_editExportedChatInvite.flags |= 1;
                    tL_messages_editExportedChatInvite.expire_date = ((Integer) this.dispalyedDates.get(selectedIndex4)).intValue() + getConnectionsManager().getCurrentTime();
                    z = true;
                }
                z = false;
            } else {
                if (this.currentInviteDate != 0) {
                    tL_messages_editExportedChatInvite.flags |= 1;
                    tL_messages_editExportedChatInvite.expire_date = 0;
                    z = true;
                }
                z = false;
            }
            int selectedIndex5 = this.usesChooseView.getSelectedIndex();
            if (selectedIndex5 < this.dispalyedUses.size()) {
                int iIntValue = ((Integer) this.dispalyedUses.get(selectedIndex5)).intValue();
                if (this.inviteToEdit.usage_limit != iIntValue) {
                    tL_messages_editExportedChatInvite.flags |= 2;
                    tL_messages_editExportedChatInvite.usage_limit = iIntValue;
                    z = true;
                }
            } else if (this.inviteToEdit.usage_limit != 0) {
                tL_messages_editExportedChatInvite.flags |= 2;
                tL_messages_editExportedChatInvite.usage_limit = 0;
                z = true;
            }
            boolean z3 = this.inviteToEdit.request_needed;
            TextCheckCell textCheckCell3 = this.approveCell;
            if (z3 != (textCheckCell3 != null && textCheckCell3.isChecked())) {
                tL_messages_editExportedChatInvite.flags |= 8;
                TextCheckCell textCheckCell4 = this.approveCell;
                boolean z4 = textCheckCell4 != null && textCheckCell4.isChecked();
                tL_messages_editExportedChatInvite.request_needed = z4;
                if (z4) {
                    tL_messages_editExportedChatInvite.flags |= 2;
                    tL_messages_editExportedChatInvite.usage_limit = 0;
                }
                z = true;
            }
            String string2 = this.nameEditText.getText().toString();
            if (!TextUtils.equals(this.inviteToEdit.title, string2)) {
                tL_messages_editExportedChatInvite.title = string2;
                tL_messages_editExportedChatInvite.flags |= 16;
                z = true;
            }
            if (z) {
                this.loading = true;
                AlertDialog alertDialog4 = new AlertDialog(getParentActivity(), 3);
                this.progressDialog = alertDialog4;
                alertDialog4.showDelayed(500L);
                getConnectionsManager().sendRequest(tL_messages_editExportedChatInvite, new RequestDelegate() { // from class: org.telegram.ui.LinkEditActivity$$ExternalSyntheticLambda15
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$onCreateClicked$15(tLObject, tL_error);
                    }
                });
                return;
            }
            finishFragment();
        }
    }

    public /* synthetic */ void lambda$onCreateClicked$13(final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LinkEditActivity$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onCreateClicked$12(tL_error, tLObject);
            }
        });
    }

    public /* synthetic */ void lambda$onCreateClicked$12(TLRPC.TL_error tL_error, TLObject tLObject) {
        this.loading = false;
        AlertDialog alertDialog = this.progressDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        if (tL_error == null) {
            Callback callback = this.callback;
            if (callback != null) {
                callback.onLinkCreated(tLObject);
            }
            finishFragment();
            return;
        }
        AlertsCreator.showSimpleAlert(this, tL_error.text);
    }

    public /* synthetic */ void lambda$onCreateClicked$15(final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LinkEditActivity$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onCreateClicked$14(tL_error, tLObject);
            }
        });
    }

    public /* synthetic */ void lambda$onCreateClicked$14(TLRPC.TL_error tL_error, TLObject tLObject) {
        this.loading = false;
        AlertDialog alertDialog = this.progressDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        if (tL_error == null) {
            if (tLObject instanceof TLRPC.TL_messages_exportedChatInvite) {
                this.inviteToEdit = (TLRPC.TL_chatInviteExported) ((TLRPC.TL_messages_exportedChatInvite) tLObject).invite;
            }
            Callback callback = this.callback;
            if (callback != null) {
                callback.onLinkEdited(this.inviteToEdit, tLObject);
            }
            finishFragment();
            return;
        }
        AlertsCreator.showSimpleAlert(this, tL_error.text);
    }

    public void chooseUses(int i) {
        int i2;
        this.dispalyedUses.clear();
        int i3 = 0;
        boolean z = false;
        int length = 0;
        while (true) {
            int[] iArr = this.defaultUses;
            if (i3 >= iArr.length) {
                break;
            }
            if (!z && i <= (i2 = iArr[i3])) {
                if (i != i2) {
                    this.dispalyedUses.add(Integer.valueOf(i));
                }
                z = true;
                length = i3;
            }
            this.dispalyedUses.add(Integer.valueOf(this.defaultUses[i3]));
            i3++;
        }
        if (!z) {
            this.dispalyedUses.add(Integer.valueOf(i));
            length = this.defaultUses.length;
        }
        int size = this.dispalyedUses.size();
        int i4 = size + 1;
        String[] strArr = new String[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            if (i5 == size) {
                strArr[i5] = LocaleController.getString(C2702R.string.NoLimit);
            } else {
                strArr[i5] = ((Integer) this.dispalyedUses.get(i5)).toString();
            }
        }
        this.usesChooseView.setOptions(length, strArr);
    }

    private void chooseDate(int i) {
        long j = i;
        this.timeEditText.setText(LocaleController.formatDateAudio(j, false));
        int currentTime = i - getConnectionsManager().getCurrentTime();
        this.dispalyedDates.clear();
        int i2 = 0;
        boolean z = false;
        int length = 0;
        while (true) {
            int[] iArr = this.defaultDates;
            if (i2 >= iArr.length) {
                break;
            }
            if (!z && currentTime < iArr[i2]) {
                this.dispalyedDates.add(Integer.valueOf(currentTime));
                length = i2;
                z = true;
            }
            this.dispalyedDates.add(Integer.valueOf(this.defaultDates[i2]));
            i2++;
        }
        if (!z) {
            this.dispalyedDates.add(Integer.valueOf(currentTime));
            length = this.defaultDates.length;
        }
        int size = this.dispalyedDates.size();
        int i3 = size + 1;
        String[] strArr = new String[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            if (i4 == size) {
                strArr[i4] = LocaleController.getString(C2702R.string.NoLimit);
            } else if (((Integer) this.dispalyedDates.get(i4)).intValue() == this.defaultDates[0]) {
                strArr[i4] = LocaleController.formatPluralString("Hours", 1, new Object[0]);
            } else if (((Integer) this.dispalyedDates.get(i4)).intValue() == this.defaultDates[1]) {
                strArr[i4] = LocaleController.formatPluralString("Days", 1, new Object[0]);
            } else if (((Integer) this.dispalyedDates.get(i4)).intValue() == this.defaultDates[2]) {
                strArr[i4] = LocaleController.formatPluralString("Weeks", 1, new Object[0]);
            } else {
                long j2 = currentTime;
                if (j2 < 86400) {
                    strArr[i4] = LocaleController.getString(C2702R.string.MessageScheduleToday);
                } else if (j2 < 31449600) {
                    strArr[i4] = LocaleController.getInstance().getFormatterScheduleDay().format(1000 * j);
                } else {
                    strArr[i4] = LocaleController.getInstance().getFormatterYear().format(1000 * j);
                }
            }
        }
        this.timeChooseView.setOptions(length, strArr);
    }

    private void resetDates() {
        this.dispalyedDates.clear();
        int i = 0;
        while (true) {
            int[] iArr = this.defaultDates;
            if (i < iArr.length) {
                this.dispalyedDates.add(Integer.valueOf(iArr[i]));
                i++;
            } else {
                this.timeChooseView.setOptions(3, LocaleController.formatPluralString("Hours", 1, new Object[0]), LocaleController.formatPluralString("Days", 1, new Object[0]), LocaleController.formatPluralString("Weeks", 1, new Object[0]), LocaleController.getString(C2702R.string.NoLimit));
                return;
            }
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void resetUses() {
        this.dispalyedUses.clear();
        int i = 0;
        while (true) {
            int[] iArr = this.defaultUses;
            if (i < iArr.length) {
                this.dispalyedUses.add(Integer.valueOf(iArr[i]));
                i++;
            } else {
                this.usesChooseView.setOptions(3, "1", "10", "100", LocaleController.getString(C2702R.string.NoLimit));
                return;
            }
        }
    }

    public void setInviteToEdit(TLRPC.TL_chatInviteExported tL_chatInviteExported) {
        this.inviteToEdit = tL_chatInviteExported;
        if (this.fragmentView == null || tL_chatInviteExported == null) {
            return;
        }
        int i = tL_chatInviteExported.expire_date;
        if (i > 0) {
            chooseDate(i);
            this.currentInviteDate = ((Integer) this.dispalyedDates.get(this.timeChooseView.getSelectedIndex())).intValue();
        } else {
            this.currentInviteDate = 0;
        }
        int i2 = tL_chatInviteExported.usage_limit;
        if (i2 > 0) {
            chooseUses(i2);
            this.usesEditText.setText(Integer.toString(tL_chatInviteExported.usage_limit));
        }
        TextCheckCell textCheckCell = this.approveCell;
        if (textCheckCell != null) {
            textCheckCell.setBackgroundColor(Theme.getColor(tL_chatInviteExported.request_needed ? Theme.key_windowBackgroundChecked : Theme.key_windowBackgroundUnchecked));
            this.approveCell.setChecked(tL_chatInviteExported.request_needed);
        }
        setUsesVisible(!tL_chatInviteExported.request_needed);
        if (!TextUtils.isEmpty(tL_chatInviteExported.title)) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(tL_chatInviteExported.title);
            Emoji.replaceEmoji(spannableStringBuilder, this.nameEditText.getPaint().getFontMetricsInt(), false);
            this.nameEditText.setText(spannableStringBuilder);
        }
        TextCheckCell textCheckCell2 = this.subCell;
        if (textCheckCell2 != null) {
            textCheckCell2.setChecked(tL_chatInviteExported.subscription_pricing != null);
        }
        if (tL_chatInviteExported.subscription_pricing != null) {
            TextCheckCell textCheckCell3 = this.approveCell;
            if (textCheckCell3 != null) {
                textCheckCell3.setChecked(false);
                this.approveCell.setCheckBoxIcon(C2702R.drawable.permission_locked);
            }
            TextInfoPrivacyCell textInfoPrivacyCell = this.approveHintCell;
            if (textInfoPrivacyCell != null) {
                textInfoPrivacyCell.setText(LocaleController.getString(C2702R.string.ApproveNewMembersDescriptionFrozen));
            }
        }
        EditTextCell editTextCell = this.subEditPriceCell;
        if (editTextCell != null) {
            editTextCell.setVisibility(tL_chatInviteExported.subscription_pricing != null ? 0 : 8);
            this.subEditPriceCell.setText(Long.toString(tL_chatInviteExported.subscription_pricing.amount));
            this.subEditPriceCell.editText.setClickable(false);
            this.subEditPriceCell.editText.setFocusable(false);
            this.subEditPriceCell.editText.setFocusableInTouchMode(false);
            this.subEditPriceCell.editText.setLongClickable(false);
        }
    }

    private void setUsesVisible(boolean z) {
        this.usesHeaderCell.setVisibility(z ? 0 : 8);
        this.usesChooseView.setVisibility(z ? 0 : 8);
        this.usesEditText.setVisibility(z ? 0 : 8);
        this.dividerUses.setVisibility(z ? 0 : 8);
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void finishFragment() {
        this.scrollView.getLayoutParams().height = this.scrollView.getHeight();
        this.finished = true;
        super.finishFragment();
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public ArrayList getThemeDescriptions() {
        ThemeDescription.ThemeDescriptionDelegate themeDescriptionDelegate = new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.LinkEditActivity$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                this.f$0.lambda$getThemeDescriptions$16();
            }

            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public /* synthetic */ void onAnimationProgress(float f) {
                ThemeDescription.ThemeDescriptionDelegate.CC.$default$onAnimationProgress(this, f);
            }
        };
        ArrayList arrayList = new ArrayList();
        int i = Theme.key_windowBackgroundWhiteBlueHeader;
        arrayList.add(new ThemeDescription(this.timeHeaderCell, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i));
        arrayList.add(new ThemeDescription(this.usesHeaderCell, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i));
        HeaderCell headerCell = this.timeHeaderCell;
        int i2 = ThemeDescription.FLAG_BACKGROUND;
        int i3 = Theme.key_windowBackgroundWhite;
        arrayList.add(new ThemeDescription(headerCell, i2, null, null, null, null, i3));
        arrayList.add(new ThemeDescription(this.usesHeaderCell, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, i3));
        arrayList.add(new ThemeDescription(this.timeChooseView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, i3));
        arrayList.add(new ThemeDescription(this.usesChooseView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, i3));
        arrayList.add(new ThemeDescription(this.timeEditText, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, i3));
        arrayList.add(new ThemeDescription(this.usesEditText, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, i3));
        arrayList.add(new ThemeDescription(this.revokeLink, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, i3));
        int i4 = Theme.key_windowBackgroundWhiteGrayText4;
        arrayList.add(new ThemeDescription(this.divider, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i4));
        arrayList.add(new ThemeDescription(this.dividerUses, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i4));
        arrayList.add(new ThemeDescription(this.dividerName, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i4));
        arrayList.add(new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_featuredStickers_addButton));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_featuredStickers_addButtonPressed));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_windowBackgroundWhiteGrayText));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_featuredStickers_buttonText));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_text_RedRegular));
        return arrayList;
    }

    public /* synthetic */ void lambda$getThemeDescriptions$16() {
        TextInfoPrivacyCell textInfoPrivacyCell = this.dividerUses;
        if (textInfoPrivacyCell != null) {
            textInfoPrivacyCell.getContext();
            this.buttonTextView.setBackgroundDrawable(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1081dp(6.0f), Theme.getColor(Theme.key_featuredStickers_addButton), Theme.getColor(Theme.key_featuredStickers_addButtonPressed)));
            EditText editText = this.usesEditText;
            int i = Theme.key_windowBackgroundWhiteBlackText;
            editText.setTextColor(Theme.getColor(i));
            EditText editText2 = this.usesEditText;
            int i2 = Theme.key_windowBackgroundWhiteGrayText;
            editText2.setHintTextColor(Theme.getColor(i2));
            this.timeEditText.setTextColor(Theme.getColor(i));
            this.timeEditText.setHintTextColor(Theme.getColor(i2));
            this.buttonTextView.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText));
            TextSettingsCell textSettingsCell = this.revokeLink;
            if (textSettingsCell != null) {
                textSettingsCell.setTextColor(Theme.getColor(Theme.key_text_RedRegular));
            }
            this.createTextView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultTitle));
            this.nameEditText.setTextColor(Theme.getColor(i));
            this.nameEditText.setHintTextColor(Theme.getColor(i2));
        }
    }
}
