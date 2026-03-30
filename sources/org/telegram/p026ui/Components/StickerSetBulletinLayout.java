package org.telegram.p026ui.Components;

import android.app.Activity;
import android.content.Context;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p026ui.ActionBar.Theme;
import org.telegram.p026ui.Components.Bulletin;
import org.telegram.p026ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.p026ui.LaunchActivity;
import org.telegram.p026ui.PremiumPreviewFragment;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes5.dex */
public class StickerSetBulletinLayout extends Bulletin.TwoLineLayout {
    public StickerSetBulletinLayout(Context context, TLObject tLObject, int i, TLRPC.Document document, Theme.ResourcesProvider resourcesProvider) {
        this(context, tLObject, 1, i, document, resourcesProvider);
    }

    /* JADX WARN: Removed duplicated region for block: B:151:0x0024 A[PHI: r4
  0x0024: PHI (r4v2 org.telegram.tgnet.TLRPC$StickerSet) = 
  (r4v1 org.telegram.tgnet.TLRPC$StickerSet)
  (r4v19 org.telegram.tgnet.TLRPC$StickerSet)
  (r4v19 org.telegram.tgnet.TLRPC$StickerSet)
 binds: [B:159:0x003c, B:147:0x0015, B:149:0x001b] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public StickerSetBulletinLayout(final android.content.Context r14, org.telegram.tgnet.TLObject r15, int r16, int r17, org.telegram.tgnet.TLRPC.Document r18, org.telegram.ui.ActionBar.Theme.ResourcesProvider r19) {
        /*
            Method dump skipped, instruction units count: 984
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.Components.StickerSetBulletinLayout.<init>(android.content.Context, org.telegram.tgnet.TLObject, int, int, org.telegram.tgnet.TLRPC$Document, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
    }

    public static /* synthetic */ void $r8$lambda$H3iEL8c72YVk4MXdEAWXkJoksi0(Context context) {
        Activity activityFindActivity = AndroidUtilities.findActivity(context);
        if (activityFindActivity instanceof LaunchActivity) {
            ((LaunchActivity) activityFindActivity).lambda$runLinkRequest$103(new PremiumPreviewFragment(LimitReachedBottomSheet.limitTypeToServerString(10)));
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$57QwHCV-IjZ3fyt7ykEQYpLyG_I */
    public static /* synthetic */ void m11941$r8$lambda$57QwHCVIjZ3fyt7ykEQYpLyG_I(Context context) {
        Activity activityFindActivity = AndroidUtilities.findActivity(context);
        if (activityFindActivity instanceof LaunchActivity) {
            ((LaunchActivity) activityFindActivity).lambda$runLinkRequest$103(new PremiumPreviewFragment(LimitReachedBottomSheet.limitTypeToServerString(9)));
        }
    }
}
