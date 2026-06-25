package androidx.core.view.insets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.R$id;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ProtectionLayout extends FrameLayout {
    private static final Object PROTECTION_VIEW = new Object();
    private ProtectionGroup mGroup;
    private final List<Protection> mProtections;

    public ProtectionLayout(Context context) {
        super(context);
        this.mProtections = new ArrayList();
    }

    public ProtectionLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ProtectionLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ProtectionLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mProtections = new ArrayList();
    }

    public ProtectionLayout(Context context, List<Protection> list) {
        super(context);
        this.mProtections = new ArrayList();
        setProtections(list);
    }

    public void setProtections(List<Protection> list) {
        this.mProtections.clear();
        this.mProtections.addAll(list);
        if (isAttachedToWindow()) {
            addProtectionViews();
            requestApplyInsets();
        }
    }

    private SystemBarStateMonitor getOrInstallSystemBarStateMonitor() {
        ViewGroup viewGroup = (ViewGroup) getRootView();
        Object tag = viewGroup.getTag(R$id.tag_system_bar_state_monitor);
        if (tag instanceof SystemBarStateMonitor) {
            return (SystemBarStateMonitor) tag;
        }
        SystemBarStateMonitor systemBarStateMonitor = new SystemBarStateMonitor(viewGroup);
        viewGroup.setTag(R$id.tag_system_bar_state_monitor, systemBarStateMonitor);
        return systemBarStateMonitor;
    }

    private void maybeUninstallSystemBarStateMonitor() {
        ViewGroup viewGroup = (ViewGroup) getRootView();
        Object tag = viewGroup.getTag(R$id.tag_system_bar_state_monitor);
        if (tag instanceof SystemBarStateMonitor) {
            SystemBarStateMonitor systemBarStateMonitor = (SystemBarStateMonitor) tag;
            if (systemBarStateMonitor.hasCallback()) {
                return;
            }
            systemBarStateMonitor.detachFromWindow();
            viewGroup.setTag(R$id.tag_system_bar_state_monitor, null);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        addProtectionViews();
        requestApplyInsets();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeProtectionViews();
        maybeUninstallSystemBarStateMonitor();
    }

    private void addProtectionViews() {
        if (this.mProtections.isEmpty()) {
            removeProtectionViews();
            return;
        }
        SystemBarStateMonitor orInstallSystemBarStateMonitor = getOrInstallSystemBarStateMonitor();
        removeProtectionViews();
        this.mGroup = new ProtectionGroup(orInstallSystemBarStateMonitor, this.mProtections);
        int childCount = getChildCount();
        int size = this.mGroup.size();
        for (int i = 0; i < size; i++) {
            addProtectionView(getContext(), i + childCount, this.mGroup.getProtection(i));
        }
    }

    private void removeProtectionViews() {
        if (this.mGroup == null) {
            return;
        }
        removeViews(getChildCount() - this.mGroup.size(), this.mGroup.size());
        int size = this.mGroup.size();
        int i = 0;
        while (true) {
            ProtectionGroup protectionGroup = this.mGroup;
            if (i < size) {
                protectionGroup.getProtection(i).getAttributes().setCallback(null);
                i++;
            } else {
                protectionGroup.dispose();
                this.mGroup = null;
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void addProtectionView(android.content.Context r7, int r8, androidx.core.view.insets.Protection r9) {
        /*
            r6 = this;
            androidx.core.view.insets.Protection$Attributes r0 = r9.getAttributes()
            int r1 = r9.getSide()
            r2 = 1
            r3 = 8
            r4 = -1
            if (r1 == r2) goto L37
            r2 = 2
            if (r1 == r2) goto L30
            r2 = 4
            if (r1 == r2) goto L27
            if (r1 != r3) goto L1d
            int r9 = r0.getHeight()
            r1 = 80
            goto L3d
        L1d:
            java.lang.String r6 = "Unexpected side: "
            int r7 = r9.getSide()
            com.sun.jna.IntegerType$$ExternalSyntheticBUOutline0.m547m(r6, r7)
            return
        L27:
            int r9 = r0.getWidth()
            r1 = 5
        L2c:
            r5 = r4
            r4 = r9
            r9 = r5
            goto L3d
        L30:
            int r9 = r0.getHeight()
            r1 = 48
            goto L3d
        L37:
            int r9 = r0.getWidth()
            r1 = 3
            goto L2c
        L3d:
            android.widget.FrameLayout$LayoutParams r2 = new android.widget.FrameLayout$LayoutParams
            r2.<init>(r4, r9, r1)
            androidx.core.graphics.Insets r9 = r0.getMargin()
            int r1 = r9.left
            r2.leftMargin = r1
            int r1 = r9.top
            r2.topMargin = r1
            int r1 = r9.right
            r2.rightMargin = r1
            int r9 = r9.bottom
            r2.bottomMargin = r9
            android.view.View r9 = new android.view.View
            r9.<init>(r7)
            java.lang.Object r7 = androidx.core.view.insets.ProtectionLayout.PROTECTION_VIEW
            r9.setTag(r7)
            float r7 = r0.getTranslationX()
            r9.setTranslationX(r7)
            float r7 = r0.getTranslationY()
            r9.setTranslationY(r7)
            float r7 = r0.getAlpha()
            r9.setAlpha(r7)
            boolean r7 = r0.isVisible()
            if (r7 == 0) goto L7c
            r3 = 0
        L7c:
            r9.setVisibility(r3)
            android.graphics.drawable.Drawable r7 = r0.getDrawable()
            r9.setBackground(r7)
            androidx.core.view.insets.ProtectionLayout$1 r7 = new androidx.core.view.insets.ProtectionLayout$1
            r7.<init>()
            r0.setCallback(r7)
            r6.addView(r9, r8, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.view.insets.ProtectionLayout.addProtectionView(android.content.Context, int, androidx.core.view.insets.Protection):void");
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (view != null && view.getTag() != PROTECTION_VIEW) {
            ProtectionGroup protectionGroup = this.mGroup;
            int childCount = getChildCount() - (protectionGroup != null ? protectionGroup.size() : 0);
            if (i > childCount || i < 0) {
                i = childCount;
            }
        }
        super.addView(view, i, layoutParams);
    }
}
