package org.telegram.p035ui.Components;

import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class CornerPath extends Path {
    private static ArrayList<RectF> recycled;
    private boolean isPathCreated;
    private int paddingX;
    private int paddingY;
    private final ArrayList<RectF> rects;
    private float rectsUnionDiffDelta;
    protected boolean useCornerPathImplementation;

    public CornerPath() {
        this.isPathCreated = false;
        this.useCornerPathImplementation = true;
        this.rectsUnionDiffDelta = 0.0f;
        this.rects = new ArrayList<>(1);
    }

    public CornerPath(int i) {
        this.isPathCreated = false;
        this.useCornerPathImplementation = true;
        this.rectsUnionDiffDelta = 0.0f;
        this.rects = new ArrayList<>(i);
    }

    public void setPadding(int i, int i2) {
        this.paddingX = i;
        this.paddingY = i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x007e  */
    @Override // android.graphics.Path
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addRect(android.graphics.RectF r10, android.graphics.Path.Direction r11) {
        /*
            r9 = this;
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 34
            if (r0 < r1) goto La1
            boolean r0 = r9.useCornerPathImplementation
            if (r0 != 0) goto Lc
            goto La1
        Lc:
            java.util.ArrayList<android.graphics.RectF> r11 = r9.rects
            int r11 = r11.size()
            if (r11 <= 0) goto L29
            java.util.ArrayList<android.graphics.RectF> r11 = r9.rects
            int r0 = r11.size()
            int r0 = r0 + (-1)
            java.lang.Object r11 = r11.get(r0)
            android.graphics.RectF r11 = (android.graphics.RectF) r11
            boolean r11 = r11.contains(r10)
            if (r11 == 0) goto L29
            return
        L29:
            java.util.ArrayList<android.graphics.RectF> r11 = r9.rects
            int r11 = r11.size()
            r0 = 0
            if (r11 <= 0) goto L7e
            float r11 = r10.top
            java.util.ArrayList<android.graphics.RectF> r1 = r9.rects
            int r2 = r1.size()
            int r2 = r2 + (-1)
            java.lang.Object r1 = r1.get(r2)
            android.graphics.RectF r1 = (android.graphics.RectF) r1
            float r1 = r1.top
            float r11 = r11 - r1
            float r11 = java.lang.Math.abs(r11)
            float r1 = r9.rectsUnionDiffDelta
            int r11 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            if (r11 > 0) goto L7e
            float r11 = r10.bottom
            java.util.ArrayList<android.graphics.RectF> r1 = r9.rects
            int r2 = r1.size()
            int r2 = r2 + (-1)
            java.lang.Object r1 = r1.get(r2)
            android.graphics.RectF r1 = (android.graphics.RectF) r1
            float r1 = r1.bottom
            float r11 = r11 - r1
            float r11 = java.lang.Math.abs(r11)
            float r1 = r9.rectsUnionDiffDelta
            int r11 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            if (r11 > 0) goto L7e
            java.util.ArrayList<android.graphics.RectF> r11 = r9.rects
            int r1 = r11.size()
            int r1 = r1 + (-1)
            java.lang.Object r11 = r11.get(r1)
            android.graphics.RectF r11 = (android.graphics.RectF) r11
            r11.union(r10)
            goto L9e
        L7e:
            java.util.ArrayList<android.graphics.RectF> r11 = org.telegram.p035ui.Components.CornerPath.recycled
            if (r11 == 0) goto L91
            int r11 = r11.size()
            if (r11 <= 0) goto L91
            java.util.ArrayList<android.graphics.RectF> r11 = org.telegram.p035ui.Components.CornerPath.recycled
            java.lang.Object r11 = r11.remove(r0)
            android.graphics.RectF r11 = (android.graphics.RectF) r11
            goto L96
        L91:
            android.graphics.RectF r11 = new android.graphics.RectF
            r11.<init>()
        L96:
            r11.set(r10)
            java.util.ArrayList<android.graphics.RectF> r10 = r9.rects
            r10.add(r11)
        L9e:
            r9.isPathCreated = r0
            return
        La1:
            float r0 = r10.left
            int r1 = r9.paddingX
            float r2 = (float) r1
            float r4 = r0 - r2
            float r0 = r10.top
            int r2 = r9.paddingY
            float r3 = (float) r2
            float r5 = r0 - r3
            float r0 = r10.right
            float r1 = (float) r1
            float r6 = r0 + r1
            float r10 = r10.bottom
            float r0 = (float) r2
            float r7 = r10 + r0
            r3 = r9
            r8 = r11
            super.addRect(r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.CornerPath.addRect(android.graphics.RectF, android.graphics.Path$Direction):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x007c  */
    @Override // android.graphics.Path
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addRect(float r4, float r5, float r6, float r7, android.graphics.Path.Direction r8) {
        /*
            r3 = this;
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 34
            if (r0 < r1) goto L9f
            boolean r0 = r3.useCornerPathImplementation
            if (r0 != 0) goto Lc
            goto L9f
        Lc:
            java.util.ArrayList<android.graphics.RectF> r8 = r3.rects
            int r8 = r8.size()
            if (r8 <= 0) goto L29
            java.util.ArrayList<android.graphics.RectF> r8 = r3.rects
            int r0 = r8.size()
            int r0 = r0 + (-1)
            java.lang.Object r8 = r8.get(r0)
            android.graphics.RectF r8 = (android.graphics.RectF) r8
            boolean r8 = r8.contains(r4, r5, r6, r7)
            if (r8 == 0) goto L29
            return
        L29:
            java.util.ArrayList<android.graphics.RectF> r8 = r3.rects
            int r8 = r8.size()
            r0 = 0
            if (r8 <= 0) goto L7c
            java.util.ArrayList<android.graphics.RectF> r8 = r3.rects
            int r1 = r8.size()
            int r1 = r1 + (-1)
            java.lang.Object r8 = r8.get(r1)
            android.graphics.RectF r8 = (android.graphics.RectF) r8
            float r8 = r8.top
            float r8 = r5 - r8
            float r8 = java.lang.Math.abs(r8)
            float r1 = r3.rectsUnionDiffDelta
            int r8 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r8 > 0) goto L7c
            java.util.ArrayList<android.graphics.RectF> r8 = r3.rects
            int r1 = r8.size()
            int r1 = r1 + (-1)
            java.lang.Object r8 = r8.get(r1)
            android.graphics.RectF r8 = (android.graphics.RectF) r8
            float r8 = r8.bottom
            float r8 = r7 - r8
            float r8 = java.lang.Math.abs(r8)
            float r1 = r3.rectsUnionDiffDelta
            int r8 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r8 > 0) goto L7c
            java.util.ArrayList<android.graphics.RectF> r8 = r3.rects
            int r1 = r8.size()
            int r1 = r1 + (-1)
            java.lang.Object r8 = r8.get(r1)
            android.graphics.RectF r8 = (android.graphics.RectF) r8
            r8.union(r4, r5, r6, r7)
            goto L9c
        L7c:
            java.util.ArrayList<android.graphics.RectF> r8 = org.telegram.p035ui.Components.CornerPath.recycled
            if (r8 == 0) goto L8f
            int r8 = r8.size()
            if (r8 <= 0) goto L8f
            java.util.ArrayList<android.graphics.RectF> r8 = org.telegram.p035ui.Components.CornerPath.recycled
            java.lang.Object r8 = r8.remove(r0)
            android.graphics.RectF r8 = (android.graphics.RectF) r8
            goto L94
        L8f:
            android.graphics.RectF r8 = new android.graphics.RectF
            r8.<init>()
        L94:
            r8.set(r4, r5, r6, r7)
            java.util.ArrayList<android.graphics.RectF> r4 = r3.rects
            r4.add(r8)
        L9c:
            r3.isPathCreated = r0
            return
        L9f:
            int r0 = r3.paddingX
            float r1 = (float) r0
            float r4 = r4 - r1
            int r1 = r3.paddingY
            float r2 = (float) r1
            float r5 = r5 - r2
            float r0 = (float) r0
            float r6 = r6 + r0
            float r0 = (float) r1
            float r7 = r7 + r0
            super.addRect(r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.CornerPath.addRect(float, float, float, float, android.graphics.Path$Direction):void");
    }

    @Override // android.graphics.Path
    public void reset() {
        super.reset();
        if (Build.VERSION.SDK_INT < 34 || !this.useCornerPathImplementation) {
            return;
        }
        resetRects();
    }

    @Override // android.graphics.Path
    public void rewind() {
        super.rewind();
        if (Build.VERSION.SDK_INT < 34 || !this.useCornerPathImplementation) {
            return;
        }
        resetRects();
    }

    private void resetRects() {
        if (recycled == null) {
            recycled = new ArrayList<>(this.rects.size());
        }
        recycled.addAll(this.rects);
        this.rects.clear();
        this.isPathCreated = false;
    }

    public void closeRects() {
        if (Build.VERSION.SDK_INT < 34 || !this.useCornerPathImplementation || this.isPathCreated) {
            return;
        }
        createClosedPathsFromRects(this.rects);
        this.isPathCreated = true;
    }

    public void setUseCornerPathImplementation(boolean z) {
        this.useCornerPathImplementation = z;
    }

    public void setRectsUnionDiffDelta(float f) {
        this.rectsUnionDiffDelta = f;
    }

    private void createClosedPathsFromRects(List<RectF> list) {
        if (list.isEmpty()) {
            return;
        }
        boolean z = false;
        if (list.size() == 1) {
            super.addRect(list.get(0).left - this.paddingX, list.get(0).top - this.paddingY, list.get(0).right + this.paddingX, list.get(0).bottom + this.paddingY, Path.Direction.CW);
            return;
        }
        RectF rectF = list.get(0);
        int size = list.size() - 1;
        super.moveTo(rectF.left - this.paddingX, rectF.top - this.paddingY);
        for (int i = 1; i < list.size(); i++) {
            RectF rectF2 = list.get(i);
            if (rectF2.width() != 0.0f) {
                float f = rectF.bottom;
                int i2 = this.paddingY;
                float f2 = f + i2;
                float f3 = rectF2.top;
                if (f2 >= f3 - i2) {
                    float f4 = rectF.left;
                    if (f4 <= rectF2.right) {
                        float f5 = rectF.right;
                        float f6 = rectF2.left;
                        if (f5 >= f6) {
                            if (f4 != f6) {
                                super.lineTo(f4 - this.paddingX, f3);
                                super.lineTo(rectF2.left - this.paddingX, rectF2.top);
                            }
                            rectF = rectF2;
                        }
                    }
                }
                z = true;
                size = i;
                break;
            }
        }
        super.lineTo(rectF.left - this.paddingX, rectF.bottom + this.paddingY);
        super.lineTo(rectF.right + this.paddingX, rectF.bottom + this.paddingY);
        for (int i3 = size - 1; i3 >= 0; i3--) {
            RectF rectF3 = list.get(i3);
            if (rectF3.width() != 0.0f) {
                float f7 = rectF.right;
                if (f7 != rectF3.right) {
                    super.lineTo(f7 + this.paddingX, rectF.top);
                    super.lineTo(rectF3.right + this.paddingX, rectF.top);
                }
                rectF = rectF3;
            }
        }
        super.lineTo(rectF.right + this.paddingX, rectF.top - this.paddingY);
        super.close();
        if (z) {
            createClosedPathsFromRects(list.subList(size, list.size()));
        }
    }
}
