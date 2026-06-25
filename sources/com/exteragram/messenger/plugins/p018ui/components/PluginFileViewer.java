package com.exteragram.messenger.plugins.p018ui.components;

import android.app.Activity;
import android.content.DialogInterface;
import com.exteragram.messenger.utils.MarkdownUtils;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.p028io.FilesKt;
import kotlin.text.Charsets;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_iv;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J&\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eJ\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u001a\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, m877d2 = {"Lcom/exteragram/messenger/plugins/ui/components/PluginFileViewer;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "MAX_FILE_SIZE", _UrlKt.FRAGMENT_ENCODE_SET, "MAX_BLOCK_LENGTH", "open", _UrlKt.FRAGMENT_ENCODE_SET, "fragment", "Lorg/telegram/ui/ActionBar/BaseFragment;", "file", "Ljava/io/File;", "fileName", _UrlKt.FRAGMENT_ENCODE_SET, "createMessageObject", "Lorg/telegram/messenger/MessageObject;", "normalizeFileName", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nPluginFileViewer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PluginFileViewer.kt\ncom/exteragram/messenger/plugins/ui/components/PluginFileViewer\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,127:1\n1#2:128\n*E\n"})
public final class PluginFileViewer {
    public static final PluginFileViewer INSTANCE = new PluginFileViewer();
    private static final int MAX_BLOCK_LENGTH = 8192;
    private static final int MAX_FILE_SIZE = 524288;

    private PluginFileViewer() {
    }

    public static /* synthetic */ boolean open$default(PluginFileViewer pluginFileViewer, BaseFragment baseFragment, File file, String str, int i, Object obj) {
        if ((i & 4) != 0) {
            str = null;
        }
        return pluginFileViewer.open(baseFragment, file, str);
    }

    public final boolean open(final BaseFragment fragment, final File file, final String fileName) {
        Activity parentActivity;
        if (fragment == null || (parentActivity = fragment.getParentActivity()) == null || file == null || !file.exists() || !file.isFile()) {
            return false;
        }
        if (file.length() > 524288) {
            BulletinFactory.m1143of(fragment).createSimpleBulletin(C2797R.raw.error, LocaleController.getString(C2797R.string.ImportFileTooLarge)).show();
            return false;
        }
        final AlertDialog alertDialog = new AlertDialog(parentActivity, 3, fragment.getResourceProvider());
        alertDialog.setCanceledOnTouchOutside(false);
        final boolean[] zArr = {false};
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.exteragram.messenger.plugins.ui.components.PluginFileViewer$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                PluginFileViewer.m2565$r8$lambda$V5c36KhCJGXK1eWQobTq6ixb5g(zArr, dialogInterface);
            }
        });
        alertDialog.showDelayed(150L);
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.components.PluginFileViewer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                PluginFileViewer.$r8$lambda$qqdbMSxrLs0kkr2wrRKExk_Q7bY(file, fileName, alertDialog, zArr, fragment);
            }
        });
        return true;
    }

    /* JADX INFO: renamed from: $r8$lambda$V5c36KhC-JGXK1eWQobTq6ixb5g */
    public static void m2565$r8$lambda$V5c36KhCJGXK1eWQobTq6ixb5g(boolean[] zArr, DialogInterface dialogInterface) {
        zArr[0] = true;
    }

    public static void $r8$lambda$qqdbMSxrLs0kkr2wrRKExk_Q7bY(File file, String str, final AlertDialog alertDialog, final boolean[] zArr, final BaseFragment baseFragment) {
        final MessageObject messageObjectCreateMessageObject;
        try {
            PluginFileViewer pluginFileViewer = INSTANCE;
            messageObjectCreateMessageObject = pluginFileViewer.createMessageObject(file, pluginFileViewer.normalizeFileName(file, str));
        } catch (Throwable th) {
            FileLog.m1048e(th);
            messageObjectCreateMessageObject = null;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.components.PluginFileViewer$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PluginFileViewer.open$lambda$1$0(alertDialog, zArr, messageObjectCreateMessageObject, baseFragment);
            }
        });
    }

    public static final void open$lambda$1$0(AlertDialog alertDialog, boolean[] zArr, MessageObject messageObject, BaseFragment baseFragment) {
        try {
            alertDialog.dismiss();
        } catch (Throwable unused) {
        }
        if (zArr[0]) {
            return;
        }
        if (messageObject != null) {
            baseFragment.createArticleViewer(false).open(messageObject);
        } else {
            BulletinFactory.m1143of(baseFragment).createSimpleBulletin(C2797R.raw.error, LocaleController.getString(C2797R.string.ErrorOccurred)).show();
        }
    }

    private final MessageObject createMessageObject(File file, String fileName) {
        String text = FilesKt.readText(file, Charsets.UTF_8);
        TL_iv.TL_page tL_page = new TL_iv.TL_page();
        tL_page.local = file;
        tL_page.url = fileName;
        MarkdownUtils.appendPreformattedBlocks(tL_page.blocks, text, "python", 8192);
        TLRPC.TL_webPage tL_webPage = new TLRPC.TL_webPage();
        tL_webPage.f1416id = file.getAbsolutePath().hashCode();
        tL_webPage.url = fileName;
        tL_webPage.display_url = fileName;
        tL_webPage.title = fileName;
        tL_webPage.flags |= 1028;
        tL_webPage.cached_page = tL_page;
        long j = UserConfig.getInstance(UserConfig.selectedAccount).clientUserId;
        TLRPC.TL_message tL_message = new TLRPC.TL_message();
        tL_message.f1271id = 0;
        tL_message.date = (int) (System.currentTimeMillis() / 1000);
        tL_message.message = fileName;
        tL_message.out = true;
        TLRPC.TL_peerUser tL_peerUser = new TLRPC.TL_peerUser();
        tL_peerUser.user_id = j;
        tL_message.peer_id = tL_peerUser;
        TLRPC.TL_peerUser tL_peerUser2 = new TLRPC.TL_peerUser();
        tL_peerUser2.user_id = j;
        tL_message.from_id = tL_peerUser2;
        TLRPC.TL_messageMediaWebPage tL_messageMediaWebPage = new TLRPC.TL_messageMediaWebPage();
        tL_messageMediaWebPage.webpage = tL_webPage;
        tL_message.media = tL_messageMediaWebPage;
        return new MessageObject(UserConfig.selectedAccount, tL_message, false, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x000c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final java.lang.String normalizeFileName(java.io.File r1, java.lang.String r2) {
        /*
            r0 = this;
            if (r2 == 0) goto Lc
            boolean r0 = kotlin.text.StringsKt.isBlank(r2)
            if (r0 != 0) goto L9
            goto La
        L9:
            r2 = 0
        La:
            if (r2 != 0) goto L10
        Lc:
            java.lang.String r2 = r1.getName()
        L10:
            r0 = 1
            java.lang.String r1 = ".plugin"
            boolean r0 = kotlin.text.StringsKt.endsWith(r2, r1, r0)
            if (r0 == 0) goto L1a
            return r2
        L1a:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.plugins.p018ui.components.PluginFileViewer.normalizeFileName(java.io.File, java.lang.String):java.lang.String");
    }
}
