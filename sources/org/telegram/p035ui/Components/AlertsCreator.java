package org.telegram.p035ui.Components;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.URLSpan;
import android.util.Base64;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.util.Consumer;
import com.android.p006dx.p009io.Opcodes;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.utils.system.VibratorUtils;
import com.google.android.material.timepicker.TimeModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import kotlin.time.DurationKt;
import okhttp3.internal.url._UrlKt;
import org.mvel2.asm.signature.SignatureVisitor;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AiTonesController$$ExternalSyntheticLambda0;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AppGlobalConfig;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.OneUIUtilities;
import org.telegram.messenger.RichMessageLayout;
import org.telegram.messenger.SecretChatHelper;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.SvgHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.pip.utils.PipUtils;
import org.telegram.p035ui.ActionBar.ActionBarMenuItem;
import org.telegram.p035ui.ActionBar.ActionBarPopupWindow;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.AlertDialogDecor;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.BottomSheet;
import org.telegram.p035ui.ActionBar.SimpleTextView;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Business.TimezonesController;
import org.telegram.p035ui.CacheControlActivity;
import org.telegram.p035ui.Cells.AccountSelectCell;
import org.telegram.p035ui.Cells.CheckBoxCell;
import org.telegram.p035ui.Cells.RadioColorCell;
import org.telegram.p035ui.Cells.TextColorCell;
import org.telegram.p035ui.ChatActivity;
import org.telegram.p035ui.Components.AlertsCreator;
import org.telegram.p035ui.Components.AnimatedTextView;
import org.telegram.p035ui.Components.Bulletin;
import org.telegram.p035ui.Components.Forum.ForumUtilities;
import org.telegram.p035ui.Components.LinkSpanDrawable;
import org.telegram.p035ui.Components.NumberPicker;
import org.telegram.p035ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.p035ui.Components.voip.VoIPHelper;
import org.telegram.p035ui.LanguageSelectActivity;
import org.telegram.p035ui.LaunchActivity;
import org.telegram.p035ui.NotificationsCustomSettingsActivity;
import org.telegram.p035ui.NotificationsSettingsActivity;
import org.telegram.p035ui.PhotoViewer;
import org.telegram.p035ui.PremiumFeatureCell;
import org.telegram.p035ui.PremiumPreviewFragment;
import org.telegram.p035ui.PrivacyControlActivity;
import org.telegram.p035ui.ProfileActivity;
import org.telegram.p035ui.Stars.StarGiftSheet;
import org.telegram.p035ui.Stars.StarsController;
import org.telegram.p035ui.Stars.StarsIntroActivity;
import org.telegram.p035ui.Stories.DarkThemeResourceProvider;
import org.telegram.p035ui.Stories.recorder.ButtonWithCounterView;
import org.telegram.p035ui.Stories.recorder.HintView2;
import org.telegram.p035ui.ThemePreviewActivity;
import org.telegram.p035ui.TooManyCommunitiesActivity;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_account;
import org.telegram.tgnet.p034tl.TL_phone;
import org.telegram.tgnet.p034tl.TL_stars;
import org.telegram.tgnet.p034tl.TL_update;
import p026j$.time.Instant;
import p026j$.time.LocalDate;
import p026j$.time.YearMonth;
import p026j$.time.ZoneId;
import p026j$.time.ZoneOffset;
import p026j$.time.temporal.ChronoUnit;

/* JADX INFO: loaded from: classes7.dex */
public abstract class AlertsCreator {
    private static final Pattern URL_PATTERN = Pattern.compile("^([a-zA-Z][a-zA-Z0-9+\\-.]*://)?([a-zA-Z0-9\\-]+\\.)+[a-zA-Z]{2,}(:\\d+)?(/[^\\s]*)?$");

    public interface AccountSelectDelegate {
        void didSelectAccount(int i);
    }

    public interface BlockDialogCallback {
        void run(boolean z, boolean z2);
    }

    public interface DatePickerDelegate {
        void didSelectDate(int i, int i2, int i3);
    }

    public interface FormattedDatePickerDelegate {
        void didSelectDate(int i, int i2);
    }

    public interface ScheduleDatePickerDelegate {
        void didSelectDate(boolean z, int i, int i2);
    }

    public interface SoundFrequencyDelegate {
        void didSelectValues(int i, int i2);
    }

    public interface StatusUntilDatePickerDelegate {
        void didSelectDate(int i);
    }

    public static /* synthetic */ void $r8$lambda$JiwJr3bR3ySTvE4ZdMp08xfUBy0() {
    }

    public static /* synthetic */ void $r8$lambda$Q870NArO2_5LHxEHbS7bVr1pnug(NumberPicker numberPicker, int i, int i2) {
    }

    public static /* synthetic */ void $r8$lambda$QyQAG5qAoSCkQua5TZfArwY9NCk(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    /* JADX INFO: renamed from: $r8$lambda$_YkZpB-Vxn8muhSiPb31kIo3wbk */
    public static /* synthetic */ void m9979$r8$lambda$_YkZpBVxn8muhSiPb31kIo3wbk(NumberPicker numberPicker, int i, int i2) {
    }

    public static /* synthetic */ void $r8$lambda$_r3RcvTconMV7D20v6ZBT5fPT8k(AlertDialog alertDialog, int i) {
    }

    public static /* synthetic */ void $r8$lambda$tp8ZIg7_hiaR_9puUNHqcuNKTA8(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    public static Dialog createForgotPasscodeDialog(Context context) {
        return new AlertDialog.Builder(context).setTitle(LocaleController.getString(C2797R.string.ForgotPasscode)).setMessage(LocaleController.getString(C2797R.string.ForgotPasscodeInfo)).setPositiveButton(LocaleController.getString(C2797R.string.Close), null).create();
    }

    public static Dialog createLocationRequiredDialog(final Context context, boolean z) {
        return new AlertDialog.Builder(context).setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.PermissionNoLocationFriends))).setTopAnimation(C2797R.raw.permission_request_location, 72, false, Theme.getColor(Theme.key_dialogTopBackground)).setPositiveButton(LocaleController.getString(C2797R.string.PermissionOpenSettings), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda166
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.m10007$r8$lambda$z9OV7Sej2fK9BMVVPq5XSk9LM(context, alertDialog, i);
            }
        }).setNegativeButton(LocaleController.getString(C2797R.string.ContactsPermissionAlertNotNow), null).create();
    }

    /* JADX INFO: renamed from: $r8$lambda$z9OV7Sej2fK9BMVVPq5X-Sk-9LM */
    public static /* synthetic */ void m10007$r8$lambda$z9OV7Sej2fK9BMVVPq5XSk9LM(Context context, AlertDialog alertDialog, int i) {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
            context.startActivity(intent);
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    public static Dialog createBackgroundActivityDialog(final Context context) {
        int i;
        AlertDialog.Builder title = new AlertDialog.Builder(context).setTitle(LocaleController.getString(C2797R.string.AllowBackgroundActivity));
        if (OneUIUtilities.isOneUI()) {
            i = Build.VERSION.SDK_INT >= 31 ? C2797R.string.AllowBackgroundActivityInfoOneUIAboveS : C2797R.string.AllowBackgroundActivityInfoOneUIBelowS;
        } else {
            i = C2797R.string.AllowBackgroundActivityInfo;
        }
        return title.setMessage(AndroidUtilities.replaceTags(LocaleController.getString(i))).setTopAnimation(C2797R.raw.permission_request_apk, 72, false, Theme.getColor(Theme.key_dialogTopBackground)).setPositiveButton(LocaleController.getString(C2797R.string.PermissionOpenSettings), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda7
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i2) {
                AlertsCreator.$r8$lambda$AvuHCR1k6V5fBzOuStuj5qfZeNs(context, alertDialog, i2);
            }
        }).setNegativeButton(LocaleController.getString(C2797R.string.ContactsPermissionAlertNotNow), null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                SharedConfig.BackgroundActivityPrefs.increaseDismissedCount();
            }
        }).create();
    }

    public static /* synthetic */ void $r8$lambda$AvuHCR1k6V5fBzOuStuj5qfZeNs(Context context, AlertDialog alertDialog, int i) {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
            context.startActivity(intent);
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    public static Dialog createWebViewPermissionsRequestDialog(final Context context, Theme.ResourcesProvider resourcesProvider, String[] strArr, int i, String str, String str2, final Consumer<Boolean> consumer) {
        final boolean z;
        if (strArr == null || !(context instanceof Activity)) {
            z = false;
        } else {
            Activity activity = (Activity) context;
            for (String str3 : strArr) {
                if (activity.checkSelfPermission(str3) != 0 && activity.shouldShowRequestPermissionRationale(str3)) {
                    z = true;
                    break;
                }
            }
            z = false;
        }
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        AlertDialog.Builder topAnimation = new AlertDialog.Builder(context, resourcesProvider).setTopAnimation(i, 72, false, Theme.getColor(Theme.key_dialogTopBackground));
        if (z) {
            str = str2;
        }
        return topAnimation.setMessage(AndroidUtilities.replaceTags(str)).setPositiveButton(LocaleController.getString(z ? C2797R.string.PermissionOpenSettings : C2797R.string.BotWebViewRequestAllow), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda161
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i2) {
                AlertsCreator.m10004$r8$lambda$tUfQ2PKxixHa2KrEl7AuAV6nLo(z, context, atomicBoolean, consumer, alertDialog, i2);
            }
        }).setNegativeButton(LocaleController.getString(C2797R.string.BotWebViewRequestDontAllow), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda162
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i2) {
                AlertsCreator.$r8$lambda$HEvbBaVkS3tygf2fstRGhrdMj8k(atomicBoolean, consumer, alertDialog, i2);
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda163
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AlertsCreator.$r8$lambda$e_UUPM06Fsq_qCQnM0W1euYrafk(atomicBoolean, consumer, dialogInterface);
            }
        }).create();
    }

    /* JADX INFO: renamed from: $r8$lambda$tUfQ2-PKxixHa2KrEl7AuAV6nLo */
    public static /* synthetic */ void m10004$r8$lambda$tUfQ2PKxixHa2KrEl7AuAV6nLo(boolean z, Context context, AtomicBoolean atomicBoolean, Consumer consumer, AlertDialog alertDialog, int i) {
        if (z) {
            try {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
                context.startActivity(intent);
                return;
            } catch (Exception e) {
                FileLog.m1048e(e);
                return;
            }
        }
        atomicBoolean.set(true);
        consumer.accept(Boolean.TRUE);
    }

    public static /* synthetic */ void $r8$lambda$HEvbBaVkS3tygf2fstRGhrdMj8k(AtomicBoolean atomicBoolean, Consumer consumer, AlertDialog alertDialog, int i) {
        atomicBoolean.set(true);
        consumer.accept(Boolean.FALSE);
    }

    public static /* synthetic */ void $r8$lambda$e_UUPM06Fsq_qCQnM0W1euYrafk(AtomicBoolean atomicBoolean, Consumer consumer, DialogInterface dialogInterface) {
        if (atomicBoolean.get()) {
            return;
        }
        consumer.accept(Boolean.FALSE);
    }

    public static Dialog createApkRestrictedDialog(final Context context, Theme.ResourcesProvider resourcesProvider) {
        return new AlertDialog.Builder(context, resourcesProvider).setMessage(LocaleController.getString(C2797R.string.ApkRestricted)).setTopAnimation(C2797R.raw.permission_request_apk, 72, false, Theme.getColor(Theme.key_dialogTopBackground)).setPositiveButton(LocaleController.getString(C2797R.string.PermissionOpenSettings), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.m9937$r8$lambda$423SrzgdSx4Htk3AIZLaUXHAbI(context, alertDialog, i);
            }
        }).setNegativeButton(LocaleController.getString(C2797R.string.ContactsPermissionAlertNotNow), null).create();
    }

    /* JADX INFO: renamed from: $r8$lambda$423SrzgdSx4Htk3AI-ZLaUXHAbI */
    public static /* synthetic */ void m9937$r8$lambda$423SrzgdSx4Htk3AIZLaUXHAbI(Context context, AlertDialog alertDialog, int i) {
        try {
            context.startActivity(new Intent("android.settings.MANAGE_UNKNOWN_APP_SOURCES", Uri.parse("package:" + context.getPackageName())));
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    public static /* synthetic */ void $r8$lambda$4f2RXDpSskM5bIoBqQs4mqEINPk(long j, int i, long j2) {
        Activity activity = AndroidUtilities.getActivity();
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        new StarsIntroActivity.StarsNeededSheet(activity, (PhotoViewer.getInstance().isVisible() || (safeLastFragment != null && safeLastFragment.hasShownSheet())) ? new DarkThemeResourceProvider() : safeLastFragment != null ? safeLastFragment.getResourceProvider() : null, j, 13, DialogObject.getShortName(i, j2), new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                AlertsCreator.$r8$lambda$JiwJr3bR3ySTvE4ZdMp08xfUBy0();
            }
        }, j2).show();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static Dialog processError(final int i, TLRPC.TL_error tL_error, BaseFragment baseFragment, TLObject tLObject, Object... objArr) {
        String str;
        TLRPC.InputPeer inputPeer;
        long peerDialogId;
        String restrictedErrorText;
        TLRPC.Chat chat;
        String firstName;
        BaseFragment lastFragment = baseFragment;
        if (tL_error != null && tL_error.code != 406 && (str = tL_error.text) != null) {
            if ("BALANCE_TOO_LOW".equalsIgnoreCase(str)) {
                final long allowedPaidStars = StarsController.getAllowedPaidStars(tLObject);
                final long peer = StarsController.getPeer(tLObject);
                if (allowedPaidStars > 0) {
                    StarsController.getInstance(i).getBalance(true, new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            AlertsCreator.$r8$lambda$4f2RXDpSskM5bIoBqQs4mqEINPk(allowedPaidStars, i, peer);
                        }
                    }, true);
                }
            } else if (tL_error.text.equals("JOIN_GUARD_TIMEOUT")) {
                showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.GuardBotTimeoutTitle), LocaleController.getString(C2797R.string.GuardBotTimeout));
            } else {
                boolean z = tLObject instanceof TLRPC.TL_messages_sendMessage;
                boolean zBooleanValue = false;
                if (z && tL_error.text.contains("PRIVACY_PREMIUM_REQUIRED")) {
                    long peerDialogId2 = DialogObject.getPeerDialogId(((TLRPC.TL_messages_sendMessage) tLObject).peer);
                    if (peerDialogId2 >= 0) {
                        firstName = UserObject.getFirstName(MessagesController.getInstance(i).getUser(Long.valueOf(peerDialogId2)));
                    } else {
                        TLRPC.Chat chat2 = MessagesController.getInstance(i).getChat(Long.valueOf(-peerDialogId2));
                        if (chat2 == null) {
                            firstName = _UrlKt.FRAGMENT_ENCODE_SET;
                        } else {
                            firstName = chat2.title;
                        }
                    }
                    if (lastFragment == null) {
                        lastFragment = LaunchActivity.getLastFragment();
                    }
                    showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.MessagePremiumErrorTitle), LocaleController.formatString(C2797R.string.MessagePremiumErrorMessage, firstName));
                    MessagesController.getInstance(i).invalidateUserPremiumBlocked(peerDialogId2, 0);
                } else {
                    boolean z2 = tLObject instanceof TLRPC.TL_messages_initHistoryImport;
                    if (z2 || (tLObject instanceof TLRPC.TL_messages_checkHistoryImportPeer) || (tLObject instanceof TLRPC.TL_messages_checkHistoryImport) || (tLObject instanceof TLRPC.TL_messages_startHistoryImport)) {
                        if (z2) {
                            inputPeer = ((TLRPC.TL_messages_initHistoryImport) tLObject).peer;
                        } else {
                            inputPeer = tLObject instanceof TLRPC.TL_messages_startHistoryImport ? ((TLRPC.TL_messages_startHistoryImport) tLObject).peer : null;
                        }
                        if (lastFragment == null) {
                            lastFragment = LaunchActivity.getLastFragment();
                        }
                        if (tL_error.text.contains("USER_IS_BLOCKED")) {
                            showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.ImportErrorTitle), LocaleController.getString(C2797R.string.ImportErrorUserBlocked));
                        } else if (tL_error.text.contains("USER_NOT_MUTUAL_CONTACT")) {
                            showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.ImportErrorTitle), LocaleController.getString(C2797R.string.ImportMutualError));
                        } else if (tL_error.text.contains("IMPORT_PEER_TYPE_INVALID")) {
                            if (inputPeer instanceof TLRPC.TL_inputPeerUser) {
                                showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.ImportErrorTitle), LocaleController.getString(C2797R.string.ImportErrorChatInvalidUser));
                            } else {
                                showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.ImportErrorTitle), LocaleController.getString(C2797R.string.ImportErrorChatInvalidGroup));
                            }
                        } else if (tL_error.text.contains("CHAT_ADMIN_REQUIRED")) {
                            showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.ImportErrorTitle), LocaleController.getString(C2797R.string.ImportErrorNotAdmin));
                        } else if (tL_error.text.startsWith("IMPORT_FORMAT")) {
                            showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.ImportErrorTitle), LocaleController.getString(C2797R.string.ImportErrorFileFormatInvalid));
                        } else if (tL_error.text.startsWith("PEER_ID_INVALID")) {
                            showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.ImportErrorTitle), LocaleController.getString(C2797R.string.ImportErrorPeerInvalid));
                        } else if (tL_error.text.contains("IMPORT_LANG_NOT_FOUND")) {
                            showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.ImportErrorTitle), LocaleController.getString(C2797R.string.ImportErrorFileLang));
                        } else if (tL_error.text.contains("IMPORT_UPLOAD_FAILED")) {
                            showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.ImportErrorTitle), LocaleController.getString(C2797R.string.ImportFailedToUpload));
                        } else if (tL_error.text.startsWith("FLOOD_WAIT")) {
                            showFloodWaitAlert(tL_error.text, lastFragment);
                        } else {
                            showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.ImportErrorTitle), LocaleController.getString(C2797R.string.ErrorOccurred) + "\n" + tL_error.text);
                        }
                    } else if ((tLObject instanceof TL_account.saveSecureValue) || (tLObject instanceof TL_account.getAuthorizationForm)) {
                        if (lastFragment == null) {
                            lastFragment = LaunchActivity.getLastFragment();
                        }
                        if (tL_error.text.contains("PHONE_NUMBER_INVALID")) {
                            showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.InvalidPhoneNumber));
                        } else if (tL_error.text.startsWith("FLOOD_WAIT")) {
                            showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.FloodWait));
                        } else if ("APP_VERSION_OUTDATED".equals(tL_error.text)) {
                            showUpdateAppAlert(lastFragment.getParentActivity(), LocaleController.getString(C2797R.string.UpdateAppAlert), true);
                        } else {
                            showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.ErrorOccurred) + "\n" + tL_error.text);
                        }
                    } else {
                        boolean z3 = tLObject instanceof TLRPC.TL_channels_joinChannel;
                        if (z3 || (tLObject instanceof TLRPC.TL_channels_editAdmin) || (tLObject instanceof TLRPC.TL_channels_inviteToChannel) || (tLObject instanceof TLRPC.TL_messages_addChatUser) || (tLObject instanceof TLRPC.TL_messages_startBot) || (tLObject instanceof TLRPC.TL_channels_editBanned) || (tLObject instanceof TLRPC.TL_messages_editChatDefaultBannedRights) || (tLObject instanceof TLRPC.TL_messages_editChatAdmin) || (tLObject instanceof TLRPC.TL_messages_migrateChat) || (tLObject instanceof TL_phone.inviteToGroupCall)) {
                            if (lastFragment != null && tL_error.text.equals("CHANNELS_TOO_MUCH")) {
                                if (lastFragment.getParentActivity() != null) {
                                    lastFragment.showDialog(new LimitReachedBottomSheet(lastFragment, lastFragment.getParentActivity(), 5, i, null));
                                } else if (z3 || (tLObject instanceof TLRPC.TL_channels_inviteToChannel)) {
                                    lastFragment.presentFragment(new TooManyCommunitiesActivity(0));
                                } else {
                                    lastFragment.presentFragment(new TooManyCommunitiesActivity(1));
                                }
                                return null;
                            }
                            if (lastFragment != null) {
                                if (objArr != null && objArr.length > 0) {
                                    zBooleanValue = ((Boolean) objArr[0]).booleanValue();
                                }
                                showAddUserAlert(tL_error, lastFragment, zBooleanValue, tLObject);
                            } else if (tL_error.text.equals("PEER_FLOOD")) {
                                NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.needShowAlert, 1);
                            }
                        } else if (tLObject instanceof TLRPC.TL_messages_createChat) {
                            if (lastFragment == null) {
                                lastFragment = LaunchActivity.getLastFragment();
                            }
                            BaseFragment baseFragment2 = lastFragment;
                            if (tL_error.text.equals("CHANNELS_TOO_MUCH")) {
                                if (baseFragment2.getParentActivity() != null) {
                                    baseFragment2.showDialog(new LimitReachedBottomSheet(baseFragment2, baseFragment2.getParentActivity(), 5, i, null));
                                } else {
                                    baseFragment2.presentFragment(new TooManyCommunitiesActivity(2));
                                }
                                return null;
                            }
                            if (tL_error.text.startsWith("FLOOD_WAIT")) {
                                showFloodWaitAlert(tL_error.text, baseFragment2);
                            } else {
                                showAddUserAlert(tL_error, baseFragment2, false, tLObject);
                            }
                        } else if (tLObject instanceof TLRPC.TL_channels_createChannel) {
                            if (lastFragment == null) {
                                lastFragment = LaunchActivity.getLastFragment();
                            }
                            BaseFragment baseFragment3 = lastFragment;
                            if (tL_error.text.equals("CHANNELS_TOO_MUCH")) {
                                if (baseFragment3.getParentActivity() != null) {
                                    baseFragment3.showDialog(new LimitReachedBottomSheet(baseFragment3, baseFragment3.getParentActivity(), 5, i, null));
                                } else {
                                    baseFragment3.presentFragment(new TooManyCommunitiesActivity(2));
                                }
                                return null;
                            }
                            if (tL_error.text.startsWith("FLOOD_WAIT")) {
                                showFloodWaitAlert(tL_error.text, baseFragment3);
                            } else {
                                showAddUserAlert(tL_error, baseFragment3, false, tLObject);
                            }
                        } else if (tLObject instanceof TLRPC.TL_messages_editMessage) {
                            if (!tL_error.text.equals("MESSAGE_NOT_MODIFIED")) {
                                if (lastFragment != null) {
                                    showSimpleAlert(lastFragment, LocaleController.getString(C2797R.string.EditMessageError));
                                } else {
                                    showSimpleToast(null, LocaleController.getString(C2797R.string.EditMessageError));
                                }
                            }
                        } else if (z || (tLObject instanceof TLRPC.TL_messages_sendMedia) || (tLObject instanceof TLRPC.TL_messages_sendInlineBotResult) || (tLObject instanceof TLRPC.TL_messages_forwardMessages) || (tLObject instanceof TLRPC.TL_messages_sendMultiMedia) || (tLObject instanceof TLRPC.TL_messages_sendScheduledMessages)) {
                            if (z) {
                                peerDialogId = DialogObject.getPeerDialogId(((TLRPC.TL_messages_sendMessage) tLObject).peer);
                            } else if (tLObject instanceof TLRPC.TL_messages_sendMedia) {
                                peerDialogId = DialogObject.getPeerDialogId(((TLRPC.TL_messages_sendMedia) tLObject).peer);
                            } else if (tLObject instanceof TLRPC.TL_messages_sendInlineBotResult) {
                                peerDialogId = DialogObject.getPeerDialogId(((TLRPC.TL_messages_sendInlineBotResult) tLObject).peer);
                            } else if (tLObject instanceof TLRPC.TL_messages_forwardMessages) {
                                peerDialogId = DialogObject.getPeerDialogId(((TLRPC.TL_messages_forwardMessages) tLObject).to_peer);
                            } else if (tLObject instanceof TLRPC.TL_messages_sendMultiMedia) {
                                peerDialogId = DialogObject.getPeerDialogId(((TLRPC.TL_messages_sendMultiMedia) tLObject).peer);
                            } else {
                                peerDialogId = tLObject instanceof TLRPC.TL_messages_sendScheduledMessages ? DialogObject.getPeerDialogId(((TLRPC.TL_messages_sendScheduledMessages) tLObject).peer) : 0L;
                            }
                            String str2 = tL_error.text;
                            if (str2 != null && str2.startsWith("CHAT_SEND_") && tL_error.text.endsWith("FORBIDDEN")) {
                                restrictedErrorText = tL_error.text;
                                chat = peerDialogId < 0 ? MessagesController.getInstance(i).getChat(Long.valueOf(-peerDialogId)) : null;
                                String str3 = tL_error.text;
                                str3.getClass();
                                switch (str3) {
                                    case "CHAT_SEND_VOICES_FORBIDDEN":
                                        restrictedErrorText = ChatObject.getRestrictedErrorText(chat, 20);
                                        break;
                                    case "CHAT_SEND_PLAIN_FORBIDDEN":
                                        restrictedErrorText = ChatObject.getRestrictedErrorText(chat, 22);
                                        break;
                                    case "CHAT_SEND_AUDIOS_FORBIDDEN":
                                        restrictedErrorText = ChatObject.getRestrictedErrorText(chat, 18);
                                        break;
                                    case "CHAT_SEND_POLL_FORBIDDEN":
                                        restrictedErrorText = ChatObject.getRestrictedErrorText(chat, 10);
                                        break;
                                    case "CHAT_SEND_DOCS_FORBIDDEN":
                                        restrictedErrorText = ChatObject.getRestrictedErrorText(chat, 19);
                                        break;
                                    case "CHAT_SEND_ROUNDVIDEOS_FORBIDDEN":
                                        restrictedErrorText = ChatObject.getRestrictedErrorText(chat, 21);
                                        break;
                                    case "CHAT_SEND_VIDEOS_FORBIDDEN":
                                        restrictedErrorText = ChatObject.getRestrictedErrorText(chat, 17);
                                        break;
                                    case "CHAT_SEND_GIFS_FORBIDDEN":
                                        restrictedErrorText = ChatObject.getRestrictedErrorText(chat, 23);
                                        break;
                                    case "CHAT_SEND_PHOTOS_FORBIDDEN":
                                        restrictedErrorText = ChatObject.getRestrictedErrorText(chat, 16);
                                        break;
                                    case "CHAT_SEND_STICKERS_FORBIDDEN":
                                        restrictedErrorText = ChatObject.getRestrictedErrorText(chat, 8);
                                        break;
                                }
                                NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.showBulletin, 1, restrictedErrorText);
                            } else {
                                String str4 = tL_error.text;
                                str4.getClass();
                                switch (str4.hashCode()) {
                                    case -1809401834:
                                        if (!str4.equals("USER_BANNED_IN_CHANNEL")) {
                                        }
                                        break;
                                    case -454039871:
                                        if (!str4.equals("PEER_FLOOD")) {
                                        }
                                        break;
                                    case 1169786080:
                                        if (!str4.equals("SCHEDULE_TOO_MUCH")) {
                                        }
                                        break;
                                    default:
                                        break;
                                }
                                /*  JADX ERROR: Method code generation error
                                    java.lang.NullPointerException: Switch insn not found in header
                                    	at java.base/java.util.Objects.requireNonNull(Objects.java:259)
                                    	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:246)
                                    	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:88)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:140)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                    	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:157)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:136)
                                    	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:157)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:136)
                                    	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:157)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:136)
                                    	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:157)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:136)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:140)
                                    	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:157)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:136)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:140)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:140)
                                    	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:157)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:136)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:305)
                                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:284)
                                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:412)
                                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:337)
                                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:303)
                                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                                    */
                                /*
                                    Method dump skipped, instruction units count: 2540
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.AlertsCreator.processError(int, org.telegram.tgnet.TLRPC$TL_error, org.telegram.ui.ActionBar.BaseFragment, org.telegram.tgnet.TLObject, java.lang.Object[]):android.app.Dialog");
                            }

                            public static Toast showSimpleToast(BaseFragment baseFragment, String str) {
                                Context parentActivity;
                                if (str == null) {
                                    return null;
                                }
                                if (baseFragment != null && baseFragment.getParentActivity() != null) {
                                    parentActivity = baseFragment.getParentActivity();
                                } else {
                                    parentActivity = ApplicationLoader.applicationContext;
                                }
                                Toast toastMakeText = Toast.makeText(parentActivity, str, 1);
                                try {
                                    toastMakeText.show();
                                    return toastMakeText;
                                } catch (Exception e) {
                                    FileLog.m1048e(e);
                                    return toastMakeText;
                                }
                            }

                            public static AlertDialog showUpdateAppAlert(final Context context, String str, boolean z) {
                                if (context == null || str == null) {
                                    return null;
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle(LocaleController.getString(C2797R.string.AppName));
                                builder.setMessage(str);
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.f1162OK), null);
                                if (z) {
                                    builder.setNegativeButton(LocaleController.getString(C2797R.string.UpdateApp), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda32
                                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                        public final void onClick(AlertDialog alertDialog, int i) {
                                            Browser.openUrl(context, BuildVars.RELEASES_URL);
                                        }
                                    });
                                }
                                return builder.show();
                            }

                            public static AlertDialog.Builder createLanguageAlert(final LaunchActivity launchActivity, final TLRPC.TL_langPackLanguage tL_langPackLanguage) {
                                String string;
                                int iIndexOf;
                                if (tL_langPackLanguage == null) {
                                    return null;
                                }
                                tL_langPackLanguage.lang_code = tL_langPackLanguage.lang_code.replace(SignatureVisitor.SUPER, '_').toLowerCase();
                                tL_langPackLanguage.plural_code = tL_langPackLanguage.plural_code.replace(SignatureVisitor.SUPER, '_').toLowerCase();
                                String str = tL_langPackLanguage.base_lang_code;
                                if (str != null) {
                                    tL_langPackLanguage.base_lang_code = str.replace(SignatureVisitor.SUPER, '_').toLowerCase();
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(launchActivity);
                                if (LocaleController.getInstance().getCurrentLocaleInfo().shortName.equals(tL_langPackLanguage.lang_code)) {
                                    builder.setTitle(LocaleController.getString(C2797R.string.Language));
                                    string = LocaleController.formatString("LanguageSame", C2797R.string.LanguageSame, tL_langPackLanguage.name);
                                    builder.setNegativeButton(LocaleController.getString(C2797R.string.f1162OK), null);
                                    builder.setNeutralButton(LocaleController.getString(C2797R.string.SETTINGS), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda133
                                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                        public final void onClick(AlertDialog alertDialog, int i) {
                                            launchActivity.lambda$runLinkRequest$101(new LanguageSelectActivity());
                                        }
                                    });
                                } else if (tL_langPackLanguage.strings_count == 0) {
                                    builder.setTitle(LocaleController.getString(C2797R.string.LanguageUnknownTitle));
                                    string = LocaleController.formatString("LanguageUnknownCustomAlert", C2797R.string.LanguageUnknownCustomAlert, tL_langPackLanguage.name);
                                    builder.setNegativeButton(LocaleController.getString(C2797R.string.f1162OK), null);
                                } else {
                                    builder.setTitle(LocaleController.getString(C2797R.string.LanguageTitle));
                                    boolean z = tL_langPackLanguage.official;
                                    String str2 = tL_langPackLanguage.name;
                                    if (z) {
                                        string = LocaleController.formatString("LanguageAlert", C2797R.string.LanguageAlert, str2, Integer.valueOf((int) Math.ceil((tL_langPackLanguage.translated_count / tL_langPackLanguage.strings_count) * 100.0f)));
                                    } else {
                                        string = LocaleController.formatString("LanguageCustomAlert", C2797R.string.LanguageCustomAlert, str2, Integer.valueOf((int) Math.ceil((tL_langPackLanguage.translated_count / tL_langPackLanguage.strings_count) * 100.0f)));
                                    }
                                    builder.setPositiveButton(LocaleController.getString(C2797R.string.Change), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda134
                                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                        public final void onClick(AlertDialog alertDialog, int i) {
                                            AlertsCreator.$r8$lambda$6ZqQMNFwAbwiSWlJWIeA75Lfd6A(tL_langPackLanguage, launchActivity, alertDialog, i);
                                        }
                                    });
                                    builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                }
                                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(AndroidUtilities.replaceTags(string));
                                int iIndexOf2 = TextUtils.indexOf((CharSequence) spannableStringBuilder, '[');
                                if (iIndexOf2 != -1) {
                                    int i = iIndexOf2 + 1;
                                    iIndexOf = TextUtils.indexOf((CharSequence) spannableStringBuilder, ']', i);
                                    if (iIndexOf != -1) {
                                        spannableStringBuilder.delete(iIndexOf, iIndexOf + 1);
                                        spannableStringBuilder.delete(iIndexOf2, i);
                                    }
                                } else {
                                    iIndexOf = -1;
                                }
                                if (iIndexOf2 != -1 && iIndexOf != -1) {
                                    spannableStringBuilder.setSpan(new URLSpanNoUnderline(tL_langPackLanguage.translations_url) { // from class: org.telegram.ui.Components.AlertsCreator.1
                                        final /* synthetic */ AlertDialog.Builder val$builder;

                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        public C37141(String str3, AlertDialog.Builder builder2) {
                                            super(str3);
                                            builder = builder2;
                                        }

                                        @Override // org.telegram.p035ui.Components.URLSpanNoUnderline, android.text.style.URLSpan, android.text.style.ClickableSpan
                                        public void onClick(View view) {
                                            builder.getDismissRunnable().run();
                                            super.onClick(view);
                                        }
                                    }, iIndexOf2, iIndexOf - 1, 33);
                                }
                                TextView textView = new TextView(launchActivity);
                                textView.setText(spannableStringBuilder);
                                textView.setTextSize(1, 16.0f);
                                textView.setLinkTextColor(Theme.getColor(Theme.key_dialogTextLink));
                                textView.setHighlightColor(Theme.getColor(Theme.key_dialogLinkSelection));
                                textView.setPadding(AndroidUtilities.m1036dp(23.0f), 0, AndroidUtilities.m1036dp(23.0f), 0);
                                textView.setMovementMethod(new AndroidUtilities.LinkMovementMethodMy());
                                textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                                builder2.setView(textView);
                                return builder2;
                            }

                            public static /* synthetic */ void $r8$lambda$6ZqQMNFwAbwiSWlJWIeA75Lfd6A(TLRPC.TL_langPackLanguage tL_langPackLanguage, LaunchActivity launchActivity, AlertDialog alertDialog, int i) {
                                String str;
                                boolean z = tL_langPackLanguage.official;
                                String str2 = tL_langPackLanguage.lang_code;
                                if (z) {
                                    str = "remote_" + str2;
                                } else {
                                    str = "unofficial_" + str2;
                                }
                                LocaleController.LocaleInfo languageFromDict = LocaleController.getInstance().getLanguageFromDict(str);
                                if (languageFromDict == null) {
                                    languageFromDict = new LocaleController.LocaleInfo();
                                    languageFromDict.name = tL_langPackLanguage.native_name;
                                    languageFromDict.nameEnglish = tL_langPackLanguage.name;
                                    languageFromDict.shortName = tL_langPackLanguage.lang_code;
                                    languageFromDict.baseLangCode = tL_langPackLanguage.base_lang_code;
                                    languageFromDict.pluralLangCode = tL_langPackLanguage.plural_code;
                                    languageFromDict.isRtl = tL_langPackLanguage.rtl;
                                    if (tL_langPackLanguage.official) {
                                        languageFromDict.pathToFile = "remote";
                                    } else {
                                        languageFromDict.pathToFile = "unofficial";
                                    }
                                }
                                LocaleController.getInstance().applyLanguage(languageFromDict, true, false, false, true, UserConfig.selectedAccount, null);
                                launchActivity.rebuildAllFragments(true);
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$1 */
                            public class C37141 extends URLSpanNoUnderline {
                                final /* synthetic */ AlertDialog.Builder val$builder;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C37141(String str3, AlertDialog.Builder builder2) {
                                    super(str3);
                                    builder = builder2;
                                }

                                @Override // org.telegram.p035ui.Components.URLSpanNoUnderline, android.text.style.URLSpan, android.text.style.ClickableSpan
                                public void onClick(View view) {
                                    builder.getDismissRunnable().run();
                                    super.onClick(view);
                                }
                            }

                            public static boolean checkSlowMode(Context context, int i, long j, boolean z) {
                                TLRPC.Chat chat;
                                if (!DialogObject.isChatDialog(j) || (chat = MessagesController.getInstance(i).getChat(Long.valueOf(-j))) == null || !chat.slowmode_enabled || ChatObject.hasAdminRights(chat)) {
                                    return false;
                                }
                                if (!z) {
                                    TLRPC.ChatFull chatFull = MessagesController.getInstance(i).getChatFull(chat.f1245id);
                                    if (chatFull == null) {
                                        chatFull = MessagesStorage.getInstance(i).loadChatInfo(chat.f1245id, ChatObject.isChannel(chat), new CountDownLatch(1), false, false);
                                    }
                                    if (chatFull != null && chatFull.slowmode_next_send_date >= ConnectionsManager.getInstance(i).getCurrentTime()) {
                                        z = true;
                                    }
                                }
                                if (!z) {
                                    return false;
                                }
                                createSimpleAlert(context, chat.title, LocaleController.getString(C2797R.string.SlowmodeSendError)).show();
                                return true;
                            }

                            public static AlertDialog.Builder createNoAccessAlert(Context context, String str, String str2, Theme.ResourcesProvider resourcesProvider) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle(str);
                                HashMap map = new HashMap();
                                int i = Theme.key_dialogTopBackground;
                                map.put("info1.**", Integer.valueOf(Theme.getColor(i, resourcesProvider)));
                                map.put("info2.**", Integer.valueOf(Theme.getColor(i, resourcesProvider)));
                                builder.setTopAnimation(C2797R.raw.not_available, 52, false, Theme.getColor(i, resourcesProvider), map);
                                builder.setTopAnimationIsNew(true);
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Close), null);
                                builder.setMessage(str2);
                                return builder;
                            }

                            public static AlertDialog.Builder createSimpleAlert(Context context, String str) {
                                return createSimpleAlert(context, null, str);
                            }

                            public static AlertDialog.Builder createSimpleAlert(Context context, String str, String str2) {
                                return createSimpleAlert(context, str, str2, null);
                            }

                            public static AlertDialog.Builder createSimpleAlert(Context context, String str, String str2, Theme.ResourcesProvider resourcesProvider) {
                                return createSimpleAlert(context, str, str2, null, null, resourcesProvider);
                            }

                            public static AlertDialog.Builder createSimpleAlert(Context context, String str, String str2, String str3, final Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
                                if (context == null || str2 == null) {
                                    return null;
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(context, resourcesProvider);
                                if (str == null) {
                                    str = LocaleController.getString(C2797R.string.AppName);
                                }
                                builder.setTitle(str);
                                builder.setMessage(str2);
                                if (str3 == null) {
                                    builder.setPositiveButton(LocaleController.getString(C2797R.string.f1162OK), null);
                                    return builder;
                                }
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                builder.setPositiveButton(str3, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda9
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        AlertsCreator.$r8$lambda$m_KswKeRAssHkiLhfDbnwtSAr5M(runnable, alertDialog, i);
                                    }
                                });
                                return builder;
                            }

                            public static /* synthetic */ void $r8$lambda$m_KswKeRAssHkiLhfDbnwtSAr5M(Runnable runnable, AlertDialog alertDialog, int i) {
                                alertDialog.dismiss();
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            public static void createStoriesAlbumEnterNameForCreate(Context context, BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider, MessagesStorage.StringCallback stringCallback) {
                                createStoriesAlbumEnterName(context, baseFragment, LocaleController.getString(C2797R.string.StoriesAlbumCreateNew), LocaleController.getString(C2797R.string.StoriesAlbumAddHint), LocaleController.getString(C2797R.string.StoriesAlbumTitleInputHint), null, LocaleController.getString(C2797R.string.Create), resourcesProvider, stringCallback);
                            }

                            public static void createStoriesAlbumEnterNameForRename(Context context, BaseFragment baseFragment, String str, Theme.ResourcesProvider resourcesProvider, MessagesStorage.StringCallback stringCallback) {
                                createStoriesAlbumEnterName(context, baseFragment, LocaleController.getString(C2797R.string.StoriesAlbumRename), LocaleController.getString(C2797R.string.StoriesAlbumRenameHint), LocaleController.getString(C2797R.string.StoriesAlbumTitleInputHint), str, LocaleController.getString(C2797R.string.Rename), resourcesProvider, stringCallback);
                            }

                            public static void createStoriesAlbumEnterName(Context context, final BaseFragment baseFragment, String str, String str2, String str3, String str4, String str5, Theme.ResourcesProvider resourcesProvider, final MessagesStorage.StringCallback stringCallback) {
                                final Activity activityFindActivity = AndroidUtilities.findActivity(context);
                                final View currentFocus = activityFindActivity != null ? activityFindActivity.getCurrentFocus() : null;
                                final AlertDialog[] alertDialogArr = new AlertDialog[1];
                                AlertDialog.Builder builder = new AlertDialog.Builder(context, resourcesProvider);
                                builder.setTitle(str == null ? LocaleController.getString(C2797R.string.AppName) : str);
                                builder.setMessage(str2);
                                final C37252 c37252 = new EditTextCaption(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.2
                                    AnimatedTextView.AnimatedTextDrawable limit;
                                    AnimatedColor limitColor = new AnimatedColor(this);
                                    private int limitCount;
                                    final /* synthetic */ Theme.ResourcesProvider val$resourcesProvider;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C37252(Context context2, Theme.ResourcesProvider resourcesProvider2, Theme.ResourcesProvider resourcesProvider22) {
                                        super(context2, resourcesProvider22);
                                        resourcesProvider = resourcesProvider22;
                                        this.limitColor = new AnimatedColor(this);
                                        AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
                                        this.limit = animatedTextDrawable;
                                        animatedTextDrawable.setAnimationProperties(0.2f, 0L, 160L, CubicBezierInterpolator.EASE_OUT_QUINT);
                                        this.limit.setTextSize(AndroidUtilities.m1036dp(15.33f));
                                        this.limit.setCallback(this);
                                        this.limit.setGravity(5);
                                    }

                                    @Override // android.widget.TextView, android.view.View
                                    public boolean verifyDrawable(Drawable drawable) {
                                        return drawable == this.limit || super.verifyDrawable(drawable);
                                    }

                                    @Override // org.telegram.p035ui.Components.EditTextEffects, android.widget.TextView
                                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                                        super.onTextChanged(charSequence, i, i2, i3);
                                        if (this.limit != null) {
                                            this.limitCount = 12 - charSequence.length();
                                            this.limit.cancelAnimation();
                                            AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = this.limit;
                                            int i4 = this.limitCount;
                                            String str6 = _UrlKt.FRAGMENT_ENCODE_SET;
                                            if (i4 <= 4) {
                                                str6 = _UrlKt.FRAGMENT_ENCODE_SET + this.limitCount;
                                            }
                                            animatedTextDrawable.setText(str6);
                                        }
                                    }

                                    @Override // android.view.View
                                    public void dispatchDraw(Canvas canvas) {
                                        super.dispatchDraw(canvas);
                                        this.limit.setTextColor(this.limitColor.set(Theme.getColor(this.limitCount < 0 ? Theme.key_text_RedRegular : Theme.key_dialogSearchHint, resourcesProvider)));
                                        this.limit.setBounds(getScrollX(), 0, getScrollX() + getWidth(), getHeight());
                                        this.limit.draw(canvas);
                                    }
                                };
                                c37252.lineYFix = true;
                                c37252.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda172
                                    @Override // android.widget.TextView.OnEditorActionListener
                                    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                                        return AlertsCreator.m9977$r8$lambda$XmmyoHn6lHAr_950xOv40giscI(c37252, stringCallback, alertDialogArr, currentFocus, textView, i, keyEvent);
                                    }
                                });
                                c37252.setTextSize(1, 18.0f);
                                c37252.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider22));
                                c37252.setHintColor(Theme.getColor(Theme.key_groupcreate_hintText, resourcesProvider22));
                                c37252.setHintText(str3);
                                c37252.setFocusable(true);
                                c37252.setInputType(147457);
                                c37252.setLineColors(Theme.getColor(Theme.key_windowBackgroundWhiteInputField, resourcesProvider22), Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated, resourcesProvider22), Theme.getColor(Theme.key_text_RedRegular, resourcesProvider22));
                                c37252.setImeOptions(6);
                                c37252.setBackgroundDrawable(null);
                                c37252.setPadding(0, AndroidUtilities.m1036dp(6.0f), 0, AndroidUtilities.m1036dp(6.0f));
                                c37252.setText(str4);
                                c37252.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.Components.AlertsCreator.3
                                    boolean ignoreTextChange;

                                    @Override // android.text.TextWatcher
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                                    }

                                    @Override // android.text.TextWatcher
                                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                                    }

                                    public C37343() {
                                    }

                                    @Override // android.text.TextWatcher
                                    public void afterTextChanged(Editable editable) {
                                        if (!this.ignoreTextChange && editable.length() > 12) {
                                            this.ignoreTextChange = true;
                                            editable.delete(12, editable.length());
                                            AndroidUtilities.shakeView(editTextCaption);
                                            try {
                                                editTextCaption.performHapticFeedback(3, 2);
                                            } catch (Exception unused) {
                                            }
                                            this.ignoreTextChange = false;
                                        }
                                    }
                                });
                                LinearLayout linearLayout = new LinearLayout(context2);
                                linearLayout.setOrientation(1);
                                linearLayout.addView(c37252, LayoutHelper.createLinear(-1, -2, 24.0f, 0.0f, 24.0f, 10.0f));
                                builder.makeCustomMaxHeight();
                                builder.setView(linearLayout);
                                builder.setWidth(AndroidUtilities.m1036dp(292.0f));
                                builder.setPositiveButton(str5, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda173
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        AlertsCreator.$r8$lambda$4X5evEaly5Z5noomoLqLYltJee8(c37252, stringCallback, alertDialog, i);
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda174
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        alertDialog.dismiss();
                                    }
                                });
                                alertDialogArr[0] = builder.create();
                                if (baseFragment != null) {
                                    AndroidUtilities.requestAdjustNothing(activityFindActivity, baseFragment.getClassGuid());
                                }
                                alertDialogArr[0].setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda175
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        AlertsCreator.m9944$r8$lambda$C14bQF25GcuJmKEUteH82ngXs(c37252, baseFragment, activityFindActivity, dialogInterface);
                                    }
                                });
                                alertDialogArr[0].setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda176
                                    @Override // android.content.DialogInterface.OnShowListener
                                    public final void onShow(DialogInterface dialogInterface) {
                                        AlertsCreator.m9947$r8$lambda$DSoEFF3CSrWwNeXzgxe3UFG4A0(c37252, dialogInterface);
                                    }
                                });
                                alertDialogArr[0].show();
                                alertDialogArr[0].setDismissDialogByButtons(false);
                                c37252.setSelection(c37252.getText().length());
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$2 */
                            public class C37252 extends EditTextCaption {
                                AnimatedTextView.AnimatedTextDrawable limit;
                                AnimatedColor limitColor = new AnimatedColor(this);
                                private int limitCount;
                                final /* synthetic */ Theme.ResourcesProvider val$resourcesProvider;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C37252(Context context2, Theme.ResourcesProvider resourcesProvider22, Theme.ResourcesProvider resourcesProvider222) {
                                    super(context2, resourcesProvider222);
                                    resourcesProvider = resourcesProvider222;
                                    this.limitColor = new AnimatedColor(this);
                                    AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
                                    this.limit = animatedTextDrawable;
                                    animatedTextDrawable.setAnimationProperties(0.2f, 0L, 160L, CubicBezierInterpolator.EASE_OUT_QUINT);
                                    this.limit.setTextSize(AndroidUtilities.m1036dp(15.33f));
                                    this.limit.setCallback(this);
                                    this.limit.setGravity(5);
                                }

                                @Override // android.widget.TextView, android.view.View
                                public boolean verifyDrawable(Drawable drawable) {
                                    return drawable == this.limit || super.verifyDrawable(drawable);
                                }

                                @Override // org.telegram.p035ui.Components.EditTextEffects, android.widget.TextView
                                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                                    super.onTextChanged(charSequence, i, i2, i3);
                                    if (this.limit != null) {
                                        this.limitCount = 12 - charSequence.length();
                                        this.limit.cancelAnimation();
                                        AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = this.limit;
                                        int i4 = this.limitCount;
                                        String str6 = _UrlKt.FRAGMENT_ENCODE_SET;
                                        if (i4 <= 4) {
                                            str6 = _UrlKt.FRAGMENT_ENCODE_SET + this.limitCount;
                                        }
                                        animatedTextDrawable.setText(str6);
                                    }
                                }

                                @Override // android.view.View
                                public void dispatchDraw(Canvas canvas) {
                                    super.dispatchDraw(canvas);
                                    this.limit.setTextColor(this.limitColor.set(Theme.getColor(this.limitCount < 0 ? Theme.key_text_RedRegular : Theme.key_dialogSearchHint, resourcesProvider)));
                                    this.limit.setBounds(getScrollX(), 0, getScrollX() + getWidth(), getHeight());
                                    this.limit.draw(canvas);
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$XmmyoHn6lHAr_950-xOv40giscI */
                            public static /* synthetic */ boolean m9977$r8$lambda$XmmyoHn6lHAr_950xOv40giscI(EditTextCaption editTextCaption, MessagesStorage.StringCallback stringCallback, AlertDialog[] alertDialogArr, View view, TextView textView, int i, KeyEvent keyEvent) {
                                if (i != 6) {
                                    return false;
                                }
                                String string = editTextCaption.getText().toString();
                                if (string.length() > 12) {
                                    AndroidUtilities.shakeView(editTextCaption);
                                    return true;
                                }
                                stringCallback.run(string);
                                AlertDialog alertDialog = alertDialogArr[0];
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                                if (view != null) {
                                    view.requestFocus();
                                }
                                return true;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$3 */
                            public class C37343 implements TextWatcher {
                                boolean ignoreTextChange;

                                @Override // android.text.TextWatcher
                                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                                }

                                @Override // android.text.TextWatcher
                                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                                }

                                public C37343() {
                                }

                                @Override // android.text.TextWatcher
                                public void afterTextChanged(Editable editable) {
                                    if (!this.ignoreTextChange && editable.length() > 12) {
                                        this.ignoreTextChange = true;
                                        editable.delete(12, editable.length());
                                        AndroidUtilities.shakeView(editTextCaption);
                                        try {
                                            editTextCaption.performHapticFeedback(3, 2);
                                        } catch (Exception unused) {
                                        }
                                        this.ignoreTextChange = false;
                                    }
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$4X5evEaly5Z5noomoLqLYltJee8(EditTextCaption editTextCaption, MessagesStorage.StringCallback stringCallback, AlertDialog alertDialog, int i) {
                                String strTrim = editTextCaption.getText().toString().trim();
                                if (strTrim.length() > 12 || strTrim.isEmpty()) {
                                    AndroidUtilities.shakeView(editTextCaption);
                                } else {
                                    stringCallback.run(strTrim);
                                    alertDialog.dismiss();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$C14bQF25GcuJm-KEUteH82ng-Xs */
                            public static /* synthetic */ void m9944$r8$lambda$C14bQF25GcuJmKEUteH82ngXs(EditTextCaption editTextCaption, BaseFragment baseFragment, Activity activity, DialogInterface dialogInterface) {
                                AndroidUtilities.hideKeyboard(editTextCaption);
                                if (baseFragment != null) {
                                    AndroidUtilities.requestAdjustResize(activity, baseFragment.getClassGuid());
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$DSoEFF3CSrWwNeXzgxe3UFG4A-0 */
                            public static /* synthetic */ void m9947$r8$lambda$DSoEFF3CSrWwNeXzgxe3UFG4A0(EditTextCaption editTextCaption, DialogInterface dialogInterface) {
                                editTextCaption.requestFocus();
                                AndroidUtilities.showKeyboard(editTextCaption);
                            }

                            public static void showAddLinkToPoll(Context context, Theme.ResourcesProvider resourcesProvider, String str, TLRPC.WebPage webPage, final Utilities.Callback<String> callback, final Runnable runnable) {
                                Activity activityFindActivity = AndroidUtilities.findActivity(context);
                                final View currentFocus = activityFindActivity != null ? activityFindActivity.getCurrentFocus() : null;
                                final AlertDialog[] alertDialogArr = new AlertDialog[1];
                                AlertDialog.Builder builder = new AlertDialog.Builder(context, resourcesProvider);
                                builder.setTitle(LocaleController.getString(C2797R.string.PollV2AddLinkTitle));
                                builder.setMessage(LocaleController.getString(C2797R.string.PollV2AddLinkMessage));
                                final C37454 c37454 = new EditTextBoldCursor(context) { // from class: org.telegram.ui.Components.AlertsCreator.4
                                    public C37454(Context context2) {
                                        super(context2);
                                    }

                                    @Override // com.exteragram.messenger.components.ReceiveContentEditText, android.widget.TextView, android.view.View
                                    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
                                        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
                                        editorInfo.imeOptions &= -1073741825;
                                        return inputConnectionOnCreateInputConnection;
                                    }
                                };
                                c37454.setTextSize(1, 16.0f);
                                int i = Theme.key_dialogTextBlack;
                                c37454.setTextColor(Theme.getColor(i, resourcesProvider));
                                c37454.setHintTextColor(Theme.getColor(Theme.key_groupcreate_hintText, resourcesProvider));
                                c37454.setHint(LocaleController.getString(C2797R.string.PollV2AddLinkUrlHint));
                                c37454.setInputType(17);
                                c37454.setImeOptions(6);
                                c37454.setMaxLines(10);
                                c37454.setSingleLine(false);
                                c37454.setPadding(AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(13.0f), AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(13.0f));
                                c37454.setCursorWidth(1.5f);
                                c37454.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4, resourcesProvider));
                                if (str != null) {
                                    c37454.setText(str);
                                    c37454.setSelection(str.length());
                                }
                                c37454.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda252
                                    @Override // android.widget.TextView.OnEditorActionListener
                                    public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                                        return AlertsCreator.$r8$lambda$ISGHVganXhbap0xqfzUQvvZf8iE(c37454, callback, alertDialogArr, currentFocus, textView, i2, keyEvent);
                                    }
                                });
                                GradientDrawable gradientDrawable = new GradientDrawable();
                                gradientDrawable.setCornerRadius(AndroidUtilities.m1036dp(22.0f));
                                gradientDrawable.setColor(Theme.multAlpha(Theme.getColor(i, resourcesProvider), 0.06f));
                                c37454.setBackground(gradientDrawable);
                                LinearLayout linearLayout = new LinearLayout(context2);
                                linearLayout.setOrientation(1);
                                linearLayout.addView(c37454, LayoutHelper.createLinear(-1, -2, 24.0f, 4.0f, 24.0f, 9.0f));
                                if (WebPagePreviewView.hasPreview(webPage)) {
                                    WebPagePreviewView webPagePreviewView = new WebPagePreviewView(context2, resourcesProvider, UserConfig.selectedAccount);
                                    webPagePreviewView.setWebPage(webPage);
                                    linearLayout.addView(webPagePreviewView, LayoutHelper.createLinear(-1, -2, 22.0f, 3.0f, 22.0f, 7.0f));
                                }
                                builder.makeCustomMaxHeight();
                                builder.setView(linearLayout);
                                builder.setWidth(Math.min(AndroidUtilities.m1036dp(320.0f), (AndroidUtilities.displaySize.x * 85) / 100));
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Done), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda253
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i2) {
                                        AlertsCreator.$r8$lambda$gG9xjEcjlYHYWcSNM7cus94BWsQ(c37454, callback, alertDialog, i2);
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda254
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i2) {
                                        alertDialog.dismiss();
                                    }
                                });
                                if (runnable != null) {
                                    builder.setNeutralButton(LocaleController.getString(C2797R.string.Delete), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda255
                                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                        public final void onClick(AlertDialog alertDialog, int i2) {
                                            AlertsCreator.m9967$r8$lambda$QBsRViuFHoT2tZXxJrB59PHg(runnable, alertDialog, i2);
                                        }
                                    });
                                }
                                AlertDialog alertDialogCreate = builder.create();
                                alertDialogArr[0] = alertDialogCreate;
                                alertDialogCreate.setDismissDialogByButtons(false);
                                alertDialogArr[0].setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda256
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        AndroidUtilities.hideKeyboard(c37454);
                                    }
                                });
                                alertDialogArr[0].setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda257
                                    @Override // android.content.DialogInterface.OnShowListener
                                    public final void onShow(DialogInterface dialogInterface) {
                                        AlertsCreator.$r8$lambda$ADkHyuSF8XrVGKedIjp7IUXCv8c(c37454, dialogInterface);
                                    }
                                });
                                alertDialogArr[0].show();
                                TextView textView = (TextView) alertDialogArr[0].getButton(-3);
                                if (textView != null) {
                                    textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$4 */
                            public class C37454 extends EditTextBoldCursor {
                                public C37454(Context context2) {
                                    super(context2);
                                }

                                @Override // com.exteragram.messenger.components.ReceiveContentEditText, android.widget.TextView, android.view.View
                                public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
                                    InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
                                    editorInfo.imeOptions &= -1073741825;
                                    return inputConnectionOnCreateInputConnection;
                                }
                            }

                            public static /* synthetic */ boolean $r8$lambda$ISGHVganXhbap0xqfzUQvvZf8iE(EditTextBoldCursor editTextBoldCursor, Utilities.Callback callback, AlertDialog[] alertDialogArr, View view, TextView textView, int i, KeyEvent keyEvent) {
                                if (i != 6) {
                                    return false;
                                }
                                String strTrim = editTextBoldCursor.getText().toString().trim();
                                if (!isValidUrl(strTrim)) {
                                    AndroidUtilities.shakeView(editTextBoldCursor);
                                    return true;
                                }
                                callback.run(strTrim);
                                AlertDialog alertDialog = alertDialogArr[0];
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                                if (view != null) {
                                    view.requestFocus();
                                }
                                return true;
                            }

                            public static /* synthetic */ void $r8$lambda$gG9xjEcjlYHYWcSNM7cus94BWsQ(EditTextBoldCursor editTextBoldCursor, Utilities.Callback callback, AlertDialog alertDialog, int i) {
                                String strTrim = editTextBoldCursor.getText().toString().trim();
                                if (!isValidUrl(strTrim)) {
                                    AndroidUtilities.shakeView(editTextBoldCursor);
                                } else {
                                    callback.run(strTrim);
                                    alertDialog.dismiss();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$QBsRViu-FHoT2t-ZXxJrB59-PHg */
                            public static /* synthetic */ void m9967$r8$lambda$QBsRViuFHoT2tZXxJrB59PHg(Runnable runnable, AlertDialog alertDialog, int i) {
                                runnable.run();
                                alertDialog.dismiss();
                            }

                            public static /* synthetic */ void $r8$lambda$ADkHyuSF8XrVGKedIjp7IUXCv8c(EditTextBoldCursor editTextBoldCursor, DialogInterface dialogInterface) {
                                editTextBoldCursor.requestFocus();
                                AndroidUtilities.showKeyboard(editTextBoldCursor);
                            }

                            public static void showAddBrowserException(Context context, Theme.ResourcesProvider resourcesProvider, boolean z, final Utilities.Callback<String> callback) {
                                int i;
                                int i2;
                                Activity activityFindActivity = AndroidUtilities.findActivity(context);
                                final View currentFocus = activityFindActivity != null ? activityFindActivity.getCurrentFocus() : null;
                                final AlertDialog[] alertDialogArr = new AlertDialog[1];
                                AlertDialog.Builder builder = new AlertDialog.Builder(context, resourcesProvider);
                                if (z) {
                                    i = C2797R.string.BrowserSettingsAddTitle;
                                } else {
                                    i = C2797R.string.BrowserSettingsAddTitleExternal;
                                }
                                builder.setTitle(LocaleController.getString(i));
                                if (z) {
                                    i2 = C2797R.string.BrowserSettingsAddText;
                                } else {
                                    i2 = C2797R.string.BrowserSettingsAddTextExternal;
                                }
                                builder.setMessage(LocaleController.getString(i2));
                                final EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(context);
                                editTextBoldCursor.setTextSize(1, 16.0f);
                                int i3 = Theme.key_dialogTextBlack;
                                editTextBoldCursor.setTextColor(Theme.getColor(i3, resourcesProvider));
                                editTextBoldCursor.setHintTextColor(Theme.getColor(Theme.key_groupcreate_hintText, resourcesProvider));
                                editTextBoldCursor.setHint(LocaleController.getString(C2797R.string.BrowserSettingsAddHint));
                                editTextBoldCursor.setInputType(17);
                                editTextBoldCursor.setImeOptions(6);
                                editTextBoldCursor.setSingleLine(true);
                                editTextBoldCursor.setFocusable(true);
                                editTextBoldCursor.setPadding(AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(13.0f), AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(13.0f));
                                editTextBoldCursor.setCursorWidth(1.5f);
                                editTextBoldCursor.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4, resourcesProvider));
                                GradientDrawable gradientDrawable = new GradientDrawable();
                                gradientDrawable.setCornerRadius(AndroidUtilities.m1036dp(22.0f));
                                gradientDrawable.setColor(Theme.multAlpha(Theme.getColor(i3, resourcesProvider), 0.06f));
                                editTextBoldCursor.setBackground(gradientDrawable);
                                final Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda115
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AlertsCreator.$r8$lambda$ZItObE1VZ1DDG7W_v3Fox3KBpr0(editTextBoldCursor, callback, alertDialogArr, currentFocus);
                                    }
                                };
                                editTextBoldCursor.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda116
                                    @Override // android.widget.TextView.OnEditorActionListener
                                    public final boolean onEditorAction(TextView textView, int i4, KeyEvent keyEvent) {
                                        return AlertsCreator.$r8$lambda$VRQrE8qeVBub6KxkCgxwZs0jPpo(runnable, textView, i4, keyEvent);
                                    }
                                });
                                LinearLayout linearLayout = new LinearLayout(context);
                                linearLayout.setOrientation(1);
                                linearLayout.addView(editTextBoldCursor, LayoutHelper.createLinear(-1, -2, 24.0f, 4.0f, 24.0f, 9.0f));
                                builder.makeCustomMaxHeight();
                                builder.setView(linearLayout);
                                builder.setWidth(Math.min(AndroidUtilities.m1036dp(320.0f), (AndroidUtilities.displaySize.x * 85) / 100));
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Done), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda117
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i4) {
                                        runnable.run();
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda118
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i4) {
                                        alertDialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialogCreate = builder.create();
                                alertDialogArr[0] = alertDialogCreate;
                                alertDialogCreate.setDismissDialogByButtons(false);
                                alertDialogArr[0].setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda119
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        AndroidUtilities.hideKeyboard(editTextBoldCursor);
                                    }
                                });
                                alertDialogArr[0].setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda120
                                    @Override // android.content.DialogInterface.OnShowListener
                                    public final void onShow(DialogInterface dialogInterface) {
                                        AlertsCreator.$r8$lambda$iT0Q1OqWEhHXGl71eDAXEy3kh3U(editTextBoldCursor, dialogInterface);
                                    }
                                });
                                alertDialogArr[0].show();
                            }

                            public static /* synthetic */ void $r8$lambda$ZItObE1VZ1DDG7W_v3Fox3KBpr0(EditTextBoldCursor editTextBoldCursor, Utilities.Callback callback, AlertDialog[] alertDialogArr, View view) {
                                String strTrim = editTextBoldCursor.getText().toString().trim();
                                Uri uri = Uri.parse(strTrim);
                                if (uri == null || uri.getHost() == null) {
                                    uri = Uri.parse("https://" + strTrim);
                                }
                                if (uri == null || uri.getHost() == null) {
                                    AndroidUtilities.shakeView(editTextBoldCursor);
                                    return;
                                }
                                String lowerCase = uri.getHost().toLowerCase();
                                if (lowerCase.startsWith("www.")) {
                                    lowerCase = lowerCase.substring(4);
                                }
                                callback.run(lowerCase);
                                AlertDialog alertDialog = alertDialogArr[0];
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                                if (view != null) {
                                    view.requestFocus();
                                }
                            }

                            public static /* synthetic */ boolean $r8$lambda$VRQrE8qeVBub6KxkCgxwZs0jPpo(Runnable runnable, TextView textView, int i, KeyEvent keyEvent) {
                                if (i != 6) {
                                    return false;
                                }
                                runnable.run();
                                return true;
                            }

                            public static /* synthetic */ void $r8$lambda$iT0Q1OqWEhHXGl71eDAXEy3kh3U(EditTextBoldCursor editTextBoldCursor, DialogInterface dialogInterface) {
                                editTextBoldCursor.requestFocus();
                                AndroidUtilities.showKeyboard(editTextBoldCursor);
                            }

                            private static boolean isValidUrl(String str) {
                                if (TextUtils.isEmpty(str)) {
                                    return false;
                                }
                                return URL_PATTERN.matcher(str.trim()).matches();
                            }

                            public static Dialog showSimpleAlert(BaseFragment baseFragment, String str) {
                                return showSimpleAlert(baseFragment, null, str);
                            }

                            public static Dialog showSimpleAlert(BaseFragment baseFragment, String str, String str2) {
                                return showSimpleAlert(baseFragment, str, str2, null);
                            }

                            public static Dialog showSimpleAlert(BaseFragment baseFragment, String str, String str2, Theme.ResourcesProvider resourcesProvider) {
                                if (baseFragment == null) {
                                    baseFragment = LaunchActivity.getSafeLastFragment();
                                }
                                if (str2 == null || baseFragment == null || baseFragment.getParentActivity() == null) {
                                    return null;
                                }
                                AlertDialog alertDialogCreate = createSimpleAlert(baseFragment.getParentActivity(), str, str2, resourcesProvider).create();
                                baseFragment.showDialog(alertDialogCreate);
                                return alertDialogCreate;
                            }

                            public static AlertDialog showSimpleConfirmAlert(BaseFragment baseFragment, String str, CharSequence charSequence, String str2, boolean z, final Runnable runnable) {
                                TextView textView;
                                AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity(), baseFragment.getResourceProvider());
                                builder.setTitle(str);
                                builder.setMessage(charSequence);
                                builder.setPositiveButton(str2, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda110
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        AlertsCreator.$r8$lambda$qSxmfXAeInUSKRaIsH_hJGi8SJ0(runnable, alertDialog, i);
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                AlertDialog alertDialogCreate = builder.create();
                                baseFragment.showDialog(alertDialogCreate);
                                if (z && (textView = (TextView) alertDialogCreate.getButton(-1)) != null) {
                                    textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                                }
                                return alertDialogCreate;
                            }

                            public static /* synthetic */ void $r8$lambda$qSxmfXAeInUSKRaIsH_hJGi8SJ0(Runnable runnable, AlertDialog alertDialog, int i) {
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            public static void showBlockReportSpamReplyAlert(final ChatActivity chatActivity, final MessageObject messageObject, long j, final Theme.ResourcesProvider resourcesProvider, final Runnable runnable) {
                                if (chatActivity == null || chatActivity.getParentActivity() == null || messageObject == null) {
                                    return;
                                }
                                final AccountInstance accountInstance = chatActivity.getAccountInstance();
                                TLRPC.User user = j > 0 ? accountInstance.getMessagesController().getUser(Long.valueOf(j)) : null;
                                final TLRPC.Chat chat = j < 0 ? accountInstance.getMessagesController().getChat(Long.valueOf(-j)) : null;
                                if (user == null && chat == null) {
                                    return;
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(chatActivity.getParentActivity(), resourcesProvider);
                                builder.setDimEnabled(runnable == null);
                                builder.setOnPreDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda111
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        AlertsCreator.$r8$lambda$phyXVKXNegTfHBDr9NIOcHL1MLY(runnable, dialogInterface);
                                    }
                                });
                                builder.setTitle(LocaleController.getString(C2797R.string.BlockUser));
                                if (user != null) {
                                    builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("BlockUserReplyAlert", C2797R.string.BlockUserReplyAlert, UserObject.getFirstName(user))));
                                } else {
                                    builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("BlockUserReplyAlert", C2797R.string.BlockUserReplyAlert, chat.title)));
                                }
                                LinearLayout linearLayout = new LinearLayout(chatActivity.getParentActivity());
                                linearLayout.setOrientation(1);
                                final CheckBoxCell[] checkBoxCellArr = {new CheckBoxCell(chatActivity.getParentActivity(), 1, resourcesProvider)};
                                checkBoxCellArr[0].setBackgroundDrawable(Theme.getSelectorDrawable(false));
                                checkBoxCellArr[0].setTag(0);
                                checkBoxCellArr[0].setText(LocaleController.getString(C2797R.string.DeleteReportSpam), _UrlKt.FRAGMENT_ENCODE_SET, true, false);
                                checkBoxCellArr[0].setPadding(LocaleController.isRTL ? AndroidUtilities.m1036dp(16.0f) : AndroidUtilities.m1036dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.m1036dp(8.0f) : AndroidUtilities.m1036dp(16.0f), 0);
                                linearLayout.addView(checkBoxCellArr[0], LayoutHelper.createLinear(-1, -2));
                                checkBoxCellArr[0].setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda112
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        checkBoxCellArr[((Integer) view.getTag()).intValue()].setChecked(!r0[view.intValue()].isChecked(), true);
                                    }
                                });
                                builder.setView(linearLayout);
                                final TLRPC.User user2 = user;
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.BlockAndDeleteReplies), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda113
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        AlertsCreator.$r8$lambda$SWakS6uOn0KYDUNvXc_b71eisps(user2, accountInstance, chatActivity, chat, messageObject, checkBoxCellArr, resourcesProvider, alertDialog, i);
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                AlertDialog alertDialogCreate = builder.create();
                                chatActivity.showDialog(alertDialogCreate);
                                TextView textView = (TextView) alertDialogCreate.getButton(-1);
                                if (textView != null) {
                                    textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$phyXVKXNegTfHBDr9NIOcHL1MLY(Runnable runnable, DialogInterface dialogInterface) {
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$SWakS6uOn0KYDUNvXc_b71eisps(TLRPC.User user, final AccountInstance accountInstance, ChatActivity chatActivity, TLRPC.Chat chat, MessageObject messageObject, CheckBoxCell[] checkBoxCellArr, Theme.ResourcesProvider resourcesProvider, AlertDialog alertDialog, int i) {
                                UndoView undoView;
                                if (user != null) {
                                    accountInstance.getMessagesStorage().deleteUserChatHistory(chatActivity.getDialogId(), user.f1407id);
                                } else {
                                    accountInstance.getMessagesStorage().deleteUserChatHistory(chatActivity.getDialogId(), -chat.f1245id);
                                }
                                TLRPC.TL_contacts_blockFromReplies tL_contacts_blockFromReplies = new TLRPC.TL_contacts_blockFromReplies();
                                tL_contacts_blockFromReplies.msg_id = messageObject.getId();
                                tL_contacts_blockFromReplies.delete_message = true;
                                tL_contacts_blockFromReplies.delete_history = true;
                                if (checkBoxCellArr[0].isChecked()) {
                                    tL_contacts_blockFromReplies.report_spam = true;
                                    if (chatActivity.getParentActivity() != null && (undoView = chatActivity.getUndoView()) != null) {
                                        undoView.showWithAction(0L, 74, (Runnable) null);
                                    }
                                }
                                accountInstance.getConnectionsManager().sendRequest(tL_contacts_blockFromReplies, new RequestDelegate() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda169
                                    @Override // org.telegram.tgnet.RequestDelegate
                                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                                        AlertsCreator.$r8$lambda$adSke5LmPOJ4vPxxmkIFBSyqMrA(accountInstance, tLObject, tL_error);
                                    }
                                });
                            }

                            public static /* synthetic */ void $r8$lambda$adSke5LmPOJ4vPxxmkIFBSyqMrA(AccountInstance accountInstance, TLObject tLObject, TLRPC.TL_error tL_error) {
                                if (tLObject instanceof TLRPC.Updates) {
                                    accountInstance.getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
                                }
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX WARN: Removed duplicated region for block: B:104:0x0149  */
                            /* JADX WARN: Removed duplicated region for block: B:114:0x01ac  */
                            /* JADX WARN: Removed duplicated region for block: B:120:? A[RETURN, SYNTHETIC] */
                            /* JADX WARN: Removed duplicated region for block: B:77:0x0049  */
                            /* JADX WARN: Removed duplicated region for block: B:97:0x011c  */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public static void showBlockReportSpamAlert(org.telegram.p035ui.ActionBar.BaseFragment r18, long r19, final org.telegram.tgnet.TLRPC.User r21, final org.telegram.tgnet.TLRPC.Chat r22, final org.telegram.tgnet.TLRPC.EncryptedChat r23, final boolean r24, org.telegram.tgnet.TLRPC.ChatFull r25, final org.telegram.messenger.MessagesStorage.IntCallback r26, org.telegram.ui.ActionBar.Theme.ResourcesProvider r27) {
                                /*
                                    Method dump skipped, instruction units count: 438
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.AlertsCreator.showBlockReportSpamAlert(org.telegram.ui.ActionBar.BaseFragment, long, org.telegram.tgnet.TLRPC$User, org.telegram.tgnet.TLRPC$Chat, org.telegram.tgnet.TLRPC$EncryptedChat, boolean, org.telegram.tgnet.TLRPC$ChatFull, org.telegram.messenger.MessagesStorage$IntCallback, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
                            }

                            public static /* synthetic */ void $r8$lambda$cipNYM3EBFJoQqyqmgk4ylKane4(TLRPC.User user, AccountInstance accountInstance, CheckBoxCell[] checkBoxCellArr, long j, TLRPC.Chat chat, TLRPC.EncryptedChat encryptedChat, boolean z, MessagesStorage.IntCallback intCallback, AlertDialog alertDialog, int i) {
                                CheckBoxCell checkBoxCell;
                                if (user != null) {
                                    accountInstance.getMessagesController().blockPeer(user.f1407id);
                                }
                                if (checkBoxCellArr == null || ((checkBoxCell = checkBoxCellArr[0]) != null && checkBoxCell.isChecked())) {
                                    accountInstance.getMessagesController().reportSpam(j, user, chat, encryptedChat, chat != null && z);
                                }
                                if (checkBoxCellArr == null || checkBoxCellArr[1].isChecked()) {
                                    if (chat == null || ChatObject.isNotInChat(chat)) {
                                        accountInstance.getMessagesController().deleteDialog(j, 0);
                                    } else {
                                        accountInstance.getMessagesController().deleteParticipantFromChat(-j, accountInstance.getMessagesController().getUser(Long.valueOf(accountInstance.getUserConfig().getClientUserId())));
                                    }
                                    intCallback.run(1);
                                    return;
                                }
                                intCallback.run(0);
                            }

                            public static void showCustomNotificationsDialog(BaseFragment baseFragment, long j, int i, int i2, ArrayList<NotificationsSettingsActivity.NotificationException> arrayList, ArrayList<NotificationsSettingsActivity.NotificationException> arrayList2, int i3, MessagesStorage.IntCallback intCallback) {
                                showCustomNotificationsDialog(baseFragment, j, i, i2, arrayList, arrayList2, i3, intCallback, null);
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX WARN: Type inference failed for: r0v11 */
                            /* JADX WARN: Type inference failed for: r0v12 */
                            /* JADX WARN: Type inference failed for: r0v6, types: [android.view.ViewGroup] */
                            /* JADX WARN: Type inference failed for: r12v0, types: [org.telegram.ui.ActionBar.AlertDialog$Builder] */
                            /* JADX WARN: Type inference failed for: r13v0 */
                            /* JADX WARN: Type inference failed for: r13v1, types: [boolean, int] */
                            /* JADX WARN: Type inference failed for: r13v2, types: [android.view.View] */
                            /* JADX WARN: Type inference failed for: r13v3 */
                            /* JADX WARN: Type inference failed for: r13v4 */
                            /* JADX WARN: Type inference failed for: r13v5 */
                            /* JADX WARN: Type inference failed for: r13v6 */
                            /* JADX WARN: Type inference failed for: r14v0 */
                            /* JADX WARN: Type inference failed for: r14v1, types: [boolean, int] */
                            /* JADX WARN: Type inference failed for: r14v3 */
                            /* JADX WARN: Type inference failed for: r5v8, types: [android.view.View, android.widget.TextView] */
                            /* JADX WARN: Type inference failed for: r6v22 */
                            public static void showCustomNotificationsDialog(final BaseFragment baseFragment, long j, final int i, final int i2, final ArrayList<NotificationsSettingsActivity.NotificationException> arrayList, final ArrayList<NotificationsSettingsActivity.NotificationException> arrayList2, final int i3, final MessagesStorage.IntCallback intCallback, final MessagesStorage.IntCallback intCallback2) {
                                int i4;
                                ?? r13;
                                final long j2 = j;
                                if (baseFragment == null || baseFragment.getParentActivity() == null) {
                                    return;
                                }
                                ?? r132 = 0;
                                final boolean zIsGlobalNotificationsEnabled = NotificationsController.getInstance(i3).isGlobalNotificationsEnabled(j2, false, false);
                                ?? r14 = 1;
                                String[] strArr = {LocaleController.getString(C2797R.string.NotificationsTurnOn), LocaleController.formatString("MuteFor", C2797R.string.MuteFor, LocaleController.formatPluralString("Hours", 1, new Object[0])), LocaleController.formatString("MuteFor", C2797R.string.MuteFor, LocaleController.formatPluralString("Days", 2, new Object[0])), (j2 == 0 && (baseFragment instanceof NotificationsCustomSettingsActivity)) ? null : LocaleController.getString(C2797R.string.NotificationsCustomize), LocaleController.getString(C2797R.string.NotificationsTurnOff)};
                                int[] iArr = {C2797R.drawable.notifications_on, C2797R.drawable.notifications_mute1h, C2797R.drawable.notifications_mute2d, C2797R.drawable.notifications_settings, C2797R.drawable.notifications_off};
                                LinearLayout linearLayout = new LinearLayout(baseFragment.getParentActivity());
                                linearLayout.setOrientation(1);
                                final ?? builder = new AlertDialog.Builder(baseFragment.getParentActivity());
                                int i5 = 0;
                                ?? r0 = linearLayout;
                                while (i5 < 5) {
                                    if (strArr[i5] == null) {
                                        r13 = r0;
                                        i4 = i5;
                                    } else {
                                        ?? textView = new TextView(baseFragment.getParentActivity());
                                        Drawable drawable = baseFragment.getParentActivity().getResources().getDrawable(iArr[i5]);
                                        if (i5 == 4) {
                                            textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                                            drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_text_RedRegular), PorterDuff.Mode.MULTIPLY));
                                        } else {
                                            textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                                            drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogIcon), PorterDuff.Mode.MULTIPLY));
                                        }
                                        textView.setTextSize(r14, 16.0f);
                                        textView.setLines(r14);
                                        textView.setMaxLines(r14);
                                        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                                        textView.setTag(Integer.valueOf(i5));
                                        textView.setBackgroundDrawable(Theme.getSelectorDrawable(r132));
                                        textView.setPadding(AndroidUtilities.m1036dp(24.0f), r132, AndroidUtilities.m1036dp(24.0f), r132);
                                        textView.setSingleLine(r14);
                                        textView.setGravity(19);
                                        textView.setCompoundDrawablePadding(AndroidUtilities.m1036dp(26.0f));
                                        textView.setText(strArr[i5]);
                                        r0.addView(textView, LayoutHelper.createLinear(-1, 48, 51));
                                        i4 = i5;
                                        r13 = r0;
                                        textView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda135
                                            @Override // android.view.View.OnClickListener
                                            public final void onClick(View view) {
                                                AlertsCreator.$r8$lambda$oA_EylbAnf55g8odrWuoCVynqRg(j2, i3, zIsGlobalNotificationsEnabled, i, intCallback2, i2, baseFragment, arrayList, arrayList2, intCallback, builder, view);
                                            }
                                        });
                                    }
                                    i5 = i4 + 1;
                                    j2 = j;
                                    r0 = r13;
                                    r132 = 0;
                                    r14 = 1;
                                }
                                builder.setTitle(LocaleController.getString(C2797R.string.Notifications));
                                builder.setView(r0);
                                baseFragment.showDialog(builder.create());
                            }

                            /* JADX WARN: Removed duplicated region for block: B:101:0x00e8  */
                            /* JADX WARN: Removed duplicated region for block: B:103:0x00ed  */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public static /* synthetic */ void $r8$lambda$oA_EylbAnf55g8odrWuoCVynqRg(long r20, int r22, boolean r23, int r24, org.telegram.messenger.MessagesStorage.IntCallback r25, int r26, org.telegram.p035ui.ActionBar.BaseFragment r27, java.util.ArrayList r28, java.util.ArrayList r29, org.telegram.messenger.MessagesStorage.IntCallback r30, org.telegram.ui.ActionBar.AlertDialog.Builder r31, android.view.View r32) {
                                /*
                                    Method dump skipped, instruction units count: 290
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.AlertsCreator.$r8$lambda$oA_EylbAnf55g8odrWuoCVynqRg(long, int, boolean, int, org.telegram.messenger.MessagesStorage$IntCallback, int, org.telegram.ui.ActionBar.BaseFragment, java.util.ArrayList, java.util.ArrayList, org.telegram.messenger.MessagesStorage$IntCallback, org.telegram.ui.ActionBar.AlertDialog$Builder, android.view.View):void");
                            }

                            public static AlertDialog showSecretLocationAlert(Context context, int i, final Runnable runnable, boolean z, Theme.ResourcesProvider resourcesProvider) {
                                ArrayList arrayList = new ArrayList();
                                final ArrayList arrayList2 = new ArrayList();
                                int i2 = MessagesController.getInstance(i).availableMapProviders;
                                if ((i2 & 1) != 0) {
                                    arrayList.add(LocaleController.getString(C2797R.string.MapPreviewProviderTelegram));
                                    arrayList2.add(0);
                                }
                                if ((i2 & 2) != 0) {
                                    arrayList.add(LocaleController.getString(C2797R.string.MapPreviewProviderGoogle));
                                    arrayList2.add(1);
                                }
                                if ((i2 & 4) != 0) {
                                    arrayList.add(LocaleController.getString(C2797R.string.MapPreviewProviderYandex));
                                    arrayList2.add(3);
                                }
                                arrayList.add(LocaleController.getString(C2797R.string.MapPreviewProviderNobody));
                                arrayList2.add(2);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(context, resourcesProvider);
                                builder.setTitle(LocaleController.getString(C2797R.string.MapPreviewProviderTitle));
                                LinearLayout linearLayout = new LinearLayout(context);
                                linearLayout.setOrientation(1);
                                builder.setView(linearLayout);
                                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                                    RadioColorCell radioColorCell = new RadioColorCell(context, resourcesProvider);
                                    radioColorCell.setPadding(AndroidUtilities.m1036dp(4.0f), 0, AndroidUtilities.m1036dp(4.0f), 0);
                                    radioColorCell.setTag(Integer.valueOf(i3));
                                    radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
                                    radioColorCell.setTextAndValue((CharSequence) arrayList.get(i3), SharedConfig.mapPreviewType == ((Integer) arrayList2.get(i3)).intValue());
                                    radioColorCell.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_listSelector), 2));
                                    linearLayout.addView(radioColorCell);
                                    radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda75
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            AlertsCreator.$r8$lambda$uneY0pemtkVUHFqrIHGfPdotySk(arrayList2, runnable, builder, view);
                                        }
                                    });
                                }
                                if (!z) {
                                    builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                }
                                AlertDialog alertDialogShow = builder.show();
                                if (z) {
                                    alertDialogShow.setCanceledOnTouchOutside(false);
                                }
                                return alertDialogShow;
                            }

                            public static /* synthetic */ void $r8$lambda$uneY0pemtkVUHFqrIHGfPdotySk(ArrayList arrayList, Runnable runnable, AlertDialog.Builder builder, View view) {
                                SharedConfig.setSecretMapPreviewType(((Integer) arrayList.get(((Integer) view.getTag()).intValue())).intValue());
                                if (runnable != null) {
                                    runnable.run();
                                }
                                builder.getDismissRunnable().run();
                            }

                            public static void updateDayPicker(NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(2, numberPicker2.getValue());
                                calendar.set(1, numberPicker3.getValue());
                                numberPicker.setMinValue(1);
                                numberPicker.setMaxValue(calendar.getActualMaximum(5));
                            }

                            private static void checkPickerDate(NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                int i = 1;
                                int i2 = calendar.get(1);
                                int i3 = calendar.get(2);
                                int i4 = calendar.get(5);
                                numberPicker3.setMinValue(i2);
                                int value = numberPicker3.getValue();
                                numberPicker2.setMinValue(value == i2 ? i3 : 0);
                                int value2 = numberPicker2.getValue();
                                if (value == i2 && value2 == i3) {
                                    i = i4;
                                }
                                numberPicker.setMinValue(i);
                            }

                            public static void showOpenUrlAlert(BaseFragment baseFragment, String str, boolean z, boolean z2) {
                                showOpenUrlAlert(baseFragment, str, z, true, z2, false, null, null);
                            }

                            public static void showOpenUrlAlert(BaseFragment baseFragment, String str, boolean z, boolean z2, boolean z3, Browser.Progress progress, Theme.ResourcesProvider resourcesProvider) {
                                showOpenUrlAlert(baseFragment, str, z, z2, z3, false, progress, resourcesProvider);
                            }

                            public static void showOpenUrlAlert(BaseFragment baseFragment, String str, boolean z, boolean z2, boolean z3, boolean z4, Browser.Progress progress, Theme.ResourcesProvider resourcesProvider) {
                                showOpenUrlAlert(baseFragment, str, z, z2, z3, z4, progress, (TLRPC.WebPage) null, resourcesProvider);
                            }

                            public static void showOpenUrlAlert(Context context, String str, boolean z, boolean z2, boolean z3, boolean z4, long j, Browser.Progress progress, Theme.ResourcesProvider resourcesProvider) {
                                showOpenUrlAlert(context, str, z, z2, z3, z4, j, progress, null, resourcesProvider);
                            }

                            public static void showOpenUrlAlert(BaseFragment baseFragment, String str, boolean z, boolean z2, boolean z3, boolean z4, Browser.Progress progress, TLRPC.WebPage webPage, Theme.ResourcesProvider resourcesProvider) {
                                if (baseFragment == null || baseFragment.getParentActivity() == null) {
                                    return;
                                }
                                showOpenUrlAlert(baseFragment, baseFragment.getParentActivity(), str, z, z2, z3, z4, baseFragment instanceof ChatActivity ? ((ChatActivity) baseFragment).getInlineReturn() : 0L, progress, webPage, resourcesProvider);
                            }

                            public static void showOpenUrlAlert(Context context, String str, boolean z, boolean z2, boolean z3, boolean z4, long j, Browser.Progress progress, TLRPC.WebPage webPage, Theme.ResourcesProvider resourcesProvider) {
                                showOpenUrlAlert(null, context, str, z, z2, z3, z4, j, progress, webPage, resourcesProvider);
                            }

                            private static void showOpenUrlAlert(final BaseFragment baseFragment, final Context context, final String str, boolean z, final boolean z2, boolean z3, boolean z4, final long j, final Browser.Progress progress, TLRPC.WebPage webPage, Theme.ResourcesProvider resourcesProvider) {
                                String strReplaceHostname;
                                if (AndroidUtilities.isContextSafe(context)) {
                                    String scheme = str == null ? null : Uri.parse(str).getScheme();
                                    if (Browser.isInternalUrl(str, null) || !z3 || "mailto".equalsIgnoreCase(scheme)) {
                                        Browser.openUrl(context, Uri.parse(str), j == 0, z2, z4 && checkInternalBotApp(str), progress, null, false, true, false);
                                        return;
                                    }
                                    if (z) {
                                        try {
                                            Uri uri = Uri.parse(str);
                                            strReplaceHostname = Browser.replaceHostname(uri, Browser.IDN_toUnicode(uri.getHost()), null);
                                        } catch (Exception e) {
                                            FileLog.m1048e(e);
                                            strReplaceHostname = str;
                                        }
                                    } else {
                                        strReplaceHostname = str;
                                    }
                                    final Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            Browser.openUrl(context, Uri.parse(str), j == 0, z2, progress);
                                        }
                                    };
                                    final AlertDialog[] alertDialogArr = new AlertDialog[1];
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context, resourcesProvider);
                                    builder.setTitle(LocaleController.getString(C2797R.string.OpenUrlTitle));
                                    TextView textView = new TextView(context);
                                    textView.setText(strReplaceHostname);
                                    textView.setTextSize(1, 14.0f);
                                    int i = Theme.key_dialogTextBlack;
                                    textView.setTextColor(Theme.getColor(i, resourcesProvider));
                                    textView.setGravity(17);
                                    textView.setMaxLines(5);
                                    textView.setEllipsize(TextUtils.TruncateAt.END);
                                    LinearLayout linearLayout = new LinearLayout(context);
                                    linearLayout.setOrientation(1);
                                    linearLayout.setGravity(17);
                                    linearLayout.setPadding(AndroidUtilities.m1036dp(14.0f), AndroidUtilities.m1036dp(12.0f), AndroidUtilities.m1036dp(14.0f), AndroidUtilities.m1036dp(10.0f));
                                    GradientDrawable gradientDrawable = new GradientDrawable();
                                    gradientDrawable.setCornerRadius(AndroidUtilities.m1036dp(22.0f));
                                    gradientDrawable.setColor(Theme.multAlpha(Theme.getColor(i, resourcesProvider), 0.06f));
                                    linearLayout.setBackground(gradientDrawable);
                                    ScaleStateListAnimator.apply(linearLayout, 0.025f, 1.2f);
                                    linearLayout.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda2
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            AlertsCreator.m9983$r8$lambda$b30e2bxlAqi6yIQjk5a9OzT_8(alertDialogArr, str, baseFragment, view);
                                        }
                                    });
                                    linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2));
                                    SimpleTextView simpleTextView = new SimpleTextView(context);
                                    simpleTextView.setText(LocaleController.getString(C2797R.string.TapToCopy));
                                    simpleTextView.setTextSize(12);
                                    simpleTextView.setTextColor(Theme.multAlpha(Theme.getColor(i, resourcesProvider), 0.45f));
                                    simpleTextView.setGravity(17);
                                    simpleTextView.setMaxLines(1);
                                    simpleTextView.setPadding(0, AndroidUtilities.m1036dp(6.0f), 0, 0);
                                    linearLayout.addView(simpleTextView, LayoutHelper.createLinear(-1, -2));
                                    LinearLayout linearLayout2 = new LinearLayout(context);
                                    linearLayout2.setOrientation(1);
                                    linearLayout2.addView(linearLayout, LayoutHelper.createLinear(-1, -2, 22.0f, 4.0f, 22.0f, 9.0f));
                                    if (WebPagePreviewView.hasPreview(webPage)) {
                                        WebPagePreviewView webPagePreviewView = new WebPagePreviewView(context, resourcesProvider, UserConfig.selectedAccount);
                                        webPagePreviewView.setWebPage(webPage);
                                        linearLayout2.addView(webPagePreviewView, LayoutHelper.createLinear(-1, -2, 22.0f, 3.0f, 22.0f, 7.0f));
                                    }
                                    builder.setView(linearLayout2);
                                    builder.setWidth(Math.min(AndroidUtilities.m1036dp(320.0f), (AndroidUtilities.displaySize.x * 85) / 100));
                                    builder.setPositiveButton(LocaleController.getString(C2797R.string.Open), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda3
                                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                        public final void onClick(AlertDialog alertDialog, int i2) {
                                            runnable.run();
                                        }
                                    });
                                    builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                    alertDialogArr[0] = builder.show();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$b30e2bxlAqi6-yIQ-jk5a9OzT_8 */
                            public static /* synthetic */ void m9983$r8$lambda$b30e2bxlAqi6yIQjk5a9OzT_8(AlertDialog[] alertDialogArr, String str, BaseFragment baseFragment, View view) {
                                AlertDialog alertDialog = alertDialogArr[0];
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                                if (AndroidUtilities.addToClipboard(str)) {
                                    BulletinFactory.m1143of(baseFragment).createCopyLinkBulletin().show();
                                }
                            }

                            public static void showOpenExternalBrowserAlert(Context context, Theme.ResourcesProvider resourcesProvider, String str, boolean z, boolean z2, final Utilities.Callback2<Boolean, Boolean> callback2) {
                                if (AndroidUtilities.isContextSafe(context)) {
                                    final AlertDialog[] alertDialogArr = new AlertDialog[1];
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context, resourcesProvider);
                                    builder.setTitle(LocaleController.getString(C2797R.string.OpenUrlTitle));
                                    TextView textView = new TextView(context);
                                    textView.setText(str);
                                    textView.setTextSize(1, 14.0f);
                                    int i = Theme.key_dialogTextBlack;
                                    textView.setTextColor(Theme.getColor(i, resourcesProvider));
                                    textView.setGravity(17);
                                    textView.setMaxLines(5);
                                    textView.setEllipsize(TextUtils.TruncateAt.END);
                                    textView.setPadding(AndroidUtilities.m1036dp(14.0f), AndroidUtilities.m1036dp(12.0f), AndroidUtilities.m1036dp(14.0f), AndroidUtilities.m1036dp(12.0f));
                                    GradientDrawable gradientDrawable = new GradientDrawable();
                                    gradientDrawable.setCornerRadius(AndroidUtilities.m1036dp(22.0f));
                                    gradientDrawable.setColor(Theme.multAlpha(Theme.getColor(i, resourcesProvider), 0.06f));
                                    textView.setBackground(gradientDrawable);
                                    final CheckBoxCell checkBoxCell = new CheckBoxCell(context, 1, resourcesProvider);
                                    checkBoxCell.setMultiline(true);
                                    checkBoxCell.getTextView().getLayoutParams().width = -1;
                                    checkBoxCell.getTextView().setSingleLine(false);
                                    checkBoxCell.getTextView().setMaxLines(3);
                                    checkBoxCell.getTextView().setTextSize(1, 16.0f);
                                    checkBoxCell.setText(LocaleController.getString(z ? C2797R.string.BrowserAlwaysOpenExternal : C2797R.string.BrowserAlwaysOpenInApp), _UrlKt.FRAGMENT_ENCODE_SET, false, false);
                                    checkBoxCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda136
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            checkBoxCell.setChecked(!r0.isChecked(), true);
                                        }
                                    });
                                    LinearLayout linearLayout = new LinearLayout(context);
                                    linearLayout.setOrientation(1);
                                    linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2, 22.0f, 4.0f, 22.0f, 9.0f));
                                    if (z2) {
                                        linearLayout.addView(checkBoxCell, LayoutHelper.createLinear(-1, -2, 3, 8, 6, 8, 4));
                                    }
                                    builder.setView(linearLayout);
                                    builder.setWidth(Math.min(AndroidUtilities.m1036dp(320.0f), (AndroidUtilities.displaySize.x * 85) / 100));
                                    builder.setPositiveButton(LocaleController.getString(C2797R.string.Open), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda137
                                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                        public final void onClick(AlertDialog alertDialog, int i2) {
                                            AlertsCreator.m10005$r8$lambda$vcYlpZtlX2JaFx_jMiAD8CtlEw(callback2, checkBoxCell, alertDialogArr, alertDialog, i2);
                                        }
                                    });
                                    builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda138
                                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                        public final void onClick(AlertDialog alertDialog, int i2) {
                                            AlertsCreator.m9929$r8$lambda$VzQslZH2HBCJdHlYERkbKwqX78(callback2, checkBoxCell, alertDialogArr, alertDialog, i2);
                                        }
                                    });
                                    alertDialogArr[0] = builder.show();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$vcYlpZtlX2JaFx_jM-iAD8CtlEw */
                            public static /* synthetic */ void m10005$r8$lambda$vcYlpZtlX2JaFx_jMiAD8CtlEw(Utilities.Callback2 callback2, CheckBoxCell checkBoxCell, AlertDialog[] alertDialogArr, AlertDialog alertDialog, int i) {
                                callback2.run(Boolean.TRUE, Boolean.valueOf(checkBoxCell.isChecked()));
                                AlertDialog alertDialog2 = alertDialogArr[0];
                                if (alertDialog2 != null) {
                                    alertDialog2.dismiss();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$-VzQslZH2HBCJdHlYERkbKwqX78 */
                            public static /* synthetic */ void m9929$r8$lambda$VzQslZH2HBCJdHlYERkbKwqX78(Utilities.Callback2 callback2, CheckBoxCell checkBoxCell, AlertDialog[] alertDialogArr, AlertDialog alertDialog, int i) {
                                callback2.run(Boolean.FALSE, Boolean.valueOf(checkBoxCell.isChecked()));
                                AlertDialog alertDialog2 = alertDialogArr[0];
                                if (alertDialog2 != null) {
                                    alertDialog2.dismiss();
                                }
                            }

                            private static boolean checkInternalBotApp(String str) {
                                return Uri.parse(str).getPath().matches("^/\\w*/[^\\d]*(?:\\?startapp=.*?|)$");
                            }

                            public static AlertDialog createSupportAlert(final BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider) {
                                if (baseFragment == null || baseFragment.getParentActivity() == null) {
                                    return null;
                                }
                                LinkSpanDrawable.LinksTextView linksTextView = new LinkSpanDrawable.LinksTextView(baseFragment.getParentActivity(), baseFragment.getResourceProvider());
                                SpannableString spannableString = new SpannableString(Html.fromHtml(LocaleController.getString(C2797R.string.AskAQuestionInfo).replace("\n", "<br>")));
                                for (URLSpan uRLSpan : (URLSpan[]) spannableString.getSpans(0, spannableString.length(), URLSpan.class)) {
                                    int spanStart = spannableString.getSpanStart(uRLSpan);
                                    int spanEnd = spannableString.getSpanEnd(uRLSpan);
                                    spannableString.removeSpan(uRLSpan);
                                    spannableString.setSpan(new URLSpanNoUnderline(uRLSpan.getURL()) { // from class: org.telegram.ui.Components.AlertsCreator.5
                                        final /* synthetic */ BaseFragment val$fragment;

                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        public C37565(String str, final BaseFragment baseFragment2) {
                                            super(str);
                                            baseFragment = baseFragment2;
                                        }

                                        @Override // org.telegram.p035ui.Components.URLSpanNoUnderline, android.text.style.URLSpan, android.text.style.ClickableSpan
                                        public void onClick(View view) {
                                            baseFragment.dismissCurrentDialog();
                                            super.onClick(view);
                                        }
                                    }, spanStart, spanEnd, 0);
                                }
                                linksTextView.setText(spannableString);
                                linksTextView.setTextSize(1, 16.0f);
                                linksTextView.setLinkTextColor(Theme.getColor(Theme.key_dialogTextLink, resourcesProvider));
                                linksTextView.setHighlightColor(Theme.getColor(Theme.key_dialogLinkSelection, resourcesProvider));
                                linksTextView.setPadding(AndroidUtilities.m1036dp(23.0f), 0, AndroidUtilities.m1036dp(23.0f), 0);
                                linksTextView.setMovementMethod(new AndroidUtilities.LinkMovementMethodMy());
                                linksTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
                                AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment2.getParentActivity(), resourcesProvider);
                                builder.setView(linksTextView);
                                builder.setTitle(LocaleController.getString(C2797R.string.AskAQuestion));
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.AskButton), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda74
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        AlertsCreator.performAskAQuestion(baseFragment2);
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                return builder.create();
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$5 */
                            public class C37565 extends URLSpanNoUnderline {
                                final /* synthetic */ BaseFragment val$fragment;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C37565(String str, final BaseFragment baseFragment2) {
                                    super(str);
                                    baseFragment = baseFragment2;
                                }

                                @Override // org.telegram.p035ui.Components.URLSpanNoUnderline, android.text.style.URLSpan, android.text.style.ClickableSpan
                                public void onClick(View view) {
                                    baseFragment.dismissCurrentDialog();
                                    super.onClick(view);
                                }
                            }

                            /* JADX WARN: Removed duplicated region for block: B:48:0x0057  */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public static void performAskAQuestion(final org.telegram.p035ui.ActionBar.BaseFragment r10) {
                                /*
                                    int r0 = r10.getCurrentAccount()
                                    android.content.SharedPreferences r1 = org.telegram.messenger.MessagesController.getMainSettings(r0)
                                    java.lang.String r2 = "support_id2"
                                    r3 = 0
                                    long r5 = org.telegram.messenger.AndroidUtilities.getPrefIntOrLong(r1, r2, r3)
                                    int r2 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
                                    r3 = 0
                                    r4 = 0
                                    if (r2 == 0) goto L58
                                    org.telegram.messenger.MessagesController r2 = org.telegram.messenger.MessagesController.getInstance(r0)
                                    java.lang.Long r5 = java.lang.Long.valueOf(r5)
                                    org.telegram.tgnet.TLRPC$User r2 = r2.getUser(r5)
                                    if (r2 != 0) goto L57
                                    java.lang.String r5 = "support_user"
                                    java.lang.String r5 = r1.getString(r5, r4)
                                    if (r5 == 0) goto L57
                                    byte[] r5 = android.util.Base64.decode(r5, r3)     // Catch: java.lang.Exception -> L4c
                                    if (r5 == 0) goto L57
                                    org.telegram.tgnet.SerializedData r2 = new org.telegram.tgnet.SerializedData     // Catch: java.lang.Exception -> L4c
                                    r2.<init>(r5)     // Catch: java.lang.Exception -> L4c
                                    int r5 = r2.readInt32(r3)     // Catch: java.lang.Exception -> L4c
                                    org.telegram.tgnet.TLRPC$User r5 = org.telegram.tgnet.TLRPC.User.TLdeserialize(r2, r5, r3)     // Catch: java.lang.Exception -> L4c
                                    if (r5 == 0) goto L4e
                                    long r6 = r5.f1407id     // Catch: java.lang.Exception -> L4c
                                    r8 = 333000(0x514c8, double:1.64524E-318)
                                    int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                                    if (r6 != 0) goto L4e
                                    r5 = r4
                                    goto L4e
                                L4c:
                                    r2 = move-exception
                                    goto L53
                                L4e:
                                    r2.cleanup()     // Catch: java.lang.Exception -> L4c
                                    r4 = r5
                                    goto L58
                                L53:
                                    org.telegram.messenger.FileLog.m1048e(r2)
                                    goto L58
                                L57:
                                    r4 = r2
                                L58:
                                    if (r4 != 0) goto L7c
                                    org.telegram.ui.ActionBar.AlertDialog r2 = new org.telegram.ui.ActionBar.AlertDialog
                                    android.app.Activity r4 = r10.getParentActivity()
                                    r5 = 3
                                    r2.<init>(r4, r5)
                                    r2.setCanCancel(r3)
                                    r2.show()
                                    org.telegram.tgnet.TLRPC$TL_help_getSupport r3 = new org.telegram.tgnet.TLRPC$TL_help_getSupport
                                    r3.<init>()
                                    org.telegram.tgnet.ConnectionsManager r4 = org.telegram.tgnet.ConnectionsManager.getInstance(r0)
                                    org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda114 r5 = new org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda114
                                    r5.<init>()
                                    r4.sendRequest(r3, r5)
                                    goto L98
                                L7c:
                                    org.telegram.messenger.MessagesController r0 = org.telegram.messenger.MessagesController.getInstance(r0)
                                    r1 = 1
                                    r0.putUser(r4, r1)
                                    android.os.Bundle r0 = new android.os.Bundle
                                    r0.<init>()
                                    java.lang.String r1 = "user_id"
                                    long r2 = r4.f1407id
                                    r0.putLong(r1, r2)
                                    org.telegram.ui.ChatActivity r1 = new org.telegram.ui.ChatActivity
                                    r1.<init>(r0)
                                    r10.presentFragment(r1)
                                L98:
                                    return
                                */
                                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.AlertsCreator.performAskAQuestion(org.telegram.ui.ActionBar.BaseFragment):void");
                            }

                            public static /* synthetic */ void $r8$lambda$PzWaOPdU_E4NVhLCFukR0YYRQ84(final SharedPreferences sharedPreferences, final AlertDialog alertDialog, final int i, final BaseFragment baseFragment, TLObject tLObject, TLRPC.TL_error tL_error) {
                                if (tL_error == null) {
                                    final TLRPC.TL_help_support tL_help_support = (TLRPC.TL_help_support) tLObject;
                                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda167
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            AlertsCreator.$r8$lambda$2bDpdu6mSQJSyLYbnpA81fk2zkE(sharedPreferences, tL_help_support, alertDialog, i, baseFragment);
                                        }
                                    });
                                } else {
                                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda168
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            AlertsCreator.$r8$lambda$NYgLKmse5vJXPZunxQcSR2xsEa4(alertDialog);
                                        }
                                    });
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$2bDpdu6mSQJSyLYbnpA81fk2zkE(SharedPreferences sharedPreferences, TLRPC.TL_help_support tL_help_support, AlertDialog alertDialog, int i, BaseFragment baseFragment) {
                                SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                                editorEdit.putLong("support_id2", tL_help_support.user.f1407id);
                                SerializedData serializedData = new SerializedData();
                                tL_help_support.user.serializeToStream(serializedData);
                                editorEdit.putString("support_user", Base64.encodeToString(serializedData.toByteArray(), 0));
                                editorEdit.apply();
                                serializedData.cleanup();
                                try {
                                    alertDialog.dismiss();
                                } catch (Exception e) {
                                    FileLog.m1048e(e);
                                }
                                ArrayList arrayList = new ArrayList();
                                arrayList.add(tL_help_support.user);
                                MessagesStorage.getInstance(i).putUsersAndChats(arrayList, null, true, true);
                                MessagesController.getInstance(i).putUser(tL_help_support.user, false);
                                Bundle bundle = new Bundle();
                                bundle.putLong("user_id", tL_help_support.user.f1407id);
                                baseFragment.presentFragment(new ChatActivity(bundle));
                            }

                            public static /* synthetic */ void $r8$lambda$NYgLKmse5vJXPZunxQcSR2xsEa4(AlertDialog alertDialog) {
                                try {
                                    alertDialog.dismiss();
                                } catch (Exception e) {
                                    FileLog.m1048e(e);
                                }
                            }

                            public static void createImportDialogAlert(BaseFragment baseFragment, String str, String str2, TLRPC.User user, TLRPC.Chat chat, final Runnable runnable) {
                                if (baseFragment == null || baseFragment.getParentActivity() == null) {
                                    return;
                                }
                                if (chat == null && user == null) {
                                    return;
                                }
                                int currentAccount = baseFragment.getCurrentAccount();
                                Activity parentActivity = baseFragment.getParentActivity();
                                AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                                long clientUserId = UserConfig.getInstance(currentAccount).getClientUserId();
                                TextView textView = new TextView(parentActivity);
                                textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                                textView.setTextSize(1, 16.0f);
                                textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
                                FrameLayout frameLayout = new FrameLayout(parentActivity);
                                builder.setView(frameLayout);
                                AvatarDrawable avatarDrawable = new AvatarDrawable();
                                avatarDrawable.setTextSize(AndroidUtilities.m1036dp(12.0f));
                                BackupImageView backupImageView = new BackupImageView(parentActivity);
                                backupImageView.setRoundRadius(ExteraConfig.getAvatarCorners(40.0f));
                                frameLayout.addView(backupImageView, LayoutHelper.createFrame(40, 40.0f, (LocaleController.isRTL ? 5 : 3) | 48, 22.0f, 5.0f, 22.0f, 0.0f));
                                TextView textView2 = new TextView(parentActivity);
                                textView2.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
                                textView2.setTextSize(1, 20.0f);
                                textView2.setTypeface(AndroidUtilities.bold());
                                textView2.setLines(1);
                                textView2.setMaxLines(1);
                                textView2.setSingleLine(true);
                                textView2.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
                                textView2.setEllipsize(TextUtils.TruncateAt.END);
                                textView2.setText(LocaleController.getString(C2797R.string.ImportMessages));
                                boolean z = LocaleController.isRTL;
                                frameLayout.addView(textView2, LayoutHelper.createFrame(-1, -2.0f, (z ? 5 : 3) | 48, z ? 21 : 76, 11.0f, z ? 76 : 21, 0.0f));
                                frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 57.0f, 24.0f, 9.0f));
                                if (user != null) {
                                    if (UserObject.isReplyUser(user)) {
                                        avatarDrawable.setScaleSize(0.8f);
                                        avatarDrawable.setAvatarType(12);
                                        backupImageView.setImage((ImageLocation) null, (String) null, avatarDrawable, user);
                                    } else if (user.f1407id == clientUserId) {
                                        avatarDrawable.setScaleSize(0.8f);
                                        avatarDrawable.setAvatarType(1);
                                        backupImageView.setImage((ImageLocation) null, (String) null, avatarDrawable, user);
                                    } else {
                                        avatarDrawable.setScaleSize(1.0f);
                                        avatarDrawable.setInfo(currentAccount, user);
                                        backupImageView.setForUserOrChat(user, avatarDrawable);
                                    }
                                } else {
                                    avatarDrawable.setInfo(currentAccount, chat);
                                    backupImageView.setForUserOrChat(chat, avatarDrawable);
                                }
                                textView.setText(AndroidUtilities.replaceTags(str2));
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Import), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda193
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        AlertsCreator.$r8$lambda$FWdOO0kzZOpCQVTDMiCegiJ_mt0(runnable, alertDialog, i);
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                baseFragment.showDialog(builder.create());
                            }

                            public static /* synthetic */ void $r8$lambda$FWdOO0kzZOpCQVTDMiCegiJ_mt0(Runnable runnable, AlertDialog alertDialog, int i) {
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            public static void createBotLaunchAlert(final BaseFragment baseFragment, final TLRPC.User user, final Runnable runnable, final Runnable runnable2) {
                                final Context context = baseFragment.getContext();
                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                C37676 c37676 = new LinkSpanDrawable.LinksTextView(context) { // from class: org.telegram.ui.Components.AlertsCreator.6
                                    public C37676(final Context context2) {
                                        super(context2);
                                    }

                                    @Override // org.telegram.ui.Components.LinkSpanDrawable.LinksTextView, android.widget.TextView
                                    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
                                        super.setText(Emoji.replaceEmoji(charSequence, getPaint().getFontMetricsInt(), false), bufferType);
                                    }
                                };
                                NotificationCenter.listenEmojiLoading(c37676);
                                c37676.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                                c37676.setLinkTextColor(Theme.getColor(Theme.key_chat_messageLinkIn));
                                c37676.setTextSize(1, 16.0f);
                                c37676.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
                                FrameLayout frameLayout = new FrameLayout(context2);
                                builder.setCustomViewOffset(6);
                                builder.setView(frameLayout);
                                AvatarDrawable avatarDrawable = new AvatarDrawable();
                                avatarDrawable.setTextSize(AndroidUtilities.m1036dp(18.0f));
                                BackupImageView backupImageView = new BackupImageView(context2);
                                backupImageView.setRoundRadius(AndroidUtilities.m1036dp(20.0f));
                                frameLayout.addView(backupImageView, LayoutHelper.createFrame(40, 40.0f, (LocaleController.isRTL ? 5 : 3) | 48, 22.0f, 5.0f, 22.0f, 0.0f));
                                SimpleTextView simpleTextView = new SimpleTextView(context2);
                                simpleTextView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
                                simpleTextView.setTextSize(20);
                                simpleTextView.setTypeface(AndroidUtilities.bold());
                                simpleTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
                                simpleTextView.setEllipsizeByGradient(true);
                                simpleTextView.setText(user.first_name);
                                if (user.scam) {
                                    simpleTextView.setRightDrawable(Theme.dialogs_scamDrawable);
                                } else if (user.fake) {
                                    simpleTextView.setRightDrawable(Theme.dialogs_fakeDrawable);
                                } else if (user.verified) {
                                    Drawable drawableMutate = context2.getResources().getDrawable(C2797R.drawable.verified_area).mutate();
                                    int color = Theme.getColor(Theme.key_chats_verifiedBackground);
                                    PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
                                    drawableMutate.setColorFilter(new PorterDuffColorFilter(color, mode));
                                    Drawable drawableMutate2 = context2.getResources().getDrawable(C2797R.drawable.verified_check).mutate();
                                    drawableMutate2.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_verifiedCheck), mode));
                                    simpleTextView.setRightDrawable(new CombinedDrawable(drawableMutate, drawableMutate2));
                                }
                                TextView textView = new TextView(context2);
                                textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlue));
                                textView.setTextSize(1, 14.0f);
                                textView.setLines(1);
                                textView.setMaxLines(1);
                                textView.setSingleLine(true);
                                textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
                                textView.setEllipsize(TextUtils.TruncateAt.END);
                                textView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda12
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.$r8$lambda$s4JDjuTF_t1j9GAGznGBLrWtF5A(user, baseFragment, builder, view);
                                    }
                                });
                                SpannableString spannableStringValueOf = SpannableString.valueOf(LocaleController.getString(C2797R.string.MoreAboutThisBot) + "  ");
                                ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2797R.drawable.attach_arrow_right);
                                coloredImageSpan.setTopOffset(1);
                                coloredImageSpan.setSize(AndroidUtilities.m1036dp(10.0f));
                                spannableStringValueOf.setSpan(coloredImageSpan, spannableStringValueOf.length() - 1, spannableStringValueOf.length(), 33);
                                textView.setText(spannableStringValueOf);
                                boolean z = LocaleController.isRTL;
                                frameLayout.addView(simpleTextView, LayoutHelper.createFrame(-1, -2.0f, (z ? 5 : 3) | 48, z ? 21 : 76, 0.0f, z ? 76 : 21, 0.0f));
                                boolean z2 = LocaleController.isRTL;
                                frameLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f, (z2 ? 5 : 3) | 48, z2 ? 21 : 76, 24.0f, z2 ? 76 : 21, 0.0f));
                                frameLayout.addView(c37676, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 57.0f, 24.0f, 1.0f));
                                if (UserObject.isReplyUser(user)) {
                                    avatarDrawable.setScaleSize(0.8f);
                                    avatarDrawable.setAvatarType(12);
                                    backupImageView.setImage((ImageLocation) null, (String) null, avatarDrawable, user);
                                } else {
                                    avatarDrawable.setScaleSize(1.0f);
                                    avatarDrawable.setInfo(baseFragment.getCurrentAccount(), user);
                                    backupImageView.setForUserOrChat(user, avatarDrawable);
                                }
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Start), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda13
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        AlertsCreator.$r8$lambda$srDd_cB6ofUHVZWcNBd35cS4OmA(runnable, alertDialog, i);
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                final AlertDialog alertDialogCreate = builder.create();
                                baseFragment.showDialog(alertDialogCreate, false, new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda14
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        AlertsCreator.m9950$r8$lambda$F9iRLBRH5FMmzgQQ63TDhQwXVM(runnable2, dialogInterface);
                                    }
                                });
                                c37676.setText(AndroidUtilities.replaceSingleTag(LocaleController.getString(C2797R.string.BotWebViewStartPermission2), new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda15
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AlertsCreator.m9960$r8$lambda$MZXD8gLxncxVCH032GjoS7d5Q(alertDialogCreate, context2);
                                    }
                                }));
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$6 */
                            public class C37676 extends LinkSpanDrawable.LinksTextView {
                                public C37676(final Context context2) {
                                    super(context2);
                                }

                                @Override // org.telegram.ui.Components.LinkSpanDrawable.LinksTextView, android.widget.TextView
                                public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
                                    super.setText(Emoji.replaceEmoji(charSequence, getPaint().getFontMetricsInt(), false), bufferType);
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$s4JDjuTF_t1j9GAGznGBLrWtF5A(TLRPC.User user, BaseFragment baseFragment, AlertDialog.Builder builder, View view) {
                                Bundle bundle = new Bundle();
                                bundle.putLong("user_id", user.f1407id);
                                if (baseFragment.getMessagesController().checkCanOpenChat(bundle, baseFragment)) {
                                    baseFragment.presentFragment(new ProfileActivity(bundle));
                                }
                                builder.getDismissRunnable().run();
                            }

                            public static /* synthetic */ void $r8$lambda$srDd_cB6ofUHVZWcNBd35cS4OmA(Runnable runnable, AlertDialog alertDialog, int i) {
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$F9iRLBR-H5FMmzgQQ63TDhQwXVM */
                            public static /* synthetic */ void m9950$r8$lambda$F9iRLBRH5FMmzgQQ63TDhQwXVM(Runnable runnable, DialogInterface dialogInterface) {
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$MZXD8gLxncxVCH032GjoS7--d5Q */
                            public static /* synthetic */ void m9960$r8$lambda$MZXD8gLxncxVCH032GjoS7d5Q(AlertDialog alertDialog, Context context) {
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                                Browser.openUrl(context, LocaleController.getString(C2797R.string.BotWebViewStartPermissionLink));
                            }

                            /* JADX WARN: Removed duplicated region for block: B:105:0x0131  */
                            /* JADX WARN: Removed duplicated region for block: B:106:0x0133  */
                            /* JADX WARN: Removed duplicated region for block: B:109:0x018c  */
                            /* JADX WARN: Removed duplicated region for block: B:110:0x018e  */
                            /* JADX WARN: Removed duplicated region for block: B:113:0x0197  */
                            /* JADX WARN: Removed duplicated region for block: B:114:0x0199  */
                            /* JADX WARN: Removed duplicated region for block: B:117:0x019d  */
                            /* JADX WARN: Removed duplicated region for block: B:118:0x019f  */
                            /* JADX WARN: Removed duplicated region for block: B:121:0x01b8  */
                            /* JADX WARN: Removed duplicated region for block: B:122:0x01ba  */
                            /* JADX WARN: Removed duplicated region for block: B:125:0x01bf  */
                            /* JADX WARN: Removed duplicated region for block: B:126:0x01c1  */
                            /* JADX WARN: Removed duplicated region for block: B:130:0x01c6  */
                            /* JADX WARN: Removed duplicated region for block: B:133:0x01df  */
                            /* JADX WARN: Removed duplicated region for block: B:134:0x01e1  */
                            /* JADX WARN: Removed duplicated region for block: B:137:0x01f9  */
                            /* JADX WARN: Removed duplicated region for block: B:148:0x027e  */
                            /* JADX WARN: Removed duplicated region for block: B:149:0x028d  */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public static void createBotLaunchAlert(final org.telegram.p035ui.ActionBar.BaseFragment r28, final java.util.concurrent.atomic.AtomicBoolean r29, final org.telegram.tgnet.TLRPC.User r30, final java.lang.Runnable r31) {
                                /*
                                    Method dump skipped, instruction units count: 719
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.AlertsCreator.createBotLaunchAlert(org.telegram.ui.ActionBar.BaseFragment, java.util.concurrent.atomic.AtomicBoolean, org.telegram.tgnet.TLRPC$User, java.lang.Runnable):void");
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$7 */
                            public class C37787 extends LinkSpanDrawable.LinksTextView {
                                public C37787(Context context) {
                                    super(context);
                                }

                                @Override // org.telegram.ui.Components.LinkSpanDrawable.LinksTextView, android.widget.TextView
                                public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
                                    super.setText(Emoji.replaceEmoji(charSequence, getPaint().getFontMetricsInt(), false), bufferType);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$8 */
                            public class C37808 extends FrameLayout {
                                final /* synthetic */ CheckBoxCell[] val$cell;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C37808(Context context, CheckBoxCell[] checkBoxCellArr) {
                                    super(context);
                                    checkBoxCellArr = checkBoxCellArr;
                                }

                                @Override // android.widget.FrameLayout, android.view.View
                                public void onMeasure(int i, int i2) {
                                    super.onMeasure(i, i2);
                                    if (checkBoxCellArr[0] != null) {
                                        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + checkBoxCellArr[0].getMeasuredHeight() + AndroidUtilities.m1036dp(7.0f));
                                    }
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$G-3ElzEU6GqvmKxIvYAWT8s68Po */
                            public static /* synthetic */ void m9951$r8$lambda$G3ElzEU6GqvmKxIvYAWT8s68Po(TLRPC.User user, BaseFragment baseFragment, AlertDialog.Builder builder, View view) {
                                Bundle bundle = new Bundle();
                                bundle.putLong("user_id", user.f1407id);
                                if (baseFragment.getMessagesController().checkCanOpenChat(bundle, baseFragment)) {
                                    baseFragment.presentFragment(new ProfileActivity(bundle));
                                }
                                builder.getDismissRunnable().run();
                            }

                            public static /* synthetic */ void $r8$lambda$kUO1J6mtQIXt3fR2kLBDpAxgDUQ(AtomicBoolean atomicBoolean, View view) {
                                atomicBoolean.set(!atomicBoolean.get());
                                ((CheckBoxCell) view).setChecked(atomicBoolean.get(), true);
                            }

                            /* JADX INFO: renamed from: $r8$lambda$QEH0-go5bfDyb6JxLw6sBDwekYY */
                            public static /* synthetic */ void m9968$r8$lambda$QEH0go5bfDyb6JxLw6sBDwekYY(AlertDialog alertDialog, Context context) {
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                                Browser.openUrl(context, LocaleController.getString(C2797R.string.BotWebViewStartPermissionLink));
                            }

                            public static boolean ensurePaidMessagesMultiConfirmationTopicKeys(int i, ArrayList<MessagesStorage.TopicKey> arrayList, int i2, Utilities.Callback<HashMap<Long, Long>> callback) {
                                HashSet hashSet = new HashSet();
                                if (arrayList != null) {
                                    int size = arrayList.size();
                                    int i3 = 0;
                                    while (i3 < size) {
                                        MessagesStorage.TopicKey topicKey = arrayList.get(i3);
                                        i3++;
                                        hashSet.add(Long.valueOf(topicKey.dialogId));
                                    }
                                }
                                return ensurePaidMessagesMultiConfirmation(i, new ArrayList(hashSet), i2, callback);
                            }

                            public static boolean ensurePaidMessagesMultiConfirmation(final int i, final ArrayList<Long> arrayList, int i2, final Utilities.Callback<HashMap<Long, Long>> callback) {
                                boolean z = false;
                                if (callback == null) {
                                    return false;
                                }
                                if (arrayList == null || arrayList.isEmpty()) {
                                    callback.run(new HashMap<>());
                                    return false;
                                }
                                final HashMap<Long, Long> map = new HashMap<>();
                                int size = arrayList.size();
                                int i3 = 0;
                                int i4 = 0;
                                long j = 0;
                                boolean z2 = true;
                                while (i4 < size) {
                                    Long l = arrayList.get(i4);
                                    i4++;
                                    Long l2 = l;
                                    long jLongValue = l2.longValue();
                                    boolean z3 = z;
                                    long sendPaidMessagesStars = MessagesController.getInstance(i).getSendPaidMessagesStars(jLongValue);
                                    if (sendPaidMessagesStars <= 0 && jLongValue > 0) {
                                        sendPaidMessagesStars = DialogObject.getMessagesStarsPrice(MessagesController.getInstance(i).isUserContactBlocked(jLongValue));
                                    }
                                    map.put(l2, Long.valueOf(sendPaidMessagesStars));
                                    j += sendPaidMessagesStars;
                                    StarsController.getInstance(i).sendingMessagesCount.put(l2, Integer.valueOf(i2));
                                    if (sendPaidMessagesStars > 0) {
                                        i3++;
                                    }
                                    if (sendPaidMessagesStars > 0 && z2) {
                                        if (MessagesController.getInstance(i).getMainSettings().getLong("ask_paid_message_" + jLongValue + "_price", 0L) < sendPaidMessagesStars) {
                                            z2 = z3;
                                        }
                                    }
                                    z = z3;
                                }
                                boolean z4 = z;
                                final long jMax = j * ((long) Math.max(1, i2));
                                if (z2 || jMax <= 0) {
                                    callback.run(map);
                                    return z4;
                                }
                                final Activity activity = AndroidUtilities.getActivity();
                                BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
                                final Theme.ResourcesProvider darkThemeResourceProvider = (PhotoViewer.getInstance().isVisible() || (safeLastFragment != null && safeLastFragment.hasShownSheet())) ? new DarkThemeResourceProvider() : safeLastFragment != null ? safeLastFragment.getResourceProvider() : null;
                                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                                spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessageMulti1", i3)));
                                spannableStringBuilder.append((CharSequence) " ");
                                spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessageMulti2", (int) jMax, LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessageMulti2Messages", i2 * Math.max(1, i3)))));
                                showAlertWithCheckboxWithBalance(activity, LocaleController.getString(C2797R.string.MessageLockedStarsConfirmTitle), spannableStringBuilder, LocaleController.getString(C2797R.string.MessageLockedStarsConfirmMessageDontAsk), LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessagePay", i2), new Utilities.Callback() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda82
                                    @Override // org.telegram.messenger.Utilities.Callback
                                    public final void run(Object obj) {
                                        AlertsCreator.$r8$lambda$12CozHwMSFmqhuIBbZJqQZx54U4(i, arrayList, jMax, activity, darkThemeResourceProvider, callback, map, (Boolean) obj);
                                    }
                                }, darkThemeResourceProvider);
                                return true;
                            }

                            public static /* synthetic */ void $r8$lambda$12CozHwMSFmqhuIBbZJqQZx54U4(final int i, final ArrayList arrayList, final long j, final Activity activity, final Theme.ResourcesProvider resourcesProvider, final Utilities.Callback callback, final HashMap map, Boolean bool) {
                                if (bool.booleanValue()) {
                                    SharedPreferences.Editor editorEdit = MessagesController.getInstance(i).getMainSettings().edit();
                                    int size = arrayList.size();
                                    int i2 = 0;
                                    while (i2 < size) {
                                        Object obj = arrayList.get(i2);
                                        i2++;
                                        Long l = (Long) obj;
                                        long jLongValue = l.longValue();
                                        long sendPaidMessagesStars = MessagesController.getInstance(i).getSendPaidMessagesStars(jLongValue);
                                        if (sendPaidMessagesStars <= 0 && jLongValue > 0) {
                                            sendPaidMessagesStars = DialogObject.getMessagesStarsPrice(MessagesController.getInstance(i).isUserContactBlocked(jLongValue));
                                        }
                                        editorEdit.putLong("ask_paid_message_" + jLongValue + "_price", sendPaidMessagesStars);
                                        StarsController.getInstance(i).justAgreedToNotAskDialogs.put(l, Long.valueOf(System.currentTimeMillis()));
                                    }
                                    editorEdit.apply();
                                }
                                Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda160
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AlertsCreator.m9958$r8$lambda$KrVKTkkKvcjmyeXvQZn31hxb4(i, j, activity, arrayList, resourcesProvider, callback, map);
                                    }
                                };
                                if (!StarsController.getInstance(i).balanceAvailable()) {
                                    StarsController.getInstance(i).invalidateBalance(runnable);
                                } else {
                                    runnable.run();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$KrVKTkkKvcjmyeXv--QZn31hxb4 */
                            public static /* synthetic */ void m9958$r8$lambda$KrVKTkkKvcjmyeXvQZn31hxb4(int i, long j, Activity activity, ArrayList arrayList, Theme.ResourcesProvider resourcesProvider, final Utilities.Callback callback, final HashMap map) {
                                if (StarsController.getInstance(i).getBalance().amount >= j) {
                                    callback.run(map);
                                } else {
                                    if (activity == null) {
                                        return;
                                    }
                                    long jLongValue = ((Long) arrayList.get(0)).longValue();
                                    new StarsIntroActivity.StarsNeededSheet(activity, resourcesProvider, j, 13, DialogObject.getShortName(i, jLongValue), new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda196
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            callback.run(map);
                                        }
                                    }, jLongValue).show();
                                }
                            }

                            public static boolean needsPaidMessageAlert(int i, long j) {
                                long sendPaidMessagesStars = MessagesController.getInstance(i).getSendPaidMessagesStars(j);
                                if (sendPaidMessagesStars <= 0 && j > 0) {
                                    sendPaidMessagesStars = DialogObject.getMessagesStarsPrice(MessagesController.getInstance(i).isUserContactBlocked(j));
                                }
                                if (sendPaidMessagesStars <= 0) {
                                    return false;
                                }
                                SharedPreferences mainSettings = MessagesController.getInstance(i).getMainSettings();
                                StringBuilder sb = new StringBuilder("ask_paid_message_");
                                sb.append(j);
                                sb.append("_price");
                                return sendPaidMessagesStars > mainSettings.getLong(sb.toString(), 0L);
                            }

                            public static boolean ensurePaidMessageConfirmation(int i, long j, int i2, Utilities.Callback<Long> callback) {
                                return ensurePaidMessageConfirmation(i, j, i2, callback, 0L);
                            }

                            public static boolean ensurePaidMessageConfirmation(final int i, final long j, int i2, final Utilities.Callback<Long> callback, long j2) {
                                if (callback == null) {
                                    return false;
                                }
                                long sendPaidMessagesStars = MessagesController.getInstance(i).getSendPaidMessagesStars(j);
                                if (sendPaidMessagesStars <= 0 && j > 0) {
                                    sendPaidMessagesStars = DialogObject.getMessagesStarsPrice(MessagesController.getInstance(i).isUserContactBlocked(j));
                                }
                                final long j3 = ((long) i2) * sendPaidMessagesStars;
                                StarsController.getInstance(i).sendingMessagesCount.put(Long.valueOf(j), Integer.valueOf(i2));
                                if (j3 <= 0 || j2 == j3) {
                                    callback.run(Long.valueOf(j3));
                                    return false;
                                }
                                final long j4 = sendPaidMessagesStars;
                                showPayForMessageAlert(i, j, j4, i2, new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda6
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AlertsCreator.m9946$r8$lambda$CiXdcSTbAcUKl8objJqDXmi8W4(i, j3, j, callback, j4);
                                    }
                                });
                                return true;
                            }

                            /* JADX INFO: renamed from: $r8$lambda$CiXdcSTbAcUKl8ob-jJqDXmi8W4 */
                            public static /* synthetic */ void m9946$r8$lambda$CiXdcSTbAcUKl8objJqDXmi8W4(final int i, final long j, final long j2, final Utilities.Callback callback, final long j3) {
                                Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda85
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AlertsCreator.m9984$r8$lambda$bAUVoYRPKirTeoy32_EXbfGjew(i, j, j2, callback, j3);
                                    }
                                };
                                if (!StarsController.getInstance(i).balanceAvailable()) {
                                    StarsController.getInstance(i).invalidateBalance(runnable);
                                } else {
                                    runnable.run();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$bAUVoYRPKi-rTeoy32_EXbfGjew */
                            public static /* synthetic */ void m9984$r8$lambda$bAUVoYRPKirTeoy32_EXbfGjew(int i, long j, long j2, final Utilities.Callback callback, final long j3) {
                                if (StarsController.getInstance(i).getBalance().amount < j) {
                                    Activity activity = AndroidUtilities.getActivity();
                                    BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
                                    Theme.ResourcesProvider darkThemeResourceProvider = (PhotoViewer.getInstance().isVisible() || (safeLastFragment != null && safeLastFragment.hasShownSheet())) ? new DarkThemeResourceProvider() : safeLastFragment != null ? safeLastFragment.getResourceProvider() : null;
                                    if (activity == null) {
                                        return;
                                    }
                                    new StarsIntroActivity.StarsNeededSheet(activity, darkThemeResourceProvider, j, 13, DialogObject.getShortName(i, j2), new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda149
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            callback.run(Long.valueOf(j3));
                                        }
                                    }, j2).show();
                                    return;
                                }
                                callback.run(Long.valueOf(j3));
                            }

                            public static void showPayForMessageAlert(final int i, final long j, final long j2, int i2, final Runnable runnable) {
                                TLRPC.Chat chat;
                                if (runnable == null) {
                                    return;
                                }
                                if (j2 <= MessagesController.getInstance(i).getMainSettings().getLong("ask_paid_message_" + j + "_price", 0L)) {
                                    runnable.run();
                                    return;
                                }
                                Activity activity = AndroidUtilities.getActivity();
                                BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
                                Theme.ResourcesProvider darkThemeResourceProvider = (PhotoViewer.getInstance().isVisible() || (safeLastFragment != null && safeLastFragment.hasShownSheet())) ? new DarkThemeResourceProvider() : safeLastFragment != null ? safeLastFragment.getResourceProvider() : null;
                                String shortName = DialogObject.getShortName(i, j);
                                if (ChatObject.isMonoForum(i, j)) {
                                    shortName = ForumUtilities.getMonoForumTitle(i, j, true);
                                } else if (safeLastFragment instanceof ChatActivity) {
                                    ChatActivity chatActivity = (ChatActivity) safeLastFragment;
                                    if (chatActivity.isComments && chatActivity.getDialogId() == j && (chat = chatActivity.replyOriginalChat) != null) {
                                        shortName = DialogObject.getShortName(i, -chat.f1245id);
                                    }
                                }
                                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                                int i3 = (int) j2;
                                spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessage1", i3, shortName)));
                                spannableStringBuilder.append((CharSequence) " ");
                                if (i2 == 1) {
                                    spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessage2One", i3)));
                                } else {
                                    spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessage2Many1", (int) (((long) i2) * j2))));
                                    spannableStringBuilder.append((CharSequence) " ");
                                    spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessage2Many2", i2)));
                                }
                                showAlertWithCheckboxWithBalance(activity, LocaleController.getString(C2797R.string.MessageLockedStarsConfirmTitle), spannableStringBuilder, LocaleController.getString(C2797R.string.MessageLockedStarsConfirmMessageDontAsk), LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessagePay", i2), new Utilities.Callback() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda92
                                    @Override // org.telegram.messenger.Utilities.Callback
                                    public final void run(Object obj) {
                                        AlertsCreator.m9934$r8$lambda$1v3KqW8k0NbLyutMNpjIq9Q_3o(i, j, j2, runnable, (Boolean) obj);
                                    }
                                }, darkThemeResourceProvider);
                            }

                            /* JADX INFO: renamed from: $r8$lambda$1v3KqW8k0NbLyutMNpjIq9Q_-3o */
                            public static /* synthetic */ void m9934$r8$lambda$1v3KqW8k0NbLyutMNpjIq9Q_3o(int i, long j, long j2, Runnable runnable, Boolean bool) {
                                if (bool.booleanValue()) {
                                    MessagesController.getInstance(i).getMainSettings().edit().putLong("ask_paid_message_" + j + "_price", j2).apply();
                                    StarsController.getInstance(i).justAgreedToNotAskDialogs.put(Long.valueOf(j), Long.valueOf(System.currentTimeMillis()));
                                }
                                AndroidUtilities.runOnUIThread(runnable);
                            }

                            public static AlertDialog showAlertWithCheckbox(Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, Utilities.Callback<Boolean> callback, Theme.ResourcesProvider resourcesProvider) {
                                return showAlertWithCheckbox(context, charSequence, charSequence2, charSequence3, charSequence4, callback, resourcesProvider, false);
                            }

                            public static AlertDialog showAlertWithCheckboxWithBalance(Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, Utilities.Callback<Boolean> callback, Theme.ResourcesProvider resourcesProvider) {
                                return showAlertWithCheckbox(context, charSequence, charSequence2, charSequence3, charSequence4, callback, resourcesProvider, true);
                            }

                            public static AlertDialog showAlertWithCheckbox(Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, final Utilities.Callback<Boolean> callback, Theme.ResourcesProvider resourcesProvider, boolean z) {
                                if (context == null) {
                                    callback.run(Boolean.FALSE);
                                    return null;
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(context, resourcesProvider);
                                CheckBoxCell[] checkBoxCellArr = new CheckBoxCell[1];
                                final boolean[] zArr = new boolean[1];
                                C37819 c37819 = new TextView(context) { // from class: org.telegram.ui.Components.AlertsCreator.9
                                    public C37819(Context context2) {
                                        super(context2);
                                    }

                                    @Override // android.widget.TextView
                                    public void setText(CharSequence charSequence5, TextView.BufferType bufferType) {
                                        super.setText(Emoji.replaceEmoji(charSequence5, getPaint().getFontMetricsInt(), false), bufferType);
                                    }
                                };
                                NotificationCenter.listenEmojiLoading(c37819);
                                c37819.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
                                c37819.setTextSize(1, 16.0f);
                                c37819.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
                                c37819.setText(charSequence2);
                                C371510 c371510 = new FrameLayout(context2) { // from class: org.telegram.ui.Components.AlertsCreator.10
                                    final /* synthetic */ CheckBoxCell[] val$cell;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C371510(Context context2, CheckBoxCell[] checkBoxCellArr2) {
                                        super(context2);
                                        checkBoxCellArr = checkBoxCellArr2;
                                    }

                                    @Override // android.widget.FrameLayout, android.view.View
                                    public void onMeasure(int i, int i2) {
                                        super.onMeasure(i, i2);
                                        if (checkBoxCellArr[0] != null) {
                                            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + checkBoxCellArr[0].getMeasuredHeight() + AndroidUtilities.m1036dp(7.0f));
                                        }
                                    }
                                };
                                builder.setCustomViewOffset(6);
                                builder.setView(c371510);
                                TextView textView = new TextView(context2);
                                textView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem, resourcesProvider));
                                textView.setTextSize(1, 20.0f);
                                textView.setTypeface(AndroidUtilities.bold());
                                textView.setLines(1);
                                textView.setMaxLines(1);
                                textView.setSingleLine(true);
                                textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
                                textView.setEllipsize(TextUtils.TruncateAt.END);
                                textView.setText(charSequence);
                                c371510.addView(textView, LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 8.0f, 24.0f, 0.0f));
                                c371510.addView(c37819, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 48.0f, 24.0f, 6.0f));
                                if (!TextUtils.isEmpty(charSequence3)) {
                                    CheckBoxCell checkBoxCell = new CheckBoxCell(context2, 1, resourcesProvider);
                                    checkBoxCellArr2[0] = checkBoxCell;
                                    checkBoxCell.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_listSelector, resourcesProvider), 7, AndroidUtilities.m1036dp(12.0f)));
                                    checkBoxCellArr2[0].setMultiline(true);
                                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) checkBoxCellArr2[0].getCheckBoxView().getLayoutParams();
                                    layoutParams.topMargin = 0;
                                    layoutParams.gravity = (LocaleController.isRTL ? 5 : 3) | 16;
                                    checkBoxCellArr2[0].getCheckBoxView().setLayoutParams(layoutParams);
                                    checkBoxCellArr2[0].setText(charSequence3, _UrlKt.FRAGMENT_ENCODE_SET, false, false);
                                    checkBoxCellArr2[0].setPadding(LocaleController.isRTL ? AndroidUtilities.m1036dp(4.0f) : 0, AndroidUtilities.m1036dp(12.0f), LocaleController.isRTL ? 0 : AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(12.0f));
                                    c371510.addView(checkBoxCellArr2[0], LayoutHelper.createFrame(-1, -2.0f, 83, 8.0f, 0.0f, 8.0f, 0.0f));
                                    checkBoxCellArr2[0].setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda72
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            AlertsCreator.m9954$r8$lambda$IDADqTTQseM53LT_U9689NuVXQ(zArr, view);
                                        }
                                    });
                                }
                                builder.setPositiveButton(charSequence4, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda73
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        callback.run(Boolean.valueOf(zArr[0]));
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                AlertDialog alertDialogCreate = builder.create();
                                if (z) {
                                    alertDialogCreate.setShowStarsBalance(true);
                                }
                                alertDialogCreate.show();
                                return alertDialogCreate;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$9 */
                            public class C37819 extends TextView {
                                public C37819(Context context2) {
                                    super(context2);
                                }

                                @Override // android.widget.TextView
                                public void setText(CharSequence charSequence5, TextView.BufferType bufferType) {
                                    super.setText(Emoji.replaceEmoji(charSequence5, getPaint().getFontMetricsInt(), false), bufferType);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$10 */
                            public class C371510 extends FrameLayout {
                                final /* synthetic */ CheckBoxCell[] val$cell;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C371510(Context context2, CheckBoxCell[] checkBoxCellArr2) {
                                    super(context2);
                                    checkBoxCellArr = checkBoxCellArr2;
                                }

                                @Override // android.widget.FrameLayout, android.view.View
                                public void onMeasure(int i, int i2) {
                                    super.onMeasure(i, i2);
                                    if (checkBoxCellArr[0] != null) {
                                        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + checkBoxCellArr[0].getMeasuredHeight() + AndroidUtilities.m1036dp(7.0f));
                                    }
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$IDADqTTQseM53LT_U9689Nu-VXQ */
                            public static /* synthetic */ void m9954$r8$lambda$IDADqTTQseM53LT_U9689NuVXQ(boolean[] zArr, View view) {
                                boolean z = !zArr[0];
                                zArr[0] = z;
                                ((CheckBoxCell) view).setChecked(z, true);
                            }

                            public static void createClearOrDeleteDialogAlert(BaseFragment baseFragment, boolean z, TLRPC.Chat chat, TLRPC.User user, boolean z2, boolean z3, boolean z4, boolean z5, MessagesStorage.BooleanCallback booleanCallback) {
                                createClearOrDeleteDialogAlert(baseFragment, z, false, chat, user, z2, z3, z4, z5, booleanCallback, baseFragment != null ? baseFragment.getResourceProvider() : null);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:428:0x03a8  */
                            /* JADX WARN: Removed duplicated region for block: B:435:0x03e0  */
                            /* JADX WARN: Removed duplicated region for block: B:445:0x03f9  */
                            /* JADX WARN: Removed duplicated region for block: B:453:0x0434  */
                            /* JADX WARN: Removed duplicated region for block: B:500:0x0588  */
                            /* JADX WARN: Removed duplicated region for block: B:502:0x0591  */
                            /* JADX WARN: Removed duplicated region for block: B:526:0x061e  */
                            /* JADX WARN: Removed duplicated region for block: B:530:? A[RETURN, SYNTHETIC] */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public static void createClearOrDeleteDialogAlert(final org.telegram.p035ui.ActionBar.BaseFragment r38, final boolean r39, final boolean r40, final org.telegram.tgnet.TLRPC.Chat r41, final org.telegram.tgnet.TLRPC.User r42, final boolean r43, final boolean r44, boolean r45, final boolean r46, final org.telegram.messenger.MessagesStorage.BooleanCallback r47, final org.telegram.ui.ActionBar.Theme.ResourcesProvider r48) {
                                /*
                                    Method dump skipped, instruction units count: 1576
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.AlertsCreator.createClearOrDeleteDialogAlert(org.telegram.ui.ActionBar.BaseFragment, boolean, boolean, org.telegram.tgnet.TLRPC$Chat, org.telegram.tgnet.TLRPC$User, boolean, boolean, boolean, boolean, org.telegram.messenger.MessagesStorage$BooleanCallback, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$11 */
                            public class C371611 extends TextView {
                                public C371611(Context context) {
                                    super(context);
                                }

                                @Override // android.widget.TextView
                                public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
                                    super.setText(Emoji.replaceEmoji(charSequence, getPaint().getFontMetricsInt(), false), bufferType);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$12 */
                            public class C371712 extends FrameLayout {
                                final /* synthetic */ CheckBoxCell[] val$cell;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C371712(Context context, CheckBoxCell[] checkBoxCellArr) {
                                    super(context);
                                    checkBoxCellArr = checkBoxCellArr;
                                }

                                @Override // android.widget.FrameLayout, android.view.View
                                public void onMeasure(int i, int i2) {
                                    super.onMeasure(i, i2);
                                    if (checkBoxCellArr[0] != null) {
                                        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + checkBoxCellArr[0].getMeasuredHeight() + AndroidUtilities.m1036dp(7.0f));
                                    }
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$_KfE7E7udJ9Ayx3776pnqy2tWak(boolean[] zArr, View view) {
                                boolean z = !zArr[0];
                                zArr[0] = z;
                                ((CheckBoxCell) view).setChecked(z, true);
                            }

                            public static /* synthetic */ void $r8$lambda$KRfM5xJIsyTA1LoohkAAvVkR_Iw(boolean z, TLRPC.Chat chat, AlertDialog.Builder builder, boolean[] zArr) {
                                if (z && ChatObject.isChannel(chat)) {
                                    View button = builder.create().getButton(-1);
                                    if (button instanceof TextView) {
                                        TextView textView = (TextView) button;
                                        if (zArr[0]) {
                                            textView.setText(LocaleController.getString(ChatObject.isChannelAndNotMegaGroup(chat) ? C2797R.string.ChannelDelete : C2797R.string.DeleteMega));
                                            return;
                                        }
                                        if (chat.monoforum) {
                                            textView.setText(LocaleController.getString(C2797R.string.LeaveConversationMenu));
                                        } else if (chat.megagroup) {
                                            textView.setText(LocaleController.getString(C2797R.string.LeaveMega));
                                        } else {
                                            textView.setText(LocaleController.getString(C2797R.string.LeaveChannel));
                                        }
                                    }
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$2en-194kp74cjeT0l2JYq5115rA */
                            public static /* synthetic */ void m9935$r8$lambda$2en194kp74cjeT0l2JYq5115rA(boolean[] zArr, Runnable runnable, View view) {
                                boolean z = !zArr[0];
                                zArr[0] = z;
                                ((CheckBoxCell) view).setChecked(z, true);
                                runnable.run();
                            }

                            public static /* synthetic */ void $r8$lambda$EDrY8F6KahX0Z3Qwil4AcmHUbjc(boolean z, final boolean z2, boolean z3, final TLRPC.User user, final BaseFragment baseFragment, final boolean z4, final TLRPC.Chat chat, final boolean z5, final boolean[] zArr, final boolean z6, final MessagesStorage.BooleanCallback booleanCallback, final Theme.ResourcesProvider resourcesProvider, AlertDialog.Builder builder, final int i, final Context context, AlertDialog alertDialog, int i2) {
                                if (!z && !z2 && !z3) {
                                    if (UserObject.isUserSelf(user)) {
                                        createClearOrDeleteDialogAlert(baseFragment, z4, true, chat, user, false, z5, zArr[0], z6, booleanCallback, resourcesProvider);
                                        return;
                                    }
                                    if (user != null && zArr[0]) {
                                        MessagesStorage.getInstance(baseFragment.getCurrentAccount()).getMessagesCount(user.f1407id, new MessagesStorage.IntCallback() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda143
                                            @Override // org.telegram.messenger.MessagesStorage.IntCallback
                                            public final void run(int i3) {
                                                AlertsCreator.m9994$r8$lambda$jsxryQcpGaN7DV93RlNGjsnlNc(baseFragment, z4, chat, user, z5, zArr, z6, booleanCallback, resourcesProvider, i3);
                                            }
                                        });
                                        return;
                                    }
                                    if (ChatObject.isChannel(chat) && chat.creator && !zArr[0]) {
                                        final Browser.Progress progressMakeButtonLoading = builder.create().makeButtonLoading(-1);
                                        progressMakeButtonLoading.init();
                                        TLRPC.TL_channels_getFutureCreatorAfterLeave tL_channels_getFutureCreatorAfterLeave = new TLRPC.TL_channels_getFutureCreatorAfterLeave();
                                        tL_channels_getFutureCreatorAfterLeave.channel = MessagesController.getInputChannel(chat);
                                        ConnectionsManager.getInstance(i).sendRequestTyped(tL_channels_getFutureCreatorAfterLeave, new AiTonesController$$ExternalSyntheticLambda0(), new Utilities.Callback2() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda144
                                            @Override // org.telegram.messenger.Utilities.Callback2
                                            public final void run(Object obj, Object obj2) {
                                                AlertsCreator.$r8$lambda$TAY9LIaef95EksCPYu6uCButGQ0(progressMakeButtonLoading, booleanCallback, z2, zArr, baseFragment, chat, context, i, resourcesProvider, (TLRPC.User) obj, (TLRPC.TL_error) obj2);
                                            }
                                        });
                                        return;
                                    }
                                }
                                if (booleanCallback != null) {
                                    booleanCallback.run(z2 || zArr[0]);
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$jsxryQc-pGaN7DV93RlNGjsnlNc */
                            public static /* synthetic */ void m9994$r8$lambda$jsxryQcpGaN7DV93RlNGjsnlNc(BaseFragment baseFragment, boolean z, TLRPC.Chat chat, TLRPC.User user, boolean z2, boolean[] zArr, boolean z3, MessagesStorage.BooleanCallback booleanCallback, Theme.ResourcesProvider resourcesProvider, int i) {
                                if (i >= 50) {
                                    createClearOrDeleteDialogAlert(baseFragment, z, true, chat, user, false, z2, zArr[0], z3, booleanCallback, resourcesProvider);
                                } else if (booleanCallback != null) {
                                    booleanCallback.run(zArr[0]);
                                }
                            }

                            /* JADX WARN: Removed duplicated region for block: B:28:0x0016  */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public static /* synthetic */ void $r8$lambda$TAY9LIaef95EksCPYu6uCButGQ0(org.telegram.messenger.browser.Browser.Progress r7, final org.telegram.messenger.MessagesStorage.BooleanCallback r8, boolean r9, boolean[] r10, org.telegram.p035ui.ActionBar.BaseFragment r11, final org.telegram.tgnet.TLRPC.Chat r12, final android.content.Context r13, final int r14, final org.telegram.ui.ActionBar.Theme.ResourcesProvider r15, org.telegram.tgnet.TLRPC.User r16, org.telegram.tgnet.TLRPC.TL_error r17) {
                                /*
                                    r7.end()
                                    r7 = r16
                                    boolean r0 = r7 instanceof org.telegram.tgnet.TLRPC.TL_userEmpty
                                    if (r0 == 0) goto La
                                    r7 = 0
                                La:
                                    r3 = r7
                                    if (r3 != 0) goto L1b
                                    if (r8 == 0) goto L1a
                                    if (r9 != 0) goto L16
                                    r7 = 0
                                    boolean r9 = r10[r7]
                                    if (r9 == 0) goto L17
                                L16:
                                    r7 = 1
                                L17:
                                    r8.run(r7)
                                L1a:
                                    return
                                L1b:
                                    org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda170 r0 = new org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda170
                                    r5 = r8
                                    r2 = r12
                                    r1 = r13
                                    r4 = r14
                                    r6 = r15
                                    r0.<init>()
                                    org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda171 r7 = new org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda171
                                    r7.<init>()
                                    showLeaveGroupWithFutureOwner(r11, r12, r3, r0, r7)
                                    return
                                */
                                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.AlertsCreator.$r8$lambda$TAY9LIaef95EksCPYu6uCButGQ0(org.telegram.messenger.browser.Browser$Progress, org.telegram.messenger.MessagesStorage$BooleanCallback, boolean, boolean[], org.telegram.ui.ActionBar.BaseFragment, org.telegram.tgnet.TLRPC$Chat, android.content.Context, int, org.telegram.ui.ActionBar.Theme$ResourcesProvider, org.telegram.tgnet.TLRPC$User, org.telegram.tgnet.TLRPC$TL_error):void");
                            }

                            public static /* synthetic */ void $r8$lambda$nmJfafoXJmNr27jcm0YkYTcqdvY(int i, final MessagesStorage.BooleanCallback booleanCallback) {
                                NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
                                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda242
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AlertsCreator.m9941$r8$lambda$7NdAR39RmhSaVrDQzhZ_5NIkNc(booleanCallback);
                                    }
                                }, 250L);
                            }

                            /* JADX INFO: renamed from: $r8$lambda$7NdAR39Rm-hSaVrDQzhZ_5NIkNc */
                            public static /* synthetic */ void m9941$r8$lambda$7NdAR39RmhSaVrDQzhZ_5NIkNc(MessagesStorage.BooleanCallback booleanCallback) {
                                if (booleanCallback != null) {
                                    booleanCallback.run(false);
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$fKbIdNPGh8xbX2LJhDZjzohPd4Q(MessagesStorage.BooleanCallback booleanCallback) {
                                if (booleanCallback != null) {
                                    booleanCallback.run(false);
                                }
                            }

                            public static void showLeaveGroupWithFutureOwner(BaseFragment baseFragment, TLRPC.Chat chat, TLRPC.User user, final Runnable runnable, final Runnable runnable2) {
                                if (baseFragment == null || baseFragment.getParentActivity() == null || chat == null) {
                                    return;
                                }
                                Context context = baseFragment.getContext();
                                TLRPC.User currentUser = UserConfig.getInstance(baseFragment.getCurrentAccount()).getCurrentUser();
                                boolean zIsChannelAndNotMegaGroup = ChatObject.isChannelAndNotMegaGroup(chat);
                                FrameLayout frameLayout = new FrameLayout(context);
                                frameLayout.setClipToPadding(false);
                                frameLayout.setClipChildren(false);
                                BackupImageView backupImageView = new BackupImageView(context);
                                AvatarDrawable avatarDrawable = new AvatarDrawable();
                                avatarDrawable.setInfo(currentUser);
                                backupImageView.setRoundRadius(AndroidUtilities.m1036dp(30.0f));
                                backupImageView.setForUserOrChat(currentUser, avatarDrawable);
                                frameLayout.addView(backupImageView, LayoutHelper.createFrame(60, 60.0f, 17, -48.0f, 15.0f, 0.0f, 12.0f));
                                ImageView imageView = new ImageView(context);
                                imageView.setImageResource(C2797R.drawable.msg_arrow_avatar);
                                imageView.setColorFilter(new PorterDuffColorFilter(baseFragment.getThemedColor(Theme.key_divider), PorterDuff.Mode.SRC_IN));
                                frameLayout.addView(imageView, LayoutHelper.createFrame(24, 24.0f, 17, 0.0f, 15.0f, 0.0f, 12.0f));
                                C371813 c371813 = new BackupImageView(context) { // from class: org.telegram.ui.Components.AlertsCreator.13
                                    final Path path = new Path();

                                    public C371813(Context context2) {
                                        super(context2);
                                        this.path = new Path();
                                    }

                                    @Override // org.telegram.p035ui.Components.BackupImageView, android.view.View
                                    public void onDraw(Canvas canvas) {
                                        canvas.save();
                                        this.path.rewind();
                                        this.path.addCircle(AndroidUtilities.m1036dp(54.0f), AndroidUtilities.m1036dp(53.0f), AndroidUtilities.m1036dp(14.0f), Path.Direction.CW);
                                        canvas.clipPath(this.path, Region.Op.DIFFERENCE);
                                        super.onDraw(canvas);
                                        canvas.restore();
                                    }
                                };
                                AvatarDrawable avatarDrawable2 = new AvatarDrawable();
                                avatarDrawable2.setInfo(user);
                                c371813.setRoundRadius(AndroidUtilities.m1036dp(30.0f));
                                c371813.setForUserOrChat(user, avatarDrawable2);
                                frameLayout.addView(c371813, LayoutHelper.createFrame(60, 60.0f, 17, 48.0f, 15.0f, 0.0f, 12.0f));
                                BackupImageView backupImageView2 = new BackupImageView(context2);
                                AvatarDrawable avatarDrawable3 = new AvatarDrawable();
                                avatarDrawable3.setInfo(chat);
                                backupImageView2.setRoundRadius(AndroidUtilities.m1036dp(12.0f));
                                backupImageView2.setForUserOrChat(chat, avatarDrawable3);
                                frameLayout.addView(backupImageView2, LayoutHelper.createFrame(24, 24.0f, 17, 72.0f, 26.0f, 0.0f, 0.0f));
                                AlertDialog.Builder builder = new AlertDialog.Builder(context2);
                                builder.setTopViewAspectRatio(-1.0f);
                                builder.setTopView(frameLayout);
                                builder.setTitle(LocaleController.getString(zIsChannelAndNotMegaGroup ? C2797R.string.LeaveChannelTitle : C2797R.string.LeaveGroupTitle));
                                builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString(zIsChannelAndNotMegaGroup ? C2797R.string.LeaveChannelNewOwnerText : C2797R.string.LeaveGroupNewOwnerText, UserObject.getUserName(user), chat.title)));
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.AppointNewOwner), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda220
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        runnable.run();
                                    }
                                });
                                builder.setNeutralButton(LocaleController.getString(C2797R.string.Cancel), null);
                                builder.setPositiveButton(LocaleController.getString(zIsChannelAndNotMegaGroup ? C2797R.string.LeaveChannel : C2797R.string.LeaveMegaMenu), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda221
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        runnable2.run();
                                    }
                                });
                                AlertDialog alertDialogCreate = builder.create();
                                alertDialogCreate.show();
                                View button = alertDialogCreate.getButton(-1);
                                if (button instanceof TextView) {
                                    ((TextView) button).setTextColor(Theme.getColor(Theme.key_text_RedBold));
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$13 */
                            public class C371813 extends BackupImageView {
                                final Path path = new Path();

                                public C371813(Context context2) {
                                    super(context2);
                                    this.path = new Path();
                                }

                                @Override // org.telegram.p035ui.Components.BackupImageView, android.view.View
                                public void onDraw(Canvas canvas) {
                                    canvas.save();
                                    this.path.rewind();
                                    this.path.addCircle(AndroidUtilities.m1036dp(54.0f), AndroidUtilities.m1036dp(53.0f), AndroidUtilities.m1036dp(14.0f), Path.Direction.CW);
                                    canvas.clipPath(this.path, Region.Op.DIFFERENCE);
                                    super.onDraw(canvas);
                                    canvas.restore();
                                }
                            }

                            public static void createClearOrDeleteDialogsAlert(BaseFragment baseFragment, boolean z, boolean z2, int i, int i2, boolean z3, final MessagesStorage.BooleanCallback booleanCallback, Theme.ResourcesProvider resourcesProvider) {
                                CharSequence string;
                                int currentAccount = baseFragment.getCurrentAccount();
                                Activity parentActivity = baseFragment.getParentActivity();
                                AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity, resourcesProvider);
                                UserConfig.getInstance(currentAccount).getClientUserId();
                                CheckBoxCell[] checkBoxCellArr = new CheckBoxCell[1];
                                final boolean[] zArr = new boolean[1];
                                TextView textView = new TextView(parentActivity);
                                textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                                textView.setTextSize(1, 16.0f);
                                textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
                                C371914 c371914 = new FrameLayout(parentActivity) { // from class: org.telegram.ui.Components.AlertsCreator.14
                                    final /* synthetic */ CheckBoxCell[] val$cell;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C371914(Context parentActivity2, CheckBoxCell[] checkBoxCellArr2) {
                                        super(parentActivity2);
                                        checkBoxCellArr = checkBoxCellArr2;
                                    }

                                    @Override // android.widget.FrameLayout, android.view.View
                                    public void onMeasure(int i3, int i4) {
                                        super.onMeasure(i3, i4);
                                        if (checkBoxCellArr[0] != null) {
                                            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + checkBoxCellArr[0].getMeasuredHeight() + AndroidUtilities.m1036dp(7.0f));
                                        }
                                    }
                                };
                                builder.setCustomViewOffset(6);
                                builder.setView(c371914);
                                TextView textView2 = new TextView(parentActivity2);
                                textView2.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
                                textView2.setTextSize(1, 20.0f);
                                textView2.setTypeface(AndroidUtilities.bold());
                                textView2.setLines(1);
                                textView2.setMaxLines(1);
                                textView2.setSingleLine(true);
                                textView2.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
                                textView2.setEllipsize(TextUtils.TruncateAt.END);
                                if (z2) {
                                    if (z3) {
                                        CheckBoxCell checkBoxCell = new CheckBoxCell(parentActivity2, 1, resourcesProvider);
                                        checkBoxCellArr2[0] = checkBoxCell;
                                        checkBoxCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                                        checkBoxCellArr2[0].setText(LocaleController.getString(C2797R.string.DeleteMessagesForBothSidesWherePossible), _UrlKt.FRAGMENT_ENCODE_SET, false, false);
                                        checkBoxCellArr2[0].setPadding(LocaleController.isRTL ? AndroidUtilities.m1036dp(16.0f) : AndroidUtilities.m1036dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.m1036dp(8.0f) : AndroidUtilities.m1036dp(16.0f), 0);
                                        c371914.addView(checkBoxCellArr2[0], LayoutHelper.createFrame(-1, 48.0f, 83, 0.0f, 0.0f, 0.0f, 0.0f));
                                        checkBoxCellArr2[0].setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda130
                                            @Override // android.view.View.OnClickListener
                                            public final void onClick(View view) {
                                                AlertsCreator.$r8$lambda$bFdjOO7m01QzuIJ8KbVkOK2fa5I(zArr, view);
                                            }
                                        });
                                    }
                                    textView2.setText(LocaleController.formatString("DeleteFewChatsTitle", C2797R.string.DeleteFewChatsTitle, LocaleController.formatPluralString("ChatsSelected", i2, new Object[0])));
                                    textView.setText(LocaleController.getString("AreYouSureDeleteFewChats", C2797R.string.AreYouSureDeleteFewChats));
                                } else if (i != 0) {
                                    textView2.setText(LocaleController.formatString("ClearCacheFewChatsTitle", C2797R.string.ClearCacheFewChatsTitle, LocaleController.formatPluralString("ChatsSelectedClearCache", i2, new Object[0])));
                                    textView.setText(LocaleController.getString("AreYouSureClearHistoryCacheFewChats", C2797R.string.AreYouSureClearHistoryCacheFewChats));
                                } else {
                                    textView2.setText(LocaleController.formatString("ClearFewChatsTitle", C2797R.string.ClearFewChatsTitle, LocaleController.formatPluralString("ChatsSelectedClear", i2, new Object[0])));
                                    textView.setText(LocaleController.getString("AreYouSureClearHistoryFewChats", C2797R.string.AreYouSureClearHistoryFewChats));
                                }
                                c371914.addView(textView2, LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 11.0f, 24.0f, 0.0f));
                                c371914.addView(textView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 57.0f, 24.0f, 1.0f));
                                if (z2) {
                                    string = LocaleController.getString("Delete", C2797R.string.Delete);
                                } else if (i != 0) {
                                    string = LocaleController.getString("ClearHistoryCache", C2797R.string.ClearHistoryCache);
                                } else {
                                    string = LocaleController.getString("ClearHistory", C2797R.string.ClearHistory);
                                }
                                builder.setPositiveButton(string, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda131
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i3) {
                                        AlertsCreator.m9970$r8$lambda$TixIKo_0JvcxrJ7B5HUnRnNW6I(booleanCallback, zArr, alertDialog, i3);
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString("Cancel", C2797R.string.Cancel), null);
                                AlertDialog alertDialogCreate = builder.create();
                                baseFragment.showDialog(alertDialogCreate);
                                TextView textView3 = (TextView) alertDialogCreate.getButton(-1);
                                if (textView3 != null) {
                                    textView3.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$14 */
                            public class C371914 extends FrameLayout {
                                final /* synthetic */ CheckBoxCell[] val$cell;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C371914(Context parentActivity2, CheckBoxCell[] checkBoxCellArr2) {
                                    super(parentActivity2);
                                    checkBoxCellArr = checkBoxCellArr2;
                                }

                                @Override // android.widget.FrameLayout, android.view.View
                                public void onMeasure(int i3, int i4) {
                                    super.onMeasure(i3, i4);
                                    if (checkBoxCellArr[0] != null) {
                                        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + checkBoxCellArr[0].getMeasuredHeight() + AndroidUtilities.m1036dp(7.0f));
                                    }
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$bFdjOO7m01QzuIJ8KbVkOK2fa5I(boolean[] zArr, View view) {
                                boolean z = !zArr[0];
                                zArr[0] = z;
                                ((CheckBoxCell) view).setChecked(z, true);
                            }

                            /* JADX INFO: renamed from: $r8$lambda$TixIKo_-0JvcxrJ7B5HUnRnNW6I */
                            public static /* synthetic */ void m9970$r8$lambda$TixIKo_0JvcxrJ7B5HUnRnNW6I(MessagesStorage.BooleanCallback booleanCallback, boolean[] zArr, AlertDialog alertDialog, int i) {
                                if (booleanCallback != null) {
                                    booleanCallback.run(zArr[0]);
                                }
                            }

                            /* JADX WARN: Removed duplicated region for block: B:145:0x017b  */
                            /* JADX WARN: Removed duplicated region for block: B:153:0x0191  */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public static void createClearDaysDialogAlert(org.telegram.p035ui.ActionBar.BaseFragment r26, int r27, org.telegram.tgnet.TLRPC.User r28, org.telegram.tgnet.TLRPC.Chat r29, boolean r30, final org.telegram.messenger.MessagesStorage.BooleanCallback r31, org.telegram.ui.ActionBar.Theme.ResourcesProvider r32) {
                                /*
                                    Method dump skipped, instruction units count: 598
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.AlertsCreator.createClearDaysDialogAlert(org.telegram.ui.ActionBar.BaseFragment, int, org.telegram.tgnet.TLRPC$User, org.telegram.tgnet.TLRPC$Chat, boolean, org.telegram.messenger.MessagesStorage$BooleanCallback, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$15 */
                            public class C372015 extends TextView {
                                public C372015(Context context) {
                                    super(context);
                                }

                                @Override // android.widget.TextView
                                public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
                                    super.setText(Emoji.replaceEmoji(charSequence, getPaint().getFontMetricsInt(), false), bufferType);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$16 */
                            public class C372116 extends FrameLayout {
                                final /* synthetic */ CheckBoxCell[] val$cell;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C372116(Context context, CheckBoxCell[] checkBoxCellArr) {
                                    super(context);
                                    checkBoxCellArr = checkBoxCellArr;
                                }

                                @Override // android.widget.FrameLayout, android.view.View
                                public void onMeasure(int i, int i2) {
                                    super.onMeasure(i, i2);
                                    if (checkBoxCellArr[0] != null) {
                                        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + checkBoxCellArr[0].getMeasuredHeight());
                                    }
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$CVmPVxywTsxbJu3GCs44D079DqM(boolean[] zArr, View view) {
                                boolean z = !zArr[0];
                                zArr[0] = z;
                                ((CheckBoxCell) view).setChecked(z, true);
                            }

                            public static void createCallDialogAlert(final BaseFragment baseFragment, final TLRPC.User user, final boolean z) {
                                String string;
                                String string2;
                                if (baseFragment == null || baseFragment.getParentActivity() == null || user == null || UserObject.isDeleted(user) || UserConfig.getInstance(baseFragment.getCurrentAccount()).getClientUserId() == user.f1407id) {
                                    return;
                                }
                                baseFragment.getCurrentAccount();
                                Activity parentActivity = baseFragment.getParentActivity();
                                FrameLayout frameLayout = new FrameLayout(parentActivity);
                                if (z) {
                                    string = LocaleController.getString(C2797R.string.VideoCallAlertTitle);
                                    string2 = LocaleController.formatString("VideoCallAlert", C2797R.string.VideoCallAlert, UserObject.getUserName(user));
                                } else {
                                    string = LocaleController.getString(C2797R.string.CallAlertTitle);
                                    string2 = LocaleController.formatString("CallAlert", C2797R.string.CallAlert, UserObject.getUserName(user));
                                }
                                C372217 c372217 = new TextView(parentActivity) { // from class: org.telegram.ui.Components.AlertsCreator.17
                                    public C372217(Context parentActivity2) {
                                        super(parentActivity2);
                                    }

                                    @Override // android.widget.TextView
                                    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
                                        super.setText(Emoji.replaceEmoji(charSequence, getPaint().getFontMetricsInt(), false), bufferType);
                                    }
                                };
                                NotificationCenter.listenEmojiLoading(c372217);
                                c372217.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                                c372217.setTextSize(1, 16.0f);
                                c372217.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
                                c372217.setText(AndroidUtilities.replaceTags(string2));
                                AvatarDrawable avatarDrawable = new AvatarDrawable();
                                avatarDrawable.setTextSize(AndroidUtilities.m1036dp(12.0f));
                                avatarDrawable.setScaleSize(1.0f);
                                avatarDrawable.setInfo(baseFragment.getCurrentAccount(), user);
                                BackupImageView backupImageView = new BackupImageView(parentActivity2);
                                backupImageView.setRoundRadius(ExteraConfig.getAvatarCorners(40.0f));
                                backupImageView.setForUserOrChat(user, avatarDrawable);
                                frameLayout.addView(backupImageView, LayoutHelper.createFrame(40, 40.0f, (LocaleController.isRTL ? 5 : 3) | 48, 22.0f, 5.0f, 22.0f, 0.0f));
                                TextView textView = new TextView(parentActivity2);
                                textView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
                                textView.setTextSize(1, 20.0f);
                                textView.setTypeface(AndroidUtilities.bold());
                                textView.setLines(1);
                                textView.setMaxLines(1);
                                textView.setSingleLine(true);
                                textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
                                textView.setEllipsize(TextUtils.TruncateAt.END);
                                textView.setText(string);
                                boolean z2 = LocaleController.isRTL;
                                frameLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f, (z2 ? 5 : 3) | 48, z2 ? 21 : 76, 11.0f, z2 ? 76 : 21, 0.0f));
                                frameLayout.addView(c372217, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 57.0f, 24.0f, 9.0f));
                                baseFragment.showDialog(new AlertDialog.Builder(parentActivity2).setView(frameLayout).setPositiveButton(LocaleController.getString(C2797R.string.Call), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda16
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        AlertsCreator.m9928$r8$lambda$5rjpOMz9mhmbid89qKabbGxwcc(baseFragment, user, z, alertDialog, i);
                                    }
                                }).setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null).create());
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$17 */
                            public class C372217 extends TextView {
                                public C372217(Context parentActivity2) {
                                    super(parentActivity2);
                                }

                                @Override // android.widget.TextView
                                public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
                                    super.setText(Emoji.replaceEmoji(charSequence, getPaint().getFontMetricsInt(), false), bufferType);
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$-5rjpOMz9mhmbid89qKabbGxwcc */
                            public static /* synthetic */ void m9928$r8$lambda$5rjpOMz9mhmbid89qKabbGxwcc(BaseFragment baseFragment, TLRPC.User user, boolean z, AlertDialog alertDialog, int i) {
                                TLRPC.UserFull userFull = baseFragment.getMessagesController().getUserFull(user.f1407id);
                                VoIPHelper.startCall(user, z, userFull != null && userFull.video_calls_available, baseFragment.getParentActivity(), userFull, baseFragment.getAccountInstance(), true);
                            }

                            public static void createChangeBioAlert(String str, final long j, Context context, final int i) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle(LocaleController.getString(j > 0 ? C2797R.string.UserBio : C2797R.string.DescriptionPlaceholder));
                                builder.setMessage(LocaleController.getString(j > 0 ? C2797R.string.VoipGroupBioEditAlertText : C2797R.string.DescriptionInfo));
                                FrameLayout frameLayout = new FrameLayout(context);
                                frameLayout.setClipChildren(false);
                                if (j < 0) {
                                    long j2 = -j;
                                    if (MessagesController.getInstance(i).getChatFull(j2) == null) {
                                        MessagesController.getInstance(i).loadFullChat(j2, ConnectionsManager.generateClassGuid(), true);
                                    }
                                }
                                NumberTextView numberTextView = new NumberTextView(context);
                                final EditText editText = new EditText(context);
                                int i2 = Theme.key_voipgroup_actionBarItems;
                                editText.setTextColor(Theme.getColor(i2));
                                editText.setHint(LocaleController.getString(j > 0 ? C2797R.string.UserBio : C2797R.string.DescriptionPlaceholder));
                                editText.setTextSize(1, 16.0f);
                                editText.setBackground(Theme.createEditTextDrawable(context, true));
                                editText.setMaxLines(4);
                                editText.setRawInputType(147457);
                                editText.setImeOptions(6);
                                int i3 = j > 0 ? 70 : 255;
                                editText.setFilters(new InputFilter[]{new CodepointsLengthInputFilter(i3) { // from class: org.telegram.ui.Components.AlertsCreator.18
                                    final /* synthetic */ NumberTextView val$checkTextView;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C372318(int i32, NumberTextView numberTextView2) {
                                        super(i32);
                                        numberTextView = numberTextView2;
                                    }

                                    @Override // org.telegram.p035ui.Components.CodepointsLengthInputFilter, android.text.InputFilter
                                    public CharSequence filter(CharSequence charSequence, int i4, int i5, Spanned spanned, int i6, int i7) {
                                        CharSequence charSequenceFilter = super.filter(charSequence, i4, i5, spanned, i6, i7);
                                        if (charSequenceFilter != null && charSequence != null && charSequenceFilter.length() != charSequence.length()) {
                                            VibratorUtils.vibrate();
                                            AndroidUtilities.shakeView(numberTextView);
                                        }
                                        return charSequenceFilter;
                                    }
                                }});
                                numberTextView2.setCenterAlign(true);
                                numberTextView2.setTextSize(15);
                                numberTextView2.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText4));
                                numberTextView2.setImportantForAccessibility(2);
                                frameLayout.addView(numberTextView2, LayoutHelper.createFrame(20, 20.0f, LocaleController.isRTL ? 3 : 5, 0.0f, 14.0f, 21.0f, 0.0f));
                                editText.setPadding(LocaleController.isRTL ? AndroidUtilities.m1036dp(24.0f) : 0, AndroidUtilities.m1036dp(8.0f), LocaleController.isRTL ? 0 : AndroidUtilities.m1036dp(24.0f), AndroidUtilities.m1036dp(8.0f));
                                editText.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.Components.AlertsCreator.19
                                    final /* synthetic */ NumberTextView val$checkTextView;
                                    final /* synthetic */ int val$maxSymbolsCount;

                                    @Override // android.text.TextWatcher
                                    public void beforeTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
                                    }

                                    @Override // android.text.TextWatcher
                                    public void onTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
                                    }

                                    public C372419(int i32, NumberTextView numberTextView2) {
                                        i = i32;
                                        numberTextView = numberTextView2;
                                    }

                                    @Override // android.text.TextWatcher
                                    public void afterTextChanged(Editable editable) {
                                        int iCodePointCount = i - Character.codePointCount(editable, 0, editable.length());
                                        NumberTextView numberTextView2 = numberTextView;
                                        if (iCodePointCount < 30) {
                                            numberTextView2.setNumber(iCodePointCount, numberTextView2.getVisibility() == 0);
                                            AndroidUtilities.updateViewVisibilityAnimated(numberTextView, true);
                                        } else {
                                            AndroidUtilities.updateViewVisibilityAnimated(numberTextView2, false);
                                        }
                                    }
                                });
                                AndroidUtilities.updateViewVisibilityAnimated(numberTextView2, false, 0.0f, false);
                                editText.setText(str);
                                editText.setSelection(editText.getText().toString().length());
                                builder.setView(frameLayout);
                                final AlertDialog.OnButtonClickListener onButtonClickListener = new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda207
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i4) {
                                        AlertsCreator.$r8$lambda$sFXULotiTcxxiTdDYpY1bMdW_XQ(j, i, editText, alertDialog, i4);
                                    }
                                };
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Save), onButtonClickListener);
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                builder.setOnPreDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda208
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        AndroidUtilities.hideKeyboard(editText);
                                    }
                                });
                                frameLayout.addView(editText, LayoutHelper.createFrame(-1, -2.0f, 0, 23.0f, 12.0f, 23.0f, 21.0f));
                                editText.requestFocus();
                                AndroidUtilities.showKeyboard(editText);
                                final AlertDialog alertDialogCreate = builder.create();
                                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda209
                                    @Override // android.widget.TextView.OnEditorActionListener
                                    public final boolean onEditorAction(TextView textView, int i4, KeyEvent keyEvent) {
                                        return AlertsCreator.$r8$lambda$GtxmQA5g0JCoSdqUXczG8Ly0M8A(j, alertDialogCreate, onButtonClickListener, textView, i4, keyEvent);
                                    }
                                });
                                alertDialogCreate.setBackgroundColor(Theme.getColor(Theme.key_voipgroup_dialogBackground));
                                alertDialogCreate.show();
                                alertDialogCreate.setTextColor(Theme.getColor(i2));
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$18 */
                            public class C372318 extends CodepointsLengthInputFilter {
                                final /* synthetic */ NumberTextView val$checkTextView;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C372318(int i32, NumberTextView numberTextView2) {
                                    super(i32);
                                    numberTextView = numberTextView2;
                                }

                                @Override // org.telegram.p035ui.Components.CodepointsLengthInputFilter, android.text.InputFilter
                                public CharSequence filter(CharSequence charSequence, int i4, int i5, Spanned spanned, int i6, int i7) {
                                    CharSequence charSequenceFilter = super.filter(charSequence, i4, i5, spanned, i6, i7);
                                    if (charSequenceFilter != null && charSequence != null && charSequenceFilter.length() != charSequence.length()) {
                                        VibratorUtils.vibrate();
                                        AndroidUtilities.shakeView(numberTextView);
                                    }
                                    return charSequenceFilter;
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$19 */
                            public class C372419 implements TextWatcher {
                                final /* synthetic */ NumberTextView val$checkTextView;
                                final /* synthetic */ int val$maxSymbolsCount;

                                @Override // android.text.TextWatcher
                                public void beforeTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
                                }

                                @Override // android.text.TextWatcher
                                public void onTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
                                }

                                public C372419(int i32, NumberTextView numberTextView2) {
                                    i = i32;
                                    numberTextView = numberTextView2;
                                }

                                @Override // android.text.TextWatcher
                                public void afterTextChanged(Editable editable) {
                                    int iCodePointCount = i - Character.codePointCount(editable, 0, editable.length());
                                    NumberTextView numberTextView2 = numberTextView;
                                    if (iCodePointCount < 30) {
                                        numberTextView2.setNumber(iCodePointCount, numberTextView2.getVisibility() == 0);
                                        AndroidUtilities.updateViewVisibilityAnimated(numberTextView, true);
                                    } else {
                                        AndroidUtilities.updateViewVisibilityAnimated(numberTextView2, false);
                                    }
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$sFXULotiTcxxiTdDYpY1bMdW_XQ(long j, int i, EditText editText, AlertDialog alertDialog, int i2) {
                                String str = _UrlKt.FRAGMENT_ENCODE_SET;
                                if (j > 0) {
                                    TLRPC.UserFull userFull = MessagesController.getInstance(i).getUserFull(UserConfig.getInstance(i).getClientUserId());
                                    String strTrim = editText.getText().toString().replace("\n", " ").replaceAll(" +", " ").trim();
                                    if (userFull != null) {
                                        String str2 = userFull.about;
                                        if (str2 != null) {
                                            str = str2;
                                        }
                                        if (str.equals(strTrim)) {
                                            AndroidUtilities.hideKeyboard(editText);
                                            alertDialog.dismiss();
                                            return;
                                        } else {
                                            userFull.about = strTrim;
                                            NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.userInfoDidLoad, Long.valueOf(j), userFull);
                                        }
                                    }
                                    TL_account.updateProfile updateprofile = new TL_account.updateProfile();
                                    updateprofile.about = strTrim;
                                    updateprofile.flags |= 4;
                                    NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.showBulletin, 2, Long.valueOf(j));
                                    ConnectionsManager.getInstance(i).sendRequest(updateprofile, new RequestDelegate() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda251
                                        @Override // org.telegram.tgnet.RequestDelegate
                                        public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                                            AlertsCreator.$r8$lambda$QyQAG5qAoSCkQua5TZfArwY9NCk(tLObject, tL_error);
                                        }
                                    }, 2);
                                } else {
                                    long j2 = -j;
                                    TLRPC.ChatFull chatFull = MessagesController.getInstance(i).getChatFull(j2);
                                    String string = editText.getText().toString();
                                    if (chatFull != null) {
                                        String str3 = chatFull.about;
                                        if (str3 != null) {
                                            str = str3;
                                        }
                                        if (str.equals(string)) {
                                            AndroidUtilities.hideKeyboard(editText);
                                            alertDialog.dismiss();
                                            return;
                                        } else {
                                            chatFull.about = string;
                                            NotificationCenter notificationCenter = NotificationCenter.getInstance(i);
                                            int i3 = NotificationCenter.chatInfoDidLoad;
                                            Boolean bool = Boolean.FALSE;
                                            notificationCenter.lambda$postNotificationNameOnUIThread$1(i3, chatFull, 0, bool, bool);
                                        }
                                    }
                                    NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.showBulletin, 2, Long.valueOf(j));
                                    MessagesController.getInstance(i).updateChatAbout(j2, string, chatFull);
                                }
                                alertDialog.dismiss();
                            }

                            public static /* synthetic */ boolean $r8$lambda$GtxmQA5g0JCoSdqUXczG8Ly0M8A(long j, AlertDialog alertDialog, AlertDialog.OnButtonClickListener onButtonClickListener, TextView textView, int i, KeyEvent keyEvent) {
                                if ((i != 6 && (j <= 0 || keyEvent.getKeyCode() != 66)) || !alertDialog.isShowing()) {
                                    return false;
                                }
                                onButtonClickListener.onClick(alertDialog, 0);
                                return true;
                            }

                            public static void createChangeNameAlert(final long j, Context context, final int i) {
                                String str;
                                String str2;
                                final EditText editText;
                                if (DialogObject.isUserDialog(j)) {
                                    TLRPC.User user = MessagesController.getInstance(i).getUser(Long.valueOf(j));
                                    str = user.first_name;
                                    str2 = user.last_name;
                                } else {
                                    str = MessagesController.getInstance(i).getChat(Long.valueOf(-j)).title;
                                    str2 = null;
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle(LocaleController.getString(j > 0 ? C2797R.string.VoipEditName : C2797R.string.VoipEditTitle));
                                LinearLayout linearLayout = new LinearLayout(context);
                                linearLayout.setOrientation(1);
                                final EditText editText2 = new EditText(context);
                                int i2 = Theme.key_voipgroup_actionBarItems;
                                editText2.setTextColor(Theme.getColor(i2));
                                editText2.setTextSize(1, 16.0f);
                                editText2.setMaxLines(1);
                                editText2.setLines(1);
                                editText2.setSingleLine(true);
                                editText2.setGravity(LocaleController.isRTL ? 5 : 3);
                                editText2.setInputType(49152);
                                editText2.setImeOptions(j > 0 ? 5 : 6);
                                editText2.setHint(LocaleController.getString(j > 0 ? C2797R.string.FirstName : C2797R.string.VoipEditTitleHint));
                                editText2.setBackground(Theme.createEditTextDrawable(context, true));
                                editText2.setPadding(0, AndroidUtilities.m1036dp(8.0f), 0, AndroidUtilities.m1036dp(8.0f));
                                editText2.requestFocus();
                                if (j > 0) {
                                    editText = new EditText(context);
                                    editText.setTextColor(Theme.getColor(i2));
                                    editText.setTextSize(1, 16.0f);
                                    editText.setMaxLines(1);
                                    editText.setLines(1);
                                    editText.setSingleLine(true);
                                    editText.setGravity(LocaleController.isRTL ? 5 : 3);
                                    editText.setInputType(49152);
                                    editText.setImeOptions(6);
                                    editText.setHint(LocaleController.getString(C2797R.string.LastName));
                                    editText.setBackground(Theme.createEditTextDrawable(context, true));
                                    editText.setPadding(0, AndroidUtilities.m1036dp(8.0f), 0, AndroidUtilities.m1036dp(8.0f));
                                } else {
                                    editText = null;
                                }
                                AndroidUtilities.showKeyboard(editText2);
                                linearLayout.addView(editText2, LayoutHelper.createLinear(-1, -2, 0, 23, 12, 23, 21));
                                if (editText != null) {
                                    linearLayout.addView(editText, LayoutHelper.createLinear(-1, -2, 0, 23, 12, 23, 21));
                                }
                                editText2.setText(str);
                                editText2.setSelection(editText2.getText().toString().length());
                                if (editText != null) {
                                    editText.setText(str2);
                                    editText.setSelection(editText.getText().toString().length());
                                }
                                builder.setView(linearLayout);
                                final AlertDialog.OnButtonClickListener onButtonClickListener = new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda203
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i3) {
                                        AlertsCreator.m10000$r8$lambda$rA3v4ORzzlXWhTmzoJfmmuNW34(editText2, j, i, editText, alertDialog, i3);
                                    }
                                };
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Save), onButtonClickListener);
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                builder.setOnPreDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda204
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        AlertsCreator.$r8$lambda$HwNCc8irpAO_z45Fv7VXBXkNnjk(editText2, editText, dialogInterface);
                                    }
                                });
                                final AlertDialog alertDialogCreate = builder.create();
                                alertDialogCreate.setBackgroundColor(Theme.getColor(Theme.key_voipgroup_dialogBackground));
                                alertDialogCreate.show();
                                alertDialogCreate.setTextColor(Theme.getColor(i2));
                                TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda205
                                    @Override // android.widget.TextView.OnEditorActionListener
                                    public final boolean onEditorAction(TextView textView, int i3, KeyEvent keyEvent) {
                                        return AlertsCreator.$r8$lambda$_XBjOr3ZDUTPANB_D5PZO4vXj8g(alertDialogCreate, onButtonClickListener, textView, i3, keyEvent);
                                    }
                                };
                                if (editText != null) {
                                    editText.setOnEditorActionListener(onEditorActionListener);
                                } else {
                                    editText2.setOnEditorActionListener(onEditorActionListener);
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$rA3v4ORzzl-XWhTmzoJfmmuNW34 */
                            public static /* synthetic */ void m10000$r8$lambda$rA3v4ORzzlXWhTmzoJfmmuNW34(EditText editText, long j, int i, EditText editText2, AlertDialog alertDialog, int i2) {
                                if (editText.getText() == null) {
                                    return;
                                }
                                if (j > 0) {
                                    TLRPC.User user = MessagesController.getInstance(i).getUser(Long.valueOf(j));
                                    String string = editText.getText().toString();
                                    String string2 = editText2.getText().toString();
                                    String str = user.first_name;
                                    String str2 = user.last_name;
                                    if (str == null) {
                                        str = _UrlKt.FRAGMENT_ENCODE_SET;
                                    }
                                    if (str2 == null) {
                                        str2 = _UrlKt.FRAGMENT_ENCODE_SET;
                                    }
                                    if (str.equals(string) && str2.equals(string2)) {
                                        alertDialog.dismiss();
                                        return;
                                    }
                                    TL_account.updateProfile updateprofile = new TL_account.updateProfile();
                                    updateprofile.flags = 3;
                                    updateprofile.first_name = string;
                                    user.first_name = string;
                                    updateprofile.last_name = string2;
                                    user.last_name = string2;
                                    TLRPC.User user2 = MessagesController.getInstance(i).getUser(Long.valueOf(UserConfig.getInstance(i).getClientUserId()));
                                    if (user2 != null) {
                                        user2.first_name = updateprofile.first_name;
                                        user2.last_name = updateprofile.last_name;
                                    }
                                    UserConfig.getInstance(i).saveConfig(true);
                                    NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
                                    NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_NAME));
                                    ConnectionsManager.getInstance(i).sendRequest(updateprofile, new RequestDelegate() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda241
                                        @Override // org.telegram.tgnet.RequestDelegate
                                        public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                                            AlertsCreator.$r8$lambda$tp8ZIg7_hiaR_9puUNHqcuNKTA8(tLObject, tL_error);
                                        }
                                    });
                                    NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.showBulletin, 3, Long.valueOf(j));
                                } else {
                                    long j2 = -j;
                                    TLRPC.Chat chat = MessagesController.getInstance(i).getChat(Long.valueOf(j2));
                                    String string3 = editText.getText().toString();
                                    String str3 = chat.title;
                                    if (str3 != null && str3.equals(string3)) {
                                        alertDialog.dismiss();
                                        return;
                                    }
                                    chat.title = string3;
                                    NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_CHAT_NAME));
                                    MessagesController.getInstance(i).changeChatTitle(j2, string3);
                                    NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.showBulletin, 3, Long.valueOf(j));
                                }
                                alertDialog.dismiss();
                            }

                            public static /* synthetic */ void $r8$lambda$HwNCc8irpAO_z45Fv7VXBXkNnjk(EditText editText, EditText editText2, DialogInterface dialogInterface) {
                                AndroidUtilities.hideKeyboard(editText);
                                AndroidUtilities.hideKeyboard(editText2);
                            }

                            public static /* synthetic */ boolean $r8$lambda$_XBjOr3ZDUTPANB_D5PZO4vXj8g(AlertDialog alertDialog, AlertDialog.OnButtonClickListener onButtonClickListener, TextView textView, int i, KeyEvent keyEvent) {
                                if ((i != 6 && keyEvent.getKeyCode() != 66) || !alertDialog.isShowing()) {
                                    return false;
                                }
                                onButtonClickListener.onClick(alertDialog, 0);
                                return true;
                            }

                            public static void showChatWithAdmin(BaseFragment baseFragment, TLRPC.User user, String str, boolean z, int i) {
                                if (baseFragment.getParentActivity() == null) {
                                    return;
                                }
                                BottomSheet.Builder builder = new BottomSheet.Builder(baseFragment.getParentActivity());
                                builder.setTitle(LocaleController.getString(z ? C2797R.string.ChatWithAdminChannelTitle : C2797R.string.ChatWithAdminGroupTitle), true);
                                LinearLayout linearLayout = new LinearLayout(baseFragment.getParentActivity());
                                linearLayout.setOrientation(1);
                                TextView textView = new TextView(baseFragment.getParentActivity());
                                linearLayout.addView(textView, LayoutHelper.createLinear(-1, -1, 0, 21, 0, 21, 8));
                                textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                                textView.setTextSize(1, 16.0f);
                                textView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("ChatWithAdminMessage", C2797R.string.ChatWithAdminMessage, str, LocaleController.formatDateAudio(i, false))));
                                TextView textView2 = new TextView(baseFragment.getParentActivity());
                                textView2.setPadding(AndroidUtilities.m1036dp(34.0f), 0, AndroidUtilities.m1036dp(34.0f), 0);
                                textView2.setGravity(17);
                                textView2.setTextSize(1, 14.0f);
                                textView2.setTypeface(AndroidUtilities.bold());
                                textView2.setText(LocaleController.getString(C2797R.string.IUnderstand));
                                textView2.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText));
                                textView2.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(8.0f), Theme.getColor(Theme.key_featuredStickers_addButton), Theme.getColor(Theme.key_featuredStickers_addButtonPressed)));
                                linearLayout.addView(textView2, LayoutHelper.createLinear(-1, 48, 0, 16, 12, 16, 8));
                                builder.setCustomView(linearLayout);
                                final BottomSheet bottomSheetShow = builder.show();
                                textView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda78
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        bottomSheetShow.lambda$new$0();
                                    }
                                });
                            }

                            public static void createContactInviteDialog(final BaseFragment baseFragment, String str, String str2, final String str3) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity());
                                builder.setTitle(LocaleController.getString(C2797R.string.ContactNotRegisteredTitle));
                                builder.setMessage(LocaleController.formatString("ContactNotRegistered", C2797R.string.ContactNotRegistered, ContactsController.formatName(str, str2)));
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Invite), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda159
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        AlertsCreator.m9997$r8$lambda$ltAkgOgfDfnzIblxuv14QHrhAg(str3, baseFragment, alertDialog, i);
                                    }
                                });
                                baseFragment.showDialog(builder.create());
                            }

                            /* JADX INFO: renamed from: $r8$lambda$ltAkgOgfDfnzIblxuv14-QHrhAg */
                            public static /* synthetic */ void m9997$r8$lambda$ltAkgOgfDfnzIblxuv14QHrhAg(String str, BaseFragment baseFragment, AlertDialog alertDialog, int i) {
                                try {
                                    Intent intent = new Intent("android.intent.action.VIEW", Uri.fromParts("sms", str, null));
                                    intent.putExtra("sms_body", ContactsController.getInstance(baseFragment.getCurrentAccount()).getInviteText(1));
                                    baseFragment.getParentActivity().startActivityForResult(intent, 500);
                                } catch (Exception e) {
                                    FileLog.m1048e(e);
                                }
                            }

                            public static ActionBarPopupWindow createSimplePopup(BaseFragment baseFragment, View view, View view2, float f, float f2) {
                                if (baseFragment == null || view2 == null || view == null) {
                                    return null;
                                }
                                ActionBarPopupWindow actionBarPopupWindow = new ActionBarPopupWindow(view, -2, -2);
                                actionBarPopupWindow.setPauseNotifications(true);
                                actionBarPopupWindow.setDismissAnimationDuration(Opcodes.REM_INT_LIT8);
                                actionBarPopupWindow.setOutsideTouchable(true);
                                actionBarPopupWindow.setClippingEnabled(true);
                                actionBarPopupWindow.setAnimationStyle(C2797R.style.PopupContextAnimation);
                                actionBarPopupWindow.setFocusable(true);
                                view.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(1000.0f), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(1000.0f), Integer.MIN_VALUE));
                                actionBarPopupWindow.setInputMethodMode(2);
                                actionBarPopupWindow.getContentView().setFocusableInTouchMode(true);
                                float x = 0.0f;
                                View view3 = view2;
                                float y = 0.0f;
                                while (view3 != view2.getRootView()) {
                                    x += view3.getX();
                                    y += view3.getY();
                                    view3 = (View) view3.getParent();
                                    if (view3 == null) {
                                        break;
                                    }
                                }
                                actionBarPopupWindow.showAtLocation(view2.getRootView(), 0, (int) ((x + f) - (view.getMeasuredWidth() / 2.0f)), (int) ((y + f2) - (view.getMeasuredHeight() / 2.0f)));
                                actionBarPopupWindow.dimBehind();
                                return actionBarPopupWindow;
                            }

                            public static void checkRestrictedInviteUsers(final int i, final TLRPC.Chat chat, TLRPC.TL_messages_invitedUsers tL_messages_invitedUsers) {
                                TLRPC.User user;
                                if (tL_messages_invitedUsers == null || tL_messages_invitedUsers.missing_invitees.isEmpty() || chat == null) {
                                    return;
                                }
                                final ArrayList arrayList = new ArrayList();
                                final ArrayList arrayList2 = new ArrayList();
                                final ArrayList arrayList3 = new ArrayList();
                                ArrayList<TLRPC.TL_missingInvitee> arrayList4 = tL_messages_invitedUsers.missing_invitees;
                                int size = arrayList4.size();
                                int i2 = 0;
                                while (i2 < size) {
                                    TLRPC.TL_missingInvitee tL_missingInvitee = arrayList4.get(i2);
                                    i2++;
                                    TLRPC.TL_missingInvitee tL_missingInvitee2 = tL_missingInvitee;
                                    if (tL_messages_invitedUsers.updates != null) {
                                        for (int i3 = 0; i3 < tL_messages_invitedUsers.updates.users.size(); i3++) {
                                            user = tL_messages_invitedUsers.updates.users.get(i3);
                                            if (user.f1407id == tL_missingInvitee2.user_id) {
                                                break;
                                            }
                                        }
                                        user = null;
                                    } else {
                                        user = null;
                                    }
                                    if (user == null) {
                                        user = MessagesController.getInstance(i).getUser(Long.valueOf(tL_missingInvitee2.user_id));
                                    }
                                    if (user != null) {
                                        arrayList.add(user);
                                        if (tL_missingInvitee2.premium_required_for_pm) {
                                            arrayList2.add(Long.valueOf(user.f1407id));
                                        }
                                        if (tL_missingInvitee2.premium_would_allow_invite) {
                                            arrayList3.add(Long.valueOf(user.f1407id));
                                        }
                                    }
                                }
                                if (arrayList.isEmpty()) {
                                    return;
                                }
                                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda4
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AlertsCreator.m9956$r8$lambda$Jf591mrS4ZZHllNVvVQjoSN4k(i, chat, arrayList, arrayList2, arrayList3);
                                    }
                                }, 200L);
                            }

                            /* JADX INFO: renamed from: $r8$lambda$Jf591mrS4ZZHll-NVvV-QjoSN4k */
                            public static /* synthetic */ void m9956$r8$lambda$Jf591mrS4ZZHllNVvVQjoSN4k(int i, TLRPC.Chat chat, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3) {
                                BaseFragment lastFragment;
                                if (!LaunchActivity.isActive || (lastFragment = LaunchActivity.getLastFragment()) == null || lastFragment.getParentActivity() == null) {
                                    return;
                                }
                                LimitReachedBottomSheet limitReachedBottomSheet = new LimitReachedBottomSheet(lastFragment, lastFragment.getParentActivity(), 11, i, null);
                                limitReachedBottomSheet.setRestrictedUsers(chat, arrayList, arrayList2, arrayList3, null);
                                limitReachedBottomSheet.show();
                            }

                            public static void createBlockDialogAlert(BaseFragment baseFragment, int i, boolean z, TLRPC.User user, final BlockDialogCallback blockDialogCallback) {
                                String string;
                                if (baseFragment == null || baseFragment.getParentActivity() == null) {
                                    return;
                                }
                                if (i == 1 && user == null) {
                                    return;
                                }
                                Activity parentActivity = baseFragment.getParentActivity();
                                AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                                CheckBoxCell[] checkBoxCellArr = new CheckBoxCell[2];
                                LinearLayout linearLayout = new LinearLayout(parentActivity);
                                linearLayout.setOrientation(1);
                                builder.setView(linearLayout);
                                if (i != 1) {
                                    builder.setTitle(LocaleController.formatString("BlockUserTitle", C2797R.string.BlockUserTitle, LocaleController.formatPluralString("UsersCountTitle", i, new Object[0])));
                                    string = LocaleController.getString(C2797R.string.BlockUsers);
                                    builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("BlockUsersMessage", C2797R.string.BlockUsersMessage, LocaleController.formatPluralString("UsersCount", i, new Object[0]))));
                                } else {
                                    String name = ContactsController.formatName(user.first_name, user.last_name);
                                    builder.setTitle(LocaleController.formatString("BlockUserTitle", C2797R.string.BlockUserTitle, name));
                                    string = LocaleController.getString(C2797R.string.BlockUser);
                                    builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("BlockUserMessage", C2797R.string.BlockUserMessage, name)));
                                }
                                final boolean[] zArr = {true, true};
                                for (final int i2 = 0; i2 < 2; i2++) {
                                    if (i2 != 0 || z) {
                                        CheckBoxCell checkBoxCell = new CheckBoxCell(parentActivity, 1);
                                        checkBoxCellArr[i2] = checkBoxCell;
                                        checkBoxCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                                        if (i2 == 0) {
                                            checkBoxCellArr[i2].setText(LocaleController.getString(C2797R.string.ReportSpamTitle), _UrlKt.FRAGMENT_ENCODE_SET, true, false);
                                        } else {
                                            checkBoxCellArr[i2].setText(LocaleController.getString(i == 1 ? C2797R.string.DeleteThisChatBothSides : C2797R.string.DeleteTheseChatsBothSides), _UrlKt.FRAGMENT_ENCODE_SET, true, false);
                                        }
                                        checkBoxCellArr[i2].setPadding(LocaleController.isRTL ? AndroidUtilities.m1036dp(16.0f) : AndroidUtilities.m1036dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.m1036dp(8.0f) : AndroidUtilities.m1036dp(16.0f), 0);
                                        linearLayout.addView(checkBoxCellArr[i2], LayoutHelper.createLinear(-1, 48));
                                        checkBoxCellArr[i2].setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda150
                                            @Override // android.view.View.OnClickListener
                                            public final void onClick(View view) {
                                                AlertsCreator.m9966$r8$lambda$PiINdf7nMBVsq9A41rTWwW3wIM(zArr, i2, view);
                                            }
                                        });
                                    }
                                }
                                builder.setPositiveButton(string, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda151
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i3) {
                                        AlertsCreator.BlockDialogCallback blockDialogCallback2 = blockDialogCallback;
                                        boolean[] zArr2 = zArr;
                                        blockDialogCallback2.run(zArr2[0], zArr2[1]);
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                AlertDialog alertDialogCreate = builder.create();
                                baseFragment.showDialog(alertDialogCreate);
                                TextView textView = (TextView) alertDialogCreate.getButton(-1);
                                if (textView != null) {
                                    textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$PiINdf7nMBVsq9A-41rTWwW3wIM */
                            public static /* synthetic */ void m9966$r8$lambda$PiINdf7nMBVsq9A41rTWwW3wIM(boolean[] zArr, int i, View view) {
                                boolean z = !zArr[i];
                                zArr[i] = z;
                                ((CheckBoxCell) view).setChecked(z, true);
                            }

                            public static BottomSheet createTimePickerDialog(Context context, String str, final int i, final int i2, final int i3, final Utilities.Callback<Integer> callback) {
                                if (context == null) {
                                    return null;
                                }
                                ScheduleDatePickerColors scheduleDatePickerColors = new ScheduleDatePickerColors();
                                BottomSheet.Builder builder = new BottomSheet.Builder(context, false, null);
                                builder.setApplyBottomPadding(false);
                                final C372622 c372622 = new NumberPicker(context) { // from class: org.telegram.ui.Components.AlertsCreator.22
                                    public C372622(Context context2) {
                                        super(context2);
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i4) {
                                        return LocaleController.formatPluralString("Hours", i4, new Object[0]);
                                    }
                                };
                                final C372723 c372723 = new LinearLayout(context2) { // from class: org.telegram.ui.Components.AlertsCreator.23
                                    private Text ampmText;
                                    private boolean isAM;
                                    private final Text separatorText = new Text(":", 18.0f);
                                    final /* synthetic */ NumberPicker val$hourPicker;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C372723(Context context2, final NumberPicker c3726222) {
                                        super(context2);
                                        numberPicker = c3726222;
                                        this.separatorText = new Text(":", 18.0f);
                                    }

                                    @Override // android.view.ViewGroup, android.view.View
                                    public void dispatchDraw(Canvas canvas) {
                                        int i4 = Theme.key_windowBackgroundWhiteBlackText;
                                        this.separatorText.draw(canvas, (getWidth() - this.separatorText.getCurrentWidth()) / 2.0f, getHeight() / 2.0f, Theme.getColor(i4), 1.0f);
                                        if (!LocaleController.is24HourFormat) {
                                            boolean z = numberPicker.getValue() % 24 < 12;
                                            if (this.isAM != z || this.ampmText == null) {
                                                this.isAM = z;
                                                this.ampmText = new Text(z ? "AM" : "PM", 18.0f);
                                            }
                                            this.ampmText.draw(canvas, (getWidth() / 2.0f) + AndroidUtilities.m1036dp(43.0f), (getHeight() / 2.0f) + AndroidUtilities.m1036dp(1.0f), Theme.getColor(i4), 1.0f);
                                        }
                                        super.dispatchDraw(canvas);
                                    }
                                };
                                c372723.setOrientation(0);
                                c372723.setWeightSum(1.0f);
                                c3726222.setAllItemsCount(24);
                                c3726222.setItemCount(5);
                                c3726222.setTextColor(scheduleDatePickerColors.textColor);
                                c3726222.setGravity(5);
                                c3726222.setTextOffset(-AndroidUtilities.m1036dp(12.0f));
                                final C372824 c372824 = new NumberPicker(context2) { // from class: org.telegram.ui.Components.AlertsCreator.24
                                    public C372824(Context context2) {
                                        super(context2);
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i4) {
                                        return LocaleController.formatPluralString("Minutes", i4, new Object[0]);
                                    }
                                };
                                c372824.setWrapSelectorWheel(true);
                                c372824.setAllItemsCount(60);
                                c372824.setItemCount(5);
                                c372824.setTextColor(scheduleDatePickerColors.textColor);
                                c372824.setGravity(3);
                                c372824.setTextOffset(AndroidUtilities.m1036dp(12.0f));
                                final Utilities.Callback callback2 = new Utilities.Callback() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda243
                                    @Override // org.telegram.messenger.Utilities.Callback
                                    public final void run(Object obj) {
                                        AlertsCreator.m9973$r8$lambda$VhgLIx6KxDJkmo4JL6IrlQoUxg(i2, i3, c3726222, c372824, i, c372723, (Boolean) obj);
                                    }
                                };
                                c372723.addView(c3726222, LayoutHelper.createLinear(0, 270, 0.5f));
                                c3726222.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda244
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i4) {
                                        return AlertsCreator.$r8$lambda$gsAGb0m6JrJmfNQJCki2DEsYwEc(i4);
                                    }
                                });
                                c3726222.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda245
                                    @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                                    public final void onValueChange(NumberPicker numberPicker, int i4, int i5) {
                                        callback2.run(Boolean.TRUE);
                                    }
                                });
                                c372723.addView(c372824, LayoutHelper.createLinear(0, 270, 0.5f));
                                c372824.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda246
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i4) {
                                        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i4));
                                    }
                                });
                                c372824.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda247
                                    @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                                    public final void onValueChange(NumberPicker numberPicker, int i4, int i5) {
                                        callback2.run(Boolean.TRUE);
                                    }
                                });
                                callback2.run(Boolean.FALSE);
                                C372925 c372925 = new LinearLayout(context2) { // from class: org.telegram.ui.Components.AlertsCreator.25
                                    boolean ignoreLayout = false;
                                    final /* synthetic */ NumberPicker val$hourPicker;
                                    final /* synthetic */ NumberPicker val$minutePicker;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C372925(Context context2, final NumberPicker c3726222, final NumberPicker c3728242) {
                                        super(context2);
                                        numberPicker = c3726222;
                                        numberPicker = c3728242;
                                        this.ignoreLayout = false;
                                    }

                                    @Override // android.widget.LinearLayout, android.view.View
                                    public void onMeasure(int i4, int i5) {
                                        this.ignoreLayout = true;
                                        Point point = AndroidUtilities.displaySize;
                                        int i6 = point.x > point.y ? 3 : 5;
                                        numberPicker.setItemCount(i6);
                                        numberPicker.setItemCount(i6);
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i6;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i6;
                                        this.ignoreLayout = false;
                                        super.onMeasure(i4, i5);
                                    }

                                    @Override // android.view.View, android.view.ViewParent
                                    public void requestLayout() {
                                        if (this.ignoreLayout) {
                                            return;
                                        }
                                        super.requestLayout();
                                    }
                                };
                                c372925.setOrientation(1);
                                FrameLayout frameLayout = new FrameLayout(context2);
                                TextView textView = new TextView(context2);
                                textView.setText(str);
                                textView.setTextColor(scheduleDatePickerColors.textColor);
                                textView.setTextSize(1, 20.0f);
                                textView.setTypeface(AndroidUtilities.bold());
                                frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
                                textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda248
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        return AlertsCreator.$r8$lambda$FZD8eC92_56tRWdS_pwLtsrzNfU(view, motionEvent);
                                    }
                                });
                                c372925.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
                                c372925.addView(c372723, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
                                ButtonWithCounterView round = new ButtonWithCounterView(context2, null).setRound();
                                round.setText(LocaleController.getString(C2797R.string.Select), false);
                                round.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda249
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        bottomSheetArr[0].lambda$new$0();
                                    }
                                });
                                c372925.addView(round, LayoutHelper.createLinear(-1, 48, 0, 16, 12, 16, 12));
                                builder.setCustomView(c372925);
                                BottomSheet bottomSheetShow = builder.show();
                                bottomSheetShow.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda250
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        callback.run(Integer.valueOf((c3726222.getValue() * 60) + c3728242.getValue()));
                                    }
                                });
                                bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
                                bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
                                BottomSheet bottomSheetCreate = builder.create();
                                final BottomSheet[] bottomSheetArr = {bottomSheetCreate};
                                return bottomSheetCreate;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$22 */
                            public class C372622 extends NumberPicker {
                                public C372622(Context context2) {
                                    super(context2);
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i4) {
                                    return LocaleController.formatPluralString("Hours", i4, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$23 */
                            public class C372723 extends LinearLayout {
                                private Text ampmText;
                                private boolean isAM;
                                private final Text separatorText = new Text(":", 18.0f);
                                final /* synthetic */ NumberPicker val$hourPicker;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C372723(Context context2, final NumberPicker c3726222) {
                                    super(context2);
                                    numberPicker = c3726222;
                                    this.separatorText = new Text(":", 18.0f);
                                }

                                @Override // android.view.ViewGroup, android.view.View
                                public void dispatchDraw(Canvas canvas) {
                                    int i4 = Theme.key_windowBackgroundWhiteBlackText;
                                    this.separatorText.draw(canvas, (getWidth() - this.separatorText.getCurrentWidth()) / 2.0f, getHeight() / 2.0f, Theme.getColor(i4), 1.0f);
                                    if (!LocaleController.is24HourFormat) {
                                        boolean z = numberPicker.getValue() % 24 < 12;
                                        if (this.isAM != z || this.ampmText == null) {
                                            this.isAM = z;
                                            this.ampmText = new Text(z ? "AM" : "PM", 18.0f);
                                        }
                                        this.ampmText.draw(canvas, (getWidth() / 2.0f) + AndroidUtilities.m1036dp(43.0f), (getHeight() / 2.0f) + AndroidUtilities.m1036dp(1.0f), Theme.getColor(i4), 1.0f);
                                    }
                                    super.dispatchDraw(canvas);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$24 */
                            public class C372824 extends NumberPicker {
                                public C372824(Context context2) {
                                    super(context2);
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i4) {
                                    return LocaleController.formatPluralString("Minutes", i4, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$VhgLIx6KxDJkmo4JL6Irl-QoUxg */
                            public static /* synthetic */ void m9973$r8$lambda$VhgLIx6KxDJkmo4JL6IrlQoUxg(int i, int i2, NumberPicker numberPicker, NumberPicker numberPicker2, int i3, LinearLayout linearLayout, Boolean bool) {
                                int minValue;
                                int value;
                                int i4 = i % 60;
                                int i5 = (i - i4) / 60;
                                int i6 = i2 % 60;
                                int i7 = (i2 - i6) / 60;
                                if (i6 == 0 && i7 > 0) {
                                    i7--;
                                    i6 = 59;
                                }
                                if (bool.booleanValue()) {
                                    value = numberPicker.getValue();
                                    minValue = numberPicker2.getValue();
                                } else {
                                    minValue = i3 % 60;
                                    value = (i3 - minValue) / 60;
                                    if (value == 24) {
                                        value--;
                                        minValue = 59;
                                    }
                                }
                                numberPicker.setMinValue(i5);
                                numberPicker.setMaxValue(i7);
                                if (value > i7) {
                                    numberPicker.setValue(i7);
                                    value = i7;
                                } else if (value < i5) {
                                    numberPicker.setValue(i5);
                                    value = i5;
                                }
                                if (value <= i5) {
                                    numberPicker2.setMinValue(i4);
                                    numberPicker2.setMaxValue(i5 == i7 ? i6 : 59);
                                } else if (value >= i7) {
                                    if (i5 != i7) {
                                        i4 = 0;
                                    }
                                    numberPicker2.setMinValue(i4);
                                    numberPicker2.setMaxValue(i6);
                                } else if (i5 == i7) {
                                    numberPicker2.setMinValue(i4);
                                    numberPicker2.setMaxValue(i6);
                                } else {
                                    numberPicker2.setMinValue(0);
                                    numberPicker2.setMaxValue(59);
                                }
                                if (minValue > numberPicker2.getMaxValue()) {
                                    minValue = numberPicker2.getMaxValue();
                                    numberPicker2.setValue(minValue);
                                } else if (minValue < numberPicker2.getMinValue()) {
                                    minValue = numberPicker2.getMinValue();
                                    numberPicker2.setValue(minValue);
                                }
                                if (!bool.booleanValue()) {
                                    numberPicker.setValue(value);
                                    numberPicker2.setValue(minValue);
                                }
                                linearLayout.invalidate();
                            }

                            public static /* synthetic */ String $r8$lambda$gsAGb0m6JrJmfNQJCki2DEsYwEc(int i) {
                                boolean z = LocaleController.is24HourFormat;
                                String str = String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf((i % 12 != 0 || z) ? i % (z ? 24 : 12) : 12));
                                return i >= 24 ? LocaleController.formatString(C2797R.string.BusinessHoursNextDayPicker, str) : str;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$25 */
                            public class C372925 extends LinearLayout {
                                boolean ignoreLayout = false;
                                final /* synthetic */ NumberPicker val$hourPicker;
                                final /* synthetic */ NumberPicker val$minutePicker;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C372925(Context context2, final NumberPicker c3726222, final NumberPicker c3728242) {
                                    super(context2);
                                    numberPicker = c3726222;
                                    numberPicker = c3728242;
                                    this.ignoreLayout = false;
                                }

                                @Override // android.widget.LinearLayout, android.view.View
                                public void onMeasure(int i4, int i5) {
                                    this.ignoreLayout = true;
                                    Point point = AndroidUtilities.displaySize;
                                    int i6 = point.x > point.y ? 3 : 5;
                                    numberPicker.setItemCount(i6);
                                    numberPicker.setItemCount(i6);
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i6;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i6;
                                    this.ignoreLayout = false;
                                    super.onMeasure(i4, i5);
                                }

                                @Override // android.view.View, android.view.ViewParent
                                public void requestLayout() {
                                    if (this.ignoreLayout) {
                                        return;
                                    }
                                    super.requestLayout();
                                }
                            }

                            public static /* synthetic */ boolean $r8$lambda$FZD8eC92_56tRWdS_pwLtsrzNfU(View view, MotionEvent motionEvent) {
                                return true;
                            }

                            public static AlertDialog.Builder createDatePickerDialog(Context context, int i, int i2, int i3, int i4, int i5, int i6, String str, final boolean z, final DatePickerDelegate datePickerDelegate) {
                                if (context == null) {
                                    return null;
                                }
                                LinearLayout linearLayout = new LinearLayout(context);
                                linearLayout.setOrientation(0);
                                linearLayout.setWeightSum(1.0f);
                                final NumberPicker numberPicker = new NumberPicker(context);
                                final NumberPicker numberPicker2 = new NumberPicker(context);
                                final NumberPicker numberPicker3 = new NumberPicker(context);
                                linearLayout.addView(numberPicker2, LayoutHelper.createLinear(0, -2, 0.3f));
                                numberPicker2.setOnScrollListener(new NumberPicker.OnScrollListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda177
                                    @Override // org.telegram.ui.Components.NumberPicker.OnScrollListener
                                    public final void onScrollStateChange(NumberPicker numberPicker4, int i7) {
                                        AlertsCreator.m9974$r8$lambda$Wa9X13o9woLT0m4wizTHcnSHig(z, numberPicker2, numberPicker, numberPicker3, numberPicker4, i7);
                                    }
                                });
                                numberPicker.setMinValue(0);
                                numberPicker.setMaxValue(11);
                                linearLayout.addView(numberPicker, LayoutHelper.createLinear(0, -2, 0.3f));
                                numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda178
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i7) {
                                        return AlertsCreator.$r8$lambda$56PpeE_Z5umoKGMzBFC0pH5xzoM(i7);
                                    }
                                });
                                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda179
                                    @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                                    public final void onValueChange(NumberPicker numberPicker4, int i7, int i8) {
                                        AlertsCreator.updateDayPicker(numberPicker2, numberPicker, numberPicker3);
                                    }
                                });
                                numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda180
                                    @Override // org.telegram.ui.Components.NumberPicker.OnScrollListener
                                    public final void onScrollStateChange(NumberPicker numberPicker4, int i7) {
                                        AlertsCreator.m10002$r8$lambda$tFDmHyRJSNiynm2aUcs6X0nOIU(z, numberPicker2, numberPicker, numberPicker3, numberPicker4, i7);
                                    }
                                });
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                int i7 = calendar.get(1);
                                numberPicker3.setMinValue(i + i7);
                                numberPicker3.setMaxValue(i2 + i7);
                                numberPicker3.setValue(i7 + i3);
                                linearLayout.addView(numberPicker3, LayoutHelper.createLinear(0, -2, 0.4f));
                                numberPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda181
                                    @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                                    public final void onValueChange(NumberPicker numberPicker4, int i8, int i9) {
                                        AlertsCreator.updateDayPicker(numberPicker2, numberPicker, numberPicker3);
                                    }
                                });
                                numberPicker3.setOnScrollListener(new NumberPicker.OnScrollListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda182
                                    @Override // org.telegram.ui.Components.NumberPicker.OnScrollListener
                                    public final void onScrollStateChange(NumberPicker numberPicker4, int i8) {
                                        AlertsCreator.$r8$lambda$R44R0t9MSgFz4YKLtYJUzLwdQhw(z, numberPicker2, numberPicker, numberPicker3, numberPicker4, i8);
                                    }
                                });
                                updateDayPicker(numberPicker2, numberPicker, numberPicker3);
                                if (z) {
                                    checkPickerDate(numberPicker2, numberPicker, numberPicker3);
                                }
                                if (i4 != -1) {
                                    numberPicker2.setValue(i4);
                                    numberPicker.setValue(i5);
                                    numberPicker3.setValue(i6);
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle(str);
                                builder.setView(linearLayout);
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Set), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda183
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i8) {
                                        AlertsCreator.m9982$r8$lambda$arWJVaph7jc5Mo0ZHX3y1sGTg(z, numberPicker2, numberPicker, numberPicker3, datePickerDelegate, alertDialog, i8);
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                return builder;
                            }

                            /* JADX INFO: renamed from: $r8$lambda$Wa9X13o9woLT0m4wiz-THcnSHig */
                            public static /* synthetic */ void m9974$r8$lambda$Wa9X13o9woLT0m4wizTHcnSHig(boolean z, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, NumberPicker numberPicker4, int i) {
                                if (z && i == 0) {
                                    checkPickerDate(numberPicker, numberPicker2, numberPicker3);
                                }
                            }

                            public static /* synthetic */ String $r8$lambda$56PpeE_Z5umoKGMzBFC0pH5xzoM(int i) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(5, 1);
                                calendar.set(2, i);
                                return calendar.getDisplayName(2, 1, Locale.getDefault());
                            }

                            /* JADX INFO: renamed from: $r8$lambda$tFDmHyRJSNiynm2aUcs6X0-nOIU */
                            public static /* synthetic */ void m10002$r8$lambda$tFDmHyRJSNiynm2aUcs6X0nOIU(boolean z, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, NumberPicker numberPicker4, int i) {
                                if (z && i == 0) {
                                    checkPickerDate(numberPicker, numberPicker2, numberPicker3);
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$R44R0t9MSgFz4YKLtYJUzLwdQhw(boolean z, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, NumberPicker numberPicker4, int i) {
                                if (z && i == 0) {
                                    checkPickerDate(numberPicker, numberPicker2, numberPicker3);
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$arWJVaph--7jc5Mo0ZHX3y1sGTg */
                            public static /* synthetic */ void m9982$r8$lambda$arWJVaph7jc5Mo0ZHX3y1sGTg(boolean z, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, DatePickerDelegate datePickerDelegate, AlertDialog alertDialog, int i) {
                                if (z) {
                                    checkPickerDate(numberPicker, numberPicker2, numberPicker3);
                                }
                                datePickerDelegate.didSelectDate(numberPicker3.getValue(), numberPicker2.getValue(), numberPicker.getValue());
                            }

                            public static boolean checkScheduleDate(TextView textView, TextView textView2, int i, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3) {
                                return checkScheduleDate(textView, textView2, 0L, 0L, i, numberPicker, numberPicker2, numberPicker3);
                            }

                            public static boolean checkScheduleDate(TextView textView, TextView textView2, long j, int i, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3) {
                                return checkScheduleDate(textView, textView2, 0L, j, i, numberPicker, numberPicker2, numberPicker3);
                            }

                            public static boolean checkScheduleDate(TextView textView, TextView textView2, long j, long j2, int i, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3) {
                                long j3;
                                int i2;
                                long timeInMillis;
                                int iBetween;
                                int i3;
                                int i4;
                                String pluralString;
                                int value = numberPicker.getValue();
                                int value2 = numberPicker2.getValue();
                                int value3 = numberPicker3.getValue();
                                Calendar calendar = Calendar.getInstance();
                                long jCurrentTimeMillis = System.currentTimeMillis();
                                calendar.setTimeInMillis(jCurrentTimeMillis);
                                int i5 = calendar.get(1);
                                calendar.get(6);
                                if (j2 > 0) {
                                    i2 = i5;
                                    calendar.setTimeInMillis(jCurrentTimeMillis + (j2 * 1000));
                                    calendar.set(11, 23);
                                    calendar.set(12, 59);
                                    calendar.set(13, 59);
                                    calendar.set(14, 0);
                                    j3 = jCurrentTimeMillis;
                                    iBetween = (int) ChronoUnit.DAYS.between(Instant.ofEpochMilli(jCurrentTimeMillis).atZone(ZoneId.systemDefault()).mo668n(), Instant.ofEpochMilli(calendar.getTimeInMillis()).atZone(ZoneId.systemDefault()).mo668n());
                                    timeInMillis = calendar.getTimeInMillis();
                                    i3 = 23;
                                    i4 = 59;
                                } else {
                                    j3 = jCurrentTimeMillis;
                                    i2 = i5;
                                    timeInMillis = j2;
                                    iBetween = 0;
                                    i3 = 0;
                                    i4 = 0;
                                }
                                int i6 = i4;
                                long millis = j > 0 ? TimeUnit.SECONDS.toMillis(j) : 60000L;
                                long j4 = j3 + millis;
                                calendar.setTimeInMillis(j4);
                                int i7 = calendar.get(11);
                                int i8 = calendar.get(12);
                                long j5 = timeInMillis;
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.add(6, value);
                                calendar.set(11, value2);
                                calendar.set(12, value3);
                                calendar.set(13, 0);
                                calendar.set(14, 0);
                                long timeInMillis2 = calendar.getTimeInMillis();
                                numberPicker.setMinValue(0);
                                if (j5 > 0) {
                                    numberPicker.setMaxValue(iBetween);
                                }
                                int value4 = numberPicker.getValue();
                                numberPicker2.setMinValue(value4 == 0 ? i7 : 0);
                                if (j5 > 0) {
                                    numberPicker2.setMaxValue(value4 == iBetween ? i3 : 23);
                                }
                                int value5 = numberPicker2.getValue();
                                numberPicker3.setMinValue((value4 == 0 && value5 == i7) ? i8 : 0);
                                if (j5 > 0) {
                                    numberPicker3.setMaxValue((value4 == iBetween && value5 == i3) ? i6 : 59);
                                }
                                int value6 = numberPicker3.getValue();
                                if (timeInMillis2 <= j4) {
                                    calendar.setTimeInMillis(j4);
                                } else if (j5 > 0 && timeInMillis2 > j5) {
                                    calendar.setTimeInMillis(j5);
                                }
                                int i9 = calendar.get(1);
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.add(6, value4);
                                calendar.set(11, value5);
                                calendar.set(12, value6);
                                calendar.set(13, 0);
                                calendar.set(14, 0);
                                long timeInMillis3 = calendar.getTimeInMillis();
                                if (textView != null) {
                                    textView.setText(LocaleController.getInstance().getFormatterScheduleSend((value4 == 0 ? 0 : i2 == i9 ? 1 : 2) + (i * 3)).format(timeInMillis3));
                                }
                                if (textView2 != null) {
                                    int i10 = (int) ((timeInMillis3 - j3) / 1000);
                                    if (i10 > 86400) {
                                        pluralString = LocaleController.formatPluralString("DaysSchedule", Math.round(i10 / 86400.0f), new Object[0]);
                                    } else if (i10 >= 3600) {
                                        pluralString = LocaleController.formatPluralString("HoursSchedule", Math.round(i10 / 3600.0f), new Object[0]);
                                    } else if (i10 >= 60) {
                                        pluralString = LocaleController.formatPluralString("MinutesSchedule", Math.round(i10 / 60.0f), new Object[0]);
                                    } else {
                                        pluralString = LocaleController.formatPluralString("SecondsSchedule", i10, new Object[0]);
                                    }
                                    if (textView2.getTag() != null) {
                                        textView2.setText(LocaleController.formatString("VoipChannelScheduleInfo", C2797R.string.VoipChannelScheduleInfo, pluralString));
                                    } else {
                                        textView2.setText(LocaleController.formatString("VoipGroupScheduleInfo", C2797R.string.VoipGroupScheduleInfo, pluralString));
                                    }
                                }
                                return timeInMillis2 - j3 > millis;
                            }

                            public static long checkFormattedDateInput(ButtonWithCounterView buttonWithCounterView, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, NumberPicker numberPicker4, int i) {
                                long jCurrentTimeMillis = System.currentTimeMillis();
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(jCurrentTimeMillis);
                                int i2 = calendar.get(1);
                                int value = ((numberPicker2.getValue() - 120) / 12) + i2;
                                calendar.clear();
                                calendar.set(1, value);
                                int i3 = 2;
                                calendar.set(2, (numberPicker2.getValue() - 120) % 12);
                                numberPicker.setMinValue(1);
                                numberPicker.setMaxValue(calendar.getActualMaximum(5));
                                int value2 = numberPicker.getValue();
                                int value3 = numberPicker3.getValue();
                                int value4 = numberPicker4.getValue();
                                calendar.set(5, value2);
                                calendar.set(11, value3);
                                calendar.set(12, value4);
                                long timeInMillis = calendar.getTimeInMillis();
                                calendar.setTimeInMillis(timeInMillis);
                                if (buttonWithCounterView != null) {
                                    if (i != 0) {
                                        TLRPC.TL_messageEntityFormattedDate tL_messageEntityFormattedDate = new TLRPC.TL_messageEntityFormattedDate();
                                        tL_messageEntityFormattedDate.date = (int) (timeInMillis / 1000);
                                        tL_messageEntityFormattedDate.relative = (i & 1) != 0;
                                        tL_messageEntityFormattedDate.short_time = (i & 2) != 0;
                                        tL_messageEntityFormattedDate.long_time = (i & 4) != 0;
                                        tL_messageEntityFormattedDate.short_date = (i & 8) != 0;
                                        tL_messageEntityFormattedDate.long_date = (i & 16) != 0;
                                        tL_messageEntityFormattedDate.day_of_week = (i & 32) != 0;
                                        tL_messageEntityFormattedDate.flags = i;
                                        buttonWithCounterView.setText(LocaleController.formatEntityFormattedDate(tL_messageEntityFormattedDate), true);
                                        return timeInMillis;
                                    }
                                    if (value2 == 0) {
                                        i3 = 0;
                                    } else if (i2 == value) {
                                        i3 = 1;
                                    }
                                    buttonWithCounterView.setText(LocaleController.getInstance().getFormatterScheduleSend(i3 + 9).format(timeInMillis), true);
                                }
                                return timeInMillis;
                            }

                            public static class ScheduleDatePickerColors {
                                public final int backgroundColor;
                                public final int buttonBackgroundColor;
                                public final int buttonBackgroundPressedColor;
                                public final int buttonTextColor;
                                public final int iconColor;
                                public final int iconSelectorColor;
                                public final int subMenuBackgroundColor;
                                public final int subMenuSelectorColor;
                                public final int subMenuTextColor;
                                public final int textColor;

                                public /* synthetic */ ScheduleDatePickerColors(AlertsCreatorIA alertsCreatorIA) {
                                    this();
                                }

                                private ScheduleDatePickerColors() {
                                    this((Theme.ResourcesProvider) null);
                                }

                                /* JADX WARN: Illegal instructions before constructor call */
                                public ScheduleDatePickerColors(Theme.ResourcesProvider resourcesProvider) {
                                    int i = Theme.key_dialogTextBlack;
                                    int colorOrDefault = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i) : Theme.getColor(i);
                                    int i2 = Theme.key_dialogBackground;
                                    int colorOrDefault2 = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i2) : Theme.getColor(i2);
                                    int i3 = Theme.key_sheet_other;
                                    int colorOrDefault3 = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i3) : Theme.getColor(i3);
                                    int i4 = Theme.key_player_actionBarSelector;
                                    int colorOrDefault4 = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i4) : Theme.getColor(i4);
                                    int i5 = Theme.key_actionBarDefaultSubmenuItem;
                                    int colorOrDefault5 = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i5) : Theme.getColor(i5);
                                    int i6 = Theme.key_actionBarDefaultSubmenuBackground;
                                    int colorOrDefault6 = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i6) : Theme.getColor(i6);
                                    int i7 = Theme.key_listSelector;
                                    int colorOrDefault7 = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i7) : Theme.getColor(i7);
                                    int i8 = Theme.key_featuredStickers_buttonText;
                                    int colorOrDefault8 = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i8) : Theme.getColor(i8);
                                    int i9 = Theme.key_featuredStickers_addButton;
                                    this(colorOrDefault, colorOrDefault2, colorOrDefault3, colorOrDefault4, colorOrDefault5, colorOrDefault6, colorOrDefault7, colorOrDefault8, resourcesProvider != null ? resourcesProvider.getColorOrDefault(i9) : Theme.getColor(i9), resourcesProvider != null ? resourcesProvider.getColorOrDefault(Theme.key_featuredStickers_addButtonPressed) : Theme.getColor(Theme.key_featuredStickers_addButtonPressed));
                                }

                                public ScheduleDatePickerColors(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
                                    this(i, i2, i3, i4, i5, i6, i7, Theme.getColor(Theme.key_featuredStickers_buttonText), Theme.getColor(Theme.key_featuredStickers_addButton), Theme.getColor(Theme.key_featuredStickers_addButtonPressed));
                                }

                                public ScheduleDatePickerColors(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                                    this.textColor = i;
                                    this.backgroundColor = i2;
                                    this.iconColor = i3;
                                    this.iconSelectorColor = i4;
                                    this.subMenuTextColor = i5;
                                    this.subMenuBackgroundColor = i6;
                                    this.subMenuSelectorColor = i7;
                                    this.buttonTextColor = i8;
                                    this.buttonBackgroundColor = i9;
                                    this.buttonBackgroundPressedColor = i10;
                                }
                            }

                            public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, long j, ScheduleDatePickerDelegate scheduleDatePickerDelegate) {
                                return createScheduleDatePickerDialog(context, j, -1L, scheduleDatePickerDelegate, (Runnable) null);
                            }

                            public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, long j, ScheduleDatePickerDelegate scheduleDatePickerDelegate, Theme.ResourcesProvider resourcesProvider) {
                                return createScheduleDatePickerDialog(context, j, -1L, 0, scheduleDatePickerDelegate, (Runnable) null, resourcesProvider);
                            }

                            public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, long j, ScheduleDatePickerDelegate scheduleDatePickerDelegate, ScheduleDatePickerColors scheduleDatePickerColors) {
                                return createScheduleDatePickerDialog(context, j, -1L, 0, scheduleDatePickerDelegate, null, scheduleDatePickerColors, null);
                            }

                            public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, long j, ScheduleDatePickerDelegate scheduleDatePickerDelegate, Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
                                return createScheduleDatePickerDialog(context, j, -1L, 0, scheduleDatePickerDelegate, runnable, resourcesProvider);
                            }

                            public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, String str, long j, long j2, boolean z, ScheduleDatePickerDelegate scheduleDatePickerDelegate, Runnable runnable) {
                                return createScheduleDatePickerDialog(context, str, j, j2, 0, z, scheduleDatePickerDelegate, runnable, new ScheduleDatePickerColors(), null);
                            }

                            public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, long j, long j2, ScheduleDatePickerDelegate scheduleDatePickerDelegate, Runnable runnable) {
                                return createScheduleDatePickerDialog(context, j, j2, 0, scheduleDatePickerDelegate, runnable, new ScheduleDatePickerColors(), null);
                            }

                            public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, long j, long j2, int i, ScheduleDatePickerDelegate scheduleDatePickerDelegate, Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
                                return createScheduleDatePickerDialog(context, j, j2, i, scheduleDatePickerDelegate, runnable, new ScheduleDatePickerColors(resourcesProvider), resourcesProvider);
                            }

                            public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, long j, long j2, int i, ScheduleDatePickerDelegate scheduleDatePickerDelegate, Runnable runnable, ScheduleDatePickerColors scheduleDatePickerColors, Theme.ResourcesProvider resourcesProvider) {
                                return createScheduleDatePickerDialog(context, j, j2, i, false, scheduleDatePickerDelegate, runnable, scheduleDatePickerColors, resourcesProvider);
                            }

                            public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, long j, long j2, int i, boolean z, ScheduleDatePickerDelegate scheduleDatePickerDelegate, Runnable runnable, ScheduleDatePickerColors scheduleDatePickerColors, Theme.ResourcesProvider resourcesProvider) {
                                return createScheduleDatePickerDialog(context, null, j, j2, i, z, scheduleDatePickerDelegate, runnable, scheduleDatePickerColors, resourcesProvider);
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX WARN: Type inference failed for: r0v11, types: [android.view.View] */
                            /* JADX WARN: Type inference failed for: r0v19, types: [android.view.View, org.telegram.ui.ActionBar.ActionBarMenuItem] */
                            /* JADX WARN: Type inference failed for: r11v10 */
                            /* JADX WARN: Type inference failed for: r11v11, types: [org.telegram.ui.ActionBar.ActionBarMenuItem] */
                            /* JADX WARN: Type inference failed for: r11v30 */
                            /* JADX WARN: Type inference failed for: r11v31, types: [boolean] */
                            /* JADX WARN: Type inference failed for: r11v32 */
                            /* JADX WARN: Type inference failed for: r11v35 */
                            /* JADX WARN: Type inference failed for: r13v1 */
                            /* JADX WARN: Type inference failed for: r13v2, types: [android.view.ViewGroup] */
                            /* JADX WARN: Type inference failed for: r13v4 */
                            /* JADX WARN: Type inference failed for: r14v0, types: [org.telegram.ui.ActionBar.BottomSheet$Builder] */
                            /* JADX WARN: Type inference failed for: r29v3, types: [org.telegram.ui.ActionBar.ActionBarMenuItem] */
                            /* JADX WARN: Type inference failed for: r36v0 */
                            /* JADX WARN: Type inference failed for: r36v1 */
                            /* JADX WARN: Type inference failed for: r36v2 */
                            /* JADX WARN: Type inference failed for: r4v3, types: [android.view.ViewGroup, android.widget.FrameLayout] */
                            /* JADX WARN: Type inference failed for: r5v0, types: [android.view.View, android.view.ViewGroup, android.widget.LinearLayout, org.telegram.ui.Components.AlertsCreator$28] */
                            /* JADX WARN: Type inference failed for: r7v1 */
                            /* JADX WARN: Type inference failed for: r7v17, types: [android.view.ViewGroup] */
                            /* JADX WARN: Type inference failed for: r7v18 */
                            /* JADX WARN: Type inference failed for: r7v2, types: [android.view.ViewGroup] */
                            public static BottomSheet.Builder createScheduleDatePickerDialog(final Context context, final String str, final long j, long j2, int i, boolean z, final ScheduleDatePickerDelegate scheduleDatePickerDelegate, final Runnable runnable, final ScheduleDatePickerColors scheduleDatePickerColors, final Theme.ResourcesProvider resourcesProvider) {
                                char c2;
                                C373127 c373127;
                                FrameLayout frameLayout;
                                ?? r7;
                                ?? r36;
                                Context context2;
                                C373026 c373026;
                                int[] iArr;
                                char c3;
                                char c4;
                                int i2;
                                ?? r13;
                                final ?? r11;
                                char c5;
                                char c6;
                                int i3;
                                int i4;
                                final int[] iArr2;
                                final String[] strArr;
                                NumberPicker numberPicker;
                                C373026 c3730262;
                                boolean[] zArr;
                                long j3;
                                float f;
                                final int[] iArr3;
                                final TextView textView;
                                FrameLayout frameLayout2;
                                Runnable runnable2;
                                TLRPC.User user;
                                TLRPC.UserStatus userStatus;
                                ?? r112;
                                if (context == null) {
                                    return null;
                                }
                                int[] iArr4 = {i};
                                final long clientUserId = UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId();
                                final ?? builder = new BottomSheet.Builder(context, false, resourcesProvider);
                                builder.setApplyBottomPadding(false);
                                final NumberPicker numberPicker2 = new NumberPicker(context, resourcesProvider);
                                numberPicker2.setTextColor(scheduleDatePickerColors.textColor);
                                numberPicker2.setTextOffset(AndroidUtilities.m1036dp(10.0f));
                                numberPicker2.setItemCount(5);
                                C373026 c3730263 = new NumberPicker(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.26
                                    public C373026(final Context context3, final Theme.ResourcesProvider resourcesProvider2) {
                                        super(context3, resourcesProvider2);
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i5) {
                                        return LocaleController.formatPluralString("Hours", i5, new Object[0]);
                                    }
                                };
                                c3730263.setWrapSelectorWheel(true);
                                c3730263.setAllItemsCount(24);
                                c3730263.setItemCount(5);
                                c3730263.setTextColor(scheduleDatePickerColors.textColor);
                                c3730263.setTextOffset(-AndroidUtilities.m1036dp(10.0f));
                                C373127 c3731272 = new NumberPicker(context3, resourcesProvider2) { // from class: org.telegram.ui.Components.AlertsCreator.27
                                    public C373127(final Context context3, final Theme.ResourcesProvider resourcesProvider2) {
                                        super(context3, resourcesProvider2);
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i5) {
                                        return LocaleController.formatPluralString("Minutes", i5, new Object[0]);
                                    }
                                };
                                c3731272.setWrapSelectorWheel(true);
                                c3731272.setAllItemsCount(60);
                                c3731272.setItemCount(5);
                                c3731272.setTextColor(scheduleDatePickerColors.textColor);
                                c3731272.setTextOffset(-AndroidUtilities.m1036dp(34.0f));
                                ?? frameLayout3 = new FrameLayout(context3);
                                ?? c373228 = new LinearLayout(context3) { // from class: org.telegram.ui.Components.AlertsCreator.28
                                    boolean ignoreLayout = false;
                                    final /* synthetic */ NumberPicker val$dayPicker;
                                    final /* synthetic */ NumberPicker val$hourPicker;
                                    final /* synthetic */ NumberPicker val$minutePicker;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C373228(final Context context3, final NumberPicker numberPicker22, NumberPicker c37302632, NumberPicker c37312722) {
                                        super(context3);
                                        numberPicker = numberPicker22;
                                        numberPicker = c37302632;
                                        numberPicker = c37312722;
                                        this.ignoreLayout = false;
                                    }

                                    @Override // android.widget.LinearLayout, android.view.View
                                    public void onMeasure(int i5, int i6) {
                                        this.ignoreLayout = true;
                                        Point point = AndroidUtilities.displaySize;
                                        int i7 = point.x > point.y ? 3 : 5;
                                        numberPicker.setItemCount(i7);
                                        numberPicker.setItemCount(i7);
                                        numberPicker.setItemCount(i7);
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i7;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i7;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i7;
                                        this.ignoreLayout = false;
                                        super.onMeasure(i5, i6);
                                    }

                                    @Override // android.view.View, android.view.ViewParent
                                    public void requestLayout() {
                                        if (this.ignoreLayout) {
                                            return;
                                        }
                                        super.requestLayout();
                                    }
                                };
                                c373228.setClipToPadding(false);
                                c373228.setClipChildren(false);
                                c373228.setOrientation(1);
                                frameLayout3.addView(c373228, LayoutHelper.createFrame(-1, -1.0f));
                                FrameLayout frameLayout4 = new FrameLayout(context3);
                                frameLayout3.addView(frameLayout4, LayoutHelper.createFrame(-1, 100.0f, 87, 0.0f, 0.0f, 0.0f, 120.0f));
                                FrameLayout frameLayout5 = new FrameLayout(context3);
                                c373228.addView(frameLayout5, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
                                TextView textView2 = new TextView(context3);
                                if (!TextUtils.isEmpty(str)) {
                                    textView2.setText(str);
                                    c2 = 0;
                                } else if (j == clientUserId) {
                                    c2 = 0;
                                    textView2.setText(LocaleController.getString(C2797R.string.SetReminder));
                                } else {
                                    c2 = 0;
                                    textView2.setText(LocaleController.getString(C2797R.string.ScheduleMessage));
                                }
                                textView2.setTextColor(scheduleDatePickerColors.textColor);
                                textView2.setTextSize(1, 20.0f);
                                textView2.setTypeface(AndroidUtilities.bold());
                                frameLayout5.addView(textView2, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
                                textView2.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda19
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        return AlertsCreator.$r8$lambda$ozn7pG3Szh6AafadnEKhjiMln10(view, motionEvent);
                                    }
                                });
                                final boolean[] zArr2 = new boolean[1];
                                zArr2[c2] = true;
                                if (!DialogObject.isUserDialog(j) || j == clientUserId || (user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(Long.valueOf(j))) == null || user.bot || (userStatus = user.status) == null || userStatus.expires <= 0) {
                                    c373127 = c37312722;
                                    frameLayout = frameLayout4;
                                    r7 = frameLayout5;
                                    r36 = frameLayout3;
                                    context2 = context3;
                                    c373026 = c37302632;
                                    iArr = iArr4;
                                    c3 = 2;
                                    c4 = 5;
                                    i2 = 60;
                                    r13 = c373228;
                                    r11 = 0;
                                } else {
                                    String firstName = UserObject.getFirstName(user);
                                    if (firstName.length() > 10) {
                                        char c7 = c2;
                                        firstName = firstName.substring(c7 == true ? 1 : 0, 10).concat("…");
                                        r112 = c7;
                                    } else {
                                        r112 = c2;
                                    }
                                    String str2 = firstName;
                                    c373127 = c37312722;
                                    frameLayout = frameLayout4;
                                    context2 = context3;
                                    iArr = iArr4;
                                    r36 = frameLayout3;
                                    r13 = c373228;
                                    ?? r72 = frameLayout5;
                                    c4 = 5;
                                    i2 = 60;
                                    c373026 = c37302632;
                                    c3 = 2;
                                    ?? actionBarMenuItem = new ActionBarMenuItem(context2, null, 0, scheduleDatePickerColors.textColor, false, resourcesProvider2);
                                    actionBarMenuItem.setLongClickEnabled(r112);
                                    actionBarMenuItem.setSubMenuOpenSide(2);
                                    actionBarMenuItem.setIcon(C2797R.drawable.ic_ab_other);
                                    actionBarMenuItem.setBackground(Theme.createSelectorDrawable(scheduleDatePickerColors.iconSelectorColor, 1));
                                    r72.addView(actionBarMenuItem, LayoutHelper.createFrame(40, 40.0f, 53, 0.0f, 8.0f, 5.0f, 0.0f));
                                    actionBarMenuItem.addSubItem(1, LocaleController.formatString(C2797R.string.ScheduleWhenOnline, str2));
                                    actionBarMenuItem.setContentDescription(LocaleController.getString(C2797R.string.AccDescrMoreOptions));
                                    r11 = actionBarMenuItem;
                                    r7 = r72;
                                }
                                if (r11 != 0) {
                                    r11.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda22
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            AlertsCreator.$r8$lambda$8vxtbZH3APAz4gcoeViFds86fZw(r11, scheduleDatePickerColors, view);
                                        }
                                    });
                                    r11.setDelegate(new ActionBarMenuItem.ActionBarMenuItemDelegate() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda23
                                        @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemDelegate
                                        public final void onItemClick(int i5) {
                                            AlertsCreator.$r8$lambda$jQgtgE444LObLJCtXpMjIXr9krE(scheduleDatePickerDelegate, zArr2, builder, i5);
                                        }
                                    });
                                }
                                RLottieImageView rLottieImageView = new RLottieImageView(context2);
                                final RLottieDrawable rLottieDrawable = new RLottieDrawable(C2797R.raw.notify_toggle, "notify_toggle", AndroidUtilities.m1036dp(24.0f), AndroidUtilities.m1036dp(24.0f), true, null);
                                rLottieDrawable.setAllowDecodeSingleFrame(true);
                                rLottieDrawable.setPlayInDirectionOfCustomEndFrame(true);
                                rLottieDrawable.start();
                                rLottieDrawable.setCurrentFrame(40);
                                rLottieDrawable.setCustomEndFrame(40);
                                rLottieImageView.setScaleType(ImageView.ScaleType.CENTER);
                                rLottieImageView.setAnimation(rLottieDrawable);
                                rLottieImageView.setColorFilter(new PorterDuffColorFilter(scheduleDatePickerColors.textColor, PorterDuff.Mode.SRC_IN));
                                rLottieImageView.setBackground(Theme.createSelectorDrawable(scheduleDatePickerColors.iconSelectorColor, 1));
                                r7.addView(rLottieImageView, LayoutHelper.createFrame(40, 40.0f, 53, 0.0f, 8.0f, (r11 != 0 ? 42 : 0) + 8, 0.0f));
                                LinearLayout linearLayout = new LinearLayout(context2);
                                linearLayout.setOrientation(0);
                                linearLayout.setWeightSum(1.0f);
                                r13.addView(linearLayout, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
                                final Calendar calendar = Calendar.getInstance();
                                final C373329 c373329 = new TextView(context2) { // from class: org.telegram.ui.Components.AlertsCreator.29
                                    public C373329(Context context22) {
                                        super(context22);
                                    }

                                    @Override // android.widget.TextView, android.view.View
                                    public CharSequence getAccessibilityClassName() {
                                        return Button.class.getName();
                                    }
                                };
                                final ?? r29 = r11;
                                linearLayout.addView(numberPicker22, LayoutHelper.createLinear(0, 270, 0.5f));
                                numberPicker22.setMinValue(0);
                                numberPicker22.setMaxValue(365);
                                numberPicker22.setWrapSelectorWheel(false);
                                numberPicker22.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda24
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i5) {
                                        return AlertsCreator.$r8$lambda$Bcg_Gj2lrwmQ8sB_vafHOInl0dY(i5);
                                    }
                                });
                                char c8 = c3;
                                final C373026 c3730264 = c373026;
                                final C373127 c3731273 = c373127;
                                NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda25
                                    @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                                    public final void onValueChange(NumberPicker numberPicker3, int i5, int i6) {
                                        AlertsCreator.checkScheduleDate(c373329, null, str != null ? 3 : clientUserId == j ? 1 : 0, numberPicker22, c3730264, c3731273);
                                    }
                                };
                                numberPicker22.setOnValueChangedListener(onValueChangeListener);
                                c3730264.setMinValue(0);
                                c3730264.setMaxValue(23);
                                linearLayout.addView(c3730264, LayoutHelper.createLinear(0, 270, 0.2f));
                                c3730264.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda26
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i5) {
                                        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i5));
                                    }
                                });
                                c3730264.setOnValueChangedListener(onValueChangeListener);
                                c3731273.setMinValue(0);
                                c3731273.setMaxValue(59);
                                c3731273.setValue(0);
                                c3731273.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda27
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i5) {
                                        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i5));
                                    }
                                });
                                linearLayout.addView(c3731273, LayoutHelper.createLinear(0, 270, 0.3f));
                                c3731273.setOnValueChangedListener(onValueChangeListener);
                                if (j2 > 0 && j2 != 2147483646) {
                                    long j4 = 1000 * j2;
                                    calendar.setTimeInMillis(System.currentTimeMillis());
                                    calendar.set(12, 0);
                                    calendar.set(13, 0);
                                    calendar.set(14, 0);
                                    calendar.set(11, 0);
                                    int timeInMillis = (int) ((j4 - calendar.getTimeInMillis()) / DurationKt.MILLIS_IN_DAY);
                                    calendar.setTimeInMillis(j4);
                                    if (timeInMillis >= 0) {
                                        c3731273.setValue(calendar.get(12));
                                        c3730264.setValue(calendar.get(11));
                                        numberPicker22.setValue(timeInMillis);
                                    }
                                }
                                boolean[] zArr3 = {true};
                                checkScheduleDate(c373329, null, str != null ? 3 : clientUserId == j ? 1 : 0, numberPicker22, c3730264, c3731273);
                                boolean zIsTestBackend = ConnectionsManager.getInstance(UserConfig.selectedAccount).isTestBackend();
                                if (zIsTestBackend) {
                                    c6 = '\t';
                                    i3 = 10;
                                    int[] iArr5 = new int[10];
                                    iArr5[0] = 0;
                                    iArr5[1] = i2;
                                    iArr5[c8] = 300;
                                    iArr5[3] = 86400;
                                    iArr5[4] = 604800;
                                    iArr5[c4] = 1209600;
                                    iArr5[6] = 2592000;
                                    iArr5[7] = 7862400;
                                    iArr5[8] = 15724800;
                                    iArr5[9] = 31536000;
                                    c5 = 3;
                                    iArr2 = iArr5;
                                    i4 = 8;
                                } else {
                                    c5 = 3;
                                    c6 = '\t';
                                    i3 = 10;
                                    i4 = 8;
                                    iArr2 = new int[8];
                                    iArr2[0] = 0;
                                    iArr2[1] = 86400;
                                    iArr2[c8] = 604800;
                                    iArr2[3] = 1209600;
                                    iArr2[4] = 2592000;
                                    iArr2[c4] = 7862400;
                                    iArr2[6] = 15724800;
                                    iArr2[7] = 31536000;
                                }
                                if (zIsTestBackend) {
                                    strArr = new String[i3];
                                    strArr[0] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOptionNever);
                                    strArr[1] = "Every minute";
                                    strArr[c8] = "Every 5 minutes";
                                    strArr[c5] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOptionDaily);
                                    strArr[4] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOptionWeekly);
                                    strArr[c4] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOptionBiweekly);
                                    strArr[6] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOptionMonthly);
                                    strArr[7] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOption3Monthly);
                                    strArr[i4] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOption6Monthly);
                                    strArr[c6] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOptionYearly);
                                } else {
                                    strArr = new String[i4];
                                    strArr[0] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOptionNever);
                                    strArr[1] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOptionDaily);
                                    strArr[c8] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOptionWeekly);
                                    strArr[c5] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOptionBiweekly);
                                    strArr[4] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOptionMonthly);
                                    strArr[c4] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOption3Monthly);
                                    strArr[6] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOption6Monthly);
                                    strArr[7] = LocaleController.getString(C2797R.string.MessageScheduledRepeatOptionYearly);
                                }
                                if (z) {
                                    numberPicker = numberPicker22;
                                    c3730262 = c3730264;
                                    zArr = zArr3;
                                    j3 = clientUserId;
                                    f = 14.0f;
                                    iArr3 = iArr;
                                    textView = null;
                                    frameLayout2 = null;
                                    runnable2 = null;
                                } else {
                                    FrameLayout frameLayout6 = new FrameLayout(context3);
                                    f = 14.0f;
                                    int i5 = scheduleDatePickerColors.textColor;
                                    numberPicker = numberPicker22;
                                    int iBlendOver = Theme.blendOver(scheduleDatePickerColors.backgroundColor, Theme.multAlpha(i5, 0.075f));
                                    c3730262 = c3730264;
                                    int iMultAlpha = Theme.multAlpha(scheduleDatePickerColors.textColor, 0.1f);
                                    textView = new TextView(context3);
                                    zArr = zArr3;
                                    j3 = clientUserId;
                                    textView.setTextSize(1, 13.0f);
                                    textView.setTextColor(i5);
                                    textView.setPadding(AndroidUtilities.m1036dp(12.0f), 0, AndroidUtilities.m1036dp(12.0f), 0);
                                    textView.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(14.0f), iBlendOver, Theme.blendOver(iBlendOver, iMultAlpha)));
                                    textView.setGravity(17);
                                    iArr3 = iArr;
                                    Runnable runnable3 = new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda28
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            AlertsCreator.$r8$lambda$zJcYDjtsWjBGPEQDmogSZWzLpB0(iArr2, iArr3, strArr, textView);
                                        }
                                    };
                                    runnable3.run();
                                    frameLayout6.addView(textView, LayoutHelper.createFrame(-2, 28.0f, 1, 32.0f, 4.0f, 32.0f, 5.0f));
                                    r13.addView(frameLayout6, LayoutHelper.createLinear(-1, -2));
                                    runnable2 = runnable3;
                                    frameLayout2 = frameLayout6;
                                }
                                c373329.setPadding(AndroidUtilities.m1036dp(34.0f), 0, AndroidUtilities.m1036dp(34.0f), 0);
                                c373329.setGravity(17);
                                c373329.setTextColor(scheduleDatePickerColors.buttonTextColor);
                                c373329.setTextSize(1, f);
                                c373329.setTypeface(AndroidUtilities.bold());
                                c373329.setBackground(Theme.AdaptiveRipple.filledRect(scheduleDatePickerColors.buttonBackgroundColor, 24.0f));
                                r13.addView(c373329, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
                                final String[] strArr2 = strArr;
                                TextView textView3 = textView;
                                final int[] iArr6 = iArr3;
                                final int[] iArr7 = iArr2;
                                final NumberPicker numberPicker3 = numberPicker;
                                final C373026 c3730265 = c3730262;
                                final boolean[] zArr4 = zArr;
                                final long j5 = j3;
                                c373329.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda29
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.$r8$lambda$GkQBPL0BWkhKY01Dhy1fGTHp2Dg(zArr4, str, j5, j, numberPicker3, c3730265, c3731273, calendar, scheduleDatePickerDelegate, zArr2, iArr6, builder, view);
                                    }
                                });
                                builder.setCustomView(r36);
                                final BottomSheet bottomSheetShow = builder.show();
                                bottomSheetShow.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda30
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        AlertsCreator.m9933$r8$lambda$0GPag8ykDtMQNjee2vEt4sb_yI(runnable, zArr4, dialogInterface);
                                    }
                                });
                                bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
                                bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
                                if (textView3 != null) {
                                    final FrameLayout frameLayout7 = frameLayout2;
                                    final FrameLayout frameLayout8 = frameLayout;
                                    final Runnable runnable4 = runnable2;
                                    textView3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda20
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            AlertsCreator.$r8$lambda$OYpLzZAwci2cmSKNhMzAlrD5y0o(frameLayout8, resourcesProvider2, bottomSheetShow, frameLayout7, iArr7, strArr2, iArr6, runnable4, view);
                                        }
                                    });
                                }
                                final HintView2[] hintView2Arr = new HintView2[1];
                                final long j6 = j3;
                                rLottieImageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda21
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.$r8$lambda$0emMBtFvjpDcRXKWT2mtFmkbXG4(zArr2, rLottieDrawable, hintView2Arr, j, context3, j6, r29, bottomSheetShow, view);
                                    }
                                });
                                return builder;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$26 */
                            public class C373026 extends NumberPicker {
                                public C373026(final Context context3, final Theme.ResourcesProvider resourcesProvider2) {
                                    super(context3, resourcesProvider2);
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i5) {
                                    return LocaleController.formatPluralString("Hours", i5, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$27 */
                            public class C373127 extends NumberPicker {
                                public C373127(final Context context3, final Theme.ResourcesProvider resourcesProvider2) {
                                    super(context3, resourcesProvider2);
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i5) {
                                    return LocaleController.formatPluralString("Minutes", i5, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$28 */
                            public class C373228 extends LinearLayout {
                                boolean ignoreLayout = false;
                                final /* synthetic */ NumberPicker val$dayPicker;
                                final /* synthetic */ NumberPicker val$hourPicker;
                                final /* synthetic */ NumberPicker val$minutePicker;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C373228(final Context context3, final NumberPicker numberPicker22, NumberPicker c37302632, NumberPicker c37312722) {
                                    super(context3);
                                    numberPicker = numberPicker22;
                                    numberPicker = c37302632;
                                    numberPicker = c37312722;
                                    this.ignoreLayout = false;
                                }

                                @Override // android.widget.LinearLayout, android.view.View
                                public void onMeasure(int i5, int i6) {
                                    this.ignoreLayout = true;
                                    Point point = AndroidUtilities.displaySize;
                                    int i7 = point.x > point.y ? 3 : 5;
                                    numberPicker.setItemCount(i7);
                                    numberPicker.setItemCount(i7);
                                    numberPicker.setItemCount(i7);
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i7;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i7;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i7;
                                    this.ignoreLayout = false;
                                    super.onMeasure(i5, i6);
                                }

                                @Override // android.view.View, android.view.ViewParent
                                public void requestLayout() {
                                    if (this.ignoreLayout) {
                                        return;
                                    }
                                    super.requestLayout();
                                }
                            }

                            public static /* synthetic */ boolean $r8$lambda$ozn7pG3Szh6AafadnEKhjiMln10(View view, MotionEvent motionEvent) {
                                return true;
                            }

                            public static /* synthetic */ void $r8$lambda$8vxtbZH3APAz4gcoeViFds86fZw(ActionBarMenuItem actionBarMenuItem, ScheduleDatePickerColors scheduleDatePickerColors, View view) {
                                actionBarMenuItem.toggleSubMenu();
                                actionBarMenuItem.setPopupItemsColor(scheduleDatePickerColors.subMenuTextColor, false);
                                actionBarMenuItem.setupPopupRadialSelectors(scheduleDatePickerColors.subMenuSelectorColor);
                                actionBarMenuItem.redrawPopup(scheduleDatePickerColors.subMenuBackgroundColor);
                            }

                            public static /* synthetic */ void $r8$lambda$jQgtgE444LObLJCtXpMjIXr9krE(ScheduleDatePickerDelegate scheduleDatePickerDelegate, boolean[] zArr, BottomSheet.Builder builder, int i) {
                                if (i == 1) {
                                    scheduleDatePickerDelegate.didSelectDate(zArr[0], 2147483646, 0);
                                    builder.getDismissRunnable().run();
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$29 */
                            public class C373329 extends TextView {
                                public C373329(Context context22) {
                                    super(context22);
                                }

                                @Override // android.widget.TextView, android.view.View
                                public CharSequence getAccessibilityClassName() {
                                    return Button.class.getName();
                                }
                            }

                            public static /* synthetic */ String $r8$lambda$Bcg_Gj2lrwmQ8sB_vafHOInl0dY(int i) {
                                if (i == 0) {
                                    return LocaleController.getString(C2797R.string.MessageScheduleToday);
                                }
                                Calendar calendar = Calendar.getInstance();
                                int i2 = calendar.get(1);
                                calendar.add(6, i);
                                long timeInMillis = calendar.getTimeInMillis();
                                int i3 = calendar.get(1);
                                LocaleController.getInstance().getFormatterWeek().format(timeInMillis);
                                if (i3 == i2) {
                                    return LocaleController.getInstance().getFormatterWeek().format(timeInMillis) + ", " + LocaleController.getInstance().getFormatterScheduleDay().format(timeInMillis);
                                }
                                return LocaleController.getInstance().getFormatterScheduleYear().format(timeInMillis);
                            }

                            public static /* synthetic */ void $r8$lambda$zJcYDjtsWjBGPEQDmogSZWzLpB0(int[] iArr, int[] iArr2, String[] strArr, TextView textView) {
                                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                                spannableStringBuilder.append((CharSequence) LocaleController.getString(C2797R.string.MessageScheduledRepeatOption));
                                spannableStringBuilder.append((CharSequence) " ");
                                int length = spannableStringBuilder.length();
                                int i = 0;
                                while (true) {
                                    if (i >= iArr.length) {
                                        break;
                                    }
                                    if (iArr2[0] == iArr[i]) {
                                        spannableStringBuilder.append((CharSequence) strArr[i]);
                                        spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.bold()), length, spannableStringBuilder.length(), 33);
                                        break;
                                    }
                                    i++;
                                }
                                spannableStringBuilder.append((CharSequence) " v");
                                if (UserConfig.getInstance(UserConfig.selectedAccount).isPremium()) {
                                    ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2797R.drawable.arrows_select);
                                    coloredImageSpan.spaceScaleX = 0.7f;
                                    coloredImageSpan.translate(AndroidUtilities.m1036dp(-1.33f), AndroidUtilities.m1036dp(0.0f));
                                    coloredImageSpan.setAlpha(0.75f);
                                    spannableStringBuilder.setSpan(coloredImageSpan, spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 33);
                                } else {
                                    ColoredImageSpan coloredImageSpan2 = new ColoredImageSpan(C2797R.drawable.mini_switch_lock);
                                    coloredImageSpan2.spaceScaleX = 0.7f;
                                    coloredImageSpan2.translate(AndroidUtilities.m1036dp(-1.33f), AndroidUtilities.m1036dp(0.0f));
                                    coloredImageSpan2.setAlpha(0.75f);
                                    spannableStringBuilder.setSpan(coloredImageSpan2, spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 33);
                                }
                                textView.setText(spannableStringBuilder);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:27:0x003b  */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public static /* synthetic */ void $r8$lambda$GkQBPL0BWkhKY01Dhy1fGTHp2Dg(boolean[] r0, java.lang.String r1, long r2, long r4, org.telegram.p035ui.Components.NumberPicker r6, org.telegram.p035ui.Components.NumberPicker r7, org.telegram.p035ui.Components.NumberPicker r8, java.util.Calendar r9, org.telegram.ui.Components.AlertsCreator.ScheduleDatePickerDelegate r10, boolean[] r11, int[] r12, org.telegram.ui.ActionBar.BottomSheet.Builder r13, android.view.View r14) {
                                /*
                                    r14 = 0
                                    r0[r14] = r14
                                    if (r1 == 0) goto L8
                                    r0 = 3
                                L6:
                                    r3 = r0
                                    goto Lf
                                L8:
                                    int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                                    if (r0 != 0) goto Le
                                    r0 = 1
                                    goto L6
                                Le:
                                    r3 = r14
                                Lf:
                                    r1 = 0
                                    r2 = 0
                                    r4 = r6
                                    r5 = r7
                                    r6 = r8
                                    boolean r0 = checkScheduleDate(r1, r2, r3, r4, r5, r6)
                                    long r1 = java.lang.System.currentTimeMillis()
                                    r9.setTimeInMillis(r1)
                                    r1 = 6
                                    int r2 = r4.getValue()
                                    r9.add(r1, r2)
                                    r1 = 11
                                    int r2 = r5.getValue()
                                    r9.set(r1, r2)
                                    r1 = 12
                                    int r2 = r6.getValue()
                                    r9.set(r1, r2)
                                    if (r0 == 0) goto L45
                                    r0 = 13
                                    r9.set(r0, r14)
                                    r0 = 14
                                    r9.set(r0, r14)
                                L45:
                                    boolean r0 = r11[r14]
                                    long r1 = r9.getTimeInMillis()
                                    r3 = 1000(0x3e8, double:4.94E-321)
                                    long r1 = r1 / r3
                                    int r1 = (int) r1
                                    r2 = r12[r14]
                                    r10.didSelectDate(r0, r1, r2)
                                    java.lang.Runnable r0 = r13.getDismissRunnable()
                                    r0.run()
                                    return
                                */
                                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.AlertsCreator.$r8$lambda$GkQBPL0BWkhKY01Dhy1fGTHp2Dg(boolean[], java.lang.String, long, long, org.telegram.ui.Components.NumberPicker, org.telegram.ui.Components.NumberPicker, org.telegram.ui.Components.NumberPicker, java.util.Calendar, org.telegram.ui.Components.AlertsCreator$ScheduleDatePickerDelegate, boolean[], int[], org.telegram.ui.ActionBar.BottomSheet$Builder, android.view.View):void");
                            }

                            /* JADX INFO: renamed from: $r8$lambda$0GPag8ykDtMQNjee2vE-t4sb_yI */
                            public static /* synthetic */ void m9933$r8$lambda$0GPag8ykDtMQNjee2vEt4sb_yI(Runnable runnable, boolean[] zArr, DialogInterface dialogInterface) {
                                if (runnable == null || !zArr[0]) {
                                    return;
                                }
                                runnable.run();
                            }

                            public static /* synthetic */ void $r8$lambda$OYpLzZAwci2cmSKNhMzAlrD5y0o(FrameLayout frameLayout, Theme.ResourcesProvider resourcesProvider, BottomSheet bottomSheet, FrameLayout frameLayout2, int[] iArr, String[] strArr, final int[] iArr2, final Runnable runnable, View view) {
                                if (!UserConfig.getInstance(UserConfig.selectedAccount).isPremium()) {
                                    BulletinFactory.m1142of(frameLayout, resourcesProvider).createSimpleBulletin(C2797R.raw.star_premium_2, AndroidUtilities.premiumText(LocaleController.getString(C2797R.string.MessageScheduledRepeatPremium), new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda76
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            AlertsCreator.m9942$r8$lambda$9oen7LCyltAo_7tQNG398BuZ48();
                                        }
                                    })).show();
                                    return;
                                }
                                ItemOptions itemOptionsMakeOptions = ItemOptions.makeOptions(bottomSheet.container, resourcesProvider, frameLayout2);
                                for (int i = 0; i < iArr.length; i++) {
                                    final int i2 = iArr[i];
                                    itemOptionsMakeOptions.add(strArr[i], new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda77
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            AlertsCreator.$r8$lambda$hkQ4C1802vdiXmee6TeQCMU0BbU(iArr2, i2, runnable);
                                        }
                                    });
                                }
                                itemOptionsMakeOptions.setGravity(1);
                                itemOptionsMakeOptions.show();
                            }

                            /* JADX INFO: renamed from: $r8$lambda$9oen7LCyltAo_7t-QNG398BuZ48 */
                            public static /* synthetic */ void m9942$r8$lambda$9oen7LCyltAo_7tQNG398BuZ48() {
                                BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
                                if (safeLastFragment == null) {
                                    return;
                                }
                                BaseFragment.BottomSheetParams bottomSheetParams = new BaseFragment.BottomSheetParams();
                                bottomSheetParams.transitionFromLeft = true;
                                bottomSheetParams.allowNestedScroll = false;
                                safeLastFragment.showAsSheet(new PremiumPreviewFragment("schedule_repeat"), bottomSheetParams);
                            }

                            public static /* synthetic */ void $r8$lambda$hkQ4C1802vdiXmee6TeQCMU0BbU(int[] iArr, int i, Runnable runnable) {
                                iArr[0] = i;
                                runnable.run();
                            }

                            public static /* synthetic */ void $r8$lambda$0emMBtFvjpDcRXKWT2mtFmkbXG4(boolean[] zArr, RLottieDrawable rLottieDrawable, HintView2[] hintView2Arr, long j, Context context, long j2, ActionBarMenuItem actionBarMenuItem, BottomSheet bottomSheet, View view) {
                                String string;
                                boolean z = zArr[0];
                                zArr[0] = !z;
                                if (!z) {
                                    if (rLottieDrawable.getCurrentFrame() >= 40) {
                                        rLottieDrawable.setCurrentFrame(0);
                                    }
                                    rLottieDrawable.setCustomEndFrame(40);
                                    rLottieDrawable.start();
                                } else {
                                    if (rLottieDrawable.getCurrentFrame() < 40) {
                                        rLottieDrawable.setCurrentFrame(40);
                                    }
                                    rLottieDrawable.setCustomEndFrame(80);
                                    rLottieDrawable.start();
                                }
                                HintView2 hintView2 = hintView2Arr[0];
                                if (hintView2 != null) {
                                    hintView2.hide();
                                    hintView2Arr[0] = null;
                                }
                                TLRPC.User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(Long.valueOf(j));
                                TLRPC.Chat chat = MessagesController.getInstance(UserConfig.selectedAccount).getChat(Long.valueOf(-j));
                                final HintView2 hintView22 = new HintView2(context, 3);
                                hintView2Arr[0] = hintView22;
                                hintView22.setRoundingWithCornerEffect(false);
                                hintView22.setPadding(AndroidUtilities.m1036dp(8.0f), 0, AndroidUtilities.m1036dp(8.0f), 0);
                                hintView22.setRounding(20.0f);
                                hintView22.setShadow(AndroidUtilities.m1036dp(12.0f), 0.0f, AndroidUtilities.m1036dp(4.0f), Theme.multAlpha(-16777216, 0.25f));
                                if (ChatObject.isChannelAndNotMegaGroup(chat)) {
                                    string = LocaleController.getString(zArr[0] ? C2797R.string.ScheduleNotifyOnChannel : C2797R.string.ScheduleNotifyOffChannel);
                                } else if (chat != null || user == null) {
                                    string = LocaleController.getString(zArr[0] ? C2797R.string.ScheduleNotifyOnGroup : C2797R.string.ScheduleNotifyOffGroup);
                                } else if (j == j2) {
                                    string = LocaleController.getString(zArr[0] ? C2797R.string.ScheduleNotifyOnSelf : C2797R.string.ScheduleNotifyOffSelf);
                                } else {
                                    string = LocaleController.formatString(zArr[0] ? C2797R.string.ScheduleNotifyOnChat : C2797R.string.ScheduleNotifyOffChat, UserObject.getForcedFirstName(user));
                                }
                                hintView22.setText(string);
                                hintView22.setDuration(5000L);
                                hintView22.setJoint(1.0f, -((actionBarMenuItem != null ? 42 : -8) + 20));
                                hintView22.setOnHiddenListener(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda46
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AndroidUtilities.removeFromParent(hintView22);
                                    }
                                });
                                bottomSheet.getContainerView().setClipToPadding(false);
                                bottomSheet.getContainerView().setClipChildren(false);
                                bottomSheet.getContainerView().addView(hintView22, LayoutHelper.createFrame(-1, 200.0f, 48, 0.0f, -194.0f, 0.0f, 0.0f));
                                hintView22.show();
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX WARN: Type inference failed for: r11v0, types: [android.view.ViewGroup, android.widget.LinearLayout, org.telegram.ui.Components.AlertsCreator$32] */
                            /* JADX WARN: Type inference failed for: r17v3 */
                            /* JADX WARN: Type inference failed for: r17v4 */
                            /* JADX WARN: Type inference failed for: r17v5 */
                            /* JADX WARN: Type inference failed for: r17v6 */
                            /* JADX WARN: Type inference failed for: r17v7 */
                            /* JADX WARN: Type inference failed for: r2v2, types: [android.view.View, android.view.ViewGroup] */
                            /* JADX WARN: Type inference failed for: r9v0, types: [org.telegram.ui.ActionBar.BottomSheet$Builder] */
                            public static BottomSheet.Builder createDatePickerDialog(Context context, String str, String str2, long j, final ScheduleDatePickerDelegate scheduleDatePickerDelegate) {
                                ScheduleDatePickerColors scheduleDatePickerColors;
                                float f;
                                ?? r17;
                                if (context == null) {
                                    return null;
                                }
                                ScheduleDatePickerColors scheduleDatePickerColors2 = new ScheduleDatePickerColors();
                                final ?? builder = new BottomSheet.Builder(context, false);
                                builder.setApplyBottomPadding(false);
                                final NumberPicker numberPicker = new NumberPicker(context);
                                numberPicker.setTextColor(scheduleDatePickerColors2.textColor);
                                numberPicker.setTextOffset(AndroidUtilities.m1036dp(10.0f));
                                numberPicker.setItemCount(5);
                                final C373530 c373530 = new NumberPicker(context) { // from class: org.telegram.ui.Components.AlertsCreator.30
                                    public C373530(Context context2) {
                                        super(context2);
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i) {
                                        return LocaleController.formatPluralString("Hours", i, new Object[0]);
                                    }
                                };
                                c373530.setItemCount(5);
                                c373530.setTextColor(scheduleDatePickerColors2.textColor);
                                c373530.setTextOffset(-AndroidUtilities.m1036dp(10.0f));
                                final C373631 c373631 = new NumberPicker(context2) { // from class: org.telegram.ui.Components.AlertsCreator.31
                                    public C373631(Context context2) {
                                        super(context2);
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i) {
                                        return LocaleController.formatPluralString("Minutes", i, new Object[0]);
                                    }
                                };
                                c373631.setItemCount(5);
                                c373631.setTextColor(scheduleDatePickerColors2.textColor);
                                c373631.setTextOffset(-AndroidUtilities.m1036dp(34.0f));
                                ?? c373732 = new LinearLayout(context2) { // from class: org.telegram.ui.Components.AlertsCreator.32
                                    boolean ignoreLayout = false;
                                    final /* synthetic */ NumberPicker val$dayPicker;
                                    final /* synthetic */ NumberPicker val$hourPicker;
                                    final /* synthetic */ NumberPicker val$minutePicker;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C373732(Context context2, final NumberPicker numberPicker2, final NumberPicker c3735302, final NumberPicker c3736312) {
                                        super(context2);
                                        numberPicker = numberPicker2;
                                        numberPicker = c3735302;
                                        numberPicker = c3736312;
                                        this.ignoreLayout = false;
                                    }

                                    @Override // android.widget.LinearLayout, android.view.View
                                    public void onMeasure(int i, int i2) {
                                        this.ignoreLayout = true;
                                        Point point = AndroidUtilities.displaySize;
                                        int i3 = point.x > point.y ? 3 : 5;
                                        numberPicker.setItemCount(i3);
                                        numberPicker.setItemCount(i3);
                                        numberPicker.setItemCount(i3);
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                        this.ignoreLayout = false;
                                        super.onMeasure(i, i2);
                                    }

                                    @Override // android.view.View, android.view.ViewParent
                                    public void requestLayout() {
                                        if (this.ignoreLayout) {
                                            return;
                                        }
                                        super.requestLayout();
                                    }
                                };
                                c373732.setOrientation(1);
                                FrameLayout frameLayout = new FrameLayout(context2);
                                c373732.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
                                TextView textView = new TextView(context2);
                                textView.setText(str);
                                textView.setTextColor(scheduleDatePickerColors2.textColor);
                                textView.setTextSize(1, 20.0f);
                                textView.setTypeface(AndroidUtilities.bold());
                                frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
                                textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda184
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        return AlertsCreator.$r8$lambda$Pra6gkzw6DzoCbGrKEEryT8pvrM(view, motionEvent);
                                    }
                                });
                                LinearLayout linearLayout = new LinearLayout(context2);
                                linearLayout.setOrientation(0);
                                linearLayout.setWeightSum(1.0f);
                                c373732.addView(linearLayout, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
                                final Calendar calendar = Calendar.getInstance();
                                C373833 c373833 = new TextView(context2) { // from class: org.telegram.ui.Components.AlertsCreator.33
                                    public C373833(Context context2) {
                                        super(context2);
                                    }

                                    @Override // android.widget.TextView, android.view.View
                                    public CharSequence getAccessibilityClassName() {
                                        return Button.class.getName();
                                    }
                                };
                                linearLayout.addView(numberPicker2, LayoutHelper.createLinear(0, 270, 0.5f));
                                numberPicker2.setMinValue(0);
                                numberPicker2.setMaxValue(365);
                                numberPicker2.setWrapSelectorWheel(false);
                                numberPicker2.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda185
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i) {
                                        return AlertsCreator.$r8$lambda$mjXXBs_EYglfCjz8jr1KUyUajnE(i);
                                    }
                                });
                                NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda186
                                    @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                                    public final void onValueChange(NumberPicker numberPicker2, int i, int i2) {
                                        AlertsCreator.checkScheduleDate(null, null, 0, numberPicker2, c3735302, c3736312);
                                    }
                                };
                                numberPicker2.setOnValueChangedListener(onValueChangeListener);
                                c3735302.setMinValue(0);
                                c3735302.setMaxValue(23);
                                linearLayout.addView(c3735302, LayoutHelper.createLinear(0, 270, 0.2f));
                                c3735302.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda187
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i) {
                                        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i));
                                    }
                                });
                                c3735302.setOnValueChangedListener(onValueChangeListener);
                                c3736312.setMinValue(0);
                                c3736312.setMaxValue(59);
                                c3736312.setValue(0);
                                c3736312.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda188
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i) {
                                        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i));
                                    }
                                });
                                linearLayout.addView(c3736312, LayoutHelper.createLinear(0, 270, 0.3f));
                                c3736312.setOnValueChangedListener(onValueChangeListener);
                                if (j <= 0 || j == 2147483646) {
                                    scheduleDatePickerColors = scheduleDatePickerColors2;
                                    f = 34.0f;
                                    r17 = c373732;
                                } else {
                                    ?? r172 = c373732;
                                    long j2 = j * 1000;
                                    f = 34.0f;
                                    calendar.setTimeInMillis(System.currentTimeMillis());
                                    calendar.set(12, 0);
                                    calendar.set(13, 0);
                                    calendar.set(14, 0);
                                    calendar.set(11, 0);
                                    scheduleDatePickerColors = scheduleDatePickerColors2;
                                    int timeInMillis = (int) ((j2 - calendar.getTimeInMillis()) / DurationKt.MILLIS_IN_DAY);
                                    calendar.setTimeInMillis(j2);
                                    r17 = r172;
                                    if (timeInMillis >= 0) {
                                        c3736312.setValue(calendar.get(12));
                                        c3735302.setValue(calendar.get(11));
                                        numberPicker2.setValue(timeInMillis);
                                        r17 = r172;
                                    }
                                }
                                checkScheduleDate(null, null, 0, numberPicker2, c3735302, c3736312);
                                c373833.setPadding(AndroidUtilities.m1036dp(f), 0, AndroidUtilities.m1036dp(f), 0);
                                c373833.setGravity(17);
                                ScheduleDatePickerColors scheduleDatePickerColors3 = scheduleDatePickerColors;
                                c373833.setTextColor(scheduleDatePickerColors3.buttonTextColor);
                                c373833.setTextSize(1, 14.0f);
                                c373833.setTypeface(AndroidUtilities.bold());
                                c373833.setBackgroundDrawable(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(8.0f), scheduleDatePickerColors3.buttonBackgroundColor, scheduleDatePickerColors3.buttonBackgroundPressedColor));
                                c373833.setText(str2);
                                ?? r2 = r17;
                                r2.addView(c373833, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
                                c373833.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda189
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.$r8$lambda$mpShplHcuyEq2sAp4onBC2xj14Y(numberPicker2, c3735302, c3736312, calendar, scheduleDatePickerDelegate, builder, view);
                                    }
                                });
                                builder.setCustomView(r2);
                                BottomSheet bottomSheetShow = builder.show();
                                bottomSheetShow.setBackgroundColor(scheduleDatePickerColors3.backgroundColor);
                                bottomSheetShow.fixNavigationBar(scheduleDatePickerColors3.backgroundColor);
                                return builder;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$30 */
                            public class C373530 extends NumberPicker {
                                public C373530(Context context2) {
                                    super(context2);
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i) {
                                    return LocaleController.formatPluralString("Hours", i, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$31 */
                            public class C373631 extends NumberPicker {
                                public C373631(Context context2) {
                                    super(context2);
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i) {
                                    return LocaleController.formatPluralString("Minutes", i, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$32 */
                            public class C373732 extends LinearLayout {
                                boolean ignoreLayout = false;
                                final /* synthetic */ NumberPicker val$dayPicker;
                                final /* synthetic */ NumberPicker val$hourPicker;
                                final /* synthetic */ NumberPicker val$minutePicker;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C373732(Context context2, final NumberPicker numberPicker2, final NumberPicker c3735302, final NumberPicker c3736312) {
                                    super(context2);
                                    numberPicker = numberPicker2;
                                    numberPicker = c3735302;
                                    numberPicker = c3736312;
                                    this.ignoreLayout = false;
                                }

                                @Override // android.widget.LinearLayout, android.view.View
                                public void onMeasure(int i, int i2) {
                                    this.ignoreLayout = true;
                                    Point point = AndroidUtilities.displaySize;
                                    int i3 = point.x > point.y ? 3 : 5;
                                    numberPicker.setItemCount(i3);
                                    numberPicker.setItemCount(i3);
                                    numberPicker.setItemCount(i3);
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                    this.ignoreLayout = false;
                                    super.onMeasure(i, i2);
                                }

                                @Override // android.view.View, android.view.ViewParent
                                public void requestLayout() {
                                    if (this.ignoreLayout) {
                                        return;
                                    }
                                    super.requestLayout();
                                }
                            }

                            public static /* synthetic */ boolean $r8$lambda$Pra6gkzw6DzoCbGrKEEryT8pvrM(View view, MotionEvent motionEvent) {
                                return true;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$33 */
                            public class C373833 extends TextView {
                                public C373833(Context context2) {
                                    super(context2);
                                }

                                @Override // android.widget.TextView, android.view.View
                                public CharSequence getAccessibilityClassName() {
                                    return Button.class.getName();
                                }
                            }

                            public static /* synthetic */ String $r8$lambda$mjXXBs_EYglfCjz8jr1KUyUajnE(int i) {
                                if (i == 0) {
                                    return LocaleController.getString(C2797R.string.MessageScheduleToday);
                                }
                                Calendar calendar = Calendar.getInstance();
                                int i2 = calendar.get(1);
                                calendar.add(6, i);
                                long timeInMillis = calendar.getTimeInMillis();
                                if (calendar.get(1) == i2) {
                                    return LocaleController.getInstance().getFormatterScheduleDay().format(timeInMillis);
                                }
                                return LocaleController.getInstance().getFormatterScheduleYear().format(timeInMillis);
                            }

                            public static /* synthetic */ void $r8$lambda$mpShplHcuyEq2sAp4onBC2xj14Y(NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, Calendar calendar, ScheduleDatePickerDelegate scheduleDatePickerDelegate, BottomSheet.Builder builder, View view) {
                                boolean zCheckScheduleDate = checkScheduleDate(null, null, 0, numberPicker, numberPicker2, numberPicker3);
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.add(6, numberPicker.getValue());
                                calendar.set(11, numberPicker2.getValue());
                                calendar.set(12, numberPicker3.getValue());
                                if (zCheckScheduleDate) {
                                    calendar.set(13, 0);
                                    calendar.set(14, 0);
                                }
                                scheduleDatePickerDelegate.didSelectDate(true, (int) (calendar.getTimeInMillis() / 1000), 0);
                                builder.getDismissRunnable().run();
                            }

                            public static BottomSheet.Builder createBirthdayPickerDialog(Context context, String str, String str2, TL_account.TL_birthday tL_birthday, final Utilities.Callback<TL_account.TL_birthday> callback, Runnable runnable, boolean z, boolean z2, Theme.ResourcesProvider resourcesProvider) {
                                if (context == null) {
                                    return null;
                                }
                                final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
                                builder.setApplyBottomPadding(false);
                                final NumberPicker numberPicker = new NumberPicker(context, resourcesProvider);
                                numberPicker.setTextOffset(AndroidUtilities.m1036dp(10.0f));
                                numberPicker.setItemCount(5);
                                final NumberPicker numberPicker2 = new NumberPicker(context, resourcesProvider);
                                numberPicker2.setItemCount(5);
                                numberPicker2.setTextOffset(-AndroidUtilities.m1036dp(10.0f));
                                final NumberPicker numberPicker3 = new NumberPicker(context, resourcesProvider);
                                numberPicker3.setItemCount(5);
                                numberPicker3.setTextOffset(-AndroidUtilities.m1036dp(24.0f));
                                C373934 c373934 = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.34
                                    boolean ignoreLayout = false;
                                    final /* synthetic */ NumberPicker val$dayPicker;
                                    final /* synthetic */ NumberPicker val$monthPicker;
                                    final /* synthetic */ NumberPicker val$yearPicker;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C373934(Context context2, final NumberPicker numberPicker4, final NumberPicker numberPicker22, final NumberPicker numberPicker32) {
                                        super(context2);
                                        numberPicker = numberPicker4;
                                        numberPicker = numberPicker22;
                                        numberPicker = numberPicker32;
                                        this.ignoreLayout = false;
                                    }

                                    @Override // android.widget.LinearLayout, android.view.View
                                    public void onMeasure(int i, int i2) {
                                        this.ignoreLayout = true;
                                        Point point = AndroidUtilities.displaySize;
                                        int i3 = point.x > point.y ? 3 : 5;
                                        numberPicker.setItemCount(i3);
                                        numberPicker.setItemCount(i3);
                                        numberPicker.setItemCount(i3);
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                        this.ignoreLayout = false;
                                        super.onMeasure(i, i2);
                                    }

                                    @Override // android.view.View, android.view.ViewParent
                                    public void requestLayout() {
                                        if (this.ignoreLayout) {
                                            return;
                                        }
                                        super.requestLayout();
                                    }
                                };
                                c373934.setOrientation(1);
                                FrameLayout frameLayout = new FrameLayout(context2);
                                c373934.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
                                TextView textView = new TextView(context2);
                                textView.setText(str);
                                textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
                                textView.setTextSize(1, 20.0f);
                                textView.setTypeface(AndroidUtilities.bold());
                                frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
                                textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda57
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        return AlertsCreator.$r8$lambda$5Bg7Xga8RtuOpbJY_PJ6TwVAICU(view, motionEvent);
                                    }
                                });
                                LinearLayout linearLayout = new LinearLayout(context2);
                                linearLayout.setGravity(17);
                                linearLayout.setOrientation(0);
                                linearLayout.setWeightSum(1.0f);
                                c373934.addView(linearLayout, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
                                Calendar calendar = Calendar.getInstance();
                                int i = calendar.get(1) - 149;
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                final int i2 = calendar.get(5);
                                final int i3 = calendar.get(2);
                                final int i4 = calendar.get(1);
                                final int i5 = i4 + 1;
                                final Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda59
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AlertsCreator.$r8$lambda$Ft9NqWPMDJHXbWuNFzRv7Q_bLh4(numberPicker32, i5, numberPicker4, numberPicker22, i4, i3, i2);
                                    }
                                };
                                System.currentTimeMillis();
                                C374035 c374035 = new TextView(context2) { // from class: org.telegram.ui.Components.AlertsCreator.35
                                    public C374035(Context context2) {
                                        super(context2);
                                    }

                                    @Override // android.widget.TextView, android.view.View
                                    public CharSequence getAccessibilityClassName() {
                                        return Button.class.getName();
                                    }
                                };
                                linearLayout.addView(numberPicker4, LayoutHelper.createLinear(0, 270, 0.25f));
                                numberPicker4.setMinValue(1);
                                numberPicker4.setMaxValue(31);
                                numberPicker4.setWrapSelectorWheel(false);
                                numberPicker4.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda60
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i6) {
                                        return AlertsCreator.m9989$r8$lambda$fezgOfk3ceoMbZs0nabTjQSsAY(i6);
                                    }
                                });
                                NumberPicker.OnScrollListener onScrollListener = new NumberPicker.OnScrollListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda61
                                    @Override // org.telegram.ui.Components.NumberPicker.OnScrollListener
                                    public final void onScrollStateChange(NumberPicker numberPicker4, int i6) {
                                        AlertsCreator.m9990$r8$lambda$gzWsxxlWtfy6yKaXa7qnUrdfJM(runnable2, numberPicker4, i6);
                                    }
                                };
                                numberPicker4.setOnScrollListener(onScrollListener);
                                numberPicker22.setMinValue(0);
                                numberPicker22.setMaxValue(11);
                                numberPicker22.setWrapSelectorWheel(false);
                                linearLayout.addView(numberPicker22, LayoutHelper.createLinear(0, 270, 0.5f));
                                numberPicker22.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda62
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i6) {
                                        return AlertsCreator.$r8$lambda$GLsZvrDWJql175SLGyjZ55OxSsk(i6);
                                    }
                                });
                                numberPicker22.setOnScrollListener(onScrollListener);
                                numberPicker32.setMinValue(i);
                                numberPicker32.setMaxValue(i5);
                                numberPicker32.setWrapSelectorWheel(false);
                                numberPicker32.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda63
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i6) {
                                        return AlertsCreator.$r8$lambda$ilLuUksOAsxYghrH0SfvOlbvjKw(i5, i6);
                                    }
                                });
                                linearLayout.addView(numberPicker32, LayoutHelper.createLinear(0, 270, 0.25f));
                                numberPicker32.setOnScrollListener(onScrollListener);
                                if (tL_birthday != null) {
                                    numberPicker4.setValue(tL_birthday.day);
                                    numberPicker22.setValue(tL_birthday.month - 1);
                                    if ((tL_birthday.flags & 1) != 0) {
                                        numberPicker32.setValue(tL_birthday.year);
                                    } else {
                                        numberPicker32.setValue(i5);
                                    }
                                } else {
                                    numberPicker4.setValue(calendar.get(5));
                                    numberPicker22.setValue(calendar.get(2));
                                    numberPicker32.setValue(i5);
                                }
                                runnable2.run();
                                if (runnable != null) {
                                    FrameLayout frameLayout2 = new FrameLayout(context2);
                                    final LinkSpanDrawable.LinksTextView linksTextView = new LinkSpanDrawable.LinksTextView(context2);
                                    linksTextView.setPadding(AndroidUtilities.m1036dp(8.0f), 0, AndroidUtilities.m1036dp(8.0f), 0);
                                    linksTextView.setTextSize(1, 13.0f);
                                    linksTextView.setTextColor(Theme.getColor(Theme.key_dialogTextGray2, resourcesProvider));
                                    linksTextView.setLinkTextColor(Theme.getColor(Theme.key_chat_messageLinkIn, resourcesProvider));
                                    linksTextView.setGravity(17);
                                    frameLayout2.addView(linksTextView, LayoutHelper.createFrame(-2, -2, 17));
                                    c373934.addView(frameLayout2, LayoutHelper.createLinear(-1, -2));
                                    final int i6 = UserConfig.selectedAccount;
                                    final Runnable runnable3 = new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda64
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            AlertsCreator.$r8$lambda$C80QYZma6Cuoi7VvEVOUq2Ue1qU(i6, linksTextView);
                                        }
                                    };
                                    runnable3.run();
                                    NotificationCenter.getInstance(i6).listen(frameLayout2, NotificationCenter.privacyRulesUpdated, new Utilities.Callback() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda65
                                        @Override // org.telegram.messenger.Utilities.Callback
                                        public final void run(Object obj) {
                                            runnable3.run();
                                        }
                                    });
                                    ContactsController.getInstance(i6).loadPrivacySettings();
                                }
                                if (z) {
                                    ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(context2, false, resourcesProvider);
                                    buttonWithCounterView.setText(LocaleController.getString(C2797R.string.DateOfBirthHideYear), false);
                                    buttonWithCounterView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda66
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            AlertsCreator.$r8$lambda$4fNDGPIxveNZTf7EhudMw2nAFGs(numberPicker32, i5, runnable2, view);
                                        }
                                    });
                                    c373934.addView(buttonWithCounterView, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 4));
                                }
                                c374035.setPadding(AndroidUtilities.m1036dp(34.0f), 0, AndroidUtilities.m1036dp(34.0f), 0);
                                c374035.setGravity(17);
                                c374035.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText, resourcesProvider));
                                c374035.setTextSize(1, 14.0f);
                                c374035.setTypeface(AndroidUtilities.bold());
                                c374035.setText(str2);
                                c374035.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(8.0f), Theme.getColor(Theme.key_featuredStickers_addButton, resourcesProvider), Theme.getColor(Theme.key_featuredStickers_addButtonPressed, resourcesProvider)));
                                ScaleStateListAnimator.apply(c374035);
                                c373934.addView(c374035, LayoutHelper.createLinear(-1, 48, 83, 16, z ? 0 : 15, 16, z2 ? 0 : 16));
                                c374035.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda67
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.$r8$lambda$0QaJBJLcK8bebXMQIS3Ye9yanVI(numberPicker4, numberPicker22, numberPicker32, i5, builder, callback, view);
                                    }
                                });
                                if (z2) {
                                    ButtonWithCounterView buttonWithCounterView2 = new ButtonWithCounterView(context2, false, resourcesProvider);
                                    buttonWithCounterView2.setText(LocaleController.getString(C2797R.string.BirthdayRemove), false);
                                    buttonWithCounterView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda58
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            AlertsCreator.$r8$lambda$grIKmy6FflIhJCv3JF6JN_Kgu_k(builder, callback, view);
                                        }
                                    });
                                    c373934.addView(buttonWithCounterView2, LayoutHelper.createLinear(-1, 48, 83, 16, 4, 16, 16));
                                }
                                builder.setCustomView(c373934);
                                return builder;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$34 */
                            public class C373934 extends LinearLayout {
                                boolean ignoreLayout = false;
                                final /* synthetic */ NumberPicker val$dayPicker;
                                final /* synthetic */ NumberPicker val$monthPicker;
                                final /* synthetic */ NumberPicker val$yearPicker;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C373934(Context context2, final NumberPicker numberPicker4, final NumberPicker numberPicker22, final NumberPicker numberPicker32) {
                                    super(context2);
                                    numberPicker = numberPicker4;
                                    numberPicker = numberPicker22;
                                    numberPicker = numberPicker32;
                                    this.ignoreLayout = false;
                                }

                                @Override // android.widget.LinearLayout, android.view.View
                                public void onMeasure(int i, int i2) {
                                    this.ignoreLayout = true;
                                    Point point = AndroidUtilities.displaySize;
                                    int i3 = point.x > point.y ? 3 : 5;
                                    numberPicker.setItemCount(i3);
                                    numberPicker.setItemCount(i3);
                                    numberPicker.setItemCount(i3);
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                    this.ignoreLayout = false;
                                    super.onMeasure(i, i2);
                                }

                                @Override // android.view.View, android.view.ViewParent
                                public void requestLayout() {
                                    if (this.ignoreLayout) {
                                        return;
                                    }
                                    super.requestLayout();
                                }
                            }

                            public static /* synthetic */ boolean $r8$lambda$5Bg7Xga8RtuOpbJY_PJ6TwVAICU(View view, MotionEvent motionEvent) {
                                return true;
                            }

                            public static /* synthetic */ void $r8$lambda$Ft9NqWPMDJHXbWuNFzRv7Q_bLh4(NumberPicker numberPicker, int i, NumberPicker numberPicker2, NumberPicker numberPicker3, int i2, int i3, int i4) {
                                if (numberPicker.getValue() == i) {
                                    numberPicker2.setMinValue(1);
                                    try {
                                        numberPicker2.setMaxValue(YearMonth.m637of(2024, numberPicker3.getValue() + 1).lengthOfMonth());
                                    } catch (Exception e) {
                                        FileLog.m1048e(e);
                                        numberPicker2.setMaxValue(31);
                                    }
                                    numberPicker3.setMinValue(0);
                                    numberPicker3.setMaxValue(11);
                                    return;
                                }
                                if (numberPicker.getValue() == i2) {
                                    numberPicker3.setMinValue(0);
                                    numberPicker3.setMaxValue(i3);
                                    if (numberPicker3.getValue() == i3) {
                                        numberPicker2.setMinValue(1);
                                        numberPicker2.setMaxValue(i4);
                                        return;
                                    }
                                    numberPicker2.setMinValue(1);
                                    try {
                                        numberPicker2.setMaxValue(YearMonth.m637of(numberPicker.getValue(), numberPicker3.getValue() + 1).lengthOfMonth());
                                        return;
                                    } catch (Exception e2) {
                                        FileLog.m1048e(e2);
                                        numberPicker2.setMaxValue(31);
                                        return;
                                    }
                                }
                                numberPicker2.setMinValue(1);
                                try {
                                    numberPicker2.setMaxValue(YearMonth.m637of(numberPicker.getValue(), numberPicker3.getValue() + 1).lengthOfMonth());
                                } catch (Exception e3) {
                                    FileLog.m1048e(e3);
                                    numberPicker2.setMaxValue(31);
                                }
                                numberPicker3.setMinValue(0);
                                numberPicker3.setMaxValue(11);
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$35 */
                            public class C374035 extends TextView {
                                public C374035(Context context2) {
                                    super(context2);
                                }

                                @Override // android.widget.TextView, android.view.View
                                public CharSequence getAccessibilityClassName() {
                                    return Button.class.getName();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$fezgOfk3c-eoMbZs0nabTjQSsAY */
                            public static /* synthetic */ String m9989$r8$lambda$fezgOfk3ceoMbZs0nabTjQSsAY(int i) {
                                return _UrlKt.FRAGMENT_ENCODE_SET + i;
                            }

                            /* JADX INFO: renamed from: $r8$lambda$gzWsx-xlWtfy6yKaXa7qnUrdfJM */
                            public static /* synthetic */ void m9990$r8$lambda$gzWsxxlWtfy6yKaXa7qnUrdfJM(Runnable runnable, NumberPicker numberPicker, int i) {
                                if (i == 0) {
                                    runnable.run();
                                }
                            }

                            public static /* synthetic */ String $r8$lambda$GLsZvrDWJql175SLGyjZ55OxSsk(int i) {
                                switch (i) {
                                    case 0:
                                        return LocaleController.getString(C2797R.string.January);
                                    case 1:
                                        return LocaleController.getString(C2797R.string.February);
                                    case 2:
                                        return LocaleController.getString(C2797R.string.March);
                                    case 3:
                                        return LocaleController.getString(C2797R.string.April);
                                    case 4:
                                        return LocaleController.getString(C2797R.string.May);
                                    case 5:
                                        return LocaleController.getString(C2797R.string.June);
                                    case 6:
                                        return LocaleController.getString(C2797R.string.July);
                                    case 7:
                                        return LocaleController.getString(C2797R.string.August);
                                    case 8:
                                        return LocaleController.getString(C2797R.string.September);
                                    case 9:
                                        return LocaleController.getString(C2797R.string.October);
                                    case 10:
                                        return LocaleController.getString(C2797R.string.November);
                                    default:
                                        return LocaleController.getString(C2797R.string.December);
                                }
                            }

                            public static /* synthetic */ String $r8$lambda$ilLuUksOAsxYghrH0SfvOlbvjKw(int i, int i2) {
                                return i2 == i ? "—" : String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i2));
                            }

                            public static /* synthetic */ void $r8$lambda$C80QYZma6Cuoi7VvEVOUq2Ue1qU(int i, LinkSpanDrawable.LinksTextView linksTextView) {
                                final ArrayList<TLRPC.PrivacyRule> privacyRules = ContactsController.getInstance(i).getPrivacyRules(11);
                                String string = LocaleController.getString(C2797R.string.EditProfileBirthdayInfoContacts);
                                if (privacyRules != null && !privacyRules.isEmpty()) {
                                    int i2 = 0;
                                    while (true) {
                                        if (i2 >= privacyRules.size()) {
                                            break;
                                        }
                                        if (privacyRules.get(i2) instanceof TLRPC.TL_privacyValueAllowContacts) {
                                            string = LocaleController.getString(C2797R.string.EditProfileBirthdayInfoContacts);
                                            break;
                                        }
                                        if ((privacyRules.get(i2) instanceof TLRPC.TL_privacyValueAllowAll) || (privacyRules.get(i2) instanceof TLRPC.TL_privacyValueDisallowAll)) {
                                            string = LocaleController.getString(C2797R.string.EditProfileBirthdayInfo);
                                        }
                                        i2++;
                                    }
                                }
                                linksTextView.setText(AndroidUtilities.replaceArrows(AndroidUtilities.replaceSingleTag(string, new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda93
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AlertsCreator.m9962$r8$lambda$Ms1hUySs7IZYUNVkNG8bnGMFlk(privacyRules);
                                    }
                                }), true, AndroidUtilities.m1036dp(2.6666667f), AndroidUtilities.m1036dp(0.66f)));
                            }

                            /* JADX INFO: renamed from: $r8$lambda$Ms1hUySs7IZYUNVkNG8bnG-MFlk */
                            public static /* synthetic */ void m9962$r8$lambda$Ms1hUySs7IZYUNVkNG8bnGMFlk(ArrayList arrayList) {
                                BaseFragment lastFragment;
                                if (arrayList == null || (lastFragment = LaunchActivity.getLastFragment()) == null) {
                                    return;
                                }
                                BaseFragment.BottomSheetParams bottomSheetParams = new BaseFragment.BottomSheetParams();
                                bottomSheetParams.transitionFromLeft = true;
                                bottomSheetParams.allowNestedScroll = false;
                                lastFragment.showAsSheet(new PrivacyControlActivity(11), bottomSheetParams);
                            }

                            public static /* synthetic */ void $r8$lambda$4fNDGPIxveNZTf7EhudMw2nAFGs(NumberPicker numberPicker, int i, Runnable runnable, View view) {
                                numberPicker.setValue(i);
                                runnable.run();
                            }

                            public static /* synthetic */ void $r8$lambda$0QaJBJLcK8bebXMQIS3Ye9yanVI(NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, int i, BottomSheet.Builder builder, Utilities.Callback callback, View view) {
                                TL_account.TL_birthday tL_birthday = new TL_account.TL_birthday();
                                tL_birthday.day = numberPicker.getValue();
                                tL_birthday.month = numberPicker2.getValue() + 1;
                                if (numberPicker3.getValue() != i) {
                                    tL_birthday.flags |= 1;
                                    tL_birthday.year = numberPicker3.getValue();
                                }
                                builder.getDismissRunnable().run();
                                callback.run(tL_birthday);
                            }

                            public static /* synthetic */ void $r8$lambda$grIKmy6FflIhJCv3JF6JN_Kgu_k(BottomSheet.Builder builder, Utilities.Callback callback, View view) {
                                builder.getDismissRunnable().run();
                                callback.run(null);
                            }

                            public static BottomSheet.Builder createFormattedDatePickerDialog(Context context, final FormattedDatePickerDelegate formattedDatePickerDelegate, final Runnable runnable, final Theme.ResourcesProvider resourcesProvider) {
                                if (context == null) {
                                    return null;
                                }
                                ScheduleDatePickerColors scheduleDatePickerColors = new ScheduleDatePickerColors(resourcesProvider);
                                final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
                                builder.setApplyBottomPadding(false);
                                long jCurrentTimeMillis = System.currentTimeMillis();
                                final Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(jCurrentTimeMillis);
                                final int i = calendar.get(1);
                                final NumberPicker numberPicker = new NumberPicker(context, resourcesProvider);
                                numberPicker.setTextColor(scheduleDatePickerColors.textColor);
                                numberPicker.setTextOffset(AndroidUtilities.m1036dp(10.0f));
                                numberPicker.setItemCount(5);
                                numberPicker.setMinValue(1);
                                numberPicker.setMaxValue(31);
                                numberPicker.setWrapSelectorWheel(false);
                                numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda34
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i2) {
                                        return AlertsCreator.$r8$lambda$ye1U0V08DQNo8acr6yyP4Uxnvbc(i2);
                                    }
                                });
                                final NumberPicker numberPicker2 = new NumberPicker(context, resourcesProvider);
                                numberPicker2.setTextColor(scheduleDatePickerColors.textColor);
                                numberPicker2.setTextOffset(-AndroidUtilities.m1036dp(10.0f));
                                numberPicker2.setItemCount(5);
                                numberPicker2.setMinValue(0);
                                numberPicker2.setMaxValue(239);
                                numberPicker2.setValue(120);
                                numberPicker2.setWrapSelectorWheel(false);
                                numberPicker2.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda37
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i2) {
                                        return AlertsCreator.$r8$lambda$RXjTGCyixgYVH6oYGguD5U8Uajo(calendar, i, i2);
                                    }
                                });
                                final NumberPicker numberPicker3 = new NumberPicker(context, resourcesProvider);
                                numberPicker3.setContentDescriptionCallback(new Utilities.CallbackReturn() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda38
                                    @Override // org.telegram.messenger.Utilities.CallbackReturn
                                    public final Object run(Object obj) {
                                        return LocaleController.formatPluralString("Hours", ((Integer) obj).intValue(), new Object[0]);
                                    }
                                });
                                numberPicker3.setWrapSelectorWheel(true);
                                numberPicker3.setAllItemsCount(24);
                                numberPicker3.setItemCount(5);
                                numberPicker3.setTextColor(scheduleDatePickerColors.textColor);
                                numberPicker3.setTextOffset(AndroidUtilities.m1036dp(10.0f));
                                numberPicker3.setMinValue(0);
                                numberPicker3.setMaxValue(23);
                                numberPicker3.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda39
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i2) {
                                        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i2));
                                    }
                                });
                                final NumberPicker numberPicker4 = new NumberPicker(context, resourcesProvider);
                                numberPicker4.setContentDescriptionCallback(new Utilities.CallbackReturn() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda40
                                    @Override // org.telegram.messenger.Utilities.CallbackReturn
                                    public final Object run(Object obj) {
                                        return LocaleController.formatPluralString("Minutes", ((Integer) obj).intValue(), new Object[0]);
                                    }
                                });
                                numberPicker4.setWrapSelectorWheel(true);
                                numberPicker4.setAllItemsCount(60);
                                numberPicker4.setItemCount(5);
                                numberPicker4.setTextColor(scheduleDatePickerColors.textColor);
                                numberPicker4.setTextOffset(-AndroidUtilities.m1036dp(10.0f));
                                numberPicker4.setMinValue(0);
                                numberPicker4.setMaxValue(59);
                                numberPicker4.setValue(0);
                                numberPicker4.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda41
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i2) {
                                        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i2));
                                    }
                                });
                                calendar.setTimeInMillis(jCurrentTimeMillis);
                                numberPicker4.setValue(calendar.get(12));
                                numberPicker3.setValue(calendar.get(11));
                                numberPicker.setValue(calendar.get(5));
                                numberPicker2.setValue(calendar.get(2) + 120);
                                Text maxWidth = new Text(LocaleController.formatString(C2797R.string.formatDateAtTime, _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET).trim(), 16.0f).setMaxWidth(AndroidUtilities.m1036dp(100.0f));
                                Layout.Alignment alignment = Layout.Alignment.ALIGN_CENTER;
                                maxWidth.align(alignment).multiline(1).setColor(scheduleDatePickerColors.textColor);
                                Text color = new Text(":", 18.0f).setMaxWidth(AndroidUtilities.m1036dp(100.0f)).align(alignment).multiline(1).setColor(scheduleDatePickerColors.textColor);
                                FrameLayout frameLayout = new FrameLayout(context);
                                C374136 c374136 = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.36
                                    boolean ignoreLayout = false;
                                    final /* synthetic */ NumberPicker val$dayPicker;
                                    final /* synthetic */ NumberPicker val$hourPicker;
                                    final /* synthetic */ NumberPicker val$minutePicker;
                                    final /* synthetic */ NumberPicker val$monthPicker;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C374136(Context context2, final NumberPicker numberPicker22, final NumberPicker numberPicker5, final NumberPicker numberPicker32, final NumberPicker numberPicker42) {
                                        super(context2);
                                        numberPicker = numberPicker22;
                                        numberPicker = numberPicker5;
                                        numberPicker = numberPicker32;
                                        numberPicker = numberPicker42;
                                        this.ignoreLayout = false;
                                    }

                                    @Override // android.widget.LinearLayout, android.view.View
                                    public void onMeasure(int i2, int i3) {
                                        this.ignoreLayout = true;
                                        Point point = AndroidUtilities.displaySize;
                                        int i4 = point.x > point.y ? 3 : 5;
                                        numberPicker.setItemCount(i4);
                                        numberPicker.setItemCount(i4);
                                        numberPicker.setItemCount(i4);
                                        numberPicker.setItemCount(i4);
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                        this.ignoreLayout = false;
                                        super.onMeasure(i2, i3);
                                    }

                                    @Override // android.view.View, android.view.ViewParent
                                    public void requestLayout() {
                                        if (this.ignoreLayout) {
                                            return;
                                        }
                                        super.requestLayout();
                                    }
                                };
                                c374136.setOrientation(1);
                                frameLayout.addView(c374136, LayoutHelper.createFrame(-1, -1.0f));
                                FrameLayout frameLayout2 = new FrameLayout(context2);
                                c374136.addView(frameLayout2, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
                                TextView textView = new TextView(context2);
                                textView.setText(LocaleController.getString(C2797R.string.RelativeDateAddDate));
                                textView.setTextColor(scheduleDatePickerColors.textColor);
                                textView.setTextSize(1, 20.0f);
                                textView.setTypeface(AndroidUtilities.bold());
                                frameLayout2.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
                                textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda42
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        return AlertsCreator.$r8$lambda$DB5FvyJoiD81Lu2f26qHCgNY_oE(view, motionEvent);
                                    }
                                });
                                C374237 c374237 = new LinearLayout(context2) { // from class: org.telegram.ui.Components.AlertsCreator.37
                                    final /* synthetic */ NumberPicker val$minutePicker;
                                    final /* synthetic */ Text val$sep2;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C374237(Context context2, Text color2, final NumberPicker numberPicker42) {
                                        super(context2);
                                        text = color2;
                                        numberPicker = numberPicker42;
                                    }

                                    @Override // android.view.ViewGroup, android.view.View
                                    public void dispatchDraw(Canvas canvas) {
                                        super.dispatchDraw(canvas);
                                        text.draw(canvas, numberPicker.getX() - AndroidUtilities.m1036dp(50.0f), getHeight() / 2.0f);
                                    }
                                };
                                c374237.setOrientation(0);
                                c374237.setWeightSum(1.0f);
                                c374136.addView(c374237, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
                                final ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(context2, resourcesProvider);
                                final String[] strArr = {LocaleController.getString(C2797R.string.DateFormatOptionNone), LocaleController.getString(C2797R.string.DateFormatOptionRelative), LocaleController.getString(C2797R.string.DateFormatOptionShortTime), LocaleController.getString(C2797R.string.DateFormatOptionLongTime), LocaleController.getString(C2797R.string.DateFormatOptionShortDate), LocaleController.getString(C2797R.string.DateFormatOptionLongDate), LocaleController.getString(C2797R.string.DateFormatOptionDayOfWeek)};
                                final int[] iArr = {0, 1, 2, 4, 8, 16, 32};
                                final int[] iArr2 = {5};
                                int iBlendOver = Theme.blendOver(scheduleDatePickerColors.backgroundColor, Theme.multAlpha(scheduleDatePickerColors.textColor, 0.075f));
                                int iMultAlpha = Theme.multAlpha(scheduleDatePickerColors.textColor, 0.1f);
                                final FrameLayout frameLayout3 = new FrameLayout(context2);
                                final TextView textView2 = new TextView(context2);
                                textView2.setTextSize(1, 13.0f);
                                textView2.setTextColor(scheduleDatePickerColors.textColor);
                                textView2.setPadding(AndroidUtilities.m1036dp(12.0f), 0, AndroidUtilities.m1036dp(12.0f), 0);
                                textView2.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(14.0f), iBlendOver, Theme.blendOver(iBlendOver, iMultAlpha)));
                                textView2.setGravity(17);
                                final Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda43
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AlertsCreator.$r8$lambda$PXM73B6WlaQbh6_w7RoojIrrPhc(strArr, iArr2, textView2, buttonWithCounterView, numberPicker5, numberPicker22, numberPicker32, numberPicker42, iArr);
                                    }
                                };
                                frameLayout3.addView(textView2, LayoutHelper.createFrame(-2, 28.0f, 1, 32.0f, 4.0f, 32.0f, 5.0f));
                                c374136.addView(frameLayout3, LayoutHelper.createLinear(-1, -2));
                                NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda44
                                    @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                                    public final void onValueChange(NumberPicker numberPicker5, int i2, int i3) {
                                        AlertsCreator.checkFormattedDateInput(buttonWithCounterView, numberPicker5, numberPicker22, numberPicker32, numberPicker42, iArr[iArr2[0]]);
                                    }
                                };
                                c374237.addView(numberPicker5, LayoutHelper.createLinear(0, 270, 0.2f));
                                c374237.addView(numberPicker22, LayoutHelper.createLinear(0, 270, 0.4f));
                                c374237.addView(numberPicker32, LayoutHelper.createLinear(0, 270, 0.2f));
                                c374237.addView(numberPicker42, LayoutHelper.createLinear(0, 270, 0.2f));
                                numberPicker5.setOnValueChangedListener(onValueChangeListener);
                                numberPicker22.setOnValueChangedListener(onValueChangeListener);
                                numberPicker32.setOnValueChangedListener(onValueChangeListener);
                                numberPicker42.setOnValueChangedListener(onValueChangeListener);
                                final boolean[] zArr = {true};
                                buttonWithCounterView.setPadding(AndroidUtilities.m1036dp(34.0f), 0, AndroidUtilities.m1036dp(34.0f), 0);
                                buttonWithCounterView.setRound();
                                c374136.addView(buttonWithCounterView, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
                                buttonWithCounterView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda45
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.m9972$r8$lambda$UbqSJ1SO5lm5RdqvJyDz4EfJWM(zArr, iArr, iArr2, numberPicker5, numberPicker22, numberPicker32, numberPicker42, formattedDatePickerDelegate, builder, view);
                                    }
                                });
                                builder.setCustomView(frameLayout);
                                final BottomSheet bottomSheetShow = builder.show();
                                bottomSheetShow.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda35
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        AlertsCreator.$r8$lambda$lXXFFv9_avZIVg9sX7CWBFMmibQ(runnable, zArr, dialogInterface);
                                    }
                                });
                                bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
                                bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
                                textView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda36
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.$r8$lambda$xIxOEn3N7IUIBddn65pwoi3KUYA(bottomSheetShow, resourcesProvider, frameLayout3, strArr, iArr2, runnable2, view);
                                    }
                                });
                                runnable2.run();
                                return builder;
                            }

                            public static /* synthetic */ String $r8$lambda$ye1U0V08DQNo8acr6yyP4Uxnvbc(int i) {
                                return _UrlKt.FRAGMENT_ENCODE_SET + i;
                            }

                            public static /* synthetic */ String $r8$lambda$RXjTGCyixgYVH6oYGguD5U8Uajo(Calendar calendar, int i, int i2) {
                                calendar.clear();
                                calendar.set(1, i);
                                calendar.set(2, 0);
                                calendar.add(2, i2 - 120);
                                if (calendar.get(1) == i) {
                                    return LocaleController.getInstance().getFormatterMonthOnly().format(calendar.getTimeInMillis());
                                }
                                return LocaleController.getInstance().getFormatterMonthYear().format(calendar.getTimeInMillis());
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$36 */
                            public class C374136 extends LinearLayout {
                                boolean ignoreLayout = false;
                                final /* synthetic */ NumberPicker val$dayPicker;
                                final /* synthetic */ NumberPicker val$hourPicker;
                                final /* synthetic */ NumberPicker val$minutePicker;
                                final /* synthetic */ NumberPicker val$monthPicker;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C374136(Context context2, final NumberPicker numberPicker22, final NumberPicker numberPicker5, final NumberPicker numberPicker32, final NumberPicker numberPicker42) {
                                    super(context2);
                                    numberPicker = numberPicker22;
                                    numberPicker = numberPicker5;
                                    numberPicker = numberPicker32;
                                    numberPicker = numberPicker42;
                                    this.ignoreLayout = false;
                                }

                                @Override // android.widget.LinearLayout, android.view.View
                                public void onMeasure(int i2, int i3) {
                                    this.ignoreLayout = true;
                                    Point point = AndroidUtilities.displaySize;
                                    int i4 = point.x > point.y ? 3 : 5;
                                    numberPicker.setItemCount(i4);
                                    numberPicker.setItemCount(i4);
                                    numberPicker.setItemCount(i4);
                                    numberPicker.setItemCount(i4);
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                    this.ignoreLayout = false;
                                    super.onMeasure(i2, i3);
                                }

                                @Override // android.view.View, android.view.ViewParent
                                public void requestLayout() {
                                    if (this.ignoreLayout) {
                                        return;
                                    }
                                    super.requestLayout();
                                }
                            }

                            public static /* synthetic */ boolean $r8$lambda$DB5FvyJoiD81Lu2f26qHCgNY_oE(View view, MotionEvent motionEvent) {
                                return true;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$37 */
                            public class C374237 extends LinearLayout {
                                final /* synthetic */ NumberPicker val$minutePicker;
                                final /* synthetic */ Text val$sep2;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C374237(Context context2, Text color2, final NumberPicker numberPicker42) {
                                    super(context2);
                                    text = color2;
                                    numberPicker = numberPicker42;
                                }

                                @Override // android.view.ViewGroup, android.view.View
                                public void dispatchDraw(Canvas canvas) {
                                    super.dispatchDraw(canvas);
                                    text.draw(canvas, numberPicker.getX() - AndroidUtilities.m1036dp(50.0f), getHeight() / 2.0f);
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$PXM73B6WlaQbh6_w7RoojIrrPhc(String[] strArr, int[] iArr, TextView textView, ButtonWithCounterView buttonWithCounterView, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, NumberPicker numberPicker4, int[] iArr2) {
                                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                                spannableStringBuilder.append((CharSequence) LocaleController.getString(C2797R.string.DateFormatOption));
                                spannableStringBuilder.append((CharSequence) " ");
                                int length = spannableStringBuilder.length();
                                spannableStringBuilder.append((CharSequence) strArr[iArr[0]]);
                                spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.bold()), length, spannableStringBuilder.length(), 33);
                                spannableStringBuilder.append((CharSequence) " v");
                                ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2797R.drawable.arrows_select);
                                coloredImageSpan.spaceScaleX = 0.7f;
                                coloredImageSpan.translate(AndroidUtilities.m1036dp(-1.33f), AndroidUtilities.m1036dp(0.0f));
                                coloredImageSpan.setAlpha(0.75f);
                                spannableStringBuilder.setSpan(coloredImageSpan, spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 33);
                                textView.setText(spannableStringBuilder);
                                checkFormattedDateInput(buttonWithCounterView, numberPicker, numberPicker2, numberPicker3, numberPicker4, iArr2[iArr[0]]);
                            }

                            /* JADX INFO: renamed from: $r8$lambda$UbqSJ1SO5lm5-RdqvJyDz4EfJWM */
                            public static /* synthetic */ void m9972$r8$lambda$UbqSJ1SO5lm5RdqvJyDz4EfJWM(boolean[] zArr, int[] iArr, int[] iArr2, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, NumberPicker numberPicker4, FormattedDatePickerDelegate formattedDatePickerDelegate, BottomSheet.Builder builder, View view) {
                                zArr[0] = false;
                                int i = iArr[iArr2[0]];
                                formattedDatePickerDelegate.didSelectDate((int) (checkFormattedDateInput(null, numberPicker, numberPicker2, numberPicker3, numberPicker4, i) / 1000), i);
                                builder.getDismissRunnable().run();
                            }

                            public static /* synthetic */ void $r8$lambda$lXXFFv9_avZIVg9sX7CWBFMmibQ(Runnable runnable, boolean[] zArr, DialogInterface dialogInterface) {
                                if (runnable == null || !zArr[0]) {
                                    return;
                                }
                                runnable.run();
                            }

                            public static /* synthetic */ void $r8$lambda$xIxOEn3N7IUIBddn65pwoi3KUYA(BottomSheet bottomSheet, Theme.ResourcesProvider resourcesProvider, FrameLayout frameLayout, String[] strArr, final int[] iArr, final Runnable runnable, View view) {
                                ItemOptions itemOptionsMakeOptions = ItemOptions.makeOptions(bottomSheet.container, resourcesProvider, frameLayout);
                                for (final int i = 0; i < strArr.length; i++) {
                                    itemOptionsMakeOptions.add(strArr[i], new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda81
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            AlertsCreator.$r8$lambda$06KfmagqicXQ5pyudByqsTYIURY(iArr, i, runnable);
                                        }
                                    });
                                }
                                itemOptionsMakeOptions.setGravity(1);
                                itemOptionsMakeOptions.show();
                            }

                            public static /* synthetic */ void $r8$lambda$06KfmagqicXQ5pyudByqsTYIURY(int[] iArr, int i, Runnable runnable) {
                                iArr[0] = i;
                                runnable.run();
                            }

                            private static String formatPollCloseCustomDeadline(int i) {
                                String pluralString;
                                int i2 = i / 86400;
                                int i3 = (i % 86400) / 3600;
                                int i4 = (i % 3600) / 60;
                                String pluralString2 = _UrlKt.FRAGMENT_ENCODE_SET;
                                String pluralString3 = i2 > 0 ? LocaleController.formatPluralString("Days", i2, new Object[0]) : _UrlKt.FRAGMENT_ENCODE_SET;
                                if (i3 <= 0) {
                                    pluralString = _UrlKt.FRAGMENT_ENCODE_SET;
                                } else {
                                    pluralString = LocaleController.formatPluralString("Hours", i3, new Object[0]);
                                }
                                if (i4 > 0) {
                                    pluralString2 = LocaleController.formatPluralString("Minutes", i4, new Object[0]);
                                }
                                return LocaleController.formatString(C2797R.string.PollCustomDeadlineClosesIn, LocaleController.formatString(C2797R.string.PollCustomDeadlineClosesInFmt, pluralString3, pluralString, pluralString2).trim());
                            }

                            public static void checkPollCloseCustomDeadline(TextView textView, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3) {
                                int value = numberPicker.getValue();
                                int value2 = numberPicker2.getValue();
                                int value3 = numberPicker3.getValue();
                                Calendar calendar = Calendar.getInstance();
                                long jCurrentTimeMillis = System.currentTimeMillis();
                                calendar.setTimeInMillis(jCurrentTimeMillis);
                                calendar.add(6, value);
                                calendar.set(11, value2);
                                calendar.set(12, value3);
                                calendar.set(13, 0);
                                calendar.set(14, 0);
                                long timeInMillis = calendar.getTimeInMillis();
                                if (textView != null) {
                                    textView.setText(formatPollCloseCustomDeadline((int) ((timeInMillis - jCurrentTimeMillis) / 1000)));
                                }
                            }

                            public static BottomSheet.Builder createPollCloseDatePickerDialog(Context context, long j, final ScheduleDatePickerDelegate scheduleDatePickerDelegate, final Runnable runnable, ScheduleDatePickerColors scheduleDatePickerColors, Theme.ResourcesProvider resourcesProvider) {
                                TextView textView;
                                if (context == null) {
                                    return null;
                                }
                                final int i = (int) MessagesController.getInstance(UserConfig.selectedAccount).config.pollClosePeriodMax.get(TimeUnit.SECONDS);
                                final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
                                builder.setApplyBottomPadding(false);
                                final NumberPicker numberPicker = new NumberPicker(context, resourcesProvider);
                                numberPicker.setTextColor(scheduleDatePickerColors.textColor);
                                numberPicker.setTextOffset(AndroidUtilities.m1036dp(10.0f));
                                numberPicker.setItemCount(5);
                                final C374338 c374338 = new NumberPicker(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.38
                                    public C374338(Context context2, Theme.ResourcesProvider resourcesProvider2) {
                                        super(context2, resourcesProvider2);
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i2) {
                                        return LocaleController.formatPluralString("Hours", i2, new Object[0]);
                                    }
                                };
                                c374338.setWrapSelectorWheel(true);
                                c374338.setAllItemsCount(24);
                                c374338.setItemCount(5);
                                c374338.setTextColor(scheduleDatePickerColors.textColor);
                                c374338.setTextOffset(-AndroidUtilities.m1036dp(10.0f));
                                final C374439 c374439 = new NumberPicker(context2, resourcesProvider2) { // from class: org.telegram.ui.Components.AlertsCreator.39
                                    public C374439(Context context2, Theme.ResourcesProvider resourcesProvider2) {
                                        super(context2, resourcesProvider2);
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i2) {
                                        return LocaleController.formatPluralString("Minutes", i2, new Object[0]);
                                    }
                                };
                                c374439.setWrapSelectorWheel(true);
                                c374439.setAllItemsCount(60);
                                c374439.setItemCount(5);
                                c374439.setTextColor(scheduleDatePickerColors.textColor);
                                c374439.setTextOffset(-AndroidUtilities.m1036dp(34.0f));
                                FrameLayout frameLayout = new FrameLayout(context2);
                                C374640 c374640 = new LinearLayout(context2) { // from class: org.telegram.ui.Components.AlertsCreator.40
                                    boolean ignoreLayout = false;
                                    final /* synthetic */ NumberPicker val$dayPicker;
                                    final /* synthetic */ NumberPicker val$hourPicker;
                                    final /* synthetic */ NumberPicker val$minutePicker;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C374640(Context context2, final NumberPicker numberPicker2, final NumberPicker c3743382, final NumberPicker c3744392) {
                                        super(context2);
                                        numberPicker = numberPicker2;
                                        numberPicker = c3743382;
                                        numberPicker = c3744392;
                                        this.ignoreLayout = false;
                                    }

                                    @Override // android.widget.LinearLayout, android.view.View
                                    public void onMeasure(int i2, int i3) {
                                        this.ignoreLayout = true;
                                        Point point = AndroidUtilities.displaySize;
                                        int i4 = point.x > point.y ? 3 : 5;
                                        numberPicker.setItemCount(i4);
                                        numberPicker.setItemCount(i4);
                                        numberPicker.setItemCount(i4);
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                        this.ignoreLayout = false;
                                        super.onMeasure(i2, i3);
                                    }

                                    @Override // android.view.View, android.view.ViewParent
                                    public void requestLayout() {
                                        if (this.ignoreLayout) {
                                            return;
                                        }
                                        super.requestLayout();
                                    }
                                };
                                c374640.setOrientation(1);
                                frameLayout.addView(c374640, LayoutHelper.createFrame(-1, -1.0f));
                                frameLayout.addView(new FrameLayout(context2), LayoutHelper.createFrame(-1, 100.0f, 87, 0.0f, 0.0f, 0.0f, 120.0f));
                                FrameLayout frameLayout2 = new FrameLayout(context2);
                                c374640.addView(frameLayout2, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
                                TextView textView2 = new TextView(context2);
                                textView2.setText(LocaleController.getString(C2797R.string.StopPollDeadlineHeader));
                                textView2.setTextColor(scheduleDatePickerColors.textColor);
                                textView2.setTextSize(1, 20.0f);
                                textView2.setTypeface(AndroidUtilities.bold());
                                frameLayout2.addView(textView2, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
                                textView2.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda228
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        return AlertsCreator.$r8$lambda$KFj6ePGSBYs9BXY107lqp1cI6XA(view, motionEvent);
                                    }
                                });
                                LinearLayout linearLayout = new LinearLayout(context2);
                                linearLayout.setOrientation(0);
                                linearLayout.setWeightSum(1.0f);
                                c374640.addView(linearLayout, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 23, 0, 23));
                                final TextView textView3 = new TextView(context2);
                                textView3.setTextSize(1, 12.0f);
                                textView3.setTextColor(Theme.getColor(Theme.key_dialogTextGray2, resourcesProvider2));
                                textView3.setGravity(17);
                                final Calendar calendar = Calendar.getInstance();
                                C374741 c374741 = new TextView(context2) { // from class: org.telegram.ui.Components.AlertsCreator.41
                                    public C374741(Context context2) {
                                        super(context2);
                                    }

                                    @Override // android.widget.TextView, android.view.View
                                    public CharSequence getAccessibilityClassName() {
                                        return Button.class.getName();
                                    }
                                };
                                c374741.setText(LocaleController.getString(C2797R.string.StopPollDeadlineButton));
                                ScaleStateListAnimator.apply(c374741, 0.02f, 1.2f);
                                linearLayout.addView(numberPicker2, LayoutHelper.createLinear(0, 270, 0.5f));
                                numberPicker2.setMinValue(0);
                                numberPicker2.setMaxValue(365);
                                numberPicker2.setWrapSelectorWheel(false);
                                numberPicker2.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda229
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i2) {
                                        return AlertsCreator.$r8$lambda$yn9uKrf0uEqu3Bwr3keaEYOaF5Q(i2);
                                    }
                                });
                                NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda230
                                    @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                                    public final void onValueChange(NumberPicker numberPicker2, int i2, int i3) {
                                        AlertsCreator.$r8$lambda$fGl6bjMDZVj2YKRYQ7QsL8YIr10(i, numberPicker2, c3743382, c3744392, textView3, numberPicker2, i2, i3);
                                    }
                                };
                                numberPicker2.setOnValueChangedListener(onValueChangeListener);
                                c3743382.setMinValue(0);
                                c3743382.setMaxValue(23);
                                linearLayout.addView(c3743382, LayoutHelper.createLinear(0, 270, 0.2f));
                                c3743382.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda231
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i2) {
                                        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i2));
                                    }
                                });
                                c3743382.setOnValueChangedListener(onValueChangeListener);
                                c3744392.setMinValue(0);
                                c3744392.setMaxValue(59);
                                c3744392.setValue(0);
                                c3744392.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda232
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i2) {
                                        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i2));
                                    }
                                });
                                linearLayout.addView(c3744392, LayoutHelper.createLinear(0, 270, 0.3f));
                                c3744392.setOnValueChangedListener(onValueChangeListener);
                                if (j <= 0 || j == 2147483646) {
                                    textView = textView3;
                                } else {
                                    long j2 = j * 1000;
                                    calendar.setTimeInMillis(System.currentTimeMillis());
                                    calendar.set(12, 0);
                                    calendar.set(13, 0);
                                    calendar.set(14, 0);
                                    calendar.set(11, 0);
                                    textView = textView3;
                                    int timeInMillis = (int) ((j2 - calendar.getTimeInMillis()) / DurationKt.MILLIS_IN_DAY);
                                    calendar.setTimeInMillis(j2);
                                    if (timeInMillis >= 0) {
                                        c3744392.setValue(calendar.get(12));
                                        c3743382.setValue(calendar.get(11));
                                        numberPicker2.setValue(timeInMillis);
                                    }
                                }
                                final boolean[] zArr = {true};
                                checkScheduleDate(null, null, i, 3, numberPicker2, c3743382, c3744392);
                                final TextView textView4 = textView;
                                checkPollCloseCustomDeadline(textView4, numberPicker2, c3743382, c3744392);
                                c374741.setPadding(AndroidUtilities.m1036dp(34.0f), 0, AndroidUtilities.m1036dp(34.0f), 0);
                                c374741.setGravity(17);
                                c374741.setTextColor(scheduleDatePickerColors.buttonTextColor);
                                c374741.setTextSize(1, 14.0f);
                                c374741.setTypeface(AndroidUtilities.bold());
                                c374741.setBackground(Theme.AdaptiveRipple.filledRect(scheduleDatePickerColors.buttonBackgroundColor, 24.0f));
                                c374640.addView(c374741, LayoutHelper.createLinear(-1, 48, 83, 14, 15, 14, 16));
                                c374741.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda233
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.$r8$lambda$1n97Wdd9xIBZtyq3onrkQjC9IkU(zArr, i, numberPicker2, c3743382, c3744392, textView4, calendar, scheduleDatePickerDelegate, builder, view);
                                    }
                                });
                                c374640.addView(textView4, LayoutHelper.createLinear(-1, -2, 83, 14, 0, 14, 16));
                                builder.setCustomView(frameLayout);
                                BottomSheet bottomSheetShow = builder.show();
                                bottomSheetShow.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda234
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        AlertsCreator.$r8$lambda$A8_YDEgC6AayrdSGGX3_7qfyj5c(runnable, zArr, dialogInterface);
                                    }
                                });
                                bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
                                bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
                                return builder;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$38 */
                            public class C374338 extends NumberPicker {
                                public C374338(Context context2, Theme.ResourcesProvider resourcesProvider2) {
                                    super(context2, resourcesProvider2);
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i2) {
                                    return LocaleController.formatPluralString("Hours", i2, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$39 */
                            public class C374439 extends NumberPicker {
                                public C374439(Context context2, Theme.ResourcesProvider resourcesProvider2) {
                                    super(context2, resourcesProvider2);
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i2) {
                                    return LocaleController.formatPluralString("Minutes", i2, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$40 */
                            public class C374640 extends LinearLayout {
                                boolean ignoreLayout = false;
                                final /* synthetic */ NumberPicker val$dayPicker;
                                final /* synthetic */ NumberPicker val$hourPicker;
                                final /* synthetic */ NumberPicker val$minutePicker;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C374640(Context context2, final NumberPicker numberPicker2, final NumberPicker c3743382, final NumberPicker c3744392) {
                                    super(context2);
                                    numberPicker = numberPicker2;
                                    numberPicker = c3743382;
                                    numberPicker = c3744392;
                                    this.ignoreLayout = false;
                                }

                                @Override // android.widget.LinearLayout, android.view.View
                                public void onMeasure(int i2, int i3) {
                                    this.ignoreLayout = true;
                                    Point point = AndroidUtilities.displaySize;
                                    int i4 = point.x > point.y ? 3 : 5;
                                    numberPicker.setItemCount(i4);
                                    numberPicker.setItemCount(i4);
                                    numberPicker.setItemCount(i4);
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                    this.ignoreLayout = false;
                                    super.onMeasure(i2, i3);
                                }

                                @Override // android.view.View, android.view.ViewParent
                                public void requestLayout() {
                                    if (this.ignoreLayout) {
                                        return;
                                    }
                                    super.requestLayout();
                                }
                            }

                            public static /* synthetic */ boolean $r8$lambda$KFj6ePGSBYs9BXY107lqp1cI6XA(View view, MotionEvent motionEvent) {
                                return true;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$41 */
                            public class C374741 extends TextView {
                                public C374741(Context context2) {
                                    super(context2);
                                }

                                @Override // android.widget.TextView, android.view.View
                                public CharSequence getAccessibilityClassName() {
                                    return Button.class.getName();
                                }
                            }

                            public static /* synthetic */ String $r8$lambda$yn9uKrf0uEqu3Bwr3keaEYOaF5Q(int i) {
                                if (i == 0) {
                                    return LocaleController.getString(C2797R.string.MessageScheduleToday);
                                }
                                Calendar calendar = Calendar.getInstance();
                                int i2 = calendar.get(1);
                                calendar.add(6, i);
                                long timeInMillis = calendar.getTimeInMillis();
                                if (calendar.get(1) == i2) {
                                    return LocaleController.getInstance().getFormatterWeek().format(timeInMillis) + ", " + LocaleController.getInstance().getFormatterScheduleDay().format(timeInMillis);
                                }
                                return LocaleController.getInstance().getFormatterScheduleYear().format(timeInMillis);
                            }

                            public static /* synthetic */ void $r8$lambda$fGl6bjMDZVj2YKRYQ7QsL8YIr10(int i, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, TextView textView, NumberPicker numberPicker4, int i2, int i3) {
                                checkScheduleDate(null, null, i, 3, numberPicker, numberPicker2, numberPicker3);
                                checkPollCloseCustomDeadline(textView, numberPicker, numberPicker2, numberPicker3);
                            }

                            public static /* synthetic */ void $r8$lambda$1n97Wdd9xIBZtyq3onrkQjC9IkU(boolean[] zArr, int i, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, TextView textView, Calendar calendar, ScheduleDatePickerDelegate scheduleDatePickerDelegate, BottomSheet.Builder builder, View view) {
                                zArr[0] = false;
                                boolean zCheckScheduleDate = checkScheduleDate(null, null, i, 3, numberPicker, numberPicker2, numberPicker3);
                                checkPollCloseCustomDeadline(textView, numberPicker, numberPicker2, numberPicker3);
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.add(6, numberPicker.getValue());
                                calendar.set(11, numberPicker2.getValue());
                                calendar.set(12, numberPicker3.getValue());
                                if (zCheckScheduleDate) {
                                    calendar.set(13, 0);
                                    calendar.set(14, 0);
                                }
                                scheduleDatePickerDelegate.didSelectDate(true, (int) (calendar.getTimeInMillis() / 1000), 0);
                                builder.getDismissRunnable().run();
                            }

                            public static /* synthetic */ void $r8$lambda$A8_YDEgC6AayrdSGGX3_7qfyj5c(Runnable runnable, boolean[] zArr, DialogInterface dialogInterface) {
                                if (runnable == null || !zArr[0]) {
                                    return;
                                }
                                runnable.run();
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX WARN: Type inference failed for: r11v0, types: [android.view.ViewGroup, android.widget.LinearLayout, org.telegram.ui.Components.AlertsCreator$44] */
                            /* JADX WARN: Type inference failed for: r17v3 */
                            /* JADX WARN: Type inference failed for: r17v4 */
                            /* JADX WARN: Type inference failed for: r17v5 */
                            /* JADX WARN: Type inference failed for: r17v6 */
                            /* JADX WARN: Type inference failed for: r17v7 */
                            /* JADX WARN: Type inference failed for: r2v2, types: [android.view.View, android.view.ViewGroup] */
                            /* JADX WARN: Type inference failed for: r9v0, types: [org.telegram.ui.ActionBar.BottomSheet$Builder] */
                            public static BottomSheet.Builder createStatusUntilDatePickerDialog(Context context, long j, final StatusUntilDatePickerDelegate statusUntilDatePickerDelegate) {
                                ScheduleDatePickerColors scheduleDatePickerColors;
                                float f;
                                ?? r17;
                                if (context == null) {
                                    return null;
                                }
                                ScheduleDatePickerColors scheduleDatePickerColors2 = new ScheduleDatePickerColors();
                                final ?? builder = new BottomSheet.Builder(context, false);
                                builder.setApplyBottomPadding(false);
                                final NumberPicker numberPicker = new NumberPicker(context);
                                numberPicker.setTextColor(scheduleDatePickerColors2.textColor);
                                numberPicker.setTextOffset(AndroidUtilities.m1036dp(10.0f));
                                numberPicker.setItemCount(5);
                                final C374842 c374842 = new NumberPicker(context) { // from class: org.telegram.ui.Components.AlertsCreator.42
                                    public C374842(Context context2) {
                                        super(context2);
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i) {
                                        return LocaleController.formatPluralString("Hours", i, new Object[0]);
                                    }
                                };
                                c374842.setItemCount(5);
                                c374842.setTextColor(scheduleDatePickerColors2.textColor);
                                c374842.setTextOffset(-AndroidUtilities.m1036dp(10.0f));
                                final C374943 c374943 = new NumberPicker(context2) { // from class: org.telegram.ui.Components.AlertsCreator.43
                                    public C374943(Context context2) {
                                        super(context2);
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i) {
                                        return LocaleController.formatPluralString("Minutes", i, new Object[0]);
                                    }
                                };
                                c374943.setItemCount(5);
                                c374943.setTextColor(scheduleDatePickerColors2.textColor);
                                c374943.setTextOffset(-AndroidUtilities.m1036dp(34.0f));
                                ?? c375044 = new LinearLayout(context2) { // from class: org.telegram.ui.Components.AlertsCreator.44
                                    boolean ignoreLayout = false;
                                    final /* synthetic */ NumberPicker val$dayPicker;
                                    final /* synthetic */ NumberPicker val$hourPicker;
                                    final /* synthetic */ NumberPicker val$minutePicker;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C375044(Context context2, final NumberPicker numberPicker2, final NumberPicker c3748422, final NumberPicker c3749432) {
                                        super(context2);
                                        numberPicker = numberPicker2;
                                        numberPicker = c3748422;
                                        numberPicker = c3749432;
                                        this.ignoreLayout = false;
                                    }

                                    @Override // android.widget.LinearLayout, android.view.View
                                    public void onMeasure(int i, int i2) {
                                        this.ignoreLayout = true;
                                        Point point = AndroidUtilities.displaySize;
                                        int i3 = point.x > point.y ? 3 : 5;
                                        numberPicker.setItemCount(i3);
                                        numberPicker.setItemCount(i3);
                                        numberPicker.setItemCount(i3);
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                        this.ignoreLayout = false;
                                        super.onMeasure(i, i2);
                                    }

                                    @Override // android.view.View, android.view.ViewParent
                                    public void requestLayout() {
                                        if (this.ignoreLayout) {
                                            return;
                                        }
                                        super.requestLayout();
                                    }
                                };
                                c375044.setOrientation(1);
                                FrameLayout frameLayout = new FrameLayout(context2);
                                c375044.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
                                TextView textView = new TextView(context2);
                                textView.setText(LocaleController.getString(C2797R.string.SetEmojiStatusUntilTitle));
                                textView.setTextColor(scheduleDatePickerColors2.textColor);
                                textView.setTextSize(1, 20.0f);
                                textView.setTypeface(AndroidUtilities.bold());
                                frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
                                textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda235
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        return AlertsCreator.$r8$lambda$BdqljxIPbNFukOTFiEpSJFhrWZE(view, motionEvent);
                                    }
                                });
                                LinearLayout linearLayout = new LinearLayout(context2);
                                linearLayout.setOrientation(0);
                                linearLayout.setWeightSum(1.0f);
                                c375044.addView(linearLayout, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
                                final Calendar calendar = Calendar.getInstance();
                                C375145 c375145 = new TextView(context2) { // from class: org.telegram.ui.Components.AlertsCreator.45
                                    public C375145(Context context2) {
                                        super(context2);
                                    }

                                    @Override // android.widget.TextView, android.view.View
                                    public CharSequence getAccessibilityClassName() {
                                        return Button.class.getName();
                                    }
                                };
                                linearLayout.addView(numberPicker2, LayoutHelper.createLinear(0, 270, 0.5f));
                                numberPicker2.setMinValue(0);
                                numberPicker2.setMaxValue(365);
                                numberPicker2.setWrapSelectorWheel(false);
                                numberPicker2.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda236
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i) {
                                        return AlertsCreator.m9953$r8$lambda$Gwj1ulZEdEDMOsoZtULomxS17w(i);
                                    }
                                });
                                NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda237
                                    @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                                    public final void onValueChange(NumberPicker numberPicker2, int i, int i2) {
                                        AlertsCreator.checkScheduleDate(null, null, 0, numberPicker2, c3748422, c3749432);
                                    }
                                };
                                numberPicker2.setOnValueChangedListener(onValueChangeListener);
                                c3748422.setMinValue(0);
                                c3748422.setMaxValue(23);
                                linearLayout.addView(c3748422, LayoutHelper.createLinear(0, 270, 0.2f));
                                c3748422.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda238
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i) {
                                        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i));
                                    }
                                });
                                c3748422.setOnValueChangedListener(onValueChangeListener);
                                c3749432.setMinValue(0);
                                c3749432.setMaxValue(59);
                                c3749432.setValue(0);
                                c3749432.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda239
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i) {
                                        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i));
                                    }
                                });
                                linearLayout.addView(c3749432, LayoutHelper.createLinear(0, 270, 0.3f));
                                c3749432.setOnValueChangedListener(onValueChangeListener);
                                if (j <= 0 || j == 2147483646) {
                                    scheduleDatePickerColors = scheduleDatePickerColors2;
                                    f = 34.0f;
                                    r17 = c375044;
                                } else {
                                    ?? r172 = c375044;
                                    long j2 = j * 1000;
                                    f = 34.0f;
                                    calendar.setTimeInMillis(System.currentTimeMillis());
                                    calendar.set(12, 0);
                                    calendar.set(13, 0);
                                    calendar.set(14, 0);
                                    calendar.set(11, 0);
                                    scheduleDatePickerColors = scheduleDatePickerColors2;
                                    int timeInMillis = (int) ((j2 - calendar.getTimeInMillis()) / DurationKt.MILLIS_IN_DAY);
                                    calendar.setTimeInMillis(j2);
                                    r17 = r172;
                                    if (timeInMillis >= 0) {
                                        c3749432.setValue(calendar.get(12));
                                        c3748422.setValue(calendar.get(11));
                                        numberPicker2.setValue(timeInMillis);
                                        r17 = r172;
                                    }
                                }
                                checkScheduleDate(null, null, 0, numberPicker2, c3748422, c3749432);
                                c375145.setPadding(AndroidUtilities.m1036dp(f), 0, AndroidUtilities.m1036dp(f), 0);
                                c375145.setGravity(17);
                                ScheduleDatePickerColors scheduleDatePickerColors3 = scheduleDatePickerColors;
                                c375145.setTextColor(scheduleDatePickerColors3.buttonTextColor);
                                c375145.setTextSize(1, 14.0f);
                                c375145.setTypeface(AndroidUtilities.bold());
                                c375145.setBackgroundDrawable(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(8.0f), scheduleDatePickerColors3.buttonBackgroundColor, scheduleDatePickerColors3.buttonBackgroundPressedColor));
                                c375145.setText(LocaleController.getString(C2797R.string.SetEmojiStatusUntilButton));
                                ?? r2 = r17;
                                r2.addView(c375145, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
                                c375145.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda240
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.m9931$r8$lambda$lKYEgE9t12BI9rdprvXxuSwhXU(numberPicker2, c3748422, c3749432, calendar, statusUntilDatePickerDelegate, builder, view);
                                    }
                                });
                                builder.setCustomView(r2);
                                BottomSheet bottomSheetShow = builder.show();
                                bottomSheetShow.setBackgroundColor(scheduleDatePickerColors3.backgroundColor);
                                bottomSheetShow.fixNavigationBar(scheduleDatePickerColors3.backgroundColor);
                                return builder;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$42 */
                            public class C374842 extends NumberPicker {
                                public C374842(Context context2) {
                                    super(context2);
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i) {
                                    return LocaleController.formatPluralString("Hours", i, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$43 */
                            public class C374943 extends NumberPicker {
                                public C374943(Context context2) {
                                    super(context2);
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i) {
                                    return LocaleController.formatPluralString("Minutes", i, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$44 */
                            public class C375044 extends LinearLayout {
                                boolean ignoreLayout = false;
                                final /* synthetic */ NumberPicker val$dayPicker;
                                final /* synthetic */ NumberPicker val$hourPicker;
                                final /* synthetic */ NumberPicker val$minutePicker;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C375044(Context context2, final NumberPicker numberPicker2, final NumberPicker c3748422, final NumberPicker c3749432) {
                                    super(context2);
                                    numberPicker = numberPicker2;
                                    numberPicker = c3748422;
                                    numberPicker = c3749432;
                                    this.ignoreLayout = false;
                                }

                                @Override // android.widget.LinearLayout, android.view.View
                                public void onMeasure(int i, int i2) {
                                    this.ignoreLayout = true;
                                    Point point = AndroidUtilities.displaySize;
                                    int i3 = point.x > point.y ? 3 : 5;
                                    numberPicker.setItemCount(i3);
                                    numberPicker.setItemCount(i3);
                                    numberPicker.setItemCount(i3);
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                    this.ignoreLayout = false;
                                    super.onMeasure(i, i2);
                                }

                                @Override // android.view.View, android.view.ViewParent
                                public void requestLayout() {
                                    if (this.ignoreLayout) {
                                        return;
                                    }
                                    super.requestLayout();
                                }
                            }

                            public static /* synthetic */ boolean $r8$lambda$BdqljxIPbNFukOTFiEpSJFhrWZE(View view, MotionEvent motionEvent) {
                                return true;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$45 */
                            public class C375145 extends TextView {
                                public C375145(Context context2) {
                                    super(context2);
                                }

                                @Override // android.widget.TextView, android.view.View
                                public CharSequence getAccessibilityClassName() {
                                    return Button.class.getName();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$Gwj1ulZEdEDMOso-ZtULomxS17w */
                            public static /* synthetic */ String m9953$r8$lambda$Gwj1ulZEdEDMOsoZtULomxS17w(int i) {
                                if (i == 0) {
                                    return LocaleController.getString(C2797R.string.MessageScheduleToday);
                                }
                                Calendar calendar = Calendar.getInstance();
                                int i2 = calendar.get(1);
                                calendar.add(6, i);
                                long timeInMillis = calendar.getTimeInMillis();
                                int i3 = calendar.get(1);
                                if (i3 != i2 || i >= 7) {
                                    if (i3 == i2) {
                                        return LocaleController.getInstance().getFormatterScheduleDay().format(timeInMillis);
                                    }
                                    return LocaleController.getInstance().getFormatterScheduleYear().format(timeInMillis);
                                }
                                return LocaleController.getInstance().getFormatterWeek().format(timeInMillis) + ", " + LocaleController.getInstance().getFormatterScheduleDay().format(timeInMillis);
                            }

                            /* JADX INFO: renamed from: $r8$lambda$-lKYEgE9t12BI9rdprvXxuSwhXU */
                            public static /* synthetic */ void m9931$r8$lambda$lKYEgE9t12BI9rdprvXxuSwhXU(NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, Calendar calendar, StatusUntilDatePickerDelegate statusUntilDatePickerDelegate, BottomSheet.Builder builder, View view) {
                                boolean zCheckScheduleDate = checkScheduleDate(null, null, 0, numberPicker, numberPicker2, numberPicker3);
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.add(6, numberPicker.getValue());
                                calendar.set(11, numberPicker2.getValue());
                                calendar.set(12, numberPicker3.getValue());
                                if (zCheckScheduleDate) {
                                    calendar.set(13, 0);
                                    calendar.set(14, 0);
                                }
                                statusUntilDatePickerDelegate.didSelectDate((int) (calendar.getTimeInMillis() / 1000));
                                builder.getDismissRunnable().run();
                            }

                            public static BottomSheet.Builder createAutoDeleteDatePickerDialog(Context context, int i, Theme.ResourcesProvider resourcesProvider, final ScheduleDatePickerDelegate scheduleDatePickerDelegate) {
                                if (context == null) {
                                    return null;
                                }
                                ScheduleDatePickerColors scheduleDatePickerColors = new ScheduleDatePickerColors(resourcesProvider);
                                final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
                                builder.setApplyBottomPadding(false);
                                final int[] iArr = {0, 1440, 2880, 4320, 5760, 7200, 8640, 10080, 20160, 30240, 44640, 89280, 133920, 178560, 223200, 267840, 525600};
                                final C375246 c375246 = new NumberPicker(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.46
                                    final /* synthetic */ int[] val$values;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C375246(Context context2, Theme.ResourcesProvider resourcesProvider2, final int[] iArr2) {
                                        super(context2, resourcesProvider2);
                                        iArr = iArr2;
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i2) {
                                        int i3 = iArr[i2];
                                        if (i3 == 0) {
                                            return LocaleController.getString(C2797R.string.AutoDeleteNever);
                                        }
                                        if (i3 < 10080) {
                                            return LocaleController.formatPluralString("Days", i3 / 1440, new Object[0]);
                                        }
                                        if (i3 < 44640) {
                                            return LocaleController.formatPluralString("Weeks", i3 / 1440, new Object[0]);
                                        }
                                        if (i3 < 525600) {
                                            return LocaleController.formatPluralString("Months", i3 / 10080, new Object[0]);
                                        }
                                        return LocaleController.formatPluralString("Years", ((i3 * 5) / 31) * 1440, new Object[0]);
                                    }
                                };
                                c375246.setMinValue(0);
                                c375246.setMaxValue(16);
                                c375246.setTextColor(scheduleDatePickerColors.textColor);
                                c375246.setValue(0);
                                c375246.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda153
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i2) {
                                        return AlertsCreator.m9952$r8$lambda$GRAmIduodlYE5swIfm1nfefag(iArr2, i2);
                                    }
                                });
                                C375347 c375347 = new LinearLayout(context2) { // from class: org.telegram.ui.Components.AlertsCreator.47
                                    boolean ignoreLayout = false;
                                    final /* synthetic */ NumberPicker val$numberPicker;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C375347(Context context2, final NumberPicker c3752462) {
                                        super(context2);
                                        numberPicker = c3752462;
                                        this.ignoreLayout = false;
                                    }

                                    @Override // android.widget.LinearLayout, android.view.View
                                    public void onMeasure(int i2, int i3) {
                                        this.ignoreLayout = true;
                                        Point point = AndroidUtilities.displaySize;
                                        int i4 = point.x > point.y ? 3 : 5;
                                        numberPicker.setItemCount(i4);
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                        this.ignoreLayout = false;
                                        super.onMeasure(i2, i3);
                                    }

                                    @Override // android.view.View, android.view.ViewParent
                                    public void requestLayout() {
                                        if (this.ignoreLayout) {
                                            return;
                                        }
                                        super.requestLayout();
                                    }
                                };
                                c375347.setOrientation(1);
                                FrameLayout frameLayout = new FrameLayout(context2);
                                c375347.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
                                TextView textView = new TextView(context2);
                                textView.setText(LocaleController.getString(C2797R.string.AutoDeleteAfteTitle));
                                textView.setTextColor(scheduleDatePickerColors.textColor);
                                textView.setTextSize(1, 20.0f);
                                textView.setTypeface(AndroidUtilities.bold());
                                frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
                                textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda154
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        return AlertsCreator.$r8$lambda$UVGy1jpMpgFXrBhyYb2x6Blqxd8(view, motionEvent);
                                    }
                                });
                                LinearLayout linearLayout = new LinearLayout(context2);
                                linearLayout.setOrientation(0);
                                linearLayout.setWeightSum(1.0f);
                                c375347.addView(linearLayout, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
                                final C375448 c375448 = new AnimatedTextView(context2, true, true, false) { // from class: org.telegram.ui.Components.AlertsCreator.48
                                    public C375448(Context context2, boolean z, boolean z2, boolean z3) {
                                        super(context2, z, z2, z3);
                                    }

                                    @Override // android.view.View
                                    public CharSequence getAccessibilityClassName() {
                                        return Button.class.getName();
                                    }
                                };
                                linearLayout.addView(c3752462, LayoutHelper.createLinear(0, 270, 1.0f));
                                c375448.setPadding(0, 0, 0, 0);
                                c375448.setGravity(17);
                                c375448.setTextColor(scheduleDatePickerColors.buttonTextColor);
                                c375448.setTextSize(AndroidUtilities.m1036dp(14.0f));
                                c375448.setTypeface(AndroidUtilities.bold());
                                c375448.setBackgroundDrawable(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(8.0f), scheduleDatePickerColors.buttonBackgroundColor, scheduleDatePickerColors.buttonBackgroundPressedColor));
                                c375347.addView(c375448, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
                                c375448.setText(LocaleController.getString(C2797R.string.DisableAutoDeleteTimer));
                                c3752462.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda155
                                    @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                                    public final void onValueChange(NumberPicker numberPicker, int i2, int i3) {
                                        AlertsCreator.$r8$lambda$bHqdUJSNA7J8e1QCreRSLLjLOLs(c375448, numberPicker, i2, i3);
                                    }
                                });
                                c375448.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda156
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.m9975$r8$lambda$Wkvv_CGOGPexzQ317XAxjll034(iArr2, c3752462, scheduleDatePickerDelegate, builder, view);
                                    }
                                });
                                builder.setCustomView(c375347);
                                BottomSheet bottomSheetShow = builder.show();
                                bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
                                bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
                                return builder;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$46 */
                            public class C375246 extends NumberPicker {
                                final /* synthetic */ int[] val$values;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C375246(Context context2, Theme.ResourcesProvider resourcesProvider2, final int[] iArr2) {
                                    super(context2, resourcesProvider2);
                                    iArr = iArr2;
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i2) {
                                    int i3 = iArr[i2];
                                    if (i3 == 0) {
                                        return LocaleController.getString(C2797R.string.AutoDeleteNever);
                                    }
                                    if (i3 < 10080) {
                                        return LocaleController.formatPluralString("Days", i3 / 1440, new Object[0]);
                                    }
                                    if (i3 < 44640) {
                                        return LocaleController.formatPluralString("Weeks", i3 / 1440, new Object[0]);
                                    }
                                    if (i3 < 525600) {
                                        return LocaleController.formatPluralString("Months", i3 / 10080, new Object[0]);
                                    }
                                    return LocaleController.formatPluralString("Years", ((i3 * 5) / 31) * 1440, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$GRAmIduodlYE5-swIfm1-nfefag */
                            public static /* synthetic */ String m9952$r8$lambda$GRAmIduodlYE5swIfm1nfefag(int[] iArr, int i) {
                                int i2 = iArr[i];
                                if (i2 == 0) {
                                    return LocaleController.getString(C2797R.string.AutoDeleteNever);
                                }
                                if (i2 < 10080) {
                                    return LocaleController.formatPluralString("Days", i2 / 1440, new Object[0]);
                                }
                                if (i2 < 44640) {
                                    return LocaleController.formatPluralString("Weeks", i2 / 10080, new Object[0]);
                                }
                                if (i2 < 525600) {
                                    return LocaleController.formatPluralString("Months", i2 / 44640, new Object[0]);
                                }
                                return LocaleController.formatPluralString("Years", i2 / 525600, new Object[0]);
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$47 */
                            public class C375347 extends LinearLayout {
                                boolean ignoreLayout = false;
                                final /* synthetic */ NumberPicker val$numberPicker;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C375347(Context context2, final NumberPicker c3752462) {
                                    super(context2);
                                    numberPicker = c3752462;
                                    this.ignoreLayout = false;
                                }

                                @Override // android.widget.LinearLayout, android.view.View
                                public void onMeasure(int i2, int i3) {
                                    this.ignoreLayout = true;
                                    Point point = AndroidUtilities.displaySize;
                                    int i4 = point.x > point.y ? 3 : 5;
                                    numberPicker.setItemCount(i4);
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                    this.ignoreLayout = false;
                                    super.onMeasure(i2, i3);
                                }

                                @Override // android.view.View, android.view.ViewParent
                                public void requestLayout() {
                                    if (this.ignoreLayout) {
                                        return;
                                    }
                                    super.requestLayout();
                                }
                            }

                            public static /* synthetic */ boolean $r8$lambda$UVGy1jpMpgFXrBhyYb2x6Blqxd8(View view, MotionEvent motionEvent) {
                                return true;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$48 */
                            public class C375448 extends AnimatedTextView {
                                public C375448(Context context2, boolean z, boolean z2, boolean z3) {
                                    super(context2, z, z2, z3);
                                }

                                @Override // android.view.View
                                public CharSequence getAccessibilityClassName() {
                                    return Button.class.getName();
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$bHqdUJSNA7J8e1QCreRSLLjLOLs(AnimatedTextView animatedTextView, NumberPicker numberPicker, int i, int i2) {
                                try {
                                    if (i2 == 0) {
                                        animatedTextView.setText(LocaleController.getString(C2797R.string.DisableAutoDeleteTimer));
                                    } else {
                                        animatedTextView.setText(LocaleController.getString(C2797R.string.SetAutoDeleteTimer));
                                    }
                                } catch (Exception unused) {
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$Wkv-v_CGOGPexzQ317XAxjll034 */
                            public static /* synthetic */ void m9975$r8$lambda$Wkvv_CGOGPexzQ317XAxjll034(int[] iArr, NumberPicker numberPicker, ScheduleDatePickerDelegate scheduleDatePickerDelegate, BottomSheet.Builder builder, View view) {
                                scheduleDatePickerDelegate.didSelectDate(true, iArr[numberPicker.getValue()], 0);
                                builder.getDismissRunnable().run();
                            }

                            public static BottomSheet.Builder createSoundFrequencyPickerDialog(Context context, int i, int i2, final SoundFrequencyDelegate soundFrequencyDelegate, Theme.ResourcesProvider resourcesProvider) {
                                if (context == null) {
                                    return null;
                                }
                                ScheduleDatePickerColors scheduleDatePickerColors = new ScheduleDatePickerColors(resourcesProvider);
                                final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
                                builder.setApplyBottomPadding(false);
                                final C375549 c375549 = new NumberPicker(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.49
                                    public C375549(Context context2, Theme.ResourcesProvider resourcesProvider2) {
                                        super(context2, resourcesProvider2);
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i3) {
                                        return LocaleController.formatPluralString("Times", i3 + 1, new Object[0]);
                                    }
                                };
                                c375549.setMinValue(0);
                                c375549.setMaxValue(10);
                                c375549.setTextColor(scheduleDatePickerColors.textColor);
                                c375549.setValue(i - 1);
                                c375549.setWrapSelectorWheel(false);
                                c375549.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda222
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i3) {
                                        return LocaleController.formatPluralString("Times", i3 + 1, new Object[0]);
                                    }
                                });
                                final C375750 c375750 = new NumberPicker(context2, resourcesProvider2) { // from class: org.telegram.ui.Components.AlertsCreator.50
                                    public C375750(Context context2, Theme.ResourcesProvider resourcesProvider2) {
                                        super(context2, resourcesProvider2);
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i3) {
                                        return LocaleController.formatPluralString("Times", i3 + 1, new Object[0]);
                                    }
                                };
                                c375750.setMinValue(0);
                                c375750.setMaxValue(10);
                                c375750.setTextColor(scheduleDatePickerColors.textColor);
                                c375750.setValue((i2 / 60) - 1);
                                c375750.setWrapSelectorWheel(false);
                                c375750.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda223
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i3) {
                                        return LocaleController.formatPluralString("Minutes", i3 + 1, new Object[0]);
                                    }
                                });
                                NumberPicker numberPicker = new NumberPicker(context2, resourcesProvider2);
                                numberPicker.setMinValue(0);
                                numberPicker.setMaxValue(0);
                                numberPicker.setTextColor(scheduleDatePickerColors.textColor);
                                numberPicker.setValue(0);
                                numberPicker.setWrapSelectorWheel(false);
                                numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda224
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i3) {
                                        return LocaleController.getString(C2797R.string.NotificationsFrequencyDivider);
                                    }
                                });
                                C375851 c375851 = new LinearLayout(context2) { // from class: org.telegram.ui.Components.AlertsCreator.51
                                    boolean ignoreLayout = false;
                                    final /* synthetic */ NumberPicker val$divider;
                                    final /* synthetic */ NumberPicker val$minutes;
                                    final /* synthetic */ NumberPicker val$times;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C375851(Context context2, final NumberPicker c3755492, final NumberPicker c3757502, NumberPicker numberPicker2) {
                                        super(context2);
                                        numberPicker = c3755492;
                                        numberPicker = c3757502;
                                        numberPicker = numberPicker2;
                                        this.ignoreLayout = false;
                                    }

                                    @Override // android.widget.LinearLayout, android.view.View
                                    public void onMeasure(int i3, int i4) {
                                        this.ignoreLayout = true;
                                        Point point = AndroidUtilities.displaySize;
                                        int i5 = point.x > point.y ? 3 : 5;
                                        numberPicker.setItemCount(i5);
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i5;
                                        numberPicker.setItemCount(i5);
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i5;
                                        numberPicker.setItemCount(i5);
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i5;
                                        this.ignoreLayout = false;
                                        super.onMeasure(i3, i4);
                                    }

                                    @Override // android.view.View, android.view.ViewParent
                                    public void requestLayout() {
                                        if (this.ignoreLayout) {
                                            return;
                                        }
                                        super.requestLayout();
                                    }
                                };
                                c375851.setOrientation(1);
                                FrameLayout frameLayout = new FrameLayout(context2);
                                c375851.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
                                TextView textView = new TextView(context2);
                                textView.setText(LocaleController.getString(C2797R.string.NotfificationsFrequencyTitle));
                                textView.setTextColor(scheduleDatePickerColors.textColor);
                                textView.setTextSize(1, 20.0f);
                                textView.setTypeface(AndroidUtilities.bold());
                                frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
                                textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda225
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        return AlertsCreator.$r8$lambda$0Gen8CF9aJQdCtEi1nlLv_i81b0(view, motionEvent);
                                    }
                                });
                                LinearLayout linearLayout = new LinearLayout(context2);
                                linearLayout.setOrientation(0);
                                linearLayout.setWeightSum(1.0f);
                                c375851.addView(linearLayout, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
                                C375952 c375952 = new TextView(context2) { // from class: org.telegram.ui.Components.AlertsCreator.52
                                    public C375952(Context context2) {
                                        super(context2);
                                    }

                                    @Override // android.widget.TextView, android.view.View
                                    public CharSequence getAccessibilityClassName() {
                                        return Button.class.getName();
                                    }
                                };
                                linearLayout.addView(c3755492, LayoutHelper.createLinear(0, 270, 0.4f));
                                linearLayout.addView(numberPicker2, LayoutHelper.createLinear(0, -2, 0.2f, 16));
                                linearLayout.addView(c3757502, LayoutHelper.createLinear(0, 270, 0.4f));
                                c375952.setPadding(AndroidUtilities.m1036dp(34.0f), 0, AndroidUtilities.m1036dp(34.0f), 0);
                                c375952.setGravity(17);
                                c375952.setTextColor(scheduleDatePickerColors.buttonTextColor);
                                c375952.setTextSize(1, 14.0f);
                                c375952.setTypeface(AndroidUtilities.bold());
                                c375952.setBackgroundDrawable(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(8.0f), scheduleDatePickerColors.buttonBackgroundColor, scheduleDatePickerColors.buttonBackgroundPressedColor));
                                c375952.setText(LocaleController.getString(C2797R.string.AutoDeleteConfirm));
                                c375851.addView(c375952, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
                                NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda226
                                    @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                                    public final void onValueChange(NumberPicker numberPicker2, int i3, int i4) {
                                        AlertsCreator.$r8$lambda$Q870NArO2_5LHxEHbS7bVr1pnug(numberPicker2, i3, i4);
                                    }
                                };
                                c3755492.setOnValueChangedListener(onValueChangeListener);
                                c3757502.setOnValueChangedListener(onValueChangeListener);
                                c375952.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda227
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.$r8$lambda$Sn6XEjKARKmmkU2_jHlKXDRgunk(c3755492, c3757502, soundFrequencyDelegate, builder, view);
                                    }
                                });
                                builder.setCustomView(c375851);
                                BottomSheet bottomSheetShow = builder.show();
                                bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
                                bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
                                return builder;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$49 */
                            public class C375549 extends NumberPicker {
                                public C375549(Context context2, Theme.ResourcesProvider resourcesProvider2) {
                                    super(context2, resourcesProvider2);
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i3) {
                                    return LocaleController.formatPluralString("Times", i3 + 1, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$50 */
                            public class C375750 extends NumberPicker {
                                public C375750(Context context2, Theme.ResourcesProvider resourcesProvider2) {
                                    super(context2, resourcesProvider2);
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i3) {
                                    return LocaleController.formatPluralString("Times", i3 + 1, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$51 */
                            public class C375851 extends LinearLayout {
                                boolean ignoreLayout = false;
                                final /* synthetic */ NumberPicker val$divider;
                                final /* synthetic */ NumberPicker val$minutes;
                                final /* synthetic */ NumberPicker val$times;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C375851(Context context2, final NumberPicker c3755492, final NumberPicker c3757502, NumberPicker numberPicker2) {
                                    super(context2);
                                    numberPicker = c3755492;
                                    numberPicker = c3757502;
                                    numberPicker = numberPicker2;
                                    this.ignoreLayout = false;
                                }

                                @Override // android.widget.LinearLayout, android.view.View
                                public void onMeasure(int i3, int i4) {
                                    this.ignoreLayout = true;
                                    Point point = AndroidUtilities.displaySize;
                                    int i5 = point.x > point.y ? 3 : 5;
                                    numberPicker.setItemCount(i5);
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i5;
                                    numberPicker.setItemCount(i5);
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i5;
                                    numberPicker.setItemCount(i5);
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i5;
                                    this.ignoreLayout = false;
                                    super.onMeasure(i3, i4);
                                }

                                @Override // android.view.View, android.view.ViewParent
                                public void requestLayout() {
                                    if (this.ignoreLayout) {
                                        return;
                                    }
                                    super.requestLayout();
                                }
                            }

                            public static /* synthetic */ boolean $r8$lambda$0Gen8CF9aJQdCtEi1nlLv_i81b0(View view, MotionEvent motionEvent) {
                                return true;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$52 */
                            public class C375952 extends TextView {
                                public C375952(Context context2) {
                                    super(context2);
                                }

                                @Override // android.widget.TextView, android.view.View
                                public CharSequence getAccessibilityClassName() {
                                    return Button.class.getName();
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$Sn6XEjKARKmmkU2_jHlKXDRgunk(NumberPicker numberPicker, NumberPicker numberPicker2, SoundFrequencyDelegate soundFrequencyDelegate, BottomSheet.Builder builder, View view) {
                                soundFrequencyDelegate.didSelectValues(numberPicker.getValue() + 1, (numberPicker2.getValue() + 1) * 60);
                                builder.getDismissRunnable().run();
                            }

                            public static BottomSheet.Builder createMuteForPickerDialog(Context context, Theme.ResourcesProvider resourcesProvider, final ScheduleDatePickerDelegate scheduleDatePickerDelegate) {
                                if (context == null) {
                                    return null;
                                }
                                ScheduleDatePickerColors scheduleDatePickerColors = new ScheduleDatePickerColors(resourcesProvider);
                                final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
                                builder.setApplyBottomPadding(false);
                                final int[] iArr = {30, 60, 120, 180, 480, 1440, 2880, 4320, 5760, 7200, 8640, 10080, 20160, 30240, 44640, 89280, 133920, 178560, 223200, 267840, 525600};
                                final C376053 c376053 = new NumberPicker(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.53
                                    final /* synthetic */ int[] val$values;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C376053(Context context2, Theme.ResourcesProvider resourcesProvider2, final int[] iArr2) {
                                        super(context2, resourcesProvider2);
                                        iArr = iArr2;
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i) {
                                        int i2 = iArr[i];
                                        if (i2 == 0) {
                                            return LocaleController.getString(C2797R.string.MuteNever);
                                        }
                                        if (i2 < 60) {
                                            return LocaleController.formatPluralString("Minutes", i2, new Object[0]);
                                        }
                                        if (i2 < 1440) {
                                            return LocaleController.formatPluralString("Hours", i2 / 60, new Object[0]);
                                        }
                                        if (i2 < 10080) {
                                            return LocaleController.formatPluralString("Days", i2 / 1440, new Object[0]);
                                        }
                                        if (i2 < 44640) {
                                            return LocaleController.formatPluralString("Weeks", i2 / 10080, new Object[0]);
                                        }
                                        if (i2 < 525600) {
                                            return LocaleController.formatPluralString("Months", i2 / 44640, new Object[0]);
                                        }
                                        return LocaleController.formatPluralString("Years", i2 / 525600, new Object[0]);
                                    }
                                };
                                c376053.setMinValue(0);
                                c376053.setMaxValue(20);
                                c376053.setTextColor(scheduleDatePickerColors.textColor);
                                c376053.setValue(0);
                                c376053.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda125
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i) {
                                        return AlertsCreator.m9988$r8$lambda$f8e4C7YILZHPx7f8LprcaS8o0(iArr2, i);
                                    }
                                });
                                C376154 c376154 = new LinearLayout(context2) { // from class: org.telegram.ui.Components.AlertsCreator.54
                                    boolean ignoreLayout = false;
                                    final /* synthetic */ NumberPicker val$numberPicker;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C376154(Context context2, final NumberPicker c3760532) {
                                        super(context2);
                                        numberPicker = c3760532;
                                        this.ignoreLayout = false;
                                    }

                                    @Override // android.widget.LinearLayout, android.view.View
                                    public void onMeasure(int i, int i2) {
                                        this.ignoreLayout = true;
                                        Point point = AndroidUtilities.displaySize;
                                        int i3 = point.x > point.y ? 3 : 5;
                                        numberPicker.setItemCount(i3);
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                        this.ignoreLayout = false;
                                        super.onMeasure(i, i2);
                                    }

                                    @Override // android.view.View, android.view.ViewParent
                                    public void requestLayout() {
                                        if (this.ignoreLayout) {
                                            return;
                                        }
                                        super.requestLayout();
                                    }
                                };
                                c376154.setOrientation(1);
                                FrameLayout frameLayout = new FrameLayout(context2);
                                c376154.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
                                TextView textView = new TextView(context2);
                                textView.setText(LocaleController.getString(C2797R.string.MuteForAlert));
                                textView.setTextColor(scheduleDatePickerColors.textColor);
                                textView.setTextSize(1, 20.0f);
                                textView.setTypeface(AndroidUtilities.bold());
                                frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
                                textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda126
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        return AlertsCreator.m9945$r8$lambda$CayGtDRplHVBpweOBtXp6SMtak(view, motionEvent);
                                    }
                                });
                                LinearLayout linearLayout = new LinearLayout(context2);
                                linearLayout.setOrientation(0);
                                linearLayout.setWeightSum(1.0f);
                                c376154.addView(linearLayout, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
                                C376255 c376255 = new TextView(context2) { // from class: org.telegram.ui.Components.AlertsCreator.55
                                    public C376255(Context context2) {
                                        super(context2);
                                    }

                                    @Override // android.widget.TextView, android.view.View
                                    public CharSequence getAccessibilityClassName() {
                                        return Button.class.getName();
                                    }
                                };
                                linearLayout.addView(c3760532, LayoutHelper.createLinear(0, 270, 1.0f));
                                c3760532.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda127
                                    @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                                    public final void onValueChange(NumberPicker numberPicker, int i, int i2) {
                                        AlertsCreator.m9979$r8$lambda$_YkZpBVxn8muhSiPb31kIo3wbk(numberPicker, i, i2);
                                    }
                                });
                                c376255.setPadding(AndroidUtilities.m1036dp(34.0f), 0, AndroidUtilities.m1036dp(34.0f), 0);
                                c376255.setGravity(17);
                                c376255.setTextColor(scheduleDatePickerColors.buttonTextColor);
                                c376255.setTextSize(1, 14.0f);
                                c376255.setTypeface(AndroidUtilities.bold());
                                c376255.setBackgroundDrawable(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(8.0f), scheduleDatePickerColors.buttonBackgroundColor, scheduleDatePickerColors.buttonBackgroundPressedColor));
                                c376255.setText(LocaleController.getString(C2797R.string.AutoDeleteConfirm));
                                c376154.addView(c376255, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
                                c376255.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda128
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.$r8$lambda$O2KDQTdXcSxelKB85ufA9EkoUhI(iArr2, c3760532, scheduleDatePickerDelegate, builder, view);
                                    }
                                });
                                builder.setCustomView(c376154);
                                BottomSheet bottomSheetShow = builder.show();
                                bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
                                bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
                                return builder;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$53 */
                            public class C376053 extends NumberPicker {
                                final /* synthetic */ int[] val$values;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C376053(Context context2, Theme.ResourcesProvider resourcesProvider2, final int[] iArr2) {
                                    super(context2, resourcesProvider2);
                                    iArr = iArr2;
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i) {
                                    int i2 = iArr[i];
                                    if (i2 == 0) {
                                        return LocaleController.getString(C2797R.string.MuteNever);
                                    }
                                    if (i2 < 60) {
                                        return LocaleController.formatPluralString("Minutes", i2, new Object[0]);
                                    }
                                    if (i2 < 1440) {
                                        return LocaleController.formatPluralString("Hours", i2 / 60, new Object[0]);
                                    }
                                    if (i2 < 10080) {
                                        return LocaleController.formatPluralString("Days", i2 / 1440, new Object[0]);
                                    }
                                    if (i2 < 44640) {
                                        return LocaleController.formatPluralString("Weeks", i2 / 10080, new Object[0]);
                                    }
                                    if (i2 < 525600) {
                                        return LocaleController.formatPluralString("Months", i2 / 44640, new Object[0]);
                                    }
                                    return LocaleController.formatPluralString("Years", i2 / 525600, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$f8e4-C7YILZHPx7f8Lp-rcaS8o0 */
                            public static /* synthetic */ String m9988$r8$lambda$f8e4C7YILZHPx7f8LprcaS8o0(int[] iArr, int i) {
                                int i2 = iArr[i];
                                if (i2 == 0) {
                                    return LocaleController.getString(C2797R.string.MuteNever);
                                }
                                if (i2 < 60) {
                                    return LocaleController.formatPluralString("Minutes", i2, new Object[0]);
                                }
                                if (i2 < 1440) {
                                    return LocaleController.formatPluralString("Hours", i2 / 60, new Object[0]);
                                }
                                if (i2 < 10080) {
                                    return LocaleController.formatPluralString("Days", i2 / 1440, new Object[0]);
                                }
                                if (i2 < 44640) {
                                    return LocaleController.formatPluralString("Weeks", i2 / 10080, new Object[0]);
                                }
                                if (i2 < 525600) {
                                    return LocaleController.formatPluralString("Months", i2 / 44640, new Object[0]);
                                }
                                return LocaleController.formatPluralString("Years", i2 / 525600, new Object[0]);
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$54 */
                            public class C376154 extends LinearLayout {
                                boolean ignoreLayout = false;
                                final /* synthetic */ NumberPicker val$numberPicker;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C376154(Context context2, final NumberPicker c3760532) {
                                    super(context2);
                                    numberPicker = c3760532;
                                    this.ignoreLayout = false;
                                }

                                @Override // android.widget.LinearLayout, android.view.View
                                public void onMeasure(int i, int i2) {
                                    this.ignoreLayout = true;
                                    Point point = AndroidUtilities.displaySize;
                                    int i3 = point.x > point.y ? 3 : 5;
                                    numberPicker.setItemCount(i3);
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                    this.ignoreLayout = false;
                                    super.onMeasure(i, i2);
                                }

                                @Override // android.view.View, android.view.ViewParent
                                public void requestLayout() {
                                    if (this.ignoreLayout) {
                                        return;
                                    }
                                    super.requestLayout();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$CayG-tDRplHVBpweOBtXp6SMtak */
                            public static /* synthetic */ boolean m9945$r8$lambda$CayGtDRplHVBpweOBtXp6SMtak(View view, MotionEvent motionEvent) {
                                return true;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$55 */
                            public class C376255 extends TextView {
                                public C376255(Context context2) {
                                    super(context2);
                                }

                                @Override // android.widget.TextView, android.view.View
                                public CharSequence getAccessibilityClassName() {
                                    return Button.class.getName();
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$O2KDQTdXcSxelKB85ufA9EkoUhI(int[] iArr, NumberPicker numberPicker, ScheduleDatePickerDelegate scheduleDatePickerDelegate, BottomSheet.Builder builder, View view) {
                                scheduleDatePickerDelegate.didSelectDate(true, iArr[numberPicker.getValue()] * 60, 0);
                                builder.getDismissRunnable().run();
                            }

                            public static void checkCalendarDate(long j, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(j);
                                int i = 1;
                                int i2 = calendar.get(1);
                                int i3 = calendar.get(2);
                                int i4 = calendar.get(5);
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                int i5 = calendar.get(1);
                                int i6 = calendar.get(2);
                                int i7 = calendar.get(5);
                                numberPicker3.setMaxValue(i5);
                                numberPicker3.setMinValue(i2);
                                int value = numberPicker3.getValue();
                                numberPicker2.setMaxValue(value == i5 ? i6 : 11);
                                numberPicker2.setMinValue(value == i2 ? i3 : 0);
                                int value2 = numberPicker2.getValue();
                                calendar.set(1, value);
                                calendar.set(2, value2);
                                int actualMaximum = calendar.getActualMaximum(5);
                                if (value == i5 && value2 == i6) {
                                    actualMaximum = Math.min(i7, actualMaximum);
                                }
                                numberPicker.setMaxValue(actualMaximum);
                                if (value == i2 && value2 == i3) {
                                    i = i4;
                                }
                                numberPicker.setMinValue(i);
                            }

                            public static BottomSheet.Builder createCalendarPickerDialog(Context context, final long j, final MessagesStorage.IntCallback intCallback, Theme.ResourcesProvider resourcesProvider) {
                                if (context == null) {
                                    return null;
                                }
                                final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
                                builder.setApplyBottomPadding(false);
                                final NumberPicker numberPicker = new NumberPicker(context, resourcesProvider);
                                numberPicker.setTextOffset(AndroidUtilities.m1036dp(10.0f));
                                numberPicker.setItemCount(5);
                                final NumberPicker numberPicker2 = new NumberPicker(context, resourcesProvider);
                                numberPicker2.setItemCount(5);
                                numberPicker2.setTextOffset(-AndroidUtilities.m1036dp(10.0f));
                                final NumberPicker numberPicker3 = new NumberPicker(context, resourcesProvider);
                                numberPicker3.setItemCount(5);
                                numberPicker3.setTextOffset(-AndroidUtilities.m1036dp(24.0f));
                                C376356 c376356 = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.56
                                    boolean ignoreLayout = false;
                                    final /* synthetic */ NumberPicker val$dayPicker;
                                    final /* synthetic */ NumberPicker val$monthPicker;
                                    final /* synthetic */ NumberPicker val$yearPicker;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C376356(Context context2, final NumberPicker numberPicker4, final NumberPicker numberPicker22, final NumberPicker numberPicker32) {
                                        super(context2);
                                        numberPicker = numberPicker4;
                                        numberPicker = numberPicker22;
                                        numberPicker = numberPicker32;
                                        this.ignoreLayout = false;
                                    }

                                    @Override // android.widget.LinearLayout, android.view.View
                                    public void onMeasure(int i, int i2) {
                                        this.ignoreLayout = true;
                                        Point point = AndroidUtilities.displaySize;
                                        int i3 = point.x > point.y ? 3 : 5;
                                        numberPicker.setItemCount(i3);
                                        numberPicker.setItemCount(i3);
                                        numberPicker.setItemCount(i3);
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                        this.ignoreLayout = false;
                                        super.onMeasure(i, i2);
                                    }

                                    @Override // android.view.View, android.view.ViewParent
                                    public void requestLayout() {
                                        if (this.ignoreLayout) {
                                            return;
                                        }
                                        super.requestLayout();
                                    }
                                };
                                c376356.setOrientation(1);
                                FrameLayout frameLayout = new FrameLayout(context2);
                                c376356.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
                                TextView textView = new TextView(context2);
                                textView.setText(LocaleController.getString(C2797R.string.ChooseDate));
                                textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
                                textView.setTextSize(1, 20.0f);
                                textView.setTypeface(AndroidUtilities.bold());
                                frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
                                textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda86
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        return AlertsCreator.$r8$lambda$xMCj70tLJ5pfzMeF8uUot16z2LE(view, motionEvent);
                                    }
                                });
                                LinearLayout linearLayout = new LinearLayout(context2);
                                linearLayout.setOrientation(0);
                                linearLayout.setWeightSum(1.0f);
                                c376356.addView(linearLayout, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
                                System.currentTimeMillis();
                                C376457 c376457 = new TextView(context2) { // from class: org.telegram.ui.Components.AlertsCreator.57
                                    public C376457(Context context2) {
                                        super(context2);
                                    }

                                    @Override // android.widget.TextView, android.view.View
                                    public CharSequence getAccessibilityClassName() {
                                        return Button.class.getName();
                                    }
                                };
                                linearLayout.addView(numberPicker4, LayoutHelper.createLinear(0, 270, 0.25f));
                                numberPicker4.setMinValue(1);
                                numberPicker4.setMaxValue(31);
                                numberPicker4.setWrapSelectorWheel(false);
                                numberPicker4.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda87
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i) {
                                        return AlertsCreator.$r8$lambda$l4ft0_MnUM5lhCOMrNjin9FmLEY(i);
                                    }
                                });
                                NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda88
                                    @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                                    public final void onValueChange(NumberPicker numberPicker4, int i, int i2) {
                                        AlertsCreator.checkCalendarDate(j, numberPicker4, numberPicker22, numberPicker32);
                                    }
                                };
                                numberPicker4.setOnValueChangedListener(onValueChangeListener);
                                numberPicker22.setMinValue(0);
                                numberPicker22.setMaxValue(11);
                                numberPicker22.setWrapSelectorWheel(false);
                                linearLayout.addView(numberPicker22, LayoutHelper.createLinear(0, 270, 0.5f));
                                numberPicker22.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda89
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i) {
                                        return AlertsCreator.$r8$lambda$B8_r2XT0D2WRwCwIdwuqXHOvZPs(i);
                                    }
                                });
                                numberPicker22.setOnValueChangedListener(onValueChangeListener);
                                final Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(j);
                                int i = calendar.get(1);
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                int i2 = calendar.get(1);
                                numberPicker32.setMinValue(i);
                                numberPicker32.setMaxValue(i2);
                                numberPicker32.setWrapSelectorWheel(false);
                                numberPicker32.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda90
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i3) {
                                        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i3));
                                    }
                                });
                                linearLayout.addView(numberPicker32, LayoutHelper.createLinear(0, 270, 0.25f));
                                numberPicker32.setOnValueChangedListener(onValueChangeListener);
                                numberPicker4.setValue(31);
                                numberPicker22.setValue(12);
                                numberPicker32.setValue(i2);
                                checkCalendarDate(j, numberPicker4, numberPicker22, numberPicker32);
                                c376457.setPadding(AndroidUtilities.m1036dp(34.0f), 0, AndroidUtilities.m1036dp(34.0f), 0);
                                c376457.setGravity(17);
                                c376457.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText, resourcesProvider));
                                c376457.setTextSize(1, 14.0f);
                                c376457.setTypeface(AndroidUtilities.bold());
                                c376457.setText(LocaleController.getString(C2797R.string.JumpToDate));
                                c376457.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(8.0f), Theme.getColor(Theme.key_featuredStickers_addButton, resourcesProvider), Theme.getColor(Theme.key_featuredStickers_addButtonPressed, resourcesProvider)));
                                c376356.addView(c376457, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
                                c376457.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda91
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.m9993$r8$lambda$jpIcUCsuieWHpLogdOoFFuaPC8(j, numberPicker4, numberPicker22, numberPicker32, calendar, intCallback, builder, view);
                                    }
                                });
                                builder.setCustomView(c376356);
                                return builder;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$56 */
                            public class C376356 extends LinearLayout {
                                boolean ignoreLayout = false;
                                final /* synthetic */ NumberPicker val$dayPicker;
                                final /* synthetic */ NumberPicker val$monthPicker;
                                final /* synthetic */ NumberPicker val$yearPicker;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C376356(Context context2, final NumberPicker numberPicker4, final NumberPicker numberPicker22, final NumberPicker numberPicker32) {
                                    super(context2);
                                    numberPicker = numberPicker4;
                                    numberPicker = numberPicker22;
                                    numberPicker = numberPicker32;
                                    this.ignoreLayout = false;
                                }

                                @Override // android.widget.LinearLayout, android.view.View
                                public void onMeasure(int i, int i2) {
                                    this.ignoreLayout = true;
                                    Point point = AndroidUtilities.displaySize;
                                    int i3 = point.x > point.y ? 3 : 5;
                                    numberPicker.setItemCount(i3);
                                    numberPicker.setItemCount(i3);
                                    numberPicker.setItemCount(i3);
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i3;
                                    this.ignoreLayout = false;
                                    super.onMeasure(i, i2);
                                }

                                @Override // android.view.View, android.view.ViewParent
                                public void requestLayout() {
                                    if (this.ignoreLayout) {
                                        return;
                                    }
                                    super.requestLayout();
                                }
                            }

                            public static /* synthetic */ boolean $r8$lambda$xMCj70tLJ5pfzMeF8uUot16z2LE(View view, MotionEvent motionEvent) {
                                return true;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$57 */
                            public class C376457 extends TextView {
                                public C376457(Context context2) {
                                    super(context2);
                                }

                                @Override // android.widget.TextView, android.view.View
                                public CharSequence getAccessibilityClassName() {
                                    return Button.class.getName();
                                }
                            }

                            public static /* synthetic */ String $r8$lambda$l4ft0_MnUM5lhCOMrNjin9FmLEY(int i) {
                                return _UrlKt.FRAGMENT_ENCODE_SET + i;
                            }

                            public static /* synthetic */ String $r8$lambda$B8_r2XT0D2WRwCwIdwuqXHOvZPs(int i) {
                                switch (i) {
                                    case 0:
                                        return LocaleController.getString(C2797R.string.January);
                                    case 1:
                                        return LocaleController.getString(C2797R.string.February);
                                    case 2:
                                        return LocaleController.getString(C2797R.string.March);
                                    case 3:
                                        return LocaleController.getString(C2797R.string.April);
                                    case 4:
                                        return LocaleController.getString(C2797R.string.May);
                                    case 5:
                                        return LocaleController.getString(C2797R.string.June);
                                    case 6:
                                        return LocaleController.getString(C2797R.string.July);
                                    case 7:
                                        return LocaleController.getString(C2797R.string.August);
                                    case 8:
                                        return LocaleController.getString(C2797R.string.September);
                                    case 9:
                                        return LocaleController.getString(C2797R.string.October);
                                    case 10:
                                        return LocaleController.getString(C2797R.string.November);
                                    default:
                                        return LocaleController.getString(C2797R.string.December);
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$jpIcUCsuieWHpLog-dOoFFuaPC8 */
                            public static /* synthetic */ void m9993$r8$lambda$jpIcUCsuieWHpLogdOoFFuaPC8(long j, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, Calendar calendar, MessagesStorage.IntCallback intCallback, BottomSheet.Builder builder, View view) {
                                checkCalendarDate(j, numberPicker, numberPicker2, numberPicker3);
                                calendar.set(1, numberPicker3.getValue());
                                calendar.set(2, numberPicker2.getValue());
                                calendar.set(5, numberPicker.getValue());
                                calendar.set(12, 0);
                                calendar.set(11, 0);
                                calendar.set(13, 0);
                                intCallback.run((int) (calendar.getTimeInMillis() / 1000));
                                builder.getDismissRunnable().run();
                            }

                            public static BottomSheet createMuteAlert(final BaseFragment baseFragment, final long j, final long j2, final Theme.ResourcesProvider resourcesProvider) {
                                if (baseFragment == null || baseFragment.getParentActivity() == null) {
                                    return null;
                                }
                                BottomSheet.Builder builder = new BottomSheet.Builder(baseFragment.getParentActivity(), false, resourcesProvider);
                                builder.setTitle(LocaleController.getString(C2797R.string.Notifications), true);
                                builder.setItems(new CharSequence[]{LocaleController.formatString("MuteFor", C2797R.string.MuteFor, LocaleController.formatPluralString("Hours", 1, new Object[0])), LocaleController.formatString("MuteFor", C2797R.string.MuteFor, LocaleController.formatPluralString("Hours", 8, new Object[0])), LocaleController.formatString("MuteFor", C2797R.string.MuteFor, LocaleController.formatPluralString("Days", 2, new Object[0])), LocaleController.getString(C2797R.string.MuteDisable)}, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda84
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i) {
                                        AlertsCreator.$r8$lambda$WqYphtwIF0Mjpk7IsWEg59ELtlo(j, j2, baseFragment, resourcesProvider, dialogInterface, i);
                                    }
                                });
                                return builder.create();
                            }

                            public static /* synthetic */ void $r8$lambda$WqYphtwIF0Mjpk7IsWEg59ELtlo(long j, long j2, BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider, DialogInterface dialogInterface, int i) {
                                int i2;
                                if (i == 0) {
                                    i2 = 0;
                                } else {
                                    int i3 = 1;
                                    if (i != 1) {
                                        i3 = 2;
                                        if (i != 2) {
                                            i3 = 3;
                                        }
                                    }
                                    i2 = i3;
                                }
                                NotificationsController.getInstance(UserConfig.selectedAccount).setDialogNotificationsSettings(j, j2, i2);
                                if (BulletinFactory.canShowBulletin(baseFragment)) {
                                    BulletinFactory.createMuteBulletin(baseFragment, i2, 0, resourcesProvider).show();
                                }
                            }

                            public static BottomSheet createMuteAlert(final BaseFragment baseFragment, final ArrayList<Long> arrayList, final int i, final Theme.ResourcesProvider resourcesProvider) {
                                if (baseFragment == null || baseFragment.getParentActivity() == null) {
                                    return null;
                                }
                                BottomSheet.Builder builder = new BottomSheet.Builder(baseFragment.getParentActivity(), false, resourcesProvider);
                                builder.setTitle(LocaleController.getString(C2797R.string.Notifications), true);
                                builder.setItems(new CharSequence[]{LocaleController.formatString("MuteFor", C2797R.string.MuteFor, LocaleController.formatPluralString("Hours", 1, new Object[0])), LocaleController.formatString("MuteFor", C2797R.string.MuteFor, LocaleController.formatPluralString("Hours", 8, new Object[0])), LocaleController.formatString("MuteFor", C2797R.string.MuteFor, LocaleController.formatPluralString("Days", 2, new Object[0])), LocaleController.getString(C2797R.string.MuteDisable)}, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda129
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i2) {
                                        AlertsCreator.$r8$lambda$lIwptDHuuw5ULpzgMO66Bfz6lMU(arrayList, i, baseFragment, resourcesProvider, dialogInterface, i2);
                                    }
                                });
                                return builder.create();
                            }

                            public static /* synthetic */ void $r8$lambda$lIwptDHuuw5ULpzgMO66Bfz6lMU(ArrayList arrayList, int i, BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider, DialogInterface dialogInterface, int i2) {
                                int i3;
                                if (i2 == 0) {
                                    i3 = 0;
                                } else {
                                    int i4 = 1;
                                    if (i2 != 1) {
                                        i4 = 2;
                                        if (i2 != 2) {
                                            i4 = 3;
                                        }
                                    }
                                    i3 = i4;
                                }
                                if (arrayList != null) {
                                    for (int i5 = 0; i5 < arrayList.size(); i5++) {
                                        NotificationsController.getInstance(UserConfig.selectedAccount).setDialogNotificationsSettings(((Long) arrayList.get(i5)).longValue(), i, i3);
                                    }
                                }
                                if (BulletinFactory.canShowBulletin(baseFragment)) {
                                    BulletinFactory.createMuteBulletin(baseFragment, i3, 0, resourcesProvider).show();
                                }
                            }

                            public static void createReportPhotoAlert(final int i, final Context context, final long j, final TLRPC.Photo photo, final Theme.ResourcesProvider resourcesProvider) {
                                if (context == null || photo == null) {
                                    return;
                                }
                                final Utilities.Callback2 callback2 = new Utilities.Callback2() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda70
                                    @Override // org.telegram.messenger.Utilities.Callback2
                                    public final void run(Object obj, Object obj2) {
                                        AlertsCreator.$r8$lambda$vECbs5N_56vaRnrAgiDBNEY0PU4(i, j, photo, context, resourcesProvider, (Integer) obj, (String) obj2);
                                    }
                                };
                                BottomSheet.Builder builder = new BottomSheet.Builder(context, true, resourcesProvider);
                                builder.setTitle(LocaleController.getString(C2797R.string.ReportProfilePhoto), true);
                                final int[] iArr = {0, 6, 1, 2, 3, 4, 5, 100};
                                builder.setItems(new CharSequence[]{LocaleController.getString(C2797R.string.ReportChatSpam), LocaleController.getString(C2797R.string.ReportChatFakeAccount), LocaleController.getString(C2797R.string.ReportChatViolence), LocaleController.getString(C2797R.string.ReportChatChild), LocaleController.getString(C2797R.string.ReportChatIllegalDrugs), LocaleController.getString(C2797R.string.ReportChatPersonalDetails), LocaleController.getString(C2797R.string.ReportChatPornography), LocaleController.getString(C2797R.string.ReportChatOther)}, new int[]{C2797R.drawable.msg_clearcache, C2797R.drawable.msg_report_fake, C2797R.drawable.msg_report_violence, C2797R.drawable.msg_block2, C2797R.drawable.msg_report_drugs, C2797R.drawable.msg_report_personal, C2797R.drawable.msg_report_xxx, C2797R.drawable.msg_report_other}, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda71
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i2) {
                                        AlertsCreator.$r8$lambda$yn1812FPcENXWJDWKrEdtioRilc(iArr, context, resourcesProvider, callback2, dialogInterface, i2);
                                    }
                                });
                                builder.show();
                            }

                            public static /* synthetic */ void $r8$lambda$vECbs5N_56vaRnrAgiDBNEY0PU4(int i, long j, TLRPC.Photo photo, Context context, Theme.ResourcesProvider resourcesProvider, Integer num, String str) {
                                TL_account.reportProfilePhoto reportprofilephoto = new TL_account.reportProfilePhoto();
                                reportprofilephoto.peer = MessagesController.getInstance(i).getInputPeer(j);
                                TLRPC.TL_inputPhoto tL_inputPhoto = new TLRPC.TL_inputPhoto();
                                tL_inputPhoto.f1269id = photo.f1276id;
                                tL_inputPhoto.file_reference = photo.file_reference;
                                tL_inputPhoto.access_hash = photo.access_hash;
                                reportprofilephoto.photo_id = tL_inputPhoto;
                                reportprofilephoto.message = _UrlKt.FRAGMENT_ENCODE_SET;
                                if (num.intValue() == 0) {
                                    reportprofilephoto.reason = new TLRPC.TL_inputReportReasonSpam();
                                } else if (num.intValue() == 1) {
                                    reportprofilephoto.reason = new TLRPC.TL_inputReportReasonViolence();
                                } else if (num.intValue() == 2) {
                                    reportprofilephoto.reason = new TLRPC.TL_inputReportReasonChildAbuse();
                                } else if (num.intValue() == 5) {
                                    reportprofilephoto.reason = new TLRPC.TL_inputReportReasonPornography();
                                } else if (num.intValue() == 3) {
                                    reportprofilephoto.reason = new TLRPC.TL_inputReportReasonIllegalDrugs();
                                } else if (num.intValue() == 4) {
                                    reportprofilephoto.reason = new TLRPC.TL_inputReportReasonPersonalDetails();
                                }
                                ConnectionsManager.getInstance(i).sendRequest(reportprofilephoto, null);
                                BulletinFactory.m1142of(Bulletin.BulletinWindow.make(context), resourcesProvider).createReportSent(resourcesProvider).show();
                            }

                            public static /* synthetic */ void $r8$lambda$yn1812FPcENXWJDWKrEdtioRilc(int[] iArr, Context context, Theme.ResourcesProvider resourcesProvider, Utilities.Callback2 callback2, DialogInterface dialogInterface, int i) {
                                int i2 = iArr[i];
                                if (i2 == 100) {
                                    new ReportAlert(context, i2, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.58
                                        final /* synthetic */ Utilities.Callback2 val$report;

                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        public DialogC376558(Context context2, int i22, Theme.ResourcesProvider resourcesProvider2, Utilities.Callback2 callback22) {
                                            super(context2, i22, resourcesProvider2);
                                            callback2 = callback22;
                                        }

                                        @Override // org.telegram.p035ui.Components.ReportAlert
                                        public void onSend(int i3, String str) {
                                            callback2.run(Integer.valueOf(i3), str);
                                        }
                                    }.show();
                                } else {
                                    callback22.run(Integer.valueOf(i22), _UrlKt.FRAGMENT_ENCODE_SET);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$58 */
                            public class DialogC376558 extends ReportAlert {
                                final /* synthetic */ Utilities.Callback2 val$report;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public DialogC376558(Context context2, int i22, Theme.ResourcesProvider resourcesProvider2, Utilities.Callback2 callback22) {
                                    super(context2, i22, resourcesProvider2);
                                    callback2 = callback22;
                                }

                                @Override // org.telegram.p035ui.Components.ReportAlert
                                public void onSend(int i3, String str) {
                                    callback2.run(Integer.valueOf(i3), str);
                                }
                            }

                            private static String getFloodWaitString(String str) {
                                String pluralString;
                                int iIntValue = Utilities.parseInt((CharSequence) str).intValue();
                                if (iIntValue < 60) {
                                    pluralString = LocaleController.formatPluralString("Seconds", iIntValue, new Object[0]);
                                } else {
                                    pluralString = LocaleController.formatPluralString("Minutes", iIntValue / 60, new Object[0]);
                                }
                                return LocaleController.formatString("FloodWaitTime", C2797R.string.FloodWaitTime, pluralString);
                            }

                            public static void showFloodWaitAlert(String str, BaseFragment baseFragment) {
                                String pluralString;
                                if (str == null || !str.startsWith("FLOOD_WAIT") || baseFragment == null || baseFragment.getParentActivity() == null) {
                                    return;
                                }
                                int iIntValue = Utilities.parseInt((CharSequence) str).intValue();
                                if (iIntValue < 60) {
                                    pluralString = LocaleController.formatPluralString("Seconds", iIntValue, new Object[0]);
                                } else {
                                    pluralString = LocaleController.formatPluralString("Minutes", iIntValue / 60, new Object[0]);
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity());
                                builder.setTitle(LocaleController.getString(C2797R.string.AppName));
                                builder.setMessage(LocaleController.formatString("FloodWaitTime", C2797R.string.FloodWaitTime, pluralString));
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.f1162OK), null);
                                baseFragment.showDialog(builder.create(), true, null);
                            }

                            public static void showSendMediaAlert(int i, BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider) {
                                if (i == 0 || baseFragment == null || baseFragment.getParentActivity() == null) {
                                    return;
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity(), resourcesProvider);
                                builder.setTitle(LocaleController.getString(C2797R.string.UnableForward));
                                if (i == 1) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedStickers));
                                } else if (i == 2) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedMedia));
                                } else if (i == 3) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedPolls));
                                } else if (i == 4) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedStickersAll));
                                } else if (i == 5) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedMediaAll));
                                } else if (i == 6) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedPollsAll));
                                } else if (i == 7) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedPrivacyVoiceMessages));
                                } else if (i == 8) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedPrivacyVideoMessages));
                                } else if (i == 9) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedVideoAll));
                                } else if (i == 10) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedPhotoAll));
                                } else if (i == 11) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedVideo));
                                } else if (i == 12) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedPhoto));
                                } else if (i == 13) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedVoiceAll));
                                } else if (i == 14) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedVoice));
                                } else if (i == 15) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedRoundAll));
                                } else if (i == 16) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedRound));
                                } else if (i == 17) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedDocumentsAll));
                                } else if (i == 18) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedDocuments));
                                } else if (i == 19) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedMusicAll));
                                } else if (i == 20) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedMusic));
                                } else if (i == 21) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedTodoAll));
                                } else if (i == 22) {
                                    builder.setMessage(LocaleController.getString(C2797R.string.ErrorSendRestrictedTodo));
                                }
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.f1162OK), null);
                                baseFragment.showDialog(builder.create(), true, null);
                            }

                            public static void showAddUserAlert(TLRPC.TL_error tL_error, final BaseFragment baseFragment, boolean z, TLObject tLObject) {
                                AlertDialog.Builder builder;
                                if (tL_error == null || tL_error.code == 406 || tL_error.text == null || baseFragment == null || baseFragment.getParentActivity() == null) {
                                    return;
                                }
                                builder = new AlertDialog.Builder(baseFragment.getParentActivity());
                                builder.setTitle(LocaleController.getString(C2797R.string.AppName));
                                String str = tL_error.text;
                                str.getClass();
                                switch (str) {
                                    case "CHANNELS_ADMIN_LOCATED_TOO_MUCH":
                                        builder.setMessage(LocaleController.getString(C2797R.string.LocatedChannelsTooMuch));
                                        break;
                                    case "CHANNELS_ADMIN_PUBLIC_TOO_MUCH":
                                        builder.setMessage(LocaleController.getString(C2797R.string.PublicChannelsTooMuch));
                                        break;
                                    case "USERS_TOO_FEW":
                                        builder.setMessage(LocaleController.getString(C2797R.string.CreateGroupError));
                                        break;
                                    case "USER_BLOCKED":
                                    case "USER_BOT":
                                    case "USER_ID_INVALID":
                                        if (z) {
                                            builder.setMessage(LocaleController.getString(C2797R.string.ChannelUserCantAdd));
                                            break;
                                        } else {
                                            builder.setMessage(LocaleController.getString(C2797R.string.GroupUserCantAdd));
                                            break;
                                        }
                                        break;
                                    case "USER_RESTRICTED":
                                        builder.setMessage(LocaleController.getString(C2797R.string.UserRestricted));
                                        break;
                                    case "PEER_FLOOD":
                                        builder.setMessage(LocaleController.getString(C2797R.string.NobodyLikesSpam2));
                                        builder.setNegativeButton(LocaleController.getString(C2797R.string.MoreInfo), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda33
                                            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                            public final void onClick(AlertDialog alertDialog, int i) {
                                                BaseFragment baseFragment2 = baseFragment;
                                                MessagesController.getInstance(baseFragment2.getCurrentAccount()).openByUserName("spambot", baseFragment2, 1);
                                            }
                                        });
                                        break;
                                    case "BOTS_TOO_MUCH":
                                        if (z) {
                                            builder.setMessage(LocaleController.getString(C2797R.string.ChannelUserCantBot));
                                            break;
                                        } else {
                                            builder.setMessage(LocaleController.getString(C2797R.string.GroupUserCantBot));
                                            break;
                                        }
                                        break;
                                    case "USER_KICKED":
                                    case "CHAT_ADMIN_BAN_REQUIRED":
                                        if (tLObject instanceof TLRPC.TL_channels_inviteToChannel) {
                                            builder.setMessage(LocaleController.getString(C2797R.string.AddUserErrorBlacklisted));
                                            break;
                                        } else {
                                            builder.setMessage(LocaleController.getString(C2797R.string.AddAdminErrorBlacklisted));
                                            break;
                                        }
                                        break;
                                    case "YOU_BLOCKED_USER":
                                        builder.setMessage(LocaleController.getString(C2797R.string.YouBlockedUser));
                                        break;
                                    case "USER_ADMIN_INVALID":
                                        builder.setMessage(LocaleController.getString(C2797R.string.AddBannedErrorAdmin));
                                        break;
                                    case "USERS_TOO_MUCH":
                                        if (z) {
                                            builder.setMessage(LocaleController.getString(C2797R.string.ChannelUserAddLimit));
                                            break;
                                        } else {
                                            builder.setMessage(LocaleController.getString(C2797R.string.GroupUserAddLimit));
                                            break;
                                        }
                                        break;
                                    case "ADMINS_TOO_MUCH":
                                        if (z) {
                                            builder.setMessage(LocaleController.getString(C2797R.string.ChannelUserCantAdmin));
                                            break;
                                        } else {
                                            builder.setMessage(LocaleController.getString(C2797R.string.GroupUserCantAdmin));
                                            break;
                                        }
                                        break;
                                    case "CHANNELS_TOO_MUCH":
                                        builder.setTitle(LocaleController.getString(C2797R.string.ChannelTooMuchTitle));
                                        if (tLObject instanceof TLRPC.TL_channels_createChannel) {
                                            builder.setMessage(LocaleController.getString(C2797R.string.ChannelTooMuch));
                                            break;
                                        } else {
                                            builder.setMessage(LocaleController.getString(C2797R.string.ChannelTooMuchJoin));
                                            break;
                                        }
                                        break;
                                    case "USER_CHANNELS_TOO_MUCH":
                                        builder.setTitle(LocaleController.getString(C2797R.string.ChannelTooMuchTitle));
                                        builder.setMessage(LocaleController.getString(C2797R.string.UserChannelTooMuchJoin));
                                        break;
                                    case "USER_NOT_MUTUAL_CONTACT":
                                        if (z) {
                                            builder.setMessage(LocaleController.getString(C2797R.string.ChannelUserLeftError));
                                            break;
                                        } else {
                                            builder.setMessage(LocaleController.getString(C2797R.string.GroupUserLeftError));
                                            break;
                                        }
                                        break;
                                    case "CHAT_ADMIN_INVITE_REQUIRED":
                                        builder.setMessage(LocaleController.getString(C2797R.string.AddAdminErrorNotAMember));
                                        break;
                                    case "USER_PRIVACY_RESTRICTED":
                                        if (z) {
                                            builder.setMessage(LocaleController.getString(C2797R.string.InviteToChannelError));
                                            break;
                                        } else {
                                            builder.setMessage(LocaleController.getString(C2797R.string.InviteToGroupError));
                                            break;
                                        }
                                        break;
                                    case "USER_ALREADY_PARTICIPANT":
                                        builder.setTitle(LocaleController.getString(C2797R.string.VoipGroupVoiceChat));
                                        builder.setMessage(LocaleController.getString(C2797R.string.VoipGroupInviteAlreadyParticipant));
                                        break;
                                    default:
                                        builder.setMessage(LocaleController.getString(C2797R.string.ErrorOccurred) + "\n" + tL_error.text);
                                        break;
                                }
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.f1162OK), null);
                                builder.show();
                            }

                            public static Dialog createColorSelectDialog(Activity activity, long j, int i, int i2, Runnable runnable) {
                                return createColorSelectDialog(activity, j, i, i2, runnable, null);
                            }

                            public static Dialog createColorSelectDialog(Activity activity, final long j, final long j2, final int i, final Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
                                int i2;
                                SharedPreferences notificationsSettings = MessagesController.getNotificationsSettings(UserConfig.selectedAccount);
                                final String sharedPrefKey = NotificationsController.getSharedPrefKey(j, j2);
                                if (j != 0) {
                                    if (notificationsSettings.contains("color_" + sharedPrefKey)) {
                                        i2 = notificationsSettings.getInt("color_" + sharedPrefKey, -16776961);
                                    } else if (DialogObject.isChatDialog(j)) {
                                        i2 = notificationsSettings.getInt("GroupLed", -16776961);
                                    } else {
                                        i2 = notificationsSettings.getInt("MessagesLed", -16776961);
                                    }
                                } else if (i == 1) {
                                    i2 = notificationsSettings.getInt("MessagesLed", -16776961);
                                } else if (i == 0) {
                                    i2 = notificationsSettings.getInt("GroupLed", -16776961);
                                } else if (i == 3) {
                                    i2 = notificationsSettings.getInt("StoriesLed", -16776961);
                                } else if (i == 5 || i == 4) {
                                    i2 = notificationsSettings.getInt("ReactionsLed", -16776961);
                                } else {
                                    i2 = notificationsSettings.getInt("ChannelLed", -16776961);
                                }
                                final LinearLayout linearLayout = new LinearLayout(activity);
                                linearLayout.setOrientation(1);
                                String[] strArr = {LocaleController.getString(C2797R.string.ColorRed), LocaleController.getString(C2797R.string.ColorOrange), LocaleController.getString(C2797R.string.ColorYellow), LocaleController.getString(C2797R.string.ColorGreen), LocaleController.getString(C2797R.string.ColorCyan), LocaleController.getString(C2797R.string.ColorBlue), LocaleController.getString(C2797R.string.ColorViolet), LocaleController.getString(C2797R.string.ColorPink), LocaleController.getString(C2797R.string.ColorWhite)};
                                final int[] iArr = {i2};
                                for (int i3 = 0; i3 < 9; i3++) {
                                    RadioColorCell radioColorCell = new RadioColorCell(activity, resourcesProvider);
                                    radioColorCell.setPadding(AndroidUtilities.m1036dp(4.0f), 0, AndroidUtilities.m1036dp(4.0f), 0);
                                    radioColorCell.setTag(Integer.valueOf(i3));
                                    int i4 = TextColorCell.colors[i3];
                                    radioColorCell.setCheckColor(i4, i4);
                                    radioColorCell.setTextAndValue(strArr[i3], i2 == TextColorCell.colorsToSave[i3]);
                                    linearLayout.addView(radioColorCell);
                                    radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda145
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            AlertsCreator.$r8$lambda$jXi6WaZsA1o6e1FxopugUFNe3jc(linearLayout, iArr, view);
                                        }
                                    });
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity, resourcesProvider);
                                builder.setTitle(LocaleController.getString(C2797R.string.LedColor));
                                builder.setView(linearLayout);
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Set), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda146
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i5) {
                                        AlertsCreator.m9965$r8$lambda$PVvXPUD7u1YCOzzf5T0Dl3TuHE(j, sharedPrefKey, iArr, j2, i, runnable, alertDialog, i5);
                                    }
                                });
                                builder.setNeutralButton(LocaleController.getString(C2797R.string.LedDisabled), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda147
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i5) {
                                        AlertsCreator.$r8$lambda$ZtonuAaumlWVOuHJaVYTsqPLcE0(j, i, runnable, alertDialog, i5);
                                    }
                                });
                                if (j != 0) {
                                    builder.setNegativeButton(LocaleController.getString(C2797R.string.Default), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda148
                                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                        public final void onClick(AlertDialog alertDialog, int i5) {
                                            AlertsCreator.m9930$r8$lambda$_LeeM2o6PNGhWPc_oqzs9idvWk(sharedPrefKey, runnable, alertDialog, i5);
                                        }
                                    });
                                }
                                return builder.create();
                            }

                            public static /* synthetic */ void $r8$lambda$jXi6WaZsA1o6e1FxopugUFNe3jc(LinearLayout linearLayout, int[] iArr, View view) {
                                int childCount = linearLayout.getChildCount();
                                for (int i = 0; i < childCount; i++) {
                                    RadioColorCell radioColorCell = (RadioColorCell) linearLayout.getChildAt(i);
                                    radioColorCell.setChecked(radioColorCell == view, true);
                                }
                                iArr[0] = TextColorCell.colorsToSave[((Integer) view.getTag()).intValue()];
                            }

                            /* JADX INFO: renamed from: $r8$lambda$P-VvXPUD7u1YCOzzf5T0Dl3TuHE */
                            public static /* synthetic */ void m9965$r8$lambda$PVvXPUD7u1YCOzzf5T0Dl3TuHE(long j, String str, int[] iArr, long j2, int i, Runnable runnable, AlertDialog alertDialog, int i2) {
                                SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(UserConfig.selectedAccount).edit();
                                if (j != 0) {
                                    editorEdit.putInt("color_" + str, iArr[0]);
                                    NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannel(j, j2);
                                } else {
                                    if (i == 1) {
                                        editorEdit.putInt("MessagesLed", iArr[0]);
                                    } else if (i == 0) {
                                        editorEdit.putInt("GroupLed", iArr[0]);
                                    } else if (i == 3) {
                                        editorEdit.putInt("StoriesLed", iArr[0]);
                                    } else if (i == 5 || i == 4) {
                                        editorEdit.putInt("ReactionLed", iArr[0]);
                                    } else {
                                        editorEdit.putInt("ChannelLed", iArr[0]);
                                    }
                                    NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannelGlobal(i);
                                }
                                editorEdit.apply();
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$ZtonuAaumlWVOuHJaVYTsqPLcE0(long j, int i, Runnable runnable, AlertDialog alertDialog, int i2) {
                                SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(UserConfig.selectedAccount).edit();
                                if (j != 0) {
                                    editorEdit.putInt("color_" + j, 0);
                                } else if (i == 1) {
                                    editorEdit.putInt("MessagesLed", 0);
                                } else if (i == 0) {
                                    editorEdit.putInt("GroupLed", 0);
                                } else if (i == 3) {
                                    editorEdit.putInt("StoriesLed", 0);
                                } else if (i == 5 || i == 4) {
                                    editorEdit.putInt("ReactionsLed", 0);
                                } else {
                                    editorEdit.putInt("ChannelLed", 0);
                                }
                                editorEdit.apply();
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$-_LeeM2o6PNGhWPc_oqzs9idvWk */
                            public static /* synthetic */ void m9930$r8$lambda$_LeeM2o6PNGhWPc_oqzs9idvWk(String str, Runnable runnable, AlertDialog alertDialog, int i) {
                                SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(UserConfig.selectedAccount).edit();
                                editorEdit.remove("color_" + str);
                                editorEdit.apply();
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            public static Dialog createVibrationSelectDialog(Activity activity, long j, long j2, boolean z, boolean z2, Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
                                String str;
                                if (j != 0) {
                                    str = "vibrate_" + j;
                                } else {
                                    str = z ? "vibrate_group" : "vibrate_messages";
                                }
                                return createVibrationSelectDialog(activity, j, j2, str, runnable, resourcesProvider);
                            }

                            public static Dialog createVibrationSelectDialog(Activity activity, long j, long j2, String str, Runnable runnable) {
                                return createVibrationSelectDialog(activity, j, j2, str, runnable, null);
                            }

                            public static Dialog createVibrationSelectDialog(Activity activity, final long j, final long j2, String str, final Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
                                String[] strArr;
                                final String str2 = str;
                                SharedPreferences notificationsSettings = MessagesController.getNotificationsSettings(UserConfig.selectedAccount);
                                boolean z = true;
                                final int[] iArr = new int[1];
                                if (j != 0) {
                                    int i = notificationsSettings.getInt(str2, 0);
                                    iArr[0] = i;
                                    if (i == 3) {
                                        iArr[0] = 2;
                                    } else if (i == 2) {
                                        iArr[0] = 3;
                                    }
                                    strArr = new String[]{LocaleController.getString(C2797R.string.VibrationDefault), LocaleController.getString(C2797R.string.Short), LocaleController.getString(C2797R.string.Long), LocaleController.getString(C2797R.string.VibrationDisabled)};
                                } else {
                                    int i2 = notificationsSettings.getInt(str2, 0);
                                    iArr[0] = i2;
                                    if (i2 == 0) {
                                        iArr[0] = 1;
                                    } else if (i2 == 1) {
                                        iArr[0] = 2;
                                    } else if (i2 == 2) {
                                        iArr[0] = 0;
                                    }
                                    strArr = new String[]{LocaleController.getString(C2797R.string.VibrationDisabled), LocaleController.getString(C2797R.string.VibrationDefault), LocaleController.getString(C2797R.string.Short), LocaleController.getString(C2797R.string.Long), LocaleController.getString(C2797R.string.OnlyIfSilent)};
                                }
                                String[] strArr2 = strArr;
                                LinearLayout linearLayout = new LinearLayout(activity);
                                linearLayout.setOrientation(1);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(activity, resourcesProvider);
                                int i3 = 0;
                                while (i3 < strArr2.length) {
                                    RadioColorCell radioColorCell = new RadioColorCell(activity, resourcesProvider);
                                    radioColorCell.setPadding(AndroidUtilities.m1036dp(4.0f), 0, AndroidUtilities.m1036dp(4.0f), 0);
                                    radioColorCell.setTag(Integer.valueOf(i3));
                                    radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground, resourcesProvider), Theme.getColor(Theme.key_dialogRadioBackgroundChecked, resourcesProvider));
                                    radioColorCell.setTextAndValue(strArr2[i3], iArr[0] == i3 ? z : false);
                                    linearLayout.addView(radioColorCell);
                                    radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda152
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            AlertsCreator.$r8$lambda$7H8FD5YbcHaIiZKkk_w4xIQ05qU(iArr, j, str2, j2, builder, runnable, view);
                                        }
                                    });
                                    i3++;
                                    str2 = str;
                                    z = true;
                                }
                                builder.setTitle(LocaleController.getString(C2797R.string.Vibrate));
                                builder.setView(linearLayout);
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Cancel), null);
                                return builder.create();
                            }

                            public static /* synthetic */ void $r8$lambda$7H8FD5YbcHaIiZKkk_w4xIQ05qU(int[] iArr, long j, String str, long j2, AlertDialog.Builder builder, Runnable runnable, View view) {
                                iArr[0] = ((Integer) view.getTag()).intValue();
                                SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(UserConfig.selectedAccount).edit();
                                if (j != 0) {
                                    int i = iArr[0];
                                    if (i == 0) {
                                        editorEdit.putInt(str, 0);
                                    } else if (i == 1) {
                                        editorEdit.putInt(str, 1);
                                    } else if (i == 2) {
                                        editorEdit.putInt(str, 3);
                                    } else if (i == 3) {
                                        editorEdit.putInt(str, 2);
                                    }
                                    NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannel(j, j2);
                                } else {
                                    int i2 = iArr[0];
                                    if (i2 == 0) {
                                        editorEdit.putInt(str, 2);
                                    } else if (i2 == 1) {
                                        editorEdit.putInt(str, 0);
                                    } else if (i2 == 2) {
                                        editorEdit.putInt(str, 1);
                                    } else if (i2 == 3) {
                                        editorEdit.putInt(str, 3);
                                    } else if (i2 == 4) {
                                        editorEdit.putInt(str, 4);
                                    }
                                    if (str.equals("vibrate_channel")) {
                                        NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannelGlobal(2);
                                    } else if (str.equals("vibrate_group")) {
                                        NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannelGlobal(0);
                                    } else if (str.equals("vibrate_react")) {
                                        NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannelGlobal(4);
                                    } else {
                                        NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannelGlobal(1);
                                    }
                                }
                                editorEdit.apply();
                                builder.getDismissRunnable().run();
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            public static Dialog createLocationUpdateDialog(Activity activity, boolean z, TLRPC.User user, final MessagesStorage.IntCallback intCallback, Theme.ResourcesProvider resourcesProvider) {
                                final int[] iArr = new int[1];
                                String[] strArr = {LocaleController.getString(C2797R.string.SendLiveLocationFor15m), LocaleController.getString(C2797R.string.SendLiveLocationFor1h), LocaleController.getString(C2797R.string.SendLiveLocationFor8h), LocaleController.getString(C2797R.string.SendLiveLocationForever)};
                                final LinearLayout linearLayout = new LinearLayout(activity);
                                linearLayout.setOrientation(1);
                                linearLayout.setPadding(0, 0, 0, AndroidUtilities.m1036dp(4.0f));
                                TextView textView = new TextView(activity);
                                if (z) {
                                    textView.setText(LocaleController.getString(C2797R.string.LiveLocationAlertExpandMessage));
                                } else if (user != null) {
                                    textView.setText(LocaleController.formatString(C2797R.string.LiveLocationAlertPrivate, UserObject.getFirstName(user)));
                                } else {
                                    textView.setText(LocaleController.getString(C2797R.string.LiveLocationAlertGroup));
                                }
                                int i = Theme.key_dialogTextBlack;
                                textView.setTextColor(resourcesProvider != null ? resourcesProvider.getColorOrDefault(i) : Theme.getColor(i));
                                textView.setTextSize(1, 16.0f);
                                textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
                                linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 24, z ? 4 : 0, 24, 8));
                                int i2 = 0;
                                while (i2 < 4) {
                                    RadioColorCell radioColorCell = new RadioColorCell(activity, resourcesProvider);
                                    radioColorCell.heightDp = 42;
                                    radioColorCell.setPadding(AndroidUtilities.m1036dp(4.0f), 0, AndroidUtilities.m1036dp(4.0f), 0);
                                    radioColorCell.setTag(Integer.valueOf(i2));
                                    int i3 = Theme.key_radioBackground;
                                    int colorOrDefault = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i3) : Theme.getColor(i3);
                                    int i4 = Theme.key_dialogRadioBackgroundChecked;
                                    radioColorCell.setCheckColor(colorOrDefault, resourcesProvider != null ? resourcesProvider.getColorOrDefault(i4) : Theme.getColor(i4));
                                    radioColorCell.setTextAndValue(strArr[i2], iArr[0] == i2);
                                    linearLayout.addView(radioColorCell);
                                    radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda94
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            AlertsCreator.$r8$lambda$w4qlMzjTuaSj5nNaIx3YlhHlSBQ(iArr, linearLayout, view);
                                        }
                                    });
                                    i2++;
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity, resourcesProvider);
                                if (z) {
                                    builder.setTitle(LocaleController.getString(C2797R.string.LiveLocationAlertExpandTitle));
                                } else {
                                    builder.setTopImage(new ShareLocationDrawable(activity, 0), resourcesProvider != null ? resourcesProvider.getColorOrDefault(Theme.key_dialogTopBackground) : Theme.getColor(Theme.key_dialogTopBackground));
                                }
                                builder.setView(linearLayout);
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.ShareFile), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda95
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i5) {
                                        AlertsCreator.$r8$lambda$bKjyBJHrMLbXro9Nve9Do6PPrPM(iArr, intCallback, alertDialog, i5);
                                    }
                                });
                                builder.setNeutralButton(LocaleController.getString(C2797R.string.Cancel), null);
                                return builder.create();
                            }

                            public static /* synthetic */ void $r8$lambda$w4qlMzjTuaSj5nNaIx3YlhHlSBQ(int[] iArr, LinearLayout linearLayout, View view) {
                                iArr[0] = ((Integer) view.getTag()).intValue();
                                int childCount = linearLayout.getChildCount();
                                for (int i = 0; i < childCount; i++) {
                                    View childAt = linearLayout.getChildAt(i);
                                    if (childAt instanceof RadioColorCell) {
                                        ((RadioColorCell) childAt).setChecked(childAt == view, true);
                                    }
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$bKjyBJHrMLbXro9Nve9Do6PPrPM(int[] iArr, MessagesStorage.IntCallback intCallback, AlertDialog alertDialog, int i) {
                                int i2 = iArr[0];
                                intCallback.run(i2 == 0 ? RichMessageLayout.PART_MAX_HEIGHT_DP : i2 == 1 ? 3600 : i2 == 2 ? 28800 : Integer.MAX_VALUE);
                            }

                            public static AlertDialog.Builder createBackgroundLocationPermissionDialog(final Activity activity, TLRPC.User user, final Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
                                if (activity == null || Build.VERSION.SDK_INT < 29) {
                                    return null;
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity, resourcesProvider);
                                String res = AndroidUtilities.readRes(Theme.getCurrentTheme().isDark() ? C2797R.raw.permission_map_dark : C2797R.raw.permission_map);
                                String res2 = AndroidUtilities.readRes(Theme.getCurrentTheme().isDark() ? C2797R.raw.permission_pin_dark : C2797R.raw.permission_pin);
                                FrameLayout frameLayout = new FrameLayout(activity);
                                frameLayout.setClipToOutline(true);
                                frameLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: org.telegram.ui.Components.AlertsCreator.59
                                    @Override // android.view.ViewOutlineProvider
                                    public void getOutline(View view, Outline outline) {
                                        outline.setRoundRect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight() + AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f));
                                    }
                                });
                                View view = new View(activity);
                                view.setBackground(SvgHelper.getDrawable(res));
                                frameLayout.addView(view, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
                                View view2 = new View(activity);
                                view2.setBackground(SvgHelper.getDrawable(res2));
                                frameLayout.addView(view2, LayoutHelper.createFrame(60, 82.0f, 17, 0.0f, 0.0f, 0.0f, 0.0f));
                                BackupImageView backupImageView = new BackupImageView(activity);
                                backupImageView.setRoundRadius(ExteraConfig.getAvatarCorners(52.0f));
                                backupImageView.setForUserOrChat(user, new AvatarDrawable(user));
                                frameLayout.addView(backupImageView, LayoutHelper.createFrame(52, 52.0f, 17, 0.0f, 0.0f, 0.0f, 11.0f));
                                builder.setTopView(frameLayout);
                                builder.setTopViewAspectRatio(0.37820512f);
                                builder.setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.PermissionBackgroundLocation)));
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Continue), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda139
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        AlertsCreator.$r8$lambda$X4L04qfCmFBch9VqjyAYpPUQLm0(activity, alertDialog, i);
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda140
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        runnable.run();
                                    }
                                });
                                return builder;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$59 */
                            public class C376659 extends ViewOutlineProvider {
                                @Override // android.view.ViewOutlineProvider
                                public void getOutline(View view, Outline outline) {
                                    outline.setRoundRect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight() + AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f));
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$X4L04qfCmFBch9VqjyAYpPUQLm0(Activity activity, AlertDialog alertDialog, int i) {
                                if (activity.checkSelfPermission("android.permission.ACCESS_BACKGROUND_LOCATION") != 0) {
                                    activity.requestPermissions(new String[]{"android.permission.ACCESS_BACKGROUND_LOCATION"}, 30);
                                }
                            }

                            public static AlertDialog.Builder createGigagroupConvertAlert(Activity activity, AlertDialog.OnButtonClickListener onButtonClickListener, AlertDialog.OnButtonClickListener onButtonClickListener2) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                String res = AndroidUtilities.readRes(C2797R.raw.gigagroup);
                                FrameLayout frameLayout = new FrameLayout(activity);
                                frameLayout.setClipToOutline(true);
                                frameLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: org.telegram.ui.Components.AlertsCreator.60
                                    @Override // android.view.ViewOutlineProvider
                                    public void getOutline(View view, Outline outline) {
                                        outline.setRoundRect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight() + AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f));
                                    }
                                });
                                View view = new View(activity);
                                view.setBackground(new BitmapDrawable(SvgHelper.getBitmap(res, AndroidUtilities.m1036dp(320.0f), AndroidUtilities.m1036dp(127.17949f), false)));
                                frameLayout.addView(view, LayoutHelper.createFrame(-1, -1.0f, 0, -1.0f, -1.0f, -1.0f, -1.0f));
                                builder.setTopView(frameLayout);
                                builder.setTopViewAspectRatio(0.3974359f);
                                builder.setTitle(LocaleController.getString(C2797R.string.GigagroupAlertTitle));
                                builder.setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.GigagroupAlertText)));
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.GigagroupAlertLearnMore), onButtonClickListener);
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), onButtonClickListener2);
                                return builder;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$60 */
                            public class C376860 extends ViewOutlineProvider {
                                @Override // android.view.ViewOutlineProvider
                                public void getOutline(View view, Outline outline) {
                                    outline.setRoundRect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight() + AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f));
                                }
                            }

                            public static AlertDialog.Builder createDrawOverlayPermissionDialog(Activity activity, AlertDialog.OnButtonClickListener onButtonClickListener) {
                                return createDrawOverlayPermissionDialog(activity, onButtonClickListener, false);
                            }

                            public static AlertDialog.Builder createDrawOverlayPermissionDialog(final Activity activity, AlertDialog.OnButtonClickListener onButtonClickListener, final boolean z) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                String res = AndroidUtilities.readRes(C2797R.raw.pip_video_request);
                                FrameLayout frameLayout = new FrameLayout(activity);
                                frameLayout.setBackground(new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{-14535089, -14527894}));
                                frameLayout.setClipToOutline(true);
                                frameLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: org.telegram.ui.Components.AlertsCreator.61
                                    @Override // android.view.ViewOutlineProvider
                                    public void getOutline(View view, Outline outline) {
                                        outline.setRoundRect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight() + AndroidUtilities.m1036dp(6.0f), AndroidUtilities.dpf2(6.0f));
                                    }
                                });
                                View view = new View(activity);
                                view.setBackground(new BitmapDrawable(SvgHelper.getBitmap(res, AndroidUtilities.m1036dp(320.0f), AndroidUtilities.m1036dp(161.36752f), false)));
                                frameLayout.addView(view, LayoutHelper.createFrame(-1, -1.0f, 0, -1.0f, -1.0f, -1.0f, -1.0f));
                                builder.setTopView(frameLayout);
                                builder.setTitle(LocaleController.getString(C2797R.string.PermissionDrawAboveOtherAppsTitle));
                                builder.setMessage(LocaleController.getString(C2797R.string.PermissionDrawAboveOtherApps));
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Enable), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda83
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        AlertsCreator.$r8$lambda$jQlaHQMB64Cu6UQfHHu5rmF1OgI(activity, z, alertDialog, i);
                                    }
                                });
                                builder.notDrawBackgroundOnTopView(true);
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), onButtonClickListener);
                                builder.setTopViewAspectRatio(0.50427353f);
                                return builder;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$61 */
                            public class C376961 extends ViewOutlineProvider {
                                @Override // android.view.ViewOutlineProvider
                                public void getOutline(View view, Outline outline) {
                                    outline.setRoundRect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight() + AndroidUtilities.m1036dp(6.0f), AndroidUtilities.dpf2(6.0f));
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$jQlaHQMB64Cu6UQfHHu5rmF1OgI(Activity activity, boolean z, AlertDialog alertDialog, int i) {
                                if (activity != null) {
                                    if (z && PipUtils.checkPermissions(activity) == -2) {
                                        try {
                                            activity.startActivity(new Intent("android.settings.PICTURE_IN_PICTURE_SETTINGS", Uri.parse("package:" + activity.getPackageName())));
                                            return;
                                        } catch (Exception e) {
                                            FileLog.m1048e(e);
                                        }
                                    }
                                    try {
                                        activity.startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + activity.getPackageName())));
                                    } catch (Exception e2) {
                                        FileLog.m1048e(e2);
                                    }
                                }
                            }

                            public static AlertDialog.Builder createDrawOverlayGroupCallPermissionDialog(final Context context) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                String res = AndroidUtilities.readRes(C2797R.raw.pip_voice_request);
                                GroupCallPipButton groupCallPipButton = new GroupCallPipButton(context, 0, true);
                                groupCallPipButton.setImportantForAccessibility(2);
                                C377062 c377062 = new FrameLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.62
                                    final /* synthetic */ GroupCallPipButton val$button;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C377062(final Context context2, GroupCallPipButton groupCallPipButton2) {
                                        super(context2);
                                        groupCallPipButton = groupCallPipButton2;
                                    }

                                    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
                                    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
                                        super.onLayout(z, i, i2, i3, i4);
                                        groupCallPipButton.setTranslationY((getMeasuredHeight() * 0.28f) - (groupCallPipButton.getMeasuredWidth() / 2.0f));
                                        groupCallPipButton.setTranslationX((getMeasuredWidth() * 0.82f) - (groupCallPipButton.getMeasuredWidth() / 2.0f));
                                    }
                                };
                                c377062.setBackground(new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{-15128003, -15118002}));
                                c377062.setClipToOutline(true);
                                c377062.setOutlineProvider(new ViewOutlineProvider() { // from class: org.telegram.ui.Components.AlertsCreator.63
                                    @Override // android.view.ViewOutlineProvider
                                    public void getOutline(View view, Outline outline) {
                                        outline.setRoundRect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight() + AndroidUtilities.m1036dp(6.0f), AndroidUtilities.dpf2(6.0f));
                                    }
                                });
                                View view = new View(context2);
                                view.setBackground(new BitmapDrawable(SvgHelper.getBitmap(res, AndroidUtilities.m1036dp(320.0f), AndroidUtilities.m1036dp(184.61539f), false)));
                                c377062.addView(view, LayoutHelper.createFrame(-1, -1.0f, 0, -1.0f, -1.0f, -1.0f, -1.0f));
                                c377062.addView(groupCallPipButton2, LayoutHelper.createFrame(117, 117.0f));
                                builder.setTopView(c377062);
                                builder.setTitle(LocaleController.getString(C2797R.string.PermissionDrawAboveOtherAppsGroupCallTitle));
                                builder.setMessage(LocaleController.getString(C2797R.string.PermissionDrawAboveOtherAppsGroupCall));
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Enable), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda50
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        AlertsCreator.$r8$lambda$LCSqnI2z3alJ73ytKXV5PKBgo_8(context2, alertDialog, i);
                                    }
                                });
                                builder.notDrawBackgroundOnTopView(true);
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                builder.setTopViewAspectRatio(0.5769231f);
                                return builder;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$62 */
                            public class C377062 extends FrameLayout {
                                final /* synthetic */ GroupCallPipButton val$button;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C377062(final Context context2, GroupCallPipButton groupCallPipButton2) {
                                    super(context2);
                                    groupCallPipButton = groupCallPipButton2;
                                }

                                @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
                                public void onLayout(boolean z, int i, int i2, int i3, int i4) {
                                    super.onLayout(z, i, i2, i3, i4);
                                    groupCallPipButton.setTranslationY((getMeasuredHeight() * 0.28f) - (groupCallPipButton.getMeasuredWidth() / 2.0f));
                                    groupCallPipButton.setTranslationX((getMeasuredWidth() * 0.82f) - (groupCallPipButton.getMeasuredWidth() / 2.0f));
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$63 */
                            public class C377163 extends ViewOutlineProvider {
                                @Override // android.view.ViewOutlineProvider
                                public void getOutline(View view, Outline outline) {
                                    outline.setRoundRect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight() + AndroidUtilities.m1036dp(6.0f), AndroidUtilities.dpf2(6.0f));
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$LCSqnI2z3alJ73ytKXV5PKBgo_8(Context context, AlertDialog alertDialog, int i) {
                                if (context != null) {
                                    try {
                                        Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + context.getPackageName()));
                                        Activity activityFindActivity = AndroidUtilities.findActivity(context);
                                        if (activityFindActivity instanceof LaunchActivity) {
                                            activityFindActivity.startActivityForResult(intent, 105);
                                        } else {
                                            context.startActivity(intent);
                                        }
                                    } catch (Exception e) {
                                        FileLog.m1048e(e);
                                    }
                                }
                            }

                            public static AlertDialog.Builder createContactsPermissionDialog(Activity activity, final MessagesStorage.IntCallback intCallback) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                builder.setTopAnimation(C2797R.raw.permission_request_contacts, 72, false, Theme.getColor(Theme.key_dialogTopBackground));
                                builder.setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.ContactsPermissionAlert)));
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.ContactsPermissionAlertContinue), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda10
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        intCallback.run(1);
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.ContactsPermissionAlertNotNow), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda11
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        intCallback.run(0);
                                    }
                                });
                                return builder;
                            }

                            public static Dialog createFreeSpaceDialog(final LaunchActivity launchActivity) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(launchActivity);
                                builder.setTitle(LocaleController.getString(C2797R.string.LowDiskSpaceTitle));
                                builder.setMessage(LocaleController.getString(C2797R.string.LowDiskSpaceMessage2));
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.LowDiskSpaceButton), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda142
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        launchActivity.lambda$runLinkRequest$101(new CacheControlActivity());
                                    }
                                });
                                return builder.create();
                            }

                            public static Dialog createPrioritySelectDialog(Activity activity, long j, int i, int i2, Runnable runnable) {
                                return createPrioritySelectDialog(activity, j, i, i2, runnable, null);
                            }

                            public static Dialog createPrioritySelectDialog(Activity activity, long j, final long j2, int i, final Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
                                String[] strArr;
                                final long j3 = j;
                                final int i2 = i;
                                final SharedPreferences notificationsSettings = MessagesController.getNotificationsSettings(UserConfig.selectedAccount);
                                boolean z = true;
                                final int[] iArr = new int[1];
                                if (j3 != 0) {
                                    int i3 = notificationsSettings.getInt("priority_" + j3, 3);
                                    iArr[0] = i3;
                                    if (i3 == 3) {
                                        iArr[0] = 0;
                                    } else if (i3 == 4) {
                                        iArr[0] = 1;
                                    } else if (i3 == 5) {
                                        iArr[0] = 2;
                                    } else if (i3 == 0) {
                                        iArr[0] = 3;
                                    } else {
                                        iArr[0] = 4;
                                    }
                                    strArr = new String[]{LocaleController.getString(C2797R.string.NotificationsPrioritySettings), LocaleController.getString(C2797R.string.NotificationsPriorityLow), LocaleController.getString(C2797R.string.NotificationsPriorityMedium), LocaleController.getString(C2797R.string.NotificationsPriorityHigh), LocaleController.getString(C2797R.string.NotificationsPriorityUrgent)};
                                } else {
                                    if (i2 == 1) {
                                        iArr[0] = notificationsSettings.getInt("priority_messages", 1);
                                    } else if (i2 == 0) {
                                        iArr[0] = notificationsSettings.getInt("priority_group", 1);
                                    } else if (i2 == 2) {
                                        iArr[0] = notificationsSettings.getInt("priority_channel", 1);
                                    } else if (i2 == 3) {
                                        iArr[0] = notificationsSettings.getInt("priority_stories", 1);
                                    } else if (i2 == 4 || i2 == 5) {
                                        iArr[0] = notificationsSettings.getInt("priority_react", 1);
                                    }
                                    int i4 = iArr[0];
                                    if (i4 == 4) {
                                        iArr[0] = 0;
                                    } else if (i4 == 5) {
                                        iArr[0] = 1;
                                    } else if (i4 == 0) {
                                        iArr[0] = 2;
                                    } else {
                                        iArr[0] = 3;
                                    }
                                    strArr = new String[]{LocaleController.getString(C2797R.string.NotificationsPriorityLow), LocaleController.getString(C2797R.string.NotificationsPriorityMedium), LocaleController.getString(C2797R.string.NotificationsPriorityHigh), LocaleController.getString(C2797R.string.NotificationsPriorityUrgent)};
                                }
                                String[] strArr2 = strArr;
                                LinearLayout linearLayout = new LinearLayout(activity);
                                linearLayout.setOrientation(1);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(activity, resourcesProvider);
                                int i5 = 0;
                                while (i5 < strArr2.length) {
                                    RadioColorCell radioColorCell = new RadioColorCell(activity, resourcesProvider);
                                    radioColorCell.setPadding(AndroidUtilities.m1036dp(4.0f), 0, AndroidUtilities.m1036dp(4.0f), 0);
                                    radioColorCell.setTag(Integer.valueOf(i5));
                                    radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground, resourcesProvider), Theme.getColor(Theme.key_dialogRadioBackgroundChecked, resourcesProvider));
                                    radioColorCell.setTextAndValue(strArr2[i5], iArr[0] == i5 ? z : false);
                                    linearLayout.addView(radioColorCell);
                                    radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda158
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            AlertsCreator.m9981$r8$lambda$af09DBrSCBybcL3mY40eKC66X0(iArr, j3, j2, i2, notificationsSettings, builder, runnable, view);
                                        }
                                    });
                                    i5++;
                                    j3 = j;
                                    i2 = i;
                                    z = true;
                                }
                                builder.setTitle(LocaleController.getString(C2797R.string.NotificationsImportance));
                                builder.setView(linearLayout);
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Cancel), null);
                                return builder.create();
                            }

                            /* JADX INFO: renamed from: $r8$lambda$af09DB-rSCBybcL3mY40eKC66X0 */
                            public static /* synthetic */ void m9981$r8$lambda$af09DBrSCBybcL3mY40eKC66X0(int[] iArr, long j, long j2, int i, SharedPreferences sharedPreferences, AlertDialog.Builder builder, Runnable runnable, View view) {
                                int i2 = 0;
                                iArr[0] = ((Integer) view.getTag()).intValue();
                                SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(UserConfig.selectedAccount).edit();
                                if (j != 0) {
                                    int i3 = iArr[0];
                                    if (i3 == 0) {
                                        i2 = 3;
                                    } else if (i3 == 1) {
                                        i2 = 4;
                                    } else if (i3 == 2) {
                                        i2 = 5;
                                    } else if (i3 != 3) {
                                        i2 = 1;
                                    }
                                    editorEdit.putInt("priority_" + j, i2);
                                    NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannel(j, j2);
                                } else {
                                    int i4 = iArr[0];
                                    int i5 = i4 == 0 ? 4 : i4 == 1 ? 5 : i4 == 2 ? 0 : 1;
                                    if (i == 1) {
                                        editorEdit.putInt("priority_messages", i5);
                                        iArr[0] = sharedPreferences.getInt("priority_messages", 1);
                                    } else if (i == 0) {
                                        editorEdit.putInt("priority_group", i5);
                                        iArr[0] = sharedPreferences.getInt("priority_group", 1);
                                    } else if (i == 2) {
                                        editorEdit.putInt("priority_channel", i5);
                                        iArr[0] = sharedPreferences.getInt("priority_channel", 1);
                                    } else if (i == 3) {
                                        editorEdit.putInt("priority_stories", i5);
                                        iArr[0] = sharedPreferences.getInt("priority_stories", 1);
                                    } else if (i == 4 || i == 5) {
                                        editorEdit.putInt("priority_react", i5);
                                        iArr[0] = sharedPreferences.getInt("priority_react", 1);
                                    }
                                    NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannelGlobal(i);
                                }
                                editorEdit.apply();
                                builder.getDismissRunnable().run();
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            public static Dialog createPopupSelectDialog(Activity activity, final int i, final Runnable runnable) {
                                SharedPreferences notificationsSettings = MessagesController.getNotificationsSettings(UserConfig.selectedAccount);
                                final int[] iArr = new int[1];
                                if (i == 1) {
                                    iArr[0] = notificationsSettings.getInt("popupAll", 0);
                                } else if (i == 0) {
                                    iArr[0] = notificationsSettings.getInt("popupGroup", 0);
                                } else {
                                    iArr[0] = notificationsSettings.getInt("popupChannel", 0);
                                }
                                String[] strArr = {LocaleController.getString(C2797R.string.NoPopup), LocaleController.getString(C2797R.string.OnlyWhenScreenOn), LocaleController.getString(C2797R.string.OnlyWhenScreenOff), LocaleController.getString(C2797R.string.AlwaysShowPopup)};
                                LinearLayout linearLayout = new LinearLayout(activity);
                                linearLayout.setOrientation(1);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                int i2 = 0;
                                while (i2 < 4) {
                                    RadioColorCell radioColorCell = new RadioColorCell(activity);
                                    radioColorCell.setTag(Integer.valueOf(i2));
                                    radioColorCell.setPadding(AndroidUtilities.m1036dp(4.0f), 0, AndroidUtilities.m1036dp(4.0f), 0);
                                    radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
                                    radioColorCell.setTextAndValue(strArr[i2], iArr[0] == i2);
                                    linearLayout.addView(radioColorCell);
                                    radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda132
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            AlertsCreator.m9949$r8$lambda$EKB9F5UbzpL08KgrKuPxgntxes(iArr, i, builder, runnable, view);
                                        }
                                    });
                                    i2++;
                                }
                                builder.setTitle(LocaleController.getString(C2797R.string.PopupNotification));
                                builder.setView(linearLayout);
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Cancel), null);
                                return builder.create();
                            }

                            /* JADX INFO: renamed from: $r8$lambda$EKB9F5UbzpL-08KgrKuPxgntxes */
                            public static /* synthetic */ void m9949$r8$lambda$EKB9F5UbzpL08KgrKuPxgntxes(int[] iArr, int i, AlertDialog.Builder builder, Runnable runnable, View view) {
                                iArr[0] = ((Integer) view.getTag()).intValue();
                                SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(UserConfig.selectedAccount).edit();
                                if (i == 1) {
                                    editorEdit.putInt("popupAll", iArr[0]);
                                } else if (i == 0) {
                                    editorEdit.putInt("popupGroup", iArr[0]);
                                } else {
                                    editorEdit.putInt("popupChannel", iArr[0]);
                                }
                                editorEdit.apply();
                                builder.getDismissRunnable().run();
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            public static Dialog createSingleChoiceDialog(Activity activity, String[] strArr, String str, int i, final DialogInterface.OnClickListener onClickListener) {
                                LinearLayout linearLayout = new LinearLayout(activity);
                                linearLayout.setOrientation(1);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                int i2 = 0;
                                while (i2 < strArr.length) {
                                    RadioColorCell radioColorCell = new RadioColorCell(activity);
                                    radioColorCell.setPadding(AndroidUtilities.m1036dp(4.0f), 0, AndroidUtilities.m1036dp(4.0f), 0);
                                    radioColorCell.setTag(Integer.valueOf(i2));
                                    radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
                                    radioColorCell.setTextAndValue(strArr[i2], i == i2);
                                    linearLayout.addView(radioColorCell);
                                    radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda141
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            AlertsCreator.$r8$lambda$vn6GLrm2UaUGezxY1n8seBmQTn0(builder, onClickListener, view);
                                        }
                                    });
                                    i2++;
                                }
                                builder.setTitle(str);
                                builder.setView(linearLayout);
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Cancel), null);
                                return builder.create();
                            }

                            public static /* synthetic */ void $r8$lambda$vn6GLrm2UaUGezxY1n8seBmQTn0(AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener, View view) {
                                int iIntValue = ((Integer) view.getTag()).intValue();
                                builder.getDismissRunnable().run();
                                onClickListener.onClick(null, iIntValue);
                            }

                            public static AlertDialog.Builder createTTLAlert(Context context, final TLRPC.EncryptedChat encryptedChat, Theme.ResourcesProvider resourcesProvider) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context, resourcesProvider);
                                builder.setTitle(LocaleController.getString(C2797R.string.MessageLifetime));
                                final NumberPicker numberPicker = new NumberPicker(context);
                                numberPicker.setMinValue(0);
                                numberPicker.setMaxValue(20);
                                int i = encryptedChat.ttl;
                                if (i > 0 && i < 16) {
                                    numberPicker.setValue(i);
                                } else if (i == 30) {
                                    numberPicker.setValue(16);
                                } else if (i == 60) {
                                    numberPicker.setValue(17);
                                } else if (i == 3600) {
                                    numberPicker.setValue(18);
                                } else if (i == 86400) {
                                    numberPicker.setValue(19);
                                } else if (i == 604800) {
                                    numberPicker.setValue(20);
                                } else if (i == 0) {
                                    numberPicker.setValue(0);
                                }
                                numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda48
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i2) {
                                        return AlertsCreator.$r8$lambda$3hwz4UAjZXtzghP0to8keJtJazw(i2);
                                    }
                                });
                                builder.setView(numberPicker);
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Done), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda49
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i2) {
                                        AlertsCreator.$r8$lambda$bNOacsp418DXt3C6z4UhqMo5gqk(encryptedChat, numberPicker, alertDialog, i2);
                                    }
                                });
                                return builder;
                            }

                            public static /* synthetic */ String $r8$lambda$3hwz4UAjZXtzghP0to8keJtJazw(int i) {
                                if (i == 0) {
                                    return LocaleController.getString(C2797R.string.ShortMessageLifetimeForever);
                                }
                                if (i >= 1 && i < 16) {
                                    return LocaleController.formatTTLString(i);
                                }
                                if (i == 16) {
                                    return LocaleController.formatTTLString(30);
                                }
                                if (i == 17) {
                                    return LocaleController.formatTTLString(60);
                                }
                                if (i == 18) {
                                    return LocaleController.formatTTLString(3600);
                                }
                                if (i == 19) {
                                    return LocaleController.formatTTLString(86400);
                                }
                                if (i == 20) {
                                    return LocaleController.formatTTLString(604800);
                                }
                                return _UrlKt.FRAGMENT_ENCODE_SET;
                            }

                            public static /* synthetic */ void $r8$lambda$bNOacsp418DXt3C6z4UhqMo5gqk(TLRPC.EncryptedChat encryptedChat, NumberPicker numberPicker, AlertDialog alertDialog, int i) {
                                int i2 = encryptedChat.ttl;
                                int value = numberPicker.getValue();
                                if (value >= 0 && value < 16) {
                                    encryptedChat.ttl = value;
                                } else if (value == 16) {
                                    encryptedChat.ttl = 30;
                                } else if (value == 17) {
                                    encryptedChat.ttl = 60;
                                } else if (value == 18) {
                                    encryptedChat.ttl = 3600;
                                } else if (value == 19) {
                                    encryptedChat.ttl = 86400;
                                } else if (value == 20) {
                                    encryptedChat.ttl = 604800;
                                }
                                if (i2 != encryptedChat.ttl) {
                                    SecretChatHelper.getInstance(UserConfig.selectedAccount).sendTTLMessage(encryptedChat, null);
                                    MessagesStorage.getInstance(UserConfig.selectedAccount).updateEncryptedChatTTL(encryptedChat);
                                }
                            }

                            public static AlertDialog createAccountSelectDialog(Activity activity, final AccountSelectDelegate accountSelectDelegate) {
                                if (UserConfig.getActivatedAccountsCount() < 2) {
                                    return null;
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                final Runnable dismissRunnable = builder.getDismissRunnable();
                                final AlertDialog[] alertDialogArr = new AlertDialog[1];
                                LinearLayout linearLayout = new LinearLayout(activity);
                                linearLayout.setOrientation(1);
                                for (int i = 0; i < 16; i++) {
                                    if (UserConfig.getInstance(i).getCurrentUser() != null) {
                                        AccountSelectCell accountSelectCell = new AccountSelectCell(activity, false);
                                        accountSelectCell.setAccount(i, false);
                                        accountSelectCell.setPadding(AndroidUtilities.m1036dp(14.0f), 0, AndroidUtilities.m1036dp(14.0f), 0);
                                        accountSelectCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                                        linearLayout.addView(accountSelectCell, LayoutHelper.createLinear(-1, 50));
                                        accountSelectCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda31
                                            @Override // android.view.View.OnClickListener
                                            public final void onClick(View view) {
                                                AlertsCreator.m9998$r8$lambda$oBQWthlntUIblrYSXbl3zefzY(alertDialogArr, dismissRunnable, accountSelectDelegate, view);
                                            }
                                        });
                                    }
                                }
                                builder.setTitle(LocaleController.getString(C2797R.string.SelectAccount));
                                builder.setView(linearLayout);
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Cancel), null);
                                AlertDialog alertDialogCreate = builder.create();
                                alertDialogArr[0] = alertDialogCreate;
                                return alertDialogCreate;
                            }

                            /* JADX INFO: renamed from: $r8$lambda$oBQWthlntUIblr--YSXbl3zefzY */
                            public static /* synthetic */ void m9998$r8$lambda$oBQWthlntUIblrYSXbl3zefzY(AlertDialog[] alertDialogArr, Runnable runnable, AccountSelectDelegate accountSelectDelegate, View view) {
                                AlertDialog alertDialog = alertDialogArr[0];
                                if (alertDialog != null) {
                                    alertDialog.setOnDismissListener(null);
                                }
                                runnable.run();
                                accountSelectDelegate.didSelectAccount(((AccountSelectCell) view).getAccountNumber());
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX WARN: Removed duplicated region for block: B:473:0x0075  */
                            /* JADX WARN: Removed duplicated region for block: B:509:0x01ac  */
                            /* JADX WARN: Removed duplicated region for block: B:520:0x01e1  */
                            /* JADX WARN: Removed duplicated region for block: B:535:0x0229  */
                            /* JADX WARN: Removed duplicated region for block: B:540:0x0249  */
                            /* JADX WARN: Removed duplicated region for block: B:552:0x027f  */
                            /* JADX WARN: Removed duplicated region for block: B:554:0x0283  */
                            /* JADX WARN: Removed duplicated region for block: B:555:0x028a  */
                            /* JADX WARN: Removed duplicated region for block: B:562:0x02a0  */
                            /* JADX WARN: Removed duplicated region for block: B:566:0x02ae  */
                            /* JADX WARN: Removed duplicated region for block: B:646:0x04e2  */
                            /* JADX WARN: Removed duplicated region for block: B:747:0x0692  */
                            /* JADX WARN: Removed duplicated region for block: B:751:0x06b9  */
                            /* JADX WARN: Removed duplicated region for block: B:756:0x06df  */
                            /* JADX WARN: Removed duplicated region for block: B:760:0x06f7  */
                            /* JADX WARN: Removed duplicated region for block: B:789:0x07a4  */
                            /* JADX WARN: Removed duplicated region for block: B:803:0x07e2  */
                            /* JADX WARN: Removed duplicated region for block: B:827:0x0842  */
                            /* JADX WARN: Removed duplicated region for block: B:828:0x0877  */
                            /* JADX WARN: Removed duplicated region for block: B:839:0x090a  */
                            /* JADX WARN: Removed duplicated region for block: B:842:0x091c  */
                            /* JADX WARN: Removed duplicated region for block: B:871:0x0232 A[SYNTHETIC] */
                            /* JADX WARN: Removed duplicated region for block: B:873:? A[RETURN, SYNTHETIC] */
                            /* JADX WARN: Type inference failed for: r0v57 */
                            /* JADX WARN: Type inference failed for: r0v58, types: [int] */
                            /* JADX WARN: Type inference failed for: r0v71 */
                            /* JADX WARN: Type inference failed for: r15v17 */
                            /* JADX WARN: Type inference failed for: r15v18, types: [int] */
                            /* JADX WARN: Type inference failed for: r15v25 */
                            /* JADX WARN: Type inference failed for: r23v8 */
                            /* JADX WARN: Type inference failed for: r2v103 */
                            /* JADX WARN: Type inference failed for: r2v124 */
                            /* JADX WARN: Type inference failed for: r2v125 */
                            /* JADX WARN: Type inference failed for: r2v126 */
                            /* JADX WARN: Type inference failed for: r2v127 */
                            /* JADX WARN: Type inference failed for: r2v2 */
                            /* JADX WARN: Type inference failed for: r2v31 */
                            /* JADX WARN: Type inference failed for: r2v76 */
                            /* JADX WARN: Type inference failed for: r2v79 */
                            /* JADX WARN: Type inference failed for: r2v86 */
                            /* JADX WARN: Type inference failed for: r2v96 */
                            /* JADX WARN: Type inference failed for: r3v31, types: [java.util.ArrayList] */
                            /* JADX WARN: Type inference failed for: r3v33 */
                            /* JADX WARN: Type inference failed for: r3v35 */
                            /* JADX WARN: Type inference failed for: r3v57 */
                            /* JADX WARN: Type inference failed for: r42v0 */
                            /* JADX WARN: Type inference failed for: r43v0, types: [org.telegram.ui.ActionBar.BaseFragment] */
                            /* JADX WARN: Type inference failed for: r8v17 */
                            /* JADX WARN: Type inference failed for: r8v18 */
                            /* JADX WARN: Type inference failed for: r8v19, types: [int] */
                            /* JADX WARN: Type inference failed for: r8v20 */
                            /* JADX WARN: Type inference failed for: r8v21, types: [int] */
                            /* JADX WARN: Type inference failed for: r8v38 */
                            /* JADX WARN: Type inference failed for: r8v39 */
                            /* JADX WARN: Type inference failed for: r8v40 */
                            /* JADX WARN: Type inference failed for: r9v17 */
                            /* JADX WARN: Type inference failed for: r9v18, types: [int] */
                            /* JADX WARN: Type inference failed for: r9v26 */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public static void createDeleteMessagesAlert(final org.telegram.p035ui.ActionBar.BaseFragment r43, final org.telegram.tgnet.TLRPC.User r44, final org.telegram.tgnet.TLRPC.Chat r45, final org.telegram.tgnet.TLRPC.EncryptedChat r46, final org.telegram.tgnet.TLRPC.ChatFull r47, final long r48, final org.telegram.messenger.MessageObject r50, final android.util.SparseArray<org.telegram.messenger.MessageObject>[] r51, final org.telegram.messenger.MessageObject.GroupedMessages r52, final int r53, final int r54, org.telegram.tgnet.TLRPC.ChannelParticipant[] r55, final java.lang.Runnable r56, final java.lang.Runnable r57, final org.telegram.ui.ActionBar.Theme.ResourcesProvider r58) {
                                /*
                                    Method dump skipped, instruction units count: 2386
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.AlertsCreator.createDeleteMessagesAlert(org.telegram.ui.ActionBar.BaseFragment, org.telegram.tgnet.TLRPC$User, org.telegram.tgnet.TLRPC$Chat, org.telegram.tgnet.TLRPC$EncryptedChat, org.telegram.tgnet.TLRPC$ChatFull, long, org.telegram.messenger.MessageObject, android.util.SparseArray[], org.telegram.messenger.MessageObject$GroupedMessages, int, int, org.telegram.tgnet.TLRPC$ChannelParticipant[], java.lang.Runnable, java.lang.Runnable, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
                            }

                            public static /* synthetic */ void $r8$lambda$hMQT0CS_RdlxYwTJUGKgL3bZa7o(MessageObject.GroupedMessages groupedMessages, int i, BaseFragment baseFragment, int i2, int i3, MessageObject messageObject, AlertDialog alertDialog, int i4) {
                                if (groupedMessages != null && !groupedMessages.messages.isEmpty()) {
                                    SendMessagesHelper.getInstance(i).editMessage(groupedMessages.messages.get(0), null, false, baseFragment, null, i2 + i3, i3);
                                } else {
                                    SendMessagesHelper.getInstance(i).editMessage(messageObject, null, false, baseFragment, null, i2 + i3, i3);
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$MO0rnplReLc2-KGIOEVccXut3b8 */
                            public static /* synthetic */ void m9959$r8$lambda$MO0rnplReLc2KGIOEVccXut3b8(long j, boolean z, int i, MessageObject messageObject, MessageObject.GroupedMessages groupedMessages, TLRPC.EncryptedChat encryptedChat, long j2, int i2, int i3, SparseArray[] sparseArrayArr, Runnable runnable, AlertDialog alertDialog, int i4) {
                                ArrayList<Long> arrayList;
                                TLRPC.Peer peer;
                                long clientUserId = z ? UserConfig.getInstance(i).getClientUserId() : j;
                                ArrayList<Long> arrayList2 = null;
                                long j3 = 0;
                                if (messageObject != null) {
                                    ArrayList<Integer> arrayList3 = new ArrayList<>();
                                    if (groupedMessages != null) {
                                        for (int i5 = 0; i5 < groupedMessages.messages.size(); i5++) {
                                            MessageObject messageObject2 = groupedMessages.messages.get(i5);
                                            arrayList3.add(Integer.valueOf(messageObject2.getId()));
                                            if (encryptedChat != null && messageObject2.messageOwner.random_id != 0 && messageObject2.type != 10) {
                                                if (arrayList2 == null) {
                                                    arrayList2 = new ArrayList<>();
                                                }
                                                arrayList2.add(Long.valueOf(messageObject2.messageOwner.random_id));
                                            }
                                        }
                                    } else {
                                        arrayList3.add(Integer.valueOf(messageObject.getId()));
                                        if (encryptedChat != null && messageObject.messageOwner.random_id != 0 && messageObject.type != 10) {
                                            arrayList2 = new ArrayList<>();
                                            arrayList2.add(Long.valueOf(messageObject.messageOwner.random_id));
                                        }
                                    }
                                    MessagesController.getInstance(i).deleteMessages(arrayList3, arrayList2, encryptedChat, (j2 == 0 || (peer = messageObject.messageOwner.peer_id) == null || peer.chat_id != (-j2)) ? clientUserId : j2, i2, true, i3);
                                } else {
                                    int i6 = 1;
                                    while (i6 >= 0) {
                                        ArrayList<Integer> arrayList4 = new ArrayList<>();
                                        for (int i7 = 0; i7 < sparseArrayArr[i6].size(); i7++) {
                                            arrayList4.add(Integer.valueOf(sparseArrayArr[i6].keyAt(i7)));
                                        }
                                        if (encryptedChat != null) {
                                            ArrayList<Long> arrayList5 = new ArrayList<>();
                                            int i8 = 0;
                                            while (i8 < sparseArrayArr[i6].size()) {
                                                MessageObject messageObject3 = (MessageObject) sparseArrayArr[i6].valueAt(i8);
                                                long j4 = j3;
                                                long j5 = messageObject3.messageOwner.random_id;
                                                if (j5 != j4 && messageObject3.type != 10) {
                                                    arrayList5.add(Long.valueOf(j5));
                                                }
                                                i8++;
                                                j3 = j4;
                                            }
                                            arrayList = arrayList5;
                                        } else {
                                            arrayList = null;
                                        }
                                        long j6 = j3;
                                        MessagesController.getInstance(i).deleteMessages(arrayList4, arrayList, encryptedChat, (i6 != 1 || j2 == j6) ? clientUserId : j2, i2, true, i3);
                                        sparseArrayArr[i6].clear();
                                        i6--;
                                        j3 = j6;
                                    }
                                }
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$-m2f81oEYrVI3JlKJGpVN0qmKUk */
                            public static /* synthetic */ void m9932$r8$lambda$m2f81oEYrVI3JlKJGpVN0qmKUk(Runnable runnable, DialogInterface dialogInterface) {
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$50d03PVFVJwpfTf-rOOU3f0D5oQ */
                            public static /* synthetic */ TLObject m9939$r8$lambda$50d03PVFVJwpfTfrOOU3f0D5oQ(int i, long j) {
                                if (j > 0) {
                                    return MessagesController.getInstance(i).getUser(Long.valueOf(j));
                                }
                                return MessagesController.getInstance(i).getChat(Long.valueOf(-j));
                            }

                            public static /* synthetic */ boolean $r8$lambda$hpRKH2toyVjwxIyyioD2XRqR21U(long j, TLObject tLObject) {
                                if (tLObject instanceof TLRPC.User) {
                                    return ((TLRPC.User) tLObject).f1407id != j;
                                }
                                if (tLObject instanceof TLRPC.Chat) {
                                    return !ChatObject.hasAdminRights((TLRPC.Chat) tLObject);
                                }
                                return false;
                            }

                            /* JADX INFO: renamed from: $r8$lambda$eNEnxorrMc-DedDjvVEOR6O2cik */
                            public static /* synthetic */ void m9986$r8$lambda$eNEnxorrMcDedDjvVEOR6O2cik(int[] iArr, int[] iArr2, int i, TLObject tLObject, TLRPC.ChannelParticipant[] channelParticipantArr, int i2, AlertDialog[] alertDialogArr, BaseFragment baseFragment, TLRPC.User user, TLRPC.Chat chat, TLRPC.EncryptedChat encryptedChat, TLRPC.ChatFull chatFull, long j, MessageObject messageObject, SparseArray[] sparseArrayArr, MessageObject.GroupedMessages groupedMessages, int i3, int i4, Runnable runnable, Runnable runnable2, Theme.ResourcesProvider resourcesProvider) {
                                iArr[0] = iArr[0] + 1;
                                iArr2[i] = 0;
                                if (tLObject != null) {
                                    channelParticipantArr[i] = ((TLRPC.TL_channels_channelParticipant) tLObject).participant;
                                }
                                if (iArr[0] == i2) {
                                    try {
                                        alertDialogArr[0].dismiss();
                                    } catch (Throwable unused) {
                                    }
                                    alertDialogArr[0] = null;
                                    createDeleteMessagesAlert(baseFragment, user, chat, encryptedChat, chatFull, j, messageObject, sparseArrayArr, groupedMessages, i3, i4, channelParticipantArr, runnable, runnable2, resourcesProvider);
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$Gfm6RMR2kv9xu5XKwtGcLyTIf78(AlertDialog[] alertDialogArr, final int[] iArr, final int i, final Runnable runnable, BaseFragment baseFragment) {
                                AlertDialog alertDialog = alertDialogArr[0];
                                if (alertDialog == null) {
                                    return;
                                }
                                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda197
                                    @Override // android.content.DialogInterface.OnCancelListener
                                    public final void onCancel(DialogInterface dialogInterface) {
                                        AlertsCreator.$r8$lambda$Khvy2O8CSUZ7RNkcHUhrljTfhPg(iArr, i, runnable, dialogInterface);
                                    }
                                });
                                baseFragment.showDialog(alertDialogArr[0]);
                            }

                            public static /* synthetic */ void $r8$lambda$Khvy2O8CSUZ7RNkcHUhrljTfhPg(int[] iArr, int i, Runnable runnable, DialogInterface dialogInterface) {
                                for (int i2 : iArr) {
                                    if (i2 != 0) {
                                        ConnectionsManager.getInstance(i).cancelRequest(i2, true);
                                    }
                                }
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$kbgx7-kFYJqcwpgFkA8zr3Y_hjw */
                            public static /* synthetic */ void m9996$r8$lambda$kbgx7kFYJqcwpgFkA8zr3Y_hjw(boolean[] zArr, View view) {
                                boolean z = !zArr[0];
                                zArr[0] = z;
                                ((CheckBoxCell) view).setChecked(z, true);
                            }

                            public static /* synthetic */ void $r8$lambda$bGkuT195hlVEtVuPZaYzY2o_1oI(boolean[] zArr, View view) {
                                boolean z = !zArr[0];
                                zArr[0] = z;
                                ((CheckBoxCell) view).setChecked(z, true);
                            }

                            /* JADX INFO: renamed from: $r8$lambda$49VA2U1NeUd0CG7QK-1ZL_Kd4_E */
                            public static /* synthetic */ void m9938$r8$lambda$49VA2U1NeUd0CG7QK1ZL_Kd4_E(long j, boolean z, int i, MessageObject messageObject, MessageObject.GroupedMessages groupedMessages, TLRPC.EncryptedChat encryptedChat, long j2, int i2, boolean[] zArr, int i3, SparseArray[] sparseArrayArr, Runnable runnable, AlertDialog alertDialog, int i4) {
                                ArrayList<Long> arrayList;
                                TLRPC.Peer peer;
                                long clientUserId = z ? UserConfig.getInstance(i).getClientUserId() : j;
                                ArrayList<Long> arrayList2 = null;
                                long j3 = 0;
                                if (messageObject != null) {
                                    ArrayList<Integer> arrayList3 = new ArrayList<>();
                                    if (groupedMessages != null) {
                                        for (int i5 = 0; i5 < groupedMessages.messages.size(); i5++) {
                                            MessageObject messageObject2 = groupedMessages.messages.get(i5);
                                            arrayList3.add(Integer.valueOf(messageObject2.getId()));
                                            if (encryptedChat != null && messageObject2.messageOwner.random_id != 0 && messageObject2.type != 10) {
                                                if (arrayList2 == null) {
                                                    arrayList2 = new ArrayList<>();
                                                }
                                                arrayList2.add(Long.valueOf(messageObject2.messageOwner.random_id));
                                            }
                                        }
                                    } else {
                                        arrayList3.add(Integer.valueOf(messageObject.getId()));
                                        if (encryptedChat != null && messageObject.messageOwner.random_id != 0 && messageObject.type != 10) {
                                            arrayList2 = new ArrayList<>();
                                            arrayList2.add(Long.valueOf(messageObject.messageOwner.random_id));
                                        }
                                    }
                                    MessagesController.getInstance(i).deleteMessages(arrayList3, arrayList2, encryptedChat, (j2 == 0 || (peer = messageObject.messageOwner.peer_id) == null || peer.chat_id != (-j2)) ? clientUserId : j2, i2, zArr[0], i3);
                                } else {
                                    int i6 = 1;
                                    while (i6 >= 0) {
                                        ArrayList<Integer> arrayList4 = new ArrayList<>();
                                        for (int i7 = 0; i7 < sparseArrayArr[i6].size(); i7++) {
                                            arrayList4.add(Integer.valueOf(sparseArrayArr[i6].keyAt(i7)));
                                        }
                                        if (encryptedChat != null) {
                                            ArrayList<Long> arrayList5 = new ArrayList<>();
                                            int i8 = 0;
                                            while (i8 < sparseArrayArr[i6].size()) {
                                                MessageObject messageObject3 = (MessageObject) sparseArrayArr[i6].valueAt(i8);
                                                long j4 = j3;
                                                long j5 = messageObject3.messageOwner.random_id;
                                                if (j5 != j4 && messageObject3.type != 10) {
                                                    arrayList5.add(Long.valueOf(j5));
                                                }
                                                i8++;
                                                j3 = j4;
                                            }
                                            arrayList = arrayList5;
                                        } else {
                                            arrayList = null;
                                        }
                                        long j6 = j3;
                                        MessagesController.getInstance(i).deleteMessages(arrayList4, arrayList, encryptedChat, (i6 != 1 || j2 == j6) ? clientUserId : j2, i2, zArr[0], i3);
                                        sparseArrayArr[i6].clear();
                                        i6--;
                                        j3 = j6;
                                    }
                                }
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$shbnAatIWjRTJVdhllbfQqdlhaE(Runnable runnable, DialogInterface dialogInterface) {
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            public static void createThemeCreateDialog(final BaseFragment baseFragment, int i, final Theme.ThemeInfo themeInfo, final Theme.ThemeAccent themeAccent) {
                                if (baseFragment == null || baseFragment.getParentActivity() == null) {
                                    return;
                                }
                                Activity parentActivity = baseFragment.getParentActivity();
                                final EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(parentActivity);
                                editTextBoldCursor.setBackground(null);
                                editTextBoldCursor.setLineColors(Theme.getColor(Theme.key_dialogInputField), Theme.getColor(Theme.key_dialogInputFieldActivated), Theme.getColor(Theme.key_text_RedBold));
                                AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                                builder.setTitle(LocaleController.getString(C2797R.string.NewTheme));
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Create), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda121
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i2) {
                                        AlertsCreator.$r8$lambda$_r3RcvTconMV7D20v6ZBT5fPT8k(alertDialog, i2);
                                    }
                                });
                                LinearLayout linearLayout = new LinearLayout(parentActivity);
                                linearLayout.setOrientation(1);
                                builder.setView(linearLayout);
                                TextView textView = new TextView(parentActivity);
                                if (i != 0) {
                                    textView.setText(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.EnterThemeNameEdit)));
                                } else {
                                    textView.setText(LocaleController.getString(C2797R.string.EnterThemeName));
                                }
                                textView.setTextSize(1, 16.0f);
                                textView.setPadding(AndroidUtilities.m1036dp(23.0f), AndroidUtilities.m1036dp(12.0f), AndroidUtilities.m1036dp(23.0f), AndroidUtilities.m1036dp(6.0f));
                                int i2 = Theme.key_dialogTextBlack;
                                textView.setTextColor(Theme.getColor(i2));
                                linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2));
                                editTextBoldCursor.setTextSize(1, 16.0f);
                                editTextBoldCursor.setTextColor(Theme.getColor(i2));
                                editTextBoldCursor.setMaxLines(1);
                                editTextBoldCursor.setLines(1);
                                editTextBoldCursor.setInputType(16385);
                                editTextBoldCursor.setGravity(51);
                                editTextBoldCursor.setSingleLine(true);
                                editTextBoldCursor.setImeOptions(6);
                                editTextBoldCursor.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                                editTextBoldCursor.setCursorSize(AndroidUtilities.m1036dp(20.0f));
                                editTextBoldCursor.setCursorWidth(1.5f);
                                editTextBoldCursor.setPadding(0, AndroidUtilities.m1036dp(4.0f), 0, 0);
                                linearLayout.addView(editTextBoldCursor, LayoutHelper.createLinear(-1, 36, 51, 24, 6, 24, 0));
                                editTextBoldCursor.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda122
                                    @Override // android.widget.TextView.OnEditorActionListener
                                    public final boolean onEditorAction(TextView textView2, int i3, KeyEvent keyEvent) {
                                        return AlertsCreator.$r8$lambda$NoSunofCr_9GnwXXstYARvp3pLQ(textView2, i3, keyEvent);
                                    }
                                });
                                editTextBoldCursor.setText(generateThemeName(themeAccent));
                                editTextBoldCursor.setSelection(editTextBoldCursor.length());
                                final AlertDialog alertDialogCreate = builder.create();
                                alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda123
                                    @Override // android.content.DialogInterface.OnShowListener
                                    public final void onShow(DialogInterface dialogInterface) {
                                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda194
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                AlertsCreator.$r8$lambda$Mf3yD52gvDlbJXbrPusGFwBelYA(editTextBoldCursor);
                                            }
                                        });
                                    }
                                });
                                baseFragment.showDialog(alertDialogCreate);
                                editTextBoldCursor.requestFocus();
                                alertDialogCreate.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda124
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) throws Throwable {
                                        AlertsCreator.m9969$r8$lambda$QKtMfAVTeEyfiIdaREtYzDIn4Q(baseFragment, editTextBoldCursor, themeAccent, themeInfo, alertDialogCreate, view);
                                    }
                                });
                            }

                            public static /* synthetic */ boolean $r8$lambda$NoSunofCr_9GnwXXstYARvp3pLQ(TextView textView, int i, KeyEvent keyEvent) {
                                AndroidUtilities.hideKeyboard(textView);
                                return false;
                            }

                            public static /* synthetic */ void $r8$lambda$Mf3yD52gvDlbJXbrPusGFwBelYA(EditTextBoldCursor editTextBoldCursor) {
                                editTextBoldCursor.requestFocus();
                                AndroidUtilities.showKeyboard(editTextBoldCursor);
                            }

                            /* JADX INFO: renamed from: $r8$lambda$QKtMfAVTeEyfiIdaREtYzDIn-4Q */
                            public static /* synthetic */ void m9969$r8$lambda$QKtMfAVTeEyfiIdaREtYzDIn4Q(final BaseFragment baseFragment, final EditTextBoldCursor editTextBoldCursor, Theme.ThemeAccent themeAccent, Theme.ThemeInfo themeInfo, final AlertDialog alertDialog, View view) throws Throwable {
                                if (baseFragment.getParentActivity() == null) {
                                    return;
                                }
                                if (editTextBoldCursor.length() == 0) {
                                    editTextBoldCursor.performHapticFeedback(3, 2);
                                    AndroidUtilities.shakeView(editTextBoldCursor);
                                    return;
                                }
                                if (baseFragment instanceof ThemePreviewActivity) {
                                    Theme.applyPreviousTheme();
                                    baseFragment.finishFragment();
                                }
                                if (themeAccent != null) {
                                    themeInfo.setCurrentAccentId(themeAccent.f1479id);
                                    Theme.refreshThemeColors();
                                    Utilities.searchQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda165
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda206
                                                @Override // java.lang.Runnable
                                                public final void run() throws Throwable {
                                                    AlertsCreator.processCreate(editTextBoldCursor, alertDialog, baseFragment);
                                                }
                                            });
                                        }
                                    });
                                    return;
                                }
                                processCreate(editTextBoldCursor, alertDialog, baseFragment);
                            }

                            public static void processCreate(EditTextBoldCursor editTextBoldCursor, AlertDialog alertDialog, BaseFragment baseFragment) throws Throwable {
                                if (baseFragment == null || baseFragment.getParentActivity() == null) {
                                    return;
                                }
                                AndroidUtilities.hideKeyboard(editTextBoldCursor);
                                Theme.ThemeInfo themeInfoCreateNewTheme = Theme.createNewTheme(editTextBoldCursor.getText().toString());
                                NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.themeListUpdated, new Object[0]);
                                new ThemeEditorView().show(baseFragment.getParentActivity(), themeInfoCreateNewTheme);
                                alertDialog.dismiss();
                                SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
                                if (globalMainSettings.getBoolean("themehint", false)) {
                                    return;
                                }
                                globalMainSettings.edit().putBoolean("themehint", true).apply();
                                try {
                                    Toast.makeText(baseFragment.getParentActivity(), LocaleController.getString(C2797R.string.CreateNewThemeHelp), 1).show();
                                } catch (Exception e) {
                                    FileLog.m1048e(e);
                                }
                            }

                            private static String generateThemeName(Theme.ThemeAccent themeAccent) {
                                int i;
                                List listAsList = Arrays.asList("Ancient", "Antique", "Autumn", "Baby", "Barely", "Baroque", "Blazing", "Blushing", "Bohemian", "Bubbly", "Burning", "Buttered", "Classic", "Clear", "Cool", "Cosmic", "Cotton", "Cozy", "Crystal", "Dark", "Daring", "Darling", "Dawn", "Dazzling", "Deep", "Deepest", "Delicate", "Delightful", "Divine", "Double", "Downtown", "Dreamy", "Dusky", "Dusty", "Electric", "Enchanted", "Endless", "Evening", "Fantastic", "Flirty", "Forever", "Frigid", "Frosty", "Frozen", "Gentle", "Heavenly", "Hyper", "Icy", "Infinite", "Innocent", "Instant", "Luscious", "Lunar", "Lustrous", "Magic", "Majestic", "Mambo", "Midnight", "Millenium", "Morning", "Mystic", "Natural", "Neon", "Night", "Opaque", "Paradise", "Perfect", "Perky", "Polished", "Powerful", "Rich", "Royal", "Sheer", "Simply", "Sizzling", "Solar", "Sparkling", "Splendid", "Spicy", "Spring", "Stellar", "Sugared", "Summer", "Sunny", "Super", "Sweet", "Tender", "Tenacious", "Tidal", "Toasted", "Totally", "Tranquil", "Tropical", "True", "Twilight", "Twinkling", "Ultimate", "Ultra", "Velvety", "Vibrant", "Vintage", "Virtual", "Warm", "Warmest", "Whipped", "Wild", "Winsome");
                                List listAsList2 = Arrays.asList("Ambrosia", "Attack", "Avalanche", "Blast", "Bliss", "Blossom", "Blush", "Burst", "Butter", "Candy", "Carnival", "Charm", "Chiffon", "Cloud", "Comet", "Delight", "Dream", "Dust", "Fantasy", "Flame", "Flash", "Fire", "Freeze", "Frost", "Glade", "Glaze", "Gleam", "Glimmer", "Glitter", "Glow", "Grande", "Haze", "Highlight", "Ice", "Illusion", "Intrigue", "Jewel", "Jubilee", "Kiss", "Lights", "Lollypop", "Love", "Luster", "Madness", "Matte", "Mirage", "Mist", "Moon", "Muse", "Myth", "Nectar", "Nova", "Parfait", "Passion", "Pop", "Rain", "Reflection", "Rhapsody", "Romance", "Satin", "Sensation", "Silk", "Shine", "Shadow", "Shimmer", "Sky", "Spice", "Star", "Sugar", "Sunrise", "Sunset", "Sun", "Twist", "Unbound", "Velvet", "Vibrant", "Waters", "Wine", "Wink", "Wonder", "Zone");
                                HashMap map = new HashMap();
                                map.put(9306112, "Berry");
                                map.put(14598550, "Brandy");
                                map.put(8391495, "Cherry");
                                map.put(16744272, "Coral");
                                map.put(14372985, "Cranberry");
                                map.put(14423100, "Crimson");
                                map.put(14725375, "Mauve");
                                map.put(16761035, "Pink");
                                map.put(16711680, "Red");
                                map.put(16711807, "Rose");
                                map.put(8406555, "Russet");
                                map.put(16720896, "Scarlet");
                                map.put(15856113, "Seashell");
                                map.put(16724889, "Strawberry");
                                map.put(16760576, "Amber");
                                map.put(15438707, "Apricot");
                                map.put(16508850, "Banana");
                                map.put(10601738, "Citrus");
                                map.put(11560192, "Ginger");
                                map.put(16766720, "Gold");
                                map.put(16640272, "Lemon");
                                map.put(16753920, "Orange");
                                map.put(16770484, "Peach");
                                map.put(16739155, "Persimmon");
                                map.put(14996514, "Sunflower");
                                map.put(15893760, "Tangerine");
                                map.put(16763004, "Topaz");
                                map.put(16776960, "Yellow");
                                map.put(3688720, "Clover");
                                map.put(8628829, "Cucumber");
                                map.put(5294200, "Emerald");
                                map.put(11907932, "Olive");
                                map.put(65280, "Green");
                                map.put(43115, "Jade");
                                map.put(2730887, "Jungle");
                                map.put(12582656, "Lime");
                                map.put(776785, "Malachite");
                                map.put(10026904, "Mint");
                                map.put(11394989, "Moss");
                                map.put(3234721, "Azure");
                                map.put(255, "Blue");
                                map.put(18347, "Cobalt");
                                map.put(5204422, "Indigo");
                                map.put(96647, "Lagoon");
                                map.put(7461346, "Aquamarine");
                                map.put(1182351, "Ultramarine");
                                map.put(128, "Navy");
                                map.put(3101086, "Sapphire");
                                map.put(7788522, "Sky");
                                map.put(32896, "Teal");
                                map.put(4251856, "Turquoise");
                                map.put(10053324, "Amethyst");
                                map.put(5046581, "Blackberry");
                                map.put(6373457, "Eggplant");
                                map.put(13148872, "Lilac");
                                map.put(11894492, "Lavender");
                                map.put(13421823, "Periwinkle");
                                map.put(8663417, "Plum");
                                map.put(6684825, "Purple");
                                map.put(14204888, "Thistle");
                                map.put(14315734, "Orchid");
                                map.put(2361920, "Violet");
                                map.put(4137225, "Bronze");
                                map.put(3604994, "Chocolate");
                                map.put(8077056, "Cinnamon");
                                map.put(3153694, "Cocoa");
                                map.put(7365973, "Coffee");
                                map.put(7956873, "Rum");
                                map.put(5113350, "Mahogany");
                                map.put(7875865, "Mocha");
                                map.put(12759680, "Sand");
                                map.put(8924439, "Sienna");
                                map.put(7864585, "Maple");
                                map.put(15787660, "Khaki");
                                map.put(12088115, "Copper");
                                map.put(12144200, "Chestnut");
                                map.put(15653316, "Almond");
                                map.put(16776656, "Cream");
                                map.put(12186367, "Diamond");
                                map.put(11109127, "Honey");
                                map.put(16777200, "Ivory");
                                map.put(15392968, "Pearl");
                                map.put(15725299, "Porcelain");
                                map.put(13745832, "Vanilla");
                                map.put(16777215, "White");
                                map.put(8421504, "Gray");
                                map.put(0, "Black");
                                map.put(15266260, "Chrome");
                                map.put(3556687, "Charcoal");
                                map.put(789277, "Ebony");
                                map.put(12632256, "Silver");
                                map.put(16119285, "Smoke");
                                map.put(2499381, "Steel");
                                map.put(5220413, "Apple");
                                map.put(8434628, "Glacier");
                                map.put(16693933, "Melon");
                                map.put(12929932, "Mulberry");
                                map.put(11126466, "Opal");
                                map.put(5547512, "Blue");
                                Theme.ThemeAccent accent = themeAccent == null ? Theme.getCurrentTheme().getAccent(false) : themeAccent;
                                if (accent == null || (i = accent.accentColor) == 0) {
                                    i = AndroidUtilities.calcDrawableColor(Theme.getCachedWallpaper())[0];
                                }
                                int iRed = Color.red(i);
                                int iGreen = Color.green(i);
                                int iBlue = Color.blue(i);
                                String str = null;
                                int i2 = Integer.MAX_VALUE;
                                for (Map.Entry entry : map.entrySet()) {
                                    Integer num = (Integer) entry.getKey();
                                    int iRed2 = Color.red(num.intValue());
                                    int i3 = (iRed + iRed2) / 2;
                                    int i4 = iRed - iRed2;
                                    int iGreen2 = iGreen - Color.green(num.intValue());
                                    int iBlue2 = iBlue - Color.blue(num.intValue());
                                    int i5 = ((((i3 + 512) * i4) * i4) >> 8) + (iGreen2 * 4 * iGreen2) + ((((767 - i3) * iBlue2) * iBlue2) >> 8);
                                    if (i5 < i2) {
                                        str = (String) entry.getValue();
                                        i2 = i5;
                                    }
                                }
                                if (Utilities.random.nextInt() % 2 == 0) {
                                    return ((String) listAsList.get(Utilities.random.nextInt(listAsList.size()))) + " " + str;
                                }
                                return str + " " + ((String) listAsList2.get(Utilities.random.nextInt(listAsList2.size())));
                            }

                            public static void showDeclineSuggestedPostDialog(BaseFragment baseFragment, long j, boolean z, final Utilities.Callback<String> callback) {
                                Context context = baseFragment.getContext();
                                AlertDialog.Builder builder = z ? new AlertDialogDecor.Builder(context) : new AlertDialog.Builder(context);
                                builder.setTitle(LocaleController.getString(C2797R.string.SuggestedMessageDecline));
                                builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.SuggestedMessageDeclineInfo, MessagesController.getInstance(UserConfig.selectedAccount).getPeerName(j))));
                                FrameLayout frameLayout = new FrameLayout(context);
                                frameLayout.setClipChildren(false);
                                final EditText editText = new EditText(context);
                                editText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                                editText.setHint(LocaleController.getString(C2797R.string.SuggestedMessageDeclineReasonHint));
                                editText.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
                                editText.setTextSize(1, 16.0f);
                                editText.setBackground(Theme.createEditTextDrawable(context, true));
                                editText.setMaxLines(4);
                                editText.setRawInputType(147457);
                                editText.setImeOptions(6);
                                editText.setFilters(new InputFilter[]{new CodepointsLengthInputFilter(255) { // from class: org.telegram.ui.Components.AlertsCreator.64
                                    final /* synthetic */ Context val$context;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C377264(int i, Context context2) {
                                        super(i);
                                        context = context2;
                                    }

                                    @Override // org.telegram.p035ui.Components.CodepointsLengthInputFilter, android.text.InputFilter
                                    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
                                        Vibrator vibrator;
                                        CharSequence charSequenceFilter = super.filter(charSequence, i, i2, spanned, i3, i4);
                                        if (charSequenceFilter != null && charSequence != null && charSequenceFilter.length() != charSequence.length() && (vibrator = (Vibrator) context.getSystemService("vibrator")) != null) {
                                            vibrator.vibrate(200L);
                                        }
                                        return charSequenceFilter;
                                    }
                                }});
                                editText.setPadding(LocaleController.isRTL ? AndroidUtilities.m1036dp(24.0f) : 0, AndroidUtilities.m1036dp(8.0f), LocaleController.isRTL ? 0 : AndroidUtilities.m1036dp(24.0f), AndroidUtilities.m1036dp(8.0f));
                                editText.setSelection(editText.getText().toString().length());
                                builder.setView(frameLayout);
                                builder.setPositiveButton(LocaleController.getString(C2797R.string.Decline), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda190
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i) {
                                        AlertsCreator.$r8$lambda$z1MZBzZkPDURI31kp_2PuqmM3X0(callback, editText, alertDialog, i);
                                    }
                                });
                                builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null);
                                builder.setOnPreDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda191
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        AndroidUtilities.hideKeyboard(editText);
                                    }
                                });
                                frameLayout.addView(editText, LayoutHelper.createFrame(-1, -2.0f, 0, 23.0f, 0.0f, 23.0f, 21.0f));
                                editText.requestFocus();
                                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda192
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AndroidUtilities.showKeyboard(editText);
                                    }
                                }, 100L);
                                AlertDialog alertDialogCreate = builder.create();
                                baseFragment.showDialog(alertDialogCreate);
                                TextView textView = (TextView) alertDialogCreate.getButton(-1);
                                if (textView != null) {
                                    textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$64 */
                            public class C377264 extends CodepointsLengthInputFilter {
                                final /* synthetic */ Context val$context;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C377264(int i, Context context2) {
                                    super(i);
                                    context = context2;
                                }

                                @Override // org.telegram.p035ui.Components.CodepointsLengthInputFilter, android.text.InputFilter
                                public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
                                    Vibrator vibrator;
                                    CharSequence charSequenceFilter = super.filter(charSequence, i, i2, spanned, i3, i4);
                                    if (charSequenceFilter != null && charSequence != null && charSequenceFilter.length() != charSequence.length() && (vibrator = (Vibrator) context.getSystemService("vibrator")) != null) {
                                        vibrator.vibrate(200L);
                                    }
                                    return charSequenceFilter;
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$z1MZBzZkPDURI31kp_2PuqmM3X0(Utilities.Callback callback, EditText editText, AlertDialog alertDialog, int i) {
                                alertDialog.dismiss();
                                if (callback != null) {
                                    callback.run(editText.getText().toString());
                                }
                            }

                            public static BottomSheet.Builder createSuggestedMessageDatePickerDialog(Context context, long j, ScheduleDatePickerDelegate scheduleDatePickerDelegate, Theme.ResourcesProvider resourcesProvider, int i) {
                                return createSuggestedMessageDatePickerDialog(context, j, scheduleDatePickerDelegate, null, new ScheduleDatePickerColors(), resourcesProvider, i);
                            }

                            public static BottomSheet.Builder createSuggestedMessageDatePickerDialog(Context context, long j, final ScheduleDatePickerDelegate scheduleDatePickerDelegate, final Runnable runnable, ScheduleDatePickerColors scheduleDatePickerColors, Theme.ResourcesProvider resourcesProvider, int i) {
                                long j2;
                                Calendar calendar;
                                if (context == null) {
                                    return null;
                                }
                                final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
                                builder.setApplyBottomPadding(false);
                                final NumberPicker numberPicker = new NumberPicker(context, resourcesProvider);
                                numberPicker.setTextColor(scheduleDatePickerColors.textColor);
                                numberPicker.setTextOffset(AndroidUtilities.m1036dp(10.0f));
                                numberPicker.setItemCount(5);
                                final C377365 c377365 = new NumberPicker(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.65
                                    public C377365(Context context2, Theme.ResourcesProvider resourcesProvider2) {
                                        super(context2, resourcesProvider2);
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i2) {
                                        return LocaleController.formatPluralString("Hours", i2, new Object[0]);
                                    }
                                };
                                c377365.setWrapSelectorWheel(true);
                                c377365.setAllItemsCount(24);
                                c377365.setItemCount(5);
                                c377365.setTextColor(scheduleDatePickerColors.textColor);
                                c377365.setTextOffset(-AndroidUtilities.m1036dp(10.0f));
                                final C377466 c377466 = new NumberPicker(context2, resourcesProvider2) { // from class: org.telegram.ui.Components.AlertsCreator.66
                                    public C377466(Context context2, Theme.ResourcesProvider resourcesProvider2) {
                                        super(context2, resourcesProvider2);
                                    }

                                    @Override // org.telegram.p035ui.Components.NumberPicker
                                    public CharSequence getContentDescription(int i2) {
                                        return LocaleController.formatPluralString("Minutes", i2, new Object[0]);
                                    }
                                };
                                c377466.setWrapSelectorWheel(true);
                                c377466.setAllItemsCount(60);
                                c377466.setItemCount(5);
                                c377466.setTextColor(scheduleDatePickerColors.textColor);
                                c377466.setTextOffset(-AndroidUtilities.m1036dp(34.0f));
                                C377567 c377567 = new LinearLayout(context2) { // from class: org.telegram.ui.Components.AlertsCreator.67
                                    boolean ignoreLayout = false;
                                    final /* synthetic */ NumberPicker val$dayPicker;
                                    final /* synthetic */ NumberPicker val$hourPicker;
                                    final /* synthetic */ NumberPicker val$minutePicker;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C377567(Context context2, final NumberPicker numberPicker2, final NumberPicker c3773652, final NumberPicker c3774662) {
                                        super(context2);
                                        numberPicker = numberPicker2;
                                        numberPicker = c3773652;
                                        numberPicker = c3774662;
                                        this.ignoreLayout = false;
                                    }

                                    @Override // android.widget.LinearLayout, android.view.View
                                    public void onMeasure(int i2, int i3) {
                                        this.ignoreLayout = true;
                                        Point point = AndroidUtilities.displaySize;
                                        int i4 = point.x > point.y ? 3 : 5;
                                        numberPicker.setItemCount(i4);
                                        numberPicker.setItemCount(i4);
                                        numberPicker.setItemCount(i4);
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                        this.ignoreLayout = false;
                                        super.onMeasure(i2, i3);
                                    }

                                    @Override // android.view.View, android.view.ViewParent
                                    public void requestLayout() {
                                        if (this.ignoreLayout) {
                                            return;
                                        }
                                        super.requestLayout();
                                    }
                                };
                                c377567.setOrientation(1);
                                LinearLayout linearLayout = new LinearLayout(context2);
                                linearLayout.setOrientation(1);
                                c377567.addView(linearLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 22, 4));
                                TextView textView = new TextView(context2);
                                textView.setText(LocaleController.getString(i == 1 ? C2797R.string.SuggestedPostAcceptTitle : C2797R.string.PostSuggestionsAddTime));
                                textView.setTextColor(scheduleDatePickerColors.textColor);
                                textView.setTextSize(1, 20.0f);
                                textView.setTypeface(AndroidUtilities.bold());
                                linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, 51, 0, 12, 0, 0));
                                textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda211
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        return AlertsCreator.$r8$lambda$gYIk1AITF0ps4wFFU9fxFZgzP3c(view, motionEvent);
                                    }
                                });
                                TextView textView2 = new TextView(context2);
                                textView2.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2, resourcesProvider2));
                                textView2.setTextSize(1, 14.0f);
                                textView2.setText(LocaleController.getString(C2797R.string.PostSuggestionsAddTimeHint));
                                linearLayout.addView(textView2, LayoutHelper.createLinear(-2, -2, 51, 0, 2, 0, 0));
                                textView2.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda212
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        return AlertsCreator.$r8$lambda$1HreA47cic2DI0iWvJkZk6xPpyU(view, motionEvent);
                                    }
                                });
                                LinearLayout linearLayout2 = new LinearLayout(context2);
                                linearLayout2.setOrientation(0);
                                linearLayout2.setWeightSum(1.0f);
                                c377567.addView(linearLayout2, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
                                long jCurrentTimeMillis = System.currentTimeMillis();
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTimeInMillis(jCurrentTimeMillis);
                                final int i2 = calendar2.get(1);
                                AppGlobalConfig.ConfigTime configTime = MessagesController.getInstance(UserConfig.selectedAccount).config.starsSuggestedPostFutureMin;
                                TimeUnit timeUnit = TimeUnit.SECONDS;
                                final long j3 = configTime.get(timeUnit) * 2;
                                final long j4 = MessagesController.getInstance(UserConfig.selectedAccount).config.starsSuggestedPostFutureMax.get(timeUnit) - 86400;
                                final C377668 c377668 = new TextView(context2) { // from class: org.telegram.ui.Components.AlertsCreator.68
                                    public C377668(Context context2) {
                                        super(context2);
                                    }

                                    @Override // android.widget.TextView, android.view.View
                                    public CharSequence getAccessibilityClassName() {
                                        return Button.class.getName();
                                    }
                                };
                                linearLayout2.addView(numberPicker2, LayoutHelper.createLinear(0, 270, 0.5f));
                                numberPicker2.setMinValue(0);
                                numberPicker2.setMaxValue(365);
                                numberPicker2.setWrapSelectorWheel(false);
                                numberPicker2.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda213
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i3) {
                                        return AlertsCreator.m9995$r8$lambda$kRcsrOaKcYAxS2nZY24ZTyBooc(i2, i3);
                                    }
                                });
                                final int i3 = i == 1 ? 5 : 3;
                                NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda214
                                    @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                                    public final void onValueChange(NumberPicker numberPicker2, int i4, int i5) {
                                        AlertsCreator.checkScheduleDate(c377668, null, j3, j4, i3, numberPicker2, c3773652, c3774662);
                                    }
                                };
                                numberPicker2.setOnValueChangedListener(onValueChangeListener);
                                c3773652.setMinValue(0);
                                c3773652.setMaxValue(23);
                                linearLayout2.addView(c3773652, LayoutHelper.createLinear(0, 270, 0.2f));
                                c3773652.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda215
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i4) {
                                        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i4));
                                    }
                                });
                                c3773652.setOnValueChangedListener(onValueChangeListener);
                                c3774662.setMinValue(0);
                                c3774662.setMaxValue(59);
                                c3774662.setValue(0);
                                c3774662.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda216
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i4) {
                                        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i4));
                                    }
                                });
                                linearLayout2.addView(c3774662, LayoutHelper.createLinear(0, 270, 0.3f));
                                c3774662.setOnValueChangedListener(onValueChangeListener);
                                if (j <= 0 || j == 2147483646) {
                                    j2 = j3;
                                    calendar = calendar2;
                                } else {
                                    long j5 = 1000 * j;
                                    calendar = calendar2;
                                    calendar.setTimeInMillis(System.currentTimeMillis());
                                    calendar.set(12, 0);
                                    calendar.set(13, 0);
                                    calendar.set(14, 0);
                                    calendar.set(11, 0);
                                    int timeInMillis = (int) ((j5 - calendar.getTimeInMillis()) / DurationKt.MILLIS_IN_DAY);
                                    j2 = j3;
                                    calendar.setTimeInMillis(j5);
                                    if (timeInMillis >= 0) {
                                        c3774662.setValue(calendar.get(12));
                                        c3773652.setValue(calendar.get(11));
                                        numberPicker2.setValue(timeInMillis);
                                    }
                                }
                                final boolean[] zArr = {true};
                                final long j6 = j2;
                                checkScheduleDate(c377668, null, j6, j4, i3, numberPicker2, c3773652, c3774662);
                                c377668.setPadding(AndroidUtilities.m1036dp(34.0f), 0, AndroidUtilities.m1036dp(34.0f), 0);
                                c377668.setGravity(17);
                                c377668.setTextColor(scheduleDatePickerColors.buttonTextColor);
                                c377668.setTextSize(1, 14.0f);
                                c377668.setTypeface(AndroidUtilities.bold());
                                c377668.setBackground(Theme.AdaptiveRipple.filledRect(scheduleDatePickerColors.buttonBackgroundColor, 8.0f));
                                c377567.addView(c377668, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 4));
                                final Calendar calendar3 = calendar;
                                c377668.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda217
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.$r8$lambda$UslW02T3dCRO8e82nlRUSo3ET5g(zArr, j6, j4, i3, numberPicker2, c3773652, c3774662, calendar3, scheduleDatePickerDelegate, builder, view);
                                    }
                                });
                                ScaleStateListAnimator.apply(c377668, 0.02f, 1.2f);
                                C377769 c377769 = new TextView(context2) { // from class: org.telegram.ui.Components.AlertsCreator.69
                                    public C377769(Context context2) {
                                        super(context2);
                                    }

                                    @Override // android.widget.TextView, android.view.View
                                    public CharSequence getAccessibilityClassName() {
                                        return Button.class.getName();
                                    }
                                };
                                c377769.setPadding(AndroidUtilities.m1036dp(34.0f), 0, AndroidUtilities.m1036dp(34.0f), 0);
                                c377769.setGravity(17);
                                c377769.setText(LocaleController.getString(i == 1 ? C2797R.string.MessageSuggestionPublishNow : C2797R.string.PostSuggestionsAnytime));
                                c377769.setTextColor(scheduleDatePickerColors.buttonBackgroundColor);
                                c377769.setTextSize(1, 14.0f);
                                c377769.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(8.0f), Theme.getColor(Theme.key_windowBackgroundWhite), Theme.getColor(Theme.key_listSelector)));
                                c377567.addView(c377769, LayoutHelper.createLinear(-1, 48, 83, 16, 0, 16, 16));
                                c377769.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda218
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.$r8$lambda$f6_CdOStFOMsWjNuTm_dtMAhtU0(zArr, scheduleDatePickerDelegate, builder, view);
                                    }
                                });
                                ScaleStateListAnimator.apply(c377769, 0.02f, 1.2f);
                                builder.setCustomView(c377567);
                                BottomSheet bottomSheetShow = builder.show();
                                bottomSheetShow.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda219
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        AlertsCreator.m9980$r8$lambda$_uZXHpSghzjq7fwWAYiRCNqfU(runnable, zArr, dialogInterface);
                                    }
                                });
                                bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
                                bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
                                return builder;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$65 */
                            public class C377365 extends NumberPicker {
                                public C377365(Context context2, Theme.ResourcesProvider resourcesProvider2) {
                                    super(context2, resourcesProvider2);
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i2) {
                                    return LocaleController.formatPluralString("Hours", i2, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$66 */
                            public class C377466 extends NumberPicker {
                                public C377466(Context context2, Theme.ResourcesProvider resourcesProvider2) {
                                    super(context2, resourcesProvider2);
                                }

                                @Override // org.telegram.p035ui.Components.NumberPicker
                                public CharSequence getContentDescription(int i2) {
                                    return LocaleController.formatPluralString("Minutes", i2, new Object[0]);
                                }
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$67 */
                            public class C377567 extends LinearLayout {
                                boolean ignoreLayout = false;
                                final /* synthetic */ NumberPicker val$dayPicker;
                                final /* synthetic */ NumberPicker val$hourPicker;
                                final /* synthetic */ NumberPicker val$minutePicker;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C377567(Context context2, final NumberPicker numberPicker2, final NumberPicker c3773652, final NumberPicker c3774662) {
                                    super(context2);
                                    numberPicker = numberPicker2;
                                    numberPicker = c3773652;
                                    numberPicker = c3774662;
                                    this.ignoreLayout = false;
                                }

                                @Override // android.widget.LinearLayout, android.view.View
                                public void onMeasure(int i2, int i3) {
                                    this.ignoreLayout = true;
                                    Point point = AndroidUtilities.displaySize;
                                    int i4 = point.x > point.y ? 3 : 5;
                                    numberPicker.setItemCount(i4);
                                    numberPicker.setItemCount(i4);
                                    numberPicker.setItemCount(i4);
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * i4;
                                    this.ignoreLayout = false;
                                    super.onMeasure(i2, i3);
                                }

                                @Override // android.view.View, android.view.ViewParent
                                public void requestLayout() {
                                    if (this.ignoreLayout) {
                                        return;
                                    }
                                    super.requestLayout();
                                }
                            }

                            public static /* synthetic */ boolean $r8$lambda$gYIk1AITF0ps4wFFU9fxFZgzP3c(View view, MotionEvent motionEvent) {
                                return true;
                            }

                            public static /* synthetic */ boolean $r8$lambda$1HreA47cic2DI0iWvJkZk6xPpyU(View view, MotionEvent motionEvent) {
                                return true;
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$68 */
                            public class C377668 extends TextView {
                                public C377668(Context context2) {
                                    super(context2);
                                }

                                @Override // android.widget.TextView, android.view.View
                                public CharSequence getAccessibilityClassName() {
                                    return Button.class.getName();
                                }
                            }

                            /* JADX INFO: renamed from: $r8$lambda$kRcsrOaKcYAxS2-nZY24ZTyBooc */
                            public static /* synthetic */ String m9995$r8$lambda$kRcsrOaKcYAxS2nZY24ZTyBooc(int i, int i2) {
                                if (i2 == 0) {
                                    return LocaleController.getString(C2797R.string.MessageScheduleToday);
                                }
                                LocalDate localDatePlusDays = LocalDate.now().plusDays(i2);
                                int year = localDatePlusDays.getYear();
                                long epochMilli = localDatePlusDays.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
                                if (year == i) {
                                    return LocaleController.getInstance().getFormatterWeek().format(epochMilli) + ", " + LocaleController.getInstance().getFormatterScheduleDay().format(epochMilli);
                                }
                                return LocaleController.getInstance().getFormatterScheduleYear().format(epochMilli);
                            }

                            public static /* synthetic */ void $r8$lambda$UslW02T3dCRO8e82nlRUSo3ET5g(boolean[] zArr, long j, long j2, int i, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, Calendar calendar, ScheduleDatePickerDelegate scheduleDatePickerDelegate, BottomSheet.Builder builder, View view) {
                                zArr[0] = false;
                                boolean zCheckScheduleDate = checkScheduleDate(null, null, j, j2, i, numberPicker, numberPicker2, numberPicker3);
                                calendar.setTimeInMillis(LocalDate.now().plusDays(numberPicker.getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
                                calendar.set(11, numberPicker2.getValue());
                                calendar.set(12, numberPicker3.getValue());
                                if (zCheckScheduleDate) {
                                    calendar.set(13, 0);
                                }
                                scheduleDatePickerDelegate.didSelectDate(true, (int) (calendar.getTimeInMillis() / 1000), 0);
                                builder.getDismissRunnable().run();
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$69 */
                            public class C377769 extends TextView {
                                public C377769(Context context2) {
                                    super(context2);
                                }

                                @Override // android.widget.TextView, android.view.View
                                public CharSequence getAccessibilityClassName() {
                                    return Button.class.getName();
                                }
                            }

                            public static /* synthetic */ void $r8$lambda$f6_CdOStFOMsWjNuTm_dtMAhtU0(boolean[] zArr, ScheduleDatePickerDelegate scheduleDatePickerDelegate, BottomSheet.Builder builder, View view) {
                                zArr[0] = false;
                                scheduleDatePickerDelegate.didSelectDate(true, -1, 0);
                                builder.getDismissRunnable().run();
                            }

                            /* JADX INFO: renamed from: $r8$lambda$_uZXHpSghzjq7fwWAY-iRCN-qfU */
                            public static /* synthetic */ void m9980$r8$lambda$_uZXHpSghzjq7fwWAYiRCNqfU(Runnable runnable, boolean[] zArr, DialogInterface dialogInterface) {
                                if (runnable == null || !zArr[0]) {
                                    return;
                                }
                                runnable.run();
                            }

                            public static void showCallsForbidden(Context context, final int i, final long j, final Theme.ResourcesProvider resourcesProvider) {
                                BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
                                LinearLayout linearLayout = new LinearLayout(context);
                                linearLayout.setOrientation(1);
                                linearLayout.setPadding(AndroidUtilities.m1036dp(16.0f), 0, AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(8.0f));
                                FrameLayout frameLayout = new FrameLayout(context);
                                frameLayout.setClipChildren(false);
                                frameLayout.setClipToPadding(false);
                                linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, 92, 17, 0, 0, 0, 0));
                                FrameLayout frameLayout2 = new FrameLayout(context);
                                ImageView imageView = new ImageView(context);
                                imageView.setScaleType(ImageView.ScaleType.CENTER);
                                imageView.setImageResource(C2797R.drawable.story_link);
                                imageView.setScaleX(2.0f);
                                imageView.setScaleY(2.0f);
                                frameLayout2.addView(imageView, LayoutHelper.createFrame(-1, -1, 17));
                                frameLayout2.setBackground(Theme.createCircleDrawable(AndroidUtilities.m1036dp(80.0f), Theme.getColor(Theme.key_featuredStickers_addButton, resourcesProvider)));
                                frameLayout.addView(frameLayout2, LayoutHelper.createFrame(80, 80.0f, 1, 0.0f, 12.0f, 0.0f, 0.0f));
                                TextView textView = new TextView(context);
                                int i2 = Theme.key_windowBackgroundWhiteBlackText;
                                textView.setTextColor(Theme.getColor(i2, resourcesProvider));
                                textView.setTextSize(1, 20.0f);
                                textView.setTypeface(AndroidUtilities.bold());
                                textView.setText(LocaleController.getString(C2797R.string.CallForbiddenInviteLinkTitle));
                                textView.setGravity(17);
                                linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2, 32.0f, 16.0f, 32.0f, 8.0f));
                                TextView textView2 = new TextView(context);
                                textView2.setTextColor(Theme.getColor(i2, resourcesProvider));
                                textView2.setTextSize(1, 14.0f);
                                textView2.setText(AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.CallForbiddenInviteLinkText, DialogObject.getName(i, j))));
                                textView2.setGravity(17);
                                linearLayout.addView(textView2, LayoutHelper.createLinear(-1, -2, 32.0f, 0.0f, 32.0f, 18.0f));
                                final ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(context, resourcesProvider);
                                buttonWithCounterView.setText(LocaleController.getString(C2797R.string.CallForbiddenInviteLinkButton), false);
                                linearLayout.addView(buttonWithCounterView, LayoutHelper.createLinear(-1, 48, 0.0f, 0.0f, 0.0f, 0.0f));
                                builder.setCustomView(linearLayout);
                                final BottomSheet bottomSheetCreate = builder.create();
                                buttonWithCounterView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda17
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.$r8$lambda$PdRfzyJwBfQZKnM4rm1jjjUgqtY(i, buttonWithCounterView, bottomSheetCreate, j, resourcesProvider, view);
                                    }
                                });
                                bottomSheetCreate.fixNavigationBar();
                                bottomSheetCreate.show();
                            }

                            public static /* synthetic */ void $r8$lambda$PdRfzyJwBfQZKnM4rm1jjjUgqtY(final int i, final ButtonWithCounterView buttonWithCounterView, final BottomSheet bottomSheet, final long j, final Theme.ResourcesProvider resourcesProvider, View view) {
                                TL_phone.createConferenceCall createconferencecall = new TL_phone.createConferenceCall();
                                createconferencecall.random_id = Utilities.random.nextInt();
                                ConnectionsManager.getInstance(i).sendRequest(createconferencecall, new RequestDelegate() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda47
                                    @Override // org.telegram.tgnet.RequestDelegate
                                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda157
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                AlertsCreator.$r8$lambda$p_pBOoOX7R1YsAxU0ji33V9L7vg(tLObject, i, buttonWithCounterView, bottomSheet, j, tL_error, resourcesProvider);
                                            }
                                        });
                                    }
                                });
                            }

                            public static /* synthetic */ void $r8$lambda$p_pBOoOX7R1YsAxU0ji33V9L7vg(TLObject tLObject, final int i, ButtonWithCounterView buttonWithCounterView, BottomSheet bottomSheet, long j, TLRPC.TL_error tL_error, Theme.ResourcesProvider resourcesProvider) {
                                if (tLObject instanceof TLRPC.Updates) {
                                    final TLRPC.Updates updates = (TLRPC.Updates) tLObject;
                                    MessagesController.getInstance(i).putUsers(updates.users, false);
                                    MessagesController.getInstance(i).putChats(updates.chats, false);
                                    ArrayList arrayListFindUpdates = MessagesController.findUpdates(updates, TL_update.TL_updateGroupCall.class);
                                    int size = arrayListFindUpdates.size();
                                    TLRPC.GroupCall groupCall = null;
                                    int i2 = 0;
                                    while (i2 < size) {
                                        Object obj = arrayListFindUpdates.get(i2);
                                        i2++;
                                        groupCall = ((TL_update.TL_updateGroupCall) obj).call;
                                    }
                                    Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda195
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            MessagesController.getInstance(i).processUpdates(updates, false);
                                        }
                                    });
                                    if (groupCall == null || LaunchActivity.instance == null) {
                                        buttonWithCounterView.setLoading(false);
                                        return;
                                    }
                                    bottomSheet.lambda$new$0();
                                    SendMessagesHelper.getInstance(i).sendMessage(SendMessagesHelper.SendMessageParams.m1074of(groupCall.invite_link, j));
                                    BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
                                    if (safeLastFragment != null) {
                                        if (safeLastFragment instanceof ChatActivity) {
                                            ChatActivity chatActivity = (ChatActivity) safeLastFragment;
                                            if (chatActivity.getDialogId() == j && chatActivity.getChatMode() == 0) {
                                                return;
                                            }
                                        }
                                        safeLastFragment.presentFragment(ChatActivity.m1139of(j));
                                        return;
                                    }
                                    return;
                                }
                                if (!(tLObject instanceof TL_phone.groupCall)) {
                                    if (tL_error != null) {
                                        BulletinFactory.m1142of(bottomSheet.topBulletinContainer, resourcesProvider).showForError(tL_error);
                                        return;
                                    }
                                    return;
                                }
                                TL_phone.groupCall groupcall = (TL_phone.groupCall) tLObject;
                                MessagesController.getInstance(i).putUsers(groupcall.users, false);
                                MessagesController.getInstance(i).putChats(groupcall.chats, false);
                                if (LaunchActivity.instance == null) {
                                    buttonWithCounterView.setLoading(false);
                                    return;
                                }
                                TLRPC.TL_inputGroupCall tL_inputGroupCall = new TLRPC.TL_inputGroupCall();
                                TLRPC.GroupCall groupCall2 = groupcall.call;
                                tL_inputGroupCall.f1267id = groupCall2.f1260id;
                                tL_inputGroupCall.access_hash = groupCall2.access_hash;
                                bottomSheet.lambda$new$0();
                                VoIPHelper.joinConference(LaunchActivity.instance, i, tL_inputGroupCall, false, groupcall.call, null);
                                SendMessagesHelper.getInstance(i).sendMessage(SendMessagesHelper.SendMessageParams.m1074of(groupcall.call.invite_link, j));
                            }

                            public static void showGiftThemeApplyConfirm(Context context, Theme.ResourcesProvider resourcesProvider, int i, TL_stars.StarGift starGift, long j, final Runnable runnable) {
                                TLObject userOrChat = MessagesController.getInstance(i).getUserOrChat(j);
                                LinearLayout linearLayout = new LinearLayout(context);
                                linearLayout.setOrientation(1);
                                linearLayout.addView(new StarGiftSheet.GiftThemeReuseTopView(context, starGift, userOrChat), LayoutHelper.createLinear(-1, -2, 48, 0, -4, 0, 0));
                                TextView textView = new TextView(context);
                                textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
                                textView.setTextSize(1, 16.0f);
                                textView.setText(AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.GiftThemesSetInReuseInfo, DialogObject.getDialogTitle(userOrChat))));
                                linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2, 48, 24, 0, 24, 4));
                                new AlertDialog.Builder(context, resourcesProvider).setView(linearLayout).setPositiveButton(LocaleController.getString(C2797R.string.GiftThemesSetInReuseConfirm), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda198
                                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                                    public final void onClick(AlertDialog alertDialog, int i2) {
                                        runnable.run();
                                    }
                                }).setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null).show();
                            }

                            public static BottomSheet createCustomPicker(Context context, String str, int i, final String[] strArr, final Utilities.Callback<Integer> callback) {
                                if (TimezonesController.getInstance(UserConfig.selectedAccount).getTimezones().isEmpty()) {
                                    return null;
                                }
                                ScheduleDatePickerColors scheduleDatePickerColors = new ScheduleDatePickerColors();
                                BottomSheet.Builder builder = new BottomSheet.Builder(context, false, null);
                                builder.setApplyBottomPadding(false);
                                LinearLayout linearLayout = new LinearLayout(context);
                                linearLayout.setOrientation(0);
                                linearLayout.setWeightSum(1.0f);
                                final NumberPicker numberPicker = new NumberPicker(context);
                                numberPicker.setAllItemsCount(strArr.length);
                                numberPicker.setItemCount(Math.min(strArr.length, 8));
                                numberPicker.setTextColor(scheduleDatePickerColors.textColor);
                                numberPicker.setGravity(17);
                                numberPicker.setMinValue(0);
                                numberPicker.setMaxValue(strArr.length - 1);
                                numberPicker.setValue(i);
                                linearLayout.addView(numberPicker, LayoutHelper.createLinear(0, 432, 1.0f));
                                numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda258
                                    @Override // org.telegram.ui.Components.NumberPicker.Formatter
                                    public final String format(int i2) {
                                        return AlertsCreator.$r8$lambda$xfre_n6kI6Yzkx5DqCCNGwmyglQ(strArr, i2);
                                    }
                                });
                                C377970 c377970 = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.70
                                    boolean ignoreLayout = false;
                                    final /* synthetic */ NumberPicker val$picker;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C377970(Context context2, final NumberPicker numberPicker2) {
                                        super(context2);
                                        numberPicker = numberPicker2;
                                        this.ignoreLayout = false;
                                    }

                                    @Override // android.widget.LinearLayout, android.view.View
                                    public void onMeasure(int i2, int i3) {
                                        this.ignoreLayout = true;
                                        numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * 8;
                                        this.ignoreLayout = false;
                                        super.onMeasure(i2, i3);
                                    }

                                    @Override // android.view.View, android.view.ViewParent
                                    public void requestLayout() {
                                        if (this.ignoreLayout) {
                                            return;
                                        }
                                        super.requestLayout();
                                    }
                                };
                                c377970.setOrientation(1);
                                FrameLayout frameLayout = new FrameLayout(context2);
                                TextView textView = new TextView(context2);
                                textView.setText(str);
                                textView.setTextColor(scheduleDatePickerColors.textColor);
                                textView.setTextSize(1, 20.0f);
                                textView.setTypeface(AndroidUtilities.bold());
                                frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
                                textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda259
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        return AlertsCreator.$r8$lambda$vTK8rgu3VmNvz3GtPZXFNz9bPrg(view, motionEvent);
                                    }
                                });
                                c377970.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
                                c377970.addView(linearLayout, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
                                ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(context2, null);
                                buttonWithCounterView.setText(LocaleController.getString(C2797R.string.Select), false);
                                buttonWithCounterView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda260
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        bottomSheetArr[0].lambda$new$0();
                                    }
                                });
                                c377970.addView(buttonWithCounterView, LayoutHelper.createLinear(-1, 48, 0, 16, 12, 16, 12));
                                builder.setCustomView(c377970);
                                BottomSheet bottomSheetShow = builder.show();
                                bottomSheetShow.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda261
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        callback.run(Integer.valueOf(numberPicker2.getValue()));
                                    }
                                });
                                bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
                                bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
                                BottomSheet bottomSheetCreate = builder.create();
                                final BottomSheet[] bottomSheetArr = {bottomSheetCreate};
                                return bottomSheetCreate;
                            }

                            public static /* synthetic */ String $r8$lambda$xfre_n6kI6Yzkx5DqCCNGwmyglQ(String[] strArr, int i) {
                                return strArr[i];
                            }

                            /* JADX INFO: renamed from: org.telegram.ui.Components.AlertsCreator$70 */
                            public class C377970 extends LinearLayout {
                                boolean ignoreLayout = false;
                                final /* synthetic */ NumberPicker val$picker;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C377970(Context context2, final NumberPicker numberPicker2) {
                                    super(context2);
                                    numberPicker = numberPicker2;
                                    this.ignoreLayout = false;
                                }

                                @Override // android.widget.LinearLayout, android.view.View
                                public void onMeasure(int i2, int i3) {
                                    this.ignoreLayout = true;
                                    numberPicker.getLayoutParams().height = AndroidUtilities.m1036dp(42.0f) * 8;
                                    this.ignoreLayout = false;
                                    super.onMeasure(i2, i3);
                                }

                                @Override // android.view.View, android.view.ViewParent
                                public void requestLayout() {
                                    if (this.ignoreLayout) {
                                        return;
                                    }
                                    super.requestLayout();
                                }
                            }

                            public static /* synthetic */ boolean $r8$lambda$vTK8rgu3VmNvz3GtPZXFNz9bPrg(View view, MotionEvent motionEvent) {
                                return true;
                            }

                            public static void showDisableSharingInfo(Context context, Theme.ResourcesProvider resourcesProvider, final Runnable runnable) {
                                if (context == null) {
                                    return;
                                }
                                final boolean[] zArr = new boolean[1];
                                BottomSheet.Builder builder = new BottomSheet.Builder(context);
                                final Runnable dismissRunnable = builder.getDismissRunnable();
                                LinearLayout linearLayout = new LinearLayout(context);
                                linearLayout.setOrientation(1);
                                linearLayout.setClipChildren(false);
                                linearLayout.setClipToPadding(false);
                                RLottieImageView rLottieImageView = new RLottieImageView(context);
                                linearLayout.addView(rLottieImageView, LayoutHelper.createLinear(110, 110, 17, 0, 21, 0, 11));
                                rLottieImageView.setAnimation(C2797R.raw.raised_hand, 110, 110);
                                rLottieImageView.setAutoRepeat(false);
                                rLottieImageView.playAnimation();
                                TextView textView = new TextView(context);
                                textView.setTypeface(AndroidUtilities.bold());
                                textView.setGravity(17);
                                textView.setText(LocaleController.getString(C2797R.string.DisableSharingInfoHeader));
                                textView.setTextSize(1, 20.0f);
                                int i = Theme.key_windowBackgroundWhiteBlackText;
                                textView.setTextColor(Theme.getColor(i, resourcesProvider));
                                linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2, 17, 20, 0, 20, 14));
                                PremiumFeatureCell premiumFeatureCell = new PremiumFeatureCell(context, resourcesProvider);
                                premiumFeatureCell.title.setText(LocaleController.getString(C2797R.string.DisableSharingInfoHeader1));
                                premiumFeatureCell.description.setText(LocaleController.getString(C2797R.string.DisableSharingInfoText1));
                                premiumFeatureCell.nextIcon.setVisibility(8);
                                premiumFeatureCell.imageView.setImageResource(C2797R.drawable.menu_photo_off_24);
                                premiumFeatureCell.imageView.setColorFilter(Theme.getColor(i, resourcesProvider));
                                linearLayout.addView(premiumFeatureCell, LayoutHelper.createLinear(-1, -2, 6.0f, 0.0f, 6.0f, -2.0f));
                                PremiumFeatureCell premiumFeatureCell2 = new PremiumFeatureCell(context, resourcesProvider);
                                premiumFeatureCell2.title.setText(LocaleController.getString(C2797R.string.DisableSharingInfoHeader2));
                                premiumFeatureCell2.description.setText(LocaleController.getString(C2797R.string.DisableSharingInfoText2));
                                premiumFeatureCell2.nextIcon.setVisibility(8);
                                premiumFeatureCell2.imageView.setImageResource(C2797R.drawable.menu_share_off_24);
                                premiumFeatureCell2.imageView.setColorFilter(Theme.getColor(i, resourcesProvider));
                                linearLayout.addView(premiumFeatureCell2, LayoutHelper.createLinear(-1, -2, 6.0f, 0.0f, 6.0f, -2.0f));
                                PremiumFeatureCell premiumFeatureCell3 = new PremiumFeatureCell(context, resourcesProvider);
                                premiumFeatureCell3.title.setText(LocaleController.getString(C2797R.string.DisableSharingInfoHeader3));
                                premiumFeatureCell3.description.setText(LocaleController.getString(C2797R.string.DisableSharingInfoText3));
                                premiumFeatureCell3.nextIcon.setVisibility(8);
                                premiumFeatureCell3.imageView.setImageResource(C2797R.drawable.menu_download_off_24);
                                premiumFeatureCell3.imageView.setColorFilter(Theme.getColor(i, resourcesProvider));
                                linearLayout.addView(premiumFeatureCell3, LayoutHelper.createLinear(-1, -2, 6.0f, 0.0f, 6.0f, 8.0f));
                                ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(context, resourcesProvider);
                                buttonWithCounterView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda68
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlertsCreator.$r8$lambda$OnT2j_R1FVTV8_FH3L1zMRWnAzk(zArr, dismissRunnable, view);
                                    }
                                });
                                buttonWithCounterView.setRound();
                                buttonWithCounterView.setText(LocaleController.getString(C2797R.string.DisableSharingInfoButton), false);
                                linearLayout.addView(buttonWithCounterView, LayoutHelper.createLinear(-1, 48, 16.0f, 10.0f, 16.0f, 8.0f));
                                builder.setCustomView(linearLayout);
                                builder.show().setOnDismissListener(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda69
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AlertsCreator.m9991$r8$lambda$iAiQlrBcPuFoBOcGOpxoD_JGZU(zArr, runnable);
                                    }
                                });
                            }

                            public static /* synthetic */ void $r8$lambda$OnT2j_R1FVTV8_FH3L1zMRWnAzk(boolean[] zArr, Runnable runnable, View view) {
                                zArr[0] = true;
                                runnable.run();
                            }

                            /* JADX INFO: renamed from: $r8$lambda$iA-iQlrBcPuFoBOcGOpxoD_JGZU */
                            public static /* synthetic */ void m9991$r8$lambda$iAiQlrBcPuFoBOcGOpxoD_JGZU(boolean[] zArr, Runnable runnable) {
                                if (!zArr[0] || runnable == null) {
                                    return;
                                }
                                runnable.run();
                            }
                        }
