package com.exteragram.messenger.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.ScaleStateListAnimator;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"ViewConstructor"})
public class ActionRow extends FrameLayout {
    private final int GAP_DP;
    private final int HORIZONTAL_PADDING_DP;
    private final int ITEM_SIZE_DP;
    private final FrameLayout buttonsView;
    private final List<ActionItem> currentItems;

    public static class ActionItem {
        public View.OnClickListener action;
        public boolean enabled;
        public int icon;
        public View.OnLongClickListener longAction;

        public ActionItem(int i, boolean z, View.OnClickListener onClickListener) {
            this(i, z, onClickListener, null);
        }

        public ActionItem(int i, boolean z, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
            this.icon = i;
            this.enabled = z;
            this.action = onClickListener;
            this.longAction = onLongClickListener;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.icon == ((ActionItem) obj).icon;
        }

        public int hashCode() {
            return this.icon;
        }
    }

    public ActionRow(Context context, Theme.ResourcesProvider resourcesProvider, List<ActionItem> list) {
        super(context);
        this.HORIZONTAL_PADDING_DP = 10;
        this.ITEM_SIZE_DP = 40;
        this.GAP_DP = 8;
        this.currentItems = new ArrayList();
        C10711 c10711 = new FrameLayout(context) { // from class: com.exteragram.messenger.components.ActionRow.1
            public C10711(Context context2) {
                super(context2);
            }

            @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
            public void onLayout(boolean z, int i, int i2, int i3, int i4) {
                for (int i5 = 0; i5 < getChildCount(); i5++) {
                    int iM1036dp = (i3 - i) - AndroidUtilities.m1036dp((getChildCount() * 40) + 20);
                    int childCount = 1;
                    if (getChildCount() - 1 > 0) {
                        childCount = getChildCount() - 1;
                    }
                    int iM1036dp2 = AndroidUtilities.m1036dp((i5 * 40) + 10) + ((iM1036dp / childCount) * i5);
                    int iM1036dp3 = AndroidUtilities.m1036dp(8.0f);
                    View childAt = getChildAt(i5);
                    childAt.layout(iM1036dp2, iM1036dp3, childAt.getMeasuredWidth() + iM1036dp2, childAt.getMeasuredHeight() + iM1036dp3);
                }
            }
        };
        this.buttonsView = c10711;
        addView(c10711, LayoutHelper.createLinear(-1, -1));
        updateItems(list, resourcesProvider);
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.components.ActionRow$1 */
    public class C10711 extends FrameLayout {
        public C10711(Context context2) {
            super(context2);
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            for (int i5 = 0; i5 < getChildCount(); i5++) {
                int iM1036dp = (i3 - i) - AndroidUtilities.m1036dp((getChildCount() * 40) + 20);
                int childCount = 1;
                if (getChildCount() - 1 > 0) {
                    childCount = getChildCount() - 1;
                }
                int iM1036dp2 = AndroidUtilities.m1036dp((i5 * 40) + 10) + ((iM1036dp / childCount) * i5);
                int iM1036dp3 = AndroidUtilities.m1036dp(8.0f);
                View childAt = getChildAt(i5);
                childAt.layout(iM1036dp2, iM1036dp3, childAt.getMeasuredWidth() + iM1036dp2, childAt.getMeasuredHeight() + iM1036dp3);
            }
        }
    }

    public void updateItems(List<ActionItem> list, Theme.ResourcesProvider resourcesProvider) {
        this.buttonsView.removeAllViews();
        this.currentItems.clear();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        int i2 = 0;
        for (ActionItem actionItem : list) {
            if (actionItem.enabled && i2 < 4) {
                addImageButton(getContext(), resourcesProvider, this.buttonsView, actionItem, i2);
                this.currentItems.add(actionItem);
                i2++;
            } else {
                arrayList.add(actionItem);
            }
        }
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ActionItem actionItem2 = (ActionItem) obj;
            if (i2 >= 4) {
                return;
            }
            addImageButton(getContext(), resourcesProvider, this.buttonsView, actionItem2, i2);
            this.currentItems.add(actionItem2);
            i2++;
        }
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.components.ActionRow$2 */
    public class C10722 extends ImageView {
        public C10722(Context context) {
            super(context);
        }

        @Override // android.view.View
        public void setEnabled(boolean z) {
            super.setEnabled(z);
            setAlpha(z ? 1.0f : 0.5f);
        }
    }

    private void addImageButton(Context context, Theme.ResourcesProvider resourcesProvider, FrameLayout frameLayout, final ActionItem actionItem, final int i) {
        final C10722 c10722 = new ImageView(context) { // from class: com.exteragram.messenger.components.ActionRow.2
            public C10722(Context context2) {
                super(context2);
            }

            @Override // android.view.View
            public void setEnabled(boolean z) {
                super.setEnabled(z);
                setAlpha(z ? 1.0f : 0.5f);
            }
        };
        ScaleStateListAnimator.apply(c10722, 0.15f, 1.5f);
        c10722.setScaleType(ImageView.ScaleType.CENTER);
        c10722.setEnabled(actionItem.enabled);
        c10722.setImageDrawable(ContextCompat.getDrawable(context2, actionItem.icon).mutate());
        c10722.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_actionBarDefaultSubmenuItemIcon, resourcesProvider), PorterDuff.Mode.MULTIPLY));
        c10722.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector, resourcesProvider), 1, AndroidUtilities.m1036dp(20.0f)));
        c10722.setOnClickListener(actionItem.action);
        View.OnLongClickListener onLongClickListener = actionItem.longAction;
        if (onLongClickListener != null) {
            c10722.setOnLongClickListener(onLongClickListener);
        }
        c10722.setTag(actionItem);
        c10722.setAlpha(0.0f);
        c10722.setTranslationX(AndroidUtilities.m1036dp(12.0f));
        frameLayout.addView(c10722, LayoutHelper.createFrame(40, 40, 51));
        c10722.post(new Runnable() { // from class: com.exteragram.messenger.components.ActionRow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                c10722.animate().alpha(actionItem.enabled ? 1.0f : 0.5f).translationX(0.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).setStartDelay((((long) i) * 35) + 100).setDuration(400L).start();
            }
        });
    }

    public boolean isItemPresent(int i) {
        for (int i2 = 0; i2 < this.buttonsView.getChildCount(); i2++) {
            View childAt = this.buttonsView.getChildAt(i2);
            if (childAt instanceof ImageView) {
                Object tag = childAt.getTag();
                if ((tag instanceof ActionItem) && ((ActionItem) tag).icon == i) {
                    return true;
                }
            }
        }
        return false;
    }
}
