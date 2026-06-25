package org.telegram.p035ui;

import android.content.Context;
import android.view.View;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.Components.SizeNotifierFrameLayout;

/* JADX INFO: loaded from: classes3.dex */
public abstract class EmptyBaseFragment extends BaseFragment {
    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public View createView(Context context) {
        SizeNotifierFrameLayout sizeNotifierFrameLayout = new SizeNotifierFrameLayout(context);
        this.fragmentView = sizeNotifierFrameLayout;
        return sizeNotifierFrameLayout;
    }
}
