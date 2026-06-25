package com.google.android.exoplayer2.p022ui;

import android.content.Context;
import android.graphics.Matrix;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: classes4.dex */
public class AspectRatioFrameLayout extends FrameLayout {
    private final AspectRatioUpdateDispatcher aspectRatioUpdateDispatcher;
    private boolean drawingReady;
    private Matrix matrix;
    private int resizeMode;
    private int rotation;
    private float videoAspectRatio;

    public interface AspectRatioListener {
    }

    /* JADX INFO: renamed from: -$$Nest$fgetaspectRatioListener, reason: not valid java name */
    public static /* bridge */ /* synthetic */ AspectRatioListener m3276$$Nest$fgetaspectRatioListener(AspectRatioFrameLayout aspectRatioFrameLayout) {
        aspectRatioFrameLayout.getClass();
        return null;
    }

    public void setAspectRatioListener(AspectRatioListener aspectRatioListener) {
    }

    public AspectRatioFrameLayout(Context context) {
        super(context);
        this.matrix = new Matrix();
        this.resizeMode = 0;
        this.aspectRatioUpdateDispatcher = new AspectRatioUpdateDispatcher();
    }

    public void setAspectRatio(float f, int i) {
        if (this.videoAspectRatio != f) {
            this.videoAspectRatio = f;
            this.rotation = i;
            requestLayout();
        }
    }

    public int getResizeMode() {
        return this.resizeMode;
    }

    public void setResizeMode(int i) {
        if (this.resizeMode != i) {
            this.resizeMode = i;
            requestLayout();
        }
    }

    public void setDrawingReady(boolean z) {
        if (this.drawingReady == z) {
            return;
        }
        this.drawingReady = z;
    }

    public float getAspectRatio() {
        return this.videoAspectRatio;
    }

    public int getVideoRotation() {
        return this.rotation;
    }

    public boolean isDrawingReady() {
        return this.drawingReady;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0047, code lost:
    
        if (r4 > 0.0f) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0049, code lost:
    
        r2 = r2 * r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004c, code lost:
    
        r1 = r1 / r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0053, code lost:
    
        if (r4 <= 0.0f) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0062, code lost:
    
        if (r4 > 0.0f) goto L22;
     */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onMeasure(int r12, int r13) {
        /*
            r11 = this;
            super.onMeasure(r12, r13)
            float r12 = r11.videoAspectRatio
            r13 = 0
            int r12 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r12 > 0) goto Lc
            goto Lc6
        Lc:
            int r12 = r11.getMeasuredWidth()
            int r0 = r11.getMeasuredHeight()
            float r1 = (float) r12
            float r2 = (float) r0
            float r3 = r1 / r2
            float r4 = r11.videoAspectRatio
            float r4 = r4 / r3
            r5 = 1065353216(0x3f800000, float:1.0)
            float r4 = r4 - r5
            float r6 = java.lang.Math.abs(r4)
            r7 = 1008981770(0x3c23d70a, float:0.01)
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            r7 = 0
            if (r6 > 0) goto L32
            com.google.android.exoplayer2.ui.AspectRatioFrameLayout$AspectRatioUpdateDispatcher r12 = r11.aspectRatioUpdateDispatcher
            float r11 = r11.videoAspectRatio
            r12.scheduleUpdate(r11, r3, r7)
            return
        L32:
            int r6 = r11.resizeMode
            r8 = 2
            r9 = 1
            if (r6 == 0) goto L5e
            if (r6 == r9) goto L5a
            if (r6 == r8) goto L56
            r10 = 3
            if (r6 == r10) goto L4f
            r10 = 4
            if (r6 == r10) goto L43
            goto L65
        L43:
            int r13 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            float r4 = r11.videoAspectRatio
            if (r13 <= 0) goto L4c
        L49:
            float r2 = r2 * r4
        L4a:
            int r12 = (int) r2
            goto L65
        L4c:
            float r1 = r1 / r4
        L4d:
            int r0 = (int) r1
            goto L65
        L4f:
            int r13 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            float r4 = r11.videoAspectRatio
            if (r13 > 0) goto L49
            goto L4c
        L56:
            float r12 = r11.videoAspectRatio
            float r2 = r2 * r12
            goto L4a
        L5a:
            float r13 = r11.videoAspectRatio
            float r1 = r1 / r13
            goto L4d
        L5e:
            int r13 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            float r4 = r11.videoAspectRatio
            if (r13 <= 0) goto L49
            goto L4c
        L65:
            com.google.android.exoplayer2.ui.AspectRatioFrameLayout$AspectRatioUpdateDispatcher r13 = r11.aspectRatioUpdateDispatcher
            float r1 = r11.videoAspectRatio
            r13.scheduleUpdate(r1, r3, r9)
            r13 = 1073741824(0x40000000, float:2.0)
            int r12 = android.view.View.MeasureSpec.makeMeasureSpec(r12, r13)
            int r13 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r13)
            super.onMeasure(r12, r13)
            int r12 = r11.getChildCount()
        L7d:
            if (r7 >= r12) goto Lc6
            android.view.View r13 = r11.getChildAt(r7)
            boolean r0 = r13 instanceof android.view.TextureView
            if (r0 == 0) goto Lc3
            android.graphics.Matrix r12 = r11.matrix
            r12.reset()
            int r12 = r11.getWidth()
            int r12 = r12 / r8
            int r0 = r11.getHeight()
            int r0 = r0 / r8
            android.graphics.Matrix r1 = r11.matrix
            int r2 = r11.rotation
            float r2 = (float) r2
            float r12 = (float) r12
            float r0 = (float) r0
            r1.postRotate(r2, r12, r0)
            int r1 = r11.rotation
            r2 = 90
            if (r1 == r2) goto Laa
            r2 = 270(0x10e, float:3.78E-43)
            if (r1 != r2) goto Lbb
        Laa:
            int r1 = r11.getHeight()
            float r1 = (float) r1
            int r2 = r11.getWidth()
            float r2 = (float) r2
            float r1 = r1 / r2
            android.graphics.Matrix r2 = r11.matrix
            float r5 = r5 / r1
            r2.postScale(r5, r1, r12, r0)
        Lbb:
            android.view.TextureView r13 = (android.view.TextureView) r13
            android.graphics.Matrix r11 = r11.matrix
            r13.setTransform(r11)
            return
        Lc3:
            int r7 = r7 + 1
            goto L7d
        Lc6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.p022ui.AspectRatioFrameLayout.onMeasure(int, int):void");
    }

    public final class AspectRatioUpdateDispatcher implements Runnable {
        private boolean aspectRatioMismatch;
        private boolean isScheduled;
        private float naturalAspectRatio;
        private float targetAspectRatio;

        private AspectRatioUpdateDispatcher() {
        }

        public void scheduleUpdate(float f, float f2, boolean z) {
            this.targetAspectRatio = f;
            this.naturalAspectRatio = f2;
            this.aspectRatioMismatch = z;
            if (this.isScheduled) {
                return;
            }
            this.isScheduled = true;
            AspectRatioFrameLayout.this.post(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            this.isScheduled = false;
            AspectRatioFrameLayout.m3276$$Nest$fgetaspectRatioListener(AspectRatioFrameLayout.this);
        }
    }
}
