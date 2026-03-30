package org.telegram.p026ui;

import android.content.Context;
import android.view.View;
import org.telegram.p026ui.ActionBar.BaseFragment;
import org.telegram.p026ui.Components.SizeNotifierFrameLayout;

/* JADX INFO: loaded from: classes.dex */
public abstract class EmptyBaseFragment extends BaseFragment {
    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public View createView(Context context) {
        SizeNotifierFrameLayout sizeNotifierFrameLayout = new SizeNotifierFrameLayout(context);
        this.fragmentView = sizeNotifierFrameLayout;
        return sizeNotifierFrameLayout;
    }
}
