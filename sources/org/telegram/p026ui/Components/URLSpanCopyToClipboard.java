package org.telegram.p026ui.Components;

import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p026ui.ActionBar.BaseFragment;

/* JADX INFO: loaded from: classes5.dex */
public class URLSpanCopyToClipboard extends URLSpanNoUnderline {
    private BaseFragment fragment;

    public URLSpanCopyToClipboard(String str, BaseFragment baseFragment) {
        super(str);
        this.fragment = baseFragment;
    }

    @Override // org.telegram.p026ui.Components.URLSpanNoUnderline, android.text.style.URLSpan, android.text.style.ClickableSpan
    public void onClick(View view) {
        AndroidUtilities.addToClipboard(getURL());
        BulletinFactory.m1195of(this.fragment).createCopyLinkBulletin().show();
    }
}
