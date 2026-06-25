package org.telegram.p035ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.widget.NestedScrollView;
import com.exteragram.messenger.ExteraConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.p035ui.ActionBar.ActionBar;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.BottomSheet;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.ChatActivity;
import org.telegram.p035ui.Components.AlertsCreator;
import org.telegram.p035ui.Components.Bulletin;
import org.telegram.p035ui.Components.ChatAttachAlertContactsLayout;
import org.telegram.p035ui.Components.LinkSpanDrawable;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes7.dex */
public class PhonebookShareAlert extends BottomSheet {
    private ActionBar actionBar;
    private AnimatorSet actionBarAnimation;
    private View actionBarShadow;
    private Paint backgroundPaint;
    private TextView buttonTextView;
    private TLRPC.User currentUser;
    private ChatAttachAlertContactsLayout.PhonebookShareAlertDelegate delegate;
    private boolean inLayout;
    private boolean isImport;
    private LinearLayout linearLayout;
    private ListAdapter listAdapter;
    private ArrayList<AndroidUtilities.VcardItem> other;
    private BaseFragment parentFragment;
    private int phoneEndRow;
    private int phoneStartRow;
    private ArrayList<AndroidUtilities.VcardItem> phones;
    private int rowCount;
    private int scrollOffsetY;
    private NestedScrollView scrollView;
    private View shadow;
    private AnimatorSet shadowAnimation;
    private int userRow;
    private int vcardEndRow;
    private int vcardStartRow;

    @Override // org.telegram.p035ui.ActionBar.BottomSheet
    public boolean canDismissWithSwipe() {
        return false;
    }

    public class UserCell extends LinearLayout {
        public UserCell(Context context) {
            String userStatus;
            boolean z;
            super(context);
            setOrientation(1);
            if (PhonebookShareAlert.this.phones.size() == 1 && PhonebookShareAlert.this.other.size() == 0) {
                userStatus = ((AndroidUtilities.VcardItem) PhonebookShareAlert.this.phones.get(0)).getValue(true);
                z = false;
            } else {
                userStatus = (PhonebookShareAlert.this.currentUser.status == null || PhonebookShareAlert.this.currentUser.status.expires == 0) ? null : LocaleController.formatUserStatus(((BottomSheet) PhonebookShareAlert.this).currentAccount, PhonebookShareAlert.this.currentUser);
                z = true;
            }
            AvatarDrawable avatarDrawable = new AvatarDrawable();
            avatarDrawable.setTextSize(AndroidUtilities.m1036dp(30.0f));
            avatarDrawable.setInfo(((BottomSheet) PhonebookShareAlert.this).currentAccount, PhonebookShareAlert.this.currentUser);
            BackupImageView backupImageView = new BackupImageView(context);
            backupImageView.setRoundRadius(ExteraConfig.getAvatarCorners(80.0f));
            backupImageView.setForUserOrChat(PhonebookShareAlert.this.currentUser, avatarDrawable);
            addView(backupImageView, LayoutHelper.createLinear(80, 80, 49, 0, 32, 0, 0));
            TextView textView = new TextView(context);
            textView.setTypeface(AndroidUtilities.bold());
            textView.setTextSize(1, 17.0f);
            textView.setTextColor(PhonebookShareAlert.this.getThemedColor(Theme.key_dialogTextBlack));
            textView.setSingleLine(true);
            TextUtils.TruncateAt truncateAt = TextUtils.TruncateAt.END;
            textView.setEllipsize(truncateAt);
            textView.setText(ContactsController.formatName(PhonebookShareAlert.this.currentUser.first_name, PhonebookShareAlert.this.currentUser.last_name));
            addView(textView, LayoutHelper.createLinear(-2, -2, 49, 10, 10, 10, userStatus != null ? 0 : 27));
            if (userStatus != null) {
                TextView textView2 = new TextView(context);
                textView2.setTextSize(1, 14.0f);
                textView2.setTextColor(PhonebookShareAlert.this.getThemedColor(Theme.key_dialogTextGray3));
                textView2.setSingleLine(true);
                textView2.setEllipsize(truncateAt);
                textView2.setText(userStatus);
                addView(textView2, LayoutHelper.createLinear(-2, -2, 49, 10, 3, 10, z ? 27 : 11));
            }
        }
    }

    public class TextCheckBoxCell extends FrameLayout {
        private Switch checkBox;
        private ImageView imageView;
        private boolean needDivider;
        private TextView textView;
        private TextView valueTextView;

        public TextCheckBoxCell(Context context) {
            float f;
            float f2;
            float f3;
            super(context);
            TextView textView = new TextView(context);
            this.textView = textView;
            textView.setTextColor(PhonebookShareAlert.this.getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
            this.textView.setTextSize(1, 16.0f);
            this.textView.setSingleLine(false);
            this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
            this.textView.setEllipsize(TextUtils.TruncateAt.END);
            TextView textView2 = this.textView;
            boolean z = LocaleController.isRTL;
            int i = (z ? 5 : 3) | 48;
            float f4 = 72.0f;
            if (z) {
                f = PhonebookShareAlert.this.isImport ? 17 : 64;
            } else {
                f = 72.0f;
            }
            if (LocaleController.isRTL) {
                f2 = 72.0f;
            } else {
                f2 = PhonebookShareAlert.this.isImport ? 17 : 64;
            }
            addView(textView2, LayoutHelper.createFrame(-1, -1.0f, i, f, 10.0f, f2, 0.0f));
            TextView textView3 = new TextView(context);
            this.valueTextView = textView3;
            textView3.setTextColor(PhonebookShareAlert.this.getThemedColor(Theme.key_windowBackgroundWhiteGrayText2));
            this.valueTextView.setTextSize(1, 13.0f);
            this.valueTextView.setLines(1);
            this.valueTextView.setMaxLines(1);
            this.valueTextView.setSingleLine(true);
            this.valueTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            TextView textView4 = this.valueTextView;
            boolean z2 = LocaleController.isRTL;
            int i2 = z2 ? 5 : 3;
            if (z2) {
                f3 = PhonebookShareAlert.this.isImport ? 17 : 64;
            } else {
                f3 = 72.0f;
            }
            if (!LocaleController.isRTL) {
                f4 = PhonebookShareAlert.this.isImport ? 17 : 64;
            }
            addView(textView4, LayoutHelper.createFrame(-2, -2.0f, i2, f3, 35.0f, f4, 0.0f));
            ImageView imageView = new ImageView(context);
            this.imageView = imageView;
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            this.imageView.setColorFilter(new PorterDuffColorFilter(PhonebookShareAlert.this.getThemedColor(Theme.key_windowBackgroundWhiteGrayIcon), PorterDuff.Mode.MULTIPLY));
            ImageView imageView2 = this.imageView;
            boolean z3 = LocaleController.isRTL;
            addView(imageView2, LayoutHelper.createFrame(-2, -2.0f, (z3 ? 5 : 3) | 48, z3 ? 0.0f : 20.0f, 20.0f, z3 ? 20.0f : 0.0f, 0.0f));
            if (!PhonebookShareAlert.this.isImport) {
                Switch r1 = new Switch(context);
                this.checkBox = r1;
                int i3 = Theme.key_switchTrack;
                int i4 = Theme.key_switchTrackChecked;
                int i5 = Theme.key_windowBackgroundWhite;
                r1.setColors(i3, i4, i5, i5);
                addView(this.checkBox, LayoutHelper.createFrame(37, 40.0f, (LocaleController.isRTL ? 3 : 5) | 16, 22.0f, 0.0f, 22.0f, 0.0f));
            }
            setClipChildren(false);
        }

        @Override // android.view.View
        public void invalidate() {
            super.invalidate();
            Switch r0 = this.checkBox;
            if (r0 != null) {
                r0.invalidate();
            }
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            measureChildWithMargins(this.textView, i, 0, i2, 0);
            measureChildWithMargins(this.valueTextView, i, 0, i2, 0);
            measureChildWithMargins(this.imageView, i, 0, i2, 0);
            Switch r7 = this.checkBox;
            if (r7 != null) {
                measureChildWithMargins(r7, i, 0, i2, 0);
            }
            setMeasuredDimension(View.MeasureSpec.getSize(i), Math.max(AndroidUtilities.m1036dp(64.0f), this.textView.getMeasuredHeight() + this.valueTextView.getMeasuredHeight() + AndroidUtilities.m1036dp(20.0f)) + (this.needDivider ? 1 : 0));
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            int measuredHeight = this.textView.getMeasuredHeight() + AndroidUtilities.m1036dp(13.0f);
            TextView textView = this.valueTextView;
            textView.layout(textView.getLeft(), measuredHeight, this.valueTextView.getRight(), this.valueTextView.getMeasuredHeight() + measuredHeight);
        }

        public void setVCardItem(AndroidUtilities.VcardItem vcardItem, int i, boolean z) {
            this.textView.setText(vcardItem.getValue(true));
            this.valueTextView.setText(vcardItem.getType());
            Switch r0 = this.checkBox;
            if (r0 != null) {
                r0.setChecked(vcardItem.checked, false);
            }
            ImageView imageView = this.imageView;
            if (i != 0) {
                imageView.setImageResource(i);
            } else {
                imageView.setImageDrawable(null);
            }
            this.needDivider = z;
            setWillNotDraw(!z);
        }

        public void setChecked(boolean z) {
            Switch r1 = this.checkBox;
            if (r1 != null) {
                r1.setChecked(z, true);
            }
        }

        @Override // android.view.View
        public void onDraw(Canvas canvas) {
            if (this.needDivider) {
                canvas.drawLine(LocaleController.isRTL ? 0.0f : AndroidUtilities.m1036dp(70.0f), getMeasuredHeight() - 1, getMeasuredWidth() - (LocaleController.isRTL ? AndroidUtilities.m1036dp(70.0f) : 0), getMeasuredHeight() - 1, Theme.dividerPaint);
            }
        }
    }

    public PhonebookShareAlert(BaseFragment baseFragment, ContactsController.Contact contact, TLRPC.User user, Uri uri, File file, String str, String str2) {
        this(baseFragment, contact, user, uri, file, (String) null, str, str2);
    }

    public PhonebookShareAlert(BaseFragment baseFragment, ContactsController.Contact contact, TLRPC.User user, Uri uri, File file, String str, String str2, String str3) {
        this(baseFragment, contact, user, uri, file, str, str2, str3, null);
    }

    public PhonebookShareAlert(BaseFragment baseFragment, ContactsController.Contact contact, TLRPC.User user, Uri uri, File file, String str, String str2, Theme.ResourcesProvider resourcesProvider) {
        this(baseFragment, contact, user, uri, file, null, str, str2, resourcesProvider);
    }

    /* JADX WARN: Removed duplicated region for block: B:121:0x0107  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PhonebookShareAlert(org.telegram.p035ui.ActionBar.BaseFragment r14, org.telegram.messenger.ContactsController.Contact r15, org.telegram.tgnet.TLRPC.User r16, android.net.Uri r17, java.io.File r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, final org.telegram.ui.ActionBar.Theme.ResourcesProvider r22) {
        /*
            Method dump skipped, instruction units count: 843
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.PhonebookShareAlert.<init>(org.telegram.ui.ActionBar.BaseFragment, org.telegram.messenger.ContactsController$Contact, org.telegram.tgnet.TLRPC$User, android.net.Uri, java.io.File, java.lang.String, java.lang.String, java.lang.String, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.PhonebookShareAlert$1 */
    public class C46521 extends FrameLayout {
        private boolean ignoreLayout;
        private RectF rect = new RectF();
        final /* synthetic */ Context val$context;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C46521(Context context, Context context2) {
            super(context);
            context = context2;
            this.rect = new RectF();
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0 && PhonebookShareAlert.this.scrollOffsetY != 0 && motionEvent.getY() < PhonebookShareAlert.this.scrollOffsetY && PhonebookShareAlert.this.actionBar.getAlpha() == 0.0f) {
                PhonebookShareAlert.this.lambda$new$0();
                return true;
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return !PhonebookShareAlert.this.isDismissed() && super.onTouchEvent(motionEvent);
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i2);
            this.ignoreLayout = true;
            setPadding(((BottomSheet) PhonebookShareAlert.this).backgroundPaddingLeft, AndroidUtilities.statusBarHeight, ((BottomSheet) PhonebookShareAlert.this).backgroundPaddingLeft, 0);
            this.ignoreLayout = false;
            int paddingTop = size - getPaddingTop();
            View.MeasureSpec.getSize(i);
            int unused = ((BottomSheet) PhonebookShareAlert.this).backgroundPaddingLeft;
            ((FrameLayout.LayoutParams) PhonebookShareAlert.this.actionBarShadow.getLayoutParams()).topMargin = ActionBar.getCurrentActionBarHeight();
            this.ignoreLayout = true;
            int iM1036dp = AndroidUtilities.m1036dp(80.0f);
            int itemCount = PhonebookShareAlert.this.listAdapter.getItemCount();
            for (int i3 = 0; i3 < itemCount; i3++) {
                View viewCreateView = PhonebookShareAlert.this.listAdapter.createView(context, i3);
                viewCreateView.measure(i, View.MeasureSpec.makeMeasureSpec(0, 0));
                iM1036dp += viewCreateView.getMeasuredHeight();
            }
            int i4 = iM1036dp < paddingTop ? paddingTop - iM1036dp : paddingTop / 5;
            if (PhonebookShareAlert.this.scrollView.getPaddingTop() != i4) {
                PhonebookShareAlert.this.scrollView.getPaddingTop();
                PhonebookShareAlert.this.scrollView.setPadding(0, i4, 0, 0);
            }
            this.ignoreLayout = false;
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30));
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            PhonebookShareAlert.this.inLayout = true;
            super.onLayout(z, i, i2, i3, i4);
            PhonebookShareAlert.this.inLayout = false;
            PhonebookShareAlert.this.updateLayout(false);
        }

        @Override // android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (this.ignoreLayout) {
                return;
            }
            super.requestLayout();
        }

        @Override // android.view.View
        public void onDraw(Canvas canvas) {
            int i = PhonebookShareAlert.this.scrollOffsetY - ((BottomSheet) PhonebookShareAlert.this).backgroundPaddingTop;
            int measuredHeight = getMeasuredHeight() + AndroidUtilities.m1036dp(30.0f) + ((BottomSheet) PhonebookShareAlert.this).backgroundPaddingTop;
            float fM1036dp = AndroidUtilities.m1036dp(12.0f);
            float fMin = ((float) (((BottomSheet) PhonebookShareAlert.this).backgroundPaddingTop + i)) < fM1036dp ? 1.0f - Math.min(1.0f, ((fM1036dp - i) - ((BottomSheet) PhonebookShareAlert.this).backgroundPaddingTop) / fM1036dp) : 1.0f;
            int i2 = AndroidUtilities.statusBarHeight;
            ((BottomSheet) PhonebookShareAlert.this).shadowDrawable.setBounds(0, i + i2, getMeasuredWidth(), measuredHeight - i2);
            ((BottomSheet) PhonebookShareAlert.this).shadowDrawable.draw(canvas);
            if (fMin != 1.0f) {
                PhonebookShareAlert.this.backgroundPaint.setColor(PhonebookShareAlert.this.getThemedColor(Theme.key_dialogBackground));
                this.rect.set(((BottomSheet) PhonebookShareAlert.this).backgroundPaddingLeft, ((BottomSheet) PhonebookShareAlert.this).backgroundPaddingTop + r0, getMeasuredWidth() - ((BottomSheet) PhonebookShareAlert.this).backgroundPaddingLeft, ((BottomSheet) PhonebookShareAlert.this).backgroundPaddingTop + r0 + AndroidUtilities.m1036dp(24.0f));
                float f = fM1036dp * fMin;
                canvas.drawRoundRect(this.rect, f, f, PhonebookShareAlert.this.backgroundPaint);
            }
            int themedColor = PhonebookShareAlert.this.getThemedColor(Theme.key_dialogBackground);
            PhonebookShareAlert.this.backgroundPaint.setColor(Color.argb((int) (PhonebookShareAlert.this.actionBar.getAlpha() * 255.0f), (int) (Color.red(themedColor) * 0.8f), (int) (Color.green(themedColor) * 0.8f), (int) (Color.blue(themedColor) * 0.8f)));
            canvas.drawRect(((BottomSheet) PhonebookShareAlert.this).backgroundPaddingLeft, 0.0f, getMeasuredWidth() - ((BottomSheet) PhonebookShareAlert.this).backgroundPaddingLeft, AndroidUtilities.statusBarHeight, PhonebookShareAlert.this.backgroundPaint);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.PhonebookShareAlert$2 */
    public class C46532 extends NestedScrollView {
        private View focusingView;

        public C46532(Context context) {
            super(context);
        }

        @Override // androidx.core.widget.NestedScrollView, android.view.ViewGroup, android.view.ViewParent
        public void requestChildFocus(View view, View view2) {
            this.focusingView = view2;
            super.requestChildFocus(view, view2);
        }

        @Override // androidx.core.widget.NestedScrollView
        public int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
            if (this.focusingView == null || PhonebookShareAlert.this.linearLayout.getTop() != getPaddingTop()) {
                return 0;
            }
            int iComputeScrollDeltaToGetChildRectOnScreen = super.computeScrollDeltaToGetChildRectOnScreen(rect);
            int currentActionBarHeight = ActionBar.getCurrentActionBarHeight() - (((this.focusingView.getTop() - getScrollY()) + rect.top) + iComputeScrollDeltaToGetChildRectOnScreen);
            return currentActionBarHeight > 0 ? iComputeScrollDeltaToGetChildRectOnScreen - (currentActionBarHeight + AndroidUtilities.m1036dp(10.0f)) : iComputeScrollDeltaToGetChildRectOnScreen;
        }
    }

    public /* synthetic */ void lambda$new$0(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
        updateLayout(!this.inLayout);
    }

    public /* synthetic */ void lambda$new$2(int i, View view, View view2) {
        final AndroidUtilities.VcardItem vcardItem;
        int i2 = this.phoneStartRow;
        if (i >= i2 && i < this.phoneEndRow) {
            vcardItem = this.phones.get(i - i2);
        } else {
            int i3 = this.vcardStartRow;
            vcardItem = (i < i3 || i >= this.vcardEndRow) ? null : this.other.get(i - i3);
        }
        if (vcardItem == null) {
            return;
        }
        boolean z = true;
        if (this.isImport) {
            int i4 = vcardItem.type;
            if (i4 == 0) {
                try {
                    Intent intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + vcardItem.getValue(false)));
                    intent.addFlags(268435456);
                    this.parentFragment.getParentActivity().startActivityForResult(intent, 500);
                    return;
                } catch (Exception e) {
                    FileLog.m1048e(e);
                    return;
                }
            }
            if (i4 == 1) {
                Browser.openUrl(this.parentFragment.getParentActivity(), "mailto:" + vcardItem.getValue(false));
                return;
            }
            if (i4 == 3) {
                String value = vcardItem.getValue(false);
                if (!value.startsWith("http")) {
                    value = "http://".concat(value);
                }
                Browser.openUrl(this.parentFragment.getParentActivity(), value);
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this.parentFragment.getParentActivity());
            builder.setItems(new CharSequence[]{LocaleController.getString(C2797R.string.Copy)}, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Components.PhonebookShareAlert$$ExternalSyntheticLambda6
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i5) {
                    this.f$0.lambda$new$1(vcardItem, dialogInterface, i5);
                }
            });
            builder.show();
            return;
        }
        vcardItem.checked = !vcardItem.checked;
        if (i >= this.phoneStartRow && i < this.phoneEndRow) {
            int i5 = 0;
            while (true) {
                if (i5 >= this.phones.size()) {
                    z = false;
                    break;
                } else if (this.phones.get(i5).checked) {
                    break;
                } else {
                    i5++;
                }
            }
            int themedColor = getThemedColor(Theme.key_featuredStickers_buttonText);
            this.buttonTextView.setEnabled(z);
            TextView textView = this.buttonTextView;
            if (!z) {
                themedColor &= Integer.MAX_VALUE;
            }
            textView.setTextColor(themedColor);
        }
        ((TextCheckBoxCell) view).setChecked(vcardItem.checked);
    }

    public /* synthetic */ void lambda$new$1(AndroidUtilities.VcardItem vcardItem, DialogInterface dialogInterface, int i) {
        if (i == 0) {
            try {
                ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", vcardItem.getValue(false)));
                if (AndroidUtilities.shouldShowClipboardToast()) {
                    Toast.makeText(this.parentFragment.getParentActivity(), LocaleController.getString(C2797R.string.TextCopied), 0).show();
                }
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
    }

    public /* synthetic */ boolean lambda$new$3(int i, Theme.ResourcesProvider resourcesProvider, Context context, View view) {
        AndroidUtilities.VcardItem vcardItem;
        int i2 = this.phoneStartRow;
        if (i >= i2 && i < this.phoneEndRow) {
            vcardItem = this.phones.get(i - i2);
        } else {
            int i3 = this.vcardStartRow;
            vcardItem = (i < i3 || i >= this.vcardEndRow) ? null : this.other.get(i - i3);
        }
        if (vcardItem == null) {
            return false;
        }
        ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", vcardItem.getValue(false)));
        if (BulletinFactory.canShowBulletin(this.parentFragment)) {
            if (vcardItem.type == 3) {
                BulletinFactory.m1142of((FrameLayout) this.containerView, resourcesProvider).createCopyLinkBulletin().show();
            } else {
                Bulletin.SimpleLayout simpleLayout = new Bulletin.SimpleLayout(context, resourcesProvider);
                int i4 = vcardItem.type;
                if (i4 == 0) {
                    simpleLayout.textView.setText(LocaleController.getString(C2797R.string.PhoneCopied));
                    simpleLayout.imageView.setImageResource(C2797R.drawable.msg_calls);
                } else {
                    LinkSpanDrawable.LinksTextView linksTextView = simpleLayout.textView;
                    if (i4 == 1) {
                        linksTextView.setText(LocaleController.getString(C2797R.string.EmailCopied));
                        simpleLayout.imageView.setImageResource(C2797R.drawable.msg_mention);
                    } else {
                        linksTextView.setText(LocaleController.getString(C2797R.string.TextCopied));
                        simpleLayout.imageView.setImageResource(C2797R.drawable.msg_info);
                    }
                }
                if (AndroidUtilities.shouldShowClipboardToast()) {
                    Bulletin.make((FrameLayout) this.containerView, simpleLayout, 1500).show();
                }
            }
        }
        return true;
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.PhonebookShareAlert$3 */
    public class C46543 extends ActionBar {
        public C46543(Context context) {
            super(context);
        }

        @Override // android.view.View
        public void setAlpha(float f) {
            super.setAlpha(f);
            ((BottomSheet) PhonebookShareAlert.this).containerView.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.PhonebookShareAlert$4 */
    public class C46554 extends ActionBar.ActionBarMenuOnItemClick {
        public C46554() {
        }

        @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
        public void onItemClick(int i) {
            if (i == -1) {
                PhonebookShareAlert.this.lambda$new$0();
            }
        }
    }

    public /* synthetic */ void lambda$new$6(Theme.ResourcesProvider resourcesProvider, View view) {
        StringBuilder sb;
        if (this.isImport) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(LocaleController.getString(C2797R.string.AddContactTitle));
            builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
            builder.setItems(new CharSequence[]{LocaleController.getString(C2797R.string.CreateNewContact), LocaleController.getString(C2797R.string.AddToExistingContact)}, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Components.PhonebookShareAlert.5
                public DialogInterfaceOnClickListenerC46565() {
                }

                private void fillRowWithType(String str, ContentValues contentValues) {
                    if (str.startsWith("X-")) {
                        contentValues.put("data2", (Integer) 0);
                        contentValues.put("data3", str.substring(2));
                        return;
                    }
                    if ("PREF".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 12);
                        return;
                    }
                    if ("HOME".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 1);
                        return;
                    }
                    if ("MOBILE".equalsIgnoreCase(str) || "CELL".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 2);
                        return;
                    }
                    if ("OTHER".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 7);
                        return;
                    }
                    if ("WORK".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 3);
                        return;
                    }
                    if ("RADIO".equalsIgnoreCase(str) || "VOICE".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 14);
                        return;
                    }
                    if ("PAGER".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 6);
                        return;
                    }
                    if ("CALLBACK".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 8);
                        return;
                    }
                    if ("CAR".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 9);
                        return;
                    }
                    if ("ASSISTANT".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 19);
                        return;
                    }
                    if ("MMS".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 20);
                    } else if (str.startsWith("FAX")) {
                        contentValues.put("data2", (Integer) 4);
                    } else {
                        contentValues.put("data2", (Integer) 0);
                        contentValues.put("data3", str);
                    }
                }

                private void fillUrlRowWithType(String str, ContentValues contentValues) {
                    if (str.startsWith("X-")) {
                        contentValues.put("data2", (Integer) 0);
                        contentValues.put("data3", str.substring(2));
                        return;
                    }
                    if ("HOMEPAGE".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 1);
                        return;
                    }
                    if ("BLOG".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 2);
                        return;
                    }
                    if ("PROFILE".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 3);
                        return;
                    }
                    if ("HOME".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 4);
                        return;
                    }
                    if ("WORK".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 5);
                        return;
                    }
                    if ("FTP".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 6);
                    } else if ("OTHER".equalsIgnoreCase(str)) {
                        contentValues.put("data2", (Integer) 7);
                    } else {
                        contentValues.put("data2", (Integer) 0);
                        contentValues.put("data3", str);
                    }
                }

                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent;
                    String str;
                    String str2;
                    int i2;
                    boolean z;
                    Intent intent2;
                    String str3;
                    String str4;
                    DialogInterfaceOnClickListenerC46565 dialogInterfaceOnClickListenerC46565;
                    Integer num;
                    boolean z2;
                    String str5;
                    boolean z3;
                    DialogInterfaceOnClickListenerC46565 dialogInterfaceOnClickListenerC465652 = this;
                    int i3 = 3;
                    Integer num2 = 3;
                    int i4 = 1;
                    if (i == 0) {
                        intent = new Intent("android.intent.action.INSERT");
                        intent.setType("vnd.android.cursor.dir/raw_contact");
                    } else if (i == 1) {
                        intent = new Intent("android.intent.action.INSERT_OR_EDIT");
                        intent.setType("vnd.android.cursor.item/contact");
                    } else {
                        intent = null;
                    }
                    intent.putExtra("name", ContactsController.formatName(PhonebookShareAlert.this.currentUser.first_name, PhonebookShareAlert.this.currentUser.last_name));
                    ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
                    boolean z4 = false;
                    int i5 = 0;
                    while (true) {
                        str = "data1";
                        str2 = "mimetype";
                        if (i5 >= PhonebookShareAlert.this.phones.size()) {
                            break;
                        }
                        AndroidUtilities.VcardItem vcardItem = (AndroidUtilities.VcardItem) PhonebookShareAlert.this.phones.get(i5);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("mimetype", "vnd.android.cursor.item/phone_v2");
                        contentValues.put("data1", vcardItem.getValue(false));
                        dialogInterfaceOnClickListenerC465652.fillRowWithType(vcardItem.getRawType(false), contentValues);
                        arrayList.add(contentValues);
                        i5++;
                    }
                    int i6 = 0;
                    boolean z5 = false;
                    while (i6 < PhonebookShareAlert.this.other.size()) {
                        AndroidUtilities.VcardItem vcardItem2 = (AndroidUtilities.VcardItem) PhonebookShareAlert.this.other.get(i6);
                        int i7 = vcardItem2.type;
                        if (i7 == i4) {
                            ContentValues contentValues2 = new ContentValues();
                            contentValues2.put(str2, "vnd.android.cursor.item/email_v2");
                            contentValues2.put(str, vcardItem2.getValue(z4));
                            dialogInterfaceOnClickListenerC465652.fillRowWithType(vcardItem2.getRawType(z4), contentValues2);
                            arrayList.add(contentValues2);
                        } else if (i7 == i3) {
                            ContentValues contentValues3 = new ContentValues();
                            contentValues3.put(str2, "vnd.android.cursor.item/website");
                            contentValues3.put(str, vcardItem2.getValue(z4));
                            dialogInterfaceOnClickListenerC465652.fillUrlRowWithType(vcardItem2.getRawType(z4), contentValues3);
                            arrayList.add(contentValues3);
                        } else if (i7 == 4) {
                            ContentValues contentValues4 = new ContentValues();
                            contentValues4.put(str2, "vnd.android.cursor.item/note");
                            contentValues4.put(str, vcardItem2.getValue(z4));
                            arrayList.add(contentValues4);
                        } else if (i7 == 5) {
                            ContentValues contentValues5 = new ContentValues();
                            contentValues5.put(str2, "vnd.android.cursor.item/contact_event");
                            contentValues5.put(str, vcardItem2.getValue(z4));
                            contentValues5.put("data2", num2);
                            arrayList.add(contentValues5);
                        } else {
                            boolean z6 = z4;
                            i2 = i6;
                            z = z5;
                            intent2 = intent;
                            if (i7 == 2) {
                                ContentValues contentValues6 = new ContentValues();
                                contentValues6.put(str2, "vnd.android.cursor.item/postal-address_v2");
                                String[] rawValue = vcardItem2.getRawValue();
                                String str6 = str;
                                if (rawValue.length > 0) {
                                    contentValues6.put("data5", rawValue[z6 ? 1 : 0]);
                                }
                                if (rawValue.length > 1) {
                                    contentValues6.put("data6", rawValue[1]);
                                }
                                if (rawValue.length > 2) {
                                    contentValues6.put("data4", rawValue[2]);
                                }
                                if (rawValue.length > 3) {
                                    contentValues6.put("data7", rawValue[3]);
                                }
                                if (rawValue.length > 4) {
                                    contentValues6.put("data8", rawValue[4]);
                                }
                                if (rawValue.length > 5) {
                                    contentValues6.put("data9", rawValue[5]);
                                }
                                if (rawValue.length > 6) {
                                    contentValues6.put("data10", rawValue[6]);
                                }
                                String rawType = vcardItem2.getRawType(z6);
                                if ("HOME".equalsIgnoreCase(rawType)) {
                                    contentValues6.put("data2", (Integer) 1);
                                } else if ("WORK".equalsIgnoreCase(rawType)) {
                                    contentValues6.put("data2", (Integer) 2);
                                } else if ("OTHER".equalsIgnoreCase(rawType)) {
                                    contentValues6.put("data2", num2);
                                }
                                arrayList.add(contentValues6);
                                z2 = false;
                                dialogInterfaceOnClickListenerC46565 = this;
                                num = num2;
                                str3 = str2;
                                str4 = str6;
                            } else {
                                String str7 = str;
                                if (i7 == 20) {
                                    ContentValues contentValues7 = new ContentValues();
                                    contentValues7.put(str2, "vnd.android.cursor.item/im");
                                    String rawType2 = vcardItem2.getRawType(true);
                                    String rawType3 = vcardItem2.getRawType(false);
                                    str3 = str2;
                                    str4 = str7;
                                    contentValues7.put(str4, vcardItem2.getValue(false));
                                    if ("AIM".equalsIgnoreCase(rawType2)) {
                                        contentValues7.put("data5", (Integer) 0);
                                    } else if ("MSN".equalsIgnoreCase(rawType2)) {
                                        contentValues7.put("data5", (Integer) 1);
                                    } else if ("YAHOO".equalsIgnoreCase(rawType2)) {
                                        contentValues7.put("data5", (Integer) 2);
                                    } else if ("SKYPE".equalsIgnoreCase(rawType2)) {
                                        contentValues7.put("data5", num2);
                                    } else if ("QQ".equalsIgnoreCase(rawType2)) {
                                        contentValues7.put("data5", (Integer) 4);
                                    } else if ("GOOGLE-TALK".equalsIgnoreCase(rawType2)) {
                                        contentValues7.put("data5", (Integer) 5);
                                    } else if ("ICQ".equalsIgnoreCase(rawType2)) {
                                        contentValues7.put("data5", (Integer) 6);
                                    } else if ("JABBER".equalsIgnoreCase(rawType2)) {
                                        contentValues7.put("data5", (Integer) 7);
                                    } else if ("NETMEETING".equalsIgnoreCase(rawType2)) {
                                        contentValues7.put("data5", (Integer) 8);
                                    } else {
                                        contentValues7.put("data5", (Integer) (-1));
                                        contentValues7.put("data6", vcardItem2.getRawType(true));
                                    }
                                    if ("HOME".equalsIgnoreCase(rawType3)) {
                                        contentValues7.put("data2", (Integer) 1);
                                    } else if ("WORK".equalsIgnoreCase(rawType3)) {
                                        contentValues7.put("data2", (Integer) 2);
                                    } else if ("OTHER".equalsIgnoreCase(rawType3)) {
                                        contentValues7.put("data2", num2);
                                    }
                                    arrayList.add(contentValues7);
                                } else {
                                    str3 = str2;
                                    str4 = str7;
                                    if (i7 == 6 && !z) {
                                        ContentValues contentValues8 = new ContentValues();
                                        String str8 = str3;
                                        contentValues8.put(str8, "vnd.android.cursor.item/organization");
                                        dialogInterfaceOnClickListenerC46565 = this;
                                        int i8 = i2;
                                        while (i8 < PhonebookShareAlert.this.other.size()) {
                                            AndroidUtilities.VcardItem vcardItem3 = (AndroidUtilities.VcardItem) PhonebookShareAlert.this.other.get(i8);
                                            Integer num3 = num2;
                                            if (vcardItem3.type != 6) {
                                                str5 = str8;
                                            } else {
                                                String rawType4 = vcardItem3.getRawType(true);
                                                if ("ORG".equalsIgnoreCase(rawType4)) {
                                                    String[] rawValue2 = vcardItem3.getRawValue();
                                                    if (rawValue2.length != 0) {
                                                        str5 = str8;
                                                        if (rawValue2.length >= 1) {
                                                            contentValues8.put(str4, rawValue2[0]);
                                                        }
                                                        if (rawValue2.length >= 2) {
                                                            contentValues8.put("data5", rawValue2[1]);
                                                        }
                                                        z3 = true;
                                                    }
                                                    str5 = str8;
                                                } else {
                                                    str5 = str8;
                                                    if ("TITLE".equalsIgnoreCase(rawType4) || "ROLE".equalsIgnoreCase(rawType4)) {
                                                        contentValues8.put("data4", vcardItem3.getValue(false));
                                                    }
                                                    z3 = true;
                                                }
                                                String rawType5 = vcardItem3.getRawType(z3);
                                                if ("WORK".equalsIgnoreCase(rawType5)) {
                                                    contentValues8.put("data2", (Integer) 1);
                                                } else if ("OTHER".equalsIgnoreCase(rawType5)) {
                                                    contentValues8.put("data2", (Integer) 2);
                                                }
                                            }
                                            i8++;
                                            num2 = num3;
                                            str8 = str5;
                                        }
                                        num = num2;
                                        str3 = str8;
                                        z2 = false;
                                        arrayList.add(contentValues8);
                                        z = true;
                                    }
                                }
                                z2 = false;
                                dialogInterfaceOnClickListenerC46565 = this;
                                num = num2;
                            }
                            i6 = i2 + 1;
                            dialogInterfaceOnClickListenerC465652 = dialogInterfaceOnClickListenerC46565;
                            str = str4;
                            z4 = z2;
                            num2 = num;
                            z5 = z;
                            intent = intent2;
                            str2 = str3;
                            i3 = 3;
                            i4 = 1;
                        }
                        intent2 = intent;
                        num = num2;
                        z2 = z4;
                        i2 = i6;
                        z = z5;
                        str4 = str;
                        str3 = str2;
                        dialogInterfaceOnClickListenerC46565 = dialogInterfaceOnClickListenerC465652;
                        i6 = i2 + 1;
                        dialogInterfaceOnClickListenerC465652 = dialogInterfaceOnClickListenerC46565;
                        str = str4;
                        z4 = z2;
                        num2 = num;
                        z5 = z;
                        intent = intent2;
                        str2 = str3;
                        i3 = 3;
                        i4 = 1;
                    }
                    DialogInterfaceOnClickListenerC46565 dialogInterfaceOnClickListenerC465653 = dialogInterfaceOnClickListenerC465652;
                    intent.putExtra("finishActivityOnSaveCompleted", true);
                    intent.putParcelableArrayListExtra("data", arrayList);
                    try {
                        PhonebookShareAlert.this.parentFragment.getParentActivity().startActivity(intent);
                        PhonebookShareAlert.this.lambda$new$0();
                    } catch (Exception e) {
                        FileLog.m1048e(e);
                    }
                }
            });
            builder.show();
            return;
        }
        if (!this.currentUser.restriction_reason.isEmpty()) {
            sb = new StringBuilder(this.currentUser.restriction_reason.get(0).text);
        } else {
            Locale locale = Locale.US;
            TLRPC.User user = this.currentUser;
            sb = new StringBuilder(String.format(locale, "BEGIN:VCARD\nVERSION:3.0\nFN:%1$s\nEND:VCARD", ContactsController.formatName(user.first_name, user.last_name)));
        }
        int iLastIndexOf = sb.lastIndexOf("END:VCARD");
        if (iLastIndexOf >= 0) {
            this.currentUser.phone = null;
            for (int size = this.phones.size() - 1; size >= 0; size--) {
                AndroidUtilities.VcardItem vcardItem = this.phones.get(size);
                if (vcardItem.checked) {
                    TLRPC.User user2 = this.currentUser;
                    if (user2.phone == null) {
                        user2.phone = vcardItem.getValue(false);
                    }
                    for (int i = 0; i < vcardItem.vcardData.size(); i++) {
                        sb.insert(iLastIndexOf, vcardItem.vcardData.get(i) + "\n");
                    }
                }
            }
            for (int size2 = this.other.size() - 1; size2 >= 0; size2--) {
                AndroidUtilities.VcardItem vcardItem2 = this.other.get(size2);
                if (vcardItem2.checked) {
                    for (int size3 = vcardItem2.vcardData.size() - 1; size3 >= 0; size3 += -1) {
                        sb.insert(iLastIndexOf, vcardItem2.vcardData.get(size3) + "\n");
                    }
                }
            }
            this.currentUser.restriction_reason.clear();
            TLRPC.RestrictionReason restrictionReason = new TLRPC.RestrictionReason();
            restrictionReason.text = sb.toString();
            restrictionReason.reason = _UrlKt.FRAGMENT_ENCODE_SET;
            restrictionReason.platform = _UrlKt.FRAGMENT_ENCODE_SET;
            this.currentUser.restriction_reason.add(restrictionReason);
        }
        BaseFragment baseFragment = this.parentFragment;
        if ((baseFragment instanceof ChatActivity) && ((ChatActivity) baseFragment).isInScheduleMode()) {
            AlertsCreator.createScheduleDatePickerDialog(getContext(), ((ChatActivity) this.parentFragment).getDialogId(), new AlertsCreator.ScheduleDatePickerDelegate() { // from class: org.telegram.ui.Components.PhonebookShareAlert$$ExternalSyntheticLambda4
                @Override // org.telegram.ui.Components.AlertsCreator.ScheduleDatePickerDelegate
                public final void didSelectDate(boolean z, int i2, int i3) {
                    this.f$0.lambda$new$4(z, i2, i3);
                }
            }, resourcesProvider);
        } else {
            BaseFragment baseFragment2 = this.parentFragment;
            AlertsCreator.ensurePaidMessageConfirmation(this.currentAccount, baseFragment2 instanceof ChatActivity ? ((ChatActivity) baseFragment2).getDialogId() : 0L, 1, new Utilities.Callback() { // from class: org.telegram.ui.Components.PhonebookShareAlert$$ExternalSyntheticLambda5
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$new$5((Long) obj);
                }
            });
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.PhonebookShareAlert$5 */
    public class DialogInterfaceOnClickListenerC46565 implements DialogInterface.OnClickListener {
        public DialogInterfaceOnClickListenerC46565() {
        }

        private void fillRowWithType(String str, ContentValues contentValues) {
            if (str.startsWith("X-")) {
                contentValues.put("data2", (Integer) 0);
                contentValues.put("data3", str.substring(2));
                return;
            }
            if ("PREF".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 12);
                return;
            }
            if ("HOME".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 1);
                return;
            }
            if ("MOBILE".equalsIgnoreCase(str) || "CELL".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 2);
                return;
            }
            if ("OTHER".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 7);
                return;
            }
            if ("WORK".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 3);
                return;
            }
            if ("RADIO".equalsIgnoreCase(str) || "VOICE".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 14);
                return;
            }
            if ("PAGER".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 6);
                return;
            }
            if ("CALLBACK".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 8);
                return;
            }
            if ("CAR".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 9);
                return;
            }
            if ("ASSISTANT".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 19);
                return;
            }
            if ("MMS".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 20);
            } else if (str.startsWith("FAX")) {
                contentValues.put("data2", (Integer) 4);
            } else {
                contentValues.put("data2", (Integer) 0);
                contentValues.put("data3", str);
            }
        }

        private void fillUrlRowWithType(String str, ContentValues contentValues) {
            if (str.startsWith("X-")) {
                contentValues.put("data2", (Integer) 0);
                contentValues.put("data3", str.substring(2));
                return;
            }
            if ("HOMEPAGE".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 1);
                return;
            }
            if ("BLOG".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 2);
                return;
            }
            if ("PROFILE".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 3);
                return;
            }
            if ("HOME".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 4);
                return;
            }
            if ("WORK".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 5);
                return;
            }
            if ("FTP".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 6);
            } else if ("OTHER".equalsIgnoreCase(str)) {
                contentValues.put("data2", (Integer) 7);
            } else {
                contentValues.put("data2", (Integer) 0);
                contentValues.put("data3", str);
            }
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent;
            String str;
            String str2;
            int i2;
            boolean z;
            Intent intent2;
            String str3;
            String str4;
            DialogInterfaceOnClickListenerC46565 dialogInterfaceOnClickListenerC46565;
            Integer num;
            boolean z2;
            String str5;
            boolean z3;
            DialogInterfaceOnClickListenerC46565 dialogInterfaceOnClickListenerC465652 = this;
            int i3 = 3;
            Integer num2 = 3;
            int i4 = 1;
            if (i == 0) {
                intent = new Intent("android.intent.action.INSERT");
                intent.setType("vnd.android.cursor.dir/raw_contact");
            } else if (i == 1) {
                intent = new Intent("android.intent.action.INSERT_OR_EDIT");
                intent.setType("vnd.android.cursor.item/contact");
            } else {
                intent = null;
            }
            intent.putExtra("name", ContactsController.formatName(PhonebookShareAlert.this.currentUser.first_name, PhonebookShareAlert.this.currentUser.last_name));
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            boolean z4 = false;
            int i5 = 0;
            while (true) {
                str = "data1";
                str2 = "mimetype";
                if (i5 >= PhonebookShareAlert.this.phones.size()) {
                    break;
                }
                AndroidUtilities.VcardItem vcardItem = (AndroidUtilities.VcardItem) PhonebookShareAlert.this.phones.get(i5);
                ContentValues contentValues = new ContentValues();
                contentValues.put("mimetype", "vnd.android.cursor.item/phone_v2");
                contentValues.put("data1", vcardItem.getValue(false));
                dialogInterfaceOnClickListenerC465652.fillRowWithType(vcardItem.getRawType(false), contentValues);
                arrayList.add(contentValues);
                i5++;
            }
            int i6 = 0;
            boolean z5 = false;
            while (i6 < PhonebookShareAlert.this.other.size()) {
                AndroidUtilities.VcardItem vcardItem2 = (AndroidUtilities.VcardItem) PhonebookShareAlert.this.other.get(i6);
                int i7 = vcardItem2.type;
                if (i7 == i4) {
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put(str2, "vnd.android.cursor.item/email_v2");
                    contentValues2.put(str, vcardItem2.getValue(z4));
                    dialogInterfaceOnClickListenerC465652.fillRowWithType(vcardItem2.getRawType(z4), contentValues2);
                    arrayList.add(contentValues2);
                } else if (i7 == i3) {
                    ContentValues contentValues3 = new ContentValues();
                    contentValues3.put(str2, "vnd.android.cursor.item/website");
                    contentValues3.put(str, vcardItem2.getValue(z4));
                    dialogInterfaceOnClickListenerC465652.fillUrlRowWithType(vcardItem2.getRawType(z4), contentValues3);
                    arrayList.add(contentValues3);
                } else if (i7 == 4) {
                    ContentValues contentValues4 = new ContentValues();
                    contentValues4.put(str2, "vnd.android.cursor.item/note");
                    contentValues4.put(str, vcardItem2.getValue(z4));
                    arrayList.add(contentValues4);
                } else if (i7 == 5) {
                    ContentValues contentValues5 = new ContentValues();
                    contentValues5.put(str2, "vnd.android.cursor.item/contact_event");
                    contentValues5.put(str, vcardItem2.getValue(z4));
                    contentValues5.put("data2", num2);
                    arrayList.add(contentValues5);
                } else {
                    boolean z6 = z4;
                    i2 = i6;
                    z = z5;
                    intent2 = intent;
                    if (i7 == 2) {
                        ContentValues contentValues6 = new ContentValues();
                        contentValues6.put(str2, "vnd.android.cursor.item/postal-address_v2");
                        String[] rawValue = vcardItem2.getRawValue();
                        String str6 = str;
                        if (rawValue.length > 0) {
                            contentValues6.put("data5", rawValue[z6 ? 1 : 0]);
                        }
                        if (rawValue.length > 1) {
                            contentValues6.put("data6", rawValue[1]);
                        }
                        if (rawValue.length > 2) {
                            contentValues6.put("data4", rawValue[2]);
                        }
                        if (rawValue.length > 3) {
                            contentValues6.put("data7", rawValue[3]);
                        }
                        if (rawValue.length > 4) {
                            contentValues6.put("data8", rawValue[4]);
                        }
                        if (rawValue.length > 5) {
                            contentValues6.put("data9", rawValue[5]);
                        }
                        if (rawValue.length > 6) {
                            contentValues6.put("data10", rawValue[6]);
                        }
                        String rawType = vcardItem2.getRawType(z6);
                        if ("HOME".equalsIgnoreCase(rawType)) {
                            contentValues6.put("data2", (Integer) 1);
                        } else if ("WORK".equalsIgnoreCase(rawType)) {
                            contentValues6.put("data2", (Integer) 2);
                        } else if ("OTHER".equalsIgnoreCase(rawType)) {
                            contentValues6.put("data2", num2);
                        }
                        arrayList.add(contentValues6);
                        z2 = false;
                        dialogInterfaceOnClickListenerC46565 = this;
                        num = num2;
                        str3 = str2;
                        str4 = str6;
                    } else {
                        String str7 = str;
                        if (i7 == 20) {
                            ContentValues contentValues7 = new ContentValues();
                            contentValues7.put(str2, "vnd.android.cursor.item/im");
                            String rawType2 = vcardItem2.getRawType(true);
                            String rawType3 = vcardItem2.getRawType(false);
                            str3 = str2;
                            str4 = str7;
                            contentValues7.put(str4, vcardItem2.getValue(false));
                            if ("AIM".equalsIgnoreCase(rawType2)) {
                                contentValues7.put("data5", (Integer) 0);
                            } else if ("MSN".equalsIgnoreCase(rawType2)) {
                                contentValues7.put("data5", (Integer) 1);
                            } else if ("YAHOO".equalsIgnoreCase(rawType2)) {
                                contentValues7.put("data5", (Integer) 2);
                            } else if ("SKYPE".equalsIgnoreCase(rawType2)) {
                                contentValues7.put("data5", num2);
                            } else if ("QQ".equalsIgnoreCase(rawType2)) {
                                contentValues7.put("data5", (Integer) 4);
                            } else if ("GOOGLE-TALK".equalsIgnoreCase(rawType2)) {
                                contentValues7.put("data5", (Integer) 5);
                            } else if ("ICQ".equalsIgnoreCase(rawType2)) {
                                contentValues7.put("data5", (Integer) 6);
                            } else if ("JABBER".equalsIgnoreCase(rawType2)) {
                                contentValues7.put("data5", (Integer) 7);
                            } else if ("NETMEETING".equalsIgnoreCase(rawType2)) {
                                contentValues7.put("data5", (Integer) 8);
                            } else {
                                contentValues7.put("data5", (Integer) (-1));
                                contentValues7.put("data6", vcardItem2.getRawType(true));
                            }
                            if ("HOME".equalsIgnoreCase(rawType3)) {
                                contentValues7.put("data2", (Integer) 1);
                            } else if ("WORK".equalsIgnoreCase(rawType3)) {
                                contentValues7.put("data2", (Integer) 2);
                            } else if ("OTHER".equalsIgnoreCase(rawType3)) {
                                contentValues7.put("data2", num2);
                            }
                            arrayList.add(contentValues7);
                        } else {
                            str3 = str2;
                            str4 = str7;
                            if (i7 == 6 && !z) {
                                ContentValues contentValues8 = new ContentValues();
                                String str8 = str3;
                                contentValues8.put(str8, "vnd.android.cursor.item/organization");
                                dialogInterfaceOnClickListenerC46565 = this;
                                int i8 = i2;
                                while (i8 < PhonebookShareAlert.this.other.size()) {
                                    AndroidUtilities.VcardItem vcardItem3 = (AndroidUtilities.VcardItem) PhonebookShareAlert.this.other.get(i8);
                                    Integer num3 = num2;
                                    if (vcardItem3.type != 6) {
                                        str5 = str8;
                                    } else {
                                        String rawType4 = vcardItem3.getRawType(true);
                                        if ("ORG".equalsIgnoreCase(rawType4)) {
                                            String[] rawValue2 = vcardItem3.getRawValue();
                                            if (rawValue2.length != 0) {
                                                str5 = str8;
                                                if (rawValue2.length >= 1) {
                                                    contentValues8.put(str4, rawValue2[0]);
                                                }
                                                if (rawValue2.length >= 2) {
                                                    contentValues8.put("data5", rawValue2[1]);
                                                }
                                                z3 = true;
                                            }
                                            str5 = str8;
                                        } else {
                                            str5 = str8;
                                            if ("TITLE".equalsIgnoreCase(rawType4) || "ROLE".equalsIgnoreCase(rawType4)) {
                                                contentValues8.put("data4", vcardItem3.getValue(false));
                                            }
                                            z3 = true;
                                        }
                                        String rawType5 = vcardItem3.getRawType(z3);
                                        if ("WORK".equalsIgnoreCase(rawType5)) {
                                            contentValues8.put("data2", (Integer) 1);
                                        } else if ("OTHER".equalsIgnoreCase(rawType5)) {
                                            contentValues8.put("data2", (Integer) 2);
                                        }
                                    }
                                    i8++;
                                    num2 = num3;
                                    str8 = str5;
                                }
                                num = num2;
                                str3 = str8;
                                z2 = false;
                                arrayList.add(contentValues8);
                                z = true;
                            }
                        }
                        z2 = false;
                        dialogInterfaceOnClickListenerC46565 = this;
                        num = num2;
                    }
                    i6 = i2 + 1;
                    dialogInterfaceOnClickListenerC465652 = dialogInterfaceOnClickListenerC46565;
                    str = str4;
                    z4 = z2;
                    num2 = num;
                    z5 = z;
                    intent = intent2;
                    str2 = str3;
                    i3 = 3;
                    i4 = 1;
                }
                intent2 = intent;
                num = num2;
                z2 = z4;
                i2 = i6;
                z = z5;
                str4 = str;
                str3 = str2;
                dialogInterfaceOnClickListenerC46565 = dialogInterfaceOnClickListenerC465652;
                i6 = i2 + 1;
                dialogInterfaceOnClickListenerC465652 = dialogInterfaceOnClickListenerC46565;
                str = str4;
                z4 = z2;
                num2 = num;
                z5 = z;
                intent = intent2;
                str2 = str3;
                i3 = 3;
                i4 = 1;
            }
            DialogInterfaceOnClickListenerC46565 dialogInterfaceOnClickListenerC465653 = dialogInterfaceOnClickListenerC465652;
            intent.putExtra("finishActivityOnSaveCompleted", true);
            intent.putParcelableArrayListExtra("data", arrayList);
            try {
                PhonebookShareAlert.this.parentFragment.getParentActivity().startActivity(intent);
                PhonebookShareAlert.this.lambda$new$0();
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
    }

    public /* synthetic */ void lambda$new$4(boolean z, int i, int i2) {
        this.delegate.didSelectContact(this.currentUser, z, i, 0L, false, 0L);
        lambda$new$0();
    }

    public /* synthetic */ void lambda$new$5(Long l) {
        this.delegate.didSelectContact(this.currentUser, true, 0, 0L, false, l.longValue());
        lambda$new$0();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.PhonebookShareAlert$6 */
    public class C46576 implements Bulletin.Delegate {
        public C46576() {
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public int getBottomOffset(int i) {
            return AndroidUtilities.m1036dp(74.0f);
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BottomSheet, android.app.Dialog
    public void onStart() {
        super.onStart();
        Bulletin.addDelegate((FrameLayout) this.containerView, new Bulletin.Delegate() { // from class: org.telegram.ui.Components.PhonebookShareAlert.6
            public C46576() {
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public int getBottomOffset(int i) {
                return AndroidUtilities.m1036dp(74.0f);
            }
        });
    }

    @Override // android.app.Dialog
    public void onStop() {
        super.onStop();
        Bulletin.removeDelegate((FrameLayout) this.containerView);
    }

    public void setDelegate(ChatAttachAlertContactsLayout.PhonebookShareAlertDelegate phonebookShareAlertDelegate) {
        this.delegate = phonebookShareAlertDelegate;
    }

    public void updateLayout(boolean z) {
        char c2;
        View childAt = this.scrollView.getChildAt(0);
        int top = childAt.getTop() - this.scrollView.getScrollY();
        if (top < 0) {
            top = 0;
        }
        boolean z2 = top <= 0;
        if (!(z2 && this.actionBar.getTag() == null) && (z2 || this.actionBar.getTag() == null)) {
            c2 = 0;
        } else {
            this.actionBar.setTag(z2 ? num : null);
            AnimatorSet animatorSet = this.actionBarAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.actionBarAnimation = null;
            }
            if (z) {
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.actionBarAnimation = animatorSet2;
                animatorSet2.setDuration(180L);
                AnimatorSet animatorSet3 = this.actionBarAnimation;
                ActionBar actionBar = this.actionBar;
                Property property = View.ALPHA;
                c2 = 0;
                animatorSet3.playTogether(ObjectAnimator.ofFloat(actionBar, (Property<ActionBar, Float>) property, z2 ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.actionBarShadow, (Property<View, Float>) property, z2 ? 1.0f : 0.0f));
                this.actionBarAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.PhonebookShareAlert.7
                    public C46587() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        PhonebookShareAlert.this.actionBarAnimation = null;
                    }
                });
                this.actionBarAnimation.start();
            } else {
                c2 = 0;
                this.actionBar.setAlpha(z2 ? 1.0f : 0.0f);
                this.actionBarShadow.setAlpha(z2 ? 1.0f : 0.0f);
            }
        }
        if (this.scrollOffsetY != top) {
            this.scrollOffsetY = top;
            this.containerView.invalidate();
        }
        childAt.getBottom();
        this.scrollView.getMeasuredHeight();
        char c3 = childAt.getBottom() - this.scrollView.getScrollY() > this.scrollView.getMeasuredHeight() ? (char) 1 : c2;
        if ((c3 == 0 || this.shadow.getTag() != null) && (c3 != 0 || this.shadow.getTag() == null)) {
            return;
        }
        this.shadow.setTag(c3 == 0 ? null : 1);
        AnimatorSet animatorSet4 = this.shadowAnimation;
        if (animatorSet4 != null) {
            animatorSet4.cancel();
            this.shadowAnimation = null;
        }
        if (z) {
            AnimatorSet animatorSet5 = new AnimatorSet();
            this.shadowAnimation = animatorSet5;
            animatorSet5.setDuration(180L);
            AnimatorSet animatorSet6 = this.shadowAnimation;
            View view = this.shadow;
            Property property2 = View.ALPHA;
            float f = c3 != 0 ? 1.0f : 0.0f;
            float[] fArr = new float[1];
            fArr[c2] = f;
            Animator[] animatorArr = new Animator[1];
            animatorArr[c2] = ObjectAnimator.ofFloat(view, (Property<View, Float>) property2, fArr);
            animatorSet6.playTogether(animatorArr);
            this.shadowAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.PhonebookShareAlert.8
                public C46598() {
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    PhonebookShareAlert.this.shadowAnimation = null;
                }
            });
            this.shadowAnimation.start();
            return;
        }
        this.shadow.setAlpha(c3 != 0 ? 1.0f : 0.0f);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.PhonebookShareAlert$7 */
    public class C46587 extends AnimatorListenerAdapter {
        public C46587() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            PhonebookShareAlert.this.actionBarAnimation = null;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.PhonebookShareAlert$8 */
    public class C46598 extends AnimatorListenerAdapter {
        public C46598() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            PhonebookShareAlert.this.shadowAnimation = null;
        }
    }

    private void updateRows() {
        this.rowCount = 1;
        this.userRow = 0;
        if (this.phones.size() <= 1 && this.other.isEmpty()) {
            this.phoneStartRow = -1;
            this.phoneEndRow = -1;
            this.vcardStartRow = -1;
            this.vcardEndRow = -1;
            return;
        }
        if (this.phones.isEmpty()) {
            this.phoneStartRow = -1;
            this.phoneEndRow = -1;
        } else {
            int i = this.rowCount;
            this.phoneStartRow = i;
            int size = i + this.phones.size();
            this.rowCount = size;
            this.phoneEndRow = size;
        }
        if (this.other.isEmpty()) {
            this.vcardStartRow = -1;
            this.vcardEndRow = -1;
            return;
        }
        int i2 = this.rowCount;
        this.vcardStartRow = i2;
        int size2 = i2 + this.other.size();
        this.rowCount = size2;
        this.vcardEndRow = size2;
    }

    public class ListAdapter {
        public /* synthetic */ ListAdapter(PhonebookShareAlert phonebookShareAlert, PhonebookShareAlertIA phonebookShareAlertIA) {
            this();
        }

        private ListAdapter() {
        }

        public int getItemCount() {
            return PhonebookShareAlert.this.rowCount;
        }

        public void onBindViewHolder(View view, int i, int i2) {
            AndroidUtilities.VcardItem vcardItem;
            int i3;
            if (i2 == 1) {
                TextCheckBoxCell textCheckBoxCell = (TextCheckBoxCell) view;
                if (i >= PhonebookShareAlert.this.phoneStartRow && i < PhonebookShareAlert.this.phoneEndRow) {
                    vcardItem = (AndroidUtilities.VcardItem) PhonebookShareAlert.this.phones.get(i - PhonebookShareAlert.this.phoneStartRow);
                    i3 = C2797R.drawable.msg_calls;
                } else {
                    vcardItem = (AndroidUtilities.VcardItem) PhonebookShareAlert.this.other.get(i - PhonebookShareAlert.this.vcardStartRow);
                    int i4 = vcardItem.type;
                    if (i4 == 1) {
                        i3 = C2797R.drawable.msg_mention;
                    } else if (i4 == 2) {
                        i3 = C2797R.drawable.msg_location;
                    } else if (i4 == 3) {
                        i3 = C2797R.drawable.msg_link;
                    } else if (i4 == 4) {
                        i3 = C2797R.drawable.msg_info;
                    } else if (i4 == 5) {
                        i3 = C2797R.drawable.msg_calendar2;
                    } else if (i4 == 6) {
                        if ("ORG".equalsIgnoreCase(vcardItem.getRawType(true))) {
                            i3 = C2797R.drawable.msg_work;
                        } else {
                            i3 = C2797R.drawable.msg_jobtitle;
                        }
                    } else if (i4 == 20) {
                        i3 = C2797R.drawable.msg_info;
                    } else {
                        i3 = C2797R.drawable.msg_info;
                    }
                }
                textCheckBoxCell.setVCardItem(vcardItem, i3, i != getItemCount() - 1);
            }
        }

        public View createView(Context context, int i) {
            View userCell;
            int itemViewType = getItemViewType(i);
            if (itemViewType == 0) {
                userCell = PhonebookShareAlert.this.new UserCell(context);
            } else {
                userCell = PhonebookShareAlert.this.new TextCheckBoxCell(context);
            }
            onBindViewHolder(userCell, i, itemViewType);
            return userCell;
        }

        public int getItemViewType(int i) {
            return i == PhonebookShareAlert.this.userRow ? 0 : 1;
        }
    }
}
