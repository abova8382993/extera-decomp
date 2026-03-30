package org.telegram.p029ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p029ui.ActionBar.Theme;
import org.telegram.p029ui.Cells.ShadowSectionCell;
import org.telegram.p029ui.Cells.TextInfoPrivacyCell;
import org.telegram.p029ui.FiltersSetupActivity;
import p022j$.util.Objects;

/* JADX INFO: loaded from: classes7.dex */
public class SectionsScrollView extends ScrollView {
    private ArrayList children;
    private final Path clipPath;
    private LinearLayout contentView;
    private ArrayList onScroll;
    private Theme.ResourcesProvider resourcesProvider;
    private float sectionRadius;
    private float[] sectionRadiusBottom;
    private float[] sectionRadiusTop;

    public static boolean isSectionView(View view) {
        return (Objects.equals(view.getTag(), -33024) || (view instanceof TextInfoPrivacyCell) || (view instanceof ShadowSectionCell) || (view instanceof FiltersSetupActivity.HintInnerCell)) ? false : true;
    }

    public SectionsScrollView(Context context, LinearLayout linearLayout, Theme.ResourcesProvider resourcesProvider) {
        this(context, linearLayout, resourcesProvider, true);
    }

    public SectionsScrollView(Context context, LinearLayout linearLayout, Theme.ResourcesProvider resourcesProvider, boolean z) {
        super(context);
        this.sectionRadius = AndroidUtilities.m1124dp(16.0f);
        this.onScroll = new ArrayList();
        this.children = new ArrayList();
        this.clipPath = new Path();
        this.resourcesProvider = resourcesProvider;
        this.contentView = linearLayout;
        setWillNotDraw(false);
        this.contentView.setPadding(AndroidUtilities.m1124dp(12.0f), AndroidUtilities.m1124dp(z ? 12.0f : 4.0f), AndroidUtilities.m1124dp(12.0f), AndroidUtilities.m1124dp(12.0f));
        this.sectionRadiusTop = new float[]{AndroidUtilities.m1124dp(16.0f), AndroidUtilities.m1124dp(16.0f), AndroidUtilities.m1124dp(16.0f), AndroidUtilities.m1124dp(16.0f), 0.0f, 0.0f, 0.0f, 0.0f};
        this.sectionRadiusBottom = new float[]{0.0f, 0.0f, 0.0f, 0.0f, AndroidUtilities.m1124dp(16.0f), AndroidUtilities.m1124dp(16.0f), AndroidUtilities.m1124dp(16.0f), AndroidUtilities.m1124dp(16.0f)};
    }

    public void onScroll(Runnable runnable) {
        this.onScroll.add(runnable);
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        ArrayList arrayList = this.onScroll;
        int size = arrayList.size();
        int i5 = 0;
        while (i5 < size) {
            Object obj = arrayList.get(i5);
            i5++;
            ((Runnable) obj).run();
        }
        invalidate();
        this.contentView.invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void gatherChildren(android.view.ViewGroup r7, float r8, float r9) {
        /*
            r6 = this;
            r0 = 0
        L1:
            int r1 = r7.getChildCount()
            if (r0 >= r1) goto L63
            android.view.View r1 = r7.getChildAt(r0)
            int r2 = r1.getVisibility()
            if (r2 == 0) goto L12
            goto L60
        L12:
            boolean r2 = r1 instanceof android.widget.LinearLayout
            if (r2 == 0) goto L5b
            r2 = r1
            android.widget.LinearLayout r2 = (android.widget.LinearLayout) r2
            int r3 = r2.getOrientation()
            r4 = 1
            if (r3 != r4) goto L5b
            float r3 = r1.getX()
            float r3 = r3 + r8
            android.widget.LinearLayout r4 = r6.contentView
            int r4 = r4.getPaddingLeft()
            float r4 = (float) r4
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 > 0) goto L5b
            float r3 = r1.getX()
            float r3 = r3 + r8
            int r4 = r1.getWidth()
            float r4 = (float) r4
            float r3 = r3 + r4
            android.widget.LinearLayout r4 = r6.contentView
            int r4 = r4.getWidth()
            android.widget.LinearLayout r5 = r6.contentView
            int r5 = r5.getPaddingRight()
            int r4 = r4 - r5
            float r4 = (float) r4
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 < 0) goto L5b
            float r3 = r1.getX()
            float r3 = r3 + r8
            float r1 = r1.getY()
            float r1 = r1 + r9
            r6.gatherChildren(r2, r3, r1)
            goto L60
        L5b:
            java.util.ArrayList r2 = r6.children
            r2.add(r1)
        L60:
            int r0 = r0 + 1
            goto L1
        L63:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.Components.SectionsScrollView.gatherChildren(android.view.ViewGroup, float, float):void");
    }

    private float getChildX(View view) {
        if (view == this.contentView || !(view.getParent() instanceof View)) {
            return view.getX();
        }
        return getChildX((View) view.getParent()) + view.getX();
    }

    private float getChildY(View view) {
        if (view == this.contentView || !(view.getParent() instanceof View)) {
            return view.getY();
        }
        return getChildY((View) view.getParent()) + view.getY();
    }

    private void drawSectionsBackgrounds(Canvas canvas) {
        this.children.clear();
        gatherChildren(this.contentView, 0.0f, 0.0f);
        ArrayList arrayList = this.children;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            View view = null;
            View view2 = null;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                View view3 = (View) obj;
                if (!isSectionView(view3)) {
                    break;
                }
                if (view != null && Math.abs(view2.getAlpha() - view3.getAlpha()) > 0.1f) {
                    drawSectionBackground(canvas, view, view2);
                    view = null;
                }
                if (view == null) {
                    view = view3;
                }
                view2 = view3;
            }
            drawSectionBackground(canvas, view, view2);
            return;
            drawSectionBackground(canvas, view, view2);
        }
    }

    private void drawSectionBackground(Canvas canvas, View view, View view2) {
        if (view == null || view2 == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = view2.getLayoutParams();
        float f = 0.0f;
        float f2 = (view.getParent() == this.contentView || !(layoutParams instanceof ViewGroup.MarginLayoutParams)) ? 0.0f : ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
        ViewParent parent = view2.getParent();
        LinearLayout linearLayout = this.contentView;
        if (parent != linearLayout && (layoutParams2 instanceof ViewGroup.MarginLayoutParams)) {
            f = ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin;
        }
        RectF rectF = AndroidUtilities.rectTmp;
        rectF.set(linearLayout.getX() + getChildX(view), Math.max(getScrollY() - AndroidUtilities.m1124dp(16.0f), (this.contentView.getY() + getChildY(view)) - f2), this.contentView.getX() + getChildX(view) + view.getWidth(), Math.min(getHeight() + AndroidUtilities.m1124dp(16.0f) + getScrollY(), this.contentView.getY() + getChildY(view2) + view2.getHeight() + f));
        if (rectF.bottom < rectF.top) {
            return;
        }
        RecyclerListView.drawBackgroundRect(canvas, rectF, AndroidUtilities.m1124dp(16.0f), AndroidUtilities.m1124dp(16.0f), view.getAlpha(), this.resourcesProvider);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        drawSectionsBackgrounds(canvas);
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        return super.drawChild(canvas, view, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clipChild(Canvas canvas, View view) {
        if (view == null || !isSectionView(view)) {
            return;
        }
        int iIndexOfChild = this.contentView.indexOfChild(view);
        int i = iIndexOfChild - 1;
        View childAt = i < 0 ? null : this.contentView.getChildAt(i);
        int i2 = iIndexOfChild + 1;
        View childAt2 = i2 < this.contentView.getChildCount() ? this.contentView.getChildAt(i2) : null;
        boolean z = childAt != null && isSectionView(childAt);
        boolean z2 = childAt2 != null && isSectionView(childAt2);
        RectF rectF = AndroidUtilities.rectTmp;
        rectF.set(view.getX(), Math.max(getScrollY() - AndroidUtilities.m1124dp(16.0f), this.contentView.getY() + view.getY()), view.getX() + view.getWidth(), Math.min(getHeight() + getScrollY() + AndroidUtilities.m1124dp(16.0f), this.contentView.getY() + view.getY() + view.getHeight()));
        if (z && z2) {
            z = view.getY() >= rectF.top;
            boolean z3 = view.getY() + ((float) view.getHeight()) <= rectF.bottom;
            if (z && z3) {
                return;
            } else {
                z2 = z3;
            }
        }
        if (!z && !z2) {
            this.clipPath.rewind();
            Path path = this.clipPath;
            float f = this.sectionRadius;
            path.addRoundRect(rectF, f, f, Path.Direction.CW);
            canvas.clipPath(this.clipPath);
            return;
        }
        if (!z) {
            this.clipPath.rewind();
            this.clipPath.addRoundRect(rectF, this.sectionRadiusTop, Path.Direction.CW);
            canvas.clipPath(this.clipPath);
        } else {
            if (z2) {
                return;
            }
            this.clipPath.rewind();
            this.clipPath.addRoundRect(rectF, this.sectionRadiusBottom, Path.Direction.CW);
            canvas.clipPath(this.clipPath);
        }
    }

    public static class SectionsLinearLayout extends LinearLayout {
        public SectionsLinearLayout(Context context) {
            super(context);
            setWillNotDraw(false);
        }

        @Override // android.view.ViewGroup
        protected boolean drawChild(Canvas canvas, View view, long j) {
            if (getParent() instanceof SectionsScrollView) {
                SectionsScrollView sectionsScrollView = (SectionsScrollView) getParent();
                canvas.save();
                sectionsScrollView.clipChild(canvas, view);
                boolean zDrawChild = super.drawChild(canvas, view, j);
                canvas.restore();
                return zDrawChild;
            }
            return super.drawChild(canvas, view, j);
        }

        @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            if (getParent() instanceof SectionsScrollView) {
                ((SectionsScrollView) getParent()).invalidate();
            }
        }
    }
}
