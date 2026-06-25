package org.telegram.p035ui;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.text.Layout;
import android.view.Choreographer;
import android.view.View;
import androidx.core.math.MathUtils;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;

/* JADX INFO: loaded from: classes6.dex */
public final class MultiLayoutTypingAnimator implements Choreographer.FrameCallback {
    private static final LinearGradient GRADIENT;
    private static final Matrix GRAD_MTX = new Matrix();
    private static final Paint MASK_PAINT;
    private View invalidateTarget;
    private View lastInvalidatedView;
    private Runnable onFinishRunnable;
    private final Choreographer choreo = Choreographer.getInstance();
    private List<? extends Block> blocks = new ArrayList();
    private final ArrayList<Float> blockAlphas = new ArrayList<>();
    private int curBlockIdx = 0;
    private int curLineIdx = 0;
    private float xPosition = 0.0f;
    private boolean running = false;
    private boolean finished = true;
    private long lastFrameNs = 0;
    private float speedPxPerSec = AndroidUtilities.m1036dp(40.0f);

    public interface Block {
        Layout getLayout();

        View getParentView();
    }

    public interface Renderer {
        void draw(Canvas canvas);
    }

    private void applyBlockAlphas(float f) {
        int size = this.blocks.size();
        View view = null;
        int i = 0;
        while (i < size) {
            Block block = this.blocks.get(i);
            float fMin = (this.finished || i <= this.curBlockIdx) ? 1.0f : 0.0f;
            float fFloatValue = i < this.blockAlphas.size() ? this.blockAlphas.get(i).floatValue() : fMin;
            if (fFloatValue == fMin) {
                fMin = fFloatValue;
            } else if (f > 0.0f) {
                float f2 = f / 0.2f;
                fMin = fMin > fFloatValue ? Math.min(fMin, f2 + fFloatValue) : Math.max(fMin, fFloatValue - f2);
            }
            if (i < this.blockAlphas.size() && fMin != fFloatValue) {
                this.blockAlphas.set(i, Float.valueOf(fMin));
            }
            View parentView = block.getParentView();
            if (parentView != null && parentView != view) {
                if (parentView.getAlpha() != fMin) {
                    parentView.setAlpha(fMin);
                }
                view = parentView;
            }
            i++;
        }
    }

    private void resetBlockAlphas() {
        int size = this.blockAlphas.size();
        for (int i = 0; i < size; i++) {
            this.blockAlphas.set(i, Float.valueOf(1.0f));
        }
        int size2 = this.blocks.size();
        View view = null;
        for (int i2 = 0; i2 < size2; i2++) {
            View parentView = this.blocks.get(i2).getParentView();
            if (parentView != null && parentView != view) {
                if (parentView.getAlpha() != 1.0f) {
                    parentView.setAlpha(1.0f);
                }
                view = parentView;
            }
        }
    }

    public float getBlockAlpha(Block block) {
        int iIndexOf = indexOf(block);
        if (iIndexOf < 0 || iIndexOf >= this.blockAlphas.size()) {
            return 1.0f;
        }
        return this.blockAlphas.get(iIndexOf).floatValue();
    }

    private void invalidate() {
        Block block;
        int i = this.curBlockIdx;
        View parentView = (i < 0 || i >= this.blocks.size() || (block = this.blocks.get(this.curBlockIdx)) == null) ? null : block.getParentView();
        if (parentView != null) {
            parentView.invalidate();
            View view = this.lastInvalidatedView;
            if (view != null && view != parentView) {
                view.invalidate();
            }
            this.lastInvalidatedView = parentView;
            return;
        }
        View view2 = this.invalidateTarget;
        if (view2 != null) {
            view2.invalidate();
        }
    }

    public void setBlocks(List<? extends Block> list) {
        ArrayList<Float> arrayList;
        if (!this.blocks.isEmpty() && this.curBlockIdx >= this.blocks.size()) {
            int size = this.blocks.size() - 1;
            this.curBlockIdx = size;
            Layout layout = this.blocks.get(size).getLayout();
            int iMax = Math.max(0, layout == null ? 0 : layout.getLineCount() - 1);
            this.curLineIdx = iMax;
            this.xPosition = layout == null ? 0.0f : layout.getLineWidth(iMax);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        this.blocks = list;
        recalcSpeed();
        this.finished = isAtAbsoluteEnd();
        while (true) {
            int size2 = this.blockAlphas.size();
            int size3 = this.blocks.size();
            arrayList = this.blockAlphas;
            if (size2 <= size3) {
                break;
            } else {
                arrayList.remove(arrayList.size() - 1);
            }
        }
        int size4 = arrayList.size();
        while (size4 < this.blocks.size()) {
            this.blockAlphas.add(Float.valueOf((this.finished || size4 <= this.curBlockIdx) ? 1.0f : 0.0f));
            size4++;
        }
        if (!this.finished && !this.running) {
            start();
        }
        applyBlockAlphas(0.0f);
        invalidate();
    }

    public void start() {
        if (this.running) {
            return;
        }
        this.running = true;
        if (isAtAbsoluteEnd()) {
            this.finished = true;
        }
        this.lastFrameNs = 0L;
        this.choreo.postFrameCallback(this);
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setOnFinishListener(Runnable runnable) {
        this.onFinishRunnable = runnable;
    }

    public int getFadeLineIndex(Block block) {
        if (isFadeBlock(block)) {
            return this.curLineIdx;
        }
        return -1;
    }

    public float getFadeXPosition(Block block) {
        if (isFadeBlock(block)) {
            return this.xPosition;
        }
        return 0.0f;
    }

    public boolean needDraw(Block block) {
        int iIndexOf = indexOf(block);
        if (iIndexOf < 0 || this.blocks.isEmpty()) {
            return false;
        }
        int i = this.curBlockIdx;
        return iIndexOf < i || iIndexOf <= i;
    }

    public boolean isFadeBlock(Block block) {
        Layout layout;
        return indexOf(block) == this.curBlockIdx && (layout = block.getLayout()) != null && this.curLineIdx < layout.getLineCount();
    }

    @Override // android.view.Choreographer.FrameCallback
    public void doFrame(long j) {
        float f;
        if (this.running) {
            if (this.lastFrameNs != 0) {
                f = (j - r0) * 1.0E-9f;
                advance(f);
            } else {
                f = 0.0f;
            }
            this.lastFrameNs = j;
            applyBlockAlphas(f);
            invalidate();
            if (this.finished) {
                this.running = false;
                resetBlockAlphas();
                Runnable runnable = this.onFinishRunnable;
                if (runnable != null) {
                    runnable.run();
                    this.onFinishRunnable = null;
                    return;
                }
                return;
            }
            this.choreo.postFrameCallback(this);
        }
    }

    private void advance(float f) {
        if (this.blocks.isEmpty() || f <= 0.0f) {
            this.finished = this.blocks.isEmpty();
            return;
        }
        float f2 = this.speedPxPerSec * f;
        while (true) {
            if (f2 <= 0.0f) {
                break;
            }
            if (this.curBlockIdx >= this.blocks.size()) {
                this.finished = true;
                break;
            }
            Layout layout = this.blocks.get(this.curBlockIdx).getLayout();
            if (layout == null || layout.getLineCount() == 0) {
                this.curBlockIdx++;
                this.curLineIdx = 0;
                this.xPosition = 0.0f;
            } else {
                if (this.curLineIdx >= layout.getLineCount()) {
                    int lineCount = layout.getLineCount() - 1;
                    this.curLineIdx = lineCount;
                    this.xPosition = lineWidth(layout, lineCount);
                }
                float fLineWidth = lineWidth(layout, this.curLineIdx);
                if (fLineWidth <= 0.001f) {
                    if (nextLineOrBlock(layout)) {
                        break;
                    }
                } else {
                    float f3 = this.xPosition;
                    float f4 = fLineWidth - f3;
                    if (f4 <= 0.001f) {
                        if (nextLineOrBlock(layout)) {
                            break;
                        }
                    } else {
                        if (f2 < f4) {
                            f4 = f2;
                        }
                        float f5 = f3 + f4;
                        this.xPosition = f5;
                        f2 -= f4;
                        if (fLineWidth - f5 <= 0.001f && !nextLineOrBlock(layout)) {
                            f2 = 0.0f;
                        }
                    }
                }
            }
        }
        this.finished = isAtAbsoluteEnd();
    }

    private boolean nextLineOrBlock(Layout layout) {
        int i = this.curLineIdx + 1;
        this.curLineIdx = i;
        this.xPosition = 0.0f;
        if (i >= layout.getLineCount()) {
            int i2 = this.curBlockIdx + 1;
            this.curBlockIdx = i2;
            this.curLineIdx = 0;
            this.xPosition = 0.0f;
            if (i2 >= this.blocks.size()) {
                return true;
            }
        }
        return false;
    }

    private void recalcSpeed() {
        float fComputeRemainingPixels = computeRemainingPixels();
        float fM1036dp = AndroidUtilities.m1036dp(40.0f);
        if (fComputeRemainingPixels <= 0.001f) {
            this.speedPxPerSec = fM1036dp;
        } else {
            this.speedPxPerSec = Math.max(fM1036dp, fComputeRemainingPixels / 1.05f);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x005c A[PHI: r5
  0x005c: PHI (r5v2 float) = (r5v1 float), (r5v1 float), (r5v3 float) binds: [B:21:0x0051, B:22:0x0053, B:24:0x005a] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float computeRemainingPixels() {
        /*
            r8 = this;
            java.util.List<? extends org.telegram.ui.MultiLayoutTypingAnimator$Block> r0 = r8.blocks
            boolean r0 = r0.isEmpty()
            r1 = 0
            if (r0 == 0) goto La
            return r1
        La:
            int r0 = r8.curBlockIdx
        Lc:
            java.util.List<? extends org.telegram.ui.MultiLayoutTypingAnimator$Block> r2 = r8.blocks
            int r2 = r2.size()
            if (r0 >= r2) goto L63
            java.util.List<? extends org.telegram.ui.MultiLayoutTypingAnimator$Block> r2 = r8.blocks
            java.lang.Object r2 = r2.get(r0)
            org.telegram.ui.MultiLayoutTypingAnimator$Block r2 = (org.telegram.ui.MultiLayoutTypingAnimator.Block) r2
            android.text.Layout r2 = r2.getLayout()
            if (r2 != 0) goto L23
            goto L60
        L23:
            int r3 = r8.curBlockIdx
            r4 = 0
            if (r0 != r3) goto L3c
            int r3 = r8.curLineIdx
            int r3 = java.lang.Math.max(r3, r4)
            int r5 = r2.getLineCount()
            int r5 = r5 + (-1)
            int r4 = java.lang.Math.max(r4, r5)
            int r4 = java.lang.Math.min(r3, r4)
        L3c:
            r3 = r4
        L3d:
            int r5 = r2.getLineCount()
            if (r3 >= r5) goto L60
            float r5 = r8.lineWidth(r2, r3)
            r6 = 981668463(0x3a83126f, float:0.001)
            int r7 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r7 > 0) goto L4f
            goto L5d
        L4f:
            int r7 = r8.curBlockIdx
            if (r0 != r7) goto L5c
            if (r3 != r4) goto L5c
            float r7 = r8.xPosition
            float r5 = r5 - r7
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 <= 0) goto L5d
        L5c:
            float r1 = r1 + r5
        L5d:
            int r3 = r3 + 1
            goto L3d
        L60:
            int r0 = r0 + 1
            goto Lc
        L63:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.MultiLayoutTypingAnimator.computeRemainingPixels():float");
    }

    private boolean isAtAbsoluteEnd() {
        if (this.blocks.isEmpty()) {
            return true;
        }
        int size = this.blocks.size() - 1;
        Layout layout = null;
        while (size >= 0) {
            layout = this.blocks.get(size).getLayout();
            if (layout != null && layout.getLineCount() > 0) {
                break;
            }
            size--;
        }
        if (size < 0 || layout == null) {
            return true;
        }
        int i = this.curBlockIdx;
        if (i < size) {
            return false;
        }
        if (i > size) {
            return true;
        }
        int lineCount = layout.getLineCount() - 1;
        return this.curLineIdx >= lineCount && this.xPosition >= lineWidth(layout, lineCount) - 0.001f;
    }

    private float lineWidth(Layout layout, int i) {
        float lineRight = layout.getLineRight(i) - layout.getLineLeft(i);
        return lineRight >= 0.0f ? lineRight : -lineRight;
    }

    public int indexOf(Block block) {
        int size = this.blocks.size();
        for (int i = 0; i < size; i++) {
            if (this.blocks.get(i) == block) {
                return i;
            }
        }
        return -1;
    }

    static {
        Paint paint = new Paint(1);
        MASK_PAINT = paint;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 1.0f, 0.0f, -1, 16777215, Shader.TileMode.CLAMP);
        GRADIENT = linearGradient;
        paint.setShader(linearGradient);
    }

    public static void drawLayoutWithLastLineFade(Canvas canvas, Layout layout, int i, float f) {
        drawLayoutWithLastLineFade(canvas, layout, i, f, null);
    }

    public static void drawLayoutWithLastLineFade(Canvas canvas, final Layout layout, int i, float f, Renderer renderer) {
        if (layout == null) {
            return;
        }
        int lineCount = layout.getLineCount();
        if (i < 0 || i >= lineCount) {
            return;
        }
        Renderer renderer2 = renderer != null ? renderer : new Renderer() { // from class: org.telegram.ui.MultiLayoutTypingAnimator$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.MultiLayoutTypingAnimator.Renderer
            public final void draw(Canvas canvas2) {
                layout.draw(canvas2);
            }
        };
        int width = layout.getWidth();
        layout.getHeight();
        int lineTop = layout.getLineTop(i);
        int lineBottom = layout.getLineBottom(i);
        if (lineTop > 0) {
            canvas.save();
            canvas.clipRect(0.0f, 0.0f, width, lineTop);
            renderer2.draw(canvas);
            canvas.restore();
        }
        float lineLeft = layout.getLineLeft(i);
        float lineRight = layout.getLineRight(i);
        float fMin = Math.min(lineLeft, lineRight);
        float fMax = Math.max(lineLeft, lineRight);
        if (fMax <= fMin) {
            return;
        }
        int paragraphDirection = layout.getParagraphDirection(i);
        float f2 = fMax - fMin;
        float fClamp = MathUtils.clamp(f, 0.0f, f2);
        float f3 = fClamp / f2;
        if (fClamp <= 0.0f) {
            return;
        }
        if (fClamp >= f2) {
            canvas.save();
            canvas.clipRect(0.0f, lineTop, width, lineBottom);
            renderer2.draw(canvas);
            canvas.restore();
            return;
        }
        float fLerp = AndroidUtilities.lerp(-AndroidUtilities.m1036dp(50.0f), f2, f3);
        AndroidUtilities.m1036dp(50.0f);
        float f4 = lineTop;
        float f5 = lineBottom;
        int iSaveLayer = canvas.saveLayer(fMin, f4, fMax, f5, null);
        canvas.save();
        canvas.clipRect(fMin, f4, fMax, f5);
        renderer2.draw(canvas);
        canvas.restore();
        Matrix matrix = GRAD_MTX;
        matrix.reset();
        if (paragraphDirection >= 0) {
            matrix.setScale(AndroidUtilities.m1036dp(50.0f), 1.0f);
            matrix.postTranslate(fLerp, 0.0f);
        } else {
            matrix.setScale(-AndroidUtilities.m1036dp(50.0f), 1.0f);
            matrix.postTranslate(f2 - fLerp, 0.0f);
        }
        GRADIENT.setLocalMatrix(matrix);
        canvas.drawRect(fMin, f4, fMax, f5, MASK_PAINT);
        canvas.restoreToCount(iSaveLayer);
    }
}
