package org.telegram.p035ui.Components;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.Bulletin;
import org.telegram.p035ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.p035ui.LaunchActivity;
import org.telegram.p035ui.PremiumPreviewFragment;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes7.dex */
@SuppressLint({"ViewConstructor"})
public class StickerSetBulletinLayout extends Bulletin.TwoLineLayout {
    public StickerSetBulletinLayout(Context context, TLObject tLObject, int i, TLRPC.Document document, Theme.ResourcesProvider resourcesProvider) {
        this(context, tLObject, 1, i, document, resourcesProvider);
    }

    /* JADX WARN: Removed duplicated region for block: B:158:0x0021 A[PHI: r3
  0x0021: PHI (r3v2 org.telegram.tgnet.TLRPC$StickerSet) = 
  (r3v1 org.telegram.tgnet.TLRPC$StickerSet)
  (r3v1 org.telegram.tgnet.TLRPC$StickerSet)
  (r3v1 org.telegram.tgnet.TLRPC$StickerSet)
  (r3v18 org.telegram.tgnet.TLRPC$StickerSet)
  (r3v18 org.telegram.tgnet.TLRPC$StickerSet)
 binds: [B:169:0x0047, B:171:0x004d, B:173:0x0053, B:154:0x0012, B:156:0x0018] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public StickerSetBulletinLayout(final android.content.Context r12, org.telegram.tgnet.TLObject r13, int r14, int r15, org.telegram.tgnet.TLRPC.Document r16, org.telegram.ui.ActionBar.Theme.ResourcesProvider r17) {
        /*
            Method dump skipped, instruction units count: 980
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.StickerSetBulletinLayout.<init>(android.content.Context, org.telegram.tgnet.TLObject, int, int, org.telegram.tgnet.TLRPC$Document, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
    }

    public static /* synthetic */ void $r8$lambda$H3iEL8c72YVk4MXdEAWXkJoksi0(Context context) {
        Activity activityFindActivity = AndroidUtilities.findActivity(context);
        if (activityFindActivity instanceof LaunchActivity) {
            ((LaunchActivity) activityFindActivity).lambda$runLinkRequest$101(new PremiumPreviewFragment(LimitReachedBottomSheet.limitTypeToServerString(10)));
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$57QwHCV-IjZ3fyt7ykEQYpLyG_I */
    public static /* synthetic */ void m14321$r8$lambda$57QwHCVIjZ3fyt7ykEQYpLyG_I(Context context) {
        Activity activityFindActivity = AndroidUtilities.findActivity(context);
        if (activityFindActivity instanceof LaunchActivity) {
            ((LaunchActivity) activityFindActivity).lambda$runLinkRequest$101(new PremiumPreviewFragment(LimitReachedBottomSheet.limitTypeToServerString(9)));
        }
    }
}
